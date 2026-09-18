package com.una.embyhub.model.dto.response.invitation;

import java.io.Serializable;
import lombok.Generated;

public class InvitationCodeStatusResponse implements Serializable {
   private Long allInvitationCodeCount;
   private Long availableInvitationCodeCount;
   private Long usedInvitationCodeCount;

   @Generated
   public Long getAllInvitationCodeCount() {
      return this.allInvitationCodeCount;
   }

   @Generated
   public Long getAvailableInvitationCodeCount() {
      return this.availableInvitationCodeCount;
   }

   @Generated
   public Long getUsedInvitationCodeCount() {
      return this.usedInvitationCodeCount;
   }

   @Generated
   public void setAllInvitationCodeCount(final Long allInvitationCodeCount) {
      this.allInvitationCodeCount = allInvitationCodeCount;
   }

   @Generated
   public void setAvailableInvitationCodeCount(final Long availableInvitationCodeCount) {
      this.availableInvitationCodeCount = availableInvitationCodeCount;
   }

   @Generated
   public void setUsedInvitationCodeCount(final Long usedInvitationCodeCount) {
      this.usedInvitationCodeCount = usedInvitationCodeCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof InvitationCodeStatusResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$allInvitationCodeCount = this.getAllInvitationCodeCount();
         Object other$allInvitationCodeCount = other.getAllInvitationCodeCount();
         if (this$allInvitationCodeCount == null ? other$allInvitationCodeCount == null : this$allInvitationCodeCount.equals(other$allInvitationCodeCount)) {
            Object this$availableInvitationCodeCount = this.getAvailableInvitationCodeCount();
            Object other$availableInvitationCodeCount = other.getAvailableInvitationCodeCount();
            if (this$availableInvitationCodeCount == null
               ? other$availableInvitationCodeCount == null
               : this$availableInvitationCodeCount.equals(other$availableInvitationCodeCount)) {
               Object this$usedInvitationCodeCount = this.getUsedInvitationCodeCount();
               Object other$usedInvitationCodeCount = other.getUsedInvitationCodeCount();
               return this$usedInvitationCodeCount == null
                  ? other$usedInvitationCodeCount == null
                  : this$usedInvitationCodeCount.equals(other$usedInvitationCodeCount);
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
      return other instanceof InvitationCodeStatusResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $allInvitationCodeCount = this.getAllInvitationCodeCount();
      result = result * 59 + ($allInvitationCodeCount == null ? 43 : $allInvitationCodeCount.hashCode());
      Object $availableInvitationCodeCount = this.getAvailableInvitationCodeCount();
      result = result * 59 + ($availableInvitationCodeCount == null ? 43 : $availableInvitationCodeCount.hashCode());
      Object $usedInvitationCodeCount = this.getUsedInvitationCodeCount();
      return result * 59 + ($usedInvitationCodeCount == null ? 43 : $usedInvitationCodeCount.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "InvitationCodeStatusResponse(allInvitationCodeCount="
         + this.getAllInvitationCodeCount()
         + ", availableInvitationCodeCount="
         + this.getAvailableInvitationCodeCount()
         + ", usedInvitationCodeCount="
         + this.getUsedInvitationCodeCount()
         + ")";
   }
}
