package com.una.embyhub.model.dto.response.tmdbfollow;

import java.io.Serializable;
import lombok.Generated;

public class TmdbCastResponse implements Serializable {
   private String name;
   private String character;
   private String profilePath;
   private Integer order;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getCharacter() {
      return this.character;
   }

   @Generated
   public String getProfilePath() {
      return this.profilePath;
   }

   @Generated
   public Integer getOrder() {
      return this.order;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setCharacter(final String character) {
      this.character = character;
   }

   @Generated
   public void setProfilePath(final String profilePath) {
      this.profilePath = profilePath;
   }

   @Generated
   public void setOrder(final Integer order) {
      this.order = order;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbCastResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$order = this.getOrder();
         Object other$order = other.getOrder();
         if (this$order == null ? other$order == null : this$order.equals(other$order)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$character = this.getCharacter();
               Object other$character = other.getCharacter();
               if (this$character == null ? other$character == null : this$character.equals(other$character)) {
                  Object this$profilePath = this.getProfilePath();
                  Object other$profilePath = other.getProfilePath();
                  return this$profilePath == null ? other$profilePath == null : this$profilePath.equals(other$profilePath);
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
      return other instanceof TmdbCastResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $order = this.getOrder();
      result = result * 59 + ($order == null ? 43 : $order.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $character = this.getCharacter();
      result = result * 59 + ($character == null ? 43 : $character.hashCode());
      Object $profilePath = this.getProfilePath();
      return result * 59 + ($profilePath == null ? 43 : $profilePath.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbCastResponse(name="
         + this.getName()
         + ", character="
         + this.getCharacter()
         + ", profilePath="
         + this.getProfilePath()
         + ", order="
         + this.getOrder()
         + ")";
   }
}
