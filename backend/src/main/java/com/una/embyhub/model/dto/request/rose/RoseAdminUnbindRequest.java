package com.una.embyhub.model.dto.request.rose;

import java.io.Serializable;
import lombok.Generated;

public class RoseAdminUnbindRequest implements Serializable {
   private String adminPassword;

   @Generated
   public String getAdminPassword() {
      return this.adminPassword;
   }

   @Generated
   public void setAdminPassword(final String adminPassword) {
      this.adminPassword = adminPassword;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseAdminUnbindRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$adminPassword = this.getAdminPassword();
         Object other$adminPassword = other.getAdminPassword();
         return this$adminPassword == null ? other$adminPassword == null : this$adminPassword.equals(other$adminPassword);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof RoseAdminUnbindRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $adminPassword = this.getAdminPassword();
      return result * 59 + ($adminPassword == null ? 43 : $adminPassword.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RoseAdminUnbindRequest(adminPassword=" + this.getAdminPassword() + ")";
   }
}
