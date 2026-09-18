package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import com.una.embyhub.component.MapSummaryCache;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.service.playbackreporting.PlaybackReportingCacheService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

@Configuration
@EnableScheduling
public class PlaybackReportingCacheJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PlaybackReportingCacheJob.class);
   private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final PlaybackReportingCacheService playbackReportingCacheService;
   private final MapSummaryCache mapSummaryCache;

   public PlaybackReportingCacheJob(
      EmbyInfoCacheManagerUtils embyInfoCacheManager, PlaybackReportingCacheService playbackReportingCacheService, MapSummaryCache mapSummaryCache
   ) {
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.playbackReportingCacheService = playbackReportingCacheService;
      this.mapSummaryCache = mapSummaryCache;
   }

   @EventListener({ApplicationReadyEvent.class})
   public void syncYesterdayOnStartup() {
      CompletableFuture.runAsync(() -> this.syncPastDays(1, "启动补齐"));
   }

   @Scheduled(
      cron = "0 0 3 * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "PR播放记录同步",
      remark = "每天凌晨3点同步昨天播放记录到本地库"
   )
   public void syncYesterdayDaily() {
      this.syncPastDays(1, "定时同步");
   }

   private void syncPastDays(int days, String source) {
      log.info("{} Playback Reporting 播放记录开始：{}，days={}", source, DateUtil.formatDateTime(new Date()), days);
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
      if (CollectionUtils.isEmpty(serverConfigs)) {
         serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
      }

      LocalDate today = LocalDate.now(ZONE_ID);
      boolean mapDataChanged = false;

      for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
         Long serverId = serverConfig.id();

         for (int i = 1; i <= days; i++) {
            LocalDate playDay = today.minusDays((long)i);

            try {
               int count = this.playbackReportingCacheService.syncServerDay(serverId, playDay);
               if (count > 0) {
                  mapDataChanged = true;
                  log.info("服务器[{}] Playback Reporting 播放记录同步完成：day={}, count={}", serverConfig.serverName(), playDay, count);
               }
            } catch (Exception var12) {
               this.playbackReportingCacheService.markSyncFailure(serverId, playDay, var12.getMessage());
               log.error("服务器[{}] Playback Reporting 播放记录同步失败：day={}", serverConfig.serverName(), playDay, var12);
            }
         }
      }

      if (mapDataChanged) {
         this.mapSummaryCache.refresh();
      }

      log.info("{} Playback Reporting 播放记录结束：{}", source, DateUtil.formatDateTime(new Date()));
   }
}
