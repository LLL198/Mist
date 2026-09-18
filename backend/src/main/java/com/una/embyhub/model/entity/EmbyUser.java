package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("emby_user")
public class EmbyUser extends BaseEntity implements Serializable {
   public static final String COL_CARD_PASSWORD = "card_password";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_user_id")
   private String embyUserId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("emby_user_password")
   private String embyUserPassword;
   @TableField("is_admin")
   private Integer isAdmin;
   @TableField(
      value = "is_primary_admin",
      updateStrategy = FieldStrategy.NEVER
   )
   private Integer isPrimaryAdmin;
   @TableField("user_status")
   private Integer userStatus;
   @TableField("disable_reason")
   private String disableReason;
   @TableField("disabled_datetime")
   private Date disabledDatetime;
   @TableField("expiration_date")
   private Date expirationDate;
   @TableField("expire_date_count")
   private Long expireDateCount;
   @TableField("remarks")
   private String remarks;
   @TableField("request_packages_count")
   private Integer requestPackagesCount;
   @TableField("email")
   private String email;
   @TableField("mobile")
   private String mobile;
   @TableField("gender")
   private String gender;
   @TableField("birthday")
   private Date birthday;
   @TableField("interests")
   private String interests;
   @TableField("avatar")
   private String avatar;
   @TableField("invitation_code")
   private String invitationCode;
   @TableField("register_channel")
   private Integer registerChannel;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @JsonIgnore
   @TableField(
      value = "identity_group_id",
      fill = FieldFill.INSERT
   )
   private Long identityGroupId;
   @TableField("host_line_type")
   private Integer hostLineType;
   @TableField("is_distributor")
   private Integer isDistributor;
   public static final String COL_ID = "id";
   public static final String COL_EMBY_USER_ID = "emby_user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_EMBY_USER_PASSWORD = "emby_user_password";
   public static final String COL_IS_ADMIN = "is_admin";
   public static final String COL_IS_PRIMARY_ADMIN = "is_primary_admin";
   public static final String COL_USER_STATUS = "user_status";
   public static final String COL_DISABLE_REASON = "disable_reason";
   public static final String COL_DISABLED_DATETIME = "disabled_datetime";
   public static final String COL_EXPIRATION_DATE = "expiration_date";
   public static final String COL_EXPIRE_DATE_COUNT = "expire_date_count";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";
   public static final String COL_REMARKS = "remarks";
   public static final String COL_REQUEST_PACKAGES_COUNT = "request_packages_count";
   public static final String COL_EMAIL = "email";
   public static final String COL_MOBILE = "mobile";
   public static final String COL_GENDER = "gender";
   public static final String COL_BIRTHDAY = "birthday";
   public static final String COL_INTERESTS = "interests";
   public static final String COL_AVATAR = "avatar";
   public static final String COL_INVITATION_CODE = "invitation_code";
   public static final String COL_REGISTER_CHANNEL = "register_channel";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_IDENTITY_GROUP_ID = "identity_group_id";
   public static final String COL_HOST_LINE_TYPE = "host_line_type";
   public static final String COL_IS_DISTRIBUTOR = "is_distributor";

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
   public String getEmbyUserPassword() {
      return this.embyUserPassword;
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
   public String getDisableReason() {
      return this.disableReason;
   }

   @Generated
   public Date getDisabledDatetime() {
      return this.disabledDatetime;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
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
   public String getInvitationCode() {
      return this.invitationCode;
   }

   @Generated
   public Integer getRegisterChannel() {
      return this.registerChannel;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Long getIdentityGroupId() {
      return this.identityGroupId;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
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
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setEmbyUserPassword(final String embyUserPassword) {
      this.embyUserPassword = embyUserPassword;
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
   public void setUserStatus(final Integer userStatus) {
      this.userStatus = userStatus;
   }

   @Generated
   public void setDisableReason(final String disableReason) {
      this.disableReason = disableReason;
   }

   @Generated
   public void setDisabledDatetime(final Date disabledDatetime) {
      this.disabledDatetime = disabledDatetime;
   }

   @Generated
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
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
   public void setInvitationCode(final String invitationCode) {
      this.invitationCode = invitationCode;
   }

   @Generated
   public void setRegisterChannel(final Integer registerChannel) {
      this.registerChannel = registerChannel;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @JsonIgnore
   @Generated
   public void setIdentityGroupId(final Long identityGroupId) {
      this.identityGroupId = identityGroupId;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setIsDistributor(final Integer isDistributor) {
      this.isDistributor = isDistributor;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUser(id="
         + this.getId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyUserPassword="
         + this.getEmbyUserPassword()
         + ", isAdmin="
         + this.getIsAdmin()
         + ", isPrimaryAdmin="
         + this.getIsPrimaryAdmin()
         + ", userStatus="
         + this.getUserStatus()
         + ", disableReason="
         + this.getDisableReason()
         + ", disabledDatetime="
         + this.getDisabledDatetime()
         + ", expirationDate="
         + this.getExpirationDate()
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
         + ", invitationCode="
         + this.getInvitationCode()
         + ", registerChannel="
         + this.getRegisterChannel()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", identityGroupId="
         + this.getIdentityGroupId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", isDistributor="
         + this.getIsDistributor()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUser other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
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
                                 Object this$identityGroupId = this.getIdentityGroupId();
                                 Object other$identityGroupId = other.getIdentityGroupId();
                                 if (this$identityGroupId == null ? other$identityGroupId == null : this$identityGroupId.equals(other$identityGroupId)) {
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
                                                Object this$embyUserPassword = this.getEmbyUserPassword();
                                                Object other$embyUserPassword = other.getEmbyUserPassword();
                                                if (this$embyUserPassword == null
                                                   ? other$embyUserPassword == null
                                                   : this$embyUserPassword.equals(other$embyUserPassword)) {
                                                   Object this$disableReason = this.getDisableReason();
                                                   Object other$disableReason = other.getDisableReason();
                                                   if (this$disableReason == null
                                                      ? other$disableReason == null
                                                      : this$disableReason.equals(other$disableReason)) {
                                                      Object this$disabledDatetime = this.getDisabledDatetime();
                                                      Object other$disabledDatetime = other.getDisabledDatetime();
                                                      if (this$disabledDatetime == null
                                                         ? other$disabledDatetime == null
                                                         : this$disabledDatetime.equals(other$disabledDatetime)) {
                                                         Object this$expirationDate = this.getExpirationDate();
                                                         Object other$expirationDate = other.getExpirationDate();
                                                         if (this$expirationDate == null
                                                            ? other$expirationDate == null
                                                            : this$expirationDate.equals(other$expirationDate)) {
                                                            Object this$remarks = this.getRemarks();
                                                            Object other$remarks = other.getRemarks();
                                                            if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                                                               Object this$email = this.getEmail();
                                                               Object other$email = other.getEmail();
                                                               if (this$email == null ? other$email == null : this$email.equals(other$email)) {
                                                                  Object this$mobile = this.getMobile();
                                                                  Object other$mobile = other.getMobile();
                                                                  if (this$mobile == null ? other$mobile == null : this$mobile.equals(other$mobile)) {
                                                                     Object this$gender = this.getGender();
                                                                     Object other$gender = other.getGender();
                                                                     if (this$gender == null ? other$gender == null : this$gender.equals(other$gender)) {
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
                                                                              if (this$avatar == null ? other$avatar == null : this$avatar.equals(other$avatar)
                                                                                 )
                                                                               {
                                                                                 Object this$invitationCode = this.getInvitationCode();
                                                                                 Object other$invitationCode = other.getInvitationCode();
                                                                                 return this$invitationCode == null
                                                                                    ? other$invitationCode == null
                                                                                    : this$invitationCode.equals(other$invitationCode);
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
      return other instanceof EmbyUser;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $isAdmin = this.getIsAdmin();
      result = result * 59 + ($isAdmin == null ? 43 : $isAdmin.hashCode());
      Object $isPrimaryAdmin = this.getIsPrimaryAdmin();
      result = result * 59 + ($isPrimaryAdmin == null ? 43 : $isPrimaryAdmin.hashCode());
      Object $userStatus = this.getUserStatus();
      result = result * 59 + ($userStatus == null ? 43 : $userStatus.hashCode());
      Object $expireDateCount = this.getExpireDateCount();
      result = result * 59 + ($expireDateCount == null ? 43 : $expireDateCount.hashCode());
      Object $requestPackagesCount = this.getRequestPackagesCount();
      result = result * 59 + ($requestPackagesCount == null ? 43 : $requestPackagesCount.hashCode());
      Object $registerChannel = this.getRegisterChannel();
      result = result * 59 + ($registerChannel == null ? 43 : $registerChannel.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $identityGroupId = this.getIdentityGroupId();
      result = result * 59 + ($identityGroupId == null ? 43 : $identityGroupId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $isDistributor = this.getIsDistributor();
      result = result * 59 + ($isDistributor == null ? 43 : $isDistributor.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $embyUserPassword = this.getEmbyUserPassword();
      result = result * 59 + ($embyUserPassword == null ? 43 : $embyUserPassword.hashCode());
      Object $disableReason = this.getDisableReason();
      result = result * 59 + ($disableReason == null ? 43 : $disableReason.hashCode());
      Object $disabledDatetime = this.getDisabledDatetime();
      result = result * 59 + ($disabledDatetime == null ? 43 : $disabledDatetime.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
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
      Object $invitationCode = this.getInvitationCode();
      return result * 59 + ($invitationCode == null ? 43 : $invitationCode.hashCode());
   }
}
