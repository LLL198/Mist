package com.una.embyhub.model.dto.request.moviepilot;

import lombok.Generated;

public class MoviePilotLoginRequest {
   private String url;
   private String username;
   private String password;
   private String otpPassword;

   @Generated
   public String getUrl() {
      return this.url;
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
   public String getOtpPassword() {
      return this.otpPassword;
   }

   @Generated
   public void setUrl(final String url) {
      this.url = url;
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
   public void setOtpPassword(final String otpPassword) {
      this.otpPassword = otpPassword;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePilotLoginRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$url = this.getUrl();
         Object other$url = other.getUrl();
         if (this$url == null ? other$url == null : this$url.equals(other$url)) {
            Object this$username = this.getUsername();
            Object other$username = other.getUsername();
            if (this$username == null ? other$username == null : this$username.equals(other$username)) {
               Object this$password = this.getPassword();
               Object other$password = other.getPassword();
               if (this$password == null ? other$password == null : this$password.equals(other$password)) {
                  Object this$otpPassword = this.getOtpPassword();
                  Object other$otpPassword = other.getOtpPassword();
                  return this$otpPassword == null ? other$otpPassword == null : this$otpPassword.equals(other$otpPassword);
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
      return other instanceof MoviePilotLoginRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $url = this.getUrl();
      result = result * 59 + ($url == null ? 43 : $url.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $password = this.getPassword();
      result = result * 59 + ($password == null ? 43 : $password.hashCode());
      Object $otpPassword = this.getOtpPassword();
      return result * 59 + ($otpPassword == null ? 43 : $otpPassword.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePilotLoginRequest(url="
         + this.getUrl()
         + ", username="
         + this.getUsername()
         + ", password="
         + this.getPassword()
         + ", otpPassword="
         + this.getOtpPassword()
         + ")";
   }
}
