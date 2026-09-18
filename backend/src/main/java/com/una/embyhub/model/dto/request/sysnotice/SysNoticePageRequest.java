package com.una.embyhub.model.dto.request.sysnotice;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class SysNoticePageRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.LIKE
   )
   private String noticeTitle;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer noticeStatus;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer noticeScope;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer topFlag;
   @BindQuery(
      column = "create_datetime",
      comparison = Comparison.GE
   )
   private Date createStartTime;
   @BindQuery(
      column = "create_datetime",
      comparison = Comparison.LE
   )
   private Date createEndTime;

   @Generated
   public String getNoticeTitle() {
      return this.noticeTitle;
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
   public Date getCreateStartTime() {
      return this.createStartTime;
   }

   @Generated
   public Date getCreateEndTime() {
      return this.createEndTime;
   }

   @Generated
   public void setNoticeTitle(final String noticeTitle) {
      this.noticeTitle = noticeTitle;
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
   public void setCreateStartTime(final Date createStartTime) {
      this.createStartTime = createStartTime;
   }

   @Generated
   public void setCreateEndTime(final Date createEndTime) {
      this.createEndTime = createEndTime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SysNoticePageRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$noticeStatus = this.getNoticeStatus();
         Object other$noticeStatus = other.getNoticeStatus();
         if (this$noticeStatus == null ? other$noticeStatus == null : this$noticeStatus.equals(other$noticeStatus)) {
            Object this$noticeScope = this.getNoticeScope();
            Object other$noticeScope = other.getNoticeScope();
            if (this$noticeScope == null ? other$noticeScope == null : this$noticeScope.equals(other$noticeScope)) {
               Object this$topFlag = this.getTopFlag();
               Object other$topFlag = other.getTopFlag();
               if (this$topFlag == null ? other$topFlag == null : this$topFlag.equals(other$topFlag)) {
                  Object this$noticeTitle = this.getNoticeTitle();
                  Object other$noticeTitle = other.getNoticeTitle();
                  if (this$noticeTitle == null ? other$noticeTitle == null : this$noticeTitle.equals(other$noticeTitle)) {
                     Object this$createStartTime = this.getCreateStartTime();
                     Object other$createStartTime = other.getCreateStartTime();
                     if (this$createStartTime == null ? other$createStartTime == null : this$createStartTime.equals(other$createStartTime)) {
                        Object this$createEndTime = this.getCreateEndTime();
                        Object other$createEndTime = other.getCreateEndTime();
                        return this$createEndTime == null ? other$createEndTime == null : this$createEndTime.equals(other$createEndTime);
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
      return other instanceof SysNoticePageRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $noticeStatus = this.getNoticeStatus();
      result = result * 59 + ($noticeStatus == null ? 43 : $noticeStatus.hashCode());
      Object $noticeScope = this.getNoticeScope();
      result = result * 59 + ($noticeScope == null ? 43 : $noticeScope.hashCode());
      Object $topFlag = this.getTopFlag();
      result = result * 59 + ($topFlag == null ? 43 : $topFlag.hashCode());
      Object $noticeTitle = this.getNoticeTitle();
      result = result * 59 + ($noticeTitle == null ? 43 : $noticeTitle.hashCode());
      Object $createStartTime = this.getCreateStartTime();
      result = result * 59 + ($createStartTime == null ? 43 : $createStartTime.hashCode());
      Object $createEndTime = this.getCreateEndTime();
      return result * 59 + ($createEndTime == null ? 43 : $createEndTime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SysNoticePageRequest(noticeTitle="
         + this.getNoticeTitle()
         + ", noticeStatus="
         + this.getNoticeStatus()
         + ", noticeScope="
         + this.getNoticeScope()
         + ", topFlag="
         + this.getTopFlag()
         + ", createStartTime="
         + this.getCreateStartTime()
         + ", createEndTime="
         + this.getCreateEndTime()
         + ")";
   }
}
