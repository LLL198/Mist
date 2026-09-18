package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_record")
public class PointsRecord extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("user_id")
   private Long userId;
   @TableField("record_type")
   private String recordType;
   @TableField("amount")
   private Integer amount;
   @TableField("balance_after")
   private Integer balanceAfter;
   @TableField("description")
   private String description;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getRecordType() {
      return this.recordType;
   }

   @Generated
   public Integer getAmount() {
      return this.amount;
   }

   @Generated
   public Integer getBalanceAfter() {
      return this.balanceAfter;
   }

   @Generated
   public String getDescription() {
      return this.description;
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
   public void setRecordType(final String recordType) {
      this.recordType = recordType;
   }

   @Generated
   public void setAmount(final Integer amount) {
      this.amount = amount;
   }

   @Generated
   public void setBalanceAfter(final Integer balanceAfter) {
      this.balanceAfter = balanceAfter;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsRecord(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", recordType="
         + this.getRecordType()
         + ", amount="
         + this.getAmount()
         + ", balanceAfter="
         + this.getBalanceAfter()
         + ", description="
         + this.getDescription()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsRecord other)) {
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
               Object this$amount = this.getAmount();
               Object other$amount = other.getAmount();
               if (this$amount == null ? other$amount == null : this$amount.equals(other$amount)) {
                  Object this$balanceAfter = this.getBalanceAfter();
                  Object other$balanceAfter = other.getBalanceAfter();
                  if (this$balanceAfter == null ? other$balanceAfter == null : this$balanceAfter.equals(other$balanceAfter)) {
                     Object this$recordType = this.getRecordType();
                     Object other$recordType = other.getRecordType();
                     if (this$recordType == null ? other$recordType == null : this$recordType.equals(other$recordType)) {
                        Object this$description = this.getDescription();
                        Object other$description = other.getDescription();
                        return this$description == null ? other$description == null : this$description.equals(other$description);
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
      return other instanceof PointsRecord;
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
      Object $amount = this.getAmount();
      result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
      Object $balanceAfter = this.getBalanceAfter();
      result = result * 59 + ($balanceAfter == null ? 43 : $balanceAfter.hashCode());
      Object $recordType = this.getRecordType();
      result = result * 59 + ($recordType == null ? 43 : $recordType.hashCode());
      Object $description = this.getDescription();
      return result * 59 + ($description == null ? 43 : $description.hashCode());
   }
}
