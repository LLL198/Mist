package com.una.embyhub.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.movie.model.MovieDownloadRecordWithDetailsResponse;
import com.una.embyhub.movie.model.MoviePtSubscribe;
import com.una.embyhub.movie.model.MoviePtSubscribeRequest;
import com.una.embyhub.movie.model.MoviePtSubscribeSearchProgressEvent;
import com.una.embyhub.movie.model.MoviePtSubscribeSearchResponse;
import com.una.embyhub.movie.model.MovieSubscribeQualityConfig;
import java.util.List;
import java.util.function.Consumer;

public interface MoviePtSubscribeService {
   List<MoviePtSubscribe> list(String state);

   default List<MoviePtSubscribe> list() {
      return this.list(null);
   }

   MoviePtSubscribe getById(Long id);

   MoviePtSubscribe save(MoviePtSubscribeRequest request);

   void delete(Long id);

   void deleteByTmdbId(Long tmdbId, String type, Integer season);

   MoviePtSubscribeSearchResponse searchOnce(Long id, Integer limit, Boolean autoDownload, String title, String originalTitle, String year, String type);

   default MoviePtSubscribeSearchResponse searchOnceWithProgress(
      Long id,
      Integer limit,
      Boolean autoDownload,
      String title,
      String originalTitle,
      String year,
      String type,
      Consumer<MoviePtSubscribeSearchProgressEvent> progressConsumer
   ) {
      return this.searchOnce(id, limit, autoDownload, title, originalTitle, year, type);
   }

   List<MoviePtSubscribeSearchResponse> searchAll(Integer limit, Boolean autoDownload);

   Page<MovieDownloadRecordWithDetailsResponse> pageMatchedDownloadRecords(Long id, long current, long size);

   MovieSubscribeQualityConfig saveQualityConfig(MovieSubscribeQualityConfig config);

   MovieSubscribeQualityConfig getQualityConfig();
}
