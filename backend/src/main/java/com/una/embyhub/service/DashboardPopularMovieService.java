package com.una.embyhub.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.foam.client.EmbyClient;
import com.una.embyhub.model.dto.response.dashboard.DashboardPopularMovieResponse;
import com.una.embyhub.model.dto.response.playrecords.PlayCountSummary;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DashboardPopularMovieService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DashboardPopularMovieService.class);
   private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
   private static final int DISPLAY_LIMIT = 8;
   private static final int SUMMARY_SCAN_LIMIT = 80;
   private static final String MEDIA_ITEM_TYPES = "Movie,Series,Episode";
   private static final String CACHE_PREFIX = "dashboard:popular-movies:v3:";
   private static final String META_CACHE_PREFIX = "dashboard:popular-movie:meta:v2:";
   private final PlayRecordsService playRecordsService;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final EmbyClient embyClient;
   private final StringRedisTemplate stringRedisTemplate;

   public DashboardPopularMovieService(
      PlayRecordsService playRecordsService, EmbyInfoCacheManagerUtils embyInfoCacheManager, EmbyClient embyClient, StringRedisTemplate stringRedisTemplate
   ) {
      this.playRecordsService = playRecordsService;
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.embyClient = embyClient;
      this.stringRedisTemplate = stringRedisTemplate;
   }

   public List<DashboardPopularMovieResponse> getPopularMovies(Long embyInfoId) {
      String key = this.cacheKey(embyInfoId);
      String cached = this.stringRedisTemplate.opsForValue().get(key);
      if (StringUtils.hasText(cached)) {
         try {
            List<DashboardPopularMovieResponse> rows = JSON.parseArray(cached, DashboardPopularMovieResponse.class);
            return rows == null ? List.of() : rows;
         } catch (Exception var5) {
            log.warn("首页热门影片缓存解析失败，删除旧缓存: {}", key, var5);
            this.stringRedisTemplate.delete(key);
         }
      }

      return this.refreshPopularMovies(embyInfoId);
   }

   public synchronized void refreshAllPopularMovies() {
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> configs = this.embyInfoCacheManager.getEnabledConfigs();
      if (configs != null && !configs.isEmpty()) {
         for (EmbyInfoCacheManagerUtils.EmbyServerConfig config : configs) {
            if (config != null && config.id() != null) {
               try {
                  this.refreshPopularMovies(config.id());
               } catch (Exception var5) {
                  log.warn("首页热门影片缓存刷新失败，serverId={}", config.id(), var5);
               }
            }
         }
      } else {
         log.info("首页热门影片缓存刷新跳过：暂无启用服务器");
      }
   }

   public synchronized List<DashboardPopularMovieResponse> refreshPopularMovies(Long embyInfoId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig config = embyInfoId == null
         ? this.embyInfoCacheManager.getRequiredConfig()
         : this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      Long serverId = config.id();
      DashboardPopularMovieService.DateRange range = this.buildRollingRange();
      List<PlayCountSummary> summaries = this.playRecordsService.summaryByRange(range.start(), range.end(), serverId);
      Map<String, DashboardPopularMovieService.PopularMovieAccumulator> movies = new LinkedHashMap<>();
      List<PlayCountSummary> safeSummaries = summaries == null ? List.of() : summaries;
      safeSummaries
         .stream()
         .filter(summary -> summary != null && StringUtils.hasText(summary.getContent()))
         .limit(80L)
         .forEach(summary -> this.addSummary(config, movies, summary));
      List<DashboardPopularMovieResponse> rows = movies.values()
         .stream()
         .sorted(Comparator.comparingLong(DashboardPopularMovieService.PopularMovieAccumulator::getPlayCount).reversed())
         .limit(8L)
         .map(DashboardPopularMovieService.PopularMovieAccumulator::toResponse)
         .toList();
      this.stringRedisTemplate.opsForValue().set(this.cacheKey(serverId), JSON.toJSONString(rows), 36L, TimeUnit.HOURS);
      log.info("首页热门影片缓存刷新完成，serverId={}, count={}", serverId, rows.size());
      return rows;
   }

   private void addSummary(
      EmbyInfoCacheManagerUtils.EmbyServerConfig config, Map<String, DashboardPopularMovieService.PopularMovieAccumulator> movies, PlayCountSummary summary
   ) {
      DashboardPopularMovieService.MovieMeta meta = this.resolveMovieMeta(config, summary.getContent());
      if (meta != null && meta.isFound()) {
         String key = StringUtils.hasText(meta.getItemId())
            ? Objects.toString(meta.getItemType(), "Media") + ":" + meta.getItemId()
            : this.normalizeTitle(meta.getTitle()) + ":" + Objects.toString(meta.getYear(), "");
         DashboardPopularMovieService.PopularMovieAccumulator accumulator = movies.computeIfAbsent(
            key, ignored -> new DashboardPopularMovieService.PopularMovieAccumulator(meta)
         );
         accumulator.increment(summary.getPlayCount());
      }
   }

   private DashboardPopularMovieService.MovieMeta resolveMovieMeta(EmbyInfoCacheManagerUtils.EmbyServerConfig config, String content) {
      String key = this.metaCacheKey(config.id(), content);
      String cached = this.stringRedisTemplate.opsForValue().get(key);
      if (StringUtils.hasText(cached)) {
         try {
            return JSON.parseObject(cached, DashboardPopularMovieService.MovieMeta.class);
         } catch (Exception var9) {
            log.debug("首页热门影片元数据缓存解析失败，删除旧缓存: {}", key, var9);
            this.stringRedisTemplate.delete(key);
         }
      }

      DashboardPopularMovieService.MovieMeta meta = this.fetchMovieMeta(config, content);
      if (meta != null) {
         long ttl = meta.isFound() ? 7L : 12L;
         TimeUnit unit = meta.isFound() ? TimeUnit.DAYS : TimeUnit.HOURS;
         this.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(meta), ttl, unit);
      }

      return meta;
   }

   private DashboardPopularMovieService.MovieMeta fetchMovieMeta(EmbyInfoCacheManagerUtils.EmbyServerConfig config, String content) {
      DashboardPopularMovieService.MovieMeta empty = DashboardPopularMovieService.MovieMeta.notFound();

      try {
         JSONObject result = this.embyClient.searchLibraryForSystem(config, content, 8, "Movie,Series,Episode");
         JSONArray items = result == null ? null : result.getJSONArray("Items");
         if (items != null && !items.isEmpty()) {
            JSONObject item = this.chooseMediaItem(items, content);
            if (item == null) {
               return empty;
            } else {
               DashboardPopularMovieService.MovieMeta meta = new DashboardPopularMovieService.MovieMeta();
               meta.setFound(true);
               meta.setEmbyInfoId(config.id());
               meta.setItemType(this.resolveItemType(item));
               meta.setItemId(this.resolveItemId(item));
               meta.setTitle(this.resolveTitle(item, content));
               meta.setYear(this.resolveYear(item));
               meta.setImageTag(this.resolveImageTag(item));
               return meta;
            }
         } else {
            return empty;
         }
      } catch (Exception var8) {
         log.debug("首页热门影片元数据拉取失败，serverId={}, content={}", config.id(), content, var8);
         return null;
      }
   }

   private JSONObject chooseMediaItem(JSONArray items, String content) {
      String normalizedContent = this.normalizeTitle(content);
      JSONObject firstSeriesOrMovie = null;
      JSONObject firstEpisode = null;

      for (int i = 0; i < items.size(); i++) {
         JSONObject item = items.getJSONObject(i);
         if (item != null) {
            String type = item.getString("Type");
            if (this.isSeriesOrMovie(type) && firstSeriesOrMovie == null) {
               firstSeriesOrMovie = item;
            }

            if ("Episode".equalsIgnoreCase(type) && firstEpisode == null) {
               firstEpisode = item;
            }

            if (this.isSeriesOrMovie(type) && this.normalizeTitle(item.getString("Name")).equals(normalizedContent)) {
               return item;
            }
         }
      }

      for (int ix = 0; ix < items.size(); ix++) {
         JSONObject item = items.getJSONObject(ix);
         if (item != null
            && "Episode".equalsIgnoreCase(item.getString("Type"))
            && (
               this.normalizeTitle(item.getString("SeriesName")).equals(normalizedContent)
                  || this.normalizeTitle(item.getString("Name")).equals(normalizedContent)
            )) {
            return item;
         }
      }

      return firstSeriesOrMovie != null ? firstSeriesOrMovie : firstEpisode;
   }

   private boolean isSeriesOrMovie(String type) {
      return "Movie".equalsIgnoreCase(type) || "Series".equalsIgnoreCase(type);
   }

   private String resolveItemType(JSONObject item) {
      String type = item.getString("Type");
      return "Episode".equalsIgnoreCase(type) ? "Series" : type;
   }

   private String resolveItemId(JSONObject item) {
      if ("Episode".equalsIgnoreCase(item.getString("Type"))) {
         String seriesId = item.getString("SeriesId");
         if (StringUtils.hasText(seriesId)) {
            return seriesId;
         }
      }

      return item.getString("Id");
   }

   private String resolveTitle(JSONObject item, String fallback) {
      if ("Episode".equalsIgnoreCase(item.getString("Type"))) {
         String seriesName = item.getString("SeriesName");
         if (StringUtils.hasText(seriesName)) {
            return seriesName;
         }
      }

      String name = item.getString("Name");
      return StringUtils.hasText(name) ? name : fallback;
   }

   private String resolveImageTag(JSONObject item) {
      if ("Episode".equalsIgnoreCase(item.getString("Type"))) {
         String seriesImageTag = item.getString("SeriesPrimaryImageTag");
         if (StringUtils.hasText(seriesImageTag)) {
            return seriesImageTag;
         }
      }

      String tag = item.getString("PrimaryImageTag");
      if (StringUtils.hasText(tag)) {
         return tag;
      } else {
         JSONObject imageTags = item.getJSONObject("ImageTags");
         return imageTags == null ? null : imageTags.getString("Primary");
      }
   }

   private String resolveYear(JSONObject item) {
      Object productionYear = item.get("ProductionYear");
      if (productionYear != null && StringUtils.hasText(String.valueOf(productionYear))) {
         return String.valueOf(productionYear);
      } else {
         String premiereDate = item.getString("PremiereDate");
         return StringUtils.hasText(premiereDate) && premiereDate.length() >= 4 ? premiereDate.substring(0, 4) : null;
      }
   }

   private String normalizeTitle(String value) {
      return !StringUtils.hasText(value) ? "" : value.replaceAll("[（(]\\d{4}[）)]", "").replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
   }

   private DashboardPopularMovieService.DateRange buildRollingRange() {
      LocalDateTime end = LocalDateTime.now(ZONE_ID);
      LocalDateTime start = end.minusDays(7L);
      return new DashboardPopularMovieService.DateRange(Date.from(start.atZone(ZONE_ID).toInstant()), Date.from(end.atZone(ZONE_ID).toInstant()));
   }

   private String cacheKey(Long embyInfoId) {
      return "dashboard:popular-movies:v3:" + (embyInfoId == null ? "default" : embyInfoId);
   }

   private String metaCacheKey(Long embyInfoId, String content) {
      return "dashboard:popular-movie:meta:v2:" + (embyInfoId == null ? "default" : embyInfoId) + ":" + DigestUtil.sha256Hex(content);
   }

   private static record DateRange(Date start, Date end) {
   }

   private static class MovieMeta implements Serializable {
      private static final long serialVersionUID = 1L;
      private boolean found;
      private String itemId;
      private Long embyInfoId;
      private String itemType;
      private String title;
      private String year;
      private String imageTag;

      private static DashboardPopularMovieService.MovieMeta notFound() {
         return new DashboardPopularMovieService.MovieMeta();
      }

      @Generated
      public MovieMeta() {
      }

      @Generated
      public boolean isFound() {
         return this.found;
      }

      @Generated
      public String getItemId() {
         return this.itemId;
      }

      @Generated
      public Long getEmbyInfoId() {
         return this.embyInfoId;
      }

      @Generated
      public String getItemType() {
         return this.itemType;
      }

      @Generated
      public String getTitle() {
         return this.title;
      }

      @Generated
      public String getYear() {
         return this.year;
      }

      @Generated
      public String getImageTag() {
         return this.imageTag;
      }

      @Generated
      public void setFound(final boolean found) {
         this.found = found;
      }

      @Generated
      public void setItemId(final String itemId) {
         this.itemId = itemId;
      }

      @Generated
      public void setEmbyInfoId(final Long embyInfoId) {
         this.embyInfoId = embyInfoId;
      }

      @Generated
      public void setItemType(final String itemType) {
         this.itemType = itemType;
      }

      @Generated
      public void setTitle(final String title) {
         this.title = title;
      }

      @Generated
      public void setYear(final String year) {
         this.year = year;
      }

      @Generated
      public void setImageTag(final String imageTag) {
         this.imageTag = imageTag;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof DashboardPopularMovieService.MovieMeta other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.isFound() != other.isFound()) {
            return false;
         } else {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$itemId = this.getItemId();
               Object other$itemId = other.getItemId();
               if (this$itemId == null ? other$itemId == null : this$itemId.equals(other$itemId)) {
                  Object this$itemType = this.getItemType();
                  Object other$itemType = other.getItemType();
                  if (this$itemType == null ? other$itemType == null : this$itemType.equals(other$itemType)) {
                     Object this$title = this.getTitle();
                     Object other$title = other.getTitle();
                     if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                        Object this$year = this.getYear();
                        Object other$year = other.getYear();
                        if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                           Object this$imageTag = this.getImageTag();
                           Object other$imageTag = other.getImageTag();
                           return this$imageTag == null ? other$imageTag == null : this$imageTag.equals(other$imageTag);
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
         return other instanceof DashboardPopularMovieService.MovieMeta;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + (this.isFound() ? 79 : 97);
         Object $embyInfoId = this.getEmbyInfoId();
         result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
         Object $itemId = this.getItemId();
         result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
         Object $itemType = this.getItemType();
         result = result * 59 + ($itemType == null ? 43 : $itemType.hashCode());
         Object $title = this.getTitle();
         result = result * 59 + ($title == null ? 43 : $title.hashCode());
         Object $year = this.getYear();
         result = result * 59 + ($year == null ? 43 : $year.hashCode());
         Object $imageTag = this.getImageTag();
         return result * 59 + ($imageTag == null ? 43 : $imageTag.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "DashboardPopularMovieService.MovieMeta(found="
            + this.isFound()
            + ", itemId="
            + this.getItemId()
            + ", embyInfoId="
            + this.getEmbyInfoId()
            + ", itemType="
            + this.getItemType()
            + ", title="
            + this.getTitle()
            + ", year="
            + this.getYear()
            + ", imageTag="
            + this.getImageTag()
            + ")";
      }
   }

   private static class PopularMovieAccumulator {
      private final DashboardPopularMovieService.MovieMeta meta;
      private long playCount;

      private PopularMovieAccumulator(DashboardPopularMovieService.MovieMeta meta) {
         this.meta = meta;
      }

      private long getPlayCount() {
         return this.playCount;
      }

      private void increment(Long count) {
         if (count != null && count > 0L) {
            this.playCount = this.playCount + count;
         }
      }

      private DashboardPopularMovieResponse toResponse() {
         DashboardPopularMovieResponse response = new DashboardPopularMovieResponse();
         response.setItemId(this.meta.getItemId());
         response.setEmbyInfoId(this.meta.getEmbyInfoId());
         response.setTitle(this.meta.getTitle());
         response.setYear(this.meta.getYear());
         response.setImageTag(this.meta.getImageTag());
         response.setPlayCount(this.playCount);
         return response;
      }
   }
}
