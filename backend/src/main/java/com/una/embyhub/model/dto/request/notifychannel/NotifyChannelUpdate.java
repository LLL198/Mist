package com.una.embyhub.model.dto.request.notifychannel;

import java.io.Serializable;
import lombok.Generated;

public class NotifyChannelUpdate implements Serializable {
   private Long id;
   private String name;
   private String desc;
   private String iconType;
   private Integer enabled;
   private String customIcon;
   private String params;

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
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NotifyChannelUpdate other)) {
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
   protected boolean canEqual(final Object other) {
      return other instanceof NotifyChannelUpdate;
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

   @Generated
   @Override
   public String toString() {
      return "NotifyChannelUpdate(id="
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
}
