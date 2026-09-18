package com.una.embyhub.movie.service;

import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;

public interface MovieMetadataScrapeService {
   void scrape(MovieDownloadRecordEntity record);
}
