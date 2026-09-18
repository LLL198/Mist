package com.una.embyhub.model.dto.request.invitation;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class InvitationCodeGenerateRequest implements Serializable {
   private Long embyInfoId;
   private Integer hostLineType;
   private Integer count;
   private Integer usageLimit;
   private Date expireDatetime;
   private Integer validityDays;
   private Integer rewardDuration;
   private String rewardDurationUnit;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public Integer getCount() {
      return this.count;
   }

   @Generated
   public Integer getUsageLimit() {
      return this.usageLimit;
   }

   @Generated
   public Date getExpireDatetime() {
      return this.expireDatetime;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setCount(final Integer count) {
      this.count = count;
   }

   @Generated
   public void setUsageLimit(final Integer usageLimit) {
      this.usageLimit = usageLimit;
   }

   @Generated
   public void setExpireDatetime(final Date expireDatetime) {
      this.expireDatetime = expireDatetime;
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
      } else if (!(o instanceof InvitationCodeGenerateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$hostLineType = this.getHostLineType();
            Object other$hostLineType = other.getHostLineType();
            if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
               Object this$count = this.getCount();
               Object other$count = other.getCount();
               if (this$count == null ? other$count == null : this$count.equals(other$count)) {
                  Object this$usageLimit = this.getUsageLimit();
                  Object other$usageLimit = other.getUsageLimit();
                  if (this$usageLimit == null ? other$usageLimit == null : this$usageLimit.equals(other$usageLimit)) {
                     Object this$validityDays = this.getValidityDays();
                     Object other$validityDays = other.getValidityDays();
                     if (this$validityDays == null ? other$validityDays == null : this$validityDays.equals(other$validityDays)) {
                        Object this$rewardDuration = this.getRewardDuration();
                        Object other$rewardDuration = other.getRewardDuration();
                        if (this$rewardDuration == null ? other$rewardDuration == null : this$rewardDuration.equals(other$rewardDuration)) {
                           Object this$expireDatetime = this.getExpireDatetime();
                           Object other$expireDatetime = other.getExpireDatetime();
                           if (this$expireDatetime == null ? other$expireDatetime == null : this$expireDatetime.equals(other$expireDatetime)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof InvitationCodeGenerateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $count = this.getCount();
      result = result * 59 + ($count == null ? 43 : $count.hashCode());
      Object $usageLimit = this.getUsageLimit();
      result = result * 59 + ($usageLimit == null ? 43 : $usageLimit.hashCode());
      Object $validityDays = this.getValidityDays();
      result = result * 59 + ($validityDays == null ? 43 : $validityDays.hashCode());
      Object $rewardDuration = this.getRewardDuration();
      result = result * 59 + ($rewardDuration == null ? 43 : $rewardDuration.hashCode());
      Object $expireDatetime = this.getExpireDatetime();
      result = result * 59 + ($expireDatetime == null ? 43 : $expireDatetime.hashCode());
      Object $rewardDurationUnit = this.getRewardDurationUnit();
      return result * 59 + ($rewardDurationUnit == null ? 43 : $rewardDurationUnit.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "InvitationCodeGenerateRequest(embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", count="
         + this.getCount()
         + ", usageLimit="
         + this.getUsageLimit()
         + ", expireDatetime="
         + this.getExpireDatetime()
         + ", validityDays="
         + this.getValidityDays()
         + ", rewardDuration="
         + this.getRewardDuration()
         + ", rewardDurationUnit="
         + this.getRewardDurationUnit()
         + ")";
   }
}
