package com.una.embyhub.model.dto.request.pointsbot;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotUserAdjustRequest implements Serializable {
   @NotNull(
      message = "积分用户ID不能为空"
   )
   @Positive(
      message = "积分用户ID不合法"
   )
   private Long id;
   @NotNull(
      message = "积分调整值不能为空"
   )
   @Min(
      value = -2147483648L,
      message = "积分调整值不合法"
   )
   @Max(
      value = 2147483647L,
      message = "积分调整值不合法"
   )
   private Integer delta;
   @NotBlank(
      message = "请填写调整说明"
   )
   @Size(
      max = 100,
      message = "调整说明不能超过100个字符"
   )
   private String remark;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Integer getDelta() {
      return this.delta;
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
   public void setDelta(final Integer delta) {
      this.delta = delta;
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
      } else if (!(o instanceof PointsBotUserAdjustRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$delta = this.getDelta();
            Object other$delta = other.getDelta();
            if (this$delta == null ? other$delta == null : this$delta.equals(other$delta)) {
               Object this$remark = this.getRemark();
               Object other$remark = other.getRemark();
               return this$remark == null ? other$remark == null : this$remark.equals(other$remark);
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
      return other instanceof PointsBotUserAdjustRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $delta = this.getDelta();
      result = result * 59 + ($delta == null ? 43 : $delta.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotUserAdjustRequest(id=" + this.getId() + ", delta=" + this.getDelta() + ", remark=" + this.getRemark() + ")";
   }
}
