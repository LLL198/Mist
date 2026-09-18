package com.una.embyhub.model.dto.response.tmdb;

import lombok.Generated;

public class MediaItemResponse {
   private String id;
   private double voteAverage;
   private double popularity;
   private String releaseDate;
   private double score;

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public double getVoteAverage() {
      return this.voteAverage;
   }

   @Generated
   public double getPopularity() {
      return this.popularity;
   }

   @Generated
   public String getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public double getScore() {
      return this.score;
   }

   @Generated
   public void setId(final String id) {
      this.id = id;
   }

   @Generated
   public void setVoteAverage(final double voteAverage) {
      this.voteAverage = voteAverage;
   }

   @Generated
   public void setPopularity(final double popularity) {
      this.popularity = popularity;
   }

   @Generated
   public void setReleaseDate(final String releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setScore(final double score) {
      this.score = score;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MediaItemResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (Double.compare(this.getVoteAverage(), other.getVoteAverage()) != 0) {
         return false;
      } else if (Double.compare(this.getPopularity(), other.getPopularity()) != 0) {
         return false;
      } else if (Double.compare(this.getScore(), other.getScore()) != 0) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$releaseDate = this.getReleaseDate();
            Object other$releaseDate = other.getReleaseDate();
            return this$releaseDate == null ? other$releaseDate == null : this$releaseDate.equals(other$releaseDate);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof MediaItemResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $voteAverage = Double.doubleToLongBits(this.getVoteAverage());
      result = result * 59 + (int)($voteAverage >>> 32 ^ $voteAverage);
      long $popularity = Double.doubleToLongBits(this.getPopularity());
      result = result * 59 + (int)($popularity >>> 32 ^ $popularity);
      long $score = Double.doubleToLongBits(this.getScore());
      result = result * 59 + (int)($score >>> 32 ^ $score);
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $releaseDate = this.getReleaseDate();
      return result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MediaItemResponse(id="
         + this.getId()
         + ", voteAverage="
         + this.getVoteAverage()
         + ", popularity="
         + this.getPopularity()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", score="
         + this.getScore()
         + ")";
   }

   @Generated
   public MediaItemResponse(final String id, final double voteAverage, final double popularity, final String releaseDate, final double score) {
      this.id = id;
      this.voteAverage = voteAverage;
      this.popularity = popularity;
      this.releaseDate = releaseDate;
      this.score = score;
   }
}
