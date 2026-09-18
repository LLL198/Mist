package com.una.embyhub.model.dto.request.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotLevelConfigRequest implements Serializable {
   private String levelName;
   private Integer enabled;

   @Generated
   public String getLevelName() {
      return this.levelName;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setLevelName(final String levelName) {
      this.levelName = levelName;
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
      } else if (!(o instanceof PointsBotLevelConfigRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$levelName = this.getLevelName();
            Object other$levelName = other.getLevelName();
            return this$levelName == null ? other$levelName == null : this$levelName.equals(other$levelName);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotLevelConfigRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $levelName = this.getLevelName();
      return result * 59 + ($levelName == null ? 43 : $levelName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLevelConfigRequest(levelName=" + this.getLevelName() + ", enabled=" + this.getEnabled() + ")";
   }
}
