package com.una.embyhub.model.dto.request.douban;

import lombok.Generated;

public class DoubanSearchRequest extends DoubanHotRequest {
   private String query;

   @Generated
   public String getQuery() {
      return this.query;
   }

   @Generated
   public void setQuery(final String query) {
      this.query = query;
   }

   @Generated
   @Override
   public String toString() {
      return "DoubanSearchRequest(query=" + this.getQuery() + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DoubanSearchRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$query = this.getQuery();
         Object other$query = other.getQuery();
         return this$query == null ? other$query == null : this$query.equals(other$query);
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof DoubanSearchRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $query = this.getQuery();
      return result * 59 + ($query == null ? 43 : $query.hashCode());
   }
}
