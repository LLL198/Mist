package com.una.embyhub.config.handler;

import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import embyclient.ApiClient;
import info.movito.themoviedbapi.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiClientConfiguration {
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Value("${tmdb.apitoken}")
   private String tmdbApitoken;

   @Bean
   public ApiClient apiClient() {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient);
      return apiClient;
   }

   @Bean
   public TmdbApi tmdbApi() {
      return new TmdbApi(this.tmdbApitoken);
   }
}
