package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import lombok.Generated;

public class PointsBotUserResponse implements Serializable {
   private Long id;
   private Long chatId;
   private Long userId;
   private String username;
   private String displayName;
   private String embyUserName;
   private Long levelId;
   private String levelName;
   private Long points;
   private Integer checkinStreak;
   private LocalDate lastCheckinDate;
   private LocalDate lastMessageDate;
   private Integer dailyMessagePoints;
   private Integer dailyMessageCount;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getDisplayName() {
      return this.displayName;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Long getLevelId() {
      return this.levelId;
   }

   @Generated
   public String getLevelName() {
      return this.levelName;
   }

   @Generated
   public Long getPoints() {
      return this.points;
   }

   @Generated
   public Integer getCheckinStreak() {
      return this.checkinStreak;
   }

   @Generated
   public LocalDate getLastCheckinDate() {
      return this.lastCheckinDate;
   }

   @Generated
   public LocalDate getLastMessageDate() {
      return this.lastMessageDate;
   }

   @Generated
   public Integer getDailyMessagePoints() {
      return this.dailyMessagePoints;
   }

   @Generated
   public Integer getDailyMessageCount() {
      return this.dailyMessageCount;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public String getCreateUserName() {
      return this.createUserName;
   }

   @Generated
   public String getUpdateUserName() {
      return this.updateUserName;
   }

   @Generated
   public Long getUpdateUserId() {
      return this.updateUserId;
   }

   @Generated
   public Long getCreateUserId() {
      return this.createUserId;
   }

   @Generated
   public Integer getDelFlag() {
      return this.delFlag;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setDisplayName(final String displayName) {
      this.displayName = displayName;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setLevelId(final Long levelId) {
      this.levelId = levelId;
   }

   @Generated
   public void setLevelName(final String levelName) {
      this.levelName = levelName;
   }

   @Generated
   public void setPoints(final Long points) {
      this.points = points;
   }

   @Generated
   public void setCheckinStreak(final Integer checkinStreak) {
      this.checkinStreak = checkinStreak;
   }

   @Generated
   public void setLastCheckinDate(final LocalDate lastCheckinDate) {
      this.lastCheckinDate = lastCheckinDate;
   }

   @Generated
   public void setLastMessageDate(final LocalDate lastMessageDate) {
      this.lastMessageDate = lastMessageDate;
   }

   @Generated
   public void setDailyMessagePoints(final Integer dailyMessagePoints) {
      this.dailyMessagePoints = dailyMessagePoints;
   }

   @Generated
   public void setDailyMessageCount(final Integer dailyMessageCount) {
      this.dailyMessageCount = dailyMessageCount;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   public void setCreateUserName(final String createUserName) {
      this.createUserName = createUserName;
   }

   @Generated
   public void setUpdateUserName(final String updateUserName) {
      this.updateUserName = updateUserName;
   }

   @Generated
   public void setUpdateUserId(final Long updateUserId) {
      this.updateUserId = updateUserId;
   }

   @Generated
   public void setCreateUserId(final Long createUserId) {
      this.createUserId = createUserId;
   }

   @Generated
   public void setDelFlag(final Integer delFlag) {
      this.delFlag = delFlag;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotUserResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$levelId = this.getLevelId();
                  Object other$levelId = other.getLevelId();
                  if (this$levelId == null ? other$levelId == null : this$levelId.equals(other$levelId)) {
                     Object this$points = this.getPoints();
                     Object other$points = other.getPoints();
                     if (this$points == null ? other$points == null : this$points.equals(other$points)) {
                        Object this$checkinStreak = this.getCheckinStreak();
                        Object other$checkinStreak = other.getCheckinStreak();
                        if (this$checkinStreak == null ? other$checkinStreak == null : this$checkinStreak.equals(other$checkinStreak)) {
                           Object this$dailyMessagePoints = this.getDailyMessagePoints();
                           Object other$dailyMessagePoints = other.getDailyMessagePoints();
                           if (this$dailyMessagePoints == null ? other$dailyMessagePoints == null : this$dailyMessagePoints.equals(other$dailyMessagePoints)) {
                              Object this$dailyMessageCount = this.getDailyMessageCount();
                              Object other$dailyMessageCount = other.getDailyMessageCount();
                              if (this$dailyMessageCount == null ? other$dailyMessageCount == null : this$dailyMessageCount.equals(other$dailyMessageCount)) {
                                 Object this$updateUserId = this.getUpdateUserId();
                                 Object other$updateUserId = other.getUpdateUserId();
                                 if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                                    Object this$createUserId = this.getCreateUserId();
                                    Object other$createUserId = other.getCreateUserId();
                                    if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                                       Object this$delFlag = this.getDelFlag();
                                       Object other$delFlag = other.getDelFlag();
                                       if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                                          Object this$username = this.getUsername();
                                          Object other$username = other.getUsername();
                                          if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                                             Object this$displayName = this.getDisplayName();
                                             Object other$displayName = other.getDisplayName();
                                             if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                                                Object this$embyUserName = this.getEmbyUserName();
                                                Object other$embyUserName = other.getEmbyUserName();
                                                if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                                   Object this$levelName = this.getLevelName();
                                                   Object other$levelName = other.getLevelName();
                                                   if (this$levelName == null ? other$levelName == null : this$levelName.equals(other$levelName)) {
                                                      Object this$lastCheckinDate = this.getLastCheckinDate();
                                                      Object other$lastCheckinDate = other.getLastCheckinDate();
                                                      if (this$lastCheckinDate == null
                                                         ? other$lastCheckinDate == null
                                                         : this$lastCheckinDate.equals(other$lastCheckinDate)) {
                                                         Object this$lastMessageDate = this.getLastMessageDate();
                                                         Object other$lastMessageDate = other.getLastMessageDate();
                                                         if (this$lastMessageDate == null
                                                            ? other$lastMessageDate == null
                                                            : this$lastMessageDate.equals(other$lastMessageDate)) {
                                                            Object this$createDatetime = this.getCreateDatetime();
                                                            Object other$createDatetime = other.getCreateDatetime();
                                                            if (this$createDatetime == null
                                                               ? other$createDatetime == null
                                                               : this$createDatetime.equals(other$createDatetime)) {
                                                               Object this$updateDatetime = this.getUpdateDatetime();
                                                               Object other$updateDatetime = other.getUpdateDatetime();
                                                               if (this$updateDatetime == null
                                                                  ? other$updateDatetime == null
                                                                  : this$updateDatetime.equals(other$updateDatetime)) {
                                                                  Object this$createUserName = this.getCreateUserName();
                                                                  Object other$createUserName = other.getCreateUserName();
                                                                  if (this$createUserName == null
                                                                     ? other$createUserName == null
                                                                     : this$createUserName.equals(other$createUserName)) {
                                                                     Object this$updateUserName = this.getUpdateUserName();
                                                                     Object other$updateUserName = other.getUpdateUserName();
                                                                     return this$updateUserName == null
                                                                        ? other$updateUserName == null
                                                                        : this$updateUserName.equals(other$updateUserName);
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
      return other instanceof PointsBotUserResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $levelId = this.getLevelId();
      result = result * 59 + ($levelId == null ? 43 : $levelId.hashCode());
      Object $points = this.getPoints();
      result = result * 59 + ($points == null ? 43 : $points.hashCode());
      Object $checkinStreak = this.getCheckinStreak();
      result = result * 59 + ($checkinStreak == null ? 43 : $checkinStreak.hashCode());
      Object $dailyMessagePoints = this.getDailyMessagePoints();
      result = result * 59 + ($dailyMessagePoints == null ? 43 : $dailyMessagePoints.hashCode());
      Object $dailyMessageCount = this.getDailyMessageCount();
      result = result * 59 + ($dailyMessageCount == null ? 43 : $dailyMessageCount.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $levelName = this.getLevelName();
      result = result * 59 + ($levelName == null ? 43 : $levelName.hashCode());
      Object $lastCheckinDate = this.getLastCheckinDate();
      result = result * 59 + ($lastCheckinDate == null ? 43 : $lastCheckinDate.hashCode());
      Object $lastMessageDate = this.getLastMessageDate();
      result = result * 59 + ($lastMessageDate == null ? 43 : $lastMessageDate.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      return result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotUserResponse(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", levelId="
         + this.getLevelId()
         + ", levelName="
         + this.getLevelName()
         + ", points="
         + this.getPoints()
         + ", checkinStreak="
         + this.getCheckinStreak()
         + ", lastCheckinDate="
         + this.getLastCheckinDate()
         + ", lastMessageDate="
         + this.getLastMessageDate()
         + ", dailyMessagePoints="
         + this.getDailyMessagePoints()
         + ", dailyMessageCount="
         + this.getDailyMessageCount()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", updateUserName="
         + this.getUpdateUserName()
         + ", updateUserId="
         + this.getUpdateUserId()
         + ", createUserId="
         + this.getCreateUserId()
         + ", delFlag="
         + this.getDelFlag()
         + ")";
   }
}
