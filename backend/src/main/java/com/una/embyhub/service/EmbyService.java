package com.una.embyhub.service;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.model.dto.request.emby.GetItemsRequest;
import com.una.embyhub.model.dto.request.emby.PublisherSearchRequest;
import com.una.embyhub.model.dto.response.emby.EmbySettingsResponse;
import com.una.embyhub.model.dto.response.emby.EmbyStudioPresetResponse;
import com.una.embyhub.model.dto.response.emby.GetEmbyUrlResponse;
import com.una.embyhub.model.dto.response.emby.GetEpisodesByIdResponse;
import com.una.embyhub.model.dto.response.emby.NowPlayingGroupedResponse;
import com.una.embyhub.model.dto.response.emby.PublisherSearchResponse;
import com.una.embyhub.model.dto.response.emby.QueryResultBaseItemResponse;
import com.una.embyhub.model.dto.response.emby.SessionSessionInfoResponse;
import com.una.embyhub.model.dto.response.emby.StatsResponse;
import embyclient.ApiException;
import embyclient.model.QueryResultBaseItemDto;
import java.util.List;

public interface EmbyService {
   QueryResultBaseItemResponse getItems(GetItemsRequest getItemsRequest) throws ApiException;

   StatsResponse stats();

   void notifier(JSONObject data);

   JSONObject clientFilterWebhook(JSONObject data);

   QueryResultBaseItemDto getShowsByIdSeasons(String tvId) throws ApiException;

   GetEpisodesByIdResponse getEpisodesById(String tvId) throws ApiException;

   GetEpisodesByIdResponse getEpisodesById(String tvId, Long embyInfoId) throws ApiException;

   GetEmbyUrlResponse getEmbyUrl(String itemId, String serverId);

   List<SessionSessionInfoResponse> getNowPlaying() throws ApiException;

   List<NowPlayingGroupedResponse> getNowPlayingGrouped() throws ApiException;

   EmbySettingsResponse getEmbySettings();

   JSONObject getShowSeasons(String showId);

   String getEmbySettingsUrl();

   PublisherSearchResponse searchByPublisher(PublisherSearchRequest request) throws ApiException;

   List<EmbyStudioPresetResponse> getStudioPresets(Long embyInfoId);
}
