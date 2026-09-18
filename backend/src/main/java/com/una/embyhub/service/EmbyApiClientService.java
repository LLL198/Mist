package com.una.embyhub.service;

import com.una.embyhub.model.dto.response.emby.EmbyTmdbResponse;

public interface EmbyApiClientService {
   default boolean getEmbyByTmdbId(String tmdbId) {
      return this.getEmbyByTmdbId(tmdbId, null);
   }

   boolean getEmbyByTmdbId(String tmdbId, Long embyInfoId);

   EmbyTmdbResponse getEmbyTmdbResponseByTmdbIdAll(String tmdbId);

   default EmbyTmdbResponse getEmbyTmdbResponseByTmdbId(String tmdbId) {
      return this.getEmbyTmdbResponseByTmdbId(tmdbId, null);
   }

   EmbyTmdbResponse getEmbyTmdbResponseByTmdbId(String tmdbId, Long embyInfoId);
}
