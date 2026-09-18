package com.una.embyhub.pointsbot.model;

import lombok.Generated;

public class SanguoshaCard {
   private String code;
   private String name;
   private String emoji;
   private String effect;
   private int minPoints;
   private int maxPoints;

   @Generated
   public String getCode() {
      return this.code;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getEmoji() {
      return this.emoji;
   }

   @Generated
   public String getEffect() {
      return this.effect;
   }

   @Generated
   public int getMinPoints() {
      return this.minPoints;
   }

   @Generated
   public int getMaxPoints() {
      return this.maxPoints;
   }

   @Generated
   public void setCode(final String code) {
      this.code = code;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setEmoji(final String emoji) {
      this.emoji = emoji;
   }

   @Generated
   public void setEffect(final String effect) {
      this.effect = effect;
   }

   @Generated
   public void setMinPoints(final int minPoints) {
      this.minPoints = minPoints;
   }

   @Generated
   public void setMaxPoints(final int maxPoints) {
      this.maxPoints = maxPoints;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SanguoshaCard other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getMinPoints() != other.getMinPoints()) {
         return false;
      } else if (this.getMaxPoints() != other.getMaxPoints()) {
         return false;
      } else {
         Object this$code = this.getCode();
         Object other$code = other.getCode();
         if (this$code == null ? other$code == null : this$code.equals(other$code)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$emoji = this.getEmoji();
               Object other$emoji = other.getEmoji();
               if (this$emoji == null ? other$emoji == null : this$emoji.equals(other$emoji)) {
                  Object this$effect = this.getEffect();
                  Object other$effect = other.getEffect();
                  return this$effect == null ? other$effect == null : this$effect.equals(other$effect);
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
      return other instanceof SanguoshaCard;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getMinPoints();
      result = result * 59 + this.getMaxPoints();
      Object $code = this.getCode();
      result = result * 59 + ($code == null ? 43 : $code.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $emoji = this.getEmoji();
      result = result * 59 + ($emoji == null ? 43 : $emoji.hashCode());
      Object $effect = this.getEffect();
      return result * 59 + ($effect == null ? 43 : $effect.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SanguoshaCard(code="
         + this.getCode()
         + ", name="
         + this.getName()
         + ", emoji="
         + this.getEmoji()
         + ", effect="
         + this.getEffect()
         + ", minPoints="
         + this.getMinPoints()
         + ", maxPoints="
         + this.getMaxPoints()
         + ")";
   }

   @Generated
   public SanguoshaCard() {
   }

   @Generated
   public SanguoshaCard(final String code, final String name, final String emoji, final String effect, final int minPoints, final int maxPoints) {
      this.code = code;
      this.name = name;
      this.emoji = emoji;
      this.effect = effect;
      this.minPoints = minPoints;
      this.maxPoints = maxPoints;
   }
}
