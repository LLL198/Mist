package com.una.embyhub.movie.job;

import cn.hutool.core.date.DateUtil;
import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;
import com.una.embyhub.movie.mapper.MovieDownloadRecordMapper;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieDownloadRecordReorganizeRequest;
import com.una.embyhub.movie.model.MoviePtSite;
import com.una.embyhub.movie.model.MovieQbittorrentTorrent;
import com.una.embyhub.movie.model.MovieScrapePathConfig;
import com.una.embyhub.movie.service.MovieDownloadRecordService;
import com.una.embyhub.movie.service.MovieMetadataScrapeService;
import com.una.embyhub.movie.service.MovieNotifyTmdbEnrichService;
import com.una.embyhub.movie.service.MoviePtSiteService;
import com.una.embyhub.movie.service.MovieQbittorrentService;
import com.una.embyhub.movie.service.MovieScrapePathConfigService;
import com.una.embyhub.movie.util.MovieCategoryResolver;
import com.una.embyhub.movie.util.MovieEpisodeParser;
import com.una.embyhub.service.TmdbService;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.movies.Translation;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class MovieDownloadRecordSyncJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MovieDownloadRecordSyncJob.class);
   private static final String QB_TAG_DEFAULT = "Mist";
   private static final Set<String> COMPLETED_STATES = Set.of("uploading", "stalledUP", "queuedUP", "pausedUP", "checkingUP", "forcedUP");
   private final MovieDownloadRecordService downloadRecordService;
   private final MovieQbittorrentService qbittorrentService;
   private final MovieDownloadRecordMapper downloadRecordMapper;
   private final MovieMetadataScrapeService metadataScrapeService;
   private final TmdbService tmdbService;
   private final MovieNotifyTmdbEnrichService movieNotifyTmdbEnrichService;
   private final MovieCategoryResolver categoryResolver;
   private final NotifyUtils notifyUtils;
   private final MovieScrapePathConfigService scrapePathConfigService;
   private final MoviePtSiteService moviePtSiteService;
   private static final Pattern RESOLUTION_PATTERN = Pattern.compile("(?i)(\\d{3,4}p|4k|8k)");
   private static final Pattern QUALITY_PATTERN = Pattern.compile("(?i)(BluRay|Remux|WEB-DL|WEBRip|HDTV|BD|WEB|HDR|DV)");
   private static final Pattern PART_PATTERN = Pattern.compile("(?i)(?:part|cd|disc|pt)[ ._-]?(\\d{1,2})");
   private static final Pattern EPISODE_CODE_PATTERN = Pattern.compile("(?i)^S(\\d{1,3})E(\\d{1,4})$");

   @ScheduledTaskMeta(
      name = "刮削下载整理",
      remark = "同步 qBittorrent 完成状态并硬链接文件"
   )
   public void syncDownloadRecords() {
      log.info("刮削下载整理，刮削整理时间，" + DateUtil.now());
      List<MovieDownloadRecordEntity> records = this.downloadRecordService.listPendingRecords();
      if (!records.isEmpty()) {
         for (MovieDownloadRecordEntity record : records) {
            try {
               this.processRecord(record);
            } catch (Exception var5) {
               log.warn("下载记录同步失败 id={}: {}", record.getId(), var5.getMessage());
            }
         }
      }
   }

   public MovieActionResponse reorganizeRecords(MovieDownloadRecordReorganizeRequest request) {
      if (request != null && !CollectionUtils.isEmpty(request.getRecordIds())) {
         List<Long> recordIds = request.getRecordIds().stream().filter(Objects::nonNull).distinct().toList();
         if (CollectionUtils.isEmpty(recordIds)) {
            return MovieActionResponse.builder().success(false).message("recordIds 不能为空").build();
         } else {
            MovieScrapePathConfig config = null;
            if (request.getScrapePathConfigId() != null) {
               config = this.scrapePathConfigService.getById(request.getScrapePathConfigId());
               if (config == null) {
                  return MovieActionResponse.builder().success(false).message("刮削路径配置不存在").build();
               }
            }

            int successCount = 0;
            int failCount = 0;
            List<Long> failedRecordIds = new ArrayList<>();
            String firstErrorMessage = null;

            for (Long recordId : recordIds) {
               MovieDownloadRecordEntity record = this.downloadRecordService.findById(recordId);
               if (record == null) {
                  failCount++;
                  failedRecordIds.add(recordId);
                  if (firstErrorMessage == null) {
                     firstErrorMessage = "下载记录不存在";
                  }
               } else {
                  try {
                     this.applyReorganizeConfig(record, config, request);
                     MovieDownloadRecordSyncJob.ReorganizeResult result = this.reorganizeSingleRecord(record);
                     if (result.success()) {
                        successCount++;
                     } else {
                        failCount++;
                        failedRecordIds.add(recordId);
                        if (firstErrorMessage == null) {
                           firstErrorMessage = result.message();
                        }
                     }
                  } catch (Exception var12) {
                     failCount++;
                     failedRecordIds.add(recordId);
                     if (firstErrorMessage == null) {
                        firstErrorMessage = var12.getMessage();
                     }

                     log.warn("手动重新整理失败 recordId={}: {}", recordId, var12.getMessage());
                  }
               }
            }

            StringBuilder message = new StringBuilder("重新整理完成：成功").append(successCount).append("条，失败").append(failCount).append("条");
            if (!CollectionUtils.isEmpty(failedRecordIds)) {
               message.append("，失败ID=").append(failedRecordIds);
            }

            if (StringUtils.hasText(firstErrorMessage)) {
               message.append("，首个失败原因=").append(firstErrorMessage);
            }

            return MovieActionResponse.builder().success(failCount == 0).message(message.toString()).build();
         }
      } else {
         return MovieActionResponse.builder().success(false).message("recordIds 不能为空").build();
      }
   }

   private void processRecord(MovieDownloadRecordEntity record) {
      if (record != null) {
         this.refreshRecordConfig(record);
         Long downloaderId = record.getDownloaderId();
         List<MovieQbittorrentTorrent> torrents = List.of();
         boolean hasQbHash = StringUtils.hasText(record.getQbHash());
         if (hasQbHash) {
            torrents = downloaderId != null
               ? this.qbittorrentService.getQueueByHash(downloaderId, record.getQbHash())
               : this.qbittorrentService.getQueueByHash(record.getQbHash());
         }

         String qbTag = record.getQbTag();
         boolean canFallbackByTag = !hasQbHash && StringUtils.hasText(qbTag) && !"Mist".equalsIgnoreCase(qbTag.trim());
         if (torrents.isEmpty() && canFallbackByTag) {
            torrents = downloaderId != null
               ? this.qbittorrentService.getQueueByTag(downloaderId, record.getQbTag())
               : this.qbittorrentService.getQueueByTag(record.getQbTag());
         }

         if (torrents.isEmpty()) {
            if ("DOWNLOADING".equals(record.getStatus())) {
               log.warn("下载中任务在 qBittorrent 中未找到，标记失败 id={}", record.getId());
               this.markFailedAndNotify(record, "下载中任务在 qBittorrent 未找到对应种子，任务已失败");
            } else {
               if (this.isTorrentMissingTimeout(record)) {
                  log.warn("种子超过30分钟未找到，标记失败 id={}", record.getId());
                  this.markFailedAndNotify(record, "种子已被删除或超过30分钟未找到");
               }
            }
         } else {
            MovieQbittorrentTorrent torrent = this.findMatchingTorrent(record, torrents);
            if (torrent == null) {
               if (this.isTorrentMissingTimeout(record)) {
                  log.warn("种子存在但无法匹配到记录，超过30分钟标记失败 id={}", record.getId());
                  this.markFailedAndNotify(record, "种子存在但无法匹配到当前下载记录");
               }
            } else {
               this.downloadRecordService.updateQbInfo(record.getId(), record.getQbTag(), torrent.getHash(), torrent.getName());
               if (!this.isCompleted(torrent)) {
                  this.downloadRecordService.updateStatusIfDifferent(record.getId(), "DOWNLOADING");
               } else if (!"LINKED".equals(record.getStatus())) {
                  this.downloadRecordService.updateStatusIfDifferent(record.getId(), "COMPLETED");
                  StringBuilder errorMsg = new StringBuilder();
                  StringBuilder notifyTitle = new StringBuilder();
                  boolean linked = this.createHardlinks(record, torrent, errorMsg, notifyTitle);
                  String finalStatus = linked ? "LINKED" : "LINK_FAILED";
                  String errorStr = null;
                  if (!linked) {
                     errorStr = errorMsg.length() > 0 ? errorMsg.toString() : "硬链接失败，请检查路径配置";
                  }

                  this.downloadRecordService.updateStatusWithReason(record.getId(), finalStatus, errorStr);
                  this.sendOrganizeNotify(record, linked, errorStr, notifyTitle.toString());
               }
            }
         }
      }
   }

   private void applyReorganizeConfig(MovieDownloadRecordEntity record, MovieScrapePathConfig config, MovieDownloadRecordReorganizeRequest request) {
      if (record != null) {
         if (config != null) {
            if (StringUtils.hasText(config.getQbDownloadPath())) {
               record.setQbDownloadPath(config.getQbDownloadPath());
            }

            if (StringUtils.hasText(config.getHardlinkPath())) {
               record.setHardlinkPath(config.getHardlinkPath());
            }

            if (config.getOverwrite() != null) {
               record.setOverwrite(config.getOverwrite());
            }

            if (config.getCoexist() != null) {
               record.setCoexist(config.getCoexist());
            }

            if (config.getQualityPriority() != null) {
               record.setQualityPriority(config.getQualityPriority());
            }

            if (config.getSizePriority() != null) {
               record.setSizePriority(config.getSizePriority());
            }

            if (config.getHardlinkMode() != null) {
               record.setHardlinkMode(config.getHardlinkMode());
            }
         }

         if (request != null) {
            if (request.getOverwrite() != null) {
               record.setOverwrite(request.getOverwrite());
            }

            if (request.getCoexist() != null) {
               record.setCoexist(request.getCoexist());
            }

            if (request.getQualityPriority() != null) {
               record.setQualityPriority(request.getQualityPriority());
            }

            if (request.getSizePriority() != null) {
               record.setSizePriority(request.getSizePriority());
            }

            if (request.getHardlinkMode() != null) {
               record.setHardlinkMode(request.getHardlinkMode());
            }
         }

         this.downloadRecordService.updateRecord(record);
      }
   }

   private MovieDownloadRecordSyncJob.ReorganizeResult reorganizeSingleRecord(MovieDownloadRecordEntity record) {
      if (record != null && record.getId() != null) {
         String contentPath = this.resolveReorganizeContentPath(record);
         if (!StringUtils.hasText(contentPath)) {
            return new MovieDownloadRecordSyncJob.ReorganizeResult(false, "未找到可整理的源路径");
         } else {
            this.downloadRecordService.updateStatusIfDifferent(record.getId(), "COMPLETED");
            StringBuilder errorMsg = new StringBuilder();
            boolean linked = this.createHardlinksByContentPath(record, contentPath, errorMsg);
            String finalStatus = linked ? "LINKED" : "LINK_FAILED";
            String errorStr = linked ? null : (errorMsg.length() > 0 ? errorMsg.toString() : "重新整理失败");
            this.downloadRecordService.updateStatusWithReason(record.getId(), finalStatus, errorStr);
            return linked ? new MovieDownloadRecordSyncJob.ReorganizeResult(true, "重新整理成功") : new MovieDownloadRecordSyncJob.ReorganizeResult(false, errorStr);
         }
      } else {
         return new MovieDownloadRecordSyncJob.ReorganizeResult(false, "下载记录不存在");
      }
   }

   private String resolveReorganizeContentPath(MovieDownloadRecordEntity record) {
      if (record == null) {
         return null;
      } else {
         String sourcePath = record.getSourceFilePath();
         if (StringUtils.hasText(sourcePath)) {
            try {
               Path source = Paths.get(sourcePath);
               if (Files.exists(source)) {
                  return source.toString();
               }
            } catch (Exception var4) {
               log.warn("解析历史源路径失败 recordId={}: {}", record.getId(), var4.getMessage());
            }
         }

         MovieQbittorrentTorrent torrent = this.findReorganizeTorrent(record);
         if (torrent == null) {
            return null;
         } else if (!this.isCompleted(torrent)) {
            return null;
         } else {
            this.downloadRecordService.updateQbInfo(record.getId(), record.getQbTag(), torrent.getHash(), torrent.getName());
            return this.resolveContentPath(torrent);
         }
      }
   }

   private MovieQbittorrentTorrent findReorganizeTorrent(MovieDownloadRecordEntity record) {
      if (record == null) {
         return null;
      } else {
         Long downloaderId = record.getDownloaderId();
         List<MovieQbittorrentTorrent> torrents = List.of();
         boolean hasQbHash = StringUtils.hasText(record.getQbHash());
         if (hasQbHash) {
            torrents = downloaderId != null
               ? this.qbittorrentService.getQueueByHash(downloaderId, record.getQbHash())
               : this.qbittorrentService.getQueueByHash(record.getQbHash());
         }

         String qbTag = record.getQbTag();
         boolean canFallbackByTag = !hasQbHash && StringUtils.hasText(qbTag) && !"Mist".equalsIgnoreCase(qbTag.trim());
         if (CollectionUtils.isEmpty(torrents) && canFallbackByTag) {
            torrents = downloaderId != null
               ? this.qbittorrentService.getQueueByTag(downloaderId, record.getQbTag())
               : this.qbittorrentService.getQueueByTag(record.getQbTag());
         }

         return CollectionUtils.isEmpty(torrents) ? null : this.findMatchingTorrent(record, torrents);
      }
   }

   private void markFailedAndNotify(MovieDownloadRecordEntity record, String reason) {
      if (record != null && record.getId() != null) {
         this.downloadRecordService.updateStatusWithReason(record.getId(), "FAILED", reason);
         this.sendOrganizeNotify(record, false, reason);
      }
   }

   private boolean isCompleted(MovieQbittorrentTorrent torrent) {
      if (torrent == null) {
         return false;
      } else {
         Double progress = torrent.getProgress();
         if (progress != null && progress >= 1.0) {
            return true;
         } else {
            String state = torrent.getState();
            return StringUtils.hasText(state) && COMPLETED_STATES.contains(state);
         }
      }
   }

   private boolean isTorrentMissingTimeout(MovieDownloadRecordEntity record) {
      if (record == null) {
         return false;
      } else {
         Date refTime = record.getUpdateDatetime() != null ? record.getUpdateDatetime() : record.getCreateDatetime();
         if (refTime == null) {
            return false;
         } else {
            long elapsedMinutes = (System.currentTimeMillis() - refTime.getTime()) / 60000L;
            return elapsedMinutes >= 30L;
         }
      }
   }

   private MovieQbittorrentTorrent findMatchingTorrent(MovieDownloadRecordEntity record, List<MovieQbittorrentTorrent> torrents) {
      if (torrents != null && !torrents.isEmpty()) {
         if (record != null && StringUtils.hasText(record.getQbHash())) {
            for (MovieQbittorrentTorrent torrent : torrents) {
               if (record.getQbHash().equalsIgnoreCase(torrent.getHash())) {
                  return torrent;
               }
            }
         }

         if (record != null && StringUtils.hasText(record.getQbTorrentName())) {
            for (MovieQbittorrentTorrent torrentx : torrents) {
               if (record.getQbTorrentName().equalsIgnoreCase(torrentx.getName())) {
                  return torrentx;
               }
            }
         }

         if (record != null && StringUtils.hasText(record.getQbDownloadPath())) {
            String recordPath = this.normalizePath(record.getQbDownloadPath());

            for (MovieQbittorrentTorrent torrentxx : torrents) {
               String torrentPath = this.normalizePath(torrentxx.getSavePath());
               if (StringUtils.hasText(torrentPath) && (torrentPath.equals(recordPath) || torrentPath.startsWith(recordPath + "/"))) {
                  return torrentxx;
               }
            }
         }

         if (record != null && StringUtils.hasText(record.getMovieName())) {
            String name = record.getMovieName().toLowerCase();

            for (MovieQbittorrentTorrent torrentxxx : torrents) {
               if (StringUtils.hasText(torrentxxx.getName()) && torrentxxx.getName().toLowerCase().contains(name)) {
                  return torrentxxx;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private String normalizePath(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String normalized = value.trim().replace("\\", "/");

         while (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
         }

         return normalized;
      }
   }

   private String extractQuality(String filename) {
      if (!StringUtils.hasText(filename)) {
         return null;
      } else {
         Matcher matcher = QUALITY_PATTERN.matcher(filename);
         return matcher.find() ? matcher.group(1) : null;
      }
   }

   private boolean createHardlinks(MovieDownloadRecordEntity record, MovieQbittorrentTorrent torrent, StringBuilder errorMsg) {
      return this.createHardlinks(record, torrent, errorMsg, null);
   }

   private boolean createHardlinks(MovieDownloadRecordEntity record, MovieQbittorrentTorrent torrent, StringBuilder errorMsg, StringBuilder notifyTitle) {
      String contentPath = this.resolveContentPath(torrent);
      return this.createHardlinksByContentPath(record, contentPath, errorMsg, notifyTitle);
   }

   private boolean createHardlinksByContentPath(MovieDownloadRecordEntity record, String contentPath, StringBuilder errorMsg) {
      return this.createHardlinksByContentPath(record, contentPath, errorMsg, null);
   }

   private boolean createHardlinksByContentPath(MovieDownloadRecordEntity record, String contentPath, StringBuilder errorMsg, StringBuilder notifyTitle) {
      String hardlinkPath = record.getHardlinkPath();
      if (!StringUtils.hasText(hardlinkPath)) {
         log.warn("硬链接路径为空，无法处理记录 id={}", record.getId());
         return false;
      } else if (!StringUtils.hasText(contentPath)) {
         log.warn("未找到内容路径，无法处理记录 id={}", record.getId());
         return false;
      } else {
         Path source = Paths.get(contentPath);
         if (!Files.exists(source)) {
            log.warn("内容路径不存在: {}", contentPath);
            return false;
         } else {
            record.setSourceFilePath(contentPath);
            String resolution = this.resolveResolution(source);
            String categoryFolder = this.determineCategoryFolder(record);
            String folderResolution = "movie".equalsIgnoreCase(record.getMediaType()) ? resolution : null;
            String showFolderName = this.formatMovieFolder(record.getMovieName(), record.getMovieYear(), record.getTmdbId(), folderResolution);
            Path targetRoot = Paths.get(hardlinkPath, categoryFolder, showFolderName);

            try {
               Files.createDirectories(targetRoot);
               if ("movie".equalsIgnoreCase(record.getMediaType())) {
                  this.processCoexistAndQuality(targetRoot, record, source);
               }

               List<MovieDownloadRecordSyncJob.LinkEntry> linkedEntries = new ArrayList<>();
               if (Files.isDirectory(source)) {
                  linkedEntries.addAll(this.linkDirectoryWithRename(source, targetRoot, record, errorMsg));
               } else {
                  MovieDownloadRecordSyncJob.LinkEntry entry = this.linkFileWithRename(source, targetRoot, record, errorMsg);
                  if (entry != null) {
                     linkedEntries.add(entry);
                  }
               }

               if (linkedEntries.isEmpty()) {
                  return false;
               } else {
                  if (notifyTitle != null) {
                     String resolvedNotifyTitle = this.buildOrganizeNotifyTitle(record, linkedEntries);
                     if (StringUtils.hasText(resolvedNotifyTitle)) {
                        notifyTitle.setLength(0);
                        notifyTitle.append(resolvedNotifyTitle);
                     }
                  }

                  long totalSizeBytes = 0L;

                  for (MovieDownloadRecordSyncJob.LinkEntry entryx : linkedEntries) {
                     try {
                        totalSizeBytes += Files.size(Paths.get(entryx.linkPath()));
                     } catch (IOException var18) {
                        log.warn("计算文件大小失败: {}", entryx.linkPath());
                     }
                  }

                  if (totalSizeBytes > 0L) {
                     record.setSize(this.formatFileSize(totalSizeBytes));
                  }

                  if (linkedEntries.size() == 1) {
                     record.setLinkFilePath(linkedEntries.get(0).linkPath());
                  } else {
                     record.setLinkFilePath(targetRoot.toString());
                  }

                  this.downloadRecordService.updateRecord(record);
                  this.triggerMetadataScrape(record);
                  this.persistOrganizeRecords(record, linkedEntries);
                  return true;
               }
            } catch (IOException var19) {
               log.warn("硬链接失败 id={}: {}", record.getId(), var19.getMessage());
               return false;
            }
         }
      }
   }

   private void processCoexistAndQuality(Path targetRoot, MovieDownloadRecordEntity record, Path source) {
      Integer coexist = record.getCoexist();
      Integer qualityPriority = record.getQualityPriority();
      boolean allowCoexist = coexist != null && coexist == 1;
      boolean isQualityPriority = qualityPriority != null && qualityPriority == 1;
      if (!allowCoexist) {
         try {
            label115: {
               try (Stream<Path> stream = Files.list(targetRoot)) {
                  List<Path> existingFiles = stream.filter(p -> Files.isRegularFile(p) && this.isVideoFile(p)).toList();
                  if (!existingFiles.isEmpty()) {
                     String targetFilename = this.generateNewFilename(source, record);
                     Path targetFile = StringUtils.hasText(targetFilename) ? targetRoot.resolve(targetFilename) : null;
                     int newScore = this.getResolutionScore(source.getFileName().toString());

                     for (Path existingFile : existingFiles) {
                        if (targetFile == null || !existingFile.equals(targetFile)) {
                           if (isQualityPriority) {
                              int oldScore = this.getResolutionScore(existingFile.getFileName().toString());
                              if (newScore > oldScore) {
                                 try {
                                    Files.deleteIfExists(existingFile);
                                    log.info("画质优先，删除旧文件: {}", existingFile);
                                 } catch (IOException var19) {
                                    log.warn("删除旧文件失败: {}", existingFile);
                                 }
                              }
                           } else {
                              try {
                                 Files.deleteIfExists(existingFile);
                                 log.info("不允许并存，删除旧文件: {}", existingFile);
                              } catch (IOException var18) {
                                 log.warn("删除旧文件失败: {}", existingFile);
                              }
                           }
                        }
                     }
                     break label115;
                  }
               }

               return;
            }
         } catch (IOException var21) {
            log.warn("扫描目标目录失败: {}", targetRoot);
         }
      }
   }

   private int getResolutionScore(String filename) {
      if (!StringUtils.hasText(filename)) {
         return 0;
      } else {
         String lower = filename.toLowerCase();
         if (lower.contains("4320p") || lower.contains("8k")) {
            return 4320;
         } else if (lower.contains("2160p") || lower.contains("4k")) {
            return 2160;
         } else if (lower.contains("1080p")) {
            return 1080;
         } else if (lower.contains("720p")) {
            return 720;
         } else {
            return lower.contains("480p") ? 480 : 0;
         }
      }
   }

   private List<MovieDownloadRecordSyncJob.LinkEntry> linkDirectoryWithRename(
      Path sourceDir, Path targetRoot, MovieDownloadRecordEntity record, StringBuilder errorMsg
   ) throws IOException {
      final List<MovieDownloadRecordSyncJob.LinkEntry> paths = new ArrayList<>();
      Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (Files.isHidden(file)) {
               return FileVisitResult.CONTINUE;
            } else {
               if (MovieDownloadRecordSyncJob.this.isVideoFile(file)) {
                  try {
                     MovieDownloadRecordSyncJob.LinkEntry entry = MovieDownloadRecordSyncJob.this.linkFileWithRename(file, targetRoot, record, errorMsg);
                     if (entry != null) {
                        paths.add(entry);
                     }
                  } catch (Exception var4) {
                     MovieDownloadRecordSyncJob.log.warn("文件硬链接个别失败: {} -> {}", file, var4.getMessage());
                  }
               }

               return FileVisitResult.CONTINUE;
            }
         }
      });
      return paths;
   }

   private MovieDownloadRecordSyncJob.LinkEntry linkFileWithRename(Path sourceFile, Path targetRoot, MovieDownloadRecordEntity record, StringBuilder errorMsg) throws IOException {
      String newFilename = this.generateNewFilename(sourceFile, record);
      if (!StringUtils.hasText(newFilename)) {
         if (errorMsg != null && errorMsg.length() == 0) {
            errorMsg.append("无法生成新文件名: ").append(sourceFile.getFileName());
         }

         return null;
      } else {
         Path targetFile = targetRoot.resolve(newFilename);
         if ("tv".equalsIgnoreCase(record.getMediaType())) {
            boolean shouldProceed = this.handleTvEpisodeConflict(targetFile, sourceFile, record, errorMsg);
            if (!shouldProceed) {
               log.info("根据并存/画质策略，跳过链接: {}", sourceFile);
               return null;
            }
         }

         boolean success = this.createLink(sourceFile, targetFile, record, errorMsg);
         if (!success) {
            return null;
         } else {
            MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parseFromPath(sourceFile);
            String episodeCodes = this.buildEpisodeCodes(meta);
            String episodeSeqs = MovieEpisodeParser.buildEpisodeSeqs(meta);
            return new MovieDownloadRecordSyncJob.LinkEntry(sourceFile.toString(), targetFile.toString(), episodeCodes, episodeSeqs);
         }
      }
   }

   private boolean handleTvEpisodeConflict(Path targetFile, Path sourceFile, MovieDownloadRecordEntity record, StringBuilder errorMsg) {
      Integer coexist = record.getCoexist();
      if (coexist != null && coexist == 1) {
         return true;
      } else {
         Integer qualityPriority = record.getQualityPriority();
         boolean isQualityPriority = qualityPriority != null && qualityPriority == 1;
         Path seasonDir = targetFile.getParent();
         if (!Files.exists(seasonDir)) {
            return true;
         } else {
            MovieEpisodeParser.EpisodeMeta newMeta = MovieEpisodeParser.parseFromPath(sourceFile);
            if (newMeta != null && newMeta.getBeginEpisode() != null) {
               try {
                  boolean var26;
                  try (Stream<Path> stream = Files.list(seasonDir)) {
                     List<Path> existingFiles = stream.filter(p -> Files.isRegularFile(p) && this.isVideoFile(p)).toList();
                     boolean shouldLink = true;
                     int newScore = this.getResolutionScore(sourceFile.getFileName().toString());

                     for (Path existingFile : existingFiles) {
                        if (!existingFile.equals(targetFile)) {
                           MovieEpisodeParser.EpisodeMeta oldMeta = MovieEpisodeParser.parseFromPath(existingFile);
                           if (oldMeta != null && oldMeta.getBeginEpisode() != null) {
                              boolean sameSeason = newMeta.getBeginSeason() == null && oldMeta.getBeginSeason() == null
                                 || newMeta.getBeginSeason() != null && newMeta.getBeginSeason().equals(oldMeta.getBeginSeason());
                              boolean sameEpisode = newMeta.getBeginEpisode().equals(oldMeta.getBeginEpisode());
                              if (sameSeason && sameEpisode) {
                                 if (isQualityPriority) {
                                    int oldScore = this.getResolutionScore(existingFile.getFileName().toString());
                                    if (newScore > oldScore) {
                                       try {
                                          this.deleteRelatedFiles(existingFile);
                                          Files.deleteIfExists(existingFile);
                                          log.info("画质优先(TV)，删除旧文件: {}", existingFile);
                                       } catch (IOException var23) {
                                          log.warn("删除旧文件失败: {}", existingFile);
                                       }
                                    } else {
                                       shouldLink = false;
                                       if (errorMsg != null && errorMsg.length() == 0) {
                                          errorMsg.append("画质优先：现有文件画质(").append(oldScore).append(")不低于新文件(").append(newScore).append(")，跳过整理");
                                       }
                                    }
                                 } else {
                                    try {
                                       this.deleteRelatedFiles(existingFile);
                                       Files.deleteIfExists(existingFile);
                                       log.info("不允许并存(TV)，删除旧文件: {}", existingFile);
                                    } catch (IOException var22) {
                                       log.warn("删除旧文件失败: {}", existingFile);
                                    }
                                 }
                              }
                           }
                        }
                     }

                     var26 = shouldLink;
                  }

                  return var26;
               } catch (IOException var25) {
                  log.warn("扫描剧集目录失败: {}", seasonDir);
                  return true;
               }
            } else {
               return true;
            }
         }
      }
   }

   private void deleteRelatedFiles(Path videoFile) {
      if (videoFile != null) {
         try {
            String filename = videoFile.getFileName().toString();
            String basename = filename;
            int dotIndex = filename.lastIndexOf(46);
            if (dotIndex > 0) {
               basename = filename.substring(0, dotIndex);
            }

            String finalBasename = basename;
            Path parent = videoFile.getParent();
            if (parent != null && Files.exists(parent)) {
               try (Stream<Path> stream = Files.list(parent)) {
                  stream.filter(p -> !p.equals(videoFile)).filter(p -> p.getFileName().toString().startsWith(finalBasename)).forEach(p -> {
                     try {
                        Files.deleteIfExists(p);
                        log.info("删除关联文件: {}", p);
                     } catch (IOException var2x) {
                        log.warn("删除关联文件失败: {}", p);
                     }
                  });
               }
            }
         } catch (Exception var12) {
            log.warn("清理关联文件失败: {}", videoFile);
         }
      }
   }

   private boolean createLink(Path source, Path target, MovieDownloadRecordEntity record, StringBuilder errorMsg) throws IOException {
      if (Files.exists(target)) {
         Integer overwrite = record.getOverwrite();
         Integer sizePriority = record.getSizePriority();
         boolean allowOverwrite = overwrite != null && overwrite == 1;
         boolean isSizePriority = sizePriority != null && sizePriority == 1;
         if (!allowOverwrite) {
            if (errorMsg != null && errorMsg.length() == 0) {
               errorMsg.append("文件已存在且未开启覆盖，跳过整理");
            }

            return false;
         }

         if (isSizePriority) {
            long sourceSize = Files.size(source);
            long targetSize = Files.size(target);
            if (sourceSize <= targetSize) {
               if (errorMsg != null && errorMsg.length() == 0) {
                  if (sourceSize == targetSize) {
                     errorMsg.append("文件已存在且大小一致，跳过重复整理");
                  } else {
                     errorMsg.append("大小优先：新文件(")
                        .append(this.formatFileSize(sourceSize))
                        .append(")小于现有文件(")
                        .append(this.formatFileSize(targetSize))
                        .append(")，跳过");
                  }
               }

               return false;
            }
         }

         try {
            this.deleteRelatedFiles(target);
            Files.delete(target);
         } catch (IOException var13) {
            log.error("删除已存在文件失败: {}", target, var13);
            return false;
         }
      }

      Files.createDirectories(target.getParent());
      Integer mode = record.getHardlinkMode();
      boolean useHardlink = mode == null || mode == 1;

      try {
         if (useHardlink) {
            Files.createLink(target, source);
         } else {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
         }

         return true;
      } catch (IOException var14) {
         if (useHardlink && Files.isRegularFile(source)) {
            log.warn("硬链接失败，尝试复制: {} -> {}", source, target);
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            return true;
         } else {
            throw var14;
         }
      }
   }

   private String buildEpisodeCodes(MovieEpisodeParser.EpisodeMeta meta) {
      List<String> codes = MovieEpisodeParser.buildEpisodeCodes(meta, 200);
      if (codes != null && !codes.isEmpty()) {
         StringJoiner joiner = new StringJoiner(",");

         for (String code : codes) {
            if (StringUtils.hasText(code)) {
               joiner.add(code);
            }
         }

         return joiner.toString();
      } else {
         return "";
      }
   }

   private void persistOrganizeRecords(MovieDownloadRecordEntity record, List<MovieDownloadRecordSyncJob.LinkEntry> entries) {
      if (record != null && record.getId() != null && entries != null && !entries.isEmpty()) {
         List<MovieDownloadRecordSyncJob.AtomicLinkEntry> atomicEntries = this.splitToAtomicEntries(entries, record.getMediaType());
         if (!atomicEntries.isEmpty()) {
            String originalTitle = record.getTitle();
            MovieDownloadRecordSyncJob.AtomicLinkEntry first = atomicEntries.get(0);
            this.applyAtomicEntry(record, originalTitle, first);
            this.downloadRecordService.updateRecord(record);

            for (int i = 1; i < atomicEntries.size(); i++) {
               MovieDownloadRecordSyncJob.AtomicLinkEntry entry = atomicEntries.get(i);
               MovieDownloadRecordEntity extraRecord = new MovieDownloadRecordEntity();
               BeanUtils.copyProperties(record, extraRecord);
               extraRecord.setId(null);
               extraRecord.setStatus("LINKED");
               extraRecord.setCreateDatetime(null);
               extraRecord.setUpdateDatetime(null);
               extraRecord.setCreateUserId(null);
               extraRecord.setUpdateUserId(null);
               extraRecord.setCreateUserName(null);
               extraRecord.setUpdateUserName(null);
               this.applyAtomicEntry(extraRecord, originalTitle, entry);
               this.downloadRecordMapper.insert(extraRecord);
            }
         }
      }
   }

   private void applyAtomicEntry(MovieDownloadRecordEntity record, String originalTitle, MovieDownloadRecordSyncJob.AtomicLinkEntry entry) {
      if (record != null && entry != null) {
         record.setSourceFilePath(entry.sourcePath());
         record.setLinkFilePath(entry.linkPath());
         record.setEpisodeCodes(entry.episodeCode());
         record.setEpisodeSeqs(entry.episodeSeq());
         record.setTitle(this.resolveAtomicTitle(originalTitle, record.getMediaType(), entry.episodeCode()));
      }
   }

   private List<MovieDownloadRecordSyncJob.AtomicLinkEntry> splitToAtomicEntries(List<MovieDownloadRecordSyncJob.LinkEntry> entries, String mediaType) {
      if (!"tv".equalsIgnoreCase(mediaType)) {
         MovieDownloadRecordSyncJob.LinkEntry first = entries.get(0);
         return List.of(new MovieDownloadRecordSyncJob.AtomicLinkEntry(first.sourcePath(), first.linkPath(), "", ""));
      } else {
         List<MovieDownloadRecordSyncJob.AtomicLinkEntry> results = new ArrayList<>();
         Set<String> uniqueKeys = new LinkedHashSet<>();

         for (MovieDownloadRecordSyncJob.LinkEntry entry : entries) {
            if (entry != null) {
               List<String> episodeCodes = this.splitEpisodeCodes(entry.episodeCodes());
               if (episodeCodes.isEmpty()) {
                  String key = this.buildAtomicUniqueKey(entry.linkPath(), "");
                  if (uniqueKeys.add(key)) {
                     results.add(new MovieDownloadRecordSyncJob.AtomicLinkEntry(entry.sourcePath(), entry.linkPath(), "", ""));
                  }
               } else {
                  for (String code : episodeCodes) {
                     String normalizedCode = code.trim();
                     if (StringUtils.hasText(normalizedCode)) {
                        String key = this.buildAtomicUniqueKey(entry.linkPath(), normalizedCode);
                        if (uniqueKeys.add(key)) {
                           results.add(
                              new MovieDownloadRecordSyncJob.AtomicLinkEntry(
                                 entry.sourcePath(), entry.linkPath(), normalizedCode, this.resolveEpisodeSeqFromCode(normalizedCode)
                              )
                           );
                        }
                     }
                  }
               }
            }
         }

         return results;
      }
   }

   private List<String> splitEpisodeCodes(String episodeCodes) {
      return !StringUtils.hasText(episodeCodes) ? List.of() : Arrays.stream(episodeCodes.split(",")).map(String::trim).filter(StringUtils::hasText).toList();
   }

   private String resolveEpisodeSeqFromCode(String episodeCode) {
      MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parse(episodeCode);
      String seqs = MovieEpisodeParser.buildEpisodeSeqs(meta);
      return StringUtils.hasText(seqs) ? seqs : "";
   }

   private String resolveAtomicTitle(String originalTitle, String mediaType, String episodeCode) {
      if ("tv".equalsIgnoreCase(mediaType) && StringUtils.hasText(episodeCode)) {
         return episodeCode;
      } else {
         return StringUtils.hasText(originalTitle) ? originalTitle.trim() : "";
      }
   }

   private String buildOrganizeNotifyTitle(MovieDownloadRecordEntity record, List<MovieDownloadRecordSyncJob.LinkEntry> linkedEntries) {
      String fallbackTitle = record != null && StringUtils.hasText(record.getTitle()) ? record.getTitle().trim() : "";
      if (record != null && "tv".equalsIgnoreCase(record.getMediaType()) && !CollectionUtils.isEmpty(linkedEntries)) {
         List<MovieDownloadRecordSyncJob.AtomicLinkEntry> atomicEntries = this.splitToAtomicEntries(linkedEntries, record.getMediaType());
         if (CollectionUtils.isEmpty(atomicEntries)) {
            return fallbackTitle;
         } else {
            Map<Integer, Set<Integer>> seasonEpisodes = new TreeMap<>();

            for (MovieDownloadRecordSyncJob.AtomicLinkEntry entry : atomicEntries) {
               if (entry != null && StringUtils.hasText(entry.episodeCode())) {
                  Matcher matcher = EPISODE_CODE_PATTERN.matcher(entry.episodeCode().trim());
                  if (matcher.matches()) {
                     try {
                        int season = Integer.parseInt(matcher.group(1));
                        int episode = Integer.parseInt(matcher.group(2));
                        seasonEpisodes.computeIfAbsent(season, key -> new TreeSet<>()).add(episode);
                     } catch (NumberFormatException var14) {
                     }
                  }
               }
            }

            if (seasonEpisodes.isEmpty()) {
               return fallbackTitle;
            } else {
               List<String> rangeParts = new ArrayList<>();

               for (Entry<Integer, Set<Integer>> entryx : seasonEpisodes.entrySet()) {
                  int season = entryx.getKey();
                  List<Integer> episodes = new ArrayList<>(entryx.getValue());

                  for (int index = 0; index < episodes.size(); index++) {
                     int start = episodes.get(index);
                     int end = start;

                     while (index + 1 < episodes.size() && episodes.get(index + 1) == end + 1) {
                        end = episodes.get(++index);
                     }

                     rangeParts.add(this.formatEpisodeRange(season, start, end));
                  }
               }

               return rangeParts.isEmpty() ? fallbackTitle : String.join("、", rangeParts);
            }
         }
      } else {
         return fallbackTitle;
      }
   }

   private String formatEpisodeRange(int season, int startEpisode, int endEpisode) {
      return startEpisode == endEpisode
         ? String.format("S%02dE%02d", season, startEpisode)
         : String.format("S%02dE%02d-E%02d", season, startEpisode, endEpisode);
   }

   private String buildAtomicUniqueKey(String linkPath, String episodeCode) {
      String normalizedLink = StringUtils.hasText(linkPath) ? linkPath.trim() : "";
      String normalizedCode = StringUtils.hasText(episodeCode) ? episodeCode.trim() : "";
      return normalizedLink + "#" + normalizedCode;
   }

   private void triggerMetadataScrape(MovieDownloadRecordEntity record) {
      if (record != null && record.getTmdbId() != null && StringUtils.hasText(record.getMediaType())) {
         try {
            this.metadataScrapeService.scrape(record);
         } catch (Exception var3) {
            log.warn("刮削失败，不影响硬链接状态 id={}: {}", record.getId(), var3.getMessage());
         }
      }
   }

   private String generateNewFilename(Path sourceFile, MovieDownloadRecordEntity record) {
      String extension = this.getExtension(sourceFile.getFileName().toString());
      String originalFilename = sourceFile.getFileName().toString();
      String mediaType = record.getMediaType();
      boolean isTv = "tv".equalsIgnoreCase(mediaType);
      String safeTitle = this.safeTitle(record.getMovieName());
      String safeYear = this.sanitizeName(record.getMovieYear());
      String resolution = "";
      Matcher resMatcher = RESOLUTION_PATTERN.matcher(originalFilename);
      if (resMatcher.find()) {
         resolution = resMatcher.group(1);
      }

      if (isTv) {
         MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parseFromPath(sourceFile);
         if (meta != null && meta.getBeginEpisode() != null) {
            int season = meta.getBeginSeason() != null ? meta.getBeginSeason() : 1;
            String seasonFolder = "Season " + season;
            String seasonEpisode = MovieEpisodeParser.buildSeasonEpisodeTag(meta);
            String episodeSeqs = MovieEpisodeParser.buildEpisodeSeqs(meta);
            String part = this.extractPart(originalFilename);
            StringBuilder name = new StringBuilder();
            name.append(safeTitle);
            if (StringUtils.hasText(seasonEpisode)) {
               name.append(" - ").append(seasonEpisode);
            }

            if (StringUtils.hasText(part)) {
               name.append("-").append(part);
            }

            if (StringUtils.hasText(episodeSeqs)) {
               name.append(" - 第").append(episodeSeqs).append("集");
            }

            if (StringUtils.hasText(resolution)) {
               name.append(" - ").append(resolution);
            }

            name.append(extension);
            return Paths.get(seasonFolder, name.toString()).toString();
         } else {
            log.warn("未识别到集数，跳过重命名: {}", sourceFile);
            return null;
         }
      } else {
         StringBuilder namex = new StringBuilder();
         namex.append(safeTitle);
         if (StringUtils.hasText(safeYear)) {
            namex.append(" (").append(safeYear).append(")");
         }

         if (record.getTmdbId() != null) {
            if (StringUtils.hasText(safeYear)) {
               namex.append(" ");
            } else {
               namex.append(" ");
            }

            namex.append("{tmdb-").append(record.getTmdbId()).append("}");
         }

         String partx = this.extractPart(originalFilename);
         if (StringUtils.hasText(partx)) {
            namex.append("-").append(partx);
         }

         if (StringUtils.hasText(resolution)) {
            namex.append(" - ").append(resolution);
         }

         namex.append(extension);
         return namex.toString();
      }
   }

   private String extractPart(String filename) {
      if (!StringUtils.hasText(filename)) {
         return "";
      } else {
         Matcher matcher = PART_PATTERN.matcher(filename);
         return matcher.find() ? "Part" + matcher.group(1) : "";
      }
   }

   private boolean isVideoFile(Path file) {
      String name = file.getFileName().toString().toLowerCase();
      return name.endsWith(".mkv")
         || name.endsWith(".mp4")
         || name.endsWith(".avi")
         || name.endsWith(".mov")
         || name.endsWith(".wmv")
         || name.endsWith(".iso")
         || name.endsWith(".m4v")
         || name.endsWith(".ts")
         || name.endsWith(".m2ts")
         || name.endsWith(".flv")
         || name.endsWith(".webm")
         || name.endsWith(".mpg")
         || name.endsWith(".mpeg")
         || name.endsWith(".rmvb");
   }

   private String getExtension(String filename) {
      int dot = filename.lastIndexOf(46);
      return dot > 0 ? filename.substring(dot) : "";
   }

   private String determineCategoryFolder(MovieDownloadRecordEntity record) {
      if (record.getTmdbId() == null) {
         return "未分类";
      } else {
         try {
            String mediaType = record.getMediaType();
            if ("movie".equalsIgnoreCase(mediaType)) {
               return this.determineMovieCategory(record.getTmdbId());
            }

            if ("tv".equalsIgnoreCase(mediaType)) {
               return this.determineTvCategory(record.getTmdbId());
            }
         } catch (Exception var3) {
            log.warn("获取TMDB信息失败，使用默认分类 id={}: {}", record.getId(), var3.getMessage());
         }

         return "未分类";
      }
   }

   private String determineMovieCategory(Long tmdbId) {
      try {
         MovieDb movie = this.tmdbService.getMovieDetails(tmdbId.intValue(), "zh-CN");
         if (movie == null) {
            return "未分类";
         } else {
            String category = this.categoryResolver.resolveMovie(movie);
            return StringUtils.hasText(category) ? category : "未分类";
         }
      } catch (Exception var4) {
         log.warn("获取电影详情失败 tmdbId={}: {}", tmdbId, var4.getMessage());
         return "未分类";
      }
   }

   private String determineTvCategory(Long tmdbId) {
      try {
         TvSeriesDb tv = this.tmdbService.getTvSeries(tmdbId.intValue(), "zh-CN");
         if (tv == null) {
            return "未分类";
         } else {
            String category = this.categoryResolver.resolveTv(tv);
            return StringUtils.hasText(category) ? category : "未分类";
         }
      } catch (Exception var4) {
         log.warn("获取剧集详情失败 tmdbId={}: {}", tmdbId, var4.getMessage());
         return "未分类";
      }
   }

   private String resolveContentPath(MovieQbittorrentTorrent torrent) {
      if (torrent == null) {
         return null;
      } else if (StringUtils.hasText(torrent.getContentPath())) {
         return torrent.getContentPath();
      } else {
         return StringUtils.hasText(torrent.getSavePath()) && StringUtils.hasText(torrent.getName())
            ? Paths.get(torrent.getSavePath(), torrent.getName()).toString()
            : null;
      }
   }

   private String resolveResolution(Path source) {
      if (source == null) {
         return null;
      } else {
         try {
            if (Files.isRegularFile(source)) {
               return this.extractResolution(source.getFileName().toString());
            }

            if (Files.isDirectory(source)) {
               try (Stream<Path> stream = Files.walk(source, 2)) {
                  Path firstVideo = stream.filter(p -> Files.isRegularFile(p) && this.isVideoFile(p)).findFirst().orElse(null);
                  if (firstVideo != null) {
                     return this.extractResolution(firstVideo.getFileName().toString());
                  }

                  return null;
               }
            }
         } catch (Exception var7) {
            log.warn("提取分辨率失败: {}", source);
         }

         return null;
      }
   }

   private String extractResolution(String filename) {
      if (!StringUtils.hasText(filename)) {
         return null;
      } else {
         Matcher matcher = RESOLUTION_PATTERN.matcher(filename);
         if (matcher.find()) {
            String resolution = matcher.group(1).toLowerCase();

            return switch (resolution) {
               case "4k" -> "2160p";
               case "8k" -> "4320p";
               default -> resolution;
            };
         } else {
            return null;
         }
      }
   }

   private String formatMovieFolder(String name, String year, Long tmdbId, String resolution) {
      String safeName = this.safeTitle(name);
      String safeYear = this.sanitizeName(year);
      StringBuilder builder = new StringBuilder(safeName);
      if (StringUtils.hasText(safeYear)) {
         builder.append(" (").append(safeYear).append(")");
      }

      if (tmdbId != null) {
         builder.append(" {tmdb-").append(tmdbId).append("}");
      }

      if (StringUtils.hasText(resolution)) {
         builder.append(" {").append(resolution).append("}");
      }

      return builder.toString();
   }

   private String sanitizeName(String value) {
      return !StringUtils.hasText(value) ? "" : value.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
   }

   private String safeTitle(String value) {
      String sanitized = this.sanitizeName(value);
      return StringUtils.hasText(sanitized) ? sanitized : "未知影片";
   }

   private void sendOrganizeNotify(MovieDownloadRecordEntity record, boolean success) {
      this.sendOrganizeNotify(record, success, "硬链接失败，请检查路径配置", null);
   }

   private void sendOrganizeNotify(MovieDownloadRecordEntity record, boolean success, String errorMessage) {
      this.sendOrganizeNotify(record, success, errorMessage, null);
   }

   private void sendOrganizeNotify(MovieDownloadRecordEntity record, boolean success, String errorMessage, String notifyTitle) {
      try {
         String mediaTypeLabel = "tv".equalsIgnoreCase(record.getMediaType()) ? "剧集" : "电影";
         String statusLabel = success ? "整理完成" : "整理失败";
         String templateCode = success ? "subscribe_organize_success" : "subscribe_organize_failed";
         Map<String, String> extras = new HashMap<>();
         String resolution = "";
         String quality = "";
         if (StringUtils.hasText(record.getSourceFilePath())) {
            try {
               Path source = Paths.get(record.getSourceFilePath());
               resolution = this.resolveResolution(source);
               quality = this.extractQuality(source.getFileName().toString());
            } catch (Exception var19) {
               log.warn("通知提取媒体信息失败: {}", var19.getMessage());
            }
         }

         if (!StringUtils.hasText(resolution)) {
            resolution = "未知";
         }

         if (!StringUtils.hasText(quality)) {
            quality = "未知";
         }

         String displayTitle = StringUtils.hasText(notifyTitle) ? notifyTitle.trim() : record.getTitle();
         String titleWithQuality = StringUtils.hasText(displayTitle) ? displayTitle : "";
         if (!"未知".equals(resolution) || !"未知".equals(quality)) {
            titleWithQuality = titleWithQuality + " [" + resolution + (StringUtils.hasText(quality) ? " - " + quality : "") + "]";
         }

         String siteName = "";
         if (record.getSiteId() != null) {
            try {
               MoviePtSite site = this.moviePtSiteService.getById(record.getSiteId());
               if (site != null && StringUtils.hasText(site.getName())) {
                  siteName = site.getName();
               }
            } catch (Exception var18) {
               log.warn("获取站点信息失败 siteId={}: {}", record.getSiteId(), var18.getMessage());
            }
         }

         extras.put("subscribeName", StringUtils.hasText(record.getMovieName()) ? record.getMovieName() : "未知订阅");
         extras.put("movieName", StringUtils.hasText(record.getMovieName()) ? record.getMovieName() : "未知影片");
         extras.put("mediaTypeLabel", mediaTypeLabel);
         extras.put("downloadTitle", titleWithQuality);
         extras.put("resolution", resolution);
         extras.put("quality", quality);
         extras.put("downloadStatus", statusLabel);
         extras.put("siteName", siteName);
         if (!success) {
            extras.put("errorMessage", StringUtils.hasText(errorMessage) ? errorMessage : "整理失败");
         }

         String statusOverview = String.format(
            "[%s] %s %s", mediaTypeLabel, StringUtils.hasText(record.getMovieName()) ? record.getMovieName() : "未知影片", statusLabel
         );
         String movieName = StringUtils.hasText(record.getMovieName()) ? record.getMovieName() : "未知影片";
         if (StringUtils.hasText(record.getPosterUrl())) {
            SendPhotoRequest request = new SendPhotoRequest();
            request.setParseMode("HTML");
            request.setName("订阅整理通知：" + movieName);
            request.setImgUrl(record.getPosterUrl());
            request.setBackdropPath(record.getCoverUrl());
            this.movieNotifyTmdbEnrichService.fillTmdbInfo(request, record.getTmdbId(), "tv".equalsIgnoreCase(record.getMediaType()) ? "tv" : "movie");
            request.setExtraVariables(extras);
            request.getExtraVariables().put("isSuccess", String.valueOf(success));
            this.notifyUtils.sendMultiChannel(request, templateCode, NotifyMessageType.PHOTO_DETAIL, false, "telegram", "dingding", "messagepush");
            SendPhotoRequest wechatRequest = new SendPhotoRequest();
            BeanUtils.copyProperties(request, wechatRequest);
            if (StringUtils.hasText(record.getCoverUrl())) {
               wechatRequest.setImgUrl(record.getCoverUrl());
            }

            this.notifyUtils.sendMultiChannel(wechatRequest, templateCode, NotifyMessageType.PHOTO_DETAIL, false, "wechat", "wechatBot");
         } else {
            SendMessageRequest request = new SendMessageRequest();
            request.setParseMode("HTML");
            request.setName("订阅整理通知：" + movieName);
            request.setOverview(statusOverview);
            request.setImgUrl(record.getPosterUrl());
            request.setExtraVariables(extras);
            this.notifyUtils.sendMultiChannel(request, templateCode, false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
         }
      } catch (Exception var20) {
         log.warn("发送整理通知失败 recordId={}: {}", record.getId(), var20.getMessage());
      }
   }

   private void fillTmdbInfo(SendPhotoRequest request, Long tmdbId, String type) {
      if (tmdbId != null) {
         try {
            if ("movie".equalsIgnoreCase(type)) {
               MovieDb movie = this.tmdbService.getMovieDetails(tmdbId.intValue(), "zh-CN", MovieAppendToResponse.TRANSLATIONS);
               if (movie != null
                  && !StringUtils.hasText(movie.getOverview())
                  && movie.getTranslations() != null
                  && movie.getTranslations().getTranslations() != null) {
                  List<Translation> translations = movie.getTranslations()
                     .getTranslations()
                     .stream()
                     .filter(t -> t.getData() != null && StringUtils.hasText(t.getData().getOverview()))
                     .toList();
                  Translation bestTranslation = translations.stream()
                     .filter(t -> "zh".equalsIgnoreCase(t.getIso6391()) || "cn".equalsIgnoreCase(t.getIso6391()))
                     .findFirst()
                     .orElseGet(
                        () -> translations.stream()
                              .filter(t -> "en".equalsIgnoreCase(t.getIso6391()))
                              .findFirst()
                              .orElseGet(() -> translations.stream().findAny().orElse(null))
                     );
                  if (bestTranslation != null) {
                     movie.setOverview(bestTranslation.getData().getOverview());
                  }
               }

               if (movie != null) {
                  if (StringUtils.hasText(movie.getReleaseDate())) {
                     try {
                        LocalDate date = LocalDate.parse(movie.getReleaseDate());
                        request.setProductionYear(date.getYear());
                     } catch (Exception var8) {
                     }
                  }

                  if (movie.getGenres() != null) {
                     request.setGenres(movie.getGenres().stream().map(g -> g.getName()).collect(Collectors.joining(",")));
                  }

                  request.setVoteAverage(movie.getVoteAverage());
                  request.setVoteCount(movie.getVoteCount());
                  if (movie.getProductionCountries() != null) {
                     request.setProductionCountries(movie.getProductionCountries().stream().map(p -> p.getName()).collect(Collectors.joining(",")));
                  }

                  if (StringUtils.hasText(movie.getOverview())) {
                     request.setOverview(movie.getOverview());
                  }

                  request.setType("Movie");
                  request.setDisplayTitle(movie.getTitle());
               }
            } else if ("tv".equalsIgnoreCase(type)) {
               TvSeriesDb tv = this.tmdbService.getTvSeries(tmdbId.intValue(), "zh-CN", TvSeriesAppendToResponse.TRANSLATIONS);
               if (tv != null && !StringUtils.hasText(tv.getOverview()) && tv.getTranslations() != null && tv.getTranslations().getTranslations() != null) {
                  List<info.movito.themoviedbapi.model.tv.series.Translation> translations = tv.getTranslations()
                     .getTranslations()
                     .stream()
                     .filter(t -> t.getData() != null && StringUtils.hasText(t.getData().getOverview()))
                     .toList();
                  info.movito.themoviedbapi.model.tv.series.Translation bestTranslation = translations.stream()
                     .filter(t -> "zh".equalsIgnoreCase(t.getIso6391()) || "cn".equalsIgnoreCase(t.getIso6391()))
                     .findFirst()
                     .orElseGet(
                        () -> translations.stream()
                              .filter(t -> "en".equalsIgnoreCase(t.getIso6391()))
                              .findFirst()
                              .orElseGet(() -> translations.stream().findAny().orElse(null))
                     );
                  if (bestTranslation != null) {
                     tv.setOverview(bestTranslation.getData().getOverview());
                  }
               }

               if (tv != null) {
                  if (StringUtils.hasText(tv.getFirstAirDate())) {
                     try {
                        LocalDate date = LocalDate.parse(tv.getFirstAirDate());
                        request.setProductionYear(date.getYear());
                     } catch (Exception var7) {
                     }
                  }

                  if (tv.getGenres() != null) {
                     request.setGenres(tv.getGenres().stream().map(g -> g.getName()).collect(Collectors.joining(",")));
                  }

                  request.setVoteAverage(tv.getVoteAverage());
                  request.setVoteCount(tv.getVoteCount());
                  if (tv.getOriginCountry() != null) {
                     request.setProductionCountries(String.join(",", tv.getOriginCountry()));
                  }

                  if (StringUtils.hasText(tv.getOverview())) {
                     request.setOverview(tv.getOverview());
                  }

                  request.setType("Series");
                  request.setDisplayTitle(tv.getName());
               }
            }
         } catch (Exception var9) {
            log.warn("获取TMDB详情失败 tmdbId={}: {}", tmdbId, var9.getMessage());
         }
      }
   }

   private void refreshRecordConfig(MovieDownloadRecordEntity record) {
      String downloadPath = record.getQbDownloadPath();
      if (StringUtils.hasText(downloadPath)) {
         try {
            List<MovieScrapePathConfig> configs = this.scrapePathConfigService.list();
            if (configs == null || configs.isEmpty()) {
               return;
            }

            MovieScrapePathConfig matched = null;
            String normDownloadPath = Paths.get(downloadPath).normalize().toAbsolutePath().toString();

            for (MovieScrapePathConfig config : configs) {
               String configPath = config.getQbDownloadPath();
               if (StringUtils.hasText(configPath)) {
                  String normConfigPath = Paths.get(configPath).normalize().toAbsolutePath().toString();
                  if (normDownloadPath.equals(normConfigPath)) {
                     matched = config;
                     break;
                  }
               }
            }

            if (matched != null) {
               record.setCoexist(matched.getCoexist());
               record.setQualityPriority(matched.getQualityPriority());
               record.setOverwrite(matched.getOverwrite());
               record.setSizePriority(matched.getSizePriority());
               record.setHardlinkMode(matched.getHardlinkMode());
               if (StringUtils.hasText(matched.getHardlinkPath())) {
                  record.setHardlinkPath(matched.getHardlinkPath());
               }

               this.downloadRecordService.updateRecord(record);
            }
         } catch (Exception var10) {
            log.warn("刷新下载记录配置失败 id={}: {}", record.getId(), var10.getMessage());
         }
      }
   }

   private String formatFileSize(long size) {
      if (size <= 0L) {
         return "0 B";
      } else {
         String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
         int digitGroups = (int)(Math.log10((double)size) / Math.log10(1024.0));
         return new DecimalFormat("#,##0.#").format((double)size / Math.pow(1024.0, (double)digitGroups)) + " " + units[digitGroups];
      }
   }

   @Generated
   public MovieDownloadRecordSyncJob(
      final MovieDownloadRecordService downloadRecordService,
      final MovieQbittorrentService qbittorrentService,
      final MovieDownloadRecordMapper downloadRecordMapper,
      final MovieMetadataScrapeService metadataScrapeService,
      final TmdbService tmdbService,
      final MovieNotifyTmdbEnrichService movieNotifyTmdbEnrichService,
      final MovieCategoryResolver categoryResolver,
      final NotifyUtils notifyUtils,
      final MovieScrapePathConfigService scrapePathConfigService,
      final MoviePtSiteService moviePtSiteService
   ) {
      this.downloadRecordService = downloadRecordService;
      this.qbittorrentService = qbittorrentService;
      this.downloadRecordMapper = downloadRecordMapper;
      this.metadataScrapeService = metadataScrapeService;
      this.tmdbService = tmdbService;
      this.movieNotifyTmdbEnrichService = movieNotifyTmdbEnrichService;
      this.categoryResolver = categoryResolver;
      this.notifyUtils = notifyUtils;
      this.scrapePathConfigService = scrapePathConfigService;
      this.moviePtSiteService = moviePtSiteService;
   }

   private static record AtomicLinkEntry(String sourcePath, String linkPath, String episodeCode, String episodeSeq) {
   }

   private static record LinkEntry(String sourcePath, String linkPath, String episodeCodes, String episodeSeqs) {
   }

   private static record ReorganizeResult(boolean success, String message) {
   }
}
