package com.una.embyhub.model.dto.response.telegram;

import java.io.Serializable;
import lombok.Generated;

public class TelegramLoginSessionResponse implements Serializable {
   private String sessionId;
   private String clientToken;
   private String loginUrl;

   @Generated
   public String getSessionId() {
      return this.sessionId;
   }

   @Generated
   public String getClientToken() {
      return this.clientToken;
   }

   @Generated
   public String getLoginUrl() {
      return this.loginUrl;
   }

   @Generated
   public void setSessionId(final String sessionId) {
      this.sessionId = sessionId;
   }

   @Generated
   public void setClientToken(final String clientToken) {
      this.clientToken = clientToken;
   }

   @Generated
   public void setLoginUrl(final String loginUrl) {
      this.loginUrl = loginUrl;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramLoginSessionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$sessionId = this.getSessionId();
         Object other$sessionId = other.getSessionId();
         if (this$sessionId == null ? other$sessionId == null : this$sessionId.equals(other$sessionId)) {
            Object this$clientToken = this.getClientToken();
            Object other$clientToken = other.getClientToken();
            if (this$clientToken == null ? other$clientToken == null : this$clientToken.equals(other$clientToken)) {
               Object this$loginUrl = this.getLoginUrl();
               Object other$loginUrl = other.getLoginUrl();
               return this$loginUrl == null ? other$loginUrl == null : this$loginUrl.equals(other$loginUrl);
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
      return other instanceof TelegramLoginSessionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $sessionId = this.getSessionId();
      result = result * 59 + ($sessionId == null ? 43 : $sessionId.hashCode());
      Object $clientToken = this.getClientToken();
      result = result * 59 + ($clientToken == null ? 43 : $clientToken.hashCode());
      Object $loginUrl = this.getLoginUrl();
      return result * 59 + ($loginUrl == null ? 43 : $loginUrl.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramLoginSessionResponse(sessionId="
         + this.getSessionId()
         + ", clientToken="
         + this.getClientToken()
         + ", loginUrl="
         + this.getLoginUrl()
         + ")";
   }

   @Generated
   public TelegramLoginSessionResponse() {
   }

   @Generated
   public TelegramLoginSessionResponse(final String sessionId, final String clientToken, final String loginUrl) {
      this.sessionId = sessionId;
      this.clientToken = clientToken;
      this.loginUrl = loginUrl;
   }
}
