package com.una.embyhub.model.dto.request.embydevice;

import lombok.Generated;

public class EmbyDeviceRequest {
   private String deviceName;
   private String appName;
   private Integer blocked;
   private Long embyInfoId;

   @Generated
   public String getDeviceName() {
      return this.deviceName;
   }

   @Generated
   public String getAppName() {
      return this.appName;
   }

   @Generated
   public Integer getBlocked() {
      return this.blocked;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
   }

   @Generated
   public void setAppName(final String appName) {
      this.appName = appName;
   }

   @Generated
   public void setBlocked(final Integer blocked) {
      this.blocked = blocked;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyDeviceRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$blocked = this.getBlocked();
         Object other$blocked = other.getBlocked();
         if (this$blocked == null ? other$blocked == null : this$blocked.equals(other$blocked)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$deviceName = this.getDeviceName();
               Object other$deviceName = other.getDeviceName();
               if (this$deviceName == null ? other$deviceName == null : this$deviceName.equals(other$deviceName)) {
                  Object this$appName = this.getAppName();
                  Object other$appName = other.getAppName();
                  return this$appName == null ? other$appName == null : this$appName.equals(other$appName);
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
      return other instanceof EmbyDeviceRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $blocked = this.getBlocked();
      result = result * 59 + ($blocked == null ? 43 : $blocked.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $deviceName = this.getDeviceName();
      result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
      Object $appName = this.getAppName();
      return result * 59 + ($appName == null ? 43 : $appName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyDeviceRequest(deviceName="
         + this.getDeviceName()
         + ", appName="
         + this.getAppName()
         + ", blocked="
         + this.getBlocked()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }
}
