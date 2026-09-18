package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class UserOauthBindingResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private String provider;
   private String providerUserId;
   private String providerUsername;
   private String providerAvatar;
   private Date createDatetime;

   @Generated
   public String getProvider() {
      return this.provider;
   }

   @Generated
   public String getProviderUserId() {
      return this.providerUserId;
   }

   @Generated
   public String getProviderUsername() {
      return this.providerUsername;
   }

   @Generated
   public String getProviderAvatar() {
      return this.providerAvatar;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public void setProvider(final String provider) {
      this.provider = provider;
   }

   @Generated
   public void setProviderUserId(final String providerUserId) {
      this.providerUserId = providerUserId;
   }

   @Generated
   public void setProviderUsername(final String providerUsername) {
      this.providerUsername = providerUsername;
   }

   @Generated
   public void setProviderAvatar(final String providerAvatar) {
      this.providerAvatar = providerAvatar;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserOauthBindingResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$provider = this.getProvider();
         Object other$provider = other.getProvider();
         if (this$provider == null ? other$provider == null : this$provider.equals(other$provider)) {
            Object this$providerUserId = this.getProviderUserId();
            Object other$providerUserId = other.getProviderUserId();
            if (this$providerUserId == null ? other$providerUserId == null : this$providerUserId.equals(other$providerUserId)) {
               Object this$providerUsername = this.getProviderUsername();
               Object other$providerUsername = other.getProviderUsername();
               if (this$providerUsername == null ? other$providerUsername == null : this$providerUsername.equals(other$providerUsername)) {
                  Object this$providerAvatar = this.getProviderAvatar();
                  Object other$providerAvatar = other.getProviderAvatar();
                  if (this$providerAvatar == null ? other$providerAvatar == null : this$providerAvatar.equals(other$providerAvatar)) {
                     Object this$createDatetime = this.getCreateDatetime();
                     Object other$createDatetime = other.getCreateDatetime();
                     return this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime);
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
      return other instanceof UserOauthBindingResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $provider = this.getProvider();
      result = result * 59 + ($provider == null ? 43 : $provider.hashCode());
      Object $providerUserId = this.getProviderUserId();
      result = result * 59 + ($providerUserId == null ? 43 : $providerUserId.hashCode());
      Object $providerUsername = this.getProviderUsername();
      result = result * 59 + ($providerUsername == null ? 43 : $providerUsername.hashCode());
      Object $providerAvatar = this.getProviderAvatar();
      result = result * 59 + ($providerAvatar == null ? 43 : $providerAvatar.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserOauthBindingResponse(provider="
         + this.getProvider()
         + ", providerUserId="
         + this.getProviderUserId()
         + ", providerUsername="
         + this.getProviderUsername()
         + ", providerAvatar="
         + this.getProviderAvatar()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
