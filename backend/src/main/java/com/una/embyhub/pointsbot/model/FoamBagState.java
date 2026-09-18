package com.una.embyhub.pointsbot.model;

import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagConfigResponse;
import com.una.embyhub.model.entity.PointsBotFoamBag;
import java.time.LocalDateTime;
import lombok.Generated;

public class FoamBagState {
   private PointsBotFoamBagConfigResponse config;
   private PointsBotFoamBag activeBag;
   private LocalDateTime penaltyUntil;
   private long points;
   private int usedToday;

   @Generated
   FoamBagState(
      final PointsBotFoamBagConfigResponse config, final PointsBotFoamBag activeBag, final LocalDateTime penaltyUntil, final long points, final int usedToday
   ) {
      this.config = config;
      this.activeBag = activeBag;
      this.penaltyUntil = penaltyUntil;
      this.points = points;
      this.usedToday = usedToday;
   }

   @Generated
   public static FoamBagState.FoamBagStateBuilder builder() {
      return new FoamBagState.FoamBagStateBuilder();
   }

   @Generated
   public PointsBotFoamBagConfigResponse getConfig() {
      return this.config;
   }

   @Generated
   public PointsBotFoamBag getActiveBag() {
      return this.activeBag;
   }

   @Generated
   public LocalDateTime getPenaltyUntil() {
      return this.penaltyUntil;
   }

   @Generated
   public long getPoints() {
      return this.points;
   }

   @Generated
   public int getUsedToday() {
      return this.usedToday;
   }

   @Generated
   public void setConfig(final PointsBotFoamBagConfigResponse config) {
      this.config = config;
   }

   @Generated
   public void setActiveBag(final PointsBotFoamBag activeBag) {
      this.activeBag = activeBag;
   }

   @Generated
   public void setPenaltyUntil(final LocalDateTime penaltyUntil) {
      this.penaltyUntil = penaltyUntil;
   }

   @Generated
   public void setPoints(final long points) {
      this.points = points;
   }

   @Generated
   public void setUsedToday(final int usedToday) {
      this.usedToday = usedToday;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamBagState other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getPoints() != other.getPoints()) {
         return false;
      } else if (this.getUsedToday() != other.getUsedToday()) {
         return false;
      } else {
         Object this$config = this.getConfig();
         Object other$config = other.getConfig();
         if (this$config == null ? other$config == null : this$config.equals(other$config)) {
            Object this$activeBag = this.getActiveBag();
            Object other$activeBag = other.getActiveBag();
            if (this$activeBag == null ? other$activeBag == null : this$activeBag.equals(other$activeBag)) {
               Object this$penaltyUntil = this.getPenaltyUntil();
               Object other$penaltyUntil = other.getPenaltyUntil();
               return this$penaltyUntil == null ? other$penaltyUntil == null : this$penaltyUntil.equals(other$penaltyUntil);
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
      return other instanceof FoamBagState;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $points = this.getPoints();
      result = result * 59 + (int)($points >>> 32 ^ $points);
      result = result * 59 + this.getUsedToday();
      Object $config = this.getConfig();
      result = result * 59 + ($config == null ? 43 : $config.hashCode());
      Object $activeBag = this.getActiveBag();
      result = result * 59 + ($activeBag == null ? 43 : $activeBag.hashCode());
      Object $penaltyUntil = this.getPenaltyUntil();
      return result * 59 + ($penaltyUntil == null ? 43 : $penaltyUntil.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamBagState(config="
         + this.getConfig()
         + ", activeBag="
         + this.getActiveBag()
         + ", penaltyUntil="
         + this.getPenaltyUntil()
         + ", points="
         + this.getPoints()
         + ", usedToday="
         + this.getUsedToday()
         + ")";
   }

   @Generated
   public static class FoamBagStateBuilder {
      @Generated
      private PointsBotFoamBagConfigResponse config;
      @Generated
      private PointsBotFoamBag activeBag;
      @Generated
      private LocalDateTime penaltyUntil;
      @Generated
      private long points;
      @Generated
      private int usedToday;

      @Generated
      FoamBagStateBuilder() {
      }

      @Generated
      public FoamBagState.FoamBagStateBuilder config(final PointsBotFoamBagConfigResponse config) {
         this.config = config;
         return this;
      }

      @Generated
      public FoamBagState.FoamBagStateBuilder activeBag(final PointsBotFoamBag activeBag) {
         this.activeBag = activeBag;
         return this;
      }

      @Generated
      public FoamBagState.FoamBagStateBuilder penaltyUntil(final LocalDateTime penaltyUntil) {
         this.penaltyUntil = penaltyUntil;
         return this;
      }

      @Generated
      public FoamBagState.FoamBagStateBuilder points(final long points) {
         this.points = points;
         return this;
      }

      @Generated
      public FoamBagState.FoamBagStateBuilder usedToday(final int usedToday) {
         this.usedToday = usedToday;
         return this;
      }

      @Generated
      public FoamBagState build() {
         return new FoamBagState(this.config, this.activeBag, this.penaltyUntil, this.points, this.usedToday);
      }

      @Generated
      @Override
      public String toString() {
         return "FoamBagState.FoamBagStateBuilder(config="
            + this.config
            + ", activeBag="
            + this.activeBag
            + ", penaltyUntil="
            + this.penaltyUntil
            + ", points="
            + this.points
            + ", usedToday="
            + this.usedToday
            + ")";
      }
   }
}
