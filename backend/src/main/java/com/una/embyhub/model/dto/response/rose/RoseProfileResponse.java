package com.una.embyhub.model.dto.response.rose;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class RoseProfileResponse implements Serializable {
   private Boolean enabled;
   private Boolean baseUrlConfigured;
   private List<String> supportedApps;
   private RoseBindingResponse binding;
   private Object roseProfile;
   private Object meta;
   private String message;

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public Boolean getBaseUrlConfigured() {
      return this.baseUrlConfigured;
   }

   @Generated
   public List<String> getSupportedApps() {
      return this.supportedApps;
   }

   @Generated
   public RoseBindingResponse getBinding() {
      return this.binding;
   }

   @Generated
   public Object getRoseProfile() {
      return this.roseProfile;
   }

   @Generated
   public Object getMeta() {
      return this.meta;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setBaseUrlConfigured(final Boolean baseUrlConfigured) {
      this.baseUrlConfigured = baseUrlConfigured;
   }

   @Generated
   public void setSupportedApps(final List<String> supportedApps) {
      this.supportedApps = supportedApps;
   }

   @Generated
   public void setBinding(final RoseBindingResponse binding) {
      this.binding = binding;
   }

   @Generated
   public void setRoseProfile(final Object roseProfile) {
      this.roseProfile = roseProfile;
   }

   @Generated
   public void setMeta(final Object meta) {
      this.meta = meta;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseProfileResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$baseUrlConfigured = this.getBaseUrlConfigured();
            Object other$baseUrlConfigured = other.getBaseUrlConfigured();
            if (this$baseUrlConfigured == null ? other$baseUrlConfigured == null : this$baseUrlConfigured.equals(other$baseUrlConfigured)) {
               Object this$supportedApps = this.getSupportedApps();
               Object other$supportedApps = other.getSupportedApps();
               if (this$supportedApps == null ? other$supportedApps == null : this$supportedApps.equals(other$supportedApps)) {
                  Object this$binding = this.getBinding();
                  Object other$binding = other.getBinding();
                  if (this$binding == null ? other$binding == null : this$binding.equals(other$binding)) {
                     Object this$roseProfile = this.getRoseProfile();
                     Object other$roseProfile = other.getRoseProfile();
                     if (this$roseProfile == null ? other$roseProfile == null : this$roseProfile.equals(other$roseProfile)) {
                        Object this$meta = this.getMeta();
                        Object other$meta = other.getMeta();
                        if (this$meta == null ? other$meta == null : this$meta.equals(other$meta)) {
                           Object this$message = this.getMessage();
                           Object other$message = other.getMessage();
                           return this$message == null ? other$message == null : this$message.equals(other$message);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof RoseProfileResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $baseUrlConfigured = this.getBaseUrlConfigured();
      result = result * 59 + ($baseUrlConfigured == null ? 43 : $baseUrlConfigured.hashCode());
      Object $supportedApps = this.getSupportedApps();
      result = result * 59 + ($supportedApps == null ? 43 : $supportedApps.hashCode());
      Object $binding = this.getBinding();
      result = result * 59 + ($binding == null ? 43 : $binding.hashCode());
      Object $roseProfile = this.getRoseProfile();
      result = result * 59 + ($roseProfile == null ? 43 : $roseProfile.hashCode());
      Object $meta = this.getMeta();
      result = result * 59 + ($meta == null ? 43 : $meta.hashCode());
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RoseProfileResponse(enabled="
         + this.getEnabled()
         + ", baseUrlConfigured="
         + this.getBaseUrlConfigured()
         + ", supportedApps="
         + this.getSupportedApps()
         + ", binding="
         + this.getBinding()
         + ", roseProfile="
         + this.getRoseProfile()
         + ", meta="
         + this.getMeta()
         + ", message="
         + this.getMessage()
         + ")";
   }
}
