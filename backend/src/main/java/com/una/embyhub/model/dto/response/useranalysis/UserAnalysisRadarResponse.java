package com.una.embyhub.model.dto.response.useranalysis;

import java.io.Serializable;
import lombok.Generated;

public class UserAnalysisRadarResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private String label;
   private Double value;
   private Double maxValue;

   @Generated
   public String getLabel() {
      return this.label;
   }

   @Generated
   public Double getValue() {
      return this.value;
   }

   @Generated
   public Double getMaxValue() {
      return this.maxValue;
   }

   @Generated
   public void setLabel(final String label) {
      this.label = label;
   }

   @Generated
   public void setValue(final Double value) {
      this.value = value;
   }

   @Generated
   public void setMaxValue(final Double maxValue) {
      this.maxValue = maxValue;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserAnalysisRadarResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$value = this.getValue();
         Object other$value = other.getValue();
         if (this$value == null ? other$value == null : this$value.equals(other$value)) {
            Object this$maxValue = this.getMaxValue();
            Object other$maxValue = other.getMaxValue();
            if (this$maxValue == null ? other$maxValue == null : this$maxValue.equals(other$maxValue)) {
               Object this$label = this.getLabel();
               Object other$label = other.getLabel();
               return this$label == null ? other$label == null : this$label.equals(other$label);
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
      return other instanceof UserAnalysisRadarResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $value = this.getValue();
      result = result * 59 + ($value == null ? 43 : $value.hashCode());
      Object $maxValue = this.getMaxValue();
      result = result * 59 + ($maxValue == null ? 43 : $maxValue.hashCode());
      Object $label = this.getLabel();
      return result * 59 + ($label == null ? 43 : $label.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserAnalysisRadarResponse(label=" + this.getLabel() + ", value=" + this.getValue() + ", maxValue=" + this.getMaxValue() + ")";
   }
}
