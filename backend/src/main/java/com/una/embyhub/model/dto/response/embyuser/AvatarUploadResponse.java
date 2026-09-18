package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import lombok.Generated;

public class AvatarUploadResponse implements Serializable {
   private String url;
   private String fileName;

   @Generated
   public String getUrl() {
      return this.url;
   }

   @Generated
   public String getFileName() {
      return this.fileName;
   }

   @Generated
   public void setUrl(final String url) {
      this.url = url;
   }

   @Generated
   public void setFileName(final String fileName) {
      this.fileName = fileName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AvatarUploadResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$url = this.getUrl();
         Object other$url = other.getUrl();
         if (this$url == null ? other$url == null : this$url.equals(other$url)) {
            Object this$fileName = this.getFileName();
            Object other$fileName = other.getFileName();
            return this$fileName == null ? other$fileName == null : this$fileName.equals(other$fileName);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof AvatarUploadResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $url = this.getUrl();
      result = result * 59 + ($url == null ? 43 : $url.hashCode());
      Object $fileName = this.getFileName();
      return result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "AvatarUploadResponse(url=" + this.getUrl() + ", fileName=" + this.getFileName() + ")";
   }
}
