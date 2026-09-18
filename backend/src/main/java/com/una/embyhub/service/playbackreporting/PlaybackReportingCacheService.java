package com.una.embyhub.service.playbackreporting;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.mapper.PlaybackReportingRecordMapper;
import com.una.embyhub.mapper.PlaybackReportingSyncDayMapper;
import com.una.embyhub.model.dto.response.playbackreporting.UserPlaylistResponse;
import com.una.embyhub.model.entity.PlaybackReportingRecord;
import com.una.embyhub.model.entity.PlaybackReportingSyncDay;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PlaybackReportingCacheService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PlaybackReportingCacheService.class);
   private static final int STATUS_SUCCESS = 1;
   private static final int STATUS_FAILED = 2;
   private static final long CACHE_QUERY_PAGE_SIZE = 200L;
   private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
   private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
   private final PlaybackReportingRecordMapper recordMapper;
   private final PlaybackReportingSyncDayMapper syncDayMapper;
   private final PlaybackReportingService playbackReportingService;

   public PlaybackReportingCacheService(
      PlaybackReportingRecordMapper recordMapper, PlaybackReportingSyncDayMapper syncDayMapper, PlaybackReportingService playbackReportingService
   ) {
      this.recordMapper = recordMapper;
      this.syncDayMapper = syncDayMapper;
      this.playbackReportingService = playbackReportingService;
   }

   public Page<UserPlaylistResponse> pageHistory(Long embyInfoId, Integer days, String keyword, Long current, Long size) {
      long safeCurrent = current != null && current >= 1L ? current : 1L;
      long safeSize = size != null && size >= 1L ? Math.min(size, 200L) : 20L;
      int safeDays = days == null ? 0 : Math.max(0, Math.min(days, 365));
      if (safeDays <= 0) {
         return new Page<>(safeCurrent, safeSize, 0L);
      } else {
         LocalDate today = LocalDate.now(ZONE_ID);
         Date startTime = this.toDate(today.minusDays((long)safeDays));
         Date endTime = this.toDate(today);
         String trimmedKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;
         LambdaQueryWrapper<PlaybackReportingRecord> wrapper = Wrappers.lambdaQuery();
         if (embyInfoId != null) {
            wrapper.eq(PlaybackReportingRecord::getEmbyInfoId, embyInfoId);
         }

         wrapper.ge(PlaybackReportingRecord::getPlayDate, startTime).lt(PlaybackReportingRecord::getPlayDate, endTime);
         if (StringUtils.hasText(trimmedKeyword)) {
            wrapper.and(
               w -> w.like(PlaybackReportingRecord::getUserName, trimmedKeyword)
                     .or()
                     .like(PlaybackReportingRecord::getNickName, trimmedKeyword)
                     .or()
                     .like(PlaybackReportingRecord::getItemName, trimmedKeyword)
            );
         }

         wrapper.orderByDesc(PlaybackReportingRecord::getPlayDate).orderByDesc(PlaybackReportingRecord::getId);
         Page<PlaybackReportingRecord> entityPage = this.recordMapper.selectPage(new Page<>(safeCurrent, safeSize), wrapper);
         Page<UserPlaylistResponse> responsePage = new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());
         responsePage.setRecords(entityPage.getRecords().stream().map(this::toResponse).toList());
         return responsePage;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public int syncServerDay(Long embyInfoId, LocalDate playDay) {
      Date playDayDate = this.toDate(playDay);
      if (this.hasSuccessfulSync(embyInfoId, playDayDate)) {
         log.debug("Playback Reporting 播放记录已同步，serverId={}, day={}", embyInfoId, playDay);
         return 0;
      } else {
         LambdaQueryWrapper<PlaybackReportingRecord> deleteWrapper = Wrappers.lambdaQuery();
         this.applyServerCondition(deleteWrapper, embyInfoId);
         deleteWrapper.eq(PlaybackReportingRecord::getPlayDay, playDayDate);
         this.recordMapper.delete(deleteWrapper);
         int recordCount = this.syncPlaybackActivityRecordsByPage(embyInfoId, playDay);
         this.upsertSyncDay(embyInfoId, playDayDate, 1, recordCount, null);
         return recordCount;
      }
   }

   private int syncPlaybackActivityRecordsByPage(Long embyInfoId, LocalDate playDay) {
      String endDate = playDay.format(DAY_FORMATTER);
      int recordCount = 0;
      long current = 1L;

      while (true) {
         Page<UserPlaylistResponse> activityPage = this.playbackReportingService.pagePlaybackActivityRecords(embyInfoId, 1, endDate, null, current, 200L);
         List<UserPlaylistResponse> playlist = activityPage == null ? List.of() : activityPage.getRecords();
         if (playlist == null || playlist.isEmpty()) {
            break;
         }

         List<PlaybackReportingRecord> records = playlist.stream()
            .filter(item -> playDay.equals(this.extractPlayDay(item)))
            .map(item -> this.toRecord(embyInfoId, playDay, item))
            .filter(Objects::nonNull)
            .toList();

         for (PlaybackReportingRecord record : records) {
            this.recordMapper.insert(record);
         }

         recordCount += records.size();
         if (activityPage != null && activityPage.getPages() > 0L && current >= activityPage.getPages() || (long)playlist.size() < 200L) {
            break;
         }

         current++;
      }

      return recordCount;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void markSyncFailure(Long embyInfoId, LocalDate playDay, String errorMessage) {
      this.upsertSyncDay(embyInfoId, this.toDate(playDay), 2, 0, this.trimError(errorMessage));
   }

   private boolean hasSuccessfulSync(Long embyInfoId, Date playDay) {
      LambdaQueryWrapper<PlaybackReportingSyncDay> wrapper = Wrappers.lambdaQuery();
      this.applySyncServerCondition(wrapper, embyInfoId);
      wrapper.eq(PlaybackReportingSyncDay::getPlayDay, playDay).eq(PlaybackReportingSyncDay::getSyncStatus, Integer.valueOf(1)).last("limit 1");
      PlaybackReportingSyncDay syncDay = this.syncDayMapper.selectOne(wrapper);
      if (syncDay == null) {
         return false;
      } else if (syncDay.getRecordCount() != null && syncDay.getRecordCount() > 0) {
         LambdaQueryWrapper<PlaybackReportingRecord> recordWrapper = Wrappers.lambdaQuery();
         this.applyServerCondition(recordWrapper, embyInfoId);
         recordWrapper.eq(PlaybackReportingRecord::getPlayDay, playDay)
            .isNotNull(PlaybackReportingRecord::getClientName)
            .ne(PlaybackReportingRecord::getClientName, "")
            .last("limit 1");
         return this.recordMapper.selectCount(recordWrapper) > 0L;
      } else {
         return true;
      }
   }

   private void upsertSyncDay(Long embyInfoId, Date playDay, int status, int recordCount, String errorMessage) {
      LambdaQueryWrapper<PlaybackReportingSyncDay> wrapper = Wrappers.lambdaQuery();
      this.applySyncServerCondition(wrapper, embyInfoId);
      wrapper.eq(PlaybackReportingSyncDay::getPlayDay, playDay).last("limit 1");
      PlaybackReportingSyncDay syncDay = this.syncDayMapper.selectOne(wrapper);
      if (syncDay == null) {
         syncDay = new PlaybackReportingSyncDay();
         syncDay.setEmbyInfoId(embyInfoId);
         syncDay.setPlayDay(playDay);
         syncDay.setSyncStatus(status);
         syncDay.setRecordCount(recordCount);
         syncDay.setSyncedAt(new Date());
         syncDay.setErrorMessage(errorMessage);
         this.syncDayMapper.insert(syncDay);
      } else {
         syncDay.setSyncStatus(status);
         syncDay.setRecordCount(recordCount);
         syncDay.setSyncedAt(new Date());
         syncDay.setErrorMessage(errorMessage);
         this.syncDayMapper.updateById(syncDay);
      }
   }

   private PlaybackReportingRecord toRecord(Long embyInfoId, LocalDate playDay, UserPlaylistResponse response) {
      Date playDate = this.parsePlayDate(response);
      if (playDate == null) {
         return null;
      } else {
         PlaybackReportingRecord record = new PlaybackReportingRecord();
         record.setEmbyInfoId(embyInfoId);
         record.setPlayDay(this.toDate(playDay));
         record.setPlayDate(playDate);
         record.setUserId(response.getUserId());
         record.setUserName(response.getUserName());
         record.setNickName(response.getNickName());
         record.setItemId(response.getItemId());
         record.setItemName(response.getItemName());
         record.setItemType(response.getItemType());
         record.setDuration(this.parseDuration(response.getDuration()));
         record.setClientName(response.getClientName());
         record.setRemoteAddress(response.getRemoteAddress());
         record.setLocation(response.getLocation());
         record.setPosterUrl(response.getPosterUrl());
         record.setRowHash(this.buildRowHash(embyInfoId, response));
         return record;
      }
   }

   private UserPlaylistResponse toResponse(PlaybackReportingRecord record) {
      UserPlaylistResponse response = new UserPlaylistResponse();
      if (record.getPlayDate() != null) {
         response.setDate(DateUtil.format(record.getPlayDate(), "yyyy-MM-dd"));
         response.setTime(DateUtil.format(record.getPlayDate(), "HH:mm:ss"));
         response.setDateTime(DateUtil.format(record.getPlayDate(), "yyyy-MM-dd HH:mm:ss"));
      }

      response.setUserId(record.getUserId());
      response.setUserName(record.getUserName());
      response.setNickName(record.getNickName());
      response.setItemId(record.getItemId());
      response.setItemName(record.getItemName());
      response.setItemType(record.getItemType());
      response.setDuration(record.getDuration() == null ? null : String.valueOf(record.getDuration()));
      response.setClientName(record.getClientName());
      response.setRemoteAddress(record.getRemoteAddress());
      response.setLocation(record.getLocation());
      response.setPosterUrl(record.getPosterUrl());
      return response;
   }

   private Date parsePlayDate(UserPlaylistResponse response) {
      if (StringUtils.hasText(response.getDateTime())) {
         return DateUtil.parse(response.getDateTime(), "yyyy-MM-dd HH:mm:ss");
      } else {
         return StringUtils.hasText(response.getDate()) && StringUtils.hasText(response.getTime())
            ? DateUtil.parse(response.getDate() + " " + response.getTime(), "yyyy-MM-dd HH:mm:ss")
            : null;
      }
   }

   private LocalDate extractPlayDay(UserPlaylistResponse response) {
      String dayText = response.getDate();
      if (!StringUtils.hasText(dayText) && StringUtils.hasText(response.getDateTime()) && response.getDateTime().length() >= 10) {
         dayText = response.getDateTime().substring(0, 10);
      }

      if (!StringUtils.hasText(dayText)) {
         return null;
      } else {
         try {
            return LocalDate.parse(dayText, DAY_FORMATTER);
         } catch (Exception var4) {
            return null;
         }
      }
   }

   private Integer parseDuration(String duration) {
      if (!StringUtils.hasText(duration)) {
         return null;
      } else {
         try {
            return Integer.parseInt(duration);
         } catch (NumberFormatException var3) {
            return null;
         }
      }
   }

   private Date toDate(LocalDate localDate) {
      return Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
   }

   private String buildRowHash(Long embyInfoId, UserPlaylistResponse response) {
      String raw = String.join(
         "|",
         Objects.toString(embyInfoId, ""),
         Objects.toString(response.getDate(), ""),
         Objects.toString(response.getTime(), ""),
         Objects.toString(response.getUserId(), ""),
         Objects.toString(response.getUserName(), ""),
         Objects.toString(response.getItemId(), ""),
         Objects.toString(response.getItemName(), ""),
         Objects.toString(response.getDuration(), ""),
         Objects.toString(response.getClientName(), ""),
         Objects.toString(response.getRemoteAddress(), "")
      );

      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         return HexFormat.of().formatHex(digest.digest(raw.getBytes(StandardCharsets.UTF_8)));
      } catch (NoSuchAlgorithmException var5) {
         throw new IllegalStateException("SHA-256 algorithm is not available", var5);
      }
   }

   private String trimError(String errorMessage) {
      if (!StringUtils.hasText(errorMessage)) {
         return null;
      } else {
         return errorMessage.length() > 1900 ? errorMessage.substring(0, 1900) : errorMessage;
      }
   }

   private void applyServerCondition(LambdaQueryWrapper<PlaybackReportingRecord> wrapper, Long embyInfoId) {
      if (embyInfoId == null) {
         wrapper.isNull(PlaybackReportingRecord::getEmbyInfoId);
      } else {
         wrapper.eq(PlaybackReportingRecord::getEmbyInfoId, embyInfoId);
      }
   }

   private void applySyncServerCondition(LambdaQueryWrapper<PlaybackReportingSyncDay> wrapper, Long embyInfoId) {
      if (embyInfoId == null) {
         wrapper.isNull(PlaybackReportingSyncDay::getEmbyInfoId);
      } else {
         wrapper.eq(PlaybackReportingSyncDay::getEmbyInfoId, embyInfoId);
      }
   }
}
