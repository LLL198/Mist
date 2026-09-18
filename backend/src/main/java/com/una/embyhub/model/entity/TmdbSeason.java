package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("tmdb_season")
public class TmdbSeason extends BaseEntity implements Serializable {
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
   @TableField("name")
   private String name;
   @TableField("overview")
   private String overview;
   @TableField("poster_path")
   private String posterPath;
   @TableField("air_date")
   private Date airDate;
   @TableField("episode_count")
   private Integer episodeCount;
   @TableField("casts")
   private String casts;

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
   public String getName() {
      return this.name;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public String getPosterPath() {
      return this.posterPath;
   }

   @Generated
   public Date getAirDate() {
      return this.airDate;
   }

   @Generated
   public Integer getEpisodeCount() {
      return this.episodeCount;
   }

   @Generated
   public String getCasts() {
      return this.casts;
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
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setPosterPath(final String posterPath) {
      this.posterPath = posterPath;
   }

   @Generated
   public void setAirDate(final Date airDate) {
      this.airDate = airDate;
   }

   @Generated
   public void setEpisodeCount(final Integer episodeCount) {
      this.episodeCount = episodeCount;
   }

   @Generated
   public void setCasts(final String casts) {
      this.casts = casts;
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbSeason(id="
         + this.getId()
         + ", followId="
         + this.getFollowId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", seasonNumber="
         + this.getSeasonNumber()
         + ", name="
         + this.getName()
         + ", overview="
         + this.getOverview()
         + ", posterPath="
         + this.getPosterPath()
         + ", airDate="
         + this.getAirDate()
         + ", episodeCount="
         + this.getEpisodeCount()
         + ", casts="
         + this.getCasts()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbSeason other)) {
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
                     Object this$episodeCount = this.getEpisodeCount();
                     Object other$episodeCount = other.getEpisodeCount();
                     if (this$episodeCount == null ? other$episodeCount == null : this$episodeCount.equals(other$episodeCount)) {
                        Object this$name = this.getName();
                        Object other$name = other.getName();
                        if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                           Object this$overview = this.getOverview();
                           Object other$overview = other.getOverview();
                           if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                              Object this$posterPath = this.getPosterPath();
                              Object other$posterPath = other.getPosterPath();
                              if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                 Object this$airDate = this.getAirDate();
                                 Object other$airDate = other.getAirDate();
                                 if (this$airDate == null ? other$airDate == null : this$airDate.equals(other$airDate)) {
                                    Object this$casts = this.getCasts();
                                    Object other$casts = other.getCasts();
                                    return this$casts == null ? other$casts == null : this$casts.equals(other$casts);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbSeason;
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
      Object $episodeCount = this.getEpisodeCount();
      result = result * 59 + ($episodeCount == null ? 43 : $episodeCount.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $airDate = this.getAirDate();
      result = result * 59 + ($airDate == null ? 43 : $airDate.hashCode());
      Object $casts = this.getCasts();
      return result * 59 + ($casts == null ? 43 : $casts.hashCode());
   }
}
