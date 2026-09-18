package com.una.embyhub.job;

import java.util.Arrays;

public enum PlaybackRankingPosterStyle {
   CLASSIC_PORTRAIT("classic_portrait"),
   POSTER_RAIL("poster_rail"),
   DUAL_SPOTLIGHT("dual_spotlight"),
   HERO_SPOTLIGHT("hero_spotlight"),
   MIDNIGHT_TICKET("midnight_ticket"),
   STREAMING_MAGAZINE("streaming_magazine");

   public static final PlaybackRankingPosterStyle DEFAULT = STREAMING_MAGAZINE;
   private final String configValue;

   private PlaybackRankingPosterStyle(String configValue) {
      this.configValue = configValue;
   }

   public String configValue() {
      return this.configValue;
   }

   public static PlaybackRankingPosterStyle fromConfigValue(String value) {
      if (value != null && !value.isBlank()) {
         String normalized = value.trim();
         return Arrays.stream(values()).filter(style -> style.configValue.equalsIgnoreCase(normalized)).findFirst().orElse(DEFAULT);
      } else {
         return DEFAULT;
      }
   }
}
