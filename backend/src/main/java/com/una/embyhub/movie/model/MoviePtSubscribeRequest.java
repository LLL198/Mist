package com.una.embyhub.movie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class MoviePtSubscribeRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long id;
   @NotBlank(
      message = "订阅名称不能为空"
   )
   private String name;
   private String originalTitle;
   private String keyword;
   private String type;
   private String year;
   private Long tmdbId;
   private String posterPath;
   private String backdropPath;
   private Integer season;
   private Integer startEpisode;
   @JsonFormat(
      with = {Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY}
   )
   private List<Long> siteId;
   private Long scrapePathConfigId;
   private Long downloaderId;
   private Integer autoDownload;
   private Integer enabled;
   private String state;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getOriginalTitle() {
      return this.originalTitle;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
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
   public Long getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getPosterPath() {
      return this.posterPath;
   }

   @Generated
   public String getBackdropPath() {
      return this.backdropPath;
   }

   @Generated
   public Integer getSeason() {
      return this.season;
   }

   @Generated
   public Integer getStartEpisode() {
      return this.startEpisode;
   }

   @Generated
   public List<Long> getSiteId() {
      return this.siteId;
   }

   @Generated
   public Long getScrapePathConfigId() {
      return this.scrapePathConfigId;
   }

   @Generated
   public Long getDownloaderId() {
      return this.downloaderId;
   }

   @Generated
   public Integer getAutoDownload() {
      return this.autoDownload;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getState() {
      return this.state;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setOriginalTitle(final String originalTitle) {
      this.originalTitle = originalTitle;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
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
   public void setTmdbId(final Long tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setPosterPath(final String posterPath) {
      this.posterPath = posterPath;
   }

   @Generated
   public void setBackdropPath(final String backdropPath) {
      this.backdropPath = backdropPath;
   }

   @Generated
   public void setSeason(final Integer season) {
      this.season = season;
   }

   @Generated
   public void setStartEpisode(final Integer startEpisode) {
      this.startEpisode = startEpisode;
   }

   @JsonFormat(
      with = {Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY}
   )
   @Generated
   public void setSiteId(final List<Long> siteId) {
      this.siteId = siteId;
   }

   @Generated
   public void setScrapePathConfigId(final Long scrapePathConfigId) {
      this.scrapePathConfigId = scrapePathConfigId;
   }

   @Generated
   public void setDownloaderId(final Long downloaderId) {
      this.downloaderId = downloaderId;
   }

   @Generated
   public void setAutoDownload(final Integer autoDownload) {
      this.autoDownload = autoDownload;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setState(final String state) {
      this.state = state;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSubscribeRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$tmdbId = this.getTmdbId();
            Object other$tmdbId = other.getTmdbId();
            if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
               Object this$season = this.getSeason();
               Object other$season = other.getSeason();
               if (this$season == null ? other$season == null : this$season.equals(other$season)) {
                  Object this$startEpisode = this.getStartEpisode();
                  Object other$startEpisode = other.getStartEpisode();
                  if (this$startEpisode == null ? other$startEpisode == null : this$startEpisode.equals(other$startEpisode)) {
                     Object this$scrapePathConfigId = this.getScrapePathConfigId();
                     Object other$scrapePathConfigId = other.getScrapePathConfigId();
                     if (this$scrapePathConfigId == null ? other$scrapePathConfigId == null : this$scrapePathConfigId.equals(other$scrapePathConfigId)) {
                        Object this$downloaderId = this.getDownloaderId();
                        Object other$downloaderId = other.getDownloaderId();
                        if (this$downloaderId == null ? other$downloaderId == null : this$downloaderId.equals(other$downloaderId)) {
                           Object this$autoDownload = this.getAutoDownload();
                           Object other$autoDownload = other.getAutoDownload();
                           if (this$autoDownload == null ? other$autoDownload == null : this$autoDownload.equals(other$autoDownload)) {
                              Object this$enabled = this.getEnabled();
                              Object other$enabled = other.getEnabled();
                              if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                                 Object this$name = this.getName();
                                 Object other$name = other.getName();
                                 if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                    Object this$originalTitle = this.getOriginalTitle();
                                    Object other$originalTitle = other.getOriginalTitle();
                                    if (this$originalTitle == null ? other$originalTitle == null : this$originalTitle.equals(other$originalTitle)) {
                                       Object this$keyword = this.getKeyword();
                                       Object other$keyword = other.getKeyword();
                                       if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                                          Object this$type = this.getType();
                                          Object other$type = other.getType();
                                          if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                             Object this$year = this.getYear();
                                             Object other$year = other.getYear();
                                             if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                                                Object this$posterPath = this.getPosterPath();
                                                Object other$posterPath = other.getPosterPath();
                                                if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                                   Object this$backdropPath = this.getBackdropPath();
                                                   Object other$backdropPath = other.getBackdropPath();
                                                   if (this$backdropPath == null ? other$backdropPath == null : this$backdropPath.equals(other$backdropPath)) {
                                                      Object this$siteId = this.getSiteId();
                                                      Object other$siteId = other.getSiteId();
                                                      if (this$siteId == null ? other$siteId == null : this$siteId.equals(other$siteId)) {
                                                         Object this$state = this.getState();
                                                         Object other$state = other.getState();
                                                         return this$state == null ? other$state == null : this$state.equals(other$state);
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
      return other instanceof MoviePtSubscribeRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $startEpisode = this.getStartEpisode();
      result = result * 59 + ($startEpisode == null ? 43 : $startEpisode.hashCode());
      Object $scrapePathConfigId = this.getScrapePathConfigId();
      result = result * 59 + ($scrapePathConfigId == null ? 43 : $scrapePathConfigId.hashCode());
      Object $downloaderId = this.getDownloaderId();
      result = result * 59 + ($downloaderId == null ? 43 : $downloaderId.hashCode());
      Object $autoDownload = this.getAutoDownload();
      result = result * 59 + ($autoDownload == null ? 43 : $autoDownload.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $originalTitle = this.getOriginalTitle();
      result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $year = this.getYear();
      result = result * 59 + ($year == null ? 43 : $year.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $siteId = this.getSiteId();
      result = result * 59 + ($siteId == null ? 43 : $siteId.hashCode());
      Object $state = this.getState();
      return result * 59 + ($state == null ? 43 : $state.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSubscribeRequest(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", originalTitle="
         + this.getOriginalTitle()
         + ", keyword="
         + this.getKeyword()
         + ", type="
         + this.getType()
         + ", year="
         + this.getYear()
         + ", tmdbId="
         + this.getTmdbId()
         + ", posterPath="
         + this.getPosterPath()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", season="
         + this.getSeason()
         + ", startEpisode="
         + this.getStartEpisode()
         + ", siteId="
         + this.getSiteId()
         + ", scrapePathConfigId="
         + this.getScrapePathConfigId()
         + ", downloaderId="
         + this.getDownloaderId()
         + ", autoDownload="
         + this.getAutoDownload()
         + ", enabled="
         + this.getEnabled()
         + ", state="
         + this.getState()
         + ")";
   }
}
