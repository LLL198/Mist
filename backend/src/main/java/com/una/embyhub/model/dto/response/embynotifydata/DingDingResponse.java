package com.una.embyhub.model.dto.response.embynotifydata;

import java.io.Serializable;
import lombok.Generated;

public class DingDingResponse implements Serializable {
   private String accessToken;
   private String secret;

   @Generated
   public String getAccessToken() {
      return this.accessToken;
   }

   @Generated
   public String getSecret() {
      return this.secret;
   }

   @Generated
   public void setAccessToken(final String accessToken) {
      this.accessToken = accessToken;
   }

   @Generated
   public void setSecret(final String secret) {
      this.secret = secret;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DingDingResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$accessToken = this.getAccessToken();
         Object other$accessToken = other.getAccessToken();
         if (this$accessToken == null ? other$accessToken == null : this$accessToken.equals(other$accessToken)) {
            Object this$secret = this.getSecret();
            Object other$secret = other.getSecret();
            return this$secret == null ? other$secret == null : this$secret.equals(other$secret);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof DingDingResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $accessToken = this.getAccessToken();
      result = result * 59 + ($accessToken == null ? 43 : $accessToken.hashCode());
      Object $secret = this.getSecret();
      return result * 59 + ($secret == null ? 43 : $secret.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DingDingResponse(accessToken=" + this.getAccessToken() + ", secret=" + this.getSecret() + ")";
   }
}
