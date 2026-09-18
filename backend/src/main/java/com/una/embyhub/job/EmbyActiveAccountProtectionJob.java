package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.mapper.PlayRecordsMapper;
import com.una.embyhub.mapper.PlaybackReportingRecordMapper;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.PlayRecords;
import com.una.embyhub.model.entity.PlaybackReportingRecord;
import com.una.embyhub.service.EmbyUserService;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.UserServiceApi;
import embyclient.model.QueryResultUserDto;
import embyclient.model.UserDto;
import embyclient.model.UserPolicy;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Configuration
@EnableScheduling
public class EmbyActiveAccountProtectionJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyActiveAccountProtectionJob.class);
   private static final int DEFAULT_ACTIVITY_CHECK_DAYS = 21;
   private static final int DEFAULT_DISABLED_RETENTION_DAYS = 15;
   private static final String DISABLE_REASON_ACTIVE_PROTECTION = "ACTIVE_PROTECTION";
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final EmbyUserService embyUserService;
   private final PlaybackReportingRecordMapper playbackReportingRecordMapper;
   private final PlayRecordsMapper playRecordsMapper;

   public EmbyActiveAccountProtectionJob(
      ConfigCacheLoaderUtils configCacheLoaderUtils,
      EmbyInfoCacheManagerUtils embyInfoCacheManager,
      EmbyUserService embyUserService,
      PlaybackReportingRecordMapper playbackReportingRecordMapper,
      PlayRecordsMapper playRecordsMapper
   ) {
      this.configCacheLoaderUtils = configCacheLoaderUtils;
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.embyUserService = embyUserService;
      this.playbackReportingRecordMapper = playbackReportingRecordMapper;
      this.playRecordsMapper = playRecordsMapper;
   }

   @Scheduled(
      cron = "0 30 8 * * ?",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "活跃保号任务",
      remark = "按配置服务器检测长期未观看用户并禁用或删除"
   )
   public void run() {
      String configValue = this.configCacheLoaderUtils.getConfigValue("emby_active_account_protection");
      if (StringUtils.hasText(configValue)) {
         EmbyActiveAccountProtectionJob.ActiveProtectionConfig config = this.parseConfig(configValue);
         if (CollectionUtils.isEmpty(config.serverIds())) {
            log.info("活跃保号任务跳过：未配置需要执行的服务器");
         } else {
            log.info(
               "开始执行活跃保号任务：serverIds={}, activityCheckDays={}, disabledRetentionDays={}, deleteAfterDisabled={}",
               config.serverIds(),
               config.activityCheckDays(),
               config.disabledRetentionDays(),
               config.deleteAfterDisabled()
            );

            for (Long serverId : config.serverIds()) {
               this.runServer(serverId, config);
            }

            log.info("活跃保号任务执行完成");
         }
      }
   }

   private void runServer(Long serverId, EmbyActiveAccountProtectionJob.ActiveProtectionConfig config) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig;
      try {
         serverConfig = this.embyInfoCacheManager.getRequiredConfigById(serverId);
      } catch (Exception var5) {
         log.warn("活跃保号跳过服务器：服务器不存在或未启用，serverId={}", serverId);
         return;
      }

      if (serverConfig != null && serverConfig.id() != null) {
         Map<String, UserDto> remoteUserMap = this.loadRemoteUsers(serverConfig);
         if (remoteUserMap.isEmpty()) {
            log.warn("活跃保号跳过服务器：未获取到远端用户，serverId={}", serverId);
         } else {
            if (config.deleteAfterDisabled()) {
               this.deleteInactiveDisabledUsers(serverConfig, remoteUserMap, config);
            }

            this.disableInactiveUsers(serverConfig, remoteUserMap, config);
         }
      } else {
         log.warn("活跃保号跳过服务器：服务器不存在或未启用，serverId={}", serverId);
      }
   }

   private Map<String, UserDto> loadRemoteUsers(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      try {
         QueryResultUserDto remoteUsers = this.buildUserServiceApi(serverConfig).getUsersQuery(null, null, null, null, null, null);
         if (remoteUsers != null && !CollectionUtils.isEmpty(remoteUsers.getItems())) {
            Map<String, UserDto> result = new HashMap<>();

            for (UserDto user : remoteUsers.getItems()) {
               if (StringUtils.hasText(user.getId())) {
                  result.put(user.getId(), user);
               }

               if (StringUtils.hasText(user.getName())) {
                  result.put(user.getName(), user);
               }
            }

            return result;
         } else {
            return Collections.emptyMap();
         }
      } catch (ApiException var6) {
         log.error("活跃保号获取远端用户失败：serverId={}, status={}, body={}", serverConfig.id(), var6.getCode(), var6.getResponseBody(), var6);
         return Collections.emptyMap();
      } catch (Exception var7) {
         log.error("活跃保号获取远端用户失败：serverId={}", serverConfig.id(), var7);
         return Collections.emptyMap();
      }
   }

   private void deleteInactiveDisabledUsers(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, Map<String, UserDto> remoteUserMap, EmbyActiveAccountProtectionJob.ActiveProtectionConfig config
   ) {
      for (EmbyUser user : this.baseUserQuery(serverConfig.id())
         .eq(EmbyUser::getUserStatus, Integer.valueOf(1))
         .eq(EmbyUser::getDisableReason, "ACTIVE_PROTECTION")
         .list()) {
         UserDto remoteUser = this.resolveRemoteUser(remoteUserMap, user);
         if (remoteUser != null) {
            Date lastActivity = this.resolveLastActivity(serverConfig.id(), user, remoteUser);
            if (this.isInactive(lastActivity, config.disabledRetentionDays())) {
               try {
                  log.info("活跃保号删除用户：serverId={}, userName={}, lastActivity={}", serverConfig.id(), user.getEmbyUserName(), this.formatNullable(lastActivity));
                  this.embyUserService.deleteByUserId(Collections.singletonList(user.getId()));
               } catch (Exception var10) {
                  log.error("活跃保号删除用户失败：serverId={}, userId={}, embyUserName={}", serverConfig.id(), user.getId(), user.getEmbyUserName(), var10);
               }
            }
         }
      }
   }

   private void disableInactiveUsers(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, Map<String, UserDto> remoteUserMap, EmbyActiveAccountProtectionJob.ActiveProtectionConfig config
   ) {
      for (EmbyUser user : this.baseUserQuery(serverConfig.id()).eq(EmbyUser::getUserStatus, Integer.valueOf(0)).list()) {
         UserDto remoteUser = this.resolveRemoteUser(remoteUserMap, user);
         if (remoteUser != null) {
            Date lastActivity = this.resolveLastActivity(serverConfig.id(), user, remoteUser);
            if (this.isInactive(lastActivity, config.activityCheckDays())) {
               try {
                  this.disableRemoteUser(serverConfig, remoteUser);
                  EmbyUser update = new EmbyUser();
                  update.setId(user.getId());
                  update.setUserStatus(1);
                  update.setDisableReason("ACTIVE_PROTECTION");
                  update.setDisabledDatetime(new Date());
                  this.embyUserService.updateById(update);
                  log.info("活跃保号禁用用户：serverId={}, userName={}, lastActivity={}", serverConfig.id(), user.getEmbyUserName(), this.formatNullable(lastActivity));
               } catch (ApiException var10) {
                  log.error(
                     "活跃保号禁用远端用户失败：serverId={}, userName={}, status={}, body={}",
                     serverConfig.id(),
                     user.getEmbyUserName(),
                     var10.getCode(),
                     var10.getResponseBody(),
                     var10
                  );
               } catch (Exception var11) {
                  log.error("活跃保号禁用用户失败：serverId={}, userName={}", serverConfig.id(), user.getEmbyUserName(), var11);
               }
            }
         }
      }
   }

   private LambdaQueryChainWrapper<EmbyUser> baseUserQuery(Long serverId) {
      return new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper())
         .eq(EmbyUser::getEmbyInfoId, serverId)
         .ne(EmbyUser::getIsAdmin, Integer.valueOf(1))
         .and(wrapper -> wrapper.ne(EmbyUser::getHostLineType, Integer.valueOf(HostLineTypeEnum.WHITELIST.getCode())).or().isNull(EmbyUser::getHostLineType));
   }

   private UserDto resolveRemoteUser(Map<String, UserDto> remoteUserMap, EmbyUser user) {
      if (StringUtils.hasText(user.getEmbyUserId())) {
         UserDto remoteUser = remoteUserMap.get(user.getEmbyUserId());
         if (remoteUser != null) {
            return remoteUser;
         }
      }

      return StringUtils.hasText(user.getEmbyUserName()) ? remoteUserMap.get(user.getEmbyUserName()) : null;
   }

   private Date resolveLastActivity(Long serverId, EmbyUser user, UserDto remoteUser) {
      Date remoteLastActivity = this.toDate(remoteUser.getLastActivityDate());
      if (remoteLastActivity != null) {
         return remoteLastActivity;
      } else {
         Date playbackReportingActivity = this.findLatestPlaybackReportingActivity(serverId, user);
         return playbackReportingActivity != null ? playbackReportingActivity : this.findLatestPlayRecordsActivity(serverId, user);
      }
   }

   private Date findLatestPlaybackReportingActivity(Long serverId, EmbyUser user) {
      PlaybackReportingRecord record = new LambdaQueryChainWrapper<>(this.playbackReportingRecordMapper)
         .eq(PlaybackReportingRecord::getEmbyInfoId, serverId)
         .and(
            wrapper -> wrapper.eq(StringUtils.hasText(user.getEmbyUserId()), PlaybackReportingRecord::getUserId, user.getEmbyUserId())
                  .or()
                  .eq(StringUtils.hasText(user.getEmbyUserName()), PlaybackReportingRecord::getUserName, user.getEmbyUserName())
         )
         .orderByDesc(PlaybackReportingRecord::getPlayDate)
         .last("limit 1")
         .one();
      return record == null ? null : record.getPlayDate();
   }

   private Date findLatestPlayRecordsActivity(Long serverId, EmbyUser user) {
      PlayRecords record = new LambdaQueryChainWrapper<>(this.playRecordsMapper)
         .eq(PlayRecords::getEmbyInfoId, serverId)
         .and(
            wrapper -> wrapper.eq(StringUtils.hasText(user.getEmbyUserId()), PlayRecords::getEmbyUserId, user.getEmbyUserId())
                  .or()
                  .eq(StringUtils.hasText(user.getEmbyUserName()), PlayRecords::getEmbyUserName, user.getEmbyUserName())
         )
         .orderByDesc(PlayRecords::getPlayDate)
         .last("limit 1")
         .one();
      return record == null ? null : record.getPlayDate();
   }

   private void disableRemoteUser(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, UserDto remoteUser) throws ApiException {
      UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
      UserDto latestUser = userServiceApi.getUsersById(remoteUser.getId());
      UserPolicy policy = latestUser.getPolicy() == null ? new UserPolicy() : latestUser.getPolicy();
      policy.setIsDisabled(true);
      userServiceApi.postUsersByIdPolicy(policy, remoteUser.getId());
   }

   private UserServiceApi buildUserServiceApi(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, serverConfig);
      return new UserServiceApi(apiClient);
   }

   private Date toDate(OffsetDateTime offsetDateTime) {
      return offsetDateTime == null ? null : Date.from(offsetDateTime.toInstant());
   }

   private boolean isInactive(Date lastActivity, int days) {
      return lastActivity == null ? true : DateUtil.offsetDay(lastActivity, days).before(new Date());
   }

   private String formatNullable(Date date) {
      return date == null ? "无播放记录" : DateUtil.formatDateTime(date);
   }

   private EmbyActiveAccountProtectionJob.ActiveProtectionConfig parseConfig(String configValue) {
      try {
         JSONObject json = JSON.parseObject(configValue);
         List<Long> serverIds = this.parseServerIds(json.get("serverIds"));
         int activityCheckDays = this.positiveOrDefault(json.getIntValue("activityCheckDays"), 21);
         int disabledRetentionDays = this.positiveOrDefault(json.getIntValue("disabledRetentionDays"), 15);
         Boolean deleteAfterDisabledValue = json.getBoolean("deleteAfterDisabled");
         boolean deleteAfterDisabled = deleteAfterDisabledValue == null || deleteAfterDisabledValue;
         return new EmbyActiveAccountProtectionJob.ActiveProtectionConfig(serverIds, activityCheckDays, disabledRetentionDays, deleteAfterDisabled);
      } catch (Exception var8) {
         log.warn("活跃保号配置解析失败，已跳过执行：{}", configValue, var8);
         return new EmbyActiveAccountProtectionJob.ActiveProtectionConfig(List.of(), 21, 15, true);
      }
   }

   private List<Long> parseServerIds(Object value) {
      Set<Long> result = new LinkedHashSet<>();
      if (value instanceof JSONArray) {
         for (Object item : (JSONArray)value) {
            this.addServerId(result, item);
         }
      } else if (value instanceof Iterable) {
         for (Object item : (Iterable)value) {
            this.addServerId(result, item);
         }
      } else {
         String text = String.valueOf(value == null ? "" : value).trim();
         if (StringUtils.hasText(text)) {
            for (String item : text.split("[,，\\s]+")) {
               this.addServerId(result, item);
            }
         }
      }

      return result.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
   }

   private void addServerId(Set<Long> result, Object value) {
      String text = String.valueOf(value == null ? "" : value).trim();
      if (StringUtils.hasText(text)) {
         try {
            long serverId = Long.parseLong(text);
            if (serverId > 0L) {
               result.add(serverId);
            }
         } catch (NumberFormatException var6) {
            log.warn("活跃保号忽略无效服务器 ID：{}", text);
         }
      }
   }

   private int positiveOrDefault(int value, int fallback) {
      return value > 0 ? value : fallback;
   }

   private static record ActiveProtectionConfig(List<Long> serverIds, int activityCheckDays, int disabledRetentionDays, boolean deleteAfterDisabled) {
   }
}
