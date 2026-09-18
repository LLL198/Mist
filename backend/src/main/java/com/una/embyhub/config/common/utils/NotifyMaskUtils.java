package com.una.embyhub.config.common.utils;

import org.springframework.util.StringUtils;

public final class NotifyMaskUtils {
   private static final int USER_NAME_VISIBLE_PREFIX_LENGTH = 3;
   private static final String MASK_BLOCK = "▦";

   private NotifyMaskUtils() {
   }

   public static String maskUserName(String userName) {
      if (!StringUtils.hasText(userName)) {
         return "未知用户";
      } else {
         String text = userName.trim();
         int length = text.length();
         return length <= 3 ? text.substring(0, 1) + "▦".repeat(Math.max(1, length - 1)) : text.substring(0, 3) + "▦".repeat(length - 3);
      }
   }
}
