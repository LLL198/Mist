package com.una.embyhub.model.dto.request.foammigration;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class FoamDataMigrationRequest implements Serializable {
   private String hostPort;
   private String databaseName;
   private String jdbcUrl;
   @NotBlank(
      message = "旧库用户名不能为空"
   )
   private String username;
   private String password;
   private Integer batchSize;
   @Deprecated
   private Boolean fullData;

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
   public Integer getBatchSize() {
      return this.batchSize;
   }

   @Deprecated
   @Generated
   public Boolean getFullData() {
      return this.fullData;
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
   public void setBatchSize(final Integer batchSize) {
      this.batchSize = batchSize;
   }

   @Deprecated
   @Generated
   public void setFullData(final Boolean fullData) {
      this.fullData = fullData;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDataMigrationRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$batchSize = this.getBatchSize();
         Object other$batchSize = other.getBatchSize();
         if (this$batchSize == null ? other$batchSize == null : this$batchSize.equals(other$batchSize)) {
            Object this$fullData = this.getFullData();
            Object other$fullData = other.getFullData();
            if (this$fullData == null ? other$fullData == null : this$fullData.equals(other$fullData)) {
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
                           return this$password == null ? other$password == null : this$password.equals(other$password);
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
      return other instanceof FoamDataMigrationRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $batchSize = this.getBatchSize();
      result = result * 59 + ($batchSize == null ? 43 : $batchSize.hashCode());
      Object $fullData = this.getFullData();
      result = result * 59 + ($fullData == null ? 43 : $fullData.hashCode());
      Object $hostPort = this.getHostPort();
      result = result * 59 + ($hostPort == null ? 43 : $hostPort.hashCode());
      Object $databaseName = this.getDatabaseName();
      result = result * 59 + ($databaseName == null ? 43 : $databaseName.hashCode());
      Object $jdbcUrl = this.getJdbcUrl();
      result = result * 59 + ($jdbcUrl == null ? 43 : $jdbcUrl.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $password = this.getPassword();
      return result * 59 + ($password == null ? 43 : $password.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDataMigrationRequest(hostPort="
         + this.getHostPort()
         + ", databaseName="
         + this.getDatabaseName()
         + ", jdbcUrl="
         + this.getJdbcUrl()
         + ", username="
         + this.getUsername()
         + ", password="
         + this.getPassword()
         + ", batchSize="
         + this.getBatchSize()
         + ", fullData="
         + this.getFullData()
         + ")";
   }
}
