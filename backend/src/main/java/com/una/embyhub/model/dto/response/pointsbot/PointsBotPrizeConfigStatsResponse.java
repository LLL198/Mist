package com.una.embyhub.model.dto.response.pointsbot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

@Schema(
   description = "积分奖品配置统计信息"
)
public class PointsBotPrizeConfigStatsResponse {
   @Schema(
      description = "总奖品数"
   )
   private Long totalPrizes;
   @Schema(
      description = "启用奖品数（enabled = 1）"
   )
   private Long enabledPrizes;
   @Schema(
      description = "剩余库存总数"
   )
   private Long totalRemainingQuantity;
   @Schema(
      description = "已售罄奖品数（remainingQuantity <= 0）"
   )
   private Long soldOutPrizes;
   @Schema(
      description = "总奖品价值（所有奖品所需积分总和）"
   )
   private Long totalPrizeValue;

   @Generated
   public Long getTotalPrizes() {
      return this.totalPrizes;
   }

   @Generated
   public Long getEnabledPrizes() {
      return this.enabledPrizes;
   }

   @Generated
   public Long getTotalRemainingQuantity() {
      return this.totalRemainingQuantity;
   }

   @Generated
   public Long getSoldOutPrizes() {
      return this.soldOutPrizes;
   }

   @Generated
   public Long getTotalPrizeValue() {
      return this.totalPrizeValue;
   }

   @Generated
   public void setTotalPrizes(final Long totalPrizes) {
      this.totalPrizes = totalPrizes;
   }

   @Generated
   public void setEnabledPrizes(final Long enabledPrizes) {
      this.enabledPrizes = enabledPrizes;
   }

   @Generated
   public void setTotalRemainingQuantity(final Long totalRemainingQuantity) {
      this.totalRemainingQuantity = totalRemainingQuantity;
   }

   @Generated
   public void setSoldOutPrizes(final Long soldOutPrizes) {
      this.soldOutPrizes = soldOutPrizes;
   }

   @Generated
   public void setTotalPrizeValue(final Long totalPrizeValue) {
      this.totalPrizeValue = totalPrizeValue;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPrizeConfigStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalPrizes = this.getTotalPrizes();
         Object other$totalPrizes = other.getTotalPrizes();
         if (this$totalPrizes == null ? other$totalPrizes == null : this$totalPrizes.equals(other$totalPrizes)) {
            Object this$enabledPrizes = this.getEnabledPrizes();
            Object other$enabledPrizes = other.getEnabledPrizes();
            if (this$enabledPrizes == null ? other$enabledPrizes == null : this$enabledPrizes.equals(other$enabledPrizes)) {
               Object this$totalRemainingQuantity = this.getTotalRemainingQuantity();
               Object other$totalRemainingQuantity = other.getTotalRemainingQuantity();
               if (this$totalRemainingQuantity == null
                  ? other$totalRemainingQuantity == null
                  : this$totalRemainingQuantity.equals(other$totalRemainingQuantity)) {
                  Object this$soldOutPrizes = this.getSoldOutPrizes();
                  Object other$soldOutPrizes = other.getSoldOutPrizes();
                  if (this$soldOutPrizes == null ? other$soldOutPrizes == null : this$soldOutPrizes.equals(other$soldOutPrizes)) {
                     Object this$totalPrizeValue = this.getTotalPrizeValue();
                     Object other$totalPrizeValue = other.getTotalPrizeValue();
                     return this$totalPrizeValue == null ? other$totalPrizeValue == null : this$totalPrizeValue.equals(other$totalPrizeValue);
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
      return other instanceof PointsBotPrizeConfigStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalPrizes = this.getTotalPrizes();
      result = result * 59 + ($totalPrizes == null ? 43 : $totalPrizes.hashCode());
      Object $enabledPrizes = this.getEnabledPrizes();
      result = result * 59 + ($enabledPrizes == null ? 43 : $enabledPrizes.hashCode());
      Object $totalRemainingQuantity = this.getTotalRemainingQuantity();
      result = result * 59 + ($totalRemainingQuantity == null ? 43 : $totalRemainingQuantity.hashCode());
      Object $soldOutPrizes = this.getSoldOutPrizes();
      result = result * 59 + ($soldOutPrizes == null ? 43 : $soldOutPrizes.hashCode());
      Object $totalPrizeValue = this.getTotalPrizeValue();
      return result * 59 + ($totalPrizeValue == null ? 43 : $totalPrizeValue.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPrizeConfigStatsResponse(totalPrizes="
         + this.getTotalPrizes()
         + ", enabledPrizes="
         + this.getEnabledPrizes()
         + ", totalRemainingQuantity="
         + this.getTotalRemainingQuantity()
         + ", soldOutPrizes="
         + this.getSoldOutPrizes()
         + ", totalPrizeValue="
         + this.getTotalPrizeValue()
         + ")";
   }
}
