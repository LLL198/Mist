package com.una.embyhub.model.dto.request.supportticket;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class SupportTicketReviewRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   @NotNull(
      message = "工单ID不能为空"
   )
   private Long ticketId;
   @NotNull(
      message = "审批状态不能为空"
   )
   private Integer status;
   private String replyContent;

   @Generated
   public Long getTicketId() {
      return this.ticketId;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
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
   public void setStatus(final Integer status) {
      this.status = status;
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
      } else if (!(o instanceof SupportTicketReviewRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$ticketId = this.getTicketId();
         Object other$ticketId = other.getTicketId();
         if (this$ticketId == null ? other$ticketId == null : this$ticketId.equals(other$ticketId)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$replyContent = this.getReplyContent();
               Object other$replyContent = other.getReplyContent();
               return this$replyContent == null ? other$replyContent == null : this$replyContent.equals(other$replyContent);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof SupportTicketReviewRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $ticketId = this.getTicketId();
      result = result * 59 + ($ticketId == null ? 43 : $ticketId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $replyContent = this.getReplyContent();
      return result * 59 + ($replyContent == null ? 43 : $replyContent.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SupportTicketReviewRequest(ticketId=" + this.getTicketId() + ", status=" + this.getStatus() + ", replyContent=" + this.getReplyContent() + ")";
   }
}
