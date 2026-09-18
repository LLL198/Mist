package com.una.embyhub.config.common.wechatbot;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class WechatBotMenuService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatBotMenuService.class);
   public static final String EVENT_KEY_HELP = "WECHAT_BOT_HELP";
   public static final String EVENT_KEY_STATS = "WECHAT_BOT_STATS";
   public static final String EVENT_KEY_CREATE_USER = "WECHAT_BOT_CREATE_USER";
   public static final String EVENT_KEY_EXTEND_USERS = "WECHAT_BOT_EXTEND_USERS";
   public static final String EVENT_KEY_GENERATE_CARDS = "WECHAT_BOT_GENERATE_CARDS";
   public static final String EVENT_KEY_SEARCH_GUIDE = "WECHAT_BOT_SEARCH_GUIDE";
   public static final String EVENT_KEY_SEARCH_SAMPLE = "WECHAT_BOT_SEARCH_SAMPLE";
   public static final String EVENT_KEY_EMBY_SEARCH = "WECHAT_BOT_EMBY_SEARCH";
   private static final String TOKEN_API = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
   private static final String MENU_API = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=%s&agentid=%s";
   private final NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   private final RestTemplate restTemplate = new RestTemplate();

   @EventListener({ApplicationReadyEvent.class})
   public void autoSyncMenu() {
      this.syncMenuInternal("应用启动自动刷新菜单");
   }

   public boolean syncMenu() {
      return this.syncMenuInternal("手动触发菜单刷新");
   }

   private boolean syncMenuInternal(String scene) {
      WechatBotProperties properties = this.getProperties();
      if (properties == null || !properties.isMenuEnabled()) {
         log.info("企业微信菜单未启用，跳过同步");
         return false;
      } else if (!this.hasMenuConfig(properties)) {
         log.warn("缺少菜单所需的 corpId/agentId/appSecret，无法同步企业微信菜单");
         return false;
      } else {
         String token = this.fetchAccessToken(properties);
         if (!StringUtils.hasText(token)) {
            log.warn("{}失败：获取 access_token 为空", scene);
            return false;
         } else {
            boolean success = this.pushMenu(token, properties);
            if (success) {
               log.info("{}成功", scene);
            }

            return success;
         }
      }
   }

   private boolean hasMenuConfig(WechatBotProperties properties) {
      return properties != null
         && StringUtils.hasText(properties.getCorpId())
         && StringUtils.hasText(properties.getAgentId())
         && StringUtils.hasText(properties.getAppSecret());
   }

   private String fetchAccessToken(WechatBotProperties properties) {
      String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s", properties.getCorpId(), properties.getAppSecret());
      ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
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
   }

   private boolean pushMenu(String accessToken, WechatBotProperties properties) {
      String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=%s&agentid=%s", accessToken, properties.getAgentId());
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Map<String, Object>> request = new HttpEntity<>(this.buildMenuPayload(), headers);
      ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class);
      JSONObject body = JSONObject.parseObject(response.getBody());
      if (body == null) {
         log.warn("创建企业微信菜单返回空响应");
         return false;
      } else {
         Integer errCode = body.getInteger("errcode");
         if (errCode != null && errCode == 0) {
            return true;
         } else {
            log.warn("创建企业微信菜单失败，errcode={}，errmsg={}", errCode, body.getString("errmsg"));
            return false;
         }
      }
   }

   private Map<String, Object> buildMenuPayload() {
      Map<String, Object> payload = new HashMap<>();
      List<Map<String, Object>> buttons = new ArrayList<>();
      Map<String, Object> userMenu = new HashMap<>();
      userMenu.put("name", "用户");
      userMenu.put(
         "sub_button",
         List.of(
            this.clickButton("开始 / 帮助", "WECHAT_BOT_HELP"),
            this.clickButton("创建用户", "WECHAT_BOT_CREATE_USER"),
            this.clickButton("批量延期", "WECHAT_BOT_EXTEND_USERS"),
            this.clickButton("用户统计", "WECHAT_BOT_STATS")
         )
      );
      Map<String, Object> cardMenu = new HashMap<>();
      cardMenu.put("name", "卡密");
      cardMenu.put("sub_button", List.of(this.clickButton("生成卡密", "WECHAT_BOT_GENERATE_CARDS")));
      Map<String, Object> searchMenu = new HashMap<>();
      searchMenu.put("name", "搜索");
      searchMenu.put(
         "sub_button",
         List.of(
            this.clickButton("搜索资源库", "WECHAT_BOT_EMBY_SEARCH"),
            this.clickButton("搜索TMDB", "WECHAT_BOT_SEARCH_GUIDE"),
            this.clickButton("示例搜索", "WECHAT_BOT_SEARCH_SAMPLE")
         )
      );
      buttons.add(userMenu);
      buttons.add(cardMenu);
      buttons.add(searchMenu);
      payload.put("button", buttons);
      return payload;
   }

   private Map<String, Object> clickButton(String name, String key) {
      Map<String, Object> button = new HashMap<>();
      button.put("type", "click");
      button.put("name", name);
      button.put("key", key);
      return button;
   }

   private WechatBotProperties getProperties() {
      String json = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("wechatBot");
      return !StringUtils.hasText(json) ? null : JSONObject.parseObject(json, WechatBotProperties.class);
   }

   @Generated
   public WechatBotMenuService(final NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils) {
      this.notifyChannelCacheLoaderUtils = notifyChannelCacheLoaderUtils;
   }
}
