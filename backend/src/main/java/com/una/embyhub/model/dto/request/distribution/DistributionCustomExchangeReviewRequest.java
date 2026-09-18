package com.una.embyhub.model.dto.request.distribution;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class DistributionCustomExchangeReviewRequest implements Serializable {
   private List<Long> idList;
   private Integer status;
   private String reviewComment;

   @Generated
   public List<Long> getIdList() {
      return this.idList;
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
   public void setIdList(final List<Long> idList) {
      this.idList = idList;
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
      } else if (!(o instanceof DistributionCustomExchangeReviewRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$status = this.getStatus();
         Object other$status = other.getStatus();
         if (this$status == null ? other$status == null : this$status.equals(other$status)) {
            Object this$idList = this.getIdList();
            Object other$idList = other.getIdList();
            if (this$idList == null ? other$idList == null : this$idList.equals(other$idList)) {
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
      return other instanceof DistributionCustomExchangeReviewRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $idList = this.getIdList();
      result = result * 59 + ($idList == null ? 43 : $idList.hashCode());
      Object $reviewComment = this.getReviewComment();
      return result * 59 + ($reviewComment == null ? 43 : $reviewComment.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionCustomExchangeReviewRequest(idList="
         + this.getIdList()
         + ", status="
         + this.getStatus()
         + ", reviewComment="
         + this.getReviewComment()
         + ")";
   }
}
