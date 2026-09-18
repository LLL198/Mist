package com.una.embyhub.model.dto.response.embyuser;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class LoginMultipleServerResponse implements Serializable {
   private List<LoginMultipleServerResponse.ServerOption> servers;

   @Generated
   public List<LoginMultipleServerResponse.ServerOption> getServers() {
      return this.servers;
   }

   @Generated
   public void setServers(final List<LoginMultipleServerResponse.ServerOption> servers) {
      this.servers = servers;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LoginMultipleServerResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$servers = this.getServers();
         Object other$servers = other.getServers();
         return this$servers == null ? other$servers == null : this$servers.equals(other$servers);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof LoginMultipleServerResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $servers = this.getServers();
      return result * 59 + ($servers == null ? 43 : $servers.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "LoginMultipleServerResponse(servers=" + this.getServers() + ")";
   }

   @Generated
   public LoginMultipleServerResponse() {
   }

   @Generated
   public LoginMultipleServerResponse(final List<LoginMultipleServerResponse.ServerOption> servers) {
      this.servers = servers;
   }

   public static class ServerOption implements Serializable {
      private Long embyInfoId;
      private String serverName;

      @Generated
      public Long getEmbyInfoId() {
         return this.embyInfoId;
      }

      @Generated
      public String getServerName() {
         return this.serverName;
      }

      @Generated
      public void setEmbyInfoId(final Long embyInfoId) {
         this.embyInfoId = embyInfoId;
      }

      @Generated
      public void setServerName(final String serverName) {
         this.serverName = serverName;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof LoginMultipleServerResponse.ServerOption other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$serverName = this.getServerName();
               Object other$serverName = other.getServerName();
               return this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName);
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof LoginMultipleServerResponse.ServerOption;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $embyInfoId = this.getEmbyInfoId();
         result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
         Object $serverName = this.getServerName();
         return result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "LoginMultipleServerResponse.ServerOption(embyInfoId=" + this.getEmbyInfoId() + ", serverName=" + this.getServerName() + ")";
      }

      @Generated
      public ServerOption() {
      }

      @Generated
      public ServerOption(final Long embyInfoId, final String serverName) {
         this.embyInfoId = embyInfoId;
         this.serverName = serverName;
      }
   }
}
