package com.una.embyhub.config.common.cookiecloud;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Cookie.Builder;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeChatBrowserService implements AutoCloseable {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WeChatBrowserService.class);
   private static final String WEWORK_BASE_URL = "https://work.weixin.qq.com";
   private static final String APP_MANAGEMENT_BASE = "https://work.weixin.qq.com/wework_admin/frame#apps/modApiApp/";
   private final WeChatIpProperties properties;
   private WebDriver driver;
   private boolean initialized = false;

   public WeChatBrowserService(WeChatIpProperties properties) {
      this.properties = properties;
   }

   public void init() {
      if (!this.initialized) {
         log.info("初始化 Chrome WebDriver...");
         ChromeOptions options = new ChromeOptions();
         options.addArguments(new String[]{"--lang=zh-CN"});
         options.addArguments(new String[]{"--disable-gpu"});
         options.addArguments(new String[]{"--no-sandbox"});
         options.addArguments(new String[]{"--disable-dev-shm-usage"});
         options.addArguments(new String[]{"--disable-extensions"});
         options.addArguments(new String[]{"--window-size=1920,1080"});
         if (this.properties.isHeadless()) {
            options.addArguments(new String[]{"--headless=new"});
         }

         String seleniumRemoteUrl = System.getenv("SELENIUM_REMOTE_URL");
         if (seleniumRemoteUrl != null && !seleniumRemoteUrl.isEmpty()) {
            try {
               log.info("使用远程 Selenium Grid: {}", seleniumRemoteUrl);
               this.driver = new RemoteWebDriver(new URI(seleniumRemoteUrl).toURL(), options);
            } catch (Exception var4) {
               log.error("连接远程 Selenium Grid 失败: {}", var4.getMessage());
               throw new RuntimeException("无法连接 Selenium Grid: " + seleniumRemoteUrl, var4);
            }
         } else {
            log.info("使用本地 ChromeDriver");
            this.driver = new ChromeDriver(options);
         }

         this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
         this.initialized = true;
         log.info("Chrome WebDriver 初始化完成");
      }
   }

   public void openLoginPage() {
      this.ensureInitialized();
      log.info("打开企业微信登录页: {}", this.properties.getWechatUrl());
      this.driver.get(this.properties.getWechatUrl());
      this.sleep(3000L);
   }

   public byte[] findQrCode() {
      this.ensureInitialized();

      try {
         WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(5L));
         WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
         this.driver.switchTo().frame(iframe);
         WebElement qrCodeImg = this.driver.findElement(By.cssSelector("img.qrcode_login_img"));
         if (qrCodeImg != null) {
            String qrCodeUrl = qrCodeImg.getAttribute("src");
            if (qrCodeUrl != null && qrCodeUrl.startsWith("/")) {
               qrCodeUrl = "https://work.weixin.qq.com" + qrCodeUrl;
            }

            byte[] qrCodeBytes = this.downloadImage(qrCodeUrl);
            this.driver.switchTo().defaultContent();
            return qrCodeBytes;
         } else {
            this.driver.switchTo().defaultContent();
            log.warn("未找到二维码图片");
            return null;
         }
      } catch (Exception var6) {
         this.driver.switchTo().defaultContent();
         log.debug("查找二维码失败: {}", var6.getMessage());
         return null;
      }
   }

   public boolean checkLoginStatus() {
      this.ensureInitialized();

      try {
         this.driver.navigate().refresh();
         this.sleep(2000L);
         String currentUrl = this.driver.getCurrentUrl();
         log.debug("当前URL: {}", currentUrl);
         if (!currentUrl.contains("wework_admin/frame") && !currentUrl.contains("wework_admin/index") && !currentUrl.contains("#apps")) {
            try {
               WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(3L));
               WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("check_corp_info")));
               if (element != null) {
                  log.info("登录成功！检测到企业信息元素");
                  return true;
               }
            } catch (TimeoutException var4) {
            }

            if (!currentUrl.contains("loginpage_wx") && !currentUrl.contains("login")) {
               return false;
            } else {
               log.debug("仍在登录页，等待扫码");
               return false;
            }
         } else {
            log.info("登录成功！URL已跳转到管理后台");
            return true;
         }
      } catch (Exception var5) {
         log.error("检查登录状态异常: {}", var5.getMessage());
         return false;
      }
   }

   public boolean needsVerification() {
      this.ensureInitialized();

      try {
         WebElement captchaPanel = this.driver.findElement(By.cssSelector(".receive_captcha_panel"));
         if (captchaPanel != null && captchaPanel.isDisplayed()) {
            log.info("需要短信验证");
            return true;
         }
      } catch (NoSuchElementException var2) {
      }

      return false;
   }

   public boolean inputVerificationCode(String verificationCode) {
      this.ensureInitialized();
      if (verificationCode != null && verificationCode.length() == 6) {
         try {
            log.info("开始输入验证码: {}", verificationCode);
            new WebDriverWait(this.driver, Duration.ofSeconds(10L));
            JavascriptExecutor js = (JavascriptExecutor)this.driver;
            String[] inputSelectors = new String[]{
               ".receive_captcha_panel input",
               ".captcha_input input",
               "input.captcha_input",
               "input[type='text']",
               "input[type='tel']",
               "input[placeholder*='验证码']",
               ".js_captcha_input",
               ".input_captcha"
            };
            WebElement inputElement = null;

            for (String selector : inputSelectors) {
               try {
                  List<WebElement> inputs = this.driver.findElements(By.cssSelector(selector));
                  if (!inputs.isEmpty()) {
                     inputElement = inputs.get(0);
                     if (inputElement.isDisplayed()) {
                        log.info("找到验证码输入框: {}", selector);
                        break;
                     }
                  }
               } catch (Exception var17) {
               }
            }

            if (inputElement != null) {
               js.executeScript("arguments[0].scrollIntoView({block: 'center'});", inputElement);
               this.sleep(500L);
               inputElement.clear();
               inputElement.sendKeys(verificationCode);
               log.info("已输入验证码到输入框");
            } else {
               log.warn("未找到验证码输入框，尝试通过键盘输入...");
               WebElement activeElement = this.driver.switchTo().activeElement();
               if (activeElement != null) {
                  activeElement.sendKeys(verificationCode);
                  log.info("已通过活动元素输入验证码");
               } else {
                  for (char digit : verificationCode.toCharArray()) {
                     this.driver.findElement(By.tagName("body")).sendKeys(String.valueOf(digit));
                     this.sleep(200L);
                  }

                  log.info("已通过body逐字符输入验证码");
               }
            }

            this.sleep(1000L);
            String[] confirmSelectors = new String[]{
               ".confirm_btn",
               ".js_submit_captcha",
               ".captcha_confirm_btn",
               "button.confirm",
               "a.confirm_btn",
               ".submit_btn",
               "button[type='submit']",
               ".receive_captcha_panel .btn_primary",
               ".receive_captcha_panel button"
            };
            WebElement confirmBtn = null;

            for (String selector : confirmSelectors) {
               try {
                  for (WebElement btn : this.driver.findElements(By.cssSelector(selector))) {
                     if (btn.isDisplayed() && btn.isEnabled()) {
                        confirmBtn = btn;
                        log.info("找到确认按钮: {}", selector);
                        break;
                     }
                  }

                  if (confirmBtn != null) {
                     break;
                  }
               } catch (Exception var16) {
               }
            }

            if (confirmBtn != null) {
               js.executeScript("arguments[0].scrollIntoView({block: 'center'});", confirmBtn);
               this.sleep(500L);
               js.executeScript("arguments[0].click();", confirmBtn);
               log.info("已点击确认按钮");
            } else {
               log.warn("未找到确认按钮，尝试按回车键提交");
               if (inputElement != null) {
                  inputElement.sendKeys(Keys.ENTER);
               } else {
                  this.driver.findElement(By.tagName("body")).sendKeys(Keys.ENTER);
               }
            }

            this.sleep(3000L);

            try {
               for (WebElement errorEl : this.driver.findElements(By.cssSelector(".error_tips, .captcha_error, .tips_error"))) {
                  if (errorEl.isDisplayed()) {
                     String errorText = errorEl.getText();
                     log.error("验证码验证失败，页面错误提示: {}", errorText);
                     return false;
                  }
               }
            } catch (Exception var15) {
            }

            boolean result = this.checkLoginStatus();
            log.info("验证码验证结果: {}", result ? "成功" : "失败");
            return result;
         } catch (Exception var18) {
            log.error("输入验证码失败: {}", var18.getMessage(), var18);
            return false;
         }
      } else {
         log.error("验证码格式错误，需要6位数字，实际: {}", verificationCode);
         return false;
      }
   }

   public void injectCookies(List<Map<String, Object>> cookies) {
      this.ensureInitialized();
      if (cookies != null && !cookies.isEmpty()) {
         for (Map<String, Object> cookieData : cookies) {
            try {
               Builder builder = new Builder((String)cookieData.get("name"), (String)cookieData.get("value"));
               if (cookieData.containsKey("domain")) {
                  builder.domain((String)cookieData.get("domain"));
               }

               if (cookieData.containsKey("path")) {
                  builder.path((String)cookieData.get("path"));
               }

               this.driver.manage().addCookie(builder.build());
            } catch (Exception var5) {
               log.warn("注入Cookie失败: {}", var5.getMessage());
            }
         }

         log.info("注入了 {} 个Cookie", cookies.size());
      }
   }

   public List<Map<String, Object>> extractCookies() {
      this.ensureInitialized();
      Set<Cookie> cookies = this.driver.manage().getCookies();
      return cookies.stream().map(cookie -> {
         Map<String, Object> map = new HashMap<>();
         map.put("name", cookie.getName());
         map.put("value", cookie.getValue());
         map.put("domain", cookie.getDomain());
         map.put("path", cookie.getPath());
         return map;
      }).collect(Collectors.toList());
   }

   public boolean modifyTrustedIp(String newIp) {
      this.ensureInitialized();
      List<String> appIds = this.properties.getAppIdList();
      if (appIds.isEmpty()) {
         log.error("未配置应用ID，无法修改IP");
         return false;
      } else {
         boolean allSuccess = true;

         for (String appId : appIds) {
            boolean success = this.modifyAppTrustedIp(appId, newIp);
            if (!success) {
               allSuccess = false;
            }
         }

         return allSuccess;
      }
   }

   private boolean modifyAppTrustedIp(String appId, String newIp) {
      try {
         String appUrl = "https://work.weixin.qq.com/wework_admin/frame#apps/modApiApp/" + appId.trim();
         log.info("打开应用页面: {}", appUrl);
         this.driver.get(appUrl);
         this.sleep(3000L);
         WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10L));
         JavascriptExecutor js = (JavascriptExecutor)this.driver;
         WebElement configBtn = null;
         String[] selectors = new String[]{
            "//div[contains(@class, 'js_show_ipConfig_dialog')]//a[text()='配置']",
            "//a[contains(@class, 'js_show_ipConfig_dialog')]",
            "//span[text()='可信IP']/following::a[text()='配置'][1]",
            "//a[text()='配置']"
         };

         for (String xpath : selectors) {
            try {
               configBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
               if (configBtn != null && configBtn.isDisplayed()) {
                  log.info("找到配置按钮: {}", xpath);
                  break;
               }
            } catch (Exception var13) {
            }
         }

         if (configBtn == null) {
            log.error("未找到配置按钮");
            return false;
         } else {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", configBtn);
            this.sleep(500L);
            js.executeScript("arguments[0].click();", configBtn);
            log.info("已点击配置按钮");
            this.sleep(1000L);
            WebElement textarea = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("textarea.js_ipConfig_textarea")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", textarea);
            this.sleep(500L);
            textarea.clear();
            textarea.sendKeys(newIp);
            log.info("已输入公网IP: {}", newIp);
            WebElement confirmBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".js_ipConfig_confirmBtn")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", confirmBtn);
            this.sleep(500L);
            js.executeScript("arguments[0].click();", confirmBtn);
            this.sleep(3000L);
            log.info("应用 {} 可信IP修改成功", appId);
            return true;
         }
      } catch (Exception var14) {
         log.error("修改应用 {} 可信IP失败: {}", appId, var14.getMessage());
         return false;
      }
   }

   public void refreshPage() {
      this.ensureInitialized();
      this.driver.navigate().refresh();
      this.sleep(2000L);
   }

   @Override
   public void close() {
      if (this.driver != null) {
         try {
            this.driver.quit();
            log.info("WebDriver 已关闭");
         } catch (Exception var2) {
            log.warn("关闭 WebDriver 失败: {}", var2.getMessage());
         }

         this.driver = null;
         this.initialized = false;
      }
   }

   private void ensureInitialized() {
      if (!this.initialized) {
         this.init();
      }
   }

   private byte[] downloadImage(String imageUrl) {
      try {
         URL url = new URL(imageUrl);

         byte[] var7;
         try (
            InputStream is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ) {
            byte[] buffer = new byte[4096];

            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
               baos.write(buffer, 0, bytesRead);
            }

            var7 = baos.toByteArray();
         }

         return var7;
      } catch (Exception var12) {
         log.error("下载图片失败: {}", var12.getMessage());
         return null;
      }
   }

   private void sleep(long millis) {
      try {
         Thread.sleep(millis);
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
      }
   }
}
