package com.una.embyhub.model.dto.response.embynotifydata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class TelegramBotAdminConfig implements Serializable {
   private String telegramId;
   private String name;
   private List<String> permissions = new ArrayList<>();

   @Generated
   public String getTelegramId() {
      return this.telegramId;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public List<String> getPermissions() {
      return this.permissions;
   }

   @Generated
   public void setTelegramId(final String telegramId) {
      this.telegramId = telegramId;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setPermissions(final List<String> permissions) {
      this.permissions = permissions;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBotAdminConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$telegramId = this.getTelegramId();
         Object other$telegramId = other.getTelegramId();
         if (this$telegramId == null ? other$telegramId == null : this$telegramId.equals(other$telegramId)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$permissions = this.getPermissions();
               Object other$permissions = other.getPermissions();
               return this$permissions == null ? other$permissions == null : this$permissions.equals(other$permissions);
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
      return other instanceof TelegramBotAdminConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $telegramId = this.getTelegramId();
      result = result * 59 + ($telegramId == null ? 43 : $telegramId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $permissions = this.getPermissions();
      return result * 59 + ($permissions == null ? 43 : $permissions.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBotAdminConfig(telegramId=" + this.getTelegramId() + ", name=" + this.getName() + ", permissions=" + this.getPermissions() + ")";
   }
}
