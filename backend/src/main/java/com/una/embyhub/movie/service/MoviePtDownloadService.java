package com.una.embyhub.movie.service;

import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MoviePtDownloadRequest;

public interface MoviePtDownloadService {
   MovieActionResponse downloadAndAdd(MoviePtDownloadRequest request);
}
