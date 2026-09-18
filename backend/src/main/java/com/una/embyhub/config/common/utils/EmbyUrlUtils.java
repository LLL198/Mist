package com.una.embyhub.config.common.utils;

import java.util.Locale;
import org.springframework.util.StringUtils;

public final class EmbyUrlUtils {
   private static final String EMBY_PREFIX = "/emby";

   private EmbyUrlUtils() {
   }

   public static String buildApiUrl(String baseUrl, String path) {
      if (!StringUtils.hasText(baseUrl)) {
         return normalizePath(path);
      } else {
         String base = trimTrailingSlashes(baseUrl);
         String normalizedPath = normalizePath(path);
         String lowerBase = base.toLowerCase(Locale.ROOT);
         String lowerPath = normalizedPath.toLowerCase(Locale.ROOT);
         if (lowerBase.endsWith("/emby") && lowerPath.startsWith("/emby/")) {
            normalizedPath = normalizedPath.substring("/emby".length());
         }

         return base + normalizedPath;
      }
   }

   private static String normalizePath(String path) {
      if (!StringUtils.hasText(path)) {
         return "/";
      } else {
         return path.startsWith("/") ? path : "/" + path;
      }
   }

   private static String trimTrailingSlashes(String value) {
      int end = value.length();

      while (end > 0 && value.charAt(end - 1) == '/') {
         end--;
      }

      return value.substring(0, end);
   }
}
