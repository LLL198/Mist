package com.una.embyhub.model.dto.request.rose;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class RoseBindRequest implements Serializable {
   private String app;
   private String cookie;
   private String embyPassword;
   private String targetRoot;
   private String targetRootName;
   private String targetRootPath;
   private List<RoseLibraryBindingRequest> libraries;

   @Generated
   public String getApp() {
      return this.app;
   }

   @Generated
   public String getCookie() {
      return this.cookie;
   }

   @Generated
   public String getEmbyPassword() {
      return this.embyPassword;
   }

   @Generated
   public String getTargetRoot() {
      return this.targetRoot;
   }

   @Generated
   public String getTargetRootName() {
      return this.targetRootName;
   }

   @Generated
   public String getTargetRootPath() {
      return this.targetRootPath;
   }

   @Generated
   public List<RoseLibraryBindingRequest> getLibraries() {
      return this.libraries;
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
   public void setEmbyPassword(final String embyPassword) {
      this.embyPassword = embyPassword;
   }

   @Generated
   public void setTargetRoot(final String targetRoot) {
      this.targetRoot = targetRoot;
   }

   @Generated
   public void setTargetRootName(final String targetRootName) {
      this.targetRootName = targetRootName;
   }

   @Generated
   public void setTargetRootPath(final String targetRootPath) {
      this.targetRootPath = targetRootPath;
   }

   @Generated
   public void setLibraries(final List<RoseLibraryBindingRequest> libraries) {
      this.libraries = libraries;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseBindRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$app = this.getApp();
         Object other$app = other.getApp();
         if (this$app == null ? other$app == null : this$app.equals(other$app)) {
            Object this$cookie = this.getCookie();
            Object other$cookie = other.getCookie();
            if (this$cookie == null ? other$cookie == null : this$cookie.equals(other$cookie)) {
               Object this$embyPassword = this.getEmbyPassword();
               Object other$embyPassword = other.getEmbyPassword();
               if (this$embyPassword == null ? other$embyPassword == null : this$embyPassword.equals(other$embyPassword)) {
                  Object this$targetRoot = this.getTargetRoot();
                  Object other$targetRoot = other.getTargetRoot();
                  if (this$targetRoot == null ? other$targetRoot == null : this$targetRoot.equals(other$targetRoot)) {
                     Object this$targetRootName = this.getTargetRootName();
                     Object other$targetRootName = other.getTargetRootName();
                     if (this$targetRootName == null ? other$targetRootName == null : this$targetRootName.equals(other$targetRootName)) {
                        Object this$targetRootPath = this.getTargetRootPath();
                        Object other$targetRootPath = other.getTargetRootPath();
                        if (this$targetRootPath == null ? other$targetRootPath == null : this$targetRootPath.equals(other$targetRootPath)) {
                           Object this$libraries = this.getLibraries();
                           Object other$libraries = other.getLibraries();
                           return this$libraries == null ? other$libraries == null : this$libraries.equals(other$libraries);
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
      return other instanceof RoseBindRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $app = this.getApp();
      result = result * 59 + ($app == null ? 43 : $app.hashCode());
      Object $cookie = this.getCookie();
      result = result * 59 + ($cookie == null ? 43 : $cookie.hashCode());
      Object $embyPassword = this.getEmbyPassword();
      result = result * 59 + ($embyPassword == null ? 43 : $embyPassword.hashCode());
      Object $targetRoot = this.getTargetRoot();
      result = result * 59 + ($targetRoot == null ? 43 : $targetRoot.hashCode());
      Object $targetRootName = this.getTargetRootName();
      result = result * 59 + ($targetRootName == null ? 43 : $targetRootName.hashCode());
      Object $targetRootPath = this.getTargetRootPath();
      result = result * 59 + ($targetRootPath == null ? 43 : $targetRootPath.hashCode());
      Object $libraries = this.getLibraries();
      return result * 59 + ($libraries == null ? 43 : $libraries.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RoseBindRequest(app="
         + this.getApp()
         + ", cookie="
         + this.getCookie()
         + ", embyPassword="
         + this.getEmbyPassword()
         + ", targetRoot="
         + this.getTargetRoot()
         + ", targetRootName="
         + this.getTargetRootName()
         + ", targetRootPath="
         + this.getTargetRootPath()
         + ", libraries="
         + this.getLibraries()
         + ")";
   }
}
