package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotRedPacketRequest implements Serializable {
   private Long chatId;
   private Long creatorUserId;
   private String keyword;
   private String status;

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getCreatorUserId() {
      return this.creatorUserId;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
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
   public void setCreatorUserId(final Long creatorUserId) {
      this.creatorUserId = creatorUserId;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
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
      } else if (!(o instanceof PointsBotRedPacketRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$chatId = this.getChatId();
         Object other$chatId = other.getChatId();
         if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
            Object this$creatorUserId = this.getCreatorUserId();
            Object other$creatorUserId = other.getCreatorUserId();
            if (this$creatorUserId == null ? other$creatorUserId == null : this$creatorUserId.equals(other$creatorUserId)) {
               Object this$keyword = this.getKeyword();
               Object other$keyword = other.getKeyword();
               if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                  Object this$status = this.getStatus();
                  Object other$status = other.getStatus();
                  return this$status == null ? other$status == null : this$status.equals(other$status);
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
      return other instanceof PointsBotRedPacketRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $creatorUserId = this.getCreatorUserId();
      result = result * 59 + ($creatorUserId == null ? 43 : $creatorUserId.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $status = this.getStatus();
      return result * 59 + ($status == null ? 43 : $status.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotRedPacketRequest(chatId="
         + this.getChatId()
         + ", creatorUserId="
         + this.getCreatorUserId()
         + ", keyword="
         + this.getKeyword()
         + ", status="
         + this.getStatus()
         + ")";
   }
}
