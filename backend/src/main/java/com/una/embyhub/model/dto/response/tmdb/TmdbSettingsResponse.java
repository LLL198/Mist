package com.una.embyhub.model.dto.response.tmdb;

import java.io.Serializable;
import lombok.Generated;

public class TmdbSettingsResponse implements Serializable {
   private String tmdbKey;

   @Generated
   public String getTmdbKey() {
      return this.tmdbKey;
   }

   @Generated
   public void setTmdbKey(final String tmdbKey) {
      this.tmdbKey = tmdbKey;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbSettingsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbKey = this.getTmdbKey();
         Object other$tmdbKey = other.getTmdbKey();
         return this$tmdbKey == null ? other$tmdbKey == null : this$tmdbKey.equals(other$tmdbKey);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbSettingsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbKey = this.getTmdbKey();
      return result * 59 + ($tmdbKey == null ? 43 : $tmdbKey.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbSettingsResponse(tmdbKey=" + this.getTmdbKey() + ")";
   }
}
