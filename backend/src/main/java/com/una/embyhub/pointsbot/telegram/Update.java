package com.una.embyhub.pointsbot.telegram;

import lombok.Generated;

public class Update {
   private final Message message;
   private final CallbackQuery callbackQuery;

   public static Update message(Message message) {
      return new Update(message, null);
   }

   public static Update callbackQuery(CallbackQuery callbackQuery) {
      return new Update(null, callbackQuery);
   }

   public boolean hasMessage() {
      return this.message != null;
   }

   public boolean hasCallbackQuery() {
      return this.callbackQuery != null;
   }

   @Generated
   public Message getMessage() {
      return this.message;
   }

   @Generated
   public CallbackQuery getCallbackQuery() {
      return this.callbackQuery;
   }

   @Generated
   public Update(final Message message, final CallbackQuery callbackQuery) {
      this.message = message;
      this.callbackQuery = callbackQuery;
   }
}
