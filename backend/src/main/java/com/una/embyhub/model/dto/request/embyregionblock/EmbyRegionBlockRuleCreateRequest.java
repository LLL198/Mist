package com.una.embyhub.model.dto.request.embyregionblock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class EmbyRegionBlockRuleCreateRequest implements Serializable {
   @NotBlank(
      message = "地区类型不能为空"
   )
   @Pattern(
      regexp = "COUNTRY|PROVINCE",
      message = "地区类型仅支持国家/地区或省/州"
   )
   private String ruleType;
   @NotBlank(
      message = "国家/地区不能为空"
   )
   @Size(
      max = 128,
      message = "国家/地区名称不能超过128个字符"
   )
   private String country;
   @Size(
      max = 128,
      message = "省/州名称不能超过128个字符"
   )
   private String province;
   @NotNull(
      message = "启用状态不能为空"
   )
   private Boolean enabled;

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
      } else if (!(o instanceof EmbyRegionBlockRuleCreateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyRegionBlockRuleCreateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
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
      return "EmbyRegionBlockRuleCreateRequest(ruleType="
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
