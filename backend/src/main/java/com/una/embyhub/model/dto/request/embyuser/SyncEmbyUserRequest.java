package com.una.embyhub.model.dto.request.embyuser;

import jakarta.validation.constraints.NotNull;
import lombok.Generated;

public class SyncEmbyUserRequest implements ProtectedUserMutationRequest {
   @NotNull(
      message = "源服务器不能为空"
   )
   private Long sourceEmbyInfoId;
   @NotNull(
      message = "目标服务器不能为空"
   )
   private Long targetEmbyInfoId;
   private String defaultPassword;

   @Generated
   public Long getSourceEmbyInfoId() {
      return this.sourceEmbyInfoId;
   }

   @Generated
   public Long getTargetEmbyInfoId() {
      return this.targetEmbyInfoId;
   }

   @Generated
   public String getDefaultPassword() {
      return this.defaultPassword;
   }

   @Generated
   public void setSourceEmbyInfoId(final Long sourceEmbyInfoId) {
      this.sourceEmbyInfoId = sourceEmbyInfoId;
   }

   @Generated
   public void setTargetEmbyInfoId(final Long targetEmbyInfoId) {
      this.targetEmbyInfoId = targetEmbyInfoId;
   }

   @Generated
   public void setDefaultPassword(final String defaultPassword) {
      this.defaultPassword = defaultPassword;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SyncEmbyUserRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$sourceEmbyInfoId = this.getSourceEmbyInfoId();
         Object other$sourceEmbyInfoId = other.getSourceEmbyInfoId();
         if (this$sourceEmbyInfoId == null ? other$sourceEmbyInfoId == null : this$sourceEmbyInfoId.equals(other$sourceEmbyInfoId)) {
            Object this$targetEmbyInfoId = this.getTargetEmbyInfoId();
            Object other$targetEmbyInfoId = other.getTargetEmbyInfoId();
            if (this$targetEmbyInfoId == null ? other$targetEmbyInfoId == null : this$targetEmbyInfoId.equals(other$targetEmbyInfoId)) {
               Object this$defaultPassword = this.getDefaultPassword();
               Object other$defaultPassword = other.getDefaultPassword();
               return this$defaultPassword == null ? other$defaultPassword == null : this$defaultPassword.equals(other$defaultPassword);
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
      return other instanceof SyncEmbyUserRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $sourceEmbyInfoId = this.getSourceEmbyInfoId();
      result = result * 59 + ($sourceEmbyInfoId == null ? 43 : $sourceEmbyInfoId.hashCode());
      Object $targetEmbyInfoId = this.getTargetEmbyInfoId();
      result = result * 59 + ($targetEmbyInfoId == null ? 43 : $targetEmbyInfoId.hashCode());
      Object $defaultPassword = this.getDefaultPassword();
      return result * 59 + ($defaultPassword == null ? 43 : $defaultPassword.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SyncEmbyUserRequest(sourceEmbyInfoId="
         + this.getSourceEmbyInfoId()
         + ", targetEmbyInfoId="
         + this.getTargetEmbyInfoId()
         + ", defaultPassword="
         + this.getDefaultPassword()
         + ")";
   }
}
