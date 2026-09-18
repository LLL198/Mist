package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Generated;

public class PointsBotFoamBagConfigUpdate {
   @NotEmpty(
      message = "至少配置一个雾袋积分档位"
   )
   @Size(
      max = 10,
      message = "雾袋积分档位最多配置10个"
   )
   private List<Integer> amountTiers;
   @Min(
      value = 1L,
      message = "每日次数必须大于0"
   )
   @Max(
      value = 100L,
      message = "每日次数不能超过100"
   )
   private int dailyLimit;
   @Min(
      value = 1L,
      message = "归还倍数必须大于0"
   )
   @Max(
      value = 10L,
      message = "归还倍数不能超过10"
   )
   private int repaymentMultiplier;
   @Min(
      value = 1L,
      message = "归还期限必须大于0"
   )
   @Max(
      value = 720L,
      message = "归还期限不能超过720小时"
   )
   private int repaymentHours;
   @Min(
      value = 1L,
      message = "限制天数必须大于0"
   )
   @Max(
      value = 365L,
      message = "限制天数不能超过365天"
   )
   private int penaltyDays;
   @NotNull(
      message = "请选择是否允许未绑定用户使用"
   )
   private Boolean allowUnboundUsers;

   @Generated
   public List<Integer> getAmountTiers() {
      return this.amountTiers;
   }

   @Generated
   public int getDailyLimit() {
      return this.dailyLimit;
   }

   @Generated
   public int getRepaymentMultiplier() {
      return this.repaymentMultiplier;
   }

   @Generated
   public int getRepaymentHours() {
      return this.repaymentHours;
   }

   @Generated
   public int getPenaltyDays() {
      return this.penaltyDays;
   }

   @Generated
   public Boolean getAllowUnboundUsers() {
      return this.allowUnboundUsers;
   }

   @Generated
   public void setAmountTiers(final List<Integer> amountTiers) {
      this.amountTiers = amountTiers;
   }

   @Generated
   public void setDailyLimit(final int dailyLimit) {
      this.dailyLimit = dailyLimit;
   }

   @Generated
   public void setRepaymentMultiplier(final int repaymentMultiplier) {
      this.repaymentMultiplier = repaymentMultiplier;
   }

   @Generated
   public void setRepaymentHours(final int repaymentHours) {
      this.repaymentHours = repaymentHours;
   }

   @Generated
   public void setPenaltyDays(final int penaltyDays) {
      this.penaltyDays = penaltyDays;
   }

   @Generated
   public void setAllowUnboundUsers(final Boolean allowUnboundUsers) {
      this.allowUnboundUsers = allowUnboundUsers;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotFoamBagConfigUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getDailyLimit() != other.getDailyLimit()) {
         return false;
      } else if (this.getRepaymentMultiplier() != other.getRepaymentMultiplier()) {
         return false;
      } else if (this.getRepaymentHours() != other.getRepaymentHours()) {
         return false;
      } else if (this.getPenaltyDays() != other.getPenaltyDays()) {
         return false;
      } else {
         Object this$allowUnboundUsers = this.getAllowUnboundUsers();
         Object other$allowUnboundUsers = other.getAllowUnboundUsers();
         if (this$allowUnboundUsers == null ? other$allowUnboundUsers == null : this$allowUnboundUsers.equals(other$allowUnboundUsers)) {
            Object this$amountTiers = this.getAmountTiers();
            Object other$amountTiers = other.getAmountTiers();
            return this$amountTiers == null ? other$amountTiers == null : this$amountTiers.equals(other$amountTiers);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotFoamBagConfigUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getDailyLimit();
      result = result * 59 + this.getRepaymentMultiplier();
      result = result * 59 + this.getRepaymentHours();
      result = result * 59 + this.getPenaltyDays();
      Object $allowUnboundUsers = this.getAllowUnboundUsers();
      result = result * 59 + ($allowUnboundUsers == null ? 43 : $allowUnboundUsers.hashCode());
      Object $amountTiers = this.getAmountTiers();
      return result * 59 + ($amountTiers == null ? 43 : $amountTiers.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotFoamBagConfigUpdate(amountTiers="
         + this.getAmountTiers()
         + ", dailyLimit="
         + this.getDailyLimit()
         + ", repaymentMultiplier="
         + this.getRepaymentMultiplier()
         + ", repaymentHours="
         + this.getRepaymentHours()
         + ", penaltyDays="
         + this.getPenaltyDays()
         + ", allowUnboundUsers="
         + this.getAllowUnboundUsers()
         + ")";
   }
}
