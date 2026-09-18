package com.una.embyhub.model.dto.response.docker;

import java.io.Serializable;
import lombok.Generated;

public class DockerImagesVersionResponse implements Serializable {
   private DockerImageVersionResponse backend;
   private DockerImageVersionResponse frontend;

   @Generated
   public DockerImageVersionResponse getBackend() {
      return this.backend;
   }

   @Generated
   public DockerImageVersionResponse getFrontend() {
      return this.frontend;
   }

   @Generated
   public void setBackend(final DockerImageVersionResponse backend) {
      this.backend = backend;
   }

   @Generated
   public void setFrontend(final DockerImageVersionResponse frontend) {
      this.frontend = frontend;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DockerImagesVersionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$backend = this.getBackend();
         Object other$backend = other.getBackend();
         if (this$backend == null ? other$backend == null : this$backend.equals(other$backend)) {
            Object this$frontend = this.getFrontend();
            Object other$frontend = other.getFrontend();
            return this$frontend == null ? other$frontend == null : this$frontend.equals(other$frontend);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof DockerImagesVersionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $backend = this.getBackend();
      result = result * 59 + ($backend == null ? 43 : $backend.hashCode());
      Object $frontend = this.getFrontend();
      return result * 59 + ($frontend == null ? 43 : $frontend.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DockerImagesVersionResponse(backend=" + this.getBackend() + ", frontend=" + this.getFrontend() + ")";
   }

   @Generated
   public DockerImagesVersionResponse() {
   }

   @Generated
   public DockerImagesVersionResponse(final DockerImageVersionResponse backend, final DockerImageVersionResponse frontend) {
      this.backend = backend;
      this.frontend = frontend;
   }
}
