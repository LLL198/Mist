package com.una.embyhub.config.common.telegrambot;

import java.util.Arrays;
import java.util.Optional;

public enum TelegramBotPermission {
   USER_VIEW,
   USER_WHITELIST,
   USER_STATUS,
   USER_PASSWORD,
   USER_CREATE,
   LIBRARY_ACCESS,
   CARD_MANAGE,
   USER_RENEW,
   BROADCAST,
   POINTS_ADMIN,
   SCRATCH_RECORD_VIEW,
   LOTTERY_MANAGE,
   KICK_DELETE_USER;

   public static Optional<TelegramBotPermission> from(String value) {
      return value == null ? Optional.empty() : Arrays.stream(values()).filter(permission -> permission.name().equalsIgnoreCase(value.trim())).findFirst();
   }
}
