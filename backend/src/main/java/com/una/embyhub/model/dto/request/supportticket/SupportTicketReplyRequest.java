package com.una.embyhub.model.dto.request.supportticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class SupportTicketReplyRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   @NotNull(
      message = "工单ID不能为空"
   )
   private Long ticketId;
   @NotBlank(
      message = "回复内容不能为空"
   )
   private String replyContent;

   @Generated
   public Long getTicketId() {
      return this.ticketId;
   }

   @Generated
   public String getReplyContent() {
      return this.replyContent;
   }

   @Generated
   public void setTicketId(final Long ticketId) {
      this.ticketId = ticketId;
   }

   @Generated
   public void setReplyContent(final String replyContent) {
      this.replyContent = replyContent;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SupportTicketReplyRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$ticketId = this.getTicketId();
         Object other$ticketId = other.getTicketId();
         if (this$ticketId == null ? other$ticketId == null : this$ticketId.equals(other$ticketId)) {
            Object this$replyContent = this.getReplyContent();
            Object other$replyContent = other.getReplyContent();
            return this$replyContent == null ? other$replyContent == null : this$replyContent.equals(other$replyContent);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof SupportTicketReplyRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $ticketId = this.getTicketId();
      result = result * 59 + ($ticketId == null ? 43 : $ticketId.hashCode());
      Object $replyContent = this.getReplyContent();
      return result * 59 + ($replyContent == null ? 43 : $replyContent.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SupportTicketReplyRequest(ticketId=" + this.getTicketId() + ", replyContent=" + this.getReplyContent() + ")";
   }
}
