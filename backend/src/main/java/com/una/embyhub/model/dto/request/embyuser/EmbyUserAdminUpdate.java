package com.una.embyhub.model.dto.request.embyuser;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class EmbyUserAdminUpdate implements ProtectedUserMutationRequest, Serializable {
   @NotNull(
      message = "用户id不能为空"
   )
   private Long userId;
   @NotNull(
      message = "管理员状态不能为空"
   )
   private Integer isAdmin;

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Integer getIsAdmin() {
      return this.isAdmin;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setIsAdmin(final Integer isAdmin) {
      this.isAdmin = isAdmin;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserAdminUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userId = this.getUserId();
         Object other$userId = other.getUserId();
         if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
            Object this$isAdmin = this.getIsAdmin();
            Object other$isAdmin = other.getIsAdmin();
            return this$isAdmin == null ? other$isAdmin == null : this$isAdmin.equals(other$isAdmin);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyUserAdminUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $isAdmin = this.getIsAdmin();
      return result * 59 + ($isAdmin == null ? 43 : $isAdmin.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserAdminUpdate(userId=" + this.getUserId() + ", isAdmin=" + this.getIsAdmin() + ")";
   }
}
