package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("admin_menu_permission")
public class AdminMenuPermission implements Serializable {
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("admin_user_id")
   private Long adminUserId;
   @TableField("menu_key")
   private String menuKey;
   @TableField("create_datetime")
   private Date createDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getAdminUserId() {
      return this.adminUserId;
   }

   @Generated
   public String getMenuKey() {
      return this.menuKey;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setAdminUserId(final Long adminUserId) {
      this.adminUserId = adminUserId;
   }

   @Generated
   public void setMenuKey(final String menuKey) {
      this.menuKey = menuKey;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AdminMenuPermission other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$adminUserId = this.getAdminUserId();
            Object other$adminUserId = other.getAdminUserId();
            if (this$adminUserId == null ? other$adminUserId == null : this$adminUserId.equals(other$adminUserId)) {
               Object this$menuKey = this.getMenuKey();
               Object other$menuKey = other.getMenuKey();
               if (this$menuKey == null ? other$menuKey == null : this$menuKey.equals(other$menuKey)) {
                  Object this$createDatetime = this.getCreateDatetime();
                  Object other$createDatetime = other.getCreateDatetime();
                  return this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime);
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
      return other instanceof AdminMenuPermission;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $adminUserId = this.getAdminUserId();
      result = result * 59 + ($adminUserId == null ? 43 : $adminUserId.hashCode());
      Object $menuKey = this.getMenuKey();
      result = result * 59 + ($menuKey == null ? 43 : $menuKey.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "AdminMenuPermission(id="
         + this.getId()
         + ", adminUserId="
         + this.getAdminUserId()
         + ", menuKey="
         + this.getMenuKey()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
