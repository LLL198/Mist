package com.una.embyhub.model.dto.request.distributionapplication;

import java.io.Serializable;
import lombok.Generated;

public class ProductListRequest implements Serializable {
   private String name;
   private Integer isEnabled;
   private String productType;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public Integer getIsEnabled() {
      return this.isEnabled;
   }

   @Generated
   public String getProductType() {
      return this.productType;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setIsEnabled(final Integer isEnabled) {
      this.isEnabled = isEnabled;
   }

   @Generated
   public void setProductType(final String productType) {
      this.productType = productType;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ProductListRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$isEnabled = this.getIsEnabled();
         Object other$isEnabled = other.getIsEnabled();
         if (this$isEnabled == null ? other$isEnabled == null : this$isEnabled.equals(other$isEnabled)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof ProductListRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $isEnabled = this.getIsEnabled();
      result = result * 59 + ($isEnabled == null ? 43 : $isEnabled.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $productType = this.getProductType();
      return result * 59 + ($productType == null ? 43 : $productType.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ProductListRequest(name=" + this.getName() + ", isEnabled=" + this.getIsEnabled() + ", productType=" + this.getProductType() + ")";
   }
}
