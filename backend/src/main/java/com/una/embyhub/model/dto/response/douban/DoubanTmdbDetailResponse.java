package com.una.embyhub.model.dto.response.douban;

import com.alibaba.fastjson2.JSONObject;
import lombok.Generated;

public class DoubanTmdbDetailResponse {
   private String doubanId;
   private String imdbId;
   private Integer tmdbId;
   private String tmdbType;
   private JSONObject movieDetail;
   private JSONObject tvDetail;

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
   public JSONObject getMovieDetail() {
      return this.movieDetail;
   }

   @Generated
   public JSONObject getTvDetail() {
      return this.tvDetail;
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
   public void setMovieDetail(final JSONObject movieDetail) {
      this.movieDetail = movieDetail;
   }

   @Generated
   public void setTvDetail(final JSONObject tvDetail) {
      this.tvDetail = tvDetail;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DoubanTmdbDetailResponse other)) {
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
                  if (this$tmdbType == null ? other$tmdbType == null : this$tmdbType.equals(other$tmdbType)) {
                     Object this$movieDetail = this.getMovieDetail();
                     Object other$movieDetail = other.getMovieDetail();
                     if (this$movieDetail == null ? other$movieDetail == null : this$movieDetail.equals(other$movieDetail)) {
                        Object this$tvDetail = this.getTvDetail();
                        Object other$tvDetail = other.getTvDetail();
                        return this$tvDetail == null ? other$tvDetail == null : this$tvDetail.equals(other$tvDetail);
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
      return other instanceof DoubanTmdbDetailResponse;
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
      result = result * 59 + ($tmdbType == null ? 43 : $tmdbType.hashCode());
      Object $movieDetail = this.getMovieDetail();
      result = result * 59 + ($movieDetail == null ? 43 : $movieDetail.hashCode());
      Object $tvDetail = this.getTvDetail();
      return result * 59 + ($tvDetail == null ? 43 : $tvDetail.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DoubanTmdbDetailResponse(doubanId="
         + this.getDoubanId()
         + ", imdbId="
         + this.getImdbId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", tmdbType="
         + this.getTmdbType()
         + ", movieDetail="
         + this.getMovieDetail()
         + ", tvDetail="
         + this.getTvDetail()
         + ")";
   }
}
