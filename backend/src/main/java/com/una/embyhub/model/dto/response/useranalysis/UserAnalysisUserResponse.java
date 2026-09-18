package com.una.embyhub.model.dto.response.useranalysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class UserAnalysisUserResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long userId;
   private String embyUserId;
   private String embyUserName;
   private Long embyInfoId;
   private String serverName;
   private String avatar;
   private Integer userStatus;
   private Date registerDatetime;
   private Date lastPlayDatetime;
   private Long totalPlayCount = 0L;
   private Long totalDurationSeconds = 0L;
   private Long activeDayCount = 0L;
   private List<UserAnalysisDimensionResponse> locationStats = new ArrayList<>();
   private List<UserAnalysisDimensionResponse> playerStats = new ArrayList<>();
   private List<UserAnalysisDimensionResponse> itemTypeStats = new ArrayList<>();
   private List<UserAnalysisDimensionResponse> timePeriodStats = new ArrayList<>();
   private List<UserAnalysisTimelineResponse> playTimeline = new ArrayList<>();
   private List<UserAnalysisRadarResponse> radarStats = new ArrayList<>();

   @Generated
   public Long getUserId() {
      return this.userId;
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public String getAvatar() {
      return this.avatar;
   }

   @Generated
   public Integer getUserStatus() {
      return this.userStatus;
   }

   @Generated
   public Date getRegisterDatetime() {
      return this.registerDatetime;
   }

   @Generated
   public Date getLastPlayDatetime() {
      return this.lastPlayDatetime;
   }

   @Generated
   public Long getTotalPlayCount() {
      return this.totalPlayCount;
   }

   @Generated
   public Long getTotalDurationSeconds() {
      return this.totalDurationSeconds;
   }

   @Generated
   public Long getActiveDayCount() {
      return this.activeDayCount;
   }

   @Generated
   public List<UserAnalysisDimensionResponse> getLocationStats() {
      return this.locationStats;
   }

   @Generated
   public List<UserAnalysisDimensionResponse> getPlayerStats() {
      return this.playerStats;
   }

   @Generated
   public List<UserAnalysisDimensionResponse> getItemTypeStats() {
      return this.itemTypeStats;
   }

   @Generated
   public List<UserAnalysisDimensionResponse> getTimePeriodStats() {
      return this.timePeriodStats;
   }

   @Generated
   public List<UserAnalysisTimelineResponse> getPlayTimeline() {
      return this.playTimeline;
   }

   @Generated
   public List<UserAnalysisRadarResponse> getRadarStats() {
      return this.radarStats;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
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
   public void setAvatar(final String avatar) {
      this.avatar = avatar;
   }

   @Generated
   public void setUserStatus(final Integer userStatus) {
      this.userStatus = userStatus;
   }

   @Generated
   public void setRegisterDatetime(final Date registerDatetime) {
      this.registerDatetime = registerDatetime;
   }

   @Generated
   public void setLastPlayDatetime(final Date lastPlayDatetime) {
      this.lastPlayDatetime = lastPlayDatetime;
   }

   @Generated
   public void setTotalPlayCount(final Long totalPlayCount) {
      this.totalPlayCount = totalPlayCount;
   }

   @Generated
   public void setTotalDurationSeconds(final Long totalDurationSeconds) {
      this.totalDurationSeconds = totalDurationSeconds;
   }

   @Generated
   public void setActiveDayCount(final Long activeDayCount) {
      this.activeDayCount = activeDayCount;
   }

   @Generated
   public void setLocationStats(final List<UserAnalysisDimensionResponse> locationStats) {
      this.locationStats = locationStats;
   }

   @Generated
   public void setPlayerStats(final List<UserAnalysisDimensionResponse> playerStats) {
      this.playerStats = playerStats;
   }

   @Generated
   public void setItemTypeStats(final List<UserAnalysisDimensionResponse> itemTypeStats) {
      this.itemTypeStats = itemTypeStats;
   }

   @Generated
   public void setTimePeriodStats(final List<UserAnalysisDimensionResponse> timePeriodStats) {
      this.timePeriodStats = timePeriodStats;
   }

   @Generated
   public void setPlayTimeline(final List<UserAnalysisTimelineResponse> playTimeline) {
      this.playTimeline = playTimeline;
   }

   @Generated
   public void setRadarStats(final List<UserAnalysisRadarResponse> radarStats) {
      this.radarStats = radarStats;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserAnalysisUserResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userId = this.getUserId();
         Object other$userId = other.getUserId();
         if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$userStatus = this.getUserStatus();
               Object other$userStatus = other.getUserStatus();
               if (this$userStatus == null ? other$userStatus == null : this$userStatus.equals(other$userStatus)) {
                  Object this$totalPlayCount = this.getTotalPlayCount();
                  Object other$totalPlayCount = other.getTotalPlayCount();
                  if (this$totalPlayCount == null ? other$totalPlayCount == null : this$totalPlayCount.equals(other$totalPlayCount)) {
                     Object this$totalDurationSeconds = this.getTotalDurationSeconds();
                     Object other$totalDurationSeconds = other.getTotalDurationSeconds();
                     if (this$totalDurationSeconds == null ? other$totalDurationSeconds == null : this$totalDurationSeconds.equals(other$totalDurationSeconds)) {
                        Object this$activeDayCount = this.getActiveDayCount();
                        Object other$activeDayCount = other.getActiveDayCount();
                        if (this$activeDayCount == null ? other$activeDayCount == null : this$activeDayCount.equals(other$activeDayCount)) {
                           Object this$embyUserId = this.getEmbyUserId();
                           Object other$embyUserId = other.getEmbyUserId();
                           if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                              Object this$embyUserName = this.getEmbyUserName();
                              Object other$embyUserName = other.getEmbyUserName();
                              if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                 Object this$serverName = this.getServerName();
                                 Object other$serverName = other.getServerName();
                                 if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                    Object this$avatar = this.getAvatar();
                                    Object other$avatar = other.getAvatar();
                                    if (this$avatar == null ? other$avatar == null : this$avatar.equals(other$avatar)) {
                                       Object this$registerDatetime = this.getRegisterDatetime();
                                       Object other$registerDatetime = other.getRegisterDatetime();
                                       if (this$registerDatetime == null
                                          ? other$registerDatetime == null
                                          : this$registerDatetime.equals(other$registerDatetime)) {
                                          Object this$lastPlayDatetime = this.getLastPlayDatetime();
                                          Object other$lastPlayDatetime = other.getLastPlayDatetime();
                                          if (this$lastPlayDatetime == null
                                             ? other$lastPlayDatetime == null
                                             : this$lastPlayDatetime.equals(other$lastPlayDatetime)) {
                                             Object this$locationStats = this.getLocationStats();
                                             Object other$locationStats = other.getLocationStats();
                                             if (this$locationStats == null ? other$locationStats == null : this$locationStats.equals(other$locationStats)) {
                                                Object this$playerStats = this.getPlayerStats();
                                                Object other$playerStats = other.getPlayerStats();
                                                if (this$playerStats == null ? other$playerStats == null : this$playerStats.equals(other$playerStats)) {
                                                   Object this$itemTypeStats = this.getItemTypeStats();
                                                   Object other$itemTypeStats = other.getItemTypeStats();
                                                   if (this$itemTypeStats == null
                                                      ? other$itemTypeStats == null
                                                      : this$itemTypeStats.equals(other$itemTypeStats)) {
                                                      Object this$timePeriodStats = this.getTimePeriodStats();
                                                      Object other$timePeriodStats = other.getTimePeriodStats();
                                                      if (this$timePeriodStats == null
                                                         ? other$timePeriodStats == null
                                                         : this$timePeriodStats.equals(other$timePeriodStats)) {
                                                         Object this$playTimeline = this.getPlayTimeline();
                                                         Object other$playTimeline = other.getPlayTimeline();
                                                         if (this$playTimeline == null
                                                            ? other$playTimeline == null
                                                            : this$playTimeline.equals(other$playTimeline)) {
                                                            Object this$radarStats = this.getRadarStats();
                                                            Object other$radarStats = other.getRadarStats();
                                                            return this$radarStats == null
                                                               ? other$radarStats == null
                                                               : this$radarStats.equals(other$radarStats);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof UserAnalysisUserResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $userStatus = this.getUserStatus();
      result = result * 59 + ($userStatus == null ? 43 : $userStatus.hashCode());
      Object $totalPlayCount = this.getTotalPlayCount();
      result = result * 59 + ($totalPlayCount == null ? 43 : $totalPlayCount.hashCode());
      Object $totalDurationSeconds = this.getTotalDurationSeconds();
      result = result * 59 + ($totalDurationSeconds == null ? 43 : $totalDurationSeconds.hashCode());
      Object $activeDayCount = this.getActiveDayCount();
      result = result * 59 + ($activeDayCount == null ? 43 : $activeDayCount.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $avatar = this.getAvatar();
      result = result * 59 + ($avatar == null ? 43 : $avatar.hashCode());
      Object $registerDatetime = this.getRegisterDatetime();
      result = result * 59 + ($registerDatetime == null ? 43 : $registerDatetime.hashCode());
      Object $lastPlayDatetime = this.getLastPlayDatetime();
      result = result * 59 + ($lastPlayDatetime == null ? 43 : $lastPlayDatetime.hashCode());
      Object $locationStats = this.getLocationStats();
      result = result * 59 + ($locationStats == null ? 43 : $locationStats.hashCode());
      Object $playerStats = this.getPlayerStats();
      result = result * 59 + ($playerStats == null ? 43 : $playerStats.hashCode());
      Object $itemTypeStats = this.getItemTypeStats();
      result = result * 59 + ($itemTypeStats == null ? 43 : $itemTypeStats.hashCode());
      Object $timePeriodStats = this.getTimePeriodStats();
      result = result * 59 + ($timePeriodStats == null ? 43 : $timePeriodStats.hashCode());
      Object $playTimeline = this.getPlayTimeline();
      result = result * 59 + ($playTimeline == null ? 43 : $playTimeline.hashCode());
      Object $radarStats = this.getRadarStats();
      return result * 59 + ($radarStats == null ? 43 : $radarStats.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserAnalysisUserResponse(userId="
         + this.getUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", avatar="
         + this.getAvatar()
         + ", userStatus="
         + this.getUserStatus()
         + ", registerDatetime="
         + this.getRegisterDatetime()
         + ", lastPlayDatetime="
         + this.getLastPlayDatetime()
         + ", totalPlayCount="
         + this.getTotalPlayCount()
         + ", totalDurationSeconds="
         + this.getTotalDurationSeconds()
         + ", activeDayCount="
         + this.getActiveDayCount()
         + ", locationStats="
         + this.getLocationStats()
         + ", playerStats="
         + this.getPlayerStats()
         + ", itemTypeStats="
         + this.getItemTypeStats()
         + ", timePeriodStats="
         + this.getTimePeriodStats()
         + ", playTimeline="
         + this.getPlayTimeline()
         + ", radarStats="
         + this.getRadarStats()
         + ")";
   }
}
