package com.una.embyhub.foam.controller;

import com.una.embyhub.foam.response.douban.FoamDoubanDiscoverResponse;
import com.una.embyhub.foam.service.FoamDoubanService;
import com.una.embyhub.service.DoubanImageProxyService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"foam/douban"})
public class FoamDoubanController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(FoamDoubanController.class);
   @Autowired
   private FoamDoubanService doubanService;
   @Autowired
   private DoubanImageProxyService imageProxyService;

   @GetMapping({"movies"})
   public FoamDoubanDiscoverResponse discoverMovies(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "30") int count,
      @RequestParam(defaultValue = "U") String sort,
      @RequestParam(defaultValue = "") String tags
   ) {
      log.info("探索豆瓣电影: page={}, count={}, sort={}, tags={}", page, count, sort, tags);
      return this.doubanService.discoverMovies(page, count, sort, tags);
   }

   @GetMapping({"tvs"})
   public FoamDoubanDiscoverResponse discoverTvs(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "30") int count,
      @RequestParam(defaultValue = "U") String sort,
      @RequestParam(defaultValue = "") String tags
   ) {
      log.info("探索豆瓣剧集: page={}, count={}, sort={}, tags={}", page, count, sort, tags);
      return this.doubanService.discoverTvs(page, count, sort, tags);
   }

   @GetMapping({"/image2"})
   public ResponseEntity<byte[]> proxyImage(@RequestParam("url") String url) {
      return this.imageProxyService.proxy(url);
   }
}
