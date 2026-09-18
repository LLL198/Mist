package com.una.embyhub.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.config.common.utils.WechatBotUtils;
import com.una.embyhub.config.common.utils.WechatWorkUtils;
import com.una.embyhub.config.common.wechatbot.WechatBotProperties;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.response.embynotifydata.WechatResponse;
import com.una.embyhub.service.WechatService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WechatServiceImpl implements WechatService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatServiceImpl.class);
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   private static final String DATA_URL_PREFIX = "data:image/jpeg;base64,";

   @Override
   public boolean sendPhoto(SendPhotoRequest sendPhotoRequest) throws Exception {
      return this.sendPhoto(sendPhotoRequest, "wechat");
   }

   @Override
   public boolean sendPhoto(SendPhotoRequest sendPhotoRequest, String channelType) throws Exception {
      String channel = this.normalizeChannel(channelType);
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue(channel);
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("企业微信配置未开启 ,不发送图片消息");
         return false;
      } else if (this.isWechatBot(channel)) {
         return this.sendWechatBotPhoto(sendPhotoRequest, notifyChannelValue);
      } else {
         WechatResponse wechatResponse = JSONObject.parseObject(notifyChannelValue, WechatResponse.class);
         String webhookUrl = this.resolveWebhookUrl(wechatResponse);
         if (!StringUtils.hasText(webhookUrl)) {
            log.warn("企业微信 Webhook 未配置，取消推送");
            return false;
         } else {
            String description = this.buildContent(sendPhotoRequest.getCaption(), null);
            description = this.truncateTo512Bytes(this.normalizeLineBreaks(description));
            String picUrl = StringUtils.hasText(sendPhotoRequest.getBackdropPath()) ? sendPhotoRequest.getBackdropPath() : sendPhotoRequest.getImgUrl();
            picUrl = this.resolvePicUrlWithServer(sendPhotoRequest, picUrl);
            String linkUrl = StringUtils.hasText(sendPhotoRequest.getTmdbUrl()) ? sendPhotoRequest.getTmdbUrl() : sendPhotoRequest.getServerUrl();
            if (this.trySendCustomPoster(webhookUrl, picUrl, sendPhotoRequest)) {
               return true;
            } else {
               WechatWorkUtils.sendNewsMessage(webhookUrl, sendPhotoRequest.getName(), description, linkUrl, picUrl);
               return true;
            }
         }
      }
   }

   @Override
   public boolean sendServerPhoto(SendPhotoRequest sendPhotoRequest) throws Exception {
      return this.sendServerPhoto(sendPhotoRequest, "wechat");
   }

   @Override
   public boolean sendServerPhoto(SendPhotoRequest sendPhotoRequest, String channelType) throws Exception {
      String channel = this.normalizeChannel(channelType);
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue(channel);
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("企业微信配置未开启 ,不发送图片消息");
         return false;
      } else if (this.isWechatBot(channel)) {
         return this.sendWechatBotServerPhoto(sendPhotoRequest, notifyChannelValue);
      } else {
         WechatResponse wechatResponse = JSONObject.parseObject(notifyChannelValue, WechatResponse.class);
         String webhookUrl = this.resolveWebhookUrl(wechatResponse);
         if (!StringUtils.hasText(webhookUrl)) {
            log.warn("企业微信 Webhook 未配置，取消推送");
            return false;
         } else {
            String picUrl = StringUtils.hasText(sendPhotoRequest.getBackdropPath()) ? sendPhotoRequest.getBackdropPath() : sendPhotoRequest.getImgUrl();
            picUrl = this.resolvePicUrlWithServer(sendPhotoRequest, picUrl);
            byte[] imageBytes = this.extractImageBytes(sendPhotoRequest, picUrl);
            if (imageBytes == null) {
               log.warn("未获取到图片数据，改用图文消息发送");
               return this.sendPhoto(sendPhotoRequest, channel);
            } else {
               WechatWorkUtils.sendImageMessage(webhookUrl, imageBytes);
               return true;
            }
         }
      }
   }

   @Override
   public boolean sendMessage(SendMessageRequest sendMessageRequest) throws Exception {
      return this.sendMessage(sendMessageRequest, "wechat");
   }

   @Override
   public boolean sendMessage(SendMessageRequest sendMessageRequest, String channelType) throws Exception {
      String channel = this.normalizeChannel(channelType);
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue(channel);
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("企业微信配置未开启 ,不发送文本消息");
         return false;
      } else if (this.isWechatBot(channel)) {
         return this.sendWechatBotMessage(sendMessageRequest, notifyChannelValue);
      } else {
         WechatResponse wechatResponse = JSONObject.parseObject(notifyChannelValue, WechatResponse.class);
         String webhookUrl = this.resolveWebhookUrl(wechatResponse);
         if (!StringUtils.hasText(webhookUrl)) {
            log.warn("企业微信 Webhook 未配置，取消推送");
            return false;
         } else {
            String nameSection = StringUtils.hasText(sendMessageRequest.getName()) ? "**" + sendMessageRequest.getName() + "**" : "";
            String content = this.buildContent(nameSection, sendMessageRequest.getOverview());
            WechatWorkUtils.sendMarkdownMessage(webhookUrl, this.normalizeLineBreaks(content));
            return true;
         }
      }
   }

   @Override
   public boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest) throws Exception {
      return this.sendPhotoMessage(sendPhotoRequest, "wechat");
   }

   @Override
   public boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest, String channelType) throws Exception {
      return this.sendPhoto(sendPhotoRequest, channelType);
   }

   private String normalizeChannel(String channelType) {
      return StringUtils.hasText(channelType) ? channelType : "wechat";
   }

   private boolean isWechatBot(String channel) {
      return "wechatBot".equals(channel);
   }

   private String resolveWebhookUrl(WechatResponse wechatResponse) {
      if (wechatResponse == null) {
         return null;
      } else {
         return StringUtils.hasText(wechatResponse.getWebhookUrl()) ? wechatResponse.getWebhookUrl() : wechatResponse.getWebhookKey();
      }
   }

   private String normalizeLineBreaks(String text) {
      return !StringUtils.hasText(text) ? text : text.replace("\r\n", "\n").replace("\\r\\n", "\n").replace("\\n", "\n");
   }

   private String buildContent(String baseSection, String overview) {
      StringBuilder builder = new StringBuilder();
      if (StringUtils.hasText(baseSection)) {
         builder.append(baseSection.trim());
      }

      if (StringUtils.hasText(overview)) {
         builder.append("\n");
         builder.append(overview.trim());
      }

      return builder.toString();
   }

   private String truncateTo512Bytes(String text) {
      if (!StringUtils.hasText(text)) {
         return "";
      } else {
         byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
         if (bytes.length <= 512) {
            return text;
         } else {
            int maxBytes = 509;
            int currentBytes = 0;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {
               char c = text.charAt(i);
               int charSize = String.valueOf(c).getBytes(StandardCharsets.UTF_8).length;
               if (currentBytes + charSize > maxBytes) {
                  break;
               }

               sb.append(c);
               currentBytes += charSize;
            }

            sb.append("...");
            return sb.toString();
         }
      }
   }

   private boolean trySendCustomPoster(String webhookUrl, String picUrl, SendPhotoRequest request) {
      if (!this.isCustomPosterEnabled()) {
         return false;
      } else {
         byte[] imageBytes = null;
         if (this.isDataUrl(picUrl)) {
            imageBytes = this.decodeDataUrl(picUrl);
         } else if (request.getImgUrlInputStream() != null) {
            try {
               imageBytes = request.getImgUrlInputStream().readAllBytes();
               if (request.getImgUrlInputStream().markSupported()) {
                  request.getImgUrlInputStream().reset();
               } else {
                  request.setImgUrlInputStream(new ByteArrayInputStream(imageBytes));
               }
            } catch (Exception var6) {
               log.warn("读取自定义海报流失败", (Throwable)var6);
            }
         }

         if (imageBytes == null) {
            if (this.isDataUrl(picUrl)) {
               log.warn("解析自定义海报 Base64 失败，使用图文消息发送");
            }

            return false;
         } else {
            WechatWorkUtils.sendImageMessage(webhookUrl, imageBytes);
            return true;
         }
      }
   }

   private byte[] extractImageBytes(SendPhotoRequest sendPhotoRequest, String picUrl) {
      if (this.isDataUrl(picUrl)) {
         return this.decodeDataUrl(picUrl);
      } else {
         if (sendPhotoRequest.getImgUrlInputStream() != null) {
            try {
               return sendPhotoRequest.getImgUrlInputStream().readAllBytes();
            } catch (Exception var12) {
               log.warn("读取图片流失败", (Throwable)var12);
            }
         }

         if (StringUtils.hasText(picUrl) && (picUrl.startsWith("http://") || picUrl.startsWith("https://"))) {
            try {
               byte[] var14;
               try (InputStream in = new URL(picUrl).openStream()) {
                  var14 = in.readAllBytes();
               }

               return var14;
            } catch (Exception var11) {
               log.warn("下载图片失败: {}", picUrl, var11);
            }
         }

         try (InputStream defaultIn = ResourceUtil.getStream("img/default.jpg")) {
            return defaultIn != null ? defaultIn.readAllBytes() : null;
         } catch (Exception var9) {
            log.warn("加载默认图片失败", (Throwable)var9);
            return null;
         }
      }
   }

   private boolean isCustomPosterEnabled() {
      String value = this.configCacheLoaderUtils.getConfigValue("custom_poster_enabled");
      return "true".equalsIgnoreCase(value);
   }

   private boolean sendWechatBotPhoto(SendPhotoRequest sendPhotoRequest, String notifyChannelValue) {
      WechatBotProperties properties = this.parseWechatBotProperties(notifyChannelValue);
      if (!this.hasWechatBotMessageConfig(properties)) {
         log.warn("企业微信应用配置不完整，取消推送");
         return false;
      } else {
         String description = this.truncateTo512Bytes(this.normalizeLineBreaks(this.buildContent(sendPhotoRequest.getCaption(), null)));
         String picUrl = StringUtils.hasText(sendPhotoRequest.getBackdropPath()) ? sendPhotoRequest.getBackdropPath() : sendPhotoRequest.getImgUrl();
         picUrl = this.resolvePicUrlWithServer(sendPhotoRequest, picUrl);
         String linkUrl = StringUtils.hasText(sendPhotoRequest.getTmdbUrl()) ? sendPhotoRequest.getTmdbUrl() : sendPhotoRequest.getServerUrl();
         return this.trySendWechatBotImage(properties, sendPhotoRequest, picUrl)
            ? true
            : WechatBotUtils.sendNewsMessage(properties, sendPhotoRequest.getName(), description, linkUrl, picUrl);
      }
   }

   private boolean sendWechatBotServerPhoto(SendPhotoRequest sendPhotoRequest, String notifyChannelValue) throws Exception {
      WechatBotProperties properties = this.parseWechatBotProperties(notifyChannelValue);
      if (!this.hasWechatBotMessageConfig(properties)) {
         log.warn("企业微信应用配置不完整，取消推送");
         return false;
      } else {
         String picUrl = StringUtils.hasText(sendPhotoRequest.getBackdropPath()) ? sendPhotoRequest.getBackdropPath() : sendPhotoRequest.getImgUrl();
         picUrl = this.resolvePicUrlWithServer(sendPhotoRequest, picUrl);
         byte[] imageBytes = this.extractImageBytes(sendPhotoRequest, picUrl);
         if (imageBytes == null) {
            log.warn("未获取到图片数据，改用图文消息发送");
            return this.sendWechatBotPhoto(sendPhotoRequest, notifyChannelValue);
         } else if (WechatBotUtils.sendImageMessage(properties, imageBytes)) {
            return true;
         } else {
            log.warn("企业微信应用图片发送失败，改用图文消息发送");
            return this.sendWechatBotPhoto(sendPhotoRequest, notifyChannelValue);
         }
      }
   }

   private boolean sendWechatBotMessage(SendMessageRequest sendMessageRequest, String notifyChannelValue) {
      WechatBotProperties properties = this.parseWechatBotProperties(notifyChannelValue);
      if (!this.hasWechatBotMessageConfig(properties)) {
         log.warn("企业微信应用配置不完整，取消推送");
         return false;
      } else {
         String nameSection = StringUtils.hasText(sendMessageRequest.getName()) ? "**" + sendMessageRequest.getName() + "**" : "";
         String content = this.buildContent(nameSection, sendMessageRequest.getOverview());
         return WechatBotUtils.sendMarkdownMessage(properties, this.normalizeLineBreaks(content));
      }
   }

   private boolean trySendWechatBotImage(WechatBotProperties properties, SendPhotoRequest sendPhotoRequest, String picUrl) {
      if (!this.isCustomPosterEnabled()) {
         return false;
      } else {
         byte[] imageBytes = null;
         if (this.isDataUrl(picUrl)) {
            imageBytes = this.decodeDataUrl(picUrl);
         } else if (sendPhotoRequest.getImgUrlInputStream() != null) {
            imageBytes = this.extractImageBytes(sendPhotoRequest, picUrl);
         }

         return imageBytes == null ? false : WechatBotUtils.sendImageMessage(properties, imageBytes);
      }
   }

   private boolean hasWechatBotMessageConfig(WechatBotProperties properties) {
      return properties != null
         && StringUtils.hasText(properties.getCorpId())
         && StringUtils.hasText(properties.getAppSecret())
         && StringUtils.hasText(properties.getAgentId());
   }

   private WechatBotProperties parseWechatBotProperties(String notifyChannelValue) {
      return !StringUtils.hasText(notifyChannelValue) ? null : JSONObject.parseObject(notifyChannelValue, WechatBotProperties.class);
   }

   private boolean isDataUrl(String picUrl) {
      return StringUtils.hasText(picUrl) && picUrl.startsWith("data:image/jpeg;base64,");
   }

   private byte[] decodeDataUrl(String picUrl) {
      try {
         return Base64.getDecoder().decode(picUrl.substring("data:image/jpeg;base64,".length()));
      } catch (IllegalArgumentException var3) {
         log.warn("Base64 解码失败", (Throwable)var3);
         return null;
      }
   }

   private String resolvePicUrlWithServer(SendPhotoRequest sendPhotoRequest, String picUrl) {
      if (StringUtils.hasText(picUrl) && !this.isDataUrl(picUrl) && !picUrl.startsWith("http")) {
         String serverUrl = sendPhotoRequest.getServerUrl();
         if (!StringUtils.hasText(serverUrl)) {
            return picUrl;
         } else {
            boolean serverEndsWithSlash = serverUrl.endsWith("/");
            boolean picStartsWithSlash = picUrl.startsWith("/");
            if (serverEndsWithSlash && picStartsWithSlash) {
               return serverUrl.substring(0, serverUrl.length() - 1) + picUrl;
            } else {
               return !serverEndsWithSlash && !picStartsWithSlash ? serverUrl + "/" + picUrl : serverUrl + picUrl;
            }
         }
      } else {
         return picUrl;
      }
   }
}
