package com.una.embyhub.model.dto.request.tmdbfollow;

import java.io.Serializable;
import lombok.Generated;

public class TmdbFollowSyncRequest implements Serializable {
   private Long followId;
   private boolean toGroup = true;

   @Generated
   public Long getFollowId() {
      return this.followId;
   }

   @Generated
   public boolean isToGroup() {
      return this.toGroup;
   }

   @Generated
   public void setFollowId(final Long followId) {
      this.followId = followId;
   }

   @Generated
   public void setToGroup(final boolean toGroup) {
      this.toGroup = toGroup;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollowSyncRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isToGroup() != other.isToGroup()) {
         return false;
      } else {
         Object this$followId = this.getFollowId();
         Object other$followId = other.getFollowId();
         return this$followId == null ? other$followId == null : this$followId.equals(other$followId);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbFollowSyncRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isToGroup() ? 79 : 97);
      Object $followId = this.getFollowId();
      return result * 59 + ($followId == null ? 43 : $followId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowSyncRequest(followId=" + this.getFollowId() + ", toGroup=" + this.isToGroup() + ")";
   }
}
