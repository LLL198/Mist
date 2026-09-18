package com.una.embyhub.model.dto.response.tmdbfollow;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class TmdbEpisodeResponse implements Serializable {
   private Integer seasonNumber;
   private Integer episodeNumber;
   private String name;
   private String overview;
   private Date airDate;
   private String stillPath;
   private Boolean watched;

   @Generated
   public Integer getSeasonNumber() {
      return this.seasonNumber;
   }

   @Generated
   public Integer getEpisodeNumber() {
      return this.episodeNumber;
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
   public Date getAirDate() {
      return this.airDate;
   }

   @Generated
   public String getStillPath() {
      return this.stillPath;
   }

   @Generated
   public Boolean getWatched() {
      return this.watched;
   }

   @Generated
   public void setSeasonNumber(final Integer seasonNumber) {
      this.seasonNumber = seasonNumber;
   }

   @Generated
   public void setEpisodeNumber(final Integer episodeNumber) {
      this.episodeNumber = episodeNumber;
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
   public void setAirDate(final Date airDate) {
      this.airDate = airDate;
   }

   @Generated
   public void setStillPath(final String stillPath) {
      this.stillPath = stillPath;
   }

   @Generated
   public void setWatched(final Boolean watched) {
      this.watched = watched;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbEpisodeResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$seasonNumber = this.getSeasonNumber();
         Object other$seasonNumber = other.getSeasonNumber();
         if (this$seasonNumber == null ? other$seasonNumber == null : this$seasonNumber.equals(other$seasonNumber)) {
            Object this$episodeNumber = this.getEpisodeNumber();
            Object other$episodeNumber = other.getEpisodeNumber();
            if (this$episodeNumber == null ? other$episodeNumber == null : this$episodeNumber.equals(other$episodeNumber)) {
               Object this$watched = this.getWatched();
               Object other$watched = other.getWatched();
               if (this$watched == null ? other$watched == null : this$watched.equals(other$watched)) {
                  Object this$name = this.getName();
                  Object other$name = other.getName();
                  if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                     Object this$overview = this.getOverview();
                     Object other$overview = other.getOverview();
                     if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                        Object this$airDate = this.getAirDate();
                        Object other$airDate = other.getAirDate();
                        if (this$airDate == null ? other$airDate == null : this$airDate.equals(other$airDate)) {
                           Object this$stillPath = this.getStillPath();
                           Object other$stillPath = other.getStillPath();
                           return this$stillPath == null ? other$stillPath == null : this$stillPath.equals(other$stillPath);
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
      return other instanceof TmdbEpisodeResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $seasonNumber = this.getSeasonNumber();
      result = result * 59 + ($seasonNumber == null ? 43 : $seasonNumber.hashCode());
      Object $episodeNumber = this.getEpisodeNumber();
      result = result * 59 + ($episodeNumber == null ? 43 : $episodeNumber.hashCode());
      Object $watched = this.getWatched();
      result = result * 59 + ($watched == null ? 43 : $watched.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $airDate = this.getAirDate();
      result = result * 59 + ($airDate == null ? 43 : $airDate.hashCode());
      Object $stillPath = this.getStillPath();
      return result * 59 + ($stillPath == null ? 43 : $stillPath.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbEpisodeResponse(seasonNumber="
         + this.getSeasonNumber()
         + ", episodeNumber="
         + this.getEpisodeNumber()
         + ", name="
         + this.getName()
         + ", overview="
         + this.getOverview()
         + ", airDate="
         + this.getAirDate()
         + ", stillPath="
         + this.getStillPath()
         + ", watched="
         + this.getWatched()
         + ")";
   }
}
