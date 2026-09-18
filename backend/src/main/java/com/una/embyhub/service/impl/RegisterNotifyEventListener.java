package com.una.embyhub.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.NotifyMaskUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.event.CardRegisterSuccessNotifyEvent;
import com.una.embyhub.event.CardRenewSuccessNotifyEvent;
import com.una.embyhub.event.InvitationRegisterSuccessNotifyEvent;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.service.EmbyInfoService;
import java.util.Date;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.StringUtils;

@Component
public class RegisterNotifyEventListener {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(RegisterNotifyEventListener.class);
   private static final int REGISTER_CODE_VISIBLE_PREFIX_LENGTH = 11;
   private static final int REGISTER_CODE_SHORT_VISIBLE_PREFIX_LENGTH = 4;
   private static final String REGISTER_CODE_MASK_BLOCK = "▦";
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private EmbyInfoService embyInfoService;

   @Async
   @TransactionalEventListener(
      phase = TransactionPhase.AFTER_COMMIT,
      fallbackExecution = true
   )
   public void onCardRegisterSuccess(CardRegisterSuccessNotifyEvent event) {
      if (event != null && this.isRegisterNotifyTelegramGroupEnabled("card_register_enabled")) {
         try {
            SendMessageRequest request = this.buildRegisterSuccessMessage(event.userName(), event.embyInfoId(), this.formatRegisterDays(event.validityDays()));
            request.getExtraVariables().put("registerCode", this.maskRegisterCode(event.cardPassword()));
            this.notifyUtils.sendMultiChannel(request, "card_register_success", true, "telegram");
         } catch (Exception var3) {
            log.error("卡密注册成功 Telegram 群聊通知异步发送失败", (Throwable)var3);
         }
      }
   }

   @Async
   @TransactionalEventListener(
      phase = TransactionPhase.AFTER_COMMIT,
      fallbackExecution = true
   )
   public void onCardRenewSuccess(CardRenewSuccessNotifyEvent event) {
      if (event != null && this.isCardRenewNotifyTelegramGroupEnabled()) {
         try {
            SendMessageRequest request = this.buildCardRenewSuccessMessage(
               event.userName(), event.embyInfoId(), this.formatRegisterDays(event.validityDays()), event.expirationDate()
            );
            request.getExtraVariables().put("registerCode", this.maskRegisterCode(event.cardPassword()));
            this.notifyUtils.sendMultiChannel(request, "card_renew_success", true, "telegram");
         } catch (Exception var3) {
            log.error("卡密续费成功 Telegram 群聊通知异步发送失败", (Throwable)var3);
         }
      }
   }

   @Async
   @TransactionalEventListener(
      phase = TransactionPhase.AFTER_COMMIT,
      fallbackExecution = true
   )
   public void onInvitationRegisterSuccess(InvitationRegisterSuccessNotifyEvent event) {
      if (event != null && this.isRegisterNotifyTelegramGroupEnabled("invitation_register_enabled")) {
         try {
            SendMessageRequest request = this.buildRegisterSuccessMessage(event.userName(), event.embyInfoId(), this.formatRegisterDays(event.validityDays()));
            request.getExtraVariables().put("invitationCode", this.maskRegisterCode(event.invitationCode()));
            this.notifyUtils.sendMultiChannel(request, "invitation_register_success", true, "telegram");
         } catch (Exception var3) {
            log.error("邀请码注册成功 Telegram 群聊通知异步发送失败", (Throwable)var3);
         }
      }
   }

   private SendMessageRequest buildRegisterSuccessMessage(String userName, Long embyInfoId, String registerDays) {
      SendMessageRequest request = new SendMessageRequest();
      String maskedUserName = NotifyMaskUtils.maskUserName(userName);
      request.setName(maskedUserName);
      request.setParseMode("plain");
      request.setServerName(this.resolveServerName(embyInfoId));
      request.getExtraVariables().put("userName", maskedUserName);
      request.getExtraVariables().put("registerDays", registerDays);
      return request;
   }

   private SendMessageRequest buildCardRenewSuccessMessage(String userName, Long embyInfoId, String renewDays, Date expirationDate) {
      SendMessageRequest request = new SendMessageRequest();
      String maskedUserName = NotifyMaskUtils.maskUserName(userName);
      request.setName(maskedUserName);
      request.setParseMode("plain");
      request.setServerName(this.resolveServerName(embyInfoId));
      request.getExtraVariables().put("userName", maskedUserName);
      request.getExtraVariables().put("renewDays", renewDays);
      request.getExtraVariables().put("expirationDate", this.formatExpirationDate(expirationDate));
      return request;
   }

   private boolean isRegisterNotifyTelegramGroupEnabled(String configKey) {
      return this.isTelegramGroupNotifyEnabled(configKey, "notifyTelegramGroup");
   }

   private boolean isCardRenewNotifyTelegramGroupEnabled() {
      return this.isTelegramGroupNotifyEnabled("card_register_enabled", "notifyRenewTelegramGroup");
   }

   private boolean isTelegramGroupNotifyEnabled(String configKey, String fieldName) {
      String value = this.configCacheLoaderUtils.getConfigValue(configKey);
      if (!StringUtils.hasText(value)) {
         return false;
      } else {
         String text = value.trim();
         if (!text.startsWith("{")) {
            return false;
         } else {
            try {
               JSONObject config = JSONObject.parseObject(text);
               return config.getBooleanValue(fieldName);
            } catch (Exception var6) {
               log.warn("注册群聊通知配置 JSON 无效: key={}, value={}", configKey, value);
               return false;
            }
         }
      }
   }

   private String resolveServerName(Long embyInfoId) {
      if (embyInfoId == null) {
         return "未知服务器";
      } else {
         EmbyInfo embyInfo = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper()).eq(EmbyInfo::getId, embyInfoId).one();
         return embyInfo != null && StringUtils.hasText(embyInfo.getServerName()) ? embyInfo.getServerName() : "未知服务器";
      }
   }

   private String formatRegisterDays(Integer days) {
      return days != null && days > 0 ? days + "天" : "永久";
   }

   private String formatExpirationDate(Date expirationDate) {
      return expirationDate == null ? "永久" : DateUtil.formatDateTime(expirationDate);
   }

   private String maskRegisterCode(String code) {
      if (!StringUtils.hasText(code)) {
         return "▦".repeat(4);
      } else {
         String text = code.trim();
         int length = text.length();
         if (length <= 4) {
            return text.substring(0, 1) + "▦".repeat(Math.max(1, length - 1));
         } else {
            int visibleLength = Math.min(length - 1, length <= 11 ? 4 : 11);
            return text.substring(0, visibleLength) + "▦".repeat(length - visibleLength);
         }
      }
   }
}
