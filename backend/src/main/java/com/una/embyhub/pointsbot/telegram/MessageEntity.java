package com.una.embyhub.pointsbot.telegram;

import lombok.Generated;

public class MessageEntity {
   private final String type;
   private final User user;
   private final Integer offset;
   private final Integer length;
   private final String text;

   public MessageEntity(String type, User user) {
      this(type, user, null, null, null);
   }

   public MessageEntity(String type, User user, Integer offset, Integer length, String text) {
      this.type = type;
      this.user = user;
      this.offset = offset;
      this.length = length;
      this.text = text;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public User getUser() {
      return this.user;
   }

   @Generated
   public Integer getOffset() {
      return this.offset;
   }

   @Generated
   public Integer getLength() {
      return this.length;
   }

   @Generated
   public String getText() {
      return this.text;
   }
}
