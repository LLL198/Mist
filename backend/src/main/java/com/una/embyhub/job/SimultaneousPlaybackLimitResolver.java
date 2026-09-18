package com.una.embyhub.job;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.StringUtils;

final class SimultaneousPlaybackLimitResolver {
   private static final int FALLBACK_THRESHOLD = 2;

   private SimultaneousPlaybackLimitResolver() {
   }

   static int resolve(String configValue, Long serverId, String embyUserId, String embyUserName) {
      if (!StringUtils.hasText(configValue)) {
         return 0;
      } else {
         String value = configValue.trim();
         if (!value.startsWith("{")) {
            return nonNegativeOrDefault(value, 2);
         } else {
            try {
               JSONObject config = JSON.parseObject(value);
               int defaultThreshold = nonNegativeOrDefault(config.get("defaultThreshold"), 2);
               int serverThreshold = resolveServerThreshold(config, serverId, defaultThreshold);
               return resolveUserThreshold(config, serverId, embyUserId, embyUserName, serverThreshold);
            } catch (Exception var8) {
               return 2;
            }
         }
      }
   }

   static boolean isLimitViolation(int sessionCount, int threshold, boolean unlimitedUser) {
      return !unlimitedUser && threshold > 0 && sessionCount >= threshold;
   }

   private static int resolveServerThreshold(JSONObject config, Long serverId, int defaultThreshold) {
      if (serverId == null) {
         return defaultThreshold;
      } else {
         JSONObject serverThresholds = config.getJSONObject("serverThresholds");
         return serverThresholds == null ? defaultThreshold : nonNegativeOrDefault(serverThresholds.get(String.valueOf(serverId)), defaultThreshold);
      }
   }

   private static int resolveUserThreshold(JSONObject config, Long serverId, String embyUserId, String embyUserName, int serverThreshold) {
      JSONArray userThresholds = config.getJSONArray("userThresholds");
      if (userThresholds != null && !userThresholds.isEmpty()) {
         for (Object item : userThresholds) {
            if (item instanceof JSONObject) {
               JSONObject rule = (JSONObject)item;
               if (sameServer(rule.get("serverId"), serverId)) {
                  String configuredUserId = normalize(rule.getString("userId"));
                  boolean matches = StringUtils.hasText(configuredUserId)
                     ? configuredUserId.equals(normalize(embyUserId))
                     : sameUserName(rule.getString("userName"), embyUserName);
                  if (matches) {
                     return nonNegativeOrDefault(rule.get("threshold"), serverThreshold);
                  }
               }
            }
         }

         return serverThreshold;
      } else {
         return serverThreshold;
      }
   }

   private static boolean sameServer(Object configuredServerId, Long serverId) {
      return serverId != null && String.valueOf(serverId).equals(normalize(configuredServerId));
   }

   private static boolean sameUserName(String configuredUserName, String embyUserName) {
      String configured = normalize(configuredUserName);
      String current = normalize(embyUserName);
      return StringUtils.hasText(configured) && configured.equalsIgnoreCase(current);
   }

   private static String normalize(Object value) {
      return value == null ? "" : String.valueOf(value).trim();
   }

   private static int nonNegativeOrDefault(Object value, int fallback) {
      if (value == null) {
         return fallback;
      } else {
         try {
            int parsed = Integer.parseInt(String.valueOf(value).trim());
            return parsed >= 0 ? parsed : fallback;
         } catch (NumberFormatException var3) {
            return fallback;
         }
      }
   }
}
