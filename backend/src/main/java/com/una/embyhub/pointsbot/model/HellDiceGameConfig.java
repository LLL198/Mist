package com.una.embyhub.pointsbot.model;

import com.alibaba.fastjson2.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class HellDiceGameConfig {
   private int minBet = 5;
   private int maxBet = 30;
   private int dailyPlayLimit = 300;
   private int decisionSeconds = 15;
   private int bettingSeconds = 8;
   private int panelRetentionSeconds = 120;
   private int singlePayoutCap = 300;
   private int dailyPlayerProfitCap = 300;
   private int dailyGroupProfitCap = 1000;
   private int initialVaultPoints = 300;
   private int vaultCapacity = 3000;
   private int adminTopUpLifetimeCap = 3000;
   private int lossVaultPercent = 90;
   private boolean spectatorBetEnabled = true;
   private int spectatorMinBet = 1;
   private int spectatorMaxBetPerLayer = 5;
   private int spectatorMaxBetPerRound = 20;
   private int spectatorMaxBetPerDay = 50;
   private int spectatorPoolCapPerLayer = 100;
   private int spectatorFeePercent = 5;
   private int leaderboardLimit = 10;
   private List<List<Integer>> layerDeathNumbers = defaultLayerDeathNumbers();
   @Deprecated
   @JSONField(
      serialize = false
   )
   private List<Integer> layerDeathMaxes;
   private List<Integer> layerPayoutPercents = new ArrayList<>(List.of(120, 160, 240, 360, 700, 1000));

   public HellDiceGameConfig copy() {
      HellDiceGameConfig result = new HellDiceGameConfig();
      result.setMinBet(this.minBet);
      result.setMaxBet(this.maxBet);
      result.setDailyPlayLimit(this.dailyPlayLimit);
      result.setDecisionSeconds(this.decisionSeconds);
      result.setBettingSeconds(this.bettingSeconds);
      result.setPanelRetentionSeconds(this.panelRetentionSeconds);
      result.setSinglePayoutCap(this.singlePayoutCap);
      result.setDailyPlayerProfitCap(this.dailyPlayerProfitCap);
      result.setDailyGroupProfitCap(this.dailyGroupProfitCap);
      result.setInitialVaultPoints(this.initialVaultPoints);
      result.setVaultCapacity(this.vaultCapacity);
      result.setAdminTopUpLifetimeCap(this.adminTopUpLifetimeCap);
      result.setLossVaultPercent(this.lossVaultPercent);
      result.setSpectatorBetEnabled(this.spectatorBetEnabled);
      result.setSpectatorMinBet(this.spectatorMinBet);
      result.setSpectatorMaxBetPerLayer(this.spectatorMaxBetPerLayer);
      result.setSpectatorMaxBetPerRound(this.spectatorMaxBetPerRound);
      result.setSpectatorMaxBetPerDay(this.spectatorMaxBetPerDay);
      result.setSpectatorPoolCapPerLayer(this.spectatorPoolCapPerLayer);
      result.setSpectatorFeePercent(this.spectatorFeePercent);
      result.setLeaderboardLimit(this.leaderboardLimit);
      result.setLayerDeathNumbers(copyDeathNumbers(this.layerDeathNumbers));
      result.setLayerDeathMaxes(this.layerDeathMaxes == null ? null : new ArrayList<>(this.layerDeathMaxes));
      result.setLayerPayoutPercents(this.layerPayoutPercents == null ? new ArrayList<>() : new ArrayList<>(this.layerPayoutPercents));
      return result;
   }

   public static List<List<Integer>> defaultLayerDeathNumbers() {
      return copyDeathNumbers(
         List.of(List.of(1, 3, 5), List.of(1, 3, 5), List.of(1, 3, 5, 6), List.of(1, 3, 5, 6), List.of(1, 2, 3, 4, 5), List.of(1, 2, 3, 4, 5))
      );
   }

   private static List<List<Integer>> copyDeathNumbers(List<List<Integer>> source) {
      if (source == null) {
         return new ArrayList<>();
      } else {
         List<List<Integer>> result = new ArrayList<>(source.size());

         for (List<Integer> numbers : source) {
            result.add(numbers == null ? new ArrayList<>() : new ArrayList<>(numbers));
         }

         return result;
      }
   }

   @Generated
   public int getMinBet() {
      return this.minBet;
   }

   @Generated
   public int getMaxBet() {
      return this.maxBet;
   }

   @Generated
   public int getDailyPlayLimit() {
      return this.dailyPlayLimit;
   }

   @Generated
   public int getDecisionSeconds() {
      return this.decisionSeconds;
   }

   @Generated
   public int getBettingSeconds() {
      return this.bettingSeconds;
   }

   @Generated
   public int getPanelRetentionSeconds() {
      return this.panelRetentionSeconds;
   }

   @Generated
   public int getSinglePayoutCap() {
      return this.singlePayoutCap;
   }

   @Generated
   public int getDailyPlayerProfitCap() {
      return this.dailyPlayerProfitCap;
   }

   @Generated
   public int getDailyGroupProfitCap() {
      return this.dailyGroupProfitCap;
   }

   @Generated
   public int getInitialVaultPoints() {
      return this.initialVaultPoints;
   }

   @Generated
   public int getVaultCapacity() {
      return this.vaultCapacity;
   }

   @Generated
   public int getAdminTopUpLifetimeCap() {
      return this.adminTopUpLifetimeCap;
   }

   @Generated
   public int getLossVaultPercent() {
      return this.lossVaultPercent;
   }

   @Generated
   public boolean isSpectatorBetEnabled() {
      return this.spectatorBetEnabled;
   }

   @Generated
   public int getSpectatorMinBet() {
      return this.spectatorMinBet;
   }

   @Generated
   public int getSpectatorMaxBetPerLayer() {
      return this.spectatorMaxBetPerLayer;
   }

   @Generated
   public int getSpectatorMaxBetPerRound() {
      return this.spectatorMaxBetPerRound;
   }

   @Generated
   public int getSpectatorMaxBetPerDay() {
      return this.spectatorMaxBetPerDay;
   }

   @Generated
   public int getSpectatorPoolCapPerLayer() {
      return this.spectatorPoolCapPerLayer;
   }

   @Generated
   public int getSpectatorFeePercent() {
      return this.spectatorFeePercent;
   }

   @Generated
   public int getLeaderboardLimit() {
      return this.leaderboardLimit;
   }

   @Generated
   public List<List<Integer>> getLayerDeathNumbers() {
      return this.layerDeathNumbers;
   }

   @Deprecated
   @Generated
   public List<Integer> getLayerDeathMaxes() {
      return this.layerDeathMaxes;
   }

   @Generated
   public List<Integer> getLayerPayoutPercents() {
      return this.layerPayoutPercents;
   }

   @Generated
   public void setMinBet(final int minBet) {
      this.minBet = minBet;
   }

   @Generated
   public void setMaxBet(final int maxBet) {
      this.maxBet = maxBet;
   }

   @Generated
   public void setDailyPlayLimit(final int dailyPlayLimit) {
      this.dailyPlayLimit = dailyPlayLimit;
   }

   @Generated
   public void setDecisionSeconds(final int decisionSeconds) {
      this.decisionSeconds = decisionSeconds;
   }

   @Generated
   public void setBettingSeconds(final int bettingSeconds) {
      this.bettingSeconds = bettingSeconds;
   }

   @Generated
   public void setPanelRetentionSeconds(final int panelRetentionSeconds) {
      this.panelRetentionSeconds = panelRetentionSeconds;
   }

   @Generated
   public void setSinglePayoutCap(final int singlePayoutCap) {
      this.singlePayoutCap = singlePayoutCap;
   }

   @Generated
   public void setDailyPlayerProfitCap(final int dailyPlayerProfitCap) {
      this.dailyPlayerProfitCap = dailyPlayerProfitCap;
   }

   @Generated
   public void setDailyGroupProfitCap(final int dailyGroupProfitCap) {
      this.dailyGroupProfitCap = dailyGroupProfitCap;
   }

   @Generated
   public void setInitialVaultPoints(final int initialVaultPoints) {
      this.initialVaultPoints = initialVaultPoints;
   }

   @Generated
   public void setVaultCapacity(final int vaultCapacity) {
      this.vaultCapacity = vaultCapacity;
   }

   @Generated
   public void setAdminTopUpLifetimeCap(final int adminTopUpLifetimeCap) {
      this.adminTopUpLifetimeCap = adminTopUpLifetimeCap;
   }

   @Generated
   public void setLossVaultPercent(final int lossVaultPercent) {
      this.lossVaultPercent = lossVaultPercent;
   }

   @Generated
   public void setSpectatorBetEnabled(final boolean spectatorBetEnabled) {
      this.spectatorBetEnabled = spectatorBetEnabled;
   }

   @Generated
   public void setSpectatorMinBet(final int spectatorMinBet) {
      this.spectatorMinBet = spectatorMinBet;
   }

   @Generated
   public void setSpectatorMaxBetPerLayer(final int spectatorMaxBetPerLayer) {
      this.spectatorMaxBetPerLayer = spectatorMaxBetPerLayer;
   }

   @Generated
   public void setSpectatorMaxBetPerRound(final int spectatorMaxBetPerRound) {
      this.spectatorMaxBetPerRound = spectatorMaxBetPerRound;
   }

   @Generated
   public void setSpectatorMaxBetPerDay(final int spectatorMaxBetPerDay) {
      this.spectatorMaxBetPerDay = spectatorMaxBetPerDay;
   }

   @Generated
   public void setSpectatorPoolCapPerLayer(final int spectatorPoolCapPerLayer) {
      this.spectatorPoolCapPerLayer = spectatorPoolCapPerLayer;
   }

   @Generated
   public void setSpectatorFeePercent(final int spectatorFeePercent) {
      this.spectatorFeePercent = spectatorFeePercent;
   }

   @Generated
   public void setLeaderboardLimit(final int leaderboardLimit) {
      this.leaderboardLimit = leaderboardLimit;
   }

   @Generated
   public void setLayerDeathNumbers(final List<List<Integer>> layerDeathNumbers) {
      this.layerDeathNumbers = layerDeathNumbers;
   }

   @Deprecated
   @Generated
   public void setLayerDeathMaxes(final List<Integer> layerDeathMaxes) {
      this.layerDeathMaxes = layerDeathMaxes;
   }

   @Generated
   public void setLayerPayoutPercents(final List<Integer> layerPayoutPercents) {
      this.layerPayoutPercents = layerPayoutPercents;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof HellDiceGameConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getMinBet() != other.getMinBet()) {
         return false;
      } else if (this.getMaxBet() != other.getMaxBet()) {
         return false;
      } else if (this.getDailyPlayLimit() != other.getDailyPlayLimit()) {
         return false;
      } else if (this.getDecisionSeconds() != other.getDecisionSeconds()) {
         return false;
      } else if (this.getBettingSeconds() != other.getBettingSeconds()) {
         return false;
      } else if (this.getPanelRetentionSeconds() != other.getPanelRetentionSeconds()) {
         return false;
      } else if (this.getSinglePayoutCap() != other.getSinglePayoutCap()) {
         return false;
      } else if (this.getDailyPlayerProfitCap() != other.getDailyPlayerProfitCap()) {
         return false;
      } else if (this.getDailyGroupProfitCap() != other.getDailyGroupProfitCap()) {
         return false;
      } else if (this.getInitialVaultPoints() != other.getInitialVaultPoints()) {
         return false;
      } else if (this.getVaultCapacity() != other.getVaultCapacity()) {
         return false;
      } else if (this.getAdminTopUpLifetimeCap() != other.getAdminTopUpLifetimeCap()) {
         return false;
      } else if (this.getLossVaultPercent() != other.getLossVaultPercent()) {
         return false;
      } else if (this.isSpectatorBetEnabled() != other.isSpectatorBetEnabled()) {
         return false;
      } else if (this.getSpectatorMinBet() != other.getSpectatorMinBet()) {
         return false;
      } else if (this.getSpectatorMaxBetPerLayer() != other.getSpectatorMaxBetPerLayer()) {
         return false;
      } else if (this.getSpectatorMaxBetPerRound() != other.getSpectatorMaxBetPerRound()) {
         return false;
      } else if (this.getSpectatorMaxBetPerDay() != other.getSpectatorMaxBetPerDay()) {
         return false;
      } else if (this.getSpectatorPoolCapPerLayer() != other.getSpectatorPoolCapPerLayer()) {
         return false;
      } else if (this.getSpectatorFeePercent() != other.getSpectatorFeePercent()) {
         return false;
      } else if (this.getLeaderboardLimit() != other.getLeaderboardLimit()) {
         return false;
      } else {
         Object this$layerDeathNumbers = this.getLayerDeathNumbers();
         Object other$layerDeathNumbers = other.getLayerDeathNumbers();
         if (this$layerDeathNumbers == null ? other$layerDeathNumbers == null : this$layerDeathNumbers.equals(other$layerDeathNumbers)) {
            Object this$layerDeathMaxes = this.getLayerDeathMaxes();
            Object other$layerDeathMaxes = other.getLayerDeathMaxes();
            if (this$layerDeathMaxes == null ? other$layerDeathMaxes == null : this$layerDeathMaxes.equals(other$layerDeathMaxes)) {
               Object this$layerPayoutPercents = this.getLayerPayoutPercents();
               Object other$layerPayoutPercents = other.getLayerPayoutPercents();
               return this$layerPayoutPercents == null ? other$layerPayoutPercents == null : this$layerPayoutPercents.equals(other$layerPayoutPercents);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof HellDiceGameConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getMinBet();
      result = result * 59 + this.getMaxBet();
      result = result * 59 + this.getDailyPlayLimit();
      result = result * 59 + this.getDecisionSeconds();
      result = result * 59 + this.getBettingSeconds();
      result = result * 59 + this.getPanelRetentionSeconds();
      result = result * 59 + this.getSinglePayoutCap();
      result = result * 59 + this.getDailyPlayerProfitCap();
      result = result * 59 + this.getDailyGroupProfitCap();
      result = result * 59 + this.getInitialVaultPoints();
      result = result * 59 + this.getVaultCapacity();
      result = result * 59 + this.getAdminTopUpLifetimeCap();
      result = result * 59 + this.getLossVaultPercent();
      result = result * 59 + (this.isSpectatorBetEnabled() ? 79 : 97);
      result = result * 59 + this.getSpectatorMinBet();
      result = result * 59 + this.getSpectatorMaxBetPerLayer();
      result = result * 59 + this.getSpectatorMaxBetPerRound();
      result = result * 59 + this.getSpectatorMaxBetPerDay();
      result = result * 59 + this.getSpectatorPoolCapPerLayer();
      result = result * 59 + this.getSpectatorFeePercent();
      result = result * 59 + this.getLeaderboardLimit();
      Object $layerDeathNumbers = this.getLayerDeathNumbers();
      result = result * 59 + ($layerDeathNumbers == null ? 43 : $layerDeathNumbers.hashCode());
      Object $layerDeathMaxes = this.getLayerDeathMaxes();
      result = result * 59 + ($layerDeathMaxes == null ? 43 : $layerDeathMaxes.hashCode());
      Object $layerPayoutPercents = this.getLayerPayoutPercents();
      return result * 59 + ($layerPayoutPercents == null ? 43 : $layerPayoutPercents.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "HellDiceGameConfig(minBet="
         + this.getMinBet()
         + ", maxBet="
         + this.getMaxBet()
         + ", dailyPlayLimit="
         + this.getDailyPlayLimit()
         + ", decisionSeconds="
         + this.getDecisionSeconds()
         + ", bettingSeconds="
         + this.getBettingSeconds()
         + ", panelRetentionSeconds="
         + this.getPanelRetentionSeconds()
         + ", singlePayoutCap="
         + this.getSinglePayoutCap()
         + ", dailyPlayerProfitCap="
         + this.getDailyPlayerProfitCap()
         + ", dailyGroupProfitCap="
         + this.getDailyGroupProfitCap()
         + ", initialVaultPoints="
         + this.getInitialVaultPoints()
         + ", vaultCapacity="
         + this.getVaultCapacity()
         + ", adminTopUpLifetimeCap="
         + this.getAdminTopUpLifetimeCap()
         + ", lossVaultPercent="
         + this.getLossVaultPercent()
         + ", spectatorBetEnabled="
         + this.isSpectatorBetEnabled()
         + ", spectatorMinBet="
         + this.getSpectatorMinBet()
         + ", spectatorMaxBetPerLayer="
         + this.getSpectatorMaxBetPerLayer()
         + ", spectatorMaxBetPerRound="
         + this.getSpectatorMaxBetPerRound()
         + ", spectatorMaxBetPerDay="
         + this.getSpectatorMaxBetPerDay()
         + ", spectatorPoolCapPerLayer="
         + this.getSpectatorPoolCapPerLayer()
         + ", spectatorFeePercent="
         + this.getSpectatorFeePercent()
         + ", leaderboardLimit="
         + this.getLeaderboardLimit()
         + ", layerDeathNumbers="
         + this.getLayerDeathNumbers()
         + ", layerDeathMaxes="
         + this.getLayerDeathMaxes()
         + ", layerPayoutPercents="
         + this.getLayerPayoutPercents()
         + ")";
   }
}
