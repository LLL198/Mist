package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("emby_notify_data_details")
public class EmbyNotifyDataDetails extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_notify_data_id")
   private Long embyNotifyDataId;
   @TableField("episode_details")
   private String episodeDetails;
   @TableField("episode_info")
   private String episodeInfo;
   @TableField("`size`")
   private String size;
   @TableField("`status`")
   private Integer status;
   @TableField("emby_info_id")
   private Long embyInfoId;
   public static final String COL_ID = "id";
   public static final String COL_EMBY_NOTIFY_DATA_ID = "emby_notify_data_id";
   public static final String COL_EPISODE_DETAILS = "episode_details";
   public static final String COL_EPISODE_INFO = "episode_info";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_SIZE = "size";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";
   public static final String COL_STATUS = "status";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyNotifyDataId() {
      return this.embyNotifyDataId;
   }

   @Generated
   public String getEpisodeDetails() {
      return this.episodeDetails;
   }

   @Generated
   public String getEpisodeInfo() {
      return this.episodeInfo;
   }

   @Generated
   public String getSize() {
      return this.size;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
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
   public void setEmbyNotifyDataId(final Long embyNotifyDataId) {
      this.embyNotifyDataId = embyNotifyDataId;
   }

   @Generated
   public void setEpisodeDetails(final String episodeDetails) {
      this.episodeDetails = episodeDetails;
   }

   @Generated
   public void setEpisodeInfo(final String episodeInfo) {
      this.episodeInfo = episodeInfo;
   }

   @Generated
   public void setSize(final String size) {
      this.size = size;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyNotifyDataDetails(id="
         + this.getId()
         + ", embyNotifyDataId="
         + this.getEmbyNotifyDataId()
         + ", episodeDetails="
         + this.getEpisodeDetails()
         + ", episodeInfo="
         + this.getEpisodeInfo()
         + ", size="
         + this.getSize()
         + ", status="
         + this.getStatus()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyNotifyDataDetails other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyNotifyDataId = this.getEmbyNotifyDataId();
            Object other$embyNotifyDataId = other.getEmbyNotifyDataId();
            if (this$embyNotifyDataId == null ? other$embyNotifyDataId == null : this$embyNotifyDataId.equals(other$embyNotifyDataId)) {
               Object this$status = this.getStatus();
               Object other$status = other.getStatus();
               if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                  Object this$embyInfoId = this.getEmbyInfoId();
                  Object other$embyInfoId = other.getEmbyInfoId();
                  if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                     Object this$episodeDetails = this.getEpisodeDetails();
                     Object other$episodeDetails = other.getEpisodeDetails();
                     if (this$episodeDetails == null ? other$episodeDetails == null : this$episodeDetails.equals(other$episodeDetails)) {
                        Object this$episodeInfo = this.getEpisodeInfo();
                        Object other$episodeInfo = other.getEpisodeInfo();
                        if (this$episodeInfo == null ? other$episodeInfo == null : this$episodeInfo.equals(other$episodeInfo)) {
                           Object this$size = this.getSize();
                           Object other$size = other.getSize();
                           return this$size == null ? other$size == null : this$size.equals(other$size);
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
      return other instanceof EmbyNotifyDataDetails;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyNotifyDataId = this.getEmbyNotifyDataId();
      result = result * 59 + ($embyNotifyDataId == null ? 43 : $embyNotifyDataId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $episodeDetails = this.getEpisodeDetails();
      result = result * 59 + ($episodeDetails == null ? 43 : $episodeDetails.hashCode());
      Object $episodeInfo = this.getEpisodeInfo();
      result = result * 59 + ($episodeInfo == null ? 43 : $episodeInfo.hashCode());
      Object $size = this.getSize();
      return result * 59 + ($size == null ? 43 : $size.hashCode());
   }
}
