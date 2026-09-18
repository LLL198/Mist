package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.model.dto.request.foammigration.FoamDataMigrationRequest;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationConnectionResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationJobResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationPlanResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationProgressResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationResultResponse;
import com.una.embyhub.service.FoamDataMigrationService;
import jakarta.annotation.PreDestroy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping({"foamMigration"})
public class FoamDataMigrationController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(FoamDataMigrationController.class);
   private static final long SSE_TIMEOUT_MILLIS = TimeUnit.HOURS.toMillis(2L);
   private static final AtomicInteger MIGRATION_THREAD_ID = new AtomicInteger(1);
   private final FoamDataMigrationService migrationService;
   private final ConcurrentMap<String, FoamDataMigrationController.MigrationJob> migrationJobs = new ConcurrentHashMap<>();
   private final AtomicReference<String> activeJobId = new AtomicReference<>();
   private final ExecutorService migrationExecutor = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), runnable -> {
      Thread thread = new Thread(runnable);
      thread.setName("foam-migration-sse-" + MIGRATION_THREAD_ID.getAndIncrement());
      thread.setDaemon(true);
      return thread;
   }, new AbortPolicy());

   public FoamDataMigrationController(FoamDataMigrationService migrationService) {
      this.migrationService = migrationService;
   }

   @GetMapping({"tables"})
   @SaCheckPermission({"admin"})
   public FoamDataMigrationPlanResponse tables() {
      return this.migrationService.plan();
   }

   @PostMapping({"test"})
   @SaCheckPermission({"admin"})
   public FoamDataMigrationConnectionResponse test(@RequestBody @Validated FoamDataMigrationRequest request) {
      return this.migrationService.testConnection(request);
   }

   @PostMapping({"sync"})
   @SaCheckPermission({"admin"})
   public FoamDataMigrationResultResponse sync(@RequestBody @Validated FoamDataMigrationRequest request) {
      return this.migrationService.sync(request);
   }

   @PostMapping({"sync/start"})
   @SaCheckPermission({"admin"})
   public FoamDataMigrationJobResponse startSync(@RequestBody @Validated FoamDataMigrationRequest request) {
      FoamDataMigrationController.MigrationJob activeJob = this.activeMigrationJob();
      if (activeJob != null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "已有数据库迁移任务正在执行，请等待同步完成");
      } else {
         String jobId = UUID.randomUUID().toString();
         FoamDataMigrationController.MigrationJob job = new FoamDataMigrationController.MigrationJob(jobId, this.initialProgress(jobId, request));
         this.migrationJobs.put(jobId, job);
         this.activeJobId.set(jobId);
         CompletableFuture.runAsync(() -> this.runMigrationJob(job, request), this.migrationExecutor);
         FoamDataMigrationJobResponse response = new FoamDataMigrationJobResponse();
         response.setJobId(jobId);
         response.setProgress(job.latestProgress);
         return response;
      }
   }

   @GetMapping(
      value = {"sync/stream/{jobId}"},
      produces = {"text/event-stream"}
   )
   @SaCheckPermission({"admin"})
   public SseEmitter syncStream(@PathVariable String jobId) {
      SseEmitter emitter = new SseEmitter(SSE_TIMEOUT_MILLIS);
      FoamDataMigrationController.MigrationJob job = this.migrationJobs.get(jobId);
      if (job == null) {
         FoamDataMigrationProgressResponse progress = this.initialProgress(jobId);
         progress.setStatus("FAILED");
         progress.setStage("FAILED");
         progress.setMessage("迁移任务不存在或已过期");
         this.sendProgressEvent(emitter, progress);
         emitter.complete();
         return emitter;
      } else {
         job.emitters.add(emitter);
         emitter.onCompletion(() -> job.emitters.remove(emitter));
         emitter.onTimeout(() -> {
            job.emitters.remove(emitter);
            emitter.complete();
         });
         emitter.onError(error -> {
            job.emitters.remove(emitter);
            log.debug("Mist 数据迁移 SSE 连接异常: {}", error.getMessage());
         });
         this.sendProgressEvent(emitter, job.latestProgress);
         if (job.isTerminal()) {
            emitter.complete();
         }

         return emitter;
      }
   }

   private FoamDataMigrationController.MigrationJob activeMigrationJob() {
      String jobId = this.activeJobId.get();
      if (jobId == null) {
         return null;
      } else {
         FoamDataMigrationController.MigrationJob job = this.migrationJobs.get(jobId);
         if (job != null && !job.isTerminal()) {
            return job;
         } else {
            this.activeJobId.compareAndSet(jobId, null);
            return null;
         }
      }
   }

   private void runMigrationJob(FoamDataMigrationController.MigrationJob job, FoamDataMigrationRequest request) {
      try {
         this.migrationService.sync(request, progressx -> this.publishProgress(job, progressx));
      } catch (Exception var8) {
         log.warn("Mist 数据迁移任务失败: {}", var8.getMessage());
         FoamDataMigrationProgressResponse progress = job.latestProgress == null ? this.initialProgress(job.jobId) : job.latestProgress;
         progress.setJobId(job.jobId);
         progress.setStatus("FAILED");
         progress.setStage("FAILED");
         progress.setMessage(var8.getMessage());
         this.publishProgress(job, progress);
      } finally {
         this.activeJobId.compareAndSet(job.jobId, null);
         this.completeEmitters(job);
      }
   }

   private FoamDataMigrationProgressResponse initialProgress(String jobId) {
      return this.initialProgress(jobId, null);
   }

   private FoamDataMigrationProgressResponse initialProgress(String jobId, FoamDataMigrationRequest request) {
      FoamDataMigrationPlanResponse plan = this.migrationService.plan();
      int tableCount = plan.getTableCount();
      FoamDataMigrationProgressResponse progress = new FoamDataMigrationProgressResponse();
      progress.setJobId(jobId);
      progress.setStatus("PENDING");
      progress.setStage("PENDING");
      progress.setPercent(0);
      progress.setTotalTables(tableCount);
      progress.setProcessedTables(0);
      progress.setSyncedTableCount(0);
      progress.setSkippedTableCount(0);
      progress.setFailedTableCount(0);
      progress.setCurrentTableSourceRows(0L);
      progress.setCurrentTableSyncedRows(0L);
      progress.setTotalSyncedRows(0L);
      progress.setDurationMs(0L);
      progress.setMessage("迁移任务已创建，正在排队启动...");
      return progress;
   }

   private void publishProgress(FoamDataMigrationController.MigrationJob job, FoamDataMigrationProgressResponse progress) {
      if (progress != null) {
         progress.setJobId(job.jobId);
         job.latestProgress = progress;

         for (SseEmitter emitter : job.emitters) {
            if (!this.sendProgressEvent(emitter, progress)) {
               job.emitters.remove(emitter);
            }
         }

         if (job.isTerminal()) {
            this.completeEmitters(job);
         }
      }
   }

   private boolean sendProgressEvent(SseEmitter emitter, FoamDataMigrationProgressResponse progress) {
      try {
         synchronized (emitter) {
            emitter.send(SseEmitter.event().name("foam-migration-progress").data(progress));
         }

         return true;
      } catch (Exception var6) {
         log.debug("Mist 数据迁移 SSE 发送失败: {}", var6.getMessage());
         return false;
      }
   }

   private void completeEmitters(FoamDataMigrationController.MigrationJob job) {
      for (SseEmitter emitter : job.emitters) {
         try {
            emitter.complete();
         } catch (Exception var5) {
            log.debug("Mist 数据迁移 SSE 关闭失败: {}", var5.getMessage());
         }
      }

      job.emitters.clear();
   }

   @PreDestroy
   public void shutdownMigrationExecutor() {
      this.migrationExecutor.shutdown();

      try {
         if (!this.migrationExecutor.awaitTermination(5L, TimeUnit.SECONDS)) {
            this.migrationExecutor.shutdownNow();
         }
      } catch (InterruptedException var2) {
         this.migrationExecutor.shutdownNow();
         Thread.currentThread().interrupt();
      }
   }

   private static class MigrationJob {
      private final String jobId;
      private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
      private volatile FoamDataMigrationProgressResponse latestProgress;

      private MigrationJob(String jobId, FoamDataMigrationProgressResponse latestProgress) {
         this.jobId = jobId;
         this.latestProgress = latestProgress;
      }

      private boolean isTerminal() {
         String status = this.latestProgress == null ? "" : this.latestProgress.getStatus();
         return "COMPLETED".equals(status) || "FAILED".equals(status);
      }
   }
}
