package com.una.embyhub.pointsbot.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class BlackjackSession {
   private Long userId;
   private Long chatId;
   private int betAmount;
   private Long messageId;
   private List<BlackjackCard> playerHand;
   private List<BlackjackCard> dealerHand;
   private BlackjackSession.GameStatus status;
   private LocalDateTime lastActiveTime;
   private String settlementResult;

   public void addPlayerCard(BlackjackCard card) {
      this.playerHand.add(card);
   }

   public void addDealerCard(BlackjackCard card) {
      this.dealerHand.add(card);
   }

   @Generated
   private static List<BlackjackCard> $default$playerHand() {
      return new ArrayList<>();
   }

   @Generated
   private static List<BlackjackCard> $default$dealerHand() {
      return new ArrayList<>();
   }

   @Generated
   private static BlackjackSession.GameStatus $default$status() {
      return BlackjackSession.GameStatus.PLAYER_TURN;
   }

   @Generated
   private static LocalDateTime $default$lastActiveTime() {
      return LocalDateTime.now();
   }

   @Generated
   public static BlackjackSession.BlackjackSessionBuilder builder() {
      return new BlackjackSession.BlackjackSessionBuilder();
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public int getBetAmount() {
      return this.betAmount;
   }

   @Generated
   public Long getMessageId() {
      return this.messageId;
   }

   @Generated
   public List<BlackjackCard> getPlayerHand() {
      return this.playerHand;
   }

   @Generated
   public List<BlackjackCard> getDealerHand() {
      return this.dealerHand;
   }

   @Generated
   public BlackjackSession.GameStatus getStatus() {
      return this.status;
   }

   @Generated
   public LocalDateTime getLastActiveTime() {
      return this.lastActiveTime;
   }

   @Generated
   public String getSettlementResult() {
      return this.settlementResult;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setBetAmount(final int betAmount) {
      this.betAmount = betAmount;
   }

   @Generated
   public void setMessageId(final Long messageId) {
      this.messageId = messageId;
   }

   @Generated
   public void setPlayerHand(final List<BlackjackCard> playerHand) {
      this.playerHand = playerHand;
   }

   @Generated
   public void setDealerHand(final List<BlackjackCard> dealerHand) {
      this.dealerHand = dealerHand;
   }

   @Generated
   public void setStatus(final BlackjackSession.GameStatus status) {
      this.status = status;
   }

   @Generated
   public void setLastActiveTime(final LocalDateTime lastActiveTime) {
      this.lastActiveTime = lastActiveTime;
   }

   @Generated
   public void setSettlementResult(final String settlementResult) {
      this.settlementResult = settlementResult;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BlackjackSession other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getBetAmount() != other.getBetAmount()) {
         return false;
      } else {
         Object this$userId = this.getUserId();
         Object other$userId = other.getUserId();
         if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$messageId = this.getMessageId();
               Object other$messageId = other.getMessageId();
               if (this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId)) {
                  Object this$playerHand = this.getPlayerHand();
                  Object other$playerHand = other.getPlayerHand();
                  if (this$playerHand == null ? other$playerHand == null : this$playerHand.equals(other$playerHand)) {
                     Object this$dealerHand = this.getDealerHand();
                     Object other$dealerHand = other.getDealerHand();
                     if (this$dealerHand == null ? other$dealerHand == null : this$dealerHand.equals(other$dealerHand)) {
                        Object this$status = this.getStatus();
                        Object other$status = other.getStatus();
                        if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                           Object this$lastActiveTime = this.getLastActiveTime();
                           Object other$lastActiveTime = other.getLastActiveTime();
                           if (this$lastActiveTime == null ? other$lastActiveTime == null : this$lastActiveTime.equals(other$lastActiveTime)) {
                              Object this$settlementResult = this.getSettlementResult();
                              Object other$settlementResult = other.getSettlementResult();
                              return this$settlementResult == null ? other$settlementResult == null : this$settlementResult.equals(other$settlementResult);
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
      return other instanceof BlackjackSession;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getBetAmount();
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $messageId = this.getMessageId();
      result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
      Object $playerHand = this.getPlayerHand();
      result = result * 59 + ($playerHand == null ? 43 : $playerHand.hashCode());
      Object $dealerHand = this.getDealerHand();
      result = result * 59 + ($dealerHand == null ? 43 : $dealerHand.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $lastActiveTime = this.getLastActiveTime();
      result = result * 59 + ($lastActiveTime == null ? 43 : $lastActiveTime.hashCode());
      Object $settlementResult = this.getSettlementResult();
      return result * 59 + ($settlementResult == null ? 43 : $settlementResult.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "BlackjackSession(userId="
         + this.getUserId()
         + ", chatId="
         + this.getChatId()
         + ", betAmount="
         + this.getBetAmount()
         + ", messageId="
         + this.getMessageId()
         + ", playerHand="
         + this.getPlayerHand()
         + ", dealerHand="
         + this.getDealerHand()
         + ", status="
         + this.getStatus()
         + ", lastActiveTime="
         + this.getLastActiveTime()
         + ", settlementResult="
         + this.getSettlementResult()
         + ")";
   }

   @Generated
   public BlackjackSession(
      final Long userId,
      final Long chatId,
      final int betAmount,
      final Long messageId,
      final List<BlackjackCard> playerHand,
      final List<BlackjackCard> dealerHand,
      final BlackjackSession.GameStatus status,
      final LocalDateTime lastActiveTime,
      final String settlementResult
   ) {
      this.userId = userId;
      this.chatId = chatId;
      this.betAmount = betAmount;
      this.messageId = messageId;
      this.playerHand = playerHand;
      this.dealerHand = dealerHand;
      this.status = status;
      this.lastActiveTime = lastActiveTime;
      this.settlementResult = settlementResult;
   }

   @Generated
   public BlackjackSession() {
      this.playerHand = $default$playerHand();
      this.dealerHand = $default$dealerHand();
      this.status = $default$status();
      this.lastActiveTime = $default$lastActiveTime();
   }

   @Generated
   public static class BlackjackSessionBuilder {
      @Generated
      private Long userId;
      @Generated
      private Long chatId;
      @Generated
      private int betAmount;
      @Generated
      private Long messageId;
      @Generated
      private boolean playerHand$set;
      @Generated
      private List<BlackjackCard> playerHand$value;
      @Generated
      private boolean dealerHand$set;
      @Generated
      private List<BlackjackCard> dealerHand$value;
      @Generated
      private boolean status$set;
      @Generated
      private BlackjackSession.GameStatus status$value;
      @Generated
      private boolean lastActiveTime$set;
      @Generated
      private LocalDateTime lastActiveTime$value;
      @Generated
      private String settlementResult;

      @Generated
      BlackjackSessionBuilder() {
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder userId(final Long userId) {
         this.userId = userId;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder chatId(final Long chatId) {
         this.chatId = chatId;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder betAmount(final int betAmount) {
         this.betAmount = betAmount;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder messageId(final Long messageId) {
         this.messageId = messageId;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder playerHand(final List<BlackjackCard> playerHand) {
         this.playerHand$value = playerHand;
         this.playerHand$set = true;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder dealerHand(final List<BlackjackCard> dealerHand) {
         this.dealerHand$value = dealerHand;
         this.dealerHand$set = true;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder status(final BlackjackSession.GameStatus status) {
         this.status$value = status;
         this.status$set = true;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder lastActiveTime(final LocalDateTime lastActiveTime) {
         this.lastActiveTime$value = lastActiveTime;
         this.lastActiveTime$set = true;
         return this;
      }

      @Generated
      public BlackjackSession.BlackjackSessionBuilder settlementResult(final String settlementResult) {
         this.settlementResult = settlementResult;
         return this;
      }

      @Generated
      public BlackjackSession build() {
         List<BlackjackCard> playerHand$value = this.playerHand$value;
         if (!this.playerHand$set) {
            playerHand$value = BlackjackSession.$default$playerHand();
         }

         List<BlackjackCard> dealerHand$value = this.dealerHand$value;
         if (!this.dealerHand$set) {
            dealerHand$value = BlackjackSession.$default$dealerHand();
         }

         BlackjackSession.GameStatus status$value = this.status$value;
         if (!this.status$set) {
            status$value = BlackjackSession.$default$status();
         }

         LocalDateTime lastActiveTime$value = this.lastActiveTime$value;
         if (!this.lastActiveTime$set) {
            lastActiveTime$value = BlackjackSession.$default$lastActiveTime();
         }

         return new BlackjackSession(
            this.userId,
            this.chatId,
            this.betAmount,
            this.messageId,
            playerHand$value,
            dealerHand$value,
            status$value,
            lastActiveTime$value,
            this.settlementResult
         );
      }

      @Generated
      @Override
      public String toString() {
         return "BlackjackSession.BlackjackSessionBuilder(userId="
            + this.userId
            + ", chatId="
            + this.chatId
            + ", betAmount="
            + this.betAmount
            + ", messageId="
            + this.messageId
            + ", playerHand$value="
            + this.playerHand$value
            + ", dealerHand$value="
            + this.dealerHand$value
            + ", status$value="
            + this.status$value
            + ", lastActiveTime$value="
            + this.lastActiveTime$value
            + ", settlementResult="
            + this.settlementResult
            + ")";
      }
   }

   public static enum GameStatus {
      PLAYER_TURN,
      DEALER_TURN,
      FINISHED;
   }
}
