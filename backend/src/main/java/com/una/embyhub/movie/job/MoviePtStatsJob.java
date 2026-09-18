package com.una.embyhub.movie.job;

import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.movie.service.MoviePtSiteService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class MoviePtStatsJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtStatsJob.class);
   private final MoviePtSiteService moviePtSiteService;

   @Scheduled(
      cron = "0 0 */6 * * ?"
   )
   @ScheduledTaskMeta(
      name = "PT站点数据刷新",
      remark = "定期刷新PT站点用户统计数据"
   )
   public void configureTasks() {
      log.info("开始刷新PT站点用户统计数据");

      try {
         this.moviePtSiteService.refreshAllUserStats();
      } catch (Exception var2) {
         log.error("刷新PT站点用户统计数据失败", (Throwable)var2);
      }
   }

   @Async
   @EventListener({ApplicationReadyEvent.class})
   public void initUserStats() {
      log.info("系统启动，开始初始化PT站点用户统计数据");

      try {
         this.moviePtSiteService.refreshAllUserStats();
      } catch (Exception var2) {
         log.error("初始化PT站点用户统计数据失败", (Throwable)var2);
      }
   }

   @Generated
   public MoviePtStatsJob(final MoviePtSiteService moviePtSiteService) {
      this.moviePtSiteService = moviePtSiteService;
   }
}
