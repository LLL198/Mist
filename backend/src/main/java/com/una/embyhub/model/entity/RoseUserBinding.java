package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("rose_user_binding")
public class RoseUserBinding extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_id")
   private String embyUserId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("rose_account_id")
   private String roseAccountId;
   @TableField("rose_account_name")
   private String roseAccountName;
   @TableField("rose_user_id")
   private String roseUserId;
   @TableField("rose_username")
   private String roseUsername;
   @TableField("rose_mobile")
   private String roseMobile;
   @TableField("rose_avatar_url")
   private String roseAvatarUrl;
   @TableField("device_app")
   private String deviceApp;
   @TableField("cookie_text")
   private String cookieText;
   @TableField("cookie_text_masked")
   private String cookieTextMasked;
   @TableField("rose_auth_cookie_text")
   private String roseAuthCookieText;
   @TableField("target_root")
   private String targetRoot;
   @TableField("libraries_json")
   private String librariesJson;
   @TableField("binding_status")
   private String bindingStatus;
   @TableField("qr_session_id")
   private String qrSessionId;
   @TableField("qr_status")
   private String qrStatus;
   @TableField("qr_scan_url")
   private String qrScanUrl;
   @TableField("last_error")
   private String lastError;
   @TableField("rose_profile_json")
   private String roseProfileJson;
   @TableField("account_summary_json")
   private String accountSummaryJson;
   @TableField("cookie_updated_at")
   private Date cookieUpdatedAt;
   @TableField("bound_at")
   private Date boundAt;
   @TableField("last_sync_at")
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public String getCookieText() {
      return this.cookieText;
   }

   @Generated
   public String getCookieTextMasked() {
      return this.cookieTextMasked;
   }

   @Generated
   public String getRoseAuthCookieText() {
      return this.roseAuthCookieText;
   }

   @Generated
   public String getTargetRoot() {
      return this.targetRoot;
   }

   @Generated
   public String getLibrariesJson() {
      return this.librariesJson;
   }

   @Generated
   public String getBindingStatus() {
      return this.bindingStatus;
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
   public String getRoseProfileJson() {
      return this.roseProfileJson;
   }

   @Generated
   public String getAccountSummaryJson() {
      return this.accountSummaryJson;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
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
   public void setCookieText(final String cookieText) {
      this.cookieText = cookieText;
   }

   @Generated
   public void setCookieTextMasked(final String cookieTextMasked) {
      this.cookieTextMasked = cookieTextMasked;
   }

   @Generated
   public void setRoseAuthCookieText(final String roseAuthCookieText) {
      this.roseAuthCookieText = roseAuthCookieText;
   }

   @Generated
   public void setTargetRoot(final String targetRoot) {
      this.targetRoot = targetRoot;
   }

   @Generated
   public void setLibrariesJson(final String librariesJson) {
      this.librariesJson = librariesJson;
   }

   @Generated
   public void setBindingStatus(final String bindingStatus) {
      this.bindingStatus = bindingStatus;
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
   public void setRoseProfileJson(final String roseProfileJson) {
      this.roseProfileJson = roseProfileJson;
   }

   @Generated
   public void setAccountSummaryJson(final String accountSummaryJson) {
      this.accountSummaryJson = accountSummaryJson;
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
   public String toString() {
      return "RoseUserBinding(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
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
         + ", cookieText="
         + this.getCookieText()
         + ", cookieTextMasked="
         + this.getCookieTextMasked()
         + ", roseAuthCookieText="
         + this.getRoseAuthCookieText()
         + ", targetRoot="
         + this.getTargetRoot()
         + ", librariesJson="
         + this.getLibrariesJson()
         + ", bindingStatus="
         + this.getBindingStatus()
         + ", qrSessionId="
         + this.getQrSessionId()
         + ", qrStatus="
         + this.getQrStatus()
         + ", qrScanUrl="
         + this.getQrScanUrl()
         + ", lastError="
         + this.getLastError()
         + ", roseProfileJson="
         + this.getRoseProfileJson()
         + ", accountSummaryJson="
         + this.getAccountSummaryJson()
         + ", cookieUpdatedAt="
         + this.getCookieUpdatedAt()
         + ", boundAt="
         + this.getBoundAt()
         + ", lastSyncAt="
         + this.getLastSyncAt()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseUserBinding other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
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
                                             Object this$cookieText = this.getCookieText();
                                             Object other$cookieText = other.getCookieText();
                                             if (this$cookieText == null ? other$cookieText == null : this$cookieText.equals(other$cookieText)) {
                                                Object this$cookieTextMasked = this.getCookieTextMasked();
                                                Object other$cookieTextMasked = other.getCookieTextMasked();
                                                if (this$cookieTextMasked == null
                                                   ? other$cookieTextMasked == null
                                                   : this$cookieTextMasked.equals(other$cookieTextMasked)) {
                                                   Object this$roseAuthCookieText = this.getRoseAuthCookieText();
                                                   Object other$roseAuthCookieText = other.getRoseAuthCookieText();
                                                   if (this$roseAuthCookieText == null
                                                      ? other$roseAuthCookieText == null
                                                      : this$roseAuthCookieText.equals(other$roseAuthCookieText)) {
                                                      Object this$targetRoot = this.getTargetRoot();
                                                      Object other$targetRoot = other.getTargetRoot();
                                                      if (this$targetRoot == null ? other$targetRoot == null : this$targetRoot.equals(other$targetRoot)) {
                                                         Object this$librariesJson = this.getLibrariesJson();
                                                         Object other$librariesJson = other.getLibrariesJson();
                                                         if (this$librariesJson == null
                                                            ? other$librariesJson == null
                                                            : this$librariesJson.equals(other$librariesJson)) {
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
                                                                           Object this$roseProfileJson = this.getRoseProfileJson();
                                                                           Object other$roseProfileJson = other.getRoseProfileJson();
                                                                           if (this$roseProfileJson == null
                                                                              ? other$roseProfileJson == null
                                                                              : this$roseProfileJson.equals(other$roseProfileJson)) {
                                                                              Object this$accountSummaryJson = this.getAccountSummaryJson();
                                                                              Object other$accountSummaryJson = other.getAccountSummaryJson();
                                                                              if (this$accountSummaryJson == null
                                                                                 ? other$accountSummaryJson == null
                                                                                 : this$accountSummaryJson.equals(other$accountSummaryJson)) {
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
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof RoseUserBinding;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
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
      Object $cookieText = this.getCookieText();
      result = result * 59 + ($cookieText == null ? 43 : $cookieText.hashCode());
      Object $cookieTextMasked = this.getCookieTextMasked();
      result = result * 59 + ($cookieTextMasked == null ? 43 : $cookieTextMasked.hashCode());
      Object $roseAuthCookieText = this.getRoseAuthCookieText();
      result = result * 59 + ($roseAuthCookieText == null ? 43 : $roseAuthCookieText.hashCode());
      Object $targetRoot = this.getTargetRoot();
      result = result * 59 + ($targetRoot == null ? 43 : $targetRoot.hashCode());
      Object $librariesJson = this.getLibrariesJson();
      result = result * 59 + ($librariesJson == null ? 43 : $librariesJson.hashCode());
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
      Object $roseProfileJson = this.getRoseProfileJson();
      result = result * 59 + ($roseProfileJson == null ? 43 : $roseProfileJson.hashCode());
      Object $accountSummaryJson = this.getAccountSummaryJson();
      result = result * 59 + ($accountSummaryJson == null ? 43 : $accountSummaryJson.hashCode());
      Object $cookieUpdatedAt = this.getCookieUpdatedAt();
      result = result * 59 + ($cookieUpdatedAt == null ? 43 : $cookieUpdatedAt.hashCode());
      Object $boundAt = this.getBoundAt();
      result = result * 59 + ($boundAt == null ? 43 : $boundAt.hashCode());
      Object $lastSyncAt = this.getLastSyncAt();
      return result * 59 + ($lastSyncAt == null ? 43 : $lastSyncAt.hashCode());
   }
}
