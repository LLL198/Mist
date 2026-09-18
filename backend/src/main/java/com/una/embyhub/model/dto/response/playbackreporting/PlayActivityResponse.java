package com.una.embyhub.model.dto.response.playbackreporting;

import com.alibaba.fastjson2.annotation.JSONField;
import java.io.Serializable;
import java.util.Map;
import lombok.Generated;

public class PlayActivityResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   @JSONField(
      name = "user_id"
   )
   private String userId;
   @JSONField(
      name = "user_name"
   )
   private String userName;
   @JSONField(
      name = "user_usage"
   )
   private Map<String, Integer> userUsage;

   @Generated
   public String getUserId() {
      return this.userId;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public Map<String, Integer> getUserUsage() {
      return this.userUsage;
   }

   @Generated
   public void setUserId(final String userId) {
      this.userId = userId;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
   }

   @Generated
   public void setUserUsage(final Map<String, Integer> userUsage) {
      this.userUsage = userUsage;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlayActivityResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userId = this.getUserId();
         Object other$userId = other.getUserId();
         if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
            Object this$userName = this.getUserName();
            Object other$userName = other.getUserName();
            if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
               Object this$userUsage = this.getUserUsage();
               Object other$userUsage = other.getUserUsage();
               return this$userUsage == null ? other$userUsage == null : this$userUsage.equals(other$userUsage);
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
      return other instanceof PlayActivityResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $userUsage = this.getUserUsage();
      return result * 59 + ($userUsage == null ? 43 : $userUsage.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PlayActivityResponse(userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", userUsage=" + this.getUserUsage() + ")";
   }
}
