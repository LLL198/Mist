package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("tmdb_episode")
public class TmdbEpisode extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      type = IdType.AUTO
   )
   private Long id;
   @TableField("follow_id")
   private Long followId;
   @TableField("tmdb_id")
   private Integer tmdbId;
   @TableField("season_number")
   private Integer seasonNumber;
   @TableField("episode_number")
   private Integer episodeNumber;
   @TableField("name")
   private String name;
   @TableField("overview")
   private String overview;
   @TableField("air_date")
   private Date airDate;
   @TableField("still_path")
   private String stillPath;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getFollowId() {
      return this.followId;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
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
   public String getName() {
      return this.name;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public Date getAirDate() {
      return this.airDate;
   }

   @Generated
   public String getStillPath() {
      return this.stillPath;
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
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
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
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setAirDate(final Date airDate) {
      this.airDate = airDate;
   }

   @Generated
   public void setStillPath(final String stillPath) {
      this.stillPath = stillPath;
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbEpisode(id="
         + this.getId()
         + ", followId="
         + this.getFollowId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", seasonNumber="
         + this.getSeasonNumber()
         + ", episodeNumber="
         + this.getEpisodeNumber()
         + ", name="
         + this.getName()
         + ", overview="
         + this.getOverview()
         + ", airDate="
         + this.getAirDate()
         + ", stillPath="
         + this.getStillPath()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbEpisode other)) {
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
               Object this$tmdbId = this.getTmdbId();
               Object other$tmdbId = other.getTmdbId();
               if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
                  Object this$seasonNumber = this.getSeasonNumber();
                  Object other$seasonNumber = other.getSeasonNumber();
                  if (this$seasonNumber == null ? other$seasonNumber == null : this$seasonNumber.equals(other$seasonNumber)) {
                     Object this$episodeNumber = this.getEpisodeNumber();
                     Object other$episodeNumber = other.getEpisodeNumber();
                     if (this$episodeNumber == null ? other$episodeNumber == null : this$episodeNumber.equals(other$episodeNumber)) {
                        Object this$name = this.getName();
                        Object other$name = other.getName();
                        if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                           Object this$overview = this.getOverview();
                           Object other$overview = other.getOverview();
                           if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                              Object this$airDate = this.getAirDate();
                              Object other$airDate = other.getAirDate();
                              if (this$airDate == null ? other$airDate == null : this$airDate.equals(other$airDate)) {
                                 Object this$stillPath = this.getStillPath();
                                 Object other$stillPath = other.getStillPath();
                                 return this$stillPath == null ? other$stillPath == null : this$stillPath.equals(other$stillPath);
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
      return other instanceof TmdbEpisode;
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
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $seasonNumber = this.getSeasonNumber();
      result = result * 59 + ($seasonNumber == null ? 43 : $seasonNumber.hashCode());
      Object $episodeNumber = this.getEpisodeNumber();
      result = result * 59 + ($episodeNumber == null ? 43 : $episodeNumber.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $airDate = this.getAirDate();
      result = result * 59 + ($airDate == null ? 43 : $airDate.hashCode());
      Object $stillPath = this.getStillPath();
      return result * 59 + ($stillPath == null ? 43 : $stillPath.hashCode());
   }
}
