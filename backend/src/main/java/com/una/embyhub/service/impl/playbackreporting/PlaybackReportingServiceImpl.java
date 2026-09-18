package com.una.embyhub.service.impl.playbackreporting;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.EmbyUrlUtils;
import com.una.embyhub.config.common.utils.IpAddressUtils;
import com.una.embyhub.model.dto.request.playbackreporting.CustomQueryRequest;
import com.una.embyhub.model.dto.response.playbackreporting.MoviesReportResponse;
import com.una.embyhub.model.dto.response.playbackreporting.PlayActivityResponse;
import com.una.embyhub.model.dto.response.playbackreporting.TvShowsReportResponse;
import com.una.embyhub.model.dto.response.playbackreporting.UserPlaylistResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.playbackreporting.PlaybackReportingService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlaybackReportingServiceImpl implements PlaybackReportingService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PlaybackReportingServiceImpl.class);
   private static final String BASE_PATH = "/user_usage_stats";
   private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
   private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
   private static final int KEYWORD_QUERY_LIMIT = 20000;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private Ip2regionSearcher ip2regionSearcher;
   @Autowired
   private EmbyUserService embyUserService;

   @Override
   public JSONObject getHourlyReport(String userId, Integer days, String endDate, String filter) {
      Map<String, Object> params = this.baseParams();
      this.putIfNotBlank(params, "user_id", userId);
      this.putIfNotNull(params, "days", days);
      this.putIfNotBlank(params, "end_date", endDate);
      this.putIfNotBlank(params, "filter", filter);
      return this.doGet("/HourlyReport", params);
   }

   @Override
   public List<MoviesReportResponse> getMoviesReport(Long embyInfoId, String userId, Integer days, String endDate) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      Map<String, Object> params = this.baseParamsWithConfig(serverConfig);
      this.putIfNotBlank(params, "user_id", userId);
      this.putIfNotNull(params, "days", days);
      this.putIfNotBlank(params, "end_date", endDate);
      return this.doGetListWithConfig("/MoviesReport", params, MoviesReportResponse.class, serverConfig);
   }

   @Override
   public List<PlayActivityResponse> getPlayActivity(Long embyInfoId, Integer days, String endDate, String filter, String dataType) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      Map<String, Object> params = this.baseParamsWithConfig(serverConfig);
      this.putIfNotNull(params, "days", days);
      this.putIfNotBlank(params, "end_date", !StringUtils.hasText(endDate) ? DateUtil.formatDate(new Date()) : endDate);
      this.putIfNotBlank(params, "filter", filter);
      this.putIfNotBlank(params, "data_type", dataType);
      return this.doGetListWithConfig("/PlayActivity", params, PlayActivityResponse.class, serverConfig);
   }

   @Override
   public List<TvShowsReportResponse> getTvShowsReport(Long embyInfoId, String userId, Integer days, String endDate) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      Map<String, Object> params = this.baseParamsWithConfig(serverConfig);
      this.putIfNotBlank(params, "user_id", userId);
      this.putIfNotNull(params, "days", days);
      this.putIfNotBlank(params, "end_date", endDate);
      return this.doGetListWithConfig("/TvShowsReport", params, TvShowsReportResponse.class, serverConfig);
   }

   @Override
   public List<UserPlaylistResponse> getUserPlaylist(
      Long embyInfoId, String userName, Boolean aggregateData, String filterName, Integer days, String endDate, String filter
   ) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      EmbyUser embyUser = null;
      if (StringUtils.hasText(userName)) {
         embyUser = this.embyUserService
            .lambdaQuery()
            .eq(EmbyUser::getEmbyUserName, userName)
            .eq(embyInfoId != null, EmbyUser::getEmbyInfoId, embyInfoId)
            .one();
      }

      String userId = null;
      String nickName = null;
      if (embyUser != null) {
         userId = embyUser.getEmbyUserId();
         nickName = embyUser.getEmbyUserName();
      }

      Map<String, Object> params = this.baseParamsWithConfig(serverConfig);
      this.putIfNotBlank(params, "user_id", userId);
      this.putIfNotNull(params, "aggregate_data", aggregateData);
      this.putIfNotBlank(params, "filter_name", filterName);
      this.putIfNotNull(params, "days", days);
      this.putIfNotBlank(params, "end_date", endDate);
      this.putIfNotBlank(params, "filter", filter);
      List<UserPlaylistResponse> list = this.doGetListWithConfig("/UserPlaylist", params, UserPlaylistResponse.class, serverConfig);
      if (list == null) {
         return List.of();
      } else {
         Map<String, String> userIdToNickName = new HashMap<>();
         if (nickName != null && userId != null) {
            userIdToNickName.put(userId, nickName);
         }

         list.forEach(response -> this.enrichUserPlaylistResponse(response, userIdToNickName, serverConfig));
         return list;
      }
   }

   private void enrichUserPlaylistResponse(
      UserPlaylistResponse response, Map<String, String> nickNameMap, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig
   ) {
      if (StringUtils.hasText(response.getDate()) && StringUtils.hasText(response.getTime())) {
         response.setDateTime(response.getDate() + " " + response.getTime());
      }

      String ip = response.getRemoteAddress();
      if (StringUtils.hasText(ip)) {
         Optional<IpAddressUtils.ParsedIp> parsedIp = IpAddressUtils.parseLiteral(ip);
         if (parsedIp.isPresent() && parsedIp.get().privateOrLocal()) {
            response.setLocation("内网");
         } else {
            IpAddressUtils.safeLookup(this.ip2regionSearcher, ip).ifPresent(ipInfo -> response.setLocation(ipInfo.getAddressAndIsp()));
         }
      }

      String userId = response.getUserId();
      if (StringUtils.hasText(userId)) {
         String nickName = nickNameMap.get(userId);
         if (nickName == null) {
            EmbyUser user = this.embyUserService
               .lambdaQuery()
               .eq(EmbyUser::getEmbyUserId, userId)
               .eq(serverConfig.id() != null, EmbyUser::getEmbyInfoId, serverConfig.id())
               .one();
            if (user != null) {
               nickName = user.getEmbyUserName();
               nickNameMap.put(userId, nickName);
            }
         }

         response.setNickName(nickName);
      }

      if (response.getItemId() != null && serverConfig != null && StringUtils.hasText(serverConfig.url())) {
         String posterUrl = EmbyUrlUtils.buildApiUrl(serverConfig.url(), "/Items/" + response.getItemId() + "/Images/Primary") + "?maxHeight=300&quality=90";
         response.setPosterUrl(posterUrl);
      }
   }

   @Override
   public JSONObject getItemPath(Integer id) {
      Map<String, Object> params = this.baseParams();
      this.putIfNotNull(params, "id", id);
      return this.doGet("/get_item_path", params);
   }

   @Override
   public JSONObject getItemStats(Integer id) {
      Map<String, Object> params = this.baseParams();
      this.putIfNotNull(params, "id", id);
      return this.doGet("/get_item_stats", params);
   }

   @Override
   public JSONObject getItems(String filter, String itemType, Integer parent) {
      Map<String, Object> params = this.baseParams();
      this.putIfNotBlank(params, "filter", filter);
      this.putIfNotBlank(params, "item_type", itemType);
      this.putIfNotNull(params, "parent", parent);
      return this.doGet("/get_items", params);
   }

   @Override
   public JSONObject importBackup(MultipartFile file) {
      if (file != null && !file.isEmpty()) {
         String url = this.buildUrl("/import_backup");

         try {
            String body = HttpRequest.post(url).body(file.getBytes()).execute().body();
            return JSON.parseObject(body);
         } catch (IOException var4) {
            log.error("导入 Playback Reporting 备份失败", (Throwable)var4);
            throw new BizException(ResponseStatusEnum.PLAYBACK_REPORTING_NOT_INSTALLED);
         }
      } else {
         throw new BizException(ResponseStatusEnum.AVATAR_FILE_EMPTY);
      }
   }

   @Override
   public JSONObject loadBackup(String backupFile) {
      Map<String, Object> params = this.baseParams();
      this.putIfNotBlank(params, "backupfile", backupFile);
      return this.doGet("/load_backup", params);
   }

   @Override
   public JSONObject saveBackup() {
      return this.doGet("/save_backup", this.baseParams());
   }

   @Override
   public JSONObject getSessionList() {
      return this.doGet("/session_list", this.baseParams());
   }

   @Override
   public JSONObject submitCustomQuery(CustomQueryRequest request) {
      if (request == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      } else {
         return this.submitCustomQuery(request, this.embyInfoCacheManager.getRequiredConfig());
      }
   }

   @Override
   public Page<UserPlaylistResponse> pagePlaybackActivityRecords(Long embyInfoId, Integer days, String endDate, String keyword, Long current, Long size) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      long safeCurrent = current != null && current >= 1L ? current : 1L;
      long safeSize = size != null && size >= 1L ? Math.min(size, 200L) : 20L;
      LocalDate endDay = this.parseEndDay(endDate);
      LocalDate startDay = endDay.minusDays((long)Math.max(days == null ? 1 : days, 1) - 1L);
      String startDateTime = startDay.format(DAY_FORMATTER) + " 00:00:00";
      String endDateTime = endDay.plusDays(1L).format(DAY_FORMATTER) + " 00:00:00";
      String trimmedKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;
      if (StringUtils.hasText(trimmedKeyword)) {
         return this.pagePlaybackActivityRecordsWithKeyword(serverConfig, startDateTime, endDateTime, trimmedKeyword, safeCurrent, safeSize);
      } else {
         long offset = (safeCurrent - 1L) * safeSize;
         String where = this.buildPlaybackActivityWhere(startDateTime, endDateTime);
         long total = this.queryPlaybackActivityTotal(serverConfig, where);
         List<UserPlaylistResponse> records = this.queryPlaybackActivityRecords(serverConfig, where, safeSize, offset);
         return this.toPage(records, safeCurrent, safeSize, total);
      }
   }

   @Override
   public JSONObject getTypeFilterList() {
      return this.doGet("/type_filter_list", this.baseParams());
   }

   @Override
   public JSONObject getUserActivity(Integer days, String endDate) {
      Map<String, Object> params = this.baseParams();
      this.putIfNotNull(params, "days", days);
      this.putIfNotBlank(params, "end_date", endDate);
      return this.doGet("/user_activity", params);
   }

   @Override
   public JSONObject getUserList() {
      return this.doGet("/user_list", this.baseParams());
   }

   @Override
   public JSONObject manageUser(String action, String id) {
      this.embyUserService.assertEmbyUserCanBeManaged(id);
      String path = "/user_manage/" + action + "/" + id;
      return this.doGet(path, this.baseParams());
   }

   @Override
   public List<JSONObject> getBreakdownReport(Long embyInfoId, String breakdownType, String userId, Integer days, String endDate) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      Map<String, Object> params = this.baseParamsWithConfig(serverConfig);
      this.putIfNotBlank(params, "user_id", userId);
      this.putIfNotNull(params, "days", days);
      this.putIfNotBlank(params, "end_date", endDate);
      return this.doGetListWithConfig("/" + breakdownType + "/BreakdownReport", params, JSONObject.class, serverConfig);
   }

   @Override
   public JSONObject getUserDayItems(String userId, String date, String filter) {
      Map<String, Object> params = this.baseParams();
      this.putIfNotBlank(params, "Filter", filter);
      String path = "/" + userId + "/" + date + "/GetItems";
      return this.doGet(path, params);
   }

   private Map<String, Object> baseParams() {
      Map<String, Object> params = new HashMap<>();
      params.put("api_key", this.embyInfoCacheManager.getRequiredConfig().apiKey());
      return params;
   }

   private Map<String, Object> baseParamsWithConfig(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      Map<String, Object> params = new HashMap<>();
      params.put("api_key", config.apiKey());
      return params;
   }

   private Page<UserPlaylistResponse> pagePlaybackActivityRecordsWithKeyword(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String startDateTime, String endDateTime, String keyword, long current, long size
   ) {
      String where = this.buildPlaybackActivityWhere(startDateTime, endDateTime);
      List<UserPlaylistResponse> source = this.queryPlaybackActivityRecords(serverConfig, where, 20000L, 0L);
      String lowerKeyword = keyword.toLowerCase();
      List<UserPlaylistResponse> filtered = source.stream().filter(row -> this.playbackRecordMatches(row, lowerKeyword)).toList();
      long from = Math.min((current - 1L) * size, (long)filtered.size());
      long to = Math.min(from + size, (long)filtered.size());
      return this.toPage(filtered.subList((int)from, (int)to), current, size, (long)filtered.size());
   }

   private boolean playbackRecordMatches(UserPlaylistResponse row, String lowerKeyword) {
      return this.containsIgnoreCase(row.getUserName(), lowerKeyword)
         || this.containsIgnoreCase(row.getNickName(), lowerKeyword)
         || this.containsIgnoreCase(row.getItemName(), lowerKeyword)
         || this.containsIgnoreCase(row.getItemType(), lowerKeyword)
         || this.containsIgnoreCase(row.getPlaybackMethod(), lowerKeyword)
         || this.containsIgnoreCase(row.getClientName(), lowerKeyword)
         || this.containsIgnoreCase(row.getDeviceName(), lowerKeyword)
         || this.containsIgnoreCase(row.getTranscodeReasons(), lowerKeyword)
         || this.containsIgnoreCase(row.getRemoteAddress(), lowerKeyword)
         || this.containsIgnoreCase(row.getLocation(), lowerKeyword);
   }

   private boolean containsIgnoreCase(String value, String lowerKeyword) {
      return StringUtils.hasText(value) && value.toLowerCase().contains(lowerKeyword);
   }

   private Page<UserPlaylistResponse> toPage(List<UserPlaylistResponse> records, long current, long size, long total) {
      Page<UserPlaylistResponse> page = new Page<>(current, size, total);
      page.setRecords(records);
      return page;
   }

   private long queryPlaybackActivityTotal(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String where) {
      String sql = "SELECT COUNT(1) AS total FROM PlaybackActivity " + where;
      JSONObject response = this.submitCustomQuery(this.buildCustomQuery(sql), serverConfig);
      List<Map<String, Object>> rows = this.customQueryRows(response);
      if (rows.isEmpty()) {
         return 0L;
      } else {
         Object value = rows.get(0).get("total");
         if (value == null) {
            value = rows.get(0).get("COUNT(1)");
         }

         try {
            return Long.parseLong(String.valueOf(value));
         } catch (Exception var8) {
            return 0L;
         }
      }
   }

   private List<UserPlaylistResponse> queryPlaybackActivityRecords(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String where, long limit, long offset
   ) {
      String sql = "SELECT ROWID,\n       DateCreated,\n       UserId,\n       ItemId,\n       ItemType,\n       ItemName,\n       PlaybackMethod,\n       ClientName,\n       DeviceName,\n       PlayDuration,\n       PauseDuration,\n       RemoteAddress,\n       TranscodeReasons\nFROM PlaybackActivity\n%s\nORDER BY DateCreated DESC, ROWID DESC\nLIMIT %d OFFSET %d\n"
         .formatted(where, limit, offset);
      JSONObject response = this.submitCustomQuery(this.buildCustomQuery(sql, true), serverConfig);
      List<Map<String, Object>> rows = this.customQueryRows(response);
      return rows.stream().map(row -> this.toUserPlaylistResponse((Map<String, Object>)row, serverConfig)).toList();
   }

   private String buildPlaybackActivityWhere(String startDateTime, String endDateTime) {
      return "WHERE ItemType IN ('Movie', 'Episode') AND DateCreated >= '"
         + this.escapeSql(startDateTime)
         + "' AND DateCreated < '"
         + this.escapeSql(endDateTime)
         + "'";
   }

   private CustomQueryRequest buildCustomQuery(String sql) {
      return this.buildCustomQuery(sql, false);
   }

   private CustomQueryRequest buildCustomQuery(String sql, boolean replaceUserId) {
      CustomQueryRequest request = new CustomQueryRequest();
      request.setCustomQueryString(sql);
      request.setReplaceUserId(replaceUserId);
      return request;
   }

   private JSONObject submitCustomQuery(CustomQueryRequest request, EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      String url = this.buildUrlWithConfig("/submit_custom_query", config) + "?api_key=" + config.apiKey();

      try {
         String response = HttpRequest.post(url).body(JSON.toJSONString(request)).contentType("application/json; charset=utf-8").execute().body();
         return JSON.parseObject(response);
      } catch (Exception var5) {
         log.error("调用 Playback Reporting 自定义查询接口异常", (Throwable)var5);
         throw new BizException(ResponseStatusEnum.PLAYBACK_REPORTING_NOT_INSTALLED);
      }
   }

   private List<Map<String, Object>> customQueryRows(JSONObject response) {
      JSONArray columns = response == null ? null : response.getJSONArray("colums");
      if (columns == null) {
         columns = response == null ? null : response.getJSONArray("columns");
      }

      JSONArray results = response == null ? null : response.getJSONArray("results");
      if (columns != null && results != null) {
         List<Map<String, Object>> rows = new ArrayList<>();

         for (int rowIndex = 0; rowIndex < results.size(); rowIndex++) {
            JSONArray values = results.getJSONArray(rowIndex);
            if (values != null) {
               Map<String, Object> row = new HashMap<>();

               for (int columnIndex = 0; columnIndex < columns.size() && columnIndex < values.size(); columnIndex++) {
                  row.put(columns.getString(columnIndex), values.get(columnIndex));
               }

               rows.add(row);
            }
         }

         return rows;
      } else {
         String message = response == null ? null : response.getString("message");
         if (StringUtils.hasText(message)) {
            throw new BizException("Playback Reporting 自定义查询失败: " + this.cleanHtmlMessage(message));
         } else {
            return List.of();
         }
      }
   }

   private String cleanHtmlMessage(String message) {
      return message.replace("<br/>", "\n").replace("<br>", "\n").replace("<pre>", "\n").replace("</pre>", "").trim();
   }

   private UserPlaylistResponse toUserPlaylistResponse(Map<String, Object> row, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      UserPlaylistResponse response = new UserPlaylistResponse();
      String dateCreated = this.stringValue(row.get("DateCreated"));
      String normalizedDateTime = this.normalizePlaybackDateTime(dateCreated);
      if (StringUtils.hasText(normalizedDateTime)) {
         response.setDate(normalizedDateTime.substring(0, 10));
         response.setTime(normalizedDateTime.substring(11));
         response.setDateTime(normalizedDateTime);
      }

      response.setUserId(this.stringValue(row.get("UserId")));
      response.setUserName(this.firstText(row.get("UserName"), row.get("UserId")));
      response.setNickName(response.getUserName());
      response.setItemId(this.parseInteger(row.get("ItemId")));
      response.setItemType(this.stringValue(row.get("ItemType")));
      response.setItemName(this.stringValue(row.get("ItemName")));
      response.setDuration(this.stringValue(row.get("PlayDuration")));
      response.setPauseDuration(this.stringValue(row.get("PauseDuration")));
      response.setPlaybackMethod(this.stringValue(row.get("PlaybackMethod")));
      response.setClientName(this.stringValue(row.get("ClientName")));
      response.setDeviceName(this.stringValue(row.get("DeviceName")));
      response.setTranscodeReasons(this.stringValue(row.get("TranscodeReasons")));
      response.setRemoteAddress(this.stringValue(row.get("RemoteAddress")));
      this.enrichUserPlaylistResponse(response, new HashMap<>(), serverConfig);
      return response;
   }

   private String normalizePlaybackDateTime(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         String trimmed = value.trim();
         return trimmed.length() >= 19 ? trimmed.substring(0, 19) : trimmed;
      }
   }

   private Integer parseInteger(Object value) {
      if (value == null) {
         return null;
      } else {
         try {
            return Integer.valueOf(String.valueOf(value));
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private String firstText(Object... values) {
      for (Object value : values) {
         String text = this.stringValue(value);
         if (StringUtils.hasText(text)) {
            return text;
         }
      }

      return null;
   }

   private String stringValue(Object value) {
      return value == null ? null : String.valueOf(value);
   }

   private String escapeSql(String value) {
      return value == null ? "" : value.replace("'", "''");
   }

   private LocalDate parseEndDay(String endDate) {
      if (StringUtils.hasText(endDate)) {
         try {
            return LocalDate.parse(endDate, DAY_FORMATTER);
         } catch (Exception var3) {
            log.warn("Playback Reporting 查询日期格式无效，endDate={}", endDate);
         }
      }

      return LocalDate.now(ZONE_ID);
   }

   private JSONObject doGet(String path, Map<String, Object> params) {
      String url = this.buildUrl(path);

      try {
         String response = HttpUtil.get(url, params);
         return JSON.parseObject(response);
      } catch (Exception var5) {
         log.error("调用 Playback Reporting 接口异常，path={}", path, var5);
         throw new BizException(ResponseStatusEnum.PLAYBACK_REPORTING_NOT_INSTALLED);
      }
   }

   private <T> List<T> doGetList(String path, Map<String, Object> params, Class<T> clazz) {
      String url = this.buildUrl(path);

      try {
         String response = HttpUtil.get(url, params);
         return JSON.parseArray(response, clazz);
      } catch (Exception var6) {
         log.error("调用 Playback Reporting 接口异常，path={}", path, var6);
         throw new BizException(ResponseStatusEnum.PLAYBACK_REPORTING_NOT_INSTALLED);
      }
   }

   private <T> List<T> doGetListWithConfig(String path, Map<String, Object> params, Class<T> clazz, EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      String url = this.buildUrlWithConfig(path, config);

      try {
         String response = HttpUtil.get(url, params);
         return JSON.parseArray(response, clazz);
      } catch (Exception var7) {
         log.error("调用 Playback Reporting 接口异常，path={}", path, var7);
         throw new BizException(ResponseStatusEnum.PLAYBACK_REPORTING_NOT_INSTALLED);
      }
   }

   private String buildUrl(String path) {
      String normalizedPath = path.startsWith("/") ? path : "/" + path;
      return this.embyInfoCacheManager.getRequiredConfig().url() + "/user_usage_stats" + normalizedPath;
   }

   private String buildUrlWithConfig(String path, EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      String normalizedPath = path.startsWith("/") ? path : "/" + path;
      return config.url() + "/user_usage_stats" + normalizedPath;
   }

   private void putIfNotBlank(Map<String, Object> params, String key, String value) {
      if (StringUtils.hasText(value)) {
         params.put(key, value);
      }
   }

   private void putIfNotNull(Map<String, Object> params, String key, Object value) {
      if (value != null) {
         params.put(key, value);
      }
   }
}
