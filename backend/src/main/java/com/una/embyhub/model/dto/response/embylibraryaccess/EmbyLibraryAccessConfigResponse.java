package com.una.embyhub.model.dto.response.embylibraryaccess;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class EmbyLibraryAccessConfigResponse {
   private boolean configured;
   private boolean enabled;
   private List<String> visibleFolderIds = new ArrayList<>();
   private String source;

   @Generated
   public boolean isConfigured() {
      return this.configured;
   }

   @Generated
   public boolean isEnabled() {
      return this.enabled;
   }

   @Generated
   public List<String> getVisibleFolderIds() {
      return this.visibleFolderIds;
   }

   @Generated
   public String getSource() {
      return this.source;
   }

   @Generated
   public void setConfigured(final boolean configured) {
      this.configured = configured;
   }

   @Generated
   public void setEnabled(final boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setVisibleFolderIds(final List<String> visibleFolderIds) {
      this.visibleFolderIds = visibleFolderIds;
   }

   @Generated
   public void setSource(final String source) {
      this.source = source;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyLibraryAccessConfigResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isConfigured() != other.isConfigured()) {
         return false;
      } else if (this.isEnabled() != other.isEnabled()) {
         return false;
      } else {
         Object this$visibleFolderIds = this.getVisibleFolderIds();
         Object other$visibleFolderIds = other.getVisibleFolderIds();
         if (this$visibleFolderIds == null ? other$visibleFolderIds == null : this$visibleFolderIds.equals(other$visibleFolderIds)) {
            Object this$source = this.getSource();
            Object other$source = other.getSource();
            return this$source == null ? other$source == null : this$source.equals(other$source);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyLibraryAccessConfigResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isConfigured() ? 79 : 97);
      result = result * 59 + (this.isEnabled() ? 79 : 97);
      Object $visibleFolderIds = this.getVisibleFolderIds();
      result = result * 59 + ($visibleFolderIds == null ? 43 : $visibleFolderIds.hashCode());
      Object $source = this.getSource();
      return result * 59 + ($source == null ? 43 : $source.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessConfigResponse(configured="
         + this.isConfigured()
         + ", enabled="
         + this.isEnabled()
         + ", visibleFolderIds="
         + this.getVisibleFolderIds()
         + ", source="
         + this.getSource()
         + ")";
   }

   @Generated
   public EmbyLibraryAccessConfigResponse() {
   }

   @Generated
   public EmbyLibraryAccessConfigResponse(final boolean configured, final boolean enabled, final List<String> visibleFolderIds, final String source) {
      this.configured = configured;
      this.enabled = enabled;
      this.visibleFolderIds = visibleFolderIds;
      this.source = source;
   }
}
