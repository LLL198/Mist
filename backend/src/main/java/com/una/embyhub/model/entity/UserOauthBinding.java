package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Generated;

@TableName("user_oauth_binding")
public class UserOauthBinding extends BaseEntity {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("user_id")
   private Long userId;
   @TableField("provider")
   private String provider;
   @TableField("provider_user_id")
   private String providerUserId;
   @TableField("provider_username")
   private String providerUsername;
   @TableField("provider_avatar")
   private String providerAvatar;
   @TableField("extra_data")
   private String extraData;
   public static final String PROVIDER_TELEGRAM = "telegram";
   public static final String PROVIDER_WECHAT = "wechat";
   public static final String PROVIDER_GOOGLE = "google";
   public static final String COL_ID = "id";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_PROVIDER = "provider";
   public static final String COL_PROVIDER_USER_ID = "provider_user_id";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

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
   public String getExtraData() {
      return this.extraData;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
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
   public void setExtraData(final String extraData) {
      this.extraData = extraData;
   }

   @Generated
   @Override
   public String toString() {
      return "UserOauthBinding(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", provider="
         + this.getProvider()
         + ", providerUserId="
         + this.getProviderUserId()
         + ", providerUsername="
         + this.getProviderUsername()
         + ", providerAvatar="
         + this.getProviderAvatar()
         + ", extraData="
         + this.getExtraData()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserOauthBinding other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
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
                           Object this$extraData = this.getExtraData();
                           Object other$extraData = other.getExtraData();
                           return this$extraData == null ? other$extraData == null : this$extraData.equals(other$extraData);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof UserOauthBinding;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $provider = this.getProvider();
      result = result * 59 + ($provider == null ? 43 : $provider.hashCode());
      Object $providerUserId = this.getProviderUserId();
      result = result * 59 + ($providerUserId == null ? 43 : $providerUserId.hashCode());
      Object $providerUsername = this.getProviderUsername();
      result = result * 59 + ($providerUsername == null ? 43 : $providerUsername.hashCode());
      Object $providerAvatar = this.getProviderAvatar();
      result = result * 59 + ($providerAvatar == null ? 43 : $providerAvatar.hashCode());
      Object $extraData = this.getExtraData();
      return result * 59 + ($extraData == null ? 43 : $extraData.hashCode());
   }
}
