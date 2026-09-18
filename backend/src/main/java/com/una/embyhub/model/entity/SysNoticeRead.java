package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("sys_notice_read")
public class SysNoticeRead implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final String COL_ID = "id";
   public static final String COL_NOTICE_ID = "notice_id";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_READ_DATETIME = "read_datetime";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("notice_id")
   private Long noticeId;
   @TableField("user_id")
   private Long userId;
   @TableField("read_datetime")
   private Date readDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getNoticeId() {
      return this.noticeId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Date getReadDatetime() {
      return this.readDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setNoticeId(final Long noticeId) {
      this.noticeId = noticeId;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setReadDatetime(final Date readDatetime) {
      this.readDatetime = readDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SysNoticeRead other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$noticeId = this.getNoticeId();
            Object other$noticeId = other.getNoticeId();
            if (this$noticeId == null ? other$noticeId == null : this$noticeId.equals(other$noticeId)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$readDatetime = this.getReadDatetime();
                  Object other$readDatetime = other.getReadDatetime();
                  return this$readDatetime == null ? other$readDatetime == null : this$readDatetime.equals(other$readDatetime);
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
      return other instanceof SysNoticeRead;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $noticeId = this.getNoticeId();
      result = result * 59 + ($noticeId == null ? 43 : $noticeId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $readDatetime = this.getReadDatetime();
      return result * 59 + ($readDatetime == null ? 43 : $readDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SysNoticeRead(id="
         + this.getId()
         + ", noticeId="
         + this.getNoticeId()
         + ", userId="
         + this.getUserId()
         + ", readDatetime="
         + this.getReadDatetime()
         + ")";
   }
}
