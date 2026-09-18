package com.una.embyhub.pointsbot.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class ScratchCardPayoutPolicy {
   public static final int JACKPOT_CHANCE_PERCENT = 1;
   public static final int NORMAL_LOW_CHANCE_PERCENT = 70;
   public static final int NORMAL_LOW_REWARD_MIN = 30;
   public static final int NORMAL_LOW_REWARD_MAX = 50;
   public static final int NORMAL_HIGH_REWARD_MIN = 50;
   public static final int NORMAL_HIGH_REWARD_MAX = 100;
   public static final List<Integer> JACKPOT_REWARDS = List.of(666, 777, 3888);

   private ScratchCardPayoutPolicy() {
   }

   public static ScratchCardPayoutPolicy.DrawResult draw(List<Integer> occupiedCells, Random random) {
      return draw(occupiedCells, random, false);
   }

   public static ScratchCardPayoutPolicy.DrawResult draw(List<Integer> occupiedCells, Random random, boolean forceJackpot) {
      return draw(occupiedCells, random, forceJackpot, true);
   }

   public static ScratchCardPayoutPolicy.DrawResult draw(List<Integer> occupiedCells, Random random, boolean forceJackpot, boolean jackpotEligible) {
      if (occupiedCells != null && !occupiedCells.isEmpty()) {
         Integer jackpotCell = null;
         Integer jackpotReward = null;
         if (jackpotEligible && (forceJackpot || random.nextInt(100) < 1)) {
            jackpotCell = occupiedCells.get(random.nextInt(occupiedCells.size()));
            jackpotReward = JACKPOT_REWARDS.get(random.nextInt(JACKPOT_REWARDS.size()));
         }

         Map<Integer, Integer> rewards = new LinkedHashMap<>();

         for (Integer cell : occupiedCells) {
            int reward = cell.equals(jackpotCell) ? jackpotReward : drawNormalReward(random);
            rewards.put(cell, reward);
         }

         return new ScratchCardPayoutPolicy.DrawResult(rewards, jackpotCell);
      } else {
         return new ScratchCardPayoutPolicy.DrawResult(Map.of(), null);
      }
   }

   private static int drawNormalReward(Random random) {
      return random.nextInt(100) < 70 ? random.nextInt(21) + 30 : random.nextInt(51) + 50;
   }

   public static record DrawResult(Map<Integer, Integer> rewards, Integer jackpotCell) {
   }
}
