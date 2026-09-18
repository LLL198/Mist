package com.una.embyhub.model.dto.response.embylibraryaccess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Generated;

public class EmbyLibraryFolderResponse {
   private String id;
   private String name;
   private String primaryImageTag;
   private String imageItemId;
   @JsonIgnore
   private List<String> legacyIds;
   @JsonIgnore
   private List<String> subFolderAccessIds;

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getPrimaryImageTag() {
      return this.primaryImageTag;
   }

   @Generated
   public String getImageItemId() {
      return this.imageItemId;
   }

   @Generated
   public List<String> getLegacyIds() {
      return this.legacyIds;
   }

   @Generated
   public List<String> getSubFolderAccessIds() {
      return this.subFolderAccessIds;
   }

   @Generated
   public void setId(final String id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setPrimaryImageTag(final String primaryImageTag) {
      this.primaryImageTag = primaryImageTag;
   }

   @Generated
   public void setImageItemId(final String imageItemId) {
      this.imageItemId = imageItemId;
   }

   @JsonIgnore
   @Generated
   public void setLegacyIds(final List<String> legacyIds) {
      this.legacyIds = legacyIds;
   }

   @JsonIgnore
   @Generated
   public void setSubFolderAccessIds(final List<String> subFolderAccessIds) {
      this.subFolderAccessIds = subFolderAccessIds;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyLibraryFolderResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$primaryImageTag = this.getPrimaryImageTag();
               Object other$primaryImageTag = other.getPrimaryImageTag();
               if (this$primaryImageTag == null ? other$primaryImageTag == null : this$primaryImageTag.equals(other$primaryImageTag)) {
                  Object this$imageItemId = this.getImageItemId();
                  Object other$imageItemId = other.getImageItemId();
                  if (this$imageItemId == null ? other$imageItemId == null : this$imageItemId.equals(other$imageItemId)) {
                     Object this$legacyIds = this.getLegacyIds();
                     Object other$legacyIds = other.getLegacyIds();
                     if (this$legacyIds == null ? other$legacyIds == null : this$legacyIds.equals(other$legacyIds)) {
                        Object this$subFolderAccessIds = this.getSubFolderAccessIds();
                        Object other$subFolderAccessIds = other.getSubFolderAccessIds();
                        return this$subFolderAccessIds == null ? other$subFolderAccessIds == null : this$subFolderAccessIds.equals(other$subFolderAccessIds);
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
      return other instanceof EmbyLibraryFolderResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $primaryImageTag = this.getPrimaryImageTag();
      result = result * 59 + ($primaryImageTag == null ? 43 : $primaryImageTag.hashCode());
      Object $imageItemId = this.getImageItemId();
      result = result * 59 + ($imageItemId == null ? 43 : $imageItemId.hashCode());
      Object $legacyIds = this.getLegacyIds();
      result = result * 59 + ($legacyIds == null ? 43 : $legacyIds.hashCode());
      Object $subFolderAccessIds = this.getSubFolderAccessIds();
      return result * 59 + ($subFolderAccessIds == null ? 43 : $subFolderAccessIds.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryFolderResponse(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", primaryImageTag="
         + this.getPrimaryImageTag()
         + ", imageItemId="
         + this.getImageItemId()
         + ", legacyIds="
         + this.getLegacyIds()
         + ", subFolderAccessIds="
         + this.getSubFolderAccessIds()
         + ")";
   }

   @Generated
   public EmbyLibraryFolderResponse() {
   }

   @Generated
   public EmbyLibraryFolderResponse(
      final String id,
      final String name,
      final String primaryImageTag,
      final String imageItemId,
      final List<String> legacyIds,
      final List<String> subFolderAccessIds
   ) {
      this.id = id;
      this.name = name;
      this.primaryImageTag = primaryImageTag;
      this.imageItemId = imageItemId;
      this.legacyIds = legacyIds;
      this.subFolderAccessIds = subFolderAccessIds;
   }
}
