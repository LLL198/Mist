package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("simultaneous_playback_record")
public class SimultaneousPlaybackRecord extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("emby_user_id")
   private String embyUserId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("detection_time")
   private Date detectionTime;
   @TableField("session_count")
   private Integer sessionCount;
   public static final String COL_ID = "id";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_EMBY_USER_ID = "emby_user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_DETECTION_TIME = "detection_time";
   public static final String COL_SESSION_COUNT = "session_count";
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Date getDetectionTime() {
      return this.detectionTime;
   }

   @Generated
   public Integer getSessionCount() {
      return this.sessionCount;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setDetectionTime(final Date detectionTime) {
      this.detectionTime = detectionTime;
   }

   @Generated
   public void setSessionCount(final Integer sessionCount) {
      this.sessionCount = sessionCount;
   }

   @Generated
   @Override
   public String toString() {
      return "SimultaneousPlaybackRecord(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", detectionTime="
         + this.getDetectionTime()
         + ", sessionCount="
         + this.getSessionCount()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SimultaneousPlaybackRecord other)) {
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
               Object this$sessionCount = this.getSessionCount();
               Object other$sessionCount = other.getSessionCount();
               if (this$sessionCount == null ? other$sessionCount == null : this$sessionCount.equals(other$sessionCount)) {
                  Object this$embyUserId = this.getEmbyUserId();
                  Object other$embyUserId = other.getEmbyUserId();
                  if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                     Object this$embyUserName = this.getEmbyUserName();
                     Object other$embyUserName = other.getEmbyUserName();
                     if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                        Object this$detectionTime = this.getDetectionTime();
                        Object other$detectionTime = other.getDetectionTime();
                        return this$detectionTime == null ? other$detectionTime == null : this$detectionTime.equals(other$detectionTime);
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
      return other instanceof SimultaneousPlaybackRecord;
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
      Object $sessionCount = this.getSessionCount();
      result = result * 59 + ($sessionCount == null ? 43 : $sessionCount.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $detectionTime = this.getDetectionTime();
      return result * 59 + ($detectionTime == null ? 43 : $detectionTime.hashCode());
   }
}
