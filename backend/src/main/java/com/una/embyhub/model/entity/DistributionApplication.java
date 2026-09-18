package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("distribution_application")
public class DistributionApplication extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("order_no")
   private String orderNo;
   @TableField("user_id")
   private Long userId;
   @TableField("card_count")
   private Integer cardCount;
   @TableField("card_days")
   private Integer cardDays;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("status")
   private Integer status;
   @TableField("review_comment")
   private String reviewComment;

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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
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
   @Override
   public String toString() {
      return "DistributionApplication(id="
         + this.getId()
         + ", orderNo="
         + this.getOrderNo()
         + ", userId="
         + this.getUserId()
         + ", cardCount="
         + this.getCardCount()
         + ", cardDays="
         + this.getCardDays()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", status="
         + this.getStatus()
         + ", reviewComment="
         + this.getReviewComment()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DistributionApplication other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
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
                     Object this$embyInfoId = this.getEmbyInfoId();
                     Object other$embyInfoId = other.getEmbyInfoId();
                     if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                        Object this$status = this.getStatus();
                        Object other$status = other.getStatus();
                        if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                           Object this$orderNo = this.getOrderNo();
                           Object other$orderNo = other.getOrderNo();
                           if (this$orderNo == null ? other$orderNo == null : this$orderNo.equals(other$orderNo)) {
                              Object this$reviewComment = this.getReviewComment();
                              Object other$reviewComment = other.getReviewComment();
                              return this$reviewComment == null ? other$reviewComment == null : this$reviewComment.equals(other$reviewComment);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof DistributionApplication;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $cardCount = this.getCardCount();
      result = result * 59 + ($cardCount == null ? 43 : $cardCount.hashCode());
      Object $cardDays = this.getCardDays();
      result = result * 59 + ($cardDays == null ? 43 : $cardDays.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $orderNo = this.getOrderNo();
      result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
      Object $reviewComment = this.getReviewComment();
      return result * 59 + ($reviewComment == null ? 43 : $reviewComment.hashCode());
   }
}
