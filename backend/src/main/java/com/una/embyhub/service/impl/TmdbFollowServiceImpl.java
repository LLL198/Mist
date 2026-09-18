package com.una.embyhub.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.TmdbFollowMapper;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowCancelRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowCheckRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowProgressBatchRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowProgressRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowQueryRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowSubscribeRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowSyncRequest;
import com.una.embyhub.model.dto.response.tmdbfollow.TmdbCastResponse;
import com.una.embyhub.model.dto.response.tmdbfollow.TmdbEpisodeResponse;
import com.una.embyhub.model.dto.response.tmdbfollow.TmdbFollowResponse;
import com.una.embyhub.model.dto.response.tmdbfollow.TmdbSeasonResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.TmdbEpisode;
import com.una.embyhub.model.entity.TmdbFollow;
import com.una.embyhub.model.entity.TmdbSeason;
import com.una.embyhub.model.entity.TmdbWatchProgress;
import com.una.embyhub.service.TmdbEpisodeService;
import com.una.embyhub.service.TmdbFollowAsyncService;
import com.una.embyhub.service.TmdbFollowService;
import com.una.embyhub.service.TmdbSeasonService;
import com.una.embyhub.service.TmdbService;
import com.una.embyhub.service.TmdbWatchProgressService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.core.TvEpisode;
import info.movito.themoviedbapi.model.tv.core.TvSeason;
import info.movito.themoviedbapi.model.tv.core.credits.Cast;
import info.movito.themoviedbapi.model.tv.core.credits.Credits;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonEpisode;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeasonsAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

