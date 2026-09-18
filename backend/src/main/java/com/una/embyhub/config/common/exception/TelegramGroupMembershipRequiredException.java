package com.una.embyhub.config.common.exception;

import org.springframework.util.StringUtils;

public class TelegramGroupMembershipRequiredException extends BizException {
   private final String telegramMarkdownMessage;

   public TelegramGroupMembershipRequiredException(String groupTitle, String groupLink) {
      this("积分群/频道", groupTitle, groupLink);
   }

   public TelegramGroupMembershipRequiredException(String groupLabel, String groupTitle, String groupLink) {
      super(plainMessage(groupLabel));
      this.telegramMarkdownMessage = this.buildTelegramMarkdownMessage(normalizedGroupLabel(groupLabel), groupTitle, groupLink);
   }

   public String getTelegramMarkdownMessage() {
      return this.telegramMarkdownMessage;
   }

   private String buildTelegramMarkdownMessage(String groupLabel, String groupTitle, String groupLink) {
      if (!StringUtils.hasText(groupLink)) {
         return plainMessage(groupLabel);
      } else {
         String label = StringUtils.hasText(groupTitle) ? groupTitle.trim() : groupLabel;
         return "请先加入 [" + this.escapeMarkdownLinkText(label) + "](" + groupLink + ") 后再绑定 Telegram";
      }
   }

   private static String plainMessage(String groupLabel) {
      return "请先加入" + normalizedGroupLabel(groupLabel) + "后再绑定 Telegram";
   }

   private static String normalizedGroupLabel(String groupLabel) {
      return StringUtils.hasText(groupLabel) ? groupLabel.trim() : "所选群聊";
   }

   private String escapeMarkdownLinkText(String value) {
      return value.replace("\\", "\\\\").replace("_", "\\_").replace("*", "\\*").replace("`", "\\`").replace("[", "\\[").replace("]", "\\]");
   }
}
