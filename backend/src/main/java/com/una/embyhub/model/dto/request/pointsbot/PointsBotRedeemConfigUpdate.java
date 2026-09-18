package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotRedeemConfigUpdate implements Serializable {
   @NotNull(
      message = "配置ID不能为空"
   )
   @Positive(
      message = "配置ID不合法"
   )
   private Long id;
   @Size(
      min = 1,
      max = 100,
      message = "配置名称长度必须为1到100个字符"
   )
   private String configName;
   @Pattern(
      regexp = "CREATE_ACCOUNT|RENEW",
      message = "兑换类型不合法"
   )
   private String redeemType;
   @Positive(
      message = "兑换天数必须大于0"
   )
   private Integer redeemDays;
   @Positive(
      message = "Emby服务器ID不合法"
   )
   private Long embyInfoId;
   @Min(
      value = 0L,
      message = "启用状态不合法"
   )
   @Max(
      value = 1L,
      message = "启用状态不合法"
   )
   private Integer enabled;
   private Integer sort;
   @Positive(
      message = "所需积分必须大于0"
   )
   private Integer requiredPoints;
   @Size(
      max = 255,
      message = "备注不能超过255个字符"
   )
   private String remark;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getConfigName() {
      return this.configName;
   }

   @Generated
   public String getRedeemType() {
      return this.redeemType;
   }

   @Generated
   public Integer getRedeemDays() {
      return this.redeemDays;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public Integer getSort() {
      return this.sort;
   }

   @Generated
   public Integer getRequiredPoints() {
      return this.requiredPoints;
   }

   @Generated
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setConfigName(final String configName) {
      this.configName = configName;
   }

   @Generated
   public void setRedeemType(final String redeemType) {
      this.redeemType = redeemType;
   }

   @Generated
   public void setRedeemDays(final Integer redeemDays) {
      this.redeemDays = redeemDays;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setSort(final Integer sort) {
      this.sort = sort;
   }

   @Generated
   public void setRequiredPoints(final Integer requiredPoints) {
      this.requiredPoints = requiredPoints;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotRedeemConfigUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$redeemDays = this.getRedeemDays();
            Object other$redeemDays = other.getRedeemDays();
            if (this$redeemDays == null ? other$redeemDays == null : this$redeemDays.equals(other$redeemDays)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                  Object this$enabled = this.getEnabled();
                  Object other$enabled = other.getEnabled();
                  if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                     Object this$sort = this.getSort();
                     Object other$sort = other.getSort();
                     if (this$sort == null ? other$sort == null : this$sort.equals(other$sort)) {
                        Object this$requiredPoints = this.getRequiredPoints();
                        Object other$requiredPoints = other.getRequiredPoints();
                        if (this$requiredPoints == null ? other$requiredPoints == null : this$requiredPoints.equals(other$requiredPoints)) {
                           Object this$configName = this.getConfigName();
                           Object other$configName = other.getConfigName();
                           if (this$configName == null ? other$configName == null : this$configName.equals(other$configName)) {
                              Object this$redeemType = this.getRedeemType();
                              Object other$redeemType = other.getRedeemType();
                              if (this$redeemType == null ? other$redeemType == null : this$redeemType.equals(other$redeemType)) {
                                 Object this$remark = this.getRemark();
                                 Object other$remark = other.getRemark();
                                 return this$remark == null ? other$remark == null : this$remark.equals(other$remark);
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
      return other instanceof PointsBotRedeemConfigUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $redeemDays = this.getRedeemDays();
      result = result * 59 + ($redeemDays == null ? 43 : $redeemDays.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $sort = this.getSort();
      result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
      Object $requiredPoints = this.getRequiredPoints();
      result = result * 59 + ($requiredPoints == null ? 43 : $requiredPoints.hashCode());
      Object $configName = this.getConfigName();
      result = result * 59 + ($configName == null ? 43 : $configName.hashCode());
      Object $redeemType = this.getRedeemType();
      result = result * 59 + ($redeemType == null ? 43 : $redeemType.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotRedeemConfigUpdate(id="
         + this.getId()
         + ", configName="
         + this.getConfigName()
         + ", redeemType="
         + this.getRedeemType()
         + ", redeemDays="
         + this.getRedeemDays()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", enabled="
         + this.getEnabled()
         + ", sort="
         + this.getSort()
         + ", requiredPoints="
         + this.getRequiredPoints()
         + ", remark="
         + this.getRemark()
         + ")";
   }
}
