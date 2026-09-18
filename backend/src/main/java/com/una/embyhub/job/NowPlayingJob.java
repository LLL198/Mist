package com.una.embyhub.job;

import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.IpAddressUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.response.emby.SessionSessionInfoResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecord;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecordDetail;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.SimultaneousPlaybackRecordService;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.SessionsServiceApi;
import embyclient.api.UserServiceApi;
import embyclient.model.BaseItemDto;
import embyclient.model.SessionSessionInfo;
import embyclient.model.UserDto;
import embyclient.model.UserPolicy;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Generated;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

@Configuration
@EnableScheduling
public class NowPlayingJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(NowPlayingJob.class);
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private Ip2regionSearcher searchSearcher;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private SimultaneousPlaybackRecordService simultaneousPlaybackRecordService;

   @Scheduled(
      cron = "0 * * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "同时播放检测任务",
      remark = "用户同时播放内容检测"
   )
   public void configureTasks() {
      log.info("正在播放相同用户检测");
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = Optional.ofNullable(this.embyInfoCacheManager.getEnabledConfigs())
         .filter(configs -> !configs.isEmpty())
         .orElse(null);
      if (serverConfigs == null) {
         log.warn("未找到启用的 Emby 服务器配置，终止本次同时播放检测任务");
      } else {
         for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
            try {
               SessionsServiceApi sessionServiceApi = new SessionsServiceApi(this.buildApiClient(serverConfig));
               List<SessionSessionInfo> sessions = sessionServiceApi.getSessions(null, null, null);
               List<SessionSessionInfoResponse> sessionSessionInfoResponses = BeanUtils.convertList(sessions, SessionSessionInfoResponse.class);
               sessionSessionInfoResponses = sessionSessionInfoResponses.stream()
                  .filter(sessionSessionInfo -> sessionSessionInfo.getNowPlayingItem() != null)
                  .collect(Collectors.toList());
               Map<String, List<SessionSessionInfoResponse>> groupedByUserName = sessionSessionInfoResponses.stream()
                  .collect(Collectors.groupingBy(SessionSessionInfoResponse::getUserName));

               for (Entry<String, List<SessionSessionInfoResponse>> entry : groupedByUserName.entrySet()) {
                  String userName = entry.getKey();
                  List<SessionSessionInfoResponse> userSessions = entry.getValue();
                  if (userSessions != null && userSessions.size() > 1) {
                     String disableMessage = "";
                     int playbackThreshold = this.resolvePlaybackThreshold(serverConfig, userSessions.get(0));
                     boolean thresholdReached = playbackThreshold > 0 && userSessions.size() >= playbackThreshold;
                     boolean unlimitedUser = thresholdReached && this.isUnlimitedUser(serverConfig, userSessions.get(0));
                     boolean limitViolated = SimultaneousPlaybackLimitResolver.isLimitViolation(userSessions.size(), playbackThreshold, unlimitedUser);
                     if (limitViolated) {
                        if (this.disableUser(serverConfig, userSessions.get(0))) {
                           disableMessage = "\ud83d\udeab 已自动禁用该用户（超过同时播放限制）。\n\n";
                        }
                     } else if (thresholdReached) {
                        log.info("用户 {} 是管理员或白名单用户，跳过自动禁用和同时播放提醒", userName);
                     }

                     StringBuilder detailsBuilder = new StringBuilder();

                     for (SessionSessionInfoResponse session : userSessions) {
                        detailsBuilder.append("\ud83c\udfac 播放内容：").append(session.getNowPlayingItem().getName()).append("\n");
                        String remoteEndPoint = session.getRemoteEndPoint();
                        String remoteAddress = IpAddressUtils.safeAddressAndIsp(this.searchSearcher, remoteEndPoint);
                        detailsBuilder.append("\ud83c\udf0d 播放地址：").append(StringUtils.hasText(remoteEndPoint) ? remoteEndPoint : "未知");
                        if (StringUtils.hasText(remoteAddress)) {
                           detailsBuilder.append(" ").append(remoteAddress);
                        }

                        detailsBuilder.append("\n");
                        detailsBuilder.append("⏰ 播放时间：")
                           .append(
                              session.getLastActivityDate()
                                 .atZoneSameInstant(ZoneId.of("Asia/Shanghai"))
                                 .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                           )
                           .append("\n\n");
                     }

                     detailsBuilder.append(disableMessage);
                     Map<String, String> extras = new HashMap<>();
                     extras.put("userName", userName);
                     extras.put("playbackDetails", detailsBuilder + "\n");
                     this.saveSimultaneousPlaybackRecord(serverConfig, userSessions);
                     if (limitViolated && this.isSimultaneousPlaybackNotifyEnabled()) {
                        SendMessageRequest sendMessageRequest = new SendMessageRequest();
                        sendMessageRequest.setParseMode("Markdown");
                        sendMessageRequest.setServerUrl(serverConfig.url());
                        sendMessageRequest.setServerName(this.resolveServerName(serverConfig));
                        sendMessageRequest.setExtraVariables(extras);
                        this.notifyUtils
                           .sendMultiChannel(sendMessageRequest, "simultaneous_playback", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
                     }
                  }
               }
            } catch (Exception var22) {
               log.warn("获取服务器 [{}] 的正在播放信息失败: {}", serverConfig.serverName(), var22.getMessage(), var22);
            }
         }
      }
   }

   private ApiClient buildApiClient(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, config);
      return apiClient;
   }

   private String resolveServerName(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      return config != null && config.id() != null
         ? Optional.ofNullable(this.embyInfoService.getById(config.id())).map(embyInfo -> embyInfo.getServerName()).orElse(null)
         : null;
   }

   private int resolvePlaybackThreshold(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, SessionSessionInfoResponse session) {
      String threshold = this.configCacheLoaderUtils.getConfigValue("simultaneous_playback_disable_threshold");
      return SimultaneousPlaybackLimitResolver.resolve(
         threshold,
         serverConfig == null ? null : serverConfig.id(),
         session == null ? null : session.getUserId(),
         session == null ? null : session.getUserName()
      );
   }

   private boolean isSimultaneousPlaybackNotifyEnabled() {
      String notifyEnabled = this.configCacheLoaderUtils.getConfigValue("simultaneous_playback_notify");
      return "true".equalsIgnoreCase(notifyEnabled);
   }

   private void saveSimultaneousPlaybackRecord(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, List<SessionSessionInfoResponse> userSessions) {
      if (serverConfig != null && userSessions != null && !userSessions.isEmpty()) {
         SessionSessionInfoResponse firstSession = userSessions.get(0);
         SimultaneousPlaybackRecord record = new SimultaneousPlaybackRecord();
         record.setEmbyInfoId(serverConfig.id());
         record.setEmbyUserId(firstSession.getUserId());
         record.setEmbyUserName(firstSession.getUserName());
         record.setDetectionTime(this.convertToDate(firstSession.getLastActivityDate()));
         record.setSessionCount(userSessions.size());
         List<SimultaneousPlaybackRecordDetail> details = userSessions.stream().map(session -> {
            SimultaneousPlaybackRecordDetail detail = new SimultaneousPlaybackRecordDetail();
            BaseItemDto nowPlayingItem = session.getNowPlayingItem();
            detail.setItemId(nowPlayingItem != null ? nowPlayingItem.getId() : null);
            detail.setItemName(nowPlayingItem != null ? nowPlayingItem.getName() : null);
            detail.setItemType(nowPlayingItem != null ? nowPlayingItem.getType() : null);
            detail.setPosterUrl(this.buildPosterUrl(session, serverConfig));
            detail.setPlaybackTime(this.convertToDate(session.getLastActivityDate()));
            detail.setClient(session.getClient());
            detail.setDeviceName(session.getDeviceName());
            detail.setRemoteEndpoint(session.getRemoteEndPoint());
            detail.setRemoteAddress(this.resolveRemoteAddress(session.getRemoteEndPoint()));
            return detail;
         }).collect(Collectors.toList());
         this.simultaneousPlaybackRecordService.saveRecordWithDetails(record, details);
      }
   }

   private Date convertToDate(OffsetDateTime offsetDateTime) {
      return offsetDateTime == null ? new Date() : Date.from(offsetDateTime.toInstant());
   }

   private String resolveRemoteAddress(String remoteEndPoint) {
      if (!StringUtils.hasText(remoteEndPoint)) {
         return null;
      } else {
         String address = IpAddressUtils.safeAddressAndIsp(this.searchSearcher, remoteEndPoint);
         return StringUtils.hasText(address) ? address : null;
      }
   }

   private String buildPosterUrl(SessionSessionInfoResponse session, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (session != null && session.getNowPlayingItem() != null) {
         BaseItemDto nowPlayingItem = session.getNowPlayingItem();
         Map<String, String> imageTags = nowPlayingItem.getImageTags();
         if ("Movie".equals(nowPlayingItem.getType()) && imageTags != null && imageTags.get("Primary") != null) {
            return "Items/" + nowPlayingItem.getId() + "/Images/Primary?tag=" + imageTags.get("Primary") + "&quality=90&maxWidth=200";
         } else {
            return "Episode".equals(nowPlayingItem.getType()) && nowPlayingItem.getParentId() != null && nowPlayingItem.getSeriesPrimaryImageTag() != null
               ? "Items/" + nowPlayingItem.getParentId() + "/Images/Primary?tag=" + nowPlayingItem.getSeriesPrimaryImageTag() + "&quality=90&maxWidth=200"
               : null;
         }
      } else {
         return null;
      }
   }

   private boolean isUnlimitedUser(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, SessionSessionInfoResponse session) throws ApiException {
      if (this.isLocalUnlimitedUser(serverConfig, session)) {
         return true;
      } else {
         UserServiceApi userServiceApi = new UserServiceApi(this.buildApiClient(serverConfig));
         UserDto userDto = userServiceApi.getUsersById(session.getUserId());
         if (userDto != null && userDto.getPolicy() != null) {
            Boolean isAdmin = userDto.getPolicy().isIsAdministrator();
            return Boolean.TRUE.equals(isAdmin);
         } else {
            throw new ApiException("无法获取用户信息");
         }
      }
   }

   private boolean isLocalUnlimitedUser(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, SessionSessionInfoResponse session) {
      if (serverConfig != null && serverConfig.id() != null && session != null) {
         EmbyUser localUser = null;
         if (StringUtils.hasText(session.getUserId())) {
            localUser = this.embyUserService
               .lambdaQuery()
               .eq(EmbyUser::getEmbyInfoId, serverConfig.id())
               .eq(EmbyUser::getEmbyUserId, session.getUserId())
               .last("limit 1")
               .one();
         }

         if (localUser == null && StringUtils.hasText(session.getUserName())) {
            localUser = this.embyUserService
               .lambdaQuery()
               .eq(EmbyUser::getEmbyInfoId, serverConfig.id())
               .eq(EmbyUser::getEmbyUserName, session.getUserName())
               .last("limit 1")
               .one();
         }

         return localUser != null
            && (
               Integer.valueOf(1).equals(localUser.getIsAdmin())
                  || Integer.valueOf(1).equals(localUser.getIsPrimaryAdmin())
                  || HostLineTypeEnum.normalize(localUser.getHostLineType()) == HostLineTypeEnum.WHITELIST.getCode()
            );
      } else {
         return false;
      }
   }

   private boolean disableUser(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, SessionSessionInfoResponse session) {
      if (serverConfig != null && session != null && session.getUserId() != null) {
         try {
            UserServiceApi userServiceApi = new UserServiceApi(this.buildApiClient(serverConfig));
            UserDto userDto = userServiceApi.getUsersById(session.getUserId());
            UserPolicy userPolicy = userDto.getPolicy();
            userPolicy.setIsDisabled(true);
            userServiceApi.postUsersByIdPolicy(userPolicy, session.getUserId());
         } catch (ApiException var6) {
            log.error("自动禁用Emby用户失败 userId={} status={} body={}", session.getUserId(), var6.getCode(), var6.getResponseBody(), var6);
            return false;
         }

         this.embyUserService
            .lambdaUpdate()
            .eq(EmbyUser::getEmbyUserId, session.getUserId())
            .eq(EmbyUser::getEmbyInfoId, serverConfig.id())
            .set(EmbyUser::getUserStatus, Integer.valueOf(1))
            .update();
         return true;
      } else {
         return false;
      }
   }
}
