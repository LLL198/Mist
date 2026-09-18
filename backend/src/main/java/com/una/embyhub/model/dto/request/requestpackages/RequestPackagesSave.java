package com.una.embyhub.model.dto.request.requestpackages;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Generated;

public class RequestPackagesSave implements Serializable {
   private String title;
   private String description;
   private Integer count;
   private String icon;
   private BigDecimal amount;

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public Integer getCount() {
      return this.count;
   }

   @Generated
   public String getIcon() {
      return this.icon;
   }

   @Generated
   public BigDecimal getAmount() {
      return this.amount;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setCount(final Integer count) {
      this.count = count;
   }

   @Generated
   public void setIcon(final String icon) {
      this.icon = icon;
   }

   @Generated
   public void setAmount(final BigDecimal amount) {
      this.amount = amount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestPackagesSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$count = this.getCount();
         Object other$count = other.getCount();
         if (this$count == null ? other$count == null : this$count.equals(other$count)) {
            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null ? other$title == null : this$title.equals(other$title)) {
               Object this$description = this.getDescription();
               Object other$description = other.getDescription();
               if (this$description == null ? other$description == null : this$description.equals(other$description)) {
                  Object this$icon = this.getIcon();
                  Object other$icon = other.getIcon();
                  if (this$icon == null ? other$icon == null : this$icon.equals(other$icon)) {
                     Object this$amount = this.getAmount();
                     Object other$amount = other.getAmount();
                     return this$amount == null ? other$amount == null : this$amount.equals(other$amount);
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
      return other instanceof RequestPackagesSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $count = this.getCount();
      result = result * 59 + ($count == null ? 43 : $count.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
      Object $icon = this.getIcon();
      result = result * 59 + ($icon == null ? 43 : $icon.hashCode());
      Object $amount = this.getAmount();
      return result * 59 + ($amount == null ? 43 : $amount.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestPackagesSave(title="
         + this.getTitle()
         + ", description="
         + this.getDescription()
         + ", count="
         + this.getCount()
         + ", icon="
         + this.getIcon()
         + ", amount="
         + this.getAmount()
         + ")";
   }
}
