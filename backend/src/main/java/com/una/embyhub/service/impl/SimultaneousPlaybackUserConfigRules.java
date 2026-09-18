package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.Collection;
import java.util.function.Predicate;
import org.springframework.util.StringUtils;

final class SimultaneousPlaybackUserConfigRules {
   private SimultaneousPlaybackUserConfigRules() {
   }

   static SimultaneousPlaybackUserConfigRules.CleanupResult removeUsers(String configValue, Collection<SimultaneousPlaybackUserConfigRules.UserIdentity> users) {
      return users != null && !users.isEmpty()
         ? retainUsers(configValue, rule -> users.stream().noneMatch(user -> matches(rule, user)))
         : SimultaneousPlaybackUserConfigRules.CleanupResult.unchanged(configValue);
   }

   static SimultaneousPlaybackUserConfigRules.CleanupResult retainUsers(
      String configValue, Predicate<SimultaneousPlaybackUserConfigRules.UserRule> retainedUser
   ) {
      SimultaneousPlaybackUserConfigRules.ParsedConfig parsed = parse(configValue);
      if (parsed != null && !parsed.userThresholds().isEmpty()) {
         JSONArray retainedRules = new JSONArray(parsed.userThresholds().size());
         int removedCount = 0;

         for (Object item : parsed.userThresholds()) {
            if (item instanceof JSONObject) {
               JSONObject ruleObject = (JSONObject)item;
               SimultaneousPlaybackUserConfigRules.UserRule rule = toUserRule(ruleObject);
               if (rule != null && !retainedUser.test(rule)) {
                  removedCount++;
               } else {
                  retainedRules.add(item);
               }
            } else {
               retainedRules.add(item);
            }
         }

         if (removedCount == 0) {
            return SimultaneousPlaybackUserConfigRules.CleanupResult.unchanged(configValue);
         } else {
            parsed.config().put("userThresholds", retainedRules);
            return new SimultaneousPlaybackUserConfigRules.CleanupResult(parsed.config().toJSONString(), removedCount);
         }
      } else {
         return SimultaneousPlaybackUserConfigRules.CleanupResult.unchanged(configValue);
      }
   }

   private static SimultaneousPlaybackUserConfigRules.ParsedConfig parse(String configValue) {
      if (StringUtils.hasText(configValue) && configValue.trim().startsWith("{")) {
         try {
            JSONObject config = JSON.parseObject(configValue.trim());
            JSONArray userThresholds = config == null ? null : config.getJSONArray("userThresholds");
            return config != null && userThresholds != null ? new SimultaneousPlaybackUserConfigRules.ParsedConfig(config, userThresholds) : null;
         } catch (Exception var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static SimultaneousPlaybackUserConfigRules.UserRule toUserRule(JSONObject rule) {
      Long serverId = positiveLong(rule.get("serverId"));
      String userId = normalize(rule.get("userId"));
      String userName = normalize(rule.get("userName"));
      return serverId != null && (StringUtils.hasText(userId) || StringUtils.hasText(userName))
         ? new SimultaneousPlaybackUserConfigRules.UserRule(serverId, userId, userName)
         : null;
   }

   private static boolean matches(SimultaneousPlaybackUserConfigRules.UserRule rule, SimultaneousPlaybackUserConfigRules.UserIdentity user) {
      if (rule == null || user == null || !rule.serverId().equals(user.serverId())) {
         return false;
      } else {
         return StringUtils.hasText(rule.userId())
            ? rule.userId().equals(normalize(user.userId()))
            : StringUtils.hasText(rule.userName()) && rule.userName().equalsIgnoreCase(normalize(user.userName()));
      }
   }

   private static Long positiveLong(Object value) {
      try {
         long parsed = Long.parseLong(normalize(value));
         return parsed > 0L ? parsed : null;
      } catch (NumberFormatException var3) {
         return null;
      }
   }

   private static String normalize(Object value) {
      return value == null ? "" : String.valueOf(value).trim();
   }

   static record CleanupResult(String configValue, int removedCount) {
      static SimultaneousPlaybackUserConfigRules.CleanupResult unchanged(String configValue) {
         return new SimultaneousPlaybackUserConfigRules.CleanupResult(configValue, 0);
      }
   }

   private static record ParsedConfig(JSONObject config, JSONArray userThresholds) {
   }

   static record UserIdentity(Long serverId, String userId, String userName) {
   }

   static record UserRule(Long serverId, String userId, String userName) {
   }
}
