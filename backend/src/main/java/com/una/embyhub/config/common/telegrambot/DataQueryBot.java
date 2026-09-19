package com.una.embyhub.config.common.telegrambot;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.exception.TelegramGroupMembershipRequiredException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.config.common.utils.RedisLockUtils;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessGlobalUpdateRequest;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessUserUpdateRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserSave;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdateData;
import com.una.embyhub.model.dto.request.embyuser.InsertUserCardRequest;
import com.una.embyhub.model.dto.request.embyuser.RegisteredUserSave;
import com.una.embyhub.model.dto.request.requestlist.RequestListSave;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessConfigResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessOverviewResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUpdateResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUserOptionResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryFolderResponse;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.embyuser.InsertUserResponse;
import com.una.embyhub.model.dto.response.embyuser.RegisteredUserResponse;
import com.una.embyhub.model.dto.response.hostline.HostLineResponse;
import com.una.embyhub.model.dto.response.nullbr.MovieListResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingActionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.pointsbot.PointsBot;
import com.una.embyhub.pointsbot.telegram.Chat;
import com.una.embyhub.pointsbot.telegram.TelegramBotApiClient;
import com.una.embyhub.pointsbot.telegram.User;
import com.una.embyhub.service.CardSecurityManagementService;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyLibraryAccessService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.HostLineService;
import com.una.embyhub.service.NullbrService;
import com.una.embyhub.service.SystemConfigService;
import com.una.embyhub.service.TelegramAuthService;
import com.una.embyhub.service.TelegramBindingManager;
import com.una.embyhub.service.TelegramBindingReviewService;
import com.una.embyhub.service.TelegramRequestSubmitService;
import com.una.embyhub.service.TmdbService;
import com.una.embyhub.service.impl.TelegramPanServiceImpl;
import embyclient.ApiException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Locale.Builder;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption.EditMessageCaptionBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText.EditMessageTextBuilder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChatMember;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle.InlineQueryResultArticleBuilder;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Component
public class DataQueryBot implements LongPollingSingleThreadUpdateConsumer {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DataQueryBot.class);
   private TelegramBotApiClient telegramClient;
   @Autowired
   private NullbrService nullbrService;
   @Autowired
   private TmdbService tmdbService;
   @Value("${tmdb.imageUrl}")
   private String imageUrl;
   @Autowired
   private TelegramClientUtils telegramClientUtils;
   @Autowired
   private GroupVerificationHandler groupVerificationHandler;
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private SystemConfigService systemConfigService;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private CardSecurityManagementService cardSecurityManagementService;
   @Autowired
   private HostLineService hostLineService;
   @Autowired
   private UserOauthBindingMapper userOauthBindingMapper;
   @Autowired
   private TelegramAuthService telegramAuthService;
   @Autowired
   private TelegramRequestSubmitService telegramRequestSubmitService;
   @Autowired
   private TelegramBindingReviewService telegramBindingReviewService;
   @Autowired
   private TelegramBindingManager telegramBindingManager;
   @Autowired
   private TelegramBotAuthorizationService telegramBotAuthorizationService;
   @Autowired
   private EmbyLibraryAccessService embyLibraryAccessService;
   @Autowired
   private TelegramStartPanelImageStorage telegramStartPanelImageStorage;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   @Autowired
   private StringRedisTemplate stringRedisTemplate;
   @Autowired
   private RedisLockUtils redisLockUtils;
   private static final String SEARCH_CACHE_PREFIX = "bot:search:results:";
   private static final String RESOURCE_CACHE_PREFIX = "bot:search:resource:";
   private static final String INLINE_RESULT_CACHE_PREFIX = "bot:inline:result:";
   private static final String RATE_LIMIT_PREFIX = "bot:rate:";
   private static final String TELEGRAM_POINTS_GROUP_MEMBER_CACHE_PREFIX = "bot:telegram:points_group_member:";
   private static final String REQUEST_SUBMIT_LOCK_PREFIX = "bot:request:submit:lock:";
   private static final String TELEGRAM_CARD_OPEN_LOCK_PREFIX = "bot:cardopen:lock:";
   private static final String TELEGRAM_REGISTER_LOCK_PREFIX = "bot:register:lock:";
   private static final String TELEGRAM_REGISTER_QUEUE_KEY = "bot:register:queue";
   private static final String TELEGRAM_REGISTER_PROCESSING_QUEUE_KEY = "bot:register:processing";
   private static final String TELEGRAM_REGISTER_TASK_PREFIX = "bot:register:task:";
   private static final String ADMIN_PANEL_SESSION_PREFIX = "bot:admin:panel:";
   private static final String ADMIN_TELEGRAM_PANEL_SESSION_PREFIX = "bot:admin:telegram-panel:";
   private static final String LIBRARY_ACCESS_PANEL_SESSION_PREFIX = "bot:library-access:panel:";
   private static final String KK_REGISTRATION_GRANT_PREFIX = "bot:kk:register:";
   private static final String KK_REGISTRATION_TARGET_GRANT_PREFIX = "bot:kk:register:target:";
   private static final String COMMAND_SCOPE_ADMIN_IDS_KEY = "bot:commands:scoped_admin_ids";
   private static final String COMMAND_SCOPE_GROUP_ID_KEY = "bot:commands:scoped_group_id";
   private static final String TELEGRAM_PROCESSED_UPDATE_PREFIX = "bot:telegram:processed:";
   private static final Duration TELEGRAM_PROCESSED_UPDATE_TTL = Duration.ofDays(2L);
   private static final long CACHE_TTL_MINUTES = 15L;
   private static final long REQUEST_SUBMIT_LOCK_SECONDS = 10L;
   private static final long TELEGRAM_CARD_OPEN_LOCK_SECONDS = TimeUnit.MINUTES.toSeconds(5L);
   private static final long TELEGRAM_REGISTER_LOCK_SECONDS = TimeUnit.HOURS.toSeconds(6L);
   private static final long TELEGRAM_REGISTER_TASK_TTL_SECONDS = TimeUnit.HOURS.toSeconds(8L);
   private static final long ADMIN_PANEL_SESSION_TTL_MINUTES = 10L;
   private static final int LIBRARY_ACCESS_FOLDER_PAGE_SIZE = 8;
   private static final int LIBRARY_ACCESS_USER_PAGE_SIZE = 6;
   private static final long KK_REGISTRATION_GRANT_TTL_MINUTES = 30L;
   private static final long KK_GIFT_CONFIRMATION_CLEANUP_SECONDS = 60L;
   private static final long TELEGRAM_POINTS_GROUP_MEMBER_CACHE_SECONDS = 60L;
   private static final int INLINE_RESULT_LIMIT = 20;
   private static final long GROUP_COMMAND_CLEANUP_SECONDS = 5L;
   private static final Set<String> FOAM_GROUP_COMMANDS = Set.of(
      "/start",
      "/myaccount",
      "/mylines",
      "/bind",
      "/unbind",
      "/cancelreview",
      "/register",
      "/cardopen",
      "/cardrenew",
      "/search",
      "/request",
      "/createuser",
      "/libraryaccess",
      "/generatecards",
      "/extendusers",
      "/edituser",
      "/kk",
      "/whitelist",
      "/unwhitelist",
      "/callall",
      "/updateuserinfo",
      "/resetpassword",
      "/enableuser",
      "/disableuser",
      "/setexpiry",
      "/checkin",
      "/points",
      "/foambag",
      "/redpacket",
      "/hongbao",
      "/leaderboard",
      "/lottery",
      "/transfer",
      "/redeem",
      "/recharge",
      "/renew",
      "/exchange",
      "/prizes",
      "/sgs",
      "/blackjack",
      "/bj",
      "/dice",
      "/helldice",
      "/hell",
      "/hellrank",
      "/hellking",
      "/hellvault",
      "/slots",
      "/scratchwins",
      "/scratch",
      "/brain"
   );
   private static final long REGISTER_RATE_LIMIT_MAX_COUNT = 5L;
   private static final long REGISTER_RATE_LIMIT_WINDOW_SECONDS = TimeUnit.MINUTES.toSeconds(10L);
   private static final String REGISTER_RATE_LIMIT_WINDOW_TEXT = "10 分钟";
   private static final int TELEGRAM_REGISTER_QUOTA_UPDATE_RETRY_COUNT = 3;
   private static final int TELEGRAM_REGISTER_WORKER_COUNT = 6;
   private static final int TELEGRAM_REGISTER_QUEUE_MAX_SIZE = 10000;
   private static final long TELEGRAM_REGISTER_QUEUE_POLL_SECONDS = 5L;
   private static final long TELEGRAM_SEND_MESSAGE_INTERVAL_MILLIS = 40L;
   private static final int TELEGRAM_SEND_MESSAGE_MAX_RETRY_COUNT = 3;
   private static final Pattern TELEGRAM_RETRY_AFTER_PATTERN = Pattern.compile("retry(?:_|\\s*)after\\D*(\\d+)", 2);
   private static final long ANNOUNCEMENT_SESSION_TTL_MILLIS = TimeUnit.MINUTES.toMillis(10L);
   private static final long ANNOUNCEMENT_SEND_INTERVAL_MILLIS = 80L;
   private static final long START_PANEL_INPUT_TTL_MILLIS = TimeUnit.MINUTES.toMillis(10L);
   private static final String START_PANEL_LOGO_RESOURCE = "/img/mist.png";
   private static final String REQUEST_GUIDE_TEXT = "发送 `/request 片名` 搜索 TMDB，选择结果后点击提交求片。\n也可以使用 `@机器人 影片名` 内联搜索，体验更好；";
   private static final String REQUEST_GUIDE_AFTER_BIND_TEXT = "\ud83c\udfac 现在可以发送 `/request 片名` 搜索 TMDB 并提交求片。\n\ud83d\udca1 也可以使用 `@机器人 影片名` 内联搜索，体验更好；";
   private static final String ADMIN_PANEL_FOREIGN_CLICK_ALERT = "\ud83c\udf01 这片管理薄雾正由发起它的管理员守护哦～请让 TA 来轻轻操作吧。";
   private static final String KK_PROTECTED_TARGET_MESSAGE = "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～";
   private static final char[] KK_PASSWORD_LETTERS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
   private static final char[] KK_PASSWORD_DIGITS = "23456789".toCharArray();
   private static final SecureRandom KK_PASSWORD_RANDOM = new SecureRandom();
   private static final List<String> WHITELIST_CELEBRATION_COPY = List.of(
      "\ud83c\udf01 *星河落进薄雾，今夜的微光有了名字。*",
      "\ud83c\udf0c *一层薄雾越过银河，替你捎来整片星光。*",
      "✨ *梦在潮汐间醒来，星空正为新的旅程亮起。*",
      "\ud83c\udf20 *流星掠过雾海，通往远方的航线悄然展开。*",
      "\ud83c\udf19 *薄雾盛住月光，银河为幸运的人轻轻让路。*"
   );
   private static final int PAGE_SIZE = 5;
   private static final int RESOURCE_PAGE_SIZE = 5;
   @Autowired
   private TelegramPanServiceImpl telegramPanService;
   @Autowired
   private PointsBot pointsBot;
   private final Map<Long, DataQueryBot.PendingCreateUser> pendingCreateUsers = new ConcurrentHashMap<>();
   private final Map<Long, DataQueryBot.PendingCardBatch> pendingCardBatches = new ConcurrentHashMap<>();
   private final Map<Long, DataQueryBot.PendingUserEdit> pendingUserEdits = new ConcurrentHashMap<>();
   private final Map<Long, DataQueryBot.PendingExtendBatch> pendingExtendBatches = new ConcurrentHashMap<>();
   private final Map<Long, DataQueryBot.PendingAnnouncementBroadcast> pendingAnnouncementBroadcasts = new ConcurrentHashMap<>();
   private final Map<Long, DataQueryBot.PendingPanelCommand> pendingPanelCommands = new ConcurrentHashMap<>();
   private final Map<Long, DataQueryBot.PendingKkRegistration> pendingKkRegistrations = new ConcurrentHashMap<>();
   private final Map<Long, DataQueryBot.PendingTelegramMemberMute> pendingTelegramMemberMutes = new ConcurrentHashMap<>();
   private volatile File startPanelLogoFile;
   private final ScheduledExecutorService transientMessageCleaner = Executors.newSingleThreadScheduledExecutor(r -> {
      Thread thread = new Thread(r, "telegram-transient-message-cleaner");
      thread.setDaemon(true);
      return thread;
   });
   private final ExecutorService announcementExecutor = Executors.newSingleThreadExecutor(r -> {
      Thread thread = new Thread(r, "telegram-announcement-broadcast");
      thread.setDaemon(true);
      return thread;
   });
   private final ExecutorService telegramRegisterExecutor = Executors.newFixedThreadPool(6, r -> {
      Thread thread = new Thread(r, "telegram-register-worker");
      thread.setDaemon(true);
      return thread;
   });
   private final AtomicBoolean telegramRegisterWorkersStarted = new AtomicBoolean(false);
   private volatile boolean telegramRegisterWorkersRunning = true;
   private volatile long telegramConsumeStartedAtEpochSecond;
   private final Object telegramSendRateLock = new Object();
   private long nextTelegramSendMessageAtMillis;

   @PreDestroy
   public void shutdownTransientMessageCleaner() {
      this.telegramRegisterWorkersRunning = false;
      this.transientMessageCleaner.shutdownNow();
      this.announcementExecutor.shutdownNow();
      this.telegramRegisterExecutor.shutdownNow();
   }

   private void saveSearchResults(long chatId, List<TmdbResponse.Result> results) {
      this.redisTemplate.opsForValue().set("bot:search:results:" + chatId, results, 15L, TimeUnit.MINUTES);
   }

   private List<TmdbResponse.Result> getSearchResults(long chatId) {
      return (List<TmdbResponse.Result>)this.redisTemplate.opsForValue().get("bot:search:results:" + chatId);
   }

   private void saveResourceCache(long chatId, MovieListResponse response) {
      this.redisTemplate.opsForValue().set("bot:search:resource:" + chatId, response, 15L, TimeUnit.MINUTES);
   }

   private MovieListResponse getResourceCache(long chatId) {
      return (MovieListResponse)this.redisTemplate.opsForValue().get("bot:search:resource:" + chatId);
   }

   private void saveInlineResult(String token, TmdbResponse.Result result) {
      this.redisTemplate.opsForValue().set("bot:inline:result:" + token, result, 15L, TimeUnit.MINUTES);
   }

   private TmdbResponse.Result getInlineResult(String token) {
      return (TmdbResponse.Result)this.redisTemplate.opsForValue().get("bot:inline:result:" + token);
   }

   @PostConstruct
   public synchronized void initCommands() {
      if (this.telegramClient != null) {
         try {
            this.setCommandScope(List.of(new BotCommand("start", "打开 Mist 服务面板")), BotCommandScopeDefault.builder().build());
            List<BotCommand> privateCommands = this.buildPrivateUserCommands();
            List<BotCommand> groupCommands = this.buildGroupUserCommands();
            this.setCommandScope(privateCommands, BotCommandScopeAllPrivateChats.builder().build());
            this.setCommandScope(groupCommands, BotCommandScopeAllGroupChats.builder().build());
            TelegramResponse telegram = this.telegramClientUtils.getTelegramResponse();
            long ownerId = this.parseTelegramId(telegram == null ? null : telegram.getBotChatId());
            long groupId = this.parseTelegramId(telegram == null ? null : telegram.getBotChatGroupId());
            Map<Long, Set<TelegramBotPermission>> delegated = this.telegramBotAuthorizationService.configuredAdminPermissions();
            Set<Long> currentAdminIds = new LinkedHashSet<>(delegated.keySet());
            if (ownerId > 0L) {
               currentAdminIds.add(ownerId);
            }

            this.clearStaleCommandScopes(currentAdminIds, groupId);

            for (Long adminId : currentAdminIds) {
               this.refreshCommandMenuForAdmin(adminId, ownerId, groupId, delegated.getOrDefault(adminId, Set.of()), privateCommands, groupCommands);
            }

            this.rememberCommandScopes(currentAdminIds, groupId);
            log.info("成功按用户身份设置 Telegram 机器人命令菜单，管理员数量={}", currentAdminIds.size());
         } catch (Exception var12) {
            log.error("设置机器人命令失败", (Throwable)var12);
         }
      }
   }

   private List<BotCommand> buildPrivateUserCommands() {
      List<BotCommand> commands = new ArrayList<>(
         List.of(
            new BotCommand("start", "打开 Mist 服务面板"),
            new BotCommand("myaccount", "查询关联账号"),
            new BotCommand("mylines", "查看可用线路"),
            new BotCommand("bind", "绑定 Emby 账号"),
            new BotCommand("unbind", "解除 Telegram 绑定"),
            new BotCommand("cancelreview", "取消待审批申请"),
            new BotCommand("request", "搜索 TMDB 并求片"),
            new BotCommand("register", "注册 Emby 账号"),
            new BotCommand("cardopen", "使用卡密开通账号"),
            new BotCommand("cardrenew", "使用卡密续费"),
            new BotCommand("points", "查询积分"),
            new BotCommand("redeem", "积分注册账号"),
            new BotCommand("recharge", "积分续费账号"),
            new BotCommand("exchange", "查看兑换项目"),
            new BotCommand("prizes", "查看积分奖品")
         )
      );
      if (this.pointsBot.isFoamBagEnabled()) {
         commands.add(new BotCommand("foambag", "雾袋"));
      }

      return commands;
   }

   private List<BotCommand> buildGroupUserCommands() {
      List<BotCommand> commands = new ArrayList<>(
         List.of(
            new BotCommand("start", "打开 Mist 服务面板"),
            new BotCommand("checkin", "每日签到"),
            new BotCommand("points", "查询积分"),
            new BotCommand("leaderboard", "积分排行榜"),
            new BotCommand("scratchwins", "雾中刮刮乐大奖记录"),
            new BotCommand("lottery", "参与抽奖"),
            new BotCommand("transfer", "积分互转"),
            new BotCommand("exchange", "查看兑换项目"),
            new BotCommand("prizes", "积分奖品列表")
         )
      );
      if (this.pointsBot.isFoamBagEnabled()) {
         commands.add(new BotCommand("foambag", "雾袋"));
      }

      if (this.pointsBot.isRedPacketEnabled()) {
         commands.add(new BotCommand("redpacket", "群积分红包"));
      }

      if (this.pointsBot.isGameCommandEnabled("sgs")) {
         commands.add(new BotCommand("sgs", "三国杀玩法"));
      }

      if (this.pointsBot.isGameCommandEnabled("blackjack")) {
         commands.add(new BotCommand("blackjack", "21点游戏"));
      }

      if (this.pointsBot.isGameCommandEnabled("dice")) {
         commands.add(new BotCommand("dice", "骰子比大小"));
      }

      if (this.pointsBot.isGameCommandEnabled("hell_dice")) {
         commands.add(new BotCommand("helldice", "地狱骰"));
         commands.add(new BotCommand("hellrank", "地狱之王排行"));
      }

      if (this.pointsBot.isGameCommandEnabled("slots")) {
         commands.add(new BotCommand("slots", "老虎机"));
      }

      if (this.pointsBot.isGameCommandEnabled("scratch")) {
         commands.add(new BotCommand("scratch", "雾中刮刮乐"));
      }

      if (this.pointsBot.isGameCommandEnabled("brain")) {
         commands.add(new BotCommand("brain", "Brain 脑力挑战"));
      }

      return commands;
   }

   private List<BotCommand> buildManagementCommands(Set<TelegramBotPermission> permissions, boolean group) {
      List<BotCommand> commands = new ArrayList<>();
      if (permissions.contains(TelegramBotPermission.USER_VIEW)) {
         commands.add(new BotCommand("kk", group ? "回复用户打开公开管理面板" : "打开用户管理面板"));
         if (!group) {
            commands.add(new BotCommand("edituser", "搜索并修改用户"));
         }
      }

      if (permissions.contains(TelegramBotPermission.USER_WHITELIST)) {
         commands.add(new BotCommand("whitelist", "将用户加入白名单"));
         commands.add(new BotCommand("unwhitelist", "将用户移出白名单"));
      }

      if (!group && permissions.contains(TelegramBotPermission.USER_STATUS)) {
         commands.add(new BotCommand("updateuserinfo", "更新用户有效期和额度"));
         commands.add(new BotCommand("enableuser", "启用已选择用户"));
         commands.add(new BotCommand("disableuser", "禁用已选择用户"));
         commands.add(new BotCommand("setexpiry", "设置有效期并启用"));
      }

      if (!group && permissions.contains(TelegramBotPermission.USER_PASSWORD)) {
         commands.add(new BotCommand("resetpassword", "重置已选择用户密码"));
      }

      if (!group && permissions.contains(TelegramBotPermission.USER_CREATE)) {
         commands.add(new BotCommand("createuser", "创建 Emby 用户"));
      }

      if (!group && permissions.contains(TelegramBotPermission.LIBRARY_ACCESS)) {
         commands.add(new BotCommand("libraryaccess", "设置媒体库分级"));
      }

      if (!group && permissions.contains(TelegramBotPermission.CARD_MANAGE)) {
         commands.add(new BotCommand("generatecards", "批量生成卡密"));
      }

      if (!group && permissions.contains(TelegramBotPermission.USER_RENEW)) {
         commands.add(new BotCommand("extendusers", "批量延期用户"));
      }

      if (!group && permissions.contains(TelegramBotPermission.BROADCAST)) {
         commands.add(new BotCommand("callall", "批量发送公告"));
      }

      if (!group && permissions.contains(TelegramBotPermission.POINTS_ADMIN)) {
         commands.add(new BotCommand("renew", "管理员续期账号"));
         commands.add(new BotCommand("hellvault", "管理地狱骰金库"));
      }

      if (!group && permissions.contains(TelegramBotPermission.SCRATCH_RECORD_VIEW)) {
         commands.add(new BotCommand("scratchwins", "雾中刮刮乐大奖记录"));
      }

      return commands;
   }

   private List<BotCommand> mergeCommands(List<BotCommand> base, List<BotCommand> extra) {
      Map<String, BotCommand> merged = new LinkedHashMap<>();
      base.forEach(command -> merged.put(command.getCommand(), command));
      extra.forEach(command -> merged.put(command.getCommand(), command));
      return new ArrayList<>(merged.values());
   }

   private void refreshCommandMenuForAdmin(long adminId) {
      if (this.telegramBotAuthorizationService.isAdmin(adminId)) {
         TelegramResponse telegram = this.telegramClientUtils.getTelegramResponse();
         long ownerId = this.parseTelegramId(telegram == null ? null : telegram.getBotChatId());
         long groupId = this.parseTelegramId(telegram == null ? null : telegram.getBotChatGroupId());
         this.refreshCommandMenuForAdmin(
            adminId,
            ownerId,
            groupId,
            this.telegramBotAuthorizationService.permissionsFor(adminId),
            this.buildPrivateUserCommands(),
            this.buildGroupUserCommands()
         );
      }
   }

   private void refreshCommandMenuForAdmin(
      long adminId,
      long ownerId,
      long groupId,
      Set<TelegramBotPermission> delegatedPermissions,
      List<BotCommand> privateCommands,
      List<BotCommand> groupCommands
   ) {
      Set<TelegramBotPermission> permissions = adminId == ownerId ? Set.of(TelegramBotPermission.values()) : delegatedPermissions;
      this.setCommandScope(
         this.mergeCommands(privateCommands, this.buildManagementCommands(permissions, false)), BotCommandScopeChat.builder().chatId(adminId).build()
      );
      if (groupId != 0L) {
         this.setCommandScope(
            this.mergeCommands(groupCommands, this.buildManagementCommands(permissions, true)),
            BotCommandScopeChatMember.builder().chatId(groupId).userId(adminId).build()
         );
      }
   }

   private void setCommandScope(List<BotCommand> commands, BotCommandScope scope) {
      try {
         this.telegramClient.execute(SetMyCommands.builder().commands(commands).scope(scope).build());
      } catch (TelegramApiException var4) {
         log.warn("设置 Telegram 命令作用域失败: scope={}, error={}", scope.getClass().getSimpleName(), var4.getMessage());
      }
   }

   private void clearStaleCommandScopes(Set<Long> currentAdminIds, long currentGroupId) {
      Set<String> storedIds = this.stringRedisTemplate.opsForSet().members("bot:commands:scoped_admin_ids");
      Set<Long> idsToClear = new LinkedHashSet<>(currentAdminIds);
      if (storedIds != null) {
         storedIds.forEach(value -> {
            long id = this.parseTelegramId(value);
            if (id > 0L) {
               idsToClear.add(id);
            }
         });
      }

      long storedGroupId = this.parseTelegramId(this.stringRedisTemplate.opsForValue().get("bot:commands:scoped_group_id"));

      for (Long adminId : idsToClear) {
         this.deleteCommandScope(BotCommandScopeChat.builder().chatId(adminId).build());
         if (storedGroupId != 0L) {
            this.deleteCommandScope(BotCommandScopeChatMember.builder().chatId(storedGroupId).userId(adminId).build());
         }

         if (currentGroupId != 0L && currentGroupId != storedGroupId) {
            this.deleteCommandScope(BotCommandScopeChatMember.builder().chatId(currentGroupId).userId(adminId).build());
         }
      }
   }

   private void deleteCommandScope(BotCommandScope scope) {
      try {
         this.telegramClient.execute(DeleteMyCommands.builder().scope(scope).build());
      } catch (TelegramApiException var3) {
         log.warn("清理 Telegram 命令作用域失败: {}", var3.getMessage());
      }
   }

   private void rememberCommandScopes(Set<Long> adminIds, long groupId) {
      this.stringRedisTemplate.delete("bot:commands:scoped_admin_ids");
      if (!adminIds.isEmpty()) {
         this.stringRedisTemplate.opsForSet().add("bot:commands:scoped_admin_ids", adminIds.stream().map(String::valueOf).toArray(String[]::new));
      }

      if (groupId != 0L) {
         this.stringRedisTemplate.opsForValue().set("bot:commands:scoped_group_id", String.valueOf(groupId));
      } else {
         this.stringRedisTemplate.delete("bot:commands:scoped_group_id");
      }
   }

   private long parseTelegramId(String value) {
      if (!StringUtils.hasText(value)) {
         return 0L;
      } else {
         try {
            return Long.parseLong(value.trim());
         } catch (NumberFormatException var3) {
            return 0L;
         }
      }
   }

   public void attachMtProtoClient(TelegramBotApiClient telegramClient) {
      this.telegramClient = telegramClient;
      this.pointsBot.attachSharedClient(telegramClient);
      this.initCommands();
      this.startTelegramRegisterWorkers();
   }

   public void beginTelegramConsumption(TelegramBotApiClient telegramClient) {
      this.beginTelegramConsumption(telegramClient, Instant.now().getEpochSecond());
   }

   void beginTelegramConsumption(long startedAtEpochSecond) {
      this.beginTelegramConsumption(null, startedAtEpochSecond);
   }

   void beginTelegramConsumption(TelegramBotApiClient telegramClient, long startedAtEpochSecond) {
      if (telegramClient != null) {
         this.telegramClient = telegramClient;
      }

      this.telegramConsumeStartedAtEpochSecond = Math.max(1L, startedAtEpochSecond);
      log.info("Telegram 消费保护已启用，仅处理时间戳不早于 {} 的新消息", Instant.ofEpochSecond(this.telegramConsumeStartedAtEpochSecond));
   }

   @Override
   public void consume(Update update) {
      if (!this.shouldSkipTelegramUpdate(update)) {
         if (update.hasInlineQuery()) {
            this.handleInlineQuery(update.getInlineQuery());
         } else if (update.hasChatMember()) {
            this.handleChatMemberUpdated(update.getChatMember());
         } else if (update.hasMessage() && update.getMessage().getLeftChatMember() != null) {
            this.handleLeftChatMemberMessage(update.getMessage());
         } else if (!update.hasCallbackQuery()
            || this.isTelegramBindingReviewCallback(update.getCallbackQuery())
            || !this.shouldRejectPrivateCallback(update.getCallbackQuery())) {
            if (update.hasCallbackQuery() && this.isPointsBotCallback(update.getCallbackQuery())) {
               this.consumePointsBot(update);
            } else {
               if (update.hasCallbackQuery() && this.isPanCallback(update.getCallbackQuery())) {
                  this.telegramPanService.onCallback(update.getCallbackQuery());
               }

               if (!update.hasMessage() || !this.shouldRejectPrivateMessage(update.getMessage())) {
                  if (!update.hasMessage() || !this.handlePendingAnnouncementBroadcast(update.getMessage())) {
                     if (!update.hasMessage() || !this.handlePendingKkRegistrationInput(update.getMessage())) {
                        if (!update.hasMessage() || !this.handlePendingTelegramMemberMute(update.getMessage())) {
                           if (!update.hasMessage() || !this.handlePendingPanelCommand(update.getMessage())) {
                              if (update.hasMessage() && update.getMessage().hasText()) {
                                 this.handleTextMessage(update.getMessage());
                              } else if (update.hasCallbackQuery()) {
                                 CallbackQuery callbackQuery = update.getCallbackQuery();
                                 String data = callbackQuery.getData();
                                 if (StringUtils.hasText(data) && data.startsWith("verify:")) {
                                    this.groupVerificationHandler.handleCallback(callbackQuery);
                                 } else {
                                    this.handleCallbackQuery(callbackQuery);
                                 }
                              }

                              if (update.hasMessage()) {
                                 this.consumePointsBot(update);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean shouldSkipTelegramUpdate(Update update) {
      if (update == null) {
         return true;
      } else {
         long startedAt = this.telegramConsumeStartedAtEpochSecond;
         if (startedAt <= 0L) {
            return false;
         } else {
            Integer updateDate = this.resolveTelegramUpdateDate(update);
            if (!this.requiresTelegramUpdateDate(update) || updateDate != null && updateDate > 0 && (long)updateDate.intValue() >= startedAt) {
               String dedupeKey = this.resolveTelegramUpdateDedupeKey(update);
               if (!StringUtils.hasText(dedupeKey)) {
                  return false;
               } else {
                  try {
                     boolean firstDelivery = Boolean.TRUE
                        .equals(this.stringRedisTemplate.opsForValue().setIfAbsent(dedupeKey, "1", TELEGRAM_PROCESSED_UPDATE_TTL));
                     if (!firstDelivery) {
                        log.debug("跳过重复 Telegram Update: type={}, key={}", this.telegramUpdateType(update), dedupeKey);
                     }

                     return !firstDelivery;
                  } catch (Exception var7) {
                     log.error("Telegram Update 去重失败，为避免重复副作用拒绝消费: type={}, key={}", this.telegramUpdateType(update), dedupeKey, var7);
                     return true;
                  }
               }
            } else {
               log.debug("跳过 Telegram 历史 Update: type={}, date={}, consumeStartedAt={}", this.telegramUpdateType(update), updateDate, startedAt);
               return true;
            }
         }
      }
   }

   private Integer resolveTelegramUpdateDate(Update update) {
      if (update.hasMessage()) {
         return update.getMessage().getDate();
      } else if (update.hasCallbackQuery() && update.getCallbackQuery().getMessage() != null) {
         return update.getCallbackQuery().getMessage().getDate();
      } else if (update.hasChatMember()) {
         return update.getChatMember().getDate();
      } else {
         return update.hasMyChatMember() ? update.getMyChatMember().getDate() : null;
      }
   }

   private boolean requiresTelegramUpdateDate(Update update) {
      return update.hasMessage()
         || update.hasChatMember()
         || update.hasMyChatMember()
         || update.hasCallbackQuery() && update.getCallbackQuery().getMessage() != null;
   }

   private String resolveTelegramUpdateDedupeKey(Update update) {
      if (update.hasMessage()) {
         Message message = update.getMessage();
         if (message.getChatId() != null && message.getMessageId() != null) {
            long stableMessageId = this.telegramClient == null
               ? message.getMessageId().longValue()
               : this.telegramClient.resolveNativeMessageId((long)message.getMessageId().intValue());
            return "bot:telegram:processed:message:" + message.getChatId() + ":" + stableMessageId;
         }
      }

      if (update.hasCallbackQuery() && StringUtils.hasText(update.getCallbackQuery().getId())) {
         return "bot:telegram:processed:callback:" + update.getCallbackQuery().getId();
      } else if (update.hasInlineQuery() && StringUtils.hasText(update.getInlineQuery().getId())) {
         return "bot:telegram:processed:inline:" + update.getInlineQuery().getId();
      } else {
         ChatMemberUpdated memberUpdated = update.hasChatMember() ? update.getChatMember() : (update.hasMyChatMember() ? update.getMyChatMember() : null);
         if (memberUpdated != null
            && memberUpdated.getChat() != null
            && memberUpdated.getNewChatMember() != null
            && memberUpdated.getNewChatMember().getUser() != null
            && memberUpdated.getDate() != null) {
            return "bot:telegram:processed:chat-member:"
               + memberUpdated.getChat().getId()
               + ":"
               + memberUpdated.getNewChatMember().getUser().getId()
               + ":"
               + memberUpdated.getDate()
               + ":"
               + memberUpdated.getNewChatMember().getClass().getSimpleName();
         } else {
            return update.getUpdateId() != null ? "bot:telegram:processed:update:" + update.getUpdateId() : null;
         }
      }
   }

   private String telegramUpdateType(Update update) {
      if (update.hasMessage()) {
         return "message";
      } else if (update.hasCallbackQuery()) {
         return "callback";
      } else if (update.hasInlineQuery()) {
         return "inline";
      } else {
         return !update.hasChatMember() && !update.hasMyChatMember() ? "other" : "chat-member";
      }
   }

   private boolean isPanCallback(CallbackQuery callbackQuery) {
      if (callbackQuery != null && callbackQuery.getMessage() != null && StringUtils.hasText(callbackQuery.getData())) {
         String data = callbackQuery.getData();
         return "home".equalsIgnoreCase(data) || "noop".equalsIgnoreCase(data) || data.startsWith("home:") || data.startsWith("cat:");
      } else {
         return false;
      }
   }

   private boolean isPointsBotCallback(CallbackQuery callbackQuery) {
      if (callbackQuery != null && StringUtils.hasText(callbackQuery.getData())) {
         String data = callbackQuery.getData();
         return data.startsWith("bj_")
            || data.startsWith("scratch:")
            || data.startsWith("scratchwins:")
            || data.startsWith("brain:")
            || data.startsWith("hell:")
            || data.startsWith("foam_bag:")
            || data.startsWith("red_packet:")
            || data.startsWith("lottery_prize_");
      } else {
         return false;
      }
   }

   private boolean isTelegramBindingReviewCallback(CallbackQuery callbackQuery) {
      return callbackQuery != null && StringUtils.hasText(callbackQuery.getData()) && callbackQuery.getData().startsWith("tg_binding_review:");
   }

   private void consumePointsBot(Update update) {
      try {
         com.una.embyhub.pointsbot.telegram.Update pointsUpdate = this.toPointsBotUpdate(update);
         if (pointsUpdate != null) {
            this.pointsBot.consume(pointsUpdate);
         }
      } catch (Exception var3) {
         log.warn("转发积分机器人消息失败", (Throwable)var3);
      }
   }

   private com.una.embyhub.pointsbot.telegram.Update toPointsBotUpdate(Update update) {
      if (update.hasMessage()) {
         return com.una.embyhub.pointsbot.telegram.Update.message(this.toPointsBotMessage(update.getMessage()));
      } else if (update.hasCallbackQuery()) {
         CallbackQuery callbackQuery = update.getCallbackQuery();
         User from = this.toPointsBotUser(callbackQuery.getFrom());
         com.una.embyhub.pointsbot.telegram.Message message = this.toPointsBotMessage(callbackQuery.getMessage(), from);
         return com.una.embyhub.pointsbot.telegram.Update.callbackQuery(
            new com.una.embyhub.pointsbot.telegram.CallbackQuery(callbackQuery.getId(), callbackQuery.getData(), message, from)
         );
      } else {
         return null;
      }
   }

   private com.una.embyhub.pointsbot.telegram.Message toPointsBotMessage(Message source) {
      return source == null
         ? null
         : com.una.embyhub.pointsbot.telegram.Message.builder()
            .messageId(this.toPointsBotMessageId(source.getMessageId()))
            .chatId(source.getChatId() == null ? 0L : source.getChatId())
            .chat(this.toPointsBotChat(source.getChat()))
            .from(this.toPointsBotUser(source.getFrom()))
            .text(source.getText())
            .entities(this.toPointsBotEntities(source.getEntities(), source.getText()))
            .photo(source.hasPhoto())
            .video(source.hasVideo())
            .animation(source.hasAnimation())
            .document(source.hasDocument())
            .audio(source.hasAudio())
            .voice(source.hasVoice())
            .sticker(source.hasSticker())
            .build();
   }

   private com.una.embyhub.pointsbot.telegram.Message toPointsBotMessage(MaybeInaccessibleMessage source, User from) {
      return source == null
         ? null
         : com.una.embyhub.pointsbot.telegram.Message.builder()
            .messageId(this.toPointsBotMessageId(source.getMessageId()))
            .chatId(source.getChatId() == null ? 0L : source.getChatId())
            .chat(this.toPointsBotChat(source.getChat()))
            .from(from)
            .entities(List.of())
            .build();
   }

   private long toPointsBotMessageId(Integer botApiMessageId) {
      if (botApiMessageId == null) {
         return 0L;
      } else {
         return this.telegramClient == null ? botApiMessageId.longValue() : this.telegramClient.resolveNativeMessageId((long)botApiMessageId.intValue());
      }
   }

   private Chat toPointsBotChat(org.telegram.telegrambots.meta.api.objects.chat.Chat source) {
      return source == null
         ? null
         : new Chat(source.getId() == null ? 0L : source.getId(), Boolean.TRUE.equals(source.isGroupChat()), Boolean.TRUE.equals(source.isSuperGroupChat()));
   }

   private User toPointsBotUser(org.telegram.telegrambots.meta.api.objects.User source) {
      return source == null ? null : new User(source.getId(), source.getUserName(), source.getFirstName(), source.getLastName(), source.getIsBot());
   }

   private List<com.una.embyhub.pointsbot.telegram.MessageEntity> toPointsBotEntities(List<MessageEntity> source, String messageText) {
      if (CollectionUtils.isEmpty(source)) {
         return List.of();
      } else {
         List<com.una.embyhub.pointsbot.telegram.MessageEntity> entities = new ArrayList<>();

         for (MessageEntity entity : source) {
            if ("text_mention".equals(entity.getType())) {
               entities.add(
                  new com.una.embyhub.pointsbot.telegram.MessageEntity(
                     entity.getType(),
                     this.toPointsBotUser(entity.getUser()),
                     entity.getOffset(),
                     entity.getLength(),
                     this.extractEntityText(messageText, entity.getOffset(), entity.getLength())
                  )
               );
            } else if ("mention".equals(entity.getType())) {
               entities.add(
                  new com.una.embyhub.pointsbot.telegram.MessageEntity(
                     entity.getType(),
                     null,
                     entity.getOffset(),
                     entity.getLength(),
                     this.extractEntityText(messageText, entity.getOffset(), entity.getLength())
                  )
               );
            }
         }

         return entities;
      }
   }

   private String extractEntityText(String messageText, Integer offset, Integer length) {
      if (messageText != null && offset != null && length != null && offset >= 0 && length > 0) {
         int end = offset + length;
         return end > messageText.length() ? null : messageText.substring(offset, end);
      } else {
         return null;
      }
   }

   private long toLong(Integer value) {
      return value == null ? 0L : value.longValue();
   }

   private void handleChatMemberUpdated(ChatMemberUpdated chatMemberUpdated) {
      if (chatMemberUpdated != null && chatMemberUpdated.getChat() != null) {
         ChatMember oldMember = chatMemberUpdated.getOldChatMember();
         ChatMember newMember = chatMemberUpdated.getNewChatMember();
         if (this.isActiveChatMember(oldMember) && this.isLeftChatMember(newMember)) {
            org.telegram.telegrambots.meta.api.objects.User telegramUser = this.getChatMemberUser(newMember);
            if (telegramUser == null) {
               telegramUser = this.getChatMemberUser(oldMember);
            }

            if (telegramUser != null && telegramUser.getId() != null) {
               this.handleTelegramUserLeft(chatMemberUpdated.getChat().getId(), telegramUser, chatMemberUpdated.getFrom());
            }
         }
      }
   }

   private void handleLeftChatMemberMessage(Message message) {
      if (message != null && message.getChat() != null && message.getLeftChatMember() != null) {
         this.handleTelegramUserLeft(message.getChatId(), message.getLeftChatMember(), message.getFrom());
      }
   }

   private void handleTelegramUserLeft(
      Long chatId, org.telegram.telegrambots.meta.api.objects.User telegramUser, org.telegram.telegrambots.meta.api.objects.User telegramActor
   ) {
      TelegramLeaveAutoDeleteSettings settings = TelegramLeaveAutoDeleteSettings.fromConfigValue(
         this.configCacheLoaderUtils.getConfigValue("telegram_leave_auto_delete_enabled")
      );
      if (settings.enabled() && telegramUser != null && telegramUser.getId() != null) {
         if (!this.isConfiguredTelegramLeaveGroup(chatId, settings)) {
            log.debug("忽略非配置群聊的 Telegram 退群事件: chatId={}, telegramUserId={}", chatId, telegramUser.getId());
         } else {
            Long telegramActorId = telegramActor == null ? null : telegramActor.getId();
            boolean selfLeave = Objects.equals(telegramUser.getId(), telegramActorId);
            boolean authorizedKick = !selfLeave
               && telegramActorId != null
               && settings.authorizedKickDeleteEnabled()
               && this.telegramBotAuthorizationService.hasPermission(telegramActorId, TelegramBotPermission.KICK_DELETE_USER);
            if (!selfLeave && !authorizedKick) {
               log.warn("忽略未授权的 Telegram 成员移除事件: chatId={}, actorTelegramUserId={}, targetTelegramUserId={}", chatId, telegramActorId, telegramUser.getId());
            } else {
               Long telegramUserId = telegramUser.getId();
               log.info(
                  "收到配置群聊的 Telegram 成员离开事件: chatId={}, actorTelegramUserId={}, targetTelegramUserId={}, action={}",
                  chatId,
                  telegramActorId,
                  telegramUserId,
                  selfLeave ? "self_leave" : "authorized_kick"
               );

               try {
                  EmbyUser boundUser = this.telegramBindingManager.findBoundUser(telegramUserId, false);
                  if (boundUser == null) {
                     log.info("Telegram 用户 {} 已退群，但未找到 Emby 绑定", telegramUserId);
                     return;
                  }

                  if (boundUser.getId() == null) {
                     log.warn("Telegram 用户 {} 已退群，但绑定用户缺少 Mist 系统 ID，跳过删除", telegramUserId);
                     return;
                  }

                  boolean protectedFoamAccount = Integer.valueOf(1).equals(boundUser.getIsAdmin()) || this.isWhitelistHostLineType(boundUser.getHostLineType());
                  boolean protectedTelegramOperator = this.telegramBotAuthorizationService.isAdmin(telegramUserId);
                  if (protectedFoamAccount || protectedTelegramOperator) {
                     this.telegramAuthService.forceUnbindByTelegramId(telegramUserId);
                     log.warn("Telegram 用户 {} 已解绑受保护账号 {}，Mist 管理员/白名单或 Telegram 通知渠道 Owner/授权管理员不自动删除", telegramUserId, boundUser.getEmbyUserName());
                     return;
                  }

                  this.embyUserService.deleteByUserId(List.of(boundUser.getId()));
                  log.info("Telegram 用户 {} 退群，已删除 Telegram 绑定、Mist 系统账号和 Emby 用户 {}({})", telegramUserId, boundUser.getEmbyUserName(), boundUser.getId());
               } catch (ApiException var12) {
                  this.forceUnbindAfterDeleteFailure(telegramUserId);
                  log.error("Telegram 用户 {} 退群后删除 Mist/Emby 账号失败，已执行兜底解绑", telegramUserId, var12);
               } catch (Exception var13) {
                  this.forceUnbindAfterDeleteFailure(telegramUserId);
                  log.error("处理 Telegram 用户 {} 退群清理失败", telegramUserId, var13);
               }
            }
         }
      }
   }

   private void forceUnbindAfterDeleteFailure(Long telegramUserId) {
      try {
         this.telegramAuthService.forceUnbindByTelegramId(telegramUserId);
      } catch (Exception var3) {
         log.error("Telegram 用户 {} 退群删号失败后兜底解绑也失败", telegramUserId, var3);
      }
   }

   private boolean isConfiguredTelegramLeaveGroup(Long chatId, TelegramLeaveAutoDeleteSettings settings) {
      TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
      return telegramResponse == null
         ? false
         : settings.monitors(chatId, telegramResponse.getBotChatGroupId(), telegramResponse.getGroupChatId(), telegramResponse.getLibraryNotifyChatId());
   }

   private String getChatMemberStatus(ChatMember chatMember) {
      return chatMember == null ? null : chatMember.getStatus();
   }

   private org.telegram.telegrambots.meta.api.objects.User getChatMemberUser(ChatMember chatMember) {
      return chatMember == null ? null : chatMember.getUser();
   }

   private boolean isActiveMemberStatus(String status) {
      return "creator".equalsIgnoreCase(status)
         || "administrator".equalsIgnoreCase(status)
         || "member".equalsIgnoreCase(status)
         || "restricted".equalsIgnoreCase(status);
   }

   private boolean isLeftStatus(String status) {
      return "left".equalsIgnoreCase(status) || "kicked".equalsIgnoreCase(status);
   }

   private boolean isActiveChatMember(ChatMember chatMember) {
      return chatMember instanceof ChatMemberRestricted restricted
         ? Boolean.TRUE.equals(restricted.getIsMember())
         : this.isActiveMemberStatus(this.getChatMemberStatus(chatMember));
   }

   private boolean isLeftChatMember(ChatMember chatMember) {
      return chatMember instanceof ChatMemberRestricted restricted
         ? Boolean.FALSE.equals(restricted.getIsMember())
         : this.isLeftStatus(this.getChatMemberStatus(chatMember));
   }

   private boolean isUserAdmin(long chatId, long userId) {
      if (chatId == userId) {
         return true;
      } else {
         try {
            GetChatAdministrators getAdmins = new GetChatAdministrators(String.valueOf(chatId));
            getAdmins.setChatId(chatId);

            for (ChatMember admin : this.telegramClient.execute(getAdmins)) {
               if (admin.getUser().getId().equals(userId)) {
                  return true;
               }
            }
         } catch (TelegramApiException var9) {
            log.error("无法获取聊天 {} 的管理员列表: {}", chatId, var9.getMessage());
         }

         return false;
      }
   }

   private boolean requiresAdmin(String command) {
      return this.permissionForCommand(command) != null;
   }

   private boolean hasAdminPermission(Message message) {
      String command = this.extractNormalizedCommand(message == null ? null : message.getText());
      TelegramBotPermission permission = this.permissionForCommand(command);
      return permission != null && this.hasAdminPermission(message, permission, this.isGroupManagementCommand(command));
   }

   private boolean hasAdminPermission(Message message, TelegramBotPermission permission, boolean allowGroup) {
      if (message != null && message.getFrom() != null && message.getFrom().getId() != null) {
         long chatId = message.getChatId();
         long userId = message.getFrom().getId();
         return this.checkAdminPermission(chatId, userId, message.isUserMessage(), permission, allowGroup);
      } else {
         return false;
      }
   }

   private boolean hasAdminPermission(CallbackQuery callbackQuery) {
      TelegramBotPermission permission = this.permissionForCallback(callbackQuery == null ? null : callbackQuery.getData());
      return permission != null && this.hasAdminPermission(callbackQuery, permission);
   }

   private boolean hasAdminPermission(CallbackQuery callbackQuery, TelegramBotPermission permission) {
      return this.hasAdminPermission(callbackQuery, permission, false);
   }

   private boolean hasAdminPermission(CallbackQuery callbackQuery, TelegramBotPermission permission, boolean allowGroup) {
      if (callbackQuery != null && callbackQuery.getMessage() != null && callbackQuery.getFrom() != null) {
         long chatId = callbackQuery.getMessage().getChatId();
         long userId = callbackQuery.getFrom().getId();
         MaybeInaccessibleMessage maybeMessage = callbackQuery.getMessage();
         boolean isPrivateChat = maybeMessage instanceof Message && ((Message)maybeMessage).isUserMessage();
         return this.checkAdminPermission(chatId, userId, isPrivateChat, permission, allowGroup);
      } else {
         return false;
      }
   }

   private boolean isBotOwner(long userId) {
      return this.telegramBotAuthorizationService.isOwner(userId);
   }

   private boolean checkAdminPermission(long chatId, long userId, boolean isPrivateChat, TelegramBotPermission permission, boolean allowGroup) {
      boolean allowedLocation = isPrivateChat || allowGroup && this.telegramBotAuthorizationService.isConfiguredManagementGroup(chatId);
      if (!allowedLocation) {
         return false;
      } else if (!this.telegramBotAuthorizationService.hasPermission(userId, permission)) {
         if (isPrivateChat) {
            this.sendMessage(chatId, "❌ 当前账号没有执行此管理命令的权限。");
         }

         return false;
      } else {
         return true;
      }
   }

   private String extractNormalizedCommand(String text) {
      if (StringUtils.hasText(text) && text.startsWith("/")) {
         String command = text.trim().split("\\s+", 2)[0].toLowerCase(Locale.ROOT);
         int botSuffix = command.indexOf(64);
         return botSuffix > 0 ? command.substring(0, botSuffix) : command;
      } else {
         return "";
      }
   }

   private TelegramBotPermission permissionForCommand(String command) {
      if (!StringUtils.hasText(command)) {
         return null;
      } else {
         String var2 = command.toLowerCase(Locale.ROOT);

         return switch (var2) {
            case "/kk", "/edituser" -> TelegramBotPermission.USER_VIEW;
            case "/whitelist", "/unwhitelist" -> TelegramBotPermission.USER_WHITELIST;
            case "/updateuserinfo", "/enableuser", "/disableuser", "/setexpiry" -> TelegramBotPermission.USER_STATUS;
            case "/panelmute" -> TelegramBotPermission.USER_VIEW;
            case "/resetpassword" -> TelegramBotPermission.USER_PASSWORD;
            case "/createuser" -> TelegramBotPermission.USER_CREATE;
            case "/libraryaccess" -> TelegramBotPermission.LIBRARY_ACCESS;
            case "/generatecards" -> TelegramBotPermission.CARD_MANAGE;
            case "/extendusers" -> TelegramBotPermission.USER_RENEW;
            case "/callall" -> TelegramBotPermission.BROADCAST;
            case "/renew", "/hellvault" -> TelegramBotPermission.POINTS_ADMIN;
            default -> null;
         };
      }
   }

   private TelegramBotPermission permissionForCallback(String data) {
      if (!StringUtils.hasText(data)) {
         return null;
      } else if (data.startsWith("create_server:")) {
         return TelegramBotPermission.USER_CREATE;
      } else if (data.startsWith("card_server:")) {
         return TelegramBotPermission.CARD_MANAGE;
      } else if (data.startsWith("edit_user:")) {
         return TelegramBotPermission.USER_VIEW;
      } else if (data.startsWith("extend_server:")) {
         return TelegramBotPermission.USER_RENEW;
      } else {
         return data.startsWith("lib_access:") ? TelegramBotPermission.LIBRARY_ACCESS : null;
      }
   }

   private boolean isGroupManagementCommand(String command) {
      return "/kk".equals(command) || "/whitelist".equals(command) || "/unwhitelist".equals(command);
   }

   private boolean shouldRejectPrivateMessage(Message message) {
      if (message != null && message.isUserMessage() && message.getFrom() != null && message.getFrom().getId() != null) {
         String reason = this.resolvePrivateChatMembershipRejectReason(message.getFrom().getId());
         if (!StringUtils.hasText(reason)) {
            return false;
         } else {
            this.sendMessage(message.getChatId(), reason);
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean shouldRejectPrivateCallback(CallbackQuery callbackQuery) {
      if (callbackQuery != null
         && callbackQuery.getFrom() != null
         && callbackQuery.getFrom().getId() != null
         && callbackQuery.getMessage() instanceof Message message
         && message.isUserMessage()) {
         String reason = this.resolvePrivateChatMembershipRejectReason(callbackQuery.getFrom().getId());
         if (!StringUtils.hasText(reason)) {
            return false;
         } else {
            this.answerCallbackQuery(callbackQuery.getId(), reason);
            return true;
         }
      } else {
         return false;
      }
   }

   private String resolvePrivateChatMembershipRejectReason(Long telegramUserId) {
      if (telegramUserId != null && !this.telegramBotAuthorizationService.isAdmin(telegramUserId) && this.isPrivateChatMemberRequired()) {
         String pointsChatId = this.resolvePointsChatIdForMembership();
         if (!StringUtils.hasText(pointsChatId)) {
            return "\ud83d\udd12 当前未配置积分群/频道，暂时无法使用机器人。";
         } else {
            return this.isTelegramUserInPointsChat(pointsChatId, telegramUserId) ? null : "\ud83d\udd12 请先加入当前积分群/频道后再使用机器人。";
         }
      } else {
         return null;
      }
   }

   private boolean isPrivateChatMemberRequired() {
      TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
      return telegramResponse == null ? true : !Boolean.FALSE.equals(telegramResponse.getPrivateChatMemberRequired());
   }

   private String resolvePointsChatIdForMembership() {
      TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
      if (telegramResponse == null) {
         return null;
      } else {
         return StringUtils.hasText(telegramResponse.getGroupChatId()) ? telegramResponse.getGroupChatId() : telegramResponse.getBotChatGroupId();
      }
   }

   private boolean isTelegramUserInPointsChat(String pointsChatId, Long telegramUserId) {
      String cacheKey = "bot:telegram:points_group_member:" + pointsChatId + ":" + telegramUserId;
      Object cached = this.redisTemplate.opsForValue().get(cacheKey);
      if (cached != null) {
         return Boolean.parseBoolean(String.valueOf(cached));
      } else {
         boolean member = false;
         if (this.telegramClient == null) {
            log.warn("检查 Telegram 用户是否在积分群失败: Telegram 客户端未初始化");
            this.redisTemplate.opsForValue().set(cacheKey, false, 60L, TimeUnit.SECONDS);
            return false;
         } else {
            try {
               ChatMember chatMember = this.telegramClient.execute(GetChatMember.builder().chatId(pointsChatId).userId(telegramUserId).build());
               member = this.isActiveMemberStatus(this.getChatMemberStatus(chatMember));
               if (member && chatMember instanceof ChatMemberRestricted restricted) {
                  member = !Boolean.FALSE.equals(restricted.getIsMember());
               }
            } catch (TelegramApiException var8) {
               log.warn("检查 Telegram 用户是否在积分群失败: pointsChatId={}, telegramUserId={}, error={}", pointsChatId, telegramUserId, var8.getMessage());
            } catch (Exception var9) {
               log.warn("检查 Telegram 用户是否在积分群异常: pointsChatId={}, telegramUserId={}", pointsChatId, telegramUserId, var9);
            }

            this.redisTemplate.opsForValue().set(cacheKey, member, 60L, TimeUnit.SECONDS);
            return member;
         }
      }
   }

   private void handleTextMessage(Message message) {
      long chatId = message.getChatId();
      if (message.getFrom() != null && message.getFrom().getId() != null) {
         long userId = message.getFrom().getId();
         String text = message.getText();
         if (StringUtils.hasText(text)) {
            String[] parts = text.split("\\s+", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";
            if (!message.isUserMessage() && command.startsWith("@") && StringUtils.hasText(argument)) {
               String mentionedUsername = command;
               String candidateCommand = argument.split("\\s+", 2)[0];
               String normalizedCandidate = candidateCommand.toLowerCase(Locale.ROOT);
               int candidateBotSuffix = normalizedCandidate.indexOf(64);
               if (candidateBotSuffix > 0) {
                  normalizedCandidate = normalizedCandidate.substring(0, candidateBotSuffix);
               }

               if ("/kk".equals(normalizedCandidate)) {
                  command = candidateCommand;
                  argument = mentionedUsername;
               }
            }

            String normalizedCommand = command.toLowerCase();
            if (normalizedCommand.contains("@")) {
               normalizedCommand = normalizedCommand.substring(0, normalizedCommand.indexOf(64));
            }

            if (this.shouldCleanupFoamGroupCommand(command, normalizedCommand)) {
               this.scheduleGroupCommandCleanup(message);
            }

            if (!this.requiresAdmin(normalizedCommand) || this.hasAdminPermission(message)) {
               switch (normalizedCommand) {
                  case "/start":
                     if (argument.startsWith("kkreg_")) {
                        this.handleKkRegistrationStart(message, argument.substring("kkreg_".length()));
                     } else if (StringUtils.hasText(argument) || !this.handlePendingKkRegistrationStart(message)) {
                        if (argument.startsWith("bind_")) {
                           this.handleBindCommand(message, argument);
                        } else if (argument.startsWith("login_")) {
                           this.handleLoginCommand(message, argument);
                        } else if (!argument.startsWith("brain_")) {
                           this.handleStartPanel(message);
                        }
                     }
                     break;
                  case "/myaccount":
                     this.handleMyAccountCommand(message);
                     break;
                  case "/mylines":
                     this.handleMyLinesCommand(message);
                     break;
                  case "/bind":
                     this.handleCredentialBindCommand(message, argument);
                     break;
                  case "/unbind":
                     this.handleTelegramUnbindCommand(message);
                     break;
                  case "/cancelreview":
                     this.handleTelegramCancelReviewCommand(message, argument);
                     break;
                  case "/register":
                     this.handleTelegramRegisterCommand(message, argument);
                     break;
                  case "/cardopen":
                     this.handleTelegramCardOpenCommand(message, argument);
                     break;
                  case "/cardrenew":
                     this.handleTelegramCardRenewCommand(message, argument);
                     break;
                  case "/search":
                  case "/request":
                     if (!message.isUserMessage()) {
                        this.sendMessage(chatId, "\ud83d\udd12 求片只支持私聊机器人，请私聊发送 `/request 片名`。");
                        return;
                     }

                     if (argument.isEmpty()) {
                        this.sendMessage(chatId, "\ud83d\udd0d 请输入要搜索的关键词。\n例如：`/request 瞬息全宇宙`");
                        return;
                     }

                     if (this.telegramAuthService.findBoundUser(userId) == null) {
                        this.sendBindRequiredMessage(chatId);
                        return;
                     }

                     if (!this.tryAcquireRateLimit("search:" + userId, 10L, 60L)) {
                        this.sendMessage(chatId, "⏳ 搜索太频繁了，请稍后再试。");
                        return;
                     }

                     try {
                        TmdbResponse tmdbResponse = this.tmdbService.searchDataTelegram(argument, 1);
                        if (tmdbResponse != null && tmdbResponse.getResults() != null && !tmdbResponse.getResults().isEmpty()) {
                           List<TmdbResponse.Result> validResults = tmdbResponse.getResults()
                              .stream()
                              .filter(
                                 r -> r.getPosterPath() != null
                                       && !r.getPosterPath().isEmpty()
                                       && r.getId() != 0
                                       && r.getMediaType() != null
                                       && (r.getTitle() != null || r.getName() != null)
                              )
                              .collect(Collectors.toList());
                           if (validResults.isEmpty()) {
                              this.sendMessage(chatId, "\ud83e\udded 未找到可供显示的有效结果，请尝试其他关键词。");
                              return;
                           }

                           this.saveSearchResults(chatId, validResults);
                           this.sendOrEditRequestCard(chatId, null, 0);
                           break;
                        }

                        this.sendMessage(chatId, "\ud83d\udd0e 未找到关于 `" + this.escapeMarkdown(argument) + "` 的任何结果。");
                        return;
                     } catch (Exception var15) {
                        log.error("TMDB API 搜索失败: {}", var15.getMessage());
                        this.sendMessage(chatId, "❌ 搜索 `" + this.escapeMarkdown(argument) + "` 失败，请检查网络或稍后重试。");
                        break;
                     }
                  case "/createuser":
                     this.handleCreateUserCommand(message, argument);
                     break;
                  case "/libraryaccess":
                     this.handleLibraryAccessCommand(message);
                     break;
                  case "/generatecards":
                     this.handleGenerateCardsCommand(message, argument);
                     break;
                  case "/extendusers":
                     this.handleExtendUsersCommand(message, argument);
                     break;
                  case "/edituser":
                     this.handleEditUserCommand(message, argument);
                     break;
                  case "/kk":
                     this.handleKkCommand(message, argument);
                     break;
                  case "/whitelist":
                     this.handleWhitelistCommand(message, argument, true);
                     break;
                  case "/unwhitelist":
                     this.handleWhitelistCommand(message, argument, false);
                     break;
                  case "/callall":
                     this.handleCallAllCommand(message);
                     break;
                  case "/updateuserinfo":
                     this.handleUpdateUserInfoCommand(message, argument);
                     break;
                  case "/resetpassword":
                     this.handleResetPasswordCommand(message, argument);
                     break;
                  case "/enableuser":
                     this.handleEnableUserCommand(message);
                     break;
                  case "/disableuser":
                     this.handleDisableUserCommand(message);
                     break;
                  case "/setexpiry":
                     this.handleSetExpiryCommand(message, argument);
               }
            }
         }
      } else {
         log.warn("忽略缺少发送者信息的 Telegram 文本消息: chatId={}, messageId={}", chatId, message.getMessageId());
      }
   }

   private boolean shouldCleanupFoamGroupCommand(String command, String normalizedCommand) {
      if (!FOAM_GROUP_COMMANDS.contains(normalizedCommand)) {
         return false;
      } else {
         int botSuffix = command.indexOf(64);
         if (botSuffix < 0) {
            return true;
         } else {
            String addressedBotName = command.substring(botSuffix + 1).trim();
            TelegramResponse telegram = this.telegramClientUtils == null ? null : this.telegramClientUtils.getTelegramResponse();
            String configuredBotName = telegram == null ? null : telegram.getBotName();
            return StringUtils.hasText(addressedBotName) && StringUtils.hasText(configuredBotName)
               ? addressedBotName.equalsIgnoreCase(configuredBotName.replaceFirst("^@", ""))
               : false;
         }
      }
   }

   private void handleCallAllCommand(Message message) {
      if (this.hasAdminPermission(message)) {
         long chatId = message.getChatId();
         if (message.isUserMessage()) {
            long userId = message.getFrom().getId();
            this.pendingAnnouncementBroadcasts.put(userId, DataQueryBot.PendingAnnouncementBroadcast.waitingMessage(chatId));
            this.sendMessage(chatId, "**\ud83d\udce2 批量公告**\n\n请在 10 分钟内发送公告内容。收到后我会让你选择接收范围；退出请输入 /cancel");
         }
      }
   }

   private void handleStartPanel(Message message) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      TelegramResponse telegram = this.telegramClientUtils.getTelegramResponse();
      if (!message.isUserMessage()) {
         String botName = telegram == null ? null : telegram.getBotName();
         if (!StringUtils.hasText(botName)) {
            this.sendMessage(chatId, "请私聊机器人发送 `/start` 打开服务面板。");
         } else {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
               .keyboard(
                  List.of(new InlineKeyboardRow(InlineKeyboardButton.builder().text("\ud83d\udfe0 打开 Mist 私聊面板").url(this.buildPrivateBotUrl(botName)).build()))
               )
               .build();
            this.sendKeyboardMessage(chatId, "为了保持群聊整洁，完整服务面板仅在私聊中显示。", keyboard);
         }
      } else {
         this.pendingPanelCommands.remove(userId);
         boolean owner = this.isBotOwner(userId);
         boolean admin = this.telegramBotAuthorizationService.isAdmin(userId);
         if (admin) {
            this.refreshCommandMenuForAdmin(userId);
         }

         String text = this.buildStartPanelHomeText(owner, admin);
         InlineKeyboardMarkup keyboard = this.buildStartPanelHomeKeyboard(admin);
         if (!this.sendStartPanelPhoto(chatId, text, keyboard)) {
            this.sendKeyboardMessage(chatId, text, keyboard);
         }
      }
   }

   private String buildStartPanelHomeText(boolean owner, boolean admin) {
      String role = owner ? "黑桃C · 全部权限" : (admin ? "管理员 · 按授权执行" : "Mist 用户");
      return this.foamPanelTitle("服务中心") + "身份：*" + role + "*";
   }

   private InlineKeyboardMarkup buildStartPanelHomeKeyboard(boolean admin) {
      List<InlineKeyboardRow> rows = new ArrayList<>();
      rows.add(
         new InlineKeyboardRow(
            InlineKeyboardButton.builder().text("\ud83d\udc64 我的账号").callbackData("start_panel:account").build(),
            InlineKeyboardButton.builder().text("\ud83d\udce1 可用线路").callbackData("start_panel:lines").build()
         )
      );
      rows.add(
         new InlineKeyboardRow(
            InlineKeyboardButton.builder().text("\ud83c\udfac 求片中心").callbackData("start_panel:request").build(),
            InlineKeyboardButton.builder().text("\ud83e\ude99 积分中心").callbackData("start_panel:points").build()
         )
      );
      rows.add(
         new InlineKeyboardRow(
            InlineKeyboardButton.builder().text("✅ 每日签到").callbackData("start_action:checkin").build(),
            InlineKeyboardButton.builder().text("\ud83d\udd17 绑定与卡密").callbackData("start_panel:binding").build()
         )
      );
      if (admin) {
         rows.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text("\ud83d\udee1️ 管理中心").callbackData("start_panel:admin").build()));
      }

      return InlineKeyboardMarkup.builder().keyboard(rows).build();
   }

   private boolean sendStartPanelPhoto(long chatId, String caption, InlineKeyboardMarkup keyboard) {
      File customImage = this.resolveConfiguredStartPanelImage();
      if (customImage != null) {
         try {
            this.sendStartPanelPhotoFile(chatId, caption, keyboard, customImage);
            return true;
         } catch (Exception var8) {
            log.warn("发送自定义 Telegram 首页图片失败，尝试内置默认图: chatId={}, error={}", chatId, var8.getMessage());
         }
      }

      try {
         File logoFile = this.resolveStartPanelLogoFile();
         this.sendStartPanelPhotoFile(chatId, caption, keyboard, logoFile);
         return true;
      } catch (Exception var7) {
         log.warn("发送内置 Telegram 首页图片失败，降级为文本面板: chatId={}, error={}", chatId, var7.getMessage());
         return false;
      }
   }

   private File resolveConfiguredStartPanelImage() {
      TelegramResponse telegram = this.telegramClientUtils.getTelegramResponse();
      return telegram != null && StringUtils.hasText(telegram.getStartPanelImage())
         ? this.telegramStartPanelImageStorage.resolve(telegram.getStartPanelImage())
         : null;
   }

   private void sendStartPanelPhotoFile(long chatId, String caption, InlineKeyboardMarkup keyboard, File image) throws TelegramApiException {
      Message sentMessage = this.telegramClient
         .execute(SendPhoto.builder().chatId(chatId).photo(new InputFile(image)).caption(caption).parseMode("Markdown").replyMarkup(keyboard).build());
      this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
   }

   private File resolveStartPanelLogoFile() throws IOException {
      File cached = this.startPanelLogoFile;
      if (cached != null && cached.isFile() && cached.length() > 0L) {
         return cached;
      } else {
         synchronized (this) {
            cached = this.startPanelLogoFile;
            if (cached != null && cached.isFile() && cached.length() > 0L) {
               return cached;
            } else {
               File var5;
               try (InputStream input = DataQueryBot.class.getResourceAsStream("/img/mist.png")) {
                  if (input == null) {
                     throw new IOException("classpath 中未找到 /img/mist.png");
                  }

                  File extracted = Files.createTempFile("foam-telegram-logo-", ".png").toFile();
                  Files.copy(input, extracted.toPath(), StandardCopyOption.REPLACE_EXISTING);
                  extracted.deleteOnExit();
                  this.startPanelLogoFile = extracted;
                  var5 = extracted;
               }

               return var5;
            }
         }
      }
   }

   private void handleKkCommand(Message message, String argument) {
      if (this.hasAdminPermission(message, TelegramBotPermission.USER_VIEW, true)) {
         long operatorId = message.getFrom().getId();
         if (!this.tryAcquireRateLimit("admin_panel:" + operatorId, 20L, 60L)) {
            this.sendMessage(message.getChatId(), "⏳ 管理面板打开过于频繁，请稍后再试。");
         } else {
            DataQueryBot.TelegramKkTarget telegramTarget = this.resolveKkTelegramTarget(message, argument);
            if (telegramTarget != null) {
               if (this.isKkProtectedTelegramTarget(telegramTarget.getTelegramUserId())) {
                  this.sendMessage(message.getChatId(), "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
               } else {
                  EmbyUser boundUser = this.telegramBindingManager.findBoundUser(telegramTarget.getTelegramUserId(), false);
                  if (boundUser == null) {
                     this.renderAdminTelegramMemberPanel(operatorId, telegramTarget, null, message.getChatId(), null);
                  } else if (this.isKkProtectedEmbyTarget(boundUser)) {
                     this.sendMessage(message.getChatId(), "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
                  } else {
                     this.pendingUserEdits.put(operatorId, new DataQueryBot.PendingUserEdit(operatorId, boundUser.getId()));
                     boolean delivered = this.sendAdminUserPanelToChat(operatorId, boundUser, message.getChatId(), true);
                     if (!delivered) {
                        this.pendingUserEdits.remove(operatorId);
                        this.sendMessage(message.getChatId(), "❌ 用户管理面板暂时没有展开，请稍后再试。");
                     }
                  }
               }
            } else {
               EmbyUser target = this.resolveAdminCommandTarget(message, argument, false);
               if (target != null && this.isKkProtectedEmbyTarget(target)) {
                  this.sendMessage(message.getChatId(), "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
               } else if (target != null && this.canBotOperatorViewTarget(operatorId, target)) {
                  this.pendingUserEdits.put(operatorId, new DataQueryBot.PendingUserEdit(operatorId, target.getId()));
                  boolean delivered = message.isUserMessage()
                     ? this.sendAdminUserPanel(operatorId, target)
                     : this.sendAdminUserPanelToChat(operatorId, target, message.getChatId(), true);
                  if (!delivered) {
                     this.pendingUserEdits.remove(operatorId);
                     this.sendMessage(message.getChatId(), "❌ 用户管理面板暂时没有展开，请稍后再试。");
                  }
               } else {
                  this.sendMessage(
                     message.getChatId(), "❌ 未找到可管理的目标。群聊可回复任意成员发送 `/kk`，也可发送 `/kk @Telegram用户名` 或 `@Telegram用户名 /kk`；私聊可填写 Telegram ID 或 Emby 用户名。"
                  );
               }
            }
         }
      }
   }

   private DataQueryBot.TelegramKkTarget resolveKkTelegramTarget(Message message, String argument) {
      if (message != null && !message.isUserMessage()) {
         Message replied = message.getReplyToMessage();
         if (replied != null && replied.getFrom() != null && replied.getFrom().getId() != null && !Boolean.TRUE.equals(replied.getFrom().getIsBot())) {
            return DataQueryBot.TelegramKkTarget.from(replied.getFrom());
         } else {
            Long mentionedUserId = this.resolveTextMentionedTelegramUserId(message);
            if (mentionedUserId != null) {
               return new DataQueryBot.TelegramKkTarget(mentionedUserId, mentionedUserId.toString(), null);
            } else if (!StringUtils.hasText(argument)) {
               return null;
            } else {
               String target = argument.trim();
               if (target.startsWith("@")) {
                  Long resolvedTelegramUserId = this.telegramClient == null ? null : this.telegramClient.resolvePublicUserId(target);
                  return resolvedTelegramUserId == null ? null : new DataQueryBot.TelegramKkTarget(resolvedTelegramUserId, target, target.substring(1));
               } else {
                  try {
                     long telegramUserId = Long.parseLong(target);
                     return telegramUserId > 0L ? new DataQueryBot.TelegramKkTarget(telegramUserId, target, null) : null;
                  } catch (NumberFormatException var8) {
                     return null;
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   private void handleWhitelistCommand(Message message, String argument, boolean whitelist) {
      if (this.hasAdminPermission(message, TelegramBotPermission.USER_WHITELIST, true)) {
         String targetArgument = argument == null ? "" : argument.trim();
         Integer ordinaryDays = null;
         if (!whitelist) {
            String[] parts = targetArgument.split("\\s+");
            String daysText;
            if (!message.isUserMessage() && message.getReplyToMessage() != null) {
               daysText = parts.length == 0 ? "" : parts[parts.length - 1];
               targetArgument = "";
            } else {
               if (parts.length < 2) {
                  this.sendMessage(message.getChatId(), "用法：私聊 `/unwhitelist <Telegram ID或Emby用户名> <有效天数>`；群聊回复用户 `/unwhitelist <有效天数>`。");
                  return;
               }

               daysText = parts[parts.length - 1];
               targetArgument = String.join(" ", Arrays.copyOf(parts, parts.length - 1)).trim();
            }

            try {
               ordinaryDays = Integer.parseInt(daysText);
            } catch (NumberFormatException var11) {
               this.sendMessage(message.getChatId(), "❌ 有效天数必须是整数。");
               return;
            }
         }

         EmbyUser target = this.resolveAdminCommandTarget(message, targetArgument, true);
         long operatorId = message.getFrom().getId();
         if (target != null && this.canBotOperatorViewTarget(operatorId, target)) {
            if (!this.tryAcquireRateLimit("admin_mutation:" + operatorId, 30L, 60L)) {
               this.sendMessage(message.getChatId(), "⏳ 管理操作过于频繁，请稍后再试。");
            } else {
               try {
                  this.embyUserService.updateUserWhitelistByBot(target.getId(), whitelist, ordinaryDays, this.isBotOwner(operatorId));
                  log.info(
                     "Telegram 管理操作完成: operatorId={}, targetUserId={}, action={}", operatorId, target.getId(), whitelist ? "WHITELIST_ADD" : "WHITELIST_REMOVE"
                  );
                  String result = whitelist
                     ? "\ud83c\udf01 *一层薄雾越过潮线*\n\n`"
                        + this.escapeMarkdown(target.getEmbyUserName())
                        + "` 已由 "
                        + this.telegramUserMention(message.getFrom())
                        + " 送入白名单，全部线路随光展开。"
                     : "\ud83c\udf0a *潮汐轻落，薄雾回到日常航道*\n\n`"
                        + this.escapeMarkdown(target.getEmbyUserName())
                        + "` 已由 "
                        + this.telegramUserMention(message.getFrom())
                        + " 移出白名单，普通账号有效期重置为 "
                        + ordinaryDays
                        + " 天。";
                  if (message.isUserMessage()) {
                     this.sendMessage(message.getChatId(), result);
                  } else {
                     this.sendPersistentMessage(
                        message.getChatId(), whitelist ? this.buildWhitelistCelebrationMessage(message.getChatId(), target, message.getFrom()) : result
                     );
                  }
               } catch (BizException var10) {
                  this.sendMessage(message.getChatId(), "❌ 操作失败：" + var10.getMessage());
               }
            }
         } else {
            this.sendMessage(message.getChatId(), "❌ 未找到可管理的绑定用户。");
         }
      }
   }

   private EmbyUser resolveAdminCommandTarget(Message message, String argument, boolean requireReplyInGroup) {
      if (!message.isUserMessage()) {
         Message replied = message.getReplyToMessage();
         if (replied != null && replied.getFrom() != null && replied.getFrom().getId() != null && !Boolean.TRUE.equals(replied.getFrom().getIsBot())) {
            return this.telegramBindingManager.findBoundUser(replied.getFrom().getId(), false);
         }

         Long mentionedUserId = this.resolveTextMentionedTelegramUserId(message);
         if (mentionedUserId != null) {
            EmbyUser mentionedUser = this.telegramBindingManager.findBoundUser(mentionedUserId, false);
            if (mentionedUser != null) {
               return mentionedUser;
            }
         }

         if (!StringUtils.hasText(argument)) {
            return null;
         }
      }

      if (!StringUtils.hasText(argument)) {
         return null;
      } else {
         String target = argument.trim();
         if (target.startsWith("@")) {
            EmbyUser byUsername = this.telegramBindingManager.findBoundUserByTelegramUsername(target, false);
            if (byUsername != null) {
               return byUsername;
            } else {
               Long resolvedTelegramUserId = this.telegramClient == null ? null : this.telegramClient.resolvePublicUserId(target);
               return resolvedTelegramUserId == null ? null : this.telegramBindingManager.findBoundUser(resolvedTelegramUserId, false);
            }
         } else {
            try {
               long telegramId = Long.parseLong(target);
               EmbyUser bound = this.telegramBindingManager.findBoundUser(telegramId, false);
               if (bound != null) {
                  return bound;
               }
            } catch (NumberFormatException var8) {
            }

            List<EmbyUser> matches = this.embyUserService.lambdaQuery().eq(EmbyUser::getEmbyUserName, target).last("limit 2").list();
            return matches.size() == 1 ? matches.get(0) : null;
         }
      }
   }

   private Long resolveTextMentionedTelegramUserId(Message message) {
      if (message != null && !CollectionUtils.isEmpty(message.getEntities())) {
         for (MessageEntity entity : message.getEntities()) {
            if ("text_mention".equals(entity.getType())
               && entity.getUser() != null
               && entity.getUser().getId() != null
               && !Boolean.TRUE.equals(entity.getUser().getIsBot())) {
               return entity.getUser().getId();
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean canBotOperatorViewTarget(long operatorId, EmbyUser target) {
      return target == null
         ? false
         : this.isBotOwner(operatorId) || !Integer.valueOf(1).equals(target.getIsAdmin()) && !Integer.valueOf(1).equals(target.getIsPrimaryAdmin());
   }

   private boolean isKkProtectedEmbyTarget(EmbyUser target) {
      if (target == null) {
         return false;
      } else if (!Integer.valueOf(1).equals(target.getIsAdmin()) && !Integer.valueOf(1).equals(target.getIsPrimaryAdmin())) {
         Long boundTelegramUserId = this.resolveBoundTelegramUserId(target);
         return boundTelegramUserId != null && this.isKkProtectedTelegramTarget(boundTelegramUserId);
      } else {
         return true;
      }
   }

   private boolean isKkProtectedTelegramTarget(long telegramUserId) {
      return this.telegramBotAuthorizationService.isOwner(telegramUserId) || this.telegramBotAuthorizationService.isConfiguredAdmin(telegramUserId);
   }

   private boolean canAuthorizedAdminModerateGroup(long operatorId) {
      return this.telegramBotAuthorizationService.isAdmin(operatorId)
         && this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_VIEW);
   }

   private boolean sendAdminUserPanel(long operatorId, EmbyUser target) {
      return this.renderAdminUserPanel(operatorId, target, null, null);
   }

   private boolean sendAdminUserPanelToChat(long operatorId, EmbyUser target, long destinationChatId, boolean publicPanel) {
      return this.renderAdminUserPanel(operatorId, target, null, null, destinationChatId, publicPanel);
   }

   private boolean renderAdminUserPanel(long operatorId, EmbyUser target, Message panelMessage, String notice) {
      long destinationChatId = panelMessage == null ? operatorId : panelMessage.getChatId();
      boolean publicPanel = panelMessage != null && !panelMessage.isUserMessage();
      return this.renderAdminUserPanel(operatorId, target, panelMessage, notice, destinationChatId, publicPanel);
   }

   private boolean renderAdminUserPanel(long operatorId, EmbyUser target, Message panelMessage, String notice, long destinationChatId, boolean publicPanel) {
      if (target != null && this.isKkProtectedEmbyTarget(target)) {
         if (panelMessage == null) {
            this.sendMessage(destinationChatId, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
         } else {
            this.editStartPanelMessage(panelMessage, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
         }

         return false;
      } else if (target != null && this.canBotOperatorViewTarget(operatorId, target)) {
         String token = UUID.randomUUID().toString().replace("-", "");
         this.stringRedisTemplate.opsForValue().set("bot:admin:panel:" + token, operatorId + ":" + target.getId(), 10L, TimeUnit.MINUTES);
         EmbyInfo server = target.getEmbyInfoId() == null ? null : this.embyInfoService.getById(target.getEmbyInfoId());
         boolean whitelist = HostLineTypeEnum.normalize(target.getHostLineType()) == HostLineTypeEnum.WHITELIST.getCode();
         boolean canManageWhitelist = this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_WHITELIST);
         String expiration = whitelist
            ? "长期有效"
            : (
               target.getExpirationDate() == null
                  ? "未设置"
                  : target.getExpirationDate()
                     .toInstant()
                     .atZone(ZoneId.systemDefault())
                     .toLocalDateTime()
                     .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            );
         String text = this.foamPanelTitle("用户管理")
            + "账号：`"
            + this.escapeMarkdown(target.getEmbyUserName())
            + "`\n服务器："
            + this.escapeMarkdown(this.buildServerLabel(server))
            + "\n状态："
            + (Integer.valueOf(1).equals(target.getUserStatus()) ? "\ud83d\udd34 已禁用" : "\ud83d\udfe2 正常")
            + "\n线路："
            + (whitelist ? "白名单 · 全部线路" : "普通线路")
            + "\n有效期："
            + expiration
            + "\n求片额度："
            + (target.getRequestPackagesCount() == null ? 0 : target.getRequestPackagesCount());
         if (whitelist && canManageWhitelist) {
            text = text + "\n\n↩️ 移出白名单并重置有效期：";
         }

         if (publicPanel) {
            text = text + "\n\n\ud83c\udf01 [管理员](tg://user?id=" + operatorId + ") 拨开这层账号薄雾，操作会在这里留下微光。";
         }

         if (StringUtils.hasText(notice)) {
            text = text + "\n\n" + notice;
         }

         List<InlineKeyboardRow> rows = new ArrayList<>();
         List<InlineKeyboardButton> publicActionButtons = new ArrayList<>();
         if (this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_STATUS)) {
            InlineKeyboardButton statusButton = InlineKeyboardButton.builder()
               .text(Integer.valueOf(1).equals(target.getUserStatus()) ? "\ud83d\udfe2 启用用户" : "\ud83d\udd34 禁用用户")
               .callbackData("admin_user:" + token + ":" + (Integer.valueOf(1).equals(target.getUserStatus()) ? "enable" : "disable"))
               .build();
            if (publicPanel) {
               publicActionButtons.add(statusButton);
            } else {
               rows.add(new InlineKeyboardRow(statusButton));
            }

            if (!publicPanel && !whitelist) {
               rows.add(
                  new InlineKeyboardRow(
                     InlineKeyboardButton.builder().text("\ud83d\udcdd 改期限/额度").callbackData("admin_user:" + token + ":update").build(),
                     InlineKeyboardButton.builder().text("⏰ 改期并启用").callbackData("admin_user:" + token + ":expiry").build()
                  )
               );
            }
         }

         if (!publicPanel && this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_PASSWORD)) {
            rows.add(
               new InlineKeyboardRow(InlineKeyboardButton.builder().text("\ud83d\udd11 重置用户密码").callbackData("admin_user:" + token + ":password").build())
            );
         }

         if (canManageWhitelist) {
            if (whitelist) {
               rows.add(this.buildWhitelistRemovalRow(token));
            } else {
               InlineKeyboardButton whitelistButton = InlineKeyboardButton.builder().text("✅ 加入白名单").callbackData("admin_user:" + token + ":whitelist").build();
               if (publicPanel) {
                  publicActionButtons.add(whitelistButton);
               } else {
                  rows.add(new InlineKeyboardRow(whitelistButton));
               }
            }
         }

         Long boundTelegramUserId = this.resolveBoundTelegramUserId(target);
         boolean canModerateTelegramMember = publicPanel
            && boundTelegramUserId != null
            && boundTelegramUserId != operatorId
            && !this.isKkProtectedTelegramTarget(boundTelegramUserId)
            && this.canAuthorizedAdminModerateGroup(operatorId);
         if (canModerateTelegramMember) {
            boolean muted = this.isGroupMemberMuted(destinationChatId, boundTelegramUserId);
            publicActionButtons.add(
               InlineKeyboardButton.builder()
                  .text(muted ? "\ud83d\udd0a 解除禁言" : "\ud83d\udd07 禁言用户")
                  .callbackData("admin_user:" + token + ":" + (muted ? "unmute" : "mute"))
                  .build()
            );
            publicActionButtons.add(InlineKeyboardButton.builder().text("\ud83d\udeab 踢出并封禁").callbackData("admin_user:" + token + ":kickban").build());
         }

         for (int index = 0; index < publicActionButtons.size(); index += 2) {
            InlineKeyboardButton first = publicActionButtons.get(index);
            rows.add(index + 1 < publicActionButtons.size() ? new InlineKeyboardRow(first, publicActionButtons.get(index + 1)) : new InlineKeyboardRow(first));
         }

         rows.add(
            new InlineKeyboardRow(
               InlineKeyboardButton.builder().text("\ud83d\udd04 刷新").callbackData("admin_user:" + token + ":refresh").build(),
               InlineKeyboardButton.builder()
                  .text(publicPanel ? "❌ 收起面板" : "\ud83d\udee1️ 管理中心")
                  .callbackData("admin_user:" + token + ":" + (publicPanel ? "close" : "back"))
                  .build()
            )
         );
         InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(rows).build();
         if (panelMessage == null) {
            return publicPanel
               ? this.sendExpiringAdminKeyboardMessage(destinationChatId, text, keyboard)
               : this.sendKeyboardMessage(destinationChatId, text, keyboard);
         } else {
            this.editStartPanelMessage(panelMessage, text, keyboard);
            return true;
         }
      } else {
         String textx = this.foamPanelTitle("用户管理") + "❌ 该用户不存在或不可操作。";
         if (panelMessage == null) {
            this.sendMessage(destinationChatId, textx);
         } else {
            this.editStartPanelMessage(panelMessage, textx, this.keyboard(List.of(this.button("\ud83d\udee1️ 返回管理中心", "start_panel:admin")), 1));
         }

         return false;
      }
   }

   private Long resolveBoundTelegramUserId(EmbyUser target) {
      if (target != null && target.getId() != null) {
         UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(target.getId());
         return binding == null ? null : this.parseTelegramUserId(binding.getProviderUserId());
      } else {
         return null;
      }
   }

   private boolean isGroupMemberMuted(long chatId, long telegramUserId) {
      try {
         ChatMember member = this.telegramClient.execute(GetChatMember.builder().chatId(chatId).userId(telegramUserId).build());
         if (member instanceof ChatMemberRestricted restricted && Boolean.FALSE.equals(restricted.getCanSendMessages())) {
            return true;
         }

         return false;
      } catch (TelegramApiException var7) {
         log.warn("读取 Telegram 群成员禁言状态失败: chatId={}, telegramUserId={}, message={}", chatId, telegramUserId, var7.getMessage());
         return false;
      } catch (Exception var8) {
         log.warn("读取 Telegram 群成员禁言状态异常: chatId={}, telegramUserId={}", chatId, telegramUserId, var8);
         return false;
      }
   }

   private boolean renderAdminTelegramMemberPanel(
      long operatorId, DataQueryBot.TelegramKkTarget target, Message panelMessage, long destinationChatId, String notice
   ) {
      if (target == null || target.getTelegramUserId() <= 0L) {
         return false;
      } else if (this.isKkProtectedTelegramTarget(target.getTelegramUserId())) {
         if (panelMessage == null) {
            this.sendMessage(destinationChatId, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
         } else {
            this.editStartPanelMessage(panelMessage, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
         }

         return false;
      } else {
         EmbyUser boundUser = this.telegramBindingManager.findBoundUser(target.getTelegramUserId(), false);
         if (boundUser == null) {
            String sessionToken = UUID.randomUUID().toString().replace("-", "");
            this.stringRedisTemplate
               .opsForValue()
               .set("bot:admin:telegram-panel:" + sessionToken, operatorId + ":" + target.getTelegramUserId(), 10L, TimeUnit.MINUTES);
            String text = this.foamPanelTitle("群成员")
               + "成员："
               + this.telegramUserMention(target.getTelegramUserId(), target.getDisplayName())
               + "\nTelegram ID：`"
               + target.getTelegramUserId()
               + "`\n账号状态：尚未关联 Mist 账号";
            if (StringUtils.hasText(notice)) {
               text = text + "\n\n" + notice;
            }

            List<InlineKeyboardRow> rows = new ArrayList<>();
            if (this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_CREATE)) {
               TelegramResponse telegram = this.telegramClientUtils.getTelegramResponse();
               String botName = telegram == null ? null : telegram.getBotName();
               if (StringUtils.hasText(botName)) {
                  rows.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text("✨ 创建账号").callbackData("admin_tg:" + sessionToken + ":gift").build()));
               }
            }

            if (this.canAuthorizedAdminModerateGroup(operatorId)) {
               boolean muted = this.isGroupMemberMuted(destinationChatId, target.getTelegramUserId());
               rows.add(
                  new InlineKeyboardRow(
                     InlineKeyboardButton.builder()
                        .text(muted ? "\ud83d\udd0a 解除禁言" : "\ud83d\udd07 禁言用户")
                        .callbackData("admin_tg:" + sessionToken + ":" + (muted ? "unmute" : "mute"))
                        .build(),
                     InlineKeyboardButton.builder().text("\ud83d\udeab 踢出并封禁").callbackData("admin_tg:" + sessionToken + ":kickban").build()
                  )
               );
            }

            rows.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text("❌ 收起面板").callbackData("admin_tg:" + sessionToken + ":close").build()));
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(rows).build();
            if (panelMessage == null) {
               return this.sendExpiringAdminKeyboardMessage(destinationChatId, text, keyboard);
            } else {
               this.editStartPanelMessage(panelMessage, text, keyboard);
               return true;
            }
         } else if (this.isKkProtectedEmbyTarget(boundUser)) {
            if (panelMessage == null) {
               this.sendMessage(destinationChatId, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
            } else {
               this.editStartPanelMessage(panelMessage, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
            }

            return false;
         } else {
            return panelMessage == null
               ? this.sendAdminUserPanelToChat(operatorId, boundUser, destinationChatId, true)
               : this.renderAdminUserPanel(operatorId, boundUser, panelMessage, notice);
         }
      }
   }

   private String createKkRegistrationGrant(long operatorId, long targetTelegramUserId) {
      String token = UUID.randomUUID().toString().replace("-", "");
      String targetGrantKey = "bot:kk:register:target:" + targetTelegramUserId;
      String previousToken = this.stringRedisTemplate.opsForValue().get(targetGrantKey);
      if (StringUtils.hasText(previousToken)) {
         this.stringRedisTemplate.delete("bot:kk:register:" + previousToken);
      }

      this.stringRedisTemplate.opsForValue().set("bot:kk:register:" + token, operatorId + ":" + targetTelegramUserId, 30L, TimeUnit.MINUTES);
      this.stringRedisTemplate.opsForValue().set(targetGrantKey, token, 30L, TimeUnit.MINUTES);
      return token;
   }

   private InlineKeyboardRow buildWhitelistRemovalRow(String token) {
      return new InlineKeyboardRow(
         InlineKeyboardButton.builder().text("↩️ 7天").callbackData("admin_user:" + token + ":unwhite7").build(),
         InlineKeyboardButton.builder().text("\ud83d\udcc5 30天").callbackData("admin_user:" + token + ":unwhite30").build(),
         InlineKeyboardButton.builder().text("\ud83d\uddd3️ 90天").callbackData("admin_user:" + token + ":unwhite90").build()
      );
   }

   private boolean sendKeyboardMessage(long chatId, String text, InlineKeyboardMarkup keyboard) {
      try {
         Message sentMessage = this.telegramClient.execute(SendMessage.builder().chatId(chatId).text(text).parseMode("Markdown").replyMarkup(keyboard).build());
         this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
         return true;
      } catch (TelegramApiException var6) {
         log.warn("发送 Telegram 面板失败: chatId={}, error={}", chatId, var6.getMessage());
         return false;
      }
   }

   private boolean sendExpiringAdminKeyboardMessage(long chatId, String text, InlineKeyboardMarkup keyboard) {
      try {
         Message sentMessage = this.telegramClient.execute(SendMessage.builder().chatId(chatId).text(text).parseMode("Markdown").replyMarkup(keyboard).build());
         this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId(), 10L, TimeUnit.MINUTES);
         return true;
      } catch (TelegramApiException var6) {
         log.warn("发送 Telegram 限时管理面板失败: chatId={}, error={}", chatId, var6.getMessage());
         return false;
      }
   }

   private String buildPrivateBotUrl(String botName) {
      return this.buildPrivateBotUrl(botName, "panel");
   }

   private String buildPrivateBotUrl(String botName, String startParameter) {
      String normalizedBotName = botName.startsWith("@") ? botName.substring(1) : botName;
      return "https://t.me/" + normalizedBotName + "?start=" + startParameter;
   }

   private boolean handlePendingAnnouncementBroadcast(Message message) {
      if (message != null && message.getFrom() != null && message.getFrom().getId() != null && message.isUserMessage()) {
         long userId = message.getFrom().getId();
         DataQueryBot.PendingAnnouncementBroadcast pending = this.pendingAnnouncementBroadcasts.get(userId);
         if (pending == null) {
            return false;
         } else if (!this.hasAdminPermission(message, TelegramBotPermission.BROADCAST, false)) {
            this.pendingAnnouncementBroadcasts.remove(userId);
            return true;
         } else {
            long chatId = message.getChatId();
            if (pending.isExpired()) {
               this.pendingAnnouncementBroadcasts.remove(userId);
               this.sendMessage(chatId, "批量公告已超时，请重新发送 /callall。");
               return true;
            } else {
               String text = message.hasText() ? message.getText().trim() : "";
               String normalizedCommand = this.normalizeBotCommand(text);
               if ("/cancel".equals(normalizedCommand)) {
                  this.pendingAnnouncementBroadcasts.remove(userId);
                  this.sendMessage(chatId, "已取消本次批量公告。");
                  return true;
               } else if (pending.isWaitingMessage()) {
                  if ("/callall".equals(normalizedCommand)) {
                     this.pendingAnnouncementBroadcasts.remove(userId);
                     return false;
                  } else if (message.getMessageId() == null) {
                     this.sendMessage(chatId, "暂时无法读取这条公告内容，请换一条消息重试，退出请输入 /cancel。");
                     return true;
                  } else {
                     this.pendingAnnouncementBroadcasts.put(userId, pending.withAnnouncementMessage(chatId, message.getMessageId()));
                     this.sendMessage(chatId, "请选择接收范围：\n`1` - 仅发送给已有 Emby 账号的用户\n`2` - 发送给所有可联系用户\n退出请输入 /cancel");
                     return true;
                  }
               } else if (!"1".equals(text) && !"2".equals(text)) {
                  this.sendMessage(chatId, "请回复 `1` 或 `2` 选择接收范围，退出请输入 /cancel。");
                  return true;
               } else {
                  this.pendingAnnouncementBroadcasts.remove(userId);
                  List<Long> targetChatIds = this.loadAnnouncementTargetChatIds(text);
                  if (targetChatIds.isEmpty()) {
                     this.sendMessage(chatId, "未找到可发送公告的 Telegram 用户。");
                     return true;
                  } else {
                     String targetLabel = "1".equals(text) ? "已有 Emby 账号用户" : "所有可联系用户";
                     this.sendMessage(chatId, "\ud83d\udce1 正在发送公告...\n\ud83c\udfaf 范围：" + targetLabel + "\n\ud83d\udc65 人数：" + targetChatIds.size());
                     this.announcementExecutor.submit(() -> this.sendAnnouncementBroadcast(userId, chatId, pending, targetChatIds, targetLabel));
                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   private List<Long> loadAnnouncementTargetChatIds(String targetMode) {
      List<UserOauthBinding> bindings;
      if ("1".equals(targetMode)) {
         List<Long> accountUserIds = this.embyUserService
            .lambdaQuery()
            .select(EmbyUser::getId)
            .isNotNull(EmbyUser::getEmbyUserId)
            .ne(EmbyUser::getEmbyUserId, "")
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .list()
            .stream()
            .map(EmbyUser::getId)
            .filter(id -> id != null)
            .toList();
         if (accountUserIds.isEmpty()) {
            return List.of();
         }

         bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .select(UserOauthBinding::getProviderUserId)
            .eq(UserOauthBinding::getProvider, "telegram")
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .isNotNull(UserOauthBinding::getProviderUserId)
            .ne(UserOauthBinding::getProviderUserId, "")
            .in(UserOauthBinding::getUserId, accountUserIds)
            .list();
      } else {
         bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .select(UserOauthBinding::getProviderUserId)
            .eq(UserOauthBinding::getProvider, "telegram")
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .isNotNull(UserOauthBinding::getProviderUserId)
            .ne(UserOauthBinding::getProviderUserId, "")
            .list();
      }

      LinkedHashSet<Long> targetChatIds = new LinkedHashSet<>();

      for (UserOauthBinding binding : bindings) {
         Long telegramId = this.parseTelegramUserId(binding.getProviderUserId());
         if (telegramId != null) {
            targetChatIds.add(telegramId);
         }
      }

      return new ArrayList<>(targetChatIds);
   }

   private Long parseTelegramUserId(String rawId) {
      if (!StringUtils.hasText(rawId)) {
         return null;
      } else {
         try {
            return Long.parseLong(rawId.trim());
         } catch (NumberFormatException var3) {
            log.warn("跳过无效 Telegram 用户 ID: {}", rawId);
            return null;
         }
      }
   }

   private void sendAnnouncementBroadcast(
      long operatorId, long responseChatId, DataQueryBot.PendingAnnouncementBroadcast pending, List<Long> targetChatIds, String targetLabel
   ) {
      long start = System.nanoTime();
      int success = 0;
      int failed = 0;

      for (Long targetChatId : targetChatIds) {
         try {
            MessageId copied = this.copyAnnouncementMessage(targetChatId, pending);
            if (copied != null && copied.getMessageId() != null) {
               success++;
            } else {
               failed++;
            }

            Thread.sleep(80L);
         } catch (InterruptedException var15) {
            Thread.currentThread().interrupt();
            failed++;
            break;
         } catch (Exception var16) {
            failed++;
            log.warn("批量公告发送失败: targetChatId={}, error={}", targetChatId, var16.getMessage());
         }
      }

      double seconds = (double)(System.nanoTime() - start) / 1.0E9;
      this.sendMessage(
         responseChatId,
         String.format(Locale.ROOT, "✅ 公告发送完成\n\n\ud83c\udfaf 范围：%s\n\ud83d\udcec 成功：%d\n⚠️ 失败：%d\n⏱️ 耗时 %.3f s", targetLabel, success, failed, seconds)
      );
      log.info(
         "【批量公告】operator={} target={} success={} failed={} cost={}s", operatorId, targetLabel, success, failed, String.format(Locale.ROOT, "%.3f", seconds)
      );
   }

   private MessageId copyAnnouncementMessage(long targetChatId, DataQueryBot.PendingAnnouncementBroadcast pending) throws TelegramApiException {
      CopyMessage copyMessage = CopyMessage.builder()
         .chatId(String.valueOf(targetChatId))
         .fromChatId(String.valueOf(pending.getFromChatId()))
         .messageId(pending.getMessageId())
         .build();
      return this.telegramClient.execute(copyMessage);
   }

   private String normalizeBotCommand(String text) {
      if (StringUtils.hasText(text) && text.startsWith("/")) {
         String command = text.split("\\s+", 2)[0].toLowerCase();
         if (command.contains("@")) {
            command = command.substring(0, command.indexOf(64));
         }

         return command;
      } else {
         return "";
      }
   }

   private void handleCreateUserCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         String[] args = argument.split("\\s+", 3);
         if (args.length < 3) {
            this.sendMessage(chatId, "用法：`/createuser <用户名> <天数> <备注>`\n示例：`/createuser test 30 机器人创建`");
         } else {
            String embyUserName = args[0];

            int days;
            try {
               days = Integer.parseInt(args[1]);
            } catch (NumberFormatException var17) {
               this.sendMessage(chatId, "天数字段必须是数字，例如：`/createuser test 30 备注`");
               return;
            }

            if (days <= 0) {
               this.sendMessage(chatId, "天数必须大于 0。");
            } else {
               String remarks = args[2].trim();
               if (!StringUtils.hasText(remarks)) {
                  this.sendMessage(chatId, "备注不能为空。");
               } else {
                  List<EmbyInfo> servers = this.embyInfoService
                     .lambdaQuery()
                     .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
                     .eq(EmbyInfo::getStatus, Integer.valueOf(0))
                     .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                     .list();
                  if (servers != null && !servers.isEmpty()) {
                     this.pendingCreateUsers.put(userId, new DataQueryBot.PendingCreateUser(chatId, embyUserName, days, remarks));
                     List<InlineKeyboardRow> rows = new ArrayList<>();

                     for (EmbyInfo server : servers) {
                        InlineKeyboardButton button = InlineKeyboardButton.builder()
                           .text("\ud83d\udda5️ " + this.buildServerLabel(server))
                           .callbackData("create_server:" + server.getId())
                           .build();
                        rows.add(new InlineKeyboardRow(button));
                     }

                     InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(rows).build();
                     SendMessage selectServerMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text(this.foamPanelTitle("用户创建") + "请选择要创建用户的服务器：")
                        .parseMode("Markdown")
                        .replyMarkup(keyboard)
                        .build();

                     try {
                        Message sentMessage = this.telegramClient.execute(selectServerMessage);
                        this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
                     } catch (TelegramApiException var16) {
                        log.error("发送服务器选择列表失败", (Throwable)var16);
                        this.sendMessage(chatId, "发送服务器列表失败，请稍后再试。");
                     }
                  } else {
                     this.sendMessage(chatId, "未找到可用服务器，请先在后台配置。");
                  }
               }
            }
         }
      }
   }

   private void handleCallbackQuery(CallbackQuery callbackQuery) {
      String data = callbackQuery.getData();
      if (!StringUtils.hasText(data)) {
         this.answerCallbackQuery(callbackQuery.getId());
      } else if (data.startsWith("admin_user:")) {
         this.handleAdminUserPanelCallback(callbackQuery, data);
      } else if (data.startsWith("admin_tg:")) {
         this.handleAdminTelegramMemberPanelCallback(callbackQuery, data);
      } else if (data.startsWith("lib_access:")) {
         this.handleLibraryAccessCallback(callbackQuery, data);
      } else if (data.startsWith("kkreg_cancel:")) {
         this.handleKkRegistrationCancelCallback(callbackQuery, data.substring("kkreg_cancel:".length()));
      } else if (data.startsWith("tgb:")) {
         this.handleCredentialBindServerCallback(callbackQuery, data);
      } else {
         this.answerCallbackQuery(callbackQuery.getId());
         if (data.startsWith("start_panel:")) {
            this.handleStartPanelCallback(callbackQuery, data.substring("start_panel:".length()));
         } else if (data.startsWith("start_action:")) {
            this.handleStartPanelActionCallback(callbackQuery, data.substring("start_action:".length()));
         } else if (data.startsWith("start_admin:")) {
            this.handleStartPanelAdminCallback(callbackQuery, data.substring("start_admin:".length()));
         } else if ("start_input:cancel".equals(data)) {
            this.handleStartPanelInputCancel(callbackQuery);
         } else if (data.startsWith("points_exchange:")) {
            this.handlePointsExchangeCallback(callbackQuery, data);
         } else {
            String[] parts = data.split(":");
            String action = parts[0];
            if ("submit_inline_request".equals(action)) {
               if (parts.length == 3) {
                  this.handleInlineRequestSubmit(callbackQuery, parts[1], parts[2]);
               }
            } else if ("tg_binding_review".equals(action)) {
               if (parts.length == 3) {
                  this.handleTelegramBindingReviewCallback(callbackQuery, parts[1], parts[2]);
               }
            } else if (callbackQuery.getMessage() != null) {
               long chatId = callbackQuery.getMessage().getChatId();
               Integer messageId = callbackQuery.getMessage().getMessageId();
               switch (action) {
                  case "noop":
                  default:
                     break;
                  case "confirm_unbind":
                     this.handleTelegramUnbindConfirm(callbackQuery);
                     break;
                  case "cancel_unbind":
                     this.editMessageTextSilently(chatId, messageId, "已取消解除绑定。");
                     break;
                  case "page":
                     if (parts.length == 2) {
                        int newPage = Integer.parseInt(parts[1]);
                        this.sendOrEditResultListPage(chatId, messageId, newPage);
                     }
                     break;
                  case "select":
                     if (parts.length == 2) {
                        int selectedIndex = Integer.parseInt(parts[1]);
                        this.showItemDetails(chatId, messageId, selectedIndex, 0);
                     }
                     break;
                  case "resource_page":
                     if (parts.length == 3) {
                        int itemIndex = Integer.parseInt(parts[1]);
                        int resourcePage = Integer.parseInt(parts[2]);
                        this.showItemDetails(chatId, messageId, itemIndex, resourcePage);
                     }
                     break;
                  case "request_page":
                     if (parts.length == 2) {
                        try {
                           int selectedIndex = Integer.parseInt(parts[1]);
                           this.sendOrEditRequestCard(chatId, messageId, selectedIndex);
                        } catch (NumberFormatException var17) {
                           this.sendMessage(chatId, "求片选择无效，请重新搜索。");
                        }
                     }
                     break;
                  case "submit_request":
                     if (parts.length == 2) {
                        try {
                           int selectedIndex = Integer.parseInt(parts[1]);
                           this.handleRequestSubmit(callbackQuery, selectedIndex);
                        } catch (NumberFormatException var16) {
                           this.sendMessage(chatId, "求片选择无效，请重新搜索。");
                        }
                     }
                     break;
                  case "back_to_list":
                     if (parts.length == 2) {
                        int pageToGoBackTo = Integer.parseInt(parts[1]);
                        this.sendOrEditResultListPage(chatId, messageId, pageToGoBackTo);
                     }
                     break;
                  case "create_server":
                     if (parts.length == 2) {
                        try {
                           long serverId = Long.parseLong(parts[1]);
                           this.processCreateUserSelection(callbackQuery, serverId);
                        } catch (NumberFormatException var15) {
                           this.sendMessage(chatId, "服务器选择无效，请重新尝试。");
                        }
                     }
                     break;
                  case "card_server":
                     if (parts.length == 2) {
                        try {
                           long serverId = Long.parseLong(parts[1]);
                           this.processCardBatchSelection(callbackQuery, serverId);
                        } catch (NumberFormatException var14) {
                           this.sendMessage(chatId, "服务器选择无效，请重新尝试。");
                        }
                     }
                     break;
                  case "edit_user":
                     if (parts.length == 2) {
                        try {
                           long userId = Long.parseLong(parts[1]);
                           this.handleUserSelection(callbackQuery, userId);
                        } catch (NumberFormatException var13) {
                           this.sendMessage(chatId, "用户选择无效，请重新搜索。");
                        }
                     }
                     break;
                  case "extend_server":
                     if (parts.length == 2) {
                        try {
                           long serverId = Long.parseLong(parts[1]);
                           this.processExtendBatchSelection(callbackQuery, serverId);
                        } catch (NumberFormatException var12) {
                           this.sendMessage(chatId, "服务器选择无效，请重新尝试。");
                        }
                     }
               }
            }
         }
      }
   }

   private void handleStartPanelCallback(CallbackQuery callbackQuery, String action) {
      Message message = this.resolvePrivateStartPanelMessage(callbackQuery);
      if (message != null) {
         long userId = callbackQuery.getFrom().getId();
         this.renderStartPanelPage(message, userId, action);
      }
   }

   private void renderStartPanelPage(Message message, long userId, String action) {
      String text;
      InlineKeyboardMarkup keyboard;
      switch (action) {
         case "home":
            boolean owner = this.isBotOwner(userId);
            boolean admin = this.telegramBotAuthorizationService.isAdmin(userId);
            text = this.buildStartPanelHomeText(owner, admin);
            keyboard = this.buildStartPanelHomeKeyboard(admin);
            break;
         case "account":
            text = this.foamPanelTitle("账号中心") + "查询当前 Telegram 关联账号，或继续查看这个账号可用的 Emby 线路。";
            keyboard = this.keyboard(
               List.of(
                  this.button("\ud83d\udd0e 查询我的账号", "start_action:myaccount"),
                  this.button("\ud83d\udce1 查看可用线路", "start_action:mylines"),
                  this.button("\ud83d\udd17 绑定与卡密", "start_panel:binding"),
                  this.button("\ud83c\udfe0 返回首页", "start_panel:home")
               ),
               2
            );
            break;
         case "lines":
            text = this.foamPanelTitle("线路中心") + "线路只会按当前 Telegram 绑定的 Emby 账号和实际权限展示。";
            keyboard = this.keyboard(
               List.of(
                  this.button("\ud83d\ude80 查询可用线路", "start_action:mylines"),
                  this.button("\ud83d\udc64 我的账号", "start_panel:account"),
                  this.button("\ud83c\udfe0 返回首页", "start_panel:home")
               ),
               2
            );
            break;
         case "request":
            text = this.foamPanelTitle("求片中心") + "点击按钮后直接输入片名，我会展示 TMDB 海报卡片供你选择和提交。\n\n\ud83d\udca1 也可以在任意聊天中使用 `@机器人 片名` 内联搜索。";
            keyboard = this.keyboard(
               List.of(
                  this.button("\ud83d\udd0d 开始求片", "start_action:request"),
                  this.button("\ud83d\udcd6 查看求片说明", "start_action:request_guide"),
                  this.button("\ud83c\udfe0 返回首页", "start_panel:home")
               ),
               2
            );
            break;
         case "points":
            text = this.foamPanelTitle("积分中心") + "查询积分、使用雾袋、查看兑换项目，或直接进入积分注册和续费流程。";
            List<InlineKeyboardButton> pointButtons = new ArrayList<>();
            pointButtons.add(this.button("\ud83e\ude99 查询积分", "start_action:points"));
            if (this.pointsBot.isFoamBagEnabled()) {
               pointButtons.add(this.button("\ud83c\udf01 雾袋", "start_action:foambag"));
            }

            pointButtons.add(this.button("\ud83c\udfaf 兑换项目", "start_action:exchange"));
            pointButtons.add(this.button("\ud83c\udf81 积分奖品", "start_action:prizes"));
            pointButtons.add(this.button("\ud83d\udc51 积分注册", "start_action:redeem"));
            pointButtons.add(this.button("♻️ 积分续费", "start_action:recharge"));
            pointButtons.add(this.button("\ud83c\udfe0 返回首页", "start_panel:home"));
            keyboard = this.keyboard(pointButtons, 2);
            break;
         case "binding":
            text = this.foamPanelTitle("绑定与卡密") + "绑定、注册和卡密流程都从按钮开始；需要账号、密码或卡密时再按提示输入。";
            keyboard = this.keyboard(
               List.of(
                  this.button("\ud83d\udd17 绑定账号", "start_action:bind"),
                  this.button("↩️ 解除绑定", "start_action:unbind"),
                  this.button("\ud83d\udd52 取消审批", "start_action:cancelreview"),
                  this.button("\ud83d\udc51 注册账号", "start_action:register"),
                  this.button("\ud83c\udf9f️ 卡密开号", "start_action:cardopen"),
                  this.button("♻️ 卡密续费", "start_action:cardrenew"),
                  this.button("\ud83c\udfe0 返回首页", "start_panel:home")
               ),
               2
            );
            break;
         case "admin":
            if (!this.telegramBotAuthorizationService.isAdmin(userId)) {
               this.sendMessage(userId, "❌ 当前账号没有机器人管理权限。");
               return;
            }

            text = this.foamPanelTitle("管理中心") + "仅显示当前账号已获授权的管理操作；点击后会继续通过按钮或输入提示完成。";
            keyboard = this.buildStartPanelAdminKeyboard(userId);
            break;
         default:
            return;
      }

      this.editStartPanelMessage(message, text, keyboard);
   }

   private Message resolvePrivateStartPanelMessage(CallbackQuery callbackQuery) {
      if (callbackQuery.getMessage() instanceof Message message
         && message.isUserMessage()
         && callbackQuery.getFrom() != null
         && callbackQuery.getFrom().getId() != null
         && callbackQuery.getFrom().getId().equals(message.getChatId())) {
         return message;
      }

      return null;
   }

   private InlineKeyboardButton button(String text, String callbackData) {
      return InlineKeyboardButton.builder().text(text).callbackData(callbackData).build();
   }

   private String foamPanelTitle(String title) {
      return "\ud83c\udf01 *Mist " + title + "*\n\n";
   }

   private InlineKeyboardMarkup keyboard(List<InlineKeyboardButton> buttons, int columns) {
      List<InlineKeyboardRow> rows = new ArrayList<>();
      int safeColumns = Math.max(1, columns);

      for (int index = 0; index < buttons.size(); index += safeColumns) {
         InlineKeyboardRow row = new InlineKeyboardRow();

         for (int offset = 0; offset < safeColumns && index + offset < buttons.size(); offset++) {
            row.add(buttons.get(index + offset));
         }

         rows.add(row);
      }

      return InlineKeyboardMarkup.builder().keyboard(rows).build();
   }

   private InlineKeyboardMarkup buildStartPanelAdminKeyboard(long operatorId) {
      Set<TelegramBotPermission> permissions = this.isBotOwner(operatorId)
         ? Set.of(TelegramBotPermission.values())
         : this.telegramBotAuthorizationService.permissionsFor(operatorId);
      List<InlineKeyboardButton> buttons = new ArrayList<>();
      if (permissions.contains(TelegramBotPermission.USER_VIEW)) {
         buttons.add(this.button("\ud83d\udc64 用户管理", "start_admin:kk"));
      }

      if (permissions.contains(TelegramBotPermission.USER_CREATE)) {
         buttons.add(this.button("\ud83d\udc51 创建 Emby 用户", "start_admin:createuser"));
      }

      if (permissions.contains(TelegramBotPermission.LIBRARY_ACCESS)) {
         buttons.add(this.button("\ud83d\uddc2️ 媒体库分级", "start_admin:libraryaccess"));
      }

      if (permissions.contains(TelegramBotPermission.CARD_MANAGE)) {
         buttons.add(this.button("\ud83c\udf9f️ 批量生成卡密", "start_admin:generatecards"));
      }

      if (permissions.contains(TelegramBotPermission.USER_RENEW)) {
         buttons.add(this.button("♻️ 批量延期用户", "start_admin:extendusers"));
      }

      if (permissions.contains(TelegramBotPermission.BROADCAST)) {
         buttons.add(this.button("\ud83d\udce2 批量发送公告", "start_admin:callall"));
      }

      if (permissions.contains(TelegramBotPermission.POINTS_ADMIN)) {
         buttons.add(this.button("\ud83e\ude99 管理员续期账号", "start_admin:renew"));
         buttons.add(this.button("\ud83d\udd25 地狱骰金库", "hell:admin:refresh"));
      }

      if (permissions.contains(TelegramBotPermission.SCRATCH_RECORD_VIEW)) {
         buttons.add(this.button("\ud83c\udf01 刮刮乐大奖记录", "start_admin:scratchwins"));
      }

      buttons.add(this.button("\ud83c\udfe0 返回首页", "start_panel:home"));
      return this.keyboard(buttons, 2);
   }

   private void handleLibraryAccessCommand(Message message) {
      if (!message.isUserMessage()) {
         this.sendMessage(message.getChatId(), "\ud83d\udd12 媒体库分级仅支持在机器人私聊中设置。");
      } else {
         long operatorId = message.getFrom().getId();
         List<EmbyInfo> servers = this.loadAvailableServers();
         if (servers.isEmpty()) {
            this.sendMessage(operatorId, "❌ 暂无可用的 Emby 服务器，请先在后台完成配置。");
         } else {
            this.sendKeyboardMessage(operatorId, this.foamPanelTitle("媒体库分级") + "请选择要设置的 Emby 服务器：", this.buildLibraryAccessServerKeyboard(servers));
         }
      }
   }

   private InlineKeyboardMarkup buildLibraryAccessServerKeyboard(List<EmbyInfo> servers) {
      List<InlineKeyboardButton> buttons = servers.stream()
         .filter(server -> server != null && server.getId() != null)
         .map(server -> this.button("\ud83d\udda5️ " + this.truncateTelegramButton(this.buildServerLabel(server), 24), "lib_access:server:" + server.getId()))
         .toList();
      List<InlineKeyboardButton> allButtons = new ArrayList<>(buttons);
      allButtons.add(this.button("\ud83c\udfe0 返回首页", "start_panel:home"));
      return this.keyboard(allButtons, 1);
   }

   private void renderLibraryAccessServers(Message panelMessage, long operatorId) {
      List<EmbyInfo> servers = this.loadAvailableServers();
      if (servers.isEmpty()) {
         this.editStartPanelMessage(
            panelMessage,
            this.foamPanelTitle("媒体库分级") + "❌ 暂无可用的 Emby 服务器。",
            this.keyboard(List.of(this.button("\ud83d\udee1️ 返回管理中心", "start_panel:admin")), 1)
         );
      } else {
         this.editStartPanelMessage(panelMessage, this.foamPanelTitle("媒体库分级") + "请选择要设置的 Emby 服务器：", this.buildLibraryAccessServerKeyboard(servers));
      }
   }

   private void handleLibraryAccessCallback(CallbackQuery callbackQuery, String data) {
      Message panelMessage = this.resolvePrivateStartPanelMessage(callbackQuery);
      if (panelMessage != null && this.telegramBotAuthorizationService.hasPermission(callbackQuery.getFrom().getId(), TelegramBotPermission.LIBRARY_ACCESS)) {
         String[] parts = data.split(":");
         boolean sessionAction = parts.length >= 4 && "s".equals(parts[1]);
         if (!sessionAction) {
            this.answerCallbackQuery(callbackQuery.getId());
         }

         try {
            if (parts.length == 2 && "servers".equals(parts[1])) {
               this.renderLibraryAccessServers(panelMessage, callbackQuery.getFrom().getId());
               return;
            }

            if (parts.length == 3 && "server".equals(parts[1])) {
               this.renderLibraryAccessScope(panelMessage, callbackQuery.getFrom().getId(), Long.parseLong(parts[2]));
               return;
            }

            if (parts.length == 3 && "global".equals(parts[1])) {
               this.openLibraryAccessSession(panelMessage, callbackQuery.getFrom().getId(), Long.parseLong(parts[2]), null);
               return;
            }

            if (parts.length == 4 && "users".equals(parts[1])) {
               this.renderLibraryAccessUsers(panelMessage, callbackQuery.getFrom().getId(), Long.parseLong(parts[2]), Integer.parseInt(parts[3]));
               return;
            }

            if (parts.length == 4 && "user".equals(parts[1])) {
               this.openLibraryAccessSession(panelMessage, callbackQuery.getFrom().getId(), Long.parseLong(parts[2]), Long.parseLong(parts[3]));
               return;
            }

            if (parts.length >= 4 && "s".equals(parts[1])) {
               this.handleLibraryAccessSessionAction(callbackQuery, panelMessage, parts);
            }
         } catch (NumberFormatException var7) {
            this.editStartPanelMessage(
               panelMessage,
               this.foamPanelTitle("媒体库分级") + "❌ 面板参数已失效，请重新打开。",
               this.keyboard(List.of(this.button("\ud83d\uddc2️ 重新选择服务器", "lib_access:servers")), 1)
            );
         } catch (Exception var8) {
            log.warn("处理 Telegram 媒体库分级面板失败: operatorId={}, data={}, error={}", callbackQuery.getFrom().getId(), data, var8.getMessage());
            this.editStartPanelMessage(
               panelMessage,
               this.foamPanelTitle("媒体库分级") + "❌ " + this.escapeMarkdown(StringUtils.hasText(var8.getMessage()) ? var8.getMessage() : "操作失败，请稍后再试。"),
               this.keyboard(List.of(this.button("\ud83d\uddc2️ 返回服务器选择", "lib_access:servers")), 1)
            );
         }
      } else {
         this.answerCallbackQuery(callbackQuery.getId(), "当前账号没有设置媒体库分级的权限。");
      }
   }

   private void renderLibraryAccessScope(Message panelMessage, long operatorId, long serverId) {
      EmbyInfo server = this.embyInfoService.getById(Long.valueOf(serverId));
      if (server != null && Integer.valueOf(1).equals(server.getEnabled()) && Integer.valueOf(0).equals(server.getStatus())) {
         this.editStartPanelMessage(
            panelMessage,
            this.foamPanelTitle("媒体库分级") + "服务器：*" + this.escapeMarkdown(this.buildServerLabel(server)) + "*\n\n服务器默认设置适用于该服务器的所有普通用户；也可以选择某个用户，单独设置他能看到的媒体库。",
            this.keyboard(
               List.of(
                  this.button("\ud83c\udf10 服务器默认设置", "lib_access:global:" + serverId),
                  this.button("\ud83d\udc64 用户单独设置", "lib_access:users:" + serverId + ":0"),
                  this.button("⬅️ 返回服务器", "lib_access:servers"),
                  this.button("\ud83d\udee1️ 返回管理中心", "start_panel:admin")
               ),
               2
            )
         );
      } else {
         throw new BizException("服务器不可用，请重新选择");
      }
   }

   private void renderLibraryAccessUsers(Message panelMessage, long operatorId, long serverId, int requestedPage) {
      List<EmbyLibraryAccessUserOptionResponse> users = this.embyLibraryAccessService.listUsersFromTelegram(serverId, operatorId);
      int pages = Math.max(1, (int)Math.ceil((double)users.size() / 6.0));
      int page = Math.max(0, Math.min(requestedPage, pages - 1));
      int start = page * 6;
      int end = Math.min(start + 6, users.size());
      List<InlineKeyboardRow> rows = new ArrayList<>();

      for (int index = start; index < end; index++) {
         EmbyLibraryAccessUserOptionResponse user = users.get(index);
         rows.add(
            new InlineKeyboardRow(
               this.button("\ud83d\udc64 " + this.truncateTelegramButton(user.getEmbyUserName(), 26), "lib_access:user:" + serverId + ":" + user.getId())
            )
         );
      }

      if (pages > 1) {
         InlineKeyboardRow paginationRow = new InlineKeyboardRow();
         if (page > 0) {
            paginationRow.add(this.button("⬅️ 上一页", "lib_access:users:" + serverId + ":" + (page - 1)));
         }

         paginationRow.add(this.button(page + 1 + " / " + pages, "noop"));
         if (page + 1 < pages) {
            paginationRow.add(this.button("下一页 ➡️", "lib_access:users:" + serverId + ":" + (page + 1)));
         }

         rows.add(paginationRow);
      }

      rows.add(new InlineKeyboardRow(this.button("⬅️ 返回配置范围", "lib_access:server:" + serverId)));
      String text = this.foamPanelTitle("媒体库分级") + (users.isEmpty() ? "当前服务器暂无可配置的普通用户。" : "请选择要单独设置的用户：");
      this.editStartPanelMessage(panelMessage, text, InlineKeyboardMarkup.builder().keyboard(rows).build());
   }

   private void openLibraryAccessSession(Message panelMessage, long operatorId, long serverId, Long userId) {
      EmbyLibraryAccessOverviewResponse overview = this.embyLibraryAccessService.overviewFromTelegram(serverId, userId, operatorId);
      EmbyLibraryAccessConfigResponse selectedConfig = userId == null ? overview.getGlobalConfig() : overview.getUserConfig();
      List<String> selectedFolderIds = selectedConfig != null && selectedConfig.isEnabled()
         ? selectedConfig.getVisibleFolderIds()
         : overview.getEffectiveConfig().getVisibleFolderIds();
      JSONObject session = new JSONObject();
      session.put("operatorId", Long.valueOf(operatorId));
      session.put("serverId", Long.valueOf(serverId));
      session.put("serverName", overview.getServerName());
      session.put("userId", userId);
      session.put("userName", overview.getUserName());
      session.put("scope", userId == null ? "GLOBAL" : "USER");
      session.put("enabled", Boolean.valueOf(selectedConfig != null && selectedConfig.isEnabled()));
      session.put("selectedFolderIds", selectedFolderIds == null ? List.of() : selectedFolderIds);
      session.put("folderIds", overview.getFolders().stream().map(EmbyLibraryFolderResponse::getId).toList());
      session.put("folderNames", overview.getFolders().stream().map(EmbyLibraryFolderResponse::getName).toList());
      session.put("page", Integer.valueOf(0));
      String token = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
      this.saveLibraryAccessSession(token, session);
      this.renderLibraryAccessSession(panelMessage, token, session, null);
   }

   private void handleLibraryAccessSessionAction(CallbackQuery callbackQuery, Message panelMessage, String[] parts) {
      String token = parts[2];
      JSONObject session = this.loadLibraryAccessSession(token);
      long operatorId = callbackQuery.getFrom().getId();
      if (session != null && session.getLongValue("operatorId") == operatorId) {
         this.answerCallbackQuery(callbackQuery.getId());
         String action = parts[3];
         List<String> folderIds = this.jsonStringList(session, "folderIds");
         LinkedHashSet<String> selected = new LinkedHashSet<>(this.jsonStringList(session, "selectedFolderIds"));
         if ("f".equals(action) && parts.length == 5) {
            int index = Integer.parseInt(parts[4]);
            if (index < 0 || index >= folderIds.size()) {
               throw new BizException("媒体库选项已失效");
            }

            String folderId = folderIds.get(index);
            if (!selected.remove(folderId)) {
               selected.add(folderId);
            }

            session.put("selectedFolderIds", new ArrayList<>(selected));
         } else if ("all".equals(action)) {
            session.put("selectedFolderIds", selected.size() == folderIds.size() ? List.of() : new ArrayList<>(folderIds));
         } else if ("switch".equals(action)) {
            session.put("enabled", Boolean.valueOf(!session.getBooleanValue("enabled")));
         } else if ("page".equals(action) && parts.length == 5) {
            session.put("page", Integer.valueOf(Integer.parseInt(parts[4])));
         } else {
            if ("back".equals(action)) {
               this.renderLibraryAccessScope(panelMessage, operatorId, session.getLongValue("serverId"));
               return;
            }

            if ("save".equals(action)) {
               EmbyLibraryAccessUpdateResponse result = this.saveLibraryAccessFromTelegram(callbackQuery, session);
               this.saveLibraryAccessSession(token, session);
               String notice = result.getFailedUserCount() > 0
                  ? "⚠️ 规则已保存；同步成功 " + result.getAppliedUserCount() + " 人，失败 " + result.getFailedUserCount() + " 人。"
                  : "✅ 规则已保存并同步 " + result.getAppliedUserCount() + " 人。";
               this.renderLibraryAccessSession(panelMessage, token, session, notice);
               return;
            }
         }

         this.saveLibraryAccessSession(token, session);
         this.renderLibraryAccessSession(panelMessage, token, session, null);
      } else {
         this.answerCallbackQuery(callbackQuery.getId(), "面板已过期或不属于当前账号。");
      }
   }

   private EmbyLibraryAccessUpdateResponse saveLibraryAccessFromTelegram(CallbackQuery callbackQuery, JSONObject session) {
      String actorName = "Telegram " + this.telegramDisplayName(callbackQuery.getFrom()) + " (" + callbackQuery.getFrom().getId() + ")";
      List<String> selectedFolderIds = this.jsonStringList(session, "selectedFolderIds");
      if ("USER".equals(session.getString("scope"))) {
         EmbyLibraryAccessUserUpdateRequest request = new EmbyLibraryAccessUserUpdateRequest();
         request.setEmbyInfoId(session.getLong("serverId"));
         request.setUserId(session.getLong("userId"));
         request.setOverrideEnabled(session.getBooleanValue("enabled"));
         request.setVisibleFolderIds(selectedFolderIds);
         return this.embyLibraryAccessService.updateUserFromTelegram(request, callbackQuery.getFrom().getId(), actorName);
      } else {
         EmbyLibraryAccessGlobalUpdateRequest request = new EmbyLibraryAccessGlobalUpdateRequest();
         request.setEmbyInfoId(session.getLong("serverId"));
         request.setEnabled(session.getBooleanValue("enabled"));
         request.setVisibleFolderIds(selectedFolderIds);
         return this.embyLibraryAccessService.updateGlobalFromTelegram(request, callbackQuery.getFrom().getId(), actorName);
      }
   }

   private void renderLibraryAccessSession(Message panelMessage, String token, JSONObject session, String notice) {
      List<String> folderIds = this.jsonStringList(session, "folderIds");
      List<String> folderNames = this.jsonStringList(session, "folderNames");
      Set<String> selected = new LinkedHashSet<>(this.jsonStringList(session, "selectedFolderIds"));
      int pages = Math.max(1, (int)Math.ceil((double)folderIds.size() / 8.0));
      int page = Math.max(0, Math.min(session.getIntValue("page"), pages - 1));
      session.put("page", Integer.valueOf(page));
      int start = page * 8;
      int end = Math.min(start + 8, folderIds.size());
      List<InlineKeyboardRow> rows = new ArrayList<>();

      for (int index = start; index < end; index += 2) {
         InlineKeyboardRow row = new InlineKeyboardRow();

         for (int offset = 0; offset < 2 && index + offset < end; offset++) {
            int folderIndex = index + offset;
            String folderId = folderIds.get(folderIndex);
            String folderName = folderIndex < folderNames.size() ? folderNames.get(folderIndex) : folderId;
            row.add(
               this.button(
                  (selected.contains(folderId) ? "✅ " : "\ud83d\ude48 ") + this.truncateTelegramButton(folderName, 18),
                  "lib_access:s:" + token + ":f:" + folderIndex
               )
            );
         }

         rows.add(row);
      }

      if (pages > 1) {
         InlineKeyboardRow paginationRow = new InlineKeyboardRow();
         if (page > 0) {
            paginationRow.add(this.button("⬅️", "lib_access:s:" + token + ":page:" + (page - 1)));
         }

         paginationRow.add(this.button(page + 1 + " / " + pages, "noop"));
         if (page + 1 < pages) {
            paginationRow.add(this.button("➡️", "lib_access:s:" + token + ":page:" + (page + 1)));
         }

         rows.add(paginationRow);
      }

      boolean enabled = session.getBooleanValue("enabled");
      boolean userScope = "USER".equals(session.getString("scope"));
      rows.add(
         new InlineKeyboardRow(
            this.button(enabled ? "\ud83d\udfe0 关闭" : "⚪ 开启", "lib_access:s:" + token + ":switch"),
            this.button(selected.size() == folderIds.size() ? "取消全选" : "全选", "lib_access:s:" + token + ":all")
         )
      );
      rows.add(new InlineKeyboardRow(this.button("\ud83d\udcbe 保存并同步", "lib_access:s:" + token + ":save")));
      rows.add(new InlineKeyboardRow(this.button("⬅️ 返回配置范围", "lib_access:s:" + token + ":back")));
      String state = userScope ? (enabled ? "该用户按已选范围展示" : "该用户跟随服务器默认设置") : (enabled ? "普通用户按已选范围展示" : "普通用户默认可看全部媒体库");
      StringBuilder text = new StringBuilder(this.foamPanelTitle("媒体库分级"))
         .append("服务器：*")
         .append(this.escapeMarkdown(session.getString("serverName")))
         .append("*\n");
      if (userScope) {
         text.append("用户：`").append(this.escapeMarkdown(session.getString("userName"))).append("`\n");
      }

      text.append("状态：").append(state).append("\n").append("展示：").append(selected.size()).append(" / ").append(folderIds.size()).append(" 个媒体库");
      if (StringUtils.hasText(notice)) {
         text.append("\n\n").append(notice);
      }

      this.editStartPanelMessage(panelMessage, text.toString(), InlineKeyboardMarkup.builder().keyboard(rows).build());
   }

   private void saveLibraryAccessSession(String token, JSONObject session) {
      this.stringRedisTemplate.opsForValue().set("bot:library-access:panel:" + token, session.toJSONString(), 10L, TimeUnit.MINUTES);
   }

   private JSONObject loadLibraryAccessSession(String token) {
      if (!StringUtils.hasText(token)) {
         return null;
      } else {
         String value = this.stringRedisTemplate.opsForValue().get("bot:library-access:panel:" + token);
         return StringUtils.hasText(value) ? JSON.parseObject(value) : null;
      }
   }

   private List<String> jsonStringList(JSONObject object, String key) {
      return (List<String>)(object != null && object.getJSONArray(key) != null
         ? object.getJSONArray(key)
            .toJavaList(String.class)
            .stream()
            .filter(StringUtils::hasText)
            .map(String::trim)
            .distinct()
            .collect(Collectors.toCollection(ArrayList::new))
         : new ArrayList<>());
   }

   private String truncateTelegramButton(String value, int maxLength) {
      String text = StringUtils.hasText(value) ? value.trim() : "未命名";
      return text.length() <= maxLength ? text : text.substring(0, Math.max(1, maxLength - 1)) + "…";
   }

   private void editStartPanelMessage(Message message, String text, InlineKeyboardMarkup keyboard) {
      boolean captionMessage = message.hasPhoto() || message.hasCaption();

      try {
         this.executeStartPanelEdit(message, text, keyboard, true, captionMessage);
      } catch (TelegramApiException var11) {
         log.warn("切换 Telegram 服务面板失败: chatId={}, messageId={}, error={}", message.getChatId(), message.getMessageId(), var11.getMessage());
         if (this.isTelegramMessageNotModified(var11)) {
            return;
         }

         boolean retryAsCaption = this.resolveStartPanelCaptionRetry(var11, captionMessage);

         try {
            this.executeStartPanelEdit(
               message, retryAsCaption == captionMessage ? this.plainStartPanelText(text) : text, keyboard, retryAsCaption != captionMessage, retryAsCaption
            );
         } catch (TelegramApiException var10) {
            TelegramApiException retryException = var10;
            if (this.isTelegramMessageNotModified(var10)) {
               return;
            }

            if (retryAsCaption != captionMessage) {
               try {
                  this.executeStartPanelEdit(message, this.plainStartPanelText(text), keyboard, false, retryAsCaption);
                  return;
               } catch (TelegramApiException var9) {
                  retryException = var9;
               }
            }

            log.error(
               "原位切换 Telegram 服务面板重试失败，不发送新消息: chatId={}, messageId={}, error={}", message.getChatId(), message.getMessageId(), retryException.getMessage()
            );
         }
      }
   }

   private void executeStartPanelEdit(Message message, String text, InlineKeyboardMarkup keyboard, boolean markdown, boolean captionMessage) throws TelegramApiException {
      if (captionMessage) {
         EditMessageCaptionBuilder builder = EditMessageCaption.builder()
            .chatId(message.getChatId())
            .messageId(message.getMessageId())
            .caption(text)
            .replyMarkup(keyboard);
         if (markdown) {
            builder.parseMode("Markdown");
         }

         this.telegramClient.execute(builder.build());
      } else {
         EditMessageTextBuilder builder = EditMessageText.builder()
            .chatId(message.getChatId())
            .messageId(message.getMessageId())
            .text(text)
            .replyMarkup(keyboard);
         if (markdown) {
            builder.parseMode("Markdown");
         }

         this.telegramClient.execute(builder.build());
      }
   }

   private boolean resolveStartPanelCaptionRetry(TelegramApiException exception, boolean currentMode) {
      if (exception != null && exception.getMessage() != null) {
         String message = exception.getMessage().toLowerCase(Locale.ROOT);
         if (message.contains("no text in the message to edit")) {
            return true;
         } else {
            return !message.contains("no caption") && !message.contains("caption is empty") ? currentMode : false;
         }
      } else {
         return currentMode;
      }
   }

   private boolean isTelegramMessageNotModified(TelegramApiException exception) {
      return exception != null && exception.getMessage() != null && exception.getMessage().toLowerCase(Locale.ROOT).contains("message is not modified");
   }

   private String plainStartPanelText(String text) {
      return text == null ? "" : text.replace("*", "").replace("`", "");
   }

   private void handleStartPanelActionCallback(CallbackQuery callbackQuery, String action) {
      Message panelMessage = this.resolvePrivateStartPanelMessage(callbackQuery);
      if (panelMessage != null) {
         switch (action) {
            case "myaccount":
               this.dispatchPanelCommand(callbackQuery, "/myaccount");
               break;
            case "mylines":
               this.dispatchPanelCommand(callbackQuery, "/mylines");
               break;
            case "request":
               this.beginPanelInput(callbackQuery, "/request", "\ud83c\udfac 请输入要搜索的电影或剧集名称。");
               break;
            case "request_guide":
               this.sendMessage(callbackQuery.getFrom().getId(), "\ud83c\udfac 发送 `/request 片名` 搜索 TMDB，选择结果后点击提交求片。\n也可以使用 `@机器人 影片名` 内联搜索，体验更好；");
               break;
            case "checkin":
               this.dispatchPanelCommand(callbackQuery, "/checkin");
               break;
            case "points":
               this.dispatchPanelCommand(callbackQuery, "/points");
               break;
            case "foambag":
               this.dispatchPanelCommand(callbackQuery, "/foambag");
               break;
            case "exchange":
               this.dispatchPanelCommand(callbackQuery, "/exchange");
               break;
            case "prizes":
               this.dispatchPanelCommand(callbackQuery, "/prizes");
               break;
            case "redeem":
            case "recharge":
               this.dispatchPanelCommand(callbackQuery, "/exchange");
               break;
            case "bind":
               this.handleStartPanelBindAction(callbackQuery, panelMessage);
               break;
            case "unbind":
               this.dispatchPanelCommand(callbackQuery, "/unbind");
               break;
            case "cancelreview":
               this.dispatchPanelCommand(callbackQuery, "/cancelreview");
               break;
            case "register":
               this.beginPanelInput(callbackQuery, "/register", "\ud83d\udc51 请输入：`Emby用户名 密码`\n\ud83d\udd10 密码长度需为 6-30 位。");
               break;
            case "cardopen":
               this.beginPanelInput(callbackQuery, "/cardopen", "\ud83c\udf9f️ 请输入：`卡密 Emby用户名 [密码]`\n不填写密码时系统会自动生成。");
               break;
            case "cardrenew":
               this.beginPanelInput(callbackQuery, "/cardrenew", "♻️ 请输入卡密；续费对象为当前 Telegram 绑定账号。");
         }
      }
   }

   private void handlePointsExchangeCallback(CallbackQuery callbackQuery, String data) {
      Message panelMessage = this.resolvePrivateStartPanelMessage(callbackQuery);
      if (panelMessage != null) {
         String[] parts = data.split(":", 3);
         if (parts.length >= 2) {
            String action = parts[1];
            if ("points".equals(action)) {
               this.dispatchPanelCommand(callbackQuery, "/points");
            } else if ("cancel".equals(action)) {
               this.renderStartPanelPage(panelMessage, callbackQuery.getFrom().getId(), "points");
            } else if (parts.length == 3) {
               long configId;
               try {
                  configId = Long.parseLong(parts[2]);
               } catch (NumberFormatException var10) {
                  this.editStartPanelMessage(
                     panelMessage,
                     this.foamPanelTitle("积分中心") + "\ud83c\udf19 这个兑换项目已经找不到了，请返回后重新选择。",
                     this.keyboard(List.of(this.button("↩️ 返回积分中心", "start_panel:points")), 1)
                  );
                  return;
               }

               if (configId > 0L) {
                  switch (action) {
                     case "create":
                        this.beginPanelInput(callbackQuery, "/redeem " + configId, "\ud83d\udc51 请输入：`Emby用户名 [密码]`\n不填写密码时，系统会自动生成 6 位随机密码。");
                        break;
                     case "renew":
                        this.editStartPanelMessage(
                           panelMessage,
                           this.foamPanelTitle("积分续费") + "♻️ 将使用项目 `#" + configId + "` 为当前 Telegram 绑定的 Emby 账号续费。\n\n确认后才会扣除积分并执行续费。",
                           this.keyboard(
                              List.of(this.button("✅ 确认续费", "points_exchange:renew_confirm:" + configId), this.button("↩️ 返回积分中心", "points_exchange:cancel")),
                              2
                           )
                        );
                        break;
                     case "renew_confirm":
                        this.dispatchPanelCommand(callbackQuery, "/recharge " + configId);
                  }
               }
            }
         }
      }
   }

   private void handleStartPanelBindAction(CallbackQuery callbackQuery, Message panelMessage) {
      long telegramUserId = callbackQuery.getFrom().getId();
      EmbyUser boundUser = this.telegramBindingManager.findBoundUser(telegramUserId, false);
      if (boundUser == null) {
         this.beginPanelInput(callbackQuery, "/bind", "\ud83d\udd17 请输入：`Emby用户名 密码`");
      } else {
         this.pendingPanelCommands.remove(telegramUserId);
         String embyUserName = StringUtils.hasText(boundUser.getEmbyUserName()) ? boundUser.getEmbyUserName() : "当前账号";
         this.editStartPanelMessage(
            panelMessage,
            this.foamPanelTitle("绑定与卡密") + "✅ 已绑定 Emby 账号：`" + this.escapeMarkdown(embyUserName) + "`\n\n如需更换绑定，请先解除当前绑定。",
            this.keyboard(List.of(this.button("↩️ 解除绑定", "start_action:unbind"), this.button("\ud83d\udd17 返回绑定中心", "start_panel:binding")), 2)
         );
      }
   }

   private void handleStartPanelAdminCallback(CallbackQuery callbackQuery, String command) {
      if (this.resolvePrivateStartPanelMessage(callbackQuery) != null && StringUtils.hasText(command)) {
         long operatorId = callbackQuery.getFrom().getId();
         String normalizedCommand = "/" + command.toLowerCase(Locale.ROOT);
         if ("/libraryaccess".equals(normalizedCommand)) {
            if (!this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.LIBRARY_ACCESS)) {
               this.sendMessage(operatorId, "❌ 当前账号没有设置媒体库分级的权限。");
            } else {
               this.renderLibraryAccessServers((Message)callbackQuery.getMessage(), operatorId);
            }
         } else if (!"/scratchwins".equals(normalizedCommand)) {
            TelegramBotPermission permission = this.permissionForCommand(normalizedCommand);
            if (permission == null || !this.telegramBotAuthorizationService.hasPermission(operatorId, permission)) {
               this.sendMessage(operatorId, "❌ 当前账号没有执行此管理操作的权限。");
            } else if (!"/callall".equals(normalizedCommand) && !"/enableuser".equals(normalizedCommand) && !"/disableuser".equals(normalizedCommand)) {
               this.beginPanelInput(callbackQuery, normalizedCommand, this.adminCommandPrompt(normalizedCommand));
            } else {
               this.dispatchPanelCommand(callbackQuery, normalizedCommand);
            }
         } else if (!this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.SCRATCH_RECORD_VIEW)) {
            this.sendMessage(operatorId, "❌ 当前账号没有查看刮刮乐大奖记录的权限。");
         } else {
            this.dispatchPanelCommand(callbackQuery, normalizedCommand);
         }
      }
   }

   private String adminCommandPrompt(String command) {
      return switch (command) {
         case "/kk" -> "\ud83d\udc64 请输入 Telegram ID、@用户名、Emby 用户名或关键词。";
         case "/edituser" -> "\ud83d\udd0e 请输入要搜索的 Emby 用户名关键词。";
         case "/whitelist" -> "✅ 请输入目标 Telegram ID 或 Emby 用户名。";
         case "/unwhitelist" -> "↩️ 请输入：`目标 Telegram ID或Emby用户名 有效天数`";
         case "/updateuserinfo" -> "\ud83d\udcdd 请输入：`yyyy-MM-dd HH:mm:ss 求片次数 备注`\n请先通过“搜索并修改用户”选择目标用户。";
         case "/setexpiry" -> "⏰ 请输入新的到期时间：`yyyy-MM-dd HH:mm:ss`\n请先通过“搜索并修改用户”选择目标用户。";
         case "/resetpassword" -> "\ud83d\udd11 请输入新密码。\n请先通过“搜索并修改用户”选择目标用户。";
         case "/createuser" -> "\ud83d\udc51 请输入：`用户名 天数 备注`";
         case "/generatecards" -> "\ud83c\udf9f️ 请输入：`数量 天数`";
         case "/extendusers" -> "♻️ 请输入：`延期天数 [过期天数范围]`";
         case "/renew" -> "♻️ 请输入：`Emby用户名|天数`\n或：`Emby用户名|服务器ID|天数`";
         default -> "⌨️ 请输入此操作需要的参数。";
      };
   }

   private void beginPanelInput(CallbackQuery callbackQuery, String command, String prompt) {
      Message panelMessage = this.resolvePrivateStartPanelMessage(callbackQuery);
      if (panelMessage != null) {
         long userId = callbackQuery.getFrom().getId();
         this.pendingPanelCommands
            .put(
               userId,
               new DataQueryBot.PendingPanelCommand(
                  command, System.currentTimeMillis() + START_PANEL_INPUT_TTL_MILLIS, panelMessage, this.panelPageForCommand(command), null
               )
            );
         InlineKeyboardMarkup keyboard = this.keyboard(List.of(this.button("❌ 取消输入", "start_input:cancel")), 1);
         this.editStartPanelMessage(
            panelMessage, this.foamPanelTitle(this.panelInputTitle(command)) + prompt + "\n\n⏳ 请在 10 分钟内发送；输入 `/cancel` 也可取消。", keyboard
         );
      }
   }

   private String panelInputTitle(String command) {
      String normalizedCommand = this.extractNormalizedCommand(command);

      return switch (normalizedCommand) {
         case "/request" -> "求片中心";
         case "/redeem", "/recharge" -> "积分中心";
         case "/bind", "/register", "/cardopen", "/cardrenew" -> "绑定与卡密";
         case "/kk", "/edituser", "/whitelist", "/unwhitelist", "/updateuserinfo", "/setexpiry", "/resetpassword" -> "用户管理";
         case "/createuser" -> "用户创建";
         case "/generatecards" -> "卡密管理";
         case "/extendusers" -> "用户延期";
         case "/renew" -> "管理续期";
         default -> "服务中心";
      };
   }

   private void handleStartPanelInputCancel(CallbackQuery callbackQuery) {
      Message message = this.resolvePrivateStartPanelMessage(callbackQuery);
      if (message != null) {
         long userId = callbackQuery.getFrom().getId();
         DataQueryBot.PendingPanelCommand pending = this.pendingPanelCommands.remove(userId);
         this.restorePendingPanel(userId, pending, message, "ℹ️ 已取消本次输入。");
      }
   }

   private boolean handlePendingTelegramMemberMute(Message message) {
      if (message != null && message.hasText() && message.getFrom() != null && message.getFrom().getId() != null) {
         long operatorId = message.getFrom().getId();
         DataQueryBot.PendingTelegramMemberMute pending = this.pendingTelegramMemberMutes.get(operatorId);
         if (pending == null
            || pending.getPanelMessage() == null
            || !Objects.equals(pending.getPanelMessage().getChatId(), message.getChatId())
            || message.isUserMessage()) {
            return false;
         } else if (pending.isExpired()) {
            this.pendingTelegramMemberMutes.remove(operatorId, pending);
            this.renderAdminTelegramMemberPanel(operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "⏰ 本次输入已过期，请重新点击禁言按钮。");
            return false;
         } else {
            String input = message.getText().trim();
            if (!this.isExplicitGroupMuteInput(message, pending.getPanelMessage(), input)) {
               return false;
            } else {
               this.deleteMessageSilently(message.getChatId(), message.getMessageId());
               if ("/cancel".equalsIgnoreCase(input)) {
                  this.pendingTelegramMemberMutes.remove(operatorId, pending);
                  this.renderAdminTelegramMemberPanel(operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "ℹ️ 已取消本次输入。");
                  return true;
               } else if (this.canAuthorizedAdminModerateGroup(operatorId)
                  && this.telegramBotAuthorizationService.isConfiguredManagementGroup(message.getChatId())) {
                  int minutes;
                  try {
                     minutes = Integer.parseInt(input);
                  } catch (NumberFormatException var11) {
                     this.renderAdminTelegramMemberPanel(
                        operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "❌ 禁言时间请输入整数分钟，例如 `30`。"
                     );
                     return true;
                  }

                  if (minutes >= 1 && minutes <= 525600) {
                     this.pendingTelegramMemberMutes.remove(operatorId, pending);
                     if (!this.tryAcquireRateLimit("admin_mutation:" + operatorId, 30L, 60L)) {
                        this.renderAdminTelegramMemberPanel(operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "⏳ 操作过于频繁，请稍后再试。");
                        return true;
                     } else {
                        try {
                           long targetTelegramUserId = pending.getTarget().getTelegramUserId();
                           this.requireModeratableTelegramMember(message, targetTelegramUserId, operatorId);
                           this.telegramClient.restrictChatMember(message.getChatId(), targetTelegramUserId, minutes);
                           this.renderAdminTelegramMemberPanel(
                              operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "\ud83c\udf19 禁言已生效，水面会安静 " + minutes + " 分钟。"
                           );
                        } catch (BizException var9) {
                           this.renderAdminTelegramMemberPanel(
                              operatorId,
                              pending.getTarget(),
                              pending.getPanelMessage(),
                              message.getChatId(),
                              "❌ 操作失败：" + this.escapeMarkdown(var9.getMessage())
                           );
                        } catch (TelegramBotApiClient.TelegramBotApiException var10) {
                           this.renderAdminTelegramMemberPanel(
                              operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "❌ Telegram 拒绝了这次操作，请检查群类型及机器人的成员管理权限。"
                           );
                        }

                        return true;
                     }
                  } else {
                     this.renderAdminTelegramMemberPanel(
                        operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "❌ 禁言时间需要在 1 到 525600 分钟之间。"
                     );
                     return true;
                  }
               } else {
                  this.pendingTelegramMemberMutes.remove(operatorId, pending);
                  this.renderAdminTelegramMemberPanel(operatorId, pending.getTarget(), pending.getPanelMessage(), message.getChatId(), "❌ 当前授权状态已变化，操作没有执行。");
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private boolean handlePendingPanelCommand(Message message) {
      if (message != null && message.hasText() && message.getFrom() != null && message.getFrom().getId() != null) {
         long userId = message.getFrom().getId();
         DataQueryBot.PendingPanelCommand pending = this.pendingPanelCommands.get(userId);
         if (pending == null) {
            return false;
         } else {
            boolean privateInput = message.isUserMessage() && userId == message.getChatId();
            Message pendingPanel = pending.getPanelMessage();
            boolean groupMuteInput = "/panelmute".equals(pending.getCommand())
               && pendingPanel != null
               && !pendingPanel.isUserMessage()
               && Objects.equals(pendingPanel.getChatId(), message.getChatId());
            if (!privateInput && !groupMuteInput) {
               return false;
            } else if (pending.isExpired()) {
               this.pendingPanelCommands.remove(userId, pending);
               this.restorePendingPanel(userId, pending, pending.getPanelMessage(), "⏰ 本次输入已过期，请重新点击对应按钮。");
               return privateInput;
            } else {
               String input = message.getText().trim();
               if (groupMuteInput && !this.isExplicitGroupMuteInput(message, pendingPanel, input)) {
                  return false;
               } else {
                  this.deleteMessageSilently(message.getChatId(), message.getMessageId());
                  if (input.startsWith("/")) {
                     this.pendingPanelCommands.remove(userId, pending);
                     if ("/cancel".equalsIgnoreCase(input)) {
                        this.restorePendingPanel(userId, pending, pending.getPanelMessage(), "ℹ️ 已取消本次输入。");
                        return true;
                     } else {
                        this.restorePendingPanel(userId, pending, pending.getPanelMessage(), null);
                        return false;
                     }
                  } else if (pending.getTargetUserId() != null) {
                     return this.handlePendingAdminUserAction(message, pending, input);
                  } else if ("/kk".equals(pending.getCommand())) {
                     return this.handlePendingAdminUserSearch(message, pending, input);
                  } else {
                     this.pendingPanelCommands.remove(userId, pending);
                     this.restorePendingPanel(userId, pending, pending.getPanelMessage(), null);
                     this.dispatchPanelCommand(this.buildPanelCommandMessage(message, pending.getCommand() + " " + input));
                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   private boolean isExplicitGroupMuteInput(Message message, Message panelMessage, String input) {
      if ("/cancel".equalsIgnoreCase(input)) {
         return true;
      } else if (StringUtils.hasText(input) && input.matches("[0-9]+")) {
         Message repliedMessage = message.getReplyToMessage();
         return repliedMessage != null
            && panelMessage != null
            && Objects.equals(repliedMessage.getChatId(), panelMessage.getChatId())
            && Objects.equals(repliedMessage.getMessageId(), panelMessage.getMessageId());
      } else {
         return false;
      }
   }

   private String panelPageForCommand(String command) {
      String normalizedCommand = this.extractNormalizedCommand(command);
      if (Set.of("/redeem", "/recharge", "/renew").contains(normalizedCommand)) {
         return "/renew".equals(normalizedCommand) ? "admin" : "points";
      } else if (Set.of("/bind", "/register", "/cardopen", "/cardrenew").contains(normalizedCommand)) {
         return "binding";
      } else if ("/request".equals(normalizedCommand)) {
         return "request";
      } else {
         return this.permissionForCommand(normalizedCommand) != null ? "admin" : "home";
      }
   }

   private void restorePendingPanel(long operatorId, DataQueryBot.PendingPanelCommand pending, Message fallbackMessage, String notice) {
      Message panelMessage = pending != null && pending.getPanelMessage() != null ? pending.getPanelMessage() : fallbackMessage;
      if (panelMessage != null) {
         if (pending != null && pending.getTargetUserId() != null) {
            EmbyUser target = this.embyUserService.getById(pending.getTargetUserId());
            this.renderAdminUserPanel(operatorId, target, panelMessage, notice);
         } else {
            String returnPanel = pending == null ? "home" : pending.getReturnPanel();
            this.renderStartPanelPage(panelMessage, operatorId, StringUtils.hasText(returnPanel) ? returnPanel : "home");
         }
      }
   }

   private boolean handlePendingAdminUserSearch(Message message, DataQueryBot.PendingPanelCommand pending, String input) {
      long operatorId = message.getFrom().getId();
      if (!this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_VIEW)) {
         this.pendingPanelCommands.remove(operatorId, pending);
         this.restorePendingPanel(operatorId, pending, pending.getPanelMessage(), "❌ 当前账号没有查看用户的权限。");
         return true;
      } else {
         EmbyUser exact = this.resolveAdminCommandTarget(message, input, false);
         if (exact != null && this.isKkProtectedEmbyTarget(exact)) {
            this.pendingPanelCommands.remove(operatorId, pending);
            this.editStartPanelMessage(pending.getPanelMessage(), "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
            return true;
         } else if (exact != null && this.canBotOperatorViewTarget(operatorId, exact)) {
            this.pendingPanelCommands.remove(operatorId, pending);
            this.pendingUserEdits.put(operatorId, new DataQueryBot.PendingUserEdit(operatorId, exact.getId()));
            this.renderAdminUserPanel(operatorId, exact, pending.getPanelMessage(), null);
            return true;
         } else {
            String keyword = input.startsWith("@") ? input.substring(1) : input;
            List<EmbyUser> matches = this.embyUserService
               .lambdaQuery()
               .like(EmbyUser::getEmbyUserName, keyword)
               .orderByAsc(EmbyUser::getEmbyUserName)
               .last("limit 10")
               .list()
               .stream()
               .filter(candidatex -> this.canBotOperatorViewTarget(operatorId, candidatex) && !this.isKkProtectedEmbyTarget(candidatex))
               .toList();
            if (matches.isEmpty()) {
               this.pendingPanelCommands.put(operatorId, pending.refresh());
               this.editStartPanelMessage(
                  pending.getPanelMessage(),
                  this.foamPanelTitle("用户管理") + "\ud83d\udd0e 没有找到可管理的用户。\n\n请重新输入 Telegram ID、@用户名、Emby 用户名或关键词。\n\n⏳ 输入状态已保留 10 分钟。",
                  this.keyboard(List.of(this.button("❌ 取消搜索", "start_input:cancel")), 1)
               );
               return true;
            } else {
               this.pendingPanelCommands.remove(operatorId, pending);
               List<InlineKeyboardRow> rows = new ArrayList<>();

               for (EmbyUser candidate : matches) {
                  String statusEmoji = Integer.valueOf(1).equals(candidate.getUserStatus()) ? "\ud83d\udd34" : "\ud83d\udfe2";
                  rows.add(
                     new InlineKeyboardRow(
                        this.button(
                           statusEmoji + " " + candidate.getEmbyUserName() + this.formatExpirationDate(candidate.getExpirationDate()),
                           "edit_user:" + candidate.getId()
                        )
                     )
                  );
               }

               rows.add(new InlineKeyboardRow(this.button("\ud83d\udee1️ 返回管理中心", "start_panel:admin")));
               this.editStartPanelMessage(
                  pending.getPanelMessage(),
                  this.foamPanelTitle("用户管理") + "\ud83d\udc65 请选择要管理的用户。\n\n关键词：`" + this.escapeMarkdown(input) + "`\n最多显示 10 个匹配结果。",
                  InlineKeyboardMarkup.builder().keyboard(rows).build()
               );
               return true;
            }
         }
      }
   }

   private void dispatchPanelCommand(CallbackQuery callbackQuery, String commandText) {
      Message panelMessage = this.resolvePrivateStartPanelMessage(callbackQuery);
      if (panelMessage != null) {
         this.dispatchPanelCommand(
            Message.builder().messageId(panelMessage.getMessageId()).chat(panelMessage.getChat()).from(callbackQuery.getFrom()).text(commandText).build()
         );
      }
   }

   private Message buildPanelCommandMessage(Message source, String commandText) {
      return Message.builder().messageId(source.getMessageId()).chat(source.getChat()).from(source.getFrom()).text(commandText).build();
   }

   private void dispatchPanelCommand(Message commandMessage) {
      String command = this.extractNormalizedCommand(commandMessage.getText());
      if (this.isPointsPanelCommand(command)) {
         this.pointsBot.consume(com.una.embyhub.pointsbot.telegram.Update.message(this.toPointsBotMessage(commandMessage)));
      } else {
         this.handleTextMessage(commandMessage);
      }
   }

   private boolean isPointsPanelCommand(String command) {
      return Set.of("/checkin", "/points", "/foambag", "/exchange", "/prizes", "/redeem", "/recharge", "/renew", "/scratchwins").contains(command);
   }

   private void handleAdminTelegramMemberPanelCallback(CallbackQuery callbackQuery, String data) {
      if (callbackQuery.getMessage() instanceof Message message && callbackQuery.getFrom() != null && callbackQuery.getFrom().getId() != null) {
         String[] parts = data.split(":", 3);
         if (parts.length != 3) {
            this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 这片管理薄雾已经散去，请重新打开。");
            return;
         }

         String token = parts[1];
         String action = parts[2];
         String sessionKey = "bot:admin:telegram-panel:" + token;
         String session = this.stringRedisTemplate.opsForValue().get(sessionKey);
         if (!StringUtils.hasText(session)) {
            this.answerCallbackQuery(callbackQuery.getId(), "⏳ 面板已过期，请重新回复目标成员发送 `/kk`。");
            return;
         }

         String[] sessionParts = session.split(":", 2);
         long operatorId = sessionParts.length == 2 ? this.parseTelegramId(sessionParts[0]) : 0L;
         long targetTelegramUserId = sessionParts.length == 2 ? this.parseTelegramId(sessionParts[1]) : 0L;
         if (operatorId > 0L && targetTelegramUserId > 0L) {
            if (callbackQuery.getFrom().getId() != operatorId) {
               this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf01 这片管理薄雾正由发起它的管理员守护哦～请让 TA 来轻轻操作吧。");
               return;
            }

            if (this.telegramBotAuthorizationService.isConfiguredManagementGroup(message.getChatId()) && this.canAuthorizedAdminModerateGroup(operatorId)) {
               if ("gift".equals(action) && !this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_CREATE)) {
                  this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 当前没有创建账号的授权，请联系渠道 Owner 调整权限。");
                  return;
               }

               if (this.isKkProtectedTelegramTarget(targetTelegramUserId)) {
                  this.stringRedisTemplate.delete(sessionKey);
                  this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
                  this.editStartPanelMessage(message, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
                  return;
               }

               this.answerCallbackQuery(callbackQuery.getId());
               DataQueryBot.TelegramKkTarget target = this.loadTelegramKkTarget(message.getChatId(), targetTelegramUserId);
               if ("close".equals(action)) {
                  this.stringRedisTemplate.delete(sessionKey);
                  this.pendingTelegramMemberMutes.remove(operatorId);

                  try {
                     this.telegramClient.execute(DeleteMessage.builder().chatId(message.getChatId()).messageId(message.getMessageId()).build());
                  } catch (TelegramApiException var22) {
                     this.editStartPanelMessage(message, "\ud83c\udf01 这片管理薄雾已经轻轻散去。", null);
                  }

                  return;
               }

               if ("gift".equals(action)) {
                  EmbyUser boundUser = this.telegramBindingManager.findBoundUser(targetTelegramUserId, false);
                  if (boundUser != null) {
                     this.stringRedisTemplate.delete(sessionKey);
                     if (this.isKkProtectedEmbyTarget(boundUser)) {
                        this.editStartPanelMessage(message, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
                     } else {
                        this.renderAdminUserPanel(operatorId, boundUser, message, "\ud83c\udf01 这位成员已经关联 Mist 账号，已切换到账号面板。");
                     }

                     return;
                  }

                  TelegramResponse telegram = this.telegramClientUtils.getTelegramResponse();
                  String botName = telegram == null ? null : telegram.getBotName();
                  if (!StringUtils.hasText(botName)) {
                     this.renderAdminTelegramMemberPanel(operatorId, target, message, message.getChatId(), "❌ 机器人用户名尚未配置，暂时无法发送开户邀请。");
                     return;
                  }

                  String grantToken = this.createKkRegistrationGrant(operatorId, targetTelegramUserId);
                  boolean deliveredPrivately = this.sendKkRegistrationInvitationToTarget(target, botName, grantToken);
                  this.stringRedisTemplate.delete(sessionKey);
                  String normalizedBotName = botName.startsWith("@") ? botName : "@" + botName;
                  String confirmation = this.foamPanelTitle("群成员")
                     + "\ud83c\udf81 "
                     + this.telegramUserMention(callbackQuery.getFrom())
                     + " 已为 "
                     + this.telegramUserMention(targetTelegramUserId, target.getDisplayName())
                     + " 准备好开户资格。\n\n"
                     + (
                        deliveredPrivately
                           ? "\ud83d\udce8 领取入口已经发送到目标用户私聊。"
                           : "\ud83d\udcac 机器人暂时无法主动私聊目标用户，请让 TA 私聊 " + this.escapeMarkdown(normalizedBotName) + " 并发送 `/start` 领取。"
                     )
                     + "\n\n\ud83c\udf01 本提示将在 60 秒后自动收起。";
                  this.editStartPanelMessage(message, confirmation, null);
                  this.scheduleGroupMessageCleanup(message.getChatId(), message.getMessageId(), 60L, TimeUnit.SECONDS);
                  return;
               }

               if ("mute".equals(action)) {
                  this.pendingTelegramMemberMutes
                     .put(operatorId, new DataQueryBot.PendingTelegramMemberMute(target, message, System.currentTimeMillis() + START_PANEL_INPUT_TTL_MILLIS));
                  this.editStartPanelMessage(
                     message,
                     this.foamPanelTitle("群成员") + "\ud83d\udd07 请回复本面板输入禁言分钟数：`1-525600`\n例如发送 `30`，表示禁言 30 分钟。\n\n⏳ 请在 10 分钟内发送；输入 `/cancel` 可取消。",
                     null
                  );
                  return;
               }

               if (!Set.of("kickban", "unmute").contains(action)) {
                  return;
               }

               if (!this.tryAcquireRateLimit("admin_mutation:" + operatorId, 30L, 60L)) {
                  this.renderAdminTelegramMemberPanel(operatorId, target, message, message.getChatId(), "⏳ 操作过于频繁，请稍后再试。");
                  return;
               }

               this.stringRedisTemplate.delete(sessionKey);

               try {
                  if ("kickban".equals(action)) {
                     this.requireModeratableTelegramMember(message, targetTelegramUserId, operatorId);
                     this.telegramClient.banChatMember(message.getChatId(), targetTelegramUserId);
                     this.renderAdminTelegramMemberPanel(operatorId, target, message, message.getChatId(), "\ud83c\udf19 已将这位成员移出并封禁。");
                  } else {
                     this.requireModeratableTelegramMember(message, targetTelegramUserId, operatorId);
                     this.telegramClient.unrestrictChatMember(message.getChatId(), targetTelegramUserId);
                     this.renderAdminTelegramMemberPanel(operatorId, target, message, message.getChatId(), "\ud83c\udf0a 已解除禁言，可以重新发言了。");
                  }
               } catch (BizException var23) {
                  this.renderAdminTelegramMemberPanel(operatorId, target, message, message.getChatId(), "❌ 操作失败：" + this.escapeMarkdown(var23.getMessage()));
               } catch (TelegramBotApiClient.TelegramBotApiException var24) {
                  this.renderAdminTelegramMemberPanel(operatorId, target, message, message.getChatId(), "❌ Telegram 拒绝了这次操作，请检查群类型及机器人的成员管理权限。");
               }

               return;
            }

            this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 这颗按钮不在你当前的授权范围里。");
            return;
         }

         this.stringRedisTemplate.delete(sessionKey);
         this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 面板状态有些迷路了，请重新打开。");
         return;
      }

      this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 这颗按钮暂时无法响应，请稍后再试。");
   }

   private DataQueryBot.TelegramKkTarget loadTelegramKkTarget(long chatId, long telegramUserId) {
      try {
         ChatMember member = this.telegramClient.execute(GetChatMember.builder().chatId(chatId).userId(telegramUserId).build());
         if (member != null && member.getUser() != null) {
            return DataQueryBot.TelegramKkTarget.from(member.getUser());
         }
      } catch (Exception var6) {
         log.debug("读取 Telegram 成员资料失败: chatId={}, telegramUserId={}", chatId, telegramUserId);
      }

      return new DataQueryBot.TelegramKkTarget(telegramUserId, String.valueOf(telegramUserId), null);
   }

   private boolean sendKkRegistrationInvitationToTarget(DataQueryBot.TelegramKkTarget target, String botName, String grantToken) {
      InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
         .keyboard(
            List.of(
               new InlineKeyboardRow(
                  InlineKeyboardButton.builder().text("\ud83c\udf81 领取并创建账号").url(this.buildPrivateBotUrl(botName, "kkreg_" + grantToken)).build()
               )
            )
         )
         .build();

      try {
         this.telegramClient
            .execute(
               SendMessage.builder()
                  .chatId(target.getTelegramUserId())
                  .text("\ud83c\udf81 *一份 Mist 账号礼物已经送达*\n\n点击下方按钮后输入想使用的 Emby 用户名；登录密码会由系统生成，并且只在这个私聊中发送。")
                  .parseMode("Markdown")
                  .replyMarkup(keyboard)
                  .build()
            );
         return true;
      } catch (Exception var6) {
         log.info("Telegram /kk 开户邀请无法主动私聊发送: telegramUserId={}, error={}", target.getTelegramUserId(), var6.getMessage());
         return false;
      }
   }

   private void handleAdminUserPanelCallback(CallbackQuery callbackQuery, String data) {
      if (callbackQuery.getMessage() instanceof Message message && callbackQuery.getFrom() != null && callbackQuery.getFrom().getId() != null) {
         boolean privatePanel = message.isUserMessage();
         if (privatePanel && !callbackQuery.getFrom().getId().equals(message.getChatId())) {
            this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf01 这片管理薄雾正由发起它的管理员守护哦～请让 TA 来轻轻操作吧。");
            return;
         }

         String[] parts = data.split(":", 3);
         if (parts.length != 3) {
            this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 这颗按钮已经找不到回家的路啦，请重新打开面板。");
            return;
         }

         long operatorId = callbackQuery.getFrom().getId();
         String token = parts[1];
         String action = parts[2];
         String sessionKey = "bot:admin:panel:" + token;
         String session = this.stringRedisTemplate.opsForValue().get(sessionKey);
         if (!StringUtils.hasText(session)) {
            this.answerCallbackQuery(callbackQuery.getId(), "⏳ 这片管理薄雾已经轻轻散去，请让管理员重新打开面板吧。");
            if (privatePanel) {
               this.editStartPanelMessage(
                  message,
                  this.foamPanelTitle("用户管理") + "⏳ 用户管理面板已过期，请返回管理中心重新选择用户。",
                  this.keyboard(List.of(this.button("\ud83d\udee1️ 返回管理中心", "start_panel:admin")), 1)
               );
            }

            return;
         }

         String[] sessionParts = session.split(":", 2);
         if (sessionParts.length != 2) {
            this.stringRedisTemplate.delete(sessionKey);
            this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 面板状态有些迷路了，请重新打开一次。");
            return;
         }

         long sessionOperator = this.parseTelegramId(sessionParts[0]);
         long targetUserId = this.parseTelegramId(sessionParts[1]);
         if (sessionOperator != operatorId) {
            log.warn("拒绝非法 Telegram 管理面板回调: operatorId={}, token={}", operatorId, token);
            this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf01 这片管理薄雾正由发起它的管理员守护哦～请让 TA 来轻轻操作吧。");
            return;
         }

         if (targetUserId <= 0L) {
            this.stringRedisTemplate.delete(sessionKey);
            this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 没有找到这片账号薄雾，请重新打开面板。");
            return;
         }
         TelegramBotPermission requiredPermission = switch (action) {
            case "refresh", "back", "close" -> TelegramBotPermission.USER_VIEW;
            case "enable", "disable", "update", "expiry" -> TelegramBotPermission.USER_STATUS;
            case "kickban", "mute", "unmute" -> TelegramBotPermission.USER_VIEW;
            case "password" -> TelegramBotPermission.USER_PASSWORD;
            case "whitelist", "unwhite7", "unwhite30", "unwhite90" -> TelegramBotPermission.USER_WHITELIST;
            default -> null;
         };
         boolean allowedLocation = privatePanel || this.telegramBotAuthorizationService.isConfiguredManagementGroup(message.getChatId());
         if (requiredPermission != null && allowedLocation && this.telegramBotAuthorizationService.hasPermission(operatorId, requiredPermission)) {
            this.answerCallbackQuery(callbackQuery.getId());
            EmbyUser target = this.embyUserService.getById(Long.valueOf(targetUserId));
            if (target != null && this.isKkProtectedEmbyTarget(target)) {
               this.stringRedisTemplate.delete(sessionKey);
               this.editStartPanelMessage(message, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
               return;
            }

            if (target != null && this.canBotOperatorViewTarget(operatorId, target)) {
               if ("back".equals(action)) {
                  this.stringRedisTemplate.delete(sessionKey);
                  this.pendingUserEdits.remove(operatorId);
                  this.pendingPanelCommands.remove(operatorId);
                  this.renderStartPanelPage(message, operatorId, "admin");
                  return;
               }

               if ("close".equals(action)) {
                  this.stringRedisTemplate.delete(sessionKey);
                  this.pendingPanelCommands.remove(operatorId);

                  try {
                     this.telegramClient.execute(DeleteMessage.builder().chatId(message.getChatId()).messageId(message.getMessageId()).build());
                  } catch (TelegramApiException var24) {
                     if (message.hasPhoto()) {
                        this.editStartPanelMessage(message, this.foamPanelTitle("管理中心") + "这片管理薄雾已经轻轻散去。", null);
                     } else {
                        this.editMessageTextSilently(message.getChatId(), message.getMessageId(), "\ud83c\udf01 这片管理薄雾已经轻轻散去。");
                     }
                  }

                  return;
               }

               if ("refresh".equals(action)) {
                  this.stringRedisTemplate.delete(sessionKey);
                  this.pendingPanelCommands.remove(operatorId);
                  this.renderAdminUserPanel(operatorId, target, message, null);
                  return;
               }

               if ("mute".equals(action)) {
                  if (privatePanel) {
                     this.renderAdminUserPanel(operatorId, target, message, "\ud83d\udd12 禁言操作只能在群聊 `/kk` 面板中完成。");
                     return;
                  }

                  if (message.getChat() != null && Boolean.TRUE.equals(message.getChat().isSuperGroupChat())) {
                     this.beginAdminUserMuteInput(operatorId, target, message, token);
                     return;
                  }

                  this.renderAdminUserPanel(operatorId, target, message, "\ud83c\udf19 当前是 Telegram 基础群，平台不支持对单个成员设置禁言。请先将群升级为超级群，再使用这个按钮。");
                  return;
               }

               if (Set.of("update", "expiry", "password").contains(action)) {
                  if (!privatePanel) {
                     this.renderAdminUserPanel(operatorId, target, message, "\ud83d\udd12 这项操作需要输入敏感信息，请在私聊管理中心完成。");
                     return;
                  }

                  this.stringRedisTemplate.delete(sessionKey);
                  this.beginAdminUserPanelInput(operatorId, target, message, action);
                  return;
               }

               if (!this.tryAcquireRateLimit("admin_mutation:" + operatorId, 30L, 60L)) {
                  this.renderAdminUserPanel(operatorId, target, message, "⏳ 操作过于频繁，请稍后再试。");
                  return;
               }

               this.stringRedisTemplate.delete(sessionKey);

               try {
                  boolean owner = this.isBotOwner(operatorId);
                  String actionTargetMention = "kickban".equals(action) && !privatePanel ? this.telegramBoundUserMention(message.getChatId(), target) : null;
                  switch (action) {
                     case "enable":
                        this.embyUserService.enableUserByBot(targetUserId, owner);
                        break;
                     case "disable":
                        this.embyUserService.disableUserByBot(targetUserId, owner);
                        break;
                     case "whitelist":
                        this.embyUserService.updateUserWhitelistByBot(targetUserId, true, null, owner);
                        break;
                     case "unwhite7":
                        this.embyUserService.updateUserWhitelistByBot(targetUserId, false, 7, owner);
                        break;
                     case "unwhite30":
                        this.embyUserService.updateUserWhitelistByBot(targetUserId, false, 30, owner);
                        break;
                     case "unwhite90":
                        this.embyUserService.updateUserWhitelistByBot(targetUserId, false, 90, owner);
                        break;
                     case "kickban":
                        this.kickBanTelegramMember(message, target, operatorId);
                        break;
                     case "unmute":
                        this.unmuteTelegramMember(message, target, operatorId);
                        break;
                     default:
                        return;
                  }

                  log.info("Telegram 管理面板操作完成: operatorId={}, targetUserId={}, action={}", operatorId, targetUserId, action);
                  EmbyUser refreshed = this.embyUserService.getById(Long.valueOf(targetUserId));
                  if ("whitelist".equals(action) && !privatePanel) {
                     this.sendPersistentMessage(
                        message.getChatId(),
                        this.buildWhitelistCelebrationMessage(message.getChatId(), refreshed == null ? target : refreshed, callbackQuery.getFrom())
                     );
                  }

                  this.renderAdminUserPanel(
                     operatorId, refreshed, message, this.adminUserActionSuccessNotice(action, callbackQuery.getFrom(), !privatePanel, actionTargetMention)
                  );
               } catch (BizException var25) {
                  this.renderAdminUserPanel(
                     operatorId, this.embyUserService.getById(Long.valueOf(targetUserId)), message, "❌ 操作失败：" + this.escapeMarkdown(var25.getMessage())
                  );
               } catch (TelegramBotApiClient.TelegramBotApiException var26) {
                  log.warn("Telegram 群管理 API 拒绝操作: operatorId={}, targetUserId={}, action={}, error={}", operatorId, targetUserId, action, var26.getMessage());
                  this.renderAdminUserPanel(
                     operatorId,
                     this.embyUserService.getById(Long.valueOf(targetUserId)),
                     message,
                     "❌ Telegram 拒绝了这次操作：" + this.escapeMarkdown(var26.getMessage()) + "\n\n请检查群类型，以及机器人是否拥有“限制成员”权限。"
                  );
               } catch (Exception var27) {
                  log.error("Telegram 管理面板操作失败: operatorId={}, targetUserId={}, action={}", operatorId, targetUserId, action, var27);
                  this.renderAdminUserPanel(operatorId, this.embyUserService.getById(Long.valueOf(targetUserId)), message, "❌ 操作失败，请稍后再试。");
               }

               return;
            }

            this.stringRedisTemplate.delete(sessionKey);
            this.editStartPanelMessage(
               message, this.foamPanelTitle("用户管理") + "❌ 该用户不存在或不可管理。", this.keyboard(List.of(this.button("\ud83d\udee1️ 返回管理中心", "start_panel:admin")), 1)
            );
            return;
         }

         this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 这颗按钮不在你当前的权限里，让有权限的管理员来处理吧。");
         return;
      }

      this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 这颗按钮暂时无法响应，请稍后再试。");
   }

   private void beginAdminUserMuteInput(long operatorId, EmbyUser target, Message panelMessage, String sessionToken) {
      this.pendingPanelCommands
         .put(
            operatorId,
            new DataQueryBot.PendingPanelCommand("/panelmute", System.currentTimeMillis() + START_PANEL_INPUT_TTL_MILLIS, panelMessage, "admin", target.getId())
         );
      this.editStartPanelMessage(
         panelMessage,
         this.foamPanelTitle("用户管理")
            + "\ud83d\udd07 当前账号：`"
            + this.escapeMarkdown(target.getEmbyUserName())
            + "`\n\n请回复本面板输入禁言分钟数：`1-525600`\n例如发送 `30`，表示禁言 30 分钟。\n\n⏳ 请在 10 分钟内发送；输入 `/cancel` 也可取消。",
         this.keyboard(List.of(this.button("↩️ 返回用户面板", "admin_user:" + sessionToken + ":refresh")), 1)
      );
   }

   private void kickBanTelegramMember(Message panelMessage, EmbyUser target, long operatorId) {
      Long targetTelegramUserId = this.requireModeratableTelegramMember(panelMessage, target, operatorId);
      this.telegramClient.banChatMember(panelMessage.getChatId(), targetTelegramUserId);
   }

   private void muteTelegramMember(Message panelMessage, EmbyUser target, long operatorId, int minutes) {
      Long targetTelegramUserId = this.requireModeratableTelegramMember(panelMessage, target, operatorId);
      this.telegramClient.restrictChatMember(panelMessage.getChatId(), targetTelegramUserId, minutes);
   }

   private void unmuteTelegramMember(Message panelMessage, EmbyUser target, long operatorId) {
      Long targetTelegramUserId = this.requireModeratableTelegramMember(panelMessage, target, operatorId);
      this.telegramClient.unrestrictChatMember(panelMessage.getChatId(), targetTelegramUserId);
   }

   private Long requireModeratableTelegramMember(Message panelMessage, EmbyUser target, long operatorId) {
      if (panelMessage != null && !panelMessage.isUserMessage()) {
         if (!this.telegramBotAuthorizationService.isConfiguredManagementGroup(panelMessage.getChatId())) {
            throw new BizException("当前群聊不是已配置的管理群");
         } else {
            Long targetTelegramUserId = this.resolveBoundTelegramUserId(target);
            if (targetTelegramUserId == null) {
               throw new BizException("当前 Emby 账号没有有效的 Telegram 绑定");
            } else if (targetTelegramUserId == operatorId) {
               throw new BizException("不能对自己执行这项群管理操作");
            } else if (this.isKkProtectedTelegramTarget(targetTelegramUserId)) {
               throw new BizException("\ud83c\udf01 这位伙伴暂时不开放这项操作哦～");
            } else {
               return targetTelegramUserId;
            }
         }
      } else {
         throw new BizException("群成员管理只能在群聊 `/kk` 面板中执行");
      }
   }

   private long requireModeratableTelegramMember(Message panelMessage, long targetTelegramUserId, long operatorId) {
      if (panelMessage != null && !panelMessage.isUserMessage()) {
         if (!this.telegramBotAuthorizationService.isConfiguredManagementGroup(panelMessage.getChatId())) {
            throw new BizException("当前群聊不是已配置的管理群");
         } else if (targetTelegramUserId <= 0L) {
            throw new BizException("目标 Telegram 身份无效");
         } else if (targetTelegramUserId == operatorId) {
            throw new BizException("不能对自己执行这项群成员操作");
         } else if (this.isKkProtectedTelegramTarget(targetTelegramUserId)) {
            throw new BizException("\ud83c\udf01 这位伙伴暂时不开放这项操作哦～");
         } else {
            return targetTelegramUserId;
         }
      } else {
         throw new BizException("群成员操作只能在群聊 `/kk` 面板中执行");
      }
   }

   private void beginAdminUserPanelInput(long operatorId, EmbyUser target, Message panelMessage, String action) {
      String command = switch (action) {
         case "update" -> "/updateuserinfo";
         case "expiry" -> "/setexpiry";
         case "password" -> "/resetpassword";
         default -> null;
      };
      if (command != null) {
         this.pendingPanelCommands
            .put(
               operatorId,
               new DataQueryBot.PendingPanelCommand(command, System.currentTimeMillis() + START_PANEL_INPUT_TTL_MILLIS, panelMessage, "admin", target.getId())
            );
         this.editStartPanelMessage(
            panelMessage, this.adminUserActionPrompt(command, target, null), this.keyboard(List.of(this.button("↩️ 返回用户面板", "start_input:cancel")), 1)
         );
      }
   }

   private String adminUserActionPrompt(String command, EmbyUser target, String error) {
      String instruction = switch (command) {
         case "/updateuserinfo" -> "请输入：`yyyy-MM-dd HH:mm:ss 求片次数 备注`\n示例：`2026-12-31 23:59:59 10 VIP用户`";
         case "/setexpiry" -> "请输入新的到期时间：`yyyy-MM-dd HH:mm:ss`";
         case "/resetpassword" -> "请输入新密码。";
         case "/panelmute" -> "请回复本面板输入禁言分钟数：`1-525600`";
         default -> "请输入操作参数。";
      };
      String text = this.foamPanelTitle("用户管理")
         + "⌨️ 当前账号：`"
         + this.escapeMarkdown(target.getEmbyUserName())
         + "`\n\n"
         + instruction
         + "\n\n⏳ 请在 10 分钟内发送；输入 `/cancel` 返回用户面板。";
      if (StringUtils.hasText(error)) {
         text = text + "\n\n" + error;
      }

      return text;
   }

   private String adminUserActionSuccessNotice(
      String action, org.telegram.telegrambots.meta.api.objects.User operator, boolean publicNotice, String targetMention
   ) {
      if (publicNotice) {
         String actor = this.telegramUserMention(operator);
         String target = StringUtils.hasText(targetMention) ? targetMention : "目标成员";

         return switch (action) {
            case "enable" -> "\ud83c\udf24️ " + actor + " 轻触雾面，薄雾重新映亮；账号已恢复启用。";
            case "disable" -> "\ud83c\udf19 " + actor + " 收起一道微光，薄雾暂沉静水；账号已暂停使用。";
            case "whitelist" -> "✨ " + actor + " 推开潮线，薄雾已进入白名单，全部线路随光展开。";
            case "unwhite7" -> "\ud83c\udf0a " + actor + " 让潮汐轻落，账号已移出白名单，普通账号有效期重置为 7 天。";
            case "unwhite30" -> "\ud83c\udf0a " + actor + " 让潮汐轻落，账号已移出白名单，普通账号有效期重置为 30 天。";
            case "unwhite90" -> "\ud83c\udf0a " + actor + " 让潮汐轻落，账号已移出白名单，普通账号有效期重置为 90 天。";
            case "kickban" -> "\ud83c\udf19 " + actor + " 轻轻合上群门，" + target + " 已被移出并封禁。";
            case "unmute" -> "\ud83c\udf24️ " + actor + " 轻轻解开静音结，目标成员已经可以重新说话。";
            default -> "\ud83c\udf01 " + actor + " 的操作已完成，水面留下了一圈清晰的涟漪。";
         };
      } else {
         return switch (action) {
            case "enable" -> "✅ 用户已启用。";
            case "disable" -> "✅ 用户已禁用。";
            case "whitelist" -> "✅ 用户已加入白名单。";
            case "unwhite7" -> "✅ 已移出白名单，普通账号有效期重置为 7 天。";
            case "unwhite30" -> "✅ 已移出白名单，普通账号有效期重置为 30 天。";
            case "unwhite90" -> "✅ 已移出白名单，普通账号有效期重置为 90 天。";
            case "kickban" -> "✅ 目标成员已从当前群聊移出并封禁。";
            case "unmute" -> "✅ 目标成员已解除禁言。";
            default -> "✅ 操作成功。";
         };
      }
   }

   private String buildWhitelistCelebrationMessage(long chatId, EmbyUser target, org.telegram.telegrambots.meta.api.objects.User operator) {
      return this.buildWhitelistCelebrationMessage(this.telegramBoundUserMention(chatId, target), this.telegramUserMention(operator));
   }

   private String buildWhitelistCelebrationMessage(String recipientMention, String operatorMention) {
      String opening = WHITELIST_CELEBRATION_COPY.get(ThreadLocalRandom.current().nextInt(WHITELIST_CELEBRATION_COPY.size()));
      return opening + "\n\n\ud83c\udf89 恭喜 " + recipientMention + "，获得 " + operatorMention + " 送出的白名单。\n\n\ud83c\udf01 愿薄雾托住所有期待，往后的观影时光都有星光与好故事相伴。";
   }

   private String telegramBoundUserMention(long chatId, EmbyUser target) {
      if (target != null && target.getId() != null) {
         UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(target.getId());
         Long telegramUserId = binding == null ? null : this.parseTelegramUserId(binding.getProviderUserId());
         if (telegramUserId == null) {
            return "`" + this.escapeMarkdown(target.getEmbyUserName()) + "`";
         } else {
            try {
               ChatMember member = this.telegramClient.execute(GetChatMember.builder().chatId(chatId).userId(telegramUserId).build());
               org.telegram.telegrambots.meta.api.objects.User telegramUser = this.getChatMemberUser(member);
               if (telegramUser != null) {
                  return this.telegramUserMention(telegramUser);
               }
            } catch (TelegramApiException var8) {
               log.debug("读取白名单获赠者群昵称失败，使用绑定资料: chatId={}, telegramUserId={}, message={}", chatId, telegramUserId, var8.getMessage());
            }

            String fallbackName = binding == null ? null : binding.getProviderUsername();
            if (StringUtils.hasText(fallbackName) && !fallbackName.startsWith("@")) {
               fallbackName = "@" + fallbackName;
            }

            if (!StringUtils.hasText(fallbackName)) {
               fallbackName = target.getEmbyUserName();
            }

            return this.telegramUserMention(telegramUserId, fallbackName);
         }
      } else {
         return "这位幸运用户";
      }
   }

   private boolean handlePendingAdminUserAction(Message message, DataQueryBot.PendingPanelCommand pending, String input) {
      long operatorId = message.getFrom().getId();
      long targetUserId = pending.getTargetUserId();
      TelegramBotPermission permission = this.permissionForCommand(pending.getCommand());
      EmbyUser target = this.embyUserService.getById(Long.valueOf(targetUserId));
      if (target != null && this.isKkProtectedEmbyTarget(target)) {
         this.pendingPanelCommands.remove(operatorId, pending);
         this.editStartPanelMessage(pending.getPanelMessage(), "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
         return true;
      } else if (permission != null
         && this.telegramBotAuthorizationService.hasPermission(operatorId, permission)
         && target != null
         && this.canBotOperatorViewTarget(operatorId, target)) {
         if (!this.tryAcquireRateLimit("admin_mutation:" + operatorId, 30L, 60L)) {
            this.keepPendingAdminUserInput(operatorId, pending, target, "⏳ 操作过于频繁，请稍后再试。");
            return true;
         } else {
            try {
               String var11 = pending.getCommand();
               String successNotice;
               switch (var11) {
                  case "/updateuserinfo":
                     String[] args = input.split("\\s+", 4);
                     if (args.length < 4) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 参数不完整，请按示例重新输入。");
                        return true;
                     }

                     int requestCount;
                     LocalDateTime dateTimex;
                     try {
                        dateTimex = LocalDateTime.parse(args[0] + " " + args[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        requestCount = Integer.parseInt(args[2]);
                     } catch (DateTimeParseException var19) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 日期格式错误，请使用 `yyyy-MM-dd HH:mm:ss`。");
                        return true;
                     } catch (NumberFormatException var20) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 求片次数必须是整数。");
                        return true;
                     }

                     if (requestCount < 0) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 求片次数不能小于 0。");
                        return true;
                     }

                     EmbyUserUpdateData updateData = new EmbyUserUpdateData();
                     updateData.setId(targetUserId);
                     updateData.setExpirationDate(Date.from(dateTimex.atZone(ZoneId.systemDefault()).toInstant()));
                     updateData.setRequestPackagesCount(requestCount);
                     updateData.setRemarks(args[3]);
                     this.embyUserService.updateUserDataByBot(updateData, this.isBotOwner(operatorId));
                     successNotice = "✅ 有效期、求片额度和备注已更新。";
                     break;
                  case "/setexpiry":
                     LocalDateTime dateTime;
                     try {
                        dateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                     } catch (DateTimeParseException var18) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 日期格式错误，请使用 `yyyy-MM-dd HH:mm:ss`。");
                        return true;
                     }

                     if (!dateTime.isAfter(LocalDateTime.now())) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 到期时间必须晚于当前时间。");
                        return true;
                     }

                     EmbyUserUpdateData expiryUpdateData = new EmbyUserUpdateData();
                     expiryUpdateData.setId(targetUserId);
                     expiryUpdateData.setExpirationDate(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
                     this.embyUserService.updateUserDataByBot(expiryUpdateData, this.isBotOwner(operatorId));
                     this.embyUserService.enableUserByBot(targetUserId, this.isBotOwner(operatorId));
                     successNotice = "✅ 有效期已更新，用户已启用。";
                     break;
                  case "/resetpassword":
                     if (!StringUtils.hasText(input)) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 新密码不能为空。");
                        return true;
                     }

                     this.embyUserService.resetPasswordByBot(targetUserId, input, this.isBotOwner(operatorId));
                     successNotice = "✅ 用户密码已重置。";
                     break;
                  case "/panelmute":
                     int minutes;
                     try {
                        minutes = Integer.parseInt(input);
                     } catch (NumberFormatException var17) {
                        this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 禁言时间请输入整数分钟，例如 `30`。");
                        return true;
                     }

                     if (minutes >= 1 && minutes <= 525600) {
                        Message panelMessage = pending.getPanelMessage();
                        if (panelMessage != null
                           && Objects.equals(panelMessage.getChatId(), message.getChatId())
                           && this.telegramBotAuthorizationService.isConfiguredManagementGroup(panelMessage.getChatId())) {
                           this.muteTelegramMember(panelMessage, target, operatorId, minutes);
                           successNotice = "\ud83c\udf19 禁言已生效，水面会安静 " + minutes + " 分钟。";
                           break;
                        }

                        this.pendingPanelCommands.remove(operatorId, pending);
                        return true;
                     }

                     this.keepPendingAdminUserInput(operatorId, pending, target, "❌ 禁言时间需要在 1 到 525600 分钟之间。");
                     return true;
                  default:
                     this.pendingPanelCommands.remove(operatorId, pending);
                     return true;
               }

               this.pendingPanelCommands.remove(operatorId, pending);
               log.info("Telegram 用户管理面板输入操作完成: operatorId={}, targetUserId={}, command={}", operatorId, targetUserId, pending.getCommand());
               this.renderAdminUserPanel(operatorId, this.embyUserService.getById(Long.valueOf(targetUserId)), pending.getPanelMessage(), successNotice);
            } catch (BizException var21) {
               this.pendingPanelCommands.remove(operatorId, pending);
               this.renderAdminUserPanel(
                  operatorId,
                  this.embyUserService.getById(Long.valueOf(targetUserId)),
                  pending.getPanelMessage(),
                  "❌ 操作失败：" + this.escapeMarkdown(var21.getMessage())
               );
            } catch (Exception var22) {
               this.pendingPanelCommands.remove(operatorId, pending);
               log.error("Telegram 用户管理面板输入操作失败: operatorId={}, targetUserId={}, command={}", operatorId, targetUserId, pending.getCommand(), var22);
               this.renderAdminUserPanel(operatorId, this.embyUserService.getById(Long.valueOf(targetUserId)), pending.getPanelMessage(), "❌ 操作失败，请稍后再试。");
            }

            return true;
         }
      } else {
         this.pendingPanelCommands.remove(operatorId, pending);
         this.restorePendingPanel(operatorId, pending, pending.getPanelMessage(), "❌ 用户不存在或当前账号已无此操作权限。");
         return true;
      }
   }

   private void keepPendingAdminUserInput(long operatorId, DataQueryBot.PendingPanelCommand pending, EmbyUser target, String error) {
      DataQueryBot.PendingPanelCommand refreshed = pending.refresh();
      this.pendingPanelCommands.put(operatorId, refreshed);
      boolean publicMuteInput = "/panelmute".equals(refreshed.getCommand())
         && refreshed.getPanelMessage() != null
         && !refreshed.getPanelMessage().isUserMessage();
      this.editStartPanelMessage(
         refreshed.getPanelMessage(),
         this.adminUserActionPrompt(refreshed.getCommand(), target, error),
         publicMuteInput ? null : this.keyboard(List.of(this.button("↩️ 返回用户面板", "start_input:cancel")), 1)
      );
   }

   private void handleInlineQuery(InlineQuery inlineQuery) {
      if (inlineQuery != null && inlineQuery.getFrom() != null) {
         long telegramUserId = inlineQuery.getFrom().getId();
         String query = inlineQuery.getQuery() == null ? "" : inlineQuery.getQuery().trim();
         if (!StringUtils.hasText(query)) {
            this.answerInlineQuery(inlineQuery.getId(), List.of(), 1, true);
         } else {
            String membershipRejectReason = this.resolvePrivateChatMembershipRejectReason(telegramUserId);
            if (StringUtils.hasText(membershipRejectReason)) {
               this.answerInlineQuery(
                  inlineQuery.getId(),
                  List.of(this.buildInlineInfoArticle("member_required", "请先加入积分群/频道", membershipRejectReason, membershipRejectReason)),
                  1,
                  true
               );
            } else if (!this.tryAcquireRateLimit("inline_search:" + telegramUserId, 20L, 60L)) {
               this.answerInlineQuery(inlineQuery.getId(), List.of(this.buildInlineInfoArticle("rate_limit", "搜索太频繁了", "请稍后再试", "搜索太频繁了，请稍后再试。")), 1, true);
            } else if (this.telegramAuthService.findBoundUser(telegramUserId) == null) {
               this.answerInlineQuery(
                  inlineQuery.getId(),
                  List.of(this.buildInlineInfoArticle("bind_required", "请先绑定 Emby 账号", "绑定后才能通过 Telegram 搜索并提交求片", "请先私聊机器人使用 /bind 用户名 密码 绑定 Emby 账号。")),
                  1,
                  true
               );
            } else {
               try {
                  TmdbResponse tmdbResponse = this.tmdbService.searchDataTelegram(query, 1);
                  List<TmdbResponse.Result> validResults = tmdbResponse != null && tmdbResponse.getResults() != null
                     ? tmdbResponse.getResults().stream().filter(this::isValidTmdbRequestResult).limit(20L).collect(Collectors.toList())
                     : List.of();
                  if (validResults.isEmpty()) {
                     this.answerInlineQuery(
                        inlineQuery.getId(), List.of(this.buildInlineInfoArticle("empty", "没有找到结果", "换个关键词再试试", "没有找到 “" + query + "” 的 TMDB 结果。")), 1, true
                     );
                     return;
                  }

                  boolean allowSubmit = this.isPrivateInlineChat(inlineQuery.getChatType());
                  List<InlineQueryResult> inlineResults = new ArrayList<>();

                  for (TmdbResponse.Result result : validResults) {
                     String token = this.buildInlineResultToken(result);
                     this.saveInlineResult(token, result);
                     inlineResults.add(this.buildInlineArticleResult(token, result, allowSubmit));
                  }

                  this.answerInlineQuery(inlineQuery.getId(), inlineResults, 0, true);
               } catch (Exception var13) {
                  log.error("Telegram inline TMDB 搜索失败: telegramUserId={}, query={}", telegramUserId, query, var13);
                  this.answerInlineQuery(inlineQuery.getId(), List.of(this.buildInlineInfoArticle("error", "搜索失败", "请稍后再试", "搜索失败，请稍后再试。")), 1, true);
               }
            }
         }
      }
   }

   private boolean isValidTmdbRequestResult(TmdbResponse.Result result) {
      return result != null
         && StringUtils.hasText(result.getPosterPath())
         && result.getId() != 0
         && StringUtils.hasText(result.getMediaType())
         && ("movie".equals(result.getMediaType()) || "tv".equals(result.getMediaType()))
         && (StringUtils.hasText(result.getTitle()) || StringUtils.hasText(result.getName()));
   }

   private boolean isPrivateInlineChat(String chatType) {
      return !StringUtils.hasText(chatType) || "private".equalsIgnoreCase(chatType) || "sender".equalsIgnoreCase(chatType);
   }

   private InlineQueryResult buildInlineArticleResult(String token, TmdbResponse.Result result, boolean allowSubmit) {
      String posterUrl = this.buildTmdbImageUrl(result.getPosterPath());
      InlineQueryResultArticleBuilder<?, ?> builder = InlineQueryResultArticle.builder()
         .id(token)
         .thumbnailUrl(posterUrl)
         .thumbnailWidth(120)
         .thumbnailHeight(180)
         .title(this.buildInlineResultTitle(result))
         .description(this.buildInlineResultDescription(result))
         .inputMessageContent(
            InputTextMessageContent.builder()
               .messageText(this.buildInlineSelectedMessage(result, posterUrl))
               .parseMode("HTML")
               .linkPreviewOptions(LinkPreviewOptions.builder().urlField(posterUrl).preferLargeMedia(true).showAboveText(true).build())
               .build()
         )
         .replyMarkup(this.buildInlineResultKeyboard(token, result, allowSubmit));
      return builder.build();
   }

   private InlineQueryResult buildInlineInfoArticle(String id, String title, String description, String messageText) {
      return InlineQueryResultArticle.builder()
         .id(id)
         .title(title)
         .description(description)
         .inputMessageContent(InputTextMessageContent.builder().messageText(messageText).build())
         .build();
   }

   private InlineKeyboardMarkup buildInlineResultKeyboard(String token, TmdbResponse.Result result, boolean allowSubmit) {
      InlineKeyboardButton tmdbButton = InlineKeyboardButton.builder().text("\ud83c\udf5f TMDB").url(this.buildTmdbPageUrl(result)).build();
      if (!allowSubmit) {
         return InlineKeyboardMarkup.builder().keyboard(List.of(new InlineKeyboardRow(tmdbButton))).build();
      } else {
         InlineKeyboardButton submitButton = InlineKeyboardButton.builder().text("\ud83c\udfac 提交求片").callbackData("submit_inline_request:" + token).build();
         return InlineKeyboardMarkup.builder().keyboard(List.of(new InlineKeyboardRow(tmdbButton, submitButton))).build();
      }
   }

   private String buildInlineResultToken(TmdbResponse.Result result) {
      return result.getMediaType() + ":" + result.getId();
   }

   private String buildTmdbPageUrl(TmdbResponse.Result result) {
      return "https://www.themoviedb.org/" + ("tv".equals(result.getMediaType()) ? "tv" : "movie") + "/" + result.getId();
   }

   private String buildInlineResultTitle(TmdbResponse.Result result) {
      String title = this.resolveTmdbTitle(result);
      String originalTitle = this.resolveTmdbOriginalTitle(result);
      String releaseDate = this.resolveTmdbReleaseDate(result);
      String year = StringUtils.hasText(releaseDate) && releaseDate.length() >= 4 ? " (" + releaseDate.substring(0, 4) + ")" : "";
      return StringUtils.hasText(originalTitle) && !originalTitle.equals(title)
         ? this.formatMediaTypeToEmoji(result.getMediaType()) + " " + title + " - " + originalTitle + year
         : this.formatMediaTypeToEmoji(result.getMediaType()) + " " + title + year;
   }

   private String buildInlineResultDescription(TmdbResponse.Result result) {
      String overview = StringUtils.hasText(result.getOverview()) ? result.getOverview() : "暂无简介";
      return this.abbreviate("简介： -" + overview, 120);
   }

   private String buildInlineSelectedMessage(TmdbResponse.Result result, String posterUrl) {
      StringBuilder builder = new StringBuilder();
      builder.append(this.formatMediaTypeToEmoji(result.getMediaType())).append(" 《").append(this.resolveTmdbTitle(result));
      String originalTitle = this.resolveTmdbOriginalTitle(result);
      if (StringUtils.hasText(originalTitle) && !originalTitle.equals(this.resolveTmdbTitle(result))) {
         builder.append(" - ").append(originalTitle);
      }

      builder.append("》\n\n");
      this.appendInlineMeta(builder, "\ud83d\uddd3", "年份", this.resolveTmdbYear(result));
      this.appendInlineMeta(builder, "\ud83e\udded", "地区", this.buildRegionText(result));
      this.appendInlineMeta(builder, "\ud83c\udf9a", "类型", this.formatMediaTypeText(result.getMediaType()));
      this.appendInlineMeta(builder, "\ud83c\udff7", "标签", this.buildGenreText(result));
      this.appendInlineMeta(builder, "\ud83d\udcab", "评分", this.buildScoreText(result));
      builder.append("\n");
      builder.append(this.buildOverviewText(result));
      builder.append("\n\n");
      builder.append("✨ 看起来不错的话，点下面按钮把它丢进求片队列。");
      String messageText = this.abbreviate(builder.toString(), 980);
      return !StringUtils.hasText(posterUrl)
         ? this.escapeTelegramHtml(messageText)
         : "<a href=\"" + this.escapeTelegramHtmlAttribute(posterUrl) + "\">&#8205;</a>" + this.escapeTelegramHtml(messageText);
   }

   private String escapeTelegramHtml(String value) {
      return value == null ? "" : value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
   }

   private String escapeTelegramHtmlAttribute(String value) {
      return this.escapeTelegramHtml(value).replace("\"", "&quot;");
   }

   private void appendInlineMeta(StringBuilder builder, String icon, String label, String value) {
      if (StringUtils.hasText(value)) {
         builder.append(icon).append(" ").append(label).append(" | ").append(value).append("\n");
      }
   }

   private String resolveTmdbYear(TmdbResponse.Result result) {
      String date = this.resolveTmdbReleaseDate(result);
      return StringUtils.hasText(date) && date.length() >= 4 ? date.substring(0, 4) : "未知";
   }

   private String buildRegionText(TmdbResponse.Result result) {
      if (result.getOriginCountry() != null && !result.getOriginCountry().isEmpty()) {
         return result.getOriginCountry().stream().filter(StringUtils::hasText).map(this::displayCountryName).collect(Collectors.joining(" / "));
      } else {
         String language = this.displayLanguageName(result.getOriginalLanguage());
         return StringUtils.hasText(language) ? language + "语区" : "未知";
      }
   }

   private String buildGenreText(TmdbResponse.Result result) {
      if (result.getGenreIds() != null && !result.getGenreIds().isEmpty()) {
         List<String> names = result.getGenreIds()
            .stream()
            .map(id -> this.resolveGenreName(id, result.getMediaType()))
            .filter(StringUtils::hasText)
            .distinct()
            .limit(4L)
            .collect(Collectors.toList());
         return names.isEmpty() ? "待补全" : String.join(" / ", names);
      } else {
         return "待补全";
      }
   }

   private String buildScoreText(TmdbResponse.Result result) {
      if (result.getVoteAverage() != null && !(result.getVoteAverage() <= 0.0)) {
         String score = String.format("%.1f", result.getVoteAverage());
         return result.getVoteCount() != null && result.getVoteCount() > 0 ? score + " / 10（" + result.getVoteCount() + " 人评）" : score + " / 10";
      } else {
         return "暂无评分";
      }
   }

   private String buildOverviewText(TmdbResponse.Result result) {
      String overview = StringUtils.hasText(result.getOverview()) ? result.getOverview() : "暂无简介。";
      return this.abbreviate("\ud83d\udcd6 " + overview, 1200);
   }

   private String displayCountryName(String countryCode) {
      if (!StringUtils.hasText(countryCode)) {
         return "";
      } else {
         try {
            Locale locale = new Builder().setRegion(countryCode.trim().toUpperCase(Locale.ROOT)).build();
            String displayName = locale.getDisplayCountry(Locale.SIMPLIFIED_CHINESE);
            return StringUtils.hasText(displayName) ? displayName : countryCode;
         } catch (Exception var4) {
            return countryCode;
         }
      }
   }

   private String displayLanguageName(String languageCode) {
      if (!StringUtils.hasText(languageCode)) {
         return "";
      } else {
         try {
            Locale locale = Locale.forLanguageTag(languageCode.trim().toLowerCase(Locale.ROOT));
            String displayName = locale.getDisplayLanguage(Locale.SIMPLIFIED_CHINESE);
            return StringUtils.hasText(displayName) ? displayName : languageCode;
         } catch (Exception var4) {
            return languageCode;
         }
      }
   }

   private String resolveGenreName(Integer genreId, String mediaType) {
      if (genreId == null) {
         return null;
      } else {
         Map<Integer, String> genres = "tv".equals(mediaType) ? this.tvGenreNames() : this.movieGenreNames();
         return genres.getOrDefault(genreId, null);
      }
   }

   private Map<Integer, String> movieGenreNames() {
      Map<Integer, String> genres = new LinkedHashMap<>();
      genres.put(28, "动作");
      genres.put(12, "冒险");
      genres.put(16, "动画");
      genres.put(35, "喜剧");
      genres.put(80, "犯罪");
      genres.put(99, "纪录片");
      genres.put(18, "剧情");
      genres.put(10751, "家庭");
      genres.put(14, "奇幻");
      genres.put(36, "历史");
      genres.put(27, "恐怖");
      genres.put(10402, "音乐");
      genres.put(9648, "悬疑");
      genres.put(10749, "爱情");
      genres.put(878, "科幻");
      genres.put(10770, "电视电影");
      genres.put(53, "惊悚");
      genres.put(10752, "战争");
      genres.put(37, "西部");
      return genres;
   }

   private Map<Integer, String> tvGenreNames() {
      Map<Integer, String> genres = new LinkedHashMap<>();
      genres.put(10759, "动作冒险");
      genres.put(16, "动画");
      genres.put(35, "喜剧");
      genres.put(80, "犯罪");
      genres.put(99, "纪录片");
      genres.put(18, "剧情");
      genres.put(10751, "家庭");
      genres.put(10762, "儿童");
      genres.put(9648, "悬疑");
      genres.put(10763, "新闻");
      genres.put(10764, "真人秀");
      genres.put(10765, "科幻奇幻");
      genres.put(10766, "肥皂剧");
      genres.put(10767, "脱口秀");
      genres.put(10768, "战争政治");
      genres.put(37, "西部");
      return genres;
   }

   private void answerInlineQuery(String inlineQueryId, List<InlineQueryResult> results, int cacheTime, boolean personal) {
      try {
         AnswerInlineQuery answerInlineQuery = AnswerInlineQuery.builder()
            .inlineQueryId(inlineQueryId)
            .results(results)
            .cacheTime(cacheTime)
            .isPersonal(personal)
            .nextOffset("")
            .build();
         this.telegramClient.execute(answerInlineQuery);
      } catch (TelegramApiException var6) {
         log.error("响应 Telegram inline query 失败: {}", var6.getMessage(), var6);
      }
   }

   private void handleGenerateCardsCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         String[] args = argument.split("\\s+");
         if (args.length < 2) {
            this.sendMessage(chatId, "用法：`/generatecards <数量> <天数>`\n示例：`/generatecards 10 30`");
         } else {
            int count;
            int day;
            try {
               count = Integer.parseInt(args[0]);
               day = Integer.parseInt(args[1]);
            } catch (NumberFormatException var15) {
               this.sendMessage(chatId, "数量和天数都必须是数字，例如：`/generatecards 10 30`");
               return;
            }

            if (count > 0 && day > 0) {
               List<EmbyInfo> servers = this.loadAvailableServers();
               if (servers.isEmpty()) {
                  this.sendMessage(chatId, "未找到可用服务器，请先在后台配置。");
               } else {
                  this.pendingCardBatches.put(userId, new DataQueryBot.PendingCardBatch(chatId, count, day));
                  InlineKeyboardMarkup keyboard = this.buildServerSelectionKeyboard(servers, "card_server:");
                  SendMessage selectServerMessage = SendMessage.builder()
                     .chatId(chatId)
                     .text(this.foamPanelTitle("卡密管理") + "请选择要生成卡密的服务器：")
                     .parseMode("Markdown")
                     .replyMarkup(keyboard)
                     .build();

                  try {
                     Message sentMessage = this.telegramClient.execute(selectServerMessage);
                     this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
                  } catch (TelegramApiException var14) {
                     log.error("发送服务器选择列表失败", (Throwable)var14);
                     this.sendMessage(chatId, "发送服务器列表失败，请稍后再试。");
                  }
               }
            } else {
               this.sendMessage(chatId, "数量和天数必须大于 0。");
            }
         }
      }
   }

   private void handleExtendUsersCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         String[] args = argument.split("\\s+");
         if (args.length >= 1 && StringUtils.hasText(args[0])) {
            Integer expiredRange = null;

            Integer extensionDay;
            try {
               extensionDay = Integer.parseInt(args[0]);
               if (args.length > 1) {
                  expiredRange = Integer.parseInt(args[1]);
               }
            } catch (NumberFormatException var15) {
               this.sendMessage(chatId, "参数必须为数字，例如：`/extendusers 15 30`");
               return;
            }

            if (extensionDay != null && extensionDay > 0) {
               List<EmbyInfo> servers = this.embyInfoService
                  .lambdaQuery()
                  .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
                  .eq(EmbyInfo::getStatus, Integer.valueOf(0))
                  .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                  .list();
               if (CollectionUtils.isEmpty(servers)) {
                  this.sendMessage(chatId, "未找到可用服务器，请先在后台配置。");
               } else {
                  this.pendingExtendBatches.put(userId, new DataQueryBot.PendingExtendBatch(chatId, extensionDay, expiredRange));
                  InlineKeyboardMarkup keyboard = this.buildServerSelectionKeyboard(servers, "extend_server:");
                  SendMessage selectServerMessage = SendMessage.builder()
                     .chatId(chatId)
                     .text(this.foamPanelTitle("用户延期") + "请选择要延期用户的服务器：")
                     .parseMode("Markdown")
                     .replyMarkup(keyboard)
                     .build();

                  try {
                     Message sentMessage = this.telegramClient.execute(selectServerMessage);
                     this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
                  } catch (TelegramApiException var14) {
                     log.error("发送服务器选择列表失败", (Throwable)var14);
                     this.sendMessage(chatId, "发送服务器列表失败，请稍后再试。");
                  }
               }
            } else {
               this.sendMessage(chatId, "延期天数必须大于 0。");
            }
         } else {
            this.sendMessage(chatId, "用法：`/extendusers <延期天数> [过期天数范围]`\n示例：`/extendusers 15 30`\n选择服务器后延期该服务器的用户");
         }
      }
   }

   private void handleEditUserCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         String keyword = argument.trim();
         if (!StringUtils.hasText(keyword)) {
            this.sendMessage(chatId, "请输入要搜索的用户名关键词，例如：`/edituser test`");
         } else {
            List<EmbyUser> users = this.embyUserService
               .lambdaQuery()
               .like(EmbyUser::getEmbyUserName, keyword)
               .orderByAsc(EmbyUser::getEmbyUserName)
               .last("limit 10")
               .list();
            users = users.stream().filter(candidate -> this.canBotOperatorViewTarget(userId, candidate)).toList();
            if (users != null && !users.isEmpty()) {
               List<InlineKeyboardRow> rows = new ArrayList<>();

               for (EmbyUser user : users) {
                  String statusEmoji = user.getUserStatus() != null && user.getUserStatus() == 1 ? "\ud83d\udd34 " : "\ud83d\udfe2 ";
                  InlineKeyboardButton button = InlineKeyboardButton.builder()
                     .text(statusEmoji + user.getEmbyUserName() + this.formatExpirationDate(user.getExpirationDate()))
                     .callbackData("edit_user:" + user.getId())
                     .build();
                  rows.add(new InlineKeyboardRow(button));
               }

               InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(rows).build();
               SendMessage sendMessage = SendMessage.builder()
                  .chatId(chatId)
                  .text(this.foamPanelTitle("用户管理") + "\ud83d\udc65 请选择要修改的用户（最多显示 10 个）：")
                  .parseMode("Markdown")
                  .replyMarkup(keyboard)
                  .build();
               this.pendingUserEdits.remove(userId);

               try {
                  Message sentMessage = this.telegramClient.execute(sendMessage);
                  this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
               } catch (TelegramApiException var14) {
                  log.error("发送用户列表失败", (Throwable)var14);
                  this.sendMessage(chatId, "发送用户列表失败，请稍后再试。");
               }
            } else {
               this.sendMessage(chatId, "未找到匹配的用户。");
            }
         }
      }
   }

   private void handleUpdateUserInfoCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         DataQueryBot.PendingUserEdit pending = this.pendingUserEdits.get(userId);
         if (pending == null) {
            this.sendMessage(chatId, "请先使用 /edituser 搜索并选择用户。");
         } else {
            String[] args = argument.split("\\s+", 4);
            if (args.length < 4) {
               this.sendMessage(
                  chatId,
                  "\ud83d\udcdd *用法：*\n`/updateuserinfo <到期时间> <求片次数> <备注>`\n\n⏰ *时间格式：* `yyyy-MM-dd HH:mm:ss`\n\ud83d\udccc *示例：*\n`/updateuserinfo 2025-12-31 23:59:59 10 VIP用户`\n\n\ud83d\udca1 备注可包含空格"
               );
            } else {
               String dateTimeStr = args[0] + " " + args[1];
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

               LocalDateTime dateTime;
               Integer requestCount;
               try {
                  dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                  requestCount = Integer.parseInt(args[2]);
               } catch (DateTimeParseException var18) {
                  this.sendMessage(chatId, "❌ 日期时间格式错误！\n正确格式：`yyyy-MM-dd HH:mm:ss`\n示例：`2025-12-31 23:59:59`");
                  return;
               } catch (NumberFormatException var19) {
                  this.sendMessage(chatId, "❌ 求片次数必须是数字！");
                  return;
               }

               String remarks = args[3];
               EmbyUserUpdateData updateData = new EmbyUserUpdateData();
               updateData.setId(pending.getUserId());
               updateData.setExpirationDate(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
               updateData.setRequestPackagesCount(requestCount);
               updateData.setRemarks(remarks);

               try {
                  this.embyUserService.updateUserDataByBot(updateData, this.isBotOwner(userId));
                  this.sendMessage(chatId, "✅ 用户信息已更新。");
               } catch (BizException var16) {
                  this.sendMessage(chatId, "更新失败：" + var16.getMessage());
               } catch (Exception var17) {
                  log.error("更新用户信息失败", (Throwable)var17);
                  this.sendMessage(chatId, "更新失败，请稍后再试。");
               }
            }
         }
      }
   }

   private void handleMyAccountCommand(Message message) {
      long chatId = message.getChatId();
      if (!message.isUserMessage()) {
         this.sendMessage(chatId, "\ud83d\udd12 请私聊机器人后使用 `/myaccount` 查询你的关联账号。");
      } else {
         long telegramUserId = message.getFrom().getId();
         String telegramId = String.valueOf(telegramUserId);
         String telegramUsername = message.getFrom().getUserName();
         EmbyUser user = this.telegramBindingManager.findBoundUser(telegramUserId, false);
         StringBuilder sb = new StringBuilder();
         sb.append(this.foamPanelTitle("账号中心"));
         sb.append("\ud83c\udd94 *Telegram ID：* `").append(telegramId).append("`\n");
         if (StringUtils.hasText(telegramUsername)) {
            String normalizedUsername = telegramUsername.startsWith("@") ? telegramUsername : "@" + telegramUsername;
            sb.append("\ud83d\udc64 *Telegram 用户名：* `").append(normalizedUsername).append("`\n");
         }

         if (user == null) {
            sb.append("\n\ud83e\uddd0 未找到与你当前 Telegram 账号关联的 Emby 账号。\n\n").append("\ud83d\udd17 如果你已经在网页端绑定过 Telegram，请稍后再试；如果还未绑定，请先在 Mist 网页端完成绑定。");
            this.sendMessage(chatId, sb.toString());
         } else {
            sb.append("\n✅ *当前绑定账号*\n\n");
            sb.append("\ud83d\udc64 `").append(user.getEmbyUserName()).append("`\n");
            sb.append("\ud83d\udcca 状态：").append(user.getUserStatus() != null && user.getUserStatus() == 1 ? "禁用" : "启用").append("\n");
            sb.append("⏰ 到期：").append(this.formatExpirationForHostLine(user.getExpirationDate(), user.getHostLineType())).append("\n");
            this.appendWhitelistUserLine(sb, user.getHostLineType(), true);
            if (user.getEmbyInfoId() != null) {
               EmbyInfo server = this.embyInfoService.getById(user.getEmbyInfoId());
               sb.append("\ud83d\udda5️ 服务器：").append(this.buildServerLabel(server)).append("\n");
            }

            this.sendMessage(chatId, sb.toString());
         }
      }
   }

   private void handleMyLinesCommand(Message message) {
      long chatId = message.getChatId();
      if (!message.isUserMessage()) {
         this.sendMessage(chatId, "\ud83d\udd12 请私聊机器人后使用 `/mylines` 查看你的线路。");
      } else {
         long telegramUserId = message.getFrom().getId();
         if (!this.tryAcquireRateLimit("mylines:" + telegramUserId, 10L, 60L)) {
            this.sendMessage(chatId, "⏳ 查询太频繁了，请稍后再试。");
         } else {
            EmbyUser boundUser = this.telegramAuthService.findBoundUser(telegramUserId);
            if (boundUser == null) {
               this.sendMessage(chatId, "\ud83d\udd17 请先绑定 Emby 账号后再查看线路：\n1. \ud83c\udf10 网页个人资料中点击绑定 Telegram\n2. \ud83e\udd16 或私聊发送 `/bind 用户名 密码`");
            } else {
               List<HostLineResponse> lines;
               try {
                  lines = this.hostLineService.listUserAvailableLines(boundUser);
               } catch (BizException var13) {
                  this.sendMessage(chatId, "❌ " + var13.getMessage());
                  return;
               } catch (Exception var14) {
                  log.error("Telegram 查询绑定用户线路失败: telegramUserId={}, embyUserId={}", telegramUserId, boundUser.getId(), var14);
                  this.sendMessage(chatId, "❌ 查询线路失败，请稍后再试。");
                  return;
               }

               StringBuilder sb = new StringBuilder();
               sb.append(this.foamPanelTitle("线路中心"));
               sb.append("\ud83d\udc64 账号：`").append(this.escapeMarkdown(boundUser.getEmbyUserName())).append("`\n");
               if (boundUser.getEmbyInfoId() != null) {
                  EmbyInfo server = this.embyInfoService.getById(boundUser.getEmbyInfoId());
                  sb.append("\ud83d\udda5️ 服务器：").append(this.escapeMarkdown(this.buildServerLabel(server))).append("\n");
               }

               if (CollectionUtils.isEmpty(lines)) {
                  sb.append("\n\ud83e\uddd0 当前账号暂无可展示线路，请联系管理员。");
                  this.sendMessage(chatId, sb.toString());
               } else {
                  sb.append("\ud83d\udd10 权限：")
                     .append(
                        boundUser.getIsAdmin() != null && boundUser.getIsAdmin() == 1
                           ? "管理员线路"
                           : HostLineTypeEnum.resolveUserRoleLabel(boundUser.getHostLineType())
                     )
                     .append("\n\n");
                  int index = 1;

                  for (HostLineResponse line : lines) {
                     sb.append(index++).append(". *").append(this.escapeMarkdown(line.getLineName())).append("*");
                     if (StringUtils.hasText(line.getLineTypeName())) {
                        sb.append("（").append(this.escapeMarkdown(line.getLineTypeName())).append("）");
                     }

                     sb.append("\n");
                     String address = line.getLineAddress();
                     if (StringUtils.hasText(address)) {
                        sb.append("   `").append(this.escapeMarkdown(address)).append("`\n");
                     }

                     if (StringUtils.hasText(line.getRemark())) {
                        sb.append("   ").append(this.escapeMarkdown(line.getRemark())).append("\n");
                     }

                     if (index <= lines.size()) {
                        sb.append("\n");
                     }
                  }

                  this.sendMessage(chatId, sb.toString());
               }
            }
         }
      }
   }

   private void handleBindCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      String telegramUsername = message.getFrom().getUserName();
      String sessionId = argument.substring("bind_".length()).trim();
      if (!StringUtils.hasText(sessionId)) {
         this.sendMessage(chatId, "❌ 无效的绑定链接，请返回网页重新点击绑定。");
      } else if (sessionId.matches("\\d+")) {
         this.sendMessage(chatId, "⏰ 该绑定链接已过期或版本过旧，请返回网页重新点击绑定。");
      } else {
         try {
            TelegramBindingActionResponse result = this.telegramAuthService.completeBind(sessionId, telegramUserId, telegramUsername, null);
            if (result == null) {
               this.sendMessage(chatId, "⏰ 绑定链接已过期，请返回网页重新点击绑定。");
               return;
            }

            String embyUserName = result.getUser() == null ? "" : result.getUser().getEmbyUserName();
            if (result.isPending()) {
               this.sendMessage(
                  chatId,
                  "\ud83d\udd52 *绑定申请已提交*\n\n\ud83d\udc64 Emby 用户：`"
                     + this.escapeMarkdown(embyUserName)
                     + "`\n\ud83c\udd94 审批指纹：`"
                     + this.escapeMarkdown(this.publicReviewId(result))
                     + "`\n\n管理员审批通过后，Telegram 绑定才会生效。\n如需取消，请发送 `/cancelreview`。"
               );
            } else {
               this.sendMessage(
                  chatId,
                  "✅ *绑定成功！*\n\n\ud83c\udf89 您的 Telegram 账户已成功绑定到 Emby 用户：\n\ud83d\udc64 用户名：`"
                     + this.escapeMarkdown(embyUserName)
                     + "`\n\n\ud83c\udfac 现在可以发送 `/request 片名` 搜索 TMDB 并提交求片。\n\ud83d\udca1 也可以使用 `@机器人 影片名` 内联搜索，体验更好；"
               );
            }
         } catch (BizException var11) {
            this.sendMessage(chatId, "❌ " + this.telegramBizMessage(var11));
         } catch (Exception var12) {
            log.error("Telegram 一次性会话绑定失败", (Throwable)var12);
            this.sendMessage(chatId, "❌ 绑定失败，请稍后再试。");
         }
      }
   }

   private void handleCredentialBindCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      if (chatId != telegramUserId) {
         this.sendMessage(chatId, "\ud83d\udd12 请私聊机器人使用 `/bind 用户名 密码` 绑定账号。");
      } else {
         EmbyUser boundUser = this.telegramBindingManager.findBoundUser(telegramUserId, false);
         if (boundUser != null) {
            this.sendMessage(chatId, "✅ 已绑定 Emby 账号：`" + this.escapeMarkdown(boundUser.getEmbyUserName()) + "`\n\ud83d\udd01 如需更换绑定，请先发送 `/unbind`。");
         } else if (!this.tryAcquireRateLimit("bind:" + telegramUserId, 5L, 600L)) {
            this.sendMessage(chatId, "⏳ 绑定尝试太频繁了，请 10 分钟后再试。");
         } else {
            String[] args = argument.trim().split("\\s+", 2);
            if (args.length >= 2 && StringUtils.hasText(args[0]) && StringUtils.hasText(args[1])) {
               try {
                  TelegramBindingActionResponse result = this.telegramAuthService
                     .bindByCredentials(telegramUserId, message.getFrom().getUserName(), null, args[0], args[1]);
                  if (result.isServerSelectionRequired()) {
                     this.sendCredentialBindServerSelection(chatId, result);
                     return;
                  }

                  String embyUserName = result.getUser() == null ? args[0] : result.getUser().getEmbyUserName();
                  if (result.isPending()) {
                     this.sendMessage(
                        chatId,
                        "\ud83d\udd52 绑定申请已提交：`"
                           + this.escapeMarkdown(embyUserName)
                           + "`\n\ud83c\udd94 审批指纹：`"
                           + this.escapeMarkdown(this.publicReviewId(result))
                           + "`\n管理员审批通过后会通知你；如需取消，请发送 `/cancelreview`。"
                     );
                  } else {
                     this.sendMessage(
                        chatId,
                        "✅ 绑定成功：`"
                           + this.escapeMarkdown(embyUserName)
                           + "`\n\ud83c\udfac 现在可以发送 `/request 片名` 搜索 TMDB 并提交求片。\n\ud83d\udca1 也可以使用 `@机器人 影片名` 内联搜索，体验更好；"
                     );
                  }
               } catch (BizException var11) {
                  this.sendMessage(chatId, "❌ " + this.telegramBizMessage(var11));
               } catch (Exception var12) {
                  log.error("Telegram 私聊绑定失败", (Throwable)var12);
                  this.sendMessage(chatId, "❌ 绑定失败，请稍后再试。");
               }
            } else {
               this.sendMessage(chatId, "\ud83d\udcdd 用法：`/bind 用户名 密码`");
            }
         }
      }
   }

   private void sendCredentialBindServerSelection(long chatId, TelegramBindingActionResponse result) {
      if (StringUtils.hasText(result.getSelectionToken()) && !CollectionUtils.isEmpty(result.getServers())) {
         List<InlineKeyboardRow> rows = result.getServers()
            .stream()
            .filter(server -> server.getEmbyInfoId() != null)
            .map(
               server -> new InlineKeyboardRow(
                     this.button(
                        "\ud83d\udda5️ " + this.truncateTelegramButton(server.getServerName(), 28),
                        "tgb:" + result.getSelectionToken() + ":" + server.getEmbyInfoId()
                     )
                  )
            )
            .toList();
         if (rows.isEmpty()) {
            this.sendMessage(chatId, "❌ 服务器候选项读取失败，请重新发送 `/bind 用户名 密码`。");
         } else {
            this.sendKeyboardMessage(
               chatId, "\ud83d\udd17 *请选择要绑定的服务器*\n\n这个账号属于同一身份组下的多个服务器，请选择本次要绑定的服务器。\n选择会话 5 分钟内有效。", InlineKeyboardMarkup.builder().keyboard(rows).build()
            );
         }
      } else {
         this.sendMessage(chatId, "❌ 服务器候选项读取失败，请重新发送 `/bind 用户名 密码`。");
      }
   }

   private void handleCredentialBindServerCallback(CallbackQuery callbackQuery, String data) {
      this.answerCallbackQuery(callbackQuery.getId());
      if (callbackQuery.getFrom() != null && callbackQuery.getFrom().getId() != null && callbackQuery.getMessage() != null) {
         String[] parts = data.split(":", 3);
         long chatId = callbackQuery.getMessage().getChatId();
         Integer messageId = callbackQuery.getMessage().getMessageId();
         if (parts.length != 3) {
            this.editMessageTextSilently(chatId, messageId, "❌ 服务器选择无效，请重新发送 `/bind 用户名 密码`。");
         } else {
            try {
               Long embyInfoId = Long.valueOf(parts[2]);
               TelegramBindingActionResponse result = this.telegramAuthService
                  .completeCredentialBindSelection(parts[1], callbackQuery.getFrom().getId(), callbackQuery.getFrom().getUserName(), null, embyInfoId);
               String embyUserName = result.getUser() == null ? "Emby 账号" : result.getUser().getEmbyUserName();
               if (result.isPending()) {
                  this.editMessageTextSilently(
                     chatId,
                     messageId,
                     "\ud83d\udd52 绑定申请已提交：`"
                        + this.escapeMarkdown(embyUserName)
                        + "`\n\ud83c\udd94 审批指纹：`"
                        + this.escapeMarkdown(this.publicReviewId(result))
                        + "`\n管理员审批通过后会通知你；如需取消，请发送 `/cancelreview`。"
                  );
               } else {
                  this.editMessageTextSilently(
                     chatId,
                     messageId,
                     "✅ 绑定成功：`"
                        + this.escapeMarkdown(embyUserName)
                        + "`\n\ud83c\udfac 现在可以发送 `/request 片名` 搜索 TMDB 并提交求片。\n\ud83d\udca1 也可以使用 `@机器人 影片名` 内联搜索，体验更好；"
                  );
               }
            } catch (NumberFormatException var10) {
               this.editMessageTextSilently(chatId, messageId, "❌ 服务器选择无效，请重新发送 `/bind 用户名 密码`。");
            } catch (BizException var11) {
               this.editMessageTextSilently(chatId, messageId, "❌ " + this.telegramBizMessage(var11));
            } catch (Exception var12) {
               log.error("Telegram 凭据绑定服务器选择失败", (Throwable)var12);
               this.editMessageTextSilently(chatId, messageId, "❌ 绑定失败，请稍后再试。");
            }
         }
      }
   }

   private void handleTelegramUnbindCommand(Message message) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      if (chatId != telegramUserId) {
         this.sendMessage(chatId, "\ud83d\udd12 解绑只支持私聊机器人，请私聊发送 `/unbind`。");
      } else if (!this.tryAcquireRateLimit("unbind:" + telegramUserId, 5L, 600L)) {
         this.sendMessage(chatId, "⏳ 解绑操作太频繁了，请 10 分钟后再试。");
      } else {
         EmbyUser boundUser = this.findTelegramBoundUserForUnbind(telegramUserId);
         if (boundUser == null) {
            this.sendMessage(chatId, "ℹ️ 当前 Telegram 账号没有绑定 Emby 账号。");
         } else {
            InlineKeyboardButton confirmButton = InlineKeyboardButton.builder().text("✅ 确认解绑").callbackData("confirm_unbind").build();
            InlineKeyboardButton cancelButton = InlineKeyboardButton.builder().text("↩️ 取消").callbackData("cancel_unbind").build();
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(List.of(new InlineKeyboardRow(confirmButton, cancelButton))).build();
            SendMessage confirmMessage = SendMessage.builder()
               .chatId(chatId)
               .text(
                  this.foamPanelTitle("绑定管理")
                     + "⚠️ 确认解除 Telegram 绑定？\n\n当前绑定账号：`"
                     + this.escapeMarkdown(boundUser.getEmbyUserName())
                     + "`\n\n解绑后不会删除 Emby 账号，但将无法通过 Telegram 登录、求片或使用需要绑定身份的功能。"
               )
               .parseMode("Markdown")
               .replyMarkup(keyboard)
               .build();

            try {
               this.telegramClient.execute(confirmMessage);
            } catch (TelegramApiException var12) {
               log.error("发送 Telegram 解绑确认失败", (Throwable)var12);
               this.sendMessage(chatId, "❌ 发送解绑确认失败，请稍后再试。");
            }
         }
      }
   }

   private void handleTelegramUnbindConfirm(CallbackQuery callbackQuery) {
      if (callbackQuery != null && callbackQuery.getFrom() != null && callbackQuery.getMessage() != null) {
         long telegramUserId = callbackQuery.getFrom().getId();
         long chatId = callbackQuery.getMessage().getChatId();
         Integer messageId = callbackQuery.getMessage().getMessageId();
         if (chatId != telegramUserId) {
            this.sendMessage(chatId, "\ud83d\udd12 解绑只允许本人私聊确认。");
         } else if (!this.tryAcquireRateLimit("unbind_confirm:" + telegramUserId, 3L, 600L)) {
            this.editMessageTextSilently(chatId, messageId, "⏳ 解绑确认太频繁了，请 10 分钟后再试。");
         } else {
            try {
               TelegramBindingActionResponse result = this.telegramAuthService.unbindByTelegramId(telegramUserId);
               if (result.getUser() == null) {
                  this.editMessageTextSilently(chatId, messageId, "ℹ️ 当前 Telegram 账号没有绑定 Emby 账号。");
                  return;
               }

               if (result.isPending()) {
                  this.editMessageTextSilently(
                     chatId,
                     messageId,
                     "\ud83d\udd52 已提交 Telegram 解绑申请：`"
                        + this.escapeMarkdown(result.getUser().getEmbyUserName())
                        + "`\n\ud83c\udd94 审批指纹：`"
                        + this.escapeMarkdown(this.publicReviewId(result))
                        + "`\n管理员通过前，当前绑定仍然有效。\n如需取消，请发送 `/cancelreview`。"
                  );
               } else {
                  this.editMessageTextSilently(
                     chatId,
                     messageId,
                     "✅ 已解除 Telegram 绑定：`"
                        + this.escapeMarkdown(result.getUser().getEmbyUserName())
                        + "`\n\ud83d\udce6 Emby 账号仍然保留，需要时可重新使用 `/bind 用户名 密码` 绑定。"
                  );
               }
            } catch (BizException var8) {
               this.editMessageTextSilently(chatId, messageId, "❌ " + var8.getMessage());
            } catch (Exception var9) {
               log.error("Telegram 私聊解绑失败", (Throwable)var9);
               this.editMessageTextSilently(chatId, messageId, "❌ 解绑失败，请稍后再试。");
            }
         }
      }
   }

   private void handleTelegramBindingReviewCallback(CallbackQuery callbackQuery, String decision, String reviewIdText) {
      if (callbackQuery != null && callbackQuery.getFrom() != null && callbackQuery.getMessage() != null) {
         long chatId = callbackQuery.getMessage().getChatId();
         Integer messageId = callbackQuery.getMessage().getMessageId();

         long reviewId;
         try {
            reviewId = Long.parseLong(reviewIdText);
         } catch (NumberFormatException var15) {
            this.editMessageTextSilently(chatId, messageId, "❌ 审批指纹无效。");
            return;
         }

         boolean approved = "approve".equalsIgnoreCase(decision);
         if (!approved && !"reject".equalsIgnoreCase(decision)) {
            this.editMessageTextSilently(chatId, messageId, "❌ 审批操作无效。");
         } else {
            try {
               org.telegram.telegrambots.meta.api.objects.User telegramReviewer = callbackQuery.getFrom();
               TelegramBindingReviewResponse result = this.telegramBindingReviewService
                  .reviewFromTelegram(reviewId, approved, telegramReviewer.getId(), telegramReviewer.getUserName(), this.telegramDisplayName(telegramReviewer));
               String reviewerMention = this.telegramUserMention(telegramReviewer.getId(), this.telegramDisplayName(telegramReviewer));
               this.editMessageTextSilently(
                  chatId,
                  messageId,
                  (approved ? "✅ *审批已通过*" : "❌ *审批已拒绝*")
                     + "\n\n\ud83d\udccc 操作："
                     + this.escapeMarkdown(result.getActionTypeName())
                     + "\n\ud83d\udc64 Emby 账号：`"
                     + this.escapeMarkdown(result.getEmbyUserName())
                     + "`\n\ud83e\uddd1\u200d\ud83d\udcbc 审批人："
                     + reviewerMention
                     + "\n\ud83c\udd94 审批指纹：`"
                     + this.escapeMarkdown(StringUtils.hasText(result.getReviewUuid()) ? result.getReviewUuid() : String.valueOf(result.getId()))
                     + "`"
               );
            } catch (BizException var13) {
               this.editMessageTextSilently(chatId, messageId, "⚠️ " + this.escapeMarkdown(var13.getMessage()));
            } catch (Exception var14) {
               log.error("Telegram 内联审批失败: reviewId={}", reviewId, var14);
               this.editMessageTextSilently(chatId, messageId, "❌ 审批失败，请稍后重试。");
            }
         }
      }
   }

   private EmbyUser findTelegramBoundUserForUnbind(Long telegramUserId) {
      if (telegramUserId == null) {
         return null;
      } else {
         UserOauthBinding binding = this.userOauthBindingMapper
            .selectOne(
               new LambdaQueryWrapper<UserOauthBinding>()
                  .eq(UserOauthBinding::getProvider, "telegram")
                  .eq(UserOauthBinding::getProviderUserId, String.valueOf(telegramUserId))
                  .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                  .last("limit 1")
            );
         return binding != null && binding.getUserId() != null
            ? this.embyUserService.lambdaQuery().eq(EmbyUser::getId, binding.getUserId()).eq(BaseEntity::getDelFlag, Integer.valueOf(0)).one()
            : null;
      }
   }

   private boolean handlePendingKkRegistrationStart(Message message) {
      if (message != null
         && message.getFrom() != null
         && message.getFrom().getId() != null
         && message.isUserMessage()
         && message.getFrom().getId().equals(message.getChatId())) {
         long telegramUserId = message.getFrom().getId();
         String targetGrantKey = "bot:kk:register:target:" + telegramUserId;
         String token = this.stringRedisTemplate.opsForValue().get(targetGrantKey);
         if (!StringUtils.hasText(token)) {
            return false;
         } else {
            DataQueryBot.KkRegistrationGrant grant = this.loadKkRegistrationGrant(token);
            if (grant != null && grant.getTargetTelegramUserId() == telegramUserId) {
               this.handleKkRegistrationStart(message, token);
               return true;
            } else {
               this.stringRedisTemplate.delete(targetGrantKey);
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private void handleKkRegistrationStart(Message message, String token) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      if (message.isUserMessage() && chatId == telegramUserId) {
         DataQueryBot.KkRegistrationGrant grant = this.loadKkRegistrationGrant(token);
         if (grant == null || grant.getTargetTelegramUserId() != telegramUserId) {
            this.sendMessage(chatId, "\ud83c\udf19 这份账号礼物已失效，或并不是为当前 Telegram 身份准备的。");
         } else if (!this.canGrantKkRegistration(grant.getOperatorId())) {
            this.deleteKkRegistrationGrant(token, telegramUserId);
            this.sendMessage(chatId, "\ud83c\udf19 这份账号礼物暂时无法领取，请让发起人重新打开 `/kk` 面板。");
         } else if (this.isKkProtectedTelegramTarget(telegramUserId)) {
            this.deleteKkRegistrationGrant(token, telegramUserId);
            this.sendMessage(chatId, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
         } else if (this.telegramBindingManager.findBoundUser(telegramUserId, false) != null) {
            this.deleteKkRegistrationGrant(token, telegramUserId);
            this.sendMessage(chatId, "\ud83c\udf01 你已经关联了 Mist 账号，不需要重复创建啦。");
         } else if (this.telegramBindingReviewService.hasPendingReviewForTelegram(telegramUserId)) {
            this.deleteKkRegistrationGrant(token, telegramUserId);
            this.sendMessage(chatId, "\ud83d\udd52 当前还有一份绑定申请正在等待处理，请完成后再领取账号礼物。");
         } else {
            this.pendingKkRegistrations
               .put(
                  telegramUserId,
                  new DataQueryBot.PendingKkRegistration(token, grant.getOperatorId(), System.currentTimeMillis() + START_PANEL_INPUT_TTL_MILLIS)
               );
            InlineKeyboardMarkup cancelKeyboard = InlineKeyboardMarkup.builder()
               .keyboard(List.of(new InlineKeyboardRow(InlineKeyboardButton.builder().text("❌ 取消领取").callbackData("kkreg_cancel:" + token).build())))
               .build();
            this.sendKeyboardMessage(
               chatId, "\ud83c\udf81 *一份 Mist 账号礼物正在等待你*\n\n请在 10 分钟内发送想使用的 Emby 用户名。\n\ud83d\udd10 登录密码会由系统安全生成，并且只在这个私聊中发送给你。", cancelKeyboard
            );
         }
      } else {
         this.sendMessage(chatId, "\ud83d\udd12 请在与机器人的私聊中打开这份账号礼物。");
      }
   }

   private void handleKkRegistrationCancelCallback(CallbackQuery callbackQuery, String token) {
      if (callbackQuery.getMessage() instanceof Message message && callbackQuery.getFrom() != null && callbackQuery.getFrom().getId() != null) {
         long telegramUserId = callbackQuery.getFrom().getId();
         if (message.isUserMessage() && message.getChatId() == telegramUserId) {
            DataQueryBot.PendingKkRegistration pending = this.pendingKkRegistrations.get(telegramUserId);
            if (pending != null && Objects.equals(token, pending.getToken())) {
               this.pendingKkRegistrations.remove(telegramUserId, pending);
               this.deleteKkRegistrationGrant(pending.getToken(), telegramUserId);
               this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf01 已取消领取。");
               this.editStartPanelMessage(message, "\ud83c\udf01 已取消领取，这份账号礼物没有被使用。", null);
               return;
            }

            this.answerCallbackQuery(callbackQuery.getId(), "⏳ 这份领取会话已经失效。");
            return;
         }

         this.answerCallbackQuery(callbackQuery.getId(), "\ud83d\udd12 请在目标用户的机器人私聊中操作。");
         return;
      }

      this.answerCallbackQuery(callbackQuery.getId(), "\ud83c\udf19 这份账号礼物暂时无法取消，请稍后再试。");
   }

   private boolean handlePendingKkRegistrationInput(Message message) {
      if (message != null
         && message.hasText()
         && message.getFrom() != null
         && message.getFrom().getId() != null
         && message.isUserMessage()
         && message.getFrom().getId().equals(message.getChatId())) {
         long telegramUserId = message.getFrom().getId();
         DataQueryBot.PendingKkRegistration pending = this.pendingKkRegistrations.get(telegramUserId);
         if (pending == null) {
            return false;
         } else {
            long chatId = message.getChatId();
            String input = message.getText().trim();
            if (pending.isExpired()) {
               this.pendingKkRegistrations.remove(telegramUserId, pending);
               this.sendMessage(chatId, "⏰ 领取时间已结束，请让发起人重新打开 `/kk` 面板。");
               return true;
            } else if ("/cancel".equalsIgnoreCase(input)) {
               this.pendingKkRegistrations.remove(telegramUserId, pending);
               this.deleteKkRegistrationGrant(pending.getToken(), telegramUserId);
               this.sendMessage(chatId, "\ud83c\udf01 已取消领取，这份账号礼物没有被使用。");
               return true;
            } else if (input.startsWith("/")) {
               this.pendingKkRegistrations.remove(telegramUserId, pending);
               return false;
            } else if (StringUtils.hasText(input) && !input.chars().anyMatch(Character::isWhitespace)) {
               DataQueryBot.KkRegistrationGrant grant = this.loadKkRegistrationGrant(pending.getToken());
               if (grant == null
                  || grant.getTargetTelegramUserId() != telegramUserId
                  || grant.getOperatorId() != pending.getOperatorId()
                  || !this.canGrantKkRegistration(grant.getOperatorId())) {
                  this.pendingKkRegistrations.remove(telegramUserId, pending);
                  this.deleteKkRegistrationGrant(pending.getToken(), telegramUserId);
                  this.sendMessage(chatId, "\ud83c\udf19 这份账号礼物已失效，请让发起人重新打开 `/kk` 面板。");
                  return true;
               } else if (this.isKkProtectedTelegramTarget(telegramUserId)) {
                  this.pendingKkRegistrations.remove(telegramUserId, pending);
                  this.deleteKkRegistrationGrant(pending.getToken(), telegramUserId);
                  this.sendMessage(chatId, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
                  return true;
               } else if (this.telegramBindingManager.findBoundUser(telegramUserId, false) != null) {
                  this.pendingKkRegistrations.remove(telegramUserId, pending);
                  this.deleteKkRegistrationGrant(pending.getToken(), telegramUserId);
                  this.sendMessage(chatId, "\ud83c\udf01 你已经关联了 Mist 账号，不需要重复创建啦。");
                  return true;
               } else if (this.telegramBindingReviewService.hasPendingReviewForTelegram(telegramUserId)) {
                  this.sendMessage(chatId, "\ud83d\udd52 当前还有一份绑定申请正在等待处理，请完成后再继续。");
                  return true;
               } else if (!this.tryAcquireRateLimit("register:" + telegramUserId, 5L, REGISTER_RATE_LIMIT_WINDOW_SECONDS)) {
                  this.sendMessage(chatId, "⏳ 尝试太频繁了，请 10 分钟后再试。");
                  return true;
               } else {
                  String lockKey = "bot:register:lock:" + telegramUserId;
                  String lockToken = this.redisLockUtils.tryLock(lockKey, TELEGRAM_REGISTER_LOCK_SECONDS);
                  if (!StringUtils.hasText(lockToken)) {
                     this.sendMessage(chatId, "⏳ 当前账号礼物正在处理中，请稍后再试。");
                     return true;
                  } else {
                     boolean submitted = false;

                     boolean e;
                     try {
                        if (this.hasTelegramRegisterQueueCapacity()) {
                           String taskId = UUID.randomUUID().toString();
                           String generatedPassword = this.generateKkRegistrationPassword();
                           DataQueryBot.PendingTelegramRegister task = new DataQueryBot.PendingTelegramRegister(
                              taskId,
                              chatId,
                              telegramUserId,
                              message.getFrom().getUserName(),
                              input,
                              generatedPassword,
                              "Telegram /kk 赠送开户",
                              lockKey,
                              lockToken,
                              System.currentTimeMillis(),
                              true,
                              grant.getOperatorId()
                           );
                           this.saveTelegramRegisterTask(task);
                           this.stringRedisTemplate.opsForList().leftPush("bot:register:queue", taskId);
                           this.pendingKkRegistrations.remove(telegramUserId, pending);
                           this.deleteKkRegistrationGrant(pending.getToken(), telegramUserId);
                           this.sendMessage(chatId, "⏳ 账号礼物已进入创建队列，完成后会在这个私聊发送账号和密码。");
                           submitted = true;
                           return true;
                        }

                        this.sendMessage(chatId, "⏳ 当前注册队列繁忙，请稍后再试。");
                        e = true;
                     } catch (Exception var19) {
                        log.warn("Telegram /kk 赠送开户任务入队失败: telegramUserId={}", telegramUserId, var19);
                        this.sendMessage(chatId, "❌ 创建任务入队失败，请稍后再试。");
                        return true;
                     } finally {
                        if (!submitted) {
                           this.redisLockUtils.unlock(lockKey, lockToken);
                        }
                     }

                     return e;
                  }
               }
            } else {
               this.sendMessage(chatId, "\ud83d\udcdd 用户名不能为空或包含空格，请重新发送一个用户名。");
               return true;
            }
         }
      } else {
         return false;
      }
   }

   private boolean canGrantKkRegistration(long operatorId) {
      return this.telegramBotAuthorizationService.isAdmin(operatorId)
         && this.telegramBotAuthorizationService.hasPermission(operatorId, TelegramBotPermission.USER_CREATE);
   }

   private DataQueryBot.KkRegistrationGrant loadKkRegistrationGrant(String token) {
      if (!StringUtils.hasText(token)) {
         return null;
      } else {
         String value = this.stringRedisTemplate.opsForValue().get("bot:kk:register:" + token);
         if (!StringUtils.hasText(value)) {
            return null;
         } else {
            String[] parts = value.split(":", 2);
            if (parts.length != 2) {
               return null;
            } else {
               long operatorId = this.parseTelegramId(parts[0]);
               long targetTelegramUserId = this.parseTelegramId(parts[1]);
               return operatorId > 0L && targetTelegramUserId > 0L ? new DataQueryBot.KkRegistrationGrant(operatorId, targetTelegramUserId) : null;
            }
         }
      }
   }

   private void deleteKkRegistrationGrant(String token, long targetTelegramUserId) {
      if (StringUtils.hasText(token)) {
         this.stringRedisTemplate.delete("bot:kk:register:" + token);
      }

      if (targetTelegramUserId > 0L) {
         String targetGrantKey = "bot:kk:register:target:" + targetTelegramUserId;
         String currentToken = this.stringRedisTemplate.opsForValue().get(targetGrantKey);
         if (!StringUtils.hasText(currentToken) || Objects.equals(token, currentToken)) {
            this.stringRedisTemplate.delete(targetGrantKey);
         }
      }
   }

   private String generateKkRegistrationPassword() {
      StringBuilder password = new StringBuilder(12);

      for (int index = 0; index < 6; index++) {
         password.append(KK_PASSWORD_LETTERS[KK_PASSWORD_RANDOM.nextInt(KK_PASSWORD_LETTERS.length)]);
         password.append(KK_PASSWORD_DIGITS[KK_PASSWORD_RANDOM.nextInt(KK_PASSWORD_DIGITS.length)]);
      }

      return password.toString();
   }

   private void handleTelegramRegisterCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      if (!message.isUserMessage()) {
         this.sendMessage(chatId, "\ud83d\udd12 注册只支持私聊机器人，请私聊发送 `/register 用户名 密码`。");
      } else if (!this.isTelegramBotRegistrationEnabled()) {
         this.sendMessage(chatId, "\ud83d\udeab Telegram 注册暂未开启，请联系管理员。");
      } else if (!this.tryAcquireRateLimit("register:" + telegramUserId, 5L, REGISTER_RATE_LIMIT_WINDOW_SECONDS)) {
         this.sendMessage(chatId, "⏳ 注册尝试太频繁了，请 10 分钟后再试。");
      } else {
         String[] args = argument.trim().split("\\s+", 2);
         if (args.length >= 2 && StringUtils.hasText(args[0]) && StringUtils.hasText(args[1])) {
            if (this.telegramAuthService.findBoundUser(telegramUserId) != null) {
               this.sendMessage(chatId, "ℹ️ 当前 Telegram 已绑定 Emby 账号，无需重复注册。\n\ud83d\udc64 发送 `/myaccount` 可查看绑定信息。");
            } else if (this.telegramBindingReviewService.hasPendingReviewForTelegram(telegramUserId)) {
               this.sendMessage(chatId, "\ud83d\udd52 当前已有待审批的 Telegram 绑定申请，请等待管理员处理后再注册。");
            } else {
               String lockKey = "bot:register:lock:" + telegramUserId;
               String lockToken = this.redisLockUtils.tryLock(lockKey, TELEGRAM_REGISTER_LOCK_SECONDS);
               if (!StringUtils.hasText(lockToken)) {
                  this.sendMessage(chatId, "⏳ 当前 Telegram 注册正在处理中，请稍后再试。");
               } else {
                  boolean submitted = false;

                  try {
                     if (this.hasTelegramRegisterQueueCapacity()) {
                        String embyUserName = args[0].trim();
                        String rawPassword = args[1].trim();
                        String taskId = UUID.randomUUID().toString();
                        DataQueryBot.PendingTelegramRegister task = new DataQueryBot.PendingTelegramRegister(
                           taskId,
                           chatId,
                           telegramUserId,
                           message.getFrom().getUserName(),
                           embyUserName,
                           rawPassword,
                           this.buildTelegramRegisterChannelDetail(message),
                           lockKey,
                           lockToken,
                           System.currentTimeMillis()
                        );
                        this.saveTelegramRegisterTask(task);
                        this.stringRedisTemplate.opsForList().leftPush("bot:register:queue", taskId);
                        this.sendMessage(chatId, "⏳ 已进入 Telegram 注册队列，完成后会私聊通知你。");
                        submitted = true;
                        return;
                     }

                     this.sendMessage(chatId, "⏳ 当前注册队列繁忙，请稍后再试。");
                  } catch (Exception var18) {
                     log.warn("Telegram 注册任务入队失败: telegramUserId={}", telegramUserId, var18);
                     this.sendMessage(chatId, "❌ 注册任务入队失败，请稍后再试。");
                     return;
                  } finally {
                     if (!submitted) {
                        this.redisLockUtils.unlock(lockKey, lockToken);
                     }
                  }
               }
            }
         } else {
            this.sendMessage(chatId, "\ud83d\udcdd 用法：`/register 用户名 密码`\n\ud83d\udd10 密码长度需为 6-30 位。");
         }
      }
   }

   private void handleTelegramCancelReviewCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      if (message.isUserMessage() && chatId == telegramUserId) {
         if (!this.tryAcquireRateLimit("cancel_review:" + telegramUserId, 5L, 600L)) {
            this.sendMessage(chatId, "⏳ 取消申请操作太频繁了，请 10 分钟后再试。");
         } else {
            String reviewUuid = StringUtils.hasText(argument) ? argument.trim().replaceFirst("^#", "") : null;

            try {
               TelegramBindingReviewResponse result = this.telegramBindingReviewService.cancelByTelegramUserId(telegramUserId, reviewUuid);
               this.sendMessage(
                  chatId,
                  "↩️ *申请已自助取消*\n\n\ud83d\udccc 操作："
                     + this.escapeMarkdown(result.getActionTypeName())
                     + "\n\ud83d\udc64 Emby 账号：`"
                     + this.escapeMarkdown(result.getEmbyUserName())
                     + "`\n\ud83c\udd94 审批指纹：`"
                     + this.escapeMarkdown(result.getReviewUuid())
                     + "`\n\n管理员将无法再通过或拒绝这条申请，需要时可重新提交。"
               );
            } catch (BizException var9) {
               this.sendMessage(chatId, "❌ " + var9.getMessage());
            } catch (Exception var10) {
               log.error("Telegram 用户自助取消审批申请失败: telegramUserId={}, reviewUuid={}", telegramUserId, reviewUuid, var10);
               this.sendMessage(chatId, "❌ 取消申请失败，请稍后再试。");
            }
         }
      } else {
         this.sendMessage(chatId, "\ud83d\udd12 取消审批申请只支持私聊机器人，请私聊发送 `/cancelreview`。");
      }
   }

   private void handleTelegramCardOpenCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      if (!message.isUserMessage()) {
         this.sendMessage(chatId, "\ud83d\udd12 卡密开号只支持私聊机器人，请私聊发送 `/cardopen 卡密 用户名 [密码]`。");
      } else if (!this.tryAcquireRateLimit("cardopen:" + telegramUserId, 5L, 600L)) {
         this.sendMessage(chatId, "⏳ 卡密操作太频繁了，请 10 分钟后再试。");
      } else {
         String[] args = argument.trim().split("\\s+");
         if (args.length >= 2 && StringUtils.hasText(args[0]) && StringUtils.hasText(args[1])) {
            String rawPassword = args.length >= 3 ? args[2].trim() : null;
            if (!StringUtils.hasText(rawPassword) || rawPassword.length() >= 6 && rawPassword.length() <= 30) {
               String lockKey = "bot:cardopen:lock:" + telegramUserId;
               String lockToken = this.redisLockUtils.tryLock(lockKey, TELEGRAM_CARD_OPEN_LOCK_SECONDS);
               if (!StringUtils.hasText(lockToken)) {
                  this.sendMessage(chatId, "⏳ 当前卡密开号正在处理中，请稍后再试。");
               } else {
                  try {
                     EmbyUser existingBinding = this.findTelegramBoundUserForUnbind(telegramUserId);
                     if (existingBinding != null) {
                        this.sendMessage(
                           chatId,
                           "ℹ️ 当前 Telegram 已绑定 Emby 账号：`"
                              + this.escapeMarkdown(existingBinding.getEmbyUserName())
                              + "`\n如需续费，请发送 `/cardrenew 卡密`；如需开号，请先 `/unbind`。"
                        );
                        return;
                     }

                     if (!this.telegramBindingReviewService.hasPendingReviewForTelegram(telegramUserId)) {
                        InsertUserCardRequest request = new InsertUserCardRequest();
                        request.setCardPassword(args[0].trim());
                        request.setEmbyUserName(args[1].trim());
                        request.setPassword(rawPassword);
                        request.setRemarks("Telegram卡密开号");
                        InsertUserResponse response = this.embyUserService
                           .insertUserCardByTelegram(request, this.buildTelegramCardChannelDetail("Telegram卡密开号", message));
                        TelegramBindingActionResponse bindResult = this.bindCreatedCardUser(response, telegramUserId, message);
                        StringBuilder sb = new StringBuilder();
                        if (bindResult == null) {
                           sb.append("✅ 卡密开号成功，但自动绑定失败。\n请使用 `/bind 用户名 密码` 手动绑定。\n\n");
                        } else if (bindResult.isPending()) {
                           sb.append("✅ 卡密开号成功，绑定申请已提交审批。\n")
                              .append("\ud83c\udd94 审批指纹：`")
                              .append(this.publicReviewId(bindResult))
                              .append("`\n")
                              .append("如需取消，请发送 `/cancelreview`。\n\n");
                        } else {
                           sb.append("✅ 卡密开号成功，已自动绑定。\n\n");
                        }

                        this.appendCreateAccountAccessLines(sb, response);
                        this.sendMessage(chatId, sb.toString());
                        return;
                     }

                     this.sendMessage(chatId, "\ud83d\udd52 当前已有待审批的 Telegram 绑定申请，请等待管理员处理后再使用卡密开号。");
                  } catch (BizException var21) {
                     this.sendMessage(chatId, "❌ " + var21.getMessage());
                     return;
                  } catch (ApiException var22) {
                     log.error("Telegram 卡密开号创建 Emby 用户失败: telegramUserId={}", telegramUserId, var22);
                     this.sendMessage(chatId, "❌ Emby 服务器创建用户失败，请稍后再试或联系管理员。");
                     return;
                  } catch (Exception var23) {
                     log.error("Telegram 卡密开号失败: telegramUserId={}", telegramUserId, var23);
                     this.sendMessage(chatId, "❌ 卡密开号失败，请稍后再试。");
                     return;
                  } finally {
                     this.redisLockUtils.unlock(lockKey, lockToken);
                  }
               }
            } else {
               this.sendMessage(chatId, "\ud83d\udd10 密码长度需为 6-30 位。");
            }
         } else {
            this.sendMessage(chatId, "\ud83d\udcdd 用法：`/cardopen 卡密 用户名 [密码]`\n\ud83d\udd10 不填写密码时系统会自动生成。");
         }
      }
   }

   private void handleTelegramCardRenewCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      if (!message.isUserMessage()) {
         this.sendMessage(chatId, "\ud83d\udd12 卡密续费只支持私聊机器人，请私聊发送 `/cardrenew 卡密`。");
      } else if (!this.tryAcquireRateLimit("cardrenew:" + telegramUserId, 5L, 600L)) {
         this.sendMessage(chatId, "⏳ 卡密操作太频繁了，请 10 分钟后再试。");
      } else {
         String cardPassword = argument.trim();
         if (StringUtils.hasText(cardPassword) && cardPassword.split("\\s+").length <= 1) {
            EmbyUser boundUser = this.findTelegramBoundUserForUnbind(telegramUserId);
            if (boundUser == null) {
               this.sendMessage(chatId, "\ud83d\udd17 当前 Telegram 尚未绑定 Emby 账号。\n已有账号请先 `/bind 用户名 密码`，没有账号可用 `/cardopen 卡密 用户名` 开号。");
            } else {
               try {
                  EmbyUserCustomResponse response = this.embyUserService
                     .renewUserByTelegramCard(cardPassword, boundUser.getId(), this.buildTelegramCardChannelDetail("Telegram卡密续费", message));
                  StringBuilder sb = new StringBuilder();
                  sb.append("✅ 卡密续费成功。\n\n");
                  sb.append("\ud83d\udc64 用户名：`").append(this.escapeMarkdown(response.getEmbyUserName())).append("`\n");
                  this.appendWhitelistUserLine(sb, response.getHostLineType(), true);
                  sb.append("⏰ 新到期时间：").append(this.formatExpirationForHostLine(response.getExpirationDate(), response.getHostLineType())).append("\n");
                  this.appendServerNameLine(sb, response.getId());
                  this.sendMessage(chatId, sb.toString());
               } catch (BizException var11) {
                  if (ResponseStatusEnum.PLEASE_USE_CORRECT_CARD_KEY.getCode().equals(var11.getCode())) {
                     this.sendMessage(chatId, "❌ 这张卡密不属于当前绑定账号所在服务器，请确认卡密来源。");
                     return;
                  }

                  this.sendMessage(chatId, "❌ " + var11.getMessage());
               } catch (Exception var12) {
                  log.error("Telegram 卡密续费失败: telegramUserId={}, userId={}", telegramUserId, boundUser.getId(), var12);
                  this.sendMessage(chatId, "❌ 卡密续费失败，请稍后再试。");
               }
            }
         } else {
            this.sendMessage(chatId, "\ud83d\udcdd 用法：`/cardrenew 卡密`\n续费对象为当前 Telegram 绑定的 Emby 账号。");
         }
      }
   }

   private void startTelegramRegisterWorkers() {
      if (this.telegramRegisterWorkersStarted.compareAndSet(false, true)) {
         this.recoverTelegramRegisterProcessingQueue();

         for (int i = 0; i < 6; i++) {
            this.telegramRegisterExecutor.submit(this::consumeTelegramRegisterQueue);
         }

         log.info("Telegram 注册 Redis 队列 worker 已启动，workerCount={}", 6);
      }
   }

   private void recoverTelegramRegisterProcessingQueue() {
      int recovered = 0;

      try {
         while (true) {
            String taskId = this.stringRedisTemplate.opsForList().rightPop("bot:register:processing");
            if (!StringUtils.hasText(taskId)) {
               if (recovered > 0) {
                  log.info("已恢复 Telegram 注册 processing 队列任务: count={}", recovered);
               }
               break;
            }

            if (Boolean.TRUE.equals(this.stringRedisTemplate.hasKey(this.telegramRegisterTaskKey(taskId)))) {
               this.stringRedisTemplate.opsForList().leftPush("bot:register:queue", taskId);
               recovered++;
            }
         }
      } catch (Exception var3) {
         log.warn("恢复 Telegram 注册 processing 队列失败", (Throwable)var3);
      }
   }

   private void consumeTelegramRegisterQueue() {
      while (this.telegramRegisterWorkersRunning && !Thread.currentThread().isInterrupted()) {
         String taskId = null;

         try {
            taskId = this.stringRedisTemplate.opsForList().rightPopAndLeftPush("bot:register:queue", "bot:register:processing", 5L, TimeUnit.SECONDS);
            if (StringUtils.hasText(taskId)) {
               DataQueryBot.PendingTelegramRegister task = this.loadTelegramRegisterTask(taskId);
               if (task == null) {
                  log.warn("Telegram 注册任务不存在或已过期: taskId={}", taskId);
               } else if (!taskId.equals(task.getTaskId())) {
                  log.warn("Telegram 注册任务 ID 不匹配: queueTaskId={}, payloadTaskId={}", taskId, task.getTaskId());
               } else {
                  this.processTelegramRegisterTask(task);
               }
            }
         } catch (Exception var6) {
            log.warn("消费 Telegram 注册 Redis 队列失败", (Throwable)var6);
            this.sleepTelegramRegisterWorker();
         } finally {
            if (StringUtils.hasText(taskId)) {
               this.ackTelegramRegisterTask(taskId);
            }
         }
      }
   }

   private void sleepTelegramRegisterWorker() {
      try {
         TimeUnit.SECONDS.sleep(1L);
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
      }
   }

   private boolean hasTelegramRegisterQueueCapacity() {
      Long waitingSize = this.stringRedisTemplate.opsForList().size("bot:register:queue");
      Long processingSize = this.stringRedisTemplate.opsForList().size("bot:register:processing");
      long totalSize = (waitingSize == null ? 0L : waitingSize) + (processingSize == null ? 0L : processingSize);
      return totalSize < 10000L;
   }

   private void saveTelegramRegisterTask(DataQueryBot.PendingTelegramRegister task) {
      this.stringRedisTemplate
         .opsForValue()
         .set(this.telegramRegisterTaskKey(task.getTaskId()), JSON.toJSONString(task), TELEGRAM_REGISTER_TASK_TTL_SECONDS, TimeUnit.SECONDS);
   }

   private DataQueryBot.PendingTelegramRegister loadTelegramRegisterTask(String taskId) {
      String taskJson = this.stringRedisTemplate.opsForValue().get(this.telegramRegisterTaskKey(taskId));
      return !StringUtils.hasText(taskJson) ? null : JSON.parseObject(taskJson, DataQueryBot.PendingTelegramRegister.class);
   }

   private void ackTelegramRegisterTask(String taskId) {
      try {
         this.stringRedisTemplate.opsForList().remove("bot:register:processing", 1L, taskId);
         this.stringRedisTemplate.delete(this.telegramRegisterTaskKey(taskId));
      } catch (Exception var3) {
         log.warn("确认 Telegram 注册任务完成失败: taskId={}", taskId, var3);
      }
   }

   private String telegramRegisterTaskKey(String taskId) {
      return "bot:register:task:" + taskId;
   }

   private boolean isTelegramRegisterTaskLockValid(DataQueryBot.PendingTelegramRegister task) {
      Object currentToken = this.redisTemplate.opsForValue().get(task.getLockKey());
      return currentToken != null && task.getLockToken().equals(String.valueOf(currentToken));
   }

   private void processTelegramRegisterTask(DataQueryBot.PendingTelegramRegister task) {
      boolean quotaReserved = false;
      boolean giftedByKk = task.isAdminGifted();

      try {
         if (!this.isTelegramRegisterTaskLockValid(task)) {
            log.warn("忽略已失效的 Telegram 注册任务: taskId={}, telegramUserId={}", task.getTaskId(), task.getTelegramUserId());
            return;
         }

         if (giftedByKk && (!this.canGrantKkRegistration(task.getGrantedByTelegramUserId()) || this.isKkProtectedTelegramTarget(task.getTelegramUserId()))) {
            this.sendMessage(task.getChatId(), "\ud83c\udf19 这份账号礼物的授权状态已经变化，本次没有创建账号。");
            return;
         }

         boolean alreadyBound = giftedByKk
            ? this.telegramBindingManager.findBoundUser(task.getTelegramUserId(), false) != null
            : this.telegramAuthService.findBoundUser(task.getTelegramUserId()) != null;
         if (alreadyBound) {
            this.sendMessage(task.getChatId(), "ℹ️ 当前 Telegram 已绑定 Emby 账号，无需重复注册。\n\ud83d\udc64 发送 `/myaccount` 可查看绑定信息。");
            return;
         }

         if (!giftedByKk) {
            if (!this.isTelegramBotRegistrationEnabled()) {
               this.sendMessage(task.getChatId(), "\ud83d\udeab Telegram 注册暂未开启，请联系管理员。");
               return;
            }

            if (!this.tryReserveTelegramRegisterQuota()) {
               this.sendMessage(task.getChatId(), "\ud83c\udf9f️ 本轮 Telegram 注册名额已用完，或管理员尚未配置可用名额。");
               return;
            }

            quotaReserved = true;
         }

         RegisteredUserSave save = new RegisteredUserSave();
         save.setEmbyUserName(task.getEmbyUserName());
         save.setEmbyUserPassword(task.getRawPassword());
         save.setUserStatus(0);
         save.setRemarks(giftedByKk ? "Telegram /kk 赠送开户" : "Telegram注册");
         if (giftedByKk) {
            int defaultDays = this.resolveKkGiftRegisterDefaultDays();
            if (defaultDays > 0) {
               save.setExpirationDate(Date.from(LocalDateTime.now().plusDays((long)defaultDays).atZone(ZoneId.systemDefault()).toInstant()));
            }
         }

         RegisteredUserResponse registeredUser = this.embyUserService.registeredUserByTelegram(save, task.getRegisterChannelDetail());

         try {
            if (!giftedByKk) {
               TelegramBindingActionResponse bindResult = this.telegramAuthService
                  .bindExistingUser(registeredUser.getId(), task.getTelegramUserId(), task.getTelegramUsername(), null, "BOT_REGISTER");
               if (bindResult.isPending()) {
                  this.sendMessage(
                     task.getChatId(),
                     "✅ Telegram 注册成功，绑定申请已提交审批。\n\ud83d\udc64 用户名："
                        + this.escapeMarkdown(registeredUser.getEmbyUserName())
                        + "\n\ud83d\udd10 密码："
                        + this.escapeMarkdown(task.getRawPassword())
                        + "\n\ud83c\udd94 审批指纹：`"
                        + this.escapeMarkdown(this.publicReviewId(bindResult))
                        + "`\n\n管理员审批通过后会通知你；如需取消，请发送 `/cancelreview`。"
                  );
               } else {
                  this.sendMessage(
                     task.getChatId(),
                     "✅ Telegram 注册成功，已自动绑定。\n\ud83d\udc64 用户名："
                        + this.escapeMarkdown(registeredUser.getEmbyUserName())
                        + "\n\ud83d\udd10 密码："
                        + this.escapeMarkdown(task.getRawPassword())
                        + "\n\n\ud83c\udfac 现在可以发送 `/request 片名` 搜索 TMDB 并提交求片。\n\ud83d\udca1 也可以使用 `@机器人 影片名` 内联搜索，体验更好；"
                  );
               }

               return;
            }

            EmbyUser createdUser = this.embyUserService.getById(registeredUser.getId());
            if (createdUser == null) {
               throw new BizException("创建后的 Mist 用户记录不存在");
            }

            this.telegramBindingManager.bind(createdUser, task.getTelegramUserId(), task.getTelegramUsername(), null, false);
            this.sendMessage(
               task.getChatId(),
               "\ud83c\udf89 *Mist 账号创建成功*\n\n\ud83d\udc64 用户名：`"
                  + this.escapeMarkdown(registeredUser.getEmbyUserName())
                  + "`\n\ud83d\udd10 登录密码：`"
                  + this.escapeMarkdown(task.getRawPassword())
                  + "`\n\n密码仅在本次私聊发送，请立即妥善保存。\n\n\ud83c\udfac 现在可以发送 `/request 片名` 搜索 TMDB 并提交求片。\n\ud83d\udca1 也可以使用 `@机器人 影片名` 内联搜索，体验更好；"
            );
         } catch (Exception var14) {
            log.error("Telegram 注册后自动绑定失败: telegramUserId={}, userId={}", task.getTelegramUserId(), registeredUser.getId(), var14);
            this.sendMessage(
               task.getChatId(),
               "✅ 账号已创建，但自动绑定失败，请使用 `/bind 用户名 密码` 手动绑定。\n\ud83d\udc64 用户名："
                  + this.escapeMarkdown(registeredUser.getEmbyUserName())
                  + "\n\ud83d\udd10 密码："
                  + this.escapeMarkdown(task.getRawPassword())
            );
            return;
         }
      } catch (BizException var15) {
         if (quotaReserved) {
            this.releaseTelegramRegisterQuota();
         }

         this.sendMessage(task.getChatId(), "❌ " + var15.getMessage());
         return;
      } catch (ApiException var16) {
         if (quotaReserved) {
            this.releaseTelegramRegisterQuota();
         }

         log.error("Telegram 注册创建 Emby 用户失败: telegramUserId={}", task.getTelegramUserId(), var16);
         this.sendMessage(task.getChatId(), "❌ Emby 服务器创建用户失败，请稍后再试或联系管理员。");
         return;
      } catch (Exception var17) {
         if (quotaReserved) {
            this.releaseTelegramRegisterQuota();
         }

         log.error("Telegram 注册失败: telegramUserId={}", task.getTelegramUserId(), var17);
         this.sendMessage(task.getChatId(), "❌ 注册失败，请稍后再试。");
         return;
      } finally {
         this.redisLockUtils.unlock(task.getLockKey(), task.getLockToken());
      }
   }

   private int resolveKkGiftRegisterDefaultDays() {
      LambdaQueryChainWrapper<SystemConfig> registerConfigQuery = this.systemConfigService.lambdaQuery();
      registerConfigQuery.eq(SystemConfig::getConfigKey, "telegram_bot_register_enabled");
      SystemConfig registerConfig = registerConfigQuery.one();
      if (registerConfig != null && StringUtils.hasText(registerConfig.getConfigValue())) {
         JSONObject config = this.parseTelegramRegisterConfig(registerConfig.getConfigValue());
         return Math.max(0, config.getIntValue("defaultDays"));
      } else {
         return 0;
      }
   }

   private boolean isTelegramBotRegistrationEnabled() {
      return this.configCacheLoaderUtils.getConfigValue("telegram_bot_register_enabled") != null;
   }

   private boolean tryReserveTelegramRegisterQuota() {
      for (int i = 0; i < 3; i++) {
         SystemConfig registerConfig = this.systemConfigService.lambdaQuery().eq(SystemConfig::getConfigKey, "telegram_bot_register_enabled").one();
         if (registerConfig == null || !Integer.valueOf(1).equals(registerConfig.getIsEnabled())) {
            return false;
         }

         String currentValue = registerConfig.getConfigValue();
         JSONObject currentConfig = this.parseTelegramRegisterConfig(currentValue);
         int maxCount = currentConfig.getIntValue("maxCount");
         int usedCount = currentConfig.getIntValue("usedCount");
         if (maxCount <= 0) {
            return false;
         }

         if (usedCount >= maxCount) {
            return false;
         }

         currentConfig.put("usedCount", Integer.valueOf(usedCount + 1));
         boolean updated = this.systemConfigService
            .lambdaUpdate()
            .set(SystemConfig::getConfigValue, currentConfig.toJSONString())
            .eq(SystemConfig::getId, registerConfig.getId())
            .eq(currentValue != null, SystemConfig::getConfigValue, currentValue)
            .isNull(currentValue == null, SystemConfig::getConfigValue)
            .update();
         if (updated) {
            this.configCacheLoaderUtils.refreshCache();
            return true;
         }
      }

      return false;
   }

   private void releaseTelegramRegisterQuota() {
      try {
         for (int i = 0; i < 3; i++) {
            SystemConfig registerConfig = this.systemConfigService.lambdaQuery().eq(SystemConfig::getConfigKey, "telegram_bot_register_enabled").one();
            if (registerConfig == null) {
               return;
            }

            String currentValue = registerConfig.getConfigValue();
            JSONObject currentConfig = this.parseTelegramRegisterConfig(currentValue);
            int usedCount = currentConfig.getIntValue("usedCount");
            if (usedCount <= 0) {
               return;
            }

            currentConfig.put("usedCount", Integer.valueOf(usedCount - 1));
            boolean updated = this.systemConfigService
               .lambdaUpdate()
               .set(SystemConfig::getConfigValue, currentConfig.toJSONString())
               .eq(SystemConfig::getId, registerConfig.getId())
               .eq(currentValue != null, SystemConfig::getConfigValue, currentValue)
               .isNull(currentValue == null, SystemConfig::getConfigValue)
               .update();
            if (updated) {
               this.configCacheLoaderUtils.refreshCache();
               return;
            }
         }

         log.warn("归还 Telegram 注册名额重试后仍未成功");
      } catch (Exception var7) {
         log.warn("归还 Telegram 注册名额失败", (Throwable)var7);
      }
   }

   private JSONObject parseTelegramRegisterConfig(String value) {
      JSONObject config = new JSONObject();
      config.put("defaultDays", Integer.valueOf(0));
      config.put("maxCount", Integer.valueOf(0));
      config.put("usedCount", Integer.valueOf(0));
      if (!StringUtils.hasText(value)) {
         return config;
      } else {
         String text = value.trim();
         if (text.startsWith("{")) {
            try {
               JSONObject parsed = JSONObject.parseObject(text);
               config.put("defaultDays", Integer.valueOf(this.parseNonNegativeInt(parsed.getString("defaultDays"))));
               config.put("maxCount", Integer.valueOf(this.parseNonNegativeInt(parsed.getString("maxCount"))));
               config.put("usedCount", Integer.valueOf(this.parseNonNegativeInt(parsed.getString("usedCount"))));
               return config;
            } catch (Exception var5) {
            }
         }

         config.put("defaultDays", Integer.valueOf(this.parseNonNegativeInt(text)));
         return config;
      }
   }

   private int parseNonNegativeInt(String value) {
      if (!StringUtils.hasText(value)) {
         return 0;
      } else {
         try {
            return Math.max(0, Integer.parseInt(value.trim()));
         } catch (NumberFormatException var3) {
            return 0;
         }
      }
   }

   private String buildTelegramRegisterChannelDetail(Message message) {
      String telegramUsername = message.getFrom().getUserName();
      if (StringUtils.hasText(telegramUsername)) {
         String normalizedUsername = telegramUsername.startsWith("@") ? telegramUsername : "@" + telegramUsername;
         return "Telegram开放注册(" + normalizedUsername + ")";
      } else {
         return "Telegram开放注册(" + message.getFrom().getId() + ")";
      }
   }

   private String buildTelegramCardChannelDetail(String prefix, Message message) {
      String telegramUsername = message.getFrom().getUserName();
      if (StringUtils.hasText(telegramUsername)) {
         String normalizedUsername = telegramUsername.startsWith("@") ? telegramUsername : "@" + telegramUsername;
         return prefix + "(" + normalizedUsername + ")";
      } else {
         return prefix + "(" + message.getFrom().getId() + ")";
      }
   }

   private void appendCreateAccountAccessLines(StringBuilder builder, InsertUserResponse response) {
      builder.append("\ud83d\udc64 账号：").append(this.escapeMarkdown(this.valueOrDash(response.getEmbyUserName()))).append("\n");
      builder.append("\ud83d\udd10 密码：").append(this.escapeMarkdown(this.valueOrDash(response.getEmbyUserPassword()))).append("\n");
      this.appendWhitelistUserLine(builder, response.getHostLineType(), true);
      builder.append("⏰ 到期：").append(this.formatExpirationForHostLine(response.getExpirationDate(), response.getHostLineType())).append("\n");
      builder.append("\ud83d\udce1 线路类型：").append(this.escapeMarkdown(this.valueOrDash(response.getHostLineTypeName()))).append("\n");
      builder.append("\ud83c\udf10 域名/IP：").append(this.escapeMarkdown(this.valueOrDash(response.getHost()))).append("\n");
      builder.append("\ud83d\udd0c 端口：").append(this.escapeMarkdown(this.valueOrDash(response.getPort()))).append("\n");
      builder.append("\ud83d\udd17 协议：").append(this.escapeMarkdown(this.valueOrDash(response.getProtocol())));
   }

   private String valueOrDash(Object value) {
      if (value == null) {
         return "-";
      } else {
         String text = String.valueOf(value);
         return StringUtils.hasText(text) ? text : "-";
      }
   }

   private void appendWhitelistUserLine(StringBuilder builder, Integer hostLineType, boolean withEmoji) {
      if (this.isWhitelistHostLineType(hostLineType)) {
         builder.append(withEmoji ? "\ud83c\udff7️ 用户类型：白名单用户\n" : "用户类型：白名单用户\n");
      }
   }

   private String formatExpirationForHostLine(Date expirationDate, Integer hostLineType) {
      return this.isWhitelistHostLineType(hostLineType) ? "永久" : this.formatDateTime(expirationDate);
   }

   private boolean isWhitelistHostLineType(Integer hostLineType) {
      return HostLineTypeEnum.normalize(hostLineType) == HostLineTypeEnum.WHITELIST.getCode();
   }

   private void appendServerNameLine(StringBuilder builder, Long userId) {
      String serverName = this.resolveServerNameByUserId(userId);
      if (StringUtils.hasText(serverName)) {
         builder.append("\ud83d\udda5️ 服务器：").append(this.escapeMarkdown(serverName));
      }
   }

   private String resolveServerNameByUserId(Long userId) {
      if (userId == null) {
         return null;
      } else {
         EmbyUser user = this.embyUserService.getById(userId);
         if (user != null && user.getEmbyInfoId() != null) {
            EmbyInfo server = this.embyInfoService.getById(user.getEmbyInfoId());
            if (server == null) {
               return null;
            } else {
               return StringUtils.hasText(server.getServerName()) ? server.getServerName() : "服务器 " + server.getId();
            }
         } else {
            return null;
         }
      }
   }

   private TelegramBindingActionResponse bindCreatedCardUser(InsertUserResponse response, long telegramUserId, Message message) {
      try {
         return this.telegramAuthService.bindExistingUser(response.getId(), telegramUserId, message.getFrom().getUserName(), null, "BOT_CARD");
      } catch (Exception var6) {
         log.error("Telegram 卡密开号后自动绑定失败: telegramUserId={}, userId={}", telegramUserId, response == null ? null : response.getId(), var6);
         return null;
      }
   }

   private void sendBindRequiredMessage(long chatId) {
      this.sendMessage(
         chatId,
         "\ud83d\udd17 请先绑定 Emby 账号后再求片：\n1. \ud83c\udf10 网页个人资料中点击绑定 Telegram\n2. \ud83e\udd16 或私聊发送 `/bind 用户名 密码`\n\ud83c\udd95 如果还没有账号，管理员开启 Telegram 私聊注册后可发送 `/register 用户名 密码`。"
      );
   }

   private String escapeMarkdown(String value) {
      return value == null ? "" : value.replace("\\", "\\\\").replace("_", "\\_").replace("*", "\\*").replace("`", "\\`").replace("[", "\\[");
   }

   private String telegramBizMessage(BizException exception) {
      return exception instanceof TelegramGroupMembershipRequiredException membershipRequired
         ? membershipRequired.getTelegramMarkdownMessage()
         : exception.getMessage();
   }

   private String publicReviewId(TelegramBindingActionResponse result) {
      if (result == null) {
         return "";
      } else {
         return StringUtils.hasText(result.getReviewUuid()) ? result.getReviewUuid() : String.valueOf(result.getReviewId());
      }
   }

   private void handleLoginCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long telegramUserId = message.getFrom().getId();
      String sessionId = argument.substring("login_".length()).trim();
      if (!StringUtils.hasText(sessionId)) {
         this.sendMessage(chatId, "❌ 无效的登录链接，缺少会话ID。");
      } else {
         EmbyUserCustomResponse result = this.telegramAuthService.completeLogin(sessionId, telegramUserId);
         if (result != null) {
            String successMessage = "✅ *登录成功！*\n\n\ud83c\udf89 欢迎回来，" + result.getEmbyUserName() + "！\n\n请返回网页完成登录。";
            this.sendMessage(chatId, successMessage);
         } else {
            UserOauthBinding binding = this.telegramBindingManager.findBindingByTelegramId(telegramUserId);
            if (binding == null) {
               this.sendMessage(chatId, "❌ 登录失败：您的 Telegram 账户尚未绑定 Emby 账户。\n\n\ud83d\udd17 请先在网页端登录后绑定 Telegram。");
            } else {
               this.sendMessage(chatId, "❌ 登录失败：登录链接已过期或无效。\n\n\ud83d\udd01 请返回网页重新点击 Telegram 登录。");
            }
         }
      }
   }

   private void handleResetPasswordCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         DataQueryBot.PendingUserEdit pending = this.pendingUserEdits.get(userId);
         if (pending == null) {
            this.sendMessage(chatId, "请先使用 /edituser 搜索并选择用户。");
         } else {
            String newPassword = argument.trim();
            if (!StringUtils.hasText(newPassword)) {
               this.sendMessage(chatId, "用法：`/resetpassword <新密码>`");
            } else {
               try {
                  this.embyUserService.resetPasswordByBot(pending.getUserId(), newPassword, this.isBotOwner(userId));
                  this.sendMessage(chatId, "密码已重置。");
               } catch (BizException var10) {
                  this.sendMessage(chatId, "重置失败：" + var10.getMessage());
               } catch (Exception var11) {
                  log.error("重置密码失败", (Throwable)var11);
                  this.sendMessage(chatId, "重置失败，请稍后再试。");
               }
            }
         }
      }
   }

   private void handleEnableUserCommand(Message message) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         DataQueryBot.PendingUserEdit pending = this.pendingUserEdits.get(userId);
         if (pending == null) {
            this.sendMessage(chatId, "请先使用 `/edituser` 搜索并选择用户。");
         } else {
            EmbyUser user = this.embyUserService.getById(Long.valueOf(pending.getUserId()));
            if (user == null) {
               this.sendMessage(chatId, "❌ 用户不存在。");
               this.pendingUserEdits.remove(userId);
            } else if (user.getUserStatus() != null && user.getUserStatus() == 0) {
               this.sendMessage(chatId, "⚠️ 该用户已处于启用状态。");
            } else {
               Date now = new Date();
               boolean isExpired = user.getExpirationDate() != null && user.getExpirationDate().before(now);
               if (isExpired) {
                  this.sendMessage(chatId, "⚠️ 该用户已过期，请先设置新的过期时间：\n\n使用 `/setexpiry <到期时间>`\n时间格式：`yyyy-MM-dd HH:mm:ss`\n示例：`/setexpiry 2025-12-31 23:59:59`");
               } else {
                  try {
                     this.embyUserService.enableUserByBot(pending.getUserId(), this.isBotOwner(userId));
                     this.sendMessage(chatId, "✅ 用户 `" + user.getEmbyUserName() + "` 已启用。");
                  } catch (BizException var11) {
                     this.sendMessage(chatId, "❌ 启用失败：" + var11.getMessage());
                  } catch (Exception var12) {
                     log.error("启用用户失败", (Throwable)var12);
                     this.sendMessage(chatId, "❌ 启用失败，请稍后再试。");
                  }
               }
            }
         }
      }
   }

   private void handleDisableUserCommand(Message message) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         DataQueryBot.PendingUserEdit pending = this.pendingUserEdits.get(userId);
         if (pending == null) {
            this.sendMessage(chatId, "请先使用 `/edituser` 搜索并选择用户。");
         } else {
            EmbyUser user = this.embyUserService.getById(Long.valueOf(pending.getUserId()));
            if (user == null) {
               this.sendMessage(chatId, "❌ 用户不存在。");
               this.pendingUserEdits.remove(userId);
            } else if (user.getUserStatus() != null && user.getUserStatus() == 1) {
               this.sendMessage(chatId, "⚠️ 该用户已处于禁用状态。");
            } else {
               try {
                  this.embyUserService.disableUserByBot(pending.getUserId(), this.isBotOwner(userId));
                  this.sendMessage(chatId, "✅ 用户 `" + user.getEmbyUserName() + "` 已禁用。");
               } catch (BizException var9) {
                  this.sendMessage(chatId, "❌ 禁用失败：" + var9.getMessage());
               } catch (Exception var10) {
                  log.error("禁用用户失败", (Throwable)var10);
                  this.sendMessage(chatId, "❌ 禁用失败，请稍后再试。");
               }
            }
         }
      }
   }

   private void handleSetExpiryCommand(Message message, String argument) {
      long chatId = message.getChatId();
      long userId = message.getFrom().getId();
      if (this.hasAdminPermission(message)) {
         DataQueryBot.PendingUserEdit pending = this.pendingUserEdits.get(userId);
         if (pending == null) {
            this.sendMessage(chatId, "请先使用 `/edituser` 搜索并选择用户。");
         } else {
            EmbyUser user = this.embyUserService.getById(Long.valueOf(pending.getUserId()));
            if (user == null) {
               this.sendMessage(chatId, "❌ 用户不存在。");
               this.pendingUserEdits.remove(userId);
            } else {
               String[] args = argument.split("\\s+", 2);
               if (args.length >= 2 && StringUtils.hasText(args[0]) && StringUtils.hasText(args[1])) {
                  String dateTimeStr = args[0] + " " + args[1];
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                  LocalDateTime dateTime;
                  try {
                     dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                  } catch (DateTimeParseException var17) {
                     this.sendMessage(chatId, "❌ 日期时间格式错误！\n正确格式：`yyyy-MM-dd HH:mm:ss`\n示例：`2025-12-31 23:59:59`");
                     return;
                  }

                  if (dateTime.isBefore(LocalDateTime.now())) {
                     this.sendMessage(chatId, "❌ 到期时间必须是未来时间！");
                  } else {
                     try {
                        EmbyUserUpdateData updateData = new EmbyUserUpdateData();
                        updateData.setId(pending.getUserId());
                        updateData.setExpirationDate(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
                        this.embyUserService.updateUserDataByBot(updateData, this.isBotOwner(userId));
                        this.embyUserService.enableUserByBot(pending.getUserId(), this.isBotOwner(userId));
                        String formattedDate = dateTime.format(formatter);
                        this.sendMessage(chatId, "✅ 用户 `" + user.getEmbyUserName() + "` 已启用。\n⏰ 新到期时间：`" + formattedDate + "`");
                     } catch (BizException var15) {
                        this.sendMessage(chatId, "❌ 操作失败：" + var15.getMessage());
                     } catch (Exception var16) {
                        log.error("设置过期时间并启用用户失败", (Throwable)var16);
                        this.sendMessage(chatId, "❌ 操作失败，请稍后再试。");
                     }
                  }
               } else {
                  this.sendMessage(
                     chatId, "\ud83d\udcdd *用法：*\n`/setexpiry <到期时间>`\n\n⏰ *时间格式：* `yyyy-MM-dd HH:mm:ss`\n\ud83d\udccc *示例：*\n`/setexpiry 2025-12-31 23:59:59`"
                  );
               }
            }
         }
      }
   }

   private void sendOrEditResultListPage(long chatId, Integer messageId, int page) {
      List<TmdbResponse.Result> results = this.getSearchResults(chatId);
      if (results != null && !results.isEmpty()) {
         List<InlineKeyboardRow> keyboardRows = new ArrayList<>();
         int startIndex = page * 5;
         int endIndex = Math.min(startIndex + 5, results.size());

         for (int i = startIndex; i < endIndex; i++) {
            TmdbResponse.Result result = results.get(i);
            String title = result.getTitle() != null ? result.getTitle() : result.getName();
            String releaseDate = result.getReleaseDate() != null ? result.getReleaseDate() : result.getFirstAirDate();
            String year = releaseDate != null && releaseDate.length() >= 4 ? " (" + releaseDate.substring(0, 4) + ")" : "";
            String mediaTypeEmoji = this.formatMediaTypeToEmoji(result.getMediaType());
            InlineKeyboardButton button = InlineKeyboardButton.builder().text(mediaTypeEmoji + " " + title + year).callbackData("select:" + i).build();
            keyboardRows.add(new InlineKeyboardRow(button));
         }

         List<InlineKeyboardButton> navButtons = new ArrayList<>();
         if (page > 0) {
            navButtons.add(InlineKeyboardButton.builder().text("⬅️ 上一页").callbackData("page:" + (page - 1)).build());
         }

         navButtons.add(InlineKeyboardButton.builder().text("\ud83d\udcc4 第 " + (page + 1) + " 页").callbackData("noop").build());
         if (endIndex < results.size()) {
            navButtons.add(InlineKeyboardButton.builder().text("➡️ 下一页").callbackData("page:" + (page + 1)).build());
         }

         if (!navButtons.isEmpty()) {
            keyboardRows.add(new InlineKeyboardRow(navButtons));
         }

         InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(keyboardRows).build();
         String messageText = this.foamPanelTitle("求片中心") + "\ud83d\udd0d 为你找到 *" + results.size() + "* 个相关结果，请选择：";

         try {
            if (messageId == null) {
               SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(messageText).parseMode("Markdown").replyMarkup(keyboard).build();
               Message sentMessage = this.telegramClient.execute(sendMessage);
               this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
            } else {
               try {
                  EditMessageText editMessage = EditMessageText.builder()
                     .chatId(chatId)
                     .messageId(messageId)
                     .text(messageText)
                     .parseMode("Markdown")
                     .replyMarkup(keyboard)
                     .build();
                  this.telegramClient.execute(editMessage);
               } catch (TelegramApiException var17) {
                  if (var17.getMessage() != null && var17.getMessage().contains("message is not modified")) {
                     log.debug("消息内容无变化，跳过编辑");
                     return;
                  }

                  log.warn("编辑消息失败，尝试删除并重新发送: {}", var17.getMessage());

                  try {
                     DeleteMessage deleteMessage = DeleteMessage.builder().chatId(chatId).messageId(messageId).build();
                     this.telegramClient.execute(deleteMessage);
                  } catch (TelegramApiException var16) {
                     log.warn("删除消息失败: {}", var16.getMessage());
                  }

                  SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(messageText).parseMode("Markdown").replyMarkup(keyboard).build();
                  Message sentMessage = this.telegramClient.execute(sendMessage);
                  this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
               }
            }
         } catch (TelegramApiException var18) {
            log.error("发送或编辑列表页失败", (Throwable)var18);
         }
      } else {
         this.sendMessage(chatId, "搜索结果已过期或无效，请重新搜索。");
      }
   }

   private void sendOrEditRequestCard(long chatId, Integer messageId, int index) {
      List<TmdbResponse.Result> results = this.getSearchResults(chatId);
      if (results == null || results.isEmpty()) {
         this.sendMessage(chatId, "搜索结果已过期，请重新使用 `/request 片名` 搜索。");
      } else if (index >= 0 && index < results.size()) {
         TmdbResponse.Result result = results.get(index);
         String caption = this.buildRequestCardCaption(result, index, results.size());
         InlineKeyboardMarkup keyboard = this.buildRequestCardKeyboard(index, results.size(), result);
         String posterUrl = this.buildTmdbImageUrl(result.getPosterPath());
         if (!StringUtils.hasText(posterUrl)) {
            this.sendOrEditRequestTextCard(chatId, messageId, caption, keyboard);
         } else {
            try {
               if (messageId == null) {
                  this.sendRequestPhotoCard(chatId, posterUrl, caption, keyboard);
                  return;
               }

               InputMediaPhoto media = InputMediaPhoto.builder().media(posterUrl).caption(caption).build();
               EditMessageMedia editMedia = EditMessageMedia.builder().chatId(chatId).messageId(messageId).media(media).replyMarkup(keyboard).build();
               this.telegramClient.execute(editMedia);
            } catch (TelegramApiException var13) {
               log.warn("编辑 Telegram 求片卡片失败，尝试删除后重发: {}", var13.getMessage());
               this.deleteMessageSilently(chatId, messageId);

               try {
                  this.sendRequestPhotoCard(chatId, posterUrl, caption, keyboard);
               } catch (TelegramApiException var12) {
                  log.error("发送 Telegram 求片卡片失败", (Throwable)var12);
                  this.sendOrEditRequestTextCard(chatId, null, caption, keyboard);
               }
            }
         }
      } else {
         this.sendMessage(chatId, "求片选择无效，请重新搜索。");
      }
   }

   private void sendRequestPhotoCard(long chatId, String posterUrl, String caption, InlineKeyboardMarkup keyboard) throws TelegramApiException {
      SendPhoto sendPhoto = SendPhoto.builder().chatId(chatId).photo(new InputFile(posterUrl)).caption(caption).replyMarkup(keyboard).build();
      this.telegramClient.execute(sendPhoto);
   }

   private void sendOrEditRequestTextCard(long chatId, Integer messageId, String caption, InlineKeyboardMarkup keyboard) {
      try {
         if (messageId == null) {
            SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(caption).replyMarkup(keyboard).build();
            Message sentMessage = this.telegramClient.execute(sendMessage);
            this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
            return;
         }

         this.telegramClient.execute(EditMessageText.builder().chatId(chatId).messageId(messageId).text(caption).replyMarkup(keyboard).build());
      } catch (TelegramApiException var8) {
         log.error("发送或编辑 Telegram 求片文本卡片失败", (Throwable)var8);
         this.sendMessage(chatId, "显示求片结果失败，请重新搜索。");
      }
   }

   private InlineKeyboardMarkup buildRequestCardKeyboard(int index, int total, TmdbResponse.Result result) {
      List<InlineKeyboardRow> rows = new ArrayList<>();
      List<InlineKeyboardButton> navButtons = new ArrayList<>();
      if (index > 0) {
         navButtons.add(InlineKeyboardButton.builder().text("⬅️ 上一部").callbackData("request_page:" + (index - 1)).build());
      }

      navButtons.add(InlineKeyboardButton.builder().text("\ud83d\udcc4 " + (index + 1) + " / " + total).callbackData("noop").build());
      if (index < total - 1) {
         navButtons.add(InlineKeyboardButton.builder().text("➡️ 下一部").callbackData("request_page:" + (index + 1)).build());
      }

      rows.add(new InlineKeyboardRow(navButtons));
      InlineKeyboardButton tmdbButton = InlineKeyboardButton.builder().text("\ud83c\udf5f TMDB").url(this.buildTmdbPageUrl(result)).build();
      InlineKeyboardButton submitButton = InlineKeyboardButton.builder().text("\ud83c\udfac 提交求片").callbackData("submit_request:" + index).build();
      rows.add(new InlineKeyboardRow(tmdbButton, submitButton));
      return InlineKeyboardMarkup.builder().keyboard(rows).build();
   }

   private String buildRequestCardCaption(TmdbResponse.Result result, int index, int total) {
      String title = this.resolveTmdbTitle(result);
      String releaseDate = this.resolveTmdbReleaseDate(result);
      String year = StringUtils.hasText(releaseDate) && releaseDate.length() >= 4 ? " (" + releaseDate.substring(0, 4) + ")" : "";
      String overview = StringUtils.hasText(result.getOverview()) ? result.getOverview() : "暂无简介。";
      String rating = result.getVoteAverage() == null ? "0.0" : String.format("%.1f", result.getVoteAverage());
      String caption = this.formatMediaTypeToEmoji(result.getMediaType())
         + " "
         + title
         + year
         + "\n\n简介："
         + overview
         + "\n\n类型："
         + this.formatMediaTypeText(result.getMediaType())
         + "    评分："
         + rating
         + "/10\n结果："
         + (index + 1)
         + " / "
         + total;
      return this.abbreviate(caption, 980);
   }

   private String formatMediaTypeText(String mediaType) {
      if ("tv".equals(mediaType)) {
         return "剧集";
      } else {
         return "movie".equals(mediaType) ? "电影" : "未知";
      }
   }

   private String abbreviate(String value, int maxLength) {
      return value != null && value.length() > maxLength ? value.substring(0, Math.max(0, maxLength - 3)) + "..." : value;
   }

   private void showItemDetails(long chatId, Integer messageId, int index, int resourcePage) {
      List<TmdbResponse.Result> results = this.getSearchResults(chatId);
      if (results != null && index >= 0 && index < results.size()) {
         TmdbResponse.Result result = results.get(index);
         String caption = this.buildCaption(result);
         MovieListResponse resourceResponse = null;
         int totalResources = 0;
         int totalPages = 0;

         String resourceInfo;
         try {
            if (resourcePage == 0) {
               resourceResponse = this.nullbrService.select(String.valueOf(result.getId()), result.getMediaType());
               this.saveResourceCache(chatId, resourceResponse);
            } else {
               resourceResponse = this.getResourceCache(chatId);
            }

            if (resourceResponse != null && resourceResponse.getMovieList115DTOList() != null) {
               totalResources = resourceResponse.getMovieList115DTOList().size();
               totalPages = (int)Math.ceil((double)totalResources / 5.0);
               resourceInfo = this.formatNullbrResponsePaged(resourceResponse, resourcePage, 5);
            } else {
               resourceInfo = "❌ *未找到相关资源*";
            }
         } catch (Exception var22) {
            log.error("在详情页获取资源时出错: {}", var22.getMessage());
            resourceInfo = "❌ 获取资源信息失败：" + var22.getMessage();
         }

         caption = caption + "\n\n" + resourceInfo;
         InlineKeyboardMarkup keyboard = this.buildDetailViewKeyboard(index, resourcePage, totalPages);
         int MAX_CAPTION_LENGTH = 1024;
         if (caption.length() > 1024) {
            caption = caption.substring(0, 1014) + "...";
         }

         try {
            InputMediaPhoto media = InputMediaPhoto.builder().media(this.imageUrl + result.getPosterPath()).caption(caption).parseMode("Markdown").build();
            EditMessageMedia editMedia = EditMessageMedia.builder().chatId(chatId).messageId(messageId).media(media).replyMarkup(keyboard).build();
            this.telegramClient.execute(editMedia);
         } catch (TelegramApiException var21) {
            String errorMessage = var21.getMessage() != null ? var21.getMessage().toLowerCase() : "";
            if (errorMessage.contains("caption") && errorMessage.contains("long")) {
               log.warn("Caption 仍然过长，尝试发送不带资源的详情。");
               String shortCaption = this.buildCaption(result) + "\n\n⚠️ *资源信息过长，请使用分页查看*";
               if (shortCaption.length() > 1024) {
                  shortCaption = shortCaption.substring(0, 1014) + "...";
               }

               try {
                  InputMediaPhoto mediax = InputMediaPhoto.builder()
                     .media(this.imageUrl + result.getPosterPath())
                     .caption(shortCaption)
                     .parseMode("Markdown")
                     .build();
                  EditMessageMedia editMediax = EditMessageMedia.builder().chatId(chatId).messageId(messageId).media(mediax).replyMarkup(keyboard).build();
                  this.telegramClient.execute(editMediax);
               } catch (TelegramApiException var20) {
                  log.error("尝试发送短Caption时再次失败。", (Throwable)var20);
                  this.sendMessage(chatId, "显示详情失败：内容过长。");
               }
            } else {
               log.error("编辑消息以显示详情时失败。", (Throwable)var21);
               this.sendMessage(chatId, "显示详情失败：" + var21.getMessage());
            }
         }
      } else {
         this.sendMessage(chatId, "无法获取该条目的详细信息，请重试。");
      }
   }

   private void handleRequestSubmit(CallbackQuery callbackQuery, int index) {
      long chatId = callbackQuery.getMessage().getChatId();
      long telegramUserId = callbackQuery.getFrom().getId();
      if (chatId != telegramUserId) {
         this.sendMessage(chatId, "\ud83d\udd12 请私聊机器人提交求片，避免泄露账号绑定信息。");
      } else if (!this.tryAcquireRateLimit("request:" + telegramUserId, 3L, 60L)) {
         this.sendMessage(chatId, "⏳ 求片提交太频繁了，请稍后再试。");
      } else if (!this.tryAcquireRequestSubmitLock(telegramUserId)) {
         this.sendMessage(chatId, "⏳ 正在处理上一次求片提交，请勿重复点击。");
      } else {
         EmbyUser embyUser = this.telegramAuthService.findBoundUser(telegramUserId);
         if (embyUser == null) {
            this.sendMessage(chatId, "\ud83d\udd17 请先绑定 Emby 账号：\n1. \ud83c\udf10 网页个人资料中点击绑定 Telegram\n2. \ud83e\udd16 或私聊发送 `/bind 用户名 密码`");
         } else {
            List<TmdbResponse.Result> results = this.getSearchResults(chatId);
            if (results != null && index >= 0 && index < results.size()) {
               TmdbResponse.Result result = results.get(index);

               try {
                  RequestListSave requestListSave = this.buildRequestListSave(result);
                  TelegramRequestSubmitService.TelegramRequestSubmitResult submitResult = this.telegramRequestSubmitService
                     .submit(embyUser.getId(), telegramUserId, requestListSave);
                  String title = this.resolveTmdbTitle(result);
                  this.sendMessage(chatId, this.buildRequestSubmitSuccessMessage(title, submitResult));
               } catch (BizException var13) {
                  this.sendMessage(chatId, "❌ " + var13.getMessage());
               } catch (Exception var14) {
                  log.error("Telegram 提交求片失败: telegramUserId={}, index={}", telegramUserId, index, var14);
                  this.sendMessage(chatId, "❌ 提交求片失败，请稍后再试。");
               }
            } else {
               this.sendMessage(chatId, "⏰ 搜索结果已过期，请重新使用 `/request 片名` 搜索。");
            }
         }
      }
   }

   private void handleInlineRequestSubmit(CallbackQuery callbackQuery, String mediaType, String tmdbId) {
      long telegramUserId = callbackQuery.getFrom().getId();
      Long callbackChatId = callbackQuery.getMessage() == null ? null : callbackQuery.getMessage().getChatId();
      long responseChatId = callbackChatId == null ? telegramUserId : callbackChatId;
      if (callbackChatId != null && callbackChatId != telegramUserId) {
         this.sendMessage(responseChatId, "\ud83d\udd12 请私聊机器人提交求片，群聊里不允许提交。");
      } else if (!this.tryAcquireRateLimit("request:" + telegramUserId, 3L, 60L)) {
         this.sendMessage(responseChatId, "⏳ 求片提交太频繁了，请稍后再试。");
      } else if (!this.tryAcquireRequestSubmitLock(telegramUserId)) {
         this.sendMessage(responseChatId, "⏳ 正在处理上一次求片提交，请勿重复点击。");
      } else {
         EmbyUser embyUser = this.telegramAuthService.findBoundUser(telegramUserId);
         if (embyUser == null) {
            this.sendBindRequiredMessage(responseChatId);
         } else {
            TmdbResponse.Result result = this.getInlineResult(mediaType + ":" + tmdbId);
            if (result == null) {
               this.sendMessage(responseChatId, "⏰ 搜索结果已过期，请重新 `@机器人 片名` 搜索。");
            } else {
               try {
                  RequestListSave requestListSave = this.buildRequestListSave(result);
                  TelegramRequestSubmitService.TelegramRequestSubmitResult submitResult = this.telegramRequestSubmitService
                     .submit(embyUser.getId(), telegramUserId, requestListSave);
                  this.sendMessage(responseChatId, this.buildRequestSubmitSuccessMessage(this.resolveTmdbTitle(result), submitResult));
               } catch (BizException var13) {
                  this.sendMessage(responseChatId, "❌ " + var13.getMessage());
               } catch (Exception var14) {
                  log.error("Telegram inline 提交求片失败: telegramUserId={}, mediaType={}, tmdbId={}", telegramUserId, mediaType, tmdbId, var14);
                  this.sendMessage(responseChatId, "❌ 提交求片失败，请稍后再试。");
               }
            }
         }
      }
   }

   private RequestListSave buildRequestListSave(TmdbResponse.Result result) {
      RequestListSave save = new RequestListSave();
      String mediaType = "tv".equals(result.getMediaType()) ? "tv" : "movie";
      save.setName(this.resolveTmdbTitle(result));
      save.setOriginalName(this.resolveTmdbOriginalTitle(result));
      save.setType(mediaType);
      save.setScore(result.getVoteAverage() == null ? "0" : String.format("%.1f", result.getVoteAverage()));
      save.setOverview(result.getOverview());
      save.setReleaseDate(this.parseTmdbDate(this.resolveTmdbReleaseDate(result)));
      save.setImageUrl(this.buildTmdbImageUrl(result.getPosterPath()));
      save.setBackdropPath(this.buildTmdbImageUrl(result.getBackdropPath()));
      save.setTmdbUrl("https://www.themoviedb.org/" + mediaType + "/" + result.getId());
      save.setTmdbId(result.getId());
      if ("tv".equals(mediaType)) {
         save.setParentTmdbId(result.getId());
      }

      return save;
   }

   private String resolveTmdbTitle(TmdbResponse.Result result) {
      String title = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
      return StringUtils.hasText(title) ? title : "未知标题";
   }

   private String buildRequestSubmitSuccessMessage(String title, TelegramRequestSubmitService.TelegramRequestSubmitResult submitResult) {
      StringBuilder message = new StringBuilder("✅ 求片已提交：`").append(title).append("`\n");
      if (submitResult != null && submitResult.getChargeResult() != null) {
         if (submitResult.getChargeResult().isPointsEnabled() && submitResult.getChargeResult().getPointsCost() > 0) {
            message.append("\ud83d\udc8e 本次消耗 ")
               .append(submitResult.getChargeResult().getPointsCost())
               .append(" 积分，当前余额 ")
               .append(submitResult.getChargeResult().getBalanceAfter())
               .append("。\n");
         } else if (submitResult.getChargeResult().isPointsEnabled() && submitResult.getChargeResult().getDailyFreeCount() > 0) {
            message.append("\ud83c\udf81 本次使用今日免费次数 ")
               .append(submitResult.getChargeResult().getTodayUsedAfter())
               .append("/")
               .append(submitResult.getChargeResult().getDailyFreeCount())
               .append("。\n");
         } else if (submitResult.getChargeResult().isPointsEnabled()) {
            message.append("\ud83c\udf81 本次无需消耗积分。\n");
         } else if (submitResult.getRemainingRequestPackagesCount() != null) {
            message.append("\ud83c\udfab 本次使用网页求片次数，剩余 ").append(submitResult.getRemainingRequestPackagesCount()).append(" 次。\n");
         } else {
            message.append("\ud83c\udfab 本次使用网页求片次数。\n");
         }
      }

      message.append("\ud83d\udccc 请在网页求片中心查看进度。");
      return message.toString();
   }

   private String resolveTmdbOriginalTitle(TmdbResponse.Result result) {
      String title = StringUtils.hasText(result.getOriginalTitle()) ? result.getOriginalTitle() : result.getOriginalName();
      return StringUtils.hasText(title) ? title : this.resolveTmdbTitle(result);
   }

   private String resolveTmdbReleaseDate(TmdbResponse.Result result) {
      return StringUtils.hasText(result.getReleaseDate()) ? result.getReleaseDate() : result.getFirstAirDate();
   }

   private Date parseTmdbDate(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         try {
            return Date.from(LocalDate.parse(value).atStartOfDay(ZoneId.systemDefault()).toInstant());
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private String buildTmdbImageUrl(String path) {
      if (!StringUtils.hasText(path)) {
         return null;
      } else {
         return !path.startsWith("http://") && !path.startsWith("https://") ? this.imageUrl + path : path;
      }
   }

   private String formatNullbrResponsePaged(MovieListResponse response, int page, int pageSize) {
      if (response != null && response.getMovieList115DTOList() != null && !response.getMovieList115DTOList().isEmpty()) {
         List<MovieListResponse.MovieList115DTO> allItems = response.getMovieList115DTOList();
         int totalItems = allItems.size();
         int totalPages = (int)Math.ceil((double)totalItems / (double)pageSize);
         int startIndex = page * pageSize;
         int endIndex = Math.min(startIndex + pageSize, totalItems);
         if (startIndex >= totalItems) {
            return "❌ *没有更多资源了*";
         } else {
            StringBuilder sb = new StringBuilder();
            sb.append("\ud83d\udd17 *资源列表* (").append(page + 1).append("/").append(totalPages).append("):\n\n");

            for (int i = startIndex; i < endIndex; i++) {
               MovieListResponse.MovieList115DTO item = allItems.get(i);
               String cleanTitle = this.extractMovieTitle(item.getTitle());
               String cloudProvider = this.extractCloudProvider(item.getShareLink());
               sb.append("\ud83d\udcc1 [").append(cleanTitle);
               if (!cloudProvider.isEmpty()) {
                  sb.append(" (").append(cloudProvider).append(")");
               }

               sb.append("](").append(item.getShareLink()).append(") ");
               if (StringUtils.hasText(item.getSize())) {
                  sb.append("   \ud83d\udcbe `").append(item.getSize()).append("`");
                  if (StringUtils.hasText(item.getResolution())) {
                     sb.append(" \ud83d\udcfa `").append(item.getResolution()).append("`");
                  }

                  sb.append("\n");
               }

               sb.append("\n");
            }

            return sb.toString();
         }
      } else {
         return "❌ *未找到相关资源*";
      }
   }

   private String extractCloudProvider(String shareLink) {
      if (shareLink != null && !shareLink.isEmpty()) {
         String link = shareLink.toLowerCase();
         if (link.contains("115.com") || link.contains("115cdn.com")) {
            return "115";
         } else if (link.contains("quark") || link.contains("夸克")) {
            return "夸克";
         } else if (link.contains("pan.baidu.com") || link.contains("百度")) {
            return "百度";
         } else if (link.contains("aliyundrive") || link.contains("alipan") || link.contains("阿里")) {
            return "阿里";
         } else if (link.contains("xunlei") || link.contains("迅雷")) {
            return "迅雷";
         } else if (link.contains("uc.cn") || link.contains("drive.uc")) {
            return "UC";
         } else if (link.contains("123pan") || link.contains("123云盘")) {
            return "123盘";
         } else if (link.contains("lanzou") || link.contains("蓝奏")) {
            return "蓝奏";
         } else if (link.contains("tianyi") || link.contains("天翼") || link.contains("189.cn")) {
            return "天翼";
         } else {
            return !link.contains("weiyun") && !link.contains("微云") ? "" : "微云";
         }
      } else {
         return "";
      }
   }

   private String extractMovieTitle(String filename) {
      if (filename != null && !filename.isEmpty()) {
         int firstBracket = filename.indexOf(91);
         int secondBracket = filename.indexOf(93);
         if (firstBracket >= 0 && secondBracket > firstBracket) {
            String bracketContent = filename.substring(firstBracket + 1, secondBracket);
            StringBuilder chineseTitle = new StringBuilder();

            for (char c : bracketContent.toCharArray()) {
               if (Character.toString(c).matches("[\\u4e00-\\u9fa5]")) {
                  chineseTitle.append(c);
               } else if (chineseTitle.length() > 0) {
                  break;
               }
            }

            if (chineseTitle.length() > 0) {
               return chineseTitle.toString();
            }

            String[] parts = bracketContent.split("[_\\[]");
            if (parts.length > 0 && !parts[0].isEmpty()) {
               return parts[0].replaceAll("\\d{4}$", "").trim();
            }
         }

         if (!filename.matches(".*[\\[\\]()_@#].*")) {
            return filename;
         } else {
            StringBuilder chineseTitle = new StringBuilder();

            for (char cx : filename.toCharArray()) {
               if (Character.toString(cx).matches("[\\u4e00-\\u9fa5]")) {
                  chineseTitle.append(cx);
               }
            }

            if (chineseTitle.length() >= 2) {
               return chineseTitle.toString();
            } else {
               return filename.length() > 30 ? filename.substring(0, 30) + "..." : filename;
            }
         }
      } else {
         return "未知资源";
      }
   }

   private InlineKeyboardMarkup buildDetailViewKeyboard(int index, int resourcePage, int totalResourcePages) {
      int currentPage = index / 5;
      List<InlineKeyboardRow> rows = new ArrayList<>();
      if (totalResourcePages > 1) {
         List<InlineKeyboardButton> pageButtons = new ArrayList<>();
         if (resourcePage > 0) {
            pageButtons.add(InlineKeyboardButton.builder().text("⬅️ 上页资源").callbackData("resource_page:" + index + ":" + (resourcePage - 1)).build());
         }

         pageButtons.add(InlineKeyboardButton.builder().text("\ud83d\udcc4 " + (resourcePage + 1) + "/" + totalResourcePages).callbackData("noop").build());
         if (resourcePage < totalResourcePages - 1) {
            pageButtons.add(InlineKeyboardButton.builder().text("➡️ 下页资源").callbackData("resource_page:" + index + ":" + (resourcePage + 1)).build());
         }

         rows.add(new InlineKeyboardRow(pageButtons));
      }

      InlineKeyboardButton requestButton = InlineKeyboardButton.builder().text("\ud83d\udcdd 提交求片").callbackData("submit_request:" + index).build();
      rows.add(new InlineKeyboardRow(requestButton));
      InlineKeyboardButton backButton = InlineKeyboardButton.builder().text("\ud83d\udd19 返回列表").callbackData("back_to_list:" + currentPage).build();
      rows.add(new InlineKeyboardRow(backButton));
      return InlineKeyboardMarkup.builder().keyboard(rows).build();
   }

   private String buildCaption(TmdbResponse.Result result) {
      String title = result.getTitle() != null ? result.getTitle() : result.getName();
      String releaseDate = result.getReleaseDate() != null ? result.getReleaseDate() : result.getFirstAirDate();
      String year = releaseDate != null && releaseDate.length() >= 4 ? " (" + releaseDate.substring(0, 4) + ")" : "";
      String mediaType = result.getMediaType() != null ? result.getMediaType().replace("tv", "电视剧").replace("movie", "电影") : "未知";
      double rating = result.getVoteAverage();
      String overview = result.getOverview() != null && !result.getOverview().isEmpty() ? result.getOverview() : "暂无简介。";
      String ratingStr = String.format("%.1f", rating);
      return String.format("\ud83c\udfac *%s%s*\n\n**类型**: %s\n\n⭐ **评分**: %s / 10\n\n**简介**:\n%s", title, year, mediaType, ratingStr, overview);
   }

   private void processCreateUserSelection(CallbackQuery callbackQuery, long serverId) {
      if (this.hasAdminPermission(callbackQuery)) {
         long userId = callbackQuery.getFrom().getId();
         DataQueryBot.PendingCreateUser pending = this.pendingCreateUsers.get(userId);
         long chatId = callbackQuery.getMessage().getChatId();
         if (pending == null) {
            this.sendMessage(chatId, "未找到待创建的用户信息，请重新使用 /createuser 命令。");
         } else {
            EmbyUserSave embyUserSave = new EmbyUserSave();
            embyUserSave.setEmbyUserName(pending.getUserName());
            embyUserSave.setDay(pending.getDay());
            embyUserSave.setRemarks(pending.getRemarks());
            embyUserSave.setEmbyInfoId(serverId);

            try {
               InsertUserResponse response = this.embyUserService.insertUser(embyUserSave);
               StringBuilder sb = new StringBuilder();
               sb.append("✅ 用户创建成功！\n");
               sb.append("用户名：").append(response.getEmbyUserName()).append("\n");
               sb.append("密码：").append(response.getEmbyUserPassword()).append("\n");
               sb.append("到期时间：").append(response.getExpirationDate());
               EmbyInfo server = this.embyInfoService.getById(Long.valueOf(serverId));
               if (server != null) {
                  sb.append("\n服务器：").append(this.buildServerLabel(server));
               }

               this.sendMessage(chatId, sb.toString());
            } catch (ApiException var17) {
               this.sendMessage(chatId, "创建用户失败：" + var17.getResponseBody());
            } catch (Exception var18) {
               log.error("创建用户失败", (Throwable)var18);
               this.sendMessage(chatId, "创建用户失败，请稍后再试，" + var18.getMessage());
            } finally {
               this.pendingCreateUsers.remove(userId);
            }
         }
      }
   }

   private void processCardBatchSelection(CallbackQuery callbackQuery, long serverId) {
      if (this.hasAdminPermission(callbackQuery)) {
         long userId = callbackQuery.getFrom().getId();
         DataQueryBot.PendingCardBatch pending = this.pendingCardBatches.get(userId);
         long chatId = callbackQuery.getMessage().getChatId();
         if (pending == null) {
            this.sendMessage(chatId, "未找到待生成的卡密信息，请重新使用 /generatecards 命令。");
         } else {
            try {
               List<String> cards = this.cardSecurityManagementService
                  .addCardSecurityManagementList(
                     pending.getCount(),
                     pending.getDay(),
                     serverId,
                     0,
                     null,
                     "Telegram " + this.telegramDisplayName(callbackQuery.getFrom()) + " (" + callbackQuery.getFrom().getId() + ")"
                  );
               StringBuilder sb = new StringBuilder();
               sb.append("✅ 卡密生成成功，共 ").append(cards.size()).append(" 张。\n");

               for (String card : cards) {
                  sb.append(card).append("\n");
               }

               this.sendMessage(chatId, sb.toString());
            } catch (BizException var17) {
               this.sendMessage(chatId, "生成卡密失败：" + var17.getMessage());
            } catch (Exception var18) {
               log.error("生成卡密失败", (Throwable)var18);
               this.sendMessage(chatId, "生成卡密失败，请稍后再试。");
            } finally {
               this.pendingCardBatches.remove(userId);
            }
         }
      }
   }

   private void handleUserSelection(CallbackQuery callbackQuery, long selectedUserId) {
      if (this.hasAdminPermission(callbackQuery)) {
         EmbyUser user = this.embyUserService.getById(Long.valueOf(selectedUserId));
         long operatorId = callbackQuery.getFrom().getId();
         long chatId = callbackQuery.getMessage().getChatId();
         if (user != null && this.isKkProtectedEmbyTarget(user)) {
            if (callbackQuery.getMessage() instanceof Message panelMessage) {
               this.editStartPanelMessage(panelMessage, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～", null);
            } else {
               this.sendMessage(chatId, "\ud83c\udf01 这位伙伴正被星光轻轻守护，暂时不开放查看或操作哦～");
            }
         } else if (user != null && this.canBotOperatorViewTarget(operatorId, user)) {
            this.pendingUserEdits.put(operatorId, new DataQueryBot.PendingUserEdit(operatorId, selectedUserId));
            if (callbackQuery.getMessage() instanceof Message panelMessage && panelMessage.isUserMessage()) {
               this.renderAdminUserPanel(operatorId, user, panelMessage, null);
               return;
            }

            this.sendAdminUserPanel(operatorId, user);
         } else {
            if (callbackQuery.getMessage() instanceof Message panelMessage && panelMessage.isUserMessage()) {
               this.editStartPanelMessage(
                  panelMessage,
                  this.foamPanelTitle("用户管理") + "❌ 未找到该用户，请重新搜索。",
                  this.keyboard(List.of(this.button("\ud83d\udee1️ 返回管理中心", "start_panel:admin")), 1)
               );
               return;
            }

            this.sendMessage(chatId, "未找到该用户，请重新搜索。");
         }
      }
   }

   private String buildServerLabel(EmbyInfo server) {
      if (server == null) {
         return "未知服务器";
      } else if (StringUtils.hasText(server.getServerName())) {
         return server.getServerName();
      } else {
         return server.getId() != null ? "服务器-" + server.getId() : "未知服务器";
      }
   }

   private List<EmbyInfo> loadAvailableServers() {
      return this.embyInfoService
         .lambdaQuery()
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .list();
   }

   private InlineKeyboardMarkup buildServerSelectionKeyboard(List<EmbyInfo> servers, String callbackPrefix) {
      List<InlineKeyboardRow> rows = new ArrayList<>();

      for (EmbyInfo server : servers) {
         InlineKeyboardButton button = InlineKeyboardButton.builder()
            .text("\ud83d\udda5️ " + this.buildServerLabel(server))
            .callbackData(callbackPrefix + server.getId())
            .build();
         rows.add(new InlineKeyboardRow(button));
      }

      return InlineKeyboardMarkup.builder().keyboard(rows).build();
   }

   private String formatExpirationDate(Date date) {
      if (date == null) {
         return " (无到期时间)";
      } else {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         String formattedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
         return " 到期:" + formattedDate;
      }
   }

   private String formatDateTime(Date date) {
      if (date == null) {
         return "未设置";
      } else {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
      }
   }

   private void processExtendBatchSelection(CallbackQuery callbackQuery, long serverId) {
      if (this.hasAdminPermission(callbackQuery)) {
         long userId = callbackQuery.getFrom().getId();
         DataQueryBot.PendingExtendBatch pending = this.pendingExtendBatches.get(userId);
         long chatId = callbackQuery.getMessage().getChatId();
         if (pending == null) {
            this.sendMessage(chatId, "未找到待延期的参数信息，请重新使用 /extendusers 命令。");
         } else {
            try {
               int updated = this.embyUserService.extendExpiredUser(serverId, pending.getExpiredRange(), pending.getExtensionDay());
               this.sendMessage(chatId, "✅ 已为 " + updated + " 位用户延期 " + pending.getExtensionDay() + " 天。");
            } catch (BizException var14) {
               this.sendMessage(chatId, "延期失败：" + var14.getMessage());
            } catch (Exception var15) {
               log.error("批量延期失败", (Throwable)var15);
               this.sendMessage(chatId, "批量延期失败，请稍后再试。");
            } finally {
               this.pendingExtendBatches.remove(userId);
            }
         }
      }
   }

   private String formatMediaTypeToEmoji(String rawType) {
      if (rawType == null) {
         return "❔";
      } else {
         switch (rawType) {
            case "movie":
               return "\ud83c\udfa5";
            case "tv":
               return "\ud83d\udcfa";
            default:
               return "❔";
         }
      }
   }

   private void sendMessage(long chatId, String text) {
      this.sendMessage(chatId, text, true);
   }

   private void sendPersistentMessage(long chatId, String text) {
      this.sendMessage(chatId, text, false);
   }

   private void sendMessage(long chatId, String text, boolean cleanupGroupMessage) {
      SendMessage message = SendMessage.builder().chatId(chatId).text(text).parseMode("Markdown").build();

      for (int attempt = 0; attempt <= 3; attempt++) {
         this.waitForTelegramSendMessagePermit();

         try {
            Message sentMessage = this.telegramClient.execute(message);
            if (cleanupGroupMessage) {
               this.scheduleGroupMessageCleanup(chatId, sentMessage == null ? null : sentMessage.getMessageId());
            }

            return;
         } catch (TelegramApiException var9) {
            Integer retryAfterSeconds = this.resolveTelegramRetryAfterSeconds(var9);
            if (retryAfterSeconds == null || attempt >= 3) {
               log.error("发送消息失败: chatId={}, attempt={}", chatId, attempt + 1, var9);
               return;
            }

            log.warn("Telegram 发消息触发限流，等待 {} 秒后重试: chatId={}, attempt={}", retryAfterSeconds, chatId, attempt + 1);
            this.sleepBeforeTelegramRetry(retryAfterSeconds);
         }
      }
   }

   private String telegramUserMention(org.telegram.telegrambots.meta.api.objects.User user) {
      if (user != null && user.getId() != null) {
         String name = StringUtils.hasText(user.getFirstName())
            ? user.getFirstName().trim()
            : (StringUtils.hasText(user.getUserName()) ? "@" + user.getUserName() : "管理员");
         return this.telegramUserMention(user.getId(), name);
      } else {
         return "管理员";
      }
   }

   private String telegramDisplayName(org.telegram.telegrambots.meta.api.objects.User user) {
      if (user == null) {
         return "管理员";
      } else if (StringUtils.hasText(user.getUserName())) {
         return "@" + user.getUserName().trim().replaceFirst("^@", "");
      } else {
         StringBuilder displayName = new StringBuilder();
         if (StringUtils.hasText(user.getFirstName())) {
            displayName.append(user.getFirstName().trim());
         }

         if (StringUtils.hasText(user.getLastName())) {
            if (!displayName.isEmpty()) {
               displayName.append(' ');
            }

            displayName.append(user.getLastName().trim());
         }

         return displayName.isEmpty() ? "管理员" : displayName.toString();
      }
   }

   private String telegramUserMention(long telegramUserId, String displayName) {
      return "[" + this.escapeMarkdown(displayName) + "](tg://user?id=" + telegramUserId + ")";
   }

   private void waitForTelegramSendMessagePermit() {
      long waitMillis;
      synchronized (this.telegramSendRateLock) {
         long now = System.currentTimeMillis();
         long sendAt = Math.max(now, this.nextTelegramSendMessageAtMillis);
         this.nextTelegramSendMessageAtMillis = sendAt + 40L;
         waitMillis = sendAt - now;
      }

      if (waitMillis > 0L) {
         try {
            TimeUnit.MILLISECONDS.sleep(waitMillis);
         } catch (InterruptedException var9) {
            Thread.currentThread().interrupt();
         }
      }
   }

   private void sleepBeforeTelegramRetry(int retryAfterSeconds) {
      try {
         TimeUnit.SECONDS.sleep((long)Math.max(1, retryAfterSeconds) + 1L);
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
      }
   }

   private Integer resolveTelegramRetryAfterSeconds(TelegramApiException e) {
      if (e instanceof TelegramApiRequestException requestException) {
         Integer retryAfter = requestException.getParameters() == null ? null : requestException.getParameters().getRetryAfter();
         if (retryAfter != null && retryAfter > 0) {
            return retryAfter;
         }

         if (Integer.valueOf(429).equals(requestException.getErrorCode())) {
            return this.parseRetryAfterSeconds(requestException.getMessage());
         }
      }

      return this.parseRetryAfterSeconds(e.getMessage());
   }

   private Integer parseRetryAfterSeconds(String message) {
      if (!StringUtils.hasText(message)) {
         return null;
      } else {
         Matcher matcher = TELEGRAM_RETRY_AFTER_PATTERN.matcher(message);
         if (!matcher.find()) {
            return null;
         } else {
            try {
               int retryAfter = Integer.parseInt(matcher.group(1));
               return retryAfter > 0 ? retryAfter : null;
            } catch (NumberFormatException var4) {
               return null;
            }
         }
      }
   }

   private void scheduleGroupCommandCleanup(Message message) {
      if (message != null && !message.isUserMessage()) {
         this.scheduleGroupMessageCleanup(message.getChatId(), message.getMessageId());
      }
   }

   private void scheduleGroupMessageCleanup(long chatId, Integer messageId) {
      this.scheduleGroupMessageCleanup(chatId, messageId, 5L, TimeUnit.SECONDS);
   }

   private void scheduleGroupMessageCleanup(long chatId, Integer messageId, long delay, TimeUnit timeUnit) {
      if (messageId != null && chatId < 0L) {
         this.transientMessageCleaner.schedule(() -> this.deleteMessageSilently(chatId, messageId), delay, timeUnit);
      }
   }

   private void editMessageTextSilently(long chatId, Integer messageId, String text) {
      if (messageId != null) {
         try {
            EditMessageText editMessage = EditMessageText.builder()
               .chatId(chatId)
               .messageId(messageId)
               .text(text)
               .parseMode("Markdown")
               .replyMarkup(InlineKeyboardMarkup.builder().keyboard(List.of()).build())
               .build();
            this.telegramClient.execute(editMessage);
         } catch (TelegramApiException var6) {
            log.debug("编辑 Telegram 消息失败: {}", var6.getMessage());
         }
      }
   }

   private void deleteMessageSilently(long chatId, Integer messageId) {
      if (messageId != null) {
         try {
            DeleteMessage deleteMessage = DeleteMessage.builder().chatId(chatId).messageId(messageId).build();
            this.telegramClient.execute(deleteMessage);
         } catch (TelegramApiException var5) {
            log.debug("删除 Telegram 临时消息失败: {}", var5.getMessage());
         }
      }
   }

   private boolean tryAcquireRateLimit(String keySuffix, long maxCount, long windowSeconds) {
      String key = "bot:rate:" + keySuffix;

      try {
         Long count = this.redisTemplate.opsForValue().increment(key);
         if (count == null) {
            return true;
         } else {
            Long ttl = this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (count == 1L || ttl == null || ttl < 0L) {
               this.redisTemplate.expire(key, windowSeconds, TimeUnit.SECONDS);
            }

            return count <= maxCount;
         }
      } catch (Exception var9) {
         log.warn("Telegram Bot 限流计数失败，按放行处理: key={}", key, var9);
         return true;
      }
   }

   private boolean tryAcquireRequestSubmitLock(long telegramUserId) {
      String key = "bot:request:submit:lock:" + telegramUserId;

      try {
         return Boolean.TRUE.equals(this.redisTemplate.opsForValue().setIfAbsent(key, "1", 10L, TimeUnit.SECONDS));
      } catch (Exception var5) {
         log.warn("Telegram 求片提交锁写入失败，按放行处理: key={}", key, var5);
         return true;
      }
   }

   private void answerCallbackQuery(String callbackQueryId) {
      AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQueryId);

      try {
         this.telegramClient.execute(answer);
      } catch (TelegramApiException var4) {
         log.error("响应回调失败: {}", var4.getMessage());
      }
   }

   private void answerCallbackQuery(String callbackQueryId, String text) {
      AnswerCallbackQuery answer = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text(text).showAlert(true).build();

      try {
         this.telegramClient.execute(answer);
      } catch (TelegramApiException var5) {
         log.error("响应回调失败: {}", var5.getMessage());
      }
   }

   private static class KkRegistrationGrant {
      private final long operatorId;
      private final long targetTelegramUserId;

      KkRegistrationGrant(long operatorId, long targetTelegramUserId) {
         this.operatorId = operatorId;
         this.targetTelegramUserId = targetTelegramUserId;
      }

      long getOperatorId() {
         return this.operatorId;
      }

      long getTargetTelegramUserId() {
         return this.targetTelegramUserId;
      }
   }

   private static class PendingAnnouncementBroadcast {
      private final long chatId;
      private final long expiresAtMillis;
      private final Long fromChatId;
      private final Integer messageId;

      private PendingAnnouncementBroadcast(long chatId, long expiresAtMillis, Long fromChatId, Integer messageId) {
         this.chatId = chatId;
         this.expiresAtMillis = expiresAtMillis;
         this.fromChatId = fromChatId;
         this.messageId = messageId;
      }

      static DataQueryBot.PendingAnnouncementBroadcast waitingMessage(long chatId) {
         return new DataQueryBot.PendingAnnouncementBroadcast(chatId, System.currentTimeMillis() + DataQueryBot.ANNOUNCEMENT_SESSION_TTL_MILLIS, null, null);
      }

      DataQueryBot.PendingAnnouncementBroadcast withAnnouncementMessage(long fromChatId, Integer messageId) {
         return new DataQueryBot.PendingAnnouncementBroadcast(this.chatId, this.expiresAtMillis, fromChatId, messageId);
      }

      boolean isExpired() {
         return System.currentTimeMillis() > this.expiresAtMillis;
      }

      boolean isWaitingMessage() {
         return this.fromChatId == null || this.messageId == null;
      }

      public Long getFromChatId() {
         return this.fromChatId;
      }

      public Integer getMessageId() {
         return this.messageId;
      }
   }

   private static class PendingCardBatch {
      private final long chatId;
      private final int count;
      private final int day;

      PendingCardBatch(long chatId, int count, int day) {
         this.chatId = chatId;
         this.count = count;
         this.day = day;
      }

      public long getChatId() {
         return this.chatId;
      }

      public int getCount() {
         return this.count;
      }

      public int getDay() {
         return this.day;
      }
   }

   private static class PendingCreateUser {
      private final long chatId;
      private final String userName;
      private final int day;
      private final String remarks;

      PendingCreateUser(long chatId, String userName, int day, String remarks) {
         this.chatId = chatId;
         this.userName = userName;
         this.day = day;
         this.remarks = remarks;
      }

      public long getChatId() {
         return this.chatId;
      }

      public String getUserName() {
         return this.userName;
      }

      public int getDay() {
         return this.day;
      }

      public String getRemarks() {
         return this.remarks;
      }
   }

   private static class PendingExtendBatch {
      private final long chatId;
      private final int extensionDay;
      private final Integer expiredRange;

      PendingExtendBatch(long chatId, int extensionDay, Integer expiredRange) {
         this.chatId = chatId;
         this.extensionDay = extensionDay;
         this.expiredRange = expiredRange;
      }

      public long getChatId() {
         return this.chatId;
      }

      public int getExtensionDay() {
         return this.extensionDay;
      }

      public Integer getExpiredRange() {
         return this.expiredRange;
      }
   }

   private static class PendingKkRegistration {
      private final String token;
      private final long operatorId;
      private final long expiresAtMillis;

      PendingKkRegistration(String token, long operatorId, long expiresAtMillis) {
         this.token = token;
         this.operatorId = operatorId;
         this.expiresAtMillis = expiresAtMillis;
      }

      String getToken() {
         return this.token;
      }

      long getOperatorId() {
         return this.operatorId;
      }

      boolean isExpired() {
         return System.currentTimeMillis() > this.expiresAtMillis;
      }
   }

   private static class PendingPanelCommand {
      private final String command;
      private final long expiresAtMillis;
      private final Message panelMessage;
      private final String returnPanel;
      private final Long targetUserId;

      PendingPanelCommand(String command, long expiresAtMillis, Message panelMessage, String returnPanel, Long targetUserId) {
         this.command = command;
         this.expiresAtMillis = expiresAtMillis;
         this.panelMessage = panelMessage;
         this.returnPanel = returnPanel;
         this.targetUserId = targetUserId;
      }

      String getCommand() {
         return this.command;
      }

      Message getPanelMessage() {
         return this.panelMessage;
      }

      String getReturnPanel() {
         return this.returnPanel;
      }

      Long getTargetUserId() {
         return this.targetUserId;
      }

      DataQueryBot.PendingPanelCommand refresh() {
         return new DataQueryBot.PendingPanelCommand(
            this.command, System.currentTimeMillis() + DataQueryBot.START_PANEL_INPUT_TTL_MILLIS, this.panelMessage, this.returnPanel, this.targetUserId
         );
      }

      boolean isExpired() {
         return System.currentTimeMillis() > this.expiresAtMillis;
      }
   }

   private static class PendingTelegramMemberMute {
      private final DataQueryBot.TelegramKkTarget target;
      private final Message panelMessage;
      private final long expiresAtMillis;

      PendingTelegramMemberMute(DataQueryBot.TelegramKkTarget target, Message panelMessage, long expiresAtMillis) {
         this.target = target;
         this.panelMessage = panelMessage;
         this.expiresAtMillis = expiresAtMillis;
      }

      DataQueryBot.TelegramKkTarget getTarget() {
         return this.target;
      }

      Message getPanelMessage() {
         return this.panelMessage;
      }

      boolean isExpired() {
         return System.currentTimeMillis() > this.expiresAtMillis;
      }
   }

   private static class PendingTelegramRegister {
      private String taskId;
      private long chatId;
      private long telegramUserId;
      private String telegramUsername;
      private String embyUserName;
      private String rawPassword;
      private String registerChannelDetail;
      private String lockKey;
      private String lockToken;
      private long createdAtMillis;
      private boolean adminGifted;
      private long grantedByTelegramUserId;

      public PendingTelegramRegister() {
      }

      PendingTelegramRegister(
         String taskId,
         long chatId,
         long telegramUserId,
         String telegramUsername,
         String embyUserName,
         String rawPassword,
         String registerChannelDetail,
         String lockKey,
         String lockToken,
         long createdAtMillis
      ) {
         this(
            taskId, chatId, telegramUserId, telegramUsername, embyUserName, rawPassword, registerChannelDetail, lockKey, lockToken, createdAtMillis, false, 0L
         );
      }

      PendingTelegramRegister(
         String taskId,
         long chatId,
         long telegramUserId,
         String telegramUsername,
         String embyUserName,
         String rawPassword,
         String registerChannelDetail,
         String lockKey,
         String lockToken,
         long createdAtMillis,
         boolean adminGifted,
         long grantedByTelegramUserId
      ) {
         this.taskId = taskId;
         this.chatId = chatId;
         this.telegramUserId = telegramUserId;
         this.telegramUsername = telegramUsername;
         this.embyUserName = embyUserName;
         this.rawPassword = rawPassword;
         this.registerChannelDetail = registerChannelDetail;
         this.lockKey = lockKey;
         this.lockToken = lockToken;
         this.createdAtMillis = createdAtMillis;
         this.adminGifted = adminGifted;
         this.grantedByTelegramUserId = grantedByTelegramUserId;
      }

      public String getTaskId() {
         return this.taskId;
      }

      public void setTaskId(String taskId) {
         this.taskId = taskId;
      }

      public long getChatId() {
         return this.chatId;
      }

      public void setChatId(long chatId) {
         this.chatId = chatId;
      }

      public long getTelegramUserId() {
         return this.telegramUserId;
      }

      public void setTelegramUserId(long telegramUserId) {
         this.telegramUserId = telegramUserId;
      }

      public String getTelegramUsername() {
         return this.telegramUsername;
      }

      public void setTelegramUsername(String telegramUsername) {
         this.telegramUsername = telegramUsername;
      }

      public String getEmbyUserName() {
         return this.embyUserName;
      }

      public void setEmbyUserName(String embyUserName) {
         this.embyUserName = embyUserName;
      }

      public String getRawPassword() {
         return this.rawPassword;
      }

      public void setRawPassword(String rawPassword) {
         this.rawPassword = rawPassword;
      }

      public String getRegisterChannelDetail() {
         return this.registerChannelDetail;
      }

      public void setRegisterChannelDetail(String registerChannelDetail) {
         this.registerChannelDetail = registerChannelDetail;
      }

      public String getLockKey() {
         return this.lockKey;
      }

      public void setLockKey(String lockKey) {
         this.lockKey = lockKey;
      }

      public String getLockToken() {
         return this.lockToken;
      }

      public void setLockToken(String lockToken) {
         this.lockToken = lockToken;
      }

      public long getCreatedAtMillis() {
         return this.createdAtMillis;
      }

      public void setCreatedAtMillis(long createdAtMillis) {
         this.createdAtMillis = createdAtMillis;
      }

      public boolean isAdminGifted() {
         return this.adminGifted;
      }

      public void setAdminGifted(boolean adminGifted) {
         this.adminGifted = adminGifted;
      }

      public long getGrantedByTelegramUserId() {
         return this.grantedByTelegramUserId;
      }

      public void setGrantedByTelegramUserId(long grantedByTelegramUserId) {
         this.grantedByTelegramUserId = grantedByTelegramUserId;
      }
   }

   private static class PendingUserEdit {
      private final long chatId;
      private final long userId;

      PendingUserEdit(long chatId, long userId) {
         this.chatId = chatId;
         this.userId = userId;
      }

      public long getChatId() {
         return this.chatId;
      }

      public long getUserId() {
         return this.userId;
      }
   }

   private static class TelegramKkTarget {
      private final long telegramUserId;
      private final String displayName;
      private final String telegramUsername;

      TelegramKkTarget(long telegramUserId, String displayName, String telegramUsername) {
         this.telegramUserId = telegramUserId;
         this.displayName = StringUtils.hasText(displayName) ? displayName : String.valueOf(telegramUserId);
         this.telegramUsername = telegramUsername;
      }

      static DataQueryBot.TelegramKkTarget from(org.telegram.telegrambots.meta.api.objects.User user) {
         String displayName = user == null ? null : user.getFirstName();
         if (!StringUtils.hasText(displayName) && user != null) {
            displayName = user.getUserName();
         }

         return new DataQueryBot.TelegramKkTarget(
            user != null && user.getId() != null ? user.getId() : 0L, displayName, user == null ? null : user.getUserName()
         );
      }

      long getTelegramUserId() {
         return this.telegramUserId;
      }

      String getDisplayName() {
         return this.displayName;
      }

      String getTelegramUsername() {
         return this.telegramUsername;
      }
   }
}
