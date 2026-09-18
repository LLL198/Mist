package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import lombok.Generated;

public class EmbySettingsResponse implements Serializable {
   private String embyUrl;
   private String embyKey;
   private String adminUserId;

   @Generated
   public String getEmbyUrl() {
      return this.embyUrl;
   }

   @Generated
   public String getEmbyKey() {
      return this.embyKey;
   }

   @Generated
   public String getAdminUserId() {
      return this.adminUserId;
   }

   @Generated
   public void setEmbyUrl(final String embyUrl) {
      this.embyUrl = embyUrl;
   }

   @Generated
   public void setEmbyKey(final String embyKey) {
      this.embyKey = embyKey;
   }

   @Generated
   public void setAdminUserId(final String adminUserId) {
      this.adminUserId = adminUserId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbySettingsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyUrl = this.getEmbyUrl();
         Object other$embyUrl = other.getEmbyUrl();
         if (this$embyUrl == null ? other$embyUrl == null : this$embyUrl.equals(other$embyUrl)) {
            Object this$embyKey = this.getEmbyKey();
            Object other$embyKey = other.getEmbyKey();
            if (this$embyKey == null ? other$embyKey == null : this$embyKey.equals(other$embyKey)) {
               Object this$adminUserId = this.getAdminUserId();
               Object other$adminUserId = other.getAdminUserId();
               return this$adminUserId == null ? other$adminUserId == null : this$adminUserId.equals(other$adminUserId);
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
      return other instanceof EmbySettingsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyUrl = this.getEmbyUrl();
      result = result * 59 + ($embyUrl == null ? 43 : $embyUrl.hashCode());
      Object $embyKey = this.getEmbyKey();
      result = result * 59 + ($embyKey == null ? 43 : $embyKey.hashCode());
      Object $adminUserId = this.getAdminUserId();
      return result * 59 + ($adminUserId == null ? 43 : $adminUserId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbySettingsResponse(embyUrl=" + this.getEmbyUrl() + ", embyKey=" + this.getEmbyKey() + ", adminUserId=" + this.getAdminUserId() + ")";
   }
}
