package com.una.embyhub.model.dto.request.scheduledtask;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class ScheduledTaskCronBatchResetRequest implements Serializable {
   @NotEmpty(
      message = "任务标识列表不能为空"
   )
   private List<String> keys;

   @Generated
   public List<String> getKeys() {
      return this.keys;
   }

   @Generated
   public void setKeys(final List<String> keys) {
      this.keys = keys;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ScheduledTaskCronBatchResetRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$keys = this.getKeys();
         Object other$keys = other.getKeys();
         return this$keys == null ? other$keys == null : this$keys.equals(other$keys);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof ScheduledTaskCronBatchResetRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $keys = this.getKeys();
      return result * 59 + ($keys == null ? 43 : $keys.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ScheduledTaskCronBatchResetRequest(keys=" + this.getKeys() + ")";
   }
}
