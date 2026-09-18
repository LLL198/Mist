package com.una.embyhub.config.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.model.dto.request.requestlist.RequestListSave;
import com.una.embyhub.model.dto.request.telegram.SearchRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.telegram.SearchResponse;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.RequestList;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.SearchService;
import com.una.embyhub.service.TelegramPanService;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PushUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PushUtils.class);
   private static final int OVERVIEW_PREVIEW_LINES = 5;
   private static final int OVERVIEW_PREVIEW_LINE_WIDTH = 36;
   @Autowired
   private TelegramPanService telegramPanService;
   @Autowired
   private SearchService searchService;
   @Autowired
   private TelegramClientUtils telegramClientUtils;
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private EmbyInfoService embyInfoService;

   @Async
   public void pushAsync(RequestListSave requestListSave, RequestList requestList, EmbyUser embyUser) {
      SendPhotoRequest sendPhotoRequest = new SendPhotoRequest();
      String season = this.buildSeasonText(requestList);
      Map<String, String> extras = new HashMap<>();
      extras.put("requestName", requestList.getName());
      extras.put("year", DateUtil.format(requestList.getReleaseDate(), "yyyy"));
      extras.put("season", season);
      extras.put("userName", embyUser.getEmbyUserName());
      extras.put("requestOverviewBrief", this.buildOverviewPreview(requestList.getOverview()));
      sendPhotoRequest.setName(requestList.getName());
      sendPhotoRequest.setOverview(requestList.getOverview());
      sendPhotoRequest.setTmdbUrl(requestList.getTmdbUrl());
      sendPhotoRequest.setImgUrlInputStream(ResourceUtil.getStream("img/default.jpg"));
      sendPhotoRequest.setImgUrl(requestList.getImageUrl());
      sendPhotoRequest.setParseMode("Markdown");
      sendPhotoRequest.setProductionYear(Integer.valueOf(DateUtil.format(requestList.getReleaseDate(), "yyyy")));
      sendPhotoRequest.setGenres("#用户提交求片#" + requestList.getRemark());
      sendPhotoRequest.setType(requestList.getType());
      sendPhotoRequest.setBackdropPath(requestListSave.getBackdropPath());
      if (StringUtils.hasText(requestList.getScore())) {
         try {
            sendPhotoRequest.setVoteAverage(Double.parseDouble(requestList.getScore()));
         } catch (NumberFormatException var15) {
         }
      }

      if ("tv".equals(requestList.getType())) {
         if (requestList.getSeason() != null) {
            sendPhotoRequest.setSeasonNumber(requestList.getSeason());
         }

         if (requestList.getEpisode() != null) {
            sendPhotoRequest.setEpisodeNumber(requestList.getEpisode());
         }
      }

      sendPhotoRequest.setRuntime(requestList.getRuntime());
      sendPhotoRequest.setProductionCountries(requestList.getProductionCountries());
      if (requestList.getReleaseDate() != null) {
         sendPhotoRequest.setReleaseDate(DateUtil.format(requestList.getReleaseDate(), "yyyy-MM-dd"));
      }

      Long targetEmbyInfoId = requestList.getEmbyInfoId() != null ? requestList.getEmbyInfoId() : requestListSave.getEmbyInfoId();
      EmbyInfo embyInfo = targetEmbyInfoId != null ? this.embyInfoService.getById(targetEmbyInfoId) : null;
      String serverName = embyInfo != null ? embyInfo.getServerName() : null;
      sendPhotoRequest.setServerUrl(this.buildServerUrl(embyInfo));
      sendPhotoRequest.setServerName(serverName);
      extras.put("serverName", serverName);
      sendPhotoRequest.setExtraVariables(extras);

      try {
         this.notifyUtils
            .sendMultiChannel(
               sendPhotoRequest, "request_submitted", NotifyMessageType.PHOTO_DETAIL, false, "telegram", "wechat", "wechatBot", "dingding", "messagepush"
            );
      } catch (TelegramApiException var14) {
         log.error("新增求片列表通知发送失败", (Throwable)var14);
      }

      TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
      if (telegramResponse != null) {
         try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setKw(requestList.getName());
            searchRequest.setRes("results");
            SearchResponse searchResponse = this.searchService.search(searchRequest);
            if (!CollectionUtils.isEmpty(searchResponse.getData().getResults())) {
               this.telegramPanService
                  .pushHome(telegramResponse.getBotChatId(), searchResponse, telegramResponse.getBotToken(), requestList.getName(), sendPhotoRequest.getName());
            }
         } catch (Exception var13) {
            log.error("新增求片列表搜索资源列表通知推送失败", (Throwable)var13);
         }
      }
   }

   private String buildServerUrl(EmbyInfo embyInfo) {
      if (embyInfo == null) {
         return null;
      } else {
         String embyUrl = embyInfo.getEmbyUrl();
         if (!StringUtils.hasText(embyUrl) || !embyUrl.startsWith("http://") && !embyUrl.startsWith("https://")) {
            StringBuilder baseUrl = new StringBuilder();
            if (StringUtils.hasText(embyInfo.getEmbyAgreement())) {
               baseUrl.append(embyInfo.getEmbyAgreement()).append("://");
            }

            if (StringUtils.hasText(embyInfo.getEmbyUrl())) {
               baseUrl.append(embyInfo.getEmbyUrl());
            }

            if (StringUtils.hasText(embyInfo.getEmbyPort())) {
               if (embyInfo.getEmbyUrl() != null && !embyInfo.getEmbyUrl().contains(":")) {
                  baseUrl.append(":");
               }

               baseUrl.append(embyInfo.getEmbyPort());
            }

            return baseUrl.length() > 0 ? baseUrl.toString() : null;
         } else {
            return embyUrl;
         }
      }
   }

   private String buildSeasonText(RequestList requestList) {
      return "tv".equals(requestList.getType()) && requestList.getSeason() != null ? " S" + String.format("%02d", requestList.getSeason()) : "";
   }

   private String buildOverviewPreview(String overview) {
      if (!StringUtils.hasText(overview)) {
         return "暂无简介";
      } else {
         String normalized = overview.replaceAll("\\s+", " ").trim();
         StringBuilder preview = new StringBuilder();
         int maxWidth = 180;
         int currentWidth = 0;
         boolean truncated = false;
         int offset = 0;

         while (offset < normalized.length()) {
            int codePoint = normalized.codePointAt(offset);
            offset += Character.charCount(codePoint);
            int charWidth = this.displayWidth(codePoint);
            if (currentWidth + charWidth > maxWidth) {
               truncated = true;
               break;
            }

            preview.appendCodePoint(codePoint);
            currentWidth += charWidth;
         }

         String result = this.trimTrailingWhitespace(preview.toString());
         return truncated ? result + "..." : result;
      }
   }

   private int displayWidth(int codePoint) {
      return codePoint > 255 ? 2 : 1;
   }

   private String trimTrailingWhitespace(String value) {
      int end = value.length();

      while (end > 0 && Character.isWhitespace(value.charAt(end - 1))) {
         end--;
      }

      return value.substring(0, end);
   }

   @Async
   public void pushCompletedAsync(RequestList requestList, String serverUrl, String serverName) {
      SendPhotoRequest sendPhotoRequest = new SendPhotoRequest();
      String season = this.buildSeasonText(requestList);
      Map<String, String> extras = new HashMap<>();
      extras.put("requestName", requestList.getName());
      extras.put("year", DateUtil.format(requestList.getReleaseDate(), "yyyy"));
      extras.put("season", season);
      extras.put("serverName", serverName);
      extras.put("requestOverviewBrief", this.buildOverviewPreview(requestList.getOverview()));
      sendPhotoRequest.setName(requestList.getName());
      sendPhotoRequest.setOverview(requestList.getOverview());
      sendPhotoRequest.setTmdbUrl(requestList.getTmdbUrl());
      sendPhotoRequest.setImgUrlInputStream(ResourceUtil.getStream("img/default.jpg"));
      sendPhotoRequest.setImgUrl(requestList.getImageUrl());
      sendPhotoRequest.setParseMode("Markdown");
      sendPhotoRequest.setProductionYear(Integer.valueOf(DateUtil.format(requestList.getReleaseDate(), "yyyy")));
      sendPhotoRequest.setGenres("#求片入库");
      sendPhotoRequest.setType(requestList.getType());
      sendPhotoRequest.setBackdropPath(requestList.getBackdropPath());
      if (StringUtils.hasText(requestList.getScore())) {
         try {
            sendPhotoRequest.setVoteAverage(Double.parseDouble(requestList.getScore()));
         } catch (NumberFormatException var9) {
         }
      }

      if ("tv".equals(requestList.getType())) {
         if (requestList.getSeason() != null) {
            sendPhotoRequest.setSeasonNumber(requestList.getSeason());
         }

         if (requestList.getEpisode() != null) {
            sendPhotoRequest.setEpisodeNumber(requestList.getEpisode());
         }
      }

      sendPhotoRequest.setRuntime(requestList.getRuntime());
      sendPhotoRequest.setProductionCountries(requestList.getProductionCountries());
      if (requestList.getReleaseDate() != null) {
         sendPhotoRequest.setReleaseDate(DateUtil.format(requestList.getReleaseDate(), "yyyy-MM-dd"));
      }

      sendPhotoRequest.setServerUrl(serverUrl);
      sendPhotoRequest.setServerName(serverName);
      sendPhotoRequest.setExtraVariables(extras);

      try {
         this.notifyUtils
            .sendMultiChannel(
               sendPhotoRequest, "request_completed", NotifyMessageType.PHOTO_DETAIL, true, "telegram", "wechat", "wechatBot", "dingding", "messagepush"
            );
      } catch (TelegramApiException var8) {
         log.error("求片入库通知发送失败", (Throwable)var8);
      }
   }
}
