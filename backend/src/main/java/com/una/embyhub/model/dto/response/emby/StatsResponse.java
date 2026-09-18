package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import lombok.Generated;

public class StatsResponse implements Serializable {
   private Integer movieCount = 0;
   private Integer seriesCount = 0;
   private Integer episodeCount = 0;
   private Integer musicCount = 0;

   @Generated
   public Integer getMovieCount() {
      return this.movieCount;
   }

   @Generated
   public Integer getSeriesCount() {
      return this.seriesCount;
   }

   @Generated
   public Integer getEpisodeCount() {
      return this.episodeCount;
   }

   @Generated
   public Integer getMusicCount() {
      return this.musicCount;
   }

   @Generated
   public void setMovieCount(final Integer movieCount) {
      this.movieCount = movieCount;
   }

   @Generated
   public void setSeriesCount(final Integer seriesCount) {
      this.seriesCount = seriesCount;
   }

   @Generated
   public void setEpisodeCount(final Integer episodeCount) {
      this.episodeCount = episodeCount;
   }

   @Generated
   public void setMusicCount(final Integer musicCount) {
      this.musicCount = musicCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof StatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$movieCount = this.getMovieCount();
         Object other$movieCount = other.getMovieCount();
         if (this$movieCount == null ? other$movieCount == null : this$movieCount.equals(other$movieCount)) {
            Object this$seriesCount = this.getSeriesCount();
            Object other$seriesCount = other.getSeriesCount();
            if (this$seriesCount == null ? other$seriesCount == null : this$seriesCount.equals(other$seriesCount)) {
               Object this$episodeCount = this.getEpisodeCount();
               Object other$episodeCount = other.getEpisodeCount();
               if (this$episodeCount == null ? other$episodeCount == null : this$episodeCount.equals(other$episodeCount)) {
                  Object this$musicCount = this.getMusicCount();
                  Object other$musicCount = other.getMusicCount();
                  return this$musicCount == null ? other$musicCount == null : this$musicCount.equals(other$musicCount);
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
      return other instanceof StatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $movieCount = this.getMovieCount();
      result = result * 59 + ($movieCount == null ? 43 : $movieCount.hashCode());
      Object $seriesCount = this.getSeriesCount();
      result = result * 59 + ($seriesCount == null ? 43 : $seriesCount.hashCode());
      Object $episodeCount = this.getEpisodeCount();
      result = result * 59 + ($episodeCount == null ? 43 : $episodeCount.hashCode());
      Object $musicCount = this.getMusicCount();
      return result * 59 + ($musicCount == null ? 43 : $musicCount.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "StatsResponse(movieCount="
         + this.getMovieCount()
         + ", seriesCount="
         + this.getSeriesCount()
         + ", episodeCount="
         + this.getEpisodeCount()
         + ", musicCount="
         + this.getMusicCount()
         + ")";
   }
}
