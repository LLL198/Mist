package com.una.embyhub.model.dto.request.telegram;

import java.io.Serializable;
import lombok.Generated;

public class TelegramBindingReviewRequest implements Serializable {
   private String keyword;
   private String reviewUuid;
   private String embyUserName;
   private String telegramUsername;
   private String telegramUserId;
   private String actionType;
   private Integer status;
   private String requestSource;

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public String getReviewUuid() {
      return this.reviewUuid;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getTelegramUsername() {
      return this.telegramUsername;
   }

   @Generated
   public String getTelegramUserId() {
      return this.telegramUserId;
   }

   @Generated
   public String getActionType() {
      return this.actionType;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getRequestSource() {
      return this.requestSource;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setReviewUuid(final String reviewUuid) {
      this.reviewUuid = reviewUuid;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setTelegramUsername(final String telegramUsername) {
      this.telegramUsername = telegramUsername;
   }

   @Generated
   public void setTelegramUserId(final String telegramUserId) {
      this.telegramUserId = telegramUserId;
   }

   @Generated
   public void setActionType(final String actionType) {
      this.actionType = actionType;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setRequestSource(final String requestSource) {
      this.requestSource = requestSource;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBindingReviewRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$status = this.getStatus();
         Object other$status = other.getStatus();
         if (this$status == null ? other$status == null : this$status.equals(other$status)) {
            Object this$keyword = this.getKeyword();
            Object other$keyword = other.getKeyword();
            if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
               Object this$reviewUuid = this.getReviewUuid();
               Object other$reviewUuid = other.getReviewUuid();
               if (this$reviewUuid == null ? other$reviewUuid == null : this$reviewUuid.equals(other$reviewUuid)) {
                  Object this$embyUserName = this.getEmbyUserName();
                  Object other$embyUserName = other.getEmbyUserName();
                  if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                     Object this$telegramUsername = this.getTelegramUsername();
                     Object other$telegramUsername = other.getTelegramUsername();
                     if (this$telegramUsername == null ? other$telegramUsername == null : this$telegramUsername.equals(other$telegramUsername)) {
                        Object this$telegramUserId = this.getTelegramUserId();
                        Object other$telegramUserId = other.getTelegramUserId();
                        if (this$telegramUserId == null ? other$telegramUserId == null : this$telegramUserId.equals(other$telegramUserId)) {
                           Object this$actionType = this.getActionType();
                           Object other$actionType = other.getActionType();
                           if (this$actionType == null ? other$actionType == null : this$actionType.equals(other$actionType)) {
                              Object this$requestSource = this.getRequestSource();
                              Object other$requestSource = other.getRequestSource();
                              return this$requestSource == null ? other$requestSource == null : this$requestSource.equals(other$requestSource);
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
      return other instanceof TelegramBindingReviewRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $reviewUuid = this.getReviewUuid();
      result = result * 59 + ($reviewUuid == null ? 43 : $reviewUuid.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $telegramUsername = this.getTelegramUsername();
      result = result * 59 + ($telegramUsername == null ? 43 : $telegramUsername.hashCode());
      Object $telegramUserId = this.getTelegramUserId();
      result = result * 59 + ($telegramUserId == null ? 43 : $telegramUserId.hashCode());
      Object $actionType = this.getActionType();
      result = result * 59 + ($actionType == null ? 43 : $actionType.hashCode());
      Object $requestSource = this.getRequestSource();
      return result * 59 + ($requestSource == null ? 43 : $requestSource.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindingReviewRequest(keyword="
         + this.getKeyword()
         + ", reviewUuid="
         + this.getReviewUuid()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", telegramUsername="
         + this.getTelegramUsername()
         + ", telegramUserId="
         + this.getTelegramUserId()
         + ", actionType="
         + this.getActionType()
         + ", status="
         + this.getStatus()
         + ", requestSource="
         + this.getRequestSource()
         + ")";
   }
}
