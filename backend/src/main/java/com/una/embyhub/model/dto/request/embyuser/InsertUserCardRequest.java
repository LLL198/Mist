package com.una.embyhub.model.dto.request.embyuser;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import lombok.Generated;

public class InsertUserCardRequest implements ProtectedUserMutationRequest, Serializable {
   private String cardPassword;
   private String remarks;
   private String password;
   @NotEmpty(
      message = "emby用户名不能为空"
   )
   private String embyUserName;

   @Generated
   public String getCardPassword() {
      return this.cardPassword;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public void setCardPassword(final String cardPassword) {
      this.cardPassword = cardPassword;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setPassword(final String password) {
      this.password = password;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof InsertUserCardRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$cardPassword = this.getCardPassword();
         Object other$cardPassword = other.getCardPassword();
         if (this$cardPassword == null ? other$cardPassword == null : this$cardPassword.equals(other$cardPassword)) {
            Object this$remarks = this.getRemarks();
            Object other$remarks = other.getRemarks();
            if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
               Object this$password = this.getPassword();
               Object other$password = other.getPassword();
               if (this$password == null ? other$password == null : this$password.equals(other$password)) {
                  Object this$embyUserName = this.getEmbyUserName();
                  Object other$embyUserName = other.getEmbyUserName();
                  return this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName);
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
      return other instanceof InsertUserCardRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $cardPassword = this.getCardPassword();
      result = result * 59 + ($cardPassword == null ? 43 : $cardPassword.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $password = this.getPassword();
      result = result * 59 + ($password == null ? 43 : $password.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "InsertUserCardRequest(cardPassword="
         + this.getCardPassword()
         + ", remarks="
         + this.getRemarks()
         + ", password="
         + this.getPassword()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ")";
   }
}
