package com.una.embyhub.movie.service;

import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieQbittorrentConfig;
import com.una.embyhub.movie.model.MovieQbittorrentConfigRequest;
import com.una.embyhub.movie.model.MovieQbittorrentTorrent;
import java.util.List;

public interface MovieQbittorrentService {
   MovieQbittorrentConfig getConfig();

   MovieQbittorrentConfig saveConfig(MovieQbittorrentConfigRequest request);

   MovieQbittorrentConfig createConfig(MovieQbittorrentConfigRequest request);

   MovieQbittorrentConfig updateConfig(Long id, MovieQbittorrentConfigRequest request);

   MovieQbittorrentConfig getConfigById(Long id);

   List<MovieQbittorrentConfig> listConfigs();

   void deleteConfig(Long id);

   List<MovieQbittorrentTorrent> getQueue(boolean filterActive, Long downloaderId);

   MovieActionResponse addMagnet(String magnet, String savePath);

   MovieActionResponse addMagnet(String magnet, String savePath, String tag);

   MovieActionResponse addTorrentFile(byte[] torrentContent, String filename, String savePath);

   MovieActionResponse addTorrentFile(byte[] torrentContent, String filename, String savePath, String tag);

   MovieActionResponse addMagnet(Long configId, String magnet, String savePath, String tag);

   MovieActionResponse addTorrentFile(Long configId, byte[] torrentContent, String filename, String savePath, String tag);

   List<MovieQbittorrentTorrent> getQueueByTag(String tag);

   List<MovieQbittorrentTorrent> getQueueByTag(Long configId, String tag);

   List<MovieQbittorrentTorrent> getQueueByHash(String hash);

   List<MovieQbittorrentTorrent> getQueueByHash(Long configId, String hash);

   boolean removeTags(String hash, String tag);

   boolean removeTags(Long configId, String hash, String tag);

   boolean deleteTags(String tag);

   boolean deleteTags(Long configId, String tag);

   MovieActionResponse pause(String hashes, Long downloaderId);

   MovieActionResponse resume(String hashes, Long downloaderId);

   MovieActionResponse delete(Long recordId, boolean deleteFiles);
}
