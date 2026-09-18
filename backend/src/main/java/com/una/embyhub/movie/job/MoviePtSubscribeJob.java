package com.una.embyhub.movie.job;

import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.movie.service.MoviePtSubscribeService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class MoviePtSubscribeJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtSubscribeJob.class);
   private static final int DEFAULT_LIMIT = 20;
   private final MoviePtSubscribeService moviePtSubscribeService;

   @Scheduled(
      cron = "0 */5 * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "PT订阅搜索",
      remark = "定时执行本地PT订阅搜索并按订阅配置自动下载"
   )
   public void runSubscribeSearch() {
      log.info("开始执行本地 PT 订阅定时搜索");

      try {
         this.moviePtSubscribeService.searchAll(20, null);
      } catch (Exception var2) {
         log.error("本地 PT 订阅定时搜索执行失败", (Throwable)var2);
      }
   }

   @Generated
   public MoviePtSubscribeJob(final MoviePtSubscribeService moviePtSubscribeService) {
      this.moviePtSubscribeService = moviePtSubscribeService;
   }
}
