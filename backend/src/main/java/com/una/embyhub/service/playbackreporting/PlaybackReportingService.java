package com.una.embyhub.service.playbackreporting;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.model.dto.request.playbackreporting.CustomQueryRequest;
import com.una.embyhub.model.dto.response.playbackreporting.MoviesReportResponse;
import com.una.embyhub.model.dto.response.playbackreporting.PlayActivityResponse;
import com.una.embyhub.model.dto.response.playbackreporting.TvShowsReportResponse;
import com.una.embyhub.model.dto.response.playbackreporting.UserPlaylistResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PlaybackReportingService {
   JSONObject getHourlyReport(String userId, Integer days, String endDate, String filter);

   List<MoviesReportResponse> getMoviesReport(Long embyInfoId, String userId, Integer days, String endDate);

   List<PlayActivityResponse> getPlayActivity(Long embyInfoId, Integer days, String endDate, String filter, String dataType);

   List<TvShowsReportResponse> getTvShowsReport(Long embyInfoId, String userId, Integer days, String endDate);

   List<UserPlaylistResponse> getUserPlaylist(
      Long embyInfoId, String userName, Boolean aggregateData, String filterName, Integer days, String endDate, String filter
   );

   JSONObject getItemPath(Integer id);

   JSONObject getItemStats(Integer id);

   JSONObject getItems(String filter, String itemType, Integer parent);

   JSONObject importBackup(MultipartFile file);

   JSONObject loadBackup(String backupFile);

   JSONObject saveBackup();

   JSONObject getSessionList();

   JSONObject submitCustomQuery(CustomQueryRequest request);

   Page<UserPlaylistResponse> pagePlaybackActivityRecords(Long embyInfoId, Integer days, String endDate, String keyword, Long current, Long size);

   JSONObject getTypeFilterList();

   JSONObject getUserActivity(Integer days, String endDate);

   JSONObject getUserList();

   JSONObject manageUser(String action, String id);

   List<JSONObject> getBreakdownReport(Long embyInfoId, String breakdownType, String userId, Integer days, String endDate);

   JSONObject getUserDayItems(String userId, String date, String filter);
}
