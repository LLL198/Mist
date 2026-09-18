package com.una.embyhub.model.dto.request.embyregionblock;

import java.io.Serializable;
import lombok.Generated;

public class EmbyRegionBlockRuleRequest implements Serializable {
   private String keyword;
   private String ruleType;
   private String country;
   private String province;
   private Boolean enabled;

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public String getRuleType() {
      return this.ruleType;
   }

   @Generated
   public String getCountry() {
      return this.country;
   }

   @Generated
   public String getProvince() {
      return this.province;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setRuleType(final String ruleType) {
      this.ruleType = ruleType;
   }

   @Generated
   public void setCountry(final String country) {
      this.country = country;
   }

   @Generated
   public void setProvince(final String province) {
      this.province = province;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyRegionBlockRuleRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$keyword = this.getKeyword();
            Object other$keyword = other.getKeyword();
            if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
               Object this$ruleType = this.getRuleType();
               Object other$ruleType = other.getRuleType();
               if (this$ruleType == null ? other$ruleType == null : this$ruleType.equals(other$ruleType)) {
                  Object this$country = this.getCountry();
                  Object other$country = other.getCountry();
                  if (this$country == null ? other$country == null : this$country.equals(other$country)) {
                     Object this$province = this.getProvince();
                     Object other$province = other.getProvince();
                     return this$province == null ? other$province == null : this$province.equals(other$province);
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
      return other instanceof EmbyRegionBlockRuleRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $ruleType = this.getRuleType();
      result = result * 59 + ($ruleType == null ? 43 : $ruleType.hashCode());
      Object $country = this.getCountry();
      result = result * 59 + ($country == null ? 43 : $country.hashCode());
      Object $province = this.getProvince();
      return result * 59 + ($province == null ? 43 : $province.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyRegionBlockRuleRequest(keyword="
         + this.getKeyword()
         + ", ruleType="
         + this.getRuleType()
         + ", country="
         + this.getCountry()
         + ", province="
         + this.getProvince()
         + ", enabled="
         + this.getEnabled()
         + ")";
   }
}
