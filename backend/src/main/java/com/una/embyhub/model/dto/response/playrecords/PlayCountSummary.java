package com.una.embyhub.model.dto.response.playrecords;

import lombok.Generated;

public class PlayCountSummary {
   private String content;
   private Long playCount;

   @Generated
   public String getContent() {
      return this.content;
   }

   @Generated
   public Long getPlayCount() {
      return this.playCount;
   }

   @Generated
   public void setContent(final String content) {
      this.content = content;
   }

   @Generated
   public void setPlayCount(final Long playCount) {
      this.playCount = playCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlayCountSummary other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$playCount = this.getPlayCount();
         Object other$playCount = other.getPlayCount();
         if (this$playCount == null ? other$playCount == null : this$playCount.equals(other$playCount)) {
            Object this$content = this.getContent();
            Object other$content = other.getContent();
            return this$content == null ? other$content == null : this$content.equals(other$content);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PlayCountSummary;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $playCount = this.getPlayCount();
      result = result * 59 + ($playCount == null ? 43 : $playCount.hashCode());
      Object $content = this.getContent();
      return result * 59 + ($content == null ? 43 : $content.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PlayCountSummary(content=" + this.getContent() + ", playCount=" + this.getPlayCount() + ")";
   }
}
