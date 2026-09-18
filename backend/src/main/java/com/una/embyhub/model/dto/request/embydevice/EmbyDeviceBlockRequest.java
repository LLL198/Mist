package com.una.embyhub.model.dto.request.embydevice;

import jakarta.validation.constraints.NotBlank;
import lombok.Generated;

public class EmbyDeviceBlockRequest {
   @NotBlank(
      message = "设备ID不能为空"
   )
   private String deviceId;
   private Long embyInfoId;

   @Generated
   public String getDeviceId() {
      return this.deviceId;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setDeviceId(final String deviceId) {
      this.deviceId = deviceId;
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
      } else if (!(o instanceof EmbyDeviceBlockRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$deviceId = this.getDeviceId();
            Object other$deviceId = other.getDeviceId();
            return this$deviceId == null ? other$deviceId == null : this$deviceId.equals(other$deviceId);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyDeviceBlockRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $deviceId = this.getDeviceId();
      return result * 59 + ($deviceId == null ? 43 : $deviceId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyDeviceBlockRequest(deviceId=" + this.getDeviceId() + ", embyInfoId=" + this.getEmbyInfoId() + ")";
   }
}
