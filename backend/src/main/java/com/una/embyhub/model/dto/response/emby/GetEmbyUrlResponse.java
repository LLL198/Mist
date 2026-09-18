package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import lombok.Generated;

public class GetEmbyUrlResponse implements Serializable {
   private String embyItemUrl;

   @Generated
   public String getEmbyItemUrl() {
      return this.embyItemUrl;
   }

   @Generated
   public void setEmbyItemUrl(final String embyItemUrl) {
      this.embyItemUrl = embyItemUrl;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof GetEmbyUrlResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyItemUrl = this.getEmbyItemUrl();
         Object other$embyItemUrl = other.getEmbyItemUrl();
         return this$embyItemUrl == null ? other$embyItemUrl == null : this$embyItemUrl.equals(other$embyItemUrl);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof GetEmbyUrlResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyItemUrl = this.getEmbyItemUrl();
      return result * 59 + ($embyItemUrl == null ? 43 : $embyItemUrl.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "GetEmbyUrlResponse(embyItemUrl=" + this.getEmbyItemUrl() + ")";
   }
}
