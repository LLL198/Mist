package com.una.embyhub.model.dto.response.foammigration;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class FoamDataMigrationPlanResponse implements Serializable {
   private String scope;
   private Integer tableCount;
   private List<String> tables;

   @Generated
   public String getScope() {
      return this.scope;
   }

   @Generated
   public Integer getTableCount() {
      return this.tableCount;
   }

   @Generated
   public List<String> getTables() {
      return this.tables;
   }

   @Generated
   public void setScope(final String scope) {
      this.scope = scope;
   }

   @Generated
   public void setTableCount(final Integer tableCount) {
      this.tableCount = tableCount;
   }

   @Generated
   public void setTables(final List<String> tables) {
      this.tables = tables;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDataMigrationPlanResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tableCount = this.getTableCount();
         Object other$tableCount = other.getTableCount();
         if (this$tableCount == null ? other$tableCount == null : this$tableCount.equals(other$tableCount)) {
            Object this$scope = this.getScope();
            Object other$scope = other.getScope();
            if (this$scope == null ? other$scope == null : this$scope.equals(other$scope)) {
               Object this$tables = this.getTables();
               Object other$tables = other.getTables();
               return this$tables == null ? other$tables == null : this$tables.equals(other$tables);
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
      return other instanceof FoamDataMigrationPlanResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tableCount = this.getTableCount();
      result = result * 59 + ($tableCount == null ? 43 : $tableCount.hashCode());
      Object $scope = this.getScope();
      result = result * 59 + ($scope == null ? 43 : $scope.hashCode());
      Object $tables = this.getTables();
      return result * 59 + ($tables == null ? 43 : $tables.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDataMigrationPlanResponse(scope=" + this.getScope() + ", tableCount=" + this.getTableCount() + ", tables=" + this.getTables() + ")";
   }
}
