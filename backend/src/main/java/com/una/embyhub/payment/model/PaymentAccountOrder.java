package com.una.embyhub.payment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.una.embyhub.model.entity.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Generated;

@TableName("payment_account_order")
public class PaymentAccountOrder extends BaseEntity {
   @TableId(
      type = IdType.AUTO
   )
   private Long id;
   private String orderNo;
   private String buyerName;
   private String lookupTokenHash;
   private String providerTradeNo;
   private String paymentType;
   private String status;
   private Long packageId;
   private BigDecimal amount;
   private String productName;
   private Integer validityDays;
   private Long embyInfoId;
   private Integer hostLineType;
   private String accountUserName;
   private String encryptedPassword;
   private Long embyUserId;
   private Long cardSecurityId;
   private String qrCode;
   private String payUrl;
   private String clientIpHash;
   private Date lastQueryDatetime;
   private Integer providerQueryCount;
   private Date paidDatetime;
   private Date provisionStartedDatetime;
   private Date completedDatetime;
   private Date expiresDatetime;
   private Date credentialRevealedDatetime;
   private Long manualConfirmedBy;
   private Date manualConfirmedDatetime;
   private String manualConfirmReason;
   private String providerCheckResult;
   private String failureReason;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getOrderNo() {
      return this.orderNo;
   }

   @Generated
   public String getBuyerName() {
      return this.buyerName;
   }

   @Generated
   public String getLookupTokenHash() {
      return this.lookupTokenHash;
   }

   @Generated
   public String getProviderTradeNo() {
      return this.providerTradeNo;
   }

