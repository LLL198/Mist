package com.una.embyhub.model.dto.request.tmdbfollow;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class TmdbFollowCheckRequest implements Serializable {
   @NotNull(
      message = "TMDB ID不能为空"
   )
   private Integer tmdbId;

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollowCheckRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbId = this.getTmdbId();
         Object other$tmdbId = other.getTmdbId();
         return this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbFollowCheckRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbId = this.getTmdbId();
      return result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowCheckRequest(tmdbId=" + this.getTmdbId() + ")";
   }
}
