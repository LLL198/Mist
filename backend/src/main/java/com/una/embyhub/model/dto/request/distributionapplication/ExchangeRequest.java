package com.una.embyhub.model.dto.request.distributionapplication;

import java.io.Serializable;
import lombok.Generated;

public class ExchangeRequest implements Serializable {
   private Long productId;

   @Generated
   public Long getProductId() {
      return this.productId;
   }

   @Generated
   public void setProductId(final Long productId) {
      this.productId = productId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ExchangeRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$productId = this.getProductId();
         Object other$productId = other.getProductId();
         return this$productId == null ? other$productId == null : this$productId.equals(other$productId);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof ExchangeRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $productId = this.getProductId();
      return result * 59 + ($productId == null ? 43 : $productId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ExchangeRequest(productId=" + this.getProductId() + ")";
   }
}
