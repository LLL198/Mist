package com.una.embyhub.model.dto.request.requestlist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class RequestListReject implements Serializable {
   @NotEmpty(
      message = "求片列表id不能为空"
   )
   private List<Long> requestListIdList;
   @Size(
      max = 255,
      message = "拒绝理由不能超过255个字符"
   )
   private String remark;
   private Long embyInfoId;

   @Generated
   public List<Long> getRequestListIdList() {
      return this.requestListIdList;
   }

   @Generated
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public void setRequestListIdList(final List<Long> requestListIdList) {
      this.requestListIdList = requestListIdList;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestListReject other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$requestListIdList = this.getRequestListIdList();
            Object other$requestListIdList = other.getRequestListIdList();
            if (this$requestListIdList == null ? other$requestListIdList == null : this$requestListIdList.equals(other$requestListIdList)) {
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
      return other instanceof RequestListReject;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $requestListIdList = this.getRequestListIdList();
      result = result * 59 + ($requestListIdList == null ? 43 : $requestListIdList.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestListReject(requestListIdList="
         + this.getRequestListIdList()
         + ", remark="
         + this.getRemark()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }
}
