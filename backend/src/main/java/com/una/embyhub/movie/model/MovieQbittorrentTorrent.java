package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MovieQbittorrentTorrent implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;
   private String hash;
   private String savePath;
   private String contentPath;
   private String tags;
   private Double progress;
   private String state;
   private Long dlspeed;
   private Long upspeed;
   private Long size;
   private Long eta;
   private Long recordId;
   private String movieName;
   private String movieYear;
   private String title;
   private String movieNameWithYear;
   private String posterUrl;
   private String mediaType;
   private String siteName;

   @Generated
   MovieQbittorrentTorrent(
      final String name,
      final String hash,
      final String savePath,
      final String contentPath,
      final String tags,
      final Double progress,
      final String state,
      final Long dlspeed,
      final Long upspeed,
      final Long size,
      final Long eta,
      final Long recordId,
      final String movieName,
      final String movieYear,
      final String title,
      final String movieNameWithYear,
      final String posterUrl,
      final String mediaType,
      final String siteName
   ) {
      this.name = name;
      this.hash = hash;
      this.savePath = savePath;
      this.contentPath = contentPath;
      this.tags = tags;
      this.progress = progress;
      this.state = state;
      this.dlspeed = dlspeed;
      this.upspeed = upspeed;
      this.size = size;
      this.eta = eta;
      this.recordId = recordId;
      this.movieName = movieName;
      this.movieYear = movieYear;
      this.title = title;
      this.movieNameWithYear = movieNameWithYear;
      this.posterUrl = posterUrl;
      this.mediaType = mediaType;
      this.siteName = siteName;
   }

   @Generated
   public static MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder builder() {
      return new MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder();
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getHash() {
      return this.hash;
   }

   @Generated
   public String getSavePath() {
      return this.savePath;
   }

   @Generated
   public String getContentPath() {
      return this.contentPath;
   }

   @Generated
   public String getTags() {
      return this.tags;
   }

   @Generated
   public Double getProgress() {
      return this.progress;
   }

   @Generated
   public String getState() {
      return this.state;
   }

   @Generated
   public Long getDlspeed() {
      return this.dlspeed;
   }

   @Generated
   public Long getUpspeed() {
      return this.upspeed;
   }

   @Generated
   public Long getSize() {
      return this.size;
   }

   @Generated
   public Long getEta() {
      return this.eta;
   }

   @Generated
   public Long getRecordId() {
      return this.recordId;
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
   public String getMovieNameWithYear() {
      return this.movieNameWithYear;
   }

   @Generated
   public String getPosterUrl() {
      return this.posterUrl;
   }

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public String getSiteName() {
      return this.siteName;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setHash(final String hash) {
      this.hash = hash;
   }

   @Generated
   public void setSavePath(final String savePath) {
      this.savePath = savePath;
   }

   @Generated
   public void setContentPath(final String contentPath) {
      this.contentPath = contentPath;
   }

   @Generated
   public void setTags(final String tags) {
      this.tags = tags;
   }

   @Generated
   public void setProgress(final Double progress) {
      this.progress = progress;
   }

   @Generated
   public void setState(final String state) {
      this.state = state;
   }

   @Generated
   public void setDlspeed(final Long dlspeed) {
      this.dlspeed = dlspeed;
   }

   @Generated
   public void setUpspeed(final Long upspeed) {
      this.upspeed = upspeed;
   }

   @Generated
   public void setSize(final Long size) {
      this.size = size;
   }

   @Generated
   public void setEta(final Long eta) {
      this.eta = eta;
   }

   @Generated
   public void setRecordId(final Long recordId) {
      this.recordId = recordId;
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
   public void setMovieNameWithYear(final String movieNameWithYear) {
      this.movieNameWithYear = movieNameWithYear;
   }

   @Generated
   public void setPosterUrl(final String posterUrl) {
      this.posterUrl = posterUrl;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setSiteName(final String siteName) {
      this.siteName = siteName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieQbittorrentTorrent other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$progress = this.getProgress();
         Object other$progress = other.getProgress();
         if (this$progress == null ? other$progress == null : this$progress.equals(other$progress)) {
            Object this$dlspeed = this.getDlspeed();
            Object other$dlspeed = other.getDlspeed();
            if (this$dlspeed == null ? other$dlspeed == null : this$dlspeed.equals(other$dlspeed)) {
               Object this$upspeed = this.getUpspeed();
               Object other$upspeed = other.getUpspeed();
               if (this$upspeed == null ? other$upspeed == null : this$upspeed.equals(other$upspeed)) {
                  Object this$size = this.getSize();
                  Object other$size = other.getSize();
                  if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                     Object this$eta = this.getEta();
                     Object other$eta = other.getEta();
                     if (this$eta == null ? other$eta == null : this$eta.equals(other$eta)) {
                        Object this$recordId = this.getRecordId();
                        Object other$recordId = other.getRecordId();
                        if (this$recordId == null ? other$recordId == null : this$recordId.equals(other$recordId)) {
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                              Object this$hash = this.getHash();
                              Object other$hash = other.getHash();
                              if (this$hash == null ? other$hash == null : this$hash.equals(other$hash)) {
                                 Object this$savePath = this.getSavePath();
                                 Object other$savePath = other.getSavePath();
                                 if (this$savePath == null ? other$savePath == null : this$savePath.equals(other$savePath)) {
                                    Object this$contentPath = this.getContentPath();
                                    Object other$contentPath = other.getContentPath();
                                    if (this$contentPath == null ? other$contentPath == null : this$contentPath.equals(other$contentPath)) {
                                       Object this$tags = this.getTags();
                                       Object other$tags = other.getTags();
                                       if (this$tags == null ? other$tags == null : this$tags.equals(other$tags)) {
                                          Object this$state = this.getState();
                                          Object other$state = other.getState();
                                          if (this$state == null ? other$state == null : this$state.equals(other$state)) {
                                             Object this$movieName = this.getMovieName();
                                             Object other$movieName = other.getMovieName();
                                             if (this$movieName == null ? other$movieName == null : this$movieName.equals(other$movieName)) {
                                                Object this$movieYear = this.getMovieYear();
                                                Object other$movieYear = other.getMovieYear();
                                                if (this$movieYear == null ? other$movieYear == null : this$movieYear.equals(other$movieYear)) {
                                                   Object this$title = this.getTitle();
                                                   Object other$title = other.getTitle();
                                                   if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                                      Object this$movieNameWithYear = this.getMovieNameWithYear();
                                                      Object other$movieNameWithYear = other.getMovieNameWithYear();
                                                      if (this$movieNameWithYear == null
                                                         ? other$movieNameWithYear == null
                                                         : this$movieNameWithYear.equals(other$movieNameWithYear)) {
                                                         Object this$posterUrl = this.getPosterUrl();
                                                         Object other$posterUrl = other.getPosterUrl();
                                                         if (this$posterUrl == null ? other$posterUrl == null : this$posterUrl.equals(other$posterUrl)) {
                                                            Object this$mediaType = this.getMediaType();
                                                            Object other$mediaType = other.getMediaType();
                                                            if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                                                               Object this$siteName = this.getSiteName();
                                                               Object other$siteName = other.getSiteName();
                                                               return this$siteName == null ? other$siteName == null : this$siteName.equals(other$siteName);
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
      return other instanceof MovieQbittorrentTorrent;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $progress = this.getProgress();
      result = result * 59 + ($progress == null ? 43 : $progress.hashCode());
      Object $dlspeed = this.getDlspeed();
      result = result * 59 + ($dlspeed == null ? 43 : $dlspeed.hashCode());
      Object $upspeed = this.getUpspeed();
      result = result * 59 + ($upspeed == null ? 43 : $upspeed.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
      Object $eta = this.getEta();
      result = result * 59 + ($eta == null ? 43 : $eta.hashCode());
      Object $recordId = this.getRecordId();
      result = result * 59 + ($recordId == null ? 43 : $recordId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $hash = this.getHash();
      result = result * 59 + ($hash == null ? 43 : $hash.hashCode());
      Object $savePath = this.getSavePath();
      result = result * 59 + ($savePath == null ? 43 : $savePath.hashCode());
      Object $contentPath = this.getContentPath();
      result = result * 59 + ($contentPath == null ? 43 : $contentPath.hashCode());
      Object $tags = this.getTags();
      result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
      Object $state = this.getState();
      result = result * 59 + ($state == null ? 43 : $state.hashCode());
      Object $movieName = this.getMovieName();
      result = result * 59 + ($movieName == null ? 43 : $movieName.hashCode());
      Object $movieYear = this.getMovieYear();
      result = result * 59 + ($movieYear == null ? 43 : $movieYear.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $movieNameWithYear = this.getMovieNameWithYear();
      result = result * 59 + ($movieNameWithYear == null ? 43 : $movieNameWithYear.hashCode());
      Object $posterUrl = this.getPosterUrl();
      result = result * 59 + ($posterUrl == null ? 43 : $posterUrl.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $siteName = this.getSiteName();
      return result * 59 + ($siteName == null ? 43 : $siteName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieQbittorrentTorrent(name="
         + this.getName()
         + ", hash="
         + this.getHash()
         + ", savePath="
         + this.getSavePath()
         + ", contentPath="
         + this.getContentPath()
         + ", tags="
         + this.getTags()
         + ", progress="
         + this.getProgress()
         + ", state="
         + this.getState()
         + ", dlspeed="
         + this.getDlspeed()
         + ", upspeed="
         + this.getUpspeed()
         + ", size="
         + this.getSize()
         + ", eta="
         + this.getEta()
         + ", recordId="
         + this.getRecordId()
         + ", movieName="
         + this.getMovieName()
         + ", movieYear="
         + this.getMovieYear()
         + ", title="
         + this.getTitle()
         + ", movieNameWithYear="
         + this.getMovieNameWithYear()
         + ", posterUrl="
         + this.getPosterUrl()
         + ", mediaType="
         + this.getMediaType()
         + ", siteName="
         + this.getSiteName()
         + ")";
   }

   @Generated
   public static class MovieQbittorrentTorrentBuilder {
      @Generated
      private String name;
      @Generated
      private String hash;
      @Generated
      private String savePath;
      @Generated
      private String contentPath;
      @Generated
      private String tags;
      @Generated
      private Double progress;
      @Generated
      private String state;
      @Generated
      private Long dlspeed;
      @Generated
      private Long upspeed;
      @Generated
      private Long size;
      @Generated
      private Long eta;
      @Generated
      private Long recordId;
      @Generated
      private String movieName;
      @Generated
      private String movieYear;
      @Generated
      private String title;
      @Generated
      private String movieNameWithYear;
      @Generated
      private String posterUrl;
      @Generated
      private String mediaType;
      @Generated
      private String siteName;

      @Generated
      MovieQbittorrentTorrentBuilder() {
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder name(final String name) {
         this.name = name;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder hash(final String hash) {
         this.hash = hash;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder savePath(final String savePath) {
         this.savePath = savePath;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder contentPath(final String contentPath) {
         this.contentPath = contentPath;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder tags(final String tags) {
         this.tags = tags;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder progress(final Double progress) {
         this.progress = progress;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder state(final String state) {
         this.state = state;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder dlspeed(final Long dlspeed) {
         this.dlspeed = dlspeed;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder upspeed(final Long upspeed) {
         this.upspeed = upspeed;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder size(final Long size) {
         this.size = size;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder eta(final Long eta) {
         this.eta = eta;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder recordId(final Long recordId) {
         this.recordId = recordId;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder movieName(final String movieName) {
         this.movieName = movieName;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder movieYear(final String movieYear) {
         this.movieYear = movieYear;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder title(final String title) {
         this.title = title;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder movieNameWithYear(final String movieNameWithYear) {
         this.movieNameWithYear = movieNameWithYear;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder posterUrl(final String posterUrl) {
         this.posterUrl = posterUrl;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder mediaType(final String mediaType) {
         this.mediaType = mediaType;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder siteName(final String siteName) {
         this.siteName = siteName;
         return this;
      }

      @Generated
      public MovieQbittorrentTorrent build() {
         return new MovieQbittorrentTorrent(
            this.name,
            this.hash,
            this.savePath,
            this.contentPath,
            this.tags,
            this.progress,
            this.state,
            this.dlspeed,
            this.upspeed,
            this.size,
            this.eta,
            this.recordId,
            this.movieName,
            this.movieYear,
            this.title,
            this.movieNameWithYear,
            this.posterUrl,
            this.mediaType,
            this.siteName
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MovieQbittorrentTorrent.MovieQbittorrentTorrentBuilder(name="
            + this.name
            + ", hash="
            + this.hash
            + ", savePath="
            + this.savePath
            + ", contentPath="
            + this.contentPath
            + ", tags="
            + this.tags
            + ", progress="
            + this.progress
            + ", state="
            + this.state
            + ", dlspeed="
            + this.dlspeed
            + ", upspeed="
            + this.upspeed
            + ", size="
            + this.size
            + ", eta="
            + this.eta
            + ", recordId="
            + this.recordId
            + ", movieName="
            + this.movieName
            + ", movieYear="
            + this.movieYear
            + ", title="
            + this.title
            + ", movieNameWithYear="
            + this.movieNameWithYear
            + ", posterUrl="
            + this.posterUrl
            + ", mediaType="
            + this.mediaType
            + ", siteName="
            + this.siteName
            + ")";
      }
   }
}
