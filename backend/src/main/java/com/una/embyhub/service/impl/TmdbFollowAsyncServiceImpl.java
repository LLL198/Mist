package com.una.embyhub.service.impl;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowProgressBatchRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowProgressRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowSubscribeRequest;
import com.una.embyhub.model.entity.TmdbFollow;
import com.una.embyhub.service.TmdbFollowAsyncService;
import com.una.embyhub.service.TmdbFollowService;
import com.una.embyhub.service.TmdbService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import java.util.Date;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TmdbFollowAsyncServiceImpl implements TmdbFollowAsyncService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbFollowAsyncServiceImpl.class);
   @Autowired
   private TmdbService tmdbService;
   @Lazy
   @Autowired
   private TmdbFollowService tmdbFollowService;

   @Async
   @Override
   public void asyncSyncFollowData(Long followId, TmdbFollowSubscribeRequest request) {
      try {
         TmdbFollow follow = this.tmdbFollowService.getById(followId);
         if (follow == null) {
            log.error("订阅记录不存在：{}", followId);
            return;
         }

         String mediaType = follow.getMediaType();
         if ("movie".equalsIgnoreCase(mediaType)) {
            this.syncMovieData(follow);
         } else {
            this.syncTvData(follow);
         }

         follow.setStatus(0);
         follow.setLastSyncTime(new Date());
         this.tmdbFollowService.updateById(follow);
         log.info("订阅数据同步成功：{} - {}", follow.getName(), follow.getTmdbId());
      } catch (Exception var5) {
         log.error("订阅数据同步失败：followId={}", followId, var5);
         TmdbFollow followx = this.tmdbFollowService.getById(followId);
         if (followx != null) {
            followx.setStatus(3);
            this.tmdbFollowService.updateById(followx);
         }
      }
   }

   @Async
   @Override
   public void asyncBatchUpdateProgress(TmdbFollowProgressBatchRequest request) {
      log.info("开始异步批量更新观看进度，共 {} 条", request.getProgressList().size());
      int successCount = 0;
      int failCount = 0;

      for (TmdbFollowProgressRequest progressRequest : request.getProgressList()) {
         try {
            this.tmdbFollowService.updateProgress(progressRequest);
            successCount++;
         } catch (Exception var7) {
            failCount++;
            log.error("批量更新进度失败：followId={}, error={}", progressRequest.getFollowId(), var7.getMessage());
         }
      }

      log.info("批量更新观看进度完成，成功: {}, 失败: {}", successCount, failCount);
   }

   private void syncTvData(TmdbFollow follow) throws TmdbException {
      TvSeriesDb tvSeriesDb = this.tmdbService.getTvSeries(follow.getTmdbId(), follow.getLanguage(), TvSeriesAppendToResponse.values());
      if (tvSeriesDb == null) {
         throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "未找到对应剧集");
      } else {
         this.tmdbFollowService.syncTvDataInternal(follow, tvSeriesDb);
      }
   }

   private void syncMovieData(TmdbFollow follow) throws TmdbException {
      MovieDb movieDb = this.tmdbService.getMovieDetails(follow.getTmdbId(), follow.getLanguage(), MovieAppendToResponse.values());
      if (movieDb == null) {
         throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "未找到对应电影");
      } else {
         this.tmdbFollowService.syncMovieDataInternal(follow, movieDb);
      }
   }
}
