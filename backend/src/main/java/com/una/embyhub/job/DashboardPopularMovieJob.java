package com.una.embyhub.job;

import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.service.DashboardPopularMovieService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DashboardPopularMovieJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DashboardPopularMovieJob.class);
   private final DashboardPopularMovieService dashboardPopularMovieService;

   public DashboardPopularMovieJob(DashboardPopularMovieService dashboardPopularMovieService) {
      this.dashboardPopularMovieService = dashboardPopularMovieService;
   }

   @Scheduled(
      cron = "0 0 4 * * ?",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "首页热门影片缓存",
      remark = "每天凌晨4点分析近7天播放记录并刷新首页热门影片缓存"
   )
   public void refreshPopularMoviesDaily() {
      log.info("首页热门影片缓存任务开始");
      this.dashboardPopularMovieService.refreshAllPopularMovies();
      log.info("首页热门影片缓存任务结束");
   }
}
