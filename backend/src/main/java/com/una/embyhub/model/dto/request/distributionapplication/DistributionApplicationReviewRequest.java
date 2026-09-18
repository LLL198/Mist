package com.una.embyhub.model.dto.request.distributionapplication;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class DistributionApplicationReviewRequest implements Serializable {
   private List<Long> applicationIds;
   private Integer status;
   private String reviewComment;

   @Generated
   public List<Long> getApplicationIds() {
      return this.applicationIds;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getReviewComment() {
      return this.reviewComment;
   }

   @Generated
   public void setApplicationIds(final List<Long> applicationIds) {
      this.applicationIds = applicationIds;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setReviewComment(final String reviewComment) {
      this.reviewComment = reviewComment;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DistributionApplicationReviewRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$status = this.getStatus();
         Object other$status = other.getStatus();
         if (this$status == null ? other$status == null : this$status.equals(other$status)) {
            Object this$applicationIds = this.getApplicationIds();
            Object other$applicationIds = other.getApplicationIds();
            if (this$applicationIds == null ? other$applicationIds == null : this$applicationIds.equals(other$applicationIds)) {
               Object this$reviewComment = this.getReviewComment();
               Object other$reviewComment = other.getReviewComment();
               return this$reviewComment == null ? other$reviewComment == null : this$reviewComment.equals(other$reviewComment);
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
      return other instanceof DistributionApplicationReviewRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $applicationIds = this.getApplicationIds();
      result = result * 59 + ($applicationIds == null ? 43 : $applicationIds.hashCode());
      Object $reviewComment = this.getReviewComment();
      return result * 59 + ($reviewComment == null ? 43 : $reviewComment.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionApplicationReviewRequest(applicationIds="
         + this.getApplicationIds()
         + ", status="
         + this.getStatus()
         + ", reviewComment="
         + this.getReviewComment()
         + ")";
   }
}
