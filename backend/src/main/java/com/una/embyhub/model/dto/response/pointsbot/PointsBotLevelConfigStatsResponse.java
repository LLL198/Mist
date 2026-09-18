package com.una.embyhub.model.dto.response.pointsbot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.Generated;

@Schema(
   description = "积分等级配置统计信息"
)
public class PointsBotLevelConfigStatsResponse {
   @Schema(
      description = "总等级数"
   )
   private Long totalLevels;
   @Schema(
      description = "启用等级数（enabled = 1）"
   )
   private Long enabledLevels;
   @Schema(
      description = "各等级用户分布（levelId -> 用户数）"
   )
   private Map<Long, Long> userDistribution;

   @Generated
   public Long getTotalLevels() {
      return this.totalLevels;
   }

   @Generated
   public Long getEnabledLevels() {
      return this.enabledLevels;
   }

   @Generated
   public Map<Long, Long> getUserDistribution() {
      return this.userDistribution;
   }

   @Generated
   public void setTotalLevels(final Long totalLevels) {
      this.totalLevels = totalLevels;
   }

   @Generated
   public void setEnabledLevels(final Long enabledLevels) {
      this.enabledLevels = enabledLevels;
   }

   @Generated
   public void setUserDistribution(final Map<Long, Long> userDistribution) {
      this.userDistribution = userDistribution;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLevelConfigStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalLevels = this.getTotalLevels();
         Object other$totalLevels = other.getTotalLevels();
         if (this$totalLevels == null ? other$totalLevels == null : this$totalLevels.equals(other$totalLevels)) {
            Object this$enabledLevels = this.getEnabledLevels();
            Object other$enabledLevels = other.getEnabledLevels();
            if (this$enabledLevels == null ? other$enabledLevels == null : this$enabledLevels.equals(other$enabledLevels)) {
               Object this$userDistribution = this.getUserDistribution();
               Object other$userDistribution = other.getUserDistribution();
               return this$userDistribution == null ? other$userDistribution == null : this$userDistribution.equals(other$userDistribution);
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
      return other instanceof PointsBotLevelConfigStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalLevels = this.getTotalLevels();
      result = result * 59 + ($totalLevels == null ? 43 : $totalLevels.hashCode());
      Object $enabledLevels = this.getEnabledLevels();
      result = result * 59 + ($enabledLevels == null ? 43 : $enabledLevels.hashCode());
      Object $userDistribution = this.getUserDistribution();
      return result * 59 + ($userDistribution == null ? 43 : $userDistribution.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLevelConfigStatsResponse(totalLevels="
         + this.getTotalLevels()
         + ", enabledLevels="
         + this.getEnabledLevels()
         + ", userDistribution="
         + this.getUserDistribution()
         + ")";
   }
}
