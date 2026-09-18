package com.una.embyhub.model.dto.request.embyinfo;

import java.io.Serializable;
import lombok.Generated;

public class EmbyInfoRequest implements Serializable {
   private Integer status;
   private Integer enabled;
   private Integer spread;
   private String serverName;

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public Integer getSpread() {
      return this.spread;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setSpread(final Integer spread) {
      this.spread = spread;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyInfoRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$status = this.getStatus();
         Object other$status = other.getStatus();
         if (this$status == null ? other$status == null : this$status.equals(other$status)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$spread = this.getSpread();
               Object other$spread = other.getSpread();
               if (this$spread == null ? other$spread == null : this$spread.equals(other$spread)) {
                  Object this$serverName = this.getServerName();
                  Object other$serverName = other.getServerName();
                  return this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName);
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
      return other instanceof EmbyInfoRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $spread = this.getSpread();
      result = result * 59 + ($spread == null ? 43 : $spread.hashCode());
      Object $serverName = this.getServerName();
      return result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyInfoRequest(status="
         + this.getStatus()
         + ", enabled="
         + this.getEnabled()
         + ", spread="
         + this.getSpread()
         + ", serverName="
         + this.getServerName()
         + ")";
   }
}
