package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowProgressBatchRequest;
import com.una.embyhub.model.dto.request.tmdbfollow.TmdbFollowSubscribeRequest;

public interface TmdbFollowAsyncService {
   void asyncSyncFollowData(Long followId, TmdbFollowSubscribeRequest request);

   void asyncBatchUpdateProgress(TmdbFollowProgressBatchRequest request);
}
