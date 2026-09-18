package com.una.embyhub.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;
import com.una.embyhub.movie.entity.MovieQbittorrentConfigEntity;
import com.una.embyhub.movie.mapper.MovieDownloadRecordMapper;
import com.una.embyhub.movie.mapper.MovieQbittorrentConfigMapper;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieDownloadRecordFileResponse;
import com.una.embyhub.movie.model.MovieDownloadRecordWithDetailsResponse;
import com.una.embyhub.movie.model.MoviePtDownloadRequest;
import com.una.embyhub.movie.model.MovieScrapePathConfig;
import com.una.embyhub.movie.service.MovieDownloadRecordService;
import com.una.embyhub.movie.util.MovieEpisodeParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MovieDownloadRecordServiceImpl implements MovieDownloadRecordService {
   private static final int QB_TAG_LENGTH = 10;
   private static final String QB_TAG_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
   private static final SecureRandom RANDOM = new SecureRandom();
   private static final Set<String> MEDIA_EXTS = Set.of(
      ".mkv", ".mp4", ".avi", ".mov", ".wmv", ".iso", ".m4v", ".ts", ".m2ts", ".flv", ".webm", ".mpg", ".mpeg", ".rmvb"
   );
   private final MovieDownloadRecordMapper recordMapper;
   private final MovieQbittorrentConfigMapper qbittorrentConfigMapper;

   @Override
   public MovieDownloadRecordEntity createRecord(MoviePtDownloadRequest request, MovieScrapePathConfig config, String savePath, Long downloaderId) {
      MovieDownloadRecordEntity entity = new MovieDownloadRecordEntity();
      entity.setMovieName(this.trimToEmpty(request.getMovieName()));
      entity.setMovieYear(this.trimToEmpty(request.getMovieYear()));
      entity.setTitle(this.resolveTitle(request));
      entity.setTmdbId(request.getTmdbId());
      entity.setMediaType(this.trimToEmpty(request.getMediaType()));
      this.fillEpisodeFields(entity);
      entity.setSubscribeId(request.getSubscribeId());
      entity.setDownloaderId(downloaderId);
      entity.setPosterUrl(this.trimToEmpty(request.getPosterUrl()));
      entity.setCoverUrl(this.trimToEmpty(request.getCoverUrl()));
      entity.setSize(this.trimToEmpty(request.getSize()));
      if (StringUtils.hasText(savePath)) {
         entity.setQbDownloadPath(savePath.trim());
      } else if (config != null) {
         entity.setQbDownloadPath(config.getQbDownloadPath());
      }

      if (config != null) {
         entity.setHardlinkPath(config.getHardlinkPath());
         entity.setOverwrite(config.getOverwrite());
         entity.setCoexist(config.getCoexist());
         entity.setQualityPriority(config.getQualityPriority());
         entity.setSizePriority(config.getSizePriority());
         entity.setHardlinkMode(config.getHardlinkMode());
      }

      entity.setQbTag(this.generateRandomTag());
      entity.setStatus("PENDING");
      entity.setSiteId(request.getSiteId());
      this.recordMapper.insert(entity);
      return entity;
   }

   private void fillEpisodeFields(MovieDownloadRecordEntity entity) {
      if (entity != null && "tv".equalsIgnoreCase(this.trimToEmpty(entity.getMediaType()))) {
         MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parse(entity.getTitle());
         if (meta != null && meta.hasEpisode()) {
            List<String> codes = MovieEpisodeParser.buildEpisodeCodes(meta, 200);
            if (!CollectionUtils.isEmpty(codes)) {
               entity.setEpisodeCodes(String.join(",", codes));
            }

            String seqs = MovieEpisodeParser.buildEpisodeSeqs(meta);
            if (StringUtils.hasText(seqs)) {
               entity.setEpisodeSeqs(seqs);
            }
         }
      }
   }

   @Override
   public void updateStatus(Long id, String status) {
      if (id != null && StringUtils.hasText(status)) {
         MovieDownloadRecordEntity entity = this.recordMapper.selectById(id);
         if (entity != null) {
            entity.setStatus(status);
            this.recordMapper.updateById(entity);
         }
      }
   }

   @Override
   public void updateStatusWithReason(Long id, String status, String reason) {
      if (id != null && StringUtils.hasText(status)) {
         MovieDownloadRecordEntity entity = this.recordMapper.selectById(id);
         if (entity != null) {
            entity.setStatus(status);
            entity.setErrorMessage(StringUtils.hasText(reason) ? reason.trim() : null);
            this.recordMapper.updateById(entity);
         }
      }
   }

   @Override
   public boolean updateStatusIfDifferent(Long id, String status) {
      if (id != null && StringUtils.hasText(status)) {
         MovieDownloadRecordEntity update = new MovieDownloadRecordEntity();
         update.setStatus(status);
         update.setErrorMessage(null);
         QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
         wrapper.eq("id", id);
         wrapper.eq("del_flag", Integer.valueOf(0));
         wrapper.and(condition -> condition.isNull("status").or().ne("status", status));
         return this.recordMapper.update(update, wrapper) > 0;
      } else {
         return false;
      }
   }

   @Override
   public void updateQbInfo(Long id, String qbTag, String qbHash, String qbTorrentName) {
      if (id != null) {
         MovieDownloadRecordEntity entity = this.recordMapper.selectById(id);
         if (entity != null) {
            if (StringUtils.hasText(qbTag)) {
               entity.setQbTag(qbTag);
            }

            if (StringUtils.hasText(qbHash)) {
               entity.setQbHash(qbHash);
            }

            if (StringUtils.hasText(qbTorrentName)) {
               entity.setQbTorrentName(qbTorrentName);
            }

            this.recordMapper.updateById(entity);
         }
      }
   }

   @Override
   public void updateRecord(MovieDownloadRecordEntity record) {
      if (record != null && record.getId() != null) {
         this.recordMapper.updateById(record);
      }
   }

   @Override
   public List<MovieDownloadRecordEntity> listPendingRecords() {
      QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      wrapper.in("status", new Object[]{"PENDING", "DOWNLOADING", "COMPLETED"});
      wrapper.orderByAsc("id");
      return this.recordMapper.selectList(wrapper);
   }

   @Override
   public String findLatestStatusByFingerprint(Long excludeId, Long subscribeId, Long tmdbId, String mediaType, String movieName, String title) {
      QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      if (excludeId != null) {
         wrapper.ne("id", excludeId);
      }

      boolean hasScope = false;
      if (subscribeId != null) {
         wrapper.eq("subscribe_id", subscribeId);
         hasScope = true;
      } else if (tmdbId != null) {
         wrapper.eq("tmdb_id", tmdbId);
         hasScope = true;
      } else if (StringUtils.hasText(movieName)) {
         wrapper.eq("movie_name", movieName.trim());
         hasScope = true;
      }

      if (!hasScope) {
         return null;
      } else {
         if (StringUtils.hasText(mediaType)) {
            wrapper.eq("media_type", mediaType.trim());
         }

         if (StringUtils.hasText(title)) {
            wrapper.eq("title", title.trim());
         }

         wrapper.orderByDesc("id");
         wrapper.last("limit 1");
         MovieDownloadRecordEntity latest = this.recordMapper.selectOne(wrapper);
         return latest == null ? null : latest.getStatus();
      }
   }

   @Override
   public List<MovieDownloadRecordWithDetailsResponse> listWithDetails() {
      QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      wrapper.orderByDesc("id");
      List<MovieDownloadRecordEntity> records = this.recordMapper.selectList(wrapper);
      return this.buildWithDetails(records);
   }

   @Override
   public Page<MovieDownloadRecordWithDetailsResponse> pageWithDetails(long current, long size, String keyword) {
      long safeCurrent = current <= 0L ? 1L : current;
      long safeSize = size <= 0L ? 10L : size;
      QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      String trimmedKeyword = this.trimToEmpty(keyword);
      if (StringUtils.hasText(trimmedKeyword)) {
         wrapper.and(match -> match.like("movie_name", trimmedKeyword).or().like("movie_year", trimmedKeyword));
      }

      wrapper.orderByDesc("id");
      Page<MovieDownloadRecordEntity> page = new Page<>(safeCurrent, safeSize);
      Page<MovieDownloadRecordEntity> recordPage = this.recordMapper.selectPage(page, wrapper);
      List<MovieDownloadRecordWithDetailsResponse> responses = this.buildWithDetails(recordPage.getRecords());
      Page<MovieDownloadRecordWithDetailsResponse> result = new Page<>();
      result.setTotal(recordPage.getTotal());
      result.setSize(recordPage.getSize());
      result.setCurrent(recordPage.getCurrent());
      result.setPages(recordPage.getPages());
      result.setRecords(responses);
      return result;
   }

   @Override
   public Page<MovieDownloadRecordWithDetailsResponse> pageWithDetailsBySubscribeId(Long subscribeId, long current, long size) {
      long safeCurrent = current <= 0L ? 1L : current;
      long safeSize = size <= 0L ? 10L : size;
      QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      wrapper.eq("subscribe_id", subscribeId);
      wrapper.orderByDesc("id");
      Page<MovieDownloadRecordEntity> page = new Page<>(safeCurrent, safeSize);
      Page<MovieDownloadRecordEntity> recordPage = this.recordMapper.selectPage(page, wrapper);
      List<MovieDownloadRecordWithDetailsResponse> responses = this.buildWithDetails(recordPage.getRecords());
      Page<MovieDownloadRecordWithDetailsResponse> result = new Page<>();
      result.setTotal(recordPage.getTotal());
      result.setSize(recordPage.getSize());
      result.setCurrent(recordPage.getCurrent());
      result.setPages(recordPage.getPages());
      result.setRecords(responses);
      return result;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public MovieActionResponse deleteRecord(List<Long> recordIds, boolean deleteScrapedFiles, boolean deleteSourceFiles) {
      if (CollectionUtils.isEmpty(recordIds)) {
         return MovieActionResponse.builder().success(false).message("recordId/recordIds 不能为空").build();
      } else {
         Set<Long> recordIdSet = this.normalizeIds(recordIds);
         if (CollectionUtils.isEmpty(recordIdSet)) {
            return MovieActionResponse.builder().success(false).message("下载记录不存在").build();
         } else {
            Map<Long, MovieDownloadRecordEntity> recordMap = this.loadRecordMap(recordIdSet);
            if (CollectionUtils.isEmpty(recordMap)) {
               return MovieActionResponse.builder().success(false).message("下载记录不存在").build();
            } else {
               int fileDeleteCount = 0;
               int recordDeleteCount = 0;

               for (MovieDownloadRecordEntity record : recordMap.values()) {
                  fileDeleteCount += this.deleteTargetFiles(record, deleteScrapedFiles, deleteSourceFiles);
               }

               List<Long> deleteIds = new ArrayList<>(recordMap.keySet());
               this.recordMapper.deleteBatchIds(deleteIds);
               recordDeleteCount += deleteIds.size();
               String message = String.format("删除完成：记录%d条，文件/目录%d个", recordDeleteCount, fileDeleteCount);
               return MovieActionResponse.builder().success(true).message(message).build();
            }
         }
      }
   }

   private Set<Long> normalizeIds(List<Long> ids) {
      return CollectionUtils.isEmpty(ids) ? null : ids.stream().filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
   }

   private Map<Long, MovieDownloadRecordEntity> loadRecordMap(Set<Long> targetIds) {
      if (CollectionUtils.isEmpty(targetIds)) {
         return Map.of();
      } else {
         List<MovieDownloadRecordEntity> records = this.recordMapper.selectBatchIds(targetIds);
         return CollectionUtils.isEmpty(records)
            ? Map.of()
            : records.stream()
               .filter(item -> item != null && item.getId() != null && item.getDelFlag() == 0)
               .collect(Collectors.toMap(MovieDownloadRecordEntity::getId, item -> (MovieDownloadRecordEntity)item));
      }
   }

   @Override
   public MovieDownloadRecordEntity findById(Long recordId) {
      if (recordId == null) {
         return null;
      } else {
         MovieDownloadRecordEntity entity = this.recordMapper.selectById(recordId);
         return entity != null && entity.getDelFlag() != 1 ? entity : null;
      }
   }

   @Override
   public int deleteByRecordIds(List<Long> recordIds) {
      if (CollectionUtils.isEmpty(recordIds)) {
         return 0;
      } else {
         List<Long> normalizedIds = recordIds.stream().filter(Objects::nonNull).distinct().toList();
         if (CollectionUtils.isEmpty(normalizedIds)) {
            return 0;
         } else {
            QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("del_flag", Integer.valueOf(0));
            wrapper.in("id", normalizedIds);
            List<MovieDownloadRecordEntity> records = this.recordMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(records)) {
               return 0;
            } else {
               List<Long> ids = records.stream().map(MovieDownloadRecordEntity::getId).filter(Objects::nonNull).toList();
               return this.deleteRecordsByIds(ids);
            }
         }
      }
   }

   @Override
   public int deleteByQbHashes(List<String> hashes, Long downloaderId) {
      if (hashes != null && !hashes.isEmpty()) {
         QueryWrapper<MovieDownloadRecordEntity> wrapper = new QueryWrapper<>();
         wrapper.eq("del_flag", Integer.valueOf(0));
         if (downloaderId != null) {
            wrapper.eq("downloader_id", downloaderId);
         }

         wrapper.in("qb_hash", hashes);
         List<MovieDownloadRecordEntity> records = this.recordMapper.selectList(wrapper);
         if (records.isEmpty()) {
            return 0;
         } else {
            List<Long> ids = records.stream().map(MovieDownloadRecordEntity::getId).collect(Collectors.toList());
            return this.deleteRecordsByIds(ids);
         }
      } else {
         return 0;
      }
   }

   private int deleteRecordsByIds(List<Long> ids) {
      if (CollectionUtils.isEmpty(ids)) {
         return 0;
      } else {
         this.recordMapper.deleteBatchIds(ids);
         return ids.size();
      }
   }

   private List<MovieDownloadRecordFileResponse> toFileResponses(MovieDownloadRecordEntity record) {
      return record != null && record.getId() != null
         ? List.of(
            MovieDownloadRecordFileResponse.builder()
               .id(record.getId())
               .recordId(record.getId())
               .sourceFilePath(record.getSourceFilePath())
               .linkFilePath(record.getLinkFilePath())
               .episodeCodes(record.getEpisodeCodes())
               .episodeSeqs(record.getEpisodeSeqs())
               .build()
         )
         : List.of();
   }

   private List<MovieDownloadRecordWithDetailsResponse> buildWithDetails(List<MovieDownloadRecordEntity> records) {
      if (CollectionUtils.isEmpty(records)) {
         return List.of();
      } else {
         Map<Long, String> downloaderNameMap = this.buildDownloaderNameMap(records);
         return records.stream()
            .map(
               record -> MovieDownloadRecordWithDetailsResponse.builder()
                     .id(record.getId())
                     .subscribeId(record.getSubscribeId())
                     .downloaderId(record.getDownloaderId())
                     .downloaderName(this.resolveDownloaderName(record.getDownloaderId(), downloaderNameMap))
                     .tmdbId(record.getTmdbId())
                     .mediaType(record.getMediaType())
                     .movieName(record.getMovieName())
                     .movieYear(record.getMovieYear())
                     .title(record.getTitle())
                     .posterUrl(record.getPosterUrl())
                     .coverUrl(record.getCoverUrl())
                     .qbDownloadPath(record.getQbDownloadPath())
                     .hardlinkPath(record.getHardlinkPath())
                     .qbTag(record.getQbTag())
                     .qbHash(record.getQbHash())
                     .qbTorrentName(record.getQbTorrentName())
                     .status(record.getStatus())
                     .statusName(this.resolveStatusName(record.getStatus()))
                     .sourceFilePath(record.getSourceFilePath())
                     .linkFilePath(record.getLinkFilePath())
                     .episodeCodes(record.getEpisodeCodes())
                     .episodeSeqs(record.getEpisodeSeqs())
                     .hardlinkMode(record.getHardlinkMode())
                     .hardlinkModeName(this.resolveHardlinkModeName(record.getHardlinkMode()))
                     .size(record.getSize())
                     .createDatetime(record.getCreateDatetime())
                     .updateDatetime(record.getUpdateDatetime())
                     .errorMessage(record.getErrorMessage())
                     .details(this.toFileResponses(record))
                     .build()
            )
            .collect(Collectors.toList());
      }
   }

   private String resolveHardlinkModeName(Integer mode) {
      if (mode == null) {
         return "硬链接";
      } else {
         return mode == 1 ? "硬链接" : "复制";
      }
   }

   private Map<Long, String> buildDownloaderNameMap(List<MovieDownloadRecordEntity> records) {
      if (CollectionUtils.isEmpty(records)) {
         return Map.of();
      } else {
         Set<Long> downloaderIds = records.stream()
            .map(MovieDownloadRecordEntity::getDownloaderId)
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(LinkedHashSet::new));
         if (downloaderIds.isEmpty()) {
            return Map.of();
         } else {
            QueryWrapper<MovieQbittorrentConfigEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("del_flag", Integer.valueOf(0));
            wrapper.in("id", downloaderIds);
            List<MovieQbittorrentConfigEntity> configs = this.qbittorrentConfigMapper.selectList(wrapper);
            return CollectionUtils.isEmpty(configs)
               ? Map.of()
               : configs.stream()
                  .filter(Objects::nonNull)
                  .collect(Collectors.toMap(MovieQbittorrentConfigEntity::getId, item -> this.trimToEmpty(item.getDownloaderName()), (left, right) -> left));
         }
      }
   }

   private String resolveDownloaderName(Long downloaderId, Map<Long, String> downloaderNameMap) {
      return downloaderId != null && downloaderNameMap != null ? this.trimToEmpty(downloaderNameMap.get(downloaderId)) : "";
   }

   private String resolveStatusName(String status) {
      if (!StringUtils.hasText(status)) {
         return "";
      } else {
         String normalized = status.trim().toUpperCase();
         if ("PENDING".equals(normalized)) {
            return "待处理";
         } else if ("DOWNLOADING".equals(normalized)) {
            return "下载中";
         } else if ("COMPLETED".equals(normalized)) {
            return "下载完成";
         } else if ("LINKED".equals(normalized)) {
            return "已整理";
         } else if ("LINK_FAILED".equals(normalized)) {
            return "整理失败";
         } else {
            return "FAILED".equals(normalized) ? "失败" : "未知";
         }
      }
   }

   private int deleteTargetFiles(MovieDownloadRecordEntity record, boolean deleteScrapedFiles, boolean deleteSourceFiles) {
      int deletedCount = 0;
      if (record != null) {
         if (deleteSourceFiles && this.deleteMediaPath(record.getSourceFilePath(), record.getQbDownloadPath(), true)) {
            deletedCount++;
         }

         if (deleteScrapedFiles && this.deleteMediaPath(record.getLinkFilePath(), record.getHardlinkPath(), true)) {
            deletedCount++;
         }
      }

      return deletedCount;
   }

   private boolean deleteMediaPath(String pathValue, String rootValue, boolean allowRecursiveDirDelete) {
      if (!StringUtils.hasText(pathValue)) {
         return false;
      } else {
         try {
            Path path = Paths.get(pathValue).toAbsolutePath().normalize();
            Path root = StringUtils.hasText(rootValue) ? Paths.get(rootValue).toAbsolutePath().normalize() : null;
            if (root != null && !path.startsWith(root)) {
               return false;
            } else if (!Files.exists(path)) {
               return false;
            } else {
               boolean deleted = false;
               if (!Files.isDirectory(path)) {
                  deleted = Files.deleteIfExists(path);
                  this.cleanupEmptyParents(path.getParent(), root);
                  return deleted;
               } else {
                  boolean canDeleteDir = allowRecursiveDirDelete || this.isBlurayFolder(path);
                  if (canDeleteDir && this.canDeleteTargetDir(path)) {
                     deleted = this.deleteDirectoryRecursive(path);
                  }

                  Path cleanupStart = deleted ? path.getParent() : path;
                  this.cleanupEmptyParents(cleanupStart, root);
                  return deleted;
               }
            }
         } catch (Exception var9) {
            return false;
         }
      }
   }

   private void cleanupEmptyParents(Path startDir, Path root) {
      if (startDir != null) {
         Path current = startDir;

         while (current != null && this.canCleanupDir(current, root)) {
            if (Files.exists(current) && Files.isDirectory(current)) {
               if (this.containsMediaFile(current) || !this.deleteDirectoryRecursive(current)) {
                  break;
               }

               current = current.getParent();
            } else {
               current = current.getParent();
            }
         }
      }
   }

   private boolean canCleanupDir(Path dir, Path root) {
      if (dir == null) {
         return false;
      } else {
         Path normalized = dir.toAbsolutePath().normalize();
         if (root != null) {
            Path normalizedRoot = root.toAbsolutePath().normalize();
            return !normalized.startsWith(normalizedRoot) ? false : !normalized.equals(normalizedRoot);
         } else {
            int depth = normalized.getNameCount();
            return depth > 2;
         }
      }
   }

   private boolean canDeleteTargetDir(Path dir) {
      int depth = dir.toAbsolutePath().normalize().getNameCount();
      return depth > 2;
   }

   private boolean containsMediaFile(Path dir) {
      try {
         boolean var3;
         try (Stream<Path> walk = Files.walk(dir)) {
            var3 = walk.anyMatch(path -> Files.isRegularFile(path) && this.isMediaFile(path));
         }

         return var3;
      } catch (IOException var7) {
         return true;
      }
   }

   private boolean isMediaFile(Path file) {
      String name = file.getFileName().toString().toLowerCase();

      for (String ext : MEDIA_EXTS) {
         if (name.endsWith(ext)) {
            return true;
         }
      }

      return false;
   }

   private boolean isBlurayFolder(Path dir) {
      return Files.isDirectory(dir.resolve("BDMV")) || Files.isDirectory(dir.resolve("CERTIFICATE"));
   }

   private boolean deleteDirectoryRecursive(Path path) {
      try (Stream<Path> walk = Files.walk(path)) {
         walk.sorted(Comparator.reverseOrder()).forEach(item -> {
            try {
               Files.deleteIfExists(item);
            } catch (IOException var2x) {
            }
         });
      } catch (IOException var7) {
         return false;
      }

      return !Files.exists(path);
   }

   private String trimToEmpty(String value) {
      return value == null ? "" : value.trim();
   }

   private String resolveTitle(MoviePtDownloadRequest request) {
      if (request == null) {
         return "";
      } else {
         String title = this.trimToEmpty(request.getTitle());
         if (!StringUtils.hasText(title)) {
            return "";
         } else {
            String mediaType = this.trimToEmpty(request.getMediaType());
            if ("tv".equalsIgnoreCase(mediaType)) {
               MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parse(title);
               return this.trimToEmpty(MovieEpisodeParser.buildSeasonEpisodeTag(meta));
            } else {
               return title;
            }
         }
      }
   }

   private String generateRandomTag() {
      StringBuilder builder = new StringBuilder(10);

      for (int i = 0; i < 10; i++) {
         int idx = RANDOM.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length());
         builder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(idx));
      }

      return builder.toString();
   }

   @Generated
   public MovieDownloadRecordServiceImpl(final MovieDownloadRecordMapper recordMapper, final MovieQbittorrentConfigMapper qbittorrentConfigMapper) {
      this.recordMapper = recordMapper;
      this.qbittorrentConfigMapper = qbittorrentConfigMapper;
   }
}
