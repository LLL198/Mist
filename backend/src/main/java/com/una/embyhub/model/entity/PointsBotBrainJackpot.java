package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_bot_brain_jackpot")
public class PointsBotBrainJackpot extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("jackpot_points")
   private Integer jackpotPoints;
   @TableField("completed_rounds")
   private Integer completedRounds;
   @TableField("peak_pending")
   private Boolean peakPending;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Integer getJackpotPoints() {
      return this.jackpotPoints;
   }

   @Generated
   public Integer getCompletedRounds() {
      return this.completedRounds;
   }

   @Generated
   public Boolean getPeakPending() {
      return this.peakPending;
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
   public void setJackpotPoints(final Integer jackpotPoints) {
      this.jackpotPoints = jackpotPoints;
   }

   @Generated
   public void setCompletedRounds(final Integer completedRounds) {
      this.completedRounds = completedRounds;
   }

   @Generated
   public void setPeakPending(final Boolean peakPending) {
      this.peakPending = peakPending;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotBrainJackpot(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", jackpotPoints="
         + this.getJackpotPoints()
         + ", completedRounds="
         + this.getCompletedRounds()
         + ", peakPending="
         + this.getPeakPending()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotBrainJackpot other)) {
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
               Object this$jackpotPoints = this.getJackpotPoints();
               Object other$jackpotPoints = other.getJackpotPoints();
               if (this$jackpotPoints == null ? other$jackpotPoints == null : this$jackpotPoints.equals(other$jackpotPoints)) {
                  Object this$completedRounds = this.getCompletedRounds();
                  Object other$completedRounds = other.getCompletedRounds();
                  if (this$completedRounds == null ? other$completedRounds == null : this$completedRounds.equals(other$completedRounds)) {
                     Object this$peakPending = this.getPeakPending();
                     Object other$peakPending = other.getPeakPending();
                     return this$peakPending == null ? other$peakPending == null : this$peakPending.equals(other$peakPending);
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
      return other instanceof PointsBotBrainJackpot;
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
      Object $jackpotPoints = this.getJackpotPoints();
      result = result * 59 + ($jackpotPoints == null ? 43 : $jackpotPoints.hashCode());
      Object $completedRounds = this.getCompletedRounds();
      result = result * 59 + ($completedRounds == null ? 43 : $completedRounds.hashCode());
      Object $peakPending = this.getPeakPending();
      return result * 59 + ($peakPending == null ? 43 : $peakPending.hashCode());
   }
}
