package com.una.embyhub.pointsbot.telegram;

import java.util.List;
import lombok.Generated;

public class Message {
   private final long messageId;
   private final long chatId;
   private final Chat chat;
   private final User from;
   private final String text;
   private final List<MessageEntity> entities;
   private final boolean photo;
   private final boolean video;
   private final boolean animation;
   private final boolean document;
   private final boolean audio;
   private final boolean voice;
   private final boolean sticker;

   public boolean hasText() {
      return this.text != null && !this.text.isBlank();
   }

   public boolean hasPhoto() {
      return this.photo;
   }

   public boolean hasVideo() {
      return this.video;
   }

   public boolean hasAnimation() {
      return this.animation;
   }

   public boolean hasDocument() {
      return this.document;
   }

   public boolean hasAudio() {
      return this.audio;
   }

   public boolean hasVoice() {
      return this.voice;
   }

   public boolean hasSticker() {
      return this.sticker;
   }

   @Generated
   Message(
      final long messageId,
      final long chatId,
      final Chat chat,
      final User from,
      final String text,
      final List<MessageEntity> entities,
      final boolean photo,
      final boolean video,
      final boolean animation,
      final boolean document,
      final boolean audio,
      final boolean voice,
      final boolean sticker
   ) {
      this.messageId = messageId;
      this.chatId = chatId;
      this.chat = chat;
      this.from = from;
      this.text = text;
      this.entities = entities;
      this.photo = photo;
      this.video = video;
      this.animation = animation;
      this.document = document;
      this.audio = audio;
      this.voice = voice;
      this.sticker = sticker;
   }

   @Generated
   public static Message.MessageBuilder builder() {
      return new Message.MessageBuilder();
   }

   @Generated
   public long getMessageId() {
      return this.messageId;
   }

   @Generated
   public long getChatId() {
      return this.chatId;
   }

   @Generated
   public Chat getChat() {
      return this.chat;
   }

   @Generated
   public User getFrom() {
      return this.from;
   }

   @Generated
   public String getText() {
      return this.text;
   }

   @Generated
   public List<MessageEntity> getEntities() {
      return this.entities;
   }

   @Generated
   public boolean isPhoto() {
      return this.photo;
   }

   @Generated
   public boolean isVideo() {
      return this.video;
   }

   @Generated
   public boolean isAnimation() {
      return this.animation;
   }

   @Generated
   public boolean isDocument() {
      return this.document;
   }

   @Generated
   public boolean isAudio() {
      return this.audio;
   }

   @Generated
   public boolean isVoice() {
      return this.voice;
   }

   @Generated
   public boolean isSticker() {
      return this.sticker;
   }

   @Generated
   public static class MessageBuilder {
      @Generated
      private long messageId;
      @Generated
      private long chatId;
      @Generated
      private Chat chat;
      @Generated
      private User from;
      @Generated
      private String text;
      @Generated
      private List<MessageEntity> entities;
      @Generated
      private boolean photo;
      @Generated
      private boolean video;
      @Generated
      private boolean animation;
      @Generated
      private boolean document;
      @Generated
      private boolean audio;
      @Generated
      private boolean voice;
      @Generated
      private boolean sticker;

      @Generated
      MessageBuilder() {
      }

      @Generated
      public Message.MessageBuilder messageId(final long messageId) {
         this.messageId = messageId;
         return this;
      }

      @Generated
      public Message.MessageBuilder chatId(final long chatId) {
         this.chatId = chatId;
         return this;
      }

      @Generated
      public Message.MessageBuilder chat(final Chat chat) {
         this.chat = chat;
         return this;
      }

      @Generated
      public Message.MessageBuilder from(final User from) {
         this.from = from;
         return this;
      }

      @Generated
      public Message.MessageBuilder text(final String text) {
         this.text = text;
         return this;
      }

      @Generated
      public Message.MessageBuilder entities(final List<MessageEntity> entities) {
         this.entities = entities;
         return this;
      }

      @Generated
      public Message.MessageBuilder photo(final boolean photo) {
         this.photo = photo;
         return this;
      }

      @Generated
      public Message.MessageBuilder video(final boolean video) {
         this.video = video;
         return this;
      }

      @Generated
      public Message.MessageBuilder animation(final boolean animation) {
         this.animation = animation;
         return this;
      }

      @Generated
      public Message.MessageBuilder document(final boolean document) {
         this.document = document;
         return this;
      }

      @Generated
      public Message.MessageBuilder audio(final boolean audio) {
         this.audio = audio;
         return this;
      }

      @Generated
      public Message.MessageBuilder voice(final boolean voice) {
         this.voice = voice;
         return this;
      }

      @Generated
      public Message.MessageBuilder sticker(final boolean sticker) {
         this.sticker = sticker;
         return this;
      }

      @Generated
      public Message build() {
         return new Message(
            this.messageId,
            this.chatId,
            this.chat,
            this.from,
            this.text,
            this.entities,
            this.photo,
            this.video,
            this.animation,
            this.document,
            this.audio,
            this.voice,
            this.sticker
         );
      }

      @Generated
      @Override
      public String toString() {
         return "Message.MessageBuilder(messageId="
            + this.messageId
            + ", chatId="
            + this.chatId
            + ", chat="
            + this.chat
            + ", from="
            + this.from
            + ", text="
            + this.text
            + ", entities="
            + this.entities
            + ", photo="
            + this.photo
            + ", video="
            + this.video
            + ", animation="
            + this.animation
            + ", document="
            + this.document
            + ", audio="
            + this.audio
            + ", voice="
            + this.voice
            + ", sticker="
            + this.sticker
            + ")";
      }
   }
}
