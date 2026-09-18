package com.una.embyhub.model.dto.request.telegram;

import lombok.Generated;

public class MediaSearchRequest {
   private String mediaType;
   private String query;
   private Integer page;

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public String getQuery() {
      return this.query;
   }

   @Generated
   public Integer getPage() {
      return this.page;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setQuery(final String query) {
      this.query = query;
   }

   @Generated
   public void setPage(final Integer page) {
      this.page = page;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MediaSearchRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$page = this.getPage();
         Object other$page = other.getPage();
         if (this$page == null ? other$page == null : this$page.equals(other$page)) {
            Object this$mediaType = this.getMediaType();
            Object other$mediaType = other.getMediaType();
            if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
               Object this$query = this.getQuery();
               Object other$query = other.getQuery();
               return this$query == null ? other$query == null : this$query.equals(other$query);
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
      return other instanceof MediaSearchRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $page = this.getPage();
      result = result * 59 + ($page == null ? 43 : $page.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $query = this.getQuery();
      return result * 59 + ($query == null ? 43 : $query.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MediaSearchRequest(mediaType=" + this.getMediaType() + ", query=" + this.getQuery() + ", page=" + this.getPage() + ")";
   }
}
