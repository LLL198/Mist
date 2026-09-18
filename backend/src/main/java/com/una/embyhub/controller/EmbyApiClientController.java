package com.una.embyhub.controller;
import com.una.embyhub.model.dto.response.emby.EmbyTmdbResponse;
import com.una.embyhub.service.EmbyApiClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"embyApiClient"})
public class EmbyApiClientController {
   @Autowired
   private EmbyApiClientService embyApiClientService;

   @PostMapping({"getEmbyTmdbResponseByTmdbIdAll"})
   public EmbyTmdbResponse getEmbyTmdbResponseByTmdbIdAll(@RequestParam String tmdbId) {
      return this.embyApiClientService.getEmbyTmdbResponseByTmdbIdAll(tmdbId);
   }

   @PostMapping({"getEmbyTmdbResponseByTmdbId"})
   public EmbyTmdbResponse getEmbyTmdbResponseByTmdbId(@RequestParam String tmdbId, @RequestParam(required = false) Long embyInfoId) {
      return this.embyApiClientService.getEmbyTmdbResponseByTmdbId(tmdbId, embyInfoId);
   }
}
