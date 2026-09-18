package com.una.embyhub.pointsbot.model;

import lombok.Generated;

public class BrainGameConfig {
   private int entryCost = 10;
   private int dailyPlayLimit = 3;
   private int dailyChampionLimit = 2;
   private int registrationSeconds = 15;
   private int answerSeconds = 60;
   private int minPlayers = 5;
   private int maxPlayers = 30;
   private int peakMinPlayers = 10;
   private int peakEveryRounds = 10;
   private int jackpotCap = 1000;
   private int championPercent = 70;
   private int followerPercent = 15;
   private int jackpotPercent = 10;
   private int sinkPercent = 5;
   private int peakChampionPercent = 70;
   private int peakFollowerPercent = 20;
   private int targetArithmeticWeight = 30;
   private int codeLockWeight = 25;
   private int bullsAndCowsWeight = 20;
   private int lightsOutWeight = 15;
   private int flashMemoryWeight = 10;

   @Generated
   public int getEntryCost() {
      return this.entryCost;
   }

   @Generated
   public int getDailyPlayLimit() {
      return this.dailyPlayLimit;
   }

   @Generated
   public int getDailyChampionLimit() {
      return this.dailyChampionLimit;
   }

   @Generated
   public int getRegistrationSeconds() {
      return this.registrationSeconds;
   }

   @Generated
   public int getAnswerSeconds() {
      return this.answerSeconds;
   }

   @Generated
   public int getMinPlayers() {
      return this.minPlayers;
   }

   @Generated
   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   @Generated
   public int getPeakMinPlayers() {
      return this.peakMinPlayers;
   }

   @Generated
   public int getPeakEveryRounds() {
      return this.peakEveryRounds;
   }

   @Generated
   public int getJackpotCap() {
      return this.jackpotCap;
   }

   @Generated
   public int getChampionPercent() {
      return this.championPercent;
   }

   @Generated
   public int getFollowerPercent() {
      return this.followerPercent;
   }

   @Generated
   public int getJackpotPercent() {
      return this.jackpotPercent;
   }

   @Generated
   public int getSinkPercent() {
      return this.sinkPercent;
   }

   @Generated
   public int getPeakChampionPercent() {
      return this.peakChampionPercent;
   }

   @Generated
   public int getPeakFollowerPercent() {
      return this.peakFollowerPercent;
   }

   @Generated
   public int getTargetArithmeticWeight() {
      return this.targetArithmeticWeight;
   }

   @Generated
   public int getCodeLockWeight() {
      return this.codeLockWeight;
   }

   @Generated
   public int getBullsAndCowsWeight() {
      return this.bullsAndCowsWeight;
   }

   @Generated
   public int getLightsOutWeight() {
      return this.lightsOutWeight;
   }

   @Generated
   public int getFlashMemoryWeight() {
      return this.flashMemoryWeight;
   }

   @Generated
   public void setEntryCost(final int entryCost) {
      this.entryCost = entryCost;
   }

   @Generated
   public void setDailyPlayLimit(final int dailyPlayLimit) {
      this.dailyPlayLimit = dailyPlayLimit;
   }

   @Generated
   public void setDailyChampionLimit(final int dailyChampionLimit) {
      this.dailyChampionLimit = dailyChampionLimit;
   }

   @Generated
   public void setRegistrationSeconds(final int registrationSeconds) {
      this.registrationSeconds = registrationSeconds;
   }

   @Generated
   public void setAnswerSeconds(final int answerSeconds) {
      this.answerSeconds = answerSeconds;
   }

   @Generated
   public void setMinPlayers(final int minPlayers) {
      this.minPlayers = minPlayers;
   }

   @Generated
   public void setMaxPlayers(final int maxPlayers) {
      this.maxPlayers = maxPlayers;
   }

   @Generated
   public void setPeakMinPlayers(final int peakMinPlayers) {
      this.peakMinPlayers = peakMinPlayers;
   }

   @Generated
   public void setPeakEveryRounds(final int peakEveryRounds) {
      this.peakEveryRounds = peakEveryRounds;
   }

   @Generated
   public void setJackpotCap(final int jackpotCap) {
      this.jackpotCap = jackpotCap;
   }

   @Generated
   public void setChampionPercent(final int championPercent) {
      this.championPercent = championPercent;
   }

   @Generated
   public void setFollowerPercent(final int followerPercent) {
      this.followerPercent = followerPercent;
   }

   @Generated
   public void setJackpotPercent(final int jackpotPercent) {
      this.jackpotPercent = jackpotPercent;
   }

   @Generated
   public void setSinkPercent(final int sinkPercent) {
      this.sinkPercent = sinkPercent;
   }

   @Generated
   public void setPeakChampionPercent(final int peakChampionPercent) {
      this.peakChampionPercent = peakChampionPercent;
   }

   @Generated
   public void setPeakFollowerPercent(final int peakFollowerPercent) {
      this.peakFollowerPercent = peakFollowerPercent;
   }

   @Generated
   public void setTargetArithmeticWeight(final int targetArithmeticWeight) {
      this.targetArithmeticWeight = targetArithmeticWeight;
   }

   @Generated
   public void setCodeLockWeight(final int codeLockWeight) {
      this.codeLockWeight = codeLockWeight;
   }

