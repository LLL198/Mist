package com.una.embyhub.config.job;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskExplorer {
   private final ScheduledTaskHolder taskHolder;
   private final ScheduledTaskMetricsAspect metricsAspect;
   private final ScheduledTaskRuntimeManager runtimeManager;

   public ScheduledTaskExplorer(ScheduledTaskHolder taskHolder, ScheduledTaskMetricsAspect metricsAspect, ScheduledTaskRuntimeManager runtimeManager) {
      this.taskHolder = taskHolder;
      this.metricsAspect = metricsAspect;
      this.runtimeManager = runtimeManager;
   }

   public List<ScheduledTaskInfo> listAllTasks() {
      Set<ScheduledTask> scheduledTasks = this.taskHolder.getScheduledTasks();
      Map<String, ScheduledTaskMetricsAspect.TaskMetric> metricMap = this.metricsAspect.getMetrics();
      List<ScheduledTaskInfo> result = new ArrayList<>();

      for (ScheduledTask st : scheduledTasks) {
         Task springTask = st.getTask();
         Runnable runnable = springTask.getRunnable();
         ScheduledMethodRunnable smr = this.unwrapToSMR(runnable);
         Method method = null;
         Class<?> targetClass = null;
         if (smr != null) {
            method = AopUtils.getMostSpecificMethod(smr.getMethod(), smr.getTarget().getClass());
            targetClass = smr.getTarget().getClass();
         }

         String key = targetClass != null && method != null ? targetClass.getName() + "#" + method.getName() : null;
         String annotatedName = null;
         String annotatedRemark = null;
         boolean hidden = false;
         if (method != null) {
            ScheduledTaskMeta meta = AnnotatedElementUtils.findMergedAnnotation(method, ScheduledTaskMeta.class);
            if (meta != null) {
               hidden = meta.hidden();
               annotatedName = blankToNull(meta.name());
               annotatedRemark = meta.remark();
            }
         }

         if (!hidden) {
            String defaultName = targetClass != null && method != null ? targetClass.getSimpleName() + "#" + method.getName() : key;
            ScheduledTaskMetricsAspect.TaskMetric metric = key != null ? metricMap.get(key) : null;
            String finalName = nonEmpty(annotatedName)
               ? annotatedName
               : (metric != null && nonEmpty(metric.getTaskName()) ? metric.getTaskName() : defaultName);
            String finalRemark = nonEmpty(annotatedRemark) ? annotatedRemark : (metric != null ? metric.getTaskRemark() : null);
            ScheduledTaskInfo info = new ScheduledTaskInfo();
            info.setKey(key);
            info.setTaskName(finalName);
            info.setTaskRemark(finalRemark);
            if (targetClass != null) {
               info.setBeanClass(targetClass.getName());
            }

            if (method != null) {
               info.setMethodName(method.getName());
            }

            if (metric != null) {
               info.setCurrentExecutionTime(metric.getLastStartTime());
               info.setLastEndTime(metric.getLastEndTime());
               info.setLastDurationMs(metric.getLastDurationMs());
               info.setRunCount(metric.getRunCount());
               info.setLastError(metric.getLastError());
            }

            Date nextTime = null;
            if (springTask instanceof CronTask cronTask) {
               info.setType("CRON");
               String effectiveCron = key != null ? this.runtimeManager.getEffectiveCron(key, cronTask.getExpression()) : cronTask.getExpression();
               info.setCron(effectiveCron);
               ZoneId zone = this.resolveZone(method);
               LocalDateTime now = LocalDateTime.now(zone);
               LocalDateTime next = CronExpression.parse(effectiveCron).next(now);
               if (next != null) {
                  nextTime = Date.from(next.atZone(zone).toInstant());
               }
            } else if (springTask instanceof TriggerTask) {
               info.setType("TRIGGER");
            } else {
               info.setType("UNKNOWN");
            }

            info.setNextExecutionTime(nextTime);
            result.add(info);
         }
      }

      return result;
   }

   private ZoneId resolveZone(Method method) {
      try {
         Scheduled sch = method != null ? AnnotatedElementUtils.findMergedAnnotation(method, Scheduled.class) : null;
         if (sch != null && sch.zone() != null && !sch.zone().isBlank()) {
            return ZoneId.of(sch.zone());
         }
      } catch (Exception var3) {
      }

      return ZoneId.of("Asia/Shanghai");
   }

   private ScheduledMethodRunnable unwrapToSMR(Runnable r) {
      if (r == null) {
         return null;
      } else {
         Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());

         while (r != null && !(r instanceof ScheduledMethodRunnable) && visited.add(r)) {
            Runnable inner = this.tryGetRunnableField(r, "delegate", "task", "runnable");
            if (inner != null) {
               r = inner;
            } else {
               if (!"org.springframework.scheduling.support.ReschedulingRunnable".equals(r.getClass().getName())) {
                  break;
               }

               inner = this.tryGetRunnableField(r, "task");
               if (inner == null) {
                  break;
               }

               r = inner;
            }
         }

         return r instanceof ScheduledMethodRunnable ? (ScheduledMethodRunnable)r : null;
      }
   }

   private Runnable tryGetRunnableField(Object obj, String... fieldNames) {
      Class<?> c = obj.getClass();

      for (String name : fieldNames) {
         Field f = this.findFieldRecursive(c, name);
         if (f != null) {
            try {
               f.setAccessible(true);
               Object v = f.get(obj);
               if (v instanceof Runnable) {
                  return (Runnable)v;
               }
            } catch (Throwable var10) {
            }
         }
      }

      return null;
   }

   private Field findFieldRecursive(Class<?> c, String name) {
      for (Class<?> cur = c; cur != null && cur != Object.class; cur = cur.getSuperclass()) {
         try {
            return cur.getDeclaredField(name);
         } catch (NoSuchFieldException var5) {
         }
      }

      return null;
   }

   private static boolean nonEmpty(String s) {
      return s != null && !s.isBlank();
   }

   private static String blankToNull(String s) {
      return nonEmpty(s) ? s : null;
   }
}
