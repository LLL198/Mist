package com.una.embyhub.model.dto.response.scheduledtask;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;

public class PlaybackRankingConfigSummaryResponse {
   private Map<String, List<String>> excludedUserIdsByServer = new LinkedHashMap<>();

   @Generated
   public Map<String, List<String>> getExcludedUserIdsByServer() {
      return this.excludedUserIdsByServer;
   }

   @Generated
   public void setExcludedUserIdsByServer(final Map<String, List<String>> excludedUserIdsByServer) {
      this.excludedUserIdsByServer = excludedUserIdsByServer;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlaybackRankingConfigSummaryResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$excludedUserIdsByServer = this.getExcludedUserIdsByServer();
         Object other$excludedUserIdsByServer = other.getExcludedUserIdsByServer();
         return this$excludedUserIdsByServer == null
            ? other$excludedUserIdsByServer == null
            : this$excludedUserIdsByServer.equals(other$excludedUserIdsByServer);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PlaybackRankingConfigSummaryResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $excludedUserIdsByServer = this.getExcludedUserIdsByServer();
      return result * 59 + ($excludedUserIdsByServer == null ? 43 : $excludedUserIdsByServer.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PlaybackRankingConfigSummaryResponse(excludedUserIdsByServer=" + this.getExcludedUserIdsByServer() + ")";
   }
}
