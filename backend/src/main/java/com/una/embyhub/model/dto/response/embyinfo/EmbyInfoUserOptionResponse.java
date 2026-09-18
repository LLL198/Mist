package com.una.embyhub.model.dto.response.embyinfo;

import java.io.Serializable;
import lombok.Generated;

public class EmbyInfoUserOptionResponse implements Serializable {
   private String id;
   private String name;
   private String avatarUrl;

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getAvatarUrl() {
      return this.avatarUrl;
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
   public void setAvatarUrl(final String avatarUrl) {
      this.avatarUrl = avatarUrl;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyInfoUserOptionResponse other)) {
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
               Object this$avatarUrl = this.getAvatarUrl();
               Object other$avatarUrl = other.getAvatarUrl();
               return this$avatarUrl == null ? other$avatarUrl == null : this$avatarUrl.equals(other$avatarUrl);
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
      return other instanceof EmbyInfoUserOptionResponse;
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
      Object $avatarUrl = this.getAvatarUrl();
      return result * 59 + ($avatarUrl == null ? 43 : $avatarUrl.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyInfoUserOptionResponse(id=" + this.getId() + ", name=" + this.getName() + ", avatarUrl=" + this.getAvatarUrl() + ")";
   }

   @Generated
   public EmbyInfoUserOptionResponse() {
   }

   @Generated
   public EmbyInfoUserOptionResponse(final String id, final String name, final String avatarUrl) {
      this.id = id;
      this.name = name;
      this.avatarUrl = avatarUrl;
   }
}
