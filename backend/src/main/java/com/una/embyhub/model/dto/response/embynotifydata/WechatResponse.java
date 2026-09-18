package com.una.embyhub.model.dto.response.embynotifydata;

import java.io.Serializable;
import lombok.Generated;

public class WechatResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private String webhookKey;
   private String webhookUrl;

   @Generated
   public String getWebhookKey() {
      return this.webhookKey;
   }

   @Generated
   public String getWebhookUrl() {
      return this.webhookUrl;
   }

   @Generated
   public void setWebhookKey(final String webhookKey) {
      this.webhookKey = webhookKey;
   }

   @Generated
   public void setWebhookUrl(final String webhookUrl) {
      this.webhookUrl = webhookUrl;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WechatResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$webhookKey = this.getWebhookKey();
         Object other$webhookKey = other.getWebhookKey();
         if (this$webhookKey == null ? other$webhookKey == null : this$webhookKey.equals(other$webhookKey)) {
            Object this$webhookUrl = this.getWebhookUrl();
            Object other$webhookUrl = other.getWebhookUrl();
            return this$webhookUrl == null ? other$webhookUrl == null : this$webhookUrl.equals(other$webhookUrl);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof WechatResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $webhookKey = this.getWebhookKey();
      result = result * 59 + ($webhookKey == null ? 43 : $webhookKey.hashCode());
      Object $webhookUrl = this.getWebhookUrl();
      return result * 59 + ($webhookUrl == null ? 43 : $webhookUrl.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "WechatResponse(webhookKey=" + this.getWebhookKey() + ", webhookUrl=" + this.getWebhookUrl() + ")";
   }
}
