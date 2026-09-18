package com.una.embyhub.model.dto.request.embyiplocations;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import lombok.Generated;

public class EmbyIpLocationsRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String ipAddress;
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   private String embyUserName;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long embyInfoId;

   @Generated
   public String getIpAddress() {
      return this.ipAddress;
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
   public void setIpAddress(final String ipAddress) {
      this.ipAddress = ipAddress;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyIpLocationsRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$ipAddress = this.getIpAddress();
            Object other$ipAddress = other.getIpAddress();
            if (this$ipAddress == null ? other$ipAddress == null : this$ipAddress.equals(other$ipAddress)) {
               Object this$embyUserName = this.getEmbyUserName();
               Object other$embyUserName = other.getEmbyUserName();
               return this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName);
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
      return other instanceof EmbyIpLocationsRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $ipAddress = this.getIpAddress();
      result = result * 59 + ($ipAddress == null ? 43 : $ipAddress.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyIpLocationsRequest(ipAddress="
         + this.getIpAddress()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }
}
