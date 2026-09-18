package com.una.embyhub.model.dto.response.douban;

import lombok.Generated;

public class DoubanIdMappingResponse {
   private String doubanId;
   private String imdbId;
   private Integer tmdbId;
   private String tmdbType;

   @Generated
   public String getDoubanId() {
      return this.doubanId;
   }

   @Generated
   public String getImdbId() {
      return this.imdbId;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getTmdbType() {
      return this.tmdbType;
   }

   @Generated
   public void setDoubanId(final String doubanId) {
      this.doubanId = doubanId;
   }

   @Generated
   public void setImdbId(final String imdbId) {
      this.imdbId = imdbId;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setTmdbType(final String tmdbType) {
      this.tmdbType = tmdbType;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DoubanIdMappingResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbId = this.getTmdbId();
         Object other$tmdbId = other.getTmdbId();
         if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
            Object this$doubanId = this.getDoubanId();
            Object other$doubanId = other.getDoubanId();
            if (this$doubanId == null ? other$doubanId == null : this$doubanId.equals(other$doubanId)) {
               Object this$imdbId = this.getImdbId();
               Object other$imdbId = other.getImdbId();
               if (this$imdbId == null ? other$imdbId == null : this$imdbId.equals(other$imdbId)) {
                  Object this$tmdbType = this.getTmdbType();
                  Object other$tmdbType = other.getTmdbType();
                  return this$tmdbType == null ? other$tmdbType == null : this$tmdbType.equals(other$tmdbType);
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
      return other instanceof DoubanIdMappingResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $imdbId = this.getImdbId();
      result = result * 59 + ($imdbId == null ? 43 : $imdbId.hashCode());
      Object $tmdbType = this.getTmdbType();
      return result * 59 + ($tmdbType == null ? 43 : $tmdbType.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DoubanIdMappingResponse(doubanId="
         + this.getDoubanId()
         + ", imdbId="
         + this.getImdbId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", tmdbType="
         + this.getTmdbType()
         + ")";
   }
}
