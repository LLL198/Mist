package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("system_config")
public class SystemConfig extends BaseEntity implements Serializable {
   public static final String COL_ID = "id";
   public static final String COL_CONFIG_KEY = "config_key";
   public static final String COL_CONFIG_VALUE = "config_value";
   public static final String COL_IS_ENABLED = "is_enabled";
   public static final String COL_DESCRIPTION = "description";
   public static final String COL_IS_UPDATE = "is_update";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("`name`")
   private String name;
   @TableField("config_key")
   private String configKey;
   @TableField("config_value")
   private String configValue;
   @TableField("is_enabled")
   private Integer isEnabled;
   @TableField("description")
   private String description;
   @TableField("is_update")
   private Integer isUpdate;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
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
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
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
   @Override
   public String toString() {
      return "SystemConfig(id="
         + this.getId()
         + ", name="
         + this.getName()
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
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SystemConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
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
                  Object this$name = this.getName();
                  Object other$name = other.getName();
                  if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                     Object this$configKey = this.getConfigKey();
                     Object other$configKey = other.getConfigKey();
                     if (this$configKey == null ? other$configKey == null : this$configKey.equals(other$configKey)) {
                        Object this$configValue = this.getConfigValue();
                        Object other$configValue = other.getConfigValue();
                        if (this$configValue == null ? other$configValue == null : this$configValue.equals(other$configValue)) {
                           Object this$description = this.getDescription();
                           Object other$description = other.getDescription();
                           return this$description == null ? other$description == null : this$description.equals(other$description);
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
      return other instanceof SystemConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $isEnabled = this.getIsEnabled();
      result = result * 59 + ($isEnabled == null ? 43 : $isEnabled.hashCode());
      Object $isUpdate = this.getIsUpdate();
      result = result * 59 + ($isUpdate == null ? 43 : $isUpdate.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $configKey = this.getConfigKey();
      result = result * 59 + ($configKey == null ? 43 : $configKey.hashCode());
      Object $configValue = this.getConfigValue();
      result = result * 59 + ($configValue == null ? 43 : $configValue.hashCode());
      Object $description = this.getDescription();
      return result * 59 + ($description == null ? 43 : $description.hashCode());
   }
}
