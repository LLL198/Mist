package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotLotteryRequest implements Serializable {
   private Long chatId;
   private String title;
   private String status;

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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLotteryRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$chatId = this.getChatId();
         Object other$chatId = other.getChatId();
         if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null ? other$title == null : this$title.equals(other$title)) {
               Object this$status = this.getStatus();
               Object other$status = other.getStatus();
               return this$status == null ? other$status == null : this$status.equals(other$status);
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
      return other instanceof PointsBotLotteryRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $status = this.getStatus();
      return result * 59 + ($status == null ? 43 : $status.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLotteryRequest(chatId=" + this.getChatId() + ", title=" + this.getTitle() + ", status=" + this.getStatus() + ")";
   }
}
