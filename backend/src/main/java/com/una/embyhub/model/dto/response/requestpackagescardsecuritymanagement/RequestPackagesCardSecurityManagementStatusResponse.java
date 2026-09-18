package com.una.embyhub.model.dto.response.requestpackagescardsecuritymanagement;

import java.io.Serializable;
import lombok.Generated;

public class RequestPackagesCardSecurityManagementStatusResponse implements Serializable {
   private Long allCardSecurityManagementCount;
   private Long notUsedCardSecurityManagementCount;
   private Long usedCardSecurityManagementCount;
   private Long todayAddCount;

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
   public Long getTodayAddCount() {
      return this.todayAddCount;
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
   public void setTodayAddCount(final Long todayAddCount) {
      this.todayAddCount = todayAddCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestPackagesCardSecurityManagementStatusResponse other)) {
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
                  Object this$todayAddCount = this.getTodayAddCount();
                  Object other$todayAddCount = other.getTodayAddCount();
                  return this$todayAddCount == null ? other$todayAddCount == null : this$todayAddCount.equals(other$todayAddCount);
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
      return other instanceof RequestPackagesCardSecurityManagementStatusResponse;
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
      Object $todayAddCount = this.getTodayAddCount();
      return result * 59 + ($todayAddCount == null ? 43 : $todayAddCount.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestPackagesCardSecurityManagementStatusResponse(allCardSecurityManagementCount="
         + this.getAllCardSecurityManagementCount()
         + ", notUsedCardSecurityManagementCount="
         + this.getNotUsedCardSecurityManagementCount()
         + ", usedCardSecurityManagementCount="
         + this.getUsedCardSecurityManagementCount()
         + ", todayAddCount="
         + this.getTodayAddCount()
         + ")";
   }
}
