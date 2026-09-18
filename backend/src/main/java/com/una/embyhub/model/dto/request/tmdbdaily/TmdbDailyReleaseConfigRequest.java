package com.una.embyhub.model.dto.request.tmdbdaily;

import java.io.Serializable;
import lombok.Generated;

public class TmdbDailyReleaseConfigRequest implements Serializable {
   private Boolean enabled = false;
   private Boolean includeMovies = true;
   private Boolean includeTv = true;
   private Integer limit = 15;
   private String region = "CN,US,GB";
   private String timezone = "Asia/Shanghai";
   private String language = "zh-CN";
   private String releaseTypes = "2|3";
   private String originCountry = "";
   private String originalLanguage = "";
   private Boolean telegramGroupEnabled = true;
   private String telegramGroupTarget = "channel";
   private Boolean telegramBotEnabled = false;
   private Boolean wechatEnabled = true;
   private Boolean wechatBotEnabled = false;
   private Boolean sendWhenEmpty = false;
   private Integer queryDelayMillis = 500;
   private Integer maxPages = 3;

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public Boolean getIncludeMovies() {
      return this.includeMovies;
   }

   @Generated
   public Boolean getIncludeTv() {
      return this.includeTv;
   }

   @Generated
   public Integer getLimit() {
      return this.limit;
   }

   @Generated
   public String getRegion() {
      return this.region;
   }

   @Generated
   public String getTimezone() {
      return this.timezone;
   }

   @Generated
   public String getLanguage() {
      return this.language;
   }

   @Generated
   public String getReleaseTypes() {
      return this.releaseTypes;
   }

   @Generated
   public String getOriginCountry() {
      return this.originCountry;
   }

   @Generated
   public String getOriginalLanguage() {
      return this.originalLanguage;
   }

   @Generated
   public Boolean getTelegramGroupEnabled() {
      return this.telegramGroupEnabled;
   }

   @Generated
   public String getTelegramGroupTarget() {
      return this.telegramGroupTarget;
   }

   @Generated
   public Boolean getTelegramBotEnabled() {
      return this.telegramBotEnabled;
   }

   @Generated
   public Boolean getWechatEnabled() {
      return this.wechatEnabled;
   }

   @Generated
   public Boolean getWechatBotEnabled() {
      return this.wechatBotEnabled;
   }

   @Generated
   public Boolean getSendWhenEmpty() {
      return this.sendWhenEmpty;
   }

   @Generated
   public Integer getQueryDelayMillis() {
      return this.queryDelayMillis;
   }

