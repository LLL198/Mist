package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Generated;

public class PointsBotRedPacketResponse implements Serializable {
   private Long id;
   private Long chatId;
   private Long messageId;
   private Long creatorUserId;
   private String creatorUsername;
   private String creatorDisplayName;
   private String greeting;
   private Integer totalPoints;
   private Integer totalCount;
   private Integer claimedCount;
   private Integer remainingPoints;
   private Integer remainingCount;
   private String status;
   private LocalDateTime publishedAt;
   private LocalDateTime expiresAt;
   private LocalDateTime finishedAt;
   private Integer refundedPoints;
   private LocalDateTime refundedAt;
   private Date createDatetime;
   private Date updateDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getMessageId() {
      return this.messageId;
   }

   @Generated
   public Long getCreatorUserId() {
      return this.creatorUserId;
   }

   @Generated
   public String getCreatorUsername() {
      return this.creatorUsername;
   }

   @Generated
   public String getCreatorDisplayName() {
      return this.creatorDisplayName;
   }

   @Generated
   public String getGreeting() {
      return this.greeting;
   }

   @Generated
   public Integer getTotalPoints() {
      return this.totalPoints;
   }

   @Generated
   public Integer getTotalCount() {
      return this.totalCount;
   }

   @Generated
   public Integer getClaimedCount() {
      return this.claimedCount;
   }

   @Generated
   public Integer getRemainingPoints() {
      return this.remainingPoints;
   }

