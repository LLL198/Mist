package com.una.embyhub.config.common.config;

import java.time.Duration;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
   prefix = "foam.security.invitation-registration-rate-limit"
)
public class InvitationRegistrationRateLimitProperties {
   private int maxAttempts = 5;
   private Duration attemptWindow = Duration.ofMinutes(10L);
   private int dailySuccessLimit = 2;
   private Duration dailySuccessWindow = Duration.ofDays(1L);
   private Duration sameInvitationCooldown = Duration.ofDays(1L);
   private Duration openRegistrationCooldown = Duration.ofDays(1L);
   private Duration reservationTimeout = Duration.ofMinutes(5L);

   @Generated
   public int getMaxAttempts() {
      return this.maxAttempts;
   }

   @Generated
   public Duration getAttemptWindow() {
      return this.attemptWindow;
   }

   @Generated
   public int getDailySuccessLimit() {
      return this.dailySuccessLimit;
   }

   @Generated
   public Duration getDailySuccessWindow() {
      return this.dailySuccessWindow;
   }

   @Generated
   public Duration getSameInvitationCooldown() {
      return this.sameInvitationCooldown;
   }

   @Generated
   public Duration getOpenRegistrationCooldown() {
      return this.openRegistrationCooldown;
   }

   @Generated
   public Duration getReservationTimeout() {
      return this.reservationTimeout;
   }

   @Generated
   public void setMaxAttempts(final int maxAttempts) {
      this.maxAttempts = maxAttempts;
   }

   @Generated
   public void setAttemptWindow(final Duration attemptWindow) {
      this.attemptWindow = attemptWindow;
   }

   @Generated
   public void setDailySuccessLimit(final int dailySuccessLimit) {
      this.dailySuccessLimit = dailySuccessLimit;
   }

   @Generated
   public void setDailySuccessWindow(final Duration dailySuccessWindow) {
      this.dailySuccessWindow = dailySuccessWindow;
   }

   @Generated
   public void setSameInvitationCooldown(final Duration sameInvitationCooldown) {
      this.sameInvitationCooldown = sameInvitationCooldown;
   }

   @Generated
   public void setOpenRegistrationCooldown(final Duration openRegistrationCooldown) {
      this.openRegistrationCooldown = openRegistrationCooldown;
   }

   @Generated
   public void setReservationTimeout(final Duration reservationTimeout) {
      this.reservationTimeout = reservationTimeout;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof InvitationRegistrationRateLimitProperties other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getMaxAttempts() != other.getMaxAttempts()) {
         return false;
      } else if (this.getDailySuccessLimit() != other.getDailySuccessLimit()) {
         return false;
      } else {
         Object this$attemptWindow = this.getAttemptWindow();
         Object other$attemptWindow = other.getAttemptWindow();
         if (this$attemptWindow == null ? other$attemptWindow == null : this$attemptWindow.equals(other$attemptWindow)) {
            Object this$dailySuccessWindow = this.getDailySuccessWindow();
            Object other$dailySuccessWindow = other.getDailySuccessWindow();
            if (this$dailySuccessWindow == null ? other$dailySuccessWindow == null : this$dailySuccessWindow.equals(other$dailySuccessWindow)) {
               Object this$sameInvitationCooldown = this.getSameInvitationCooldown();
               Object other$sameInvitationCooldown = other.getSameInvitationCooldown();
               if (this$sameInvitationCooldown == null
                  ? other$sameInvitationCooldown == null
                  : this$sameInvitationCooldown.equals(other$sameInvitationCooldown)) {
                  Object this$openRegistrationCooldown = this.getOpenRegistrationCooldown();
                  Object other$openRegistrationCooldown = other.getOpenRegistrationCooldown();
                  if (this$openRegistrationCooldown == null
                     ? other$openRegistrationCooldown == null
                     : this$openRegistrationCooldown.equals(other$openRegistrationCooldown)) {
                     Object this$reservationTimeout = this.getReservationTimeout();
                     Object other$reservationTimeout = other.getReservationTimeout();
                     return this$reservationTimeout == null ? other$reservationTimeout == null : this$reservationTimeout.equals(other$reservationTimeout);
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
   protected boolean canEqual(final Object other) {
      return other instanceof InvitationRegistrationRateLimitProperties;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getMaxAttempts();
      result = result * 59 + this.getDailySuccessLimit();
      Object $attemptWindow = this.getAttemptWindow();
      result = result * 59 + ($attemptWindow == null ? 43 : $attemptWindow.hashCode());
      Object $dailySuccessWindow = this.getDailySuccessWindow();
      result = result * 59 + ($dailySuccessWindow == null ? 43 : $dailySuccessWindow.hashCode());
      Object $sameInvitationCooldown = this.getSameInvitationCooldown();
      result = result * 59 + ($sameInvitationCooldown == null ? 43 : $sameInvitationCooldown.hashCode());
      Object $openRegistrationCooldown = this.getOpenRegistrationCooldown();
      result = result * 59 + ($openRegistrationCooldown == null ? 43 : $openRegistrationCooldown.hashCode());
      Object $reservationTimeout = this.getReservationTimeout();
      return result * 59 + ($reservationTimeout == null ? 43 : $reservationTimeout.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "InvitationRegistrationRateLimitProperties(maxAttempts="
         + this.getMaxAttempts()
         + ", attemptWindow="
         + this.getAttemptWindow()
         + ", dailySuccessLimit="
         + this.getDailySuccessLimit()
         + ", dailySuccessWindow="
         + this.getDailySuccessWindow()
         + ", sameInvitationCooldown="
         + this.getSameInvitationCooldown()
         + ", openRegistrationCooldown="
         + this.getOpenRegistrationCooldown()
         + ", reservationTimeout="
         + this.getReservationTimeout()
         + ")";
   }
}
