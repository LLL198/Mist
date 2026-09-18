package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotFoamBagRequest implements Serializable {
   private Long chatId;
   private Long userId;
   private String keyword;
   private String username;
   private String displayName;
   private String status;

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
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
   public String getStatus() {
      return this.status;
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
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
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
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotFoamBagRequest other)) {
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
               Object this$keyword = this.getKeyword();
               Object other$keyword = other.getKeyword();
               if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                  Object this$username = this.getUsername();
                  Object other$username = other.getUsername();
                  if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                     Object this$displayName = this.getDisplayName();
                     Object other$displayName = other.getDisplayName();
                     if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
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
      return other instanceof PointsBotFoamBagRequest;
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
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $status = this.getStatus();
      return result * 59 + ($status == null ? 43 : $status.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotFoamBagRequest(chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", keyword="
         + this.getKeyword()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", status="
         + this.getStatus()
         + ")";
   }
}
