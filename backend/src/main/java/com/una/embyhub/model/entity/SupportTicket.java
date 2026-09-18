package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("support_ticket")
public class SupportTicket extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final String COL_ID = "id";
   public static final String COL_TITLE = "title";
   public static final String COL_CONTENT = "content";
   public static final String COL_STATUS = "status";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_REPLY_COUNT = "reply_count";
   public static final String COL_LAST_REPLY_CONTENT = "last_reply_content";
   public static final String COL_LAST_REPLY_USER_NAME = "last_reply_user_name";
   public static final String COL_LAST_REPLY_DATETIME = "last_reply_datetime";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("title")
   private String title;
   @TableField("content")
   private String content;
   @TableField("`status`")
   private Integer status;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("reply_count")
   private Integer replyCount;
   @TableField("last_reply_content")
   private String lastReplyContent;
   @TableField("last_reply_user_name")
   private String lastReplyUserName;
   @TableField("last_reply_datetime")
   private Date lastReplyDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getContent() {
      return this.content;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
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
   public Integer getReplyCount() {
      return this.replyCount;
   }

   @Generated
   public String getLastReplyContent() {
      return this.lastReplyContent;
   }

   @Generated
   public String getLastReplyUserName() {
      return this.lastReplyUserName;
   }

   @Generated
   public Date getLastReplyDatetime() {
      return this.lastReplyDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
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
   public void setStatus(final Integer status) {
      this.status = status;
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
   public void setReplyCount(final Integer replyCount) {
      this.replyCount = replyCount;
   }

   @Generated
   public void setLastReplyContent(final String lastReplyContent) {
      this.lastReplyContent = lastReplyContent;
   }

   @Generated
   public void setLastReplyUserName(final String lastReplyUserName) {
      this.lastReplyUserName = lastReplyUserName;
   }

   @Generated
   public void setLastReplyDatetime(final Date lastReplyDatetime) {
      this.lastReplyDatetime = lastReplyDatetime;
   }

   @Generated
   @Override
   public String toString() {
      return "SupportTicket(id="
         + this.getId()
         + ", title="
         + this.getTitle()
         + ", content="
         + this.getContent()
         + ", status="
         + this.getStatus()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", replyCount="
         + this.getReplyCount()
         + ", lastReplyContent="
         + this.getLastReplyContent()
         + ", lastReplyUserName="
         + this.getLastReplyUserName()
         + ", lastReplyDatetime="
         + this.getLastReplyDatetime()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SupportTicket other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$replyCount = this.getReplyCount();
                  Object other$replyCount = other.getReplyCount();
                  if (this$replyCount == null ? other$replyCount == null : this$replyCount.equals(other$replyCount)) {
                     Object this$title = this.getTitle();
                     Object other$title = other.getTitle();
                     if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                        Object this$content = this.getContent();
                        Object other$content = other.getContent();
                        if (this$content == null ? other$content == null : this$content.equals(other$content)) {
                           Object this$embyUserName = this.getEmbyUserName();
                           Object other$embyUserName = other.getEmbyUserName();
                           if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                              Object this$lastReplyContent = this.getLastReplyContent();
                              Object other$lastReplyContent = other.getLastReplyContent();
                              if (this$lastReplyContent == null ? other$lastReplyContent == null : this$lastReplyContent.equals(other$lastReplyContent)) {
                                 Object this$lastReplyUserName = this.getLastReplyUserName();
                                 Object other$lastReplyUserName = other.getLastReplyUserName();
                                 if (this$lastReplyUserName == null ? other$lastReplyUserName == null : this$lastReplyUserName.equals(other$lastReplyUserName)) {
                                    Object this$lastReplyDatetime = this.getLastReplyDatetime();
                                    Object other$lastReplyDatetime = other.getLastReplyDatetime();
                                    return this$lastReplyDatetime == null
                                       ? other$lastReplyDatetime == null
                                       : this$lastReplyDatetime.equals(other$lastReplyDatetime);
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
      return other instanceof SupportTicket;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $replyCount = this.getReplyCount();
      result = result * 59 + ($replyCount == null ? 43 : $replyCount.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $content = this.getContent();
      result = result * 59 + ($content == null ? 43 : $content.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $lastReplyContent = this.getLastReplyContent();
      result = result * 59 + ($lastReplyContent == null ? 43 : $lastReplyContent.hashCode());
      Object $lastReplyUserName = this.getLastReplyUserName();
      result = result * 59 + ($lastReplyUserName == null ? 43 : $lastReplyUserName.hashCode());
      Object $lastReplyDatetime = this.getLastReplyDatetime();
      return result * 59 + ($lastReplyDatetime == null ? 43 : $lastReplyDatetime.hashCode());
   }
}
