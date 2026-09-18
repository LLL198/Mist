package com.una.embyhub.model.dto.response.playbackreporting;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;

public class HourlyReportResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private Map<String, Integer> hourlyData;

   public HourlyReportResponse() {
      this.hourlyData = new LinkedHashMap<>();
   }

   public HourlyReportResponse(Map<String, Integer> hourlyData) {
      this.hourlyData = hourlyData;
   }

   public static HourlyReportResponse fromMap(Map<String, Object> data) {
      HourlyReportResponse response = new HourlyReportResponse();
      if (data != null) {
         data.forEach((key, value) -> {
            if (value instanceof Number) {
               response.getHourlyData().put(key, ((Number)value).intValue());
            }
         });
      }

      return response;
   }

   public int getPlayCount(int day, int hour) {
      String key = String.format("%d-%02d", day, hour);
      return this.hourlyData.getOrDefault(key, 0);
   }

   public int getDayTotal(int day) {
      int total = 0;

      for (int hour = 0; hour < 24; hour++) {
         total += this.getPlayCount(day, hour);
      }

      return total;
   }

   public int getTotalPlayCount() {
      return this.hourlyData.values().stream().mapToInt(Integer::intValue).sum();
   }

   @Generated
   public Map<String, Integer> getHourlyData() {
      return this.hourlyData;
   }

   @Generated
   public void setHourlyData(final Map<String, Integer> hourlyData) {
      this.hourlyData = hourlyData;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof HourlyReportResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$hourlyData = this.getHourlyData();
         Object other$hourlyData = other.getHourlyData();
         return this$hourlyData == null ? other$hourlyData == null : this$hourlyData.equals(other$hourlyData);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof HourlyReportResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $hourlyData = this.getHourlyData();
      return result * 59 + ($hourlyData == null ? 43 : $hourlyData.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "HourlyReportResponse(hourlyData=" + this.getHourlyData() + ")";
   }
}
