package com.una.embyhub.pointsbot.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.una.embyhub.mapper.PointsBotLotteryEntryMapper;
import com.una.embyhub.mapper.PointsBotLotteryMapper;
import com.una.embyhub.model.entity.PointsBotLottery;
import com.una.embyhub.model.entity.PointsBotLotteryEntry;
import com.una.embyhub.pointsbot.model.PointsProfile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.stereotype.Service;

@Service
public class PointsBotLotteryService {
   public static final String STATUS_OPEN = "OPEN";
   public static final String STATUS_CLOSED = "CLOSED";
   private final PointsBotLotteryMapper lotteryMapper;
   private final PointsBotLotteryEntryMapper entryMapper;
   private final ObjectMapper objectMapper;

   public PointsBotLottery findActiveLottery(long chatId) {
      return new LambdaQueryChainWrapper<>(this.lotteryMapper)
         .eq(PointsBotLottery::getChatId, Long.valueOf(chatId))
         .eq(PointsBotLottery::getStatus, "OPEN")
         .orderByDesc(PointsBotLottery::getDrawAt)
         .last("limit 1")
         .one();
   }

   public PointsBotLottery createLottery(
      long chatId, String title, Long prizeConfigId, PointsBotLotteryService.PointsBotLotteryConfig config, PointsProfile creator, LocalDateTime drawAt
   ) {
      PointsBotLottery lottery = new PointsBotLottery();
      lottery.setChatId(chatId);
      lottery.setTitle(title);
      lottery.setPrizeConfigId(prizeConfigId);
      lottery.setStatus("OPEN");
      lottery.setDrawAt(drawAt);
      if (creator != null) {
         lottery.setCreatedByUserId(creator.getUserId());
         lottery.setCreatedByUsername(creator.getUsername());
         lottery.setCreatedByDisplayName(creator.getDisplayName());
      }

      if (config != null && config.getWinnerCount() != null && config.getWinnerCount() > 1) {
         lottery.setWinnerCount(config.getWinnerCount());
      } else {
         lottery.setWinnerCount(1);
      }

      this.lotteryMapper.insert(lottery);
      return lottery;
   }

   public boolean addEntry(long lotteryId, long chatId, PointsProfile profile, String note) {
      PointsBotLotteryEntry existing = new LambdaQueryChainWrapper<>(this.entryMapper)
         .eq(PointsBotLotteryEntry::getLotteryId, Long.valueOf(lotteryId))
         .eq(PointsBotLotteryEntry::getUserId, Long.valueOf(profile.getUserId()))
         .one();
      if (existing != null) {
         return false;
      } else {
         PointsBotLotteryEntry entry = new PointsBotLotteryEntry();
         entry.setLotteryId(lotteryId);
         entry.setChatId(chatId);
         entry.setUserId(profile.getUserId());
         entry.setUsername(profile.getUsername());
         entry.setDisplayName(profile.getDisplayName());
         entry.setEntryNote(note);
         this.entryMapper.insert(entry);
         return true;
      }
   }

   public List<PointsBotLotteryEntry> listEntries(long lotteryId) {
      return new LambdaQueryChainWrapper<>(this.entryMapper).eq(PointsBotLotteryEntry::getLotteryId, Long.valueOf(lotteryId)).list();
   }

   public List<PointsBotLottery> findDueLotteries(LocalDateTime now) {
      return ChainWrappers.lambdaQueryChain(this.lotteryMapper).eq(PointsBotLottery::getStatus, "OPEN").le(PointsBotLottery::getDrawAt, now).list();
   }

   public List<PointsBotLotteryEntry> drawLottery(PointsBotLottery lottery, Random random) {
      List<PointsBotLotteryEntry> entries = this.listEntries(lottery.getId());
      LocalDateTime now = LocalDateTime.now();
      if (entries.isEmpty()) {
         lottery.setStatus("CLOSED");
         lottery.setDrawnAt(now);
         this.lotteryMapper.updateById(lottery);
         return List.of();
      } else {
         int count = lottery.getWinnerCount() != null && lottery.getWinnerCount() > 0 ? lottery.getWinnerCount() : 1;
         if (count > entries.size()) {
            count = entries.size();
         }

         List<PointsBotLotteryEntry> winners = new ArrayList<>();
         List<PointsBotLotteryEntry> pool = new ArrayList<>(entries);

         for (int i = 0; i < count; i++) {
            int index = random.nextInt(pool.size());
            PointsBotLotteryEntry winner = pool.remove(index);
            winner.setIsWinner(true);
            this.entryMapper.updateById(winner);
            winners.add(winner);
         }

         lottery.setStatus("CLOSED");
         lottery.setDrawnAt(now);
         if (!winners.isEmpty()) {
            PointsBotLotteryEntry firstWinner = winners.get(0);
            lottery.setWinnerUserId(firstWinner.getUserId());
            lottery.setWinnerUsername(firstWinner.getUsername());
            lottery.setWinnerDisplayName(firstWinner.getDisplayName());
            lottery.setWinnerEntryId(firstWinner.getId());
            lottery.setWinnerEntryId(firstWinner.getId());
         }

         try {
            List<Map<String, Object>> winnerInfos = winners.stream().map(w -> {
               Map<String, Object> info = new HashMap<>();
               info.put("userId", w.getUserId());
               info.put("username", w.getUsername());
               info.put("displayName", w.getDisplayName());
               info.put("entryId", w.getId());
               return info;
            }).collect(Collectors.toList());
            lottery.setWinnersJson(this.objectMapper.writeValueAsString(winnerInfos));
         } catch (Exception var11) {
         }

         this.lotteryMapper.updateById(lottery);
         return winners;
      }
   }

   public void updateLottery(PointsBotLottery lottery) {
      this.lotteryMapper.updateById(lottery);
   }

   @Generated
   public PointsBotLotteryService(final PointsBotLotteryMapper lotteryMapper, final PointsBotLotteryEntryMapper entryMapper, final ObjectMapper objectMapper) {
      this.lotteryMapper = lotteryMapper;
      this.entryMapper = entryMapper;
      this.objectMapper = objectMapper;
   }

   public static class PointsBotLotteryConfig {
      private Integer winnerCount;

      public PointsBotLotteryConfig(Integer winnerCount) {
         this.winnerCount = winnerCount;
      }

      public Integer getWinnerCount() {
         return this.winnerCount;
      }

      public void setWinnerCount(Integer winnerCount) {
         this.winnerCount = winnerCount;
      }
   }
}
