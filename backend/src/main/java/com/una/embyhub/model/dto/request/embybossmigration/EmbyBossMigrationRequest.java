package com.una.embyhub.model.dto.request.embybossmigration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class EmbyBossMigrationRequest implements Serializable {
   private String hostPort;
   private String databaseName;
   private String jdbcUrl;
   @NotBlank(
      message = "源库用户名不能为空"
   )
   private String username;
   private String password;
   @Size(
      max = 65535,
      message = "config.json 内容过长"
   )
   private String configJson;
   @NotNull(
      message = "Emby服务器ID不能为空"
   )
   private Long embyInfoId;
   private Long chatId;
   private Boolean dryRun = true;
   private Boolean overwriteExisting = true;
   private Boolean syncEmbyUsers = true;
   private Boolean syncPointsBotUsers = true;
   private Boolean syncUserPoints = true;
   private Boolean syncOauthBindings = true;
   private Boolean syncLevelConfigs = true;
   private Boolean syncEmbyServerConfig = true;
   private Boolean syncHostLines = true;
   private Boolean syncTelegramConfig = true;
   private Boolean syncPointLedger = true;
   private String levelNameA;
   private String levelNameB;
   private String levelNameC;
   private String levelNameD;
   private String confirmation;

   @Generated
   public String getHostPort() {
      return this.hostPort;
   }

   @Generated
   public String getDatabaseName() {
      return this.databaseName;
   }

   @Generated
   public String getJdbcUrl() {
      return this.jdbcUrl;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public String getConfigJson() {
      return this.configJson;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Boolean getDryRun() {
      return this.dryRun;
   }

   @Generated
   public Boolean getOverwriteExisting() {
      return this.overwriteExisting;
   }

   @Generated
   public Boolean getSyncEmbyUsers() {
      return this.syncEmbyUsers;
   }

   @Generated
   public Boolean getSyncPointsBotUsers() {
      return this.syncPointsBotUsers;
   }

   @Generated
   public Boolean getSyncUserPoints() {
      return this.syncUserPoints;
   }

   @Generated
   public Boolean getSyncOauthBindings() {
      return this.syncOauthBindings;
   }

   @Generated
   public Boolean getSyncLevelConfigs() {
      return this.syncLevelConfigs;
   }

   @Generated
   public Boolean getSyncEmbyServerConfig() {
      return this.syncEmbyServerConfig;
   }

   @Generated
   public Boolean getSyncHostLines() {
      return this.syncHostLines;
   }

   @Generated
   public Boolean getSyncTelegramConfig() {
      return this.syncTelegramConfig;
   }

   @Generated
   public Boolean getSyncPointLedger() {
      return this.syncPointLedger;
   }

   @Generated
   public String getLevelNameA() {
      return this.levelNameA;
   }

   @Generated
   public String getLevelNameB() {
      return this.levelNameB;
   }

   @Generated
   public String getLevelNameC() {
      return this.levelNameC;
   }

   @Generated
   public String getLevelNameD() {
      return this.levelNameD;
   }

   @Generated
   public String getConfirmation() {
      return this.confirmation;
   }

   @Generated
   public void setHostPort(final String hostPort) {
      this.hostPort = hostPort;
   }

   @Generated
   public void setDatabaseName(final String databaseName) {
      this.databaseName = databaseName;
   }

   @Generated
   public void setJdbcUrl(final String jdbcUrl) {
      this.jdbcUrl = jdbcUrl;
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
   public void setConfigJson(final String configJson) {
      this.configJson = configJson;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setDryRun(final Boolean dryRun) {
      this.dryRun = dryRun;
   }

   @Generated
   public void setOverwriteExisting(final Boolean overwriteExisting) {
      this.overwriteExisting = overwriteExisting;
   }

   @Generated
   public void setSyncEmbyUsers(final Boolean syncEmbyUsers) {
      this.syncEmbyUsers = syncEmbyUsers;
   }

   @Generated
   public void setSyncPointsBotUsers(final Boolean syncPointsBotUsers) {
      this.syncPointsBotUsers = syncPointsBotUsers;
   }

   @Generated
   public void setSyncUserPoints(final Boolean syncUserPoints) {
      this.syncUserPoints = syncUserPoints;
   }

   @Generated
   public void setSyncOauthBindings(final Boolean syncOauthBindings) {
      this.syncOauthBindings = syncOauthBindings;
   }

   @Generated
   public void setSyncLevelConfigs(final Boolean syncLevelConfigs) {
      this.syncLevelConfigs = syncLevelConfigs;
   }

   @Generated
   public void setSyncEmbyServerConfig(final Boolean syncEmbyServerConfig) {
      this.syncEmbyServerConfig = syncEmbyServerConfig;
   }

   @Generated
   public void setSyncHostLines(final Boolean syncHostLines) {
      this.syncHostLines = syncHostLines;
   }

   @Generated
   public void setSyncTelegramConfig(final Boolean syncTelegramConfig) {
      this.syncTelegramConfig = syncTelegramConfig;
   }

   @Generated
   public void setSyncPointLedger(final Boolean syncPointLedger) {
      this.syncPointLedger = syncPointLedger;
   }

   @Generated
   public void setLevelNameA(final String levelNameA) {
      this.levelNameA = levelNameA;
   }

   @Generated
   public void setLevelNameB(final String levelNameB) {
      this.levelNameB = levelNameB;
   }

   @Generated
   public void setLevelNameC(final String levelNameC) {
      this.levelNameC = levelNameC;
   }

   @Generated
   public void setLevelNameD(final String levelNameD) {
      this.levelNameD = levelNameD;
   }

   @Generated
   public void setConfirmation(final String confirmation) {
      this.confirmation = confirmation;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyBossMigrationRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$dryRun = this.getDryRun();
               Object other$dryRun = other.getDryRun();
               if (this$dryRun == null ? other$dryRun == null : this$dryRun.equals(other$dryRun)) {
                  Object this$overwriteExisting = this.getOverwriteExisting();
                  Object other$overwriteExisting = other.getOverwriteExisting();
                  if (this$overwriteExisting == null ? other$overwriteExisting == null : this$overwriteExisting.equals(other$overwriteExisting)) {
                     Object this$syncEmbyUsers = this.getSyncEmbyUsers();
                     Object other$syncEmbyUsers = other.getSyncEmbyUsers();
                     if (this$syncEmbyUsers == null ? other$syncEmbyUsers == null : this$syncEmbyUsers.equals(other$syncEmbyUsers)) {
                        Object this$syncPointsBotUsers = this.getSyncPointsBotUsers();
                        Object other$syncPointsBotUsers = other.getSyncPointsBotUsers();
                        if (this$syncPointsBotUsers == null ? other$syncPointsBotUsers == null : this$syncPointsBotUsers.equals(other$syncPointsBotUsers)) {
                           Object this$syncUserPoints = this.getSyncUserPoints();
                           Object other$syncUserPoints = other.getSyncUserPoints();
                           if (this$syncUserPoints == null ? other$syncUserPoints == null : this$syncUserPoints.equals(other$syncUserPoints)) {
                              Object this$syncOauthBindings = this.getSyncOauthBindings();
                              Object other$syncOauthBindings = other.getSyncOauthBindings();
                              if (this$syncOauthBindings == null ? other$syncOauthBindings == null : this$syncOauthBindings.equals(other$syncOauthBindings)) {
                                 Object this$syncLevelConfigs = this.getSyncLevelConfigs();
                                 Object other$syncLevelConfigs = other.getSyncLevelConfigs();
                                 if (this$syncLevelConfigs == null ? other$syncLevelConfigs == null : this$syncLevelConfigs.equals(other$syncLevelConfigs)) {
                                    Object this$syncEmbyServerConfig = this.getSyncEmbyServerConfig();
                                    Object other$syncEmbyServerConfig = other.getSyncEmbyServerConfig();
                                    if (this$syncEmbyServerConfig == null
                                       ? other$syncEmbyServerConfig == null
                                       : this$syncEmbyServerConfig.equals(other$syncEmbyServerConfig)) {
                                       Object this$syncHostLines = this.getSyncHostLines();
                                       Object other$syncHostLines = other.getSyncHostLines();
                                       if (this$syncHostLines == null ? other$syncHostLines == null : this$syncHostLines.equals(other$syncHostLines)) {
                                          Object this$syncTelegramConfig = this.getSyncTelegramConfig();
                                          Object other$syncTelegramConfig = other.getSyncTelegramConfig();
                                          if (this$syncTelegramConfig == null
                                             ? other$syncTelegramConfig == null
                                             : this$syncTelegramConfig.equals(other$syncTelegramConfig)) {
                                             Object this$syncPointLedger = this.getSyncPointLedger();
                                             Object other$syncPointLedger = other.getSyncPointLedger();
                                             if (this$syncPointLedger == null
                                                ? other$syncPointLedger == null
                                                : this$syncPointLedger.equals(other$syncPointLedger)) {
                                                Object this$hostPort = this.getHostPort();
                                                Object other$hostPort = other.getHostPort();
                                                if (this$hostPort == null ? other$hostPort == null : this$hostPort.equals(other$hostPort)) {
                                                   Object this$databaseName = this.getDatabaseName();
                                                   Object other$databaseName = other.getDatabaseName();
                                                   if (this$databaseName == null ? other$databaseName == null : this$databaseName.equals(other$databaseName)) {
                                                      Object this$jdbcUrl = this.getJdbcUrl();
                                                      Object other$jdbcUrl = other.getJdbcUrl();
                                                      if (this$jdbcUrl == null ? other$jdbcUrl == null : this$jdbcUrl.equals(other$jdbcUrl)) {
                                                         Object this$username = this.getUsername();
                                                         Object other$username = other.getUsername();
                                                         if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                                                            Object this$password = this.getPassword();
                                                            Object other$password = other.getPassword();
                                                            if (this$password == null ? other$password == null : this$password.equals(other$password)) {
                                                               Object this$configJson = this.getConfigJson();
                                                               Object other$configJson = other.getConfigJson();
                                                               if (this$configJson == null
                                                                  ? other$configJson == null
                                                                  : this$configJson.equals(other$configJson)) {
                                                                  Object this$levelNameA = this.getLevelNameA();
                                                                  Object other$levelNameA = other.getLevelNameA();
                                                                  if (this$levelNameA == null
                                                                     ? other$levelNameA == null
                                                                     : this$levelNameA.equals(other$levelNameA)) {
                                                                     Object this$levelNameB = this.getLevelNameB();
                                                                     Object other$levelNameB = other.getLevelNameB();
                                                                     if (this$levelNameB == null
                                                                        ? other$levelNameB == null
                                                                        : this$levelNameB.equals(other$levelNameB)) {
                                                                        Object this$levelNameC = this.getLevelNameC();
                                                                        Object other$levelNameC = other.getLevelNameC();
                                                                        if (this$levelNameC == null
                                                                           ? other$levelNameC == null
                                                                           : this$levelNameC.equals(other$levelNameC)) {
                                                                           Object this$levelNameD = this.getLevelNameD();
                                                                           Object other$levelNameD = other.getLevelNameD();
                                                                           if (this$levelNameD == null
                                                                              ? other$levelNameD == null
                                                                              : this$levelNameD.equals(other$levelNameD)) {
                                                                              Object this$confirmation = this.getConfirmation();
                                                                              Object other$confirmation = other.getConfirmation();
                                                                              return this$confirmation == null
                                                                                 ? other$confirmation == null
                                                                                 : this$confirmation.equals(other$confirmation);
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
      return other instanceof EmbyBossMigrationRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $dryRun = this.getDryRun();
      result = result * 59 + ($dryRun == null ? 43 : $dryRun.hashCode());
      Object $overwriteExisting = this.getOverwriteExisting();
      result = result * 59 + ($overwriteExisting == null ? 43 : $overwriteExisting.hashCode());
      Object $syncEmbyUsers = this.getSyncEmbyUsers();
      result = result * 59 + ($syncEmbyUsers == null ? 43 : $syncEmbyUsers.hashCode());
      Object $syncPointsBotUsers = this.getSyncPointsBotUsers();
      result = result * 59 + ($syncPointsBotUsers == null ? 43 : $syncPointsBotUsers.hashCode());
      Object $syncUserPoints = this.getSyncUserPoints();
      result = result * 59 + ($syncUserPoints == null ? 43 : $syncUserPoints.hashCode());
      Object $syncOauthBindings = this.getSyncOauthBindings();
      result = result * 59 + ($syncOauthBindings == null ? 43 : $syncOauthBindings.hashCode());
      Object $syncLevelConfigs = this.getSyncLevelConfigs();
      result = result * 59 + ($syncLevelConfigs == null ? 43 : $syncLevelConfigs.hashCode());
      Object $syncEmbyServerConfig = this.getSyncEmbyServerConfig();
      result = result * 59 + ($syncEmbyServerConfig == null ? 43 : $syncEmbyServerConfig.hashCode());
      Object $syncHostLines = this.getSyncHostLines();
      result = result * 59 + ($syncHostLines == null ? 43 : $syncHostLines.hashCode());
      Object $syncTelegramConfig = this.getSyncTelegramConfig();
      result = result * 59 + ($syncTelegramConfig == null ? 43 : $syncTelegramConfig.hashCode());
      Object $syncPointLedger = this.getSyncPointLedger();
      result = result * 59 + ($syncPointLedger == null ? 43 : $syncPointLedger.hashCode());
      Object $hostPort = this.getHostPort();
      result = result * 59 + ($hostPort == null ? 43 : $hostPort.hashCode());
      Object $databaseName = this.getDatabaseName();
      result = result * 59 + ($databaseName == null ? 43 : $databaseName.hashCode());
      Object $jdbcUrl = this.getJdbcUrl();
      result = result * 59 + ($jdbcUrl == null ? 43 : $jdbcUrl.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $password = this.getPassword();
      result = result * 59 + ($password == null ? 43 : $password.hashCode());
      Object $configJson = this.getConfigJson();
      result = result * 59 + ($configJson == null ? 43 : $configJson.hashCode());
      Object $levelNameA = this.getLevelNameA();
      result = result * 59 + ($levelNameA == null ? 43 : $levelNameA.hashCode());
      Object $levelNameB = this.getLevelNameB();
      result = result * 59 + ($levelNameB == null ? 43 : $levelNameB.hashCode());
      Object $levelNameC = this.getLevelNameC();
      result = result * 59 + ($levelNameC == null ? 43 : $levelNameC.hashCode());
      Object $levelNameD = this.getLevelNameD();
      result = result * 59 + ($levelNameD == null ? 43 : $levelNameD.hashCode());
      Object $confirmation = this.getConfirmation();
      return result * 59 + ($confirmation == null ? 43 : $confirmation.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyBossMigrationRequest(hostPort="
         + this.getHostPort()
         + ", databaseName="
         + this.getDatabaseName()
         + ", jdbcUrl="
         + this.getJdbcUrl()
         + ", username="
         + this.getUsername()
         + ", password="
         + this.getPassword()
         + ", configJson="
         + this.getConfigJson()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", chatId="
         + this.getChatId()
         + ", dryRun="
         + this.getDryRun()
         + ", overwriteExisting="
         + this.getOverwriteExisting()
         + ", syncEmbyUsers="
         + this.getSyncEmbyUsers()
         + ", syncPointsBotUsers="
         + this.getSyncPointsBotUsers()
         + ", syncUserPoints="
         + this.getSyncUserPoints()
         + ", syncOauthBindings="
         + this.getSyncOauthBindings()
         + ", syncLevelConfigs="
         + this.getSyncLevelConfigs()
         + ", syncEmbyServerConfig="
         + this.getSyncEmbyServerConfig()
         + ", syncHostLines="
         + this.getSyncHostLines()
         + ", syncTelegramConfig="
         + this.getSyncTelegramConfig()
         + ", syncPointLedger="
         + this.getSyncPointLedger()
         + ", levelNameA="
         + this.getLevelNameA()
         + ", levelNameB="
         + this.getLevelNameB()
         + ", levelNameC="
         + this.getLevelNameC()
         + ", levelNameD="
         + this.getLevelNameD()
         + ", confirmation="
         + this.getConfirmation()
         + ")";
   }
}
