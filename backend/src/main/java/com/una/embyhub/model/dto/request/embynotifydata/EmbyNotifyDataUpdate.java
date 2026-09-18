package com.una.embyhub.model.dto.request.embynotifydata;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyNotifyDataUpdate implements Serializable {
   private Long id;
   private String name;
   private String overview;
   private String productionYear;
   private String type;
   private Integer status;
   private String imgUrl;
   private String tmdbUrl;
   private String displayTitle;
   private String genres;
   private String size;
   private Double voteAverage;
   private Integer voteCount;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;
   private String backdropPath;
   private Long embyInfoId;

   @Generated
   public Long getId() {
      return this.id;
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
   public String getProductionYear() {
      return this.productionYear;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getImgUrl() {
      return this.imgUrl;
   }

   @Generated
   public String getTmdbUrl() {
      return this.tmdbUrl;
   }

   @Generated
   public String getDisplayTitle() {
      return this.displayTitle;
   }

   @Generated
   public String getGenres() {
      return this.genres;
   }

   @Generated
   public String getSize() {
      return this.size;
   }

   @Generated
   public Double getVoteAverage() {
      return this.voteAverage;
   }

   @Generated
   public Integer getVoteCount() {
      return this.voteCount;
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
   public Long getUpdateUserId() {
      return this.updateUserId;
   }

   @Generated
   public Long getCreateUserId() {
      return this.createUserId;
   }

   @Generated
   public Integer getDelFlag() {
      return this.delFlag;
   }

   @Generated
   public String getBackdropPath() {
      return this.backdropPath;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setProductionYear(final String productionYear) {
      this.productionYear = productionYear;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setImgUrl(final String imgUrl) {
      this.imgUrl = imgUrl;
   }

   @Generated
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
   }

   @Generated
   public void setDisplayTitle(final String displayTitle) {
      this.displayTitle = displayTitle;
   }

   @Generated
   public void setGenres(final String genres) {
      this.genres = genres;
   }

   @Generated
   public void setSize(final String size) {
      this.size = size;
   }

   @Generated
   public void setVoteAverage(final Double voteAverage) {
      this.voteAverage = voteAverage;
   }

   @Generated
   public void setVoteCount(final Integer voteCount) {
      this.voteCount = voteCount;
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
   public void setUpdateUserId(final Long updateUserId) {
      this.updateUserId = updateUserId;
   }

   @Generated
   public void setCreateUserId(final Long createUserId) {
      this.createUserId = createUserId;
   }

   @Generated
   public void setDelFlag(final Integer delFlag) {
      this.delFlag = delFlag;
   }

   @Generated
   public void setBackdropPath(final String backdropPath) {
      this.backdropPath = backdropPath;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyNotifyDataUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$voteAverage = this.getVoteAverage();
               Object other$voteAverage = other.getVoteAverage();
               if (this$voteAverage == null ? other$voteAverage == null : this$voteAverage.equals(other$voteAverage)) {
                  Object this$voteCount = this.getVoteCount();
                  Object other$voteCount = other.getVoteCount();
                  if (this$voteCount == null ? other$voteCount == null : this$voteCount.equals(other$voteCount)) {
                     Object this$updateUserId = this.getUpdateUserId();
                     Object other$updateUserId = other.getUpdateUserId();
                     if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                        Object this$createUserId = this.getCreateUserId();
                        Object other$createUserId = other.getCreateUserId();
                        if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                           Object this$delFlag = this.getDelFlag();
                           Object other$delFlag = other.getDelFlag();
                           if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                              Object this$embyInfoId = this.getEmbyInfoId();
                              Object other$embyInfoId = other.getEmbyInfoId();
                              if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                                 Object this$name = this.getName();
                                 Object other$name = other.getName();
                                 if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                    Object this$overview = this.getOverview();
                                    Object other$overview = other.getOverview();
                                    if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                       Object this$productionYear = this.getProductionYear();
                                       Object other$productionYear = other.getProductionYear();
                                       if (this$productionYear == null ? other$productionYear == null : this$productionYear.equals(other$productionYear)) {
                                          Object this$type = this.getType();
                                          Object other$type = other.getType();
                                          if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                             Object this$imgUrl = this.getImgUrl();
                                             Object other$imgUrl = other.getImgUrl();
                                             if (this$imgUrl == null ? other$imgUrl == null : this$imgUrl.equals(other$imgUrl)) {
                                                Object this$tmdbUrl = this.getTmdbUrl();
                                                Object other$tmdbUrl = other.getTmdbUrl();
                                                if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                                                   Object this$displayTitle = this.getDisplayTitle();
                                                   Object other$displayTitle = other.getDisplayTitle();
                                                   if (this$displayTitle == null ? other$displayTitle == null : this$displayTitle.equals(other$displayTitle)) {
                                                      Object this$genres = this.getGenres();
                                                      Object other$genres = other.getGenres();
                                                      if (this$genres == null ? other$genres == null : this$genres.equals(other$genres)) {
                                                         Object this$size = this.getSize();
                                                         Object other$size = other.getSize();
                                                         if (this$size == null ? other$size == null : this$size.equals(other$size)) {
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
                                                                        Object this$backdropPath = this.getBackdropPath();
                                                                        Object other$backdropPath = other.getBackdropPath();
                                                                        return this$backdropPath == null
                                                                           ? other$backdropPath == null
                                                                           : this$backdropPath.equals(other$backdropPath);
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
      return other instanceof EmbyNotifyDataUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $voteAverage = this.getVoteAverage();
      result = result * 59 + ($voteAverage == null ? 43 : $voteAverage.hashCode());
      Object $voteCount = this.getVoteCount();
      result = result * 59 + ($voteCount == null ? 43 : $voteCount.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $productionYear = this.getProductionYear();
      result = result * 59 + ($productionYear == null ? 43 : $productionYear.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $imgUrl = this.getImgUrl();
      result = result * 59 + ($imgUrl == null ? 43 : $imgUrl.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $displayTitle = this.getDisplayTitle();
      result = result * 59 + ($displayTitle == null ? 43 : $displayTitle.hashCode());
      Object $genres = this.getGenres();
      result = result * 59 + ($genres == null ? 43 : $genres.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      result = result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
      Object $backdropPath = this.getBackdropPath();
      return result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyNotifyDataUpdate(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", overview="
         + this.getOverview()
         + ", productionYear="
         + this.getProductionYear()
         + ", type="
         + this.getType()
         + ", status="
         + this.getStatus()
         + ", imgUrl="
         + this.getImgUrl()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", displayTitle="
         + this.getDisplayTitle()
         + ", genres="
         + this.getGenres()
         + ", size="
         + this.getSize()
         + ", voteAverage="
         + this.getVoteAverage()
         + ", voteCount="
         + this.getVoteCount()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", updateUserName="
         + this.getUpdateUserName()
         + ", updateUserId="
         + this.getUpdateUserId()
         + ", createUserId="
         + this.getCreateUserId()
         + ", delFlag="
         + this.getDelFlag()
         + ", backdropPath="
         + this.getBackdropPath()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }
}
