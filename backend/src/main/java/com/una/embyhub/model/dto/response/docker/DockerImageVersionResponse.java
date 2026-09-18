package com.una.embyhub.model.dto.response.docker;

import java.io.Serializable;
import lombok.Generated;

public class DockerImageVersionResponse implements Serializable {
   private String image;
   private String currentVersion;
   private String latestVersion;

   @Generated
   public String getImage() {
      return this.image;
   }

   @Generated
   public String getCurrentVersion() {
      return this.currentVersion;
   }

   @Generated
   public String getLatestVersion() {
      return this.latestVersion;
   }

   @Generated
   public void setImage(final String image) {
      this.image = image;
   }

   @Generated
   public void setCurrentVersion(final String currentVersion) {
      this.currentVersion = currentVersion;
   }

   @Generated
   public void setLatestVersion(final String latestVersion) {
      this.latestVersion = latestVersion;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DockerImageVersionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$image = this.getImage();
         Object other$image = other.getImage();
         if (this$image == null ? other$image == null : this$image.equals(other$image)) {
            Object this$currentVersion = this.getCurrentVersion();
            Object other$currentVersion = other.getCurrentVersion();
            if (this$currentVersion == null ? other$currentVersion == null : this$currentVersion.equals(other$currentVersion)) {
               Object this$latestVersion = this.getLatestVersion();
               Object other$latestVersion = other.getLatestVersion();
               return this$latestVersion == null ? other$latestVersion == null : this$latestVersion.equals(other$latestVersion);
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
      return other instanceof DockerImageVersionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $image = this.getImage();
      result = result * 59 + ($image == null ? 43 : $image.hashCode());
      Object $currentVersion = this.getCurrentVersion();
      result = result * 59 + ($currentVersion == null ? 43 : $currentVersion.hashCode());
      Object $latestVersion = this.getLatestVersion();
      return result * 59 + ($latestVersion == null ? 43 : $latestVersion.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DockerImageVersionResponse(image="
         + this.getImage()
         + ", currentVersion="
         + this.getCurrentVersion()
         + ", latestVersion="
         + this.getLatestVersion()
         + ")";
   }
}
