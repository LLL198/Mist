package com.una.embyhub.model.dto.request.adminmenu;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Generated;

public class AdminMenuPermissionUpdateRequest {
   @NotNull
   private List<String> menuKeys;

   @Generated
   public List<String> getMenuKeys() {
      return this.menuKeys;
   }

   @Generated
   public void setMenuKeys(final List<String> menuKeys) {
      this.menuKeys = menuKeys;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AdminMenuPermissionUpdateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$menuKeys = this.getMenuKeys();
         Object other$menuKeys = other.getMenuKeys();
         return this$menuKeys == null ? other$menuKeys == null : this$menuKeys.equals(other$menuKeys);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof AdminMenuPermissionUpdateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $menuKeys = this.getMenuKeys();
      return result * 59 + ($menuKeys == null ? 43 : $menuKeys.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "AdminMenuPermissionUpdateRequest(menuKeys=" + this.getMenuKeys() + ")";
   }
}
