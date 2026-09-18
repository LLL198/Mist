package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("playback_reporting_record")
public class PlaybackReportingRecord extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("play_day")
   private Date playDay;
   @TableField("play_date")
   private Date playDate;
   @TableField("user_id")
   private String userId;
   @TableField("user_name")
   private String userName;
   @TableField("nick_name")
   private String nickName;
   @TableField("item_id")
   private Integer itemId;
   @TableField("item_name")
   private String itemName;
   @TableField("item_type")
   private String itemType;
   @TableField("duration")
   private Integer duration;
   @TableField("client_name")
   private String clientName;
   @TableField("remote_address")
   private String remoteAddress;
   @TableField("location")
   private String location;
   @TableField("poster_url")
   private String posterUrl;
   @TableField("row_hash")
   private String rowHash;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Date getPlayDay() {
      return this.playDay;
   }

   @Generated
   public Date getPlayDate() {
      return this.playDate;
   }

   @Generated
   public String getUserId() {
      return this.userId;
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
   public Integer getItemId() {
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
   public Integer getDuration() {
      return this.duration;
   }

   @Generated
   public String getClientName() {
      return this.clientName;
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
   public String getPosterUrl() {
      return this.posterUrl;
   }

   @Generated
   public String getRowHash() {
      return this.rowHash;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setPlayDay(final Date playDay) {
      this.playDay = playDay;
   }

   @Generated
   public void setPlayDate(final Date playDate) {
      this.playDate = playDate;
   }

   @Generated
   public void setUserId(final String userId) {
      this.userId = userId;
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
   public void setItemId(final Integer itemId) {
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
   public void setDuration(final Integer duration) {
      this.duration = duration;
   }

   @Generated
   public void setClientName(final String clientName) {
      this.clientName = clientName;
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
   public void setPosterUrl(final String posterUrl) {
      this.posterUrl = posterUrl;
   }

   @Generated
   public void setRowHash(final String rowHash) {
      this.rowHash = rowHash;
   }

   @Generated
   @Override
   public String toString() {
      return "PlaybackReportingRecord(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", playDay="
         + this.getPlayDay()
         + ", playDate="
         + this.getPlayDate()
         + ", userId="
         + this.getUserId()
         + ", userName="
         + this.getUserName()
         + ", nickName="
         + this.getNickName()
         + ", itemId="
         + this.getItemId()
         + ", itemName="
         + this.getItemName()
         + ", itemType="
         + this.getItemType()
         + ", duration="
         + this.getDuration()
         + ", clientName="
         + this.getClientName()
         + ", remoteAddress="
         + this.getRemoteAddress()
         + ", location="
         + this.getLocation()
         + ", posterUrl="
         + this.getPosterUrl()
         + ", rowHash="
         + this.getRowHash()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlaybackReportingRecord other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$itemId = this.getItemId();
               Object other$itemId = other.getItemId();
               if (this$itemId == null ? other$itemId == null : this$itemId.equals(other$itemId)) {
                  Object this$duration = this.getDuration();
                  Object other$duration = other.getDuration();
                  if (this$duration == null ? other$duration == null : this$duration.equals(other$duration)) {
                     Object this$playDay = this.getPlayDay();
                     Object other$playDay = other.getPlayDay();
                     if (this$playDay == null ? other$playDay == null : this$playDay.equals(other$playDay)) {
                        Object this$playDate = this.getPlayDate();
                        Object other$playDate = other.getPlayDate();
                        if (this$playDate == null ? other$playDate == null : this$playDate.equals(other$playDate)) {
                           Object this$userId = this.getUserId();
                           Object other$userId = other.getUserId();
                           if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                              Object this$userName = this.getUserName();
                              Object other$userName = other.getUserName();
                              if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                                 Object this$nickName = this.getNickName();
                                 Object other$nickName = other.getNickName();
                                 if (this$nickName == null ? other$nickName == null : this$nickName.equals(other$nickName)) {
                                    Object this$itemName = this.getItemName();
                                    Object other$itemName = other.getItemName();
                                    if (this$itemName == null ? other$itemName == null : this$itemName.equals(other$itemName)) {
                                       Object this$itemType = this.getItemType();
                                       Object other$itemType = other.getItemType();
                                       if (this$itemType == null ? other$itemType == null : this$itemType.equals(other$itemType)) {
                                          Object this$clientName = this.getClientName();
                                          Object other$clientName = other.getClientName();
                                          if (this$clientName == null ? other$clientName == null : this$clientName.equals(other$clientName)) {
                                             Object this$remoteAddress = this.getRemoteAddress();
                                             Object other$remoteAddress = other.getRemoteAddress();
                                             if (this$remoteAddress == null ? other$remoteAddress == null : this$remoteAddress.equals(other$remoteAddress)) {
                                                Object this$location = this.getLocation();
                                                Object other$location = other.getLocation();
                                                if (this$location == null ? other$location == null : this$location.equals(other$location)) {
                                                   Object this$posterUrl = this.getPosterUrl();
                                                   Object other$posterUrl = other.getPosterUrl();
                                                   if (this$posterUrl == null ? other$posterUrl == null : this$posterUrl.equals(other$posterUrl)) {
                                                      Object this$rowHash = this.getRowHash();
                                                      Object other$rowHash = other.getRowHash();
                                                      return this$rowHash == null ? other$rowHash == null : this$rowHash.equals(other$rowHash);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PlaybackReportingRecord;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $itemId = this.getItemId();
      result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
      Object $duration = this.getDuration();
      result = result * 59 + ($duration == null ? 43 : $duration.hashCode());
      Object $playDay = this.getPlayDay();
      result = result * 59 + ($playDay == null ? 43 : $playDay.hashCode());
      Object $playDate = this.getPlayDate();
      result = result * 59 + ($playDate == null ? 43 : $playDate.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $nickName = this.getNickName();
      result = result * 59 + ($nickName == null ? 43 : $nickName.hashCode());
      Object $itemName = this.getItemName();
      result = result * 59 + ($itemName == null ? 43 : $itemName.hashCode());
      Object $itemType = this.getItemType();
      result = result * 59 + ($itemType == null ? 43 : $itemType.hashCode());
      Object $clientName = this.getClientName();
      result = result * 59 + ($clientName == null ? 43 : $clientName.hashCode());
      Object $remoteAddress = this.getRemoteAddress();
      result = result * 59 + ($remoteAddress == null ? 43 : $remoteAddress.hashCode());
      Object $location = this.getLocation();
      result = result * 59 + ($location == null ? 43 : $location.hashCode());
      Object $posterUrl = this.getPosterUrl();
      result = result * 59 + ($posterUrl == null ? 43 : $posterUrl.hashCode());
      Object $rowHash = this.getRowHash();
      return result * 59 + ($rowHash == null ? 43 : $rowHash.hashCode());
   }
}
