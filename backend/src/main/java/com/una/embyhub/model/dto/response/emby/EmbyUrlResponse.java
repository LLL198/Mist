package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import lombok.Generated;

public class EmbyUrlResponse implements Serializable {
   private String url;
   private String serverId;

   @Generated
   public String getUrl() {
      return this.url;
   }

   @Generated
   public String getServerId() {
      return this.serverId;
   }

   @Generated
   public void setUrl(final String url) {
      this.url = url;
   }

   @Generated
   public void setServerId(final String serverId) {
      this.serverId = serverId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUrlResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$url = this.getUrl();
         Object other$url = other.getUrl();
         if (this$url == null ? other$url == null : this$url.equals(other$url)) {
            Object this$serverId = this.getServerId();
            Object other$serverId = other.getServerId();
            return this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyUrlResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $url = this.getUrl();
      result = result * 59 + ($url == null ? 43 : $url.hashCode());
      Object $serverId = this.getServerId();
      return result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUrlResponse(url=" + this.getUrl() + ", serverId=" + this.getServerId() + ")";
   }
}
