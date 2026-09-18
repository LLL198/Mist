package com.una.embyhub.model.dto.request.systemconfig;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Generated;

public class SystemConfigEnabledRequest {
   @NotBlank(
      message = "配置键名不能为空"
   )
   @Size(
      max = 64,
      message = "配置键名过长"
   )
   private String configKey;

   @Generated
   public String getConfigKey() {
      return this.configKey;
   }

   @Generated
   public void setConfigKey(final String configKey) {
      this.configKey = configKey;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SystemConfigEnabledRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$configKey = this.getConfigKey();
         Object other$configKey = other.getConfigKey();
         return this$configKey == null ? other$configKey == null : this$configKey.equals(other$configKey);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof SystemConfigEnabledRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $configKey = this.getConfigKey();
      return result * 59 + ($configKey == null ? 43 : $configKey.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SystemConfigEnabledRequest(configKey=" + this.getConfigKey() + ")";
   }
}
