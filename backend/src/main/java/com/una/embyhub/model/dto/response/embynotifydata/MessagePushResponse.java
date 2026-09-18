package com.una.embyhub.model.dto.response.embynotifydata;

import java.io.Serializable;
import lombok.Generated;

public class MessagePushResponse implements Serializable {
   private String userId;
   private String userKey;

   @Generated
   public String getUserId() {
      return this.userId;
   }

   @Generated
   public String getUserKey() {
      return this.userKey;
   }

   @Generated
   public void setUserId(final String userId) {
      this.userId = userId;
   }

   @Generated
   public void setUserKey(final String userKey) {
      this.userKey = userKey;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MessagePushResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userId = this.getUserId();
         Object other$userId = other.getUserId();
         if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
            Object this$userKey = this.getUserKey();
            Object other$userKey = other.getUserKey();
            return this$userKey == null ? other$userKey == null : this$userKey.equals(other$userKey);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof MessagePushResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $userKey = this.getUserKey();
      return result * 59 + ($userKey == null ? 43 : $userKey.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MessagePushResponse(userId=" + this.getUserId() + ", userKey=" + this.getUserKey() + ")";
   }
}
