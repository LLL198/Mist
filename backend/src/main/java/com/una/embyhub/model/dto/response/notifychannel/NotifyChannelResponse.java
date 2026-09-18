package com.una.embyhub.model.dto.response.notifychannel;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class NotifyChannelResponse implements Serializable {
   private Long id;
   private String name;
   private String desc;
   private String iconType;
   private Integer enabled;
   private String customIcon;
   private String params;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;

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
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public String getCreateUserName() {
      return this.createUserName;
   }

   @Generated
   public String getUpdateUserName() {
      return this.updateUserName;
   }

   @Generated
   public Long getUpdateUserId() {
      return this.updateUserId;
   }

   @Generated
   public Long getCreateUserId() {
      return this.createUserId;
   }

   @Generated
   public Integer getDelFlag() {
      return this.delFlag;
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
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   public void setCreateUserName(final String createUserName) {
      this.createUserName = createUserName;
   }

   @Generated
   public void setUpdateUserName(final String updateUserName) {
      this.updateUserName = updateUserName;
   }

   @Generated
   public void setUpdateUserId(final Long updateUserId) {
      this.updateUserId = updateUserId;
   }

   @Generated
   public void setCreateUserId(final Long createUserId) {
      this.createUserId = createUserId;
   }

   @Generated
   public void setDelFlag(final Integer delFlag) {
      this.delFlag = delFlag;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NotifyChannelResponse other)) {
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
               Object this$updateUserId = this.getUpdateUserId();
               Object other$updateUserId = other.getUpdateUserId();
               if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                  Object this$createUserId = this.getCreateUserId();
                  Object other$createUserId = other.getCreateUserId();
                  if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                     Object this$delFlag = this.getDelFlag();
                     Object other$delFlag = other.getDelFlag();
                     if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
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
                                    if (this$params == null ? other$params == null : this$params.equals(other$params)) {
                                       Object this$createDatetime = this.getCreateDatetime();
                                       Object other$createDatetime = other.getCreateDatetime();
                                       if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                          Object this$updateDatetime = this.getUpdateDatetime();
                                          Object other$updateDatetime = other.getUpdateDatetime();
                                          if (this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime)) {
                                             Object this$createUserName = this.getCreateUserName();
                                             Object other$createUserName = other.getCreateUserName();
                                             if (this$createUserName == null ? other$createUserName == null : this$createUserName.equals(other$createUserName)) {
                                                Object this$updateUserName = this.getUpdateUserName();
                                                Object other$updateUserName = other.getUpdateUserName();
                                                return this$updateUserName == null
                                                   ? other$updateUserName == null
                                                   : this$updateUserName.equals(other$updateUserName);
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
      return other instanceof NotifyChannelResponse;
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
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $desc = this.getDesc();
      result = result * 59 + ($desc == null ? 43 : $desc.hashCode());
      Object $iconType = this.getIconType();
      result = result * 59 + ($iconType == null ? 43 : $iconType.hashCode());
      Object $customIcon = this.getCustomIcon();
      result = result * 59 + ($customIcon == null ? 43 : $customIcon.hashCode());
      Object $params = this.getParams();
      result = result * 59 + ($params == null ? 43 : $params.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      return result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "NotifyChannelResponse(id="
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
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", updateUserName="
         + this.getUpdateUserName()
         + ", updateUserId="
         + this.getUpdateUserId()
         + ", createUserId="
         + this.getCreateUserId()
         + ", delFlag="
         + this.getDelFlag()
         + ")";
   }
}
