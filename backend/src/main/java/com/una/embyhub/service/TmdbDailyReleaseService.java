package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.tmdbdaily.TmdbDailyReleaseConfigRequest;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseCalendarDayResponse;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseResponse;
import com.una.embyhub.model.dto.response.tmdbdaily.TmdbDailyReleaseSyncResponse;
import com.una.embyhub.model.entity.TmdbDailyRelease;
import java.time.LocalDate;
import java.util.List;

public interface TmdbDailyReleaseService extends IService<TmdbDailyRelease> {
   TmdbDailyReleaseConfigRequest getConfig();

   TmdbDailyReleaseConfigRequest saveConfig(TmdbDailyReleaseConfigRequest request);

   TmdbDailyReleaseSyncResponse submitSyncDate(LocalDate date, boolean notify);

   TmdbDailyReleaseSyncResponse syncDate(LocalDate date, boolean notify);

   TmdbDailyReleaseSyncResponse runDailyJob();

   List<TmdbDailyReleaseResponse> listByDate(LocalDate date);

   List<TmdbDailyReleaseCalendarDayResponse> monthSummary(int year, int month);
}
