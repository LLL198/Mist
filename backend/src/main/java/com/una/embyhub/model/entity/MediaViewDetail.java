package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("media_view_detail")
public class MediaViewDetail extends BaseEntity implements Serializable {
   public static final String COL_CREATE_TIME = "create_time";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("media_id")
   private Long mediaId;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("view_time")
   private String viewTime;
   @TableField("device")
   private String device;
   public static final String COL_ID = "id";
   public static final String COL_MEDIA_ID = "media_id";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_VIEW_TIME = "view_time";
   public static final String COL_DEVICE = "device";
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
   public Long getMediaId() {
      return this.mediaId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getViewTime() {
      return this.viewTime;
   }

   @Generated
   public String getDevice() {
      return this.device;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setMediaId(final Long mediaId) {
      this.mediaId = mediaId;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setViewTime(final String viewTime) {
      this.viewTime = viewTime;
   }

   @Generated
   public void setDevice(final String device) {
      this.device = device;
   }

   @Generated
   @Override
   public String toString() {
      return "MediaViewDetail(id="
         + this.getId()
         + ", mediaId="
         + this.getMediaId()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", viewTime="
         + this.getViewTime()
         + ", device="
         + this.getDevice()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MediaViewDetail other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$mediaId = this.getMediaId();
            Object other$mediaId = other.getMediaId();
            if (this$mediaId == null ? other$mediaId == null : this$mediaId.equals(other$mediaId)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$embyUserName = this.getEmbyUserName();
                  Object other$embyUserName = other.getEmbyUserName();
                  if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                     Object this$viewTime = this.getViewTime();
                     Object other$viewTime = other.getViewTime();
                     if (this$viewTime == null ? other$viewTime == null : this$viewTime.equals(other$viewTime)) {
                        Object this$device = this.getDevice();
                        Object other$device = other.getDevice();
                        return this$device == null ? other$device == null : this$device.equals(other$device);
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
      return other instanceof MediaViewDetail;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $mediaId = this.getMediaId();
      result = result * 59 + ($mediaId == null ? 43 : $mediaId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $viewTime = this.getViewTime();
      result = result * 59 + ($viewTime == null ? 43 : $viewTime.hashCode());
      Object $device = this.getDevice();
      return result * 59 + ($device == null ? 43 : $device.hashCode());
   }
}
