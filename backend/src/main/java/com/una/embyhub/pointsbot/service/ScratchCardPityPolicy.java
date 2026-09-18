package com.una.embyhub.pointsbot.service;

import java.util.Random;

public final class ScratchCardPityPolicy {
   public static final int INTRO_MIN_TARGET_ROUNDS = 1;
   public static final int INTRO_MAX_TARGET_ROUNDS = 10;
   public static final int NORMAL_MIN_TARGET_ROUNDS = 50;
   public static final int NORMAL_MAX_TARGET_ROUNDS = 60;
   public static final int NORMAL_MIN_PARTICIPANTS = 3;

   private ScratchCardPityPolicy() {
   }

   public static ScratchCardPityPolicy.PityState next(
      Integer previousTarget, Integer previousProgress, boolean previousHadJackpot, int participantCount, Random random
   ) {
      if (previousTarget == null || previousProgress == null) {
         int target = randomTarget(1, 10, random);
         return new ScratchCardPityPolicy.PityState(target, 1, true, participantCount >= 1);
      } else if (previousHadJackpot || !isKnownTarget(previousTarget) || previousProgress < 0) {
         int target = randomTarget(50, 60, random);
         boolean eligible = participantCount >= 3;
         return new ScratchCardPityPolicy.PityState(target, eligible ? 1 : 0, false, eligible);
      } else if (isIntroductoryTarget(previousTarget)) {
         return new ScratchCardPityPolicy.PityState(previousTarget, previousProgress + 1, true, participantCount >= 1);
      } else {
         boolean eligible = participantCount >= 3;
         int progress = previousProgress + (eligible ? 1 : 0);
         return new ScratchCardPityPolicy.PityState(previousTarget, progress, false, eligible);
      }
   }

   private static int randomTarget(int min, int max, Random random) {
      return random.nextInt(max - min + 1) + min;
   }

   private static boolean isKnownTarget(int target) {
      return isIntroductoryTarget(target) || target >= 50 && target <= 60;
   }

   private static boolean isIntroductoryTarget(int target) {
      return target >= 1 && target <= 10;
   }

   public static record PityState(int target, int progress, boolean introductory, boolean jackpotEligible) {
      public boolean guaranteed() {
         return this.jackpotEligible && this.progress >= this.target;
      }
   }
}
