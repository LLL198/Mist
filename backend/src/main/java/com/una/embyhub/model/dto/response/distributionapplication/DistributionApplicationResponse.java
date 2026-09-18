package com.una.embyhub.model.dto.response.distributionapplication;

import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class DistributionApplicationResponse implements Serializable {
   private Long id;
   private String orderNo;
   private Long userId;
   private Integer cardCount;
   private Integer cardDays;
   private Integer status;
   private String reviewComment;
   private Long embyInfoId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;
   @BindField(
      entity = EmbyUser.class,
      field = "embyUserName",
      condition = "this.userId=id"
   )
   private String userName;
   private Date createDatetime;
   private Date updateDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getOrderNo() {
      return this.orderNo;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Integer getCardCount() {
      return this.cardCount;
   }

   @Generated
   public Integer getCardDays() {
      return this.cardDays;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getReviewComment() {
      return this.reviewComment;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public String getUserName() {
      return this.userName;
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
   public void setOrderNo(final String orderNo) {
      this.orderNo = orderNo;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setCardCount(final Integer cardCount) {
      this.cardCount = cardCount;
   }

   @Generated
   public void setCardDays(final Integer cardDays) {
      this.cardDays = cardDays;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setReviewComment(final String reviewComment) {
      this.reviewComment = reviewComment;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
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
      } else if (!(o instanceof DistributionApplicationResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$cardCount = this.getCardCount();
               Object other$cardCount = other.getCardCount();
               if (this$cardCount == null ? other$cardCount == null : this$cardCount.equals(other$cardCount)) {
                  Object this$cardDays = this.getCardDays();
                  Object other$cardDays = other.getCardDays();
                  if (this$cardDays == null ? other$cardDays == null : this$cardDays.equals(other$cardDays)) {
                     Object this$status = this.getStatus();
                     Object other$status = other.getStatus();
                     if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                        Object this$embyInfoId = this.getEmbyInfoId();
                        Object other$embyInfoId = other.getEmbyInfoId();
                        if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                           Object this$orderNo = this.getOrderNo();
                           Object other$orderNo = other.getOrderNo();
                           if (this$orderNo == null ? other$orderNo == null : this$orderNo.equals(other$orderNo)) {
                              Object this$reviewComment = this.getReviewComment();
                              Object other$reviewComment = other.getReviewComment();
                              if (this$reviewComment == null ? other$reviewComment == null : this$reviewComment.equals(other$reviewComment)) {
                                 Object this$serverName = this.getServerName();
                                 Object other$serverName = other.getServerName();
                                 if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                    Object this$userName = this.getUserName();
                                    Object other$userName = other.getUserName();
                                    if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                                       Object this$createDatetime = this.getCreateDatetime();
                                       Object other$createDatetime = other.getCreateDatetime();
                                       if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                          Object this$updateDatetime = this.getUpdateDatetime();
                                          Object other$updateDatetime = other.getUpdateDatetime();
                                          return this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime);
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
      return other instanceof DistributionApplicationResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $cardCount = this.getCardCount();
      result = result * 59 + ($cardCount == null ? 43 : $cardCount.hashCode());
      Object $cardDays = this.getCardDays();
      result = result * 59 + ($cardDays == null ? 43 : $cardDays.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $orderNo = this.getOrderNo();
      result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
      Object $reviewComment = this.getReviewComment();
      result = result * 59 + ($reviewComment == null ? 43 : $reviewComment.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionApplicationResponse(id="
         + this.getId()
         + ", orderNo="
         + this.getOrderNo()
         + ", userId="
         + this.getUserId()
         + ", cardCount="
         + this.getCardCount()
         + ", cardDays="
         + this.getCardDays()
         + ", status="
         + this.getStatus()
         + ", reviewComment="
         + this.getReviewComment()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", userName="
         + this.getUserName()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
