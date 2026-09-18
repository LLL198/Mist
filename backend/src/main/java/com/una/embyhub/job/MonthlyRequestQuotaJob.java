package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.EmbyUserService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

@Configuration
@EnableScheduling
public class MonthlyRequestQuotaJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MonthlyRequestQuotaJob.class);
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;

   @Scheduled(
      cron = "0 30 0 * * ?",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "每月求片次数自动增加",
      remark = "每月自动为未禁用且未过期的用户增加求片次数"
   )
   public void executeMonthlyRequestQuota() {
      log.info("每月求片次数检查任务开始：{}", DateUtil.formatDateTime(new Date()));
      String configJson = this.configCacheLoaderUtils.getConfigValue("monthly_request_config");
      if (!StringUtils.hasText(configJson)) {
         log.debug("未配置每月求片次数自动增加，跳过执行");
      } else {
         JSONObject config;
         try {
            config = JSON.parseObject(configJson);
         } catch (Exception var14) {
            log.error("解析每月求片次数配置失败：{}", configJson, var14);
            return;
         }

         Integer count = config.getInteger("count");
         Integer day = config.getInteger("day");
         String mode = config.getString("mode");
         if (count != null && count > 0) {
            if (day == null || day < 1 || day > 28) {
               day = 1;
            }

            if (!StringUtils.hasText(mode)) {
               mode = "ADD";
            }

            int todayDay = LocalDate.now().getDayOfMonth();
            if (todayDay != day) {
               log.debug("今天({})不是配置的执行日期({})，跳过执行", todayDay, day);
            } else {
               log.info("开始执行每月求片次数自动增加，模式：{}，次数：{}", mode, count);
               List<EmbyUser> eligibleUsers = new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper())
                  .eq(EmbyUser::getUserStatus, Integer.valueOf(0))
                  .ne(EmbyUser::getIsAdmin, Integer.valueOf(1))
                  .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                  .and(w -> w.gt(EmbyUser::getExpirationDate, new Date()).or().isNull(EmbyUser::getExpirationDate))
                  .list();
               if (eligibleUsers.isEmpty()) {
                  log.info("没有符合条件的用户需要增加求片次数");
               } else {
                  int successCount = 0;

                  for (EmbyUser user : eligibleUsers) {
                     try {
                        int newCount;
                        if ("RESET".equalsIgnoreCase(mode)) {
                           newCount = count;
                        } else {
                           int currentCount = user.getRequestPackagesCount() != null ? user.getRequestPackagesCount() : 0;
                           newCount = currentCount + count;
                        }

                        new LambdaUpdateChainWrapper<>(this.embyUserService.getBaseMapper())
                           .eq(EmbyUser::getId, user.getId())
                           .set(EmbyUser::getRequestPackagesCount, Integer.valueOf(newCount))
                           .update();
                        successCount++;
                        log.debug("用户 {} 求片次数更新：{} -> {}", user.getEmbyUserName(), user.getRequestPackagesCount(), newCount);
                     } catch (Exception var13) {
                        log.error("更新用户 {} 求片次数失败", user.getEmbyUserName(), var13);
                     }
                  }

                  log.info("每月求片次数自动增加完成，共处理 {} 个用户，成功 {} 个", eligibleUsers.size(), successCount);
               }
            }
         } else {
            log.debug("每月求片次数配置的count无效，跳过执行");
         }
      }
   }
}
