package com.una.embyhub.model.dto.request.embyblockkeyword;

import lombok.Generated;

public class EmbyClientFilterSettingsRequest {
   private Boolean enabled;
   private Boolean blockUser;
   private Boolean regionEnabled;

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public Boolean getBlockUser() {
      return this.blockUser;
   }

   @Generated
   public Boolean getRegionEnabled() {
      return this.regionEnabled;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setBlockUser(final Boolean blockUser) {
      this.blockUser = blockUser;
   }

   @Generated
   public void setRegionEnabled(final Boolean regionEnabled) {
      this.regionEnabled = regionEnabled;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyClientFilterSettingsRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$blockUser = this.getBlockUser();
            Object other$blockUser = other.getBlockUser();
            if (this$blockUser == null ? other$blockUser == null : this$blockUser.equals(other$blockUser)) {
               Object this$regionEnabled = this.getRegionEnabled();
               Object other$regionEnabled = other.getRegionEnabled();
               return this$regionEnabled == null ? other$regionEnabled == null : this$regionEnabled.equals(other$regionEnabled);
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
      return other instanceof EmbyClientFilterSettingsRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $blockUser = this.getBlockUser();
      result = result * 59 + ($blockUser == null ? 43 : $blockUser.hashCode());
      Object $regionEnabled = this.getRegionEnabled();
      return result * 59 + ($regionEnabled == null ? 43 : $regionEnabled.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyClientFilterSettingsRequest(enabled="
         + this.getEnabled()
         + ", blockUser="
         + this.getBlockUser()
         + ", regionEnabled="
         + this.getRegionEnabled()
         + ")";
   }
}
