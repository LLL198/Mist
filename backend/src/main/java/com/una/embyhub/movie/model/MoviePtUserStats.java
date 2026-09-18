package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MoviePtUserStats implements Serializable {
   private static final long serialVersionUID = 1L;
   private String username;
   private String uploaded;
   private String downloaded;
   private String ratio;

   @Generated
   MoviePtUserStats(final String username, final String uploaded, final String downloaded, final String ratio) {
      this.username = username;
      this.uploaded = uploaded;
      this.downloaded = downloaded;
      this.ratio = ratio;
   }

   @Generated
   public static MoviePtUserStats.MoviePtUserStatsBuilder builder() {
      return new MoviePtUserStats.MoviePtUserStatsBuilder();
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getUploaded() {
      return this.uploaded;
   }

   @Generated
   public String getDownloaded() {
      return this.downloaded;
   }

   @Generated
   public String getRatio() {
      return this.ratio;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setUploaded(final String uploaded) {
      this.uploaded = uploaded;
   }

   @Generated
   public void setDownloaded(final String downloaded) {
      this.downloaded = downloaded;
   }

   @Generated
   public void setRatio(final String ratio) {
      this.ratio = ratio;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtUserStats other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$username = this.getUsername();
         Object other$username = other.getUsername();
         if (this$username == null ? other$username == null : this$username.equals(other$username)) {
            Object this$uploaded = this.getUploaded();
            Object other$uploaded = other.getUploaded();
            if (this$uploaded == null ? other$uploaded == null : this$uploaded.equals(other$uploaded)) {
               Object this$downloaded = this.getDownloaded();
               Object other$downloaded = other.getDownloaded();
               if (this$downloaded == null ? other$downloaded == null : this$downloaded.equals(other$downloaded)) {
                  Object this$ratio = this.getRatio();
                  Object other$ratio = other.getRatio();
                  return this$ratio == null ? other$ratio == null : this$ratio.equals(other$ratio);
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
      return other instanceof MoviePtUserStats;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $uploaded = this.getUploaded();
      result = result * 59 + ($uploaded == null ? 43 : $uploaded.hashCode());
      Object $downloaded = this.getDownloaded();
      result = result * 59 + ($downloaded == null ? 43 : $downloaded.hashCode());
      Object $ratio = this.getRatio();
      return result * 59 + ($ratio == null ? 43 : $ratio.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtUserStats(username="
         + this.getUsername()
         + ", uploaded="
         + this.getUploaded()
         + ", downloaded="
         + this.getDownloaded()
         + ", ratio="
         + this.getRatio()
         + ")";
   }

   @Generated
   public static class MoviePtUserStatsBuilder {
      @Generated
      private String username;
      @Generated
      private String uploaded;
      @Generated
      private String downloaded;
      @Generated
      private String ratio;

      @Generated
      MoviePtUserStatsBuilder() {
      }

      @Generated
      public MoviePtUserStats.MoviePtUserStatsBuilder username(final String username) {
         this.username = username;
         return this;
      }

      @Generated
      public MoviePtUserStats.MoviePtUserStatsBuilder uploaded(final String uploaded) {
         this.uploaded = uploaded;
         return this;
      }

      @Generated
      public MoviePtUserStats.MoviePtUserStatsBuilder downloaded(final String downloaded) {
         this.downloaded = downloaded;
         return this;
      }

      @Generated
      public MoviePtUserStats.MoviePtUserStatsBuilder ratio(final String ratio) {
         this.ratio = ratio;
         return this;
      }

      @Generated
      public MoviePtUserStats build() {
         return new MoviePtUserStats(this.username, this.uploaded, this.downloaded, this.ratio);
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePtUserStats.MoviePtUserStatsBuilder(username="
            + this.username
            + ", uploaded="
            + this.uploaded
            + ", downloaded="
            + this.downloaded
            + ", ratio="
            + this.ratio
            + ")";
      }
   }
}
