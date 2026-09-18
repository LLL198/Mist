package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Generated;

@TableName("request_packages")
public class RequestPackages extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("title")
   private String title;
   @TableField("description")
   private String description;
   @TableField("`count`")
   private Integer count;
   @TableField("icon")
   private String icon;
   @TableField("amount")
   private BigDecimal amount;
   public static final String COL_ID = "id";
   public static final String COL_TITLE = "title";
   public static final String COL_DESCRIPTION = "description";
   public static final String COL_COUNT = "count";
   public static final String COL_ICON = "icon";
   public static final String COL_AMOUNT = "amount";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";

   @Generated
   public Long getId() {
      return this.id;
   }

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
   public void setId(final Long id) {
      this.id = id;
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
   public String toString() {
      return "RequestPackages(id="
         + this.getId()
         + ", title="
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

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestPackages other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
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
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof RequestPackages;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
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
}
