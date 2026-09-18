package com.una.embyhub.pointsbot.config;

import com.una.embyhub.config.common.telegrambot.DataQueryBot;
import com.una.embyhub.pointsbot.PointsBot;
import jakarta.annotation.PostConstruct;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PointsBotInitializer {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PointsBotInitializer.class);
   private final PointsBot pointsBot;
   private final DataQueryBot dataQueryBot;

   @PostConstruct
   public synchronized void start() {
      this.reloadOnly();
   }

   public synchronized void restart() {
      this.reloadOnly();
   }

   private void reloadOnly() {
      try {
         this.pointsBot.reloadConfig();
         this.dataQueryBot.initCommands();
         log.info("积分机器人配置与 Telegram 命令菜单已重载。");
      } catch (Exception var2) {
         log.error("积分机器人配置重载失败: {}", var2.getMessage());
      }
   }

   @Generated
   public PointsBotInitializer(final PointsBot pointsBot, final DataQueryBot dataQueryBot) {
      this.pointsBot = pointsBot;
      this.dataQueryBot = dataQueryBot;
   }
}
