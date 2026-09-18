package com.una.embyhub.movie.service;

import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.service.TmdbService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.movies.Translation;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MovieNotifyTmdbEnrichService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MovieNotifyTmdbEnrichService.class);
   private static final String TMDB_LANGUAGE_ZH = "zh-CN";
   private final TmdbService tmdbService;

   public void fillTmdbInfo(SendPhotoRequest request, Long tmdbId, String mediaType) {
      if (request != null && tmdbId != null && StringUtils.hasText(mediaType)) {
         try {
            String normalizedType = mediaType.trim().toLowerCase();
            if ("movie".equals(normalizedType)) {
               this.fillMovieInfo(request, tmdbId.intValue());
               return;
            }

            if ("tv".equals(normalizedType)
               || "series".equals(normalizedType)
               || "episode".equals(normalizedType)
               || "电视剧".equals(mediaType.trim())
               || "剧集".equals(mediaType.trim())) {
               this.fillTvInfo(request, tmdbId.intValue());
            }
         } catch (Exception var5) {
            log.warn("获取TMDB详情失败 tmdbId={}: {}", tmdbId, var5.getMessage());
         }
      }
   }

   private void fillMovieInfo(SendPhotoRequest request, int tmdbId) throws TmdbException {
      MovieDb movie = this.tmdbService.getMovieDetails(tmdbId, "zh-CN", MovieAppendToResponse.TRANSLATIONS);
      if (movie != null) {
         String overview = this.resolveMovieOverview(movie.getOverview(), movie.getTranslations() != null ? movie.getTranslations().getTranslations() : null);
         this.applyReleaseDate(request, movie.getReleaseDate());
         request.setGenres(this.joinMovieGenres(movie));
         request.setVoteAverage(movie.getVoteAverage());
         request.setVoteCount(movie.getVoteCount());
         request.setRuntime(movie.getRuntime());
         if (movie.getProductionCountries() != null) {
            request.setProductionCountries(
               movie.getProductionCountries().stream().map(country -> country.getName()).filter(StringUtils::hasText).collect(Collectors.joining(","))
            );
         }

         if (!StringUtils.hasText(request.getOverview()) && StringUtils.hasText(overview)) {
            request.setOverview(overview);
         }

         request.setType("movie");
         if (!StringUtils.hasText(request.getDisplayTitle())) {
            request.setDisplayTitle(movie.getTitle());
         }
      }
   }

   private void fillTvInfo(SendPhotoRequest request, int tmdbId) throws TmdbException {
      TvSeriesDb tv = this.tmdbService.getTvSeries(tmdbId, "zh-CN", TvSeriesAppendToResponse.TRANSLATIONS);
      if (tv != null) {
         String overview = this.resolveTvOverview(tv.getOverview(), tv.getTranslations() != null ? tv.getTranslations().getTranslations() : null);
         this.applyReleaseDate(request, tv.getFirstAirDate());
         request.setGenres(this.joinTvGenres(tv));
         request.setVoteAverage(tv.getVoteAverage());
         request.setVoteCount(tv.getVoteCount());
         if (!CollectionUtils.isEmpty(tv.getEpisodeRunTime())) {
            request.setRuntime(tv.getEpisodeRunTime().stream().filter(runtime -> runtime != null && runtime > 0).findFirst().orElse(null));
         }

         if (!CollectionUtils.isEmpty(tv.getOriginCountry())) {
            request.setProductionCountries(tv.getOriginCountry().stream().filter(StringUtils::hasText).collect(Collectors.joining(",")));
         }

         if (!StringUtils.hasText(request.getOverview()) && StringUtils.hasText(overview)) {
            request.setOverview(overview);
         }

         request.setType("tv");
         if (!StringUtils.hasText(request.getDisplayTitle())) {
            request.setDisplayTitle(tv.getName());
         }
      }
   }

   private void applyReleaseDate(SendPhotoRequest request, String releaseDate) {
      if (StringUtils.hasText(releaseDate)) {
         request.setReleaseDate(releaseDate);

         try {
            LocalDate date = LocalDate.parse(releaseDate);
            request.setProductionYear(date.getYear());
         } catch (Exception var4) {
         }
      }
   }

   private String joinMovieGenres(MovieDb movie) {
      return movie.getGenres() == null
         ? null
         : movie.getGenres().stream().map(genre -> genre.getName()).filter(StringUtils::hasText).collect(Collectors.joining(","));
   }

   private String joinTvGenres(TvSeriesDb tv) {
      return tv.getGenres() == null
         ? null
         : tv.getGenres().stream().map(genre -> genre.getName()).filter(StringUtils::hasText).collect(Collectors.joining(","));
   }

   private String resolveMovieOverview(String overview, List<Translation> translations) {
      if (StringUtils.hasText(overview)) {
         return overview;
      } else {
         return CollectionUtils.isEmpty(translations)
            ? overview
            : translations.stream()
               .filter(translation -> translation.getData() != null && StringUtils.hasText(translation.getData().getOverview()))
               .sorted((left, right) -> this.translationPriority(left.getIso6391()) - this.translationPriority(right.getIso6391()))
               .map(translation -> translation.getData().getOverview())
               .findFirst()
               .orElse(overview);
      }
   }

   private String resolveTvOverview(String overview, List<info.movito.themoviedbapi.model.tv.series.Translation> translations) {
      if (StringUtils.hasText(overview)) {
         return overview;
      } else {
         return CollectionUtils.isEmpty(translations)
            ? overview
            : translations.stream()
               .filter(translation -> translation.getData() != null && StringUtils.hasText(translation.getData().getOverview()))
               .sorted((left, right) -> this.translationPriority(left.getIso6391()) - this.translationPriority(right.getIso6391()))
               .map(translation -> translation.getData().getOverview())
               .findFirst()
               .orElse(overview);
      }
   }

   private int translationPriority(String iso) {
      if ("zh".equalsIgnoreCase(iso) || "cn".equalsIgnoreCase(iso)) {
         return 0;
      } else {
         return "en".equalsIgnoreCase(iso) ? 1 : 2;
      }
   }

   @Generated
   public MovieNotifyTmdbEnrichService(final TmdbService tmdbService) {
      this.tmdbService = tmdbService;
   }
}
