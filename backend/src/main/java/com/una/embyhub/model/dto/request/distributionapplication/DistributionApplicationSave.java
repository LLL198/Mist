package com.una.embyhub.model.dto.request.distributionapplication;

import java.io.Serializable;
import lombok.Generated;

public class DistributionApplicationSave implements Serializable {
   private Integer cardCount;
   private Integer cardDays;
   private Long embyInfoId;

   @Generated
   public Integer getCardCount() {
      return this.cardCount;
   }

   @Generated
   public Integer getCardDays() {
      return this.cardDays;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setCardCount(final Integer cardCount) {
      this.cardCount = cardCount;
   }

   @Generated
   public void setCardDays(final Integer cardDays) {
      this.cardDays = cardDays;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DistributionApplicationSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$cardCount = this.getCardCount();
         Object other$cardCount = other.getCardCount();
         if (this$cardCount == null ? other$cardCount == null : this$cardCount.equals(other$cardCount)) {
            Object this$cardDays = this.getCardDays();
            Object other$cardDays = other.getCardDays();
            if (this$cardDays == null ? other$cardDays == null : this$cardDays.equals(other$cardDays)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               return this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId);
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
      return other instanceof DistributionApplicationSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $cardCount = this.getCardCount();
      result = result * 59 + ($cardCount == null ? 43 : $cardCount.hashCode());
      Object $cardDays = this.getCardDays();
      result = result * 59 + ($cardDays == null ? 43 : $cardDays.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      return result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionApplicationSave(cardCount=" + this.getCardCount() + ", cardDays=" + this.getCardDays() + ", embyInfoId=" + this.getEmbyInfoId() + ")";
   }
}
