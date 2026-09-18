package com.una.embyhub.model.dto.request.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class EmbyUserUpdatePassword implements ProtectedUserMutationRequest, Serializable {
   private Long id;
   private String oldPassword;
   private String embyUserPassword;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getOldPassword() {
      return this.oldPassword;
   }

   @Generated
   public String getEmbyUserPassword() {
      return this.embyUserPassword;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setOldPassword(final String oldPassword) {
      this.oldPassword = oldPassword;
   }

   @Generated
   public void setEmbyUserPassword(final String embyUserPassword) {
      this.embyUserPassword = embyUserPassword;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserUpdatePassword other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$oldPassword = this.getOldPassword();
            Object other$oldPassword = other.getOldPassword();
            if (this$oldPassword == null ? other$oldPassword == null : this$oldPassword.equals(other$oldPassword)) {
               Object this$embyUserPassword = this.getEmbyUserPassword();
               Object other$embyUserPassword = other.getEmbyUserPassword();
               return this$embyUserPassword == null ? other$embyUserPassword == null : this$embyUserPassword.equals(other$embyUserPassword);
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
      return other instanceof EmbyUserUpdatePassword;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $oldPassword = this.getOldPassword();
      result = result * 59 + ($oldPassword == null ? 43 : $oldPassword.hashCode());
      Object $embyUserPassword = this.getEmbyUserPassword();
      return result * 59 + ($embyUserPassword == null ? 43 : $embyUserPassword.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserUpdatePassword(id=" + this.getId() + ", oldPassword=" + this.getOldPassword() + ", embyUserPassword=" + this.getEmbyUserPassword() + ")";
   }
}
