package com.una.embyhub.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.foam.client.TmdbClient;
import com.una.embyhub.model.dto.request.tmdb.UpcomingTrailersRequest;
import com.una.embyhub.model.dto.response.telegram.PublisherGroupResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbSettingsResponse;
import com.una.embyhub.service.TmdbService;
import info.movito.themoviedbapi.model.core.multi.MultiResultsPage;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"tmdb"})
public class TmdbController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbController.class);
   @Autowired
   private TmdbService tmdbService;
   @Autowired
   private TmdbClient tmdbClient;

   @PostMapping({"search"})
   public MultiResultsPage search(@RequestParam String query, Integer page) throws TmdbException {
      return this.tmdbService.search(query, page);
   }

   @PostMapping({"searchData"})
   public TmdbResponse searchData(@RequestParam String query, Integer page) throws TmdbException {
      return this.tmdbService.searchData(query, page);
   }

   @PostMapping({"trending"})
   public TmdbResponse trendingAll(@RequestParam Integer dayType, Integer page) throws TmdbException {
      return this.tmdbService.trendingAll(dayType, page);
   }

   @PostMapping({"trendingMovie"})
   public TmdbResponse trendingMovie(@RequestParam Integer dayType) throws TmdbException {
      return this.tmdbService.trendingMovie(dayType);
   }

   @PostMapping({"trendingTv"})
   public TmdbResponse trendingTv(@RequestParam Integer dayType) throws TmdbException {
      return this.tmdbService.trendingTv(dayType);
   }

   @PostMapping({"trendingAllImages"})
   public List<String> trendingAllImages() throws TmdbException {
      return this.tmdbService.trendingAllImages();
   }

   @PostMapping({"trendingAllImagesPopular"})
   public List<String> trendingAllImagesPopular(String width) throws TmdbException {
      return this.tmdbService.trendingAllImagesPopular(width);
   }

   @PostMapping({"findById"})
   public FindResults findById(@RequestParam String externalId, @RequestParam ExternalSource externalSource, @RequestParam String language) throws TmdbException {
      return this.tmdbService.findById(externalId, externalSource, language);
   }

   @PostMapping({"getMovieDetails"})
   public MovieDb getMovieDetails(@RequestParam int movieId, @RequestParam String language, @RequestParam MovieAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbService.getMovieDetails(movieId, language, appendToResponse);
   }

   @PostMapping({"getEpisodeDetails"})
   public TvEpisodeDb getEpisodeDetails(
      @RequestParam int seriesId,
      @RequestParam int seasonNumber,
      @RequestParam int episodeNumber,
      @RequestParam String language,
      @RequestParam TvEpisodesAppendToResponse... appendToResponse
   ) throws TmdbException {
      return this.tmdbService.getEpisodeDetails(seriesId, seasonNumber, episodeNumber, language, appendToResponse);
   }

   @PostMapping({"getTvSeasons"})
   public TvSeasonDb getTvSeasons(
      @RequestParam int seriesId, int seasonNumber, @RequestParam String language, @RequestParam TvSeasonsAppendToResponse... appendToResponse
   ) throws TmdbException {
      return this.tmdbService.getTvSeasons(seriesId, seasonNumber, language, appendToResponse);
   }

   @PostMapping({"getTvSeries"})
   public TvSeriesDb getTvSeries(@RequestParam int seriesId, @RequestParam String language, @RequestParam TvSeriesAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbService.getTvSeries(seriesId, language, appendToResponse);
   }

   @PostMapping({"getTmdbSettings"})
   public TmdbSettingsResponse getTmdbSettings() {
      return this.tmdbService.getTmdbSettings();
   }

   @GetMapping({"publishers"})
   public List<PublisherGroupResponse> getPublishers() {
      return this.tmdbService.getPublisherDetails();
   }

   @PostMapping({"upcomingTrailers"})
   public JSONObject getUpcomingTrailers(@RequestBody UpcomingTrailersRequest request) {
      String type = request.getType() != null ? request.getType() : "all";
      Integer page = request.getPage() != null ? request.getPage() : 1;
      Integer limit = request.getLimit() != null ? request.getLimit() : 60;
      JSONObject response = new JSONObject();
      JSONArray movieResults = new JSONArray();
      JSONArray tvResults = new JSONArray();
      if ("movie".equalsIgnoreCase(type)) {
         movieResults = this.fetchMovieTrailers(page, limit);
      } else if ("tv".equalsIgnoreCase(type)) {
         tvResults = this.fetchTvTrailers(page, limit);
      } else {
         movieResults = this.fetchMovieTrailers(page, limit);
         tvResults = this.fetchTvTrailers(page, limit);
      }

      response.put("movies", movieResults);
      response.put("tvShows", tvResults);
      response.put("page", page);
      response.put("totalMovies", Integer.valueOf(movieResults.size()));
      response.put("totalTvShows", Integer.valueOf(tvResults.size()));
      return response;
   }

   private JSONArray fetchMovieTrailers(Integer page, Integer limit) {
      JSONArray results = new JSONArray();
      JSONArray allMovies = new JSONArray();
      CompletableFuture<JSONObject> nowPlayingFuture = CompletableFuture.supplyAsync(() -> this.tmdbClient.fetchNowPlayingMoviesForTrailers(page));
      CompletableFuture<JSONObject> upcomingFuture = CompletableFuture.supplyAsync(() -> this.tmdbClient.fetchUpcomingMoviesForTrailers(page));

      try {
         JSONObject nowPlaying = nowPlayingFuture.get(10L, TimeUnit.SECONDS);
         if (nowPlaying != null && nowPlaying.containsKey("results")) {
            allMovies.addAll(nowPlaying.getJSONArray("results"));
         }

         JSONObject upcoming = upcomingFuture.get(10L, TimeUnit.SECONDS);
         if (upcoming != null && upcoming.containsKey("results")) {
            allMovies.addAll(upcoming.getJSONArray("results"));
         }
      } catch (Exception var20) {
         log.error("获取电影数据源失败", (Throwable)var20);
      }

      if (!allMovies.isEmpty()) {
         Set<Long> processedIds = new HashSet<>();
         List<CompletableFuture<JSONObject>> futures = new ArrayList<>();
         LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6L);
         List<JSONObject> filteredMovies = new ArrayList<>();

         for (int i = 0; i < allMovies.size(); i++) {
            JSONObject movie = allMovies.getJSONObject(i);
            String releaseDateStr = movie.getString("release_date");
            if (StringUtils.hasText(releaseDateStr)) {
               try {
                  LocalDate releaseDate = LocalDate.parse(releaseDateStr);
                  if (releaseDate.isAfter(sixMonthsAgo)) {
                     filteredMovies.add(movie);
                  }
               } catch (Exception var19) {
                  filteredMovies.add(movie);
               }
            } else {
               filteredMovies.add(movie);
            }
         }

         filteredMovies.sort((m1, m2) -> {
            String d1 = m1.getString("release_date");
            String d2 = m2.getString("release_date");
            if (d1 == null) {
               return 1;
            } else {
               return d2 == null ? -1 : d2.compareTo(d1);
            }
         });
         int maxProcess = limit;
         int count = 0;

         for (JSONObject movieItem : filteredMovies) {
            if (count >= maxProcess) {
               break;
            }

            Long movieId = movieItem.getLong("id");
            if (movieId != null && !processedIds.contains(movieId)) {
               processedIds.add(movieId);
               count++;
               CompletableFuture<JSONObject> future = CompletableFuture.supplyAsync(() -> {
                  JSONObject videosData = this.tmdbClient.fetchMovieVideos(movieItem.getLong("id"));
                  if (videosData != null && videosData.containsKey("results")) {
                     JSONArray trailers = this.filterTrailersOnlyJson(videosData.getJSONArray("results"));
                     if (!trailers.isEmpty()) {
                        JSONObject mediaWithTrailers = new JSONObject(movieItem);
                        mediaWithTrailers.put("mediaType", "movie");
                        mediaWithTrailers.put("videos", trailers);
                        return mediaWithTrailers;
                     }
                  }

                  return null;
               });
               futures.add(future);
            }
         }

         for (CompletableFuture<JSONObject> future : futures) {
            try {
               JSONObject media = future.get(10L, TimeUnit.SECONDS);
               if (media != null) {
                  results.add(media);
               }
            } catch (Exception var18) {
            }
         }
      }

      return results;
   }

   private JSONArray fetchTvTrailers(Integer page, Integer limit) {
      JSONArray results = new JSONArray();
      JSONArray allTv = new JSONArray();
      CompletableFuture<JSONObject> onTheAirFuture = CompletableFuture.supplyAsync(() -> this.tmdbClient.fetchOnTheAirTvForTrailers(page));
      CompletableFuture<JSONObject> airingTodayFuture = CompletableFuture.supplyAsync(() -> this.tmdbClient.fetchAiringTodayTvForTrailers(page));

      try {
         JSONObject onTheAir = onTheAirFuture.get(10L, TimeUnit.SECONDS);
         if (onTheAir != null && onTheAir.containsKey("results")) {
            allTv.addAll(onTheAir.getJSONArray("results"));
         }

         JSONObject airingToday = airingTodayFuture.get(10L, TimeUnit.SECONDS);
         if (airingToday != null && airingToday.containsKey("results")) {
            allTv.addAll(airingToday.getJSONArray("results"));
         }
      } catch (Exception var17) {
         log.error("获取电视剧数据源失败", (Throwable)var17);
      }

      if (!allTv.isEmpty()) {
         Set<Long> processedIds = new HashSet<>();
         List<CompletableFuture<JSONObject>> futures = new ArrayList<>();
         int maxProcess = limit;
         int count = 0;

         for (int i = 0; i < allTv.size() && count < maxProcess; i++) {
            JSONObject tvItem = allTv.getJSONObject(i);
            Long tvId = tvItem.getLong("id");
            if (tvId != null && !processedIds.contains(tvId)) {
               processedIds.add(tvId);
               count++;
               CompletableFuture<JSONObject> future = CompletableFuture.supplyAsync(() -> {
                  JSONObject videosData = this.tmdbClient.fetchTvVideos(tvItem.getLong("id"));
                  if (videosData != null && videosData.containsKey("results")) {
                     JSONArray trailers = this.filterTrailersOnlyJson(videosData.getJSONArray("results"));
                     if (!trailers.isEmpty()) {
                        JSONObject mediaWithTrailers = new JSONObject(tvItem);
                        mediaWithTrailers.put("mediaType", "tv");
                        mediaWithTrailers.put("videos", trailers);
                        return mediaWithTrailers;
                     }
                  }

                  return null;
               });
               futures.add(future);
            }
         }

         for (CompletableFuture<JSONObject> future : futures) {
            try {
               JSONObject media = future.get(10L, TimeUnit.SECONDS);
               if (media != null) {
                  results.add(media);
               }
            } catch (Exception var16) {
            }
         }
      }

      return results;
   }

   private JSONArray filterTrailersOnlyJson(JSONArray videosArray) {
      JSONArray trailerList = new JSONArray();
      if (videosArray != null) {
         for (int i = 0; i < videosArray.size(); i++) {
            JSONObject videoItem = videosArray.getJSONObject(i);
            String type = videoItem.getString("type");
            if ("Trailer".equalsIgnoreCase(type) || "Teaser".equalsIgnoreCase(type)) {
               trailerList.add(videoItem);
            }
         }
      }

      return trailerList;
   }
}