   @Generated
   public Integer getRemainingCount() {
      return this.remainingCount;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public LocalDateTime getPublishedAt() {
      return this.publishedAt;
   }

   @Generated
   public LocalDateTime getExpiresAt() {
      return this.expiresAt;
   }

   @Generated
   public LocalDateTime getFinishedAt() {
      return this.finishedAt;
   }

   @Generated
   public Integer getRefundedPoints() {
      return this.refundedPoints;
   }

   @Generated
   public LocalDateTime getRefundedAt() {
      return this.refundedAt;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setMessageId(final Long messageId) {
      this.messageId = messageId;
   }

   @Generated
   public void setCreatorUserId(final Long creatorUserId) {
      this.creatorUserId = creatorUserId;
   }

   @Generated
   public void setCreatorUsername(final String creatorUsername) {
      this.creatorUsername = creatorUsername;
   }

   @Generated
   public void setCreatorDisplayName(final String creatorDisplayName) {
      this.creatorDisplayName = creatorDisplayName;
   }

   @Generated
   public void setGreeting(final String greeting) {
      this.greeting = greeting;
   }

   @Generated
   public void setTotalPoints(final Integer totalPoints) {
      this.totalPoints = totalPoints;
   }

   @Generated
   public void setTotalCount(final Integer totalCount) {
      this.totalCount = totalCount;
   }

   @Generated
   public void setClaimedCount(final Integer claimedCount) {
      this.claimedCount = claimedCount;
   }

   @Generated
   public void setRemainingPoints(final Integer remainingPoints) {
      this.remainingPoints = remainingPoints;
   }

   @Generated
   public void setRemainingCount(final Integer remainingCount) {
      this.remainingCount = remainingCount;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setPublishedAt(final LocalDateTime publishedAt) {
      this.publishedAt = publishedAt;
   }

   @Generated
   public void setExpiresAt(final LocalDateTime expiresAt) {
      this.expiresAt = expiresAt;
   }

   @Generated
   public void setFinishedAt(final LocalDateTime finishedAt) {
      this.finishedAt = finishedAt;
   }

   @Generated
   public void setRefundedPoints(final Integer refundedPoints) {
      this.refundedPoints = refundedPoints;
   }

   @Generated
   public void setRefundedAt(final LocalDateTime refundedAt) {
      this.refundedAt = refundedAt;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotRedPacketResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$messageId = this.getMessageId();
               Object other$messageId = other.getMessageId();
               if (this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId)) {
                  Object this$creatorUserId = this.getCreatorUserId();
                  Object other$creatorUserId = other.getCreatorUserId();
                  if (this$creatorUserId == null ? other$creatorUserId == null : this$creatorUserId.equals(other$creatorUserId)) {
                     Object this$totalPoints = this.getTotalPoints();
                     Object other$totalPoints = other.getTotalPoints();
                     if (this$totalPoints == null ? other$totalPoints == null : this$totalPoints.equals(other$totalPoints)) {
                        Object this$totalCount = this.getTotalCount();
                        Object other$totalCount = other.getTotalCount();
                        if (this$totalCount == null ? other$totalCount == null : this$totalCount.equals(other$totalCount)) {
                           Object this$claimedCount = this.getClaimedCount();
                           Object other$claimedCount = other.getClaimedCount();
                           if (this$claimedCount == null ? other$claimedCount == null : this$claimedCount.equals(other$claimedCount)) {
                              Object this$remainingPoints = this.getRemainingPoints();
                              Object other$remainingPoints = other.getRemainingPoints();
                              if (this$remainingPoints == null ? other$remainingPoints == null : this$remainingPoints.equals(other$remainingPoints)) {
                                 Object this$remainingCount = this.getRemainingCount();
                                 Object other$remainingCount = other.getRemainingCount();
                                 if (this$remainingCount == null ? other$remainingCount == null : this$remainingCount.equals(other$remainingCount)) {
                                    Object this$refundedPoints = this.getRefundedPoints();
                                    Object other$refundedPoints = other.getRefundedPoints();
                                    if (this$refundedPoints == null ? other$refundedPoints == null : this$refundedPoints.equals(other$refundedPoints)) {
                                       Object this$creatorUsername = this.getCreatorUsername();
                                       Object other$creatorUsername = other.getCreatorUsername();
                                       if (this$creatorUsername == null ? other$creatorUsername == null : this$creatorUsername.equals(other$creatorUsername)) {
                                          Object this$creatorDisplayName = this.getCreatorDisplayName();
                                          Object other$creatorDisplayName = other.getCreatorDisplayName();
                                          if (this$creatorDisplayName == null
                                             ? other$creatorDisplayName == null
                                             : this$creatorDisplayName.equals(other$creatorDisplayName)) {
                                             Object this$greeting = this.getGreeting();
                                             Object other$greeting = other.getGreeting();
                                             if (this$greeting == null ? other$greeting == null : this$greeting.equals(other$greeting)) {
                                                Object this$status = this.getStatus();
                                                Object other$status = other.getStatus();
                                                if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                   Object this$publishedAt = this.getPublishedAt();
                                                   Object other$publishedAt = other.getPublishedAt();
                                                   if (this$publishedAt == null ? other$publishedAt == null : this$publishedAt.equals(other$publishedAt)) {
                                                      Object this$expiresAt = this.getExpiresAt();
                                                      Object other$expiresAt = other.getExpiresAt();
                                                      if (this$expiresAt == null ? other$expiresAt == null : this$expiresAt.equals(other$expiresAt)) {
                                                         Object this$finishedAt = this.getFinishedAt();
                                                         Object other$finishedAt = other.getFinishedAt();
                                                         if (this$finishedAt == null ? other$finishedAt == null : this$finishedAt.equals(other$finishedAt)) {
                                                            Object this$refundedAt = this.getRefundedAt();
                                                            Object other$refundedAt = other.getRefundedAt();
                                                            if (this$refundedAt == null ? other$refundedAt == null : this$refundedAt.equals(other$refundedAt)) {
                                                               Object this$createDatetime = this.getCreateDatetime();
                                                               Object other$createDatetime = other.getCreateDatetime();
                                                               if (this$createDatetime == null
                                                                  ? other$createDatetime == null
                                                                  : this$createDatetime.equals(other$createDatetime)) {
                                                                  Object this$updateDatetime = this.getUpdateDatetime();
                                                                  Object other$updateDatetime = other.getUpdateDatetime();
                                                                  return this$updateDatetime == null
                                                                     ? other$updateDatetime == null
                                                                     : this$updateDatetime.equals(other$updateDatetime);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotRedPacketResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $messageId = this.getMessageId();
      result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
      Object $creatorUserId = this.getCreatorUserId();
      result = result * 59 + ($creatorUserId == null ? 43 : $creatorUserId.hashCode());
      Object $totalPoints = this.getTotalPoints();
      result = result * 59 + ($totalPoints == null ? 43 : $totalPoints.hashCode());
      Object $totalCount = this.getTotalCount();
      result = result * 59 + ($totalCount == null ? 43 : $totalCount.hashCode());
      Object $claimedCount = this.getClaimedCount();
      result = result * 59 + ($claimedCount == null ? 43 : $claimedCount.hashCode());
      Object $remainingPoints = this.getRemainingPoints();
      result = result * 59 + ($remainingPoints == null ? 43 : $remainingPoints.hashCode());
      Object $remainingCount = this.getRemainingCount();
      result = result * 59 + ($remainingCount == null ? 43 : $remainingCount.hashCode());
      Object $refundedPoints = this.getRefundedPoints();
      result = result * 59 + ($refundedPoints == null ? 43 : $refundedPoints.hashCode());
      Object $creatorUsername = this.getCreatorUsername();
      result = result * 59 + ($creatorUsername == null ? 43 : $creatorUsername.hashCode());
      Object $creatorDisplayName = this.getCreatorDisplayName();
      result = result * 59 + ($creatorDisplayName == null ? 43 : $creatorDisplayName.hashCode());
      Object $greeting = this.getGreeting();
      result = result * 59 + ($greeting == null ? 43 : $greeting.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $publishedAt = this.getPublishedAt();
      result = result * 59 + ($publishedAt == null ? 43 : $publishedAt.hashCode());
      Object $expiresAt = this.getExpiresAt();
      result = result * 59 + ($expiresAt == null ? 43 : $expiresAt.hashCode());
      Object $finishedAt = this.getFinishedAt();
      result = result * 59 + ($finishedAt == null ? 43 : $finishedAt.hashCode());
      Object $refundedAt = this.getRefundedAt();
      result = result * 59 + ($refundedAt == null ? 43 : $refundedAt.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotRedPacketResponse(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", messageId="
         + this.getMessageId()
         + ", creatorUserId="
         + this.getCreatorUserId()
         + ", creatorUsername="
         + this.getCreatorUsername()
         + ", creatorDisplayName="
         + this.getCreatorDisplayName()
         + ", greeting="
         + this.getGreeting()
         + ", totalPoints="
         + this.getTotalPoints()
         + ", totalCount="
         + this.getTotalCount()
         + ", claimedCount="
         + this.getClaimedCount()
         + ", remainingPoints="
         + this.getRemainingPoints()
         + ", remainingCount="
         + this.getRemainingCount()
         + ", status="
         + this.getStatus()
         + ", publishedAt="
         + this.getPublishedAt()
         + ", expiresAt="
         + this.getExpiresAt()
         + ", finishedAt="
         + this.getFinishedAt()
         + ", refundedPoints="
         + this.getRefundedPoints()
         + ", refundedAt="
         + this.getRefundedAt()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
