package com.una.embyhub.model.dto.response.emby;

import com.google.gson.annotations.SerializedName;
import java.time.OffsetDateTime;
import lombok.Generated;

public class EmbyDeviceInfoResponse {
   @SerializedName("Id")
   private String id;
   @SerializedName("Name")
   private String name;
   @SerializedName("AppName")
   private String appName;
   @SerializedName("AppVersion")
   private String appVersion;
   @SerializedName("LastUserId")
   private String lastUserId;
   @SerializedName("LastUserName")
   private String lastUserName;
   @SerializedName("DateLastActivity")
   private OffsetDateTime dateLastActivity;
   @SerializedName("IsBlocked")
   private Boolean blocked;
   @SerializedName("ServerId")
   private String serverId;

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getAppName() {
      return this.appName;
   }

   @Generated
   public String getAppVersion() {
      return this.appVersion;
   }

   @Generated
   public String getLastUserId() {
      return this.lastUserId;
   }

   @Generated
   public String getLastUserName() {
      return this.lastUserName;
   }

   @Generated
   public OffsetDateTime getDateLastActivity() {
      return this.dateLastActivity;
   }

   @Generated
   public Boolean getBlocked() {
      return this.blocked;
   }

   @Generated
   public String getServerId() {
      return this.serverId;
   }

   @Generated
   public void setId(final String id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setAppName(final String appName) {
      this.appName = appName;
   }

   @Generated
   public void setAppVersion(final String appVersion) {
      this.appVersion = appVersion;
   }

   @Generated
   public void setLastUserId(final String lastUserId) {
      this.lastUserId = lastUserId;
   }

   @Generated
   public void setLastUserName(final String lastUserName) {
      this.lastUserName = lastUserName;
   }

   @Generated
   public void setDateLastActivity(final OffsetDateTime dateLastActivity) {
      this.dateLastActivity = dateLastActivity;
   }

   @Generated
   public void setBlocked(final Boolean blocked) {
      this.blocked = blocked;
   }

   @Generated
   public void setServerId(final String serverId) {
      this.serverId = serverId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyDeviceInfoResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$blocked = this.getBlocked();
         Object other$blocked = other.getBlocked();
         if (this$blocked == null ? other$blocked == null : this$blocked.equals(other$blocked)) {
            Object this$id = this.getId();
            Object other$id = other.getId();
            if (this$id == null ? other$id == null : this$id.equals(other$id)) {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                  Object this$appName = this.getAppName();
                  Object other$appName = other.getAppName();
                  if (this$appName == null ? other$appName == null : this$appName.equals(other$appName)) {
                     Object this$appVersion = this.getAppVersion();
                     Object other$appVersion = other.getAppVersion();
                     if (this$appVersion == null ? other$appVersion == null : this$appVersion.equals(other$appVersion)) {
                        Object this$lastUserId = this.getLastUserId();
                        Object other$lastUserId = other.getLastUserId();
                        if (this$lastUserId == null ? other$lastUserId == null : this$lastUserId.equals(other$lastUserId)) {
                           Object this$lastUserName = this.getLastUserName();
                           Object other$lastUserName = other.getLastUserName();
                           if (this$lastUserName == null ? other$lastUserName == null : this$lastUserName.equals(other$lastUserName)) {
                              Object this$dateLastActivity = this.getDateLastActivity();
                              Object other$dateLastActivity = other.getDateLastActivity();
                              if (this$dateLastActivity == null ? other$dateLastActivity == null : this$dateLastActivity.equals(other$dateLastActivity)) {
                                 Object this$serverId = this.getServerId();
                                 Object other$serverId = other.getServerId();
                                 return this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId);
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
      return other instanceof EmbyDeviceInfoResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $blocked = this.getBlocked();
      result = result * 59 + ($blocked == null ? 43 : $blocked.hashCode());
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $appName = this.getAppName();
      result = result * 59 + ($appName == null ? 43 : $appName.hashCode());
      Object $appVersion = this.getAppVersion();
      result = result * 59 + ($appVersion == null ? 43 : $appVersion.hashCode());
      Object $lastUserId = this.getLastUserId();
      result = result * 59 + ($lastUserId == null ? 43 : $lastUserId.hashCode());
      Object $lastUserName = this.getLastUserName();
      result = result * 59 + ($lastUserName == null ? 43 : $lastUserName.hashCode());
      Object $dateLastActivity = this.getDateLastActivity();
      result = result * 59 + ($dateLastActivity == null ? 43 : $dateLastActivity.hashCode());
      Object $serverId = this.getServerId();
      return result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyDeviceInfoResponse(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", appName="
         + this.getAppName()
         + ", appVersion="
         + this.getAppVersion()
         + ", lastUserId="
         + this.getLastUserId()
         + ", lastUserName="
         + this.getLastUserName()
         + ", dateLastActivity="
         + this.getDateLastActivity()
         + ", blocked="
         + this.getBlocked()
         + ", serverId="
         + this.getServerId()
         + ")";
   }
}
