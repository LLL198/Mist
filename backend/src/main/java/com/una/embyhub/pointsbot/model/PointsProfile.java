package com.una.embyhub.pointsbot.model;

import java.time.LocalDate;
import lombok.Generated;

public class PointsProfile {
   private Long id;
   private long chatId;
   private long userId;
   private String username;
   private String displayName;
   private Long levelId;
   private String levelName;
   private long points;
   private int checkinStreak;
   private LocalDate lastCheckinDate;
   private LocalDate lastMessageDate;
   private int dailyMessagePoints;
   private int dailyMessageCount;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public long getChatId() {
      return this.chatId;
   }

   @Generated
   public long getUserId() {
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
   public Long getLevelId() {
      return this.levelId;
   }

   @Generated
   public String getLevelName() {
      return this.levelName;
   }

   @Generated
   public long getPoints() {
      return this.points;
   }

   @Generated
   public int getCheckinStreak() {
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
   public int getDailyMessagePoints() {
      return this.dailyMessagePoints;
   }

   @Generated
   public int getDailyMessageCount() {
      return this.dailyMessageCount;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setChatId(final long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setUserId(final long userId) {
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
   public void setLevelId(final Long levelId) {
      this.levelId = levelId;
   }

   @Generated
   public void setLevelName(final String levelName) {
      this.levelName = levelName;
   }

   @Generated
   public void setPoints(final long points) {
      this.points = points;
   }

   @Generated
   public void setCheckinStreak(final int checkinStreak) {
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
   public void setDailyMessagePoints(final int dailyMessagePoints) {
      this.dailyMessagePoints = dailyMessagePoints;
   }

   @Generated
   public void setDailyMessageCount(final int dailyMessageCount) {
      this.dailyMessageCount = dailyMessageCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsProfile other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getChatId() != other.getChatId()) {
         return false;
      } else if (this.getUserId() != other.getUserId()) {
         return false;
      } else if (this.getPoints() != other.getPoints()) {
         return false;
      } else if (this.getCheckinStreak() != other.getCheckinStreak()) {
         return false;
      } else if (this.getDailyMessagePoints() != other.getDailyMessagePoints()) {
         return false;
      } else if (this.getDailyMessageCount() != other.getDailyMessageCount()) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$levelId = this.getLevelId();
            Object other$levelId = other.getLevelId();
            if (this$levelId == null ? other$levelId == null : this$levelId.equals(other$levelId)) {
               Object this$username = this.getUsername();
               Object other$username = other.getUsername();
               if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                  Object this$displayName = this.getDisplayName();
                  Object other$displayName = other.getDisplayName();
                  if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                     Object this$levelName = this.getLevelName();
                     Object other$levelName = other.getLevelName();
                     if (this$levelName == null ? other$levelName == null : this$levelName.equals(other$levelName)) {
                        Object this$lastCheckinDate = this.getLastCheckinDate();
                        Object other$lastCheckinDate = other.getLastCheckinDate();
                        if (this$lastCheckinDate == null ? other$lastCheckinDate == null : this$lastCheckinDate.equals(other$lastCheckinDate)) {
                           Object this$lastMessageDate = this.getLastMessageDate();
                           Object other$lastMessageDate = other.getLastMessageDate();
                           return this$lastMessageDate == null ? other$lastMessageDate == null : this$lastMessageDate.equals(other$lastMessageDate);
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
      return other instanceof PointsProfile;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $chatId = this.getChatId();
      result = result * 59 + (int)($chatId >>> 32 ^ $chatId);
      long $userId = this.getUserId();
      result = result * 59 + (int)($userId >>> 32 ^ $userId);
      long $points = this.getPoints();
      result = result * 59 + (int)($points >>> 32 ^ $points);
      result = result * 59 + this.getCheckinStreak();
      result = result * 59 + this.getDailyMessagePoints();
      result = result * 59 + this.getDailyMessageCount();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $levelId = this.getLevelId();
      result = result * 59 + ($levelId == null ? 43 : $levelId.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $levelName = this.getLevelName();
      result = result * 59 + ($levelName == null ? 43 : $levelName.hashCode());
      Object $lastCheckinDate = this.getLastCheckinDate();
      result = result * 59 + ($lastCheckinDate == null ? 43 : $lastCheckinDate.hashCode());
      Object $lastMessageDate = this.getLastMessageDate();
      return result * 59 + ($lastMessageDate == null ? 43 : $lastMessageDate.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsProfile(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
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
         + ")";
   }
}
