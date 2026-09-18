package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("play_records")
public class PlayRecords extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_user_id")
   private String embyUserId;
   @TableField("play_date")
   private Date playDate;
   @TableField("record_type")
   private String recordType;
   @TableField("device")
   private String device;
   @TableField("content")
   private String content;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("emby_info_id")
   private Long embyInfoId;
   public static final String COL_ID = "id";
   public static final String COL_EMBY_USER_ID = "emby_user_id";
   public static final String COL_PLAY_DATE = "play_date";
   public static final String COL_RECORD_TYPE = "record_type";
   public static final String COL_DEVICE = "device";
   public static final String COL_CONTENT = "content";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public String toString() {
      return "PlayRecords(id="
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
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlayRecords other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
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
                              return this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PlayRecords;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
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
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }
}
