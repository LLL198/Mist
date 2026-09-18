package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_scratch_entry")
public class PointsBotScratchEntry extends BaseEntity implements Serializable {
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
   @TableField("cell_number")
   private Integer cellNumber;
   @TableField("user_id")
   private Long userId;
   @TableField("username")
   private String username;
   @TableField("display_name")
   private String displayName;
   @TableField("reward_points")
   private Integer rewardPoints;
   @TableField("is_jackpot")
   private Boolean jackpot;
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
   public Integer getCellNumber() {
      return this.cellNumber;
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
   public Integer getRewardPoints() {
      return this.rewardPoints;
   }

   @Generated
   public Boolean getJackpot() {
      return this.jackpot;
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
   public void setCellNumber(final Integer cellNumber) {
      this.cellNumber = cellNumber;
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
   public void setRewardPoints(final Integer rewardPoints) {
      this.rewardPoints = rewardPoints;
   }

   @Generated
   public void setJackpot(final Boolean jackpot) {
      this.jackpot = jackpot;
   }

   @Generated
   public void setSettledAt(final LocalDateTime settledAt) {
      this.settledAt = settledAt;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotScratchEntry(id="
         + this.getId()
         + ", roundId="
         + this.getRoundId()
         + ", chatId="
         + this.getChatId()
         + ", cellNumber="
         + this.getCellNumber()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", rewardPoints="
         + this.getRewardPoints()
         + ", jackpot="
         + this.getJackpot()
         + ", settledAt="
         + this.getSettledAt()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotScratchEntry other)) {
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
                  Object this$cellNumber = this.getCellNumber();
                  Object other$cellNumber = other.getCellNumber();
                  if (this$cellNumber == null ? other$cellNumber == null : this$cellNumber.equals(other$cellNumber)) {
                     Object this$userId = this.getUserId();
                     Object other$userId = other.getUserId();
                     if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                        Object this$rewardPoints = this.getRewardPoints();
                        Object other$rewardPoints = other.getRewardPoints();
                        if (this$rewardPoints == null ? other$rewardPoints == null : this$rewardPoints.equals(other$rewardPoints)) {
                           Object this$jackpot = this.getJackpot();
                           Object other$jackpot = other.getJackpot();
                           if (this$jackpot == null ? other$jackpot == null : this$jackpot.equals(other$jackpot)) {
                              Object this$username = this.getUsername();
                              Object other$username = other.getUsername();
                              if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                                 Object this$displayName = this.getDisplayName();
                                 Object other$displayName = other.getDisplayName();
                                 if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
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
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotScratchEntry;
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
      Object $cellNumber = this.getCellNumber();
      result = result * 59 + ($cellNumber == null ? 43 : $cellNumber.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $rewardPoints = this.getRewardPoints();
      result = result * 59 + ($rewardPoints == null ? 43 : $rewardPoints.hashCode());
      Object $jackpot = this.getJackpot();
      result = result * 59 + ($jackpot == null ? 43 : $jackpot.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $settledAt = this.getSettledAt();
      return result * 59 + ($settledAt == null ? 43 : $settledAt.hashCode());
   }
}
