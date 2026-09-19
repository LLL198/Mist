package com.una.embyhub.pointsbot;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.PointsBotRedeemTypeEnum;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.telegrambot.TelegramBotAuthorizationService;
import com.una.embyhub.config.common.telegrambot.TelegramBotPermission;
import com.una.embyhub.config.common.utils.RedisLockUtils;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserSave;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.embyuser.InsertUserResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagConfigResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.PointsBotBrainEntry;
import com.una.embyhub.model.entity.PointsBotBrainRound;
import com.una.embyhub.model.entity.PointsBotFoamBag;
import com.una.embyhub.model.entity.PointsBotLevelConfig;
import com.una.embyhub.model.entity.PointsBotLottery;
import com.una.embyhub.model.entity.PointsBotLotteryEntry;
import com.una.embyhub.model.entity.PointsBotPrizeConfig;
import com.una.embyhub.model.entity.PointsBotRedPacket;
import com.una.embyhub.model.entity.PointsBotRedPacketClaim;
import com.una.embyhub.model.entity.PointsBotRedeemConfig;
import com.una.embyhub.model.entity.PointsBotScratchEntry;
import com.una.embyhub.model.entity.PointsBotScratchRound;
import com.una.embyhub.pointsbot.model.BlackjackCard;
import com.una.embyhub.pointsbot.model.BlackjackSession;
import com.una.embyhub.pointsbot.model.BrainGameConfig;
import com.una.embyhub.pointsbot.model.CheckinResult;
import com.una.embyhub.pointsbot.model.FoamBagState;
import com.una.embyhub.pointsbot.model.FoamBagTransferResult;
import com.una.embyhub.pointsbot.model.HellDiceGameConfig;
import com.una.embyhub.pointsbot.model.PointsBotConfig;
import com.una.embyhub.pointsbot.model.PointsProfile;
import com.una.embyhub.pointsbot.model.RedPacketClaimResult;
import com.una.embyhub.pointsbot.model.RedPacketExpireResult;
import com.una.embyhub.pointsbot.model.SanguoshaCard;
import com.una.embyhub.pointsbot.service.BlackjackGameService;
import com.una.embyhub.pointsbot.service.BrainGameService;
import com.una.embyhub.pointsbot.service.HellDiceGameService;
import com.una.embyhub.pointsbot.service.PointsBotConfigRules;
import com.una.embyhub.pointsbot.service.PointsBotConfigService;
import com.una.embyhub.pointsbot.service.PointsBotFoamBagService;
import com.una.embyhub.pointsbot.service.PointsBotGameConfigService;
import com.una.embyhub.pointsbot.service.PointsBotLotteryService;
import com.una.embyhub.pointsbot.service.PointsBotRedPacketService;
import com.una.embyhub.pointsbot.service.PointsStore;
import com.una.embyhub.pointsbot.service.SanguoshaCardService;
import com.una.embyhub.pointsbot.service.ScratchCardGameService;
import com.una.embyhub.pointsbot.service.TelegramGameRateLimiter;
import com.una.embyhub.pointsbot.telegram.CallbackQuery;
import com.una.embyhub.pointsbot.telegram.Message;
import com.una.embyhub.pointsbot.telegram.MessageEntity;
import com.una.embyhub.pointsbot.telegram.TelegramBotApiClient;
import com.una.embyhub.pointsbot.telegram.Update;
import com.una.embyhub.pointsbot.telegram.User;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.PointsBotLevelConfigService;
import com.una.embyhub.service.PointsBotPrizeConfigService;
import com.una.embyhub.service.PointsBotRedeemConfigService;
import com.una.embyhub.service.TelegramBindingManager;
import embyclient.ApiException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

