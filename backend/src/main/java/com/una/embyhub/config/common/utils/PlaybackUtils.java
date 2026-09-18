package com.una.embyhub.config.common.utils;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import java.util.Date;
import lombok.Generated;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.util.StringUtils;

public class PlaybackUtils {
   public static void main(String[] args) {
      String jsonStr = "{\"Title\":\"root 在 Google Chrome macOS 上停止播放 堡垒：戴安娜 - S1, Ep3 - 齐心协力\",\"Description\":\"2025年8月20日星期三 下午5:35\",\"Date\":\"2025-08-20T09:35:38.7501240Z\",\"Event\":\"playback.stop\",\"User\":{\"Name\":\"root\",\"Id\":\"f7c3aeac133d42939840afe36b47c249\"},\"Item\":{\"Name\":\"齐心协力\",\"ServerId\":\"ccaa9fe18b2b4c23843e17a296f6c4ab\",\"Id\":\"5070\",\"DateCreated\":\"2025-06-13T05:45:32.0000000Z\",\"Container\":\"mp4\",\"SortName\":\"齐心协力\",\"PremiereDate\":\"2024-10-10T00:00:00.0000000Z\",\"ExternalUrls\":[{\"Name\":\"IMDb\",\"Url\":\"https://www.imdb.com/title/tt27766701\"},{\"Name\":\"TheTVDB\",\"Url\":\"https://thetvdb.com/?tab=episode&id=10594121\"},{\"Name\":\"Trakt\",\"Url\":\"https://trakt.tv/search/imdb/tt27766701\"}],\"Path\":\"/Emby媒体库/电视剧/堡垒：戴安娜 (2024)/堡垒：戴安娜 (2024).S01E03.mp4\",\"Overview\":\"爱托雷派爱德和戴安娜前往西西里执行一项敏感任务，而这项任务威胁到了两人间的联盟。然而，在地中海的阳光下，两人发现彼此间的渊源远不止各自的目标那么简单。八年前，戴安娜潜入曼提柯尔并执行了她的首个任务。\",\"Taglines\":[],\"Genres\":[],\"CommunityRating\":5.8,\"RunTimeTicks\":58150720000,\"Size\":3985637894,\"FileName\":\"堡垒：戴安娜 (2024).S01E03.mp4\",\"Bitrate\":5483182,\"ProductionYear\":2024,\"IndexNumber\":3,\"ParentIndexNumber\":1,\"RemoteTrailers\":[],\"ProviderIds\":{\"Tvdb\":\"10594121\",\"Imdb\":\"tt27766701\"},\"IsFolder\":false,\"ParentId\":\"5069\",\"Type\":\"Episode\",\"Studios\":[],\"GenreItems\":[],\"TagItems\":[],\"ParentLogoItemId\":\"5069\",\"ParentBackdropItemId\":\"5069\",\"ParentBackdropImageTags\":[\"2543b7b543bf2323f966eabeaa621942\"],\"SeriesName\":\"堡垒：戴安娜\",\"SeriesId\":\"5069\",\"SeasonId\":\"5082\",\"PrimaryImageAspectRatio\":1.7777777777777777,\"SeriesPrimaryImageTag\":\"d4b85cba2495fa80199598bf00cc8c5a\",\"SeasonName\":\"第 1 季\",\"ImageTags\":{\"Primary\":\"7360654138d5f8034493953564f7ac17\"},\"BackdropImageTags\":[],\"ParentLogoImageTag\":\"7eeb4b11752a15465f86d9be58f45fc6\",\"MediaType\":\"Video\",\"Width\":1920,\"Height\":800},\"Server\":{\"Name\":\"Mist媒体库\",\"Id\":\"ccaa9fe18b2b4c23843e17a296f6c4ab\",\"Version\":\"4.8.11.0\"},\"Session\":{\"RemoteEndPoint\":\"172.19.0.1\",\"Client\":\"Emby Web\",\"DeviceName\":\"Google Chrome macOS\",\"DeviceId\":\"5fc4faed-7ff4-46c4-8d7c-a790e3e0d33f\",\"ApplicationVersion\":\"4.8.11.0\",\"Id\":\"efde3240174bd96ddaf4d44b9cbc6fd2\"},\"PlaybackInfo\":{\"PlayedToCompletion\":false,\"PositionTicks\":11017133590,\"PlaylistIndex\":-1,\"PlaylistLength\":0,\"PlaySessionId\":\"ed32c041dddf4dcfa1329ed51c1ab780\"}}\n";
      PlaybackUtils.PlaybackInfo playbackInfo = parsePlaybackInfo(jsonStr, null);
      System.out.println(playbackInfo.toEmojiDescription());
   }

