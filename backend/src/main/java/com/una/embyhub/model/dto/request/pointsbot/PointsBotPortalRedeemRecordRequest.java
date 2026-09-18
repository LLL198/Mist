package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotPortalRedeemRecordRequest implements Serializable {
   @Pattern(
      regexp = "^(|CREATE_ACCOUNT|RENEW)$",
      message = "兑换类型不合法"
   )
   private String redeemType;
   @Pattern(
      regexp = "^(|PROCESSING|SUCCESS|REFUNDED)$",
      message = "兑换状态不合法"
   )
   private String status;

   @Generated
   public String getRedeemType() {
      return this.redeemType;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public void setRedeemType(final String redeemType) {
      this.redeemType = redeemType;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalRedeemRecordRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$redeemType = this.getRedeemType();
         Object other$redeemType = other.getRedeemType();
         if (this$redeemType == null ? other$redeemType == null : this$redeemType.equals(other$redeemType)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            return this$status == null ? other$status == null : this$status.equals(other$status);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotPortalRedeemRecordRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $redeemType = this.getRedeemType();
      result = result * 59 + ($redeemType == null ? 43 : $redeemType.hashCode());
      Object $status = this.getStatus();
      return result * 59 + ($status == null ? 43 : $status.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalRedeemRecordRequest(redeemType=" + this.getRedeemType() + ", status=" + this.getStatus() + ")";
   }
}
