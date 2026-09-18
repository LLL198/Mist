package com.una.embyhub.model.dto.response.pointsbot;

import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.model.entity.PointsBotPrizeConfig;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class PointsBotLotteryResponse implements Serializable {
   private Long id;
   private Long chatId;
   private String title;
   private String status;
   private Long prizeConfigId;
   @BindField(
      entity = PointsBotPrizeConfig.class,
      field = "prizeName",
      condition = "this.prize_config_id=id"
   )
   private String prizeName;
   @BindField(
      entity = PointsBotPrizeConfig.class,
      field = "requiredPoints",
      condition = "this.prize_config_id=id"
   )
   private Integer prizeRequiredPoints;
   private LocalDateTime drawAt;
   private LocalDateTime drawnAt;
   private Long createdByUserId;
   private String createdByUsername;
   private String createdByDisplayName;
   private Long winnerUserId;
   private String winnerUsername;
   private String winnerDisplayName;
   private Long winnerEntryId;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;
   private Integer winnerCount;
   private String winnersJson;
   private List<PointsBotLotteryResponse.WinnerInfo> winners;

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
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Long getPrizeConfigId() {
      return this.prizeConfigId;
   }

   @Generated
   public String getPrizeName() {
      return this.prizeName;
   }

   @Generated
   public Integer getPrizeRequiredPoints() {
      return this.prizeRequiredPoints;
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
   public Integer getWinnerCount() {
      return this.winnerCount;
   }

   @Generated
   public String getWinnersJson() {
      return this.winnersJson;
   }

   @Generated
   public List<PointsBotLotteryResponse.WinnerInfo> getWinners() {
      return this.winners;
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
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setPrizeConfigId(final Long prizeConfigId) {
      this.prizeConfigId = prizeConfigId;
   }

   @Generated
   public void setPrizeName(final String prizeName) {
      this.prizeName = prizeName;
   }

   @Generated
   public void setPrizeRequiredPoints(final Integer prizeRequiredPoints) {
      this.prizeRequiredPoints = prizeRequiredPoints;
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
   public void setWinnerCount(final Integer winnerCount) {
      this.winnerCount = winnerCount;
   }

   @Generated
   public void setWinnersJson(final String winnersJson) {
      this.winnersJson = winnersJson;
   }

   @Generated
   public void setWinners(final List<PointsBotLotteryResponse.WinnerInfo> winners) {
      this.winners = winners;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLotteryResponse other)) {
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
               Object this$prizeConfigId = this.getPrizeConfigId();
               Object other$prizeConfigId = other.getPrizeConfigId();
               if (this$prizeConfigId == null ? other$prizeConfigId == null : this$prizeConfigId.equals(other$prizeConfigId)) {
                  Object this$prizeRequiredPoints = this.getPrizeRequiredPoints();
                  Object other$prizeRequiredPoints = other.getPrizeRequiredPoints();
                  if (this$prizeRequiredPoints == null ? other$prizeRequiredPoints == null : this$prizeRequiredPoints.equals(other$prizeRequiredPoints)) {
                     Object this$createdByUserId = this.getCreatedByUserId();
                     Object other$createdByUserId = other.getCreatedByUserId();
                     if (this$createdByUserId == null ? other$createdByUserId == null : this$createdByUserId.equals(other$createdByUserId)) {
                        Object this$winnerUserId = this.getWinnerUserId();
                        Object other$winnerUserId = other.getWinnerUserId();
                        if (this$winnerUserId == null ? other$winnerUserId == null : this$winnerUserId.equals(other$winnerUserId)) {
                           Object this$winnerEntryId = this.getWinnerEntryId();
                           Object other$winnerEntryId = other.getWinnerEntryId();
                           if (this$winnerEntryId == null ? other$winnerEntryId == null : this$winnerEntryId.equals(other$winnerEntryId)) {
                              Object this$updateUserId = this.getUpdateUserId();
                              Object other$updateUserId = other.getUpdateUserId();
                              if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                                 Object this$createUserId = this.getCreateUserId();
                                 Object other$createUserId = other.getCreateUserId();
                                 if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                                    Object this$delFlag = this.getDelFlag();
                                    Object other$delFlag = other.getDelFlag();
                                    if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                                       Object this$winnerCount = this.getWinnerCount();
                                       Object other$winnerCount = other.getWinnerCount();
                                       if (this$winnerCount == null ? other$winnerCount == null : this$winnerCount.equals(other$winnerCount)) {
                                          Object this$title = this.getTitle();
                                          Object other$title = other.getTitle();
                                          if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                             Object this$status = this.getStatus();
                                             Object other$status = other.getStatus();
                                             if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                Object this$prizeName = this.getPrizeName();
                                                Object other$prizeName = other.getPrizeName();
                                                if (this$prizeName == null ? other$prizeName == null : this$prizeName.equals(other$prizeName)) {
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
                                                                              if (this$updateUserName == null
                                                                                 ? other$updateUserName == null
                                                                                 : this$updateUserName.equals(other$updateUserName)) {
                                                                                 Object this$winnersJson = this.getWinnersJson();
                                                                                 Object other$winnersJson = other.getWinnersJson();
                                                                                 if (this$winnersJson == null
                                                                                    ? other$winnersJson == null
                                                                                    : this$winnersJson.equals(other$winnersJson)) {
                                                                                    Object this$winners = this.getWinners();
                                                                                    Object other$winners = other.getWinners();
                                                                                    return this$winners == null
                                                                                       ? other$winners == null
                                                                                       : this$winners.equals(other$winners);
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
      return other instanceof PointsBotLotteryResponse;
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
      Object $prizeConfigId = this.getPrizeConfigId();
      result = result * 59 + ($prizeConfigId == null ? 43 : $prizeConfigId.hashCode());
      Object $prizeRequiredPoints = this.getPrizeRequiredPoints();
      result = result * 59 + ($prizeRequiredPoints == null ? 43 : $prizeRequiredPoints.hashCode());
      Object $createdByUserId = this.getCreatedByUserId();
      result = result * 59 + ($createdByUserId == null ? 43 : $createdByUserId.hashCode());
      Object $winnerUserId = this.getWinnerUserId();
      result = result * 59 + ($winnerUserId == null ? 43 : $winnerUserId.hashCode());
      Object $winnerEntryId = this.getWinnerEntryId();
      result = result * 59 + ($winnerEntryId == null ? 43 : $winnerEntryId.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $winnerCount = this.getWinnerCount();
      result = result * 59 + ($winnerCount == null ? 43 : $winnerCount.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $prizeName = this.getPrizeName();
      result = result * 59 + ($prizeName == null ? 43 : $prizeName.hashCode());
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
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      result = result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
      Object $winnersJson = this.getWinnersJson();
      result = result * 59 + ($winnersJson == null ? 43 : $winnersJson.hashCode());
      Object $winners = this.getWinners();
      return result * 59 + ($winners == null ? 43 : $winners.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLotteryResponse(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", title="
         + this.getTitle()
         + ", status="
         + this.getStatus()
         + ", prizeConfigId="
         + this.getPrizeConfigId()
         + ", prizeName="
         + this.getPrizeName()
         + ", prizeRequiredPoints="
         + this.getPrizeRequiredPoints()
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
         + ", winnerCount="
         + this.getWinnerCount()
         + ", winnersJson="
         + this.getWinnersJson()
         + ", winners="
         + this.getWinners()
         + ")";
   }

   public static class WinnerInfo implements Serializable {
      private Long userId;
      private String username;
      private String displayName;
      private Long entryId;

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
      public Long getEntryId() {
         return this.entryId;
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
      public void setEntryId(final Long entryId) {
         this.entryId = entryId;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof PointsBotLotteryResponse.WinnerInfo other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$entryId = this.getEntryId();
               Object other$entryId = other.getEntryId();
               if (this$entryId == null ? other$entryId == null : this$entryId.equals(other$entryId)) {
                  Object this$username = this.getUsername();
                  Object other$username = other.getUsername();
                  if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                     Object this$displayName = this.getDisplayName();
                     Object other$displayName = other.getDisplayName();
                     return this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName);
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
         return other instanceof PointsBotLotteryResponse.WinnerInfo;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $userId = this.getUserId();
         result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
         Object $entryId = this.getEntryId();
         result = result * 59 + ($entryId == null ? 43 : $entryId.hashCode());
         Object $username = this.getUsername();
         result = result * 59 + ($username == null ? 43 : $username.hashCode());
         Object $displayName = this.getDisplayName();
         return result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotLotteryResponse.WinnerInfo(userId="
            + this.getUserId()
            + ", username="
            + this.getUsername()
            + ", displayName="
            + this.getDisplayName()
            + ", entryId="
            + this.getEntryId()
            + ")";
      }
   }
}
