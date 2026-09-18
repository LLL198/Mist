package com.una.embyhub.movie.model;

import java.util.Date;
import java.util.List;
import lombok.Generated;

public class MovieDownloadRecordWithDetailsResponse {
   private Long id;
   private Long subscribeId;
   private Long downloaderId;
   private String downloaderName;
   private Long tmdbId;
   private String mediaType;
   private String movieName;
   private String movieYear;
   private String title;
   private String posterUrl;
   private String coverUrl;
   private String qbDownloadPath;
   private String hardlinkPath;
   private String qbTag;
   private String qbHash;
   private String qbTorrentName;
   private String status;
   private String statusName;
   private String sourceFilePath;
   private String linkFilePath;
   private String episodeCodes;
   private String episodeSeqs;
   private Date createDatetime;
   private Date updateDatetime;
   private Integer hardlinkMode;
   private String hardlinkModeName;
   private String size;
   private String errorMessage;
   private List<MovieDownloadRecordFileResponse> details;

   @Generated
   MovieDownloadRecordWithDetailsResponse(
      final Long id,
      final Long subscribeId,
      final Long downloaderId,
      final String downloaderName,
      final Long tmdbId,
      final String mediaType,
      final String movieName,
      final String movieYear,
      final String title,
      final String posterUrl,
      final String coverUrl,
      final String qbDownloadPath,
      final String hardlinkPath,
      final String qbTag,
      final String qbHash,
      final String qbTorrentName,
      final String status,
      final String statusName,
      final String sourceFilePath,
      final String linkFilePath,
      final String episodeCodes,
      final String episodeSeqs,
      final Date createDatetime,
      final Date updateDatetime,
      final Integer hardlinkMode,
      final String hardlinkModeName,
      final String size,
      final String errorMessage,
      final List<MovieDownloadRecordFileResponse> details
   ) {
      this.id = id;
      this.subscribeId = subscribeId;
      this.downloaderId = downloaderId;
      this.downloaderName = downloaderName;
      this.tmdbId = tmdbId;
      this.mediaType = mediaType;
      this.movieName = movieName;
      this.movieYear = movieYear;
      this.title = title;
      this.posterUrl = posterUrl;
      this.coverUrl = coverUrl;
      this.qbDownloadPath = qbDownloadPath;
      this.hardlinkPath = hardlinkPath;
      this.qbTag = qbTag;
      this.qbHash = qbHash;
      this.qbTorrentName = qbTorrentName;
      this.status = status;
      this.statusName = statusName;
      this.sourceFilePath = sourceFilePath;
      this.linkFilePath = linkFilePath;
      this.episodeCodes = episodeCodes;
      this.episodeSeqs = episodeSeqs;
      this.createDatetime = createDatetime;
      this.updateDatetime = updateDatetime;
      this.hardlinkMode = hardlinkMode;
      this.hardlinkModeName = hardlinkModeName;
      this.size = size;
      this.errorMessage = errorMessage;
      this.details = details;
   }

