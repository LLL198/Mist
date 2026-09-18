package com.una.embyhub.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.RedisLockUtils;
import com.una.embyhub.model.dto.response.emby.EmbyStudioPresetResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class EmbyStudioCacheService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyStudioCacheService.class);
   private static final String CACHE_KEY_PREFIX = "emby:studios:all:v1:";
   private static final String LOCK_KEY_PREFIX = "emby:studios:refreshing:";
   private static final Duration CACHE_TTL = Duration.ofDays(7L);
   private static final long LOCK_TTL_SECONDS = Duration.ofMinutes(30L).toSeconds();
   private static final int PAGE_SIZE = 200;
   private static final int REQUEST_TIMEOUT_MS = 60000;
   private final RedisTemplate<String, Object> redisTemplate;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final RedisLockUtils redisLockUtils;
   private final Set<String> refreshingServers = ConcurrentHashMap.newKeySet();

   public List<EmbyStudioPresetResponse.Studio> getCachedStudios(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      if (!this.isUsableConfig(config)) {
         return Collections.emptyList();
      } else {
         try {
            Object cached = this.redisTemplate.opsForValue().get(this.cacheKey(config));
            return this.normalizeStudios(cached);
         } catch (Exception var3) {
            log.warn("读取 Emby Studio 缓存失败: server={}, error={}", this.serverLabel(config), var3.getMessage());
            return Collections.emptyList();
         }
      }
   }

   public boolean hasCache(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      if (!this.isUsableConfig(config)) {
         return false;
      } else {
         try {
            return Boolean.TRUE.equals(this.redisTemplate.hasKey(this.cacheKey(config)));
         } catch (Exception var3) {
            log.warn("检查 Emby Studio 缓存失败: server={}, error={}", this.serverLabel(config), var3.getMessage());
            return false;
         }
      }
   }

   @Async
   public void refreshIfMissingAsync(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      this.refreshServerSafely(config, true);
   }

   @Async
   public void refreshMissingCachesAsync() {
      this.refreshMissingCaches();
   }

   public void refreshMissingCaches() {
      for (EmbyInfoCacheManagerUtils.EmbyServerConfig config : this.refreshConfigs()) {
         this.refreshServerSafely(config, true);
      }
   }

   public void refreshAllCaches() {
      for (EmbyInfoCacheManagerUtils.EmbyServerConfig config : this.refreshConfigs()) {
         this.refreshServerSafely(config, false);
      }
   }

   private void refreshServerSafely(EmbyInfoCacheManagerUtils.EmbyServerConfig config, boolean onlyIfMissing) {
      if (this.isUsableConfig(config)) {
         if (!onlyIfMissing || !this.hasCache(config)) {
            String identity = this.cacheIdentity(config);
            if (!this.refreshingServers.add(identity)) {
               log.info("Emby Studio 缓存正在刷新中，跳过重复任务: server={}", this.serverLabel(config));
            } else {
               String lockKey = "emby:studios:refreshing:" + identity;
               String lockToken = null;

               try {
                  try {
                     lockToken = this.redisLockUtils.tryLock(lockKey, LOCK_TTL_SECONDS);
                     if (!StringUtils.hasText(lockToken)) {
                        log.info("Emby Studio 缓存已有其他实例刷新中: server={}", this.serverLabel(config));
                        return;
                     }
                  } catch (Exception var11) {
                     log.warn("Emby Studio 缓存 Redis 锁不可用，使用本地锁继续: server={}, error={}", this.serverLabel(config), var11.getMessage());
                  }

                  if (!onlyIfMissing || !this.hasCache(config)) {
                     List<EmbyStudioPresetResponse.Studio> studios = this.fetchAllStudios(config);
                     this.redisTemplate.opsForValue().set(this.cacheKey(config), studios, CACHE_TTL.toSeconds(), TimeUnit.SECONDS);
                     log.info("Emby Studio 缓存刷新完成: server={}, count={}", this.serverLabel(config), studios.size());
                     return;
                  }
               } catch (Exception var12) {
                  log.error("Emby Studio 缓存刷新失败: server={}", this.serverLabel(config), var12);
                  return;
               } finally {
                  if (StringUtils.hasText(lockToken)) {
                     this.redisLockUtils.unlock(lockKey, lockToken);
                  }

                  this.refreshingServers.remove(identity);
               }
            }
         }
      }
   }

   private List<EmbyInfoCacheManagerUtils.EmbyServerConfig> refreshConfigs() {
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> configs = this.embyInfoCacheManager.getEnabledConfigs();
      if (!CollectionUtils.isEmpty(configs)) {
         return configs;
      } else {
         try {
            return List.of(this.embyInfoCacheManager.getRequiredConfig());
         } catch (Exception var3) {
            log.warn("未找到可用 Emby 配置，跳过 Studio 缓存刷新: {}", var3.getMessage());
            return Collections.emptyList();
         }
      }
   }

   private List<EmbyStudioPresetResponse.Studio> fetchAllStudios(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      Map<String, EmbyStudioPresetResponse.Studio> studioById = new LinkedHashMap<>();
      int startIndex = 0;

      Integer total;
      do {
         JSONObject result = this.fetchStudiosPage(config, startIndex);
         JSONArray items = result.getJSONArray("Items");
         if (items == null || items.isEmpty()) {
            break;
         }

         for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            String id = item.getString("Id");
            String name = item.getString("Name");
            if (StringUtils.hasText(id) && StringUtils.hasText(name) && !studioById.containsKey(id)) {
               EmbyStudioPresetResponse.Studio studio = new EmbyStudioPresetResponse.Studio();
               studio.setId(id);
               studio.setName(name);
               studioById.put(id, studio);
            }
         }

         startIndex += items.size();
         total = result.getInteger("TotalRecordCount");
      } while (total != null && startIndex < total);

      return new ArrayList<>(studioById.values());
   }

   private JSONObject fetchStudiosPage(EmbyInfoCacheManagerUtils.EmbyServerConfig config, int startIndex) {
      String url = this.buildBase(config.url(), "/emby/Studios");
      Map<String, Object> params = new LinkedHashMap<>();
      String userId = this.queryUserId(config);
      if (StringUtils.hasText(userId)) {
         params.put("UserId", userId);
      }

      params.put("StartIndex", startIndex);
      params.put("Limit", 200);
      params.put("Recursive", true);
      params.put("EnableImages", false);
      params.put("api_key", config.apiKey());
      params.put("SortBy", "SortName");
      HttpResponse response = HttpRequest.get(url)
         .form(params)
         .header(Header.ACCEPT, "application/json, text/plain, */*")
         .header("X-Emby-Token", config.apiKey())
         .timeout(60000)
         .execute();
      if (!response.isOk()) {
         throw new RuntimeException("获取 Emby Studio 失败: status=" + response.getStatus() + ", body=" + response.body());
      } else {
         return JSON.parseObject(response.body());
      }
   }

   private List<EmbyStudioPresetResponse.Studio> normalizeStudios(Object cached) {
      if (cached instanceof List<?> list) {
         ArrayList studios = new ArrayList();

         for (Object item : list) {
            EmbyStudioPresetResponse.Studio studio = this.toStudio(item);
            if (studio != null && StringUtils.hasText(studio.getId()) && StringUtils.hasText(studio.getName())) {
               studios.add(studio);
            }
         }

         return studios;
      } else {
         return Collections.emptyList();
      }
   }

   private EmbyStudioPresetResponse.Studio toStudio(Object item) {
      if (item instanceof EmbyStudioPresetResponse.Studio) {
         return (EmbyStudioPresetResponse.Studio)item;
      } else if (item instanceof JSONObject object) {
         return this.studioOf(object.getString("id"), object.getString("name"));
      } else if (item instanceof Map<?, ?> map) {
         Object id = this.firstValue(map, "id", "Id");
         Object name = this.firstValue(map, "name", "Name");
         return this.studioOf(id == null ? null : id.toString(), name == null ? null : name.toString());
      } else {
         return null;
      }
   }

   private Object firstValue(Map<?, ?> map, String lowerKey, String upperKey) {
      Object value = map.get(lowerKey);
      return value != null ? value : map.get(upperKey);
   }

   private EmbyStudioPresetResponse.Studio studioOf(String id, String name) {
      if (StringUtils.hasText(id) && StringUtils.hasText(name)) {
         EmbyStudioPresetResponse.Studio studio = new EmbyStudioPresetResponse.Studio();
         studio.setId(id);
         studio.setName(name);
         return studio;
      } else {
         return null;
      }
   }

   private String queryUserId(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      return StringUtils.hasText(config.adminQueryUserid()) ? config.adminQueryUserid() : config.copyfromuserid();
   }

   private String cacheKey(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      return "emby:studios:all:v1:" + this.cacheIdentity(config);
   }

   private String cacheIdentity(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      if (config.id() != null) {
         return String.valueOf(config.id());
      } else {
         return StringUtils.hasText(config.url()) ? Integer.toHexString(config.url().hashCode()) : "default";
      }
   }

   private String serverLabel(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      if (config == null) {
         return "unknown";
      } else {
         return StringUtils.hasText(config.serverName()) ? config.serverName() : this.cacheIdentity(config);
      }
   }

   private boolean isUsableConfig(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      return config != null && StringUtils.hasText(config.url()) && StringUtils.hasText(config.apiKey());
   }

   private String buildBase(String baseUrl, String path) {
      String base = StrUtil.removeSuffix(baseUrl, "/");
      if (!path.startsWith("/")) {
         path = "/" + path;
      }

      if (base.toLowerCase().endsWith("/emby") && path.toLowerCase().startsWith("/emby/")) {
         path = path.substring("/emby".length());
      }

      return base + path;
   }

   @Generated
   public EmbyStudioCacheService(
      final RedisTemplate<String, Object> redisTemplate, final EmbyInfoCacheManagerUtils embyInfoCacheManager, final RedisLockUtils redisLockUtils
   ) {
      this.redisTemplate = redisTemplate;
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.redisLockUtils = redisLockUtils;
   }
}
