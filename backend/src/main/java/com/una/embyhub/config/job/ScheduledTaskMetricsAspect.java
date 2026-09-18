package com.una.embyhub.config.job;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.Generated;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ScheduledTaskMetricsAspect {
   private final ConcurrentMap<String, ScheduledTaskMetricsAspect.TaskMetric> metrics = new ConcurrentHashMap<>();

   @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
   public Object record(ProceedingJoinPoint pjp) throws Throwable {
      MethodSignature sig = (MethodSignature)pjp.getSignature();
      Method methodOnProxy = sig.getMethod();
      Class<?> targetClass = pjp.getTarget().getClass();
      Method specificMethod = AopUtils.getMostSpecificMethod(methodOnProxy, targetClass);
      String key = targetClass.getName() + "#" + specificMethod.getName();
      ScheduledTaskMeta meta = AnnotatedElementUtils.findMergedAnnotation(specificMethod, ScheduledTaskMeta.class);
      String taskName = meta != null && !meta.name().isBlank() ? meta.name() : targetClass.getSimpleName() + "#" + specificMethod.getName();
      String taskRemark = meta != null ? meta.remark() : "";
      long start = System.currentTimeMillis();
      Date startDate = new Date(start);
      ScheduledTaskMetricsAspect.TaskMetric metric = this.metrics.computeIfAbsent(key, k -> new ScheduledTaskMetricsAspect.TaskMetric());
      metric.setTaskName(taskName);
      metric.setTaskRemark(taskRemark);
      metric.setLastStartTime(startDate);

      try {
         Object ret = pjp.proceed();
         long end = System.currentTimeMillis();
         metric.setLastEndTime(new Date(end));
         metric.setLastDurationMs(end - start);
         metric.setRunCount(metric.getRunCount() + 1L);
         metric.setLastError(null);
         return ret;
      } catch (Throwable var17) {
         long endx = System.currentTimeMillis();
         metric.setLastEndTime(new Date(endx));
         metric.setLastDurationMs(endx - start);
         metric.setRunCount(metric.getRunCount() + 1L);
         metric.setLastError(var17.getClass().getSimpleName() + ": " + var17.getMessage());
         throw var17;
      }
   }

   public Map<String, ScheduledTaskMetricsAspect.TaskMetric> getMetrics() {
      return this.metrics;
   }

   public static class TaskMetric {
      private String taskName;
      private String taskRemark;
      private Date lastStartTime;
      private Date lastEndTime;
      private long lastDurationMs;
      private long runCount;
      private String lastError;

      @Generated
      public String getTaskName() {
         return this.taskName;
      }

      @Generated
      public String getTaskRemark() {
         return this.taskRemark;
      }

      @Generated
      public Date getLastStartTime() {
         return this.lastStartTime;
      }

      @Generated
      public Date getLastEndTime() {
         return this.lastEndTime;
      }

      @Generated
      public long getLastDurationMs() {
         return this.lastDurationMs;
      }

      @Generated
      public long getRunCount() {
         return this.runCount;
      }

      @Generated
      public String getLastError() {
         return this.lastError;
      }

      @Generated
      public void setTaskName(final String taskName) {
         this.taskName = taskName;
      }

      @Generated
      public void setTaskRemark(final String taskRemark) {
         this.taskRemark = taskRemark;
      }

      @Generated
      public void setLastStartTime(final Date lastStartTime) {
         this.lastStartTime = lastStartTime;
      }

      @Generated
      public void setLastEndTime(final Date lastEndTime) {
         this.lastEndTime = lastEndTime;
      }

      @Generated
      public void setLastDurationMs(final long lastDurationMs) {
         this.lastDurationMs = lastDurationMs;
      }

      @Generated
      public void setRunCount(final long runCount) {
         this.runCount = runCount;
      }

      @Generated
      public void setLastError(final String lastError) {
         this.lastError = lastError;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof ScheduledTaskMetricsAspect.TaskMetric other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getLastDurationMs() != other.getLastDurationMs()) {
            return false;
         } else if (this.getRunCount() != other.getRunCount()) {
            return false;
         } else {
            Object this$taskName = this.getTaskName();
            Object other$taskName = other.getTaskName();
            if (this$taskName == null ? other$taskName == null : this$taskName.equals(other$taskName)) {
               Object this$taskRemark = this.getTaskRemark();
               Object other$taskRemark = other.getTaskRemark();
               if (this$taskRemark == null ? other$taskRemark == null : this$taskRemark.equals(other$taskRemark)) {
                  Object this$lastStartTime = this.getLastStartTime();
                  Object other$lastStartTime = other.getLastStartTime();
                  if (this$lastStartTime == null ? other$lastStartTime == null : this$lastStartTime.equals(other$lastStartTime)) {
                     Object this$lastEndTime = this.getLastEndTime();
                     Object other$lastEndTime = other.getLastEndTime();
                     if (this$lastEndTime == null ? other$lastEndTime == null : this$lastEndTime.equals(other$lastEndTime)) {
                        Object this$lastError = this.getLastError();
                        Object other$lastError = other.getLastError();
                        return this$lastError == null ? other$lastError == null : this$lastError.equals(other$lastError);
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
         return other instanceof ScheduledTaskMetricsAspect.TaskMetric;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         long $lastDurationMs = this.getLastDurationMs();
         result = result * 59 + (int)($lastDurationMs >>> 32 ^ $lastDurationMs);
         long $runCount = this.getRunCount();
         result = result * 59 + (int)($runCount >>> 32 ^ $runCount);
         Object $taskName = this.getTaskName();
         result = result * 59 + ($taskName == null ? 43 : $taskName.hashCode());
         Object $taskRemark = this.getTaskRemark();
         result = result * 59 + ($taskRemark == null ? 43 : $taskRemark.hashCode());
         Object $lastStartTime = this.getLastStartTime();
         result = result * 59 + ($lastStartTime == null ? 43 : $lastStartTime.hashCode());
         Object $lastEndTime = this.getLastEndTime();
         result = result * 59 + ($lastEndTime == null ? 43 : $lastEndTime.hashCode());
         Object $lastError = this.getLastError();
         return result * 59 + ($lastError == null ? 43 : $lastError.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "ScheduledTaskMetricsAspect.TaskMetric(taskName="
            + this.getTaskName()
            + ", taskRemark="
            + this.getTaskRemark()
            + ", lastStartTime="
            + this.getLastStartTime()
            + ", lastEndTime="
            + this.getLastEndTime()
            + ", lastDurationMs="
            + this.getLastDurationMs()
            + ", runCount="
            + this.getRunCount()
            + ", lastError="
            + this.getLastError()
            + ")";
      }
   }
}
