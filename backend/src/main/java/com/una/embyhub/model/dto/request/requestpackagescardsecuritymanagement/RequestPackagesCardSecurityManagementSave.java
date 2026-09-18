package com.una.embyhub.model.dto.request.requestpackagescardsecuritymanagement;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class RequestPackagesCardSecurityManagementSave implements Serializable {
   private Long id;
   private String cardPassword;
   private Integer cardCount;
   private Integer cardStatus;
   private Long userId;
   private String embyUserName;
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
   public String getCardPassword() {
      return this.cardPassword;
   }

   @Generated
   public Integer getCardCount() {
      return this.cardCount;
   }

   @Generated
   public Integer getCardStatus() {
      return this.cardStatus;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
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
   public void setCardPassword(final String cardPassword) {
      this.cardPassword = cardPassword;
   }

   @Generated
   public void setCardCount(final Integer cardCount) {
      this.cardCount = cardCount;
   }

   @Generated
   public void setCardStatus(final Integer cardStatus) {
      this.cardStatus = cardStatus;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
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
      } else if (!(o instanceof RequestPackagesCardSecurityManagementSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$cardCount = this.getCardCount();
            Object other$cardCount = other.getCardCount();
            if (this$cardCount == null ? other$cardCount == null : this$cardCount.equals(other$cardCount)) {
               Object this$cardStatus = this.getCardStatus();
               Object other$cardStatus = other.getCardStatus();
               if (this$cardStatus == null ? other$cardStatus == null : this$cardStatus.equals(other$cardStatus)) {
                  Object this$userId = this.getUserId();
                  Object other$userId = other.getUserId();
                  if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                     Object this$updateUserId = this.getUpdateUserId();
                     Object other$updateUserId = other.getUpdateUserId();
                     if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                        Object this$createUserId = this.getCreateUserId();
                        Object other$createUserId = other.getCreateUserId();
                        if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                           Object this$delFlag = this.getDelFlag();
                           Object other$delFlag = other.getDelFlag();
                           if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                              Object this$cardPassword = this.getCardPassword();
                              Object other$cardPassword = other.getCardPassword();
                              if (this$cardPassword == null ? other$cardPassword == null : this$cardPassword.equals(other$cardPassword)) {
                                 Object this$embyUserName = this.getEmbyUserName();
                                 Object other$embyUserName = other.getEmbyUserName();
                                 if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof RequestPackagesCardSecurityManagementSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $cardCount = this.getCardCount();
      result = result * 59 + ($cardCount == null ? 43 : $cardCount.hashCode());
      Object $cardStatus = this.getCardStatus();
      result = result * 59 + ($cardStatus == null ? 43 : $cardStatus.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $cardPassword = this.getCardPassword();
      result = result * 59 + ($cardPassword == null ? 43 : $cardPassword.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
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
      return "RequestPackagesCardSecurityManagementSave(id="
         + this.getId()
         + ", cardPassword="
         + this.getCardPassword()
         + ", cardCount="
         + this.getCardCount()
         + ", cardStatus="
         + this.getCardStatus()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
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
