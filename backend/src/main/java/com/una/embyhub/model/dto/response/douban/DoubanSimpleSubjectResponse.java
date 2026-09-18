package com.una.embyhub.model.dto.response.douban;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class DoubanSimpleSubjectResponse implements Serializable {
   private String doubanId;
   private String title;
   private String originalTitle;
   private String releaseYear;
   private String rate;
   private String cover;
   private String background;
   private String url;
   private Boolean playable;
   private Boolean isNew;
   private List<String> types;
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
   public String getOriginalTitle() {
      return this.originalTitle;
   }

   @Generated
   public String getReleaseYear() {
      return this.releaseYear;
   }

   @Generated
   public String getRate() {
      return this.rate;
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
   public String getUrl() {
      return this.url;
   }

   @Generated
   public Boolean getPlayable() {
      return this.playable;
   }

   @Generated
   public Boolean getIsNew() {
      return this.isNew;
   }

   @Generated
   public List<String> getTypes() {
      return this.types;
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
   public void setOriginalTitle(final String originalTitle) {
      this.originalTitle = originalTitle;
   }

   @Generated
   public void setReleaseYear(final String releaseYear) {
      this.releaseYear = releaseYear;
   }

   @Generated
   public void setRate(final String rate) {
      this.rate = rate;
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
   public void setUrl(final String url) {
      this.url = url;
   }

   @Generated
   public void setPlayable(final Boolean playable) {
      this.playable = playable;
   }

   @Generated
   public void setIsNew(final Boolean isNew) {
      this.isNew = isNew;
   }

   @Generated
   public void setTypes(final List<String> types) {
      this.types = types;
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
      } else if (!(o instanceof DoubanSimpleSubjectResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$playable = this.getPlayable();
         Object other$playable = other.getPlayable();
         if (this$playable == null ? other$playable == null : this$playable.equals(other$playable)) {
            Object this$isNew = this.getIsNew();
            Object other$isNew = other.getIsNew();
            if (this$isNew == null ? other$isNew == null : this$isNew.equals(other$isNew)) {
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
                           Object this$originalTitle = this.getOriginalTitle();
                           Object other$originalTitle = other.getOriginalTitle();
                           if (this$originalTitle == null ? other$originalTitle == null : this$originalTitle.equals(other$originalTitle)) {
                              Object this$releaseYear = this.getReleaseYear();
                              Object other$releaseYear = other.getReleaseYear();
                              if (this$releaseYear == null ? other$releaseYear == null : this$releaseYear.equals(other$releaseYear)) {
                                 Object this$rate = this.getRate();
                                 Object other$rate = other.getRate();
                                 if (this$rate == null ? other$rate == null : this$rate.equals(other$rate)) {
                                    Object this$cover = this.getCover();
                                    Object other$cover = other.getCover();
                                    if (this$cover == null ? other$cover == null : this$cover.equals(other$cover)) {
                                       Object this$background = this.getBackground();
                                       Object other$background = other.getBackground();
                                       if (this$background == null ? other$background == null : this$background.equals(other$background)) {
                                          Object this$url = this.getUrl();
                                          Object other$url = other.getUrl();
                                          if (this$url == null ? other$url == null : this$url.equals(other$url)) {
                                             Object this$types = this.getTypes();
                                             Object other$types = other.getTypes();
                                             return this$types == null ? other$types == null : this$types.equals(other$types);
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
      return other instanceof DoubanSimpleSubjectResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $playable = this.getPlayable();
      result = result * 59 + ($playable == null ? 43 : $playable.hashCode());
      Object $isNew = this.getIsNew();
      result = result * 59 + ($isNew == null ? 43 : $isNew.hashCode());
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $inLibrary = this.getInLibrary();
      result = result * 59 + ($inLibrary == null ? 43 : $inLibrary.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $originalTitle = this.getOriginalTitle();
      result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
      Object $releaseYear = this.getReleaseYear();
      result = result * 59 + ($releaseYear == null ? 43 : $releaseYear.hashCode());
      Object $rate = this.getRate();
      result = result * 59 + ($rate == null ? 43 : $rate.hashCode());
      Object $cover = this.getCover();
      result = result * 59 + ($cover == null ? 43 : $cover.hashCode());
      Object $background = this.getBackground();
      result = result * 59 + ($background == null ? 43 : $background.hashCode());
      Object $url = this.getUrl();
      result = result * 59 + ($url == null ? 43 : $url.hashCode());
      Object $types = this.getTypes();
      return result * 59 + ($types == null ? 43 : $types.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DoubanSimpleSubjectResponse(doubanId="
         + this.getDoubanId()
         + ", title="
         + this.getTitle()
         + ", originalTitle="
         + this.getOriginalTitle()
         + ", releaseYear="
         + this.getReleaseYear()
         + ", rate="
         + this.getRate()
         + ", cover="
         + this.getCover()
         + ", background="
         + this.getBackground()
         + ", url="
         + this.getUrl()
         + ", playable="
         + this.getPlayable()
         + ", isNew="
         + this.getIsNew()
         + ", types="
         + this.getTypes()
         + ", tmdbId="
         + this.getTmdbId()
         + ", inLibrary="
         + this.getInLibrary()
         + ")";
   }
}
