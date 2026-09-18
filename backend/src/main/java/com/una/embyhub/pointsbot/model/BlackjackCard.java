package com.una.embyhub.pointsbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Generated;

public class BlackjackCard {
   private BlackjackCard.Suit suit;
   private BlackjackCard.Rank rank;

   @JsonIgnore
   public int getValue() {
      return this.rank.getValue();
   }

   @Override
   public String toString() {
      return this.suit.getSymbol() + this.rank.getName();
   }

   @Generated
   public BlackjackCard.Suit getSuit() {
      return this.suit;
   }

   @Generated
   public BlackjackCard.Rank getRank() {
      return this.rank;
   }

   @Generated
   public void setSuit(final BlackjackCard.Suit suit) {
      this.suit = suit;
   }

   @Generated
   public void setRank(final BlackjackCard.Rank rank) {
      this.rank = rank;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BlackjackCard other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$suit = this.getSuit();
         Object other$suit = other.getSuit();
         if (this$suit == null ? other$suit == null : this$suit.equals(other$suit)) {
            Object this$rank = this.getRank();
            Object other$rank = other.getRank();
            return this$rank == null ? other$rank == null : this$rank.equals(other$rank);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof BlackjackCard;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $suit = this.getSuit();
      result = result * 59 + ($suit == null ? 43 : $suit.hashCode());
      Object $rank = this.getRank();
      return result * 59 + ($rank == null ? 43 : $rank.hashCode());
   }

   @Generated
   public BlackjackCard(final BlackjackCard.Suit suit, final BlackjackCard.Rank rank) {
      this.suit = suit;
      this.rank = rank;
   }

   @Generated
   public BlackjackCard() {
   }

   public static enum Rank {
      TWO("2", 2),
      THREE("3", 3),
      FOUR("4", 4),
      FIVE("5", 5),
      SIX("6", 6),
      SEVEN("7", 7),
      EIGHT("8", 8),
      NINE("9", 9),
      TEN("10", 10),
      JACK("J", 10),
      QUEEN("Q", 10),
      KING("K", 10),
      ACE("A", 11);

      private final String name;
      private final int value;

      @Generated
      public String getName() {
         return this.name;
      }

      @Generated
      public int getValue() {
         return this.value;
      }

      @Generated
      private Rank(final String name, final int value) {
         this.name = name;
         this.value = value;
      }
   }

   public static enum Suit {
      SPADE("♠"),
      HEART("❤"),
      CLUB("♣"),
      DIAMOND("♦");

      private final String symbol;

      @Generated
      public String getSymbol() {
         return this.symbol;
      }

      @Generated
      private Suit(final String symbol) {
         this.symbol = symbol;
      }
   }
}
