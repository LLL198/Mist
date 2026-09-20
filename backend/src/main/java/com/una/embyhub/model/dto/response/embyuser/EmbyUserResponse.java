package com.una.embyhub.model.dto.response.embyuser;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diboot.core.binding.annotation.BindEntityList;
import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.rose.RoseBindingResponse;
import com.una.embyhub.model.entity.CardSecurityManagement;
import com.una.embyhub.model.entity.EmbyInfo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class EmbyUserResponse implements Serializable {
   private Long id;
   private String embyUserId;
   private String embyUserName;
   private Integer isAdmin;
   private Integer isPrimaryAdmin;
   private Integer userStatus;
   private String userStatusName;
   private Date expirationDate;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;
   @BindEntityList(
      entity = CardSecurityManagement.class,
      condition = "this.id=user_id"
   )
   private List<CardSecurityManagementResponse> cardSecurityManagementList;
   private Long expireDateCount;
   private String remarks;
   private Integer requestPackagesCount;
   private String email;
   private String mobile;
   private String gender;
   private Date birthday;
   private String interests;
   private String avatar;
   private Integer registerChannel;
   private String registerChannelName;
   private String inviterName;
   @TableField("emby_info_id")
   private Long embyInfoId;
   private Integer hostLineType;
   private String hostLineTypeName;
   private Integer isDistributor;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;
   private RoseBindingResponse roseBinding;
   private UserOauthBindingResponse telegramBinding;

   public String getRoleName() {
      if (Integer.valueOf(1).equals(this.isPrimaryAdmin)) {
         return "超管员";
      } else if (Integer.valueOf(1).equals(this.isAdmin)) {
         return "管理员";
      } else {
         return Integer.valueOf(1).equals(this.isDistributor) ? "分销商" : "普通用户";
      }
   }

   public void setUserStatus(Integer userStatus) {
      this.userStatus = userStatus;
      if (this.expireDateCount != null && this.expireDateCount <= 3L && this.expireDateCount >= 0L && this.userStatus == 0) {
         this.userStatusName = "即将过期";
      } else if (this.userStatus == 0) {
         this.userStatusName = "启用";
      } else if (this.userStatus == 1) {
         this.userStatusName = "禁用";
      }
   }

   public void setRegisterChannel(Integer registerChannel) {
      this.registerChannel = registerChannel;
      this.registerChannelName = RegisterChannelEnum.resolveLabel(registerChannel);
   }

   public void setHostLineType(Integer hostLineType) {
      this.hostLineType = HostLineTypeEnum.normalize(hostLineType);
      this.hostLineTypeName = HostLineTypeEnum.resolveUserRoleLabel(hostLineType);
   }

   public String getAccountScopeName() {
      return Integer.valueOf(1).equals(this.isPrimaryAdmin) ? "系统账号" : this.serverName;
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Integer getIsAdmin() {
      return this.isAdmin;
   }

   @Generated
   public Integer getIsPrimaryAdmin() {
      return this.isPrimaryAdmin;
   }

   @Generated
   public Integer getUserStatus() {
      return this.userStatus;
   }

   @Generated
   public String getUserStatusName() {
      return this.userStatusName;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
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
   public List<CardSecurityManagementResponse> getCardSecurityManagementList() {
      return this.cardSecurityManagementList;
   }

   @Generated
   public Long getExpireDateCount() {
      return this.expireDateCount;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Integer getRequestPackagesCount() {
      return this.requestPackagesCount;
   }

   @Generated
   public String getEmail() {
      return this.email;
   }

   @Generated
   public String getMobile() {
      return this.mobile;
   }

   @Generated
   public String getGender() {
      return this.gender;
   }

   @Generated
   public Date getBirthday() {
      return this.birthday;
   }

   @Generated
   public String getInterests() {
      return this.interests;
   }

   @Generated
   public String getAvatar() {
      return this.avatar;
   }

   @Generated
   public Integer getRegisterChannel() {
      return this.registerChannel;
   }

   @Generated
   public String getRegisterChannelName() {
      return this.registerChannelName;
   }

   @Generated
   public String getInviterName() {
      return this.inviterName;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public Integer getIsDistributor() {
      return this.isDistributor;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public RoseBindingResponse getRoseBinding() {
      return this.roseBinding;
   }

   @Generated
   public UserOauthBindingResponse getTelegramBinding() {
      return this.telegramBinding;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setIsAdmin(final Integer isAdmin) {
      this.isAdmin = isAdmin;
   }

   @Generated
   public void setIsPrimaryAdmin(final Integer isPrimaryAdmin) {
      this.isPrimaryAdmin = isPrimaryAdmin;
   }

   @Generated
   public void setUserStatusName(final String userStatusName) {
      this.userStatusName = userStatusName;
   }

   @Generated
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
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
   public void setCardSecurityManagementList(final List<CardSecurityManagementResponse> cardSecurityManagementList) {
      this.cardSecurityManagementList = cardSecurityManagementList;
   }

   @Generated
   public void setExpireDateCount(final Long expireDateCount) {
      this.expireDateCount = expireDateCount;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setRequestPackagesCount(final Integer requestPackagesCount) {
      this.requestPackagesCount = requestPackagesCount;
   }

   @Generated
   public void setEmail(final String email) {
      this.email = email;
   }

   @Generated
   public void setMobile(final String mobile) {
      this.mobile = mobile;
   }

   @Generated
   public void setGender(final String gender) {
      this.gender = gender;
   }

   @Generated
   public void setBirthday(final Date birthday) {
      this.birthday = birthday;
   }

   @Generated
   public void setInterests(final String interests) {
      this.interests = interests;
   }

   @Generated
   public void setAvatar(final String avatar) {
      this.avatar = avatar;
   }

   @Generated
   public void setRegisterChannelName(final String registerChannelName) {
      this.registerChannelName = registerChannelName;
   }

   @Generated
   public void setInviterName(final String inviterName) {
      this.inviterName = inviterName;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setHostLineTypeName(final String hostLineTypeName) {
      this.hostLineTypeName = hostLineTypeName;
   }

   @Generated
   public void setIsDistributor(final Integer isDistributor) {
      this.isDistributor = isDistributor;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setRoseBinding(final RoseBindingResponse roseBinding) {
      this.roseBinding = roseBinding;
   }

   @Generated
   public void setTelegramBinding(final UserOauthBindingResponse telegramBinding) {
      this.telegramBinding = telegramBinding;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$isAdmin = this.getIsAdmin();
            Object other$isAdmin = other.getIsAdmin();
            if (this$isAdmin == null ? other$isAdmin == null : this$isAdmin.equals(other$isAdmin)) {
               Object this$isPrimaryAdmin = this.getIsPrimaryAdmin();
               Object other$isPrimaryAdmin = other.getIsPrimaryAdmin();
               if (this$isPrimaryAdmin == null ? other$isPrimaryAdmin == null : this$isPrimaryAdmin.equals(other$isPrimaryAdmin)) {
                  Object this$userStatus = this.getUserStatus();
                  Object other$userStatus = other.getUserStatus();
                  if (this$userStatus == null ? other$userStatus == null : this$userStatus.equals(other$userStatus)) {
                     Object this$updateUserId = this.getUpdateUserId();
                     Object other$updateUserId = other.getUpdateUserId();
                     if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                        Object this$createUserId = this.getCreateUserId();
                        Object other$createUserId = other.getCreateUserId();
                        if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                           Object this$delFlag = this.getDelFlag();
                           Object other$delFlag = other.getDelFlag();
                           if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                              Object this$expireDateCount = this.getExpireDateCount();
                              Object other$expireDateCount = other.getExpireDateCount();
                              if (this$expireDateCount == null ? other$expireDateCount == null : this$expireDateCount.equals(other$expireDateCount)) {
                                 Object this$requestPackagesCount = this.getRequestPackagesCount();
                                 Object other$requestPackagesCount = other.getRequestPackagesCount();
                                 if (this$requestPackagesCount == null
                                    ? other$requestPackagesCount == null
                                    : this$requestPackagesCount.equals(other$requestPackagesCount)) {
                                    Object this$registerChannel = this.getRegisterChannel();
                                    Object other$registerChannel = other.getRegisterChannel();
                                    if (this$registerChannel == null ? other$registerChannel == null : this$registerChannel.equals(other$registerChannel)) {
                                       Object this$embyInfoId = this.getEmbyInfoId();
                                       Object other$embyInfoId = other.getEmbyInfoId();
                                       if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                                          Object this$hostLineType = this.getHostLineType();
                                          Object other$hostLineType = other.getHostLineType();
                                          if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                                             Object this$isDistributor = this.getIsDistributor();
                                             Object other$isDistributor = other.getIsDistributor();
                                             if (this$isDistributor == null ? other$isDistributor == null : this$isDistributor.equals(other$isDistributor)) {
                                                Object this$embyUserId = this.getEmbyUserId();
                                                Object other$embyUserId = other.getEmbyUserId();
                                                if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                                                   Object this$embyUserName = this.getEmbyUserName();
                                                   Object other$embyUserName = other.getEmbyUserName();
                                                   if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                                      Object this$userStatusName = this.getUserStatusName();
                                                      Object other$userStatusName = other.getUserStatusName();
                                                      if (this$userStatusName == null
                                                         ? other$userStatusName == null
                                                         : this$userStatusName.equals(other$userStatusName)) {
                                                         Object this$expirationDate = this.getExpirationDate();
                                                         Object other$expirationDate = other.getExpirationDate();
                                                         if (this$expirationDate == null
                                                            ? other$expirationDate == null
                                                            : this$expirationDate.equals(other$expirationDate)) {
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
                                                                        Object this$cardSecurityManagementList = this.getCardSecurityManagementList();
                                                                        Object other$cardSecurityManagementList = other.getCardSecurityManagementList();
                                                                        if (this$cardSecurityManagementList == null
                                                                           ? other$cardSecurityManagementList == null
                                                                           : this$cardSecurityManagementList.equals(other$cardSecurityManagementList)) {
                                                                           Object this$remarks = this.getRemarks();
                                                                           Object other$remarks = other.getRemarks();
                                                                           if (this$remarks == null
                                                                              ? other$remarks == null
                                                                              : this$remarks.equals(other$remarks)) {
                                                                              Object this$email = this.getEmail();
                                                                              Object other$email = other.getEmail();
                                                                              if (this$email == null ? other$email == null : this$email.equals(other$email)) {
                                                                                 Object this$mobile = this.getMobile();
                                                                                 Object other$mobile = other.getMobile();
                                                                                 if (this$mobile == null
                                                                                    ? other$mobile == null
                                                                                    : this$mobile.equals(other$mobile)) {
                                                                                    Object this$gender = this.getGender();
                                                                                    Object other$gender = other.getGender();
                                                                                    if (this$gender == null
                                                                                       ? other$gender == null
                                                                                       : this$gender.equals(other$gender)) {
                                                                                       Object this$birthday = this.getBirthday();
                                                                                       Object other$birthday = other.getBirthday();
                                                                                       if (this$birthday == null
                                                                                          ? other$birthday == null
                                                                                          : this$birthday.equals(other$birthday)) {
                                                                                          Object this$interests = this.getInterests();
                                                                                          Object other$interests = other.getInterests();
                                                                                          if (this$interests == null
                                                                                             ? other$interests == null
                                                                                             : this$interests.equals(other$interests)) {
                                                                                             Object this$avatar = this.getAvatar();
                                                                                             Object other$avatar = other.getAvatar();
                                                                                             if (this$avatar == null
                                                                                                ? other$avatar == null
                                                                                                : this$avatar.equals(other$avatar)) {
                                                                                                Object this$registerChannelName = this.getRegisterChannelName();
                                                                                                Object other$registerChannelName = other.getRegisterChannelName();
                                                                                                if (this$registerChannelName == null
                                                                                                   ? other$registerChannelName == null
                                                                                                   : this$registerChannelName.equals(other$registerChannelName)
                                                                                                   )
                                                                                                 {
                                                                                                   Object this$hostLineTypeName = this.getHostLineTypeName();
                                                                                                   Object other$hostLineTypeName = other.getHostLineTypeName();
                                                                                                   if (this$hostLineTypeName == null
                                                                                                      ? other$hostLineTypeName == null
                                                                                                      : this$hostLineTypeName.equals(other$hostLineTypeName)) {
                                                                                                      Object this$serverName = this.getServerName();
                                                                                                      Object other$serverName = other.getServerName();
                                                                                                      if (this$serverName == null
                                                                                                         ? other$serverName == null
                                                                                                         : this$serverName.equals(other$serverName)) {
                                                                                                         Object this$roseBinding = this.getRoseBinding();
                                                                                                         Object other$roseBinding = other.getRoseBinding();
                                                                                                         if (this$roseBinding == null
                                                                                                            ? other$roseBinding == null
                                                                                                            : this$roseBinding.equals(other$roseBinding)) {
                                                                                                            Object this$telegramBinding = this.getTelegramBinding();
                                                                                                            Object other$telegramBinding = other.getTelegramBinding();
                                                                                                            return this$telegramBinding == null
                                                                                                               ? other$telegramBinding == null
                                                                                                               : this$telegramBinding.equals(
                                                                                                                  other$telegramBinding
                                                                                                               );
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
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyUserResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $isAdmin = this.getIsAdmin();
      result = result * 59 + ($isAdmin == null ? 43 : $isAdmin.hashCode());
      Object $isPrimaryAdmin = this.getIsPrimaryAdmin();
      result = result * 59 + ($isPrimaryAdmin == null ? 43 : $isPrimaryAdmin.hashCode());
      Object $userStatus = this.getUserStatus();
      result = result * 59 + ($userStatus == null ? 43 : $userStatus.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $expireDateCount = this.getExpireDateCount();
      result = result * 59 + ($expireDateCount == null ? 43 : $expireDateCount.hashCode());
      Object $requestPackagesCount = this.getRequestPackagesCount();
      result = result * 59 + ($requestPackagesCount == null ? 43 : $requestPackagesCount.hashCode());
      Object $registerChannel = this.getRegisterChannel();
      result = result * 59 + ($registerChannel == null ? 43 : $registerChannel.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $isDistributor = this.getIsDistributor();
      result = result * 59 + ($isDistributor == null ? 43 : $isDistributor.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $userStatusName = this.getUserStatusName();
      result = result * 59 + ($userStatusName == null ? 43 : $userStatusName.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      result = result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
      Object $cardSecurityManagementList = this.getCardSecurityManagementList();
      result = result * 59 + ($cardSecurityManagementList == null ? 43 : $cardSecurityManagementList.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $email = this.getEmail();
      result = result * 59 + ($email == null ? 43 : $email.hashCode());
      Object $mobile = this.getMobile();
      result = result * 59 + ($mobile == null ? 43 : $mobile.hashCode());
      Object $gender = this.getGender();
      result = result * 59 + ($gender == null ? 43 : $gender.hashCode());
      Object $birthday = this.getBirthday();
      result = result * 59 + ($birthday == null ? 43 : $birthday.hashCode());
      Object $interests = this.getInterests();
      result = result * 59 + ($interests == null ? 43 : $interests.hashCode());
      Object $avatar = this.getAvatar();
      result = result * 59 + ($avatar == null ? 43 : $avatar.hashCode());
      Object $registerChannelName = this.getRegisterChannelName();
      result = result * 59 + ($registerChannelName == null ? 43 : $registerChannelName.hashCode());
      Object $hostLineTypeName = this.getHostLineTypeName();
      result = result * 59 + ($hostLineTypeName == null ? 43 : $hostLineTypeName.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $roseBinding = this.getRoseBinding();
      result = result * 59 + ($roseBinding == null ? 43 : $roseBinding.hashCode());
      Object $telegramBinding = this.getTelegramBinding();
      return result * 59 + ($telegramBinding == null ? 43 : $telegramBinding.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserResponse(id="
         + this.getId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", isAdmin="
         + this.getIsAdmin()
         + ", isPrimaryAdmin="
         + this.getIsPrimaryAdmin()
         + ", userStatus="
         + this.getUserStatus()
         + ", userStatusName="
         + this.getUserStatusName()
         + ", expirationDate="
         + this.getExpirationDate()
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
         + ", cardSecurityManagementList="
         + this.getCardSecurityManagementList()
         + ", expireDateCount="
         + this.getExpireDateCount()
         + ", remarks="
         + this.getRemarks()
         + ", requestPackagesCount="
         + this.getRequestPackagesCount()
         + ", email="
         + this.getEmail()
         + ", mobile="
         + this.getMobile()
         + ", gender="
         + this.getGender()
         + ", birthday="
         + this.getBirthday()
         + ", interests="
         + this.getInterests()
         + ", avatar="
         + this.getAvatar()
         + ", registerChannel="
         + this.getRegisterChannel()
         + ", registerChannelName="
         + this.getRegisterChannelName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", hostLineTypeName="
         + this.getHostLineTypeName()
         + ", isDistributor="
         + this.getIsDistributor()
         + ", serverName="
         + this.getServerName()
         + ", roseBinding="
         + this.getRoseBinding()
         + ", telegramBinding="
         + this.getTelegramBinding()
         + ")";
   }
}
