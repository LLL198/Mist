package com.una.embyhub.model.dto.request.tmdbfollow;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class TmdbFollowProgressBatchRequest implements Serializable {
   @NotNull(
      message = "进度列表不能为空"
   )
   @NotEmpty(
      message = "进度列表不能为空"
   )
   @Valid
   private List<TmdbFollowProgressRequest> progressList;

   @Generated
   public List<TmdbFollowProgressRequest> getProgressList() {
      return this.progressList;
   }

   @Generated
   public void setProgressList(final List<TmdbFollowProgressRequest> progressList) {
      this.progressList = progressList;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollowProgressBatchRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$progressList = this.getProgressList();
         Object other$progressList = other.getProgressList();
         return this$progressList == null ? other$progressList == null : this$progressList.equals(other$progressList);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbFollowProgressBatchRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $progressList = this.getProgressList();
      return result * 59 + ($progressList == null ? 43 : $progressList.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowProgressBatchRequest(progressList=" + this.getProgressList() + ")";
   }
}
