package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.telegrambot.TelegramBotPermission;
import com.una.embyhub.config.common.telegrambot.TelegramStartPanelImageStorage;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.mapper.NotifyChannelMapper;
import com.una.embyhub.model.dto.request.notifychannel.NotifyChannelSave;
import com.una.embyhub.model.dto.request.notifychannel.NotifyChannelUpdate;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramBotAdminConfig;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.notifychannel.NotifyChannelResponse;
import com.una.embyhub.model.entity.NotifyChannel;
import com.una.embyhub.pointsbot.service.PointsBotConfigRules;
import com.una.embyhub.pointsbot.service.PointsBotGameConfigService;
import com.una.embyhub.service.NotifyChannelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class NotifyChannelServiceImpl extends ServiceImpl<NotifyChannelMapper, NotifyChannel> implements NotifyChannelService {
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   @Autowired
   private TelegramStartPanelImageStorage telegramStartPanelImageStorage;
   @Autowired
   private PointsBotGameConfigService pointsBotGameConfigService;

   @Override
   public List<NotifyChannelResponse> select() {
      return BeanUtils.convertList(this.list(), NotifyChannelResponse.class);
   }

   @Override
   public NotifyChannelResponse add(NotifyChannelSave notifyChannelSave) {
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(NotifyChannel::getIconType, notifyChannelSave.getIconType()).count();
      if (count > 0L) {
         throw new BizException(ResponseStatusEnum.NOTIFY_CHANNEL_ICON_TYPE_EXIST);
      } else {
         NotifyChannel notifyChannel = BeanUtils.convert(notifyChannelSave, NotifyChannel.class);
         if ("telegram".equals(notifyChannel.getIconType())) {
            notifyChannel.setCustomIcon(null);
         }

         notifyChannel.setParams(this.normalizeAndValidateParams(notifyChannel.getIconType(), notifyChannel.getParams()));
         this.save(notifyChannel);
         this.notifyChannelCacheLoaderUtils.loadConfigCache();
         return BeanUtils.convert(notifyChannel, NotifyChannelResponse.class);
      }
   }

   @Override
   public void update(NotifyChannelUpdate notifyChannelUpdate) {
      NotifyChannel existing = this.getById(notifyChannelUpdate.getId());
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(NotifyChannel::getIconType, notifyChannelUpdate.getIconType())
         .ne(NotifyChannel::getId, notifyChannelUpdate.getId())
         .count();
      if (count > 0L) {
         throw new BizException(ResponseStatusEnum.NOTIFY_CHANNEL_ICON_TYPE_EXIST);
      } else {
         NotifyChannel notifyChannel = BeanUtils.convert(notifyChannelUpdate, NotifyChannel.class);
         boolean clearTelegramStartImage = existing != null
            && "telegram".equals(existing.getIconType())
            && (
               !"telegram".equals(notifyChannel.getIconType())
                  || notifyChannelUpdate.getCustomIcon() != null && !StringUtils.hasText(notifyChannelUpdate.getCustomIcon())
            );
         if (existing != null
            && "telegram".equals(notifyChannel.getIconType())
            && StringUtils.hasText(notifyChannelUpdate.getCustomIcon())
            && !Objects.equals(existing.getCustomIcon(), notifyChannelUpdate.getCustomIcon())) {
            notifyChannel.setCustomIcon(existing.getCustomIcon());
         }

         String normalizedParams = this.normalizeAndValidateParams(notifyChannel.getIconType(), notifyChannel.getParams());
         if ("telegram".equals(notifyChannel.getIconType()) && existing != null) {
            normalizedParams = this.preserveWhitelistGiftTemplateConfig(existing.getParams(), normalizedParams);
         }
         notifyChannel.setParams(normalizedParams);
         this.updateById(notifyChannel);
         if (clearTelegramStartImage) {
            this.lambdaUpdate().eq(NotifyChannel::getId, notifyChannelUpdate.getId()).set(NotifyChannel::getCustomIcon, null).update();
            this.deleteImageAfterCommit(existing.getCustomIcon());
         }

         this.notifyChannelCacheLoaderUtils.loadConfigCache();
      }
   }

   @Override
   public synchronized boolean grantTelegramBotPermission(String telegramId, String name, TelegramBotPermission permission) {
      if (!StringUtils.hasText(telegramId) || permission == null) {
         throw this.badRequest("Telegram ID 或授权权限无效");
      }

      NotifyChannel channel = this.lambdaQuery().eq(NotifyChannel::getIconType, "telegram").eq(NotifyChannel::getEnabled, Integer.valueOf(1)).one();
      if (channel == null) {
         throw this.badRequest("Telegram 通知渠道尚未启用");
      }

      JSONObject params = StringUtils.hasText(channel.getParams()) ? JSONObject.parseObject(channel.getParams()) : new JSONObject();
      if (params == null) {
         params = new JSONObject();
      }

      TelegramResponse telegram = params.toJavaObject(TelegramResponse.class);
      long ownerId = this.parsePositiveTelegramId(telegram.getBotChatId(), "Owner Telegram ID");
      long targetId = this.parsePositiveTelegramId(telegramId, "管理员 Telegram ID");
      if (targetId == ownerId) {
         throw this.badRequest("Owner 已拥有全部权限，无需重复授权");
      }

      List<TelegramBotAdminConfig> admins = telegram.getBotAdmins();
      if (admins == null) {
         admins = new ArrayList<>();
         telegram.setBotAdmins(admins);
      }

      TelegramBotAdminConfig target = admins.stream()
         .filter(admin -> admin != null && String.valueOf(targetId).equals(StringUtils.trimWhitespace(admin.getTelegramId())))
         .findFirst()
         .orElse(null);
      boolean changed = false;
      if (target == null) {
         target = new TelegramBotAdminConfig();
         target.setTelegramId(String.valueOf(targetId));
         target.setName(StringUtils.hasText(name) ? name.trim() : String.valueOf(targetId));
         target.setPermissions(new ArrayList<>(List.of(permission.name())));
         admins.add(target);
         changed = true;
      } else {
         if (StringUtils.hasText(name) && !name.trim().equals(target.getName())) {
            target.setName(name.trim());
            changed = true;
         }
         List<String> permissions = target.getPermissions();
         if (permissions == null) {
            permissions = new ArrayList<>();
            target.setPermissions(permissions);
         }
         if (!permissions.stream().anyMatch(permission.name()::equalsIgnoreCase)) {
            permissions.add(permission.name());
            changed = true;
         }
      }

      if (changed) {
         channel.setParams(this.normalizeAndValidateParams(channel.getIconType(), JSONObject.from(telegram).toJSONString()));
         this.updateById(channel);
         this.notifyChannelCacheLoaderUtils.loadConfigCache();
      }

      return changed;
   }

   @Override
   public synchronized boolean revokeTelegramBotPermissions(String telegramId) {
      long targetId = this.parsePositiveTelegramId(telegramId, "管理员 Telegram ID");
      NotifyChannel channel = this.lambdaQuery().eq(NotifyChannel::getIconType, "telegram").eq(NotifyChannel::getEnabled, Integer.valueOf(1)).one();
      if (channel == null) {
         throw this.badRequest("Telegram 通知渠道尚未启用");
      }

      JSONObject params = StringUtils.hasText(channel.getParams()) ? JSONObject.parseObject(channel.getParams()) : new JSONObject();
      if (params == null) {
         params = new JSONObject();
      }

      TelegramResponse telegram = params.toJavaObject(TelegramResponse.class);
      long ownerId = this.parsePositiveTelegramId(telegram.getBotChatId(), "Owner Telegram ID");
      if (targetId == ownerId) {
         throw this.badRequest("Owner 不能被撤销授权");
      }

      List<TelegramBotAdminConfig> admins = telegram.getBotAdmins();
      if (admins == null || admins.isEmpty()) {
         return false;
      }

      boolean changed = admins.removeIf(admin -> admin != null && String.valueOf(targetId).equals(StringUtils.trimWhitespace(admin.getTelegramId())));
      if (changed) {
         channel.setParams(this.normalizeAndValidateParams(channel.getIconType(), JSONObject.from(telegram).toJSONString()));
         this.updateById(channel);
         this.notifyChannelCacheLoaderUtils.loadConfigCache();
      }

      return changed;
   }

   @Override
   public void delete(Long id) {
      NotifyChannel existing = this.getById(id);
      this.removeById(id);
      if (existing != null && "telegram".equals(existing.getIconType())) {
         this.deleteImageAfterCommit(existing.getCustomIcon());
      }

      this.notifyChannelCacheLoaderUtils.loadConfigCache();
   }

   @Override
   public NotifyChannelResponse uploadTelegramStartPanelImage(Long channelId, MultipartFile file) {
      NotifyChannel channel = this.requireTelegramChannel(channelId);
      String oldFileName = channel.getCustomIcon();
      String newFileName = this.telegramStartPanelImageStorage.store(channelId, file);

      boolean updated;
      try {
         updated = this.lambdaUpdate().eq(NotifyChannel::getId, channelId).set(NotifyChannel::getCustomIcon, newFileName).update();
      } catch (RuntimeException var8) {
         this.telegramStartPanelImageStorage.delete(newFileName);
         throw var8;
      }

      if (!updated) {
         this.telegramStartPanelImageStorage.delete(newFileName);
         throw this.badRequest("Telegram 渠道不存在或已被删除");
      } else {
         this.deleteImageAfterCommit(oldFileName);
         this.deleteImageOnRollback(newFileName);
         this.notifyChannelCacheLoaderUtils.loadConfigCache();
         return BeanUtils.convert(this.getById(channelId), NotifyChannelResponse.class);
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public byte[] getTelegramStartPanelImage(Long channelId) {
      NotifyChannel channel = this.requireTelegramChannel(channelId);
      return this.telegramStartPanelImageStorage.read(channel.getCustomIcon());
   }

   @Override
   public NotifyChannelResponse resetTelegramStartPanelImage(Long channelId) {
      NotifyChannel channel = this.requireTelegramChannel(channelId);
      String oldFileName = channel.getCustomIcon();
      boolean updated = this.lambdaUpdate().eq(NotifyChannel::getId, channelId).set(NotifyChannel::getCustomIcon, null).update();
      if (!updated) {
         throw this.badRequest("Telegram 渠道不存在或已被删除");
      } else {
         this.deleteImageAfterCommit(oldFileName);
         this.notifyChannelCacheLoaderUtils.loadConfigCache();
         return BeanUtils.convert(this.getById(channelId), NotifyChannelResponse.class);
      }
   }

   private NotifyChannel requireTelegramChannel(Long channelId) {
      if (channelId != null && channelId > 0L) {
         NotifyChannel channel = this.getById(channelId);
         if (channel != null && "telegram".equals(channel.getIconType())) {
            return channel;
         } else {
            throw this.badRequest("未找到对应的 Telegram 通知渠道");
         }
      } else {
         throw this.badRequest("Telegram 渠道 ID 无效");
      }
   }

   private void deleteImageAfterCommit(String configuredFileName) {
      if (StringUtils.hasText(configuredFileName)) {
         if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            this.telegramStartPanelImageStorage.delete(configuredFileName);
         } else {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
               @Override
               public void afterCommit() {
                  NotifyChannelServiceImpl.this.telegramStartPanelImageStorage.delete(configuredFileName);
               }
            });
         }
      }
   }

   private void deleteImageOnRollback(String configuredFileName) {
      if (StringUtils.hasText(configuredFileName) && TransactionSynchronizationManager.isSynchronizationActive()) {
         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
               if (status != 0) {
                  NotifyChannelServiceImpl.this.telegramStartPanelImageStorage.delete(configuredFileName);
               }
            }
         });
      }
   }

   private String normalizeAndValidateParams(String iconType, String rawParams) {
      JSONObject params = JSONObject.parseObject(rawParams);
      if ("telegram".equals(iconType) || "pointsBot".equals(iconType)) {
         PointsBotConfigRules.validateTransferRange(params);
         PointsBotConfigRules.validateGameConfiguration(params);
         PointsBotConfigRules.validateRedPacketConfiguration(params);
         this.pointsBotGameConfigService.validateSelectedGames(params.getList("enabledGameCommands", String.class));
      }

      if ("telegram".equals(iconType)) {
         this.validateTelegramAdmins(params);
      }

      return JSONObject.toJSONString(params);
   }

   private String preserveWhitelistGiftTemplateConfig(String previousRawParams, String normalizedRawParams) {
      JSONObject previous = StringUtils.hasText(previousRawParams) ? JSONObject.parseObject(previousRawParams) : null;
      JSONObject current = StringUtils.hasText(normalizedRawParams) ? JSONObject.parseObject(normalizedRawParams) : null;
      if (previous == null || current == null) {
         return normalizedRawParams;
      }

      for (String key : List.of("whitelistGiftTemplateMode", "whitelistGiftTemplates", "whitelistGiftAdminTemplates")) {
         if (!current.containsKey(key) && previous.containsKey(key)) {
            current.put(key, previous.get(key));
         }
      }

      return current.toJSONString();
   }

   private void validateTelegramAdmins(JSONObject params) {
      TelegramResponse telegram = params.toJavaObject(TelegramResponse.class);
      List<TelegramBotAdminConfig> admins = telegram.getBotAdmins();
      if (!CollectionUtils.isEmpty(admins)) {
         if (admins.size() > 20) {
            throw this.badRequest("机器人管理员最多配置 20 人");
         } else {
            long ownerId = this.parsePositiveTelegramId(telegram.getBotChatId(), "Owner Telegram ID");
            Set<Long> adminIds = new HashSet<>();

            for (TelegramBotAdminConfig admin : admins) {
               if (admin == null) {
                  throw this.badRequest("机器人管理员配置不能为空");
               }

               long adminId = this.parsePositiveTelegramId(admin.getTelegramId(), "管理员 Telegram ID");
               if (adminId == ownerId) {
                  throw this.badRequest("Owner 已拥有全部权限，无需重复添加");
               }

               if (!adminIds.add(adminId)) {
                  throw this.badRequest("管理员 Telegram ID 不能重复");
               }

               if (CollectionUtils.isEmpty(admin.getPermissions())) {
                  throw this.badRequest("请至少为每位机器人管理员选择一项权限");
               }

               for (String permission : admin.getPermissions()) {
                  if (TelegramBotPermission.from(permission).isEmpty()) {
                     throw this.badRequest("存在不受支持的机器人管理员权限");
                  }
               }

               boolean requiresUserSelection = admin.getPermissions()
                  .stream()
                  .anyMatch(
                     permissionx -> TelegramBotPermission.USER_STATUS.name().equals(permissionx)
                           || TelegramBotPermission.USER_PASSWORD.name().equals(permissionx)
                  );
               if (requiresUserSelection && admin.getPermissions().stream().noneMatch(TelegramBotPermission.USER_VIEW.name()::equals)) {
                  throw this.badRequest("状态或密码权限需要同时授予用户查询权限");
               }
            }
         }
      }
   }

   private long parsePositiveTelegramId(String value, String label) {
      if (!StringUtils.hasText(value)) {
         throw this.badRequest("请填写" + label);
      } else {
         try {
            long parsed = Long.parseLong(value.trim());
            if (parsed <= 0L) {
               throw this.badRequest(label + "必须为正整数");
            } else {
               return parsed;
            }
         } catch (NumberFormatException var5) {
            throw this.badRequest(label + "格式不正确");
         }
      }
   }

   private BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }
}
