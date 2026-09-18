package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("tmdb_watch_progress")
public class TmdbWatchProgress extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      type = IdType.AUTO
   )
   private Long id;
   @TableField("follow_id")
   private Long followId;
   @TableField("watcher_name")
   private String watcherName;
   @Deprecated
   @TableField("season_number")
   private Integer seasonNumber;
   @Deprecated
   @TableField("episode_number")
   private Integer episodeNumber;
   @TableField("watched_episodes")
   private String watchedEpisodes;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getFollowId() {
      return this.followId;
   }

   @Generated
   public String getWatcherName() {
      return this.watcherName;
   }

   @Deprecated
   @Generated
   public Integer getSeasonNumber() {
      return this.seasonNumber;
   }

   @Deprecated
   @Generated
   public Integer getEpisodeNumber() {
      return this.episodeNumber;
   }

   @Generated
   public String getWatchedEpisodes() {
      return this.watchedEpisodes;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setFollowId(final Long followId) {
      this.followId = followId;
   }

   @Generated
   public void setWatcherName(final String watcherName) {
      this.watcherName = watcherName;
   }

   @Deprecated
   @Generated
   public void setSeasonNumber(final Integer seasonNumber) {
      this.seasonNumber = seasonNumber;
   }

   @Deprecated
   @Generated
   public void setEpisodeNumber(final Integer episodeNumber) {
      this.episodeNumber = episodeNumber;
   }

   @Generated
   public void setWatchedEpisodes(final String watchedEpisodes) {
      this.watchedEpisodes = watchedEpisodes;
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbWatchProgress(id="
         + this.getId()
         + ", followId="
         + this.getFollowId()
         + ", watcherName="
         + this.getWatcherName()
         + ", seasonNumber="
         + this.getSeasonNumber()
         + ", episodeNumber="
         + this.getEpisodeNumber()
         + ", watchedEpisodes="
         + this.getWatchedEpisodes()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbWatchProgress other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
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
                     if (this$watcherName == null ? other$watcherName == null : this$watcherName.equals(other$watcherName)) {
                        Object this$watchedEpisodes = this.getWatchedEpisodes();
                        Object other$watchedEpisodes = other.getWatchedEpisodes();
                        return this$watchedEpisodes == null ? other$watchedEpisodes == null : this$watchedEpisodes.equals(other$watchedEpisodes);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbWatchProgress;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $followId = this.getFollowId();
      result = result * 59 + ($followId == null ? 43 : $followId.hashCode());
      Object $seasonNumber = this.getSeasonNumber();
      result = result * 59 + ($seasonNumber == null ? 43 : $seasonNumber.hashCode());
      Object $episodeNumber = this.getEpisodeNumber();
      result = result * 59 + ($episodeNumber == null ? 43 : $episodeNumber.hashCode());
      Object $watcherName = this.getWatcherName();
      result = result * 59 + ($watcherName == null ? 43 : $watcherName.hashCode());
      Object $watchedEpisodes = this.getWatchedEpisodes();
      return result * 59 + ($watchedEpisodes == null ? 43 : $watchedEpisodes.hashCode());
   }
}
