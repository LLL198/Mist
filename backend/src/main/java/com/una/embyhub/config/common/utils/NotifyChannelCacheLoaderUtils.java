package com.una.embyhub.config.common.utils;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.model.entity.NotifyChannel;
import com.una.embyhub.service.NotifyChannelService;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NotifyChannelCacheLoaderUtils {
   @Autowired
   private NotifyChannelService notifyChannelService;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   private static final String REDIS_KEY = "notify:channel:config";

   @PostConstruct
   public void init() {
      this.loadConfigCache();
   }

   public void loadConfigCache() {
      List<NotifyChannel> notifyChannelList = new LambdaQueryChainWrapper<>(this.notifyChannelService.getBaseMapper())
         .eq(NotifyChannel::getEnabled, Integer.valueOf(1))
         .list();
      Map<String, String> tempCache = new HashMap<>();
      notifyChannelList.forEach(notifyChannel -> {
         String params = notifyChannel.getParams();
         if ("telegram".equals(notifyChannel.getIconType())) {
            JSONObject telegramParams = JSONObject.parseObject(params);
            if (telegramParams == null) {
               telegramParams = new JSONObject();
            }

            if (StringUtils.hasText(notifyChannel.getCustomIcon())) {
               telegramParams.put("startPanelImage", notifyChannel.getCustomIcon());
            } else {
               telegramParams.remove("startPanelImage");
            }

            params = telegramParams.toJSONString();
         }

         tempCache.put(notifyChannel.getIconType(), params);
      });
      this.redisTemplate.delete("notify:channel:config");
      if (!tempCache.isEmpty()) {
         this.redisTemplate.<String, String>opsForHash().putAll("notify:channel:config", tempCache);
      }
   }

   public String getNotifyChannelValue(String key) {
      Object value = this.redisTemplate.opsForHash().get("notify:channel:config", key);
      return value != null ? value.toString() : null;
   }

   public Map<String, String> getAllNotifyChannels() {
      Map<Object, Object> entries = this.redisTemplate.<Object, Object>opsForHash().entries("notify:channel:config");
      Map<String, String> result = new HashMap<>();
      entries.forEach((k, v) -> result.put(k.toString(), v.toString()));
      return result;
   }

   public void refreshCache() {
      this.loadConfigCache();
   }
}
