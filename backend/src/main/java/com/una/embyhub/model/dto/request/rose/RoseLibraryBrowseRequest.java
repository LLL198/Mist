package com.una.embyhub.model.dto.request.rose;

import java.io.Serializable;
import lombok.Generated;

public class RoseLibraryBrowseRequest implements Serializable {
   private String cid;
   private String sourceRoot;
   private String accountId;
   private String sourceAccount;
   private String app;
   private String cookie;
   private String cookieText;
   private String cookies;
   private String embyPassword;

   @Generated
   public String getCid() {
      return this.cid;
   }

   @Generated
   public String getSourceRoot() {
      return this.sourceRoot;
   }

   @Generated
   public String getAccountId() {
      return this.accountId;
   }

   @Generated
   public String getSourceAccount() {
      return this.sourceAccount;
   }

   @Generated
   public String getApp() {
      return this.app;
   }

   @Generated
   public String getCookie() {
      return this.cookie;
   }

   @Generated
   public String getCookieText() {
      return this.cookieText;
   }

   @Generated
   public String getCookies() {
      return this.cookies;
   }

   @Generated
   public String getEmbyPassword() {
      return this.embyPassword;
   }

   @Generated
   public void setCid(final String cid) {
      this.cid = cid;
   }

   @Generated
   public void setSourceRoot(final String sourceRoot) {
      this.sourceRoot = sourceRoot;
   }

   @Generated
   public void setAccountId(final String accountId) {
      this.accountId = accountId;
   }

   @Generated
   public void setSourceAccount(final String sourceAccount) {
      this.sourceAccount = sourceAccount;
   }

   @Generated
   public void setApp(final String app) {
      this.app = app;
   }

   @Generated
   public void setCookie(final String cookie) {
      this.cookie = cookie;
   }

   @Generated
   public void setCookieText(final String cookieText) {
      this.cookieText = cookieText;
   }

   @Generated
   public void setCookies(final String cookies) {
      this.cookies = cookies;
   }

   @Generated
   public void setEmbyPassword(final String embyPassword) {
      this.embyPassword = embyPassword;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseLibraryBrowseRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$cid = this.getCid();
         Object other$cid = other.getCid();
         if (this$cid == null ? other$cid == null : this$cid.equals(other$cid)) {
            Object this$sourceRoot = this.getSourceRoot();
            Object other$sourceRoot = other.getSourceRoot();
            if (this$sourceRoot == null ? other$sourceRoot == null : this$sourceRoot.equals(other$sourceRoot)) {
               Object this$accountId = this.getAccountId();
               Object other$accountId = other.getAccountId();
               if (this$accountId == null ? other$accountId == null : this$accountId.equals(other$accountId)) {
                  Object this$sourceAccount = this.getSourceAccount();
                  Object other$sourceAccount = other.getSourceAccount();
                  if (this$sourceAccount == null ? other$sourceAccount == null : this$sourceAccount.equals(other$sourceAccount)) {
                     Object this$app = this.getApp();
                     Object other$app = other.getApp();
                     if (this$app == null ? other$app == null : this$app.equals(other$app)) {
                        Object this$cookie = this.getCookie();
                        Object other$cookie = other.getCookie();
                        if (this$cookie == null ? other$cookie == null : this$cookie.equals(other$cookie)) {
                           Object this$cookieText = this.getCookieText();
                           Object other$cookieText = other.getCookieText();
                           if (this$cookieText == null ? other$cookieText == null : this$cookieText.equals(other$cookieText)) {
                              Object this$cookies = this.getCookies();
                              Object other$cookies = other.getCookies();
                              if (this$cookies == null ? other$cookies == null : this$cookies.equals(other$cookies)) {
                                 Object this$embyPassword = this.getEmbyPassword();
                                 Object other$embyPassword = other.getEmbyPassword();
                                 return this$embyPassword == null ? other$embyPassword == null : this$embyPassword.equals(other$embyPassword);
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
      return other instanceof RoseLibraryBrowseRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $cid = this.getCid();
      result = result * 59 + ($cid == null ? 43 : $cid.hashCode());
      Object $sourceRoot = this.getSourceRoot();
      result = result * 59 + ($sourceRoot == null ? 43 : $sourceRoot.hashCode());
      Object $accountId = this.getAccountId();
      result = result * 59 + ($accountId == null ? 43 : $accountId.hashCode());
      Object $sourceAccount = this.getSourceAccount();
      result = result * 59 + ($sourceAccount == null ? 43 : $sourceAccount.hashCode());
      Object $app = this.getApp();
      result = result * 59 + ($app == null ? 43 : $app.hashCode());
      Object $cookie = this.getCookie();
      result = result * 59 + ($cookie == null ? 43 : $cookie.hashCode());
      Object $cookieText = this.getCookieText();
      result = result * 59 + ($cookieText == null ? 43 : $cookieText.hashCode());
      Object $cookies = this.getCookies();
      result = result * 59 + ($cookies == null ? 43 : $cookies.hashCode());
      Object $embyPassword = this.getEmbyPassword();
      return result * 59 + ($embyPassword == null ? 43 : $embyPassword.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RoseLibraryBrowseRequest(cid="
         + this.getCid()
         + ", sourceRoot="
         + this.getSourceRoot()
         + ", accountId="
         + this.getAccountId()
         + ", sourceAccount="
         + this.getSourceAccount()
         + ", app="
         + this.getApp()
         + ", cookie="
         + this.getCookie()
         + ", cookieText="
         + this.getCookieText()
         + ", cookies="
         + this.getCookies()
         + ", embyPassword="
         + this.getEmbyPassword()
         + ")";
   }
}
