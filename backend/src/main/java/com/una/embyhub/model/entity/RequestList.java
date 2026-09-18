package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("request_list")
public class RequestList extends BaseEntity implements Serializable {
   public static final String COL_ID = "id";
   public static final String COL_NAME = "name";
   public static final String COL_TYPE = "type";
   public static final String COL_IMAGE_URL = "image_url";
   public static final String COL_SCORE = "score";
   public static final String COL_TMDB_URL = "tmdb_url";
   public static final String COL_RELEASE_DATE = "release_date";
   public static final String COL_TMDB_ID = "tmdb_id";
   public static final String COL_OVERVIEW = "overview";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_STATUS = "status";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_EMBY_SERVER_ID = "emby_server_id";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";
   public static final String COL_BACKDROP_PATH = "backdrop_path";
   public static final String COL_REMARK = "remark";
   public static final String COL_AUDIT_STATUS = "audit_status";
   public static final String COL_DOUBAN_ID = "douban_id";
   public static final String COL_DOUBAN_URL = "douban_url";
   public static final String COL_DOUBAN_SCORE = "douban_score";
   public static final String COL_DOUBAN_IMAGE = "douban_image";
   public static final String COL_REQUEST_SOURCE = "request_source";
   public static final String COL_TELEGRAM_USER_ID = "telegram_user_id";
   public static final String COL_POINTS_COST = "points_cost";
   public static final String COL_POINTS_REFUNDED = "points_refunded";
   public static final String COL_POINTS_REF_ID = "points_ref_id";
   public static final String COL_MOVIE_PILOT_SUBSCRIPTION_ID = "movie_pilot_subscription_id";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("`name`")
   private String name;
   @TableField("`type`")
   private String type;
   @TableField("image_url")
   private String imageUrl;
   @TableField("score")
   private String score;
   @TableField("tmdb_url")
   private String tmdbUrl;
   @TableField("release_date")
   private Date releaseDate;
   @TableField("parent_tmdb_id")
   private Integer parentTmdbId;
   @TableField("tmdb_id")
   private Integer tmdbId;
   @TableField("overview")
   private String overview;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("emby_server_id")
   private String embyServerId;
   @TableField("`status`")
   private Integer status;
   @TableField("douban_id")
   private String doubanId;
   @TableField("douban_url")
   private String doubanUrl;
   @TableField("douban_score")
   private String doubanScore;
   @TableField("douban_image")
   private String doubanImage;
   @TableField("audit_status")
   private Integer auditStatus;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("backdrop_path")
   private String backdropPath;
   @TableField("season")
   private Integer season;
   @TableField("episode")
   private Integer episode;
   @TableField("remark")
   private String remark;
   @TableField("runtime")
   private Integer runtime;
   @TableField("production_countries")
   private String productionCountries;
   @TableField("request_source")
   private String requestSource;
   @TableField("telegram_user_id")
   private Long telegramUserId;
   @TableField("points_cost")
   private Integer pointsCost;
   @TableField("points_refunded")
   private Integer pointsRefunded;
   @TableField("points_ref_id")
   private String pointsRefId;
   @TableField("movie_pilot_subscription_id")
   private Long moviePilotSubscriptionId;

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
   public Date getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Integer getParentTmdbId() {
      return this.parentTmdbId;
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getEmbyServerId() {
      return this.embyServerId;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
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
   public Integer getAuditStatus() {
      return this.auditStatus;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
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
   public Integer getEpisode() {
      return this.episode;
   }

   @Generated
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public Integer getRuntime() {
      return this.runtime;
   }

   @Generated
   public String getProductionCountries() {
      return this.productionCountries;
   }

   @Generated
   public String getRequestSource() {
      return this.requestSource;
   }

   @Generated
   public Long getTelegramUserId() {
      return this.telegramUserId;
   }

   @Generated
   public Integer getPointsCost() {
      return this.pointsCost;
   }

   @Generated
   public Integer getPointsRefunded() {
      return this.pointsRefunded;
   }

   @Generated
   public String getPointsRefId() {
      return this.pointsRefId;
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
   public void setType(final String type) {
      this.type = type;
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
   public void setReleaseDate(final Date releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setParentTmdbId(final Integer parentTmdbId) {
      this.parentTmdbId = parentTmdbId;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEmbyServerId(final String embyServerId) {
      this.embyServerId = embyServerId;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
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
   public void setAuditStatus(final Integer auditStatus) {
      this.auditStatus = auditStatus;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
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
   public void setEpisode(final Integer episode) {
      this.episode = episode;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   public void setRuntime(final Integer runtime) {
      this.runtime = runtime;
   }

   @Generated
   public void setProductionCountries(final String productionCountries) {
      this.productionCountries = productionCountries;
   }

   @Generated
   public void setRequestSource(final String requestSource) {
      this.requestSource = requestSource;
   }

   @Generated
   public void setTelegramUserId(final Long telegramUserId) {
      this.telegramUserId = telegramUserId;
   }

   @Generated
   public void setPointsCost(final Integer pointsCost) {
      this.pointsCost = pointsCost;
   }

   @Generated
   public void setPointsRefunded(final Integer pointsRefunded) {
      this.pointsRefunded = pointsRefunded;
   }

   @Generated
   public void setPointsRefId(final String pointsRefId) {
      this.pointsRefId = pointsRefId;
   }

   @Generated
   public void setMoviePilotSubscriptionId(final Long moviePilotSubscriptionId) {
      this.moviePilotSubscriptionId = moviePilotSubscriptionId;
   }

   @Generated
   @Override
   public String toString() {
      return "RequestList(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", type="
         + this.getType()
         + ", imageUrl="
         + this.getImageUrl()
         + ", score="
         + this.getScore()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", parentTmdbId="
         + this.getParentTmdbId()
         + ", tmdbId="
         + this.getTmdbId()
         + ", overview="
         + this.getOverview()
         + ", userId="
         + this.getUserId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", status="
         + this.getStatus()
         + ", doubanId="
         + this.getDoubanId()
         + ", doubanUrl="
         + this.getDoubanUrl()
         + ", doubanScore="
         + this.getDoubanScore()
         + ", doubanImage="
         + this.getDoubanImage()
         + ", auditStatus="
         + this.getAuditStatus()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", season="
         + this.getSeason()
         + ", episode="
         + this.getEpisode()
         + ", remark="
         + this.getRemark()
         + ", runtime="
         + this.getRuntime()
         + ", productionCountries="
         + this.getProductionCountries()
         + ", requestSource="
         + this.getRequestSource()
         + ", telegramUserId="
         + this.getTelegramUserId()
         + ", pointsCost="
         + this.getPointsCost()
         + ", pointsRefunded="
         + this.getPointsRefunded()
         + ", pointsRefId="
         + this.getPointsRefId()
         + ", moviePilotSubscriptionId="
         + this.getMoviePilotSubscriptionId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestList other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$parentTmdbId = this.getParentTmdbId();
            Object other$parentTmdbId = other.getParentTmdbId();
            if (this$parentTmdbId == null ? other$parentTmdbId == null : this$parentTmdbId.equals(other$parentTmdbId)) {
               Object this$tmdbId = this.getTmdbId();
               Object other$tmdbId = other.getTmdbId();
               if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
                  Object this$userId = this.getUserId();
                  Object other$userId = other.getUserId();
                  if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                     Object this$embyInfoId = this.getEmbyInfoId();
                     Object other$embyInfoId = other.getEmbyInfoId();
                     if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                        Object this$status = this.getStatus();
                        Object other$status = other.getStatus();
                        if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                           Object this$auditStatus = this.getAuditStatus();
                           Object other$auditStatus = other.getAuditStatus();
                           if (this$auditStatus == null ? other$auditStatus == null : this$auditStatus.equals(other$auditStatus)) {
                              Object this$season = this.getSeason();
                              Object other$season = other.getSeason();
                              if (this$season == null ? other$season == null : this$season.equals(other$season)) {
                                 Object this$episode = this.getEpisode();
                                 Object other$episode = other.getEpisode();
                                 if (this$episode == null ? other$episode == null : this$episode.equals(other$episode)) {
                                    Object this$runtime = this.getRuntime();
                                    Object other$runtime = other.getRuntime();
                                    if (this$runtime == null ? other$runtime == null : this$runtime.equals(other$runtime)) {
                                       Object this$telegramUserId = this.getTelegramUserId();
                                       Object other$telegramUserId = other.getTelegramUserId();
                                       if (this$telegramUserId == null ? other$telegramUserId == null : this$telegramUserId.equals(other$telegramUserId)) {
                                          Object this$pointsCost = this.getPointsCost();
                                          Object other$pointsCost = other.getPointsCost();
                                          if (this$pointsCost == null ? other$pointsCost == null : this$pointsCost.equals(other$pointsCost)) {
                                             Object this$pointsRefunded = this.getPointsRefunded();
                                             Object other$pointsRefunded = other.getPointsRefunded();
                                             if (this$pointsRefunded == null ? other$pointsRefunded == null : this$pointsRefunded.equals(other$pointsRefunded)) {
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
                                                         Object this$imageUrl = this.getImageUrl();
                                                         Object other$imageUrl = other.getImageUrl();
                                                         if (this$imageUrl == null ? other$imageUrl == null : this$imageUrl.equals(other$imageUrl)) {
                                                            Object this$score = this.getScore();
                                                            Object other$score = other.getScore();
                                                            if (this$score == null ? other$score == null : this$score.equals(other$score)) {
                                                               Object this$tmdbUrl = this.getTmdbUrl();
                                                               Object other$tmdbUrl = other.getTmdbUrl();
                                                               if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                                                                  Object this$releaseDate = this.getReleaseDate();
                                                                  Object other$releaseDate = other.getReleaseDate();
                                                                  if (this$releaseDate == null
                                                                     ? other$releaseDate == null
                                                                     : this$releaseDate.equals(other$releaseDate)) {
                                                                     Object this$overview = this.getOverview();
                                                                     Object other$overview = other.getOverview();
                                                                     if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                                                        Object this$embyServerId = this.getEmbyServerId();
                                                                        Object other$embyServerId = other.getEmbyServerId();
                                                                        if (this$embyServerId == null
                                                                           ? other$embyServerId == null
                                                                           : this$embyServerId.equals(other$embyServerId)) {
                                                                           Object this$doubanId = this.getDoubanId();
                                                                           Object other$doubanId = other.getDoubanId();
                                                                           if (this$doubanId == null
                                                                              ? other$doubanId == null
                                                                              : this$doubanId.equals(other$doubanId)) {
                                                                              Object this$doubanUrl = this.getDoubanUrl();
                                                                              Object other$doubanUrl = other.getDoubanUrl();
                                                                              if (this$doubanUrl == null
                                                                                 ? other$doubanUrl == null
                                                                                 : this$doubanUrl.equals(other$doubanUrl)) {
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
                                                                                       Object this$embyUserName = this.getEmbyUserName();
                                                                                       Object other$embyUserName = other.getEmbyUserName();
                                                                                       if (this$embyUserName == null
                                                                                          ? other$embyUserName == null
                                                                                          : this$embyUserName.equals(other$embyUserName)) {
                                                                                          Object this$backdropPath = this.getBackdropPath();
                                                                                          Object other$backdropPath = other.getBackdropPath();
                                                                                          if (this$backdropPath == null
                                                                                             ? other$backdropPath == null
                                                                                             : this$backdropPath.equals(other$backdropPath)) {
                                                                                             Object this$remark = this.getRemark();
                                                                                             Object other$remark = other.getRemark();
                                                                                             if (this$remark == null
                                                                                                ? other$remark == null
                                                                                                : this$remark.equals(other$remark)) {
                                                                                                Object this$productionCountries = this.getProductionCountries();
                                                                                                Object other$productionCountries = other.getProductionCountries();
                                                                                                if (this$productionCountries == null
                                                                                                   ? other$productionCountries == null
                                                                                                   : this$productionCountries.equals(other$productionCountries)
                                                                                                   )
                                                                                                 {
                                                                                                   Object this$requestSource = this.getRequestSource();
                                                                                                   Object other$requestSource = other.getRequestSource();
                                                                                                   if (this$requestSource == null
                                                                                                      ? other$requestSource == null
                                                                                                      : this$requestSource.equals(other$requestSource)) {
                                                                                                      Object this$pointsRefId = this.getPointsRefId();
                                                                                                      Object other$pointsRefId = other.getPointsRefId();
                                                                                                      return this$pointsRefId == null
                                                                                                         ? other$pointsRefId == null
                                                                                                         : this$pointsRefId.equals(other$pointsRefId);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof RequestList;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $parentTmdbId = this.getParentTmdbId();
      result = result * 59 + ($parentTmdbId == null ? 43 : $parentTmdbId.hashCode());
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $auditStatus = this.getAuditStatus();
      result = result * 59 + ($auditStatus == null ? 43 : $auditStatus.hashCode());
      Object $season = this.getSeason();
      result = result * 59 + ($season == null ? 43 : $season.hashCode());
      Object $episode = this.getEpisode();
      result = result * 59 + ($episode == null ? 43 : $episode.hashCode());
      Object $runtime = this.getRuntime();
      result = result * 59 + ($runtime == null ? 43 : $runtime.hashCode());
      Object $telegramUserId = this.getTelegramUserId();
      result = result * 59 + ($telegramUserId == null ? 43 : $telegramUserId.hashCode());
      Object $pointsCost = this.getPointsCost();
      result = result * 59 + ($pointsCost == null ? 43 : $pointsCost.hashCode());
      Object $pointsRefunded = this.getPointsRefunded();
      result = result * 59 + ($pointsRefunded == null ? 43 : $pointsRefunded.hashCode());
      Object $moviePilotSubscriptionId = this.getMoviePilotSubscriptionId();
      result = result * 59 + ($moviePilotSubscriptionId == null ? 43 : $moviePilotSubscriptionId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $imageUrl = this.getImageUrl();
      result = result * 59 + ($imageUrl == null ? 43 : $imageUrl.hashCode());
      Object $score = this.getScore();
      result = result * 59 + ($score == null ? 43 : $score.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $doubanId = this.getDoubanId();
      result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
      Object $doubanUrl = this.getDoubanUrl();
      result = result * 59 + ($doubanUrl == null ? 43 : $doubanUrl.hashCode());
      Object $doubanScore = this.getDoubanScore();
      result = result * 59 + ($doubanScore == null ? 43 : $doubanScore.hashCode());
      Object $doubanImage = this.getDoubanImage();
      result = result * 59 + ($doubanImage == null ? 43 : $doubanImage.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $backdropPath = this.getBackdropPath();
      result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
      Object $remark = this.getRemark();
      result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
      Object $productionCountries = this.getProductionCountries();
      result = result * 59 + ($productionCountries == null ? 43 : $productionCountries.hashCode());
      Object $requestSource = this.getRequestSource();
      result = result * 59 + ($requestSource == null ? 43 : $requestSource.hashCode());
      Object $pointsRefId = this.getPointsRefId();
      return result * 59 + ($pointsRefId == null ? 43 : $pointsRefId.hashCode());
   }
}