   @Generated
   public static MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder builder() {
      return new MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getSubscribeId() {
      return this.subscribeId;
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
   public Long getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public String getMovieName() {
      return this.movieName;
   }

   @Generated
   public String getMovieYear() {
      return this.movieYear;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getPosterUrl() {
      return this.posterUrl;
   }

   @Generated
   public String getCoverUrl() {
      return this.coverUrl;
   }

   @Generated
   public String getQbDownloadPath() {
      return this.qbDownloadPath;
   }

   @Generated
   public String getHardlinkPath() {
      return this.hardlinkPath;
   }

   @Generated
   public String getQbTag() {
      return this.qbTag;
   }

   @Generated
   public String getQbHash() {
      return this.qbHash;
   }

   @Generated
   public String getQbTorrentName() {
      return this.qbTorrentName;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public String getStatusName() {
      return this.statusName;
   }

   @Generated
   public String getSourceFilePath() {
      return this.sourceFilePath;
   }

   @Generated
   public String getLinkFilePath() {
      return this.linkFilePath;
   }

   @Generated
   public String getEpisodeCodes() {
      return this.episodeCodes;
   }

   @Generated
   public String getEpisodeSeqs() {
      return this.episodeSeqs;
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
   public Integer getHardlinkMode() {
      return this.hardlinkMode;
   }

   @Generated
   public String getHardlinkModeName() {
      return this.hardlinkModeName;
   }

   @Generated
   public String getSize() {
      return this.size;
   }

   @Generated
   public String getErrorMessage() {
      return this.errorMessage;
   }

   @Generated
   public List<MovieDownloadRecordFileResponse> getDetails() {
      return this.details;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setSubscribeId(final Long subscribeId) {
      this.subscribeId = subscribeId;
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
   public void setTmdbId(final Long tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setMovieName(final String movieName) {
      this.movieName = movieName;
   }

   @Generated
   public void setMovieYear(final String movieYear) {
      this.movieYear = movieYear;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setPosterUrl(final String posterUrl) {
      this.posterUrl = posterUrl;
   }

   @Generated
   public void setCoverUrl(final String coverUrl) {
      this.coverUrl = coverUrl;
   }

   @Generated
   public void setQbDownloadPath(final String qbDownloadPath) {
      this.qbDownloadPath = qbDownloadPath;
   }

   @Generated
   public void setHardlinkPath(final String hardlinkPath) {
      this.hardlinkPath = hardlinkPath;
   }

   @Generated
   public void setQbTag(final String qbTag) {
      this.qbTag = qbTag;
   }

   @Generated
   public void setQbHash(final String qbHash) {
      this.qbHash = qbHash;
   }

   @Generated
   public void setQbTorrentName(final String qbTorrentName) {
      this.qbTorrentName = qbTorrentName;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setStatusName(final String statusName) {
      this.statusName = statusName;
   }

   @Generated
   public void setSourceFilePath(final String sourceFilePath) {
      this.sourceFilePath = sourceFilePath;
   }

   @Generated
   public void setLinkFilePath(final String linkFilePath) {
      this.linkFilePath = linkFilePath;
   }

   @Generated
   public void setEpisodeCodes(final String episodeCodes) {
      this.episodeCodes = episodeCodes;
   }

   @Generated
   public void setEpisodeSeqs(final String episodeSeqs) {
      this.episodeSeqs = episodeSeqs;
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
   public void setHardlinkMode(final Integer hardlinkMode) {
      this.hardlinkMode = hardlinkMode;
   }

   @Generated
   public void setHardlinkModeName(final String hardlinkModeName) {
      this.hardlinkModeName = hardlinkModeName;
   }

   @Generated
   public void setSize(final String size) {
      this.size = size;
   }

   @Generated
   public void setErrorMessage(final String errorMessage) {
      this.errorMessage = errorMessage;
   }

   @Generated
   public void setDetails(final List<MovieDownloadRecordFileResponse> details) {
      this.details = details;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieDownloadRecordWithDetailsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$subscribeId = this.getSubscribeId();
            Object other$subscribeId = other.getSubscribeId();
            if (this$subscribeId == null ? other$subscribeId == null : this$subscribeId.equals(other$subscribeId)) {
               Object this$downloaderId = this.getDownloaderId();
               Object other$downloaderId = other.getDownloaderId();
               if (this$downloaderId == null ? other$downloaderId == null : this$downloaderId.equals(other$downloaderId)) {
                  Object this$tmdbId = this.getTmdbId();
                  Object other$tmdbId = other.getTmdbId();
                  if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
                     Object this$hardlinkMode = this.getHardlinkMode();
                     Object other$hardlinkMode = other.getHardlinkMode();
                     if (this$hardlinkMode == null ? other$hardlinkMode == null : this$hardlinkMode.equals(other$hardlinkMode)) {
                        Object this$downloaderName = this.getDownloaderName();
                        Object other$downloaderName = other.getDownloaderName();
                        if (this$downloaderName == null ? other$downloaderName == null : this$downloaderName.equals(other$downloaderName)) {
                           Object this$mediaType = this.getMediaType();
                           Object other$mediaType = other.getMediaType();
                           if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                              Object this$movieName = this.getMovieName();
                              Object other$movieName = other.getMovieName();
                              if (this$movieName == null ? other$movieName == null : this$movieName.equals(other$movieName)) {
                                 Object this$movieYear = this.getMovieYear();
                                 Object other$movieYear = other.getMovieYear();
                                 if (this$movieYear == null ? other$movieYear == null : this$movieYear.equals(other$movieYear)) {
                                    Object this$title = this.getTitle();
                                    Object other$title = other.getTitle();
                                    if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                       Object this$posterUrl = this.getPosterUrl();
                                       Object other$posterUrl = other.getPosterUrl();
                                       if (this$posterUrl == null ? other$posterUrl == null : this$posterUrl.equals(other$posterUrl)) {
                                          Object this$coverUrl = this.getCoverUrl();
                                          Object other$coverUrl = other.getCoverUrl();
                                          if (this$coverUrl == null ? other$coverUrl == null : this$coverUrl.equals(other$coverUrl)) {
                                             Object this$qbDownloadPath = this.getQbDownloadPath();
                                             Object other$qbDownloadPath = other.getQbDownloadPath();
                                             if (this$qbDownloadPath == null ? other$qbDownloadPath == null : this$qbDownloadPath.equals(other$qbDownloadPath)) {
                                                Object this$hardlinkPath = this.getHardlinkPath();
                                                Object other$hardlinkPath = other.getHardlinkPath();
                                                if (this$hardlinkPath == null ? other$hardlinkPath == null : this$hardlinkPath.equals(other$hardlinkPath)) {
                                                   Object this$qbTag = this.getQbTag();
                                                   Object other$qbTag = other.getQbTag();
                                                   if (this$qbTag == null ? other$qbTag == null : this$qbTag.equals(other$qbTag)) {
                                                      Object this$qbHash = this.getQbHash();
                                                      Object other$qbHash = other.getQbHash();
                                                      if (this$qbHash == null ? other$qbHash == null : this$qbHash.equals(other$qbHash)) {
                                                         Object this$qbTorrentName = this.getQbTorrentName();
                                                         Object other$qbTorrentName = other.getQbTorrentName();
                                                         if (this$qbTorrentName == null
                                                            ? other$qbTorrentName == null
                                                            : this$qbTorrentName.equals(other$qbTorrentName)) {
                                                            Object this$status = this.getStatus();
                                                            Object other$status = other.getStatus();
                                                            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                               Object this$statusName = this.getStatusName();
                                                               Object other$statusName = other.getStatusName();
                                                               if (this$statusName == null
                                                                  ? other$statusName == null
                                                                  : this$statusName.equals(other$statusName)) {
                                                                  Object this$sourceFilePath = this.getSourceFilePath();
                                                                  Object other$sourceFilePath = other.getSourceFilePath();
                                                                  if (this$sourceFilePath == null
                                                                     ? other$sourceFilePath == null
                                                                     : this$sourceFilePath.equals(other$sourceFilePath)) {
                                                                     Object this$linkFilePath = this.getLinkFilePath();
                                                                     Object other$linkFilePath = other.getLinkFilePath();
                                                                     if (this$linkFilePath == null
                                                                        ? other$linkFilePath == null
                                                                        : this$linkFilePath.equals(other$linkFilePath)) {
                                                                        Object this$episodeCodes = this.getEpisodeCodes();
                                                                        Object other$episodeCodes = other.getEpisodeCodes();
                                                                        if (this$episodeCodes == null
                                                                           ? other$episodeCodes == null
                                                                           : this$episodeCodes.equals(other$episodeCodes)) {
                                                                           Object this$episodeSeqs = this.getEpisodeSeqs();
                                                                           Object other$episodeSeqs = other.getEpisodeSeqs();
                                                                           if (this$episodeSeqs == null
                                                                              ? other$episodeSeqs == null
                                                                              : this$episodeSeqs.equals(other$episodeSeqs)) {
                                                                              Object this$createDatetime = this.getCreateDatetime();
                                                                              Object other$createDatetime = other.getCreateDatetime();
                                                                              if (this$createDatetime == null
                                                                                 ? other$createDatetime == null
                                                                                 : this$createDatetime.equals(other$createDatetime)) {
                                                                                 Object this$updateDatetime = this.getUpdateDatetime();
                                                                                 Object other$updateDatetime = other.getUpdateDatetime();
                                                                                 if (this$updateDatetime == null
                                                                                    ? other$updateDatetime == null
                                                                                    : this$updateDatetime.equals(other$updateDatetime)) {
                                                                                    Object this$hardlinkModeName = this.getHardlinkModeName();
                                                                                    Object other$hardlinkModeName = other.getHardlinkModeName();
                                                                                    if (this$hardlinkModeName == null
                                                                                       ? other$hardlinkModeName == null
                                                                                       : this$hardlinkModeName.equals(other$hardlinkModeName)) {
                                                                                       Object this$size = this.getSize();
                                                                                       Object other$size = other.getSize();
                                                                                       if (this$size == null
                                                                                          ? other$size == null
                                                                                          : this$size.equals(other$size)) {
                                                                                          Object this$errorMessage = this.getErrorMessage();
                                                                                          Object other$errorMessage = other.getErrorMessage();
                                                                                          if (this$errorMessage == null
                                                                                             ? other$errorMessage == null
                                                                                             : this$errorMessage.equals(other$errorMessage)) {
                                                                                             Object this$details = this.getDetails();
                                                                                             Object other$details = other.getDetails();
                                                                                             return this$details == null
                                                                                                ? other$details == null
                                                                                                : this$details.equals(other$details);
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
      return other instanceof MovieDownloadRecordWithDetailsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $subscribeId = this.getSubscribeId();
      result = result * 59 + ($subscribeId == null ? 43 : $subscribeId.hashCode());
      Object $downloaderId = this.getDownloaderId();
      result = result * 59 + ($downloaderId == null ? 43 : $downloaderId.hashCode());
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $hardlinkMode = this.getHardlinkMode();
      result = result * 59 + ($hardlinkMode == null ? 43 : $hardlinkMode.hashCode());
      Object $downloaderName = this.getDownloaderName();
      result = result * 59 + ($downloaderName == null ? 43 : $downloaderName.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $movieName = this.getMovieName();
      result = result * 59 + ($movieName == null ? 43 : $movieName.hashCode());
      Object $movieYear = this.getMovieYear();
      result = result * 59 + ($movieYear == null ? 43 : $movieYear.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $posterUrl = this.getPosterUrl();
      result = result * 59 + ($posterUrl == null ? 43 : $posterUrl.hashCode());
      Object $coverUrl = this.getCoverUrl();
      result = result * 59 + ($coverUrl == null ? 43 : $coverUrl.hashCode());
      Object $qbDownloadPath = this.getQbDownloadPath();
      result = result * 59 + ($qbDownloadPath == null ? 43 : $qbDownloadPath.hashCode());
      Object $hardlinkPath = this.getHardlinkPath();
      result = result * 59 + ($hardlinkPath == null ? 43 : $hardlinkPath.hashCode());
      Object $qbTag = this.getQbTag();
      result = result * 59 + ($qbTag == null ? 43 : $qbTag.hashCode());
      Object $qbHash = this.getQbHash();
      result = result * 59 + ($qbHash == null ? 43 : $qbHash.hashCode());
      Object $qbTorrentName = this.getQbTorrentName();
      result = result * 59 + ($qbTorrentName == null ? 43 : $qbTorrentName.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $statusName = this.getStatusName();
      result = result * 59 + ($statusName == null ? 43 : $statusName.hashCode());
      Object $sourceFilePath = this.getSourceFilePath();
      result = result * 59 + ($sourceFilePath == null ? 43 : $sourceFilePath.hashCode());
      Object $linkFilePath = this.getLinkFilePath();
      result = result * 59 + ($linkFilePath == null ? 43 : $linkFilePath.hashCode());
      Object $episodeCodes = this.getEpisodeCodes();
      result = result * 59 + ($episodeCodes == null ? 43 : $episodeCodes.hashCode());
      Object $episodeSeqs = this.getEpisodeSeqs();
      result = result * 59 + ($episodeSeqs == null ? 43 : $episodeSeqs.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $hardlinkModeName = this.getHardlinkModeName();
      result = result * 59 + ($hardlinkModeName == null ? 43 : $hardlinkModeName.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
      Object $errorMessage = this.getErrorMessage();
      result = result * 59 + ($errorMessage == null ? 43 : $errorMessage.hashCode());
      Object $details = this.getDetails();
      return result * 59 + ($details == null ? 43 : $details.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieDownloadRecordWithDetailsResponse(id="
         + this.getId()
         + ", subscribeId="
         + this.getSubscribeId()
         + ", downloaderId="
         + this.getDownloaderId()
         + ", downloaderName="
         + this.getDownloaderName()
         + ", tmdbId="
         + this.getTmdbId()
         + ", mediaType="
         + this.getMediaType()
         + ", movieName="
         + this.getMovieName()
         + ", movieYear="
         + this.getMovieYear()
         + ", title="
         + this.getTitle()
         + ", posterUrl="
         + this.getPosterUrl()
         + ", coverUrl="
         + this.getCoverUrl()
         + ", qbDownloadPath="
         + this.getQbDownloadPath()
         + ", hardlinkPath="
         + this.getHardlinkPath()
         + ", qbTag="
         + this.getQbTag()
         + ", qbHash="
         + this.getQbHash()
         + ", qbTorrentName="
         + this.getQbTorrentName()
         + ", status="
         + this.getStatus()
         + ", statusName="
         + this.getStatusName()
         + ", sourceFilePath="
         + this.getSourceFilePath()
         + ", linkFilePath="
         + this.getLinkFilePath()
         + ", episodeCodes="
         + this.getEpisodeCodes()
         + ", episodeSeqs="
         + this.getEpisodeSeqs()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", hardlinkMode="
         + this.getHardlinkMode()
         + ", hardlinkModeName="
         + this.getHardlinkModeName()
         + ", size="
         + this.getSize()
         + ", errorMessage="
         + this.getErrorMessage()
         + ", details="
         + this.getDetails()
         + ")";
   }

   @Generated
   public static class MovieDownloadRecordWithDetailsResponseBuilder {
      @Generated
      private Long id;
      @Generated
      private Long subscribeId;
      @Generated
      private Long downloaderId;
      @Generated
      private String downloaderName;
      @Generated
      private Long tmdbId;
      @Generated
      private String mediaType;
      @Generated
      private String movieName;
      @Generated
      private String movieYear;
      @Generated
      private String title;
      @Generated
      private String posterUrl;
      @Generated
      private String coverUrl;
      @Generated
      private String qbDownloadPath;
      @Generated
      private String hardlinkPath;
      @Generated
      private String qbTag;
      @Generated
      private String qbHash;
      @Generated
      private String qbTorrentName;
      @Generated
      private String status;
      @Generated
      private String statusName;
      @Generated
      private String sourceFilePath;
      @Generated
      private String linkFilePath;
      @Generated
      private String episodeCodes;
      @Generated
      private String episodeSeqs;
      @Generated
      private Date createDatetime;
      @Generated
      private Date updateDatetime;
      @Generated
      private Integer hardlinkMode;
      @Generated
      private String hardlinkModeName;
      @Generated
      private String size;
      @Generated
      private String errorMessage;
      @Generated
      private List<MovieDownloadRecordFileResponse> details;

      @Generated
      MovieDownloadRecordWithDetailsResponseBuilder() {
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder subscribeId(final Long subscribeId) {
         this.subscribeId = subscribeId;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder downloaderId(final Long downloaderId) {
         this.downloaderId = downloaderId;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder downloaderName(final String downloaderName) {
         this.downloaderName = downloaderName;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder tmdbId(final Long tmdbId) {
         this.tmdbId = tmdbId;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder mediaType(final String mediaType) {
         this.mediaType = mediaType;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder movieName(final String movieName) {
         this.movieName = movieName;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder movieYear(final String movieYear) {
         this.movieYear = movieYear;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder title(final String title) {
         this.title = title;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder posterUrl(final String posterUrl) {
         this.posterUrl = posterUrl;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder coverUrl(final String coverUrl) {
         this.coverUrl = coverUrl;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder qbDownloadPath(final String qbDownloadPath) {
         this.qbDownloadPath = qbDownloadPath;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder hardlinkPath(final String hardlinkPath) {
         this.hardlinkPath = hardlinkPath;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder qbTag(final String qbTag) {
         this.qbTag = qbTag;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder qbHash(final String qbHash) {
         this.qbHash = qbHash;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder qbTorrentName(final String qbTorrentName) {
         this.qbTorrentName = qbTorrentName;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder status(final String status) {
         this.status = status;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder statusName(final String statusName) {
         this.statusName = statusName;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder sourceFilePath(final String sourceFilePath) {
         this.sourceFilePath = sourceFilePath;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder linkFilePath(final String linkFilePath) {
         this.linkFilePath = linkFilePath;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder episodeCodes(final String episodeCodes) {
         this.episodeCodes = episodeCodes;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder episodeSeqs(final String episodeSeqs) {
         this.episodeSeqs = episodeSeqs;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder createDatetime(final Date createDatetime) {
         this.createDatetime = createDatetime;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder updateDatetime(final Date updateDatetime) {
         this.updateDatetime = updateDatetime;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder hardlinkMode(final Integer hardlinkMode) {
         this.hardlinkMode = hardlinkMode;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder hardlinkModeName(final String hardlinkModeName) {
         this.hardlinkModeName = hardlinkModeName;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder size(final String size) {
         this.size = size;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder errorMessage(final String errorMessage) {
         this.errorMessage = errorMessage;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder details(final List<MovieDownloadRecordFileResponse> details) {
         this.details = details;
         return this;
      }

      @Generated
      public MovieDownloadRecordWithDetailsResponse build() {
         return new MovieDownloadRecordWithDetailsResponse(
            this.id,
            this.subscribeId,
            this.downloaderId,
            this.downloaderName,
            this.tmdbId,
            this.mediaType,
            this.movieName,
            this.movieYear,
            this.title,
            this.posterUrl,
            this.coverUrl,
            this.qbDownloadPath,
            this.hardlinkPath,
            this.qbTag,
            this.qbHash,
            this.qbTorrentName,
            this.status,
            this.statusName,
            this.sourceFilePath,
            this.linkFilePath,
            this.episodeCodes,
            this.episodeSeqs,
            this.createDatetime,
            this.updateDatetime,
            this.hardlinkMode,
            this.hardlinkModeName,
            this.size,
            this.errorMessage,
            this.details
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MovieDownloadRecordWithDetailsResponse.MovieDownloadRecordWithDetailsResponseBuilder(id="
            + this.id
            + ", subscribeId="
            + this.subscribeId
            + ", downloaderId="
            + this.downloaderId
            + ", downloaderName="
            + this.downloaderName
            + ", tmdbId="
            + this.tmdbId
            + ", mediaType="
            + this.mediaType
            + ", movieName="
            + this.movieName
            + ", movieYear="
            + this.movieYear
            + ", title="
            + this.title
            + ", posterUrl="
            + this.posterUrl
            + ", coverUrl="
            + this.coverUrl
            + ", qbDownloadPath="
            + this.qbDownloadPath
            + ", hardlinkPath="
            + this.hardlinkPath
            + ", qbTag="
            + this.qbTag
            + ", qbHash="
            + this.qbHash
            + ", qbTorrentName="
            + this.qbTorrentName
            + ", status="
            + this.status
            + ", statusName="
            + this.statusName
            + ", sourceFilePath="
            + this.sourceFilePath
            + ", linkFilePath="
            + this.linkFilePath
            + ", episodeCodes="
            + this.episodeCodes
            + ", episodeSeqs="
            + this.episodeSeqs
            + ", createDatetime="
            + this.createDatetime
            + ", updateDatetime="
            + this.updateDatetime
            + ", hardlinkMode="
            + this.hardlinkMode
            + ", hardlinkModeName="
            + this.hardlinkModeName
            + ", size="
            + this.size
            + ", errorMessage="
            + this.errorMessage
            + ", details="
            + this.details
            + ")";
      }
   }
}
