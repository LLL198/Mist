package com.una.embyhub.model.dto.response.embyuserrecord;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.config.common.enums.RenewChannelEnum;
import com.una.embyhub.model.entity.EmbyInfo;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;
import org.springframework.util.StringUtils;

public class EmbyUserRenewRecordResponse implements Serializable {
   private Long id;
   private Long userId;
   private String embyUserId;
   private String embyUserName;
   private Integer renewChannel;
   private String renewChannelName;
   private String renewChannelDetail;
   private Integer renewDays;
   private Date expirationDateBefore;
   private Date expirationDateAfter;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;
   private Date createDatetime;

   public void setRenewChannel(Integer renewChannel) {
      this.renewChannel = renewChannel;
      this.renewChannelName = RenewChannelEnum.resolveLabel(renewChannel);
   }

   public String getRenewChannelDetail() {
      return StringUtils.hasText(this.renewChannelDetail) ? this.renewChannelDetail : this.renewChannelName;
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
   public Integer getRenewChannel() {
      return this.renewChannel;
   }

   @Generated
   public String getRenewChannelName() {
      return this.renewChannelName;
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
   public String getServerName() {
      return this.serverName;
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
   public void setRenewChannelName(final String renewChannelName) {
      this.renewChannelName = renewChannelName;
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
   public void setServerName(final String serverName) {
      this.serverName = serverName;
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
      } else if (!(o instanceof EmbyUserRenewRecordResponse other)) {
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
                              Object this$renewChannelName = this.getRenewChannelName();
                              Object other$renewChannelName = other.getRenewChannelName();
                              if (this$renewChannelName == null ? other$renewChannelName == null : this$renewChannelName.equals(other$renewChannelName)) {
                                 Object this$renewChannelDetail = this.getRenewChannelDetail();
                                 Object other$renewChannelDetail = other.getRenewChannelDetail();
                                 if (this$renewChannelDetail == null
                                    ? other$renewChannelDetail == null
                                    : this$renewChannelDetail.equals(other$renewChannelDetail)) {
                                    Object this$expirationDateBefore = this.getExpirationDateBefore();
                                    Object other$expirationDateBefore = other.getExpirationDateBefore();
                                    if (this$expirationDateBefore == null
                                       ? other$expirationDateBefore == null
                                       : this$expirationDateBefore.equals(other$expirationDateBefore)) {
                                       Object this$expirationDateAfter = this.getExpirationDateAfter();
                                       Object other$expirationDateAfter = other.getExpirationDateAfter();
                                       if (this$expirationDateAfter == null
                                          ? other$expirationDateAfter == null
                                          : this$expirationDateAfter.equals(other$expirationDateAfter)) {
                                          Object this$serverName = this.getServerName();
                                          Object other$serverName = other.getServerName();
                                          if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof EmbyUserRenewRecordResponse;
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
      Object $renewChannelName = this.getRenewChannelName();
      result = result * 59 + ($renewChannelName == null ? 43 : $renewChannelName.hashCode());
      Object $renewChannelDetail = this.getRenewChannelDetail();
      result = result * 59 + ($renewChannelDetail == null ? 43 : $renewChannelDetail.hashCode());
      Object $expirationDateBefore = this.getExpirationDateBefore();
      result = result * 59 + ($expirationDateBefore == null ? 43 : $expirationDateBefore.hashCode());
      Object $expirationDateAfter = this.getExpirationDateAfter();
      result = result * 59 + ($expirationDateAfter == null ? 43 : $expirationDateAfter.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserRenewRecordResponse(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", renewChannel="
         + this.getRenewChannel()
         + ", renewChannelName="
         + this.getRenewChannelName()
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
         + ", serverName="
         + this.getServerName()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
