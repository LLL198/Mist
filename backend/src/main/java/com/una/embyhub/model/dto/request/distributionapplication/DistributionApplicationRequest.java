package com.una.embyhub.model.dto.request.distributionapplication;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import lombok.Generated;

public class DistributionApplicationRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long userId;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer status;
   private String userName;
   private String orderNo;

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
   public String getOrderNo() {
      return this.orderNo;
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
   public void setOrderNo(final String orderNo) {
      this.orderNo = orderNo;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DistributionApplicationRequest other)) {
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
               Object this$userName = this.getUserName();
               Object other$userName = other.getUserName();
               if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                  Object this$orderNo = this.getOrderNo();
                  Object other$orderNo = other.getOrderNo();
                  return this$orderNo == null ? other$orderNo == null : this$orderNo.equals(other$orderNo);
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
      return other instanceof DistributionApplicationRequest;
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
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $orderNo = this.getOrderNo();
      return result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionApplicationRequest(userId="
         + this.getUserId()
         + ", status="
         + this.getStatus()
         + ", userName="
         + this.getUserName()
         + ", orderNo="
         + this.getOrderNo()
         + ")";
   }
}
