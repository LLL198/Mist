package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowCancelRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowCheckRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowProgressBatchRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowProgressRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowQueryRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowSubscribeRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowSyncRequest;
import com.una.embyhub.model.dto.response.tmdbfollow.TmdbEpisodeResponse;
import com.una.embyhub.model.dto.response.tmdbfollow.TmdbFollowResponse;
import com.una.embyhub.model.dto.response.tmdbfollow.TmdbSeasonResponse;
import com.una.embyhub.model.entity.TmdbFollow;
import com.una.embyhub.model.entity.TmdbWatchProgress;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import java.util.List;

public interface TmdbFollowService extends IService<TmdbFollow> {
   TmdbFollowResponse subscribe(TmdbFollowSubscribeRequest request) throws TmdbException;

   List<TmdbFollowResponse> listFollows();

   Page<TmdbFollowResponse> pageFollows(MybatisPlusPage<TmdbFollowQueryRequest> page);

   TmdbFollowResponse updateProgress(TmdbFollowProgressRequest request);

   Boolean batchUpdateProgress(TmdbFollowProgressBatchRequest request);

   TmdbWatchProgress getProgress(Long followId, String watcherName);

   List<TmdbFollowResponse> sync(TmdbFollowSyncRequest request) throws TmdbException;

   void unsubscribe(TmdbFollowCancelRequest request);

   Boolean isSubscribed(TmdbFollowCheckRequest request);

   List<TmdbSeasonResponse> getSeasons(Long followId);

   List<TmdbEpisodeResponse> getEpisodes(Long followId, Integer seasonNumber);

   void syncTvDataInternal(TmdbFollow follow, TvSeriesDb tvSeriesDb) throws TmdbException;

   void syncMovieDataInternal(TmdbFollow follow, MovieDb movieDb);
}
