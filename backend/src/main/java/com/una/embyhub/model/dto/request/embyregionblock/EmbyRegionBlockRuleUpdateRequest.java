package com.una.embyhub.model.dto.request.embyregionblock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class EmbyRegionBlockRuleUpdateRequest implements Serializable {
   @NotBlank(
      message = "地区规则编码不能为空"
   )
   private String ruleCode;
   @NotNull(
      message = "启用状态不能为空"
   )
   private Boolean enabled;

   @Generated
   public String getRuleCode() {
      return this.ruleCode;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setRuleCode(final String ruleCode) {
      this.ruleCode = ruleCode;
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
      } else if (!(o instanceof EmbyRegionBlockRuleUpdateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$ruleCode = this.getRuleCode();
            Object other$ruleCode = other.getRuleCode();
            return this$ruleCode == null ? other$ruleCode == null : this$ruleCode.equals(other$ruleCode);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyRegionBlockRuleUpdateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $ruleCode = this.getRuleCode();
      return result * 59 + ($ruleCode == null ? 43 : $ruleCode.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyRegionBlockRuleUpdateRequest(ruleCode=" + this.getRuleCode() + ", enabled=" + this.getEnabled() + ")";
   }
}
