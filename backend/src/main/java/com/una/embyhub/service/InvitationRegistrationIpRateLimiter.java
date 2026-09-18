package com.una.embyhub.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.una.embyhub.config.common.config.InvitationRegistrationRateLimitProperties;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.exception.RateLimitException;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

@Service
public class InvitationRegistrationIpRateLimiter {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(InvitationRegistrationIpRateLimiter.class);
   private static final String KEY_PREFIX = "foam:register:invitation-ip:v1:";
   private static final DefaultRedisScript<Long> ATTEMPT_SCRIPT = new DefaultRedisScript<>(
      "local count = redis.call('INCR', KEYS[1])\nlocal ttl = redis.call('PTTL', KEYS[1])\nif count == 1 or ttl < 0 then\n    redis.call('PEXPIRE', KEYS[1], ARGV[1])\nend\nreturn count\n",
      Long.class
   );
   private static final DefaultRedisScript<Long> RESERVE_SCRIPT = new DefaultRedisScript<>(
      "local now = tonumber(ARGV[1])\nlocal dailyWindow = tonumber(ARGV[2])\nlocal dailyLimit = tonumber(ARGV[3])\nlocal token = ARGV[4]\nlocal reservationTtl = tonumber(ARGV[5])\nlocal targetTtl = tonumber(ARGV[6])\n\nredis.call('ZREMRANGEBYSCORE', KEYS[3], '-inf', now - dailyWindow)\nif redis.call('ZCARD', KEYS[3]) >= dailyLimit then\n    return -2\nend\nif redis.call('EXISTS', KEYS[2]) == 1 then\n    return -3\nend\nif redis.call('EXISTS', KEYS[1]) == 1 then\n    return -1\nend\n\nredis.call('SET', KEYS[2], token, 'PX', reservationTtl)\nredis.call('SET', KEYS[1], token, 'PX', targetTtl)\nreturn 1\n",
      Long.class
   );
   private static final DefaultRedisScript<Long> CONFIRM_SCRIPT = new DefaultRedisScript<>(
      "local token = ARGV[1]\nif redis.call('GET', KEYS[1]) ~= token then\n    return 0\nend\n\nredis.call('SET', KEYS[1], 'SUCCESS:' .. token, 'PX', ARGV[2])\nredis.call('ZADD', KEYS[3], ARGV[3], token)\nredis.call('PEXPIRE', KEYS[3], tonumber(ARGV[4]) + 60000)\nif redis.call('GET', KEYS[2]) == token then\n    redis.call('DEL', KEYS[2])\nend\nreturn 1\n",
      Long.class
   );
   private static final DefaultRedisScript<Long> RELEASE_SCRIPT = new DefaultRedisScript<>(
      "local token = ARGV[1]\nlocal released = 0\nif redis.call('GET', KEYS[1]) == token then\n    redis.call('DEL', KEYS[1])\n    released = released + 1\nend\nif redis.call('GET', KEYS[2]) == token then\n    redis.call('DEL', KEYS[2])\n    released = released + 1\nend\nreturn released\n",
      Long.class
   );
   private final StringRedisTemplate redisTemplate;
   private final InvitationRegistrationRateLimitProperties properties;

   public void checkAttempt(String clientIp) {
      Duration window = this.positiveDuration(this.properties.getAttemptWindow(), Duration.ofMinutes(10L));
      int maxAttempts = Math.max(1, this.properties.getMaxAttempts());
      String key = "foam:register:invitation-ip:v1:attempt:" + this.hash(this.normalizeIp(clientIp));

      try {
         Long count = this.redisTemplate.execute(ATTEMPT_SCRIPT, List.of(key), new Object[]{String.valueOf(window.toMillis())});
         if (count == null) {
            throw this.protectionUnavailable(null);
         } else if (count > (long)maxAttempts) {
            throw new RateLimitException("注册请求过于频繁，请" + this.durationText(window) + "后再试", window.toSeconds());
         }
      } catch (BizException var6) {
         throw var6;
      } catch (RuntimeException var7) {
         throw this.protectionUnavailable(var7);
      }
   }

   public InvitationRegistrationIpRateLimiter.Reservation reserve(String invitationCode, String clientIp) {
      Duration cooldown = this.positiveDuration(this.properties.getSameInvitationCooldown(), Duration.ofDays(1L));
      return this.reserveRegistration(
         this.hash(this.normalizeInvitationCode(invitationCode)), clientIp, cooldown, "当前网络" + this.durationText(cooldown) + "内已经使用过该邀请码"
      );
   }

   public InvitationRegistrationIpRateLimiter.Reservation reserveOpenRegistration(String clientIp) {
      Duration cooldown = this.positiveDuration(this.properties.getOpenRegistrationCooldown(), Duration.ofDays(1L));
      return this.reserveRegistration(this.hash("scope:open-registration"), clientIp, cooldown, "当前网络" + this.durationText(cooldown) + "内已经完成过开放注册");
   }

