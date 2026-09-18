package com.una.embyhub.model.dto.request.systemconfig;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class SystemConfigSave implements Serializable {
   private Long id;
   private String configKey;
   private String configValue;
   private Integer isEnabled;
   private String description;
   private Integer isUpdate;
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
      } else if (!(o instanceof SystemConfigSave other)) {
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
                  Object this$updateUserId = this.getUpdateUserId();
                  Object other$updateUserId = other.getUpdateUserId();
                  if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                     Object this$createUserId = this.getCreateUserId();
                     Object other$createUserId = other.getCreateUserId();
                     if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                        Object this$delFlag = this.getDelFlag();
                        Object other$delFlag = other.getDelFlag();
                        if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                           Object this$configKey = this.getConfigKey();
                           Object other$configKey = other.getConfigKey();
                           if (this$configKey == null ? other$configKey == null : this$configKey.equals(other$configKey)) {
                              Object this$configValue = this.getConfigValue();
                              Object other$configValue = other.getConfigValue();
                              if (this$configValue == null ? other$configValue == null : this$configValue.equals(other$configValue)) {
                                 Object this$description = this.getDescription();
                                 Object other$description = other.getDescription();
                                 if (this$description == null ? other$description == null : this$description.equals(other$description)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof SystemConfigSave;
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
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $configKey = this.getConfigKey();
      result = result * 59 + ($configKey == null ? 43 : $configKey.hashCode());
      Object $configValue = this.getConfigValue();
      result = result * 59 + ($configValue == null ? 43 : $configValue.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
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
      return "SystemConfigSave(id="
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
