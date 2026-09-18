package com.una.embyhub.model.dto.response.systemtool;

import lombok.Generated;

public class UrlConnectivityResponse {
   private String url;
   private boolean success;
   private Long durationMs;
   private String errorMessage;

   @Generated
   public String getUrl() {
      return this.url;
   }

   @Generated
   public boolean isSuccess() {
      return this.success;
   }

   @Generated
   public Long getDurationMs() {
      return this.durationMs;
   }

   @Generated
   public String getErrorMessage() {
      return this.errorMessage;
   }

   @Generated
   public void setUrl(final String url) {
      this.url = url;
   }

   @Generated
   public void setSuccess(final boolean success) {
      this.success = success;
   }

   @Generated
   public void setDurationMs(final Long durationMs) {
      this.durationMs = durationMs;
   }

   @Generated
   public void setErrorMessage(final String errorMessage) {
      this.errorMessage = errorMessage;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UrlConnectivityResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isSuccess() != other.isSuccess()) {
         return false;
      } else {
         Object this$durationMs = this.getDurationMs();
         Object other$durationMs = other.getDurationMs();
         if (this$durationMs == null ? other$durationMs == null : this$durationMs.equals(other$durationMs)) {
            Object this$url = this.getUrl();
            Object other$url = other.getUrl();
            if (this$url == null ? other$url == null : this$url.equals(other$url)) {
               Object this$errorMessage = this.getErrorMessage();
               Object other$errorMessage = other.getErrorMessage();
               return this$errorMessage == null ? other$errorMessage == null : this$errorMessage.equals(other$errorMessage);
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
      return other instanceof UrlConnectivityResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isSuccess() ? 79 : 97);
      Object $durationMs = this.getDurationMs();
      result = result * 59 + ($durationMs == null ? 43 : $durationMs.hashCode());
      Object $url = this.getUrl();
      result = result * 59 + ($url == null ? 43 : $url.hashCode());
      Object $errorMessage = this.getErrorMessage();
      return result * 59 + ($errorMessage == null ? 43 : $errorMessage.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UrlConnectivityResponse(url="
         + this.getUrl()
         + ", success="
         + this.isSuccess()
         + ", durationMs="
         + this.getDurationMs()
         + ", errorMessage="
         + this.getErrorMessage()
         + ")";
   }
}
