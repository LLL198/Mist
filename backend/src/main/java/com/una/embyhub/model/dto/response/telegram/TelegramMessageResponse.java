package com.una.embyhub.model.dto.response.telegram;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class TelegramMessageResponse implements Serializable {
   private String title;
   private String textContent;
   private String imageUrl;
   private String messageLink;
   private String time;
   private Integer messageId;
   private String channelName;
   private List<String> shareLinks;

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getTextContent() {
      return this.textContent;
   }

   @Generated
   public String getImageUrl() {
      return this.imageUrl;
   }

   @Generated
   public String getMessageLink() {
      return this.messageLink;
   }

   @Generated
   public String getTime() {
      return this.time;
   }

   @Generated
   public Integer getMessageId() {
      return this.messageId;
   }

   @Generated
   public String getChannelName() {
      return this.channelName;
   }

   @Generated
   public List<String> getShareLinks() {
      return this.shareLinks;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setTextContent(final String textContent) {
      this.textContent = textContent;
   }

   @Generated
   public void setImageUrl(final String imageUrl) {
      this.imageUrl = imageUrl;
   }

   @Generated
   public void setMessageLink(final String messageLink) {
      this.messageLink = messageLink;
   }

   @Generated
   public void setTime(final String time) {
      this.time = time;
   }

   @Generated
   public void setMessageId(final Integer messageId) {
      this.messageId = messageId;
   }

   @Generated
   public void setChannelName(final String channelName) {
      this.channelName = channelName;
   }

   @Generated
   public void setShareLinks(final List<String> shareLinks) {
      this.shareLinks = shareLinks;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramMessageResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$messageId = this.getMessageId();
         Object other$messageId = other.getMessageId();
         if (this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId)) {
            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null ? other$title == null : this$title.equals(other$title)) {
               Object this$textContent = this.getTextContent();
               Object other$textContent = other.getTextContent();
               if (this$textContent == null ? other$textContent == null : this$textContent.equals(other$textContent)) {
                  Object this$imageUrl = this.getImageUrl();
                  Object other$imageUrl = other.getImageUrl();
                  if (this$imageUrl == null ? other$imageUrl == null : this$imageUrl.equals(other$imageUrl)) {
                     Object this$messageLink = this.getMessageLink();
                     Object other$messageLink = other.getMessageLink();
                     if (this$messageLink == null ? other$messageLink == null : this$messageLink.equals(other$messageLink)) {
                        Object this$time = this.getTime();
                        Object other$time = other.getTime();
                        if (this$time == null ? other$time == null : this$time.equals(other$time)) {
                           Object this$channelName = this.getChannelName();
                           Object other$channelName = other.getChannelName();
                           if (this$channelName == null ? other$channelName == null : this$channelName.equals(other$channelName)) {
                              Object this$shareLinks = this.getShareLinks();
                              Object other$shareLinks = other.getShareLinks();
                              return this$shareLinks == null ? other$shareLinks == null : this$shareLinks.equals(other$shareLinks);
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
      return other instanceof TelegramMessageResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $messageId = this.getMessageId();
      result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $textContent = this.getTextContent();
      result = result * 59 + ($textContent == null ? 43 : $textContent.hashCode());
      Object $imageUrl = this.getImageUrl();
      result = result * 59 + ($imageUrl == null ? 43 : $imageUrl.hashCode());
      Object $messageLink = this.getMessageLink();
      result = result * 59 + ($messageLink == null ? 43 : $messageLink.hashCode());
      Object $time = this.getTime();
      result = result * 59 + ($time == null ? 43 : $time.hashCode());
      Object $channelName = this.getChannelName();
      result = result * 59 + ($channelName == null ? 43 : $channelName.hashCode());
      Object $shareLinks = this.getShareLinks();
      return result * 59 + ($shareLinks == null ? 43 : $shareLinks.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramMessageResponse(title="
         + this.getTitle()
         + ", textContent="
         + this.getTextContent()
         + ", imageUrl="
         + this.getImageUrl()
         + ", messageLink="
         + this.getMessageLink()
         + ", time="
         + this.getTime()
         + ", messageId="
         + this.getMessageId()
         + ", channelName="
         + this.getChannelName()
         + ", shareLinks="
         + this.getShareLinks()
         + ")";
   }
}
