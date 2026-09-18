package com.una.embyhub.pointsbot.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.mapper.NotifyChannelMapper;
import com.una.embyhub.mapper.PointsBotGameConfigMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotGameConfigUpdate;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotGameConfigResponse;
import com.una.embyhub.model.entity.NotifyChannel;
import com.una.embyhub.model.entity.PointsBotGameConfig;
import com.una.embyhub.pointsbot.model.BrainGameConfig;
import com.una.embyhub.pointsbot.model.HellDiceGameConfig;
import com.una.embyhub.pointsbot.model.PointsBotConfig;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PointsBotGameConfigService {
   private static final Map<String, PointsBotGameConfigService.CatalogGame> CATALOG = createCatalog();
   private final PointsBotGameConfigMapper gameConfigMapper;
   private final NotifyChannelMapper notifyChannelMapper;
   private final NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   private final HellDiceGameService hellDiceGameService;
   private final PointsBotConfigService pointsBotConfigService;

   public List<PointsBotGameConfigResponse> list() {
      Map<String, PointsBotGameConfig> stored = new LinkedHashMap<>();
      new LambdaQueryChainWrapper<>(this.gameConfigMapper)
         .orderByAsc(PointsBotGameConfig::getSortOrder)
         .list()
         .forEach(item -> stored.put(item.getGameCode(), item));
      return CATALOG.values().stream().map(game -> this.toResponse(game, stored.get(game.code()))).toList();
   }

   public boolean isEnabled(String gameCode) {
      String code = normalize(gameCode);
      PointsBotGameConfigService.CatalogGame game = CATALOG.get(code);
      if (game == null) {
         return false;
      } else {
         PointsBotGameConfig stored = this.find(code);
         return stored == null ? game.defaultEnabled() : Integer.valueOf(1).equals(stored.getEnabled());
      }
   }

   public Set<String> enabledGameCodes() {
      LinkedHashSet<String> result = new LinkedHashSet<>();

      for (PointsBotGameConfigService.CatalogGame game : CATALOG.values()) {
         if (this.isEnabled(game.code())) {
            result.add(game.code());
         }
      }

      return result;
   }

   public BrainGameConfig getBrainConfig() {
      PointsBotGameConfig stored = this.find("brain");
      if (stored != null && StringUtils.hasText(stored.getConfigJson())) {
         try {
            BrainGameConfig config = JSONObject.parseObject(stored.getConfigJson(), BrainGameConfig.class);
            validateBrainConfig(config);
            return config;
         } catch (RuntimeException var3) {
            return new BrainGameConfig();
         }
      } else {
         return new BrainGameConfig();
      }
   }

   public HellDiceGameConfig getHellDiceConfig() {
      PointsBotGameConfig stored = this.find("hell_dice");
      if (stored != null && StringUtils.hasText(stored.getConfigJson())) {
         try {
            HellDiceGameConfig config = JSONObject.parseObject(stored.getConfigJson(), HellDiceGameConfig.class);
            validateHellDiceConfig(config);
            return config;
         } catch (RuntimeException var3) {
            return new HellDiceGameConfig();
         }
      } else {
         return new HellDiceGameConfig();
      }
   }

   @Transactional
   public HellDiceGameService.VaultView topUpHellDiceVault(int amount) {
      return this.topUpHellDiceVault(amount, "WEB_ADMIN", null);
   }

   @Transactional
   public HellDiceGameService.VaultView topUpHellDiceVault(int amount, String source, String actorId) {
      long chatId = this.requireConfiguredPointsChatId();
      return this.hellDiceGameService.topUpVault(chatId, amount, this.getHellDiceConfig(), source, actorId);
   }

   public HellDiceGameService.VaultView getConfiguredHellDiceVault() {
      return this.hellDiceGameService.getVaultView(this.requireConfiguredPointsChatId(), this.getHellDiceConfig());
   }

   private long requireConfiguredPointsChatId() {
      PointsBotConfigService.PointsBotChannelConfig loaded = this.pointsBotConfigService.loadConfig();
      PointsBotConfig config = loaded == null ? null : loaded.getConfig();
      if (config == null) {
         throw badRequest("积分机器人尚未配置，不能补充地狱金库");
      } else if (!StringUtils.hasText(config.getGroupChatId())) {
         throw badRequest("积分机器人尚未配置群聊，不能补充地狱金库");
      } else {
         try {
            return Long.parseLong(config.getGroupChatId());
         } catch (NumberFormatException var4) {
            throw badRequest("积分机器人群聊ID配置不合法");
         }
      }
   }

   @Transactional
   public PointsBotGameConfigResponse update(PointsBotGameConfigUpdate request) {
      String code = normalize(request.getGameCode());
      PointsBotGameConfigService.CatalogGame game = CATALOG.get(code);
      if (game == null) {
         throw badRequest("不支持的游戏");
      } else {
         BrainGameConfig brainConfig = null;
         if ("brain".equals(code)) {
            brainConfig = request.getBrainConfig() == null ? this.getBrainConfig() : request.getBrainConfig();
            validateBrainConfig(brainConfig);
         }

         HellDiceGameConfig hellDiceConfig = null;
         if ("hell_dice".equals(code)) {
            hellDiceConfig = request.getHellDiceConfig() == null ? this.getHellDiceConfig() : request.getHellDiceConfig();
            validateHellDiceConfig(hellDiceConfig);
            this.validateVaultCapacityAgainstCurrentBalance(hellDiceConfig);
         }

         PointsBotGameConfig stored = this.find(code);
         if (stored == null) {
            stored = new PointsBotGameConfig();
            stored.setGameCode(code);
            stored.setSortOrder(game.sortOrder());
         }

         stored.setEnabled(Boolean.TRUE.equals(request.getEnabled()) ? 1 : 0);
         if (brainConfig != null) {
            stored.setConfigJson(JSONObject.toJSONString(brainConfig));
         } else if (hellDiceConfig != null) {
            stored.setConfigJson(JSONObject.toJSONString(hellDiceConfig));
         } else {
            stored.setConfigJson("{}");
         }

         if (stored.getId() == null) {
            this.gameConfigMapper.insert(stored);
         } else {
            this.gameConfigMapper.updateById(stored);
         }

         if (!Boolean.TRUE.equals(request.getEnabled())) {
            this.removeDisabledGameFromChannels(code);
         }

         this.notifyChannelCacheLoaderUtils.loadConfigCache();
         return this.toResponse(game, stored);
      }
   }

   public void validateSelectedGames(List<String> selected) {
      if (selected != null) {
         for (String raw : selected) {
            String code = normalize(raw);
            if (!CATALOG.containsKey(code)) {
               throw badRequest("存在不受支持的游戏命令");
            }

            if (!this.isEnabled(code)) {
               throw badRequest("游戏「" + CATALOG.get(code).name() + "」已在游戏管理中关闭，不能选择");
            }
         }
      }
   }

   private PointsBotGameConfig find(String code) {
      return new LambdaQueryChainWrapper<>(this.gameConfigMapper).eq(PointsBotGameConfig::getGameCode, code).last("limit 1").one();
   }

   private PointsBotGameConfigResponse toResponse(PointsBotGameConfigService.CatalogGame game, PointsBotGameConfig stored) {
      boolean enabled = stored == null ? game.defaultEnabled() : Integer.valueOf(1).equals(stored.getEnabled());
      BrainGameConfig brainConfig = "brain".equals(game.code()) ? this.parseBrainConfig(stored) : null;
      HellDiceGameConfig hellDiceConfig = "hell_dice".equals(game.code()) ? this.parseHellDiceConfig(stored) : null;
      return PointsBotGameConfigResponse.builder()
         .gameCode(game.code())
         .gameName(game.name())
         .command(game.command())
         .description(game.description())
         .icon(game.icon())
         .enabled(enabled)
         .sortOrder(game.sortOrder())
         .configurable("brain".equals(game.code()) || "hell_dice".equals(game.code()))
         .brainConfig(brainConfig)
         .hellDiceConfig(hellDiceConfig)
         .hellDiceVault(hellDiceConfig == null ? null : this.resolveHellDiceVault(hellDiceConfig))
         .build();
   }

   private HellDiceGameConfig parseHellDiceConfig(PointsBotGameConfig stored) {
      if (stored != null && StringUtils.hasText(stored.getConfigJson())) {
         try {
            HellDiceGameConfig config = JSONObject.parseObject(stored.getConfigJson(), HellDiceGameConfig.class);
            validateHellDiceConfig(config);
            return config;
         } catch (RuntimeException var3) {
            return new HellDiceGameConfig();
         }
      } else {
         return new HellDiceGameConfig();
      }
   }

   private HellDiceGameService.VaultView resolveHellDiceVault(HellDiceGameConfig config) {
      PointsBotConfigService.PointsBotChannelConfig loaded = this.pointsBotConfigService.loadConfig();
      if (loaded != null && loaded.getConfig() != null) {
         PointsBotConfig channelConfig = loaded.getConfig();
         if (!StringUtils.hasText(channelConfig.getGroupChatId())) {
            return null;
         } else {
            try {
               return this.hellDiceGameService.getVaultView(Long.parseLong(channelConfig.getGroupChatId()), config);
            } catch (NumberFormatException var5) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private void validateVaultCapacityAgainstCurrentBalance(HellDiceGameConfig config) {
      HellDiceGameService.VaultView current = this.resolveHellDiceVault(config);
      if (current != null && current.initialized() && config.getVaultCapacity() < current.balance()) {
         throw badRequest("金库容量不能低于当前余额 " + current.balance());
      } else if (current != null && current.initialized() && config.getAdminTopUpLifetimeCap() < current.totalSubsidy()) {
         throw badRequest("累计补充上限不能低于已经补充的 " + current.totalSubsidy() + " 积分");
      }
   }

   private BrainGameConfig parseBrainConfig(PointsBotGameConfig stored) {
      if (stored != null && StringUtils.hasText(stored.getConfigJson())) {
         try {
            BrainGameConfig config = JSONObject.parseObject(stored.getConfigJson(), BrainGameConfig.class);
            validateBrainConfig(config);
            return config;
         } catch (RuntimeException var3) {
            return new BrainGameConfig();
         }
      } else {
         return new BrainGameConfig();
      }
   }

   private void removeDisabledGameFromChannels(String code) {
      for (NotifyChannel channel : new LambdaQueryChainWrapper<>(this.notifyChannelMapper)
         .in(NotifyChannel::getIconType, new Object[]{"telegram", "pointsBot"})
         .list()) {
         if (StringUtils.hasText(channel.getParams())) {
            try {
               JSONObject params = JSONObject.parseObject(channel.getParams());
               List<String> selected = params.getList("enabledGameCommands", String.class);
               if (selected != null && !selected.stream().noneMatch(item -> code.equals(normalize(item)))) {
                  List<String> filtered = new ArrayList<>(selected);
                  filtered.removeIf(item -> code.equals(normalize(item)));
                  params.put("enabledGameCommands", filtered);
                  params.put("gameCommandsVersion", Integer.valueOf(4));
                  channel.setParams(JSONObject.toJSONString(params));
                  this.notifyChannelMapper.updateById(channel);
               }
            } catch (RuntimeException var8) {
            }
         }
      }
   }

   private static void validateBrainConfig(BrainGameConfig config) {
      if (config == null) {
         throw badRequest("Brain 游戏配置不能为空");
      } else {
         range(config.getEntryCost(), 1, 1000, "报名积分");
         range(config.getDailyPlayLimit(), 1, 20, "每日参与次数");
         range(config.getDailyChampionLimit(), 1, config.getDailyPlayLimit(), "每日冠军次数");
         range(config.getRegistrationSeconds(), 5, 120, "报名时间");
         range(config.getAnswerSeconds(), 15, 300, "答题时间");
         range(config.getMinPlayers(), 2, 100, "普通局最少人数");
         range(config.getMaxPlayers(), config.getMinPlayers(), 100, "普通局最多人数");
         range(config.getPeakMinPlayers(), config.getMinPlayers(), config.getMaxPlayers(), "巅峰局最少人数");
         range(config.getPeakEveryRounds(), 2, 100, "巅峰局触发轮数");
         range(config.getJackpotCap(), 100, 100000, "奖池上限");
         if (config.getChampionPercent() + config.getFollowerPercent() + config.getJackpotPercent() + config.getSinkPercent() != 100) {
            throw badRequest("普通局奖励比例合计必须为 100%");
         } else if (config.getPeakChampionPercent() + config.getPeakFollowerPercent() > 100) {
            throw badRequest("巅峰局奖励比例不能超过 100%");
         } else {
            int weightTotal = config.getTargetArithmeticWeight()
               + config.getCodeLockWeight()
               + config.getBullsAndCowsWeight()
               + config.getLightsOutWeight()
               + config.getFlashMemoryWeight();
            if (weightTotal <= 0) {
               throw badRequest("至少启用一种 Brain 题型");
            } else {
               range(config.getTargetArithmeticWeight(), 0, 100, "目标数运算权重");
               range(config.getCodeLockWeight(), 0, 100, "数字密码锁权重");
               range(config.getBullsAndCowsWeight(), 0, 100, "AB 猜数权重");
               range(config.getLightsOutWeight(), 0, 100, "熄灯谜题权重");
               range(config.getFlashMemoryWeight(), 0, 100, "闪记挑战权重");
            }
         }
      }
   }

   static void validateHellDiceConfig(HellDiceGameConfig config) {
      if (config == null) {
         throw badRequest("地狱骰配置不能为空");
      } else {
         range(config.getMinBet(), 1, 1000, "最低下注");
         range(config.getMaxBet(), config.getMinBet(), 1000, "最高下注");
         range(config.getDailyPlayLimit(), 1, Integer.MAX_VALUE, "每日参与次数");
         range(config.getDecisionSeconds(), 5, 120, "玩家选择时间");
         range(config.getBettingSeconds(), 3, 60, "观众下注时间");
         range(config.getPanelRetentionSeconds(), 30, 3600, "结算面板保留时间");
         range(config.getSinglePayoutCap(), config.getMinBet(), 100000, "单局最高返还");
         range(config.getDailyPlayerProfitCap(), 1, 100000, "个人每日盈利上限");
         range(config.getDailyGroupProfitCap(), 1, 1000000, "全群每日盈利支出上限");
         range(config.getInitialVaultPoints(), 0, 100000, "初始金库");
         range(config.getVaultCapacity(), Math.max(1, config.getInitialVaultPoints()), 1000000, "金库容量");
         range(config.getAdminTopUpLifetimeCap(), config.getInitialVaultPoints(), 1000000, "累计补充上限");
         range(config.getLossVaultPercent(), 0, 100, "失败积分进入金库比例");
         range(config.getSpectatorMinBet(), 1, 1000, "观众最低下注");
         range(config.getSpectatorMaxBetPerLayer(), config.getSpectatorMinBet(), 10000, "观众每层最高下注");
         range(config.getSpectatorMaxBetPerRound(), config.getSpectatorMaxBetPerLayer(), 50000, "观众每局最高下注");
         range(config.getSpectatorMaxBetPerDay(), config.getSpectatorMaxBetPerRound(), 100000, "观众每日最高下注");
         range(config.getSpectatorPoolCapPerLayer(), config.getSpectatorMaxBetPerLayer(), 100000, "单层观众奖池上限");
         range(config.getSpectatorFeePercent(), 0, 20, "观众下注手续费");
         range(config.getLeaderboardLimit(), 1, 50, "地狱之王显示人数");
         List<List<Integer>> deathNumbers = config.getLayerDeathNumbers();
         if (deathNumbers != null && deathNumbers.size() == 6) {
            for (int index = 0; index < deathNumbers.size(); index++) {
               List<Integer> layerNumbers = deathNumbers.get(index);
               if (layerNumbers == null || layerNumbers.isEmpty() || layerNumbers.size() > 5) {
                  throw badRequest("第 " + (index + 1) + " 层必须配置1到5个坠落点数");
               }

               Set<Integer> uniqueNumbers = new LinkedHashSet<>();

               for (Integer number : layerNumbers) {
                  if (number == null || number < 1 || number > 6 || !uniqueNumbers.add(number)) {
                     throw badRequest("第 " + (index + 1) + " 层坠落点数必须在1到6之间且不能重复");
                  }
               }
            }

            List<Integer> payouts = config.getLayerPayoutPercents();
            if (payouts != null && payouts.size() == 6) {
               int previous = 100;

               for (int index = 0; index < payouts.size(); index++) {
                  Integer payout = payouts.get(index);
                  if (payout == null || payout <= previous || payout > 1000) {
                     throw badRequest("第 " + (index + 1) + " 层返还比例必须递增且不超过1000%");
                  }

                  int safeSides = 6 - deathNumbers.get(index).size();
                  long expectedNumerator = (long)payout.intValue() * (long)safeSides * 100L;
                  long allowedNumerator = (long)previous * 6L * 95L;
                  if (expectedNumerator > allowedNumerator) {
                     throw badRequest("第 " + (index + 1) + " 层返还比例过高，继续下潜的理论返还率不能超过95%");
                  }

                  previous = payout;
               }

               int maxPayout = (int)((long)config.getMaxBet() * (long)payouts.get(5).intValue() / 100L);
               if (maxPayout > config.getSinglePayoutCap()) {
                  int minPayout = (int)((long)config.getMinBet() * (long)payouts.get(5).intValue() / 100L);
                  if (minPayout > config.getSinglePayoutCap()) {
                     throw badRequest("单局最高返还不足以覆盖最低下注的第六层奖励");
                  }
               }
            } else {
               throw badRequest("地狱骰必须配置六层返还比例");
            }
         } else {
            throw badRequest("地狱骰必须配置六层坠落点数");
         }
      }
   }

   private static void range(int value, int min, int max, String label) {
      if (value < min || value > max) {
         throw badRequest(label + "必须在 " + min + " 到 " + max + " 之间");
      }
   }

   private static String normalize(String value) {
      if (value == null) {
         return "";
      } else {
         String normalized = value.trim().toLowerCase(Locale.ROOT);
         if (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
         }

         return "bj".equals(normalized) ? "blackjack" : normalized;
      }
   }

   private static BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }

   private static Map<String, PointsBotGameConfigService.CatalogGame> createCatalog() {
      LinkedHashMap<String, PointsBotGameConfigService.CatalogGame> result = new LinkedHashMap<>();
      add(result, new PointsBotGameConfigService.CatalogGame("brain", "Brain 脑力挑战", "/brain", "60 秒随机脑力题，报名积分进入奖池并按答对顺序结算。", "mdi-brain", 10, true));
      add(result, new PointsBotGameConfigService.CatalogGame("sgs", "三国杀", "/sgs", "通过 @用户触发积分牌局。", "mdi-cards-playing-outline", 20, true));
      add(result, new PointsBotGameConfigService.CatalogGame("blackjack", "21 点", "/blackjack · /bj", "下注积分进行 21 点对局。", "mdi-cards-spade-outline", 30, true));
      add(result, new PointsBotGameConfigService.CatalogGame("dice", "骰子比大小", "/dice", "玩家与庄家投骰子比大小。", "mdi-dice-5-outline", 40, true));
      add(result, new PointsBotGameConfigService.CatalogGame("hell_dice", "地狱骰", "/helldice · /hellrank", "单骰逐层下潜，玩家可随时收手，观众可参与对手盘。", "mdi-fire", 45, true));
      add(result, new PointsBotGameConfigService.CatalogGame("slots", "老虎机", "/slots", "下注积分抽取三连或 Jackpot。", "mdi-slot-machine-outline", 50, true));
      add(result, new PointsBotGameConfigService.CatalogGame("scratch", "雾中刮刮乐", "/scratch", "消耗积分选择雾滴格，等待自动开奖。", "mdi-ticket-percent-outline", 60, true));
      return result;
   }

   private static void add(Map<String, PointsBotGameConfigService.CatalogGame> target, PointsBotGameConfigService.CatalogGame game) {
      target.put(game.code(), game);
   }

   @Generated
   public PointsBotGameConfigService(
      final PointsBotGameConfigMapper gameConfigMapper,
      final NotifyChannelMapper notifyChannelMapper,
      final NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils,
      final HellDiceGameService hellDiceGameService,
      final PointsBotConfigService pointsBotConfigService
   ) {
      this.gameConfigMapper = gameConfigMapper;
      this.notifyChannelMapper = notifyChannelMapper;
      this.notifyChannelCacheLoaderUtils = notifyChannelCacheLoaderUtils;
      this.hellDiceGameService = hellDiceGameService;
      this.pointsBotConfigService = pointsBotConfigService;
   }

   private static record CatalogGame(String code, String name, String command, String description, String icon, int sortOrder, boolean defaultEnabled) {
   }
}
