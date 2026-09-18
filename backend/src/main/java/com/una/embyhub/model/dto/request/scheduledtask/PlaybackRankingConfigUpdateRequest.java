package com.una.embyhub.model.dto.request.scheduledtask;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Generated;

public class PlaybackRankingConfigUpdateRequest {
   @NotNull(
      message = "服务器不能为空"
   )
   private Long embyInfoId;
   @NotNull(
      message = "排除用户列表不能为空"
   )
   @Size(
      max = 500,
      message = "单个服务器最多排除500个用户"
   )
   private List<String> excludedUserIds;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public List<String> getExcludedUserIds() {
      return this.excludedUserIds;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setExcludedUserIds(final List<String> excludedUserIds) {
      this.excludedUserIds = excludedUserIds;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlaybackRankingConfigUpdateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$excludedUserIds = this.getExcludedUserIds();
            Object other$excludedUserIds = other.getExcludedUserIds();
            return this$excludedUserIds == null ? other$excludedUserIds == null : this$excludedUserIds.equals(other$excludedUserIds);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PlaybackRankingConfigUpdateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $excludedUserIds = this.getExcludedUserIds();
      return result * 59 + ($excludedUserIds == null ? 43 : $excludedUserIds.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PlaybackRankingConfigUpdateRequest(embyInfoId=" + this.getEmbyInfoId() + ", excludedUserIds=" + this.getExcludedUserIds() + ")";
   }
}
