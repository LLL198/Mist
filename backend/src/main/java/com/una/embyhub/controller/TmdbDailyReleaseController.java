package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.request.tmdbdaily.TmdbDailyReleaseConfigRequest;
import com.una.embyhub.model.dto.request.tmdbdaily.TmdbDailyReleaseSyncRequest;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseCalendarDayResponse;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseResponse;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseSyncResponse;
import com.una.embyhub.service.TmdbDailyReleaseService;
import java.time.LocalDate;
import java.util.List;
import lombok.Generated;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"tmdb/daily-release"})
@SaCheckPermission({"admin"})
public class TmdbDailyReleaseController {
   private final TmdbDailyReleaseService tmdbDailyReleaseService;

   @GetMapping({"config"})
   public TmdbDailyReleaseConfigRequest getConfig() {
      return this.tmdbDailyReleaseService.getConfig();
   }

   @PutMapping({"config"})
   public TmdbDailyReleaseConfigRequest saveConfig(@RequestBody TmdbDailyReleaseConfigRequest request) {
      return this.tmdbDailyReleaseService.saveConfig(request);
   }

   @PostMapping({"sync"})
   public TmdbDailyReleaseSyncResponse sync(@RequestBody(required = false) TmdbDailyReleaseSyncRequest request) {
      String date = request == null ? null : request.getDate();
      boolean notify = request != null && Boolean.TRUE.equals(request.getNotify());
      return this.tmdbDailyReleaseService.submitSyncDate(this.parseDate(date), notify);
   }

   @GetMapping({"list"})
   public List<TmdbDailyReleaseResponse> list(@RequestParam String date) {
      return this.tmdbDailyReleaseService.listByDate(this.parseDate(date));
   }

   @GetMapping({"calendar"})
   public List<TmdbDailyReleaseCalendarDayResponse> calendar(@RequestParam Integer year, @RequestParam Integer month) {
      return this.tmdbDailyReleaseService.monthSummary(year, month);
   }

   private LocalDate parseDate(String date) {
      return !StringUtils.hasText(date) ? null : LocalDate.parse(date.trim());
   }

   @Generated
   public TmdbDailyReleaseController(final TmdbDailyReleaseService tmdbDailyReleaseService) {
      this.tmdbDailyReleaseService = tmdbDailyReleaseService;
   }
}
