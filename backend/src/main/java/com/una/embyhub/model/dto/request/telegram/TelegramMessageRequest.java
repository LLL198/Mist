package com.una.embyhub.model.dto.request.telegram;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class TelegramMessageRequest implements Serializable {
   private String query;
   private List<String> channels;

   @Generated
   public String getQuery() {
      return this.query;
   }

   @Generated
   public List<String> getChannels() {
      return this.channels;
   }

   @Generated
   public void setQuery(final String query) {
      this.query = query;
   }

   @Generated
   public void setChannels(final List<String> channels) {
      this.channels = channels;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramMessageRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$query = this.getQuery();
         Object other$query = other.getQuery();
         if (this$query == null ? other$query == null : this$query.equals(other$query)) {
            Object this$channels = this.getChannels();
            Object other$channels = other.getChannels();
            return this$channels == null ? other$channels == null : this$channels.equals(other$channels);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TelegramMessageRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $query = this.getQuery();
      result = result * 59 + ($query == null ? 43 : $query.hashCode());
      Object $channels = this.getChannels();
      return result * 59 + ($channels == null ? 43 : $channels.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramMessageRequest(query=" + this.getQuery() + ", channels=" + this.getChannels() + ")";
   }
}
