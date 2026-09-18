package com.una.embyhub.model.dto.request.embyuser;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyUserProfileUpdate implements ProtectedUserMutationRequest, Serializable {
   private String email;
   private String mobile;
   private String gender;
   private Date birthday;
   private String interests;
   private String remarks;

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
   public String getRemarks() {
      return this.remarks;
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
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserProfileUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
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
                     if (this$interests == null ? other$interests == null : this$interests.equals(other$interests)) {
                        Object this$remarks = this.getRemarks();
                        Object other$remarks = other.getRemarks();
                        return this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks);
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
      return other instanceof EmbyUserProfileUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
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
      Object $remarks = this.getRemarks();
      return result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserProfileUpdate(email="
         + this.getEmail()
         + ", mobile="
         + this.getMobile()
         + ", gender="
         + this.getGender()
         + ", birthday="
         + this.getBirthday()
         + ", interests="
         + this.getInterests()
         + ", remarks="
         + this.getRemarks()
         + ")";
   }
}
