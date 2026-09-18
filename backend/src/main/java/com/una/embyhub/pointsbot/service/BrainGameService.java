package com.una.embyhub.pointsbot.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.una.embyhub.mapper.PointsBotBrainEntryMapper;
import com.una.embyhub.mapper.PointsBotBrainJackpotMapper;
import com.una.embyhub.mapper.PointsBotBrainRoundMapper;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotBrainEntry;
import com.una.embyhub.model.entity.PointsBotBrainJackpot;
import com.una.embyhub.model.entity.PointsBotBrainRound;
import com.una.embyhub.pointsbot.model.BrainGameConfig;
import com.una.embyhub.pointsbot.model.BrainQuestion;
import com.una.embyhub.pointsbot.model.PointsProfile;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrainGameService {
   public static final String STATUS_REGISTERING = "REGISTERING";
   public static final String STATUS_ACTIVE = "ACTIVE";
   public static final String STATUS_SETTLED = "SETTLED";
   public static final String STATUS_CANCELLED = "CANCELLED";
   public static final String STATUS_INVALID = "INVALID";
   private static final ZoneId BUSINESS_ZONE = ZoneId.of("Asia/Shanghai");
   private final PointsBotBrainRoundMapper roundMapper;
   private final PointsBotBrainEntryMapper entryMapper;
   private final PointsBotBrainJackpotMapper jackpotMapper;
   private final PointsStore pointsStore;
   private final BrainQuestionGenerator questionGenerator;

   public PointsBotBrainRound findOpenRound(long chatId) {
      return new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotBrainRound::getChatId, Long.valueOf(chatId))
         .in(PointsBotBrainRound::getStatus, new Object[]{"REGISTERING", "ACTIVE"})
         .orderByDesc(PointsBotBrainRound::getId)
         .last("limit 1")
         .one();
   }

   public boolean hasOpenRounds() {
      return ChainWrappers.lambdaQueryChain(this.roundMapper).in(PointsBotBrainRound::getStatus, new Object[]{"REGISTERING", "ACTIVE"}).count() > 0L;
   }

   @Transactional
   public synchronized PointsBotBrainRound createRound(long chatId, BrainGameConfig config) {
      PointsBotBrainRound open = this.findOpenRound(chatId);
      if (open != null) {
         return open;
      } else {
         PointsBotBrainJackpot state = this.lockOrCreateJackpot(chatId);
         boolean peak = Boolean.TRUE.equals(state.getPeakPending()) || (state.getCompletedRounds() + 1) % config.getPeakEveryRounds() == 0;
         PointsBotBrainRound round = new PointsBotBrainRound();
         round.setChatId(chatId);
         round.setStatus("REGISTERING");
         round.setPeak(peak);
         round.setConfigJson(JSONObject.toJSONString(config));
         round.setEntryCost(config.getEntryCost());
         round.setMinPlayers(peak ? config.getPeakMinPlayers() : config.getMinPlayers());
         round.setMaxPlayers(config.getMaxPlayers());
         round.setRegistrationEndsAt(LocalDateTime.now().plusSeconds((long)config.getRegistrationSeconds()));
         round.setTotalPot(0);
         this.roundMapper.insert(round);
         return round;
      }
   }

   public BrainGameService.RoundView getRoundView(long roundId) {
      PointsBotBrainRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      return round == null ? null : new BrainGameService.RoundView(round, this.listEntries(roundId), this.currentJackpot(round.getChatId()));
   }

   public void updateMessageId(long roundId, long messageId) {
      PointsBotBrainRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      if (round != null) {
         round.setMessageId(messageId);
         this.roundMapper.updateById(round);
      }
   }

   @Transactional
   public BrainGameService.JoinResult join(long roundId, long chatId, long userId, String username, String displayName) {
      PointsBotBrainRound round = this.lockRound(roundId);
      if (round != null && "REGISTERING".equals(round.getStatus())) {
         if (round.getChatId() == null || round.getChatId() != chatId) {
            return new BrainGameService.JoinResult(BrainGameService.JoinStatus.WRONG_CHAT, round, null);
         } else if (!LocalDateTime.now().isBefore(round.getRegistrationEndsAt())) {
            return new BrainGameService.JoinResult(BrainGameService.JoinStatus.EXPIRED, round, null);
         } else {
            PointsBotBrainEntry existing = this.findEntry(roundId, userId);
            if (existing != null) {
               return new BrainGameService.JoinResult(BrainGameService.JoinStatus.ALREADY_JOINED, round, existing);
            } else if (this.countEntries(roundId) >= (long)round.getMaxPlayers().intValue()) {
               return new BrainGameService.JoinResult(BrainGameService.JoinStatus.FULL, round, null);
            } else {
               BrainGameConfig config = this.parseConfig(round);
               if (this.countDailyPlays(chatId, userId) >= (long)config.getDailyPlayLimit()) {
                  return new BrainGameService.JoinResult(BrainGameService.JoinStatus.DAILY_LIMIT, round, null);
               } else if (this.countDailyChampions(chatId, userId) >= (long)config.getDailyChampionLimit()) {
                  return new BrainGameService.JoinResult(BrainGameService.JoinStatus.CHAMPION_LIMIT, round, null);
               } else {
                  PointsProfile profile = this.pointsStore.getOrCreate(chatId, userId, username, displayName);
                  if (profile.getPoints() < (long)round.getEntryCost().intValue()) {
                     return new BrainGameService.JoinResult(BrainGameService.JoinStatus.INSUFFICIENT_POINTS, round, null);
                  } else {
                     PointsBotBrainEntry entry = new PointsBotBrainEntry();
                     entry.setRoundId(roundId);
                     entry.setChatId(chatId);
                     entry.setUserId(userId);
                     entry.setUsername(username);
                     entry.setDisplayName(displayName);
                     entry.setEntryCost(round.getEntryCost());
                     entry.setSubmissionCount(0);
                     entry.setCorrect(false);
                     entry.setCountedPlay(false);
                     this.entryMapper.insert(entry);
                     int applied = this.pointsStore.addPoints(profile, -round.getEntryCost(), "Brain-报名", "brain:" + roundId + ":entry");
                     if (applied != -round.getEntryCost()) {
                        throw new IllegalStateException("Brain 报名积分扣除失败");
                     } else {
                        round.setTotalPot((round.getTotalPot() == null ? 0 : round.getTotalPot()) + round.getEntryCost());
                        this.roundMapper.updateById(round);
                        return new BrainGameService.JoinResult(BrainGameService.JoinStatus.JOINED, round, entry);
                     }
                  }
               }
            }
         }
      } else {
         return new BrainGameService.JoinResult(BrainGameService.JoinStatus.CLOSED, round, null);
      }
   }

   public List<Long> findRegistrationDueRoundIds(LocalDateTime now) {
      return ChainWrappers.lambdaQueryChain(this.roundMapper)
         .select(PointsBotBrainRound::getId)
         .eq(PointsBotBrainRound::getStatus, "REGISTERING")
         .le(PointsBotBrainRound::getRegistrationEndsAt, now)
         .list()
         .stream()
         .map(PointsBotBrainRound::getId)
         .toList();
   }

   @Transactional
   public BrainGameService.RegistrationResult closeRegistration(long roundId) {
      PointsBotBrainRound round = this.lockRound(roundId);
      if (round == null) {
         return new BrainGameService.RegistrationResult(BrainGameService.RegistrationStatus.NOT_FOUND, null, List.of());
      } else if (!"REGISTERING".equals(round.getStatus())) {
         return new BrainGameService.RegistrationResult(BrainGameService.RegistrationStatus.ALREADY_PROCESSED, round, this.listEntries(roundId));
      } else if (LocalDateTime.now().isBefore(round.getRegistrationEndsAt())) {
         return new BrainGameService.RegistrationResult(BrainGameService.RegistrationStatus.NOT_DUE, round, this.listEntries(roundId));
      } else {
         List<PointsBotBrainEntry> entries = this.listEntries(roundId);
         if (entries.size() < round.getMinPlayers()) {
            this.refundEntries(round, entries, "Brain-报名取消退款");
            round.setStatus("CANCELLED");
            round.setSettledAt(LocalDateTime.now());
            this.roundMapper.updateById(round);
            if (Boolean.TRUE.equals(round.getPeak())) {
               PointsBotBrainJackpot state = this.lockOrCreateJackpot(round.getChatId());
               state.setPeakPending(true);
               this.jackpotMapper.updateById(state);
            }

            return new BrainGameService.RegistrationResult(BrainGameService.RegistrationStatus.CANCELLED, round, entries);
         } else {
            BrainGameConfig config = this.parseConfig(round);

            try {
               BrainQuestion question = this.questionGenerator.generate(config);
               LocalDateTime now = LocalDateTime.now();
               round.setQuestionType(question.type());
               round.setQuestionPrompt(question.prompt());
               round.setHiddenPrompt(question.hiddenPrompt());
               round.setAnswerData(question.answerData());
               round.setExplanation(question.explanation());
               round.setStartedAt(now);
               round.setQuestionHidesAt(question.hideAfterSeconds() > 0 ? now.plusSeconds((long)question.hideAfterSeconds()) : null);
               round.setAnswerEndsAt(now.plusSeconds((long)config.getAnswerSeconds()));
               round.setStatus("ACTIVE");
               this.roundMapper.updateById(round);

               for (PointsBotBrainEntry entry : entries) {
                  entry.setCountedPlay(true);
                  this.entryMapper.updateById(entry);
               }

               return new BrainGameService.RegistrationResult(BrainGameService.RegistrationStatus.STARTED, round, entries);
            } catch (RuntimeException var10) {
               this.refundEntries(round, entries, "Brain-题目异常退款");
               round.setStatus("INVALID");
               round.setSettledAt(LocalDateTime.now());
               this.roundMapper.updateById(round);
               if (Boolean.TRUE.equals(round.getPeak())) {
                  PointsBotBrainJackpot state = this.lockOrCreateJackpot(round.getChatId());
                  state.setPeakPending(true);
                  this.jackpotMapper.updateById(state);
               }

               return new BrainGameService.RegistrationResult(BrainGameService.RegistrationStatus.INVALID, round, entries);
            }
         }
      }
   }

   public BrainGameService.PrivateSession findPrivateSession(long userId) {
      for (PointsBotBrainEntry entry : new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotBrainEntry::getUserId, Long.valueOf(userId))
         .orderByDesc(PointsBotBrainEntry::getId)
         .last("limit 10")
         .list()) {
         PointsBotBrainRound round = this.roundMapper.selectById(entry.getRoundId());
         if (round != null && "ACTIVE".equals(round.getStatus()) && LocalDateTime.now().isBefore(round.getAnswerEndsAt())) {
            return new BrainGameService.PrivateSession(round, entry);
         }
      }

      return null;
   }

   public BrainGameService.PrivateSession findPrivateSession(long roundId, long userId) {
      PointsBotBrainRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      PointsBotBrainEntry entry = this.findEntry(roundId, userId);
      return round != null && entry != null && "ACTIVE".equals(round.getStatus()) ? new BrainGameService.PrivateSession(round, entry) : null;
   }

   @Transactional
   public BrainGameService.SubmissionResult submit(long userId, String rawAnswer) {
      BrainGameService.PrivateSession session = this.findPrivateSession(userId);
      if (session == null) {
         return new BrainGameService.SubmissionResult(BrainGameService.SubmissionStatus.NO_ACTIVE_ROUND, null, null, null);
      } else {
         PointsBotBrainRound round = this.lockRound(session.round().getId());
         PointsBotBrainEntry entry = this.findEntryForUpdate(round.getId(), userId);
         if (round == null || entry == null || !"ACTIVE".equals(round.getStatus()) || !LocalDateTime.now().isBefore(round.getAnswerEndsAt())) {
            return new BrainGameService.SubmissionResult(BrainGameService.SubmissionStatus.CLOSED, round, entry, null);
         } else if (Boolean.TRUE.equals(entry.getCorrect())) {
            return new BrainGameService.SubmissionResult(BrainGameService.SubmissionStatus.ALREADY_CORRECT, round, entry, null);
         } else {
            boolean bulls = "BULLS_AND_COWS".equals(round.getQuestionType());
            int maxSubmissions = bulls ? 8 : 2;
            int current = entry.getSubmissionCount() == null ? 0 : entry.getSubmissionCount();
            if (current >= maxSubmissions) {
               return new BrainGameService.SubmissionResult(BrainGameService.SubmissionStatus.ATTEMPT_LIMIT, round, entry, null);
            } else {
               BrainQuestion question = this.toQuestion(round);
               BrainQuestionGenerator.AnswerCheck check = this.questionGenerator.check(question, rawAnswer);
               entry.setSubmissionCount(current + 1);
               if (check.correct()) {
                  LocalDateTime now = LocalDateTime.now();
                  entry.setCorrect(true);
                  entry.setCorrectAt(now);
                  entry.setEffectiveCorrectAt(!bulls && current > 0 ? now.plusSeconds(5L) : now);
               }

               this.entryMapper.updateById(entry);
               BrainGameService.SubmissionStatus status = check.correct()
                  ? BrainGameService.SubmissionStatus.RECEIVED_CORRECT
                  : BrainGameService.SubmissionStatus.RECEIVED;
               return new BrainGameService.SubmissionResult(status, round, entry, bulls ? check.feedback() : null);
            }
         }
      }
   }

   public List<Long> findQuestionsToHide(LocalDateTime now) {
      return ChainWrappers.lambdaQueryChain(this.roundMapper)
         .select(PointsBotBrainRound::getId)
         .eq(PointsBotBrainRound::getStatus, "ACTIVE")
         .isNotNull(PointsBotBrainRound::getQuestionHidesAt)
         .le(PointsBotBrainRound::getQuestionHidesAt, now)
         .list()
         .stream()
         .map(PointsBotBrainRound::getId)
         .toList();
   }

   public void markQuestionHidden(long roundId) {
      PointsBotBrainRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      if (round != null && round.getQuestionHidesAt() != null) {
         round.setQuestionHidesAt(null);
         this.roundMapper.updateById(round);
      }
   }

   public List<Long> findSettlementDueRoundIds(LocalDateTime now) {
      return ChainWrappers.lambdaQueryChain(this.roundMapper)
         .select(PointsBotBrainRound::getId)
         .eq(PointsBotBrainRound::getStatus, "ACTIVE")
         .le(PointsBotBrainRound::getAnswerEndsAt, now)
         .list()
         .stream()
         .map(PointsBotBrainRound::getId)
         .toList();
   }

   @Transactional
   public BrainGameService.SettlementResult settle(long roundId) {
      PointsBotBrainRound round = this.lockRound(roundId);
      if (round == null) {
         return new BrainGameService.SettlementResult(BrainGameService.SettlementStatus.NOT_FOUND, null, List.of(), 0);
      } else if (!"ACTIVE".equals(round.getStatus())) {
         return new BrainGameService.SettlementResult(BrainGameService.SettlementStatus.ALREADY_SETTLED, round, this.listEntries(roundId), 0);
      } else if (LocalDateTime.now().isBefore(round.getAnswerEndsAt())) {
         return new BrainGameService.SettlementResult(BrainGameService.SettlementStatus.NOT_DUE, round, this.listEntries(roundId), 0);
      } else {
         List<PointsBotBrainEntry> entries = this.listEntries(roundId);
         List<PointsBotBrainEntry> correct = entries.stream()
            .filter(entryx -> Boolean.TRUE.equals(entryx.getCorrect()))
            .sorted(Comparator.comparing(PointsBotBrainEntry::getEffectiveCorrectAt).thenComparing(PointsBotBrainEntry::getId))
            .toList();
         BrainGameConfig config = this.parseConfig(round);
         PointsBotBrainJackpot state = this.lockOrCreateJackpot(round.getChatId());
         int lockedJackpotBefore = state.getJackpotPoints();
         int remainingJackpot = lockedJackpotBefore;
         int pot = round.getTotalPot() == null ? 0 : round.getTotalPot();
         BrainPayoutPolicy.NormalPayout normalPayout = BrainPayoutPolicy.normal(pot, correct.size(), config);
         int jackpotAddition = normalPayout.jackpotAddition();
         if (!correct.isEmpty()) {
            this.reward(correct.get(0), normalPayout.championReward(), "Brain-第一名", roundId);

            for (int index = 1; index < correct.size(); index++) {
               this.reward(correct.get(index), normalPayout.followerReward(), "Brain-答对奖励", roundId);
            }

            if (Boolean.TRUE.equals(round.getPeak()) && lockedJackpotBefore > 0) {
               BrainPayoutPolicy.PeakPayout peakPayout = BrainPayoutPolicy.peak(lockedJackpotBefore, correct.size(), config);
               this.reward(correct.get(0), peakPayout.championReward(), "Brain-巅峰第一名", roundId);

               for (int index = 1; index < correct.size(); index++) {
                  this.reward(correct.get(index), peakPayout.followerReward(), "Brain-巅峰答对奖励", roundId);
               }

               remainingJackpot = peakPayout.remainingJackpot();
            }
         }

         int rank = 1;

         for (PointsBotBrainEntry entry : correct) {
            entry.setRankNo(rank++);
            entry.setSettledAt(LocalDateTime.now());
            this.entryMapper.updateById(entry);
         }

         for (PointsBotBrainEntry entry : entries) {
            if (!Boolean.TRUE.equals(entry.getCorrect())) {
               entry.setRewardPoints(0);
               entry.setSettledAt(LocalDateTime.now());
               this.entryMapper.updateById(entry);
            }
         }

         int jackpotAfter = Math.min(config.getJackpotCap(), Math.max(0, remainingJackpot + jackpotAddition));
         state.setJackpotPoints(jackpotAfter);
         state.setCompletedRounds(state.getCompletedRounds() + 1);
         if (Boolean.TRUE.equals(round.getPeak())) {
            state.setPeakPending(false);
         }

         this.jackpotMapper.updateById(state);
         round.setStatus("SETTLED");
         round.setSettledAt(LocalDateTime.now());
         round.setChampionUserId(correct.isEmpty() ? null : correct.get(0).getUserId());
         round.setJackpotBefore(lockedJackpotBefore);
         round.setJackpotAfter(jackpotAfter);
         this.roundMapper.updateById(round);
         return new BrainGameService.SettlementResult(BrainGameService.SettlementStatus.SETTLED, round, this.listEntries(roundId), jackpotAfter);
      }
   }

   private void reward(PointsBotBrainEntry entry, int amount, String reason, long roundId) {
      if (amount > 0) {
         PointsProfile profile = this.pointsStore.findByUserId(entry.getChatId(), entry.getUserId());
         if (profile == null) {
            throw new IllegalStateException("找不到 Brain 参与用户: " + entry.getUserId());
         } else {
            this.pointsStore.addPoints(profile, amount, reason, "brain:" + roundId + ":reward");
            entry.setRewardPoints((entry.getRewardPoints() == null ? 0 : entry.getRewardPoints()) + amount);
         }
      }
   }

   private void refundEntries(PointsBotBrainRound round, List<PointsBotBrainEntry> entries, String reason) {
      for (PointsBotBrainEntry entry : entries) {
         PointsProfile profile = this.pointsStore.findByUserId(round.getChatId(), entry.getUserId());
         if (profile != null) {
            this.pointsStore.addPoints(profile, entry.getEntryCost(), reason, "brain:" + round.getId() + ":refund");
         }

         entry.setCountedPlay(false);
         entry.setRewardPoints(entry.getEntryCost());
         entry.setSettledAt(LocalDateTime.now());
         this.entryMapper.updateById(entry);
      }
   }

   private PointsBotBrainJackpot lockOrCreateJackpot(long chatId) {
      PointsBotBrainJackpot state = new LambdaQueryChainWrapper<>(this.jackpotMapper)
         .eq(PointsBotBrainJackpot::getChatId, Long.valueOf(chatId))
         .last("FOR UPDATE")
         .one();
      if (state == null) {
         state = new PointsBotBrainJackpot();
         state.setChatId(chatId);
         state.setJackpotPoints(0);
         state.setCompletedRounds(0);
         state.setPeakPending(false);
         this.jackpotMapper.insert(state);
      }

      if (state.getJackpotPoints() == null) {
         state.setJackpotPoints(0);
      }

      if (state.getCompletedRounds() == null) {
         state.setCompletedRounds(0);
      }

      return state;
   }

   private int currentJackpot(long chatId) {
      PointsBotBrainJackpot state = new LambdaQueryChainWrapper<>(this.jackpotMapper)
         .eq(PointsBotBrainJackpot::getChatId, Long.valueOf(chatId))
         .last("limit 1")
         .one();
      return state != null && state.getJackpotPoints() != null ? state.getJackpotPoints() : 0;
   }

   private PointsBotBrainRound lockRound(long roundId) {
      return new LambdaQueryChainWrapper<>(this.roundMapper).eq(PointsBotBrainRound::getId, Long.valueOf(roundId)).last("FOR UPDATE").one();
   }

   private PointsBotBrainEntry findEntry(long roundId, long userId) {
      return new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotBrainEntry::getRoundId, Long.valueOf(roundId))
         .eq(PointsBotBrainEntry::getUserId, Long.valueOf(userId))
         .last("limit 1")
         .one();
   }

   private PointsBotBrainEntry findEntryForUpdate(long roundId, long userId) {
      return new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotBrainEntry::getRoundId, Long.valueOf(roundId))
         .eq(PointsBotBrainEntry::getUserId, Long.valueOf(userId))
         .last("FOR UPDATE")
         .one();
   }

   private List<PointsBotBrainEntry> listEntries(long roundId) {
      return new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotBrainEntry::getRoundId, Long.valueOf(roundId))
         .orderByAsc(PointsBotBrainEntry::getId)
         .list();
   }

   private long countEntries(long roundId) {
      return new LambdaQueryChainWrapper<>(this.entryMapper).eq(PointsBotBrainEntry::getRoundId, Long.valueOf(roundId)).count();
   }

   private long countDailyPlays(long chatId, long userId) {
      LocalDateTime start = LocalDate.now(BUSINESS_ZONE).atStartOfDay();
      return new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotBrainEntry::getChatId, Long.valueOf(chatId))
         .eq(PointsBotBrainEntry::getUserId, Long.valueOf(userId))
         .eq(PointsBotBrainEntry::getCountedPlay, Boolean.valueOf(true))
         .ge(BaseEntity::getCreateDatetime, Timestamp.valueOf(start))
         .count();
   }

   private long countDailyChampions(long chatId, long userId) {
      LocalDateTime start = LocalDate.now(BUSINESS_ZONE).atStartOfDay();
      return new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotBrainEntry::getChatId, Long.valueOf(chatId))
         .eq(PointsBotBrainEntry::getUserId, Long.valueOf(userId))
         .eq(PointsBotBrainEntry::getRankNo, Integer.valueOf(1))
         .ge(PointsBotBrainEntry::getSettledAt, start)
         .count();
   }

   private BrainGameConfig parseConfig(PointsBotBrainRound round) {
      BrainGameConfig config = JSONObject.parseObject(round.getConfigJson(), BrainGameConfig.class);
      return config == null ? new BrainGameConfig() : config;
   }

   private BrainQuestion toQuestion(PointsBotBrainRound round) {
      return new BrainQuestion(round.getQuestionType(), round.getQuestionPrompt(), round.getHiddenPrompt(), round.getAnswerData(), round.getExplanation(), 0);
   }

   @Generated
   public BrainGameService(
      final PointsBotBrainRoundMapper roundMapper,
      final PointsBotBrainEntryMapper entryMapper,
      final PointsBotBrainJackpotMapper jackpotMapper,
      final PointsStore pointsStore,
      final BrainQuestionGenerator questionGenerator
   ) {
      this.roundMapper = roundMapper;
      this.entryMapper = entryMapper;
      this.jackpotMapper = jackpotMapper;
      this.pointsStore = pointsStore;
      this.questionGenerator = questionGenerator;
   }

   public static record JoinResult(BrainGameService.JoinStatus status, PointsBotBrainRound round, PointsBotBrainEntry entry) {
   }

   public static enum JoinStatus {
      JOINED,
      CLOSED,
      WRONG_CHAT,
      EXPIRED,
      ALREADY_JOINED,
      FULL,
      DAILY_LIMIT,
      CHAMPION_LIMIT,
      INSUFFICIENT_POINTS;
   }

   public static record PrivateSession(PointsBotBrainRound round, PointsBotBrainEntry entry) {
   }

   public static record RegistrationResult(BrainGameService.RegistrationStatus status, PointsBotBrainRound round, List<PointsBotBrainEntry> entries) {
   }

   public static enum RegistrationStatus {
      STARTED,
      CANCELLED,
      INVALID,
      NOT_DUE,
      ALREADY_PROCESSED,
      NOT_FOUND;
   }

   public static record RoundView(PointsBotBrainRound round, List<PointsBotBrainEntry> entries, int jackpotPoints) {
   }

   public static record SettlementResult(
      BrainGameService.SettlementStatus status, PointsBotBrainRound round, List<PointsBotBrainEntry> entries, int jackpotAfter
   ) {
   }

   public static enum SettlementStatus {
      SETTLED,
      NOT_DUE,
      ALREADY_SETTLED,
      NOT_FOUND;
   }

   public static record SubmissionResult(BrainGameService.SubmissionStatus status, PointsBotBrainRound round, PointsBotBrainEntry entry, String feedback) {
   }

   public static enum SubmissionStatus {
      RECEIVED,
      RECEIVED_CORRECT,
      ALREADY_CORRECT,
      ATTEMPT_LIMIT,
      CLOSED,
      NO_ACTIVE_ROUND;
   }
}
