package com.una.embyhub.config.common.utils;

import com.alibaba.fastjson2.JSON;
import com.una.embyhub.model.dto.request.moviepilot.MoviePilotLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MoviePilotConfigUtils {
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;

   public MoviePilotLoginRequest getMoviePilotLoginRequest() {
      String configValue = this.configCacheLoaderUtils.getConfigValue("movie_pilot_config");
      return StringUtils.hasText(configValue) ? JSON.parseObject(configValue, MoviePilotLoginRequest.class) : null;
   }
}
