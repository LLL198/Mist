package com.una.embyhub.model.dto.request.scheduledtask;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class ScheduledTaskRunNowRequest implements Serializable {
   @NotBlank(
      message = "任务标识不能为空"
   )
   private String key;

   @Generated
   public String getKey() {
      return this.key;
   }

   @Generated
   public void setKey(final String key) {
      this.key = key;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ScheduledTaskRunNowRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$key = this.getKey();
         Object other$key = other.getKey();
         return this$key == null ? other$key == null : this$key.equals(other$key);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof ScheduledTaskRunNowRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $key = this.getKey();
      return result * 59 + ($key == null ? 43 : $key.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ScheduledTaskRunNowRequest(key=" + this.getKey() + ")";
   }
}
