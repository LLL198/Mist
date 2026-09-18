package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotPrizeConfigRequest implements Serializable {
   private String prizeName;
   private Long levelId;
   private Integer enabled;

   @Generated
   public String getPrizeName() {
      return this.prizeName;
   }

   @Generated
   public Long getLevelId() {
      return this.levelId;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setPrizeName(final String prizeName) {
      this.prizeName = prizeName;
   }

   @Generated
   public void setLevelId(final Long levelId) {
      this.levelId = levelId;
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
      } else if (!(o instanceof PointsBotPrizeConfigRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$levelId = this.getLevelId();
         Object other$levelId = other.getLevelId();
         if (this$levelId == null ? other$levelId == null : this$levelId.equals(other$levelId)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$prizeName = this.getPrizeName();
               Object other$prizeName = other.getPrizeName();
               return this$prizeName == null ? other$prizeName == null : this$prizeName.equals(other$prizeName);
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
      return other instanceof PointsBotPrizeConfigRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $levelId = this.getLevelId();
      result = result * 59 + ($levelId == null ? 43 : $levelId.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $prizeName = this.getPrizeName();
      return result * 59 + ($prizeName == null ? 43 : $prizeName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPrizeConfigRequest(prizeName=" + this.getPrizeName() + ", levelId=" + this.getLevelId() + ", enabled=" + this.getEnabled() + ")";
   }
}
