package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("tmdb_follow")
public class TmdbFollow extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      type = IdType.AUTO
   )
   private Long id;
   @TableField("tmdb_id")
   private Integer tmdbId;
   @TableField("media_type")
   private String mediaType;
   @TableField("name")
   private String name;
   @TableField("original_name")
   private String originalName;
   @TableField("poster_path")
   private String posterPath;
   @TableField("backdrop_path")
   private String backdropPath;
   @TableField("overview")
   private String overview;
   @TableField("next_air_date")
   private Date nextAirDate;
   @TableField("next_season_number")
   private Integer nextSeasonNumber;
   @TableField("next_episode_number")
   private Integer nextEpisodeNumber;
   @TableField("last_notified_season")
   private Integer lastNotifiedSeason;
   @TableField("last_notified_episode")
   private Integer lastNotifiedEpisode;
   @TableField("language")
   private String language;
   @TableField("notify_channels")
   private String notifyChannels;
   @TableField("status")
   private Integer status;
   @TableField("last_sync_time")
   private Date lastSyncTime;
   @TableField("subscriber_name")
   private String subscriberName;
   @TableField("release_date")
   private Date releaseDate;
   @TableField("runtime_minutes")
   private Integer runtimeMinutes;
   @TableField("release_notified")
   private Boolean releaseNotified;
   @TableField("latest_season_number")
   private Integer latestSeasonNumber;
   @TableField("latest_season_name")
   private String latestSeasonName;
   @TableField("latest_season_overview")
   private String latestSeasonOverview;
   @TableField("latest_season_poster_path")
   private String latestSeasonPosterPath;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getOriginalName() {
      return this.originalName;
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
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public Date getNextAirDate() {
      return this.nextAirDate;
   }

   @Generated
   public Integer getNextSeasonNumber() {
      return this.nextSeasonNumber;
   }

   @Generated
   public Integer getNextEpisodeNumber() {
      return this.nextEpisodeNumber;
   }

   @Generated
   public Integer getLastNotifiedSeason() {
      return this.lastNotifiedSeason;
   }

   @Generated
   public Integer getLastNotifiedEpisode() {
      return this.lastNotifiedEpisode;
   }

   @Generated
   public String getLanguage() {
      return this.language;
   }

   @Generated
   public String getNotifyChannels() {
      return this.notifyChannels;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public Date getLastSyncTime() {
      return this.lastSyncTime;
   }

   @Generated
   public String getSubscriberName() {
      return this.subscriberName;
   }

   @Generated
   public Date getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Integer getRuntimeMinutes() {
      return this.runtimeMinutes;
   }

   @Generated
   public Boolean getReleaseNotified() {
      return this.releaseNotified;
   }

   @Generated
   public Integer getLatestSeasonNumber() {
      return this.latestSeasonNumber;
   }

   @Generated
   public String getLatestSeasonName() {
      return this.latestSeasonName;
   }

   @Generated
   public String getLatestSeasonOverview() {
      return this.latestSeasonOverview;
   }

   @Generated
   public String getLatestSeasonPosterPath() {
      return this.latestSeasonPosterPath;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setOriginalName(final String originalName) {
      this.originalName = originalName;
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
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setNextAirDate(final Date nextAirDate) {
      this.nextAirDate = nextAirDate;
   }

   @Generated
   public void setNextSeasonNumber(final Integer nextSeasonNumber) {
      this.nextSeasonNumber = nextSeasonNumber;
   }

   @Generated
   public void setNextEpisodeNumber(final Integer nextEpisodeNumber) {
      this.nextEpisodeNumber = nextEpisodeNumber;
   }

   @Generated
   public void setLastNotifiedSeason(final Integer lastNotifiedSeason) {
      this.lastNotifiedSeason = lastNotifiedSeason;
   }

   @Generated
   public void setLastNotifiedEpisode(final Integer lastNotifiedEpisode) {
      this.lastNotifiedEpisode = lastNotifiedEpisode;
   }

   @Generated
   public void setLanguage(final String language) {
      this.language = language;
   }

   @Generated
   public void setNotifyChannels(final String notifyChannels) {
      this.notifyChannels = notifyChannels;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setLastSyncTime(final Date lastSyncTime) {
      this.lastSyncTime = lastSyncTime;
   }

   @Generated
   public void setSubscriberName(final String subscriberName) {
      this.subscriberName = subscriberName;
   }

   @Generated
   public void setReleaseDate(final Date releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setRuntimeMinutes(final Integer runtimeMinutes) {
      this.runtimeMinutes = runtimeMinutes;
   }

   @Generated
   public void setReleaseNotified(final Boolean releaseNotified) {
      this.releaseNotified = releaseNotified;
   }

   @Generated
   public void setLatestSeasonNumber(final Integer latestSeasonNumber) {
      this.latestSeasonNumber = latestSeasonNumber;
   }

   @Generated
   public void setLatestSeasonName(final String latestSeasonName) {
      this.latestSeasonName = latestSeasonName;
   }

   @Generated
   public void setLatestSeasonOverview(final String latestSeasonOverview) {
      this.latestSeasonOverview = latestSeasonOverview;
   }

   @Generated
   public void setLatestSeasonPosterPath(final String latestSeasonPosterPath) {
      this.latestSeasonPosterPath = latestSeasonPosterPath;
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollow(id="
         + this.getId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", mediaType="
         + this.getMediaType()
         + ", name="
         + this.getName()
         + ", originalName="
         + this.getOriginalName()
         + ", posterPath="
         + this.getPosterPath()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", overview="
         + this.getOverview()
         + ", nextAirDate="
         + this.getNextAirDate()
         + ", nextSeasonNumber="
         + this.getNextSeasonNumber()
         + ", nextEpisodeNumber="
         + this.getNextEpisodeNumber()
         + ", lastNotifiedSeason="
         + this.getLastNotifiedSeason()
         + ", lastNotifiedEpisode="
         + this.getLastNotifiedEpisode()
         + ", language="
         + this.getLanguage()
         + ", notifyChannels="
         + this.getNotifyChannels()
         + ", status="
         + this.getStatus()
         + ", lastSyncTime="
         + this.getLastSyncTime()
         + ", subscriberName="
         + this.getSubscriberName()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", runtimeMinutes="
         + this.getRuntimeMinutes()
         + ", releaseNotified="
         + this.getReleaseNotified()
         + ", latestSeasonNumber="
         + this.getLatestSeasonNumber()
         + ", latestSeasonName="
         + this.getLatestSeasonName()
         + ", latestSeasonOverview="
         + this.getLatestSeasonOverview()
         + ", latestSeasonPosterPath="
         + this.getLatestSeasonPosterPath()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollow other)) {
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
               Object this$nextSeasonNumber = this.getNextSeasonNumber();
               Object other$nextSeasonNumber = other.getNextSeasonNumber();
               if (this$nextSeasonNumber == null ? other$nextSeasonNumber == null : this$nextSeasonNumber.equals(other$nextSeasonNumber)) {
                  Object this$nextEpisodeNumber = this.getNextEpisodeNumber();
                  Object other$nextEpisodeNumber = other.getNextEpisodeNumber();
                  if (this$nextEpisodeNumber == null ? other$nextEpisodeNumber == null : this$nextEpisodeNumber.equals(other$nextEpisodeNumber)) {
                     Object this$lastNotifiedSeason = this.getLastNotifiedSeason();
                     Object other$lastNotifiedSeason = other.getLastNotifiedSeason();
                     if (this$lastNotifiedSeason == null ? other$lastNotifiedSeason == null : this$lastNotifiedSeason.equals(other$lastNotifiedSeason)) {
                        Object this$lastNotifiedEpisode = this.getLastNotifiedEpisode();
                        Object other$lastNotifiedEpisode = other.getLastNotifiedEpisode();
                        if (this$lastNotifiedEpisode == null ? other$lastNotifiedEpisode == null : this$lastNotifiedEpisode.equals(other$lastNotifiedEpisode)) {
                           Object this$status = this.getStatus();
                           Object other$status = other.getStatus();
                           if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                              Object this$runtimeMinutes = this.getRuntimeMinutes();
                              Object other$runtimeMinutes = other.getRuntimeMinutes();
                              if (this$runtimeMinutes == null ? other$runtimeMinutes == null : this$runtimeMinutes.equals(other$runtimeMinutes)) {
                                 Object this$releaseNotified = this.getReleaseNotified();
                                 Object other$releaseNotified = other.getReleaseNotified();
                                 if (this$releaseNotified == null ? other$releaseNotified == null : this$releaseNotified.equals(other$releaseNotified)) {
                                    Object this$latestSeasonNumber = this.getLatestSeasonNumber();
                                    Object other$latestSeasonNumber = other.getLatestSeasonNumber();
                                    if (this$latestSeasonNumber == null
                                       ? other$latestSeasonNumber == null
                                       : this$latestSeasonNumber.equals(other$latestSeasonNumber)) {
                                       Object this$mediaType = this.getMediaType();
                                       Object other$mediaType = other.getMediaType();
                                       if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                                          Object this$name = this.getName();
                                          Object other$name = other.getName();
                                          if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                             Object this$originalName = this.getOriginalName();
                                             Object other$originalName = other.getOriginalName();
                                             if (this$originalName == null ? other$originalName == null : this$originalName.equals(other$originalName)) {
                                                Object this$posterPath = this.getPosterPath();
                                                Object other$posterPath = other.getPosterPath();
                                                if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                                   Object this$backdropPath = this.getBackdropPath();
                                                   Object other$backdropPath = other.getBackdropPath();
                                                   if (this$backdropPath == null ? other$backdropPath == null : this$backdropPath.equals(other$backdropPath)) {
                                                      Object this$overview = this.getOverview();
                                                      Object other$overview = other.getOverview();
                                                      if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                                         Object this$nextAirDate = this.getNextAirDate();
                                                         Object other$nextAirDate = other.getNextAirDate();
                                                         if (this$nextAirDate == null ? other$nextAirDate == null : this$nextAirDate.equals(other$nextAirDate)) {
                                                            Object this$language = this.getLanguage();
                                                            Object other$language = other.getLanguage();
                                                            if (this$language == null ? other$language == null : this$language.equals(other$language)) {
                                                               Object this$notifyChannels = this.getNotifyChannels();
                                                               Object other$notifyChannels = other.getNotifyChannels();
                                                               if (this$notifyChannels == null
                                                                  ? other$notifyChannels == null
                                                                  : this$notifyChannels.equals(other$notifyChannels)) {
                                                                  Object this$lastSyncTime = this.getLastSyncTime();
                                                                  Object other$lastSyncTime = other.getLastSyncTime();
                                                                  if (this$lastSyncTime == null
                                                                     ? other$lastSyncTime == null
                                                                     : this$lastSyncTime.equals(other$lastSyncTime)) {
                                                                     Object this$subscriberName = this.getSubscriberName();
                                                                     Object other$subscriberName = other.getSubscriberName();
                                                                     if (this$subscriberName == null
                                                                        ? other$subscriberName == null
                                                                        : this$subscriberName.equals(other$subscriberName)) {
                                                                        Object this$releaseDate = this.getReleaseDate();
                                                                        Object other$releaseDate = other.getReleaseDate();
                                                                        if (this$releaseDate == null
                                                                           ? other$releaseDate == null
                                                                           : this$releaseDate.equals(other$releaseDate)) {
                                                                           Object this$latestSeasonName = this.getLatestSeasonName();
                                                                           Object other$latestSeasonName = other.getLatestSeasonName();
                                                                           if (this$latestSeasonName == null
                                                                              ? other$latestSeasonName == null
                                                                              : this$latestSeasonName.equals(other$latestSeasonName)) {
                                                                              Object this$latestSeasonOverview = this.getLatestSeasonOverview();
                                                                              Object other$latestSeasonOverview = other.getLatestSeasonOverview();
                                                                              if (this$latestSeasonOverview == null
                                                                                 ? other$latestSeasonOverview == null
                                                                                 : this$latestSeasonOverview.equals(other$latestSeasonOverview)) {
                                                                                 Object this$latestSeasonPosterPath = this.getLatestSeasonPosterPath();
                                                                                 Object other$latestSeasonPosterPath = other.getLatestSeasonPosterPath();
                                                                                 return this$latestSeasonPosterPath == null
                                                                                    ? other$latestSeasonPosterPath == null
                                                                                    : this$latestSeasonPosterPath.equals(other$latestSeasonPosterPath);
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
      return other instanceof TmdbFollow;
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
      Object $nextSeasonNumber = this.getNextSeasonNumber();
      result = result * 59 + ($nextSeasonNumber == null ? 43 : $nextSeasonNumber.hashCode());
      Object $nextEpisodeNumber = this.getNextEpisodeNumber();
      result = result * 59 + ($nextEpisodeNumber == null ? 43 : $nextEpisodeNumber.hashCode());
      Object $lastNotifiedSeason = this.getLastNotifiedSeason();
      result = result * 59 + ($lastNotifiedSeason == null ? 43 : $lastNotifiedSeason.hashCode());
      Object $lastNotifiedEpisode = this.getLastNotifiedEpisode();
      result = result * 59 + ($lastNotifiedEpisode == null ? 43 : $lastNotifiedEpisode.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $runtimeMinutes = this.getRuntimeMinutes();
      result = result * 59 + ($runtimeMinutes == null ? 43 : $runtimeMinutes.hashCode());
      Object $releaseNotified = this.getReleaseNotified();
      result = result * 59 + ($releaseNotified == null ? 43 : $releaseNotified.hashCode());
      Object $latestSeasonNumber = this.getLatestSeasonNumber();
      result = result * 59 + ($latestSeasonNumber == null ? 43 : $latestSeasonNumber.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $originalName = this.getOriginalName();
      result = result * 59 + ($originalName == null ? 43 : $originalName.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $nextAirDate = this.getNextAirDate();
      result = result * 59 + ($nextAirDate == null ? 43 : $nextAirDate.hashCode());
      Object $language = this.getLanguage();
      result = result * 59 + ($language == null ? 43 : $language.hashCode());
      Object $notifyChannels = this.getNotifyChannels();
      result = result * 59 + ($notifyChannels == null ? 43 : $notifyChannels.hashCode());
      Object $lastSyncTime = this.getLastSyncTime();
      result = result * 59 + ($lastSyncTime == null ? 43 : $lastSyncTime.hashCode());
      Object $subscriberName = this.getSubscriberName();
      result = result * 59 + ($subscriberName == null ? 43 : $subscriberName.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $latestSeasonName = this.getLatestSeasonName();
      result = result * 59 + ($latestSeasonName == null ? 43 : $latestSeasonName.hashCode());
      Object $latestSeasonOverview = this.getLatestSeasonOverview();
      result = result * 59 + ($latestSeasonOverview == null ? 43 : $latestSeasonOverview.hashCode());
      Object $latestSeasonPosterPath = this.getLatestSeasonPosterPath();
      return result * 59 + ($latestSeasonPosterPath == null ? 43 : $latestSeasonPosterPath.hashCode());
   }
}
