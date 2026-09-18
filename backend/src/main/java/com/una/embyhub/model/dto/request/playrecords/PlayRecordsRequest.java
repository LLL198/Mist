package com.una.embyhub.model.dto.request.playrecords;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PlayRecordsRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.BETWEEN_BEGIN,
      column = "play_date"
   )
   private Date playDateStart;
   @BindQuery(
      comparison = Comparison.BETWEEN_END,
      column = "play_date"
   )
   private Date playDateEnd;
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   private String embyUserName;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long embyInfoId;

   @Generated
   public Date getPlayDateStart() {
      return this.playDateStart;
   }

   @Generated
   public Date getPlayDateEnd() {
      return this.playDateEnd;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setPlayDateStart(final Date playDateStart) {
      this.playDateStart = playDateStart;
   }

   @Generated
   public void setPlayDateEnd(final Date playDateEnd) {
      this.playDateEnd = playDateEnd;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
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
      } else if (!(o instanceof PlayRecordsRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$playDateStart = this.getPlayDateStart();
            Object other$playDateStart = other.getPlayDateStart();
            if (this$playDateStart == null ? other$playDateStart == null : this$playDateStart.equals(other$playDateStart)) {
               Object this$playDateEnd = this.getPlayDateEnd();
               Object other$playDateEnd = other.getPlayDateEnd();
               if (this$playDateEnd == null ? other$playDateEnd == null : this$playDateEnd.equals(other$playDateEnd)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PlayRecordsRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $playDateStart = this.getPlayDateStart();
      result = result * 59 + ($playDateStart == null ? 43 : $playDateStart.hashCode());
      Object $playDateEnd = this.getPlayDateEnd();
      result = result * 59 + ($playDateEnd == null ? 43 : $playDateEnd.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PlayRecordsRequest(playDateStart="
         + this.getPlayDateStart()
         + ", playDateEnd="
         + this.getPlayDateEnd()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }
}
