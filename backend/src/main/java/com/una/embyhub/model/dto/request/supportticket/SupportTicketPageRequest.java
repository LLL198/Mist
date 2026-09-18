package com.una.embyhub.model.dto.request.supportticket;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class SupportTicketPageRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   @BindQuery(
      comparison = Comparison.LIKE
   )
   private String title;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer status;
   @BindQuery(
      comparison = Comparison.LIKE
   )
   private String embyUserName;
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
   public String getTitle() {
      return this.title;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
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
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
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
      } else if (!(o instanceof SupportTicketPageRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$status = this.getStatus();
         Object other$status = other.getStatus();
         if (this$status == null ? other$status == null : this$status.equals(other$status)) {
            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null ? other$title == null : this$title.equals(other$title)) {
               Object this$embyUserName = this.getEmbyUserName();
               Object other$embyUserName = other.getEmbyUserName();
               if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof SupportTicketPageRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $createStartTime = this.getCreateStartTime();
      result = result * 59 + ($createStartTime == null ? 43 : $createStartTime.hashCode());
      Object $createEndTime = this.getCreateEndTime();
      return result * 59 + ($createEndTime == null ? 43 : $createEndTime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SupportTicketPageRequest(title="
         + this.getTitle()
         + ", status="
         + this.getStatus()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", createStartTime="
         + this.getCreateStartTime()
         + ", createEndTime="
         + this.getCreateEndTime()
         + ")";
   }
}
