package com.una.embyhub.job;

import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.service.TmdbDailyReleaseService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TmdbDailyReleaseJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbDailyReleaseJob.class);
   private final TmdbDailyReleaseService tmdbDailyReleaseService;

   @Scheduled(
      cron = "0 30 0 * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "追新定时任务",
      remark = "每天凌晨00:30抓取今日上映电影和播出剧集并按配置推送"
   )
   public void runDaily() {
      log.info("追新定时任务开始");
      this.tmdbDailyReleaseService.runDailyJob();
      log.info("追新定时任务结束");
   }

   @Generated
   public TmdbDailyReleaseJob(final TmdbDailyReleaseService tmdbDailyReleaseService) {
      this.tmdbDailyReleaseService = tmdbDailyReleaseService;
   }
}
