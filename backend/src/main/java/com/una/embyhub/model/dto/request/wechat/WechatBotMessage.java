package com.una.embyhub.model.dto.request.wechat;

import lombok.Generated;

public class WechatBotMessage {
   private String toUser;
   private String fromUser;
   private String content;
   private String msgType;
   private String event;
   private String eventKey;

   @Generated
   public String getToUser() {
      return this.toUser;
   }

   @Generated
   public String getFromUser() {
      return this.fromUser;
   }

   @Generated
   public String getContent() {
      return this.content;
   }

   @Generated
   public String getMsgType() {
      return this.msgType;
   }

   @Generated
   public String getEvent() {
      return this.event;
   }

   @Generated
   public String getEventKey() {
      return this.eventKey;
   }

   @Generated
   public void setToUser(final String toUser) {
      this.toUser = toUser;
   }

   @Generated
   public void setFromUser(final String fromUser) {
      this.fromUser = fromUser;
   }

   @Generated
   public void setContent(final String content) {
      this.content = content;
   }

   @Generated
   public void setMsgType(final String msgType) {
      this.msgType = msgType;
   }

   @Generated
   public void setEvent(final String event) {
      this.event = event;
   }

   @Generated
   public void setEventKey(final String eventKey) {
      this.eventKey = eventKey;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WechatBotMessage other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$toUser = this.getToUser();
         Object other$toUser = other.getToUser();
         if (this$toUser == null ? other$toUser == null : this$toUser.equals(other$toUser)) {
            Object this$fromUser = this.getFromUser();
            Object other$fromUser = other.getFromUser();
            if (this$fromUser == null ? other$fromUser == null : this$fromUser.equals(other$fromUser)) {
               Object this$content = this.getContent();
               Object other$content = other.getContent();
               if (this$content == null ? other$content == null : this$content.equals(other$content)) {
                  Object this$msgType = this.getMsgType();
                  Object other$msgType = other.getMsgType();
                  if (this$msgType == null ? other$msgType == null : this$msgType.equals(other$msgType)) {
                     Object this$event = this.getEvent();
                     Object other$event = other.getEvent();
                     if (this$event == null ? other$event == null : this$event.equals(other$event)) {
                        Object this$eventKey = this.getEventKey();
                        Object other$eventKey = other.getEventKey();
                        return this$eventKey == null ? other$eventKey == null : this$eventKey.equals(other$eventKey);
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
      return other instanceof WechatBotMessage;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $toUser = this.getToUser();
      result = result * 59 + ($toUser == null ? 43 : $toUser.hashCode());
      Object $fromUser = this.getFromUser();
      result = result * 59 + ($fromUser == null ? 43 : $fromUser.hashCode());
      Object $content = this.getContent();
      result = result * 59 + ($content == null ? 43 : $content.hashCode());
      Object $msgType = this.getMsgType();
      result = result * 59 + ($msgType == null ? 43 : $msgType.hashCode());
      Object $event = this.getEvent();
      result = result * 59 + ($event == null ? 43 : $event.hashCode());
      Object $eventKey = this.getEventKey();
      return result * 59 + ($eventKey == null ? 43 : $eventKey.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "WechatBotMessage(toUser="
         + this.getToUser()
         + ", fromUser="
         + this.getFromUser()
         + ", content="
         + this.getContent()
         + ", msgType="
         + this.getMsgType()
         + ", event="
         + this.getEvent()
         + ", eventKey="
         + this.getEventKey()
         + ")";
   }
}
