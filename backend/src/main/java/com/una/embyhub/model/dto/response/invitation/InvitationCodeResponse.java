package com.una.embyhub.model.dto.response.invitation;

import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class InvitationCodeResponse implements Serializable {
   private Long id;
   private String code;
   private Long embyInfoId;
   private String serverName;
   private Integer hostLineType;
   private String hostLineTypeName;
   private Integer status;
   private Integer usageLimit;
   private Integer usedCount;
   private Date expireDatetime;
   private String usedBy;
   private Date usedDatetime;
   private Date createDatetime;
   private String createUserName;
   private Integer validityDays;
   private Integer rewardDuration;
   private String rewardDurationUnit;

   public void setHostLineType(Integer hostLineType) {
      this.hostLineType = HostLineTypeEnum.normalize(hostLineType);
      this.hostLineTypeName = HostLineTypeEnum.resolveLabel(hostLineType);
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
   public String getCode() {
      return this.code;
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
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public String getHostLineTypeName() {
      return this.hostLineTypeName;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public Integer getUsageLimit() {
      return this.usageLimit;
   }

   @Generated
   public Integer getUsedCount() {
      return this.usedCount;
   }

   @Generated
   public Date getExpireDatetime() {
      return this.expireDatetime;
   }

   @Generated
   public String getUsedBy() {
      return this.usedBy;
   }

   @Generated
   public Date getUsedDatetime() {
      return this.usedDatetime;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public String getCreateUserName() {
      return this.createUserName;
   }

   @Generated
   public Integer getValidityDays() {
      return this.validityDays;
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
   public void setCode(final String code) {
      this.code = code;
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
   public void setHostLineTypeName(final String hostLineTypeName) {
      this.hostLineTypeName = hostLineTypeName;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setUsageLimit(final Integer usageLimit) {
      this.usageLimit = usageLimit;
   }

   @Generated
   public void setUsedCount(final Integer usedCount) {
      this.usedCount = usedCount;
   }

   @Generated
   public void setExpireDatetime(final Date expireDatetime) {
      this.expireDatetime = expireDatetime;
   }

   @Generated
   public void setUsedBy(final String usedBy) {
      this.usedBy = usedBy;
   }

   @Generated
   public void setUsedDatetime(final Date usedDatetime) {
      this.usedDatetime = usedDatetime;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setCreateUserName(final String createUserName) {
      this.createUserName = createUserName;
   }

   @Generated
   public void setValidityDays(final Integer validityDays) {
      this.validityDays = validityDays;
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
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof InvitationCodeResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$hostLineType = this.getHostLineType();
               Object other$hostLineType = other.getHostLineType();
               if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                  Object this$status = this.getStatus();
                  Object other$status = other.getStatus();
                  if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                     Object this$usageLimit = this.getUsageLimit();
                     Object other$usageLimit = other.getUsageLimit();
                     if (this$usageLimit == null ? other$usageLimit == null : this$usageLimit.equals(other$usageLimit)) {
                        Object this$usedCount = this.getUsedCount();
                        Object other$usedCount = other.getUsedCount();
                        if (this$usedCount == null ? other$usedCount == null : this$usedCount.equals(other$usedCount)) {
                           Object this$validityDays = this.getValidityDays();
                           Object other$validityDays = other.getValidityDays();
                           if (this$validityDays == null ? other$validityDays == null : this$validityDays.equals(other$validityDays)) {
                              Object this$rewardDuration = this.getRewardDuration();
                              Object other$rewardDuration = other.getRewardDuration();
                              if (this$rewardDuration == null ? other$rewardDuration == null : this$rewardDuration.equals(other$rewardDuration)) {
                                 Object this$code = this.getCode();
                                 Object other$code = other.getCode();
                                 if (this$code == null ? other$code == null : this$code.equals(other$code)) {
                                    Object this$serverName = this.getServerName();
                                    Object other$serverName = other.getServerName();
                                    if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                       Object this$hostLineTypeName = this.getHostLineTypeName();
                                       Object other$hostLineTypeName = other.getHostLineTypeName();
                                       if (this$hostLineTypeName == null
                                          ? other$hostLineTypeName == null
                                          : this$hostLineTypeName.equals(other$hostLineTypeName)) {
                                          Object this$expireDatetime = this.getExpireDatetime();
                                          Object other$expireDatetime = other.getExpireDatetime();
                                          if (this$expireDatetime == null ? other$expireDatetime == null : this$expireDatetime.equals(other$expireDatetime)) {
                                             Object this$usedBy = this.getUsedBy();
                                             Object other$usedBy = other.getUsedBy();
                                             if (this$usedBy == null ? other$usedBy == null : this$usedBy.equals(other$usedBy)) {
                                                Object this$usedDatetime = this.getUsedDatetime();
                                                Object other$usedDatetime = other.getUsedDatetime();
                                                if (this$usedDatetime == null ? other$usedDatetime == null : this$usedDatetime.equals(other$usedDatetime)) {
                                                   Object this$createDatetime = this.getCreateDatetime();
                                                   Object other$createDatetime = other.getCreateDatetime();
                                                   if (this$createDatetime == null
                                                      ? other$createDatetime == null
                                                      : this$createDatetime.equals(other$createDatetime)) {
                                                      Object this$createUserName = this.getCreateUserName();
                                                      Object other$createUserName = other.getCreateUserName();
                                                      if (this$createUserName == null
                                                         ? other$createUserName == null
                                                         : this$createUserName.equals(other$createUserName)) {
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
      return other instanceof InvitationCodeResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $usageLimit = this.getUsageLimit();
      result = result * 59 + ($usageLimit == null ? 43 : $usageLimit.hashCode());
      Object $usedCount = this.getUsedCount();
      result = result * 59 + ($usedCount == null ? 43 : $usedCount.hashCode());
      Object $validityDays = this.getValidityDays();
      result = result * 59 + ($validityDays == null ? 43 : $validityDays.hashCode());
      Object $rewardDuration = this.getRewardDuration();
      result = result * 59 + ($rewardDuration == null ? 43 : $rewardDuration.hashCode());
      Object $code = this.getCode();
      result = result * 59 + ($code == null ? 43 : $code.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $hostLineTypeName = this.getHostLineTypeName();
      result = result * 59 + ($hostLineTypeName == null ? 43 : $hostLineTypeName.hashCode());
      Object $expireDatetime = this.getExpireDatetime();
      result = result * 59 + ($expireDatetime == null ? 43 : $expireDatetime.hashCode());
      Object $usedBy = this.getUsedBy();
      result = result * 59 + ($usedBy == null ? 43 : $usedBy.hashCode());
      Object $usedDatetime = this.getUsedDatetime();
      result = result * 59 + ($usedDatetime == null ? 43 : $usedDatetime.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $rewardDurationUnit = this.getRewardDurationUnit();
      return result * 59 + ($rewardDurationUnit == null ? 43 : $rewardDurationUnit.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "InvitationCodeResponse(id="
         + this.getId()
         + ", code="
         + this.getCode()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", hostLineType="
         + this.getHostLineType()
         + ", hostLineTypeName="
         + this.getHostLineTypeName()
         + ", status="
         + this.getStatus()
         + ", usageLimit="
         + this.getUsageLimit()
         + ", usedCount="
         + this.getUsedCount()
         + ", expireDatetime="
         + this.getExpireDatetime()
         + ", usedBy="
         + this.getUsedBy()
         + ", usedDatetime="
         + this.getUsedDatetime()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", validityDays="
         + this.getValidityDays()
         + ", rewardDuration="
         + this.getRewardDuration()
         + ", rewardDurationUnit="
         + this.getRewardDurationUnit()
         + ")";
   }
}
