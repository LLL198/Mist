package com.una.embyhub.movie.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.movie.model.MovieDownloadRecordWithDetailsResponse;
import com.una.embyhub.movie.model.MoviePtSubscribe;
import com.una.embyhub.movie.model.MoviePtSubscribeRequest;
import com.una.embyhub.movie.model.MoviePtSubscribeSearchProgressEvent;
import com.una.embyhub.movie.model.MoviePtSubscribeSearchResponse;
import com.una.embyhub.movie.model.MovieSubscribeQualityConfig;
import com.una.embyhub.movie.service.MoviePtSubscribeService;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping({"movie/pt-subscribe"})
@Validated
@SaCheckPermission({"admin"})
public class MoviePtSubscribeController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtSubscribeController.class);
   private static final String SUBSCRIBE_SEARCH_SSE_EVENT = "pt-subscribe-search-progress";
   private static final long SSE_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(10L);
   private static final AtomicInteger SSE_THREAD_ID = new AtomicInteger(1);
   private final MoviePtSubscribeService moviePtSubscribeService;
   private final ExecutorService sseExecutor = new ThreadPoolExecutor(
      2, Math.max(4, Runtime.getRuntime().availableProcessors()), 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(128), runnable -> {
         Thread thread = new Thread(runnable);
         thread.setName("pt-subscribe-sse-" + SSE_THREAD_ID.getAndIncrement());
         thread.setDaemon(true);
         return thread;
      }, new CallerRunsPolicy()
   );

   @GetMapping
   public List<MoviePtSubscribe> list(@RequestParam(value = "state",required = false) String state) {
      return this.moviePtSubscribeService.list(state);
   }

   @GetMapping({"{id}"})
   public MoviePtSubscribe detail(@PathVariable("id") Long id) {
      return this.moviePtSubscribeService.getById(id);
   }

   @PostMapping
   public MoviePtSubscribe save(@RequestBody @Valid MoviePtSubscribeRequest request) {
      return this.moviePtSubscribeService.save(request);
   }

   @DeleteMapping({"{id}"})
   public void delete(@PathVariable("id") Long id) {
      this.moviePtSubscribeService.delete(id);
   }

   @DeleteMapping({"tmdb/{tmdbId}"})
   public void deleteByTmdb(
      @PathVariable("tmdbId") Long tmdbId,
      @RequestParam(value = "type",required = false) String type,
      @RequestParam(value = "season",required = false) Integer season
   ) {
      this.moviePtSubscribeService.deleteByTmdbId(tmdbId, type, season);
   }

   @PostMapping({"{id}/search"})
   public MoviePtSubscribeSearchResponse searchOnce(
      @PathVariable("id") Long id,
      @RequestParam(value = "limit",defaultValue = "20") Integer limit,
      @RequestParam(value = "autoDownload",required = false) Boolean autoDownload,
      @RequestParam(value = "title",required = false) String title,
      @RequestParam(value = "original_title",required = false) String originalTitle,
      @RequestParam(value = "year",required = false) String year,
      @RequestParam(value = "type",required = false) String type
   ) {
      return this.moviePtSubscribeService.searchOnce(id, limit, autoDownload, title, originalTitle, year, type);
   }

   @GetMapping(
      value = {"{id}/search"},
      produces = {"text/event-stream"}
   )
   public SseEmitter searchOnceStream(
      @PathVariable("id") Long id,
      @RequestParam(value = "limit",defaultValue = "20") Integer limit,
      @RequestParam(value = "autoDownload",required = false) Boolean autoDownload,
      @RequestParam(value = "title",required = false) String title,
      @RequestParam(value = "original_title",required = false) String originalTitle,
      @RequestParam(value = "year",required = false) String year,
      @RequestParam(value = "type",required = false) String type
   ) {
      SseEmitter emitter = new SseEmitter(SSE_TIMEOUT_MILLIS);
      Object sendLock = new Object();
      long startAt = System.currentTimeMillis();
      this.sendSubscribeSearchProgressEvent(
         emitter,
         sendLock,
         MoviePtSubscribeSearchProgressEvent.builder().type("START").subscribeId(id).limit(limit).autoDownload(autoDownload).message("订阅搜索开始").build()
      );
      CompletableFuture.runAsync(
         () -> {
            try {
               MoviePtSubscribeSearchResponse result = this.moviePtSubscribeService
                  .searchOnceWithProgress(
                     id, limit, autoDownload, title, originalTitle, year, type, event -> this.sendSubscribeSearchProgressEvent(emitter, sendLock, event)
                  );
               String completeMessage = result != null && result.getMessage() != null && !result.getMessage().isBlank() ? result.getMessage() : "订阅搜索完成";
               this.sendSubscribeSearchProgressEvent(
                  emitter,
                  sendLock,
                  MoviePtSubscribeSearchProgressEvent.builder()
                     .type("COMPLETE")
                     .subscribeId(id)
                     .limit(limit)
                     .autoDownload(autoDownload)
                     .elapsedMs(System.currentTimeMillis() - startAt)
                     .message(completeMessage)
                     .result(result)
                     .build()
               );
               synchronized (sendLock) {
                  emitter.complete();
               }
            } catch (Exception var19) {
               Exception e = var19;
               log.warn("订阅搜索 SSE 推送异常 subscribeId={} : {}", id, var19.getMessage());
               this.sendSubscribeSearchProgressEvent(
                  emitter,
                  sendLock,
                  MoviePtSubscribeSearchProgressEvent.builder()
                     .type("ERROR")
                     .subscribeId(id)
                     .limit(limit)
                     .autoDownload(autoDownload)
                     .elapsedMs(System.currentTimeMillis() - startAt)
                     .message(var19.getMessage())
                     .build()
               );
               synchronized (sendLock) {
                  emitter.completeWithError(e);
               }
            }
         },
         this.sseExecutor
      );
      emitter.onTimeout(() -> {
         log.warn("订阅搜索 SSE 超时 subscribeId={}", id);
         emitter.complete();
      });
      emitter.onError(ex -> log.warn("订阅搜索 SSE 连接异常 subscribeId={} : {}", id, ex.getMessage()));
      return emitter;
   }

   private void sendSubscribeSearchProgressEvent(SseEmitter emitter, Object sendLock, MoviePtSubscribeSearchProgressEvent event) {
      try {
         synchronized (sendLock) {
            emitter.send(SseEmitter.event().name("pt-subscribe-search-progress").data(event));
         }
      } catch (Exception var7) {
         log.debug("订阅搜索 SSE 发送失败: {}", var7.getMessage());
      }
   }

   @PreDestroy
   public void shutdownSseExecutor() {
      this.sseExecutor.shutdown();

      try {
         if (!this.sseExecutor.awaitTermination(5L, TimeUnit.SECONDS)) {
            this.sseExecutor.shutdownNow();
         }
      } catch (InterruptedException var2) {
         this.sseExecutor.shutdownNow();
         Thread.currentThread().interrupt();
      }
   }

   @PostMapping({"search-all"})
   public List<MoviePtSubscribeSearchResponse> searchAll(
      @RequestParam(value = "limit",defaultValue = "20") Integer limit, @RequestParam(value = "autoDownload",required = false) Boolean autoDownload
   ) {
      return this.moviePtSubscribeService.searchAll(limit, autoDownload);
   }

   @GetMapping({"{id}/download-records"})
   public Page<MovieDownloadRecordWithDetailsResponse> pageMatchedDownloadRecords(
      @PathVariable("id") Long id,
      @RequestParam(value = "current",defaultValue = "1") Long current,
      @RequestParam(value = "size",defaultValue = "20") Long size
   ) {
      return this.moviePtSubscribeService.pageMatchedDownloadRecords(id, current, size);
   }

   @GetMapping({"quality-config"})
   public MovieSubscribeQualityConfig getQualityConfig() {
      return this.moviePtSubscribeService.getQualityConfig();
   }

   @PostMapping({"quality-config"})
   public MovieSubscribeQualityConfig saveQualityConfig(@RequestBody MovieSubscribeQualityConfig config) {
      return this.moviePtSubscribeService.saveQualityConfig(config);
   }

   @Generated
   public MoviePtSubscribeController(final MoviePtSubscribeService moviePtSubscribeService) {
      this.moviePtSubscribeService = moviePtSubscribeService;
   }
}
