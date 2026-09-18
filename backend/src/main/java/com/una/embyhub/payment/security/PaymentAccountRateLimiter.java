package com.una.embyhub.payment.security;

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
public class PaymentAccountRateLimiter {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PaymentAccountRateLimiter.class);
   private static final String KEY_PREFIX = "foam:payment-account-rate:v1:";
   private static final DefaultRedisScript<Long> LIMIT_SCRIPT = new DefaultRedisScript<>(
      "local count = redis.call('INCR', KEYS[1])\nlocal ttl = redis.call('PTTL', KEYS[1])\nif count == 1 or ttl < 0 then\n    redis.call('PEXPIRE', KEYS[1], ARGV[1])\nend\nreturn count\n",
      Long.class
   );
   private final StringRedisTemplate redisTemplate;

   public void checkCreate(String clientIp) {
      this.check("create", clientIp, 5, Duration.ofMinutes(10L), "创建支付订单过于频繁");
   }

   public void checkLookup(String clientIp) {
      this.check("lookup", clientIp, 120, Duration.ofMinutes(1L), "订单查询过于频繁");
   }

   private void check(String scope, String clientIp, int limit, Duration window, String message) {
      String ip = StringUtils.hasText(clientIp) ? clientIp.trim() : "unknown";

      try {
         Long count = this.redisTemplate
            .execute(
               LIMIT_SCRIPT, List.of("foam:payment-account-rate:v1:" + scope + ":" + DigestUtil.sha256Hex(ip)), new Object[]{String.valueOf(window.toMillis())}
            );
         if (count == null) {
            throw this.unavailable(null);
         } else if (count > (long)limit) {
            throw new RateLimitException(message + "，请稍后再试", window.toSeconds());
         }
      } catch (BizException var8) {
         throw var8;
      } catch (RuntimeException var9) {
         throw this.unavailable(var9);
      }
   }

   private BizException unavailable(RuntimeException cause) {
      if (cause != null) {
         log.warn("支付公开入口限流不可用，已拒绝请求: error={}", cause.getMessage());
      }

      return new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "支付保护暂时不可用，请稍后重试");
   }

   @Generated
   public PaymentAccountRateLimiter(final StringRedisTemplate redisTemplate) {
      this.redisTemplate = redisTemplate;
   }
}