   @Generated
   public Integer getMaxPages() {
      return this.maxPages;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setIncludeMovies(final Boolean includeMovies) {
      this.includeMovies = includeMovies;
   }

   @Generated
   public void setIncludeTv(final Boolean includeTv) {
      this.includeTv = includeTv;
   }

   @Generated
   public void setLimit(final Integer limit) {
      this.limit = limit;
   }

   @Generated
   public void setRegion(final String region) {
      this.region = region;
   }

   @Generated
   public void setTimezone(final String timezone) {
      this.timezone = timezone;
   }

   @Generated
   public void setLanguage(final String language) {
      this.language = language;
   }

   @Generated
   public void setReleaseTypes(final String releaseTypes) {
      this.releaseTypes = releaseTypes;
   }

   @Generated
   public void setOriginCountry(final String originCountry) {
      this.originCountry = originCountry;
   }

   @Generated
   public void setOriginalLanguage(final String originalLanguage) {
      this.originalLanguage = originalLanguage;
   }

   @Generated
   public void setTelegramGroupEnabled(final Boolean telegramGroupEnabled) {
      this.telegramGroupEnabled = telegramGroupEnabled;
   }

   @Generated
   public void setTelegramGroupTarget(final String telegramGroupTarget) {
      this.telegramGroupTarget = telegramGroupTarget;
   }

   @Generated
   public void setTelegramBotEnabled(final Boolean telegramBotEnabled) {
      this.telegramBotEnabled = telegramBotEnabled;
   }

   @Generated
   public void setWechatEnabled(final Boolean wechatEnabled) {
      this.wechatEnabled = wechatEnabled;
   }

   @Generated
   public void setWechatBotEnabled(final Boolean wechatBotEnabled) {
      this.wechatBotEnabled = wechatBotEnabled;
   }

   @Generated
   public void setSendWhenEmpty(final Boolean sendWhenEmpty) {
      this.sendWhenEmpty = sendWhenEmpty;
   }

   @Generated
   public void setQueryDelayMillis(final Integer queryDelayMillis) {
      this.queryDelayMillis = queryDelayMillis;
   }

   @Generated
   public void setMaxPages(final Integer maxPages) {
      this.maxPages = maxPages;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbDailyReleaseConfigRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$includeMovies = this.getIncludeMovies();
            Object other$includeMovies = other.getIncludeMovies();
            if (this$includeMovies == null ? other$includeMovies == null : this$includeMovies.equals(other$includeMovies)) {
               Object this$includeTv = this.getIncludeTv();
               Object other$includeTv = other.getIncludeTv();
               if (this$includeTv == null ? other$includeTv == null : this$includeTv.equals(other$includeTv)) {
                  Object this$limit = this.getLimit();
                  Object other$limit = other.getLimit();
                  if (this$limit == null ? other$limit == null : this$limit.equals(other$limit)) {
                     Object this$telegramGroupEnabled = this.getTelegramGroupEnabled();
                     Object other$telegramGroupEnabled = other.getTelegramGroupEnabled();
                     if (this$telegramGroupEnabled == null ? other$telegramGroupEnabled == null : this$telegramGroupEnabled.equals(other$telegramGroupEnabled)) {
                        Object this$telegramBotEnabled = this.getTelegramBotEnabled();
                        Object other$telegramBotEnabled = other.getTelegramBotEnabled();
                        if (this$telegramBotEnabled == null ? other$telegramBotEnabled == null : this$telegramBotEnabled.equals(other$telegramBotEnabled)) {
                           Object this$wechatEnabled = this.getWechatEnabled();
                           Object other$wechatEnabled = other.getWechatEnabled();
                           if (this$wechatEnabled == null ? other$wechatEnabled == null : this$wechatEnabled.equals(other$wechatEnabled)) {
                              Object this$wechatBotEnabled = this.getWechatBotEnabled();
                              Object other$wechatBotEnabled = other.getWechatBotEnabled();
                              if (this$wechatBotEnabled == null ? other$wechatBotEnabled == null : this$wechatBotEnabled.equals(other$wechatBotEnabled)) {
                                 Object this$sendWhenEmpty = this.getSendWhenEmpty();
                                 Object other$sendWhenEmpty = other.getSendWhenEmpty();
                                 if (this$sendWhenEmpty == null ? other$sendWhenEmpty == null : this$sendWhenEmpty.equals(other$sendWhenEmpty)) {
                                    Object this$queryDelayMillis = this.getQueryDelayMillis();
                                    Object other$queryDelayMillis = other.getQueryDelayMillis();
                                    if (this$queryDelayMillis == null ? other$queryDelayMillis == null : this$queryDelayMillis.equals(other$queryDelayMillis)) {
                                       Object this$maxPages = this.getMaxPages();
                                       Object other$maxPages = other.getMaxPages();
                                       if (this$maxPages == null ? other$maxPages == null : this$maxPages.equals(other$maxPages)) {
                                          Object this$region = this.getRegion();
                                          Object other$region = other.getRegion();
                                          if (this$region == null ? other$region == null : this$region.equals(other$region)) {
                                             Object this$timezone = this.getTimezone();
                                             Object other$timezone = other.getTimezone();
                                             if (this$timezone == null ? other$timezone == null : this$timezone.equals(other$timezone)) {
                                                Object this$language = this.getLanguage();
                                                Object other$language = other.getLanguage();
                                                if (this$language == null ? other$language == null : this$language.equals(other$language)) {
                                                   Object this$releaseTypes = this.getReleaseTypes();
                                                   Object other$releaseTypes = other.getReleaseTypes();
                                                   if (this$releaseTypes == null ? other$releaseTypes == null : this$releaseTypes.equals(other$releaseTypes)) {
                                                      Object this$originCountry = this.getOriginCountry();
                                                      Object other$originCountry = other.getOriginCountry();
                                                      if (this$originCountry == null
                                                         ? other$originCountry == null
                                                         : this$originCountry.equals(other$originCountry)) {
                                                         Object this$originalLanguage = this.getOriginalLanguage();
                                                         Object other$originalLanguage = other.getOriginalLanguage();
                                                         if (this$originalLanguage == null
                                                            ? other$originalLanguage == null
                                                            : this$originalLanguage.equals(other$originalLanguage)) {
                                                            Object this$telegramGroupTarget = this.getTelegramGroupTarget();
                                                            Object other$telegramGroupTarget = other.getTelegramGroupTarget();
                                                            return this$telegramGroupTarget == null
                                                               ? other$telegramGroupTarget == null
                                                               : this$telegramGroupTarget.equals(other$telegramGroupTarget);
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
      return other instanceof TmdbDailyReleaseConfigRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $includeMovies = this.getIncludeMovies();
      result = result * 59 + ($includeMovies == null ? 43 : $includeMovies.hashCode());
      Object $includeTv = this.getIncludeTv();
      result = result * 59 + ($includeTv == null ? 43 : $includeTv.hashCode());
      Object $limit = this.getLimit();
      result = result * 59 + ($limit == null ? 43 : $limit.hashCode());
      Object $telegramGroupEnabled = this.getTelegramGroupEnabled();
      result = result * 59 + ($telegramGroupEnabled == null ? 43 : $telegramGroupEnabled.hashCode());
      Object $telegramBotEnabled = this.getTelegramBotEnabled();
      result = result * 59 + ($telegramBotEnabled == null ? 43 : $telegramBotEnabled.hashCode());
      Object $wechatEnabled = this.getWechatEnabled();
      result = result * 59 + ($wechatEnabled == null ? 43 : $wechatEnabled.hashCode());
      Object $wechatBotEnabled = this.getWechatBotEnabled();
      result = result * 59 + ($wechatBotEnabled == null ? 43 : $wechatBotEnabled.hashCode());
      Object $sendWhenEmpty = this.getSendWhenEmpty();
      result = result * 59 + ($sendWhenEmpty == null ? 43 : $sendWhenEmpty.hashCode());
      Object $queryDelayMillis = this.getQueryDelayMillis();
      result = result * 59 + ($queryDelayMillis == null ? 43 : $queryDelayMillis.hashCode());
      Object $maxPages = this.getMaxPages();
      result = result * 59 + ($maxPages == null ? 43 : $maxPages.hashCode());
      Object $region = this.getRegion();
      result = result * 59 + ($region == null ? 43 : $region.hashCode());
      Object $timezone = this.getTimezone();
      result = result * 59 + ($timezone == null ? 43 : $timezone.hashCode());
      Object $language = this.getLanguage();
      result = result * 59 + ($language == null ? 43 : $language.hashCode());
      Object $releaseTypes = this.getReleaseTypes();
      result = result * 59 + ($releaseTypes == null ? 43 : $releaseTypes.hashCode());
      Object $originCountry = this.getOriginCountry();
      result = result * 59 + ($originCountry == null ? 43 : $originCountry.hashCode());
      Object $originalLanguage = this.getOriginalLanguage();
      result = result * 59 + ($originalLanguage == null ? 43 : $originalLanguage.hashCode());
      Object $telegramGroupTarget = this.getTelegramGroupTarget();
      return result * 59 + ($telegramGroupTarget == null ? 43 : $telegramGroupTarget.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbDailyReleaseConfigRequest(enabled="
         + this.getEnabled()
         + ", includeMovies="
         + this.getIncludeMovies()
         + ", includeTv="
         + this.getIncludeTv()
         + ", limit="
         + this.getLimit()
         + ", region="
         + this.getRegion()
         + ", timezone="
         + this.getTimezone()
         + ", language="
         + this.getLanguage()
         + ", releaseTypes="
         + this.getReleaseTypes()
         + ", originCountry="
         + this.getOriginCountry()
         + ", originalLanguage="
         + this.getOriginalLanguage()
         + ", telegramGroupEnabled="
         + this.getTelegramGroupEnabled()
         + ", telegramGroupTarget="
         + this.getTelegramGroupTarget()
         + ", telegramBotEnabled="
         + this.getTelegramBotEnabled()
         + ", wechatEnabled="
         + this.getWechatEnabled()
         + ", wechatBotEnabled="
         + this.getWechatBotEnabled()
         + ", sendWhenEmpty="
         + this.getSendWhenEmpty()
         + ", queryDelayMillis="
         + this.getQueryDelayMillis()
         + ", maxPages="
         + this.getMaxPages()
         + ")";
   }
}
