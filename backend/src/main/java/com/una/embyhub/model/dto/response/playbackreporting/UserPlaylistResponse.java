package com.una.embyhub.model.dto.response.playbackreporting;

import com.alibaba.fastjson2.annotation.JSONField;
import java.io.Serializable;
import lombok.Generated;

public class UserPlaylistResponse implements Serializable {
   private String date;
   private String time;
   private String dateTime;
   @JSONField(
      name = "user_id"
   )
   private String userId;
   @JSONField(
      name = "item_name"
   )
   private String itemName;
   @JSONField(
      name = "item_id"
   )
   private Integer itemId;
   @JSONField(
      name = "item_type"
   )
   private String itemType;
   private String duration;
   private String pauseDuration;
   private String playbackMethod;
   private String clientName;
   private String deviceName;
   private String transcodeReasons;
   @JSONField(
      name = "remote_address"
   )
   private String remoteAddress;
   private String location;
   @JSONField(
      name = "user_name"
   )
   private String userName;
   private String nickName;
   @JSONField(
      name = "user_has_image"
   )
   private Boolean userHasImage;
   private String posterUrl;

   @Generated
   public String getDate() {
      return this.date;
   }

   @Generated
   public String getTime() {
      return this.time;
   }

   @Generated
   public String getDateTime() {
      return this.dateTime;
   }

   @Generated
   public String getUserId() {
      return this.userId;
   }

   @Generated
   public String getItemName() {
      return this.itemName;
   }

   @Generated
   public Integer getItemId() {
      return this.itemId;
   }

   @Generated
   public String getItemType() {
      return this.itemType;
   }

   @Generated
   public String getDuration() {
      return this.duration;
   }

   @Generated
   public String getPauseDuration() {
      return this.pauseDuration;
   }

   @Generated
   public String getPlaybackMethod() {
      return this.playbackMethod;
   }

   @Generated
   public String getClientName() {
      return this.clientName;
   }

   @Generated
   public String getDeviceName() {
      return this.deviceName;
   }

   @Generated
   public String getTranscodeReasons() {
      return this.transcodeReasons;
   }

   @Generated
   public String getRemoteAddress() {
      return this.remoteAddress;
   }

   @Generated
   public String getLocation() {
      return this.location;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public String getNickName() {
      return this.nickName;
   }

   @Generated
   public Boolean getUserHasImage() {
      return this.userHasImage;
   }

   @Generated
   public String getPosterUrl() {
      return this.posterUrl;
   }

   @Generated
   public void setDate(final String date) {
      this.date = date;
   }

   @Generated
   public void setTime(final String time) {
      this.time = time;
   }

   @Generated
   public void setDateTime(final String dateTime) {
      this.dateTime = dateTime;
   }

   @Generated
   public void setUserId(final String userId) {
      this.userId = userId;
   }

   @Generated
   public void setItemName(final String itemName) {
      this.itemName = itemName;
   }

   @Generated
   public void setItemId(final Integer itemId) {
      this.itemId = itemId;
   }

   @Generated
   public void setItemType(final String itemType) {
      this.itemType = itemType;
   }

   @Generated
   public void setDuration(final String duration) {
      this.duration = duration;
   }

   @Generated
   public void setPauseDuration(final String pauseDuration) {
      this.pauseDuration = pauseDuration;
   }

   @Generated
   public void setPlaybackMethod(final String playbackMethod) {
      this.playbackMethod = playbackMethod;
   }

   @Generated
   public void setClientName(final String clientName) {
      this.clientName = clientName;
   }

   @Generated
   public void setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
   }

   @Generated
   public void setTranscodeReasons(final String transcodeReasons) {
      this.transcodeReasons = transcodeReasons;
   }

   @Generated
   public void setRemoteAddress(final String remoteAddress) {
      this.remoteAddress = remoteAddress;
   }

   @Generated
   public void setLocation(final String location) {
      this.location = location;
   }

   @Generated
   public void setUserName(final String userName) {
      this.userName = userName;
   }

   @Generated
   public void setNickName(final String nickName) {
      this.nickName = nickName;
   }

   @Generated
   public void setUserHasImage(final Boolean userHasImage) {
      this.userHasImage = userHasImage;
   }

