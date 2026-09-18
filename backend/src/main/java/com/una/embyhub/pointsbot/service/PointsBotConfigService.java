package com.una.embyhub.pointsbot.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.mapper.NotifyChannelMapper;
import com.una.embyhub.model.entity.NotifyChannel;
import com.una.embyhub.pointsbot.model.PointsBotConfig;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PointsBotConfigService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PointsBotConfigService.class);
   private final NotifyChannelMapper notifyChannelMapper;

   public PointsBotConfigService.PointsBotChannelConfig loadConfig() {
      NotifyChannel channel = this.findChannel("telegram");
      if (channel == null) {
         channel = this.findChannel("pointsBot");
      }

      if (channel == null) {
         return new PointsBotConfigService.PointsBotChannelConfig(false, new PointsBotConfig());
      } else {
         PointsBotConfig config = this.parseConfig(channel.getParams());
         boolean enabled = channel.getEnabled() != null && channel.getEnabled() == 1 && StringUtils.hasText(config.getGroupChatId());
         return new PointsBotConfigService.PointsBotChannelConfig(enabled, config);
      }
   }

   private NotifyChannel findChannel(String iconType) {
      return new LambdaQueryChainWrapper<>(this.notifyChannelMapper).eq(NotifyChannel::getIconType, iconType).last("limit 1").one();
   }

   private PointsBotConfig parseConfig(String params) {
      if (!StringUtils.hasText(params)) {
         return new PointsBotConfig();
      } else {
         try {
            JSONObject json = JSONObject.parseObject(params);
            PointsBotConfig parsed = json.toJavaObject(PointsBotConfig.class);
            if (parsed == null) {
               parsed = new PointsBotConfig();
            }

            if (!StringUtils.hasText(parsed.getGroupChatId())) {
               parsed.setGroupChatId(json.getString("botChatGroupId"));
            }

            if (!StringUtils.hasText(parsed.getDmChatId())) {
               parsed.setDmChatId(json.getString("botChatId"));
            }

            return PointsBotConfigRules.normalizeGameConfiguration(PointsBotConfigRules.normalizeTransferRange(parsed));
         } catch (Exception var4) {
            log.warn("积分机器人配置解析失败: {}", var4.getMessage());
            return new PointsBotConfig();
         }
      }
   }

   @Generated
   public PointsBotConfigService(final NotifyChannelMapper notifyChannelMapper) {
      this.notifyChannelMapper = notifyChannelMapper;
   }

   public static class PointsBotChannelConfig {
      private final boolean enabled;
      private final PointsBotConfig config;

      public PointsBotChannelConfig(boolean enabled, PointsBotConfig config) {
         this.enabled = enabled;
         this.config = config;
      }

      @Generated
      public boolean isEnabled() {
         return this.enabled;
      }

      @Generated
      public PointsBotConfig getConfig() {
         return this.config;
      }
   }
}
