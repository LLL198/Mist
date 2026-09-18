package com.una.embyhub.model.dto.request.embyuser;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class AuthRecordCreateRequest implements Serializable {
   @NotBlank(
      message = "名称不能为空"
   )
   private String name;
   @NotBlank(
      message = "IP不能为空"
   )
   private String ip;
   @NotBlank(
      message = "用户名不能为空"
   )
   private String username;
   private Long createUserId;
   private String createUserName;
   private String ipAddress;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getIp() {
      return this.ip;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public Long getCreateUserId() {
      return this.createUserId;
   }

   @Generated
   public String getCreateUserName() {
      return this.createUserName;
   }

   @Generated
   public String getIpAddress() {
      return this.ipAddress;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setIp(final String ip) {
      this.ip = ip;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setCreateUserId(final Long createUserId) {
      this.createUserId = createUserId;
   }

   @Generated
   public void setCreateUserName(final String createUserName) {
      this.createUserName = createUserName;
   }

   @Generated
   public void setIpAddress(final String ipAddress) {
      this.ipAddress = ipAddress;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AuthRecordCreateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$createUserId = this.getCreateUserId();
         Object other$createUserId = other.getCreateUserId();
         if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$ip = this.getIp();
               Object other$ip = other.getIp();
               if (this$ip == null ? other$ip == null : this$ip.equals(other$ip)) {
                  Object this$username = this.getUsername();
                  Object other$username = other.getUsername();
                  if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                     Object this$createUserName = this.getCreateUserName();
                     Object other$createUserName = other.getCreateUserName();
                     if (this$createUserName == null ? other$createUserName == null : this$createUserName.equals(other$createUserName)) {
                        Object this$ipAddress = this.getIpAddress();
                        Object other$ipAddress = other.getIpAddress();
                        return this$ipAddress == null ? other$ipAddress == null : this$ipAddress.equals(other$ipAddress);
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
      return other instanceof AuthRecordCreateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $ip = this.getIp();
      result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $ipAddress = this.getIpAddress();
      return result * 59 + ($ipAddress == null ? 43 : $ipAddress.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "AuthRecordCreateRequest(name="
         + this.getName()
         + ", ip="
         + this.getIp()
         + ", username="
         + this.getUsername()
         + ", createUserId="
         + this.getCreateUserId()
         + ", createUserName="
         + this.getCreateUserName()
         + ", ipAddress="
         + this.getIpAddress()
         + ")";
   }
}
