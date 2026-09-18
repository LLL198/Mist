package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_brain_entry")
public class PointsBotBrainEntry extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("round_id")
   private Long roundId;
   @TableField("chat_id")
   private Long chatId;
   @TableField("user_id")
   private Long userId;
   @TableField("username")
   private String username;
   @TableField("display_name")
   private String displayName;
   @TableField("entry_cost")
   private Integer entryCost;
   @TableField("submission_count")
   private Integer submissionCount;
   @TableField("is_correct")
   private Boolean correct;
   @TableField("correct_at")
   private LocalDateTime correctAt;
   @TableField("effective_correct_at")
   private LocalDateTime effectiveCorrectAt;
   @TableField("rank_no")
   private Integer rankNo;
   @TableField("reward_points")
   private Integer rewardPoints;
   @TableField("counted_play")
   private Boolean countedPlay;
   @TableField("settled_at")
   private LocalDateTime settledAt;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getRoundId() {
      return this.roundId;
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
   public Integer getEntryCost() {
      return this.entryCost;
   }

   @Generated
   public Integer getSubmissionCount() {
      return this.submissionCount;
   }

   @Generated
   public Boolean getCorrect() {
      return this.correct;
   }

   @Generated
   public LocalDateTime getCorrectAt() {
      return this.correctAt;
   }

   @Generated
   public LocalDateTime getEffectiveCorrectAt() {
      return this.effectiveCorrectAt;
   }

   @Generated
   public Integer getRankNo() {
      return this.rankNo;
   }

   @Generated
   public Integer getRewardPoints() {
      return this.rewardPoints;
   }

   @Generated
   public Boolean getCountedPlay() {
      return this.countedPlay;
   }

   @Generated
   public LocalDateTime getSettledAt() {
      return this.settledAt;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setRoundId(final Long roundId) {
      this.roundId = roundId;
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
   public void setEntryCost(final Integer entryCost) {
      this.entryCost = entryCost;
   }

   @Generated
   public void setSubmissionCount(final Integer submissionCount) {
      this.submissionCount = submissionCount;
   }

   @Generated
   public void setCorrect(final Boolean correct) {
      this.correct = correct;
   }

   @Generated
   public void setCorrectAt(final LocalDateTime correctAt) {
      this.correctAt = correctAt;
   }

   @Generated
   public void setEffectiveCorrectAt(final LocalDateTime effectiveCorrectAt) {
      this.effectiveCorrectAt = effectiveCorrectAt;
   }

   @Generated
   public void setRankNo(final Integer rankNo) {
      this.rankNo = rankNo;
   }

   @Generated
   public void setRewardPoints(final Integer rewardPoints) {
      this.rewardPoints = rewardPoints;
   }

   @Generated
   public void setCountedPlay(final Boolean countedPlay) {
      this.countedPlay = countedPlay;
   }

   @Generated
   public void setSettledAt(final LocalDateTime settledAt) {
      this.settledAt = settledAt;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotBrainEntry(id="
         + this.getId()
         + ", roundId="
         + this.getRoundId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", entryCost="
         + this.getEntryCost()
         + ", submissionCount="
         + this.getSubmissionCount()
         + ", correct="
         + this.getCorrect()
         + ", correctAt="
         + this.getCorrectAt()
         + ", effectiveCorrectAt="
         + this.getEffectiveCorrectAt()
         + ", rankNo="
         + this.getRankNo()
         + ", rewardPoints="
         + this.getRewardPoints()
         + ", countedPlay="
         + this.getCountedPlay()
         + ", settledAt="
         + this.getSettledAt()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotBrainEntry other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$roundId = this.getRoundId();
            Object other$roundId = other.getRoundId();
            if (this$roundId == null ? other$roundId == null : this$roundId.equals(other$roundId)) {
               Object this$chatId = this.getChatId();
               Object other$chatId = other.getChatId();
               if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
                  Object this$userId = this.getUserId();
                  Object other$userId = other.getUserId();
                  if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                     Object this$entryCost = this.getEntryCost();
                     Object other$entryCost = other.getEntryCost();
                     if (this$entryCost == null ? other$entryCost == null : this$entryCost.equals(other$entryCost)) {
                        Object this$submissionCount = this.getSubmissionCount();
                        Object other$submissionCount = other.getSubmissionCount();
                        if (this$submissionCount == null ? other$submissionCount == null : this$submissionCount.equals(other$submissionCount)) {
                           Object this$correct = this.getCorrect();
                           Object other$correct = other.getCorrect();
                           if (this$correct == null ? other$correct == null : this$correct.equals(other$correct)) {
                              Object this$rankNo = this.getRankNo();
                              Object other$rankNo = other.getRankNo();
                              if (this$rankNo == null ? other$rankNo == null : this$rankNo.equals(other$rankNo)) {
                                 Object this$rewardPoints = this.getRewardPoints();
                                 Object other$rewardPoints = other.getRewardPoints();
                                 if (this$rewardPoints == null ? other$rewardPoints == null : this$rewardPoints.equals(other$rewardPoints)) {
                                    Object this$countedPlay = this.getCountedPlay();
                                    Object other$countedPlay = other.getCountedPlay();
                                    if (this$countedPlay == null ? other$countedPlay == null : this$countedPlay.equals(other$countedPlay)) {
                                       Object this$username = this.getUsername();
                                       Object other$username = other.getUsername();
                                       if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                                          Object this$displayName = this.getDisplayName();
                                          Object other$displayName = other.getDisplayName();
                                          if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                                             Object this$correctAt = this.getCorrectAt();
                                             Object other$correctAt = other.getCorrectAt();
                                             if (this$correctAt == null ? other$correctAt == null : this$correctAt.equals(other$correctAt)) {
                                                Object this$effectiveCorrectAt = this.getEffectiveCorrectAt();
                                                Object other$effectiveCorrectAt = other.getEffectiveCorrectAt();
                                                if (this$effectiveCorrectAt == null
                                                   ? other$effectiveCorrectAt == null
                                                   : this$effectiveCorrectAt.equals(other$effectiveCorrectAt)) {
                                                   Object this$settledAt = this.getSettledAt();
                                                   Object other$settledAt = other.getSettledAt();
                                                   return this$settledAt == null ? other$settledAt == null : this$settledAt.equals(other$settledAt);
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
      return other instanceof PointsBotBrainEntry;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $roundId = this.getRoundId();
      result = result * 59 + ($roundId == null ? 43 : $roundId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $entryCost = this.getEntryCost();
      result = result * 59 + ($entryCost == null ? 43 : $entryCost.hashCode());
      Object $submissionCount = this.getSubmissionCount();
      result = result * 59 + ($submissionCount == null ? 43 : $submissionCount.hashCode());
      Object $correct = this.getCorrect();
      result = result * 59 + ($correct == null ? 43 : $correct.hashCode());
      Object $rankNo = this.getRankNo();
      result = result * 59 + ($rankNo == null ? 43 : $rankNo.hashCode());
      Object $rewardPoints = this.getRewardPoints();
      result = result * 59 + ($rewardPoints == null ? 43 : $rewardPoints.hashCode());
      Object $countedPlay = this.getCountedPlay();
      result = result * 59 + ($countedPlay == null ? 43 : $countedPlay.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $correctAt = this.getCorrectAt();
      result = result * 59 + ($correctAt == null ? 43 : $correctAt.hashCode());
      Object $effectiveCorrectAt = this.getEffectiveCorrectAt();
      result = result * 59 + ($effectiveCorrectAt == null ? 43 : $effectiveCorrectAt.hashCode());
      Object $settledAt = this.getSettledAt();
      return result * 59 + ($settledAt == null ? 43 : $settledAt.hashCode());
   }
}
