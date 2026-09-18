package com.una.embyhub.model.dto.response.foammigration;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class FoamDataMigrationResultResponse implements Serializable {
   private Integer tableCount;
   private Integer syncedTableCount;
   private Integer skippedTableCount;
   private Integer failedTableCount;
   private Long totalSourceRows;
   private Long totalSyncedRows;
   private Long durationMs;
   private List<FoamDataMigrationTableResultResponse> tables;

   @Generated
   public Integer getTableCount() {
      return this.tableCount;
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
   public Long getTotalSourceRows() {
      return this.totalSourceRows;
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
   public List<FoamDataMigrationTableResultResponse> getTables() {
      return this.tables;
   }

   @Generated
   public void setTableCount(final Integer tableCount) {
      this.tableCount = tableCount;
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
   public void setTotalSourceRows(final Long totalSourceRows) {
      this.totalSourceRows = totalSourceRows;
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
   public void setTables(final List<FoamDataMigrationTableResultResponse> tables) {
      this.tables = tables;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDataMigrationResultResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tableCount = this.getTableCount();
         Object other$tableCount = other.getTableCount();
         if (this$tableCount == null ? other$tableCount == null : this$tableCount.equals(other$tableCount)) {
            Object this$syncedTableCount = this.getSyncedTableCount();
            Object other$syncedTableCount = other.getSyncedTableCount();
            if (this$syncedTableCount == null ? other$syncedTableCount == null : this$syncedTableCount.equals(other$syncedTableCount)) {
               Object this$skippedTableCount = this.getSkippedTableCount();
               Object other$skippedTableCount = other.getSkippedTableCount();
               if (this$skippedTableCount == null ? other$skippedTableCount == null : this$skippedTableCount.equals(other$skippedTableCount)) {
                  Object this$failedTableCount = this.getFailedTableCount();
                  Object other$failedTableCount = other.getFailedTableCount();
                  if (this$failedTableCount == null ? other$failedTableCount == null : this$failedTableCount.equals(other$failedTableCount)) {
                     Object this$totalSourceRows = this.getTotalSourceRows();
                     Object other$totalSourceRows = other.getTotalSourceRows();
                     if (this$totalSourceRows == null ? other$totalSourceRows == null : this$totalSourceRows.equals(other$totalSourceRows)) {
                        Object this$totalSyncedRows = this.getTotalSyncedRows();
                        Object other$totalSyncedRows = other.getTotalSyncedRows();
                        if (this$totalSyncedRows == null ? other$totalSyncedRows == null : this$totalSyncedRows.equals(other$totalSyncedRows)) {
                           Object this$durationMs = this.getDurationMs();
                           Object other$durationMs = other.getDurationMs();
                           if (this$durationMs == null ? other$durationMs == null : this$durationMs.equals(other$durationMs)) {
                              Object this$tables = this.getTables();
                              Object other$tables = other.getTables();
                              return this$tables == null ? other$tables == null : this$tables.equals(other$tables);
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
      return other instanceof FoamDataMigrationResultResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tableCount = this.getTableCount();
      result = result * 59 + ($tableCount == null ? 43 : $tableCount.hashCode());
      Object $syncedTableCount = this.getSyncedTableCount();
      result = result * 59 + ($syncedTableCount == null ? 43 : $syncedTableCount.hashCode());
      Object $skippedTableCount = this.getSkippedTableCount();
      result = result * 59 + ($skippedTableCount == null ? 43 : $skippedTableCount.hashCode());
      Object $failedTableCount = this.getFailedTableCount();
      result = result * 59 + ($failedTableCount == null ? 43 : $failedTableCount.hashCode());
      Object $totalSourceRows = this.getTotalSourceRows();
      result = result * 59 + ($totalSourceRows == null ? 43 : $totalSourceRows.hashCode());
      Object $totalSyncedRows = this.getTotalSyncedRows();
      result = result * 59 + ($totalSyncedRows == null ? 43 : $totalSyncedRows.hashCode());
      Object $durationMs = this.getDurationMs();
      result = result * 59 + ($durationMs == null ? 43 : $durationMs.hashCode());
      Object $tables = this.getTables();
      return result * 59 + ($tables == null ? 43 : $tables.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDataMigrationResultResponse(tableCount="
         + this.getTableCount()
         + ", syncedTableCount="
         + this.getSyncedTableCount()
         + ", skippedTableCount="
         + this.getSkippedTableCount()
         + ", failedTableCount="
         + this.getFailedTableCount()
         + ", totalSourceRows="
         + this.getTotalSourceRows()
         + ", totalSyncedRows="
         + this.getTotalSyncedRows()
         + ", durationMs="
         + this.getDurationMs()
         + ", tables="
         + this.getTables()
         + ")";
   }
}
