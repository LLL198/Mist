package com.una.embyhub.model.dto.request.systemconfig;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class SystemConfigUpdate implements Serializable {
   @NotNull(
      message = "配置 ID 不能为空"
   )
   private Long id;
   private String configValue;
   @NotNull(
      message = "配置启用状态不能为空"
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
   private Integer isUpdate;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getConfigValue() {
      return this.configValue;
   }

   @Generated
   public Integer getIsEnabled() {
      return this.isEnabled;
   }

   @Generated
   public Integer getIsUpdate() {
      return this.isUpdate;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setConfigValue(final String configValue) {
      this.configValue = configValue;
   }

   @Generated
   public void setIsEnabled(final Integer isEnabled) {
      this.isEnabled = isEnabled;
   }

   @Generated
   public void setIsUpdate(final Integer isUpdate) {
      this.isUpdate = isUpdate;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SystemConfigUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$isEnabled = this.getIsEnabled();
            Object other$isEnabled = other.getIsEnabled();
            if (this$isEnabled == null ? other$isEnabled == null : this$isEnabled.equals(other$isEnabled)) {
               Object this$isUpdate = this.getIsUpdate();
               Object other$isUpdate = other.getIsUpdate();
               if (this$isUpdate == null ? other$isUpdate == null : this$isUpdate.equals(other$isUpdate)) {
                  Object this$configValue = this.getConfigValue();
                  Object other$configValue = other.getConfigValue();
                  return this$configValue == null ? other$configValue == null : this$configValue.equals(other$configValue);
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
      return other instanceof SystemConfigUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $isEnabled = this.getIsEnabled();
      result = result * 59 + ($isEnabled == null ? 43 : $isEnabled.hashCode());
      Object $isUpdate = this.getIsUpdate();
      result = result * 59 + ($isUpdate == null ? 43 : $isUpdate.hashCode());
      Object $configValue = this.getConfigValue();
      return result * 59 + ($configValue == null ? 43 : $configValue.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SystemConfigUpdate(id="
         + this.getId()
         + ", configValue="
         + this.getConfigValue()
         + ", isEnabled="
         + this.getIsEnabled()
         + ", isUpdate="
         + this.getIsUpdate()
         + ")";
   }
}
