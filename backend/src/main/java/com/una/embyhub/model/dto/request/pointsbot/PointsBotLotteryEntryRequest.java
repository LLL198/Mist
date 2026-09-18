package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotLotteryEntryRequest implements Serializable {
   private Long lotteryId;
   private Long chatId;
   private Long userId;

   @Generated
   public Long getLotteryId() {
      return this.lotteryId;
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
   public void setLotteryId(final Long lotteryId) {
      this.lotteryId = lotteryId;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLotteryEntryRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$lotteryId = this.getLotteryId();
         Object other$lotteryId = other.getLotteryId();
         if (this$lotteryId == null ? other$lotteryId == null : this$lotteryId.equals(other$lotteryId)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               return this$userId == null ? other$userId == null : this$userId.equals(other$userId);
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
      return other instanceof PointsBotLotteryEntryRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $lotteryId = this.getLotteryId();
      result = result * 59 + ($lotteryId == null ? 43 : $lotteryId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $userId = this.getUserId();
      return result * 59 + ($userId == null ? 43 : $userId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLotteryEntryRequest(lotteryId=" + this.getLotteryId() + ", chatId=" + this.getChatId() + ", userId=" + this.getUserId() + ")";
   }
}
