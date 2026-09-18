package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("invitation_code")
public class InvitationCode extends BaseEntity implements Serializable {
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("code")
   private String code;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("host_line_type")
   private Integer hostLineType;
   @TableField("status")
   private Integer status;
   @TableField("usage_limit")
   private Integer usageLimit;
   @TableField("used_count")
   private Integer usedCount;
   @TableField("expire_datetime")
   private Date expireDatetime;
   @TableField("used_by")
   private String usedBy;
   @TableField("used_datetime")
   private Date usedDatetime;
   @TableField("validity_days")
   private Integer validityDays;
   @TableField("reward_duration")
   private Integer rewardDuration;
   @TableField("reward_duration_unit")
   private String rewardDurationUnit;
   public static final String COL_ID = "id";
   public static final String COL_CODE = "code";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_HOST_LINE_TYPE = "host_line_type";
   public static final String COL_STATUS = "status";
   public static final String COL_USAGE_LIMIT = "usage_limit";
   public static final String COL_USED_COUNT = "used_count";
   public static final String COL_EXPIRE_DATETIME = "expire_datetime";
   public static final String COL_USED_BY = "used_by";
   public static final String COL_USED_DATETIME = "used_datetime";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";
   public static final String COL_VALIDITY_DAYS = "validity_days";
   public static final String COL_REWARD_DURATION = "reward_duration";
   public static final String COL_REWARD_DURATION_UNIT = "reward_duration_unit";

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
   public Integer getHostLineType() {
      return this.hostLineType;
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
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
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
   public String toString() {
      return "InvitationCode(id="
         + this.getId()
         + ", code="
         + this.getCode()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
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
         + ", validityDays="
         + this.getValidityDays()
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
      } else if (!(o instanceof InvitationCode other)) {
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
                                    Object this$expireDatetime = this.getExpireDatetime();
                                    Object other$expireDatetime = other.getExpireDatetime();
                                    if (this$expireDatetime == null ? other$expireDatetime == null : this$expireDatetime.equals(other$expireDatetime)) {
                                       Object this$usedBy = this.getUsedBy();
                                       Object other$usedBy = other.getUsedBy();
                                       if (this$usedBy == null ? other$usedBy == null : this$usedBy.equals(other$usedBy)) {
                                          Object this$usedDatetime = this.getUsedDatetime();
                                          Object other$usedDatetime = other.getUsedDatetime();
                                          if (this$usedDatetime == null ? other$usedDatetime == null : this$usedDatetime.equals(other$usedDatetime)) {
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
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof InvitationCode;
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
      Object $expireDatetime = this.getExpireDatetime();
      result = result * 59 + ($expireDatetime == null ? 43 : $expireDatetime.hashCode());
      Object $usedBy = this.getUsedBy();
      result = result * 59 + ($usedBy == null ? 43 : $usedBy.hashCode());
      Object $usedDatetime = this.getUsedDatetime();
      result = result * 59 + ($usedDatetime == null ? 43 : $usedDatetime.hashCode());
      Object $rewardDurationUnit = this.getRewardDurationUnit();
      return result * 59 + ($rewardDurationUnit == null ? 43 : $rewardDurationUnit.hashCode());
   }
}
