package com.una.embyhub.model.dto.response.systemconfig;

import java.io.Serializable;
import lombok.Generated;

public class SystemConfigResponse implements Serializable {
   private Long id;
   private String configKey;
   private String configValue;
   private Integer isEnabled;
   private String description;
   private Integer isUpdate;
   private String name;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getConfigKey() {
      return this.configKey;
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
   public String getDescription() {
      return this.description;
   }

   @Generated
   public Integer getIsUpdate() {
      return this.isUpdate;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setConfigKey(final String configKey) {
      this.configKey = configKey;
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
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setIsUpdate(final Integer isUpdate) {
      this.isUpdate = isUpdate;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SystemConfigResponse other)) {
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
                  Object this$configKey = this.getConfigKey();
                  Object other$configKey = other.getConfigKey();
                  if (this$configKey == null ? other$configKey == null : this$configKey.equals(other$configKey)) {
                     Object this$configValue = this.getConfigValue();
                     Object other$configValue = other.getConfigValue();
                     if (this$configValue == null ? other$configValue == null : this$configValue.equals(other$configValue)) {
                        Object this$description = this.getDescription();
                        Object other$description = other.getDescription();
                        if (this$description == null ? other$description == null : this$description.equals(other$description)) {
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           return this$name == null ? other$name == null : this$name.equals(other$name);
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
      return other instanceof SystemConfigResponse;
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
      Object $configKey = this.getConfigKey();
      result = result * 59 + ($configKey == null ? 43 : $configKey.hashCode());
      Object $configValue = this.getConfigValue();
      result = result * 59 + ($configValue == null ? 43 : $configValue.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
      Object $name = this.getName();
      return result * 59 + ($name == null ? 43 : $name.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SystemConfigResponse(id="
         + this.getId()
         + ", configKey="
         + this.getConfigKey()
         + ", configValue="
         + this.getConfigValue()
         + ", isEnabled="
         + this.getIsEnabled()
         + ", description="
         + this.getDescription()
         + ", isUpdate="
         + this.getIsUpdate()
         + ", name="
         + this.getName()
         + ")";
   }
}
