package com.una.embyhub.foam.client;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.model.dto.response.requestlist.RequestListStatusResponse;
import com.una.embyhub.service.EmbyApiClientService;
import com.una.embyhub.service.RequestListService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TmdbClient {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbClient.class);
   @Value("${tmdb.apiKey}")
   private String tmdbApiKey;
   @Value("https://api.themoviedb.org/3")
   private String tmdbApiBaseUrl;
   @Autowired
   private EmbyApiClientService embyApiClientService;
   @Autowired
   private RequestListService requlistService;
   @Autowired
   private StringRedisTemplate stringRedisTemplate;
   private static final String TMDB_CACHE_PREFIX = "tmdb:cache:v3:";
   private static final long DEFAULT_CACHE_TTL_MINUTES = 30L;
   private static final long DETAIL_CACHE_TTL_HOURS = 24L;
   private static final String TYPE_MOVIE = "movie";
   private static final String TYPE_TV = "tv";

   public JSONObject request(String path, Map<String, Object> extraParams) {
      return this.requestInternal(path, extraParams);
   }

   private JSONObject requestInternal(String path, Map<String, Object> extraParams) {
      Map<String, Object> params = new HashMap<>();
      params.put("api_key", this.tmdbApiKey);
      params.put("language", "zh-CN");
      if (extraParams != null) {
         params.putAll(extraParams);
      }

      String url = this.tmdbApiBaseUrl + path;
      String resp = HttpRequest.get(url).form(params).timeout((int)TimeUnit.SECONDS.toMillis(10L)).execute().body();
      JSONObject json = JSON.parseObject(resp);
      return json.containsKey("status_code") && json.getIntValue("status_code") != 200 ? json : json;
   }

   private void enrichResultsWithLibraryStatus(JSONObject response) {
      JSONArray results = response.getJSONArray("results");
      if (results != null && !results.isEmpty()) {
         List<JSONObject> itemsToEnrich = new ArrayList<>(results.size());
         List<CompletableFuture<Boolean>> futures = new ArrayList<>(results.size());

         try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < results.size(); i++) {
               JSONObject item = results.getJSONObject(i);
               Long tmdbId = item.getLong("id");
               if (tmdbId != null) {
                  String tmdbIdStr = String.valueOf(tmdbId);
                  itemsToEnrich.add(item);
                  futures.add(
                     CompletableFuture.<Boolean>supplyAsync(() -> this.embyApiClientService.getEmbyByTmdbId(tmdbIdStr), executor).exceptionally(ex -> {
                        log.warn("查询 Emby 入库状态失败，tmdbId={}", tmdbIdStr, ex);
                        return false;
                     })
                  );
               }
            }
         }

         for (int ix = 0; ix < itemsToEnrich.size(); ix++) {
            boolean inLibrary = futures.get(ix).join();
            itemsToEnrich.get(ix).put("inLibrary", Boolean.valueOf(inLibrary));
         }
      }
   }

   public JSONObject fetchTrending(String timeWindow, Integer page) {
      String cacheKey = "tmdb:cache:v3:trending:" + timeWindow + ":" + page;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         timeWindow = timeWindow != null && !timeWindow.isEmpty() ? timeWindow : "day";
         params.put("page", page == null ? 1 : page);
         JSONObject result = this.requestInternal("/trending/all/" + timeWindow, params);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchPopularMovies(Integer page) {
      String cacheKey = "tmdb:cache:v3:popular:movie:" + page;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("page", page == null ? 1 : page);
         JSONObject result = this.requestInternal("/movie/popular", params);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchPopularTv(Integer page) {
      String cacheKey = "tmdb:cache:v3:popular:tv:" + page;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("page", page == null ? 1 : page);
         JSONObject result = this.requestInternal("/tv/popular", params);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject searchMulti(String keyword, Integer page) {
      if (keyword != null && !keyword.trim().isEmpty()) {
         String cacheKey = "tmdb:cache:v3:search:multi:" + keyword + ":" + page;
         JSONObject cached = this.getCachedJson(cacheKey);
         if (cached != null) {
            return cached;
         } else {
            Map<String, Object> params = new HashMap<>();
            params.put("query", keyword);
            params.put("include_adult", false);
            params.put("page", page == null ? 1 : page);
            JSONObject result = this.requestInternal("/search/multi", params);
            if (result.containsKey("results")) {
               this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
            }

            return result;
         }
      } else {
         JSONObject json = new JSONObject();
         json.put("results", new Object[0]);
         json.put("total_results", Integer.valueOf(0));
         return json;
      }
   }

   public JSONObject fetchMovieDetail(Long id) {
      JSONObject result = this.fetchMovieDetailWithoutRequestStatus(id);
      this.enrichRequestStatus(result, "movie");
      return result;
   }

   public JSONObject fetchMovieDetailWithoutRequestStatus(Long id) {
      String cacheKey = "tmdb:cache:v3:movie:detail:" + id;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("append_to_response", "videos,credits,release_dates");
         params.put("include_video_language", "zh,en,null");
         JSONObject result = this.requestInternal("/movie/" + id, params);
         if (result.containsKey("id")) {
            this.cacheJson(cacheKey, result, 24L, TimeUnit.HOURS);
         }

         return result;
      }
   }

   public JSONObject fetchTvDetail(Long id) {
      JSONObject result = this.fetchTvDetailWithoutRequestStatus(id);
      this.enrichRequestStatus(result, "tv");
      return result;
   }

   public JSONObject fetchTvDetailWithoutRequestStatus(Long id) {
      String cacheKey = "tmdb:cache:v3:tv:detail:" + id;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("append_to_response", "videos,credits,content_ratings");
         params.put("include_video_language", "zh,en,null");
         JSONObject result = this.requestInternal("/tv/" + id, params);
         if (result.containsKey("id")) {
            this.cacheJson(cacheKey, result, 24L, TimeUnit.HOURS);
         }

         return result;
      }
   }

   private void enrichRequestStatus(JSONObject json, String type) {
      if (json != null && !json.isEmpty() && (!json.containsKey("status_code") || json.getIntValue("status_code") == 200)) {
         if ("movie".equals(type)) {
            this.putRequestOwnershipFlags(json, json.getString("id"), null, type);
         } else {
            if ("tv".equals(type)) {
               JSONArray seasons = json.getJSONArray("seasons");
               if (seasons != null) {
                  seasons.forEach(season -> {
                     JSONObject itemSeasons = (JSONObject)season;
                     this.putRequestOwnershipFlags(itemSeasons, itemSeasons.getString("id"), itemSeasons.getInteger("season_number"), type);
                  });
               }
            }
         }
      }
   }

   private void putRequestOwnershipFlags(JSONObject json, String tmdbId, Integer season, String type) {
      RequestListStatusResponse status = this.requlistService.getRequestStatus(tmdbId, season, type);
      json.put("isSubmitted", Boolean.valueOf(status.isSubmitted()));
      json.put("isCurrentUserSubmitted", Boolean.valueOf(status.isCurrentUserSubmitted()));
      json.put("isCurrentUserRejected", Boolean.valueOf(status.isCurrentUserRejected()));
      json.put("isPendingImport", Boolean.valueOf(status.isPendingImport()));
   }

   public JSONObject fetchTvSeasonDetail(Long id, Integer seasonNumber) {
      String cacheKey = "tmdb:cache:v3:tv:season:detail:" + id + ":" + seasonNumber;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         JSONObject result = this.requestInternal("/tv/" + id + "/season/" + seasonNumber, params);
         if (result.containsKey("id")) {
            this.cacheJson(cacheKey, result, 24L, TimeUnit.HOURS);
         }

         return result;
      }
   }

   public JSONObject fetchMovieRecommendations(Long movieId) {
      String cacheKey = "tmdb:cache:v3:movie:recommendations:" + movieId;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         JSONObject result = this.requestInternal("/movie/" + movieId + "/recommendations", new HashMap<>());
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchTvRecommendations(Long tvId) {
      String cacheKey = "tmdb:cache:v3:tv:recommendations:" + tvId;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         JSONObject result = this.requestInternal("/tv/" + tvId + "/recommendations", new HashMap<>());
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchUpcomingMovies(Integer page) {
      String cacheKey = "tmdb:cache:v3:movie:upcoming:" + page;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("page", page == null ? 1 : page);
         JSONObject result = this.requestInternal("/movie/upcoming", params);
         this.enrichResultsWithLibraryStatus(result);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchOnTheAirTv(Integer page) {
      String cacheKey = "tmdb:cache:v3:tv:on_the_air:" + page;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("page", page == null ? 1 : page);
         JSONObject result = this.requestInternal("/tv/on_the_air", params);
         this.enrichResultsWithLibraryStatus(result);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchPopularMoviesForTrailers(Integer page) {
      return this.fetchPopularMovies(page);
   }

   public JSONObject fetchPopularTvForTrailers(Integer page) {
      return this.fetchPopularTv(page);
   }

   public JSONObject fetchNowPlayingMoviesForTrailers(Integer page) {
      String cacheKey = "tmdb:cache:v3:movie:now_playing:" + page;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("page", page == null ? 1 : page);
         JSONObject result = this.requestInternal("/movie/now_playing", params);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchAiringTodayTvForTrailers(Integer page) {
      String cacheKey = "tmdb:cache:v3:tv:airing_today:" + page;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("page", page == null ? 1 : page);
         JSONObject result = this.requestInternal("/tv/airing_today", params);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
         }

         return result;
      }
   }

   public JSONObject fetchUpcomingMoviesForTrailers(Integer page) {
      return this.fetchUpcomingMovies(page);
   }

   public JSONObject fetchOnTheAirTvForTrailers(Integer page) {
      return this.fetchOnTheAirTv(page);
   }

   public JSONObject fetchMovieVideos(Long movieId) {
      String cacheKey = "tmdb:cache:v3:movie:videos:" + movieId;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("include_video_language", "zh,en,null");
         JSONObject result = this.requestInternal("/movie/" + movieId + "/videos", params);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 24L, TimeUnit.HOURS);
         }

         return result;
      }
   }

   public JSONObject fetchTvVideos(Long tvId) {
      String cacheKey = "tmdb:cache:v3:tv:videos:" + tvId;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("include_video_language", "zh,en,null");
         JSONObject result = this.requestInternal("/tv/" + tvId + "/videos", params);
         if (result.containsKey("results")) {
            this.cacheJson(cacheKey, result, 24L, TimeUnit.HOURS);
         }

         return result;
      }
   }

   private JSONObject getCachedJson(String cacheKey) {
      String cached = this.stringRedisTemplate.opsForValue().get(cacheKey);
      if (!StringUtils.hasText(cached)) {
         return null;
      } else {
         try {
            return JSON.parseObject(cached);
         } catch (Exception var4) {
            log.warn("TMDB cache parse failed, delete stale key: {}", cacheKey, var4);
            this.stringRedisTemplate.delete(cacheKey);
            return null;
         }
      }
   }

   private void cacheJson(String cacheKey, JSONObject value, long ttl, TimeUnit timeUnit) {
      this.stringRedisTemplate.opsForValue().set(cacheKey, value.toJSONString(), ttl, timeUnit);
   }
}
