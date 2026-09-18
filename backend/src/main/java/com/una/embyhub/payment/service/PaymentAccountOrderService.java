package com.una.embyhub.payment.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.PaymentAccountOrderMapper;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.CardSecurityManagement;
import com.una.embyhub.payment.client.EasyPayClient;
import com.una.embyhub.payment.model.PaymentAccountDtos;
import com.una.embyhub.payment.model.PaymentAccountOrder;
import com.una.embyhub.payment.model.PaymentManagementDtos;
import com.una.embyhub.payment.model.PaymentPurchasePackage;
import com.una.embyhub.payment.security.PaymentAccountRateLimiter;
import com.una.embyhub.service.CardSecurityManagementService;
import com.una.embyhub.service.EmbyUserService;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PaymentAccountOrderService extends ServiceImpl<PaymentAccountOrderMapper, PaymentAccountOrder> {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PaymentAccountOrderService.class);
   private static final String ORDER_NO_PREFIX = "NOVA";
   private static final String TOKEN_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
   private static final Duration ACTIVE_QUERY_INTERVAL = Duration.ofSeconds(5L);
   private final SecureRandom secureRandom = new SecureRandom();
   private final PaymentPurchaseSettingService settingService;
   private final PaymentPurchasePackageService packageService;
   private final EasyPayClient easyPayClient;
   private final PaymentCardDeliveryService cardDeliveryService;
   private final CardSecurityManagementService cardService;
   private final PaymentAccountRateLimiter rateLimiter;
   private final EmbyUserService embyUserService;

   public PaymentAccountDtos.PublicConfig publicConfig() {
      boolean enabled = this.settingService.isEnabled();
      List<PaymentAccountDtos.PublicPackage> packages = this.packageService.listPublicPackages();
      String reason = this.settingService.unavailableReason();
      if (reason == null && packages.isEmpty()) {
         reason = "尚未维护可购买的套餐";
      }

      return new PaymentAccountDtos.PublicConfig(enabled, reason == null, reason, packages, this.settingService.paymentTypes());
   }

   public PaymentAccountDtos.CreateOrderResponse createOrder(PaymentAccountDtos.CreateOrderRequest request, String clientIp) {
      this.requireReadyForPurchase();
      this.rateLimiter.checkCreate(clientIp);
      PaymentPurchasePackage selectedPackage = this.packageService.requirePurchasable(request.packageId());
      PaymentPurchaseSettingService.ProviderConfig providerConfig = this.settingService.requireProviderConfig();
      String paymentType = request.paymentType().trim().toLowerCase();
      String buyerName = request.buyerName().trim();
      if (!providerConfig.paymentTypes().contains(paymentType)) {
         throw new BizException("当前支付方式未启用");
      } else {
         String orderNo = this.generateOrderNo();
         String lookupCode = this.randomText("ABCDEFGHJKLMNPQRSTUVWXYZ23456789", 16);
         Date now = new Date();
         Date expiresAt = Date.from(Instant.now().plus(providerConfig.orderTimeout()));
         PaymentAccountOrder order = new PaymentAccountOrder();
         order.setOrderNo(orderNo);
         order.setBuyerName(buyerName);
         order.setLookupTokenHash(DigestUtil.sha256Hex(lookupCode));
         order.setPaymentType(paymentType);
         order.setStatus("PENDING");
         order.setPackageId(selectedPackage.getId());
         order.setAmount(selectedPackage.getPrice());
         order.setProductName(selectedPackage.getName());
         order.setValidityDays(selectedPackage.getValidityDays());
         order.setEmbyInfoId(selectedPackage.getEmbyInfoId());
         order.setHostLineType(selectedPackage.getHostLineType());
         order.setClientIpHash(DigestUtil.sha256Hex(normalizeClientIp(clientIp)));
         order.setProviderQueryCount(0);
         order.setExpiresDatetime(expiresAt);
         order.setCreateDatetime(now);
         order.setUpdateDatetime(now);
         this.save(order);

         try {
            EasyPayClient.CreateResult providerOrder = this.easyPayClient
               .createOrder(
                  providerConfig,
                  new EasyPayClient.CreateCommand(orderNo, paymentType, selectedPackage.getName(), selectedPackage.getPrice(), normalizeClientIp(clientIp))
               );
            new LambdaUpdateChainWrapper<>(this.getBaseMapper())
               .eq(PaymentAccountOrder::getId, order.getId())
               .eq(PaymentAccountOrder::getStatus, "PENDING")
               .set(StringUtils.hasText(providerOrder.tradeNo()), PaymentAccountOrder::getProviderTradeNo, providerOrder.tradeNo())
               .set(PaymentAccountOrder::getQrCode, providerOrder.qrCode())
               .set(PaymentAccountOrder::getPayUrl, providerOrder.payUrl())
               .set(BaseEntity::getUpdateDatetime, new Date())
               .update();
            return new PaymentAccountDtos.CreateOrderResponse(
               orderNo,
               buyerName,
               lookupCode,
               "PENDING",
               selectedPackage.getName(),
               selectedPackage.getValidityDays(),
               selectedPackage.getPrice(),
               paymentType,
               providerOrder.qrCode(),
               providerOrder.payUrl(),
               expiresAt
            );
         } catch (RuntimeException var13) {
            new LambdaUpdateChainWrapper<>(this.getBaseMapper())
               .eq(PaymentAccountOrder::getId, order.getId())
               .set(PaymentAccountOrder::getStatus, "FAILED")
               .set(PaymentAccountOrder::getFailureReason, safeFailure(var13))
               .set(BaseEntity::getUpdateDatetime, new Date())
               .update();
            throw var13;
         }
      }
   }

   public PaymentAccountDtos.LookupOrderResponse lookupOrder(PaymentAccountDtos.LookupOrderRequest request, String clientIp) {
      this.rateLimiter.checkLookup(clientIp);
      PaymentAccountOrder order = this.findOrder(request.orderNo());
      this.verifyLookupCode(order, request.lookupCode());
      int automaticQueryLimit = this.settingService.automaticQueryLimit();
      this.refreshFromProviderIfDue(order, automaticQueryLimit);
      this.fulfillIfPaid(order.getOrderNo());
      order = this.getById(order.getId());
      CardSecurityManagement card = order.getCardSecurityId() == null ? null : this.cardService.getById(order.getCardSecurityId());
      return new PaymentAccountDtos.LookupOrderResponse(
         order.getOrderNo(),
         order.getBuyerName(),
         order.getStatus(),
         statusText(order.getStatus()),
         order.getProductName(),
         order.getValidityDays(),
         card == null ? null : card.getCardPassword(),
         "PENDING".equals(order.getStatus()) ? order.getQrCode() : null,
         "PENDING".equals(order.getStatus()) ? order.getPayUrl() : null,
         order.getExpiresDatetime(),
         responseMessage(order, automaticQueryLimit),
         providerQueryCount(order),
         automaticQueryLimit,
         automaticQueryLimitReached(order, automaticQueryLimit)
      );
   }

   public void handleNotification(Map<String, String> parameters) {
      PaymentPurchaseSettingService.ProviderConfig providerConfig = this.settingService.requireProviderConfig();
      EasyPayClient.Notification notification = this.easyPayClient.verifyNotification(providerConfig, parameters);
      if (!notification.paid()) {
         throw new BizException("易支付订单尚未支付");
      } else {
         PaymentAccountOrder order = this.findOrder(notification.orderNo());
         this.markPaid(order, notification.amount(), notification.tradeNo(), false, null);
         this.fulfillIfPaid(order.getOrderNo());
      }
   }

   public Page<PaymentManagementDtos.OrderResponse> listAdminOrders(
      long current, long size, String status, String orderNo, String buyerName, String providerTradeNo, String packageName
   ) {
      this.assertPrimaryAdmin();
      long safeCurrent = Math.max(1L, current);
      long safeSize = Math.max(1L, Math.min(100L, size));
      LambdaQueryWrapper<PaymentAccountOrder> wrapper = new LambdaQueryWrapper<PaymentAccountOrder>()
         .eq(StringUtils.hasText(status), PaymentAccountOrder::getStatus, normalizeStatus(status))
         .like(StringUtils.hasText(orderNo), PaymentAccountOrder::getOrderNo, orderNo == null ? null : orderNo.trim())
         .like(StringUtils.hasText(buyerName), PaymentAccountOrder::getBuyerName, buyerName == null ? null : buyerName.trim())
         .like(StringUtils.hasText(providerTradeNo), PaymentAccountOrder::getProviderTradeNo, providerTradeNo == null ? null : providerTradeNo.trim())
         .like(StringUtils.hasText(packageName), PaymentAccountOrder::getProductName, packageName == null ? null : packageName.trim())
         .orderByDesc(PaymentAccountOrder::getId);
      Page<PaymentAccountOrder> source = this.page(new Page<>(safeCurrent, safeSize), wrapper);
      Page<PaymentManagementDtos.OrderResponse> response = new Page<>(source.getCurrent(), source.getSize(), source.getTotal());
      int automaticQueryLimit = this.settingService.automaticQueryLimit();
      response.setRecords(source.getRecords().stream().map(order -> this.toAdminOrder(order, automaticQueryLimit)).toList());
      return response;
   }

   public PaymentManagementDtos.ReconcileResponse reconcileAdminOrder(String orderNo) {
      this.assertPrimaryAdmin();
      PaymentAccountOrder order = this.findOrder(orderNo);
      int automaticQueryLimit = this.settingService.automaticQueryLimit();
      boolean resumed = automaticQueryLimitReached(order, automaticQueryLimit);

      try {
         if (resumed) {
            this.resetAutomaticQueryBudget(order.getId());
            order = this.getById(order.getId());
         }

         this.refreshFromProvider(order, true, automaticQueryLimit);
         this.fulfillIfPaid(order.getOrderNo());
         order = this.getById(order.getId());
         String message = resumed && isAutomaticallyQueryable(order) ? "已恢复自动查单额度，并完成本次上游查询。" : responseMessage(order, automaticQueryLimit);
         return new PaymentManagementDtos.ReconcileResponse(order.getStatus(), statusText(order.getStatus()), message);
      } catch (RuntimeException var6) {
         this.saveProviderCheckResult(order.getId(), "人工查单失败：" + safeFailure(var6));
         throw var6;
      }
   }

   public PaymentManagementDtos.OrderResponse manuallyConfirmPaid(String orderNo, PaymentManagementDtos.ManualConfirmRequest request) {
      this.assertPrimaryAdmin();
      if (request != null && request.confirmedReceived()) {
         PaymentAccountOrder order = this.findOrder(orderNo);
         if ("COMPLETED".equals(order.getStatus())) {
            return this.toAdminOrder(order);
         } else {
            String reason = request.reason() == null ? "" : request.reason().trim();
            if (reason.length() >= 5 && reason.length() <= 512) {
               String resolvedProviderTradeNo = StringUtils.hasText(request.providerTradeNo()) ? request.providerTradeNo().trim() : null;
               EasyPayClient.QueryResult upstreamResult = null;

               String providerResult;
               try {
                  upstreamResult = this.easyPayClient.queryOrder(this.settingService.requireProviderConfig(), order.getOrderNo());
                  providerResult = upstreamResult.paid() ? "上游查单显示已支付" : "上游查单未确认支付：" + truncate(upstreamResult.message(), 420);
               } catch (RuntimeException var12) {
                  providerResult = "上游查单失败，管理员基于收款凭证强制确认：" + safeFailure(var12);
               }

               if (upstreamResult != null && upstreamResult.paid()) {
                  if (upstreamResult.amount() == null || order.getAmount().compareTo(upstreamResult.amount()) != 0) {
                     throw new BizException("上游已支付金额与本地订单金额不一致，已拒绝发卡");
                  }

                  if (!StringUtils.hasText(resolvedProviderTradeNo)) {
                     resolvedProviderTradeNo = upstreamResult.tradeNo();
                  }
               }

               Date now = new Date();
               long adminId = StpUtil.getLoginIdAsLong();
               boolean updated = new LambdaUpdateChainWrapper<>(this.getBaseMapper())
                  .eq(PaymentAccountOrder::getId, order.getId())
                  .in(PaymentAccountOrder::getStatus, new Object[]{"PENDING", "EXPIRED", "REVIEW", "FAILED"})
                  .set(StringUtils.hasText(resolvedProviderTradeNo), PaymentAccountOrder::getProviderTradeNo, resolvedProviderTradeNo)
                  .set(PaymentAccountOrder::getStatus, "PAID")
                  .set(PaymentAccountOrder::getPaidDatetime, now)
                  .set(PaymentAccountOrder::getManualConfirmedBy, Long.valueOf(adminId))
                  .set(PaymentAccountOrder::getManualConfirmedDatetime, now)
                  .set(PaymentAccountOrder::getManualConfirmReason, reason)
                  .set(PaymentAccountOrder::getProviderCheckResult, truncate(providerResult, 512))
                  .set(PaymentAccountOrder::getFailureReason, null)
                  .set(BaseEntity::getUpdateDatetime, now)
                  .update();
               if (!updated) {
                  order = this.getById(order.getId());
                  if (!"PAID".equals(order.getStatus())) {
                     throw new BizException("订单状态已变化，请刷新后重试");
                  }
               }

               this.fulfillIfPaid(order.getOrderNo());
               return this.toAdminOrder(this.getById(order.getId()));
            } else {
               throw new BizException("人工确认原因需为 5 到 512 个字符");
            }
         }
      } else {
         throw new BizException("请明确确认该订单已实际收款");
      }
   }

   public void reconcileOpenOrders() {
      List<PaymentAccountOrder> paidOrders = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(PaymentAccountOrder::getStatus, "PAID")
         .orderByAsc(PaymentAccountOrder::getPaidDatetime)
         .last("LIMIT 30")
         .list();
      paidOrders.forEach(orderx -> this.fulfillIfPaid(orderx.getOrderNo()));
      this.reconcileStaleProvisioning();
      if (this.settingService.isProviderConfigured()) {
         int automaticQueryLimit = this.settingService.automaticQueryLimit();
         Date oldest = Date.from(Instant.now().minus(Duration.ofDays(7L)));

         for (PaymentAccountOrder order : new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .in(PaymentAccountOrder::getStatus, new Object[]{"PENDING", "EXPIRED"})
            .lt(PaymentAccountOrder::getProviderQueryCount, Integer.valueOf(automaticQueryLimit))
            .ge(BaseEntity::getCreateDatetime, oldest)
            .orderByAsc(BaseEntity::getCreateDatetime)
            .last("LIMIT 50")
            .list()) {
            try {
               this.refreshFromProvider(order, false, automaticQueryLimit);
            } catch (RuntimeException var8) {
               log.warn("易支付订单主动对账失败: orderNo={}, error={}", order.getOrderNo(), var8.getMessage());
            }
         }
      }
   }

   private void refreshFromProviderIfDue(PaymentAccountOrder order, int automaticQueryLimit) {
      if (isAutomaticallyQueryable(order) && !automaticQueryLimitReached(order, automaticQueryLimit)) {
         Date lastQuery = order.getLastQueryDatetime();
         if (lastQuery == null || !lastQuery.toInstant().plus(ACTIVE_QUERY_INTERVAL).isAfter(Instant.now())) {
            try {
               this.refreshFromProvider(order, false, automaticQueryLimit);
            } catch (RuntimeException var5) {
               log.debug("前台查单时上游查询失败，保留本地状态: orderNo={}, error={}", order.getOrderNo(), var5.getMessage());
            }
         }
      }
   }

   private void refreshFromProvider(PaymentAccountOrder order, boolean force, int automaticQueryLimit) {
      Date now = new Date();
      LambdaUpdateChainWrapper<PaymentAccountOrder> claim = new LambdaUpdateChainWrapper<>(this.getBaseMapper()).eq(PaymentAccountOrder::getId, order.getId());
      if (!force) {
         claim.lt(PaymentAccountOrder::getProviderQueryCount, Integer.valueOf(automaticQueryLimit))
            .and(
               wrapper -> wrapper.isNull(PaymentAccountOrder::getLastQueryDatetime)
                     .or()
                     .lt(PaymentAccountOrder::getLastQueryDatetime, Date.from(Instant.now().minus(ACTIVE_QUERY_INTERVAL)))
            );
      }

      boolean claimed = claim.set(PaymentAccountOrder::getLastQueryDatetime, now)
         .setSql("provider_query_count = provider_query_count + 1", new Object[0])
         .set(BaseEntity::getUpdateDatetime, now)
         .update();
      if (claimed) {
         EasyPayClient.QueryResult result = this.easyPayClient.queryOrder(this.settingService.requireProviderConfig(), order.getOrderNo());
         this.saveProviderCheckResult(order.getId(), result.paid() ? "上游查单显示已支付" : "上游查单未确认支付：" + truncate(result.message(), 420));
         if (result.paid()) {
            this.markPaid(order, result.amount(), result.tradeNo(), false, null);
            this.fulfillIfPaid(order.getOrderNo());
         } else {
            if (order.getExpiresDatetime() != null && order.getExpiresDatetime().before(now) && "PENDING".equals(order.getStatus())) {
               new LambdaUpdateChainWrapper<>(this.getBaseMapper())
                  .eq(PaymentAccountOrder::getId, order.getId())
                  .eq(PaymentAccountOrder::getStatus, "PENDING")
                  .set(PaymentAccountOrder::getStatus, "EXPIRED")
                  .set(BaseEntity::getUpdateDatetime, now)
                  .update();
            }
         }
      }
   }

   private void markPaid(PaymentAccountOrder order, BigDecimal paidAmount, String providerTradeNo, boolean manual, String manualReason) {
      if (!"COMPLETED".equals(order.getStatus()) && !"PAID".equals(order.getStatus()) && !"PROVISIONING".equals(order.getStatus())) {
         if (paidAmount != null && order.getAmount().compareTo(paidAmount) == 0) {
            Date now = new Date();
            LambdaUpdateChainWrapper<PaymentAccountOrder> update = new LambdaUpdateChainWrapper<>(this.getBaseMapper())
               .eq(PaymentAccountOrder::getId, order.getId())
               .in(PaymentAccountOrder::getStatus, new Object[]{"PENDING", "EXPIRED"})
               .set(StringUtils.hasText(providerTradeNo), PaymentAccountOrder::getProviderTradeNo, providerTradeNo)
               .set(PaymentAccountOrder::getStatus, "PAID")
               .set(PaymentAccountOrder::getPaidDatetime, now)
               .set(PaymentAccountOrder::getFailureReason, null)
               .set(BaseEntity::getUpdateDatetime, now);
            if (manual) {
               update.set(PaymentAccountOrder::getManualConfirmedBy, Long.valueOf(StpUtil.getLoginIdAsLong()))
                  .set(PaymentAccountOrder::getManualConfirmedDatetime, now)
                  .set(PaymentAccountOrder::getManualConfirmReason, truncate(manualReason, 512));
            }

            update.update();
         } else {
            this.moveToReview(order.getId(), "支付金额与订单快照不一致");
            throw new BizException("支付金额与订单不一致，请联系管理员");
         }
      }
   }

   private void fulfillIfPaid(String orderNo) {
      PaymentAccountOrder current = this.findOrder(orderNo);
      if ("PAID".equals(current.getStatus()) || "PROVISIONING".equals(current.getStatus())) {
         try {
            this.cardDeliveryService.fulfill(orderNo);
         } catch (RuntimeException var4) {
            this.moveToReview(current.getId(), "已收款但生成卡密失败：" + safeFailure(var4));
            log.error("已付款订单生成卡密失败: orderNo={}", orderNo, var4);
         }
      }
   }

   private void reconcileStaleProvisioning() {
      Date staleBefore = Date.from(Instant.now().minus(Duration.ofMinutes(5L)));

      for (PaymentAccountOrder order : new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(PaymentAccountOrder::getStatus, "PROVISIONING")
         .lt(PaymentAccountOrder::getProvisionStartedDatetime, staleBefore)
         .last("LIMIT 20")
         .list()) {
         CardSecurityManagement card = new LambdaQueryChainWrapper<>(this.cardService.getBaseMapper())
            .eq(CardSecurityManagement::getPaymentOrderId, order.getId())
            .oneOpt()
            .orElse(null);
         if (card != null) {
            new LambdaUpdateChainWrapper<>(this.getBaseMapper())
               .eq(PaymentAccountOrder::getId, order.getId())
               .set(PaymentAccountOrder::getStatus, "COMPLETED")
               .set(PaymentAccountOrder::getCardSecurityId, card.getId())
               .set(PaymentAccountOrder::getCompletedDatetime, new Date())
               .set(PaymentAccountOrder::getFailureReason, null)
               .set(BaseEntity::getUpdateDatetime, new Date())
               .update();
         } else {
            new LambdaUpdateChainWrapper<>(this.getBaseMapper())
               .eq(PaymentAccountOrder::getId, order.getId())
               .eq(PaymentAccountOrder::getStatus, "PROVISIONING")
               .set(PaymentAccountOrder::getStatus, "PAID")
               .set(BaseEntity::getUpdateDatetime, new Date())
               .update();
            this.fulfillIfPaid(order.getOrderNo());
         }
      }
   }

   private PaymentManagementDtos.OrderResponse toAdminOrder(PaymentAccountOrder order) {
      return this.toAdminOrder(order, this.settingService.automaticQueryLimit());
   }

   private PaymentManagementDtos.OrderResponse toAdminOrder(PaymentAccountOrder order, int automaticQueryLimit) {
      CardSecurityManagement card = order.getCardSecurityId() == null ? null : this.cardService.getById(order.getCardSecurityId());
      return new PaymentManagementDtos.OrderResponse(
         order.getId(),
         order.getOrderNo(),
         order.getBuyerName(),
         order.getProviderTradeNo(),
         order.getPaymentType(),
         order.getStatus(),
         statusText(order.getStatus()),
         order.getPackageId(),
         order.getProductName(),
         order.getValidityDays(),
         order.getAmount(),
         order.getEmbyInfoId(),
         order.getHostLineType(),
         order.getCardSecurityId(),
         card == null ? null : card.getCardPassword(),
         card == null ? null : card.getCardStatus(),
         order.getCreateDatetime(),
         order.getPaidDatetime(),
         order.getCompletedDatetime(),
         order.getExpiresDatetime(),
         order.getFailureReason(),
         order.getManualConfirmedBy(),
         order.getManualConfirmedDatetime(),
         order.getManualConfirmReason(),
         order.getProviderCheckResult(),
         providerQueryCount(order),
         automaticQueryLimit,
         automaticQueryLimitReached(order, automaticQueryLimit)
      );
   }

   private void resetAutomaticQueryBudget(Long orderId) {
      Date now = new Date();
      boolean updated = new LambdaUpdateChainWrapper<>(this.getBaseMapper())
         .eq(PaymentAccountOrder::getId, orderId)
         .set(PaymentAccountOrder::getProviderQueryCount, Integer.valueOf(0))
         .set(PaymentAccountOrder::getLastQueryDatetime, now)
         .set(BaseEntity::getUpdateDatetime, now)
         .update();
      if (!updated) {
         throw new BizException("恢复自动查单失败，请刷新后重试");
      }
   }

   private void saveProviderCheckResult(Long orderId, String result) {
      new LambdaUpdateChainWrapper<>(this.getBaseMapper())
         .eq(PaymentAccountOrder::getId, orderId)
         .set(PaymentAccountOrder::getProviderCheckResult, truncate(result, 512))
         .set(BaseEntity::getUpdateDatetime, new Date())
         .update();
   }

   private void moveToReview(Long orderId, String reason) {
      new LambdaUpdateChainWrapper<>(this.getBaseMapper())
         .eq(PaymentAccountOrder::getId, orderId)
         .set(PaymentAccountOrder::getStatus, "REVIEW")
         .set(PaymentAccountOrder::getFailureReason, truncate(reason, 512))
         .set(BaseEntity::getUpdateDatetime, new Date())
         .update();
   }

   private PaymentAccountOrder findOrder(String orderNo) {
      PaymentAccountOrder order = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(PaymentAccountOrder::getOrderNo, orderNo == null ? null : orderNo.trim().toUpperCase())
         .oneOpt()
         .orElse(null);
      if (order == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "订单号或查询码不正确");
      } else {
         return order;
      }
   }

   private void verifyLookupCode(PaymentAccountOrder order, String lookupCode) {
      byte[] expected = order.getLookupTokenHash().getBytes(StandardCharsets.US_ASCII);
      byte[] actual = DigestUtil.sha256Hex(lookupCode == null ? "" : lookupCode.trim().toUpperCase()).getBytes(StandardCharsets.US_ASCII);
      if (!MessageDigest.isEqual(expected, actual)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "订单号或查询码不正确");
      }
   }

   private void requireReadyForPurchase() {
      if (!this.settingService.isEnabled()) {
         throw new BizException("扫码购买卡密暂未开放");
      } else {
         String reason = this.settingService.unavailableReason();
         if (reason != null) {
            throw new BizException(reason);
         } else if (this.packageService.listPublicPackages().isEmpty()) {
            throw new BizException("尚未维护可购买的套餐");
         }
      }
   }

   public static String statusText(String status) {
      return switch (status) {
         case "PENDING" -> "等待支付";
         case "PAID" -> "已支付，等待发卡";
         case "PROVISIONING" -> "正在生成卡密";
         case "COMPLETED" -> "卡密已生成";
         case "EXPIRED" -> "订单已过期";
         case "REVIEW" -> "需要人工处理";
         case "FAILED" -> "创建订单失败";
         default -> "处理中";
      };
   }

   private static String responseMessage(PaymentAccountOrder order, int automaticQueryLimit) {
      if (automaticQueryLimitReached(order, automaticQueryLimit)) {
         return "自动查单已达到 " + automaticQueryLimit + " 次上限，系统已停止继续请求；如已付款，请联系管理员核验。";
      } else {
         String var2 = order.getStatus();

         return switch (var2) {
            case "COMPLETED" -> "卡密已交付，请复制后前往卡密激活，自行设置账号和密码。";
            case "REVIEW" -> "付款或发卡结果需要管理员核对，请保留订单号并稍后重试。";
            case "EXPIRED" -> "订单已过期；如实际已付款，系统会在自动查单上限内继续对账。";
            case "FAILED" -> "付款码创建失败，请重新选择套餐创建订单。";
            case "PAID", "PROVISIONING" -> "已确认到账，系统正在生成卡密，请稍后重试。";
            default -> "暂未确认到账；如已付款，请等待 1 到 2 分钟后再次查询。";
         };
      }
   }

   private static boolean isAutomaticallyQueryable(PaymentAccountOrder order) {
      return "PENDING".equals(order.getStatus()) || "EXPIRED".equals(order.getStatus());
   }

   private static int providerQueryCount(PaymentAccountOrder order) {
      return Math.max(0, order.getProviderQueryCount() == null ? 0 : order.getProviderQueryCount());
   }

   private static boolean automaticQueryLimitReached(PaymentAccountOrder order, int automaticQueryLimit) {
      return isAutomaticallyQueryable(order) && providerQueryCount(order) >= automaticQueryLimit;
   }

   private void assertPrimaryAdmin() {
      this.embyUserService.assertCurrentUserCanManageAdministrators();
   }

   private String generateOrderNo() {
      return "NOVA" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + this.randomText("ABCDEFGHJKLMNPQRSTUVWXYZ23456789", 12);
   }

   private String randomText(String alphabet, int length) {
      StringBuilder value = new StringBuilder(length);

      for (int index = 0; index < length; index++) {
         value.append(alphabet.charAt(this.secureRandom.nextInt(alphabet.length())));
      }

      return value.toString();
   }

   private static String normalizeClientIp(String clientIp) {
      return StringUtils.hasText(clientIp) ? clientIp.trim() : "unknown";
   }

   private static String normalizeStatus(String status) {
      String normalized = status == null ? "" : status.trim().toUpperCase();
      return normalized.matches("PENDING|PAID|PROVISIONING|COMPLETED|EXPIRED|REVIEW|FAILED") ? normalized : "__INVALID__";
   }

   private static String safeFailure(Throwable throwable) {
      String message = throwable == null ? null : throwable.getMessage();
      return truncate(StringUtils.hasText(message) ? message.trim() : "未知错误", 480);
   }

   private static String truncate(String value, int maxLength) {
      return value != null && value.length() > maxLength ? value.substring(0, maxLength) : value;
   }

   @Generated
   public PaymentAccountOrderService(
      final PaymentPurchaseSettingService settingService,
      final PaymentPurchasePackageService packageService,
      final EasyPayClient easyPayClient,
      final PaymentCardDeliveryService cardDeliveryService,
      final CardSecurityManagementService cardService,
      final PaymentAccountRateLimiter rateLimiter,
      final EmbyUserService embyUserService
   ) {
      this.settingService = settingService;
      this.packageService = packageService;
      this.easyPayClient = easyPayClient;
      this.cardDeliveryService = cardDeliveryService;
      this.cardService = cardService;
      this.rateLimiter = rateLimiter;
      this.embyUserService = embyUserService;
   }
}
