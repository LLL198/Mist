package com.una.embyhub.model.dto.request.telegram;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class TelegramLoginRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   @NotNull(
      message = "Telegram 用户 ID 不能为空"
   )
   @Positive(
      message = "Telegram 用户 ID 无效"
   )
   private Long id;
   @Size(
      max = 64,
      message = "Telegram 名字过长"
   )
   private String first_name;
   @Size(
      max = 64,
      message = "Telegram 姓氏过长"
   )
   private String last_name;
   @Size(
      max = 32,
      message = "Telegram 用户名过长"
   )
   private String username;
   @Size(
      max = 512,
      message = "Telegram 头像地址过长"
   )
   private String photo_url;
   @NotNull(
      message = "Telegram 认证时间不能为空"
   )
   @Positive(
      message = "Telegram 认证时间无效"
   )
   private Long auth_date;
   @NotBlank(
      message = "Telegram 签名不能为空"
   )
   @Pattern(
      regexp = "^[0-9a-fA-F]{64}$",
      message = "Telegram 签名格式无效"
   )
   private String hash;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getFirst_name() {
      return this.first_name;
   }

   @Generated
   public String getLast_name() {
      return this.last_name;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getPhoto_url() {
      return this.photo_url;
   }

   @Generated
   public Long getAuth_date() {
      return this.auth_date;
   }

   @Generated
   public String getHash() {
      return this.hash;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setFirst_name(final String first_name) {
      this.first_name = first_name;
   }

   @Generated
   public void setLast_name(final String last_name) {
      this.last_name = last_name;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setPhoto_url(final String photo_url) {
      this.photo_url = photo_url;
   }

   @Generated
   public void setAuth_date(final Long auth_date) {
      this.auth_date = auth_date;
   }

   @Generated
   public void setHash(final String hash) {
      this.hash = hash;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramLoginRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$auth_date = this.getAuth_date();
            Object other$auth_date = other.getAuth_date();
            if (this$auth_date == null ? other$auth_date == null : this$auth_date.equals(other$auth_date)) {
               Object this$first_name = this.getFirst_name();
               Object other$first_name = other.getFirst_name();
               if (this$first_name == null ? other$first_name == null : this$first_name.equals(other$first_name)) {
                  Object this$last_name = this.getLast_name();
                  Object other$last_name = other.getLast_name();
                  if (this$last_name == null ? other$last_name == null : this$last_name.equals(other$last_name)) {
                     Object this$username = this.getUsername();
                     Object other$username = other.getUsername();
                     if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                        Object this$photo_url = this.getPhoto_url();
                        Object other$photo_url = other.getPhoto_url();
                        if (this$photo_url == null ? other$photo_url == null : this$photo_url.equals(other$photo_url)) {
                           Object this$hash = this.getHash();
                           Object other$hash = other.getHash();
                           return this$hash == null ? other$hash == null : this$hash.equals(other$hash);
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
      return other instanceof TelegramLoginRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $auth_date = this.getAuth_date();
      result = result * 59 + ($auth_date == null ? 43 : $auth_date.hashCode());
      Object $first_name = this.getFirst_name();
      result = result * 59 + ($first_name == null ? 43 : $first_name.hashCode());
      Object $last_name = this.getLast_name();
      result = result * 59 + ($last_name == null ? 43 : $last_name.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $photo_url = this.getPhoto_url();
      result = result * 59 + ($photo_url == null ? 43 : $photo_url.hashCode());
      Object $hash = this.getHash();
      return result * 59 + ($hash == null ? 43 : $hash.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramLoginRequest(id="
         + this.getId()
         + ", first_name="
         + this.getFirst_name()
         + ", last_name="
         + this.getLast_name()
         + ", username="
         + this.getUsername()
         + ", photo_url="
         + this.getPhoto_url()
         + ", auth_date="
         + this.getAuth_date()
         + ", hash="
         + this.getHash()
         + ")";
   }
}
