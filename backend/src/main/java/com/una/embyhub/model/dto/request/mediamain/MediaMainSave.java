package com.una.embyhub.model.dto.request.mediamain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Generated;

public class MediaMainSave implements Serializable {
   private Long id;
   private String title;
   private Long embyInfoId;
   private String type;
   private String posterPath;
   private Date releaseDate;
   private Integer playCount;
   private BigDecimal rating;
   private Integer duration;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getPosterPath() {
      return this.posterPath;
   }

   @Generated
   public Date getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Integer getPlayCount() {
      return this.playCount;
   }

   @Generated
   public BigDecimal getRating() {
      return this.rating;
   }

   @Generated
   public Integer getDuration() {
      return this.duration;
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
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setPosterPath(final String posterPath) {
      this.posterPath = posterPath;
   }

   @Generated
   public void setReleaseDate(final Date releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setPlayCount(final Integer playCount) {
      this.playCount = playCount;
   }

   @Generated
   public void setRating(final BigDecimal rating) {
      this.rating = rating;
   }

   @Generated
   public void setDuration(final Integer duration) {
      this.duration = duration;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MediaMainSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$playCount = this.getPlayCount();
               Object other$playCount = other.getPlayCount();
               if (this$playCount == null ? other$playCount == null : this$playCount.equals(other$playCount)) {
                  Object this$duration = this.getDuration();
                  Object other$duration = other.getDuration();
                  if (this$duration == null ? other$duration == null : this$duration.equals(other$duration)) {
                     Object this$updateUserId = this.getUpdateUserId();
                     Object other$updateUserId = other.getUpdateUserId();
                     if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                        Object this$createUserId = this.getCreateUserId();
                        Object other$createUserId = other.getCreateUserId();
                        if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                           Object this$delFlag = this.getDelFlag();
                           Object other$delFlag = other.getDelFlag();
                           if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                              Object this$title = this.getTitle();
                              Object other$title = other.getTitle();
                              if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                 Object this$type = this.getType();
                                 Object other$type = other.getType();
                                 if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                    Object this$posterPath = this.getPosterPath();
                                    Object other$posterPath = other.getPosterPath();
                                    if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                       Object this$releaseDate = this.getReleaseDate();
                                       Object other$releaseDate = other.getReleaseDate();
                                       if (this$releaseDate == null ? other$releaseDate == null : this$releaseDate.equals(other$releaseDate)) {
                                          Object this$rating = this.getRating();
                                          Object other$rating = other.getRating();
                                          if (this$rating == null ? other$rating == null : this$rating.equals(other$rating)) {
                                             Object this$createDatetime = this.getCreateDatetime();
                                             Object other$createDatetime = other.getCreateDatetime();
                                             if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
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
                                                      return this$updateUserName == null
                                                         ? other$updateUserName == null
                                                         : this$updateUserName.equals(other$updateUserName);
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
      return other instanceof MediaMainSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $playCount = this.getPlayCount();
      result = result * 59 + ($playCount == null ? 43 : $playCount.hashCode());
      Object $duration = this.getDuration();
      result = result * 59 + ($duration == null ? 43 : $duration.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $rating = this.getRating();
      result = result * 59 + ($rating == null ? 43 : $rating.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      return result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MediaMainSave(id="
         + this.getId()
         + ", title="
         + this.getTitle()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", type="
         + this.getType()
         + ", posterPath="
         + this.getPosterPath()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", playCount="
         + this.getPlayCount()
         + ", rating="
         + this.getRating()
         + ", duration="
         + this.getDuration()
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
         + ")";
   }
}
