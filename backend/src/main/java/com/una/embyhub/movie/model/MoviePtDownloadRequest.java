package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MoviePtDownloadRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long siteId;
   private Long tmdbId;
   private String mediaType;
   private String torrentId;
   private String downloadUrl;
   private String savePath;
   private Long scrapePathConfigId;
   private String movieName;
   private String title;
   private String size;
   private String movieYear;
   private String posterUrl;
   private String coverUrl;
   private Long downloaderId;
   private Long subscribeId;
   private Boolean skipNotify;

   @Generated
   public Long getSiteId() {
      return this.siteId;
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
   public String getTorrentId() {
      return this.torrentId;
   }

   @Generated
   public String getDownloadUrl() {
      return this.downloadUrl;
   }

   @Generated
   public String getSavePath() {
      return this.savePath;
   }

   @Generated
   public Long getScrapePathConfigId() {
      return this.scrapePathConfigId;
   }

   @Generated
   public String getMovieName() {
      return this.movieName;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getSize() {
      return this.size;
   }

   @Generated
   public String getMovieYear() {
      return this.movieYear;
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
   public Long getDownloaderId() {
      return this.downloaderId;
   }

   @Generated
   public Long getSubscribeId() {
      return this.subscribeId;
   }

   @Generated
   public Boolean getSkipNotify() {
      return this.skipNotify;
   }

   @Generated
   public void setSiteId(final Long siteId) {
      this.siteId = siteId;
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
   public void setTorrentId(final String torrentId) {
      this.torrentId = torrentId;
   }

   @Generated
   public void setDownloadUrl(final String downloadUrl) {
      this.downloadUrl = downloadUrl;
   }

   @Generated
   public void setSavePath(final String savePath) {
      this.savePath = savePath;
   }

   @Generated
   public void setScrapePathConfigId(final Long scrapePathConfigId) {
      this.scrapePathConfigId = scrapePathConfigId;
   }

   @Generated
   public void setMovieName(final String movieName) {
      this.movieName = movieName;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setSize(final String size) {
      this.size = size;
   }

   @Generated
   public void setMovieYear(final String movieYear) {
      this.movieYear = movieYear;
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
   public void setDownloaderId(final Long downloaderId) {
      this.downloaderId = downloaderId;
   }

   @Generated
   public void setSubscribeId(final Long subscribeId) {
      this.subscribeId = subscribeId;
   }

   @Generated
   public void setSkipNotify(final Boolean skipNotify) {
      this.skipNotify = skipNotify;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtDownloadRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$siteId = this.getSiteId();
         Object other$siteId = other.getSiteId();
         if (this$siteId == null ? other$siteId == null : this$siteId.equals(other$siteId)) {
            Object this$tmdbId = this.getTmdbId();
            Object other$tmdbId = other.getTmdbId();
            if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
               Object this$scrapePathConfigId = this.getScrapePathConfigId();
               Object other$scrapePathConfigId = other.getScrapePathConfigId();
               if (this$scrapePathConfigId == null ? other$scrapePathConfigId == null : this$scrapePathConfigId.equals(other$scrapePathConfigId)) {
                  Object this$downloaderId = this.getDownloaderId();
                  Object other$downloaderId = other.getDownloaderId();
                  if (this$downloaderId == null ? other$downloaderId == null : this$downloaderId.equals(other$downloaderId)) {
                     Object this$subscribeId = this.getSubscribeId();
                     Object other$subscribeId = other.getSubscribeId();
                     if (this$subscribeId == null ? other$subscribeId == null : this$subscribeId.equals(other$subscribeId)) {
                        Object this$skipNotify = this.getSkipNotify();
                        Object other$skipNotify = other.getSkipNotify();
                        if (this$skipNotify == null ? other$skipNotify == null : this$skipNotify.equals(other$skipNotify)) {
                           Object this$mediaType = this.getMediaType();
                           Object other$mediaType = other.getMediaType();
                           if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                              Object this$torrentId = this.getTorrentId();
                              Object other$torrentId = other.getTorrentId();
                              if (this$torrentId == null ? other$torrentId == null : this$torrentId.equals(other$torrentId)) {
                                 Object this$downloadUrl = this.getDownloadUrl();
                                 Object other$downloadUrl = other.getDownloadUrl();
                                 if (this$downloadUrl == null ? other$downloadUrl == null : this$downloadUrl.equals(other$downloadUrl)) {
                                    Object this$savePath = this.getSavePath();
                                    Object other$savePath = other.getSavePath();
                                    if (this$savePath == null ? other$savePath == null : this$savePath.equals(other$savePath)) {
                                       Object this$movieName = this.getMovieName();
                                       Object other$movieName = other.getMovieName();
                                       if (this$movieName == null ? other$movieName == null : this$movieName.equals(other$movieName)) {
                                          Object this$title = this.getTitle();
                                          Object other$title = other.getTitle();
                                          if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                             Object this$size = this.getSize();
                                             Object other$size = other.getSize();
                                             if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                                                Object this$movieYear = this.getMovieYear();
                                                Object other$movieYear = other.getMovieYear();
                                                if (this$movieYear == null ? other$movieYear == null : this$movieYear.equals(other$movieYear)) {
                                                   Object this$posterUrl = this.getPosterUrl();
                                                   Object other$posterUrl = other.getPosterUrl();
                                                   if (this$posterUrl == null ? other$posterUrl == null : this$posterUrl.equals(other$posterUrl)) {
                                                      Object this$coverUrl = this.getCoverUrl();
                                                      Object other$coverUrl = other.getCoverUrl();
                                                      return this$coverUrl == null ? other$coverUrl == null : this$coverUrl.equals(other$coverUrl);
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
      return other instanceof MoviePtDownloadRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $siteId = this.getSiteId();
      result = result * 59 + ($siteId == null ? 43 : $siteId.hashCode());
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $scrapePathConfigId = this.getScrapePathConfigId();
      result = result * 59 + ($scrapePathConfigId == null ? 43 : $scrapePathConfigId.hashCode());
      Object $downloaderId = this.getDownloaderId();
      result = result * 59 + ($downloaderId == null ? 43 : $downloaderId.hashCode());
      Object $subscribeId = this.getSubscribeId();
      result = result * 59 + ($subscribeId == null ? 43 : $subscribeId.hashCode());
      Object $skipNotify = this.getSkipNotify();
      result = result * 59 + ($skipNotify == null ? 43 : $skipNotify.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $torrentId = this.getTorrentId();
      result = result * 59 + ($torrentId == null ? 43 : $torrentId.hashCode());
      Object $downloadUrl = this.getDownloadUrl();
      result = result * 59 + ($downloadUrl == null ? 43 : $downloadUrl.hashCode());
      Object $savePath = this.getSavePath();
      result = result * 59 + ($savePath == null ? 43 : $savePath.hashCode());
      Object $movieName = this.getMovieName();
      result = result * 59 + ($movieName == null ? 43 : $movieName.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
      Object $movieYear = this.getMovieYear();
      result = result * 59 + ($movieYear == null ? 43 : $movieYear.hashCode());
      Object $posterUrl = this.getPosterUrl();
      result = result * 59 + ($posterUrl == null ? 43 : $posterUrl.hashCode());
      Object $coverUrl = this.getCoverUrl();
      return result * 59 + ($coverUrl == null ? 43 : $coverUrl.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtDownloadRequest(siteId="
         + this.getSiteId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", mediaType="
         + this.getMediaType()
         + ", torrentId="
         + this.getTorrentId()
         + ", downloadUrl="
         + this.getDownloadUrl()
         + ", savePath="
         + this.getSavePath()
         + ", scrapePathConfigId="
         + this.getScrapePathConfigId()
         + ", movieName="
         + this.getMovieName()
         + ", title="
         + this.getTitle()
         + ", size="
         + this.getSize()
         + ", movieYear="
         + this.getMovieYear()
         + ", posterUrl="
         + this.getPosterUrl()
         + ", coverUrl="
         + this.getCoverUrl()
         + ", downloaderId="
         + this.getDownloaderId()
         + ", subscribeId="
         + this.getSubscribeId()
         + ", skipNotify="
         + this.getSkipNotify()
         + ")";
   }
}
