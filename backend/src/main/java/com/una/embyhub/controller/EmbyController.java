package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import com.una.embyhub.model.dto.response.emby.StatsResponse;
import com.una.embyhub.service.EmbyService;
import embyclient.ApiException;
import embyclient.model.QueryResultBaseItemDto;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"emby"})
public class EmbyController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyController.class);
   @Autowired
   private EmbyService embyService;

   @PostMapping({"getItems"})
   public QueryResultBaseItemResponse getItems(@RequestBody GetItemsRequest getItemsRequest) throws ApiException {
      return this.embyService.getItems(getItemsRequest);
   }

   @PostMapping({"stats"})
   public StatsResponse stats() {
      return this.embyService.stats();
   }

   @PostMapping({"notifier"})
   public void notifier(@RequestBody JSONObject data) {
      this.embyService.notifier(data);
   }

   @PostMapping({"webhook/client-filter"})
   public JSONObject clientFilterWebhook(HttpServletRequest request) throws IOException {
      return this.embyService.clientFilterWebhook(this.parseWebhookPayload(request));
   }

   private JSONObject parseWebhookPayload(HttpServletRequest request) throws IOException {
      String formData = request.getParameter("data");
      if (StringUtils.hasText(formData)) {
         return JSONObject.parseObject(formData);
      } else {
         String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
         return StringUtils.hasText(body) ? JSONObject.parseObject(body) : new JSONObject();
      }
   }

   @PostMapping({"getShowsByIdSeasons"})
   public QueryResultBaseItemDto getShowsByIdSeasons(@RequestParam String tvId) throws ApiException {
      return this.embyService.getShowsByIdSeasons(tvId);
   }

   @PostMapping({"getEpisodesById"})
   public GetEpisodesByIdResponse getEpisodesById(@RequestParam String tvId, @RequestParam(required = false) Long embyInfoId) throws ApiException {
      return this.embyService.getEpisodesById(tvId, embyInfoId);
   }

   @PostMapping({"getEmbyUrl"})
   public GetEmbyUrlResponse getEmbyUrl(@RequestParam String itemId, @RequestParam String serverId) {
      return this.embyService.getEmbyUrl(itemId, serverId);
   }

   @PostMapping({"getNowPlaying"})
   @SaCheckPermission({"admin"})
   public List<NowPlayingGroupedResponse> getNowPlaying() throws ApiException {
      return this.embyService.getNowPlayingGrouped();
   }

   @PostMapping({"getEmbySettings"})
   @SaCheckPermission({"admin"})
   public EmbySettingsResponse getEmbySettings() {
      return this.embyService.getEmbySettings();
   }

   @GetMapping({"/shows/{showId}/seasons"})
   public JSONObject getSeasons(@PathVariable String showId) {
      return this.embyService.getShowSeasons(showId);
   }

   @PostMapping({"getEmbySettingsUrl"})
   public String getEmbySettingsUrl() {
      return this.embyService.getEmbySettingsUrl();
   }

   @PostMapping({"publisher/search"})
   public PublisherSearchResponse searchByPublisher(@RequestBody PublisherSearchRequest request) throws ApiException {
      return this.embyService.searchByPublisher(request);
   }

   @GetMapping({"studios/presets"})
   public List<EmbyStudioPresetResponse> getStudioPresets(@RequestParam(value = "embyInfoId",required = false) Long embyInfoId) {
      return this.embyService.getStudioPresets(embyInfoId);
   }
}
