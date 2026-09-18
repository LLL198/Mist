package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("notify_channel")
public class NotifyChannel extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("`name`")
   private String name;
   @TableField("`desc`")
   private String desc;
   @TableField("icon_type")
   private String iconType;
   @TableField("enabled")
   private Integer enabled;
   @TableField("custom_icon")
   private String customIcon;
   @TableField("params")
   private String params;
   public static final String COL_ID = "id";
   public static final String COL_NAME = "name";
   public static final String COL_DESC = "desc";
   public static final String COL_ICON_TYPE = "icon_type";
   public static final String COL_ENABLED = "enabled";
   public static final String COL_CUSTOM_ICON = "custom_icon";
   public static final String COL_PARAMS = "params";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getDesc() {
      return this.desc;
   }

   @Generated
   public String getIconType() {
      return this.iconType;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getCustomIcon() {
      return this.customIcon;
   }

   @Generated
   public String getParams() {
      return this.params;
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
   public void setDesc(final String desc) {
      this.desc = desc;
   }

   @Generated
   public void setIconType(final String iconType) {
      this.iconType = iconType;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setCustomIcon(final String customIcon) {
      this.customIcon = customIcon;
   }

   @Generated
   public void setParams(final String params) {
      this.params = params;
   }

   @Generated
   @Override
   public String toString() {
      return "NotifyChannel(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", desc="
         + this.getDesc()
         + ", iconType="
         + this.getIconType()
         + ", enabled="
         + this.getEnabled()
         + ", customIcon="
         + this.getCustomIcon()
         + ", params="
         + this.getParams()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NotifyChannel other)) {
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
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                  Object this$desc = this.getDesc();
                  Object other$desc = other.getDesc();
                  if (this$desc == null ? other$desc == null : this$desc.equals(other$desc)) {
                     Object this$iconType = this.getIconType();
                     Object other$iconType = other.getIconType();
                     if (this$iconType == null ? other$iconType == null : this$iconType.equals(other$iconType)) {
                        Object this$customIcon = this.getCustomIcon();
                        Object other$customIcon = other.getCustomIcon();
                        if (this$customIcon == null ? other$customIcon == null : this$customIcon.equals(other$customIcon)) {
                           Object this$params = this.getParams();
                           Object other$params = other.getParams();
                           return this$params == null ? other$params == null : this$params.equals(other$params);
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
      return other instanceof NotifyChannel;
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
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $desc = this.getDesc();
      result = result * 59 + ($desc == null ? 43 : $desc.hashCode());
      Object $iconType = this.getIconType();
      result = result * 59 + ($iconType == null ? 43 : $iconType.hashCode());
      Object $customIcon = this.getCustomIcon();
      result = result * 59 + ($customIcon == null ? 43 : $customIcon.hashCode());
      Object $params = this.getParams();
      return result * 59 + ($params == null ? 43 : $params.hashCode());
   }
}
