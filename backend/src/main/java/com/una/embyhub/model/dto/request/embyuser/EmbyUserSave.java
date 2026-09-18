package com.una.embyhub.model.dto.request.embyuser;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import lombok.Generated;

public class EmbyUserSave implements ProtectedUserMutationRequest, Serializable {
   private Integer day;
   @NotEmpty(
      message = "备注不能为空"
   )
   private String remarks;
   private String embyUserName;
   private String embyUserPassword;
   private Long embyInfoId;
   private Integer hostLineType;
   private Integer registerChannel;
   private String registerChannelDetail;
   private Integer isAdmin;

   @Generated
   public Integer getDay() {
      return this.day;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public Integer getRegisterChannel() {
      return this.registerChannel;
   }

   @Generated
   public String getRegisterChannelDetail() {
      return this.registerChannelDetail;
   }

   @Generated
   public Integer getIsAdmin() {
      return this.isAdmin;
   }

   @Generated
   public void setDay(final Integer day) {
      this.day = day;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setRegisterChannel(final Integer registerChannel) {
      this.registerChannel = registerChannel;
   }

   @Generated
   public void setRegisterChannelDetail(final String registerChannelDetail) {
      this.registerChannelDetail = registerChannelDetail;
   }

   @Generated
   public void setIsAdmin(final Integer isAdmin) {
      this.isAdmin = isAdmin;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$day = this.getDay();
         Object other$day = other.getDay();
         if (this$day == null ? other$day == null : this$day.equals(other$day)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$hostLineType = this.getHostLineType();
               Object other$hostLineType = other.getHostLineType();
               if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                  Object this$registerChannel = this.getRegisterChannel();
                  Object other$registerChannel = other.getRegisterChannel();
                  if (this$registerChannel == null ? other$registerChannel == null : this$registerChannel.equals(other$registerChannel)) {
                     Object this$isAdmin = this.getIsAdmin();
                     Object other$isAdmin = other.getIsAdmin();
                     if (this$isAdmin == null ? other$isAdmin == null : this$isAdmin.equals(other$isAdmin)) {
                        Object this$remarks = this.getRemarks();
                        Object other$remarks = other.getRemarks();
                        if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                           Object this$embyUserName = this.getEmbyUserName();
                           Object other$embyUserName = other.getEmbyUserName();
                           if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                              Object this$embyUserPassword = this.getEmbyUserPassword();
                              Object other$embyUserPassword = other.getEmbyUserPassword();
                              if (this$embyUserPassword == null ? other$embyUserPassword == null : this$embyUserPassword.equals(other$embyUserPassword)) {
                                 Object this$registerChannelDetail = this.getRegisterChannelDetail();
                                 Object other$registerChannelDetail = other.getRegisterChannelDetail();
                                 return this$registerChannelDetail == null
                                    ? other$registerChannelDetail == null
                                    : this$registerChannelDetail.equals(other$registerChannelDetail);
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
      return other instanceof EmbyUserSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $day = this.getDay();
      result = result * 59 + ($day == null ? 43 : $day.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $registerChannel = this.getRegisterChannel();
      result = result * 59 + ($registerChannel == null ? 43 : $registerChannel.hashCode());
      Object $isAdmin = this.getIsAdmin();
      result = result * 59 + ($isAdmin == null ? 43 : $isAdmin.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $embyUserPassword = this.getEmbyUserPassword();
      result = result * 59 + ($embyUserPassword == null ? 43 : $embyUserPassword.hashCode());
      Object $registerChannelDetail = this.getRegisterChannelDetail();
      return result * 59 + ($registerChannelDetail == null ? 43 : $registerChannelDetail.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserSave(day="
         + this.getDay()
         + ", remarks="
         + this.getRemarks()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyUserPassword="
         + this.getEmbyUserPassword()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", registerChannel="
         + this.getRegisterChannel()
         + ", registerChannelDetail="
         + this.getRegisterChannelDetail()
         + ", isAdmin="
         + this.getIsAdmin()
         + ")";
   }
}
