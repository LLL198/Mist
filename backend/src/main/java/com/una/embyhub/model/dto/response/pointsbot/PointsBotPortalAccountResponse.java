package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public final class PointsBotPortalAccountResponse implements Serializable {
   private final boolean telegramBound;
   private final boolean pointsAccountExists;
   private final long balance;
   private final String levelName;
   private final String telegramUsername;

   @Generated
   PointsBotPortalAccountResponse(
      final boolean telegramBound, final boolean pointsAccountExists, final long balance, final String levelName, final String telegramUsername
   ) {
      this.telegramBound = telegramBound;
      this.pointsAccountExists = pointsAccountExists;
      this.balance = balance;
      this.levelName = levelName;
      this.telegramUsername = telegramUsername;
   }

   @Generated
   public static PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder builder() {
      return new PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder();
   }

   @Generated
   public boolean isTelegramBound() {
      return this.telegramBound;
   }

   @Generated
   public boolean isPointsAccountExists() {
      return this.pointsAccountExists;
   }

   @Generated
   public long getBalance() {
      return this.balance;
   }

   @Generated
   public String getLevelName() {
      return this.levelName;
   }

   @Generated
   public String getTelegramUsername() {
      return this.telegramUsername;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalAccountResponse other)) {
         return false;
      } else if (this.isTelegramBound() != other.isTelegramBound()) {
         return false;
      } else if (this.isPointsAccountExists() != other.isPointsAccountExists()) {
         return false;
      } else if (this.getBalance() != other.getBalance()) {
         return false;
      } else {
         Object this$levelName = this.getLevelName();
         Object other$levelName = other.getLevelName();
         if (this$levelName == null ? other$levelName == null : this$levelName.equals(other$levelName)) {
            Object this$telegramUsername = this.getTelegramUsername();
            Object other$telegramUsername = other.getTelegramUsername();
            return this$telegramUsername == null ? other$telegramUsername == null : this$telegramUsername.equals(other$telegramUsername);
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isTelegramBound() ? 79 : 97);
      result = result * 59 + (this.isPointsAccountExists() ? 79 : 97);
      long $balance = this.getBalance();
      result = result * 59 + (int)($balance >>> 32 ^ $balance);
      Object $levelName = this.getLevelName();
      result = result * 59 + ($levelName == null ? 43 : $levelName.hashCode());
      Object $telegramUsername = this.getTelegramUsername();
      return result * 59 + ($telegramUsername == null ? 43 : $telegramUsername.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalAccountResponse(telegramBound="
         + this.isTelegramBound()
         + ", pointsAccountExists="
         + this.isPointsAccountExists()
         + ", balance="
         + this.getBalance()
         + ", levelName="
         + this.getLevelName()
         + ", telegramUsername="
         + this.getTelegramUsername()
         + ")";
   }

   @Generated
   public static class PointsBotPortalAccountResponseBuilder {
      @Generated
      private boolean telegramBound;
      @Generated
      private boolean pointsAccountExists;
      @Generated
      private long balance;
      @Generated
      private String levelName;
      @Generated
      private String telegramUsername;

      @Generated
      PointsBotPortalAccountResponseBuilder() {
      }

      @Generated
      public PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder telegramBound(final boolean telegramBound) {
         this.telegramBound = telegramBound;
         return this;
      }

      @Generated
      public PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder pointsAccountExists(final boolean pointsAccountExists) {
         this.pointsAccountExists = pointsAccountExists;
         return this;
      }

      @Generated
      public PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder balance(final long balance) {
         this.balance = balance;
         return this;
      }

      @Generated
      public PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder levelName(final String levelName) {
         this.levelName = levelName;
         return this;
      }

      @Generated
      public PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder telegramUsername(final String telegramUsername) {
         this.telegramUsername = telegramUsername;
         return this;
      }

      @Generated
      public PointsBotPortalAccountResponse build() {
         return new PointsBotPortalAccountResponse(this.telegramBound, this.pointsAccountExists, this.balance, this.levelName, this.telegramUsername);
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotPortalAccountResponse.PointsBotPortalAccountResponseBuilder(telegramBound="
            + this.telegramBound
            + ", pointsAccountExists="
            + this.pointsAccountExists
            + ", balance="
            + this.balance
            + ", levelName="
            + this.levelName
            + ", telegramUsername="
            + this.telegramUsername
            + ")";
      }
   }
}
