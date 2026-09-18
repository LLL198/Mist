package com.una.embyhub.config.common.telegrambot;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.pointsbot.telegram.TelegramBotApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BotInitializer {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(BotInitializer.class);
   @Autowired
   private DataQueryBot dataQueryBot;
   @Autowired
   private NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   private TelegramBotApiClient mtProtoClient;

   @PostConstruct
   public void start() {
      try {
         String notifyChannelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("telegram");
         if (StringUtils.hasText(notifyChannelValue)) {
            TelegramResponse telegramResponse = JSONObject.parseObject(notifyChannelValue, TelegramResponse.class);
            if (!StringUtils.hasText(telegramResponse.getBotToken())
               || telegramResponse.getApiId() == null
               || telegramResponse.getApiId() <= 0
               || !StringUtils.hasText(telegramResponse.getApiHash())) {
               log.info("Telegram MTProto 参数未配置完整，机器人不启动");
               return;
            }

            this.mtProtoClient = new TelegramBotApiClient(
               telegramResponse.getBotToken(), telegramResponse.getApiId(), telegramResponse.getApiHash(), null, this.dataQueryBot::consume
            );
            this.dataQueryBot.beginTelegramConsumption(this.mtProtoClient);
            this.mtProtoClient.start();
            this.dataQueryBot.attachMtProtoClient(this.mtProtoClient);
            log.info("Telegram MTProto 机器人成功启动！");
         } else {
            log.info("Telegram配置未开启，机器人不启动");
         }
      } catch (Exception var3) {
         log.error("启动机器人失败: " + var3.getMessage());
         var3.printStackTrace();
      }
   }

   @PreDestroy
   public void stop() {
      if (this.mtProtoClient != null) {
         this.mtProtoClient.close();
      }
   }
}
