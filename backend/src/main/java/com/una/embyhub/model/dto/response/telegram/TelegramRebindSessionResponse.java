package com.una.embyhub.model.dto.response.telegram;

import java.io.Serializable;
import lombok.Generated;

public class TelegramRebindSessionResponse implements Serializable {
   private String verificationId;
   private long expiresInSeconds;
   private String targetDisplay;
   private boolean verified;

   @Generated
   public String getVerificationId() {
      return this.verificationId;
   }

   @Generated
   public long getExpiresInSeconds() {
      return this.expiresInSeconds;
   }

   @Generated
   public String getTargetDisplay() {
      return this.targetDisplay;
   }

   @Generated
   public boolean isVerified() {
      return this.verified;
   }

   @Generated
   public void setVerificationId(final String verificationId) {
      this.verificationId = verificationId;
   }

   @Generated
   public void setExpiresInSeconds(final long expiresInSeconds) {
      this.expiresInSeconds = expiresInSeconds;
   }

   @Generated
   public void setTargetDisplay(final String targetDisplay) {
      this.targetDisplay = targetDisplay;
   }

   @Generated
   public void setVerified(final boolean verified) {
      this.verified = verified;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramRebindSessionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getExpiresInSeconds() != other.getExpiresInSeconds()) {
         return false;
      } else if (this.isVerified() != other.isVerified()) {
         return false;
      } else {
         Object this$verificationId = this.getVerificationId();
         Object other$verificationId = other.getVerificationId();
         if (this$verificationId == null ? other$verificationId == null : this$verificationId.equals(other$verificationId)) {
            Object this$targetDisplay = this.getTargetDisplay();
            Object other$targetDisplay = other.getTargetDisplay();
            return this$targetDisplay == null ? other$targetDisplay == null : this$targetDisplay.equals(other$targetDisplay);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TelegramRebindSessionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $expiresInSeconds = this.getExpiresInSeconds();
      result = result * 59 + (int)($expiresInSeconds >>> 32 ^ $expiresInSeconds);
      result = result * 59 + (this.isVerified() ? 79 : 97);
      Object $verificationId = this.getVerificationId();
      result = result * 59 + ($verificationId == null ? 43 : $verificationId.hashCode());
      Object $targetDisplay = this.getTargetDisplay();
      return result * 59 + ($targetDisplay == null ? 43 : $targetDisplay.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramRebindSessionResponse(verificationId="
         + this.getVerificationId()
         + ", expiresInSeconds="
         + this.getExpiresInSeconds()
         + ", targetDisplay="
         + this.getTargetDisplay()
         + ", verified="
         + this.isVerified()
         + ")";
   }

   @Generated
   public TelegramRebindSessionResponse() {
   }

   @Generated
   public TelegramRebindSessionResponse(final String verificationId, final long expiresInSeconds, final String targetDisplay, final boolean verified) {
      this.verificationId = verificationId;
      this.expiresInSeconds = expiresInSeconds;
      this.targetDisplay = targetDisplay;
      this.verified = verified;
   }
}
