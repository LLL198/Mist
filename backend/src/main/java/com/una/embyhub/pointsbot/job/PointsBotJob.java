package com.una.embyhub.pointsbot.job;

import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.model.entity.PointsBotFoamBag;
import com.una.embyhub.model.entity.PointsBotLottery;
import com.una.embyhub.model.entity.PointsBotLotteryEntry;
import com.una.embyhub.model.entity.PointsBotPrizeConfig;
import com.una.embyhub.pointsbot.PointsBot;
import com.una.embyhub.pointsbot.service.PointsBotFoamBagService;
import com.una.embyhub.pointsbot.service.PointsBotLotteryService;
import com.una.embyhub.service.PointsBotPrizeConfigService;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PointsBotJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PointsBotJob.class);
   private final PointsBotLotteryService lotteryService;
   private final PointsBotPrizeConfigService prizeConfigService;
   private final PointsBot pointsBot;
   private final PointsBotFoamBagService foamBagService;
   private final Random random = new SecureRandom();

   @Scheduled(
      cron = "0 * * * * *"
   )
   @ScheduledTaskMeta(
      name = "定时检查到期的抽奖并自动开奖",
      remark = "定时同步到期的抽奖并自动开奖"
   )
   public void scheduleLotteryDraws() {
      if (this.pointsBot.isEnabled()) {
         for (PointsBotLottery lottery : this.lotteryService.findDueLotteries(LocalDateTime.now())) {
            try {
               List<PointsBotLotteryEntry> winners = this.lotteryService.drawLottery(lottery, this.random);
               if (winners.isEmpty()) {
                  this.pointsBot.sendPublicMessage(lottery.getChatId(), String.format("抽奖已结束：%s\n结果：无人参与。", lottery.getTitle()));
               } else {
                  if (lottery.getPrizeConfigId() != null) {
                     PointsBotPrizeConfig prize = this.prizeConfigService.getById(lottery.getPrizeConfigId());
                     if (prize != null && prize.getRemainingQuantity() != null && prize.getRemainingQuantity() > 0) {
                        int reduce = winners.size();
                        int newQty = Math.max(0, prize.getRemainingQuantity() - reduce);
                        prize.setRemainingQuantity(newQty);
                        this.prizeConfigService.updateById(prize);
                     }
                  }

                  StringBuilder winnerNames = new StringBuilder();

                  for (PointsBotLotteryEntry winner : winners) {
                     if (winnerNames.length() > 0) {
                        winnerNames.append(", ");
                     }

                     String name = winner.getUsername() != null && !winner.getUsername().isBlank() ? "@" + winner.getUsername() : winner.getDisplayName();
                     winnerNames.append(name);
                     this.pointsBot.sendPublicMessage(winner.getUserId(), String.format("\ud83c\udf89 恭喜中奖！\n抽奖：%s\n请联系管理员领取奖励。", lottery.getTitle()));
                  }

                  this.pointsBot
                     .sendPublicMessage(lottery.getChatId(), String.format("\ud83c\udf89 抽奖结果：%s\n中奖者：%s", lottery.getTitle(), winnerNames.toString()));
               }
            } catch (Exception var9) {
               log.error("自动开奖失败: lotteryId={}", lottery.getId(), var9);
            }
         }
      }
   }

   @Scheduled(
      cron = "30 * * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "积分红包任务恢复",
      remark = "恢复红包到期任务并补结算过期红包",
      locked = true
   )
   public void scheduleRedPacketRecovery() {
      this.pointsBot.recoverRedPacketTasks();
   }

   @Scheduled(
      cron = "15 * * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "雾袋逾期扫描",
      remark = "归零逾期用户积分并开始限制期"
   )
   public void scheduleFoamBagOverdueScan() {
      for (Long recordId : this.foamBagService.findDueIds(200)) {
         try {
            PointsBotFoamBag penalized = this.foamBagService.penalizeDue(recordId);
            if (penalized != null && this.pointsBot.isEnabled()) {
               this.pointsBot
                  .sendPublicMessage(penalized.getUserId(), "⚠️ 雾袋已逾期\n\n积分已归零，未来 " + penalized.getPenaltyDays() + " 天内无法签到、使用雾袋或接收他人转赠积分。\n限制结束后会自动恢复。");
            }
         } catch (Exception var4) {
            log.error("雾袋逾期处理失败: recordId={}", recordId, var4);
         }
      }
   }

   @Generated
   public PointsBotJob(
      final PointsBotLotteryService lotteryService,
      final PointsBotPrizeConfigService prizeConfigService,
      final PointsBot pointsBot,
      final PointsBotFoamBagService foamBagService
   ) {
      this.lotteryService = lotteryService;
      this.prizeConfigService = prizeConfigService;
      this.pointsBot = pointsBot;
      this.foamBagService = foamBagService;
   }
}
