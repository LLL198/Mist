package com.una.embyhub.payment.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.PaymentPurchaseSettingMapper;
import com.una.embyhub.payment.model.PaymentManagementDtos;
import com.una.embyhub.payment.model.PaymentPurchaseSetting;
import com.una.embyhub.payment.security.PaymentConfigCipher;
import com.una.embyhub.service.EmbyUserService;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

@Service
public class PaymentPurchaseSettingService extends ServiceImpl<PaymentPurchaseSettingMapper, PaymentPurchaseSetting> {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PaymentPurchaseSettingService.class);
   private static final long SETTING_ID = 1L;
   private final PaymentConfigCipher configCipher;
   private final EmbyUserService embyUserService;

   public PaymentManagementDtos.SettingResponse getAdminSetting() {
      this.assertPrimaryAdmin();
      return this.toResponse(this.current());
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PaymentManagementDtos.SettingResponse updateAdminSetting(PaymentManagementDtos.UpdateSettingRequest request) {
      this.assertPrimaryAdmin();
      if (request == null) {
         throw new BizException("支付设置不能为空");
      } else {
         PaymentPurchaseSetting setting = this.current();
         boolean protectionWasConfigured = this.configCipher.isConfigured();
         if (!protectionWasConfigured
            && StringUtils.hasText(setting.getMerchantKeyCipher())
            && StringUtils.hasText(request.encryptionKey())
            && !request.clearMerchantKey()
            && !StringUtils.hasText(request.merchantKey())) {
            throw new BizException("重新初始化商户密钥保护时，请同时重新输入商户密钥 PKEY");
         } else {
            String merchantKeyToReencrypt = null;
            PaymentConfigCipher.KeyRotation keyRotation = null;
            if (StringUtils.hasText(request.encryptionKey())) {
               if (protectionWasConfigured) {
                  if (!request.clearMerchantKey() && !StringUtils.hasText(request.merchantKey()) && StringUtils.hasText(setting.getMerchantKeyCipher())) {
                     merchantKeyToReencrypt = this.configCipher.decrypt(setting.getMerchantKeyCipher());
                  }

                  keyRotation = this.configCipher.rotateFromAdminInput(request.encryptionKey());
                  this.registerRotationRollback(keyRotation);
               } else {
                  this.configCipher.initializeFromAdminInput(request.encryptionKey());
               }
            }

            setting.setEnabled(request.enabled() ? 1 : 0);
            setting.setApiBaseUrl(normalizeApiBase(request.apiBaseUrl()));
            setting.setMerchantPid(normalizeText(request.merchantPid(), 128, "商户号"));
            setting.setPublicApiBaseUrl(normalizeBaseUrl(request.publicApiBaseUrl(), "外网 API 地址"));
            setting.setWebBaseUrl(normalizeBaseUrl(request.webBaseUrl(), "Web 地址"));
            setting.setSupportedTypes(String.join(",", normalizePaymentTypes(request.supportedTypes())));
            setting.setOrderTimeoutMinutes(bounded(request.orderTimeoutMinutes(), 5, 120, 15, "订单超时时间"));
            setting.setAutoQueryLimit(bounded(request.autoQueryLimit(), 1, 300, 30, "自动查单上限"));
            setting.setConnectTimeoutSeconds(bounded(request.connectTimeoutSeconds(), 1, 30, 5, "连接超时时间"));
            setting.setRequestTimeoutSeconds(bounded(request.requestTimeoutSeconds(), 1, 60, 10, "请求超时时间"));
            if (request.clearMerchantKey()) {
               setting.setMerchantKeyCipher(null);
            } else if (StringUtils.hasText(request.merchantKey())) {
               if (request.merchantKey().trim().length() > 512) {
                  throw new BizException("商户密钥不能超过 512 个字符");
               }

               setting.setMerchantKeyCipher(this.configCipher.encrypt(request.merchantKey()));
            } else if (keyRotation != null && keyRotation.changed() && StringUtils.hasText(merchantKeyToReencrypt)) {
               setting.setMerchantKeyCipher(this.configCipher.encrypt(merchantKeyToReencrypt));
            }

            if (!this.updateById(setting)) {
               throw new BizException("支付设置保存失败，请稍后重试");
            } else {
               return this.toResponse(this.current());
            }
         }
      }
   }

   public boolean isEnabled() {
      return Integer.valueOf(1).equals(this.current().getEnabled());
   }

   public List<String> paymentTypes() {
      return parsePaymentTypes(this.current().getSupportedTypes());
   }

   public String unavailableReason() {
      return this.unavailableReason(this.current());
   }

   public boolean isProviderConfigured() {
      return this.unavailableReason(this.current()) == null;
   }

   public int automaticQueryLimit() {
      Integer limit = this.current().getAutoQueryLimit();
      return limit == null ? 30 : limit;
   }

   public PaymentPurchaseSettingService.ProviderConfig requireProviderConfig() {
      PaymentPurchaseSetting setting = this.current();
      String reason = this.unavailableReason(setting);
      if (reason != null) {
         throw new BizException(reason);
      } else {
         return new PaymentPurchaseSettingService.ProviderConfig(
            setting.getApiBaseUrl(),
            setting.getMerchantPid(),
            this.configCipher.decrypt(setting.getMerchantKeyCipher()),
            setting.getPublicApiBaseUrl(),
            setting.getWebBaseUrl(),
            parsePaymentTypes(setting.getSupportedTypes()),
            Duration.ofSeconds((long)setting.getConnectTimeoutSeconds().intValue()),
            Duration.ofSeconds((long)setting.getRequestTimeoutSeconds().intValue()),
            Duration.ofMinutes((long)setting.getOrderTimeoutMinutes().intValue())
         );
      }
   }

   private PaymentManagementDtos.SettingResponse toResponse(PaymentPurchaseSetting setting) {
      String reason = this.unavailableReason(setting);
      return new PaymentManagementDtos.SettingResponse(
         Integer.valueOf(1).equals(setting.getEnabled()),
         setting.getApiBaseUrl(),
         setting.getMerchantPid(),
         StringUtils.hasText(setting.getMerchantKeyCipher()),
         this.configCipher.isConfigured(),
         this.configCipher.isAdminEditable(),
         setting.getPublicApiBaseUrl(),
         setting.getWebBaseUrl(),
         parsePaymentTypes(setting.getSupportedTypes()),
         setting.getOrderTimeoutMinutes(),
         setting.getAutoQueryLimit() == null ? 30 : setting.getAutoQueryLimit(),
         setting.getConnectTimeoutSeconds(),
         setting.getRequestTimeoutSeconds(),
         reason == null,
         reason
      );
   }

   private void registerRotationRollback(PaymentConfigCipher.KeyRotation rotation) {
      if (rotation.changed()) {
         if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            rotation.rollback();
            throw new BizException("商户密钥保护更换失败：数据库事务不可用");
         } else {
            try {
               TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                  @Override
                  public void afterCompletion(int status) {
                     if (status != 0) {
                        try {
                           rotation.rollback();
                        } catch (RuntimeException var3) {
                           PaymentPurchaseSettingService.log.error("支付设置事务回滚后，商户密钥保护文件恢复失败", (Throwable)var3);
                        }
                     }
                  }
               });
            } catch (RuntimeException var3) {
               rotation.rollback();
               throw var3;
            }
         }
      }
   }

   private String unavailableReason(PaymentPurchaseSetting setting) {
      if (!this.configCipher.isConfigured()) {
         return "商户密钥保护尚未初始化";
      } else if (!StringUtils.hasText(setting.getApiBaseUrl())) {
         return "易支付接口地址尚未配置";
      } else if (!StringUtils.hasText(setting.getMerchantPid())) {
         return "易支付商户号尚未配置";
      } else if (!StringUtils.hasText(setting.getMerchantKeyCipher())) {
         return "易支付商户密钥尚未配置";
      } else if (!StringUtils.hasText(setting.getPublicApiBaseUrl())) {
         return "Mist 外网 API 地址尚未配置";
      } else if (!StringUtils.hasText(setting.getWebBaseUrl())) {
         return "Mist Web 地址尚未配置";
      } else if (parsePaymentTypes(setting.getSupportedTypes()).isEmpty()) {
         return "至少需要启用一种支付方式";
      } else {
         try {
            this.configCipher.decrypt(setting.getMerchantKeyCipher());
            return null;
         } catch (RuntimeException var3) {
            return "商户密钥无法解密，请重新初始化保护并重新输入商户密钥";
         }
      }
   }

   private PaymentPurchaseSetting current() {
      PaymentPurchaseSetting setting = this.getById(Long.valueOf(1L));
      if (setting == null) {
         setting = new PaymentPurchaseSetting();
         setting.setId(1L);
         setting.setEnabled(0);
         setting.setSupportedTypes("alipay,wxpay");
         setting.setOrderTimeoutMinutes(15);
         setting.setAutoQueryLimit(30);
         setting.setConnectTimeoutSeconds(5);
         setting.setRequestTimeoutSeconds(10);
         this.save(setting);
      }

      return setting;
   }

   private void assertPrimaryAdmin() {
      this.embyUserService.assertCurrentUserCanManageAdministrators();
   }

   private static String normalizeApiBase(String value) {
      String normalized = normalizeBaseUrl(value, "易支付接口地址");
      return normalized == null ? null : normalized.replaceFirst("/(?:submit|mapi|api)\\.php(?:\\?.*)?$", "").replaceAll("/+$", "");
   }

   private static String normalizeBaseUrl(String value, String label) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         String normalized = value.trim().replaceAll("/+$", "");
         if (normalized.length() > 512) {
            throw new BizException(label + "不能超过 512 个字符");
         } else if (!isHttpsOrLoopbackHttp(normalized)) {
            throw new BizException(label + "必须使用 HTTPS；仅本机地址允许 HTTP");
         } else {
            return normalized;
         }
      }
   }

   private static boolean isHttpsOrLoopbackHttp(String value) {
      try {
         URI uri = URI.create(value);
         if (!StringUtils.hasText(uri.getHost()) || uri.getUserInfo() != null) {
            return false;
         } else if ("https".equalsIgnoreCase(uri.getScheme())) {
            return true;
         } else if (!"http".equalsIgnoreCase(uri.getScheme())) {
            return false;
         } else {
            String host = uri.getHost();
            return "localhost".equalsIgnoreCase(host) || "127.0.0.1".equals(host) || "::1".equals(host);
         }
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   private static String normalizeText(String value, int max, String label) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         String normalized = value.trim();
         if (normalized.length() > max) {
            throw new BizException(label + "不能超过 " + max + " 个字符");
         } else {
            return normalized;
         }
      }
   }

   private static List<String> normalizePaymentTypes(List<String> values) {
      return values == null
         ? List.of()
         : values.stream()
            .filter(StringUtils::hasText)
            .map(String::trim)
            .map(String::toLowerCase)
            .filter(value -> value.matches("[a-z0-9_-]{1,32}"))
            .distinct()
            .limit(8L)
            .toList();
   }

   private static List<String> parsePaymentTypes(String value) {
      return !StringUtils.hasText(value) ? List.of() : normalizePaymentTypes(Arrays.asList(value.split(",")));
   }

   private static int bounded(Integer value, int min, int max, int fallback, String label) {
      int resolved = value == null ? fallback : value;
      if (resolved >= min && resolved <= max) {
         return resolved;
      } else {
         throw new BizException(label + "需在 " + min + " 到 " + max + " 之间");
      }
   }

   @Generated
   public PaymentPurchaseSettingService(final PaymentConfigCipher configCipher, final EmbyUserService embyUserService) {
      this.configCipher = configCipher;
      this.embyUserService = embyUserService;
   }

   public static record ProviderConfig(
      String apiBaseUrl,
      String merchantPid,
      String merchantKey,
      String publicApiBaseUrl,
      String webBaseUrl,
      List<String> paymentTypes,
      Duration connectTimeout,
      Duration requestTimeout,
      Duration orderTimeout
   ) {
   }
}
