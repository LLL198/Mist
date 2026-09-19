package com.una.embyhub.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.foam.controller.FoamTmdbController;
import com.una.embyhub.model.dto.request.douban.DoubanHotRequest;
import com.una.embyhub.model.dto.request.douban.DoubanSearchRequest;
import com.una.embyhub.model.dto.response.douban.DoubanIdMappingResponse;
import com.una.embyhub.model.dto.response.douban.DoubanPageResponse;
import com.una.embyhub.model.dto.response.douban.DoubanSimpleSubjectResponse;
import com.una.embyhub.model.dto.response.douban.DoubanSubjectResponse;
import com.una.embyhub.model.dto.response.douban.DoubanTmdbDetailResponse;
import com.una.embyhub.service.DoubanService;
import com.una.embyhub.service.DoubanImageProxyService;
import com.una.embyhub.service.EmbyApiClientService;
import com.una.embyhub.service.TmdbService;
import info.movito.themoviedbapi.model.find.FindResults;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.model.time.ExternalSource;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DoubanServiceImpl implements DoubanService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DoubanServiceImpl.class);
   private static final String HOT_URL = "https://movie.douban.com/j/search_subjects";
   private static final String SEARCH_URL = "https://movie.douban.com/j/subject_suggest";
   private static final String SUBJECT_URL = "https://movie.douban.com/j/subject_abstract";
   private static final String SUBJECT_DETAIL_URL = "https://movie.douban.com/subject/%s/";
   private static final String DEFAULT_TAG = "热门";
   private static final String DEFAULT_REFERER = "https://movie.douban.com";
   private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36";
   private static final String DEFAULT_COOKIE = "";
   private static final int DEFAULT_TIMEOUT = 10000;
   private static final int DEFAULT_PAGE_SIZE = 20;
   private static final int DEFAULT_ENRICH_CONCURRENCY = 4;
   private static final Pattern IMDB_ID_PATTERN = Pattern.compile("https?://www\\.imdb\\.com/title/(tt\\d+)");
   private static final Pattern IMDB_ID_TEXT_PATTERN = Pattern.compile("<span class=\"pl\">IMDb:</span>\\s*(tt\\d+)");
   private static final Pattern BID_PATTERN = Pattern.compile("bid=([^;]+)");
   private static final String DOUBAN_LOGIN_REQUIRED = "error code: 004";
   @Autowired
   private TmdbService tmdbService;
   @Autowired
   private FoamTmdbController foamTmdbController;
   @Autowired
   private EmbyApiClientService embyApiClientService;
   @Autowired
   private DoubanImageProxyService doubanImageProxyService;
   @Value("${douban.cookie:}")
   private String doubanCookie;
   @Value("${douban.enrich.concurrency:4}")
   private Integer doubanEnrichConcurrency;
   private String globalBid = null;

   @PostConstruct
   public void init() {
      log.info("初始化豆瓣服务 (Global)...");

      try {
         HttpResponse response = HttpRequest.get("https://movie.douban.com/")
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36")
            .timeout(10000)
            .execute();
         String setCookie = response.header("Set-Cookie");
         if (StringUtils.hasText(setCookie)) {
            Matcher matcher = BID_PATTERN.matcher(setCookie);
            if (matcher.find()) {
               this.globalBid = matcher.group(1);
            }
         }

         response.close();
      } catch (Exception var4) {
         log.warn("初始化获取bid失败: {}", var4.getMessage());
      }
   }

   @Override
   public DoubanPageResponse trending(DoubanHotRequest request) {
      int page = this.normalizePage(request.getPage());
      int pageSize = this.normalizePageSize(request.getPageSize());
      Map<String, Object> params = this.buildPagingParams(request.getType(), request.getTag(), page, pageSize);
      JSONObject jsonObject = this.executeGet("https://movie.douban.com/j/search_subjects", params);
      return this.buildPageResponse(jsonObject, page, pageSize);
   }

   @Override
   public DoubanPageResponse search(DoubanSearchRequest request) {
      if (!StringUtils.hasText(request.getQuery())) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "搜索关键词不能为空");
      } else {
         int page = this.normalizePage(request.getPage());
         int pageSize = this.normalizePageSize(request.getPageSize());
         Map<String, Object> params = Map.of("q", request.getQuery());
         JSONArray jsonArray = this.executeGetArray("https://movie.douban.com/j/subject_suggest", params);
         return this.buildPageResponse(jsonArray, page, pageSize);
      }
   }

   @Override
   public DoubanSubjectResponse getSubject(String doubanId) {
      if (!StringUtils.hasText(doubanId)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "豆瓣ID不能为空");
      } else {
         Map<String, Object> params = Map.of("subject_id", doubanId);
         JSONObject jsonObject = this.executeGet("https://movie.douban.com/j/subject_abstract", params);
         JSONObject subject = jsonObject.getJSONObject("subject");
         if (subject == null) {
            throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "未找到对应的豆瓣条目");
         } else {
            return this.convertToSubjectResponse(subject);
         }
      }
   }

   @Override
   public DoubanIdMappingResponse getIdMapping(String doubanId) {
      return this.resolveIdMapping(doubanId);
   }

   @Override
   public DoubanTmdbDetailResponse getTmdbDetail(String doubanId) {
      DoubanIdMappingResponse mapping = this.resolveIdMapping(doubanId);
      if (mapping.getTmdbId() != null && StringUtils.hasText(mapping.getTmdbType())) {
         DoubanTmdbDetailResponse response = new DoubanTmdbDetailResponse();
         response.setDoubanId(mapping.getDoubanId());
         response.setImdbId(mapping.getImdbId());
         response.setTmdbId(mapping.getTmdbId());
         response.setTmdbType(mapping.getTmdbType());

         try {
            if ("movie".equalsIgnoreCase(mapping.getTmdbType())) {
               JSONObject movieDb = this.foamTmdbController.movieDetail(mapping.getTmdbId().longValue());
               response.setMovieDetail(movieDb);
            } else if ("tv".equalsIgnoreCase(mapping.getTmdbType())) {
               JSONObject tvSeriesDb = this.foamTmdbController.tvDetail(mapping.getTmdbId().longValue());
               response.setTvDetail(tvSeriesDb);
            }

            return response;
         } catch (Exception var5) {
            log.warn("获取TMDB详情失败, tmdbId:{}", mapping.getTmdbId(), var5);
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "获取TMDB详情失败");
         }
      } else {
         throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "未找到对应的TMDB ID");
      }
   }

   private DoubanIdMappingResponse resolveIdMapping(String doubanId) {
      if (!StringUtils.hasText(doubanId)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "豆瓣ID不能为空");
      } else {
         String imdbId = this.fetchImdbId(doubanId);
         Integer tmdbId = null;
         String tmdbType = null;
         if (StringUtils.hasText(imdbId)) {
            try {
               FindResults findResults = this.tmdbService.findById(imdbId, ExternalSource.IMDB_ID, "zh-CN");
               if (findResults != null && !CollectionUtils.isEmpty(findResults.getMovieResults())) {
                  tmdbId = findResults.getMovieResults().get(0).getId();
                  tmdbType = "movie";
               } else if (findResults != null && !CollectionUtils.isEmpty(findResults.getTvSeriesResults())) {
                  tmdbId = findResults.getTvSeriesResults().get(0).getId();
                  tmdbType = "tv";
               } else if (findResults != null && !CollectionUtils.isEmpty(findResults.getTvEpisodeResults())) {
                  tmdbId = findResults.getTvEpisodeResults().get(0).getShowId();
                  tmdbType = "tv";
               }
            } catch (TmdbException var6) {
               log.warn("获取TMDB ID失败, imdbId:{}", imdbId, var6);
            }
         }

         if (tmdbId == null && !StringUtils.hasText(imdbId)) {
            throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "未找到对应的IMDb ID");
         } else if (tmdbId == null) {
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "获取TMDB ID失败");
         } else {
            DoubanIdMappingResponse response = new DoubanIdMappingResponse();
            response.setDoubanId(doubanId);
            response.setImdbId(imdbId);
            response.setTmdbId(tmdbId);
            response.setTmdbType(tmdbType);
            return response;
         }
      }
   }

   @Override
   public ResponseEntity<byte[]> proxyImage(String imageUrl) {
      return this.doubanImageProxyService.proxy(imageUrl);
   }

   private JSONObject executeGet(String url, Map<String, Object> params) {
      HttpResponse response = this.executeGetWithRetry(url, params, false);

      JSONObject var4;
      try {
         if (!response.isOk()) {
            log.warn("调用豆瓣接口失败, status:{}, body:{}", response.getStatus(), response.body());
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "豆瓣接口调用失败");
         }

         var4 = JSON.parseObject(response.body());
      } finally {
         response.close();
      }

      return var4;
   }

   private JSONArray executeGetArray(String url, Map<String, Object> params) {
      HttpResponse response = this.executeGetWithRetry(url, params, false);

      JSONArray var4;
      try {
         if (!response.isOk()) {
            log.warn("调用豆瓣接口失败, status:{}, body:{}", response.getStatus(), response.body());
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "豆瓣接口调用失败");
         }

         var4 = JSON.parseArray(response.body());
      } finally {
         response.close();
      }

      return var4;
   }

   private Map<String, Object> buildPagingParams(String type, String tag, int page, int pageSize) {
      Map<String, Object> params = new HashMap<>();
      String resolvedType = this.normalizeType(type);
      if (StringUtils.hasText(resolvedType)) {
         params.put("type", resolvedType);
      }

      params.put("sort", "recommend");
      params.put("tag", StringUtils.hasText(tag) ? tag : "热门");
      params.put("page_limit", pageSize);
      params.put("page_start", (page - 1) * pageSize);
      return params;
   }

   private DoubanPageResponse buildPageResponse(JSONObject jsonObject, int page, int pageSize) {
      DoubanPageResponse response = new DoubanPageResponse();
      response.setPage(page);
      response.setPageSize(pageSize);
      response.setTotal(jsonObject.getLongValue("total"));
      JSONArray subjects = jsonObject.getJSONArray("subjects");
      List<DoubanSimpleSubjectResponse> items = new ArrayList<>();
      if (!CollectionUtils.isEmpty(subjects)) {
         for (int i = 0; i < subjects.size(); i++) {
            JSONObject subject = subjects.getJSONObject(i);
            items.add(this.convertSubjectListItem(subject));
         }
      }

      this.enrichItemsWithLibraryStatus(items);
      response.setItems(items);
      return response;
   }

   private DoubanPageResponse buildPageResponse(JSONArray suggestions, int page, int pageSize) {
      DoubanPageResponse response = new DoubanPageResponse();
      response.setPage(page);
      response.setPageSize(pageSize);
      long total = CollectionUtils.isEmpty(suggestions) ? 0L : (long)suggestions.size();
      response.setTotal(total);
      List<DoubanSimpleSubjectResponse> items = new ArrayList<>();
      if (!CollectionUtils.isEmpty(suggestions)) {
         int start = (page - 1) * pageSize;
         int end = Math.min(start + pageSize, suggestions.size());

         for (int i = start; i < end; i++) {
            JSONObject suggestion = suggestions.getJSONObject(i);
            items.add(this.convertSuggestionItem(suggestion));
         }
      }

      this.enrichItemsWithLibraryStatus(items);
      response.setItems(items);
      return response;
   }

   private void enrichItemsWithLibraryStatus(List<DoubanSimpleSubjectResponse> items) {
      if (!CollectionUtils.isEmpty(items)) {
         List<CompletableFuture<Void>> futures = new ArrayList<>(items.size());

         try (ExecutorService executor = Executors.newFixedThreadPool(this.resolveEnrichConcurrency())) {
            for (DoubanSimpleSubjectResponse item : items) {
               futures.add(CompletableFuture.runAsync(() -> {
                  try {
                     DoubanIdMappingResponse mapping = this.resolveIdMappingSafe(item.getDoubanId());
                     if (mapping != null && mapping.getTmdbId() != null) {
                        item.setTmdbId(mapping.getTmdbId());
                        item.setInLibrary(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(mapping.getTmdbId())));
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

   private DoubanIdMappingResponse resolveIdMappingSafe(String doubanId) {
      if (!StringUtils.hasText(doubanId)) {
         return null;
      } else {
         try {
            return this.resolveIdMapping(doubanId);
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private DoubanSimpleSubjectResponse convertSubjectListItem(JSONObject subject) {
      DoubanSimpleSubjectResponse response = new DoubanSimpleSubjectResponse();
      response.setDoubanId(subject.getString("id"));
      response.setTitle(subject.getString("title"));
      response.setOriginalTitle(subject.getString("original_title"));
      response.setReleaseYear(subject.getString("year"));
      response.setRate(subject.getString("rate"));
      response.setCover(subject.getString("cover"));
      String background = subject.getString("cover_xl");
      response.setBackground(StringUtils.hasText(background) ? background : subject.getString("cover"));
      response.setUrl(subject.getString("url"));
      response.setPlayable(subject.getBoolean("playable"));
      response.setIsNew(subject.getBoolean("is_new"));
      response.setTypes(subject.getList("types", String.class));
      return response;
   }

   private DoubanSimpleSubjectResponse convertSuggestionItem(JSONObject suggestion) {
      DoubanSimpleSubjectResponse response = new DoubanSimpleSubjectResponse();
      response.setDoubanId(suggestion.getString("id"));
      response.setTitle(suggestion.getString("title"));
      response.setOriginalTitle(suggestion.getString("sub_title"));
      response.setReleaseYear(suggestion.getString("year"));
      response.setCover(suggestion.getString("img"));
      response.setBackground(suggestion.getString("img"));
      response.setUrl(suggestion.getString("url"));
      String type = suggestion.getString("type");
      if (StringUtils.hasText(type)) {
         String episode = suggestion.getString("episode");
         if (StringUtils.hasText(episode)) {
            response.setTypes(List.of("tv"));
         } else {
            response.setTypes(List.of("movie"));
         }
      }

      return response;
   }

   private DoubanSubjectResponse convertToSubjectResponse(JSONObject subject) {
      DoubanSubjectResponse response = new DoubanSubjectResponse();
      response.setDoubanId(subject.getString("id"));
      response.setTitle(subject.getString("title"));
      response.setOriginalTitle(subject.getString("original_title"));
      response.setReleaseYear(subject.getString("release_year"));
      response.setCover(subject.getString("cover_url"));
      String background = subject.getString("cover_xl");
      response.setBackground(StringUtils.hasText(background) ? background : subject.getString("cover_url"));
      response.setUrl(subject.getString("url"));
      response.setIntro(subject.getString("intro"));
      response.setTypes(subject.getList("types", String.class));
      response.setRegions(subject.getList("regions", String.class));
      response.setLanguages(subject.getList("languages", String.class));
      response.setDurations(subject.getList("durations", String.class));
      response.setDirectors(subject.getList("directors", String.class));
      response.setActors(subject.getList("actors", String.class));
      response.setCurrentSeason(subject.getInteger("current_season"));
      response.setSeasonsCount(subject.getInteger("seasons_count"));
      response.setEpisodesCount(subject.getInteger("episodes_count"));
      response.setEpisodesInfo(subject.getString("episodes_info"));
      JSONObject rating = subject.getJSONObject("rating");
      if (rating != null) {
         response.setScore(rating.getString("value"));
      }

      return response;
   }

   private String fetchImdbId(String doubanId) {
      String url = String.format("https://movie.douban.com/subject/%s/", doubanId);
      String body = this.fetchDoubanDetailHtml(url);
      if (!StringUtils.hasText(body)) {
         return null;
      } else {
         Matcher matcher = IMDB_ID_PATTERN.matcher(body);
         if (matcher.find()) {
            return matcher.group(1);
         } else {
            Matcher textMatcher = IMDB_ID_TEXT_PATTERN.matcher(body);
            return textMatcher.find() ? textMatcher.group(1) : null;
         }
      }
   }

   private HttpRequest withDefaultHeaders(HttpRequest request) {
      String cookie = StringUtils.hasText(this.doubanCookie) ? this.doubanCookie.trim() : (this.globalBid != null ? "bid=" + this.globalBid : "");
      request.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
         .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
         .header("Referer", "https://movie.douban.com")
         .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36");
      if (StringUtils.hasText(cookie)) {
         request.header("Cookie", cookie);
      }

      return request;
   }

   private String fetchDoubanDetailHtml(String url) {
      HttpResponse response = this.executeGetWithRetry(url, null, false);

      String var3;
      try {
         if (!response.isOk()) {
            log.warn("获取豆瓣详情页失败, status:{}, body:{}", response.getStatus(), response.body());
            return "";
         }

         var3 = response.body();
      } finally {
         response.close();
      }

      return var3;
   }

   private HttpResponse executeGetWithRetry(String url, Map<String, Object> params, boolean followRedirects) {
      HttpRequest request = this.withDefaultHeaders(HttpRequest.get(url)).setFollowRedirects(followRedirects).timeout(10000);
      if (params != null) {
         request.form(params);
      }

      return request.execute();
   }

   private int normalizePage(Integer page) {
      return page != null && page >= 1 ? page : 1;
   }

   private int normalizePageSize(Integer pageSize) {
      return pageSize != null && pageSize >= 1 ? pageSize : 20;
   }

   private int resolveEnrichConcurrency() {
      return this.doubanEnrichConcurrency != null && this.doubanEnrichConcurrency >= 1 ? this.doubanEnrichConcurrency : 4;
   }

   private String normalizeType(String type) {
      if (!StringUtils.hasText(type)) {
         return "movie";
      } else {
         String normalized = type.toLowerCase();
         return !"tv".equals(normalized) && !"movie".equals(normalized) ? "movie" : normalized;
      }
   }
}
