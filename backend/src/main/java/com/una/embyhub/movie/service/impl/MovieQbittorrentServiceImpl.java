package com.una.embyhub.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.movie.client.MovieQbittorrentClient;
import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;
import com.una.embyhub.movie.entity.MoviePtSiteEntity;
import com.una.embyhub.movie.entity.MovieQbittorrentConfigEntity;
import com.una.embyhub.movie.mapper.MoviePtSiteMapper;
import com.una.embyhub.movie.mapper.MovieQbittorrentConfigMapper;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieQbittorrentConfig;
import com.una.embyhub.movie.model.MovieQbittorrentConfigRequest;
import com.una.embyhub.movie.model.MovieQbittorrentTorrent;
import com.una.embyhub.movie.service.MovieDownloadRecordService;
import com.una.embyhub.movie.service.MovieQbittorrentService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MovieQbittorrentServiceImpl implements MovieQbittorrentService {
   private static final Set<String> ACTIVE_STATES = Set.of("downloading", "stalledDL", "pausedDL", "queuedDL", "checkingDL", "metaDL", "allocating");
   private final MovieQbittorrentConfigMapper configMapper;
   private final MovieQbittorrentClient qbittorrentClient;
   private final MovieDownloadRecordService downloadRecordService;
   private final MoviePtSiteMapper moviePtSiteMapper;

   @Override
   public MovieQbittorrentConfig getConfig() {
      MovieQbittorrentConfigEntity entity = this.getLatestConfig();
      return entity == null ? MovieQbittorrentConfig.builder().host("").username("").password("").downloaderName("").build() : this.toModel(entity);
   }

   @Override
   public MovieQbittorrentConfig saveConfig(MovieQbittorrentConfigRequest request) {
      MovieQbittorrentConfigEntity entity = this.getLatestConfig();
      return entity == null ? this.createConfig(request) : this.updateEntity(entity.getId(), request);
   }

   @Override
   public MovieQbittorrentConfig createConfig(MovieQbittorrentConfigRequest request) {
      MovieQbittorrentConfigEntity entity = new MovieQbittorrentConfigEntity();
      return this.persistEntity(entity, request);
   }

   @Override
   public MovieQbittorrentConfig updateConfig(Long id, MovieQbittorrentConfigRequest request) {
      return this.updateEntity(id, request);
   }

   @Override
   public MovieQbittorrentConfig getConfigById(Long id) {
      MovieQbittorrentConfigEntity entity = this.configMapper.selectById(id);
      if (entity != null && entity.getDelFlag() != 1) {
         return this.toModel(entity);
      } else {
         throw new BizException("配置不存在");
      }
   }

   @Override
   public List<MovieQbittorrentConfig> listConfigs() {
      QueryWrapper<MovieQbittorrentConfigEntity> wrapper = new QueryWrapper<>();
      wrapper.orderByDesc("id");
      List<MovieQbittorrentConfigEntity> entities = this.configMapper.selectList(wrapper);
      return entities.stream().map(this::toModel).toList();
   }

   @Override
   public void deleteConfig(Long id) {
      this.configMapper.deleteById(id);
   }

   private MovieQbittorrentConfig updateEntity(Long id, MovieQbittorrentConfigRequest request) {
      MovieQbittorrentConfigEntity entity = this.configMapper.selectById(id);
      if (entity != null && entity.getDelFlag() != 1) {
         return this.persistEntity(entity, request);
      } else {
         throw new BizException("配置不存在");
      }
   }

   private MovieQbittorrentConfig persistEntity(MovieQbittorrentConfigEntity entity, MovieQbittorrentConfigRequest request) {
      MovieQbittorrentConfig config = MovieQbittorrentConfig.builder()
         .host(this.trimToEmpty(request.getHost()))
         .username(this.trimToEmpty(request.getUsername()))
         .password(this.trimToEmpty(request.getPassword()))
         .downloaderName(this.trimToEmpty(request.getDownloaderName()))
         .build();
      entity.setHost(config.getHost());
      entity.setUsername(config.getUsername());
      entity.setPassword(config.getPassword());
      entity.setDownloaderName(config.getDownloaderName());
      if (entity.getId() == null) {
         this.configMapper.insert(entity);
      } else {
         this.configMapper.updateById(entity);
      }

      return this.toModel(entity);
   }

   @Override
   public List<MovieQbittorrentTorrent> getQueue(boolean filterActive, Long downloaderId) {
      MovieQbittorrentConfig config = downloaderId != null ? this.getConfigById(downloaderId) : this.getConfig();
      if (config != null && StringUtils.hasText(config.getHost())) {
         List<MovieDownloadRecordEntity> records = this.downloadRecordService.listPendingRecords();
         if (records != null && !records.isEmpty()) {
            Set<String> hashSet = new LinkedHashSet<>();
            List<String> hashes = new ArrayList<>();
            Set<String> tagSet = new LinkedHashSet<>();

            for (MovieDownloadRecordEntity record : records) {
               String normalizedHash = this.normalizeHash(record.getQbHash());
               if (StringUtils.hasText(normalizedHash)) {
                  if (hashSet.add(normalizedHash)) {
                     hashes.add(normalizedHash);
                  }
               } else {
                  String tag = this.trimToEmpty(record.getQbTag());
                  if (StringUtils.hasText(tag)) {
                     tagSet.add(tag);
                  }
               }
            }

            Set<Long> siteIds = records.stream().map(MovieDownloadRecordEntity::getSiteId).filter(id -> id != null).collect(Collectors.toSet());
            Map<Long, String> siteNameMap = new HashMap<>();
            if (!siteIds.isEmpty()) {
               QueryWrapper<MoviePtSiteEntity> siteWrapper = new QueryWrapper<>();
               siteWrapper.in("id", siteIds);
               List<MoviePtSiteEntity> sites = this.moviePtSiteMapper.selectList(siteWrapper);
               if (sites != null) {
                  for (MoviePtSiteEntity site : sites) {
                     siteNameMap.put(site.getId(), site.getName());
                  }
               }
            }

            Map<String, MovieQbittorrentTorrent> torrentsByHash = new HashMap<>();
            if (!hashes.isEmpty()) {
               for (MovieQbittorrentTorrent torrent : this.qbittorrentClient.fetchQueueByHashes(config, hashes)) {
                  String hash = this.normalizeHash(torrent.getHash());
                  if (StringUtils.hasText(hash)) {
                     torrentsByHash.put(hash, torrent);
                  }
               }
            }

            Map<String, List<MovieQbittorrentTorrent>> torrentsByTag = new HashMap<>();
            if (!tagSet.isEmpty()) {
               for (String tag : tagSet) {
                  List<MovieQbittorrentTorrent> torrents = this.qbittorrentClient.fetchQueueByTag(config, tag);
                  if (torrents != null && !torrents.isEmpty()) {
                     torrentsByTag.put(tag, torrents);
                  }
               }
            }

            List<MovieQbittorrentTorrent> result = new ArrayList<>();

            for (MovieDownloadRecordEntity recordx : records) {
               MovieQbittorrentTorrent matched = null;
               String normalizedHash = this.normalizeHash(recordx.getQbHash());
               if (StringUtils.hasText(normalizedHash)) {
                  matched = torrentsByHash.get(normalizedHash);
               }

               String recordTag = this.trimToEmpty(recordx.getQbTag());
               if (matched == null && StringUtils.hasText(recordTag)) {
                  List<MovieQbittorrentTorrent> tagTorrents = torrentsByTag.get(recordTag);
                  matched = this.findMatchingTorrent(recordx, tagTorrents);
               }

               if (matched != null && (!filterActive || this.isActive(matched))) {
                  MovieQbittorrentTorrent payload = this.cloneTorrent(matched);
                  this.enrichRecordInfo(payload, recordx, siteNameMap);
                  result.add(payload);
               }
            }

            return result;
         } else {
            return List.of();
         }
      } else {
         throw new BizException("请先配置 qBittorrent 连接信息");
      }
   }

   @Override
   public MovieActionResponse addMagnet(String magnet, String savePath) {
      MovieQbittorrentConfig config = this.getConfig();
      return this.qbittorrentClient.addMagnet(config, magnet, savePath);
   }

   @Override
   public MovieActionResponse addMagnet(String magnet, String savePath, String tag) {
      MovieQbittorrentConfig config = this.getConfig();
      return this.qbittorrentClient.addMagnet(config, magnet, savePath, tag);
   }

   @Override
   public MovieActionResponse addTorrentFile(byte[] torrentContent, String filename, String savePath) {
      MovieQbittorrentConfig config = this.getConfig();
      return this.qbittorrentClient.addTorrentFile(config, torrentContent, filename, savePath);
   }

   @Override
   public MovieActionResponse addTorrentFile(byte[] torrentContent, String filename, String savePath, String tag) {
      MovieQbittorrentConfig config = this.getConfig();
      return this.qbittorrentClient.addTorrentFile(config, torrentContent, filename, savePath, tag);
   }

   @Override
   public List<MovieQbittorrentTorrent> getQueueByTag(String tag) {
      MovieQbittorrentConfig config = this.getConfig();
      if (config != null && StringUtils.hasText(config.getHost())) {
         return this.qbittorrentClient.fetchQueueByTag(config, tag);
      } else {
         throw new BizException("请先配置 qBittorrent 连接信息");
      }
   }

   @Override
   public List<MovieQbittorrentTorrent> getQueueByTag(Long configId, String tag) {
      MovieQbittorrentConfig config = this.getConfigById(configId);
      return this.qbittorrentClient.fetchQueueByTag(config, tag);
   }

   @Override
   public List<MovieQbittorrentTorrent> getQueueByHash(String hash) {
      if (!StringUtils.hasText(hash)) {
         return List.of();
      } else {
         MovieQbittorrentConfig config = this.getConfig();
         if (config != null && StringUtils.hasText(config.getHost())) {
            return this.qbittorrentClient.fetchQueueByHashes(config, List.of(hash));
         } else {
            throw new BizException("请先配置 qBittorrent 连接信息");
         }
      }
   }

   @Override
   public List<MovieQbittorrentTorrent> getQueueByHash(Long configId, String hash) {
      if (!StringUtils.hasText(hash)) {
         return List.of();
      } else {
         MovieQbittorrentConfig config = this.getConfigById(configId);
         return this.qbittorrentClient.fetchQueueByHashes(config, List.of(hash));
      }
   }

   @Override
   public boolean removeTags(String hash, String tag) {
      MovieQbittorrentConfig config = this.getConfig();
      if (config != null && StringUtils.hasText(config.getHost())) {
         return this.qbittorrentClient.removeTags(config, hash, tag);
      } else {
         throw new BizException("请先配置 qBittorrent 连接信息");
      }
   }

   @Override
   public boolean removeTags(Long configId, String hash, String tag) {
      MovieQbittorrentConfig config = this.getConfigById(configId);
      return this.qbittorrentClient.removeTags(config, hash, tag);
   }

   @Override
   public boolean deleteTags(String tag) {
      MovieQbittorrentConfig config = this.getConfig();
      if (config != null && StringUtils.hasText(config.getHost())) {
         return this.qbittorrentClient.deleteTags(config, tag);
      } else {
         throw new BizException("请先配置 qBittorrent 连接信息");
      }
   }

   @Override
   public boolean deleteTags(Long configId, String tag) {
      MovieQbittorrentConfig config = this.getConfigById(configId);
      return this.qbittorrentClient.deleteTags(config, tag);
   }

   @Override
   public MovieActionResponse pause(String hashes, Long downloaderId) {
      List<String> hashList = this.splitHashes(hashes);
      if (hashList.isEmpty()) {
         return MovieActionResponse.builder().success(false).message("hashes 不能为空").build();
      } else {
         MovieQbittorrentConfig config = this.getConfigById(downloaderId);
         boolean success = this.qbittorrentClient.pauseTorrents(config, this.joinHashes(hashList));
         return MovieActionResponse.builder().success(success).message(success ? "已暂停任务" : "暂停任务失败").build();
      }
   }

   @Override
   public MovieActionResponse resume(String hashes, Long downloaderId) {
      List<String> hashList = this.splitHashes(hashes);
      if (hashList.isEmpty()) {
         return MovieActionResponse.builder().success(false).message("hashes 不能为空").build();
      } else {
         MovieQbittorrentConfig config = this.getConfigById(downloaderId);
         boolean success = this.qbittorrentClient.resumeTorrents(config, this.joinHashes(hashList));
         return MovieActionResponse.builder().success(success).message(success ? "已开始任务" : "开始任务失败").build();
      }
   }

   @Override
   public MovieActionResponse delete(Long recordId, boolean deleteFiles) {
      if (recordId == null) {
         return MovieActionResponse.builder().success(false).message("recordId 不能为空").build();
      } else {
         MovieDownloadRecordEntity record = this.downloadRecordService.findById(recordId);
         if (record != null && record.getDelFlag() != 1) {
            Long recordDownloaderId = record.getDownloaderId();
            String targetHash = this.resolveTargetHash(record, recordDownloaderId);
            if (!StringUtils.hasText(targetHash)) {
               return MovieActionResponse.builder().success(false).message("下载记录未找到可删除任务").build();
            } else {
               MovieQbittorrentConfig config = recordDownloaderId != null ? this.getConfigById(recordDownloaderId) : this.getConfig();
               boolean success = this.qbittorrentClient.deleteTorrents(config, targetHash, deleteFiles);
               if (!success) {
                  return MovieActionResponse.builder().success(false).message("删除任务失败").build();
               } else {
                  int deleted = this.downloadRecordService.deleteByRecordIds(List.of(recordId));
                  String message = deleted > 0 ? "已删除任务并清理记录" : "已删除任务";
                  return MovieActionResponse.builder().success(true).message(message).build();
               }
            }
         } else {
            return MovieActionResponse.builder().success(false).message("下载记录不存在").build();
         }
      }
   }

   @Override
   public MovieActionResponse addMagnet(Long configId, String magnet, String savePath, String tag) {
      MovieQbittorrentConfig config = this.getConfigById(configId);
      return this.qbittorrentClient.addMagnet(config, magnet, savePath, tag);
   }

   @Override
   public MovieActionResponse addTorrentFile(Long configId, byte[] torrentContent, String filename, String savePath, String tag) {
      MovieQbittorrentConfig config = this.getConfigById(configId);
      return this.qbittorrentClient.addTorrentFile(config, torrentContent, filename, savePath, tag);
   }

   private String trimToEmpty(String value) {
      return value == null ? "" : value.trim();
   }

   private String normalizeHash(String hash) {
      return !StringUtils.hasText(hash) ? "" : hash.trim().toLowerCase(Locale.ROOT);
   }

   private MovieQbittorrentTorrent cloneTorrent(MovieQbittorrentTorrent torrent) {
      return torrent == null
         ? null
         : MovieQbittorrentTorrent.builder()
            .name(torrent.getName())
            .hash(torrent.getHash())
            .savePath(torrent.getSavePath())
            .contentPath(torrent.getContentPath())
            .tags(torrent.getTags())
            .progress(torrent.getProgress())
            .state(torrent.getState())
            .dlspeed(torrent.getDlspeed())
            .upspeed(torrent.getUpspeed())
            .size(torrent.getSize())
            .eta(torrent.getEta())
            .recordId(torrent.getRecordId())
            .siteName(torrent.getSiteName())
            .build();
   }

   private void enrichRecordInfo(MovieQbittorrentTorrent torrent, MovieDownloadRecordEntity record, Map<Long, String> siteNameMap) {
      if (torrent != null && record != null) {
         torrent.setRecordId(record.getId());
         String movieName = this.trimToEmpty(record.getMovieName());
         String movieYear = this.trimToEmpty(record.getMovieYear());
         String displayName = this.buildMovieNameWithYear(movieName, movieYear);
         if (!StringUtils.hasText(displayName)) {
            displayName = this.trimToEmpty(torrent.getName());
         }

         torrent.setMovieName(movieName);
         torrent.setMovieYear(movieYear);
         String title = this.trimToEmpty(record.getTitle());
         if (StringUtils.hasText(title)) {
            torrent.setTitle(title);
         }

         torrent.setMovieNameWithYear(displayName);
         torrent.setPosterUrl(this.trimToEmpty(record.getPosterUrl()));
         torrent.setMediaType(this.trimToEmpty(record.getMediaType()));
         if (record.getSiteId() != null && siteNameMap != null) {
            torrent.setSiteName(siteNameMap.get(record.getSiteId()));
         }
      }
   }

   private String resolveTargetHash(MovieDownloadRecordEntity record, Long downloaderId) {
      if (record == null) {
         return "";
      } else {
         String hash = this.normalizeHash(record.getQbHash());
         if (StringUtils.hasText(hash)) {
            return hash;
         } else {
            String tag = this.trimToEmpty(record.getQbTag());
            if (!StringUtils.hasText(tag)) {
               return "";
            } else {
               List<MovieQbittorrentTorrent> torrents = downloaderId != null ? this.getQueueByTag(downloaderId, tag) : this.getQueueByTag(tag);
               MovieQbittorrentTorrent matched = this.findMatchingTorrent(record, torrents);
               return matched == null ? "" : this.normalizeHash(matched.getHash());
            }
         }
      }
   }

   private String buildMovieNameWithYear(String movieName, String movieYear) {
      if (!StringUtils.hasText(movieName)) {
         return "";
      } else {
         return StringUtils.hasText(movieYear) ? movieName + " (" + movieYear + ")" : movieName;
      }
   }

   private boolean isActive(MovieQbittorrentTorrent torrent) {
      if (torrent == null) {
         return false;
      } else {
         String state = torrent.getState();
         Double progress = torrent.getProgress();
         return StringUtils.hasText(state) && ACTIVE_STATES.contains(state) ? true : this.isPausedIncomplete(state, progress);
      }
   }

   private boolean isPausedIncomplete(String state, Double progress) {
      if (StringUtils.hasText(state) && state.startsWith("stoppedDL")) {
         double value = progress == null ? 0.0 : progress;
         return value < 1.0;
      } else {
         return false;
      }
   }

   private MovieQbittorrentTorrent findMatchingTorrent(MovieDownloadRecordEntity record, List<MovieQbittorrentTorrent> torrents) {
      if (torrents != null && !torrents.isEmpty()) {
         if (torrents.size() == 1) {
            return torrents.get(0);
         } else {
            if (record != null && StringUtils.hasText(record.getQbHash())) {
               String targetHash = this.normalizeHash(record.getQbHash());

               for (MovieQbittorrentTorrent torrent : torrents) {
                  if (targetHash.equals(this.normalizeHash(torrent.getHash()))) {
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
               String name = record.getMovieName().toLowerCase(Locale.ROOT);

               for (MovieQbittorrentTorrent torrentxxx : torrents) {
                  if (StringUtils.hasText(torrentxxx.getName()) && torrentxxx.getName().toLowerCase(Locale.ROOT).contains(name)) {
                     return torrentxxx;
                  }
               }
            }

            return null;
         }
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

   private MovieQbittorrentConfigEntity getLatestConfig() {
      QueryWrapper<MovieQbittorrentConfigEntity> wrapper = new QueryWrapper<>();
      wrapper.orderByDesc("id");
      wrapper.last("LIMIT 1");
      return this.configMapper.selectOne(wrapper);
   }

   private List<String> splitHashes(String hashes) {
      if (!StringUtils.hasText(hashes)) {
         return List.of();
      } else {
         String[] parts = hashes.split("[,|\\s]+");
         return Arrays.stream(parts)
            .map(String::trim)
            .filter(StringUtils::hasText)
            .map(value -> value.toLowerCase(Locale.ROOT))
            .distinct()
            .collect(Collectors.toList());
      }
   }

   private String joinHashes(List<String> hashes) {
      return hashes != null && !hashes.isEmpty() ? String.join("|", hashes) : "";
   }

   private MovieQbittorrentConfig toModel(MovieQbittorrentConfigEntity entity) {
      return MovieQbittorrentConfig.builder()
         .host(entity.getHost())
         .username(entity.getUsername())
         .password(entity.getPassword())
         .downloaderName(entity.getDownloaderName())
         .id(entity.getId())
         .build();
   }

   @Generated
   public MovieQbittorrentServiceImpl(
      final MovieQbittorrentConfigMapper configMapper,
      final MovieQbittorrentClient qbittorrentClient,
      final MovieDownloadRecordService downloadRecordService,
      final MoviePtSiteMapper moviePtSiteMapper
   ) {
      this.configMapper = configMapper;
      this.qbittorrentClient = qbittorrentClient;
      this.downloadRecordService = downloadRecordService;
      this.moviePtSiteMapper = moviePtSiteMapper;
   }
}
