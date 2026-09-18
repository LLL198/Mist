package com.una.embyhub.model.dto.request.telegram;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SendMessageRequest implements Serializable {
   private String chatId;
   private String name;
   private String overview;
   private String tmdbUrl;
   private String imgUrl;
   private String parseMode;
   private String serverUrl;
   private String serverName;
   private TelegramClient telegramClient;
   private Map<String, String> extraVariables = new HashMap<>();

   @Generated
   public String getChatId() {
      return this.chatId;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getOverview() {
      return this.overview;
   }

   @Generated
   public String getTmdbUrl() {
      return this.tmdbUrl;
   }

   @Generated
   public String getImgUrl() {
      return this.imgUrl;
   }

   @Generated
   public String getParseMode() {
      return this.parseMode;
   }

   @Generated
   public String getServerUrl() {
      return this.serverUrl;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public TelegramClient getTelegramClient() {
      return this.telegramClient;
   }

   @Generated
   public Map<String, String> getExtraVariables() {
      return this.extraVariables;
   }

   @Generated
   public void setChatId(final String chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setOverview(final String overview) {
      this.overview = overview;
   }

   @Generated
   public void setTmdbUrl(final String tmdbUrl) {
      this.tmdbUrl = tmdbUrl;
   }

   @Generated
   public void setImgUrl(final String imgUrl) {
      this.imgUrl = imgUrl;
   }

   @Generated
   public void setParseMode(final String parseMode) {
      this.parseMode = parseMode;
   }

   @Generated
   public void setServerUrl(final String serverUrl) {
      this.serverUrl = serverUrl;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setTelegramClient(final TelegramClient telegramClient) {
      this.telegramClient = telegramClient;
   }

   @Generated
   public void setExtraVariables(final Map<String, String> extraVariables) {
      this.extraVariables = extraVariables;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SendMessageRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$chatId = this.getChatId();
         Object other$chatId = other.getChatId();
         if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$overview = this.getOverview();
               Object other$overview = other.getOverview();
               if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                  Object this$tmdbUrl = this.getTmdbUrl();
                  Object other$tmdbUrl = other.getTmdbUrl();
                  if (this$tmdbUrl == null ? other$tmdbUrl == null : this$tmdbUrl.equals(other$tmdbUrl)) {
                     Object this$imgUrl = this.getImgUrl();
                     Object other$imgUrl = other.getImgUrl();
                     if (this$imgUrl == null ? other$imgUrl == null : this$imgUrl.equals(other$imgUrl)) {
                        Object this$parseMode = this.getParseMode();
                        Object other$parseMode = other.getParseMode();
                        if (this$parseMode == null ? other$parseMode == null : this$parseMode.equals(other$parseMode)) {
                           Object this$serverUrl = this.getServerUrl();
                           Object other$serverUrl = other.getServerUrl();
                           if (this$serverUrl == null ? other$serverUrl == null : this$serverUrl.equals(other$serverUrl)) {
                              Object this$serverName = this.getServerName();
                              Object other$serverName = other.getServerName();
                              if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                 Object this$telegramClient = this.getTelegramClient();
                                 Object other$telegramClient = other.getTelegramClient();
                                 if (this$telegramClient == null ? other$telegramClient == null : this$telegramClient.equals(other$telegramClient)) {
                                    Object this$extraVariables = this.getExtraVariables();
                                    Object other$extraVariables = other.getExtraVariables();
                                    return this$extraVariables == null ? other$extraVariables == null : this$extraVariables.equals(other$extraVariables);
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
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
      return other instanceof SendMessageRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $overview = this.getOverview();
      result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
      Object $tmdbUrl = this.getTmdbUrl();
      result = result * 59 + ($tmdbUrl == null ? 43 : $tmdbUrl.hashCode());
      Object $imgUrl = this.getImgUrl();
      result = result * 59 + ($imgUrl == null ? 43 : $imgUrl.hashCode());
      Object $parseMode = this.getParseMode();
      result = result * 59 + ($parseMode == null ? 43 : $parseMode.hashCode());
      Object $serverUrl = this.getServerUrl();
      result = result * 59 + ($serverUrl == null ? 43 : $serverUrl.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $telegramClient = this.getTelegramClient();
      result = result * 59 + ($telegramClient == null ? 43 : $telegramClient.hashCode());
      Object $extraVariables = this.getExtraVariables();
      return result * 59 + ($extraVariables == null ? 43 : $extraVariables.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SendMessageRequest(chatId="
         + this.getChatId()
         + ", name="
         + this.getName()
         + ", overview="
         + this.getOverview()
         + ", tmdbUrl="
         + this.getTmdbUrl()
         + ", imgUrl="
         + this.getImgUrl()
         + ", parseMode="
         + this.getParseMode()
         + ", serverUrl="
         + this.getServerUrl()
         + ", serverName="
         + this.getServerName()
         + ", telegramClient="
         + this.getTelegramClient()
         + ", extraVariables="
         + this.getExtraVariables()
         + ")";
   }
}
