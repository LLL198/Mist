package com.una.embyhub.model.dto.response.tmdbfollow;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class TmdbSeasonResponse implements Serializable {
   private Integer seasonNumber;
   private String name;
   private String overview;
   private String posterPath;
   private Date airDate;
   private Integer episodeCount;
   private List<TmdbCastResponse> casts;
   private List<TmdbEpisodeResponse> episodes;

   @Generated
   public Integer getSeasonNumber() {
      return this.seasonNumber;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public String getPosterPath() {
      return this.posterPath;
   }

   @Generated
   public Date getAirDate() {
      return this.airDate;
   }

   @Generated
   public Integer getEpisodeCount() {
      return this.episodeCount;
   }

   @Generated
   public List<TmdbCastResponse> getCasts() {
      return this.casts;
   }

   @Generated
   public List<TmdbEpisodeResponse> getEpisodes() {
      return this.episodes;
   }

   @Generated
   public void setSeasonNumber(final Integer seasonNumber) {
      this.seasonNumber = seasonNumber;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setPosterPath(final String posterPath) {
      this.posterPath = posterPath;
   }

   @Generated
   public void setAirDate(final Date airDate) {
      this.airDate = airDate;
   }

   @Generated
   public void setEpisodeCount(final Integer episodeCount) {
      this.episodeCount = episodeCount;
   }

   @Generated
   public void setCasts(final List<TmdbCastResponse> casts) {
      this.casts = casts;
   }

   @Generated
   public void setEpisodes(final List<TmdbEpisodeResponse> episodes) {
      this.episodes = episodes;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbSeasonResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$seasonNumber = this.getSeasonNumber();
         Object other$seasonNumber = other.getSeasonNumber();
         if (this$seasonNumber == null ? other$seasonNumber == null : this$seasonNumber.equals(other$seasonNumber)) {
            Object this$episodeCount = this.getEpisodeCount();
            Object other$episodeCount = other.getEpisodeCount();
            if (this$episodeCount == null ? other$episodeCount == null : this$episodeCount.equals(other$episodeCount)) {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                  Object this$overview = this.getOverview();
                  Object other$overview = other.getOverview();
                  if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                     Object this$posterPath = this.getPosterPath();
                     Object other$posterPath = other.getPosterPath();
                     if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                        Object this$airDate = this.getAirDate();
                        Object other$airDate = other.getAirDate();
                        if (this$airDate == null ? other$airDate == null : this$airDate.equals(other$airDate)) {
                           Object this$casts = this.getCasts();
                           Object other$casts = other.getCasts();
                           if (this$casts == null ? other$casts == null : this$casts.equals(other$casts)) {
                              Object this$episodes = this.getEpisodes();
                              Object other$episodes = other.getEpisodes();
                              return this$episodes == null ? other$episodes == null : this$episodes.equals(other$episodes);
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
      return other instanceof TmdbSeasonResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $seasonNumber = this.getSeasonNumber();
      result = result * 59 + ($seasonNumber == null ? 43 : $seasonNumber.hashCode());
      Object $episodeCount = this.getEpisodeCount();
      result = result * 59 + ($episodeCount == null ? 43 : $episodeCount.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $airDate = this.getAirDate();
      result = result * 59 + ($airDate == null ? 43 : $airDate.hashCode());
      Object $casts = this.getCasts();
      result = result * 59 + ($casts == null ? 43 : $casts.hashCode());
      Object $episodes = this.getEpisodes();
      return result * 59 + ($episodes == null ? 43 : $episodes.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbSeasonResponse(seasonNumber="
         + this.getSeasonNumber()
         + ", name="
         + this.getName()
         + ", overview="
         + this.getOverview()
         + ", posterPath="
         + this.getPosterPath()
         + ", airDate="
         + this.getAirDate()
         + ", episodeCount="
         + this.getEpisodeCount()
         + ", casts="
         + this.getCasts()
         + ", episodes="
         + this.getEpisodes()
         + ")";
   }
}
