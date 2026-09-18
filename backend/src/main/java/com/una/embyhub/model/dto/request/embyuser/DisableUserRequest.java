package com.una.embyhub.model.dto.request.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class DisableUserRequest implements ProtectedUserMutationRequest, Serializable {
   private String embyUserId;

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DisableUserRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyUserId = this.getEmbyUserId();
         Object other$embyUserId = other.getEmbyUserId();
         return this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof DisableUserRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyUserId = this.getEmbyUserId();
      return result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DisableUserRequest(embyUserId=" + this.getEmbyUserId() + ")";
   }
}