@Service
public class TmdbFollowServiceImpl extends ServiceImpl<TmdbFollowMapper, TmdbFollow> implements TmdbFollowService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbFollowServiceImpl.class);
   private static final String TMDB_TV_URL = "https://www.themoviedb.org/tv/";
   private static final String TEMPLATE_CODE = "tmdb_follow_update";
   private static final String TMDB_POSTER_SIZE = "https://image.tmdb.org/t/p/w500";
   @Autowired
   private TmdbService tmdbService;
   @Autowired
   private TmdbEpisodeService tmdbEpisodeService;
   @Autowired
   private TmdbSeasonService tmdbSeasonService;
   @Autowired
   private TmdbWatchProgressService tmdbWatchProgressService;
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private TmdbFollowAsyncService tmdbFollowAsyncService;
   @Value("${tmdb.imageUrl:https://image.tmdb.org/t/p/original}")
   private String tmdbImageUrl;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TmdbFollowResponse subscribe(TmdbFollowSubscribeRequest request) throws TmdbException {
      String mediaType = this.resolveMediaType(request.getMediaType());
      TmdbFollow existingFollow = this.lambdaQuery()
         .eq(TmdbFollow::getTmdbId, request.getTmdbId())
         .eq(TmdbFollow::getSubscriberName, request.getSubscriberName())
         .one();
      if (existingFollow != null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "您已订阅此内容，请勿重复订阅");
      } else {
         TmdbFollow follow = new TmdbFollow();
         follow.setTmdbId(request.getTmdbId());
         follow.setMediaType(mediaType);
         follow.setLanguage(request.getLanguage() != null ? request.getLanguage() : "zh-CN");
         follow.setSubscriberName(request.getSubscriberName());
         follow.setNotifyChannels(request.getNotifyChannels() != null ? String.join(",", request.getNotifyChannels()) : "");
         follow.setCreateDatetime(new Date());
         follow.setStatus(2);
         this.save(follow);
         final Long followId = follow.getId();
         final TmdbFollowSubscribeRequest finalRequest = request;
         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
               TmdbFollowServiceImpl.this.tmdbFollowAsyncService.asyncSyncFollowData(followId, finalRequest);
            }
         });
         return this.buildResponse(follow);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void syncTvDataInternal(TmdbFollow follow, TvSeriesDb tvSeriesDb) throws TmdbException {
      this.fillFollowWithSeries(follow, tvSeriesDb, this.splitChannels(follow.getNotifyChannels()), follow.getSubscriberName(), follow.getLanguage());
      this.updateById(follow);
      this.syncSeasons(follow, tvSeriesDb);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void syncMovieDataInternal(TmdbFollow follow, MovieDb movieDb) {
      this.fillFollowWithMovie(follow, movieDb, this.splitChannels(follow.getNotifyChannels()), follow.getSubscriberName(), follow.getLanguage());
      Date releaseDate = follow.getReleaseDate();
      if (releaseDate != null && !releaseDate.after(new Date())) {
         follow.setReleaseNotified(true);
      } else {
         follow.setReleaseNotified(false);
      }

      this.updateById(follow);
   }

   @Override
   public List<TmdbFollowResponse> listFollows() {
      return this.list().stream().map(this::buildResponse).toList();
   }

   @Override
   public Page<TmdbFollowResponse> pageFollows(MybatisPlusPage<TmdbFollowQueryRequest> page) {
      TmdbFollowQueryRequest request = page.getObject();
      Page<TmdbFollow> pageDto = page.getPageDto(TmdbFollow.class);
      LambdaQueryWrapper<TmdbFollow> wrapper = new LambdaQueryWrapper<>();
      if (request != null) {
         if (StringUtils.hasText(request.getMediaType())) {
            wrapper.eq(TmdbFollow::getMediaType, request.getMediaType());
         }

         if (StringUtils.hasText(request.getName())) {
            wrapper.like(TmdbFollow::getName, request.getName());
         }
      }

      wrapper.orderByDesc(BaseEntity::getCreateDatetime);
      Page<TmdbFollow> resultPage = this.page(pageDto, wrapper);
      Page<TmdbFollowResponse> responsePage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
      responsePage.setRecords(resultPage.getRecords().stream().map(this::buildResponse).toList());
      return responsePage;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TmdbFollowResponse updateProgress(TmdbFollowProgressRequest request) {
      TmdbFollow follow = this.getById(request.getFollowId());
      if (follow == null) {
         throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "追剧订阅不存在");
      } else {
         LambdaQueryChainWrapper<TmdbWatchProgress> wrapper = new LambdaQueryChainWrapper<>(this.tmdbWatchProgressService.getBaseMapper())
            .eq(TmdbWatchProgress::getFollowId, request.getFollowId());
         if (StringUtils.hasText(request.getWatcherName())) {
            wrapper.eq(TmdbWatchProgress::getWatcherName, request.getWatcherName());
         }

         TmdbWatchProgress progress = wrapper.one();
         if (progress == null) {
            progress = new TmdbWatchProgress();
            progress.setFollowId(request.getFollowId());
            progress.setWatcherName(request.getWatcherName());
            progress.setWatchedEpisodes("[]");
         }

         List<String> watchedList = JSON.parseArray(StringUtils.hasText(progress.getWatchedEpisodes()) ? progress.getWatchedEpisodes() : "[]", String.class);
         if (this.isMovie(follow)) {
            String movieKey = "movie";
            if (watchedList.contains(movieKey)) {
               watchedList.remove(movieKey);
            } else {
               watchedList.add(movieKey);
            }

            progress.setWatchedEpisodes(JSON.toJSONString(watchedList));
            progress.setSeasonNumber(null);
            progress.setEpisodeNumber(null);
         } else {
            if (request.getSeasonNumber() == null || request.getEpisodeNumber() == null) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "电视剧进度更新需要提供季数和集数");
            }

            String episodeKey = request.getSeasonNumber() + "-" + request.getEpisodeNumber();
            if (watchedList.contains(episodeKey)) {
               watchedList.remove(episodeKey);
            } else {
               watchedList.add(episodeKey);
            }

            progress.setWatchedEpisodes(JSON.toJSONString(watchedList));
            if (CollUtil.isNotEmpty(watchedList)) {
               int maxSeason = 0;
               int maxEpisode = 0;

               for (String key : watchedList) {
                  String[] parts = key.split("-");
                  int s = Integer.parseInt(parts[0]);
                  int e = Integer.parseInt(parts[1]);
                  if (s > maxSeason || s == maxSeason && e > maxEpisode) {
                     maxSeason = s;
                     maxEpisode = e;
                  }
               }

               progress.setSeasonNumber(maxSeason);
               progress.setEpisodeNumber(maxEpisode);
            } else {
               progress.setSeasonNumber(null);
               progress.setEpisodeNumber(null);
            }
         }

         this.tmdbWatchProgressService.saveProgress(progress);
         return this.buildResponse(follow);
      }
   }

   @Override
   public Boolean batchUpdateProgress(TmdbFollowProgressBatchRequest request) {
      for (TmdbFollowProgressRequest progressRequest : request.getProgressList()) {
         try {
            this.updateProgress(progressRequest);
         } catch (Exception var5) {
            log.error("批量更新进度失败：followId={}, error={}", progressRequest.getFollowId(), var5.getMessage());
         }
      }

      return true;
   }

   @Override
   public TmdbWatchProgress getProgress(Long followId, String watcherName) {
      TmdbFollow follow = this.getById(followId);
      if (follow == null) {
         throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "追剧订阅不存在");
      } else {
         LambdaQueryChainWrapper<TmdbWatchProgress> wrapper = new LambdaQueryChainWrapper<>(this.tmdbWatchProgressService.getBaseMapper())
            .eq(TmdbWatchProgress::getFollowId, followId);
         if (StringUtils.hasText(watcherName)) {
            wrapper.eq(TmdbWatchProgress::getWatcherName, watcherName);
         }

         return wrapper.one();
      }
   }

   @Override
   public List<TmdbFollowResponse> sync(TmdbFollowSyncRequest request) throws TmdbException {
      List<TmdbFollow> follows = request.getFollowId() == null ? this.list() : this.lambdaQuery().eq(TmdbFollow::getId, request.getFollowId()).list();

      for (TmdbFollow follow : follows) {
         try {
            this.syncSingleFollow(follow, request.isToGroup());
         } catch (Exception var6) {
            log.error("同步追剧失败：{}", follow.getName(), var6);
         }
      }

      return follows.stream().map(this::buildResponse).toList();
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void unsubscribe(TmdbFollowCancelRequest request) {
      if (request != null && (request.getFollowId() != null || request.getTmdbId() != null)) {
         TmdbFollow follow = request.getFollowId() != null
            ? this.getById(request.getFollowId())
            : this.lambdaQuery().eq(TmdbFollow::getTmdbId, request.getTmdbId()).one();
         if (follow == null) {
            throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "追剧订阅不存在");
         } else {
            this.removeById(follow.getId());
            this.tmdbEpisodeService.remove(new LambdaQueryWrapper<TmdbEpisode>().eq(TmdbEpisode::getFollowId, follow.getId()));
            this.tmdbWatchProgressService.remove(new LambdaQueryWrapper<TmdbWatchProgress>().eq(TmdbWatchProgress::getFollowId, follow.getId()));
         }
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "followId 和 tmdbId 不能同时为空");
      }
   }

   @Override
   public Boolean isSubscribed(TmdbFollowCheckRequest request) {
      return this.lambdaQuery().eq(TmdbFollow::getTmdbId, request.getTmdbId()).exists();
   }

   private void syncSingleFollow(TmdbFollow follow, boolean toGroup) throws TmdbException {
      if (this.isMovie(follow)) {
         this.syncSingleMovie(follow, toGroup);
      } else {
         TvSeriesDb tvSeriesDb = this.tmdbService.getTvSeries(follow.getTmdbId(), follow.getLanguage(), TvSeriesAppendToResponse.values());
         if (tvSeriesDb == null) {
            log.warn("未能从 TMDB 获取剧集：{}", follow.getTmdbId());
         } else {
            boolean followChanged = false;
            if (("Ended".equalsIgnoreCase(tvSeriesDb.getStatus()) || "Canceled".equalsIgnoreCase(tvSeriesDb.getStatus()))
               && tvSeriesDb.getNextEpisodeToAir() == null
               && !this.isNewEpisode(follow, tvSeriesDb.getLastEpisodeToAir())) {
               log.debug("剧集 {} ({}) 已完结且无更新，跳过同步", follow.getName(), follow.getTmdbId());
               follow.setLastSyncTime(new Date());
               this.updateById(follow);
            } else {
               followChanged = this.fillFollowWithSeries(
                  follow, tvSeriesDb, this.splitChannels(follow.getNotifyChannels()), follow.getSubscriberName(), follow.getLanguage()
               );
               boolean seasonsChanged = this.syncSeasons(follow, tvSeriesDb);
               followChanged = followChanged || seasonsChanged;
               boolean notifiedChanged = this.processEpisodeNotifications(follow, tvSeriesDb, toGroup);
               followChanged = followChanged || notifiedChanged;
               TvEpisode nextEpisode = tvSeriesDb.getNextEpisodeToAir();
               if (nextEpisode != null) {
                  this.saveEpisode(follow, nextEpisode);
               }

               boolean needUpdate = followChanged || seasonsChanged || notifiedChanged;
               if (needUpdate) {
                  follow.setLastSyncTime(new Date());
                  this.updateById(follow);
               }
            }
         }
      }
   }

   private boolean processEpisodeNotifications(TmdbFollow follow, TvSeriesDb tvSeriesDb, boolean toGroup) {
      TvEpisode lastEpisode = tvSeriesDb.getLastEpisodeToAir();
      if (lastEpisode != null && this.isAired(lastEpisode)) {
         Integer lastNotifiedSeason = follow.getLastNotifiedSeason();
         Integer lastNotifiedEpisode = follow.getLastNotifiedEpisode();
         if (this.isNewEpisode(follow, lastEpisode)) {
            List<TmdbEpisode> newEpisodes = this.findNewEpisodes(follow, tvSeriesDb, lastNotifiedSeason, lastNotifiedEpisode);
            if (CollUtil.isNotEmpty(newEpisodes)) {
               this.sendAggregatedEpisodeNotify(follow, newEpisodes, toGroup);
               TmdbEpisode latest = newEpisodes.get(newEpisodes.size() - 1);
               follow.setLastNotifiedSeason(latest.getSeasonNumber());
               follow.setLastNotifiedEpisode(latest.getEpisodeNumber());
               return true;
            }

            if (lastNotifiedSeason == null || lastNotifiedEpisode == null) {
               follow.setLastNotifiedSeason(lastEpisode.getSeasonNumber());
               follow.setLastNotifiedEpisode(lastEpisode.getEpisodeNumber());
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private List<TmdbEpisode> findNewEpisodes(TmdbFollow follow, TvSeriesDb tvSeriesDb, Integer lastSeason, Integer lastEpisode) {
      Integer targetSeason = this.resolveLatestSeasonNumber(tvSeriesDb);
      if (targetSeason == null) {
         return List.of();
      } else {
         List<TmdbEpisode> seasonEpisodes = this.tmdbEpisodeService.listByFollowAndSeason(follow.getId(), targetSeason);
         return CollUtil.isEmpty(seasonEpisodes) ? List.of() : seasonEpisodes.stream().filter(ep -> {
            if (ep.getAirDate() != null && ep.getAirDate().after(new Date())) {
               return false;
            } else if (lastSeason != null && lastEpisode != null) {
               return ep.getSeasonNumber() > lastSeason ? true : ep.getSeasonNumber().equals(lastSeason) && ep.getEpisodeNumber() > lastEpisode;
            } else {
               return true;
            }
         }).sorted((a, b) -> {
            int s = Integer.compare(a.getSeasonNumber(), b.getSeasonNumber());
            return s != 0 ? s : Integer.compare(a.getEpisodeNumber(), b.getEpisodeNumber());
         }).collect(Collectors.toList());
      }
   }

   private void sendAggregatedEpisodeNotify(TmdbFollow follow, List<TmdbEpisode> episodes, boolean toGroup) {
      if (!CollUtil.isEmpty(episodes)) {
         TmdbEpisode latest = episodes.get(episodes.size() - 1);
         StringBuilder tvInfoBuilder = new StringBuilder();

         for (TmdbEpisode ep : episodes) {
            String name = StringUtils.hasText(ep.getName()) ? ep.getName() : "最新集";
            String dateStr = ep.getAirDate() != null ? DateUtil.formatDate(ep.getAirDate()) : "";
            tvInfoBuilder.append(String.format("S%02dE%02d - %s %s\n", ep.getSeasonNumber(), ep.getEpisodeNumber(), name, dateStr));
         }

         if (tvInfoBuilder.length() > 0) {
            tvInfoBuilder.setLength(tvInfoBuilder.length() - 1);
         }

         List<String> channels = this.splitChannels(follow.getNotifyChannels());
         if (CollUtil.isEmpty(channels)) {
            log.info("订阅 {} 未配置通知渠道，跳过推送", follow.getName());
         } else {
            SendPhotoRequest sendPhotoRequest = new SendPhotoRequest();
            sendPhotoRequest.setName(follow.getName());
            sendPhotoRequest.setOverview(StringUtils.hasText(latest.getOverview()) ? latest.getOverview() : follow.getOverview());
            sendPhotoRequest.setTmdbUrl("https://www.themoviedb.org/tv/" + follow.getTmdbId());
            sendPhotoRequest.setImgUrl(this.buildFullPath(follow.getPosterPath()));
            sendPhotoRequest.setBackdropPath(this.buildFullPath(follow.getBackdropPath()));
            sendPhotoRequest.setType("tv");
            sendPhotoRequest.setSeasonNumber(latest.getSeasonNumber());
            sendPhotoRequest.setEpisodeNumber(latest.getEpisodeNumber());
            sendPhotoRequest.setTvInfo(tvInfoBuilder.toString());
            sendPhotoRequest.getExtraVariables().put("subscriber", follow.getSubscriberName());
            sendPhotoRequest.setReleaseDate(latest.getAirDate() != null ? DateUtil.formatDate(latest.getAirDate()) : "待定");

            try {
               this.notifyUtils
                  .sendMultiChannel(sendPhotoRequest, "tmdb_follow_update", NotifyMessageType.PHOTO_DETAIL, toGroup, channels.toArray(new String[0]));
            } catch (Exception var10) {
               log.error("发送追剧推送失败：{}", follow.getName(), var10);
            }
         }
      }
   }

   private void syncSingleMovie(TmdbFollow follow, boolean toGroup) throws TmdbException {
      if (!Boolean.TRUE.equals(follow.getReleaseNotified())) {
         MovieDb movieDb = this.tmdbService.getMovieDetails(follow.getTmdbId(), follow.getLanguage(), MovieAppendToResponse.values());
         if (movieDb == null) {
            log.warn("未能从 TMDB 获取电影：{}", follow.getTmdbId());
         } else {
            this.fillFollowWithMovie(follow, movieDb, this.splitChannels(follow.getNotifyChannels()), follow.getSubscriberName(), follow.getLanguage());
            Date releaseDate = follow.getReleaseDate();
            boolean shouldNotifyRelease = releaseDate != null && !releaseDate.after(new Date()) && !Boolean.TRUE.equals(follow.getReleaseNotified());
            if (shouldNotifyRelease) {
               this.sendMovieNotify(follow, movieDb, toGroup);
               follow.setReleaseNotified(true);
            }

            follow.setLastSyncTime(new Date());
            this.updateById(follow);
         }
      }
   }

   private void sendMovieNotify(TmdbFollow follow, MovieDb movieDb, boolean toGroup) {
      List<String> channels = this.splitChannels(follow.getNotifyChannels());
      if (CollUtil.isEmpty(channels)) {
         log.info("订阅 {} 未配置通知渠道，跳过电影推送", follow.getName());
      } else {
         SendPhotoRequest sendPhotoRequest = new SendPhotoRequest();
         sendPhotoRequest.setName(follow.getName());
         sendPhotoRequest.setOverview(StringUtils.hasText(movieDb.getOverview()) ? movieDb.getOverview() : follow.getOverview());
         sendPhotoRequest.setTmdbUrl("https://www.themoviedb.org/movie/" + follow.getTmdbId());
         sendPhotoRequest.setImgUrl(this.buildFullPath(follow.getPosterPath()));
         sendPhotoRequest.setBackdropPath(this.buildFullPath(follow.getBackdropPath()));
         sendPhotoRequest.setType("movie");
         sendPhotoRequest.setReleaseDate(movieDb.getReleaseDate() != null ? movieDb.getReleaseDate() : null);
         sendPhotoRequest.getExtraVariables().put("subscriber", follow.getSubscriberName());
         sendPhotoRequest.getExtraVariables().put("runtime", String.valueOf(movieDb.getRuntime()));

         try {
            this.notifyUtils.sendMultiChannel(sendPhotoRequest, "tmdb_follow_update", NotifyMessageType.PHOTO_DETAIL, toGroup, channels.toArray(new String[0]));
         } catch (Exception var7) {
            log.error("发送电影上映推送失败：{}", follow.getName(), var7);
         }
      }
   }

   private boolean isNewEpisode(TmdbFollow follow, TvEpisode lastEpisode) {
      if (lastEpisode == null) {
         return false;
      } else if (follow.getLastNotifiedSeason() != null && follow.getLastNotifiedEpisode() != null) {
         return !Objects.equals(follow.getLastNotifiedSeason(), lastEpisode.getSeasonNumber())
            ? lastEpisode.getSeasonNumber() > follow.getLastNotifiedSeason()
            : lastEpisode.getEpisodeNumber() > follow.getLastNotifiedEpisode();
      } else {
         return true;
      }
   }

   private boolean isAired(TvEpisode episode) {
      if (episode != null && episode.getAirDate() != null) {
         Date airDate = DateUtil.parse(episode.getAirDate());
         return airDate != null && !airDate.after(new Date());
      } else {
         return false;
      }
   }

   private boolean fillFollowWithSeries(TmdbFollow follow, TvSeriesDb tvSeriesDb, List<String> channels, String subscriberName, String language) {
      boolean changed = false;
      changed |= this.updateIfChanged(follow::getTmdbId, follow::setTmdbId, tvSeriesDb.getId());
      changed |= this.updateIfChanged(follow::getMediaType, follow::setMediaType, "tv");
      changed |= this.updateIfChanged(
         follow::getName, follow::setName, StringUtils.hasText(tvSeriesDb.getName()) ? tvSeriesDb.getName() : tvSeriesDb.getOriginalName()
      );
      changed |= this.updateIfChanged(follow::getOriginalName, follow::setOriginalName, tvSeriesDb.getOriginalName());
      changed |= this.updateIfChanged(follow::getPosterPath, follow::setPosterPath, tvSeriesDb.getPosterPath());
      changed |= this.updateIfChanged(follow::getBackdropPath, follow::setBackdropPath, tvSeriesDb.getBackdropPath());
      changed |= this.updateIfChanged(follow::getOverview, follow::setOverview, tvSeriesDb.getOverview());
      changed |= this.updateIfChanged(follow::getLanguage, follow::setLanguage, language);
      changed |= this.updateIfChanged(follow::getSubscriberName, follow::setSubscriberName, subscriberName);
      changed |= this.updateIfChanged(follow::getNotifyChannels, follow::setNotifyChannels, String.join(",", channels));
      TvEpisode nextEpisode = tvSeriesDb.getNextEpisodeToAir();
      if (nextEpisode != null) {
         changed |= this.updateIfChanged(follow::getNextAirDate, follow::setNextAirDate, this.parseDate(nextEpisode.getAirDate()));
         changed |= this.updateIfChanged(follow::getNextSeasonNumber, follow::setNextSeasonNumber, nextEpisode.getSeasonNumber());
         changed |= this.updateIfChanged(follow::getNextEpisodeNumber, follow::setNextEpisodeNumber, nextEpisode.getEpisodeNumber());
      }

      TvEpisode lastEpisode = tvSeriesDb.getLastEpisodeToAir();
      if (follow.getLastNotifiedEpisode() == null && lastEpisode != null) {
         changed |= this.updateIfChanged(follow::getLastNotifiedSeason, follow::setLastNotifiedSeason, lastEpisode.getSeasonNumber());
         changed |= this.updateIfChanged(follow::getLastNotifiedEpisode, follow::setLastNotifiedEpisode, lastEpisode.getEpisodeNumber());
      }

      return changed;
   }

   private void fillFollowWithMovie(TmdbFollow follow, MovieDb movieDb, List<String> channels, String subscriberName, String language) {
      this.updateIfChanged(follow::getTmdbId, follow::setTmdbId, movieDb.getId());
      this.updateIfChanged(follow::getMediaType, follow::setMediaType, "movie");
      this.updateIfChanged(follow::getName, follow::setName, StringUtils.hasText(movieDb.getTitle()) ? movieDb.getTitle() : movieDb.getOriginalTitle());
      this.updateIfChanged(follow::getOriginalName, follow::setOriginalName, movieDb.getOriginalTitle());
      this.updateIfChanged(follow::getPosterPath, follow::setPosterPath, movieDb.getPosterPath());
      this.updateIfChanged(follow::getBackdropPath, follow::setBackdropPath, movieDb.getBackdropPath());
      this.updateIfChanged(follow::getOverview, follow::setOverview, movieDb.getOverview());
      this.updateIfChanged(follow::getLanguage, follow::setLanguage, language);
      this.updateIfChanged(follow::getSubscriberName, follow::setSubscriberName, subscriberName);
      this.updateIfChanged(follow::getNotifyChannels, follow::setNotifyChannels, String.join(",", channels));
      Date releaseDate = this.parseDate(movieDb.getReleaseDate());
      if (releaseDate != null) {
         this.updateIfChanged(follow::getNextAirDate, follow::setNextAirDate, releaseDate);
      }

      this.updateIfChanged(follow::getReleaseDate, follow::setReleaseDate, releaseDate);
      this.updateIfChanged(follow::getRuntimeMinutes, follow::setRuntimeMinutes, movieDb.getRuntime());
   }

   private <T> boolean updateIfChanged(Supplier<T> getter, Consumer<T> setter, T newValue) {
      T oldValue = getter.get();
      if (!Objects.equals(oldValue, newValue)) {
         setter.accept(newValue);
         return true;
      } else {
         return false;
      }
   }

   private void saveEpisode(TmdbFollow follow, TvEpisode episode) {
      if (episode != null) {
         TmdbEpisode entity = this.buildEpisodeEntity(follow, episode);
         this.tmdbEpisodeService.saveOrUpdateEpisode(entity);
      }
   }

   private TmdbEpisode buildEpisodeEntity(TmdbFollow follow, TvEpisode episode) {
      if (episode == null) {
         return null;
      } else {
         TmdbEpisode entity = new TmdbEpisode();
         entity.setFollowId(follow.getId());
         entity.setTmdbId(follow.getTmdbId());
         entity.setSeasonNumber(episode.getSeasonNumber());
         entity.setEpisodeNumber(episode.getEpisodeNumber());
         entity.setName(episode.getName());
         entity.setOverview(episode.getOverview());
         entity.setAirDate(this.parseDate(episode.getAirDate()));
         entity.setStillPath(episode.getStillPath());
         return entity;
      }
   }

   private TmdbEpisode buildEpisodeEntity(TmdbFollow follow, TvSeasonEpisode episode) {
      if (episode == null) {
         return null;
      } else {
         TmdbEpisode entity = new TmdbEpisode();
         entity.setFollowId(follow.getId());
         entity.setTmdbId(follow.getTmdbId());
         entity.setSeasonNumber(episode.getSeasonNumber());
         entity.setEpisodeNumber(episode.getEpisodeNumber());
         entity.setName(episode.getName());
         entity.setOverview(episode.getOverview());
         entity.setAirDate(this.parseDate(episode.getAirDate()));
         entity.setStillPath(episode.getStillPath());
         return entity;
      }
   }

   private TmdbFollowResponse buildResponse(TmdbFollow follow) {
      TmdbFollowResponse response = BeanUtils.convert(follow, TmdbFollowResponse.class);
      response.setMediaType(follow.getMediaType());
      response.setReleaseDate(follow.getReleaseDate());
      response.setRuntimeMinutes(follow.getRuntimeMinutes());
      response.setNotifyChannels(this.splitChannels(follow.getNotifyChannels()));
      response.setIsSubscribed(true);
      response.setPosterPath(this.buildPosterPath(follow.getPosterPath()));
      response.setBackdropPath(this.buildFullPath(follow.getBackdropPath()));
      response.setLatestSeasonPosterPath(this.buildPosterPath(follow.getLatestSeasonPosterPath()));
      TmdbWatchProgress progress = new LambdaQueryChainWrapper<>(this.tmdbWatchProgressService.getBaseMapper())
         .eq(TmdbWatchProgress::getFollowId, follow.getId())
         .one();
      if (progress != null) {
         response.setProgressSeason(progress.getSeasonNumber());
         response.setProgressEpisode(progress.getEpisodeNumber());
         response.setWatcherName(progress.getWatcherName());
      }

      if (!this.isMovie(follow)) {
         Integer latestSeasonNumber = follow.getLatestSeasonNumber() != null
            ? follow.getLatestSeasonNumber()
            : this.tmdbEpisodeService.findLatestSeasonNumber(follow.getId());
         response.setLatestSeasonNumber(latestSeasonNumber);
         return response;
      } else {
         if (progress != null && StringUtils.hasText(progress.getWatchedEpisodes())) {
            List<String> watchedList = JSON.parseArray(progress.getWatchedEpisodes(), String.class);
            response.setMovieWatched(watchedList.contains("movie"));
         } else {
            response.setMovieWatched(false);
         }

         return response;
      }
   }

   @Override
   public List<TmdbSeasonResponse> getSeasons(Long followId) {
      TmdbFollow follow = this.getById(followId);
      if (follow == null) {
         throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "订阅不存在");
      } else {
         List<TmdbSeason> seasons = this.tmdbSeasonService.listByFollow(followId);
         return CollUtil.isEmpty(seasons) ? List.of() : seasons.stream().map(season -> this.convertSeasonBasic(season)).toList();
      }
   }

   @Override
   public List<TmdbEpisodeResponse> getEpisodes(Long followId, Integer seasonNumber) {
      TmdbFollow follow = this.getById(followId);
      if (follow == null) {
         throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "订阅不存在");
      } else {
         List<TmdbEpisode> episodes = this.tmdbEpisodeService.listByFollowAndSeason(followId, seasonNumber);
         if (CollUtil.isEmpty(episodes)) {
            return List.of();
         } else {
            TmdbWatchProgress progress = new LambdaQueryChainWrapper<>(this.tmdbWatchProgressService.getBaseMapper())
               .eq(TmdbWatchProgress::getFollowId, followId)
               .one();
            List<String> watchedList = new ArrayList<>();
            if (progress != null && StringUtils.hasText(progress.getWatchedEpisodes())) {
               watchedList = JSON.parseArray(progress.getWatchedEpisodes(), String.class);
            }

            List<String> finalWatchedList = watchedList;
            return episodes.stream().map(episode -> {
               TmdbEpisodeResponse response = this.convertEpisode(episode);
               String episodeKey = episode.getSeasonNumber() + "-" + episode.getEpisodeNumber();
               response.setWatched(finalWatchedList.contains(episodeKey));
               return response;
            }).toList();
         }
      }
   }

   private TmdbSeasonResponse convertSeasonBasic(TmdbSeason season) {
      TmdbSeasonResponse response = BeanUtils.convert(season, TmdbSeasonResponse.class);
      response.setPosterPath(this.buildPosterPath(season.getPosterPath()));
      if (StringUtils.hasText(season.getCasts())) {
         List<TmdbCastResponse> casts = JSON.parseArray(season.getCasts(), TmdbCastResponse.class);
         if (CollUtil.isNotEmpty(casts)) {
            casts = casts.stream().limit(10L).toList();
            casts.forEach(cast -> cast.setProfilePath(this.buildPosterPath(cast.getProfilePath())));
            response.setCasts(casts);
         }
      }

      return response;
   }

   private TmdbEpisodeResponse convertEpisode(TmdbEpisode episode) {
      TmdbEpisodeResponse response = BeanUtils.convert(episode, TmdbEpisodeResponse.class);
      response.setStillPath(this.buildPosterPath(episode.getStillPath()));
      return response;
   }

   private boolean syncSeasons(TmdbFollow follow, TvSeriesDb tvSeriesDb) throws TmdbException {
      if (CollUtil.isEmpty(tvSeriesDb.getSeasons())) {
         return false;
      } else {
         Map<Integer, TmdbSeason> seasonMap = this.tmdbSeasonService
            .listByFollow(follow.getId())
            .stream()
            .collect(Collectors.toMap(TmdbSeason::getSeasonNumber, seasonx -> seasonx, (a, b) -> a, HashMap::new));
         Map<String, TmdbEpisode> episodeMap = this.tmdbEpisodeService
            .listByFollow(follow.getId())
            .stream()
            .collect(Collectors.toMap(this::buildEpisodeKey, episodex -> episodex, (a, b) -> a, HashMap::new));
         Integer latestSeasonNumber = this.resolveLatestSeasonNumber(tvSeriesDb);
         boolean updated = false;

         for (TvSeason season : tvSeriesDb.getSeasons()) {
            if (season.getSeasonNumber() != null) {
               TvSeasonDb seasonDb = this.tmdbService
                  .getTvSeasons(tvSeriesDb.getId(), season.getSeasonNumber(), follow.getLanguage(), TvSeasonsAppendToResponse.values());
               if (seasonDb != null) {
                  if (latestSeasonNumber == null || seasonDb.getSeasonNumber() > latestSeasonNumber) {
                     latestSeasonNumber = seasonDb.getSeasonNumber();
                  }

                  TmdbSeason seasonEntity = this.buildSeasonEntity(follow, seasonDb);
                  TmdbSeason existingSeason = seasonMap.get(seasonEntity.getSeasonNumber());
                  if (existingSeason == null) {
                     this.tmdbSeasonService.save(seasonEntity);
                     seasonMap.put(seasonEntity.getSeasonNumber(), seasonEntity);
                     updated = true;
                  } else if (this.isSeasonChanged(existingSeason, seasonEntity)) {
                     seasonEntity.setId(existingSeason.getId());
                     this.tmdbSeasonService.updateById(seasonEntity);
                     seasonMap.put(seasonEntity.getSeasonNumber(), seasonEntity);
                     updated = true;
                  }

                  if (CollUtil.isNotEmpty(seasonDb.getEpisodes())) {
                     for (TvSeasonEpisode episode : seasonDb.getEpisodes()) {
                        TmdbEpisode episodeEntity = this.buildEpisodeEntity(follow, episode);
                        String key = this.buildEpisodeKey(episodeEntity);
                        TmdbEpisode existingEpisode = episodeMap.get(key);
                        if (existingEpisode == null) {
                           this.tmdbEpisodeService.save(episodeEntity);
                           episodeMap.put(key, episodeEntity);
                           updated = true;
                        } else if (this.isEpisodeChanged(existingEpisode, episodeEntity)) {
                           episodeEntity.setId(existingEpisode.getId());
                           this.tmdbEpisodeService.updateById(episodeEntity);
                           episodeMap.put(key, episodeEntity);
                           updated = true;
                        }
                     }
                  }
               }
            }
         }

         if (latestSeasonNumber != null) {
            TmdbSeason latestSeason = seasonMap.get(latestSeasonNumber);
            if (latestSeason != null && this.isLatestSeasonChanged(follow, latestSeason)) {
               follow.setLatestSeasonNumber(latestSeason.getSeasonNumber());
               follow.setLatestSeasonName(latestSeason.getName());
               follow.setLatestSeasonOverview(latestSeason.getOverview());
               follow.setLatestSeasonPosterPath(latestSeason.getPosterPath());
               updated = true;
            }
         }

         return updated;
      }
   }

   private TmdbSeason buildSeasonEntity(TmdbFollow follow, TvSeasonDb seasonDb) {
      TmdbSeason seasonEntity = new TmdbSeason();
      seasonEntity.setFollowId(follow.getId());
      seasonEntity.setTmdbId(follow.getTmdbId());
      seasonEntity.setSeasonNumber(seasonDb.getSeasonNumber());
      seasonEntity.setName(seasonDb.getName());
      seasonEntity.setOverview(seasonDb.getOverview());
      seasonEntity.setPosterPath(seasonDb.getPosterPath());
      seasonEntity.setAirDate(this.parseDate(seasonDb.getAirDate()));
      seasonEntity.setEpisodeCount(seasonDb.getEpisodes() != null ? seasonDb.getEpisodes().size() : 0);
      seasonEntity.setCasts(this.buildCastJson(seasonDb));
      return seasonEntity;
   }

   private String buildEpisodeKey(TmdbEpisode episode) {
      return episode.getSeasonNumber() + "_" + episode.getEpisodeNumber();
   }

   private boolean isEpisodeChanged(TmdbEpisode existing, TmdbEpisode incoming) {
      return incoming == null
         ? false
         : !Objects.equals(existing.getName(), incoming.getName())
            || !Objects.equals(existing.getOverview(), incoming.getOverview())
            || !Objects.equals(existing.getStillPath(), incoming.getStillPath())
            || !Objects.equals(existing.getAirDate(), incoming.getAirDate());
   }

   private boolean isSeasonChanged(TmdbSeason existing, TmdbSeason incoming) {
      return incoming == null
         ? false
         : !Objects.equals(existing.getName(), incoming.getName())
            || !Objects.equals(existing.getOverview(), incoming.getOverview())
            || !Objects.equals(existing.getPosterPath(), incoming.getPosterPath())
            || !Objects.equals(existing.getAirDate(), incoming.getAirDate())
            || !Objects.equals(existing.getEpisodeCount(), incoming.getEpisodeCount())
            || !Objects.equals(existing.getCasts(), incoming.getCasts());
   }

   private boolean isLatestSeasonChanged(TmdbFollow follow, TmdbSeason latestSeason) {
      return !Objects.equals(follow.getLatestSeasonNumber(), latestSeason.getSeasonNumber())
         || !Objects.equals(follow.getLatestSeasonName(), latestSeason.getName())
         || !Objects.equals(follow.getLatestSeasonOverview(), latestSeason.getOverview())
         || !Objects.equals(follow.getLatestSeasonPosterPath(), latestSeason.getPosterPath());
   }

   private Integer resolveLatestSeasonNumber(TvSeriesDb tvSeriesDb) {
      if (tvSeriesDb.getLastEpisodeToAir() != null) {
         return tvSeriesDb.getLastEpisodeToAir().getSeasonNumber();
      } else if (tvSeriesDb.getNextEpisodeToAir() != null) {
         return tvSeriesDb.getNextEpisodeToAir().getSeasonNumber();
      } else {
         return CollUtil.isNotEmpty(tvSeriesDb.getSeasons())
            ? tvSeriesDb.getSeasons()
               .stream()
               .filter(season -> season.getSeasonNumber() != null)
               .max((a, b) -> Integer.compare(a.getSeasonNumber(), b.getSeasonNumber()))
               .map(season -> season.getSeasonNumber())
               .orElse(null)
            : null;
      }
   }

   private String buildCastJson(TvSeasonDb seasonDb) {
      Credits credits = seasonDb.getCredits();
      if (credits != null && !CollUtil.isEmpty(credits.getCast())) {
         List<TmdbCastResponse> casts = credits.getCast()
            .stream()
            .filter(Objects::nonNull)
            .sorted((a, b) -> Integer.compare(a.getOrder(), b.getOrder()))
            .limit(20L)
            .map(this::convertCast)
            .toList();
         return JSON.toJSONString(casts);
      } else {
         return null;
      }
   }

   private TmdbCastResponse convertCast(Cast cast) {
      TmdbCastResponse response = new TmdbCastResponse();
      response.setName(cast.getName());
      response.setCharacter(cast.getCharacter());
      response.setProfilePath(cast.getProfilePath());
      response.setOrder(cast.getOrder());
      return response;
   }

   private List<String> splitChannels(String channels) {
      return !StringUtils.hasText(channels)
         ? List.of()
         : Arrays.stream(channels.split(",")).filter(StringUtils::hasText).map(String::trim).collect(Collectors.toList());
   }

   private String buildFullPath(String path) {
      if (!StringUtils.hasText(path)) {
         return null;
      } else {
         return path.startsWith("http") ? path : this.tmdbImageUrl + path;
      }
   }

   private String buildPosterPath(String path) {
      if (!StringUtils.hasText(path)) {
         return null;
      } else {
         return path.startsWith("http") ? path : "https://image.tmdb.org/t/p/w500" + path;
      }
   }

   private String resolveMediaType(String mediaType) {
      return StringUtils.hasText(mediaType) ? mediaType : "tv";
   }

   private boolean isMovie(TmdbFollow follow) {
      return "movie".equalsIgnoreCase(follow.getMediaType());
   }

   private Date parseDate(String dateStr) {
      return !StringUtils.hasText(dateStr) ? null : DateUtil.parse(dateStr);
   }
}
