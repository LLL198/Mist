package com.una.embyhub.controller;

import com.una.embyhub.config.common.utils.MovieDbUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.core.TvKeywords;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.core.video.VideoResults;
import info.movito.themoviedbapi.model.tv.core.credits.Credits;
import info.movito.themoviedbapi.model.tv.series.Images;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"tv"})
public class TvApiController {
   @Autowired
   private TmdbApi tmdbApi;
   @Autowired
   private MovieDbUtils movieDbUtils;

   @PostMapping({"{tvId}"})
   public TvSeriesDb getTvDetails(@PathVariable int tvId, @RequestParam String language, TvSeriesAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbApi.getTvSeries().getDetails(tvId, language, appendToResponse);
   }

   @PostMapping({"getTvCredits"})
   public Credits getTvCredits(@RequestParam int tvId, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getTvSeries().getCredits(tvId, language);
   }

   @PostMapping({"getTvKeywords"})
   public TvKeywords getTvKeywords(@RequestParam int tvId) throws TmdbException {
      return this.tmdbApi.getTvSeries().getKeywords(tvId);
   }

   @PostMapping({"getTvImages"})
   public Images getTvImages(@RequestParam int tvId, @RequestParam String includeImageLanguage) throws TmdbException {
      return this.tmdbApi.getTvSeries().getImages(tvId, "zh-CN", includeImageLanguage);
   }

   @PostMapping({"getTvList"})
   public List<Genre> getTvList(@RequestParam String language) throws TmdbException {
      return this.tmdbApi.getGenre().getTvList(language);
   }

   @PostMapping({"getTvRecommendations"})
   public TvSeriesResultsPage getTvRecommendations(@RequestParam int tvId, @RequestParam String language, @RequestParam int page) throws TmdbException {
      return this.tmdbApi.getTvSeries().getSimilar(tvId, language, page);
   }

   @PostMapping({"getTvPopular"})
   public String getMoviePopular() throws TmdbException {
      return this.movieDbUtils.fetchMediaItemResponses(3, 2);
   }

   @PostMapping({"getTvVideos"})
   public VideoResults getTvVideos(@RequestParam int tvId, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getTvSeries().getVideos(tvId, language);
   }

   @PostMapping({"getTvPopularData"})
   public TvSeriesResultsPage getTvPopularData(@RequestParam int page, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getTvSeriesLists().getPopular(language, page);
   }
}
