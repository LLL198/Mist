package com.una.embyhub.model.dto.request.embyuser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class EmbyUserTelegramGroupCheckRequest implements Serializable {
   @NotEmpty(
      message = "请选择需要检测的用户"
   )
   @Size(
      max = 100,
      message = "单次最多检测100个用户"
   )
   private List<Long> userIds;

   @Generated
   public List<Long> getUserIds() {
      return this.userIds;
   }

   @Generated
   public void setUserIds(final List<Long> userIds) {
      this.userIds = userIds;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserTelegramGroupCheckRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userIds = this.getUserIds();
         Object other$userIds = other.getUserIds();
         return this$userIds == null ? other$userIds == null : this$userIds.equals(other$userIds);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyUserTelegramGroupCheckRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userIds = this.getUserIds();
      return result * 59 + ($userIds == null ? 43 : $userIds.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserTelegramGroupCheckRequest(userIds=" + this.getUserIds() + ")";
   }
}