   public static PlaybackUtils.PlaybackInfo parsePlaybackInfo(String data, Ip2regionSearcher searchSearcher) {
      JSONObject json = JSON.parseObject(data);
      PlaybackUtils.PlaybackInfo info = new PlaybackUtils.PlaybackInfo();
      info.setOriginalTitle(json.getString("Title"));
      info.setDescription(json.getString("Description"));
      info.setDate(json.getString("Date"));
      info.setEvent(json.getString("Event"));
      JSONObject user = json.getJSONObject("User");
      info.setUserName(user.getString("Name"));
      info.setUserId(user.getString("Id"));
      JSONObject item = json.getJSONObject("Item");
      info.setItemName(item.getString("Name"));
      info.setItemId(item.getString("Id"));
      info.setOverview(item.getString("Overview"));
      info.setCommunityRating(item.getDouble("CommunityRating"));
      info.setRunTimeTicks(item.getLong("RunTimeTicks"));
      info.setSeriesName(item.getString("SeriesName"));
      info.setSeasonName(item.getString("SeasonName"));
      info.setFileName(item.getString("FileName"));
      info.setProductionYear(item.getInteger("ProductionYear"));
      info.setType(item.getString("Type"));
      info.setSeasonNumber(item.getInteger("ParentIndexNumber"));
      info.setEpisodeNumber(item.getInteger("IndexNumber"));
      JSONObject server = json.getJSONObject("Server");
      info.setServerName(server.getString("Name"));
      info.setServerId(server.getString("Id"));
      info.setServerVersion(server.getString("Version"));
      JSONObject session = json.getJSONObject("Session");
      info.setClient(session.getString("Client"));
      info.setDeviceName(session.getString("DeviceName"));
      info.setRemoteEndPoint(session.getString("RemoteEndPoint"));
      JSONObject playbackInfo = json.getJSONObject("PlaybackInfo");
      info.setPlayedToCompletion(playbackInfo.getBoolean("PlayedToCompletion"));
      info.setPositionTicks(playbackInfo.getLong("PositionTicks"));
      info.setPlaySessionId(playbackInfo.getString("PlaySessionId"));
      info.setFormattedTitle(generateFormattedTitle(info));
      IpAddressUtils.safeLookup(searchSearcher, info.getRemoteEndPoint()).ifPresent(ipInfo -> info.setIpAddress(ipInfo.getAddressAndIsp()));
      return info;
   }

   private static String generateFormattedTitle(PlaybackUtils.PlaybackInfo info) {
      StringBuilder title = new StringBuilder();
      String baseName = "Episode".equals(info.getType()) ? info.getSeriesName() : info.getItemName();
      title.append(baseName).append(" (").append(info.getProductionYear()).append(")");
      if ("Episode".equals(info.getType()) && info.getSeasonNumber() != null && info.getEpisodeNumber() != null) {
         title.append(" S")
            .append(String.format("%02d", info.getSeasonNumber()))
            .append("E")
            .append(String.format("%02d", info.getEpisodeNumber()))
            .append(" ")
            .append(info.getItemName());
      }

      return title.toString();
   }

   public static String formatTicksToTime(long ticks) {
      long seconds = ticks / 10000000L;
      long hours = seconds / 3600L;
      long minutes = seconds % 3600L / 60L;
      long secs = seconds % 60L;
      return hours > 0L ? String.format("%d:%d:%d", hours, minutes, secs) : String.format("%d:%02d", minutes, secs);
   }

