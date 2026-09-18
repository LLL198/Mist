package com.una.embyhub.model.dto.response.pointsbot;

import com.una.embyhub.pointsbot.model.BrainGameConfig;
import com.una.embyhub.pointsbot.model.HellDiceGameConfig;
import com.una.embyhub.pointsbot.service.HellDiceGameService;
import lombok.Generated;

public class PointsBotGameConfigResponse {
   private String gameCode;
   private String gameName;
   private String command;
   private String description;
   private String icon;
   private boolean enabled;
   private int sortOrder;
   private boolean configurable;
   private BrainGameConfig brainConfig;
   private HellDiceGameConfig hellDiceConfig;
   private HellDiceGameService.VaultView hellDiceVault;

   @Generated
   PointsBotGameConfigResponse(
      final String gameCode,
      final String gameName,
      final String command,
      final String description,
      final String icon,
      final boolean enabled,
      final int sortOrder,
      final boolean configurable,
      final BrainGameConfig brainConfig,
      final HellDiceGameConfig hellDiceConfig,
      final HellDiceGameService.VaultView hellDiceVault
   ) {
      this.gameCode = gameCode;
      this.gameName = gameName;
      this.command = command;
      this.description = description;
      this.icon = icon;
      this.enabled = enabled;
      this.sortOrder = sortOrder;
      this.configurable = configurable;
      this.brainConfig = brainConfig;
      this.hellDiceConfig = hellDiceConfig;
      this.hellDiceVault = hellDiceVault;
   }

   @Generated
   public static PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder builder() {
      return new PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder();
   }

   @Generated
   public String getGameCode() {
      return this.gameCode;
   }

   @Generated
   public String getGameName() {
      return this.gameName;
   }

