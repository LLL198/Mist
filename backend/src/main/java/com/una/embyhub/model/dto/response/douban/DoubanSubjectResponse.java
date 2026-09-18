package com.una.embyhub.model.dto.response.douban;

import java.util.List;
import lombok.Generated;

public class DoubanSubjectResponse extends DoubanSimpleSubjectResponse {
   private String intro;
   private List<String> directors;
   private List<String> actors;
   private List<String> regions;
   private List<String> languages;
   private List<String> durations;
   private String score;
   private Integer currentSeason;
   private Integer seasonsCount;
   private Integer episodesCount;
   private String episodesInfo;

   @Generated
   public String getIntro() {
      return this.intro;
   }

   @Generated
   public List<String> getDirectors() {
      return this.directors;
   }

   @Generated
   public List<String> getActors() {
      return this.actors;
   }

   @Generated
   public List<String> getRegions() {
      return this.regions;
   }

   @Generated
   public List<String> getLanguages() {
      return this.languages;
   }

   @Generated
   public List<String> getDurations() {
      return this.durations;
   }

   @Generated
   public String getScore() {
      return this.score;
   }

   @Generated
   public Integer getCurrentSeason() {
      return this.currentSeason;
   }

   @Generated
   public Integer getSeasonsCount() {
      return this.seasonsCount;
   }

   @Generated
   public Integer getEpisodesCount() {
      return this.episodesCount;
   }

   @Generated
   public String getEpisodesInfo() {
      return this.episodesInfo;
   }

   @Generated
   public void setIntro(final String intro) {
      this.intro = intro;
   }

   @Generated
   public void setDirectors(final List<String> directors) {
      this.directors = directors;
   }

   @Generated
   public void setActors(final List<String> actors) {
      this.actors = actors;
   }

   @Generated
   public void setRegions(final List<String> regions) {
      this.regions = regions;
   }

   @Generated
   public void setLanguages(final List<String> languages) {
      this.languages = languages;
   }

   @Generated
   public void setDurations(final List<String> durations) {
      this.durations = durations;
   }

   @Generated
   public void setScore(final String score) {
      this.score = score;
   }

   @Generated
   public void setCurrentSeason(final Integer currentSeason) {
      this.currentSeason = currentSeason;
   }

   @Generated
   public void setSeasonsCount(final Integer seasonsCount) {
      this.seasonsCount = seasonsCount;
   }

   @Generated
   public void setEpisodesCount(final Integer episodesCount) {
      this.episodesCount = episodesCount;
   }

   @Generated
   public void setEpisodesInfo(final String episodesInfo) {
      this.episodesInfo = episodesInfo;
   }

   @Generated
   @Override
   public String toString() {
      return "DoubanSubjectResponse(intro="
         + this.getIntro()
         + ", directors="
         + this.getDirectors()
         + ", actors="
         + this.getActors()
         + ", regions="
         + this.getRegions()
         + ", languages="
         + this.getLanguages()
         + ", durations="
         + this.getDurations()
         + ", score="
         + this.getScore()
         + ", currentSeason="
         + this.getCurrentSeason()
         + ", seasonsCount="
         + this.getSeasonsCount()
         + ", episodesCount="
         + this.getEpisodesCount()
         + ", episodesInfo="
         + this.getEpisodesInfo()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DoubanSubjectResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$currentSeason = this.getCurrentSeason();
         Object other$currentSeason = other.getCurrentSeason();
         if (this$currentSeason == null ? other$currentSeason == null : this$currentSeason.equals(other$currentSeason)) {
            Object this$seasonsCount = this.getSeasonsCount();
            Object other$seasonsCount = other.getSeasonsCount();
            if (this$seasonsCount == null ? other$seasonsCount == null : this$seasonsCount.equals(other$seasonsCount)) {
               Object this$episodesCount = this.getEpisodesCount();
               Object other$episodesCount = other.getEpisodesCount();
               if (this$episodesCount == null ? other$episodesCount == null : this$episodesCount.equals(other$episodesCount)) {
                  Object this$intro = this.getIntro();
                  Object other$intro = other.getIntro();
                  if (this$intro == null ? other$intro == null : this$intro.equals(other$intro)) {
                     Object this$directors = this.getDirectors();
                     Object other$directors = other.getDirectors();
                     if (this$directors == null ? other$directors == null : this$directors.equals(other$directors)) {
                        Object this$actors = this.getActors();
                        Object other$actors = other.getActors();
                        if (this$actors == null ? other$actors == null : this$actors.equals(other$actors)) {
                           Object this$regions = this.getRegions();
                           Object other$regions = other.getRegions();
                           if (this$regions == null ? other$regions == null : this$regions.equals(other$regions)) {
                              Object this$languages = this.getLanguages();
                              Object other$languages = other.getLanguages();
                              if (this$languages == null ? other$languages == null : this$languages.equals(other$languages)) {
                                 Object this$durations = this.getDurations();
                                 Object other$durations = other.getDurations();
                                 if (this$durations == null ? other$durations == null : this$durations.equals(other$durations)) {
                                    Object this$score = this.getScore();
                                    Object other$score = other.getScore();
                                    if (this$score == null ? other$score == null : this$score.equals(other$score)) {
                                       Object this$episodesInfo = this.getEpisodesInfo();
                                       Object other$episodesInfo = other.getEpisodesInfo();
                                       return this$episodesInfo == null ? other$episodesInfo == null : this$episodesInfo.equals(other$episodesInfo);
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
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof DoubanSubjectResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $currentSeason = this.getCurrentSeason();
      result = result * 59 + ($currentSeason == null ? 43 : $currentSeason.hashCode());
      Object $seasonsCount = this.getSeasonsCount();
      result = result * 59 + ($seasonsCount == null ? 43 : $seasonsCount.hashCode());
      Object $episodesCount = this.getEpisodesCount();
      result = result * 59 + ($episodesCount == null ? 43 : $episodesCount.hashCode());
      Object $intro = this.getIntro();
      result = result * 59 + ($intro == null ? 43 : $intro.hashCode());
      Object $directors = this.getDirectors();
      result = result * 59 + ($directors == null ? 43 : $directors.hashCode());
      Object $actors = this.getActors();
      result = result * 59 + ($actors == null ? 43 : $actors.hashCode());
      Object $regions = this.getRegions();
      result = result * 59 + ($regions == null ? 43 : $regions.hashCode());
      Object $languages = this.getLanguages();
      result = result * 59 + ($languages == null ? 43 : $languages.hashCode());
      Object $durations = this.getDurations();
      result = result * 59 + ($durations == null ? 43 : $durations.hashCode());
      Object $score = this.getScore();
      result = result * 59 + ($score == null ? 43 : $score.hashCode());
      Object $episodesInfo = this.getEpisodesInfo();
      return result * 59 + ($episodesInfo == null ? 43 : $episodesInfo.hashCode());
   }
}
