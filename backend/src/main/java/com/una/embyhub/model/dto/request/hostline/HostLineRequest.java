package com.una.embyhub.model.dto.request.hostline;

import java.io.Serializable;
import lombok.Generated;

public class HostLineRequest implements Serializable {
   private Long embyInfoId;
   private String lineName;
   private Integer lineType;
   private Integer isDisplay;
   private Integer enabled;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getLineName() {
      return this.lineName;
   }

   @Generated
   public Integer getLineType() {
      return this.lineType;
   }

   @Generated
   public Integer getIsDisplay() {
      return this.isDisplay;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setLineName(final String lineName) {
      this.lineName = lineName;
   }

   @Generated
   public void setLineType(final Integer lineType) {
      this.lineType = lineType;
   }

   @Generated
   public void setIsDisplay(final Integer isDisplay) {
      this.isDisplay = isDisplay;
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
      } else if (!(o instanceof HostLineRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$lineType = this.getLineType();
            Object other$lineType = other.getLineType();
            if (this$lineType == null ? other$lineType == null : this$lineType.equals(other$lineType)) {
               Object this$isDisplay = this.getIsDisplay();
               Object other$isDisplay = other.getIsDisplay();
               if (this$isDisplay == null ? other$isDisplay == null : this$isDisplay.equals(other$isDisplay)) {
                  Object this$enabled = this.getEnabled();
                  Object other$enabled = other.getEnabled();
                  if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                     Object this$lineName = this.getLineName();
                     Object other$lineName = other.getLineName();
                     return this$lineName == null ? other$lineName == null : this$lineName.equals(other$lineName);
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
      return other instanceof HostLineRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $lineType = this.getLineType();
      result = result * 59 + ($lineType == null ? 43 : $lineType.hashCode());
      Object $isDisplay = this.getIsDisplay();
      result = result * 59 + ($isDisplay == null ? 43 : $isDisplay.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $lineName = this.getLineName();
      return result * 59 + ($lineName == null ? 43 : $lineName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "HostLineRequest(embyInfoId="
         + this.getEmbyInfoId()
         + ", lineName="
         + this.getLineName()
         + ", lineType="
         + this.getLineType()
         + ", isDisplay="
         + this.getIsDisplay()
         + ", enabled="
         + this.getEnabled()
         + ")";
   }
}
