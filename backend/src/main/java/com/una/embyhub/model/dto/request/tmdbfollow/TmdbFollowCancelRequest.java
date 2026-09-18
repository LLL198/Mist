package com.una.embyhub.model.dto.request.tmdbfollow;

import lombok.Generated;

public class TmdbFollowCancelRequest {
   private Long followId;
   private Integer tmdbId;

   @Generated
   public Long getFollowId() {
      return this.followId;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public void setFollowId(final Long followId) {
      this.followId = followId;
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
      } else if (!(o instanceof TmdbFollowCancelRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$followId = this.getFollowId();
         Object other$followId = other.getFollowId();
         if (this$followId == null ? other$followId == null : this$followId.equals(other$followId)) {
            Object this$tmdbId = this.getTmdbId();
            Object other$tmdbId = other.getTmdbId();
            return this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbFollowCancelRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $followId = this.getFollowId();
      result = result * 59 + ($followId == null ? 43 : $followId.hashCode());
      Object $tmdbId = this.getTmdbId();
      return result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowCancelRequest(followId=" + this.getFollowId() + ", tmdbId=" + this.getTmdbId() + ")";
   }
}
