package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotRedeemConfigRequest implements Serializable {
   private String configName;
   private String redeemType;
   private Long embyInfoId;
   private Integer enabled;

   @Generated
   public String getConfigName() {
      return this.configName;
   }

   @Generated
   public String getRedeemType() {
      return this.redeemType;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setConfigName(final String configName) {
      this.configName = configName;
   }

   @Generated
   public void setRedeemType(final String redeemType) {
      this.redeemType = redeemType;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotRedeemConfigRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$configName = this.getConfigName();
               Object other$configName = other.getConfigName();
               if (this$configName == null ? other$configName == null : this$configName.equals(other$configName)) {
                  Object this$redeemType = this.getRedeemType();
                  Object other$redeemType = other.getRedeemType();
                  return this$redeemType == null ? other$redeemType == null : this$redeemType.equals(other$redeemType);
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
      return other instanceof PointsBotRedeemConfigRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $configName = this.getConfigName();
      result = result * 59 + ($configName == null ? 43 : $configName.hashCode());
      Object $redeemType = this.getRedeemType();
      return result * 59 + ($redeemType == null ? 43 : $redeemType.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotRedeemConfigRequest(configName="
         + this.getConfigName()
         + ", redeemType="
         + this.getRedeemType()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", enabled="
         + this.getEnabled()
         + ")";
   }
}
