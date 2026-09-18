package com.una.embyhub.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.service.TelegramMediaService;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TelegramMediaServiceImpl implements TelegramMediaService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramMediaServiceImpl.class);
   @Value("${tmdb.apiKey}")
   private String apiKey;
   @Value("https://api.themoviedb.org/3")
   private String apiBaseUrl;
   @Value("zh-CN")
   private String language;

   @Override
   public JSONObject getHeroList(String timeWindow, int page) {
      return this.getTrending(timeWindow, page);
   }

   public JSONObject getTrending(String timeWindow, int page) {
      String path = String.format("/trending/all/%s", timeWindow);
      Map<String, Object> params = MapUtil.of("page", page);
      return this.executeGetRequest(path, params);
   }

   @Override
   public JSONObject getList(String mediaType, String category, int page) {
      if ("multi".equals(mediaType)) {
         return this.getTrending("day", page);
      } else {
         String path = String.format("/%s/%s", mediaType, category);
         Map<String, Object> params = MapUtil.of("page", page);
         return this.executeGetRequest(path, params);
      }
   }

   @Override
   public JSONObject search(String mediaType, String query, int page) {
      String path = String.format("/search/%s", mediaType);
      Map<String, Object> params = MapUtil.of("query", query);
      return this.executeGetRequest(path, params);
   }

   @Override
   public JSONObject getDetails(String mediaType, Long id) {
      String path = String.format("/%s/%d", mediaType, id);
      Map<String, Object> params = MapUtil.of("append_to_response", "images,videos,credits,aggregate_credits,recommendations");
      return this.executeGetRequest(path, params);
   }

   @Override
   public JSONObject getSeasonDetails(Long tvId, int seasonNumber) {
      String path = String.format("/tv/%d/season/%d", tvId, seasonNumber);
      return this.executeGetRequest(path, null);
   }

   private JSONObject executeGetRequest(String path, Map<String, Object> params) {
      if (StringUtils.hasText(this.apiKey) && !"YOUR_TMDB_API_V3_KEY_HERE".equals(this.apiKey)) {
         Map<String, Object> queryParams = new HashMap<>();
         if (params != null) {
            queryParams.putAll(params);
         }

         queryParams.put("api_key", this.apiKey);
         queryParams.put("language", this.language);
         String url = this.apiBaseUrl + path;

         try {
            String responseBody = HttpRequest.get(url).form(queryParams).timeout(10000).execute().body();
            return JSON.parseObject(responseBody);
         } catch (Exception var6) {
            log.error("调用TMDB API失败，路径: {}. 错误: {}", path, var6.getMessage());
            return JSON.parseObject(String.format("{\"error\": \"从TMDB获取数据失败: %s\"}", var6.getMessage()));
         }
      } else {
         log.error("TMDB API Key 未在 application.properties 中配置。");
         return JSON.parseObject("{\"error\": \"服务器端的TMDB API Key未配置。\"}");
      }
   }
}
