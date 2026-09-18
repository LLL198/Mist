package com.una.embyhub.model.dto.request.embyiplocations;

import java.io.Serializable;
import lombok.Generated;

public class ThresholdUserRequest implements Serializable {
   private Integer thresholdUserCount = 0;
   private Long embyInfoId;

   @Generated
   public Integer getThresholdUserCount() {
      return this.thresholdUserCount;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setThresholdUserCount(final Integer thresholdUserCount) {
      this.thresholdUserCount = thresholdUserCount;
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
      } else if (!(o instanceof ThresholdUserRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$thresholdUserCount = this.getThresholdUserCount();
         Object other$thresholdUserCount = other.getThresholdUserCount();
         if (this$thresholdUserCount == null ? other$thresholdUserCount == null : this$thresholdUserCount.equals(other$thresholdUserCount)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            return this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof ThresholdUserRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $thresholdUserCount = this.getThresholdUserCount();
      result = result * 59 + ($thresholdUserCount == null ? 43 : $thresholdUserCount.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      return result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ThresholdUserRequest(thresholdUserCount=" + this.getThresholdUserCount() + ", embyInfoId=" + this.getEmbyInfoId() + ")";
   }
}
