package com.una.embyhub.foam.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.foam.client.TmdbClient;
import com.una.embyhub.model.entity.PlayRecords;
import com.una.embyhub.service.EmbyApiClientService;
import com.una.embyhub.service.PlayRecordsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class TmdbRecommendationService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbRecommendationService.class);
   private static final int DEFAULT_PAGE_SIZE = 20;
   private static final int MAX_PAGE_SIZE = 50;
   private static final int HISTORY_LIMIT = 10;
   private static final int RECOMMENDATIONS_PER_ITEM = 5;
   private static final int MAX_RECOMMENDATION_LIMIT = 100;
   private static final String RECOMMENDATION_CACHE_PREFIX = "tmdb:recommend:v2:";
   private static final long CACHE_TTL_MINUTES = 30L;
   private final TmdbClient tmdbClient;
   private final PlayRecordsService playRecordsService;
   private final EmbyApiClientService embyApiClientService;
   private final StringRedisTemplate stringRedisTemplate;

   public TmdbRecommendationService(
      TmdbClient tmdbClient, PlayRecordsService playRecordsService, EmbyApiClientService embyApiClientService, StringRedisTemplate stringRedisTemplate
   ) {
      this.tmdbClient = tmdbClient;
      this.playRecordsService = playRecordsService;
      this.embyApiClientService = embyApiClientService;
      this.stringRedisTemplate = stringRedisTemplate;
   }

   public JSONObject recommend(String userName, Integer page, Integer pageSize, Integer limit) {
      int resolvedPage = page != null && page >= 1 ? page : 1;
      Integer resolvedPageSizeParam = pageSize != null ? pageSize : limit;
      int resolvedPageSize = resolvedPageSizeParam == null ? 20 : resolvedPageSizeParam;
      resolvedPageSize = Math.min(Math.max(resolvedPageSize, 1), 50);
      String cacheKey = "tmdb:recommend:v2:" + (userName == null ? "guest" : userName) + ":" + resolvedPage + ":" + resolvedPageSize;
      JSONObject cached = this.getCachedJson(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         int targetSize = resolvedPage * resolvedPageSize;
         if ((resolvedPage - 1) * resolvedPageSize >= 100) {
            return this.buildEmptyResponse(resolvedPage, resolvedPageSize, 100);
         } else {
            List<String> historyTitles = this.playRecordsService
               .findRecentPlays(userName, 10)
               .stream()
               .map(PlayRecords::getContent)
               .filter(StringUtils::hasText)
               .distinct()
               .limit(10L)
               .collect(Collectors.toList());
            JSONObject result;
            if (CollectionUtils.isEmpty(historyTitles)) {
               log.info("用户 {} 无播放记录，返回热门内容", userName);
               result = this.buildResponseFromTrending(resolvedPage, resolvedPageSize, "无播放历史，按 TMDB 趋势推荐");
            } else {
               List<TmdbRecommendationService.MatchedItem> matchedItems = new ArrayList<>();
               Set<Long> watchedIds = new HashSet<>();

               for (String title : historyTitles) {
                  TmdbRecommendationService.MatchedItem matched = this.searchAndMatch(title);
                  if (matched != null) {
                     matchedItems.add(matched);
                     watchedIds.add(matched.tmdbId);
                  }
               }

               if (CollectionUtils.isEmpty(matchedItems)) {
                  log.info("用户 {} 的播放记录未能匹配到 TMDB 内容，返回热门内容", userName);
                  result = this.buildResponseFromTrending(resolvedPage, resolvedPageSize, "未匹配到播放历史，按 TMDB 趋势推荐");
               } else {
                  JSONArray allRecommendations = new JSONArray();
                  Set<Long> addedIds = new HashSet<>(watchedIds);

                  for (TmdbRecommendationService.MatchedItem item : matchedItems) {
                     JSONArray recommendations = this.fetchRecommendationsForItem(item);
                     if (recommendations != null) {
                        int addedFromThisItem = 0;
                        JSONArray toEnrich = new JSONArray();

                        for (int i = 0; i < recommendations.size() && addedFromThisItem < 5; i++) {
                           JSONObject rec = recommendations.getJSONObject(i);
                           Long recId = rec.getLong("id");
                           if (recId != null && addedIds.add(recId)) {
                              JSONObject enriched = JSON.parseObject(rec.toJSONString());
                              enriched.put("recommendation_reason", "因为你看过「" + item.title + "」");
                              enriched.put("source_title", item.title);
                              toEnrich.add(enriched);
                              addedFromThisItem++;
                           }
                        }

                        this.enrichItemsWithLibraryStatus(toEnrich);

                        for (int ix = 0; ix < toEnrich.size(); ix++) {
                           allRecommendations.add(toEnrich.getJSONObject(ix));
                        }

                        if (allRecommendations.size() >= targetSize) {
                           break;
                        }
                     }
                  }

                  int trendingPage = 1;

                  while (allRecommendations.size() < targetSize) {
                     JSONObject trending = this.tmdbClient.fetchTrending("day", trendingPage);
                     JSONArray trendingResults = trending.getJSONArray("results");
                     if (CollectionUtils.isEmpty(trendingResults)) {
                        break;
                     }

                     boolean addedAnyFromPage = false;

                     for (int ix = 0; ix < trendingResults.size() && allRecommendations.size() < targetSize; ix++) {
                        JSONObject itemx = trendingResults.getJSONObject(ix);
                        Long itemId = itemx.getLong("id");
                        if (itemId != null && addedIds.add(itemId)) {
                           JSONObject enriched = JSON.parseObject(itemx.toJSONString());
                           enriched.put("recommendation_reason", "热门趋势内容");
                           Object inLibrary = itemx.get("inLibrary");
                           enriched.put("inLibrary", inLibrary != null ? inLibrary : false);
                           allRecommendations.add(enriched);
                           addedAnyFromPage = true;
                        }
                     }

                     if (++trendingPage > 10) {
                        break;
                     }
                  }

                  result = this.paginateResponse(allRecommendations, resolvedPage, resolvedPageSize);
               }
            }

            if (result != null && result.containsKey("results")) {
               this.cacheJson(cacheKey, result, 30L, TimeUnit.MINUTES);
            }

            return result;
         }
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
            log.warn("TMDB recommendation cache parse failed, delete stale key: {}", cacheKey, var4);
            this.stringRedisTemplate.delete(cacheKey);
            return null;
         }
      }
   }

   private void cacheJson(String cacheKey, JSONObject value, long ttl, TimeUnit timeUnit) {
      this.stringRedisTemplate.opsForValue().set(cacheKey, value.toJSONString(), ttl, timeUnit);
   }

   private TmdbRecommendationService.MatchedItem searchAndMatch(String title) {
      try {
         Map<String, Object> params = new HashMap<>();
         params.put("query", title);
         params.put("include_adult", false);
         params.put("page", 1);
         JSONObject searchResult = this.tmdbClient.request("/search/multi", params);
         JSONArray results = searchResult.getJSONArray("results");
         if (CollectionUtils.isEmpty(results)) {
            return null;
         } else {
            JSONObject first = results.getJSONObject(0);
            Long id = first.getLong("id");
            String mediaType = first.getString("media_type");
            String matchedTitle = first.getString("title");
            if (!StringUtils.hasText(matchedTitle)) {
               matchedTitle = first.getString("name");
            }

            if (id == null || !StringUtils.hasText(mediaType)) {
               return null;
            } else {
               return !"movie".equals(mediaType) && !"tv".equals(mediaType)
                  ? null
                  : new TmdbRecommendationService.MatchedItem(id, mediaType, matchedTitle != null ? matchedTitle : title);
            }
         }
      } catch (Exception var9) {
         log.warn("搜索标题失败: {}", title, var9);
         return null;
      }
   }

   private JSONArray fetchRecommendationsForItem(TmdbRecommendationService.MatchedItem item) {
      try {
         JSONObject response;
         if ("movie".equals(item.mediaType)) {
            response = this.tmdbClient.fetchMovieRecommendations(item.tmdbId);
         } else {
            response = this.tmdbClient.fetchTvRecommendations(item.tmdbId);
         }

         return response == null ? null : response.getJSONArray("results");
      } catch (Exception var3) {
         log.warn("获取推荐失败: {} ({})", item.title, item.tmdbId, var3);
         return null;
      }
   }

   private JSONObject buildResponseFromTrending(int page, int pageSize, String reason) {
      JSONArray allTrendings = new JSONArray();
      int targetSize = page * pageSize;
      int trendingPage = 1;

      while (allTrendings.size() < targetSize) {
         JSONObject trending = this.tmdbClient.fetchTrending("day", trendingPage);
         JSONArray results = trending.getJSONArray("results");
         if (CollectionUtils.isEmpty(results)) {
            break;
         }

         for (int i = 0; i < results.size(); i++) {
            JSONObject item = results.getJSONObject(i);
            JSONObject enriched = JSON.parseObject(item.toJSONString());
            enriched.put("recommendation_reason", reason);
            Object inLibrary = item.get("inLibrary");
            enriched.put("inLibrary", inLibrary != null ? inLibrary : false);
            allTrendings.add(enriched);
         }

         if (++trendingPage > 10) {
            break;
         }
      }

      return this.paginateResponse(allTrendings, page, pageSize);
   }

   private void enrichItemsWithLibraryStatus(JSONArray items) {
      if (!CollectionUtils.isEmpty(items)) {
         List<CompletableFuture<Void>> futures = new ArrayList<>(items.size());

         try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < items.size(); i++) {
               JSONObject item = items.getJSONObject(i);
               Long tmdbId = item.getLong("id");
               if (tmdbId == null) {
                  item.put("inLibrary", Boolean.valueOf(false));
               } else {
                  futures.add(CompletableFuture.runAsync(() -> {
                     try {
                        boolean inLibrary = this.embyApiClientService.getEmbyByTmdbId(String.valueOf(tmdbId));
                        item.put("inLibrary", Boolean.valueOf(inLibrary));
                     } catch (Exception var4x) {
                        log.warn("查询 Emby 入库状态失败，tmdbId={}", tmdbId, var4x);
                        item.put("inLibrary", Boolean.valueOf(false));
                     }
                  }, executor));
               }
            }
         }

         CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
      }
   }

   private JSONObject buildEmptyResponse(int page, int pageSize, int totalResults) {
      JSONObject response = new JSONObject();
      response.put("page", Integer.valueOf(page));
      int totalPages = (int)Math.ceil((double)totalResults / (double)pageSize);
      response.put("total_pages", Integer.valueOf(totalPages));
      response.put("total_results", Integer.valueOf(totalResults));
      response.put("results", new JSONArray());
      return response;
   }

   private JSONObject paginateResponse(JSONArray allRecommendations, int page, int pageSize) {
      int totalResults = 100;
      int totalPages = (int)Math.ceil((double)totalResults / (double)pageSize);
      int startIndex = Math.max(0, (page - 1) * pageSize);
      JSONArray pagedResults = new JSONArray();
      int actualSize = allRecommendations.size();
      if (startIndex < actualSize) {
         int endIndex = Math.min(startIndex + pageSize, actualSize);

         for (int i = startIndex; i < endIndex; i++) {
            pagedResults.add(allRecommendations.get(i));
         }
      }

      JSONObject response = new JSONObject();
      response.put("page", Integer.valueOf(page));
      response.put("total_pages", Integer.valueOf(totalPages));
      response.put("total_results", Integer.valueOf(totalResults));
      response.put("results", pagedResults);
      return response;
   }

   private static class MatchedItem {
      final Long tmdbId;
      final String mediaType;
      final String title;

      MatchedItem(Long tmdbId, String mediaType, String title) {
         this.tmdbId = tmdbId;
         this.mediaType = mediaType;
         this.title = title;
      }
   }
}
