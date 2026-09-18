package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_bot_lottery_entry")
public class PointsBotLotteryEntry extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("lottery_id")
   private Long lotteryId;
   @TableField("chat_id")
   private Long chatId;
   @TableField("user_id")
   private Long userId;
   @TableField("username")
   private String username;
   @TableField("display_name")
   private String displayName;
   @TableField("entry_note")
   private String entryNote;
   @TableField("is_winner")
   private Boolean isWinner;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getLotteryId() {
      return this.lotteryId;
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
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getDisplayName() {
      return this.displayName;
   }

   @Generated
   public String getEntryNote() {
      return this.entryNote;
   }

   @Generated
   public Boolean getIsWinner() {
      return this.isWinner;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setLotteryId(final Long lotteryId) {
      this.lotteryId = lotteryId;
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
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setDisplayName(final String displayName) {
      this.displayName = displayName;
   }

   @Generated
   public void setEntryNote(final String entryNote) {
      this.entryNote = entryNote;
   }

   @Generated
   public void setIsWinner(final Boolean isWinner) {
      this.isWinner = isWinner;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLotteryEntry(id="
         + this.getId()
         + ", lotteryId="
         + this.getLotteryId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", entryNote="
         + this.getEntryNote()
         + ", isWinner="
         + this.getIsWinner()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLotteryEntry other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$lotteryId = this.getLotteryId();
            Object other$lotteryId = other.getLotteryId();
            if (this$lotteryId == null ? other$lotteryId == null : this$lotteryId.equals(other$lotteryId)) {
               Object this$chatId = this.getChatId();
               Object other$chatId = other.getChatId();
               if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
                  Object this$userId = this.getUserId();
                  Object other$userId = other.getUserId();
                  if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                     Object this$isWinner = this.getIsWinner();
                     Object other$isWinner = other.getIsWinner();
                     if (this$isWinner == null ? other$isWinner == null : this$isWinner.equals(other$isWinner)) {
                        Object this$username = this.getUsername();
                        Object other$username = other.getUsername();
                        if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                           Object this$displayName = this.getDisplayName();
                           Object other$displayName = other.getDisplayName();
                           if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                              Object this$entryNote = this.getEntryNote();
                              Object other$entryNote = other.getEntryNote();
                              return this$entryNote == null ? other$entryNote == null : this$entryNote.equals(other$entryNote);
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
      return other instanceof PointsBotLotteryEntry;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $lotteryId = this.getLotteryId();
      result = result * 59 + ($lotteryId == null ? 43 : $lotteryId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $isWinner = this.getIsWinner();
      result = result * 59 + ($isWinner == null ? 43 : $isWinner.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $entryNote = this.getEntryNote();
      return result * 59 + ($entryNote == null ? 43 : $entryNote.hashCode());
   }
}
