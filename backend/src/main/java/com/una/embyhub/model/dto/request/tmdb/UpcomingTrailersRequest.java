package com.una.embyhub.model.dto.request.tmdb;

import lombok.Generated;

public class UpcomingTrailersRequest {
   private String type = "all";
   private Integer page = 1;
   private Integer limit = 60;

   @Generated
   public String getType() {
      return this.type;
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
   public void setType(final String type) {
      this.type = type;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UpcomingTrailersRequest other)) {
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
               Object this$type = this.getType();
               Object other$type = other.getType();
               return this$type == null ? other$type == null : this$type.equals(other$type);
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
      return other instanceof UpcomingTrailersRequest;
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
      Object $type = this.getType();
      return result * 59 + ($type == null ? 43 : $type.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UpcomingTrailersRequest(type=" + this.getType() + ", page=" + this.getPage() + ", limit=" + this.getLimit() + ")";
   }

   @Generated
   public UpcomingTrailersRequest(final String type, final Integer page, final Integer limit) {
      this.type = type;
      this.page = page;
      this.limit = limit;
   }

   @Generated
   public UpcomingTrailersRequest() {
   }
}
