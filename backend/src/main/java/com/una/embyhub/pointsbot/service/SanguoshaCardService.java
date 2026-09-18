package com.una.embyhub.pointsbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.una.embyhub.pointsbot.model.SanguoshaCard;
import jakarta.annotation.PostConstruct;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SanguoshaCardService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SanguoshaCardService.class);
   public static final String EFFECT_STEAL = "STEAL";
   public static final String EFFECT_GIFT = "GIFT";
   public static final String EFFECT_MUTUAL_GIFT = "MUTUAL_GIFT";
   public static final String EFFECT_BOTH_GAIN = "BOTH_GAIN";
   public static final String EFFECT_SELF_GAIN = "SELF_GAIN";
   public static final String EFFECT_TARGET_LOSE = "TARGET_LOSE";
   public static final String EFFECT_BOTH_LOSE = "BOTH_LOSE";
   public static final String EFFECT_DUEL = "DUEL";
   public static final String EFFECT_NO_EFFECT = "NO_EFFECT";
   private static final String DECK_REDIS_KEY = "pointsbot:sanguosha:deck:v1";
   private static final String DAILY_PLAY_KEY_PREFIX = "pointsbot:sanguosha:daily:";
   private static final List<String> TRIGGER_WORDS = List.of("三国杀");
   private final StringRedisTemplate stringRedisTemplate;
   private final ObjectMapper objectMapper;
   private final Random random = new SecureRandom();

   @PostConstruct
   public void preloadDeck() {
      try {
         this.stringRedisTemplate.opsForValue().set("pointsbot:sanguosha:deck:v1", this.objectMapper.writeValueAsString(this.defaultDeck()));
         log.info("三国杀积分牌堆已加载到 Redis：{}", "pointsbot:sanguosha:deck:v1");
      } catch (Exception var2) {
         log.warn("三国杀积分牌堆加载到 Redis 失败，将使用内存默认牌堆: {}", var2.getMessage());
      }
   }

   public boolean isTriggerText(String text) {
      if (!StringUtils.hasText(text)) {
         return false;
      } else {
         String normalized = text.replaceAll("\\s+", "");
         return TRIGGER_WORDS.contains(normalized);
      }
   }

   public SanguoshaCardService.DailyPlayResult tryConsumeDailyPlay(long chatId, long userId, int dailyLimit) {
      int limit = Math.max(0, dailyLimit);
      if (limit == 0) {
         return new SanguoshaCardService.DailyPlayResult(true, 0, 0);
      } else {
         String key = "pointsbot:sanguosha:daily:" + LocalDate.now() + ":" + chatId + ":" + userId;

         try {
            Long used = this.stringRedisTemplate.opsForValue().increment(key);
            if (used != null && used == 1L) {
               this.stringRedisTemplate.expire(key, Duration.ofDays(2L));
            }

            int usedCount = used == null ? 0 : used.intValue();
            return new SanguoshaCardService.DailyPlayResult(usedCount <= limit, Math.min(usedCount, limit), limit);
         } catch (Exception var10) {
            log.warn("记录三国杀每日次数失败，默认放行: {}", var10.getMessage());
            return new SanguoshaCardService.DailyPlayResult(true, 0, limit);
         }
      }
   }

   public SanguoshaCard drawCard() {
      List<SanguoshaCard> deck = this.loadDeck();
      return deck.get(this.random.nextInt(deck.size()));
   }

   public int randomPoints(SanguoshaCard card) {
      if (card == null) {
         return 0;
      } else {
         int min = Math.max(0, card.getMinPoints());
         int max = Math.max(min, card.getMaxPoints());
         return max <= min ? min : this.random.nextInt(max - min + 1) + min;
      }
   }

   public List<String> triggerWords() {
      return TRIGGER_WORDS;
   }

   private List<SanguoshaCard> loadDeck() {
      try {
         String json = this.stringRedisTemplate.opsForValue().get("pointsbot:sanguosha:deck:v1");
         if (StringUtils.hasText(json)) {
            List<SanguoshaCard> cards = this.objectMapper.readValue(json, new TypeReference<List<SanguoshaCard>>() {
            });
            if (cards != null && !cards.isEmpty()) {
               return cards;
            }
         }
      } catch (Exception var3) {
         log.warn("读取三国杀积分牌堆失败，使用默认牌堆: {}", var3.getMessage());
      }

      return this.defaultDeck();
   }

   private List<SanguoshaCard> defaultDeck() {
      return List.of(
         new SanguoshaCard("shunshou", "顺手牵羊", "\ud83d\udc11", "STEAL", 1, 8),
         new SanguoshaCard("jiedao", "借刀杀人", "\ud83d\udde1", "STEAL", 2, 6),
         new SanguoshaCard("tao", "桃", "\ud83c\udf51", "GIFT", 1, 5),
         new SanguoshaCard("rende", "仁德", "\ud83e\udd32", "GIFT", 2, 8),
         new SanguoshaCard("tie_suo", "铁索连环", "⛓", "MUTUAL_GIFT", 1, 6),
         new SanguoshaCard("wugu", "五谷丰登", "\ud83c\udf3e", "BOTH_GAIN", 1, 5),
         new SanguoshaCard("taoyuan", "桃园结义", "\ud83c\udfd5", "BOTH_GAIN", 2, 6),
         new SanguoshaCard("wuzhong", "无中生有", "✨", "SELF_GAIN", 2, 8),
         new SanguoshaCard("guohe", "过河拆桥", "\ud83c\udf09", "TARGET_LOSE", 1, 5),
         new SanguoshaCard("nanman", "南蛮入侵", "\ud83d\udc18", "BOTH_LOSE", 1, 4),
         new SanguoshaCard("juedou", "决斗", "⚔", "DUEL", 1, 8),
         new SanguoshaCard("sha", "杀", "\ud83e\ude78", "DUEL", 1, 6),
         new SanguoshaCard("shan", "闪", "\ud83d\udca8", "NO_EFFECT", 0, 0),
         new SanguoshaCard("wuxie", "无懈可击", "\ud83d\udee1", "NO_EFFECT", 0, 0)
      );
   }

   @Generated
   public SanguoshaCardService(final StringRedisTemplate stringRedisTemplate, final ObjectMapper objectMapper) {
      this.stringRedisTemplate = stringRedisTemplate;
      this.objectMapper = objectMapper;
   }

   public static record DailyPlayResult(boolean allowed, int used, int limit) {
   }
}
