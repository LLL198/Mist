package com.una.embyhub.movie.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.una.embyhub.model.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("movie_pt_subscribe")
public class MoviePtSubscribeEntity extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("name")
   private String name;
   @TableField("original_title")
   private String originalTitle;
   @TableField("keyword")
   private String keyword;
   @TableField("type")
   private String type;
   @TableField("year")
   private String year;
   @TableField("tmdb_id")
   private Long tmdbId;
   @TableField("poster_path")
   private String posterPath;
   @TableField("backdrop_path")
   private String backdropPath;
   @TableField("season")
   private Integer season;
   @TableField("start_episode")
   private Integer startEpisode;
   @TableField(
      value = "site_id",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String siteId;
   @TableField(
      value = "scrape_path_config_id",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private Long scrapePathConfigId;
   @TableField("downloader_id")
   private Long downloaderId;
   @TableField("auto_download")
   private Integer autoDownload;
   @TableField("enabled")
   private Integer enabled;
   @TableField("state")
   private String state;
   @TableField("tmdb_latest_episode")
   private Integer tmdbLatestEpisode;
   @TableField("last_downloaded_episode")
   private Integer lastDownloadedEpisode;
   @TableField("last_matched_count")
   private Integer lastMatchedCount;
   @TableField("last_search_time")
   private Date lastSearchTime;
   @TableField("last_download_time")
   private Date lastDownloadTime;
   @TableField(
      value = "last_error",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String lastError;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getOriginalTitle() {
      return this.originalTitle;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getYear() {
      return this.year;
   }

   @Generated
   public Long getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getPosterPath() {
      return this.posterPath;
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
   public Integer getStartEpisode() {
      return this.startEpisode;
   }

   @Generated
   public String getSiteId() {
      return this.siteId;
   }

   @Generated
   public Long getScrapePathConfigId() {
      return this.scrapePathConfigId;
   }

   @Generated
   public Long getDownloaderId() {
      return this.downloaderId;
   }

   @Generated
   public Integer getAutoDownload() {
      return this.autoDownload;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getState() {
      return this.state;
   }

   @Generated
   public Integer getTmdbLatestEpisode() {
      return this.tmdbLatestEpisode;
   }

   @Generated
   public Integer getLastDownloadedEpisode() {
      return this.lastDownloadedEpisode;
   }

   @Generated
   public Integer getLastMatchedCount() {
      return this.lastMatchedCount;
   }

   @Generated
   public Date getLastSearchTime() {
      return this.lastSearchTime;
   }

   @Generated
   public Date getLastDownloadTime() {
      return this.lastDownloadTime;
   }

   @Generated
   public String getLastError() {
      return this.lastError;
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
   public void setOriginalTitle(final String originalTitle) {
      this.originalTitle = originalTitle;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setYear(final String year) {
      this.year = year;
   }

   @Generated
   public void setTmdbId(final Long tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setPosterPath(final String posterPath) {
      this.posterPath = posterPath;
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
   public void setStartEpisode(final Integer startEpisode) {
      this.startEpisode = startEpisode;
   }

   @Generated
   public void setSiteId(final String siteId) {
      this.siteId = siteId;
   }

   @Generated
   public void setScrapePathConfigId(final Long scrapePathConfigId) {
      this.scrapePathConfigId = scrapePathConfigId;
   }

   @Generated
   public void setDownloaderId(final Long downloaderId) {
      this.downloaderId = downloaderId;
   }

   @Generated
   public void setAutoDownload(final Integer autoDownload) {
      this.autoDownload = autoDownload;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setState(final String state) {
      this.state = state;
   }

   @Generated
   public void setTmdbLatestEpisode(final Integer tmdbLatestEpisode) {
      this.tmdbLatestEpisode = tmdbLatestEpisode;
   }

   @Generated
   public void setLastDownloadedEpisode(final Integer lastDownloadedEpisode) {
      this.lastDownloadedEpisode = lastDownloadedEpisode;
   }

   @Generated
   public void setLastMatchedCount(final Integer lastMatchedCount) {
      this.lastMatchedCount = lastMatchedCount;
   }

   @Generated
   public void setLastSearchTime(final Date lastSearchTime) {
      this.lastSearchTime = lastSearchTime;
   }

   @Generated
   public void setLastDownloadTime(final Date lastDownloadTime) {
      this.lastDownloadTime = lastDownloadTime;
   }

   @Generated
   public void setLastError(final String lastError) {
      this.lastError = lastError;
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSubscribeEntity(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", originalTitle="
         + this.getOriginalTitle()
         + ", keyword="
         + this.getKeyword()
         + ", type="
         + this.getType()
         + ", year="
         + this.getYear()
         + ", tmdbId="
         + this.getTmdbId()
         + ", posterPath="
         + this.getPosterPath()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", season="
         + this.getSeason()
         + ", startEpisode="
         + this.getStartEpisode()
         + ", siteId="
         + this.getSiteId()
         + ", scrapePathConfigId="
         + this.getScrapePathConfigId()
         + ", downloaderId="
         + this.getDownloaderId()
         + ", autoDownload="
         + this.getAutoDownload()
         + ", enabled="
         + this.getEnabled()
         + ", state="
         + this.getState()
         + ", tmdbLatestEpisode="
         + this.getTmdbLatestEpisode()
         + ", lastDownloadedEpisode="
         + this.getLastDownloadedEpisode()
         + ", lastMatchedCount="
         + this.getLastMatchedCount()
         + ", lastSearchTime="
         + this.getLastSearchTime()
         + ", lastDownloadTime="
         + this.getLastDownloadTime()
         + ", lastError="
         + this.getLastError()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSubscribeEntity other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$tmdbId = this.getTmdbId();
            Object other$tmdbId = other.getTmdbId();
            if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
               Object this$season = this.getSeason();
               Object other$season = other.getSeason();
               if (this$season == null ? other$season == null : this$season.equals(other$season)) {
                  Object this$startEpisode = this.getStartEpisode();
                  Object other$startEpisode = other.getStartEpisode();
                  if (this$startEpisode == null ? other$startEpisode == null : this$startEpisode.equals(other$startEpisode)) {
                     Object this$scrapePathConfigId = this.getScrapePathConfigId();
                     Object other$scrapePathConfigId = other.getScrapePathConfigId();
                     if (this$scrapePathConfigId == null ? other$scrapePathConfigId == null : this$scrapePathConfigId.equals(other$scrapePathConfigId)) {
                        Object this$downloaderId = this.getDownloaderId();
                        Object other$downloaderId = other.getDownloaderId();
                        if (this$downloaderId == null ? other$downloaderId == null : this$downloaderId.equals(other$downloaderId)) {
                           Object this$autoDownload = this.getAutoDownload();
                           Object other$autoDownload = other.getAutoDownload();
                           if (this$autoDownload == null ? other$autoDownload == null : this$autoDownload.equals(other$autoDownload)) {
                              Object this$enabled = this.getEnabled();
                              Object other$enabled = other.getEnabled();
                              if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                                 Object this$tmdbLatestEpisode = this.getTmdbLatestEpisode();
                                 Object other$tmdbLatestEpisode = other.getTmdbLatestEpisode();
                                 if (this$tmdbLatestEpisode == null ? other$tmdbLatestEpisode == null : this$tmdbLatestEpisode.equals(other$tmdbLatestEpisode)) {
                                    Object this$lastDownloadedEpisode = this.getLastDownloadedEpisode();
                                    Object other$lastDownloadedEpisode = other.getLastDownloadedEpisode();
                                    if (this$lastDownloadedEpisode == null
                                       ? other$lastDownloadedEpisode == null
                                       : this$lastDownloadedEpisode.equals(other$lastDownloadedEpisode)) {
                                       Object this$lastMatchedCount = this.getLastMatchedCount();
                                       Object other$lastMatchedCount = other.getLastMatchedCount();
                                       if (this$lastMatchedCount == null
                                          ? other$lastMatchedCount == null
                                          : this$lastMatchedCount.equals(other$lastMatchedCount)) {
                                          Object this$name = this.getName();
                                          Object other$name = other.getName();
                                          if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                             Object this$originalTitle = this.getOriginalTitle();
                                             Object other$originalTitle = other.getOriginalTitle();
                                             if (this$originalTitle == null ? other$originalTitle == null : this$originalTitle.equals(other$originalTitle)) {
                                                Object this$keyword = this.getKeyword();
                                                Object other$keyword = other.getKeyword();
                                                if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                                                   Object this$type = this.getType();
                                                   Object other$type = other.getType();
                                                   if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                                      Object this$year = this.getYear();
                                                      Object other$year = other.getYear();
                                                      if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                                                         Object this$posterPath = this.getPosterPath();
                                                         Object other$posterPath = other.getPosterPath();
                                                         if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                                            Object this$backdropPath = this.getBackdropPath();
                                                            Object other$backdropPath = other.getBackdropPath();
                                                            if (this$backdropPath == null
                                                               ? other$backdropPath == null
                                                               : this$backdropPath.equals(other$backdropPath)) {
                                                               Object this$siteId = this.getSiteId();
                                                               Object other$siteId = other.getSiteId();
                                                               if (this$siteId == null ? other$siteId == null : this$siteId.equals(other$siteId)) {
                                                                  Object this$state = this.getState();
                                                                  Object other$state = other.getState();
                                                                  if (this$state == null ? other$state == null : this$state.equals(other$state)) {
                                                                     Object this$lastSearchTime = this.getLastSearchTime();
                                                                     Object other$lastSearchTime = other.getLastSearchTime();
                                                                     if (this$lastSearchTime == null
                                                                        ? other$lastSearchTime == null
                                                                        : this$lastSearchTime.equals(other$lastSearchTime)) {
                                                                        Object this$lastDownloadTime = this.getLastDownloadTime();
                                                                        Object other$lastDownloadTime = other.getLastDownloadTime();
                                                                        if (this$lastDownloadTime == null
                                                                           ? other$lastDownloadTime == null
                                                                           : this$lastDownloadTime.equals(other$lastDownloadTime)) {
                                                                           Object this$lastError = this.getLastError();
                                                                           Object other$lastError = other.getLastError();
                                                                           return this$lastError == null
                                                                              ? other$lastError == null
                                                                              : this$lastError.equals(other$lastError);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof MoviePtSubscribeEntity;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $startEpisode = this.getStartEpisode();
      result = result * 59 + ($startEpisode == null ? 43 : $startEpisode.hashCode());
      Object $scrapePathConfigId = this.getScrapePathConfigId();
      result = result * 59 + ($scrapePathConfigId == null ? 43 : $scrapePathConfigId.hashCode());
      Object $downloaderId = this.getDownloaderId();
      result = result * 59 + ($downloaderId == null ? 43 : $downloaderId.hashCode());
      Object $autoDownload = this.getAutoDownload();
      result = result * 59 + ($autoDownload == null ? 43 : $autoDownload.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $tmdbLatestEpisode = this.getTmdbLatestEpisode();
      result = result * 59 + ($tmdbLatestEpisode == null ? 43 : $tmdbLatestEpisode.hashCode());
      Object $lastDownloadedEpisode = this.getLastDownloadedEpisode();
      result = result * 59 + ($lastDownloadedEpisode == null ? 43 : $lastDownloadedEpisode.hashCode());
      Object $lastMatchedCount = this.getLastMatchedCount();
      result = result * 59 + ($lastMatchedCount == null ? 43 : $lastMatchedCount.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $originalTitle = this.getOriginalTitle();
      result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $year = this.getYear();
      result = result * 59 + ($year == null ? 43 : $year.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $siteId = this.getSiteId();
      result = result * 59 + ($siteId == null ? 43 : $siteId.hashCode());
      Object $state = this.getState();
      result = result * 59 + ($state == null ? 43 : $state.hashCode());
      Object $lastSearchTime = this.getLastSearchTime();
      result = result * 59 + ($lastSearchTime == null ? 43 : $lastSearchTime.hashCode());
      Object $lastDownloadTime = this.getLastDownloadTime();
      result = result * 59 + ($lastDownloadTime == null ? 43 : $lastDownloadTime.hashCode());
      Object $lastError = this.getLastError();
      return result * 59 + ($lastError == null ? 43 : $lastError.hashCode());
   }
}
