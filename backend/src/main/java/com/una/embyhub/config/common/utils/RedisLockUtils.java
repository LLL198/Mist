package com.una.embyhub.config.common.utils;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLockUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(RedisLockUtils.class);
   private final RedisTemplate<String, Object> redisTemplate;
   private static final DefaultRedisScript<Long> UNLOCK_SCRIPT = new DefaultRedisScript<>(
      "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class
   );

   public String tryLock(String key, long ttlSeconds) {
      if (StringUtils.hasText(key) && ttlSeconds > 0L) {
         String token = UUID.randomUUID().toString();

         try {
            Boolean locked = this.redisTemplate.opsForValue().setIfAbsent(key, token, ttlSeconds, TimeUnit.SECONDS);
            return Boolean.TRUE.equals(locked) ? token : null;
         } catch (Exception var6) {
            log.warn("Redis 锁写入失败: key={}", key, var6);
            throw var6;
         }
      } else {
         return null;
      }
   }

   public void unlock(String key, String token) {
      if (StringUtils.hasText(key) && StringUtils.hasText(token)) {
         try {
            this.redisTemplate.execute(UNLOCK_SCRIPT, Collections.singletonList(key), token);
         } catch (Exception var4) {
            log.warn("Redis 锁释放失败: key={}", key, var4);
         }
      }
   }

   @Generated
   public RedisLockUtils(final RedisTemplate<String, Object> redisTemplate) {
      this.redisTemplate = redisTemplate;
   }
}
