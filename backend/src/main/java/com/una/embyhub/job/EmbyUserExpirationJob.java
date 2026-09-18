package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.dto.request.embyuser.DisableUserRequest;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.RoseUserBindingService;
import embyclient.ApiException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

@Configuration
@EnableScheduling
public class EmbyUserExpirationJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyUserExpirationJob.class);
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private UserOauthBindingMapper userOauthBindingMapper;
   @Autowired
   private RoseUserBindingService roseUserBindingService;
   @Autowired
   private UserExpirationTelegramNotifyHelper telegramNotifyHelper;

   @Scheduled(
      cron = "0 0 * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "用户到期禁用任务",
      remark = "用户到期禁用会提醒管理员"
   )
   public void configureTasksDisableUser() {
      log.info("用户过期禁用定时任务：{}", DateUtil.formatDateTime(new Date()));
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
         if (serverConfigs.isEmpty()) {
            serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
         }

         boolean directTelegramNotify = this.telegramNotifyHelper.isDirectUserNotifyEnabled();

         for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
            for (EmbyUser embyUser : new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper())
               .eq(EmbyUser::getUserStatus, Integer.valueOf(0))
               .ne(EmbyUser::getIsAdmin, Integer.valueOf(1))
               .and(
                  wrapper -> wrapper.ne(EmbyUser::getHostLineType, Integer.valueOf(HostLineTypeEnum.WHITELIST.getCode()))
                        .or()
                        .isNull(EmbyUser::getHostLineType)
               )
               .eq(serverConfig.id() != null, EmbyUser::getEmbyInfoId, serverConfig.id())
               .isNull(serverConfig.id() == null, EmbyUser::getEmbyInfoId)
               .list()) {
               if (embyUser.getExpirationDate() != null && embyUser.getExpirationDate().before(new Date())) {
                  log.info("用户过期禁用：{}", embyUser.getEmbyUserName());
                  DisableUserRequest disableUserRequest = new DisableUserRequest();
                  disableUserRequest.setEmbyUserId(embyUser.getEmbyUserId());
                  this.embyUserService.disableUser(disableUserRequest);
                  Map<String, String> extras = new HashMap<>();
                  extras.put("userName", embyUser.getEmbyUserName());
                  extras.put("reason", "过期");
                  SendMessageRequest sendMessageRequest = new SendMessageRequest();
                  sendMessageRequest.setParseMode("Markdown");
                  sendMessageRequest.setName("Emby用户过期禁用提醒");
                  sendMessageRequest.setServerUrl(this.resolveServerUrl(serverConfig));
                  sendMessageRequest.setServerName(this.resolveServerName(serverConfig));
                  sendMessageRequest.setExtraVariables(extras);
                  this.telegramNotifyHelper.sendTelegram(embyUser, sendMessageRequest, "user_disabled", directTelegramNotify);
                  this.notifyUtils.sendMultiChannel(sendMessageRequest, "user_disabled", false, "wechat", "wechatBot", "dingding", "messagepush");
                  log.info("用户禁用成功：{}", embyUser.getEmbyUserName());
               }
            }
      }
   }

   @Scheduled(
      cron = "0 0 * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "用户到期删除任务",
      remark = "用户到期查询是否需要删除任务"
   )
   public void configureTasksRemoveUser() throws ApiException {
      log.info("用户过期删除定时任务：{}", DateUtil.formatDateTime(new Date()));
      String daysExpired = this.configCacheLoaderUtils.getConfigValue("days_expired");
         Long days = 0L;
         if (StringUtils.hasText(daysExpired)) {
            days = Long.parseLong(daysExpired);
         }

         List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
         if (serverConfigs.isEmpty()) {
            serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
         }

         boolean directTelegramNotify = this.telegramNotifyHelper.isDirectUserNotifyEnabled();

         for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
            for (EmbyUser embyUser : new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper())
               .eq(EmbyUser::getUserStatus, Integer.valueOf(1))
               .ne(EmbyUser::getIsAdmin, Integer.valueOf(1))
               .and(
                  wrapper -> wrapper.ne(EmbyUser::getHostLineType, Integer.valueOf(HostLineTypeEnum.WHITELIST.getCode()))
                        .or()
                        .isNull(EmbyUser::getHostLineType)
               )
               .eq(serverConfig.id() != null, EmbyUser::getEmbyInfoId, serverConfig.id())
               .isNull(serverConfig.id() == null, EmbyUser::getEmbyInfoId)
               .list()) {
               if (embyUser.getExpirationDate() != null
                  && new Date().after(embyUser.getExpirationDate())
                  && (days <= 0L || DateUtil.betweenDay(embyUser.getExpirationDate(), new Date(), true) > days)) {
                  log.info("用户过期删除：{}", embyUser.getEmbyUserName());
                  SendMessageRequest sendMessageRequest = new SendMessageRequest();
                  Map<String, String> extras = new HashMap<>();
                  extras.put("userName", embyUser.getEmbyUserName());
                  extras.put("reason", "过期");
                  sendMessageRequest.setParseMode("Markdown");
                  sendMessageRequest.setName("Emby用户过期删除提醒");
                  sendMessageRequest.setServerUrl(this.resolveServerUrl(serverConfig));
                  sendMessageRequest.setServerName(this.resolveServerName(serverConfig));
                  sendMessageRequest.setExtraVariables(extras);
                  this.telegramNotifyHelper.sendTelegram(embyUser, sendMessageRequest, "user_deleted", directTelegramNotify);
                  this.notifyUtils.sendMultiChannel(sendMessageRequest, "user_deleted", false, "wechat", "wechatBot", "dingding", "messagepush");
                  this.userOauthBindingMapper.delete(new LambdaQueryWrapper<UserOauthBinding>().eq(UserOauthBinding::getUserId, embyUser.getId()));

                  try {
                     this.roseUserBindingService.unbindExpiredUserIfBoundAsync(embyUser);
                  } catch (Exception var13) {
                     log.warn(
                        "Rose到期删除自动解绑触发失败，不影响用户删除主流程：userId={}, embyUserName={}, error={}", embyUser.getId(), embyUser.getEmbyUserName(), var13.getMessage()
                     );
                  }

                  this.embyUserService.deleteByUserId(Collections.singletonList(embyUser.getId()));
                  log.info("用户删除成功：{}", embyUser.getEmbyUserName());
               }
            }
      }
   }

   @Scheduled(
      cron = "0 30 4 * * ?",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "用户状态一致性检查",
      remark = "反向对账：禁用本地已禁用但Emby未禁用的用户"
   )
   public void configureTasksSyncUserStatusConsistency() {
      log.info("开始执行用户状态一致性检查任务...");
      int fixedCount = this.embyUserService.syncUserStatusConsistency();
      log.info("用户状态一致性检查任务完成，修复用户数量：{}", fixedCount);
   }

   private String getServerLabel(Long embyInfoId) {
      if (embyInfoId == null) {
         return "默认服务器";
      } else {
         EmbyInfo embyInfo = this.embyInfoService.getById(embyInfoId);
         return embyInfo != null && StringUtils.hasText(embyInfo.getServerName()) ? embyInfo.getServerName() : "服务器-" + embyInfoId;
      }
   }

   private String getServerLabel(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (serverConfig == null) {
         return "默认服务器";
      } else if (serverConfig.id() != null) {
         return this.getServerLabel(serverConfig.id());
      } else {
         return StringUtils.hasText(serverConfig.serverName()) ? serverConfig.serverName() : "默认服务器";
      }
   }

   private String getServerUrl(Long embyInfoId) {
      if (embyInfoId == null) {
         return null;
      } else {
         EmbyInfo embyInfo = this.embyInfoService.getById(embyInfoId);
         if (embyInfo == null) {
            return null;
         } else {
            String embyUrl = embyInfo.getEmbyUrl();
            if (!StringUtils.hasText(embyUrl) || !embyUrl.startsWith("http://") && !embyUrl.startsWith("https://")) {
               StringBuilder baseUrl = new StringBuilder();
               if (StringUtils.hasText(embyInfo.getEmbyAgreement())) {
                  baseUrl.append(embyInfo.getEmbyAgreement()).append("://");
               }

               if (StringUtils.hasText(embyInfo.getEmbyUrl())) {
                  baseUrl.append(embyInfo.getEmbyUrl());
               }

               if (StringUtils.hasText(embyInfo.getEmbyPort())) {
                  if (embyInfo.getEmbyUrl() != null && !embyInfo.getEmbyUrl().contains(":")) {
                     baseUrl.append(":");
                  }

                  baseUrl.append(embyInfo.getEmbyPort());
               }

               return baseUrl.length() > 0 ? baseUrl.toString() : null;
            } else {
               return embyUrl;
            }
         }
      }
   }

   private String resolveServerName(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (serverConfig == null) {
         return "未知服务器";
      } else if (StringUtils.hasText(serverConfig.serverName())) {
         return serverConfig.serverName();
      } else {
         if (serverConfig.id() != null) {
            EmbyInfo embyInfo = this.embyInfoService.getById(serverConfig.id());
            if (embyInfo != null && StringUtils.hasText(embyInfo.getServerName())) {
               return embyInfo.getServerName();
            }
         }

         return "未知服务器";
      }
   }

   private String resolveServerUrl(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (serverConfig == null) {
         return null;
      } else {
         String configUrl = serverConfig.url();
         if (!StringUtils.hasText(configUrl) || !configUrl.startsWith("http://") && !configUrl.startsWith("https://")) {
            return Objects.nonNull(serverConfig.id()) ? this.getServerUrl(serverConfig.id()) : configUrl;
         } else {
            return configUrl;
         }
      }
   }

}
