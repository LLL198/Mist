package com.una.embyhub.model.dto.request.embylibraryaccess;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Generated;

public class EmbyLibraryAccessUsersUpdateRequest {
   @NotNull(
      message = "请选择服务器"
   )
   private Long embyInfoId;
   @NotEmpty(
      message = "请至少选择一个用户"
   )
   @Size(
      max = 500,
      message = "单次最多设置500个用户"
   )
   private List<Long> userIds;
   @NotNull(
      message = "请设置用户覆盖开关"
   )
   private Boolean overrideEnabled;
   private List<String> visibleFolderIds;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public List<Long> getUserIds() {
      return this.userIds;
   }

   @Generated
   public Boolean getOverrideEnabled() {
      return this.overrideEnabled;
   }

   @Generated
   public List<String> getVisibleFolderIds() {
      return this.visibleFolderIds;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setUserIds(final List<Long> userIds) {
      this.userIds = userIds;
   }

   @Generated
   public void setOverrideEnabled(final Boolean overrideEnabled) {
      this.overrideEnabled = overrideEnabled;
   }

   @Generated
   public void setVisibleFolderIds(final List<String> visibleFolderIds) {
      this.visibleFolderIds = visibleFolderIds;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyLibraryAccessUsersUpdateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$overrideEnabled = this.getOverrideEnabled();
            Object other$overrideEnabled = other.getOverrideEnabled();
            if (this$overrideEnabled == null ? other$overrideEnabled == null : this$overrideEnabled.equals(other$overrideEnabled)) {
               Object this$userIds = this.getUserIds();
               Object other$userIds = other.getUserIds();
               if (this$userIds == null ? other$userIds == null : this$userIds.equals(other$userIds)) {
                  Object this$visibleFolderIds = this.getVisibleFolderIds();
                  Object other$visibleFolderIds = other.getVisibleFolderIds();
                  return this$visibleFolderIds == null ? other$visibleFolderIds == null : this$visibleFolderIds.equals(other$visibleFolderIds);
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
      return other instanceof EmbyLibraryAccessUsersUpdateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $overrideEnabled = this.getOverrideEnabled();
      result = result * 59 + ($overrideEnabled == null ? 43 : $overrideEnabled.hashCode());
      Object $userIds = this.getUserIds();
      result = result * 59 + ($userIds == null ? 43 : $userIds.hashCode());
      Object $visibleFolderIds = this.getVisibleFolderIds();
      return result * 59 + ($visibleFolderIds == null ? 43 : $visibleFolderIds.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessUsersUpdateRequest(embyInfoId="
         + this.getEmbyInfoId()
         + ", userIds="
         + this.getUserIds()
         + ", overrideEnabled="
         + this.getOverrideEnabled()
         + ", visibleFolderIds="
         + this.getVisibleFolderIds()
         + ")";
   }
}
