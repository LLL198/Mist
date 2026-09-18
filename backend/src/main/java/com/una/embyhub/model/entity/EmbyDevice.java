package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Generated;

@TableName("emby_device")
public class EmbyDevice extends BaseEntity {
   public static final String COL_DEVICE_ID = "device_id";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("device_id")
   private String deviceId;
   @TableField("device_name")
   private String deviceName;
   @TableField("app_name")
   private String appName;
   @TableField("app_version")
   private String appVersion;
   @TableField("last_user_id")
   private String lastUserId;
   @TableField("last_user_name")
   private String lastUserName;
   @TableField("blocked")
   private Integer blocked;
   @TableField("last_activity_time")
   private Date lastActivityTime;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("emby_server_id")
   private String embyServerId;
   @TableField("server_name")
   private String serverName;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getDeviceId() {
      return this.deviceId;
   }

   @Generated
   public String getDeviceName() {
      return this.deviceName;
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
   public Integer getBlocked() {
      return this.blocked;
   }

   @Generated
   public Date getLastActivityTime() {
      return this.lastActivityTime;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getEmbyServerId() {
      return this.embyServerId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setDeviceId(final String deviceId) {
      this.deviceId = deviceId;
   }

   @Generated
   public void setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
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
   public void setBlocked(final Integer blocked) {
      this.blocked = blocked;
   }

   @Generated
   public void setLastActivityTime(final Date lastActivityTime) {
      this.lastActivityTime = lastActivityTime;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEmbyServerId(final String embyServerId) {
      this.embyServerId = embyServerId;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyDevice(id="
         + this.getId()
         + ", deviceId="
         + this.getDeviceId()
         + ", deviceName="
         + this.getDeviceName()
         + ", appName="
         + this.getAppName()
         + ", appVersion="
         + this.getAppVersion()
         + ", lastUserId="
         + this.getLastUserId()
         + ", lastUserName="
         + this.getLastUserName()
         + ", blocked="
         + this.getBlocked()
         + ", lastActivityTime="
         + this.getLastActivityTime()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", serverName="
         + this.getServerName()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyDevice other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$blocked = this.getBlocked();
            Object other$blocked = other.getBlocked();
            if (this$blocked == null ? other$blocked == null : this$blocked.equals(other$blocked)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                  Object this$deviceId = this.getDeviceId();
                  Object other$deviceId = other.getDeviceId();
                  if (this$deviceId == null ? other$deviceId == null : this$deviceId.equals(other$deviceId)) {
                     Object this$deviceName = this.getDeviceName();
                     Object other$deviceName = other.getDeviceName();
                     if (this$deviceName == null ? other$deviceName == null : this$deviceName.equals(other$deviceName)) {
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
                                    Object this$lastActivityTime = this.getLastActivityTime();
                                    Object other$lastActivityTime = other.getLastActivityTime();
                                    if (this$lastActivityTime == null ? other$lastActivityTime == null : this$lastActivityTime.equals(other$lastActivityTime)) {
                                       Object this$embyServerId = this.getEmbyServerId();
                                       Object other$embyServerId = other.getEmbyServerId();
                                       if (this$embyServerId == null ? other$embyServerId == null : this$embyServerId.equals(other$embyServerId)) {
                                          Object this$serverName = this.getServerName();
                                          Object other$serverName = other.getServerName();
                                          return this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName);
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
      return other instanceof EmbyDevice;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $blocked = this.getBlocked();
      result = result * 59 + ($blocked == null ? 43 : $blocked.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $deviceId = this.getDeviceId();
      result = result * 59 + ($deviceId == null ? 43 : $deviceId.hashCode());
      Object $deviceName = this.getDeviceName();
      result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
      Object $appName = this.getAppName();
      result = result * 59 + ($appName == null ? 43 : $appName.hashCode());
      Object $appVersion = this.getAppVersion();
      result = result * 59 + ($appVersion == null ? 43 : $appVersion.hashCode());
      Object $lastUserId = this.getLastUserId();
      result = result * 59 + ($lastUserId == null ? 43 : $lastUserId.hashCode());
      Object $lastUserName = this.getLastUserName();
      result = result * 59 + ($lastUserName == null ? 43 : $lastUserName.hashCode());
      Object $lastActivityTime = this.getLastActivityTime();
      result = result * 59 + ($lastActivityTime == null ? 43 : $lastActivityTime.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $serverName = this.getServerName();
      return result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
   }
}
