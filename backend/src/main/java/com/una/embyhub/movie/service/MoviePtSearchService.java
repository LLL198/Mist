package com.una.embyhub.movie.service;

import com.una.embyhub.movie.model.MoviePtSearchProgressEvent;
import com.una.embyhub.movie.model.MoviePtSearchResult;
import java.util.List;
import java.util.function.Consumer;

public interface MoviePtSearchService {
   List<MoviePtSearchResult> search(String keyword, Long siteId, Integer limit, String title, String originalTitle, String year, String type);

   List<MoviePtSearchResult> searchWithProgress(
      String keyword,
      Long siteId,
      Integer limit,
      String title,
      String originalTitle,
      String year,
      String type,
      Consumer<MoviePtSearchProgressEvent> progressConsumer
   );
}
