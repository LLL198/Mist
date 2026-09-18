package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class NowPlayingGroupedResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long serverId;
   private String serverName;
   private List<SessionSessionInfoResponse> sessions = new ArrayList<>();

   @Generated
   public Long getServerId() {
      return this.serverId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public List<SessionSessionInfoResponse> getSessions() {
      return this.sessions;
   }

   @Generated
   public NowPlayingGroupedResponse setServerId(final Long serverId) {
      this.serverId = serverId;
      return this;
   }

   @Generated
   public NowPlayingGroupedResponse setServerName(final String serverName) {
      this.serverName = serverName;
      return this;
   }

   @Generated
   public NowPlayingGroupedResponse setSessions(final List<SessionSessionInfoResponse> sessions) {
      this.sessions = sessions;
      return this;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NowPlayingGroupedResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$serverId = this.getServerId();
         Object other$serverId = other.getServerId();
         if (this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId)) {
            Object this$serverName = this.getServerName();
            Object other$serverName = other.getServerName();
            if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
               Object this$sessions = this.getSessions();
               Object other$sessions = other.getSessions();
               return this$sessions == null ? other$sessions == null : this$sessions.equals(other$sessions);
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
      return other instanceof NowPlayingGroupedResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $serverId = this.getServerId();
      result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $sessions = this.getSessions();
      return result * 59 + ($sessions == null ? 43 : $sessions.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "NowPlayingGroupedResponse(serverId=" + this.getServerId() + ", serverName=" + this.getServerName() + ", sessions=" + this.getSessions() + ")";
   }
}
