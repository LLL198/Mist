package com.una.embyhub.payment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public final class PaymentManagementDtos {
   private PaymentManagementDtos() {
   }

   public static record ManualConfirmRequest(
      String providerTradeNo,
      @NotBlank(message = "请输入人工确认原因") @Size(min = 5,max = 512,message = "人工确认原因需为 5 到 512 个字符") String reason,
      boolean confirmedReceived
   ) {
   }

   public static record OrderResponse(
      Long id,
      String orderNo,
      String buyerName,
      String providerTradeNo,
      String paymentType,
      String status,
      String statusText,
      Long packageId,
      String packageName,
      Integer validityDays,
      BigDecimal amount,
      Long embyInfoId,
      Integer hostLineType,
      Long cardSecurityId,
      String cardPassword,
      Integer cardStatus,
      Date createDatetime,
      Date paidDatetime,
      Date completedDatetime,
      Date expiresDatetime,
      String failureReason,
      Long manualConfirmedBy,
      Date manualConfirmedDatetime,
      String manualConfirmReason,
      String providerCheckResult,
      Integer providerQueryCount,
      Integer autoQueryLimit,
      boolean autoQueryStopped
   ) {
   }

   public static record PackageRequest(
      @NotBlank(message = "请输入套餐名称") @Size(max = 128,message = "套餐名称不能超过 128 个字符") String name,
      @NotNull(message = "请输入有效天数") Integer validityDays,
      @NotNull(message = "请输入套餐价格") BigDecimal price,
      @NotNull(message = "请选择服务器") Long embyInfoId,
      Integer hostLineType,
      boolean enabled,
      Integer sortOrder,
      @Size(max = 255,message = "备注不能超过 255 个字符") String remarks
   ) {
   }

   public static record PackageResponse(
      Long id,
      String name,
      Integer validityDays,
      BigDecimal price,
      Long embyInfoId,
      Integer hostLineType,
      boolean enabled,
      Integer sortOrder,
      String remarks,
      Date createDatetime,
      Date updateDatetime
   ) {
   }

   public static record ReconcileResponse(String status, String statusText, String message) {
   }

   public static record SettingResponse(
      boolean enabled,
      String apiBaseUrl,
      String merchantPid,
      boolean merchantKeyConfigured,
      boolean encryptionKeyConfigured,
      boolean encryptionKeyEditable,
      String publicApiBaseUrl,
      String webBaseUrl,
      List<String> supportedTypes,
      Integer orderTimeoutMinutes,
      Integer autoQueryLimit,
      Integer connectTimeoutSeconds,
      Integer requestTimeoutSeconds,
      boolean configured,
      String unavailableReason
   ) {
   }

   public static record UpdateSettingRequest(
      boolean enabled,
      String apiBaseUrl,
      String merchantPid,
      String merchantKey,
      String encryptionKey,
      boolean clearMerchantKey,
      String publicApiBaseUrl,
      String webBaseUrl,
      List<String> supportedTypes,
      Integer orderTimeoutMinutes,
      Integer autoQueryLimit,
      Integer connectTimeoutSeconds,
      Integer requestTimeoutSeconds
   ) {
   }
}
