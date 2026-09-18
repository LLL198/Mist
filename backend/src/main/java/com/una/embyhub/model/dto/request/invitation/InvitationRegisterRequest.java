package com.una.embyhub.model.dto.request.invitation;

import com.una.embyhub.model.dto.request.embyuser.ProtectedUserMutationRequest;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;
import org.springframework.util.StringUtils;

public class InvitationRegisterRequest implements ProtectedUserMutationRequest, Serializable {
   private String invitationCode;
   private String aff;
   private String embyUserName;
   private String embyUserPassword;
   private String remarks;
   private String email;
   private String mobile;
   private String gender;
   private Date birthday;
   private String interests;

   public String resolveInvitationCode() {
      return StringUtils.hasText(this.invitationCode) ? this.invitationCode : this.aff;
   }

   @Generated
   public String getInvitationCode() {
      return this.invitationCode;
   }

   @Generated
   public String getAff() {
      return this.aff;
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
   public String getRemarks() {
      return this.remarks;
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
   public void setInvitationCode(final String invitationCode) {
      this.invitationCode = invitationCode;
   }

   @Generated
   public void setAff(final String aff) {
      this.aff = aff;
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
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
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
      } else if (!(o instanceof InvitationRegisterRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$invitationCode = this.getInvitationCode();
         Object other$invitationCode = other.getInvitationCode();
         if (this$invitationCode == null ? other$invitationCode == null : this$invitationCode.equals(other$invitationCode)) {
            Object this$aff = this.getAff();
            Object other$aff = other.getAff();
            if (this$aff == null ? other$aff == null : this$aff.equals(other$aff)) {
               Object this$embyUserName = this.getEmbyUserName();
               Object other$embyUserName = other.getEmbyUserName();
               if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                  Object this$embyUserPassword = this.getEmbyUserPassword();
                  Object other$embyUserPassword = other.getEmbyUserPassword();
                  if (this$embyUserPassword == null ? other$embyUserPassword == null : this$embyUserPassword.equals(other$embyUserPassword)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof InvitationRegisterRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $invitationCode = this.getInvitationCode();
      result = result * 59 + ($invitationCode == null ? 43 : $invitationCode.hashCode());
      Object $aff = this.getAff();
      result = result * 59 + ($aff == null ? 43 : $aff.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $embyUserPassword = this.getEmbyUserPassword();
      result = result * 59 + ($embyUserPassword == null ? 43 : $embyUserPassword.hashCode());
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
      return "InvitationRegisterRequest(invitationCode="
         + this.getInvitationCode()
         + ", aff="
         + this.getAff()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyUserPassword="
         + this.getEmbyUserPassword()
         + ", remarks="
         + this.getRemarks()
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
