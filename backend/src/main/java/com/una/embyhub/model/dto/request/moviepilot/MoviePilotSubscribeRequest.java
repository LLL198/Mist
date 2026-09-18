package com.una.embyhub.model.dto.request.moviepilot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;

public class MoviePilotSubscribeRequest {
   @NotBlank(
      message = "影片名称不能为空"
   )
   private String name;
   @NotBlank(
      message = "影片类型不能为空"
   )
   private String type;
   @NotBlank(
      message = "年份不能为空"
   )
   private String year;
   @NotNull(
      message = "tmdbid不能为空"
   )
   private Long tmdbid;
   private String doubanid;
   private String bangumiid;
   private String mediaid;
   private Integer season;
   private Integer bestVersion;
   private String episodeGroup;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getYear() {
      return this.year;
   }

   @Generated
   public Long getTmdbid() {
      return this.tmdbid;
   }

   @Generated
   public String getDoubanid() {
      return this.doubanid;
   }

   @Generated
   public String getBangumiid() {
      return this.bangumiid;
   }

   @Generated
   public String getMediaid() {
      return this.mediaid;
   }

   @Generated
   public Integer getSeason() {
      return this.season;
   }

   @Generated
   public Integer getBestVersion() {
      return this.bestVersion;
   }

   @Generated
   public String getEpisodeGroup() {
      return this.episodeGroup;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setYear(final String year) {
      this.year = year;
   }

   @Generated
   public void setTmdbid(final Long tmdbid) {
      this.tmdbid = tmdbid;
   }

   @Generated
   public void setDoubanid(final String doubanid) {
      this.doubanid = doubanid;
   }

   @Generated
   public void setBangumiid(final String bangumiid) {
      this.bangumiid = bangumiid;
   }

   @Generated
   public void setMediaid(final String mediaid) {
      this.mediaid = mediaid;
   }

   @Generated
   public void setSeason(final Integer season) {
      this.season = season;
   }

   @Generated
   public void setBestVersion(final Integer bestVersion) {
      this.bestVersion = bestVersion;
   }

   @Generated
   public void setEpisodeGroup(final String episodeGroup) {
      this.episodeGroup = episodeGroup;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePilotSubscribeRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbid = this.getTmdbid();
         Object other$tmdbid = other.getTmdbid();
         if (this$tmdbid == null ? other$tmdbid == null : this$tmdbid.equals(other$tmdbid)) {
            Object this$season = this.getSeason();
            Object other$season = other.getSeason();
            if (this$season == null ? other$season == null : this$season.equals(other$season)) {
               Object this$bestVersion = this.getBestVersion();
               Object other$bestVersion = other.getBestVersion();
               if (this$bestVersion == null ? other$bestVersion == null : this$bestVersion.equals(other$bestVersion)) {
                  Object this$name = this.getName();
                  Object other$name = other.getName();
                  if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                     Object this$type = this.getType();
                     Object other$type = other.getType();
                     if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                        Object this$year = this.getYear();
                        Object other$year = other.getYear();
                        if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                           Object this$doubanid = this.getDoubanid();
                           Object other$doubanid = other.getDoubanid();
                           if (this$doubanid == null ? other$doubanid == null : this$doubanid.equals(other$doubanid)) {
                              Object this$bangumiid = this.getBangumiid();
                              Object other$bangumiid = other.getBangumiid();
                              if (this$bangumiid == null ? other$bangumiid == null : this$bangumiid.equals(other$bangumiid)) {
                                 Object this$mediaid = this.getMediaid();
                                 Object other$mediaid = other.getMediaid();
                                 if (this$mediaid == null ? other$mediaid == null : this$mediaid.equals(other$mediaid)) {
                                    Object this$episodeGroup = this.getEpisodeGroup();
                                    Object other$episodeGroup = other.getEpisodeGroup();
                                    return this$episodeGroup == null ? other$episodeGroup == null : this$episodeGroup.equals(other$episodeGroup);
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
   protected boolean canEqual(final Object other) {
      return other instanceof MoviePilotSubscribeRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbid = this.getTmdbid();
      result = result * 59 + ($tmdbid == null ? 43 : $tmdbid.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $bestVersion = this.getBestVersion();
      result = result * 59 + ($bestVersion == null ? 43 : $bestVersion.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $year = this.getYear();
      result = result * 59 + ($year == null ? 43 : $year.hashCode());
      Object $doubanid = this.getDoubanid();
      result = result * 59 + ($doubanid == null ? 43 : $doubanid.hashCode());
      Object $bangumiid = this.getBangumiid();
      result = result * 59 + ($bangumiid == null ? 43 : $bangumiid.hashCode());
      Object $mediaid = this.getMediaid();
      result = result * 59 + ($mediaid == null ? 43 : $mediaid.hashCode());
      Object $episodeGroup = this.getEpisodeGroup();
      return result * 59 + ($episodeGroup == null ? 43 : $episodeGroup.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePilotSubscribeRequest(name="
         + this.getName()
         + ", type="
         + this.getType()
         + ", year="
         + this.getYear()
         + ", tmdbid="
         + this.getTmdbid()
         + ", doubanid="
         + this.getDoubanid()
         + ", bangumiid="
         + this.getBangumiid()
         + ", mediaid="
         + this.getMediaid()
         + ", season="
         + this.getSeason()
         + ", bestVersion="
         + this.getBestVersion()
         + ", episodeGroup="
         + this.getEpisodeGroup()
         + ")";
   }
}
