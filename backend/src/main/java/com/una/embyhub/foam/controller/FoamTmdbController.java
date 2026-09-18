package com.una.embyhub.foam.controller;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.foam.client.TmdbClient;
import com.una.embyhub.foam.service.TmdbRecommendationService;
import com.una.embyhub.service.EmbyApiClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/foam/tmdb"})
public class FoamTmdbController {
   @Autowired
   private TmdbClient tmdbClient;
   @Autowired
   private TmdbRecommendationService tmdbRecommendationService;
   @Autowired
   private EmbyApiClientService embyApiClientService;

   @GetMapping({"/library-status"})
   public Boolean getLibraryStatus(@RequestParam String tmdbId, @RequestParam(required = false) Long embyInfoId) {
      return !StringUtils.hasText(tmdbId) ? false : this.embyApiClientService.getEmbyByTmdbId(tmdbId, embyInfoId);
   }

   @GetMapping({"/trending"})
   public JSONObject trending(@RequestParam(defaultValue = "day") String timeWindow, @RequestParam(defaultValue = "1") Integer page) {
      return this.tmdbClient.fetchTrending(timeWindow, page);
   }

   @GetMapping({"/movies/popular"})
   public JSONObject popularMovies(@RequestParam(defaultValue = "1") Integer page) {
      return this.tmdbClient.fetchPopularMovies(page);
   }

   @GetMapping({"/tv/popular"})
   public JSONObject popularTv(@RequestParam(defaultValue = "1") Integer page) {
      return this.tmdbClient.fetchPopularTv(page);
   }

   @GetMapping({"/search/multi"})
   public JSONObject searchMulti(@RequestParam String keyword, @RequestParam(defaultValue = "1") Integer page) {
      return this.tmdbClient.searchMulti(keyword, page);
   }

   @GetMapping({"/movie/{id}"})
   public JSONObject movieDetail(@PathVariable Long id) {
      return this.tmdbClient.fetchMovieDetail(id);
   }

   @GetMapping({"/movie/{id}/videos"})
   public JSONObject movieVideos(@PathVariable Long id) {
      return this.tmdbClient.fetchMovieVideos(id);
   }

   @GetMapping({"/tv/{id}"})
   public JSONObject tvDetail(@PathVariable Long id) {
      return this.tmdbClient.fetchTvDetail(id);
   }

   @GetMapping({"/tv/{id}/videos"})
   public JSONObject tvVideos(@PathVariable Long id) {
      return this.tmdbClient.fetchTvVideos(id);
   }

   @GetMapping({"/tv/{id}/season/{seasonNumber}"})
   public JSONObject tvSeasonDetail(@PathVariable Long id, @PathVariable Integer seasonNumber) {
      return this.tmdbClient.fetchTvSeasonDetail(id, seasonNumber);
   }

   @GetMapping({"/recommend"})
   public JSONObject recommend(
      @RequestParam(required = false) String userName,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(required = false) Integer pageSize,
      @RequestParam(required = false) Integer limit
   ) {
      return this.tmdbRecommendationService.recommend(userName, page, pageSize, limit);
   }
}
