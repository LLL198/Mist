package com.una.embyhub.controller.playbackreporting;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.model.dto.request.playbackreporting.CustomQueryRequest;
import com.una.embyhub.model.dto.response.playbackreporting.HourlyReportResponse;
import com.una.embyhub.model.dto.response.playbackreporting.MoviesReportResponse;
import com.una.embyhub.model.dto.response.playbackreporting.PlayActivityResponse;
import com.una.embyhub.model.dto.response.playbackreporting.TvShowsReportResponse;
import com.una.embyhub.model.dto.response.playbackreporting.UserPlaylistResponse;
import com.una.embyhub.service.playbackreporting.PlaybackReportingCacheService;
import com.una.embyhub.service.playbackreporting.PlaybackReportingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"playbackReporting"})
public class PlaybackReportingController {
   @Autowired
   private PlaybackReportingService playbackReportingService;
   @Autowired
   private PlaybackReportingCacheService playbackReportingCacheService;

   @GetMapping({"hourlyReport"})
   @SaCheckPermission({"admin"})
   public HourlyReportResponse getHourlyReport(
      @RequestParam(value = "userId",required = false) String userId,
      @RequestParam(value = "days",required = false) Integer days,
      @RequestParam(value = "endDate",required = false) String endDate,
      @RequestParam(value = "filter",required = false) String filter
   ) {
      JSONObject jsonResult = this.playbackReportingService.getHourlyReport(userId, days, endDate, filter);
      return HourlyReportResponse.fromMap(jsonResult);
   }

   @GetMapping({"moviesReport"})
   @SaCheckPermission({"admin"})
   public List<MoviesReportResponse> getMoviesReport(
      @RequestParam("embyInfoId") Long embyInfoId,
      @RequestParam(value = "userId",required = false) String userId,
      @RequestParam(value = "days",required = false) Integer days,
      @RequestParam(value = "endDate",required = false) String endDate
   ) {
      return this.playbackReportingService.getMoviesReport(embyInfoId, userId, days, endDate);
   }

   @GetMapping({"playActivity"})
   @SaCheckPermission({"admin"})
   public List<PlayActivityResponse> getPlayActivity(
      @RequestParam("embyInfoId") Long embyInfoId,
      @RequestParam(value = "days",required = false) Integer days,
      @RequestParam(value = "endDate",required = false) String endDate,
      @RequestParam(value = "filter",required = false,defaultValue = "Episode,Movie") String filter,
      @RequestParam(value = "dataType",required = false,defaultValue = "time") String dataType
   ) {
      return this.playbackReportingService.getPlayActivity(embyInfoId, days, endDate, filter, dataType);
   }

   @GetMapping({"tvShowsReport"})
   @SaCheckPermission({"admin"})
   public List<TvShowsReportResponse> getTvShowsReport(
      @RequestParam("embyInfoId") Long embyInfoId,
      @RequestParam(value = "userId",required = false) String userId,
      @RequestParam(value = "days",required = false) Integer days,
      @RequestParam(value = "endDate",required = false) String endDate
   ) {
      return this.playbackReportingService.getTvShowsReport(embyInfoId, userId, days, endDate);
   }

   @GetMapping({"userPlaylist"})
   @SaCheckPermission({"admin"})
   public List<UserPlaylistResponse> getUserPlaylist(
      @RequestParam(value = "embyInfoId",required = false) Long embyInfoId,
      @RequestParam(value = "userName",required = false) String userName,
      @RequestParam(value = "aggregateData",required = false,defaultValue = "false") Boolean aggregateData,
      @RequestParam(value = "filterName",required = false) String filterName,
      @RequestParam(value = "days",required = false) Integer days,
      @RequestParam(value = "endDate",required = false) String endDate,
      @RequestParam(value = "filter",required = false) String filter
   ) {
      return this.playbackReportingService.getUserPlaylist(embyInfoId, userName, aggregateData, filterName, days, endDate, filter);
   }

   @GetMapping({"playbackActivityRecords"})
   @SaCheckPermission({"admin"})
   public Page<UserPlaylistResponse> getPlaybackActivityRecords(
      @RequestParam(value = "embyInfoId",required = false) Long embyInfoId,
      @RequestParam(value = "days",required = false,defaultValue = "1") Integer days,
      @RequestParam(value = "endDate",required = false) String endDate,
      @RequestParam(value = "keyword",required = false) String keyword,
      @RequestParam(value = "current",required = false,defaultValue = "1") Long current,
      @RequestParam(value = "size",required = false,defaultValue = "20") Long size
   ) {
      return this.playbackReportingService.pagePlaybackActivityRecords(embyInfoId, days, endDate, keyword, current, size);
   }

