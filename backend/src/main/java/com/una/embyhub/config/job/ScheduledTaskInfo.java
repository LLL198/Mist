package com.una.embyhub.config.job;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class ScheduledTaskInfo implements Serializable {
   private String key;
   private String taskName;
   private String taskRemark;
   private String type;
   private String beanClass;
   private String methodName;
   private String cron;
   private Long fixedDelayMs;
   private Long fixedRateMs;
   private Long initialDelayMs;
   private Date currentExecutionTime;
   private Date lastEndTime;
   private Long lastDurationMs;
   private Long runCount;
   private String lastError;
   private Date nextExecutionTime;

   @Generated
   public String getKey() {
      return this.key;
   }

   @Generated
   public String getTaskName() {
      return this.taskName;
   }

   @Generated
   public String getTaskRemark() {
      return this.taskRemark;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getBeanClass() {
      return this.beanClass;
   }

   @Generated
   public String getMethodName() {
      return this.methodName;
   }

   @Generated
   public String getCron() {
      return this.cron;
   }

   @Generated
   public Long getFixedDelayMs() {
      return this.fixedDelayMs;
   }

   @Generated
   public Long getFixedRateMs() {
      return this.fixedRateMs;
   }

   @Generated
   public Long getInitialDelayMs() {
      return this.initialDelayMs;
   }

   @Generated
   public Date getCurrentExecutionTime() {
      return this.currentExecutionTime;
   }

   @Generated
   public Date getLastEndTime() {
      return this.lastEndTime;
   }

   @Generated
   public Long getLastDurationMs() {
      return this.lastDurationMs;
   }

   @Generated
   public Long getRunCount() {
      return this.runCount;
   }

   @Generated
   public String getLastError() {
      return this.lastError;
   }

   @Generated
   public Date getNextExecutionTime() {
      return this.nextExecutionTime;
   }

   @Generated
   public void setKey(final String key) {
      this.key = key;
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
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setBeanClass(final String beanClass) {
      this.beanClass = beanClass;
   }

   @Generated
   public void setMethodName(final String methodName) {
      this.methodName = methodName;
   }

   @Generated
   public void setCron(final String cron) {
      this.cron = cron;
   }

   @Generated
   public void setFixedDelayMs(final Long fixedDelayMs) {
      this.fixedDelayMs = fixedDelayMs;
   }

   @Generated
   public void setFixedRateMs(final Long fixedRateMs) {
      this.fixedRateMs = fixedRateMs;
   }

   @Generated
   public void setInitialDelayMs(final Long initialDelayMs) {
      this.initialDelayMs = initialDelayMs;
   }

   @Generated
   public void setCurrentExecutionTime(final Date currentExecutionTime) {
      this.currentExecutionTime = currentExecutionTime;
   }

   @Generated
   public void setLastEndTime(final Date lastEndTime) {
      this.lastEndTime = lastEndTime;
   }

   @Generated
   public void setLastDurationMs(final Long lastDurationMs) {
      this.lastDurationMs = lastDurationMs;
   }

   @Generated
   public void setRunCount(final Long runCount) {
      this.runCount = runCount;
   }

   @Generated
   public void setLastError(final String lastError) {
      this.lastError = lastError;
   }

   @Generated
   public void setNextExecutionTime(final Date nextExecutionTime) {
      this.nextExecutionTime = nextExecutionTime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ScheduledTaskInfo other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$fixedDelayMs = this.getFixedDelayMs();
         Object other$fixedDelayMs = other.getFixedDelayMs();
         if (this$fixedDelayMs == null ? other$fixedDelayMs == null : this$fixedDelayMs.equals(other$fixedDelayMs)) {
            Object this$fixedRateMs = this.getFixedRateMs();
            Object other$fixedRateMs = other.getFixedRateMs();
            if (this$fixedRateMs == null ? other$fixedRateMs == null : this$fixedRateMs.equals(other$fixedRateMs)) {
               Object this$initialDelayMs = this.getInitialDelayMs();
               Object other$initialDelayMs = other.getInitialDelayMs();
               if (this$initialDelayMs == null ? other$initialDelayMs == null : this$initialDelayMs.equals(other$initialDelayMs)) {
                  Object this$lastDurationMs = this.getLastDurationMs();
                  Object other$lastDurationMs = other.getLastDurationMs();
                  if (this$lastDurationMs == null ? other$lastDurationMs == null : this$lastDurationMs.equals(other$lastDurationMs)) {
                     Object this$runCount = this.getRunCount();
                     Object other$runCount = other.getRunCount();
                     if (this$runCount == null ? other$runCount == null : this$runCount.equals(other$runCount)) {
                        Object this$key = this.getKey();
                        Object other$key = other.getKey();
                        if (this$key == null ? other$key == null : this$key.equals(other$key)) {
                           Object this$taskName = this.getTaskName();
                           Object other$taskName = other.getTaskName();
                           if (this$taskName == null ? other$taskName == null : this$taskName.equals(other$taskName)) {
                              Object this$taskRemark = this.getTaskRemark();
                              Object other$taskRemark = other.getTaskRemark();
                              if (this$taskRemark == null ? other$taskRemark == null : this$taskRemark.equals(other$taskRemark)) {
                                 Object this$type = this.getType();
                                 Object other$type = other.getType();
                                 if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                    Object this$beanClass = this.getBeanClass();
                                    Object other$beanClass = other.getBeanClass();
                                    if (this$beanClass == null ? other$beanClass == null : this$beanClass.equals(other$beanClass)) {
                                       Object this$methodName = this.getMethodName();
                                       Object other$methodName = other.getMethodName();
                                       if (this$methodName == null ? other$methodName == null : this$methodName.equals(other$methodName)) {
                                          Object this$cron = this.getCron();
                                          Object other$cron = other.getCron();
                                          if (this$cron == null ? other$cron == null : this$cron.equals(other$cron)) {
                                             Object this$currentExecutionTime = this.getCurrentExecutionTime();
                                             Object other$currentExecutionTime = other.getCurrentExecutionTime();
                                             if (this$currentExecutionTime == null
                                                ? other$currentExecutionTime == null
                                                : this$currentExecutionTime.equals(other$currentExecutionTime)) {
                                                Object this$lastEndTime = this.getLastEndTime();
                                                Object other$lastEndTime = other.getLastEndTime();
                                                if (this$lastEndTime == null ? other$lastEndTime == null : this$lastEndTime.equals(other$lastEndTime)) {
                                                   Object this$lastError = this.getLastError();
                                                   Object other$lastError = other.getLastError();
                                                   if (this$lastError == null ? other$lastError == null : this$lastError.equals(other$lastError)) {
                                                      Object this$nextExecutionTime = this.getNextExecutionTime();
                                                      Object other$nextExecutionTime = other.getNextExecutionTime();
                                                      return this$nextExecutionTime == null
                                                         ? other$nextExecutionTime == null
                                                         : this$nextExecutionTime.equals(other$nextExecutionTime);
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
      return other instanceof ScheduledTaskInfo;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $fixedDelayMs = this.getFixedDelayMs();
      result = result * 59 + ($fixedDelayMs == null ? 43 : $fixedDelayMs.hashCode());
      Object $fixedRateMs = this.getFixedRateMs();
      result = result * 59 + ($fixedRateMs == null ? 43 : $fixedRateMs.hashCode());
      Object $initialDelayMs = this.getInitialDelayMs();
      result = result * 59 + ($initialDelayMs == null ? 43 : $initialDelayMs.hashCode());
      Object $lastDurationMs = this.getLastDurationMs();
      result = result * 59 + ($lastDurationMs == null ? 43 : $lastDurationMs.hashCode());
      Object $runCount = this.getRunCount();
      result = result * 59 + ($runCount == null ? 43 : $runCount.hashCode());
      Object $key = this.getKey();
      result = result * 59 + ($key == null ? 43 : $key.hashCode());
      Object $taskName = this.getTaskName();
      result = result * 59 + ($taskName == null ? 43 : $taskName.hashCode());
      Object $taskRemark = this.getTaskRemark();
      result = result * 59 + ($taskRemark == null ? 43 : $taskRemark.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $beanClass = this.getBeanClass();
      result = result * 59 + ($beanClass == null ? 43 : $beanClass.hashCode());
      Object $methodName = this.getMethodName();
      result = result * 59 + ($methodName == null ? 43 : $methodName.hashCode());
      Object $cron = this.getCron();
      result = result * 59 + ($cron == null ? 43 : $cron.hashCode());
      Object $currentExecutionTime = this.getCurrentExecutionTime();
      result = result * 59 + ($currentExecutionTime == null ? 43 : $currentExecutionTime.hashCode());
      Object $lastEndTime = this.getLastEndTime();
      result = result * 59 + ($lastEndTime == null ? 43 : $lastEndTime.hashCode());
      Object $lastError = this.getLastError();
      result = result * 59 + ($lastError == null ? 43 : $lastError.hashCode());
      Object $nextExecutionTime = this.getNextExecutionTime();
      return result * 59 + ($nextExecutionTime == null ? 43 : $nextExecutionTime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ScheduledTaskInfo(key="
         + this.getKey()
         + ", taskName="
         + this.getTaskName()
         + ", taskRemark="
         + this.getTaskRemark()
         + ", type="
         + this.getType()
         + ", beanClass="
         + this.getBeanClass()
         + ", methodName="
         + this.getMethodName()
         + ", cron="
         + this.getCron()
         + ", fixedDelayMs="
         + this.getFixedDelayMs()
         + ", fixedRateMs="
         + this.getFixedRateMs()
         + ", initialDelayMs="
         + this.getInitialDelayMs()
         + ", currentExecutionTime="
         + this.getCurrentExecutionTime()
         + ", lastEndTime="
         + this.getLastEndTime()
         + ", lastDurationMs="
         + this.getLastDurationMs()
         + ", runCount="
         + this.getRunCount()
         + ", lastError="
         + this.getLastError()
         + ", nextExecutionTime="
         + this.getNextExecutionTime()
         + ")";
   }
}
