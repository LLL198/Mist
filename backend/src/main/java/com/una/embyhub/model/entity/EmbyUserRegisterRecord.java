package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("emby_user_register_record")
public class EmbyUserRegisterRecord extends BaseEntity implements Serializable {
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
   @TableField("register_channel")
   private Integer registerChannel;
   @TableField("register_channel_detail")
   private String registerChannelDetail;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("remarks")
   private String remarks;
   @TableField("expiration_date")
   private Date expirationDate;
   @TableField("register_days")
   private Integer registerDays;
   @TableField("reward_duration")
   private Integer rewardDuration;
   @TableField("reward_duration_unit")
   private String rewardDurationUnit;
   public static final String COL_ID = "id";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_EMBY_USER_ID = "emby_user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_REGISTER_CHANNEL = "register_channel";
   public static final String COL_REGISTER_CHANNEL_DETAIL = "register_channel_detail";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_REMARKS = "remarks";
   public static final String COL_EXPIRATION_DATE = "expiration_date";
   public static final String COL_REGISTER_DAYS = "register_days";
   public static final String COL_REWARD_DURATION = "reward_duration";
   public static final String COL_REWARD_DURATION_UNIT = "reward_duration_unit";
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
   public Integer getRegisterChannel() {
      return this.registerChannel;
   }

   @Generated
   public String getRegisterChannelDetail() {
      return this.registerChannelDetail;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
   }

   @Generated
   public Integer getRegisterDays() {
      return this.registerDays;
   }

   @Generated
   public Integer getRewardDuration() {
      return this.rewardDuration;
   }

   @Generated
   public String getRewardDurationUnit() {
      return this.rewardDurationUnit;
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
   public void setRegisterChannel(final Integer registerChannel) {
      this.registerChannel = registerChannel;
   }

   @Generated
   public void setRegisterChannelDetail(final String registerChannelDetail) {
      this.registerChannelDetail = registerChannelDetail;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
   }

   @Generated
   public void setRegisterDays(final Integer registerDays) {
      this.registerDays = registerDays;
   }

   @Generated
   public void setRewardDuration(final Integer rewardDuration) {
      this.rewardDuration = rewardDuration;
   }

   @Generated
   public void setRewardDurationUnit(final String rewardDurationUnit) {
      this.rewardDurationUnit = rewardDurationUnit;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserRegisterRecord(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", registerChannel="
         + this.getRegisterChannel()
         + ", registerChannelDetail="
         + this.getRegisterChannelDetail()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", remarks="
         + this.getRemarks()
         + ", expirationDate="
         + this.getExpirationDate()
         + ", registerDays="
         + this.getRegisterDays()
         + ", rewardDuration="
         + this.getRewardDuration()
         + ", rewardDurationUnit="
         + this.getRewardDurationUnit()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserRegisterRecord other)) {
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
               Object this$registerChannel = this.getRegisterChannel();
               Object other$registerChannel = other.getRegisterChannel();
               if (this$registerChannel == null ? other$registerChannel == null : this$registerChannel.equals(other$registerChannel)) {
                  Object this$embyInfoId = this.getEmbyInfoId();
                  Object other$embyInfoId = other.getEmbyInfoId();
                  if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                     Object this$registerDays = this.getRegisterDays();
                     Object other$registerDays = other.getRegisterDays();
                     if (this$registerDays == null ? other$registerDays == null : this$registerDays.equals(other$registerDays)) {
                        Object this$rewardDuration = this.getRewardDuration();
                        Object other$rewardDuration = other.getRewardDuration();
                        if (this$rewardDuration == null ? other$rewardDuration == null : this$rewardDuration.equals(other$rewardDuration)) {
                           Object this$embyUserId = this.getEmbyUserId();
                           Object other$embyUserId = other.getEmbyUserId();
                           if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                              Object this$embyUserName = this.getEmbyUserName();
                              Object other$embyUserName = other.getEmbyUserName();
                              if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                 Object this$registerChannelDetail = this.getRegisterChannelDetail();
                                 Object other$registerChannelDetail = other.getRegisterChannelDetail();
                                 if (this$registerChannelDetail == null
                                    ? other$registerChannelDetail == null
                                    : this$registerChannelDetail.equals(other$registerChannelDetail)) {
                                    Object this$remarks = this.getRemarks();
                                    Object other$remarks = other.getRemarks();
                                    if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                                       Object this$expirationDate = this.getExpirationDate();
                                       Object other$expirationDate = other.getExpirationDate();
                                       if (this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate)) {
                                          Object this$rewardDurationUnit = this.getRewardDurationUnit();
                                          Object other$rewardDurationUnit = other.getRewardDurationUnit();
                                          return this$rewardDurationUnit == null
                                             ? other$rewardDurationUnit == null
                                             : this$rewardDurationUnit.equals(other$rewardDurationUnit);
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
      return other instanceof EmbyUserRegisterRecord;
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
      Object $registerChannel = this.getRegisterChannel();
      result = result * 59 + ($registerChannel == null ? 43 : $registerChannel.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $registerDays = this.getRegisterDays();
      result = result * 59 + ($registerDays == null ? 43 : $registerDays.hashCode());
      Object $rewardDuration = this.getRewardDuration();
      result = result * 59 + ($rewardDuration == null ? 43 : $rewardDuration.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $registerChannelDetail = this.getRegisterChannelDetail();
      result = result * 59 + ($registerChannelDetail == null ? 43 : $registerChannelDetail.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $rewardDurationUnit = this.getRewardDurationUnit();
      return result * 59 + ($rewardDurationUnit == null ? 43 : $rewardDurationUnit.hashCode());
   }
}
