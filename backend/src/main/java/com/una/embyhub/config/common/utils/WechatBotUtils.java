package com.una.embyhub.config.common.utils;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.wechatbot.WechatBotProperties;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class WechatBotUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatBotUtils.class);
   private static final String TOKEN_API = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
   private static final String MESSAGE_API = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
   private static final String MEDIA_UPLOAD_API = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=image";

   private WechatBotUtils() {
   }

   public static boolean sendMarkdownMessage(WechatBotProperties properties, String content) {
      return sendMarkdownMessage(properties, content, "@all");
   }

   public static boolean sendMarkdownMessage(WechatBotProperties properties, String content, String toUser) {
      String accessToken = fetchAccessToken(properties);
      if (!StringUtils.hasText(accessToken)) {
         return false;
      } else {
         Map<String, Object> payload = basePayload(properties, toUser);
         payload.put("msgtype", "markdown");
         payload.put("markdown", Map.of("content", content));
         return postMessage(accessToken, payload);
      }
   }

   public static boolean sendNewsMessage(WechatBotProperties properties, String title, String description, String url, String picUrl) {
      String accessToken = fetchAccessToken(properties);
      if (!StringUtils.hasText(accessToken)) {
         return false;
      } else {
         Map<String, Object> article = new HashMap<>();
         article.put("title", StringUtils.hasText(title) ? title : "");
         article.put("description", StringUtils.hasText(description) ? description : "");
         if (StringUtils.hasText(url)) {
            article.put("url", url);
         }

         if (StringUtils.hasText(picUrl)) {
            article.put("picurl", picUrl);
         }

         Map<String, Object> payload = basePayload(properties, "@all");
         payload.put("msgtype", "news");
         payload.put("news", Map.of("articles", List.of(article)));
         return postMessage(accessToken, payload);
      }
   }

   public static boolean sendImageMessage(WechatBotProperties properties, byte[] imageBytes) {
      if (imageBytes != null && imageBytes.length != 0) {
         String accessToken = fetchAccessToken(properties);
         if (!StringUtils.hasText(accessToken)) {
            return false;
         } else {
            String mediaId = uploadImage(accessToken, imageBytes);
            if (!StringUtils.hasText(mediaId)) {
               return false;
            } else {
               Map<String, Object> payload = basePayload(properties, "@all");
               payload.put("msgtype", "image");
               payload.put("image", Map.of("media_id", mediaId));
               return postMessage(accessToken, payload);
            }
         }
      } else {
         log.warn("企业微信应用图片为空，取消发送");
         return false;
      }
   }

   private static String fetchAccessToken(WechatBotProperties properties) {
      if (properties != null && StringUtils.hasText(properties.getCorpId()) && StringUtils.hasText(properties.getAppSecret())) {
         RestTemplate restTemplate = new RestTemplate();
         String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s", properties.getCorpId(), properties.getAppSecret());
         ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
         JSONObject body = JSONObject.parseObject(response.getBody());
         if (body == null) {
            log.warn("企业微信 access_token 响应为空");
            return null;
         } else {
            Integer errCode = body.getInteger("errcode");
            if (errCode != null && errCode != 0) {
               log.warn("获取企业微信 access_token 失败，errcode={}，errmsg={}", errCode, body.getString("errmsg"));
               return null;
            } else {
               return body.getString("access_token");
            }
         }
      } else {
         log.warn("企业微信应用缺少 corpId 或 appSecret，无法获取 access_token");
         return null;
      }
   }

   private static Map<String, Object> basePayload(WechatBotProperties properties) {
      return basePayload(properties, "@all");
   }

   private static Map<String, Object> basePayload(WechatBotProperties properties, String toUser) {
      Map<String, Object> payload = new HashMap<>();
      payload.put("touser", StringUtils.hasText(toUser) ? toUser : "@all");
      payload.put("agentid", properties.getAgentId());
      return payload;
   }

   private static boolean postMessage(String accessToken, Map<String, Object> payload) {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
      String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s", accessToken);
      ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
      JSONObject body = JSONObject.parseObject(response.getBody());
      if (body == null) {
         log.warn("企业微信应用发送返回空响应");
         return false;
      } else {
         Integer errCode = body.getInteger("errcode");
         if (errCode != null && errCode == 0) {
            log.info("企业微信应用消息发送成功");
            return true;
         } else {
            log.warn("企业微信应用发送失败，errcode={}，errmsg={}", errCode, body.getString("errmsg"));
            return false;
         }
      }
   }

   private static String uploadImage(String accessToken, byte[] imageBytes) {
      RestTemplate restTemplate = new RestTemplate();
      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("media", new ByteArrayResource(imageBytes) {
         @Override
         public String getFilename() {
            return "image.jpg";
         }
      });
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
      String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=image", accessToken);
      ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
      JSONObject json = JSONObject.parseObject(response.getBody());
      if (json == null) {
         log.warn("企业微信图片上传返回空响应");
         return null;
      } else {
         Integer errCode = json.getInteger("errcode");
         if (errCode != null && errCode == 0) {
            return json.getString("media_id");
         } else {
            log.warn("企业微信图片上传失败，errcode={}，errmsg={}", errCode, json.getString("errmsg"));
            return null;
         }
      }
   }
}
