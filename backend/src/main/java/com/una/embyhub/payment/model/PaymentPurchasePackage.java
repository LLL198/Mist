package com.una.embyhub.payment.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.una.embyhub.model.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Generated;

@TableName("payment_purchase_package")
public class PaymentPurchasePackage extends BaseEntity {
   @TableId(
      type = IdType.AUTO
   )
   private Long id;
   private String name;
   private Integer validityDays;
   private BigDecimal price;
   private Long embyInfoId;
   private Integer hostLineType;
   private Integer enabled;
   private Integer sortOrder;
   @TableField(
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String remarks;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public Integer getValidityDays() {
      return this.validityDays;
   }

   @Generated
   public BigDecimal getPrice() {
      return this.price;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public Integer getSortOrder() {
      return this.sortOrder;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
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
   public void setValidityDays(final Integer validityDays) {
      this.validityDays = validityDays;
   }

   @Generated
   public void setPrice(final BigDecimal price) {
      this.price = price;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setSortOrder(final Integer sortOrder) {
      this.sortOrder = sortOrder;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   @Override
   public String toString() {
      return "PaymentPurchasePackage(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", validityDays="
         + this.getValidityDays()
         + ", price="
         + this.getPrice()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", enabled="
         + this.getEnabled()
         + ", sortOrder="
         + this.getSortOrder()
         + ", remarks="
         + this.getRemarks()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PaymentPurchasePackage other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$validityDays = this.getValidityDays();
            Object other$validityDays = other.getValidityDays();
            if (this$validityDays == null ? other$validityDays == null : this$validityDays.equals(other$validityDays)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                  Object this$hostLineType = this.getHostLineType();
                  Object other$hostLineType = other.getHostLineType();
                  if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                     Object this$enabled = this.getEnabled();
                     Object other$enabled = other.getEnabled();
                     if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                        Object this$sortOrder = this.getSortOrder();
                        Object other$sortOrder = other.getSortOrder();
                        if (this$sortOrder == null ? other$sortOrder == null : this$sortOrder.equals(other$sortOrder)) {
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                              Object this$price = this.getPrice();
                              Object other$price = other.getPrice();
                              if (this$price == null ? other$price == null : this$price.equals(other$price)) {
                                 Object this$remarks = this.getRemarks();
                                 Object other$remarks = other.getRemarks();
                                 return this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks);
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
      return other instanceof PaymentPurchasePackage;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $validityDays = this.getValidityDays();
      result = result * 59 + ($validityDays == null ? 43 : $validityDays.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $sortOrder = this.getSortOrder();
      result = result * 59 + ($sortOrder == null ? 43 : $sortOrder.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $price = this.getPrice();
      result = result * 59 + ($price == null ? 43 : $price.hashCode());
      Object $remarks = this.getRemarks();
      return result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
   }
}
