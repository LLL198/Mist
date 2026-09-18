package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.config.job.ScheduledTaskExplorer;
import com.una.embyhub.config.job.ScheduledTaskInfo;
import com.una.embyhub.config.job.ScheduledTaskRuntimeManager;
import com.una.embyhub.model.dto.request.scheduledtask.PlaybackRankingConfigUpdateRequest;
import com.una.embyhub.model.dto.request.scheduledtask.ScheduledTaskCronBatchResetRequest;
import com.una.embyhub.model.dto.request.scheduledtask.ScheduledTaskCronResetRequest;
import com.una.embyhub.model.dto.request.scheduledtask.ScheduledTaskCronUpdateRequest;
import com.una.embyhub.model.dto.request.scheduledtask.ScheduledTaskRunNowRequest;
import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingConfigResponse;
import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingConfigSummaryResponse;
import com.una.embyhub.service.PlaybackRankingConfigService;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"scheduledTask"})
public class ScheduledTaskController {
   private final ScheduledTaskExplorer explorer;
   private final ScheduledTaskRuntimeManager runtimeManager;
   private final PlaybackRankingConfigService playbackRankingConfigService;

   public ScheduledTaskController(
      ScheduledTaskExplorer explorer, ScheduledTaskRuntimeManager runtimeManager, PlaybackRankingConfigService playbackRankingConfigService
   ) {
      this.explorer = explorer;
      this.runtimeManager = runtimeManager;
      this.playbackRankingConfigService = playbackRankingConfigService;
   }

   @GetMapping({"listAll"})
   public List<ScheduledTaskInfo> listAll() {
      return this.explorer.listAllTasks();
   }

   @PostMapping({"updateCron"})
   @SaCheckPermission({"admin"})
   public void updateCron(@RequestBody @Validated ScheduledTaskCronUpdateRequest request) {
      this.runtimeManager.updateCron(request.getKey(), request.getCron());
   }

   @PostMapping({"resetCron"})
   @SaCheckPermission({"admin"})
   public void resetCron(@RequestBody @Validated ScheduledTaskCronResetRequest request) {
      this.runtimeManager.resetCron(request.getKey());
   }

   @PostMapping({"resetCronBatch"})
   @SaCheckPermission({"admin"})
   public void resetCronBatch(@RequestBody @Validated ScheduledTaskCronBatchResetRequest request) {
      this.runtimeManager.resetCronBatch(request.getKeys());
   }

   @PostMapping({"resetCronAll"})
   @SaCheckPermission({"admin"})
   public void resetCronAll() {
      this.runtimeManager.resetCronAll();
   }

   @PostMapping({"runNow"})
   @SaCheckPermission({"admin"})
   public void runNow(@RequestBody @Validated ScheduledTaskRunNowRequest request) {
      this.runtimeManager.runNow(request.getKey());
   }

   @GetMapping({"playbackRanking/configSummary"})
   @SaCheckPermission({"admin"})
   public PlaybackRankingConfigSummaryResponse playbackRankingConfigSummary() {
      return this.playbackRankingConfigService.summary();
   }

   @GetMapping({"playbackRanking/config"})
   @SaCheckPermission({"admin"})
   public PlaybackRankingConfigResponse playbackRankingConfig(@RequestParam Long embyInfoId) {
      return this.playbackRankingConfigService.details(embyInfoId);
   }

   @PostMapping({"playbackRanking/config"})
   @SaCheckPermission({"admin"})
   public PlaybackRankingConfigResponse updatePlaybackRankingConfig(@RequestBody @Validated PlaybackRankingConfigUpdateRequest request) {
      return this.playbackRankingConfigService.update(request);
   }
}
