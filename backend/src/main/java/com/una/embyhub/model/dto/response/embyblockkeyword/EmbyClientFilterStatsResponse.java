package com.una.embyhub.model.dto.response.embyblockkeyword;

import java.io.Serializable;
import lombok.Generated;

public class EmbyClientFilterStatsResponse implements Serializable {
   private Boolean enabled;
   private Boolean blockUser;
   private Boolean regionEnabled;
   private Long customRuleCount;
   private Long enabledRuleCount;
   private Long interceptTotalCount;
   private Long regionCatalogRuleCount;
   private Long regionEnabledRuleCount;
   private Long regionInterceptCount;

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
   public Long getCustomRuleCount() {
      return this.customRuleCount;
   }

   @Generated
   public Long getEnabledRuleCount() {
      return this.enabledRuleCount;
   }

   @Generated
   public Long getInterceptTotalCount() {
      return this.interceptTotalCount;
   }

   @Generated
   public Long getRegionCatalogRuleCount() {
      return this.regionCatalogRuleCount;
   }

   @Generated
   public Long getRegionEnabledRuleCount() {
      return this.regionEnabledRuleCount;
   }

   @Generated
   public Long getRegionInterceptCount() {
      return this.regionInterceptCount;
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
   public void setCustomRuleCount(final Long customRuleCount) {
      this.customRuleCount = customRuleCount;
   }

   @Generated
   public void setEnabledRuleCount(final Long enabledRuleCount) {
      this.enabledRuleCount = enabledRuleCount;
   }

   @Generated
   public void setInterceptTotalCount(final Long interceptTotalCount) {
      this.interceptTotalCount = interceptTotalCount;
   }

   @Generated
   public void setRegionCatalogRuleCount(final Long regionCatalogRuleCount) {
      this.regionCatalogRuleCount = regionCatalogRuleCount;
   }

   @Generated
   public void setRegionEnabledRuleCount(final Long regionEnabledRuleCount) {
      this.regionEnabledRuleCount = regionEnabledRuleCount;
   }

   @Generated
   public void setRegionInterceptCount(final Long regionInterceptCount) {
      this.regionInterceptCount = regionInterceptCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyClientFilterStatsResponse other)) {
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
                  Object this$customRuleCount = this.getCustomRuleCount();
                  Object other$customRuleCount = other.getCustomRuleCount();
                  if (this$customRuleCount == null ? other$customRuleCount == null : this$customRuleCount.equals(other$customRuleCount)) {
                     Object this$enabledRuleCount = this.getEnabledRuleCount();
                     Object other$enabledRuleCount = other.getEnabledRuleCount();
                     if (this$enabledRuleCount == null ? other$enabledRuleCount == null : this$enabledRuleCount.equals(other$enabledRuleCount)) {
                        Object this$interceptTotalCount = this.getInterceptTotalCount();
                        Object other$interceptTotalCount = other.getInterceptTotalCount();
                        if (this$interceptTotalCount == null ? other$interceptTotalCount == null : this$interceptTotalCount.equals(other$interceptTotalCount)) {
                           Object this$regionCatalogRuleCount = this.getRegionCatalogRuleCount();
                           Object other$regionCatalogRuleCount = other.getRegionCatalogRuleCount();
                           if (this$regionCatalogRuleCount == null
                              ? other$regionCatalogRuleCount == null
                              : this$regionCatalogRuleCount.equals(other$regionCatalogRuleCount)) {
                              Object this$regionEnabledRuleCount = this.getRegionEnabledRuleCount();
                              Object other$regionEnabledRuleCount = other.getRegionEnabledRuleCount();
                              if (this$regionEnabledRuleCount == null
                                 ? other$regionEnabledRuleCount == null
                                 : this$regionEnabledRuleCount.equals(other$regionEnabledRuleCount)) {
                                 Object this$regionInterceptCount = this.getRegionInterceptCount();
                                 Object other$regionInterceptCount = other.getRegionInterceptCount();
                                 return this$regionInterceptCount == null
                                    ? other$regionInterceptCount == null
                                    : this$regionInterceptCount.equals(other$regionInterceptCount);
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
      return other instanceof EmbyClientFilterStatsResponse;
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
      Object $customRuleCount = this.getCustomRuleCount();
      result = result * 59 + ($customRuleCount == null ? 43 : $customRuleCount.hashCode());
      Object $enabledRuleCount = this.getEnabledRuleCount();
      result = result * 59 + ($enabledRuleCount == null ? 43 : $enabledRuleCount.hashCode());
      Object $interceptTotalCount = this.getInterceptTotalCount();
      result = result * 59 + ($interceptTotalCount == null ? 43 : $interceptTotalCount.hashCode());
      Object $regionCatalogRuleCount = this.getRegionCatalogRuleCount();
      result = result * 59 + ($regionCatalogRuleCount == null ? 43 : $regionCatalogRuleCount.hashCode());
      Object $regionEnabledRuleCount = this.getRegionEnabledRuleCount();
      result = result * 59 + ($regionEnabledRuleCount == null ? 43 : $regionEnabledRuleCount.hashCode());
      Object $regionInterceptCount = this.getRegionInterceptCount();
      return result * 59 + ($regionInterceptCount == null ? 43 : $regionInterceptCount.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyClientFilterStatsResponse(enabled="
         + this.getEnabled()
         + ", blockUser="
         + this.getBlockUser()
         + ", regionEnabled="
         + this.getRegionEnabled()
         + ", customRuleCount="
         + this.getCustomRuleCount()
         + ", enabledRuleCount="
         + this.getEnabledRuleCount()
         + ", interceptTotalCount="
         + this.getInterceptTotalCount()
         + ", regionCatalogRuleCount="
         + this.getRegionCatalogRuleCount()
         + ", regionEnabledRuleCount="
         + this.getRegionEnabledRuleCount()
         + ", regionInterceptCount="
         + this.getRegionInterceptCount()
         + ")";
   }
}
