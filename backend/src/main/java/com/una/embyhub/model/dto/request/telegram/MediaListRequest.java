package com.una.embyhub.model.dto.request.telegram;

import lombok.Generated;

public class MediaListRequest {
   private String mediaType;
   private String category;
   private Integer page;

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public String getCategory() {
      return this.category;
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
   public void setCategory(final String category) {
      this.category = category;
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
      } else if (!(o instanceof MediaListRequest other)) {
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
               Object this$category = this.getCategory();
               Object other$category = other.getCategory();
               return this$category == null ? other$category == null : this$category.equals(other$category);
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
      return other instanceof MediaListRequest;
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
      Object $category = this.getCategory();
      return result * 59 + ($category == null ? 43 : $category.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MediaListRequest(mediaType=" + this.getMediaType() + ", category=" + this.getCategory() + ", page=" + this.getPage() + ")";
   }
}
