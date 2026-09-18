package com.una.embyhub.model.dto.response.embyuserrecord;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.model.entity.EmbyInfo;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;
import org.springframework.util.StringUtils;

public class EmbyUserRegisterRecordResponse implements Serializable {
   private Long id;
   private Long userId;
   private String embyUserId;
   private String embyUserName;
   private Integer registerChannel;
   private String registerChannelName;
   private String registerChannelDetail;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;
   private String remarks;
   private Date expirationDate;
   private Integer registerDays;
   private Integer rewardDuration;
   private String rewardDurationUnit;
   private Date createDatetime;

   public void setRegisterChannel(Integer registerChannel) {
      this.registerChannel = registerChannel;
      this.registerChannelName = RegisterChannelEnum.resolveLabel(registerChannel);
   }

   public String getRegisterChannelDetail() {
      return StringUtils.hasText(this.registerChannelDetail) ? this.registerChannelDetail : this.registerChannelName;
   }

   public String getRewardDescription() {
      return this.rewardDuration != null && this.rewardDuration > 0 ? this.rewardDuration + " " + this.rewardUnitLabel(this.rewardDurationUnit) : "无奖励";
   }

   private String rewardUnitLabel(String unit) {
      return "HOUR".equalsIgnoreCase(unit) ? "小时" : "天";
   }

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
   public String getRegisterChannelName() {
      return this.registerChannelName;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
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
   public Date getCreateDatetime() {
      return this.createDatetime;
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
   public void setRegisterChannelName(final String registerChannelName) {
      this.registerChannelName = registerChannelName;
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
   public void setServerName(final String serverName) {
      this.serverName = serverName;
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
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserRegisterRecordResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
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
                                 Object this$registerChannelName = this.getRegisterChannelName();
                                 Object other$registerChannelName = other.getRegisterChannelName();
                                 if (this$registerChannelName == null
                                    ? other$registerChannelName == null
                                    : this$registerChannelName.equals(other$registerChannelName)) {
                                    Object this$registerChannelDetail = this.getRegisterChannelDetail();
                                    Object other$registerChannelDetail = other.getRegisterChannelDetail();
                                    if (this$registerChannelDetail == null
                                       ? other$registerChannelDetail == null
                                       : this$registerChannelDetail.equals(other$registerChannelDetail)) {
                                       Object this$serverName = this.getServerName();
                                       Object other$serverName = other.getServerName();
                                       if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                          Object this$remarks = this.getRemarks();
                                          Object other$remarks = other.getRemarks();
                                          if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                                             Object this$expirationDate = this.getExpirationDate();
                                             Object other$expirationDate = other.getExpirationDate();
                                             if (this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate)) {
                                                Object this$rewardDurationUnit = this.getRewardDurationUnit();
                                                Object other$rewardDurationUnit = other.getRewardDurationUnit();
                                                if (this$rewardDurationUnit == null
                                                   ? other$rewardDurationUnit == null
                                                   : this$rewardDurationUnit.equals(other$rewardDurationUnit)) {
                                                   Object this$createDatetime = this.getCreateDatetime();
                                                   Object other$createDatetime = other.getCreateDatetime();
                                                   return this$createDatetime == null
                                                      ? other$createDatetime == null
                                                      : this$createDatetime.equals(other$createDatetime);
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
      return other instanceof EmbyUserRegisterRecordResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
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
      Object $registerChannelName = this.getRegisterChannelName();
      result = result * 59 + ($registerChannelName == null ? 43 : $registerChannelName.hashCode());
      Object $registerChannelDetail = this.getRegisterChannelDetail();
      result = result * 59 + ($registerChannelDetail == null ? 43 : $registerChannelDetail.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $rewardDurationUnit = this.getRewardDurationUnit();
      result = result * 59 + ($rewardDurationUnit == null ? 43 : $rewardDurationUnit.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserRegisterRecordResponse(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", registerChannel="
         + this.getRegisterChannel()
         + ", registerChannelName="
         + this.getRegisterChannelName()
         + ", registerChannelDetail="
         + this.getRegisterChannelDetail()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
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
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
