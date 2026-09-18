package com.una.embyhub.model.dto.request.pointsbot;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotUserRequest implements Serializable {
   private Long chatId;
   private Long userId;
   @BindQuery(
      comparison = Comparison.STARTSWITH
   )
   private String username;
   private String displayName;
   private Long levelId;
   @BindQuery(
      ignore = true
   )
   private String embyUserName;

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
   public Long getLevelId() {
      return this.levelId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
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
   public void setLevelId(final Long levelId) {
      this.levelId = levelId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotUserRequest other)) {
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
               Object this$levelId = this.getLevelId();
               Object other$levelId = other.getLevelId();
               if (this$levelId == null ? other$levelId == null : this$levelId.equals(other$levelId)) {
                  Object this$username = this.getUsername();
                  Object other$username = other.getUsername();
                  if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                     Object this$displayName = this.getDisplayName();
                     Object other$displayName = other.getDisplayName();
                     if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                        Object this$embyUserName = this.getEmbyUserName();
                        Object other$embyUserName = other.getEmbyUserName();
                        return this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName);
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
      return other instanceof PointsBotUserRequest;
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
      Object $levelId = this.getLevelId();
      result = result * 59 + ($levelId == null ? 43 : $levelId.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotUserRequest(chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", levelId="
         + this.getLevelId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ")";
   }
}
