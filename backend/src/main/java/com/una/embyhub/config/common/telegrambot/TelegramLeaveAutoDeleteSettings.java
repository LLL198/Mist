package com.una.embyhub.config.common.telegrambot;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.StringUtils;

record TelegramLeaveAutoDeleteSettings(boolean enabled, boolean pointsGroupEnabled, boolean libraryNotifyGroupEnabled, boolean authorizedKickDeleteEnabled) {
   static TelegramLeaveAutoDeleteSettings fromConfigValue(String configValue) {
      if (configValue == null) {
         return disabled();
      } else if (StringUtils.hasText(configValue) && configValue.trim().startsWith("{")) {
         try {
            JSONObject json = JSONObject.parseObject(configValue);
            return json == null
               ? disabled()
               : new TelegramLeaveAutoDeleteSettings(
                  true,
                  booleanField(json, "pointsGroupEnabled", true),
                  booleanField(json, "libraryNotifyGroupEnabled", false),
                  booleanField(json, "authorizedKickDeleteEnabled", false)
               );
         } catch (Exception var2) {
            return disabled();
         }
      } else {
         return defaults();
      }
   }

   boolean monitors(Long chatId, String pointsGroupChatId, String legacyPointsGroupChatId, String libraryNotifyChatId) {
      return this.enabled && chatId != null
         ? this.pointsGroupEnabled && (matches(chatId, pointsGroupChatId) || matches(chatId, legacyPointsGroupChatId))
            || this.libraryNotifyGroupEnabled && matches(chatId, libraryNotifyChatId)
         : false;
   }

   private static TelegramLeaveAutoDeleteSettings defaults() {
      return new TelegramLeaveAutoDeleteSettings(true, true, false, false);
   }

   private static TelegramLeaveAutoDeleteSettings disabled() {
      return new TelegramLeaveAutoDeleteSettings(false, false, false, false);
   }

   private static boolean booleanField(JSONObject json, String field, boolean fallback) {
      Boolean value = json.getBoolean(field);
      return value == null ? fallback : value;
   }

   private static boolean matches(Long chatId, String configuredChatId) {
      if (!StringUtils.hasText(configuredChatId)) {
         return false;
      } else {
         try {
            return chatId.equals(Long.parseLong(configuredChatId.trim()));
         } catch (NumberFormatException var3) {
            return false;
         }
      }
   }
}
