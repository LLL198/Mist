package com.una.embyhub.payment.client;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.payment.service.PaymentPurchaseSettingService;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EasyPayClient {
   private static final String TRADE_SUCCESS = "TRADE_SUCCESS";

   public EasyPayClient.CreateResult createOrder(PaymentPurchaseSettingService.ProviderConfig config, EasyPayClient.CreateCommand command) {
      Map<String, String> parameters = new LinkedHashMap<>();
      parameters.put("pid", config.merchantPid());
      parameters.put("type", command.paymentType());
      parameters.put("out_trade_no", command.orderNo());
      parameters.put("notify_url", config.publicApiBaseUrl() + "/paymentAccount/easypay/notify");
      parameters.put("return_url", config.webBaseUrl() + "/login?paymentOrder=" + urlEncode(command.orderNo()));
      parameters.put("name", command.productName());
      parameters.put("money", command.amount().setScale(2).toPlainString());
      parameters.put("clientip", command.clientIp());
      parameters.put("sign", sign(parameters, config.merchantKey()));
      parameters.put("sign_type", "MD5");
      JSONObject response = this.requestJson(config, "/mapi.php", parameters, true, "创建易支付订单");
      if (response.getIntValue("code") != 1) {
         throw new BizException("易支付创建订单失败：" + safeMessage(response.getString("msg")));
      } else {
         String qrCode = response.getString("qrcode");
         String payUrl = firstText(response.getString("payurl"), response.getString("payurl2"));
         if (!StringUtils.hasText(qrCode) && !StringUtils.hasText(payUrl)) {
            throw new BizException("易支付未返回二维码或付款地址");
         } else {
            return new EasyPayClient.CreateResult(response.getString("trade_no"), qrCode, payUrl);
         }
      }
   }

   public EasyPayClient.QueryResult queryOrder(PaymentPurchaseSettingService.ProviderConfig config, String orderNo) {
      Map<String, String> parameters = new LinkedHashMap<>();
      parameters.put("act", "order");
      parameters.put("pid", config.merchantPid());
      parameters.put("key", config.merchantKey());
      parameters.put("out_trade_no", orderNo);
      JSONObject response = this.requestJson(config, "/api.php", parameters, false, "查询易支付订单");
      JSONObject data = response.getJSONObject("data");
      String tradeStatus = firstText(response.getString("trade_status"), value(data, "trade_status"));
      Integer status = response.getInteger("status");
      if (status == null && data != null) {
         status = data.getInteger("status");
      }

      String money = firstText(response.getString("money"), value(data, "money"));
      String tradeNo = firstText(response.getString("trade_no"), value(data, "trade_no"));
      boolean paid = "TRADE_SUCCESS".equals(tradeStatus) || !StringUtils.hasText(tradeStatus) && Integer.valueOf(1).equals(status);
      return new EasyPayClient.QueryResult(paid, parseAmount(money), tradeNo, safeMessage(firstText(response.getString("msg"), value(data, "msg"))));
   }

   public EasyPayClient.Notification verifyNotification(PaymentPurchaseSettingService.ProviderConfig config, Map<String, String> parameters) {
      if (parameters != null && StringUtils.hasText(parameters.get("sign"))) {
         String expected = sign(parameters, config.merchantKey());
         if (!MessageDigest.isEqual(
            expected.getBytes(StandardCharsets.US_ASCII), parameters.get("sign").trim().toLowerCase().getBytes(StandardCharsets.US_ASCII)
         )) {
            throw new BizException("易支付回调签名无效");
         } else if (!config.merchantPid().equals(parameters.get("pid"))) {
            throw new BizException("易支付回调商户号不匹配");
         } else {
            return new EasyPayClient.Notification(
               parameters.get("out_trade_no"),
               parameters.get("trade_no"),
               parseAmount(parameters.get("money")),
               "TRADE_SUCCESS".equals(parameters.get("trade_status"))
            );
         }
      } else {
         throw new BizException("易支付回调缺少签名");
      }
   }

   public static String sign(Map<String, String> parameters, String merchantKey) {
      String canonical = parameters.entrySet()
         .stream()
         .filter(entry -> !"sign".equals(entry.getKey()) && !"sign_type".equals(entry.getKey()))
         .filter(entry -> StringUtils.hasText(entry.getValue()))
         .sorted(Comparator.comparing(Entry::getKey))
         .map(entry -> entry.getKey() + "=" + entry.getValue())
         .collect(Collectors.joining("&"));
      return DigestUtil.md5Hex(canonical + (merchantKey == null ? "" : merchantKey)).toLowerCase();
   }

   private JSONObject requestJson(PaymentPurchaseSettingService.ProviderConfig config, String path, Map<String, String> parameters, boolean post, String action) {
      try {
         HttpClient client = HttpClient.newBuilder().connectTimeout(config.connectTimeout()).followRedirects(Redirect.NEVER).build();
         String encoded = formEncode(parameters);
         URI uri = URI.create(config.apiBaseUrl() + path + (post ? "" : "?" + encoded));
         Builder builder = HttpRequest.newBuilder(uri).timeout(config.requestTimeout());
         if (post) {
            builder.header("Content-Type", "application/x-www-form-urlencoded").POST(BodyPublishers.ofString(encoded));
         } else {
            builder.GET();
         }

         HttpResponse<String> response = client.send(builder.build(), BodyHandlers.ofString(StandardCharsets.UTF_8));
         if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return JSON.parseObject(response.body());
         } else {
            throw new BizException(action + "失败：上游 HTTP " + response.statusCode());
         }
      } catch (BizException var11) {
         throw var11;
      } catch (InterruptedException var12) {
         Thread.currentThread().interrupt();
         throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), action + "被中断，请稍后重试");
      } catch (Exception var13) {
         throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), action + "失败，请稍后重试");
      }
   }

   private static String formEncode(Map<String, String> parameters) {
      return parameters.entrySet()
         .stream()
         .filter(entry -> entry.getValue() != null)
         .map(entry -> urlEncode(entry.getKey()) + "=" + urlEncode(entry.getValue()))
         .collect(Collectors.joining("&"));
   }

   private static String urlEncode(String value) {
      return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
   }

   private static BigDecimal parseAmount(String value) {
      try {
         return StringUtils.hasText(value) ? new BigDecimal(value.trim()) : null;
      } catch (NumberFormatException var2) {
         return null;
      }
   }

   private static String value(JSONObject object, String key) {
      return object == null ? null : object.getString(key);
   }

   private static String firstText(String first, String second) {
      return StringUtils.hasText(first) ? first : second;
   }

   private static String safeMessage(String value) {
      return StringUtils.hasText(value) ? value.trim() : "未支付或上游未返回说明";
   }

   public static record CreateCommand(String orderNo, String paymentType, String productName, BigDecimal amount, String clientIp) {
   }

   public static record CreateResult(String tradeNo, String qrCode, String payUrl) {
   }

   public static record Notification(String orderNo, String tradeNo, BigDecimal amount, boolean paid) {
   }

   public static record QueryResult(boolean paid, BigDecimal amount, String tradeNo, String message) {
   }
}
