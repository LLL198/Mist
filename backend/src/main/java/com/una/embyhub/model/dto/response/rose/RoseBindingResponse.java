package com.una.embyhub.model.dto.response.rose;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class RoseBindingResponse implements Serializable {
   private Long id;
   private Long userId;
   private String embyUserId;
   private String embyUserName;
   private String roseAccountId;
   private String roseAccountName;
   private String roseUserId;
   private String roseUsername;
   private String roseMobile;
   private String roseAvatarUrl;
   private String deviceApp;
   private Boolean hasCookie;
   private String cookieTextMasked;
   private String targetRoot;
   private String targetRootName;
   private String targetRootPath;
   private List<RoseLibraryBindingResponse> libraries;
   private String bindingStatus;
   private Boolean bound;
   private String qrSessionId;
   private String qrStatus;
   private String qrScanUrl;
   private String lastError;
   private Object roseProfile;
   private Object accountSummary;
   private Date cookieUpdatedAt;
   private Date boundAt;
   private Date lastSyncAt;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getRoseAccountId() {
      return this.roseAccountId;
   }

   @Generated
   public String getRoseAccountName() {
      return this.roseAccountName;
   }

   @Generated
   public String getRoseUserId() {
      return this.roseUserId;
   }

   @Generated
   public String getRoseUsername() {
      return this.roseUsername;
   }

   @Generated
   public String getRoseMobile() {
      return this.roseMobile;
   }

   @Generated
   public String getRoseAvatarUrl() {
      return this.roseAvatarUrl;
   }

   @Generated
   public String getDeviceApp() {
      return this.deviceApp;
   }

   @Generated
   public Boolean getHasCookie() {
      return this.hasCookie;
   }

   @Generated
   public String getCookieTextMasked() {
      return this.cookieTextMasked;
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
   public List<RoseLibraryBindingResponse> getLibraries() {
      return this.libraries;
   }

   @Generated
   public String getBindingStatus() {
      return this.bindingStatus;
   }

   @Generated
   public Boolean getBound() {
      return this.bound;
   }

   @Generated
   public String getQrSessionId() {
      return this.qrSessionId;
   }

   @Generated
   public String getQrStatus() {
      return this.qrStatus;
   }

   @Generated
   public String getQrScanUrl() {
      return this.qrScanUrl;
   }

   @Generated
   public String getLastError() {
      return this.lastError;
   }

   @Generated
   public Object getRoseProfile() {
      return this.roseProfile;
   }

   @Generated
   public Object getAccountSummary() {
      return this.accountSummary;
   }

   @Generated
   public Date getCookieUpdatedAt() {
      return this.cookieUpdatedAt;
   }

   @Generated
   public Date getBoundAt() {
      return this.boundAt;
   }

   @Generated
   public Date getLastSyncAt() {
      return this.lastSyncAt;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setRoseAccountId(final String roseAccountId) {
      this.roseAccountId = roseAccountId;
   }

   @Generated
   public void setRoseAccountName(final String roseAccountName) {
      this.roseAccountName = roseAccountName;
   }

   @Generated
   public void setRoseUserId(final String roseUserId) {
      this.roseUserId = roseUserId;
   }

   @Generated
   public void setRoseUsername(final String roseUsername) {
      this.roseUsername = roseUsername;
   }

   @Generated
   public void setRoseMobile(final String roseMobile) {
      this.roseMobile = roseMobile;
   }

   @Generated
   public void setRoseAvatarUrl(final String roseAvatarUrl) {
      this.roseAvatarUrl = roseAvatarUrl;
   }

   @Generated
   public void setDeviceApp(final String deviceApp) {
      this.deviceApp = deviceApp;
   }

   @Generated
   public void setHasCookie(final Boolean hasCookie) {
      this.hasCookie = hasCookie;
   }

   @Generated
   public void setCookieTextMasked(final String cookieTextMasked) {
      this.cookieTextMasked = cookieTextMasked;
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
   public void setLibraries(final List<RoseLibraryBindingResponse> libraries) {
      this.libraries = libraries;
   }

   @Generated
   public void setBindingStatus(final String bindingStatus) {
      this.bindingStatus = bindingStatus;
   }

   @Generated
   public void setBound(final Boolean bound) {
      this.bound = bound;
   }

   @Generated
   public void setQrSessionId(final String qrSessionId) {
      this.qrSessionId = qrSessionId;
   }

   @Generated
   public void setQrStatus(final String qrStatus) {
      this.qrStatus = qrStatus;
   }

   @Generated
   public void setQrScanUrl(final String qrScanUrl) {
      this.qrScanUrl = qrScanUrl;
   }

   @Generated
   public void setLastError(final String lastError) {
      this.lastError = lastError;
   }

   @Generated
   public void setRoseProfile(final Object roseProfile) {
      this.roseProfile = roseProfile;
   }

   @Generated
   public void setAccountSummary(final Object accountSummary) {
      this.accountSummary = accountSummary;
   }

   @Generated
   public void setCookieUpdatedAt(final Date cookieUpdatedAt) {
      this.cookieUpdatedAt = cookieUpdatedAt;
   }

   @Generated
   public void setBoundAt(final Date boundAt) {
      this.boundAt = boundAt;
   }

   @Generated
   public void setLastSyncAt(final Date lastSyncAt) {
      this.lastSyncAt = lastSyncAt;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseBindingResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$hasCookie = this.getHasCookie();
               Object other$hasCookie = other.getHasCookie();
               if (this$hasCookie == null ? other$hasCookie == null : this$hasCookie.equals(other$hasCookie)) {
                  Object this$bound = this.getBound();
                  Object other$bound = other.getBound();
                  if (this$bound == null ? other$bound == null : this$bound.equals(other$bound)) {
                     Object this$embyUserId = this.getEmbyUserId();
                     Object other$embyUserId = other.getEmbyUserId();
                     if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                        Object this$embyUserName = this.getEmbyUserName();
                        Object other$embyUserName = other.getEmbyUserName();
                        if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                           Object this$roseAccountId = this.getRoseAccountId();
                           Object other$roseAccountId = other.getRoseAccountId();
                           if (this$roseAccountId == null ? other$roseAccountId == null : this$roseAccountId.equals(other$roseAccountId)) {
                              Object this$roseAccountName = this.getRoseAccountName();
                              Object other$roseAccountName = other.getRoseAccountName();
                              if (this$roseAccountName == null ? other$roseAccountName == null : this$roseAccountName.equals(other$roseAccountName)) {
                                 Object this$roseUserId = this.getRoseUserId();
                                 Object other$roseUserId = other.getRoseUserId();
                                 if (this$roseUserId == null ? other$roseUserId == null : this$roseUserId.equals(other$roseUserId)) {
                                    Object this$roseUsername = this.getRoseUsername();
                                    Object other$roseUsername = other.getRoseUsername();
                                    if (this$roseUsername == null ? other$roseUsername == null : this$roseUsername.equals(other$roseUsername)) {
                                       Object this$roseMobile = this.getRoseMobile();
                                       Object other$roseMobile = other.getRoseMobile();
                                       if (this$roseMobile == null ? other$roseMobile == null : this$roseMobile.equals(other$roseMobile)) {
                                          Object this$roseAvatarUrl = this.getRoseAvatarUrl();
                                          Object other$roseAvatarUrl = other.getRoseAvatarUrl();
                                          if (this$roseAvatarUrl == null ? other$roseAvatarUrl == null : this$roseAvatarUrl.equals(other$roseAvatarUrl)) {
                                             Object this$deviceApp = this.getDeviceApp();
                                             Object other$deviceApp = other.getDeviceApp();
                                             if (this$deviceApp == null ? other$deviceApp == null : this$deviceApp.equals(other$deviceApp)) {
                                                Object this$cookieTextMasked = this.getCookieTextMasked();
                                                Object other$cookieTextMasked = other.getCookieTextMasked();
                                                if (this$cookieTextMasked == null
                                                   ? other$cookieTextMasked == null
                                                   : this$cookieTextMasked.equals(other$cookieTextMasked)) {
                                                   Object this$targetRoot = this.getTargetRoot();
                                                   Object other$targetRoot = other.getTargetRoot();
                                                   if (this$targetRoot == null ? other$targetRoot == null : this$targetRoot.equals(other$targetRoot)) {
                                                      Object this$targetRootName = this.getTargetRootName();
                                                      Object other$targetRootName = other.getTargetRootName();
                                                      if (this$targetRootName == null
                                                         ? other$targetRootName == null
                                                         : this$targetRootName.equals(other$targetRootName)) {
                                                         Object this$targetRootPath = this.getTargetRootPath();
                                                         Object other$targetRootPath = other.getTargetRootPath();
                                                         if (this$targetRootPath == null
                                                            ? other$targetRootPath == null
                                                            : this$targetRootPath.equals(other$targetRootPath)) {
                                                            Object this$libraries = this.getLibraries();
                                                            Object other$libraries = other.getLibraries();
                                                            if (this$libraries == null ? other$libraries == null : this$libraries.equals(other$libraries)) {
                                                               Object this$bindingStatus = this.getBindingStatus();
                                                               Object other$bindingStatus = other.getBindingStatus();
                                                               if (this$bindingStatus == null
                                                                  ? other$bindingStatus == null
                                                                  : this$bindingStatus.equals(other$bindingStatus)) {
                                                                  Object this$qrSessionId = this.getQrSessionId();
                                                                  Object other$qrSessionId = other.getQrSessionId();
                                                                  if (this$qrSessionId == null
                                                                     ? other$qrSessionId == null
                                                                     : this$qrSessionId.equals(other$qrSessionId)) {
                                                                     Object this$qrStatus = this.getQrStatus();
                                                                     Object other$qrStatus = other.getQrStatus();
                                                                     if (this$qrStatus == null ? other$qrStatus == null : this$qrStatus.equals(other$qrStatus)) {
                                                                        Object this$qrScanUrl = this.getQrScanUrl();
                                                                        Object other$qrScanUrl = other.getQrScanUrl();
                                                                        if (this$qrScanUrl == null
                                                                           ? other$qrScanUrl == null
                                                                           : this$qrScanUrl.equals(other$qrScanUrl)) {
                                                                           Object this$lastError = this.getLastError();
                                                                           Object other$lastError = other.getLastError();
                                                                           if (this$lastError == null
                                                                              ? other$lastError == null
                                                                              : this$lastError.equals(other$lastError)) {
                                                                              Object this$roseProfile = this.getRoseProfile();
                                                                              Object other$roseProfile = other.getRoseProfile();
                                                                              if (this$roseProfile == null
                                                                                 ? other$roseProfile == null
                                                                                 : this$roseProfile.equals(other$roseProfile)) {
                                                                                 Object this$accountSummary = this.getAccountSummary();
                                                                                 Object other$accountSummary = other.getAccountSummary();
                                                                                 if (this$accountSummary == null
                                                                                    ? other$accountSummary == null
                                                                                    : this$accountSummary.equals(other$accountSummary)) {
                                                                                    Object this$cookieUpdatedAt = this.getCookieUpdatedAt();
                                                                                    Object other$cookieUpdatedAt = other.getCookieUpdatedAt();
                                                                                    if (this$cookieUpdatedAt == null
                                                                                       ? other$cookieUpdatedAt == null
                                                                                       : this$cookieUpdatedAt.equals(other$cookieUpdatedAt)) {
                                                                                       Object this$boundAt = this.getBoundAt();
                                                                                       Object other$boundAt = other.getBoundAt();
                                                                                       if (this$boundAt == null
                                                                                          ? other$boundAt == null
                                                                                          : this$boundAt.equals(other$boundAt)) {
                                                                                          Object this$lastSyncAt = this.getLastSyncAt();
                                                                                          Object other$lastSyncAt = other.getLastSyncAt();
                                                                                          return this$lastSyncAt == null
                                                                                             ? other$lastSyncAt == null
                                                                                             : this$lastSyncAt.equals(other$lastSyncAt);
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
      return other instanceof RoseBindingResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $hasCookie = this.getHasCookie();
      result = result * 59 + ($hasCookie == null ? 43 : $hasCookie.hashCode());
      Object $bound = this.getBound();
      result = result * 59 + ($bound == null ? 43 : $bound.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $roseAccountId = this.getRoseAccountId();
      result = result * 59 + ($roseAccountId == null ? 43 : $roseAccountId.hashCode());
      Object $roseAccountName = this.getRoseAccountName();
      result = result * 59 + ($roseAccountName == null ? 43 : $roseAccountName.hashCode());
      Object $roseUserId = this.getRoseUserId();
      result = result * 59 + ($roseUserId == null ? 43 : $roseUserId.hashCode());
      Object $roseUsername = this.getRoseUsername();
      result = result * 59 + ($roseUsername == null ? 43 : $roseUsername.hashCode());
      Object $roseMobile = this.getRoseMobile();
      result = result * 59 + ($roseMobile == null ? 43 : $roseMobile.hashCode());
      Object $roseAvatarUrl = this.getRoseAvatarUrl();
      result = result * 59 + ($roseAvatarUrl == null ? 43 : $roseAvatarUrl.hashCode());
      Object $deviceApp = this.getDeviceApp();
      result = result * 59 + ($deviceApp == null ? 43 : $deviceApp.hashCode());
      Object $cookieTextMasked = this.getCookieTextMasked();
      result = result * 59 + ($cookieTextMasked == null ? 43 : $cookieTextMasked.hashCode());
      Object $targetRoot = this.getTargetRoot();
      result = result * 59 + ($targetRoot == null ? 43 : $targetRoot.hashCode());
      Object $targetRootName = this.getTargetRootName();
      result = result * 59 + ($targetRootName == null ? 43 : $targetRootName.hashCode());
      Object $targetRootPath = this.getTargetRootPath();
      result = result * 59 + ($targetRootPath == null ? 43 : $targetRootPath.hashCode());
      Object $libraries = this.getLibraries();
      result = result * 59 + ($libraries == null ? 43 : $libraries.hashCode());
      Object $bindingStatus = this.getBindingStatus();
      result = result * 59 + ($bindingStatus == null ? 43 : $bindingStatus.hashCode());
      Object $qrSessionId = this.getQrSessionId();
      result = result * 59 + ($qrSessionId == null ? 43 : $qrSessionId.hashCode());
      Object $qrStatus = this.getQrStatus();
      result = result * 59 + ($qrStatus == null ? 43 : $qrStatus.hashCode());
      Object $qrScanUrl = this.getQrScanUrl();
      result = result * 59 + ($qrScanUrl == null ? 43 : $qrScanUrl.hashCode());
      Object $lastError = this.getLastError();
      result = result * 59 + ($lastError == null ? 43 : $lastError.hashCode());
      Object $roseProfile = this.getRoseProfile();
      result = result * 59 + ($roseProfile == null ? 43 : $roseProfile.hashCode());
      Object $accountSummary = this.getAccountSummary();
      result = result * 59 + ($accountSummary == null ? 43 : $accountSummary.hashCode());
      Object $cookieUpdatedAt = this.getCookieUpdatedAt();
      result = result * 59 + ($cookieUpdatedAt == null ? 43 : $cookieUpdatedAt.hashCode());
      Object $boundAt = this.getBoundAt();
      result = result * 59 + ($boundAt == null ? 43 : $boundAt.hashCode());
      Object $lastSyncAt = this.getLastSyncAt();
      return result * 59 + ($lastSyncAt == null ? 43 : $lastSyncAt.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RoseBindingResponse(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", roseAccountId="
         + this.getRoseAccountId()
         + ", roseAccountName="
         + this.getRoseAccountName()
         + ", roseUserId="
         + this.getRoseUserId()
         + ", roseUsername="
         + this.getRoseUsername()
         + ", roseMobile="
         + this.getRoseMobile()
         + ", roseAvatarUrl="
         + this.getRoseAvatarUrl()
         + ", deviceApp="
         + this.getDeviceApp()
         + ", hasCookie="
         + this.getHasCookie()
         + ", cookieTextMasked="
         + this.getCookieTextMasked()
         + ", targetRoot="
         + this.getTargetRoot()
         + ", targetRootName="
         + this.getTargetRootName()
         + ", targetRootPath="
         + this.getTargetRootPath()
         + ", libraries="
         + this.getLibraries()
         + ", bindingStatus="
         + this.getBindingStatus()
         + ", bound="
         + this.getBound()
         + ", qrSessionId="
         + this.getQrSessionId()
         + ", qrStatus="
         + this.getQrStatus()
         + ", qrScanUrl="
         + this.getQrScanUrl()
         + ", lastError="
         + this.getLastError()
         + ", roseProfile="
         + this.getRoseProfile()
         + ", accountSummary="
         + this.getAccountSummary()
         + ", cookieUpdatedAt="
         + this.getCookieUpdatedAt()
         + ", boundAt="
         + this.getBoundAt()
         + ", lastSyncAt="
         + this.getLastSyncAt()
         + ")";
   }
}
