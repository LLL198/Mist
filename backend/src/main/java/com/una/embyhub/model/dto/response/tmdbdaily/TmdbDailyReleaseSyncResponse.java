package com.una.embyhub.model.dto.response.tmdbdaily;

import java.io.Serializable;
import lombok.Generated;

public class TmdbDailyReleaseSyncResponse implements Serializable {
   private String date;
   private Integer totalCount = 0;
   private Integer movieCount = 0;
   private Integer tvCount = 0;
   private Boolean notified = false;
   private String message;

   @Generated
   public String getDate() {
      return this.date;
   }

   @Generated
   public Integer getTotalCount() {
      return this.totalCount;
   }

   @Generated
   public Integer getMovieCount() {
      return this.movieCount;
   }

   @Generated
   public Integer getTvCount() {
      return this.tvCount;
   }

   @Generated
   public Boolean getNotified() {
      return this.notified;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public void setDate(final String date) {
      this.date = date;
   }

   @Generated
   public void setTotalCount(final Integer totalCount) {
      this.totalCount = totalCount;
   }

   @Generated
   public void setMovieCount(final Integer movieCount) {
      this.movieCount = movieCount;
   }

   @Generated
   public void setTvCount(final Integer tvCount) {
      this.tvCount = tvCount;
   }

   @Generated
   public void setNotified(final Boolean notified) {
      this.notified = notified;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbDailyReleaseSyncResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalCount = this.getTotalCount();
         Object other$totalCount = other.getTotalCount();
         if (this$totalCount == null ? other$totalCount == null : this$totalCount.equals(other$totalCount)) {
            Object this$movieCount = this.getMovieCount();
            Object other$movieCount = other.getMovieCount();
            if (this$movieCount == null ? other$movieCount == null : this$movieCount.equals(other$movieCount)) {
               Object this$tvCount = this.getTvCount();
               Object other$tvCount = other.getTvCount();
               if (this$tvCount == null ? other$tvCount == null : this$tvCount.equals(other$tvCount)) {
                  Object this$notified = this.getNotified();
                  Object other$notified = other.getNotified();
                  if (this$notified == null ? other$notified == null : this$notified.equals(other$notified)) {
                     Object this$date = this.getDate();
                     Object other$date = other.getDate();
                     if (this$date == null ? other$date == null : this$date.equals(other$date)) {
                        Object this$message = this.getMessage();
                        Object other$message = other.getMessage();
                        return this$message == null ? other$message == null : this$message.equals(other$message);
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof TmdbDailyReleaseSyncResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalCount = this.getTotalCount();
      result = result * 59 + ($totalCount == null ? 43 : $totalCount.hashCode());
      Object $movieCount = this.getMovieCount();
      result = result * 59 + ($movieCount == null ? 43 : $movieCount.hashCode());
      Object $tvCount = this.getTvCount();
      result = result * 59 + ($tvCount == null ? 43 : $tvCount.hashCode());
      Object $notified = this.getNotified();
      result = result * 59 + ($notified == null ? 43 : $notified.hashCode());
      Object $date = this.getDate();
      result = result * 59 + ($date == null ? 43 : $date.hashCode());
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbDailyReleaseSyncResponse(date="
         + this.getDate()
         + ", totalCount="
         + this.getTotalCount()
         + ", movieCount="
         + this.getMovieCount()
         + ", tvCount="
         + this.getTvCount()
         + ", notified="
         + this.getNotified()
         + ", message="
         + this.getMessage()
         + ")";
   }
}
