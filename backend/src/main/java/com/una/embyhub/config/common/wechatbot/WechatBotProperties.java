package com.una.embyhub.config.common.wechatbot;

import lombok.Generated;

public class WechatBotProperties {
   private String token;
   private String encodingAesKey;
   private String corpId;
   private String agentId;
   private String appSecret;
   private boolean menuEnabled;
   private String allowedSenders;

   @Generated
   public String getToken() {
      return this.token;
   }

   @Generated
   public String getEncodingAesKey() {
      return this.encodingAesKey;
   }

   @Generated
   public String getCorpId() {
      return this.corpId;
   }

   @Generated
   public String getAgentId() {
      return this.agentId;
   }

   @Generated
   public String getAppSecret() {
      return this.appSecret;
   }

   @Generated
   public boolean isMenuEnabled() {
      return this.menuEnabled;
   }

   @Generated
   public String getAllowedSenders() {
      return this.allowedSenders;
   }

   @Generated
   public void setToken(final String token) {
      this.token = token;
   }

   @Generated
   public void setEncodingAesKey(final String encodingAesKey) {
      this.encodingAesKey = encodingAesKey;
   }

   @Generated
   public void setCorpId(final String corpId) {
      this.corpId = corpId;
   }

   @Generated
   public void setAgentId(final String agentId) {
      this.agentId = agentId;
   }

   @Generated
   public void setAppSecret(final String appSecret) {
      this.appSecret = appSecret;
   }

   @Generated
   public void setMenuEnabled(final boolean menuEnabled) {
      this.menuEnabled = menuEnabled;
   }

   @Generated
   public void setAllowedSenders(final String allowedSenders) {
      this.allowedSenders = allowedSenders;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WechatBotProperties other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isMenuEnabled() != other.isMenuEnabled()) {
         return false;
      } else {
         Object this$token = this.getToken();
         Object other$token = other.getToken();
         if (this$token == null ? other$token == null : this$token.equals(other$token)) {
            Object this$encodingAesKey = this.getEncodingAesKey();
            Object other$encodingAesKey = other.getEncodingAesKey();
            if (this$encodingAesKey == null ? other$encodingAesKey == null : this$encodingAesKey.equals(other$encodingAesKey)) {
               Object this$corpId = this.getCorpId();
               Object other$corpId = other.getCorpId();
               if (this$corpId == null ? other$corpId == null : this$corpId.equals(other$corpId)) {
                  Object this$agentId = this.getAgentId();
                  Object other$agentId = other.getAgentId();
                  if (this$agentId == null ? other$agentId == null : this$agentId.equals(other$agentId)) {
                     Object this$appSecret = this.getAppSecret();
                     Object other$appSecret = other.getAppSecret();
                     if (this$appSecret == null ? other$appSecret == null : this$appSecret.equals(other$appSecret)) {
                        Object this$allowedSenders = this.getAllowedSenders();
                        Object other$allowedSenders = other.getAllowedSenders();
                        return this$allowedSenders == null ? other$allowedSenders == null : this$allowedSenders.equals(other$allowedSenders);
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
      return other instanceof WechatBotProperties;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isMenuEnabled() ? 79 : 97);
      Object $token = this.getToken();
      result = result * 59 + ($token == null ? 43 : $token.hashCode());
      Object $encodingAesKey = this.getEncodingAesKey();
      result = result * 59 + ($encodingAesKey == null ? 43 : $encodingAesKey.hashCode());
      Object $corpId = this.getCorpId();
      result = result * 59 + ($corpId == null ? 43 : $corpId.hashCode());
      Object $agentId = this.getAgentId();
      result = result * 59 + ($agentId == null ? 43 : $agentId.hashCode());
      Object $appSecret = this.getAppSecret();
      result = result * 59 + ($appSecret == null ? 43 : $appSecret.hashCode());
      Object $allowedSenders = this.getAllowedSenders();
      return result * 59 + ($allowedSenders == null ? 43 : $allowedSenders.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "WechatBotProperties(token="
         + this.getToken()
         + ", encodingAesKey="
         + this.getEncodingAesKey()
         + ", corpId="
         + this.getCorpId()
         + ", agentId="
         + this.getAgentId()
         + ", appSecret="
         + this.getAppSecret()
         + ", menuEnabled="
         + this.isMenuEnabled()
         + ", allowedSenders="
         + this.getAllowedSenders()
         + ")";
   }
}
