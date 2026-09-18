package com.una.embyhub.model.dto.response.foammigration;

import java.io.Serializable;
import lombok.Generated;

public class FoamDataMigrationJobResponse implements Serializable {
   private String jobId;
   private FoamDataMigrationProgressResponse progress;

   @Generated
   public String getJobId() {
      return this.jobId;
   }

   @Generated
   public FoamDataMigrationProgressResponse getProgress() {
      return this.progress;
   }

   @Generated
   public void setJobId(final String jobId) {
      this.jobId = jobId;
   }

   @Generated
   public void setProgress(final FoamDataMigrationProgressResponse progress) {
      this.progress = progress;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDataMigrationJobResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$jobId = this.getJobId();
         Object other$jobId = other.getJobId();
         if (this$jobId == null ? other$jobId == null : this$jobId.equals(other$jobId)) {
            Object this$progress = this.getProgress();
            Object other$progress = other.getProgress();
            return this$progress == null ? other$progress == null : this$progress.equals(other$progress);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof FoamDataMigrationJobResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $jobId = this.getJobId();
      result = result * 59 + ($jobId == null ? 43 : $jobId.hashCode());
      Object $progress = this.getProgress();
      return result * 59 + ($progress == null ? 43 : $progress.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDataMigrationJobResponse(jobId=" + this.getJobId() + ", progress=" + this.getProgress() + ")";
   }
}
