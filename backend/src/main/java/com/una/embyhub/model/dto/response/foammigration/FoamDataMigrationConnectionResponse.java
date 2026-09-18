package com.una.embyhub.model.dto.response.foammigration;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class FoamDataMigrationConnectionResponse implements Serializable {
   private String productName;
   private String productVersion;
   private String catalog;
   private Integer supportedTableCount;
   private List<String> availableTables;
   private List<String> missingTables;
   private Integer availableTableCount;
   private Integer missingTableCount;

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
   public Integer getSupportedTableCount() {
      return this.supportedTableCount;
   }

   @Generated
   public List<String> getAvailableTables() {
      return this.availableTables;
   }

   @Generated
   public List<String> getMissingTables() {
      return this.missingTables;
   }

   @Generated
   public Integer getAvailableTableCount() {
      return this.availableTableCount;
   }

   @Generated
   public Integer getMissingTableCount() {
      return this.missingTableCount;
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
   public void setSupportedTableCount(final Integer supportedTableCount) {
      this.supportedTableCount = supportedTableCount;
   }

   @Generated
   public void setAvailableTables(final List<String> availableTables) {
      this.availableTables = availableTables;
   }

   @Generated
   public void setMissingTables(final List<String> missingTables) {
      this.missingTables = missingTables;
   }

   @Generated
   public void setAvailableTableCount(final Integer availableTableCount) {
      this.availableTableCount = availableTableCount;
   }

   @Generated
   public void setMissingTableCount(final Integer missingTableCount) {
      this.missingTableCount = missingTableCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDataMigrationConnectionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$supportedTableCount = this.getSupportedTableCount();
         Object other$supportedTableCount = other.getSupportedTableCount();
         if (this$supportedTableCount == null ? other$supportedTableCount == null : this$supportedTableCount.equals(other$supportedTableCount)) {
            Object this$availableTableCount = this.getAvailableTableCount();
            Object other$availableTableCount = other.getAvailableTableCount();
            if (this$availableTableCount == null ? other$availableTableCount == null : this$availableTableCount.equals(other$availableTableCount)) {
               Object this$missingTableCount = this.getMissingTableCount();
               Object other$missingTableCount = other.getMissingTableCount();
               if (this$missingTableCount == null ? other$missingTableCount == null : this$missingTableCount.equals(other$missingTableCount)) {
                  Object this$productName = this.getProductName();
                  Object other$productName = other.getProductName();
                  if (this$productName == null ? other$productName == null : this$productName.equals(other$productName)) {
                     Object this$productVersion = this.getProductVersion();
                     Object other$productVersion = other.getProductVersion();
                     if (this$productVersion == null ? other$productVersion == null : this$productVersion.equals(other$productVersion)) {
                        Object this$catalog = this.getCatalog();
                        Object other$catalog = other.getCatalog();
                        if (this$catalog == null ? other$catalog == null : this$catalog.equals(other$catalog)) {
                           Object this$availableTables = this.getAvailableTables();
                           Object other$availableTables = other.getAvailableTables();
                           if (this$availableTables == null ? other$availableTables == null : this$availableTables.equals(other$availableTables)) {
                              Object this$missingTables = this.getMissingTables();
                              Object other$missingTables = other.getMissingTables();
                              return this$missingTables == null ? other$missingTables == null : this$missingTables.equals(other$missingTables);
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
      return other instanceof FoamDataMigrationConnectionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $supportedTableCount = this.getSupportedTableCount();
      result = result * 59 + ($supportedTableCount == null ? 43 : $supportedTableCount.hashCode());
      Object $availableTableCount = this.getAvailableTableCount();
      result = result * 59 + ($availableTableCount == null ? 43 : $availableTableCount.hashCode());
      Object $missingTableCount = this.getMissingTableCount();
      result = result * 59 + ($missingTableCount == null ? 43 : $missingTableCount.hashCode());
      Object $productName = this.getProductName();
      result = result * 59 + ($productName == null ? 43 : $productName.hashCode());
      Object $productVersion = this.getProductVersion();
      result = result * 59 + ($productVersion == null ? 43 : $productVersion.hashCode());
      Object $catalog = this.getCatalog();
      result = result * 59 + ($catalog == null ? 43 : $catalog.hashCode());
      Object $availableTables = this.getAvailableTables();
      result = result * 59 + ($availableTables == null ? 43 : $availableTables.hashCode());
      Object $missingTables = this.getMissingTables();
      return result * 59 + ($missingTables == null ? 43 : $missingTables.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDataMigrationConnectionResponse(productName="
         + this.getProductName()
         + ", productVersion="
         + this.getProductVersion()
         + ", catalog="
         + this.getCatalog()
         + ", supportedTableCount="
         + this.getSupportedTableCount()
         + ", availableTables="
         + this.getAvailableTables()
         + ", missingTables="
         + this.getMissingTables()
         + ", availableTableCount="
         + this.getAvailableTableCount()
         + ", missingTableCount="
         + this.getMissingTableCount()
         + ")";
   }
}
