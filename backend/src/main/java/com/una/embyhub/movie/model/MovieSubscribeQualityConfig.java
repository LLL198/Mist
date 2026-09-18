package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MovieSubscribeQualityConfig implements Serializable {
   private static final long serialVersionUID = 1L;
   private String movieMinResolution;
   private String moviePreferredQuality;
   private String movieRequiredAudioEffects;
   private String movieRequiredTags;
   private String tvMinResolution;
   private String tvPreferredQuality;
   private String tvRequiredAudioEffects;
   private String tvRequiredTags;
   private Boolean strictFilter;

   @Generated
   public String getMovieMinResolution() {
      return this.movieMinResolution;
   }

   @Generated
   public String getMoviePreferredQuality() {
      return this.moviePreferredQuality;
   }

   @Generated
   public String getMovieRequiredAudioEffects() {
      return this.movieRequiredAudioEffects;
   }

   @Generated
   public String getMovieRequiredTags() {
      return this.movieRequiredTags;
   }

   @Generated
   public String getTvMinResolution() {
      return this.tvMinResolution;
   }

   @Generated
   public String getTvPreferredQuality() {
      return this.tvPreferredQuality;
   }

   @Generated
   public String getTvRequiredAudioEffects() {
      return this.tvRequiredAudioEffects;
   }

   @Generated
   public String getTvRequiredTags() {
      return this.tvRequiredTags;
   }

   @Generated
   public Boolean getStrictFilter() {
      return this.strictFilter;
   }

   @Generated
   public void setMovieMinResolution(final String movieMinResolution) {
      this.movieMinResolution = movieMinResolution;
   }

   @Generated
   public void setMoviePreferredQuality(final String moviePreferredQuality) {
      this.moviePreferredQuality = moviePreferredQuality;
   }

   @Generated
   public void setMovieRequiredAudioEffects(final String movieRequiredAudioEffects) {
      this.movieRequiredAudioEffects = movieRequiredAudioEffects;
   }

   @Generated
   public void setMovieRequiredTags(final String movieRequiredTags) {
      this.movieRequiredTags = movieRequiredTags;
   }

   @Generated
   public void setTvMinResolution(final String tvMinResolution) {
      this.tvMinResolution = tvMinResolution;
   }

   @Generated
   public void setTvPreferredQuality(final String tvPreferredQuality) {
      this.tvPreferredQuality = tvPreferredQuality;
   }

   @Generated
   public void setTvRequiredAudioEffects(final String tvRequiredAudioEffects) {
      this.tvRequiredAudioEffects = tvRequiredAudioEffects;
   }

   @Generated
   public void setTvRequiredTags(final String tvRequiredTags) {
      this.tvRequiredTags = tvRequiredTags;
   }

   @Generated
   public void setStrictFilter(final Boolean strictFilter) {
      this.strictFilter = strictFilter;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieSubscribeQualityConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$strictFilter = this.getStrictFilter();
         Object other$strictFilter = other.getStrictFilter();
         if (this$strictFilter == null ? other$strictFilter == null : this$strictFilter.equals(other$strictFilter)) {
            Object this$movieMinResolution = this.getMovieMinResolution();
            Object other$movieMinResolution = other.getMovieMinResolution();
            if (this$movieMinResolution == null ? other$movieMinResolution == null : this$movieMinResolution.equals(other$movieMinResolution)) {
               Object this$moviePreferredQuality = this.getMoviePreferredQuality();
               Object other$moviePreferredQuality = other.getMoviePreferredQuality();
               if (this$moviePreferredQuality == null ? other$moviePreferredQuality == null : this$moviePreferredQuality.equals(other$moviePreferredQuality)) {
                  Object this$movieRequiredAudioEffects = this.getMovieRequiredAudioEffects();
                  Object other$movieRequiredAudioEffects = other.getMovieRequiredAudioEffects();
                  if (this$movieRequiredAudioEffects == null
                     ? other$movieRequiredAudioEffects == null
                     : this$movieRequiredAudioEffects.equals(other$movieRequiredAudioEffects)) {
                     Object this$movieRequiredTags = this.getMovieRequiredTags();
                     Object other$movieRequiredTags = other.getMovieRequiredTags();
                     if (this$movieRequiredTags == null ? other$movieRequiredTags == null : this$movieRequiredTags.equals(other$movieRequiredTags)) {
                        Object this$tvMinResolution = this.getTvMinResolution();
                        Object other$tvMinResolution = other.getTvMinResolution();
                        if (this$tvMinResolution == null ? other$tvMinResolution == null : this$tvMinResolution.equals(other$tvMinResolution)) {
                           Object this$tvPreferredQuality = this.getTvPreferredQuality();
                           Object other$tvPreferredQuality = other.getTvPreferredQuality();
                           if (this$tvPreferredQuality == null ? other$tvPreferredQuality == null : this$tvPreferredQuality.equals(other$tvPreferredQuality)) {
                              Object this$tvRequiredAudioEffects = this.getTvRequiredAudioEffects();
                              Object other$tvRequiredAudioEffects = other.getTvRequiredAudioEffects();
                              if (this$tvRequiredAudioEffects == null
                                 ? other$tvRequiredAudioEffects == null
                                 : this$tvRequiredAudioEffects.equals(other$tvRequiredAudioEffects)) {
                                 Object this$tvRequiredTags = this.getTvRequiredTags();
                                 Object other$tvRequiredTags = other.getTvRequiredTags();
                                 return this$tvRequiredTags == null ? other$tvRequiredTags == null : this$tvRequiredTags.equals(other$tvRequiredTags);
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
      return other instanceof MovieSubscribeQualityConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $strictFilter = this.getStrictFilter();
      result = result * 59 + ($strictFilter == null ? 43 : $strictFilter.hashCode());
      Object $movieMinResolution = this.getMovieMinResolution();
      result = result * 59 + ($movieMinResolution == null ? 43 : $movieMinResolution.hashCode());
      Object $moviePreferredQuality = this.getMoviePreferredQuality();
      result = result * 59 + ($moviePreferredQuality == null ? 43 : $moviePreferredQuality.hashCode());
      Object $movieRequiredAudioEffects = this.getMovieRequiredAudioEffects();
      result = result * 59 + ($movieRequiredAudioEffects == null ? 43 : $movieRequiredAudioEffects.hashCode());
      Object $movieRequiredTags = this.getMovieRequiredTags();
      result = result * 59 + ($movieRequiredTags == null ? 43 : $movieRequiredTags.hashCode());
      Object $tvMinResolution = this.getTvMinResolution();
      result = result * 59 + ($tvMinResolution == null ? 43 : $tvMinResolution.hashCode());
      Object $tvPreferredQuality = this.getTvPreferredQuality();
      result = result * 59 + ($tvPreferredQuality == null ? 43 : $tvPreferredQuality.hashCode());
      Object $tvRequiredAudioEffects = this.getTvRequiredAudioEffects();
      result = result * 59 + ($tvRequiredAudioEffects == null ? 43 : $tvRequiredAudioEffects.hashCode());
      Object $tvRequiredTags = this.getTvRequiredTags();
      return result * 59 + ($tvRequiredTags == null ? 43 : $tvRequiredTags.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieSubscribeQualityConfig(movieMinResolution="
         + this.getMovieMinResolution()
         + ", moviePreferredQuality="
         + this.getMoviePreferredQuality()
         + ", movieRequiredAudioEffects="
         + this.getMovieRequiredAudioEffects()
         + ", movieRequiredTags="
         + this.getMovieRequiredTags()
         + ", tvMinResolution="
         + this.getTvMinResolution()
         + ", tvPreferredQuality="
         + this.getTvPreferredQuality()
         + ", tvRequiredAudioEffects="
         + this.getTvRequiredAudioEffects()
         + ", tvRequiredTags="
         + this.getTvRequiredTags()
         + ", strictFilter="
         + this.getStrictFilter()
         + ")";
   }
}
