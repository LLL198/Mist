package com.una.embyhub.model.dto.response.embyclientfilter;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyClientFilterRecordResponse implements Serializable {
   private Long id;
   private Long embyInfoId;
   private String embyServerId;
   private String serverName;
   private String event;
   private String filterType;
   private String embyUserId;
   private String embyUserName;
   private String sessionId;
   private String clientName;
   private String deviceName;
   private String deviceId;
   private String applicationVersion;
   private String remoteEndpoint;
   private String resolvedIp;
   private String country;
   private String province;
   private String city;
   private String itemId;
   private String itemName;
   private String itemType;
   private String matchedPattern;
   private Integer usingDefaultPatterns;
   private Integer stopSuccess;
   private Integer messageSuccess;
   private Integer blockUserEnabled;
   private Integer blockUserSuccess;
   private Integer notifySent;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date triggerTime;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date createDatetime;

   @Generated
   public Long getId() {
      return this.id;
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
   public String getEvent() {
      return this.event;
   }

   @Generated
   public String getFilterType() {
      return this.filterType;
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
   public String getSessionId() {
      return this.sessionId;
   }

   @Generated
   public String getClientName() {
      return this.clientName;
   }

   @Generated
   public String getDeviceName() {
      return this.deviceName;
   }

   @Generated
   public String getDeviceId() {
      return this.deviceId;
   }

   @Generated
   public String getApplicationVersion() {
      return this.applicationVersion;
   }

   @Generated
   public String getRemoteEndpoint() {
      return this.remoteEndpoint;
   }

   @Generated
   public String getResolvedIp() {
      return this.resolvedIp;
   }

   @Generated
   public String getCountry() {
      return this.country;
   }

   @Generated
   public String getProvince() {
      return this.province;
   }

   @Generated
   public String getCity() {
      return this.city;
   }

   @Generated
   public String getItemId() {
      return this.itemId;
   }

   @Generated
   public String getItemName() {
      return this.itemName;
   }

   @Generated
   public String getItemType() {
      return this.itemType;
   }

   @Generated
   public String getMatchedPattern() {
      return this.matchedPattern;
   }

   @Generated
   public Integer getUsingDefaultPatterns() {
      return this.usingDefaultPatterns;
   }

   @Generated
   public Integer getStopSuccess() {
      return this.stopSuccess;
   }

   @Generated
   public Integer getMessageSuccess() {
      return this.messageSuccess;
   }

   @Generated
   public Integer getBlockUserEnabled() {
      return this.blockUserEnabled;
   }

   @Generated
   public Integer getBlockUserSuccess() {
      return this.blockUserSuccess;
   }

   @Generated
   public Integer getNotifySent() {
      return this.notifySent;
   }

   @Generated
   public Date getTriggerTime() {
      return this.triggerTime;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
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
   public void setEvent(final String event) {
      this.event = event;
   }

   @Generated
   public void setFilterType(final String filterType) {
      this.filterType = filterType;
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
   public void setSessionId(final String sessionId) {
      this.sessionId = sessionId;
   }

   @Generated
   public void setClientName(final String clientName) {
      this.clientName = clientName;
   }

   @Generated
   public void setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
   }

   @Generated
   public void setDeviceId(final String deviceId) {
      this.deviceId = deviceId;
   }

   @Generated
   public void setApplicationVersion(final String applicationVersion) {
      this.applicationVersion = applicationVersion;
   }

   @Generated
   public void setRemoteEndpoint(final String remoteEndpoint) {
      this.remoteEndpoint = remoteEndpoint;
   }

   @Generated
   public void setResolvedIp(final String resolvedIp) {
      this.resolvedIp = resolvedIp;
   }

   @Generated
   public void setCountry(final String country) {
      this.country = country;
   }

   @Generated
   public void setProvince(final String province) {
      this.province = province;
   }

   @Generated
   public void setCity(final String city) {
      this.city = city;
   }

   @Generated
   public void setItemId(final String itemId) {
      this.itemId = itemId;
   }

   @Generated
   public void setItemName(final String itemName) {
      this.itemName = itemName;
   }

   @Generated
   public void setItemType(final String itemType) {
      this.itemType = itemType;
   }

   @Generated
   public void setMatchedPattern(final String matchedPattern) {
      this.matchedPattern = matchedPattern;
   }

   @Generated
   public void setUsingDefaultPatterns(final Integer usingDefaultPatterns) {
      this.usingDefaultPatterns = usingDefaultPatterns;
   }

   @Generated
   public void setStopSuccess(final Integer stopSuccess) {
      this.stopSuccess = stopSuccess;
   }

   @Generated
   public void setMessageSuccess(final Integer messageSuccess) {
      this.messageSuccess = messageSuccess;
   }

   @Generated
   public void setBlockUserEnabled(final Integer blockUserEnabled) {
      this.blockUserEnabled = blockUserEnabled;
   }

   @Generated
   public void setBlockUserSuccess(final Integer blockUserSuccess) {
      this.blockUserSuccess = blockUserSuccess;
   }

   @Generated
   public void setNotifySent(final Integer notifySent) {
      this.notifySent = notifySent;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setTriggerTime(final Date triggerTime) {
      this.triggerTime = triggerTime;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyClientFilterRecordResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$usingDefaultPatterns = this.getUsingDefaultPatterns();
               Object other$usingDefaultPatterns = other.getUsingDefaultPatterns();
               if (this$usingDefaultPatterns == null ? other$usingDefaultPatterns == null : this$usingDefaultPatterns.equals(other$usingDefaultPatterns)) {
                  Object this$stopSuccess = this.getStopSuccess();
                  Object other$stopSuccess = other.getStopSuccess();
                  if (this$stopSuccess == null ? other$stopSuccess == null : this$stopSuccess.equals(other$stopSuccess)) {
                     Object this$messageSuccess = this.getMessageSuccess();
                     Object other$messageSuccess = other.getMessageSuccess();
                     if (this$messageSuccess == null ? other$messageSuccess == null : this$messageSuccess.equals(other$messageSuccess)) {
                        Object this$blockUserEnabled = this.getBlockUserEnabled();
                        Object other$blockUserEnabled = other.getBlockUserEnabled();
                        if (this$blockUserEnabled == null ? other$blockUserEnabled == null : this$blockUserEnabled.equals(other$blockUserEnabled)) {
                           Object this$blockUserSuccess = this.getBlockUserSuccess();
                           Object other$blockUserSuccess = other.getBlockUserSuccess();
                           if (this$blockUserSuccess == null ? other$blockUserSuccess == null : this$blockUserSuccess.equals(other$blockUserSuccess)) {
                              Object this$notifySent = this.getNotifySent();
                              Object other$notifySent = other.getNotifySent();
                              if (this$notifySent == null ? other$notifySent == null : this$notifySent.equals(other$notifySent)) {
                                 Object this$embyServerId = this.getEmbyServerId();
                                 Object other$embyServerId = other.getEmbyServerId();
                                 if (this$embyServerId == null ? other$embyServerId == null : this$embyServerId.equals(other$embyServerId)) {
                                    Object this$serverName = this.getServerName();
                                    Object other$serverName = other.getServerName();
                                    if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                       Object this$event = this.getEvent();
                                       Object other$event = other.getEvent();
                                       if (this$event == null ? other$event == null : this$event.equals(other$event)) {
                                          Object this$filterType = this.getFilterType();
                                          Object other$filterType = other.getFilterType();
                                          if (this$filterType == null ? other$filterType == null : this$filterType.equals(other$filterType)) {
                                             Object this$embyUserId = this.getEmbyUserId();
                                             Object other$embyUserId = other.getEmbyUserId();
                                             if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                                                Object this$embyUserName = this.getEmbyUserName();
                                                Object other$embyUserName = other.getEmbyUserName();
                                                if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                                   Object this$sessionId = this.getSessionId();
                                                   Object other$sessionId = other.getSessionId();
                                                   if (this$sessionId == null ? other$sessionId == null : this$sessionId.equals(other$sessionId)) {
                                                      Object this$clientName = this.getClientName();
                                                      Object other$clientName = other.getClientName();
                                                      if (this$clientName == null ? other$clientName == null : this$clientName.equals(other$clientName)) {
                                                         Object this$deviceName = this.getDeviceName();
                                                         Object other$deviceName = other.getDeviceName();
                                                         if (this$deviceName == null ? other$deviceName == null : this$deviceName.equals(other$deviceName)) {
                                                            Object this$deviceId = this.getDeviceId();
                                                            Object other$deviceId = other.getDeviceId();
                                                            if (this$deviceId == null ? other$deviceId == null : this$deviceId.equals(other$deviceId)) {
                                                               Object this$applicationVersion = this.getApplicationVersion();
                                                               Object other$applicationVersion = other.getApplicationVersion();
                                                               if (this$applicationVersion == null
                                                                  ? other$applicationVersion == null
                                                                  : this$applicationVersion.equals(other$applicationVersion)) {
                                                                  Object this$remoteEndpoint = this.getRemoteEndpoint();
                                                                  Object other$remoteEndpoint = other.getRemoteEndpoint();
                                                                  if (this$remoteEndpoint == null
                                                                     ? other$remoteEndpoint == null
                                                                     : this$remoteEndpoint.equals(other$remoteEndpoint)) {
                                                                     Object this$resolvedIp = this.getResolvedIp();
                                                                     Object other$resolvedIp = other.getResolvedIp();
                                                                     if (this$resolvedIp == null
                                                                        ? other$resolvedIp == null
                                                                        : this$resolvedIp.equals(other$resolvedIp)) {
                                                                        Object this$country = this.getCountry();
                                                                        Object other$country = other.getCountry();
                                                                        if (this$country == null ? other$country == null : this$country.equals(other$country)) {
                                                                           Object this$province = this.getProvince();
                                                                           Object other$province = other.getProvince();
                                                                           if (this$province == null
                                                                              ? other$province == null
                                                                              : this$province.equals(other$province)) {
                                                                              Object this$city = this.getCity();
                                                                              Object other$city = other.getCity();
                                                                              if (this$city == null ? other$city == null : this$city.equals(other$city)) {
                                                                                 Object this$itemId = this.getItemId();
                                                                                 Object other$itemId = other.getItemId();
                                                                                 if (this$itemId == null
                                                                                    ? other$itemId == null
                                                                                    : this$itemId.equals(other$itemId)) {
                                                                                    Object this$itemName = this.getItemName();
                                                                                    Object other$itemName = other.getItemName();
                                                                                    if (this$itemName == null
                                                                                       ? other$itemName == null
                                                                                       : this$itemName.equals(other$itemName)) {
                                                                                       Object this$itemType = this.getItemType();
                                                                                       Object other$itemType = other.getItemType();
                                                                                       if (this$itemType == null
                                                                                          ? other$itemType == null
                                                                                          : this$itemType.equals(other$itemType)) {
                                                                                          Object this$matchedPattern = this.getMatchedPattern();
                                                                                          Object other$matchedPattern = other.getMatchedPattern();
                                                                                          if (this$matchedPattern == null
                                                                                             ? other$matchedPattern == null
                                                                                             : this$matchedPattern.equals(other$matchedPattern)) {
                                                                                             Object this$triggerTime = this.getTriggerTime();
                                                                                             Object other$triggerTime = other.getTriggerTime();
                                                                                             if (this$triggerTime == null
                                                                                                ? other$triggerTime == null
                                                                                                : this$triggerTime.equals(other$triggerTime)) {
                                                                                                Object this$createDatetime = this.getCreateDatetime();
                                                                                                Object other$createDatetime = other.getCreateDatetime();
                                                                                                return this$createDatetime == null
                                                                                                   ? other$createDatetime == null
                                                                                                   : this$createDatetime.equals(other$createDatetime);
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
      return other instanceof EmbyClientFilterRecordResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $usingDefaultPatterns = this.getUsingDefaultPatterns();
      result = result * 59 + ($usingDefaultPatterns == null ? 43 : $usingDefaultPatterns.hashCode());
      Object $stopSuccess = this.getStopSuccess();
      result = result * 59 + ($stopSuccess == null ? 43 : $stopSuccess.hashCode());
      Object $messageSuccess = this.getMessageSuccess();
      result = result * 59 + ($messageSuccess == null ? 43 : $messageSuccess.hashCode());
      Object $blockUserEnabled = this.getBlockUserEnabled();
      result = result * 59 + ($blockUserEnabled == null ? 43 : $blockUserEnabled.hashCode());
      Object $blockUserSuccess = this.getBlockUserSuccess();
      result = result * 59 + ($blockUserSuccess == null ? 43 : $blockUserSuccess.hashCode());
      Object $notifySent = this.getNotifySent();
      result = result * 59 + ($notifySent == null ? 43 : $notifySent.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $event = this.getEvent();
      result = result * 59 + ($event == null ? 43 : $event.hashCode());
      Object $filterType = this.getFilterType();
      result = result * 59 + ($filterType == null ? 43 : $filterType.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $sessionId = this.getSessionId();
      result = result * 59 + ($sessionId == null ? 43 : $sessionId.hashCode());
      Object $clientName = this.getClientName();
      result = result * 59 + ($clientName == null ? 43 : $clientName.hashCode());
      Object $deviceName = this.getDeviceName();
      result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
      Object $deviceId = this.getDeviceId();
      result = result * 59 + ($deviceId == null ? 43 : $deviceId.hashCode());
      Object $applicationVersion = this.getApplicationVersion();
      result = result * 59 + ($applicationVersion == null ? 43 : $applicationVersion.hashCode());
      Object $remoteEndpoint = this.getRemoteEndpoint();
      result = result * 59 + ($remoteEndpoint == null ? 43 : $remoteEndpoint.hashCode());
      Object $resolvedIp = this.getResolvedIp();
      result = result * 59 + ($resolvedIp == null ? 43 : $resolvedIp.hashCode());
      Object $country = this.getCountry();
      result = result * 59 + ($country == null ? 43 : $country.hashCode());
      Object $province = this.getProvince();
      result = result * 59 + ($province == null ? 43 : $province.hashCode());
      Object $city = this.getCity();
      result = result * 59 + ($city == null ? 43 : $city.hashCode());
      Object $itemId = this.getItemId();
      result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
      Object $itemName = this.getItemName();
      result = result * 59 + ($itemName == null ? 43 : $itemName.hashCode());
      Object $itemType = this.getItemType();
      result = result * 59 + ($itemType == null ? 43 : $itemType.hashCode());
      Object $matchedPattern = this.getMatchedPattern();
      result = result * 59 + ($matchedPattern == null ? 43 : $matchedPattern.hashCode());
      Object $triggerTime = this.getTriggerTime();
      result = result * 59 + ($triggerTime == null ? 43 : $triggerTime.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyClientFilterRecordResponse(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", serverName="
         + this.getServerName()
         + ", event="
         + this.getEvent()
         + ", filterType="
         + this.getFilterType()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", sessionId="
         + this.getSessionId()
         + ", clientName="
         + this.getClientName()
         + ", deviceName="
         + this.getDeviceName()
         + ", deviceId="
         + this.getDeviceId()
         + ", applicationVersion="
         + this.getApplicationVersion()
         + ", remoteEndpoint="
         + this.getRemoteEndpoint()
         + ", resolvedIp="
         + this.getResolvedIp()
         + ", country="
         + this.getCountry()
         + ", province="
         + this.getProvince()
         + ", city="
         + this.getCity()
         + ", itemId="
         + this.getItemId()
         + ", itemName="
         + this.getItemName()
         + ", itemType="
         + this.getItemType()
         + ", matchedPattern="
         + this.getMatchedPattern()
         + ", usingDefaultPatterns="
         + this.getUsingDefaultPatterns()
         + ", stopSuccess="
         + this.getStopSuccess()
         + ", messageSuccess="
         + this.getMessageSuccess()
         + ", blockUserEnabled="
         + this.getBlockUserEnabled()
         + ", blockUserSuccess="
         + this.getBlockUserSuccess()
         + ", notifySent="
         + this.getNotifySent()
         + ", triggerTime="
         + this.getTriggerTime()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
