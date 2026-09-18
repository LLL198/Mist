package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.DingDingUtils;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.response.embynotifydata.DingDingResponse;
import com.una.embyhub.service.DingDingService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DingDingServiceImpl implements DingDingService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DingDingServiceImpl.class);
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;

   @Override
   public boolean sendPhoto(SendPhotoRequest sendPhotoRequest) throws Exception {
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("dingding");
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("钉钉配置未开启 ,不发送图片消息");
         return false;
      } else {
         log.info("钉钉开始发送图片消息：{}", JSONObject.toJSONString(sendPhotoRequest));
         DingDingResponse dingDingResponse = JSONObject.parseObject(notifyChannelValue, DingDingResponse.class);
         String content = sendPhotoRequest.getCaption();
         if (!StringUtils.hasText(content)) {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.hasText(sendPhotoRequest.getBackdropPath())) {
               sb.append("![钉钉发送图片url](" + sendPhotoRequest.getBackdropPath() + ")\n");
            }

            if (!"Movie".equals(sendPhotoRequest.getType()) && !"movie".equals(sendPhotoRequest.getType())) {
               sb.append("名称：" + sendPhotoRequest.getName());
               sb.append("\n\n");
               if (StringUtils.hasText(sendPhotoRequest.getTvInfo())) {
                  sb.append(sendPhotoRequest.getTvInfo());
                  sb.append("\n\n");
               }
            } else {
               sb.append("名称：" + sendPhotoRequest.getName());
               sb.append("\n\n");
            }

            if (StringUtils.hasText(sendPhotoRequest.getDisplayTitle())) {
               sb.append("\ud83d\udcfa 分辨率：" + sendPhotoRequest.getDisplayTitle());
               sb.append("\n\n");
            }

            if (StringUtils.hasText(sendPhotoRequest.getGenres())) {
               sb.append("\ud83c\udff7 标签：" + sendPhotoRequest.getGenres());
               sb.append("\n\n");
            }

            sb.append("\ud83d\uddc2 类型：" + (!"Movie".equals(sendPhotoRequest.getType()) && !"movie".equals(sendPhotoRequest.getType()) ? "#剧集" : "#电影"));
            sb.append("\n\n");
            if (sendPhotoRequest.getSize() != null && !"0".equals(sendPhotoRequest.getSize())) {
               sb.append("\ud83d\udce6 文件大小：" + sendPhotoRequest.getSize());
               sb.append("\n\n");
            }

            sb.append("\n\n");
            sb.append("简介：" + sendPhotoRequest.getOverview());
            content = sb.toString();
         }

         DingDingUtils.sendMarkdownMessage(dingDingResponse.getAccessToken(), dingDingResponse.getSecret(), sendPhotoRequest.getName(), content);
         return true;
      }
   }

   @Override
   public boolean sendMessage(SendMessageRequest sendMessageRequest) throws Exception {
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("dingding");
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("钉钉配置未开启 ,不发送图片消息");
         return false;
      } else {
         DingDingResponse dingDingResponse = JSONObject.parseObject(notifyChannelValue, DingDingResponse.class);
         DingDingUtils.sendMarkdownMessage(
            dingDingResponse.getAccessToken(), dingDingResponse.getSecret(), sendMessageRequest.getName(), sendMessageRequest.getOverview()
         );
         return true;
      }
   }

   @Override
   public boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest) throws Exception {
      String content = sendPhotoRequest.getCaption();
      if (!StringUtils.hasText(content)) {
         StringBuilder sb = new StringBuilder();
         if (StringUtils.hasText(sendPhotoRequest.getBackdropPath())) {
            sb.append("![钉钉发送图片url](" + sendPhotoRequest.getBackdropPath() + ")\n");
         }

         sb.append(sendPhotoRequest.getOverview());
         content = sb.toString();
      }

      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("dingding");
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("钉钉配置未开启 ,不发送图片消息");
         return false;
      } else {
         log.info("钉钉开始发送图片文字消息：{}", JSONObject.toJSONString(sendPhotoRequest));
         DingDingResponse dingDingResponse = JSONObject.parseObject(notifyChannelValue, DingDingResponse.class);
         DingDingUtils.sendMarkdownMessage(dingDingResponse.getAccessToken(), dingDingResponse.getSecret(), sendPhotoRequest.getName(), content);
         return true;
      }
   }
}
