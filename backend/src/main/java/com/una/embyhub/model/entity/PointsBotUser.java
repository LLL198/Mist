package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Generated;

@TableName("points_bot_user")
public class PointsBotUser extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("user_id")
   private Long userId;
   @TableField("username")
   private String username;
   @TableField("display_name")
   private String displayName;
   @TableField("level_id")
   private Long levelId;
   @TableField("level_name")
   private String levelName;
   @TableField("points")
   private Long points;
   @TableField("checkin_streak")
   private Integer checkinStreak;
   @TableField("last_checkin_date")
   private LocalDate lastCheckinDate;
   @TableField("last_message_date")
   private LocalDate lastMessageDate;
   @TableField("daily_message_points")
   private Integer dailyMessagePoints;
   @TableField("daily_message_count")
   private Integer dailyMessageCount;

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
   @Override
   public String toString() {
      return "PointsBotUser(id="
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

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotUser other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
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
                                          if (this$lastCheckinDate == null ? other$lastCheckinDate == null : this$lastCheckinDate.equals(other$lastCheckinDate)
                                             )
                                           {
                                             Object this$lastMessageDate = this.getLastMessageDate();
                                             Object other$lastMessageDate = other.getLastMessageDate();
                                             return this$lastMessageDate == null
                                                ? other$lastMessageDate == null
                                                : this$lastMessageDate.equals(other$lastMessageDate);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotUser;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
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
}
