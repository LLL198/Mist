package com.una.embyhub.model.dto.response.embybossmigration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;

public class EmbyBossMigrationResultResponse implements Serializable {
   private Boolean dryRun;
   private String productName;
   private String productVersion;
   private String catalog;
   private Long embyInfoId;
   private Long chatId;
   private Long sourceEmbyRows = 0L;
   private Long sourceEmby2Rows = 0L;
   private Long sourceMigratableUsers = 0L;
   private Long skippedNoAccountRows = 0L;
   private Long embyUserInserted = 0L;
   private Long embyUserUpdated = 0L;
   private Long pointsBotUserInserted = 0L;
   private Long pointsBotUserUpdated = 0L;
   private Long userPointsInserted = 0L;
   private Long userPointsUpdated = 0L;
   private Long oauthBindingInserted = 0L;
   private Long oauthBindingUpdated = 0L;
   private Long levelConfigInserted = 0L;
   private Long levelConfigUpdated = 0L;
   private Long pointLedgerInserted = 0L;
   private Long embyInfoConfigUpdated = 0L;
   private Long hostLineInserted = 0L;
   private Long hostLineUpdated = 0L;
   private Long pointsBotConfigUpdated = 0L;
   private Map<String, Long> levelCounts = new LinkedHashMap<>();
   private List<String> warnings = new ArrayList<>();
   private String message;

   public void incrementLevel(String code) {
      String key = code != null && !code.isBlank() ? code : "unknown";
      this.levelCounts.put(key, this.levelCounts.getOrDefault(key, 0L) + 1L);
   }

   public void addWarning(String warning) {
      if (warning != null && !warning.isBlank()) {
         this.warnings.add(warning);
      }
   }

   @Generated
   public Boolean getDryRun() {
      return this.dryRun;
   }

   @Generated
   public String getProductName() {
      return this.productName;
   }

   @Generated
   public String getProductVersion() {
      return this.productVersion;
   }

