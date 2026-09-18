package com.una.embyhub.movie.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;
import com.una.embyhub.movie.entity.MoviePtSubscribeEntity;
import com.una.embyhub.movie.event.MoviePtSubscribeDownloadNotifyEvent;
import com.una.embyhub.movie.event.MoviePtSubscribeSavedEvent;
import com.una.embyhub.movie.mapper.MovieDownloadRecordMapper;
import com.una.embyhub.movie.mapper.MoviePtSubscribeMapper;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieDownloadRecordWithDetailsResponse;
import com.una.embyhub.movie.model.MoviePtDownloadRequest;
import com.una.embyhub.movie.model.MoviePtSearchProgressEvent;
import com.una.embyhub.movie.model.MoviePtSearchResult;
import com.una.embyhub.movie.model.MoviePtSite;
import com.una.embyhub.movie.model.MoviePtSubscribe;
import com.una.embyhub.movie.model.MoviePtSubscribeRequest;
import com.una.embyhub.movie.model.MoviePtSubscribeSearchProgressEvent;
import com.una.embyhub.movie.model.MoviePtSubscribeSearchResponse;
import com.una.embyhub.movie.model.MovieQbittorrentConfig;
import com.una.embyhub.movie.model.MovieScrapePathConfig;
import com.una.embyhub.movie.model.MovieSubscribeQualityConfig;
import com.una.embyhub.movie.service.MovieDownloadRecordService;
import com.una.embyhub.movie.service.MovieNotifyTmdbEnrichService;
import com.una.embyhub.movie.service.MoviePtDownloadService;
import com.una.embyhub.movie.service.MoviePtSearchService;
import com.una.embyhub.movie.service.MoviePtSiteService;
import com.una.embyhub.movie.service.MoviePtSubscribeService;
import com.una.embyhub.movie.service.MovieQbittorrentService;
import com.una.embyhub.movie.service.MovieScrapePathConfigService;
import com.una.embyhub.movie.util.MovieEpisodeParser;
import com.una.embyhub.service.SystemConfigService;
import com.una.embyhub.service.TmdbService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.movies.Translation;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonEpisode;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MoviePtSubscribeServiceImpl implements MoviePtSubscribeService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtSubscribeServiceImpl.class);
   private static final String TYPE_MOVIE = "movie";
   private static final String TYPE_TV = "tv";
   private static final String STATE_NEW = "N";
   private static final String STATE_RUNNING = "R";
   private static final String STATE_STOPPED = "S";
   private static final String STATE_COMPLETED = "C";
   private static final int DEFAULT_LIMIT = 20;
   private static final int MAX_EPISODE_KEYWORDS = 5;
   private static final int MAX_SINGLE_AUTO_DOWNLOAD_ATTEMPTS = 5;
   private static final int MAX_BATCH_AUTO_DOWNLOAD_ATTEMPTS = 5;
   private static final String TMDB_LANGUAGE_ZH = "zh-CN";
   private static final String TMDB_LANGUAGE_EN = "en-US";
   private static final String TMDB_IMAGE_BASE_DEFAULT = "https://image.tmdb.org/t/p/original";
   private static final Pattern EPISODE_CODE_PATTERN = Pattern.compile("(?i)S(\\d{1,3})E(\\d{1,4})");
   private static final String CONFIG_KEY_SUBSCRIBE_QUALITY = "subscribe_quality_config";
   private static final Pattern PUBLISH_DATETIME_PATTERN = Pattern.compile(
      "(?<!\\d)(20\\d{2})[-./](\\d{1,2})[-./](\\d{1,2})(?:[\\sT](\\d{1,2})(?::(\\d{1,2}))?(?::(\\d{1,2}))?)?(?!\\d)"
   );
   private static final Pattern PUBLISH_COMPACT_DATE_PATTERN = Pattern.compile("(?<!\\d)(20\\d{2})(\\d{2})(\\d{2})(?!\\d)");
   private static final Set<String> DOWNLOAD_TRACK_STATUSES = Set.of("PENDING", "DOWNLOADING", "COMPLETED", "LINKED", "LINK_FAILED");
   private static final Set<String> DOWNLOAD_COMPLETED_STATUSES = Set.of("COMPLETED", "LINKED", "LINK_FAILED");
   private final MoviePtSubscribeMapper moviePtSubscribeMapper;
   private final MovieDownloadRecordMapper movieDownloadRecordMapper;
   private final MovieDownloadRecordService movieDownloadRecordService;
   private final MoviePtSearchService moviePtSearchService;
   private final MoviePtDownloadService moviePtDownloadService;
   private final MoviePtSiteService moviePtSiteService;
   private final MovieQbittorrentService movieQbittorrentService;
   private final MovieScrapePathConfigService movieScrapePathConfigService;
   private final TmdbService tmdbService;
   private final MovieNotifyTmdbEnrichService movieNotifyTmdbEnrichService;
   private final NotifyUtils notifyUtils;
   private final SystemConfigService systemConfigService;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;
   private final ApplicationEventPublisher applicationEventPublisher;
   private final ConcurrentMap<Long, ReentrantLock> subscribeSearchLocks = new ConcurrentHashMap<>();
   @Value("${tmdb.imageUrl:https://image.tmdb.org/t/p/original}")
   private String tmdbImageUrl;

   @Override
   public List<MoviePtSubscribe> list(String state) {
      String normalizedState = this.normalizeState(state);
      QueryWrapper<MoviePtSubscribeEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      if (StringUtils.hasText(normalizedState)) {
         wrapper.eq("state", normalizedState);
      }

      wrapper.orderByDesc("update_datetime");
      wrapper.orderByDesc("id");
      Map<Long, String> scrapePathNameMap = this.buildScrapePathNameMap();
      Map<Long, String> downloaderNameMap = this.buildDownloaderNameMap();
      return this.moviePtSubscribeMapper.selectList(wrapper).stream().map(entity -> this.toModel(entity, scrapePathNameMap, downloaderNameMap)).toList();
   }

   @Override
   public MoviePtSubscribe getById(Long id) {
      return this.toModel(this.getEntityById(id), null, null);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public MoviePtSubscribe save(MoviePtSubscribeRequest request) {
      if (request == null) {
         throw new BizException("请求参数不能为空");
      } else {
         MoviePtSubscribeEntity entity;
         if (request.getId() != null) {
            entity = this.getEntityById(request.getId());
         } else {
            entity = new MoviePtSubscribeEntity();
            entity.setState("N");
            entity.setLastMatchedCount(0);
         }

         String name = this.trimToNull(request.getName());
         if (!StringUtils.hasText(name)) {
            throw new BizException("订阅名称不能为空");
         } else {
            String originalTitle = this.trimToNull(request.getOriginalTitle());
            String type = this.normalizeType(request.getType());
            String year = this.trimToNull(request.getYear());
            Integer season = "tv".equals(type) ? this.resolveSeason(request.getSeason(), entity.getSeason()) : null;
            Integer startEpisode = "tv".equals(type) ? this.resolveStartEpisode(request.getStartEpisode(), entity.getStartEpisode()) : null;
            String keyword = this.trimToNull(request.getKeyword());
            if (!StringUtils.hasText(keyword)) {
               keyword = this.buildDefaultKeyword(name, year, season, type);
            }

            List<Long> normalizedSiteIds = this.normalizeSiteIds(request.getSiteId());

            for (Long siteId : normalizedSiteIds) {
               this.moviePtSiteService.getById(siteId);
            }

            Long resolvedScrapePathConfigId = request.getScrapePathConfigId();
            Integer autoDownload = request.getAutoDownload() != null
               ? this.normalizeAutoDownload(request.getAutoDownload())
               : this.normalizeAutoDownload(entity.getAutoDownload());
            if (autoDownload == 1 && resolvedScrapePathConfigId == null) {
               throw new BizException("开启自动下载时 scrapePathConfigId 不能为空");
            } else {
               if (resolvedScrapePathConfigId != null) {
                  this.movieScrapePathConfigService.getById(resolvedScrapePathConfigId);
               }

               Integer enabled = request.getEnabled() != null ? this.normalizeEnabled(request.getEnabled()) : this.normalizeEnabled(entity.getEnabled());
               String state = this.normalizeState(request.getState());
               Long oldTmdbId = entity.getTmdbId();
               Integer oldSeason = entity.getSeason();
               String oldType = this.trimToNull(entity.getType());
               entity.setName(name);
               entity.setOriginalTitle(originalTitle);
               entity.setKeyword(keyword);
               entity.setType(type);
               entity.setYear(year);
               entity.setTmdbId(request.getTmdbId());
               entity.setSeason(season);
               entity.setStartEpisode(startEpisode);
               entity.setSiteId(this.joinSiteIds(normalizedSiteIds));
               entity.setScrapePathConfigId(resolvedScrapePathConfigId);
               entity.setDownloaderId(request.getDownloaderId());
               entity.setAutoDownload(autoDownload);
               entity.setEnabled(enabled);
               String requestPosterPath = this.trimToNull(request.getPosterPath());
               String requestBackdropPath = this.trimToNull(request.getBackdropPath());
               boolean requestPosterSpecified = requestPosterPath != null;
               boolean requestBackdropSpecified = requestBackdropPath != null;
               String resolvedPosterPath = requestPosterSpecified ? this.normalizeTmdbImageUrl(requestPosterPath) : this.trimToNull(entity.getPosterPath());
               String resolvedBackdropPath = requestBackdropSpecified
                  ? this.normalizeTmdbImageUrl(requestBackdropPath)
                  : this.trimToNull(entity.getBackdropPath());
               boolean tmdbChanged = !Objects.equals(oldTmdbId, entity.getTmdbId());
               boolean typeChanged = !Objects.equals(oldType, type);
               if (entity.getTmdbId() != null
                  && (tmdbChanged || typeChanged || !StringUtils.hasText(resolvedPosterPath) || !StringUtils.hasText(resolvedBackdropPath))) {
                  MoviePtSubscribeServiceImpl.TmdbImagePaths tmdbImagePaths = this.loadTmdbImagePaths(entity.getTmdbId(), type);
                  if (!requestPosterSpecified && (tmdbChanged || typeChanged || !StringUtils.hasText(resolvedPosterPath))) {
                     resolvedPosterPath = tmdbImagePaths.posterPath();
                  }

                  if (!requestBackdropSpecified && (tmdbChanged || typeChanged || !StringUtils.hasText(resolvedBackdropPath))) {
                     resolvedBackdropPath = tmdbImagePaths.backdropPath();
                  }
               } else if (entity.getTmdbId() == null && (tmdbChanged || typeChanged)) {
                  if (!requestPosterSpecified) {
                     resolvedPosterPath = null;
                  }

                  if (!requestBackdropSpecified) {
                     resolvedBackdropPath = null;
                  }
               }

               entity.setPosterPath(resolvedPosterPath);
               entity.setBackdropPath(resolvedBackdropPath);
               if (!"tv".equals(type)) {
                  entity.setSeason(null);
                  entity.setStartEpisode(null);
                  entity.setTmdbLatestEpisode(null);
                  entity.setLastDownloadedEpisode(0);
               } else if (!Objects.equals(oldTmdbId, entity.getTmdbId()) || !Objects.equals(oldSeason, entity.getSeason())) {
                  entity.setTmdbLatestEpisode(null);
                  entity.setLastDownloadedEpisode(0);
               }

               if (StringUtils.hasText(state)) {
                  entity.setState(state);
               } else if (entity.getState() == null) {
                  entity.setState("N");
               }

               boolean created = entity.getId() == null;
               if (created) {
                  this.moviePtSubscribeMapper.insert(entity);
               } else {
                  this.moviePtSubscribeMapper.updateById(entity);
               }

               this.publishSubscribeSavedEvent(entity, created);
               return this.toModel(entity, null, null);
            }
         }
      }
   }

   private void publishSubscribeSavedEvent(MoviePtSubscribeEntity entity, boolean created) {
      MoviePtSubscribeEntity snapshot = new MoviePtSubscribeEntity();
      BeanUtils.copyProperties(entity, snapshot);
      this.applicationEventPublisher.publishEvent(new MoviePtSubscribeSavedEvent(snapshot, created));
   }

   private void publishSubscribeDownloadNotifyEvent(MoviePtSubscribeEntity subscribe, MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution execution) {
      if (subscribe != null && execution != null) {
         List<MoviePtSearchResult> notifyResults = execution.successfulResults() == null
            ? List.of()
            : execution.successfulResults().stream().filter(Objects::nonNull).toList();
         if (CollectionUtils.isEmpty(notifyResults) && execution.selectedResult() != null) {
            notifyResults = List.of(execution.selectedResult());
         }

         if (!CollectionUtils.isEmpty(notifyResults)) {
            MoviePtSubscribeEntity snapshot = new MoviePtSubscribeEntity();
            BeanUtils.copyProperties(subscribe, snapshot);
            String summary = execution.summaryResult() != null ? execution.summaryResult().getMessage() : "";
            this.applicationEventPublisher
               .publishEvent(new MoviePtSubscribeDownloadNotifyEvent(snapshot, List.copyOf(notifyResults), summary, execution.downloadedEpisodes()));
         }
      }
   }

   @Async
   @TransactionalEventListener(
      phase = TransactionPhase.AFTER_COMMIT
   )
   public void onMoviePtSubscribeSaved(MoviePtSubscribeSavedEvent event) {
      if (event != null && event.subscribe() != null && event.created()) {
         this.sendSubscribeAddedNotify(event.subscribe());
      }
   }

   @Async
   @TransactionalEventListener(
      phase = TransactionPhase.AFTER_COMMIT
   )
   public void onMoviePtSubscribeDownloadNotify(MoviePtSubscribeDownloadNotifyEvent event) {
      if (event != null && event.subscribe() != null && !CollectionUtils.isEmpty(event.notifyResults())) {
         this.sendSubscribeDownloadNotify(event.subscribe(), event.notifyResults(), event.summary(), event.downloadedEpisodes());
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void delete(Long id) {
      this.getEntityById(id);
      this.moviePtSubscribeMapper.deleteById(id);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void deleteByTmdbId(Long tmdbId, String type, Integer season) {
      if (tmdbId == null) {
         throw new BizException("TMDB ID 不能为空");
      } else {
         LambdaQueryWrapper<MoviePtSubscribeEntity> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(MoviePtSubscribeEntity::getTmdbId, tmdbId);
         if (StringUtils.hasText(type)) {
            wrapper.eq(MoviePtSubscribeEntity::getType, this.normalizeType(type));
         }

         if (season != null) {
            wrapper.eq(MoviePtSubscribeEntity::getSeason, season);
         }

         this.moviePtSubscribeMapper.delete(wrapper);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public MoviePtSubscribeSearchResponse searchOnce(Long id, Integer limit, Boolean autoDownload, String title, String originalTitle, String year, String type) {
      return this.searchOnceWithProgress(id, limit, autoDownload, title, originalTitle, year, type, null);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public MoviePtSubscribeSearchResponse searchOnceWithProgress(
      Long id,
      Integer limit,
      Boolean autoDownload,
      String title,
      String originalTitle,
      String year,
      String type,
      Consumer<MoviePtSubscribeSearchProgressEvent> progressConsumer
   ) {
      MoviePtSubscribeEntity entity = this.getEntityById(id);
      MoviePtSubscribeServiceImpl.SearchFilter searchFilter = this.resolveSearchFilter(entity, title, originalTitle, year, type);
      return this.runSubscribeSearchWithLock(entity, limit, autoDownload, "manual", progressConsumer, searchFilter);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public List<MoviePtSubscribeSearchResponse> searchAll(Integer limit, Boolean autoDownload) {
      QueryWrapper<MoviePtSubscribeEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      wrapper.eq("enabled", Integer.valueOf(1));
      wrapper.and(item -> item.isNull("state").or().notIn("state", new Object[]{"S", "C"}));
      wrapper.orderByAsc("id");
      List<MoviePtSubscribeEntity> subscribes = this.moviePtSubscribeMapper.selectList(wrapper);
      return subscribes.stream()
         .map(
            subscribe -> {
               try {
                  MoviePtSubscribeServiceImpl.SearchFilter searchFilter = this.resolveSearchFilter(subscribe, null, null, null, null);
                  return this.runSubscribeSearchWithLock(subscribe, limit, autoDownload, "schedule", null, searchFilter);
               } catch (Exception var6) {
                  var6.printStackTrace();
                  String errorMessage = this.trimToNull(var6.getMessage());
                  if (!StringUtils.hasText(errorMessage)) {
                     errorMessage = var6.getClass().getSimpleName();
                  }

                  log.warn("执行订阅搜索失败 subscribeId={} : {}", subscribe.getId(), errorMessage);
                  this.updateSubscribeLastError(subscribe.getId(), errorMessage);
                  return MoviePtSubscribeSearchResponse.builder()
                     .subscribeId(subscribe.getId())
                     .subscribeName(subscribe.getName())
                     .keyword(subscribe.getKeyword())
                     .startEpisode(subscribe.getStartEpisode())
                     .state(subscribe.getState())
                     .matchedCount(0)
                     .autoDownloadTriggered(false)
                     .message("执行失败: " + errorMessage)
                     .build();
               }
            }
         )
         .toList();
   }

   private MoviePtSubscribeSearchResponse runSubscribeSearchWithLock(
      MoviePtSubscribeEntity subscribe,
      Integer limit,
      Boolean autoDownloadOverride,
      String triggerSource,
      Consumer<MoviePtSubscribeSearchProgressEvent> progressConsumer,
      MoviePtSubscribeServiceImpl.SearchFilter searchFilter
   ) {
      if (subscribe != null && subscribe.getId() != null) {
         Long subscribeId = subscribe.getId();
         ReentrantLock lock = this.subscribeSearchLocks.computeIfAbsent(subscribeId, key -> new ReentrantLock());
         boolean acquired = lock.tryLock();
         if (!acquired) {
            log.info("订阅搜索正在执行，跳过重复触发 subscribeId={} source={}", subscribeId, triggerSource);
            return this.buildSkippedSearchResponse(subscribe, "订阅搜索正在执行中，已跳过重复触发");
         } else {
            MoviePtSubscribeSearchResponse var10;
            try {
               var10 = this.runSubscribeSearch(subscribe, limit, autoDownloadOverride, progressConsumer, searchFilter);
            } finally {
               lock.unlock();
               if (!lock.isLocked() && !lock.hasQueuedThreads()) {
                  this.subscribeSearchLocks.remove(subscribeId, lock);
               }
            }

            return var10;
         }
      } else {
         throw new BizException("订阅不存在");
      }
   }

   private MoviePtSubscribeSearchResponse buildSkippedSearchResponse(MoviePtSubscribeEntity subscribe, String message) {
      return subscribe == null
         ? MoviePtSubscribeSearchResponse.builder().matchedCount(0).autoDownloadTriggered(false).message(message).build()
         : MoviePtSubscribeSearchResponse.builder()
            .subscribeId(subscribe.getId())
            .subscribeName(subscribe.getName())
            .keyword(subscribe.getKeyword())
            .startEpisode(subscribe.getStartEpisode())
            .state(subscribe.getState())
            .matchedCount(0)
            .autoDownloadTriggered(false)
            .message(message)
            .build();
   }

   private MoviePtSubscribeServiceImpl.SearchFilter resolveSearchFilter(
      MoviePtSubscribeEntity subscribe, String title, String originalTitle, String year, String type
   ) {
      String resolvedTitle = this.firstNonBlank(title, subscribe == null ? null : subscribe.getName());
      String resolvedOriginalTitle = this.firstNonBlank(originalTitle, subscribe == null ? null : subscribe.getOriginalTitle());
      String resolvedYear = this.firstNonBlank(year, subscribe == null ? null : subscribe.getYear());
      String resolvedType = this.firstNonBlank(type, subscribe == null ? null : subscribe.getType());
      return new MoviePtSubscribeServiceImpl.SearchFilter(resolvedTitle, resolvedOriginalTitle, resolvedYear, resolvedType);
   }

   @Override
   public Page<MovieDownloadRecordWithDetailsResponse> pageMatchedDownloadRecords(Long id, long current, long size) {
      MoviePtSubscribeEntity subscribe = this.getEntityById(id);
      return this.movieDownloadRecordService.pageWithDetailsBySubscribeId(subscribe.getId(), current, size);
   }

   private MoviePtSubscribeSearchResponse runSubscribeSearch(
      MoviePtSubscribeEntity subscribe,
      Integer limit,
      Boolean autoDownloadOverride,
      Consumer<MoviePtSubscribeSearchProgressEvent> progressConsumer,
      MoviePtSubscribeServiceImpl.SearchFilter searchFilter
   ) {
      if (subscribe == null) {
         throw new BizException("订阅不存在");
      } else if (!this.isEnabled(subscribe)) {
         return MoviePtSubscribeSearchResponse.builder()
            .subscribeId(subscribe.getId())
            .subscribeName(subscribe.getName())
            .keyword(subscribe.getKeyword())
            .startEpisode(subscribe.getStartEpisode())
            .state(subscribe.getState())
            .matchedCount(0)
            .autoDownloadTriggered(false)
            .message("订阅未启用，已跳过")
            .build();
      } else if ("S".equalsIgnoreCase(subscribe.getState())) {
         return MoviePtSubscribeSearchResponse.builder()
            .subscribeId(subscribe.getId())
            .subscribeName(subscribe.getName())
            .keyword(subscribe.getKeyword())
            .startEpisode(subscribe.getStartEpisode())
            .state(subscribe.getState())
            .matchedCount(0)
            .autoDownloadTriggered(false)
            .message("订阅处于暂停状态，已跳过")
            .build();
      } else {
         MoviePtSubscribeServiceImpl.SubscribeSearchPlan searchPlan = this.buildSearchPlan(subscribe);
         String keyword = searchPlan.keyword();
         int finalLimit = this.normalizeLimit(limit);
         subscribe.setState("R");
         subscribe.setLastSearchTime(new Date());
         subscribe.setTmdbLatestEpisode(searchPlan.tmdbLatestEpisode());
         List<Integer> downloadedEpisodes = this.normalizeEpisodeList(searchPlan.downloadedEpisodes());
         List<Integer> remainingMissingEpisodes = this.normalizeEpisodeList(searchPlan.missingEpisodes());
         subscribe.setLastDownloadedEpisode(this.resolveMaxEpisode(downloadedEpisodes));
         if (searchPlan.skipSearch()) {
            subscribe.setLastMatchedCount(0);
            subscribe.setLastError(this.trimToNull(searchPlan.skipMessage()));
            this.updateSubscribeStateAfterSearch(subscribe, remainingMissingEpisodes);
            this.moviePtSubscribeMapper.updateById(subscribe);
            return MoviePtSubscribeSearchResponse.builder()
               .subscribeId(subscribe.getId())
               .subscribeName(subscribe.getName())
               .keyword(keyword)
               .searchedKeywords(searchPlan.searchedKeywords())
               .state(subscribe.getState())
               .matchedCount(0)
               .tmdbLatestEpisode(searchPlan.tmdbLatestEpisode())
               .startEpisode(subscribe.getStartEpisode())
               .downloadedEpisodes(downloadedEpisodes)
               .missingEpisodes(remainingMissingEpisodes)
               .autoDownloadTriggered(false)
               .message(searchPlan.skipMessage())
               .build();
         } else {
            List<MoviePtSearchResult> results = this.searchByKeywords(
               searchPlan.searchedKeywords(),
               this.parseSiteIds(subscribe.getSiteId()),
               finalLimit,
               subscribe.getId(),
               autoDownloadOverride,
               progressConsumer,
               searchFilter
            );
            results = this.filterSearchResults(subscribe, results, searchPlan);
            subscribe.setLastMatchedCount(results.size());
            boolean shouldAutoDownload = this.resolveAutoDownload(autoDownloadOverride, subscribe);
            MoviePtSearchResult selected = this.selectBestResult(results, subscribe, remainingMissingEpisodes);
            MovieActionResponse downloadResult = null;
            if (results.isEmpty()) {
               subscribe.setLastError("未搜索到资源");
            } else if (shouldAutoDownload) {
               MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution execution = this.executeBatchAutoDownload(subscribe, results, remainingMissingEpisodes);
               selected = execution.selectedResult();
               downloadResult = execution.summaryResult();
               downloadedEpisodes = this.mergeEpisodes(downloadedEpisodes, execution.downloadedEpisodes());
               remainingMissingEpisodes = this.normalizeEpisodeList(execution.remainingMissingEpisodes());
               subscribe.setLastDownloadedEpisode(this.resolveMaxEpisode(downloadedEpisodes));
               if (execution.successCount() > 0) {
                  subscribe.setLastDownloadTime(new Date());
                  this.advanceStartEpisodeAfterDownloadSuccess(subscribe, downloadedEpisodes, remainingMissingEpisodes);
                  this.publishSubscribeDownloadNotifyEvent(subscribe, execution);
                  subscribe.setLastError(null);
               } else {
                  subscribe.setLastError(this.trimToNull(execution.lastError()));
               }
            } else {
               subscribe.setLastError(null);
            }

            this.updateSubscribeStateAfterSearch(subscribe, remainingMissingEpisodes);
            this.moviePtSubscribeMapper.updateById(subscribe);
            return MoviePtSubscribeSearchResponse.builder()
               .subscribeId(subscribe.getId())
               .subscribeName(subscribe.getName())
               .keyword(keyword)
               .searchedKeywords(searchPlan.searchedKeywords())
               .state(subscribe.getState())
               .matchedCount(results.size())
               .tmdbLatestEpisode(searchPlan.tmdbLatestEpisode())
               .startEpisode(subscribe.getStartEpisode())
               .downloadedEpisodes(downloadedEpisodes)
               .missingEpisodes(remainingMissingEpisodes)
               .autoDownloadTriggered(shouldAutoDownload)
               .selectedResult(selected)
               .downloadResult(downloadResult)
               .results(results)
               .message(
                  this.buildSearchMessage(
                     results.size(), shouldAutoDownload, downloadResult, subscribe.getTmdbLatestEpisode(), downloadedEpisodes, remainingMissingEpisodes
                  )
               )
               .build();
         }
      }
   }

   private void updateSubscribeLastError(Long subscribeId, String lastError) {
      if (subscribeId != null) {
         LambdaUpdateWrapper<MoviePtSubscribeEntity> updateWrapper = new LambdaUpdateWrapper<>();
         updateWrapper.eq(MoviePtSubscribeEntity::getId, subscribeId).set(MoviePtSubscribeEntity::getLastError, this.trimToNull(lastError));
         this.moviePtSubscribeMapper.update(null, updateWrapper);
      }
   }

   private MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution executeBatchAutoDownload(
      MoviePtSubscribeEntity subscribe, List<MoviePtSearchResult> results, List<Integer> missingEpisodes
   ) {
      List<Integer> normalizedMissingEpisodes = this.normalizeEpisodeList(missingEpisodes);
      if (CollectionUtils.isEmpty(results)) {
         String message = "未搜索到资源";
         return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
            null, MovieActionResponse.builder().success(false).message(message).build(), List.of(), normalizedMissingEpisodes, 0, 0, 0, message, List.of()
         );
      } else if (subscribe.getScrapePathConfigId() == null) {
         String message = "订阅未配置刮削路径，无法自动下载";
         return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
            null, MovieActionResponse.builder().success(false).message(message).build(), List.of(), normalizedMissingEpisodes, 0, 0, 0, message, List.of()
         );
      } else {
         boolean tvBatchMode = this.isTvSubscribe(subscribe) && !CollectionUtils.isEmpty(normalizedMissingEpisodes);
         if (!tvBatchMode) {
            if (this.isMovieSubscribe(subscribe) && this.hasTrackedMovieDownload(subscribe)) {
               String message = "已有对应电影下载任务，跳过重复提交";
               return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
                  null, MovieActionResponse.builder().success(true).message(message).build(), List.of(), List.of(), 0, 0, 0, message, List.of()
               );
            } else {
               List<MoviePtSearchResult> orderedCandidates = this.buildSingleModeRetryCandidates(results);
               if (CollectionUtils.isEmpty(orderedCandidates)) {
                  String message = "未选择到可下载资源";
                  return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
                     null, MovieActionResponse.builder().success(false).message(message).build(), List.of(), List.of(), 0, 0, 0, message, List.of()
                  );
               } else {
                  MoviePtSearchResult firstAttempted = null;
                  MoviePtSearchResult firstTracked = null;
                  Set<Integer> trackedEpisodes = new LinkedHashSet<>();
                  int attemptCount = 0;
                  int failureCount = 0;
                  String lastError = null;

                  for (MoviePtSearchResult candidate : orderedCandidates) {
                     if (attemptCount >= 5) {
                        break;
                     }

                     if (this.isTvResultAlreadyTracked(subscribe, candidate)) {
                        if (firstTracked == null) {
                           firstTracked = candidate;
                        }

                        trackedEpisodes.addAll(this.extractResultEpisodes(candidate, subscribe));
                     } else {
                        if (firstAttempted == null) {
                           firstAttempted = candidate;
                        }

                        MovieActionResponse response = this.triggerDownload(subscribe, candidate);
                        attemptCount++;
                        if (response != null && response.isSuccess()) {
                           int successCount = 1;
                           String message = this.buildBatchAutoDownloadMessage(false, attemptCount, successCount, failureCount, 0);
                           Set<Integer> selectedEpisodes = this.extractResultEpisodes(candidate, subscribe);
                           return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
                              candidate,
                              MovieActionResponse.builder().success(true).message(message).build(),
                              this.normalizeEpisodeList(new ArrayList<>(selectedEpisodes)),
                              List.of(),
                              attemptCount,
                              successCount,
                              failureCount,
                              null,
                              List.of(candidate)
                           );
                        }

                        failureCount++;
                        String singleError = response == null ? null : this.trimToNull(response.getMessage());
                        lastError = StringUtils.hasText(singleError) ? singleError : "自动下载失败";
                     }
                  }

                  if (attemptCount <= 0 && !CollectionUtils.isEmpty(trackedEpisodes)) {
                     String message = "已有对应剧集下载任务，跳过重复提交";
                     return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
                        firstTracked,
                        MovieActionResponse.builder().success(true).message(message).build(),
                        this.normalizeEpisodeList(new ArrayList<>(trackedEpisodes)),
                        List.of(),
                        0,
                        0,
                        0,
                        message,
                        List.of()
                     );
                  } else {
                     String message = this.buildBatchAutoDownloadMessage(false, attemptCount, 0, failureCount, 0);
                     if (!StringUtils.hasText(lastError)) {
                        lastError = message;
                     }

                     return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
                        firstAttempted != null ? firstAttempted : firstTracked,
                        MovieActionResponse.builder().success(false).message(message).build(),
                        List.of(),
                        List.of(),
                        attemptCount,
                        0,
                        failureCount,
                        lastError,
                        List.of()
                     );
                  }
               }
            }
         } else {
            Set<Integer> initialMissingSet = new LinkedHashSet<>(normalizedMissingEpisodes);
            Map<String, MoviePtSearchResult> candidateByKey = new LinkedHashMap<>();
            Map<String, Set<Integer>> coverageByKey = new LinkedHashMap<>();

            for (MoviePtSearchResult result : results) {
               Set<Integer> covered = this.resolveCoveredMissingEpisodes(result, subscribe, initialMissingSet);
               if (!CollectionUtils.isEmpty(covered)) {
                  String key = this.buildResultUniqueKey(result);
                  if (!candidateByKey.containsKey(key)) {
                     candidateByKey.put(key, result);
                     coverageByKey.put(key, new LinkedHashSet<>(covered));
                  } else {
                     Set<Integer> merged = new LinkedHashSet<>(coverageByKey.getOrDefault(key, Set.of()));
                     merged.addAll(covered);
                     coverageByKey.put(key, merged);
                     MoviePtSearchResult better = this.pickBetterResult(candidateByKey.get(key), result, subscribe, initialMissingSet);
                     candidateByKey.put(key, better);
                  }
               }
            }

            if (coverageByKey.isEmpty()) {
               String message = "未命中可补缺资源，未触发自动下载";
               return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
                  null,
                  MovieActionResponse.builder().success(false).message(message).build(),
                  List.of(),
                  normalizedMissingEpisodes,
                  0,
                  0,
                  0,
                  message,
                  List.of()
               );
            } else {
               int attemptCount = 0;
               int successCount = 0;
               int failureCount = 0;
               String lastError = null;
               List<Integer> downloadedEpisodes = new ArrayList<>();
               List<MoviePtSearchResult> successfulResults = new ArrayList<>();
               Set<Integer> remainingMissingSet = new LinkedHashSet<>(initialMissingSet);
               Set<String> attemptedKeys = new LinkedHashSet<>();
               MoviePtSearchResult firstAttempted = null;
               MoviePtSearchResult firstSuccess = null;

               while (attemptCount < 5 && !remainingMissingSet.isEmpty()) {
                  String selectedKey = null;
                  MoviePtSearchResult selectedCandidate = null;
                  Set<Integer> selectedCoverage = Set.of();

                  for (Entry<String, MoviePtSearchResult> entry : candidateByKey.entrySet()) {
                     String key = entry.getKey();
                     if (StringUtils.hasText(key) && !attemptedKeys.contains(key)) {
                        Set<Integer> coverage = coverageByKey.getOrDefault(key, Set.of());
                        if (!CollectionUtils.isEmpty(coverage)) {
                           Set<Integer> uncoveredEpisodes = coverage.stream()
                              .filter(remainingMissingSet::contains)
                              .collect(Collectors.toCollection(LinkedHashSet::new));
                           if (!CollectionUtils.isEmpty(uncoveredEpisodes)) {
                              if (selectedCandidate == null) {
                                 selectedKey = key;
                                 selectedCandidate = entry.getValue();
                                 selectedCoverage = uncoveredEpisodes;
                              } else if (uncoveredEpisodes.size() > selectedCoverage.size()) {
                                 selectedKey = key;
                                 selectedCandidate = entry.getValue();
                                 selectedCoverage = uncoveredEpisodes;
                              } else if (uncoveredEpisodes.size() >= selectedCoverage.size()) {
                                 MoviePtSearchResult better = this.pickBetterResult(selectedCandidate, entry.getValue(), subscribe, remainingMissingSet);
                                 if (better == entry.getValue()) {
                                    selectedKey = key;
                                    selectedCandidate = entry.getValue();
                                    selectedCoverage = uncoveredEpisodes;
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (!StringUtils.hasText(selectedKey) || selectedCandidate == null || CollectionUtils.isEmpty(selectedCoverage)) {
                     break;
                  }

                  attemptedKeys.add(selectedKey);
                  attemptCount++;
                  if (firstAttempted == null) {
                     firstAttempted = selectedCandidate;
                  }

                  MovieActionResponse singleResult = this.triggerDownload(subscribe, selectedCandidate);
                  if (singleResult != null && singleResult.isSuccess()) {
                     successCount++;
                     if (firstSuccess == null) {
                        firstSuccess = selectedCandidate;
                     }

                     successfulResults.add(selectedCandidate);
                     remainingMissingSet.removeAll(selectedCoverage);
                     downloadedEpisodes.addAll(selectedCoverage);
                  } else {
                     failureCount++;
                     String singleError = singleResult == null ? null : this.trimToNull(singleResult.getMessage());
                     lastError = StringUtils.hasText(singleError) ? singleError : "自动下载失败";
                  }
               }

               List<Integer> downloadedEpisodeList = this.normalizeEpisodeList(downloadedEpisodes);
               List<Integer> remainingEpisodeList = remainingMissingSet.stream().sorted().toList();
               String summaryMessage = this.buildBatchAutoDownloadMessage(true, attemptCount, successCount, failureCount, remainingEpisodeList.size());
               MovieActionResponse summaryResult = MovieActionResponse.builder().success(successCount > 0).message(summaryMessage).build();
               if (!StringUtils.hasText(lastError) && successCount <= 0) {
                  lastError = summaryMessage;
               }

               return new MoviePtSubscribeServiceImpl.BatchAutoDownloadExecution(
                  firstSuccess != null ? firstSuccess : firstAttempted,
                  summaryResult,
                  downloadedEpisodeList,
                  remainingEpisodeList,
                  attemptCount,
                  successCount,
                  failureCount,
                  lastError,
                  List.copyOf(successfulResults)
               );
            }
         }
      }
   }

   private LinkedHashMap<String, MoviePtSearchResult> planBatchDownloadResults(
      MoviePtSubscribeEntity subscribe,
      Map<String, MoviePtSearchResult> candidateByKey,
      Map<String, Set<Integer>> coverageByKey,
      Set<Integer> initialMissingSet
   ) {
      LinkedHashMap<String, MoviePtSearchResult> plannedResultsByKey = new LinkedHashMap<>();
      if (!CollectionUtils.isEmpty(candidateByKey) && !CollectionUtils.isEmpty(initialMissingSet)) {
         Set<Integer> remainingMissingSet = new LinkedHashSet<>(initialMissingSet);

         while (!remainingMissingSet.isEmpty() && plannedResultsByKey.size() < 5) {
            String bestKey = null;
            MoviePtSearchResult bestResult = null;
            Set<Integer> bestUncoveredEpisodes = Set.of();

            for (Entry<String, MoviePtSearchResult> candidateEntry : candidateByKey.entrySet()) {
               String candidateKey = candidateEntry.getKey();
               MoviePtSearchResult candidate = candidateEntry.getValue();
               Set<Integer> coverage = coverageByKey.getOrDefault(candidateKey, Set.of());
               if (!CollectionUtils.isEmpty(coverage)) {
                  Set<Integer> uncoveredEpisodes = coverage.stream().filter(remainingMissingSet::contains).collect(Collectors.toCollection(LinkedHashSet::new));
                  if (!CollectionUtils.isEmpty(uncoveredEpisodes)) {
                     if (bestResult == null) {
                        bestKey = candidateKey;
                        bestResult = candidate;
                        bestUncoveredEpisodes = uncoveredEpisodes;
                     } else if (uncoveredEpisodes.size() > bestUncoveredEpisodes.size()) {
                        bestKey = candidateKey;
                        bestResult = candidate;
                        bestUncoveredEpisodes = uncoveredEpisodes;
                     } else if (uncoveredEpisodes.size() >= bestUncoveredEpisodes.size()) {
                        MoviePtSearchResult better = this.pickBetterResult(bestResult, candidate, subscribe, remainingMissingSet);
                        if (better == candidate) {
                           bestKey = candidateKey;
                           bestResult = candidate;
                           bestUncoveredEpisodes = uncoveredEpisodes;
                        }
                     }
                  }
               }
            }

            if (!StringUtils.hasText(bestKey) || bestResult == null || CollectionUtils.isEmpty(bestUncoveredEpisodes)) {
               break;
            }

            plannedResultsByKey.put(bestKey, bestResult);
            remainingMissingSet.removeAll(bestUncoveredEpisodes);
         }

         return plannedResultsByKey;
      } else {
         return plannedResultsByKey;
      }
   }

   private Set<Integer> resolveCoveredMissingEpisodes(MoviePtSearchResult result, MoviePtSubscribeEntity subscribe, Set<Integer> remainingMissingEpisodes) {
      if (result != null && subscribe != null && this.isTvSubscribe(subscribe) && !CollectionUtils.isEmpty(remainingMissingEpisodes)) {
         MovieEpisodeParser.EpisodeMeta meta = this.parseEpisodeMeta(result);
         if (meta == null) {
            return Set.of();
         } else {
            Integer parsedSeason = meta.getBeginSeason();
            if (subscribe.getSeason() != null && parsedSeason != null && !subscribe.getSeason().equals(parsedSeason)) {
               return Set.of();
            } else if (meta.hasEpisode()) {
               Set<Integer> resultEpisodes = this.extractEpisodeSet(meta);
               if (CollectionUtils.isEmpty(resultEpisodes)) {
                  return Set.of();
               } else {
                  return (Set<Integer>)(!remainingMissingEpisodes.containsAll(resultEpisodes) ? Set.of() : new LinkedHashSet<>(resultEpisodes));
               }
            } else {
               return (Set<Integer>)(parsedSeason == null ? Set.of() : new LinkedHashSet<>(remainingMissingEpisodes));
            }
         }
      } else {
         return Set.of();
      }
   }

   private List<Integer> mergeEpisodes(List<Integer> baseEpisodes, List<Integer> appendEpisodes) {
      Set<Integer> merged = new LinkedHashSet<>();
      merged.addAll(this.normalizeEpisodeList(baseEpisodes));
      merged.addAll(this.normalizeEpisodeList(appendEpisodes));
      return merged.stream().sorted().toList();
   }

   private List<Integer> normalizeEpisodeList(List<Integer> episodes) {
      return CollectionUtils.isEmpty(episodes) ? List.of() : episodes.stream().filter(Objects::nonNull).filter(item -> item > 0).distinct().sorted().toList();
   }

   private String buildBatchAutoDownloadMessage(boolean tvBatchMode, int attemptCount, int successCount, int failureCount, int remainingCount) {
      if (attemptCount <= 0) {
         return tvBatchMode ? "未命中可补缺资源，未触发自动下载" : "未选择到可下载资源";
      } else if (successCount <= 0) {
         return "自动下载失败，已尝试 " + attemptCount + " 条资源";
      } else if (!tvBatchMode) {
         return failureCount > 0 ? "自动下载部分成功，成功 " + successCount + " 条，失败 " + failureCount + " 条" : "自动下载成功，已下载 1 条资源";
      } else if (failureCount > 0) {
         return "自动下载部分成功，成功 " + successCount + " 条，失败 " + failureCount + " 条，剩余缺失 " + remainingCount + " 集";
      } else {
         return remainingCount > 0 ? "自动下载成功 " + successCount + " 条，剩余缺失 " + remainingCount + " 集" : "自动下载完成，本轮已补齐缺失剧集，共下载 " + successCount + " 条";
      }
   }

   private MovieActionResponse triggerDownload(MoviePtSubscribeEntity subscribe, MoviePtSearchResult selected) {
      if (selected == null) {
         return MovieActionResponse.builder().success(false).message("未选择到可下载资源").build();
      } else if (subscribe.getScrapePathConfigId() == null) {
         return MovieActionResponse.builder().success(false).message("订阅未配置刮削路径，无法自动下载").build();
      } else {
         MoviePtDownloadRequest request = new MoviePtDownloadRequest();
         List<Long> subscribeSiteIds = this.parseSiteIds(subscribe.getSiteId());
         request.setSiteId(selected.getSiteId() != null ? selected.getSiteId() : this.pickFirstSiteId(subscribeSiteIds));
         request.setTmdbId(subscribe.getTmdbId());
         request.setMediaType(subscribe.getType());
         request.setTorrentId(selected.getTorrentId());
         request.setDownloadUrl(selected.getDownloadUrl());
         request.setScrapePathConfigId(subscribe.getScrapePathConfigId());
         request.setMovieName(subscribe.getName());
         request.setMovieYear(subscribe.getYear());
         request.setTitle(this.buildDownloadRecordTitle(subscribe, selected));
         request.setPosterUrl(subscribe.getPosterPath());
         request.setCoverUrl(StringUtils.hasText(selected.getCover()) ? selected.getCover() : subscribe.getBackdropPath());
         request.setDownloaderId(subscribe.getDownloaderId());
         request.setSubscribeId(subscribe.getId());
         request.setSkipNotify(true);
         return this.moviePtDownloadService.downloadAndAdd(request);
      }
   }

   private String buildDownloadRecordTitle(MoviePtSubscribeEntity subscribe, MoviePtSearchResult selected) {
      if (selected == null) {
         return "";
      } else {
         return !this.isTvSubscribe(subscribe)
            ? this.trimToNull(selected.getTitle())
            : Stream.of(selected.getTitle(), selected.getSubtitle()).map(this::trimToNull).filter(StringUtils::hasText).collect(Collectors.joining(" "));
      }
   }

   private boolean hasTrackedMovieDownload(MoviePtSubscribeEntity subscribe) {
      if (subscribe == null) {
         return false;
      } else {
         List<MovieDownloadRecordEntity> records = this.listRecordsByStatuses("movie", subscribe.getTmdbId(), subscribe.getId(), DOWNLOAD_TRACK_STATUSES);
         return !CollectionUtils.isEmpty(records);
      }
   }

   private boolean hasCompletedMovieDownload(MoviePtSubscribeEntity subscribe) {
      if (subscribe == null) {
         return false;
      } else {
         List<MovieDownloadRecordEntity> records = this.listRecordsByStatuses("movie", subscribe.getTmdbId(), subscribe.getId(), DOWNLOAD_COMPLETED_STATUSES);
         return !CollectionUtils.isEmpty(records);
      }
   }

   private boolean isTvResultAlreadyTracked(MoviePtSubscribeEntity subscribe, MoviePtSearchResult result) {
      if (subscribe != null && this.isTvSubscribe(subscribe) && result != null) {
         Set<Integer> resultEpisodes = this.extractResultEpisodes(result, subscribe);
         if (CollectionUtils.isEmpty(resultEpisodes)) {
            return false;
         } else {
            List<Integer> trackedEpisodes = this.loadDownloadedEpisodes(subscribe.getTmdbId(), subscribe.getId(), subscribe.getSeason());
            if (CollectionUtils.isEmpty(trackedEpisodes)) {
               return false;
            } else {
               Set<Integer> trackedSet = new LinkedHashSet<>(trackedEpisodes);
               return trackedSet.containsAll(resultEpisodes);
            }
         }
      } else {
         return false;
      }
   }

   private Set<Integer> extractResultEpisodes(MoviePtSearchResult result, MoviePtSubscribeEntity subscribe) {
      if (result != null && subscribe != null && this.isTvSubscribe(subscribe)) {
         MovieEpisodeParser.EpisodeMeta meta = this.parseEpisodeMeta(result);
         if (meta != null && meta.hasEpisode()) {
            Integer parsedSeason = meta.getBeginSeason();
            return subscribe.getSeason() != null && parsedSeason != null && !subscribe.getSeason().equals(parsedSeason)
               ? Set.of()
               : this.extractEpisodeSet(meta);
         } else {
            return Set.of();
         }
      } else {
         return Set.of();
      }
   }

   private void updateSubscribeStateAfterSearch(MoviePtSubscribeEntity subscribe, List<Integer> remainingMissingEpisodes) {
      if (subscribe != null && !"S".equalsIgnoreCase(subscribe.getState())) {
         if (this.shouldMarkSubscribeCompleted(subscribe, remainingMissingEpisodes)) {
            subscribe.setState("C");
            subscribe.setEnabled(0);
         } else {
            subscribe.setState("R");
         }
      }
   }

   private boolean shouldMarkSubscribeCompleted(MoviePtSubscribeEntity subscribe, List<Integer> remainingMissingEpisodes) {
      if (subscribe == null) {
         return false;
      } else if (this.isMovieSubscribe(subscribe)) {
         return this.hasCompletedMovieDownload(subscribe);
      } else {
         return !this.isTvSubscribe(subscribe) ? false : this.isTvSubscribeCompleted(subscribe, remainingMissingEpisodes);
      }
   }

   private boolean isTvSubscribeCompleted(MoviePtSubscribeEntity subscribe, List<Integer> remainingMissingEpisodes) {
      if (subscribe == null || subscribe.getTmdbId() == null || subscribe.getSeason() == null || subscribe.getSeason() <= 0) {
         return false;
      } else if (!CollectionUtils.isEmpty(remainingMissingEpisodes)) {
         return false;
      } else {
         MoviePtSubscribeServiceImpl.TmdbSeasonProgress seasonProgress = this.loadTmdbSeasonProgress(subscribe.getTmdbId(), subscribe.getSeason());
         if (seasonProgress == null) {
            return false;
         } else {
            Integer latestAiredEpisode = seasonProgress.latestAiredEpisode();
            Integer latestEpisode = seasonProgress.latestEpisode();
            if (latestAiredEpisode != null && latestAiredEpisode > 0) {
               if (latestEpisode != null && latestEpisode > latestAiredEpisode) {
                  return false;
               } else {
                  int startEpisode = this.normalizeStartEpisode(subscribe.getStartEpisode());
                  Long subscribeScopeId = subscribe.getId();
                  Long tmdbScopeId = subscribeScopeId == null ? subscribe.getTmdbId() : null;
                  List<Integer> completedEpisodes = this.loadCompletedEpisodes(tmdbScopeId, subscribeScopeId, subscribe.getSeason())
                     .stream()
                     .filter(Objects::nonNull)
                     .filter(item -> item >= startEpisode && item <= latestAiredEpisode)
                     .distinct()
                     .sorted()
                     .toList();
                  Set<Integer> completedSet = new LinkedHashSet<>(completedEpisodes);
                  return this.computeMissingEpisodes(startEpisode, latestAiredEpisode, completedSet).isEmpty();
               }
            } else {
               return false;
            }
         }
      }
   }

   private MoviePtSearchResult selectBestResult(List<MoviePtSearchResult> results, MoviePtSubscribeEntity subscribe, List<Integer> missingEpisodes) {
      if (results != null && !results.isEmpty()) {
         Set<Integer> missingEpisodeSet = (Set<Integer>)(missingEpisodes == null ? Set.of() : new LinkedHashSet<>(missingEpisodes));
         return results.stream().max(this.buildResultComparator(subscribe, missingEpisodeSet)).orElse(null);
      } else {
         return null;
      }
   }

   private MoviePtSearchResult pickBetterResult(
      MoviePtSearchResult current, MoviePtSearchResult candidate, MoviePtSubscribeEntity subscribe, Set<Integer> missingEpisodeSet
   ) {
      if (candidate == null) {
         return current;
      } else if (current == null) {
         return candidate;
      } else {
         Comparator<MoviePtSearchResult> comparator = this.buildResultComparator(subscribe, missingEpisodeSet);
         return comparator.compare(candidate, current) > 0 ? candidate : current;
      }
   }

   private Comparator<MoviePtSearchResult> buildResultComparator(MoviePtSubscribeEntity subscribe, Set<Integer> missingEpisodeSet) {
      Set<Integer> safeMissingSet = missingEpisodeSet == null ? Set.of() : missingEpisodeSet;
      return Comparator.<MoviePtSearchResult>comparingInt(r -> this.episodeMatchScore(r, subscribe, safeMissingSet))
         .thenComparingInt(r -> this.promoScore(r.getPromo()))
         .thenComparingInt(r -> this.qualityScore(r.getQuality()))
         .thenComparingInt(r -> this.audioEffectScore(r.getAudioEffects()))
         .thenComparingInt(r -> this.parseSeeders(r.getSeeders()))
         .thenComparingInt(r -> this.resolutionScore(r.getResolution()));
   }

   private List<MoviePtSearchResult> buildSingleModeRetryCandidates(List<MoviePtSearchResult> results) {
      if (CollectionUtils.isEmpty(results)) {
         return List.of();
      } else {
         LinkedHashMap<String, MoviePtSearchResult> dedup = new LinkedHashMap<>();

         for (MoviePtSearchResult result : results) {
            String key = this.buildResultUniqueKey(result);
            if (StringUtils.hasText(key)) {
               dedup.putIfAbsent(key, result);
            }
         }

         List<MoviePtSearchResult> ordered = new ArrayList<>(dedup.values());
         ordered.sort((left, right) -> {
            int bySeeders = Integer.compare(this.parseSeeders(right.getSeeders()), this.parseSeeders(left.getSeeders()));
            if (bySeeders != 0) {
               return bySeeders;
            } else {
               long rightPublishTime = this.resolvePublishTimeScore(right);
               long leftPublishTime = this.resolvePublishTimeScore(left);
               boolean bothHasPublishTime = rightPublishTime != Long.MIN_VALUE && leftPublishTime != Long.MIN_VALUE;
               if (bothHasPublishTime) {
                  int byPublishTime = Long.compare(rightPublishTime, leftPublishTime);
                  if (byPublishTime != 0) {
                     return byPublishTime;
                  }
               }

               return Integer.compare(this.resolutionScore(right.getResolution()), this.resolutionScore(left.getResolution()));
            }
         });
         return ordered;
      }
   }

   private long resolvePublishTimeScore(MoviePtSearchResult result) {
      if (result == null) {
         return Long.MIN_VALUE;
      } else {
         long best = Long.MIN_VALUE;
         best = Math.max(best, this.extractPublishTimeScore(result.getSubtitle()));
         best = Math.max(best, this.extractPublishTimeScore(result.getTitle()));
         if (!CollectionUtils.isEmpty(result.getTags())) {
            for (String tag : result.getTags()) {
               best = Math.max(best, this.extractPublishTimeScore(tag));
            }
         }

         return best;
      }
   }

   private long extractPublishTimeScore(String text) {
      String value = this.trimToNull(text);
      if (!StringUtils.hasText(value)) {
         return Long.MIN_VALUE;
      } else {
         long best = Long.MIN_VALUE;
         Matcher dateTimeMatcher = PUBLISH_DATETIME_PATTERN.matcher(value);

         while (dateTimeMatcher.find()) {
            long parsed = this.toDateTimeScore(
               this.parseInteger(dateTimeMatcher.group(1)),
               this.parseInteger(dateTimeMatcher.group(2)),
               this.parseInteger(dateTimeMatcher.group(3)),
               this.parseInteger(dateTimeMatcher.group(4)),
               this.parseInteger(dateTimeMatcher.group(5)),
               this.parseInteger(dateTimeMatcher.group(6))
            );
            if (parsed > best) {
               best = parsed;
            }
         }

         Matcher compactMatcher = PUBLISH_COMPACT_DATE_PATTERN.matcher(value);

         while (compactMatcher.find()) {
            long parsed = this.toDateTimeScore(
               this.parseInteger(compactMatcher.group(1)), this.parseInteger(compactMatcher.group(2)), this.parseInteger(compactMatcher.group(3)), 0, 0, 0
            );
            if (parsed > best) {
               best = parsed;
            }
         }

         return best;
      }
   }

   private long toDateTimeScore(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
      int safeHour = hour == null ? 0 : hour;
      int safeMinute = minute == null ? 0 : minute;
      int safeSecond = second == null ? 0 : second;
      if (year == null || month == null || day == null) {
         return Long.MIN_VALUE;
      } else if (safeHour >= 0 && safeHour <= 23 && safeMinute >= 0 && safeMinute <= 59 && safeSecond >= 0 && safeSecond <= 59) {
         try {
            LocalDate.of(year, month, day);
         } catch (Exception var11) {
            return Long.MIN_VALUE;
         }

         return (long)year.intValue() * 10000000000L
            + (long)month.intValue() * 100000000L
            + (long)day.intValue() * 1000000L
            + (long)safeHour * 10000L
            + (long)safeMinute * 100L
            + (long)safeSecond;
      } else {
         return Long.MIN_VALUE;
      }
   }

   private int promoScore(String promo) {
      if (!StringUtils.hasText(promo)) {
         return 0;
      } else {
         String lower = promo.toLowerCase(Locale.ROOT);
         if (!lower.contains("2x") || !lower.contains("free") && !lower.contains("免费")) {
            return !lower.contains("free") && !lower.contains("免费") && !lower.contains("zero") ? 1 : 2;
         } else {
            return 3;
         }
      }
   }

   private int resolutionScore(String resolution) {
      if (!StringUtils.hasText(resolution)) {
         return 0;
      } else {
         String value = resolution.toLowerCase(Locale.ROOT);
         if (value.contains("2160") || value.contains("4k")) {
            return 3;
         } else if (value.contains("1080")) {
            return 2;
         } else {
            return value.contains("720") ? 1 : 0;
         }
      }
   }

   private int qualityScore(String quality) {
      if (!StringUtils.hasText(quality)) {
         return 0;
      } else {
         String value = quality.toLowerCase(Locale.ROOT);
         if ("remux".equals(value)) {
            return 5;
         } else if ("bluray".equals(value)) {
            return 4;
         } else if ("webdl".equals(value)) {
            return 3;
         } else if ("webrip".equals(value)) {
            return 2;
         } else {
            return !"hdtv".equals(value) && !"hdrip".equals(value) ? 0 : 1;
         }
      }
   }

   private int audioEffectScore(List<String> audioEffects) {
      if (CollectionUtils.isEmpty(audioEffects)) {
         return 0;
      } else {
         int best = 0;

         for (String effect : audioEffects) {
            best = Math.max(best, this.singleAudioEffectScore(effect));
         }

         return best;
      }
   }

   private int singleAudioEffectScore(String effect) {
      if (!StringUtils.hasText(effect)) {
         return 0;
      } else {
         String var2 = effect.toLowerCase(Locale.ROOT);

         return switch (var2) {
            case "atmos" -> 6;
            case "truehd" -> 5;
            case "dts_hd" -> 4;
            case "dolby_vision" -> 3;
            case "hdr10plus" -> 2;
            case "hdr" -> 1;
            default -> 0;
         };
      }
   }

   private int parseSeeders(String seeders) {
      if (!StringUtils.hasText(seeders)) {
         return 0;
      } else {
         String digits = seeders.replaceAll("[^0-9]", "");
         if (!StringUtils.hasText(digits)) {
            return 0;
         } else {
            try {
               return Integer.parseInt(digits);
            } catch (NumberFormatException var4) {
               return 0;
            }
         }
      }
   }

   private String buildSearchMessage(
      int matchedCount,
      boolean autoDownload,
      MovieActionResponse downloadResult,
      Integer tmdbLatestEpisode,
      List<Integer> downloadedEpisodes,
      List<Integer> missingEpisodes
   ) {
      List<String> parts = new ArrayList<>();
      if (tmdbLatestEpisode != null) {
         int downloaded = downloadedEpisodes == null ? 0 : downloadedEpisodes.size();
         int missing = missingEpisodes == null ? 0 : missingEpisodes.size();
         parts.add("TMDB 最新已播第 " + tmdbLatestEpisode + " 集，已下载 " + downloaded + " 集，缺失 " + missing + " 集");
      }

      if (matchedCount <= 0) {
         parts.add("未搜索到资源");
         return String.join("；", parts);
      } else if (!autoDownload) {
         parts.add("搜索完成，未开启自动下载");
         return String.join("；", parts);
      } else if (downloadResult == null) {
         parts.add("搜索完成，未触发下载");
         return String.join("；", parts);
      } else {
         if (StringUtils.hasText(downloadResult.getMessage())) {
            parts.add(downloadResult.getMessage());
         } else {
            parts.add(downloadResult.isSuccess() ? "搜索完成，已触发自动下载" : "搜索完成，自动下载失败");
         }

         return String.join("；", parts);
      }
   }

   private boolean resolveAutoDownload(Boolean autoDownloadOverride, MoviePtSubscribeEntity subscribe) {
      return autoDownloadOverride != null ? autoDownloadOverride : subscribe.getAutoDownload() != null && subscribe.getAutoDownload() == 1;
   }

   private String resolveKeyword(MoviePtSubscribeEntity subscribe) {
      String keyword = this.trimToNull(subscribe.getKeyword());
      if (StringUtils.hasText(keyword)) {
         return keyword;
      } else {
         keyword = this.buildDefaultKeyword(subscribe.getName(), subscribe.getYear(), subscribe.getSeason(), this.normalizeType(subscribe.getType()));
         subscribe.setKeyword(keyword);
         return keyword;
      }
   }

   private String buildDefaultKeyword(String name, String year, Integer season, String type) {
      String baseName = this.trimToNull(name);
      if (!StringUtils.hasText(baseName)) {
         return "";
      } else if ("tv".equals(type) && season != null && season > 0) {
         return baseName + " S" + String.format("%02d", season);
      } else {
         return StringUtils.hasText(year) ? baseName + " " + year.trim() : baseName;
      }
   }

   private MoviePtSubscribeServiceImpl.TmdbImagePaths loadTmdbImagePaths(Long tmdbId, String type) {
      if (tmdbId == null) {
         return new MoviePtSubscribeServiceImpl.TmdbImagePaths(null, null);
      } else if ("tv".equals(type)) {
         TvSeriesDb series = this.fetchTvSeriesFromTmdb(tmdbId, "zh-CN");
         if (series == null || !StringUtils.hasText(series.getPosterPath()) && !StringUtils.hasText(series.getBackdropPath())) {
            series = this.fetchTvSeriesFromTmdb(tmdbId, "en-US");
         }

         return series == null
            ? new MoviePtSubscribeServiceImpl.TmdbImagePaths(null, null)
            : new MoviePtSubscribeServiceImpl.TmdbImagePaths(
               this.normalizeTmdbImageUrl(series.getPosterPath()), this.normalizeTmdbImageUrl(series.getBackdropPath())
            );
      } else {
         MovieDb movie = this.fetchMovieFromTmdb(tmdbId, "zh-CN");
         if (movie == null || !StringUtils.hasText(movie.getPosterPath()) && !StringUtils.hasText(movie.getBackdropPath())) {
            movie = this.fetchMovieFromTmdb(tmdbId, "en-US");
         }

         return movie == null
            ? new MoviePtSubscribeServiceImpl.TmdbImagePaths(null, null)
            : new MoviePtSubscribeServiceImpl.TmdbImagePaths(
               this.normalizeTmdbImageUrl(movie.getPosterPath()), this.normalizeTmdbImageUrl(movie.getBackdropPath())
            );
      }
   }

   private TvSeriesDb fetchTvSeriesFromTmdb(Long tmdbId, String language) {
      try {
         return this.tmdbService.getTvSeries(tmdbId.intValue(), language);
      } catch (Exception var4) {
         log.debug("获取 TMDB 剧集详情失败 tmdbId={} language={} : {}", tmdbId, language, var4.getMessage());
         return null;
      }
   }

   private MovieDb fetchMovieFromTmdb(Long tmdbId, String language) {
      try {
         return this.tmdbService.getMovieDetails(tmdbId.intValue(), language);
      } catch (Exception var4) {
         log.debug("获取 TMDB 电影详情失败 tmdbId={} language={} : {}", tmdbId, language, var4.getMessage());
         return null;
      }
   }

   private String normalizeTmdbImageUrl(String path) {
      String value = this.trimToNull(path);
      if (!StringUtils.hasText(value)) {
         return null;
      } else if (value.startsWith("http://") || value.startsWith("https://")) {
         return value;
      } else if (!value.startsWith("data:") && !value.startsWith("blob:") && !value.startsWith("file:")) {
         String base = this.trimToNull(this.tmdbImageUrl);
         if (!StringUtils.hasText(base)) {
            base = "https://image.tmdb.org/t/p/original";
         }

         if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
         }

         String relative = value.startsWith("/") ? value : "/" + value;
         return base + relative;
      } else {
         return value;
      }
   }

   private MoviePtSubscribeServiceImpl.SubscribeSearchPlan buildSearchPlan(MoviePtSubscribeEntity subscribe) {
      String keyword = this.resolveKeyword(subscribe);
      List<String> defaultKeywords = this.buildSubscribeSearchKeywords(subscribe, keyword);
      if (subscribe == null || !this.isTvSubscribe(subscribe)) {
         return new MoviePtSubscribeServiceImpl.SubscribeSearchPlan(keyword, defaultKeywords, null, List.of(), List.of(), false, null);
      } else if (subscribe.getTmdbId() != null && subscribe.getSeason() != null && subscribe.getSeason() > 0) {
         Integer latestEpisode = this.loadTmdbLatestEpisode(subscribe.getTmdbId(), subscribe.getSeason());
         if (latestEpisode != null && latestEpisode > 0) {
            int startEpisode = this.normalizeStartEpisode(subscribe.getStartEpisode());
            Long subscribeScopeId = subscribe.getId();
            Long tmdbScopeId = subscribeScopeId == null ? subscribe.getTmdbId() : null;
            List<Integer> downloadedEpisodes = this.loadDownloadedEpisodes(tmdbScopeId, subscribeScopeId, subscribe.getSeason())
               .stream()
               .filter(Objects::nonNull)
               .filter(item -> item >= startEpisode)
               .distinct()
               .sorted()
               .toList();
            Set<Integer> downloadedSet = new LinkedHashSet<>(downloadedEpisodes);
            List<Integer> missingEpisodes = this.computeMissingEpisodes(startEpisode, latestEpisode, downloadedSet);
            List<String> searchedKeywords = this.buildTvKeywords(subscribe, defaultKeywords, missingEpisodes);
            if (missingEpisodes.isEmpty()) {
               String message = "TMDB 当前季已无缺失剧集，跳过本轮搜索";
               return new MoviePtSubscribeServiceImpl.SubscribeSearchPlan(
                  keyword, searchedKeywords, latestEpisode, downloadedEpisodes, missingEpisodes, true, message
               );
            } else {
               return new MoviePtSubscribeServiceImpl.SubscribeSearchPlan(
                  keyword, searchedKeywords, latestEpisode, downloadedEpisodes, missingEpisodes, false, null
               );
            }
         } else {
            return new MoviePtSubscribeServiceImpl.SubscribeSearchPlan(keyword, defaultKeywords, null, List.of(), List.of(), false, null);
         }
      } else {
         return new MoviePtSubscribeServiceImpl.SubscribeSearchPlan(keyword, defaultKeywords, null, List.of(), List.of(), false, null);
      }
   }

   private List<MoviePtSearchResult> searchByKeywords(
      List<String> keywords,
      List<Long> siteIds,
      int limit,
      Long subscribeId,
      Boolean autoDownload,
      Consumer<MoviePtSubscribeSearchProgressEvent> progressConsumer,
      MoviePtSubscribeServiceImpl.SearchFilter searchFilter
   ) {
      if (CollectionUtils.isEmpty(keywords)) {
         return List.of();
      } else {
         List<String> normalizedKeywords = keywords.stream().map(this::trimToNull).filter(StringUtils::hasText).distinct().toList();
         if (CollectionUtils.isEmpty(normalizedKeywords)) {
            return List.of();
         } else {
            List<Long> targetSiteIds = this.normalizeSiteIds(siteIds);
            Map<String, MoviePtSearchResult> merged = new LinkedHashMap<>();

            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
               for (String keyword : normalizedKeywords) {
                  List<MoviePtSearchResult> partialResults = new ArrayList<>();
                  if (CollectionUtils.isEmpty(targetSiteIds)) {
                     try {
                        partialResults = this.moviePtSearchService
                           .searchWithProgress(
                              keyword,
                              null,
                              limit,
                              searchFilter == null ? null : searchFilter.title(),
                              searchFilter == null ? null : searchFilter.originalTitle(),
                              searchFilter == null ? null : searchFilter.year(),
                              searchFilter == null ? null : searchFilter.type(),
                              searchProgressEvent -> this.emitSubscribeSearchSiteProgress(
                                    progressConsumer, subscribeId, limit, autoDownload, searchProgressEvent
                                 )
                           );
                     } catch (Exception var20) {
                        log.warn("订阅关键词搜索失败 keyword={} siteId={} : {}", keyword, null, this.unwrapExceptionMessage(var20));
                     }
                  } else {
                     for (CompletableFuture<List<MoviePtSearchResult>> future : targetSiteIds.stream()
                        .map(
                           siteId -> CompletableFuture.<List<MoviePtSearchResult>>supplyAsync(
                                    () -> this.moviePtSearchService
                                          .searchWithProgress(
                                             keyword,
                                             siteId,
                                             limit,
                                             searchFilter == null ? null : searchFilter.title(),
                                             searchFilter == null ? null : searchFilter.originalTitle(),
                                             searchFilter == null ? null : searchFilter.year(),
                                             searchFilter == null ? null : searchFilter.type(),
                                             searchProgressEvent -> this.emitSubscribeSearchSiteProgress(
                                                   progressConsumer, subscribeId, limit, autoDownload, searchProgressEvent
                                                )
                                          ),
                                    executor
                                 )
                                 .exceptionally(ex -> {
                                    log.warn("订阅关键词搜索失败 keyword={} siteId={} : {}", keyword, siteId, this.unwrapExceptionMessage(ex));
                                    return List.of();
                                 })
                        )
                        .toList()) {
                        List<MoviePtSearchResult> sitePartial = future.join();
                        if (!CollectionUtils.isEmpty(sitePartial)) {
                           partialResults.addAll(sitePartial);
                        }
                     }
                  }

                  if (!CollectionUtils.isEmpty(partialResults)) {
                     for (MoviePtSearchResult result : partialResults) {
                        merged.putIfAbsent(this.buildResultUniqueKey(result), result);
                     }

                     if (!merged.isEmpty()) {
                        break;
                     }
                  }
               }
            }

            return new ArrayList<>(merged.values());
         }
      }
   }

   private void emitSubscribeSearchSiteProgress(
      Consumer<MoviePtSubscribeSearchProgressEvent> progressConsumer, Long subscribeId, Integer limit, Boolean autoDownload, MoviePtSearchProgressEvent event
   ) {
      if (progressConsumer != null && event != null && StringUtils.hasText(event.getType())) {
         String type = event.getType().toUpperCase(Locale.ROOT);
         if (type.startsWith("SITE_")) {
            progressConsumer.accept(
               MoviePtSubscribeSearchProgressEvent.builder()
                  .type(type)
                  .subscribeId(subscribeId)
                  .limit(limit)
                  .autoDownload(autoDownload)
                  .keyword(event.getKeyword())
                  .totalSites(event.getTotalSites())
                  .completedSites(event.getCompletedSites())
                  .siteId(event.getSiteId())
                  .siteName(event.getSiteName())
                  .siteResultCount(event.getSiteResultCount())
                  .totalResults(event.getTotalResults())
                  .elapsedMs(event.getElapsedMs())
                  .message(event.getMessage())
                  .build()
            );
         }
      }
   }

   private List<MoviePtSearchResult> filterSearchResults(
      MoviePtSubscribeEntity subscribe, List<MoviePtSearchResult> results, MoviePtSubscribeServiceImpl.SubscribeSearchPlan plan
   ) {
      if (CollectionUtils.isEmpty(results)) {
         return List.of();
      } else {
         List<MoviePtSearchResult> episodeFiltered = results;
         if (subscribe != null && this.isTvSubscribe(subscribe) && !CollectionUtils.isEmpty(plan.missingEpisodes())) {
            Set<Integer> missingSet = new LinkedHashSet<>(plan.missingEpisodes());
            List<MoviePtSearchResult> temp = results.stream().filter(item -> this.matchMissingEpisodes(item, subscribe.getSeason(), missingSet)).toList();
            if (!temp.isEmpty()) {
               episodeFiltered = temp;
            }
         }

         return this.filterByQualityConfig(subscribe, episodeFiltered);
      }
   }

   private List<MoviePtSearchResult> filterByQualityConfig(MoviePtSubscribeEntity subscribe, List<MoviePtSearchResult> results) {
      if (CollectionUtils.isEmpty(results)) {
         return results;
      } else {
         MovieSubscribeQualityConfig config = this.loadQualityConfig();
         if (config == null) {
            return results;
         } else {
            boolean isMovie = this.isMovieSubscribe(subscribe);
            String minRes = isMovie ? config.getMovieMinResolution() : config.getTvMinResolution();
            String preferredQuality = isMovie ? config.getMoviePreferredQuality() : config.getTvPreferredQuality();
            String requiredAudio = isMovie ? config.getMovieRequiredAudioEffects() : config.getTvRequiredAudioEffects();
            String requiredTags = isMovie ? config.getMovieRequiredTags() : config.getTvRequiredTags();
            boolean strictFilter = Boolean.TRUE.equals(config.getStrictFilter());
            if (!StringUtils.hasText(minRes)
               && !StringUtils.hasText(preferredQuality)
               && !StringUtils.hasText(requiredAudio)
               && !StringUtils.hasText(requiredTags)) {
               return results;
            } else {
               int minResScore = this.parseMinResolutionScore(minRes);
               Set<String> qualitySet = this.parseCommaSeparatedSet(preferredQuality);
               Set<String> audioSet = this.parseCommaSeparatedSet(requiredAudio);
               Set<String> tagSet = this.parseCommaSeparatedSet(requiredTags);
               List<MoviePtSearchResult> filtered = results.stream()
                  .filter(r -> this.matchMinResolution(r, minResScore))
                  .filter(r -> this.matchPreferredQuality(r, qualitySet))
                  .filter(r -> this.matchRequiredAudioEffects(r, audioSet))
                  .filter(r -> this.matchRequiredTags(r, tagSet))
                  .toList();
               if (filtered.isEmpty()) {
                  if (strictFilter) {
                     log.info("全局过滤后无符合资源，当前为强制过滤模式，返回 0 条结果");
                     return filtered;
                  } else {
                     log.info("全局过滤后无符合资源，当前为优先匹配模式，降级使用全部 {} 条结果", results.size());
                     return results;
                  }
               } else {
                  log.debug("全局过滤: {}/{} 条资源符合条件，strictFilter={}", filtered.size(), results.size(), strictFilter);
                  return filtered;
               }
            }
         }
      }
   }

   private MovieSubscribeQualityConfig loadQualityConfig() {
      try {
         String json = this.systemConfigService.getConfigValue("subscribe_quality_config");
         return !StringUtils.hasText(json) ? null : JSON.parseObject(json, MovieSubscribeQualityConfig.class);
      } catch (Exception var2) {
         log.warn("加载全局画质配置失败: {}", var2.getMessage());
         return null;
      }
   }

   @Override
   public MovieSubscribeQualityConfig saveQualityConfig(MovieSubscribeQualityConfig config) {
      String json = JSON.toJSONString(config);
      SystemConfig existing = this.findSystemConfigByKey("subscribe_quality_config");
      if (existing != null) {
         existing.setConfigValue(json);
         this.systemConfigService.updateById(existing);
      } else {
         SystemConfig newConfig = new SystemConfig();
         newConfig.setName("订阅画质音效配置");
         newConfig.setConfigKey("subscribe_quality_config");
         newConfig.setConfigValue(json);
         newConfig.setIsEnabled(1);
         newConfig.setIsUpdate(1);
         newConfig.setDescription("订阅自动下载时的全局画质、音效、标签过滤配置（含强制过滤开关）");
         this.systemConfigService.save(newConfig);
      }

      this.configCacheLoaderUtils.refreshCache();
      return config;
   }

   @Override
   public MovieSubscribeQualityConfig getQualityConfig() {
      MovieSubscribeQualityConfig config = this.loadQualityConfig();
      return config != null ? config : new MovieSubscribeQualityConfig();
   }

   private SystemConfig findSystemConfigByKey(String configKey) {
      try {
         return this.systemConfigService.lambdaQuery().eq(SystemConfig::getConfigKey, configKey).last("LIMIT 1").one();
      } catch (Exception var3) {
         log.warn("查找系统配置失败: {}", var3.getMessage());
         return null;
      }
   }

   private int parseMinResolutionScore(String minRes) {
      if (!StringUtils.hasText(minRes)) {
         return 0;
      } else {
         String lower = minRes.toLowerCase(Locale.ROOT);
         if (lower.contains("2160") || lower.contains("4k")) {
            return 3;
         } else if (lower.contains("1080")) {
            return 2;
         } else {
            return lower.contains("720") ? 1 : 0;
         }
      }
   }

   private Set<String> parseCommaSeparatedSet(String value) {
      if (!StringUtils.hasText(value)) {
         return Set.of();
      } else {
         Set<String> result = new LinkedHashSet<>();

         for (String item : value.split(",")) {
            String trimmed = item.trim().toLowerCase(Locale.ROOT);
            if (StringUtils.hasText(trimmed)) {
               result.add(trimmed);
            }
         }

         return result;
      }
   }

   private boolean matchMinResolution(MoviePtSearchResult result, int minResScore) {
      return minResScore <= 0 ? true : this.resolutionScore(result.getResolution()) >= minResScore;
   }

   private boolean matchPreferredQuality(MoviePtSearchResult result, Set<String> qualitySet) {
      if (qualitySet.isEmpty()) {
         return true;
      } else {
         return !StringUtils.hasText(result.getQuality()) ? false : qualitySet.contains(result.getQuality().toLowerCase(Locale.ROOT));
      }
   }

   private boolean matchRequiredAudioEffects(MoviePtSearchResult result, Set<String> audioSet) {
      if (audioSet.isEmpty()) {
         return true;
      } else if (CollectionUtils.isEmpty(result.getAudioEffects())) {
         return false;
      } else {
         for (String effect : result.getAudioEffects()) {
            if (audioSet.contains(effect.toLowerCase(Locale.ROOT))) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean matchRequiredTags(MoviePtSearchResult result, Set<String> tagSet) {
      if (tagSet.isEmpty()) {
         return true;
      } else if (result == null) {
         return false;
      } else {
         List<String> resultTags = result.getTags();
         if (resultTags == null) {
            resultTags = List.of();
         }
         for (String tag : resultTags) {
            String normalized = this.trimToNull(tag);
            if (StringUtils.hasText(normalized)) {
               String lowerTag = normalized.toLowerCase(Locale.ROOT);

               for (String expected : tagSet) {
                  if (lowerTag.contains(expected)) {
                     return true;
                  }
               }
            }
         }

         String title = this.trimToNull(result.getTitle());
         String subtitle = this.trimToNull(result.getSubtitle());
         String combined = Stream.of(title, subtitle).filter(StringUtils::hasText).map(item -> item.toLowerCase(Locale.ROOT)).collect(Collectors.joining(" "));
         if (!StringUtils.hasText(combined)) {
            return false;
         } else {
            for (String expectedx : tagSet) {
               if (combined.contains(expectedx)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private boolean matchMissingEpisodes(MoviePtSearchResult result, Integer season, Set<Integer> missingEpisodes) {
      MovieEpisodeParser.EpisodeMeta meta = this.parseEpisodeMeta(result);
      if (meta == null) {
         return false;
      } else {
         Integer parsedSeason = meta.getBeginSeason();
         if (season != null && parsedSeason != null && !season.equals(parsedSeason)) {
            return false;
         } else if (meta.hasEpisode()) {
            Set<Integer> resultEpisodes = this.extractEpisodeSet(meta);
            return resultEpisodes.isEmpty() ? false : missingEpisodes.containsAll(resultEpisodes);
         } else {
            return parsedSeason != null && (season == null || season.equals(parsedSeason));
         }
      }
   }

   private int episodeMatchScore(MoviePtSearchResult result, MoviePtSubscribeEntity subscribe, Set<Integer> missingEpisodes) {
      if (subscribe != null && this.isTvSubscribe(subscribe) && !CollectionUtils.isEmpty(missingEpisodes)) {
         MovieEpisodeParser.EpisodeMeta meta = this.parseEpisodeMeta(result);
         if (meta == null) {
            return 0;
         } else {
            Integer parsedSeason = meta.getBeginSeason();
            if (subscribe.getSeason() != null && parsedSeason != null && !subscribe.getSeason().equals(parsedSeason)) {
               return -1;
            } else if (!meta.hasEpisode()) {
               return parsedSeason != null ? 1 : 0;
            } else {
               Set<Integer> resultEpisodes = this.extractEpisodeSet(meta);
               if (resultEpisodes.isEmpty()) {
                  return 0;
               } else {
                  return missingEpisodes.containsAll(resultEpisodes) ? 2 : 0;
               }
            }
         }
      } else {
         return 0;
      }
   }

   private MovieEpisodeParser.EpisodeMeta parseEpisodeMeta(MoviePtSearchResult result) {
      if (result == null) {
         return null;
      } else {
         String text = Stream.of(result.getTitle(), result.getSubtitle()).map(this::trimToNull).filter(StringUtils::hasText).collect(Collectors.joining(" "));
         return !StringUtils.hasText(text) ? null : MovieEpisodeParser.parse(text);
      }
   }

   private Set<Integer> extractEpisodeSet(MovieEpisodeParser.EpisodeMeta meta) {
      if (meta != null && meta.hasEpisode() && meta.getBeginEpisode() != null) {
         int begin = meta.getBeginEpisode();
         int end = meta.getEndEpisode() == null ? begin : meta.getEndEpisode();
         if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
         }

         Set<Integer> episodes = new LinkedHashSet<>();

         for (int i = begin; i <= end; i++) {
            episodes.add(i);
         }

         return episodes;
      } else {
         return Set.of();
      }
   }

   private List<Integer> computeMissingEpisodes(int startEpisode, int latestEpisode, Set<Integer> downloadedEpisodes) {
      if (latestEpisode < startEpisode) {
         return List.of();
      } else {
         List<Integer> missing = new ArrayList<>();

         for (int episode = startEpisode; episode <= latestEpisode; episode++) {
            if (downloadedEpisodes == null || !downloadedEpisodes.contains(episode)) {
               missing.add(episode);
            }
         }

         return missing;
      }
   }

   private List<String> buildTvKeywords(MoviePtSubscribeEntity subscribe, List<String> fallbackKeywords, List<Integer> missingEpisodes) {
      LinkedHashSet<String> keywords = new LinkedHashSet<>();
      Integer season = subscribe == null ? null : subscribe.getSeason();
      List<String> baseCandidates = new ArrayList<>();
      this.addBaseKeywordCandidate(baseCandidates, subscribe == null ? null : subscribe.getName());
      this.addBaseKeywordCandidate(baseCandidates, subscribe == null ? null : subscribe.getOriginalTitle());
      if (!CollectionUtils.isEmpty(fallbackKeywords)) {
         for (String fallbackKeyword : fallbackKeywords) {
            this.addBaseKeywordCandidate(baseCandidates, this.stripSeasonSuffix(fallbackKeyword));
         }
      }

      String primaryBase = CollectionUtils.isEmpty(baseCandidates) ? "" : baseCandidates.get(0);
      if (StringUtils.hasText(primaryBase) && season != null && season > 0 && !CollectionUtils.isEmpty(missingEpisodes)) {
         for (int i = 0; i < Math.min(5, missingEpisodes.size()); i++) {
            Integer episode = missingEpisodes.get(i);
            keywords.add(primaryBase + " S" + String.format("%02d", season) + "E" + String.format("%02d", episode));
         }
      }

      for (String base : baseCandidates) {
         if (StringUtils.hasText(base) && season != null && season > 0) {
            keywords.add(base + " S" + String.format("%02d", season));
         }

         if (StringUtils.hasText(base)) {
            keywords.add(base);
         }
      }

      if (!CollectionUtils.isEmpty(fallbackKeywords)) {
         for (String fallbackKeyword : fallbackKeywords) {
            String value = this.trimToNull(fallbackKeyword);
            if (StringUtils.hasText(value)) {
               keywords.add(value);
            }
         }
      }

      return new ArrayList<>(keywords);
   }

   private List<String> buildSubscribeSearchKeywords(MoviePtSubscribeEntity subscribe, String fallbackKeyword) {
      LinkedHashSet<String> keywords = new LinkedHashSet<>();
      this.addSearchKeywordCandidate(keywords, subscribe == null ? null : subscribe.getName());
      this.addSearchKeywordCandidate(keywords, subscribe == null ? null : subscribe.getOriginalTitle());
      this.addSearchKeywordCandidate(keywords, this.stripSeasonSuffix(fallbackKeyword));
      this.addSearchKeywordCandidate(keywords, fallbackKeyword);
      return new ArrayList<>(keywords);
   }

   private void addSearchKeywordCandidate(Set<String> output, String keyword) {
      String value = this.trimToNull(keyword);
      if (StringUtils.hasText(value)) {
         output.add(value);
      }
   }

   private void addBaseKeywordCandidate(List<String> output, String keyword) {
      String value = this.trimToNull(keyword);
      if (StringUtils.hasText(value)) {
         if (!output.contains(value)) {
            output.add(value);
         }
      }
   }

   private String stripSeasonSuffix(String keyword) {
      String value = this.trimToNull(keyword);
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String stripped = value.replaceAll("(?i)\\s*S\\d{1,3}(?:E\\d{1,4})?\\s*$", "").trim();
         return StringUtils.hasText(stripped) ? stripped : value;
      }
   }

   private Integer loadTmdbLatestEpisode(Long tmdbId, Integer season) {
      MoviePtSubscribeServiceImpl.TmdbSeasonProgress progress = this.loadTmdbSeasonProgress(tmdbId, season);
      if (progress == null) {
         return null;
      } else {
         Integer latestAired = progress.latestAiredEpisode();
         Integer latestAny = progress.latestEpisode();
         return latestAired != null && latestAired > 0 ? latestAired : (latestAny != null && latestAny > 0 ? latestAny : null);
      }
   }

   private MoviePtSubscribeServiceImpl.TmdbSeasonProgress loadTmdbSeasonProgress(Long tmdbId, Integer season) {
      if (tmdbId != null && season != null && season > 0) {
         TvSeasonDb seasonDb = this.fetchSeasonFromTmdb(tmdbId, season, "zh-CN");
         if (seasonDb == null || CollectionUtils.isEmpty(seasonDb.getEpisodes())) {
            seasonDb = this.fetchSeasonFromTmdb(tmdbId, season, "en-US");
         }

         if (seasonDb != null && !CollectionUtils.isEmpty(seasonDb.getEpisodes())) {
            int latestAired = 0;
            int latestAny = 0;
            LocalDate today = LocalDate.now();

            for (TvSeasonEpisode episode : seasonDb.getEpisodes()) {
               if (episode != null && episode.getEpisodeNumber() != null && episode.getEpisodeNumber() > 0) {
                  int episodeNo = episode.getEpisodeNumber();
                  latestAny = Math.max(latestAny, episodeNo);
                  if (this.isEpisodeAired(episode.getAirDate(), today)) {
                     latestAired = Math.max(latestAired, episodeNo);
                  }
               }
            }

            Integer latestAiredEpisode = latestAired > 0 ? latestAired : null;
            Integer latestEpisode = latestAny > 0 ? latestAny : null;
            return new MoviePtSubscribeServiceImpl.TmdbSeasonProgress(latestAiredEpisode, latestEpisode);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private TvSeasonDb fetchSeasonFromTmdb(Long tmdbId, Integer season, String language) {
      try {
         return this.tmdbService.getTvSeasons(tmdbId.intValue(), season, language);
      } catch (Exception var5) {
         log.debug("获取 TMDB 季详情失败 tmdbId={} season={} language={} : {}", tmdbId, season, language, var5.getMessage());
         return null;
      }
   }

   private boolean isEpisodeAired(String airDate, LocalDate today) {
      if (!StringUtils.hasText(airDate)) {
         return true;
      } else {
         try {
            return !LocalDate.parse(airDate).isAfter(today);
         } catch (DateTimeParseException var4) {
            return true;
         }
      }
   }

   private List<Integer> loadDownloadedEpisodes(Long tmdbId, Integer season) {
      return this.loadDownloadedEpisodes(tmdbId, null, season);
   }

   private List<Integer> loadDownloadedEpisodes(Long tmdbId, Long subscribeId, Integer season) {
      if ((tmdbId != null || subscribeId != null) && season != null && season > 0) {
         List<MovieDownloadRecordEntity> records = this.listRecordsByStatuses("tv", tmdbId, subscribeId, DOWNLOAD_TRACK_STATUSES);
         return this.extractEpisodesFromRecords(records, season);
      } else {
         return List.of();
      }
   }

   private List<Integer> loadCompletedEpisodes(Long tmdbId, Long subscribeId, Integer season) {
      if ((tmdbId != null || subscribeId != null) && season != null && season > 0) {
         List<MovieDownloadRecordEntity> records = this.listRecordsByStatuses("tv", tmdbId, subscribeId, DOWNLOAD_COMPLETED_STATUSES);
         return this.extractEpisodesFromRecords(records, season);
      } else {
         return List.of();
      }
   }

   private List<Integer> extractEpisodesFromRecords(List<MovieDownloadRecordEntity> records, Integer season) {
      if (CollectionUtils.isEmpty(records)) {
         return List.of();
      } else {
         Set<Integer> episodes = new LinkedHashSet<>();

         for (MovieDownloadRecordEntity record : records) {
            episodes.addAll(this.parseEpisodesFromTitle(record == null ? null : record.getTitle(), season));
            episodes.addAll(this.parseEpisodesFromTitle(record == null ? null : record.getQbTorrentName(), season));
            episodes.addAll(this.parseEpisodesFromEpisodeCodes(record == null ? null : record.getEpisodeCodes(), season));
         }

         return episodes.stream().sorted().toList();
      }
   }

   private List<MovieDownloadRecordEntity> listRecordsByStatuses(String mediaType, Long tmdbId, Long subscribeId, Set<String> statuses) {
      String normalizedType = this.trimToNull(mediaType);
      if (StringUtils.hasText(normalizedType) && (tmdbId != null || subscribeId != null) && !CollectionUtils.isEmpty(statuses)) {
         QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
         wrapper.eq("del_flag", Integer.valueOf(0));
         wrapper.eq("media_type", normalizedType);
         wrapper.in("status", statuses);
         if (tmdbId != null && subscribeId != null) {
            wrapper.and(item -> item.eq("tmdb_id", tmdbId).or().eq("subscribe_id", subscribeId));
         } else if (tmdbId != null) {
            wrapper.eq("tmdb_id", tmdbId);
         } else {
            wrapper.eq("subscribe_id", subscribeId);
         }

         return this.movieDownloadRecordMapper.selectList(wrapper);
      } else {
         return List.of();
      }
   }

   private Set<Integer> parseEpisodesFromTitle(String title, Integer season) {
      Set<Integer> episodes = new LinkedHashSet<>();
      String value = this.trimToNull(title);
      if (!StringUtils.hasText(value)) {
         return episodes;
      } else {
         MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parse(value);
         if (meta == null || !meta.hasEpisode()) {
            return episodes;
         } else if (meta.getBeginSeason() != null && season != null && !season.equals(meta.getBeginSeason())) {
            return episodes;
         } else {
            episodes.addAll(this.extractEpisodeSet(meta));
            return episodes;
         }
      }
   }

   private Set<Integer> parseEpisodesFromEpisodeCodes(String episodeCodes, Integer season) {
      Set<Integer> episodes = new LinkedHashSet<>();
      String value = this.trimToNull(episodeCodes);
      if (!StringUtils.hasText(value)) {
         return episodes;
      } else {
         String[] parts = value.split(",");

         for (String part : parts) {
            String token = this.trimToNull(part);
            if (StringUtils.hasText(token)) {
               Matcher matcher = EPISODE_CODE_PATTERN.matcher(token);
               if (matcher.find()) {
                  Integer seasonNo = this.parseInteger(matcher.group(1));
                  Integer episodeNo = this.parseInteger(matcher.group(2));
                  if (seasonNo != null && episodeNo != null && episodeNo > 0 && (season == null || season.equals(seasonNo))) {
                     episodes.add(episodeNo);
                  }
               }
            }
         }

         return episodes;
      }
   }

   private String buildResultUniqueKey(MoviePtSearchResult result) {
      return result == null
         ? ""
         : String.format(
            "%s|%s|%s|%s",
            result.getSiteId(),
            this.trimToNull(result.getTorrentId()),
            this.trimToNull(result.getDownloadUrl()),
            this.trimToNull(result.getTitle())
         );
   }

   private boolean isTvSubscribe(MoviePtSubscribeEntity subscribe) {
      return subscribe == null ? false : "tv".equalsIgnoreCase(this.trimToNull(subscribe.getType()));
   }

   private boolean isMovieSubscribe(MoviePtSubscribeEntity subscribe) {
      return subscribe == null ? false : "movie".equalsIgnoreCase(this.trimToNull(subscribe.getType()));
   }

   private int resolveMaxEpisode(List<Integer> episodes) {
      return CollectionUtils.isEmpty(episodes) ? 0 : episodes.stream().filter(Objects::nonNull).max(Integer::compareTo).orElse(0);
   }

   private void advanceStartEpisodeAfterDownloadSuccess(
      MoviePtSubscribeEntity subscribe, List<Integer> downloadedEpisodes, List<Integer> remainingMissingEpisodes
   ) {
      if (subscribe != null && this.isTvSubscribe(subscribe)) {
         int currentStart = this.normalizeStartEpisode(subscribe.getStartEpisode());
         int nextStart = currentStart;
         List<Integer> normalizedRemaining = this.normalizeEpisodeList(remainingMissingEpisodes);
         if (!CollectionUtils.isEmpty(normalizedRemaining)) {
            nextStart = normalizedRemaining.get(0);
         } else {
            int maxDownloadedEpisode = this.resolveMaxEpisode(downloadedEpisodes);
            int latestAiredEpisode = subscribe.getTmdbLatestEpisode() == null ? 0 : subscribe.getTmdbLatestEpisode();
            if (maxDownloadedEpisode <= 0 && latestAiredEpisode > 0) {
               maxDownloadedEpisode = latestAiredEpisode;
            }

            if (maxDownloadedEpisode > 0) {
               if (latestAiredEpisode > 0 && maxDownloadedEpisode >= latestAiredEpisode) {
                  nextStart = latestAiredEpisode;
               } else {
                  nextStart = maxDownloadedEpisode + 1;
               }
            }
         }

         if (nextStart > 0 && nextStart != currentStart) {
            subscribe.setStartEpisode(nextStart);
         }
      }
   }

   private Integer parseInteger(String value) {
      String text = this.trimToNull(value);
      if (!StringUtils.hasText(text)) {
         return null;
      } else {
         try {
            return Integer.parseInt(text);
         } catch (NumberFormatException var4) {
            return null;
         }
      }
   }

   private String unwrapExceptionMessage(Throwable throwable) {
      Throwable current = throwable;

      while (current != null && current.getCause() != null) {
         current = current.getCause();
      }

      return current != null && StringUtils.hasText(current.getMessage()) ? current.getMessage() : "unknown error";
   }

   private MoviePtSubscribeEntity getEntityById(Long id) {
      if (id == null) {
         throw new BizException("订阅ID不能为空");
      } else {
         MoviePtSubscribeEntity entity = this.moviePtSubscribeMapper.selectById(id);
         if (entity != null && entity.getDelFlag() != 1) {
            return entity;
         } else {
            throw new BizException("订阅不存在");
         }
      }
   }

   private boolean isEnabled(MoviePtSubscribeEntity subscribe) {
      return subscribe.getEnabled() == null || subscribe.getEnabled() == 1;
   }

   private int normalizeLimit(Integer limit) {
      return limit != null && limit > 0 ? limit : 20;
   }

   private Integer resolveSeason(Integer requestSeason, Integer existingSeason) {
      Integer season = requestSeason != null ? requestSeason : existingSeason;
      if (season == null) {
         season = 1;
      }

      return this.normalizeSeason(season);
   }

   private Integer resolveStartEpisode(Integer requestStartEpisode, Integer existingStartEpisode) {
      Integer startEpisode = requestStartEpisode != null ? requestStartEpisode : existingStartEpisode;
      return this.normalizeStartEpisode(startEpisode);
   }

   private Integer normalizeEnabled(Integer enabled) {
      return enabled == null ? 1 : enabled == 0 ? 0 : 1;
   }

   private Integer normalizeAutoDownload(Integer autoDownload) {
      return autoDownload == null ? 0 : autoDownload == 1 ? 1 : 0;
   }

   private Integer normalizeSeason(Integer season) {
      if (season == null) {
         return null;
      } else if (season <= 0) {
         throw new BizException("season 必须大于 0");
      } else {
         return season;
      }
   }

   private int normalizeStartEpisode(Integer startEpisode) {
      if (startEpisode == null) {
         return 1;
      } else if (startEpisode <= 0) {
         throw new BizException("startEpisode 必须大于 0");
      } else {
         return startEpisode;
      }
   }

   private String normalizeType(String type) {
      String value = this.trimToNull(type);
      if (!StringUtils.hasText(value)) {
         return "movie";
      } else {
         String lower = value.toLowerCase(Locale.ROOT);
         if ("电影".equals(value) || "movie".equals(lower)) {
            return "movie";
         } else if (!"电视剧".equals(value) && !"tv".equals(lower) && !"series".equals(lower)) {
            throw new BizException("type 仅支持 movie/tv 或 电影/电视剧");
         } else {
            return "tv";
         }
      }
   }

   private String normalizeState(String state) {
      String value = this.trimToNull(state);
      if (!StringUtils.hasText(value)) {
         return null;
      } else if (!"完结".equals(value) && !"订阅完结".equals(value) && !"completed".equalsIgnoreCase(value)) {
         String upper = value.toUpperCase(Locale.ROOT);
         if (!"N".equals(upper) && !"R".equals(upper) && !"S".equals(upper) && !"C".equals(upper)) {
            throw new BizException("state 仅支持 N/R/S/C");
         } else {
            return upper;
         }
      } else {
         return "C";
      }
   }

   private List<Long> normalizeSiteIds(List<Long> siteIds) {
      return CollectionUtils.isEmpty(siteIds) ? List.of() : siteIds.stream().map(siteId -> {
         if (siteId == null) {
            return null;
         } else if (siteId <= 0L) {
            throw new BizException("siteId 必须大于 0");
         } else {
            return (Long)siteId;
         }
      }).filter(Objects::nonNull).distinct().toList();
   }

   private List<Long> parseSiteIds(String rawSiteIds) {
      String value = this.trimToNull(rawSiteIds);
      if (!StringUtils.hasText(value)) {
         return List.of();
      } else {
         LinkedHashSet<Long> result = new LinkedHashSet<>();

         for (String part : value.split(",")) {
            Long siteId = this.parseLongSafely(this.trimToNull(part));
            if (siteId != null && siteId > 0L) {
               result.add(siteId);
            }
         }

         return new ArrayList<>(result);
      }
   }

   private String joinSiteIds(List<Long> siteIds) {
      List<Long> normalized = this.normalizeSiteIds(siteIds);
      return CollectionUtils.isEmpty(normalized) ? null : normalized.stream().map(String::valueOf).collect(Collectors.joining(","));
   }

   private String resolveSiteNames(String rawSiteIds) {
      List<Long> siteIds = this.parseSiteIds(rawSiteIds);
      return CollectionUtils.isEmpty(siteIds) ? "不限站点" : siteIds.stream().map(id -> {
         try {
            MoviePtSite site = this.moviePtSiteService.getById(id);
            return site != null && StringUtils.hasText(site.getName()) ? site.getName() : null;
         } catch (Exception var3) {
            return null;
         }
      }).filter(Objects::nonNull).collect(Collectors.joining(", "));
   }

   private Long pickFirstSiteId(List<Long> siteIds) {
      return CollectionUtils.isEmpty(siteIds) ? null : siteIds.get(0);
   }

   private String trimToNull(String value) {
      if (value == null) {
         return null;
      } else {
         String trimmed = value.trim();
         return trimmed.isEmpty() ? null : trimmed;
      }
   }

   private String firstNonBlank(String first, String fallback) {
      String firstValue = this.trimToNull(first);
      return StringUtils.hasText(firstValue) ? firstValue : this.trimToNull(fallback);
   }

   private Long parseLongSafely(String text) {
      if (!StringUtils.hasText(text)) {
         return null;
      } else {
         try {
            return Long.parseLong(text);
         } catch (NumberFormatException var3) {
            return null;
         }
      }
   }

   private Integer normalizePositive(Integer value) {
      return value != null && value > 0 ? value : null;
   }

   private String formatSeasonCode(Integer season) {
      Integer normalizedSeason = this.normalizePositive(season);
      return normalizedSeason == null ? null : String.format("S%02d", normalizedSeason);
   }

   private String formatSeasonEpisode(Integer season, Integer episode) {
      Integer normalizedSeason = this.normalizePositive(season);
      Integer normalizedEpisode = this.normalizePositive(episode);
      return normalizedSeason != null && normalizedEpisode != null ? String.format("S%02dE%02d", normalizedSeason, normalizedEpisode) : null;
   }

   private String buildUpdateProgress(String latestUpdatedSeasonEpisode, String latestAiredSeasonEpisode) {
      if (StringUtils.hasText(latestUpdatedSeasonEpisode) && StringUtils.hasText(latestAiredSeasonEpisode)) {
         return latestUpdatedSeasonEpisode + " / " + latestAiredSeasonEpisode;
      } else if (StringUtils.hasText(latestUpdatedSeasonEpisode)) {
         return latestUpdatedSeasonEpisode;
      } else {
         return StringUtils.hasText(latestAiredSeasonEpisode) ? "未下载 / " + latestAiredSeasonEpisode : null;
      }
   }

   private MoviePtSubscribe toModel(MoviePtSubscribeEntity entity, Map<Long, String> scrapePathNameMap, Map<Long, String> downloaderNameMap) {
      if (entity == null) {
         return null;
      } else {
         boolean tvSubscribe = "tv".equalsIgnoreCase(this.trimToNull(entity.getType()));
         Integer subscribedSeason = tvSubscribe ? this.normalizePositive(entity.getSeason()) : null;
         Integer subscribedSeasonCount = subscribedSeason == null ? 0 : 1;
         Integer latestUpdatedEpisode = tvSubscribe ? this.normalizePositive(entity.getLastDownloadedEpisode()) : null;
         Integer latestUpdatedSeason = latestUpdatedEpisode == null ? null : subscribedSeason;
         Integer latestAiredEpisode = tvSubscribe ? this.normalizePositive(entity.getTmdbLatestEpisode()) : null;
         String latestUpdatedSeasonEpisode = this.formatSeasonEpisode(latestUpdatedSeason, latestUpdatedEpisode);
         String latestAiredSeasonEpisode = this.formatSeasonEpisode(subscribedSeason, latestAiredEpisode);
         return MoviePtSubscribe.builder()
            .id(entity.getId())
            .name(entity.getName())
            .originalTitle(entity.getOriginalTitle())
            .keyword(entity.getKeyword())
            .type(entity.getType())
            .year(entity.getYear())
            .tmdbId(entity.getTmdbId())
            .posterPath(entity.getPosterPath())
            .backdropPath(entity.getBackdropPath())
            .season(entity.getSeason())
            .startEpisode(entity.getStartEpisode())
            .siteId(this.parseSiteIds(entity.getSiteId()))
            .scrapePathConfigId(entity.getScrapePathConfigId())
            .scrapePathConfigName(this.resolveScrapePathConfigName(entity.getScrapePathConfigId(), scrapePathNameMap))
            .downloaderId(entity.getDownloaderId())
            .downloaderName(this.resolveDownloaderName(entity.getDownloaderId(), downloaderNameMap))
            .autoDownload(entity.getAutoDownload())
            .enabled(entity.getEnabled())
            .state(entity.getState())
            .tmdbLatestEpisode(entity.getTmdbLatestEpisode())
            .lastDownloadedEpisode(entity.getLastDownloadedEpisode())
            .subscribedSeasonCount(subscribedSeasonCount)
            .subscribedSeasonCode(this.formatSeasonCode(subscribedSeason))
            .latestUpdatedSeason(latestUpdatedSeason)
            .latestUpdatedEpisode(latestUpdatedEpisode)
            .latestUpdatedSeasonEpisode(latestUpdatedSeasonEpisode)
            .latestAiredSeasonEpisode(latestAiredSeasonEpisode)
            .updateProgress(this.buildUpdateProgress(latestUpdatedSeasonEpisode, latestAiredSeasonEpisode))
            .lastMatchedCount(entity.getLastMatchedCount())
            .lastSearchTime(entity.getLastSearchTime())
            .lastDownloadTime(entity.getLastDownloadTime())
            .lastError(entity.getLastError())
            .createDatetime(entity.getCreateDatetime())
            .updateDatetime(entity.getUpdateDatetime())
            .build();
      }
   }

   private Map<Long, String> buildScrapePathNameMap() {
      return this.movieScrapePathConfigService
         .list()
         .stream()
         .filter(Objects::nonNull)
         .filter(config -> config.getId() != null)
         .collect(Collectors.toMap(MovieScrapePathConfig::getId, config -> this.trimToNull(config.getName()), (first, second) -> first, LinkedHashMap::new));
   }

   private String resolveScrapePathConfigName(Long scrapePathConfigId, Map<Long, String> scrapePathNameMap) {
      if (scrapePathConfigId == null) {
         return null;
      } else if (scrapePathNameMap != null) {
         return scrapePathNameMap.get(scrapePathConfigId);
      } else {
         try {
            MovieScrapePathConfig config = this.movieScrapePathConfigService.getById(scrapePathConfigId);
            return config == null ? null : this.trimToNull(config.getName());
         } catch (Exception var4) {
            return null;
         }
      }
   }

   private Map<Long, String> buildDownloaderNameMap() {
      return this.movieQbittorrentService
         .listConfigs()
         .stream()
         .filter(Objects::nonNull)
         .filter(config -> config.getId() != null)
         .collect(
            Collectors.toMap(MovieQbittorrentConfig::getId, config -> this.trimToNull(config.getDownloaderName()), (first, second) -> first, LinkedHashMap::new)
         );
   }

   private String resolveDownloaderName(Long downloaderId, Map<Long, String> downloaderNameMap) {
      if (downloaderId == null) {
         return null;
      } else if (downloaderNameMap != null) {
         return downloaderNameMap.get(downloaderId);
      } else {
         try {
            MovieQbittorrentConfig config = this.movieQbittorrentService.getConfigById(downloaderId);
            return config == null ? null : this.trimToNull(config.getDownloaderName());
         } catch (Exception var4) {
            return null;
         }
      }
   }

   private void sendSubscribeDownloadNotify(
      MoviePtSubscribeEntity subscribe, List<MoviePtSearchResult> notifyResults, String summary, List<Integer> downloadedEpisodes
   ) {
      try {
         String mediaTypeLabel = "tv".equals(subscribe.getType()) ? "剧集" : "电影";
         if (CollectionUtils.isEmpty(notifyResults)) {
            return;
         }

         List<MoviePtSubscribeServiceImpl.SubscribeDownloadNotifyItem> notifyItems = this.buildSubscribeDownloadNotifyItems(
            subscribe, notifyResults, downloadedEpisodes
         );
         if (CollectionUtils.isEmpty(notifyItems)) {
            return;
         }

         for (MoviePtSubscribeServiceImpl.SubscribeDownloadNotifyItem item : notifyItems) {
            String downloadTitle = item.downloadTitle();
            String downloadSize = this.trimToNull(item.downloadSize());
            String downloadSizeLine = this.buildDownloadSizeLine(downloadSize);
            Long actualSiteId = item.siteId() != null
               ? item.siteId()
               : (subscribe.getSiteId() != null ? this.parseSiteIds(subscribe.getSiteId()).stream().findFirst().orElse(null) : null);
            String siteName = actualSiteId != null ? this.resolveSiteNames(String.valueOf(actualSiteId)) : "未知站点";
            Map<String, String> extras = new HashMap<>();
            extras.put("subscribeName", subscribe.getName());
            extras.put("movieName", subscribe.getName());
            extras.put("mediaTypeLabel", mediaTypeLabel);
            extras.put("downloadTitle", downloadTitle);
            extras.put("downloadSize", downloadSize == null ? "" : downloadSize);
            extras.put("downloadSizeLine", downloadSizeLine);
            extras.put("downloadSummary", summary);
            extras.put("siteName", siteName);
            if (StringUtils.hasText(subscribe.getPosterPath())) {
               SendPhotoRequest request = new SendPhotoRequest();
               request.setParseMode("Markdown");
               request.setName("订阅下载通知");
               request.setOverview(String.format("[%s] %s 已触发自动下载：%s", mediaTypeLabel, subscribe.getName(), downloadTitle));
               request.setImgUrl(subscribe.getPosterPath());
               request.setBackdropPath(subscribe.getBackdropPath());
               request.setSize(downloadSize);
               this.movieNotifyTmdbEnrichService.fillTmdbInfo(request, subscribe.getTmdbId(), subscribe.getType());
               request.setName(String.format("订阅下载: %s", subscribe.getName()));
               request.setExtraVariables(extras);
               this.notifyUtils.sendMultiChannel(request, "subscribe_download", NotifyMessageType.PHOTO_DETAIL, false, "telegram", "dingding", "messagepush");
               this.notifyUtils.sendMultiChannel(request, "subscribe_download", NotifyMessageType.PHOTO_DETAIL, false, "wechat", "wechatBot");
            } else {
               SendMessageRequest request = new SendMessageRequest();
               request.setParseMode("Markdown");
               request.setName("订阅下载通知");
               request.setOverview(String.format("[%s] %s 已触发自动下载：%s", mediaTypeLabel, subscribe.getName(), downloadTitle));
               request.setImgUrl(subscribe.getPosterPath());
               request.setExtraVariables(extras);
               this.notifyUtils.sendMultiChannel(request, "subscribe_download", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
            }
         }
      } catch (Exception var16) {
         log.warn("发送订阅下载通知失败 subscribeId={}: {}", subscribe.getId(), var16.getMessage());
      }
   }

   private List<MoviePtSubscribeServiceImpl.SubscribeDownloadNotifyItem> buildSubscribeDownloadNotifyItems(
      MoviePtSubscribeEntity subscribe, List<MoviePtSearchResult> notifyResults, List<Integer> downloadedEpisodes
   ) {
      if (CollectionUtils.isEmpty(notifyResults)) {
         return List.of();
      } else {
         if (this.isTvSubscribe(subscribe)) {
            boolean hasPack = false;

            for (MoviePtSearchResult res : notifyResults) {
               if (res != null && StringUtils.hasText(res.getTitle())) {
                  MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parse(res.getTitle());
                  if (meta != null && meta.getBeginEpisode() == null) {
                     hasPack = true;
                     break;
                  }

                  String title = res.getTitle().toLowerCase();
                  if (title.contains("全集") || title.contains("complete") || title.contains("full season")) {
                     hasPack = true;
                     break;
                  }
               }
            }

            List<Integer> normalizedEpisodes = this.normalizeEpisodeList(downloadedEpisodes);
            if (!CollectionUtils.isEmpty(normalizedEpisodes)) {
               String episodesTitle;
               if (hasPack) {
                  String completed = this.buildTvDownloadedEpisodeTitle(null, normalizedEpisodes);
                  episodesTitle = this.formatSeasonCode(subscribe.getSeason()) + " (补齐 " + completed + ")";
               } else {
                  episodesTitle = this.buildTvDownloadedEpisodeTitle(subscribe, normalizedEpisodes);
               }

               String firstSize = notifyResults.stream()
                  .map(MoviePtSearchResult::getSize)
                  .map(this::trimToNull)
                  .filter(StringUtils::hasText)
                  .findFirst()
                  .orElse(null);
               Long firstSiteId = notifyResults.stream().map(MoviePtSearchResult::getSiteId).filter(Objects::nonNull).findFirst().orElse(null);
               return List.of(new MoviePtSubscribeServiceImpl.SubscribeDownloadNotifyItem(episodesTitle, firstSize, firstSiteId));
            }
         }

         List<MoviePtSubscribeServiceImpl.SubscribeDownloadNotifyItem> fallbackItems = new ArrayList<>();

         for (MoviePtSearchResult notifyResult : notifyResults) {
            if (notifyResult != null) {
               fallbackItems.add(
                  new MoviePtSubscribeServiceImpl.SubscribeDownloadNotifyItem(
                     this.buildDownloadRecordTitle(subscribe, notifyResult), this.trimToNull(notifyResult.getSize()), notifyResult.getSiteId()
                  )
               );
            }
         }

         return fallbackItems;
      }
   }

   private String buildTvDownloadedEpisodeTitle(MoviePtSubscribeEntity subscribe, List<Integer> downloadedEpisodes) {
      List<Integer> normalizedEpisodes = this.normalizeEpisodeList(downloadedEpisodes);
      if (CollectionUtils.isEmpty(normalizedEpisodes)) {
         return "";
      } else {
         Integer season = subscribe == null ? null : this.normalizePositive(subscribe.getSeason());
         List<String> ranges = new ArrayList<>();
         int start = normalizedEpisodes.get(0);
         int prev = start;

         for (int i = 1; i < normalizedEpisodes.size(); i++) {
            int current = normalizedEpisodes.get(i);
            if (current == prev + 1) {
               prev = current;
            } else {
               ranges.add(this.formatEpisodeRange(season, start, prev));
               start = current;
               prev = current;
            }
         }

         ranges.add(this.formatEpisodeRange(season, start, prev));
         return String.join("、", ranges);
      }
   }

   private String formatEpisodeRange(Integer season, int startEpisode, int endEpisode) {
      int start = Math.min(startEpisode, endEpisode);
      int end = Math.max(startEpisode, endEpisode);
      if (season != null && season > 0) {
         return start == end ? String.format("S%02dE%02d", season, start) : String.format("S%02dE%02d-E%02d", season, start, end);
      } else {
         return start == end ? String.format("E%02d", start) : String.format("E%02d-E%02d", start, end);
      }
   }

   private String buildDownloadSizeLine(String size) {
      return !StringUtils.hasText(size) ? "" : "\ud83d\udcbe 大小：" + size + "\n";
   }

   private void sendSubscribeAddedNotify(MoviePtSubscribeEntity subscribe) {
      try {
         String mediaTypeLabel = "tv".equals(subscribe.getType()) ? "剧集" : "电影";
         String displayName = subscribe.getName();
         if ("tv".equals(subscribe.getType()) && subscribe.getSeason() != null) {
            displayName = displayName + " 第" + subscribe.getSeason() + "季";
         }

         Map<String, String> extras = new HashMap<>();
         extras.put("subscribeName", displayName);
         extras.put("movieName", displayName);
         extras.put("mediaTypeLabel", mediaTypeLabel);
         extras.put("year", subscribe.getYear());
         extras.put("keyword", subscribe.getKeyword());
         extras.put("siteName", this.resolveSiteNames(subscribe.getSiteId()));
         if (StringUtils.hasText(subscribe.getPosterPath())) {
            SendPhotoRequest request = new SendPhotoRequest();
            request.setParseMode("Markdown");
            request.setName("订阅新增通知");
            request.setOverview(String.format("已添加新的 %s 订阅：%s", mediaTypeLabel, displayName));
            request.setImgUrl(subscribe.getPosterPath());
            request.setBackdropPath(subscribe.getBackdropPath());
            this.movieNotifyTmdbEnrichService.fillTmdbInfo(request, subscribe.getTmdbId(), subscribe.getType());
            request.setName(String.format("订阅新增: %s", displayName));
            request.setExtraVariables(extras);
            this.notifyUtils.sendMultiChannel(request, "subscribe_added", NotifyMessageType.PHOTO_DETAIL, false, "telegram", "dingding", "messagepush");
            this.notifyUtils.sendMultiChannel(request, "subscribe_added", NotifyMessageType.PHOTO_DETAIL, false, "wechat", "wechatBot");
         } else {
            SendMessageRequest request = new SendMessageRequest();
            request.setParseMode("Markdown");
            request.setName("订阅新增通知");
            request.setOverview(String.format("已添加新的 %s 订阅：%s", mediaTypeLabel, displayName));
            request.setImgUrl(subscribe.getPosterPath());
            request.setExtraVariables(extras);
            this.notifyUtils.sendMultiChannel(request, "subscribe_added", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
         }
      } catch (Exception var6) {
         log.warn("发送订阅新增通知失败 subscribeId={}: {}", subscribe.getId(), var6.getMessage());
      }
   }

   private void fillTmdbInfo(SendPhotoRequest request, Long tmdbId, String type) {
      if (tmdbId != null) {
         try {
            if ("movie".equals(type)) {
               MovieDb movie = this.tmdbService.getMovieDetails(tmdbId.intValue(), "zh-CN", MovieAppendToResponse.TRANSLATIONS);
               if (movie != null
                  && !StringUtils.hasText(movie.getOverview())
                  && movie.getTranslations() != null
                  && movie.getTranslations().getTranslations() != null) {
                  List<Translation> translations = movie.getTranslations()
                     .getTranslations()
                     .stream()
                     .filter(t -> t.getData() != null && StringUtils.hasText(t.getData().getOverview()))
                     .toList();
                  Translation bestTranslation = translations.stream()
                     .filter(t -> "zh".equalsIgnoreCase(t.getIso6391()) || "cn".equalsIgnoreCase(t.getIso6391()))
                     .findFirst()
                     .orElseGet(
                        () -> translations.stream()
                              .filter(t -> "en".equalsIgnoreCase(t.getIso6391()))
                              .findFirst()
                              .orElseGet(() -> translations.stream().findAny().orElse(null))
                     );
                  if (bestTranslation != null) {
                     movie.setOverview(bestTranslation.getData().getOverview());
                  }
               }

               if (movie != null) {
                  if (StringUtils.hasText(movie.getReleaseDate())) {
                     try {
                        LocalDate date = LocalDate.parse(movie.getReleaseDate());
                        request.setProductionYear(date.getYear());
                     } catch (Exception var8) {
                     }
                  }

                  if (movie.getGenres() != null) {
                     request.setGenres(movie.getGenres().stream().map(g -> g.getName()).collect(Collectors.joining(",")));
                  }

                  request.setVoteAverage(movie.getVoteAverage());
                  request.setVoteCount(movie.getVoteCount());
                  if (movie.getProductionCountries() != null) {
                     request.setProductionCountries(movie.getProductionCountries().stream().map(p -> p.getName()).collect(Collectors.joining(",")));
                  }

                  if (StringUtils.hasText(movie.getOverview())) {
                     request.setOverview(movie.getOverview());
                  }

                  request.setType("Movie");
                  request.setDisplayTitle(movie.getTitle());
               }
            } else if ("tv".equals(type)) {
               TvSeriesDb tv = this.tmdbService.getTvSeries(tmdbId.intValue(), "zh-CN", TvSeriesAppendToResponse.TRANSLATIONS);
               if (tv != null && !StringUtils.hasText(tv.getOverview()) && tv.getTranslations() != null && tv.getTranslations().getTranslations() != null) {
                  List<info.movito.themoviedbapi.model.tv.series.Translation> translations = tv.getTranslations()
                     .getTranslations()
                     .stream()
                     .filter(t -> t.getData() != null && StringUtils.hasText(t.getData().getOverview()))
                     .toList();
                  info.movito.themoviedbapi.model.tv.series.Translation bestTranslation = translations.stream()
                     .filter(t -> "zh".equalsIgnoreCase(t.getIso6391()) || "cn".equalsIgnoreCase(t.getIso6391()))
                     .findFirst()
                     .orElseGet(
                        () -> translations.stream()
                              .filter(t -> "en".equalsIgnoreCase(t.getIso6391()))
                              .findFirst()
                              .orElseGet(() -> translations.stream().findAny().orElse(null))
                     );
                  if (bestTranslation != null) {
                     tv.setOverview(bestTranslation.getData().getOverview());
                  }
               }

               if (tv != null) {
                  if (StringUtils.hasText(tv.getFirstAirDate())) {
                     try {
                        LocalDate date = LocalDate.parse(tv.getFirstAirDate());
                        request.setProductionYear(date.getYear());
                     } catch (Exception var7) {
                     }
                  }

                  if (tv.getGenres() != null) {
                     request.setGenres(tv.getGenres().stream().map(g -> g.getName()).collect(Collectors.joining(",")));
                  }

                  request.setVoteAverage(tv.getVoteAverage());
                  request.setVoteCount(tv.getVoteCount());
                  if (tv.getOriginCountry() != null) {
                     request.setProductionCountries(String.join(",", tv.getOriginCountry()));
                  }

                  if (StringUtils.hasText(tv.getOverview())) {
                     request.setOverview(tv.getOverview());
                  }

                  request.setType("Series");
                  request.setDisplayTitle(tv.getName());
               }
            }
         } catch (Exception var9) {
            log.warn("获取TMDB详情失败 tmdbId={}: {}", tmdbId, var9.getMessage());
         }
      }
   }

   @Generated
   public MoviePtSubscribeServiceImpl(
      final MoviePtSubscribeMapper moviePtSubscribeMapper,
      final MovieDownloadRecordMapper movieDownloadRecordMapper,
      final MovieDownloadRecordService movieDownloadRecordService,
      final MoviePtSearchService moviePtSearchService,
      final MoviePtDownloadService moviePtDownloadService,
      final MoviePtSiteService moviePtSiteService,
      final MovieQbittorrentService movieQbittorrentService,
      final MovieScrapePathConfigService movieScrapePathConfigService,
      final TmdbService tmdbService,
      final MovieNotifyTmdbEnrichService movieNotifyTmdbEnrichService,
      final NotifyUtils notifyUtils,
      final SystemConfigService systemConfigService,
      final ConfigCacheLoaderUtils configCacheLoaderUtils,
      final ApplicationEventPublisher applicationEventPublisher
   ) {
      this.moviePtSubscribeMapper = moviePtSubscribeMapper;
      this.movieDownloadRecordMapper = movieDownloadRecordMapper;
      this.movieDownloadRecordService = movieDownloadRecordService;
      this.moviePtSearchService = moviePtSearchService;
      this.moviePtDownloadService = moviePtDownloadService;
      this.moviePtSiteService = moviePtSiteService;
      this.movieQbittorrentService = movieQbittorrentService;
      this.movieScrapePathConfigService = movieScrapePathConfigService;
      this.tmdbService = tmdbService;
      this.movieNotifyTmdbEnrichService = movieNotifyTmdbEnrichService;
      this.notifyUtils = notifyUtils;
      this.systemConfigService = systemConfigService;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
      this.applicationEventPublisher = applicationEventPublisher;
   }

   private static record BatchAutoDownloadExecution(
      MoviePtSearchResult selectedResult,
      MovieActionResponse summaryResult,
      List<Integer> downloadedEpisodes,
      List<Integer> remainingMissingEpisodes,
      int attemptCount,
      int successCount,
      int failureCount,
      String lastError,
      List<MoviePtSearchResult> successfulResults
   ) {
   }

   private static record SearchFilter(String title, String originalTitle, String year, String type) {
   }

   private static record SubscribeDownloadNotifyItem(String downloadTitle, String downloadSize, Long siteId) {
   }

   private static record SubscribeSearchPlan(
      String keyword,
      List<String> searchedKeywords,
      Integer tmdbLatestEpisode,
      List<Integer> downloadedEpisodes,
      List<Integer> missingEpisodes,
      boolean skipSearch,
      String skipMessage
   ) {
   }

   private static record TmdbImagePaths(String posterPath, String backdropPath) {
   }

   private static record TmdbSeasonProgress(Integer latestAiredEpisode, Integer latestEpisode) {
   }
}
