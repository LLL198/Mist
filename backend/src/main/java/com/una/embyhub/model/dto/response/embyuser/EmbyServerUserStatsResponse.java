package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class EmbyServerUserStatsResponse implements Serializable {
   private Long embyInfoId;
   private String serverName;
   private Long totalUserCount;
   private Long activeUserCount;
   private Long disabledUserCount;
   private Long todayNewUserCount;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public Long getTotalUserCount() {
      return this.totalUserCount;
   }

   @Generated
   public Long getActiveUserCount() {
      return this.activeUserCount;
   }

   @Generated
   public Long getDisabledUserCount() {
      return this.disabledUserCount;
   }

   @Generated
   public Long getTodayNewUserCount() {
      return this.todayNewUserCount;
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
   public void setTotalUserCount(final Long totalUserCount) {
      this.totalUserCount = totalUserCount;
   }

   @Generated
   public void setActiveUserCount(final Long activeUserCount) {
      this.activeUserCount = activeUserCount;
   }

   @Generated
   public void setDisabledUserCount(final Long disabledUserCount) {
      this.disabledUserCount = disabledUserCount;
   }

   @Generated
   public void setTodayNewUserCount(final Long todayNewUserCount) {
      this.todayNewUserCount = todayNewUserCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyServerUserStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$totalUserCount = this.getTotalUserCount();
            Object other$totalUserCount = other.getTotalUserCount();
            if (this$totalUserCount == null ? other$totalUserCount == null : this$totalUserCount.equals(other$totalUserCount)) {
               Object this$activeUserCount = this.getActiveUserCount();
               Object other$activeUserCount = other.getActiveUserCount();
               if (this$activeUserCount == null ? other$activeUserCount == null : this$activeUserCount.equals(other$activeUserCount)) {
                  Object this$disabledUserCount = this.getDisabledUserCount();
                  Object other$disabledUserCount = other.getDisabledUserCount();
                  if (this$disabledUserCount == null ? other$disabledUserCount == null : this$disabledUserCount.equals(other$disabledUserCount)) {
                     Object this$todayNewUserCount = this.getTodayNewUserCount();
                     Object other$todayNewUserCount = other.getTodayNewUserCount();
                     if (this$todayNewUserCount == null ? other$todayNewUserCount == null : this$todayNewUserCount.equals(other$todayNewUserCount)) {
                        Object this$serverName = this.getServerName();
                        Object other$serverName = other.getServerName();
                        return this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName);
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
      return other instanceof EmbyServerUserStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $totalUserCount = this.getTotalUserCount();
      result = result * 59 + ($totalUserCount == null ? 43 : $totalUserCount.hashCode());
      Object $activeUserCount = this.getActiveUserCount();
      result = result * 59 + ($activeUserCount == null ? 43 : $activeUserCount.hashCode());
      Object $disabledUserCount = this.getDisabledUserCount();
      result = result * 59 + ($disabledUserCount == null ? 43 : $disabledUserCount.hashCode());
      Object $todayNewUserCount = this.getTodayNewUserCount();
      result = result * 59 + ($todayNewUserCount == null ? 43 : $todayNewUserCount.hashCode());
      Object $serverName = this.getServerName();
      return result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyServerUserStatsResponse(embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", totalUserCount="
         + this.getTotalUserCount()
         + ", activeUserCount="
         + this.getActiveUserCount()
         + ", disabledUserCount="
         + this.getDisabledUserCount()
         + ", todayNewUserCount="
         + this.getTodayNewUserCount()
         + ")";
   }
}
