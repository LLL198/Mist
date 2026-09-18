package com.una.embyhub.model.dto.response.cardsecuritymanagement;

import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class CardSecurityManagementResponse implements Serializable {
   private Long id;
   private String cardPassword;
   private Integer cardValidity;
   private Integer cardStatus;
   private String cardStatusName;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;
   private Date expirationDate;
   private Long userId;
   private String embyUserName;
   private Long embyInfoId;
   private String copyfromuserid;
   private String remarks;
   private Integer hostLineType;
   private String hostLineTypeName;
   private String embyUrl;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;
   private Long distributorId;
   @BindField(
      entity = EmbyUser.class,
      field = "embyUserName",
      condition = "this.distributorId=id"
   )
   private String distributorName;
   private Integer isDistributor;

   public void setCardStatus(Integer cardStatus) {
      this.cardStatus = cardStatus;
      if (cardStatus == 0) {
         this.cardStatusName = "未使用";
      }

      if (cardStatus == 1) {
         this.cardStatusName = "已使用";
      }
   }

   public void setHostLineType(Integer hostLineType) {
      this.hostLineType = HostLineTypeEnum.normalize(hostLineType);
      this.hostLineTypeName = HostLineTypeEnum.resolveLabel(hostLineType);
   }

   public void setDistributorId(Long distributorId) {
      this.distributorId = distributorId;
      this.isDistributor = distributorId != null && distributorId > 0L ? 1 : 0;
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getCardPassword() {
      return this.cardPassword;
   }

   @Generated
   public Integer getCardValidity() {
      return this.cardValidity;
   }

   @Generated
   public Integer getCardStatus() {
      return this.cardStatus;
   }

   @Generated
   public String getCardStatusName() {
      return this.cardStatusName;
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
   public Date getExpirationDate() {
      return this.expirationDate;
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getCopyfromuserid() {
      return this.copyfromuserid;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public String getHostLineTypeName() {
      return this.hostLineTypeName;
   }

   @Generated
   public String getEmbyUrl() {
      return this.embyUrl;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public Long getDistributorId() {
      return this.distributorId;
   }

   @Generated
   public String getDistributorName() {
      return this.distributorName;
   }

   @Generated
   public Integer getIsDistributor() {
      return this.isDistributor;
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
   public void setCardValidity(final Integer cardValidity) {
      this.cardValidity = cardValidity;
   }

   @Generated
   public void setCardStatusName(final String cardStatusName) {
      this.cardStatusName = cardStatusName;
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
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setCopyfromuserid(final String copyfromuserid) {
      this.copyfromuserid = copyfromuserid;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setHostLineTypeName(final String hostLineTypeName) {
      this.hostLineTypeName = hostLineTypeName;
   }

   @Generated
   public void setEmbyUrl(final String embyUrl) {
      this.embyUrl = embyUrl;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setDistributorName(final String distributorName) {
      this.distributorName = distributorName;
   }

   @Generated
   public void setIsDistributor(final Integer isDistributor) {
      this.isDistributor = isDistributor;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CardSecurityManagementResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$cardValidity = this.getCardValidity();
            Object other$cardValidity = other.getCardValidity();
            if (this$cardValidity == null ? other$cardValidity == null : this$cardValidity.equals(other$cardValidity)) {
               Object this$cardStatus = this.getCardStatus();
               Object other$cardStatus = other.getCardStatus();
               if (this$cardStatus == null ? other$cardStatus == null : this$cardStatus.equals(other$cardStatus)) {
                  Object this$updateUserId = this.getUpdateUserId();
                  Object other$updateUserId = other.getUpdateUserId();
                  if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                     Object this$createUserId = this.getCreateUserId();
                     Object other$createUserId = other.getCreateUserId();
                     if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                        Object this$delFlag = this.getDelFlag();
                        Object other$delFlag = other.getDelFlag();
                        if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                           Object this$userId = this.getUserId();
                           Object other$userId = other.getUserId();
                           if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                              Object this$embyInfoId = this.getEmbyInfoId();
                              Object other$embyInfoId = other.getEmbyInfoId();
                              if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                                 Object this$hostLineType = this.getHostLineType();
                                 Object other$hostLineType = other.getHostLineType();
                                 if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                                    Object this$distributorId = this.getDistributorId();
                                    Object other$distributorId = other.getDistributorId();
                                    if (this$distributorId == null ? other$distributorId == null : this$distributorId.equals(other$distributorId)) {
                                       Object this$isDistributor = this.getIsDistributor();
                                       Object other$isDistributor = other.getIsDistributor();
                                       if (this$isDistributor == null ? other$isDistributor == null : this$isDistributor.equals(other$isDistributor)) {
                                          Object this$cardPassword = this.getCardPassword();
                                          Object other$cardPassword = other.getCardPassword();
                                          if (this$cardPassword == null ? other$cardPassword == null : this$cardPassword.equals(other$cardPassword)) {
                                             Object this$cardStatusName = this.getCardStatusName();
                                             Object other$cardStatusName = other.getCardStatusName();
                                             if (this$cardStatusName == null ? other$cardStatusName == null : this$cardStatusName.equals(other$cardStatusName)) {
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
                                                            Object this$expirationDate = this.getExpirationDate();
                                                            Object other$expirationDate = other.getExpirationDate();
                                                            if (this$expirationDate == null
                                                               ? other$expirationDate == null
                                                               : this$expirationDate.equals(other$expirationDate)) {
                                                               Object this$embyUserName = this.getEmbyUserName();
                                                               Object other$embyUserName = other.getEmbyUserName();
                                                               if (this$embyUserName == null
                                                                  ? other$embyUserName == null
                                                                  : this$embyUserName.equals(other$embyUserName)) {
                                                                  Object this$copyfromuserid = this.getCopyfromuserid();
                                                                  Object other$copyfromuserid = other.getCopyfromuserid();
                                                                  if (this$copyfromuserid == null
                                                                     ? other$copyfromuserid == null
                                                                     : this$copyfromuserid.equals(other$copyfromuserid)) {
                                                                     Object this$remarks = this.getRemarks();
                                                                     Object other$remarks = other.getRemarks();
                                                                     if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                                                                        Object this$hostLineTypeName = this.getHostLineTypeName();
                                                                        Object other$hostLineTypeName = other.getHostLineTypeName();
                                                                        if (this$hostLineTypeName == null
                                                                           ? other$hostLineTypeName == null
                                                                           : this$hostLineTypeName.equals(other$hostLineTypeName)) {
                                                                           Object this$embyUrl = this.getEmbyUrl();
                                                                           Object other$embyUrl = other.getEmbyUrl();
                                                                           if (this$embyUrl == null
                                                                              ? other$embyUrl == null
                                                                              : this$embyUrl.equals(other$embyUrl)) {
                                                                              Object this$serverName = this.getServerName();
                                                                              Object other$serverName = other.getServerName();
                                                                              if (this$serverName == null
                                                                                 ? other$serverName == null
                                                                                 : this$serverName.equals(other$serverName)) {
                                                                                 Object this$distributorName = this.getDistributorName();
                                                                                 Object other$distributorName = other.getDistributorName();
                                                                                 return this$distributorName == null
                                                                                    ? other$distributorName == null
                                                                                    : this$distributorName.equals(other$distributorName);
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
      return other instanceof CardSecurityManagementResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $cardValidity = this.getCardValidity();
      result = result * 59 + ($cardValidity == null ? 43 : $cardValidity.hashCode());
      Object $cardStatus = this.getCardStatus();
      result = result * 59 + ($cardStatus == null ? 43 : $cardStatus.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $distributorId = this.getDistributorId();
      result = result * 59 + ($distributorId == null ? 43 : $distributorId.hashCode());
      Object $isDistributor = this.getIsDistributor();
      result = result * 59 + ($isDistributor == null ? 43 : $isDistributor.hashCode());
      Object $cardPassword = this.getCardPassword();
      result = result * 59 + ($cardPassword == null ? 43 : $cardPassword.hashCode());
      Object $cardStatusName = this.getCardStatusName();
      result = result * 59 + ($cardStatusName == null ? 43 : $cardStatusName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      result = result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $copyfromuserid = this.getCopyfromuserid();
      result = result * 59 + ($copyfromuserid == null ? 43 : $copyfromuserid.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $hostLineTypeName = this.getHostLineTypeName();
      result = result * 59 + ($hostLineTypeName == null ? 43 : $hostLineTypeName.hashCode());
      Object $embyUrl = this.getEmbyUrl();
      result = result * 59 + ($embyUrl == null ? 43 : $embyUrl.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $distributorName = this.getDistributorName();
      return result * 59 + ($distributorName == null ? 43 : $distributorName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "CardSecurityManagementResponse(id="
         + this.getId()
         + ", cardPassword="
         + this.getCardPassword()
         + ", cardValidity="
         + this.getCardValidity()
         + ", cardStatus="
         + this.getCardStatus()
         + ", cardStatusName="
         + this.getCardStatusName()
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
         + ", expirationDate="
         + this.getExpirationDate()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", copyfromuserid="
         + this.getCopyfromuserid()
         + ", remarks="
         + this.getRemarks()
         + ", hostLineType="
         + this.getHostLineType()
         + ", hostLineTypeName="
         + this.getHostLineTypeName()
         + ", embyUrl="
         + this.getEmbyUrl()
         + ", serverName="
         + this.getServerName()
         + ", distributorId="
         + this.getDistributorId()
         + ", distributorName="
         + this.getDistributorName()
         + ", isDistributor="
         + this.getIsDistributor()
         + ")";
   }
}
