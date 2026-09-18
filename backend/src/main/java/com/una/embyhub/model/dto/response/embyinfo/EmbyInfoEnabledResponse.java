package com.una.embyhub.model.dto.response.embyinfo;

import java.io.Serializable;
import lombok.Generated;

public class EmbyInfoEnabledResponse implements Serializable {
   private Long id;
   private Long embyInfoId;
   private String embyUrl;
   private String embyOpenUrl;
   private String serverName;
   private String embyServerId;
   private String protocol;
   private String host;
   private int port;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getEmbyUrl() {
      return this.embyUrl;
   }

   @Generated
   public String getEmbyOpenUrl() {
      return this.embyOpenUrl;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public String getEmbyServerId() {
      return this.embyServerId;
   }

   @Generated
   public String getProtocol() {
      return this.protocol;
   }

   @Generated
   public String getHost() {
      return this.host;
   }

   @Generated
   public int getPort() {
      return this.port;
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
   public void setEmbyUrl(final String embyUrl) {
      this.embyUrl = embyUrl;
   }

   @Generated
   public void setEmbyOpenUrl(final String embyOpenUrl) {
      this.embyOpenUrl = embyOpenUrl;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setEmbyServerId(final String embyServerId) {
      this.embyServerId = embyServerId;
   }

   @Generated
   public void setProtocol(final String protocol) {
      this.protocol = protocol;
   }

   @Generated
   public void setHost(final String host) {
      this.host = host;
   }

   @Generated
   public void setPort(final int port) {
      this.port = port;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyInfoEnabledResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getPort() != other.getPort()) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$embyUrl = this.getEmbyUrl();
               Object other$embyUrl = other.getEmbyUrl();
               if (this$embyUrl == null ? other$embyUrl == null : this$embyUrl.equals(other$embyUrl)) {
                  Object this$embyOpenUrl = this.getEmbyOpenUrl();
                  Object other$embyOpenUrl = other.getEmbyOpenUrl();
                  if (this$embyOpenUrl == null ? other$embyOpenUrl == null : this$embyOpenUrl.equals(other$embyOpenUrl)) {
                     Object this$serverName = this.getServerName();
                     Object other$serverName = other.getServerName();
                     if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                        Object this$embyServerId = this.getEmbyServerId();
                        Object other$embyServerId = other.getEmbyServerId();
                        if (this$embyServerId == null ? other$embyServerId == null : this$embyServerId.equals(other$embyServerId)) {
                           Object this$protocol = this.getProtocol();
                           Object other$protocol = other.getProtocol();
                           if (this$protocol == null ? other$protocol == null : this$protocol.equals(other$protocol)) {
                              Object this$host = this.getHost();
                              Object other$host = other.getHost();
                              return this$host == null ? other$host == null : this$host.equals(other$host);
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
      return other instanceof EmbyInfoEnabledResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getPort();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $embyUrl = this.getEmbyUrl();
      result = result * 59 + ($embyUrl == null ? 43 : $embyUrl.hashCode());
      Object $embyOpenUrl = this.getEmbyOpenUrl();
      result = result * 59 + ($embyOpenUrl == null ? 43 : $embyOpenUrl.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $protocol = this.getProtocol();
      result = result * 59 + ($protocol == null ? 43 : $protocol.hashCode());
      Object $host = this.getHost();
      return result * 59 + ($host == null ? 43 : $host.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyInfoEnabledResponse(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", embyUrl="
         + this.getEmbyUrl()
         + ", embyOpenUrl="
         + this.getEmbyOpenUrl()
         + ", serverName="
         + this.getServerName()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", protocol="
         + this.getProtocol()
         + ", host="
         + this.getHost()
         + ", port="
         + this.getPort()
         + ")";
   }
}
