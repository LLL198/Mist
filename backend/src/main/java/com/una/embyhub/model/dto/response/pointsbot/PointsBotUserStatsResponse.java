package com.una.embyhub.model.dto.response.pointsbot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

@Schema(
   description = "积分机器人用户统计信息"
)
public class PointsBotUserStatsResponse {
   @Schema(
      description = "总用户数"
   )
   private Long totalUsers;
   @Schema(
      description = "总积分数"
   )
   private Long totalPoints;
   @Schema(
      description = "平均积分"
   )
   private Double avgPoints;
   @Schema(
      description = "最高积分"
   )
   private Long maxPoints;
   @Schema(
      description = "最低积分"
   )
   private Long minPoints;
   @Schema(
      description = "活跃用户数（近7天有活动）"
   )
   private Long activeUsers;
   @Schema(
      description = "连续签到用户数（streak > 0）"
   )
   private Long streakUsers;

   @Generated
   public Long getTotalUsers() {
      return this.totalUsers;
   }

   @Generated
   public Long getTotalPoints() {
      return this.totalPoints;
   }

   @Generated
   public Double getAvgPoints() {
      return this.avgPoints;
   }

   @Generated
   public Long getMaxPoints() {
      return this.maxPoints;
   }

   @Generated
   public Long getMinPoints() {
      return this.minPoints;
   }

   @Generated
   public Long getActiveUsers() {
      return this.activeUsers;
   }

   @Generated
   public Long getStreakUsers() {
      return this.streakUsers;
   }

   @Generated
   public void setTotalUsers(final Long totalUsers) {
      this.totalUsers = totalUsers;
   }

   @Generated
   public void setTotalPoints(final Long totalPoints) {
      this.totalPoints = totalPoints;
   }

   @Generated
   public void setAvgPoints(final Double avgPoints) {
      this.avgPoints = avgPoints;
   }

   @Generated
   public void setMaxPoints(final Long maxPoints) {
      this.maxPoints = maxPoints;
   }

   @Generated
   public void setMinPoints(final Long minPoints) {
      this.minPoints = minPoints;
   }

   @Generated
   public void setActiveUsers(final Long activeUsers) {
      this.activeUsers = activeUsers;
   }

   @Generated
   public void setStreakUsers(final Long streakUsers) {
      this.streakUsers = streakUsers;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotUserStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalUsers = this.getTotalUsers();
         Object other$totalUsers = other.getTotalUsers();
         if (this$totalUsers == null ? other$totalUsers == null : this$totalUsers.equals(other$totalUsers)) {
            Object this$totalPoints = this.getTotalPoints();
            Object other$totalPoints = other.getTotalPoints();
            if (this$totalPoints == null ? other$totalPoints == null : this$totalPoints.equals(other$totalPoints)) {
               Object this$avgPoints = this.getAvgPoints();
               Object other$avgPoints = other.getAvgPoints();
               if (this$avgPoints == null ? other$avgPoints == null : this$avgPoints.equals(other$avgPoints)) {
                  Object this$maxPoints = this.getMaxPoints();
                  Object other$maxPoints = other.getMaxPoints();
                  if (this$maxPoints == null ? other$maxPoints == null : this$maxPoints.equals(other$maxPoints)) {
                     Object this$minPoints = this.getMinPoints();
                     Object other$minPoints = other.getMinPoints();
                     if (this$minPoints == null ? other$minPoints == null : this$minPoints.equals(other$minPoints)) {
                        Object this$activeUsers = this.getActiveUsers();
                        Object other$activeUsers = other.getActiveUsers();
                        if (this$activeUsers == null ? other$activeUsers == null : this$activeUsers.equals(other$activeUsers)) {
                           Object this$streakUsers = this.getStreakUsers();
                           Object other$streakUsers = other.getStreakUsers();
                           return this$streakUsers == null ? other$streakUsers == null : this$streakUsers.equals(other$streakUsers);
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
      return other instanceof PointsBotUserStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalUsers = this.getTotalUsers();
      result = result * 59 + ($totalUsers == null ? 43 : $totalUsers.hashCode());
      Object $totalPoints = this.getTotalPoints();
      result = result * 59 + ($totalPoints == null ? 43 : $totalPoints.hashCode());
      Object $avgPoints = this.getAvgPoints();
      result = result * 59 + ($avgPoints == null ? 43 : $avgPoints.hashCode());
      Object $maxPoints = this.getMaxPoints();
      result = result * 59 + ($maxPoints == null ? 43 : $maxPoints.hashCode());
      Object $minPoints = this.getMinPoints();
      result = result * 59 + ($minPoints == null ? 43 : $minPoints.hashCode());
      Object $activeUsers = this.getActiveUsers();
      result = result * 59 + ($activeUsers == null ? 43 : $activeUsers.hashCode());
      Object $streakUsers = this.getStreakUsers();
      return result * 59 + ($streakUsers == null ? 43 : $streakUsers.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotUserStatsResponse(totalUsers="
         + this.getTotalUsers()
         + ", totalPoints="
         + this.getTotalPoints()
         + ", avgPoints="
         + this.getAvgPoints()
         + ", maxPoints="
         + this.getMaxPoints()
         + ", minPoints="
         + this.getMinPoints()
         + ", activeUsers="
         + this.getActiveUsers()
         + ", streakUsers="
         + this.getStreakUsers()
         + ")";
   }
}
