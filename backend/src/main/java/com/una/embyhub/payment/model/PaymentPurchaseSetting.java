package com.una.embyhub.payment.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.una.embyhub.model.entity.BaseEntity;
import lombok.Generated;

@TableName("payment_purchase_setting")
public class PaymentPurchaseSetting extends BaseEntity {
   @TableId(
      type = IdType.INPUT
   )
   private Long id;
   private Integer enabled;
   @TableField(
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String apiBaseUrl;
   @TableField(
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String merchantPid;
   @TableField(
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String merchantKeyCipher;
   @TableField(
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String publicApiBaseUrl;
   @TableField(
      updateStrategy = FieldStrategy.ALWAYS
   )
   private String webBaseUrl;
   private String supportedTypes;
   private Integer orderTimeoutMinutes;
   private Integer autoQueryLimit;
   private Integer connectTimeoutSeconds;
   private Integer requestTimeoutSeconds;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getApiBaseUrl() {
      return this.apiBaseUrl;
   }

   @Generated
   public String getMerchantPid() {
      return this.merchantPid;
   }

   @Generated
   public String getMerchantKeyCipher() {
      return this.merchantKeyCipher;
   }

   @Generated
   public String getPublicApiBaseUrl() {
      return this.publicApiBaseUrl;
   }

   @Generated
   public String getWebBaseUrl() {
      return this.webBaseUrl;
   }

   @Generated
   public String getSupportedTypes() {
      return this.supportedTypes;
   }

   @Generated
   public Integer getOrderTimeoutMinutes() {
      return this.orderTimeoutMinutes;
   }

   @Generated
   public Integer getAutoQueryLimit() {
      return this.autoQueryLimit;
   }

   @Generated
   public Integer getConnectTimeoutSeconds() {
      return this.connectTimeoutSeconds;
   }

   @Generated
   public Integer getRequestTimeoutSeconds() {
      return this.requestTimeoutSeconds;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setApiBaseUrl(final String apiBaseUrl) {
      this.apiBaseUrl = apiBaseUrl;
   }

   @Generated
   public void setMerchantPid(final String merchantPid) {
      this.merchantPid = merchantPid;
   }

   @Generated
   public void setMerchantKeyCipher(final String merchantKeyCipher) {
      this.merchantKeyCipher = merchantKeyCipher;
   }

   @Generated
   public void setPublicApiBaseUrl(final String publicApiBaseUrl) {
      this.publicApiBaseUrl = publicApiBaseUrl;
   }

   @Generated
   public void setWebBaseUrl(final String webBaseUrl) {
      this.webBaseUrl = webBaseUrl;
   }

   @Generated
   public void setSupportedTypes(final String supportedTypes) {
      this.supportedTypes = supportedTypes;
   }

   @Generated
   public void setOrderTimeoutMinutes(final Integer orderTimeoutMinutes) {
      this.orderTimeoutMinutes = orderTimeoutMinutes;
   }

   @Generated
   public void setAutoQueryLimit(final Integer autoQueryLimit) {
      this.autoQueryLimit = autoQueryLimit;
   }

   @Generated
   public void setConnectTimeoutSeconds(final Integer connectTimeoutSeconds) {
      this.connectTimeoutSeconds = connectTimeoutSeconds;
   }

   @Generated
   public void setRequestTimeoutSeconds(final Integer requestTimeoutSeconds) {
      this.requestTimeoutSeconds = requestTimeoutSeconds;
   }

   @Generated
   @Override
   public String toString() {
      return "PaymentPurchaseSetting(id="
         + this.getId()
         + ", enabled="
         + this.getEnabled()
         + ", apiBaseUrl="
         + this.getApiBaseUrl()
         + ", merchantPid="
         + this.getMerchantPid()
         + ", merchantKeyCipher="
         + this.getMerchantKeyCipher()
         + ", publicApiBaseUrl="
         + this.getPublicApiBaseUrl()
         + ", webBaseUrl="
         + this.getWebBaseUrl()
         + ", supportedTypes="
         + this.getSupportedTypes()
         + ", orderTimeoutMinutes="
         + this.getOrderTimeoutMinutes()
         + ", autoQueryLimit="
         + this.getAutoQueryLimit()
         + ", connectTimeoutSeconds="
         + this.getConnectTimeoutSeconds()
         + ", requestTimeoutSeconds="
         + this.getRequestTimeoutSeconds()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PaymentPurchaseSetting other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$orderTimeoutMinutes = this.getOrderTimeoutMinutes();
               Object other$orderTimeoutMinutes = other.getOrderTimeoutMinutes();
               if (this$orderTimeoutMinutes == null ? other$orderTimeoutMinutes == null : this$orderTimeoutMinutes.equals(other$orderTimeoutMinutes)) {
                  Object this$autoQueryLimit = this.getAutoQueryLimit();
                  Object other$autoQueryLimit = other.getAutoQueryLimit();
                  if (this$autoQueryLimit == null ? other$autoQueryLimit == null : this$autoQueryLimit.equals(other$autoQueryLimit)) {
                     Object this$connectTimeoutSeconds = this.getConnectTimeoutSeconds();
                     Object other$connectTimeoutSeconds = other.getConnectTimeoutSeconds();
                     if (this$connectTimeoutSeconds == null
                        ? other$connectTimeoutSeconds == null
                        : this$connectTimeoutSeconds.equals(other$connectTimeoutSeconds)) {
                        Object this$requestTimeoutSeconds = this.getRequestTimeoutSeconds();
                        Object other$requestTimeoutSeconds = other.getRequestTimeoutSeconds();
                        if (this$requestTimeoutSeconds == null
                           ? other$requestTimeoutSeconds == null
                           : this$requestTimeoutSeconds.equals(other$requestTimeoutSeconds)) {
                           Object this$apiBaseUrl = this.getApiBaseUrl();
                           Object other$apiBaseUrl = other.getApiBaseUrl();
                           if (this$apiBaseUrl == null ? other$apiBaseUrl == null : this$apiBaseUrl.equals(other$apiBaseUrl)) {
                              Object this$merchantPid = this.getMerchantPid();
                              Object other$merchantPid = other.getMerchantPid();
                              if (this$merchantPid == null ? other$merchantPid == null : this$merchantPid.equals(other$merchantPid)) {
                                 Object this$merchantKeyCipher = this.getMerchantKeyCipher();
                                 Object other$merchantKeyCipher = other.getMerchantKeyCipher();
                                 if (this$merchantKeyCipher == null ? other$merchantKeyCipher == null : this$merchantKeyCipher.equals(other$merchantKeyCipher)) {
                                    Object this$publicApiBaseUrl = this.getPublicApiBaseUrl();
                                    Object other$publicApiBaseUrl = other.getPublicApiBaseUrl();
                                    if (this$publicApiBaseUrl == null ? other$publicApiBaseUrl == null : this$publicApiBaseUrl.equals(other$publicApiBaseUrl)) {
                                       Object this$webBaseUrl = this.getWebBaseUrl();
                                       Object other$webBaseUrl = other.getWebBaseUrl();
                                       if (this$webBaseUrl == null ? other$webBaseUrl == null : this$webBaseUrl.equals(other$webBaseUrl)) {
                                          Object this$supportedTypes = this.getSupportedTypes();
                                          Object other$supportedTypes = other.getSupportedTypes();
                                          return this$supportedTypes == null ? other$supportedTypes == null : this$supportedTypes.equals(other$supportedTypes);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PaymentPurchaseSetting;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $orderTimeoutMinutes = this.getOrderTimeoutMinutes();
      result = result * 59 + ($orderTimeoutMinutes == null ? 43 : $orderTimeoutMinutes.hashCode());
      Object $autoQueryLimit = this.getAutoQueryLimit();
      result = result * 59 + ($autoQueryLimit == null ? 43 : $autoQueryLimit.hashCode());
      Object $connectTimeoutSeconds = this.getConnectTimeoutSeconds();
      result = result * 59 + ($connectTimeoutSeconds == null ? 43 : $connectTimeoutSeconds.hashCode());
      Object $requestTimeoutSeconds = this.getRequestTimeoutSeconds();
      result = result * 59 + ($requestTimeoutSeconds == null ? 43 : $requestTimeoutSeconds.hashCode());
      Object $apiBaseUrl = this.getApiBaseUrl();
      result = result * 59 + ($apiBaseUrl == null ? 43 : $apiBaseUrl.hashCode());
      Object $merchantPid = this.getMerchantPid();
      result = result * 59 + ($merchantPid == null ? 43 : $merchantPid.hashCode());
      Object $merchantKeyCipher = this.getMerchantKeyCipher();
      result = result * 59 + ($merchantKeyCipher == null ? 43 : $merchantKeyCipher.hashCode());
      Object $publicApiBaseUrl = this.getPublicApiBaseUrl();
      result = result * 59 + ($publicApiBaseUrl == null ? 43 : $publicApiBaseUrl.hashCode());
      Object $webBaseUrl = this.getWebBaseUrl();
      result = result * 59 + ($webBaseUrl == null ? 43 : $webBaseUrl.hashCode());
      Object $supportedTypes = this.getSupportedTypes();
      return result * 59 + ($supportedTypes == null ? 43 : $supportedTypes.hashCode());
   }
}
