package com.una.embyhub.model.dto.request.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class EmbyUserMultiCreateRequest implements ProtectedUserMutationRequest, Serializable {
   private String embyUserName;
   private String embyUserPassword;
   private Integer day;
   private Integer isAdmin;
   private Integer hostLineType;
   private String remarks;

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getEmbyUserPassword() {
      return this.embyUserPassword;
   }

   @Generated
   public Integer getDay() {
      return this.day;
   }

   @Generated
   public Integer getIsAdmin() {
      return this.isAdmin;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setEmbyUserPassword(final String embyUserPassword) {
      this.embyUserPassword = embyUserPassword;
   }

   @Generated
   public void setDay(final Integer day) {
      this.day = day;
   }

   @Generated
   public void setIsAdmin(final Integer isAdmin) {
      this.isAdmin = isAdmin;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserMultiCreateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$day = this.getDay();
         Object other$day = other.getDay();
         if (this$day == null ? other$day == null : this$day.equals(other$day)) {
            Object this$isAdmin = this.getIsAdmin();
            Object other$isAdmin = other.getIsAdmin();
            if (this$isAdmin == null ? other$isAdmin == null : this$isAdmin.equals(other$isAdmin)) {
               Object this$hostLineType = this.getHostLineType();
               Object other$hostLineType = other.getHostLineType();
               if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                  Object this$embyUserName = this.getEmbyUserName();
                  Object other$embyUserName = other.getEmbyUserName();
                  if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
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
      return other instanceof EmbyUserMultiCreateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $day = this.getDay();
      result = result * 59 + ($day == null ? 43 : $day.hashCode());
      Object $isAdmin = this.getIsAdmin();
      result = result * 59 + ($isAdmin == null ? 43 : $isAdmin.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $embyUserPassword = this.getEmbyUserPassword();
      result = result * 59 + ($embyUserPassword == null ? 43 : $embyUserPassword.hashCode());
      Object $remarks = this.getRemarks();
      return result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserMultiCreateRequest(embyUserName="
         + this.getEmbyUserName()
         + ", embyUserPassword="
         + this.getEmbyUserPassword()
         + ", day="
         + this.getDay()
         + ", isAdmin="
         + this.getIsAdmin()
         + ", hostLineType="
         + this.getHostLineType()
         + ", remarks="
         + this.getRemarks()
         + ")";
   }
}
