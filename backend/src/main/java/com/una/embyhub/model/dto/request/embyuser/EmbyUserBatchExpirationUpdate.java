package com.una.embyhub.model.dto.request.embyuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class EmbyUserBatchExpirationUpdate implements ProtectedUserMutationRequest, Serializable {
   @NotEmpty(
      message = "请选择需要修改到期时间的用户"
   )
   @Size(
      max = 100,
      message = "单次最多修改100个用户"
   )
   private List<Long> userIds;
   @NotNull(
      message = "到期时间不能为空"
   )
   @JsonFormat(
      pattern = "yyyy/MM/dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date expirationDate;

   @Generated
   public List<Long> getUserIds() {
      return this.userIds;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
   }

   @Generated
   public void setUserIds(final List<Long> userIds) {
      this.userIds = userIds;
   }

   @JsonFormat(
      pattern = "yyyy/MM/dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserBatchExpirationUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$userIds = this.getUserIds();
         Object other$userIds = other.getUserIds();
         if (this$userIds == null ? other$userIds == null : this$userIds.equals(other$userIds)) {
            Object this$expirationDate = this.getExpirationDate();
            Object other$expirationDate = other.getExpirationDate();
            return this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyUserBatchExpirationUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $userIds = this.getUserIds();
      result = result * 59 + ($userIds == null ? 43 : $userIds.hashCode());
      Object $expirationDate = this.getExpirationDate();
      return result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserBatchExpirationUpdate(userIds=" + this.getUserIds() + ", expirationDate=" + this.getExpirationDate() + ")";
   }
}
