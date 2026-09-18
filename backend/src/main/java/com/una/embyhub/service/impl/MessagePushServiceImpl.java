package com.una.embyhub.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.response.embynotifydata.MessagePushResponse;
import com.una.embyhub.service.MessagePushService;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessagePushServiceImpl implements MessagePushService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MessagePushServiceImpl.class);
   private static final String BASE_URL = "https://messagepush.luckfast.com/send";
   private static final String DATA_URL_PREFIX = "data:";
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;

   @Override
   public boolean sendPhoto(SendPhotoRequest sendPhotoRequest) throws Exception {
      return this.sendPhotoMessage(sendPhotoRequest);
   }

   @Override
   public boolean sendMessage(SendMessageRequest sendMessageRequest) throws Exception {
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("messagepush");
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("消息推送助手配置未开启 ,不发送文本消息");
         return false;
      } else {
         MessagePushResponse config = JSONObject.parseObject(notifyChannelValue, MessagePushResponse.class);
         if (!this.isConfigValid(config)) {
            log.warn("消息推送助手配置缺失 userId 或 userKey");
            return false;
         } else {
            String title = sendMessageRequest.getName();
            String message = sendMessageRequest.getOverview();
            if (!StringUtils.hasText(message)) {
               log.info("消息推送助手文本内容为空，取消发送");
               return false;
            } else {
               Map<String, Object> params = this.buildParams(title, null, message, this.resolveUrl(sendMessageRequest.getTmdbUrl()), null);
               return this.send(config, params);
            }
         }
      }
   }

   @Override
   public boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest) throws Exception {
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("messagepush");
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("消息推送助手配置未开启 ,不发送图片消息");
         return false;
      } else {
         MessagePushResponse config = JSONObject.parseObject(notifyChannelValue, MessagePushResponse.class);
         if (!this.isConfigValid(config)) {
            log.warn("消息推送助手配置缺失 userId 或 userKey");
            return false;
         } else {
            String title = sendPhotoRequest.getName();
            String subtitle = sendPhotoRequest.getPlayTitle();
            String message = sendPhotoRequest.getCaption();
            if (!StringUtils.hasText(message)) {
               log.info("消息推送助手图片内容为空，取消发送");
               return false;
            } else {
               String url = this.resolveUrl(sendPhotoRequest.getTmdbUrl());
               String image = this.resolveImageUrl(sendPhotoRequest.getBackdropPath(), sendPhotoRequest.getImgUrl());
               Map<String, Object> params = this.buildParams(title, subtitle, message, url, image);
               return this.send(config, params);
            }
         }
      }
   }

   private boolean send(MessagePushResponse config, Map<String, Object> params) {
      String url = String.format("%s/%s/%s", "https://messagepush.luckfast.com/send", config.getUserId(), config.getUserKey());

      try {
         String requestUrl = HttpUtil.urlWithForm(url, params, CharsetUtil.CHARSET_UTF_8, false);
         String response = HttpUtil.get(requestUrl);
         log.info("消息推送助手发送结果: {}", response);
         return true;
      } catch (Exception var6) {
         log.error("消息推送助手发送失败", (Throwable)var6);
         return false;
      }
   }

   private Map<String, Object> buildParams(String title, String subtitle, String message, String url, String image) {
      Map<String, Object> params = new HashMap<>();
      if (StringUtils.hasText(title)) {
         params.put("title", title);
      }

      if (StringUtils.hasText(subtitle)) {
         params.put("subtitle", subtitle);
      }

      if (StringUtils.hasText(message)) {
         params.put("message", message);
      }

      if (StringUtils.hasText(url)) {
         params.put("url", url);
      }

      if (StringUtils.hasText(image)) {
         params.put("image", image);
         params.put("icon", image);
      }

      return params;
   }

   private String resolveUrl(String tmdbUrl) {
      return StringUtils.hasText(tmdbUrl) ? tmdbUrl : null;
   }

   private String resolveImageUrl(String backdropPath, String imgUrl) {
      String candidate = StringUtils.hasText(backdropPath) ? backdropPath : imgUrl;
      if (!StringUtils.hasText(candidate)) {
         return null;
      } else if (candidate.startsWith("data:")) {
         return null;
      } else {
         return !candidate.startsWith("http://") && !candidate.startsWith("https://") ? null : candidate;
      }
   }

   private boolean isConfigValid(MessagePushResponse config) {
      return config != null && StringUtils.hasText(config.getUserId()) && StringUtils.hasText(config.getUserKey());
   }
}
