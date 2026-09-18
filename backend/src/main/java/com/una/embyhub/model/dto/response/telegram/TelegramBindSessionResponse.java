package com.una.embyhub.model.dto.response.telegram;

import java.io.Serializable;
import lombok.Generated;

public class TelegramBindSessionResponse implements Serializable {
   private String bindUrl;

   @Generated
   public String getBindUrl() {
      return this.bindUrl;
   }

   @Generated
   public void setBindUrl(final String bindUrl) {
      this.bindUrl = bindUrl;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBindSessionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$bindUrl = this.getBindUrl();
         Object other$bindUrl = other.getBindUrl();
         return this$bindUrl == null ? other$bindUrl == null : this$bindUrl.equals(other$bindUrl);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TelegramBindSessionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $bindUrl = this.getBindUrl();
      return result * 59 + ($bindUrl == null ? 43 : $bindUrl.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindSessionResponse(bindUrl=" + this.getBindUrl() + ")";
   }

   @Generated
   public TelegramBindSessionResponse() {
   }

   @Generated
   public TelegramBindSessionResponse(final String bindUrl) {
      this.bindUrl = bindUrl;
   }
}
