package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("sys_notice")
public class SysNotice extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("notice_title")
   private String noticeTitle;
   @TableField("notice_content")
   private String noticeContent;
   @TableField("notice_status")
   private Integer noticeStatus;
   @TableField("notice_scope")
   private Integer noticeScope;
   @TableField("top_flag")
   private Integer topFlag;
   @TableField("sort")
   private Integer sort;
   public static final String COL_ID = "id";
   public static final String COL_NOTICE_TITLE = "notice_title";
   public static final String COL_NOTICE_CONTENT = "notice_content";
   public static final String COL_NOTICE_STATUS = "notice_status";
   public static final String COL_NOTICE_SCOPE = "notice_scope";
   public static final String COL_TOP_FLAG = "top_flag";
   public static final String COL_SORT = "sort";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getNoticeTitle() {
      return this.noticeTitle;
   }

   @Generated
   public String getNoticeContent() {
      return this.noticeContent;
   }

   @Generated
   public Integer getNoticeStatus() {
      return this.noticeStatus;
   }

   @Generated
   public Integer getNoticeScope() {
      return this.noticeScope;
   }

   @Generated
   public Integer getTopFlag() {
      return this.topFlag;
   }

   @Generated
   public Integer getSort() {
      return this.sort;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setNoticeTitle(final String noticeTitle) {
      this.noticeTitle = noticeTitle;
   }

   @Generated
   public void setNoticeContent(final String noticeContent) {
      this.noticeContent = noticeContent;
   }

   @Generated
   public void setNoticeStatus(final Integer noticeStatus) {
      this.noticeStatus = noticeStatus;
   }

   @Generated
   public void setNoticeScope(final Integer noticeScope) {
      this.noticeScope = noticeScope;
   }

   @Generated
   public void setTopFlag(final Integer topFlag) {
      this.topFlag = topFlag;
   }

   @Generated
   public void setSort(final Integer sort) {
      this.sort = sort;
   }

   @Generated
   @Override
   public String toString() {
      return "SysNotice(id="
         + this.getId()
         + ", noticeTitle="
         + this.getNoticeTitle()
         + ", noticeContent="
         + this.getNoticeContent()
         + ", noticeStatus="
         + this.getNoticeStatus()
         + ", noticeScope="
         + this.getNoticeScope()
         + ", topFlag="
         + this.getTopFlag()
         + ", sort="
         + this.getSort()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SysNotice other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$noticeStatus = this.getNoticeStatus();
            Object other$noticeStatus = other.getNoticeStatus();
            if (this$noticeStatus == null ? other$noticeStatus == null : this$noticeStatus.equals(other$noticeStatus)) {
               Object this$noticeScope = this.getNoticeScope();
               Object other$noticeScope = other.getNoticeScope();
               if (this$noticeScope == null ? other$noticeScope == null : this$noticeScope.equals(other$noticeScope)) {
                  Object this$topFlag = this.getTopFlag();
                  Object other$topFlag = other.getTopFlag();
                  if (this$topFlag == null ? other$topFlag == null : this$topFlag.equals(other$topFlag)) {
                     Object this$sort = this.getSort();
                     Object other$sort = other.getSort();
                     if (this$sort == null ? other$sort == null : this$sort.equals(other$sort)) {
                        Object this$noticeTitle = this.getNoticeTitle();
                        Object other$noticeTitle = other.getNoticeTitle();
                        if (this$noticeTitle == null ? other$noticeTitle == null : this$noticeTitle.equals(other$noticeTitle)) {
                           Object this$noticeContent = this.getNoticeContent();
                           Object other$noticeContent = other.getNoticeContent();
                           return this$noticeContent == null ? other$noticeContent == null : this$noticeContent.equals(other$noticeContent);
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
      return other instanceof SysNotice;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $noticeStatus = this.getNoticeStatus();
      result = result * 59 + ($noticeStatus == null ? 43 : $noticeStatus.hashCode());
      Object $noticeScope = this.getNoticeScope();
      result = result * 59 + ($noticeScope == null ? 43 : $noticeScope.hashCode());
      Object $topFlag = this.getTopFlag();
      result = result * 59 + ($topFlag == null ? 43 : $topFlag.hashCode());
      Object $sort = this.getSort();
      result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
      Object $noticeTitle = this.getNoticeTitle();
      result = result * 59 + ($noticeTitle == null ? 43 : $noticeTitle.hashCode());
      Object $noticeContent = this.getNoticeContent();
      return result * 59 + ($noticeContent == null ? 43 : $noticeContent.hashCode());
   }
}
