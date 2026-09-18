package com.una.embyhub.model.dto.response.playrecords;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PlaySessionDailyStatResponse implements Serializable {
   private Long embyUserId;
   private String embyUserName;
   private String content;
   private String device;
   private String playDay;
   private Date firstPlayStartTime;
   private Date lastPlayEndTime;
   private Integer totalSeconds;
   private String totalPlayTime;

   @Generated
   public Long getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getContent() {
      return this.content;
   }

   @Generated
   public String getDevice() {
      return this.device;
   }

   @Generated
   public String getPlayDay() {
      return this.playDay;
   }

   @Generated
   public Date getFirstPlayStartTime() {
      return this.firstPlayStartTime;
   }

   @Generated
   public Date getLastPlayEndTime() {
      return this.lastPlayEndTime;
   }

   @Generated
   public Integer getTotalSeconds() {
      return this.totalSeconds;
   }

   @Generated
   public String getTotalPlayTime() {
      return this.totalPlayTime;
   }

   @Generated
   public void setEmbyUserId(final Long embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setContent(final String content) {
      this.content = content;
   }

   @Generated
   public void setDevice(final String device) {
      this.device = device;
   }

   @Generated
   public void setPlayDay(final String playDay) {
      this.playDay = playDay;
   }

   @Generated
   public void setFirstPlayStartTime(final Date firstPlayStartTime) {
      this.firstPlayStartTime = firstPlayStartTime;
   }

   @Generated
   public void setLastPlayEndTime(final Date lastPlayEndTime) {
      this.lastPlayEndTime = lastPlayEndTime;
   }

   @Generated
   public void setTotalSeconds(final Integer totalSeconds) {
      this.totalSeconds = totalSeconds;
   }

   @Generated
   public void setTotalPlayTime(final String totalPlayTime) {
      this.totalPlayTime = totalPlayTime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlaySessionDailyStatResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyUserId = this.getEmbyUserId();
         Object other$embyUserId = other.getEmbyUserId();
         if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
            Object this$totalSeconds = this.getTotalSeconds();
            Object other$totalSeconds = other.getTotalSeconds();
            if (this$totalSeconds == null ? other$totalSeconds == null : this$totalSeconds.equals(other$totalSeconds)) {
               Object this$embyUserName = this.getEmbyUserName();
               Object other$embyUserName = other.getEmbyUserName();
               if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                  Object this$content = this.getContent();
                  Object other$content = other.getContent();
                  if (this$content == null ? other$content == null : this$content.equals(other$content)) {
                     Object this$device = this.getDevice();
                     Object other$device = other.getDevice();
                     if (this$device == null ? other$device == null : this$device.equals(other$device)) {
                        Object this$playDay = this.getPlayDay();
                        Object other$playDay = other.getPlayDay();
                        if (this$playDay == null ? other$playDay == null : this$playDay.equals(other$playDay)) {
                           Object this$firstPlayStartTime = this.getFirstPlayStartTime();
                           Object other$firstPlayStartTime = other.getFirstPlayStartTime();
                           if (this$firstPlayStartTime == null ? other$firstPlayStartTime == null : this$firstPlayStartTime.equals(other$firstPlayStartTime)) {
                              Object this$lastPlayEndTime = this.getLastPlayEndTime();
                              Object other$lastPlayEndTime = other.getLastPlayEndTime();
                              if (this$lastPlayEndTime == null ? other$lastPlayEndTime == null : this$lastPlayEndTime.equals(other$lastPlayEndTime)) {
                                 Object this$totalPlayTime = this.getTotalPlayTime();
                                 Object other$totalPlayTime = other.getTotalPlayTime();
                                 return this$totalPlayTime == null ? other$totalPlayTime == null : this$totalPlayTime.equals(other$totalPlayTime);
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
      return other instanceof PlaySessionDailyStatResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $totalSeconds = this.getTotalSeconds();
      result = result * 59 + ($totalSeconds == null ? 43 : $totalSeconds.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $content = this.getContent();
      result = result * 59 + ($content == null ? 43 : $content.hashCode());
      Object $device = this.getDevice();
      result = result * 59 + ($device == null ? 43 : $device.hashCode());
      Object $playDay = this.getPlayDay();
      result = result * 59 + ($playDay == null ? 43 : $playDay.hashCode());
      Object $firstPlayStartTime = this.getFirstPlayStartTime();
      result = result * 59 + ($firstPlayStartTime == null ? 43 : $firstPlayStartTime.hashCode());
      Object $lastPlayEndTime = this.getLastPlayEndTime();
      result = result * 59 + ($lastPlayEndTime == null ? 43 : $lastPlayEndTime.hashCode());
      Object $totalPlayTime = this.getTotalPlayTime();
      return result * 59 + ($totalPlayTime == null ? 43 : $totalPlayTime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PlaySessionDailyStatResponse(embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", content="
         + this.getContent()
         + ", device="
         + this.getDevice()
         + ", playDay="
         + this.getPlayDay()
         + ", firstPlayStartTime="
         + this.getFirstPlayStartTime()
         + ", lastPlayEndTime="
         + this.getLastPlayEndTime()
         + ", totalSeconds="
         + this.getTotalSeconds()
         + ", totalPlayTime="
         + this.getTotalPlayTime()
         + ")";
   }
}
