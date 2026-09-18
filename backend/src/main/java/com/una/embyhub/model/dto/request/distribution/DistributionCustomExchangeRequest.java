package com.una.embyhub.model.dto.request.distribution;

import java.io.Serializable;
import lombok.Generated;

public class DistributionCustomExchangeRequest implements Serializable {
   private String exchangeNo;
   private Long userId;
   private Integer status;
   private String userName;

   @Generated
   public String getExchangeNo() {
      return this.exchangeNo;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public void setExchangeNo(final String exchangeNo) {
      this.exchangeNo = exchangeNo;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DistributionCustomExchangeRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userId = this.getUserId();
         Object other$userId = other.getUserId();
         if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$exchangeNo = this.getExchangeNo();
               Object other$exchangeNo = other.getExchangeNo();
               if (this$exchangeNo == null ? other$exchangeNo == null : this$exchangeNo.equals(other$exchangeNo)) {
                  Object this$userName = this.getUserName();
                  Object other$userName = other.getUserName();
                  return this$userName == null ? other$userName == null : this$userName.equals(other$userName);
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
      return other instanceof DistributionCustomExchangeRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $exchangeNo = this.getExchangeNo();
      result = result * 59 + ($exchangeNo == null ? 43 : $exchangeNo.hashCode());
      Object $userName = this.getUserName();
      return result * 59 + ($userName == null ? 43 : $userName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionCustomExchangeRequest(exchangeNo="
         + this.getExchangeNo()
         + ", userId="
         + this.getUserId()
         + ", status="
         + this.getStatus()
         + ", userName="
         + this.getUserName()
         + ")";
   }
}
