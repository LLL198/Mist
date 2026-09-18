package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.PlayRecords;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.PlayRecordsService;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.ActivityLogServiceApi;
import embyclient.model.QueryResultActivityLogEntry;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

@Configuration
@EnableScheduling
public class PlayRecordsJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PlayRecordsJob.class);
   @Autowired
   private PlayRecordsService playRecordsService;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;

   @Scheduled(
      cron = "0 0 * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "播放记录任务",
      remark = "用户播放记录任务"
   )
   public void configureTasks() {
      log.info("开始执行播放记录定时任务：{}", DateUtil.formatDateTime(new Date()));
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
      if (CollectionUtils.isEmpty(serverConfigs)) {
         serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
      }

      for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
         Date date = DateUtil.beginOfDay(new Date());
         PlayRecords playRecords = new LambdaQueryChainWrapper<>(this.playRecordsService.getBaseMapper())
            .eq(serverConfig.id() != null, PlayRecords::getEmbyInfoId, serverConfig.id())
            .orderByDesc(PlayRecords::getPlayDate)
            .last("limit 1")
            .one();
         if (playRecords != null) {
            date = playRecords.getPlayDate();
         }

         ActivityLogServiceApi activityLogServiceApi = new ActivityLogServiceApi(this.buildApiClient(serverConfig));

         try {
            OffsetDateTime startDate = OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            QueryResultActivityLogEntry queryResultActivityLogEntry = activityLogServiceApi.getSystemActivitylogEntries(null, null, startDate);
            this.parseAndExtractData(JSONObject.toJSONString(queryResultActivityLogEntry), serverConfig.id());
         } catch (ApiException var9) {
            log.error("播放记录获取失败", (Throwable)var9);
         }
      }
   }

   public void parseAndExtractData(String jsonData, Long embyInfoId) {
      JSONObject rootObject = JSON.parseObject(jsonData);
      JSONArray itemsArray = rootObject.getJSONArray("Items");
      if (itemsArray != null && itemsArray.size() > 0) {
         for (int i = 0; i < itemsArray.size(); i++) {
            JSONObject itemObject = itemsArray.getJSONObject(i);
            String embyUserName = itemObject.getString("Name");
            String embyUserId = "0";
            embyUserName = this.extractUsernameFromName(embyUserName);
            String recordType = itemObject.getString("Type");
            if ("VideoPlayback".equals(recordType)
               || "VideoPlaybackStopped".equals(recordType)
               || "playback.start".equals(recordType)
               || "playback.stop".equals(recordType)) {
               String date = itemObject.getString("Date");
               String device = this.extractDeviceFromName(itemObject.getString("Name"));
               String content = this.extractContentFromName(itemObject.getString("Name"));
               EmbyUser embyUser = new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper()).eq(EmbyUser::getEmbyUserName, embyUserName).one();
               if (embyUser != null) {
                  embyUserId = embyUser.getEmbyUserId();
               }

               PlayRecords record = new PlayRecords();
               record.setEmbyUserId(embyUserId);
               record.setPlayDate(this.convertDateFormat(date));
               record.setRecordType(recordType);
               record.setDevice(device);
               record.setContent(content);
               record.setEmbyUserName(embyUserName);
               record.setEmbyInfoId(embyInfoId);
               this.playRecordsService.save(record);
            }
         }
      }
   }

   public String extractUsernameFromName(String name) {
      int index = name.indexOf("在");
      if (index > 0) {
         return name.substring(0, index).trim();
      } else {
         int stopIndex = name.indexOf("已停止播放");
         int upIndex = name.indexOf("上");
         return upIndex >= 0 && stopIndex > upIndex ? name.substring(upIndex + 1, stopIndex).trim() : "未知用户";
      }
   }

   public String extractDeviceFromName(String name) {
      int startIndex = name.indexOf("在") + 1;
      int endIndex = name.indexOf("上");
      if (startIndex > 0 && endIndex > startIndex) {
         return name.substring(startIndex, endIndex).trim();
      } else {
         return endIndex > 0 ? name.substring(0, endIndex).trim() : "未知设备";
      }
   }

   public String extractContentFromName(String name) {
      int startIndex = name.indexOf("播放") + 2;
      return startIndex > 2 ? name.substring(startIndex).trim() : "未知内容";
   }

   public Date convertDateFormat(String isoDate) {
      try {
         return JSON.parseObject("{\"date\":\"" + isoDate + "\"}").getDate("date");
      } catch (Exception var3) {
         return new Date();
      }
   }

   private ApiClient buildApiClient(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, config);
      return apiClient;
   }
}
