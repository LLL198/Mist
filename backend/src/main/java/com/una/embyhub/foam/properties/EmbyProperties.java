package com.una.embyhub.foam.properties;

import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
   prefix = "emby"
)
public class EmbyProperties {
   private String url;
   private String apikey;
   private String copyfromuserid;

   @Generated
   public String getUrl() {
      return this.url;
   }

   @Generated
   public String getApikey() {
      return this.apikey;
   }

   @Generated
   public String getCopyfromuserid() {
      return this.copyfromuserid;
   }

   @Generated
   public void setUrl(final String url) {
      this.url = url;
   }

   @Generated
   public void setApikey(final String apikey) {
      this.apikey = apikey;
   }

   @Generated
   public void setCopyfromuserid(final String copyfromuserid) {
      this.copyfromuserid = copyfromuserid;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyProperties other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$url = this.getUrl();
         Object other$url = other.getUrl();
         if (this$url == null ? other$url == null : this$url.equals(other$url)) {
            Object this$apikey = this.getApikey();
            Object other$apikey = other.getApikey();
            if (this$apikey == null ? other$apikey == null : this$apikey.equals(other$apikey)) {
               Object this$copyfromuserid = this.getCopyfromuserid();
               Object other$copyfromuserid = other.getCopyfromuserid();
               return this$copyfromuserid == null ? other$copyfromuserid == null : this$copyfromuserid.equals(other$copyfromuserid);
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
      return other instanceof EmbyProperties;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $url = this.getUrl();
      result = result * 59 + ($url == null ? 43 : $url.hashCode());
      Object $apikey = this.getApikey();
      result = result * 59 + ($apikey == null ? 43 : $apikey.hashCode());
      Object $copyfromuserid = this.getCopyfromuserid();
      return result * 59 + ($copyfromuserid == null ? 43 : $copyfromuserid.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyProperties(url=" + this.getUrl() + ", apikey=" + this.getApikey() + ", copyfromuserid=" + this.getCopyfromuserid() + ")";
   }
}
