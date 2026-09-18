package com.una.embyhub.pointsbot.telegram;

import lombok.Generated;

public class Chat {
   private final long id;
   private final boolean groupChat;
   private final boolean superGroupChat;

   @Generated
   public long getId() {
      return this.id;
   }

   @Generated
   public boolean isGroupChat() {
      return this.groupChat;
   }

   @Generated
   public boolean isSuperGroupChat() {
      return this.superGroupChat;
   }

   @Generated
   public Chat(final long id, final boolean groupChat, final boolean superGroupChat) {
      this.id = id;
      this.groupChat = groupChat;
      this.superGroupChat = superGroupChat;
   }
}
