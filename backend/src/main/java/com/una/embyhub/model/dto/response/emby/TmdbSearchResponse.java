package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import lombok.Generated;

public class TmdbSearchResponse implements Serializable {
   private String imgUrl;
   private String backdropPath;
   private String overview;
   private String releaseDate;
   private Integer tmdbId;
   private Double voteAverage;
   private Integer voteCount;
   private Integer runtime;
   private String productionCountries;

   @Generated
   public String getImgUrl() {
      return this.imgUrl;
   }

   @Generated
   public String getBackdropPath() {
      return this.backdropPath;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public String getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public Double getVoteAverage() {
      return this.voteAverage;
   }

   @Generated
   public Integer getVoteCount() {
      return this.voteCount;
   }

   @Generated
   public Integer getRuntime() {
      return this.runtime;
   }

   @Generated
   public String getProductionCountries() {
      return this.productionCountries;
   }

   @Generated
   public void setImgUrl(final String imgUrl) {
      this.imgUrl = imgUrl;
   }

   @Generated
   public void setBackdropPath(final String backdropPath) {
      this.backdropPath = backdropPath;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setReleaseDate(final String releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setVoteAverage(final Double voteAverage) {
      this.voteAverage = voteAverage;
   }

   @Generated
   public void setVoteCount(final Integer voteCount) {
      this.voteCount = voteCount;
   }

   @Generated
   public void setRuntime(final Integer runtime) {
      this.runtime = runtime;
   }

   @Generated
   public void setProductionCountries(final String productionCountries) {
      this.productionCountries = productionCountries;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbSearchResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbId = this.getTmdbId();
         Object other$tmdbId = other.getTmdbId();
         if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
            Object this$voteAverage = this.getVoteAverage();
            Object other$voteAverage = other.getVoteAverage();
            if (this$voteAverage == null ? other$voteAverage == null : this$voteAverage.equals(other$voteAverage)) {
               Object this$voteCount = this.getVoteCount();
               Object other$voteCount = other.getVoteCount();
               if (this$voteCount == null ? other$voteCount == null : this$voteCount.equals(other$voteCount)) {
                  Object this$runtime = this.getRuntime();
                  Object other$runtime = other.getRuntime();
                  if (this$runtime == null ? other$runtime == null : this$runtime.equals(other$runtime)) {
                     Object this$imgUrl = this.getImgUrl();
                     Object other$imgUrl = other.getImgUrl();
                     if (this$imgUrl == null ? other$imgUrl == null : this$imgUrl.equals(other$imgUrl)) {
                        Object this$backdropPath = this.getBackdropPath();
                        Object other$backdropPath = other.getBackdropPath();
                        if (this$backdropPath == null ? other$backdropPath == null : this$backdropPath.equals(other$backdropPath)) {
                           Object this$overview = this.getOverview();
                           Object other$overview = other.getOverview();
                           if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                              Object this$releaseDate = this.getReleaseDate();
                              Object other$releaseDate = other.getReleaseDate();
                              if (this$releaseDate == null ? other$releaseDate == null : this$releaseDate.equals(other$releaseDate)) {
                                 Object this$productionCountries = this.getProductionCountries();
                                 Object other$productionCountries = other.getProductionCountries();
                                 return this$productionCountries == null
                                    ? other$productionCountries == null
                                    : this$productionCountries.equals(other$productionCountries);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbSearchResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $voteAverage = this.getVoteAverage();
      result = result * 59 + ($voteAverage == null ? 43 : $voteAverage.hashCode());
      Object $voteCount = this.getVoteCount();
      result = result * 59 + ($voteCount == null ? 43 : $voteCount.hashCode());
      Object $runtime = this.getRuntime();
      result = result * 59 + ($runtime == null ? 43 : $runtime.hashCode());
      Object $imgUrl = this.getImgUrl();
      result = result * 59 + ($imgUrl == null ? 43 : $imgUrl.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $productionCountries = this.getProductionCountries();
      return result * 59 + ($productionCountries == null ? 43 : $productionCountries.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbSearchResponse(imgUrl="
         + this.getImgUrl()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", overview="
         + this.getOverview()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", tmdbId="
         + this.getTmdbId()
         + ", voteAverage="
         + this.getVoteAverage()
         + ", voteCount="
         + this.getVoteCount()
         + ", runtime="
         + this.getRuntime()
         + ", productionCountries="
         + this.getProductionCountries()
         + ")";
   }
}
