package com.una.embyhub.config.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.util.StringUtils;

public final class PlaybackReportingLocationUtils {
   private static final Set<String> EMPTY_TOKENS = Set.of("0", "null", "unknown");

   private PlaybackReportingLocationUtils() {
   }

   public static PlaybackReportingLocationUtils.LocationParts parse(String location) {
      if (!StringUtils.hasText(location)) {
         return new PlaybackReportingLocationUtils.LocationParts(null, null, "未知", null);
      } else {
         String normalized = location.trim();
         if (normalized.contains("内网")) {
            return new PlaybackReportingLocationUtils.LocationParts("内网", "内网", "内网", null);
         } else {
            List<String> parts = splitLocation(normalized);
            if (parts.isEmpty()) {
               return new PlaybackReportingLocationUtils.LocationParts(null, null, "未知", null);
            } else if (parts.size() == 1) {
               return new PlaybackReportingLocationUtils.LocationParts(null, null, parts.get(0), null);
            } else {
               String country = parts.get(0);
               String isp = null;
               int addressEnd = parts.size();
               String last = parts.get(parts.size() - 1);
               if (parts.size() >= 5 || parts.size() >= 3 && looksLikeIsp(last)) {
                  isp = last;
                  addressEnd = parts.size() - 1;
               }

               List<String> addressParts = parts.subList(1, addressEnd);
               String region = null;
               String city = null;
               if (addressParts.size() == 1) {
                  region = addressParts.get(0);
               } else if (addressParts.size() >= 2) {
                  region = addressParts.get(addressParts.size() - 2);
                  city = addressParts.get(addressParts.size() - 1);
               }

               return new PlaybackReportingLocationUtils.LocationParts(country, region, city, isp);
            }
         }
      }
   }

   private static List<String> splitLocation(String location) {
      String[] rawParts = location.replace('|', ' ').replace('/', ' ').split("\\s+");
      LinkedHashSet<String> deduplicated = new LinkedHashSet<>();

      for (String rawPart : rawParts) {
         String part = rawPart == null ? "" : rawPart.trim();
         if (StringUtils.hasText(part) && !EMPTY_TOKENS.contains(part.toLowerCase(Locale.ROOT))) {
            deduplicated.add(part);
         }
      }

      return new ArrayList<>(deduplicated);
   }

   private static boolean looksLikeIsp(String value) {
      String text = value == null ? "" : value.toLowerCase(Locale.ROOT);
      return text.contains("电信")
         || text.contains("联通")
         || text.contains("移动")
         || text.contains("铁通")
         || text.contains("广电")
         || text.contains("教育网")
         || text.contains("长城")
         || text.contains("鹏博士")
         || text.contains("alibaba")
         || text.contains("aliyun")
         || text.contains("tencent")
         || text.contains("cloudflare")
         || text.contains("amazon")
         || text.contains("aws")
         || text.contains("google")
         || text.contains("microsoft")
         || text.contains("azure");
   }

   public static record LocationParts(String country, String region, String city, String isp) {
      public String displayName() {
         if (StringUtils.hasText(this.city)) {
            return this.city;
         } else if (StringUtils.hasText(this.region)) {
            return this.region;
         } else {
            return StringUtils.hasText(this.country) ? this.country : "未知";
         }
      }
   }
}
