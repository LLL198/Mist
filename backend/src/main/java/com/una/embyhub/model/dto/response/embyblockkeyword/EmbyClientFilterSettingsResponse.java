package com.una.embyhub.model.dto.response.embyblockkeyword;

import java.util.List;
import lombok.Generated;

public class EmbyClientFilterSettingsResponse {
   private Boolean enabled;
   private Boolean blockUser;
   private Boolean regionEnabled;
   private Boolean usingDefaultPatterns;
   private List<String> defaultPatterns;
   private List<String> effectivePatterns;

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
   public Boolean getUsingDefaultPatterns() {
      return this.usingDefaultPatterns;
   }

   @Generated
   public List<String> getDefaultPatterns() {
      return this.defaultPatterns;
   }

   @Generated
   public List<String> getEffectivePatterns() {
      return this.effectivePatterns;
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
   public void setUsingDefaultPatterns(final Boolean usingDefaultPatterns) {
      this.usingDefaultPatterns = usingDefaultPatterns;
   }

   @Generated
   public void setDefaultPatterns(final List<String> defaultPatterns) {
      this.defaultPatterns = defaultPatterns;
   }

   @Generated
   public void setEffectivePatterns(final List<String> effectivePatterns) {
      this.effectivePatterns = effectivePatterns;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyClientFilterSettingsResponse other)) {
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
               if (this$regionEnabled == null ? other$regionEnabled == null : this$regionEnabled.equals(other$regionEnabled)) {
                  Object this$usingDefaultPatterns = this.getUsingDefaultPatterns();
                  Object other$usingDefaultPatterns = other.getUsingDefaultPatterns();
                  if (this$usingDefaultPatterns == null ? other$usingDefaultPatterns == null : this$usingDefaultPatterns.equals(other$usingDefaultPatterns)) {
                     Object this$defaultPatterns = this.getDefaultPatterns();
                     Object other$defaultPatterns = other.getDefaultPatterns();
                     if (this$defaultPatterns == null ? other$defaultPatterns == null : this$defaultPatterns.equals(other$defaultPatterns)) {
                        Object this$effectivePatterns = this.getEffectivePatterns();
                        Object other$effectivePatterns = other.getEffectivePatterns();
                        return this$effectivePatterns == null ? other$effectivePatterns == null : this$effectivePatterns.equals(other$effectivePatterns);
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
      return other instanceof EmbyClientFilterSettingsResponse;
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
      result = result * 59 + ($regionEnabled == null ? 43 : $regionEnabled.hashCode());
      Object $usingDefaultPatterns = this.getUsingDefaultPatterns();
      result = result * 59 + ($usingDefaultPatterns == null ? 43 : $usingDefaultPatterns.hashCode());
      Object $defaultPatterns = this.getDefaultPatterns();
      result = result * 59 + ($defaultPatterns == null ? 43 : $defaultPatterns.hashCode());
      Object $effectivePatterns = this.getEffectivePatterns();
      return result * 59 + ($effectivePatterns == null ? 43 : $effectivePatterns.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyClientFilterSettingsResponse(enabled="
         + this.getEnabled()
         + ", blockUser="
         + this.getBlockUser()
         + ", regionEnabled="
         + this.getRegionEnabled()
         + ", usingDefaultPatterns="
         + this.getUsingDefaultPatterns()
         + ", defaultPatterns="
         + this.getDefaultPatterns()
         + ", effectivePatterns="
         + this.getEffectivePatterns()
         + ")";
   }
}
