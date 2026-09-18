package com.una.embyhub.model.dto.request.pointsrecord;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import lombok.Generated;

public class PointsRecordRequest implements Serializable {
   private String username;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String recordType;

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getRecordType() {
      return this.recordType;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setRecordType(final String recordType) {
      this.recordType = recordType;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsRecordRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$username = this.getUsername();
         Object other$username = other.getUsername();
         if (this$username == null ? other$username == null : this$username.equals(other$username)) {
            Object this$recordType = this.getRecordType();
            Object other$recordType = other.getRecordType();
            return this$recordType == null ? other$recordType == null : this$recordType.equals(other$recordType);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsRecordRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $recordType = this.getRecordType();
      return result * 59 + ($recordType == null ? 43 : $recordType.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsRecordRequest(username=" + this.getUsername() + ", recordType=" + this.getRecordType() + ")";
   }
}
