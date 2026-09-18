package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_hell_bet")
public class PointsBotHellBet extends BaseEntity implements Serializable {
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
   @TableField("depth")
   private Integer depth;
   @TableField("user_id")
   private Long userId;
   @TableField("username")
   private String username;
   @TableField("display_name")
   private String displayName;
   @TableField("side")
   private String side;
   @TableField("bet_points")
   private Integer betPoints;
   @TableField("status")
   private String status;
   @TableField("payout_points")
   private Integer payoutPoints;
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
   public Integer getDepth() {
      return this.depth;
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
   public String getSide() {
      return this.side;
   }

   @Generated
   public Integer getBetPoints() {
      return this.betPoints;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Integer getPayoutPoints() {
      return this.payoutPoints;
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
   public void setDepth(final Integer depth) {
      this.depth = depth;
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
   public void setSide(final String side) {
      this.side = side;
   }

   @Generated
   public void setBetPoints(final Integer betPoints) {
      this.betPoints = betPoints;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setPayoutPoints(final Integer payoutPoints) {
      this.payoutPoints = payoutPoints;
   }

   @Generated
   public void setSettledAt(final LocalDateTime settledAt) {
      this.settledAt = settledAt;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotHellBet(id="
         + this.getId()
         + ", roundId="
         + this.getRoundId()
         + ", chatId="
         + this.getChatId()
         + ", depth="
         + this.getDepth()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", side="
         + this.getSide()
         + ", betPoints="
         + this.getBetPoints()
         + ", status="
         + this.getStatus()
         + ", payoutPoints="
         + this.getPayoutPoints()
         + ", settledAt="
         + this.getSettledAt()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotHellBet other)) {
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
                  Object this$depth = this.getDepth();
                  Object other$depth = other.getDepth();
                  if (this$depth == null ? other$depth == null : this$depth.equals(other$depth)) {
                     Object this$userId = this.getUserId();
                     Object other$userId = other.getUserId();
                     if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                        Object this$betPoints = this.getBetPoints();
                        Object other$betPoints = other.getBetPoints();
                        if (this$betPoints == null ? other$betPoints == null : this$betPoints.equals(other$betPoints)) {
                           Object this$payoutPoints = this.getPayoutPoints();
                           Object other$payoutPoints = other.getPayoutPoints();
                           if (this$payoutPoints == null ? other$payoutPoints == null : this$payoutPoints.equals(other$payoutPoints)) {
                              Object this$username = this.getUsername();
                              Object other$username = other.getUsername();
                              if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                                 Object this$displayName = this.getDisplayName();
                                 Object other$displayName = other.getDisplayName();
                                 if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                                    Object this$side = this.getSide();
                                    Object other$side = other.getSide();
                                    if (this$side == null ? other$side == null : this$side.equals(other$side)) {
                                       Object this$status = this.getStatus();
                                       Object other$status = other.getStatus();
                                       if (this$status == null ? other$status == null : this$status.equals(other$status)) {
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
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotHellBet;
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
      Object $depth = this.getDepth();
      result = result * 59 + ($depth == null ? 43 : $depth.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $betPoints = this.getBetPoints();
      result = result * 59 + ($betPoints == null ? 43 : $betPoints.hashCode());
      Object $payoutPoints = this.getPayoutPoints();
      result = result * 59 + ($payoutPoints == null ? 43 : $payoutPoints.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $side = this.getSide();
      result = result * 59 + ($side == null ? 43 : $side.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $settledAt = this.getSettledAt();
      return result * 59 + ($settledAt == null ? 43 : $settledAt.hashCode());
   }
}
