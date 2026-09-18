package com.una.embyhub.model.dto.request.telegram;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Generated;

public class TelegramRebindSubmitRequest implements Serializable {
   @NotBlank(
      message = "换绑会话不能为空"
   )
   @Pattern(
      regexp = "^[0-9a-f]{32}$",
      message = "换绑会话格式无效"
   )
   private String verificationId;

   @Generated
   public String getVerificationId() {
      return this.verificationId;
   }

   @Generated
   public void setVerificationId(final String verificationId) {
      this.verificationId = verificationId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramRebindSubmitRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$verificationId = this.getVerificationId();
         Object other$verificationId = other.getVerificationId();
         return this$verificationId == null ? other$verificationId == null : this$verificationId.equals(other$verificationId);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TelegramRebindSubmitRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $verificationId = this.getVerificationId();
      return result * 59 + ($verificationId == null ? 43 : $verificationId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramRebindSubmitRequest(verificationId=" + this.getVerificationId() + ")";
   }
}
