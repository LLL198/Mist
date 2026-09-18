package com.una.embyhub.model.dto.request.embyuserrecord;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyUserRenewRecordRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.BETWEEN_BEGIN,
      column = "create_datetime"
   )
   private Date createDatetimeStart;
   @BindQuery(
      comparison = Comparison.BETWEEN_END,
      column = "create_datetime"
   )
   private Date createDatetimeEnd;
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   private String embyUserName;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer renewChannel;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long embyInfoId;

   @Generated
   public Date getCreateDatetimeStart() {
      return this.createDatetimeStart;
   }

   @Generated
   public Date getCreateDatetimeEnd() {
      return this.createDatetimeEnd;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Integer getRenewChannel() {
      return this.renewChannel;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setCreateDatetimeStart(final Date createDatetimeStart) {
      this.createDatetimeStart = createDatetimeStart;
   }

   @Generated
   public void setCreateDatetimeEnd(final Date createDatetimeEnd) {
      this.createDatetimeEnd = createDatetimeEnd;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setRenewChannel(final Integer renewChannel) {
      this.renewChannel = renewChannel;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserRenewRecordRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$renewChannel = this.getRenewChannel();
         Object other$renewChannel = other.getRenewChannel();
         if (this$renewChannel == null ? other$renewChannel == null : this$renewChannel.equals(other$renewChannel)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$createDatetimeStart = this.getCreateDatetimeStart();
               Object other$createDatetimeStart = other.getCreateDatetimeStart();
               if (this$createDatetimeStart == null ? other$createDatetimeStart == null : this$createDatetimeStart.equals(other$createDatetimeStart)) {
                  Object this$createDatetimeEnd = this.getCreateDatetimeEnd();
                  Object other$createDatetimeEnd = other.getCreateDatetimeEnd();
                  if (this$createDatetimeEnd == null ? other$createDatetimeEnd == null : this$createDatetimeEnd.equals(other$createDatetimeEnd)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyUserRenewRecordRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $renewChannel = this.getRenewChannel();
      result = result * 59 + ($renewChannel == null ? 43 : $renewChannel.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $createDatetimeStart = this.getCreateDatetimeStart();
      result = result * 59 + ($createDatetimeStart == null ? 43 : $createDatetimeStart.hashCode());
      Object $createDatetimeEnd = this.getCreateDatetimeEnd();
      result = result * 59 + ($createDatetimeEnd == null ? 43 : $createDatetimeEnd.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserRenewRecordRequest(createDatetimeStart="
         + this.getCreateDatetimeStart()
         + ", createDatetimeEnd="
         + this.getCreateDatetimeEnd()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", renewChannel="
         + this.getRenewChannel()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }
}
