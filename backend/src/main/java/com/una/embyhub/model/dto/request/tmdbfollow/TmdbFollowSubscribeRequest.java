package com.una.embyhub.model.dto.request.tmdbfollow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class TmdbFollowSubscribeRequest implements Serializable {
   @NotNull(
      message = "TMDB 剧集ID不能为空"
   )
   private Integer tmdbId;
   private String mediaType = "tv";
   private String language = "zh-CN";
   @NotEmpty(
      message = "至少选择一个通知渠道"
   )
   private List<String> notifyChannels;
   @NotBlank(
      message = "订阅人名称不能为空"
   )
   private String subscriberName;

   @Generated
   public Integer getTmdbId() {
      return this.tmdbId;
   }

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public String getLanguage() {
      return this.language;
   }

   @Generated
   public List<String> getNotifyChannels() {
      return this.notifyChannels;
   }

   @Generated
   public String getSubscriberName() {
      return this.subscriberName;
   }

   @Generated
   public void setTmdbId(final Integer tmdbId) {
      this.tmdbId = tmdbId;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setLanguage(final String language) {
      this.language = language;
   }

   @Generated
   public void setNotifyChannels(final List<String> notifyChannels) {
      this.notifyChannels = notifyChannels;
   }

   @Generated
   public void setSubscriberName(final String subscriberName) {
      this.subscriberName = subscriberName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbFollowSubscribeRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tmdbId = this.getTmdbId();
         Object other$tmdbId = other.getTmdbId();
         if (this$tmdbId == null ? other$tmdbId == null : this$tmdbId.equals(other$tmdbId)) {
            Object this$mediaType = this.getMediaType();
            Object other$mediaType = other.getMediaType();
            if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
               Object this$language = this.getLanguage();
               Object other$language = other.getLanguage();
               if (this$language == null ? other$language == null : this$language.equals(other$language)) {
                  Object this$notifyChannels = this.getNotifyChannels();
                  Object other$notifyChannels = other.getNotifyChannels();
                  if (this$notifyChannels == null ? other$notifyChannels == null : this$notifyChannels.equals(other$notifyChannels)) {
                     Object this$subscriberName = this.getSubscriberName();
                     Object other$subscriberName = other.getSubscriberName();
                     return this$subscriberName == null ? other$subscriberName == null : this$subscriberName.equals(other$subscriberName);
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
      return other instanceof TmdbFollowSubscribeRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tmdbId = this.getTmdbId();
      result = result * 59 + ($tmdbId == null ? 43 : $tmdbId.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $language = this.getLanguage();
      result = result * 59 + ($language == null ? 43 : $language.hashCode());
      Object $notifyChannels = this.getNotifyChannels();
      result = result * 59 + ($notifyChannels == null ? 43 : $notifyChannels.hashCode());
      Object $subscriberName = this.getSubscriberName();
      return result * 59 + ($subscriberName == null ? 43 : $subscriberName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbFollowSubscribeRequest(tmdbId="
         + this.getTmdbId()
         + ", mediaType="
         + this.getMediaType()
         + ", language="
         + this.getLanguage()
         + ", notifyChannels="
         + this.getNotifyChannels()
         + ", subscriberName="
         + this.getSubscriberName()
         + ")";
   }
}
