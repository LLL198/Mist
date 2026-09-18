package com.una.embyhub.model.dto.request.embynotifydatadetails;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyNotifyDataDetailsSave implements Serializable {
   private Long id;
   private Long embyNotifyDataId;
   private String episodeDetails;
   private String episodeInfo;
   private String size;
   private Integer status;
   private Long embyInfoId;
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
   public Long getEmbyNotifyDataId() {
      return this.embyNotifyDataId;
   }

   @Generated
   public String getEpisodeDetails() {
      return this.episodeDetails;
   }

   @Generated
   public String getEpisodeInfo() {
      return this.episodeInfo;
   }

   @Generated
   public String getSize() {
      return this.size;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public void setEmbyNotifyDataId(final Long embyNotifyDataId) {
      this.embyNotifyDataId = embyNotifyDataId;
   }

   @Generated
   public void setEpisodeDetails(final String episodeDetails) {
      this.episodeDetails = episodeDetails;
   }

   @Generated
   public void setEpisodeInfo(final String episodeInfo) {
      this.episodeInfo = episodeInfo;
   }

   @Generated
   public void setSize(final String size) {
      this.size = size;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
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
      } else if (!(o instanceof EmbyNotifyDataDetailsSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyNotifyDataId = this.getEmbyNotifyDataId();
            Object other$embyNotifyDataId = other.getEmbyNotifyDataId();
            if (this$embyNotifyDataId == null ? other$embyNotifyDataId == null : this$embyNotifyDataId.equals(other$embyNotifyDataId)) {
               Object this$status = this.getStatus();
               Object other$status = other.getStatus();
               if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                  Object this$embyInfoId = this.getEmbyInfoId();
                  Object other$embyInfoId = other.getEmbyInfoId();
                  if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                     Object this$updateUserId = this.getUpdateUserId();
                     Object other$updateUserId = other.getUpdateUserId();
                     if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                        Object this$createUserId = this.getCreateUserId();
                        Object other$createUserId = other.getCreateUserId();
                        if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                           Object this$delFlag = this.getDelFlag();
                           Object other$delFlag = other.getDelFlag();
                           if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                              Object this$episodeDetails = this.getEpisodeDetails();
                              Object other$episodeDetails = other.getEpisodeDetails();
                              if (this$episodeDetails == null ? other$episodeDetails == null : this$episodeDetails.equals(other$episodeDetails)) {
                                 Object this$episodeInfo = this.getEpisodeInfo();
                                 Object other$episodeInfo = other.getEpisodeInfo();
                                 if (this$episodeInfo == null ? other$episodeInfo == null : this$episodeInfo.equals(other$episodeInfo)) {
                                    Object this$size = this.getSize();
                                    Object other$size = other.getSize();
                                    if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                                       Object this$createDatetime = this.getCreateDatetime();
                                       Object other$createDatetime = other.getCreateDatetime();
                                       if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                          Object this$updateDatetime = this.getUpdateDatetime();
                                          Object other$updateDatetime = other.getUpdateDatetime();
                                          if (this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime)) {
                                             Object this$createUserName = this.getCreateUserName();
                                             Object other$createUserName = other.getCreateUserName();
                                             if (this$createUserName == null ? other$createUserName == null : this$createUserName.equals(other$createUserName)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyNotifyDataDetailsSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyNotifyDataId = this.getEmbyNotifyDataId();
      result = result * 59 + ($embyNotifyDataId == null ? 43 : $embyNotifyDataId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $episodeDetails = this.getEpisodeDetails();
      result = result * 59 + ($episodeDetails == null ? 43 : $episodeDetails.hashCode());
      Object $episodeInfo = this.getEpisodeInfo();
      result = result * 59 + ($episodeInfo == null ? 43 : $episodeInfo.hashCode());
      Object $size = this.getSize();
      result = result * 59 + ($size == null ? 43 : $size.hashCode());
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
      return "EmbyNotifyDataDetailsSave(id="
         + this.getId()
         + ", embyNotifyDataId="
         + this.getEmbyNotifyDataId()
         + ", episodeDetails="
         + this.getEpisodeDetails()
         + ", episodeInfo="
         + this.getEpisodeInfo()
         + ", size="
         + this.getSize()
         + ", status="
         + this.getStatus()
         + ", embyInfoId="
         + this.getEmbyInfoId()
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
