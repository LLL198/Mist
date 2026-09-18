package com.una.embyhub.model.dto.request.embynotifydata;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import lombok.Generated;

public class EmbyNotifyDataRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   private String name;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String productionYear;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String type;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer status;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long embyInfoId;
   @BindQuery(
      comparison = Comparison.CONTAINS
   )
   private String productionCountries;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getProductionYear() {
      return this.productionYear;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getProductionCountries() {
      return this.productionCountries;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setProductionYear(final String productionYear) {
      this.productionYear = productionYear;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setProductionCountries(final String productionCountries) {
      this.productionCountries = productionCountries;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyNotifyDataRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$status = this.getStatus();
         Object other$status = other.getStatus();
         if (this$status == null ? other$status == null : this$status.equals(other$status)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                  Object this$productionYear = this.getProductionYear();
                  Object other$productionYear = other.getProductionYear();
                  if (this$productionYear == null ? other$productionYear == null : this$productionYear.equals(other$productionYear)) {
                     Object this$type = this.getType();
                     Object other$type = other.getType();
                     if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                        Object this$productionCountries = this.getProductionCountries();
                        Object other$productionCountries = other.getProductionCountries();
                        return this$productionCountries == null
                           ? other$productionCountries == null
                           : this$productionCountries.equals(other$productionCountries);
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
      return other instanceof EmbyNotifyDataRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $productionYear = this.getProductionYear();
      result = result * 59 + ($productionYear == null ? 43 : $productionYear.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $productionCountries = this.getProductionCountries();
      return result * 59 + ($productionCountries == null ? 43 : $productionCountries.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyNotifyDataRequest(name="
         + this.getName()
         + ", productionYear="
         + this.getProductionYear()
         + ", type="
         + this.getType()
         + ", status="
         + this.getStatus()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", productionCountries="
         + this.getProductionCountries()
         + ")";
   }
}
