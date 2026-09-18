package com.una.embyhub.model.dto.request.playbackreporting;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

public class CustomQueryRequest {
   @JSONField(
      name = "CustomQueryString"
   )
   @JsonProperty("CustomQueryString")
   @JsonAlias({"customQueryString"})
   private String customQueryString;
   @JSONField(
      name = "ReplaceUserId"
   )
   @JsonProperty("ReplaceUserId")
   @JsonAlias({"replaceUserId"})
   private Boolean replaceUserId;

   @Generated
   public String getCustomQueryString() {
      return this.customQueryString;
   }

   @Generated
   public Boolean getReplaceUserId() {
      return this.replaceUserId;
   }

   @JsonProperty("CustomQueryString")
   @JsonAlias({"customQueryString"})
   @Generated
   public void setCustomQueryString(final String customQueryString) {
      this.customQueryString = customQueryString;
   }

   @JsonProperty("ReplaceUserId")
   @JsonAlias({"replaceUserId"})
   @Generated
   public void setReplaceUserId(final Boolean replaceUserId) {
      this.replaceUserId = replaceUserId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CustomQueryRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$replaceUserId = this.getReplaceUserId();
         Object other$replaceUserId = other.getReplaceUserId();
         if (this$replaceUserId == null ? other$replaceUserId == null : this$replaceUserId.equals(other$replaceUserId)) {
            Object this$customQueryString = this.getCustomQueryString();
            Object other$customQueryString = other.getCustomQueryString();
            return this$customQueryString == null ? other$customQueryString == null : this$customQueryString.equals(other$customQueryString);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof CustomQueryRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $replaceUserId = this.getReplaceUserId();
      result = result * 59 + ($replaceUserId == null ? 43 : $replaceUserId.hashCode());
      Object $customQueryString = this.getCustomQueryString();
      return result * 59 + ($customQueryString == null ? 43 : $customQueryString.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "CustomQueryRequest(customQueryString=" + this.getCustomQueryString() + ", replaceUserId=" + this.getReplaceUserId() + ")";
   }
}
