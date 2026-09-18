package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Generated;

public class PointsBotHellVaultTopUpRequest {
   @Min(
      value = 1L,
      message = "补充积分不能少于1"
   )
   @Max(
      value = 100000L,
      message = "单次补充积分不能超过100000"
   )
   private int amount;

   @Generated
   public int getAmount() {
      return this.amount;
   }

   @Generated
   public void setAmount(final int amount) {
      this.amount = amount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotHellVaultTopUpRequest other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : this.getAmount() == other.getAmount();
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotHellVaultTopUpRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      return result * 59 + this.getAmount();
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotHellVaultTopUpRequest(amount=" + this.getAmount() + ")";
   }
}
