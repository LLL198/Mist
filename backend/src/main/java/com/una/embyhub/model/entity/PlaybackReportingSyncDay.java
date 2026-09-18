package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("playback_reporting_sync_day")
public class PlaybackReportingSyncDay extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("play_day")
   private Date playDay;
   @TableField("sync_status")
   private Integer syncStatus;
   @TableField("record_count")
   private Integer recordCount;
   @TableField("synced_at")
   private Date syncedAt;
   @TableField("error_message")
   private String errorMessage;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Date getPlayDay() {
      return this.playDay;
   }

   @Generated
   public Integer getSyncStatus() {
      return this.syncStatus;
   }

   @Generated
   public Integer getRecordCount() {
      return this.recordCount;
   }

   @Generated
   public Date getSyncedAt() {
      return this.syncedAt;
   }

   @Generated
   public String getErrorMessage() {
      return this.errorMessage;
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
   public void setPlayDay(final Date playDay) {
      this.playDay = playDay;
   }

   @Generated
   public void setSyncStatus(final Integer syncStatus) {
      this.syncStatus = syncStatus;
   }

   @Generated
   public void setRecordCount(final Integer recordCount) {
      this.recordCount = recordCount;
   }

   @Generated
   public void setSyncedAt(final Date syncedAt) {
      this.syncedAt = syncedAt;
   }

   @Generated
   public void setErrorMessage(final String errorMessage) {
      this.errorMessage = errorMessage;
   }

   @Generated
   @Override
   public String toString() {
      return "PlaybackReportingSyncDay(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", playDay="
         + this.getPlayDay()
         + ", syncStatus="
         + this.getSyncStatus()
         + ", recordCount="
         + this.getRecordCount()
         + ", syncedAt="
         + this.getSyncedAt()
         + ", errorMessage="
         + this.getErrorMessage()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlaybackReportingSyncDay other)) {
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
               Object this$syncStatus = this.getSyncStatus();
               Object other$syncStatus = other.getSyncStatus();
               if (this$syncStatus == null ? other$syncStatus == null : this$syncStatus.equals(other$syncStatus)) {
                  Object this$recordCount = this.getRecordCount();
                  Object other$recordCount = other.getRecordCount();
                  if (this$recordCount == null ? other$recordCount == null : this$recordCount.equals(other$recordCount)) {
                     Object this$playDay = this.getPlayDay();
                     Object other$playDay = other.getPlayDay();
                     if (this$playDay == null ? other$playDay == null : this$playDay.equals(other$playDay)) {
                        Object this$syncedAt = this.getSyncedAt();
                        Object other$syncedAt = other.getSyncedAt();
                        if (this$syncedAt == null ? other$syncedAt == null : this$syncedAt.equals(other$syncedAt)) {
                           Object this$errorMessage = this.getErrorMessage();
                           Object other$errorMessage = other.getErrorMessage();
                           return this$errorMessage == null ? other$errorMessage == null : this$errorMessage.equals(other$errorMessage);
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
      return other instanceof PlaybackReportingSyncDay;
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
      Object $syncStatus = this.getSyncStatus();
      result = result * 59 + ($syncStatus == null ? 43 : $syncStatus.hashCode());
      Object $recordCount = this.getRecordCount();
      result = result * 59 + ($recordCount == null ? 43 : $recordCount.hashCode());
      Object $playDay = this.getPlayDay();
      result = result * 59 + ($playDay == null ? 43 : $playDay.hashCode());
      Object $syncedAt = this.getSyncedAt();
      result = result * 59 + ($syncedAt == null ? 43 : $syncedAt.hashCode());
      Object $errorMessage = this.getErrorMessage();
      return result * 59 + ($errorMessage == null ? 43 : $errorMessage.hashCode());
   }
}
