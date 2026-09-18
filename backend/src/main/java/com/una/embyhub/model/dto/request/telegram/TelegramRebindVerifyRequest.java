package com.una.embyhub.model.dto.request.telegram;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Generated;

public class TelegramRebindVerifyRequest implements Serializable {
   @NotBlank(
      message = "换绑会话不能为空"
   )
   @Pattern(
      regexp = "^[0-9a-f]{32}$",
      message = "换绑会话格式无效"
   )
   private String verificationId;
   @NotBlank(
      message = "验证码不能为空"
   )
   @Pattern(
      regexp = "\\d{6}",
      message = "请输入 6 位数字验证码"
   )
   private String verificationCode;

   @Generated
   public String getVerificationId() {
      return this.verificationId;
   }

   @Generated
   public String getVerificationCode() {
      return this.verificationCode;
   }

   @Generated
   public void setVerificationId(final String verificationId) {
      this.verificationId = verificationId;
   }

   @Generated
   public void setVerificationCode(final String verificationCode) {
      this.verificationCode = verificationCode;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramRebindVerifyRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$verificationId = this.getVerificationId();
         Object other$verificationId = other.getVerificationId();
         if (this$verificationId == null ? other$verificationId == null : this$verificationId.equals(other$verificationId)) {
            Object this$verificationCode = this.getVerificationCode();
            Object other$verificationCode = other.getVerificationCode();
            return this$verificationCode == null ? other$verificationCode == null : this$verificationCode.equals(other$verificationCode);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TelegramRebindVerifyRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $verificationId = this.getVerificationId();
      result = result * 59 + ($verificationId == null ? 43 : $verificationId.hashCode());
      Object $verificationCode = this.getVerificationCode();
      return result * 59 + ($verificationCode == null ? 43 : $verificationCode.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramRebindVerifyRequest(verificationId=" + this.getVerificationId() + ", verificationCode=" + this.getVerificationCode() + ")";
   }
}
