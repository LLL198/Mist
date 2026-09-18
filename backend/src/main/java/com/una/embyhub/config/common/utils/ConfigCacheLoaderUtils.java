package com.una.embyhub.config.common.utils;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.service.SystemConfigService;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConfigCacheLoaderUtils {
   @Autowired
   private SystemConfigService systemConfigService;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   private static final String REDIS_KEY = "system:config";

   @PostConstruct
   public void init() {
      this.loadConfigCache();
   }

   public void loadConfigCache() {
      List<SystemConfig> systemConfigList = new LambdaQueryChainWrapper<>(this.systemConfigService.getBaseMapper())
         .eq(SystemConfig::getIsEnabled, Integer.valueOf(1))
         .list();
      Map<String, String> tempCache = new HashMap<>();
      systemConfigList.forEach(config -> tempCache.put(config.getConfigKey(), config.getConfigValue()));
      this.redisTemplate.delete("system:config");
      if (!tempCache.isEmpty()) {
         this.redisTemplate.<String, String>opsForHash().putAll("system:config", tempCache);
      }
   }

   public String getConfigValue(String key) {
      Object value = this.redisTemplate.opsForHash().get("system:config", key);
      return value != null ? value.toString() : null;
   }

   public Map<String, String> getAllConfigs() {
      Map<Object, Object> entries = this.redisTemplate.<Object, Object>opsForHash().entries("system:config");
      Map<String, String> result = new HashMap<>();
      entries.forEach((k, v) -> result.put(k.toString(), v.toString()));
      return result;
   }

   public void refreshCache() {
      this.loadConfigCache();
   }
}
