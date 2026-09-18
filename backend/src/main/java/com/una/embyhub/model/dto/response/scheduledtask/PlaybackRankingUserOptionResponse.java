package com.una.embyhub.model.dto.response.scheduledtask;

import lombok.Generated;

public class PlaybackRankingUserOptionResponse {
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
      } else if (!(o instanceof PlaybackRankingUserOptionResponse other)) {
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
      return other instanceof PlaybackRankingUserOptionResponse;
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
      return "PlaybackRankingUserOptionResponse(id=" + this.getId() + ", name=" + this.getName() + ")";
   }

   @Generated
   public PlaybackRankingUserOptionResponse() {
   }

   @Generated
   public PlaybackRankingUserOptionResponse(final String id, final String name) {
      this.id = id;
      this.name = name;
   }
}
