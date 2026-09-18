package com.una.embyhub.model.dto.request.rose;

import java.io.Serializable;
import lombok.Generated;

public class RoseQrStartRequest implements Serializable {
   private String app;
   private String embyPassword;

   @Generated
   public String getApp() {
      return this.app;
   }

   @Generated
   public String getEmbyPassword() {
      return this.embyPassword;
   }

   @Generated
   public void setApp(final String app) {
      this.app = app;
   }

   @Generated
   public void setEmbyPassword(final String embyPassword) {
      this.embyPassword = embyPassword;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseQrStartRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$app = this.getApp();
         Object other$app = other.getApp();
         if (this$app == null ? other$app == null : this$app.equals(other$app)) {
            Object this$embyPassword = this.getEmbyPassword();
            Object other$embyPassword = other.getEmbyPassword();
            return this$embyPassword == null ? other$embyPassword == null : this$embyPassword.equals(other$embyPassword);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof RoseQrStartRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $app = this.getApp();
      result = result * 59 + ($app == null ? 43 : $app.hashCode());
      Object $embyPassword = this.getEmbyPassword();
      return result * 59 + ($embyPassword == null ? 43 : $embyPassword.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RoseQrStartRequest(app=" + this.getApp() + ", embyPassword=" + this.getEmbyPassword() + ")";
   }
}
