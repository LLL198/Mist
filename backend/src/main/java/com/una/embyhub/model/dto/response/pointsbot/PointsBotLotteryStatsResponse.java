package com.una.embyhub.model.dto.response.pointsbot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

@Schema(
   description = "积分机器人抽奖统计信息"
)
public class PointsBotLotteryStatsResponse {
   @Schema(
      description = "总抽奖数"
   )
   private Long totalLotteries;
   @Schema(
      description = "进行中抽奖数（status = 'OPEN'）"
   )
   private Long openLotteries;
   @Schema(
      description = "已完成抽奖数（status = 'CLOSED'）"
   )
   private Long closedLotteries;
   @Schema(
      description = "总参与人次（从entry表统计）"
   )
   private Long totalParticipants;
   @Schema(
      description = "参与率（有人参与的抽奖占比）"
   )
   private String participationRate;

   @Generated
   public Long getTotalLotteries() {
      return this.totalLotteries;
   }

   @Generated
   public Long getOpenLotteries() {
      return this.openLotteries;
   }

   @Generated
   public Long getClosedLotteries() {
      return this.closedLotteries;
   }

   @Generated
   public Long getTotalParticipants() {
      return this.totalParticipants;
   }

   @Generated
   public String getParticipationRate() {
      return this.participationRate;
   }

   @Generated
   public void setTotalLotteries(final Long totalLotteries) {
      this.totalLotteries = totalLotteries;
   }

   @Generated
   public void setOpenLotteries(final Long openLotteries) {
      this.openLotteries = openLotteries;
   }

   @Generated
   public void setClosedLotteries(final Long closedLotteries) {
      this.closedLotteries = closedLotteries;
   }

   @Generated
   public void setTotalParticipants(final Long totalParticipants) {
      this.totalParticipants = totalParticipants;
   }

   @Generated
   public void setParticipationRate(final String participationRate) {
      this.participationRate = participationRate;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLotteryStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalLotteries = this.getTotalLotteries();
         Object other$totalLotteries = other.getTotalLotteries();
         if (this$totalLotteries == null ? other$totalLotteries == null : this$totalLotteries.equals(other$totalLotteries)) {
            Object this$openLotteries = this.getOpenLotteries();
            Object other$openLotteries = other.getOpenLotteries();
            if (this$openLotteries == null ? other$openLotteries == null : this$openLotteries.equals(other$openLotteries)) {
               Object this$closedLotteries = this.getClosedLotteries();
               Object other$closedLotteries = other.getClosedLotteries();
               if (this$closedLotteries == null ? other$closedLotteries == null : this$closedLotteries.equals(other$closedLotteries)) {
                  Object this$totalParticipants = this.getTotalParticipants();
                  Object other$totalParticipants = other.getTotalParticipants();
                  if (this$totalParticipants == null ? other$totalParticipants == null : this$totalParticipants.equals(other$totalParticipants)) {
                     Object this$participationRate = this.getParticipationRate();
                     Object other$participationRate = other.getParticipationRate();
                     return this$participationRate == null ? other$participationRate == null : this$participationRate.equals(other$participationRate);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotLotteryStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalLotteries = this.getTotalLotteries();
      result = result * 59 + ($totalLotteries == null ? 43 : $totalLotteries.hashCode());
      Object $openLotteries = this.getOpenLotteries();
      result = result * 59 + ($openLotteries == null ? 43 : $openLotteries.hashCode());
      Object $closedLotteries = this.getClosedLotteries();
      result = result * 59 + ($closedLotteries == null ? 43 : $closedLotteries.hashCode());
      Object $totalParticipants = this.getTotalParticipants();
      result = result * 59 + ($totalParticipants == null ? 43 : $totalParticipants.hashCode());
      Object $participationRate = this.getParticipationRate();
      return result * 59 + ($participationRate == null ? 43 : $participationRate.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLotteryStatsResponse(totalLotteries="
         + this.getTotalLotteries()
         + ", openLotteries="
         + this.getOpenLotteries()
         + ", closedLotteries="
         + this.getClosedLotteries()
         + ", totalParticipants="
         + this.getTotalParticipants()
         + ", participationRate="
         + this.getParticipationRate()
         + ")";
   }
}
