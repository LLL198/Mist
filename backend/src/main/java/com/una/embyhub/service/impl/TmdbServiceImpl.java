package com.una.embyhub.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.model.dto.request.telegram.PublisherInfoRequest;
import com.una.embyhub.model.dto.response.telegram.PublisherGroupResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbSettingsResponse;
import com.una.embyhub.service.EmbyApiClientService;
import com.una.embyhub.service.RequestListService;
import com.una.embyhub.service.TmdbService;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.core.multi.MultiMovie;
import info.movito.themoviedbapi.model.core.multi.MultiResultsPage;
import info.movito.themoviedbapi.model.core.multi.MultiTvSeries;
import info.movito.themoviedbapi.model.find.FindResults;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.episode.TvEpisodeDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvEpisodesAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeasonsAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import info.movito.themoviedbapi.tools.model.time.ExternalSource;
import info.movito.themoviedbapi.tools.model.time.TimeWindow;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class TmdbServiceImpl implements TmdbService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbServiceImpl.class);
   @Value("${tmdb.imageUrl}")
   private String imageUrl;
   private static final String movieUrl = "https://www.themoviedb.org/movie";
   private static final String tvShowUrl = "https://www.themoviedb.org/tv";
   @Autowired
   private TmdbApi tmdbApi;
   @Autowired
   private EmbyApiClientService embyApiClientService;
   @Autowired
   private RequestListService requlistService;
   private String imageUrlWidth = "https://image.tmdb.org/t/p/";
   @Value("${tmdb.apiKey}")
   private String tmdbApiKey;
   @Value("https://api.themoviedb.org/3")
   private String tmdbApiBaseUrl;
   private static final Map<String, List<Map<String, Object>>> TMDB_PUBLISHER_IDS = new LinkedHashMap<>();

   @Override
   public MultiResultsPage search(String query, Integer page) throws TmdbException {
      return this.tmdbApi.getSearch().searchMulti(query, false, "zh-CN", page);
   }

   @Override
   public TmdbResponse searchData(String query, Integer page) throws TmdbException {
      MultiResultsPage multiResultsPage = this.tmdbApi.getSearch().searchMulti(query, false, "zh-CN", page);
      TmdbResponse tmdbResponse = BeanUtils.convert(multiResultsPage, TmdbResponse.class);
      List<TmdbResponse.Result> resultList = new ArrayList<>();
      tmdbResponse.setResults(resultList);
      multiResultsPage.getResults().forEach(x -> {
         TmdbResponse.Result result = BeanUtils.convert(x, TmdbResponse.Result.class);
         if (x instanceof MultiMovie) {
            result.setIsExsit(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(((MultiMovie)x).getId())));
            result.setIsSubmitted(this.requlistService.getRequestListStatusByTmdbid(String.valueOf(((MultiMovie)x).getId())));
            result.setMediaType("movie");
         }

         if (x instanceof MultiTvSeries) {
            result.setIsExsit(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(((MultiTvSeries)x).getId())));
            result.setIsSubmitted(this.requlistService.getRequestListStatusByTmdbid(String.valueOf(((MultiTvSeries)x).getId())));
            result.setMediaType("tv");
         }

         resultList.add(result);
      });
      return tmdbResponse;
   }

   @Override
   public TmdbResponse searchDataTelegram(String query, Integer page) throws TmdbException {
      MultiResultsPage multiResultsPage = this.tmdbApi.getSearch().searchMulti(query, false, "zh-CN", page);
      TmdbResponse tmdbResponse = BeanUtils.convert(multiResultsPage, TmdbResponse.class);
      List<TmdbResponse.Result> resultList = new ArrayList<>();
      tmdbResponse.setResults(resultList);
      multiResultsPage.getResults().forEach(x -> {
         TmdbResponse.Result result = BeanUtils.convert(x, TmdbResponse.Result.class);
         if (x instanceof MultiMovie) {
            result.setMediaType("movie");
         }

         if (x instanceof MultiTvSeries) {
            result.setMediaType("tv");
         }

         resultList.add(result);
      });
      return tmdbResponse;
   }

   @Override
   public TmdbResponse trendingAll(Integer dayType, Integer page) throws TmdbException {
      TimeWindow timeWindow = null;
      if (dayType == 0) {
         timeWindow = TimeWindow.DAY;
      }

      if (dayType == 1) {
         timeWindow = TimeWindow.WEEK;
      }

      new MultiResultsPage();
      MultiResultsPage multiResultsPage;
      if (page != null) {
         multiResultsPage = this.tmdbApi.getTrending().getAll(timeWindow, "zh-CN", page);
      } else {
         multiResultsPage = this.tmdbApi.getTrending().getAll(timeWindow, "zh-CN");
      }

      TmdbResponse tmdbResponse = BeanUtils.convert(multiResultsPage, TmdbResponse.class);
      List<TmdbResponse.Result> resultList = new ArrayList<>();
      tmdbResponse.setResults(resultList);
      multiResultsPage.getResults().forEach(x -> {
         TmdbResponse.Result result = BeanUtils.convert(x, TmdbResponse.Result.class);
         if (x instanceof MultiMovie) {
            result.setIsExsit(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(((MultiMovie)x).getId())));
            result.setIsSubmitted(this.requlistService.getRequestListStatusByTmdbid(String.valueOf(((MultiMovie)x).getId())));
            result.setMediaType("movie");
         }

         if (x instanceof MultiTvSeries) {
            result.setIsExsit(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(((MultiTvSeries)x).getId())));
            result.setIsSubmitted(this.requlistService.getRequestListStatusByTmdbid(String.valueOf(((MultiTvSeries)x).getId())));
            result.setMediaType("tv");
         }

         resultList.add(result);
      });
      return tmdbResponse;
   }

   @Override
   public TmdbResponse trendingMovie(Integer dayType) throws TmdbException {
      TimeWindow timeWindow = null;
      if (dayType == 0) {
         timeWindow = TimeWindow.DAY;
      }

      if (dayType == 1) {
         timeWindow = TimeWindow.WEEK;
      }

      MovieResultsPage movieResultsPage = this.tmdbApi.getTrending().getMovies(timeWindow, "zh-CN");
      TmdbResponse tmdbResponse = BeanUtils.convert(movieResultsPage, TmdbResponse.class);
      List<TmdbResponse.Result> resultList = new ArrayList<>();
      tmdbResponse.setResults(resultList);
      movieResultsPage.getResults().forEach(x -> {
         TmdbResponse.Result result = BeanUtils.convert(x, TmdbResponse.Result.class);
         result.setIsExsit(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(x.getId())));
         result.setIsSubmitted(this.requlistService.getRequestListStatusByTmdbid(String.valueOf(x.getId())));
         result.setMediaType("movie");
         resultList.add(result);
      });
      return tmdbResponse;
   }

   @Override
   public TmdbResponse trendingTv(Integer dayType) throws TmdbException {
      TimeWindow timeWindow = null;
      if (dayType == 0) {
         timeWindow = TimeWindow.DAY;
      }

      if (dayType == 1) {
         timeWindow = TimeWindow.WEEK;
      }

      TvSeriesResultsPage tvSeriesResultsPage = this.tmdbApi.getTrending().getTv(timeWindow, "zh-CN");
      TmdbResponse tmdbResponse = BeanUtils.convert(tvSeriesResultsPage, TmdbResponse.class);
      List<TmdbResponse.Result> resultList = new ArrayList<>();
      tmdbResponse.setResults(resultList);
      tvSeriesResultsPage.getResults().forEach(x -> {
         TmdbResponse.Result result = BeanUtils.convert(x, TmdbResponse.Result.class);
         result.setIsExsit(this.embyApiClientService.getEmbyByTmdbId(String.valueOf(x.getId())));
         result.setIsSubmitted(this.requlistService.getRequestListStatusByTmdbid(String.valueOf(x.getId())));
         result.setMediaType("tv");
         resultList.add(result);
      });
      return tmdbResponse;
   }

   @Override
   public List<String> trendingAllImages() throws TmdbException {
      InputStream inputStream = ResourceUtil.getStream("img/image.json");
      String read = IoUtil.read(inputStream, StandardCharsets.UTF_8);
      List<String> imgUrlList = JSONArray.parseArray(read, String.class);
      Map<Integer, List<String>> weeklyData = distributeDataByWeek(imgUrlList);
      int dayOfWeek = DateUtil.dayOfWeek(new Date());
      return weeklyData.get(dayOfWeek);
   }

   @Override
   public List<String> trendingAllImagesPopular(String width) throws TmdbException {
      log.info("获取热门内容图片");
      TimeWindow timeWindow = TimeWindow.DAY;
      List<String> imgUrlList = new ArrayList<>();
      if (StringUtils.hasText(width)) {
         this.imageUrl = this.imageUrlWidth + width;
      }

      this.tmdbApi.getTrending().getAll(timeWindow, "zh-CN").getResults().forEach(x -> {
         if (x instanceof MultiMovie) {
            imgUrlList.add(this.imageUrl + ((MultiMovie)x).getBackdropPath());
         }

         if (x instanceof MultiTvSeries) {
            imgUrlList.add(this.imageUrl + ((MultiTvSeries)x).getBackdropPath());
         }
      });
      return imgUrlList;
   }

   @Override
   public FindResults findById(String externalId, ExternalSource externalSource, String language) throws TmdbException {
      return this.tmdbApi.getFind().findById(externalId, externalSource, language);
   }

   @Override
   public MovieResultsPage searchMovie(String query, Integer year) throws TmdbException {
      return this.tmdbApi.getSearch().searchMovie(query, false, "zh-CN", null, year, null, "1");
   }

   @Override
   public TvSeriesResultsPage searchTv(String query, Integer year) throws TmdbException {
      return this.tmdbApi.getSearch().searchTv(query, year, false, "zh-CN", 1, null);
   }

   @Override
   public MovieDb getMovieDetails(int movieId, String language, MovieAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbApi.getMovies().getDetails(movieId, language, appendToResponse);
   }

   @Override
   public TvEpisodeDb getEpisodeDetails(int seriesId, int seasonNumber, int episodeNumber, String language, TvEpisodesAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbApi.getTvEpisodes().getDetails(seriesId, seasonNumber, episodeNumber, language, appendToResponse);
   }

   @Override
   public TvSeasonDb getTvSeasons(int seriesId, int seasonNumber, String language, TvSeasonsAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbApi.getTvSeasons().getDetails(seriesId, seasonNumber, language, appendToResponse);
   }

   @Override
   public TvSeriesDb getTvSeries(int seriesId, String language, TvSeriesAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbApi.getTvSeries().getDetails(seriesId, language, appendToResponse);
   }

   @Override
   public TmdbSettingsResponse getTmdbSettings() {
      TmdbSettingsResponse tmdbSettingsResponse = new TmdbSettingsResponse();
      tmdbSettingsResponse.setTmdbKey(this.tmdbApiKey);
      return tmdbSettingsResponse;
   }

   @Override
   public List<PublisherGroupResponse> getPublisherDetails() {
      return TMDB_PUBLISHER_IDS.entrySet().stream().map(this::createPublisherGroup).collect(Collectors.toList());
   }

   private PublisherGroupResponse createPublisherGroup(Entry<String, List<Map<String, Object>>> entry) {
      String groupName = entry.getKey();
      List<PublisherInfoRequest> publisherInfos = entry.getValue().stream().map(this::fetchPublisherInfo).filter(Objects::nonNull).collect(Collectors.toList());
      return new PublisherGroupResponse(groupName, publisherInfos);
   }

   private PublisherInfoRequest fetchPublisherInfo(Map<String, Object> pubConfig) {
      String type = (String)pubConfig.get("type");
      Integer id = (Integer)pubConfig.get("id");
      List<String> names = (List<String>)pubConfig.get("names");
      String url = String.format("%s/%s/%d", this.tmdbApiBaseUrl, type, id);

      try {
         HttpRequest request = HttpUtil.createGet(url).form("api_key", this.tmdbApiKey).form("language", "zh-CN");
         String responseBody = request.execute().body();
         JSONObject data = JSON.parseObject(responseBody);
         return new PublisherInfoRequest(data.getLongValue("id"), data.getString("name"), data.getString("logo_path"), names);
      } catch (Exception var9) {
         log.error("Failed to fetch TMDB data for type '{}', id {}: {}", type, id, var9.getMessage());
         return null;
      }
   }

   private static Map<Integer, List<String>> distributeDataByWeek(List<String> dataList) {
      if (dataList.size() != 70) {
         throw new IllegalArgumentException("数据量必须为70条，当前为：" + dataList.size());
      } else {
         Integer[] weekDays = new Integer[]{1, 2, 3, 4, 5, 6, 7};
         Map<Integer, List<String>> result = new LinkedHashMap<>();

         for (Integer day : weekDays) {
            result.put(day, new ArrayList<>());
         }

         int dayIndex = 0;

         for (int i = 0; i < dataList.size(); i++) {
            Integer day = weekDays[dayIndex];
            result.get(day).add(dataList.get(i));
            if ((i + 1) % 10 == 0) {
               dayIndex++;
            }
         }

         return result;
      }
   }

   static {
      TMDB_PUBLISHER_IDS.put(
         "流媒体平台",
         List.of(
            Map.of("type", "network", "id", 213, "names", List.of("Netflix")),
            Map.of("type", "network", "id", 2739, "names", List.of("Disney+", "Disney")),
            Map.of("type", "network", "id", 49, "names", List.of("HBO", "HBO Max")),
            Map.of("type", "network", "id", 1024, "names", List.of("Amazon Prime Video", "Amazon Studios")),
            Map.of("type", "network", "id", 2552, "names", List.of("Apple TV+")),
            Map.of("type", "network", "id", 453, "names", List.of("Hulu"))
         )
      );
      TMDB_PUBLISHER_IDS.put(
         "电影制片厂",
         List.of(
            Map.of("type", "company", "id", 420, "names", List.of("Marvel Studios")),
            Map.of("type", "company", "id", 2, "names", List.of("Walt Disney Pictures")),
            Map.of("type", "company", "id", 174, "names", List.of("Warner Bros. Pictures", "Warner Bros. Television")),
            Map.of("type", "company", "id", 5, "names", List.of("Columbia Pictures")),
            Map.of("type", "company", "id", 4, "names", List.of("Paramount")),
            Map.of("type", "company", "id", 25, "names", List.of("20th Century Studios")),
            Map.of("type", "company", "id", 33, "names", List.of("Universal Pictures")),
            Map.of("type", "company", "id", 1632, "names", List.of("Lionsgate"))
         )
      );
      TMDB_PUBLISHER_IDS.put(
         "电视台 & 其他",
         List.of(
            Map.of("type", "network", "id", 19, "names", List.of("BBC", "BBC One", "BBC Two", "BBC Studios")),
            Map.of("type", "network", "id", 67, "names", List.of("NHK")),
            Map.of("type", "network", "id", 323, "names", List.of("Fuji TV", "Fuji Television"))
         )
      );
   }
}
