package com.una.embyhub.movie.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.una.embyhub.model.entity.BaseEntity;
import java.io.Serializable;
import lombok.Generated;

@TableName("movie_download_record")
public class MovieDownloadRecordEntity extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("tmdb_id")
   private Long tmdbId;
   @TableField("media_type")
   private String mediaType;
   @TableField("subscribe_id")
   private Long subscribeId;
   @TableField("downloader_id")
   private Long downloaderId;
   @TableField("site_id")
   private Long siteId;
   @TableField("movie_name")
   private String movieName;
   @TableField("movie_year")
   private String movieYear;
   @TableField("title")
   private String title;
   @TableField("poster_url")
   private String posterUrl;
   @TableField("cover_url")
   private String coverUrl;
   @TableField("qb_download_path")
   private String qbDownloadPath;
   @TableField("hardlink_path")
   private String hardlinkPath;
   @TableField("qb_tag")
   private String qbTag;
   @TableField("qb_hash")
   private String qbHash;
   @TableField("qb_torrent_name")
   private String qbTorrentName;
   @TableField("status")
   private String status;
   @TableField("source_file_path")
   private String sourceFilePath;
   @TableField("link_file_path")
   private String linkFilePath;
   @TableField("episode_codes")
   private String episodeCodes;
   @TableField("episode_seqs")
   private String episodeSeqs;
   @TableField("overwrite")
   private Integer overwrite;
   @TableField("coexist")
   private Integer coexist;
   @TableField("quality_priority")
   private Integer qualityPriority;
   @TableField("size_priority")
   private Integer sizePriority;
   @TableField("hardlink_mode")
   private Integer hardlinkMode;
   @TableField("size")
   private String size;
   @TableField(
      value = "error_message",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String errorMessage;

   @Generated
   public Long getId() {
      return this.id;
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
   public Long getSubscribeId() {
      return this.subscribeId;
   }

   @Generated
   public Long getDownloaderId() {
      return this.downloaderId;
   }

   @Generated
   public Long getSiteId() {
      return this.siteId;
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
   public Integer getOverwrite() {
      return this.overwrite;
   }

   @Generated
   public Integer getCoexist() {
      return this.coexist;
   }

   @Generated
   public Integer getQualityPriority() {
      return this.qualityPriority;
   }

   @Generated
   public Integer getSizePriority() {
      return this.sizePriority;
   }

   @Generated
   public Integer getHardlinkMode() {
      return this.hardlinkMode;
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
   public void setId(final Long id) {
      this.id = id;
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
   public void setSubscribeId(final Long subscribeId) {
      this.subscribeId = subscribeId;
   }

   @Generated
   public void setDownloaderId(final Long downloaderId) {
      this.downloaderId = downloaderId;
   }

   @Generated
   public void setSiteId(final Long siteId) {
      this.siteId = siteId;
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
   public void setOverwrite(final Integer overwrite) {
      this.overwrite = overwrite;
   }

   @Generated
   public void setCoexist(final Integer coexist) {
      this.coexist = coexist;
   }

   @Generated
   public void setQualityPriority(final Integer qualityPriority) {
      this.qualityPriority = qualityPriority;
   }

   @Generated
   public void setSizePriority(final Integer sizePriority) {
      this.sizePriority = sizePriority;
   }

   @Generated
   public void setHardlinkMode(final Integer hardlinkMode) {
      this.hardlinkMode = hardlinkMode;
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
   @Override
   public String toString() {
      return "MovieDownloadRecordEntity(id="
         + this.getId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", mediaType="
         + this.getMediaType()
         + ", subscribeId="
         + this.getSubscribeId()
         + ", downloaderId="
         + this.getDownloaderId()
         + ", siteId="
         + this.getSiteId()
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
         + ", sourceFilePath="
         + this.getSourceFilePath()
         + ", linkFilePath="
         + this.getLinkFilePath()
         + ", episodeCodes="
         + this.getEpisodeCodes()
         + ", episodeSeqs="
         + this.getEpisodeSeqs()
         + ", overwrite="
         + this.getOverwrite()
         + ", coexist="
         + this.getCoexist()
         + ", qualityPriority="
         + this.getQualityPriority()
         + ", sizePriority="
         + this.getSizePriority()
         + ", hardlinkMode="
         + this.getHardlinkMode()
         + ", size="
         + this.getSize()
         + ", errorMessage="
         + this.getErrorMessage()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieDownloadRecordEntity other)) {
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
               Object this$subscribeId = this.getSubscribeId();
               Object other$subscribeId = other.getSubscribeId();
               if (this$subscribeId == null ? other$subscribeId == null : this$subscribeId.equals(other$subscribeId)) {
                  Object this$downloaderId = this.getDownloaderId();
                  Object other$downloaderId = other.getDownloaderId();
                  if (this$downloaderId == null ? other$downloaderId == null : this$downloaderId.equals(other$downloaderId)) {
                     Object this$siteId = this.getSiteId();
                     Object other$siteId = other.getSiteId();
                     if (this$siteId == null ? other$siteId == null : this$siteId.equals(other$siteId)) {
                        Object this$overwrite = this.getOverwrite();
                        Object other$overwrite = other.getOverwrite();
                        if (this$overwrite == null ? other$overwrite == null : this$overwrite.equals(other$overwrite)) {
                           Object this$coexist = this.getCoexist();
                           Object other$coexist = other.getCoexist();
                           if (this$coexist == null ? other$coexist == null : this$coexist.equals(other$coexist)) {
                              Object this$qualityPriority = this.getQualityPriority();
                              Object other$qualityPriority = other.getQualityPriority();
                              if (this$qualityPriority == null ? other$qualityPriority == null : this$qualityPriority.equals(other$qualityPriority)) {
                                 Object this$sizePriority = this.getSizePriority();
                                 Object other$sizePriority = other.getSizePriority();
                                 if (this$sizePriority == null ? other$sizePriority == null : this$sizePriority.equals(other$sizePriority)) {
                                    Object this$hardlinkMode = this.getHardlinkMode();
                                    Object other$hardlinkMode = other.getHardlinkMode();
                                    if (this$hardlinkMode == null ? other$hardlinkMode == null : this$hardlinkMode.equals(other$hardlinkMode)) {
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
                                                         if (this$qbDownloadPath == null
                                                            ? other$qbDownloadPath == null
                                                            : this$qbDownloadPath.equals(other$qbDownloadPath)) {
                                                            Object this$hardlinkPath = this.getHardlinkPath();
                                                            Object other$hardlinkPath = other.getHardlinkPath();
                                                            if (this$hardlinkPath == null
                                                               ? other$hardlinkPath == null
                                                               : this$hardlinkPath.equals(other$hardlinkPath)) {
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
                                                                                       Object this$size = this.getSize();
                                                                                       Object other$size = other.getSize();
                                                                                       if (this$size == null
                                                                                          ? other$size == null
                                                                                          : this$size.equals(other$size)) {
                                                                                          Object this$errorMessage = this.getErrorMessage();
                                                                                          Object other$errorMessage = other.getErrorMessage();
                                                                                          return this$errorMessage == null
                                                                                             ? other$errorMessage == null
                                                                                             : this$errorMessage.equals(other$errorMessage);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof MovieDownloadRecordEntity;
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
      Object $subscribeId = this.getSubscribeId();
      result = result * 59 + ($subscribeId == null ? 43 : $subscribeId.hashCode());
      Object $downloaderId = this.getDownloaderId();
      result = result * 59 + ($downloaderId == null ? 43 : $downloaderId.hashCode());
      Object $siteId = this.getSiteId();
      result = result * 59 + ($siteId == null ? 43 : $siteId.hashCode());
      Object $overwrite = this.getOverwrite();
      result = result * 59 + ($overwrite == null ? 43 : $overwrite.hashCode());
      Object $coexist = this.getCoexist();
      result = result * 59 + ($coexist == null ? 43 : $coexist.hashCode());
      Object $qualityPriority = this.getQualityPriority();
      result = result * 59 + ($qualityPriority == null ? 43 : $qualityPriority.hashCode());
      Object $sizePriority = this.getSizePriority();
      result = result * 59 + ($sizePriority == null ? 43 : $sizePriority.hashCode());
      Object $hardlinkMode = this.getHardlinkMode();
      result = result * 59 + ($hardlinkMode == null ? 43 : $hardlinkMode.hashCode());
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
      Object $sourceFilePath = this.getSourceFilePath();
      result = result * 59 + ($sourceFilePath == null ? 43 : $sourceFilePath.hashCode());
      Object $linkFilePath = this.getLinkFilePath();
      result = result * 59 + ($linkFilePath == null ? 43 : $linkFilePath.hashCode());
      Object $episodeCodes = this.getEpisodeCodes();
      result = result * 59 + ($episodeCodes == null ? 43 : $episodeCodes.hashCode());
      Object $episodeSeqs = this.getEpisodeSeqs();
      result = result * 59 + ($episodeSeqs == null ? 43 : $episodeSeqs.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
      Object $errorMessage = this.getErrorMessage();
      return result * 59 + ($errorMessage == null ? 43 : $errorMessage.hashCode());
   }
}
