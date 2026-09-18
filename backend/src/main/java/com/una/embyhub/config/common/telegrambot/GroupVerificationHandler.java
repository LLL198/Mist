package com.una.embyhub.config.common.telegrambot;

import com.una.embyhub.config.common.utils.TelegramClientUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.BanChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class GroupVerificationHandler {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(GroupVerificationHandler.class);
   @Autowired
   private TelegramClientUtils telegramClientUtils;
   private TelegramClient telegramClient;
   private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16);
   private final SecureRandom random = new SecureRandom();
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   private static final String REDIS_KEY_PREFIX_VERIFY = "telegram:verify:session:";

   @PostConstruct
   public void init() {
      if (this.telegramClientUtils.getTelegramClient() != null) {
         this.telegramClient = this.telegramClientUtils.getTelegramClient();
      }
   }

   @PreDestroy
   public void onShutdown() {
      log.info("正在关闭群组验证调度器...");
      this.scheduler.shutdown();
   }

   public void handleNewMembers(Message message) {
      if (this.telegramClient != null) {
         long chatId = message.getChatId();
         log.info("开始处理新成员入群验证: chatId={}, userCount={}", chatId, message.getNewChatMembers().size());

         for (User newUser : message.getNewChatMembers()) {
            if (newUser.getIsBot()) {
               log.info("忽略机器人用户: {}", newUser.getId());
            } else {
               log.info("准备限制用户权限: userId={}, userName={}", newUser.getId(), newUser.getFirstName());
               this.restrictUser(chatId, newUser.getId());

               try {
                  log.info("发送验证消息: userId={}", newUser.getId());
                  this.sendAndScheduleArithmeticVerification(chatId, newUser);
               } catch (TelegramApiException var7) {
                  log.error("为用户 {} 发送验证消息失败: {}", newUser.getId(), var7.getMessage());
               }
            }
         }
      }
   }

   public void handleCallback(CallbackQuery callbackQuery) {
      if (this.telegramClient != null) {
         String[] dataParts = callbackQuery.getData().split(":");
         if (dataParts.length == 3 && "verify".equals(dataParts[0])) {
            long chatId = callbackQuery.getMessage().getChatId();
            User clicker = callbackQuery.getFrom();
            long targetUserId = Long.parseLong(dataParts[1]);
            int userAnswer = Integer.parseInt(dataParts[2]);
            if (!clicker.getId().equals(targetUserId)) {
               this.answerCallbackQueryWithAlert(callbackQuery.getId(), "这不是给你的验证。");
            } else {
               String userKey = chatId + ":" + targetUserId;
               GroupVerificationHandler.VerificationInfo verificationInfo = (GroupVerificationHandler.VerificationInfo)this.redisTemplate
                  .opsForValue()
                  .get("telegram:verify:session:" + userKey);
               if (verificationInfo == null) {
                  this.answerCallbackQueryWithAlert(callbackQuery.getId(), "验证已过期，你可能已被踢出群组。");
                  this.deleteMessage(chatId, callbackQuery.getMessage().getMessageId());
               } else {
                  if (userAnswer == verificationInfo.getCorrectAnswer()) {
                     log.info("用户 {} 在聊天 {} 中验证成功。", targetUserId, chatId);
                     this.redisTemplate.delete("telegram:verify:session:" + userKey);
                     this.unrestrictUser(chatId, targetUserId);
                     this.answerCallbackQueryWithAlert(callbackQuery.getId(), "验证成功，欢迎！");
                     this.deleteMessage(chatId, verificationInfo.getMessageId());
                  } else {
                     log.warn("用户 {} 在聊天 {} 中验证失败（答案错误）。", targetUserId, chatId);
                     this.redisTemplate.delete("telegram:verify:session:" + userKey);
                     this.answerCallbackQueryWithAlert(callbackQuery.getId(), "答案错误，你已被踢出群组。");
                     this.kickUser(chatId, targetUserId, clicker.getFirstName(), verificationInfo.getMessageId(), "回答验证问题错误");
                  }
               }
            }
         }
      }
   }

   private void restrictUser(long chatId, long userId) {
      ChatPermissions restrictions = new ChatPermissions();
      restrictions.setCanSendMessages(false);
      restrictions.setCanSendOtherMessages(false);
      restrictions.setCanAddWebPagePreviews(false);
      RestrictChatMember restrictChatMember = RestrictChatMember.builder().chatId(chatId).userId(userId).permissions(restrictions).build();

      try {
         this.telegramClient.execute(restrictChatMember);
      } catch (TelegramApiException var8) {
         log.error("限制新用户 {} 权限失败: {}", userId, var8.getMessage());
      }
   }

   private boolean unrestrictUser(long chatId, long userId) {
      ChatPermissions fullPermissions = new ChatPermissions();
      fullPermissions.setCanSendMessages(true);
      fullPermissions.setCanSendOtherMessages(true);
      fullPermissions.setCanAddWebPagePreviews(true);
      RestrictChatMember unrestrictMember = RestrictChatMember.builder().chatId(chatId).userId(userId).permissions(fullPermissions).build();

      try {
         this.telegramClient.execute(unrestrictMember);
         return true;
      } catch (TelegramApiException var8) {
         log.error("解除用户 {} 权限限制失败: {}", userId, var8.getMessage());
         return false;
      }
   }

   private void sendAndScheduleArithmeticVerification(long chatId, User newUser) throws TelegramApiException {
      int num1 = this.random.nextInt(10) + 1;
      int num2 = this.random.nextInt(10) + 1;
      int correctAnswer = num1 + num2;
      List<Integer> options = new ArrayList<>();
      options.add(correctAnswer);

      while (options.size() < 4) {
         int wrongAnswer = correctAnswer + (this.random.nextInt(9) - 4);
         if (!options.contains(wrongAnswer) && wrongAnswer > 0) {
            options.add(wrongAnswer);
         }
      }

      Collections.shuffle(options);
      List<InlineKeyboardButton> buttons = options.stream()
         .map(option -> InlineKeyboardButton.builder().text(String.valueOf(option)).callbackData("verify:" + newUser.getId() + ":" + option).build())
         .collect(Collectors.toList());
      InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboardRow(new InlineKeyboardRow(buttons)).build();
      String welcomeText = String.format("欢迎 [%s](tg://user?id=%d)！\n为证明您是人类，请在1分钟内回答问题：\n\n`%d + %d = ?`", newUser.getFirstName(), newUser.getId(), num1, num2);
      SendMessage verificationMessage = SendMessage.builder().chatId(chatId).text(welcomeText).parseMode("Markdown").replyMarkup(keyboard).build();
      Message sentMessage = this.telegramClient.execute(verificationMessage);
      String userKey = chatId + ":" + newUser.getId();
      GroupVerificationHandler.VerificationInfo info = new GroupVerificationHandler.VerificationInfo(sentMessage.getMessageId(), correctAnswer);
      this.redisTemplate.opsForValue().set("telegram:verify:session:" + userKey, info, 70L, TimeUnit.SECONDS);
      String userName = newUser.getFirstName();
      this.scheduler
         .schedule(
            () -> {
               GroupVerificationHandler.VerificationInfo stillPendingInfo = (GroupVerificationHandler.VerificationInfo)this.redisTemplate
                  .opsForValue()
                  .get("telegram:verify:session:" + userKey);
               if (stillPendingInfo != null) {
                  this.redisTemplate.delete("telegram:verify:session:" + userKey);
                  this.kickUser(chatId, newUser.getId(), userName, stillPendingInfo.getMessageId(), "验证超时");
               }
            },
            1L,
            TimeUnit.MINUTES
         );
   }

   private void kickUser(long chatId, long userId, String userName, int messageId, String reason) {
      this.deleteMessage(chatId, messageId);
      Integer notificationMessageId = null;

      try {
         String kickMessage = String.format("⚠️ [%s](tg://user?id=%d) 因 %s 已被移出群组。", userName, userId, reason);
         SendMessage notification = SendMessage.builder().chatId(chatId).text(kickMessage).parseMode("Markdown").build();
         Message sentNotification = this.telegramClient.execute(notification);
         notificationMessageId = sentNotification.getMessageId();
      } catch (TelegramApiException var13) {
         log.warn("发送踢出通知消息失败: {}", var13.getMessage());
      }

      try {
         BanChatMember kickChatMember = BanChatMember.builder().chatId(chatId).userId(userId).build();
         this.telegramClient.execute(kickChatMember);
         log.info("已将用户 {} ({}) 从聊天 {} 中踢出。原因: {}", userName, userId, chatId, reason);
         this.scheduler.schedule(() -> {
            try {
               UnbanChatMember unbanChatMember = UnbanChatMember.builder().chatId(chatId).userId(userId).onlyIfBanned(true).build();
               this.telegramClient.execute(unbanChatMember);
               log.info("已解除用户 {} ({}) 的封禁，现在可以重新加入群组", userName, userId);
            } catch (TelegramApiException var7) {
               log.warn("解除用户 {} ({}) 封禁失败: {}", userName, userId, var7.getMessage());
            }
         }, 60L, TimeUnit.SECONDS);
      } catch (TelegramApiException var12) {
         log.warn("无法踢出用户 {} ({}) (可能已被手动批准或已离开): {}", userName, userId, var12.getMessage());
      }

      if (notificationMessageId != null) {
         int msgId = notificationMessageId;
         this.scheduler.schedule(() -> this.deleteMessage(chatId, msgId), 10L, TimeUnit.SECONDS);
      }
   }

   private void deleteMessage(long chatId, int messageId) {
      DeleteMessage deleteMessage = DeleteMessage.builder().chatId(chatId).messageId(messageId).build();

      try {
         this.telegramClient.execute(deleteMessage);
      } catch (TelegramApiException var6) {
         log.warn("在聊天 {} 中删除消息 {} 失败: {}", chatId, messageId, var6.getMessage());
      }
   }

   private void answerCallbackQueryWithAlert(String callbackQueryId, String text) {
      AnswerCallbackQuery answer = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text(text).showAlert(true).build();

      try {
         this.telegramClient.execute(answer);
      } catch (TelegramApiException var5) {
         log.error("回应带弹窗警报的回调查询失败: {}", var5.getMessage());
      }
   }

   private static class VerificationInfo implements Serializable {
      private int messageId;
      private int correctAnswer;

      @Generated
      public int getMessageId() {
         return this.messageId;
      }

      @Generated
      public int getCorrectAnswer() {
         return this.correctAnswer;
      }

      @Generated
      public void setMessageId(final int messageId) {
         this.messageId = messageId;
      }

      @Generated
      public void setCorrectAnswer(final int correctAnswer) {
         this.correctAnswer = correctAnswer;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof GroupVerificationHandler.VerificationInfo other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            return this.getMessageId() != other.getMessageId() ? false : this.getCorrectAnswer() == other.getCorrectAnswer();
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof GroupVerificationHandler.VerificationInfo;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + this.getMessageId();
         return result * 59 + this.getCorrectAnswer();
      }

      @Generated
      @Override
      public String toString() {
         return "GroupVerificationHandler.VerificationInfo(messageId=" + this.getMessageId() + ", correctAnswer=" + this.getCorrectAnswer() + ")";
      }

      @Generated
      public VerificationInfo() {
      }

      @Generated
      public VerificationInfo(final int messageId, final int correctAnswer) {
         this.messageId = messageId;
         this.correctAnswer = correctAnswer;
      }
   }
}
