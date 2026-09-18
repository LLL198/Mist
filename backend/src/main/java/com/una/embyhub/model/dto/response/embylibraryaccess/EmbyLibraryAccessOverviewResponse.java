package com.una.embyhub.model.dto.response.embylibraryaccess;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class EmbyLibraryAccessOverviewResponse {
   private Long embyInfoId;
   private String serverName;
   private Long userId;
   private String userName;
   private List<EmbyLibraryFolderResponse> folders = new ArrayList<>();
   private EmbyLibraryAccessConfigResponse globalConfig;
   private EmbyLibraryAccessConfigResponse userConfig;
   private EmbyLibraryAccessConfigResponse effectiveConfig;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public List<EmbyLibraryFolderResponse> getFolders() {
      return this.folders;
   }

   @Generated
   public EmbyLibraryAccessConfigResponse getGlobalConfig() {
      return this.globalConfig;
   }

   @Generated
   public EmbyLibraryAccessConfigResponse getUserConfig() {
      return this.userConfig;
   }

   @Generated
   public EmbyLibraryAccessConfigResponse getEffectiveConfig() {
      return this.effectiveConfig;
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
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
   }

   @Generated
   public void setFolders(final List<EmbyLibraryFolderResponse> folders) {
      this.folders = folders;
   }

   @Generated
   public void setGlobalConfig(final EmbyLibraryAccessConfigResponse globalConfig) {
      this.globalConfig = globalConfig;
   }

   @Generated
   public void setUserConfig(final EmbyLibraryAccessConfigResponse userConfig) {
      this.userConfig = userConfig;
   }

   @Generated
   public void setEffectiveConfig(final EmbyLibraryAccessConfigResponse effectiveConfig) {
      this.effectiveConfig = effectiveConfig;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyLibraryAccessOverviewResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$serverName = this.getServerName();
               Object other$serverName = other.getServerName();
               if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                  Object this$userName = this.getUserName();
                  Object other$userName = other.getUserName();
                  if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                     Object this$folders = this.getFolders();
                     Object other$folders = other.getFolders();
                     if (this$folders == null ? other$folders == null : this$folders.equals(other$folders)) {
                        Object this$globalConfig = this.getGlobalConfig();
                        Object other$globalConfig = other.getGlobalConfig();
                        if (this$globalConfig == null ? other$globalConfig == null : this$globalConfig.equals(other$globalConfig)) {
                           Object this$userConfig = this.getUserConfig();
                           Object other$userConfig = other.getUserConfig();
                           if (this$userConfig == null ? other$userConfig == null : this$userConfig.equals(other$userConfig)) {
                              Object this$effectiveConfig = this.getEffectiveConfig();
                              Object other$effectiveConfig = other.getEffectiveConfig();
                              return this$effectiveConfig == null ? other$effectiveConfig == null : this$effectiveConfig.equals(other$effectiveConfig);
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
      return other instanceof EmbyLibraryAccessOverviewResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $folders = this.getFolders();
      result = result * 59 + ($folders == null ? 43 : $folders.hashCode());
      Object $globalConfig = this.getGlobalConfig();
      result = result * 59 + ($globalConfig == null ? 43 : $globalConfig.hashCode());
      Object $userConfig = this.getUserConfig();
      result = result * 59 + ($userConfig == null ? 43 : $userConfig.hashCode());
      Object $effectiveConfig = this.getEffectiveConfig();
      return result * 59 + ($effectiveConfig == null ? 43 : $effectiveConfig.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessOverviewResponse(embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", userId="
         + this.getUserId()
         + ", userName="
         + this.getUserName()
         + ", folders="
         + this.getFolders()
         + ", globalConfig="
         + this.getGlobalConfig()
         + ", userConfig="
         + this.getUserConfig()
         + ", effectiveConfig="
         + this.getEffectiveConfig()
         + ")";
   }
}
