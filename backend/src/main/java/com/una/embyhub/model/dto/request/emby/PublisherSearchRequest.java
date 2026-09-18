package com.una.embyhub.model.dto.request.emby;

import java.io.Serializable;
import lombok.Generated;

public class PublisherSearchRequest implements Serializable {
   private String publisher;
   private Integer page = 1;
   private Integer limit = 50;
   private String includeItemTypes = "Movie,Series";
   private String genres;

   @Generated
   public String getPublisher() {
      return this.publisher;
   }

   @Generated
   public Integer getPage() {
      return this.page;
   }

   @Generated
   public Integer getLimit() {
      return this.limit;
   }

   @Generated
   public String getIncludeItemTypes() {
      return this.includeItemTypes;
   }

   @Generated
   public String getGenres() {
      return this.genres;
   }

   @Generated
   public void setPublisher(final String publisher) {
      this.publisher = publisher;
   }

   @Generated
   public void setPage(final Integer page) {
      this.page = page;
   }

   @Generated
   public void setLimit(final Integer limit) {
      this.limit = limit;
   }

   @Generated
   public void setIncludeItemTypes(final String includeItemTypes) {
      this.includeItemTypes = includeItemTypes;
   }

   @Generated
   public void setGenres(final String genres) {
      this.genres = genres;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PublisherSearchRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$page = this.getPage();
         Object other$page = other.getPage();
         if (this$page == null ? other$page == null : this$page.equals(other$page)) {
            Object this$limit = this.getLimit();
            Object other$limit = other.getLimit();
            if (this$limit == null ? other$limit == null : this$limit.equals(other$limit)) {
               Object this$publisher = this.getPublisher();
               Object other$publisher = other.getPublisher();
               if (this$publisher == null ? other$publisher == null : this$publisher.equals(other$publisher)) {
                  Object this$includeItemTypes = this.getIncludeItemTypes();
                  Object other$includeItemTypes = other.getIncludeItemTypes();
                  if (this$includeItemTypes == null ? other$includeItemTypes == null : this$includeItemTypes.equals(other$includeItemTypes)) {
                     Object this$genres = this.getGenres();
                     Object other$genres = other.getGenres();
                     return this$genres == null ? other$genres == null : this$genres.equals(other$genres);
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
      return other instanceof PublisherSearchRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $page = this.getPage();
      result = result * 59 + ($page == null ? 43 : $page.hashCode());
      Object $limit = this.getLimit();
      result = result * 59 + ($limit == null ? 43 : $limit.hashCode());
      Object $publisher = this.getPublisher();
      result = result * 59 + ($publisher == null ? 43 : $publisher.hashCode());
      Object $includeItemTypes = this.getIncludeItemTypes();
      result = result * 59 + ($includeItemTypes == null ? 43 : $includeItemTypes.hashCode());
      Object $genres = this.getGenres();
      return result * 59 + ($genres == null ? 43 : $genres.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PublisherSearchRequest(publisher="
         + this.getPublisher()
         + ", page="
         + this.getPage()
         + ", limit="
         + this.getLimit()
         + ", includeItemTypes="
         + this.getIncludeItemTypes()
         + ", genres="
         + this.getGenres()
         + ")";
   }
}
