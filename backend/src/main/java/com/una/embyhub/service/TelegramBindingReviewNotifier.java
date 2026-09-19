package com.una.embyhub.service;

import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.NotifyMaskUtils;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.mapper.TelegramBindingReviewMessageMapper;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.entity.TelegramBindingReview;
import com.una.embyhub.model.entity.TelegramBindingReviewMessage;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.chat.ChatFullInfo;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class TelegramBindingReviewNotifier {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramBindingReviewNotifier.class);
   private final TelegramClientUtils telegramClientUtils;
   private final UserOauthBindingMapper userOauthBindingMapper;
   private final TelegramBindingReviewMessageMapper reviewMessageMapper;

   public TelegramBindingReviewNotifier.TelegramChatProfile resolveTelegramChatProfile(Long telegramUserId) {
      if (telegramUserId == null) {
         return TelegramBindingReviewNotifier.TelegramChatProfile.unresolved();
      } else {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         if (client == null) {
            return TelegramBindingReviewNotifier.TelegramChatProfile.unresolved();
         } else {
            try {
               ChatFullInfo chat = client.execute(GetChat.builder().chatId(telegramUserId).build());
               if (chat == null) {
                  return TelegramBindingReviewNotifier.TelegramChatProfile.unresolved();
               } else {
                  String username = StringUtils.hasText(chat.getUserName()) ? chat.getUserName().trim().replaceFirst("^@", "") : null;
                  String displayName = this.displayName(chat.getFirstName(), chat.getLastName(), username);
                  return new TelegramBindingReviewNotifier.TelegramChatProfile(true, username, displayName);
               }
            } catch (TelegramApiException var6) {
               log.warn("读取 Telegram 公开资料失败: telegramUserId={}, message={}", telegramUserId, var6.getMessage());
               return TelegramBindingReviewNotifier.TelegramChatProfile.unresolved();
            }
         }
      }
   }

   public void sendRebindVerificationCode(Long telegramUserId, String verificationCode, String embyUserName, long expiresInMinutes) {
      if (telegramUserId != null && StringUtils.hasText(verificationCode)) {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         if (client == null) {
            throw new BizException("Telegram 机器人未配置，暂时无法发送验证码");
         } else {
            String text = "\ud83c\udf01 <b>Mist 换绑验证码</b>\n\n\ud83d\udd04 正在为 Emby 账号 <b>"
               + this.html(embyUserName)
               + "</b> 更换绑定 Telegram。\n\n\ud83d\udd10 <b>验证码：</b><code>"
               + this.html(verificationCode)
               + "</code>\n⏳ <b>有效期：</b>"
               + expiresInMinutes
               + " 分钟\n\n\ud83d\udee1️ 请勿把验证码交给任何人。\n⚠️ 如果不是你本人操作，请忽略这条消息。";

            try {
               client.execute(SendMessage.builder().chatId(telegramUserId).text(text).parseMode("HTML").build());
            } catch (TelegramApiException var9) {
               log.warn("发送 Telegram 换绑验证码失败: telegramUserId={}, message={}", telegramUserId, var9.getMessage());
               throw new BizException("验证码发送失败，请确认新 Telegram 已私聊并启动 Mist Bot");
            }
         }
      } else {
         throw new BizException("Telegram 验证信息无效");
      }
   }

   public void notifyOldTelegramRebound(TelegramBindingReview review) {
      if (review != null && StringUtils.hasText(review.getOldTelegramUserId())) {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         if (client != null) {
            long oldTelegramUserId;
            try {
               oldTelegramUserId = Long.parseLong(review.getOldTelegramUserId());
            } catch (NumberFormatException var8) {
               return;
            }

            String text = "⚠️ <b>Mist 账号安全提醒</b>\n\n与你绑定的 Emby 账号 <b>"
               + this.html(review.getEmbyUserName())
               + "</b> 已更换 Telegram 归属。\n新绑定："
               + this.telegramDisplay(review)
               + "\n新 Telegram ID：<code>"
               + this.html(review.getTelegramUserId())
               + "</code>\n\n旧日的薄雾已经离岸。若这不是你的操作，请立即联系管理员检查账号安全。";

            try {
               client.execute(SendMessage.builder().chatId(oldTelegramUserId).text(text).parseMode("HTML").build());
            } catch (TelegramApiException var7) {
               log.warn("发送旧 Telegram 换绑安全提醒失败: reviewId={}, telegramUserId={}, message={}", review.getId(), oldTelegramUserId, var7.getMessage());
            }
         }
      }
   }

   public void notifyNewReview(TelegramBindingReview review) {
      if (review != null && review.getId() != null) {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         if (client == null) {
            log.warn("Telegram 绑定审批通知未发送：Telegram 通知渠道未配置");
         } else {
            InlineKeyboardButton approve = InlineKeyboardButton.builder().text("✅ 同意").callbackData("tg_binding_review:approve:" + review.getId()).build();
            InlineKeyboardButton reject = InlineKeyboardButton.builder().text("❌ 拒绝").callbackData("tg_binding_review:reject:" + review.getId()).build();
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(List.of(new InlineKeyboardRow(approve, reject))).build();
            String oldBindingText = "REBIND".equals(review.getActionType())
               ? "\ud83d\udd70 <b>原 Telegram：</b>"
                  + this.oldTelegramDisplay(review)
                  + "\n\ud83d\udd22 <b>原 Telegram ID：</b><code>"
                  + this.html(review.getOldTelegramUserId())
                  + "</code>\n"
               : "";
            String text = "\ud83d\udd10 <b>Telegram "
               + this.actionName(review)
               + "审批</b>\n\n\ud83d\udc64 <b>Emby 账号：</b>"
               + this.html(review.getEmbyUserName())
               + "\n"
               + oldBindingText
               + "✈️ <b>Telegram：</b>"
               + this.telegramDisplay(review)
               + "\n\ud83d\udd22 <b>Telegram ID：</b><code>"
               + this.html(review.getTelegramUserId())
               + "</code>\n\ud83e\udded <b>申请来源：</b>"
               + this.sourceName(review.getRequestSource())
               + "\n\ud83c\udd94 <b>审批指纹：</b><code>"
               + this.html(this.publicReviewId(review))
               + "</code>\n\n\ud83d\udc47 请确认后选择审批结果";

            for (Long chatId : this.resolveAdminChatIds()) {
               SendMessage message = SendMessage.builder().chatId(chatId).text(text).parseMode("HTML").replyMarkup(keyboard).build();

               try {
                  Message sentMessage = client.execute(message);
                  this.rememberReviewMessage(review.getId(), chatId, sentMessage);
               } catch (TelegramApiException var12) {
                  log.warn("发送 Telegram 绑定审批通知失败: reviewId={}, chatId={}, message={}", review.getId(), chatId, var12.getMessage());
               }
            }
         }
      }
   }

   public void synchronizeReviewStatus(TelegramBindingReview review) {
      this.synchronizeReviewStatus(review, null);
   }

   public void synchronizeReviewStatus(TelegramBindingReview review, TelegramBindingReviewNotifier.TelegramReviewerIdentity reviewerIdentity) {
      if (review != null && review.getId() != null && !Integer.valueOf(0).equals(review.getStatus())) {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         if (client != null) {
            List<TelegramBindingReviewMessage> messages;
            try {
               messages = this.reviewMessageMapper.selectActiveByReviewId(review.getId());
            } catch (RuntimeException var11) {
               log.warn("读取 Telegram 绑定审批消息坐标失败: reviewId={}, message={}", review.getId(), var11.getMessage());
               return;
            }

            if (messages != null && !messages.isEmpty()) {
               InlineKeyboardMarkup clearedKeyboard = InlineKeyboardMarkup.builder().keyboard(List.of()).build();
               String text = this.resolvedReviewText(review, reviewerIdentity);

               for (TelegramBindingReviewMessage message : messages) {
                  if (message != null && message.getChatId() != null && message.getMessageId() != null) {
                     try {
                        client.execute(
                           EditMessageText.builder()
                              .chatId(message.getChatId())
                              .messageId(message.getMessageId())
                              .text(text)
                              .parseMode("HTML")
                              .replyMarkup(clearedKeyboard)
                              .build()
                        );
                     } catch (TelegramApiException var10) {
                        log.warn(
                           "同步 Telegram 绑定审批状态失败: reviewId={}, chatId={}, messageId={}, message={}",
                           review.getId(),
                           message.getChatId(),
                           message.getMessageId(),
                           var10.getMessage()
                        );
                     }
                  }
               }
            }
         }
      }
   }

   public void notifyReviewResult(TelegramBindingReview review) {
      if (review != null && StringUtils.hasText(review.getTelegramUserId())) {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         if (client != null) {
            long chatId;
            try {
               chatId = Long.parseLong(review.getTelegramUserId());
            } catch (NumberFormatException var9) {
               return;
            }

            boolean approved = Integer.valueOf(1).equals(review.getStatus());
            String text = (approved ? "✅ <b>审批已通过</b>" : "❌ <b>审批未通过</b>")
               + "\n\n\ud83c\udd94 <b>审批指纹：</b><code>"
               + this.html(this.publicReviewId(review))
               + "</code>\n\ud83d\udccc <b>操作：</b>"
               + this.actionName(review)
               + "\n\ud83d\udc64 <b>Emby 账号：</b>"
               + this.html(review.getEmbyUserName())
               + "\n\ud83e\uddd1\u200d\ud83d\udcbc <b>审批人：</b>"
               + this.html(review.getReviewerUserName())
               + "\n"
               + (StringUtils.hasText(review.getReviewRemark()) ? "\ud83d\udcdd <b>备注：</b>" + this.html(review.getReviewRemark()) + "\n" : "")
               + "\n"
               + (
                  approved
                     ? (
                        "BIND".equals(review.getActionType())
                           ? "\ud83c\udf89 Telegram 绑定现已生效。"
                           : (
                              "REBIND".equals(review.getActionType())
                                 ? "\ud83c\udf01 Telegram 换绑现已生效，新的归属已写入 Mist。"
                                 : "\ud83d\udce6 Telegram 绑定已解除，Emby 账号仍然保留。"
                           )
                     )
                     : "ℹ️ 当前账号绑定关系未发生变化。"
               );

            try {
               client.execute(SendMessage.builder().chatId(chatId).text(text).parseMode("HTML").build());
            } catch (TelegramApiException var8) {
               log.warn("发送 Telegram 绑定审批结果失败: reviewId={}, message={}", review.getId(), var8.getMessage());
            }
         }
      }
   }

   public void notifyBindingSuccess(TelegramBindingReview review, Set<String> targets) {
      if (review != null && targets != null && !targets.isEmpty()) {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
         if (client != null && config != null) {
            for (Entry<Long, Boolean> destination : this.resolveSuccessDestinations(config, targets).entrySet()) {
               Long chatId = destination.getKey();
               String text = this.bindingSuccessText(review, destination.getValue());

               try {
                  client.execute(SendMessage.builder().chatId(chatId).text(text).parseMode("HTML").build());
               } catch (TelegramApiException var10) {
                  log.warn("发送 Telegram 绑定操作成功通知失败: action={}, chatId={}, message={}", review.getActionType(), chatId, var10.getMessage());
               }
            }
         }
      }
   }

   private String bindingSuccessText(TelegramBindingReview review, boolean maskEmbyUserName) {
      String embyUserName = maskEmbyUserName ? NotifyMaskUtils.maskUserName(review.getEmbyUserName()) : review.getEmbyUserName();
      String oldBindingText = "REBIND".equals(review.getActionType())
         ? "\ud83d\udd70 <b>原 Telegram：</b>"
            + this.oldTelegramDisplay(review)
            + "\n\ud83d\udd22 <b>原 Telegram ID：</b><code>"
            + this.html(review.getOldTelegramUserId())
            + "</code>\n"
         : "";
      return "✅ <b>Telegram "
         + this.actionName(review)
         + "成功</b>\n\n\ud83d\udc64 <b>Emby 账号：</b>"
         + this.html(embyUserName)
         + "\n"
         + oldBindingText
         + "✈️ <b>Telegram：</b>"
         + this.telegramDisplay(review)
         + "\n\ud83d\udd22 <b>Telegram ID：</b><code>"
         + this.html(review.getTelegramUserId())
         + "</code>\n\ud83e\udded <b>操作来源：</b>"
         + this.sourceName(review.getRequestSource());
   }

   public void notifyCancellation(TelegramBindingReview review) {
      if (review != null && review.getId() != null) {
         TelegramClient client = this.telegramClientUtils.getTelegramClient();
         if (client != null) {
            String text = "↩️ <b>用户已自助取消 Telegram "
               + this.actionName(review)
               + "申请</b>\n\n\ud83c\udd94 <b>审批指纹：</b><code>"
               + this.html(this.publicReviewId(review))
               + "</code>\n\ud83d\udc64 <b>Emby 账号：</b>"
               + this.html(review.getEmbyUserName())
               + "\n✈️ <b>Telegram：</b>"
               + this.telegramDisplay(review)
               + "\n\ud83d\udd22 <b>Telegram ID：</b><code>"
               + this.html(review.getTelegramUserId())
               + "</code>";

            for (Long chatId : this.resolveAdminChatIds()) {
               try {
                  client.execute(SendMessage.builder().chatId(chatId).text(text).parseMode("HTML").build());
               } catch (TelegramApiException var7) {
                  log.warn("发送 Telegram 审批取消通知失败: reviewId={}, chatId={}, message={}", review.getId(), chatId, var7.getMessage());
               }
            }
         }
      }
   }

   private Set<Long> resolveAdminChatIds() {
      Set<Long> ids = new LinkedHashSet<>();
      TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
      if (config != null) {
         this.addChatId(ids, config.getBotChatId());
      }

      for (String id : this.userOauthBindingMapper.selectActiveTelegramAdminIds()) {
         this.addChatId(ids, id);
      }

      return ids;
   }

   private void rememberReviewMessage(Long reviewId, Long chatId, Message sentMessage) {
      if (reviewId != null && chatId != null && sentMessage != null && sentMessage.getMessageId() != null) {
         try {
            this.reviewMessageMapper.upsertMessage(reviewId, chatId, sentMessage.getMessageId());
         } catch (RuntimeException var5) {
            log.warn(
               "保存 Telegram 绑定审批消息坐标失败: reviewId={}, chatId={}, messageId={}, message={}", reviewId, chatId, sentMessage.getMessageId(), var5.getMessage()
            );
         }
      }
   }

   private String resolvedReviewText(TelegramBindingReview review, TelegramBindingReviewNotifier.TelegramReviewerIdentity reviewerIdentity) {
      String headline;
      if (Integer.valueOf(1).equals(review.getStatus())) {
         headline = "✅ <b>Telegram " + this.actionName(review) + "审批已通过</b>";
      } else if (Integer.valueOf(2).equals(review.getStatus())) {
         headline = "❌ <b>Telegram " + this.actionName(review) + "审批已拒绝</b>";
      } else if (Integer.valueOf(3).equals(review.getStatus())) {
         headline = "↩️ <b>Telegram " + this.actionName(review) + "申请已取消</b>";
      } else {
         headline = "ℹ️ <b>Telegram " + this.actionName(review) + "审批已结束</b>";
      }

      String reviewerText = Integer.valueOf(3).equals(review.getStatus())
         ? ""
         : "\ud83e\uddd1\u200d\ud83d\udcbc <b>审批人：</b>" + this.reviewerMention(review, reviewerIdentity) + "\n";
      String remarkText = StringUtils.hasText(review.getReviewRemark()) ? "\ud83d\udcdd <b>备注：</b>" + this.html(review.getReviewRemark()) + "\n" : "";
      return headline
         + "\n\n\ud83d\udccc <b>操作：</b>"
         + this.actionName(review)
         + "\n\ud83d\udc64 <b>Emby 账号：</b>"
         + this.html(review.getEmbyUserName())
         + "\n✈️ <b>Telegram：</b>"
         + this.telegramDisplay(review)
         + "\n"
         + reviewerText
         + remarkText
         + "\ud83c\udd94 <b>审批指纹：</b><code>"
         + this.html(this.publicReviewId(review))
         + "</code>\n\n\ud83d\udd12 该申请已结束，审批按钮已失效。";
   }

   private String reviewerMention(TelegramBindingReview review, TelegramBindingReviewNotifier.TelegramReviewerIdentity reviewerIdentity) {
      if (reviewerIdentity != null && reviewerIdentity.telegramUserId() != null && reviewerIdentity.telegramUserId() > 0L) {
         String username = reviewerIdentity.telegramUsername();
         String displayName;
         if (StringUtils.hasText(username)) {
            displayName = "@" + username.trim().replaceFirst("^@", "");
         } else if (StringUtils.hasText(reviewerIdentity.displayName())) {
            displayName = reviewerIdentity.displayName().trim();
         } else {
            displayName = "Telegram 管理员";
         }

         return "<a href=\"tg://user?id=" + reviewerIdentity.telegramUserId() + "\">" + this.html(displayName) + "</a>";
      } else {
         return this.html(review.getReviewerUserName());
      }
   }

   private String displayName(String firstName, String lastName, String username) {
      StringBuilder name = new StringBuilder();
      if (StringUtils.hasText(firstName)) {
         name.append(firstName.trim());
      }

      if (StringUtils.hasText(lastName)) {
         if (!name.isEmpty()) {
            name.append(' ');
         }

         name.append(lastName.trim());
      }

      if (!name.isEmpty()) {
         return name.toString();
      } else {
         return StringUtils.hasText(username) ? "@" + username : null;
      }
   }

   private Map<Long, Boolean> resolveSuccessDestinations(TelegramResponse config, Set<String> targets) {
      Map<Long, Boolean> destinations = new LinkedHashMap<>();
      if (targets.contains("bot")) {
         this.addSuccessDestination(destinations, config.getBotChatId(), false);
      }

      if (targets.contains("group")) {
         this.addSuccessDestination(destinations, config.getBotChatGroupId(), true);
      }

      return destinations;
   }

   private void addSuccessDestination(Map<Long, Boolean> destinations, String value, boolean masked) {
      Long chatId = this.parseChatId(value);
      if (chatId != null) {
         destinations.merge(chatId, masked, (current, next) -> current || next);
      }
   }

   private void addChatId(Set<Long> ids, String value) {
      Long chatId = this.parseChatId(value);
      if (chatId != null) {
         ids.add(chatId);
      }
   }

   private Long parseChatId(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         try {
            return Long.parseLong(value.trim());
         } catch (NumberFormatException var3) {
            log.warn("忽略无效的 Telegram 通知 Chat ID: {}", value);
            return null;
         }
      }
   }

   private String telegramDisplay(TelegramBindingReview review) {
      String username = StringUtils.hasText(review.getTelegramUsername())
         ? "@" + review.getTelegramUsername().replaceFirst("^@", "")
         : review.getTelegramUserId();
      return this.html(username);
   }

   private String oldTelegramDisplay(TelegramBindingReview review) {
      String username = StringUtils.hasText(review.getOldTelegramUsername())
         ? "@" + review.getOldTelegramUsername().replaceFirst("^@", "")
         : review.getOldTelegramUserId();
      return this.html(username);
   }

   private String publicReviewId(TelegramBindingReview review) {
      return StringUtils.hasText(review.getReviewUuid()) ? review.getReviewUuid() : String.valueOf(review.getId());
   }

   private String actionName(TelegramBindingReview review) {
      String var2 = review.getActionType() == null ? "" : review.getActionType();

      return switch (var2) {
         case "UNBIND" -> "解绑";
         case "REBIND" -> "换绑";
         default -> "绑定";
      };
   }

   private String sourceName(String source) {
      String var2 = source == null ? "" : source;

      return switch (var2) {
         case "BOT", "BOT_CREDENTIAL" -> "机器人命令";
         case "BOT_REGISTER" -> "机器人注册命令";
         case "BOT_CARD" -> "机器人卡密命令";
         default -> "网页";
      };
   }

   private String html(String value) {
      return HtmlUtils.htmlEscape(value == null ? "--" : value);
   }

   @Generated
   public TelegramBindingReviewNotifier(
      final TelegramClientUtils telegramClientUtils,
      final UserOauthBindingMapper userOauthBindingMapper,
      final TelegramBindingReviewMessageMapper reviewMessageMapper
   ) {
      this.telegramClientUtils = telegramClientUtils;
      this.userOauthBindingMapper = userOauthBindingMapper;
      this.reviewMessageMapper = reviewMessageMapper;
   }

   public static record TelegramChatProfile(boolean resolved, String username, String displayName) {
      public static TelegramBindingReviewNotifier.TelegramChatProfile unresolved() {
         return new TelegramBindingReviewNotifier.TelegramChatProfile(false, null, null);
      }
   }

   public static record TelegramReviewerIdentity(Long telegramUserId, String telegramUsername, String displayName) {
   }
}
