package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_bot_mist_bag_config")
public class PointsBotFoamBagConfig extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("amount_tiers_json")
   private String amountTiersJson;
   @TableField("daily_limit")
   private Integer dailyLimit;
   @TableField("repayment_multiplier")
   private Integer repaymentMultiplier;
   @TableField("repayment_hours")
   private Integer repaymentHours;
   @TableField("penalty_days")
   private Integer penaltyDays;
   @TableField("allow_unbound_users")
   private Integer allowUnboundUsers;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getAmountTiersJson() {
      return this.amountTiersJson;
   }

   @Generated
   public Integer getDailyLimit() {
      return this.dailyLimit;
   }

   @Generated
   public Integer getRepaymentMultiplier() {
      return this.repaymentMultiplier;
   }

   @Generated
   public Integer getRepaymentHours() {
      return this.repaymentHours;
   }

   @Generated
   public Integer getPenaltyDays() {
      return this.penaltyDays;
   }

   @Generated
   public Integer getAllowUnboundUsers() {
      return this.allowUnboundUsers;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setAmountTiersJson(final String amountTiersJson) {
      this.amountTiersJson = amountTiersJson;
   }

   @Generated
   public void setDailyLimit(final Integer dailyLimit) {
      this.dailyLimit = dailyLimit;
   }

   @Generated
   public void setRepaymentMultiplier(final Integer repaymentMultiplier) {
      this.repaymentMultiplier = repaymentMultiplier;
   }

   @Generated
   public void setRepaymentHours(final Integer repaymentHours) {
      this.repaymentHours = repaymentHours;
   }

   @Generated
   public void setPenaltyDays(final Integer penaltyDays) {
      this.penaltyDays = penaltyDays;
   }

   @Generated
   public void setAllowUnboundUsers(final Integer allowUnboundUsers) {
      this.allowUnboundUsers = allowUnboundUsers;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotFoamBagConfig(id="
         + this.getId()
         + ", amountTiersJson="
         + this.getAmountTiersJson()
         + ", dailyLimit="
         + this.getDailyLimit()
         + ", repaymentMultiplier="
         + this.getRepaymentMultiplier()
         + ", repaymentHours="
         + this.getRepaymentHours()
         + ", penaltyDays="
         + this.getPenaltyDays()
         + ", allowUnboundUsers="
         + this.getAllowUnboundUsers()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotFoamBagConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$dailyLimit = this.getDailyLimit();
            Object other$dailyLimit = other.getDailyLimit();
            if (this$dailyLimit == null ? other$dailyLimit == null : this$dailyLimit.equals(other$dailyLimit)) {
               Object this$repaymentMultiplier = this.getRepaymentMultiplier();
               Object other$repaymentMultiplier = other.getRepaymentMultiplier();
               if (this$repaymentMultiplier == null ? other$repaymentMultiplier == null : this$repaymentMultiplier.equals(other$repaymentMultiplier)) {
                  Object this$repaymentHours = this.getRepaymentHours();
                  Object other$repaymentHours = other.getRepaymentHours();
                  if (this$repaymentHours == null ? other$repaymentHours == null : this$repaymentHours.equals(other$repaymentHours)) {
                     Object this$penaltyDays = this.getPenaltyDays();
                     Object other$penaltyDays = other.getPenaltyDays();
                     if (this$penaltyDays == null ? other$penaltyDays == null : this$penaltyDays.equals(other$penaltyDays)) {
                        Object this$allowUnboundUsers = this.getAllowUnboundUsers();
                        Object other$allowUnboundUsers = other.getAllowUnboundUsers();
                        if (this$allowUnboundUsers == null ? other$allowUnboundUsers == null : this$allowUnboundUsers.equals(other$allowUnboundUsers)) {
                           Object this$amountTiersJson = this.getAmountTiersJson();
                           Object other$amountTiersJson = other.getAmountTiersJson();
                           return this$amountTiersJson == null ? other$amountTiersJson == null : this$amountTiersJson.equals(other$amountTiersJson);
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
      return other instanceof PointsBotFoamBagConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $dailyLimit = this.getDailyLimit();
      result = result * 59 + ($dailyLimit == null ? 43 : $dailyLimit.hashCode());
      Object $repaymentMultiplier = this.getRepaymentMultiplier();
      result = result * 59 + ($repaymentMultiplier == null ? 43 : $repaymentMultiplier.hashCode());
      Object $repaymentHours = this.getRepaymentHours();
      result = result * 59 + ($repaymentHours == null ? 43 : $repaymentHours.hashCode());
      Object $penaltyDays = this.getPenaltyDays();
      result = result * 59 + ($penaltyDays == null ? 43 : $penaltyDays.hashCode());
      Object $allowUnboundUsers = this.getAllowUnboundUsers();
      result = result * 59 + ($allowUnboundUsers == null ? 43 : $allowUnboundUsers.hashCode());
      Object $amountTiersJson = this.getAmountTiersJson();
      return result * 59 + ($amountTiersJson == null ? 43 : $amountTiersJson.hashCode());
   }
}
