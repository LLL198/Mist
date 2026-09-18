package com.una.embyhub.model.dto.response.useranalysis;

import java.io.Serializable;
import lombok.Generated;

public class UserAnalysisDimensionResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private String label;
   private Long count;
   private Long totalDurationSeconds;
   private Double percentage;

   @Generated
   public String getLabel() {
      return this.label;
   }

   @Generated
   public Long getCount() {
      return this.count;
   }

   @Generated
   public Long getTotalDurationSeconds() {
      return this.totalDurationSeconds;
   }

   @Generated
   public Double getPercentage() {
      return this.percentage;
   }

   @Generated
   public void setLabel(final String label) {
      this.label = label;
   }

   @Generated
   public void setCount(final Long count) {
      this.count = count;
   }

   @Generated
   public void setTotalDurationSeconds(final Long totalDurationSeconds) {
      this.totalDurationSeconds = totalDurationSeconds;
   }

   @Generated
   public void setPercentage(final Double percentage) {
      this.percentage = percentage;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserAnalysisDimensionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$count = this.getCount();
         Object other$count = other.getCount();
         if (this$count == null ? other$count == null : this$count.equals(other$count)) {
            Object this$totalDurationSeconds = this.getTotalDurationSeconds();
            Object other$totalDurationSeconds = other.getTotalDurationSeconds();
            if (this$totalDurationSeconds == null ? other$totalDurationSeconds == null : this$totalDurationSeconds.equals(other$totalDurationSeconds)) {
               Object this$percentage = this.getPercentage();
               Object other$percentage = other.getPercentage();
               if (this$percentage == null ? other$percentage == null : this$percentage.equals(other$percentage)) {
                  Object this$label = this.getLabel();
                  Object other$label = other.getLabel();
                  return this$label == null ? other$label == null : this$label.equals(other$label);
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
      return other instanceof UserAnalysisDimensionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $count = this.getCount();
      result = result * 59 + ($count == null ? 43 : $count.hashCode());
      Object $totalDurationSeconds = this.getTotalDurationSeconds();
      result = result * 59 + ($totalDurationSeconds == null ? 43 : $totalDurationSeconds.hashCode());
      Object $percentage = this.getPercentage();
      result = result * 59 + ($percentage == null ? 43 : $percentage.hashCode());
      Object $label = this.getLabel();
      return result * 59 + ($label == null ? 43 : $label.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserAnalysisDimensionResponse(label="
         + this.getLabel()
         + ", count="
         + this.getCount()
         + ", totalDurationSeconds="
         + this.getTotalDurationSeconds()
         + ", percentage="
         + this.getPercentage()
         + ")";
   }
}
