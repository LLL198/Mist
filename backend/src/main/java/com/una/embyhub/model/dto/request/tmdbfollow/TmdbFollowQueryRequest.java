package com.una.embyhub.model.dto.request.tmdbfollow;

import java.io.Serializable;
import lombok.Generated;

public class TmdbFollowQueryRequest implements Serializable {
   private String mediaType;
   private String name;

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollowQueryRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$mediaType = this.getMediaType();
         Object other$mediaType = other.getMediaType();
         if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            return this$name == null ? other$name == null : this$name.equals(other$name);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbFollowQueryRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $name = this.getName();
      return result * 59 + ($name == null ? 43 : $name.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowQueryRequest(mediaType=" + this.getMediaType() + ", name=" + this.getName() + ")";
   }
}
