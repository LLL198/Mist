package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class UserStatsResponse implements Serializable {
   private Long allUserCount;
   private Long activeUserCount;
   private Long inactiveUserCount;
   private Long expiringSoonUserCount;

   @Generated
   public Long getAllUserCount() {
      return this.allUserCount;
   }

   @Generated
   public Long getActiveUserCount() {
      return this.activeUserCount;
   }

   @Generated
   public Long getInactiveUserCount() {
      return this.inactiveUserCount;
   }

   @Generated
   public Long getExpiringSoonUserCount() {
      return this.expiringSoonUserCount;
   }

   @Generated
   public void setAllUserCount(final Long allUserCount) {
      this.allUserCount = allUserCount;
   }

   @Generated
   public void setActiveUserCount(final Long activeUserCount) {
      this.activeUserCount = activeUserCount;
   }

   @Generated
   public void setInactiveUserCount(final Long inactiveUserCount) {
      this.inactiveUserCount = inactiveUserCount;
   }

   @Generated
   public void setExpiringSoonUserCount(final Long expiringSoonUserCount) {
      this.expiringSoonUserCount = expiringSoonUserCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$allUserCount = this.getAllUserCount();
         Object other$allUserCount = other.getAllUserCount();
         if (this$allUserCount == null ? other$allUserCount == null : this$allUserCount.equals(other$allUserCount)) {
            Object this$activeUserCount = this.getActiveUserCount();
            Object other$activeUserCount = other.getActiveUserCount();
            if (this$activeUserCount == null ? other$activeUserCount == null : this$activeUserCount.equals(other$activeUserCount)) {
               Object this$inactiveUserCount = this.getInactiveUserCount();
               Object other$inactiveUserCount = other.getInactiveUserCount();
               if (this$inactiveUserCount == null ? other$inactiveUserCount == null : this$inactiveUserCount.equals(other$inactiveUserCount)) {
                  Object this$expiringSoonUserCount = this.getExpiringSoonUserCount();
                  Object other$expiringSoonUserCount = other.getExpiringSoonUserCount();
                  return this$expiringSoonUserCount == null
                     ? other$expiringSoonUserCount == null
                     : this$expiringSoonUserCount.equals(other$expiringSoonUserCount);
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
      return other instanceof UserStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $allUserCount = this.getAllUserCount();
      result = result * 59 + ($allUserCount == null ? 43 : $allUserCount.hashCode());
      Object $activeUserCount = this.getActiveUserCount();
      result = result * 59 + ($activeUserCount == null ? 43 : $activeUserCount.hashCode());
      Object $inactiveUserCount = this.getInactiveUserCount();
      result = result * 59 + ($inactiveUserCount == null ? 43 : $inactiveUserCount.hashCode());
      Object $expiringSoonUserCount = this.getExpiringSoonUserCount();
      return result * 59 + ($expiringSoonUserCount == null ? 43 : $expiringSoonUserCount.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserStatsResponse(allUserCount="
         + this.getAllUserCount()
         + ", activeUserCount="
         + this.getActiveUserCount()
         + ", inactiveUserCount="
         + this.getInactiveUserCount()
         + ", expiringSoonUserCount="
         + this.getExpiringSoonUserCount()
         + ")";
   }
}
