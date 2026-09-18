package com.una.embyhub.controller;

import com.una.embyhub.config.common.utils.MovieDbUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.video.VideoResults;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.Images;
import info.movito.themoviedbapi.model.movies.KeywordResults;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"movie"})
public class MovieDbApiController {
   @Autowired
   private TmdbApi tmdbApi;
   @Autowired
   private MovieDbUtils movieDbUtils;

   @PostMapping({"{movieId}"})
   public MovieDb getMovieDetails(@PathVariable int movieId, @RequestParam String language, MovieAppendToResponse... appendToResponse) throws TmdbException {
      return this.tmdbApi.getMovies().getDetails(movieId, language, appendToResponse);
   }

   @PostMapping({"getMovieCredits"})
   public Credits getMovieCredits(@RequestParam int movieId, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getMovies().getCredits(movieId, language);
   }

   @PostMapping({"getMovieKeywords"})
   public KeywordResults getMovieKeywords(@RequestParam int movieId) throws TmdbException {
      return this.tmdbApi.getMovies().getKeywords(movieId);
   }

   @PostMapping({"getMovieVideos"})
   public VideoResults getMovieVideos(@RequestParam int movieId, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getMovies().getVideos(movieId, language);
   }

   @PostMapping({"getMovieImages"})
   public Images getMovieImages(@RequestParam int movieId, @RequestParam String includeImageLanguage) throws TmdbException {
      return this.tmdbApi.getMovies().getImages(movieId, "zh-CN", includeImageLanguage);
   }

   @PostMapping({"getMovieAllGenres"})
   public List<Genre> getMovieAllGenres(@RequestParam String language) throws TmdbException {
      return this.tmdbApi.getGenre().getMovieList(language);
   }

   @PostMapping({"getMovieSimilarMovies"})
   public MovieResultsPage getMovieSimilarMovies(@RequestParam int movieId, @RequestParam String language, @RequestParam int page) throws TmdbException {
      return this.tmdbApi.getMovies().getSimilar(movieId, language, page);
   }

   @PostMapping({"getMoviePopular"})
   public String getMoviePopular() throws TmdbException {
      return this.movieDbUtils.fetchMediaItemResponses(3, 1);
   }

   @PostMapping({"getMoviePopularData"})
   public MovieResultsPage getMoviePopularData(@RequestParam int page, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getMovieLists().getPopular(language, page, null);
   }
}
