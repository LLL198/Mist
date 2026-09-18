package com.una.embyhub.model.dto.response.foammigration;

import java.io.Serializable;
import lombok.Generated;

public class FoamDataMigrationProgressResponse implements Serializable {
   private String jobId;
   private String status;
   private String stage;
   private Integer percent;
   private Integer totalTables;
   private Integer processedTables;
   private Integer currentTableIndex;
   private Integer syncedTableCount;
   private Integer skippedTableCount;
   private Integer failedTableCount;
   private String currentTable;
   private Long currentTableSourceRows;
   private Long currentTableSyncedRows;
   private Long totalSyncedRows;
   private Long durationMs;
   private String message;
   private FoamDataMigrationResultResponse result;

   @Generated
   public String getJobId() {
      return this.jobId;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public String getStage() {
      return this.stage;
   }

   @Generated
   public Integer getPercent() {
      return this.percent;
   }

   @Generated
   public Integer getTotalTables() {
      return this.totalTables;
   }

   @Generated
   public Integer getProcessedTables() {
      return this.processedTables;
   }

   @Generated
   public Integer getCurrentTableIndex() {
      return this.currentTableIndex;
   }

   @Generated
   public Integer getSyncedTableCount() {
      return this.syncedTableCount;
   }

   @Generated
   public Integer getSkippedTableCount() {
      return this.skippedTableCount;
   }

   @Generated
   public Integer getFailedTableCount() {
      return this.failedTableCount;
   }

   @Generated
   public String getCurrentTable() {
      return this.currentTable;
   }

   @Generated
   public Long getCurrentTableSourceRows() {
      return this.currentTableSourceRows;
   }

   @Generated
   public Long getCurrentTableSyncedRows() {
      return this.currentTableSyncedRows;
   }

   @Generated
   public Long getTotalSyncedRows() {
      return this.totalSyncedRows;
   }

   @Generated
   public Long getDurationMs() {
      return this.durationMs;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public FoamDataMigrationResultResponse getResult() {
      return this.result;
   }

   @Generated
   public void setJobId(final String jobId) {
      this.jobId = jobId;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setStage(final String stage) {
      this.stage = stage;
   }

   @Generated
   public void setPercent(final Integer percent) {
      this.percent = percent;
   }

   @Generated
   public void setTotalTables(final Integer totalTables) {
      this.totalTables = totalTables;
   }

   @Generated
   public void setProcessedTables(final Integer processedTables) {
      this.processedTables = processedTables;
   }

   @Generated
   public void setCurrentTableIndex(final Integer currentTableIndex) {
      this.currentTableIndex = currentTableIndex;
   }

   @Generated
   public void setSyncedTableCount(final Integer syncedTableCount) {
      this.syncedTableCount = syncedTableCount;
   }

   @Generated
   public void setSkippedTableCount(final Integer skippedTableCount) {
      this.skippedTableCount = skippedTableCount;
   }

   @Generated
   public void setFailedTableCount(final Integer failedTableCount) {
      this.failedTableCount = failedTableCount;
   }

   @Generated
   public void setCurrentTable(final String currentTable) {
      this.currentTable = currentTable;
   }

   @Generated
   public void setCurrentTableSourceRows(final Long currentTableSourceRows) {
      this.currentTableSourceRows = currentTableSourceRows;
   }

   @Generated
   public void setCurrentTableSyncedRows(final Long currentTableSyncedRows) {
      this.currentTableSyncedRows = currentTableSyncedRows;
   }

   @Generated
   public void setTotalSyncedRows(final Long totalSyncedRows) {
      this.totalSyncedRows = totalSyncedRows;
   }

   @Generated
   public void setDurationMs(final Long durationMs) {
      this.durationMs = durationMs;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   public void setResult(final FoamDataMigrationResultResponse result) {
      this.result = result;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDataMigrationProgressResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$percent = this.getPercent();
         Object other$percent = other.getPercent();
         if (this$percent == null ? other$percent == null : this$percent.equals(other$percent)) {
            Object this$totalTables = this.getTotalTables();
            Object other$totalTables = other.getTotalTables();
            if (this$totalTables == null ? other$totalTables == null : this$totalTables.equals(other$totalTables)) {
               Object this$processedTables = this.getProcessedTables();
               Object other$processedTables = other.getProcessedTables();
               if (this$processedTables == null ? other$processedTables == null : this$processedTables.equals(other$processedTables)) {
                  Object this$currentTableIndex = this.getCurrentTableIndex();
                  Object other$currentTableIndex = other.getCurrentTableIndex();
                  if (this$currentTableIndex == null ? other$currentTableIndex == null : this$currentTableIndex.equals(other$currentTableIndex)) {
                     Object this$syncedTableCount = this.getSyncedTableCount();
                     Object other$syncedTableCount = other.getSyncedTableCount();
                     if (this$syncedTableCount == null ? other$syncedTableCount == null : this$syncedTableCount.equals(other$syncedTableCount)) {
                        Object this$skippedTableCount = this.getSkippedTableCount();
                        Object other$skippedTableCount = other.getSkippedTableCount();
                        if (this$skippedTableCount == null ? other$skippedTableCount == null : this$skippedTableCount.equals(other$skippedTableCount)) {
                           Object this$failedTableCount = this.getFailedTableCount();
                           Object other$failedTableCount = other.getFailedTableCount();
                           if (this$failedTableCount == null ? other$failedTableCount == null : this$failedTableCount.equals(other$failedTableCount)) {
                              Object this$currentTableSourceRows = this.getCurrentTableSourceRows();
                              Object other$currentTableSourceRows = other.getCurrentTableSourceRows();
                              if (this$currentTableSourceRows == null
                                 ? other$currentTableSourceRows == null
                                 : this$currentTableSourceRows.equals(other$currentTableSourceRows)) {
                                 Object this$currentTableSyncedRows = this.getCurrentTableSyncedRows();
                                 Object other$currentTableSyncedRows = other.getCurrentTableSyncedRows();
                                 if (this$currentTableSyncedRows == null
                                    ? other$currentTableSyncedRows == null
                                    : this$currentTableSyncedRows.equals(other$currentTableSyncedRows)) {
                                    Object this$totalSyncedRows = this.getTotalSyncedRows();
                                    Object other$totalSyncedRows = other.getTotalSyncedRows();
                                    if (this$totalSyncedRows == null ? other$totalSyncedRows == null : this$totalSyncedRows.equals(other$totalSyncedRows)) {
                                       Object this$durationMs = this.getDurationMs();
                                       Object other$durationMs = other.getDurationMs();
                                       if (this$durationMs == null ? other$durationMs == null : this$durationMs.equals(other$durationMs)) {
                                          Object this$jobId = this.getJobId();
                                          Object other$jobId = other.getJobId();
                                          if (this$jobId == null ? other$jobId == null : this$jobId.equals(other$jobId)) {
                                             Object this$status = this.getStatus();
                                             Object other$status = other.getStatus();
                                             if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                Object this$stage = this.getStage();
                                                Object other$stage = other.getStage();
                                                if (this$stage == null ? other$stage == null : this$stage.equals(other$stage)) {
                                                   Object this$currentTable = this.getCurrentTable();
                                                   Object other$currentTable = other.getCurrentTable();
                                                   if (this$currentTable == null ? other$currentTable == null : this$currentTable.equals(other$currentTable)) {
                                                      Object this$message = this.getMessage();
                                                      Object other$message = other.getMessage();
                                                      if (this$message == null ? other$message == null : this$message.equals(other$message)) {
                                                         Object this$result = this.getResult();
                                                         Object other$result = other.getResult();
                                                         return this$result == null ? other$result == null : this$result.equals(other$result);
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
      return other instanceof FoamDataMigrationProgressResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $percent = this.getPercent();
      result = result * 59 + ($percent == null ? 43 : $percent.hashCode());
      Object $totalTables = this.getTotalTables();
      result = result * 59 + ($totalTables == null ? 43 : $totalTables.hashCode());
      Object $processedTables = this.getProcessedTables();
      result = result * 59 + ($processedTables == null ? 43 : $processedTables.hashCode());
      Object $currentTableIndex = this.getCurrentTableIndex();
      result = result * 59 + ($currentTableIndex == null ? 43 : $currentTableIndex.hashCode());
      Object $syncedTableCount = this.getSyncedTableCount();
      result = result * 59 + ($syncedTableCount == null ? 43 : $syncedTableCount.hashCode());
      Object $skippedTableCount = this.getSkippedTableCount();
      result = result * 59 + ($skippedTableCount == null ? 43 : $skippedTableCount.hashCode());
      Object $failedTableCount = this.getFailedTableCount();
      result = result * 59 + ($failedTableCount == null ? 43 : $failedTableCount.hashCode());
      Object $currentTableSourceRows = this.getCurrentTableSourceRows();
      result = result * 59 + ($currentTableSourceRows == null ? 43 : $currentTableSourceRows.hashCode());
      Object $currentTableSyncedRows = this.getCurrentTableSyncedRows();
      result = result * 59 + ($currentTableSyncedRows == null ? 43 : $currentTableSyncedRows.hashCode());
      Object $totalSyncedRows = this.getTotalSyncedRows();
      result = result * 59 + ($totalSyncedRows == null ? 43 : $totalSyncedRows.hashCode());
      Object $durationMs = this.getDurationMs();
      result = result * 59 + ($durationMs == null ? 43 : $durationMs.hashCode());
      Object $jobId = this.getJobId();
      result = result * 59 + ($jobId == null ? 43 : $jobId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $stage = this.getStage();
      result = result * 59 + ($stage == null ? 43 : $stage.hashCode());
      Object $currentTable = this.getCurrentTable();
      result = result * 59 + ($currentTable == null ? 43 : $currentTable.hashCode());
      Object $message = this.getMessage();
      result = result * 59 + ($message == null ? 43 : $message.hashCode());
      Object $result = this.getResult();
      return result * 59 + ($result == null ? 43 : $result.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDataMigrationProgressResponse(jobId="
         + this.getJobId()
         + ", status="
         + this.getStatus()
         + ", stage="
         + this.getStage()
         + ", percent="
         + this.getPercent()
         + ", totalTables="
         + this.getTotalTables()
         + ", processedTables="
         + this.getProcessedTables()
         + ", currentTableIndex="
         + this.getCurrentTableIndex()
         + ", syncedTableCount="
         + this.getSyncedTableCount()
         + ", skippedTableCount="
         + this.getSkippedTableCount()
         + ", failedTableCount="
         + this.getFailedTableCount()
         + ", currentTable="
         + this.getCurrentTable()
         + ", currentTableSourceRows="
         + this.getCurrentTableSourceRows()
         + ", currentTableSyncedRows="
         + this.getCurrentTableSyncedRows()
         + ", totalSyncedRows="
         + this.getTotalSyncedRows()
         + ", durationMs="
         + this.getDurationMs()
         + ", message="
         + this.getMessage()
         + ", result="
         + this.getResult()
         + ")";
   }
}
