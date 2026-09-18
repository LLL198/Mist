package com.una.embyhub.config.common.utils;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.mapper.NotifyTemplateMapper;
import com.una.embyhub.model.entity.NotifyTemplate;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NotifyTemplateCacheLoaderUtils {
   private static final String COMMON_CHANNEL = "common";
   private static final String REDIS_KEY = "notify:template";
   @Autowired
   private NotifyTemplateMapper notifyTemplateMapper;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;

   @PostConstruct
   public void init() {
      this.refreshCache();
   }

   public void refreshCache() {
      List<NotifyTemplate> templates = new LambdaQueryChainWrapper<>(this.notifyTemplateMapper).eq(NotifyTemplate::getEnabled, Integer.valueOf(1)).list();
      Map<String, String> temp = new HashMap<>();

      for (NotifyTemplate template : templates) {
         String channel = StringUtils.hasText(template.getChannelType()) ? template.getChannelType() : "common";
         temp.put(this.buildKey(template.getTemplateCode(), channel), template.getTemplateContent());
      }

      this.redisTemplate.delete("notify:template");
      if (!temp.isEmpty()) {
         this.redisTemplate.<String, String>opsForHash().putAll("notify:template", temp);
      }
   }

   public String getTemplateContent(String templateCode, String channelType) {
      if (!StringUtils.hasText(templateCode)) {
         return null;
      } else {
         String channel = StringUtils.hasText(channelType) ? channelType : "common";
         Object template = this.redisTemplate.opsForHash().get("notify:template", this.buildKey(templateCode, channel));
         if (template == null) {
            template = this.redisTemplate.opsForHash().get("notify:template", this.buildKey(templateCode, "common"));
         }

         return template != null ? template.toString() : null;
      }
   }

   private String buildKey(String templateCode, String channelType) {
      return templateCode + "::" + (StringUtils.hasText(channelType) ? channelType : "common");
   }
}
