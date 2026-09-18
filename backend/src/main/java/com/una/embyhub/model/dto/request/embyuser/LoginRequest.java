package com.una.embyhub.model.dto.request.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class LoginRequest implements Serializable {
   private String userName;
   private String password;
   private Long embyInfoId;

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
   }

   @Generated
   public void setPassword(final String password) {
      this.password = password;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LoginRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$userName = this.getUserName();
            Object other$userName = other.getUserName();
            if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
               Object this$password = this.getPassword();
               Object other$password = other.getPassword();
               return this$password == null ? other$password == null : this$password.equals(other$password);
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
      return other instanceof LoginRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $password = this.getPassword();
      return result * 59 + ($password == null ? 43 : $password.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "LoginRequest(userName=" + this.getUserName() + ", password=" + this.getPassword() + ", embyInfoId=" + this.getEmbyInfoId() + ")";
   }
}
