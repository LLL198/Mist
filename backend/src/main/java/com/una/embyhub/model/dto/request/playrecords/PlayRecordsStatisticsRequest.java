package com.una.embyhub.model.dto.request.playrecords;

import java.io.Serializable;
import lombok.Generated;

public class PlayRecordsStatisticsRequest implements Serializable {
   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof PlayRecordsStatisticsRequest other) ? false : other.canEqual(this);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PlayRecordsStatisticsRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int result = 1;
      return 1;
   }

   @Generated
   @Override
   public String toString() {
      return "PlayRecordsStatisticsRequest()";
   }
}
