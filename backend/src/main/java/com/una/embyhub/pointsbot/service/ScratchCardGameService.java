package com.una.embyhub.pointsbot.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.una.embyhub.mapper.PointsBotScratchEntryMapper;
import com.una.embyhub.mapper.PointsBotScratchRoundMapper;
import com.una.embyhub.model.entity.PointsBotScratchEntry;
import com.una.embyhub.model.entity.PointsBotScratchRound;
import com.una.embyhub.pointsbot.model.PointsProfile;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScratchCardGameService {
   public static final int CELL_COUNT = 9;
   public static final int ENTRY_COST = 50;
   public static final int DEFAULT_DRAW_DELAY_SECONDS = 60;
   private static final int MAX_JACKPOT_PAGE_SIZE = 50;
   public static final String STATUS_OPEN = "OPEN";
   public static final String STATUS_CLOSED = "CLOSED";
   public static final String STATUS_CANCELLED = "CANCELLED";
   private final PointsBotScratchRoundMapper roundMapper;
   private final PointsBotScratchEntryMapper entryMapper;
   private final PointsStore pointsStore;
   private final Random random = new SecureRandom();

   public PointsBotScratchRound findActiveRound(long chatId) {
      return new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotScratchRound::getChatId, Long.valueOf(chatId))
         .eq(PointsBotScratchRound::getStatus, "OPEN")
         .orderByDesc(PointsBotScratchRound::getId)
         .last("limit 1")
         .one();
   }

   public boolean hasOpenRounds() {
      return ChainWrappers.lambdaQueryChain(this.roundMapper).eq(PointsBotScratchRound::getStatus, "OPEN").count() > 0L;
   }

   @Transactional
   public synchronized PointsBotScratchRound createRound(long chatId, LocalDateTime drawAt) {
      PointsBotScratchRound active = this.findActiveRound(chatId);
      if (active != null) {
         return active;
      } else {
         PointsBotScratchRound round = new PointsBotScratchRound();
         round.setChatId(chatId);
         round.setStatus("OPEN");
         round.setEntryCost(50);
         round.setDrawAt(drawAt);
         this.roundMapper.insert(round);
         return round;
      }
   }

   public ScratchCardGameService.RoundView getRoundView(long roundId) {
      PointsBotScratchRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      return round == null ? null : new ScratchCardGameService.RoundView(round, this.listEntries(roundId));
   }

   public List<PointsBotScratchEntry> listEntries(long roundId) {
      return new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotScratchEntry::getRoundId, Long.valueOf(roundId))
         .orderByAsc(PointsBotScratchEntry::getId)
         .list();
   }

   public ScratchCardGameService.JackpotPage listJackpotRecords(long chatId, LocalDateTime fromInclusive, LocalDateTime toInclusive, int offset, int pageSize) {
      int safeOffset = Math.max(0, offset);
      int safePageSize = Math.max(1, Math.min(pageSize, 50));
      LambdaQueryChainWrapper<PointsBotScratchEntry> query = new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotScratchEntry::getChatId, Long.valueOf(chatId))
         .eq(PointsBotScratchEntry::getJackpot, Boolean.valueOf(true))
         .isNotNull(PointsBotScratchEntry::getSettledAt);
      if (fromInclusive != null) {
         query.ge(PointsBotScratchEntry::getSettledAt, fromInclusive);
      }

      if (toInclusive != null) {
         query.le(PointsBotScratchEntry::getSettledAt, toInclusive);
      }

      List<PointsBotScratchEntry> records = query.orderByDesc(PointsBotScratchEntry::getSettledAt)
         .orderByDesc(PointsBotScratchEntry::getId)
         .last("limit " + safeOffset + "," + (safePageSize + 1))
         .list();
      boolean hasNext = records.size() > safePageSize;
      if (hasNext) {
         records = List.copyOf(records.subList(0, safePageSize));
      }

      return new ScratchCardGameService.JackpotPage(records, hasNext);
   }

   public void updateMessageId(long roundId, long messageId) {
      PointsBotScratchRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      if (round != null) {
         round.setMessageId(messageId);
         this.roundMapper.updateById(round);
      }
   }

   public void cancelRound(long roundId) {
      PointsBotScratchRound round = this.roundMapper.selectById(Long.valueOf(roundId));
      if (round != null && "OPEN".equals(round.getStatus())) {
         round.setStatus("CANCELLED");
         round.setDrawnAt(LocalDateTime.now());
         this.roundMapper.updateById(round);
      }
   }

   @Transactional
   public ScratchCardGameService.JoinResult join(long roundId, long chatId, int cellNumber, long userId, String username, String displayName) {
      if (cellNumber >= 1 && cellNumber <= 9) {
         PointsBotScratchRound round = this.lockRound(roundId);
         if (round != null && "OPEN".equals(round.getStatus())) {
            if (round.getChatId() == null || round.getChatId() != chatId) {
               return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.WRONG_CHAT, round, false);
            } else if (!LocalDateTime.now().isBefore(round.getDrawAt())) {
               return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.EXPIRED, round, false);
            } else {
               PointsBotScratchEntry ownEntry = new LambdaQueryChainWrapper<>(this.entryMapper)
                  .eq(PointsBotScratchEntry::getRoundId, Long.valueOf(roundId))
                  .eq(PointsBotScratchEntry::getUserId, Long.valueOf(userId))
                  .one();
               if (ownEntry != null) {
                  return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.ALREADY_JOINED, round, false);
               } else {
                  PointsBotScratchEntry occupied = new LambdaQueryChainWrapper<>(this.entryMapper)
                     .eq(PointsBotScratchEntry::getRoundId, Long.valueOf(roundId))
                     .eq(PointsBotScratchEntry::getCellNumber, Integer.valueOf(cellNumber))
                     .one();
                  if (occupied != null) {
                     return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.CELL_OCCUPIED, round, false);
                  } else {
                     PointsProfile profile = this.pointsStore.getOrCreate(round.getChatId(), userId, username, displayName);
                     if (profile.getPoints() < 50L) {
                        return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.INSUFFICIENT_POINTS, round, false);
                     } else {
                        PointsBotScratchEntry entry = new PointsBotScratchEntry();
                        entry.setRoundId(roundId);
                        entry.setChatId(round.getChatId());
                        entry.setCellNumber(cellNumber);
                        entry.setUserId(userId);
                        entry.setUsername(username);
                        entry.setDisplayName(displayName);
                        this.entryMapper.insert(entry);
                        int applied = this.pointsStore.addPoints(profile, -50, "雾中刮刮乐-参与", "scratch:" + roundId + ":" + cellNumber);
                        if (applied != -50) {
                           throw new IllegalStateException("雾中刮刮乐参与积分扣除失败");
                        } else {
                           boolean full = this.countEntries(roundId) >= 9L;
                           if (full) {
                              round.setDrawAt(LocalDateTime.now());
                              this.roundMapper.updateById(round);
                           }

                           return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.JOINED, round, full);
                        }
                     }
                  }
               }
            }
         } else {
            return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.CLOSED, round, false);
         }
      } else {
         return ScratchCardGameService.JoinResult.of(ScratchCardGameService.JoinStatus.INVALID_CELL, null, false);
      }
   }

   public List<Long> findDueRoundIds(LocalDateTime now) {
      return ChainWrappers.lambdaQueryChain(this.roundMapper)
         .eq(PointsBotScratchRound::getStatus, "OPEN")
         .le(PointsBotScratchRound::getDrawAt, now)
         .list()
         .stream()
         .map(PointsBotScratchRound::getId)
         .toList();
   }

   @Transactional
   public ScratchCardGameService.SettlementResult settle(long roundId) {
      return this.settle(roundId, this.random);
   }

   ScratchCardGameService.SettlementResult settle(long roundId, Random drawRandom) {
      PointsBotScratchRound round = this.lockRound(roundId);
      if (round == null) {
         return new ScratchCardGameService.SettlementResult(ScratchCardGameService.SettlementStatus.NOT_FOUND, null, List.of());
      } else if (!"OPEN".equals(round.getStatus())) {
         return new ScratchCardGameService.SettlementResult(ScratchCardGameService.SettlementStatus.ALREADY_SETTLED, round, this.listEntries(roundId));
      } else if (LocalDateTime.now().isBefore(round.getDrawAt()) && this.countEntries(roundId) < 9L) {
         return new ScratchCardGameService.SettlementResult(ScratchCardGameService.SettlementStatus.NOT_DUE, round, this.listEntries(roundId));
      } else {
         List<PointsBotScratchEntry> entries = this.listEntries(roundId);
         LocalDateTime now = LocalDateTime.now();
         if (entries.isEmpty()) {
            round.setStatus("CANCELLED");
            round.setDrawnAt(now);
            this.roundMapper.updateById(round);
            return new ScratchCardGameService.SettlementResult(ScratchCardGameService.SettlementStatus.CANCELLED, round, entries);
         } else {
            List<Integer> occupiedCells = entries.stream().map(PointsBotScratchEntry::getCellNumber).toList();
            ScratchCardPityPolicy.PityState pityState = this.nextPityState(round.getChatId(), entries.size(), drawRandom);
            ScratchCardPayoutPolicy.DrawResult draw = ScratchCardPayoutPolicy.draw(
               occupiedCells, drawRandom, pityState.guaranteed(), pityState.jackpotEligible()
            );

            for (PointsBotScratchEntry entry : entries) {
               int reward = draw.rewards().get(entry.getCellNumber());
               PointsProfile profile = this.pointsStore.findByUserId(round.getChatId(), entry.getUserId());
               if (profile == null) {
                  throw new IllegalStateException("找不到刮刮乐参与用户: " + entry.getUserId());
               }

               this.pointsStore
                  .addPoints(
                     profile,
                     reward,
                     entry.getCellNumber().equals(draw.jackpotCell()) ? "雾中刮刮乐-大奖" : "雾中刮刮乐-奖励",
                     "scratch:" + roundId + ":" + entry.getCellNumber()
                  );
               entry.setRewardPoints(reward);
               entry.setJackpot(entry.getCellNumber().equals(draw.jackpotCell()));
               entry.setSettledAt(now);
               this.entryMapper.updateById(entry);
            }

            round.setStatus("CLOSED");
            round.setDrawnAt(now);
            round.setJackpotCell(draw.jackpotCell());
            round.setPityTarget(pityState.target());
            round.setPityProgress(pityState.progress());
            this.roundMapper.updateById(round);
            return new ScratchCardGameService.SettlementResult(ScratchCardGameService.SettlementStatus.SETTLED, round, entries);
         }
      }
   }

   private ScratchCardPityPolicy.PityState nextPityState(long chatId, int participantCount, Random drawRandom) {
      PointsBotScratchRound latestSettled = new LambdaQueryChainWrapper<>(this.roundMapper)
         .eq(PointsBotScratchRound::getChatId, Long.valueOf(chatId))
         .eq(PointsBotScratchRound::getStatus, "CLOSED")
         .orderByDesc(PointsBotScratchRound::getId)
         .last("limit 1")
         .one();
      return ScratchCardPityPolicy.next(
         latestSettled == null ? null : latestSettled.getPityTarget(),
         latestSettled == null ? null : latestSettled.getPityProgress(),
         latestSettled != null && latestSettled.getJackpotCell() != null,
         participantCount,
         drawRandom
      );
   }

   private PointsBotScratchRound lockRound(long roundId) {
      return new LambdaQueryChainWrapper<>(this.roundMapper).eq(PointsBotScratchRound::getId, Long.valueOf(roundId)).last("FOR UPDATE").one();
   }

   private long countEntries(long roundId) {
      return new LambdaQueryChainWrapper<>(this.entryMapper).eq(PointsBotScratchEntry::getRoundId, Long.valueOf(roundId)).count();
   }

   @Generated
   public ScratchCardGameService(final PointsBotScratchRoundMapper roundMapper, final PointsBotScratchEntryMapper entryMapper, final PointsStore pointsStore) {
      this.roundMapper = roundMapper;
      this.entryMapper = entryMapper;
      this.pointsStore = pointsStore;
   }

   public static record JackpotPage(List<PointsBotScratchEntry> records, boolean hasNext) {
   }

   public static record JoinResult(ScratchCardGameService.JoinStatus status, PointsBotScratchRound round, boolean full) {
      private static ScratchCardGameService.JoinResult of(ScratchCardGameService.JoinStatus status, PointsBotScratchRound round, boolean full) {
         return new ScratchCardGameService.JoinResult(status, round, full);
      }
   }

   public static enum JoinStatus {
      JOINED,
      INVALID_CELL,
      WRONG_CHAT,
      CLOSED,
      EXPIRED,
      ALREADY_JOINED,
      CELL_OCCUPIED,
      INSUFFICIENT_POINTS;
   }

   public static record RoundView(PointsBotScratchRound round, List<PointsBotScratchEntry> entries) {
   }

   public static record SettlementResult(ScratchCardGameService.SettlementStatus status, PointsBotScratchRound round, List<PointsBotScratchEntry> entries) {
   }

   public static enum SettlementStatus {
      SETTLED,
      CANCELLED,
      NOT_DUE,
      ALREADY_SETTLED,
      NOT_FOUND;
   }
}
