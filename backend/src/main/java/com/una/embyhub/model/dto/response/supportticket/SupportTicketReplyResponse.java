package com.una.embyhub.model.dto.response.supportticket;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class SupportTicketReplyResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long id;
   private Long ticketId;
   private String replyContent;
   private Long userId;
   private String embyUserName;
   private Integer replyRole;
   private Date createDatetime;

   public String getReplyRoleName() {
      return this.replyRole != null && this.replyRole == 1 ? "管理员" : "用户";
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getTicketId() {
      return this.ticketId;
   }

   @Generated
   public String getReplyContent() {
      return this.replyContent;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Integer getReplyRole() {
      return this.replyRole;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
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
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setReplyRole(final Integer replyRole) {
      this.replyRole = replyRole;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SupportTicketReplyResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$ticketId = this.getTicketId();
            Object other$ticketId = other.getTicketId();
            if (this$ticketId == null ? other$ticketId == null : this$ticketId.equals(other$ticketId)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$replyRole = this.getReplyRole();
                  Object other$replyRole = other.getReplyRole();
                  if (this$replyRole == null ? other$replyRole == null : this$replyRole.equals(other$replyRole)) {
                     Object this$replyContent = this.getReplyContent();
                     Object other$replyContent = other.getReplyContent();
                     if (this$replyContent == null ? other$replyContent == null : this$replyContent.equals(other$replyContent)) {
                        Object this$embyUserName = this.getEmbyUserName();
                        Object other$embyUserName = other.getEmbyUserName();
                        if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                           Object this$createDatetime = this.getCreateDatetime();
                           Object other$createDatetime = other.getCreateDatetime();
                           return this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime);
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
   protected boolean canEqual(final Object other) {
      return other instanceof SupportTicketReplyResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $ticketId = this.getTicketId();
      result = result * 59 + ($ticketId == null ? 43 : $ticketId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $replyRole = this.getReplyRole();
      result = result * 59 + ($replyRole == null ? 43 : $replyRole.hashCode());
      Object $replyContent = this.getReplyContent();
      result = result * 59 + ($replyContent == null ? 43 : $replyContent.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SupportTicketReplyResponse(id="
         + this.getId()
         + ", ticketId="
         + this.getTicketId()
         + ", replyContent="
         + this.getReplyContent()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", replyRole="
         + this.getReplyRole()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
