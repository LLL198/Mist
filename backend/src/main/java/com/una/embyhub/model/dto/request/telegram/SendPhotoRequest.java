package com.una.embyhub.model.dto.request.telegram;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SendPhotoRequest implements Serializable {
   private String chatId;
   private String name;
   private String tvInfo;
   private String overview;
   private String tmdbUrl;
   private String imgUrl;
   private InputStream imgUrlInputStream;
   private String parseMode = "Markdown";
   private Integer productionYear;
   private String genres;
   private String Type;
   private String displayTitle;
   private String Size;
   private String audioQuality;
   private String subtitleInfo;
   private Double voteAverage;
   private Integer voteCount;
   private int episodeNumber;
   private String seriesName;
   private int seasonNumber;
   private String episodeName;
   private String backdropPath;
   private String serverUrl;
   private String serverName;
   private String playUser;
   private String playTitle;
   private String userLocation;
   private String playTime;
   private String playPosition;
   private String clientInfo;
   private TelegramClient telegramClient;
   private String caption;
   private Map<String, String> extraVariables = new HashMap<>();
   private Integer runtime;
   private String productionCountries;
   private String releaseDate;

   public void setOverview(String overview) {
      this.overview = overview;
      if (!StringUtils.hasText(overview)) {
         this.overview = "本影片暂无简介，可能是因为影片还没有详情内容，请点击链接查看详情";
      }
   }

   @Generated
   public String getChatId() {
      return this.chatId;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getTvInfo() {
      return this.tvInfo;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public String getTmdbUrl() {
      return this.tmdbUrl;
   }

   @Generated
   public String getImgUrl() {
      return this.imgUrl;
   }

   @Generated
   public InputStream getImgUrlInputStream() {
      return this.imgUrlInputStream;
   }

   @Generated
   public String getParseMode() {
      return this.parseMode;
   }

   @Generated
   public Integer getProductionYear() {
      return this.productionYear;
   }

   @Generated
   public String getGenres() {
      return this.genres;
   }

   @Generated
   public String getType() {
      return this.Type;
   }

   @Generated
   public String getDisplayTitle() {
      return this.displayTitle;
   }

   @Generated
   public String getSize() {
      return this.Size;
   }

   @Generated
   public String getAudioQuality() {
      return this.audioQuality;
   }

   @Generated
   public String getSubtitleInfo() {
      return this.subtitleInfo;
   }

   @Generated
   public Double getVoteAverage() {
      return this.voteAverage;
   }

   @Generated
   public Integer getVoteCount() {
      return this.voteCount;
   }

   @Generated
   public int getEpisodeNumber() {
      return this.episodeNumber;
   }

   @Generated
   public String getSeriesName() {
      return this.seriesName;
   }

   @Generated
   public int getSeasonNumber() {
      return this.seasonNumber;
   }

   @Generated
   public String getEpisodeName() {
      return this.episodeName;
   }

   @Generated
   public String getBackdropPath() {
      return this.backdropPath;
   }

   @Generated
   public String getServerUrl() {
      return this.serverUrl;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public String getPlayUser() {
      return this.playUser;
   }

   @Generated
   public String getPlayTitle() {
      return this.playTitle;
   }

   @Generated
   public String getUserLocation() {
      return this.userLocation;
   }

   @Generated
   public String getPlayTime() {
      return this.playTime;
   }

   @Generated
   public String getPlayPosition() {
      return this.playPosition;
   }

   @Generated
   public String getClientInfo() {
      return this.clientInfo;
   }

   @Generated
   public TelegramClient getTelegramClient() {
      return this.telegramClient;
   }

   @Generated
   public String getCaption() {
      return this.caption;
   }

   @Generated
   public Map<String, String> getExtraVariables() {
      return this.extraVariables;
   }

   @Generated
   public Integer getRuntime() {
      return this.runtime;
   }

   @Generated
   public String getProductionCountries() {
      return this.productionCountries;
   }

   @Generated
   public String getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public void setChatId(final String chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setTvInfo(final String tvInfo) {
      this.tvInfo = tvInfo;
   }

   @Generated
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
   }

   @Generated
   public void setImgUrl(final String imgUrl) {
      this.imgUrl = imgUrl;
   }

   @Generated
   public void setImgUrlInputStream(final InputStream imgUrlInputStream) {
      this.imgUrlInputStream = imgUrlInputStream;
   }

   @Generated
   public void setParseMode(final String parseMode) {
      this.parseMode = parseMode;
   }

   @Generated
   public void setProductionYear(final Integer productionYear) {
      this.productionYear = productionYear;
   }

   @Generated
   public void setGenres(final String genres) {
      this.genres = genres;
   }

   @Generated
   public void setType(final String Type) {
      this.Type = Type;
   }

   @Generated
   public void setDisplayTitle(final String displayTitle) {
      this.displayTitle = displayTitle;
   }

   @Generated
   public void setSize(final String Size) {
      this.Size = Size;
   }

   @Generated
   public void setAudioQuality(final String audioQuality) {
      this.audioQuality = audioQuality;
   }

   @Generated
   public void setSubtitleInfo(final String subtitleInfo) {
      this.subtitleInfo = subtitleInfo;
   }

   @Generated
   public void setVoteAverage(final Double voteAverage) {
      this.voteAverage = voteAverage;
   }

   @Generated
   public void setVoteCount(final Integer voteCount) {
      this.voteCount = voteCount;
   }

   @Generated
   public void setEpisodeNumber(final int episodeNumber) {
      this.episodeNumber = episodeNumber;
   }

   @Generated
   public void setSeriesName(final String seriesName) {
      this.seriesName = seriesName;
   }

   @Generated
   public void setSeasonNumber(final int seasonNumber) {
      this.seasonNumber = seasonNumber;
   }

   @Generated
   public void setEpisodeName(final String episodeName) {
      this.episodeName = episodeName;
   }

   @Generated
   public void setBackdropPath(final String backdropPath) {
      this.backdropPath = backdropPath;
   }

   @Generated
   public void setServerUrl(final String serverUrl) {
      this.serverUrl = serverUrl;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setPlayUser(final String playUser) {
      this.playUser = playUser;
   }

   @Generated
   public void setPlayTitle(final String playTitle) {
      this.playTitle = playTitle;
   }

   @Generated
   public void setUserLocation(final String userLocation) {
      this.userLocation = userLocation;
   }

   @Generated
   public void setPlayTime(final String playTime) {
      this.playTime = playTime;
   }

   @Generated
   public void setPlayPosition(final String playPosition) {
      this.playPosition = playPosition;
   }

   @Generated
   public void setClientInfo(final String clientInfo) {
      this.clientInfo = clientInfo;
   }

   @Generated
   public void setTelegramClient(final TelegramClient telegramClient) {
      this.telegramClient = telegramClient;
   }

   @Generated
   public void setCaption(final String caption) {
      this.caption = caption;
   }

   @Generated
   public void setExtraVariables(final Map<String, String> extraVariables) {
      this.extraVariables = extraVariables;
   }

   @Generated
   public void setRuntime(final Integer runtime) {
      this.runtime = runtime;
   }

   @Generated
   public void setProductionCountries(final String productionCountries) {
      this.productionCountries = productionCountries;
   }

   @Generated
   public void setReleaseDate(final String releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SendPhotoRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getEpisodeNumber() != other.getEpisodeNumber()) {
         return false;
      } else if (this.getSeasonNumber() != other.getSeasonNumber()) {
         return false;
      } else {
         Object this$productionYear = this.getProductionYear();
         Object other$productionYear = other.getProductionYear();
         if (this$productionYear == null ? other$productionYear == null : this$productionYear.equals(other$productionYear)) {
            Object this$voteAverage = this.getVoteAverage();
            Object other$voteAverage = other.getVoteAverage();
            if (this$voteAverage == null ? other$voteAverage == null : this$voteAverage.equals(other$voteAverage)) {
               Object this$voteCount = this.getVoteCount();
               Object other$voteCount = other.getVoteCount();
               if (this$voteCount == null ? other$voteCount == null : this$voteCount.equals(other$voteCount)) {
                  Object this$runtime = this.getRuntime();
                  Object other$runtime = other.getRuntime();
                  if (this$runtime == null ? other$runtime == null : this$runtime.equals(other$runtime)) {
                     Object this$chatId = this.getChatId();
                     Object other$chatId = other.getChatId();
                     if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
                        Object this$name = this.getName();
                        Object other$name = other.getName();
                        if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                           Object this$tvInfo = this.getTvInfo();
                           Object other$tvInfo = other.getTvInfo();
                           if (this$tvInfo == null ? other$tvInfo == null : this$tvInfo.equals(other$tvInfo)) {
                              Object this$overview = this.getOverview();
                              Object other$overview = other.getOverview();
                              if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                 Object this$tmdbUrl = this.getTmdbUrl();
                                 Object other$tmdbUrl = other.getTmdbUrl();
                                 if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                                    Object this$imgUrl = this.getImgUrl();
                                    Object other$imgUrl = other.getImgUrl();
                                    if (this$imgUrl == null ? other$imgUrl == null : this$imgUrl.equals(other$imgUrl)) {
                                       Object this$imgUrlInputStream = this.getImgUrlInputStream();
                                       Object other$imgUrlInputStream = other.getImgUrlInputStream();
                                       if (this$imgUrlInputStream == null
                                          ? other$imgUrlInputStream == null
                                          : this$imgUrlInputStream.equals(other$imgUrlInputStream)) {
                                          Object this$parseMode = this.getParseMode();
                                          Object other$parseMode = other.getParseMode();
                                          if (this$parseMode == null ? other$parseMode == null : this$parseMode.equals(other$parseMode)) {
                                             Object this$genres = this.getGenres();
                                             Object other$genres = other.getGenres();
                                             if (this$genres == null ? other$genres == null : this$genres.equals(other$genres)) {
                                                Object this$Type = this.getType();
                                                Object other$Type = other.getType();
                                                if (this$Type == null ? other$Type == null : this$Type.equals(other$Type)) {
                                                   Object this$displayTitle = this.getDisplayTitle();
                                                   Object other$displayTitle = other.getDisplayTitle();
                                                   if (this$displayTitle == null ? other$displayTitle == null : this$displayTitle.equals(other$displayTitle)) {
                                                      Object this$Size = this.getSize();
                                                      Object other$Size = other.getSize();
                                                      if (this$Size == null ? other$Size == null : this$Size.equals(other$Size)) {
                                                         Object this$audioQuality = this.getAudioQuality();
                                                         Object other$audioQuality = other.getAudioQuality();
                                                         if (this$audioQuality == null
                                                            ? other$audioQuality == null
                                                            : this$audioQuality.equals(other$audioQuality)) {
                                                            Object this$subtitleInfo = this.getSubtitleInfo();
                                                            Object other$subtitleInfo = other.getSubtitleInfo();
                                                            if (this$subtitleInfo == null
                                                               ? other$subtitleInfo == null
                                                               : this$subtitleInfo.equals(other$subtitleInfo)) {
                                                               Object this$seriesName = this.getSeriesName();
                                                               Object other$seriesName = other.getSeriesName();
                                                               if (this$seriesName == null
                                                                  ? other$seriesName == null
                                                                  : this$seriesName.equals(other$seriesName)) {
                                                                  Object this$episodeName = this.getEpisodeName();
                                                                  Object other$episodeName = other.getEpisodeName();
                                                                  if (this$episodeName == null
                                                                     ? other$episodeName == null
                                                                     : this$episodeName.equals(other$episodeName)) {
                                                                     Object this$backdropPath = this.getBackdropPath();
                                                                     Object other$backdropPath = other.getBackdropPath();
                                                                     if (this$backdropPath == null
                                                                        ? other$backdropPath == null
                                                                        : this$backdropPath.equals(other$backdropPath)) {
                                                                        Object this$serverUrl = this.getServerUrl();
                                                                        Object other$serverUrl = other.getServerUrl();
                                                                        if (this$serverUrl == null
                                                                           ? other$serverUrl == null
                                                                           : this$serverUrl.equals(other$serverUrl)) {
                                                                           Object this$serverName = this.getServerName();
                                                                           Object other$serverName = other.getServerName();
                                                                           if (this$serverName == null
                                                                              ? other$serverName == null
                                                                              : this$serverName.equals(other$serverName)) {
                                                                              Object this$playUser = this.getPlayUser();
                                                                              Object other$playUser = other.getPlayUser();
                                                                              if (this$playUser == null
                                                                                 ? other$playUser == null
                                                                                 : this$playUser.equals(other$playUser)) {
                                                                                 Object this$playTitle = this.getPlayTitle();
                                                                                 Object other$playTitle = other.getPlayTitle();
                                                                                 if (this$playTitle == null
                                                                                    ? other$playTitle == null
                                                                                    : this$playTitle.equals(other$playTitle)) {
                                                                                    Object this$userLocation = this.getUserLocation();
                                                                                    Object other$userLocation = other.getUserLocation();
                                                                                    if (this$userLocation == null
                                                                                       ? other$userLocation == null
                                                                                       : this$userLocation.equals(other$userLocation)) {
                                                                                       Object this$playTime = this.getPlayTime();
                                                                                       Object other$playTime = other.getPlayTime();
                                                                                       if (this$playTime == null
                                                                                          ? other$playTime == null
                                                                                          : this$playTime.equals(other$playTime)) {
                                                                                          Object this$playPosition = this.getPlayPosition();
                                                                                          Object other$playPosition = other.getPlayPosition();
                                                                                          if (this$playPosition == null
                                                                                             ? other$playPosition == null
                                                                                             : this$playPosition.equals(other$playPosition)) {
                                                                                             Object this$clientInfo = this.getClientInfo();
                                                                                             Object other$clientInfo = other.getClientInfo();
                                                                                             if (this$clientInfo == null
                                                                                                ? other$clientInfo == null
                                                                                                : this$clientInfo.equals(other$clientInfo)) {
                                                                                                Object this$telegramClient = this.getTelegramClient();
                                                                                                Object other$telegramClient = other.getTelegramClient();
                                                                                                if (this$telegramClient == null
                                                                                                   ? other$telegramClient == null
                                                                                                   : this$telegramClient.equals(other$telegramClient)) {
                                                                                                   Object this$caption = this.getCaption();
                                                                                                   Object other$caption = other.getCaption();
                                                                                                   if (this$caption == null
                                                                                                      ? other$caption == null
                                                                                                      : this$caption.equals(other$caption)) {
                                                                                                      Object this$extraVariables = this.getExtraVariables();
                                                                                                      Object other$extraVariables = other.getExtraVariables();
                                                                                                      if (this$extraVariables == null
                                                                                                         ? other$extraVariables == null
                                                                                                         : this$extraVariables.equals(other$extraVariables)) {
                                                                                                         Object this$productionCountries = this.getProductionCountries();
                                                                                                         Object other$productionCountries = other.getProductionCountries();
                                                                                                         if (this$productionCountries == null
                                                                                                            ? other$productionCountries == null
                                                                                                            : this$productionCountries.equals(
                                                                                                               other$productionCountries
                                                                                                            )) {
                                                                                                            Object this$releaseDate = this.getReleaseDate();
                                                                                                            Object other$releaseDate = other.getReleaseDate();
                                                                                                            return this$releaseDate == null
                                                                                                               ? other$releaseDate == null
                                                                                                               : this$releaseDate.equals(other$releaseDate);
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
      return other instanceof SendPhotoRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getEpisodeNumber();
      result = result * 59 + this.getSeasonNumber();
      Object $productionYear = this.getProductionYear();
      result = result * 59 + ($productionYear == null ? 43 : $productionYear.hashCode());
      Object $voteAverage = this.getVoteAverage();
      result = result * 59 + ($voteAverage == null ? 43 : $voteAverage.hashCode());
      Object $voteCount = this.getVoteCount();
      result = result * 59 + ($voteCount == null ? 43 : $voteCount.hashCode());
      Object $runtime = this.getRuntime();
      result = result * 59 + ($runtime == null ? 43 : $runtime.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $tvInfo = this.getTvInfo();
      result = result * 59 + ($tvInfo == null ? 43 : $tvInfo.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $imgUrl = this.getImgUrl();
      result = result * 59 + ($imgUrl == null ? 43 : $imgUrl.hashCode());
      Object $imgUrlInputStream = this.getImgUrlInputStream();
      result = result * 59 + ($imgUrlInputStream == null ? 43 : $imgUrlInputStream.hashCode());
      Object $parseMode = this.getParseMode();
      result = result * 59 + ($parseMode == null ? 43 : $parseMode.hashCode());
      Object $genres = this.getGenres();
      result = result * 59 + ($genres == null ? 43 : $genres.hashCode());
      Object $Type = this.getType();
      result = result * 59 + ($Type == null ? 43 : $Type.hashCode());
      Object $displayTitle = this.getDisplayTitle();
      result = result * 59 + ($displayTitle == null ? 43 : $displayTitle.hashCode());
      Object $Size = this.getSize();
      result = result * 59 + ($Size == null ? 43 : $Size.hashCode());
      Object $audioQuality = this.getAudioQuality();
      result = result * 59 + ($audioQuality == null ? 43 : $audioQuality.hashCode());
      Object $subtitleInfo = this.getSubtitleInfo();
      result = result * 59 + ($subtitleInfo == null ? 43 : $subtitleInfo.hashCode());
      Object $seriesName = this.getSeriesName();
      result = result * 59 + ($seriesName == null ? 43 : $seriesName.hashCode());
      Object $episodeName = this.getEpisodeName();
      result = result * 59 + ($episodeName == null ? 43 : $episodeName.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $serverUrl = this.getServerUrl();
      result = result * 59 + ($serverUrl == null ? 43 : $serverUrl.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $playUser = this.getPlayUser();
      result = result * 59 + ($playUser == null ? 43 : $playUser.hashCode());
      Object $playTitle = this.getPlayTitle();
      result = result * 59 + ($playTitle == null ? 43 : $playTitle.hashCode());
      Object $userLocation = this.getUserLocation();
      result = result * 59 + ($userLocation == null ? 43 : $userLocation.hashCode());
      Object $playTime = this.getPlayTime();
      result = result * 59 + ($playTime == null ? 43 : $playTime.hashCode());
      Object $playPosition = this.getPlayPosition();
      result = result * 59 + ($playPosition == null ? 43 : $playPosition.hashCode());
      Object $clientInfo = this.getClientInfo();
      result = result * 59 + ($clientInfo == null ? 43 : $clientInfo.hashCode());
      Object $telegramClient = this.getTelegramClient();
      result = result * 59 + ($telegramClient == null ? 43 : $telegramClient.hashCode());
      Object $caption = this.getCaption();
      result = result * 59 + ($caption == null ? 43 : $caption.hashCode());
      Object $extraVariables = this.getExtraVariables();
      result = result * 59 + ($extraVariables == null ? 43 : $extraVariables.hashCode());
      Object $productionCountries = this.getProductionCountries();
      result = result * 59 + ($productionCountries == null ? 43 : $productionCountries.hashCode());
      Object $releaseDate = this.getReleaseDate();
      return result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SendPhotoRequest(chatId="
         + this.getChatId()
         + ", name="
         + this.getName()
         + ", tvInfo="
         + this.getTvInfo()
         + ", overview="
         + this.getOverview()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", imgUrl="
         + this.getImgUrl()
         + ", imgUrlInputStream="
         + this.getImgUrlInputStream()
         + ", parseMode="
         + this.getParseMode()
         + ", productionYear="
         + this.getProductionYear()
         + ", genres="
         + this.getGenres()
         + ", Type="
         + this.getType()
         + ", displayTitle="
         + this.getDisplayTitle()
         + ", Size="
         + this.getSize()
         + ", audioQuality="
         + this.getAudioQuality()
         + ", subtitleInfo="
         + this.getSubtitleInfo()
         + ", voteAverage="
         + this.getVoteAverage()
         + ", voteCount="
         + this.getVoteCount()
         + ", episodeNumber="
         + this.getEpisodeNumber()
         + ", seriesName="
         + this.getSeriesName()
         + ", seasonNumber="
         + this.getSeasonNumber()
         + ", episodeName="
         + this.getEpisodeName()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", serverUrl="
         + this.getServerUrl()
         + ", serverName="
         + this.getServerName()
         + ", playUser="
         + this.getPlayUser()
         + ", playTitle="
         + this.getPlayTitle()
         + ", userLocation="
         + this.getUserLocation()
         + ", playTime="
         + this.getPlayTime()
         + ", playPosition="
         + this.getPlayPosition()
         + ", clientInfo="
         + this.getClientInfo()
         + ", telegramClient="
         + this.getTelegramClient()
         + ", caption="
         + this.getCaption()
         + ", extraVariables="
         + this.getExtraVariables()
         + ", runtime="
         + this.getRuntime()
         + ", productionCountries="
         + this.getProductionCountries()
         + ", releaseDate="
         + this.getReleaseDate()
         + ")";
   }
}
