package com.una.embyhub.model.dto.request.playrecords;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PlayRecordsUpdate implements Serializable {
   private Long id;
   private String embyUserId;
   private Date playDate;
   private String recordType;
   private String device;
   private String content;
   private String embyUserName;
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
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public Date getPlayDate() {
      return this.playDate;
   }

   @Generated
   public String getRecordType() {
      return this.recordType;
   }

   @Generated
   public String getDevice() {
      return this.device;
   }

   @Generated
   public String getContent() {
      return this.content;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
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
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setPlayDate(final Date playDate) {
      this.playDate = playDate;
   }

   @Generated
   public void setRecordType(final String recordType) {
      this.recordType = recordType;
   }

   @Generated
   public void setDevice(final String device) {
      this.device = device;
   }

   @Generated
   public void setContent(final String content) {
      this.content = content;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
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
      } else if (!(o instanceof PlayRecordsUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$updateUserId = this.getUpdateUserId();
            Object other$updateUserId = other.getUpdateUserId();
            if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
               Object this$createUserId = this.getCreateUserId();
               Object other$createUserId = other.getCreateUserId();
               if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                  Object this$delFlag = this.getDelFlag();
                  Object other$delFlag = other.getDelFlag();
                  if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                     Object this$embyUserId = this.getEmbyUserId();
                     Object other$embyUserId = other.getEmbyUserId();
                     if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                        Object this$playDate = this.getPlayDate();
                        Object other$playDate = other.getPlayDate();
                        if (this$playDate == null ? other$playDate == null : this$playDate.equals(other$playDate)) {
                           Object this$recordType = this.getRecordType();
                           Object other$recordType = other.getRecordType();
                           if (this$recordType == null ? other$recordType == null : this$recordType.equals(other$recordType)) {
                              Object this$device = this.getDevice();
                              Object other$device = other.getDevice();
                              if (this$device == null ? other$device == null : this$device.equals(other$device)) {
                                 Object this$content = this.getContent();
                                 Object other$content = other.getContent();
                                 if (this$content == null ? other$content == null : this$content.equals(other$content)) {
                                    Object this$embyUserName = this.getEmbyUserName();
                                    Object other$embyUserName = other.getEmbyUserName();
                                    if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
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
      return other instanceof PlayRecordsUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $playDate = this.getPlayDate();
      result = result * 59 + ($playDate == null ? 43 : $playDate.hashCode());
      Object $recordType = this.getRecordType();
      result = result * 59 + ($recordType == null ? 43 : $recordType.hashCode());
      Object $device = this.getDevice();
      result = result * 59 + ($device == null ? 43 : $device.hashCode());
      Object $content = this.getContent();
      result = result * 59 + ($content == null ? 43 : $content.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
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
      return "PlayRecordsUpdate(id="
         + this.getId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", playDate="
         + this.getPlayDate()
         + ", recordType="
         + this.getRecordType()
         + ", device="
         + this.getDevice()
         + ", content="
         + this.getContent()
         + ", embyUserName="
         + this.getEmbyUserName()
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
