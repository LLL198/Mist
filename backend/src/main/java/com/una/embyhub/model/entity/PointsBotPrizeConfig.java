package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_bot_prize_config")
public class PointsBotPrizeConfig extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("prize_name")
   private String prizeName;
   @TableField("required_points")
   private Integer requiredPoints;
   @TableField("level_id")
   private Long levelId;
   @TableField("total_quantity")
   private Integer totalQuantity;
   @TableField("remaining_quantity")
   private Integer remainingQuantity;
   @TableField("enabled")
   private Integer enabled;
   @TableField("sort")
   private Integer sort;
   @TableField("remark")
   private String remark;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getPrizeName() {
      return this.prizeName;
   }

   @Generated
   public Integer getRequiredPoints() {
      return this.requiredPoints;
   }

   @Generated
   public Long getLevelId() {
      return this.levelId;
   }

   @Generated
   public Integer getTotalQuantity() {
      return this.totalQuantity;
   }

   @Generated
   public Integer getRemainingQuantity() {
      return this.remainingQuantity;
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
   public void setPrizeName(final String prizeName) {
      this.prizeName = prizeName;
   }

   @Generated
   public void setRequiredPoints(final Integer requiredPoints) {
      this.requiredPoints = requiredPoints;
   }

   @Generated
   public void setLevelId(final Long levelId) {
      this.levelId = levelId;
   }

   @Generated
   public void setTotalQuantity(final Integer totalQuantity) {
      this.totalQuantity = totalQuantity;
   }

   @Generated
   public void setRemainingQuantity(final Integer remainingQuantity) {
      this.remainingQuantity = remainingQuantity;
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
   public String toString() {
      return "PointsBotPrizeConfig(id="
         + this.getId()
         + ", prizeName="
         + this.getPrizeName()
         + ", requiredPoints="
         + this.getRequiredPoints()
         + ", levelId="
         + this.getLevelId()
         + ", totalQuantity="
         + this.getTotalQuantity()
         + ", remainingQuantity="
         + this.getRemainingQuantity()
         + ", enabled="
         + this.getEnabled()
         + ", sort="
         + this.getSort()
         + ", remark="
         + this.getRemark()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPrizeConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$requiredPoints = this.getRequiredPoints();
            Object other$requiredPoints = other.getRequiredPoints();
            if (this$requiredPoints == null ? other$requiredPoints == null : this$requiredPoints.equals(other$requiredPoints)) {
               Object this$levelId = this.getLevelId();
               Object other$levelId = other.getLevelId();
               if (this$levelId == null ? other$levelId == null : this$levelId.equals(other$levelId)) {
                  Object this$totalQuantity = this.getTotalQuantity();
                  Object other$totalQuantity = other.getTotalQuantity();
                  if (this$totalQuantity == null ? other$totalQuantity == null : this$totalQuantity.equals(other$totalQuantity)) {
                     Object this$remainingQuantity = this.getRemainingQuantity();
                     Object other$remainingQuantity = other.getRemainingQuantity();
                     if (this$remainingQuantity == null ? other$remainingQuantity == null : this$remainingQuantity.equals(other$remainingQuantity)) {
                        Object this$enabled = this.getEnabled();
                        Object other$enabled = other.getEnabled();
                        if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                           Object this$sort = this.getSort();
                           Object other$sort = other.getSort();
                           if (this$sort == null ? other$sort == null : this$sort.equals(other$sort)) {
                              Object this$prizeName = this.getPrizeName();
                              Object other$prizeName = other.getPrizeName();
                              if (this$prizeName == null ? other$prizeName == null : this$prizeName.equals(other$prizeName)) {
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotPrizeConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $requiredPoints = this.getRequiredPoints();
      result = result * 59 + ($requiredPoints == null ? 43 : $requiredPoints.hashCode());
      Object $levelId = this.getLevelId();
      result = result * 59 + ($levelId == null ? 43 : $levelId.hashCode());
      Object $totalQuantity = this.getTotalQuantity();
      result = result * 59 + ($totalQuantity == null ? 43 : $totalQuantity.hashCode());
      Object $remainingQuantity = this.getRemainingQuantity();
      result = result * 59 + ($remainingQuantity == null ? 43 : $remainingQuantity.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $sort = this.getSort();
      result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
      Object $prizeName = this.getPrizeName();
      result = result * 59 + ($prizeName == null ? 43 : $prizeName.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }
}
