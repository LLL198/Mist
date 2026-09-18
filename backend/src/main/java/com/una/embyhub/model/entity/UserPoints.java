package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("user_points")
public class UserPoints extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("user_id")
   private Long userId;
   @TableField("points_balance")
   private Integer pointsBalance;
   @TableField("total_earned")
   private Integer totalEarned;
   @TableField("total_spent")
   private Integer totalSpent;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Integer getPointsBalance() {
      return this.pointsBalance;
   }

   @Generated
   public Integer getTotalEarned() {
      return this.totalEarned;
   }

   @Generated
   public Integer getTotalSpent() {
      return this.totalSpent;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setPointsBalance(final Integer pointsBalance) {
      this.pointsBalance = pointsBalance;
   }

   @Generated
   public void setTotalEarned(final Integer totalEarned) {
      this.totalEarned = totalEarned;
   }

   @Generated
   public void setTotalSpent(final Integer totalSpent) {
      this.totalSpent = totalSpent;
   }

   @Generated
   @Override
   public String toString() {
      return "UserPoints(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", pointsBalance="
         + this.getPointsBalance()
         + ", totalEarned="
         + this.getTotalEarned()
         + ", totalSpent="
         + this.getTotalSpent()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserPoints other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$pointsBalance = this.getPointsBalance();
               Object other$pointsBalance = other.getPointsBalance();
               if (this$pointsBalance == null ? other$pointsBalance == null : this$pointsBalance.equals(other$pointsBalance)) {
                  Object this$totalEarned = this.getTotalEarned();
                  Object other$totalEarned = other.getTotalEarned();
                  if (this$totalEarned == null ? other$totalEarned == null : this$totalEarned.equals(other$totalEarned)) {
                     Object this$totalSpent = this.getTotalSpent();
                     Object other$totalSpent = other.getTotalSpent();
                     return this$totalSpent == null ? other$totalSpent == null : this$totalSpent.equals(other$totalSpent);
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
      return other instanceof UserPoints;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $pointsBalance = this.getPointsBalance();
      result = result * 59 + ($pointsBalance == null ? 43 : $pointsBalance.hashCode());
      Object $totalEarned = this.getTotalEarned();
      result = result * 59 + ($totalEarned == null ? 43 : $totalEarned.hashCode());
      Object $totalSpent = this.getTotalSpent();
      return result * 59 + ($totalSpent == null ? 43 : $totalSpent.hashCode());
   }
}
