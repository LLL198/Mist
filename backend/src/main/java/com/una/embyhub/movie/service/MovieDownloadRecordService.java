package com.una.embyhub.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieDownloadRecordWithDetailsResponse;
import com.una.embyhub.movie.model.MoviePtDownloadRequest;
import com.una.embyhub.movie.model.MovieScrapePathConfig;
import java.util.List;

public interface MovieDownloadRecordService {
   MovieDownloadRecordEntity createRecord(MoviePtDownloadRequest request, MovieScrapePathConfig config, String savePath, Long downloaderId);

   void updateStatus(Long id, String status);

   void updateStatusWithReason(Long id, String status, String reason);

   boolean updateStatusIfDifferent(Long id, String status);

   void updateQbInfo(Long id, String qbTag, String qbHash, String qbTorrentName);

   void updateRecord(MovieDownloadRecordEntity record);

   List<MovieDownloadRecordEntity> listPendingRecords();

   List<MovieDownloadRecordWithDetailsResponse> listWithDetails();

   Page<MovieDownloadRecordWithDetailsResponse> pageWithDetails(long current, long size, String keyword);

   Page<MovieDownloadRecordWithDetailsResponse> pageWithDetailsBySubscribeId(Long subscribeId, long current, long size);

   MovieActionResponse deleteRecord(List<Long> recordIds, boolean deleteScrapedFiles, boolean deleteSourceFiles);

   MovieDownloadRecordEntity findById(Long recordId);

   int deleteByRecordIds(List<Long> recordIds);

   int deleteByQbHashes(List<String> hashes, Long downloaderId);

   String findLatestStatusByFingerprint(Long excludeId, Long subscribeId, Long tmdbId, String mediaType, String movieName, String title);
}
