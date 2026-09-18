package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotLevelConfigUpdate implements Serializable {
   @NotNull(
      message = "等级配置ID不能为空"
   )
   private Long id;
   private String levelName;
   private Integer minPoints;
   private Integer enabled;
   private Integer sort;
   private String remark;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getLevelName() {
      return this.levelName;
   }

   @Generated
   public Integer getMinPoints() {
      return this.minPoints;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public Integer getSort() {
      return this.sort;
   }

   @Generated
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setLevelName(final String levelName) {
      this.levelName = levelName;
   }

   @Generated
   public void setMinPoints(final Integer minPoints) {
      this.minPoints = minPoints;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setSort(final Integer sort) {
      this.sort = sort;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLevelConfigUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$minPoints = this.getMinPoints();
            Object other$minPoints = other.getMinPoints();
            if (this$minPoints == null ? other$minPoints == null : this$minPoints.equals(other$minPoints)) {
               Object this$enabled = this.getEnabled();
               Object other$enabled = other.getEnabled();
               if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                  Object this$sort = this.getSort();
                  Object other$sort = other.getSort();
                  if (this$sort == null ? other$sort == null : this$sort.equals(other$sort)) {
                     Object this$levelName = this.getLevelName();
                     Object other$levelName = other.getLevelName();
                     if (this$levelName == null ? other$levelName == null : this$levelName.equals(other$levelName)) {
                        Object this$remark = this.getRemark();
                        Object other$remark = other.getRemark();
                        return this$remark == null ? other$remark == null : this$remark.equals(other$remark);
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
      return other instanceof PointsBotLevelConfigUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $minPoints = this.getMinPoints();
      result = result * 59 + ($minPoints == null ? 43 : $minPoints.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $sort = this.getSort();
      result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
      Object $levelName = this.getLevelName();
      result = result * 59 + ($levelName == null ? 43 : $levelName.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLevelConfigUpdate(id="
         + this.getId()
         + ", levelName="
         + this.getLevelName()
         + ", minPoints="
         + this.getMinPoints()
         + ", enabled="
         + this.getEnabled()
         + ", sort="
         + this.getSort()
         + ", remark="
         + this.getRemark()
         + ")";
   }
}
