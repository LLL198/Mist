package com.una.embyhub.config.common.cookiecloud;

import lombok.Generated;

public class CookieCloudProperties {
   private String url;
   private String uuid;
   private String password;

   @Generated
   public static CookieCloudProperties.CookieCloudPropertiesBuilder builder() {
      return new CookieCloudProperties.CookieCloudPropertiesBuilder();
   }

   @Generated
   public String getUrl() {
      return this.url;
   }

   @Generated
   public String getUuid() {
      return this.uuid;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public void setUrl(final String url) {
      this.url = url;
   }

   @Generated
   public void setUuid(final String uuid) {
      this.uuid = uuid;
   }

   @Generated
   public void setPassword(final String password) {
      this.password = password;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CookieCloudProperties other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$url = this.getUrl();
         Object other$url = other.getUrl();
         if (this$url == null ? other$url == null : this$url.equals(other$url)) {
            Object this$uuid = this.getUuid();
            Object other$uuid = other.getUuid();
            if (this$uuid == null ? other$uuid == null : this$uuid.equals(other$uuid)) {
               Object this$password = this.getPassword();
               Object other$password = other.getPassword();
               return this$password == null ? other$password == null : this$password.equals(other$password);
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
      return other instanceof CookieCloudProperties;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $url = this.getUrl();
      result = result * 59 + ($url == null ? 43 : $url.hashCode());
      Object $uuid = this.getUuid();
      result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
      Object $password = this.getPassword();
      return result * 59 + ($password == null ? 43 : $password.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "CookieCloudProperties(url=" + this.getUrl() + ", uuid=" + this.getUuid() + ", password=" + this.getPassword() + ")";
   }

   @Generated
   public CookieCloudProperties() {
   }

   @Generated
   public CookieCloudProperties(final String url, final String uuid, final String password) {
      this.url = url;
      this.uuid = uuid;
      this.password = password;
   }

   @Generated
   public static class CookieCloudPropertiesBuilder {
      @Generated
      private String url;
      @Generated
      private String uuid;
      @Generated
      private String password;

      @Generated
      CookieCloudPropertiesBuilder() {
      }

      @Generated
      public CookieCloudProperties.CookieCloudPropertiesBuilder url(final String url) {
         this.url = url;
         return this;
      }

      @Generated
      public CookieCloudProperties.CookieCloudPropertiesBuilder uuid(final String uuid) {
         this.uuid = uuid;
         return this;
      }

      @Generated
      public CookieCloudProperties.CookieCloudPropertiesBuilder password(final String password) {
         this.password = password;
         return this;
      }

      @Generated
      public CookieCloudProperties build() {
         return new CookieCloudProperties(this.url, this.uuid, this.password);
      }

      @Generated
      @Override
      public String toString() {
         return "CookieCloudProperties.CookieCloudPropertiesBuilder(url=" + this.url + ", uuid=" + this.uuid + ", password=" + this.password + ")";
      }
   }
}
