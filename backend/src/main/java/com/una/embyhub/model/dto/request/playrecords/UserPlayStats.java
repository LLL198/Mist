package com.una.embyhub.model.dto.request.playrecords;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class UserPlayStats implements Serializable {
   private String embyUserId;
   private String embyUserName;
   private Long embyInfoId;
   private String serverName;
   private Date playDay;
   private Integer playTimes;
   private String totalPlayTime;

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
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
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public Date getPlayDay() {
      return this.playDay;
   }

   @Generated
   public Integer getPlayTimes() {
      return this.playTimes;
   }

   @Generated
   public String getTotalPlayTime() {
      return this.totalPlayTime;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setPlayDay(final Date playDay) {
      this.playDay = playDay;
   }

   @Generated
   public void setPlayTimes(final Integer playTimes) {
      this.playTimes = playTimes;
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
      } else if (!(o instanceof UserPlayStats other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$playTimes = this.getPlayTimes();
            Object other$playTimes = other.getPlayTimes();
            if (this$playTimes == null ? other$playTimes == null : this$playTimes.equals(other$playTimes)) {
               Object this$embyUserId = this.getEmbyUserId();
               Object other$embyUserId = other.getEmbyUserId();
               if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                  Object this$embyUserName = this.getEmbyUserName();
                  Object other$embyUserName = other.getEmbyUserName();
                  if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                     Object this$serverName = this.getServerName();
                     Object other$serverName = other.getServerName();
                     if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                        Object this$playDay = this.getPlayDay();
                        Object other$playDay = other.getPlayDay();
                        if (this$playDay == null ? other$playDay == null : this$playDay.equals(other$playDay)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof UserPlayStats;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $playTimes = this.getPlayTimes();
      result = result * 59 + ($playTimes == null ? 43 : $playTimes.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $playDay = this.getPlayDay();
      result = result * 59 + ($playDay == null ? 43 : $playDay.hashCode());
      Object $totalPlayTime = this.getTotalPlayTime();
      return result * 59 + ($totalPlayTime == null ? 43 : $totalPlayTime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserPlayStats(embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", playDay="
         + this.getPlayDay()
         + ", playTimes="
         + this.getPlayTimes()
         + ", totalPlayTime="
         + this.getTotalPlayTime()
         + ")";
   }
}
