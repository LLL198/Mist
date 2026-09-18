package com.una.embyhub.payment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public final class PaymentAccountDtos {
   private PaymentAccountDtos() {
   }

   public static record CreateOrderRequest(
      @NotNull(message = "请选择套餐") Long packageId,
      @NotBlank(message = "请选择支付方式") @Pattern(regexp = "[a-z0-9_-]{1,32}",message = "支付方式无效") String paymentType,
      @NotBlank(message = "请输入购买人名称") @Size(max = 64,message = "购买人名称不能超过 64 个字符") String buyerName
   ) {
   }

   public static record CreateOrderResponse(
      String orderNo,
      String buyerName,
      String lookupCode,
      String status,
      String packageName,
      Integer validityDays,
      BigDecimal amount,
      String paymentType,
      String qrCode,
      String payUrl,
      Date expiresAt
   ) {
   }

   public static record LookupOrderRequest(
      @NotBlank(message = "请输入订单号") @Pattern(regexp = "(?:NOVA|FOAM)[0-9A-Z]{20,40}",message = "订单号格式无效") String orderNo,
      @NotBlank(message = "请输入查询码") @Pattern(regexp = "[A-Z2-9]{16}",message = "查询码格式无效") String lookupCode
   ) {
   }

   public static record LookupOrderResponse(
      String orderNo,
      String buyerName,
      String status,
      String statusText,
      String packageName,
      Integer validityDays,
      String cardPassword,
      String qrCode,
      String payUrl,
      Date orderExpiresAt,
      String message,
      Integer providerQueryCount,
      Integer autoQueryLimit,
      boolean autoQueryStopped
   ) {
   }

   public static record PublicConfig(
      boolean enabled, boolean configured, String unavailableReason, List<PaymentAccountDtos.PublicPackage> packages, List<String> paymentTypes
   ) {
   }

   public static record PublicPackage(Long id, String name, Integer validityDays, BigDecimal price) {
   }
}