   @Generated
   public String getCommand() {
      return this.command;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public String getIcon() {
      return this.icon;
   }

   @Generated
   public boolean isEnabled() {
      return this.enabled;
   }

   @Generated
   public int getSortOrder() {
      return this.sortOrder;
   }

   @Generated
   public boolean isConfigurable() {
      return this.configurable;
   }

   @Generated
   public BrainGameConfig getBrainConfig() {
      return this.brainConfig;
   }

   @Generated
   public HellDiceGameConfig getHellDiceConfig() {
      return this.hellDiceConfig;
   }

   @Generated
   public HellDiceGameService.VaultView getHellDiceVault() {
      return this.hellDiceVault;
   }

   @Generated
   public void setGameCode(final String gameCode) {
      this.gameCode = gameCode;
   }

   @Generated
   public void setGameName(final String gameName) {
      this.gameName = gameName;
   }

   @Generated
   public void setCommand(final String command) {
      this.command = command;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setIcon(final String icon) {
      this.icon = icon;
   }

   @Generated
   public void setEnabled(final boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setSortOrder(final int sortOrder) {
      this.sortOrder = sortOrder;
   }

   @Generated
   public void setConfigurable(final boolean configurable) {
      this.configurable = configurable;
   }

   @Generated
   public void setBrainConfig(final BrainGameConfig brainConfig) {
      this.brainConfig = brainConfig;
   }

   @Generated
   public void setHellDiceConfig(final HellDiceGameConfig hellDiceConfig) {
      this.hellDiceConfig = hellDiceConfig;
   }

   @Generated
   public void setHellDiceVault(final HellDiceGameService.VaultView hellDiceVault) {
      this.hellDiceVault = hellDiceVault;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotGameConfigResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isEnabled() != other.isEnabled()) {
         return false;
      } else if (this.getSortOrder() != other.getSortOrder()) {
         return false;
      } else if (this.isConfigurable() != other.isConfigurable()) {
         return false;
      } else {
         Object this$gameCode = this.getGameCode();
         Object other$gameCode = other.getGameCode();
         if (this$gameCode == null ? other$gameCode == null : this$gameCode.equals(other$gameCode)) {
            Object this$gameName = this.getGameName();
            Object other$gameName = other.getGameName();
            if (this$gameName == null ? other$gameName == null : this$gameName.equals(other$gameName)) {
               Object this$command = this.getCommand();
               Object other$command = other.getCommand();
               if (this$command == null ? other$command == null : this$command.equals(other$command)) {
                  Object this$description = this.getDescription();
                  Object other$description = other.getDescription();
                  if (this$description == null ? other$description == null : this$description.equals(other$description)) {
                     Object this$icon = this.getIcon();
                     Object other$icon = other.getIcon();
                     if (this$icon == null ? other$icon == null : this$icon.equals(other$icon)) {
                        Object this$brainConfig = this.getBrainConfig();
                        Object other$brainConfig = other.getBrainConfig();
                        if (this$brainConfig == null ? other$brainConfig == null : this$brainConfig.equals(other$brainConfig)) {
                           Object this$hellDiceConfig = this.getHellDiceConfig();
                           Object other$hellDiceConfig = other.getHellDiceConfig();
                           if (this$hellDiceConfig == null ? other$hellDiceConfig == null : this$hellDiceConfig.equals(other$hellDiceConfig)) {
                              Object this$hellDiceVault = this.getHellDiceVault();
                              Object other$hellDiceVault = other.getHellDiceVault();
                              return this$hellDiceVault == null ? other$hellDiceVault == null : this$hellDiceVault.equals(other$hellDiceVault);
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
      return other instanceof PointsBotGameConfigResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isEnabled() ? 79 : 97);
      result = result * 59 + this.getSortOrder();
      result = result * 59 + (this.isConfigurable() ? 79 : 97);
      Object $gameCode = this.getGameCode();
      result = result * 59 + ($gameCode == null ? 43 : $gameCode.hashCode());
      Object $gameName = this.getGameName();
      result = result * 59 + ($gameName == null ? 43 : $gameName.hashCode());
      Object $command = this.getCommand();
      result = result * 59 + ($command == null ? 43 : $command.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
      Object $icon = this.getIcon();
      result = result * 59 + ($icon == null ? 43 : $icon.hashCode());
      Object $brainConfig = this.getBrainConfig();
      result = result * 59 + ($brainConfig == null ? 43 : $brainConfig.hashCode());
      Object $hellDiceConfig = this.getHellDiceConfig();
      result = result * 59 + ($hellDiceConfig == null ? 43 : $hellDiceConfig.hashCode());
      Object $hellDiceVault = this.getHellDiceVault();
      return result * 59 + ($hellDiceVault == null ? 43 : $hellDiceVault.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotGameConfigResponse(gameCode="
         + this.getGameCode()
         + ", gameName="
         + this.getGameName()
         + ", command="
         + this.getCommand()
         + ", description="
         + this.getDescription()
         + ", icon="
         + this.getIcon()
         + ", enabled="
         + this.isEnabled()
         + ", sortOrder="
         + this.getSortOrder()
         + ", configurable="
         + this.isConfigurable()
         + ", brainConfig="
         + this.getBrainConfig()
         + ", hellDiceConfig="
         + this.getHellDiceConfig()
         + ", hellDiceVault="
         + this.getHellDiceVault()
         + ")";
   }

   @Generated
   public static class PointsBotGameConfigResponseBuilder {
      @Generated
      private String gameCode;
      @Generated
      private String gameName;
      @Generated
      private String command;
      @Generated
      private String description;
      @Generated
      private String icon;
      @Generated
      private boolean enabled;
      @Generated
      private int sortOrder;
      @Generated
      private boolean configurable;
      @Generated
      private BrainGameConfig brainConfig;
      @Generated
      private HellDiceGameConfig hellDiceConfig;
      @Generated
      private HellDiceGameService.VaultView hellDiceVault;

      @Generated
      PointsBotGameConfigResponseBuilder() {
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder gameCode(final String gameCode) {
         this.gameCode = gameCode;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder gameName(final String gameName) {
         this.gameName = gameName;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder command(final String command) {
         this.command = command;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder icon(final String icon) {
         this.icon = icon;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder enabled(final boolean enabled) {
         this.enabled = enabled;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder sortOrder(final int sortOrder) {
         this.sortOrder = sortOrder;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder configurable(final boolean configurable) {
         this.configurable = configurable;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder brainConfig(final BrainGameConfig brainConfig) {
         this.brainConfig = brainConfig;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder hellDiceConfig(final HellDiceGameConfig hellDiceConfig) {
         this.hellDiceConfig = hellDiceConfig;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder hellDiceVault(final HellDiceGameService.VaultView hellDiceVault) {
         this.hellDiceVault = hellDiceVault;
         return this;
      }

      @Generated
      public PointsBotGameConfigResponse build() {
         return new PointsBotGameConfigResponse(
            this.gameCode,
            this.gameName,
            this.command,
            this.description,
            this.icon,
            this.enabled,
            this.sortOrder,
            this.configurable,
            this.brainConfig,
            this.hellDiceConfig,
            this.hellDiceVault
         );
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotGameConfigResponse.PointsBotGameConfigResponseBuilder(gameCode="
            + this.gameCode
            + ", gameName="
            + this.gameName
            + ", command="
            + this.command
            + ", description="
            + this.description
            + ", icon="
            + this.icon
            + ", enabled="
            + this.enabled
            + ", sortOrder="
            + this.sortOrder
            + ", configurable="
            + this.configurable
            + ", brainConfig="
            + this.brainConfig
            + ", hellDiceConfig="
            + this.hellDiceConfig
            + ", hellDiceVault="
            + this.hellDiceVault
            + ")";
      }
   }
}
