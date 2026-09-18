package com.una.embyhub.model.dto.request.telegram;

import java.util.List;
import lombok.Generated;

public class PublisherInfoRequest {
   private long id;
   private String name;
   private String logo_path;
   private List<String> searchNames;

   @Generated
   public long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getLogo_path() {
      return this.logo_path;
   }

   @Generated
   public List<String> getSearchNames() {
      return this.searchNames;
   }

   @Generated
   public void setId(final long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setLogo_path(final String logo_path) {
      this.logo_path = logo_path;
   }

   @Generated
   public void setSearchNames(final List<String> searchNames) {
      this.searchNames = searchNames;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PublisherInfoRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getId() != other.getId()) {
         return false;
      } else {
         Object this$name = this.getName();
         Object other$name = other.getName();
         if (this$name == null ? other$name == null : this$name.equals(other$name)) {
            Object this$logo_path = this.getLogo_path();
            Object other$logo_path = other.getLogo_path();
            if (this$logo_path == null ? other$logo_path == null : this$logo_path.equals(other$logo_path)) {
               Object this$searchNames = this.getSearchNames();
               Object other$searchNames = other.getSearchNames();
               return this$searchNames == null ? other$searchNames == null : this$searchNames.equals(other$searchNames);
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
      return other instanceof PublisherInfoRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $id = this.getId();
      result = result * 59 + (int)($id >>> 32 ^ $id);
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $logo_path = this.getLogo_path();
      result = result * 59 + ($logo_path == null ? 43 : $logo_path.hashCode());
      Object $searchNames = this.getSearchNames();
      return result * 59 + ($searchNames == null ? 43 : $searchNames.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PublisherInfoRequest(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", logo_path="
         + this.getLogo_path()
         + ", searchNames="
         + this.getSearchNames()
         + ")";
   }

   @Generated
   public PublisherInfoRequest() {
   }

   @Generated
   public PublisherInfoRequest(final long id, final String name, final String logo_path, final List<String> searchNames) {
      this.id = id;
      this.name = name;
      this.logo_path = logo_path;
      this.searchNames = searchNames;
   }
}
