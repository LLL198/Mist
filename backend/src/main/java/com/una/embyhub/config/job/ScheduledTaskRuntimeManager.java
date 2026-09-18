package com.una.embyhub.config.job;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskRuntimeManager {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(ScheduledTaskRuntimeManager.class);
   private static final String REDIS_CRON_OVERRIDE_HASH_KEY = "scheduled:cron:overrides";
   private final ScheduledTaskHolder taskHolder;
   private final TaskScheduler taskScheduler;
   private final StringRedisTemplate stringRedisTemplate;
   private final ConcurrentMap<String, String> cronOverrides = new ConcurrentHashMap<>();
   private final ConcurrentMap<String, ScheduledTaskRuntimeManager.RuntimeCronTask> runtimeCronTasks = new ConcurrentHashMap<>();

   public ScheduledTaskRuntimeManager(ScheduledTaskHolder taskHolder, TaskScheduler taskScheduler, StringRedisTemplate stringRedisTemplate) {
      this.taskHolder = taskHolder;
      this.taskScheduler = taskScheduler;
      this.stringRedisTemplate = stringRedisTemplate;
   }

   @EventListener({ApplicationReadyEvent.class})
   public synchronized void restoreCronOverrides() {
      try {
         Map<Object, Object> entries = this.stringRedisTemplate.<Object, Object>opsForHash().entries("scheduled:cron:overrides");
         if (entries == null || entries.isEmpty()) {
            return;
         }

         for (Entry<Object, Object> entry : entries.entrySet()) {
            String taskKey = Objects.toString(entry.getKey(), "").trim();
            String cron = Objects.toString(entry.getValue(), "").trim();
            if (!taskKey.isBlank() && !cron.isBlank()) {
               try {
                  ScheduledTaskRuntimeManager.CronTaskBinding binding = this.findCronTaskBindingByKey(taskKey);
                  if (binding.locked()) {
                     this.clearPersistedOverride(taskKey);
                     log.warn("已忽略受保护定时任务的cron覆盖: {}", taskKey);
                  } else {
                     this.applyCronByTaskKey(taskKey, cron);
                     this.cronOverrides.put(taskKey, cron);
                     log.info("已恢复定时任务cron覆盖: {} -> {}", taskKey, cron);
                  }
               } catch (Exception var7) {
                  log.warn("恢复定时任务cron覆盖失败: {} -> {}, reason={}", taskKey, cron, var7.getMessage());
               }
            }
         }
      } catch (Exception var8) {
         log.warn("读取Redis中的定时任务cron覆盖失败: {}", var8.getMessage());
      }
   }

   public synchronized void updateCron(String key, String cron) {
      String normalizedInput = this.normalizeRequired(key, "任务标识不能为空");
      String normalizedCron = this.normalizeRequired(cron, "Cron表达式不能为空");

      try {
         CronExpression.parse(normalizedCron);
      } catch (IllegalArgumentException var6) {
         throw new IllegalArgumentException("Cron表达式无效: " + normalizedCron);
      }

      String taskKey = this.resolveTaskKey(normalizedInput);
      this.assertTaskMutable(taskKey);
      this.applyCronByTaskKey(taskKey, normalizedCron);
      this.cronOverrides.put(taskKey, normalizedCron);
      this.stringRedisTemplate.<String, String>opsForHash().put("scheduled:cron:overrides", taskKey, normalizedCron);
   }

   public synchronized void resetCron(String key) {
      String normalizedInput = this.normalizeRequired(key, "任务标识不能为空");
      String taskKey = this.resolveTaskKey(normalizedInput);
      this.assertTaskMutable(taskKey);
      this.resetCronByTaskKey(taskKey);
   }

   public synchronized void resetCronBatch(List<String> keys) {
      if (keys != null && !keys.isEmpty()) {
         for (String taskKey : keys.stream()
            .map(key -> this.normalizeRequired(key, "任务标识不能为空"))
            .map(this::resolveTaskKey)
            .peek(this::assertTaskMutable)
            .distinct()
            .toList()) {
            this.resetCronByTaskKey(taskKey);
         }
      } else {
         throw new IllegalArgumentException("任务标识列表不能为空");
      }
   }

   public synchronized void resetCronAll() {
      for (String taskKey : this.listPersistedOverrideTaskKeys()) {
         try {
            ScheduledTaskRuntimeManager.CronTaskBinding binding = this.findCronTaskBindingByKeyOrNull(taskKey);
            if (binding != null && binding.locked()) {
               this.clearPersistedOverride(taskKey);
               log.info("已清理受保护定时任务的cron覆盖: {}", taskKey);
            } else {
               this.resetCronByTaskKey(taskKey);
            }
         } catch (Exception var5) {
            this.clearPersistedOverride(taskKey);
            log.warn("恢复任务默认cron失败，已清理覆盖: {}, reason={}", taskKey, var5.getMessage());
         }
      }
   }

   public synchronized void runNow(String key) {
      String normalizedInput = this.normalizeRequired(key, "任务标识不能为空");
      String taskKey = this.resolveTaskKey(normalizedInput);
      this.assertTaskMutable(taskKey);
      ScheduledTaskRuntimeManager.RuntimeCronTask runtimeTask = this.runtimeCronTasks.get(taskKey);
      Runnable runnable = runtimeTask != null ? runtimeTask.runnable() : this.findCronTaskBindingByKey(taskKey).runnable();
      ScheduledFuture<?> future = this.taskScheduler.schedule(runnable, Instant.now());
      if (future == null) {
         throw new IllegalStateException("触发任务立即执行失败: " + taskKey);
      }
   }

   public String getEffectiveCron(String key, String defaultCron) {
      return key != null && !key.isBlank() ? this.cronOverrides.getOrDefault(key, defaultCron) : defaultCron;
   }

   private void resetCronByTaskKey(String taskKey) {
      ScheduledTaskRuntimeManager.CronTaskBinding binding = this.findCronTaskBindingByKey(taskKey);
      this.applyCronByTaskKey(taskKey, binding.defaultCron());
      this.cronOverrides.remove(taskKey);
      this.stringRedisTemplate.opsForHash().delete("scheduled:cron:overrides", taskKey);
   }

   private void applyCronByTaskKey(String taskKey, String cron) {
      ScheduledTaskRuntimeManager.RuntimeCronTask runtimeTask = this.runtimeCronTasks.get(taskKey);
      Runnable runnable;
      ZoneId zone;
      if (runtimeTask == null) {
         ScheduledTaskRuntimeManager.CronTaskBinding binding = this.findCronTaskBindingByKey(taskKey);
         binding.scheduledTask().cancel(false);
         runnable = binding.runnable();
         zone = binding.zone();
      } else {
         runtimeTask.cancel();
         runnable = runtimeTask.runnable();
         zone = runtimeTask.zone();
      }

      ScheduledFuture<?> future = this.taskScheduler.schedule(runnable, new CronTrigger(cron, zone));
      if (future == null) {
         throw new IllegalStateException("重建定时任务失败: " + taskKey);
      } else {
         this.runtimeCronTasks.put(taskKey, new ScheduledTaskRuntimeManager.RuntimeCronTask(runnable, zone, future));
      }
   }

   private String resolveTaskKey(String keyOrName) {
      ScheduledTaskRuntimeManager.CronTaskBinding byKey = this.findCronTaskBindingByKeyOrNull(keyOrName);
      if (byKey != null) {
         return byKey.key();
      } else {
         List<ScheduledTaskRuntimeManager.CronTaskBinding> byName = this.listCronTaskBindings()
            .stream()
            .filter(binding -> binding.name() != null && keyOrName.equals(binding.name()))
            .toList();
         if (byName.isEmpty()) {
            throw new IllegalArgumentException("未找到对应的CRON任务: " + keyOrName);
         } else {
            Set<String> matchedKeys = byName.stream()
               .map(ScheduledTaskRuntimeManager.CronTaskBinding::key)
               .collect(Collectors.toCollection(LinkedHashSet::new));
            if (matchedKeys.size() > 1) {
               throw new IllegalArgumentException("任务名称存在重复，请改用任务key: " + keyOrName);
            } else {
               return matchedKeys.iterator().next();
            }
         }
      }
   }

   private void assertTaskMutable(String taskKey) {
      ScheduledTaskRuntimeManager.CronTaskBinding binding = this.findCronTaskBindingByKey(taskKey);
      if (binding.locked()) {
         throw new IllegalArgumentException("该定时任务为系统保护任务，不允许通过接口修改或手动触发");
      }
   }

   private void clearPersistedOverride(String taskKey) {
      this.cronOverrides.remove(taskKey);

      try {
         this.stringRedisTemplate.opsForHash().delete("scheduled:cron:overrides", taskKey);
      } catch (Exception var3) {
      }
   }

   private ScheduledTaskRuntimeManager.CronTaskBinding findCronTaskBindingByKey(String key) {
      ScheduledTaskRuntimeManager.CronTaskBinding binding = this.findCronTaskBindingByKeyOrNull(key);
      if (binding == null) {
         throw new IllegalArgumentException("未找到对应的CRON任务: " + key);
      } else {
         return binding;
      }
   }

   private ScheduledTaskRuntimeManager.CronTaskBinding findCronTaskBindingByKeyOrNull(String key) {
      for (ScheduledTaskRuntimeManager.CronTaskBinding binding : this.listCronTaskBindings()) {
         if (key.equals(binding.key())) {
            return binding;
         }
      }

      return null;
   }

   private List<ScheduledTaskRuntimeManager.CronTaskBinding> listCronTaskBindings() {
      List<ScheduledTaskRuntimeManager.CronTaskBinding> bindings = new ArrayList<>();

      for (ScheduledTask scheduledTask : this.taskHolder.getScheduledTasks()) {
         Task task = scheduledTask.getTask();
         if (task instanceof CronTask) {
            Runnable runnable = task.getRunnable();
            ScheduledMethodRunnable smr = this.unwrapToSMR(runnable);
            if (smr != null) {
               Method method = AopUtils.getMostSpecificMethod(smr.getMethod(), smr.getTarget().getClass());
               Class<?> targetClass = smr.getTarget().getClass();
               String taskKey = targetClass.getName() + "#" + method.getName();
               String taskName = this.resolveTaskName(method, targetClass);
               String defaultCron = ((CronTask)task).getExpression();
               ZoneId zone = this.resolveZone(method);
               boolean locked = this.resolveTaskLocked(method);
               bindings.add(new ScheduledTaskRuntimeManager.CronTaskBinding(taskKey, taskName, defaultCron, locked, scheduledTask, runnable, zone));
            }
         }
      }

      return bindings;
   }

   private Set<String> listPersistedOverrideTaskKeys() {
      Set<String> taskKeys = new LinkedHashSet<>(this.cronOverrides.keySet());

      try {
         Set<Object> persistedKeys = this.stringRedisTemplate.opsForHash().keys("scheduled:cron:overrides");
         if (persistedKeys != null) {
            for (Object obj : persistedKeys) {
               String key = Objects.toString(obj, "").trim();
               if (!key.isBlank()) {
                  taskKeys.add(key);
               }
            }
         }
      } catch (Exception var6) {
         log.warn("读取Redis覆盖key失败: {}", var6.getMessage());
      }

      return taskKeys;
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

   private String resolveTaskName(Method method, Class<?> targetClass) {
      ScheduledTaskMeta meta = method != null ? AnnotatedElementUtils.findMergedAnnotation(method, ScheduledTaskMeta.class) : null;
      if (meta != null && meta.name() != null && !meta.name().isBlank()) {
         return meta.name().trim();
      } else {
         String methodName = method != null ? method.getName() : "unknown";
         String className = targetClass != null ? targetClass.getSimpleName() : "UnknownClass";
         return className + "#" + methodName;
      }
   }

   private boolean resolveTaskLocked(Method method) {
      ScheduledTaskMeta meta = method != null ? AnnotatedElementUtils.findMergedAnnotation(method, ScheduledTaskMeta.class) : null;
      return meta != null && meta.locked();
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
            } catch (Throwable var11) {
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

   private String normalizeRequired(String value, String message) {
      if (value != null && !value.isBlank()) {
         return value.trim();
      } else {
         throw new IllegalArgumentException(message);
      }
   }

   private static record CronTaskBinding(
      String key, String name, String defaultCron, boolean locked, ScheduledTask scheduledTask, Runnable runnable, ZoneId zone
   ) {
   }

   private static record RuntimeCronTask(Runnable runnable, ZoneId zone, ScheduledFuture<?> future) {
      private void cancel() {
         if (this.future != null) {
            this.future.cancel(false);
         }
      }
   }
}
