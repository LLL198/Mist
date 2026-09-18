package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("distribution_custom_exchange")
public class DistributionCustomExchange extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("exchange_no")
   private String exchangeNo;
   @TableField("user_id")
   private Long userId;
   @TableField("product_id")
   private Long productId;
   @TableField("product_name")
   private String productName;
   @TableField("product_value")
   private Integer productValue;
   @TableField("points_cost")
   private Integer pointsCost;
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
   public String getExchangeNo() {
      return this.exchangeNo;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Long getProductId() {
      return this.productId;
   }

   @Generated
   public String getProductName() {
      return this.productName;
   }

   @Generated
   public Integer getProductValue() {
      return this.productValue;
   }

   @Generated
   public Integer getPointsCost() {
      return this.pointsCost;
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
   public void setExchangeNo(final String exchangeNo) {
      this.exchangeNo = exchangeNo;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setProductId(final Long productId) {
      this.productId = productId;
   }

   @Generated
   public void setProductName(final String productName) {
      this.productName = productName;
   }

   @Generated
   public void setProductValue(final Integer productValue) {
      this.productValue = productValue;
   }

   @Generated
   public void setPointsCost(final Integer pointsCost) {
      this.pointsCost = pointsCost;
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
      return "DistributionCustomExchange(id="
         + this.getId()
         + ", exchangeNo="
         + this.getExchangeNo()
         + ", userId="
         + this.getUserId()
         + ", productId="
         + this.getProductId()
         + ", productName="
         + this.getProductName()
         + ", productValue="
         + this.getProductValue()
         + ", pointsCost="
         + this.getPointsCost()
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
      } else if (!(o instanceof DistributionCustomExchange other)) {
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
               Object this$productId = this.getProductId();
               Object other$productId = other.getProductId();
               if (this$productId == null ? other$productId == null : this$productId.equals(other$productId)) {
                  Object this$productValue = this.getProductValue();
                  Object other$productValue = other.getProductValue();
                  if (this$productValue == null ? other$productValue == null : this$productValue.equals(other$productValue)) {
                     Object this$pointsCost = this.getPointsCost();
                     Object other$pointsCost = other.getPointsCost();
                     if (this$pointsCost == null ? other$pointsCost == null : this$pointsCost.equals(other$pointsCost)) {
                        Object this$embyInfoId = this.getEmbyInfoId();
                        Object other$embyInfoId = other.getEmbyInfoId();
                        if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                           Object this$status = this.getStatus();
                           Object other$status = other.getStatus();
                           if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                              Object this$exchangeNo = this.getExchangeNo();
                              Object other$exchangeNo = other.getExchangeNo();
                              if (this$exchangeNo == null ? other$exchangeNo == null : this$exchangeNo.equals(other$exchangeNo)) {
                                 Object this$productName = this.getProductName();
                                 Object other$productName = other.getProductName();
                                 if (this$productName == null ? other$productName == null : this$productName.equals(other$productName)) {
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
      return other instanceof DistributionCustomExchange;
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
      Object $productId = this.getProductId();
      result = result * 59 + ($productId == null ? 43 : $productId.hashCode());
      Object $productValue = this.getProductValue();
      result = result * 59 + ($productValue == null ? 43 : $productValue.hashCode());
      Object $pointsCost = this.getPointsCost();
      result = result * 59 + ($pointsCost == null ? 43 : $pointsCost.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $exchangeNo = this.getExchangeNo();
      result = result * 59 + ($exchangeNo == null ? 43 : $exchangeNo.hashCode());
      Object $productName = this.getProductName();
      result = result * 59 + ($productName == null ? 43 : $productName.hashCode());
      Object $reviewComment = this.getReviewComment();
      return result * 59 + ($reviewComment == null ? 43 : $reviewComment.hashCode());
   }
}
