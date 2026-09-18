package com.una.embyhub.pointsbot.model;

import lombok.Generated;

public class CheckinResult {
   private boolean alreadyCheckedIn;
   private int delta;
   private int streak;
   private long totalPoints;
   private String message;

   @Generated
   CheckinResult(final boolean alreadyCheckedIn, final int delta, final int streak, final long totalPoints, final String message) {
      this.alreadyCheckedIn = alreadyCheckedIn;
      this.delta = delta;
      this.streak = streak;
      this.totalPoints = totalPoints;
      this.message = message;
   }

   @Generated
   public static CheckinResult.CheckinResultBuilder builder() {
      return new CheckinResult.CheckinResultBuilder();
   }

   @Generated
   public boolean isAlreadyCheckedIn() {
      return this.alreadyCheckedIn;
   }

   @Generated
   public int getDelta() {
      return this.delta;
   }

   @Generated
   public int getStreak() {
      return this.streak;
   }

   @Generated
   public long getTotalPoints() {
      return this.totalPoints;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public void setAlreadyCheckedIn(final boolean alreadyCheckedIn) {
      this.alreadyCheckedIn = alreadyCheckedIn;
   }

   @Generated
   public void setDelta(final int delta) {
      this.delta = delta;
   }

   @Generated
   public void setStreak(final int streak) {
      this.streak = streak;
   }

   @Generated
   public void setTotalPoints(final long totalPoints) {
      this.totalPoints = totalPoints;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CheckinResult other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isAlreadyCheckedIn() != other.isAlreadyCheckedIn()) {
         return false;
      } else if (this.getDelta() != other.getDelta()) {
         return false;
      } else if (this.getStreak() != other.getStreak()) {
         return false;
      } else if (this.getTotalPoints() != other.getTotalPoints()) {
         return false;
      } else {
         Object this$message = this.getMessage();
         Object other$message = other.getMessage();
         return this$message == null ? other$message == null : this$message.equals(other$message);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof CheckinResult;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isAlreadyCheckedIn() ? 79 : 97);
      result = result * 59 + this.getDelta();
      result = result * 59 + this.getStreak();
      long $totalPoints = this.getTotalPoints();
      result = result * 59 + (int)($totalPoints >>> 32 ^ $totalPoints);
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "CheckinResult(alreadyCheckedIn="
         + this.isAlreadyCheckedIn()
         + ", delta="
         + this.getDelta()
         + ", streak="
         + this.getStreak()
         + ", totalPoints="
         + this.getTotalPoints()
         + ", message="
         + this.getMessage()
         + ")";
   }

   @Generated
   public static class CheckinResultBuilder {
      @Generated
      private boolean alreadyCheckedIn;
      @Generated
      private int delta;
      @Generated
      private int streak;
      @Generated
      private long totalPoints;
      @Generated
      private String message;

      @Generated
      CheckinResultBuilder() {
      }

      @Generated
      public CheckinResult.CheckinResultBuilder alreadyCheckedIn(final boolean alreadyCheckedIn) {
         this.alreadyCheckedIn = alreadyCheckedIn;
         return this;
      }

      @Generated
      public CheckinResult.CheckinResultBuilder delta(final int delta) {
         this.delta = delta;
         return this;
      }

      @Generated
      public CheckinResult.CheckinResultBuilder streak(final int streak) {
         this.streak = streak;
         return this;
      }

      @Generated
      public CheckinResult.CheckinResultBuilder totalPoints(final long totalPoints) {
         this.totalPoints = totalPoints;
         return this;
      }

      @Generated
      public CheckinResult.CheckinResultBuilder message(final String message) {
         this.message = message;
         return this;
      }

      @Generated
      public CheckinResult build() {
         return new CheckinResult(this.alreadyCheckedIn, this.delta, this.streak, this.totalPoints, this.message);
      }

      @Generated
      @Override
      public String toString() {
         return "CheckinResult.CheckinResultBuilder(alreadyCheckedIn="
            + this.alreadyCheckedIn
            + ", delta="
            + this.delta
            + ", streak="
            + this.streak
            + ", totalPoints="
            + this.totalPoints
            + ", message="
            + this.message
            + ")";
      }
   }
}
