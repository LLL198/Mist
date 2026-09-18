package com.una.embyhub.service.impl;

import com.una.embyhub.config.common.utils.NullbrHelperUtils;
import com.una.embyhub.model.dto.response.nullbr.MovieListResponse;
import com.una.embyhub.service.NullbrService;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.tools.TmdbException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class NullbrServiceImpl implements NullbrService {
   @Autowired
   private TmdbApi tmdbApi;
   @Autowired
   private NullbrHelperUtils nullbrHelperUtils;

   @Override
   public MovieListResponse select(String tmdbId, String type) throws TmdbException {
      MovieListResponse movieListResponse = this.nullbrHelperUtils.sendMovieApiRequest(tmdbId, type);
      if (CollectionUtils.isEmpty(movieListResponse.getMovieList115DTOList())) {
         return new MovieListResponse();
      } else {
         if ("movie".equals(type)) {
            Credits credits = this.tmdbApi.getMovies().getCredits(Integer.parseInt(tmdbId), "zh-CN");
            List<MovieListResponse.CreditsDTO> profilePathList = credits.getCast()
               .stream()
               .filter(x -> StringUtils.hasText(x.getProfilePath()) && StringUtils.hasText(x.getName()))
               .map(x -> {
                  MovieListResponse.CreditsDTO creditsDTO = new MovieListResponse.CreditsDTO();
                  creditsDTO.setName(x.getName());
                  creditsDTO.setProfilePath("https://image.tmdb.org/t/p/w138_and_h175_face" + x.getProfilePath());
                  return creditsDTO;
               })
               .toList();
            movieListResponse.setCreditsDTOList(profilePathList);
         }

         if ("tv".equals(type)) {
            info.movito.themoviedbapi.model.tv.core.credits.Credits credits = this.tmdbApi.getTvSeries().getCredits(Integer.parseInt(tmdbId), "zh-CN");
            List<MovieListResponse.CreditsDTO> profilePathList = credits.getCast()
               .stream()
               .filter(x -> StringUtils.hasText(x.getProfilePath()) && StringUtils.hasText(x.getName()))
               .map(x -> {
                  MovieListResponse.CreditsDTO creditsDTO = new MovieListResponse.CreditsDTO();
                  creditsDTO.setName(x.getName());
                  creditsDTO.setProfilePath("https://image.tmdb.org/t/p/w138_and_h175_face" + x.getProfilePath());
                  return creditsDTO;
               })
               .toList();
            movieListResponse.setCreditsDTOList(profilePathList);
         }

         return movieListResponse;
      }
   }
}
