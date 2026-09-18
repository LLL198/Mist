package com.una.embyhub.model.dto.response.emby;

import com.una.embyhub.model.dto.response.telegram.PublisherGroupResponse;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class PublisherSearchResponse implements Serializable {
   private String publisher;
   private QueryResultBaseItemResponse items;
   private List<PublisherGroupResponse> publisherOptions;
   private PublisherSearchResponse.SearchMeta meta;

   @Generated
   public String getPublisher() {
      return this.publisher;
   }

   @Generated
   public QueryResultBaseItemResponse getItems() {
      return this.items;
   }

   @Generated
   public List<PublisherGroupResponse> getPublisherOptions() {
      return this.publisherOptions;
   }

   @Generated
   public PublisherSearchResponse.SearchMeta getMeta() {
      return this.meta;
   }

   @Generated
   public void setPublisher(final String publisher) {
      this.publisher = publisher;
   }

   @Generated
   public void setItems(final QueryResultBaseItemResponse items) {
      this.items = items;
   }

   @Generated
   public void setPublisherOptions(final List<PublisherGroupResponse> publisherOptions) {
      this.publisherOptions = publisherOptions;
   }

   @Generated
   public void setMeta(final PublisherSearchResponse.SearchMeta meta) {
      this.meta = meta;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PublisherSearchResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$publisher = this.getPublisher();
         Object other$publisher = other.getPublisher();
         if (this$publisher == null ? other$publisher == null : this$publisher.equals(other$publisher)) {
            Object this$items = this.getItems();
            Object other$items = other.getItems();
            if (this$items == null ? other$items == null : this$items.equals(other$items)) {
               Object this$publisherOptions = this.getPublisherOptions();
               Object other$publisherOptions = other.getPublisherOptions();
               if (this$publisherOptions == null ? other$publisherOptions == null : this$publisherOptions.equals(other$publisherOptions)) {
                  Object this$meta = this.getMeta();
                  Object other$meta = other.getMeta();
                  return this$meta == null ? other$meta == null : this$meta.equals(other$meta);
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
      return other instanceof PublisherSearchResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $publisher = this.getPublisher();
      result = result * 59 + ($publisher == null ? 43 : $publisher.hashCode());
      Object $items = this.getItems();
      result = result * 59 + ($items == null ? 43 : $items.hashCode());
      Object $publisherOptions = this.getPublisherOptions();
      result = result * 59 + ($publisherOptions == null ? 43 : $publisherOptions.hashCode());
      Object $meta = this.getMeta();
      return result * 59 + ($meta == null ? 43 : $meta.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PublisherSearchResponse(publisher="
         + this.getPublisher()
         + ", items="
         + this.getItems()
         + ", publisherOptions="
         + this.getPublisherOptions()
         + ", meta="
         + this.getMeta()
         + ")";
   }

   public static class SearchMeta implements Serializable {
      private Integer page;
      private Integer limit;
      private String includeItemTypes;
      private String genres;

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
         } else if (!(o instanceof PublisherSearchResponse.SearchMeta other)) {
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
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof PublisherSearchResponse.SearchMeta;
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
         Object $includeItemTypes = this.getIncludeItemTypes();
         result = result * 59 + ($includeItemTypes == null ? 43 : $includeItemTypes.hashCode());
         Object $genres = this.getGenres();
         return result * 59 + ($genres == null ? 43 : $genres.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "PublisherSearchResponse.SearchMeta(page="
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
}
