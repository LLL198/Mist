package com.una.embyhub.component;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.una.embyhub.config.common.utils.PlaybackReportingLocationUtils;
import com.una.embyhub.mapper.EmbyIpLocationsMapper;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationMapResponse;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MapSummaryCache implements CommandLineRunner {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MapSummaryCache.class);
   @Autowired
   private EmbyIpLocationsMapper embyIpLocationsMapper;
   private static final String NULL_GEO_VALUE = "__NULL__";
   private static final long GEO_SUCCESS_TTL_MS = TimeUnit.HOURS.toMillis(12L);
   private static final long GEO_FAILURE_TTL_MS = TimeUnit.MINUTES.toMillis(5L);
   private List<EmbyIpLocationMapResponse> cachedGlobalSummary = Collections.emptyList();
   private final ConcurrentHashMap<String, MapSummaryCache.GeocodingCacheEntry> geocodingCache = new ConcurrentHashMap<>();
   private final ReentrantLock refreshLock = new ReentrantLock();

   @Override
   public void run(String... args) throws Exception {
      CompletableFuture.runAsync(this::refresh);
   }

   public void refresh() {
      if (!this.refreshLock.tryLock()) {
         log.debug("IP位置地图汇总缓存刷新已在执行，忽略重复触发");
      } else {
         log.info("正在刷新 IP位置地图汇总缓存...");

         try {
            List<EmbyIpLocationMapResponse> list = this.embyIpLocationsMapper.selectMapSummary(null);
            if (list == null) {
               this.cachedGlobalSummary = Collections.emptyList();
            } else {
               for (EmbyIpLocationMapResponse item : list) {
                  this.applyPlaybackReportingLocation(item);
                  this.processGeocoding(item);
               }

               this.cachedGlobalSummary = list;
            }

            log.info("IP位置地图汇总缓存刷新完成，记录数: {}", this.cachedGlobalSummary.size());
         } catch (Exception var7) {
            log.error("刷新 IP位置地图汇总缓存失败", (Throwable)var7);
         } finally {
            this.refreshLock.unlock();
         }
      }
   }

   public List<EmbyIpLocationMapResponse> getGlobalSummary() {
      return this.cachedGlobalSummary;
   }

   private void applyPlaybackReportingLocation(EmbyIpLocationMapResponse item) {
      PlaybackReportingLocationUtils.LocationParts parts = PlaybackReportingLocationUtils.parse(item.getName());
      item.setCountry(parts.country());
      item.setRegion(parts.region());
      item.setCity(parts.city());
      item.setName(parts.displayName());
   }

   public void processGeocoding(EmbyIpLocationMapResponse item) {
      String addressName = this.constructAddressName(item);
      if (StringUtils.hasText(addressName)) {
         String geocodingJson = this.fetchGeocodingWithCache(addressName);
         if (StringUtils.hasText(geocodingJson)) {
            item.setGeocoding(geocodingJson);
         }
      }
   }

   private String constructAddressName(EmbyIpLocationMapResponse item) {
      StringBuilder sb = new StringBuilder();
      if (StringUtils.hasText(item.getCity())) {
         sb.append(item.getCity());
      } else if (StringUtils.hasText(item.getRegion())) {
         sb.append(item.getRegion());
      } else if (StringUtils.hasText(item.getName())) {
         sb.append(item.getName());
      }

      if (StringUtils.hasText(item.getCountry())) {
         if (sb.length() > 0) {
            sb.append(", ");
         }

         sb.append(item.getCountry());
      }

      return sb.toString();
   }

   private String fetchGeocodingFromApi(String name) {
      try {
         String baseUrl = "https://geocoding-api.open-meteo.com/v1/search";
         String fullUrl = baseUrl + "?name=" + URLUtil.encode(name) + "&count=1&language=zh&format=json";
         return HttpUtil.createGet(fullUrl)
            .header("Accept", "*/*")
            .header("Accept-Language", "zh-CN,zh;q=0.9")
            .header("Connection", "keep-alive")
            .header("Accept-Encoding", "identity")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
            .timeout(5000)
            .execute()
            .body();
      } catch (Exception var4) {
         log.error("调用 Geocoding API 失败: name={}", name, var4);
         return null;
      }
   }

   private String fetchGeocodingWithCache(String addressName) {
      long now = System.currentTimeMillis();
      MapSummaryCache.GeocodingCacheEntry cached = this.geocodingCache.get(addressName);
      if (cached != null && cached.expireAt > now) {
         return "__NULL__".equals(cached.payload) ? null : cached.payload;
      } else {
         String geocodingJson = this.fetchGeocodingFromApi(addressName);
         long ttl = StringUtils.hasText(geocodingJson) ? GEO_SUCCESS_TTL_MS : GEO_FAILURE_TTL_MS;
         this.geocodingCache
            .put(addressName, new MapSummaryCache.GeocodingCacheEntry(StringUtils.hasText(geocodingJson) ? geocodingJson : "__NULL__", now + ttl));
         return geocodingJson;
      }
   }

   private static record GeocodingCacheEntry(String payload, long expireAt) {
   }
}
