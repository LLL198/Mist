package com.una.embyhub.model.dto.logintransition;

import java.util.List;

public final class LoginTransitionDtos {
   private LoginTransitionDtos() {
   }

   public static record CurrentResponse(boolean enabled, Integer grantedDays, LoginTransitionDtos.Rule rule) {
      public static LoginTransitionDtos.CurrentResponse disabled(Integer grantedDays) {
         return new LoginTransitionDtos.CurrentResponse(false, grantedDays, null);
      }
   }

   public static record Preset(String key, String title, String description) {
   }

   public static record Rule(String id, Integer minDays, Integer maxDays, String title, String subtitle, String preset, Integer durationMs, Boolean enabled) {
   }

   public static record SettingsResponse(boolean enabled, List<LoginTransitionDtos.Rule> rules, List<LoginTransitionDtos.Preset> presets) {
   }

   public static record UpdateRequest(Boolean enabled, List<LoginTransitionDtos.Rule> rules) {
   }
}
