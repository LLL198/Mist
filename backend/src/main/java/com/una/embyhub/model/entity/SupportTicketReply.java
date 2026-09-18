package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("support_ticket_reply")
public class SupportTicketReply extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final String COL_ID = "id";
   public static final String COL_TICKET_ID = "ticket_id";
   public static final String COL_REPLY_CONTENT = "reply_content";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_REPLY_ROLE = "reply_role";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("ticket_id")
   private Long ticketId;
   @TableField("reply_content")
   private String replyContent;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("reply_role")
   private Integer replyRole;

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
   @Override
   public String toString() {
      return "SupportTicketReply(id="
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
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SupportTicketReply other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
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
                        return this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName);
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
      return other instanceof SupportTicketReply;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
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
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }
}
