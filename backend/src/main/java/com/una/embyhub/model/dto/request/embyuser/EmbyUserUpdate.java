package com.una.embyhub.model.dto.request.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class EmbyUserUpdate implements ProtectedUserMutationRequest, Serializable {
   private Long id;
   private String embyUserPassword;
   private String remarks;
   private Integer requestPackagesCount;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getEmbyUserPassword() {
      return this.embyUserPassword;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Integer getRequestPackagesCount() {
      return this.requestPackagesCount;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmbyUserPassword(final String embyUserPassword) {
      this.embyUserPassword = embyUserPassword;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setRequestPackagesCount(final Integer requestPackagesCount) {
      this.requestPackagesCount = requestPackagesCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$requestPackagesCount = this.getRequestPackagesCount();
            Object other$requestPackagesCount = other.getRequestPackagesCount();
            if (this$requestPackagesCount == null ? other$requestPackagesCount == null : this$requestPackagesCount.equals(other$requestPackagesCount)) {
               Object this$embyUserPassword = this.getEmbyUserPassword();
               Object other$embyUserPassword = other.getEmbyUserPassword();
               if (this$embyUserPassword == null ? other$embyUserPassword == null : this$embyUserPassword.equals(other$embyUserPassword)) {
                  Object this$remarks = this.getRemarks();
                  Object other$remarks = other.getRemarks();
                  return this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks);
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
      return other instanceof EmbyUserUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $requestPackagesCount = this.getRequestPackagesCount();
      result = result * 59 + ($requestPackagesCount == null ? 43 : $requestPackagesCount.hashCode());
      Object $embyUserPassword = this.getEmbyUserPassword();
      result = result * 59 + ($embyUserPassword == null ? 43 : $embyUserPassword.hashCode());
      Object $remarks = this.getRemarks();
      return result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserUpdate(id="
         + this.getId()
         + ", embyUserPassword="
         + this.getEmbyUserPassword()
         + ", remarks="
         + this.getRemarks()
         + ", requestPackagesCount="
         + this.getRequestPackagesCount()
         + ")";
   }
}
