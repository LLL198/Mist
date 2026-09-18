package com.una.embyhub.model.dto.response.embynotifydata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class TelegramResponse implements Serializable {
   private String botToken;
   private Integer apiId;
   private String apiHash;
   private String botChatId;
   private String botChatGroupId;
   private String libraryNotifyChatId;
   private String botName;
   private String groupChatId;
   private String startPanelImage;
   private Boolean privateChatMemberRequired = true;
   private Boolean groupCheckinEnabled = true;
   private List<TelegramBotAdminConfig> botAdmins = new ArrayList<>();

   @Generated
   public String getBotToken() {
      return this.botToken;
   }

   @Generated
   public Integer getApiId() {
      return this.apiId;
   }

   @Generated
   public String getApiHash() {
      return this.apiHash;
   }

   @Generated
   public String getBotChatId() {
      return this.botChatId;
   }

   @Generated
   public String getBotChatGroupId() {
      return this.botChatGroupId;
   }

   @Generated
   public String getLibraryNotifyChatId() {
      return this.libraryNotifyChatId;
   }

   @Generated
   public String getBotName() {
      return this.botName;
   }

   @Generated
   public String getGroupChatId() {
      return this.groupChatId;
   }

   @Generated
   public String getStartPanelImage() {
      return this.startPanelImage;
   }

   @Generated
   public Boolean getPrivateChatMemberRequired() {
      return this.privateChatMemberRequired;
   }

   @Generated
   public Boolean getGroupCheckinEnabled() {
      return this.groupCheckinEnabled;
   }

   @Generated
   public List<TelegramBotAdminConfig> getBotAdmins() {
      return this.botAdmins;
   }

   @Generated
   public void setBotToken(final String botToken) {
      this.botToken = botToken;
   }

   @Generated
   public void setApiId(final Integer apiId) {
      this.apiId = apiId;
   }

   @Generated
   public void setApiHash(final String apiHash) {
      this.apiHash = apiHash;
   }

   @Generated
   public void setBotChatId(final String botChatId) {
      this.botChatId = botChatId;
   }

   @Generated
   public void setBotChatGroupId(final String botChatGroupId) {
      this.botChatGroupId = botChatGroupId;
   }

   @Generated
   public void setLibraryNotifyChatId(final String libraryNotifyChatId) {
      this.libraryNotifyChatId = libraryNotifyChatId;
   }

   @Generated
   public void setBotName(final String botName) {
      this.botName = botName;
   }

   @Generated
   public void setGroupChatId(final String groupChatId) {
      this.groupChatId = groupChatId;
   }

   @Generated
   public void setStartPanelImage(final String startPanelImage) {
      this.startPanelImage = startPanelImage;
   }

   @Generated
   public void setPrivateChatMemberRequired(final Boolean privateChatMemberRequired) {
      this.privateChatMemberRequired = privateChatMemberRequired;
   }

   @Generated
   public void setGroupCheckinEnabled(final Boolean groupCheckinEnabled) {
      this.groupCheckinEnabled = groupCheckinEnabled;
   }

   @Generated
   public void setBotAdmins(final List<TelegramBotAdminConfig> botAdmins) {
      this.botAdmins = botAdmins;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$apiId = this.getApiId();
         Object other$apiId = other.getApiId();
         if (this$apiId == null ? other$apiId == null : this$apiId.equals(other$apiId)) {
            Object this$privateChatMemberRequired = this.getPrivateChatMemberRequired();
            Object other$privateChatMemberRequired = other.getPrivateChatMemberRequired();
            if (this$privateChatMemberRequired == null
               ? other$privateChatMemberRequired == null
               : this$privateChatMemberRequired.equals(other$privateChatMemberRequired)) {
               Object this$groupCheckinEnabled = this.getGroupCheckinEnabled();
               Object other$groupCheckinEnabled = other.getGroupCheckinEnabled();
               if (this$groupCheckinEnabled == null ? other$groupCheckinEnabled == null : this$groupCheckinEnabled.equals(other$groupCheckinEnabled)) {
                  Object this$botToken = this.getBotToken();
                  Object other$botToken = other.getBotToken();
                  if (this$botToken == null ? other$botToken == null : this$botToken.equals(other$botToken)) {
                     Object this$apiHash = this.getApiHash();
                     Object other$apiHash = other.getApiHash();
                     if (this$apiHash == null ? other$apiHash == null : this$apiHash.equals(other$apiHash)) {
                        Object this$botChatId = this.getBotChatId();
                        Object other$botChatId = other.getBotChatId();
                        if (this$botChatId == null ? other$botChatId == null : this$botChatId.equals(other$botChatId)) {
                           Object this$botChatGroupId = this.getBotChatGroupId();
                           Object other$botChatGroupId = other.getBotChatGroupId();
                           if (this$botChatGroupId == null ? other$botChatGroupId == null : this$botChatGroupId.equals(other$botChatGroupId)) {
                              Object this$libraryNotifyChatId = this.getLibraryNotifyChatId();
                              Object other$libraryNotifyChatId = other.getLibraryNotifyChatId();
                              if (this$libraryNotifyChatId == null
                                 ? other$libraryNotifyChatId == null
                                 : this$libraryNotifyChatId.equals(other$libraryNotifyChatId)) {
                                 Object this$botName = this.getBotName();
                                 Object other$botName = other.getBotName();
                                 if (this$botName == null ? other$botName == null : this$botName.equals(other$botName)) {
                                    Object this$groupChatId = this.getGroupChatId();
                                    Object other$groupChatId = other.getGroupChatId();
                                    if (this$groupChatId == null ? other$groupChatId == null : this$groupChatId.equals(other$groupChatId)) {
                                       Object this$startPanelImage = this.getStartPanelImage();
                                       Object other$startPanelImage = other.getStartPanelImage();
                                       if (this$startPanelImage == null ? other$startPanelImage == null : this$startPanelImage.equals(other$startPanelImage)) {
                                          Object this$botAdmins = this.getBotAdmins();
                                          Object other$botAdmins = other.getBotAdmins();
                                          return this$botAdmins == null ? other$botAdmins == null : this$botAdmins.equals(other$botAdmins);
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
      return other instanceof TelegramResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $apiId = this.getApiId();
      result = result * 59 + ($apiId == null ? 43 : $apiId.hashCode());
      Object $privateChatMemberRequired = this.getPrivateChatMemberRequired();
      result = result * 59 + ($privateChatMemberRequired == null ? 43 : $privateChatMemberRequired.hashCode());
      Object $groupCheckinEnabled = this.getGroupCheckinEnabled();
      result = result * 59 + ($groupCheckinEnabled == null ? 43 : $groupCheckinEnabled.hashCode());
      Object $botToken = this.getBotToken();
      result = result * 59 + ($botToken == null ? 43 : $botToken.hashCode());
      Object $apiHash = this.getApiHash();
      result = result * 59 + ($apiHash == null ? 43 : $apiHash.hashCode());
      Object $botChatId = this.getBotChatId();
      result = result * 59 + ($botChatId == null ? 43 : $botChatId.hashCode());
      Object $botChatGroupId = this.getBotChatGroupId();
      result = result * 59 + ($botChatGroupId == null ? 43 : $botChatGroupId.hashCode());
      Object $libraryNotifyChatId = this.getLibraryNotifyChatId();
      result = result * 59 + ($libraryNotifyChatId == null ? 43 : $libraryNotifyChatId.hashCode());
      Object $botName = this.getBotName();
      result = result * 59 + ($botName == null ? 43 : $botName.hashCode());
      Object $groupChatId = this.getGroupChatId();
      result = result * 59 + ($groupChatId == null ? 43 : $groupChatId.hashCode());
      Object $startPanelImage = this.getStartPanelImage();
      result = result * 59 + ($startPanelImage == null ? 43 : $startPanelImage.hashCode());
      Object $botAdmins = this.getBotAdmins();
      return result * 59 + ($botAdmins == null ? 43 : $botAdmins.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramResponse(botToken="
         + this.getBotToken()
         + ", apiId="
         + this.getApiId()
         + ", apiHash="
         + this.getApiHash()
         + ", botChatId="
         + this.getBotChatId()
         + ", botChatGroupId="
         + this.getBotChatGroupId()
         + ", libraryNotifyChatId="
         + this.getLibraryNotifyChatId()
         + ", botName="
         + this.getBotName()
         + ", groupChatId="
         + this.getGroupChatId()
         + ", startPanelImage="
         + this.getStartPanelImage()
         + ", privateChatMemberRequired="
         + this.getPrivateChatMemberRequired()
         + ", groupCheckinEnabled="
         + this.getGroupCheckinEnabled()
         + ", botAdmins="
         + this.getBotAdmins()
         + ")";
   }
}