   @Generated
   public void setPosterUrl(final String posterUrl) {
      this.posterUrl = posterUrl;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserPlaylistResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$itemId = this.getItemId();
         Object other$itemId = other.getItemId();
         if (this$itemId == null ? other$itemId == null : this$itemId.equals(other$itemId)) {
            Object this$userHasImage = this.getUserHasImage();
            Object other$userHasImage = other.getUserHasImage();
            if (this$userHasImage == null ? other$userHasImage == null : this$userHasImage.equals(other$userHasImage)) {
               Object this$date = this.getDate();
               Object other$date = other.getDate();
               if (this$date == null ? other$date == null : this$date.equals(other$date)) {
                  Object this$time = this.getTime();
                  Object other$time = other.getTime();
                  if (this$time == null ? other$time == null : this$time.equals(other$time)) {
                     Object this$dateTime = this.getDateTime();
                     Object other$dateTime = other.getDateTime();
                     if (this$dateTime == null ? other$dateTime == null : this$dateTime.equals(other$dateTime)) {
                        Object this$userId = this.getUserId();
                        Object other$userId = other.getUserId();
                        if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                           Object this$itemName = this.getItemName();
                           Object other$itemName = other.getItemName();
                           if (this$itemName == null ? other$itemName == null : this$itemName.equals(other$itemName)) {
                              Object this$itemType = this.getItemType();
                              Object other$itemType = other.getItemType();
                              if (this$itemType == null ? other$itemType == null : this$itemType.equals(other$itemType)) {
                                 Object this$duration = this.getDuration();
                                 Object other$duration = other.getDuration();
                                 if (this$duration == null ? other$duration == null : this$duration.equals(other$duration)) {
                                    Object this$pauseDuration = this.getPauseDuration();
                                    Object other$pauseDuration = other.getPauseDuration();
                                    if (this$pauseDuration == null ? other$pauseDuration == null : this$pauseDuration.equals(other$pauseDuration)) {
                                       Object this$playbackMethod = this.getPlaybackMethod();
                                       Object other$playbackMethod = other.getPlaybackMethod();
                                       if (this$playbackMethod == null ? other$playbackMethod == null : this$playbackMethod.equals(other$playbackMethod)) {
                                          Object this$clientName = this.getClientName();
                                          Object other$clientName = other.getClientName();
                                          if (this$clientName == null ? other$clientName == null : this$clientName.equals(other$clientName)) {
                                             Object this$deviceName = this.getDeviceName();
                                             Object other$deviceName = other.getDeviceName();
                                             if (this$deviceName == null ? other$deviceName == null : this$deviceName.equals(other$deviceName)) {
                                                Object this$transcodeReasons = this.getTranscodeReasons();
                                                Object other$transcodeReasons = other.getTranscodeReasons();
                                                if (this$transcodeReasons == null
                                                   ? other$transcodeReasons == null
                                                   : this$transcodeReasons.equals(other$transcodeReasons)) {
                                                   Object this$remoteAddress = this.getRemoteAddress();
                                                   Object other$remoteAddress = other.getRemoteAddress();
                                                   if (this$remoteAddress == null
                                                      ? other$remoteAddress == null
                                                      : this$remoteAddress.equals(other$remoteAddress)) {
                                                      Object this$location = this.getLocation();
                                                      Object other$location = other.getLocation();
                                                      if (this$location == null ? other$location == null : this$location.equals(other$location)) {
                                                         Object this$userName = this.getUserName();
                                                         Object other$userName = other.getUserName();
                                                         if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                                                            Object this$nickName = this.getNickName();
                                                            Object other$nickName = other.getNickName();
                                                            if (this$nickName == null ? other$nickName == null : this$nickName.equals(other$nickName)) {
                                                               Object this$posterUrl = this.getPosterUrl();
                                                               Object other$posterUrl = other.getPosterUrl();
                                                               return this$posterUrl == null ? other$posterUrl == null : this$posterUrl.equals(other$posterUrl);
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
      return other instanceof UserPlaylistResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $itemId = this.getItemId();
      result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
      Object $userHasImage = this.getUserHasImage();
      result = result * 59 + ($userHasImage == null ? 43 : $userHasImage.hashCode());
      Object $date = this.getDate();
      result = result * 59 + ($date == null ? 43 : $date.hashCode());
      Object $time = this.getTime();
      result = result * 59 + ($time == null ? 43 : $time.hashCode());
      Object $dateTime = this.getDateTime();
      result = result * 59 + ($dateTime == null ? 43 : $dateTime.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $itemName = this.getItemName();
      result = result * 59 + ($itemName == null ? 43 : $itemName.hashCode());
      Object $itemType = this.getItemType();
      result = result * 59 + ($itemType == null ? 43 : $itemType.hashCode());
      Object $duration = this.getDuration();
      result = result * 59 + ($duration == null ? 43 : $duration.hashCode());
      Object $pauseDuration = this.getPauseDuration();
      result = result * 59 + ($pauseDuration == null ? 43 : $pauseDuration.hashCode());
      Object $playbackMethod = this.getPlaybackMethod();
      result = result * 59 + ($playbackMethod == null ? 43 : $playbackMethod.hashCode());
      Object $clientName = this.getClientName();
      result = result * 59 + ($clientName == null ? 43 : $clientName.hashCode());
      Object $deviceName = this.getDeviceName();
      result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
      Object $transcodeReasons = this.getTranscodeReasons();
      result = result * 59 + ($transcodeReasons == null ? 43 : $transcodeReasons.hashCode());
      Object $remoteAddress = this.getRemoteAddress();
      result = result * 59 + ($remoteAddress == null ? 43 : $remoteAddress.hashCode());
      Object $location = this.getLocation();
      result = result * 59 + ($location == null ? 43 : $location.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $nickName = this.getNickName();
      result = result * 59 + ($nickName == null ? 43 : $nickName.hashCode());
      Object $posterUrl = this.getPosterUrl();
      return result * 59 + ($posterUrl == null ? 43 : $posterUrl.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserPlaylistResponse(date="
         + this.getDate()
         + ", time="
         + this.getTime()
         + ", dateTime="
         + this.getDateTime()
         + ", userId="
         + this.getUserId()
         + ", itemName="
         + this.getItemName()
         + ", itemId="
         + this.getItemId()
         + ", itemType="
         + this.getItemType()
         + ", duration="
         + this.getDuration()
         + ", pauseDuration="
         + this.getPauseDuration()
         + ", playbackMethod="
         + this.getPlaybackMethod()
         + ", clientName="
         + this.getClientName()
         + ", deviceName="
         + this.getDeviceName()
         + ", transcodeReasons="
         + this.getTranscodeReasons()
         + ", remoteAddress="
         + this.getRemoteAddress()
         + ", location="
         + this.getLocation()
         + ", userName="
         + this.getUserName()
         + ", nickName="
         + this.getNickName()
         + ", userHasImage="
         + this.getUserHasImage()
         + ", posterUrl="
         + this.getPosterUrl()
         + ")";
   }
}
