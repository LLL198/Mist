package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotPortalLedgerRequest implements Serializable {
   @Size(
      max = 50,
      message = "流水原因长度不能超过50个字符"
   )
   private String reason;
   @Pattern(
      regexp = "^(|INCOME|EXPENSE)$",
      message = "流水方向不合法"
   )
   private String direction;

   @Generated
   public String getReason() {
      return this.reason;
   }

   @Generated
   public String getDirection() {
      return this.direction;
   }

   @Generated
   public void setReason(final String reason) {
      this.reason = reason;
   }

   @Generated
   public void setDirection(final String direction) {
      this.direction = direction;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalLedgerRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$reason = this.getReason();
         Object other$reason = other.getReason();
         if (this$reason == null ? other$reason == null : this$reason.equals(other$reason)) {
            Object this$direction = this.getDirection();
            Object other$direction = other.getDirection();
            return this$direction == null ? other$direction == null : this$direction.equals(other$direction);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotPortalLedgerRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $reason = this.getReason();
      result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
      Object $direction = this.getDirection();
      return result * 59 + ($direction == null ? 43 : $direction.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalLedgerRequest(reason=" + this.getReason() + ", direction=" + this.getDirection() + ")";
   }
}
