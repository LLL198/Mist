package com.una.embyhub.config.common.cookiecloud;

import java.util.Arrays;
import java.util.List;
import lombok.Generated;

public class WeChatIpProperties {
   private String wechatUrl;
   private String appIds;
   private CookieCloudProperties cookieCloud;
   private String checkCron;
   private String refreshCron;
   private List<String> ipUrls;
   private boolean headless;
   private boolean useCookieCloud;
   private String cookieHeader;

   public List<String> getAppIdList() {
      return this.appIds != null && !this.appIds.isEmpty()
         ? Arrays.stream(this.appIds.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList()
         : List.of();
   }

   @Generated
   private static String $default$wechatUrl() {
      return "https://work.weixin.qq.com/wework_admin/loginpage_wx?from=myhome";
   }

   @Generated
   private static String $default$checkCron() {
      return "*/20 * * * *";
   }

   @Generated
   private static String $default$refreshCron() {
      return "*/20 * * * *";
   }

   @Generated
   private static List<String> $default$ipUrls() {
      return Arrays.asList("https://myip.ipip.net", "https://ddns.oray.com/checkip", "https://ip.3322.net", "https://4.ipw.cn");
   }

   @Generated
   private static boolean $default$headless() {
      return true;
   }

   @Generated
   private static boolean $default$useCookieCloud() {
      return true;
   }

   @Generated
   public static WeChatIpProperties.WeChatIpPropertiesBuilder builder() {
      return new WeChatIpProperties.WeChatIpPropertiesBuilder();
   }

   @Generated
   public String getWechatUrl() {
      return this.wechatUrl;
   }

   @Generated
   public String getAppIds() {
      return this.appIds;
   }

   @Generated
   public CookieCloudProperties getCookieCloud() {
      return this.cookieCloud;
   }

   @Generated
   public String getCheckCron() {
      return this.checkCron;
   }

   @Generated
   public String getRefreshCron() {
      return this.refreshCron;
   }

   @Generated
   public List<String> getIpUrls() {
      return this.ipUrls;
   }

   @Generated
   public boolean isHeadless() {
      return this.headless;
   }

   @Generated
   public boolean isUseCookieCloud() {
      return this.useCookieCloud;
   }

   @Generated
   public String getCookieHeader() {
      return this.cookieHeader;
   }

   @Generated
   public void setWechatUrl(final String wechatUrl) {
      this.wechatUrl = wechatUrl;
   }

   @Generated
   public void setAppIds(final String appIds) {
      this.appIds = appIds;
   }

   @Generated
   public void setCookieCloud(final CookieCloudProperties cookieCloud) {
      this.cookieCloud = cookieCloud;
   }

   @Generated
   public void setCheckCron(final String checkCron) {
      this.checkCron = checkCron;
   }

   @Generated
   public void setRefreshCron(final String refreshCron) {
      this.refreshCron = refreshCron;
   }

   @Generated
   public void setIpUrls(final List<String> ipUrls) {
      this.ipUrls = ipUrls;
   }

   @Generated
   public void setHeadless(final boolean headless) {
      this.headless = headless;
   }

   @Generated
   public void setUseCookieCloud(final boolean useCookieCloud) {
      this.useCookieCloud = useCookieCloud;
   }

   @Generated
   public void setCookieHeader(final String cookieHeader) {
      this.cookieHeader = cookieHeader;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WeChatIpProperties other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isHeadless() != other.isHeadless()) {
         return false;
      } else if (this.isUseCookieCloud() != other.isUseCookieCloud()) {
         return false;
      } else {
         Object this$wechatUrl = this.getWechatUrl();
         Object other$wechatUrl = other.getWechatUrl();
         if (this$wechatUrl == null ? other$wechatUrl == null : this$wechatUrl.equals(other$wechatUrl)) {
            Object this$appIds = this.getAppIds();
            Object other$appIds = other.getAppIds();
            if (this$appIds == null ? other$appIds == null : this$appIds.equals(other$appIds)) {
               Object this$cookieCloud = this.getCookieCloud();
               Object other$cookieCloud = other.getCookieCloud();
               if (this$cookieCloud == null ? other$cookieCloud == null : this$cookieCloud.equals(other$cookieCloud)) {
                  Object this$checkCron = this.getCheckCron();
                  Object other$checkCron = other.getCheckCron();
                  if (this$checkCron == null ? other$checkCron == null : this$checkCron.equals(other$checkCron)) {
                     Object this$refreshCron = this.getRefreshCron();
                     Object other$refreshCron = other.getRefreshCron();
                     if (this$refreshCron == null ? other$refreshCron == null : this$refreshCron.equals(other$refreshCron)) {
                        Object this$ipUrls = this.getIpUrls();
                        Object other$ipUrls = other.getIpUrls();
                        if (this$ipUrls == null ? other$ipUrls == null : this$ipUrls.equals(other$ipUrls)) {
                           Object this$cookieHeader = this.getCookieHeader();
                           Object other$cookieHeader = other.getCookieHeader();
                           return this$cookieHeader == null ? other$cookieHeader == null : this$cookieHeader.equals(other$cookieHeader);
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof WeChatIpProperties;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isHeadless() ? 79 : 97);
      result = result * 59 + (this.isUseCookieCloud() ? 79 : 97);
      Object $wechatUrl = this.getWechatUrl();
      result = result * 59 + ($wechatUrl == null ? 43 : $wechatUrl.hashCode());
      Object $appIds = this.getAppIds();
      result = result * 59 + ($appIds == null ? 43 : $appIds.hashCode());
      Object $cookieCloud = this.getCookieCloud();
      result = result * 59 + ($cookieCloud == null ? 43 : $cookieCloud.hashCode());
      Object $checkCron = this.getCheckCron();
      result = result * 59 + ($checkCron == null ? 43 : $checkCron.hashCode());
      Object $refreshCron = this.getRefreshCron();
      result = result * 59 + ($refreshCron == null ? 43 : $refreshCron.hashCode());
      Object $ipUrls = this.getIpUrls();
      result = result * 59 + ($ipUrls == null ? 43 : $ipUrls.hashCode());
      Object $cookieHeader = this.getCookieHeader();
      return result * 59 + ($cookieHeader == null ? 43 : $cookieHeader.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "WeChatIpProperties(wechatUrl="
         + this.getWechatUrl()
         + ", appIds="
         + this.getAppIds()
         + ", cookieCloud="
         + this.getCookieCloud()
         + ", checkCron="
         + this.getCheckCron()
         + ", refreshCron="
         + this.getRefreshCron()
         + ", ipUrls="
         + this.getIpUrls()
         + ", headless="
         + this.isHeadless()
         + ", useCookieCloud="
         + this.isUseCookieCloud()
         + ", cookieHeader="
         + this.getCookieHeader()
         + ")";
   }

   @Generated
   public WeChatIpProperties() {
      this.wechatUrl = $default$wechatUrl();
      this.checkCron = $default$checkCron();
      this.refreshCron = $default$refreshCron();
      this.ipUrls = $default$ipUrls();
      this.headless = $default$headless();
      this.useCookieCloud = $default$useCookieCloud();
   }

   @Generated
   public WeChatIpProperties(
      final String wechatUrl,
      final String appIds,
      final CookieCloudProperties cookieCloud,
      final String checkCron,
      final String refreshCron,
      final List<String> ipUrls,
      final boolean headless,
      final boolean useCookieCloud,
      final String cookieHeader
   ) {
      this.wechatUrl = wechatUrl;
      this.appIds = appIds;
      this.cookieCloud = cookieCloud;
      this.checkCron = checkCron;
      this.refreshCron = refreshCron;
      this.ipUrls = ipUrls;
      this.headless = headless;
      this.useCookieCloud = useCookieCloud;
      this.cookieHeader = cookieHeader;
   }

   @Generated
   public static class WeChatIpPropertiesBuilder {
      @Generated
      private boolean wechatUrl$set;
      @Generated
      private String wechatUrl$value;
      @Generated
      private String appIds;
      @Generated
      private CookieCloudProperties cookieCloud;
      @Generated
      private boolean checkCron$set;
      @Generated
      private String checkCron$value;
      @Generated
      private boolean refreshCron$set;
      @Generated
      private String refreshCron$value;
      @Generated
      private boolean ipUrls$set;
      @Generated
      private List<String> ipUrls$value;
      @Generated
      private boolean headless$set;
      @Generated
      private boolean headless$value;
      @Generated
      private boolean useCookieCloud$set;
      @Generated
      private boolean useCookieCloud$value;
      @Generated
      private String cookieHeader;

      @Generated
      WeChatIpPropertiesBuilder() {
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder wechatUrl(final String wechatUrl) {
         this.wechatUrl$value = wechatUrl;
         this.wechatUrl$set = true;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder appIds(final String appIds) {
         this.appIds = appIds;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder cookieCloud(final CookieCloudProperties cookieCloud) {
         this.cookieCloud = cookieCloud;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder checkCron(final String checkCron) {
         this.checkCron$value = checkCron;
         this.checkCron$set = true;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder refreshCron(final String refreshCron) {
         this.refreshCron$value = refreshCron;
         this.refreshCron$set = true;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder ipUrls(final List<String> ipUrls) {
         this.ipUrls$value = ipUrls;
         this.ipUrls$set = true;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder headless(final boolean headless) {
         this.headless$value = headless;
         this.headless$set = true;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder useCookieCloud(final boolean useCookieCloud) {
         this.useCookieCloud$value = useCookieCloud;
         this.useCookieCloud$set = true;
         return this;
      }

      @Generated
      public WeChatIpProperties.WeChatIpPropertiesBuilder cookieHeader(final String cookieHeader) {
         this.cookieHeader = cookieHeader;
         return this;
      }

      @Generated
      public WeChatIpProperties build() {
         String wechatUrl$value = this.wechatUrl$value;
         if (!this.wechatUrl$set) {
            wechatUrl$value = WeChatIpProperties.$default$wechatUrl();
         }

         String checkCron$value = this.checkCron$value;
         if (!this.checkCron$set) {
            checkCron$value = WeChatIpProperties.$default$checkCron();
         }

         String refreshCron$value = this.refreshCron$value;
         if (!this.refreshCron$set) {
            refreshCron$value = WeChatIpProperties.$default$refreshCron();
         }

         List<String> ipUrls$value = this.ipUrls$value;
         if (!this.ipUrls$set) {
            ipUrls$value = WeChatIpProperties.$default$ipUrls();
         }

         boolean headless$value = this.headless$value;
         if (!this.headless$set) {
            headless$value = WeChatIpProperties.$default$headless();
         }

         boolean useCookieCloud$value = this.useCookieCloud$value;
         if (!this.useCookieCloud$set) {
            useCookieCloud$value = WeChatIpProperties.$default$useCookieCloud();
         }

         return new WeChatIpProperties(
            wechatUrl$value,
            this.appIds,
            this.cookieCloud,
            checkCron$value,
            refreshCron$value,
            ipUrls$value,
            headless$value,
            useCookieCloud$value,
            this.cookieHeader
         );
      }

      @Generated
      @Override
      public String toString() {
         return "WeChatIpProperties.WeChatIpPropertiesBuilder(wechatUrl$value="
            + this.wechatUrl$value
            + ", appIds="
            + this.appIds
            + ", cookieCloud="
            + this.cookieCloud
            + ", checkCron$value="
            + this.checkCron$value
            + ", refreshCron$value="
            + this.refreshCron$value
            + ", ipUrls$value="
            + this.ipUrls$value
            + ", headless$value="
            + this.headless$value
            + ", useCookieCloud$value="
            + this.useCookieCloud$value
            + ", cookieHeader="
            + this.cookieHeader
            + ")";
      }
   }
}
