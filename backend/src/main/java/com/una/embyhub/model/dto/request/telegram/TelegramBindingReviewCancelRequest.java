package com.una.embyhub.model.dto.request.telegram;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class TelegramBindingReviewCancelRequest implements Serializable {
   @Size(
      max = 64,
      message = "审批指纹过长"
   )
   private String reviewUuid;

   @Generated
   public String getReviewUuid() {
      return this.reviewUuid;
   }

   @Generated
   public void setReviewUuid(final String reviewUuid) {
      this.reviewUuid = reviewUuid;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBindingReviewCancelRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$reviewUuid = this.getReviewUuid();
         Object other$reviewUuid = other.getReviewUuid();
         return this$reviewUuid == null ? other$reviewUuid == null : this$reviewUuid.equals(other$reviewUuid);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TelegramBindingReviewCancelRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $reviewUuid = this.getReviewUuid();
      return result * 59 + ($reviewUuid == null ? 43 : $reviewUuid.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindingReviewCancelRequest(reviewUuid=" + this.getReviewUuid() + ")";
   }
}
