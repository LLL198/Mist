package com.una.embyhub.movie.service;

import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.movie.entity.MoviePtSubscribeEntity;
import com.una.embyhub.movie.mapper.MoviePtSubscribeMapper;
import com.una.embyhub.movie.model.MoviePtDownloadRequest;
import com.una.embyhub.movie.model.MoviePtSite;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MoviePtDownloadNotifyAsyncService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtDownloadNotifyAsyncService.class);
   private final NotifyUtils notifyUtils;
   private final MovieNotifyTmdbEnrichService movieNotifyTmdbEnrichService;
   private final MoviePtSubscribeMapper moviePtSubscribeMapper;
   private final MoviePtSiteService moviePtSiteService;

   @Async
   public void sendDownloadNotify(MoviePtDownloadRequest request) {
      if (request != null) {
         try {
            MoviePtSubscribeEntity subscribe = this.loadSubscribe(request.getSubscribeId());
            String subscribeName = this.firstNonBlank(subscribe == null ? null : subscribe.getName(), request.getMovieName(), "未命名资源");
            String mediaTypeLabel = this.resolveMediaTypeLabel(this.firstNonBlank(request.getMediaType(), subscribe == null ? null : subscribe.getType()));
            String downloadTitle = this.firstNonBlank(request.getTitle(), request.getMovieName(), subscribeName);
            String downloadSize = this.firstNonBlank(request.getSize(), "");
            String downloadSizeLine = this.buildDownloadSizeLine(downloadSize);
            String siteName = "";
            if (request.getSiteId() != null) {
               try {
                  MoviePtSite site = this.moviePtSiteService.getById(request.getSiteId());
                  if (site != null && StringUtils.hasText(site.getName())) {
                     siteName = site.getName();
                  }
               } catch (Exception var15) {
                  log.warn("获取站点信息失败 siteId={}: {}", request.getSiteId(), var15.getMessage());
               }
            }

            Map<String, String> extras = new HashMap<>();
            extras.put("subscribeName", subscribeName);
            extras.put("movieName", subscribeName);
            extras.put("mediaTypeLabel", mediaTypeLabel);
            extras.put("downloadTitle", downloadTitle);
            extras.put("downloadSize", downloadSize);
            extras.put("downloadSizeLine", downloadSizeLine);
            extras.put("siteName", siteName);
            String posterPath = this.firstNonBlank(subscribe == null ? null : subscribe.getPosterPath(), request.getPosterUrl(), request.getCoverUrl());
            String backdropPath = this.firstNonBlank(subscribe == null ? null : subscribe.getBackdropPath(), request.getCoverUrl(), request.getPosterUrl());
            String overview = String.format("[%s] %s 已触发下载：%s", mediaTypeLabel, subscribeName, downloadTitle);
            if (StringUtils.hasText(posterPath)) {
               SendPhotoRequest photoRequest = new SendPhotoRequest();
               photoRequest.setParseMode("Markdown");
               photoRequest.setName(String.format("订阅下载: %s", subscribeName));
               photoRequest.setOverview(overview);
               photoRequest.setImgUrl(posterPath);
               photoRequest.setBackdropPath(backdropPath);
               photoRequest.setSize(downloadSize);
               photoRequest.setType(this.resolveMediaType(this.firstNonBlank(request.getMediaType(), subscribe == null ? null : subscribe.getType())));
               this.movieNotifyTmdbEnrichService
                  .fillTmdbInfo(photoRequest, request.getTmdbId(), this.firstNonBlank(request.getMediaType(), subscribe == null ? null : subscribe.getType()));
               photoRequest.setExtraVariables(extras);
               this.notifyUtils
                  .sendMultiChannel(photoRequest, "subscribe_download", NotifyMessageType.PHOTO_DETAIL, false, "telegram", "dingding", "messagepush");
               SendPhotoRequest wechatRequest = new SendPhotoRequest();
               BeanUtils.copyProperties(photoRequest, wechatRequest);
               if (StringUtils.hasText(backdropPath)) {
                  wechatRequest.setImgUrl(backdropPath);
               }

               this.notifyUtils.sendMultiChannel(wechatRequest, "subscribe_download", NotifyMessageType.PHOTO_DETAIL, false, "wechat", "wechatBot");
               return;
            }

            SendMessageRequest messageRequest = new SendMessageRequest();
            messageRequest.setParseMode("Markdown");
            messageRequest.setName("订阅下载通知");
            messageRequest.setOverview(overview);
            messageRequest.setImgUrl(posterPath);
            messageRequest.setExtraVariables(extras);
            this.notifyUtils.sendMultiChannel(messageRequest, "subscribe_download", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
         } catch (TelegramApiException var16) {
            log.warn("发送下载通知失败 subscribeId={} movieName={} err={}", request.getSubscribeId(), request.getMovieName(), var16.getMessage());
         } catch (Exception var17) {
            log.warn("发送下载通知失败 subscribeId={} movieName={} err={}", request.getSubscribeId(), request.getMovieName(), var17.getMessage());
         }
      }
   }

   private MoviePtSubscribeEntity loadSubscribe(Long subscribeId) {
      if (subscribeId == null) {
         return null;
      } else {
         try {
            MoviePtSubscribeEntity entity = this.moviePtSubscribeMapper.selectById(subscribeId);
            return entity != null && entity.getDelFlag() != 1 ? entity : null;
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private String resolveMediaTypeLabel(String mediaType) {
      if (!StringUtils.hasText(mediaType)) {
         return "电影";
      } else {
         String normalized = mediaType.trim().toLowerCase();
         return !"tv".equals(normalized)
               && !"series".equals(normalized)
               && !"episode".equals(normalized)
               && !"电视剧".equals(mediaType.trim())
               && !"剧集".equals(mediaType.trim())
            ? "电影"
            : "剧集";
      }
   }

   private String resolveMediaType(String mediaType) {
      if (!StringUtils.hasText(mediaType)) {
         return "movie";
      } else {
         String normalized = mediaType.trim().toLowerCase();
         return !"tv".equals(normalized)
               && !"series".equals(normalized)
               && !"episode".equals(normalized)
               && !"电视剧".equals(mediaType.trim())
               && !"剧集".equals(mediaType.trim())
            ? "movie"
            : "tv";
      }
   }

   private String buildDownloadSizeLine(String size) {
      return !StringUtils.hasText(size) ? "" : "\ud83d\udcbe 大小：" + size + "\n";
   }

   private String firstNonBlank(String... values) {
      if (values == null) {
         return "";
      } else {
         for (String value : values) {
            if (StringUtils.hasText(value)) {
               return value.trim();
            }
         }

         return "";
      }
   }

   @Generated
   public MoviePtDownloadNotifyAsyncService(
      final NotifyUtils notifyUtils,
      final MovieNotifyTmdbEnrichService movieNotifyTmdbEnrichService,
      final MoviePtSubscribeMapper moviePtSubscribeMapper,
      final MoviePtSiteService moviePtSiteService
   ) {
      this.notifyUtils = notifyUtils;
      this.movieNotifyTmdbEnrichService = movieNotifyTmdbEnrichService;
      this.moviePtSubscribeMapper = moviePtSubscribeMapper;
      this.moviePtSiteService = moviePtSiteService;
   }
}
