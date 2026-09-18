package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MovieQbittorrentConfig implements Serializable {
   private static final long serialVersionUID = 1L;
   private String host;
   private String username;
   private String password;
   private Long id;
   private String downloaderName;

   @Generated
   MovieQbittorrentConfig(final String host, final String username, final String password, final Long id, final String downloaderName) {
      this.host = host;
      this.username = username;
      this.password = password;
      this.id = id;
      this.downloaderName = downloaderName;
   }

   @Generated
   public static MovieQbittorrentConfig.MovieQbittorrentConfigBuilder builder() {
      return new MovieQbittorrentConfig.MovieQbittorrentConfigBuilder();
   }

   @Generated
   public String getHost() {
      return this.host;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getDownloaderName() {
      return this.downloaderName;
   }

   @Generated
   public void setHost(final String host) {
      this.host = host;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setPassword(final String password) {
      this.password = password;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setDownloaderName(final String downloaderName) {
      this.downloaderName = downloaderName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieQbittorrentConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$host = this.getHost();
            Object other$host = other.getHost();
            if (this$host == null ? other$host == null : this$host.equals(other$host)) {
               Object this$username = this.getUsername();
               Object other$username = other.getUsername();
               if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                  Object this$password = this.getPassword();
                  Object other$password = other.getPassword();
                  if (this$password == null ? other$password == null : this$password.equals(other$password)) {
                     Object this$downloaderName = this.getDownloaderName();
                     Object other$downloaderName = other.getDownloaderName();
                     return this$downloaderName == null ? other$downloaderName == null : this$downloaderName.equals(other$downloaderName);
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
      return other instanceof MovieQbittorrentConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $host = this.getHost();
      result = result * 59 + ($host == null ? 43 : $host.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $password = this.getPassword();
      result = result * 59 + ($password == null ? 43 : $password.hashCode());
      Object $downloaderName = this.getDownloaderName();
      return result * 59 + ($downloaderName == null ? 43 : $downloaderName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieQbittorrentConfig(host="
         + this.getHost()
         + ", username="
         + this.getUsername()
         + ", password="
         + this.getPassword()
         + ", id="
         + this.getId()
         + ", downloaderName="
         + this.getDownloaderName()
         + ")";
   }

   @Generated
   public static class MovieQbittorrentConfigBuilder {
      @Generated
      private String host;
      @Generated
      private String username;
      @Generated
      private String password;
      @Generated
      private Long id;
      @Generated
      private String downloaderName;

      @Generated
      MovieQbittorrentConfigBuilder() {
      }

      @Generated
      public MovieQbittorrentConfig.MovieQbittorrentConfigBuilder host(final String host) {
         this.host = host;
         return this;
      }

      @Generated
      public MovieQbittorrentConfig.MovieQbittorrentConfigBuilder username(final String username) {
         this.username = username;
         return this;
      }

      @Generated
      public MovieQbittorrentConfig.MovieQbittorrentConfigBuilder password(final String password) {
         this.password = password;
         return this;
      }

      @Generated
      public MovieQbittorrentConfig.MovieQbittorrentConfigBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public MovieQbittorrentConfig.MovieQbittorrentConfigBuilder downloaderName(final String downloaderName) {
         this.downloaderName = downloaderName;
         return this;
      }

      @Generated
      public MovieQbittorrentConfig build() {
         return new MovieQbittorrentConfig(this.host, this.username, this.password, this.id, this.downloaderName);
      }

      @Generated
      @Override
      public String toString() {
         return "MovieQbittorrentConfig.MovieQbittorrentConfigBuilder(host="
            + this.host
            + ", username="
            + this.username
            + ", password="
            + this.password
            + ", id="
            + this.id
            + ", downloaderName="
            + this.downloaderName
            + ")";
      }
   }
}
