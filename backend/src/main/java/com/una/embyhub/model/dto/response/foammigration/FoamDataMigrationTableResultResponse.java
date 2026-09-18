package com.una.embyhub.model.dto.response.foammigration;

import java.io.Serializable;
import lombok.Generated;

public class FoamDataMigrationTableResultResponse implements Serializable {
   private String tableName;
   private String status;
   private Long sourceRows;
   private Long syncedRows;
   private String message;

   @Generated
   public String getTableName() {
      return this.tableName;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Long getSourceRows() {
      return this.sourceRows;
   }

   @Generated
   public Long getSyncedRows() {
      return this.syncedRows;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public void setTableName(final String tableName) {
      this.tableName = tableName;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setSourceRows(final Long sourceRows) {
      this.sourceRows = sourceRows;
   }

   @Generated
   public void setSyncedRows(final Long syncedRows) {
      this.syncedRows = syncedRows;
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
      } else if (!(o instanceof FoamDataMigrationTableResultResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$sourceRows = this.getSourceRows();
         Object other$sourceRows = other.getSourceRows();
         if (this$sourceRows == null ? other$sourceRows == null : this$sourceRows.equals(other$sourceRows)) {
            Object this$syncedRows = this.getSyncedRows();
            Object other$syncedRows = other.getSyncedRows();
            if (this$syncedRows == null ? other$syncedRows == null : this$syncedRows.equals(other$syncedRows)) {
               Object this$tableName = this.getTableName();
               Object other$tableName = other.getTableName();
               if (this$tableName == null ? other$tableName == null : this$tableName.equals(other$tableName)) {
                  Object this$status = this.getStatus();
                  Object other$status = other.getStatus();
                  if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                     Object this$message = this.getMessage();
                     Object other$message = other.getMessage();
                     return this$message == null ? other$message == null : this$message.equals(other$message);
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
      return other instanceof FoamDataMigrationTableResultResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $sourceRows = this.getSourceRows();
      result = result * 59 + ($sourceRows == null ? 43 : $sourceRows.hashCode());
      Object $syncedRows = this.getSyncedRows();
      result = result * 59 + ($syncedRows == null ? 43 : $syncedRows.hashCode());
      Object $tableName = this.getTableName();
      result = result * 59 + ($tableName == null ? 43 : $tableName.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDataMigrationTableResultResponse(tableName="
         + this.getTableName()
         + ", status="
         + this.getStatus()
         + ", sourceRows="
         + this.getSourceRows()
         + ", syncedRows="
         + this.getSyncedRows()
         + ", message="
         + this.getMessage()
         + ")";
   }
}
