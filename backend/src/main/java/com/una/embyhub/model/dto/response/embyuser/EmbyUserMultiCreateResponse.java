package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class EmbyUserMultiCreateResponse implements Serializable {
   private String username;
   private String password;
   private List<EmbyUserMultiCreateResponse.ServerInfo> servers;

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public List<EmbyUserMultiCreateResponse.ServerInfo> getServers() {
      return this.servers;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setPassword(final String password) {
      this.password = password;
   }

   @Generated
   public void setServers(final List<EmbyUserMultiCreateResponse.ServerInfo> servers) {
      this.servers = servers;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserMultiCreateResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$username = this.getUsername();
         Object other$username = other.getUsername();
         if (this$username == null ? other$username == null : this$username.equals(other$username)) {
            Object this$password = this.getPassword();
            Object other$password = other.getPassword();
            if (this$password == null ? other$password == null : this$password.equals(other$password)) {
               Object this$servers = this.getServers();
               Object other$servers = other.getServers();
               return this$servers == null ? other$servers == null : this$servers.equals(other$servers);
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
      return other instanceof EmbyUserMultiCreateResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $password = this.getPassword();
      result = result * 59 + ($password == null ? 43 : $password.hashCode());
      Object $servers = this.getServers();
      return result * 59 + ($servers == null ? 43 : $servers.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserMultiCreateResponse(username=" + this.getUsername() + ", password=" + this.getPassword() + ", servers=" + this.getServers() + ")";
   }

   public static class ServerInfo implements Serializable {
      private String serverName;
      private String protocol;
      private String host;
      private Integer port;

      @Generated
      public String getServerName() {
         return this.serverName;
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
      public Integer getPort() {
         return this.port;
      }

      @Generated
      public void setServerName(final String serverName) {
         this.serverName = serverName;
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
      public void setPort(final Integer port) {
         this.port = port;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof EmbyUserMultiCreateResponse.ServerInfo other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$port = this.getPort();
            Object other$port = other.getPort();
            if (this$port == null ? other$port == null : this$port.equals(other$port)) {
               Object this$serverName = this.getServerName();
               Object other$serverName = other.getServerName();
               if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
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
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof EmbyUserMultiCreateResponse.ServerInfo;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $port = this.getPort();
         result = result * 59 + ($port == null ? 43 : $port.hashCode());
         Object $serverName = this.getServerName();
         result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
         Object $protocol = this.getProtocol();
         result = result * 59 + ($protocol == null ? 43 : $protocol.hashCode());
         Object $host = this.getHost();
         return result * 59 + ($host == null ? 43 : $host.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "EmbyUserMultiCreateResponse.ServerInfo(serverName="
            + this.getServerName()
            + ", protocol="
            + this.getProtocol()
            + ", host="
            + this.getHost()
            + ", port="
            + this.getPort()
            + ")";
      }

      @Generated
      public ServerInfo() {
      }

      @Generated
      public ServerInfo(final String serverName, final String protocol, final String host, final Integer port) {
         this.serverName = serverName;
         this.protocol = protocol;
         this.host = host;
         this.port = port;
      }
   }
}
