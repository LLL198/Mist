package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.una.embyhub.model.entity.TmdbWatchProgress;
import com.una.embyhub.service.TmdbFollowService;
import info.movito.themoviedbapi.tools.TmdbException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"tmdb/track"})
public class TmdbTrackController {
   @Autowired
   private TmdbFollowService tmdbFollowService;

   @PostMapping({"subscribe"})
   @SaCheckPermission({"admin"})
   public TmdbFollowResponse subscribe(@RequestBody @Valid TmdbFollowSubscribeRequest request) throws TmdbException {
      return this.tmdbFollowService.subscribe(request);
   }

   @PostMapping({"unsubscribe"})
   @SaCheckPermission({"admin"})
   public Boolean unsubscribe(@RequestBody TmdbFollowCancelRequest request) {
      this.tmdbFollowService.unsubscribe(request);
      return Boolean.TRUE;
   }

   @PostMapping({"list"})
   @SaCheckPermission({"admin"})
   public Page<TmdbFollowResponse> list(@RequestBody MybatisPlusPage<TmdbFollowQueryRequest> page) {
      return this.tmdbFollowService.pageFollows(page);
   }

   @GetMapping({"seasons"})
   @SaCheckPermission({"admin"})
   public List<TmdbSeasonResponse> getSeasons(@RequestParam Long followId) {
      return this.tmdbFollowService.getSeasons(followId);
   }

   @GetMapping({"episodes"})
   @SaCheckPermission({"admin"})
   public List<TmdbEpisodeResponse> getEpisodes(@RequestParam Long followId, @RequestParam Integer seasonNumber) {
      return this.tmdbFollowService.getEpisodes(followId, seasonNumber);
   }

   @PostMapping({"progress"})
   @SaCheckPermission({"admin"})
   public TmdbFollowResponse progress(@RequestBody @Valid TmdbFollowProgressRequest request) {
      return this.tmdbFollowService.updateProgress(request);
   }

   @PostMapping({"progress/batch"})
   @SaCheckPermission({"admin"})
   public Boolean batchUpdateProgress(@RequestBody @Valid TmdbFollowProgressBatchRequest request) {
      return this.tmdbFollowService.batchUpdateProgress(request);
   }

   @GetMapping({"progress"})
   @SaCheckPermission({"admin"})
   public TmdbWatchProgress getProgress(@RequestParam Long followId, @RequestParam(required = false) String watcherName) {
      return this.tmdbFollowService.getProgress(followId, watcherName);
   }

   @PostMapping({"sync"})
   @SaCheckPermission({"admin"})
   public List<TmdbFollowResponse> sync(@RequestBody(required = false) TmdbFollowSyncRequest request) throws TmdbException {
      if (request == null) {
         request = new TmdbFollowSyncRequest();
      }

      return this.tmdbFollowService.sync(request);
   }

   @PostMapping({"isSubscribed"})
   @SaCheckPermission({"admin"})
   public Boolean isSubscribed(@RequestBody @Valid TmdbFollowCheckRequest request) {
      return this.tmdbFollowService.isSubscribed(request);
   }
}
