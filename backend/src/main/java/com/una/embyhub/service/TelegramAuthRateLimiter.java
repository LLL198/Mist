package com.una.embyhub.service;

import cn.hutool.crypto.digest.DigestUtil;
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
import org.springframework.util.StringUtils;

@Service
public class TelegramAuthRateLimiter {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramAuthRateLimiter.class);
   private static final String KEY_PREFIX = "foam:telegram-auth-rate:v1:";
   private static final int SESSION_LIMIT = 10;
   private static final Duration SESSION_WINDOW = Duration.ofMinutes(5L);
   private static final int POLL_LIMIT = 120;
   private static final Duration POLL_WINDOW = Duration.ofMinutes(1L);
   private static final int LOGIN_LIMIT = 30;
   private static final Duration LOGIN_WINDOW = Duration.ofMinutes(5L);
   private static final DefaultRedisScript<Long> LIMIT_SCRIPT = new DefaultRedisScript<>(
      "local count = redis.call('INCR', KEYS[1])\nlocal ttl = redis.call('PTTL', KEYS[1])\nif count == 1 or ttl < 0 then\n    redis.call('PEXPIRE', KEYS[1], ARGV[1])\nend\nreturn count\n",
      Long.class
   );
   private final StringRedisTemplate redisTemplate;

   public void checkLoginSessionCreation(String clientIp) {
      this.check("session", clientIp, 10, SESSION_WINDOW, "Telegram 登录会话创建过于频繁");
   }

   public void checkLoginPoll(String clientIp) {
      this.check("poll", clientIp, 120, POLL_WINDOW, "Telegram 登录状态查询过于频繁");
   }

   public void checkLoginAttempt(String clientIp) {
      this.check("login", clientIp, 30, LOGIN_WINDOW, "Telegram 登录尝试过于频繁");
   }

   private void check(String scope, String clientIp, int limit, Duration window, String message) {
      String ip = StringUtils.hasText(clientIp) ? clientIp.trim() : "unknown";
      String key = "foam:telegram-auth-rate:v1:" + scope + ":" + DigestUtil.sha256Hex(ip);

      try {
         Long count = this.redisTemplate.execute(LIMIT_SCRIPT, List.of(key), new Object[]{String.valueOf(window.toMillis())});
         if (count == null) {
            throw this.protectionUnavailable(null);
         } else if (count > (long)limit) {
            throw new RateLimitException(message + "，请稍后再试", window.toSeconds());
         }
      } catch (BizException var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw this.protectionUnavailable(var10);
      }
   }

   private BizException protectionUnavailable(RuntimeException cause) {
      if (cause != null) {
         log.warn("Telegram 认证限流不可用，已拒绝公开认证请求: error={}", cause.getMessage());
      }

      return new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "Telegram 登录保护暂时不可用，请稍后重试");
   }

   @Generated
   public TelegramAuthRateLimiter(final StringRedisTemplate redisTemplate) {
      this.redisTemplate = redisTemplate;
   }
}
