package com.una.embyhub.model.dto.response.cardsecuritymanagement;

import java.io.Serializable;
import lombok.Generated;

public class CardSecurityManagementStatusResponse implements Serializable {
   private Long allCardSecurityManagementCount;
   private Long notUsedCardSecurityManagementCount;
   private Long usedCardSecurityManagementCount;
   private Long distributorAllCardCount;
   private Long distributorNotUsedCardCount;
   private Long distributorUsedCardCount;

   @Generated
   public Long getAllCardSecurityManagementCount() {
      return this.allCardSecurityManagementCount;
   }

   @Generated
   public Long getNotUsedCardSecurityManagementCount() {
      return this.notUsedCardSecurityManagementCount;
   }

   @Generated
   public Long getUsedCardSecurityManagementCount() {
      return this.usedCardSecurityManagementCount;
   }

   @Generated
   public Long getDistributorAllCardCount() {
      return this.distributorAllCardCount;
   }

   @Generated
   public Long getDistributorNotUsedCardCount() {
      return this.distributorNotUsedCardCount;
   }

   @Generated
   public Long getDistributorUsedCardCount() {
      return this.distributorUsedCardCount;
   }

   @Generated
   public void setAllCardSecurityManagementCount(final Long allCardSecurityManagementCount) {
      this.allCardSecurityManagementCount = allCardSecurityManagementCount;
   }

   @Generated
   public void setNotUsedCardSecurityManagementCount(final Long notUsedCardSecurityManagementCount) {
      this.notUsedCardSecurityManagementCount = notUsedCardSecurityManagementCount;
   }

   @Generated
   public void setUsedCardSecurityManagementCount(final Long usedCardSecurityManagementCount) {
      this.usedCardSecurityManagementCount = usedCardSecurityManagementCount;
   }

   @Generated
   public void setDistributorAllCardCount(final Long distributorAllCardCount) {
      this.distributorAllCardCount = distributorAllCardCount;
   }

   @Generated
   public void setDistributorNotUsedCardCount(final Long distributorNotUsedCardCount) {
      this.distributorNotUsedCardCount = distributorNotUsedCardCount;
   }

   @Generated
   public void setDistributorUsedCardCount(final Long distributorUsedCardCount) {
      this.distributorUsedCardCount = distributorUsedCardCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CardSecurityManagementStatusResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$allCardSecurityManagementCount = this.getAllCardSecurityManagementCount();
         Object other$allCardSecurityManagementCount = other.getAllCardSecurityManagementCount();
         if (this$allCardSecurityManagementCount == null
            ? other$allCardSecurityManagementCount == null
            : this$allCardSecurityManagementCount.equals(other$allCardSecurityManagementCount)) {
            Object this$notUsedCardSecurityManagementCount = this.getNotUsedCardSecurityManagementCount();
            Object other$notUsedCardSecurityManagementCount = other.getNotUsedCardSecurityManagementCount();
            if (this$notUsedCardSecurityManagementCount == null
               ? other$notUsedCardSecurityManagementCount == null
               : this$notUsedCardSecurityManagementCount.equals(other$notUsedCardSecurityManagementCount)) {
               Object this$usedCardSecurityManagementCount = this.getUsedCardSecurityManagementCount();
               Object other$usedCardSecurityManagementCount = other.getUsedCardSecurityManagementCount();
               if (this$usedCardSecurityManagementCount == null
                  ? other$usedCardSecurityManagementCount == null
                  : this$usedCardSecurityManagementCount.equals(other$usedCardSecurityManagementCount)) {
                  Object this$distributorAllCardCount = this.getDistributorAllCardCount();
                  Object other$distributorAllCardCount = other.getDistributorAllCardCount();
                  if (this$distributorAllCardCount == null
                     ? other$distributorAllCardCount == null
                     : this$distributorAllCardCount.equals(other$distributorAllCardCount)) {
                     Object this$distributorNotUsedCardCount = this.getDistributorNotUsedCardCount();
                     Object other$distributorNotUsedCardCount = other.getDistributorNotUsedCardCount();
                     if (this$distributorNotUsedCardCount == null
                        ? other$distributorNotUsedCardCount == null
                        : this$distributorNotUsedCardCount.equals(other$distributorNotUsedCardCount)) {
                        Object this$distributorUsedCardCount = this.getDistributorUsedCardCount();
                        Object other$distributorUsedCardCount = other.getDistributorUsedCardCount();
                        return this$distributorUsedCardCount == null
                           ? other$distributorUsedCardCount == null
                           : this$distributorUsedCardCount.equals(other$distributorUsedCardCount);
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
      return other instanceof CardSecurityManagementStatusResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $allCardSecurityManagementCount = this.getAllCardSecurityManagementCount();
      result = result * 59 + ($allCardSecurityManagementCount == null ? 43 : $allCardSecurityManagementCount.hashCode());
      Object $notUsedCardSecurityManagementCount = this.getNotUsedCardSecurityManagementCount();
      result = result * 59 + ($notUsedCardSecurityManagementCount == null ? 43 : $notUsedCardSecurityManagementCount.hashCode());
      Object $usedCardSecurityManagementCount = this.getUsedCardSecurityManagementCount();
      result = result * 59 + ($usedCardSecurityManagementCount == null ? 43 : $usedCardSecurityManagementCount.hashCode());
      Object $distributorAllCardCount = this.getDistributorAllCardCount();
      result = result * 59 + ($distributorAllCardCount == null ? 43 : $distributorAllCardCount.hashCode());
      Object $distributorNotUsedCardCount = this.getDistributorNotUsedCardCount();
      result = result * 59 + ($distributorNotUsedCardCount == null ? 43 : $distributorNotUsedCardCount.hashCode());
      Object $distributorUsedCardCount = this.getDistributorUsedCardCount();
      return result * 59 + ($distributorUsedCardCount == null ? 43 : $distributorUsedCardCount.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "CardSecurityManagementStatusResponse(allCardSecurityManagementCount="
         + this.getAllCardSecurityManagementCount()
         + ", notUsedCardSecurityManagementCount="
         + this.getNotUsedCardSecurityManagementCount()
         + ", usedCardSecurityManagementCount="
         + this.getUsedCardSecurityManagementCount()
         + ", distributorAllCardCount="
         + this.getDistributorAllCardCount()
         + ", distributorNotUsedCardCount="
         + this.getDistributorNotUsedCardCount()
         + ", distributorUsedCardCount="
         + this.getDistributorUsedCardCount()
         + ")";
   }
}
