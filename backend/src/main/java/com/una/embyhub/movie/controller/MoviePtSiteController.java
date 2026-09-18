package com.una.embyhub.movie.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.movie.model.MoviePtSite;
import com.una.embyhub.movie.model.MoviePtSiteSaveRequest;
import com.una.embyhub.movie.service.MoviePtSiteService;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"movie/pt-sites"})
@Validated
@SaCheckPermission({"admin"})
public class MoviePtSiteController {
   private final MoviePtSiteService moviePtSiteService;

   @GetMapping
   public List<MoviePtSite> list(@RequestParam(value = "enabled",required = false) Integer enabled) {
      return this.moviePtSiteService.list(enabled, true);
   }

   @GetMapping({"{id}"})
   public MoviePtSite detail(@PathVariable("id") Long id) {
      return this.moviePtSiteService.getById(id, true);
   }

   @PostMapping
   public MoviePtSite save(@RequestBody @Valid MoviePtSiteSaveRequest request) {
      return this.moviePtSiteService.save(request);
   }

   @GetMapping({"{id}/favicon"})
   public void getFavicon(@PathVariable("id") Long id, HttpServletResponse response) {
      this.moviePtSiteService.getFavicon(id, response);
   }

   @DeleteMapping({"{id}"})
   public void delete(@PathVariable("id") Long id) {
      this.moviePtSiteService.deleteById(id);
   }

   @Generated
   public MoviePtSiteController(final MoviePtSiteService moviePtSiteService) {
      this.moviePtSiteService = moviePtSiteService;
   }
}
