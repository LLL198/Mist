package com.una.embyhub.pointsbot.telegram;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import it.tdlight.Init;
import it.tdlight.Log;
import it.tdlight.Slf4JLogMessageHandler;
import it.tdlight.client.APIToken;
import it.tdlight.client.AuthenticationSupplier;
import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.client.SimpleTelegramClientBuilder;
import it.tdlight.client.SimpleTelegramClientFactory;
import it.tdlight.client.TDLibSettings;
import it.tdlight.jni.TdApi;
import it.tdlight.jni.TdApi.AuthorizationStateClosed;
import it.tdlight.jni.TdApi.AuthorizationStateLoggingOut;
import it.tdlight.jni.TdApi.AuthorizationStateReady;
import it.tdlight.jni.TdApi.BotCommandScope;
import it.tdlight.jni.TdApi.BotCommandScopeDefault;
import it.tdlight.jni.TdApi.CallbackQueryPayload;
import it.tdlight.jni.TdApi.CallbackQueryPayloadData;
import it.tdlight.jni.TdApi.ChatAdministrator;
import it.tdlight.jni.TdApi.ChatAdministrators;
import it.tdlight.jni.TdApi.ChatMemberStatusAdministrator;
import it.tdlight.jni.TdApi.ChatMemberStatusBanned;
import it.tdlight.jni.TdApi.ChatMemberStatusCreator;
import it.tdlight.jni.TdApi.ChatMemberStatusLeft;
import it.tdlight.jni.TdApi.ChatMemberStatusMember;
import it.tdlight.jni.TdApi.ChatMemberStatusRestricted;
import it.tdlight.jni.TdApi.ChatType;
import it.tdlight.jni.TdApi.ChatTypeBasicGroup;
import it.tdlight.jni.TdApi.ChatTypePrivate;
import it.tdlight.jni.TdApi.ChatTypeSupergroup;
import it.tdlight.jni.TdApi.DeleteCommands;
import it.tdlight.jni.TdApi.DeleteMessages;
import it.tdlight.jni.TdApi.FormattedText;
import it.tdlight.jni.TdApi.ForwardMessages;
import it.tdlight.jni.TdApi.GetChat;
import it.tdlight.jni.TdApi.GetMessage;
import it.tdlight.jni.TdApi.GetRepliedMessage;
import it.tdlight.jni.TdApi.GetUser;
import it.tdlight.jni.TdApi.InlineKeyboardButtonType;
import it.tdlight.jni.TdApi.InlineKeyboardButtonTypeCallback;
import it.tdlight.jni.TdApi.InlineKeyboardButtonTypeUrl;
import it.tdlight.jni.TdApi.InputFile;
import it.tdlight.jni.TdApi.InputFileLocal;
import it.tdlight.jni.TdApi.InputFileRemote;
import it.tdlight.jni.TdApi.InputInlineQueryResult;
import it.tdlight.jni.TdApi.InputInlineQueryResultArticle;
import it.tdlight.jni.TdApi.InputMessageContent;
import it.tdlight.jni.TdApi.InputMessageDice;
import it.tdlight.jni.TdApi.InputMessagePhoto;
import it.tdlight.jni.TdApi.InputMessageReplyTo;
import it.tdlight.jni.TdApi.InputMessageReplyToMessage;
import it.tdlight.jni.TdApi.InputMessageText;
import it.tdlight.jni.TdApi.MessageAnimation;
import it.tdlight.jni.TdApi.MessageAudio;
import it.tdlight.jni.TdApi.MessageChatAddMembers;
import it.tdlight.jni.TdApi.MessageChatDeleteMember;
import it.tdlight.jni.TdApi.MessageContent;
import it.tdlight.jni.TdApi.MessageDice;
import it.tdlight.jni.TdApi.MessageDocument;
import it.tdlight.jni.TdApi.MessagePhoto;
import it.tdlight.jni.TdApi.MessageSendOptions;
import it.tdlight.jni.TdApi.MessageSender;
import it.tdlight.jni.TdApi.MessageSenderUser;
import it.tdlight.jni.TdApi.MessageSticker;
import it.tdlight.jni.TdApi.MessageText;
import it.tdlight.jni.TdApi.MessageVideo;
import it.tdlight.jni.TdApi.MessageVoiceNote;
import it.tdlight.jni.TdApi.Messages;
import it.tdlight.jni.TdApi.Object;
import it.tdlight.jni.TdApi.ParseTextEntities;
import it.tdlight.jni.TdApi.Photo;
import it.tdlight.jni.TdApi.ReplyMarkupInlineKeyboard;
import it.tdlight.jni.TdApi.SearchPublicChat;
import it.tdlight.jni.TdApi.SetChatMemberStatus;
import it.tdlight.jni.TdApi.SetCommands;
import it.tdlight.jni.TdApi.TextEntity;
import it.tdlight.jni.TdApi.TextEntityType;
import it.tdlight.jni.TdApi.TextEntityTypeBold;
import it.tdlight.jni.TdApi.TextEntityTypeBotCommand;
import it.tdlight.jni.TdApi.TextEntityTypeCode;
import it.tdlight.jni.TdApi.TextEntityTypeItalic;
import it.tdlight.jni.TdApi.TextEntityTypeMention;
import it.tdlight.jni.TdApi.TextEntityTypeMentionName;
import it.tdlight.jni.TdApi.TextEntityTypePre;
import it.tdlight.jni.TdApi.TextEntityTypeTextUrl;
import it.tdlight.jni.TdApi.TextParseModeHTML;
import it.tdlight.jni.TdApi.TextParseModeMarkdown;
import it.tdlight.jni.TdApi.UpdateAuthorizationState;
import it.tdlight.jni.TdApi.UpdateChatMember;
import it.tdlight.jni.TdApi.UpdateNewCallbackQuery;
import it.tdlight.jni.TdApi.UpdateNewInlineCallbackQuery;
import it.tdlight.jni.TdApi.UpdateNewInlineQuery;
import it.tdlight.jni.TdApi.UpdateNewMessage;
import it.tdlight.jni.TdApi.UserTypeBot;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.LongFunction;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.groupadministration.BanChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.UnpinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.Dice;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberOwner;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChatMember;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TelegramBotApiClient implements AutoCloseable {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramBotApiClient.class);
   private static final AtomicBoolean TDLIGHT_INITIALIZED = new AtomicBoolean(false);
   private static final String SESSION_NAME = "pointsbot";
   private final String botToken;
   private final Integer apiId;
   private final String apiHash;
   private final Consumer<Update> updateConsumer;
   private final Consumer<org.telegram.telegrambots.meta.api.objects.Update> botApiUpdateConsumer;
   private final ExecutorService updateExecutor;
   private final TelegramClient sharedBotApiClient;
   private SimpleTelegramClientFactory clientFactory;
   private SimpleTelegramClient client;
   private final AtomicInteger botApiMessageIdSequence = new AtomicInteger(1);
   private final ConcurrentHashMap<Long, Integer> tdToBotApiMessageIds = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<Integer, Long> botApiToTdMessageIds = new ConcurrentHashMap<>();
   private final AtomicBoolean authorized = new AtomicBoolean(false);
   private final CountDownLatch authorizationReadyLatch = new CountDownLatch(1);

   public TelegramBotApiClient(String botToken, Integer apiId, String apiHash, Consumer<Update> updateConsumer) {
      this(botToken, apiId, apiHash, updateConsumer, null);
   }

   public TelegramBotApiClient(
      String botToken,
      Integer apiId,
      String apiHash,
      Consumer<Update> updateConsumer,
      Consumer<org.telegram.telegrambots.meta.api.objects.Update> botApiUpdateConsumer
   ) {
      this.botToken = botToken == null ? "" : botToken.trim();
      this.apiId = apiId;
      this.apiHash = apiHash == null ? "" : apiHash.trim();
      this.updateConsumer = updateConsumer;
      this.botApiUpdateConsumer = botApiUpdateConsumer;
      this.sharedBotApiClient = null;
      this.updateExecutor = Executors.newSingleThreadExecutor(r -> {
         Thread thread = new Thread(r, "telegram-mtproto-updates");
         thread.setDaemon(true);
         return thread;
      });
   }

   public TelegramBotApiClient(TelegramClient sharedBotApiClient) {
      this.botToken = "";
      this.apiId = null;
      this.apiHash = "";
      this.updateConsumer = null;
      this.botApiUpdateConsumer = null;
      this.sharedBotApiClient = sharedBotApiClient;
      this.updateExecutor = null;
   }

   public boolean isReady() {
      return this.sharedBotApiClient != null
         ? true
         : StringUtils.hasText(this.botToken)
            && this.apiId != null
            && this.apiId > 0
            && StringUtils.hasText(this.apiHash)
            && this.client != null
            && this.authorized.get();
   }

   public Long resolvePublicUserId(String username) {
      if (StringUtils.hasText(username) && this.sharedBotApiClient == null && this.isReady()) {
         String normalized = username.trim();

         while (normalized.startsWith("@")) {
            normalized = normalized.substring(1);
         }

         if (!StringUtils.hasText(normalized)) {
            return null;
         } else {
            try {
               it.tdlight.jni.TdApi.Chat chat = this.await(this.client.send(new SearchPublicChat(normalized)));
               if (chat != null && chat.type instanceof ChatTypePrivate privateChat) {
                  return privateChat.userId;
               }
            } catch (TelegramBotApiClient.TelegramBotApiException var6) {
               log.debug("解析 Telegram 用户名失败: username={}, error={}", username, var6.getMessage());
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public synchronized void start() {
      if (this.sharedBotApiClient == null) {
         if (!this.hasRequiredConfig()) {
            throw new TelegramBotApiClient.TelegramBotApiException("Telegram MTProto 参数未配置：需要 botToken、apiId、apiHash");
         } else {
            initTdLight();

            try {
               Path sessionPath = Paths.get("data", "tdlight", "pointsbot");
               TDLibSettings settings = TDLibSettings.create(new APIToken(this.apiId, this.apiHash));
               settings.setDatabaseDirectoryPath(sessionPath.resolve("data"));
               settings.setDownloadedFilesDirectoryPath(sessionPath.resolve("downloads"));
               this.clientFactory = new SimpleTelegramClientFactory();
               SimpleTelegramClientBuilder builder = this.clientFactory.builder(settings);
               builder.addUpdateHandler(UpdateAuthorizationState.class, this::onAuthorizationState);
               builder.addUpdateHandler(UpdateNewMessage.class, this::onNewMessage);
               builder.addUpdateHandler(UpdateNewCallbackQuery.class, this::onNewCallbackQuery);
               builder.addUpdateHandler(UpdateNewInlineCallbackQuery.class, this::onNewInlineCallbackQuery);
               builder.addUpdateHandler(UpdateNewInlineQuery.class, this::onNewInlineQuery);
               builder.addUpdateHandler(UpdateChatMember.class, this::onChatMember);
               this.client = builder.build(AuthenticationSupplier.bot(this.botToken));
               if (!this.waitUntilAuthorized(30L, TimeUnit.SECONDS)) {
                  throw new TelegramBotApiClient.TelegramBotApiException("Telegram MTProto 登录等待超时，请检查 botToken/apiId/apiHash 和网络连接");
               }
            } catch (Exception var4) {
               this.close();
               throw new TelegramBotApiClient.TelegramBotApiException("Telegram MTProto 客户端启动失败: " + describeException(var4), var4);
            }
         }
      }
   }

   public void setMyCommands(List<TelegramBotApiClient.BotCommand> commands) {
      this.setMyCommands(commands, new BotCommandScopeDefault());
   }

   private void setMyCommands(List<TelegramBotApiClient.BotCommand> commands, BotCommandScope scope) {
      if (this.isReady()) {
         if (this.sharedBotApiClient != null) {
            List<org.telegram.telegrambots.meta.api.objects.commands.BotCommand> items = commands.stream()
               .map(command -> new org.telegram.telegrambots.meta.api.objects.commands.BotCommand(command.command(), command.description()))
               .toList();
            SetMyCommands request = SetMyCommands.builder()
               .commands(items)
               .scope(org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault.builder().build())
               .build();
            this.executeBotApi(request);
         } else {
            TdApi.BotCommand[] items = commands.stream()
               .map(command -> new TdApi.BotCommand(command.command(), command.description()))
               .toArray(TdApi.BotCommand[]::new);
            this.await(this.client.send(new SetCommands(scope, "", items)));
         }
      }
   }

   public TelegramBotApiClient.ApiMessage sendMessage(long chatId, String text) {
      return this.sendMessage(chatId, text, null, null, null, false);
   }

   public TelegramBotApiClient.ApiMessage sendMessage(
      long chatId, String text, Long replyToMessageId, String parseMode, JSONObject replyMarkup, boolean disableNotification
   ) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         SendMessage request = new SendMessage(String.valueOf(chatId), text == null ? "" : text);
         if (replyToMessageId != null) {
            request.setReplyParameters(new ReplyParameters(this.toBotApiMessageId(replyToMessageId)));
         }

         if (StringUtils.hasText(parseMode)) {
            request.setParseMode("HTML".equalsIgnoreCase(parseMode) ? "html" : parseMode);
         }

         request.setReplyMarkup(this.toBotApiInlineKeyboardMarkup(replyMarkup));
         request.setDisableNotification(disableNotification);
         return TelegramBotApiClient.ApiMessage.from(this.executeBotApi(request));
      } else {
         InputMessageText content = new InputMessageText(this.formatText(text, parseMode), null, false);
         MessageSendOptions options = null;
         if (disableNotification) {
            options = new MessageSendOptions();
            options.disableNotification = true;
         }

         InputMessageReplyTo replyTo = replyToMessageId == null ? null : new InputMessageReplyToMessage(replyToMessageId, null, 0);
         it.tdlight.jni.TdApi.SendMessage requestx = new it.tdlight.jni.TdApi.SendMessage(
            chatId, 0L, replyTo, options, this.toInlineKeyboardMarkup(replyMarkup), content
         );
         return TelegramBotApiClient.ApiMessage.from(this.await(this.client.sendMessage(requestx, true)));
      }
   }

   public void deleteMessage(long chatId, long messageId) {
      this.deleteMessages(chatId, messageId);
   }

   public void deleteMessages(long chatId, long... messageIds) {
      this.ensureReady();
      if (messageIds != null && messageIds.length != 0) {
         if (this.sharedBotApiClient == null) {
            this.await(this.client.send(new DeleteMessages(chatId, messageIds, true)));
         } else {
            List<Integer> botApiMessageIds = new ArrayList<>(messageIds.length);

            for (long messageId : messageIds) {
               botApiMessageIds.add(Math.toIntExact(messageId));
            }

            this.executeBotApi(
               org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages.builder()
                  .chatId(String.valueOf(chatId))
                  .messageIds(botApiMessageIds)
                  .build()
            );
         }
      }
   }

   public void answerCallbackQuery(String callbackQueryId) {
      this.answerCallbackQuery(callbackQueryId, null, false);
   }

   public void answerCallbackQuery(String callbackQueryId, String text, boolean showAlert) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         this.executeBotApi(new AnswerCallbackQuery(callbackQueryId, text, showAlert, null, 0));
      } else {
         long id;
         try {
            id = Long.parseLong(callbackQueryId);
         } catch (NumberFormatException var7) {
            throw new TelegramBotApiClient.TelegramBotApiException("CallbackQuery ID 格式错误: " + callbackQueryId);
         }

         this.await(this.client.send(new it.tdlight.jni.TdApi.AnswerCallbackQuery(id, text, showAlert, null, 0)));
      }
   }

   public void banChatMember(long chatId, long userId) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         this.executeBotApi(BanChatMember.builder().chatId(chatId).userId(userId).revokeMessages(false).build());
      } else {
         this.await(this.client.send(new SetChatMemberStatus(chatId, new MessageSenderUser(userId), new ChatMemberStatusBanned(0))));
      }
   }

   public void restrictChatMember(long chatId, long userId, int minutes) {
      this.ensureReady();
      if (minutes <= 0) {
         throw new TelegramBotApiClient.TelegramBotApiException("禁言分钟数必须大于 0");
      } else {
         long untilEpochSeconds = Instant.now().plusSeconds(TimeUnit.MINUTES.toSeconds((long)minutes)).getEpochSecond();
         int untilDate = Math.toIntExact(untilEpochSeconds);
         if (this.sharedBotApiClient != null) {
            ChatPermissions permissions = ChatPermissions.builder()
               .canSendMessages(false)
               .canSendAudios(false)
               .canSendDocuments(false)
               .canSendPhotos(false)
               .canSendVideos(false)
               .canSendVideoNotes(false)
               .canSendVoiceNotes(false)
               .canSendPolls(false)
               .canSendOtherMessages(false)
               .canAddWebPagePreviews(false)
               .build();
            this.executeBotApi(RestrictChatMember.builder().chatId(chatId).userId(userId).permissions(permissions).untilDate(untilDate).build());
         } else {
            it.tdlight.jni.TdApi.ChatPermissions permissions = new it.tdlight.jni.TdApi.ChatPermissions(
               false, false, false, false, false, false, false, false, false, false, false, false, false, false
            );
            this.await(
               this.client.send(new SetChatMemberStatus(chatId, new MessageSenderUser(userId), new ChatMemberStatusRestricted(true, untilDate, permissions)))
            );
         }
      }
   }

   public void unrestrictChatMember(long chatId, long userId) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         ChatPermissions permissions = ChatPermissions.builder()
            .canSendMessages(true)
            .canSendAudios(true)
            .canSendDocuments(true)
            .canSendPhotos(true)
            .canSendVideos(true)
            .canSendVideoNotes(true)
            .canSendVoiceNotes(true)
            .canSendPolls(true)
            .canSendOtherMessages(true)
            .canAddWebPagePreviews(true)
            .build();
         this.executeBotApi(RestrictChatMember.builder().chatId(chatId).userId(userId).permissions(permissions).build());
      } else {
         this.await(this.client.send(new SetChatMemberStatus(chatId, new MessageSenderUser(userId), new ChatMemberStatusMember(0))));
      }
   }

   public void pinChatMessage(long chatId, long messageId, boolean disableNotification) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         this.executeBotApi(new PinChatMessage(String.valueOf(chatId), this.toBotApiMessageId(messageId), disableNotification, null));
      } else {
         this.await(this.client.send(new it.tdlight.jni.TdApi.PinChatMessage(chatId, messageId, disableNotification, false)));
      }
   }

   public void unpinChatMessage(long chatId, long messageId) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         this.executeBotApi(new UnpinChatMessage(String.valueOf(chatId), this.toBotApiMessageId(messageId), null));
      } else {
         this.await(this.client.send(new it.tdlight.jni.TdApi.UnpinChatMessage(chatId, messageId)));
      }
   }

   public void editMessageText(long chatId, long messageId, String text, String parseMode, JSONObject replyMarkup) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         EditMessageText request = new EditMessageText(text == null ? "" : text);
         request.setChatId(String.valueOf(chatId));
         request.setMessageId(this.toBotApiMessageId(messageId));
         if (StringUtils.hasText(parseMode)) {
            request.setParseMode("HTML".equalsIgnoreCase(parseMode) ? "html" : parseMode);
         }

         request.setReplyMarkup(this.toBotApiInlineKeyboardMarkup(replyMarkup));
         this.executeBotApi(request);
      } else {
         InputMessageText content = new InputMessageText(this.formatText(text, parseMode), null, false);
         this.await(this.client.send(new it.tdlight.jni.TdApi.EditMessageText(chatId, messageId, this.toInlineKeyboardMarkup(replyMarkup), content)));
      }
   }

   public void editMessageCaption(long chatId, long messageId, String caption, String parseMode, JSONObject replyMarkup) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         EditMessageCaption request = EditMessageCaption.builder()
            .chatId(chatId)
            .messageId(this.toBotApiMessageId(messageId))
            .caption(caption == null ? "" : caption)
            .parseMode(StringUtils.hasText(parseMode) && "HTML".equalsIgnoreCase(parseMode) ? "html" : parseMode)
            .replyMarkup(this.toBotApiInlineKeyboardMarkup(replyMarkup))
            .build();
         this.executeBotApi(request);
      } else {
         this.await(
            this.client
               .send(
                  new it.tdlight.jni.TdApi.EditMessageCaption(
                     chatId, messageId, this.toInlineKeyboardMarkup(replyMarkup), this.formatText(caption, parseMode), false
                  )
               )
         );
      }
   }

   public TelegramBotApiClient.ApiMessage sendDice(long chatId, String emoji) {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         SendDice request = new SendDice(String.valueOf(chatId));
         request.setEmoji(StringUtils.hasText(emoji) ? emoji : "\ud83c\udfb2");
         return TelegramBotApiClient.ApiMessage.from(this.executeBotApi(request));
      } else {
         InputMessageDice dice = new InputMessageDice(StringUtils.hasText(emoji) ? emoji : "\ud83c\udfb2", false);
         it.tdlight.jni.TdApi.SendMessage request = new it.tdlight.jni.TdApi.SendMessage(chatId, 0L, null, null, null, dice);
         return TelegramBotApiClient.ApiMessage.from(this.await(this.client.sendMessage(request, true)));
      }
   }

   @Override
   public synchronized void close() {
      this.authorized.set(false);
      if (this.client != null) {
         try {
            this.client.close();
         } catch (Exception var14) {
            log.warn("关闭 Telegram MTProto client 失败: {}", var14.getMessage());
         } finally {
            this.client = null;
         }
      }

      if (this.clientFactory != null) {
         try {
            this.clientFactory.close();
         } catch (Exception var12) {
            log.warn("关闭 Telegram MTProto factory 失败: {}", var12.getMessage());
         } finally {
            this.clientFactory = null;
         }
      }

      if (this.updateExecutor != null) {
         this.updateExecutor.shutdownNow();
      }
   }

   private void onAuthorizationState(UpdateAuthorizationState update) {
      if (update.authorizationState instanceof AuthorizationStateReady) {
         this.authorized.set(true);
         this.authorizationReadyLatch.countDown();
         log.info("Telegram MTProto 登录成功。");
      } else if (update.authorizationState instanceof AuthorizationStateClosed) {
         this.authorized.set(false);
         log.info("Telegram MTProto 连接已关闭。");
      } else if (update.authorizationState instanceof AuthorizationStateLoggingOut) {
         this.authorized.set(false);
         log.info("Telegram MTProto 正在退出登录。");
      }
   }

   private boolean waitUntilAuthorized(long timeout, TimeUnit unit) {
      try {
         return this.authorizationReadyLatch.await(timeout, unit);
      } catch (InterruptedException var5) {
         Thread.currentThread().interrupt();
         throw new TelegramBotApiClient.TelegramBotApiException("等待 Telegram MTProto 登录被中断", var5);
      }
   }

   private void onNewMessage(UpdateNewMessage update) {
      if (update.message != null && (this.updateConsumer != null || this.botApiUpdateConsumer != null)) {
         this.updateExecutor.submit(() -> {
            try {
               if (this.updateConsumer != null) {
                  Message message = this.toMessage(update.message);
                  this.updateConsumer.accept(Update.message(message));
               }

               if (this.botApiUpdateConsumer != null) {
                  this.botApiUpdateConsumer.accept(this.toBotApiUpdate(update.message));
               }
            } catch (Exception var3) {
               log.warn("处理 Telegram MTProto 消息失败: {}", var3.getMessage());
            }
         });
      }
   }

   private void onNewCallbackQuery(UpdateNewCallbackQuery update) {
      if (this.updateConsumer != null || this.botApiUpdateConsumer != null) {
         this.updateExecutor.submit(() -> {
            try {
               String data = this.callbackPayloadData(update.payload);
               if (!StringUtils.hasText(data)) {
                  return;
               }

               if (this.updateConsumer != null) {
                  Chat chat = this.toChat(update.chatId);
                  User from = this.toUser(update.senderUserId);
                  Message message = Message.builder().chatId(update.chatId).messageId(update.messageId).chat(chat).from(from).build();
                  CallbackQuery callbackQuery = new CallbackQuery(String.valueOf(update.id), data, message, from);
                  this.updateConsumer.accept(Update.callbackQuery(callbackQuery));
               }

               if (this.botApiUpdateConsumer != null) {
                  this.botApiUpdateConsumer.accept(this.toBotApiCallbackUpdate(update, data));
               }
            } catch (Exception var7) {
               log.warn("处理 Telegram MTProto 回调失败: {}", var7.getMessage());
            }
         });
      }
   }

   private void onNewInlineQuery(UpdateNewInlineQuery update) {
      if (this.botApiUpdateConsumer != null) {
         this.updateExecutor.submit(() -> {
            try {
               this.botApiUpdateConsumer.accept(this.toBotApiInlineQueryUpdate(update));
            } catch (Exception var3) {
               log.warn("处理 Telegram MTProto InlineQuery 失败: {}", var3.getMessage());
            }
         });
      }
   }

   private void onNewInlineCallbackQuery(UpdateNewInlineCallbackQuery update) {
      if (this.botApiUpdateConsumer != null) {
         this.updateExecutor.submit(() -> {
            try {
               String data = this.callbackPayloadData(update.payload);
               if (!StringUtils.hasText(data)) {
                  return;
               }

               this.botApiUpdateConsumer.accept(this.toBotApiInlineCallbackUpdate(update, data));
            } catch (Exception var3) {
               log.warn("处理 Telegram MTProto InlineCallbackQuery 失败: {}", var3.getMessage());
            }
         });
      }
   }

   private void onChatMember(UpdateChatMember update) {
      if (this.botApiUpdateConsumer != null) {
         this.updateExecutor.submit(() -> {
            try {
               this.botApiUpdateConsumer.accept(this.toBotApiChatMemberUpdate(update));
            } catch (Exception var3) {
               log.warn("处理 Telegram MTProto 群成员更新失败: {}", var3.getMessage());
            }
         });
      }
   }

   private Message toMessage(it.tdlight.jni.TdApi.Message source) {
      User from = this.toSenderUser(source.senderId);
      Chat chat = this.toChat(source.chatId);
      MessageContent content = source.content;
      String text = null;
      List<MessageEntity> entities = List.of();
      boolean photo = false;
      boolean video = false;
      boolean animation = false;
      boolean document = false;
      boolean audio = false;
      boolean voice = false;
      boolean sticker = false;
      if (content instanceof MessageText messageText && messageText.text != null) {
         text = messageText.text.text;
         entities = this.toEntities(text, messageText.text.entities);
         return Message.builder()
            .messageId(source.id)
            .chatId(source.chatId)
            .chat(chat)
            .from(from)
            .text(text)
            .entities(entities)
            .photo(photo)
            .video(video)
            .animation(animation)
            .document(document)
            .audio(audio)
            .voice(voice)
            .sticker(sticker)
            .build();
      }

      if (content instanceof MessagePhoto) {
         photo = true;
      } else if (content instanceof MessageVideo) {
         video = true;
      } else if (content instanceof MessageAnimation) {
         animation = true;
      } else if (content instanceof MessageDocument) {
         document = true;
      } else if (content instanceof MessageAudio) {
         audio = true;
      } else if (content instanceof MessageVoiceNote) {
         voice = true;
      } else if (content instanceof MessageSticker) {
         sticker = true;
      }

      return Message.builder()
         .messageId(source.id)
         .chatId(source.chatId)
         .chat(chat)
         .from(from)
         .text(text)
         .entities(entities)
         .photo(photo)
         .video(video)
         .animation(animation)
         .document(document)
         .audio(audio)
         .voice(voice)
         .sticker(sticker)
         .build();
   }

   private User toSenderUser(MessageSender sender) {
      return sender instanceof MessageSenderUser user ? this.toUser(user.userId) : null;
   }

   private User toUser(long userId) {
      it.tdlight.jni.TdApi.User tdUser = this.await(this.client.send(new GetUser(userId)));
      String username = null;
      if (tdUser.usernames != null && tdUser.usernames.activeUsernames != null && tdUser.usernames.activeUsernames.length > 0) {
         username = tdUser.usernames.activeUsernames[0];
      }

      boolean bot = tdUser.type instanceof UserTypeBot;
      return new User(tdUser.id, username, tdUser.firstName, tdUser.lastName, bot);
   }

   private Chat toChat(long chatId) {
      it.tdlight.jni.TdApi.Chat tdChat = this.await(this.client.send(new GetChat(chatId)));
      boolean group = tdChat.type instanceof ChatTypeBasicGroup;
      boolean superGroup = tdChat.type instanceof ChatTypeSupergroup;
      return new Chat(tdChat.id, group, superGroup);
   }

   private List<MessageEntity> toEntities(String text, TextEntity[] tdEntities) {
      if (tdEntities != null && tdEntities.length != 0) {
         List<MessageEntity> entities = new ArrayList<>();

         for (TextEntity entity : tdEntities) {
            if (entity.type instanceof TextEntityTypeMentionName mentionName) {
               entities.add(
                  new MessageEntity(
                     "text_mention", this.toUser(mentionName.userId), entity.offset, entity.length, this.extractEntityText(text, entity.offset, entity.length)
                  )
               );
            } else if (entity.type instanceof TextEntityTypeMention) {
               entities.add(new MessageEntity("mention", null, entity.offset, entity.length, this.extractEntityText(text, entity.offset, entity.length)));
            }
         }

         return entities;
      } else {
         return List.of();
      }
   }

   private String extractEntityText(String text, int offset, int length) {
      if (text != null && offset >= 0 && length > 0) {
         int end = offset + length;
         return end > text.length() ? null : text.substring(offset, end);
      } else {
         return null;
      }
   }

   private org.telegram.telegrambots.meta.api.objects.Update toBotApiUpdate(it.tdlight.jni.TdApi.Message source) {
      org.telegram.telegrambots.meta.api.objects.Update update = new org.telegram.telegrambots.meta.api.objects.Update();
      update.setMessage(this.toBotApiMessage(source));
      return update;
   }

   private org.telegram.telegrambots.meta.api.objects.Update toBotApiCallbackUpdate(UpdateNewCallbackQuery source, String data) {
      org.telegram.telegrambots.meta.api.objects.CallbackQuery callbackQuery = new org.telegram.telegrambots.meta.api.objects.CallbackQuery();
      callbackQuery.setId(String.valueOf(source.id));
      callbackQuery.setFrom(this.toBotApiUser(this.toUser(source.senderUserId)));
      callbackQuery.setData(data);
      it.tdlight.jni.TdApi.Message sourceMessage = this.await(this.client.send(new GetMessage(source.chatId, source.messageId)));
      org.telegram.telegrambots.meta.api.objects.message.Message message;
      if (sourceMessage != null) {
         message = this.toBotApiMessage(sourceMessage, false);
      } else {
         message = new org.telegram.telegrambots.meta.api.objects.message.Message();
         message.setMessageId(this.toBotApiMessageId(source.messageId));
         message.setChat(this.toBotApiChat(this.await(this.client.send(new GetChat(source.chatId)))));
         message.setFrom(callbackQuery.getFrom());
      }

      callbackQuery.setMessage(message);
      org.telegram.telegrambots.meta.api.objects.Update update = new org.telegram.telegrambots.meta.api.objects.Update();
      update.setCallbackQuery(callbackQuery);
      return update;
   }

   private org.telegram.telegrambots.meta.api.objects.Update toBotApiInlineCallbackUpdate(UpdateNewInlineCallbackQuery source, String data) {
      org.telegram.telegrambots.meta.api.objects.CallbackQuery callbackQuery = new org.telegram.telegrambots.meta.api.objects.CallbackQuery();
      callbackQuery.setId(String.valueOf(source.id));
      callbackQuery.setFrom(this.toBotApiUser(this.toUser(source.senderUserId)));
      callbackQuery.setData(data);
      callbackQuery.setInlineMessageId(source.inlineMessageId);
      callbackQuery.setChatInstance(String.valueOf(source.chatInstance));
      org.telegram.telegrambots.meta.api.objects.Update update = new org.telegram.telegrambots.meta.api.objects.Update();
      update.setCallbackQuery(callbackQuery);
      return update;
   }

   private org.telegram.telegrambots.meta.api.objects.Update toBotApiInlineQueryUpdate(UpdateNewInlineQuery source) {
      InlineQuery inlineQuery = new InlineQuery(String.valueOf(source.id), this.toBotApiUser(this.toUser(source.senderUserId)), source.query, source.offset);
      inlineQuery.setChatType(this.toBotApiChatType(source.chatType));
      org.telegram.telegrambots.meta.api.objects.Update update = new org.telegram.telegrambots.meta.api.objects.Update();
      update.setInlineQuery(inlineQuery);
      return update;
   }

   private org.telegram.telegrambots.meta.api.objects.Update toBotApiChatMemberUpdate(UpdateChatMember source) {
      ChatMemberUpdated memberUpdated = new ChatMemberUpdated();
      memberUpdated.setChat(this.toBotApiChat(this.await(this.client.send(new GetChat(source.chatId)))));
      memberUpdated.setFrom(this.toBotApiUser(this.toUser(source.actorUserId)));
      memberUpdated.setDate(source.date);
      memberUpdated.setOldChatMember(this.toBotApiChatMember(source.oldChatMember));
      memberUpdated.setNewChatMember(this.toBotApiChatMember(source.newChatMember));
      memberUpdated.setViaJoinRequest(source.viaJoinRequest);
      memberUpdated.setViaChatFolderInviteLink(source.viaChatFolderInviteLink);
      org.telegram.telegrambots.meta.api.objects.Update update = new org.telegram.telegrambots.meta.api.objects.Update();
      update.setChatMember(memberUpdated);
      return update;
   }

   private org.telegram.telegrambots.meta.api.objects.message.Message toBotApiMessage(it.tdlight.jni.TdApi.Message source) {
      return this.toBotApiMessage(source, true);
   }

   private org.telegram.telegrambots.meta.api.objects.message.Message toBotApiMessage(it.tdlight.jni.TdApi.Message source, boolean includeReply) {
      org.telegram.telegrambots.meta.api.objects.message.Message message;
      label33: {
         message = new org.telegram.telegrambots.meta.api.objects.message.Message();
         message.setMessageId(this.toBotApiMessageId(source.id));
         message.setDate(source.date);
         message.setChat(this.toBotApiChat(this.await(this.client.send(new GetChat(source.chatId)))));
         message.setFrom(this.toBotApiUser(this.toSenderUser(source.senderId)));
         MessageContent content = source.content;
         if (content instanceof MessageText messageText && messageText.text != null) {
            message.setText(messageText.text.text);
            message.setEntities(this.toBotApiEntities(messageText.text.entities));
            break label33;
         }

         if (content instanceof MessagePhoto messagePhoto) {
            message.setPhoto(this.toBotApiPhotos(messagePhoto.photo));
            if (messagePhoto.caption != null) {
               message.setCaption(messagePhoto.caption.text);
            }
         } else if (content instanceof MessageDice dice) {
            message.setDice(new Dice(dice.value, dice.emoji));
         } else {
            applyMemberServiceMessage(message, content, userId -> this.toBotApiUser(this.toUser(userId)));
         }
      }

      if (includeReply && source.replyTo != null) {
         try {
            it.tdlight.jni.TdApi.Message replied = this.await(this.client.send(new GetRepliedMessage(source.chatId, source.id)));
            if (replied != null) {
               message.setReplyToMessage(this.toBotApiMessage(replied, false));
            }
         } catch (TelegramBotApiClient.TelegramBotApiException var8) {
            log.debug("读取 Telegram 回复目标失败: chatId={}, messageId={}, error={}", source.chatId, source.id, var8.getMessage());
         }
      }

      return message;
   }

   static void applyMemberServiceMessage(
      org.telegram.telegrambots.meta.api.objects.message.Message message,
      MessageContent content,
      LongFunction<org.telegram.telegrambots.meta.api.objects.User> userResolver
   ) {
      if (message != null && content != null && userResolver != null) {
         if (content instanceof MessageChatDeleteMember deletedMember) {
            message.setLeftChatMember(userResolver.apply(deletedMember.userId));
         } else if (content instanceof MessageChatAddMembers addedMembers) {
            List<org.telegram.telegrambots.meta.api.objects.User> users = new ArrayList<>();
            if (addedMembers.memberUserIds != null) {
               for (long userId : addedMembers.memberUserIds) {
                  users.add(userResolver.apply(userId));
               }
            }

            message.setNewChatMembers(users);
         }
      }
   }

   private org.telegram.telegrambots.meta.api.objects.chat.Chat toBotApiChat(it.tdlight.jni.TdApi.Chat source) {
      String type = "private";
      if (source.type instanceof ChatTypeBasicGroup) {
         type = "group";
      } else if (source.type instanceof ChatTypeSupergroup supergroup) {
         type = supergroup.isChannel ? "channel" : "supergroup";
      }

      org.telegram.telegrambots.meta.api.objects.chat.Chat chat = new org.telegram.telegrambots.meta.api.objects.chat.Chat(source.id, type);
      chat.setTitle(source.title);
      return chat;
   }

   private org.telegram.telegrambots.meta.api.objects.User toBotApiUser(User source) {
      if (source != null && source.getId() != null) {
         org.telegram.telegrambots.meta.api.objects.User user = new org.telegram.telegrambots.meta.api.objects.User(
            source.getId(), source.getFirstName(), Boolean.TRUE.equals(source.getIsBot())
         );
         user.setUserName(source.getUserName());
         user.setLastName(source.getLastName());
         return user;
      } else {
         return null;
      }
   }

   private List<org.telegram.telegrambots.meta.api.objects.MessageEntity> toBotApiEntities(TextEntity[] source) {
      if (source != null && source.length != 0) {
         List<org.telegram.telegrambots.meta.api.objects.MessageEntity> entities = new ArrayList<>();

         for (TextEntity entity : source) {
            String type = this.toBotApiEntityType(entity.type);
            org.telegram.telegrambots.meta.api.objects.MessageEntity item = new org.telegram.telegrambots.meta.api.objects.MessageEntity(
               type, entity.offset, entity.length
            );
            if (entity.type instanceof TextEntityTypeMentionName mentionName) {
               item.setUser(this.toBotApiUser(this.toUser(mentionName.userId)));
            }

            entities.add(item);
         }

         return entities;
      } else {
         return List.of();
      }
   }

   private String toBotApiEntityType(TextEntityType type) {
      if (type instanceof TextEntityTypeMentionName) {
         return "text_mention";
      } else if (type instanceof TextEntityTypeBotCommand) {
         return "bot_command";
      } else if (type instanceof TextEntityTypeBold) {
         return "bold";
      } else if (type instanceof TextEntityTypeItalic) {
         return "italic";
      } else if (type instanceof TextEntityTypeCode) {
         return "code";
      } else if (type instanceof TextEntityTypePre) {
         return "pre";
      } else {
         return type instanceof TextEntityTypeTextUrl ? "text_link" : "mention";
      }
   }

   private List<PhotoSize> toBotApiPhotos(Photo photo) {
      if (photo != null && photo.sizes != null && photo.sizes.length != 0) {
         List<PhotoSize> photos = new ArrayList<>();

         for (it.tdlight.jni.TdApi.PhotoSize size : photo.sizes) {
            PhotoSize item = new PhotoSize();
            item.setWidth(size.width);
            item.setHeight(size.height);
            if (size.photo != null && size.photo.remote != null) {
               item.setFileId(size.photo.remote.id);
               item.setFileUniqueId(size.photo.remote.uniqueId);
            }

            photos.add(item);
         }

         return photos;
      } else {
         return List.of();
      }
   }

   private ChatMember toBotApiChatMember(it.tdlight.jni.TdApi.ChatMember source) {
      if (source == null) {
         return null;
      } else {
         org.telegram.telegrambots.meta.api.objects.User user = this.toBotApiUser(this.toSenderUser(source.memberId));
         if (source.status instanceof ChatMemberStatusCreator) {
            return new ChatMemberOwner(user, null, false);
         } else if (source.status instanceof ChatMemberStatusAdministrator) {
            ChatMemberAdministrator admin = new ChatMemberAdministrator();
            admin.setUser(user);
            admin.setCanManageChat(true);
            return admin;
         } else if (source.status instanceof ChatMemberStatusRestricted restricted) {
            ChatMemberRestricted item = new ChatMemberRestricted();
            item.setUser(user);
            item.setIsMember(restricted.isMember);
            if (restricted.permissions != null) {
               item.setCanSendMessages(restricted.permissions.canSendBasicMessages);
               item.setCanSendAudios(restricted.permissions.canSendAudios);
               item.setCanSendDocuments(restricted.permissions.canSendDocuments);
               item.setCanSendPhotos(restricted.permissions.canSendPhotos);
               item.setCanSendVideos(restricted.permissions.canSendVideos);
               item.setCanSendVideoNotes(restricted.permissions.canSendVideoNotes);
               item.setCanSendVoiceNotes(restricted.permissions.canSendVoiceNotes);
               item.setCanSendPolls(restricted.permissions.canSendPolls);
               item.setCanSendOtherMessages(restricted.permissions.canSendOtherMessages);
               item.setCanAddWebpagePreviews(restricted.permissions.canAddLinkPreviews);
               item.setCanChangeInfo(restricted.permissions.canChangeInfo);
               item.setCanInviteUsers(restricted.permissions.canInviteUsers);
               item.setCanPinMessages(restricted.permissions.canPinMessages);
               item.setCanManageTopics(restricted.permissions.canCreateTopics);
            }

            item.setUntilDate(restricted.restrictedUntilDate);
            return item;
         } else if (source.status instanceof ChatMemberStatusLeft) {
            return new ChatMemberLeft(user);
         } else {
            return (ChatMember)(source.status instanceof ChatMemberStatusBanned banned
               ? new ChatMemberBanned(user, banned.bannedUntilDate)
               : new ChatMemberMember(user));
         }
      }
   }

   private String toBotApiChatType(ChatType chatType) {
      if (chatType instanceof ChatTypeBasicGroup) {
         return "group";
      } else if (chatType instanceof ChatTypeSupergroup supergroup) {
         return supergroup.isChannel ? "channel" : "supergroup";
      } else {
         return "sender";
      }
   }

   private String callbackPayloadData(CallbackQueryPayload payload) {
      if (payload instanceof CallbackQueryPayloadData data && data.data != null) {
         return new String(data.data, StandardCharsets.UTF_8);
      }

      return null;
   }

   private FormattedText formatText(String text, String parseMode) {
      String safeText = text == null ? "" : text;
      if ("HTML".equalsIgnoreCase(parseMode)) {
         try {
            return this.client.execute(new ParseTextEntities(safeText, new TextParseModeHTML())).get();
         } catch (Exception var7) {
            log.warn("HTML 消息格式解析失败，降级为纯文本发送: {}", var7.getMessage());
         }
      }

      if ("Markdown".equalsIgnoreCase(parseMode) || "MarkdownV2".equalsIgnoreCase(parseMode)) {
         int markdownVersion = "MarkdownV2".equalsIgnoreCase(parseMode) ? 2 : 1;

         try {
            return this.client.execute(new ParseTextEntities(safeText, new TextParseModeMarkdown(markdownVersion))).get();
         } catch (Exception var6) {
            log.warn("{} 消息格式解析失败，降级为纯文本发送: {}", parseMode, var6.getMessage());
         }
      }

      return new FormattedText(safeText, new TextEntity[0]);
   }

   private ReplyMarkupInlineKeyboard toInlineKeyboardMarkup(JSONObject replyMarkup) {
      if (replyMarkup == null) {
         return null;
      } else {
         JSONArray rows = replyMarkup.getJSONArray("inline_keyboard");
         if (rows != null && !rows.isEmpty()) {
            it.tdlight.jni.TdApi.InlineKeyboardButton[][] keyboard = new it.tdlight.jni.TdApi.InlineKeyboardButton[rows.size()][];

            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
               JSONArray row = rows.getJSONArray(rowIndex);
               if (row != null && !row.isEmpty()) {
                  keyboard[rowIndex] = new it.tdlight.jni.TdApi.InlineKeyboardButton[row.size()];

                  for (int buttonIndex = 0; buttonIndex < row.size(); buttonIndex++) {
                     JSONObject item = row.getJSONObject(buttonIndex);
                     String text = item.getString("text");
                     String callbackData = item.getString("callback_data");
                     String url = item.getString("url");
                     keyboard[rowIndex][buttonIndex] = new it.tdlight.jni.TdApi.InlineKeyboardButton(
                        text,
                        (InlineKeyboardButtonType)(StringUtils.hasText(url)
                           ? new InlineKeyboardButtonTypeUrl(url)
                           : new InlineKeyboardButtonTypeCallback(callbackData == null ? new byte[0] : callbackData.getBytes(StandardCharsets.UTF_8)))
                     );
                  }
               } else {
                  keyboard[rowIndex] = new it.tdlight.jni.TdApi.InlineKeyboardButton[0];
               }
            }

            return new ReplyMarkupInlineKeyboard(keyboard);
         } else {
            return null;
         }
      }
   }

   private InlineKeyboardMarkup toBotApiInlineKeyboardMarkup(JSONObject replyMarkup) {
      if (replyMarkup == null) {
         return null;
      } else {
         JSONArray rows = replyMarkup.getJSONArray("inline_keyboard");
         if (rows != null && !rows.isEmpty()) {
            List<InlineKeyboardRow> keyboard = new ArrayList<>();

            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
               JSONArray row = rows.getJSONArray(rowIndex);
               InlineKeyboardRow rowItems = new InlineKeyboardRow();
               if (row != null) {
                  for (int buttonIndex = 0; buttonIndex < row.size(); buttonIndex++) {
                     JSONObject item = row.getJSONObject(buttonIndex);
                     InlineKeyboardButton button = new InlineKeyboardButton(item.getString("text"));
                     button.setCallbackData(item.getString("callback_data"));
                     button.setUrl(item.getString("url"));
                     rowItems.add(button);
                  }
               }

               keyboard.add(rowItems);
            }

            return new InlineKeyboardMarkup(keyboard);
         } else {
            return null;
         }
      }
   }

   private Integer toBotApiMessageId(long messageId) {
      return this.tdToBotApiMessageIds.computeIfAbsent(messageId, key -> {
         int next = this.botApiMessageIdSequence.getAndIncrement();
         this.botApiToTdMessageIds.put(next, key);
         return next;
      });
   }

   public long resolveNativeMessageId(long botApiMessageId) {
      if (this.sharedBotApiClient != null) {
         return botApiMessageId;
      } else {
         try {
            return this.toTdMessageId(Math.toIntExact(botApiMessageId));
         } catch (ArithmeticException var4) {
            throw new TelegramBotApiClient.TelegramBotApiException("Bot API 消息 ID 超出整数范围: " + botApiMessageId, var4);
         }
      }
   }

   private long toTdMessageId(Integer messageId) {
      return messageId == null ? 0L : this.botApiToTdMessageIds.getOrDefault(messageId, messageId.longValue());
   }

   public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         return this.sharedBotApiClient.execute(method);
      } else {
         try {
            if (method instanceof SetMyCommands request) {
               this.setMyCommands(
                  request.getCommands().stream().map(command -> new TelegramBotApiClient.BotCommand(command.getCommand(), command.getDescription())).toList(),
                  toTdCommandScope(request.getScope())
               );
               return (T)Boolean.TRUE;
            } else if (method instanceof DeleteMyCommands request) {
               this.await(this.client.send(new DeleteCommands(toTdCommandScope(request.getScope()), "")));
               return (T)Boolean.TRUE;
            } else if (method instanceof CopyMessage request) {
               return (T)this.executeCopyMessage(request);
            } else if (method instanceof SendMessage request) {
               return (T)this.executeSendMessage(request);
            } else if (method instanceof EditMessageText request) {
               this.editMessageText(
                  this.parseChatId(request.getChatId()),
                  this.toTdMessageId(request.getMessageId()),
                  request.getText(),
                  request.getParseMode(),
                  this.toJsonInlineKeyboard(request.getReplyMarkup())
               );
               return (T)Boolean.TRUE;
            } else if (method instanceof EditMessageCaption request) {
               it.tdlight.jni.TdApi.Message edited = this.await(
                  this.client
                     .send(
                        new it.tdlight.jni.TdApi.EditMessageCaption(
                           this.parseChatId(request.getChatId()),
                           this.toTdMessageId(request.getMessageId()),
                           this.toInlineKeyboardMarkup(this.toJsonInlineKeyboard(request.getReplyMarkup())),
                           this.formatText(request.getCaption(), request.getParseMode()),
                           Boolean.TRUE.equals(request.getShowCaptionAboveMedia())
                        )
                     )
               );
               return (T)this.toBotApiMessage(edited);
            } else if (method instanceof DeleteMessage request) {
               this.deleteMessage(this.parseChatId(request.getChatId()), this.toTdMessageId(request.getMessageId()));
               return (T)Boolean.TRUE;
            } else if (method instanceof org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages request) {
               long[] messageIds = request.getMessageIds().stream().mapToLong(this::toTdMessageId).toArray();
               this.deleteMessages(this.parseChatId(request.getChatId()), messageIds);
               return (T)Boolean.TRUE;
            } else if (method instanceof AnswerCallbackQuery request) {
               this.answerCallbackQuery(request.getCallbackQueryId(), request.getText(), Boolean.TRUE.equals(request.getShowAlert()));
               return (T)Boolean.TRUE;
            } else if (method instanceof AnswerInlineQuery request) {
               this.executeAnswerInlineQuery(request);
               return (T)Boolean.TRUE;
            } else if (method instanceof GetChatAdministrators request) {
               return (T)this.executeGetChatAdministrators(request);
            } else if (method instanceof GetChatMember request) {
               return (T)this.executeGetChatMember(request);
            } else {
               throw new TelegramBotApiClient.TelegramBotApiException("暂不支持的 MTProto Bot API 方法: " + method.getClass().getSimpleName());
            }
         } catch (TelegramBotApiClient.TelegramBotApiException var4) {
            throw new TelegramApiException(var4.getMessage(), var4);
         }
      }
   }

   static BotCommandScope toTdCommandScope(org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope scope) {
      if (scope == null || scope instanceof org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault) {
         return new BotCommandScopeDefault();
      } else if (scope instanceof BotCommandScopeAllPrivateChats) {
         return new it.tdlight.jni.TdApi.BotCommandScopeAllPrivateChats();
      } else if (scope instanceof BotCommandScopeAllGroupChats) {
         return new it.tdlight.jni.TdApi.BotCommandScopeAllGroupChats();
      } else if (scope instanceof BotCommandScopeAllChatAdministrators) {
         return new it.tdlight.jni.TdApi.BotCommandScopeAllChatAdministrators();
      } else if (scope instanceof BotCommandScopeChatMember member) {
         return new it.tdlight.jni.TdApi.BotCommandScopeChatMember(parseCommandScopeChatId(member.getChatId()), member.getUserId());
      } else if (scope instanceof BotCommandScopeChatAdministrators chatAdministrators) {
         return new it.tdlight.jni.TdApi.BotCommandScopeChatAdministrators(parseCommandScopeChatId(chatAdministrators.getChatId()));
      } else if (scope instanceof BotCommandScopeChat chat) {
         return new it.tdlight.jni.TdApi.BotCommandScopeChat(parseCommandScopeChatId(chat.getChatId()));
      } else {
         throw new TelegramBotApiClient.TelegramBotApiException("不支持的 Telegram 命令作用域: " + scope.getClass().getSimpleName());
      }
   }

   private static long parseCommandScopeChatId(String chatId) {
      if (!StringUtils.hasText(chatId)) {
         throw new TelegramBotApiClient.TelegramBotApiException("Telegram 命令作用域 chatId 不能为空");
      } else {
         try {
            return Long.parseLong(chatId.trim());
         } catch (NumberFormatException var2) {
            throw new TelegramBotApiClient.TelegramBotApiException("TDLib 命令作用域仅支持数字 chatId: " + chatId, var2);
         }
      }
   }

   private MessageId executeCopyMessage(CopyMessage request) {
      if (request.getMessageId() == null) {
         throw new TelegramBotApiClient.TelegramBotApiException("复制消息失败: messageId 不能为空");
      } else {
         MessageSendOptions options = this.toSendOptions(request.getDisableNotification());
         Messages messages = this.await(
            this.client
               .send(
                  new ForwardMessages(
                     this.parseChatId(request.getChatId()),
                     request.getMessageThreadId() == null ? 0L : (long)request.getMessageThreadId().intValue(),
                     this.parseChatId(request.getFromChatId()),
                     new long[]{this.toTdMessageId(request.getMessageId())},
                     options,
                     true,
                     false
                  )
               )
         );
         if (messages != null && messages.messages != null && messages.messages.length != 0 && messages.messages[0] != null) {
            return new MessageId(this.toBotApiMessageId(messages.messages[0].id).longValue());
         } else {
            throw new TelegramBotApiClient.TelegramBotApiException("复制消息失败: Telegram 未返回新消息 ID");
         }
      }
   }

   public org.telegram.telegrambots.meta.api.objects.message.Message execute(SendPhoto request) throws TelegramApiException {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         return this.sharedBotApiClient.execute(request);
      } else {
         try {
            long chatId = this.parseChatId(request.getChatId());
            InputMessagePhoto content = new InputMessagePhoto(
               this.toTdInputFile(request.getPhoto()),
               null,
               new int[0],
               0,
               0,
               this.formatText(request.getCaption(), request.getParseMode()),
               Boolean.TRUE.equals(request.getShowCaptionAboveMedia()),
               null,
               Boolean.TRUE.equals(request.getHasSpoiler())
            );
            MessageSendOptions options = this.toSendOptions(request.getDisableNotification());
            InputMessageReplyTo replyTo = request.getReplyToMessageId() == null
               ? null
               : new InputMessageReplyToMessage(this.toTdMessageId(request.getReplyToMessageId()), null, 0);
            it.tdlight.jni.TdApi.SendMessage send = new it.tdlight.jni.TdApi.SendMessage(
               chatId,
               request.getMessageThreadId() == null ? 0L : (long)request.getMessageThreadId().intValue(),
               replyTo,
               options,
               this.toInlineKeyboardMarkup(this.toJsonInlineKeyboard(request.getReplyMarkup())),
               content
            );
            return this.toBotApiMessage(this.await(this.client.sendMessage(send, true)));
         } catch (TelegramBotApiClient.TelegramBotApiException var8) {
            throw new TelegramApiException(var8.getMessage(), var8);
         }
      }
   }

   public Serializable execute(EditMessageMedia request) throws TelegramApiException {
      this.ensureReady();
      if (this.sharedBotApiClient != null) {
         return this.sharedBotApiClient.execute(request);
      } else {
         try {
            if (request.getMedia() instanceof InputMediaPhoto photo) {
               InputMessagePhoto var6 = new InputMessagePhoto(
                  this.toTdInputFile(photo),
                  null,
                  new int[0],
                  0,
                  0,
                  this.formatText(photo.getCaption(), photo.getParseMode()),
                  Boolean.TRUE.equals(photo.getShowCaptionAboveMedia()),
                  null,
                  Boolean.TRUE.equals(photo.getHasSpoiler())
               );
               it.tdlight.jni.TdApi.Message edited = this.await(
                  this.client
                     .send(
                        new it.tdlight.jni.TdApi.EditMessageMedia(
                           this.parseChatId(request.getChatId()),
                           this.toTdMessageId(request.getMessageId()),
                           this.toInlineKeyboardMarkup(this.toJsonInlineKeyboard(request.getReplyMarkup())),
                           var6
                        )
                     )
               );
               return this.toBotApiMessage(edited);
            } else {
               throw new TelegramBotApiClient.TelegramBotApiException("MTProto 目前仅支持编辑图片消息");
            }
         } catch (TelegramBotApiClient.TelegramBotApiException var5) {
            throw new TelegramApiException(var5.getMessage(), var5);
         }
      }
   }

   private org.telegram.telegrambots.meta.api.objects.message.Message executeSendMessage(SendMessage request) {
      InputMessageText content = new InputMessageText(this.formatText(request.getText(), request.getParseMode()), null, false);
      InputMessageReplyTo replyTo = null;
      Integer replyMessageId = request.getReplyParameters() == null ? request.getReplyToMessageId() : request.getReplyParameters().getMessageId();
      if (replyMessageId != null) {
         replyTo = new InputMessageReplyToMessage(this.toTdMessageId(replyMessageId), null, 0);
      }

      it.tdlight.jni.TdApi.SendMessage send = new it.tdlight.jni.TdApi.SendMessage(
         this.parseChatId(request.getChatId()),
         request.getMessageThreadId() == null ? 0L : (long)request.getMessageThreadId().intValue(),
         replyTo,
         this.toSendOptions(request.getDisableNotification()),
         this.toInlineKeyboardMarkup(this.toJsonInlineKeyboard(request.getReplyMarkup())),
         content
      );
      return this.toBotApiMessage(this.await(this.client.sendMessage(send, true)));
   }

   private void executeAnswerInlineQuery(AnswerInlineQuery request) {
      InputInlineQueryResult[] results = request.getResults() == null
         ? new InputInlineQueryResult[0]
         : request.getResults().stream().map(this::toTdInlineQueryResult).toArray(InputInlineQueryResult[]::new);
      long inlineQueryId = Long.parseLong(request.getInlineQueryId());
      this.await(
         this.client
            .send(
               new it.tdlight.jni.TdApi.AnswerInlineQuery(
                  inlineQueryId,
                  Boolean.TRUE.equals(request.getIsPersonal()),
                  null,
                  results,
                  request.getCacheTime() == null ? 0 : request.getCacheTime(),
                  request.getNextOffset() == null ? "" : request.getNextOffset()
               )
            )
      );
   }

   private ChatMember executeGetChatMember(GetChatMember request) {
      it.tdlight.jni.TdApi.ChatMember member = this.await(
         this.client.send(new it.tdlight.jni.TdApi.GetChatMember(this.parseChatId(request.getChatId()), new MessageSenderUser(request.getUserId())))
      );
      return this.toBotApiChatMember(member);
   }

   private ArrayList<ChatMember> executeGetChatAdministrators(GetChatAdministrators request) {
      ChatAdministrators admins = this.await(this.client.send(new it.tdlight.jni.TdApi.GetChatAdministrators(this.parseChatId(request.getChatId()))));
      ArrayList<ChatMember> result = new ArrayList<>();
      if (admins.administrators == null) {
         return result;
      } else {
         for (ChatAdministrator admin : admins.administrators) {
            org.telegram.telegrambots.meta.api.objects.User user = this.toBotApiUser(this.toUser(admin.userId));
            if (admin.isOwner) {
               result.add(new ChatMemberOwner(user, admin.customTitle, false));
            } else {
               ChatMemberAdministrator item = new ChatMemberAdministrator();
               item.setUser(user);
               item.setCustomTitle(admin.customTitle);
               item.setCanManageChat(true);
               result.add(item);
            }
         }

         return result;
      }
   }

   private InputInlineQueryResult toTdInlineQueryResult(InlineQueryResult source) {
      if (source instanceof InlineQueryResultArticle article) {
         return new InputInlineQueryResultArticle(
            article.getId(),
            article.getUrl() == null ? "" : article.getUrl(),
            article.getTitle() == null ? "" : article.getTitle(),
            article.getDescription() == null ? "" : article.getDescription(),
            article.getThumbnailUrl() == null ? "" : article.getThumbnailUrl(),
            article.getThumbnailWidth() == null ? 0 : article.getThumbnailWidth(),
            article.getThumbnailHeight() == null ? 0 : article.getThumbnailHeight(),
            this.toInlineKeyboardMarkup(this.toJsonInlineKeyboard(article.getReplyMarkup())),
            this.toTdInputMessageContent(article.getInputMessageContent(), article.getTitle())
         );
      } else {
         throw new TelegramBotApiClient.TelegramBotApiException("暂不支持的 InlineQueryResult: " + source.getClass().getSimpleName());
      }
   }

   private InputMessageContent toTdInputMessageContent(
      org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent source, String fallbackText
   ) {
      return source instanceof InputTextMessageContent text
         ? new InputMessageText(this.formatText(text.getMessageText(), text.getParseMode()), null, false)
         : new InputMessageText(this.formatText(fallbackText, null), null, false);
   }

   private MessageSendOptions toSendOptions(Boolean disableNotification) {
      if (!Boolean.TRUE.equals(disableNotification)) {
         return null;
      } else {
         MessageSendOptions options = new MessageSendOptions();
         options.disableNotification = true;
         return options;
      }
   }

   private InputFile toTdInputFile(org.telegram.telegrambots.meta.api.objects.InputFile inputFile) {
      if (inputFile == null) {
         throw new TelegramBotApiClient.TelegramBotApiException("图片不能为空");
      } else if (inputFile.getNewMediaFile() != null) {
         return new InputFileLocal(inputFile.getNewMediaFile().getAbsolutePath());
      } else if (inputFile.getNewMediaStream() != null) {
         throw new TelegramBotApiClient.TelegramBotApiException("MTProto 暂不支持 InputStream 图片上传");
      } else {
         return new InputFileRemote(inputFile.getAttachName());
      }
   }

   private InputFile toTdInputFile(InputMediaPhoto inputMedia) {
      if (inputMedia == null) {
         throw new TelegramBotApiClient.TelegramBotApiException("图片不能为空");
      } else if (inputMedia.getNewMediaFile() != null) {
         return new InputFileLocal(inputMedia.getNewMediaFile().getAbsolutePath());
      } else if (inputMedia.getNewMediaStream() != null) {
         throw new TelegramBotApiClient.TelegramBotApiException("MTProto 暂不支持 InputStream 图片上传");
      } else {
         return new InputFileRemote(inputMedia.getMedia());
      }
   }

   private JSONObject toJsonInlineKeyboard(ReplyKeyboard markup) {
      if (markup instanceof InlineKeyboardMarkup inlineMarkup && inlineMarkup.getKeyboard() != null) {
         JSONArray rows = new JSONArray();

         for (List<InlineKeyboardButton> row : inlineMarkup.getKeyboard()) {
            JSONArray buttons = new JSONArray();

            for (InlineKeyboardButton button : row) {
               JSONObject item = new JSONObject();
               item.put("text", button.getText());
               item.put("callback_data", button.getCallbackData());
               item.put("url", button.getUrl());
               buttons.add(item);
            }

            rows.add(buttons);
         }

         JSONObject json = new JSONObject();
         json.put("inline_keyboard", rows);
         return json;
      }

      return null;
   }

   private long parseChatId(String chatId) {
      if (!StringUtils.hasText(chatId)) {
         throw new TelegramBotApiClient.TelegramBotApiException("chatId 不能为空");
      } else {
         try {
            return Long.parseLong(chatId);
         } catch (NumberFormatException var4) {
            String username = chatId.startsWith("@") ? chatId.substring(1) : chatId;
            return this.await(this.client.send(new SearchPublicChat(username))).id;
         }
      }
   }

   private <T extends Serializable, Method extends BotApiMethod<T>> T executeBotApi(Method method) {
      try {
         return this.sharedBotApiClient.execute(method);
      } catch (TelegramApiException var3) {
         throw new TelegramBotApiClient.TelegramBotApiException(describeException(var3), var3);
      }
   }

   private org.telegram.telegrambots.meta.api.objects.message.Message executeBotApi(SendDice method) {
      try {
         return this.sharedBotApiClient.execute(method);
      } catch (TelegramApiException var3) {
         throw new TelegramBotApiClient.TelegramBotApiException(describeException(var3), var3);
      }
   }

   private <T extends Object> T await(CompletableFuture<T> future) {
      try {
         return future.get(60L, TimeUnit.SECONDS);
      } catch (ExecutionException var4) {
         Throwable cause = var4.getCause();
         throw new TelegramBotApiClient.TelegramBotApiException(
            describeException((Throwable)(cause == null ? var4 : cause)), (Throwable)(cause == null ? var4 : cause)
         );
      } catch (Exception var5) {
         throw new TelegramBotApiClient.TelegramBotApiException(describeException(var5), var5);
      }
   }

   private static String describeException(Throwable throwable) {
      if (throwable == null) {
         return "unknown error";
      } else {
         String message = throwable.getMessage();
         return StringUtils.hasText(message) ? message : throwable.toString();
      }
   }

   private void ensureReady() {
      if (!this.isReady()) {
         throw new TelegramBotApiClient.TelegramBotApiException("Telegram MTProto 客户端未启动");
      }
   }

   private boolean hasRequiredConfig() {
      return StringUtils.hasText(this.botToken) && this.apiId != null && this.apiId > 0 && StringUtils.hasText(this.apiHash);
   }

   private static void initTdLight() {
      if (TDLIGHT_INITIALIZED.compareAndSet(false, true)) {
         try {
            Init.init();
            Log.setLogMessageHandler(1, new Slf4JLogMessageHandler());
         } catch (Exception var1) {
            TDLIGHT_INITIALIZED.set(false);
            throw new TelegramBotApiClient.TelegramBotApiException("TDLight native 初始化失败: " + var1.getMessage());
         }
      }
   }

   public static JSONObject inlineKeyboard(List<List<TelegramBotApiClient.InlineButton>> rows) {
      JSONArray keyboard = new JSONArray();

      for (List<TelegramBotApiClient.InlineButton> row : rows) {
         JSONArray rowItems = new JSONArray();

         for (TelegramBotApiClient.InlineButton button : row) {
            JSONObject item = new JSONObject();
            item.put("text", button.text());
            if (StringUtils.hasText(button.url())) {
               item.put("url", button.url());
            } else {
               item.put("callback_data", button.callbackData());
            }

            rowItems.add(item);
         }

         keyboard.add(rowItems);
      }

      JSONObject markup = new JSONObject();
      markup.put("inline_keyboard", keyboard);
      return markup;
   }

   public static class ApiMessage {
      private final long messageId;
      private final Integer diceValue;

      private ApiMessage(long messageId, Integer diceValue) {
         this.messageId = messageId;
         this.diceValue = diceValue;
      }

      private static TelegramBotApiClient.ApiMessage from(it.tdlight.jni.TdApi.Message message) {
         if (message == null) {
            return null;
         } else {
            Integer diceValue = null;
            if (message.content instanceof MessageDice dice) {
               diceValue = dice.value;
            }

            return new TelegramBotApiClient.ApiMessage(message.id, diceValue);
         }
      }

      private static TelegramBotApiClient.ApiMessage from(org.telegram.telegrambots.meta.api.objects.message.Message message) {
         if (message == null) {
            return null;
         } else {
            Integer diceValue = message.getDice() == null ? null : message.getDice().getValue();
            return new TelegramBotApiClient.ApiMessage((long)message.getMessageId().intValue(), diceValue);
         }
      }

      @Generated
      public long getMessageId() {
         return this.messageId;
      }

      @Generated
      public Integer getDiceValue() {
         return this.diceValue;
      }
   }

   public static record BotCommand(String command, String description) {
   }

   public static record InlineButton(String text, String callbackData, String url) {
      public InlineButton(String text, String callbackData) {
         this(text, callbackData, null);
      }

      public static TelegramBotApiClient.InlineButton url(String text, String url) {
         return new TelegramBotApiClient.InlineButton(text, null, url);
      }
   }

   public static class TelegramBotApiException extends RuntimeException {
      public TelegramBotApiException(String message) {
         super(message);
      }

      public TelegramBotApiException(String message, Throwable cause) {
         super(message, cause);
      }
   }
}
