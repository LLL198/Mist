package com.una.embyhub.config.common.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.model.dto.response.emby.EmbyTmdbResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExpiringGuavaCacheUtils<K, V> {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(ExpiringGuavaCacheUtils.class);
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private static final String EMBY_DATA_KEY = "EMBY_DATA_KEY";
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;

   public List<EmbyTmdbResponse> getEmbyTmdbResponse() {
      Object cachedObj = this.redisTemplate.opsForValue().get("EMBY_DATA_KEY");
      if (cachedObj != null && cachedObj instanceof List) {
         return (List<EmbyTmdbResponse>)cachedObj;
      } else {
         log.info("缓存未命中，调用 Emby /Items 全量接口拉取数据");
         EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.embyInfoCacheManager.getRequiredConfig();
         String url = config.url() + "Items?IncludeItemTypes=Movie,Series&Recursive=true&Fields=ProviderIds&api_key=" + config.apiKey();
         HttpResponse response = HttpUtil.createGet(url).execute();
         if (response.getStatus() != 200) {
            log.error("请求 Emby API 失败: status={}, body={}", response.getStatus(), response.body());
            return Collections.emptyList();
         } else {
            Map<String, Object> result = JSONObject.parseObject(response.body(), Map.class);
            List<EmbyTmdbResponse> embyTmdbResponseList = JSONArray.parseArray(JSONObject.toJSONString(result.get("Items")), EmbyTmdbResponse.class);
            this.redisTemplate.opsForValue().set("EMBY_DATA_KEY", embyTmdbResponseList, 30L, TimeUnit.MINUTES);
            return embyTmdbResponseList;
         }
      }
   }

   public List<EmbyTmdbResponse> getEmbyTmdbResponseByTmdbId(String tmdbId) {
      return this.getEmbyTmdbResponseByTmdbId(tmdbId, null);
   }

   public List<EmbyTmdbResponse> getEmbyTmdbResponseByTmdbId(String tmdbId, Long embyInfoId) {
      if (tmdbId != null && !tmdbId.isBlank()) {
         EmbyInfoCacheManagerUtils.EmbyServerConfig config = embyInfoId == null
            ? this.embyInfoCacheManager.getRequiredConfig()
            : this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
         String providerPair = "tmdb." + tmdbId.trim();
         String encoded = URLEncoder.encode(providerPair, StandardCharsets.UTF_8);
         String url = config.url()
            + "Items?AnyProviderIdEquals="
            + encoded
            + "&IncludeItemTypes=Movie,Series&Recursive=true&Fields=ProviderIds&api_key="
            + config.apiKey();
         log.info("根据 tmdbId 查询 Emby，embyInfoId={}, url={}", config.id(), url);
         HttpResponse response = HttpUtil.createGet(url).execute();
         if (response.getStatus() != 200) {
            log.error("根据 tmdbId 查询 Emby API 失败: status={}, body={}", response.getStatus(), response.body());
            return Collections.emptyList();
         } else {
            Map<String, Object> result = JSONObject.parseObject(response.body(), Map.class);
            Object items = result.get("Items");
            if (items == null) {
               log.info("根据 tmdbId={} 未在 Emby 中查询到条目", tmdbId);
               return Collections.emptyList();
            } else {
               return JSONArray.parseArray(JSONObject.toJSONString(items), EmbyTmdbResponse.class);
            }
         }
      } else {
         log.warn("getEmbyTmdbResponseByTmdbId 调用时 tmdbId 为空");
         return Collections.emptyList();
      }
   }
}