   public static class PlaybackInfo {
      private String originalTitle;
      private String formattedTitle;
      private String description;
      private String date;
      private String event;
      private String ipAddress;
      private String eventName;
      private String userName;
      private String userId;
      private String itemName;
      private String itemId;
      private String overview;
      private Double communityRating;
      private Long runTimeTicks = 0L;
      private String seriesName;
      private String seasonName;
      private String fileName;
      private Integer productionYear;
      private String type;
      private Integer seasonNumber;
      private Integer episodeNumber;
      private String serverName;
      private String serverId;
      private String serverVersion;
      private String client;
      private String deviceName;
      private String remoteEndPoint;
      private Boolean playedToCompletion;
      private Long positionTicks;
      private String playSessionId;

      public void setEvent(String event) {
         this.event = event;
         if ("playback.start".equals(event)) {
            this.eventName = "▶️ 开始播放";
         }

         if ("playback.pause".equals(event)) {
            this.eventName = "⏸️ 暂停播放";
         }

         if ("playback.unpause".equals(event)) {
            this.eventName = "▶️ 取消暂停播放";
         }

         if ("playback.stop".equals(event)) {
            this.eventName = "⏸️ 停止播放";
         }
      }

      public String toEmojiDescription() {
         StringBuilder sb = new StringBuilder();
         sb.append("*");
         sb.append("\ud83c\udfa5 媒体").append("播放事件详情 \ud83c\udf1f\n");
         sb.append("\ud83d\udc64 用户：").append(this.userName).append(" ").append(this.eventName).append("\n");
         sb.append("\ud83d\udcfa 标题: ").append(this.formattedTitle).append("\n");
         sb.append("\ud83c\udf0f 用户归属地：")
            .append(this.remoteEndPoint)
            .append(" ")
            .append(StringUtils.hasText(this.ipAddress) ? this.ipAddress : "未知")
            .append("\n");
         if (!StringUtils.hasText(this.description)) {
            this.description = DateUtil.formatDateTime(new Date());
         }

         sb.append("⏰ 播放时间: ").append(this.description).append("\n");
         sb.append("\ud83d\udd75️♀️ 剧情概述:\n").append(this.overview).append("\n");
         String positionStr = PlaybackUtils.formatTicksToTime(this.positionTicks);
         String runtimeStr = PlaybackUtils.formatTicksToTime(this.runTimeTicks);
         sb.append("⏯️ 播放位置: ")
            .append(positionStr)
            .append(" / 总时长")
            .append(runtimeStr)
            .append(" 进度为:" + TimeStringPercentageCalculatorUtils.calculatePercentage(positionStr, runtimeStr))
            .append("\n");
         sb.append("社区评分: \ud83c\udf1f").append(this.communityRating == null ? 0.0 : this.communityRating).append(" / 10\n");
         sb.append("\ud83d\udcbb 客户端: ").append(this.client).append(" (").append(this.deviceName).append(")");
         sb.append("*");
         return sb.toString();
      }

      @Generated
      public String getOriginalTitle() {
         return this.originalTitle;
      }

      @Generated
      public String getFormattedTitle() {
         return this.formattedTitle;
      }

      @Generated
      public String getDescription() {
         return this.description;
      }

      @Generated
      public String getDate() {
         return this.date;
      }

      @Generated
      public String getEvent() {
         return this.event;
      }

      @Generated
      public String getIpAddress() {
         return this.ipAddress;
      }

      @Generated
      public String getEventName() {
         return this.eventName;
      }

      @Generated
      public String getUserName() {
         return this.userName;
      }

      @Generated
      public String getUserId() {
         return this.userId;
      }

      @Generated
      public String getItemName() {
         return this.itemName;
      }

      @Generated
      public String getItemId() {
         return this.itemId;
      }

      @Generated
      public String getOverview() {
         return this.overview;
      }

      @Generated
      public Double getCommunityRating() {
         return this.communityRating;
      }

      @Generated
      public Long getRunTimeTicks() {
         return this.runTimeTicks;
      }

