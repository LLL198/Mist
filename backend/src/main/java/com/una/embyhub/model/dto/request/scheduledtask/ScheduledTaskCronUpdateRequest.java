package com.una.embyhub.model.dto.request.scheduledtask;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class ScheduledTaskCronUpdateRequest implements Serializable {
   @NotBlank(
      message = "任务标识不能为空"
   )
   private String key;
   @NotBlank(
      message = "Cron表达式不能为空"
   )
   private String cron;

   @Generated
   public String getKey() {
      return this.key;
   }

   @Generated
   public String getCron() {
      return this.cron;
   }

   @Generated
   public void setKey(final String key) {
      this.key = key;
   }

   @Generated
   public void setCron(final String cron) {
      this.cron = cron;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ScheduledTaskCronUpdateRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$key = this.getKey();
         Object other$key = other.getKey();
         if (this$key == null ? other$key == null : this$key.equals(other$key)) {
            Object this$cron = this.getCron();
            Object other$cron = other.getCron();
            return this$cron == null ? other$cron == null : this$cron.equals(other$cron);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof ScheduledTaskCronUpdateRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $key = this.getKey();
      result = result * 59 + ($key == null ? 43 : $key.hashCode());
      Object $cron = this.getCron();
      return result * 59 + ($cron == null ? 43 : $cron.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ScheduledTaskCronUpdateRequest(key=" + this.getKey() + ", cron=" + this.getCron() + ")";
   }
}
