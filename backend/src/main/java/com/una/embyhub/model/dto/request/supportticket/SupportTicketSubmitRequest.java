package com.una.embyhub.model.dto.request.supportticket;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class SupportTicketSubmitRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   @NotBlank(
      message = "工单标题不能为空"
   )
   private String title;
   @NotBlank(
      message = "工单内容不能为空"
   )
   private String content;

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getContent() {
      return this.content;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setContent(final String content) {
      this.content = content;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SupportTicketSubmitRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$title = this.getTitle();
         Object other$title = other.getTitle();
         if (this$title == null ? other$title == null : this$title.equals(other$title)) {
            Object this$content = this.getContent();
            Object other$content = other.getContent();
            return this$content == null ? other$content == null : this$content.equals(other$content);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof SupportTicketSubmitRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $content = this.getContent();
      return result * 59 + ($content == null ? 43 : $content.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SupportTicketSubmitRequest(title=" + this.getTitle() + ", content=" + this.getContent() + ")";
   }
}