   @Generated
   public String getPaymentType() {
      return this.paymentType;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Long getPackageId() {
      return this.packageId;
   }

   @Generated
   public BigDecimal getAmount() {
      return this.amount;
   }

   @Generated
   public String getProductName() {
      return this.productName;
   }

   @Generated
   public Integer getValidityDays() {
      return this.validityDays;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public String getAccountUserName() {
      return this.accountUserName;
   }

   @Generated
   public String getEncryptedPassword() {
      return this.encryptedPassword;
   }

   @Generated
   public Long getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public Long getCardSecurityId() {
      return this.cardSecurityId;
   }

   @Generated
   public String getQrCode() {
      return this.qrCode;
   }

   @Generated
   public String getPayUrl() {
      return this.payUrl;
   }

   @Generated
   public String getClientIpHash() {
      return this.clientIpHash;
   }

   @Generated
   public Date getLastQueryDatetime() {
      return this.lastQueryDatetime;
   }

   @Generated
   public Integer getProviderQueryCount() {
      return this.providerQueryCount;
   }

   @Generated
   public Date getPaidDatetime() {
      return this.paidDatetime;
   }

   @Generated
   public Date getProvisionStartedDatetime() {
      return this.provisionStartedDatetime;
   }

   @Generated
   public Date getCompletedDatetime() {
      return this.completedDatetime;
   }

   @Generated
   public Date getExpiresDatetime() {
      return this.expiresDatetime;
   }

   @Generated
   public Date getCredentialRevealedDatetime() {
      return this.credentialRevealedDatetime;
   }

   @Generated
   public Long getManualConfirmedBy() {
      return this.manualConfirmedBy;
   }

   @Generated
   public Date getManualConfirmedDatetime() {
      return this.manualConfirmedDatetime;
   }

   @Generated
   public String getManualConfirmReason() {
      return this.manualConfirmReason;
   }

   @Generated
   public String getProviderCheckResult() {
      return this.providerCheckResult;
   }

   @Generated
   public String getFailureReason() {
      return this.failureReason;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setOrderNo(final String orderNo) {
      this.orderNo = orderNo;
   }

   @Generated
   public void setBuyerName(final String buyerName) {
      this.buyerName = buyerName;
   }

   @Generated
   public void setLookupTokenHash(final String lookupTokenHash) {
      this.lookupTokenHash = lookupTokenHash;
   }

   @Generated
   public void setProviderTradeNo(final String providerTradeNo) {
      this.providerTradeNo = providerTradeNo;
   }

   @Generated
   public void setPaymentType(final String paymentType) {
      this.paymentType = paymentType;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setPackageId(final Long packageId) {
      this.packageId = packageId;
   }

   @Generated
   public void setAmount(final BigDecimal amount) {
      this.amount = amount;
   }

   @Generated
   public void setProductName(final String productName) {
      this.productName = productName;
   }

   @Generated
   public void setValidityDays(final Integer validityDays) {
      this.validityDays = validityDays;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setAccountUserName(final String accountUserName) {
      this.accountUserName = accountUserName;
   }

   @Generated
   public void setEncryptedPassword(final String encryptedPassword) {
      this.encryptedPassword = encryptedPassword;
   }

   @Generated
   public void setEmbyUserId(final Long embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setCardSecurityId(final Long cardSecurityId) {
      this.cardSecurityId = cardSecurityId;
   }

   @Generated
   public void setQrCode(final String qrCode) {
      this.qrCode = qrCode;
   }

   @Generated
   public void setPayUrl(final String payUrl) {
      this.payUrl = payUrl;
   }

   @Generated
   public void setClientIpHash(final String clientIpHash) {
      this.clientIpHash = clientIpHash;
   }

   @Generated
   public void setLastQueryDatetime(final Date lastQueryDatetime) {
      this.lastQueryDatetime = lastQueryDatetime;
   }

   @Generated
   public void setProviderQueryCount(final Integer providerQueryCount) {
      this.providerQueryCount = providerQueryCount;
   }

   @Generated
   public void setPaidDatetime(final Date paidDatetime) {
      this.paidDatetime = paidDatetime;
   }

   @Generated
   public void setProvisionStartedDatetime(final Date provisionStartedDatetime) {
      this.provisionStartedDatetime = provisionStartedDatetime;
   }

   @Generated
   public void setCompletedDatetime(final Date completedDatetime) {
      this.completedDatetime = completedDatetime;
   }

   @Generated
   public void setExpiresDatetime(final Date expiresDatetime) {
      this.expiresDatetime = expiresDatetime;
   }

   @Generated
   public void setCredentialRevealedDatetime(final Date credentialRevealedDatetime) {
      this.credentialRevealedDatetime = credentialRevealedDatetime;
   }

   @Generated
   public void setManualConfirmedBy(final Long manualConfirmedBy) {
      this.manualConfirmedBy = manualConfirmedBy;
   }

   @Generated
   public void setManualConfirmedDatetime(final Date manualConfirmedDatetime) {
      this.manualConfirmedDatetime = manualConfirmedDatetime;
   }

   @Generated
   public void setManualConfirmReason(final String manualConfirmReason) {
      this.manualConfirmReason = manualConfirmReason;
   }

   @Generated
   public void setProviderCheckResult(final String providerCheckResult) {
      this.providerCheckResult = providerCheckResult;
   }

   @Generated
   public void setFailureReason(final String failureReason) {
      this.failureReason = failureReason;
   }

   @Generated
   @Override
   public String toString() {
      return "PaymentAccountOrder(id="
         + this.getId()
         + ", orderNo="
         + this.getOrderNo()
         + ", buyerName="
         + this.getBuyerName()
         + ", lookupTokenHash="
         + this.getLookupTokenHash()
         + ", providerTradeNo="
         + this.getProviderTradeNo()
         + ", paymentType="
         + this.getPaymentType()
         + ", status="
         + this.getStatus()
         + ", packageId="
         + this.getPackageId()
         + ", amount="
         + this.getAmount()
         + ", productName="
         + this.getProductName()
         + ", validityDays="
         + this.getValidityDays()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", accountUserName="
         + this.getAccountUserName()
         + ", encryptedPassword="
         + this.getEncryptedPassword()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", cardSecurityId="
         + this.getCardSecurityId()
         + ", qrCode="
         + this.getQrCode()
         + ", payUrl="
         + this.getPayUrl()
         + ", clientIpHash="
         + this.getClientIpHash()
         + ", lastQueryDatetime="
         + this.getLastQueryDatetime()
         + ", providerQueryCount="
         + this.getProviderQueryCount()
         + ", paidDatetime="
         + this.getPaidDatetime()
         + ", provisionStartedDatetime="
         + this.getProvisionStartedDatetime()
         + ", completedDatetime="
         + this.getCompletedDatetime()
         + ", expiresDatetime="
         + this.getExpiresDatetime()
         + ", credentialRevealedDatetime="
         + this.getCredentialRevealedDatetime()
         + ", manualConfirmedBy="
         + this.getManualConfirmedBy()
         + ", manualConfirmedDatetime="
         + this.getManualConfirmedDatetime()
         + ", manualConfirmReason="
         + this.getManualConfirmReason()
         + ", providerCheckResult="
         + this.getProviderCheckResult()
         + ", failureReason="
         + this.getFailureReason()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PaymentAccountOrder other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$packageId = this.getPackageId();
            Object other$packageId = other.getPackageId();
            if (this$packageId == null ? other$packageId == null : this$packageId.equals(other$packageId)) {
               Object this$validityDays = this.getValidityDays();
               Object other$validityDays = other.getValidityDays();
               if (this$validityDays == null ? other$validityDays == null : this$validityDays.equals(other$validityDays)) {
                  Object this$embyInfoId = this.getEmbyInfoId();
                  Object other$embyInfoId = other.getEmbyInfoId();
                  if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                     Object this$hostLineType = this.getHostLineType();
                     Object other$hostLineType = other.getHostLineType();
                     if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                        Object this$embyUserId = this.getEmbyUserId();
                        Object other$embyUserId = other.getEmbyUserId();
                        if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                           Object this$cardSecurityId = this.getCardSecurityId();
                           Object other$cardSecurityId = other.getCardSecurityId();
                           if (this$cardSecurityId == null ? other$cardSecurityId == null : this$cardSecurityId.equals(other$cardSecurityId)) {
                              Object this$providerQueryCount = this.getProviderQueryCount();
                              Object other$providerQueryCount = other.getProviderQueryCount();
                              if (this$providerQueryCount == null ? other$providerQueryCount == null : this$providerQueryCount.equals(other$providerQueryCount)
                                 )
                               {
                                 Object this$manualConfirmedBy = this.getManualConfirmedBy();
                                 Object other$manualConfirmedBy = other.getManualConfirmedBy();
                                 if (this$manualConfirmedBy == null ? other$manualConfirmedBy == null : this$manualConfirmedBy.equals(other$manualConfirmedBy)) {
                                    Object this$orderNo = this.getOrderNo();
                                    Object other$orderNo = other.getOrderNo();
                                    if (this$orderNo == null ? other$orderNo == null : this$orderNo.equals(other$orderNo)) {
                                       Object this$buyerName = this.getBuyerName();
                                       Object other$buyerName = other.getBuyerName();
                                       if (this$buyerName == null ? other$buyerName == null : this$buyerName.equals(other$buyerName)) {
                                          Object this$lookupTokenHash = this.getLookupTokenHash();
                                          Object other$lookupTokenHash = other.getLookupTokenHash();
                                          if (this$lookupTokenHash == null ? other$lookupTokenHash == null : this$lookupTokenHash.equals(other$lookupTokenHash)
                                             )
                                           {
                                             Object this$providerTradeNo = this.getProviderTradeNo();
                                             Object other$providerTradeNo = other.getProviderTradeNo();
                                             if (this$providerTradeNo == null
                                                ? other$providerTradeNo == null
                                                : this$providerTradeNo.equals(other$providerTradeNo)) {
                                                Object this$paymentType = this.getPaymentType();
                                                Object other$paymentType = other.getPaymentType();
                                                if (this$paymentType == null ? other$paymentType == null : this$paymentType.equals(other$paymentType)) {
                                                   Object this$status = this.getStatus();
                                                   Object other$status = other.getStatus();
                                                   if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                      Object this$amount = this.getAmount();
                                                      Object other$amount = other.getAmount();
                                                      if (this$amount == null ? other$amount == null : this$amount.equals(other$amount)) {
                                                         Object this$productName = this.getProductName();
                                                         Object other$productName = other.getProductName();
                                                         if (this$productName == null ? other$productName == null : this$productName.equals(other$productName)) {
                                                            Object this$accountUserName = this.getAccountUserName();
                                                            Object other$accountUserName = other.getAccountUserName();
                                                            if (this$accountUserName == null
                                                               ? other$accountUserName == null
                                                               : this$accountUserName.equals(other$accountUserName)) {
                                                               Object this$encryptedPassword = this.getEncryptedPassword();
                                                               Object other$encryptedPassword = other.getEncryptedPassword();
                                                               if (this$encryptedPassword == null
                                                                  ? other$encryptedPassword == null
                                                                  : this$encryptedPassword.equals(other$encryptedPassword)) {
                                                                  Object this$qrCode = this.getQrCode();
                                                                  Object other$qrCode = other.getQrCode();
                                                                  if (this$qrCode == null ? other$qrCode == null : this$qrCode.equals(other$qrCode)) {
                                                                     Object this$payUrl = this.getPayUrl();
                                                                     Object other$payUrl = other.getPayUrl();
                                                                     if (this$payUrl == null ? other$payUrl == null : this$payUrl.equals(other$payUrl)) {
                                                                        Object this$clientIpHash = this.getClientIpHash();
                                                                        Object other$clientIpHash = other.getClientIpHash();
                                                                        if (this$clientIpHash == null
                                                                           ? other$clientIpHash == null
                                                                           : this$clientIpHash.equals(other$clientIpHash)) {
                                                                           Object this$lastQueryDatetime = this.getLastQueryDatetime();
                                                                           Object other$lastQueryDatetime = other.getLastQueryDatetime();
                                                                           if (this$lastQueryDatetime == null
                                                                              ? other$lastQueryDatetime == null
                                                                              : this$lastQueryDatetime.equals(other$lastQueryDatetime)) {
                                                                              Object this$paidDatetime = this.getPaidDatetime();
                                                                              Object other$paidDatetime = other.getPaidDatetime();
                                                                              if (this$paidDatetime == null
                                                                                 ? other$paidDatetime == null
                                                                                 : this$paidDatetime.equals(other$paidDatetime)) {
                                                                                 Object this$provisionStartedDatetime = this.getProvisionStartedDatetime();
                                                                                 Object other$provisionStartedDatetime = other.getProvisionStartedDatetime();
                                                                                 if (this$provisionStartedDatetime == null
                                                                                    ? other$provisionStartedDatetime == null
                                                                                    : this$provisionStartedDatetime.equals(other$provisionStartedDatetime)) {
                                                                                    Object this$completedDatetime = this.getCompletedDatetime();
                                                                                    Object other$completedDatetime = other.getCompletedDatetime();
                                                                                    if (this$completedDatetime == null
                                                                                       ? other$completedDatetime == null
                                                                                       : this$completedDatetime.equals(other$completedDatetime)) {
                                                                                       Object this$expiresDatetime = this.getExpiresDatetime();
                                                                                       Object other$expiresDatetime = other.getExpiresDatetime();
                                                                                       if (this$expiresDatetime == null
                                                                                          ? other$expiresDatetime == null
                                                                                          : this$expiresDatetime.equals(other$expiresDatetime)) {
                                                                                          Object this$credentialRevealedDatetime = this.getCredentialRevealedDatetime();
                                                                                          Object other$credentialRevealedDatetime = other.getCredentialRevealedDatetime();
                                                                                          if (this$credentialRevealedDatetime == null
                                                                                             ? other$credentialRevealedDatetime == null
                                                                                             : this$credentialRevealedDatetime.equals(
                                                                                                other$credentialRevealedDatetime
                                                                                             )) {
                                                                                             Object this$manualConfirmedDatetime = this.getManualConfirmedDatetime();
                                                                                             Object other$manualConfirmedDatetime = other.getManualConfirmedDatetime();
                                                                                             if (this$manualConfirmedDatetime == null
                                                                                                ? other$manualConfirmedDatetime == null
                                                                                                : this$manualConfirmedDatetime.equals(
                                                                                                   other$manualConfirmedDatetime
                                                                                                )) {
                                                                                                Object this$manualConfirmReason = this.getManualConfirmReason();
                                                                                                Object other$manualConfirmReason = other.getManualConfirmReason();
                                                                                                if (this$manualConfirmReason == null
                                                                                                   ? other$manualConfirmReason == null
                                                                                                   : this$manualConfirmReason.equals(other$manualConfirmReason)
                                                                                                   )
                                                                                                 {
                                                                                                   Object this$providerCheckResult = this.getProviderCheckResult();
                                                                                                   Object other$providerCheckResult = other.getProviderCheckResult();
                                                                                                   if (this$providerCheckResult == null
                                                                                                      ? other$providerCheckResult == null
                                                                                                      : this$providerCheckResult.equals(
                                                                                                         other$providerCheckResult
                                                                                                      )) {
                                                                                                      Object this$failureReason = this.getFailureReason();
                                                                                                      Object other$failureReason = other.getFailureReason();
                                                                                                      return this$failureReason == null
                                                                                                         ? other$failureReason == null
                                                                                                         : this$failureReason.equals(other$failureReason);
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
      return other instanceof PaymentAccountOrder;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $packageId = this.getPackageId();
      result = result * 59 + ($packageId == null ? 43 : $packageId.hashCode());
      Object $validityDays = this.getValidityDays();
      result = result * 59 + ($validityDays == null ? 43 : $validityDays.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $cardSecurityId = this.getCardSecurityId();
      result = result * 59 + ($cardSecurityId == null ? 43 : $cardSecurityId.hashCode());
      Object $providerQueryCount = this.getProviderQueryCount();
      result = result * 59 + ($providerQueryCount == null ? 43 : $providerQueryCount.hashCode());
      Object $manualConfirmedBy = this.getManualConfirmedBy();
      result = result * 59 + ($manualConfirmedBy == null ? 43 : $manualConfirmedBy.hashCode());
      Object $orderNo = this.getOrderNo();
      result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
      Object $buyerName = this.getBuyerName();
      result = result * 59 + ($buyerName == null ? 43 : $buyerName.hashCode());
      Object $lookupTokenHash = this.getLookupTokenHash();
      result = result * 59 + ($lookupTokenHash == null ? 43 : $lookupTokenHash.hashCode());
      Object $providerTradeNo = this.getProviderTradeNo();
      result = result * 59 + ($providerTradeNo == null ? 43 : $providerTradeNo.hashCode());
      Object $paymentType = this.getPaymentType();
      result = result * 59 + ($paymentType == null ? 43 : $paymentType.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $amount = this.getAmount();
      result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
      Object $productName = this.getProductName();
      result = result * 59 + ($productName == null ? 43 : $productName.hashCode());
      Object $accountUserName = this.getAccountUserName();
      result = result * 59 + ($accountUserName == null ? 43 : $accountUserName.hashCode());
      Object $encryptedPassword = this.getEncryptedPassword();
      result = result * 59 + ($encryptedPassword == null ? 43 : $encryptedPassword.hashCode());
      Object $qrCode = this.getQrCode();
      result = result * 59 + ($qrCode == null ? 43 : $qrCode.hashCode());
      Object $payUrl = this.getPayUrl();
      result = result * 59 + ($payUrl == null ? 43 : $payUrl.hashCode());
      Object $clientIpHash = this.getClientIpHash();
      result = result * 59 + ($clientIpHash == null ? 43 : $clientIpHash.hashCode());
      Object $lastQueryDatetime = this.getLastQueryDatetime();
      result = result * 59 + ($lastQueryDatetime == null ? 43 : $lastQueryDatetime.hashCode());
      Object $paidDatetime = this.getPaidDatetime();
      result = result * 59 + ($paidDatetime == null ? 43 : $paidDatetime.hashCode());
      Object $provisionStartedDatetime = this.getProvisionStartedDatetime();
      result = result * 59 + ($provisionStartedDatetime == null ? 43 : $provisionStartedDatetime.hashCode());
      Object $completedDatetime = this.getCompletedDatetime();
      result = result * 59 + ($completedDatetime == null ? 43 : $completedDatetime.hashCode());
      Object $expiresDatetime = this.getExpiresDatetime();
      result = result * 59 + ($expiresDatetime == null ? 43 : $expiresDatetime.hashCode());
      Object $credentialRevealedDatetime = this.getCredentialRevealedDatetime();
      result = result * 59 + ($credentialRevealedDatetime == null ? 43 : $credentialRevealedDatetime.hashCode());
      Object $manualConfirmedDatetime = this.getManualConfirmedDatetime();
      result = result * 59 + ($manualConfirmedDatetime == null ? 43 : $manualConfirmedDatetime.hashCode());
      Object $manualConfirmReason = this.getManualConfirmReason();
      result = result * 59 + ($manualConfirmReason == null ? 43 : $manualConfirmReason.hashCode());
      Object $providerCheckResult = this.getProviderCheckResult();
      result = result * 59 + ($providerCheckResult == null ? 43 : $providerCheckResult.hashCode());
      Object $failureReason = this.getFailureReason();
      return result * 59 + ($failureReason == null ? 43 : $failureReason.hashCode());
   }
}
