package com.una.embyhub.model.dto.request.emby;

import java.io.Serializable;
import lombok.Generated;

public class GetItemsRequest implements Serializable {
   private Integer startIndex = 0;
   private Integer limit = 50;
   private String includeItemTypes;
   private String ids;
   private String studioIds;
   private String studios;
   private String genres;
   private String serverId;

   @Generated
   public Integer getStartIndex() {
      return this.startIndex;
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
   public String getIds() {
      return this.ids;
   }

   @Generated
   public String getStudioIds() {
      return this.studioIds;
   }

   @Generated
   public String getStudios() {
      return this.studios;
   }

   @Generated
   public String getGenres() {
      return this.genres;
   }

   @Generated
   public String getServerId() {
      return this.serverId;
   }

   @Generated
   public void setStartIndex(final Integer startIndex) {
      this.startIndex = startIndex;
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
   public void setIds(final String ids) {
      this.ids = ids;
   }

   @Generated
   public void setStudioIds(final String studioIds) {
      this.studioIds = studioIds;
   }

   @Generated
   public void setStudios(final String studios) {
      this.studios = studios;
   }

   @Generated
   public void setGenres(final String genres) {
      this.genres = genres;
   }

   @Generated
   public void setServerId(final String serverId) {
      this.serverId = serverId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof GetItemsRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$startIndex = this.getStartIndex();
         Object other$startIndex = other.getStartIndex();
         if (this$startIndex == null ? other$startIndex == null : this$startIndex.equals(other$startIndex)) {
            Object this$limit = this.getLimit();
            Object other$limit = other.getLimit();
            if (this$limit == null ? other$limit == null : this$limit.equals(other$limit)) {
               Object this$includeItemTypes = this.getIncludeItemTypes();
               Object other$includeItemTypes = other.getIncludeItemTypes();
               if (this$includeItemTypes == null ? other$includeItemTypes == null : this$includeItemTypes.equals(other$includeItemTypes)) {
                  Object this$ids = this.getIds();
                  Object other$ids = other.getIds();
                  if (this$ids == null ? other$ids == null : this$ids.equals(other$ids)) {
                     Object this$studioIds = this.getStudioIds();
                     Object other$studioIds = other.getStudioIds();
                     if (this$studioIds == null ? other$studioIds == null : this$studioIds.equals(other$studioIds)) {
                        Object this$studios = this.getStudios();
                        Object other$studios = other.getStudios();
                        if (this$studios == null ? other$studios == null : this$studios.equals(other$studios)) {
                           Object this$genres = this.getGenres();
                           Object other$genres = other.getGenres();
                           if (this$genres == null ? other$genres == null : this$genres.equals(other$genres)) {
                              Object this$serverId = this.getServerId();
                              Object other$serverId = other.getServerId();
                              return this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId);
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
      return other instanceof GetItemsRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $startIndex = this.getStartIndex();
      result = result * 59 + ($startIndex == null ? 43 : $startIndex.hashCode());
      Object $limit = this.getLimit();
      result = result * 59 + ($limit == null ? 43 : $limit.hashCode());
      Object $includeItemTypes = this.getIncludeItemTypes();
      result = result * 59 + ($includeItemTypes == null ? 43 : $includeItemTypes.hashCode());
      Object $ids = this.getIds();
      result = result * 59 + ($ids == null ? 43 : $ids.hashCode());
      Object $studioIds = this.getStudioIds();
      result = result * 59 + ($studioIds == null ? 43 : $studioIds.hashCode());
      Object $studios = this.getStudios();
      result = result * 59 + ($studios == null ? 43 : $studios.hashCode());
      Object $genres = this.getGenres();
      result = result * 59 + ($genres == null ? 43 : $genres.hashCode());
      Object $serverId = this.getServerId();
      return result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "GetItemsRequest(startIndex="
         + this.getStartIndex()
         + ", limit="
         + this.getLimit()
         + ", includeItemTypes="
         + this.getIncludeItemTypes()
         + ", ids="
         + this.getIds()
         + ", studioIds="
         + this.getStudioIds()
         + ", studios="
         + this.getStudios()
         + ", genres="
         + this.getGenres()
         + ", serverId="
         + this.getServerId()
         + ")";
   }
}
