package com.una.embyhub.movie.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class MoviePtSubscribe implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long id;
   private String name;
   private String originalTitle;
   private String keyword;
   private String type;
   private String year;
   private Long tmdbId;
   private String posterPath;
   private String backdropPath;
   private Integer season;
   private Integer startEpisode;
   private List<Long> siteId;
   private Long scrapePathConfigId;
   private String scrapePathConfigName;
   private Long downloaderId;
   private String downloaderName;
   private Integer autoDownload;
   private Integer enabled;
   private String state;
   private Integer tmdbLatestEpisode;
   private Integer lastDownloadedEpisode;
   private Integer subscribedSeasonCount;
   private String subscribedSeasonCode;
   private Integer latestUpdatedSeason;
   private Integer latestUpdatedEpisode;
   private String latestUpdatedSeasonEpisode;
   private String latestAiredSeasonEpisode;
   private String updateProgress;
   private Integer lastMatchedCount;
   private Date lastSearchTime;
   private Date lastDownloadTime;
   private String lastError;
   private Date createDatetime;
   private Date updateDatetime;

   @Generated
   MoviePtSubscribe(
      final Long id,
      final String name,
      final String originalTitle,
      final String keyword,
      final String type,
      final String year,
      final Long tmdbId,
      final String posterPath,
      final String backdropPath,
      final Integer season,
      final Integer startEpisode,
      final List<Long> siteId,
      final Long scrapePathConfigId,
      final String scrapePathConfigName,
      final Long downloaderId,
      final String downloaderName,
      final Integer autoDownload,
      final Integer enabled,
      final String state,
      final Integer tmdbLatestEpisode,
      final Integer lastDownloadedEpisode,
      final Integer subscribedSeasonCount,
      final String subscribedSeasonCode,
      final Integer latestUpdatedSeason,
      final Integer latestUpdatedEpisode,
      final String latestUpdatedSeasonEpisode,
      final String latestAiredSeasonEpisode,
      final String updateProgress,
      final Integer lastMatchedCount,
      final Date lastSearchTime,
      final Date lastDownloadTime,
      final String lastError,
      final Date createDatetime,
      final Date updateDatetime
   ) {
      this.id = id;
      this.name = name;
      this.originalTitle = originalTitle;
      this.keyword = keyword;
      this.type = type;
      this.year = year;
      this.tmdbId = tmdbId;
      this.posterPath = posterPath;
      this.backdropPath = backdropPath;
      this.season = season;
      this.startEpisode = startEpisode;
      this.siteId = siteId;
      this.scrapePathConfigId = scrapePathConfigId;
      this.scrapePathConfigName = scrapePathConfigName;
      this.downloaderId = downloaderId;
      this.downloaderName = downloaderName;
      this.autoDownload = autoDownload;
      this.enabled = enabled;
      this.state = state;
      this.tmdbLatestEpisode = tmdbLatestEpisode;
      this.lastDownloadedEpisode = lastDownloadedEpisode;
      this.subscribedSeasonCount = subscribedSeasonCount;
      this.subscribedSeasonCode = subscribedSeasonCode;
      this.latestUpdatedSeason = latestUpdatedSeason;
      this.latestUpdatedEpisode = latestUpdatedEpisode;
      this.latestUpdatedSeasonEpisode = latestUpdatedSeasonEpisode;
      this.latestAiredSeasonEpisode = latestAiredSeasonEpisode;
      this.updateProgress = updateProgress;
      this.lastMatchedCount = lastMatchedCount;
      this.lastSearchTime = lastSearchTime;
      this.lastDownloadTime = lastDownloadTime;
      this.lastError = lastError;
      this.createDatetime = createDatetime;
      this.updateDatetime = updateDatetime;
   }

   @Generated
   public static MoviePtSubscribe.MoviePtSubscribeBuilder builder() {
      return new MoviePtSubscribe.MoviePtSubscribeBuilder();
   }

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
   public List<Long> getSiteId() {
      return this.siteId;
   }

   @Generated
   public Long getScrapePathConfigId() {
      return this.scrapePathConfigId;
   }

   @Generated
   public String getScrapePathConfigName() {
      return this.scrapePathConfigName;
   }

   @Generated
   public Long getDownloaderId() {
      return this.downloaderId;
   }

   @Generated
   public String getDownloaderName() {
      return this.downloaderName;
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
   public Integer getSubscribedSeasonCount() {
      return this.subscribedSeasonCount;
   }

   @Generated
   public String getSubscribedSeasonCode() {
      return this.subscribedSeasonCode;
   }

   @Generated
   public Integer getLatestUpdatedSeason() {
      return this.latestUpdatedSeason;
   }

   @Generated
   public Integer getLatestUpdatedEpisode() {
      return this.latestUpdatedEpisode;
   }

   @Generated
   public String getLatestUpdatedSeasonEpisode() {
      return this.latestUpdatedSeasonEpisode;
   }

   @Generated
   public String getLatestAiredSeasonEpisode() {
      return this.latestAiredSeasonEpisode;
   }

   @Generated
   public String getUpdateProgress() {
      return this.updateProgress;
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
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
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
   public void setSiteId(final List<Long> siteId) {
      this.siteId = siteId;
   }

   @Generated
   public void setScrapePathConfigId(final Long scrapePathConfigId) {
      this.scrapePathConfigId = scrapePathConfigId;
   }

   @Generated
   public void setScrapePathConfigName(final String scrapePathConfigName) {
      this.scrapePathConfigName = scrapePathConfigName;
   }

   @Generated
   public void setDownloaderId(final Long downloaderId) {
      this.downloaderId = downloaderId;
   }

   @Generated
   public void setDownloaderName(final String downloaderName) {
      this.downloaderName = downloaderName;
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
   public void setSubscribedSeasonCount(final Integer subscribedSeasonCount) {
      this.subscribedSeasonCount = subscribedSeasonCount;
   }

   @Generated
   public void setSubscribedSeasonCode(final String subscribedSeasonCode) {
      this.subscribedSeasonCode = subscribedSeasonCode;
   }

   @Generated
   public void setLatestUpdatedSeason(final Integer latestUpdatedSeason) {
      this.latestUpdatedSeason = latestUpdatedSeason;
   }

   @Generated
   public void setLatestUpdatedEpisode(final Integer latestUpdatedEpisode) {
      this.latestUpdatedEpisode = latestUpdatedEpisode;
   }

   @Generated
   public void setLatestUpdatedSeasonEpisode(final String latestUpdatedSeasonEpisode) {
      this.latestUpdatedSeasonEpisode = latestUpdatedSeasonEpisode;
   }

   @Generated
   public void setLatestAiredSeasonEpisode(final String latestAiredSeasonEpisode) {
      this.latestAiredSeasonEpisode = latestAiredSeasonEpisode;
   }

   @Generated
   public void setUpdateProgress(final String updateProgress) {
      this.updateProgress = updateProgress;
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
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSubscribe other)) {
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
                                       Object this$subscribedSeasonCount = this.getSubscribedSeasonCount();
                                       Object other$subscribedSeasonCount = other.getSubscribedSeasonCount();
                                       if (this$subscribedSeasonCount == null
                                          ? other$subscribedSeasonCount == null
                                          : this$subscribedSeasonCount.equals(other$subscribedSeasonCount)) {
                                          Object this$latestUpdatedSeason = this.getLatestUpdatedSeason();
                                          Object other$latestUpdatedSeason = other.getLatestUpdatedSeason();
                                          if (this$latestUpdatedSeason == null
                                             ? other$latestUpdatedSeason == null
                                             : this$latestUpdatedSeason.equals(other$latestUpdatedSeason)) {
                                             Object this$latestUpdatedEpisode = this.getLatestUpdatedEpisode();
                                             Object other$latestUpdatedEpisode = other.getLatestUpdatedEpisode();
                                             if (this$latestUpdatedEpisode == null
                                                ? other$latestUpdatedEpisode == null
                                                : this$latestUpdatedEpisode.equals(other$latestUpdatedEpisode)) {
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
                                                      if (this$originalTitle == null
                                                         ? other$originalTitle == null
                                                         : this$originalTitle.equals(other$originalTitle)) {
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
                                                                  if (this$posterPath == null
                                                                     ? other$posterPath == null
                                                                     : this$posterPath.equals(other$posterPath)) {
                                                                     Object this$backdropPath = this.getBackdropPath();
                                                                     Object other$backdropPath = other.getBackdropPath();
                                                                     if (this$backdropPath == null
                                                                        ? other$backdropPath == null
                                                                        : this$backdropPath.equals(other$backdropPath)) {
                                                                        Object this$siteId = this.getSiteId();
                                                                        Object other$siteId = other.getSiteId();
                                                                        if (this$siteId == null ? other$siteId == null : this$siteId.equals(other$siteId)) {
                                                                           Object this$scrapePathConfigName = this.getScrapePathConfigName();
                                                                           Object other$scrapePathConfigName = other.getScrapePathConfigName();
                                                                           if (this$scrapePathConfigName == null
                                                                              ? other$scrapePathConfigName == null
                                                                              : this$scrapePathConfigName.equals(other$scrapePathConfigName)) {
                                                                              Object this$downloaderName = this.getDownloaderName();
                                                                              Object other$downloaderName = other.getDownloaderName();
                                                                              if (this$downloaderName == null
                                                                                 ? other$downloaderName == null
                                                                                 : this$downloaderName.equals(other$downloaderName)) {
                                                                                 Object this$state = this.getState();
                                                                                 Object other$state = other.getState();
                                                                                 if (this$state == null ? other$state == null : this$state.equals(other$state)) {
                                                                                    Object this$subscribedSeasonCode = this.getSubscribedSeasonCode();
                                                                                    Object other$subscribedSeasonCode = other.getSubscribedSeasonCode();
                                                                                    if (this$subscribedSeasonCode == null
                                                                                       ? other$subscribedSeasonCode == null
                                                                                       : this$subscribedSeasonCode.equals(other$subscribedSeasonCode)) {
                                                                                       Object this$latestUpdatedSeasonEpisode = this.getLatestUpdatedSeasonEpisode();
                                                                                       Object other$latestUpdatedSeasonEpisode = other.getLatestUpdatedSeasonEpisode();
                                                                                       if (this$latestUpdatedSeasonEpisode == null
                                                                                          ? other$latestUpdatedSeasonEpisode == null
                                                                                          : this$latestUpdatedSeasonEpisode.equals(
                                                                                             other$latestUpdatedSeasonEpisode
                                                                                          )) {
                                                                                          Object this$latestAiredSeasonEpisode = this.getLatestAiredSeasonEpisode();
                                                                                          Object other$latestAiredSeasonEpisode = other.getLatestAiredSeasonEpisode();
                                                                                          if (this$latestAiredSeasonEpisode == null
                                                                                             ? other$latestAiredSeasonEpisode == null
                                                                                             : this$latestAiredSeasonEpisode.equals(
                                                                                                other$latestAiredSeasonEpisode
                                                                                             )) {
                                                                                             Object this$updateProgress = this.getUpdateProgress();
                                                                                             Object other$updateProgress = other.getUpdateProgress();
                                                                                             if (this$updateProgress == null
                                                                                                ? other$updateProgress == null
                                                                                                : this$updateProgress.equals(other$updateProgress)) {
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
                                                                                                      if (this$lastError == null
                                                                                                         ? other$lastError == null
                                                                                                         : this$lastError.equals(other$lastError)) {
                                                                                                         Object this$createDatetime = this.getCreateDatetime();
                                                                                                         Object other$createDatetime = other.getCreateDatetime();
                                                                                                         if (this$createDatetime == null
                                                                                                            ? other$createDatetime == null
                                                                                                            : this$createDatetime.equals(other$createDatetime)) {
                                                                                                            Object this$updateDatetime = this.getUpdateDatetime();
                                                                                                            Object other$updateDatetime = other.getUpdateDatetime();
                                                                                                            return this$updateDatetime == null
                                                                                                               ? other$updateDatetime == null
                                                                                                               : this$updateDatetime.equals(
                                                                                                                  other$updateDatetime
                                                                                                               );
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
      return other instanceof MoviePtSubscribe;
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
      Object $subscribedSeasonCount = this.getSubscribedSeasonCount();
      result = result * 59 + ($subscribedSeasonCount == null ? 43 : $subscribedSeasonCount.hashCode());
      Object $latestUpdatedSeason = this.getLatestUpdatedSeason();
      result = result * 59 + ($latestUpdatedSeason == null ? 43 : $latestUpdatedSeason.hashCode());
      Object $latestUpdatedEpisode = this.getLatestUpdatedEpisode();
      result = result * 59 + ($latestUpdatedEpisode == null ? 43 : $latestUpdatedEpisode.hashCode());
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
      Object $scrapePathConfigName = this.getScrapePathConfigName();
      result = result * 59 + ($scrapePathConfigName == null ? 43 : $scrapePathConfigName.hashCode());
      Object $downloaderName = this.getDownloaderName();
      result = result * 59 + ($downloaderName == null ? 43 : $downloaderName.hashCode());
      Object $state = this.getState();
      result = result * 59 + ($state == null ? 43 : $state.hashCode());
      Object $subscribedSeasonCode = this.getSubscribedSeasonCode();
      result = result * 59 + ($subscribedSeasonCode == null ? 43 : $subscribedSeasonCode.hashCode());
      Object $latestUpdatedSeasonEpisode = this.getLatestUpdatedSeasonEpisode();
      result = result * 59 + ($latestUpdatedSeasonEpisode == null ? 43 : $latestUpdatedSeasonEpisode.hashCode());
      Object $latestAiredSeasonEpisode = this.getLatestAiredSeasonEpisode();
      result = result * 59 + ($latestAiredSeasonEpisode == null ? 43 : $latestAiredSeasonEpisode.hashCode());
      Object $updateProgress = this.getUpdateProgress();
      result = result * 59 + ($updateProgress == null ? 43 : $updateProgress.hashCode());
      Object $lastSearchTime = this.getLastSearchTime();
      result = result * 59 + ($lastSearchTime == null ? 43 : $lastSearchTime.hashCode());
      Object $lastDownloadTime = this.getLastDownloadTime();
      result = result * 59 + ($lastDownloadTime == null ? 43 : $lastDownloadTime.hashCode());
      Object $lastError = this.getLastError();
      result = result * 59 + ($lastError == null ? 43 : $lastError.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSubscribe(id="
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
         + ", scrapePathConfigName="
         + this.getScrapePathConfigName()
         + ", downloaderId="
         + this.getDownloaderId()
         + ", downloaderName="
         + this.getDownloaderName()
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
         + ", subscribedSeasonCount="
         + this.getSubscribedSeasonCount()
         + ", subscribedSeasonCode="
         + this.getSubscribedSeasonCode()
         + ", latestUpdatedSeason="
         + this.getLatestUpdatedSeason()
         + ", latestUpdatedEpisode="
         + this.getLatestUpdatedEpisode()
         + ", latestUpdatedSeasonEpisode="
         + this.getLatestUpdatedSeasonEpisode()
         + ", latestAiredSeasonEpisode="
         + this.getLatestAiredSeasonEpisode()
         + ", updateProgress="
         + this.getUpdateProgress()
         + ", lastMatchedCount="
         + this.getLastMatchedCount()
         + ", lastSearchTime="
         + this.getLastSearchTime()
         + ", lastDownloadTime="
         + this.getLastDownloadTime()
         + ", lastError="
         + this.getLastError()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }

   @Generated
   public static class MoviePtSubscribeBuilder {
      @Generated
      private Long id;
      @Generated
      private String name;
      @Generated
      private String originalTitle;
      @Generated
      private String keyword;
      @Generated
      private String type;
      @Generated
      private String year;
      @Generated
      private Long tmdbId;
      @Generated
      private String posterPath;
      @Generated
      private String backdropPath;
      @Generated
      private Integer season;
      @Generated
      private Integer startEpisode;
      @Generated
      private List<Long> siteId;
      @Generated
      private Long scrapePathConfigId;
      @Generated
      private String scrapePathConfigName;
      @Generated
      private Long downloaderId;
      @Generated
      private String downloaderName;
      @Generated
      private Integer autoDownload;
      @Generated
      private Integer enabled;
      @Generated
      private String state;
      @Generated
      private Integer tmdbLatestEpisode;
      @Generated
      private Integer lastDownloadedEpisode;
      @Generated
      private Integer subscribedSeasonCount;
      @Generated
      private String subscribedSeasonCode;
      @Generated
      private Integer latestUpdatedSeason;
      @Generated
      private Integer latestUpdatedEpisode;
      @Generated
      private String latestUpdatedSeasonEpisode;
      @Generated
      private String latestAiredSeasonEpisode;
      @Generated
      private String updateProgress;
      @Generated
      private Integer lastMatchedCount;
      @Generated
      private Date lastSearchTime;
      @Generated
      private Date lastDownloadTime;
      @Generated
      private String lastError;
      @Generated
      private Date createDatetime;
      @Generated
      private Date updateDatetime;

      @Generated
      MoviePtSubscribeBuilder() {
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder name(final String name) {
         this.name = name;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder originalTitle(final String originalTitle) {
         this.originalTitle = originalTitle;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder keyword(final String keyword) {
         this.keyword = keyword;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder type(final String type) {
         this.type = type;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder year(final String year) {
         this.year = year;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder tmdbId(final Long tmdbId) {
         this.tmdbId = tmdbId;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder posterPath(final String posterPath) {
         this.posterPath = posterPath;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder backdropPath(final String backdropPath) {
         this.backdropPath = backdropPath;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder season(final Integer season) {
         this.season = season;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder startEpisode(final Integer startEpisode) {
         this.startEpisode = startEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder siteId(final List<Long> siteId) {
         this.siteId = siteId;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder scrapePathConfigId(final Long scrapePathConfigId) {
         this.scrapePathConfigId = scrapePathConfigId;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder scrapePathConfigName(final String scrapePathConfigName) {
         this.scrapePathConfigName = scrapePathConfigName;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder downloaderId(final Long downloaderId) {
         this.downloaderId = downloaderId;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder downloaderName(final String downloaderName) {
         this.downloaderName = downloaderName;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder autoDownload(final Integer autoDownload) {
         this.autoDownload = autoDownload;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder enabled(final Integer enabled) {
         this.enabled = enabled;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder state(final String state) {
         this.state = state;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder tmdbLatestEpisode(final Integer tmdbLatestEpisode) {
         this.tmdbLatestEpisode = tmdbLatestEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder lastDownloadedEpisode(final Integer lastDownloadedEpisode) {
         this.lastDownloadedEpisode = lastDownloadedEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder subscribedSeasonCount(final Integer subscribedSeasonCount) {
         this.subscribedSeasonCount = subscribedSeasonCount;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder subscribedSeasonCode(final String subscribedSeasonCode) {
         this.subscribedSeasonCode = subscribedSeasonCode;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder latestUpdatedSeason(final Integer latestUpdatedSeason) {
         this.latestUpdatedSeason = latestUpdatedSeason;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder latestUpdatedEpisode(final Integer latestUpdatedEpisode) {
         this.latestUpdatedEpisode = latestUpdatedEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder latestUpdatedSeasonEpisode(final String latestUpdatedSeasonEpisode) {
         this.latestUpdatedSeasonEpisode = latestUpdatedSeasonEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder latestAiredSeasonEpisode(final String latestAiredSeasonEpisode) {
         this.latestAiredSeasonEpisode = latestAiredSeasonEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder updateProgress(final String updateProgress) {
         this.updateProgress = updateProgress;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder lastMatchedCount(final Integer lastMatchedCount) {
         this.lastMatchedCount = lastMatchedCount;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder lastSearchTime(final Date lastSearchTime) {
         this.lastSearchTime = lastSearchTime;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder lastDownloadTime(final Date lastDownloadTime) {
         this.lastDownloadTime = lastDownloadTime;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder lastError(final String lastError) {
         this.lastError = lastError;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder createDatetime(final Date createDatetime) {
         this.createDatetime = createDatetime;
         return this;
      }

      @Generated
      public MoviePtSubscribe.MoviePtSubscribeBuilder updateDatetime(final Date updateDatetime) {
         this.updateDatetime = updateDatetime;
         return this;
      }

      @Generated
      public MoviePtSubscribe build() {
         return new MoviePtSubscribe(
            this.id,
            this.name,
            this.originalTitle,
            this.keyword,
            this.type,
            this.year,
            this.tmdbId,
            this.posterPath,
            this.backdropPath,
            this.season,
            this.startEpisode,
            this.siteId,
            this.scrapePathConfigId,
            this.scrapePathConfigName,
            this.downloaderId,
            this.downloaderName,
            this.autoDownload,
            this.enabled,
            this.state,
            this.tmdbLatestEpisode,
            this.lastDownloadedEpisode,
            this.subscribedSeasonCount,
            this.subscribedSeasonCode,
            this.latestUpdatedSeason,
            this.latestUpdatedEpisode,
            this.latestUpdatedSeasonEpisode,
            this.latestAiredSeasonEpisode,
            this.updateProgress,
            this.lastMatchedCount,
            this.lastSearchTime,
            this.lastDownloadTime,
            this.lastError,
            this.createDatetime,
            this.updateDatetime
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePtSubscribe.MoviePtSubscribeBuilder(id="
            + this.id
            + ", name="
            + this.name
            + ", originalTitle="
            + this.originalTitle
            + ", keyword="
            + this.keyword
            + ", type="
            + this.type
            + ", year="
            + this.year
            + ", tmdbId="
            + this.tmdbId
            + ", posterPath="
            + this.posterPath
            + ", backdropPath="
            + this.backdropPath
            + ", season="
            + this.season
            + ", startEpisode="
            + this.startEpisode
            + ", siteId="
            + this.siteId
            + ", scrapePathConfigId="
            + this.scrapePathConfigId
            + ", scrapePathConfigName="
            + this.scrapePathConfigName
            + ", downloaderId="
            + this.downloaderId
            + ", downloaderName="
            + this.downloaderName
            + ", autoDownload="
            + this.autoDownload
            + ", enabled="
            + this.enabled
            + ", state="
            + this.state
            + ", tmdbLatestEpisode="
            + this.tmdbLatestEpisode
            + ", lastDownloadedEpisode="
            + this.lastDownloadedEpisode
            + ", subscribedSeasonCount="
            + this.subscribedSeasonCount
            + ", subscribedSeasonCode="
            + this.subscribedSeasonCode
            + ", latestUpdatedSeason="
            + this.latestUpdatedSeason
            + ", latestUpdatedEpisode="
            + this.latestUpdatedEpisode
            + ", latestUpdatedSeasonEpisode="
            + this.latestUpdatedSeasonEpisode
            + ", latestAiredSeasonEpisode="
            + this.latestAiredSeasonEpisode
            + ", updateProgress="
            + this.updateProgress
            + ", lastMatchedCount="
            + this.lastMatchedCount
            + ", lastSearchTime="
            + this.lastSearchTime
            + ", lastDownloadTime="
            + this.lastDownloadTime
            + ", lastError="
            + this.lastError
            + ", createDatetime="
            + this.createDatetime
            + ", updateDatetime="
            + this.updateDatetime
            + ")";
      }
   }
}
