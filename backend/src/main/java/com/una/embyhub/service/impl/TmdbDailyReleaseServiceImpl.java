package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.foam.client.TmdbClient;
import com.una.embyhub.mapper.TmdbDailyReleaseMapper;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.request.tmdbdaily.TmdbDailyReleaseConfigRequest;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseCalendarDayResponse;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseResponse;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseSyncResponse;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.model.entity.TmdbDailyRelease;
import com.una.embyhub.service.SystemConfigService;
import com.una.embyhub.service.TelegramService;
import com.una.embyhub.service.TmdbDailyReleaseService;
import com.una.embyhub.util.TmdbDailyReleasePosterRenderer;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

@Service
public class TmdbDailyReleaseServiceImpl extends ServiceImpl<TmdbDailyReleaseMapper, TmdbDailyRelease> implements TmdbDailyReleaseService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbDailyReleaseServiceImpl.class);
   private static final String MEDIA_MOVIE = "movie";
   private static final String MEDIA_TV = "tv";
   private static final String TELEGRAM_GROUP_TARGET_POINTS = "points";
   private static final String TELEGRAM_GROUP_TARGET_CHANNEL = "channel";
   private static final String TMDB_MOVIE_URL = "https://www.themoviedb.org/movie/";
   private static final String TMDB_TV_URL = "https://www.themoviedb.org/tv/";
   private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
   private static final int TV_SEASON_SCAN_LIMIT = 3;
   private final TmdbClient tmdbClient;
   private final SystemConfigService systemConfigService;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;
   private final NotifyUtils notifyUtils;
   private final TelegramClientUtils telegramClientUtils;
   private final TelegramService telegramService;
   private final TransactionTemplate transactionTemplate;
   private final Set<String> asyncSyncKeys = ConcurrentHashMap.newKeySet();
   @Value("${tmdb.imageUrl:https://image.tmdb.org/t/p/original}")
   private String tmdbImageUrl;

   @Override
   public TmdbDailyReleaseConfigRequest getConfig() {
      return this.loadConfig();
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TmdbDailyReleaseConfigRequest saveConfig(TmdbDailyReleaseConfigRequest request) {
      TmdbDailyReleaseConfigRequest normalized = this.normalizeConfig(request == null ? new TmdbDailyReleaseConfigRequest() : request);
      SystemConfig config = this.systemConfigService.lambdaQuery().eq(SystemConfig::getConfigKey, "tmdb_daily_release_config").one();
      if (config == null) {
         config = new SystemConfig();
         config.setName("追新定时任务");
         config.setConfigKey("tmdb_daily_release_config");
         config.setIsEnabled(1);
         config.setDescription("每日抓取 TMDB 上映电影和播出剧集，并推送 Telegram 图片和企业微信文本");
         config.setIsUpdate(1);
      }

      config.setConfigValue(JSON.toJSONString(normalized));
      if (config.getId() == null) {
         this.systemConfigService.save(config);
      } else {
         this.systemConfigService.updateById(config);
      }

      this.configCacheLoaderUtils.loadConfigCache();
      return normalized;
   }

   @Override
   public TmdbDailyReleaseSyncResponse runDailyJob() {
      TmdbDailyReleaseConfigRequest config = this.loadConfig();
      if (!Boolean.TRUE.equals(config.getEnabled())) {
         TmdbDailyReleaseSyncResponse response = new TmdbDailyReleaseSyncResponse();
         response.setDate(LocalDate.now(this.resolveZone(config)).format(DATE_FORMATTER));
         response.setMessage("追新定时任务未启用，已跳过");
         return response;
      } else {
         return this.syncDate(LocalDate.now(this.resolveZone(config)), true);
      }
   }

   @Override
   public TmdbDailyReleaseSyncResponse submitSyncDate(LocalDate date, boolean notify) {
      TmdbDailyReleaseConfigRequest config = this.loadConfig();
      LocalDate targetDate = date == null ? LocalDate.now(this.resolveZone(config)) : date;
      String syncKey = targetDate.format(DATE_FORMATTER) + "|" + notify;
      if (this.asyncSyncKeys.add(syncKey)) {
         CompletableFuture.runAsync(() -> {
            try {
               log.info("追新异步同步开始 date={}, notify={}", targetDate, notify);
               TmdbDailyReleaseSyncResponse result = this.syncDate(targetDate, notify);
               log.info("追新异步同步完成 date={}, total={}, notified={}", targetDate, result.getTotalCount(), result.getNotified());
            } catch (Exception var8) {
               log.error("追新异步同步失败 date={}, notify={}", targetDate, notify, var8);
            } finally {
               this.asyncSyncKeys.remove(syncKey);
            }
         });
      } else {
         log.info("追新异步同步已在执行中 date={}, notify={}", targetDate, notify);
      }

      TmdbDailyReleaseSyncResponse response = new TmdbDailyReleaseSyncResponse();
      response.setDate(targetDate.format(DATE_FORMATTER));
      response.setMessage("已提交同步");
      return response;
   }

   @Override
   public TmdbDailyReleaseSyncResponse syncDate(LocalDate date, boolean notify) {
      TmdbDailyReleaseConfigRequest config = this.loadConfig();
      LocalDate targetDate = date == null ? LocalDate.now(this.resolveZone(config)) : date;
      int limit = this.safeLimit(config);
      List<TmdbDailyRelease> candidates = new ArrayList<>();
      if (Boolean.TRUE.equals(config.getIncludeMovies())) {
         candidates.addAll(this.fetchMovies(targetDate, config, limit));
      }

      if (Boolean.TRUE.equals(config.getIncludeTv())) {
         candidates.addAll(this.fetchTv(targetDate, config, limit));
      }

      List<TmdbDailyRelease> rows = candidates.stream()
         .filter(Objects::nonNull)
         .sorted(Comparator.comparing(TmdbDailyRelease::getPopularity, Comparator.nullsLast(BigDecimal::compareTo)).reversed())
         .limit((long)limit)
         .toList();

      for (int i = 0; i < rows.size(); i++) {
         rows.get(i).setRankNo(i + 1);
         rows.get(i).setPublishDate(this.toSqlDate(targetDate));
         this.fillDefaultNotifyState(rows.get(i));
      }

      this.replaceSnapshot(targetDate, rows);
      boolean notified = false;
      String message = "已同步 " + rows.size() + " 条";
      if (notify && (!rows.isEmpty() || Boolean.TRUE.equals(config.getSendWhenEmpty()))) {
         try {
            this.sendNotifications(targetDate, rows, config);
            notified = true;
            message = message + "，已发送通知";
         } catch (Exception var11) {
            message = message + "，通知发送失败：" + var11.getMessage();
            this.updateNotifyError(targetDate, var11.getMessage());
            log.error("追新通知发送失败 date={}", targetDate, var11);
         }
      }

      TmdbDailyReleaseSyncResponse response = new TmdbDailyReleaseSyncResponse();
      response.setDate(targetDate.format(DATE_FORMATTER));
      response.setTotalCount(rows.size());
      response.setMovieCount((int)rows.stream().filter(row -> "movie".equals(row.getMediaType())).count());
      response.setTvCount((int)rows.stream().filter(row -> "tv".equals(row.getMediaType())).count());
      response.setNotified(notified);
      response.setMessage(message);
      return response;
   }

   @Override
   public List<TmdbDailyReleaseResponse> listByDate(LocalDate date) {
      LocalDate targetDate = date == null ? LocalDate.now(this.resolveZone(this.loadConfig())) : date;
      return new LambdaQueryChainWrapper<>(this.baseMapper)
         .eq(TmdbDailyRelease::getPublishDate, this.toSqlDate(targetDate))
         .orderByAsc(TmdbDailyRelease::getRankNo)
         .list()
         .stream()
         .map(this::toResponse)
         .toList();
   }

   @Override
   public List<TmdbDailyReleaseCalendarDayResponse> monthSummary(int year, int month) {
      YearMonth yearMonth = YearMonth.of(year, month);
      LocalDate start = yearMonth.atDay(1);
      LocalDate end = yearMonth.atEndOfMonth();
      ZoneId zone = this.resolveZone(this.loadConfig());
      List<TmdbDailyRelease> rows = new LambdaQueryChainWrapper<>(this.baseMapper)
         .between(TmdbDailyRelease::getPublishDate, this.toSqlDate(start), this.toSqlDate(end))
         .orderByAsc(TmdbDailyRelease::getPublishDate)
         .list();
      Map<LocalDate, TmdbDailyReleaseCalendarDayResponse> dayMap = new LinkedHashMap<>();

      for (TmdbDailyRelease row : rows) {
         LocalDate day = this.toLocalDate(row.getPublishDate(), zone);
         TmdbDailyReleaseCalendarDayResponse dayResponse = dayMap.computeIfAbsent(day, key -> {
            TmdbDailyReleaseCalendarDayResponse value = new TmdbDailyReleaseCalendarDayResponse();
            value.setDate(key.format(DATE_FORMATTER));
            return value;
         });
         dayResponse.setTotalCount(dayResponse.getTotalCount() + 1);
         if ("movie".equals(row.getMediaType())) {
            dayResponse.setMovieCount(dayResponse.getMovieCount() + 1);
         } else if ("tv".equals(row.getMediaType())) {
            dayResponse.setTvCount(dayResponse.getTvCount() + 1);
         }

         boolean rowNotified = this.one(row.getTelegramGroupSent())
            || this.one(row.getTelegramBotSent())
            || this.one(row.getWechatSent())
            || this.one(row.getWechatBotSent());
         dayResponse.setNotified(Boolean.TRUE.equals(dayResponse.getNotified()) || rowNotified);
         if (row.getLastNotifyTime() != null && (dayResponse.getLastNotifyTime() == null || row.getLastNotifyTime().after(dayResponse.getLastNotifyTime()))) {
            dayResponse.setLastNotifyTime(row.getLastNotifyTime());
         }
      }

      return new ArrayList<>(dayMap.values());
   }

   private List<TmdbDailyRelease> fetchMovies(LocalDate targetDate, TmdbDailyReleaseConfigRequest config, int limit) {
      Map<Integer, TmdbDailyRelease> rows = new LinkedHashMap<>();

      for (String region : this.splitConfigList(config.getRegion(), "CN,US,GB", ",", "，", "|")) {
         this.fetchMoviesByRegion(targetDate, config, limit, region).forEach(row -> rows.putIfAbsent(row.getTmdbId(), row));
      }

      return new ArrayList<>(rows.values());
   }

   private List<TmdbDailyRelease> fetchMoviesByRegion(LocalDate targetDate, TmdbDailyReleaseConfigRequest config, int limit, String region) {
      List<TmdbDailyRelease> rows = new ArrayList<>();
      int maxPages = this.safeMaxPages(config);

      for (int page = 1; page <= maxPages && rows.size() < limit * 2; page++) {
         Map<String, Object> params = this.baseTmdbParams(config, page);
         params.put("region", region);
         params.put("sort_by", "popularity.desc");
         params.put("release_date.gte", targetDate.format(DATE_FORMATTER));
         params.put("release_date.lte", targetDate.format(DATE_FORMATTER));
         if (StringUtils.hasText(config.getReleaseTypes())) {
            params.put("with_release_type", config.getReleaseTypes().trim());
         }

         this.applyOriginFilters(params, config);
         JSONObject result = this.tmdbClient.request("/discover/movie", params);
         JSONArray results = result.getJSONArray("results");
         if (results == null || results.isEmpty()) {
            break;
         }

         for (int i = 0; i < results.size() && rows.size() < limit * 2; i++) {
            JSONObject item = results.getJSONObject(i);
            if (this.movieReleaseDateMatchesTarget(item, targetDate)) {
               TmdbDailyRelease row = this.buildMovieRow(item, targetDate);
               if (row != null) {
                  rows.add(row);
               }
            }
         }

         this.sleepQuietly(config);
      }

      return rows;
   }

   private boolean movieReleaseDateMatchesTarget(JSONObject item, LocalDate targetDate) {
      return targetDate.equals(this.parseLocalDate(item.getString("release_date")));
   }

   private List<TmdbDailyRelease> fetchTv(LocalDate targetDate, TmdbDailyReleaseConfigRequest config, int limit) {
      List<TmdbDailyRelease> rows = new ArrayList<>();
      int maxPages = this.safeMaxPages(config);
      int inspected = 0;
      int maxInspections = Math.max(limit * 3, 20);

      for (int page = 1; page <= maxPages && rows.size() < limit * 2 && inspected < maxInspections; page++) {
         Map<String, Object> params = this.baseTmdbParams(config, page);
         params.put("sort_by", "popularity.desc");
         params.put("timezone", this.defaultString(config.getTimezone(), "Asia/Shanghai"));
         params.put("air_date.gte", targetDate.format(DATE_FORMATTER));
         params.put("air_date.lte", targetDate.format(DATE_FORMATTER));
         this.applyOriginFilters(params, config);
         JSONObject result = this.tmdbClient.request("/discover/tv", params);
         JSONArray results = result.getJSONArray("results");
         if (results == null || results.isEmpty()) {
            break;
         }

         for (int i = 0; i < results.size() && rows.size() < limit * 2 && inspected < maxInspections; i++) {
            JSONObject item = results.getJSONObject(i);
            inspected++;
            TmdbDailyRelease row = this.buildTvRow(item, targetDate, config);
            if (row != null) {
               rows.add(row);
            }
         }

         this.sleepQuietly(config);
      }

      return rows;
   }

   private Map<String, Object> baseTmdbParams(TmdbDailyReleaseConfigRequest config, int page) {
      Map<String, Object> params = new LinkedHashMap<>();
      params.put("language", this.defaultString(config.getLanguage(), "zh-CN"));
      params.put("include_adult", false);
      params.put("page", page);
      return params;
   }

   private void applyOriginFilters(Map<String, Object> params, TmdbDailyReleaseConfigRequest config) {
      if (StringUtils.hasText(config.getOriginCountry())) {
         params.put("with_origin_country", config.getOriginCountry().trim());
      }

      if (StringUtils.hasText(config.getOriginalLanguage())) {
         params.put("with_original_language", config.getOriginalLanguage().trim());
      }
   }

   private TmdbDailyRelease buildMovieRow(JSONObject item, LocalDate targetDate) {
      Integer id = item.getInteger("id");
      if (id == null) {
         return null;
      } else {
         TmdbDailyRelease row = new TmdbDailyRelease();
         row.setPublishDate(this.toSqlDate(targetDate));
         row.setMediaType("movie");
         row.setTmdbId(id);
         row.setTitle(this.firstText(item.getString("title"), item.getString("name"), item.getString("original_title")));
         row.setOriginalTitle(item.getString("original_title"));
         row.setOverview(item.getString("overview"));
         row.setPosterPath(this.fullImagePath(item.getString("poster_path")));
         row.setBackdropPath(this.fullImagePath(item.getString("backdrop_path")));
         row.setTmdbUrl("https://www.themoviedb.org/movie/" + id);
         row.setReleaseDate(this.toSqlDate(this.parseLocalDate(item.getString("release_date"))));
         row.setYear(this.resolveYear(item.getString("release_date")));
         row.setVoteAverage(this.decimal(item.getDouble("vote_average")));
         row.setVoteCount(item.getInteger("vote_count"));
         row.setPopularity(this.decimal(item.getDouble("popularity")));
         row.setOriginalLanguage(item.getString("original_language"));
         row.setOriginCountry(this.joinArray(item.getJSONArray("origin_country")));
         row.setSource("discover_movie");
         row.setRawJson(item.toJSONString());
         return row;
      }
   }

   private TmdbDailyRelease buildTvRow(JSONObject item, LocalDate targetDate, TmdbDailyReleaseConfigRequest config) {
      Integer id = item.getInteger("id");
      if (id == null) {
         return null;
      } else {
         TmdbDailyRelease row = new TmdbDailyRelease();
         row.setPublishDate(this.toSqlDate(targetDate));
         row.setMediaType("tv");
         row.setTmdbId(id);
         row.setTitle(this.firstText(item.getString("name"), item.getString("title"), item.getString("original_name")));
         row.setOriginalTitle(item.getString("original_name"));
         row.setOverview(item.getString("overview"));
         row.setPosterPath(this.fullImagePath(item.getString("poster_path")));
         row.setBackdropPath(this.fullImagePath(item.getString("backdrop_path")));
         row.setTmdbUrl("https://www.themoviedb.org/tv/" + id);
         row.setFirstAirDate(this.toSqlDate(this.parseLocalDate(item.getString("first_air_date"))));
         row.setYear(this.resolveYear(item.getString("first_air_date")));
         row.setVoteAverage(this.decimal(item.getDouble("vote_average")));
         row.setVoteCount(item.getInteger("vote_count"));
         row.setPopularity(this.decimal(item.getDouble("popularity")));
         row.setOriginalLanguage(item.getString("original_language"));
         row.setOriginCountry(this.joinArray(item.getJSONArray("origin_country")));
         row.setSource("discover_tv");
         row.setRawJson(item.toJSONString());

         try {
            this.sleepQuietly(config);
            JSONObject detail = this.tmdbClient.fetchTvDetailWithoutRequestStatus((long)id.intValue());
            this.fillTvDetail(row, detail);
            if (!this.resolveTodayEpisodes(row, detail, targetDate, config)) {
               log.debug("跳过非目标日期剧集: tmdbId={}, title={}, targetDate={}", id, row.getTitle(), targetDate);
               return null;
            } else {
               return row;
            }
         } catch (Exception var7) {
            log.warn("解析 TMDB 今日剧集分集失败: tmdbId={}, title={}, reason={}", id, row.getTitle(), var7.getMessage());
            return null;
         }
      }
   }

   private void fillTvDetail(TmdbDailyRelease row, JSONObject detail) {
      if (detail != null && !detail.isEmpty()) {
         row.setTitle(this.firstText(detail.getString("name"), row.getTitle()));
         row.setOriginalTitle(this.firstText(detail.getString("original_name"), row.getOriginalTitle()));
         row.setOverview(this.firstText(detail.getString("overview"), row.getOverview()));
         row.setPosterPath(this.firstText(this.fullImagePath(detail.getString("poster_path")), row.getPosterPath()));
         row.setBackdropPath(this.firstText(this.fullImagePath(detail.getString("backdrop_path")), row.getBackdropPath()));
         row.setFirstAirDate(this.toSqlDate(this.parseLocalDate(detail.getString("first_air_date"))));
         row.setYear(this.resolveYear(detail.getString("first_air_date")));
      }
   }

   private boolean resolveTodayEpisodes(TmdbDailyRelease row, JSONObject detail, LocalDate targetDate, TmdbDailyReleaseConfigRequest config) {
      if (detail != null && !detail.isEmpty()) {
         LinkedHashSet<Integer> candidates = new LinkedHashSet<>();
         this.addEpisodeSeasonIfDate(candidates, detail.getJSONObject("last_episode_to_air"), targetDate);
         this.addEpisodeSeasonIfDate(candidates, detail.getJSONObject("next_episode_to_air"), targetDate);
         candidates.addAll(this.candidateSeasonNumbers(detail, targetDate));

         for (Integer seasonNumber : candidates) {
            if (seasonNumber != null && seasonNumber > 0) {
               this.sleepQuietly(config);
               JSONObject seasonDetail = this.tmdbClient.fetchTvSeasonDetail((long)row.getTmdbId().intValue(), seasonNumber);
               List<Integer> episodes = this.findEpisodesByAirDate(seasonDetail == null ? null : seasonDetail.getJSONArray("episodes"), targetDate);
               if (!episodes.isEmpty()) {
                  this.fillEpisodeRange(row, seasonNumber, episodes);
                  return true;
               }
            }
         }

         return this.applyEpisodeInfo(row, detail.getJSONObject("last_episode_to_air"), List.of(targetDate))
            || this.applyEpisodeInfo(row, detail.getJSONObject("next_episode_to_air"), List.of(targetDate));
      } else {
         return false;
      }
   }

   private List<Integer> candidateSeasonNumbers(JSONObject detail, LocalDate targetDate) {
      JSONArray seasons = detail == null ? null : detail.getJSONArray("seasons");
      if (seasons != null && !seasons.isEmpty()) {
         List<JSONObject> validSeasons = new ArrayList<>();

         for (int i = 0; i < seasons.size(); i++) {
            JSONObject season = seasons.getJSONObject(i);
            Integer number = season.getInteger("season_number");
            if (number != null && number > 0) {
               validSeasons.add(season);
            }
         }

         return validSeasons.stream()
            .sorted(
               Comparator.<JSONObject, Integer>comparing(seasonx -> this.seasonDateRank(seasonx, targetDate))
                  .thenComparing(seasonx -> this.seasonDistance(seasonx, targetDate))
                  .thenComparing(seasonx -> seasonx.getInteger("season_number"), Comparator.nullsLast(Comparator.reverseOrder()))
            )
            .limit(3L)
            .map(seasonx -> seasonx.getInteger("season_number"))
            .toList();
      } else {
         return List.of();
      }
   }

   private int seasonDateRank(JSONObject season, LocalDate targetDate) {
      LocalDate airDate = this.parseLocalDate(season.getString("air_date"));
      if (airDate == null) {
         return 2;
      } else {
         return airDate.isAfter(targetDate) ? 1 : 0;
      }
   }

   private long seasonDistance(JSONObject season, LocalDate targetDate) {
      LocalDate airDate = this.parseLocalDate(season.getString("air_date"));
      return airDate == null ? Long.MAX_VALUE : Math.abs(ChronoUnit.DAYS.between(airDate, targetDate));
   }

   private void addEpisodeSeasonIfDate(Set<Integer> candidates, JSONObject episode, LocalDate targetDate) {
      if (episode != null) {
         LocalDate airDate = this.parseLocalDate(episode.getString("air_date"));
         Integer seasonNumber = episode.getInteger("season_number");
         if (targetDate.equals(airDate) && seasonNumber != null && seasonNumber > 0) {
            candidates.add(seasonNumber);
         }
      }
   }

   private List<Integer> findEpisodesByAirDate(JSONArray episodes, LocalDate targetDate) {
      if (episodes != null && !episodes.isEmpty()) {
         List<Integer> result = new ArrayList<>();

         for (int i = 0; i < episodes.size(); i++) {
            JSONObject episode = episodes.getJSONObject(i);
            LocalDate airDate = this.parseLocalDate(episode.getString("air_date"));
            Integer number = episode.getInteger("episode_number");
            if (targetDate.equals(airDate) && number != null) {
               result.add(number);
            }
         }

         return result.stream().sorted().toList();
      } else {
         return List.of();
      }
   }

   private boolean applyEpisodeInfo(TmdbDailyRelease row, JSONObject episode, List<LocalDate> targetDates) {
      if (episode == null) {
         return false;
      } else {
         LocalDate airDate = this.parseLocalDate(episode.getString("air_date"));
         if (!targetDates.isEmpty() && !targetDates.contains(airDate)) {
            return false;
         } else {
            Integer season = episode.getInteger("season_number");
            Integer episodeNumber = episode.getInteger("episode_number");
            if (season != null && season > 0 && episodeNumber != null && episodeNumber > 0) {
               row.setSeasonNumber(season);
               row.setEpisodeStart(episodeNumber);
               row.setEpisodeEnd(episodeNumber);
               row.setEpisodeDisplay(this.formatEpisodeDisplay(season, episodeNumber, episodeNumber));
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private void fillEpisodeRange(TmdbDailyRelease row, Integer seasonNumber, List<Integer> episodes) {
      Integer start = episodes.get(0);
      Integer end = episodes.get(episodes.size() - 1);
      row.setSeasonNumber(seasonNumber);
      row.setEpisodeStart(start);
      row.setEpisodeEnd(end);
      row.setEpisodeDisplay(this.formatEpisodeDisplay(seasonNumber, start, end));
   }

   private String formatEpisodeDisplay(Integer season, Integer start, Integer end) {
      String seasonText = season == null ? "" : "第" + season + "季 ";
      if (start == null) {
         return seasonText + "今日更新";
      } else {
         return end != null && !Objects.equals(start, end) ? seasonText + "第" + start + "-" + end + "集" : seasonText + "第" + start + "集";
      }
   }

   protected void replaceSnapshot(LocalDate date, List<TmdbDailyRelease> rows) {
      this.transactionTemplate.executeWithoutResult(status -> {
         this.baseMapper.hardDeleteByPublishDate(this.toSqlDate(date));
         if (!rows.isEmpty()) {
            this.saveBatch(rows);
         }
      });
   }

   private void sendNotifications(LocalDate date, List<TmdbDailyRelease> rows, TmdbDailyReleaseConfigRequest config) throws Exception {
      Date notifyTime = new Date();
      boolean telegramGroupSent = false;
      boolean telegramBotSent = false;
      if (Boolean.TRUE.equals(config.getTelegramGroupEnabled()) || Boolean.TRUE.equals(config.getTelegramBotEnabled())) {
         byte[] image = TmdbDailyReleasePosterRenderer.render(date, rows);
         TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
         if (telegramResponse != null) {
            if (Boolean.TRUE.equals(config.getTelegramGroupEnabled())) {
               telegramGroupSent = this.sendTelegramImage(this.resolveTelegramGroupChatId(telegramResponse, config.getTelegramGroupTarget()), image, date, rows);
            }

            if (Boolean.TRUE.equals(config.getTelegramBotEnabled())) {
               telegramBotSent = this.sendTelegramImage(telegramResponse.getBotChatId(), image, date, rows);
            }
         }
      }

      boolean wechatSent = false;
      boolean wechatBotSent = false;
      List<String> textChannels = new ArrayList<>();
      if (Boolean.TRUE.equals(config.getWechatEnabled())) {
         textChannels.add("wechat");
         wechatSent = true;
      }

      if (Boolean.TRUE.equals(config.getWechatBotEnabled())) {
         textChannels.add("wechatBot");
         wechatBotSent = true;
      }

      if (!textChannels.isEmpty()) {
         SendMessageRequest request = new SendMessageRequest();
         request.setParseMode(null);
         request.setExtraVariables(this.buildTextVariables(date, rows));
         this.notifyUtils.sendMultiChannel(request, "tmdb_daily_release_text", false, textChannels.toArray(new String[0]));
      }

      this.updateNotifyStatus(date, telegramGroupSent, telegramBotSent, wechatSent, wechatBotSent, notifyTime, null);
   }

   private boolean sendTelegramImage(String chatId, byte[] image, LocalDate date, List<TmdbDailyRelease> rows) throws Exception {
      if (StringUtils.hasText(chatId) && image != null && image.length != 0) {
         SendPhotoRequest request = new SendPhotoRequest();
         request.setTelegramClient(this.telegramClientUtils.getTelegramClient());
         request.setChatId(chatId);
         request.setName("追新每日上映/播出榜");
         request.setCaption(this.buildTelegramCaption(date, rows));
         request.setImgUrlInputStream(new ByteArrayInputStream(image));
         request.setParseMode(null);
         return this.telegramService.sendPhotoMessage(request);
      } else {
         return false;
      }
   }

   private String resolveTelegramGroupChatId(TelegramResponse response, String target) {
      if (response == null) {
         return null;
      } else if ("points".equals(target)) {
         return response.getBotChatGroupId();
      } else {
         return StringUtils.hasText(response.getLibraryNotifyChatId()) ? response.getLibraryNotifyChatId() : response.getBotChatGroupId();
      }
   }

   private Map<String, String> buildTextVariables(LocalDate date, List<TmdbDailyRelease> rows) {
      Map<String, String> variables = new LinkedHashMap<>();
      int movieCount = (int)rows.stream().filter(row -> "movie".equals(row.getMediaType())).count();
      int tvCount = (int)rows.stream().filter(row -> "tv".equals(row.getMediaType())).count();
      variables.put("date", date.format(DATE_FORMATTER));
      variables.put("summary", "\ud83c\udf7f 电影 " + movieCount + " 部 / \ud83d\udcfa 剧集 " + tvCount + " 部");
      variables.put("list", this.formatTextList(rows));
      return variables;
   }

   private String buildTelegramCaption(LocalDate date, List<TmdbDailyRelease> rows) {
      int movieCount = (int)rows.stream().filter(row -> "movie".equals(row.getMediaType())).count();
      int tvCount = (int)rows.stream().filter(row -> "tv".equals(row.getMediaType())).count();
      return "\ud83c\udfac 追新日历 " + date.format(DATE_FORMATTER) + "\n\ud83c\udf7f 电影 " + movieCount + " / \ud83d\udcfa 剧集 " + tvCount;
   }

   private String formatTextList(List<TmdbDailyRelease> rows) {
      if (rows != null && !rows.isEmpty()) {
         StringBuilder builder = new StringBuilder();

         for (int i = 0; i < rows.size(); i++) {
            TmdbDailyRelease row = rows.get(i);
            if (i > 0) {
               builder.append('\n');
            }

            builder.append(i + 1).append(". ");
            if ("movie".equals(row.getMediaType())) {
               builder.append("电影：").append(this.titleWithYear(row));
            } else {
               builder.append("剧集：").append(this.titleWithYear(row));
               if (StringUtils.hasText(row.getEpisodeDisplay())) {
                  builder.append(" ").append(row.getEpisodeDisplay());
               }
            }
         }

         return builder.toString();
      } else {
         return "今日暂无上映/播出数据。";
      }
   }

   private String titleWithYear(TmdbDailyRelease row) {
      String title = this.defaultString(row.getTitle(), "未命名");
      return row.getYear() != null && !title.contains(String.valueOf(row.getYear())) ? title + " (" + row.getYear() + ")" : title;
   }

   private void updateNotifyStatus(
      LocalDate date, boolean telegramGroupSent, boolean telegramBotSent, boolean wechatSent, boolean wechatBotSent, Date notifyTime, String error
   ) {
      this.lambdaUpdate()
         .eq(TmdbDailyRelease::getPublishDate, this.toSqlDate(date))
         .set(TmdbDailyRelease::getTelegramGroupSent, Integer.valueOf(telegramGroupSent ? 1 : 0))
         .set(TmdbDailyRelease::getTelegramBotSent, Integer.valueOf(telegramBotSent ? 1 : 0))
         .set(TmdbDailyRelease::getWechatSent, Integer.valueOf(wechatSent ? 1 : 0))
         .set(TmdbDailyRelease::getWechatBotSent, Integer.valueOf(wechatBotSent ? 1 : 0))
         .set(TmdbDailyRelease::getLastNotifyTime, notifyTime)
         .set(TmdbDailyRelease::getLastError, this.truncate(error, 1000))
         .update();
   }

   private void updateNotifyError(LocalDate date, String error) {
      this.lambdaUpdate().eq(TmdbDailyRelease::getPublishDate, this.toSqlDate(date)).set(TmdbDailyRelease::getLastError, this.truncate(error, 1000)).update();
   }

   private TmdbDailyReleaseResponse toResponse(TmdbDailyRelease row) {
      TmdbDailyReleaseResponse response = new TmdbDailyReleaseResponse();
      response.setId(row.getId());
      response.setPublishDate(row.getPublishDate());
      response.setMediaType(row.getMediaType());
      response.setMediaTypeLabel("movie".equals(row.getMediaType()) ? "电影" : "剧集");
      response.setTmdbId(row.getTmdbId());
      response.setTitle(row.getTitle());
      response.setOriginalTitle(row.getOriginalTitle());
      response.setYear(row.getYear());
      response.setOverview(row.getOverview());
      response.setPosterPath(row.getPosterPath());
      response.setBackdropPath(row.getBackdropPath());
      response.setTmdbUrl(row.getTmdbUrl());
      response.setReleaseDate(row.getReleaseDate());
      response.setFirstAirDate(row.getFirstAirDate());
      response.setSeasonNumber(row.getSeasonNumber());
      response.setEpisodeStart(row.getEpisodeStart());
      response.setEpisodeEnd(row.getEpisodeEnd());
      response.setEpisodeDisplay(row.getEpisodeDisplay());
      response.setVoteAverage(row.getVoteAverage());
      response.setVoteCount(row.getVoteCount());
      response.setPopularity(row.getPopularity());
      response.setOriginCountry(row.getOriginCountry());
      response.setOriginalLanguage(row.getOriginalLanguage());
      response.setRankNo(row.getRankNo());
      response.setTelegramGroupSent(row.getTelegramGroupSent());
      response.setTelegramBotSent(row.getTelegramBotSent());
      response.setWechatSent(row.getWechatSent());
      response.setWechatBotSent(row.getWechatBotSent());
      response.setLastNotifyTime(row.getLastNotifyTime());
      response.setLastError(row.getLastError());
      return response;
   }

   private TmdbDailyReleaseConfigRequest loadConfig() {
      TmdbDailyReleaseConfigRequest defaults = this.normalizeConfig(new TmdbDailyReleaseConfigRequest());
      String value = this.configCacheLoaderUtils.getConfigValue("tmdb_daily_release_config");
      if (!StringUtils.hasText(value)) {
         SystemConfig dbConfig = this.systemConfigService.lambdaQuery().eq(SystemConfig::getConfigKey, "tmdb_daily_release_config").one();
         value = dbConfig == null ? null : dbConfig.getConfigValue();
      }

      if (!StringUtils.hasText(value)) {
         return defaults;
      } else {
         try {
            TmdbDailyReleaseConfigRequest parsed = JSONObject.parseObject(value, TmdbDailyReleaseConfigRequest.class);
            return this.normalizeConfig(parsed);
         } catch (Exception var4) {
            log.warn("追新配置解析失败，使用默认配置: {}", var4.getMessage());
            return defaults;
         }
      }
   }

   private TmdbDailyReleaseConfigRequest normalizeConfig(TmdbDailyReleaseConfigRequest config) {
      TmdbDailyReleaseConfigRequest target = config == null ? new TmdbDailyReleaseConfigRequest() : config;
      target.setEnabled(Boolean.TRUE.equals(target.getEnabled()));
      target.setIncludeMovies(!Boolean.FALSE.equals(target.getIncludeMovies()));
      target.setIncludeTv(!Boolean.FALSE.equals(target.getIncludeTv()));
      target.setLimit(Math.max(1, Math.min(15, target.getLimit() == null ? 15 : target.getLimit())));
      target.setRegion(String.join(",", this.splitConfigList(target.getRegion(), "CN,US,GB", ",", "，", "|")));
      target.setTimezone(this.defaultString(target.getTimezone(), "Asia/Shanghai"));
      target.setLanguage(this.defaultString(target.getLanguage(), "zh-CN"));
      target.setReleaseTypes(this.defaultString(target.getReleaseTypes(), "2|3"));
      target.setTelegramGroupTarget(this.normalizeTelegramGroupTarget(target.getTelegramGroupTarget()));
      target.setTelegramGroupEnabled(Boolean.TRUE.equals(target.getTelegramGroupEnabled()));
      target.setTelegramBotEnabled(Boolean.TRUE.equals(target.getTelegramBotEnabled()));
      target.setWechatEnabled(Boolean.TRUE.equals(target.getWechatEnabled()));
      target.setWechatBotEnabled(Boolean.TRUE.equals(target.getWechatBotEnabled()));
      target.setSendWhenEmpty(Boolean.TRUE.equals(target.getSendWhenEmpty()));
      target.setQueryDelayMillis(Math.max(0, Math.min(5000, target.getQueryDelayMillis() == null ? 500 : target.getQueryDelayMillis())));
      target.setMaxPages(Math.max(1, Math.min(10, target.getMaxPages() == null ? 3 : target.getMaxPages())));
      return target;
   }

   private String normalizeTelegramGroupTarget(String target) {
      return "points".equalsIgnoreCase(this.defaultString(target, "")) ? "points" : "channel";
   }

   private int safeLimit(TmdbDailyReleaseConfigRequest config) {
      return Math.max(1, Math.min(15, config.getLimit() == null ? 15 : config.getLimit()));
   }

   private int safeMaxPages(TmdbDailyReleaseConfigRequest config) {
      return Math.max(1, Math.min(10, config.getMaxPages() == null ? 3 : config.getMaxPages()));
   }

   private List<String> splitConfigList(String value, String fallback, String... separators) {
      String source = StringUtils.hasText(value) ? value : fallback;
      if (!StringUtils.hasText(source)) {
         return List.of();
      } else {
         String normalized = source.trim();

         for (String separator : separators) {
            normalized = normalized.replace(separator, ",");
         }

         return Arrays.stream(normalized.split(","))
            .map(String::trim)
            .filter(StringUtils::hasText)
            .map(item -> item.toUpperCase(Locale.ROOT))
            .distinct()
            .toList();
      }
   }

   private ZoneId resolveZone(TmdbDailyReleaseConfigRequest config) {
      try {
         return ZoneId.of(this.defaultString(config.getTimezone(), "Asia/Shanghai"));
      } catch (DateTimeException var3) {
         return ZoneId.of("Asia/Shanghai");
      }
   }

   private void sleepQuietly(TmdbDailyReleaseConfigRequest config) {
      int delay = config.getQueryDelayMillis() == null ? 0 : config.getQueryDelayMillis();
      if (delay > 0) {
         try {
            Thread.sleep((long)delay);
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
         }
      }
   }

   private java.sql.Date toSqlDate(LocalDate date) {
      return date == null ? null : java.sql.Date.valueOf(date);
   }

   private LocalDate parseLocalDate(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         try {
            return LocalDate.parse(value.trim(), DATE_FORMATTER);
         } catch (DateTimeParseException var3) {
            return null;
         }
      }
   }

   private LocalDate toLocalDate(Date date, ZoneId zone) {
      if (date == null) {
         return null;
      } else {
         return date instanceof java.sql.Date sqlDate ? sqlDate.toLocalDate() : date.toInstant().atZone(zone).toLocalDate();
      }
   }

   private Integer resolveYear(String date) {
      LocalDate parsed = this.parseLocalDate(date);
      return parsed == null ? null : parsed.getYear();
   }

   private String fullImagePath(String path) {
      if (!StringUtils.hasText(path)) {
         return null;
      } else {
         String normalized = path.trim();
         if (!normalized.startsWith("http://") && !normalized.startsWith("https://")) {
            String base = StringUtils.hasText(this.tmdbImageUrl) ? this.tmdbImageUrl : "https://image.tmdb.org/t/p/original";
            return base.replaceAll("/+$", "") + (normalized.startsWith("/") ? normalized : "/" + normalized);
         } else {
            return normalized;
         }
      }
   }

   private BigDecimal decimal(Double value) {
      return value == null ? null : BigDecimal.valueOf(value);
   }

   private String joinArray(JSONArray array) {
      return array != null && !array.isEmpty() ? array.stream().map(String::valueOf).collect(Collectors.joining(",")) : null;
   }

   private String firstText(String... values) {
      if (values == null) {
         return null;
      } else {
         for (String value : values) {
            if (StringUtils.hasText(value)) {
               return value.trim();
            }
         }

         return null;
      }
   }

   private String defaultString(String value, String fallback) {
      return StringUtils.hasText(value) ? value.trim() : fallback;
   }

   private boolean one(Integer value) {
      return value != null && value == 1;
   }

   private String truncate(String value, int maxLength) {
      return value != null && value.length() > maxLength ? value.substring(0, maxLength) : value;
   }

   private void fillDefaultNotifyState(TmdbDailyRelease row) {
      row.setTelegramGroupSent(0);
      row.setTelegramBotSent(0);
      row.setWechatSent(0);
      row.setWechatBotSent(0);
      row.setLastError(null);
   }

   @Generated
   public TmdbDailyReleaseServiceImpl(
      final TmdbClient tmdbClient,
      final SystemConfigService systemConfigService,
      final ConfigCacheLoaderUtils configCacheLoaderUtils,
      final NotifyUtils notifyUtils,
      final TelegramClientUtils telegramClientUtils,
      final TelegramService telegramService,
      final TransactionTemplate transactionTemplate
   ) {
      this.tmdbClient = tmdbClient;
      this.systemConfigService = systemConfigService;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
      this.notifyUtils = notifyUtils;
      this.telegramClientUtils = telegramClientUtils;
      this.telegramService = telegramService;
      this.transactionTemplate = transactionTemplate;
   }
}
