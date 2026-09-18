package com.una.embyhub.model.dto.request.requestlist;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import lombok.Generated;

public class RequestListRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   private String name;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String type;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer tmdbId;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String doubanId;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer status;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer auditStatus;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String embyUserName;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long embyInfoId;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String embyServerId;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer season;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer episode;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getDoubanId() {
      return this.doubanId;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public Integer getAuditStatus() {
      return this.auditStatus;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getEmbyServerId() {
      return this.embyServerId;
   }

   @Generated
   public Integer getSeason() {
      return this.season;
   }

   @Generated
   public Integer getEpisode() {
      return this.episode;
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
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setDoubanId(final String doubanId) {
      this.doubanId = doubanId;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setAuditStatus(final Integer auditStatus) {
      this.auditStatus = auditStatus;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEmbyServerId(final String embyServerId) {
      this.embyServerId = embyServerId;
   }

   @Generated
   public void setSeason(final Integer season) {
      this.season = season;
   }

   @Generated
   public void setEpisode(final Integer episode) {
      this.episode = episode;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestListRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbId = this.getTmdbId();
         Object other$tmdbId = other.getTmdbId();
         if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$auditStatus = this.getAuditStatus();
               Object other$auditStatus = other.getAuditStatus();
               if (this$auditStatus == null ? other$auditStatus == null : this$auditStatus.equals(other$auditStatus)) {
                  Object this$embyInfoId = this.getEmbyInfoId();
                  Object other$embyInfoId = other.getEmbyInfoId();
                  if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                     Object this$season = this.getSeason();
                     Object other$season = other.getSeason();
                     if (this$season == null ? other$season == null : this$season.equals(other$season)) {
                        Object this$episode = this.getEpisode();
                        Object other$episode = other.getEpisode();
                        if (this$episode == null ? other$episode == null : this$episode.equals(other$episode)) {
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                              Object this$type = this.getType();
                              Object other$type = other.getType();
                              if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                 Object this$doubanId = this.getDoubanId();
                                 Object other$doubanId = other.getDoubanId();
                                 if (this$doubanId == null ? other$doubanId == null : this$doubanId.equals(other$doubanId)) {
                                    Object this$embyUserName = this.getEmbyUserName();
                                    Object other$embyUserName = other.getEmbyUserName();
                                    if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                       Object this$embyServerId = this.getEmbyServerId();
                                       Object other$embyServerId = other.getEmbyServerId();
                                       return this$embyServerId == null ? other$embyServerId == null : this$embyServerId.equals(other$embyServerId);
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
      return other instanceof RequestListRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $auditStatus = this.getAuditStatus();
      result = result * 59 + ($auditStatus == null ? 43 : $auditStatus.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $episode = this.getEpisode();
      result = result * 59 + ($episode == null ? 43 : $episode.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      return result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestListRequest(name="
         + this.getName()
         + ", type="
         + this.getType()
         + ", tmdbId="
         + this.getTmdbId()
         + ", doubanId="
         + this.getDoubanId()
         + ", status="
         + this.getStatus()
         + ", auditStatus="
         + this.getAuditStatus()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", season="
         + this.getSeason()
         + ", episode="
         + this.getEpisode()
         + ")";
   }
}
