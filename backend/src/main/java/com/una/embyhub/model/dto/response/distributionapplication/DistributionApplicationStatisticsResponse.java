package com.una.embyhub.model.dto.response.distributionapplication;

import java.io.Serializable;
import lombok.Generated;

public class DistributionApplicationStatisticsResponse implements Serializable {
   private Long total;
   private Long pending;
   private Long approved;
   private Long rejected;

   @Generated
   public Long getTotal() {
      return this.total;
   }

   @Generated
   public Long getPending() {
      return this.pending;
   }

   @Generated
   public Long getApproved() {
      return this.approved;
   }

   @Generated
   public Long getRejected() {
      return this.rejected;
   }

   @Generated
   public void setTotal(final Long total) {
      this.total = total;
   }

   @Generated
   public void setPending(final Long pending) {
      this.pending = pending;
   }

   @Generated
   public void setApproved(final Long approved) {
      this.approved = approved;
   }

   @Generated
   public void setRejected(final Long rejected) {
      this.rejected = rejected;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DistributionApplicationStatisticsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$total = this.getTotal();
         Object other$total = other.getTotal();
         if (this$total == null ? other$total == null : this$total.equals(other$total)) {
            Object this$pending = this.getPending();
            Object other$pending = other.getPending();
            if (this$pending == null ? other$pending == null : this$pending.equals(other$pending)) {
               Object this$approved = this.getApproved();
               Object other$approved = other.getApproved();
               if (this$approved == null ? other$approved == null : this$approved.equals(other$approved)) {
                  Object this$rejected = this.getRejected();
                  Object other$rejected = other.getRejected();
                  return this$rejected == null ? other$rejected == null : this$rejected.equals(other$rejected);
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
      return other instanceof DistributionApplicationStatisticsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $total = this.getTotal();
      result = result * 59 + ($total == null ? 43 : $total.hashCode());
      Object $pending = this.getPending();
      result = result * 59 + ($pending == null ? 43 : $pending.hashCode());
      Object $approved = this.getApproved();
      result = result * 59 + ($approved == null ? 43 : $approved.hashCode());
      Object $rejected = this.getRejected();
      return result * 59 + ($rejected == null ? 43 : $rejected.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionApplicationStatisticsResponse(total="
         + this.getTotal()
         + ", pending="
         + this.getPending()
         + ", approved="
         + this.getApproved()
         + ", rejected="
         + this.getRejected()
         + ")";
   }
}
