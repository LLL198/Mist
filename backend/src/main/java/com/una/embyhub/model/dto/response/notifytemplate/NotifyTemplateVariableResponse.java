package com.una.embyhub.model.dto.response.notifytemplate;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class NotifyTemplateVariableResponse implements Serializable {
   private String key;
   private String description;
   private List<String> templateCodes;
   private List<String> messageTypes;

   @Generated
   public String getKey() {
      return this.key;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public List<String> getTemplateCodes() {
      return this.templateCodes;
   }

   @Generated
   public List<String> getMessageTypes() {
      return this.messageTypes;
   }

   @Generated
   public void setKey(final String key) {
      this.key = key;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setTemplateCodes(final List<String> templateCodes) {
      this.templateCodes = templateCodes;
   }

   @Generated
   public void setMessageTypes(final List<String> messageTypes) {
      this.messageTypes = messageTypes;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NotifyTemplateVariableResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$key = this.getKey();
         Object other$key = other.getKey();
         if (this$key == null ? other$key == null : this$key.equals(other$key)) {
            Object this$description = this.getDescription();
            Object other$description = other.getDescription();
            if (this$description == null ? other$description == null : this$description.equals(other$description)) {
               Object this$templateCodes = this.getTemplateCodes();
               Object other$templateCodes = other.getTemplateCodes();
               if (this$templateCodes == null ? other$templateCodes == null : this$templateCodes.equals(other$templateCodes)) {
                  Object this$messageTypes = this.getMessageTypes();
                  Object other$messageTypes = other.getMessageTypes();
                  return this$messageTypes == null ? other$messageTypes == null : this$messageTypes.equals(other$messageTypes);
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
      return other instanceof NotifyTemplateVariableResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $key = this.getKey();
      result = result * 59 + ($key == null ? 43 : $key.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
      Object $templateCodes = this.getTemplateCodes();
      result = result * 59 + ($templateCodes == null ? 43 : $templateCodes.hashCode());
      Object $messageTypes = this.getMessageTypes();
      return result * 59 + ($messageTypes == null ? 43 : $messageTypes.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "NotifyTemplateVariableResponse(key="
         + this.getKey()
         + ", description="
         + this.getDescription()
         + ", templateCodes="
         + this.getTemplateCodes()
         + ", messageTypes="
         + this.getMessageTypes()
         + ")";
   }
}