      @Generated
      public String getSeriesName() {
         return this.seriesName;
      }

      @Generated
      public String getSeasonName() {
         return this.seasonName;
      }

      @Generated
      public String getFileName() {
         return this.fileName;
      }

      @Generated
      public Integer getProductionYear() {
         return this.productionYear;
      }

      @Generated
      public String getType() {
         return this.type;
      }

      @Generated
      public Integer getSeasonNumber() {
         return this.seasonNumber;
      }

      @Generated
      public Integer getEpisodeNumber() {
         return this.episodeNumber;
      }

      @Generated
      public String getServerName() {
         return this.serverName;
      }

      @Generated
      public String getServerId() {
         return this.serverId;
      }

      @Generated
      public String getServerVersion() {
         return this.serverVersion;
      }

      @Generated
      public String getClient() {
         return this.client;
      }

      @Generated
      public String getDeviceName() {
         return this.deviceName;
      }

      @Generated
      public String getRemoteEndPoint() {
         return this.remoteEndPoint;
      }

      @Generated
      public Boolean getPlayedToCompletion() {
         return this.playedToCompletion;
      }

      @Generated
      public Long getPositionTicks() {
         return this.positionTicks;
      }

      @Generated
      public String getPlaySessionId() {
         return this.playSessionId;
      }

      @Generated
      public void setOriginalTitle(final String originalTitle) {
         this.originalTitle = originalTitle;
      }

      @Generated
      public void setFormattedTitle(final String formattedTitle) {
         this.formattedTitle = formattedTitle;
      }

      @Generated
      public void setDescription(final String description) {
         this.description = description;
      }

      @Generated
      public void setDate(final String date) {
         this.date = date;
      }

      @Generated
      public void setIpAddress(final String ipAddress) {
         this.ipAddress = ipAddress;
      }

      @Generated
      public void setEventName(final String eventName) {
         this.eventName = eventName;
      }

      @Generated
      public void setUserName(final String userName) {
         this.userName = userName;
      }

      @Generated
      public void setUserId(final String userId) {
         this.userId = userId;
      }

      @Generated
      public void setItemName(final String itemName) {
         this.itemName = itemName;
      }

      @Generated
      public void setItemId(final String itemId) {
         this.itemId = itemId;
      }

      @Generated
      public void setOverview(final String overview) {
         this.overview = overview;
      }

      @Generated
      public void setCommunityRating(final Double communityRating) {
         this.communityRating = communityRating;
      }

      @Generated
      public void setRunTimeTicks(final Long runTimeTicks) {
         this.runTimeTicks = runTimeTicks;
      }

      @Generated
      public void setSeriesName(final String seriesName) {
         this.seriesName = seriesName;
      }

      @Generated
      public void setSeasonName(final String seasonName) {
         this.seasonName = seasonName;
      }

      @Generated
      public void setFileName(final String fileName) {
         this.fileName = fileName;
      }

      @Generated
      public void setProductionYear(final Integer productionYear) {
         this.productionYear = productionYear;
      }

      @Generated
      public void setType(final String type) {
         this.type = type;
      }

      @Generated
      public void setSeasonNumber(final Integer seasonNumber) {
         this.seasonNumber = seasonNumber;
      }

      @Generated
      public void setEpisodeNumber(final Integer episodeNumber) {
         this.episodeNumber = episodeNumber;
      }

      @Generated
      public void setServerName(final String serverName) {
         this.serverName = serverName;
      }

      @Generated
      public void setServerId(final String serverId) {
         this.serverId = serverId;
      }

      @Generated
      public void setServerVersion(final String serverVersion) {
         this.serverVersion = serverVersion;
      }

      @Generated
      public void setClient(final String client) {
         this.client = client;
      }

      @Generated
      public void setDeviceName(final String deviceName) {
         this.deviceName = deviceName;
      }

      @Generated
      public void setRemoteEndPoint(final String remoteEndPoint) {
         this.remoteEndPoint = remoteEndPoint;
      }

      @Generated
      public void setPlayedToCompletion(final Boolean playedToCompletion) {
         this.playedToCompletion = playedToCompletion;
      }

