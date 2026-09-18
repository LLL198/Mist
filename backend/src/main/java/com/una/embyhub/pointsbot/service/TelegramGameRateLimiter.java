package com.una.embyhub.pointsbot.service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
public class TelegramGameRateLimiter {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramGameRateLimiter.class);
   static final int GROUP_GAME_MESSAGE_BUDGET = 12;
   static final long GROUP_WINDOW_MILLIS = Duration.ofMinutes(1L).toMillis();
   static final long USER_COOLDOWN_MILLIS = Duration.ofSeconds(5L).toMillis();
   static final long NOTICE_COOLDOWN_SECONDS = 30L;
   static final long OUTBOUND_INTERVAL_MILLIS = 1100L;
   static final long MAX_OUTBOUND_WAIT_MILLIS = 15000L;
   static final int GROUP_ACTUAL_MESSAGE_LIMIT = 12;
   private static final String KEY_PREFIX = "foam:points-bot:telegram-game-rate:v1:";
   private static final DefaultRedisScript<List> ADMISSION_SCRIPT = new DefaultRedisScript<>(
      "local time = redis.call('TIME')\nlocal now = (tonumber(time[1]) * 1000) + math.floor(tonumber(time[2]) / 1000)\nlocal window = tonumber(ARGV[1])\nlocal budget = tonumber(ARGV[2])\nlocal cost = tonumber(ARGV[3])\nlocal cooldown = tonumber(ARGV[4])\nlocal requestId = ARGV[5]\n\nredis.call('ZREMRANGEBYSCORE', KEYS[1], '-inf', now - window)\n\nlocal userTtl = redis.call('PTTL', KEYS[2])\nif userTtl > 0 then\n    return {0, math.max(1, math.ceil(userTtl / 1000)), 2}\nend\n\nlocal used = redis.call('ZCARD', KEYS[1])\nif used + cost > budget then\n    local unitsToExpire = used + cost - budget\n    local oldest = redis.call('ZRANGE', KEYS[1], unitsToExpire - 1, unitsToExpire - 1, 'WITHSCORES')\n    local retryAfter = 1\n    if oldest[2] then\n        retryAfter = math.max(1, math.ceil((tonumber(oldest[2]) + window - now) / 1000))\n    end\n    return {0, retryAfter, 1}\nend\n\nfor index = 1, cost do\n    redis.call('ZADD', KEYS[1], now, requestId .. ':' .. index)\nend\nredis.call('PEXPIRE', KEYS[1], window + 1000)\nredis.call('SET', KEYS[2], '1', 'PX', cooldown)\nreturn {1, 0, 0}\n",
      List.class
   );
   private static final DefaultRedisScript<Long> OUTBOUND_SLOT_SCRIPT = new DefaultRedisScript<>(
      "local time = redis.call('TIME')\nlocal now = (tonumber(time[1]) * 1000) + math.floor(tonumber(time[2]) / 1000)\nlocal interval = tonumber(ARGV[1])\nlocal maxWait = tonumber(ARGV[2])\nlocal window = tonumber(ARGV[3])\nlocal messageLimit = tonumber(ARGV[4])\nlocal requestId = ARGV[5]\nlocal countActualMessage = ARGV[6] == '1'\nlocal nextSlot = tonumber(redis.call('GET', KEYS[1]) or '0')\nlocal slot = math.max(now, nextSlot)\nlocal wait = slot - now\nif wait > maxWait then\n    return -1\nend\n\nif countActualMessage then\n    redis.call('ZREMRANGEBYSCORE', KEYS[2], '-inf', now - window)\n    if redis.call('ZCARD', KEYS[2]) >= messageLimit then\n        return -2\n    end\n    redis.call('ZADD', KEYS[2], slot, requestId)\n    redis.call('PEXPIRE', KEYS[2], window + wait + interval)\nend\n\nlocal followingSlot = slot + interval\nredis.call('SET', KEYS[1], followingSlot, 'PX', followingSlot - now + interval)\nreturn wait\n",
      Long.class
   );
   private final StringRedisTemplate stringRedisTemplate;
   private final ConcurrentHashMap<Long, AtomicLong> localNextOutboundAt = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<Long, Long> localNoticeExpiresAt = new ConcurrentHashMap<>();

   public TelegramGameRateLimiter.AdmissionDecision tryAcquireGame(long chatId, long userId, String gameCommand) {
      int cost = this.messageCost(gameCommand);

      try {
         List<Object> result = this.stringRedisTemplate
            .execute(
               ADMISSION_SCRIPT,
               List.of(this.groupBudgetKey(chatId), this.userCooldownKey(chatId, userId)),
               new Object[]{
                  String.valueOf(GROUP_WINDOW_MILLIS),
                  String.valueOf(12),
                  String.valueOf(cost),
                  String.valueOf(USER_COOLDOWN_MILLIS),
                  UUID.randomUUID().toString()
               }
            );
         return this.mapAdmissionResult(result);
      } catch (RuntimeException var8) {
         log.warn("Redis 游戏限流不可用，已暂停新游戏: chatId={}, error={}", chatId, var8.getMessage());
         return TelegramGameRateLimiter.AdmissionDecision.unavailable();
      }
   }

   public boolean tryAcquireAction(long chatId, long userId) {
      try {
         return Boolean.TRUE.equals(this.stringRedisTemplate.opsForValue().setIfAbsent(this.actionCooldownKey(chatId, userId), "1", Duration.ofMillis(1100L)));
      } catch (RuntimeException var6) {
         log.warn("Redis 游戏按钮限流不可用，已拦截按钮操作: chatId={}, userId={}, error={}", chatId, userId, var6.getMessage());
         return false;
      }
   }

   public void awaitMessageTurn(long chatId) {
      this.awaitMessageTurn(chatId, true);
   }

   public void awaitLimitNoticeTurn(long chatId) {
      this.awaitMessageTurn(chatId, false);
   }

   private void awaitMessageTurn(long chatId, boolean countActualMessage) {
      long waitMillis;
      try {
         Long result = this.stringRedisTemplate
            .execute(
               OUTBOUND_SLOT_SCRIPT,
               List.of(this.outboundSlotKey(chatId), this.actualMessagesKey(chatId)),
               new Object[]{
                  String.valueOf(1100L),
                  String.valueOf(15000L),
                  String.valueOf(GROUP_WINDOW_MILLIS),
                  String.valueOf(12),
                  UUID.randomUUID().toString(),
                  countActualMessage ? "1" : "0"
               }
            );
         waitMillis = result == null ? -1L : result;
      } catch (RuntimeException var8) {
         log.warn("Redis 游戏发送节奏控制不可用，退化为单实例控制: chatId={}, error={}", chatId, var8.getMessage());
         waitMillis = this.reserveLocalOutboundSlot(chatId);
      }

      if (waitMillis < 0L || waitMillis > 15000L) {
         throw new TelegramGameRateLimiter.GameRateLimitException("群内游戏消息队列繁忙，请稍后再试");
      } else if (waitMillis != 0L) {
         try {
            Thread.sleep(waitMillis);
         } catch (InterruptedException var7) {
            Thread.currentThread().interrupt();
            throw new TelegramGameRateLimiter.GameRateLimitException("等待 Telegram 游戏消息发送时隙被中断", var7);
         }
      }
   }

   public boolean shouldSendLimitNotice(long chatId) {
      try {
         return Boolean.TRUE.equals(this.stringRedisTemplate.opsForValue().setIfAbsent(this.noticeCooldownKey(chatId), "1", Duration.ofSeconds(30L)));
      } catch (RuntimeException var7) {
         long now = System.currentTimeMillis();
         AtomicBoolean allowed = new AtomicBoolean(false);
         this.localNoticeExpiresAt.compute(chatId, (ignored, expiresAt) -> {
            if (expiresAt != null && expiresAt > now) {
               return (Long)expiresAt;
            } else {
               allowed.set(true);
               return now + Duration.ofSeconds(30L).toMillis();
            }
         });
         return allowed.get();
      }
   }

   private TelegramGameRateLimiter.AdmissionDecision mapAdmissionResult(List<?> result) {
      if (result != null && result.size() >= 3) {
         long allowed = this.longValue(result.get(0));
         long retryAfterSeconds = Math.max(1L, this.longValue(result.get(1)));
         long reasonCode = this.longValue(result.get(2));
         if (allowed == 1L) {
            return TelegramGameRateLimiter.AdmissionDecision.permit();
         } else {
            return reasonCode == 2L
               ? TelegramGameRateLimiter.AdmissionDecision.userLimited(retryAfterSeconds)
               : TelegramGameRateLimiter.AdmissionDecision.groupLimited(retryAfterSeconds);
         }
      } else {
         return TelegramGameRateLimiter.AdmissionDecision.unavailable();
      }
   }

   private long reserveLocalOutboundSlot(long chatId) {
      AtomicLong nextSlot = this.localNextOutboundAt.computeIfAbsent(chatId, ignored -> new AtomicLong());

      long now;
      long current;
      long slot;
      long following;
      do {
         now = System.currentTimeMillis();
         current = nextSlot.get();
         slot = Math.max(now, current);
         following = slot + 1100L;
      } while (!nextSlot.compareAndSet(current, following));

      return slot - now;
   }

   private int messageCost(String gameCommand) {
      String var2 = gameCommand == null ? "" : gameCommand;

      return switch (var2) {
         case "blackjack" -> 8;
         case "dice" -> 3;
         case "hell_dice" -> 1;
         case "brain" -> 3;
         case "slots" -> 2;
         default -> 1;
      };
   }

   private long longValue(Object value) {
      if (value instanceof Number number) {
         return number.longValue();
      } else {
         try {
            return Long.parseLong(String.valueOf(value));
         } catch (NumberFormatException var3) {
            return 0L;
         }
      }
   }

   private String groupBudgetKey(long chatId) {
      return "foam:points-bot:telegram-game-rate:v1:budget:" + chatId;
   }

   private String userCooldownKey(long chatId, long userId) {
      return "foam:points-bot:telegram-game-rate:v1:user:" + chatId + ":" + userId;
   }

   private String actionCooldownKey(long chatId, long userId) {
      return "foam:points-bot:telegram-game-rate:v1:action:" + chatId + ":" + userId;
   }

   private String outboundSlotKey(long chatId) {
      return "foam:points-bot:telegram-game-rate:v1:outbound:" + chatId;
   }

   private String noticeCooldownKey(long chatId) {
      return "foam:points-bot:telegram-game-rate:v1:notice:" + chatId;
   }

   private String actualMessagesKey(long chatId) {
      return "foam:points-bot:telegram-game-rate:v1:actual:" + chatId;
   }

   @Generated
   public TelegramGameRateLimiter(final StringRedisTemplate stringRedisTemplate) {
      this.stringRedisTemplate = stringRedisTemplate;
   }

   public static record AdmissionDecision(boolean allowed, TelegramGameRateLimiter.RejectionReason reason, long retryAfterSeconds) {
      public static TelegramGameRateLimiter.AdmissionDecision permit() {
         return new TelegramGameRateLimiter.AdmissionDecision(true, TelegramGameRateLimiter.RejectionReason.NONE, 0L);
      }

      public static TelegramGameRateLimiter.AdmissionDecision groupLimited(long retryAfterSeconds) {
         return new TelegramGameRateLimiter.AdmissionDecision(false, TelegramGameRateLimiter.RejectionReason.GROUP_BUSY, Math.max(1L, retryAfterSeconds));
      }

      public static TelegramGameRateLimiter.AdmissionDecision userLimited(long retryAfterSeconds) {
         return new TelegramGameRateLimiter.AdmissionDecision(false, TelegramGameRateLimiter.RejectionReason.USER_TOO_FAST, Math.max(1L, retryAfterSeconds));
      }

      public static TelegramGameRateLimiter.AdmissionDecision unavailable() {
         return new TelegramGameRateLimiter.AdmissionDecision(false, TelegramGameRateLimiter.RejectionReason.PROTECTION_UNAVAILABLE, 0L);
      }
   }

   public static class GameRateLimitException extends RuntimeException {
      public GameRateLimitException(String message) {
         super(message);
      }

      public GameRateLimitException(String message, Throwable cause) {
         super(message, cause);
      }
   }

   public static enum RejectionReason {
      NONE,
      GROUP_BUSY,
      USER_TOO_FAST,
      PROTECTION_UNAVAILABLE;
   }
}
