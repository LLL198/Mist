package com.una.embyhub.model.dto.request.requestlist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class RequestListUpdate implements Serializable {
   @NotNull(
      message = "主键id不能为空"
   )
   private Long id;
   @NotEmpty(
      message = "片名不能为空"
   )
   private String name;
   @NotEmpty(
      message = "类型不能为空"
   )
   private String type;
   @NotEmpty(
      message = "图片地址不能为空"
   )
   private String imageUrl;
   @NotEmpty(
      message = "tmdb地址不能为空"
   )
   private String tmdbUrl;
   private String doubanId;
   private String doubanUrl;
   private String doubanScore;
   private String doubanImage;
   @NotNull(
      message = "上映日期不能为空"
   )
   private Date releaseDate;
   @NotNull(
      message = "tmdb不能为空"
   )
   private Integer tmdbId;
   @NotEmpty(
      message = "简介不能为空"
   )
   private String overview;
   private Long embyInfoId;
   private String embyServerId;
   private Integer season;
   private Integer episode;
   private Integer parentTmdbId;
   private String remark;

   @Generated
   public Long getId() {
      return this.id;
   }

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
   public String getTmdbUrl() {
      return this.tmdbUrl;
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
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
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
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
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
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestListUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
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
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                              Object this$type = this.getType();
                              Object other$type = other.getType();
                              if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                 Object this$imageUrl = this.getImageUrl();
                                 Object other$imageUrl = other.getImageUrl();
                                 if (this$imageUrl == null ? other$imageUrl == null : this$imageUrl.equals(other$imageUrl)) {
                                    Object this$tmdbUrl = this.getTmdbUrl();
                                    Object other$tmdbUrl = other.getTmdbUrl();
                                    if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
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
                                                            Object this$remark = this.getRemark();
                                                            Object other$remark = other.getRemark();
                                                            return this$remark == null ? other$remark == null : this$remark.equals(other$remark);
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
      return other instanceof RequestListUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
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
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $imageUrl = this.getImageUrl();
      result = result * 59 + ($imageUrl == null ? 43 : $imageUrl.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $doubanUrl = this.getDoubanUrl();
      result = result * 59 + ($doubanUrl == null ? 43 : $doubanUrl.hashCode());
      Object $doubanScore = this.getDoubanScore();
      result = result * 59 + ($doubanScore == null ? 43 : $doubanScore.hashCode());
      Object $doubanImage = this.getDoubanImage();
      result = result * 59 + ($doubanImage == null ? 43 : $doubanImage.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestListUpdate(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", type="
         + this.getType()
         + ", imageUrl="
         + this.getImageUrl()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", doubanId="
         + this.getDoubanId()
         + ", doubanUrl="
         + this.getDoubanUrl()
         + ", doubanScore="
         + this.getDoubanScore()
         + ", doubanImage="
         + this.getDoubanImage()
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
         + ", season="
         + this.getSeason()
         + ", episode="
         + this.getEpisode()
         + ", parentTmdbId="
         + this.getParentTmdbId()
         + ", remark="
         + this.getRemark()
         + ")";
   }
}
