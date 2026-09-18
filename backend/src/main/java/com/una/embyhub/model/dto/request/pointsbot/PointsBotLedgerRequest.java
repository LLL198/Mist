package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotLedgerRequest implements Serializable {
   private Long chatId;
   private Long userId;
   private String reason;

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getReason() {
      return this.reason;
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
   public void setReason(final String reason) {
      this.reason = reason;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLedgerRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$chatId = this.getChatId();
         Object other$chatId = other.getChatId();
         if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$reason = this.getReason();
               Object other$reason = other.getReason();
               return this$reason == null ? other$reason == null : this$reason.equals(other$reason);
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
      return other instanceof PointsBotLedgerRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $reason = this.getReason();
      return result * 59 + ($reason == null ? 43 : $reason.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLedgerRequest(chatId=" + this.getChatId() + ", userId=" + this.getUserId() + ", reason=" + this.getReason() + ")";
   }
}
