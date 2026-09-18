package com.una.embyhub.model.dto.request.distributionapplication;

import java.io.Serializable;
import lombok.Generated;

public class ProductSaveRequest implements Serializable {
   private Long id;
   private String name;
   private String productType;
   private Integer pointsCost;
   private Integer productValue;
   private Long embyInfoId;
   private Integer isEnabled;
   private Integer sortOrder;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getProductType() {
      return this.productType;
   }

   @Generated
   public Integer getPointsCost() {
      return this.pointsCost;
   }

   @Generated
   public Integer getProductValue() {
      return this.productValue;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getIsEnabled() {
      return this.isEnabled;
   }

   @Generated
   public Integer getSortOrder() {
      return this.sortOrder;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setProductType(final String productType) {
      this.productType = productType;
   }

   @Generated
   public void setPointsCost(final Integer pointsCost) {
      this.pointsCost = pointsCost;
   }

   @Generated
   public void setProductValue(final Integer productValue) {
      this.productValue = productValue;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setIsEnabled(final Integer isEnabled) {
      this.isEnabled = isEnabled;
   }

   @Generated
   public void setSortOrder(final Integer sortOrder) {
      this.sortOrder = sortOrder;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ProductSaveRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$pointsCost = this.getPointsCost();
            Object other$pointsCost = other.getPointsCost();
            if (this$pointsCost == null ? other$pointsCost == null : this$pointsCost.equals(other$pointsCost)) {
               Object this$productValue = this.getProductValue();
               Object other$productValue = other.getProductValue();
               if (this$productValue == null ? other$productValue == null : this$productValue.equals(other$productValue)) {
                  Object this$embyInfoId = this.getEmbyInfoId();
                  Object other$embyInfoId = other.getEmbyInfoId();
                  if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                     Object this$isEnabled = this.getIsEnabled();
                     Object other$isEnabled = other.getIsEnabled();
                     if (this$isEnabled == null ? other$isEnabled == null : this$isEnabled.equals(other$isEnabled)) {
                        Object this$sortOrder = this.getSortOrder();
                        Object other$sortOrder = other.getSortOrder();
                        if (this$sortOrder == null ? other$sortOrder == null : this$sortOrder.equals(other$sortOrder)) {
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                              Object this$productType = this.getProductType();
                              Object other$productType = other.getProductType();
                              return this$productType == null ? other$productType == null : this$productType.equals(other$productType);
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
      return other instanceof ProductSaveRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $pointsCost = this.getPointsCost();
      result = result * 59 + ($pointsCost == null ? 43 : $pointsCost.hashCode());
      Object $productValue = this.getProductValue();
      result = result * 59 + ($productValue == null ? 43 : $productValue.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $isEnabled = this.getIsEnabled();
      result = result * 59 + ($isEnabled == null ? 43 : $isEnabled.hashCode());
      Object $sortOrder = this.getSortOrder();
      result = result * 59 + ($sortOrder == null ? 43 : $sortOrder.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $productType = this.getProductType();
      return result * 59 + ($productType == null ? 43 : $productType.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ProductSaveRequest(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", productType="
         + this.getProductType()
         + ", pointsCost="
         + this.getPointsCost()
         + ", productValue="
         + this.getProductValue()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", isEnabled="
         + this.getIsEnabled()
         + ", sortOrder="
         + this.getSortOrder()
         + ")";
   }
}
