package com.una.embyhub.model.dto.response.supportticket;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class SupportTicketResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long id;
   private String title;
   private String content;
   private Integer status;
   private String statusName;
   private Long userId;
   private String embyUserName;
   private Integer replyCount;
   private String lastReplyContent;
   private String lastReplyUserName;
   private Date lastReplyDatetime;
   private Date createDatetime;
   private Date updateDatetime;
   private List<SupportTicketReplyResponse> replies;

   public void setStatus(Integer status) {
      this.status = status;
      if (status == null) {
         this.statusName = "未知";
      } else if (status == 1) {
         this.statusName = "已批准";
      } else if (status == 2) {
         this.statusName = "已拒绝";
      } else {
         this.statusName = "待处理";
      }
   }

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
   public String getStatusName() {
      return this.statusName;
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
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public List<SupportTicketReplyResponse> getReplies() {
      return this.replies;
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
   public void setStatusName(final String statusName) {
      this.statusName = statusName;
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
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   public void setReplies(final List<SupportTicketReplyResponse> replies) {
      this.replies = replies;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SupportTicketResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
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
                           Object this$statusName = this.getStatusName();
                           Object other$statusName = other.getStatusName();
                           if (this$statusName == null ? other$statusName == null : this$statusName.equals(other$statusName)) {
                              Object this$embyUserName = this.getEmbyUserName();
                              Object other$embyUserName = other.getEmbyUserName();
                              if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                 Object this$lastReplyContent = this.getLastReplyContent();
                                 Object other$lastReplyContent = other.getLastReplyContent();
                                 if (this$lastReplyContent == null ? other$lastReplyContent == null : this$lastReplyContent.equals(other$lastReplyContent)) {
                                    Object this$lastReplyUserName = this.getLastReplyUserName();
                                    Object other$lastReplyUserName = other.getLastReplyUserName();
                                    if (this$lastReplyUserName == null
                                       ? other$lastReplyUserName == null
                                       : this$lastReplyUserName.equals(other$lastReplyUserName)) {
                                       Object this$lastReplyDatetime = this.getLastReplyDatetime();
                                       Object other$lastReplyDatetime = other.getLastReplyDatetime();
                                       if (this$lastReplyDatetime == null
                                          ? other$lastReplyDatetime == null
                                          : this$lastReplyDatetime.equals(other$lastReplyDatetime)) {
                                          Object this$createDatetime = this.getCreateDatetime();
                                          Object other$createDatetime = other.getCreateDatetime();
                                          if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                             Object this$updateDatetime = this.getUpdateDatetime();
                                             Object other$updateDatetime = other.getUpdateDatetime();
                                             if (this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime)) {
                                                Object this$replies = this.getReplies();
                                                Object other$replies = other.getReplies();
                                                return this$replies == null ? other$replies == null : this$replies.equals(other$replies);
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
      return other instanceof SupportTicketResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
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
      Object $statusName = this.getStatusName();
      result = result * 59 + ($statusName == null ? 43 : $statusName.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $lastReplyContent = this.getLastReplyContent();
      result = result * 59 + ($lastReplyContent == null ? 43 : $lastReplyContent.hashCode());
      Object $lastReplyUserName = this.getLastReplyUserName();
      result = result * 59 + ($lastReplyUserName == null ? 43 : $lastReplyUserName.hashCode());
      Object $lastReplyDatetime = this.getLastReplyDatetime();
      result = result * 59 + ($lastReplyDatetime == null ? 43 : $lastReplyDatetime.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $replies = this.getReplies();
      return result * 59 + ($replies == null ? 43 : $replies.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SupportTicketResponse(id="
         + this.getId()
         + ", title="
         + this.getTitle()
         + ", content="
         + this.getContent()
         + ", status="
         + this.getStatus()
         + ", statusName="
         + this.getStatusName()
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
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", replies="
         + this.getReplies()
         + ")";
   }
}
