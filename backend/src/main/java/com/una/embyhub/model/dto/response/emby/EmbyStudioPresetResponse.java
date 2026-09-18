package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class EmbyStudioPresetResponse implements Serializable {
   private String label;
   private List<String> aliases = new ArrayList<>();
   private String studioIds;
   private List<EmbyStudioPresetResponse.Studio> studios = new ArrayList<>();

   @Generated
   public String getLabel() {
      return this.label;
   }

   @Generated
   public List<String> getAliases() {
      return this.aliases;
   }

   @Generated
   public String getStudioIds() {
      return this.studioIds;
   }

   @Generated
   public List<EmbyStudioPresetResponse.Studio> getStudios() {
      return this.studios;
   }

   @Generated
   public void setLabel(final String label) {
      this.label = label;
   }

   @Generated
   public void setAliases(final List<String> aliases) {
      this.aliases = aliases;
   }

   @Generated
   public void setStudioIds(final String studioIds) {
      this.studioIds = studioIds;
   }

   @Generated
   public void setStudios(final List<EmbyStudioPresetResponse.Studio> studios) {
      this.studios = studios;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyStudioPresetResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$label = this.getLabel();
         Object other$label = other.getLabel();
         if (this$label == null ? other$label == null : this$label.equals(other$label)) {
            Object this$aliases = this.getAliases();
            Object other$aliases = other.getAliases();
            if (this$aliases == null ? other$aliases == null : this$aliases.equals(other$aliases)) {
               Object this$studioIds = this.getStudioIds();
               Object other$studioIds = other.getStudioIds();
               if (this$studioIds == null ? other$studioIds == null : this$studioIds.equals(other$studioIds)) {
                  Object this$studios = this.getStudios();
                  Object other$studios = other.getStudios();
                  return this$studios == null ? other$studios == null : this$studios.equals(other$studios);
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
      return other instanceof EmbyStudioPresetResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $label = this.getLabel();
      result = result * 59 + ($label == null ? 43 : $label.hashCode());
      Object $aliases = this.getAliases();
      result = result * 59 + ($aliases == null ? 43 : $aliases.hashCode());
      Object $studioIds = this.getStudioIds();
      result = result * 59 + ($studioIds == null ? 43 : $studioIds.hashCode());
      Object $studios = this.getStudios();
      return result * 59 + ($studios == null ? 43 : $studios.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyStudioPresetResponse(label="
         + this.getLabel()
         + ", aliases="
         + this.getAliases()
         + ", studioIds="
         + this.getStudioIds()
         + ", studios="
         + this.getStudios()
         + ")";
   }

   public static class Studio implements Serializable {
      private String id;
      private String name;

      @Generated
      public String getId() {
         return this.id;
      }

      @Generated
      public String getName() {
         return this.name;
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
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof EmbyStudioPresetResponse.Studio other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$id = this.getId();
            Object other$id = other.getId();
            if (this$id == null ? other$id == null : this$id.equals(other$id)) {
               Object this$name = this.getName();
               Object other$name = other.getName();
               return this$name == null ? other$name == null : this$name.equals(other$name);
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof EmbyStudioPresetResponse.Studio;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $id = this.getId();
         result = result * 59 + ($id == null ? 43 : $id.hashCode());
         Object $name = this.getName();
         return result * 59 + ($name == null ? 43 : $name.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "EmbyStudioPresetResponse.Studio(id=" + this.getId() + ", name=" + this.getName() + ")";
      }
   }
}
