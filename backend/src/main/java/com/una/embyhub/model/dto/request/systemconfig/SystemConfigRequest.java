package com.una.embyhub.model.dto.request.systemconfig;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class SystemConfigRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.EQ
   )
   @Min(
      value = 0L,
      message = "配置启用状态无效"
   )
   @Max(
      value = 1L,
      message = "配置启用状态无效"
   )
   private Integer isEnabled;
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   @Size(
      max = 128,
      message = "配置说明筛选内容过长"
   )
   private String description;

   @Generated
   public Integer getIsEnabled() {
      return this.isEnabled;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public void setIsEnabled(final Integer isEnabled) {
      this.isEnabled = isEnabled;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SystemConfigRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$isEnabled = this.getIsEnabled();
         Object other$isEnabled = other.getIsEnabled();
         if (this$isEnabled == null ? other$isEnabled == null : this$isEnabled.equals(other$isEnabled)) {
            Object this$description = this.getDescription();
            Object other$description = other.getDescription();
            return this$description == null ? other$description == null : this$description.equals(other$description);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof SystemConfigRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $isEnabled = this.getIsEnabled();
      result = result * 59 + ($isEnabled == null ? 43 : $isEnabled.hashCode());
      Object $description = this.getDescription();
      return result * 59 + ($description == null ? 43 : $description.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SystemConfigRequest(isEnabled=" + this.getIsEnabled() + ", description=" + this.getDescription() + ")";
   }
}
