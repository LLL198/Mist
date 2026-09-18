package com.una.embyhub.controller;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.model.dto.request.telegram.HeroListRequest;
import com.una.embyhub.model.dto.request.telegram.MediaListRequest;
import com.una.embyhub.model.dto.request.telegram.MediaSearchRequest;
import com.una.embyhub.service.TelegramMediaService;
import lombok.Generated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"media"})
public class TelegramMediaController {
   private final TelegramMediaService mediaService;

   @GetMapping({"hero"})
   public JSONObject getHeroList(HeroListRequest request) {
      return this.mediaService.getHeroList(request.getTimeWindow(), request.getPage());
   }

   @GetMapping({"list"})
   public JSONObject getList(MediaListRequest request) {
      return this.mediaService.getList(request.getMediaType(), request.getCategory(), request.getPage() != null ? request.getPage() : 1);
   }

   @GetMapping({"search"})
   public JSONObject search(MediaSearchRequest request) {
      return this.mediaService.search(request.getMediaType(), request.getQuery(), request.getPage() != null ? request.getPage() : 1);
   }

   @GetMapping({"{mediaType}/{id}"})
   public JSONObject getDetails(@PathVariable String mediaType, @PathVariable Long id) {
      return this.mediaService.getDetails(mediaType, id);
   }

   @GetMapping({"tv/{tvId}/season/{seasonNumber}"})
   public JSONObject getSeasonDetails(@PathVariable Long tvId, @PathVariable int seasonNumber) {
      return this.mediaService.getSeasonDetails(tvId, seasonNumber);
   }

   @Generated
   public TelegramMediaController(final TelegramMediaService mediaService) {
      this.mediaService = mediaService;
   }
}