   @Generated
   public void setBullsAndCowsWeight(final int bullsAndCowsWeight) {
      this.bullsAndCowsWeight = bullsAndCowsWeight;
   }

   @Generated
   public void setLightsOutWeight(final int lightsOutWeight) {
      this.lightsOutWeight = lightsOutWeight;
   }

   @Generated
   public void setFlashMemoryWeight(final int flashMemoryWeight) {
      this.flashMemoryWeight = flashMemoryWeight;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BrainGameConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getEntryCost() != other.getEntryCost()) {
         return false;
      } else if (this.getDailyPlayLimit() != other.getDailyPlayLimit()) {
         return false;
      } else if (this.getDailyChampionLimit() != other.getDailyChampionLimit()) {
         return false;
      } else if (this.getRegistrationSeconds() != other.getRegistrationSeconds()) {
         return false;
      } else if (this.getAnswerSeconds() != other.getAnswerSeconds()) {
         return false;
      } else if (this.getMinPlayers() != other.getMinPlayers()) {
         return false;
      } else if (this.getMaxPlayers() != other.getMaxPlayers()) {
         return false;
      } else if (this.getPeakMinPlayers() != other.getPeakMinPlayers()) {
         return false;
      } else if (this.getPeakEveryRounds() != other.getPeakEveryRounds()) {
         return false;
      } else if (this.getJackpotCap() != other.getJackpotCap()) {
         return false;
      } else if (this.getChampionPercent() != other.getChampionPercent()) {
         return false;
      } else if (this.getFollowerPercent() != other.getFollowerPercent()) {
         return false;
      } else if (this.getJackpotPercent() != other.getJackpotPercent()) {
         return false;
      } else if (this.getSinkPercent() != other.getSinkPercent()) {
         return false;
      } else if (this.getPeakChampionPercent() != other.getPeakChampionPercent()) {
         return false;
      } else if (this.getPeakFollowerPercent() != other.getPeakFollowerPercent()) {
         return false;
      } else if (this.getTargetArithmeticWeight() != other.getTargetArithmeticWeight()) {
         return false;
      } else if (this.getCodeLockWeight() != other.getCodeLockWeight()) {
         return false;
      } else if (this.getBullsAndCowsWeight() != other.getBullsAndCowsWeight()) {
         return false;
      } else {
         return this.getLightsOutWeight() != other.getLightsOutWeight() ? false : this.getFlashMemoryWeight() == other.getFlashMemoryWeight();
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof BrainGameConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getEntryCost();
      result = result * 59 + this.getDailyPlayLimit();
      result = result * 59 + this.getDailyChampionLimit();
      result = result * 59 + this.getRegistrationSeconds();
      result = result * 59 + this.getAnswerSeconds();
      result = result * 59 + this.getMinPlayers();
      result = result * 59 + this.getMaxPlayers();
      result = result * 59 + this.getPeakMinPlayers();
      result = result * 59 + this.getPeakEveryRounds();
      result = result * 59 + this.getJackpotCap();
      result = result * 59 + this.getChampionPercent();
      result = result * 59 + this.getFollowerPercent();
      result = result * 59 + this.getJackpotPercent();
      result = result * 59 + this.getSinkPercent();
      result = result * 59 + this.getPeakChampionPercent();
      result = result * 59 + this.getPeakFollowerPercent();
      result = result * 59 + this.getTargetArithmeticWeight();
      result = result * 59 + this.getCodeLockWeight();
      result = result * 59 + this.getBullsAndCowsWeight();
      result = result * 59 + this.getLightsOutWeight();
      return result * 59 + this.getFlashMemoryWeight();
   }

   @Generated
   @Override
   public String toString() {
      return "BrainGameConfig(entryCost="
         + this.getEntryCost()
         + ", dailyPlayLimit="
         + this.getDailyPlayLimit()
         + ", dailyChampionLimit="
         + this.getDailyChampionLimit()
         + ", registrationSeconds="
         + this.getRegistrationSeconds()
         + ", answerSeconds="
         + this.getAnswerSeconds()
         + ", minPlayers="
         + this.getMinPlayers()
         + ", maxPlayers="
         + this.getMaxPlayers()
         + ", peakMinPlayers="
         + this.getPeakMinPlayers()
         + ", peakEveryRounds="
         + this.getPeakEveryRounds()
         + ", jackpotCap="
         + this.getJackpotCap()
         + ", championPercent="
         + this.getChampionPercent()
         + ", followerPercent="
         + this.getFollowerPercent()
         + ", jackpotPercent="
         + this.getJackpotPercent()
         + ", sinkPercent="
         + this.getSinkPercent()
         + ", peakChampionPercent="
         + this.getPeakChampionPercent()
         + ", peakFollowerPercent="
         + this.getPeakFollowerPercent()
         + ", targetArithmeticWeight="
         + this.getTargetArithmeticWeight()
         + ", codeLockWeight="
         + this.getCodeLockWeight()
         + ", bullsAndCowsWeight="
         + this.getBullsAndCowsWeight()
         + ", lightsOutWeight="
         + this.getLightsOutWeight()
         + ", flashMemoryWeight="
         + this.getFlashMemoryWeight()
         + ")";
   }
}
