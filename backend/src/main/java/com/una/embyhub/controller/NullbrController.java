package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.response.nullbr.MovieListResponse;
import com.una.embyhub.service.NullbrService;
import info.movito.themoviedbapi.tools.TmdbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"nullbr"})
public class NullbrController {
   @Autowired
   private NullbrService nullbrService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public MovieListResponse select(@RequestParam String tmdbId, @RequestParam String type) throws TmdbException {
      return this.nullbrService.select(tmdbId, type);
   }
}
