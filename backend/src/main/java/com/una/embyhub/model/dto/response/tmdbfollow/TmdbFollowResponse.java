package com.una.embyhub.model.dto.response.tmdbfollow;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class TmdbFollowResponse implements Serializable {
   private Long id;
   private Integer tmdbId;
   private String mediaType;
   private String name;
   private String originalName;
   private String posterPath;
   private String backdropPath;
   private String overview;
   private Date nextAirDate;
   private Integer nextSeasonNumber;
   private Integer nextEpisodeNumber;
   private Integer lastNotifiedSeason;
   private Integer lastNotifiedEpisode;
   private String language;
   private List<String> notifyChannels;
   private Integer status;
   private Date lastSyncTime;
   private String subscriberName;
   private Date releaseDate;
   private Integer runtimeMinutes;
   private Integer progressSeason;
   private Integer progressEpisode;
   private String watcherName;
   private Integer latestSeasonNumber;
   private String latestSeasonName;
   private String latestSeasonOverview;
   private String latestSeasonPosterPath;
   private List<TmdbEpisodeResponse> latestSeasonEpisodes;
   private List<TmdbSeasonResponse> seasons;
   private Boolean movieWatched;
   private Boolean isSubscribed;

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
   public List<String> getNotifyChannels() {
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
   public Integer getProgressSeason() {
      return this.progressSeason;
   }

   @Generated
   public Integer getProgressEpisode() {
      return this.progressEpisode;
   }

   @Generated
   public String getWatcherName() {
      return this.watcherName;
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
   public List<TmdbEpisodeResponse> getLatestSeasonEpisodes() {
      return this.latestSeasonEpisodes;
   }

   @Generated
   public List<TmdbSeasonResponse> getSeasons() {
      return this.seasons;
   }

   @Generated
   public Boolean getMovieWatched() {
      return this.movieWatched;
   }

   @Generated
   public Boolean getIsSubscribed() {
      return this.isSubscribed;
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
   public void setNotifyChannels(final List<String> notifyChannels) {
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
   public void setProgressSeason(final Integer progressSeason) {
      this.progressSeason = progressSeason;
   }

   @Generated
   public void setProgressEpisode(final Integer progressEpisode) {
      this.progressEpisode = progressEpisode;
   }

   @Generated
   public void setWatcherName(final String watcherName) {
      this.watcherName = watcherName;
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
   public void setLatestSeasonEpisodes(final List<TmdbEpisodeResponse> latestSeasonEpisodes) {
      this.latestSeasonEpisodes = latestSeasonEpisodes;
   }

   @Generated
   public void setSeasons(final List<TmdbSeasonResponse> seasons) {
      this.seasons = seasons;
   }

   @Generated
   public void setMovieWatched(final Boolean movieWatched) {
      this.movieWatched = movieWatched;
   }

   @Generated
   public void setIsSubscribed(final Boolean isSubscribed) {
      this.isSubscribed = isSubscribed;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollowResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
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
                                 Object this$progressSeason = this.getProgressSeason();
                                 Object other$progressSeason = other.getProgressSeason();
                                 if (this$progressSeason == null ? other$progressSeason == null : this$progressSeason.equals(other$progressSeason)) {
                                    Object this$progressEpisode = this.getProgressEpisode();
                                    Object other$progressEpisode = other.getProgressEpisode();
                                    if (this$progressEpisode == null ? other$progressEpisode == null : this$progressEpisode.equals(other$progressEpisode)) {
                                       Object this$latestSeasonNumber = this.getLatestSeasonNumber();
                                       Object other$latestSeasonNumber = other.getLatestSeasonNumber();
                                       if (this$latestSeasonNumber == null
                                          ? other$latestSeasonNumber == null
                                          : this$latestSeasonNumber.equals(other$latestSeasonNumber)) {
                                          Object this$movieWatched = this.getMovieWatched();
                                          Object other$movieWatched = other.getMovieWatched();
                                          if (this$movieWatched == null ? other$movieWatched == null : this$movieWatched.equals(other$movieWatched)) {
                                             Object this$isSubscribed = this.getIsSubscribed();
                                             Object other$isSubscribed = other.getIsSubscribed();
                                             if (this$isSubscribed == null ? other$isSubscribed == null : this$isSubscribed.equals(other$isSubscribed)) {
                                                Object this$mediaType = this.getMediaType();
                                                Object other$mediaType = other.getMediaType();
                                                if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                                                   Object this$name = this.getName();
                                                   Object other$name = other.getName();
                                                   if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                                      Object this$originalName = this.getOriginalName();
                                                      Object other$originalName = other.getOriginalName();
                                                      if (this$originalName == null ? other$originalName == null : this$originalName.equals(other$originalName)
                                                         )
                                                       {
                                                         Object this$posterPath = this.getPosterPath();
                                                         Object other$posterPath = other.getPosterPath();
                                                         if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                                            Object this$backdropPath = this.getBackdropPath();
                                                            Object other$backdropPath = other.getBackdropPath();
                                                            if (this$backdropPath == null
                                                               ? other$backdropPath == null
                                                               : this$backdropPath.equals(other$backdropPath)) {
                                                               Object this$overview = this.getOverview();
                                                               Object other$overview = other.getOverview();
                                                               if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                                                  Object this$nextAirDate = this.getNextAirDate();
                                                                  Object other$nextAirDate = other.getNextAirDate();
                                                                  if (this$nextAirDate == null
                                                                     ? other$nextAirDate == null
                                                                     : this$nextAirDate.equals(other$nextAirDate)) {
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
                                                                                    Object this$watcherName = this.getWatcherName();
                                                                                    Object other$watcherName = other.getWatcherName();
                                                                                    if (this$watcherName == null
                                                                                       ? other$watcherName == null
                                                                                       : this$watcherName.equals(other$watcherName)) {
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
                                                                                             if (this$latestSeasonPosterPath == null
                                                                                                ? other$latestSeasonPosterPath == null
                                                                                                : this$latestSeasonPosterPath.equals(
                                                                                                   other$latestSeasonPosterPath
                                                                                                )) {
                                                                                                Object this$latestSeasonEpisodes = this.getLatestSeasonEpisodes();
                                                                                                Object other$latestSeasonEpisodes = other.getLatestSeasonEpisodes();
                                                                                                if (this$latestSeasonEpisodes == null
                                                                                                   ? other$latestSeasonEpisodes == null
                                                                                                   : this$latestSeasonEpisodes.equals(
                                                                                                      other$latestSeasonEpisodes
                                                                                                   )) {
                                                                                                   Object this$seasons = this.getSeasons();
                                                                                                   Object other$seasons = other.getSeasons();
                                                                                                   return this$seasons == null
                                                                                                      ? other$seasons == null
                                                                                                      : this$seasons.equals(other$seasons);
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
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbFollowResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
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
      Object $progressSeason = this.getProgressSeason();
      result = result * 59 + ($progressSeason == null ? 43 : $progressSeason.hashCode());
      Object $progressEpisode = this.getProgressEpisode();
      result = result * 59 + ($progressEpisode == null ? 43 : $progressEpisode.hashCode());
      Object $latestSeasonNumber = this.getLatestSeasonNumber();
      result = result * 59 + ($latestSeasonNumber == null ? 43 : $latestSeasonNumber.hashCode());
      Object $movieWatched = this.getMovieWatched();
      result = result * 59 + ($movieWatched == null ? 43 : $movieWatched.hashCode());
      Object $isSubscribed = this.getIsSubscribed();
      result = result * 59 + ($isSubscribed == null ? 43 : $isSubscribed.hashCode());
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
      Object $watcherName = this.getWatcherName();
      result = result * 59 + ($watcherName == null ? 43 : $watcherName.hashCode());
      Object $latestSeasonName = this.getLatestSeasonName();
      result = result * 59 + ($latestSeasonName == null ? 43 : $latestSeasonName.hashCode());
      Object $latestSeasonOverview = this.getLatestSeasonOverview();
      result = result * 59 + ($latestSeasonOverview == null ? 43 : $latestSeasonOverview.hashCode());
      Object $latestSeasonPosterPath = this.getLatestSeasonPosterPath();
      result = result * 59 + ($latestSeasonPosterPath == null ? 43 : $latestSeasonPosterPath.hashCode());
      Object $latestSeasonEpisodes = this.getLatestSeasonEpisodes();
      result = result * 59 + ($latestSeasonEpisodes == null ? 43 : $latestSeasonEpisodes.hashCode());
      Object $seasons = this.getSeasons();
      return result * 59 + ($seasons == null ? 43 : $seasons.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowResponse(id="
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
         + ", progressSeason="
         + this.getProgressSeason()
         + ", progressEpisode="
         + this.getProgressEpisode()
         + ", watcherName="
         + this.getWatcherName()
         + ", latestSeasonNumber="
         + this.getLatestSeasonNumber()
         + ", latestSeasonName="
         + this.getLatestSeasonName()
         + ", latestSeasonOverview="
         + this.getLatestSeasonOverview()
         + ", latestSeasonPosterPath="
         + this.getLatestSeasonPosterPath()
         + ", latestSeasonEpisodes="
         + this.getLatestSeasonEpisodes()
         + ", seasons="
         + this.getSeasons()
         + ", movieWatched="
         + this.getMovieWatched()
         + ", isSubscribed="
         + this.getIsSubscribed()
         + ")";
   }
}
