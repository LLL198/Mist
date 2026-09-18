package com.una.embyhub.config.common.utils;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class WechatWorkUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatWorkUtils.class);
   private static final String WEBHOOK_BASE = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=";

   private WechatWorkUtils() {
   }

   public static void sendMarkdownMessage(String webhookUrl, String content) {
      Map<String, Object> payload = new HashMap<>();
      payload.put("msgtype", "markdown");
      payload.put("markdown", Collections.singletonMap("content", content));
      post(webhookUrl, payload);
   }

   public static void sendNewsMessage(String webhookUrl, String title, String description, String url, String picUrl) {
      Map<String, Object> payload = new HashMap<>();
      payload.put("msgtype", "news");
      Map<String, Object> article = new HashMap<>();
      article.put("title", StringUtils.hasText(title) ? title : "");
      article.put("description", StringUtils.hasText(description) ? description : "");
      if (StringUtils.hasText(url)) {
         article.put("url", url);
      }

      if (StringUtils.hasText(picUrl)) {
         article.put("picurl", picUrl);
      }

      Map<String, Object> news = new HashMap<>();
      news.put("articles", Collections.singletonList(article));
      payload.put("news", news);
      post(webhookUrl, payload);
   }

   public static void sendImageMessage(String webhookUrl, byte[] imageBytes) {
      if (imageBytes != null && imageBytes.length != 0) {
         Map<String, Object> payload = new HashMap<>();
         payload.put("msgtype", "image");
         Map<String, Object> image = new HashMap<>();
         image.put("base64", Base64.getEncoder().encodeToString(imageBytes));
         image.put("md5", md5Hex(imageBytes));
         payload.put("image", image);
         post(webhookUrl, payload);
      } else {
         log.warn("图片数据为空，取消企业微信图片推送");
      }
   }

   private static void post(String webhookUrl, Map<String, Object> payload) {
      String resolvedWebhook = resolveWebhook(webhookUrl);
      if (!StringUtils.hasText(resolvedWebhook)) {
         log.warn("企业微信 Webhook 地址为空，取消推送");
      } else {
         RestTemplate restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
         ResponseEntity<String> response = restTemplate.postForEntity(resolvedWebhook, requestEntity, String.class);
         if (response.getStatusCode().is2xxSuccessful()) {
            log.info("企业微信消息发送成功");
         } else {
            log.error("企业微信消息发送失败，状态码：{}，响应：{}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("企业微信消息发送失败");
         }
      }
   }

   private static String resolveWebhook(String webhookUrlOrKey) {
      if (!StringUtils.hasText(webhookUrlOrKey)) {
         return webhookUrlOrKey;
      } else {
         return webhookUrlOrKey.startsWith("http") ? webhookUrlOrKey : "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + webhookUrlOrKey;
      }
   }

   private static String md5Hex(byte[] data) {
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] digest = md.digest(data);
         StringBuilder sb = new StringBuilder();

         for (byte b : digest) {
            sb.append(String.format("%02x", b));
         }

         return sb.toString();
      } catch (Exception var8) {
         throw new RuntimeException("计算图片 MD5 失败", var8);
      }
   }
}
