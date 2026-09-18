package com.una.embyhub.pointsbot.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class PointsBotConfig {
   public static final int CURRENT_GAME_COMMANDS_VERSION = 4;
   public static final String GAME_BRAIN = "brain";
   public static final String GAME_SANGUOSHA = "sgs";
   public static final String GAME_BLACKJACK = "blackjack";
   public static final String GAME_DICE = "dice";
   public static final String GAME_HELL_DICE = "hell_dice";
   public static final String GAME_SLOTS = "slots";
   public static final String GAME_SCRATCH = "scratch";
   public static final List<String> ALL_GAME_COMMANDS = List.of("sgs", "blackjack", "dice", "hell_dice", "slots", "scratch", "brain");
   public static final List<String> DEFAULT_GAME_COMMANDS = List.of("sgs", "blackjack", "dice", "hell_dice", "slots", "scratch", "brain");
   private String botToken;
   private Integer apiId;
   private String apiHash;
   private String botName;
   private String groupChatId;
   private String dmChatId;
   private Boolean privateChatMemberRequired = true;
   private Boolean groupCheckinEnabled = true;
   private Boolean foamBagEnabled = true;
   private Boolean redPacketEnabled = true;
   private int redPacketExpireMinutes = 1440;
   private int dailyMessagePointsLimit = 20;
   private int dailySanguoshaPlayLimit = 3;
   private Boolean gameCommandsEnabled = true;
   private Integer gameCommandsVersion;
   private List<String> enabledGameCommands = new ArrayList<>(DEFAULT_GAME_COMMANDS);
   private int checkinBaseMin = 1;
   private int checkinBaseMax = 3;
   private int checkinPenaltyChance = 15;
   private int checkinPenaltyMin = 1;
   private int checkinPenaltyMax = 3;
   private int streakBonusEvery = 7;
   private int streakBonusPoints = 2;
   private int lotteryDrawIntervalMinutes = 60;
   private int leaderboardLimit = 10;
   private int transferMinPoints = 1;
   private int transferMaxPoints = 500;

   @Generated
   public String getBotToken() {
      return this.botToken;
   }

   @Generated
   public Integer getApiId() {
      return this.apiId;
   }

   @Generated
   public String getApiHash() {
      return this.apiHash;
   }

   @Generated
   public String getBotName() {
      return this.botName;
   }

   @Generated
   public String getGroupChatId() {
      return this.groupChatId;
   }

   @Generated
   public String getDmChatId() {
      return this.dmChatId;
   }

   @Generated
   public Boolean getPrivateChatMemberRequired() {
      return this.privateChatMemberRequired;
   }

   @Generated
   public Boolean getGroupCheckinEnabled() {
      return this.groupCheckinEnabled;
   }

   @Generated
   public Boolean getFoamBagEnabled() {
      return this.foamBagEnabled;
   }

   @Generated
   public Boolean getRedPacketEnabled() {
      return this.redPacketEnabled;
   }

   @Generated
   public int getRedPacketExpireMinutes() {
      return this.redPacketExpireMinutes;
   }

   @Generated
   public int getDailyMessagePointsLimit() {
      return this.dailyMessagePointsLimit;
   }

   @Generated
   public int getDailySanguoshaPlayLimit() {
      return this.dailySanguoshaPlayLimit;
   }

   @Generated
   public Boolean getGameCommandsEnabled() {
      return this.gameCommandsEnabled;
   }

   @Generated
   public Integer getGameCommandsVersion() {
      return this.gameCommandsVersion;
   }

   @Generated
   public List<String> getEnabledGameCommands() {
      return this.enabledGameCommands;
   }

   @Generated
   public int getCheckinBaseMin() {
      return this.checkinBaseMin;
   }

   @Generated
   public int getCheckinBaseMax() {
      return this.checkinBaseMax;
   }

   @Generated
   public int getCheckinPenaltyChance() {
      return this.checkinPenaltyChance;
   }

   @Generated
   public int getCheckinPenaltyMin() {
      return this.checkinPenaltyMin;
   }

   @Generated
   public int getCheckinPenaltyMax() {
      return this.checkinPenaltyMax;
   }

   @Generated
   public int getStreakBonusEvery() {
      return this.streakBonusEvery;
   }

   @Generated
   public int getStreakBonusPoints() {
      return this.streakBonusPoints;
   }

   @Generated
   public int getLotteryDrawIntervalMinutes() {
      return this.lotteryDrawIntervalMinutes;
   }

   @Generated
   public int getLeaderboardLimit() {
      return this.leaderboardLimit;
   }

   @Generated
   public int getTransferMinPoints() {
      return this.transferMinPoints;
   }

   @Generated
   public int getTransferMaxPoints() {
      return this.transferMaxPoints;
   }

   @Generated
   public void setBotToken(final String botToken) {
      this.botToken = botToken;
   }

   @Generated
   public void setApiId(final Integer apiId) {
      this.apiId = apiId;
   }

   @Generated
   public void setApiHash(final String apiHash) {
      this.apiHash = apiHash;
   }

   @Generated
   public void setBotName(final String botName) {
      this.botName = botName;
   }

   @Generated
   public void setGroupChatId(final String groupChatId) {
      this.groupChatId = groupChatId;
   }

   @Generated
   public void setDmChatId(final String dmChatId) {
      this.dmChatId = dmChatId;
   }

   @Generated
   public void setPrivateChatMemberRequired(final Boolean privateChatMemberRequired) {
      this.privateChatMemberRequired = privateChatMemberRequired;
   }

   @Generated
   public void setGroupCheckinEnabled(final Boolean groupCheckinEnabled) {
      this.groupCheckinEnabled = groupCheckinEnabled;
   }

   @Generated
   public void setFoamBagEnabled(final Boolean foamBagEnabled) {
      this.foamBagEnabled = foamBagEnabled;
   }

   @Generated
   public void setRedPacketEnabled(final Boolean redPacketEnabled) {
      this.redPacketEnabled = redPacketEnabled;
   }

   @Generated
   public void setRedPacketExpireMinutes(final int redPacketExpireMinutes) {
      this.redPacketExpireMinutes = redPacketExpireMinutes;
   }

   @Generated
   public void setDailyMessagePointsLimit(final int dailyMessagePointsLimit) {
      this.dailyMessagePointsLimit = dailyMessagePointsLimit;
   }

   @Generated
   public void setDailySanguoshaPlayLimit(final int dailySanguoshaPlayLimit) {
      this.dailySanguoshaPlayLimit = dailySanguoshaPlayLimit;
   }

   @Generated
   public void setGameCommandsEnabled(final Boolean gameCommandsEnabled) {
      this.gameCommandsEnabled = gameCommandsEnabled;
   }

   @Generated
   public void setGameCommandsVersion(final Integer gameCommandsVersion) {
      this.gameCommandsVersion = gameCommandsVersion;
   }

   @Generated
   public void setEnabledGameCommands(final List<String> enabledGameCommands) {
      this.enabledGameCommands = enabledGameCommands;
   }

   @Generated
   public void setCheckinBaseMin(final int checkinBaseMin) {
      this.checkinBaseMin = checkinBaseMin;
   }

   @Generated
   public void setCheckinBaseMax(final int checkinBaseMax) {
      this.checkinBaseMax = checkinBaseMax;
   }

   @Generated
   public void setCheckinPenaltyChance(final int checkinPenaltyChance) {
      this.checkinPenaltyChance = checkinPenaltyChance;
   }

   @Generated
   public void setCheckinPenaltyMin(final int checkinPenaltyMin) {
      this.checkinPenaltyMin = checkinPenaltyMin;
   }

   @Generated
   public void setCheckinPenaltyMax(final int checkinPenaltyMax) {
      this.checkinPenaltyMax = checkinPenaltyMax;
   }

   @Generated
   public void setStreakBonusEvery(final int streakBonusEvery) {
      this.streakBonusEvery = streakBonusEvery;
   }

   @Generated
   public void setStreakBonusPoints(final int streakBonusPoints) {
      this.streakBonusPoints = streakBonusPoints;
   }

   @Generated
   public void setLotteryDrawIntervalMinutes(final int lotteryDrawIntervalMinutes) {
      this.lotteryDrawIntervalMinutes = lotteryDrawIntervalMinutes;
   }

   @Generated
   public void setLeaderboardLimit(final int leaderboardLimit) {
      this.leaderboardLimit = leaderboardLimit;
   }

   @Generated
   public void setTransferMinPoints(final int transferMinPoints) {
      this.transferMinPoints = transferMinPoints;
   }

   @Generated
   public void setTransferMaxPoints(final int transferMaxPoints) {
      this.transferMaxPoints = transferMaxPoints;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getRedPacketExpireMinutes() != other.getRedPacketExpireMinutes()) {
         return false;
      } else if (this.getDailyMessagePointsLimit() != other.getDailyMessagePointsLimit()) {
         return false;
      } else if (this.getDailySanguoshaPlayLimit() != other.getDailySanguoshaPlayLimit()) {
         return false;
      } else if (this.getCheckinBaseMin() != other.getCheckinBaseMin()) {
         return false;
      } else if (this.getCheckinBaseMax() != other.getCheckinBaseMax()) {
         return false;
      } else if (this.getCheckinPenaltyChance() != other.getCheckinPenaltyChance()) {
         return false;
      } else if (this.getCheckinPenaltyMin() != other.getCheckinPenaltyMin()) {
         return false;
      } else if (this.getCheckinPenaltyMax() != other.getCheckinPenaltyMax()) {
         return false;
      } else if (this.getStreakBonusEvery() != other.getStreakBonusEvery()) {
         return false;
      } else if (this.getStreakBonusPoints() != other.getStreakBonusPoints()) {
         return false;
      } else if (this.getLotteryDrawIntervalMinutes() != other.getLotteryDrawIntervalMinutes()) {
         return false;
      } else if (this.getLeaderboardLimit() != other.getLeaderboardLimit()) {
         return false;
      } else if (this.getTransferMinPoints() != other.getTransferMinPoints()) {
         return false;
      } else if (this.getTransferMaxPoints() != other.getTransferMaxPoints()) {
         return false;
      } else {
         Object this$apiId = this.getApiId();
         Object other$apiId = other.getApiId();
         if (this$apiId == null ? other$apiId == null : this$apiId.equals(other$apiId)) {
            Object this$privateChatMemberRequired = this.getPrivateChatMemberRequired();
            Object other$privateChatMemberRequired = other.getPrivateChatMemberRequired();
            if (this$privateChatMemberRequired == null
               ? other$privateChatMemberRequired == null
               : this$privateChatMemberRequired.equals(other$privateChatMemberRequired)) {
               Object this$groupCheckinEnabled = this.getGroupCheckinEnabled();
               Object other$groupCheckinEnabled = other.getGroupCheckinEnabled();
               if (this$groupCheckinEnabled == null ? other$groupCheckinEnabled == null : this$groupCheckinEnabled.equals(other$groupCheckinEnabled)) {
                  Object this$foamBagEnabled = this.getFoamBagEnabled();
                  Object other$foamBagEnabled = other.getFoamBagEnabled();
                  if (this$foamBagEnabled == null ? other$foamBagEnabled == null : this$foamBagEnabled.equals(other$foamBagEnabled)) {
                     Object this$redPacketEnabled = this.getRedPacketEnabled();
                     Object other$redPacketEnabled = other.getRedPacketEnabled();
                     if (this$redPacketEnabled == null ? other$redPacketEnabled == null : this$redPacketEnabled.equals(other$redPacketEnabled)) {
                        Object this$gameCommandsEnabled = this.getGameCommandsEnabled();
                        Object other$gameCommandsEnabled = other.getGameCommandsEnabled();
                        if (this$gameCommandsEnabled == null ? other$gameCommandsEnabled == null : this$gameCommandsEnabled.equals(other$gameCommandsEnabled)) {
                           Object this$gameCommandsVersion = this.getGameCommandsVersion();
                           Object other$gameCommandsVersion = other.getGameCommandsVersion();
                           if (this$gameCommandsVersion == null
                              ? other$gameCommandsVersion == null
                              : this$gameCommandsVersion.equals(other$gameCommandsVersion)) {
                              Object this$botToken = this.getBotToken();
                              Object other$botToken = other.getBotToken();
                              if (this$botToken == null ? other$botToken == null : this$botToken.equals(other$botToken)) {
                                 Object this$apiHash = this.getApiHash();
                                 Object other$apiHash = other.getApiHash();
                                 if (this$apiHash == null ? other$apiHash == null : this$apiHash.equals(other$apiHash)) {
                                    Object this$botName = this.getBotName();
                                    Object other$botName = other.getBotName();
                                    if (this$botName == null ? other$botName == null : this$botName.equals(other$botName)) {
                                       Object this$groupChatId = this.getGroupChatId();
                                       Object other$groupChatId = other.getGroupChatId();
                                       if (this$groupChatId == null ? other$groupChatId == null : this$groupChatId.equals(other$groupChatId)) {
                                          Object this$dmChatId = this.getDmChatId();
                                          Object other$dmChatId = other.getDmChatId();
                                          if (this$dmChatId == null ? other$dmChatId == null : this$dmChatId.equals(other$dmChatId)) {
                                             Object this$enabledGameCommands = this.getEnabledGameCommands();
                                             Object other$enabledGameCommands = other.getEnabledGameCommands();
                                             return this$enabledGameCommands == null
                                                ? other$enabledGameCommands == null
                                                : this$enabledGameCommands.equals(other$enabledGameCommands);
                                          } else {
                                             return false;
                                          }
                                       } else {
                                          return false;
                                       }
                                    } else {
                                       return false;
                                    }
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
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
      return other instanceof PointsBotConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getRedPacketExpireMinutes();
      result = result * 59 + this.getDailyMessagePointsLimit();
      result = result * 59 + this.getDailySanguoshaPlayLimit();
      result = result * 59 + this.getCheckinBaseMin();
      result = result * 59 + this.getCheckinBaseMax();
      result = result * 59 + this.getCheckinPenaltyChance();
      result = result * 59 + this.getCheckinPenaltyMin();
      result = result * 59 + this.getCheckinPenaltyMax();
      result = result * 59 + this.getStreakBonusEvery();
      result = result * 59 + this.getStreakBonusPoints();
      result = result * 59 + this.getLotteryDrawIntervalMinutes();
      result = result * 59 + this.getLeaderboardLimit();
      result = result * 59 + this.getTransferMinPoints();
      result = result * 59 + this.getTransferMaxPoints();
      Object $apiId = this.getApiId();
      result = result * 59 + ($apiId == null ? 43 : $apiId.hashCode());
      Object $privateChatMemberRequired = this.getPrivateChatMemberRequired();
      result = result * 59 + ($privateChatMemberRequired == null ? 43 : $privateChatMemberRequired.hashCode());
      Object $groupCheckinEnabled = this.getGroupCheckinEnabled();
      result = result * 59 + ($groupCheckinEnabled == null ? 43 : $groupCheckinEnabled.hashCode());
      Object $foamBagEnabled = this.getFoamBagEnabled();
      result = result * 59 + ($foamBagEnabled == null ? 43 : $foamBagEnabled.hashCode());
      Object $redPacketEnabled = this.getRedPacketEnabled();
      result = result * 59 + ($redPacketEnabled == null ? 43 : $redPacketEnabled.hashCode());
      Object $gameCommandsEnabled = this.getGameCommandsEnabled();
      result = result * 59 + ($gameCommandsEnabled == null ? 43 : $gameCommandsEnabled.hashCode());
      Object $gameCommandsVersion = this.getGameCommandsVersion();
      result = result * 59 + ($gameCommandsVersion == null ? 43 : $gameCommandsVersion.hashCode());
      Object $botToken = this.getBotToken();
      result = result * 59 + ($botToken == null ? 43 : $botToken.hashCode());
      Object $apiHash = this.getApiHash();
      result = result * 59 + ($apiHash == null ? 43 : $apiHash.hashCode());
      Object $botName = this.getBotName();
      result = result * 59 + ($botName == null ? 43 : $botName.hashCode());
      Object $groupChatId = this.getGroupChatId();
      result = result * 59 + ($groupChatId == null ? 43 : $groupChatId.hashCode());
      Object $dmChatId = this.getDmChatId();
      result = result * 59 + ($dmChatId == null ? 43 : $dmChatId.hashCode());
      Object $enabledGameCommands = this.getEnabledGameCommands();
      return result * 59 + ($enabledGameCommands == null ? 43 : $enabledGameCommands.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotConfig(botToken="
         + this.getBotToken()
         + ", apiId="
         + this.getApiId()
         + ", apiHash="
         + this.getApiHash()
         + ", botName="
         + this.getBotName()
         + ", groupChatId="
         + this.getGroupChatId()
         + ", dmChatId="
         + this.getDmChatId()
         + ", privateChatMemberRequired="
         + this.getPrivateChatMemberRequired()
         + ", groupCheckinEnabled="
         + this.getGroupCheckinEnabled()
         + ", foamBagEnabled="
         + this.getFoamBagEnabled()
         + ", redPacketEnabled="
         + this.getRedPacketEnabled()
         + ", redPacketExpireMinutes="
         + this.getRedPacketExpireMinutes()
         + ", dailyMessagePointsLimit="
         + this.getDailyMessagePointsLimit()
         + ", dailySanguoshaPlayLimit="
         + this.getDailySanguoshaPlayLimit()
         + ", gameCommandsEnabled="
         + this.getGameCommandsEnabled()
         + ", gameCommandsVersion="
         + this.getGameCommandsVersion()
         + ", enabledGameCommands="
         + this.getEnabledGameCommands()
         + ", checkinBaseMin="
         + this.getCheckinBaseMin()
         + ", checkinBaseMax="
         + this.getCheckinBaseMax()
         + ", checkinPenaltyChance="
         + this.getCheckinPenaltyChance()
         + ", checkinPenaltyMin="
         + this.getCheckinPenaltyMin()
         + ", checkinPenaltyMax="
         + this.getCheckinPenaltyMax()
         + ", streakBonusEvery="
         + this.getStreakBonusEvery()
         + ", streakBonusPoints="
         + this.getStreakBonusPoints()
         + ", lotteryDrawIntervalMinutes="
         + this.getLotteryDrawIntervalMinutes()
         + ", leaderboardLimit="
         + this.getLeaderboardLimit()
         + ", transferMinPoints="
         + this.getTransferMinPoints()
         + ", transferMaxPoints="
         + this.getTransferMaxPoints()
         + ")";
   }
}
