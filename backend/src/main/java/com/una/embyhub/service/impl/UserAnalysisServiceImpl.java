package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.PlaybackReportingLocationUtils;
import com.una.embyhub.mapper.UserAnalysisMapper;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisDimensionResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisOverviewResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisRadarResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisTimelineResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisUserOptionResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisUserResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.UserAnalysisService;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.UserServiceApi;
import embyclient.model.QueryResultUserDto;
import embyclient.model.UserDto;
import embyclient.model.UserPolicy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserAnalysisServiceImpl implements UserAnalysisService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(UserAnalysisServiceImpl.class);
   private static final int DIMENSION_LIMIT = 8;
   private static final int TIMELINE_LIMIT = 14;
   private static final int MAX_RANGE_DAYS = 7;
   private static final List<String> TIME_PERIOD_LABELS = List.of("凌晨", "上午", "下午", "夜间");
   @Autowired
   private UserAnalysisMapper userAnalysisMapper;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;

   @Override
   public UserAnalysisOverviewResponse adminOverview(
      Long userId, Long embyInfoId, String embyUserId, String embyUserName, LocalDate startDate, LocalDate endDate
   ) {
      this.ensureAdmin();
      UserAnalysisServiceImpl.DateRange range = this.resolveDateRange(startDate, endDate);
      List<UserAnalysisUserResponse> users;
      if (userId == null) {
         if (!StringUtils.hasText(embyUserId) && !StringUtils.hasText(embyUserName)) {
            users = this.loadTopUsers(embyInfoId, range);
         } else {
            if (embyInfoId == null) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请选择服务器");
            }

            UserAnalysisUserResponse user = this.userAnalysisMapper
               .selectRemoteUserSummary(embyInfoId, this.cleanText(embyUserId), this.cleanText(embyUserName), range.startDate(), range.endDate());
            if (user == null) {
               throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_FOUND);
            }

            this.completeRemoteUser(user, embyInfoId, embyUserId, embyUserName);
            users = new ArrayList<>();
            users.add(user);
         }
      } else {
         UserAnalysisUserResponse user = this.userAnalysisMapper.selectUserSummary(userId, embyInfoId, range.startDate(), range.endDate());
         if (user == null) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         }

         users = new ArrayList<>();
         users.add(user);
      }

      this.hydrateUsers(users, range);
      UserAnalysisOverviewResponse response = new UserAnalysisOverviewResponse();
      response.setAdminView(true);
      response.setSelectedUserId(userId != null ? userId : this.firstUserId(users));
      response.setUsers(users);
      response.setEmptyMessage(users.isEmpty() ? "无数据" : null);
      return response;
   }

   private List<UserAnalysisUserResponse> loadTopUsers(Long embyInfoId, UserAnalysisServiceImpl.DateRange range) {
      List<UserAnalysisUserOptionResponse> options = this.loadUserOptions(embyInfoId);
      Map<String, UserAnalysisUserResponse> summaryIndex = this.buildSummaryIndex(
         this.safeList(this.userAnalysisMapper.selectPlaybackUserSummaries(embyInfoId, range.startDate(), range.endDate()))
      );
      List<UserAnalysisUserResponse> result = new ArrayList<>();

      for (UserAnalysisUserOptionResponse option : options) {
         UserAnalysisUserResponse user = this.findSummary(summaryIndex, option);
         if (user == null) {
            user = new UserAnalysisUserResponse();
         }

         this.applyOptionToUser(user, option);
         result.add(user);
      }

      if (result.isEmpty()) {
         result.addAll(summaryIndex.values());
      }

      result.sort(this::compareTopUsers);
      return (List<UserAnalysisUserResponse>)(result.size() > 10 ? new ArrayList<>(result.subList(0, 10)) : result);
   }

   private Map<String, UserAnalysisUserResponse> buildSummaryIndex(List<UserAnalysisUserResponse> summaries) {
      Map<String, UserAnalysisUserResponse> index = new LinkedHashMap<>();

      for (UserAnalysisUserResponse summary : summaries) {
         this.putSummary(index, "id", summary.getEmbyInfoId(), summary.getEmbyUserId(), summary);
         this.putSummary(index, "name", summary.getEmbyInfoId(), summary.getEmbyUserName(), summary);
      }

      return index;
   }

   private void putSummary(Map<String, UserAnalysisUserResponse> index, String type, Long embyInfoId, String value, UserAnalysisUserResponse summary) {
      String key = this.optionLookupKey(type, embyInfoId, value);
      if (StringUtils.hasText(key)) {
         index.putIfAbsent(key, summary);
      }
   }

   private UserAnalysisUserResponse findSummary(Map<String, UserAnalysisUserResponse> summaryIndex, UserAnalysisUserOptionResponse option) {
      UserAnalysisUserResponse byId = summaryIndex.get(this.optionLookupKey("id", option.getEmbyInfoId(), option.getEmbyUserId()));
      return byId != null ? byId : summaryIndex.get(this.optionLookupKey("name", option.getEmbyInfoId(), option.getEmbyUserName()));
   }

   private int compareTopUsers(UserAnalysisUserResponse left, UserAnalysisUserResponse right) {
      int byPlay = Long.compare(this.safeLong(right.getTotalPlayCount()), this.safeLong(left.getTotalPlayCount()));
      if (byPlay != 0) {
         return byPlay;
      } else {
         int byLastPlay = Long.compare(this.dateMillis(right.getLastPlayDatetime()), this.dateMillis(left.getLastPlayDatetime()));
         return byLastPlay != 0
            ? byLastPlay
            : this.defaultLabel(left.getEmbyUserName(), "").compareToIgnoreCase(this.defaultLabel(right.getEmbyUserName(), ""));
      }
   }

   private long dateMillis(Date value) {
      return value == null ? 0L : value.getTime();
   }

   @Override
   public List<UserAnalysisUserOptionResponse> activeUserOptions(Long embyInfoId) {
      this.ensureAdmin();
      return this.loadUserOptions(embyInfoId);
   }

   @Override
   public UserAnalysisOverviewResponse myOverview(LocalDate startDate, LocalDate endDate) {
      UserAnalysisServiceImpl.DateRange range = this.resolveDateRange(startDate, endDate);
      Long userId = this.currentUserId();
      UserAnalysisUserResponse user = this.userAnalysisMapper.selectUserSummary(userId, null, range.startDate(), range.endDate());
      if (user == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         List<UserAnalysisUserResponse> users = new ArrayList<>();
         users.add(user);
         this.hydrateUsers(users, range);
         UserAnalysisOverviewResponse response = new UserAnalysisOverviewResponse();
         response.setAdminView(false);
         response.setSelectedUserId(userId);
         response.setUsers(users);
         response.setEmptyMessage(users.isEmpty() ? "无数据" : null);
         return response;
      }
   }

   private List<UserAnalysisUserOptionResponse> loadUserOptions(Long embyInfoId) {
      List<UserAnalysisUserOptionResponse> localOptions = this.safeList(this.userAnalysisMapper.selectActiveUserOptions(embyInfoId));
      Map<String, UserAnalysisUserOptionResponse> localIndex = this.buildLocalOptionIndex(localOptions);
      Map<String, UserAnalysisUserOptionResponse> result = new LinkedHashMap<>();

      for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : this.resolveServerConfigs(embyInfoId)) {
         for (UserDto remoteUser : this.fetchRemoteUsers(serverConfig)) {
            UserAnalysisUserOptionResponse option = this.buildRemoteOption(serverConfig, remoteUser, localIndex);
            String key = this.optionKey(option);
            if (StringUtils.hasText(key)) {
               result.put(key, option);
            }
         }
      }

      for (UserAnalysisUserOptionResponse localOption : localOptions) {
         String key = this.optionKey(localOption);
         if (StringUtils.hasText(key)) {
            result.putIfAbsent(key, localOption);
         }
      }

      return new ArrayList<>(result.values());
   }

   private List<EmbyInfoCacheManagerUtils.EmbyServerConfig> resolveServerConfigs(Long embyInfoId) {
      if (embyInfoId != null) {
         return Collections.singletonList(this.embyInfoCacheManager.getRequiredConfigById(embyInfoId));
      } else {
         List<EmbyInfoCacheManagerUtils.EmbyServerConfig> configs = this.embyInfoCacheManager.getEnabledConfigs();
         return configs == null ? Collections.emptyList() : configs;
      }
   }

   private Map<String, UserAnalysisUserOptionResponse> buildLocalOptionIndex(List<UserAnalysisUserOptionResponse> localOptions) {
      Map<String, UserAnalysisUserOptionResponse> index = new LinkedHashMap<>();

      for (UserAnalysisUserOptionResponse option : localOptions) {
         this.putLocalOption(index, "id", option.getEmbyInfoId(), option.getEmbyUserId(), option);
         this.putLocalOption(index, "name", option.getEmbyInfoId(), option.getEmbyUserName(), option);
      }

      return index;
   }

   private void putLocalOption(
      Map<String, UserAnalysisUserOptionResponse> index, String type, Long embyInfoId, String value, UserAnalysisUserOptionResponse option
   ) {
      String key = this.optionLookupKey(type, embyInfoId, value);
      if (StringUtils.hasText(key)) {
         index.putIfAbsent(key, option);
      }
   }

   private List<UserDto> fetchRemoteUsers(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      try {
         QueryResultUserDto queryResult = new UserServiceApi(this.buildApiClient(serverConfig)).getUsersQuery(null, null, null, null, null, null);
         return queryResult != null && queryResult.getItems() != null ? queryResult.getItems() : Collections.emptyList();
      } catch (ApiException var3) {
         log.warn("用户分析获取 Emby 用户失败：serverId={}, status={}, body={}", serverConfig.id(), var3.getCode(), var3.getResponseBody(), var3);
         return Collections.emptyList();
      }
   }

   private ApiClient buildApiClient(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, serverConfig);
      return apiClient;
   }

   private UserAnalysisUserOptionResponse buildRemoteOption(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, UserDto remoteUser, Map<String, UserAnalysisUserOptionResponse> localIndex
   ) {
      UserAnalysisUserOptionResponse local = this.findLocalOption(localIndex, serverConfig.id(), remoteUser.getId(), remoteUser.getName());
      UserAnalysisUserOptionResponse option = new UserAnalysisUserOptionResponse();
      option.setUserId(local == null ? null : local.getUserId());
      option.setEmbyInfoId(serverConfig.id());
      option.setEmbyUserId(this.cleanText(remoteUser.getId()));
      option.setEmbyUserName(this.cleanText(remoteUser.getName()));
      option.setServerName(serverConfig.serverName());
      option.setUserStatus(local != null && local.getUserStatus() != null ? local.getUserStatus() : (this.remoteUserDisabled(remoteUser) ? 1 : 0));
      return option;
   }

   private UserAnalysisUserOptionResponse findLocalOption(
      Map<String, UserAnalysisUserOptionResponse> localIndex, Long embyInfoId, String embyUserId, String embyUserName
   ) {
      UserAnalysisUserOptionResponse byId = localIndex.get(this.optionLookupKey("id", embyInfoId, embyUserId));
      return byId != null ? byId : localIndex.get(this.optionLookupKey("name", embyInfoId, embyUserName));
   }

   private boolean remoteUserDisabled(UserDto remoteUser) {
      UserPolicy policy = remoteUser == null ? null : remoteUser.getPolicy();
      return policy != null && Boolean.TRUE.equals(policy.isIsDisabled());
   }

   private void completeRemoteUser(UserAnalysisUserResponse user, Long embyInfoId, String embyUserId, String embyUserName) {
      UserAnalysisUserOptionResponse option = this.loadUserOptions(embyInfoId)
         .stream()
         .filter(item -> this.remoteUserMatches(item, embyInfoId, embyUserId, embyUserName))
         .findFirst()
         .orElse(null);
      if (option != null) {
         this.applyOptionToUser(user, option);
      }

      if (!StringUtils.hasText(user.getEmbyUserId())) {
         user.setEmbyUserId(this.cleanText(embyUserId));
      }

      if (!StringUtils.hasText(user.getEmbyUserName())) {
         user.setEmbyUserName(this.cleanText(embyUserName));
      }

      if (user.getEmbyInfoId() == null) {
         user.setEmbyInfoId(embyInfoId);
      }

      if (!StringUtils.hasText(user.getServerName())) {
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
         user.setServerName(serverConfig.serverName());
      }

      if (user.getUserStatus() == null) {
         user.setUserStatus(0);
      }
   }

   private void applyOptionToUser(UserAnalysisUserResponse user, UserAnalysisUserOptionResponse option) {
      user.setUserId(option.getUserId());
      user.setEmbyInfoId(option.getEmbyInfoId());
      if (StringUtils.hasText(option.getEmbyUserId())) {
         user.setEmbyUserId(option.getEmbyUserId());
      }

      if (StringUtils.hasText(option.getEmbyUserName())) {
         user.setEmbyUserName(option.getEmbyUserName());
      }

      if (StringUtils.hasText(option.getServerName())) {
         user.setServerName(option.getServerName());
      }

      if (option.getUserStatus() != null) {
         user.setUserStatus(option.getUserStatus());
      }
   }

   private boolean remoteUserMatches(UserAnalysisUserOptionResponse option, Long embyInfoId, String embyUserId, String embyUserName) {
      if (!Objects.equals(option.getEmbyInfoId(), embyInfoId)) {
         return false;
      } else {
         return StringUtils.hasText(embyUserId) && Objects.equals(option.getEmbyUserId(), this.cleanText(embyUserId))
            ? true
            : StringUtils.hasText(embyUserName) && Objects.equals(option.getEmbyUserName(), this.cleanText(embyUserName));
      }
   }

   private String optionKey(UserAnalysisUserOptionResponse option) {
      if (option == null || option.getEmbyInfoId() == null) {
         return "";
      } else {
         return StringUtils.hasText(option.getEmbyUserId())
            ? this.optionLookupKey("id", option.getEmbyInfoId(), option.getEmbyUserId())
            : this.optionLookupKey("name", option.getEmbyInfoId(), option.getEmbyUserName());
      }
   }

   private String optionLookupKey(String type, Long embyInfoId, String value) {
      String cleanValue = this.cleanText(value);
      return embyInfoId != null && StringUtils.hasText(cleanValue) ? type + ":" + embyInfoId + ":" + cleanValue : "";
   }

   private String cleanText(String value) {
      return StringUtils.hasText(value) ? value.trim() : null;
   }

   private void ensureAdmin() {
      if (!StpUtil.hasPermission("admin")) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   private Long currentUserId() {
      if (StpUtil.getSession().get("user") instanceof EmbyUser embyUser && embyUser.getId() != null) {
         return embyUser.getId();
      }

      return StpUtil.getLoginIdAsLong();
   }

   private UserAnalysisServiceImpl.DateRange resolveDateRange(LocalDate startDate, LocalDate endDate) {
      LocalDate today = LocalDate.now();
      LocalDate resolvedStart = startDate;
      LocalDate resolvedEnd = endDate;
      if (startDate == null && endDate == null) {
         resolvedStart = today;
         resolvedEnd = today;
      } else if (startDate == null) {
         resolvedStart = endDate;
      } else if (endDate == null) {
         resolvedEnd = startDate;
      }

      if (resolvedEnd.isBefore(resolvedStart)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "结束日期不能早于开始日期");
      } else if (ChronoUnit.DAYS.between(resolvedStart, resolvedEnd) + 1L > 7L) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "时间范围最多 7 天");
      } else {
         return new UserAnalysisServiceImpl.DateRange(resolvedStart, resolvedEnd);
      }
   }

   private void hydrateUsers(List<UserAnalysisUserResponse> users, UserAnalysisServiceImpl.DateRange range) {
      for (UserAnalysisUserResponse user : users) {
         this.normalizeTotals(user);
         user.setLocationStats(
            this.fillPercent(
               this.compactLocationStats(
                  this.userAnalysisMapper
                     .selectLocationStats(user.getEmbyInfoId(), user.getEmbyUserId(), user.getEmbyUserName(), range.startDate(), range.endDate(), 8)
               )
            )
         );
         user.setPlayerStats(
            this.fillPercent(
               this.normalizeDimensionStats(
                  this.userAnalysisMapper
                     .selectPlayerStats(user.getEmbyInfoId(), user.getEmbyUserId(), user.getEmbyUserName(), range.startDate(), range.endDate(), 8),
                  false
               )
            )
         );
         user.setItemTypeStats(
            this.fillPercent(
               this.normalizeDimensionStats(
                  this.userAnalysisMapper
                     .selectItemTypeStats(user.getEmbyInfoId(), user.getEmbyUserId(), user.getEmbyUserName(), range.startDate(), range.endDate(), 8),
                  true
               )
            )
         );
         user.setTimePeriodStats(
            this.fillPercent(
               this.fillTimePeriodStats(
                  this.normalizeDimensionStats(
                     this.userAnalysisMapper
                        .selectTimePeriodStats(user.getEmbyInfoId(), user.getEmbyUserId(), user.getEmbyUserName(), range.startDate(), range.endDate()),
                     false
                  )
               )
            )
         );
         List<UserAnalysisTimelineResponse> timeline = this.safeList(
            this.userAnalysisMapper
               .selectPlayTimeline(user.getEmbyInfoId(), user.getEmbyUserId(), user.getEmbyUserName(), range.startDate(), range.endDate(), 14)
         );
         Collections.reverse(timeline);
         user.setPlayTimeline(timeline);
         user.setRadarStats(this.buildRadarStats(user));
      }
   }

   private void normalizeTotals(UserAnalysisUserResponse user) {
      if (user.getTotalPlayCount() == null) {
         user.setTotalPlayCount(0L);
      }

      if (user.getTotalDurationSeconds() == null) {
         user.setTotalDurationSeconds(0L);
      }

      if (user.getActiveDayCount() == null) {
         user.setActiveDayCount(0L);
      }
   }

   private List<UserAnalysisDimensionResponse> compactLocationStats(List<UserAnalysisDimensionResponse> source) {
      Map<String, UserAnalysisDimensionResponse> compacted = new LinkedHashMap<>();

      for (UserAnalysisDimensionResponse item : this.safeList(source)) {
         String label = PlaybackReportingLocationUtils.parse(item.getLabel()).displayName();
         if (!StringUtils.hasText(label)) {
            label = "未知地点";
         }

         UserAnalysisDimensionResponse target = compacted.computeIfAbsent(label, key -> {
            UserAnalysisDimensionResponse next = new UserAnalysisDimensionResponse();
            next.setLabel(key);
            next.setCount(0L);
            next.setTotalDurationSeconds(0L);
            return next;
         });
         target.setCount(target.getCount() + this.safeLong(item.getCount()));
         target.setTotalDurationSeconds(target.getTotalDurationSeconds() + this.safeLong(item.getTotalDurationSeconds()));
      }

      return new ArrayList<>(compacted.values());
   }

   private List<UserAnalysisDimensionResponse> normalizeDimensionStats(List<UserAnalysisDimensionResponse> source, boolean itemType) {
      List<UserAnalysisDimensionResponse> result = new ArrayList<>();

      for (UserAnalysisDimensionResponse item : this.safeList(source)) {
         UserAnalysisDimensionResponse normalized = new UserAnalysisDimensionResponse();
         normalized.setLabel(itemType ? this.itemTypeLabel(item.getLabel()) : this.defaultLabel(item.getLabel(), "未知"));
         normalized.setCount(this.safeLong(item.getCount()));
         normalized.setTotalDurationSeconds(this.safeLong(item.getTotalDurationSeconds()));
         result.add(normalized);
      }

      return result;
   }

   private List<UserAnalysisDimensionResponse> fillTimePeriodStats(List<UserAnalysisDimensionResponse> source) {
      Map<String, UserAnalysisDimensionResponse> index = new LinkedHashMap<>();

      for (UserAnalysisDimensionResponse item : this.safeList(source)) {
         index.put(item.getLabel(), item);
      }

      List<UserAnalysisDimensionResponse> result = new ArrayList<>();

      for (String label : TIME_PERIOD_LABELS) {
         UserAnalysisDimensionResponse item = index.get(label);
         if (item == null) {
            item = new UserAnalysisDimensionResponse();
            item.setLabel(label);
            item.setCount(0L);
            item.setTotalDurationSeconds(0L);
         }

         result.add(item);
      }

      return result;
   }

   private List<UserAnalysisDimensionResponse> fillPercent(List<UserAnalysisDimensionResponse> source) {
      long total = source.stream().mapToLong(itemx -> this.safeLong(itemx.getCount())).sum();

      for (UserAnalysisDimensionResponse item : source) {
         if (total <= 0L) {
            item.setPercentage(0.0);
         } else {
            double percent = BigDecimal.valueOf((double)this.safeLong(item.getCount()) * 100.0 / (double)total).setScale(1, RoundingMode.HALF_UP).doubleValue();
            item.setPercentage(percent);
         }
      }

      return source;
   }

   private String itemTypeLabel(String value) {
      String text = this.defaultLabel(value, "未知类型");

      return switch (text) {
         case "Movie" -> "电影";
         case "Episode" -> "剧集";
         case "Series" -> "剧集合集";
         case "Audio" -> "音频";
         case "MusicVideo" -> "音乐视频";
         default -> text;
      };
   }

   private String defaultLabel(String value, String fallback) {
      return StringUtils.hasText(value) ? value.trim() : fallback;
   }

   private List<UserAnalysisRadarResponse> buildRadarStats(UserAnalysisUserResponse user) {
      List<UserAnalysisRadarResponse> list = new ArrayList<>();
      this.addRadar(list, "播放次数", user.getTotalPlayCount(), 100.0);
      this.addRadar(list, "播放时长", Math.round((double)user.getTotalDurationSeconds().longValue() / 3600.0), 200.0);
      this.addRadar(list, "活跃天数", user.getActiveDayCount(), 30.0);
      this.addRadar(list, "地点覆盖", (long)user.getLocationStats().size(), 10.0);
      this.addRadar(list, "播放器", (long)user.getPlayerStats().size(), 8.0);
      this.addRadar(list, "片种类型", (long)user.getItemTypeStats().size(), 6.0);
      return list;
   }

   private void addRadar(List<UserAnalysisRadarResponse> list, String label, Number value, Double maxValue) {
      double safeValue = value == null ? 0.0 : Math.max(0.0, value.doubleValue());
      UserAnalysisRadarResponse item = new UserAnalysisRadarResponse();
      item.setLabel(label);
      item.setValue(Math.min(safeValue, maxValue));
      item.setMaxValue(maxValue);
      list.add(item);
   }

   private Long firstUserId(List<UserAnalysisUserResponse> users) {
      return users.isEmpty() ? null : users.get(0).getUserId();
   }

   private long safeLong(Long value) {
      return value == null ? 0L : value;
   }

   private <T> List<T> safeList(List<T> source) {
      return (List<T>)(source == null ? new ArrayList<>() : source);
   }

   private static record DateRange(LocalDate startDate, LocalDate endDate) {
   }
}
