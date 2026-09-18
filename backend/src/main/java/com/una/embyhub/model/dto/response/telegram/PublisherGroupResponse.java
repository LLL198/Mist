package com.una.embyhub.model.dto.response.telegram;

import com.una.embyhub.model.dto.request.telegram.PublisherInfoRequest;
import java.util.List;
import lombok.Generated;

public class PublisherGroupResponse {
   private String name;
   private List<PublisherInfoRequest> publishers;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public List<PublisherInfoRequest> getPublishers() {
      return this.publishers;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setPublishers(final List<PublisherInfoRequest> publishers) {
      this.publishers = publishers;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PublisherGroupResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$name = this.getName();
         Object other$name = other.getName();
         if (this$name == null ? other$name == null : this$name.equals(other$name)) {
            Object this$publishers = this.getPublishers();
            Object other$publishers = other.getPublishers();
            return this$publishers == null ? other$publishers == null : this$publishers.equals(other$publishers);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PublisherGroupResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $publishers = this.getPublishers();
      return result * 59 + ($publishers == null ? 43 : $publishers.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PublisherGroupResponse(name=" + this.getName() + ", publishers=" + this.getPublishers() + ")";
   }

   @Generated
   public PublisherGroupResponse() {
   }

   @Generated
   public PublisherGroupResponse(final String name, final List<PublisherInfoRequest> publishers) {
      this.name = name;
      this.publishers = publishers;
   }
}
