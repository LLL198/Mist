package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_bot_ledger")
public class PointsBotLedger extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("user_id")
   private Long userId;
   @TableField("delta")
   private Integer delta;
   @TableField("reason")
   private String reason;
   @TableField("ref_id")
   private String refId;
   @TableField("server_id")
   private Long serverId;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Integer getDelta() {
      return this.delta;
   }

   @Generated
   public String getReason() {
      return this.reason;
   }

   @Generated
   public String getRefId() {
      return this.refId;
   }

   @Generated
   public Long getServerId() {
      return this.serverId;
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
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setDelta(final Integer delta) {
      this.delta = delta;
   }

   @Generated
   public void setReason(final String reason) {
      this.reason = reason;
   }

   @Generated
   public void setRefId(final String refId) {
      this.refId = refId;
   }

   @Generated
   public void setServerId(final Long serverId) {
      this.serverId = serverId;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLedger(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", delta="
         + this.getDelta()
         + ", reason="
         + this.getReason()
         + ", refId="
         + this.getRefId()
         + ", serverId="
         + this.getServerId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLedger other)) {
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
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$delta = this.getDelta();
                  Object other$delta = other.getDelta();
                  if (this$delta == null ? other$delta == null : this$delta.equals(other$delta)) {
                     Object this$serverId = this.getServerId();
                     Object other$serverId = other.getServerId();
                     if (this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId)) {
                        Object this$reason = this.getReason();
                        Object other$reason = other.getReason();
                        if (this$reason == null ? other$reason == null : this$reason.equals(other$reason)) {
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
      return other instanceof PointsBotLedger;
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
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $delta = this.getDelta();
      result = result * 59 + ($delta == null ? 43 : $delta.hashCode());
      Object $serverId = this.getServerId();
      result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
      Object $reason = this.getReason();
      result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
      Object $refId = this.getRefId();
      return result * 59 + ($refId == null ? 43 : $refId.hashCode());
   }
}
