package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("simultaneous_playback_record_detail")
public class SimultaneousPlaybackRecordDetail extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("record_id")
   private Long recordId;
   @TableField("item_id")
   private String itemId;
   @TableField("item_name")
   private String itemName;
   @TableField("item_type")
   private String itemType;
   @TableField("poster_url")
   private String posterUrl;
   @TableField("playback_time")
   private Date playbackTime;
   @TableField("client")
   private String client;
   @TableField("device_name")
   private String deviceName;
   @TableField("remote_endpoint")
   private String remoteEndpoint;
   @TableField("remote_address")
   private String remoteAddress;
   public static final String COL_ID = "id";
   public static final String COL_RECORD_ID = "record_id";
   public static final String COL_ITEM_ID = "item_id";
   public static final String COL_ITEM_NAME = "item_name";
   public static final String COL_ITEM_TYPE = "item_type";
   public static final String COL_POSTER_URL = "poster_url";
   public static final String COL_PLAYBACK_TIME = "playback_time";
   public static final String COL_CLIENT = "client";
   public static final String COL_DEVICE_NAME = "device_name";
   public static final String COL_REMOTE_ENDPOINT = "remote_endpoint";
   public static final String COL_REMOTE_ADDRESS = "remote_address";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getRecordId() {
      return this.recordId;
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
   public void setRecordId(final Long recordId) {
      this.recordId = recordId;
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
   public String toString() {
      return "SimultaneousPlaybackRecordDetail(id="
         + this.getId()
         + ", recordId="
         + this.getRecordId()
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SimultaneousPlaybackRecordDetail other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$recordId = this.getRecordId();
            Object other$recordId = other.getRecordId();
            if (this$recordId == null ? other$recordId == null : this$recordId.equals(other$recordId)) {
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
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof SimultaneousPlaybackRecordDetail;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $recordId = this.getRecordId();
      result = result * 59 + ($recordId == null ? 43 : $recordId.hashCode());
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
}
