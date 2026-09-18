package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("emby_notify_data")
public class EmbyNotifyData extends BaseEntity implements Serializable {
   public static final String COL_EPISODE_DETAILS = "episode_details";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("`name`")
   private String name;
   @TableField("overview")
   private String overview;
   @TableField("production_year")
   private String productionYear;
   @TableField("`type`")
   private String type;
   @TableField("`status`")
   private Integer status;
   @TableField("img_url")
   private String imgUrl;
   @TableField("tmdb_url")
   private String tmdbUrl;
   @TableField("display_title")
   private String displayTitle;
   @TableField("genres")
   private String genres;
   @TableField("`size`")
   private String size;
   @TableField("audio_quality")
   private String audioQuality;
   @TableField("subtitle_info")
   private String subtitleInfo;
   @TableField("backdrop_path")
   private String backdropPath;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("vote_average")
   private Double voteAverage;
   @TableField("vote_count")
   private Integer voteCount;
   @TableField("production_countries")
   private String productionCountries;
   public static final String COL_ID = "id";
   public static final String COL_NAME = "name";
   public static final String COL_OVERVIEW = "overview";
   public static final String COL_PRODUCTION_YEAR = "production_year";
   public static final String COL_TYPE = "type";
   public static final String COL_STATUS = "status";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";
   public static final String COL_IMG_URL = "img_url";
   public static final String COL_TMDB_URL = "tmdb_url";
   public static final String COL_DISPLAY_TITLE = "display_title";
   public static final String COL_GENRES = "genres";
   public static final String COL_SIZE = "size";
   public static final String COL_AUDIO_QUALITY = "audio_quality";
   public static final String COL_SUBTITLE_INFO = "subtitle_info";
   public static final String COL_BACKDROP_PATH = "backdrop_path";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_VOTE_AVERAGE = "vote_average";
   public static final String COL_VOTE_COUNT = "vote_count";
   public static final String COL_PRODUCTION_COUNTRIES = "production_countries";

