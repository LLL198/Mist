package com.una.embyhub.pointsbot.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.mapper.PointsBotHellBetMapper;
import com.una.embyhub.mapper.PointsBotHellRoundMapper;
import com.una.embyhub.mapper.PointsBotHellVaultLedgerMapper;
import com.una.embyhub.mapper.PointsBotHellVaultMapper;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotHellBet;
import com.una.embyhub.model.entity.PointsBotHellRound;
import com.una.embyhub.model.entity.PointsBotHellVault;
import com.una.embyhub.model.entity.PointsBotHellVaultLedger;
import com.una.embyhub.pointsbot.model.HellDiceGameConfig;
import com.una.embyhub.pointsbot.model.PointsProfile;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HellDiceGameService {
   public static final ZoneId BUSINESS_ZONE = ZoneId.of("Asia/Shanghai");
   public static final String STATUS_BETTING = "BETTING";
   public static final String STATUS_ROLLING = "ROLLING";
   public static final String STATUS_WAITING_DECISION = "WAITING_DECISION";
   public static final String STATUS_SETTLED = "SETTLED";
   public static final String STATUS_LOST = "LOST";
   public static final String STATUS_REFUNDED = "REFUNDED";
   public static final String BET_PENDING = "PENDING";
   public static final String BET_WON = "WON";
   public static final String BET_LOST = "LOST";
   public static final String BET_REFUNDED = "REFUNDED";
   public static final String SIDE_SURVIVE = "SURVIVE";
   public static final String SIDE_DIE = "DIE";
   private final PointsBotHellVaultMapper vaultMapper;
   private final PointsBotHellRoundMapper roundMapper;
   private final PointsBotHellBetMapper betMapper;
   private final PointsBotHellVaultLedgerMapper vaultLedgerMapper;
   private final PointsStore pointsStore;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.RoundView startRound(
      long chatId, long userId, String username, String displayName, int betPoints, HellDiceGameConfig suppliedConfig
   ) {
      HellDiceGameConfig config = suppliedConfig.copy();
      PointsBotHellVault vault = this.lockInitializedVault(chatId, config);
      this.resetDailyOutflow(vault);
      PointsBotHellRound active = new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotHellRound::getChatId, Long.valueOf(chatId))
         .eq(PointsBotHellRound::getActiveGuard, Integer.valueOf(1))
         .last("limit 1")
         .one();
      if (active != null) {
         throw rule(HellDiceGameService.RuleCode.ACTIVE_ROUND, "当前群已有一局地狱骰，请等待本局结束");
      } else {
         LocalDateTime dayStart = LocalDate.now(BUSINESS_ZONE).atStartOfDay();
         Date dayStartDate = Date.from(dayStart.atZone(BUSINESS_ZONE).toInstant());
         long playsToday = new LambdaQueryChainWrapper<>(this.roundMapper)
            .eq(PointsBotHellRound::getChatId, Long.valueOf(chatId))
            .eq(PointsBotHellRound::getPlayerUserId, Long.valueOf(userId))
            .ne(PointsBotHellRound::getStatus, "REFUNDED")
            .ge(BaseEntity::getCreateDatetime, dayStartDate)
            .count();
         if (playsToday >= (long)config.getDailyPlayLimit()) {
            throw rule(HellDiceGameService.RuleCode.DAILY_LIMIT, "今天的地狱骰次数已用完");
         } else {
            int userProfitToday = new LambdaQueryChainWrapper<>(this.roundMapper)
               .eq(PointsBotHellRound::getChatId, Long.valueOf(chatId))
               .eq(PointsBotHellRound::getPlayerUserId, Long.valueOf(userId))
               .in(PointsBotHellRound::getStatus, new Object[]{"SETTLED", "REFUNDED"})
               .ge(BaseEntity::getCreateDatetime, dayStartDate)
               .list()
               .stream()
               .mapToInt(roundx -> Math.max(0, value(roundx.getPayoutPoints()) - value(roundx.getBetPoints())))
               .sum();
            int userRemaining = Math.max(0, config.getDailyPlayerProfitCap() - userProfitToday);
            int groupRemaining = Math.max(0, config.getDailyGroupProfitCap() - value(vault.getDailyOutflowPoints()));
            int maxPayoutPercent = config.getLayerPayoutPercents().get(5);
            int profitPercent = maxPayoutPercent - 100;
            int minBetLiability = (int)((long)config.getMinBet() * (long)profitPercent / 100L);
            if (value(vault.getVaultPoints()) < minBetLiability) {
               throw rule(HellDiceGameService.RuleCode.VAULT_UNAVAILABLE, "地狱金库不足以承担最低下注的第六层赔付");
            } else if (userRemaining < minBetLiability) {
               throw rule(HellDiceGameService.RuleCode.PAYOUT_LIMIT, "今天的个人盈利额度已用完，请明天再来");
            } else if (groupRemaining < minBetLiability) {
               throw rule(HellDiceGameService.RuleCode.PAYOUT_LIMIT, "本群今天的地狱骰赔付额度已用完，请明天再来");
            } else {
               int availableLiability = Math.min(value(vault.getVaultPoints()), Math.min(userRemaining, groupRemaining));
               int maxByLiability = profitPercent <= 0 ? 0 : (int)Math.min(2147483647L, (long)availableLiability * 100L / (long)profitPercent);
               int maxByPayout = (int)((long)config.getSinglePayoutCap() * 100L / (long)maxPayoutPercent);
               int currentlyAllowedBet = Math.min(config.getMaxBet(), Math.min(maxByLiability, maxByPayout));
               if (currentlyAllowedBet < config.getMinBet()) {
                  throw rule(HellDiceGameService.RuleCode.PAYOUT_LIMIT, "当前赔付额度不足以开局，请稍后再试");
               } else if (betPoints < config.getMinBet()) {
                  throw rule(HellDiceGameService.RuleCode.BET_OUT_OF_RANGE, "下注不能少于 " + config.getMinBet() + " 积分");
               } else if (betPoints > currentlyAllowedBet) {
                  throw rule(HellDiceGameService.RuleCode.BET_OUT_OF_RANGE, "按当前金库余额与赔付额度，本局最多下注 " + currentlyAllowedBet + " 积分");
               } else {
                  PointsProfile profile = this.pointsStore.getOrCreate(chatId, userId, username, displayName);
                  if (profile.getPoints() < (long)betPoints) {
                     throw rule(HellDiceGameService.RuleCode.INSUFFICIENT_POINTS, "积分不足，当前只有 " + profile.getPoints() + " 积分");
                  } else {
                     int highestPayout = payoutForDepth(betPoints, 6, config);
                     int liability = highestPayout - betPoints;
                     if (liability >= 0 && liability <= availableLiability) {
                        LocalDateTime now = LocalDateTime.now(BUSINESS_ZONE);
                        PointsBotHellRound round = new PointsBotHellRound();
                        round.setChatId(chatId);
                        round.setPlayerUserId(userId);
                        round.setPlayerUsername(username);
                        round.setPlayerDisplayName(displayName);
                        round.setStatus("BETTING");
                        round.setActiveGuard(1);
                        round.setConfigJson(JSONObject.toJSONString(config));
                        round.setBetPoints(betPoints);
                        round.setMaxProfitLiability(liability);
                        round.setCurrentDepth(0);
                        round.setTargetDepth(1);
                        round.setCurrentPayout(0);
                        round.setPayoutPoints(0);
                        round.setVaultContributionPoints(0);
                        round.setBettingEndsAt(now.plusSeconds((long)config.getBettingSeconds()));
                        this.roundMapper.insert(round);
                        int applied = this.pointsStore.addPoints(profile, -betPoints, "地狱骰-下注", "HELL_ROUND:" + round.getId());
                        if (applied != -betPoints) {
                           throw rule(HellDiceGameService.RuleCode.INSUFFICIENT_POINTS, "积分余额发生变化，请重新开局");
                        } else {
                           return this.toView(round);
                        }
                     } else {
                        throw rule(HellDiceGameService.RuleCode.VAULT_UNAVAILABLE, "地狱金库暂时无法承担本局最高赔付");
                     }
                  }
               }
            }
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.RoundView bindMessage(long roundId, long chatId, long messageId) {
      PointsBotHellRound round = this.requireRoundForUpdate(roundId);
      this.requireChat(round, chatId);
      if (round.getMessageId() != null && round.getMessageId() != messageId) {
         throw rule(HellDiceGameService.RuleCode.WRONG_MESSAGE, "这个面板不属于当前地狱骰");
      } else {
         round.setMessageId(messageId);
         this.roundMapper.updateById(round);
         return this.toView(round);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.BetResult placeSpectatorBet(
      long roundId, int depth, long chatId, long messageId, long userId, String username, String displayName, String side, int amount
   ) {
      PointsBotHellRound round = this.requireRoundForUpdate(roundId);
      this.requirePanel(round, chatId, messageId);
      HellDiceGameConfig config = this.parseConfig(round);
      if (!config.isSpectatorBetEnabled()) {
         throw rule(HellDiceGameService.RuleCode.BETTING_CLOSED, "本群未开启观众下注");
      } else if (!"BETTING".equals(round.getStatus())
         || value(round.getTargetDepth()) != depth
         || round.getBettingEndsAt() == null
         || !LocalDateTime.now(BUSINESS_ZONE).isBefore(round.getBettingEndsAt())) {
         throw rule(HellDiceGameService.RuleCode.BETTING_CLOSED, "本层下注已经截止");
      } else if (round.getPlayerUserId() == userId) {
         throw rule(HellDiceGameService.RuleCode.FORBIDDEN, "不能下注自己的地狱骰");
      } else if (!"SURVIVE".equals(side) && !"DIE".equals(side)) {
         throw rule(HellDiceGameService.RuleCode.INVALID_BET, "下注方向无效");
      } else if (amount >= config.getSpectatorMinBet() && amount <= config.getSpectatorMaxBetPerLayer()) {
         PointsBotHellBet existing = new LambdaQueryChainWrapper<>(this.betMapper)
            .eq(PointsBotHellBet::getRoundId, Long.valueOf(roundId))
            .eq(PointsBotHellBet::getDepth, Integer.valueOf(depth))
            .eq(PointsBotHellBet::getUserId, Long.valueOf(userId))
            .last("limit 1")
            .one();
         if (existing != null) {
            throw rule(HellDiceGameService.RuleCode.ALREADY_BET, "本层你已经下注过了");
         } else {
            int roundTotal = new LambdaQueryChainWrapper<>(this.betMapper)
               .eq(PointsBotHellBet::getRoundId, Long.valueOf(roundId))
               .eq(PointsBotHellBet::getUserId, Long.valueOf(userId))
               .list()
               .stream()
               .mapToInt(item -> value(item.getBetPoints()))
               .sum();
            if ((long)roundTotal + (long)amount > (long)config.getSpectatorMaxBetPerRound()) {
               throw rule(HellDiceGameService.RuleCode.INVALID_BET, "每人每局观众下注最多 " + config.getSpectatorMaxBetPerRound() + " 积分");
            } else {
               Date dayStart = Date.from(LocalDate.now(BUSINESS_ZONE).atStartOfDay().atZone(BUSINESS_ZONE).toInstant());
               int dailyTotal = new LambdaQueryChainWrapper<>(this.betMapper)
                  .eq(PointsBotHellBet::getChatId, Long.valueOf(chatId))
                  .eq(PointsBotHellBet::getUserId, Long.valueOf(userId))
                  .ge(BaseEntity::getCreateDatetime, dayStart)
                  .list()
                  .stream()
                  .mapToInt(item -> value(item.getBetPoints()))
                  .sum();
               if ((long)dailyTotal + (long)amount > (long)config.getSpectatorMaxBetPerDay()) {
                  throw rule(HellDiceGameService.RuleCode.INVALID_BET, "今天的观众下注额度已用完");
               } else {
                  int layerPool = this.currentLayerBets(roundId, depth).stream().mapToInt(item -> value(item.getBetPoints())).sum();
                  if ((long)layerPool + (long)amount > (long)config.getSpectatorPoolCapPerLayer()) {
                     throw rule(HellDiceGameService.RuleCode.INVALID_BET, "本层观众奖池已达到上限");
                  } else {
                     PointsProfile profile = this.pointsStore.getOrCreate(chatId, userId, username, displayName);
                     if (profile.getPoints() < (long)amount) {
                        throw rule(HellDiceGameService.RuleCode.INSUFFICIENT_POINTS, "积分不足，当前只有 " + profile.getPoints() + " 积分");
                     } else {
                        PointsBotHellBet bet = new PointsBotHellBet();
                        bet.setRoundId(roundId);
                        bet.setChatId(chatId);
                        bet.setDepth(depth);
                        bet.setUserId(userId);
                        bet.setUsername(username);
                        bet.setDisplayName(displayName);
                        bet.setSide(side);
                        bet.setBetPoints(amount);
                        bet.setStatus("PENDING");
                        bet.setPayoutPoints(0);
                        this.betMapper.insert(bet);
                        int applied = this.pointsStore.addPoints(profile, -amount, "地狱骰-观众下注", "HELL_BET:" + bet.getId());
                        if (applied != -amount) {
                           throw rule(HellDiceGameService.RuleCode.INSUFFICIENT_POINTS, "积分余额发生变化，本次下注未成立");
                        } else {
                           return new HellDiceGameService.BetResult(this.toView(round), side, amount);
                        }
                     }
                  }
               }
            }
         }
      } else {
         throw rule(HellDiceGameService.RuleCode.INVALID_BET, "每层下注必须在 " + config.getSpectatorMinBet() + " 到 " + config.getSpectatorMaxBetPerLayer() + " 积分之间");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.RoundView beginRoll(long roundId) {
      PointsBotHellRound round = this.requireRoundForUpdate(roundId);
      if (!"BETTING".equals(round.getStatus())) {
         return null;
      } else if (round.getBettingEndsAt() != null && LocalDateTime.now(BUSINESS_ZONE).isBefore(round.getBettingEndsAt())) {
         return null;
      } else {
         List<PointsBotHellBet> bets = this.currentLayerBets(roundId, value(round.getTargetDepth()));
         boolean hasSurvive = bets.stream().anyMatch(item -> "SURVIVE".equals(item.getSide()));
         boolean hasDie = bets.stream().anyMatch(item -> "DIE".equals(item.getSide()));
         if (!hasSurvive || !hasDie) {
            this.refundBets(bets);
         }

         round.setStatus("ROLLING");
         this.roundMapper.updateById(round);
         return this.toView(round);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.RoundView recordDiceValue(long roundId, int diceValue) {
      if (diceValue >= 1 && diceValue <= 6) {
         PointsBotHellRound round = this.requireRoundForUpdate(roundId);
         if (!"ROLLING".equals(round.getStatus())) {
            return this.toView(round);
         } else if (value(round.getLastDiceValue()) > 0 && value(round.getLastDiceValue()) != diceValue) {
            throw new IllegalStateException("同一层地狱骰结果不一致");
         } else {
            round.setLastDiceValue(diceValue);
            this.roundMapper.updateById(round);
            return this.toView(round);
         }
      } else {
         throw new IllegalArgumentException("Telegram 骰子结果必须在 1 到 6 之间");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.RollResult applyDice(long roundId, int diceValue) {
      if (diceValue >= 1 && diceValue <= 6) {
         PointsBotHellRound round = this.requireRoundForUpdate(roundId);
         if (!"ROLLING".equals(round.getStatus())) {
            throw rule(HellDiceGameService.RuleCode.ROUND_CLOSED, "本层已经处理，不能重复结算");
         } else {
            HellDiceGameConfig config = this.parseConfig(round);
            int depth = value(round.getTargetDepth());
            boolean died = deathNumbers(depth, config).contains(diceValue);
            PointsBotHellVault vault = this.lockInitializedVault(round.getChatId(), config);
            this.resetDailyOutflow(vault);
            this.settleSpectatorBets(round, depth, died, config, vault);
            round.setLastDiceValue(diceValue);
            round.setBettingEndsAt(null);
            if (died) {
               int requestedContribution = value(round.getBetPoints()) * config.getLossVaultPercent() / 100;
               int accepted = this.addVaultInflow(
                  vault, requestedContribution, config.getVaultCapacity(), "PLAYER_LOSS", "HELL_ROUND", String.valueOf(round.getId())
               );
               round.setVaultContributionPoints(accepted);
               round.setPayoutPoints(0);
               round.setCurrentPayout(0);
               round.setStatus("LOST");
               round.setActiveGuard(null);
               round.setSettledAt(LocalDateTime.now(BUSINESS_ZONE));
               this.roundMapper.updateById(round);
               return new HellDiceGameService.RollResult(this.toView(round), true, false, 0);
            } else {
               round.setCurrentDepth(depth);
               round.setCurrentPayout(payoutForDepth(value(round.getBetPoints()), depth, config));
               if (depth >= 6) {
                  int payout = this.settlePlayer(round, vault, "SETTLED");
                  return new HellDiceGameService.RollResult(this.toView(round), false, true, payout);
               } else {
                  round.setStatus("WAITING_DECISION");
                  round.setDecisionEndsAt(LocalDateTime.now(BUSINESS_ZONE).plusSeconds((long)config.getDecisionSeconds()));
                  this.roundMapper.updateById(round);
                  return new HellDiceGameService.RollResult(this.toView(round), false, false, 0);
               }
            }
         }
      } else {
         throw new IllegalArgumentException("Telegram 骰子结果必须在 1 到 6 之间");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.ActionResult continueRound(long roundId, long chatId, long messageId, long userId) {
      PointsBotHellRound round = this.requireRoundForUpdate(roundId);
      this.requirePanel(round, chatId, messageId);
      this.requireOwner(round, userId);
      if (!"WAITING_DECISION".equals(round.getStatus())) {
         throw rule(HellDiceGameService.RuleCode.ROUND_CLOSED, "当前不能继续下潜");
      } else {
         HellDiceGameConfig config = this.parseConfig(round);
         if (round.getDecisionEndsAt() != null && !LocalDateTime.now(BUSINESS_ZONE).isBefore(round.getDecisionEndsAt())) {
            PointsBotHellVault vault = this.lockInitializedVault(chatId, config);
            int payout = this.settlePlayer(round, vault, "SETTLED");
            return new HellDiceGameService.ActionResult(this.toView(round), HellDiceGameService.Action.AUTO_CASHED_OUT, payout);
         } else if (value(round.getCurrentDepth()) >= 6) {
            throw rule(HellDiceGameService.RuleCode.ROUND_CLOSED, "已经通过地狱第六层");
         } else {
            round.setTargetDepth(value(round.getCurrentDepth()) + 1);
            round.setStatus("BETTING");
            round.setLastDiceValue(null);
            round.setDecisionEndsAt(null);
            round.setBettingEndsAt(LocalDateTime.now(BUSINESS_ZONE).plusSeconds((long)config.getBettingSeconds()));
            this.roundMapper.updateById(round);
            return new HellDiceGameService.ActionResult(this.toView(round), HellDiceGameService.Action.CONTINUED, 0);
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.ActionResult cashOut(long roundId, long chatId, long messageId, long userId) {
      PointsBotHellRound round = this.requireRoundForUpdate(roundId);
      this.requirePanel(round, chatId, messageId);
      this.requireOwner(round, userId);
      if (!"WAITING_DECISION".equals(round.getStatus())) {
         throw rule(HellDiceGameService.RuleCode.ROUND_CLOSED, "本局已经结算或当前不能收手");
      } else {
         PointsBotHellVault vault = this.lockInitializedVault(chatId, this.parseConfig(round));
         int payout = this.settlePlayer(round, vault, "SETTLED");
         return new HellDiceGameService.ActionResult(this.toView(round), HellDiceGameService.Action.CASHED_OUT, payout);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.ActionResult autoCashOutExpired(long roundId) {
      PointsBotHellRound round = this.requireRoundForUpdate(roundId);
      if ("WAITING_DECISION".equals(round.getStatus())
         && round.getDecisionEndsAt() != null
         && !LocalDateTime.now(BUSINESS_ZONE).isBefore(round.getDecisionEndsAt())) {
         PointsBotHellVault vault = this.lockInitializedVault(round.getChatId(), this.parseConfig(round));
         int payout = this.settlePlayer(round, vault, "SETTLED");
         return new HellDiceGameService.ActionResult(this.toView(round), HellDiceGameService.Action.AUTO_CASHED_OUT, payout);
      } else {
         return null;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.ActionResult refundAfterTransportFailure(long roundId) {
      PointsBotHellRound round = this.requireRoundForUpdate(roundId);
      if (isTerminal(round.getStatus())) {
         return null;
      } else {
         this.refundBets(this.currentLayerBets(roundId, value(round.getTargetDepth())));
         HellDiceGameConfig config = this.parseConfig(round);
         if (value(round.getCurrentDepth()) > 0) {
            PointsBotHellVault vault = this.lockInitializedVault(round.getChatId(), config);
            int payout = this.settlePlayer(round, vault, "REFUNDED");
            return new HellDiceGameService.ActionResult(this.toView(round), HellDiceGameService.Action.REFUNDED, payout);
         } else {
            PointsProfile player = this.pointsStore.findByUserId(round.getChatId(), round.getPlayerUserId());
            if (player == null) {
               throw new IllegalStateException("地狱骰玩家积分档案不存在");
            } else {
               int refund = value(round.getBetPoints());
               this.pointsStore.addPoints(player, refund, "地狱骰-系统退款", "HELL_ROUND:" + round.getId());
               round.setStatus("REFUNDED");
               round.setActiveGuard(null);
               round.setPayoutPoints(refund);
               round.setSettledAt(LocalDateTime.now(BUSINESS_ZONE));
               this.roundMapper.updateById(round);
               return new HellDiceGameService.ActionResult(this.toView(round), HellDiceGameService.Action.REFUNDED, refund);
            }
         }
      }
   }

   public HellDiceGameService.RoundView getView(long roundId) {
      PointsBotHellRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      return round == null ? null : this.toView(round);
   }

   public boolean hasOpenRounds() {
      return new LambdaQueryChainWrapper<>(this.roundMapper).eq(PointsBotHellRound::getActiveGuard, Integer.valueOf(1)).count() > 0L;
   }

   public List<Long> findExpiredBettingRoundIds(int limit) {
      return new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotHellRound::getStatus, "BETTING")
         .le(PointsBotHellRound::getBettingEndsAt, LocalDateTime.now(BUSINESS_ZONE))
         .orderByAsc(PointsBotHellRound::getBettingEndsAt)
         .last("limit " + Math.max(1, Math.min(limit, 50)))
         .list()
         .stream()
         .map(PointsBotHellRound::getId)
         .toList();
   }

   public List<Long> findExpiredDecisionRoundIds(int limit) {
      return new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotHellRound::getStatus, "WAITING_DECISION")
         .le(PointsBotHellRound::getDecisionEndsAt, LocalDateTime.now(BUSINESS_ZONE))
         .orderByAsc(PointsBotHellRound::getDecisionEndsAt)
         .last("limit " + Math.max(1, Math.min(limit, 50)))
         .list()
         .stream()
         .map(PointsBotHellRound::getId)
         .toList();
   }

   public List<HellDiceGameService.RoundView> findRollingRounds(int limit) {
      Date staleBefore = Date.from(Instant.now().minusSeconds(20L));
      return new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotHellRound::getStatus, "ROLLING")
         .le(BaseEntity::getUpdateDatetime, staleBefore)
         .orderByAsc(BaseEntity::getUpdateDatetime)
         .last("limit " + Math.max(1, Math.min(limit, 50)))
         .list()
         .stream()
         .map(this::toView)
         .toList();
   }

   public HellDiceGameService.VaultView getVaultView(long chatId, HellDiceGameConfig config) {
      PointsBotHellVault vault = new LambdaQueryChainWrapper<>(this.vaultMapper).eq(PointsBotHellVault::getChatId, Long.valueOf(chatId)).last("limit 1").one();
      if (vault != null && Boolean.TRUE.equals(vault.getInitialized())) {
         return new HellDiceGameService.VaultView(
            value(vault.getVaultPoints()), config.getVaultCapacity(), value(vault.getTotalSubsidyPoints()), config.getAdminTopUpLifetimeCap(), true
         );
      } else {
         int projected = Math.min(config.getInitialVaultPoints(), Math.min(config.getVaultCapacity(), config.getAdminTopUpLifetimeCap()));
         return new HellDiceGameService.VaultView(projected, config.getVaultCapacity(), projected, config.getAdminTopUpLifetimeCap(), false);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.VaultView topUpVault(long chatId, int amount, HellDiceGameConfig config) {
      return this.topUpVault(chatId, amount, config, "WEB_ADMIN", null);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public HellDiceGameService.VaultView topUpVault(long chatId, int amount, HellDiceGameConfig config, String source, String actorId) {
      if (amount > 0 && amount <= 100000) {
         PointsBotHellVault vault = this.lockInitializedVault(chatId, config);
         int capacityRemaining = config.getVaultCapacity() - value(vault.getVaultPoints());
         int subsidyRemaining = config.getAdminTopUpLifetimeCap() - value(vault.getTotalSubsidyPoints());
         int allowed = Math.min(capacityRemaining, subsidyRemaining);
         if (amount > allowed) {
            throw rule(HellDiceGameService.RuleCode.VAULT_UNAVAILABLE, "本次最多可补充 " + Math.max(0, allowed) + " 积分；已受金库容量或累计补充上限限制");
         } else {
            vault.setVaultPoints(value(vault.getVaultPoints()) + amount);
            vault.setTotalSubsidyPoints(value(vault.getTotalSubsidyPoints()) + amount);
            this.vaultMapper.updateById(vault);
            String auditSource = "TELEGRAM_ADMIN".equals(source) ? "TELEGRAM_ADMIN" : "WEB_ADMIN";
            String auditActor = actorId != null && actorId.matches("[1-9]\\d{0,18}") ? actorId : null;
            this.insertVaultLedger(vault, "TOP_UP", amount, auditSource, auditActor);
            return new HellDiceGameService.VaultView(
               value(vault.getVaultPoints()), config.getVaultCapacity(), value(vault.getTotalSubsidyPoints()), config.getAdminTopUpLifetimeCap(), true
            );
         }
      } else {
         throw rule(HellDiceGameService.RuleCode.INVALID_BET, "补充积分必须在 1 到 100000 之间");
      }
   }

   public List<HellDiceGameService.RankingEntry> weeklyRanking(long chatId, int limit) {
      LocalDate weekStart = LocalDate.now(BUSINESS_ZONE).minusDays((long)LocalDate.now(BUSINESS_ZONE).getDayOfWeek().getValue() - 1L);
      Date weekStartDate = Date.from(weekStart.atStartOfDay().atZone(BUSINESS_ZONE).toInstant());
      List<PointsBotHellRound> rounds = new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotHellRound::getChatId, Long.valueOf(chatId))
         .in(PointsBotHellRound::getStatus, new Object[]{"SETTLED", "LOST"})
         .ge(BaseEntity::getCreateDatetime, weekStartDate)
         .list();
      Map<Long, HellDiceGameService.RankingAccumulator> accumulators = new LinkedHashMap<>();

      for (PointsBotHellRound round : rounds) {
         HellDiceGameService.RankingAccumulator acc = accumulators.computeIfAbsent(
            round.getPlayerUserId(),
            ignored -> new HellDiceGameService.RankingAccumulator(round.getPlayerUserId(), round.getPlayerUsername(), round.getPlayerDisplayName())
         );
         acc.attempts++;
         int depth = value(round.getCurrentDepth());
         if (depth >= 6 && "SETTLED".equals(round.getStatus())) {
            acc.clears++;
         }

         Instant created = round.getCreateDatetime() == null ? Instant.now() : round.getCreateDatetime().toInstant();
         LocalDate day = created.atZone(BUSINESS_ZONE).toLocalDate();
         acc.dailyBest.merge(day, depth, Math::max);
      }

      List<HellDiceGameService.RankingEntry> result = new ArrayList<>();

      for (HellDiceGameService.RankingAccumulator acc : accumulators.values()) {
         int score = acc.dailyBest.values().stream().mapToInt(HellDiceGameService::scoreDepth).sum();
         if (score > 0) {
            int bestDepth = acc.dailyBest.values().stream().mapToInt(Integer::intValue).max().orElse(0);
            result.add(new HellDiceGameService.RankingEntry(acc.userId, acc.username, acc.displayName, score, bestDepth, acc.clears, acc.attempts));
         }
      }

      result.sort(
         Comparator.comparingInt(HellDiceGameService.RankingEntry::score)
            .reversed()
            .thenComparing(Comparator.comparingInt(HellDiceGameService.RankingEntry::clears).reversed())
            .thenComparingInt(HellDiceGameService.RankingEntry::attempts)
            .thenComparingLong(HellDiceGameService.RankingEntry::userId)
      );
      return result.stream().limit((long)Math.max(1, Math.min(limit, 50))).toList();
   }

   private int settlePlayer(PointsBotHellRound round, PointsBotHellVault vault, String finalStatus) {
      this.resetDailyOutflow(vault);
      int payout = value(round.getCurrentPayout());
      int bet = value(round.getBetPoints());
      int profit = Math.max(0, payout - bet);
      if (profit <= value(round.getMaxProfitLiability()) && profit <= value(vault.getVaultPoints())) {
         PointsProfile player = this.pointsStore.findByUserId(round.getChatId(), round.getPlayerUserId());
         if (player == null) {
            throw new IllegalStateException("地狱骰玩家积分档案不存在");
         } else {
            this.pointsStore.addPoints(player, payout, "地狱骰-收手", "HELL_ROUND:" + round.getId());
            if (profit > 0) {
               vault.setVaultPoints(value(vault.getVaultPoints()) - profit);
               vault.setTotalOutflowPoints(value(vault.getTotalOutflowPoints()) + (long)profit);
               vault.setDailyOutflowPoints(value(vault.getDailyOutflowPoints()) + profit);
               this.vaultMapper.updateById(vault);
               this.insertVaultLedger(vault, "PLAYER_PROFIT", -profit, "HELL_ROUND", String.valueOf(round.getId()));
            }

            round.setPayoutPoints(payout);
            round.setStatus(finalStatus);
            round.setActiveGuard(null);
            round.setDecisionEndsAt(null);
            round.setSettledAt(LocalDateTime.now(BUSINESS_ZONE));
            this.roundMapper.updateById(round);
            return payout;
         }
      } else {
         throw new IllegalStateException("地狱骰金库责任校验失败");
      }
   }

   private void settleSpectatorBets(PointsBotHellRound round, int depth, boolean died, HellDiceGameConfig config, PointsBotHellVault vault) {
      List<PointsBotHellBet> bets = this.currentLayerBets(round.getId(), depth);
      if (!bets.isEmpty()) {
         int survivePool = bets.stream().filter(item -> "SURVIVE".equals(item.getSide())).mapToInt(item -> value(item.getBetPoints())).sum();
         int diePool = bets.stream().filter(item -> "DIE".equals(item.getSide())).mapToInt(item -> value(item.getBetPoints())).sum();
         if (survivePool > 0 && diePool > 0) {
            String winningSide = died ? "DIE" : "SURVIVE";
            int totalPool = survivePool + diePool;
            int fee = totalPool * config.getSpectatorFeePercent() / 100;
            int distributable = totalPool - fee;
            int winnerPool = died ? diePool : survivePool;
            int distributed = 0;
            LocalDateTime now = LocalDateTime.now(BUSINESS_ZONE);

            for (PointsBotHellBet bet : bets) {
               bet.setSettledAt(now);
               if (winningSide.equals(bet.getSide())) {
                  int reward = (int)((long)distributable * (long)value(bet.getBetPoints()) / (long)winnerPool);
                  PointsProfile profile = this.pointsStore.findByUserId(round.getChatId(), bet.getUserId());
                  if (profile == null) {
                     throw new IllegalStateException("地狱骰观众积分档案不存在");
                  }

                  this.pointsStore.addPoints(profile, reward, "地狱骰-观众获胜", "HELL_BET:" + bet.getId());
                  bet.setStatus("WON");
                  bet.setPayoutPoints(reward);
                  distributed += reward;
               } else {
                  bet.setStatus("LOST");
                  bet.setPayoutPoints(0);
               }

               this.betMapper.updateById(bet);
            }

            int retained = Math.max(0, totalPool - distributed);
            this.addVaultInflow(vault, retained, config.getVaultCapacity(), "SPECTATOR_FEE", "HELL_ROUND", round.getId() + ":" + depth);
         } else {
            this.refundBets(bets);
         }
      }
   }

   private void refundBets(List<PointsBotHellBet> bets) {
      LocalDateTime now = LocalDateTime.now(BUSINESS_ZONE);

      for (PointsBotHellBet bet : bets) {
         if ("PENDING".equals(bet.getStatus())) {
            PointsProfile profile = this.pointsStore.findByUserId(bet.getChatId(), bet.getUserId());
            if (profile == null) {
               throw new IllegalStateException("地狱骰观众积分档案不存在");
            }

            int amount = value(bet.getBetPoints());
            this.pointsStore.addPoints(profile, amount, "地狱骰-观众退款", "HELL_BET:" + bet.getId());
            bet.setStatus("REFUNDED");
            bet.setPayoutPoints(amount);
            bet.setSettledAt(now);
            this.betMapper.updateById(bet);
         }
      }
   }

   private int addVaultInflow(PointsBotHellVault vault, int requested, int capacity, String changeType, String refType, String refId) {
      if (requested <= 0) {
         return 0;
      } else {
         int accepted = Math.min(requested, Math.max(0, capacity - value(vault.getVaultPoints())));
         if (accepted <= 0) {
            return 0;
         } else {
            vault.setVaultPoints(value(vault.getVaultPoints()) + accepted);
            vault.setTotalInflowPoints(value(vault.getTotalInflowPoints()) + (long)accepted);
            this.vaultMapper.updateById(vault);
            this.insertVaultLedger(vault, changeType, accepted, refType, refId);
            return accepted;
         }
      }
   }

   private PointsBotHellVault lockInitializedVault(long chatId, HellDiceGameConfig config) {
      this.vaultMapper.ensureExists(chatId);
      PointsBotHellVault vault = this.vaultMapper.selectForUpdate(chatId);
      if (vault == null) {
         throw new IllegalStateException("地狱骰金库初始化失败");
      } else {
         if (!Boolean.TRUE.equals(vault.getInitialized())) {
            int initial = Math.min(config.getInitialVaultPoints(), Math.min(config.getVaultCapacity(), config.getAdminTopUpLifetimeCap()));
            vault.setVaultPoints(initial);
            vault.setTotalSubsidyPoints(initial);
            vault.setInitialized(true);
            this.vaultMapper.updateById(vault);
            if (initial > 0) {
               this.insertVaultLedger(vault, "INITIAL", initial, "CONFIG", null);
            }
         }

         return vault;
      }
   }

   private void resetDailyOutflow(PointsBotHellVault vault) {
      LocalDate today = LocalDate.now(BUSINESS_ZONE);
      if (!today.equals(vault.getDailyOutflowDate())) {
         vault.setDailyOutflowDate(today);
         vault.setDailyOutflowPoints(0);
         this.vaultMapper.updateById(vault);
      }
   }

   private void insertVaultLedger(PointsBotHellVault vault, String type, int delta, String refType, String refId) {
      PointsBotHellVaultLedger ledger = new PointsBotHellVaultLedger();
      ledger.setChatId(vault.getChatId());
      ledger.setChangeType(type);
      ledger.setDeltaPoints(delta);
      ledger.setBalanceAfter(value(vault.getVaultPoints()));
      ledger.setRefType(refType);
      ledger.setRefId(refId);
      this.vaultLedgerMapper.insert(ledger);
   }

   private List<PointsBotHellBet> currentLayerBets(long roundId, int depth) {
      return new LambdaQueryChainWrapper<>(this.betMapper)
         .eq(PointsBotHellBet::getRoundId, Long.valueOf(roundId))
         .eq(PointsBotHellBet::getDepth, Integer.valueOf(depth))
         .eq(PointsBotHellBet::getStatus, "PENDING")
         .orderByAsc(PointsBotHellBet::getId)
         .list();
   }

   private List<PointsBotHellBet> layerBets(long roundId, int depth) {
      return new LambdaQueryChainWrapper<>(this.betMapper)
         .eq(PointsBotHellBet::getRoundId, Long.valueOf(roundId))
         .eq(PointsBotHellBet::getDepth, Integer.valueOf(depth))
         .orderByAsc(PointsBotHellBet::getId)
         .list();
   }

   private HellDiceGameService.RoundView toView(PointsBotHellRound round) {
      int depth = value(round.getTargetDepth());
      List<PointsBotHellBet> bets = this.layerBets(round.getId(), depth);
      int survivePool = bets.stream().filter(item -> "SURVIVE".equals(item.getSide())).mapToInt(item -> value(item.getBetPoints())).sum();
      int diePool = bets.stream().filter(item -> "DIE".equals(item.getSide())).mapToInt(item -> value(item.getBetPoints())).sum();
      return new HellDiceGameService.RoundView(
         round.getId(),
         round.getChatId(),
         round.getMessageId(),
         round.getPlayerUserId(),
         round.getPlayerUsername(),
         round.getPlayerDisplayName(),
         round.getStatus(),
         value(round.getBetPoints()),
         value(round.getCurrentDepth()),
         value(round.getTargetDepth()),
         value(round.getCurrentPayout()),
         value(round.getLastDiceValue()),
         round.getBettingEndsAt(),
         round.getDecisionEndsAt(),
         value(round.getPayoutPoints()),
         survivePool,
         diePool,
         bets.stream()
            .map(
               bet -> new HellDiceGameService.SpectatorBetView(
                     bet.getUserId(),
                     bet.getUsername(),
                     bet.getDisplayName(),
                     bet.getSide(),
                     value(bet.getBetPoints()),
                     bet.getStatus(),
                     value(bet.getPayoutPoints())
                  )
            )
            .toList(),
         this.parseConfig(round)
      );
   }

   private HellDiceGameConfig parseConfig(PointsBotHellRound round) {
      JSONObject snapshot = JSONObject.parseObject(round.getConfigJson());
      HellDiceGameConfig config = snapshot == null ? null : snapshot.toJavaObject(HellDiceGameConfig.class);
      if (config == null) {
         throw new IllegalStateException("地狱骰轮次配置缺失");
      } else {
         if (!snapshot.containsKey("layerDeathNumbers") && snapshot.containsKey("layerDeathMaxes")) {
            config.setLayerDeathNumbers(new ArrayList<>());
         }

         return config;
      }
   }

   private PointsBotHellRound requireRoundForUpdate(long roundId) {
      PointsBotHellRound round = this.roundMapper.selectForUpdate(roundId);
      if (round == null) {
         throw rule(HellDiceGameService.RuleCode.NOT_FOUND, "地狱骰轮次不存在或已经失效");
      } else {
         return round;
      }
   }

   private void requireChat(PointsBotHellRound round, long chatId) {
      if (round.getChatId() == null || round.getChatId() != chatId) {
         throw rule(HellDiceGameService.RuleCode.WRONG_CHAT, "这个地狱骰不属于当前群聊");
      }
   }

   private void requirePanel(PointsBotHellRound round, long chatId, long messageId) {
      this.requireChat(round, chatId);
      if (round.getMessageId() == null || round.getMessageId() != messageId) {
         throw rule(HellDiceGameService.RuleCode.WRONG_MESSAGE, "这个按钮不属于当前地狱骰面板");
      }
   }

   private void requireOwner(PointsBotHellRound round, long userId) {
      if (round.getPlayerUserId() == null || round.getPlayerUserId() != userId) {
         throw rule(HellDiceGameService.RuleCode.FORBIDDEN, "只有本局玩家可以操作");
      }
   }

   private static int payoutForDepth(int bet, int depth, HellDiceGameConfig config) {
      int index = Math.max(0, Math.min(depth, 6) - 1);
      return (int)((long)bet * (long)config.getLayerPayoutPercents().get(index).intValue() / 100L);
   }

   public static List<Integer> deathNumbers(int depth, HellDiceGameConfig config) {
      int index = Math.max(1, Math.min(depth, 6)) - 1;
      List<List<Integer>> configured = config == null ? null : config.getLayerDeathNumbers();
      if (configured != null && configured.size() == 6 && configured.get(index) != null && !configured.get(index).isEmpty()) {
         return List.copyOf(configured.get(index));
      } else {
         List<Integer> legacyDeathMaxes = config == null ? null : config.getLayerDeathMaxes();
         if (legacyDeathMaxes != null && legacyDeathMaxes.size() == 6 && legacyDeathMaxes.get(index) != null) {
            int deathMax = Math.max(1, Math.min(legacyDeathMaxes.get(index), 5));
            List<Integer> legacyDeathNumbers = new ArrayList<>(deathMax);

            for (int value = 1; value <= deathMax; value++) {
               legacyDeathNumbers.add(value);
            }

            return List.copyOf(legacyDeathNumbers);
         } else {
            return List.copyOf(HellDiceGameConfig.defaultLayerDeathNumbers().get(index));
         }
      }
   }

   private static int scoreDepth(int depth) {
      return switch (Math.max(0, Math.min(depth, 6))) {
         case 1 -> 1;
         case 2 -> 3;
         case 3 -> 6;
         case 4 -> 10;
         case 5 -> 15;
         case 6 -> 25;
         default -> 0;
      };
   }

   private static boolean isTerminal(String status) {
      return "SETTLED".equals(status) || "LOST".equals(status) || "REFUNDED".equals(status);
   }

   private static int value(Integer value) {
      return value == null ? 0 : value;
   }

   private static long value(Long value) {
      return value == null ? 0L : value;
   }

   private static HellDiceGameService.RuleViolation rule(HellDiceGameService.RuleCode code, String message) {
      return new HellDiceGameService.RuleViolation(code, message);
   }

   @Generated
   public HellDiceGameService(
      final PointsBotHellVaultMapper vaultMapper,
      final PointsBotHellRoundMapper roundMapper,
      final PointsBotHellBetMapper betMapper,
      final PointsBotHellVaultLedgerMapper vaultLedgerMapper,
      final PointsStore pointsStore
   ) {
      this.vaultMapper = vaultMapper;
      this.roundMapper = roundMapper;
      this.betMapper = betMapper;
      this.vaultLedgerMapper = vaultLedgerMapper;
      this.pointsStore = pointsStore;
   }

   public static enum Action {
      CONTINUED,
      CASHED_OUT,
      AUTO_CASHED_OUT,
      REFUNDED;
   }

   public static record ActionResult(HellDiceGameService.RoundView round, HellDiceGameService.Action action, int payout) {
   }

   public static record BetResult(HellDiceGameService.RoundView round, String side, int amount) {
   }

   private static final class RankingAccumulator {
      private final long userId;
      private final String username;
      private final String displayName;
      private final Map<LocalDate, Integer> dailyBest = new HashMap<>();
      private int clears;
      private int attempts;

      private RankingAccumulator(long userId, String username, String displayName) {
         this.userId = userId;
         this.username = username;
         this.displayName = displayName;
      }
   }

   public static record RankingEntry(long userId, String username, String displayName, int score, int bestDepth, int clears, int attempts) {
   }

   public static record RollResult(HellDiceGameService.RoundView round, boolean died, boolean completed, int payout) {
   }

   public static record RoundView(
      long id,
      long chatId,
      Long messageId,
      long playerUserId,
      String playerUsername,
      String playerDisplayName,
      String status,
      int betPoints,
      int currentDepth,
      int targetDepth,
      int currentPayout,
      int lastDiceValue,
      LocalDateTime bettingEndsAt,
      LocalDateTime decisionEndsAt,
      int payoutPoints,
      int survivePool,
      int diePool,
      List<HellDiceGameService.SpectatorBetView> spectatorBets,
      HellDiceGameConfig config
   ) {
   }

   public static enum RuleCode {
      ACTIVE_ROUND,
      DAILY_LIMIT,
      VAULT_UNAVAILABLE,
      PAYOUT_LIMIT,
      BET_OUT_OF_RANGE,
      INSUFFICIENT_POINTS,
      BETTING_CLOSED,
      INVALID_BET,
      ALREADY_BET,
      FORBIDDEN,
      WRONG_CHAT,
      WRONG_MESSAGE,
      ROUND_CLOSED,
      NOT_FOUND;
   }

   public static class RuleViolation extends RuntimeException {
      private final HellDiceGameService.RuleCode code;

      public RuleViolation(HellDiceGameService.RuleCode code, String message) {
         super(message);
         this.code = code;
      }

      public HellDiceGameService.RuleCode getCode() {
         return this.code;
      }
   }

   public static record SpectatorBetView(long userId, String username, String displayName, String side, int betPoints, String status, int payoutPoints) {
   }

   public static record VaultView(int balance, int capacity, int totalSubsidy, int subsidyLifetimeCap, boolean initialized) {
   }
}
