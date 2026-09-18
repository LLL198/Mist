package com.una.embyhub.model.dto.request.embylibraryaccess;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Generated;

public class EmbyLibraryAccessGlobalUpdateRequest {
   @NotNull(
      message = "请选择服务器"
   )
   private Long embyInfoId;
   @NotNull(
      message = "请设置分级开关"
   )
   private Boolean enabled;
   private List<String> visibleFolderIds;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
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
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
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
      } else if (!(o instanceof EmbyLibraryAccessGlobalUpdateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$visibleFolderIds = this.getVisibleFolderIds();
               Object other$visibleFolderIds = other.getVisibleFolderIds();
               return this$visibleFolderIds == null ? other$visibleFolderIds == null : this$visibleFolderIds.equals(other$visibleFolderIds);
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
      return other instanceof EmbyLibraryAccessGlobalUpdateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $visibleFolderIds = this.getVisibleFolderIds();
      return result * 59 + ($visibleFolderIds == null ? 43 : $visibleFolderIds.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessGlobalUpdateRequest(embyInfoId="
         + this.getEmbyInfoId()
         + ", enabled="
         + this.getEnabled()
         + ", visibleFolderIds="
         + this.getVisibleFolderIds()
         + ")";
   }
}
