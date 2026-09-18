package com.una.embyhub.config.common.utils;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class TelegramClientUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramClientUtils.class);
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;

   public TelegramClient getTelegramClient() {
      TelegramClient telegramClient = null;
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("telegram");
      if (StringUtils.hasText(notifyChannelValue)) {
         TelegramResponse telegramResponse = JSONObject.parseObject(notifyChannelValue, TelegramResponse.class);
         telegramClient = new OkHttpTelegramClient(telegramResponse.getBotToken());
      }

      return telegramClient;
   }

   public TelegramResponse getTelegramResponse() {
      String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("telegram");
      if (!StringUtils.hasText(notifyChannelValue)) {
         log.info("Telegram配置未开启 ,不发送图片消息");
         return null;
      } else {
         return JSONObject.parseObject(notifyChannelValue, TelegramResponse.class);
      }
   }
}
