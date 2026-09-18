package com.una.embyhub.model.dto.request.embyclientfilter;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyClientFilterRecordRequest implements Serializable {
   private String keyword;
   private String embyUserName;
   private String clientName;
   private String event;
   private String filterType;
   private Integer blockUserSuccess;
   private Integer stopSuccess;
   private Long embyInfoId;
   private Date triggerTimeStart;
   private Date triggerTimeEnd;

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getClientName() {
      return this.clientName;
   }

   @Generated
   public String getEvent() {
      return this.event;
   }

   @Generated
   public String getFilterType() {
      return this.filterType;
   }

   @Generated
   public Integer getBlockUserSuccess() {
      return this.blockUserSuccess;
   }

   @Generated
   public Integer getStopSuccess() {
      return this.stopSuccess;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Date getTriggerTimeStart() {
      return this.triggerTimeStart;
   }

   @Generated
   public Date getTriggerTimeEnd() {
      return this.triggerTimeEnd;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setClientName(final String clientName) {
      this.clientName = clientName;
   }

   @Generated
   public void setEvent(final String event) {
      this.event = event;
   }

   @Generated
   public void setFilterType(final String filterType) {
      this.filterType = filterType;
   }

   @Generated
   public void setBlockUserSuccess(final Integer blockUserSuccess) {
      this.blockUserSuccess = blockUserSuccess;
   }

   @Generated
   public void setStopSuccess(final Integer stopSuccess) {
      this.stopSuccess = stopSuccess;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setTriggerTimeStart(final Date triggerTimeStart) {
      this.triggerTimeStart = triggerTimeStart;
   }

   @Generated
   public void setTriggerTimeEnd(final Date triggerTimeEnd) {
      this.triggerTimeEnd = triggerTimeEnd;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyClientFilterRecordRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$blockUserSuccess = this.getBlockUserSuccess();
         Object other$blockUserSuccess = other.getBlockUserSuccess();
         if (this$blockUserSuccess == null ? other$blockUserSuccess == null : this$blockUserSuccess.equals(other$blockUserSuccess)) {
            Object this$stopSuccess = this.getStopSuccess();
            Object other$stopSuccess = other.getStopSuccess();
            if (this$stopSuccess == null ? other$stopSuccess == null : this$stopSuccess.equals(other$stopSuccess)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                  Object this$keyword = this.getKeyword();
                  Object other$keyword = other.getKeyword();
                  if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                     Object this$embyUserName = this.getEmbyUserName();
                     Object other$embyUserName = other.getEmbyUserName();
                     if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                        Object this$clientName = this.getClientName();
                        Object other$clientName = other.getClientName();
                        if (this$clientName == null ? other$clientName == null : this$clientName.equals(other$clientName)) {
                           Object this$event = this.getEvent();
                           Object other$event = other.getEvent();
                           if (this$event == null ? other$event == null : this$event.equals(other$event)) {
                              Object this$filterType = this.getFilterType();
                              Object other$filterType = other.getFilterType();
                              if (this$filterType == null ? other$filterType == null : this$filterType.equals(other$filterType)) {
                                 Object this$triggerTimeStart = this.getTriggerTimeStart();
                                 Object other$triggerTimeStart = other.getTriggerTimeStart();
                                 if (this$triggerTimeStart == null ? other$triggerTimeStart == null : this$triggerTimeStart.equals(other$triggerTimeStart)) {
                                    Object this$triggerTimeEnd = this.getTriggerTimeEnd();
                                    Object other$triggerTimeEnd = other.getTriggerTimeEnd();
                                    return this$triggerTimeEnd == null ? other$triggerTimeEnd == null : this$triggerTimeEnd.equals(other$triggerTimeEnd);
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
      return other instanceof EmbyClientFilterRecordRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $blockUserSuccess = this.getBlockUserSuccess();
      result = result * 59 + ($blockUserSuccess == null ? 43 : $blockUserSuccess.hashCode());
      Object $stopSuccess = this.getStopSuccess();
      result = result * 59 + ($stopSuccess == null ? 43 : $stopSuccess.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $clientName = this.getClientName();
      result = result * 59 + ($clientName == null ? 43 : $clientName.hashCode());
      Object $event = this.getEvent();
      result = result * 59 + ($event == null ? 43 : $event.hashCode());
      Object $filterType = this.getFilterType();
      result = result * 59 + ($filterType == null ? 43 : $filterType.hashCode());
      Object $triggerTimeStart = this.getTriggerTimeStart();
      result = result * 59 + ($triggerTimeStart == null ? 43 : $triggerTimeStart.hashCode());
      Object $triggerTimeEnd = this.getTriggerTimeEnd();
      return result * 59 + ($triggerTimeEnd == null ? 43 : $triggerTimeEnd.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyClientFilterRecordRequest(keyword="
         + this.getKeyword()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", clientName="
         + this.getClientName()
         + ", event="
         + this.getEvent()
         + ", filterType="
         + this.getFilterType()
         + ", blockUserSuccess="
         + this.getBlockUserSuccess()
         + ", stopSuccess="
         + this.getStopSuccess()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", triggerTimeStart="
         + this.getTriggerTimeStart()
         + ", triggerTimeEnd="
         + this.getTriggerTimeEnd()
         + ")";
   }
}
