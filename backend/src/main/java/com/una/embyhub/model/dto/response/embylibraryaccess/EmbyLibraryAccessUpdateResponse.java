package com.una.embyhub.model.dto.response.embylibraryaccess;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class EmbyLibraryAccessUpdateResponse {
   private int matchedUserCount;
   private int appliedUserCount;
   private int failedUserCount;
   private List<String> failures = new ArrayList<>();

   @Generated
   public int getMatchedUserCount() {
      return this.matchedUserCount;
   }

   @Generated
   public int getAppliedUserCount() {
      return this.appliedUserCount;
   }

   @Generated
   public int getFailedUserCount() {
      return this.failedUserCount;
   }

   @Generated
   public List<String> getFailures() {
      return this.failures;
   }

   @Generated
   public void setMatchedUserCount(final int matchedUserCount) {
      this.matchedUserCount = matchedUserCount;
   }

   @Generated
   public void setAppliedUserCount(final int appliedUserCount) {
      this.appliedUserCount = appliedUserCount;
   }

   @Generated
   public void setFailedUserCount(final int failedUserCount) {
      this.failedUserCount = failedUserCount;
   }

   @Generated
   public void setFailures(final List<String> failures) {
      this.failures = failures;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyLibraryAccessUpdateResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getMatchedUserCount() != other.getMatchedUserCount()) {
         return false;
      } else if (this.getAppliedUserCount() != other.getAppliedUserCount()) {
         return false;
      } else if (this.getFailedUserCount() != other.getFailedUserCount()) {
         return false;
      } else {
         Object this$failures = this.getFailures();
         Object other$failures = other.getFailures();
         return this$failures == null ? other$failures == null : this$failures.equals(other$failures);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyLibraryAccessUpdateResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getMatchedUserCount();
      result = result * 59 + this.getAppliedUserCount();
      result = result * 59 + this.getFailedUserCount();
      Object $failures = this.getFailures();
      return result * 59 + ($failures == null ? 43 : $failures.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessUpdateResponse(matchedUserCount="
         + this.getMatchedUserCount()
         + ", appliedUserCount="
         + this.getAppliedUserCount()
         + ", failedUserCount="
         + this.getFailedUserCount()
         + ", failures="
         + this.getFailures()
         + ")";
   }
}