   private InvitationRegistrationIpRateLimiter.Reservation reserveRegistration(
      String registrationTargetHash, String clientIp, Duration cooldown, String duplicateMessage
   ) {
      Duration dailyWindow = this.positiveDuration(this.properties.getDailySuccessWindow(), Duration.ofDays(1L));
      Duration reservationTimeout = this.positiveDuration(this.properties.getReservationTimeout(), Duration.ofMinutes(5L));
      int dailyLimit = Math.max(1, this.properties.getDailySuccessLimit());
      String ipHash = this.hash(this.normalizeIp(clientIp));
      String registrationKey = "foam:register:invitation-ip:v1:success:" + registrationTargetHash + ":" + ipHash;
      String inFlightKey = "foam:register:invitation-ip:v1:inflight:" + ipHash;
      String dailySuccessKey = "foam:register:invitation-ip:v1:daily-success:" + ipHash;
      String token = UUID.randomUUID().toString();
      InvitationRegistrationIpRateLimiter.Reservation reservation = new InvitationRegistrationIpRateLimiter.Reservation(
         registrationKey, inFlightKey, dailySuccessKey, token, cooldown, dailyWindow
      );

      try {
         Long result = this.redisTemplate
            .execute(
               RESERVE_SCRIPT,
               reservation.keys(),
               new Object[]{
                  String.valueOf(System.currentTimeMillis()),
                  String.valueOf(dailyWindow.toMillis()),
                  String.valueOf(dailyLimit),
                  token,
                  String.valueOf(reservationTimeout.toMillis()),
                  String.valueOf(cooldown.toMillis())
               }
            );
         if (result == null) {
            throw this.protectionUnavailable(null);
         } else if (result == -1L) {
            throw new RateLimitException(duplicateMessage, cooldown.toSeconds());
         } else if (result == -2L) {
            throw new RateLimitException("当前网络" + this.durationText(dailyWindow) + "内注册账号数量已达上限", dailyWindow.toSeconds());
         } else if (result == -3L) {
            throw new RateLimitException("当前网络已有注册请求正在处理中，请稍后再试", reservationTimeout.toSeconds());
         } else if (result != 1L) {
            throw this.protectionUnavailable(null);
         } else {
            return reservation;
         }
      } catch (BizException var15) {
         throw var15;
      } catch (RuntimeException var16) {
         throw this.protectionUnavailable(var16);
      }
   }

   public void completeAfterCommit(InvitationRegistrationIpRateLimiter.Reservation reservation) {
      if (reservation != null) {
         if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            this.confirm(reservation);
         } else {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
               @Override
               public void afterCommit() {
                  InvitationRegistrationIpRateLimiter.this.confirm(reservation);
               }

               @Override
               public void afterCompletion(int status) {
                  if (status != 0) {
                     InvitationRegistrationIpRateLimiter.this.release(reservation);
                  }
               }
            });
         }
      }
   }

   public void release(InvitationRegistrationIpRateLimiter.Reservation reservation) {
      if (reservation != null) {
         try {
            this.redisTemplate.execute(RELEASE_SCRIPT, reservation.keys().subList(0, 2), new Object[]{reservation.token()});
         } catch (RuntimeException var3) {
            log.warn("释放公开注册 IP 占位失败: error={}", var3.getMessage());
         }
      }
   }

   private void confirm(InvitationRegistrationIpRateLimiter.Reservation reservation) {
      try {
         Long result = this.redisTemplate
            .execute(
               CONFIRM_SCRIPT,
               reservation.keys(),
               new Object[]{
                  reservation.token(),
                  String.valueOf(reservation.cooldown().toMillis()),
                  String.valueOf(System.currentTimeMillis()),
                  String.valueOf(reservation.dailyWindow().toMillis())
               }
            );
         if (!Long.valueOf(1L).equals(result)) {
            log.warn("确认公开注册 IP 配额失败，占位将在冷却期后自动释放");
         }
      } catch (RuntimeException var3) {
         log.warn("确认公开注册 IP 配额异常，保留原占位继续保护: error={}", var3.getMessage());
      }
   }

   private BizException protectionUnavailable(RuntimeException cause) {
      if (cause != null) {
         log.warn("公开注册 IP 流量保护不可用: error={}", cause.getMessage());
      }

      return new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "注册流量保护暂不可用，请稍后再试");
   }

   private String normalizeIp(String clientIp) {
      return StringUtils.hasText(clientIp) ? clientIp.trim().toLowerCase(Locale.ROOT) : "unknown";
   }

   private String normalizeInvitationCode(String invitationCode) {
      return StringUtils.hasText(invitationCode) ? invitationCode.trim().toLowerCase(Locale.ROOT) : "unknown";
   }

   private String hash(String value) {
      return DigestUtil.sha256Hex(value);
   }

   private Duration positiveDuration(Duration value, Duration fallback) {
      return value != null && !value.isZero() && !value.isNegative() ? value : fallback;
   }

   private String durationText(Duration duration) {
      if (duration.toHours() > 24L && duration.toHours() % 24L == 0L) {
         return duration.toDays() + "天";
      } else if (duration.toMinutes() >= 1L && duration.toMinutes() % 60L != 0L) {
         return duration.toMinutes() + "分钟";
      } else {
         return duration.toHours() >= 1L ? duration.toHours() + "小时" : Math.max(1L, duration.toSeconds()) + "秒";
      }
   }

   @Generated
   public InvitationRegistrationIpRateLimiter(final StringRedisTemplate redisTemplate, final InvitationRegistrationRateLimitProperties properties) {
      this.redisTemplate = redisTemplate;
      this.properties = properties;
   }

   public static record Reservation(String registrationKey, String inFlightKey, String dailySuccessKey, String token, Duration cooldown, Duration dailyWindow) {
      List<String> keys() {
         return List.of(this.registrationKey, this.inFlightKey, this.dailySuccessKey);
      }
   }
}
