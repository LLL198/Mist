package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class EmbyUserTelegramGroupCheckResponse implements Serializable {
   private Integer totalCount = 0;
   private Integer inGroupCount = 0;
   private Integer notInGroupCount = 0;
   private Integer unboundCount = 0;
   private Integer failedCount = 0;
   private List<EmbyUserTelegramGroupCheckResponse.Result> results = new ArrayList<>();

   @Generated
   public Integer getTotalCount() {
      return this.totalCount;
   }

   @Generated
   public Integer getInGroupCount() {
      return this.inGroupCount;
   }

   @Generated
   public Integer getNotInGroupCount() {
      return this.notInGroupCount;
   }

   @Generated
   public Integer getUnboundCount() {
      return this.unboundCount;
   }

   @Generated
   public Integer getFailedCount() {
      return this.failedCount;
   }

   @Generated
   public List<EmbyUserTelegramGroupCheckResponse.Result> getResults() {
      return this.results;
   }

   @Generated
   public void setTotalCount(final Integer totalCount) {
      this.totalCount = totalCount;
   }

   @Generated
   public void setInGroupCount(final Integer inGroupCount) {
      this.inGroupCount = inGroupCount;
   }

   @Generated
   public void setNotInGroupCount(final Integer notInGroupCount) {
      this.notInGroupCount = notInGroupCount;
   }

   @Generated
   public void setUnboundCount(final Integer unboundCount) {
      this.unboundCount = unboundCount;
   }

   @Generated
   public void setFailedCount(final Integer failedCount) {
      this.failedCount = failedCount;
   }

   @Generated
   public void setResults(final List<EmbyUserTelegramGroupCheckResponse.Result> results) {
      this.results = results;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserTelegramGroupCheckResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalCount = this.getTotalCount();
         Object other$totalCount = other.getTotalCount();
         if (this$totalCount == null ? other$totalCount == null : this$totalCount.equals(other$totalCount)) {
            Object this$inGroupCount = this.getInGroupCount();
            Object other$inGroupCount = other.getInGroupCount();
            if (this$inGroupCount == null ? other$inGroupCount == null : this$inGroupCount.equals(other$inGroupCount)) {
               Object this$notInGroupCount = this.getNotInGroupCount();
               Object other$notInGroupCount = other.getNotInGroupCount();
               if (this$notInGroupCount == null ? other$notInGroupCount == null : this$notInGroupCount.equals(other$notInGroupCount)) {
                  Object this$unboundCount = this.getUnboundCount();
                  Object other$unboundCount = other.getUnboundCount();
                  if (this$unboundCount == null ? other$unboundCount == null : this$unboundCount.equals(other$unboundCount)) {
                     Object this$failedCount = this.getFailedCount();
                     Object other$failedCount = other.getFailedCount();
                     if (this$failedCount == null ? other$failedCount == null : this$failedCount.equals(other$failedCount)) {
                        Object this$results = this.getResults();
                        Object other$results = other.getResults();
                        return this$results == null ? other$results == null : this$results.equals(other$results);
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
      return other instanceof EmbyUserTelegramGroupCheckResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalCount = this.getTotalCount();
      result = result * 59 + ($totalCount == null ? 43 : $totalCount.hashCode());
      Object $inGroupCount = this.getInGroupCount();
      result = result * 59 + ($inGroupCount == null ? 43 : $inGroupCount.hashCode());
      Object $notInGroupCount = this.getNotInGroupCount();
      result = result * 59 + ($notInGroupCount == null ? 43 : $notInGroupCount.hashCode());
      Object $unboundCount = this.getUnboundCount();
      result = result * 59 + ($unboundCount == null ? 43 : $unboundCount.hashCode());
      Object $failedCount = this.getFailedCount();
      result = result * 59 + ($failedCount == null ? 43 : $failedCount.hashCode());
      Object $results = this.getResults();
      return result * 59 + ($results == null ? 43 : $results.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserTelegramGroupCheckResponse(totalCount="
         + this.getTotalCount()
         + ", inGroupCount="
         + this.getInGroupCount()
         + ", notInGroupCount="
         + this.getNotInGroupCount()
         + ", unboundCount="
         + this.getUnboundCount()
         + ", failedCount="
         + this.getFailedCount()
         + ", results="
         + this.getResults()
         + ")";
   }

   public static class Result implements Serializable {
      public static final String STATUS_IN_GROUP = "IN_GROUP";
      public static final String STATUS_NOT_IN_GROUP = "NOT_IN_GROUP";
      public static final String STATUS_UNBOUND = "UNBOUND";
      public static final String STATUS_CHECK_FAILED = "CHECK_FAILED";
      private Long userId;
      private String embyUserName;
      private String telegramUserId;
      private String telegramUsername;
      private String status;
      private String statusName;
      private String message;

      @Generated
      public Long getUserId() {
         return this.userId;
      }

      @Generated
      public String getEmbyUserName() {
         return this.embyUserName;
      }

      @Generated
      public String getTelegramUserId() {
         return this.telegramUserId;
      }

      @Generated
      public String getTelegramUsername() {
         return this.telegramUsername;
      }

      @Generated
      public String getStatus() {
         return this.status;
      }

      @Generated
      public String getStatusName() {
         return this.statusName;
      }

      @Generated
      public String getMessage() {
         return this.message;
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
      public void setTelegramUserId(final String telegramUserId) {
         this.telegramUserId = telegramUserId;
      }

      @Generated
      public void setTelegramUsername(final String telegramUsername) {
         this.telegramUsername = telegramUsername;
      }

      @Generated
      public void setStatus(final String status) {
         this.status = status;
      }

      @Generated
      public void setStatusName(final String statusName) {
         this.statusName = statusName;
      }

      @Generated
      public void setMessage(final String message) {
         this.message = message;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof EmbyUserTelegramGroupCheckResponse.Result other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$embyUserName = this.getEmbyUserName();
               Object other$embyUserName = other.getEmbyUserName();
               if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                  Object this$telegramUserId = this.getTelegramUserId();
                  Object other$telegramUserId = other.getTelegramUserId();
                  if (this$telegramUserId == null ? other$telegramUserId == null : this$telegramUserId.equals(other$telegramUserId)) {
                     Object this$telegramUsername = this.getTelegramUsername();
                     Object other$telegramUsername = other.getTelegramUsername();
                     if (this$telegramUsername == null ? other$telegramUsername == null : this$telegramUsername.equals(other$telegramUsername)) {
                        Object this$status = this.getStatus();
                        Object other$status = other.getStatus();
                        if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                           Object this$statusName = this.getStatusName();
                           Object other$statusName = other.getStatusName();
                           if (this$statusName == null ? other$statusName == null : this$statusName.equals(other$statusName)) {
                              Object this$message = this.getMessage();
                              Object other$message = other.getMessage();
                              return this$message == null ? other$message == null : this$message.equals(other$message);
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
         return other instanceof EmbyUserTelegramGroupCheckResponse.Result;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $userId = this.getUserId();
         result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
         Object $embyUserName = this.getEmbyUserName();
         result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
         Object $telegramUserId = this.getTelegramUserId();
         result = result * 59 + ($telegramUserId == null ? 43 : $telegramUserId.hashCode());
         Object $telegramUsername = this.getTelegramUsername();
         result = result * 59 + ($telegramUsername == null ? 43 : $telegramUsername.hashCode());
         Object $status = this.getStatus();
         result = result * 59 + ($status == null ? 43 : $status.hashCode());
         Object $statusName = this.getStatusName();
         result = result * 59 + ($statusName == null ? 43 : $statusName.hashCode());
         Object $message = this.getMessage();
         return result * 59 + ($message == null ? 43 : $message.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "EmbyUserTelegramGroupCheckResponse.Result(userId="
            + this.getUserId()
            + ", embyUserName="
            + this.getEmbyUserName()
            + ", telegramUserId="
            + this.getTelegramUserId()
            + ", telegramUsername="
            + this.getTelegramUsername()
            + ", status="
            + this.getStatus()
            + ", statusName="
            + this.getStatusName()
            + ", message="
            + this.getMessage()
            + ")";
      }
   }
}
