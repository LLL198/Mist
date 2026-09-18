package com.una.embyhub.job;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.service.SystemConfigService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserExpirationTelegramNotifyHelper {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(UserExpirationTelegramNotifyHelper.class);
   private final NotifyUtils notifyUtils;
   private final SystemConfigService systemConfigService;
   private final UserOauthBindingMapper userOauthBindingMapper;

   public UserExpirationTelegramNotifyHelper(NotifyUtils notifyUtils, SystemConfigService systemConfigService, UserOauthBindingMapper userOauthBindingMapper) {
      this.notifyUtils = notifyUtils;
      this.systemConfigService = systemConfigService;
      this.userOauthBindingMapper = userOauthBindingMapper;
   }

   public void sendTelegram(EmbyUser embyUser, SendMessageRequest request, String templateCode) {
      this.sendTelegram(embyUser, request, templateCode, this.isDirectUserNotifyEnabled());
   }

   public void sendTelegram(EmbyUser embyUser, SendMessageRequest request, String templateCode, boolean directUserNotifyEnabled) {
      if (directUserNotifyEnabled) {
         String chatId = this.getBoundTelegramChatId(embyUser);
         if (StringUtils.hasText(chatId) && this.notifyUtils.sendTelegramToChat(request, templateCode, chatId)) {
            return;
         }

         log.info(
            "用户到期 Telegram 直发跳过并回退管理员通知：userId={}, embyUserName={}, bound={}",
            embyUser == null ? null : embyUser.getId(),
            embyUser == null ? null : embyUser.getEmbyUserName(),
            StringUtils.hasText(chatId)
         );
      }

      this.notifyUtils.sendTelegram(request, templateCode, false);
   }

   public boolean isDirectUserNotifyEnabled() {
      SystemConfig config = new LambdaQueryChainWrapper<>(this.systemConfigService.getBaseMapper())
         .eq(SystemConfig::getConfigKey, "telegram_user_expiration_notify_enabled")
         .last("limit 1")
         .one();
      if (config == null) {
         return true;
      } else if (!Integer.valueOf(1).equals(config.getIsEnabled())) {
         return false;
      } else {
         String value = config.getConfigValue();
         if (!StringUtils.hasText(value)) {
            return true;
         } else {
            String normalized = value.trim().toLowerCase();
            return !"false".equals(normalized) && !"0".equals(normalized) && !"no".equals(normalized) && !"off".equals(normalized);
         }
      }
   }

   private String getBoundTelegramChatId(EmbyUser embyUser) {
      if (embyUser != null && embyUser.getId() != null) {
         UserOauthBinding binding = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .eq(UserOauthBinding::getUserId, embyUser.getId())
            .eq(UserOauthBinding::getProvider, "telegram")
            .last("limit 1")
            .one();
         return binding == null ? null : binding.getProviderUserId();
      } else {
         return null;
      }
   }
}
