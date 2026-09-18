package com.una.embyhub.model.dto.response.embyblockkeyword;

import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingUserOptionResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class EmbyClientFilterExclusionResponse {
   private Long embyInfoId;
   private String serverName;
   private List<String> excludedUserIds = new ArrayList<>();
   private List<PlaybackRankingUserOptionResponse> users = new ArrayList<>();

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public List<String> getExcludedUserIds() {
      return this.excludedUserIds;
   }

   @Generated
   public List<PlaybackRankingUserOptionResponse> getUsers() {
      return this.users;
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
   public void setExcludedUserIds(final List<String> excludedUserIds) {
      this.excludedUserIds = excludedUserIds;
   }

   @Generated
   public void setUsers(final List<PlaybackRankingUserOptionResponse> users) {
      this.users = users;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyClientFilterExclusionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$serverName = this.getServerName();
            Object other$serverName = other.getServerName();
            if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
               Object this$excludedUserIds = this.getExcludedUserIds();
               Object other$excludedUserIds = other.getExcludedUserIds();
               if (this$excludedUserIds == null ? other$excludedUserIds == null : this$excludedUserIds.equals(other$excludedUserIds)) {
                  Object this$users = this.getUsers();
                  Object other$users = other.getUsers();
                  return this$users == null ? other$users == null : this$users.equals(other$users);
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
      return other instanceof EmbyClientFilterExclusionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $excludedUserIds = this.getExcludedUserIds();
      result = result * 59 + ($excludedUserIds == null ? 43 : $excludedUserIds.hashCode());
      Object $users = this.getUsers();
      return result * 59 + ($users == null ? 43 : $users.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyClientFilterExclusionResponse(embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", excludedUserIds="
         + this.getExcludedUserIds()
         + ", users="
         + this.getUsers()
         + ")";
   }
}
