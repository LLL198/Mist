package com.una.embyhub.service;

import com.una.embyhub.config.common.config.EmbyLibraryAccessRateLimitProperties;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.exception.RateLimitException;
import java.time.Duration;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
public class EmbyLibraryAccessMutationRateLimiter {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyLibraryAccessMutationRateLimiter.class);
   private static final String KEY_PREFIX = "foam:emby-library-access:mutation:v1:";
   private static final Duration DEFAULT_WINDOW = Duration.ofMinutes(1L);
   private static final DefaultRedisScript<Long> LIMIT_SCRIPT = new DefaultRedisScript<>(
      "local count = redis.call('INCR', KEYS[1])\nlocal ttl = redis.call('PTTL', KEYS[1])\nif count == 1 or ttl < 0 then\n    redis.call('PEXPIRE', KEYS[1], ARGV[1])\nend\nreturn count\n",
      Long.class
   );
   private final StringRedisTemplate redisTemplate;
   private final EmbyLibraryAccessRateLimitProperties properties;

   public void checkWebGlobal(long operatorId) {
      this.check("web", "global", operatorId, this.properties.getMaxGlobalMutations());
   }

   public void checkWebUser(long operatorId) {
      this.check("web", "user", operatorId, this.properties.getMaxUserMutations());
   }

   public void checkTelegramGlobal(long operatorId) {
      this.check("telegram", "global", operatorId, this.properties.getMaxGlobalMutations());
   }

   public void checkTelegramUser(long operatorId) {
      this.check("telegram", "user", operatorId, this.properties.getMaxUserMutations());
   }

   private void check(String channel, String scope, long operatorId, int configuredLimit) {
      if (operatorId <= 0L) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      } else {
         Duration window = this.positiveDuration(this.properties.getWindow(), DEFAULT_WINDOW);
         int limit = Math.max(1, configuredLimit);
         String key = "foam:emby-library-access:mutation:v1:" + channel + ":" + scope + ":" + operatorId;

         try {
            Long count = this.redisTemplate.execute(LIMIT_SCRIPT, List.of(key), new Object[]{String.valueOf(window.toMillis())});
            if (count == null) {
               throw this.protectionUnavailable(null);
            } else if (count > (long)limit) {
               throw new RateLimitException("媒体库权限设置过于频繁，请稍后再试", window.toSeconds());
            }
         } catch (BizException var10) {
            throw var10;
         } catch (RuntimeException var11) {
            throw this.protectionUnavailable(var11);
         }
      }
   }

   private Duration positiveDuration(Duration value, Duration fallback) {
      return value != null && !value.isZero() && !value.isNegative() ? value : fallback;
   }

   private BizException protectionUnavailable(RuntimeException cause) {
      if (cause != null) {
         log.warn("媒体库分级限流不可用，已拒绝写操作: error={}", cause.getMessage());
      }

      return new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "媒体库权限保护暂时不可用，请稍后重试");
   }

   @Generated
   public EmbyLibraryAccessMutationRateLimiter(final StringRedisTemplate redisTemplate, final EmbyLibraryAccessRateLimitProperties properties) {
      this.redisTemplate = redisTemplate;
      this.properties = properties;
   }
}
