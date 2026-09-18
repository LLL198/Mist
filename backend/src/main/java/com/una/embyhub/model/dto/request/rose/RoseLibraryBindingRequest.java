package com.una.embyhub.model.dto.request.rose;

import java.io.Serializable;
import lombok.Generated;

public class RoseLibraryBindingRequest implements Serializable {
   private String libraryKey;
   private String name;
   private String targetRoot;
   private String targetRootName;
   private String targetRootPath;
   private Boolean enabled;

   @Generated
   public String getLibraryKey() {
      return this.libraryKey;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getTargetRoot() {
      return this.targetRoot;
   }

   @Generated
   public String getTargetRootName() {
      return this.targetRootName;
   }

   @Generated
   public String getTargetRootPath() {
      return this.targetRootPath;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setLibraryKey(final String libraryKey) {
      this.libraryKey = libraryKey;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setTargetRoot(final String targetRoot) {
      this.targetRoot = targetRoot;
   }

   @Generated
   public void setTargetRootName(final String targetRootName) {
      this.targetRootName = targetRootName;
   }

   @Generated
   public void setTargetRootPath(final String targetRootPath) {
      this.targetRootPath = targetRootPath;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RoseLibraryBindingRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$enabled = this.getEnabled();
         Object other$enabled = other.getEnabled();
         if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
            Object this$libraryKey = this.getLibraryKey();
            Object other$libraryKey = other.getLibraryKey();
            if (this$libraryKey == null ? other$libraryKey == null : this$libraryKey.equals(other$libraryKey)) {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                  Object this$targetRoot = this.getTargetRoot();
                  Object other$targetRoot = other.getTargetRoot();
                  if (this$targetRoot == null ? other$targetRoot == null : this$targetRoot.equals(other$targetRoot)) {
                     Object this$targetRootName = this.getTargetRootName();
                     Object other$targetRootName = other.getTargetRootName();
                     if (this$targetRootName == null ? other$targetRootName == null : this$targetRootName.equals(other$targetRootName)) {
                        Object this$targetRootPath = this.getTargetRootPath();
                        Object other$targetRootPath = other.getTargetRootPath();
                        return this$targetRootPath == null ? other$targetRootPath == null : this$targetRootPath.equals(other$targetRootPath);
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
      return other instanceof RoseLibraryBindingRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $libraryKey = this.getLibraryKey();
      result = result * 59 + ($libraryKey == null ? 43 : $libraryKey.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $targetRoot = this.getTargetRoot();
      result = result * 59 + ($targetRoot == null ? 43 : $targetRoot.hashCode());
      Object $targetRootName = this.getTargetRootName();
      result = result * 59 + ($targetRootName == null ? 43 : $targetRootName.hashCode());
      Object $targetRootPath = this.getTargetRootPath();
      return result * 59 + ($targetRootPath == null ? 43 : $targetRootPath.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RoseLibraryBindingRequest(libraryKey="
         + this.getLibraryKey()
         + ", name="
         + this.getName()
         + ", targetRoot="
         + this.getTargetRoot()
         + ", targetRootName="
         + this.getTargetRootName()
         + ", targetRootPath="
         + this.getTargetRootPath()
         + ", enabled="
         + this.getEnabled()
         + ")";
   }
}
