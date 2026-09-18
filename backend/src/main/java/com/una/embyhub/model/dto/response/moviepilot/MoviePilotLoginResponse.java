package com.una.embyhub.model.dto.response.moviepilot;

import com.alibaba.fastjson2.annotation.JSONField;
import java.util.Map;
import lombok.Generated;

public class MoviePilotLoginResponse {
   @JSONField(
      name = "access_token"
   )
   private String accessToken;
   @JSONField(
      name = "token_type"
   )
   private String tokenType;
   @JSONField(
      name = "super_user"
   )
   private Boolean superUser;
   @JSONField(
      name = "user_id"
   )
   private Long userId;
   @JSONField(
      name = "user_name"
   )
   private String userName;
   private String avatar;
   private Integer level;
   private Map<String, Object> permissions;
   private Boolean widzard;

   @Generated
   public String getAccessToken() {
      return this.accessToken;
   }

   @Generated
   public String getTokenType() {
      return this.tokenType;
   }

   @Generated
   public Boolean getSuperUser() {
      return this.superUser;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public String getAvatar() {
      return this.avatar;
   }

   @Generated
   public Integer getLevel() {
      return this.level;
   }

   @Generated
   public Map<String, Object> getPermissions() {
      return this.permissions;
   }

   @Generated
   public Boolean getWidzard() {
      return this.widzard;
   }

   @Generated
   public void setAccessToken(final String accessToken) {
      this.accessToken = accessToken;
   }

   @Generated
   public void setTokenType(final String tokenType) {
      this.tokenType = tokenType;
   }

   @Generated
   public void setSuperUser(final Boolean superUser) {
      this.superUser = superUser;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
   }

   @Generated
   public void setAvatar(final String avatar) {
      this.avatar = avatar;
   }

   @Generated
   public void setLevel(final Integer level) {
      this.level = level;
   }

   @Generated
   public void setPermissions(final Map<String, Object> permissions) {
      this.permissions = permissions;
   }

   @Generated
   public void setWidzard(final Boolean widzard) {
      this.widzard = widzard;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePilotLoginResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$superUser = this.getSuperUser();
         Object other$superUser = other.getSuperUser();
         if (this$superUser == null ? other$superUser == null : this$superUser.equals(other$superUser)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$level = this.getLevel();
               Object other$level = other.getLevel();
               if (this$level == null ? other$level == null : this$level.equals(other$level)) {
                  Object this$widzard = this.getWidzard();
                  Object other$widzard = other.getWidzard();
                  if (this$widzard == null ? other$widzard == null : this$widzard.equals(other$widzard)) {
                     Object this$accessToken = this.getAccessToken();
                     Object other$accessToken = other.getAccessToken();
                     if (this$accessToken == null ? other$accessToken == null : this$accessToken.equals(other$accessToken)) {
                        Object this$tokenType = this.getTokenType();
                        Object other$tokenType = other.getTokenType();
                        if (this$tokenType == null ? other$tokenType == null : this$tokenType.equals(other$tokenType)) {
                           Object this$userName = this.getUserName();
                           Object other$userName = other.getUserName();
                           if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                              Object this$avatar = this.getAvatar();
                              Object other$avatar = other.getAvatar();
                              if (this$avatar == null ? other$avatar == null : this$avatar.equals(other$avatar)) {
                                 Object this$permissions = this.getPermissions();
                                 Object other$permissions = other.getPermissions();
                                 return this$permissions == null ? other$permissions == null : this$permissions.equals(other$permissions);
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
   protected boolean canEqual(final Object other) {
      return other instanceof MoviePilotLoginResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $superUser = this.getSuperUser();
      result = result * 59 + ($superUser == null ? 43 : $superUser.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $level = this.getLevel();
      result = result * 59 + ($level == null ? 43 : $level.hashCode());
      Object $widzard = this.getWidzard();
      result = result * 59 + ($widzard == null ? 43 : $widzard.hashCode());
      Object $accessToken = this.getAccessToken();
      result = result * 59 + ($accessToken == null ? 43 : $accessToken.hashCode());
      Object $tokenType = this.getTokenType();
      result = result * 59 + ($tokenType == null ? 43 : $tokenType.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $avatar = this.getAvatar();
      result = result * 59 + ($avatar == null ? 43 : $avatar.hashCode());
      Object $permissions = this.getPermissions();
      return result * 59 + ($permissions == null ? 43 : $permissions.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePilotLoginResponse(accessToken="
         + this.getAccessToken()
         + ", tokenType="
         + this.getTokenType()
         + ", superUser="
         + this.getSuperUser()
         + ", userId="
         + this.getUserId()
         + ", userName="
         + this.getUserName()
         + ", avatar="
         + this.getAvatar()
         + ", level="
         + this.getLevel()
         + ", permissions="
         + this.getPermissions()
         + ", widzard="
         + this.getWidzard()
         + ")";
   }
}
