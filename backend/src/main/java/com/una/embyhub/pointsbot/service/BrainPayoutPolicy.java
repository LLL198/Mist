package com.una.embyhub.pointsbot.service;

import com.una.embyhub.pointsbot.model.BrainGameConfig;

public final class BrainPayoutPolicy {
   private BrainPayoutPolicy() {
   }

   public static BrainPayoutPolicy.NormalPayout normal(int pot, int correctCount, BrainGameConfig config) {
      if (correctCount <= 0) {
         int jackpotAddition = pot / 2;
         return new BrainPayoutPolicy.NormalPayout(0, 0, jackpotAddition, pot - jackpotAddition);
      } else {
         int champion = pot * config.getChampionPercent() / 100;
         int followerPool = pot * config.getFollowerPercent() / 100;
         int followerShare = correctCount > 1 ? followerPool / (correctCount - 1) : 0;
         int followerDistributed = followerShare * Math.max(0, correctCount - 1);
         int jackpotAddition = pot * config.getJackpotPercent() / 100 + followerPool - followerDistributed;
         int sink = Math.max(0, pot - champion - followerDistributed - jackpotAddition);
         return new BrainPayoutPolicy.NormalPayout(champion, followerShare, jackpotAddition, sink);
      }
   }

   public static BrainPayoutPolicy.PeakPayout peak(int jackpot, int correctCount, BrainGameConfig config) {
      if (jackpot > 0 && correctCount > 0) {
         int champion = jackpot * config.getPeakChampionPercent() / 100;
         int followerPool = jackpot * config.getPeakFollowerPercent() / 100;
         int followerShare = correctCount > 1 ? followerPool / (correctCount - 1) : 0;
         int followerDistributed = followerShare * Math.max(0, correctCount - 1);
         return new BrainPayoutPolicy.PeakPayout(champion, followerShare, Math.max(0, jackpot - champion - followerDistributed));
      } else {
         return new BrainPayoutPolicy.PeakPayout(0, 0, Math.max(0, jackpot));
      }
   }

   public static record NormalPayout(int championReward, int followerReward, int jackpotAddition, int sinkPoints) {
   }

   public static record PeakPayout(int championReward, int followerReward, int remainingJackpot) {
   }
}
