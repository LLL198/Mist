package com.una.embyhub.model.dto.response.moviepilot;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Generated;

public class MoviePilotSubscriptionItemResponse {
   private Long id;
   private String name;
   private String year;
   private String type;
   private String keyword;
   private Long tmdbid;
   private String doubanid;
   private String bangumiid;
   private String mediaid;
   private Integer season;
   private String poster;
   private String backdrop;
   private Integer vote;
   private String description;
   private String filter;
   private String include;
   private String exclude;
   private String quality;
   private String resolution;
   private String effect;
   @JSONField(
      name = "total_episode"
   )
   private Integer totalEpisode;
   @JSONField(
      name = "start_episode"
   )
   private Integer startEpisode;
   @JSONField(
      name = "lack_episode"
   )
   private Integer lackEpisode;
   private String note;
   private String state;
   @JSONField(
      name = "last_update"
   )
   private String lastUpdate;
   private String username;
   private String sites;
   private String downloader;
   @JSONField(
      name = "best_version"
   )
   private Integer bestVersion;
   @JSONField(
      name = "current_priority"
   )
   private Integer currentPriority;
   @JSONField(
      name = "save_path"
   )
   private String savePath;
   @JSONField(
      name = "search_imdbid"
   )
   private Integer searchImdbid;
   private String date;
   @JSONField(
      name = "custom_words"
   )
   private String customWords;
   @JSONField(
      name = "media_category"
   )
   private String mediaCategory;
   @JSONField(
      name = "filter_groups"
   )
   private String filterGroups;
   @JSONField(
      name = "episode_group"
   )
   private String episodeGroup;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getYear() {
      return this.year;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public Long getTmdbid() {
      return this.tmdbid;
   }

   @Generated
   public String getDoubanid() {
      return this.doubanid;
   }

   @Generated
   public String getBangumiid() {
      return this.bangumiid;
   }

   @Generated
   public String getMediaid() {
      return this.mediaid;
   }

   @Generated
   public Integer getSeason() {
      return this.season;
   }

   @Generated
   public String getPoster() {
      return this.poster;
   }

   @Generated
   public String getBackdrop() {
      return this.backdrop;
   }

   @Generated
   public Integer getVote() {
      return this.vote;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public String getFilter() {
      return this.filter;
   }

   @Generated
   public String getInclude() {
      return this.include;
   }

   @Generated
   public String getExclude() {
      return this.exclude;
   }

   @Generated
   public String getQuality() {
      return this.quality;
   }

   @Generated
   public String getResolution() {
      return this.resolution;
   }

   @Generated
   public String getEffect() {
      return this.effect;
   }

   @Generated
   public Integer getTotalEpisode() {
      return this.totalEpisode;
   }

   @Generated
   public Integer getStartEpisode() {
      return this.startEpisode;
   }

   @Generated
   public Integer getLackEpisode() {
      return this.lackEpisode;
   }

   @Generated
   public String getNote() {
      return this.note;
   }

   @Generated
   public String getState() {
      return this.state;
   }

   @Generated
   public String getLastUpdate() {
      return this.lastUpdate;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getSites() {
      return this.sites;
   }

   @Generated
   public String getDownloader() {
      return this.downloader;
   }

   @Generated
   public Integer getBestVersion() {
      return this.bestVersion;
   }

   @Generated
   public Integer getCurrentPriority() {
      return this.currentPriority;
   }

   @Generated
   public String getSavePath() {
      return this.savePath;
   }

   @Generated
   public Integer getSearchImdbid() {
      return this.searchImdbid;
   }

   @Generated
   public String getDate() {
      return this.date;
   }

   @Generated
   public String getCustomWords() {
      return this.customWords;
   }

   @Generated
   public String getMediaCategory() {
      return this.mediaCategory;
   }

   @Generated
   public String getFilterGroups() {
      return this.filterGroups;
   }

   @Generated
   public String getEpisodeGroup() {
      return this.episodeGroup;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setYear(final String year) {
      this.year = year;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setTmdbid(final Long tmdbid) {
      this.tmdbid = tmdbid;
   }

   @Generated
   public void setDoubanid(final String doubanid) {
      this.doubanid = doubanid;
   }

   @Generated
   public void setBangumiid(final String bangumiid) {
      this.bangumiid = bangumiid;
   }

   @Generated
   public void setMediaid(final String mediaid) {
      this.mediaid = mediaid;
   }

   @Generated
   public void setSeason(final Integer season) {
      this.season = season;
   }

   @Generated
   public void setPoster(final String poster) {
      this.poster = poster;
   }

   @Generated
   public void setBackdrop(final String backdrop) {
      this.backdrop = backdrop;
   }

   @Generated
   public void setVote(final Integer vote) {
      this.vote = vote;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setFilter(final String filter) {
      this.filter = filter;
   }

   @Generated
   public void setInclude(final String include) {
      this.include = include;
   }

   @Generated
   public void setExclude(final String exclude) {
      this.exclude = exclude;
   }

   @Generated
   public void setQuality(final String quality) {
      this.quality = quality;
   }

   @Generated
   public void setResolution(final String resolution) {
      this.resolution = resolution;
   }

   @Generated
   public void setEffect(final String effect) {
      this.effect = effect;
   }

   @Generated
   public void setTotalEpisode(final Integer totalEpisode) {
      this.totalEpisode = totalEpisode;
   }

   @Generated
   public void setStartEpisode(final Integer startEpisode) {
      this.startEpisode = startEpisode;
   }

   @Generated
   public void setLackEpisode(final Integer lackEpisode) {
      this.lackEpisode = lackEpisode;
   }

   @Generated
   public void setNote(final String note) {
      this.note = note;
   }

   @Generated
   public void setState(final String state) {
      this.state = state;
   }

   @Generated
   public void setLastUpdate(final String lastUpdate) {
      this.lastUpdate = lastUpdate;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setSites(final String sites) {
      this.sites = sites;
   }

   @Generated
   public void setDownloader(final String downloader) {
      this.downloader = downloader;
   }

   @Generated
   public void setBestVersion(final Integer bestVersion) {
      this.bestVersion = bestVersion;
   }

   @Generated
   public void setCurrentPriority(final Integer currentPriority) {
      this.currentPriority = currentPriority;
   }

   @Generated
   public void setSavePath(final String savePath) {
      this.savePath = savePath;
   }

   @Generated
   public void setSearchImdbid(final Integer searchImdbid) {
      this.searchImdbid = searchImdbid;
   }

   @Generated
   public void setDate(final String date) {
      this.date = date;
   }

   @Generated
   public void setCustomWords(final String customWords) {
      this.customWords = customWords;
   }

   @Generated
   public void setMediaCategory(final String mediaCategory) {
      this.mediaCategory = mediaCategory;
   }

   @Generated
   public void setFilterGroups(final String filterGroups) {
      this.filterGroups = filterGroups;
   }

   @Generated
   public void setEpisodeGroup(final String episodeGroup) {
      this.episodeGroup = episodeGroup;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePilotSubscriptionItemResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$tmdbid = this.getTmdbid();
            Object other$tmdbid = other.getTmdbid();
            if (this$tmdbid == null ? other$tmdbid == null : this$tmdbid.equals(other$tmdbid)) {
               Object this$season = this.getSeason();
               Object other$season = other.getSeason();
               if (this$season == null ? other$season == null : this$season.equals(other$season)) {
                  Object this$vote = this.getVote();
                  Object other$vote = other.getVote();
                  if (this$vote == null ? other$vote == null : this$vote.equals(other$vote)) {
                     Object this$totalEpisode = this.getTotalEpisode();
                     Object other$totalEpisode = other.getTotalEpisode();
                     if (this$totalEpisode == null ? other$totalEpisode == null : this$totalEpisode.equals(other$totalEpisode)) {
                        Object this$startEpisode = this.getStartEpisode();
                        Object other$startEpisode = other.getStartEpisode();
                        if (this$startEpisode == null ? other$startEpisode == null : this$startEpisode.equals(other$startEpisode)) {
                           Object this$lackEpisode = this.getLackEpisode();
                           Object other$lackEpisode = other.getLackEpisode();
                           if (this$lackEpisode == null ? other$lackEpisode == null : this$lackEpisode.equals(other$lackEpisode)) {
                              Object this$bestVersion = this.getBestVersion();
                              Object other$bestVersion = other.getBestVersion();
                              if (this$bestVersion == null ? other$bestVersion == null : this$bestVersion.equals(other$bestVersion)) {
                                 Object this$currentPriority = this.getCurrentPriority();
                                 Object other$currentPriority = other.getCurrentPriority();
                                 if (this$currentPriority == null ? other$currentPriority == null : this$currentPriority.equals(other$currentPriority)) {
                                    Object this$searchImdbid = this.getSearchImdbid();
                                    Object other$searchImdbid = other.getSearchImdbid();
                                    if (this$searchImdbid == null ? other$searchImdbid == null : this$searchImdbid.equals(other$searchImdbid)) {
                                       Object this$name = this.getName();
                                       Object other$name = other.getName();
                                       if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                          Object this$year = this.getYear();
                                          Object other$year = other.getYear();
                                          if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                                             Object this$type = this.getType();
                                             Object other$type = other.getType();
                                             if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                                Object this$keyword = this.getKeyword();
                                                Object other$keyword = other.getKeyword();
                                                if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                                                   Object this$doubanid = this.getDoubanid();
                                                   Object other$doubanid = other.getDoubanid();
                                                   if (this$doubanid == null ? other$doubanid == null : this$doubanid.equals(other$doubanid)) {
                                                      Object this$bangumiid = this.getBangumiid();
                                                      Object other$bangumiid = other.getBangumiid();
                                                      if (this$bangumiid == null ? other$bangumiid == null : this$bangumiid.equals(other$bangumiid)) {
                                                         Object this$mediaid = this.getMediaid();
                                                         Object other$mediaid = other.getMediaid();
                                                         if (this$mediaid == null ? other$mediaid == null : this$mediaid.equals(other$mediaid)) {
                                                            Object this$poster = this.getPoster();
                                                            Object other$poster = other.getPoster();
                                                            if (this$poster == null ? other$poster == null : this$poster.equals(other$poster)) {
                                                               Object this$backdrop = this.getBackdrop();
                                                               Object other$backdrop = other.getBackdrop();
                                                               if (this$backdrop == null ? other$backdrop == null : this$backdrop.equals(other$backdrop)) {
                                                                  Object this$description = this.getDescription();
                                                                  Object other$description = other.getDescription();
                                                                  if (this$description == null
                                                                     ? other$description == null
                                                                     : this$description.equals(other$description)) {
                                                                     Object this$filter = this.getFilter();
                                                                     Object other$filter = other.getFilter();
                                                                     if (this$filter == null ? other$filter == null : this$filter.equals(other$filter)) {
                                                                        Object this$include = this.getInclude();
                                                                        Object other$include = other.getInclude();
                                                                        if (this$include == null ? other$include == null : this$include.equals(other$include)) {
                                                                           Object this$exclude = this.getExclude();
                                                                           Object other$exclude = other.getExclude();
                                                                           if (this$exclude == null
                                                                              ? other$exclude == null
                                                                              : this$exclude.equals(other$exclude)) {
                                                                              Object this$quality = this.getQuality();
                                                                              Object other$quality = other.getQuality();
                                                                              if (this$quality == null
                                                                                 ? other$quality == null
                                                                                 : this$quality.equals(other$quality)) {
                                                                                 Object this$resolution = this.getResolution();
                                                                                 Object other$resolution = other.getResolution();
                                                                                 if (this$resolution == null
                                                                                    ? other$resolution == null
                                                                                    : this$resolution.equals(other$resolution)) {
                                                                                    Object this$effect = this.getEffect();
                                                                                    Object other$effect = other.getEffect();
                                                                                    if (this$effect == null
                                                                                       ? other$effect == null
                                                                                       : this$effect.equals(other$effect)) {
                                                                                       Object this$note = this.getNote();
                                                                                       Object other$note = other.getNote();
                                                                                       if (this$note == null
                                                                                          ? other$note == null
                                                                                          : this$note.equals(other$note)) {
                                                                                          Object this$state = this.getState();
                                                                                          Object other$state = other.getState();
                                                                                          if (this$state == null
                                                                                             ? other$state == null
                                                                                             : this$state.equals(other$state)) {
                                                                                             Object this$lastUpdate = this.getLastUpdate();
                                                                                             Object other$lastUpdate = other.getLastUpdate();
                                                                                             if (this$lastUpdate == null
                                                                                                ? other$lastUpdate == null
                                                                                                : this$lastUpdate.equals(other$lastUpdate)) {
                                                                                                Object this$username = this.getUsername();
                                                                                                Object other$username = other.getUsername();
                                                                                                if (this$username == null
                                                                                                   ? other$username == null
                                                                                                   : this$username.equals(other$username)) {
                                                                                                   Object this$sites = this.getSites();
                                                                                                   Object other$sites = other.getSites();
                                                                                                   if (this$sites == null
                                                                                                      ? other$sites == null
                                                                                                      : this$sites.equals(other$sites)) {
                                                                                                      Object this$downloader = this.getDownloader();
                                                                                                      Object other$downloader = other.getDownloader();
                                                                                                      if (this$downloader == null
                                                                                                         ? other$downloader == null
                                                                                                         : this$downloader.equals(other$downloader)) {
                                                                                                         Object this$savePath = this.getSavePath();
                                                                                                         Object other$savePath = other.getSavePath();
                                                                                                         if (this$savePath == null
                                                                                                            ? other$savePath == null
                                                                                                            : this$savePath.equals(other$savePath)) {
                                                                                                            Object this$date = this.getDate();
                                                                                                            Object other$date = other.getDate();
                                                                                                            if (this$date == null
                                                                                                               ? other$date == null
                                                                                                               : this$date.equals(other$date)) {
                                                                                                               Object this$customWords = this.getCustomWords();
                                                                                                               Object other$customWords = other.getCustomWords();
                                                                                                               if (this$customWords == null
                                                                                                                  ? other$customWords == null
                                                                                                                  : this$customWords.equals(other$customWords)) {
                                                                                                                  Object this$mediaCategory = this.getMediaCategory();
                                                                                                                  Object other$mediaCategory = other.getMediaCategory();
                                                                                                                  if (this$mediaCategory == null
                                                                                                                     ? other$mediaCategory == null
                                                                                                                     : this$mediaCategory.equals(
                                                                                                                        other$mediaCategory
                                                                                                                     )) {
                                                                                                                     Object this$filterGroups = this.getFilterGroups();
                                                                                                                     Object other$filterGroups = other.getFilterGroups();
                                                                                                                     if (this$filterGroups == null
                                                                                                                        ? other$filterGroups == null
                                                                                                                        : this$filterGroups.equals(
                                                                                                                           other$filterGroups
                                                                                                                        )) {
                                                                                                                        Object this$episodeGroup = this.getEpisodeGroup();
                                                                                                                        Object other$episodeGroup = other.getEpisodeGroup();
                                                                                                                        return this$episodeGroup == null
                                                                                                                           ? other$episodeGroup == null
                                                                                                                           : this$episodeGroup.equals(
                                                                                                                              other$episodeGroup
                                                                                                                           );
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
      return other instanceof MoviePilotSubscriptionItemResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $tmdbid = this.getTmdbid();
      result = result * 59 + ($tmdbid == null ? 43 : $tmdbid.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $vote = this.getVote();
      result = result * 59 + ($vote == null ? 43 : $vote.hashCode());
      Object $totalEpisode = this.getTotalEpisode();
      result = result * 59 + ($totalEpisode == null ? 43 : $totalEpisode.hashCode());
      Object $startEpisode = this.getStartEpisode();
      result = result * 59 + ($startEpisode == null ? 43 : $startEpisode.hashCode());
      Object $lackEpisode = this.getLackEpisode();
      result = result * 59 + ($lackEpisode == null ? 43 : $lackEpisode.hashCode());
      Object $bestVersion = this.getBestVersion();
      result = result * 59 + ($bestVersion == null ? 43 : $bestVersion.hashCode());
      Object $currentPriority = this.getCurrentPriority();
      result = result * 59 + ($currentPriority == null ? 43 : $currentPriority.hashCode());
      Object $searchImdbid = this.getSearchImdbid();
      result = result * 59 + ($searchImdbid == null ? 43 : $searchImdbid.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $year = this.getYear();
      result = result * 59 + ($year == null ? 43 : $year.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $doubanid = this.getDoubanid();
      result = result * 59 + ($doubanid == null ? 43 : $doubanid.hashCode());
      Object $bangumiid = this.getBangumiid();
      result = result * 59 + ($bangumiid == null ? 43 : $bangumiid.hashCode());
      Object $mediaid = this.getMediaid();
      result = result * 59 + ($mediaid == null ? 43 : $mediaid.hashCode());
      Object $poster = this.getPoster();
      result = result * 59 + ($poster == null ? 43 : $poster.hashCode());
      Object $backdrop = this.getBackdrop();
      result = result * 59 + ($backdrop == null ? 43 : $backdrop.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
      Object $filter = this.getFilter();
      result = result * 59 + ($filter == null ? 43 : $filter.hashCode());
      Object $include = this.getInclude();
      result = result * 59 + ($include == null ? 43 : $include.hashCode());
      Object $exclude = this.getExclude();
      result = result * 59 + ($exclude == null ? 43 : $exclude.hashCode());
      Object $quality = this.getQuality();
      result = result * 59 + ($quality == null ? 43 : $quality.hashCode());
      Object $resolution = this.getResolution();
      result = result * 59 + ($resolution == null ? 43 : $resolution.hashCode());
      Object $effect = this.getEffect();
      result = result * 59 + ($effect == null ? 43 : $effect.hashCode());
      Object $note = this.getNote();
      result = result * 59 + ($note == null ? 43 : $note.hashCode());
      Object $state = this.getState();
      result = result * 59 + ($state == null ? 43 : $state.hashCode());
      Object $lastUpdate = this.getLastUpdate();
      result = result * 59 + ($lastUpdate == null ? 43 : $lastUpdate.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $sites = this.getSites();
      result = result * 59 + ($sites == null ? 43 : $sites.hashCode());
      Object $downloader = this.getDownloader();
      result = result * 59 + ($downloader == null ? 43 : $downloader.hashCode());
      Object $savePath = this.getSavePath();
      result = result * 59 + ($savePath == null ? 43 : $savePath.hashCode());
      Object $date = this.getDate();
      result = result * 59 + ($date == null ? 43 : $date.hashCode());
      Object $customWords = this.getCustomWords();
      result = result * 59 + ($customWords == null ? 43 : $customWords.hashCode());
      Object $mediaCategory = this.getMediaCategory();
      result = result * 59 + ($mediaCategory == null ? 43 : $mediaCategory.hashCode());
      Object $filterGroups = this.getFilterGroups();
      result = result * 59 + ($filterGroups == null ? 43 : $filterGroups.hashCode());
      Object $episodeGroup = this.getEpisodeGroup();
      return result * 59 + ($episodeGroup == null ? 43 : $episodeGroup.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePilotSubscriptionItemResponse(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", year="
         + this.getYear()
         + ", type="
         + this.getType()
         + ", keyword="
         + this.getKeyword()
         + ", tmdbid="
         + this.getTmdbid()
         + ", doubanid="
         + this.getDoubanid()
         + ", bangumiid="
         + this.getBangumiid()
         + ", mediaid="
         + this.getMediaid()
         + ", season="
         + this.getSeason()
         + ", poster="
         + this.getPoster()
         + ", backdrop="
         + this.getBackdrop()
         + ", vote="
         + this.getVote()
         + ", description="
         + this.getDescription()
         + ", filter="
         + this.getFilter()
         + ", include="
         + this.getInclude()
         + ", exclude="
         + this.getExclude()
         + ", quality="
         + this.getQuality()
         + ", resolution="
         + this.getResolution()
         + ", effect="
         + this.getEffect()
         + ", totalEpisode="
         + this.getTotalEpisode()
         + ", startEpisode="
         + this.getStartEpisode()
         + ", lackEpisode="
         + this.getLackEpisode()
         + ", note="
         + this.getNote()
         + ", state="
         + this.getState()
         + ", lastUpdate="
         + this.getLastUpdate()
         + ", username="
         + this.getUsername()
         + ", sites="
         + this.getSites()
         + ", downloader="
         + this.getDownloader()
         + ", bestVersion="
         + this.getBestVersion()
         + ", currentPriority="
         + this.getCurrentPriority()
         + ", savePath="
         + this.getSavePath()
         + ", searchImdbid="
         + this.getSearchImdbid()
         + ", date="
         + this.getDate()
         + ", customWords="
         + this.getCustomWords()
         + ", mediaCategory="
         + this.getMediaCategory()
         + ", filterGroups="
         + this.getFilterGroups()
         + ", episodeGroup="
         + this.getEpisodeGroup()
         + ")";
   }
}
