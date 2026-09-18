package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class EmbyUserExpirationNotifyJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyUserExpirationNotifyJob.class);
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private UserExpirationTelegramNotifyHelper telegramNotifyHelper;

   @Scheduled(
      cron = "0 0 0 * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "用户过期提醒任务",
      remark = "用户过期提醒管理员续费定时任务"
   )
   public void configureTasks() {
      log.info("用户过期提醒管理员续费定时任务：{}", DateUtil.formatDateTime(new Date()));
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
      if (serverConfigs.isEmpty()) {
         serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
      }

      boolean directTelegramNotify = this.telegramNotifyHelper.isDirectUserNotifyEnabled();

      for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
         QueryWrapper<EmbyUser> queryWrapper = new QueryWrapper<>();
         queryWrapper.select(
            new String[]{
               "id",
               "emby_user_id",
               "emby_user_name",
               "emby_user_password",
               "is_admin",
               "user_status",
               "expiration_date",
               "create_datetime",
               "update_datetime",
               "create_user_name",
               "update_user_name",
               "update_user_id",
               "create_user_id",
               "del_flag",
               "DATEDIFF(expiration_date, CURRENT_TIMESTAMP()) as expireDateCount",
               "remarks"
            }
         );
         queryWrapper.eq(serverConfig.id() != null, "emby_info_id", serverConfig.id());
         queryWrapper.isNull(serverConfig.id() == null, "emby_info_id");
         queryWrapper.last(
            "and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) <= 3 and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) >= 0 and user_status != 1 order by case when is_admin = 1 then 0 else 1 end, id desc"
         );
         List<EmbyUser> embyUserList = this.embyUserService.list(queryWrapper);
         embyUserList.forEach(embyUser -> {
            Map<String, String> extras = new HashMap<>();
            extras.put("userName", embyUser.getEmbyUserName());
            extras.put("expirationDate", DateUtil.formatDateTime(embyUser.getExpirationDate()));
            extras.put("timeLeft", compareTime(embyUser.getExpirationDate(), new Date()));
            SendMessageRequest sendMessageRequest = new SendMessageRequest();
            sendMessageRequest.setParseMode("Markdown");
            sendMessageRequest.setName("Emby用户过期提醒");
            sendMessageRequest.setServerUrl(this.resolveServerUrl(serverConfig));
            sendMessageRequest.setServerName(this.resolveServerName(serverConfig));
            sendMessageRequest.setExtraVariables(extras);
            this.telegramNotifyHelper.sendTelegram(embyUser, sendMessageRequest, "user_expiration", directTelegramNotify);
            this.notifyUtils.sendMultiChannel(sendMessageRequest, "user_expiration", false, "wechat", "wechatBot", "dingding", "messagepush");
         });
      }
   }

   public static String compareTime(Date date1, Date date2) {
      if (date1.before(date2)) {
         return "已经过期";
      } else {
         long betweenMs = date1.getTime() - date2.getTime();
         long days = betweenMs / 86400000L;
         if (days > 0L) {
            return days + "天";
         } else {
            long hours = betweenMs / 3600000L;
            if (hours > 0L) {
               return hours + "小时";
            } else {
               long minutes = betweenMs / 60000L;
               if (minutes > 0L) {
                  return minutes + "分钟";
               } else {
                  long seconds = betweenMs / 1000L;
                  return seconds + "秒";
               }
            }
         }
      }
   }

   private String getServerLabel(Long embyInfoId) {
      if (embyInfoId == null) {
         return "默认服务器";
      } else {
         EmbyInfo embyInfo = this.embyInfoService.getById(embyInfoId);
         return embyInfo != null && StringUtils.hasText(embyInfo.getServerName()) ? embyInfo.getServerName() : "服务器-" + embyInfoId;
      }
   }

   private String getServerLabel(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (serverConfig == null) {
         return "默认服务器";
      } else if (serverConfig.id() != null) {
         return this.getServerLabel(serverConfig.id());
      } else {
         return StringUtils.hasText(serverConfig.serverName()) ? serverConfig.serverName() : "默认服务器";
      }
   }

   private String getServerUrl(Long embyInfoId) {
      if (embyInfoId == null) {
         return null;
      } else {
         EmbyInfo embyInfo = this.embyInfoService.getById(embyInfoId);
         if (embyInfo == null) {
            return null;
         } else {
            String embyUrl = embyInfo.getEmbyUrl();
            if (!StringUtils.hasText(embyUrl) || !embyUrl.startsWith("http://") && !embyUrl.startsWith("https://")) {
               StringBuilder baseUrl = new StringBuilder();
               if (StringUtils.hasText(embyInfo.getEmbyAgreement())) {
                  baseUrl.append(embyInfo.getEmbyAgreement()).append("://");
               }

               if (StringUtils.hasText(embyInfo.getEmbyUrl())) {
                  baseUrl.append(embyInfo.getEmbyUrl());
               }

               if (StringUtils.hasText(embyInfo.getEmbyPort())) {
                  if (embyInfo.getEmbyUrl() != null && !embyInfo.getEmbyUrl().contains(":")) {
                     baseUrl.append(":");
                  }

                  baseUrl.append(embyInfo.getEmbyPort());
               }

               return baseUrl.length() > 0 ? baseUrl.toString() : null;
            } else {
               return embyUrl;
            }
         }
      }
   }

   private String resolveServerName(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (serverConfig == null) {
         return "未知服务器";
      } else if (StringUtils.hasText(serverConfig.serverName())) {
         return serverConfig.serverName();
      } else {
         if (serverConfig.id() != null) {
            EmbyInfo embyInfo = this.embyInfoService.getById(serverConfig.id());
            if (embyInfo != null && StringUtils.hasText(embyInfo.getServerName())) {
               return embyInfo.getServerName();
            }
         }

         return "未知服务器";
      }
   }

   private String resolveServerUrl(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (serverConfig == null) {
         return null;
      } else {
         String configUrl = serverConfig.url();
         if (!StringUtils.hasText(configUrl) || !configUrl.startsWith("http://") && !configUrl.startsWith("https://")) {
            return serverConfig.id() != null ? this.getServerUrl(serverConfig.id()) : configUrl;
         } else {
            return configUrl;
         }
      }
   }
}
