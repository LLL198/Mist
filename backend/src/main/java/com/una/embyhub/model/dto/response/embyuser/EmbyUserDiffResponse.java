package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyUserDiffResponse implements Serializable {
   private Long embyInfoId;
   private String serverName;
   private String userName;
   private String systemStatus;
   private String embyStatus;
   private String diffType;
   private Long systemUserId;
   private String systemEmbyUserId;
   private String embyUserId;
   private Date createDatetime;
   private Date updateDatetime;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public String getSystemStatus() {
      return this.systemStatus;
   }

   @Generated
   public String getEmbyStatus() {
      return this.embyStatus;
   }

   @Generated
   public String getDiffType() {
      return this.diffType;
   }

   @Generated
   public Long getSystemUserId() {
      return this.systemUserId;
   }

   @Generated
   public String getSystemEmbyUserId() {
      return this.systemEmbyUserId;
   }

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
   }

   @Generated
   public void setSystemStatus(final String systemStatus) {
      this.systemStatus = systemStatus;
   }

   @Generated
   public void setEmbyStatus(final String embyStatus) {
      this.embyStatus = embyStatus;
   }

   @Generated
   public void setDiffType(final String diffType) {
      this.diffType = diffType;
   }

   @Generated
   public void setSystemUserId(final Long systemUserId) {
      this.systemUserId = systemUserId;
   }

   @Generated
   public void setSystemEmbyUserId(final String systemEmbyUserId) {
      this.systemEmbyUserId = systemEmbyUserId;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserDiffResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$systemUserId = this.getSystemUserId();
            Object other$systemUserId = other.getSystemUserId();
            if (this$systemUserId == null ? other$systemUserId == null : this$systemUserId.equals(other$systemUserId)) {
               Object this$serverName = this.getServerName();
               Object other$serverName = other.getServerName();
               if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                  Object this$userName = this.getUserName();
                  Object other$userName = other.getUserName();
                  if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                     Object this$systemStatus = this.getSystemStatus();
                     Object other$systemStatus = other.getSystemStatus();
                     if (this$systemStatus == null ? other$systemStatus == null : this$systemStatus.equals(other$systemStatus)) {
                        Object this$embyStatus = this.getEmbyStatus();
                        Object other$embyStatus = other.getEmbyStatus();
                        if (this$embyStatus == null ? other$embyStatus == null : this$embyStatus.equals(other$embyStatus)) {
                           Object this$diffType = this.getDiffType();
                           Object other$diffType = other.getDiffType();
                           if (this$diffType == null ? other$diffType == null : this$diffType.equals(other$diffType)) {
                              Object this$systemEmbyUserId = this.getSystemEmbyUserId();
                              Object other$systemEmbyUserId = other.getSystemEmbyUserId();
                              if (this$systemEmbyUserId == null ? other$systemEmbyUserId == null : this$systemEmbyUserId.equals(other$systemEmbyUserId)) {
                                 Object this$embyUserId = this.getEmbyUserId();
                                 Object other$embyUserId = other.getEmbyUserId();
                                 if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                                    Object this$createDatetime = this.getCreateDatetime();
                                    Object other$createDatetime = other.getCreateDatetime();
                                    if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                       Object this$updateDatetime = this.getUpdateDatetime();
                                       Object other$updateDatetime = other.getUpdateDatetime();
                                       return this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime);
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
      return other instanceof EmbyUserDiffResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $systemUserId = this.getSystemUserId();
      result = result * 59 + ($systemUserId == null ? 43 : $systemUserId.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $systemStatus = this.getSystemStatus();
      result = result * 59 + ($systemStatus == null ? 43 : $systemStatus.hashCode());
      Object $embyStatus = this.getEmbyStatus();
      result = result * 59 + ($embyStatus == null ? 43 : $embyStatus.hashCode());
      Object $diffType = this.getDiffType();
      result = result * 59 + ($diffType == null ? 43 : $diffType.hashCode());
      Object $systemEmbyUserId = this.getSystemEmbyUserId();
      result = result * 59 + ($systemEmbyUserId == null ? 43 : $systemEmbyUserId.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserDiffResponse(embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", userName="
         + this.getUserName()
         + ", systemStatus="
         + this.getSystemStatus()
         + ", embyStatus="
         + this.getEmbyStatus()
         + ", diffType="
         + this.getDiffType()
         + ", systemUserId="
         + this.getSystemUserId()
         + ", systemEmbyUserId="
         + this.getSystemEmbyUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
