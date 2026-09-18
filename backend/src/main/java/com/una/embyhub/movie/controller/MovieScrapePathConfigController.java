package com.una.embyhub.movie.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.movie.model.MovieScrapePathConfig;
import com.una.embyhub.movie.model.MovieScrapePathConfigRequest;
import com.una.embyhub.movie.service.MovieScrapePathConfigService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Generated;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"movie/scrape-paths"})
@Validated
@SaCheckPermission({"admin"})
public class MovieScrapePathConfigController {
   private final MovieScrapePathConfigService movieScrapePathConfigService;

   @GetMapping
   public List<MovieScrapePathConfig> list() {
      return this.movieScrapePathConfigService.list();
   }

   @GetMapping({"{id}"})
   public MovieScrapePathConfig detail(@PathVariable("id") Long id) {
      return this.movieScrapePathConfigService.getById(id);
   }

   @PostMapping
   public MovieScrapePathConfig save(@RequestBody @Valid MovieScrapePathConfigRequest request) {
      return this.movieScrapePathConfigService.save(request);
   }

   @DeleteMapping({"{id}"})
   public void delete(@PathVariable("id") Long id) {
      this.movieScrapePathConfigService.delete(id);
   }

   @Generated
   public MovieScrapePathConfigController(final MovieScrapePathConfigService movieScrapePathConfigService) {
      this.movieScrapePathConfigService = movieScrapePathConfigService;
   }
}
