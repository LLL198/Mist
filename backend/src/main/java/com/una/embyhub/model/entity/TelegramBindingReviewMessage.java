package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Generated;

@TableName("telegram_binding_review_message")
public class TelegramBindingReviewMessage extends BaseEntity {
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("review_id")
   private Long reviewId;
   @TableField("chat_id")
   private Long chatId;
   @TableField("message_id")
   private Integer messageId;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getReviewId() {
      return this.reviewId;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Integer getMessageId() {
      return this.messageId;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setReviewId(final Long reviewId) {
      this.reviewId = reviewId;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setMessageId(final Integer messageId) {
      this.messageId = messageId;
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindingReviewMessage(id="
         + this.getId()
         + ", reviewId="
         + this.getReviewId()
         + ", chatId="
         + this.getChatId()
         + ", messageId="
         + this.getMessageId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBindingReviewMessage other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$reviewId = this.getReviewId();
            Object other$reviewId = other.getReviewId();
            if (this$reviewId == null ? other$reviewId == null : this$reviewId.equals(other$reviewId)) {
               Object this$chatId = this.getChatId();
               Object other$chatId = other.getChatId();
               if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
                  Object this$messageId = this.getMessageId();
                  Object other$messageId = other.getMessageId();
                  return this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId);
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
      return other instanceof TelegramBindingReviewMessage;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $reviewId = this.getReviewId();
      result = result * 59 + ($reviewId == null ? 43 : $reviewId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $messageId = this.getMessageId();
      return result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
   }
}
