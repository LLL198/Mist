package com.una.embyhub.model.dto.response.embyregionblock;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyRegionBlockRuleResponse implements Serializable {
   private Long id;
   private String ruleCode;
   private String ruleType;
   private String country;
   private String province;
   private String displayName;
   private Boolean enabled;
   private Boolean persisted;
   private Boolean custom;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date createDatetime;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date updateDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getRuleCode() {
      return this.ruleCode;
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
   public String getDisplayName() {
      return this.displayName;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public Boolean getPersisted() {
      return this.persisted;
   }

   @Generated
   public Boolean getCustom() {
      return this.custom;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setRuleCode(final String ruleCode) {
      this.ruleCode = ruleCode;
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
   public void setDisplayName(final String displayName) {
      this.displayName = displayName;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setPersisted(final Boolean persisted) {
      this.persisted = persisted;
   }

   @Generated
   public void setCustom(final Boolean custom) {
      this.custom = custom;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyRegionBlockRuleResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$persisted = this.getPersisted();
               Object other$persisted = other.getPersisted();
               if (this$persisted == null ? other$persisted == null : this$persisted.equals(other$persisted)) {
                  Object this$custom = this.getCustom();
                  Object other$custom = other.getCustom();
                  if (this$custom == null ? other$custom == null : this$custom.equals(other$custom)) {
                     Object this$ruleCode = this.getRuleCode();
                     Object other$ruleCode = other.getRuleCode();
                     if (this$ruleCode == null ? other$ruleCode == null : this$ruleCode.equals(other$ruleCode)) {
                        Object this$ruleType = this.getRuleType();
                        Object other$ruleType = other.getRuleType();
                        if (this$ruleType == null ? other$ruleType == null : this$ruleType.equals(other$ruleType)) {
                           Object this$country = this.getCountry();
                           Object other$country = other.getCountry();
                           if (this$country == null ? other$country == null : this$country.equals(other$country)) {
                              Object this$province = this.getProvince();
                              Object other$province = other.getProvince();
                              if (this$province == null ? other$province == null : this$province.equals(other$province)) {
                                 Object this$displayName = this.getDisplayName();
                                 Object other$displayName = other.getDisplayName();
                                 if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                                    Object this$createDatetime = this.getCreateDatetime();
                                    Object other$createDatetime = other.getCreateDatetime();
                                    if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                       Object this$updateDatetime = this.getUpdateDatetime();
                                       Object other$updateDatetime = other.getUpdateDatetime();
                                       return this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime);
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
      return other instanceof EmbyRegionBlockRuleResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $persisted = this.getPersisted();
      result = result * 59 + ($persisted == null ? 43 : $persisted.hashCode());
      Object $custom = this.getCustom();
      result = result * 59 + ($custom == null ? 43 : $custom.hashCode());
      Object $ruleCode = this.getRuleCode();
      result = result * 59 + ($ruleCode == null ? 43 : $ruleCode.hashCode());
      Object $ruleType = this.getRuleType();
      result = result * 59 + ($ruleType == null ? 43 : $ruleType.hashCode());
      Object $country = this.getCountry();
      result = result * 59 + ($country == null ? 43 : $country.hashCode());
      Object $province = this.getProvince();
      result = result * 59 + ($province == null ? 43 : $province.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyRegionBlockRuleResponse(id="
         + this.getId()
         + ", ruleCode="
         + this.getRuleCode()
         + ", ruleType="
         + this.getRuleType()
         + ", country="
         + this.getCountry()
         + ", province="
         + this.getProvince()
         + ", displayName="
         + this.getDisplayName()
         + ", enabled="
         + this.getEnabled()
         + ", persisted="
         + this.getPersisted()
         + ", custom="
         + this.getCustom()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
