package com.una.embyhub.movie.model;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class MovieQbittorrentConfigRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   @NotBlank(
      message = "qBittorrent 地址不能为空"
   )
   private String host;
   @NotBlank(
      message = "qBittorrent 用户名不能为空"
   )
   private String username;
   @NotBlank(
      message = "qBittorrent 密码不能为空"
   )
   private String password;
   private String downloaderName;

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
   public void setDownloaderName(final String downloaderName) {
      this.downloaderName = downloaderName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieQbittorrentConfigRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof MovieQbittorrentConfigRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
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
      return "MovieQbittorrentConfigRequest(host="
         + this.getHost()
         + ", username="
         + this.getUsername()
         + ", password="
         + this.getPassword()
         + ", downloaderName="
         + this.getDownloaderName()
         + ")";
   }
}
