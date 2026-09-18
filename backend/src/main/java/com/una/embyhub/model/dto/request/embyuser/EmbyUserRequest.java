package com.una.embyhub.model.dto.request.embyuser;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyUserRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String embyUserId;
   @BindQuery(
      comparison = Comparison.STARTSWITH
   )
   private String embyUserName;
   @BindQuery(
      ignore = true
   )
   private Integer userStatus;
   @BindQuery(
      comparison = Comparison.BETWEEN_BEGIN,
      column = "expiration_date"
   )
   private Date expirationDateStart;
   @BindQuery(
      comparison = Comparison.BETWEEN_END,
      column = "expiration_date"
   )
   private Date expirationDateEnd;
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   private String remarks;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long embyInfoId;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer hostLineType;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer registerChannel;
   @BindQuery(
      ignore = true
   )
   private Integer telegramBound;

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Integer getUserStatus() {
      return this.userStatus;
   }

   @Generated
   public Date getExpirationDateStart() {
      return this.expirationDateStart;
   }

   @Generated
   public Date getExpirationDateEnd() {
      return this.expirationDateEnd;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public Integer getRegisterChannel() {
      return this.registerChannel;
   }

   @Generated
   public Integer getTelegramBound() {
      return this.telegramBound;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setUserStatus(final Integer userStatus) {
      this.userStatus = userStatus;
   }

   @Generated
   public void setExpirationDateStart(final Date expirationDateStart) {
      this.expirationDateStart = expirationDateStart;
   }

   @Generated
   public void setExpirationDateEnd(final Date expirationDateEnd) {
      this.expirationDateEnd = expirationDateEnd;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setRegisterChannel(final Integer registerChannel) {
      this.registerChannel = registerChannel;
   }

   @Generated
   public void setTelegramBound(final Integer telegramBound) {
      this.telegramBound = telegramBound;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userStatus = this.getUserStatus();
         Object other$userStatus = other.getUserStatus();
         if (this$userStatus == null ? other$userStatus == null : this$userStatus.equals(other$userStatus)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$hostLineType = this.getHostLineType();
               Object other$hostLineType = other.getHostLineType();
               if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                  Object this$registerChannel = this.getRegisterChannel();
                  Object other$registerChannel = other.getRegisterChannel();
                  if (this$registerChannel == null ? other$registerChannel == null : this$registerChannel.equals(other$registerChannel)) {
                     Object this$telegramBound = this.getTelegramBound();
                     Object other$telegramBound = other.getTelegramBound();
                     if (this$telegramBound == null ? other$telegramBound == null : this$telegramBound.equals(other$telegramBound)) {
                        Object this$embyUserId = this.getEmbyUserId();
                        Object other$embyUserId = other.getEmbyUserId();
                        if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                           Object this$embyUserName = this.getEmbyUserName();
                           Object other$embyUserName = other.getEmbyUserName();
                           if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                              Object this$expirationDateStart = this.getExpirationDateStart();
                              Object other$expirationDateStart = other.getExpirationDateStart();
                              if (this$expirationDateStart == null
                                 ? other$expirationDateStart == null
                                 : this$expirationDateStart.equals(other$expirationDateStart)) {
                                 Object this$expirationDateEnd = this.getExpirationDateEnd();
                                 Object other$expirationDateEnd = other.getExpirationDateEnd();
                                 if (this$expirationDateEnd == null ? other$expirationDateEnd == null : this$expirationDateEnd.equals(other$expirationDateEnd)) {
                                    Object this$remarks = this.getRemarks();
                                    Object other$remarks = other.getRemarks();
                                    return this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks);
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
      return other instanceof EmbyUserRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userStatus = this.getUserStatus();
      result = result * 59 + ($userStatus == null ? 43 : $userStatus.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $registerChannel = this.getRegisterChannel();
      result = result * 59 + ($registerChannel == null ? 43 : $registerChannel.hashCode());
      Object $telegramBound = this.getTelegramBound();
      result = result * 59 + ($telegramBound == null ? 43 : $telegramBound.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $expirationDateStart = this.getExpirationDateStart();
      result = result * 59 + ($expirationDateStart == null ? 43 : $expirationDateStart.hashCode());
      Object $expirationDateEnd = this.getExpirationDateEnd();
      result = result * 59 + ($expirationDateEnd == null ? 43 : $expirationDateEnd.hashCode());
      Object $remarks = this.getRemarks();
      return result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserRequest(embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", userStatus="
         + this.getUserStatus()
         + ", expirationDateStart="
         + this.getExpirationDateStart()
         + ", expirationDateEnd="
         + this.getExpirationDateEnd()
         + ", remarks="
         + this.getRemarks()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", registerChannel="
         + this.getRegisterChannel()
         + ", telegramBound="
         + this.getTelegramBound()
         + ")";
   }
}
