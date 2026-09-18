package com.una.embyhub.movie.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class MoviePtSearchResult implements Serializable {
   private static final long serialVersionUID = 1L;
   private String title;
   private String subtitle;
   private String cover;
   private String size;
   private String seeders;
   private String leechers;
   private String promo;
   private String downloadUrl;
   private String detailUrl;
   private String torrentId;
   private Long siteId;
   private String siteName;
   private List<String> tags;
   private String resolution;
   private String quality;
   private List<String> audioEffects;
   private Date promotionUntil;

   @Generated
   MoviePtSearchResult(
      final String title,
      final String subtitle,
      final String cover,
      final String size,
      final String seeders,
      final String leechers,
      final String promo,
      final String downloadUrl,
      final String detailUrl,
      final String torrentId,
      final Long siteId,
      final String siteName,
      final List<String> tags,
      final String resolution,
      final String quality,
      final List<String> audioEffects,
      final Date promotionUntil
   ) {
      this.title = title;
      this.subtitle = subtitle;
      this.cover = cover;
      this.size = size;
      this.seeders = seeders;
      this.leechers = leechers;
      this.promo = promo;
      this.downloadUrl = downloadUrl;
      this.detailUrl = detailUrl;
      this.torrentId = torrentId;
      this.siteId = siteId;
      this.siteName = siteName;
      this.tags = tags;
      this.resolution = resolution;
      this.quality = quality;
      this.audioEffects = audioEffects;
      this.promotionUntil = promotionUntil;
   }

   @Generated
   public static MoviePtSearchResult.MoviePtSearchResultBuilder builder() {
      return new MoviePtSearchResult.MoviePtSearchResultBuilder();
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getSubtitle() {
      return this.subtitle;
   }

   @Generated
   public String getCover() {
      return this.cover;
   }

   @Generated
   public String getSize() {
      return this.size;
   }

   @Generated
   public String getSeeders() {
      return this.seeders;
   }

   @Generated
   public String getLeechers() {
      return this.leechers;
   }

   @Generated
   public String getPromo() {
      return this.promo;
   }

   @Generated
   public String getDownloadUrl() {
      return this.downloadUrl;
   }

   @Generated
   public String getDetailUrl() {
      return this.detailUrl;
   }

   @Generated
   public String getTorrentId() {
      return this.torrentId;
   }

   @Generated
   public Long getSiteId() {
      return this.siteId;
   }

   @Generated
   public String getSiteName() {
      return this.siteName;
   }

   @Generated
   public List<String> getTags() {
      return this.tags;
   }

   @Generated
   public String getResolution() {
      return this.resolution;
   }

   @Generated
   public String getQuality() {
      return this.quality;
   }

   @Generated
   public List<String> getAudioEffects() {
      return this.audioEffects;
   }

   @Generated
   public Date getPromotionUntil() {
      return this.promotionUntil;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setSubtitle(final String subtitle) {
      this.subtitle = subtitle;
   }

   @Generated
   public void setCover(final String cover) {
      this.cover = cover;
   }

   @Generated
   public void setSize(final String size) {
      this.size = size;
   }

   @Generated
   public void setSeeders(final String seeders) {
      this.seeders = seeders;
   }

   @Generated
   public void setLeechers(final String leechers) {
      this.leechers = leechers;
   }

   @Generated
   public void setPromo(final String promo) {
      this.promo = promo;
   }

   @Generated
   public void setDownloadUrl(final String downloadUrl) {
      this.downloadUrl = downloadUrl;
   }

   @Generated
   public void setDetailUrl(final String detailUrl) {
      this.detailUrl = detailUrl;
   }

   @Generated
   public void setTorrentId(final String torrentId) {
      this.torrentId = torrentId;
   }

   @Generated
   public void setSiteId(final Long siteId) {
      this.siteId = siteId;
   }

   @Generated
   public void setSiteName(final String siteName) {
      this.siteName = siteName;
   }

   @Generated
   public void setTags(final List<String> tags) {
      this.tags = tags;
   }

   @Generated
   public void setResolution(final String resolution) {
      this.resolution = resolution;
   }

   @Generated
   public void setQuality(final String quality) {
      this.quality = quality;
   }

   @Generated
   public void setAudioEffects(final List<String> audioEffects) {
      this.audioEffects = audioEffects;
   }

   @Generated
   public void setPromotionUntil(final Date promotionUntil) {
      this.promotionUntil = promotionUntil;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSearchResult other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$siteId = this.getSiteId();
         Object other$siteId = other.getSiteId();
         if (this$siteId == null ? other$siteId == null : this$siteId.equals(other$siteId)) {
            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null ? other$title == null : this$title.equals(other$title)) {
               Object this$subtitle = this.getSubtitle();
               Object other$subtitle = other.getSubtitle();
               if (this$subtitle == null ? other$subtitle == null : this$subtitle.equals(other$subtitle)) {
                  Object this$cover = this.getCover();
                  Object other$cover = other.getCover();
                  if (this$cover == null ? other$cover == null : this$cover.equals(other$cover)) {
                     Object this$size = this.getSize();
                     Object other$size = other.getSize();
                     if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                        Object this$seeders = this.getSeeders();
                        Object other$seeders = other.getSeeders();
                        if (this$seeders == null ? other$seeders == null : this$seeders.equals(other$seeders)) {
                           Object this$leechers = this.getLeechers();
                           Object other$leechers = other.getLeechers();
                           if (this$leechers == null ? other$leechers == null : this$leechers.equals(other$leechers)) {
                              Object this$promo = this.getPromo();
                              Object other$promo = other.getPromo();
                              if (this$promo == null ? other$promo == null : this$promo.equals(other$promo)) {
                                 Object this$downloadUrl = this.getDownloadUrl();
                                 Object other$downloadUrl = other.getDownloadUrl();
                                 if (this$downloadUrl == null ? other$downloadUrl == null : this$downloadUrl.equals(other$downloadUrl)) {
                                    Object this$detailUrl = this.getDetailUrl();
                                    Object other$detailUrl = other.getDetailUrl();
                                    if (this$detailUrl == null ? other$detailUrl == null : this$detailUrl.equals(other$detailUrl)) {
                                       Object this$torrentId = this.getTorrentId();
                                       Object other$torrentId = other.getTorrentId();
                                       if (this$torrentId == null ? other$torrentId == null : this$torrentId.equals(other$torrentId)) {
                                          Object this$siteName = this.getSiteName();
                                          Object other$siteName = other.getSiteName();
                                          if (this$siteName == null ? other$siteName == null : this$siteName.equals(other$siteName)) {
                                             Object this$tags = this.getTags();
                                             Object other$tags = other.getTags();
                                             if (this$tags == null ? other$tags == null : this$tags.equals(other$tags)) {
                                                Object this$resolution = this.getResolution();
                                                Object other$resolution = other.getResolution();
                                                if (this$resolution == null ? other$resolution == null : this$resolution.equals(other$resolution)) {
                                                   Object this$quality = this.getQuality();
                                                   Object other$quality = other.getQuality();
                                                   if (this$quality == null ? other$quality == null : this$quality.equals(other$quality)) {
                                                      Object this$audioEffects = this.getAudioEffects();
                                                      Object other$audioEffects = other.getAudioEffects();
                                                      if (this$audioEffects == null ? other$audioEffects == null : this$audioEffects.equals(other$audioEffects)
                                                         )
                                                       {
                                                         Object this$promotionUntil = this.getPromotionUntil();
                                                         Object other$promotionUntil = other.getPromotionUntil();
                                                         return this$promotionUntil == null
                                                            ? other$promotionUntil == null
                                                            : this$promotionUntil.equals(other$promotionUntil);
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
      return other instanceof MoviePtSearchResult;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $siteId = this.getSiteId();
      result = result * 59 + ($siteId == null ? 43 : $siteId.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $subtitle = this.getSubtitle();
      result = result * 59 + ($subtitle == null ? 43 : $subtitle.hashCode());
      Object $cover = this.getCover();
      result = result * 59 + ($cover == null ? 43 : $cover.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
      Object $seeders = this.getSeeders();
      result = result * 59 + ($seeders == null ? 43 : $seeders.hashCode());
      Object $leechers = this.getLeechers();
      result = result * 59 + ($leechers == null ? 43 : $leechers.hashCode());
      Object $promo = this.getPromo();
      result = result * 59 + ($promo == null ? 43 : $promo.hashCode());
      Object $downloadUrl = this.getDownloadUrl();
      result = result * 59 + ($downloadUrl == null ? 43 : $downloadUrl.hashCode());
      Object $detailUrl = this.getDetailUrl();
      result = result * 59 + ($detailUrl == null ? 43 : $detailUrl.hashCode());
      Object $torrentId = this.getTorrentId();
      result = result * 59 + ($torrentId == null ? 43 : $torrentId.hashCode());
      Object $siteName = this.getSiteName();
      result = result * 59 + ($siteName == null ? 43 : $siteName.hashCode());
      Object $tags = this.getTags();
      result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
      Object $resolution = this.getResolution();
      result = result * 59 + ($resolution == null ? 43 : $resolution.hashCode());
      Object $quality = this.getQuality();
      result = result * 59 + ($quality == null ? 43 : $quality.hashCode());
      Object $audioEffects = this.getAudioEffects();
      result = result * 59 + ($audioEffects == null ? 43 : $audioEffects.hashCode());
      Object $promotionUntil = this.getPromotionUntil();
      return result * 59 + ($promotionUntil == null ? 43 : $promotionUntil.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSearchResult(title="
         + this.getTitle()
         + ", subtitle="
         + this.getSubtitle()
         + ", cover="
         + this.getCover()
         + ", size="
         + this.getSize()
         + ", seeders="
         + this.getSeeders()
         + ", leechers="
         + this.getLeechers()
         + ", promo="
         + this.getPromo()
         + ", downloadUrl="
         + this.getDownloadUrl()
         + ", detailUrl="
         + this.getDetailUrl()
         + ", torrentId="
         + this.getTorrentId()
         + ", siteId="
         + this.getSiteId()
         + ", siteName="
         + this.getSiteName()
         + ", tags="
         + this.getTags()
         + ", resolution="
         + this.getResolution()
         + ", quality="
         + this.getQuality()
         + ", audioEffects="
         + this.getAudioEffects()
         + ", promotionUntil="
         + this.getPromotionUntil()
         + ")";
   }

   @Generated
   public static class MoviePtSearchResultBuilder {
      @Generated
      private String title;
      @Generated
      private String subtitle;
      @Generated
      private String cover;
      @Generated
      private String size;
      @Generated
      private String seeders;
      @Generated
      private String leechers;
      @Generated
      private String promo;
      @Generated
      private String downloadUrl;
      @Generated
      private String detailUrl;
      @Generated
      private String torrentId;
      @Generated
      private Long siteId;
      @Generated
      private String siteName;
      @Generated
      private List<String> tags;
      @Generated
      private String resolution;
      @Generated
      private String quality;
      @Generated
      private List<String> audioEffects;
      @Generated
      private Date promotionUntil;

      @Generated
      MoviePtSearchResultBuilder() {
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder title(final String title) {
         this.title = title;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder subtitle(final String subtitle) {
         this.subtitle = subtitle;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder cover(final String cover) {
         this.cover = cover;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder size(final String size) {
         this.size = size;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder seeders(final String seeders) {
         this.seeders = seeders;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder leechers(final String leechers) {
         this.leechers = leechers;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder promo(final String promo) {
         this.promo = promo;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder downloadUrl(final String downloadUrl) {
         this.downloadUrl = downloadUrl;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder detailUrl(final String detailUrl) {
         this.detailUrl = detailUrl;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder torrentId(final String torrentId) {
         this.torrentId = torrentId;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder siteId(final Long siteId) {
         this.siteId = siteId;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder siteName(final String siteName) {
         this.siteName = siteName;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder tags(final List<String> tags) {
         this.tags = tags;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder resolution(final String resolution) {
         this.resolution = resolution;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder quality(final String quality) {
         this.quality = quality;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder audioEffects(final List<String> audioEffects) {
         this.audioEffects = audioEffects;
         return this;
      }

      @Generated
      public MoviePtSearchResult.MoviePtSearchResultBuilder promotionUntil(final Date promotionUntil) {
         this.promotionUntil = promotionUntil;
         return this;
      }

      @Generated
      public MoviePtSearchResult build() {
         return new MoviePtSearchResult(
            this.title,
            this.subtitle,
            this.cover,
            this.size,
            this.seeders,
            this.leechers,
            this.promo,
            this.downloadUrl,
            this.detailUrl,
            this.torrentId,
            this.siteId,
            this.siteName,
            this.tags,
            this.resolution,
            this.quality,
            this.audioEffects,
            this.promotionUntil
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePtSearchResult.MoviePtSearchResultBuilder(title="
            + this.title
            + ", subtitle="
            + this.subtitle
            + ", cover="
            + this.cover
            + ", size="
            + this.size
            + ", seeders="
            + this.seeders
            + ", leechers="
            + this.leechers
            + ", promo="
            + this.promo
            + ", downloadUrl="
            + this.downloadUrl
            + ", detailUrl="
            + this.detailUrl
            + ", torrentId="
            + this.torrentId
            + ", siteId="
            + this.siteId
            + ", siteName="
            + this.siteName
            + ", tags="
            + this.tags
            + ", resolution="
            + this.resolution
            + ", quality="
            + this.quality
            + ", audioEffects="
            + this.audioEffects
            + ", promotionUntil="
            + this.promotionUntil
            + ")";
      }
   }
}
