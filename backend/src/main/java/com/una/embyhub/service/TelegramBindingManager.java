package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.RedisLockUtils;
import com.una.embyhub.config.common.telegrambot.TelegramBotPermission;
import com.una.embyhub.config.common.telegrambot.TelegramBotAuthorizationService;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import lombok.Generated;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TelegramBindingManager {
   private static final String BIND_TELEGRAM_LOCK_PREFIX = "tg:bind:lock:telegram:";
   private static final String BIND_EMBY_USER_LOCK_PREFIX = "tg:bind:lock:user:";
   private static final long BIND_LOCK_TTL_SECONDS = 60L;
   private final EmbyUserMapper embyUserMapper;
   private final UserOauthBindingMapper userOauthBindingMapper;
   private final RedisLockUtils redisLockUtils;
   private final EmbyUserService embyUserService;
   private final TelegramBotAuthorizationService telegramBotAuthorizationService;

   public EmbyUser findUsableUser(Long userId) {
      EmbyUser user = this.findUser(userId);
      return user != null && !Integer.valueOf(1).equals(user.getUserStatus()) ? user : null;
   }

   public EmbyUser findUser(Long userId) {
      return userId == null
         ? null
         : new LambdaQueryChainWrapper<>(this.embyUserMapper).eq(EmbyUser::getId, userId).eq(BaseEntity::getDelFlag, Integer.valueOf(0)).one();
   }

   public UserOauthBinding findBindingByUserId(Long userId) {
      return userId == null
         ? null
         : new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .eq(UserOauthBinding::getUserId, userId)
            .eq(UserOauthBinding::getProvider, "telegram")
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .one();
   }

   public UserOauthBinding findBindingByTelegramId(Long telegramUserId) {
      return telegramUserId == null
         ? null
         : new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .eq(UserOauthBinding::getProvider, "telegram")
            .eq(UserOauthBinding::getProviderUserId, String.valueOf(telegramUserId))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .one();
   }

   public EmbyUser findBoundUser(Long telegramUserId, boolean requireUsable) {
      UserOauthBinding binding = this.findBindingByTelegramId(telegramUserId);
      if (binding == null) {
         return null;
      } else {
         return requireUsable ? this.findUsableUser(binding.getUserId()) : this.findUser(binding.getUserId());
      }
   }

   public EmbyUser findBoundUserByTelegramUsername(String telegramUsername, boolean requireUsable) {
      String normalized = this.normalizeTelegramUsername(telegramUsername);
      if (!StringUtils.hasText(normalized)) {
         return null;
      } else {
         List<UserOauthBinding> bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .eq(UserOauthBinding::getProvider, "telegram")
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .apply("LOWER(TRIM(LEADING '@' FROM provider_username)) = {0}", new Object[]{normalized})
            .last("limit 3")
            .list();
         Set<Long> userIds = new LinkedHashSet<>();

         for (UserOauthBinding binding : bindings) {
            if (binding.getUserId() != null) {
               userIds.add(binding.getUserId());
            }
         }

         if (userIds.size() != 1) {
            return null;
         } else {
            Long userId = userIds.iterator().next();
            return requireUsable ? this.findUsableUser(userId) : this.findUser(userId);
         }
      }
   }

   public void syncPanelAdminFromKkPermission(Long telegramUserId) {
      if (telegramUserId != null) {
         EmbyUser boundUser = this.findBoundUser(telegramUserId, false);
         if (boundUser != null) {
            this.syncPanelAdminFromKkPermission(telegramUserId, boundUser.getId());
         }
      }
   }

   private String normalizeTelegramUsername(String telegramUsername) {
      if (!StringUtils.hasText(telegramUsername)) {
         return null;
      } else {
         String normalized = telegramUsername.trim();

         while (normalized.startsWith("@")) {
            normalized = normalized.substring(1);
         }

         return normalized.trim().toLowerCase(Locale.ROOT);
      }
   }

   public Long findActiveAdminUserIdByTelegramId(Long telegramUserId) {
      return telegramUserId == null ? null : this.userOauthBindingMapper.selectActiveTelegramAdminUserId(String.valueOf(telegramUserId));
   }

   public void validateBind(EmbyUser embyUser, Long telegramUserId, boolean allowReplaceCurrentUserBinding) {
      if (embyUser != null && embyUser.getId() != null && telegramUserId != null) {
         String telegramId = String.valueOf(telegramUserId);
         UserOauthBinding existingBinding = this.findBindingByTelegramId(telegramUserId);
         if (existingBinding != null && !existingBinding.getUserId().equals(embyUser.getId())) {
            throw new BizException("该 Telegram 账户已绑定到其他 Emby 用户");
         } else {
            UserOauthBinding currentBinding = this.findBindingByUserId(embyUser.getId());
            if (currentBinding != null && !telegramId.equals(currentBinding.getProviderUserId()) && !allowReplaceCurrentUserBinding) {
               throw new BizException("该 Emby 用户已绑定其他 Telegram，请在网页个人页使用“更换绑定”");
            } else if (currentBinding != null && telegramId.equals(currentBinding.getProviderUserId())) {
               throw new BizException("该 Telegram 与 Emby 账号已经完成绑定");
            }
         }
      } else {
         throw new BizException("绑定参数无效");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public EmbyUser bind(EmbyUser embyUser, Long telegramUserId, String telegramUsername, String telegramAvatar, boolean allowReplaceCurrentUserBinding) {
      String telegramLockKey = "tg:bind:lock:telegram:" + telegramUserId;
      String userLockKey = "tg:bind:lock:user:" + embyUser.getId();
      String telegramLockToken = this.redisLockUtils.tryLock(telegramLockKey, 60L);
      if (!StringUtils.hasText(telegramLockToken)) {
         throw new BizException("当前 Telegram 账户正在绑定中，请稍后再试");
      } else {
         String userLockToken = null;

         EmbyUser var10;
         try {
            userLockToken = this.redisLockUtils.tryLock(userLockKey, 60L);
            if (!StringUtils.hasText(userLockToken)) {
               throw new BizException("该 Emby 用户正在绑定 Telegram，请稍后再试");
            }

            this.upsertBinding(embyUser, telegramUserId, telegramUsername, telegramAvatar, allowReplaceCurrentUserBinding);
            this.syncPanelAdminFromKkPermission(telegramUserId, embyUser.getId());
            var10 = embyUser;
         } finally {
            this.redisLockUtils.unlock(userLockKey, userLockToken);
            this.redisLockUtils.unlock(telegramLockKey, telegramLockToken);
         }

         return var10;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public EmbyUser rebind(EmbyUser embyUser, Long expectedOldTelegramUserId, Long newTelegramUserId, String newTelegramUsername, String newTelegramAvatar) {
      if (embyUser == null || embyUser.getId() == null || expectedOldTelegramUserId == null || newTelegramUserId == null) {
         throw new BizException("换绑参数无效");
      } else if (expectedOldTelegramUserId.equals(newTelegramUserId)) {
         throw new BizException("新 Telegram 账号不能与当前绑定相同");
      } else {
         String telegramLockKey = "tg:bind:lock:telegram:" + newTelegramUserId;
         String userLockKey = "tg:bind:lock:user:" + embyUser.getId();
         String telegramLockToken = this.redisLockUtils.tryLock(telegramLockKey, 60L);
         if (!StringUtils.hasText(telegramLockToken)) {
            throw new BizException("新 Telegram 账户正在绑定中，请稍后再试");
         } else {
            String userLockToken = null;

            EmbyUser var10;
            try {
               userLockToken = this.redisLockUtils.tryLock(userLockKey, 60L);
               if (!StringUtils.hasText(userLockToken)) {
                  throw new BizException("该 Emby 用户正在更换 Telegram，请稍后再试");
               }

               this.replaceBinding(embyUser, expectedOldTelegramUserId, newTelegramUserId, newTelegramUsername, newTelegramAvatar);
               this.syncPanelAdminFromKkPermission(newTelegramUserId, embyUser.getId());
               var10 = embyUser;
            } finally {
               this.redisLockUtils.unlock(userLockKey, userLockToken);
               this.redisLockUtils.unlock(telegramLockKey, telegramLockToken);
            }

            return var10;
         }
      }
   }

   private void replaceBinding(EmbyUser embyUser, Long expectedOldTelegramUserId, Long newTelegramUserId, String newTelegramUsername, String newTelegramAvatar) {
      try {
         UserOauthBinding currentBinding = this.findBindingByUserId(embyUser.getId());
         if (currentBinding != null && String.valueOf(expectedOldTelegramUserId).equals(currentBinding.getProviderUserId())) {
            UserOauthBinding targetBinding = this.findBindingByTelegramId(newTelegramUserId);
            if (targetBinding != null && !embyUser.getId().equals(targetBinding.getUserId())) {
               throw new BizException("新 Telegram 账户已绑定到其他 Emby 用户");
            } else if (targetBinding != null) {
               throw new BizException("新 Telegram 账户已经绑定当前 Emby 账号");
            } else {
               currentBinding.setProviderUserId(String.valueOf(newTelegramUserId));
               currentBinding.setProviderUsername(newTelegramUsername);
               currentBinding.setProviderAvatar(newTelegramAvatar);
               this.fillAudit(currentBinding, embyUser, false);
               if (this.userOauthBindingMapper.replaceTelegramBinding(currentBinding, String.valueOf(expectedOldTelegramUserId)) != 1) {
                  throw new BizException("Telegram 绑定关系已变化，请刷新后重新发起换绑");
               }
            }
         } else {
            throw new BizException("Telegram 绑定关系已变化，请刷新后重新发起换绑");
         }
      } catch (DuplicateKeyException var8) {
         throw new BizException("新 Telegram 账户已完成其他绑定，请刷新后重试");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public EmbyUser refreshBindingProfile(EmbyUser embyUser, Long telegramUserId, String telegramUsername, String telegramAvatar) {
      if (embyUser != null && embyUser.getId() != null && telegramUserId != null) {
         String telegramLockKey = "tg:bind:lock:telegram:" + telegramUserId;
         String userLockKey = "tg:bind:lock:user:" + embyUser.getId();
         String telegramLockToken = this.redisLockUtils.tryLock(telegramLockKey, 60L);
         if (!StringUtils.hasText(telegramLockToken)) {
            throw new BizException("Telegram 账户资料正在同步，请稍后再试");
         } else {
            String userLockToken = null;

            EmbyUser var10;
            try {
               userLockToken = this.redisLockUtils.tryLock(userLockKey, 60L);
               if (!StringUtils.hasText(userLockToken)) {
                  throw new BizException("该 Emby 用户的 Telegram 资料正在同步，请稍后再试");
               }

               UserOauthBinding currentBinding = this.findBindingByUserId(embyUser.getId());
               if (currentBinding == null || !String.valueOf(telegramUserId).equals(currentBinding.getProviderUserId())) {
                  throw new BizException("Telegram 绑定关系已变化，请刷新后重试");
               }

               currentBinding.setProviderUsername(telegramUsername);
               currentBinding.setProviderAvatar(telegramAvatar);
               this.fillAudit(currentBinding, embyUser, false);
               if (this.userOauthBindingMapper.replaceTelegramBinding(currentBinding, String.valueOf(telegramUserId)) != 1) {
                  throw new BizException("Telegram 绑定关系已变化，请刷新后重试");
               }

               var10 = embyUser;
            } finally {
               this.redisLockUtils.unlock(userLockKey, userLockToken);
               this.redisLockUtils.unlock(telegramLockKey, telegramLockToken);
            }

            return var10;
         }
      } else {
         throw new BizException("Telegram 绑定参数无效");
      }
   }

   private void upsertBinding(EmbyUser embyUser, Long telegramUserId, String telegramUsername, String telegramAvatar, boolean allowReplaceCurrentUserBinding) {
      try {
         String telegramId = String.valueOf(telegramUserId);
         UserOauthBinding existingBinding = this.findBindingByTelegramId(telegramUserId);
         if (existingBinding != null && !existingBinding.getUserId().equals(embyUser.getId())) {
            throw new BizException("该 Telegram 账户已绑定到其他 Emby 用户");
         } else {
            UserOauthBinding currentBinding = this.findBindingByUserId(embyUser.getId());
            if (currentBinding != null) {
               if (!telegramId.equals(currentBinding.getProviderUserId()) && !allowReplaceCurrentUserBinding) {
                  throw new BizException("该 Emby 用户已绑定其他 Telegram，请在网页个人页使用“更换绑定”");
               } else {
                  currentBinding.setProviderUserId(telegramId);
                  currentBinding.setProviderUsername(telegramUsername);
                  currentBinding.setProviderAvatar(telegramAvatar);
                  this.fillAudit(currentBinding, embyUser, false);
                  this.userOauthBindingMapper.updateById(currentBinding);
               }
            } else {
               UserOauthBinding newBinding = new UserOauthBinding();
               newBinding.setUserId(embyUser.getId());
               newBinding.setProvider("telegram");
               newBinding.setProviderUserId(telegramId);
               newBinding.setProviderUsername(telegramUsername);
               newBinding.setProviderAvatar(telegramAvatar);
               this.fillAudit(newBinding, embyUser, true);
               this.userOauthBindingMapper.insert(newBinding);
            }
         }
      } catch (DuplicateKeyException var10) {
         throw new BizException("该 Telegram 或 Emby 用户已完成绑定，请刷新后重试");
      }
   }

   private void syncPanelAdminFromKkPermission(Long telegramUserId, Long userId) {
      if (telegramUserId != null
         && userId != null
         && this.telegramBotAuthorizationService.hasPermission(telegramUserId, TelegramBotPermission.USER_VIEW)) {
         EmbyUser target = this.findUser(userId);
         if (target != null && !Integer.valueOf(1).equals(target.getIsPrimaryAdmin())) {
            this.embyUserService.updateUserAdminByBot(userId, 1, true);
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public EmbyUser unbindByTelegramId(Long telegramUserId) {
      if (telegramUserId == null) {
         throw new BizException("Telegram 用户无效");
      } else {
         List<UserOauthBinding> bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .eq(UserOauthBinding::getProvider, "telegram")
            .eq(UserOauthBinding::getProviderUserId, String.valueOf(telegramUserId))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .list();
         return this.removeBindings(bindings);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public EmbyUser unbindByUserId(Long userId) {
      List<UserOauthBinding> bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
         .eq(UserOauthBinding::getUserId, userId)
         .eq(UserOauthBinding::getProvider, "telegram")
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .list();
      return this.removeBindings(bindings);
   }

   private EmbyUser removeBindings(List<UserOauthBinding> bindings) {
      if (bindings != null && !bindings.isEmpty()) {
         EmbyUser boundUser = this.findUser(bindings.get(0).getUserId());
         bindings.forEach(binding -> this.userOauthBindingMapper.deleteById(binding.getId()));
         return boundUser;
      } else {
         return null;
      }
   }

   private void fillAudit(UserOauthBinding binding, EmbyUser operator, boolean insert) {
      Date now = new Date();
      if (insert) {
         binding.setCreateDatetime(now);
         binding.setCreateUserId(operator.getId());
         binding.setCreateUserName(operator.getEmbyUserName());
      }

      binding.setUpdateDatetime(now);
      binding.setUpdateUserId(operator.getId());
      binding.setUpdateUserName(operator.getEmbyUserName());
   }

   @Generated
   public TelegramBindingManager(
      final EmbyUserMapper embyUserMapper,
      final UserOauthBindingMapper userOauthBindingMapper,
      final RedisLockUtils redisLockUtils,
      final EmbyUserService embyUserService,
      final TelegramBotAuthorizationService telegramBotAuthorizationService
   ) {
      this.embyUserMapper = embyUserMapper;
      this.userOauthBindingMapper = userOauthBindingMapper;
      this.redisLockUtils = redisLockUtils;
      this.embyUserService = embyUserService;
      this.telegramBotAuthorizationService = telegramBotAuthorizationService;
   }
}
