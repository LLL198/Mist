package com.una.embyhub.model.dto.request.pointsbot;

import com.una.embyhub.pointsbot.model.BrainGameConfig;
import com.una.embyhub.pointsbot.model.HellDiceGameConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;

public class PointsBotGameConfigUpdate {
   @NotBlank(
      message = "游戏编码不能为空"
   )
   private String gameCode;
   @NotNull(
      message = "启用状态不能为空"
   )
   private Boolean enabled;
   @Valid
   private BrainGameConfig brainConfig;
   @Valid
   private HellDiceGameConfig hellDiceConfig;

   @Generated
   public String getGameCode() {
      return this.gameCode;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
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
   public void setGameCode(final String gameCode) {
      this.gameCode = gameCode;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotGameConfigUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$gameCode = this.getGameCode();
            Object other$gameCode = other.getGameCode();
            if (this$gameCode == null ? other$gameCode == null : this$gameCode.equals(other$gameCode)) {
               Object this$brainConfig = this.getBrainConfig();
               Object other$brainConfig = other.getBrainConfig();
               if (this$brainConfig == null ? other$brainConfig == null : this$brainConfig.equals(other$brainConfig)) {
                  Object this$hellDiceConfig = this.getHellDiceConfig();
                  Object other$hellDiceConfig = other.getHellDiceConfig();
                  return this$hellDiceConfig == null ? other$hellDiceConfig == null : this$hellDiceConfig.equals(other$hellDiceConfig);
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
      return other instanceof PointsBotGameConfigUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $gameCode = this.getGameCode();
      result = result * 59 + ($gameCode == null ? 43 : $gameCode.hashCode());
      Object $brainConfig = this.getBrainConfig();
      result = result * 59 + ($brainConfig == null ? 43 : $brainConfig.hashCode());
      Object $hellDiceConfig = this.getHellDiceConfig();
      return result * 59 + ($hellDiceConfig == null ? 43 : $hellDiceConfig.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotGameConfigUpdate(gameCode="
         + this.getGameCode()
         + ", enabled="
         + this.getEnabled()
         + ", brainConfig="
         + this.getBrainConfig()
         + ", hellDiceConfig="
         + this.getHellDiceConfig()
         + ")";
   }
}
