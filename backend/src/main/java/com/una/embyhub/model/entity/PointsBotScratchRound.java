package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_scratch_round")
public class PointsBotScratchRound extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("message_id")
   private Long messageId;
   @TableField("status")
   private String status;
   @TableField("entry_cost")
   private Integer entryCost;
   @TableField("draw_at")
   private LocalDateTime drawAt;
   @TableField("drawn_at")
   private LocalDateTime drawnAt;
   @TableField("jackpot_cell")
   private Integer jackpotCell;
   @TableField("pity_target")
   private Integer pityTarget;
   @TableField("pity_progress")
   private Integer pityProgress;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getMessageId() {
      return this.messageId;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Integer getEntryCost() {
      return this.entryCost;
   }

   @Generated
   public LocalDateTime getDrawAt() {
      return this.drawAt;
   }

   @Generated
   public LocalDateTime getDrawnAt() {
      return this.drawnAt;
   }

   @Generated
   public Integer getJackpotCell() {
      return this.jackpotCell;
   }

   @Generated
   public Integer getPityTarget() {
      return this.pityTarget;
   }

   @Generated
   public Integer getPityProgress() {
      return this.pityProgress;
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
   public void setMessageId(final Long messageId) {
      this.messageId = messageId;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setEntryCost(final Integer entryCost) {
      this.entryCost = entryCost;
   }

   @Generated
   public void setDrawAt(final LocalDateTime drawAt) {
      this.drawAt = drawAt;
   }

   @Generated
   public void setDrawnAt(final LocalDateTime drawnAt) {
      this.drawnAt = drawnAt;
   }

   @Generated
   public void setJackpotCell(final Integer jackpotCell) {
      this.jackpotCell = jackpotCell;
   }

   @Generated
   public void setPityTarget(final Integer pityTarget) {
      this.pityTarget = pityTarget;
   }

   @Generated
   public void setPityProgress(final Integer pityProgress) {
      this.pityProgress = pityProgress;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotScratchRound(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", messageId="
         + this.getMessageId()
         + ", status="
         + this.getStatus()
         + ", entryCost="
         + this.getEntryCost()
         + ", drawAt="
         + this.getDrawAt()
         + ", drawnAt="
         + this.getDrawnAt()
         + ", jackpotCell="
         + this.getJackpotCell()
         + ", pityTarget="
         + this.getPityTarget()
         + ", pityProgress="
         + this.getPityProgress()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotScratchRound other)) {
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
               Object this$messageId = this.getMessageId();
               Object other$messageId = other.getMessageId();
               if (this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId)) {
                  Object this$entryCost = this.getEntryCost();
                  Object other$entryCost = other.getEntryCost();
                  if (this$entryCost == null ? other$entryCost == null : this$entryCost.equals(other$entryCost)) {
                     Object this$jackpotCell = this.getJackpotCell();
                     Object other$jackpotCell = other.getJackpotCell();
                     if (this$jackpotCell == null ? other$jackpotCell == null : this$jackpotCell.equals(other$jackpotCell)) {
                        Object this$pityTarget = this.getPityTarget();
                        Object other$pityTarget = other.getPityTarget();
                        if (this$pityTarget == null ? other$pityTarget == null : this$pityTarget.equals(other$pityTarget)) {
                           Object this$pityProgress = this.getPityProgress();
                           Object other$pityProgress = other.getPityProgress();
                           if (this$pityProgress == null ? other$pityProgress == null : this$pityProgress.equals(other$pityProgress)) {
                              Object this$status = this.getStatus();
                              Object other$status = other.getStatus();
                              if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                 Object this$drawAt = this.getDrawAt();
                                 Object other$drawAt = other.getDrawAt();
                                 if (this$drawAt == null ? other$drawAt == null : this$drawAt.equals(other$drawAt)) {
                                    Object this$drawnAt = this.getDrawnAt();
                                    Object other$drawnAt = other.getDrawnAt();
                                    return this$drawnAt == null ? other$drawnAt == null : this$drawnAt.equals(other$drawnAt);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotScratchRound;
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
      Object $messageId = this.getMessageId();
      result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
      Object $entryCost = this.getEntryCost();
      result = result * 59 + ($entryCost == null ? 43 : $entryCost.hashCode());
      Object $jackpotCell = this.getJackpotCell();
      result = result * 59 + ($jackpotCell == null ? 43 : $jackpotCell.hashCode());
      Object $pityTarget = this.getPityTarget();
      result = result * 59 + ($pityTarget == null ? 43 : $pityTarget.hashCode());
      Object $pityProgress = this.getPityProgress();
      result = result * 59 + ($pityProgress == null ? 43 : $pityProgress.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $drawAt = this.getDrawAt();
      result = result * 59 + ($drawAt == null ? 43 : $drawAt.hashCode());
      Object $drawnAt = this.getDrawnAt();
      return result * 59 + ($drawnAt == null ? 43 : $drawnAt.hashCode());
   }
}
