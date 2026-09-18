package com.una.embyhub.config.common.config;

import java.time.Duration;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
   prefix = "foam.security.emby-library-access-rate-limit"
)
public class EmbyLibraryAccessRateLimitProperties {
   private int maxGlobalMutations = 10;
   private int maxUserMutations = 300;
   private Duration window = Duration.ofMinutes(1L);

   @Generated
   public int getMaxGlobalMutations() {
      return this.maxGlobalMutations;
   }

   @Generated
   public int getMaxUserMutations() {
      return this.maxUserMutations;
   }

   @Generated
   public Duration getWindow() {
      return this.window;
   }

   @Generated
   public void setMaxGlobalMutations(final int maxGlobalMutations) {
      this.maxGlobalMutations = maxGlobalMutations;
   }

   @Generated
   public void setMaxUserMutations(final int maxUserMutations) {
      this.maxUserMutations = maxUserMutations;
   }

   @Generated
   public void setWindow(final Duration window) {
      this.window = window;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyLibraryAccessRateLimitProperties other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getMaxGlobalMutations() != other.getMaxGlobalMutations()) {
         return false;
      } else if (this.getMaxUserMutations() != other.getMaxUserMutations()) {
         return false;
      } else {
         Object this$window = this.getWindow();
         Object other$window = other.getWindow();
         return this$window == null ? other$window == null : this$window.equals(other$window);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyLibraryAccessRateLimitProperties;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getMaxGlobalMutations();
      result = result * 59 + this.getMaxUserMutations();
      Object $window = this.getWindow();
      return result * 59 + ($window == null ? 43 : $window.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessRateLimitProperties(maxGlobalMutations="
         + this.getMaxGlobalMutations()
         + ", maxUserMutations="
         + this.getMaxUserMutations()
         + ", window="
         + this.getWindow()
         + ")";
   }
}
