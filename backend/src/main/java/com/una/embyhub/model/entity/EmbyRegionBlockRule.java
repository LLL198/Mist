package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("emby_region_block_rule")
public class EmbyRegionBlockRule extends BaseEntity implements Serializable {
   public static final String TYPE_COUNTRY = "COUNTRY";
   public static final String TYPE_PROVINCE = "PROVINCE";
   public static final String CUSTOM_RULE_PREFIX = "CUSTOM:";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("rule_code")
   private String ruleCode;
   @TableField("rule_type")
   private String ruleType;
   @TableField("country")
   private String country;
   @TableField("province")
   private String province;
   @TableField("display_name")
   private String displayName;
   @TableField("enabled")
   private Integer enabled;

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
   public Integer getEnabled() {
      return this.enabled;
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
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyRegionBlockRule(id="
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
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyRegionBlockRule other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
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
                           return this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyRegionBlockRule;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $ruleCode = this.getRuleCode();
      result = result * 59 + ($ruleCode == null ? 43 : $ruleCode.hashCode());
      Object $ruleType = this.getRuleType();
      result = result * 59 + ($ruleType == null ? 43 : $ruleType.hashCode());
      Object $country = this.getCountry();
      result = result * 59 + ($country == null ? 43 : $country.hashCode());
      Object $province = this.getProvince();
      result = result * 59 + ($province == null ? 43 : $province.hashCode());
      Object $displayName = this.getDisplayName();
      return result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
   }
}
