package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Generated;

@TableName("tmdb_daily_release")
public class TmdbDailyRelease extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      type = IdType.AUTO
   )
   private Long id;
   @TableField("publish_date")
   private Date publishDate;
   @TableField("media_type")
   private String mediaType;
   @TableField("tmdb_id")
   private Integer tmdbId;
   @TableField("title")
   private String title;
   @TableField("original_title")
   private String originalTitle;
   @TableField("year")
   private Integer year;
   @TableField("overview")
   private String overview;
   @TableField("poster_path")
   private String posterPath;
   @TableField("backdrop_path")
   private String backdropPath;
   @TableField("tmdb_url")
   private String tmdbUrl;
   @TableField("release_date")
   private Date releaseDate;
   @TableField("first_air_date")
   private Date firstAirDate;
   @TableField("season_number")
   private Integer seasonNumber;
   @TableField("episode_start")
   private Integer episodeStart;
   @TableField("episode_end")
   private Integer episodeEnd;
   @TableField("episode_display")
   private String episodeDisplay;
   @TableField("vote_average")
   private BigDecimal voteAverage;
   @TableField("vote_count")
   private Integer voteCount;
   @TableField("popularity")
   private BigDecimal popularity;
   @TableField("origin_country")
   private String originCountry;
   @TableField("original_language")
   private String originalLanguage;
   @TableField("rank_no")
   private Integer rankNo;
   @TableField("source")
   private String source;
   @TableField("raw_json")
   private String rawJson;
   @TableField("telegram_group_sent")
   private Integer telegramGroupSent;
   @TableField("telegram_bot_sent")
   private Integer telegramBotSent;
   @TableField("wechat_sent")
   private Integer wechatSent;
   @TableField("wechat_bot_sent")
   private Integer wechatBotSent;
   @TableField("last_notify_time")
   private Date lastNotifyTime;
   @TableField("last_error")
   private String lastError;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Date getPublishDate() {
      return this.publishDate;
   }

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
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
   public Integer getYear() {
      return this.year;
   }

   @Generated
   public String getOverview() {
      return this.overview;
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
   public String getTmdbUrl() {
      return this.tmdbUrl;
   }

   @Generated
   public Date getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Date getFirstAirDate() {
      return this.firstAirDate;
   }

   @Generated
   public Integer getSeasonNumber() {
      return this.seasonNumber;
   }

   @Generated
   public Integer getEpisodeStart() {
      return this.episodeStart;
   }

   @Generated
   public Integer getEpisodeEnd() {
      return this.episodeEnd;
   }

   @Generated
   public String getEpisodeDisplay() {
      return this.episodeDisplay;
   }

   @Generated
   public BigDecimal getVoteAverage() {
      return this.voteAverage;
   }

   @Generated
   public Integer getVoteCount() {
      return this.voteCount;
   }

   @Generated
   public BigDecimal getPopularity() {
      return this.popularity;
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
   public Integer getRankNo() {
      return this.rankNo;
   }

   @Generated
   public String getSource() {
      return this.source;
   }

   @Generated
   public String getRawJson() {
      return this.rawJson;
   }

   @Generated
   public Integer getTelegramGroupSent() {
      return this.telegramGroupSent;
   }

   @Generated
   public Integer getTelegramBotSent() {
      return this.telegramBotSent;
   }

   @Generated
   public Integer getWechatSent() {
      return this.wechatSent;
   }

   @Generated
   public Integer getWechatBotSent() {
      return this.wechatBotSent;
   }

   @Generated
   public Date getLastNotifyTime() {
      return this.lastNotifyTime;
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
   public void setPublishDate(final Date publishDate) {
      this.publishDate = publishDate;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
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
   public void setYear(final Integer year) {
      this.year = year;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
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
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
   }

   @Generated
   public void setReleaseDate(final Date releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setFirstAirDate(final Date firstAirDate) {
      this.firstAirDate = firstAirDate;
   }

   @Generated
   public void setSeasonNumber(final Integer seasonNumber) {
      this.seasonNumber = seasonNumber;
   }

   @Generated
   public void setEpisodeStart(final Integer episodeStart) {
      this.episodeStart = episodeStart;
   }

   @Generated
   public void setEpisodeEnd(final Integer episodeEnd) {
      this.episodeEnd = episodeEnd;
   }

   @Generated
   public void setEpisodeDisplay(final String episodeDisplay) {
      this.episodeDisplay = episodeDisplay;
   }

   @Generated
   public void setVoteAverage(final BigDecimal voteAverage) {
      this.voteAverage = voteAverage;
   }

   @Generated
   public void setVoteCount(final Integer voteCount) {
      this.voteCount = voteCount;
   }

   @Generated
   public void setPopularity(final BigDecimal popularity) {
      this.popularity = popularity;
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
   public void setRankNo(final Integer rankNo) {
      this.rankNo = rankNo;
   }

   @Generated
   public void setSource(final String source) {
      this.source = source;
   }

   @Generated
   public void setRawJson(final String rawJson) {
      this.rawJson = rawJson;
   }

   @Generated
   public void setTelegramGroupSent(final Integer telegramGroupSent) {
      this.telegramGroupSent = telegramGroupSent;
   }

   @Generated
   public void setTelegramBotSent(final Integer telegramBotSent) {
      this.telegramBotSent = telegramBotSent;
   }

   @Generated
   public void setWechatSent(final Integer wechatSent) {
      this.wechatSent = wechatSent;
   }

   @Generated
   public void setWechatBotSent(final Integer wechatBotSent) {
      this.wechatBotSent = wechatBotSent;
   }

   @Generated
   public void setLastNotifyTime(final Date lastNotifyTime) {
      this.lastNotifyTime = lastNotifyTime;
   }

   @Generated
   public void setLastError(final String lastError) {
      this.lastError = lastError;
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbDailyRelease(id="
         + this.getId()
         + ", publishDate="
         + this.getPublishDate()
         + ", mediaType="
         + this.getMediaType()
         + ", tmdbId="
         + this.getTmdbId()
         + ", title="
         + this.getTitle()
         + ", originalTitle="
         + this.getOriginalTitle()
         + ", year="
         + this.getYear()
         + ", overview="
         + this.getOverview()
         + ", posterPath="
         + this.getPosterPath()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", firstAirDate="
         + this.getFirstAirDate()
         + ", seasonNumber="
         + this.getSeasonNumber()
         + ", episodeStart="
         + this.getEpisodeStart()
         + ", episodeEnd="
         + this.getEpisodeEnd()
         + ", episodeDisplay="
         + this.getEpisodeDisplay()
         + ", voteAverage="
         + this.getVoteAverage()
         + ", voteCount="
         + this.getVoteCount()
         + ", popularity="
         + this.getPopularity()
         + ", originCountry="
         + this.getOriginCountry()
         + ", originalLanguage="
         + this.getOriginalLanguage()
         + ", rankNo="
         + this.getRankNo()
         + ", source="
         + this.getSource()
         + ", rawJson="
         + this.getRawJson()
         + ", telegramGroupSent="
         + this.getTelegramGroupSent()
         + ", telegramBotSent="
         + this.getTelegramBotSent()
         + ", wechatSent="
         + this.getWechatSent()
         + ", wechatBotSent="
         + this.getWechatBotSent()
         + ", lastNotifyTime="
         + this.getLastNotifyTime()
         + ", lastError="
         + this.getLastError()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbDailyRelease other)) {
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
               Object this$year = this.getYear();
               Object other$year = other.getYear();
               if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                  Object this$seasonNumber = this.getSeasonNumber();
                  Object other$seasonNumber = other.getSeasonNumber();
                  if (this$seasonNumber == null ? other$seasonNumber == null : this$seasonNumber.equals(other$seasonNumber)) {
                     Object this$episodeStart = this.getEpisodeStart();
                     Object other$episodeStart = other.getEpisodeStart();
                     if (this$episodeStart == null ? other$episodeStart == null : this$episodeStart.equals(other$episodeStart)) {
                        Object this$episodeEnd = this.getEpisodeEnd();
                        Object other$episodeEnd = other.getEpisodeEnd();
                        if (this$episodeEnd == null ? other$episodeEnd == null : this$episodeEnd.equals(other$episodeEnd)) {
                           Object this$voteCount = this.getVoteCount();
                           Object other$voteCount = other.getVoteCount();
                           if (this$voteCount == null ? other$voteCount == null : this$voteCount.equals(other$voteCount)) {
                              Object this$rankNo = this.getRankNo();
                              Object other$rankNo = other.getRankNo();
                              if (this$rankNo == null ? other$rankNo == null : this$rankNo.equals(other$rankNo)) {
                                 Object this$telegramGroupSent = this.getTelegramGroupSent();
                                 Object other$telegramGroupSent = other.getTelegramGroupSent();
                                 if (this$telegramGroupSent == null ? other$telegramGroupSent == null : this$telegramGroupSent.equals(other$telegramGroupSent)) {
                                    Object this$telegramBotSent = this.getTelegramBotSent();
                                    Object other$telegramBotSent = other.getTelegramBotSent();
                                    if (this$telegramBotSent == null ? other$telegramBotSent == null : this$telegramBotSent.equals(other$telegramBotSent)) {
                                       Object this$wechatSent = this.getWechatSent();
                                       Object other$wechatSent = other.getWechatSent();
                                       if (this$wechatSent == null ? other$wechatSent == null : this$wechatSent.equals(other$wechatSent)) {
                                          Object this$wechatBotSent = this.getWechatBotSent();
                                          Object other$wechatBotSent = other.getWechatBotSent();
                                          if (this$wechatBotSent == null ? other$wechatBotSent == null : this$wechatBotSent.equals(other$wechatBotSent)) {
                                             Object this$publishDate = this.getPublishDate();
                                             Object other$publishDate = other.getPublishDate();
                                             if (this$publishDate == null ? other$publishDate == null : this$publishDate.equals(other$publishDate)) {
                                                Object this$mediaType = this.getMediaType();
                                                Object other$mediaType = other.getMediaType();
                                                if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                                                   Object this$title = this.getTitle();
                                                   Object other$title = other.getTitle();
                                                   if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                                      Object this$originalTitle = this.getOriginalTitle();
                                                      Object other$originalTitle = other.getOriginalTitle();
                                                      if (this$originalTitle == null
                                                         ? other$originalTitle == null
                                                         : this$originalTitle.equals(other$originalTitle)) {
                                                         Object this$overview = this.getOverview();
                                                         Object other$overview = other.getOverview();
                                                         if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                                            Object this$posterPath = this.getPosterPath();
                                                            Object other$posterPath = other.getPosterPath();
                                                            if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                                               Object this$backdropPath = this.getBackdropPath();
                                                               Object other$backdropPath = other.getBackdropPath();
                                                               if (this$backdropPath == null
                                                                  ? other$backdropPath == null
                                                                  : this$backdropPath.equals(other$backdropPath)) {
                                                                  Object this$tmdbUrl = this.getTmdbUrl();
                                                                  Object other$tmdbUrl = other.getTmdbUrl();
                                                                  if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                                                                     Object this$releaseDate = this.getReleaseDate();
                                                                     Object other$releaseDate = other.getReleaseDate();
                                                                     if (this$releaseDate == null
                                                                        ? other$releaseDate == null
                                                                        : this$releaseDate.equals(other$releaseDate)) {
                                                                        Object this$firstAirDate = this.getFirstAirDate();
                                                                        Object other$firstAirDate = other.getFirstAirDate();
                                                                        if (this$firstAirDate == null
                                                                           ? other$firstAirDate == null
                                                                           : this$firstAirDate.equals(other$firstAirDate)) {
                                                                           Object this$episodeDisplay = this.getEpisodeDisplay();
                                                                           Object other$episodeDisplay = other.getEpisodeDisplay();
                                                                           if (this$episodeDisplay == null
                                                                              ? other$episodeDisplay == null
                                                                              : this$episodeDisplay.equals(other$episodeDisplay)) {
                                                                              Object this$voteAverage = this.getVoteAverage();
                                                                              Object other$voteAverage = other.getVoteAverage();
                                                                              if (this$voteAverage == null
                                                                                 ? other$voteAverage == null
                                                                                 : this$voteAverage.equals(other$voteAverage)) {
                                                                                 Object this$popularity = this.getPopularity();
                                                                                 Object other$popularity = other.getPopularity();
                                                                                 if (this$popularity == null
                                                                                    ? other$popularity == null
                                                                                    : this$popularity.equals(other$popularity)) {
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
                                                                                          Object this$source = this.getSource();
                                                                                          Object other$source = other.getSource();
                                                                                          if (this$source == null
                                                                                             ? other$source == null
                                                                                             : this$source.equals(other$source)) {
                                                                                             Object this$rawJson = this.getRawJson();
                                                                                             Object other$rawJson = other.getRawJson();
                                                                                             if (this$rawJson == null
                                                                                                ? other$rawJson == null
                                                                                                : this$rawJson.equals(other$rawJson)) {
                                                                                                Object this$lastNotifyTime = this.getLastNotifyTime();
                                                                                                Object other$lastNotifyTime = other.getLastNotifyTime();
                                                                                                if (this$lastNotifyTime == null
                                                                                                   ? other$lastNotifyTime == null
                                                                                                   : this$lastNotifyTime.equals(other$lastNotifyTime)) {
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
      return other instanceof TmdbDailyRelease;
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
      Object $year = this.getYear();
      result = result * 59 + ($year == null ? 43 : $year.hashCode());
      Object $seasonNumber = this.getSeasonNumber();
      result = result * 59 + ($seasonNumber == null ? 43 : $seasonNumber.hashCode());
      Object $episodeStart = this.getEpisodeStart();
      result = result * 59 + ($episodeStart == null ? 43 : $episodeStart.hashCode());
      Object $episodeEnd = this.getEpisodeEnd();
      result = result * 59 + ($episodeEnd == null ? 43 : $episodeEnd.hashCode());
      Object $voteCount = this.getVoteCount();
      result = result * 59 + ($voteCount == null ? 43 : $voteCount.hashCode());
      Object $rankNo = this.getRankNo();
      result = result * 59 + ($rankNo == null ? 43 : $rankNo.hashCode());
      Object $telegramGroupSent = this.getTelegramGroupSent();
      result = result * 59 + ($telegramGroupSent == null ? 43 : $telegramGroupSent.hashCode());
      Object $telegramBotSent = this.getTelegramBotSent();
      result = result * 59 + ($telegramBotSent == null ? 43 : $telegramBotSent.hashCode());
      Object $wechatSent = this.getWechatSent();
      result = result * 59 + ($wechatSent == null ? 43 : $wechatSent.hashCode());
      Object $wechatBotSent = this.getWechatBotSent();
      result = result * 59 + ($wechatBotSent == null ? 43 : $wechatBotSent.hashCode());
      Object $publishDate = this.getPublishDate();
      result = result * 59 + ($publishDate == null ? 43 : $publishDate.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $originalTitle = this.getOriginalTitle();
      result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $firstAirDate = this.getFirstAirDate();
      result = result * 59 + ($firstAirDate == null ? 43 : $firstAirDate.hashCode());
      Object $episodeDisplay = this.getEpisodeDisplay();
      result = result * 59 + ($episodeDisplay == null ? 43 : $episodeDisplay.hashCode());
      Object $voteAverage = this.getVoteAverage();
      result = result * 59 + ($voteAverage == null ? 43 : $voteAverage.hashCode());
      Object $popularity = this.getPopularity();
      result = result * 59 + ($popularity == null ? 43 : $popularity.hashCode());
      Object $originCountry = this.getOriginCountry();
      result = result * 59 + ($originCountry == null ? 43 : $originCountry.hashCode());
      Object $originalLanguage = this.getOriginalLanguage();
      result = result * 59 + ($originalLanguage == null ? 43 : $originalLanguage.hashCode());
      Object $source = this.getSource();
      result = result * 59 + ($source == null ? 43 : $source.hashCode());
      Object $rawJson = this.getRawJson();
      result = result * 59 + ($rawJson == null ? 43 : $rawJson.hashCode());
      Object $lastNotifyTime = this.getLastNotifyTime();
      result = result * 59 + ($lastNotifyTime == null ? 43 : $lastNotifyTime.hashCode());
      Object $lastError = this.getLastError();
      return result * 59 + ($lastError == null ? 43 : $lastError.hashCode());
   }
}
