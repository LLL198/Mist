package com.una.embyhub.model.dto.request.telegram;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class TelegramRebindStartRequest implements Serializable {
   @NotBlank(
      message = "当前密码不能为空"
   )
   @Size(
      max = 256,
      message = "当前密码过长"
   )
   private String currentPassword;
   @NotNull(
      message = "新 Telegram 用户 ID 不能为空"
   )
   @Positive(
      message = "新 Telegram 用户 ID 无效"
   )
   private Long newTelegramUserId;
   @Size(
      max = 33,
      message = "Telegram 用户名过长"
   )
   private String newTelegramUsername;

   @Generated
   public String getCurrentPassword() {
      return this.currentPassword;
   }

   @Generated
   public Long getNewTelegramUserId() {
      return this.newTelegramUserId;
   }

   @Generated
   public String getNewTelegramUsername() {
      return this.newTelegramUsername;
   }

   @Generated
   public void setCurrentPassword(final String currentPassword) {
      this.currentPassword = currentPassword;
   }

   @Generated
   public void setNewTelegramUserId(final Long newTelegramUserId) {
      this.newTelegramUserId = newTelegramUserId;
   }

   @Generated
   public void setNewTelegramUsername(final String newTelegramUsername) {
      this.newTelegramUsername = newTelegramUsername;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramRebindStartRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$newTelegramUserId = this.getNewTelegramUserId();
         Object other$newTelegramUserId = other.getNewTelegramUserId();
         if (this$newTelegramUserId == null ? other$newTelegramUserId == null : this$newTelegramUserId.equals(other$newTelegramUserId)) {
            Object this$currentPassword = this.getCurrentPassword();
            Object other$currentPassword = other.getCurrentPassword();
            if (this$currentPassword == null ? other$currentPassword == null : this$currentPassword.equals(other$currentPassword)) {
               Object this$newTelegramUsername = this.getNewTelegramUsername();
               Object other$newTelegramUsername = other.getNewTelegramUsername();
               return this$newTelegramUsername == null ? other$newTelegramUsername == null : this$newTelegramUsername.equals(other$newTelegramUsername);
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
      return other instanceof TelegramRebindStartRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $newTelegramUserId = this.getNewTelegramUserId();
      result = result * 59 + ($newTelegramUserId == null ? 43 : $newTelegramUserId.hashCode());
      Object $currentPassword = this.getCurrentPassword();
      result = result * 59 + ($currentPassword == null ? 43 : $currentPassword.hashCode());
      Object $newTelegramUsername = this.getNewTelegramUsername();
      return result * 59 + ($newTelegramUsername == null ? 43 : $newTelegramUsername.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramRebindStartRequest(currentPassword="
         + this.getCurrentPassword()
         + ", newTelegramUserId="
         + this.getNewTelegramUserId()
         + ", newTelegramUsername="
         + this.getNewTelegramUsername()
         + ")";
   }
}
