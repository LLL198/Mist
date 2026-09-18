package com.una.embyhub.config.common.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.model.dto.response.embynotifydata.NullbrResponse;
import com.una.embyhub.model.dto.response.nullbr.MovieListResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NullbrHelperUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(NullbrHelperUtils.class);
   private static final String Movie_BASE_URL = "https://api.nullbr.eu.org/movie/{tmdbId}/115";
   private static final String Episode_BASE_URL = "https://api.nullbr.eu.org/tv/{tmdbId}/115";
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;

   public MovieListResponse sendMovieApiRequest(String tmdbId, String type) {
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("nullbr");
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("nullbr配置未开启 ,不支持找片");
         throw new BizException(ResponseStatusEnum.NULLBR_ENABLED_ERROR);
      } else {
         NullbrResponse nullbrResponse = JSONObject.parseObject(notifyChannelValue, NullbrResponse.class);
         String base_url = "";
         if ("movie".equals(type)) {
            base_url = "https://api.nullbr.eu.org/movie/{tmdbId}/115";
         }

         if ("tv".equals(type)) {
            base_url = "https://api.nullbr.eu.org/tv/{tmdbId}/115";
         }

         if (!StringUtils.hasText(base_url)) {
            throw new BizException(ResponseStatusEnum.NULLBR_TYPE_ERROR);
         } else {
            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("tmdbId", tmdbId);
            StringSubstitutor substitutor = new StringSubstitutor(valuesMap, "{", "}");
            String replacedUrl = substitutor.replace(base_url);
            HttpResponse response = HttpRequest.get(replacedUrl)
               .header("X-APP-ID", nullbrResponse.getAppid())
               .header("X-API-KEY", nullbrResponse.getApikey())
               .execute();

            MovieListResponse var10;
            try {
               if (!response.isOk()) {
                  return new MovieListResponse();
               }

               var10 = JSONObject.parseObject(response.body(), MovieListResponse.class);
            } finally {
               response.close();
            }

            return var10;
         }
      }
   }
}
