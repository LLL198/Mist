package com.una.embyhub.movie.event;

import com.una.embyhub.movie.entity.MoviePtSubscribeEntity;
import com.una.embyhub.movie.model.MoviePtSearchResult;
import java.util.List;

public record MoviePtSubscribeDownloadNotifyEvent(
   MoviePtSubscribeEntity subscribe, List<MoviePtSearchResult> notifyResults, String summary, List<Integer> downloadedEpisodes
) {
}
