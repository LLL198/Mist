package com.una.embyhub.model.dto.request.tmdbfollow;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class TmdbFollowProgressRequest implements Serializable {
   @NotNull(
      message = "订阅ID不能为空"
   )
   private Long followId;
   private String watcherName;
   private Integer seasonNumber;
   private Integer episodeNumber;

   @Generated
   public Long getFollowId() {
      return this.followId;
   }

   @Generated
   public String getWatcherName() {
      return this.watcherName;
   }

   @Generated
   public Integer getSeasonNumber() {
      return this.seasonNumber;
   }

   @Generated
   public Integer getEpisodeNumber() {
      return this.episodeNumber;
   }

   @Generated
   public void setFollowId(final Long followId) {
      this.followId = followId;
   }

   @Generated
   public void setWatcherName(final String watcherName) {
      this.watcherName = watcherName;
   }

   @Generated
   public void setSeasonNumber(final Integer seasonNumber) {
      this.seasonNumber = seasonNumber;
   }

   @Generated
   public void setEpisodeNumber(final Integer episodeNumber) {
      this.episodeNumber = episodeNumber;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollowProgressRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$followId = this.getFollowId();
         Object other$followId = other.getFollowId();
         if (this$followId == null ? other$followId == null : this$followId.equals(other$followId)) {
            Object this$seasonNumber = this.getSeasonNumber();
            Object other$seasonNumber = other.getSeasonNumber();
            if (this$seasonNumber == null ? other$seasonNumber == null : this$seasonNumber.equals(other$seasonNumber)) {
               Object this$episodeNumber = this.getEpisodeNumber();
               Object other$episodeNumber = other.getEpisodeNumber();
               if (this$episodeNumber == null ? other$episodeNumber == null : this$episodeNumber.equals(other$episodeNumber)) {
                  Object this$watcherName = this.getWatcherName();
                  Object other$watcherName = other.getWatcherName();
                  return this$watcherName == null ? other$watcherName == null : this$watcherName.equals(other$watcherName);
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
      return other instanceof TmdbFollowProgressRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $followId = this.getFollowId();
      result = result * 59 + ($followId == null ? 43 : $followId.hashCode());
      Object $seasonNumber = this.getSeasonNumber();
      result = result * 59 + ($seasonNumber == null ? 43 : $seasonNumber.hashCode());
      Object $episodeNumber = this.getEpisodeNumber();
      result = result * 59 + ($episodeNumber == null ? 43 : $episodeNumber.hashCode());
      Object $watcherName = this.getWatcherName();
      return result * 59 + ($watcherName == null ? 43 : $watcherName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowProgressRequest(followId="
         + this.getFollowId()
         + ", watcherName="
         + this.getWatcherName()
         + ", seasonNumber="
         + this.getSeasonNumber()
         + ", episodeNumber="
         + this.getEpisodeNumber()
         + ")";
   }
}