      @Generated
      public void setPositionTicks(final Long positionTicks) {
         this.positionTicks = positionTicks;
      }

      @Generated
      public void setPlaySessionId(final String playSessionId) {
         this.playSessionId = playSessionId;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof PlaybackUtils.PlaybackInfo other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$communityRating = this.getCommunityRating();
            Object other$communityRating = other.getCommunityRating();
            if (this$communityRating == null ? other$communityRating == null : this$communityRating.equals(other$communityRating)) {
               Object this$runTimeTicks = this.getRunTimeTicks();
               Object other$runTimeTicks = other.getRunTimeTicks();
               if (this$runTimeTicks == null ? other$runTimeTicks == null : this$runTimeTicks.equals(other$runTimeTicks)) {
                  Object this$productionYear = this.getProductionYear();
                  Object other$productionYear = other.getProductionYear();
                  if (this$productionYear == null ? other$productionYear == null : this$productionYear.equals(other$productionYear)) {
                     Object this$seasonNumber = this.getSeasonNumber();
                     Object other$seasonNumber = other.getSeasonNumber();
                     if (this$seasonNumber == null ? other$seasonNumber == null : this$seasonNumber.equals(other$seasonNumber)) {
                        Object this$episodeNumber = this.getEpisodeNumber();
                        Object other$episodeNumber = other.getEpisodeNumber();
                        if (this$episodeNumber == null ? other$episodeNumber == null : this$episodeNumber.equals(other$episodeNumber)) {
                           Object this$playedToCompletion = this.getPlayedToCompletion();
                           Object other$playedToCompletion = other.getPlayedToCompletion();
                           if (this$playedToCompletion == null ? other$playedToCompletion == null : this$playedToCompletion.equals(other$playedToCompletion)) {
                              Object this$positionTicks = this.getPositionTicks();
                              Object other$positionTicks = other.getPositionTicks();
                              if (this$positionTicks == null ? other$positionTicks == null : this$positionTicks.equals(other$positionTicks)) {
                                 Object this$originalTitle = this.getOriginalTitle();
                                 Object other$originalTitle = other.getOriginalTitle();
                                 if (this$originalTitle == null ? other$originalTitle == null : this$originalTitle.equals(other$originalTitle)) {
                                    Object this$formattedTitle = this.getFormattedTitle();
                                    Object other$formattedTitle = other.getFormattedTitle();
                                    if (this$formattedTitle == null ? other$formattedTitle == null : this$formattedTitle.equals(other$formattedTitle)) {
                                       Object this$description = this.getDescription();
                                       Object other$description = other.getDescription();
                                       if (this$description == null ? other$description == null : this$description.equals(other$description)) {
                                          Object this$date = this.getDate();
                                          Object other$date = other.getDate();
                                          if (this$date == null ? other$date == null : this$date.equals(other$date)) {
                                             Object this$event = this.getEvent();
                                             Object other$event = other.getEvent();
                                             if (this$event == null ? other$event == null : this$event.equals(other$event)) {
                                                Object this$ipAddress = this.getIpAddress();
                                                Object other$ipAddress = other.getIpAddress();
                                                if (this$ipAddress == null ? other$ipAddress == null : this$ipAddress.equals(other$ipAddress)) {
                                                   Object this$eventName = this.getEventName();
                                                   Object other$eventName = other.getEventName();
                                                   if (this$eventName == null ? other$eventName == null : this$eventName.equals(other$eventName)) {
                                                      Object this$userName = this.getUserName();
                                                      Object other$userName = other.getUserName();
                                                      if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                                                         Object this$userId = this.getUserId();
                                                         Object other$userId = other.getUserId();
                                                         if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                                                            Object this$itemName = this.getItemName();
                                                            Object other$itemName = other.getItemName();
                                                            if (this$itemName == null ? other$itemName == null : this$itemName.equals(other$itemName)) {
                                                               Object this$itemId = this.getItemId();
                                                               Object other$itemId = other.getItemId();
                                                               if (this$itemId == null ? other$itemId == null : this$itemId.equals(other$itemId)) {
                                                                  Object this$overview = this.getOverview();
                                                                  Object other$overview = other.getOverview();
                                                                  if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                                                     Object this$seriesName = this.getSeriesName();
                                                                     Object other$seriesName = other.getSeriesName();
                                                                     if (this$seriesName == null
                                                                        ? other$seriesName == null
                                                                        : this$seriesName.equals(other$seriesName)) {
                                                                        Object this$seasonName = this.getSeasonName();
                                                                        Object other$seasonName = other.getSeasonName();
                                                                        if (this$seasonName == null
                                                                           ? other$seasonName == null
                                                                           : this$seasonName.equals(other$seasonName)) {
                                                                           Object this$fileName = this.getFileName();
                                                                           Object other$fileName = other.getFileName();
                                                                           if (this$fileName == null
                                                                              ? other$fileName == null
                                                                              : this$fileName.equals(other$fileName)) {
                                                                              Object this$type = this.getType();
                                                                              Object other$type = other.getType();
                                                                              if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                                                                 Object this$serverName = this.getServerName();
                                                                                 Object other$serverName = other.getServerName();
                                                                                 if (this$serverName == null
                                                                                    ? other$serverName == null
                                                                                    : this$serverName.equals(other$serverName)) {
                                                                                    Object this$serverId = this.getServerId();
                                                                                    Object other$serverId = other.getServerId();
                                                                                    if (this$serverId == null
                                                                                       ? other$serverId == null
                                                                                       : this$serverId.equals(other$serverId)) {
                                                                                       Object this$serverVersion = this.getServerVersion();
                                                                                       Object other$serverVersion = other.getServerVersion();
                                                                                       if (this$serverVersion == null
                                                                                          ? other$serverVersion == null
                                                                                          : this$serverVersion.equals(other$serverVersion)) {
                                                                                          Object this$client = this.getClient();
                                                                                          Object other$client = other.getClient();
                                                                                          if (this$client == null
                                                                                             ? other$client == null
                                                                                             : this$client.equals(other$client)) {
                                                                                             Object this$deviceName = this.getDeviceName();
                                                                                             Object other$deviceName = other.getDeviceName();
                                                                                             if (this$deviceName == null
                                                                                                ? other$deviceName == null
                                                                                                : this$deviceName.equals(other$deviceName)) {
                                                                                                Object this$remoteEndPoint = this.getRemoteEndPoint();
                                                                                                Object other$remoteEndPoint = other.getRemoteEndPoint();
                                                                                                if (this$remoteEndPoint == null
                                                                                                   ? other$remoteEndPoint == null
                                                                                                   : this$remoteEndPoint.equals(other$remoteEndPoint)) {
                                                                                                   Object this$playSessionId = this.getPlaySessionId();
                                                                                                   Object other$playSessionId = other.getPlaySessionId();
                                                                                                   return this$playSessionId == null
                                                                                                      ? other$playSessionId == null
                                                                                                      : this$playSessionId.equals(other$playSessionId);
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
         return other instanceof PlaybackUtils.PlaybackInfo;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $communityRating = this.getCommunityRating();
         result = result * 59 + ($communityRating == null ? 43 : $communityRating.hashCode());
         Object $runTimeTicks = this.getRunTimeTicks();
         result = result * 59 + ($runTimeTicks == null ? 43 : $runTimeTicks.hashCode());
         Object $productionYear = this.getProductionYear();
         result = result * 59 + ($productionYear == null ? 43 : $productionYear.hashCode());
         Object $seasonNumber = this.getSeasonNumber();
         result = result * 59 + ($seasonNumber == null ? 43 : $seasonNumber.hashCode());
         Object $episodeNumber = this.getEpisodeNumber();
         result = result * 59 + ($episodeNumber == null ? 43 : $episodeNumber.hashCode());
         Object $playedToCompletion = this.getPlayedToCompletion();
         result = result * 59 + ($playedToCompletion == null ? 43 : $playedToCompletion.hashCode());
         Object $positionTicks = this.getPositionTicks();
         result = result * 59 + ($positionTicks == null ? 43 : $positionTicks.hashCode());
         Object $originalTitle = this.getOriginalTitle();
         result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
         Object $formattedTitle = this.getFormattedTitle();
         result = result * 59 + ($formattedTitle == null ? 43 : $formattedTitle.hashCode());
         Object $description = this.getDescription();
         result = result * 59 + ($description == null ? 43 : $description.hashCode());
         Object $date = this.getDate();
         result = result * 59 + ($date == null ? 43 : $date.hashCode());
         Object $event = this.getEvent();
         result = result * 59 + ($event == null ? 43 : $event.hashCode());
         Object $ipAddress = this.getIpAddress();
         result = result * 59 + ($ipAddress == null ? 43 : $ipAddress.hashCode());
         Object $eventName = this.getEventName();
         result = result * 59 + ($eventName == null ? 43 : $eventName.hashCode());
         Object $userName = this.getUserName();
         result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
         Object $userId = this.getUserId();
         result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
         Object $itemName = this.getItemName();
         result = result * 59 + ($itemName == null ? 43 : $itemName.hashCode());
         Object $itemId = this.getItemId();
         result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
         Object $overview = this.getOverview();
         result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
         Object $seriesName = this.getSeriesName();
         result = result * 59 + ($seriesName == null ? 43 : $seriesName.hashCode());
         Object $seasonName = this.getSeasonName();
         result = result * 59 + ($seasonName == null ? 43 : $seasonName.hashCode());
         Object $fileName = this.getFileName();
         result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
         Object $type = this.getType();
         result = result * 59 + ($type == null ? 43 : $type.hashCode());
         Object $serverName = this.getServerName();
         result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
         Object $serverId = this.getServerId();
         result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
         Object $serverVersion = this.getServerVersion();
         result = result * 59 + ($serverVersion == null ? 43 : $serverVersion.hashCode());
         Object $client = this.getClient();
         result = result * 59 + ($client == null ? 43 : $client.hashCode());
         Object $deviceName = this.getDeviceName();
         result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
         Object $remoteEndPoint = this.getRemoteEndPoint();
         result = result * 59 + ($remoteEndPoint == null ? 43 : $remoteEndPoint.hashCode());
         Object $playSessionId = this.getPlaySessionId();
         return result * 59 + ($playSessionId == null ? 43 : $playSessionId.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "PlaybackUtils.PlaybackInfo(originalTitle="
            + this.getOriginalTitle()
            + ", formattedTitle="
            + this.getFormattedTitle()
            + ", description="
            + this.getDescription()
            + ", date="
            + this.getDate()
            + ", event="
            + this.getEvent()
            + ", ipAddress="
            + this.getIpAddress()
            + ", eventName="
            + this.getEventName()
            + ", userName="
            + this.getUserName()
            + ", userId="
            + this.getUserId()
            + ", itemName="
            + this.getItemName()
            + ", itemId="
            + this.getItemId()
            + ", overview="
            + this.getOverview()
            + ", communityRating="
            + this.getCommunityRating()
            + ", runTimeTicks="
            + this.getRunTimeTicks()
            + ", seriesName="
            + this.getSeriesName()
            + ", seasonName="
            + this.getSeasonName()
            + ", fileName="
            + this.getFileName()
            + ", productionYear="
            + this.getProductionYear()
            + ", type="
            + this.getType()
            + ", seasonNumber="
            + this.getSeasonNumber()
            + ", episodeNumber="
            + this.getEpisodeNumber()
            + ", serverName="
            + this.getServerName()
            + ", serverId="
            + this.getServerId()
            + ", serverVersion="
            + this.getServerVersion()
            + ", client="
            + this.getClient()
            + ", deviceName="
            + this.getDeviceName()
            + ", remoteEndPoint="
            + this.getRemoteEndPoint()
            + ", playedToCompletion="
            + this.getPlayedToCompletion()
            + ", positionTicks="
            + this.getPositionTicks()
            + ", playSessionId="
            + this.getPlaySessionId()
            + ")";
      }
   }
}
