package com.una.embyhub.pointsbot.telegram;

import lombok.Generated;

public class CallbackQuery {
   private final String id;
   private final String data;
   private final Message message;
   private final User from;

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getData() {
      return this.data;
   }

   @Generated
   public Message getMessage() {
      return this.message;
   }

   @Generated
   public User getFrom() {
      return this.from;
   }

   @Generated
   public CallbackQuery(final String id, final String data, final Message message, final User from) {
      this.id = id;
      this.data = data;
      this.message = message;
      this.from = from;
   }
}
