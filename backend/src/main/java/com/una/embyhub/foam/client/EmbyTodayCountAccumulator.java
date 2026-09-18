package com.una.embyhub.foam.client;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

final class EmbyTodayCountAccumulator {
   private final LocalDate date;
   private final ZoneId zone;
   private final Set<String> seenIds = new HashSet<>();
   private final Set<String> seenSeriesIds = new HashSet<>();
   private int movieCount;
   private int seriesCount;
   private int episodeCount;

   EmbyTodayCountAccumulator(LocalDate date, ZoneId zone) {
      this.date = date;
      this.zone = zone;
   }

   void accept(JSONArray items) {
      if (items != null) {
         for (Object rawItem : items) {
            if (rawItem instanceof JSONObject item) {
               String id = firstText(item, "Id", "id");
               if ((id.isEmpty() || this.seenIds.add(id)) && hasMediaFile(item)) {
                  String var6 = firstText(item, "Type", "type");
                  switch (var6) {
                     case "Movie":
                        this.movieCount++;
                        break;
                     case "Episode":
                        this.episodeCount++;
                        this.countSeries(firstText(item, "SeriesId", "seriesId"));
                  }
               }
            }
         }
      }
   }

   private void countSeries(String seriesId) {
      if (seriesId.isEmpty() || this.seenSeriesIds.add(seriesId)) {
         this.seriesCount++;
      }
   }

   JSONObject toJson(boolean truncated) {
      JSONObject result = new JSONObject();
      result.put("TodayMovieCount", Integer.valueOf(this.movieCount));
      result.put("TodaySeriesCount", Integer.valueOf(this.seriesCount));
      result.put("TodayEpisodeCount", Integer.valueOf(this.episodeCount));
      result.put("TodayTotalCount", Integer.valueOf(this.movieCount + this.seriesCount + this.episodeCount));
      result.put("TodayDate", this.date.toString());
      result.put("TodayTimezone", this.zone.getId());
      result.put("TodayCountTruncated", Boolean.valueOf(truncated));
      return result;
   }

   private static String firstText(JSONObject item, String primaryKey, String fallbackKey) {
      String value = item.getString(primaryKey);
      if (value == null || value.isBlank()) {
         value = item.getString(fallbackKey);
      }

      return value == null ? "" : value.trim();
   }

   private static boolean hasMediaFile(JSONObject item) {
      if (!firstText(item, "Path", "path").isEmpty()) {
         return true;
      } else {
         JSONArray mediaSources = item.getJSONArray("MediaSources");
         if (mediaSources == null) {
            mediaSources = item.getJSONArray("mediaSources");
         }

         return mediaSources != null && !mediaSources.isEmpty();
      }
   }
}
