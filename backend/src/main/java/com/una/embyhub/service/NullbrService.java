package com.una.embyhub.service;

import com.una.embyhub.model.dto.response.nullbr.MovieListResponse;
import info.movito.themoviedbapi.tools.TmdbException;

public interface NullbrService {
   MovieListResponse select(String tmdbId, String type) throws TmdbException;
}
