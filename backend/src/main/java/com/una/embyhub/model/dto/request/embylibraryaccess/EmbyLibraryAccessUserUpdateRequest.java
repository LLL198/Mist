package com.una.embyhub.model.dto.request.embylibraryaccess;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Generated;

public class EmbyLibraryAccessUserUpdateRequest {
   @NotNull(
      message = "请选择服务器"
   )
   private Long embyInfoId;
   @NotNull(
      message = "请选择用户"
   )
   private Long userId;
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
   public Long getUserId() {
      return this.userId;
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
   public void setUserId(final Long userId) {
      this.userId = userId;
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
      } else if (!(o instanceof EmbyLibraryAccessUserUpdateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$overrideEnabled = this.getOverrideEnabled();
               Object other$overrideEnabled = other.getOverrideEnabled();
               if (this$overrideEnabled == null ? other$overrideEnabled == null : this$overrideEnabled.equals(other$overrideEnabled)) {
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
      return other instanceof EmbyLibraryAccessUserUpdateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $overrideEnabled = this.getOverrideEnabled();
      result = result * 59 + ($overrideEnabled == null ? 43 : $overrideEnabled.hashCode());
      Object $visibleFolderIds = this.getVisibleFolderIds();
      return result * 59 + ($visibleFolderIds == null ? 43 : $visibleFolderIds.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessUserUpdateRequest(embyInfoId="
         + this.getEmbyInfoId()
         + ", userId="
         + this.getUserId()
         + ", overrideEnabled="
         + this.getOverrideEnabled()
         + ", visibleFolderIds="
         + this.getVisibleFolderIds()
         + ")";
   }
}
