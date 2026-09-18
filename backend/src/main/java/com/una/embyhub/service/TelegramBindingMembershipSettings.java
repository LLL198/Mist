package com.una.embyhub.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.StringUtils;

record TelegramBindingMembershipSettings(boolean enabled, boolean pointsGroupRequired, boolean libraryNotifyGroupRequired) {
   static TelegramBindingMembershipSettings fromConfigValue(String configValue) {
      if (configValue == null) {
         return disabled();
      } else if (StringUtils.hasText(configValue) && configValue.trim().startsWith("{")) {
         try {
            JSONObject json = JSONObject.parseObject(configValue);
            return json == null
               ? defaults()
               : new TelegramBindingMembershipSettings(
                  true, booleanField(json, "pointsGroupRequired", true), booleanField(json, "libraryNotifyGroupRequired", false)
               );
         } catch (Exception var2) {
            return defaults();
         }
      } else {
         return defaults();
      }
   }

   boolean requiresAnyGroup() {
      return this.enabled && (this.pointsGroupRequired || this.libraryNotifyGroupRequired);
   }

   private static TelegramBindingMembershipSettings defaults() {
      return new TelegramBindingMembershipSettings(true, true, false);
   }

   private static TelegramBindingMembershipSettings disabled() {
      return new TelegramBindingMembershipSettings(false, false, false);
   }

   private static boolean booleanField(JSONObject json, String field, boolean fallback) {
      Boolean value = json.getBoolean(field);
      return value == null ? fallback : value;
   }
}
