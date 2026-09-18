package com.una.embyhub.model.dto.request.simultaneous;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class SimultaneousPlaybackRecordRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.BETWEEN_BEGIN,
      column = "detection_time"
   )
   private Date detectionTimeStart;
   @BindQuery(
      comparison = Comparison.BETWEEN_END,
      column = "detection_time"
   )
   private Date detectionTimeEnd;
   @BindQuery(
      comparison = Comparison.CONTAINS,
      column = "emby_user_name"
   )
   private String embyUserName;
   @BindQuery(
      comparison = Comparison.EQ,
      column = "emby_info_id"
   )
   private Long embyInfoId;
   @BindQuery(
      ignore = true
   )
   private String content;

   @Generated
   public Date getDetectionTimeStart() {
      return this.detectionTimeStart;
   }

   @Generated
   public Date getDetectionTimeEnd() {
      return this.detectionTimeEnd;
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
   public String getContent() {
      return this.content;
   }

   @Generated
   public void setDetectionTimeStart(final Date detectionTimeStart) {
      this.detectionTimeStart = detectionTimeStart;
   }

   @Generated
   public void setDetectionTimeEnd(final Date detectionTimeEnd) {
      this.detectionTimeEnd = detectionTimeEnd;
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
   public void setContent(final String content) {
      this.content = content;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SimultaneousPlaybackRecordRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$detectionTimeStart = this.getDetectionTimeStart();
            Object other$detectionTimeStart = other.getDetectionTimeStart();
            if (this$detectionTimeStart == null ? other$detectionTimeStart == null : this$detectionTimeStart.equals(other$detectionTimeStart)) {
               Object this$detectionTimeEnd = this.getDetectionTimeEnd();
               Object other$detectionTimeEnd = other.getDetectionTimeEnd();
               if (this$detectionTimeEnd == null ? other$detectionTimeEnd == null : this$detectionTimeEnd.equals(other$detectionTimeEnd)) {
                  Object this$embyUserName = this.getEmbyUserName();
                  Object other$embyUserName = other.getEmbyUserName();
                  if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                     Object this$content = this.getContent();
                     Object other$content = other.getContent();
                     return this$content == null ? other$content == null : this$content.equals(other$content);
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
      return other instanceof SimultaneousPlaybackRecordRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $detectionTimeStart = this.getDetectionTimeStart();
      result = result * 59 + ($detectionTimeStart == null ? 43 : $detectionTimeStart.hashCode());
      Object $detectionTimeEnd = this.getDetectionTimeEnd();
      result = result * 59 + ($detectionTimeEnd == null ? 43 : $detectionTimeEnd.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $content = this.getContent();
      return result * 59 + ($content == null ? 43 : $content.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SimultaneousPlaybackRecordRequest(detectionTimeStart="
         + this.getDetectionTimeStart()
         + ", detectionTimeEnd="
         + this.getDetectionTimeEnd()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", content="
         + this.getContent()
         + ")";
   }
}
