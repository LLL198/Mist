package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_hell_round")
public class PointsBotHellRound extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("message_id")
   private Long messageId;
   @TableField("player_user_id")
   private Long playerUserId;
   @TableField("player_username")
   private String playerUsername;
   @TableField("player_display_name")
   private String playerDisplayName;
   @TableField("status")
   private String status;
   @TableField(
      value = "active_guard",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private Integer activeGuard;
   @TableField("config_json")
   private String configJson;
   @TableField("bet_points")
   private Integer betPoints;
   @TableField("max_profit_liability")
   private Integer maxProfitLiability;
   @TableField("current_depth")
   private Integer currentDepth;
   @TableField("target_depth")
   private Integer targetDepth;
   @TableField("current_payout")
   private Integer currentPayout;
   @TableField(
      value = "last_dice_value",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private Integer lastDiceValue;
   @TableField(
      value = "betting_ends_at",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private LocalDateTime bettingEndsAt;
   @TableField(
      value = "decision_ends_at",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private LocalDateTime decisionEndsAt;
   @TableField("payout_points")
   private Integer payoutPoints;
   @TableField("vault_contribution_points")
   private Integer vaultContributionPoints;
   @TableField("settled_at")
   private LocalDateTime settledAt;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getMessageId() {
      return this.messageId;
   }

   @Generated
   public Long getPlayerUserId() {
      return this.playerUserId;
   }

   @Generated
   public String getPlayerUsername() {
      return this.playerUsername;
   }

   @Generated
   public String getPlayerDisplayName() {
      return this.playerDisplayName;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Integer getActiveGuard() {
      return this.activeGuard;
   }

   @Generated
   public String getConfigJson() {
      return this.configJson;
   }

   @Generated
   public Integer getBetPoints() {
      return this.betPoints;
   }

   @Generated
   public Integer getMaxProfitLiability() {
      return this.maxProfitLiability;
   }

   @Generated
   public Integer getCurrentDepth() {
      return this.currentDepth;
   }

   @Generated
   public Integer getTargetDepth() {
      return this.targetDepth;
   }

   @Generated
   public Integer getCurrentPayout() {
      return this.currentPayout;
   }

   @Generated
   public Integer getLastDiceValue() {
      return this.lastDiceValue;
   }

   @Generated
   public LocalDateTime getBettingEndsAt() {
      return this.bettingEndsAt;
   }

   @Generated
   public LocalDateTime getDecisionEndsAt() {
      return this.decisionEndsAt;
   }

   @Generated
   public Integer getPayoutPoints() {
      return this.payoutPoints;
   }

   @Generated
   public Integer getVaultContributionPoints() {
      return this.vaultContributionPoints;
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
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setMessageId(final Long messageId) {
      this.messageId = messageId;
   }

   @Generated
   public void setPlayerUserId(final Long playerUserId) {
      this.playerUserId = playerUserId;
   }

   @Generated
   public void setPlayerUsername(final String playerUsername) {
      this.playerUsername = playerUsername;
   }

   @Generated
   public void setPlayerDisplayName(final String playerDisplayName) {
      this.playerDisplayName = playerDisplayName;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setActiveGuard(final Integer activeGuard) {
      this.activeGuard = activeGuard;
   }

   @Generated
   public void setConfigJson(final String configJson) {
      this.configJson = configJson;
   }

   @Generated
   public void setBetPoints(final Integer betPoints) {
      this.betPoints = betPoints;
   }

   @Generated
   public void setMaxProfitLiability(final Integer maxProfitLiability) {
      this.maxProfitLiability = maxProfitLiability;
   }

   @Generated
   public void setCurrentDepth(final Integer currentDepth) {
      this.currentDepth = currentDepth;
   }

   @Generated
   public void setTargetDepth(final Integer targetDepth) {
      this.targetDepth = targetDepth;
   }

   @Generated
   public void setCurrentPayout(final Integer currentPayout) {
      this.currentPayout = currentPayout;
   }

   @Generated
   public void setLastDiceValue(final Integer lastDiceValue) {
      this.lastDiceValue = lastDiceValue;
   }

   @Generated
   public void setBettingEndsAt(final LocalDateTime bettingEndsAt) {
      this.bettingEndsAt = bettingEndsAt;
   }

   @Generated
   public void setDecisionEndsAt(final LocalDateTime decisionEndsAt) {
      this.decisionEndsAt = decisionEndsAt;
   }

   @Generated
   public void setPayoutPoints(final Integer payoutPoints) {
      this.payoutPoints = payoutPoints;
   }

   @Generated
   public void setVaultContributionPoints(final Integer vaultContributionPoints) {
      this.vaultContributionPoints = vaultContributionPoints;
   }

   @Generated
   public void setSettledAt(final LocalDateTime settledAt) {
      this.settledAt = settledAt;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotHellRound(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", messageId="
         + this.getMessageId()
         + ", playerUserId="
         + this.getPlayerUserId()
         + ", playerUsername="
         + this.getPlayerUsername()
         + ", playerDisplayName="
         + this.getPlayerDisplayName()
         + ", status="
         + this.getStatus()
         + ", activeGuard="
         + this.getActiveGuard()
         + ", configJson="
         + this.getConfigJson()
         + ", betPoints="
         + this.getBetPoints()
         + ", maxProfitLiability="
         + this.getMaxProfitLiability()
         + ", currentDepth="
         + this.getCurrentDepth()
         + ", targetDepth="
         + this.getTargetDepth()
         + ", currentPayout="
         + this.getCurrentPayout()
         + ", lastDiceValue="
         + this.getLastDiceValue()
         + ", bettingEndsAt="
         + this.getBettingEndsAt()
         + ", decisionEndsAt="
         + this.getDecisionEndsAt()
         + ", payoutPoints="
         + this.getPayoutPoints()
         + ", vaultContributionPoints="
         + this.getVaultContributionPoints()
         + ", settledAt="
         + this.getSettledAt()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotHellRound other)) {
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
               Object this$messageId = this.getMessageId();
               Object other$messageId = other.getMessageId();
               if (this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId)) {
                  Object this$playerUserId = this.getPlayerUserId();
                  Object other$playerUserId = other.getPlayerUserId();
                  if (this$playerUserId == null ? other$playerUserId == null : this$playerUserId.equals(other$playerUserId)) {
                     Object this$activeGuard = this.getActiveGuard();
                     Object other$activeGuard = other.getActiveGuard();
                     if (this$activeGuard == null ? other$activeGuard == null : this$activeGuard.equals(other$activeGuard)) {
                        Object this$betPoints = this.getBetPoints();
                        Object other$betPoints = other.getBetPoints();
                        if (this$betPoints == null ? other$betPoints == null : this$betPoints.equals(other$betPoints)) {
                           Object this$maxProfitLiability = this.getMaxProfitLiability();
                           Object other$maxProfitLiability = other.getMaxProfitLiability();
                           if (this$maxProfitLiability == null ? other$maxProfitLiability == null : this$maxProfitLiability.equals(other$maxProfitLiability)) {
                              Object this$currentDepth = this.getCurrentDepth();
                              Object other$currentDepth = other.getCurrentDepth();
                              if (this$currentDepth == null ? other$currentDepth == null : this$currentDepth.equals(other$currentDepth)) {
                                 Object this$targetDepth = this.getTargetDepth();
                                 Object other$targetDepth = other.getTargetDepth();
                                 if (this$targetDepth == null ? other$targetDepth == null : this$targetDepth.equals(other$targetDepth)) {
                                    Object this$currentPayout = this.getCurrentPayout();
                                    Object other$currentPayout = other.getCurrentPayout();
                                    if (this$currentPayout == null ? other$currentPayout == null : this$currentPayout.equals(other$currentPayout)) {
                                       Object this$lastDiceValue = this.getLastDiceValue();
                                       Object other$lastDiceValue = other.getLastDiceValue();
                                       if (this$lastDiceValue == null ? other$lastDiceValue == null : this$lastDiceValue.equals(other$lastDiceValue)) {
                                          Object this$payoutPoints = this.getPayoutPoints();
                                          Object other$payoutPoints = other.getPayoutPoints();
                                          if (this$payoutPoints == null ? other$payoutPoints == null : this$payoutPoints.equals(other$payoutPoints)) {
                                             Object this$vaultContributionPoints = this.getVaultContributionPoints();
                                             Object other$vaultContributionPoints = other.getVaultContributionPoints();
                                             if (this$vaultContributionPoints == null
                                                ? other$vaultContributionPoints == null
                                                : this$vaultContributionPoints.equals(other$vaultContributionPoints)) {
                                                Object this$playerUsername = this.getPlayerUsername();
                                                Object other$playerUsername = other.getPlayerUsername();
                                                if (this$playerUsername == null
                                                   ? other$playerUsername == null
                                                   : this$playerUsername.equals(other$playerUsername)) {
                                                   Object this$playerDisplayName = this.getPlayerDisplayName();
                                                   Object other$playerDisplayName = other.getPlayerDisplayName();
                                                   if (this$playerDisplayName == null
                                                      ? other$playerDisplayName == null
                                                      : this$playerDisplayName.equals(other$playerDisplayName)) {
                                                      Object this$status = this.getStatus();
                                                      Object other$status = other.getStatus();
                                                      if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                         Object this$configJson = this.getConfigJson();
                                                         Object other$configJson = other.getConfigJson();
                                                         if (this$configJson == null ? other$configJson == null : this$configJson.equals(other$configJson)) {
                                                            Object this$bettingEndsAt = this.getBettingEndsAt();
                                                            Object other$bettingEndsAt = other.getBettingEndsAt();
                                                            if (this$bettingEndsAt == null
                                                               ? other$bettingEndsAt == null
                                                               : this$bettingEndsAt.equals(other$bettingEndsAt)) {
                                                               Object this$decisionEndsAt = this.getDecisionEndsAt();
                                                               Object other$decisionEndsAt = other.getDecisionEndsAt();
                                                               if (this$decisionEndsAt == null
                                                                  ? other$decisionEndsAt == null
                                                                  : this$decisionEndsAt.equals(other$decisionEndsAt)) {
                                                                  Object this$settledAt = this.getSettledAt();
                                                                  Object other$settledAt = other.getSettledAt();
                                                                  return this$settledAt == null
                                                                     ? other$settledAt == null
                                                                     : this$settledAt.equals(other$settledAt);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotHellRound;
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
      Object $messageId = this.getMessageId();
      result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
      Object $playerUserId = this.getPlayerUserId();
      result = result * 59 + ($playerUserId == null ? 43 : $playerUserId.hashCode());
      Object $activeGuard = this.getActiveGuard();
      result = result * 59 + ($activeGuard == null ? 43 : $activeGuard.hashCode());
      Object $betPoints = this.getBetPoints();
      result = result * 59 + ($betPoints == null ? 43 : $betPoints.hashCode());
      Object $maxProfitLiability = this.getMaxProfitLiability();
      result = result * 59 + ($maxProfitLiability == null ? 43 : $maxProfitLiability.hashCode());
      Object $currentDepth = this.getCurrentDepth();
      result = result * 59 + ($currentDepth == null ? 43 : $currentDepth.hashCode());
      Object $targetDepth = this.getTargetDepth();
      result = result * 59 + ($targetDepth == null ? 43 : $targetDepth.hashCode());
      Object $currentPayout = this.getCurrentPayout();
      result = result * 59 + ($currentPayout == null ? 43 : $currentPayout.hashCode());
      Object $lastDiceValue = this.getLastDiceValue();
      result = result * 59 + ($lastDiceValue == null ? 43 : $lastDiceValue.hashCode());
      Object $payoutPoints = this.getPayoutPoints();
      result = result * 59 + ($payoutPoints == null ? 43 : $payoutPoints.hashCode());
      Object $vaultContributionPoints = this.getVaultContributionPoints();
      result = result * 59 + ($vaultContributionPoints == null ? 43 : $vaultContributionPoints.hashCode());
      Object $playerUsername = this.getPlayerUsername();
      result = result * 59 + ($playerUsername == null ? 43 : $playerUsername.hashCode());
      Object $playerDisplayName = this.getPlayerDisplayName();
      result = result * 59 + ($playerDisplayName == null ? 43 : $playerDisplayName.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $configJson = this.getConfigJson();
      result = result * 59 + ($configJson == null ? 43 : $configJson.hashCode());
      Object $bettingEndsAt = this.getBettingEndsAt();
      result = result * 59 + ($bettingEndsAt == null ? 43 : $bettingEndsAt.hashCode());
      Object $decisionEndsAt = this.getDecisionEndsAt();
      result = result * 59 + ($decisionEndsAt == null ? 43 : $decisionEndsAt.hashCode());
      Object $settledAt = this.getSettledAt();
      return result * 59 + ($settledAt == null ? 43 : $settledAt.hashCode());
   }
}
