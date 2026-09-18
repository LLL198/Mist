package com.una.embyhub.pointsbot.telegram;

import lombok.Generated;

public class User {
   private final Long id;
   private final String userName;
   private final String firstName;
   private final String lastName;
   private final Boolean isBot;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public String getFirstName() {
      return this.firstName;
   }

   @Generated
   public String getLastName() {
      return this.lastName;
   }

   @Generated
   public Boolean getIsBot() {
      return this.isBot;
   }

   @Generated
   public User(final Long id, final String userName, final String firstName, final String lastName, final Boolean isBot) {
      this.id = id;
      this.userName = userName;
      this.firstName = firstName;
      this.lastName = lastName;
      this.isBot = isBot;
   }
}
