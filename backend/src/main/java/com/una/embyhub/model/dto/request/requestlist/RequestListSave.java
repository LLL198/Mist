package com.una.embyhub.model.dto.request.requestlist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class RequestListSave implements Serializable {
   @NotEmpty(
      message = "片名不能为空"
   )
   private String name;
   @NotEmpty(
      message = "类型不能为空"
   )
   private String type;
   private String imageUrl;
   @NotEmpty(
      message = "评分不能为空"
   )
   private String score;
   private String doubanId;
   private String doubanUrl;
   private String doubanScore;
   private String doubanImage;
   @NotEmpty(
      message = "tmdb地址不能为空"
   )
   private String tmdbUrl;
   private Date releaseDate;
   @NotNull(
      message = "tmdb不能为空"
   )
   private Integer tmdbId;
   private String overview;
   private Long embyInfoId;
   private String embyServerId;
   private String backdropPath;
   private Integer season;
   private Integer episode;
   private Integer parentTmdbId;
   private String originalName;
   private String remark;
   private Integer runtime;
   private String productionCountries;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getImageUrl() {
      return this.imageUrl;
   }

   @Generated
   public String getScore() {
      return this.score;
   }

   @Generated
   public String getDoubanId() {
      return this.doubanId;
   }

   @Generated
   public String getDoubanUrl() {
      return this.doubanUrl;
   }

   @Generated
   public String getDoubanScore() {
      return this.doubanScore;
   }

   @Generated
   public String getDoubanImage() {
      return this.doubanImage;
   }

   @Generated
   public String getTmdbUrl() {
      return this.tmdbUrl;
   }

   @Generated
   public Date getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getEmbyServerId() {
      return this.embyServerId;
   }

   @Generated
   public String getBackdropPath() {
      return this.backdropPath;
   }

   @Generated
   public Integer getSeason() {
      return this.season;
   }

   @Generated
   public Integer getEpisode() {
      return this.episode;
   }

   @Generated
   public Integer getParentTmdbId() {
      return this.parentTmdbId;
   }

   @Generated
   public String getOriginalName() {
      return this.originalName;
   }

   @Generated
   public String getRemark() {
      return this.remark;
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
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setImageUrl(final String imageUrl) {
      this.imageUrl = imageUrl;
   }

   @Generated
   public void setScore(final String score) {
      this.score = score;
   }

   @Generated
   public void setDoubanId(final String doubanId) {
      this.doubanId = doubanId;
   }

   @Generated
   public void setDoubanUrl(final String doubanUrl) {
      this.doubanUrl = doubanUrl;
   }

   @Generated
   public void setDoubanScore(final String doubanScore) {
      this.doubanScore = doubanScore;
   }

   @Generated
   public void setDoubanImage(final String doubanImage) {
      this.doubanImage = doubanImage;
   }

   @Generated
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
   }

   @Generated
   public void setReleaseDate(final Date releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEmbyServerId(final String embyServerId) {
      this.embyServerId = embyServerId;
   }

   @Generated
   public void setBackdropPath(final String backdropPath) {
      this.backdropPath = backdropPath;
   }

   @Generated
   public void setSeason(final Integer season) {
      this.season = season;
   }

   @Generated
   public void setEpisode(final Integer episode) {
      this.episode = episode;
   }

   @Generated
   public void setParentTmdbId(final Integer parentTmdbId) {
      this.parentTmdbId = parentTmdbId;
   }

   @Generated
   public void setOriginalName(final String originalName) {
      this.originalName = originalName;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
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
      } else if (!(o instanceof RequestListSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbId = this.getTmdbId();
         Object other$tmdbId = other.getTmdbId();
         if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$season = this.getSeason();
               Object other$season = other.getSeason();
               if (this$season == null ? other$season == null : this$season.equals(other$season)) {
                  Object this$episode = this.getEpisode();
                  Object other$episode = other.getEpisode();
                  if (this$episode == null ? other$episode == null : this$episode.equals(other$episode)) {
                     Object this$parentTmdbId = this.getParentTmdbId();
                     Object other$parentTmdbId = other.getParentTmdbId();
                     if (this$parentTmdbId == null ? other$parentTmdbId == null : this$parentTmdbId.equals(other$parentTmdbId)) {
                        Object this$runtime = this.getRuntime();
                        Object other$runtime = other.getRuntime();
                        if (this$runtime == null ? other$runtime == null : this$runtime.equals(other$runtime)) {
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                              Object this$type = this.getType();
                              Object other$type = other.getType();
                              if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                 Object this$imageUrl = this.getImageUrl();
                                 Object other$imageUrl = other.getImageUrl();
                                 if (this$imageUrl == null ? other$imageUrl == null : this$imageUrl.equals(other$imageUrl)) {
                                    Object this$score = this.getScore();
                                    Object other$score = other.getScore();
                                    if (this$score == null ? other$score == null : this$score.equals(other$score)) {
                                       Object this$doubanId = this.getDoubanId();
                                       Object other$doubanId = other.getDoubanId();
                                       if (this$doubanId == null ? other$doubanId == null : this$doubanId.equals(other$doubanId)) {
                                          Object this$doubanUrl = this.getDoubanUrl();
                                          Object other$doubanUrl = other.getDoubanUrl();
                                          if (this$doubanUrl == null ? other$doubanUrl == null : this$doubanUrl.equals(other$doubanUrl)) {
                                             Object this$doubanScore = this.getDoubanScore();
                                             Object other$doubanScore = other.getDoubanScore();
                                             if (this$doubanScore == null ? other$doubanScore == null : this$doubanScore.equals(other$doubanScore)) {
                                                Object this$doubanImage = this.getDoubanImage();
                                                Object other$doubanImage = other.getDoubanImage();
                                                if (this$doubanImage == null ? other$doubanImage == null : this$doubanImage.equals(other$doubanImage)) {
                                                   Object this$tmdbUrl = this.getTmdbUrl();
                                                   Object other$tmdbUrl = other.getTmdbUrl();
                                                   if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                                                      Object this$releaseDate = this.getReleaseDate();
                                                      Object other$releaseDate = other.getReleaseDate();
                                                      if (this$releaseDate == null ? other$releaseDate == null : this$releaseDate.equals(other$releaseDate)) {
                                                         Object this$overview = this.getOverview();
                                                         Object other$overview = other.getOverview();
                                                         if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                                            Object this$embyServerId = this.getEmbyServerId();
                                                            Object other$embyServerId = other.getEmbyServerId();
                                                            if (this$embyServerId == null
                                                               ? other$embyServerId == null
                                                               : this$embyServerId.equals(other$embyServerId)) {
                                                               Object this$backdropPath = this.getBackdropPath();
                                                               Object other$backdropPath = other.getBackdropPath();
                                                               if (this$backdropPath == null
                                                                  ? other$backdropPath == null
                                                                  : this$backdropPath.equals(other$backdropPath)) {
                                                                  Object this$originalName = this.getOriginalName();
                                                                  Object other$originalName = other.getOriginalName();
                                                                  if (this$originalName == null
                                                                     ? other$originalName == null
                                                                     : this$originalName.equals(other$originalName)) {
                                                                     Object this$remark = this.getRemark();
                                                                     Object other$remark = other.getRemark();
                                                                     if (this$remark == null ? other$remark == null : this$remark.equals(other$remark)) {
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
      return other instanceof RequestListSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $episode = this.getEpisode();
      result = result * 59 + ($episode == null ? 43 : $episode.hashCode());
      Object $parentTmdbId = this.getParentTmdbId();
      result = result * 59 + ($parentTmdbId == null ? 43 : $parentTmdbId.hashCode());
      Object $runtime = this.getRuntime();
      result = result * 59 + ($runtime == null ? 43 : $runtime.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $imageUrl = this.getImageUrl();
      result = result * 59 + ($imageUrl == null ? 43 : $imageUrl.hashCode());
      Object $score = this.getScore();
      result = result * 59 + ($score == null ? 43 : $score.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $doubanUrl = this.getDoubanUrl();
      result = result * 59 + ($doubanUrl == null ? 43 : $doubanUrl.hashCode());
      Object $doubanScore = this.getDoubanScore();
      result = result * 59 + ($doubanScore == null ? 43 : $doubanScore.hashCode());
      Object $doubanImage = this.getDoubanImage();
      result = result * 59 + ($doubanImage == null ? 43 : $doubanImage.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $originalName = this.getOriginalName();
      result = result * 59 + ($originalName == null ? 43 : $originalName.hashCode());
      Object $remark = this.getRemark();
      result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
      Object $productionCountries = this.getProductionCountries();
      return result * 59 + ($productionCountries == null ? 43 : $productionCountries.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestListSave(name="
         + this.getName()
         + ", type="
         + this.getType()
         + ", imageUrl="
         + this.getImageUrl()
         + ", score="
         + this.getScore()
         + ", doubanId="
         + this.getDoubanId()
         + ", doubanUrl="
         + this.getDoubanUrl()
         + ", doubanScore="
         + this.getDoubanScore()
         + ", doubanImage="
         + this.getDoubanImage()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", tmdbId="
         + this.getTmdbId()
         + ", overview="
         + this.getOverview()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", season="
         + this.getSeason()
         + ", episode="
         + this.getEpisode()
         + ", parentTmdbId="
         + this.getParentTmdbId()
         + ", originalName="
         + this.getOriginalName()
         + ", remark="
         + this.getRemark()
         + ", runtime="
         + this.getRuntime()
         + ", productionCountries="
         + this.getProductionCountries()
         + ")";
   }
}
