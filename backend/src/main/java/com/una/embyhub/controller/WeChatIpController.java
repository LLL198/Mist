package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.config.common.cookiecloud.WeChatBrowserService;
import com.una.embyhub.config.common.cookiecloud.WeChatIpProperties;
import com.una.embyhub.config.common.cookiecloud.WeChatIpService;
import com.una.embyhub.model.dto.request.wechatipconfig.WeChatIpConfigSave;
import com.una.embyhub.model.dto.request.wechatipconfig.WeChatIpConfigUpdate;
import com.una.embyhub.model.dto.response.wechatipconfig.WeChatIpConfigResponse;
import com.una.embyhub.model.entity.WeChatIpConfig;
import com.una.embyhub.service.WeChatIpConfigService;
import jakarta.annotation.PreDestroy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/wechat-ip"})
public class WeChatIpController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WeChatIpController.class);
   private static final long SESSION_IDLE_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(20L);
   @Autowired
   private WeChatIpConfigService weChatIpConfigService;
   private final Map<String, WeChatIpController.SessionData> sessions = new ConcurrentHashMap<>();
   private final Map<Long, WeChatIpController.SessionData> configSessions = new ConcurrentHashMap<>();
   private WeChatIpProperties properties;
   private Long currentConfigId;

   public WeChatIpProperties getProperties() {
      return this.properties;
   }

   public Long getCurrentConfigId() {
      return this.currentConfigId;
   }

   public WeChatBrowserService getLoggedInBrowser(Long configId) {
      this.cleanupExpiredSessions();
      WeChatIpController.SessionData session = this.configSessions.get(configId);
      if (session != null && session.loggedIn) {
         session.touch();
         return session.browser;
      } else {
         return null;
      }
   }

   public boolean isLoggedIn(Long configId) {
      this.cleanupExpiredSessions();
      WeChatIpController.SessionData session = this.configSessions.get(configId);
      if (session != null) {
         session.touch();
      }

      return session != null && session.loggedIn;
   }

   @PostMapping({"/config/select"})
   @SaCheckPermission({"admin"})
   public List<WeChatIpConfigResponse> selectConfig() {
      return this.weChatIpConfigService.select();
   }

   @PostMapping({"/config/add"})
   @SaCheckPermission({"admin"})
   public Map<String, Object> addConfig(@RequestBody WeChatIpConfigSave save) {
      this.weChatIpConfigService.add(save);
      Map<String, Object> result = new HashMap<>();
      result.put("success", true);
      result.put("message", "新增成功");
      return result;
   }

   @PostMapping({"/config/update"})
   @SaCheckPermission({"admin"})
   public Map<String, Object> updateConfig(@RequestBody WeChatIpConfigUpdate update) {
      this.weChatIpConfigService.update(update);
      Map<String, Object> result = new HashMap<>();
      result.put("success", true);
      result.put("message", "修改成功");
      return result;
   }

   @PostMapping({"/config/delete"})
   @SaCheckPermission({"admin"})
   public Map<String, Object> deleteConfig(@RequestParam Long id) {
      this.weChatIpConfigService.delete(id);
      Map<String, Object> result = new HashMap<>();
      result.put("success", true);
      result.put("message", "删除成功");
      return result;
   }

   @PostMapping({"/init"})
   public Map<String, Object> init() {
      Map<String, Object> result = new HashMap<>();
      this.cleanupExpiredSessions();
      WeChatIpConfig config = this.weChatIpConfigService.getFirstEnabled();
      if (config == null) {
         result.put("success", false);
         result.put("message", "未找到启用的企业微信IP配置，请先添加配置");
         return result;
      } else {
         Long previousConfigId = this.currentConfigId;
         this.currentConfigId = config.getId();
         if (previousConfigId != null && !previousConfigId.equals(this.currentConfigId)) {
            WeChatIpController.SessionData oldSession = this.configSessions.remove(previousConfigId);
            this.closeSessionData(oldSession, "切换配置，回收旧浏览器会话");
         }

         this.properties = WeChatIpProperties.builder().appIds(config.getAppIds()).build();
         log.info("企业微信IP服务配置已初始化，企业ID: {}, 应用ID: {}", config.getCorpId(), config.getAppIds());
         result.put("success", true);
         result.put("message", "配置成功");
         result.put("corpId", config.getCorpId());
         result.put("corpName", config.getCorpName());
         result.put("appIds", config.getAppIds());
         return result;
      }
   }

   @GetMapping(
      value = {"/qrcode"},
      produces = {"image/png"}
   )
   public byte[] getQrCode(@RequestParam(required = false) String sessionId) {
      if (this.properties == null) {
         throw new RuntimeException("请先调用 /init 接口初始化配置");
      } else {
         try {
            this.cleanupExpiredSessions();
            String sid = StringUtils.hasText(sessionId) ? sessionId.trim() : this.generateSessionId();
            WeChatIpController.SessionData existing = this.sessions.get(sid);
            if (existing != null) {
               if (existing.loggedIn) {
                  existing.touch();
                  throw new RuntimeException("当前会话已登录，无需扫码");
               }

               this.removeSession(sid, true, "重新生成二维码，覆盖旧会话");
            }

            WeChatBrowserService browser = new WeChatBrowserService(this.properties);
            browser.init();
            browser.openLoginPage();
            byte[] qrCodeBytes = browser.findQrCode();
            if (qrCodeBytes == null) {
               if (browser.checkLoginStatus()) {
                  this.registerSession(sid, new WeChatIpController.SessionData(sid, browser, true));
                  this.bindConfigSessionIfNeeded(this.sessions.get(sid));
                  throw new RuntimeException("已登录，无需扫码");
               } else {
                  browser.close();
                  throw new RuntimeException("无法获取二维码");
               }
            } else {
               this.registerSession(sid, new WeChatIpController.SessionData(sid, browser, false));
               log.info("生成二维码成功，sessionId: {}", sid);
               return qrCodeBytes;
            }
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            log.error("获取二维码失败: {}", var7.getMessage(), var7);
            throw new RuntimeException("获取二维码失败: " + var7.getMessage());
         }
      }
   }

   @GetMapping({"/session/new"})
   public Map<String, Object> createSession() {
      Map<String, Object> result = new HashMap<>();
      this.cleanupExpiredSessions();
      if (this.properties == null) {
         result.put("success", false);
         result.put("message", "请先调用 /init 接口初始化配置");
         return result;
      } else {
         String sessionId = this.generateSessionId();
         result.put("success", true);
         result.put("sessionId", sessionId);
         result.put("qrcodeUrl", "/wechat-ip/qrcode?sessionId=" + sessionId);
         result.put("message", "请使用 qrcodeUrl 获取二维码图片");
         return result;
      }
   }

   @GetMapping({"/check-login/{sessionId}"})
   public Map<String, Object> checkLogin(@PathVariable String sessionId) {
      Map<String, Object> result = new HashMap<>();
      this.cleanupExpiredSessions();
      String sid = sessionId == null ? null : sessionId.trim();
      result.put("sessionId", sid);
      WeChatIpController.SessionData session = this.getSession(sid);
      if (session == null) {
         result.put("success", false);
         result.put("message", "会话不存在或已过期");
         return result;
      } else if (session.loggedIn) {
         result.put("success", true);
         result.put("status", "logged_in");
         result.put("message", "已登录");
         return result;
      } else {
         try {
            if (session.browser.needsVerification()) {
               result.put("success", true);
               result.put("status", "need_verify");
               result.put("message", "需要短信验证码，请调用 /verify-code 接口");
               return result;
            } else {
               boolean loggedIn = session.browser.checkLoginStatus();
               result.put("success", true);
               if (loggedIn) {
                  session.loggedIn = true;
                  this.bindConfigSessionIfNeeded(session);
                  result.put("status", "logged_in");
                  result.put("message", "登录成功");
                  log.info("用户扫码登录成功，sessionId: {}", sid);
               } else {
                  result.put("status", "waiting_scan");
                  result.put("message", "等待扫码");
               }

               return result;
            }
         } catch (Exception var6) {
            result.put("success", false);
            result.put("status", "error");
            result.put("message", var6.getMessage());
            return result;
         }
      }
   }

   @PostMapping({"/verify-code/{sessionId}"})
   public Map<String, Object> verifyCode(@PathVariable String sessionId, @RequestParam String code) {
      Map<String, Object> result = new HashMap<>();
      this.cleanupExpiredSessions();
      String sid = sessionId == null ? null : sessionId.trim();
      result.put("sessionId", sid);
      WeChatIpController.SessionData session = this.getSession(sid);
      if (session == null) {
         result.put("success", false);
         result.put("message", "会话不存在或已过期");
         return result;
      } else {
         try {
            boolean verified = session.browser.inputVerificationCode(code);
            if (verified) {
               session.loggedIn = true;
               this.bindConfigSessionIfNeeded(session);
               result.put("success", true);
               result.put("status", "logged_in");
               result.put("message", "验证码验证成功，已登录");
               log.info("验证码验证成功，sessionId: {}", sid);
            } else {
               result.put("success", false);
               result.put("status", "verify_failed");
               result.put("message", "验证码错误或已过期");
            }

            return result;
         } catch (Exception var7) {
            log.error("验证码验证失败: {}", var7.getMessage(), var7);
            result.put("success", false);
            result.put("message", var7.getMessage());
            return result;
         }
      }
   }

   @PostMapping({"/change-ip/{sessionId}"})
   public Map<String, Object> changeIp(@PathVariable String sessionId, @RequestParam(required = false) String ip) {
      Map<String, Object> result = new HashMap<>();
      this.cleanupExpiredSessions();
      String sid = sessionId == null ? null : sessionId.trim();
      WeChatIpController.SessionData session = this.getSession(sid);
      if (session == null) {
         result.put("success", false);
         result.put("message", "会话不存在或已过期");
         return result;
      } else if (!session.loggedIn) {
         result.put("success", false);
         result.put("message", "请先完成登录");
         return result;
      } else {
         try {
            String newIp = ip;
            if (ip == null || ip.isEmpty()) {
               WeChatIpService ipService = new WeChatIpService(this.properties);
               newIp = ipService.getPublicIp();
            }

            if (newIp == null) {
               result.put("success", false);
               result.put("message", "无法获取公网IP");
               return result;
            } else {
               boolean success = session.browser.modifyTrustedIp(newIp);
               result.put("success", success);
               result.put("ip", newIp);
               result.put("message", success ? "可信IP修改成功" : "可信IP修改失败");
               if (success) {
                  log.info("可信IP修改成功: {}", newIp);
                  if (this.currentConfigId != null) {
                     this.weChatIpConfigService.updateLastIp(this.currentConfigId, newIp);
                     log.info("IP已保存到数据库，配置ID: {}, IP: {}", this.currentConfigId, newIp);
                     this.bindConfigSessionIfNeeded(session);
                  }
               }

               return result;
            }
         } catch (Exception var8) {
            log.error("修改可信IP失败: {}", var8.getMessage(), var8);
            result.put("success", false);
            result.put("message", "修改失败: " + var8.getMessage());
            return result;
         }
      }
   }

   @GetMapping({"/current-ip"})
   public Map<String, Object> getCurrentIp() {
      Map<String, Object> result = new HashMap<>();
      if (this.properties == null) {
         this.properties = WeChatIpProperties.builder().build();
      }

      WeChatIpService ipService = new WeChatIpService(this.properties);
      String ip = ipService.getPublicIp();
      if (ip != null) {
         result.put("success", true);
         result.put("ip", ip);
      } else {
         result.put("success", false);
         result.put("message", "获取公网IP失败");
      }

      return result;
   }

   @DeleteMapping({"/session/{sessionId}"})
   public Map<String, Object> closeSession(@PathVariable String sessionId) {
      Map<String, Object> result = new HashMap<>();
      this.cleanupExpiredSessions();
      String sid = sessionId == null ? null : sessionId.trim();
      this.removeSession(sid, true, "主动关闭会话");
      result.put("success", true);
      result.put("message", "会话已关闭");
      return result;
   }

   @PostMapping({"/trigger/check-ip"})
   public Map<String, Object> triggerCheckIp() {
      Map<String, Object> result = new HashMap<>();
      if (this.properties == null) {
         result.put("success", false);
         result.put("message", "请先调用 /init 接口初始化配置");
         return result;
      } else {
         try {
            WeChatIpService service = new WeChatIpService(this.properties);
            String currentIp = service.getPublicIp();
            result.put("currentIp", currentIp);
            boolean success = service.forceChangeIp();
            result.put("success", success);
            result.put("message", success ? "IP修改成功" : "IP修改失败，可能需要扫码登录");
            return result;
         } catch (Exception var5) {
            log.error("手动触发IP检测失败: {}", var5.getMessage(), var5);
            result.put("success", false);
            result.put("message", var5.getMessage());
            return result;
         }
      }
   }

   @PostMapping({"/trigger/refresh-cookie"})
   public Map<String, Object> triggerRefreshCookie() {
      Map<String, Object> result = new HashMap<>();
      if (this.properties == null) {
         result.put("success", false);
         result.put("message", "请先调用 /init 接口初始化配置");
         return result;
      } else {
         try {
            WeChatIpService service = new WeChatIpService(this.properties);
            service.refreshCookie();
            result.put("success", true);
            result.put("message", "Cookie刷新完成");
            return result;
         } catch (Exception var3) {
            log.error("Cookie刷新失败: {}", var3.getMessage(), var3);
            result.put("success", false);
            result.put("message", var3.getMessage());
            return result;
         }
      }
   }

   private String generateSessionId() {
      return UUID.randomUUID().toString().replace("-", "");
   }

   @PreDestroy
   public void shutdown() {
      Set<WeChatIpController.SessionData> allSessions = new HashSet<>();
      allSessions.addAll(this.sessions.values());
      allSessions.addAll(this.configSessions.values());
      this.sessions.clear();
      this.configSessions.clear();
      allSessions.forEach(session -> this.closeSessionData(session, "应用关闭，释放浏览器会话"));
   }

   private WeChatIpController.SessionData getSession(String sessionId) {
      if (!StringUtils.hasText(sessionId)) {
         return null;
      } else {
         WeChatIpController.SessionData session = this.sessions.get(sessionId);
         if (session != null) {
            session.touch();
         }

         return session;
      }
   }

   private void registerSession(String sessionId, WeChatIpController.SessionData session) {
      if (StringUtils.hasText(sessionId) && session != null) {
         WeChatIpController.SessionData oldSession = this.sessions.put(sessionId, session);
         if (oldSession != null && oldSession != session) {
            this.unregisterConfigSession(oldSession);
            this.closeSessionData(oldSession, "会话被新会话覆盖");
         }
      }
   }

   private void bindConfigSessionIfNeeded(WeChatIpController.SessionData session) {
      if (session != null && this.currentConfigId != null) {
         WeChatIpController.SessionData oldSession = this.configSessions.put(this.currentConfigId, session);
         if (oldSession != null && oldSession != session) {
            this.sessions.remove(oldSession.sessionId, oldSession);
            this.closeSessionData(oldSession, "配置会话替换，回收旧浏览器会话");
         }

         log.info("已登录会话已保存，配置ID: {}", this.currentConfigId);
      }
   }

   private void removeSession(String sessionId, boolean closeBrowser, String reason) {
      if (StringUtils.hasText(sessionId)) {
         WeChatIpController.SessionData removed = this.sessions.remove(sessionId);
         if (removed != null) {
            this.unregisterConfigSession(removed);
            if (closeBrowser) {
               this.closeSessionData(removed, reason);
            }
         }
      }
   }

   private void unregisterConfigSession(WeChatIpController.SessionData session) {
      if (session != null) {
         this.configSessions.forEach((configId, configSession) -> {
            if (configSession == session) {
               this.configSessions.remove(configId, configSession);
            }
         });
      }
   }

   private boolean isBoundConfigSession(WeChatIpController.SessionData session) {
      if (session == null) {
         return false;
      } else {
         for (WeChatIpController.SessionData value : this.configSessions.values()) {
            if (value == session) {
               return true;
            }
         }

         return false;
      }
   }

   private void cleanupExpiredSessions() {
      long now = System.currentTimeMillis();
      this.sessions
         .forEach(
            (sessionId, session) -> {
               if (session != null
                  && session.isExpired(now, SESSION_IDLE_TIMEOUT_MILLIS)
                  && !this.isBoundConfigSession(session)
                  && this.sessions.remove(sessionId, session)) {
                  this.closeSessionData(session, "会话空闲超时，自动关闭");
               }
            }
         );
   }

   private void closeSessionData(WeChatIpController.SessionData session, String reason) {
      if (session != null && session.browser != null) {
         try {
            session.browser.close();
            log.info("会话资源已释放，sessionId: {}, reason: {}", session.sessionId, reason);
         } catch (Exception var4) {
            log.warn("关闭会话浏览器失败，sessionId: {}, reason: {}, err: {}", session.sessionId, reason, var4.getMessage());
         }
      }
   }

   private static class SessionData {
      final String sessionId;
      final WeChatBrowserService browser;
      volatile boolean loggedIn;
      volatile long lastAccessAt;

      SessionData(String sessionId, WeChatBrowserService browser, boolean loggedIn) {
         this.sessionId = sessionId;
         this.browser = browser;
         this.loggedIn = loggedIn;
         this.lastAccessAt = System.currentTimeMillis();
      }

      void touch() {
         this.lastAccessAt = System.currentTimeMillis();
      }

      boolean isExpired(long now, long timeoutMillis) {
         return now - this.lastAccessAt > timeoutMillis;
      }
   }
}
