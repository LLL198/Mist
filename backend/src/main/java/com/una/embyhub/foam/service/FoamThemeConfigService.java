package com.una.embyhub.foam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FoamThemeConfigService {
   private static final String REDIS_KEY_PREFIX = "foam:theme:config:v1:";
   private final StringRedisTemplate stringRedisTemplate;
   private final ObjectMapper objectMapper;

   public JsonNode create(String key, JsonNode data) {
      String redisKey = this.buildKey(key);
      this.validateData(data);
      if (Boolean.TRUE.equals(this.stringRedisTemplate.hasKey(redisKey))) {
         throw new IllegalArgumentException("主题配置已存在，请使用更新接口，key: " + key);
      } else {
         this.write(redisKey, data, key);
         return data;
      }
   }

   public JsonNode get(String key) {
      String redisKey = this.buildKey(key);
      String json = this.stringRedisTemplate.opsForValue().get(redisKey);
      if (!StringUtils.hasText(json)) {
         throw new IllegalArgumentException("主题配置不存在，key: " + key);
      } else {
         return this.read(json, key);
      }
   }

   public JsonNode update(String key, JsonNode data) {
      String redisKey = this.buildKey(key);
      this.validateData(data);
      if (!Boolean.TRUE.equals(this.stringRedisTemplate.hasKey(redisKey))) {
         throw new IllegalArgumentException("主题配置不存在，无法更新，key: " + key);
      } else {
         this.write(redisKey, data, key);
         return data;
      }
   }

   public void delete(String key) {
      String redisKey = this.buildKey(key);
      Boolean deleted = this.stringRedisTemplate.delete(redisKey);
      if (!Boolean.TRUE.equals(deleted)) {
         throw new IllegalArgumentException("主题配置不存在，无法删除，key: " + key);
      }
   }

   public List<String> keys() {
      Set<String> keys = this.stringRedisTemplate.keys("foam:theme:config:v1:*");
      return CollectionUtils.isEmpty(keys)
         ? Collections.emptyList()
         : keys.stream().map(key -> key.substring("foam:theme:config:v1:".length())).sorted().collect(Collectors.toList());
   }

   private String buildKey(String key) {
      if (!StringUtils.hasText(key)) {
         throw new IllegalArgumentException("key 不能为空");
      } else {
         return "foam:theme:config:v1:" + key;
      }
   }

   private void validateData(JsonNode data) {
      if (data == null || data.isNull() || !data.isObject()) {
         throw new IllegalArgumentException("主题配置 data 必须是 JSON 对象");
      }
   }

   private JsonNode read(String json, String key) {
      try {
         return this.objectMapper.readTree(json);
      } catch (JsonProcessingException var4) {
         throw new IllegalStateException("读取 Redis 中主题配置失败，key: " + key, var4);
      }
   }

   private void write(String redisKey, JsonNode data, String key) {
      try {
         this.stringRedisTemplate.opsForValue().set(redisKey, this.objectMapper.writeValueAsString(data));
      } catch (JsonProcessingException var5) {
         throw new IllegalArgumentException("主题配置 JSON 序列化失败，key: " + key, var5);
      }
   }

   @Generated
   public FoamThemeConfigService(final StringRedisTemplate stringRedisTemplate, final ObjectMapper objectMapper) {
      this.stringRedisTemplate = stringRedisTemplate;
      this.objectMapper = objectMapper;
   }
}
