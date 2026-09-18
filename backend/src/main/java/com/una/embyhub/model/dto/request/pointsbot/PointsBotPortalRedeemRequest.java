package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotPortalRedeemRequest implements Serializable {
   @NotNull(
      message = "请选择兑换项目"
   )
   @Positive(
      message = "兑换项目不合法"
   )
   private Long configId;
   @NotBlank(
      message = "请求标识不能为空"
   )
   @Size(
      min = 16,
      max = 64,
      message = "请求标识长度不合法"
   )
   @Pattern(
      regexp = "^[A-Za-z0-9-]+$",
      message = "请求标识格式不合法"
   )
   private String requestId;
   @Size(
      max = 64,
      message = "Emby用户名长度不能超过64个字符"
   )
   private String embyUserName;
   @Size(
      min = 6,
      max = 30,
      message = "密码长度必须在6-30之间"
   )
   private String password;

   @Generated
   public Long getConfigId() {
      return this.configId;
   }

   @Generated
   public String getRequestId() {
      return this.requestId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public void setConfigId(final Long configId) {
      this.configId = configId;
   }

   @Generated
   public void setRequestId(final String requestId) {
      this.requestId = requestId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setPassword(final String password) {
      this.password = password;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalRedeemRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$configId = this.getConfigId();
         Object other$configId = other.getConfigId();
         if (this$configId == null ? other$configId == null : this$configId.equals(other$configId)) {
            Object this$requestId = this.getRequestId();
            Object other$requestId = other.getRequestId();
            if (this$requestId == null ? other$requestId == null : this$requestId.equals(other$requestId)) {
               Object this$embyUserName = this.getEmbyUserName();
               Object other$embyUserName = other.getEmbyUserName();
               if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                  Object this$password = this.getPassword();
                  Object other$password = other.getPassword();
                  return this$password == null ? other$password == null : this$password.equals(other$password);
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
      return other instanceof PointsBotPortalRedeemRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $configId = this.getConfigId();
      result = result * 59 + ($configId == null ? 43 : $configId.hashCode());
      Object $requestId = this.getRequestId();
      result = result * 59 + ($requestId == null ? 43 : $requestId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $password = this.getPassword();
      return result * 59 + ($password == null ? 43 : $password.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalRedeemRequest(configId="
         + this.getConfigId()
         + ", requestId="
         + this.getRequestId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", password="
         + this.getPassword()
         + ")";
   }
}
