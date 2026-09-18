package com.una.embyhub.config.common.cookiecloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class WeChatIpService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WeChatIpService.class);
   private static final Pattern IP_PATTERN = Pattern.compile("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b");
   private static final String WEWORK_COOKIE_DOMAIN = ".work.weixin.qq.com";
   private final WeChatIpProperties properties;
   private final CookieCloudService cookieCloudService;
   private final RestTemplate restTemplate;
   private String currentIpAddress = "0.0.0.0";
   private boolean ipChanged = true;
   private String lastVerificationCode;

   public WeChatIpService(WeChatIpProperties properties) {
      this.properties = properties;
      this.restTemplate = new RestTemplate();
      if (properties.isUseCookieCloud() && properties.getCookieCloud() != null) {
         this.cookieCloudService = new CookieCloudService(properties.getCookieCloud());
      } else {
         this.cookieCloudService = null;
      }
   }

   public boolean checkAndChangeIp() {
      log.info("开始检测公网IP...");
      if (this.checkIp()) {
         return this.changeIp();
      } else {
         log.info("IP未变化，无需修改");
         return true;
      }
   }

   public boolean checkIp() {
      String newIp = this.getPublicIp();
      if (newIp == null) {
         log.error("获取公网IP失败");
         return false;
      } else {
         log.info("当前公网IP: {}", newIp);
         if (!this.ipChanged) {
            log.info("上次IP修改未成功，继续尝试");
            this.currentIpAddress = newIp;
            return true;
         } else if (!newIp.equals(this.currentIpAddress)) {
            log.info("检测到IP变化: {} -> {}", this.currentIpAddress, newIp);
            this.currentIpAddress = newIp;
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean forceChangeIp() {
      String newIp = this.getPublicIp();
      if (newIp != null) {
         this.currentIpAddress = newIp;
      }

      return this.changeIp();
   }

   public boolean changeIp() {
      log.info("开始修改企业微信可信IP...");
      this.ipChanged = false;

      try {
         boolean var4;
         try (WeChatBrowserService browser = new WeChatBrowserService(this.properties)) {
            browser.init();
            List<Map<String, Object>> cookies = this.getCookies();
            if (cookies != null && !cookies.isEmpty()) {
               browser.injectCookies(cookies);
            }

            browser.openLoginPage();
            byte[] qrCode = browser.findQrCode();
            if (qrCode != null) {
               log.error("Cookie已失效，需要手动通过 /wechat-ip/qrcode 获取二维码扫码登录");
               return false;
            }

            if (browser.checkLoginStatus()) {
               log.info("Cookie登录成功");
               this.ipChanged = browser.modifyTrustedIp(this.currentIpAddress);
               return this.ipChanged;
            }

            log.error("登录失败");
            var4 = false;
         }

         return var4;
      } catch (Exception var7) {
         log.error("修改可信IP失败: {}", var7.getMessage(), var7);
         return false;
      }
   }

   public void refreshCookie() {
      log.info("开始刷新Cookie...");

      try (WeChatBrowserService browser = new WeChatBrowserService(this.properties)) {
         browser.init();
         List<Map<String, Object>> cookies = this.getCookies();
         if (cookies != null && !cookies.isEmpty()) {
            browser.injectCookies(cookies);
         }

         browser.openLoginPage();
         if (browser.checkLoginStatus()) {
            log.info("Cookie刷新成功，仍然有效");
         } else {
            log.warn("Cookie已失效，下次IP变动时需要手动扫码登录");
         }
      } catch (Exception var6) {
         log.error("刷新Cookie失败: {}", var6.getMessage(), var6);
      }
   }

   public void setVerificationCode(String code) {
      if (code != null && code.length() >= 6) {
         this.lastVerificationCode = code.substring(0, 6);
         log.info("收到短信验证码: {}", this.lastVerificationCode);
      }
   }

   public String getPublicIp() {
      for (String url : this.properties.getIpUrls()) {
         try {
            String response = this.restTemplate.getForObject(url, String.class);
            if (response != null) {
               Matcher matcher = IP_PATTERN.matcher(response);
               if (matcher.find()) {
                  String ip = matcher.group();
                  log.debug("从 {} 获取IP成功: {}", url, ip);
                  return ip;
               }
            }
         } catch (Exception var6) {
            log.debug("从 {} 获取IP失败: {}", url, var6.getMessage());
         }
      }

      return null;
   }

   private List<Map<String, Object>> getCookies() {
      if (this.properties.isUseCookieCloud() && this.cookieCloudService != null) {
         try {
            Map<String, Object> cookieData = this.cookieCloudService.getCookie();
            if (cookieData != null && cookieData.containsKey("cookie_data")) {
               Map<String, Object> data = (Map<String, Object>)cookieData.get("cookie_data");
               if (data.containsKey(".work.weixin.qq.com")) {
                  List<Map<String, Object>> cookies = (List<Map<String, Object>>)data.get(".work.weixin.qq.com");
                  log.info("从CookieCloud获取到 {} 个Cookie", cookies.size());
                  return cookies;
               }
            }
         } catch (Exception var4) {
            log.warn("从CookieCloud获取Cookie失败: {}", var4.getMessage());
         }
      }

      String cookieHeader = this.properties.getCookieHeader();
      return cookieHeader != null && !cookieHeader.isEmpty() ? this.parseCookieHeader(cookieHeader) : null;
   }

   private void saveCookies(List<Map<String, Object>> cookies) {
      if (this.properties.isUseCookieCloud() && this.cookieCloudService != null) {
         try {
            Map<String, List<Map<String, Object>>> grouped = new HashMap<>();

            for (Map<String, Object> cookie : cookies) {
               String domain = (String)cookie.get("domain");
               grouped.computeIfAbsent(domain, k -> new ArrayList<>()).add(cookie);
            }

            Map<String, Object> cookieData = new HashMap<>();
            cookieData.put("cookie_data", grouped);
            if (this.cookieCloudService.updateCookie(cookieData)) {
               log.info("Cookie已保存到CookieCloud");
            }
         } catch (Exception var6) {
            log.error("保存Cookie到CookieCloud失败: {}", var6.getMessage());
         }
      }
   }

   private List<Map<String, Object>> parseCookieHeader(String cookieHeader) {
      List<Map<String, Object>> cookies = new ArrayList<>();

      for (String part : cookieHeader.split(";")) {
         String[] kv = part.trim().split("=", 2);
         if (kv.length == 2) {
            Map<String, Object> cookie = new HashMap<>();
            cookie.put("name", kv[0].trim());
            cookie.put("value", kv[1].trim());
            cookie.put("domain", ".work.weixin.qq.com");
            cookie.put("path", "/");
            cookies.add(cookie);
         }
      }

      return cookies;
   }

   public String getCurrentIpAddress() {
      return this.currentIpAddress;
   }

   public boolean isIpChanged() {
      return this.ipChanged;
   }
}