@Component
public class PointsBot {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PointsBot.class);
   private static final int REDEEM_COST = 2;
   private static final String REDEEM_LOCK_PREFIX = "foam:points-bot:redeem:";
   private static final long REDEEM_LOCK_TTL_SECONDS = 300L;
   private static final int TRANSFER_MESSAGE_DELETE_DELAY_SECONDS = 10;
   private static final int BLACKJACK_MAX_PLAYER_CARDS = 7;
   private static final int SCRATCH_RESULT_DELETE_DELAY_SECONDS = 180;
   private static final int SCRATCH_PANEL_REFRESH_DEBOUNCE_MILLIS = 300;
   private static final long[] SCRATCH_RESULT_REFRESH_RETRY_DELAYS_SECONDS = new long[]{1L, 3L, 10L};
   private static final int SCRATCH_PANEL_REFRESH_LOCK_STRIPES = 64;
   private static final String SCRATCH_CALLBACK_PREFIX = "scratch:";
   private static final String SCRATCH_WINS_CALLBACK_PREFIX = "scratchwins:";
   private static final String SCRATCH_WINS_PANEL_KEY_PREFIX = "foam:points-bot:scratch:wins:panel:";
   private static final String SCRATCH_WINS_PANEL_LOCK_PREFIX = "foam:points-bot:scratch:wins:panel-lock:";
   private static final String SCRATCH_WINS_SESSION_KEY_PREFIX = "foam:points-bot:scratch:wins:session:";
   private static final int SCRATCH_WINS_GROUP_SIZE = 10;
   private static final int SCRATCH_WINS_ADMIN_PAGE_SIZE = 10;
   private static final int SCRATCH_WINS_GROUP_DELETE_SECONDS = 30;
   private static final int SCRATCH_LOCATOR_DELETE_SECONDS = 5;
   private static final Duration SCRATCH_WINS_SESSION_TTL = Duration.ofMinutes(10L);
   private static final DateTimeFormatter SCRATCH_WIN_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   private static final DateTimeFormatter FOAM_BAG_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   static final List<String> FOAM_BAG_BORROW_BLESSINGS = List.of(
      "\ud83c\udf89 愿好运和积分一起涨涨涨～",
      "\ud83c\udf40 祝你接下来好运连连，惊喜不断～",
      "✨ 愿每一分好运都稳稳落进你的口袋～",
      "\ud83d\ude80 祝你一路顺风，积分越用越有～",
      "\ud83c\udf08 愿今天的好心情和好运一起加倍～",
      "\ud83e\udd73 祝你玩得开心，好运常伴～"
   );
   static final List<String> FOAM_BAG_REPAY_BLESSINGS = List.of(
      "\ud83c\udf1f 言而有信，信用满格，愿好运一直相伴～",
      "\ud83d\udc4f 顺利归还，祝你接下来顺顺利利～",
      "\ud83c\udf40 有借有还，好运循环，愿惊喜常在～",
      "✨ 今日信用值拉满，愿好事接连发生～",
      "\ud83c\udf8a 圆满归还，祝你往后的每一步都稳稳当当～",
      "\ud83d\udcab 守信的人自带好运，祝你好运不断～"
   );
   private static final String BRAIN_CALLBACK_PREFIX = "brain:";
   private static final String HELL_CALLBACK_PREFIX = "hell:";
   private static final String HELL_ADMIN_REMINDER_KEY_PREFIX = "foam:points-bot:hell:admin-reminder:";
   private static final Duration HELL_ADMIN_REMINDER_COOLDOWN = Duration.ofMinutes(30L);
   private static final String HELL_ADMIN_TOP_UP_KEY_PREFIX = "foam:points-bot:hell:admin-top-up:";
   private static final Duration HELL_ADMIN_TOP_UP_CONFIRMATION_TTL = Duration.ofMinutes(2L);
   private static final int HELL_ADMIN_TOP_UP_MAX_PER_OPERATION = 100000;
   private static final int HELL_DICE_MESSAGE_DELETE_SECONDS = 6;
   private static final int HELL_PANEL_REFRESH_DEBOUNCE_MILLIS = 350;
   private static final int HELL_SPECTATOR_DISPLAY_LIMIT = 20;
   private static final String FOAM_BAG_CALLBACK_PREFIX = "foam_bag:";
   private static final String RED_PACKET_CALLBACK_PREFIX = "red_packet:";
   private static final int RED_PACKET_PANEL_REFRESH_DEBOUNCE_MILLIS = 500;
   private static final int RED_PACKET_CLAIM_NAME_MAX_LENGTH = 20;
   private static final Duration RED_PACKET_CONFIRMATION_TTL = Duration.ofMinutes(2L);
   private static final DateTimeFormatter RED_PACKET_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   private static final Pattern USERNAME_MENTION_PATTERN = Pattern.compile("@([A-Za-z0-9_]{3,32})");
   private final PointsBotConfigService configService;
   private final PointsStore pointsStore;
   private final PointsBotFoamBagService foamBagService;
   private final PointsBotRedPacketService redPacketService;
   private final PointsBotLotteryService lotteryService;
   private final BlackjackGameService blackjackGameService;
   private final ScratchCardGameService scratchCardGameService;
   private final BrainGameService brainGameService;
   @Autowired
   private HellDiceGameService hellDiceGameService;
   private final PointsBotGameConfigService gameConfigService;
   private final SanguoshaCardService sanguoshaCardService;
   private final TelegramGameRateLimiter telegramGameRateLimiter;
   private final EmbyInfoService embyInfoService;
   private final EmbyUserService embyUserService;
   private final TelegramBindingManager telegramBindingManager;
   private final RedisLockUtils redisLockUtils;
   private final PointsBotRedeemConfigService pointsBotRedeemConfigService;
   private final PointsBotPrizeConfigService pointsBotPrizeConfigService;
   private final PointsBotLevelConfigService pointsBotLevelConfigService;
   private final TelegramBotAuthorizationService telegramBotAuthorizationService;
   private final StringRedisTemplate stringRedisTemplate;
   private TelegramBotApiClient botApiClient;
   private TelegramBotApiClient sharedTelegramClient;
   private final Random random = new SecureRandom();
   private volatile boolean enabled;
   private PointsBotConfig config = new PointsBotConfig();
   private final ScheduledExecutorService sharedScheduler = Executors.newScheduledThreadPool(4);
   private final Set<Long> pendingScratchPanelRefreshes = ConcurrentHashMap.newKeySet();
   private final Object[] scratchPanelRefreshLocks = createScratchPanelRefreshLocks(64);
   private final Object scratchRoundTaskMonitor = new Object();
   private ScheduledFuture<?> scratchRoundTask;
   private final Object brainRoundTaskMonitor = new Object();
   private ScheduledFuture<?> brainRoundTask;
   private final Object hellRoundTaskMonitor = new Object();
   private ScheduledFuture<?> hellRoundTask;
   private final Set<Long> pendingHellPanelRefreshes = ConcurrentHashMap.newKeySet();
   private final Set<String> pendingHellPanelDeletes = ConcurrentHashMap.newKeySet();
   private final Map<Long, ScheduledFuture<?>> redPacketExpiryTasks = new ConcurrentHashMap<>();
   private final Set<Long> pendingRedPacketPanelRefreshes = ConcurrentHashMap.newKeySet();
   private final Map<String, PointsBot.PendingRedPacketConfirmation> pendingRedPacketConfirmations = new ConcurrentHashMap<>();

   @PreDestroy
   public void cleanup() {
      this.stopScratchRoundTask();
      this.stopBrainRoundTask();
      this.stopHellRoundTask();
      this.pendingHellPanelRefreshes.clear();
      this.stopAllRedPacketTasks();
      this.closeClient();
      this.sharedScheduler.shutdown();
   }

   public boolean isEnabled() {
      return this.enabled && this.botApiClient != null && this.botApiClient.isReady();
   }

   public boolean isGameCommandEnabled(String gameCommand) {
      return this.gameConfigService.isEnabled(gameCommand) && PointsBotConfigRules.isGameCommandEnabled(this.config, gameCommand);
   }

   public boolean isFoamBagEnabled() {
      return !Boolean.FALSE.equals(this.config.getFoamBagEnabled());
   }

   public boolean isRedPacketEnabled() {
      return !Boolean.FALSE.equals(this.config.getRedPacketEnabled());
   }

   public void sendPublicMessage(long chatId, String text) {
      this.sendMessage(chatId, text);
   }

   public RedPacketExpireResult cancelRedPacketByAdmin(long redPacketId) {
      RedPacketExpireResult result = this.redPacketService.cancelOpen(redPacketId);
      if (result.redPacket() != null && "CANCELLED".equals(result.redPacket().getStatus())) {
         this.cancelRedPacketExpiry(redPacketId);
         this.refreshRedPacketPanel(redPacketId);
         if (result.changed() && result.refundedPoints() > 0) {
            this.sendRedPacketRefundNotice(result.redPacket(), result.refundedPoints());
         }

         return result;
      } else {
         return result;
      }
   }

   @PostConstruct
   public void initClient() {
      this.reloadConfig();
   }

   public synchronized void reloadConfig() {
      PointsBotConfigService.PointsBotChannelConfig channelConfig = this.configService.loadConfig();
      this.enabled = channelConfig.isEnabled();
      this.config = channelConfig.getConfig();
      if (this.enabled && this.sharedTelegramClient != null) {
         this.botApiClient = this.sharedTelegramClient;
      } else {
         this.botApiClient = null;
      }

      if (this.enabled) {
         this.resumeScratchRoundTaskIfNeeded();
         this.resumeBrainRoundTaskIfNeeded();
         this.resumeHellRoundTaskIfNeeded();
      } else {
         this.stopScratchRoundTask();
         this.stopBrainRoundTask();
         this.stopHellRoundTask();
      }

      this.recoverRedPacketTasks();
   }

   public synchronized void attachSharedClient(TelegramBotApiClient telegramClient) {
      this.sharedTelegramClient = telegramClient;
      this.reloadConfig();
   }

   public void startClient() {
   }

   public void closeClient() {
      if (this.botApiClient != null && this.botApiClient != this.sharedTelegramClient) {
         this.botApiClient.close();
      }

      this.botApiClient = null;
   }

   public void initCommands() {
      if (this.botApiClient != null && this.botApiClient.isReady()) {
         List<TelegramBotApiClient.BotCommand> commands = new ArrayList<>();
         commands.add(new TelegramBotApiClient.BotCommand("checkin", "每日签到"));
         commands.add(new TelegramBotApiClient.BotCommand("points", "查询积分"));
         if (this.isFoamBagEnabled()) {
            commands.add(new TelegramBotApiClient.BotCommand("foambag", "雾袋"));
         }

         if (this.isRedPacketEnabled()) {
            commands.add(new TelegramBotApiClient.BotCommand("redpacket", "群积分红包"));
         }

         commands.add(new TelegramBotApiClient.BotCommand("leaderboard", "积分排行榜"));
         commands.add(new TelegramBotApiClient.BotCommand("scratchwins", "雾中刮刮乐大奖记录"));
         commands.add(new TelegramBotApiClient.BotCommand("lottery", "抽奖"));
         commands.add(new TelegramBotApiClient.BotCommand("transfer", "积分互转"));
         commands.add(new TelegramBotApiClient.BotCommand("redeem", "积分注册账号"));
         commands.add(new TelegramBotApiClient.BotCommand("recharge", "积分续费绑定账号"));
         commands.add(new TelegramBotApiClient.BotCommand("renew", "管理员续期账号"));
         commands.add(new TelegramBotApiClient.BotCommand("exchange", "查看可兑换项目"));
         commands.add(new TelegramBotApiClient.BotCommand("prizes", "积分奖品列表"));
         if (this.isGameCommandEnabled("sgs")) {
            commands.add(new TelegramBotApiClient.BotCommand("sgs", "三国杀玩法"));
         }

         if (this.isGameCommandEnabled("blackjack")) {
            commands.add(new TelegramBotApiClient.BotCommand("blackjack", "21点游戏"));
         }

         if (this.isGameCommandEnabled("dice")) {
            commands.add(new TelegramBotApiClient.BotCommand("dice", "骰子比大小"));
         }

         if (this.isGameCommandEnabled("hell_dice")) {
            commands.add(new TelegramBotApiClient.BotCommand("helldice", "地狱骰"));
            commands.add(new TelegramBotApiClient.BotCommand("hellrank", "地狱之王排行"));
         }

         if (this.isGameCommandEnabled("slots")) {
            commands.add(new TelegramBotApiClient.BotCommand("slots", "老虎机"));
         }

         if (this.isGameCommandEnabled("scratch")) {
            commands.add(new TelegramBotApiClient.BotCommand("scratch", "雾中刮刮乐"));
         }

         if (this.isGameCommandEnabled("brain")) {
            commands.add(new TelegramBotApiClient.BotCommand("brain", "Brain 脑力挑战"));
         }

         try {
            this.botApiClient.setMyCommands(commands);
         } catch (TelegramBotApiClient.TelegramBotApiException var3) {
            log.warn("积分机器人设置命令失败: {}", var3.getMessage());
         }
      }
   }

   public void consume(Update update) {
      if (this.enabled) {
         if (update.hasCallbackQuery()) {
            this.handleCallbackQuery(update);
         } else {
            if (update.hasMessage()) {
               Message message = update.getMessage();
               if (!this.isAllowedChat(message)) {
                  return;
               }

               if (message.hasText()) {
                  this.handleTextMessage(message);
                  return;
               }

               this.handleMessagePoints(message);
            }
         }
      }
   }

   private void handleTextMessage(Message message) {
      String text = message.getText().trim();
      if (text.startsWith("/")) {
         String command = text.split("\\s+", 2)[0].toLowerCase(Locale.ROOT);
         if (command.contains("@")) {
            command = command.substring(0, command.indexOf(64));
         }

         switch (command) {
            case "/checkin":
               this.handleCheckin(message);
               break;
            case "/points":
               this.handlePoints(message);
               break;
            case "/foambag":
               this.handleFoamBag(message);
               break;
            case "/redpacket":
            case "/hongbao":
               this.handleRedPacket(message);
               break;
            case "/leaderboard":
               this.handleLeaderboard(message);
               break;
            case "/lottery":
               this.handleLottery(message);
               break;
            case "/transfer":
               this.handleTransfer(message);
               break;
            case "/redeem":
               this.handleRedeem(message);
               break;
            case "/recharge":
               this.handleRedeemRenew(message);
               break;
            case "/renew":
               this.handleRenew(message);
               break;
            case "/exchange":
               this.handleExchange(message);
               break;
            case "/prizes":
               this.handlePrizes(message);
               break;
            case "/sgs":
               this.runGameCommand(message, "sgs", () -> this.handleSanguoshaHelp(message));
               break;
            case "/blackjack":
            case "/bj":
               this.runGameCommand(message, "blackjack", () -> this.handleBlackjack(message));
               break;
            case "/dice":
               this.runGameCommand(message, "dice", () -> this.handleDice(message));
               break;
            case "/helldice":
            case "/hell":
               this.runGameCommand(message, "hell_dice", () -> this.handleHellDice(message));
               break;
            case "/hellrank":
            case "/hellking":
               this.handleHellRankingCommand(message);
               break;
            case "/hellvault":
               this.handleHellVaultAdminCommand(message);
               break;
            case "/slots":
               this.runGameCommand(message, "slots", () -> this.handleSlots(message));
               break;
            case "/scratchwins":
               this.handleScratchWins(message);
               break;
            case "/scratch":
               this.handleScratchCardCommand(message);
               break;
            case "/brain":
               this.runGameCommand(message, "brain", () -> this.handleBrain(message));
               break;
            case "/start":
               if (!this.handleBrainPrivateStart(message, text)) {
               }
         }
      } else {
         if ("签到".equals(text)) {
            this.handleCheckin(message);
            return;
         }

         if ("雾中刮刮乐中奖记录".equals(text)) {
            this.handleScratchWins(message);
            return;
         }

         if (!this.isGroupChat(message) && this.trySubmitBrainAnswer(message)) {
            return;
         }

         if (!this.tryJoinLotteryByKeyword(message) && !this.tryPlaySanguoshaCard(message)) {
            this.handleMessagePoints(message);
         }
      }
   }

   private void runGameCommand(Message message, String gameCommand, Runnable action) {
      if (!this.isGameCommandEnabled(gameCommand)) {
         this.sendReplyAndDelete(message.getChatId(), message.getMessageId(), "\ud83c\udfae 当前游戏命令已关闭，请联系管理员。", 10);
      } else if (!this.isGroupChat(message) || this.tryStartGroupGame(message, gameCommand)) {
         action.run();
      }
   }

   private void handleCheckin(Message message) {
      long chatId = message.getChatId();
      boolean groupChat = this.isGroupChat(message);
      boolean groupCheckinEnabled = !Boolean.FALSE.equals(this.config.getGroupCheckinEnabled());
      if (groupCheckinEnabled && !groupChat) {
         this.sendMessage(chatId, "签到功能仅限群聊使用，请在群聊中签到。");
      } else if (!groupCheckinEnabled && groupChat) {
         this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "群聊签到已关闭，请私聊机器人签到。", 15);
      } else {
         Long pointsChatId = groupChat ? chatId : this.resolvePointsChatId(message);
         if (pointsChatId == null) {
            this.sendMessage(chatId, "未配置积分群/频道。");
         } else {
            User user = message.getFrom();
            PointsProfile profile = this.pointsStore.getOrCreate(pointsChatId, user.getId(), user.getUserName(), this.displayName(user));
            LocalDateTime penaltyUntil = this.foamBagService.checkCheckinRestriction(pointsChatId, user.getId());
            if (penaltyUntil != null) {
               String hint = this.foamBagService.restrictionMessage(penaltyUntil, "签到获得积分");
               if (groupChat) {
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), hint, 15);
               } else {
                  this.sendMessage(chatId, hint);
               }
            } else {
               CheckinResult result = this.doCheckin(profile);
               String reply = result.isAlreadyCheckedIn()
                  ? String.format("你今天已签到过啦，当前积分 %d。", result.getTotalPoints())
                  : String.format("✅ 签到成功：%s（连续 %d 天），当前积分 %d。", result.getMessage(), result.getStreak(), result.getTotalPoints());
               if (!groupChat) {
                  this.sendMessage(chatId, reply);
               } else {
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), reply, 15);
               }
            }
         }
      }
   }

   private void handlePoints(Message message) {
      long chatId = message.getChatId();
      Long pointsChatId = this.resolvePointsChatId(message);
      if (pointsChatId == null) {
         this.sendMessage(chatId, "未配置积分群/频道。");
      } else {
         User user = message.getFrom();
         long targetUserId = user.getId();
         if (!this.isGroupChat(message)) {
            String text = message.getText().trim();
            String argsText = text.substring("/points".length()).trim();
            if (StringUtils.hasText(argsText)) {
               try {
                  targetUserId = Long.parseLong(argsText.trim());
               } catch (NumberFormatException var12) {
                  this.sendMessage(chatId, "用户ID格式错误。\n用法：/points 或 /points 用户ID");
                  return;
               }
            }
         }

         PointsProfile profile = this.pointsStore.findByUserId(pointsChatId, targetUserId);
         if (profile == null) {
            if (this.isGroupChat(message)) {
               this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
               this.sendReplyAndDelete(chatId, message.getMessageId(), "未找到该用户的积分记录。", 15);
            } else {
               this.sendMessage(chatId, "未找到该用户的积分记录。");
            }
         } else {
            String levelLabel = profile.getLevelName() == null ? "未设置" : profile.getLevelName();
            String userLabel = this.formatUser(profile);
            String reply = String.format("\ud83c\udf01 Mist 积分中心\n\n用户：%s\n当前积分：%d\n当前等级：%s", userLabel, profile.getPoints(), levelLabel);
            if (this.isGroupChat(message)) {
               this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
               this.sendReplyAndDelete(chatId, message.getMessageId(), reply, 15);
            } else {
               this.sendMessage(chatId, reply);
            }
         }
      }
   }

   private void handleFoamBag(Message message) {
      if (this.isGroupChat(message)) {
         if (!this.isFoamBagEnabled()) {
            this.sendReplyAndDeleteBoth(message.getChatId(), message.getMessageId(), "\ud83c\udf01 当前渠道已关闭雾袋，新申请已暂停。", 15);
         } else {
            this.sendReplyAndDeleteBoth(message.getChatId(), message.getMessageId(), "\ud83c\udf01 雾袋包含个人积分和归还时间，请私聊机器人打开。", 15);
         }
      } else {
         Long pointsChatId = this.resolvePointsChatId(message);
         if (pointsChatId == null) {
            this.sendMessage(message.getChatId(), "未配置积分群/频道，暂时无法使用雾袋。");
         } else {
            this.renderFoamBagPanel(message.getChatId(), null, pointsChatId, message.getFrom());
         }
      }
   }

   private void handleFoamBagCallback(Update update) {
      CallbackQuery callback = update.getCallbackQuery();
      User user = callback.getFrom();
      Message panelMessage = callback.getMessage();
      if (user != null && panelMessage != null && !this.isGroupChat(panelMessage) && panelMessage.getChatId() == user.getId()) {
         Long pointsChatId = this.resolvePointsChatId(panelMessage);
         if (pointsChatId == null) {
            this.answerFoamBagCallback(callback, "未配置积分群/频道", true);
         } else {
            String[] parts = callback.getData().split(":", 3);
            if (parts.length < 2) {
               this.answerFoamBagCallback(callback, "这个雾袋按钮已经失效", true);
            } else {
               try {
                  String e = parts[1];
                  switch (e) {
                     case "select":
                        this.ensureFoamBagApplicationEnabled();
                        if (parts.length != 3) {
                           throw new BizException("雾袋积分档位无效");
                        }

                  int selectedAmount = Integer.parseInt(parts[2]);
                        this.renderFoamBagConfirmation(panelMessage, selectedAmount);
                        this.answerFoamBagCallback(callback, null, false);
                        break;
                     case "confirm": {
                        this.ensureFoamBagApplicationEnabled();
                        if (parts.length != 3) {
                           throw new BizException("雾袋积分档位无效");
                        }

                        int amount = Integer.parseInt(parts[2]);
                        PointsBotFoamBag record = this.foamBagService.apply(pointsChatId, user.getId(), user.getUserName(), this.displayName(user), amount);
                        this.sendFoamBagBorrowedNotice(pointsChatId, user, record);
                        this.answerFoamBagCallback(callback, "已获得 " + record.getPrincipalPoints() + " 积分", false);
                        this.renderFoamBagPanel(panelMessage.getChatId(), panelMessage.getMessageId(), pointsChatId, user);
                        break;
                     }
                     case "repay": {
                        PointsBotFoamBag record = this.foamBagService.repay(pointsChatId, user.getId());
                        this.sendFoamBagRepaidNotice(pointsChatId, user, record);
                        this.answerFoamBagCallback(callback, "已归还 " + record.getRepaymentPoints() + " 积分", false);
                        this.renderFoamBagPanel(panelMessage.getChatId(), panelMessage.getMessageId(), pointsChatId, user);
                        break;
                     }
                     case "refresh":
                     case "back":
                        this.answerFoamBagCallback(callback, null, false);
                        this.renderFoamBagPanel(panelMessage.getChatId(), panelMessage.getMessageId(), pointsChatId, user);
                        break;
                     default:
                        this.answerFoamBagCallback(callback, "这个雾袋按钮已经失效", true);
                  }
               } catch (NumberFormatException var11) {
                  this.answerFoamBagCallback(callback, "雾袋积分档位无效", true);
               } catch (BizException var12) {
                  this.answerFoamBagCallback(callback, var12.getMessage(), true);
                  this.renderFoamBagPanel(panelMessage.getChatId(), panelMessage.getMessageId(), pointsChatId, user);
               } catch (RuntimeException var13) {
                  log.error("雾袋按钮处理失败: userId={}, data={}", user.getId(), callback.getData(), var13);
                  this.answerFoamBagCallback(callback, "操作失败，请稍后重试", true);
               }
            }
         }
      } else {
         this.answerFoamBagCallback(callback, "请在自己的机器人私聊面板中操作", true);
      }
   }

   private void sendFoamBagBorrowedNotice(long chatId, User user, PointsBotFoamBag record) {
      String mention = this.redPacketUserMention(user.getId(), user.getUserName(), this.displayName(user));
      this.sendFoamBagGroupNotice(
         chatId, "\ud83c\udf01 " + mention + " 取出雾袋，获得 <b>" + record.getPrincipalPoints() + "</b> 积分！\n" + this.randomFoamBagBlessing(FOAM_BAG_BORROW_BLESSINGS)
      );
   }

   private void sendFoamBagRepaidNotice(long chatId, User user, PointsBotFoamBag record) {
      String mention = this.redPacketUserMention(user.getId(), user.getUserName(), this.displayName(user));
      this.sendFoamBagGroupNotice(
         chatId, "\ud83d\udcb8 " + mention + " 还了 <b>" + record.getRepaymentPoints() + "</b> 积分！\n" + this.randomFoamBagBlessing(FOAM_BAG_REPAY_BLESSINGS)
      );
   }

   private String randomFoamBagBlessing(List<String> blessings) {
      return blessings.get(this.random.nextInt(blessings.size()));
   }

   private void sendFoamBagGroupNotice(long chatId, String text) {
      if (this.botReady()) {
         try {
            this.botApiClient.sendMessage(chatId, text, null, "HTML", null, false);
         } catch (TelegramBotApiClient.TelegramBotApiException var5) {
            log.warn("雾袋群通知发送失败: chatId={}, error={}", chatId, var5.getMessage());
         }
      }
   }

   private void renderFoamBagConfirmation(Message panelMessage, int amount) {
      PointsBotFoamBagConfigResponse config = this.foamBagService.getConfig();
      if (!config.getAmountTiers().contains(amount)) {
         throw new BizException("这个雾袋积分档位已不可用，请返回刷新");
      } else {
         int repayment = Math.multiplyExact(amount, config.getRepaymentMultiplier());
         String text = "\ud83c\udf01 Mist 雾袋确认\n\n\ud83c\udf81 本次获得："
            + amount
            + " 积分\n\ud83d\udd01 需要归还："
            + repayment
            + " 积分\n⏰ 归还期限："
            + config.getRepaymentHours()
            + " 小时\n\n✅ 确认后立即到账。\n⚠️ 必须一次归还全部积分；逾期会将积分归零，并限制 "
            + config.getPenaltyDays()
            + " 天无法签到、使用雾袋或接收他人转赠积分。";
         List<List<TelegramBotApiClient.InlineButton>> keyboard = List.of(
            List.of(
               new TelegramBotApiClient.InlineButton("✅ 确认袋 " + amount + " 积分", "foam_bag:confirm:" + amount),
               new TelegramBotApiClient.InlineButton("↩️ 返回", "foam_bag:back")
            )
         );
         this.editFoamBagPanel(panelMessage.getChatId(), panelMessage.getMessageId(), text, keyboard);
      }
   }

   private void renderFoamBagPanel(long chatId, Long messageId, long pointsChatId, User user) {
      FoamBagState state = this.foamBagService.getState(pointsChatId, user.getId(), user.getUserName(), this.displayName(user));
      PointsBotFoamBagConfigResponse config = state.getConfig();
      StringBuilder text = new StringBuilder("\ud83c\udf01 Mist 雾袋\n\n");
      List<List<TelegramBotApiClient.InlineButton>> keyboard = new ArrayList<>();
      if (state.getPenaltyUntil() != null) {
         text.append("\ud83d\udeab 当前处于逾期限制期\n")
            .append("⏳ 剩余：")
            .append(this.foamBagService.formatRemaining(state.getPenaltyUntil()))
            .append("\n")
            .append("\ud83d\udcc5 恢复时间：")
            .append(state.getPenaltyUntil().format(FOAM_BAG_TIME_FORMATTER))
            .append("\n\n")
            .append("\ud83d\udccc 限制期间无法签到、使用雾袋或接收他人转赠积分。\n")
            .append("\ud83d\udcac 发言积分仍可正常获得。");
         keyboard.add(List.of(new TelegramBotApiClient.InlineButton("\ud83d\udd04 刷新剩余时间", "foam_bag:refresh")));
      } else if (state.getActiveBag() != null) {
         PointsBotFoamBag active = state.getActiveBag();
         text.append("\ud83d\udcb0 当前积分：")
            .append(state.getPoints())
            .append("\n")
            .append("\ud83c\udf01 已袋积分：")
            .append(active.getPrincipalPoints())
            .append("\n")
            .append("\ud83d\udd01 应归还：")
            .append(active.getRepaymentPoints())
            .append("\n")
            .append("⏰ 最晚归还：")
            .append(active.getDueAt().format(FOAM_BAG_TIME_FORMATTER))
            .append("\n")
            .append("⏳ 剩余：")
            .append(this.foamBagService.formatRemaining(active.getDueAt()))
            .append("\n\n")
            .append("\ud83d\udccc 必须一次归还全部积分，归还后才能再次使用雾袋。");
         keyboard.add(List.of(new TelegramBotApiClient.InlineButton("\ud83d\udcb8 归还 " + active.getRepaymentPoints() + " 积分", "foam_bag:repay")));
         keyboard.add(List.of(new TelegramBotApiClient.InlineButton("\ud83d\udd04 刷新余额与时间", "foam_bag:refresh")));
      } else if (!this.isFoamBagEnabled()) {
         text.append("⏸️ 当前渠道已关闭雾袋，新申请已暂停。\n").append("\ud83d\udccc 已有记录和逾期限制不会受开关影响；若关闭前存在待归还记录，仍可通过原面板完成归还。");
      } else {
         text.append("\ud83d\udcb0 当前积分：")
            .append(state.getPoints())
            .append("\n")
            .append("\ud83d\udcc5 今日已用：")
            .append(state.getUsedToday())
            .append("/")
            .append(config.getDailyLimit())
            .append(" 次\n\n")
            .append("\ud83d\udccb 雾袋规则\n")
            .append("\ud83c\udf81 选择一个积分档位，积分立即到账。\n")
            .append("⏰ 请在 ")
            .append(config.getRepaymentHours())
            .append(" 小时内归还 ")
            .append(config.getRepaymentMultiplier())
            .append(" 倍积分。\n")
            .append("\ud83d\udd01 必须归还后才能继续袋；每日最多 ")
            .append(config.getDailyLimit())
            .append(" 次。\n")
            .append("⚠️ 逾期会将积分归零，并限制 ")
            .append(config.getPenaltyDays())
            .append(" 天无法签到、使用雾袋或接收他人转赠积分。\n")
            .append("\ud83d\udcac 发言积分不受影响。");
         List<TelegramBotApiClient.InlineButton> tierRow = new ArrayList<>();

         for (Integer amount : config.getAmountTiers()) {
            tierRow.add(new TelegramBotApiClient.InlineButton("\ud83c\udf01 袋 " + amount + " 积分", "foam_bag:select:" + amount));
            if (tierRow.size() == 3) {
               keyboard.add(List.copyOf(tierRow));
               tierRow.clear();
            }
         }

         if (!tierRow.isEmpty()) {
            keyboard.add(List.copyOf(tierRow));
         }
      }

      if (messageId == null) {
         this.botApiClient.sendMessage(chatId, text.toString(), null, null, TelegramBotApiClient.inlineKeyboard(keyboard), false);
      } else {
         this.editFoamBagPanel(chatId, messageId, text.toString(), keyboard);
      }
   }

   private void ensureFoamBagApplicationEnabled() {
      if (!this.isFoamBagEnabled()) {
         throw new BizException("当前渠道已关闭雾袋，新申请已暂停");
      }
   }

   private void editFoamBagPanel(long chatId, long messageId, String text, List<List<TelegramBotApiClient.InlineButton>> keyboard) {
      try {
         this.botApiClient.editMessageText(chatId, messageId, text, null, TelegramBotApiClient.inlineKeyboard(keyboard));
      } catch (TelegramBotApiClient.TelegramBotApiException var8) {
         if (!isMessageNotModifiedError(var8)) {
            throw var8;
         }
      }
   }

   static boolean isMessageNotModifiedError(Throwable error) {
      for (Throwable current = error; current != null; current = current.getCause()) {
         String message = current.getMessage();
         if (StringUtils.hasText(message)) {
            String normalized = message.toLowerCase(Locale.ROOT).replace('_', ' ');
            if (normalized.contains("message is not modified") || normalized.contains("message not modified")) {
               return true;
            }
         }
      }

      return false;
   }

   private void answerFoamBagCallback(CallbackQuery callback, String text, boolean alert) {
      try {
         if (StringUtils.hasText(text)) {
            this.botApiClient.answerCallbackQuery(callback.getId(), text, alert);
         } else {
            this.botApiClient.answerCallbackQuery(callback.getId());
         }
      } catch (TelegramBotApiClient.TelegramBotApiException var5) {
         log.debug("雾袋按钮应答失败: {}", var5.getMessage());
      }
   }

   private void handleRedPacket(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "\ud83e\udde7 群积分红包只能在积分群聊中发送。");
      } else if (!this.isRedPacketEnabled()) {
         this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⏸️ 当前渠道已关闭群积分红包，请联系管理员开启。", 15);
      } else {
         String[] parts = message.getText().trim().split("\\s+", 4);
         if (parts.length < 3) {
            this.sendReplyAndDeleteBoth(
               chatId,
               message.getMessageId(),
               "\ud83e\udde7 积分红包用法\n\n\ud83d\udccc /redpacket 总积分 红包个数 [祝福语]\n\ud83d\udcdd 示例：/redpacket 1000 100 恭喜发财\n\ud83d\udcb0 总积分上限：1000\n\ud83d\udc65 红包个数上限：100\n⚠️ 每个红包至少需要 1 积分",
               20
            );
         } else {
            int totalPoints;
            int totalCount;
            try {
               totalPoints = Integer.parseInt(parts[1]);
               totalCount = Integer.parseInt(parts[2]);
               this.redPacketService.validateParameters(totalPoints, totalCount);
            } catch (NumberFormatException var17) {
               this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "❌ 红包总积分和红包个数必须是整数。\n\ud83d\udccc 示例：/redpacket 1000 100 恭喜发财", 15);
               return;
            } catch (BizException var18) {
               this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⚠️ " + var18.getMessage(), 15);
               return;
            }

            User user = message.getFrom();
            if (user != null && !Boolean.TRUE.equals(user.getIsBot())) {
               PointsProfile profile = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
               if (profile.getPoints() < (long)totalPoints) {
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⚠️ 积分不足，当前有 " + profile.getPoints() + " 积分，本次红包需要 " + totalPoints + " 积分。", 15);
               } else {
                  String greeting = parts.length >= 4 ? parts[3].trim() : "祝大家好运！";
                  String token = UUID.randomUUID().toString().replace("-", "");
                  int expireMinutes = Math.max(1, this.config.getRedPacketExpireMinutes());
                  List<List<TelegramBotApiClient.InlineButton>> keyboard = List.of(
                     List.of(
                        new TelegramBotApiClient.InlineButton("✅ 确认发送", "red_packet:confirm:" + token),
                        new TelegramBotApiClient.InlineButton("❌ 取消", "red_packet:cancel:" + token)
                     )
                  );
                  String confirmationText = "\ud83e\udde7 <b>确认发送积分红包</b>\n\n\ud83d\udc64 发起人："
                     + this.redPacketUserMention(user.getId(), user.getUserName(), this.displayName(user))
                     + "\n\ud83d\udcb0 红包积分："
                     + totalPoints
                     + "\n\ud83d\udc65 红包个数："
                     + totalCount
                     + "\n⏰ 过期时间："
                     + this.formatRedPacketDuration(expireMinutes)
                     + "\n\ud83c\udf8a 祝福语："
                     + escapeTelegramHtml(greeting)
                     + "\n\n⚠️ 确认后会立即从你的积分余额中扣除 "
                     + totalPoints
                     + " 积分。";

                  try {
                     TelegramBotApiClient.ApiMessage sent = this.botApiClient
                        .sendMessage(chatId, confirmationText, message.getMessageId(), "HTML", TelegramBotApiClient.inlineKeyboard(keyboard), false);
                     PointsBot.PendingRedPacketConfirmation pending = new PointsBot.PendingRedPacketConfirmation(
                        chatId,
                        sent.getMessageId(),
                        user.getId(),
                        user.getUserName(),
                        this.displayName(user),
                        totalPoints,
                        totalCount,
                        greeting,
                        expireMinutes,
                        System.currentTimeMillis() + RED_PACKET_CONFIRMATION_TTL.toMillis()
                     );
                     this.pendingRedPacketConfirmations.put(token, pending);
                     this.scheduleRedPacketConfirmationExpiry(token, pending);
                     this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
                  } catch (TelegramBotApiClient.TelegramBotApiException var16) {
                     log.warn("发送积分红包确认面板失败: chatId={}, error={}", chatId, var16.getMessage());
                     this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "❌ 红包确认面板发送失败，请稍后重试。", 15);
                  }
               }
            } else {
               this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "❌ 机器人账号不能发送积分红包。", 15);
            }
         }
      }
   }

   private void scheduleRedPacketConfirmationExpiry(String token, PointsBot.PendingRedPacketConfirmation pending) {
      try {
         this.sharedScheduler.schedule(() -> {
            if (this.pendingRedPacketConfirmations.remove(token, pending)) {
               this.editRedPacketMessage(pending.chatId(), pending.messageId(), "⏰ <b>红包确认已过期</b>\n\n\ud83d\udccc 请重新发送 /redpacket 命令。", null);
            }
         }, RED_PACKET_CONFIRMATION_TTL.toMillis(), TimeUnit.MILLISECONDS);
      } catch (RuntimeException var4) {
         log.debug("\ud83e\udde7 注册红包确认清理任务失败，将在用户点击时校验有效期: {}", var4.getMessage());
      }
   }

   private void handleRedPacketCallback(Update update) {
      CallbackQuery callback = update.getCallbackQuery();
      Message callbackMessage = callback.getMessage();
      User user = callback.getFrom();
      String[] parts = callback.getData().split(":", 3);
      if (callbackMessage != null && user != null && parts.length == 3) {
         try {
            String e = parts[1];
            switch (e) {
               case "confirm":
               case "cancel":
                  this.handleRedPacketConfirmationCallback(callback, callbackMessage, user, parts[1], parts[2]);
                  break;
               case "claim":
                  this.handleRedPacketClaimCallback(callback, callbackMessage, user, Long.parseLong(parts[2]));
                  break;
               default:
                  this.answerRedPacketCallback(callback, "❌ 这个红包按钮已经失效。", true);
            }
         } catch (NumberFormatException var8) {
            this.answerRedPacketCallback(callback, "❌ 红包数据格式错误。", true);
         } catch (BizException var9) {
            this.answerRedPacketCallback(callback, "⚠️ " + var9.getMessage(), true);
         } catch (RuntimeException var10) {
            log.error("积分红包按钮处理失败: userId={}, data={}", user.getId(), callback.getData(), var10);
            this.answerRedPacketCallback(callback, "❌ 红包操作失败，请稍后重试。", true);
         }
      } else {
         this.answerRedPacketCallback(callback, "❌ 这个红包按钮已经失效。", true);
      }
   }

   private void handleRedPacketConfirmationCallback(CallbackQuery callback, Message callbackMessage, User user, String action, String token) {
      PointsBot.PendingRedPacketConfirmation pending = this.pendingRedPacketConfirmations.get(token);
      if (pending == null || pending.chatId() != callbackMessage.getChatId() || pending.messageId() != callbackMessage.getMessageId()) {
         this.answerRedPacketCallback(callback, "⏰ 红包确认已经失效，请重新发送命令。", true);
      } else if (pending.userId() != user.getId()) {
         this.answerRedPacketCallback(callback, "\ud83d\udeab 只有红包发起人可以确认或取消。", true);
      } else if (pending.expired()) {
         this.pendingRedPacketConfirmations.remove(token, pending);
         this.editRedPacketMessage(callbackMessage.getChatId(), callbackMessage.getMessageId(), "⏰ <b>红包确认已过期</b>\n\n\ud83d\udccc 请重新发送 /redpacket 命令。", null);
         this.answerRedPacketCallback(callback, "⏰ 红包确认已过期。", true);
      } else if (!this.pendingRedPacketConfirmations.remove(token, pending)) {
         this.answerRedPacketCallback(callback, "\ud83d\udd01 这个红包确认已经处理过了。", true);
      } else if ("cancel".equals(action)) {
         this.editRedPacketMessage(callbackMessage.getChatId(), callbackMessage.getMessageId(), "❌ <b>积分红包已取消</b>\n\n✅ 未扣除任何积分。", null);
         this.answerRedPacketCallback(callback, "❌ 已取消发送红包。", false);
      } else if (!this.isRedPacketEnabled()) {
         this.editRedPacketMessage(callbackMessage.getChatId(), callbackMessage.getMessageId(), "⏸️ <b>群积分红包已关闭</b>\n\n✅ 未扣除任何积分。", null);
         this.answerRedPacketCallback(callback, "⏸️ 当前渠道已关闭群积分红包。", true);
      } else {
         PointsBotRedPacket redPacket = null;
         Long publicMessageId = null;

         try {
            LocalDateTime expiresAt = LocalDateTime.now(PointsBotRedPacketService.BUSINESS_ZONE).plusMinutes((long)pending.expireMinutes());
            redPacket = this.redPacketService
               .create(
                  pending.chatId(),
                  pending.userId(),
                  pending.username(),
                  pending.displayName(),
                  pending.totalPoints(),
                  pending.totalCount(),
                  pending.greeting(),
                  expiresAt
               );
            TelegramBotApiClient.ApiMessage publicMessage = this.botApiClient
               .sendMessage(pending.chatId(), this.renderRedPacketPanel(redPacket, 0, true), null, "HTML", this.buildRedPacketKeyboard(redPacket, true), false);
            publicMessageId = publicMessage.getMessageId();
            redPacket = this.redPacketService.publish(redPacket.getId(), publicMessageId);

            try {
               this.scheduleRedPacketExpiry(redPacket);
            } catch (RuntimeException var12) {
               log.error("\ud83e\udde7 红包已发布但到期任务注册失败，将由恢复扫描补偿: redPacketId={}", redPacket.getId(), var12);
            }

            this.editRedPacketMessage(
               callbackMessage.getChatId(),
               callbackMessage.getMessageId(),
               "✅ <b>积分红包发送成功</b>\n\n\ud83e\udde7 红包积分："
                  + redPacket.getTotalPoints()
                  + "\n\ud83d\udc65 红包个数："
                  + redPacket.getTotalCount()
                  + "\n⏰ 到期时间："
                  + redPacket.getExpiresAt().format(RED_PACKET_TIME_FORMATTER),
               null
            );
            this.answerRedPacketCallback(callback, "✅ 红包已经发出！", false);
         } catch (RuntimeException var14) {
            boolean refundConfirmed = redPacket == null;
            if (redPacket != null) {
               try {
                  RedPacketExpireResult refund = this.redPacketService.cancelPublishing(redPacket.getId());
                  refundConfirmed = refund.changed() || refund.redPacket() != null && "CANCELLED".equals(refund.redPacket().getStatus());
               } catch (RuntimeException var13) {
                  var14.addSuppressed(var13);
               }
            }

            if (publicMessageId != null) {
               this.editRedPacketMessage(
                  pending.chatId(),
                  publicMessageId,
                  refundConfirmed ? "❌ <b>红包发布失败</b>\n\n↩️ 托管积分已退回发起人。" : "⚠️ <b>红包发布异常</b>\n\n\ud83d\udee0️ 退款尚未确认，请联系管理员核查。",
                  null
               );
            }

            this.editRedPacketMessage(
               callbackMessage.getChatId(),
               callbackMessage.getMessageId(),
               refundConfirmed ? "❌ <b>积分红包发送失败</b>\n\n↩️ 如已扣除积分，系统已自动退回。" : "⚠️ <b>积分红包发送异常</b>\n\n\ud83d\udee0️ 退款尚未确认，请联系管理员核查。",
               null
            );
            throw var14;
         }
      }
   }

   private void handleRedPacketClaimCallback(CallbackQuery callback, Message callbackMessage, User user, long redPacketId) {
      if (this.isGroupChat(callbackMessage) && !Boolean.TRUE.equals(user.getIsBot())) {
         RedPacketClaimResult result = this.redPacketService
            .claim(
               redPacketId,
               callbackMessage.getChatId(),
               callbackMessage.getMessageId(),
               user.getId(),
               user.getUserName(),
               this.displayName(user),
               LocalDateTime.now(PointsBotRedPacketService.BUSINESS_ZONE)
            );
         switch (result.status()) {
            case CLAIMED:
               this.answerRedPacketCallback(callback, "\ud83c\udf89 恭喜抢到 " + result.points() + " 积分！", false);
               if ("FINISHED".equals(result.redPacket().getStatus())) {
                  this.cancelRedPacketExpiry(redPacketId);
                  this.refreshRedPacketPanel(redPacketId);
               } else {
                  this.requestRedPacketPanelRefresh(redPacketId);
               }
               break;
            case ALREADY_CLAIMED:
               this.answerRedPacketCallback(callback, "\ud83d\udd01 你已经领取过了，本次抢到 " + result.points() + " 积分。", true);
               break;
            case FINISHED:
               this.cancelRedPacketExpiry(redPacketId);
               this.refreshRedPacketPanel(redPacketId);
               this.answerRedPacketCallback(callback, "\ud83c\udfc1 手慢了，红包已经抢完。", true);
               break;
            case EXPIRED:
               this.cancelRedPacketExpiry(redPacketId);
               this.refreshRedPacketPanel(redPacketId);
               this.answerRedPacketCallback(callback, "⏰ 红包已过期，剩余积分已退回。", true);
               break;
            case CANCELLED:
               this.answerRedPacketCallback(callback, "❌ 红包已经取消，积分已退回发起人。", true);
               break;
            case NOT_OPEN:
               this.answerRedPacketCallback(callback, "⏳ 红包正在打开，请稍后再抢。", true);
               break;
            case WRONG_MESSAGE:
               this.answerRedPacketCallback(callback, "\ud83d\udeab 红包与当前群聊消息不匹配。", true);
               break;
            case NOT_FOUND:
               this.answerRedPacketCallback(callback, "❌ 红包不存在或已经失效。", true);
         }
      } else {
         this.answerRedPacketCallback(callback, "\ud83d\udeab 只有群内真实用户可以领取红包。", true);
      }
   }

   private void scheduleRedPacketExpiry(PointsBotRedPacket redPacket) {
      if (redPacket != null && redPacket.getId() != null && "OPEN".equals(redPacket.getStatus()) && redPacket.getExpiresAt() != null) {
         long delayMillis = Math.max(100L, Duration.between(LocalDateTime.now(PointsBotRedPacketService.BUSINESS_ZONE), redPacket.getExpiresAt()).toMillis());
         AtomicReference<ScheduledFuture<?>> futureReference = new AtomicReference<>();
         ScheduledFuture<?> future = this.sharedScheduler.schedule(() -> {
            this.redPacketExpiryTasks.remove(redPacket.getId(), futureReference.get());
            this.expireRedPacketAndRefresh(redPacket.getId());
         }, delayMillis, TimeUnit.MILLISECONDS);
         futureReference.set(future);
         ScheduledFuture<?> previous = this.redPacketExpiryTasks.put(redPacket.getId(), future);
         if (previous != null && previous != future) {
            previous.cancel(false);
         }

         log.debug("\ud83e\udde7 红包到期任务已启动: redPacketId={}, delayMillis={}", redPacket.getId(), delayMillis);
      }
   }

   private void expireRedPacketAndRefresh(long redPacketId) {
      try {
         RedPacketExpireResult result = this.redPacketService.expire(redPacketId, LocalDateTime.now(PointsBotRedPacketService.BUSINESS_ZONE));
         if (result.redPacket() != null && "OPEN".equals(result.redPacket().getStatus())) {
            this.scheduleRedPacketExpiry(result.redPacket());
            return;
         }

         if (result.redPacket() != null) {
            this.refreshRedPacketPanel(redPacketId);
            if (result.changed() && result.refundedPoints() > 0) {
               this.sendRedPacketRefundNotice(result.redPacket(), result.refundedPoints());
            }
         }
      } catch (RuntimeException var4) {
         log.error("\ud83e\udde7 红包到期结算失败: redPacketId={}", redPacketId, var4);
      }
   }

   public void recoverRedPacketTasks() {
      try {
         LocalDateTime now = LocalDateTime.now(PointsBotRedPacketService.BUSINESS_ZONE);

         for (Long redPacketId : this.redPacketService.findDuePacketIds(now, 200)) {
            this.cancelRedPacketExpiry(redPacketId);
            this.expireRedPacketAndRefresh(redPacketId);
         }

         for (PointsBotRedPacket redPacket : this.redPacketService.listOpenPackets(500)) {
            if (!this.redPacketExpiryTasks.containsKey(redPacket.getId())) {
               this.scheduleRedPacketExpiry(redPacket);
            }

            if (this.botReady() && redPacket.getPanelSyncedAt() == null) {
               this.refreshRedPacketPanel(redPacket.getId());
            }
         }

         if (this.botReady()) {
            for (PointsBotRedPacket redPacket : this.redPacketService.listUnsyncedTerminalPackets(100)) {
               this.refreshRedPacketPanel(redPacket.getId());
            }
         }
      } catch (RuntimeException var4) {
         log.error("\ud83e\udde7 恢复红包到期任务失败", (Throwable)var4);
      }
   }

   private void cancelRedPacketExpiry(long redPacketId) {
      ScheduledFuture<?> future = this.redPacketExpiryTasks.remove(redPacketId);
      if (future != null) {
         future.cancel(false);
         log.debug("\ud83e\udde7 红包到期任务已移除: redPacketId={}", redPacketId);
      }
   }

   private void stopAllRedPacketTasks() {
      for (ScheduledFuture<?> future : this.redPacketExpiryTasks.values()) {
         if (future != null) {
            future.cancel(false);
         }
      }

      this.redPacketExpiryTasks.clear();
      this.pendingRedPacketPanelRefreshes.clear();
      this.pendingRedPacketConfirmations.clear();
   }

   private void requestRedPacketPanelRefresh(long redPacketId) {
      if (this.pendingRedPacketPanelRefreshes.add(redPacketId)) {
         this.sharedScheduler.schedule(() -> {
            this.pendingRedPacketPanelRefreshes.remove(redPacketId);
            this.refreshRedPacketPanel(redPacketId);
         }, 500L, TimeUnit.MILLISECONDS);
      }
   }

   private void refreshRedPacketPanel(long redPacketId) {
      PointsBotRedPacketService.RedPacketView view = this.redPacketService.getView(redPacketId);
      if (view != null && view.redPacket().getMessageId() != null && this.botReady()) {
         PointsBotRedPacket redPacket = view.redPacket();
         List<PointsBotRedPacketClaim> claims = this.isTerminalRedPacketStatus(redPacket.getStatus())
            ? this.redPacketService.listClaims(redPacketId)
            : List.of();

         try {
            this.botApiClient
               .editMessageText(
                  redPacket.getChatId(),
                  redPacket.getMessageId(),
                  this.renderRedPacketPanel(redPacket, view.claimedCount(), false, claims),
                  "HTML",
                  this.buildRedPacketKeyboard(redPacket, false)
               );
            this.redPacketService.markPanelSynced(redPacketId);
         } catch (TelegramBotApiClient.TelegramBotApiException var7) {
            if (!isMessageNotModifiedError(var7)) {
               log.warn("\ud83e\udde7 更新红包面板失败: redPacketId={}, error={}", redPacketId, var7.getMessage());
            } else {
               this.redPacketService.markPanelSynced(redPacketId);
            }
         }
      }
   }

   private String renderRedPacketPanel(PointsBotRedPacket redPacket, int claimedCount, boolean forceOpen) {
      return this.renderRedPacketPanel(redPacket, claimedCount, forceOpen, List.of());
   }

   private String renderRedPacketPanel(PointsBotRedPacket redPacket, int claimedCount, boolean forceOpen, List<PointsBotRedPacketClaim> claims) {
      String status = forceOpen ? "OPEN" : redPacket.getStatus();
      int totalPoints = redPacket.getTotalPoints() == null ? 0 : redPacket.getTotalPoints();
      if (redPacket.getTotalCount() == null) {
         boolean var10000 = false;
      } else {
         redPacket.getTotalCount();
      }

      int remainingCount = redPacket.getRemainingCount() == null ? 0 : redPacket.getRemainingCount();
      int refundedPoints = redPacket.getRefundedPoints() == null ? 0 : redPacket.getRefundedPoints();
      StringBuilder text = new StringBuilder();
      if ("FINISHED".equals(status)) {
         text.append("\ud83c\udf8a <b>红包已抢完</b>\n\n");
      } else if ("EXPIRED".equals(status)) {
         text.append("⏰ <b>红包已过期</b>\n\n");
      } else if ("CANCELLED".equals(status)) {
         text.append("❌ <b>红包已取消</b>\n\n");
      } else {
         text.append("\ud83e\udde7 <b>积分红包</b>\n\n");
      }

      text.append("\ud83d\udc64 发起人：")
         .append(this.redPacketUserMention(redPacket.getCreatorUserId(), redPacket.getCreatorUsername(), redPacket.getCreatorDisplayName()))
         .append("\n")
         .append("\ud83d\udcb0 红包积分：")
         .append(totalPoints)
         .append("\n")
         .append("\ud83d\udc65 已领取：")
         .append(Math.max(0, claimedCount))
         .append(" 人\n")
         .append("\ud83e\udde7 剩余红包：")
         .append(Math.max(0, remainingCount))
         .append(" 个\n");
      if ("EXPIRED".equals(status) || "CANCELLED".equals(status)) {
         text.append("↩️ 已退回积分：").append(refundedPoints).append("\n").append("✅ 剩余积分已退回发起人\n");
      } else if ("FINISHED".equals(status)) {
         text.append("\ud83c\udfc1 红包已结束\n");
      } else {
         text.append("⏰ 过期时间：").append(redPacket.getExpiresAt().format(RED_PACKET_TIME_FORMATTER)).append("\n");
      }

      if (StringUtils.hasText(redPacket.getGreeting())) {
         text.append("\n\ud83c\udf8a ").append(escapeTelegramHtml(redPacket.getGreeting()));
      }

      if (this.isTerminalRedPacketStatus(status) && claims != null && !claims.isEmpty()) {
         text.append("\n\n\ud83d\udccb <b>领取记录</b>\n");
         int index = 1;

         for (PointsBotRedPacketClaim claim : claims) {
            if (index > 100) {
               break;
            }

            int claimedPoints = claim.getPoints() == null ? 0 : Math.max(0, claim.getPoints());
            text.append(index).append(". ").append(this.redPacketClaimUserMention(claim)).append("：").append(claimedPoints).append(" 积分\n");
            index++;
         }
      }

      return text.toString();
   }

   private boolean isTerminalRedPacketStatus(String status) {
      return "FINISHED".equals(status) || "EXPIRED".equals(status) || "CANCELLED".equals(status);
   }

   private JSONObject buildRedPacketKeyboard(PointsBotRedPacket redPacket, boolean forceOpen) {
      boolean open = forceOpen || "OPEN".equals(redPacket.getStatus());
      return open && redPacket.getRemainingCount() != null && redPacket.getRemainingCount() > 0
         ? TelegramBotApiClient.inlineKeyboard(
            List.of(List.of(new TelegramBotApiClient.InlineButton("\ud83e\udde7 抢红包", "red_packet:claim:" + redPacket.getId())))
         )
         : null;
   }

   private String redPacketUserMention(Long userId, String username, String displayName) {
      return this.redPacketUserMention(userId, username, displayName, Integer.MAX_VALUE);
   }

   private String redPacketClaimUserMention(PointsBotRedPacketClaim claim) {
      return this.redPacketUserMention(claim.getUserId(), claim.getUsername(), claim.getDisplayName(), 20);
   }

   private String redPacketUserMention(Long userId, String username, String displayName, int maxLabelLength) {
      String label = StringUtils.hasText(username) ? "@" + username.replaceFirst("^@", "") : (StringUtils.hasText(displayName) ? displayName : "用户" + userId);
      label = this.truncateTelegramLabel(label, maxLabelLength);
      return userId == null ? escapeTelegramHtml(label) : "<a href=\"tg://user?id=" + userId + "\">" + escapeTelegramHtml(label) + "</a>";
   }

   private String truncateTelegramLabel(String label, int maxLength) {
      if (label != null && maxLength > 0 && label.length() > maxLength) {
         int endIndex = maxLength - 1;
         if (endIndex > 0 && Character.isHighSurrogate(label.charAt(endIndex - 1)) && Character.isLowSurrogate(label.charAt(endIndex))) {
            endIndex--;
         }

         return label.substring(0, endIndex) + "…";
      } else {
         return label == null ? "" : label;
      }
   }

   private String formatRedPacketDuration(int minutes) {
      if (minutes % 1440 == 0) {
         return minutes / 1440 + " 天";
      } else {
         return minutes % 60 == 0 ? minutes / 60 + " 小时" : minutes + " 分钟";
      }
   }

   private void sendRedPacketRefundNotice(PointsBotRedPacket redPacket, int refundedPoints) {
      if (this.botReady() && redPacket.getCreatorUserId() != null) {
         try {
            this.botApiClient
               .sendMessage(
                  redPacket.getCreatorUserId(),
                  "↩️ <b>红包剩余积分已退回</b>\n\n\ud83e\udde7 红包编号：" + redPacket.getId() + "\n\ud83d\udcb0 退回积分：" + refundedPoints + "\n✅ 积分已经回到你的账户。",
                  null,
                  "HTML",
                  null,
                  false
               );
         } catch (TelegramBotApiClient.TelegramBotApiException var4) {
            log.debug("\ud83e\udde7 发送红包退款私聊通知失败: redPacketId={}, error={}", redPacket.getId(), var4.getMessage());
         }
      }
   }

   private void editRedPacketMessage(long chatId, long messageId, String text, JSONObject keyboard) {
      try {
         this.botApiClient.editMessageText(chatId, messageId, text, "HTML", keyboard);
      } catch (TelegramBotApiClient.TelegramBotApiException var8) {
         if (!isMessageNotModifiedError(var8)) {
            log.debug("\ud83e\udde7 编辑红包消息失败: chatId={}, messageId={}, error={}", chatId, messageId, var8.getMessage());
         }
      }
   }

   private void answerRedPacketCallback(CallbackQuery callback, String text, boolean alert) {
      try {
         this.botApiClient.answerCallbackQuery(callback.getId(), text, alert);
      } catch (TelegramBotApiClient.TelegramBotApiException var5) {
         log.debug("\ud83e\udde7 红包按钮应答失败: {}", var5.getMessage());
      }
   }

   private void handlePrizes(Message message) {
      long chatId = message.getChatId();
      List<PointsBotPrizeConfig> prizes = this.pointsBotPrizeConfigService.listAvailablePrizes();
      if (prizes.isEmpty()) {
         this.sendMessage(chatId, "暂无可兑换的奖品。");
      } else {
         StringBuilder builder = new StringBuilder("\ud83c\udf01 Mist 积分奖品\n\n");
         int index = 1;

         for (PointsBotPrizeConfig prize : prizes) {
            String levelName = null;
            if (prize.getLevelId() != null) {
               PointsBotLevelConfig level = this.pointsBotLevelConfigService.getById(prize.getLevelId());
               if (level != null) {
                  levelName = level.getLevelName();
               }
            }

            builder.append(index++)
               .append(". ")
               .append(prize.getPrizeName())
               .append(" | 需积分：")
               .append(prize.getRequiredPoints() == null ? "-" : prize.getRequiredPoints())
               .append(" | 剩余：")
               .append(prize.getRemainingQuantity() == null ? 0 : prize.getRemainingQuantity());
            if (levelName != null) {
               builder.append(" | 等级：").append(levelName);
            }

            builder.append("\n");
         }

         builder.append("\n使用 /lottery publish <序号> <标题> 来发起抽奖");
         this.sendMessage(chatId, builder.toString());
      }
   }

   private void handleExchange(Message message) {
      long chatId = message.getChatId();
      if (this.isGroupChat(message)) {
         this.sendMessage(chatId, "\ud83c\udf01 积分兑换请在私聊服务面板中完成，账号和积分会更安静地被守护。");
      } else {
         List<PointsBotRedeemConfig> configs = this.pointsBotRedeemConfigService
            .lambdaQuery()
            .eq(PointsBotRedeemConfig::getEnabled, Integer.valueOf(1))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .orderByDesc(PointsBotRedeemConfig::getSort)
            .orderByDesc(PointsBotRedeemConfig::getId)
            .list();
         if (configs.isEmpty()) {
            this.sendMessage(chatId, "暂无可兑换的项目。");
         } else {
            StringBuilder builder = new StringBuilder("\ud83c\udf01 Mist 积分兑换\n\n");
            int validCount = 0;
            List<TelegramBotApiClient.InlineButton> actionButtons = new ArrayList<>();

            for (PointsBotRedeemConfig cfg : configs) {
               if (PointsBotRedeemTypeEnum.isSupported(cfg.getRedeemType()) && cfg.getRequiredPoints() != null && cfg.getRequiredPoints() > 0) {
                  String serverName = "未知服务器";
                  if (cfg.getEmbyInfoId() != null) {
                     EmbyInfo server = this.embyInfoService.getById(cfg.getEmbyInfoId());
                     if (server != null && StringUtils.hasText(server.getServerName())) {
                        serverName = server.getServerName();
                     }
                  }

                  builder.append("【").append(++validCount).append("】");
                  String configName = StringUtils.hasText(cfg.getConfigName()) ? cfg.getConfigName() : "Emby账号";
                  builder.append(configName);
                  builder.append("\n");
                  builder.append("   • 类型：").append(PointsBotRedeemTypeEnum.resolveLabel(cfg.getRedeemType())).append("\n");
                  builder.append("   • 天数：").append(cfg.getRedeemDays() == null ? "-" : cfg.getRedeemDays()).append("天\n");
                  builder.append("   • 需积分：").append(cfg.getRequiredPoints()).append("\n");
                  builder.append("   • 服务器：").append(serverName).append("\n");
                  if (StringUtils.hasText(cfg.getRemark())) {
                     builder.append("   • 备注：").append(cfg.getRemark()).append("\n");
                  }

                  builder.append("\n");
                  boolean createAccount = PointsBotRedeemTypeEnum.CREATE_ACCOUNT.matches(cfg.getRedeemType());
                  String callbackData = "points_exchange:" + (createAccount ? "create:" : "renew:") + cfg.getId();
                  String buttonText = (createAccount ? "\ud83d\udc51 " : "♻️ ")
                     + configName
                     + " · "
                     + (cfg.getRedeemDays() == null ? "-" : cfg.getRedeemDays())
                     + "天 · "
                     + cfg.getRequiredPoints()
                     + "积分";
                  actionButtons.add(new TelegramBotApiClient.InlineButton(buttonText, callbackData));
               }
            }

            if (validCount == 0) {
               this.sendMessage(chatId, "暂无可兑换的项目。");
            } else {
               builder.append("\ud83d\udc47 选择一个项目继续；注册时只需再输入用户名和可选密码，续费会先请你确认。");
               List<List<TelegramBotApiClient.InlineButton>> keyboard = new ArrayList<>();

               for (int index = 0; index < actionButtons.size(); index += 2) {
                  if (index + 1 < actionButtons.size()) {
                     keyboard.add(List.of(actionButtons.get(index), actionButtons.get(index + 1)));
                  } else {
                     keyboard.add(List.of(actionButtons.get(index)));
                  }
               }

               keyboard.add(
                  List.of(
                     new TelegramBotApiClient.InlineButton("\ud83e\ude99 查询积分", "points_exchange:points"),
                     new TelegramBotApiClient.InlineButton("↩️ 返回积分中心", "start_panel:points")
                  )
               );

               try {
                  this.botApiClient.sendMessage(chatId, builder.toString(), null, null, TelegramBotApiClient.inlineKeyboard(keyboard), false);
               } catch (TelegramBotApiClient.TelegramBotApiException var15) {
                  log.warn("发送积分兑换面板失败: chatId={}, error={}", chatId, var15.getMessage());
                  this.sendMessage(chatId, "积分兑换面板暂时没有浮上来，请稍后再试。");
               }
            }
         }
      }
   }

   private void handleLeaderboard(Message message) {
      long chatId = message.getChatId();
      List<PointsProfile> topList = this.pointsStore.leaderboard(chatId, this.config.getLeaderboardLimit());
      this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
      if (topList.isEmpty()) {
         this.sendReplyAndDelete(chatId, message.getMessageId(), "排行榜暂无数据。", 15);
      } else {
         String leaderboard = this.buildLeaderboard(topList);
         this.sendReplyAndDelete(chatId, message.getMessageId(), leaderboard, 15);
      }
   }

   private void handleLottery(Message message) {
      long chatId = message.getChatId();
      String[] parts = message.getText().trim().split("\\s+", 4);
      String action = parts.length > 1 ? parts[1].toLowerCase() : "";
      if (!StringUtils.hasText(action)) {
         StringBuilder usage = new StringBuilder("\ud83c\udfb0 抽奖命令用法\n\n");
         usage.append("【管理员命令】\n");
         usage.append("/lottery publish [人数] [时间]\n");
         usage.append("  └ 发布抽奖 (例如: /lottery 3 10m)\n");
         usage.append("/lottery draw\n");
         usage.append("  └ 立即开奖\n\n");
         usage.append("【用户命令】\n");
         usage.append("/lottery join [备注]\n");
         usage.append("  └ 参与当前抽奖\n");
         usage.append("/lottery status\n");
         usage.append("  └ 查看抽奖状态");
         if (this.isGroupChat(message)) {
            this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
            this.sendReplyAndDelete(chatId, message.getMessageId(), usage.toString(), 15);
         } else {
            this.sendMessage(chatId, usage.toString());
         }
      } else if (action.matches("\\d+")) {
         this.handleLotteryPublish(message, parts);
      } else {
         switch (action) {
            case "publish":
            case "create":
               this.handleLotteryPublish(message, parts);
               break;
            case "join":
            case "enter":
               this.handleLotteryJoin(message, parts.length > 2 ? parts[2].trim() : "");
               break;
            case "status":
               this.handleLotteryStatus(message);
               break;
            case "draw":
               this.handleLotteryDraw(message);
               break;
            default:
               String hint = "未知的抽奖操作。\n发送 /lottery 查看完整用法。";
               if (this.isGroupChat(message)) {
                  this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
                  this.sendReplyAndDelete(chatId, message.getMessageId(), hint, 15);
               } else {
                  this.sendMessage(chatId, hint);
               }
         }
      }
   }

   private void handleTransfer(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "积分互转请在群聊中使用。");
      } else {
         PointsBot.MentionTarget targetRef = this.resolveMentionTarget(message);
         if (targetRef == null) {
            this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "用法：/transfer @用户 积分\n请使用 @ 提及目标用户", 10);
         } else {
            String[] parts = message.getText().trim().split("\\s+");
            if (parts.length < 2) {
               this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "用法：/transfer @用户 积分", 10);
            } else {
               String amountText = parts[parts.length - 1];

               int amount;
               try {
                  amount = Integer.parseInt(amountText);
               } catch (NumberFormatException var14) {
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "积分数量不合法。", 10);
                  return;
               }

               if (amount <= 0) {
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "转账积分必须为大于0的整数。", 10);
               } else if (amount >= this.config.getTransferMinPoints() && amount <= this.config.getTransferMaxPoints()) {
                  PointsProfile target = this.resolveMentionedProfile(chatId, targetRef);
                  if (target == null) {
                     this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "未找到目标用户，请确认对方在本群发过言。", 10);
                  } else {
                     User user = message.getFrom();
                     PointsProfile sender = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
                     if (sender.getUserId() == target.getUserId()) {
                        this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "不能给自己转账。", 10);
                     } else {
                        boolean isAdmin = this.isAdminUser(user, TelegramBotPermission.POINTS_ADMIN);

                        try {
                           this.foamBagService.transfer(sender, target, amount, isAdmin, "transfer_out", "transfer_in", false);
                        } catch (BizException var13) {
                           this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), var13.getMessage(), 10);
                           return;
                        }

                        StringBuilder transferMsg = new StringBuilder();
                        transferMsg.append("\ud83d\udcb8 转账成功\n\n");
                        transferMsg.append("\ud83d\udce4 转出方：").append(this.formatUser(sender)).append("\n");
                        transferMsg.append("\ud83d\udce5 接收方：").append(this.formatUser(target)).append("\n");
                        transferMsg.append("\ud83d\udcb0 转账金额：").append(amount).append(" 积分\n\n");
                        if (!isAdmin) {
                           transferMsg.append("\ud83d\udc64 ").append(this.formatUser(sender)).append(" 剩余积分：").append(sender.getPoints()).append("\n");
                        }

                        transferMsg.append("\ud83d\udc64 ").append(this.formatUser(target)).append(" 剩余积分：").append(target.getPoints());
                        this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), transferMsg.toString(), 10);
                     }
                  }
               } else {
                  this.sendReplyAndDeleteBoth(
                     chatId, message.getMessageId(), String.format("转账积分范围：%d-%d。", this.config.getTransferMinPoints(), this.config.getTransferMaxPoints()), 10
                  );
               }
            }
         }
      }
   }

   private void handleRedeem(Message message) {
      long chatId = message.getChatId();
      if (this.isGroupChat(message)) {
         this.sendMessage(chatId, "积分注册请在私聊中操作，避免密码泄露。");
      } else {
         Long pointsChatId = this.resolvePointsChatId(message);
         if (pointsChatId == null) {
            this.sendMessage(chatId, "未配置积分群/频道，请先配置 Telegram 机器人的积分群/频道 Chat ID。");
         } else {
            User user = message.getFrom();
            long userId = user.getId();
            String text = message.getText().trim();
            if (text.startsWith("/redeem")) {
               String argsText = text.substring("/redeem".length()).trim();
               String[] parts = argsText.split("\\s+", 3);
               if (parts.length >= 2 && StringUtils.hasText(parts[0]) && StringUtils.hasText(parts[1])) {
                  long configId;
                  try {
                     configId = Long.parseLong(parts[0].trim());
                  } catch (NumberFormatException var27) {
                     this.sendMessage(chatId, "配置编号格式错误，请输入数字。\n发送 /exchange 查看可兑换项目及编号");
                     return;
                  }

                  String embyUserName = parts[1].trim();
                  String embyUserPassword = parts.length >= 3 ? parts[2].trim() : null;
                  if (!StringUtils.hasText(embyUserName)) {
                     this.sendMessage(chatId, "Emby用户名不能为空。");
                  } else if (!StringUtils.hasText(embyUserPassword) || embyUserPassword.length() >= 6 && embyUserPassword.length() <= 30) {
                     PointsBotRedeemConfig redeemConfig = this.pointsBotRedeemConfigService
                        .lambdaQuery()
                        .eq(PointsBotRedeemConfig::getId, Long.valueOf(configId))
                        .eq(PointsBotRedeemConfig::getEnabled, Integer.valueOf(1))
                        .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                        .one();
                     if (redeemConfig == null) {
                        this.sendMessage(chatId, "未找到该兑换配置或配置已停用。\n发送 /exchange 查看可兑换项目");
                     } else if (!PointsBotRedeemTypeEnum.CREATE_ACCOUNT.matches(redeemConfig.getRedeemType())) {
                        this.sendMessage(chatId, "该兑换配置仅用于续费，不能注册账号。\n请发送 /exchange 选择“注册账号”类型的配置");
                     } else if (redeemConfig.getRedeemDays() == null || redeemConfig.getRedeemDays() <= 0) {
                        this.sendMessage(chatId, "该兑换配置异常，请联系管理员。");
                     } else if (redeemConfig.getRequiredPoints() != null && redeemConfig.getRequiredPoints() > 0) {
                        String lockKey = this.redeemLockKey(pointsChatId, userId);
                        String lockToken = this.tryAcquireRedeemLock(lockKey, chatId);
                        if (StringUtils.hasText(lockToken)) {
                           try {
                              PointsProfile userProfile = this.pointsStore.findByUserId(pointsChatId, userId);
                              if (userProfile == null) {
                                 this.sendMessage(chatId, "未找到你的积分记录，请先在积分群中发言或签到。");
                                 return;
                              }

                              String userLabel = this.formatUser(userProfile);
                              int requiredPoints = redeemConfig.getRequiredPoints();
                              if (userProfile.getPoints() < (long)requiredPoints) {
                                 this.sendMessage(chatId, String.format("积分不足，当前 %d 积分，需要 %d 积分注册账号。", userProfile.getPoints(), requiredPoints));
                                 return;
                              }

                              EmbyInfo server = this.embyInfoService
                                 .lambdaQuery()
                                 .eq(EmbyInfo::getId, redeemConfig.getEmbyInfoId())
                                 .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
                                 .eq(EmbyInfo::getStatus, Integer.valueOf(0))
                                 .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                                 .one();
                              if (server != null) {
                                 String configName = StringUtils.hasText(redeemConfig.getConfigName()) ? redeemConfig.getConfigName() : "积分注册";
                                 String remarks = configName + "-" + userLabel;
                                 this.processRedeemCreation(
                                    chatId, userProfile, userLabel, userId, user.getUserName(), embyUserName, embyUserPassword, remarks, redeemConfig
                                 );
                                 return;
                              }

                              this.sendMessage(chatId, "该配置关联的服务器暂不可用，请联系管理员。");
                           } finally {
                              this.redisLockUtils.unlock(lockKey, lockToken);
                           }
                        }
                     } else {
                        this.sendMessage(chatId, "该兑换配置未设置所需积分，请联系管理员。");
                     }
                  } else {
                     this.sendMessage(chatId, "密码长度需为 6-30 位；不填写密码时系统会自动生成。");
                  }
               } else {
                  this.sendMessage(chatId, "用法：/redeem 配置编号 Emby用户名 [密码]\n示例：/redeem 1 myembyuser mypass123\n不填写密码时系统会自动生成 6 位随机密码\n发送 /exchange 查看可用的注册项目及编号");
               }
            }
         }
      }
   }

   private void handleRedeemRenew(Message message) {
      long chatId = message.getChatId();
      if (this.isGroupChat(message)) {
         this.sendMessage(chatId, "积分续费请在私聊中操作，避免信息泄露。");
      } else {
         Long pointsChatId = this.resolvePointsChatId(message);
         if (pointsChatId == null) {
            this.sendMessage(chatId, "未配置积分群/频道，请先配置 Telegram 机器人的积分群/频道 Chat ID。");
         } else {
            User user = message.getFrom();
            long userId = user.getId();
            String text = message.getText().trim();
            if (text.startsWith("/recharge")) {
               String argsText = text.substring("/recharge".length()).trim();
               String[] parts = argsText.split("\\s+");
               if (parts.length == 1 && StringUtils.hasText(parts[0])) {
                  long configId;
                  try {
                     configId = Long.parseLong(parts[0].trim());
                  } catch (NumberFormatException var32) {
                     this.sendMessage(chatId, "配置编号格式错误，请输入数字。\n发送 /exchange 查看可兑换项目及编号");
                     return;
                  }

                  PointsBotRedeemConfig redeemConfig = this.pointsBotRedeemConfigService
                     .lambdaQuery()
                     .eq(PointsBotRedeemConfig::getId, Long.valueOf(configId))
                     .eq(PointsBotRedeemConfig::getEnabled, Integer.valueOf(1))
                     .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                     .one();
                  if (redeemConfig == null) {
                     this.sendMessage(chatId, "未找到该兑换配置或配置已停用。\n发送 /exchange 查看可兑换项目");
                  } else if (!PointsBotRedeemTypeEnum.RENEW.matches(redeemConfig.getRedeemType())) {
                     this.sendMessage(chatId, "该兑换配置仅用于注册账号，不能续费。\n请发送 /exchange 选择“续费”类型的配置");
                  } else if (redeemConfig.getRedeemDays() == null || redeemConfig.getRedeemDays() <= 0) {
                     this.sendMessage(chatId, "该兑换配置异常，请联系管理员。");
                  } else if (redeemConfig.getRequiredPoints() != null && redeemConfig.getRequiredPoints() > 0) {
                     EmbyInfo server = this.embyInfoService
                        .lambdaQuery()
                        .eq(EmbyInfo::getId, redeemConfig.getEmbyInfoId())
                        .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
                        .eq(EmbyInfo::getStatus, Integer.valueOf(0))
                        .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                        .one();
                     if (server == null) {
                        this.sendMessage(chatId, "该配置关联的服务器暂不可用，请联系管理员。");
                     } else {
                        EmbyUser embyUser = this.resolveBoundRenewUser(chatId, userId, redeemConfig.getEmbyInfoId(), server);
                        if (embyUser != null) {
                           String embyUserName = embyUser.getEmbyUserName();
                           String lockKey = this.redeemLockKey(pointsChatId, userId);
                           String lockToken = this.tryAcquireRedeemLock(lockKey, chatId);
                           if (StringUtils.hasText(lockToken)) {
                              try {
                                 PointsProfile userProfile = this.pointsStore.findByUserId(pointsChatId, userId);
                                 if (userProfile == null) {
                                    this.sendMessage(chatId, "未找到你的积分记录，请先在积分群中发言或签到。");
                                 } else {
                                    String userLabel = this.formatUser(userProfile);
                                    int requiredPoints = redeemConfig.getRequiredPoints();
                                    if (userProfile.getPoints() < (long)requiredPoints) {
                                       this.sendMessage(chatId, String.format("积分不足，当前 %d 积分，需要 %d 积分续费。", userProfile.getPoints(), requiredPoints));
                                    } else {
                                       int applied = this.pointsStore
                                          .addPoints(userProfile, -requiredPoints, "recharge", embyUserName, redeemConfig.getEmbyInfoId());
                                       if (applied != -requiredPoints) {
                                          if (applied != 0) {
                                             this.pointsStore.addPoints(userProfile, -applied, "recharge_refund", embyUserName, redeemConfig.getEmbyInfoId());
                                          }

                                          this.sendMessage(chatId, "积分扣除失败，请稍后重试。");
                                       } else {
                                          int days = redeemConfig.getRedeemDays();

                                          EmbyUserCustomResponse response;
                                          try {
                                             response = this.embyUserService.renewAdmin(embyUser.getId(), days);
                                          } catch (Exception var33) {
                                             this.pointsStore
                                                .addPoints(userProfile, requiredPoints, "recharge_refund", embyUserName, redeemConfig.getEmbyInfoId());
                                             log.error("积分续费账号失败", (Throwable)var33);
                                             this.sendMessage(chatId, "续费失败，积分已退回，请稍后重试或联系管理员。");
                                             return;
                                          }

                                          String serverName = StringUtils.hasText(server.getServerName())
                                             ? server.getServerName()
                                             : String.valueOf(server.getId());
                                          String configName = StringUtils.hasText(redeemConfig.getConfigName()) ? redeemConfig.getConfigName() : "积分续费";
                                          StringBuilder successMsg = new StringBuilder();
                                          successMsg.append("✅ 续费成功！\n\n");
                                          successMsg.append("\ud83d\udccb 项目：").append(configName).append("\n");
                                          successMsg.append("\ud83d\udc64 用户：").append(userLabel).append("\n");
                                          successMsg.append("\ud83c\udfae Emby用户名：").append(embyUserName).append("\n");
                                          successMsg.append("\ud83d\udda5 服务器：").append(serverName).append("\n");
                                          successMsg.append("\ud83d\udcc5 续费天数：").append(days).append("天\n");
                                          successMsg.append("\ud83d\udcb0 消费积分：").append(requiredPoints).append("\n");
                                          successMsg.append("\ud83d\udcb3 剩余积分：").append(userProfile.getPoints()).append("\n");
                                          successMsg.append("⏰ 新到期时间：").append(response.getExpirationDate());
                                          this.sendMessage(chatId, successMsg.toString());
                                       }
                                    }
                                 }
                              } finally {
                                 this.redisLockUtils.unlock(lockKey, lockToken);
                              }
                           }
                        }
                     }
                  } else {
                     this.sendMessage(chatId, "该兑换配置未设置所需积分，请联系管理员。");
                  }
               } else {
                  this.sendMessage(chatId, "用法：/recharge 配置编号\n示例：/recharge 2\n续费账号由当前 Telegram 绑定关系自动确定，无需也不能填写其他用户名\n发送 /exchange 查看可用的续费项目及编号");
               }
            }
         }
      }
   }

   private EmbyUser resolveBoundRenewUser(long chatId, long telegramUserId, long serverId, EmbyInfo server) {
      EmbyUser boundUser = this.telegramBindingManager.findBoundUser(telegramUserId, true);
      if (boundUser == null) {
         this.sendMessage(chatId, "当前 Telegram 未绑定可用的 Emby 账号，无法使用积分续费。\n请先在 Mist 网页端或机器人中完成 Telegram 绑定。");
         return null;
      } else if (boundUser.getEmbyInfoId() != null && boundUser.getEmbyInfoId() == serverId) {
         return boundUser;
      } else {
         String serverName = server != null && StringUtils.hasText(server.getServerName()) ? server.getServerName() : String.valueOf(serverId);
         this.sendMessage(chatId, String.format("当前 Telegram 绑定账号不在服务器【%s】上，请选择与绑定账号服务器匹配的续费项目。", serverName));
         return null;
      }
   }

   private void handleRenew(Message message) {
      long chatId = message.getChatId();
      if (!this.isAdminUser(message.getFrom(), TelegramBotPermission.POINTS_ADMIN)) {
         this.sendMessage(chatId, "积分续期账号仅限管理员使用。");
      } else if (this.isGroupChat(message)) {
         this.sendMessage(chatId, "积分续期账号请在私聊中操作。");
      } else {
         Long pointsChatId = this.resolvePointsChatId(message);
         if (pointsChatId == null) {
            this.sendMessage(chatId, "未配置积分群/频道，请先配置 Telegram 机器人的积分群/频道 Chat ID。");
         } else {
            String text = message.getText().trim();
            if (text.startsWith("/renew")) {
               String argsText = text.substring("/renew".length()).trim();
               String[] parts = argsText.split("\\|");
               if (parts.length < 2) {
                  this.sendMessage(chatId, "用法：/renew Emby用户名|天数\n或指定服务器：/renew Emby用户名|服务器ID|天数\n示例：/renew embyuser|30 或 /renew embyuser|1|30");
               } else {
                  String embyUserName = parts[0].trim();
                  Long specifiedServerId = null;
                  String daysStr;
                  if (parts.length >= 3) {
                     try {
                        specifiedServerId = Long.parseLong(parts[1].trim());
                        daysStr = parts[2].trim();
                     } catch (NumberFormatException var21) {
                        this.sendMessage(chatId, "服务器ID格式错误，必须是数字。");
                        return;
                     }
                  } else {
                     daysStr = parts[1].trim();
                  }

                  if (!StringUtils.hasText(embyUserName)) {
                     this.sendMessage(chatId, "Emby用户名不能为空。");
                  } else {
                     int days;
                     try {
                        days = Integer.parseInt(daysStr);
                     } catch (NumberFormatException var20) {
                        this.sendMessage(chatId, "天数字段必须是数字。");
                        return;
                     }

                     if (days <= 0) {
                        this.sendMessage(chatId, "天数必须大于 0。");
                     } else {
                        List<EmbyUser> matchedUsers = this.embyUserService
                           .lambdaQuery()
                           .eq(EmbyUser::getEmbyUserName, embyUserName)
                           .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                           .list();
                        if (matchedUsers != null && !matchedUsers.isEmpty()) {
                           EmbyUser embyUser = null;
                           if (specifiedServerId != null) {
                              for (EmbyUser user : matchedUsers) {
                                 if (user.getEmbyInfoId() != null && user.getEmbyInfoId().equals(specifiedServerId)) {
                                    embyUser = user;
                                    break;
                                 }
                              }

                              if (embyUser == null) {
                                 this.sendMessage(chatId, String.format("未找到用户名 %s 在服务器 ID %d 上的账号。", embyUserName, specifiedServerId));
                                 return;
                              }
                           } else {
                              if (matchedUsers.size() > 1) {
                                 StringBuilder msg = new StringBuilder();
                                 msg.append("⚠️ 检测到用户名 ").append(embyUserName).append(" 在多个服务器上存在：\n\n");

                                 for (EmbyUser userx : matchedUsers) {
                                    String serverName = "未知服务器";
                                    if (userx.getEmbyInfoId() != null) {
                                       EmbyInfo server = this.embyInfoService.getById(userx.getEmbyInfoId());
                                       if (server != null && StringUtils.hasText(server.getServerName())) {
                                          serverName = server.getServerName();
                                       }
                                    }

                                    msg.append("• 服务器ID: ").append(userx.getEmbyInfoId()).append(" - ").append(serverName).append("\n");
                                 }

                                 msg.append("\n请使用以下格式指定服务器：\n");
                                 msg.append("/renew ").append(embyUserName).append("|服务器ID|").append(days).append("\n");
                                 msg.append("示例：/renew ").append(embyUserName).append("|").append(matchedUsers.get(0).getEmbyInfoId()).append("|").append(days);
                                 this.sendMessage(chatId, msg.toString());
                                 return;
                              }

                              embyUser = matchedUsers.get(0);
                           }

                           try {
                              EmbyUserCustomResponse response = this.embyUserService.renewAdmin(embyUser.getId(), days);
                              String serverName = "未知服务器";
                              if (embyUser.getEmbyInfoId() != null) {
                                 EmbyInfo server = this.embyInfoService.getById(embyUser.getEmbyInfoId());
                                 if (server != null && StringUtils.hasText(server.getServerName())) {
                                    serverName = server.getServerName();
                                 }
                              }

                              StringBuilder adminMsg = new StringBuilder();
                              adminMsg.append("✅ 续期成功！\n");
                              adminMsg.append("Emby用户名：").append(embyUserName).append("\n");
                              adminMsg.append("服务器：").append(serverName).append(" (ID: ").append(embyUser.getEmbyInfoId()).append(")\n");
                              adminMsg.append("续期天数：").append(days).append("\n");
                              adminMsg.append("新到期时间：").append(response.getExpirationDate());
                              this.sendMessage(chatId, adminMsg.toString());
                           } catch (Exception var19) {
                              log.error("续期账号失败", (Throwable)var19);
                              this.sendMessage(chatId, "续期失败：" + var19.getMessage());
                           }
                        } else {
                           this.sendMessage(chatId, "未找到 Emby 用户：" + embyUserName);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void handleLotteryPublish(Message message, String[] parts) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "抽奖发布请在群聊中操作。");
      } else if (!this.isAdminUser(message.getFrom(), TelegramBotPermission.LOTTERY_MANAGE)) {
         this.sendMessage(chatId, "抽奖发布仅限管理员使用。");
      } else if (this.lotteryService.findActiveLottery(chatId) != null) {
         this.sendMessage(chatId, "当前已有进行中的抽奖，请等待开奖后再发布新的抽奖。");
      } else {
         Integer winnerCount = 1;
         String duration = "24h";
         if (parts.length > 1 && (parts[1].equalsIgnoreCase("publish") || parts[1].equalsIgnoreCase("create"))) {
            if (parts.length > 2) {
               try {
                  winnerCount = Integer.parseInt(parts[2]);
                  if (winnerCount < 1) {
                     winnerCount = 1;
                  }

                  if (parts.length > 3) {
                     duration = parts[3];
                  }
               } catch (NumberFormatException var14) {
               }
            }
         } else if (parts.length > 1 && parts[1].matches("\\d+")) {
            try {
               winnerCount = Integer.parseInt(parts[1]);
               if (winnerCount < 1) {
                  winnerCount = 1;
               }

               if (parts.length > 2) {
                  duration = parts[2];
               }
            } catch (NumberFormatException var13) {
            }
         }

         List<PointsBotPrizeConfig> availablePrizes = this.pointsBotPrizeConfigService
            .lambdaQuery()
            .eq(PointsBotPrizeConfig::getEnabled, Integer.valueOf(1))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .orderByDesc(PointsBotPrizeConfig::getSort)
            .orderByDesc(PointsBotPrizeConfig::getId)
            .list();
         if (availablePrizes != null && !availablePrizes.isEmpty()) {
            List<List<TelegramBotApiClient.InlineButton>> keyboard = new ArrayList<>();

            for (PointsBotPrizeConfig prize : availablePrizes) {
               if (prize.getRemainingQuantity() == null || prize.getRemainingQuantity() > 0) {
                  TelegramBotApiClient.InlineButton button = new TelegramBotApiClient.InlineButton(
                     String.format(
                        "\ud83c\udf81 %s (积分:%s 剩余:%s)",
                        prize.getPrizeName(),
                        prize.getRequiredPoints() == null ? "-" : prize.getRequiredPoints(),
                        prize.getRemainingQuantity() == null ? "∞" : prize.getRemainingQuantity()
                     ),
                     String.format("lottery_prize_%d_%d_%s", prize.getId(), winnerCount, duration)
                  );
                  keyboard.add(List.of(button));
               }
            }

            if (keyboard.isEmpty()) {
               this.sendMessage(chatId, "所有奖品已售罄。");
            } else {
               try {
                  this.botApiClient
                     .sendMessage(
                        chatId,
                        String.format("\ud83c\udf81 发起抽奖 (人数:%d, 时间:%s)\n请选择奖品：", winnerCount, duration),
                        null,
                        null,
                        TelegramBotApiClient.inlineKeyboard(keyboard),
                        false
                     );
               } catch (TelegramBotApiClient.TelegramBotApiException var12) {
                  log.error("Failed to send inline keyboard", (Throwable)var12);
                  this.sendMessage(chatId, "发送奖品选择失败，请稍后重试。");
               }
            }
         } else {
            this.sendMessage(chatId, "暂无可用奖品。");
         }
      }
   }

   private void handleCallbackQuery(Update update) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      String callbackData = callbackQuery.getData();
      if (callbackData.startsWith("bj_")) {
         this.handleBlackjackCallback(update);
      } else if (callbackData.startsWith("scratchwins:")) {
         this.handleScratchWinsCallback(update);
      } else if (callbackData.startsWith("scratch:")) {
         this.handleScratchCardCallback(update);
      } else if (callbackData.startsWith("brain:")) {
         this.handleBrainCallback(update);
      } else if (callbackData.startsWith("hell:")) {
         this.handleHellCallback(update);
      } else if (callbackData.startsWith("foam_bag:")) {
         this.handleFoamBagCallback(update);
      } else if (callbackData.startsWith("red_packet:")) {
         this.handleRedPacketCallback(update);
      } else {
         try {
            this.botApiClient.answerCallbackQuery(callbackQuery.getId());
         } catch (TelegramBotApiClient.TelegramBotApiException var5) {
            log.error("Failed to answer callback query", (Throwable)var5);
         }

         if (callbackData.startsWith("lottery_prize_")) {
            this.handleLotteryPrizeSelection(callbackQuery, callbackData);
         }
      }
   }

   private void handleLotteryPrizeSelection(CallbackQuery callbackQuery, String callbackData) {
      long chatId = callbackQuery.getMessage().getChatId();
      User user = callbackQuery.getFrom();
      if (!this.isAdminUser(user, TelegramBotPermission.LOTTERY_MANAGE)) {
         try {
            this.botApiClient.answerCallbackQuery(callbackQuery.getId(), "抽奖发布仅限管理员使用", true);
         } catch (TelegramBotApiClient.TelegramBotApiException var27) {
            log.error("Failed to send admin check response", (Throwable)var27);
         }
      } else if (this.lotteryService.findActiveLottery(chatId) != null) {
         this.sendMessage(chatId, "当前已有进行中的抽奖，请等待开奖后再发布新的抽奖。");
      } else {
         String[] parts = callbackData.split("_");
         long prizeId = 0L;
         int winnerCount = 1;
         String duration = "24h";

         try {
            if (parts.length >= 5) {
               prizeId = Long.parseLong(parts[2]);
               winnerCount = Integer.parseInt(parts[3]);
               duration = parts[4];
            } else {
               String suffix = callbackData.substring("lottery_prize_".length());
               prizeId = Long.parseLong(suffix);
            }
         } catch (Exception var30) {
            this.sendMessage(chatId, "数据格式错误");
            return;
         }

         PointsBotPrizeConfig prize = this.pointsBotPrizeConfigService.getById(Long.valueOf(prizeId));
         if (prize == null || prize.getEnabled() != 1 || prize.getDelFlag() == 1) {
            this.sendMessage(chatId, "该奖品不可用");
         } else if (prize.getRemainingQuantity() != null && prize.getRemainingQuantity() <= 0) {
            this.sendMessage(chatId, String.format("奖品「%s」已售罄", prize.getPrizeName()));
         } else {
            long minutes = 0L;
            String durUpper = duration.toUpperCase();

            try {
               if (durUpper.endsWith("D")) {
                  minutes = Long.parseLong(durUpper.replace("D", "")) * 24L * 60L;
               } else if (durUpper.endsWith("H")) {
                  minutes = Long.parseLong(durUpper.replace("H", "")) * 60L;
               } else if (durUpper.endsWith("M")) {
                  minutes = Long.parseLong(durUpper.replace("M", ""));
               } else if (duration.matches("\\d+")) {
                  minutes = Long.parseLong(duration);
               }
            } catch (Exception var29) {
            }

            if (minutes < 1L) {
               minutes = (long)this.config.getLotteryDrawIntervalMinutes();
            }

            String lotteryTitle = StringUtils.hasText(prize.getRemark()) ? prize.getRemark() : "本群抽奖";
            LocalDateTime drawAt = LocalDateTime.now().plusMinutes(minutes);
            PointsProfile creator = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
            PointsBotLotteryService.PointsBotLotteryConfig lotteryConfig = new PointsBotLotteryService.PointsBotLotteryConfig(winnerCount);
            this.lotteryService.createLottery(chatId, lotteryTitle, prizeId, lotteryConfig, creator, drawAt);
            String participationInfo;
            if (StringUtils.hasText(prize.getRemark())) {
               participationInfo = String.format("参与关键词：<code>%s</code>", prize.getRemark());
            } else {
               participationInfo = "参与方式：发送 <code>/lottery join</code> 参与抽奖";
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDrawAt = drawAt.format(formatter);
            StringBuilder message = new StringBuilder("\ud83c\udf89 抽奖已发起！\n");
            message.append("奖品：").append(prize.getPrizeName());
            if (prize.getRequiredPoints() != null && prize.getRequiredPoints() > 0) {
               message.append(String.format("（价值 %d 积分）", prize.getRequiredPoints()));
            }

            message.append(String.format("\n中奖名额：%d人", winnerCount));
            message.append("\n抽奖说明：").append(lotteryTitle);
            message.append("\n\n").append(participationInfo);
            message.append("\n开奖时间：").append(formattedDrawAt);

            try {
               TelegramBotApiClient.ApiMessage sentMessage = this.botApiClient.sendMessage(chatId, message.toString(), null, "HTML", null, false);
               long messageId = sentMessage.getMessageId();
               PointsBotLottery createdLottery = this.lotteryService.findActiveLottery(chatId);
               if (createdLottery != null) {
                  createdLottery.setAnnouncementMessageId(messageId);
                  this.lotteryService.updateLottery(createdLottery);
               }

               this.botApiClient.pinChatMessage(chatId, messageId, false);
               log.info("Pinned lottery announcement (messageId={}) in chat {}", messageId, chatId);
            } catch (TelegramBotApiClient.TelegramBotApiException var28) {
               log.error("Failed to send or pin lottery message", (Throwable)var28);
            }
         }
      }
   }

   private void handleLotteryJoin(Message message, String note) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "抽奖参与请在群聊中操作。");
      } else {
         PointsBotLottery active = this.lotteryService.findActiveLottery(chatId);
         this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
         if (active == null) {
            this.sendReplyAndDelete(chatId, message.getMessageId(), "暂无进行中的抽奖。", 15);
         } else {
            if (active.getPrizeConfigId() != null) {
               PointsBotPrizeConfig prize = this.pointsBotPrizeConfigService.getById(active.getPrizeConfigId());
               if (prize != null && StringUtils.hasText(prize.getRemark()) && (!StringUtils.hasText(note) || !note.trim().equals(prize.getRemark().trim()))) {
                  this.sendHtmlReplyAndDelete(
                     chatId,
                     message.getMessageId(),
                     String.format("❌ 参与口令错误！\n请直接发送关键词：<code>%s</code>\n或使用：/lottery join %s", prize.getRemark(), prize.getRemark()),
                     10
                  );
                  return;
               }
            }

            PointsProfile profile = this.pointsStore
               .getOrCreate(chatId, message.getFrom().getId(), message.getFrom().getUserName(), this.displayName(message.getFrom()));
            boolean added = this.lotteryService.addEntry(active.getId(), chatId, profile, note);
            if (added) {
               this.sendReplyAndDelete(chatId, message.getMessageId(), String.format("\ud83c\udf89 成功参与抽奖！\n本次抽奖：%s", active.getTitle()), 10);
            } else {
               this.sendReplyAndDelete(chatId, message.getMessageId(), "你已经参与过本次抽奖了。", 10);
            }
         }
      }
   }

   private void handleLotteryStatus(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "抽奖状态查询请在群聊中操作。");
      } else {
         this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
         PointsBotLottery active = this.lotteryService.findActiveLottery(chatId);
         if (active == null) {
            this.sendReplyAndDelete(chatId, message.getMessageId(), "暂无进行中的抽奖。", 15);
         } else {
            String prizeDisplay = "";
            String participationInfo = "参与方式：发送 <code>/lottery join</code> 参与抽奖";
            if (active.getPrizeConfigId() != null) {
               PointsBotPrizeConfig prize = this.pointsBotPrizeConfigService.getById(active.getPrizeConfigId());
               if (prize != null) {
                  prizeDisplay = String.format("\n奖品：%s", prize.getPrizeName());
                  if (prize.getRequiredPoints() != null && prize.getRequiredPoints() > 0) {
                     prizeDisplay = prizeDisplay + String.format("（价值 %d 积分）", prize.getRequiredPoints());
                  }

                  if (StringUtils.hasText(prize.getRemark())) {
                     participationInfo = String.format("参与关键词：<code>%s</code>", prize.getRemark());
                  }
               }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDrawAt = active.getDrawAt().format(formatter);
            int entryCount = this.lotteryService.listEntries(active.getId()).size();
            String statusMsg = String.format(
               "\ud83c\udfb0 进行中的抽奖\n\n抽奖说明：%s%s\n\n%s\n当前参与人数：%d\n开奖时间：%s", active.getTitle(), prizeDisplay, participationInfo, entryCount, formattedDrawAt
            );
            this.sendHtmlReplyAndDelete(chatId, message.getMessageId(), statusMsg, 30);
         }
      }
   }

   private void handleLotteryDraw(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "立即开奖请在群聊中操作。");
      } else {
         this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
         if (!this.isAdminUser(message.getFrom(), TelegramBotPermission.LOTTERY_MANAGE)) {
            this.sendReplyAndDelete(chatId, message.getMessageId(), "立即开奖仅限管理员使用。", 15);
         } else {
            PointsBotLottery active = this.lotteryService.findActiveLottery(chatId);
            if (active == null) {
               this.sendReplyAndDelete(chatId, message.getMessageId(), "暂无进行中的抽奖。", 15);
            } else {
               List<PointsBotLotteryEntry> winners = this.lotteryService.drawLottery(active, new Random());
               if (winners.isEmpty()) {
                  this.sendMessage(chatId, String.format("\ud83c\udf89 抽奖结果：%s\n无人参与，抽奖已取消。", active.getTitle()));
                  if (active.getAnnouncementMessageId() != null) {
                     try {
                        this.botApiClient.unpinChatMessage(chatId, active.getAnnouncementMessageId());
                        log.info("Unpinned lottery (no participants) messageId={} in chat {}", active.getAnnouncementMessageId(), chatId);
                     } catch (TelegramBotApiClient.TelegramBotApiException var10) {
                        log.warn("Failed to unpin lottery message: {}", var10.getMessage());
                     }
                  }
               } else {
                  if (active.getPrizeConfigId() != null) {
                     PointsBotPrizeConfig prize = this.pointsBotPrizeConfigService.getById(active.getPrizeConfigId());
                     if (prize != null && prize.getRemainingQuantity() != null && prize.getRemainingQuantity() > 0) {
                        int reduceAmount = winners.size();
                        int newQuantity = Math.max(0, prize.getRemainingQuantity() - reduceAmount);
                        prize.setRemainingQuantity(newQuantity);
                        this.pointsBotPrizeConfigService.updateById(prize);
                        log.info(
                           "Reduced prize quantity for prize_id={}, old={}, new={}", prize.getId(), prize.getRemainingQuantity() + reduceAmount, newQuantity
                        );
                     }
                  }

                  StringBuilder winnerNames = new StringBuilder();

                  for (PointsBotLotteryEntry winner : winners) {
                     if (winnerNames.length() > 0) {
                        winnerNames.append(", ");
                     }

                     String name = winner.getUsername() != null && !winner.getUsername().isBlank() ? "@" + winner.getUsername() : winner.getDisplayName();
                     winnerNames.append(name);
                     this.sendMessage(winner.getUserId(), String.format("\ud83c\udf89 恭喜中奖！\n抽奖：%s\n请联系管理员领取奖励。", active.getTitle()));
                  }

                  this.sendMessage(chatId, String.format("\ud83c\udf89 抽奖结果：%s\n中奖者：%s", active.getTitle(), winnerNames.toString()));
                  log.info("Attempting to unpin lottery in chat {}. AnnouncementMessageId: {}", chatId, active.getAnnouncementMessageId());
                  if (active.getAnnouncementMessageId() != null) {
                     try {
                        this.botApiClient.unpinChatMessage(chatId, active.getAnnouncementMessageId());
                        log.info("Successfully unpinned lottery announcement (messageId={}) in chat {}", active.getAnnouncementMessageId(), chatId);
                     } catch (TelegramBotApiClient.TelegramBotApiException var11) {
                        log.error(
                           "Failed to unpin lottery message in chat {}, messageId={}: {}. Error: {}",
                           chatId,
                           active.getAnnouncementMessageId(),
                           var11.getMessage(),
                           var11.getClass().getSimpleName()
                        );
                        log.error("Bot may not have 'can_pin_messages' permission or the message may no longer be pinned");
                     }
                  } else {
                     log.warn(
                        "Cannot unpin lottery in chat {}: announcement_message_id is null. This lottery may have been created before the message ID feature was added.",
                        chatId
                     );
                  }
               }
            }
         }
      }
   }

   private void processRedeemCreation(
      long chatId,
      PointsProfile targetProfile,
      String targetLabel,
      long targetUserId,
      String telegramUsername,
      String embyUserName,
      String embyUserPassword,
      String remarks,
      PointsBotRedeemConfig redeemConfig
   ) {
      if (redeemConfig != null && PointsBotRedeemTypeEnum.CREATE_ACCOUNT.matches(redeemConfig.getRedeemType())) {
         boolean deducted = false;
         long serverId = redeemConfig.getEmbyInfoId();
         int days = redeemConfig.getRedeemDays();
         int requiredPoints = redeemConfig.getRequiredPoints();

         try {
            int applied = this.pointsStore.addPoints(targetProfile, -requiredPoints, "redeem_register", embyUserName, serverId);
            if (applied != -requiredPoints) {
               if (applied != 0) {
                  this.pointsStore.addPoints(targetProfile, -applied, "redeem_register_refund", embyUserName, serverId);
               }

               this.sendMessage(chatId, "积分扣除失败，请稍后重试。");
               return;
            }

            deducted = true;
            EmbyUserSave embyUserSave = new EmbyUserSave();
            embyUserSave.setEmbyUserName(embyUserName);
            embyUserSave.setEmbyUserPassword(embyUserPassword);
            embyUserSave.setDay(days);
            embyUserSave.setRemarks(remarks);
            embyUserSave.setEmbyInfoId(serverId);
            embyUserSave.setRegisterChannel(RegisterChannelEnum.POINTS_REDEEM.getCode());
            embyUserSave.setRegisterChannelDetail(this.buildTelegramRedeemChannelDetail(telegramUsername, targetUserId));
            InsertUserResponse response = this.embyUserService.insertUser(embyUserSave);
            PointsProfile updatedProfile = this.pointsStore.findByUserId(targetProfile.getChatId(), targetProfile.getUserId());
            long remainingPoints = updatedProfile != null ? updatedProfile.getPoints() : targetProfile.getPoints();
            StringBuilder userMsg = new StringBuilder();
            userMsg.append("✅ 积分注册账号创建成功！\n");
            userMsg.append("用户名：").append(response.getEmbyUserName()).append("\n");
            userMsg.append("密码：").append(response.getEmbyUserPassword()).append("\n");
            userMsg.append("到期时间：").append(response.getExpirationDate()).append("\n");
            userMsg.append("兑换天数：").append(days).append("\n");
            userMsg.append("已扣除积分：").append(requiredPoints).append("\n");
            userMsg.append("剩余积分：").append(remainingPoints).append("\n");
            userMsg.append("线路信息：请前往 Mist 面板查询");
            this.sendMessage(chatId, userMsg.toString());
            if (StringUtils.hasText(this.config.getDmChatId())) {
               try {
                  long adminChatId = Long.parseLong(this.config.getDmChatId());
                  StringBuilder adminMsg = new StringBuilder();
                  adminMsg.append("\ud83d\udce2 积分注册通知\n");
                  adminMsg.append("用户：").append(targetLabel).append("\n");
                  adminMsg.append("Telegram ID：").append(targetUserId).append("\n");
                  adminMsg.append("Emby用户名：").append(response.getEmbyUserName()).append("\n");
                  adminMsg.append("到期时间：").append(response.getExpirationDate()).append("\n");
                  adminMsg.append("兑换天数：").append(days).append("\n");
                  adminMsg.append("扣除积分：").append(requiredPoints).append("\n");
                  adminMsg.append("剩余积分：").append(remainingPoints);
                  this.sendMessage(adminChatId, adminMsg.toString());
               } catch (NumberFormatException var27) {
                  log.warn("管理员 chatId 格式错误: {}", this.config.getDmChatId());
               }
            }
         } catch (ApiException var28) {
            if (deducted) {
               this.pointsStore.addPoints(targetProfile, requiredPoints, "redeem_register_refund", embyUserName, serverId);
            }

            log.error("积分注册创建 Emby 用户失败: configId={}, serverId={}", redeemConfig.getId(), serverId, var28);
            this.sendMessage(chatId, "创建用户失败，积分已退回，请稍后重试或联系管理员。");
         } catch (Exception var29) {
            if (deducted) {
               this.pointsStore.addPoints(targetProfile, requiredPoints, "redeem_register_refund", embyUserName, serverId);
            }

            log.error("积分注册账号创建失败", (Throwable)var29);
            this.sendMessage(chatId, "创建用户失败，积分已退回，请稍后重试或联系管理员。");
         }
      } else {
         log.warn(
            "拒绝使用非注册类型配置创建账号: configId={}, redeemType={}",
            redeemConfig == null ? null : redeemConfig.getId(),
            redeemConfig == null ? null : redeemConfig.getRedeemType()
         );
         this.sendMessage(chatId, "该兑换配置不能用于注册账号。");
      }
   }

   private String redeemLockKey(long pointsChatId, long userId) {
      return "foam:points-bot:redeem:" + pointsChatId + ":" + userId;
   }

   private String tryAcquireRedeemLock(String lockKey, long chatId) {
      try {
         String token = this.redisLockUtils.tryLock(lockKey, 300L);
         if (!StringUtils.hasText(token)) {
            this.sendMessage(chatId, "你有一笔兑换正在处理中，请勿重复提交。");
         }

         return token;
      } catch (Exception var5) {
         log.error("积分兑换锁获取失败: lockKey={}", lockKey, var5);
         this.sendMessage(chatId, "兑换服务暂时不可用，请稍后重试。");
         return null;
      }
   }

   private boolean tryJoinLotteryByKeyword(Message message) {
      if (message.getFrom() != null && !message.getFrom().getIsBot()) {
         long chatId = message.getChatId();
         if (!this.isGroupChat(message)) {
            return false;
         } else {
            PointsBotLottery active = this.lotteryService.findActiveLottery(chatId);
            if (active == null) {
               return false;
            } else {
               String keyword = null;
               if (active.getPrizeConfigId() != null) {
                  PointsBotPrizeConfig prize = this.pointsBotPrizeConfigService.getById(active.getPrizeConfigId());
                  if (prize != null && StringUtils.hasText(prize.getRemark())) {
                     keyword = prize.getRemark().trim();
                  }
               }

               if (keyword == null) {
                  return false;
               } else {
                  String userText = message.getText().trim();
                  if (!userText.equals(keyword)) {
                     return false;
                  } else {
                     User user = message.getFrom();
                     PointsProfile profile = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
                     boolean added = this.lotteryService.addEntry(active.getId(), chatId, profile, null);
                     this.deleteMessageDelayed(chatId, message.getMessageId(), 3);
                     if (added) {
                        this.sendReplyAndDelete(
                           chatId,
                           message.getMessageId(),
                           String.format("✅ @%s 成功参与抽奖！", user.getUserName() != null ? user.getUserName() : this.displayName(user)),
                           10
                        );
                     } else {
                        this.sendReplyAndDelete(
                           chatId,
                           message.getMessageId(),
                           String.format("⚠️ @%s 你已经参与过本次抽奖了", user.getUserName() != null ? user.getUserName() : this.displayName(user)),
                           10
                        );
                     }

                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   private void handleSanguoshaHelp(Message message) {
      long chatId = message.getChatId();
      int dailyLimit = Math.max(0, this.config.getDailySanguoshaPlayLimit());
      StringBuilder usage = new StringBuilder("\ud83c\udfb4 三国杀玩法\n\n");
      usage.append("在群聊中 @一位用户，并且消息里只保留「三国杀」即可触发。\n");
      usage.append("示例：@user 三国杀\n");
      usage.append("系统会随机抽一张牌，生成双方积分变化或无事发生。");
      if (dailyLimit > 0) {
         usage.append("\n每日次数：每人 ").append(dailyLimit).append(" 次。");
      } else {
         usage.append("\n每日次数：不限制。");
      }

      if (this.isGroupChat(message)) {
         this.deleteMessageDelayed(chatId, message.getMessageId(), 30);
         this.sendReplyAndDelete(chatId, message.getMessageId(), usage.toString(), 30);
      } else {
         this.sendMessage(chatId, usage.toString());
      }
   }

   private boolean tryPlaySanguoshaCard(Message message) {
      if (!this.isGameCommandEnabled("sgs")) {
         return false;
      } else if (message.getFrom() == null || Boolean.TRUE.equals(message.getFrom().getIsBot())) {
         return false;
      } else if (this.isGroupChat(message) && message.hasText()) {
         PointsBot.MentionTarget targetRef = this.resolveMentionTarget(message);
         if (targetRef == null) {
            return false;
         } else if (!this.isSanguoshaTriggerMessage(message)) {
            return false;
         } else if (!this.tryStartGroupGame(message, "sgs")) {
            return true;
         } else {
            long chatId = message.getChatId();
            User user = message.getFrom();
            PointsProfile actor = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
            PointsProfile target = this.resolveMentionedProfile(chatId, targetRef);
            if (target != null && target.getUserId() == actor.getUserId()) {
               target = actor;
            }

            if (target == null) {
               this.sendReplyAndDelete(
                  chatId,
                  message.getMessageId(),
                  String.format("\ud83c\udfb4 牌已经举起来了，但 %s 还没绑定积分档案。\n让 TA 先在群里发句话或签到一下，再来接受三国杀式问候 \ud83e\udee3", targetRef.label()),
                  15
               );
               return true;
            } else {
               SanguoshaCardService.DailyPlayResult dailyPlay = this.sanguoshaCardService
                  .tryConsumeDailyPlay(chatId, actor.getUserId(), this.config.getDailySanguoshaPlayLimit());
               if (!dailyPlay.allowed()) {
                  this.sendReplyAndDelete(
                     chatId, message.getMessageId(), String.format("\ud83c\udfb4 今日三国杀次数已用完（%d/%d）。明天再来开一局吧。", dailyPlay.used(), dailyPlay.limit()), 15
                  );
                  return true;
               } else {
                  SanguoshaCard card = this.sanguoshaCardService.drawCard();

                  String result;
                  try {
                     result = this.playSanguoshaCard(actor, target, card);
                  } catch (BizException var12) {
                     result = "\ud83c\udfb4 本回合未发生积分移动\n\n" + var12.getMessage();
                  }

                  this.sendReplyAndDelete(chatId, message.getMessageId(), result, 15);
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private boolean isSanguoshaTriggerMessage(Message message) {
      return this.sanguoshaCardService.isTriggerText(this.removeMentionText(message));
   }

   private String removeMentionText(Message message) {
      String text = message.getText();
      if (!StringUtils.hasText(text)) {
         return text;
      } else if (message.getEntities() == null) {
         return USERNAME_MENTION_PATTERN.matcher(text).replaceAll("");
      } else {
         List<MessageEntity> mentionEntities = new ArrayList<>();

         for (MessageEntity entity : message.getEntities()) {
            if (entity != null
               && ("mention".equals(entity.getType()) || "text_mention".equals(entity.getType()))
               && entity.getOffset() != null
               && entity.getLength() != null
               && entity.getOffset() >= 0
               && entity.getLength() > 0) {
               mentionEntities.add(entity);
            }
         }

         mentionEntities.sort((left, right) -> Integer.compare(right.getOffset(), left.getOffset()));
         StringBuilder builder = new StringBuilder(text);

         for (MessageEntity entityx : mentionEntities) {
            int start = entityx.getOffset();
            int end = Math.min(builder.length(), start + entityx.getLength());
            if (start < builder.length() && start < end) {
               builder.delete(start, end);
            }
         }

         return USERNAME_MENTION_PATTERN.matcher(builder.toString()).replaceAll("");
      }
   }

   private PointsBot.MentionTarget resolveMentionTarget(Message message) {
      if (message.getEntities() != null) {
         for (MessageEntity entity : message.getEntities()) {
            if ("text_mention".equals(entity.getType()) && entity.getUser() != null) {
               User user = entity.getUser();
               String username = this.normalizeMentionUsername(user.getUserName());
               String label = username != null ? "@" + username : this.displayName(user);
               return new PointsBot.MentionTarget(user.getId(), username, label);
            }
         }

         for (MessageEntity entityx : message.getEntities()) {
            if ("mention".equals(entityx.getType())) {
               String username = this.normalizeMentionUsername(entityx.getText());
               if (username != null) {
                  return new PointsBot.MentionTarget(null, username, "@" + username);
               }
            }
         }
      }

      Matcher matcher = USERNAME_MENTION_PATTERN.matcher(message.getText());
      if (matcher.find()) {
         String username = this.normalizeMentionUsername(matcher.group(1));
         if (username != null) {
            return new PointsBot.MentionTarget(null, username, "@" + username);
         }
      }

      return null;
   }

   private PointsProfile resolveMentionedProfile(long chatId, PointsBot.MentionTarget targetRef) {
      PointsProfile target = null;
      if (targetRef.userId() != null) {
         target = this.pointsStore.findByUserId(chatId, targetRef.userId());
      }

      if (target == null && StringUtils.hasText(targetRef.username())) {
         target = this.pointsStore.findByUsername(chatId, targetRef.username());
      }

      return target;
   }

   private String normalizeMentionUsername(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         String username = value.trim();
         if (username.startsWith("@")) {
            username = username.substring(1);
         }

         return username.matches("[A-Za-z0-9_]{3,32}") ? username : null;
      }
   }

   private String playSanguoshaCard(PointsProfile actor, PointsProfile target, SanguoshaCard card) {
      String header = this.buildSanguoshaHeader(actor, target, card);
      boolean selfTarget = actor.getUserId() == target.getUserId();
      String var6 = card.getEffect();

      return switch (var6) {
         case "STEAL" -> this.playStealCard(actor, target, card, header, selfTarget);
         case "GIFT" -> this.playGiftCard(actor, target, card, header, selfTarget);
         case "MUTUAL_GIFT" -> this.playMutualGiftCard(actor, target, card, header, selfTarget);
         case "BOTH_GAIN" -> this.playBothGainCard(actor, target, card, header, selfTarget);
         case "SELF_GAIN" -> this.playSelfGainCard(actor, target, card, header);
         case "TARGET_LOSE" -> this.playTargetLoseCard(actor, target, card, header, selfTarget);
         case "BOTH_LOSE" -> this.playBothLoseCard(actor, target, card, header, selfTarget);
         case "DUEL" -> this.playDuelCard(actor, target, card, header, selfTarget);
         case "NO_EFFECT" -> this.playNoEffectCard(actor, target, card, header);
         default -> header + "\n这张牌的说明书被风吹走了，本回合没有积分变化。\ud83d\udcdc\n" + this.formatSanguoshaBalance(actor, target);
      };
   }

   private String playStealCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header, boolean selfTarget) {
      int amount = this.sanguoshaCardService.randomPoints(card);
      if (selfTarget) {
         return header + "\n" + card.getEmoji() + " 你对自己发动了一次战术检索，结果发现目标还是自己。积分不变。\n" + this.formatSanguoshaBalance(actor, target);
      } else {
         int moved = this.moveSanguoshaPoints(target, actor, amount, card, "steal");
         return moved <= 0
            ? header + "\n" + card.getEmoji() + " " + this.formatUser(target) + " 防线稳住了，本回合没有积分移动。\n" + this.formatSanguoshaBalance(actor, target)
            : header
               + "\n"
               + card.getEmoji()
               + " 顺手牵羊生效："
               + this.formatUser(actor)
               + " 从 "
               + this.formatUser(target)
               + " 处转移 "
               + moved
               + " 积分。\n"
               + this.formatSanguoshaBalance(actor, target);
      }
   }

   private String playGiftCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header, boolean selfTarget) {
      int amount = this.sanguoshaCardService.randomPoints(card);
      if (selfTarget) {
         return header + "\n" + card.getEmoji() + " 自己给自己递牌，镜子里的你收得很感动。积分不变。\n" + this.formatSanguoshaBalance(actor, target);
      } else {
         int moved = this.moveSanguoshaPoints(actor, target, amount, card, "gift");
         return moved <= 0
            ? header + "\n" + card.getEmoji() + " 想送积分，但可用积分不足，本回合没有移动。\n" + this.formatSanguoshaBalance(actor, target)
            : header
               + "\n"
               + card.getEmoji()
               + " "
               + this.formatUser(actor)
               + " 让出 "
               + moved
               + " 积分给 "
               + this.formatUser(target)
               + "，主公看了都说会做人。\n"
               + this.formatSanguoshaBalance(actor, target);
      }
   }

   private String playMutualGiftCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header, boolean selfTarget) {
      if (selfTarget) {
         return header + "\n⛓ 你和自己铁索连环，最后只连上了耳机线。积分不变。\n" + this.formatSanguoshaBalance(actor, target);
      } else {
         int actorAmount = this.sanguoshaCardService.randomPoints(card);
         int targetAmount = this.sanguoshaCardService.randomPoints(card);
         List<FoamBagTransferResult> transfers = this.foamBagService
            .transferBothWays(
               actor,
               target,
               actorAmount,
               targetAmount,
               this.buildSanguoshaReason(card, "mutual_to_target_out"),
               this.buildSanguoshaReason(card, "mutual_to_target_in"),
               this.buildSanguoshaReason(card, "mutual_to_actor_out"),
               this.buildSanguoshaReason(card, "mutual_to_actor_in")
            );
         int toTarget = transfers.get(0).transferredPoints();
         int toActor = transfers.get(1).transferredPoints();
         return toTarget <= 0 && toActor <= 0
            ? header + "\n⛓ 双方可用积分都不足，互赠环节变成了互相微笑。\n" + this.formatSanguoshaBalance(actor, target)
            : header
               + "\n⛓ 铁索连环启动："
               + this.formatUser(actor)
               + " 给出 "
               + toTarget
               + " 积分，"
               + this.formatUser(target)
               + " 回礼 "
               + toActor
               + " 积分。\n礼尚往来，但账本全都记下来了。\n"
               + this.formatSanguoshaBalance(actor, target);
      }
   }

   private String playBothGainCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header, boolean selfTarget) {
      int amount = this.sanguoshaCardService.randomPoints(card);
      int actorGain = this.pointsStore.addPoints(actor, amount, this.buildSanguoshaReason(card, "gain"), String.valueOf(target.getUserId()));
      if (selfTarget) {
         return header + "\n" + card.getEmoji() + " 单人桃园小席开张，系统给你加菜 +" + actorGain + " 积分。\n" + this.formatSanguoshaBalance(actor, target);
      } else {
         int targetGain = this.pointsStore.addPoints(target, amount, this.buildSanguoshaReason(card, "gain"), String.valueOf(actor.getUserId()));
         return header
            + "\n"
            + card.getEmoji()
            + " 大家一起吃席："
            + this.formatUser(actor)
            + " +"
            + actorGain
            + "，"
            + this.formatUser(target)
            + " +"
            + targetGain
            + " 积分。\n这波叫和平发育，暂时没有人翻桌。\n"
            + this.formatSanguoshaBalance(actor, target);
      }
   }

   private String playSelfGainCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header) {
      int amount = this.sanguoshaCardService.randomPoints(card);
      int gained = this.pointsStore.addPoints(actor, amount, this.buildSanguoshaReason(card, "self_gain"), String.valueOf(target.getUserId()));
      return header
         + "\n"
         + card.getEmoji()
         + " "
         + this.formatUser(actor)
         + " 无中生有，凭空摸出 "
         + gained
         + " 积分。军师沉默，账本通过。\n"
         + this.formatSanguoshaBalance(actor, target);
   }

   private String playTargetLoseCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header, boolean selfTarget) {
      int amount = this.sanguoshaCardService.randomPoints(card);
      int lost = this.deductSanguoshaPoints(target, amount, card, "target_lose", actor.getUserId());
      if (lost <= 0) {
         return header + "\n" + card.getEmoji() + " 桥拆了，积分没动：目标可用积分不足。\n" + this.formatSanguoshaBalance(actor, target);
      } else {
         String targetLabel = selfTarget ? "你自己" : this.formatUser(target);
         return header + "\n" + card.getEmoji() + " " + targetLabel + " 被拆掉 " + lost + " 积分，桥没了，面子也有点晃。\n" + this.formatSanguoshaBalance(actor, target);
      }
   }

   private String playBothLoseCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header, boolean selfTarget) {
      int amount = this.sanguoshaCardService.randomPoints(card);
      int actorLost = this.deductSanguoshaPoints(actor, amount, card, "both_lose", target.getUserId());
      if (selfTarget) {
         return header + "\n" + card.getEmoji() + " 南蛮入侵单刷版，你减少 " + actorLost + " 积分，场面一度很个人。\n" + this.formatSanguoshaBalance(actor, target);
      } else {
         int targetLost = this.deductSanguoshaPoints(target, amount, card, "both_lose", actor.getUserId());
         return header
            + "\n"
            + card.getEmoji()
            + " 南蛮入侵！双方各尝试减少 "
            + amount
            + " 积分。实际变化："
            + this.formatUser(actor)
            + " -"
            + actorLost
            + "，"
            + this.formatUser(target)
            + " -"
            + targetLost
            + "。\n"
            + this.formatSanguoshaBalance(actor, target);
      }
   }

   private String playDuelCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header, boolean selfTarget) {
      if (selfTarget) {
         return header + "\n⚔ 你和自己决斗三百回合，最后左手宣布和右手握手言和。积分不变。\n" + this.formatSanguoshaBalance(actor, target);
      } else {
         int amount = this.sanguoshaCardService.randomPoints(card);
         boolean actorWins = this.random.nextBoolean();
         PointsProfile winner = actorWins ? actor : target;
         PointsProfile loser = actorWins ? target : actor;
         int moved = this.moveSanguoshaPoints(loser, winner, amount, card, "duel");
         return moved <= 0
            ? header + "\n⚔ 决斗打得很响，但败方可用积分不足，本回合未移动。\n" + this.formatSanguoshaBalance(actor, target)
            : header
               + "\n⚔ 决斗结果："
               + this.formatUser(winner)
               + " 获胜，从 "
               + this.formatUser(loser)
               + " 那里转移 "
               + moved
               + " 积分。\n战报已记录，群友作证。\n"
               + this.formatSanguoshaBalance(actor, target);
      }
   }

   private String playNoEffectCard(PointsProfile actor, PointsProfile target, SanguoshaCard card, String header) {
      return header + "\n" + card.getEmoji() + " 牌面很帅，效果很稳：本回合没有积分变化。双方状态保持不变。\n" + this.formatSanguoshaBalance(actor, target);
   }

   private int moveSanguoshaPoints(PointsProfile from, PointsProfile to, int amount, SanguoshaCard card, String action) {
      return from.getUserId() != to.getUserId() && amount > 0
         ? this.foamBagService
            .transfer(from, to, amount, false, this.buildSanguoshaReason(card, action + "_out"), this.buildSanguoshaReason(card, action + "_in"), true)
            .transferredPoints()
         : 0;
   }

   private int deductSanguoshaPoints(PointsProfile profile, int amount, SanguoshaCard card, String action, long refUserId) {
      if (amount <= 0) {
         return 0;
      } else {
         int deducted = this.pointsStore.addPoints(profile, -amount, this.buildSanguoshaReason(card, action), String.valueOf(refUserId));
         return Math.max(0, -deducted);
      }
   }

   private String buildSanguoshaHeader(PointsProfile actor, PointsProfile target, SanguoshaCard card) {
      String targetLabel = actor.getUserId() == target.getUserId() ? "自己" : this.formatUser(target);
      return String.format("\ud83c\udfb4 %s 对 %s 发动 %s【%s】", this.formatUser(actor), targetLabel, card.getEmoji(), card.getName());
   }

   private String buildSanguoshaReason(SanguoshaCard card, String action) {
      return "三国杀-" + card.getName() + "-" + action;
   }

   private String formatSanguoshaBalance(PointsProfile actor, PointsProfile target) {
      return actor.getUserId() == target.getUserId()
         ? String.format("\ud83d\udcca %s 当前积分：%d", this.formatUser(actor), actor.getPoints())
         : String.format("\ud83d\udcca %s：%d | %s：%d", this.formatUser(actor), actor.getPoints(), this.formatUser(target), target.getPoints());
   }

   private void handleMessagePoints(Message message) {
      if (message.getFrom() != null && !message.getFrom().getIsBot()) {
         long chatId = message.getChatId();
         if (this.isGroupChat(message)) {
            PointsProfile profile = this.pointsStore
               .getOrCreate(chatId, message.getFrom().getId(), message.getFrom().getUserName(), this.displayName(message.getFrom()));
            LocalDate today = LocalDate.now();
            this.pointsStore.updateDailyMessageStats(profile, today);
            int earned = this.calculateMessagePoints(message);
            if (earned > 0) {
               if (profile.getDailyMessagePoints() < this.config.getDailyMessagePointsLimit()) {
                  int allowed = Math.min(earned, this.config.getDailyMessagePointsLimit() - profile.getDailyMessagePoints());
                  profile.setDailyMessagePoints(profile.getDailyMessagePoints() + allowed);
                  profile.setDailyMessageCount(profile.getDailyMessageCount() + 1);
                  this.pointsStore.addPoints(profile, allowed, "message", null);
               }
            }
         }
      }
   }

   private CheckinResult doCheckin(PointsProfile profile) {
      LocalDate today = LocalDate.now();
      if (today.equals(profile.getLastCheckinDate())) {
         return CheckinResult.builder().alreadyCheckedIn(true).totalPoints(profile.getPoints()).build();
      } else {
         int streak;
         if (profile.getLastCheckinDate() != null && today.minusDays(1L).equals(profile.getLastCheckinDate())) {
            streak = profile.getCheckinStreak() + 1;
         } else {
            streak = 1;
         }

         profile.setCheckinStreak(streak);
         profile.setLastCheckinDate(today);
         boolean penalty = this.random.nextInt(100) < this.config.getCheckinPenaltyChance();
         int delta = penalty
            ? -this.randomRange(this.config.getCheckinPenaltyMin(), this.config.getCheckinPenaltyMax())
            : this.randomRange(this.config.getCheckinBaseMin(), this.config.getCheckinBaseMax());
         if (streak % this.config.getStreakBonusEvery() == 0 && !penalty) {
            delta += this.config.getStreakBonusPoints();
         }

         profile.setLastCheckinDate(today);
         profile.setCheckinStreak(streak);
         int appliedDelta = this.pointsStore.addPoints(profile, delta, "checkin", null);
         String message = appliedDelta >= 0 ? String.format("+%d 积分", appliedDelta) : String.format("%d 积分", appliedDelta);
         return CheckinResult.builder().alreadyCheckedIn(false).delta(appliedDelta).streak(streak).totalPoints(profile.getPoints()).message(message).build();
      }
   }

   private String displayName(User user) {
      String name = user.getFirstName();
      if (user.getLastName() != null && !user.getLastName().isBlank()) {
         name = name + " " + user.getLastName();
      }

      return name != null ? name : "用户" + user.getId();
   }

   private String formatUser(PointsProfile profile) {
      return profile.getUsername() != null && !profile.getUsername().isBlank() ? "@" + profile.getUsername() : profile.getDisplayName();
   }

   private String buildTelegramRedeemChannelDetail(String telegramUsername, long telegramUserId) {
      if (StringUtils.hasText(telegramUsername)) {
         String normalizedUsername = telegramUsername.startsWith("@") ? telegramUsername : "@" + telegramUsername;
         return "Telegram积分兑换(" + normalizedUsername + ")";
      } else {
         return "Telegram积分兑换(" + telegramUserId + ")";
      }
   }

   private String buildLeaderboard(List<PointsProfile> list) {
      StringBuilder builder = new StringBuilder("\ud83c\udfc6 积分排行榜\n");
      int rank = 1;

      for (PointsProfile profile : list) {
         builder.append(rank++).append(". ").append(this.formatUser(profile)).append("：").append(profile.getPoints()).append(" 积分").append("\n");
      }

      return builder.toString();
   }

   private int calculateMessagePoints(Message message) {
      if (message.hasPhoto()) {
         return 2;
      } else if (message.hasVideo() || message.hasAnimation()) {
         return 3;
      } else if (message.hasDocument() || message.hasAudio() || message.hasVoice()) {
         return 2;
      } else if (message.hasSticker()) {
         return 1;
      } else {
         return message.hasText() ? 1 : 0;
      }
   }

   private boolean isGroupChat(Message message) {
      return message.getChat() != null && (message.getChat().isGroupChat() || message.getChat().isSuperGroupChat());
   }

   private boolean isAllowedChat(Message message) {
      if (!this.isGroupChat(message)) {
         return true;
      } else {
         return !StringUtils.hasText(this.config.getGroupChatId()) ? true : this.config.getGroupChatId().equals(String.valueOf(message.getChatId()));
      }
   }

   private Long resolvePointsChatId(Message message) {
      if (this.isGroupChat(message)) {
         return message.getChatId();
      } else if (StringUtils.hasText(this.config.getGroupChatId())) {
         try {
            return Long.parseLong(this.config.getGroupChatId());
         } catch (NumberFormatException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean isAdminUser(User user, TelegramBotPermission permission) {
      return user != null && this.telegramBotAuthorizationService.hasPermission(user.getId(), permission);
   }

   private int randomRange(int min, int max) {
      return max <= min ? min : this.random.nextInt(max - min + 1) + min;
   }

   private boolean botReady() {
      return this.botApiClient != null && this.botApiClient.isReady();
   }

   private boolean tryStartGroupGame(Message message, String gameCommand) {
      User user = message.getFrom();
      if (user == null) {
         return false;
      } else {
         TelegramGameRateLimiter.AdmissionDecision decision = this.telegramGameRateLimiter.tryAcquireGame(message.getChatId(), user.getId(), gameCommand);
         if (!decision.allowed()) {
            this.notifyGameRateLimited(message, decision);
            return false;
         } else {
            try {
               this.telegramGameRateLimiter.awaitMessageTurn(message.getChatId());
               return true;
            } catch (TelegramGameRateLimiter.GameRateLimitException var6) {
               log.warn("Telegram 游戏消息发送队列繁忙: chatId={}, error={}", message.getChatId(), var6.getMessage());
               this.notifyGameRateLimited(message, TelegramGameRateLimiter.AdmissionDecision.groupLimited(2L));
               return false;
            }
         }
      }
   }

   private void notifyGameRateLimited(Message message, TelegramGameRateLimiter.AdmissionDecision decision) {
      long chatId = message.getChatId();
      if (!this.telegramGameRateLimiter.shouldSendLimitNotice(chatId)) {
         this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
      } else {
         String text = switch (decision.reason()) {
            case USER_TOO_FAST -> String.format("\ud83c\udfae 操作太快啦，本次游戏已拦截，请 %d 秒后再试。", Math.max(1L, decision.retryAfterSeconds()));
            case PROTECTION_UNAVAILABLE -> "\ud83d\udee1️ 游戏流量保护暂时不可用。为避免触发 Telegram 限流，已暂停新游戏，请稍后再试。";
            default -> String.format("\ud83c\udfae 群内游戏请求太多。为避免触发 Telegram 限流，本次已拦截，请约 %d 秒后再试。", Math.max(1L, decision.retryAfterSeconds()));
         };

         try {
            this.telegramGameRateLimiter.awaitLimitNoticeTurn(chatId);
            this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), text, 30);
         } catch (TelegramGameRateLimiter.GameRateLimitException var7) {
            log.warn("Telegram 游戏限流提示发送队列繁忙: chatId={}, error={}", chatId, var7.getMessage());
            this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
         }
      }
   }

   private void sendMessage(long chatId, String text) {
      if (this.botReady()) {
         try {
            this.botApiClient.sendMessage(chatId, text);
         } catch (TelegramBotApiClient.TelegramBotApiException var5) {
            log.warn("积分机器人发送消息失败: {}", var5.getMessage());
         }
      }
   }

   private void sendReply(long chatId, long replyToMessageId, String text) {
      if (this.botReady()) {
         try {
            this.botApiClient.sendMessage(chatId, text, replyToMessageId, null, null, false);
         } catch (TelegramBotApiClient.TelegramBotApiException var7) {
            log.warn("积分机器人发送消息失败: {}", var7.getMessage());
         }
      }
   }

   private void sendMessageAndDelete(long chatId, String text, int delaySeconds) {
      if (this.botReady()) {
         try {
            TelegramBotApiClient.ApiMessage sentMessage = this.botApiClient.sendMessage(chatId, text);
            if (sentMessage != null && delaySeconds > 0) {
               this.sharedScheduler.schedule(() -> {
                  try {
                     this.botApiClient.deleteMessage(chatId, sentMessage.getMessageId());
                  } catch (TelegramBotApiClient.TelegramBotApiException var5x) {
                     log.warn("删除消息失败: {}", var5x.getMessage());
                  }
               }, (long)delaySeconds, TimeUnit.SECONDS);
            }
         } catch (TelegramBotApiClient.TelegramBotApiException var6) {
            log.warn("积分机器人发送消息失败: {}", var6.getMessage());
         }
      }
   }

   private void sendReplyAndDelete(long chatId, long replyToMessageId, String text, int delaySeconds) {
      this.sendReplyAndDelete(chatId, replyToMessageId, text, delaySeconds, false);
   }

   private void sendHtmlReplyAndDelete(long chatId, long replyToMessageId, String text, int delaySeconds) {
      this.sendReplyAndDelete(chatId, replyToMessageId, text, delaySeconds, true);
   }

   private void sendReplyAndDelete(long chatId, long replyToMessageId, String text, int delaySeconds, boolean isHtml) {
      if (this.botReady()) {
         try {
            TelegramBotApiClient.ApiMessage sentMessage = this.botApiClient.sendMessage(chatId, text, replyToMessageId, isHtml ? "HTML" : null, null, false);
            if (sentMessage != null && delaySeconds > 0) {
               this.sharedScheduler.schedule(() -> {
                  try {
                     this.botApiClient.deleteMessage(chatId, sentMessage.getMessageId());
                  } catch (TelegramBotApiClient.TelegramBotApiException var5) {
                     log.warn("删除消息失败: {}", var5.getMessage());
                  }
               }, (long)delaySeconds, TimeUnit.SECONDS);
            }
         } catch (TelegramBotApiClient.TelegramBotApiException var9) {
            log.warn("积分机器人发送消息失败: {}", var9.getMessage());
         }
      }
   }

   private void sendReplyAndDeleteBoth(long chatId, long replyToMessageId, String text, int delaySeconds) {
      this.sendReplyAndDeleteBoth(chatId, replyToMessageId, text, delaySeconds, false);
   }

   private void sendHtmlReplyAndDeleteBoth(long chatId, long replyToMessageId, String text, int delaySeconds) {
      this.sendReplyAndDeleteBoth(chatId, replyToMessageId, text, delaySeconds, true);
   }

   private void sendReplyAndDeleteBoth(long chatId, long replyToMessageId, String text, int delaySeconds, boolean isHtml) {
      if (this.botReady()) {
         try {
            TelegramBotApiClient.ApiMessage sentMessage = this.botApiClient.sendMessage(chatId, text, replyToMessageId, isHtml ? "HTML" : null, null, false);
            if (delaySeconds <= 0) {
               return;
            }

            this.sharedScheduler.schedule(() -> {
               if (sentMessage != null) {
                  this.deleteMessagesSilently(chatId, sentMessage.getMessageId(), replyToMessageId);
               } else {
                  this.deleteMessageSilently(chatId, replyToMessageId);
               }
            }, (long)delaySeconds, TimeUnit.SECONDS);
         } catch (TelegramBotApiClient.TelegramBotApiException var9) {
            log.warn("积分机器人发送消息失败: {}", var9.getMessage());
            this.deleteMessageDelayed(chatId, replyToMessageId, delaySeconds);
         }
      }
   }

   private boolean sendLocatorReply(Message triggerMessage, long targetMessageId, String text) {
      if (this.botReady() && targetMessageId > 0L) {
         long chatId = triggerMessage.getChatId();

         try {
            TelegramBotApiClient.ApiMessage locator = this.botApiClient.sendMessage(chatId, text, targetMessageId, null, null, false);
            if (locator == null) {
               return false;
            } else {
               this.sharedScheduler
                  .schedule(() -> this.deleteMessagesSilently(chatId, locator.getMessageId(), triggerMessage.getMessageId()), 5L, TimeUnit.SECONDS);
               return true;
            }
         } catch (TelegramBotApiClient.TelegramBotApiException var8) {
            log.warn("发送 Telegram 面板定位提示失败: chatId={}, targetMessageId={}, error={}", chatId, targetMessageId, var8.getMessage());
            return false;
         }
      } else {
         return false;
      }
   }

   private void deleteMessagesSilently(long chatId, long... messageIds) {
      try {
         this.botApiClient.deleteMessages(chatId, messageIds);
      } catch (TelegramBotApiClient.TelegramBotApiException var5) {
         log.warn("批量删除消息失败: {}", var5.getMessage());
      }
   }

   private void deleteMessageSilently(long chatId, long messageId) {
      try {
         this.botApiClient.deleteMessage(chatId, messageId);
      } catch (TelegramBotApiClient.TelegramBotApiException var6) {
         log.warn("删除消息失败: {}", var6.getMessage());
      }
   }

   private void deleteMessageDelayed(long chatId, long messageId, int delaySeconds) {
      if (this.botReady()) {
         this.sharedScheduler.schedule(() -> this.deleteMessageSilently(chatId, messageId), (long)delaySeconds, TimeUnit.SECONDS);
      }
   }

   private void handleScratchWins(Message message) {
      if (this.isGroupChat(message)) {
         this.handleGroupScratchWins(message);
      } else {
         this.handlePrivateScratchWins(message);
      }
   }

   private void handleGroupScratchWins(Message message) {
      long chatId = message.getChatId();

      try {
         Long activeMessageId = this.getActiveScratchWinsMessageId(chatId);
         if (activeMessageId != null) {
            this.locateGroupScratchWins(message, activeMessageId);
         } else {
            String lockKey = "foam:points-bot:scratch:wins:panel-lock:" + chatId;
            String lockToken = this.redisLockUtils.tryLock(lockKey, 5L);
            if (!StringUtils.hasText(lockToken)) {
               this.sharedScheduler.schedule(() -> this.locateGroupScratchWinsAfterCreation(message), 250L, TimeUnit.MILLISECONDS);
            } else {
               try {
                  activeMessageId = this.getActiveScratchWinsMessageId(chatId);
                  if (activeMessageId == null) {
                     this.publishGroupScratchWins(message);
                     return;
                  }

                  this.locateGroupScratchWins(message, activeMessageId);
               } finally {
                  this.redisLockUtils.unlock(lockKey, lockToken);
               }
            }
         }
      } catch (RuntimeException var11) {
         log.warn("查询雾中刮刮乐大奖榜单缓存失败: chatId={}, error={}", chatId, var11.getMessage());
         this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⚠️ 大奖记录暂时无法读取，请稍后再试。", 10);
      }
   }

   private void locateGroupScratchWinsAfterCreation(Message message) {
      try {
         Long activeMessageId = this.getActiveScratchWinsMessageId(message.getChatId());
         if (activeMessageId != null) {
            this.locateGroupScratchWins(message, activeMessageId);
            return;
         }

         this.sendReplyAndDeleteBoth(message.getChatId(), message.getMessageId(), "⏳ 大奖记录正在生成，请稍后再试。", 5);
      } catch (RuntimeException var3) {
         log.warn("定位雾中刮刮乐大奖榜单失败: chatId={}, error={}", message.getChatId(), var3.getMessage());
         this.deleteMessageDelayed(message.getChatId(), message.getMessageId(), 5);
      }
   }

   private void publishGroupScratchWins(Message message) {
      long chatId = message.getChatId();
      ScratchCardGameService.JackpotPage page = this.scratchCardGameService.listJackpotRecords(chatId, null, LocalDateTime.now(), 0, 10);
      if (page.records().isEmpty()) {
         this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "\ud83c\udf01 本群暂时还没有雾中刮刮乐大奖记录。", 10);
      } else {
         try {
            this.telegramGameRateLimiter.awaitMessageTurn(chatId);
            TelegramBotApiClient.ApiMessage sentMessage = this.botApiClient
               .sendMessage(chatId, this.renderScratchWins("雾中刮刮乐最近10条大奖记录", page.records(), 0, false), message.getMessageId(), "HTML", null, false);
            if (sentMessage == null) {
               throw new TelegramBotApiClient.TelegramBotApiException("Telegram 未返回大奖榜单消息");
            }

            this.deleteMessageDelayed(chatId, sentMessage.getMessageId(), 30);
            this.deleteMessageDelayed(chatId, message.getMessageId(), 5);

            try {
               this.stringRedisTemplate
                  .opsForValue()
                  .set(this.scratchWinsPanelKey(chatId), String.valueOf(sentMessage.getMessageId()), Duration.ofSeconds(30L));
            } catch (RuntimeException var7) {
               log.warn("缓存雾中刮刮乐大奖榜单消息失败: chatId={}, messageId={}, error={}", chatId, sentMessage.getMessageId(), var7.getMessage());
            }
         } catch (TelegramGameRateLimiter.GameRateLimitException | TelegramBotApiClient.TelegramBotApiException var8) {
            log.warn("发送雾中刮刮乐大奖榜单失败: chatId={}, error={}", chatId, var8.getMessage());
            this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⚠️ 大奖记录暂时没有浮上来，请稍后再试。", 10);
         }
      }
   }

   private void locateGroupScratchWins(Message message, long activeMessageId) {
      try {
         this.telegramGameRateLimiter.awaitMessageTurn(message.getChatId());
         if (this.sendLocatorReply(message, activeMessageId, "\ud83d\udccd 大奖记录已经在群里展示，点击上方引用消息即可定位。")) {
            return;
         }

         this.stringRedisTemplate.delete(this.scratchWinsPanelKey(message.getChatId()));
         this.publishGroupScratchWins(message);
      } catch (TelegramGameRateLimiter.GameRateLimitException var5) {
         log.warn("发送雾中刮刮乐大奖榜单定位提示失败: chatId={}, error={}", message.getChatId(), var5.getMessage());
         this.deleteMessageDelayed(message.getChatId(), message.getMessageId(), 5);
      }
   }

   private Long getActiveScratchWinsMessageId(long chatId) {
      String value = this.stringRedisTemplate.opsForValue().get(this.scratchWinsPanelKey(chatId));
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         try {
            long messageId = Long.parseLong(value);
            return messageId > 0L ? messageId : null;
         } catch (NumberFormatException var6) {
            this.stringRedisTemplate.delete(this.scratchWinsPanelKey(chatId));
            return null;
         }
      }
   }

   private String scratchWinsPanelKey(long chatId) {
      return "foam:points-bot:scratch:wins:panel:" + chatId;
   }

   private void handlePrivateScratchWins(Message message) {
      User user = message.getFrom();
      if (!this.isAdminUser(user, TelegramBotPermission.SCRATCH_RECORD_VIEW)) {
         this.sendMessage(message.getChatId(), "❌ 当前账号没有查看刮刮乐大奖记录的权限。");
      } else {
         Long pointsChatId = this.resolvePointsChatId(message);
         if (pointsChatId == null) {
            this.sendMessage(message.getChatId(), "⚠️ 未配置积分群，暂时无法查询刮刮乐大奖记录。");
         } else {
            try {
               LocalDateTime asOf = LocalDateTime.now();
               PointsBot.ScratchWinsSession session = this.createScratchWinsSession(user.getId(), pointsChatId, asOf);
               ScratchCardGameService.JackpotPage page = this.loadAdminScratchWinsPage(session, 0);
               this.botApiClient
                  .sendMessage(
                     message.getChatId(),
                     this.renderAdminScratchWins(page.records(), 0),
                     null,
                     "HTML",
                     this.buildScratchWinsKeyboard(session.token(), 0, page.hasNext()),
                     false
                  );
            } catch (RuntimeException var7) {
               log.warn("打开管理员刮刮乐大奖记录失败: userId={}, error={}", user.getId(), var7.getMessage());
               this.sendMessage(message.getChatId(), "⚠️ 刮刮乐大奖记录暂时无法读取，请稍后再试。");
            }
         }
      }
   }

   private void handleScratchWinsCallback(Update update) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      User user = callbackQuery.getFrom();
      Message callbackMessage = callbackQuery.getMessage();
      if (user == null || callbackMessage == null || this.isGroupChat(callbackMessage) || callbackMessage.getChatId() != user.getId()) {
         this.answerScratchWinsCallback(callbackQuery, "请在机器人私聊管理面板中查看", true);
      } else if (!this.isAdminUser(user, TelegramBotPermission.SCRATCH_RECORD_VIEW)) {
         this.answerScratchWinsCallback(callbackQuery, "当前账号没有查看权限", true);
      } else {
         String[] parts = callbackQuery.getData().split(":", 3);
         if (parts.length != 3 || !StringUtils.hasText(parts[1])) {
            this.answerScratchWinsCallback(callbackQuery, "这个记录面板已经失效", true);
         } else if ("noop".equals(parts[2])) {
            this.answerScratchWinsCallback(callbackQuery, null, false);
         } else {
            int pageNumber;
            try {
               pageNumber = Integer.parseInt(parts[2]);
            } catch (NumberFormatException var9) {
               this.answerScratchWinsCallback(callbackQuery, "页码无效", true);
               return;
            }

            if (pageNumber >= 0 && pageNumber <= 10000) {
               try {
                  PointsBot.ScratchWinsSession session = this.getScratchWinsSession(parts[1]);
                  if (session == null || session.userId() != user.getId()) {
                     this.answerScratchWinsCallback(callbackQuery, "面板已过期，请从管理中心重新打开", true);
                     return;
                  }

                  ScratchCardGameService.JackpotPage page = this.loadAdminScratchWinsPage(session, pageNumber);
                  if (pageNumber > 0 && page.records().isEmpty()) {
                     this.answerScratchWinsCallback(callbackQuery, "已经没有更多记录了", false);
                     return;
                  }

                  this.stringRedisTemplate
                     .opsForValue()
                     .set("foam:points-bot:scratch:wins:session:" + session.token(), session.serialize(), SCRATCH_WINS_SESSION_TTL);
                  this.botApiClient
                     .editMessageText(
                        callbackMessage.getChatId(),
                        callbackMessage.getMessageId(),
                        this.renderAdminScratchWins(page.records(), pageNumber),
                        "HTML",
                        this.buildScratchWinsKeyboard(session.token(), pageNumber, page.hasNext())
                     );
                  this.answerScratchWinsCallback(callbackQuery, null, false);
               } catch (RuntimeException var10) {
                  log.warn("翻页查看管理员刮刮乐大奖记录失败: userId={}, error={}", user.getId(), var10.getMessage());
                  this.answerScratchWinsCallback(callbackQuery, "记录暂时无法读取，请稍后再试", true);
               }
            } else {
               this.answerScratchWinsCallback(callbackQuery, "页码无效", true);
            }
         }
      }
   }

   private PointsBot.ScratchWinsSession createScratchWinsSession(long userId, long chatId, LocalDateTime asOf) {
      String token = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
      PointsBot.ScratchWinsSession session = new PointsBot.ScratchWinsSession(token, userId, chatId, asOf);
      this.stringRedisTemplate.opsForValue().set("foam:points-bot:scratch:wins:session:" + token, session.serialize(), SCRATCH_WINS_SESSION_TTL);
      return session;
   }

   private PointsBot.ScratchWinsSession getScratchWinsSession(String token) {
      String value = this.stringRedisTemplate.opsForValue().get("foam:points-bot:scratch:wins:session:" + token);
      return PointsBot.ScratchWinsSession.parse(token, value);
   }

   private ScratchCardGameService.JackpotPage loadAdminScratchWinsPage(PointsBot.ScratchWinsSession session, int pageNumber) {
      return this.scratchCardGameService.listJackpotRecords(session.chatId(), session.asOf().minusDays(7L), session.asOf(), pageNumber * 10, 10);
   }

   private String renderAdminScratchWins(List<PointsBotScratchEntry> records, int pageNumber) {
      return this.renderScratchWins("近7天雾中刮刮乐大奖记录", records, pageNumber, true);
   }

   private String renderScratchWins(String title, List<PointsBotScratchEntry> records, int pageNumber, boolean showPage) {
      StringBuilder builder = new StringBuilder("\ud83c\udf01 <b>").append(escapeTelegramHtml(title)).append("</b>\n");
      if (showPage) {
         builder.append("\ud83d\udcc4 第 ").append(pageNumber + 1).append(" 页\n");
      }

      if (records.isEmpty()) {
         return builder.append("\n\ud83c\udf19 暂无大奖记录").toString();
      } else {
         int sequence = pageNumber * 10 + 1;

         for (PointsBotScratchEntry entry : records) {
            builder.append("\n\ud83d\udc8e <b>")
               .append(sequence++)
               .append(".</b> ")
               .append(this.formatScratchCardUser(entry))
               .append("\n\ud83d\udcb0 中奖积分：<b>")
               .append(entry.getRewardPoints() == null ? 0 : entry.getRewardPoints())
               .append("</b>")
               .append("\n⏰ 中奖时间：")
               .append(entry.getSettledAt() == null ? "-" : entry.getSettledAt().format(SCRATCH_WIN_TIME_FORMATTER))
               .append("\n");
         }

         return builder.toString().stripTrailing();
      }
   }

   private JSONObject buildScratchWinsKeyboard(String token, int pageNumber, boolean hasNext) {
      List<List<TelegramBotApiClient.InlineButton>> rows = new ArrayList<>();
      List<TelegramBotApiClient.InlineButton> navigation = new ArrayList<>();
      if (pageNumber > 0) {
         navigation.add(new TelegramBotApiClient.InlineButton("⬅️ 上一页", "scratchwins:" + token + ":" + (pageNumber - 1)));
      }

      navigation.add(new TelegramBotApiClient.InlineButton("\ud83d\udcc4 第 " + (pageNumber + 1) + " 页", "scratchwins:" + token + ":noop"));
      if (hasNext) {
         navigation.add(new TelegramBotApiClient.InlineButton("下一页 ➡️", "scratchwins:" + token + ":" + (pageNumber + 1)));
      }

      rows.add(navigation);
      rows.add(List.of(new TelegramBotApiClient.InlineButton("\ud83d\udee1️ 返回管理中心", "start_panel:admin")));
      return TelegramBotApiClient.inlineKeyboard(rows);
   }

   private void answerScratchWinsCallback(CallbackQuery callbackQuery, String text, boolean alert) {
      try {
         if (StringUtils.hasText(text)) {
            this.botApiClient.answerCallbackQuery(callbackQuery.getId(), text, alert);
         } else {
            this.botApiClient.answerCallbackQuery(callbackQuery.getId());
         }
      } catch (TelegramBotApiClient.TelegramBotApiException var5) {
         log.debug("回答雾中刮刮乐大奖记录按钮失败: {}", var5.getMessage());
      }
   }

   private void handleScratchCardCommand(Message message) {
      if (!this.isGameCommandEnabled("scratch")) {
         this.sendReplyAndDelete(message.getChatId(), message.getMessageId(), "\ud83c\udfae 当前游戏命令已关闭，请联系管理员。", 10);
      } else {
         if (this.isGroupChat(message)) {
            PointsBotScratchRound active = this.scratchCardGameService.findActiveRound(message.getChatId());
            if (active != null) {
               try {
                  this.telegramGameRateLimiter.awaitMessageTurn(message.getChatId());
                  this.handleExistingScratchCard(message, active);
               } catch (TelegramGameRateLimiter.GameRateLimitException var4) {
                  log.warn("发送雾中刮刮乐定位提示失败: chatId={}, error={}", message.getChatId(), var4.getMessage());
                  this.deleteMessageDelayed(message.getChatId(), message.getMessageId(), 5);
               }

               return;
            }
         }

         this.runGameCommand(message, "scratch", () -> this.handleScratchCard(message));
      }
   }

   private void handleScratchCard(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "❌ 雾中刮刮乐仅限群聊使用！");
      } else {
         PointsBotScratchRound active = this.scratchCardGameService.findActiveRound(chatId);
         if (active != null) {
            this.handleExistingScratchCard(message, active);
         } else {
            LocalDateTime drawAt = LocalDateTime.now().plusSeconds(60L);
            PointsBotScratchRound round = this.scratchCardGameService.createRound(chatId, drawAt);
            this.startScratchRoundTask();
            if (round.getMessageId() != null) {
               if (!this.locateActiveScratchRound(message, round)) {
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "\ud83c\udf01 本群已有一轮雾中刮刮乐正在进行，请直接点击游戏面板中的雾滴。", 10);
               }
            } else {
               try {
                  ScratchCardGameService.RoundView view = this.scratchCardGameService.getRoundView(round.getId());
                  TelegramBotApiClient.ApiMessage sentMessage = this.botApiClient
                     .sendMessage(chatId, this.renderScratchCardPanel(view), message.getMessageId(), "HTML", this.buildScratchCardKeyboard(view), false);
                  this.scratchCardGameService.updateMessageId(round.getId(), sentMessage.getMessageId());
                  this.deleteMessageDelayed(chatId, message.getMessageId(), 10);
               } catch (Exception var9) {
                  log.error("创建雾中刮刮乐面板失败: chatId={}, roundId={}", chatId, round.getId(), var9);
                  this.scratchCardGameService.cancelRound(round.getId());
                  this.stopScratchRoundTaskIfIdle();
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⚠️ 雾中刮刮乐暂时没有浮上来，请稍后再试。", 10);
               }
            }
         }
      }
   }

   private void handleExistingScratchCard(Message triggerMessage, PointsBotScratchRound round) {
      this.startScratchRoundTask();
      if (!this.locateActiveScratchRound(triggerMessage, round)) {
         this.sendReplyAndDeleteBoth(triggerMessage.getChatId(), triggerMessage.getMessageId(), "\ud83c\udf01 本群已有一轮雾中刮刮乐正在进行，请直接点击游戏面板中的雾滴。", 10);
      }
   }

   private boolean locateActiveScratchRound(Message triggerMessage, PointsBotScratchRound round) {
      return round != null && round.getMessageId() != null && this.refreshScratchCardPanel(round.getId())
         ? this.sendLocatorReply(triggerMessage, round.getMessageId(), "\ud83d\udccd 本群已有一轮雾中刮刮乐正在进行，点击上方引用消息即可定位游戏面板。")
         : false;
   }

   private void handleScratchCardCallback(Update update) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      User user = callbackQuery.getFrom();
      Message callbackMessage = callbackQuery.getMessage();
      if (user != null && callbackMessage != null) {
         long chatId = callbackMessage.getChatId();
         if (!this.telegramGameRateLimiter.tryAcquireAction(chatId, user.getId())) {
            this.answerScratchCallback(callbackQuery, "\ud83c\udf01 操作太快，请稍后再点", false);
         } else {
            String[] parts = callbackQuery.getData().split(":");
            if (parts.length != 3) {
               this.answerScratchCallback(callbackQuery, "这个雾滴已经失效", true);
            } else {
               long roundId;
               int cellNumber;
               try {
                  roundId = Long.parseLong(parts[1]);
                  cellNumber = Integer.parseInt(parts[2]);
               } catch (NumberFormatException var13) {
                  this.answerScratchCallback(callbackQuery, "这个雾滴已经失效", true);
                  return;
               }

               try {
                  ScratchCardGameService.JoinResult result = this.scratchCardGameService
                     .join(roundId, chatId, cellNumber, user.getId(), user.getUserName(), this.displayName(user));
                  switch (result.status()) {
                     case JOINED:
                        this.answerScratchCallback(callbackQuery, String.format("\ud83c\udf01 已占 %d 号格，扣除 %d 积分", cellNumber, 50), false);
                        if (result.full()) {
                           this.settleScratchCardRound(roundId);
                        } else {
                           this.scheduleScratchCardPanelRefresh(roundId);
                        }
                        break;
                     case ALREADY_JOINED:
                        this.answerScratchCallback(callbackQuery, "每轮只能选择一个雾滴", true);
                        break;
                     case CELL_OCCUPIED:
                        this.answerScratchCallback(callbackQuery, "这个雾滴已经被选走啦", false);
                        break;
                     case INSUFFICIENT_POINTS:
                        this.answerScratchCallback(callbackQuery, String.format("积分不足，参与需要 %d 积分", 50), true);
                        break;
                     case EXPIRED:
                        this.answerScratchCallback(callbackQuery, "已到开奖时间，正在开奖", false);
                        this.settleScratchCardRound(roundId);
                        break;
                     case CLOSED:
                        this.answerScratchCallback(callbackQuery, "本轮已经开奖", false);
                        break;
                     case WRONG_CHAT:
                        this.answerScratchCallback(callbackQuery, "这个雾滴不属于当前群聊", true);
                        break;
                     default:
                        this.answerScratchCallback(callbackQuery, "这个雾滴不可用", false);
                  }
               } catch (RuntimeException var12) {
                  log.error("雾中刮刮乐占格失败: chatId={}, roundId={}, userId={}, cell={}", chatId, roundId, user.getId(), cellNumber, var12);
                  this.answerScratchCallback(callbackQuery, "占格失败，未扣积分，请稍后再试", true);
               }
            }
         }
      }
   }

   private void answerScratchCallback(CallbackQuery callbackQuery, String text, boolean alert) {
      try {
         this.botApiClient.answerCallbackQuery(callbackQuery.getId(), text, alert);
      } catch (TelegramBotApiClient.TelegramBotApiException var5) {
         log.debug("回答雾中刮刮乐按钮失败: {}", var5.getMessage());
      }
   }

   public void settleScratchCardRound(long roundId) {
      try {
         ScratchCardGameService.SettlementResult result = this.scratchCardGameService.settle(roundId);
         if (result.status() == ScratchCardGameService.SettlementStatus.SETTLED || result.status() == ScratchCardGameService.SettlementStatus.CANCELLED) {
            this.syncScratchCardSettlementPanel(roundId, 0);
            this.stopScratchRoundTaskIfIdle();
         }
      } catch (RuntimeException var4) {
         log.error("雾中刮刮乐开奖失败: roundId={}", roundId, var4);
      }
   }

   public void settleDueScratchCardRounds() {
      for (Long roundId : this.scratchCardGameService.findDueRoundIds(LocalDateTime.now())) {
         this.settleScratchCardRound(roundId);
      }
   }

   void startScratchRoundTask() {
      synchronized (this.scratchRoundTaskMonitor) {
         if (this.scratchRoundTask == null || this.scratchRoundTask.isDone() || this.scratchRoundTask.isCancelled()) {
            this.scratchRoundTask = this.sharedScheduler.scheduleWithFixedDelay(this::runScratchRoundTask, 1L, 1L, TimeUnit.SECONDS);
            log.debug("雾中刮刮乐推进任务已启动");
         }
      }
   }

   private void resumeScratchRoundTaskIfNeeded() {
      try {
         if (this.scratchCardGameService.hasOpenRounds()) {
            this.startScratchRoundTask();
         }
      } catch (RuntimeException var2) {
         log.warn("恢复雾中刮刮乐推进任务失败: {}", var2.getMessage());
      }
   }

   private void runScratchRoundTask() {
      if (!this.enabled) {
         this.stopScratchRoundTask();
      } else if (this.isEnabled()) {
         try {
            this.settleDueScratchCardRounds();
            this.stopScratchRoundTaskIfIdle();
         } catch (RuntimeException var2) {
            log.error("执行雾中刮刮乐推进任务失败", (Throwable)var2);
         }
      }
   }

   private void stopScratchRoundTaskIfIdle() {
      synchronized (this.scratchRoundTaskMonitor) {
         if (this.scratchRoundTask != null && !this.scratchCardGameService.hasOpenRounds()) {
            ScheduledFuture<?> task = this.scratchRoundTask;
            this.scratchRoundTask = null;
            task.cancel(false);
            log.debug("雾中刮刮乐已无进行中轮次，推进任务已暂停");
         }
      }
   }

   private void stopScratchRoundTask() {
      synchronized (this.scratchRoundTaskMonitor) {
         if (this.scratchRoundTask != null) {
            ScheduledFuture<?> task = this.scratchRoundTask;
            this.scratchRoundTask = null;
            task.cancel(false);
            log.debug("雾中刮刮乐推进任务已暂停");
         }
      }
   }

   private boolean refreshScratchCardPanel(long roundId) {
      synchronized (this.scratchPanelRefreshLock(roundId)) {
         ScratchCardGameService.RoundView view = this.scratchCardGameService.getRoundView(roundId);
         if (view != null && view.round().getMessageId() != null && this.botReady()) {
            boolean var10000;
            try {
               this.botApiClient
                  .editMessageText(
                     view.round().getChatId(), view.round().getMessageId(), this.renderScratchCardPanel(view), "HTML", this.buildScratchCardKeyboard(view)
                  );
               var10000 = true;
            } catch (TelegramBotApiClient.TelegramBotApiException var7) {
               if (this.isTelegramMessageNotModified(var7)) {
                  return true;
               }

               log.warn("更新雾中刮刮乐面板失败: roundId={}, error={}", roundId, var7.getMessage());
               return false;
            }

            return var10000;
         } else {
            return false;
         }
      }
   }

   private void scheduleScratchCardPanelRefresh(long roundId) {
      if (this.pendingScratchPanelRefreshes.add(roundId)) {
         try {
            this.sharedScheduler.schedule(() -> {
               this.pendingScratchPanelRefreshes.remove(roundId);
               this.refreshScratchCardPanel(roundId);
            }, 300L, TimeUnit.MILLISECONDS);
         } catch (RuntimeException var4) {
            this.pendingScratchPanelRefreshes.remove(roundId);
            log.warn("调度雾中刮刮乐面板刷新失败: roundId={}, error={}", roundId, var4.getMessage());
         }
      }
   }

   private void syncScratchCardSettlementPanel(long roundId, int retryIndex) {
      if (this.refreshScratchCardPanel(roundId)) {
         this.scheduleScratchCardResultDeletion(roundId);
      } else {
         if (retryIndex < SCRATCH_RESULT_REFRESH_RETRY_DELAYS_SECONDS.length) {
            long delaySeconds = SCRATCH_RESULT_REFRESH_RETRY_DELAYS_SECONDS[retryIndex];

            try {
               this.sharedScheduler.schedule(() -> this.syncScratchCardSettlementPanel(roundId, retryIndex + 1), delaySeconds, TimeUnit.SECONDS);
               return;
            } catch (RuntimeException var7) {
               log.warn("调度雾中刮刮乐开奖结果刷新重试失败: roundId={}, error={}", roundId, var7.getMessage());
            }
         }

         this.sendScratchCardSettlementFallback(roundId);
      }
   }

   private void scheduleScratchCardResultDeletion(long roundId) {
      ScratchCardGameService.RoundView view = this.scratchCardGameService.getRoundView(roundId);
      if (view != null && view.round().getMessageId() != null) {
         this.deleteMessageDelayed(view.round().getChatId(), view.round().getMessageId(), 180);
      }
   }

   private void sendScratchCardSettlementFallback(long roundId) {
      ScratchCardGameService.RoundView view = this.scratchCardGameService.getRoundView(roundId);
      if (view != null && this.botReady()) {
         Long previousMessageId = view.round().getMessageId();

         try {
            this.telegramGameRateLimiter.awaitMessageTurn(view.round().getChatId());
            TelegramBotApiClient.ApiMessage sentMessage = this.botApiClient
               .sendMessage(view.round().getChatId(), this.renderScratchCardPanel(view), null, "HTML", null, false);
            if (sentMessage == null) {
               log.warn("补发雾中刮刮乐开奖结果失败: roundId={}, error=Telegram 未返回消息", roundId);
               return;
            }

            this.scratchCardGameService.updateMessageId(roundId, sentMessage.getMessageId());
            if (previousMessageId != null && previousMessageId != sentMessage.getMessageId()) {
               this.deleteMessageSilently(view.round().getChatId(), previousMessageId);
            }

            this.deleteMessageDelayed(view.round().getChatId(), sentMessage.getMessageId(), 180);
         } catch (RuntimeException var6) {
            log.error("补发雾中刮刮乐开奖结果失败: roundId={}", roundId, var6);
         }
      } else {
         log.warn("补发雾中刮刮乐开奖结果失败: roundId={}, error=机器人未就绪或轮次不存在", roundId);
      }
   }

   private boolean isTelegramMessageNotModified(TelegramBotApiClient.TelegramBotApiException exception) {
      return exception.getMessage() != null && exception.getMessage().toLowerCase(Locale.ROOT).contains("message is not modified");
   }

   private Object scratchPanelRefreshLock(long roundId) {
      int index = Math.floorMod(Long.hashCode(roundId), this.scratchPanelRefreshLocks.length);
      return this.scratchPanelRefreshLocks[index];
   }

   private static Object[] createScratchPanelRefreshLocks(int count) {
      Object[] locks = new Object[count];

      for (int index = 0; index < count; index++) {
         locks[index] = new Object();
      }

      return locks;
   }

   private String renderScratchCardPanel(ScratchCardGameService.RoundView view) {
      PointsBotScratchRound round = view.round();
      List<PointsBotScratchEntry> entries = view.entries();
      boolean open = "OPEN".equals(round.getStatus());
      StringBuilder builder = new StringBuilder("\ud83c\udf01 <b>雾中刮刮乐</b>\n\n");
      if (open) {
         builder.append("每格消耗：<b>")
            .append(50)
            .append(" 积分</b>\n")
            .append("开奖时间：<b>")
            .append(round.getDrawAt().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
            .append("</b>（满 9 格立即开奖）\n")
            .append("奖励：有概率开出 666 / 777 / 3888 分\n\n")
            .append("点击一个雾滴占格，开奖后积分自动到账。");
      } else if ("CANCELLED".equals(round.getStatus())) {
         builder.append("本轮无人参与，已自动结束。");
      } else {
         builder.append("\ud83c\udf89 <b>本轮已开奖，奖励已自动到账</b>");
         if (round.getJackpotCell() != null) {
            builder.append("\n\ud83d\udc8e ").append(round.getJackpotCell()).append(" 号格刮出了雾中大奖！");
         }
      }

      builder.append("\n\n<b>参与用户</b>\n");
      if (entries.isEmpty()) {
         builder.append("暂时无人参与");
         return builder.toString();
      } else {
         int sequence = 1;

         for (PointsBotScratchEntry entry : entries) {
            builder.append(sequence++).append(". ").append(this.formatScratchCardUser(entry)).append(" · ").append(entry.getCellNumber()).append(" 号雾滴");
            if (entry.getRewardPoints() != null) {
               builder.append(" · ").append(Boolean.TRUE.equals(entry.getJackpot()) ? "\ud83d\udc8e " : "+").append(entry.getRewardPoints()).append(" 分");
            }

            builder.append("\n");
         }

         return builder.toString().stripTrailing();
      }
   }

   private String formatScratchCardUser(PointsBotScratchEntry entry) {
      String label;
      if (StringUtils.hasText(entry.getUsername())) {
         label = entry.getUsername().startsWith("@") ? entry.getUsername() : "@" + entry.getUsername();
      } else if (StringUtils.hasText(entry.getDisplayName())) {
         label = entry.getDisplayName();
      } else {
         label = "用户" + entry.getUserId();
      }

      return String.format("<a href=\"tg://user?id=%d\">%s</a>", entry.getUserId(), escapeTelegramHtml(label));
   }

   private JSONObject buildScratchCardKeyboard(ScratchCardGameService.RoundView view) {
      Map<Integer, PointsBotScratchEntry> entriesByCell = new HashMap<>();

      for (PointsBotScratchEntry entry : view.entries()) {
         entriesByCell.put(entry.getCellNumber(), entry);
      }

      boolean open = "OPEN".equals(view.round().getStatus());
      List<List<TelegramBotApiClient.InlineButton>> rows = new ArrayList<>();

      for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
         List<TelegramBotApiClient.InlineButton> row = new ArrayList<>();

         for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
            int cell = rowIndex * 3 + columnIndex + 1;
            PointsBotScratchEntry entry = entriesByCell.get(cell);
            String text;
            if (open) {
               text = entry == null ? "\ud83c\udf01 " + cell : "✅ " + cell;
            } else if (entry == null) {
               text = "▫️ " + cell;
            } else if (Boolean.TRUE.equals(entry.getJackpot())) {
               text = "\ud83d\udc8e " + cell + "·" + entry.getRewardPoints();
            } else {
               text = "\ud83c\udf81 " + cell + "·" + entry.getRewardPoints();
            }

            row.add(new TelegramBotApiClient.InlineButton(text, "scratch:" + view.round().getId() + ":" + cell));
         }

         rows.add(row);
      }

      return TelegramBotApiClient.inlineKeyboard(rows);
   }

   private void handleBrain(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "\ud83e\udde0 Brain 需要先在积分群中使用 /brain 发起，再到私聊答题。");
      } else {
         PointsBotBrainRound open = this.brainGameService.findOpenRound(chatId);
         if (open != null) {
            this.startBrainRoundTask();
            this.refreshBrainPanel(open.getId());
            this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "\ud83e\udde0 本群已有一局 Brain 正在进行，请直接使用现有游戏面板。", 10);
         } else {
            BrainGameConfig gameConfig = this.gameConfigService.getBrainConfig();
            PointsBotBrainRound round = this.brainGameService.createRound(chatId, gameConfig);
            this.startBrainRoundTask();

            try {
               BrainGameService.RoundView view = this.brainGameService.getRoundView(round.getId());
               TelegramBotApiClient.ApiMessage sent = this.botApiClient
                  .sendMessage(chatId, this.renderBrainPanel(view), message.getMessageId(), "HTML", this.buildBrainKeyboard(view), false);
               this.brainGameService.updateMessageId(round.getId(), sent.getMessageId());
               this.deleteMessageDelayed(chatId, message.getMessageId(), 10);
            } catch (RuntimeException var9) {
               log.error("创建 Brain 游戏面板失败: chatId={}, roundId={}", chatId, round.getId(), var9);
               this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⚠️ Brain 游戏暂时启动失败，请稍后再试。", 10);
            }
         }
      }
   }

   private void handleBrainCallback(Update update) {
      CallbackQuery callback = update.getCallbackQuery();
      User user = callback.getFrom();
      Message callbackMessage = callback.getMessage();
      if (user != null && callbackMessage != null) {
         if (!this.telegramGameRateLimiter.tryAcquireAction(callbackMessage.getChatId(), user.getId())) {
            this.answerBrainCallback(callback, "操作太快，请稍后再试", false);
         } else {
            String[] parts = callback.getData().split(":");
            if (parts.length == 3 && "join".equals(parts[1])) {
               long roundId;
               try {
                  roundId = Long.parseLong(parts[2]);
               } catch (NumberFormatException var10) {
                  this.answerBrainCallback(callback, "这个 Brain 按钮已经失效", true);
                  return;
               }

               try {
                  BrainGameService.JoinResult result = this.brainGameService
                     .join(roundId, callbackMessage.getChatId(), user.getId(), user.getUserName(), this.displayName(user));
                  switch (result.status()) {
                     case JOINED:
                        this.answerBrainCallback(callback, "报名成功，已扣除 " + result.round().getEntryCost() + " 积分", false);
                        this.refreshBrainPanel(roundId);
                        break;
                     case ALREADY_JOINED:
                        this.answerBrainCallback(callback, "你已经报名本轮", false);
                        break;
                     case INSUFFICIENT_POINTS:
                        this.answerBrainCallback(callback, "积分不足，需要 " + result.round().getEntryCost() + " 积分", true);
                        break;
                     case DAILY_LIMIT:
                        this.answerBrainCallback(callback, "今天的 Brain 参与次数已用完", true);
                        break;
                     case CHAMPION_LIMIT:
                        this.answerBrainCallback(callback, "今天已达到冠军奖励上限", true);
                        break;
                     case FULL:
                        this.answerBrainCallback(callback, "本轮报名已满", true);
                        break;
                     case EXPIRED:
                        this.answerBrainCallback(callback, "报名已经截止", false);
                        break;
                     case WRONG_CHAT:
                        this.answerBrainCallback(callback, "这个按钮不属于当前群聊", true);
                        break;
                     default:
                        this.answerBrainCallback(callback, "本轮已不能报名", false);
                  }
               } catch (RuntimeException var9) {
                  log.error("Brain 报名失败: roundId={}, userId={}", roundId, user.getId(), var9);
                  this.answerBrainCallback(callback, "报名失败，未扣积分或积分会自动回滚", true);
               }
            } else {
               this.answerBrainCallback(callback, "这个 Brain 按钮已经失效", true);
            }
         }
      }
   }

   private void answerBrainCallback(CallbackQuery callback, String text, boolean alert) {
      try {
         this.botApiClient.answerCallbackQuery(callback.getId(), text, alert);
      } catch (TelegramBotApiClient.TelegramBotApiException var5) {
         log.debug("回答 Brain 按钮失败: {}", var5.getMessage());
      }
   }

   private boolean handleBrainPrivateStart(Message message, String text) {
      if (!this.isGroupChat(message) && message.getFrom() != null) {
         Matcher matcher = Pattern.compile("^/start(?:@\\w+)?\\s+brain_(\\d+)$", 2).matcher(text);
         if (!matcher.matches()) {
            return false;
         } else {
            long roundId = Long.parseLong(matcher.group(1));
            BrainGameService.PrivateSession session = this.brainGameService.findPrivateSession(roundId, message.getFrom().getId());
            if (session == null) {
               this.sendMessage(message.getChatId(), "这局 Brain 不存在、尚未开始，或你没有报名。");
               return true;
            } else {
               int attempts = "BULLS_AND_COWS".equals(session.round().getQuestionType()) ? 8 : 2;
               this.sendMessage(message.getChatId(), "\ud83e\udde0 已进入 Brain #" + roundId + " 私聊答题\n请直接发送答案。最多 " + attempts + " 次提交；群内结算前不会公开对错。");
               return true;
            }
         }
      } else {
         return false;
      }
   }

   private boolean trySubmitBrainAnswer(Message message) {
      if (message.getFrom() != null && this.brainGameService.findPrivateSession(message.getFrom().getId()) != null) {
         BrainGameService.SubmissionResult result = this.brainGameService.submit(message.getFrom().getId(), message.getText());
         switch (result.status()) {
            case RECEIVED:
            case RECEIVED_CORRECT:
               int count = result.entry().getSubmissionCount() == null ? 0 : result.entry().getSubmissionCount();
               if (StringUtils.hasText(result.feedback())) {
                  this.sendMessage(message.getChatId(), "第 " + count + " 次：" + result.feedback() + "\n继续猜或等待结算。");
               } else {
                  this.sendMessage(message.getChatId(), "✅ 已收取第 " + count + " 次答案，结算前不公布对错。");
               }
               break;
            case ALREADY_CORRECT:
               this.sendMessage(message.getChatId(), "答案已记录，请等待本轮结算。");
               break;
            case ATTEMPT_LIMIT:
               this.sendMessage(message.getChatId(), "本轮提交次数已用完，请等待结算。");
               break;
            case CLOSED:
            case NO_ACTIVE_ROUND:
               this.sendMessage(message.getChatId(), "本轮答题已经结束。");
         }

         return true;
      } else {
         return false;
      }
   }

   public void processBrainRounds() {
      LocalDateTime now = LocalDateTime.now();

      for (Long roundId : this.brainGameService.findRegistrationDueRoundIds(now)) {
         try {
            BrainGameService.RegistrationResult result = this.brainGameService.closeRegistration(roundId);
            if (result.status() == BrainGameService.RegistrationStatus.STARTED
               || result.status() == BrainGameService.RegistrationStatus.CANCELLED
               || result.status() == BrainGameService.RegistrationStatus.INVALID) {
               this.refreshBrainPanel(roundId);
            }
         } catch (RuntimeException var6) {
            log.error("推进 Brain 报名阶段失败: roundId={}", roundId, var6);
         }
      }

      for (Long roundId : this.brainGameService.findQuestionsToHide(now)) {
         this.brainGameService.markQuestionHidden(roundId);
         this.refreshBrainPanel(roundId);
      }

      for (Long roundId : this.brainGameService.findSettlementDueRoundIds(now)) {
         try {
            BrainGameService.SettlementResult result = this.brainGameService.settle(roundId);
            if (result.status() == BrainGameService.SettlementStatus.SETTLED) {
               this.refreshBrainPanel(roundId);
               this.sendBrainSettlementAnnouncement(result);
            }
         } catch (RuntimeException var5) {
            log.error("Brain 结算失败: roundId={}", roundId, var5);
         }
      }
   }

   void startBrainRoundTask() {
      synchronized (this.brainRoundTaskMonitor) {
         if (this.brainRoundTask == null || this.brainRoundTask.isDone() || this.brainRoundTask.isCancelled()) {
            this.brainRoundTask = this.sharedScheduler.scheduleWithFixedDelay(this::runBrainRoundTask, 1L, 1L, TimeUnit.SECONDS);
            log.debug("Brain 推进任务已启动");
         }
      }
   }

   private void resumeBrainRoundTaskIfNeeded() {
      try {
         if (this.brainGameService.hasOpenRounds()) {
            this.startBrainRoundTask();
         }
      } catch (RuntimeException var2) {
         log.warn("恢复 Brain 推进任务失败: {}", var2.getMessage());
      }
   }

   private void runBrainRoundTask() {
      if (!this.enabled) {
         this.stopBrainRoundTask();
      } else if (this.isEnabled()) {
         try {
            this.processBrainRounds();
            this.stopBrainRoundTaskIfIdle();
         } catch (RuntimeException var2) {
            log.error("执行 Brain 推进任务失败", (Throwable)var2);
         }
      }
   }

   private void stopBrainRoundTaskIfIdle() {
      synchronized (this.brainRoundTaskMonitor) {
         if (this.brainRoundTask != null && !this.brainGameService.hasOpenRounds()) {
            ScheduledFuture<?> task = this.brainRoundTask;
            this.brainRoundTask = null;
            task.cancel(false);
            log.debug("Brain 已无进行中轮次，推进任务已暂停");
         }
      }
   }

   private void stopBrainRoundTask() {
      synchronized (this.brainRoundTaskMonitor) {
         if (this.brainRoundTask != null) {
            ScheduledFuture<?> task = this.brainRoundTask;
            this.brainRoundTask = null;
            task.cancel(false);
            log.debug("Brain 推进任务已暂停");
         }
      }
   }

   private void refreshBrainPanel(long roundId) {
      BrainGameService.RoundView view = this.brainGameService.getRoundView(roundId);
      if (view != null && view.round().getMessageId() != null && this.botReady()) {
         try {
            this.botApiClient
               .editMessageText(view.round().getChatId(), view.round().getMessageId(), this.renderBrainPanel(view), "HTML", this.buildBrainKeyboard(view));
         } catch (TelegramBotApiClient.TelegramBotApiException var5) {
            log.warn("更新 Brain 面板失败: roundId={}, error={}", roundId, var5.getMessage());
         }
      }
   }

   private void sendBrainSettlementAnnouncement(BrainGameService.SettlementResult result) {
      if (result != null && result.round() != null && result.entries() != null && this.botReady()) {
         PointsBotBrainEntry champion = result.entries().stream().filter(entry -> Integer.valueOf(1).equals(entry.getRankNo())).findFirst().orElse(null);
         if (champion != null) {
            PointsBotBrainRound round = result.round();
            String championship = Boolean.TRUE.equals(round.getPeak()) ? "Brain 巅峰局冠军" : "Brain 脑力挑战冠军";
            BrainGameService.RoundView view = new BrainGameService.RoundView(round, result.entries(), result.jackpotAfter());
            String announcement = "\ud83c\udf89 恭喜 " + this.formatBrainUser(champion) + " 获得了 " + championship + "！\n\n" + this.renderBrainPanel(view);

            try {
               this.botApiClient.sendMessage(round.getChatId(), announcement, null, "HTML", null, false);
            } catch (TelegramBotApiClient.TelegramBotApiException var8) {
               log.warn("发送 Brain 冠军结算消息失败: roundId={}, error={}", round.getId(), var8.getMessage());
            }
         }
      }
   }

   private String renderBrainPanel(BrainGameService.RoundView view) {
      PointsBotBrainRound round = view.round();
      List<PointsBotBrainEntry> entries = view.entries();
      String title = Boolean.TRUE.equals(round.getPeak()) ? "\ud83c\udfc6 Brain 巅峰局" : "\ud83e\udde0 Brain 脑力挑战";
      StringBuilder text = new StringBuilder(title).append(" #").append(round.getId()).append("\n\n");
      if ("REGISTERING".equals(round.getStatus())) {
         text.append("报名积分：")
            .append(round.getEntryCost())
            .append(" 分\n")
            .append("报名人数：")
            .append(entries.size())
            .append("/")
            .append(round.getMaxPlayers())
            .append("（至少 ")
            .append(round.getMinPlayers())
            .append(" 人）\n")
            .append("报名截止：")
            .append(round.getRegistrationEndsAt().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
            .append("\n")
            .append("当前累积奖池：")
            .append(view.jackpotPoints())
            .append(" 分\n\n")
            .append("点击下方按钮报名。人数不足会全额退款；报名成功的有效局计入每日次数。");
         this.appendBrainRegistrationList(text, entries);
      } else if ("CANCELLED".equals(round.getStatus())) {
         text.append("报名人数不足，本轮取消，报名积分已全部退回。").append(Boolean.TRUE.equals(round.getPeak()) ? "\n巅峰局资格保留，下一次 /brain 仍是巅峰局。" : "");
      } else if ("INVALID".equals(round.getStatus())) {
         text.append("题目生成校验失败，本轮作废，报名积分已全部退回且不计次数。");
      } else if ("ACTIVE".equals(round.getStatus())) {
         boolean hidden = round.getHiddenPrompt() != null && round.getQuestionHidesAt() == null;
         text.append(escapeTelegramHtml(hidden ? round.getHiddenPrompt() : round.getQuestionPrompt()))
            .append("\n\n答题截止：")
            .append(round.getAnswerEndsAt().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
            .append("\n已报名 ")
            .append(entries.size())
            .append(" 人。请点击按钮到私聊提交，群内不公开答案。");
      } else {
         text.append("本轮已结算\n")
            .append("正确答案：")
            .append(escapeTelegramHtml(round.getExplanation()))
            .append("\n")
            .append("本轮奖池：")
            .append(round.getTotalPot())
            .append(" 分\n")
            .append("累积奖池：")
            .append(round.getJackpotAfter())
            .append(" 分\n\n");
         List<PointsBotBrainEntry> ranked = entries.stream()
            .filter(entryx -> entryx.getRankNo() != null)
            .sorted(Comparator.comparing(PointsBotBrainEntry::getRankNo))
            .toList();
         if (ranked.isEmpty()) {
            text.append("无人答对，本轮未发放奖励。");
         } else {
            for (PointsBotBrainEntry entry : ranked) {
               text.append(entry.getRankNo())
                  .append(". ")
                  .append(this.formatBrainUser(entry))
                  .append(" +")
                  .append(entry.getRewardPoints() == null ? 0 : entry.getRewardPoints())
                  .append(" 分")
                  .append(formatBrainElapsedTime(round, entry))
                  .append("\n");
            }
         }
      }

      return text.toString().stripTrailing();
   }

   private void appendBrainRegistrationList(StringBuilder text, List<PointsBotBrainEntry> entries) {
      text.append("\n\n<b>已报名用户</b>\n");
      if (entries.isEmpty()) {
         text.append("暂时无人报名");
      } else {
         int sequence = 1;

         for (PointsBotBrainEntry entry : entries) {
            text.append(sequence++).append(". ").append(this.formatBrainUser(entry)).append("\n");
         }
      }
   }

   private String formatBrainUser(PointsBotBrainEntry entry) {
      String label;
      if (StringUtils.hasText(entry.getUsername())) {
         label = entry.getUsername().startsWith("@") ? entry.getUsername() : "@" + entry.getUsername();
      } else if (StringUtils.hasText(entry.getDisplayName())) {
         label = entry.getDisplayName();
      } else {
         label = "用户" + entry.getUserId();
      }

      return String.format("<a href=\"tg://user?id=%d\">%s</a>", entry.getUserId(), escapeTelegramHtml(label));
   }

   private static String formatBrainElapsedTime(PointsBotBrainRound round, PointsBotBrainEntry entry) {
      if (round.getStartedAt() == null) {
         return "";
      } else {
         LocalDateTime completedAt = entry.getEffectiveCorrectAt() != null ? entry.getEffectiveCorrectAt() : entry.getCorrectAt();
         if (completedAt == null) {
            return "";
         } else {
            long elapsedSeconds = Math.max(0L, Duration.between(round.getStartedAt(), completedAt).getSeconds());
            return " · ⏰ " + elapsedSeconds + " 秒";
         }
      }
   }

   private static String escapeTelegramHtml(String value) {
      return HtmlUtils.htmlEscape(value == null ? "" : value, StandardCharsets.UTF_8.name());
   }

   private JSONObject buildBrainKeyboard(BrainGameService.RoundView view) {
      PointsBotBrainRound round = view.round();
      List<List<TelegramBotApiClient.InlineButton>> rows = new ArrayList<>();
      if ("REGISTERING".equals(round.getStatus())) {
         rows.add(List.of(new TelegramBotApiClient.InlineButton("报名 Brain（" + round.getEntryCost() + " 分）", "brain:join:" + round.getId())));
      } else if ("ACTIVE".equals(round.getStatus()) && StringUtils.hasText(this.config.getBotName())) {
         String botName = this.config.getBotName().startsWith("@") ? this.config.getBotName().substring(1) : this.config.getBotName();
         rows.add(List.of(TelegramBotApiClient.InlineButton.url("私聊提交答案", "https://t.me/" + botName + "?start=brain_" + round.getId())));
      }

      return rows.isEmpty() ? null : TelegramBotApiClient.inlineKeyboard(rows);
   }

   private void handleHellDice(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "\ud83d\udd25 地狱骰仅限积分群聊使用。");
      } else {
         User user = message.getFrom();
         if (user != null && !Boolean.TRUE.equals(user.getIsBot())) {
            String[] args = message.getText().trim().split("\\s+");
            if (args.length != 2) {
               this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "\ud83d\udd25 用法：/helldice <积分数>\n例如：/helldice 10", 15);
            } else {
               int bet;
               try {
                  bet = Integer.parseInt(args[1]);
               } catch (NumberFormatException var12) {
                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "下注积分必须是整数。", 15);
                  return;
               }

               HellDiceGameConfig gameConfig = this.gameConfigService.getHellDiceConfig();
               HellDiceGameService.RoundView round = null;

               try {
                  round = this.hellDiceGameService.startRound(chatId, user.getId(), user.getUserName(), this.displayName(user), bet, gameConfig);
                  TelegramBotApiClient.ApiMessage sent = this.botApiClient
                     .sendMessage(chatId, this.renderHellPanel(round), message.getMessageId(), "HTML", this.buildHellKeyboard(round), false);
                  round = this.hellDiceGameService.bindMessage(round.id(), chatId, sent.getMessageId());
                  this.deleteMessageDelayed(chatId, message.getMessageId(), 15);
                  this.startHellRoundTask();
               } catch (HellDiceGameService.RuleViolation var13) {
                  if (var13.getCode() == HellDiceGameService.RuleCode.VAULT_UNAVAILABLE) {
                     this.sendHellVaultUnavailable(message, gameConfig, var13.getMessage());
                  } else {
                     this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "\ud83d\udd25 " + var13.getMessage(), 15);
                  }
               } catch (RuntimeException var14) {
                  log.error("地狱骰开局失败: chatId={}, userId={}", chatId, user.getId(), var14);
                  if (round != null) {
                     try {
                        this.hellDiceGameService.refundAfterTransportFailure(round.id());
                     } catch (RuntimeException var11) {
                        var14.addSuppressed(var11);
                     }
                  }

                  this.sendReplyAndDeleteBoth(chatId, message.getMessageId(), "⚠️ 地狱骰开局失败；如已扣分，系统已尝试自动退款。", 15);
               }
            }
         }
      }
   }

   private void sendHellVaultUnavailable(Message message, HellDiceGameConfig gameConfig, String reason) {
      HellDiceGameService.VaultView vault = this.hellDiceGameService.getVaultView(message.getChatId(), gameConfig);
      String text = "\ud83d\udd25 <b>地狱骰暂时停盘</b>\n\n"
         + escapeTelegramHtml(reason)
         + "。\n当前地狱金库：<b>"
         + vault.balance()
         + "</b> 积分\n\n可以点击下方按钮提醒管理员补充金库；同一群30分钟内只会提醒一次。";
      List<List<TelegramBotApiClient.InlineButton>> keyboard = List.of(List.of(new TelegramBotApiClient.InlineButton("\ud83d\udd14 提醒管理员补充金库", "hell:remind")));

      try {
         this.botApiClient.sendMessage(message.getChatId(), text, message.getMessageId(), "HTML", TelegramBotApiClient.inlineKeyboard(keyboard), false);
         this.deleteMessageDelayed(message.getChatId(), message.getMessageId(), 15);
      } catch (TelegramBotApiClient.TelegramBotApiException var8) {
         log.warn("发送地狱骰停盘提示失败: chatId={}, error={}", message.getChatId(), var8.getMessage());
      }
   }

   private void handleHellRankingCommand(Message message) {
      if (!this.isGroupChat(message)) {
         this.sendMessage(message.getChatId(), "\ud83d\udc51 地狱之王排行仅限积分群聊查看。");
      } else if (!this.isGameCommandEnabled("hell_dice")) {
         this.sendReplyAndDelete(message.getChatId(), message.getMessageId(), "\ud83c\udfae 地狱骰当前已关闭，请联系管理员。", 10);
      } else {
         HellDiceGameConfig gameConfig = this.gameConfigService.getHellDiceConfig();
         List<HellDiceGameService.RankingEntry> ranking = this.hellDiceGameService.weeklyRanking(message.getChatId(), gameConfig.getLeaderboardLimit());
         StringBuilder text = new StringBuilder("\ud83d\udc51 <b>本周地狱之王</b>\n\n");
         if (ranking.isEmpty()) {
            text.append("本周还没有有效成绩，使用 /helldice &lt;积分&gt; 开始挑战。");
         } else {
            int rank = 1;

            for (HellDiceGameService.RankingEntry entry : ranking) {
               text.append(rank++)
                  .append(". ")
                  .append(this.formatHellUser(entry.userId(), entry.username(), entry.displayName()))
                  .append(" · ")
                  .append(entry.score())
                  .append(" 分 · 最深第 ")
                  .append(entry.bestDepth())
                  .append(" 层 · 游玩 ")
                  .append(entry.attempts())
                  .append(" 次");
               if (entry.clears() > 0) {
                  text.append(" · 通关 ").append(entry.clears()).append(" 次");
               }

               text.append("\n");
            }

            text.append("\n每天只取个人最好的一局计分，下注金额不影响排名。");
         }

         this.sendHtmlReplyAndDeleteBoth(message.getChatId(), message.getMessageId(), text.toString(), 30);
      }
   }

   private void handleHellCallback(Update update) {
      CallbackQuery callback = update.getCallbackQuery();
      User user = callback.getFrom();
      Message panel = callback.getMessage();
      String data = callback.getData();
      if (user == null || panel == null || !StringUtils.hasText(data)) {
         this.answerHellCallback(callback, "这个地狱骰按钮已经失效", true);
      } else if (data.startsWith("hell:admin:")) {
         this.handleHellVaultAdminCallback(callback, panel, user, data);
      } else if (user != null && panel != null && this.isGroupChat(panel) && this.isAllowedChat(panel)) {
         if (!this.telegramGameRateLimiter.tryAcquireAction(panel.getChatId(), user.getId())) {
            this.answerHellCallback(callback, "操作太快，请稍后再试", false);
         } else if ("hell:remind".equals(data)) {
            this.handleHellAdminReminder(callback, panel, user);
         } else {
            String[] parts = data.split(":");

            try {
               if (parts.length == 3 && "continue".equals(parts[1])) {
                  long roundId = Long.parseLong(parts[2]);
                  HellDiceGameService.ActionResult result = this.hellDiceGameService
                     .continueRound(roundId, panel.getChatId(), panel.getMessageId(), user.getId());
                  this.answerHellCallback(callback, result.action() == HellDiceGameService.Action.AUTO_CASHED_OUT ? "选择已超时，系统已自动收手" : "继续下潜，观众下注已经开放", false);
                  this.editHellPanel(result.round());
                  this.startHellRoundTask();
                  return;
               }

               if (parts.length == 3 && "cash".equals(parts[1])) {
                  long roundId = Long.parseLong(parts[2]);
                  HellDiceGameService.ActionResult result = this.hellDiceGameService.cashOut(roundId, panel.getChatId(), panel.getMessageId(), user.getId());
                  this.answerHellCallback(callback, "已安全带走 " + result.payout() + " 积分", false);
                  this.editHellPanel(result.round());
                  return;
               }

               if (parts.length == 6 && "bet".equals(parts[1])) {
                  long roundId = Long.parseLong(parts[2]);
                  int depth = Integer.parseInt(parts[3]);
                  String amount = parts[4];

                  String side = switch (amount) {
                     case "S" -> "SURVIVE";
                     case "D" -> "DIE";
                     default -> throw new NumberFormatException("invalid side");
                  };
                  int betAmount = Integer.parseInt(parts[5]);
                  HellDiceGameService.BetResult result = this.hellDiceGameService
                     .placeSpectatorBet(
                        roundId, depth, panel.getChatId(), panel.getMessageId(), user.getId(), user.getUserName(), this.displayName(user), side, betAmount
                     );
                  this.answerHellCallback(callback, "已下注 " + betAmount + " 积分：" + ("SURVIVE".equals(result.side()) ? "本层能活" : "本层会坠落"), false);
                  this.requestHellPanelRefresh(result.round().id());
                  return;
               }

               this.answerHellCallback(callback, "这个地狱骰按钮已经失效", true);
            } catch (NumberFormatException var13) {
               this.answerHellCallback(callback, "这个地狱骰按钮已经失效", true);
            } catch (HellDiceGameService.RuleViolation var14) {
               this.answerHellCallback(callback, var14.getMessage(), true);
            } catch (RuntimeException var15) {
               log.error("地狱骰按钮处理失败: chatId={}, userId={}, data={}", panel.getChatId(), user.getId(), data, var15);
               this.answerHellCallback(callback, "操作失败，积分不会重复扣除，请稍后重试", true);
            }
         }
      } else {
         this.answerHellCallback(callback, "这个按钮不属于当前积分群", true);
      }
   }

   private void handleHellAdminReminder(CallbackQuery callback, Message panel, User user) {
      String cooldownKey = "foam:points-bot:hell:admin-reminder:" + panel.getChatId();

      boolean first;
      try {
         first = Boolean.TRUE
            .equals(this.stringRedisTemplate.opsForValue().setIfAbsent(cooldownKey, String.valueOf(user.getId()), HELL_ADMIN_REMINDER_COOLDOWN));
      } catch (RuntimeException var17) {
         log.warn("地狱骰管理员提醒限流不可用，已拒绝提醒: chatId={}, error={}", panel.getChatId(), var17.getMessage());
         this.answerHellCallback(callback, "提醒保护暂时不可用，请直接联系管理员", true);
         return;
      }

      if (!first) {
         this.answerHellCallback(callback, "本群最近已经提醒过管理员，请稍后再试", false);
      } else {
         HellDiceGameConfig gameConfig = this.gameConfigService.getHellDiceConfig();
         HellDiceGameService.VaultView vault = this.hellDiceGameService.getVaultView(panel.getChatId(), gameConfig);
         int fullClearPayout = hellPayout(gameConfig.getMinBet(), 6, gameConfig);
         int minimumLiability = fullClearPayout - gameConfig.getMinBet();
         if (vault.balance() >= minimumLiability) {
            this.clearHellReminderCooldown(cooldownKey);
            this.answerHellCallback(callback, "金库已经具备最低开局额度，请重新发送 /helldice；若今日额度已满请明天再试", true);
         } else {
            Set<Long> recipients = this.telegramBotAuthorizationService.authorizedUserIds(TelegramBotPermission.POINTS_ADMIN);
            if (recipients.isEmpty()) {
               this.clearHellReminderCooldown(cooldownKey);
               this.answerHellCallback(callback, "尚未配置有积分管理权限的管理员，请直接在群内联系管理员", true);
            } else {
               String notice = "\ud83d\udd14 <b>地狱骰金库提醒</b>\n\n积分群：<code>"
                  + panel.getChatId()
                  + "</code>\n提醒用户："
                  + this.formatHellUser(user.getId(), user.getUserName(), this.displayName(user))
                  + "\n当前金库：<b>"
                  + vault.balance()
                  + "</b> / "
                  + vault.capacity()
                  + " 积分\n\n可在本私聊面板安全补充金库；操作时会重新校验当前权限和服务端额度。";
               int sentCount = 0;

               for (Long recipient : recipients) {
                  try {
                     this.botApiClient.sendMessage(recipient, notice, null, "HTML", this.buildHellVaultAdminKeyboard(vault), false);
                     sentCount++;
                  } catch (TelegramBotApiClient.TelegramBotApiException var16) {
                     log.warn("发送地狱骰管理员提醒失败: recipient={}, error={}", recipient, var16.getMessage());
                  }
               }

               if (sentCount == 0) {
                  this.clearHellReminderCooldown(cooldownKey);
                  this.answerHellCallback(callback, "提醒发送失败，请直接联系管理员", true);
               } else {
                  this.answerHellCallback(callback, "已提醒 " + sentCount + " 位有积分管理权限的管理员，本群30分钟内不会重复通知", false);
               }
            }
         }
      }
   }

   private void handleHellVaultAdminCommand(Message message) {
      User user = message.getFrom();
      if (user != null && !this.isGroupChat(message) && message.getChatId() == user.getId()) {
         if (!this.telegramBotAuthorizationService.hasPermission(user.getId(), TelegramBotPermission.POINTS_ADMIN)) {
            this.sendMessage(message.getChatId(), "⛔ 你没有积分管理权限。");
         } else {
            try {
               HellDiceGameService.VaultView vault = this.gameConfigService.getConfiguredHellDiceVault();
               this.botApiClient
                  .sendMessage(
                     message.getChatId(),
                     this.renderHellVaultAdminPanel(vault, null),
                     message.getMessageId(),
                     "HTML",
                     this.buildHellVaultAdminKeyboard(vault),
                     false
                  );
            } catch (RuntimeException var4) {
               log.warn("打开地狱骰管理员金库失败: userId={}, error={}", user.getId(), var4.getMessage());
               this.sendMessage(message.getChatId(), "⚠️ 暂时无法读取地狱骰金库，请稍后再试。");
            }
         }
      } else {
         this.sendReplyAndDelete(message.getChatId(), message.getMessageId(), "\ud83d\udd25 地狱骰金库只能由管理员在机器人私聊中管理。", 15);
      }
   }

   private void handleHellVaultAdminCallback(CallbackQuery callback, Message panel, User user, String data) {
      if (!this.isGroupChat(panel) && panel.getChatId() == user.getId()) {
         if (!this.telegramBotAuthorizationService.hasPermission(user.getId(), TelegramBotPermission.POINTS_ADMIN)) {
            this.answerHellCallback(callback, "你当前没有积分管理权限", true);
         } else if (!this.telegramGameRateLimiter.tryAcquireAction(panel.getChatId(), user.getId())) {
            this.answerHellCallback(callback, "操作太快，请稍后再试", false);
         } else {
            String pendingKey = this.hellAdminTopUpKey(user.getId(), panel.getMessageId());

            try {
               if ("hell:admin:refresh".equals(data)) {
                  if (!this.clearHellAdminTopUpConfirmation(pendingKey)) {
                     this.answerHellCallback(callback, "安全确认暂时不可用，未执行任何操作", true);
                     return;
                  }

                  HellDiceGameService.VaultView vault = this.gameConfigService.getConfiguredHellDiceVault();
                  this.editHellVaultAdminPanel(panel, vault, null);
                  this.answerHellCallback(callback, "金库数据已刷新", false);
                  return;
               }

               if ("hell:admin:cancel".equals(data)) {
                  if (!this.clearHellAdminTopUpConfirmation(pendingKey)) {
                     this.answerHellCallback(callback, "安全确认暂时不可用，未取消确认，请稍后再试", true);
                     return;
                  }

                  HellDiceGameService.VaultView vault = this.gameConfigService.getConfiguredHellDiceVault();
                  this.editHellVaultAdminPanel(panel, vault, "已取消本次补充");
                  this.answerHellCallback(callback, "已取消", false);
                  return;
               }

               if ("hell:admin:confirm".equals(data)) {
                  this.confirmHellVaultTopUp(callback, panel, user, pendingKey);
                  return;
               }

               String[] parts = data.split(":");
               if (parts.length == 4 && "prepare".equals(parts[2])) {
                  int amount = Integer.parseInt(parts[3]);
                  this.prepareHellVaultTopUp(callback, panel, amount, pendingKey);
                  return;
               }

               this.answerHellCallback(callback, "这个管理员按钮已经失效", true);
            } catch (NumberFormatException var8) {
               this.answerHellCallback(callback, "补充积分参数不合法", true);
            } catch (BizException | HellDiceGameService.RuleViolation var9) {
               this.answerHellCallback(callback, var9.getMessage(), true);
            } catch (RuntimeException var10) {
               log.error("地狱骰管理员操作失败: userId={}, data={}", user.getId(), data, var10);
               this.answerHellCallback(callback, "操作失败，未补充任何积分，请稍后再试", true);
            }
         }
      } else {
         this.answerHellCallback(callback, "管理员金库按钮只能在本人机器人私聊中使用", true);
      }
   }

   private void prepareHellVaultTopUp(CallbackQuery callback, Message panel, int amount, String pendingKey) {
      HellDiceGameService.VaultView vault = this.gameConfigService.getConfiguredHellDiceVault();
      int allowed = this.hellVaultTopUpAvailable(vault);
      if (amount > 0 && amount <= allowed) {
         try {
            this.stringRedisTemplate.opsForValue().set(pendingKey, String.valueOf(amount), HELL_ADMIN_TOP_UP_CONFIRMATION_TTL);
         } catch (RuntimeException var11) {
            log.warn("保存地狱骰补充确认失败，已拒绝操作: key={}, error={}", pendingKey, var11.getMessage());
            this.answerHellCallback(callback, "安全确认暂时不可用，未补充任何积分", true);
            return;
         }

         String text = this.renderHellVaultAdminPanel(vault, null) + "\n\n⚠️ 确认向当前积分群地狱金库补充 <b>" + amount + "</b> 积分？\n确认凭证两分钟内有效，只能在本私聊消息使用。";
         JSONObject keyboard = TelegramBotApiClient.inlineKeyboard(
            List.of(
               List.of(
                  new TelegramBotApiClient.InlineButton("✅ 确认补充 " + amount, "hell:admin:confirm"),
                  new TelegramBotApiClient.InlineButton("取消", "hell:admin:cancel")
               ),
               List.of(new TelegramBotApiClient.InlineButton("\ud83d\udee1️ 返回管理中心", "start_panel:admin"))
            )
         );

         try {
            this.editHellVaultAdminMessage(panel, text, keyboard);
         } catch (RuntimeException var10) {
            this.clearHellAdminTopUpConfirmation(pendingKey);
            throw var10;
         }

         this.answerHellCallback(callback, "请再次确认", false);
      } else {
         this.answerHellCallback(callback, "当前最多可补充 " + allowed + " 积分，请先刷新面板", true);
      }
   }

   private void confirmHellVaultTopUp(CallbackQuery callback, Message panel, User user, String pendingKey) {
      String amountValue;
      try {
         amountValue = this.stringRedisTemplate.opsForValue().getAndDelete(pendingKey);
      } catch (RuntimeException var10) {
         log.warn("读取地狱骰补充确认失败，已拒绝操作: key={}, error={}", pendingKey, var10.getMessage());
         this.answerHellCallback(callback, "安全确认暂时不可用，未补充任何积分", true);
         return;
      }

      if (!StringUtils.hasText(amountValue)) {
         this.answerHellCallback(callback, "确认已失效或已执行，请刷新金库", true);
      } else {
         int amount = Integer.parseInt(amountValue);
         HellDiceGameService.VaultView vault = this.gameConfigService.topUpHellDiceVault(amount, "TELEGRAM_ADMIN", String.valueOf(user.getId()));

         try {
            this.editHellVaultAdminPanel(panel, vault, "已安全补充 " + amount + " 积分");
            this.answerHellCallback(callback, "补充成功：" + amount + " 积分", false);
         } catch (RuntimeException var9) {
            log.warn("地狱骰金库补充成功但面板刷新失败: userId={}, amount={}, error={}", user.getId(), amount, var9.getMessage());
            this.answerHellCallback(callback, "补充已成功，但面板刷新失败；请点击刷新查看", true);
         }
      }
   }

   private String renderHellVaultAdminPanel(HellDiceGameService.VaultView vault, String status) {
      StringBuilder text = new StringBuilder("\ud83d\udd25 <b>地狱骰金库管理</b>\n\n")
         .append("当前金库：<b>")
         .append(vault.balance())
         .append("</b> / ")
         .append(vault.capacity())
         .append(" 积分\n")
         .append("累计注入：<b>")
         .append(vault.totalSubsidy())
         .append("</b> / ")
         .append(vault.subsidyLifetimeCap())
         .append(" 积分\n")
         .append("本次最多可补：<b>")
         .append(this.hellVaultTopUpAvailable(vault))
         .append("</b> 积分\n\n")
         .append("目标群由服务端积分机器人配置确定，按钮不会携带或修改群号。");
      if (StringUtils.hasText(status)) {
         text.append("\n\n✅ ").append(escapeTelegramHtml(status));
      }

      return text.toString();
   }

   private JSONObject buildHellVaultAdminKeyboard(HellDiceGameService.VaultView vault) {
      int available = this.hellVaultTopUpAvailable(vault);
      List<List<TelegramBotApiClient.InlineButton>> rows = new ArrayList<>();
      if (available > 0) {
         LinkedHashSet<Integer> candidates = new LinkedHashSet<>();
         candidates.add(Integer.valueOf(Math.min(50, available)));
         candidates.add(Integer.valueOf(Math.min(100, available)));
         candidates.add(Integer.valueOf(available));
         List<TelegramBotApiClient.InlineButton> amountButtons = candidates.stream()
            .filter(amount -> amount > 0)
            .map(amount -> new TelegramBotApiClient.InlineButton("➕ " + amount, "hell:admin:prepare:" + amount))
            .toList();
         if (!amountButtons.isEmpty()) {
            rows.add(amountButtons);
         }
      }

      rows.add(List.of(new TelegramBotApiClient.InlineButton("\ud83d\udd04 刷新金库", "hell:admin:refresh")));
      rows.add(List.of(new TelegramBotApiClient.InlineButton("\ud83d\udee1️ 返回管理中心", "start_panel:admin")));
      return TelegramBotApiClient.inlineKeyboard(rows);
   }

   private int hellVaultTopUpAvailable(HellDiceGameService.VaultView vault) {
      return Math.max(0, Math.min(100000, Math.min(vault.capacity() - vault.balance(), vault.subsidyLifetimeCap() - vault.totalSubsidy())));
   }

   private void editHellVaultAdminPanel(Message panel, HellDiceGameService.VaultView vault, String status) {
      this.editHellVaultAdminMessage(panel, this.renderHellVaultAdminPanel(vault, status), this.buildHellVaultAdminKeyboard(vault));
   }

   private void editHellVaultAdminMessage(Message panel, String text, JSONObject keyboard) {
      try {
         this.botApiClient.editMessageText(panel.getChatId(), panel.getMessageId(), text, "HTML", keyboard);
      } catch (TelegramBotApiClient.TelegramBotApiException var7) {
         if (isMessageNotModifiedError(var7)) {
            return;
         }

         if (!isTelegramTextEditUnavailable(var7)) {
            throw var7;
         }

         try {
            this.botApiClient.editMessageCaption(panel.getChatId(), panel.getMessageId(), text, "HTML", keyboard);
         } catch (TelegramBotApiClient.TelegramBotApiException var6) {
            if (!isMessageNotModifiedError(var6)) {
               throw var6;
            }
         }
      }
   }

   private static boolean isTelegramTextEditUnavailable(TelegramBotApiClient.TelegramBotApiException exception) {
      return exception != null && exception.getMessage() != null && exception.getMessage().toLowerCase(Locale.ROOT).contains("no text in the message to edit");
   }

   private String hellAdminTopUpKey(long userId, long messageId) {
      return "foam:points-bot:hell:admin-top-up:" + userId + ":" + messageId;
   }

   private boolean clearHellAdminTopUpConfirmation(String pendingKey) {
      try {
         this.stringRedisTemplate.delete(pendingKey);
         return true;
      } catch (RuntimeException var3) {
         log.warn("清理地狱骰补充确认失败: key={}, error={}", pendingKey, var3.getMessage());
         return false;
      }
   }

   private void clearHellReminderCooldown(String cooldownKey) {
      try {
         this.stringRedisTemplate.delete(cooldownKey);
      } catch (RuntimeException var3) {
         log.warn("清理地狱骰提醒冷却失败: key={}, error={}", cooldownKey, var3.getMessage());
      }
   }

   private String renderHellPanel(HellDiceGameService.RoundView round) {
      StringBuilder text = new StringBuilder("\ud83d\udd25 <b>地狱骰</b> #")
         .append(round.id())
         .append("\n\n")
         .append("玩家：")
         .append(this.formatHellUser(round.playerUserId(), round.playerUsername(), round.playerDisplayName()))
         .append("\n投入：<b>")
         .append(round.betPoints())
         .append("</b> 积分\n");
      if ("BETTING".equals(round.status())) {
         List<Integer> deathNumbers = HellDiceGameService.deathNumbers(round.targetDepth(), round.config());
         text.append("目标：第 ")
            .append(round.targetDepth())
            .append(" 层 · ")
            .append(hellDepthName(round.targetDepth()))
            .append("\n")
            .append("本层判定：<b>")
            .append(formatHellDiceRule(deathNumbers))
            .append("</b>\n")
            .append("观众池：\ud83d\ude07 ")
            .append(round.survivePool())
            .append(" / ☠️ ")
            .append(round.diePool())
            .append("\n\n")
            .append("下注结束后将自动掷骰；只有两边都有人下注时观众盘才成立，否则自动退款。");
      } else if ("ROLLING".equals(round.status())) {
         text.append("正在进入第 ")
            .append(round.targetDepth())
            .append(" 层……\n")
            .append("本层判定：<b>")
            .append(formatHellDiceRule(HellDiceGameService.deathNumbers(round.targetDepth(), round.config())))
            .append("</b>\n骰子已经封盘，请等待结果。");
      } else if ("WAITING_DECISION".equals(round.status())) {
         int nextDepth = round.currentDepth() + 1;
         int nextPayout = hellPayout(round.betPoints(), nextDepth, round.config());
         int currentProfit = round.currentPayout() - round.betPoints();
         text.append("\ud83c\udfb2 掷出 <b>")
            .append(round.lastDiceValue())
            .append("</b>：本层存活\n")
            .append("已通过：第 ")
            .append(round.currentDepth())
            .append(" 层 · ")
            .append(hellDepthName(round.currentDepth()))
            .append("\n")
            .append("本层可结算：<b>")
            .append(round.currentPayout())
            .append("</b> 积分")
            .append("（含本金 ")
            .append(round.betPoints())
            .append("，净赚 ")
            .append(currentProfit)
            .append("）\n")
            .append("下一层成功可得：<b>")
            .append(nextPayout)
            .append("</b> 积分\n")
            .append("下一层判定：<b>")
            .append(formatHellDiceRule(HellDiceGameService.deathNumbers(nextDepth, round.config())))
            .append("</b>\n\n")
            .append("超时未选择将自动收手。");
      } else if ("LOST".equals(round.status())) {
         text.append("\ud83c\udfb2 掷出 <b>")
            .append(round.lastDiceValue())
            .append("</b>：命中坠落区\n")
            .append("☠️ 坠落于第 ")
            .append(round.targetDepth())
            .append(" 层\n")
            .append("玩家结算：投入 ")
            .append(round.betPoints())
            .append(" → 实际到账 <b>0</b>｜净亏 ")
            .append(round.betPoints())
            .append("\n")
            .append("本周排行按已通过的最深层数计算。");
      } else if ("SETTLED".equals(round.status())) {
         if (round.lastDiceValue() > 0) {
            text.append("\ud83c\udfb2 最后掷出 <b>").append(round.lastDiceValue()).append("</b>：本层存活\n");
         }

         text.append("✅ 已安全离开地狱\n")
            .append("最深到达：第 ")
            .append(round.currentDepth())
            .append(" 层\n")
            .append("玩家结算：投入 ")
            .append(round.betPoints())
            .append(" → 实际到账 <b>")
            .append(round.payoutPoints())
            .append("</b>｜净赚 ")
            .append(round.payoutPoints() - round.betPoints());
         if (round.currentDepth() >= 6) {
            text.append("\n\ud83d\udc51 成功登上魔王座！");
         }
      } else if ("REFUNDED".equals(round.status())) {
         text.append("↩️ 本局因系统或Telegram异常终止\n")
            .append("玩家结算：投入 ")
            .append(round.betPoints())
            .append(" → 实际到账 <b>")
            .append(round.payoutPoints())
            .append("</b>｜盈亏 0（已退款）\n")
            .append("本局不计排行榜成绩。");
      }

      this.appendHellSpectatorSummary(text, round);
      return text.toString();
   }

   private void appendHellSpectatorSummary(StringBuilder text, HellDiceGameService.RoundView round) {
      List<HellDiceGameService.SpectatorBetView> bets = round.spectatorBets();
      if (bets != null && !bets.isEmpty()) {
         text.append("\n\n\ud83d\udc65 <b>观众下注</b>");
         int shown = Math.min(bets.size(), 20);

         for (int index = 0; index < shown; index++) {
            HellDiceGameService.SpectatorBetView bet = bets.get(index);
            text.append("\n")
               .append(this.formatHellUser(bet.userId(), bet.username(), bet.displayName()))
               .append(" · ")
               .append("SURVIVE".equals(bet.side()) ? "\ud83d\ude07 能活 " : "☠️ 会坠落 ")
               .append(bet.betPoints());
            if ("WON".equals(bet.status())) {
               text.append(" → ✅ 实际到账 ").append(bet.payoutPoints()).append("｜净赚 ").append(bet.payoutPoints() - bet.betPoints());
            } else if ("LOST".equals(bet.status())) {
               text.append(" → ❌ 实际到账 0｜净亏 ").append(bet.betPoints());
            } else if ("REFUNDED".equals(bet.status())) {
               text.append(" → ↩️ 实际到账 ").append(bet.payoutPoints()).append("｜盈亏 0（已退款）");
            } else {
               text.append(" · 待开奖");
            }
         }

         if (bets.size() > shown) {
            text.append("\n…另有 ").append(bets.size() - shown).append(" 人已下注");
         }
      }
   }

   private JSONObject buildHellKeyboard(HellDiceGameService.RoundView round) {
      List<List<TelegramBotApiClient.InlineButton>> rows = new ArrayList<>();
      if ("BETTING".equals(round.status()) && round.config().isSpectatorBetEnabled()) {
         int min = round.config().getSpectatorMinBet();
         int max = round.config().getSpectatorMaxBetPerLayer();
         rows.add(this.buildHellBetRow(round, "\ud83d\ude07 能活", "S", min, max));
         rows.add(this.buildHellBetRow(round, "☠️ 会坠落", "D", min, max));
      } else if ("WAITING_DECISION".equals(round.status())) {
         rows.add(
            List.of(
               new TelegramBotApiClient.InlineButton("\ud83d\udcb0 带走 " + round.currentPayout(), "hell:cash:" + round.id()),
               new TelegramBotApiClient.InlineButton("\ud83d\udd25 继续下潜", "hell:continue:" + round.id())
            )
         );
      }

      return rows.isEmpty() ? null : TelegramBotApiClient.inlineKeyboard(rows);
   }

   private List<TelegramBotApiClient.InlineButton> buildHellBetRow(HellDiceGameService.RoundView round, String label, String side, int min, int max) {
      List<TelegramBotApiClient.InlineButton> row = new ArrayList<>();
      row.add(new TelegramBotApiClient.InlineButton(label + " " + min, this.hellBetCallback(round, side, min)));
      if (max != min) {
         row.add(new TelegramBotApiClient.InlineButton(label + " " + max, this.hellBetCallback(round, side, max)));
      }

      return row;
   }

   private String hellBetCallback(HellDiceGameService.RoundView round, String side, int amount) {
      return "hell:bet:" + round.id() + ":" + round.targetDepth() + ":" + side + ":" + amount;
   }

   private void editHellPanel(HellDiceGameService.RoundView round) {
      if (round != null && round.messageId() != null && this.botReady()) {
         try {
            this.botApiClient.editMessageText(round.chatId(), round.messageId(), this.renderHellPanel(round), "HTML", this.buildHellKeyboard(round));
         } catch (TelegramBotApiClient.TelegramBotApiException var3) {
            if (!isMessageNotModifiedError(var3)) {
               log.warn("更新地狱骰面板失败: roundId={}, error={}", round.id(), var3.getMessage());
            }
         }

         if (isHellTerminalStatus(round.status())) {
            this.scheduleHellPanelDelete(round.chatId(), round.messageId(), round.config().getPanelRetentionSeconds());
         }
      }
   }

   private void refreshHellPanel(long roundId) {
      this.editHellPanel(this.hellDiceGameService.getView(roundId));
   }

   private void requestHellPanelRefresh(long roundId) {
      if (this.pendingHellPanelRefreshes.add(roundId)) {
         this.sharedScheduler.schedule(() -> {
            this.pendingHellPanelRefreshes.remove(roundId);
            this.refreshHellPanel(roundId);
         }, 350L, TimeUnit.MILLISECONDS);
      }
   }

   private void answerHellCallback(CallbackQuery callback, String text, boolean alert) {
      try {
         if (StringUtils.hasText(text)) {
            this.botApiClient.answerCallbackQuery(callback.getId(), text, alert);
         } else {
            this.botApiClient.answerCallbackQuery(callback.getId());
         }
      } catch (TelegramBotApiClient.TelegramBotApiException var5) {
         log.debug("回答地狱骰按钮失败: {}", var5.getMessage());
      }
   }

   private void runHellRoundTask() {
      if (!this.enabled) {
         this.stopHellRoundTask();
      } else if (this.isEnabled()) {
         try {
            for (HellDiceGameService.RoundView rolling : this.hellDiceGameService.findRollingRounds(20)) {
               try {
                  if (rolling.lastDiceValue() > 0) {
                     HellDiceGameService.RollResult result = this.hellDiceGameService.applyDice(rolling.id(), rolling.lastDiceValue());
                     this.editHellPanel(result.round());
                  } else {
                     HellDiceGameService.ActionResult result = this.hellDiceGameService.refundAfterTransportFailure(rolling.id());
                     if (result != null) {
                        this.editHellPanel(result.round());
                     }
                  }
               } catch (RuntimeException var5) {
                  log.error("恢复地狱骰掷骰状态失败: roundId={}", rolling.id(), var5);
               }
            }

            for (Long roundId : this.hellDiceGameService.findExpiredBettingRoundIds(20)) {
               this.rollHellRound(roundId);
            }

            for (Long roundId : this.hellDiceGameService.findExpiredDecisionRoundIds(20)) {
               try {
                  HellDiceGameService.ActionResult result = this.hellDiceGameService.autoCashOutExpired(roundId);
                  if (result != null) {
                     this.editHellPanel(result.round());
                  }
               } catch (RuntimeException var4) {
                  log.error("地狱骰超时自动收手失败: roundId={}", roundId, var4);
               }
            }

            this.stopHellRoundTaskIfIdle();
         } catch (RuntimeException var6) {
            log.error("执行地狱骰推进任务失败", (Throwable)var6);
         }
      }
   }

   private void rollHellRound(long roundId) {
      HellDiceGameService.RoundView rolling = this.hellDiceGameService.beginRoll(roundId);
      if (rolling != null) {
         this.editHellPanel(rolling);

         try {
            this.telegramGameRateLimiter.awaitMessageTurn(rolling.chatId());
            TelegramBotApiClient.ApiMessage dice = this.botApiClient.sendDice(rolling.chatId(), "\ud83c\udfb2");
            this.deleteMessageDelayed(rolling.chatId(), dice.getMessageId(), 6);
            this.hellDiceGameService.recordDiceValue(roundId, dice.getDiceValue());
            HellDiceGameService.RollResult result = this.hellDiceGameService.applyDice(roundId, dice.getDiceValue());
            this.sharedScheduler.schedule(() -> this.editHellPanel(result.round()), 3500L, TimeUnit.MILLISECONDS);
         } catch (TelegramGameRateLimiter.GameRateLimitException | TelegramBotApiClient.TelegramBotApiException var7) {
            log.error("地狱骰发送失败，正在安全退款: roundId={}", roundId, var7);

            try {
               HellDiceGameService.ActionResult refund = this.hellDiceGameService.refundAfterTransportFailure(roundId);
               if (refund != null) {
                  this.editHellPanel(refund.round());
               }
            } catch (RuntimeException var6) {
               log.error("地狱骰发送失败后的退款也失败: roundId={}", roundId, var6);
            }
         }
      }
   }

   void startHellRoundTask() {
      synchronized (this.hellRoundTaskMonitor) {
         if (this.hellRoundTask == null || this.hellRoundTask.isDone() || this.hellRoundTask.isCancelled()) {
            this.hellRoundTask = this.sharedScheduler.scheduleWithFixedDelay(this::runHellRoundTask, 1L, 1L, TimeUnit.SECONDS);
            log.debug("地狱骰推进任务已启动");
         }
      }
   }

   private void resumeHellRoundTaskIfNeeded() {
      if (this.hellDiceGameService != null) {
         try {
            if (this.hellDiceGameService.hasOpenRounds()) {
               this.startHellRoundTask();
            }
         } catch (RuntimeException var2) {
            log.warn("恢复地狱骰推进任务失败: {}", var2.getMessage());
         }
      }
   }

   private void stopHellRoundTaskIfIdle() {
      synchronized (this.hellRoundTaskMonitor) {
         if (this.hellRoundTask != null && !this.hellDiceGameService.hasOpenRounds()) {
            ScheduledFuture<?> task = this.hellRoundTask;
            this.hellRoundTask = null;
            task.cancel(false);
            log.debug("地狱骰已无进行中轮次，推进任务已暂停");
         }
      }
   }

   private void stopHellRoundTask() {
      synchronized (this.hellRoundTaskMonitor) {
         if (this.hellRoundTask != null) {
            ScheduledFuture<?> task = this.hellRoundTask;
            this.hellRoundTask = null;
            task.cancel(false);
            log.debug("地狱骰推进任务已暂停");
         }
      }
   }

   private static String hellDepthName(int depth) {
      return switch (depth) {
         case 1 -> "鬼门";
         case 2 -> "火湖";
         case 3 -> "刀山";
         case 4 -> "血池";
         case 5 -> "炼狱";
         case 6 -> "魔王座";
         default -> "入口";
      };
   }

   private static String formatHellDiceRule(List<Integer> deathNumbers) {
      Set<Integer> deathSet = new LinkedHashSet<>(deathNumbers);
      String deathText = formatHellDiceNumbers(deathSet.stream().sorted().toList());
      String winningText = formatHellDiceNumbers(IntStream.rangeClosed(1, 6).filter(value -> !deathSet.contains(value)).boxed().toList());
      return "\ud83c\udfb2 坠落 " + deathText + "｜存活 " + winningText;
   }

   private static String formatHellDiceNumbers(List<Integer> numbers) {
      return numbers.stream().map(String::valueOf).collect(Collectors.joining("、"));
   }

   private static boolean isHellTerminalStatus(String status) {
      return "SETTLED".equals(status) || "LOST".equals(status) || "REFUNDED".equals(status);
   }

   private void scheduleHellPanelDelete(long chatId, long messageId, int delaySeconds) {
      String key = chatId + ":" + messageId;
      if (this.pendingHellPanelDeletes.add(key)) {
         this.sharedScheduler.schedule(() -> {
            try {
               this.deleteMessageSilently(chatId, messageId);
            } finally {
               this.pendingHellPanelDeletes.remove(key);
            }
         }, (long)delaySeconds, TimeUnit.SECONDS);
      }
   }

   private static int hellPayout(int bet, int depth, HellDiceGameConfig config) {
      int index = Math.max(0, Math.min(depth, 6) - 1);
      return (int)((long)bet * (long)config.getLayerPayoutPercents().get(index).intValue() / 100L);
   }

   private String formatHellUser(long userId, String username, String displayName) {
      String label = StringUtils.hasText(username)
         ? (username.startsWith("@") ? username : "@" + username)
         : (StringUtils.hasText(displayName) ? displayName : "用户" + userId);
      return "<a href=\"tg://user?id=" + userId + "\">" + escapeTelegramHtml(label) + "</a>";
   }

   private void handleBlackjack(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "❌ 21点游戏仅限群聊使用！");
      } else {
         User user = message.getFrom();
         String[] args = message.getText().trim().split("\\s+");
         if (args.length != 2) {
            this.sendMessageAndDelete(chatId, "⚠️ 格式错误！请使用：/bj <积分数>\n例如：/bj 100", 10);
         } else {
            int betAmount;
            try {
               betAmount = Integer.parseInt(args[1]);
            } catch (NumberFormatException var12) {
               this.sendMessageAndDelete(chatId, "⚠️ 积分必须是整数！", 10);
               return;
            }

            if (betAmount <= 0) {
               this.sendMessageAndDelete(chatId, "⚠️ 下注积分必须大于0！", 10);
            } else if (this.blackjackGameService.getSession(user.getId()) != null) {
               this.sendMessageAndDelete(chatId, "⚠️ 你已经有一局正在进行的游戏！请先完成它。", 10);
            } else {
               PointsProfile profile = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
               if (profile.getPoints() < (long)betAmount) {
                  this.sendMessageAndDelete(chatId, String.format("⚠️ 积分不足！当前积分：%d", profile.getPoints()), 10);
               } else if (this.botReady()) {
                  this.pointsStore.addPoints(profile, -betAmount, "21点-下注", null);

                  try {
                     BlackjackSession session = this.blackjackGameService.startGame(user.getId(), chatId, betAmount);
                     TelegramBotApiClient.ApiMessage sentMsg = this.botApiClient
                        .sendMessage(
                           chatId, this.renderBlackjackMessage(session, user), message.getMessageId(), "HTML", this.buildBlackjackKeyboard(session), false
                        );
                     session.setMessageId(sentMsg.getMessageId());
                     this.blackjackGameService.saveSession(user.getId(), session);
                  } catch (Exception var11) {
                     log.error("创建或发送 21 点牌局失败: chatId={}, userId={}", chatId, user.getId(), var11);
                     this.pointsStore.addPoints(profile, betAmount, "21点-退款", null);

                     try {
                        this.blackjackGameService.removeSession(user.getId());
                     } catch (RuntimeException var10) {
                        log.warn("清理失败的 21 点牌局状态失败: userId={}, error={}", user.getId(), var10.getMessage());
                     }

                     this.sendMessageAndDelete(chatId, "⚠️ 21点开局失败，下注积分已退还，请稍后再试。", 10);
                  }
               }
            }
         }
      }
   }

   private void handleBlackjackCallback(Update update) {
      String data = update.getCallbackQuery().getData();
      User clicker = update.getCallbackQuery().getFrom();
      long userId = clicker.getId();
      long chatId = update.getCallbackQuery().getMessage().getChatId();
      if (!this.telegramGameRateLimiter.tryAcquireAction(chatId, userId)) {
         try {
            this.botApiClient.answerCallbackQuery(update.getCallbackQuery().getId(), "\ud83c\udfae 操作太快，请稍后再点", false);
         } catch (TelegramBotApiClient.TelegramBotApiException var18) {
            log.debug("发送 21 点按钮限流提示失败: {}", var18.getMessage());
         }
      } else {
         String[] parts = data.split("_");
         if (parts.length >= 3) {
            String action = parts[1];

            long ownerId;
            try {
               ownerId = Long.parseLong(parts[2]);
            } catch (NumberFormatException var24) {
               return;
            }

            if (userId != ownerId) {
               try {
                  this.botApiClient.answerCallbackQuery(update.getCallbackQuery().getId(), "\ud83d\udeab 这不是你的牌局！", true);
               } catch (TelegramBotApiClient.TelegramBotApiException var19) {
               }
            } else {
               BlackjackSession session = this.blackjackGameService.getSession(ownerId);
               if (session == null) {
                  try {
                     this.botApiClient.answerCallbackQuery(update.getCallbackQuery().getId(), "游戏已结束或超时", true);
                  } catch (TelegramBotApiClient.TelegramBotApiException var20) {
                  }
               } else if (session.getStatus() == BlackjackSession.GameStatus.PLAYER_TURN) {
                  try {
                     this.telegramGameRateLimiter.awaitMessageTurn(chatId);
                  } catch (TelegramGameRateLimiter.GameRateLimitException var23) {
                     try {
                        this.botApiClient.answerCallbackQuery(update.getCallbackQuery().getId(), "\ud83c\udfae 群内游戏消息较多，请稍后再操作", false);
                     } catch (TelegramBotApiClient.TelegramBotApiException var17) {
                        log.debug("发送 21 点群限流提示失败: {}", var17.getMessage());
                     }

                     return;
                  }

                  try {
                     this.botApiClient.answerCallbackQuery(update.getCallbackQuery().getId());
                  } catch (TelegramBotApiClient.TelegramBotApiException var22) {
                     log.debug("回答 21 点按钮回调失败: {}", var22.getMessage());
                  }

                  switch (action) {
                     case "hit":
                        if (session.getPlayerHand().size() >= 7) {
                           this.blackjackGameService.dealerTurn(session);
                           this.determineBlackjackWinner(session);
                        } else {
                           this.blackjackGameService.hit(session);
                           if (this.blackjackGameService.isBust(session.getPlayerHand())) {
                              session.setStatus(BlackjackSession.GameStatus.FINISHED);
                              this.finishBlackjackGame(session, false, false);
                           } else if (this.blackjackGameService.calculateScore(session.getPlayerHand()) == 21) {
                           }
                        }
                        break;
                     case "stand":
                        this.blackjackGameService.dealerTurn(session);
                        this.determineBlackjackWinner(session);
                        break;
                     case "double":
                        PointsProfile profile = this.pointsStore.findByUserId(chatId, userId);
                        if (profile.getPoints() < (long)session.getBetAmount()) {
                           try {
                              this.botApiClient.answerCallbackQuery(update.getCallbackQuery().getId(), "积分不足，无法加倍！", true);
                           } catch (TelegramBotApiClient.TelegramBotApiException var21) {
                           }

                           return;
                        }

                        this.pointsStore.addPoints(profile, -session.getBetAmount(), "21点-加倍", null);
                        session.setBetAmount(session.getBetAmount() * 2);
                        this.blackjackGameService.hit(session);
                        if (this.blackjackGameService.isBust(session.getPlayerHand())) {
                           session.setStatus(BlackjackSession.GameStatus.FINISHED);
                           this.finishBlackjackGame(session, false, false);
                        } else {
                           this.blackjackGameService.dealerTurn(session);
                           this.determineBlackjackWinner(session);
                        }
                  }

                  this.updateBlackjackMessage(session, clicker);
               }
            }
         }
      }
   }

   private void determineBlackjackWinner(BlackjackSession session) {
      int playerScore = this.blackjackGameService.calculateScore(session.getPlayerHand());
      int dealerScore = this.blackjackGameService.calculateScore(session.getDealerHand());
      boolean dealerBust = this.blackjackGameService.isBust(session.getDealerHand());
      if (dealerBust) {
         this.finishBlackjackGame(session, true, false);
      } else if (playerScore > dealerScore) {
         this.finishBlackjackGame(session, true, false);
      } else if (playerScore < dealerScore) {
         this.finishBlackjackGame(session, false, false);
      } else {
         this.finishBlackjackGame(session, false, true);
      }
   }

   private void finishBlackjackGame(BlackjackSession session, boolean win, boolean push) {
      PointsProfile profile = this.pointsStore.findByUserId(session.getChatId(), session.getUserId());
      int payout = 0;
      String resultText = "";
      String mention = String.format(
         "<a href=\"tg://user?id=%d\">%s</a>",
         session.getUserId(),
         this.displayName(this.pointsStore.findByUserId(session.getChatId(), session.getUserId()).getUsername(), session.getUserId())
      );
      if (win) {
         String winType = "赢得";
         if (this.blackjackGameService.isBlackjack(session.getPlayerHand())) {
            payout = (int)((double)session.getBetAmount() * 2.5);
            resultText = String.format("\ud83c\udf89 %s <b>Blackjack!</b> 你赢得了 <b>%d</b> 积分！(赔率 3:2)", mention, payout - session.getBetAmount());
         } else {
            payout = session.getBetAmount() * 2;
            resultText = String.format("\ud83c\udf89 %s <b>你赢了！</b> 获得 <b>%d</b> 积分！", mention, payout - session.getBetAmount());
         }

         this.pointsStore.addPoints(profile, payout, "21点-盈利", null);
      } else if (push) {
         payout = session.getBetAmount();
         resultText = String.format("\ud83e\udd1d %s <b>平局！</b> 退还 <b>%d</b> 积分。", mention, payout);
         this.pointsStore.addPoints(profile, payout, "21点-平局", null);
      } else {
         resultText = String.format(
            "\ud83d\udcb8 <a href=\"tg://user?id=%d\">%s</a> <b>你输了！</b> 本局损失 <b>%d</b> 积分。",
            session.getUserId(),
            this.displayName(this.pointsStore.findByUserId(session.getChatId(), session.getUserId()).getUsername(), session.getUserId()),
            session.getBetAmount()
         );
      }

      session.setSettlementResult(resultText);
      this.deleteMessageDelayed(session.getChatId(), session.getMessageId(), 60);
      this.blackjackGameService.removeSession(session.getUserId());
   }

   private String displayName(String username, long userId) {
      return username != null && !username.isBlank() ? username : "User" + userId;
   }

   private void updateBlackjackMessage(BlackjackSession session, User user) {
      if (session.getMessageId() != null) {
         try {
            this.botApiClient
               .editMessageText(
                  session.getChatId(), session.getMessageId(), this.renderBlackjackMessage(session, user), "HTML", this.buildBlackjackKeyboard(session)
               );
         } catch (TelegramBotApiClient.TelegramBotApiException var4) {
            log.warn("Failed to update BJ message", (Throwable)var4);
         }
      }
   }

   private String renderBlackjackMessage(BlackjackSession session, User user) {
      StringBuilder sb = new StringBuilder();
      sb.append("\ud83c\udfb2 <b>Blackjack (21点)</b>\n");
      sb.append("玩家：").append(this.displayName(user)).append("\n");
      sb.append("下注：").append(session.getBetAmount()).append(" 积分\n\n");
      sb.append("\ud83d\udc68\u200d\ud83d\udcbc <b>庄家牌面：</b>\n");
      if (session.getStatus() == BlackjackSession.GameStatus.PLAYER_TURN) {
         if (!session.getDealerHand().isEmpty()) {
            sb.append(session.getDealerHand().get(0).toString()).append("  \ud83c\udca0");
         }
      } else {
         for (BlackjackCard card : session.getDealerHand()) {
            sb.append(card.toString()).append("  ");
         }

         sb.append("(").append(this.blackjackGameService.calculateScore(session.getDealerHand())).append(")");
      }

      sb.append("\n\n");
      sb.append("\ud83d\udc64 <b>你的手牌：</b>\n");

      for (BlackjackCard card : session.getPlayerHand()) {
         sb.append(card.toString()).append("  ");
      }

      int playerScore = this.blackjackGameService.calculateScore(session.getPlayerHand());
      sb.append("(").append(playerScore).append(")\n\n");
      if (session.getStatus() == BlackjackSession.GameStatus.FINISHED) {
         if (StringUtils.hasText(session.getSettlementResult())) {
            sb.append(session.getSettlementResult());
         } else {
            sb.append("\ud83c\udfc1 游戏结束");
         }
      } else {
         sb.append("\ud83d\udc49 请操作...");
      }

      return sb.toString();
   }

   private JSONObject buildBlackjackKeyboard(BlackjackSession session) {
      if (session.getStatus() != BlackjackSession.GameStatus.PLAYER_TURN) {
         return null;
      } else {
         String ownerId = String.valueOf(session.getUserId());
         TelegramBotApiClient.InlineButton hit = new TelegramBotApiClient.InlineButton("\ud83d\udd90 要牌", "bj_hit_" + ownerId);
         TelegramBotApiClient.InlineButton stand = new TelegramBotApiClient.InlineButton("\ud83d\uded1 停牌", "bj_stand_" + ownerId);
         TelegramBotApiClient.InlineButton dbl = new TelegramBotApiClient.InlineButton("\ud83d\udcb0 加倍", "bj_double_" + ownerId);
         boolean canDouble = session.getPlayerHand().size() == 2;
         List<TelegramBotApiClient.InlineButton> row1 = new ArrayList<>();
         row1.add(hit);
         row1.add(stand);
         if (canDouble) {
            row1.add(dbl);
         }

         return TelegramBotApiClient.inlineKeyboard(List.of(row1));
      }
   }

   private void handleDice(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "❌ 骰子游戏仅限群聊使用！");
      } else {
         User user = message.getFrom();
         String[] args = message.getText().trim().split("\\s+");
         if (args.length != 2) {
            this.sendMessageAndDelete(chatId, "⚠️ 格式错误！请使用：/dice <积分数>\n例如：/dice 50", 10);
         } else {
            int betAmount;
            try {
               betAmount = Integer.parseInt(args[1]);
            } catch (NumberFormatException var14) {
               this.sendMessageAndDelete(chatId, "⚠️ 积分必须是整数！", 10);
               return;
            }

            if (betAmount <= 0) {
               this.sendMessageAndDelete(chatId, "⚠️ 下注积分必须大于0！", 10);
            } else {
               PointsProfile profile = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
               if (profile.getPoints() < (long)betAmount) {
                  this.sendMessageAndDelete(chatId, String.format("⚠️ 积分不足！当前积分：%d", profile.getPoints()), 10);
               } else if (this.botReady()) {
                  this.pointsStore.addPoints(profile, -betAmount, "DICE_BET", null);
                  this.deleteMessageDelayed(chatId, message.getMessageId(), 60);

                  try {
                     TelegramBotApiClient.ApiMessage playerMsg = this.botApiClient.sendDice(chatId, "\ud83c\udfb2");
                     int playerValue = playerMsg.getDiceValue();
                     this.deleteMessageDelayed(chatId, playerMsg.getMessageId(), 60);
                     this.telegramGameRateLimiter.awaitMessageTurn(chatId);
                     TelegramBotApiClient.ApiMessage botMsg = this.botApiClient.sendDice(chatId, "\ud83c\udfb2");
                     int botValue = botMsg.getDiceValue();
                     this.deleteMessageDelayed(chatId, botMsg.getMessageId(), 60);
                     this.sharedScheduler
                        .schedule(
                           () -> {
                              try {
                                 String mention = String.format("<a href=\"tg://user?id=%d\">%s</a>", user.getId(), this.displayName(user));
                                 String resultText;
                                 if (playerValue > botValue) {
                                    int payout = betAmount * 2;
                                    this.pointsStore.addPoints(profile, payout, "DICE_WIN", null);
                                    resultText = String.format(
                                       "\ud83c\udfb2 <b>比大小结果</b>\n\n\ud83d\udc64 你: <b>%d</b>\n\ud83e\udd16 庄家: <b>%d</b>\n\n\ud83c\udf89 %s <b>你赢了！</b> 获得 <b>%d</b> 积分！",
                                       playerValue,
                                       botValue,
                                       mention,
                                       payout - betAmount
                                    );
                                 } else if (playerValue < botValue) {
                                    resultText = String.format(
                                       "\ud83c\udfb2 <b>比大小结果</b>\n\n\ud83d\udc64 你: <b>%d</b>\n\ud83e\udd16 庄家: <b>%d</b>\n\n\ud83d\udcb8 %s <b>你输了！</b> 本局损失 <b>%d</b> 积分。",
                                       playerValue,
                                       botValue,
                                       mention,
                                       betAmount
                                    );
                                 } else {
                                    this.pointsStore.addPoints(profile, betAmount, "DICE_DRAW", null);
                                    resultText = String.format(
                                       "\ud83c\udfb2 <b>比大小结果</b>\n\n\ud83d\udc64 你: <b>%d</b>\n\ud83e\udd16 庄家: <b>%d</b>\n\n\ud83e\udd1d %s <b>平局！</b> 退还 <b>%d</b> 积分。",
                                       playerValue,
                                       botValue,
                                       mention,
                                       betAmount
                                    );
                                 }

                                 this.telegramGameRateLimiter.awaitMessageTurn(chatId);
                                 TelegramBotApiClient.ApiMessage sentResult = this.botApiClient
                                    .sendMessage(chatId, resultText, message.getMessageId(), "HTML", null, false);
                                 this.deleteMessageDelayed(chatId, sentResult.getMessageId(), 60);
                              } catch (TelegramGameRateLimiter.GameRateLimitException | TelegramBotApiClient.TelegramBotApiException var12x) {
                                 log.error("Failed to send dice result", (Throwable)var12x);
                              }
                           },
                           3500L,
                           TimeUnit.MILLISECONDS
                        );
                  } catch (TelegramGameRateLimiter.GameRateLimitException | TelegramBotApiClient.TelegramBotApiException var13) {
                     log.error("Dice game failed", (Throwable)var13);
                     this.pointsStore.addPoints(profile, betAmount, "DICE_REFUND", null);

                     try {
                        this.telegramGameRateLimiter.awaitMessageTurn(chatId);
                     } catch (TelegramGameRateLimiter.GameRateLimitException var12) {
                        log.warn("骰子退款提示等待发送时隙失败: {}", var12.getMessage());
                        return;
                     }

                     this.sendMessage(chatId, "⚠️ 游戏发生错误，积分已退还。");
                  }
               }
            }
         }
      }
   }

   private void handleSlots(Message message) {
      long chatId = message.getChatId();
      if (!this.isGroupChat(message)) {
         this.sendMessage(chatId, "❌ 老虎机仅限群聊使用！");
      } else {
         User user = message.getFrom();
         String[] args = message.getText().trim().split("\\s+");
         if (args.length != 2) {
            this.sendMessageAndDelete(chatId, "⚠️ 格式错误！请使用：/slots <积分数>\n例如：/slots 20", 10);
         } else {
            int betAmount;
            try {
               betAmount = Integer.parseInt(args[1]);
            } catch (NumberFormatException var12) {
               this.sendMessageAndDelete(chatId, "⚠️ 积分必须是整数！", 10);
               return;
            }

            if (betAmount <= 0) {
               this.sendMessageAndDelete(chatId, "⚠️ 下注积分必须大于0！", 10);
            } else {
               PointsProfile profile = this.pointsStore.getOrCreate(chatId, user.getId(), user.getUserName(), this.displayName(user));
               if (profile.getPoints() < (long)betAmount) {
                  this.sendMessageAndDelete(chatId, String.format("⚠️ 积分不足！当前积分：%d", profile.getPoints()), 10);
               } else if (this.botReady()) {
                  this.pointsStore.addPoints(profile, -betAmount, "老虎机-下注", null);
                  this.deleteMessageDelayed(chatId, message.getMessageId(), 60);

                  try {
                     TelegramBotApiClient.ApiMessage diceMsg = this.botApiClient.sendDice(chatId, "\ud83c\udfb0");
                     int value = diceMsg.getDiceValue();
                     this.deleteMessageDelayed(chatId, diceMsg.getMessageId(), 60);
                     this.sharedScheduler
                        .schedule(
                           () -> {
                              try {
                                 String mention = String.format("<a href=\"tg://user?id=%d\">%s</a>", user.getId(), this.displayName(user));
                                 String resultText;
                                 if (value == 64) {
                                    int payout = betAmount * 20;
                                    this.pointsStore.addPoints(profile, payout, "老虎机-大奖", null);
                                    resultText = String.format(
                                       "\ud83c\udfb0 <b>老虎机结果</b>\n\n\ud83c\udfaf 结果: <b>777 (Jackpot!)</b>\n\ud83c\udf89 %s <b>运气爆棚！</b> 赢得 <b>%d</b> 积分！(20倍)",
                                       mention,
                                       payout - betAmount
                                    );
                                 } else if (value != 1 && value != 22 && value != 43) {
                                    resultText = String.format(
                                       "\ud83c\udfb0 <b>老虎机结果</b>\n\n\ud83d\udcb8 %s <b>未中奖</b>，本次损失 <b>%d</b> 积分。\n(提示: 仅 777/BAR/葡萄/柠檬 三连才中奖)",
                                       mention,
                                       betAmount
                                    );
                                 } else {
                                    int payout = betAmount * 10;
                                    this.pointsStore.addPoints(profile, payout, "老虎机-盈利", null);
                                    String type = value == 1 ? "BAR" : (value == 22 ? "葡萄" : "柠檬");
                                    resultText = String.format(
                                       "\ud83c\udfb0 <b>老虎机结果</b>\n\n\ud83c\udfaf 结果: <b>%s (三连!)</b>\n\ud83c\udf89 %s <b>恭喜中奖！</b> 赢得 <b>%d</b> 积分！(10倍)",
                                       type,
                                       mention,
                                       payout - betAmount
                                    );
                                 }

                                 this.telegramGameRateLimiter.awaitMessageTurn(chatId);
                                 TelegramBotApiClient.ApiMessage sentResult = this.botApiClient
                                    .sendMessage(chatId, resultText, message.getMessageId(), "HTML", null, false);
                                 this.deleteMessageDelayed(chatId, sentResult.getMessageId(), 60);
                              } catch (TelegramGameRateLimiter.GameRateLimitException | TelegramBotApiClient.TelegramBotApiException var12x) {
                                 log.error("Failed to send slots result", (Throwable)var12x);
                              }
                           },
                           4000L,
                           TimeUnit.MILLISECONDS
                        );
                  } catch (TelegramGameRateLimiter.GameRateLimitException | TelegramBotApiClient.TelegramBotApiException var11) {
                     log.error("Slots game failed", (Throwable)var11);
                     this.pointsStore.addPoints(profile, betAmount, "老虎机-退款", null);

                     try {
                        this.telegramGameRateLimiter.awaitMessageTurn(chatId);
                     } catch (TelegramGameRateLimiter.GameRateLimitException var10) {
                        log.warn("老虎机退款提示等待发送时隙失败: {}", var10.getMessage());
                        return;
                     }

                     this.sendMessage(chatId, "⚠️ 游戏发生错误，积分已退还。");
                  }
               }
            }
         }
      }
   }

   @Generated
   public PointsBot(
      final PointsBotConfigService configService,
      final PointsStore pointsStore,
      final PointsBotFoamBagService foamBagService,
      final PointsBotRedPacketService redPacketService,
      final PointsBotLotteryService lotteryService,
      final BlackjackGameService blackjackGameService,
      final ScratchCardGameService scratchCardGameService,
      final BrainGameService brainGameService,
      final PointsBotGameConfigService gameConfigService,
      final SanguoshaCardService sanguoshaCardService,
      final TelegramGameRateLimiter telegramGameRateLimiter,
      final EmbyInfoService embyInfoService,
      final EmbyUserService embyUserService,
      final TelegramBindingManager telegramBindingManager,
      final RedisLockUtils redisLockUtils,
      final PointsBotRedeemConfigService pointsBotRedeemConfigService,
      final PointsBotPrizeConfigService pointsBotPrizeConfigService,
      final PointsBotLevelConfigService pointsBotLevelConfigService,
      final TelegramBotAuthorizationService telegramBotAuthorizationService,
      final StringRedisTemplate stringRedisTemplate
   ) {
      this.configService = configService;
      this.pointsStore = pointsStore;
      this.foamBagService = foamBagService;
      this.redPacketService = redPacketService;
      this.lotteryService = lotteryService;
      this.blackjackGameService = blackjackGameService;
      this.scratchCardGameService = scratchCardGameService;
      this.brainGameService = brainGameService;
      this.gameConfigService = gameConfigService;
      this.sanguoshaCardService = sanguoshaCardService;
      this.telegramGameRateLimiter = telegramGameRateLimiter;
      this.embyInfoService = embyInfoService;
      this.embyUserService = embyUserService;
      this.telegramBindingManager = telegramBindingManager;
      this.redisLockUtils = redisLockUtils;
      this.pointsBotRedeemConfigService = pointsBotRedeemConfigService;
      this.pointsBotPrizeConfigService = pointsBotPrizeConfigService;
      this.pointsBotLevelConfigService = pointsBotLevelConfigService;
      this.telegramBotAuthorizationService = telegramBotAuthorizationService;
      this.stringRedisTemplate = stringRedisTemplate;
   }

   private static record MentionTarget(Long userId, String username, String label) {
   }

   private static record PendingRedPacketConfirmation(
      long chatId,
      long messageId,
      long userId,
      String username,
      String displayName,
      int totalPoints,
      int totalCount,
      String greeting,
      int expireMinutes,
      long expiresAtMillis
   ) {
      private boolean expired() {
         return System.currentTimeMillis() > this.expiresAtMillis;
      }
   }

   private static record ScratchWinsSession(String token, long userId, long chatId, LocalDateTime asOf) {
      private String serialize() {
         return this.userId + "|" + this.chatId + "|" + this.asOf;
      }

      private static PointsBot.ScratchWinsSession parse(String token, String value) {
         if (StringUtils.hasText(token) && StringUtils.hasText(value)) {
            String[] parts = value.split("\\|", 3);
            if (parts.length != 3) {
               return null;
            } else {
               try {
                  return new PointsBot.ScratchWinsSession(token, Long.parseLong(parts[0]), Long.parseLong(parts[1]), LocalDateTime.parse(parts[2]));
               } catch (DateTimeParseException | NumberFormatException var4) {
                  return null;
               }
            }
         } else {
            return null;
         }
      }
   }
}
