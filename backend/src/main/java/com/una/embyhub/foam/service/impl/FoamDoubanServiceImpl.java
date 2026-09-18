package com.una.embyhub.foam.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.foam.response.douban.FoamDoubanDiscoverResponse;
import com.una.embyhub.foam.response.douban.FoamDoubanItem;
import com.una.embyhub.foam.service.FoamDoubanService;
import com.una.embyhub.service.EmbyApiClientService;
import com.una.embyhub.service.TmdbService;
import com.una.embyhub.util.DoubanUtils;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FoamDoubanServiceImpl implements FoamDoubanService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(FoamDoubanServiceImpl.class);
   private static final String FRODO_BASE_URL = "https://frodo.douban.com/api/v2";
   private static final String FRODO_API_KEY = "0dad551ec0f84ed02907ff5c42e8ec70";
   private static final String FRODO_MOVIE_RECOMMEND_URL = "/movie/recommend";
   private static final String FRODO_TV_RECOMMEND_URL = "/tv/recommend";
   private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().followRedirects(Redirect.NEVER).connectTimeout(Duration.ofSeconds(10L)).build();
   private static final String[] FRODO_USER_AGENTS = new String[]{
      "api-client/1 com.douban.frodo/7.18.0(230) Android/22",
      "api-client/1 com.douban.frodo/7.22.0.beta9(231) Android/23 product/Mate 40 vendor/HUAWEI model/Mate 40 brand/HUAWEI rom/android network/wifi platform/mobile",
      "api-client/1 com.douban.frodo/7.1.0(205) Android/29 product/perseus vendor/Xiaomi model/Mi MIX 3 rom/miui6 network/wifi platform/mobile nd/1",
      "api-client/1 com.douban.frodo/7.3.0(207) Android/22 product/MI 9 vendor/Xiaomi model/MI 9 brand/Android rom/miui6 network/wifi platform/mobile nd/1"
   };
   private final Random random = new Random();
   @Autowired
   private TmdbService tmdbService;
   @Autowired
   private EmbyApiClientService embyApiClientService;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   private static final String DOUBAN_CACHE_PREFIX = "douban:cache:";
   private static final long CACHE_TTL_MINUTES = 30L;

   @Override
   public FoamDoubanDiscoverResponse discoverMovies(int page, int count, String sort, String tags) {
      String cacheKey = "douban:cache:movies:" + page + ":" + count + ":" + sort + ":" + tags;
      FoamDoubanDiscoverResponse cached = (FoamDoubanDiscoverResponse)this.redisTemplate.opsForValue().get(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         FoamDoubanDiscoverResponse response = this.frodoRecommend("/movie/recommend", page, count, sort, tags);
         if (response != null && !CollectionUtils.isEmpty(response.getItems())) {
            this.redisTemplate.opsForValue().set(cacheKey, response, 30L, TimeUnit.MINUTES);
         }

         return response;
      }
   }

   @Override
   public FoamDoubanDiscoverResponse discoverTvs(int page, int count, String sort, String tags) {
      String cacheKey = "douban:cache:tvs:" + page + ":" + count + ":" + sort + ":" + tags;
      FoamDoubanDiscoverResponse cached = (FoamDoubanDiscoverResponse)this.redisTemplate.opsForValue().get(cacheKey);
      if (cached != null) {
         return cached;
      } else {
         FoamDoubanDiscoverResponse response = this.frodoRecommend("/tv/recommend", page, count, sort, tags);
         if (response != null && !CollectionUtils.isEmpty(response.getItems())) {
            this.redisTemplate.opsForValue().set(cacheKey, response, 30L, TimeUnit.MINUTES);
         }

         return response;
      }
   }

   private FoamDoubanDiscoverResponse frodoRecommend(String path, int page, int count, String sort, String tags) {
      int actualPage = Math.max(1, page);
      int actualCount = Math.min(100, Math.max(1, count));
      int start = (actualPage - 1) * actualCount;
      Map<String, String> params = new LinkedHashMap<>();
      params.put("start", String.valueOf(start));
      params.put("count", String.valueOf(actualCount));
      params.put("sort", StringUtils.hasText(sort) ? sort : "R");
      params.put("tags", StringUtils.hasText(tags) ? tags : "");
      JSONObject jsonObject = this.executeFrodoGet(path, params);
      return this.buildFoamResponse(jsonObject, actualPage, actualCount);
   }

   private JSONObject executeFrodoGet(String path, Map<String, String> params) {
      String url = "https://frodo.douban.com/api/v2" + path;
      String ts = todayTs();
      Map<String, String> fullParams = new LinkedHashMap<>(params);
      fullParams.put("os_rom", "android");
      fullParams.put("apiKey", "0dad551ec0f84ed02907ff5c42e8ec70");
      fullParams.put("_ts", ts);
      fullParams.put("_sig", DoubanUtils.sign(url, ts, "GET"));
      URI uri = URI.create(url + "?" + toQuery(fullParams));
      String ua = FRODO_USER_AGENTS[this.random.nextInt(FRODO_USER_AGENTS.length)];

      try {
         HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", "application/json").header("User-Agent", ua).GET().build();
         HttpResponse<String> resp = HTTP_CLIENT.send(req, BodyHandlers.ofString(StandardCharsets.UTF_8));
         if (resp.statusCode() / 100 == 2) {
            return JSON.parseObject(resp.body());
         } else {
            log.warn("Frodo API 失败: path={}, status={}, body={}", path, resp.statusCode(), resp.body());
            return null;
         }
      } catch (Exception var10) {
         log.error("Frodo API 异常: path={}", path, var10);
         return null;
      }
   }

   private static String toQuery(Map<String, String> params) {
      return params.entrySet().stream().map(e -> enc(e.getKey()) + "=" + enc(e.getValue())).collect(Collectors.joining("&"));
   }

   private static String enc(String s) {
      return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
   }

   private static String todayTs() {
      return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
   }

   private FoamDoubanDiscoverResponse buildFoamResponse(JSONObject jsonObject, int page, int pageSize) {
      FoamDoubanDiscoverResponse response = new FoamDoubanDiscoverResponse();
      response.setPage(page);
      response.setPageSize(pageSize);
      if (jsonObject == null) {
         return response;
      } else {
         JSONArray items = jsonObject.getJSONArray("items");
         List<FoamDoubanItem> foamItems = new ArrayList<>();
         if (!CollectionUtils.isEmpty(items)) {
            for (int i = 0; i < items.size(); i++) {
               JSONObject item = items.getJSONObject(i);
               foamItems.add(this.convertToFoamItem(item));
            }
         }

         response.setTotal(jsonObject.getLong("total"));
         if (response.getTotal() == null) {
            response.setTotal((long)foamItems.size());
         }

         response.setItems(foamItems);
         return response;
      }
   }

   private FoamDoubanItem convertToFoamItem(JSONObject obj) {
      FoamDoubanItem item = new FoamDoubanItem();
      item.setDoubanId(obj.getString("id"));
      item.setTitle(obj.getString("title"));
      item.setYear(obj.getString("year"));
      item.setCardSubtitle(obj.getString("card_subtitle"));
      item.setEpisodesInfo(obj.getString("episodes_info"));
      item.setUrl(obj.getString("uri"));
      if (StringUtils.hasText(item.getDoubanId())) {
         item.setUrl("https://movie.douban.com/subject/" + item.getDoubanId());
      }

      JSONObject rating = obj.getJSONObject("rating");
      if (rating != null) {
         item.setRate(rating.getString("value"));
         item.setRatingCount(rating.getInteger("count"));
      }

      JSONObject pic = obj.getJSONObject("pic");
      if (pic != null) {
         item.setCover(pic.getString("normal"));
         item.setBackground(pic.getString("large"));
      }

      JSONArray tags = obj.getJSONArray("tags");
      if (!CollectionUtils.isEmpty(tags)) {
         List<String> tagList = new ArrayList<>();

         for (int i = 0; i < tags.size(); i++) {
            tagList.add(tags.getJSONObject(i).getString("name"));
         }

         item.setTags(tagList);
      }

      return item;
   }

   private void enrichLibraryStatus(List<FoamDoubanItem> items) {
      if (!CollectionUtils.isEmpty(items)) {
         try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (FoamDoubanItem item : items) {
               futures.add(CompletableFuture.runAsync(() -> {
                  try {
                     Integer tmdbId = this.matchTmdbId(item);
                     if (tmdbId != null) {
                        item.setTmdbId(tmdbId);
                        item.setInLibrary(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(tmdbId)));
                     } else {
                        item.setInLibrary(false);
                     }
                  } catch (Exception var3x) {
                     item.setInLibrary(false);
                  }
               }, executor));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
         }
      }
   }

   private Integer matchTmdbId(FoamDoubanItem item) {
      Integer year = null;
      if (StringUtils.hasText(item.getYear())) {
         try {
            year = Integer.parseInt(item.getYear().substring(0, 4));
         } catch (Exception var4) {
         }
      }

      return this.searchInTmdb(item.getTitle(), year);
   }

   private Integer searchInTmdb(String title, Integer year) {
      try {
         MovieResultsPage movieResults = this.tmdbService.searchMovie(title, year);
         if (movieResults != null && !CollectionUtils.isEmpty(movieResults.getResults())) {
            return movieResults.getResults().get(0).getId();
         }

         TvSeriesResultsPage tvResults = this.tmdbService.searchTv(title, year);
         if (tvResults != null && !CollectionUtils.isEmpty(tvResults.getResults())) {
            return tvResults.getResults().get(0).getId();
         }
      } catch (Exception var5) {
      }

      return null;
   }
}
