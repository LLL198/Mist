package com.una.embyhub.foam.response.douban;

import java.util.List;
import lombok.Generated;

public class FoamDoubanItem {
   private String doubanId;
   private String title;
   private String year;
   private String rate;
   private Integer ratingCount;
   private String cover;
   private String background;
   private String cardSubtitle;
   private String episodesInfo;
   private List<String> tags;
   private String url;
   private Integer tmdbId;
   private Boolean inLibrary;

   @Generated
   public String getDoubanId() {
      return this.doubanId;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getYear() {
      return this.year;
   }

   @Generated
   public String getRate() {
      return this.rate;
   }

   @Generated
   public Integer getRatingCount() {
      return this.ratingCount;
   }

   @Generated
   public String getCover() {
      return this.cover;
   }

   @Generated
   public String getBackground() {
      return this.background;
   }

   @Generated
   public String getCardSubtitle() {
      return this.cardSubtitle;
   }

   @Generated
   public String getEpisodesInfo() {
      return this.episodesInfo;
   }

   @Generated
   public List<String> getTags() {
      return this.tags;
   }

   @Generated
   public String getUrl() {
      return this.url;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public Boolean getInLibrary() {
      return this.inLibrary;
   }

   @Generated
   public void setDoubanId(final String doubanId) {
      this.doubanId = doubanId;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setYear(final String year) {
      this.year = year;
   }

   @Generated
   public void setRate(final String rate) {
      this.rate = rate;
   }

   @Generated
   public void setRatingCount(final Integer ratingCount) {
      this.ratingCount = ratingCount;
   }

   @Generated
   public void setCover(final String cover) {
      this.cover = cover;
   }

   @Generated
   public void setBackground(final String background) {
      this.background = background;
   }

   @Generated
   public void setCardSubtitle(final String cardSubtitle) {
      this.cardSubtitle = cardSubtitle;
   }

   @Generated
   public void setEpisodesInfo(final String episodesInfo) {
      this.episodesInfo = episodesInfo;
   }

   @Generated
   public void setTags(final List<String> tags) {
      this.tags = tags;
   }

   @Generated
   public void setUrl(final String url) {
      this.url = url;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setInLibrary(final Boolean inLibrary) {
      this.inLibrary = inLibrary;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDoubanItem other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$ratingCount = this.getRatingCount();
         Object other$ratingCount = other.getRatingCount();
         if (this$ratingCount == null ? other$ratingCount == null : this$ratingCount.equals(other$ratingCount)) {
            Object this$tmdbId = this.getTmdbId();
            Object other$tmdbId = other.getTmdbId();
            if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
               Object this$inLibrary = this.getInLibrary();
               Object other$inLibrary = other.getInLibrary();
               if (this$inLibrary == null ? other$inLibrary == null : this$inLibrary.equals(other$inLibrary)) {
                  Object this$doubanId = this.getDoubanId();
                  Object other$doubanId = other.getDoubanId();
                  if (this$doubanId == null ? other$doubanId == null : this$doubanId.equals(other$doubanId)) {
                     Object this$title = this.getTitle();
                     Object other$title = other.getTitle();
                     if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                        Object this$year = this.getYear();
                        Object other$year = other.getYear();
                        if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                           Object this$rate = this.getRate();
                           Object other$rate = other.getRate();
                           if (this$rate == null ? other$rate == null : this$rate.equals(other$rate)) {
                              Object this$cover = this.getCover();
                              Object other$cover = other.getCover();
                              if (this$cover == null ? other$cover == null : this$cover.equals(other$cover)) {
                                 Object this$background = this.getBackground();
                                 Object other$background = other.getBackground();
                                 if (this$background == null ? other$background == null : this$background.equals(other$background)) {
                                    Object this$cardSubtitle = this.getCardSubtitle();
                                    Object other$cardSubtitle = other.getCardSubtitle();
                                    if (this$cardSubtitle == null ? other$cardSubtitle == null : this$cardSubtitle.equals(other$cardSubtitle)) {
                                       Object this$episodesInfo = this.getEpisodesInfo();
                                       Object other$episodesInfo = other.getEpisodesInfo();
                                       if (this$episodesInfo == null ? other$episodesInfo == null : this$episodesInfo.equals(other$episodesInfo)) {
                                          Object this$tags = this.getTags();
                                          Object other$tags = other.getTags();
                                          if (this$tags == null ? other$tags == null : this$tags.equals(other$tags)) {
                                             Object this$url = this.getUrl();
                                             Object other$url = other.getUrl();
                                             return this$url == null ? other$url == null : this$url.equals(other$url);
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
      return other instanceof FoamDoubanItem;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $ratingCount = this.getRatingCount();
      result = result * 59 + ($ratingCount == null ? 43 : $ratingCount.hashCode());
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $inLibrary = this.getInLibrary();
      result = result * 59 + ($inLibrary == null ? 43 : $inLibrary.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $year = this.getYear();
      result = result * 59 + ($year == null ? 43 : $year.hashCode());
      Object $rate = this.getRate();
      result = result * 59 + ($rate == null ? 43 : $rate.hashCode());
      Object $cover = this.getCover();
      result = result * 59 + ($cover == null ? 43 : $cover.hashCode());
      Object $background = this.getBackground();
      result = result * 59 + ($background == null ? 43 : $background.hashCode());
      Object $cardSubtitle = this.getCardSubtitle();
      result = result * 59 + ($cardSubtitle == null ? 43 : $cardSubtitle.hashCode());
      Object $episodesInfo = this.getEpisodesInfo();
      result = result * 59 + ($episodesInfo == null ? 43 : $episodesInfo.hashCode());
      Object $tags = this.getTags();
      result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
      Object $url = this.getUrl();
      return result * 59 + ($url == null ? 43 : $url.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDoubanItem(doubanId="
         + this.getDoubanId()
         + ", title="
         + this.getTitle()
         + ", year="
         + this.getYear()
         + ", rate="
         + this.getRate()
         + ", ratingCount="
         + this.getRatingCount()
         + ", cover="
         + this.getCover()
         + ", background="
         + this.getBackground()
         + ", cardSubtitle="
         + this.getCardSubtitle()
         + ", episodesInfo="
         + this.getEpisodesInfo()
         + ", tags="
         + this.getTags()
         + ", url="
         + this.getUrl()
         + ", tmdbId="
         + this.getTmdbId()
         + ", inLibrary="
         + this.getInLibrary()
         + ")";
   }
}