   @Generated
   public Long getId() {
      return this.id;
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
   public String getProductionYear() {
      return this.productionYear;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getImgUrl() {
      return this.imgUrl;
   }

   @Generated
   public String getTmdbUrl() {
      return this.tmdbUrl;
   }

   @Generated
   public String getDisplayTitle() {
      return this.displayTitle;
   }

   @Generated
   public String getGenres() {
      return this.genres;
   }

   @Generated
   public String getSize() {
      return this.size;
   }

   @Generated
   public String getAudioQuality() {
      return this.audioQuality;
   }

   @Generated
   public String getSubtitleInfo() {
      return this.subtitleInfo;
   }

   @Generated
   public String getBackdropPath() {
      return this.backdropPath;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public String getProductionCountries() {
      return this.productionCountries;
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
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setProductionYear(final String productionYear) {
      this.productionYear = productionYear;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setImgUrl(final String imgUrl) {
      this.imgUrl = imgUrl;
   }

   @Generated
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
   }

   @Generated
   public void setDisplayTitle(final String displayTitle) {
      this.displayTitle = displayTitle;
   }

   @Generated
   public void setGenres(final String genres) {
      this.genres = genres;
   }

   @Generated
   public void setSize(final String size) {
      this.size = size;
   }

   @Generated
   public void setAudioQuality(final String audioQuality) {
      this.audioQuality = audioQuality;
   }

   @Generated
   public void setSubtitleInfo(final String subtitleInfo) {
      this.subtitleInfo = subtitleInfo;
   }

   @Generated
   public void setBackdropPath(final String backdropPath) {
      this.backdropPath = backdropPath;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
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
   public void setProductionCountries(final String productionCountries) {
      this.productionCountries = productionCountries;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyNotifyData(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", overview="
         + this.getOverview()
         + ", productionYear="
         + this.getProductionYear()
         + ", type="
         + this.getType()
         + ", status="
         + this.getStatus()
         + ", imgUrl="
         + this.getImgUrl()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", displayTitle="
         + this.getDisplayTitle()
         + ", genres="
         + this.getGenres()
         + ", size="
         + this.getSize()
         + ", audioQuality="
         + this.getAudioQuality()
         + ", subtitleInfo="
         + this.getSubtitleInfo()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", voteAverage="
         + this.getVoteAverage()
         + ", voteCount="
         + this.getVoteCount()
         + ", productionCountries="
         + this.getProductionCountries()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyNotifyData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                  Object this$voteAverage = this.getVoteAverage();
                  Object other$voteAverage = other.getVoteAverage();
                  if (this$voteAverage == null ? other$voteAverage == null : this$voteAverage.equals(other$voteAverage)) {
                     Object this$voteCount = this.getVoteCount();
                     Object other$voteCount = other.getVoteCount();
                     if (this$voteCount == null ? other$voteCount == null : this$voteCount.equals(other$voteCount)) {
                        Object this$name = this.getName();
                        Object other$name = other.getName();
                        if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                           Object this$overview = this.getOverview();
                           Object other$overview = other.getOverview();
                           if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                              Object this$productionYear = this.getProductionYear();
                              Object other$productionYear = other.getProductionYear();
                              if (this$productionYear == null ? other$productionYear == null : this$productionYear.equals(other$productionYear)) {
                                 Object this$type = this.getType();
                                 Object other$type = other.getType();
                                 if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                    Object this$imgUrl = this.getImgUrl();
                                    Object other$imgUrl = other.getImgUrl();
                                    if (this$imgUrl == null ? other$imgUrl == null : this$imgUrl.equals(other$imgUrl)) {
                                       Object this$tmdbUrl = this.getTmdbUrl();
                                       Object other$tmdbUrl = other.getTmdbUrl();
                                       if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                                          Object this$displayTitle = this.getDisplayTitle();
                                          Object other$displayTitle = other.getDisplayTitle();
                                          if (this$displayTitle == null ? other$displayTitle == null : this$displayTitle.equals(other$displayTitle)) {
                                             Object this$genres = this.getGenres();
                                             Object other$genres = other.getGenres();
                                             if (this$genres == null ? other$genres == null : this$genres.equals(other$genres)) {
                                                Object this$size = this.getSize();
                                                Object other$size = other.getSize();
                                                if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                                                   Object this$audioQuality = this.getAudioQuality();
                                                   Object other$audioQuality = other.getAudioQuality();
                                                   if (this$audioQuality == null ? other$audioQuality == null : this$audioQuality.equals(other$audioQuality)) {
                                                      Object this$subtitleInfo = this.getSubtitleInfo();
                                                      Object other$subtitleInfo = other.getSubtitleInfo();
                                                      if (this$subtitleInfo == null ? other$subtitleInfo == null : this$subtitleInfo.equals(other$subtitleInfo)
                                                         )
                                                       {
                                                         Object this$backdropPath = this.getBackdropPath();
                                                         Object other$backdropPath = other.getBackdropPath();
                                                         if (this$backdropPath == null
                                                            ? other$backdropPath == null
                                                            : this$backdropPath.equals(other$backdropPath)) {
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
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyNotifyData;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $voteAverage = this.getVoteAverage();
      result = result * 59 + ($voteAverage == null ? 43 : $voteAverage.hashCode());
      Object $voteCount = this.getVoteCount();
      result = result * 59 + ($voteCount == null ? 43 : $voteCount.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $productionYear = this.getProductionYear();
      result = result * 59 + ($productionYear == null ? 43 : $productionYear.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $imgUrl = this.getImgUrl();
      result = result * 59 + ($imgUrl == null ? 43 : $imgUrl.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $displayTitle = this.getDisplayTitle();
      result = result * 59 + ($displayTitle == null ? 43 : $displayTitle.hashCode());
      Object $genres = this.getGenres();
      result = result * 59 + ($genres == null ? 43 : $genres.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
      Object $audioQuality = this.getAudioQuality();
      result = result * 59 + ($audioQuality == null ? 43 : $audioQuality.hashCode());
      Object $subtitleInfo = this.getSubtitleInfo();
      result = result * 59 + ($subtitleInfo == null ? 43 : $subtitleInfo.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $productionCountries = this.getProductionCountries();
      return result * 59 + ($productionCountries == null ? 43 : $productionCountries.hashCode());
   }
}
