package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_bot_hell_vault_ledger")
public class PointsBotHellVaultLedger extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("change_type")
   private String changeType;
   @TableField("delta_points")
   private Integer deltaPoints;
   @TableField("balance_after")
   private Integer balanceAfter;
   @TableField("ref_type")
   private String refType;
   @TableField("ref_id")
   private String refId;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public String getChangeType() {
      return this.changeType;
   }

   @Generated
   public Integer getDeltaPoints() {
      return this.deltaPoints;
   }

   @Generated
   public Integer getBalanceAfter() {
      return this.balanceAfter;
   }

   @Generated
   public String getRefType() {
      return this.refType;
   }

   @Generated
   public String getRefId() {
      return this.refId;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setChangeType(final String changeType) {
      this.changeType = changeType;
   }

   @Generated
   public void setDeltaPoints(final Integer deltaPoints) {
      this.deltaPoints = deltaPoints;
   }

   @Generated
   public void setBalanceAfter(final Integer balanceAfter) {
      this.balanceAfter = balanceAfter;
   }

   @Generated
   public void setRefType(final String refType) {
      this.refType = refType;
   }

   @Generated
   public void setRefId(final String refId) {
      this.refId = refId;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotHellVaultLedger(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", changeType="
         + this.getChangeType()
         + ", deltaPoints="
         + this.getDeltaPoints()
         + ", balanceAfter="
         + this.getBalanceAfter()
         + ", refType="
         + this.getRefType()
         + ", refId="
         + this.getRefId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotHellVaultLedger other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$deltaPoints = this.getDeltaPoints();
               Object other$deltaPoints = other.getDeltaPoints();
               if (this$deltaPoints == null ? other$deltaPoints == null : this$deltaPoints.equals(other$deltaPoints)) {
                  Object this$balanceAfter = this.getBalanceAfter();
                  Object other$balanceAfter = other.getBalanceAfter();
                  if (this$balanceAfter == null ? other$balanceAfter == null : this$balanceAfter.equals(other$balanceAfter)) {
                     Object this$changeType = this.getChangeType();
                     Object other$changeType = other.getChangeType();
                     if (this$changeType == null ? other$changeType == null : this$changeType.equals(other$changeType)) {
                        Object this$refType = this.getRefType();
                        Object other$refType = other.getRefType();
                        if (this$refType == null ? other$refType == null : this$refType.equals(other$refType)) {
                           Object this$refId = this.getRefId();
                           Object other$refId = other.getRefId();
                           return this$refId == null ? other$refId == null : this$refId.equals(other$refId);
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
      return other instanceof PointsBotHellVaultLedger;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $deltaPoints = this.getDeltaPoints();
      result = result * 59 + ($deltaPoints == null ? 43 : $deltaPoints.hashCode());
      Object $balanceAfter = this.getBalanceAfter();
      result = result * 59 + ($balanceAfter == null ? 43 : $balanceAfter.hashCode());
      Object $changeType = this.getChangeType();
      result = result * 59 + ($changeType == null ? 43 : $changeType.hashCode());
      Object $refType = this.getRefType();
      result = result * 59 + ($refType == null ? 43 : $refType.hashCode());
      Object $refId = this.getRefId();
      return result * 59 + ($refId == null ? 43 : $refId.hashCode());
   }
}
