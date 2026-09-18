package com.una.embyhub.model.dto.request.tmdbdaily;

import java.io.Serializable;
import lombok.Generated;

public class TmdbDailyReleaseSyncRequest implements Serializable {
   private String date;
   private Boolean notify = false;

   @Generated
   public String getDate() {
      return this.date;
   }

   @Generated
   public Boolean getNotify() {
      return this.notify;
   }

   @Generated
   public void setDate(final String date) {
      this.date = date;
   }

   @Generated
   public void setNotify(final Boolean notify) {
      this.notify = notify;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbDailyReleaseSyncRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$notify = this.getNotify();
         Object other$notify = other.getNotify();
         if (this$notify == null ? other$notify == null : this$notify.equals(other$notify)) {
            Object this$date = this.getDate();
            Object other$date = other.getDate();
            return this$date == null ? other$date == null : this$date.equals(other$date);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbDailyReleaseSyncRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $notify = this.getNotify();
      result = result * 59 + ($notify == null ? 43 : $notify.hashCode());
      Object $date = this.getDate();
      return result * 59 + ($date == null ? 43 : $date.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbDailyReleaseSyncRequest(date=" + this.getDate() + ", notify=" + this.getNotify() + ")";
   }
}
