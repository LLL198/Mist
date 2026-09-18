package com.una.embyhub.job;

import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.service.EmbyStudioCacheService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class EmbyStudioCacheJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyStudioCacheJob.class);
   private final EmbyStudioCacheService embyStudioCacheService;

   @EventListener({ApplicationReadyEvent.class})
   public void refreshMissingCachesOnStartup() {
      log.info("Emby Studio 缓存启动检查开始");
      this.embyStudioCacheService.refreshMissingCachesAsync();
   }

   @Scheduled(
      cron = "0 30 3 * * ?",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "Emby Studio 缓存刷新",
      remark = "自动同步所有启用 Emby 服务器的 Studio 缓存"
   )
   public void refreshAllStudiosDaily() {
      log.info("Emby Studio 缓存定时刷新开始");
      this.embyStudioCacheService.refreshAllCaches();
      log.info("Emby Studio 缓存定时刷新结束");
   }

   @Generated
   public EmbyStudioCacheJob(final EmbyStudioCacheService embyStudioCacheService) {
      this.embyStudioCacheService = embyStudioCacheService;
   }
}
