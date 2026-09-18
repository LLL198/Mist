package com.una.embyhub.model.dto.response.simultaneous;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class SimultaneousPlaybackRecordDetailResponse implements Serializable {
   private Long id;
   private String itemId;
   private String itemName;
   private String itemType;
   private String posterUrl;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   private Date playbackTime;
   private String client;
   private String deviceName;
   private String remoteEndpoint;
   private String remoteAddress;

   @Generated
   public static SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder builder() {
      return new SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getItemId() {
      return this.itemId;
   }

   @Generated
   public String getItemName() {
      return this.itemName;
   }

   @Generated
   public String getItemType() {
      return this.itemType;
   }

   @Generated
   public String getPosterUrl() {
      return this.posterUrl;
   }

   @Generated
   public Date getPlaybackTime() {
      return this.playbackTime;
   }

   @Generated
   public String getClient() {
      return this.client;
   }

   @Generated
   public String getDeviceName() {
      return this.deviceName;
   }

   @Generated
   public String getRemoteEndpoint() {
      return this.remoteEndpoint;
   }

   @Generated
   public String getRemoteAddress() {
      return this.remoteAddress;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setItemId(final String itemId) {
      this.itemId = itemId;
   }

   @Generated
   public void setItemName(final String itemName) {
      this.itemName = itemName;
   }

   @Generated
   public void setItemType(final String itemType) {
      this.itemType = itemType;
   }

   @Generated
   public void setPosterUrl(final String posterUrl) {
      this.posterUrl = posterUrl;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "GMT+8"
   )
   @Generated
   public void setPlaybackTime(final Date playbackTime) {
      this.playbackTime = playbackTime;
   }

   @Generated
   public void setClient(final String client) {
      this.client = client;
   }

   @Generated
   public void setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
   }

   @Generated
   public void setRemoteEndpoint(final String remoteEndpoint) {
      this.remoteEndpoint = remoteEndpoint;
   }

   @Generated
   public void setRemoteAddress(final String remoteAddress) {
      this.remoteAddress = remoteAddress;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SimultaneousPlaybackRecordDetailResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$itemId = this.getItemId();
            Object other$itemId = other.getItemId();
            if (this$itemId == null ? other$itemId == null : this$itemId.equals(other$itemId)) {
               Object this$itemName = this.getItemName();
               Object other$itemName = other.getItemName();
               if (this$itemName == null ? other$itemName == null : this$itemName.equals(other$itemName)) {
                  Object this$itemType = this.getItemType();
                  Object other$itemType = other.getItemType();
                  if (this$itemType == null ? other$itemType == null : this$itemType.equals(other$itemType)) {
                     Object this$posterUrl = this.getPosterUrl();
                     Object other$posterUrl = other.getPosterUrl();
                     if (this$posterUrl == null ? other$posterUrl == null : this$posterUrl.equals(other$posterUrl)) {
                        Object this$playbackTime = this.getPlaybackTime();
                        Object other$playbackTime = other.getPlaybackTime();
                        if (this$playbackTime == null ? other$playbackTime == null : this$playbackTime.equals(other$playbackTime)) {
                           Object this$client = this.getClient();
                           Object other$client = other.getClient();
                           if (this$client == null ? other$client == null : this$client.equals(other$client)) {
                              Object this$deviceName = this.getDeviceName();
                              Object other$deviceName = other.getDeviceName();
                              if (this$deviceName == null ? other$deviceName == null : this$deviceName.equals(other$deviceName)) {
                                 Object this$remoteEndpoint = this.getRemoteEndpoint();
                                 Object other$remoteEndpoint = other.getRemoteEndpoint();
                                 if (this$remoteEndpoint == null ? other$remoteEndpoint == null : this$remoteEndpoint.equals(other$remoteEndpoint)) {
                                    Object this$remoteAddress = this.getRemoteAddress();
                                    Object other$remoteAddress = other.getRemoteAddress();
                                    return this$remoteAddress == null ? other$remoteAddress == null : this$remoteAddress.equals(other$remoteAddress);
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
      return other instanceof SimultaneousPlaybackRecordDetailResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $itemId = this.getItemId();
      result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
      Object $itemName = this.getItemName();
      result = result * 59 + ($itemName == null ? 43 : $itemName.hashCode());
      Object $itemType = this.getItemType();
      result = result * 59 + ($itemType == null ? 43 : $itemType.hashCode());
      Object $posterUrl = this.getPosterUrl();
      result = result * 59 + ($posterUrl == null ? 43 : $posterUrl.hashCode());
      Object $playbackTime = this.getPlaybackTime();
      result = result * 59 + ($playbackTime == null ? 43 : $playbackTime.hashCode());
      Object $client = this.getClient();
      result = result * 59 + ($client == null ? 43 : $client.hashCode());
      Object $deviceName = this.getDeviceName();
      result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
      Object $remoteEndpoint = this.getRemoteEndpoint();
      result = result * 59 + ($remoteEndpoint == null ? 43 : $remoteEndpoint.hashCode());
      Object $remoteAddress = this.getRemoteAddress();
      return result * 59 + ($remoteAddress == null ? 43 : $remoteAddress.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SimultaneousPlaybackRecordDetailResponse(id="
         + this.getId()
         + ", itemId="
         + this.getItemId()
         + ", itemName="
         + this.getItemName()
         + ", itemType="
         + this.getItemType()
         + ", posterUrl="
         + this.getPosterUrl()
         + ", playbackTime="
         + this.getPlaybackTime()
         + ", client="
         + this.getClient()
         + ", deviceName="
         + this.getDeviceName()
         + ", remoteEndpoint="
         + this.getRemoteEndpoint()
         + ", remoteAddress="
         + this.getRemoteAddress()
         + ")";
   }

   @Generated
   public SimultaneousPlaybackRecordDetailResponse() {
   }

   @Generated
   public SimultaneousPlaybackRecordDetailResponse(
      final Long id,
      final String itemId,
      final String itemName,
      final String itemType,
      final String posterUrl,
      final Date playbackTime,
      final String client,
      final String deviceName,
      final String remoteEndpoint,
      final String remoteAddress
   ) {
      this.id = id;
      this.itemId = itemId;
      this.itemName = itemName;
      this.itemType = itemType;
      this.posterUrl = posterUrl;
      this.playbackTime = playbackTime;
      this.client = client;
      this.deviceName = deviceName;
      this.remoteEndpoint = remoteEndpoint;
      this.remoteAddress = remoteAddress;
   }

   @Generated
   public static class SimultaneousPlaybackRecordDetailResponseBuilder {
      @Generated
      private Long id;
      @Generated
      private String itemId;
      @Generated
      private String itemName;
      @Generated
      private String itemType;
      @Generated
      private String posterUrl;
      @Generated
      private Date playbackTime;
      @Generated
      private String client;
      @Generated
      private String deviceName;
      @Generated
      private String remoteEndpoint;
      @Generated
      private String remoteAddress;

      @Generated
      SimultaneousPlaybackRecordDetailResponseBuilder() {
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder itemId(final String itemId) {
         this.itemId = itemId;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder itemName(final String itemName) {
         this.itemName = itemName;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder itemType(final String itemType) {
         this.itemType = itemType;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder posterUrl(final String posterUrl) {
         this.posterUrl = posterUrl;
         return this;
      }

      @JsonFormat(
         pattern = "yyyy-MM-dd HH:mm:ss",
         timezone = "GMT+8"
      )
      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder playbackTime(final Date playbackTime) {
         this.playbackTime = playbackTime;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder client(final String client) {
         this.client = client;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder deviceName(final String deviceName) {
         this.deviceName = deviceName;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder remoteEndpoint(final String remoteEndpoint) {
         this.remoteEndpoint = remoteEndpoint;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder remoteAddress(final String remoteAddress) {
         this.remoteAddress = remoteAddress;
         return this;
      }

      @Generated
      public SimultaneousPlaybackRecordDetailResponse build() {
         return new SimultaneousPlaybackRecordDetailResponse(
            this.id,
            this.itemId,
            this.itemName,
            this.itemType,
            this.posterUrl,
            this.playbackTime,
            this.client,
            this.deviceName,
            this.remoteEndpoint,
            this.remoteAddress
         );
      }

      @Generated
      @Override
      public String toString() {
         return "SimultaneousPlaybackRecordDetailResponse.SimultaneousPlaybackRecordDetailResponseBuilder(id="
            + this.id
            + ", itemId="
            + this.itemId
            + ", itemName="
            + this.itemName
            + ", itemType="
            + this.itemType
            + ", posterUrl="
            + this.posterUrl
            + ", playbackTime="
            + this.playbackTime
            + ", client="
            + this.client
            + ", deviceName="
            + this.deviceName
            + ", remoteEndpoint="
            + this.remoteEndpoint
            + ", remoteAddress="
            + this.remoteAddress
            + ")";
      }
   }
}
