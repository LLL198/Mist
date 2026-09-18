package com.una.embyhub.movie.service;

import com.una.embyhub.movie.model.MovieScrapePathConfig;
import com.una.embyhub.movie.model.MovieScrapePathConfigRequest;
import java.util.List;

public interface MovieScrapePathConfigService {
   List<MovieScrapePathConfig> list();

   MovieScrapePathConfig getById(Long id);

   MovieScrapePathConfig save(MovieScrapePathConfigRequest request);

   void delete(Long id);
}