   @Generated
   public String getCatalog() {
      return this.catalog;
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
   public Long getSourceEmbyRows() {
      return this.sourceEmbyRows;
   }

   @Generated
   public Long getSourceEmby2Rows() {
      return this.sourceEmby2Rows;
   }

   @Generated
   public Long getSourceMigratableUsers() {
      return this.sourceMigratableUsers;
   }

   @Generated
   public Long getSkippedNoAccountRows() {
      return this.skippedNoAccountRows;
   }

   @Generated
   public Long getEmbyUserInserted() {
      return this.embyUserInserted;
   }

   @Generated
   public Long getEmbyUserUpdated() {
      return this.embyUserUpdated;
   }

   @Generated
   public Long getPointsBotUserInserted() {
      return this.pointsBotUserInserted;
   }

   @Generated
   public Long getPointsBotUserUpdated() {
      return this.pointsBotUserUpdated;
   }

   @Generated
   public Long getUserPointsInserted() {
      return this.userPointsInserted;
   }

   @Generated
   public Long getUserPointsUpdated() {
      return this.userPointsUpdated;
   }

   @Generated
   public Long getOauthBindingInserted() {
      return this.oauthBindingInserted;
   }

   @Generated
   public Long getOauthBindingUpdated() {
      return this.oauthBindingUpdated;
   }

   @Generated
   public Long getLevelConfigInserted() {
      return this.levelConfigInserted;
   }

   @Generated
   public Long getLevelConfigUpdated() {
      return this.levelConfigUpdated;
   }

   @Generated
   public Long getPointLedgerInserted() {
      return this.pointLedgerInserted;
   }

   @Generated
   public Long getEmbyInfoConfigUpdated() {
      return this.embyInfoConfigUpdated;
   }

   @Generated
   public Long getHostLineInserted() {
      return this.hostLineInserted;
   }

   @Generated
   public Long getHostLineUpdated() {
      return this.hostLineUpdated;
   }

   @Generated
   public Long getPointsBotConfigUpdated() {
      return this.pointsBotConfigUpdated;
   }

   @Generated
   public Map<String, Long> getLevelCounts() {
      return this.levelCounts;
   }

   @Generated
   public List<String> getWarnings() {
      return this.warnings;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public void setDryRun(final Boolean dryRun) {
      this.dryRun = dryRun;
   }

   @Generated
   public void setProductName(final String productName) {
      this.productName = productName;
   }

   @Generated
   public void setProductVersion(final String productVersion) {
      this.productVersion = productVersion;
   }

   @Generated
   public void setCatalog(final String catalog) {
      this.catalog = catalog;
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
   public void setSourceEmbyRows(final Long sourceEmbyRows) {
      this.sourceEmbyRows = sourceEmbyRows;
   }

   @Generated
   public void setSourceEmby2Rows(final Long sourceEmby2Rows) {
      this.sourceEmby2Rows = sourceEmby2Rows;
   }

   @Generated
   public void setSourceMigratableUsers(final Long sourceMigratableUsers) {
      this.sourceMigratableUsers = sourceMigratableUsers;
   }

   @Generated
   public void setSkippedNoAccountRows(final Long skippedNoAccountRows) {
      this.skippedNoAccountRows = skippedNoAccountRows;
   }

   @Generated
   public void setEmbyUserInserted(final Long embyUserInserted) {
      this.embyUserInserted = embyUserInserted;
   }

   @Generated
   public void setEmbyUserUpdated(final Long embyUserUpdated) {
      this.embyUserUpdated = embyUserUpdated;
   }

   @Generated
   public void setPointsBotUserInserted(final Long pointsBotUserInserted) {
      this.pointsBotUserInserted = pointsBotUserInserted;
   }

   @Generated
   public void setPointsBotUserUpdated(final Long pointsBotUserUpdated) {
      this.pointsBotUserUpdated = pointsBotUserUpdated;
   }

   @Generated
   public void setUserPointsInserted(final Long userPointsInserted) {
      this.userPointsInserted = userPointsInserted;
   }

   @Generated
   public void setUserPointsUpdated(final Long userPointsUpdated) {
      this.userPointsUpdated = userPointsUpdated;
   }

   @Generated
   public void setOauthBindingInserted(final Long oauthBindingInserted) {
      this.oauthBindingInserted = oauthBindingInserted;
   }

   @Generated
   public void setOauthBindingUpdated(final Long oauthBindingUpdated) {
      this.oauthBindingUpdated = oauthBindingUpdated;
   }

   @Generated
   public void setLevelConfigInserted(final Long levelConfigInserted) {
      this.levelConfigInserted = levelConfigInserted;
   }

   @Generated
   public void setLevelConfigUpdated(final Long levelConfigUpdated) {
      this.levelConfigUpdated = levelConfigUpdated;
   }

   @Generated
   public void setPointLedgerInserted(final Long pointLedgerInserted) {
      this.pointLedgerInserted = pointLedgerInserted;
   }

   @Generated
   public void setEmbyInfoConfigUpdated(final Long embyInfoConfigUpdated) {
      this.embyInfoConfigUpdated = embyInfoConfigUpdated;
   }

   @Generated
   public void setHostLineInserted(final Long hostLineInserted) {
      this.hostLineInserted = hostLineInserted;
   }

   @Generated
   public void setHostLineUpdated(final Long hostLineUpdated) {
      this.hostLineUpdated = hostLineUpdated;
   }

   @Generated
   public void setPointsBotConfigUpdated(final Long pointsBotConfigUpdated) {
      this.pointsBotConfigUpdated = pointsBotConfigUpdated;
   }

   @Generated
   public void setLevelCounts(final Map<String, Long> levelCounts) {
      this.levelCounts = levelCounts;
   }

   @Generated
   public void setWarnings(final List<String> warnings) {
      this.warnings = warnings;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyBossMigrationResultResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$dryRun = this.getDryRun();
         Object other$dryRun = other.getDryRun();
         if (this$dryRun == null ? other$dryRun == null : this$dryRun.equals(other$dryRun)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$chatId = this.getChatId();
               Object other$chatId = other.getChatId();
               if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
                  Object this$sourceEmbyRows = this.getSourceEmbyRows();
                  Object other$sourceEmbyRows = other.getSourceEmbyRows();
                  if (this$sourceEmbyRows == null ? other$sourceEmbyRows == null : this$sourceEmbyRows.equals(other$sourceEmbyRows)) {
                     Object this$sourceEmby2Rows = this.getSourceEmby2Rows();
                     Object other$sourceEmby2Rows = other.getSourceEmby2Rows();
                     if (this$sourceEmby2Rows == null ? other$sourceEmby2Rows == null : this$sourceEmby2Rows.equals(other$sourceEmby2Rows)) {
                        Object this$sourceMigratableUsers = this.getSourceMigratableUsers();
                        Object other$sourceMigratableUsers = other.getSourceMigratableUsers();
                        if (this$sourceMigratableUsers == null
                           ? other$sourceMigratableUsers == null
                           : this$sourceMigratableUsers.equals(other$sourceMigratableUsers)) {
                           Object this$skippedNoAccountRows = this.getSkippedNoAccountRows();
                           Object other$skippedNoAccountRows = other.getSkippedNoAccountRows();
                           if (this$skippedNoAccountRows == null
                              ? other$skippedNoAccountRows == null
                              : this$skippedNoAccountRows.equals(other$skippedNoAccountRows)) {
                              Object this$embyUserInserted = this.getEmbyUserInserted();
                              Object other$embyUserInserted = other.getEmbyUserInserted();
                              if (this$embyUserInserted == null ? other$embyUserInserted == null : this$embyUserInserted.equals(other$embyUserInserted)) {
                                 Object this$embyUserUpdated = this.getEmbyUserUpdated();
                                 Object other$embyUserUpdated = other.getEmbyUserUpdated();
                                 if (this$embyUserUpdated == null ? other$embyUserUpdated == null : this$embyUserUpdated.equals(other$embyUserUpdated)) {
                                    Object this$pointsBotUserInserted = this.getPointsBotUserInserted();
                                    Object other$pointsBotUserInserted = other.getPointsBotUserInserted();
                                    if (this$pointsBotUserInserted == null
                                       ? other$pointsBotUserInserted == null
                                       : this$pointsBotUserInserted.equals(other$pointsBotUserInserted)) {
                                       Object this$pointsBotUserUpdated = this.getPointsBotUserUpdated();
                                       Object other$pointsBotUserUpdated = other.getPointsBotUserUpdated();
                                       if (this$pointsBotUserUpdated == null
                                          ? other$pointsBotUserUpdated == null
                                          : this$pointsBotUserUpdated.equals(other$pointsBotUserUpdated)) {
                                          Object this$userPointsInserted = this.getUserPointsInserted();
                                          Object other$userPointsInserted = other.getUserPointsInserted();
                                          if (this$userPointsInserted == null
                                             ? other$userPointsInserted == null
                                             : this$userPointsInserted.equals(other$userPointsInserted)) {
                                             Object this$userPointsUpdated = this.getUserPointsUpdated();
                                             Object other$userPointsUpdated = other.getUserPointsUpdated();
                                             if (this$userPointsUpdated == null
                                                ? other$userPointsUpdated == null
                                                : this$userPointsUpdated.equals(other$userPointsUpdated)) {
                                                Object this$oauthBindingInserted = this.getOauthBindingInserted();
                                                Object other$oauthBindingInserted = other.getOauthBindingInserted();
                                                if (this$oauthBindingInserted == null
                                                   ? other$oauthBindingInserted == null
                                                   : this$oauthBindingInserted.equals(other$oauthBindingInserted)) {
                                                   Object this$oauthBindingUpdated = this.getOauthBindingUpdated();
                                                   Object other$oauthBindingUpdated = other.getOauthBindingUpdated();
                                                   if (this$oauthBindingUpdated == null
                                                      ? other$oauthBindingUpdated == null
                                                      : this$oauthBindingUpdated.equals(other$oauthBindingUpdated)) {
                                                      Object this$levelConfigInserted = this.getLevelConfigInserted();
                                                      Object other$levelConfigInserted = other.getLevelConfigInserted();
                                                      if (this$levelConfigInserted == null
                                                         ? other$levelConfigInserted == null
                                                         : this$levelConfigInserted.equals(other$levelConfigInserted)) {
                                                         Object this$levelConfigUpdated = this.getLevelConfigUpdated();
                                                         Object other$levelConfigUpdated = other.getLevelConfigUpdated();
                                                         if (this$levelConfigUpdated == null
                                                            ? other$levelConfigUpdated == null
                                                            : this$levelConfigUpdated.equals(other$levelConfigUpdated)) {
                                                            Object this$pointLedgerInserted = this.getPointLedgerInserted();
                                                            Object other$pointLedgerInserted = other.getPointLedgerInserted();
                                                            if (this$pointLedgerInserted == null
                                                               ? other$pointLedgerInserted == null
                                                               : this$pointLedgerInserted.equals(other$pointLedgerInserted)) {
                                                               Object this$embyInfoConfigUpdated = this.getEmbyInfoConfigUpdated();
                                                               Object other$embyInfoConfigUpdated = other.getEmbyInfoConfigUpdated();
                                                               if (this$embyInfoConfigUpdated == null
                                                                  ? other$embyInfoConfigUpdated == null
                                                                  : this$embyInfoConfigUpdated.equals(other$embyInfoConfigUpdated)) {
                                                                  Object this$hostLineInserted = this.getHostLineInserted();
                                                                  Object other$hostLineInserted = other.getHostLineInserted();
                                                                  if (this$hostLineInserted == null
                                                                     ? other$hostLineInserted == null
                                                                     : this$hostLineInserted.equals(other$hostLineInserted)) {
                                                                     Object this$hostLineUpdated = this.getHostLineUpdated();
                                                                     Object other$hostLineUpdated = other.getHostLineUpdated();
                                                                     if (this$hostLineUpdated == null
                                                                        ? other$hostLineUpdated == null
                                                                        : this$hostLineUpdated.equals(other$hostLineUpdated)) {
                                                                        Object this$pointsBotConfigUpdated = this.getPointsBotConfigUpdated();
                                                                        Object other$pointsBotConfigUpdated = other.getPointsBotConfigUpdated();
                                                                        if (this$pointsBotConfigUpdated == null
                                                                           ? other$pointsBotConfigUpdated == null
                                                                           : this$pointsBotConfigUpdated.equals(other$pointsBotConfigUpdated)) {
                                                                           Object this$productName = this.getProductName();
                                                                           Object other$productName = other.getProductName();
                                                                           if (this$productName == null
                                                                              ? other$productName == null
                                                                              : this$productName.equals(other$productName)) {
                                                                              Object this$productVersion = this.getProductVersion();
                                                                              Object other$productVersion = other.getProductVersion();
                                                                              if (this$productVersion == null
                                                                                 ? other$productVersion == null
                                                                                 : this$productVersion.equals(other$productVersion)) {
                                                                                 Object this$catalog = this.getCatalog();
                                                                                 Object other$catalog = other.getCatalog();
                                                                                 if (this$catalog == null
                                                                                    ? other$catalog == null
                                                                                    : this$catalog.equals(other$catalog)) {
                                                                                    Object this$levelCounts = this.getLevelCounts();
                                                                                    Object other$levelCounts = other.getLevelCounts();
                                                                                    if (this$levelCounts == null
                                                                                       ? other$levelCounts == null
                                                                                       : this$levelCounts.equals(other$levelCounts)) {
                                                                                       Object this$warnings = this.getWarnings();
                                                                                       Object other$warnings = other.getWarnings();
                                                                                       if (this$warnings == null
                                                                                          ? other$warnings == null
                                                                                          : this$warnings.equals(other$warnings)) {
                                                                                          Object this$message = this.getMessage();
                                                                                          Object other$message = other.getMessage();
                                                                                          return this$message == null
                                                                                             ? other$message == null
                                                                                             : this$message.equals(other$message);
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
      return other instanceof EmbyBossMigrationResultResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $dryRun = this.getDryRun();
      result = result * 59 + ($dryRun == null ? 43 : $dryRun.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $sourceEmbyRows = this.getSourceEmbyRows();
      result = result * 59 + ($sourceEmbyRows == null ? 43 : $sourceEmbyRows.hashCode());
      Object $sourceEmby2Rows = this.getSourceEmby2Rows();
      result = result * 59 + ($sourceEmby2Rows == null ? 43 : $sourceEmby2Rows.hashCode());
      Object $sourceMigratableUsers = this.getSourceMigratableUsers();
      result = result * 59 + ($sourceMigratableUsers == null ? 43 : $sourceMigratableUsers.hashCode());
      Object $skippedNoAccountRows = this.getSkippedNoAccountRows();
      result = result * 59 + ($skippedNoAccountRows == null ? 43 : $skippedNoAccountRows.hashCode());
      Object $embyUserInserted = this.getEmbyUserInserted();
      result = result * 59 + ($embyUserInserted == null ? 43 : $embyUserInserted.hashCode());
      Object $embyUserUpdated = this.getEmbyUserUpdated();
      result = result * 59 + ($embyUserUpdated == null ? 43 : $embyUserUpdated.hashCode());
      Object $pointsBotUserInserted = this.getPointsBotUserInserted();
      result = result * 59 + ($pointsBotUserInserted == null ? 43 : $pointsBotUserInserted.hashCode());
      Object $pointsBotUserUpdated = this.getPointsBotUserUpdated();
      result = result * 59 + ($pointsBotUserUpdated == null ? 43 : $pointsBotUserUpdated.hashCode());
      Object $userPointsInserted = this.getUserPointsInserted();
      result = result * 59 + ($userPointsInserted == null ? 43 : $userPointsInserted.hashCode());
      Object $userPointsUpdated = this.getUserPointsUpdated();
      result = result * 59 + ($userPointsUpdated == null ? 43 : $userPointsUpdated.hashCode());
      Object $oauthBindingInserted = this.getOauthBindingInserted();
      result = result * 59 + ($oauthBindingInserted == null ? 43 : $oauthBindingInserted.hashCode());
      Object $oauthBindingUpdated = this.getOauthBindingUpdated();
      result = result * 59 + ($oauthBindingUpdated == null ? 43 : $oauthBindingUpdated.hashCode());
      Object $levelConfigInserted = this.getLevelConfigInserted();
      result = result * 59 + ($levelConfigInserted == null ? 43 : $levelConfigInserted.hashCode());
      Object $levelConfigUpdated = this.getLevelConfigUpdated();
      result = result * 59 + ($levelConfigUpdated == null ? 43 : $levelConfigUpdated.hashCode());
      Object $pointLedgerInserted = this.getPointLedgerInserted();
      result = result * 59 + ($pointLedgerInserted == null ? 43 : $pointLedgerInserted.hashCode());
      Object $embyInfoConfigUpdated = this.getEmbyInfoConfigUpdated();
      result = result * 59 + ($embyInfoConfigUpdated == null ? 43 : $embyInfoConfigUpdated.hashCode());
      Object $hostLineInserted = this.getHostLineInserted();
      result = result * 59 + ($hostLineInserted == null ? 43 : $hostLineInserted.hashCode());
      Object $hostLineUpdated = this.getHostLineUpdated();
      result = result * 59 + ($hostLineUpdated == null ? 43 : $hostLineUpdated.hashCode());
      Object $pointsBotConfigUpdated = this.getPointsBotConfigUpdated();
      result = result * 59 + ($pointsBotConfigUpdated == null ? 43 : $pointsBotConfigUpdated.hashCode());
      Object $productName = this.getProductName();
      result = result * 59 + ($productName == null ? 43 : $productName.hashCode());
      Object $productVersion = this.getProductVersion();
      result = result * 59 + ($productVersion == null ? 43 : $productVersion.hashCode());
      Object $catalog = this.getCatalog();
      result = result * 59 + ($catalog == null ? 43 : $catalog.hashCode());
      Object $levelCounts = this.getLevelCounts();
      result = result * 59 + ($levelCounts == null ? 43 : $levelCounts.hashCode());
      Object $warnings = this.getWarnings();
      result = result * 59 + ($warnings == null ? 43 : $warnings.hashCode());
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyBossMigrationResultResponse(dryRun="
         + this.getDryRun()
         + ", productName="
         + this.getProductName()
         + ", productVersion="
         + this.getProductVersion()
         + ", catalog="
         + this.getCatalog()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", chatId="
         + this.getChatId()
         + ", sourceEmbyRows="
         + this.getSourceEmbyRows()
         + ", sourceEmby2Rows="
         + this.getSourceEmby2Rows()
         + ", sourceMigratableUsers="
         + this.getSourceMigratableUsers()
         + ", skippedNoAccountRows="
         + this.getSkippedNoAccountRows()
         + ", embyUserInserted="
         + this.getEmbyUserInserted()
         + ", embyUserUpdated="
         + this.getEmbyUserUpdated()
         + ", pointsBotUserInserted="
         + this.getPointsBotUserInserted()
         + ", pointsBotUserUpdated="
         + this.getPointsBotUserUpdated()
         + ", userPointsInserted="
         + this.getUserPointsInserted()
         + ", userPointsUpdated="
         + this.getUserPointsUpdated()
         + ", oauthBindingInserted="
         + this.getOauthBindingInserted()
         + ", oauthBindingUpdated="
         + this.getOauthBindingUpdated()
         + ", levelConfigInserted="
         + this.getLevelConfigInserted()
         + ", levelConfigUpdated="
         + this.getLevelConfigUpdated()
         + ", pointLedgerInserted="
         + this.getPointLedgerInserted()
         + ", embyInfoConfigUpdated="
         + this.getEmbyInfoConfigUpdated()
         + ", hostLineInserted="
         + this.getHostLineInserted()
         + ", hostLineUpdated="
         + this.getHostLineUpdated()
         + ", pointsBotConfigUpdated="
         + this.getPointsBotConfigUpdated()
         + ", levelCounts="
         + this.getLevelCounts()
         + ", warnings="
         + this.getWarnings()
         + ", message="
         + this.getMessage()
         + ")";
   }
}
