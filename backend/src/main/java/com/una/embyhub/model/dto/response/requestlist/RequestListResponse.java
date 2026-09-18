package com.una.embyhub.model.dto.response.requestlist;

import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.model.entity.EmbyInfo;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class RequestListResponse implements Serializable {
   private Long id;
   private String name;
   private String type;
   private String typeNmae;
   private String imageUrl;
   private String score;
   private String tmdbUrl;
   private String doubanId;
   private String doubanUrl;
   private String doubanScore;
   private String doubanImage;
   private Date releaseDate;
   private Integer tmdbId;
   private String overview;
   private Long userId;
   private Integer status;
   private String statusName;
   private Integer auditStatus;
   private String auditStatusName;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private String embyUserName;
   private Long embyInfoId;
   private String embyServerId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String embyServerName;
   private String backdropPath;
   private Integer parentTmdbId;
   private Integer season;
   private Integer episode;
   private String remark;
   private Long moviePilotSubscriptionId;

   public void setType(String type) {
      this.type = type;
      if (type.equals("movie")) {
         this.typeNmae = "电影";
      } else if (type.equals("tv")) {
         this.typeNmae = "电视剧";
      }
   }

   public void setStatus(Integer status) {
      this.status = status;
      if (status == 0) {
         this.statusName = "已提交";
      } else if (status == 1) {
         this.statusName = "已入库";
      } else if (status == 2) {
         this.statusName = "已拒绝";
      }
   }

   public void setAuditStatus(Integer auditStatus) {
      this.auditStatus = auditStatus;
      if (auditStatus != null) {
         if (auditStatus == 0) {
            this.auditStatusName = "待审核";
         } else if (auditStatus == 1) {
            this.auditStatusName = "已通过";
         } else if (auditStatus == 2) {
            this.auditStatusName = "已拒绝";
         }
      }
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getTypeNmae() {
      return this.typeNmae;
   }

   @Generated
   public String getImageUrl() {
      return this.imageUrl;
   }

   @Generated
   public String getScore() {
      return this.score;
   }

   @Generated
   public String getTmdbUrl() {
      return this.tmdbUrl;
   }

   @Generated
   public String getDoubanId() {
      return this.doubanId;
   }

   @Generated
   public String getDoubanUrl() {
      return this.doubanUrl;
   }

   @Generated
   public String getDoubanScore() {
      return this.doubanScore;
   }

   @Generated
   public String getDoubanImage() {
      return this.doubanImage;
   }

   @Generated
   public Date getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getStatusName() {
      return this.statusName;
   }

   @Generated
   public Integer getAuditStatus() {
      return this.auditStatus;
   }

   @Generated
   public String getAuditStatusName() {
      return this.auditStatusName;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public String getCreateUserName() {
      return this.createUserName;
   }

   @Generated
   public String getUpdateUserName() {
      return this.updateUserName;
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
   public String getEmbyServerName() {
      return this.embyServerName;
   }

   @Generated
   public String getBackdropPath() {
      return this.backdropPath;
   }

   @Generated
   public Integer getParentTmdbId() {
      return this.parentTmdbId;
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
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public Long getMoviePilotSubscriptionId() {
      return this.moviePilotSubscriptionId;
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
   public void setTypeNmae(final String typeNmae) {
      this.typeNmae = typeNmae;
   }

   @Generated
   public void setImageUrl(final String imageUrl) {
      this.imageUrl = imageUrl;
   }

   @Generated
   public void setScore(final String score) {
      this.score = score;
   }

   @Generated
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
   }

   @Generated
   public void setDoubanId(final String doubanId) {
      this.doubanId = doubanId;
   }

   @Generated
   public void setDoubanUrl(final String doubanUrl) {
      this.doubanUrl = doubanUrl;
   }

   @Generated
   public void setDoubanScore(final String doubanScore) {
      this.doubanScore = doubanScore;
   }

   @Generated
   public void setDoubanImage(final String doubanImage) {
      this.doubanImage = doubanImage;
   }

   @Generated
   public void setReleaseDate(final Date releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setStatusName(final String statusName) {
      this.statusName = statusName;
   }

   @Generated
   public void setAuditStatusName(final String auditStatusName) {
      this.auditStatusName = auditStatusName;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   public void setCreateUserName(final String createUserName) {
      this.createUserName = createUserName;
   }

   @Generated
   public void setUpdateUserName(final String updateUserName) {
      this.updateUserName = updateUserName;
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
   public void setEmbyServerName(final String embyServerName) {
      this.embyServerName = embyServerName;
   }

   @Generated
   public void setBackdropPath(final String backdropPath) {
      this.backdropPath = backdropPath;
   }

   @Generated
   public void setParentTmdbId(final Integer parentTmdbId) {
      this.parentTmdbId = parentTmdbId;
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
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   public void setMoviePilotSubscriptionId(final Long moviePilotSubscriptionId) {
      this.moviePilotSubscriptionId = moviePilotSubscriptionId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestListResponse other)) {
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
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$status = this.getStatus();
                  Object other$status = other.getStatus();
                  if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                     Object this$auditStatus = this.getAuditStatus();
                     Object other$auditStatus = other.getAuditStatus();
                     if (this$auditStatus == null ? other$auditStatus == null : this$auditStatus.equals(other$auditStatus)) {
                        Object this$embyInfoId = this.getEmbyInfoId();
                        Object other$embyInfoId = other.getEmbyInfoId();
                        if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                           Object this$parentTmdbId = this.getParentTmdbId();
                           Object other$parentTmdbId = other.getParentTmdbId();
                           if (this$parentTmdbId == null ? other$parentTmdbId == null : this$parentTmdbId.equals(other$parentTmdbId)) {
                              Object this$season = this.getSeason();
                              Object other$season = other.getSeason();
                              if (this$season == null ? other$season == null : this$season.equals(other$season)) {
                                 Object this$episode = this.getEpisode();
                                 Object other$episode = other.getEpisode();
                                 if (this$episode == null ? other$episode == null : this$episode.equals(other$episode)) {
                                    Object this$moviePilotSubscriptionId = this.getMoviePilotSubscriptionId();
                                    Object other$moviePilotSubscriptionId = other.getMoviePilotSubscriptionId();
                                    if (this$moviePilotSubscriptionId == null
                                       ? other$moviePilotSubscriptionId == null
                                       : this$moviePilotSubscriptionId.equals(other$moviePilotSubscriptionId)) {
                                       Object this$name = this.getName();
                                       Object other$name = other.getName();
                                       if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                          Object this$type = this.getType();
                                          Object other$type = other.getType();
                                          if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                             Object this$typeNmae = this.getTypeNmae();
                                             Object other$typeNmae = other.getTypeNmae();
                                             if (this$typeNmae == null ? other$typeNmae == null : this$typeNmae.equals(other$typeNmae)) {
                                                Object this$imageUrl = this.getImageUrl();
                                                Object other$imageUrl = other.getImageUrl();
                                                if (this$imageUrl == null ? other$imageUrl == null : this$imageUrl.equals(other$imageUrl)) {
                                                   Object this$score = this.getScore();
                                                   Object other$score = other.getScore();
                                                   if (this$score == null ? other$score == null : this$score.equals(other$score)) {
                                                      Object this$tmdbUrl = this.getTmdbUrl();
                                                      Object other$tmdbUrl = other.getTmdbUrl();
                                                      if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                                                         Object this$doubanId = this.getDoubanId();
                                                         Object other$doubanId = other.getDoubanId();
                                                         if (this$doubanId == null ? other$doubanId == null : this$doubanId.equals(other$doubanId)) {
                                                            Object this$doubanUrl = this.getDoubanUrl();
                                                            Object other$doubanUrl = other.getDoubanUrl();
                                                            if (this$doubanUrl == null ? other$doubanUrl == null : this$doubanUrl.equals(other$doubanUrl)) {
                                                               Object this$doubanScore = this.getDoubanScore();
                                                               Object other$doubanScore = other.getDoubanScore();
                                                               if (this$doubanScore == null
                                                                  ? other$doubanScore == null
                                                                  : this$doubanScore.equals(other$doubanScore)) {
                                                                  Object this$doubanImage = this.getDoubanImage();
                                                                  Object other$doubanImage = other.getDoubanImage();
                                                                  if (this$doubanImage == null
                                                                     ? other$doubanImage == null
                                                                     : this$doubanImage.equals(other$doubanImage)) {
                                                                     Object this$releaseDate = this.getReleaseDate();
                                                                     Object other$releaseDate = other.getReleaseDate();
                                                                     if (this$releaseDate == null
                                                                        ? other$releaseDate == null
                                                                        : this$releaseDate.equals(other$releaseDate)) {
                                                                        Object this$overview = this.getOverview();
                                                                        Object other$overview = other.getOverview();
                                                                        if (this$overview == null
                                                                           ? other$overview == null
                                                                           : this$overview.equals(other$overview)) {
                                                                           Object this$statusName = this.getStatusName();
                                                                           Object other$statusName = other.getStatusName();
                                                                           if (this$statusName == null
                                                                              ? other$statusName == null
                                                                              : this$statusName.equals(other$statusName)) {
                                                                              Object this$auditStatusName = this.getAuditStatusName();
                                                                              Object other$auditStatusName = other.getAuditStatusName();
                                                                              if (this$auditStatusName == null
                                                                                 ? other$auditStatusName == null
                                                                                 : this$auditStatusName.equals(other$auditStatusName)) {
                                                                                 Object this$createDatetime = this.getCreateDatetime();
                                                                                 Object other$createDatetime = other.getCreateDatetime();
                                                                                 if (this$createDatetime == null
                                                                                    ? other$createDatetime == null
                                                                                    : this$createDatetime.equals(other$createDatetime)) {
                                                                                    Object this$updateDatetime = this.getUpdateDatetime();
                                                                                    Object other$updateDatetime = other.getUpdateDatetime();
                                                                                    if (this$updateDatetime == null
                                                                                       ? other$updateDatetime == null
                                                                                       : this$updateDatetime.equals(other$updateDatetime)) {
                                                                                       Object this$createUserName = this.getCreateUserName();
                                                                                       Object other$createUserName = other.getCreateUserName();
                                                                                       if (this$createUserName == null
                                                                                          ? other$createUserName == null
                                                                                          : this$createUserName.equals(other$createUserName)) {
                                                                                          Object this$updateUserName = this.getUpdateUserName();
                                                                                          Object other$updateUserName = other.getUpdateUserName();
                                                                                          if (this$updateUserName == null
                                                                                             ? other$updateUserName == null
                                                                                             : this$updateUserName.equals(other$updateUserName)) {
                                                                                             Object this$embyUserName = this.getEmbyUserName();
                                                                                             Object other$embyUserName = other.getEmbyUserName();
                                                                                             if (this$embyUserName == null
                                                                                                ? other$embyUserName == null
                                                                                                : this$embyUserName.equals(other$embyUserName)) {
                                                                                                Object this$embyServerId = this.getEmbyServerId();
                                                                                                Object other$embyServerId = other.getEmbyServerId();
                                                                                                if (this$embyServerId == null
                                                                                                   ? other$embyServerId == null
                                                                                                   : this$embyServerId.equals(other$embyServerId)) {
                                                                                                   Object this$embyServerName = this.getEmbyServerName();
                                                                                                   Object other$embyServerName = other.getEmbyServerName();
                                                                                                   if (this$embyServerName == null
                                                                                                      ? other$embyServerName == null
                                                                                                      : this$embyServerName.equals(other$embyServerName)) {
                                                                                                      Object this$backdropPath = this.getBackdropPath();
                                                                                                      Object other$backdropPath = other.getBackdropPath();
                                                                                                      if (this$backdropPath == null
                                                                                                         ? other$backdropPath == null
                                                                                                         : this$backdropPath.equals(other$backdropPath)) {
                                                                                                         Object this$remark = this.getRemark();
                                                                                                         Object other$remark = other.getRemark();
                                                                                                         return this$remark == null
                                                                                                            ? other$remark == null
                                                                                                            : this$remark.equals(other$remark);
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
      return other instanceof RequestListResponse;
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
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $auditStatus = this.getAuditStatus();
      result = result * 59 + ($auditStatus == null ? 43 : $auditStatus.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $parentTmdbId = this.getParentTmdbId();
      result = result * 59 + ($parentTmdbId == null ? 43 : $parentTmdbId.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $episode = this.getEpisode();
      result = result * 59 + ($episode == null ? 43 : $episode.hashCode());
      Object $moviePilotSubscriptionId = this.getMoviePilotSubscriptionId();
      result = result * 59 + ($moviePilotSubscriptionId == null ? 43 : $moviePilotSubscriptionId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $typeNmae = this.getTypeNmae();
      result = result * 59 + ($typeNmae == null ? 43 : $typeNmae.hashCode());
      Object $imageUrl = this.getImageUrl();
      result = result * 59 + ($imageUrl == null ? 43 : $imageUrl.hashCode());
      Object $score = this.getScore();
      result = result * 59 + ($score == null ? 43 : $score.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $doubanUrl = this.getDoubanUrl();
      result = result * 59 + ($doubanUrl == null ? 43 : $doubanUrl.hashCode());
      Object $doubanScore = this.getDoubanScore();
      result = result * 59 + ($doubanScore == null ? 43 : $doubanScore.hashCode());
      Object $doubanImage = this.getDoubanImage();
      result = result * 59 + ($doubanImage == null ? 43 : $doubanImage.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $statusName = this.getStatusName();
      result = result * 59 + ($statusName == null ? 43 : $statusName.hashCode());
      Object $auditStatusName = this.getAuditStatusName();
      result = result * 59 + ($auditStatusName == null ? 43 : $auditStatusName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      result = result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $embyServerName = this.getEmbyServerName();
      result = result * 59 + ($embyServerName == null ? 43 : $embyServerName.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestListResponse(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", type="
         + this.getType()
         + ", typeNmae="
         + this.getTypeNmae()
         + ", imageUrl="
         + this.getImageUrl()
         + ", score="
         + this.getScore()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", doubanId="
         + this.getDoubanId()
         + ", doubanUrl="
         + this.getDoubanUrl()
         + ", doubanScore="
         + this.getDoubanScore()
         + ", doubanImage="
         + this.getDoubanImage()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", tmdbId="
         + this.getTmdbId()
         + ", overview="
         + this.getOverview()
         + ", userId="
         + this.getUserId()
         + ", status="
         + this.getStatus()
         + ", statusName="
         + this.getStatusName()
         + ", auditStatus="
         + this.getAuditStatus()
         + ", auditStatusName="
         + this.getAuditStatusName()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", updateUserName="
         + this.getUpdateUserName()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", embyServerName="
         + this.getEmbyServerName()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", parentTmdbId="
         + this.getParentTmdbId()
         + ", season="
         + this.getSeason()
         + ", episode="
         + this.getEpisode()
         + ", remark="
         + this.getRemark()
         + ", moviePilotSubscriptionId="
         + this.getMoviePilotSubscriptionId()
         + ")";
   }
}