   @GetMapping({"userPlaylistHistory"})
   @SaCheckPermission({"admin"})
   public Page<UserPlaylistResponse> getUserPlaylistHistory(
      @RequestParam(value = "embyInfoId",required = false) Long embyInfoId,
      @RequestParam(value = "days",required = false,defaultValue = "1") Integer days,
      @RequestParam(value = "keyword",required = false) String keyword,
      @RequestParam(value = "current",required = false,defaultValue = "1") Long current,
      @RequestParam(value = "size",required = false,defaultValue = "20") Long size
   ) {
      return this.playbackReportingCacheService.pageHistory(embyInfoId, days, keyword, current, size);
   }

   @GetMapping({"itemPath"})
   @SaCheckPermission({"admin"})
   public JSONObject getItemPath(@RequestParam("id") Integer id) {
      return this.playbackReportingService.getItemPath(id);
   }

   @GetMapping({"itemStats"})
   @SaCheckPermission({"admin"})
   public JSONObject getItemStats(@RequestParam("id") Integer id) {
      return this.playbackReportingService.getItemStats(id);
   }

   @GetMapping({"items"})
   @SaCheckPermission({"admin"})
   public JSONObject getItems(
      @RequestParam(value = "filter",required = false) String filter,
      @RequestParam(value = "itemType",required = false) String itemType,
      @RequestParam(value = "parent",required = false) Integer parent
   ) {
      return this.playbackReportingService.getItems(filter, itemType, parent);
   }

   @PostMapping({"importBackup"})
   @SaCheckPermission({"admin"})
   public JSONObject importBackup(@RequestPart("file") MultipartFile file) {
      return this.playbackReportingService.importBackup(file);
   }

   @GetMapping({"loadBackup"})
   @SaCheckPermission({"admin"})
   public JSONObject loadBackup(@RequestParam("backupFile") String backupFile) {
      return this.playbackReportingService.loadBackup(backupFile);
   }

   @GetMapping({"saveBackup"})
   @SaCheckPermission({"admin"})
   public JSONObject saveBackup() {
      return this.playbackReportingService.saveBackup();
   }

   @GetMapping({"sessionList"})
   @SaCheckPermission({"admin"})
   public JSONObject getSessionList() {
      return this.playbackReportingService.getSessionList();
   }

   @PostMapping({"submitCustomQuery"})
   @SaCheckPermission({"admin"})
   public JSONObject submitCustomQuery(@RequestBody CustomQueryRequest request) {
      return this.playbackReportingService.submitCustomQuery(request);
   }

   @GetMapping({"typeFilterList"})
   @SaCheckPermission({"admin"})
   public JSONObject getTypeFilterList() {
      return this.playbackReportingService.getTypeFilterList();
   }

   @GetMapping({"userActivity"})
   @SaCheckPermission({"admin"})
   public JSONObject getUserActivity(
      @RequestParam(value = "days",required = false) Integer days, @RequestParam(value = "endDate",required = false) String endDate
   ) {
      return this.playbackReportingService.getUserActivity(days, endDate);
   }

   @GetMapping({"userList"})
   @SaCheckPermission({"admin"})
   public JSONObject getUserList() {
      return this.playbackReportingService.getUserList();
   }

   @GetMapping({"userManage/{action}/{id}"})
   @SaCheckPermission({"admin"})
   public JSONObject manageUser(@PathVariable("action") String action, @PathVariable("id") String id) {
      return this.playbackReportingService.manageUser(action, id);
   }

   @GetMapping({"breakdownReport/{breakdownType}"})
   @SaCheckPermission({"admin"})
   public List<JSONObject> getBreakdownReport(
      @RequestParam("embyInfoId") Long embyInfoId,
      @PathVariable("breakdownType") String breakdownType,
      @RequestParam(value = "userId",required = false) String userId,
      @RequestParam(value = "days",required = false) Integer days,
      @RequestParam(value = "endDate",required = false) String endDate
   ) {
      return this.playbackReportingService.getBreakdownReport(embyInfoId, breakdownType, userId, days, endDate);
   }

   @GetMapping({"userDayItems/{userId}/{date}"})
   @SaCheckPermission({"admin"})
   public JSONObject getUserDayItems(
      @PathVariable("userId") String userId, @PathVariable("date") String date, @RequestParam(value = "filter",required = false) String filter
   ) {
      return this.playbackReportingService.getUserDayItems(userId, date, filter);
   }
}
