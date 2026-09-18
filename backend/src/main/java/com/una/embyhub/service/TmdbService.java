package com.una.embyhub.service;

import com.una.embyhub.model.dto.response.telegram.PublisherGroupResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbSettingsResponse;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
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
import java.util.List;

public interface TmdbService {
   MultiResultsPage search(String query, Integer page) throws TmdbException;

   TmdbResponse searchData(String query, Integer page) throws TmdbException;

   TmdbResponse searchDataTelegram(String query, Integer page) throws TmdbException;

   TmdbResponse trendingAll(Integer dayType, Integer page) throws TmdbException;

   TmdbResponse trendingMovie(Integer dayType) throws TmdbException;

   TmdbResponse trendingTv(Integer dayType) throws TmdbException;

   List<String> trendingAllImages() throws TmdbException;

   List<String> trendingAllImagesPopular(String width) throws TmdbException;

   FindResults findById(String externalId, ExternalSource externalSource, String language) throws TmdbException;

   MovieResultsPage searchMovie(String query, Integer year) throws TmdbException;

   TvSeriesResultsPage searchTv(String query, Integer year) throws TmdbException;

   MovieDb getMovieDetails(int movieId, String language, MovieAppendToResponse... appendToResponse) throws TmdbException;

   TvEpisodeDb getEpisodeDetails(int seriesId, int seasonNumber, int episodeNumber, String language, TvEpisodesAppendToResponse... appendToResponse) throws TmdbException;

   TvSeasonDb getTvSeasons(int seriesId, int seasonNumber, String language, TvSeasonsAppendToResponse... appendToResponse) throws TmdbException;

   TvSeriesDb getTvSeries(int seriesId, String language, TvSeriesAppendToResponse... appendToResponse) throws TmdbException;

   TmdbSettingsResponse getTmdbSettings();

   List<PublisherGroupResponse> getPublisherDetails();
}
