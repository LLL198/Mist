package com.una.embyhub.pointsbot.service;

import com.una.embyhub.pointsbot.model.BlackjackCard;
import com.una.embyhub.pointsbot.model.BlackjackSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlackjackGameService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(BlackjackGameService.class);
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   private static final String REDIS_KEY_PREFIX = "game:blackjack:";
   private static final long SESSION_TTL = 1L;
   private final Random random = new Random();

   public BlackjackSession startGame(Long userId, Long chatId, int betAmount) {
      BlackjackSession session = BlackjackSession.builder()
         .userId(userId)
         .chatId(chatId)
         .betAmount(betAmount)
         .playerHand(new ArrayList<>())
         .dealerHand(new ArrayList<>())
         .status(BlackjackSession.GameStatus.PLAYER_TURN)
         .lastActiveTime(LocalDateTime.now())
         .build();
      session.addPlayerCard(this.drawCard());
      session.addPlayerCard(this.drawCard());
      session.addDealerCard(this.drawCard());
      session.addDealerCard(this.drawCard());
      this.saveSession(userId, session);
      return session;
   }

   public BlackjackSession getSession(Long userId) {
      return (BlackjackSession)this.redisTemplate.opsForValue().get(this.buildKey(userId));
   }

   public void removeSession(Long userId) {
      this.redisTemplate.delete(this.buildKey(userId));
   }

   public void saveSession(Long userId, BlackjackSession session) {
      session.setLastActiveTime(LocalDateTime.now());
      this.redisTemplate.opsForValue().set(this.buildKey(userId), session, 1L, TimeUnit.HOURS);
   }

   private String buildKey(Long userId) {
      return "game:blackjack:" + userId;
   }

   public BlackjackCard drawCard() {
      BlackjackCard.Suit[] suits = BlackjackCard.Suit.values();
      BlackjackCard.Rank[] ranks = BlackjackCard.Rank.values();
      return new BlackjackCard(suits[this.random.nextInt(suits.length)], ranks[this.random.nextInt(ranks.length)]);
   }

   public void hit(BlackjackSession session) {
      session.addPlayerCard(this.drawCard());
      this.saveSession(session.getUserId(), session);
   }

   public void dealerTurn(BlackjackSession session) {
      session.setStatus(BlackjackSession.GameStatus.DEALER_TURN);

      while (this.calculateScore(session.getDealerHand()) < 17) {
         session.addDealerCard(this.drawCard());
      }

      session.setStatus(BlackjackSession.GameStatus.FINISHED);
      this.saveSession(session.getUserId(), session);
   }

   public int calculateScore(List<BlackjackCard> hand) {
      int score = 0;
      int aces = 0;

      for (BlackjackCard card : hand) {
         score += card.getValue();
         if (card.getRank() == BlackjackCard.Rank.ACE) {
            aces++;
         }
      }

      while (score > 21 && aces > 0) {
         score -= 10;
         aces--;
      }

      return score;
   }

   public boolean isBust(List<BlackjackCard> hand) {
      return this.calculateScore(hand) > 21;
   }

   public boolean isBlackjack(List<BlackjackCard> hand) {
      return hand.size() == 2 && this.calculateScore(hand) == 21;
   }
}
