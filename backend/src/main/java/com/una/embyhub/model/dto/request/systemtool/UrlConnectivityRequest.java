package com.una.embyhub.model.dto.request.systemtool;

import java.util.List;
import lombok.Generated;

public class UrlConnectivityRequest {
   private List<String> urls;

   @Generated
   public List<String> getUrls() {
      return this.urls;
   }

   @Generated
   public void setUrls(final List<String> urls) {
      this.urls = urls;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UrlConnectivityRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$urls = this.getUrls();
         Object other$urls = other.getUrls();
         return this$urls == null ? other$urls == null : this$urls.equals(other$urls);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof UrlConnectivityRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $urls = this.getUrls();
      return result * 59 + ($urls == null ? 43 : $urls.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UrlConnectivityRequest(urls=" + this.getUrls() + ")";
   }
}
