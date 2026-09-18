package com.una.embyhub.movie.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.movie.model.MoviePtSearchProgressEvent;
import com.una.embyhub.movie.model.MoviePtSearchResult;
import com.una.embyhub.movie.service.MoviePtSearchService;
import jakarta.annotation.PreDestroy;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping({"movie/pt-search"})
@SaCheckPermission({"admin"})
public class MoviePtSearchController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtSearchController.class);
   private static final long SSE_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(5L);
   private static final AtomicInteger SSE_THREAD_ID = new AtomicInteger(1);
   private final MoviePtSearchService moviePtSearchService;
   private final ExecutorService sseExecutor = new ThreadPoolExecutor(
      2, Math.max(4, Runtime.getRuntime().availableProcessors()), 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(128), runnable -> {
         Thread thread = new Thread(runnable);
         thread.setName("pt-search-sse-" + SSE_THREAD_ID.getAndIncrement());
         thread.setDaemon(true);
         return thread;
      }, new CallerRunsPolicy()
   );

   @GetMapping
   public List<MoviePtSearchResult> search(
      @RequestParam("keyword") String keyword,
      @RequestParam(value = "siteId",required = false) Long siteId,
      @RequestParam(value = "limit",defaultValue = "20") Integer limit,
      @RequestParam(value = "title",required = false) String title,
      @RequestParam(value = "original_title",required = false) String originalTitle,
      @RequestParam(value = "year",required = false) String year,
      @RequestParam(value = "type",required = false) String type
   ) {
      return this.moviePtSearchService.search(keyword, siteId, limit, title, originalTitle, year, type);
   }

   @GetMapping(
      value = {"/stream"},
      produces = {"text/event-stream"}
   )
   public SseEmitter searchStream(
      @RequestParam("keyword") String keyword,
      @RequestParam(value = "siteId",required = false) Long siteId,
      @RequestParam(value = "limit",defaultValue = "20") Integer limit,
      @RequestParam(value = "title",required = false) String title,
      @RequestParam(value = "original_title",required = false) String originalTitle,
      @RequestParam(value = "year",required = false) String year,
      @RequestParam(value = "type",required = false) String type
   ) {
      SseEmitter emitter = new SseEmitter(SSE_TIMEOUT_MILLIS);
      Object sendLock = new Object();
      CompletableFuture.runAsync(
         () -> {
            try {
               this.moviePtSearchService
                  .searchWithProgress(keyword, siteId, limit, title, originalTitle, year, type, event -> this.sendProgressEvent(emitter, sendLock, event));
               synchronized (sendLock) {
                  emitter.complete();
               }
            } catch (Exception var15) {
               Exception e = var15;
               log.warn("PT 搜索 SSE 推送异常: {}", var15.getMessage());
               this.sendProgressEvent(
                  emitter, sendLock, MoviePtSearchProgressEvent.builder().type("STREAM_ERROR").keyword(keyword).message(var15.getMessage()).build()
               );
               synchronized (sendLock) {
                  emitter.completeWithError(e);
               }
            }
         },
         this.sseExecutor
      );
      emitter.onTimeout(() -> {
         log.warn("PT 搜索 SSE 超时");
         emitter.complete();
      });
      emitter.onError(ex -> log.warn("PT 搜索 SSE 连接异常: {}", ex.getMessage()));
      return emitter;
   }

   private void sendProgressEvent(SseEmitter emitter, Object sendLock, MoviePtSearchProgressEvent event) {
      try {
         synchronized (sendLock) {
            emitter.send(SseEmitter.event().name("pt-search-progress").data(event));
         }
      } catch (Exception var7) {
         log.debug("PT 搜索 SSE 发送失败: {}", var7.getMessage());
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

   @Generated
   public MoviePtSearchController(final MoviePtSearchService moviePtSearchService) {
      this.moviePtSearchService = moviePtSearchService;
   }
}
