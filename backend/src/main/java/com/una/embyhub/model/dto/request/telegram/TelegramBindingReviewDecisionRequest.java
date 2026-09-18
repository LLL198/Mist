package com.una.embyhub.model.dto.request.telegram;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

public class TelegramBindingReviewDecisionRequest implements Serializable {
   @NotNull(
      message = "审批记录不能为空"
   )
   private Long id;
   @NotNull(
      message = "审批结果不能为空"
   )
   private Boolean approved;
   @Size(
      max = 500,
      message = "审批备注不能超过500个字符"
   )
   private String remark;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Boolean getApproved() {
      return this.approved;
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
   public void setApproved(final Boolean approved) {
      this.approved = approved;
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
      } else if (!(o instanceof TelegramBindingReviewDecisionRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$approved = this.getApproved();
            Object other$approved = other.getApproved();
            if (this$approved == null ? other$approved == null : this$approved.equals(other$approved)) {
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
      return other instanceof TelegramBindingReviewDecisionRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $approved = this.getApproved();
      result = result * 59 + ($approved == null ? 43 : $approved.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindingReviewDecisionRequest(id=" + this.getId() + ", approved=" + this.getApproved() + ", remark=" + this.getRemark() + ")";
   }
}
