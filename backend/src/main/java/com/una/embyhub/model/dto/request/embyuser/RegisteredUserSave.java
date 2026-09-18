package com.una.embyhub.model.dto.request.embyuser;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class RegisteredUserSave implements ProtectedUserMutationRequest, Serializable {
   private String embyUserId;
   private String embyUserName;
   private String embyUserPassword;
   private Integer userStatus;
   private Date expirationDate;
   private String remarks;
   private Integer requestPackagesCount;
   private Integer hostLineType;
   private String email;
   private String mobile;
   private String gender;
   private Date birthday;
   private String interests;

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
   public Integer getUserStatus() {
      return this.userStatus;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
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
   public Integer getHostLineType() {
      return this.hostLineType;
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
   public void setUserStatus(final Integer userStatus) {
      this.userStatus = userStatus;
   }

   @Generated
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
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
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RegisteredUserSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userStatus = this.getUserStatus();
         Object other$userStatus = other.getUserStatus();
         if (this$userStatus == null ? other$userStatus == null : this$userStatus.equals(other$userStatus)) {
            Object this$requestPackagesCount = this.getRequestPackagesCount();
            Object other$requestPackagesCount = other.getRequestPackagesCount();
            if (this$requestPackagesCount == null ? other$requestPackagesCount == null : this$requestPackagesCount.equals(other$requestPackagesCount)) {
               Object this$hostLineType = this.getHostLineType();
               Object other$hostLineType = other.getHostLineType();
               if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                  Object this$embyUserId = this.getEmbyUserId();
                  Object other$embyUserId = other.getEmbyUserId();
                  if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                     Object this$embyUserName = this.getEmbyUserName();
                     Object other$embyUserName = other.getEmbyUserName();
                     if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                        Object this$embyUserPassword = this.getEmbyUserPassword();
                        Object other$embyUserPassword = other.getEmbyUserPassword();
                        if (this$embyUserPassword == null ? other$embyUserPassword == null : this$embyUserPassword.equals(other$embyUserPassword)) {
                           Object this$expirationDate = this.getExpirationDate();
                           Object other$expirationDate = other.getExpirationDate();
                           if (this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate)) {
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
                                          if (this$birthday == null ? other$birthday == null : this$birthday.equals(other$birthday)) {
                                             Object this$interests = this.getInterests();
                                             Object other$interests = other.getInterests();
                                             return this$interests == null ? other$interests == null : this$interests.equals(other$interests);
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
      return other instanceof RegisteredUserSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userStatus = this.getUserStatus();
      result = result * 59 + ($userStatus == null ? 43 : $userStatus.hashCode());
      Object $requestPackagesCount = this.getRequestPackagesCount();
      result = result * 59 + ($requestPackagesCount == null ? 43 : $requestPackagesCount.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $embyUserPassword = this.getEmbyUserPassword();
      result = result * 59 + ($embyUserPassword == null ? 43 : $embyUserPassword.hashCode());
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
      return result * 59 + ($interests == null ? 43 : $interests.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RegisteredUserSave(embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyUserPassword="
         + this.getEmbyUserPassword()
         + ", userStatus="
         + this.getUserStatus()
         + ", expirationDate="
         + this.getExpirationDate()
         + ", remarks="
         + this.getRemarks()
         + ", requestPackagesCount="
         + this.getRequestPackagesCount()
         + ", hostLineType="
         + this.getHostLineType()
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
         + ")";
   }
}
