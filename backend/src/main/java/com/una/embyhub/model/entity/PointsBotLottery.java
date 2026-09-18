package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_lottery")
public class PointsBotLottery extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("title")
   private String title;
   @TableField("winner_count")
   private Integer winnerCount;
   @TableField("prize_config_id")
   private Long prizeConfigId;
   @TableField("status")
   private String status;
   @TableField("draw_at")
   private LocalDateTime drawAt;
   @TableField("drawn_at")
   private LocalDateTime drawnAt;
   @TableField("created_by_user_id")
   private Long createdByUserId;
   @TableField("created_by_username")
   private String createdByUsername;
   @TableField("created_by_display_name")
   private String createdByDisplayName;
   @TableField("winner_user_id")
   private Long winnerUserId;
   @TableField("winner_username")
   private String winnerUsername;
   @TableField("winner_display_name")
   private String winnerDisplayName;
   @TableField("winner_entry_id")
   private Long winnerEntryId;
   @TableField("announcement_message_id")
   private Long announcementMessageId;
   @TableField("winners_json")
   private String winnersJson;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public Integer getWinnerCount() {
      return this.winnerCount;
   }

   @Generated
   public Long getPrizeConfigId() {
      return this.prizeConfigId;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public LocalDateTime getDrawAt() {
      return this.drawAt;
   }

   @Generated
   public LocalDateTime getDrawnAt() {
      return this.drawnAt;
   }

   @Generated
   public Long getCreatedByUserId() {
      return this.createdByUserId;
   }

   @Generated
   public String getCreatedByUsername() {
      return this.createdByUsername;
   }

   @Generated
   public String getCreatedByDisplayName() {
      return this.createdByDisplayName;
   }

   @Generated
   public Long getWinnerUserId() {
      return this.winnerUserId;
   }

   @Generated
   public String getWinnerUsername() {
      return this.winnerUsername;
   }

   @Generated
   public String getWinnerDisplayName() {
      return this.winnerDisplayName;
   }

   @Generated
   public Long getWinnerEntryId() {
      return this.winnerEntryId;
   }

   @Generated
   public Long getAnnouncementMessageId() {
      return this.announcementMessageId;
   }

   @Generated
   public String getWinnersJson() {
      return this.winnersJson;
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
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setWinnerCount(final Integer winnerCount) {
      this.winnerCount = winnerCount;
   }

   @Generated
   public void setPrizeConfigId(final Long prizeConfigId) {
      this.prizeConfigId = prizeConfigId;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setDrawAt(final LocalDateTime drawAt) {
      this.drawAt = drawAt;
   }

   @Generated
   public void setDrawnAt(final LocalDateTime drawnAt) {
      this.drawnAt = drawnAt;
   }

   @Generated
   public void setCreatedByUserId(final Long createdByUserId) {
      this.createdByUserId = createdByUserId;
   }

   @Generated
   public void setCreatedByUsername(final String createdByUsername) {
      this.createdByUsername = createdByUsername;
   }

   @Generated
   public void setCreatedByDisplayName(final String createdByDisplayName) {
      this.createdByDisplayName = createdByDisplayName;
   }

   @Generated
   public void setWinnerUserId(final Long winnerUserId) {
      this.winnerUserId = winnerUserId;
   }

   @Generated
   public void setWinnerUsername(final String winnerUsername) {
      this.winnerUsername = winnerUsername;
   }

   @Generated
   public void setWinnerDisplayName(final String winnerDisplayName) {
      this.winnerDisplayName = winnerDisplayName;
   }

   @Generated
   public void setWinnerEntryId(final Long winnerEntryId) {
      this.winnerEntryId = winnerEntryId;
   }

   @Generated
   public void setAnnouncementMessageId(final Long announcementMessageId) {
      this.announcementMessageId = announcementMessageId;
   }

   @Generated
   public void setWinnersJson(final String winnersJson) {
      this.winnersJson = winnersJson;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLottery(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", title="
         + this.getTitle()
         + ", winnerCount="
         + this.getWinnerCount()
         + ", prizeConfigId="
         + this.getPrizeConfigId()
         + ", status="
         + this.getStatus()
         + ", drawAt="
         + this.getDrawAt()
         + ", drawnAt="
         + this.getDrawnAt()
         + ", createdByUserId="
         + this.getCreatedByUserId()
         + ", createdByUsername="
         + this.getCreatedByUsername()
         + ", createdByDisplayName="
         + this.getCreatedByDisplayName()
         + ", winnerUserId="
         + this.getWinnerUserId()
         + ", winnerUsername="
         + this.getWinnerUsername()
         + ", winnerDisplayName="
         + this.getWinnerDisplayName()
         + ", winnerEntryId="
         + this.getWinnerEntryId()
         + ", announcementMessageId="
         + this.getAnnouncementMessageId()
         + ", winnersJson="
         + this.getWinnersJson()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLottery other)) {
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
               Object this$winnerCount = this.getWinnerCount();
               Object other$winnerCount = other.getWinnerCount();
               if (this$winnerCount == null ? other$winnerCount == null : this$winnerCount.equals(other$winnerCount)) {
                  Object this$prizeConfigId = this.getPrizeConfigId();
                  Object other$prizeConfigId = other.getPrizeConfigId();
                  if (this$prizeConfigId == null ? other$prizeConfigId == null : this$prizeConfigId.equals(other$prizeConfigId)) {
                     Object this$createdByUserId = this.getCreatedByUserId();
                     Object other$createdByUserId = other.getCreatedByUserId();
                     if (this$createdByUserId == null ? other$createdByUserId == null : this$createdByUserId.equals(other$createdByUserId)) {
                        Object this$winnerUserId = this.getWinnerUserId();
                        Object other$winnerUserId = other.getWinnerUserId();
                        if (this$winnerUserId == null ? other$winnerUserId == null : this$winnerUserId.equals(other$winnerUserId)) {
                           Object this$winnerEntryId = this.getWinnerEntryId();
                           Object other$winnerEntryId = other.getWinnerEntryId();
                           if (this$winnerEntryId == null ? other$winnerEntryId == null : this$winnerEntryId.equals(other$winnerEntryId)) {
                              Object this$announcementMessageId = this.getAnnouncementMessageId();
                              Object other$announcementMessageId = other.getAnnouncementMessageId();
                              if (this$announcementMessageId == null
                                 ? other$announcementMessageId == null
                                 : this$announcementMessageId.equals(other$announcementMessageId)) {
                                 Object this$title = this.getTitle();
                                 Object other$title = other.getTitle();
                                 if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                    Object this$status = this.getStatus();
                                    Object other$status = other.getStatus();
                                    if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                       Object this$drawAt = this.getDrawAt();
                                       Object other$drawAt = other.getDrawAt();
                                       if (this$drawAt == null ? other$drawAt == null : this$drawAt.equals(other$drawAt)) {
                                          Object this$drawnAt = this.getDrawnAt();
                                          Object other$drawnAt = other.getDrawnAt();
                                          if (this$drawnAt == null ? other$drawnAt == null : this$drawnAt.equals(other$drawnAt)) {
                                             Object this$createdByUsername = this.getCreatedByUsername();
                                             Object other$createdByUsername = other.getCreatedByUsername();
                                             if (this$createdByUsername == null
                                                ? other$createdByUsername == null
                                                : this$createdByUsername.equals(other$createdByUsername)) {
                                                Object this$createdByDisplayName = this.getCreatedByDisplayName();
                                                Object other$createdByDisplayName = other.getCreatedByDisplayName();
                                                if (this$createdByDisplayName == null
                                                   ? other$createdByDisplayName == null
                                                   : this$createdByDisplayName.equals(other$createdByDisplayName)) {
                                                   Object this$winnerUsername = this.getWinnerUsername();
                                                   Object other$winnerUsername = other.getWinnerUsername();
                                                   if (this$winnerUsername == null
                                                      ? other$winnerUsername == null
                                                      : this$winnerUsername.equals(other$winnerUsername)) {
                                                      Object this$winnerDisplayName = this.getWinnerDisplayName();
                                                      Object other$winnerDisplayName = other.getWinnerDisplayName();
                                                      if (this$winnerDisplayName == null
                                                         ? other$winnerDisplayName == null
                                                         : this$winnerDisplayName.equals(other$winnerDisplayName)) {
                                                         Object this$winnersJson = this.getWinnersJson();
                                                         Object other$winnersJson = other.getWinnersJson();
                                                         return this$winnersJson == null
                                                            ? other$winnersJson == null
                                                            : this$winnersJson.equals(other$winnersJson);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotLottery;
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
      Object $winnerCount = this.getWinnerCount();
      result = result * 59 + ($winnerCount == null ? 43 : $winnerCount.hashCode());
      Object $prizeConfigId = this.getPrizeConfigId();
      result = result * 59 + ($prizeConfigId == null ? 43 : $prizeConfigId.hashCode());
      Object $createdByUserId = this.getCreatedByUserId();
      result = result * 59 + ($createdByUserId == null ? 43 : $createdByUserId.hashCode());
      Object $winnerUserId = this.getWinnerUserId();
      result = result * 59 + ($winnerUserId == null ? 43 : $winnerUserId.hashCode());
      Object $winnerEntryId = this.getWinnerEntryId();
      result = result * 59 + ($winnerEntryId == null ? 43 : $winnerEntryId.hashCode());
      Object $announcementMessageId = this.getAnnouncementMessageId();
      result = result * 59 + ($announcementMessageId == null ? 43 : $announcementMessageId.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $drawAt = this.getDrawAt();
      result = result * 59 + ($drawAt == null ? 43 : $drawAt.hashCode());
      Object $drawnAt = this.getDrawnAt();
      result = result * 59 + ($drawnAt == null ? 43 : $drawnAt.hashCode());
      Object $createdByUsername = this.getCreatedByUsername();
      result = result * 59 + ($createdByUsername == null ? 43 : $createdByUsername.hashCode());
      Object $createdByDisplayName = this.getCreatedByDisplayName();
      result = result * 59 + ($createdByDisplayName == null ? 43 : $createdByDisplayName.hashCode());
      Object $winnerUsername = this.getWinnerUsername();
      result = result * 59 + ($winnerUsername == null ? 43 : $winnerUsername.hashCode());
      Object $winnerDisplayName = this.getWinnerDisplayName();
      result = result * 59 + ($winnerDisplayName == null ? 43 : $winnerDisplayName.hashCode());
      Object $winnersJson = this.getWinnersJson();
      return result * 59 + ($winnersJson == null ? 43 : $winnersJson.hashCode());
   }
}
