package com.una.embyhub.config.common.telegrambot;

/**
 * Shared visual language for Telegram messages.
 *
 * <p>Only presentation is centralized here. Commands, callback payloads and
 * persisted data remain unchanged so existing Telegram keyboards and sessions
 * keep working.</p>
 */
public final class MistTelegramStyle {
   public static final String MARK = "🌁";
   public static final String DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄";

   private MistTelegramStyle() {
   }

   public static String markdownPanel(String title) {
      return MARK + " *MIST / " + title + "*\n" + DIVIDER + "\n\n";
   }

   public static String plainPanel(String title) {
      return MARK + " MIST / " + title + "\n" + DIVIDER + "\n\n";
   }

   public static String htmlPanel(String title) {
      return MARK + " <b>MIST / " + title + "</b>\n" + DIVIDER + "\n\n";
   }

   public static String htmlSection(String title) {
      return "\n<b>" + title + "</b>\n" + DIVIDER + "\n";
   }

   public static String markdownSection(String title) {
      return "\n*" + title + "*\n" + DIVIDER + "\n";
   }
}
