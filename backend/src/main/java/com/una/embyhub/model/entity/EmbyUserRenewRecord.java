package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("emby_user_renew_record")
public class EmbyUserRenewRecord extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_id")
   private String embyUserId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("renew_channel")
   private Integer renewChannel;
   @TableField("renew_channel_detail")
   private String renewChannelDetail;
   @TableField("renew_days")
   private Integer renewDays;
   @TableField("expiration_date_before")
   private Date expirationDateBefore;
   @TableField("expiration_date_after")
   private Date expirationDateAfter;
   @TableField("emby_info_id")
   private Long embyInfoId;
   public static final String COL_ID = "id";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_EMBY_USER_ID = "emby_user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_RENEW_CHANNEL = "renew_channel";
   public static final String COL_RENEW_CHANNEL_DETAIL = "renew_channel_detail";
   public static final String COL_RENEW_DAYS = "renew_days";
   public static final String COL_EXPIRATION_DATE_BEFORE = "expiration_date_before";
   public static final String COL_EXPIRATION_DATE_AFTER = "expiration_date_after";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_CREATE_DATETIME = "create_datetime";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Integer getRenewChannel() {
      return this.renewChannel;
   }

   @Generated
   public String getRenewChannelDetail() {
      return this.renewChannelDetail;
   }

   @Generated
   public Integer getRenewDays() {
      return this.renewDays;
   }

   @Generated
   public Date getExpirationDateBefore() {
      return this.expirationDateBefore;
   }

   @Generated
   public Date getExpirationDateAfter() {
      return this.expirationDateAfter;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setRenewChannel(final Integer renewChannel) {
      this.renewChannel = renewChannel;
   }

   @Generated
   public void setRenewChannelDetail(final String renewChannelDetail) {
      this.renewChannelDetail = renewChannelDetail;
   }

   @Generated
   public void setRenewDays(final Integer renewDays) {
      this.renewDays = renewDays;
   }

   @Generated
   public void setExpirationDateBefore(final Date expirationDateBefore) {
      this.expirationDateBefore = expirationDateBefore;
   }

   @Generated
   public void setExpirationDateAfter(final Date expirationDateAfter) {
      this.expirationDateAfter = expirationDateAfter;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserRenewRecord(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", renewChannel="
         + this.getRenewChannel()
         + ", renewChannelDetail="
         + this.getRenewChannelDetail()
         + ", renewDays="
         + this.getRenewDays()
         + ", expirationDateBefore="
         + this.getExpirationDateBefore()
         + ", expirationDateAfter="
         + this.getExpirationDateAfter()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserRenewRecord other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$renewChannel = this.getRenewChannel();
               Object other$renewChannel = other.getRenewChannel();
               if (this$renewChannel == null ? other$renewChannel == null : this$renewChannel.equals(other$renewChannel)) {
                  Object this$renewDays = this.getRenewDays();
                  Object other$renewDays = other.getRenewDays();
                  if (this$renewDays == null ? other$renewDays == null : this$renewDays.equals(other$renewDays)) {
                     Object this$embyInfoId = this.getEmbyInfoId();
                     Object other$embyInfoId = other.getEmbyInfoId();
                     if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                        Object this$embyUserId = this.getEmbyUserId();
                        Object other$embyUserId = other.getEmbyUserId();
                        if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                           Object this$embyUserName = this.getEmbyUserName();
                           Object other$embyUserName = other.getEmbyUserName();
                           if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                              Object this$renewChannelDetail = this.getRenewChannelDetail();
                              Object other$renewChannelDetail = other.getRenewChannelDetail();
                              if (this$renewChannelDetail == null ? other$renewChannelDetail == null : this$renewChannelDetail.equals(other$renewChannelDetail)
                                 )
                               {
                                 Object this$expirationDateBefore = this.getExpirationDateBefore();
                                 Object other$expirationDateBefore = other.getExpirationDateBefore();
                                 if (this$expirationDateBefore == null
                                    ? other$expirationDateBefore == null
                                    : this$expirationDateBefore.equals(other$expirationDateBefore)) {
                                    Object this$expirationDateAfter = this.getExpirationDateAfter();
                                    Object other$expirationDateAfter = other.getExpirationDateAfter();
                                    return this$expirationDateAfter == null
                                       ? other$expirationDateAfter == null
                                       : this$expirationDateAfter.equals(other$expirationDateAfter);
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
      return other instanceof EmbyUserRenewRecord;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $renewChannel = this.getRenewChannel();
      result = result * 59 + ($renewChannel == null ? 43 : $renewChannel.hashCode());
      Object $renewDays = this.getRenewDays();
      result = result * 59 + ($renewDays == null ? 43 : $renewDays.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $renewChannelDetail = this.getRenewChannelDetail();
      result = result * 59 + ($renewChannelDetail == null ? 43 : $renewChannelDetail.hashCode());
      Object $expirationDateBefore = this.getExpirationDateBefore();
      result = result * 59 + ($expirationDateBefore == null ? 43 : $expirationDateBefore.hashCode());
      Object $expirationDateAfter = this.getExpirationDateAfter();
      return result * 59 + ($expirationDateAfter == null ? 43 : $expirationDateAfter.hashCode());
   }
}
