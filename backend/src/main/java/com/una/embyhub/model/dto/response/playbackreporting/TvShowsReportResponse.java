package com.una.embyhub.model.dto.response.playbackreporting;

import java.io.Serializable;
import lombok.Generated;

public class TvShowsReportResponse implements Serializable {
   private String label;
   private Integer count;
   private Integer time;

   @Generated
   public String getLabel() {
      return this.label;
   }

   @Generated
   public Integer getCount() {
      return this.count;
   }

   @Generated
   public Integer getTime() {
      return this.time;
   }

   @Generated
   public void setLabel(final String label) {
      this.label = label;
   }

   @Generated
   public void setCount(final Integer count) {
      this.count = count;
   }

   @Generated
   public void setTime(final Integer time) {
      this.time = time;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TvShowsReportResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$count = this.getCount();
         Object other$count = other.getCount();
         if (this$count == null ? other$count == null : this$count.equals(other$count)) {
            Object this$time = this.getTime();
            Object other$time = other.getTime();
            if (this$time == null ? other$time == null : this$time.equals(other$time)) {
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
      return other instanceof TvShowsReportResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $count = this.getCount();
      result = result * 59 + ($count == null ? 43 : $count.hashCode());
      Object $time = this.getTime();
      result = result * 59 + ($time == null ? 43 : $time.hashCode());
      Object $label = this.getLabel();
      return result * 59 + ($label == null ? 43 : $label.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TvShowsReportResponse(label=" + this.getLabel() + ", count=" + this.getCount() + ", time=" + this.getTime() + ")";
   }
}
