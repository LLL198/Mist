package com.una.embyhub.model.dto.response.simultaneous;

import com.diboot.core.binding.annotation.BindEntityList;
import com.diboot.core.binding.annotation.BindField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecordDetail;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class SimultaneousPlaybackRecordResponse implements Serializable {
   private Long id;
   private Long embyInfoId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;
   private String embyUserId;
   private String embyUserName;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date detectionTime;
   private Integer sessionCount;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date createDatetime;
   @BindEntityList(
      entity = SimultaneousPlaybackRecordDetail.class,
      condition = "this.id = record_id"
   )
   private List<SimultaneousPlaybackRecordDetailResponse> details;

   @Generated
   public static SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder builder() {
      return new SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
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
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public List<SimultaneousPlaybackRecordDetailResponse> getDetails() {
      return this.details;
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
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setDetectionTime(final Date detectionTime) {
      this.detectionTime = detectionTime;
   }

   @Generated
   public void setSessionCount(final Integer sessionCount) {
      this.sessionCount = sessionCount;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setDetails(final List<SimultaneousPlaybackRecordDetailResponse> details) {
      this.details = details;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SimultaneousPlaybackRecordResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
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
                  Object this$serverName = this.getServerName();
                  Object other$serverName = other.getServerName();
                  if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                     Object this$embyUserId = this.getEmbyUserId();
                     Object other$embyUserId = other.getEmbyUserId();
                     if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                        Object this$embyUserName = this.getEmbyUserName();
                        Object other$embyUserName = other.getEmbyUserName();
                        if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                           Object this$detectionTime = this.getDetectionTime();
                           Object other$detectionTime = other.getDetectionTime();
                           if (this$detectionTime == null ? other$detectionTime == null : this$detectionTime.equals(other$detectionTime)) {
                              Object this$createDatetime = this.getCreateDatetime();
                              Object other$createDatetime = other.getCreateDatetime();
                              if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                 Object this$details = this.getDetails();
                                 Object other$details = other.getDetails();
                                 return this$details == null ? other$details == null : this$details.equals(other$details);
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
      return other instanceof SimultaneousPlaybackRecordResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $sessionCount = this.getSessionCount();
      result = result * 59 + ($sessionCount == null ? 43 : $sessionCount.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $detectionTime = this.getDetectionTime();
      result = result * 59 + ($detectionTime == null ? 43 : $detectionTime.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $details = this.getDetails();
      return result * 59 + ($details == null ? 43 : $details.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SimultaneousPlaybackRecordResponse(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", detectionTime="
         + this.getDetectionTime()
         + ", sessionCount="
         + this.getSessionCount()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", details="
         + this.getDetails()
         + ")";
   }

   @Generated
   public SimultaneousPlaybackRecordResponse() {
   }

   @Generated
   public SimultaneousPlaybackRecordResponse(
      final Long id,
      final Long embyInfoId,
      final String serverName,
      final String embyUserId,
      final String embyUserName,
      final Date detectionTime,
      final Integer sessionCount,
      final Date createDatetime,
      final List<SimultaneousPlaybackRecordDetailResponse> details
   ) {
      this.id = id;
      this.embyInfoId = embyInfoId;
      this.serverName = serverName;
      this.embyUserId = embyUserId;
      this.embyUserName = embyUserName;
      this.detectionTime = detectionTime;
      this.sessionCount = sessionCount;
      this.createDatetime = createDatetime;
      this.details = details;
   }

   @Generated
   public static class SimultaneousPlaybackRecordResponseBuilder {
      @Generated
      private Long id;
      @Generated
      private Long embyInfoId;
      @Generated
      private String serverName;
      @Generated
      private String embyUserId;
      @Generated
      private String embyUserName;
      @Generated
      private Date detectionTime;
      @Generated
      private Integer sessionCount;
      @Generated
      private Date createDatetime;
      @Generated
      private List<SimultaneousPlaybackRecordDetailResponse> details;

      @Generated
      SimultaneousPlaybackRecordResponseBuilder() {
      }

      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder embyInfoId(final Long embyInfoId) {
         this.embyInfoId = embyInfoId;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder serverName(final String serverName) {
         this.serverName = serverName;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder embyUserId(final String embyUserId) {
         this.embyUserId = embyUserId;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder embyUserName(final String embyUserName) {
         this.embyUserName = embyUserName;
         return this;
      }

      @JsonFormat(
         pattern = "yyyy-MM-dd HH:mm:ss",
         timezone = "GMT+8"
      )
      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder detectionTime(final Date detectionTime) {
         this.detectionTime = detectionTime;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder sessionCount(final Integer sessionCount) {
         this.sessionCount = sessionCount;
         return this;
      }

      @JsonFormat(
         pattern = "yyyy-MM-dd HH:mm:ss",
         timezone = "GMT+8"
      )
      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder createDatetime(final Date createDatetime) {
         this.createDatetime = createDatetime;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder details(final List<SimultaneousPlaybackRecordDetailResponse> details) {
         this.details = details;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordResponse build() {
         return new SimultaneousPlaybackRecordResponse(
            this.id,
            this.embyInfoId,
            this.serverName,
            this.embyUserId,
            this.embyUserName,
            this.detectionTime,
            this.sessionCount,
            this.createDatetime,
            this.details
         );
      }

      @Generated
      @Override
      public String toString() {
         return "SimultaneousPlaybackRecordResponse.SimultaneousPlaybackRecordResponseBuilder(id="
            + this.id
            + ", embyInfoId="
            + this.embyInfoId
            + ", serverName="
            + this.serverName
            + ", embyUserId="
            + this.embyUserId
            + ", embyUserName="
            + this.embyUserName
            + ", detectionTime="
            + this.detectionTime
            + ", sessionCount="
            + this.sessionCount
            + ", createDatetime="
            + this.createDatetime
            + ", details="
            + this.details
            + ")";
      }
   }
}
