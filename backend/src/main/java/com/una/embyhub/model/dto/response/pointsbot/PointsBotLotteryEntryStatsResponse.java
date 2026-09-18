package com.una.embyhub.model.dto.response.pointsbot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Generated;

@Schema(
   description = "积分机器人抽奖参与统计信息"
)
public class PointsBotLotteryEntryStatsResponse {
   @Schema(
      description = "总参与记录数"
   )
   private Long totalEntries;
   @Schema(
      description = "参与用户数（去重）"
   )
   private Long uniqueUsers;
   @Schema(
      description = "最活跃用户Top5（userId -> 参与次数）"
   )
   private List<Map<String, Object>> topActiveUsers;
   @Schema(
      description = "参与率（有人参与的抽奖占比）"
   )
   private String participationRate;

   @Generated
   public Long getTotalEntries() {
      return this.totalEntries;
   }

   @Generated
   public Long getUniqueUsers() {
      return this.uniqueUsers;
   }

   @Generated
   public List<Map<String, Object>> getTopActiveUsers() {
      return this.topActiveUsers;
   }

   @Generated
   public String getParticipationRate() {
      return this.participationRate;
   }

   @Generated
   public void setTotalEntries(final Long totalEntries) {
      this.totalEntries = totalEntries;
   }

   @Generated
   public void setUniqueUsers(final Long uniqueUsers) {
      this.uniqueUsers = uniqueUsers;
   }

   @Generated
   public void setTopActiveUsers(final List<Map<String, Object>> topActiveUsers) {
      this.topActiveUsers = topActiveUsers;
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
      } else if (!(o instanceof PointsBotLotteryEntryStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalEntries = this.getTotalEntries();
         Object other$totalEntries = other.getTotalEntries();
         if (this$totalEntries == null ? other$totalEntries == null : this$totalEntries.equals(other$totalEntries)) {
            Object this$uniqueUsers = this.getUniqueUsers();
            Object other$uniqueUsers = other.getUniqueUsers();
            if (this$uniqueUsers == null ? other$uniqueUsers == null : this$uniqueUsers.equals(other$uniqueUsers)) {
               Object this$topActiveUsers = this.getTopActiveUsers();
               Object other$topActiveUsers = other.getTopActiveUsers();
               if (this$topActiveUsers == null ? other$topActiveUsers == null : this$topActiveUsers.equals(other$topActiveUsers)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotLotteryEntryStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalEntries = this.getTotalEntries();
      result = result * 59 + ($totalEntries == null ? 43 : $totalEntries.hashCode());
      Object $uniqueUsers = this.getUniqueUsers();
      result = result * 59 + ($uniqueUsers == null ? 43 : $uniqueUsers.hashCode());
      Object $topActiveUsers = this.getTopActiveUsers();
      result = result * 59 + ($topActiveUsers == null ? 43 : $topActiveUsers.hashCode());
      Object $participationRate = this.getParticipationRate();
      return result * 59 + ($participationRate == null ? 43 : $participationRate.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLotteryEntryStatsResponse(totalEntries="
         + this.getTotalEntries()
         + ", uniqueUsers="
         + this.getUniqueUsers()
         + ", topActiveUsers="
         + this.getTopActiveUsers()
         + ", participationRate="
         + this.getParticipationRate()
         + ")";
   }
}
