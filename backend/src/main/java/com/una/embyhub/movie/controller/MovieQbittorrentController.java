package com.una.embyhub.movie.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieQbittorrentAddRequest;
import com.una.embyhub.movie.model.MovieQbittorrentConfig;
import com.una.embyhub.movie.model.MovieQbittorrentConfigRequest;
import com.una.embyhub.movie.model.MovieQbittorrentTorrent;
import com.una.embyhub.movie.service.MovieQbittorrentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Generated;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"movie/qbittorrent"})
@Validated
@SaCheckPermission({"admin"})
public class MovieQbittorrentController {
   private final MovieQbittorrentService qbittorrentService;

   @GetMapping({"config"})
   public MovieQbittorrentConfig getConfig() {
      return this.qbittorrentService.getConfig();
   }

   @PostMapping({"config"})
   public MovieQbittorrentConfig saveConfig(@RequestBody @Valid MovieQbittorrentConfigRequest request) {
      return this.qbittorrentService.saveConfig(request);
   }

   @PostMapping({"config/create"})
   public MovieQbittorrentConfig createConfig(@RequestBody @Valid MovieQbittorrentConfigRequest request) {
      return this.qbittorrentService.createConfig(request);
   }

   @PutMapping({"config/{id}"})
   public MovieQbittorrentConfig updateConfig(@PathVariable("id") Long id, @RequestBody @Valid MovieQbittorrentConfigRequest request) {
      return this.qbittorrentService.updateConfig(id, request);
   }

   @GetMapping({"config/{id}"})
   public MovieQbittorrentConfig getConfigById(@PathVariable("id") Long id) {
      return this.qbittorrentService.getConfigById(id);
   }

   @GetMapping({"config/list"})
   public List<MovieQbittorrentConfig> listConfigs() {
      return this.qbittorrentService.listConfigs();
   }

   @DeleteMapping({"config/{id}"})
   public void deleteConfig(@PathVariable("id") Long id) {
      this.qbittorrentService.deleteConfig(id);
   }

   @GetMapping({"queue"})
   public List<MovieQbittorrentTorrent> queue(
      @RequestParam(value = "filterActive",defaultValue = "false") boolean filterActive,
      @RequestParam(value = "downloaderId",required = false) Long downloaderId
   ) {
      return this.qbittorrentService.getQueue(filterActive, downloaderId);
   }

   @PostMapping({"add"})
   public MovieActionResponse addMagnet(@RequestBody @Valid MovieQbittorrentAddRequest request) {
      return this.qbittorrentService.addMagnet(request.getMagnet(), request.getSavePath());
   }

   @PostMapping({"pause"})
   public MovieActionResponse pause(@RequestParam("hashes") @NotBlank String hashes, @RequestParam("downloaderId") @NotNull Long downloaderId) {
      return this.qbittorrentService.pause(hashes, downloaderId);
   }

   @PostMapping({"resume"})
   public MovieActionResponse resume(@RequestParam("hashes") @NotBlank String hashes, @RequestParam("downloaderId") @NotNull Long downloaderId) {
      return this.qbittorrentService.resume(hashes, downloaderId);
   }

   @DeleteMapping({"delete"})
   public MovieActionResponse delete(@RequestParam("recordId") Long recordId, @RequestParam(value = "deleteFiles",defaultValue = "false") boolean deleteFiles) {
      return this.qbittorrentService.delete(recordId, deleteFiles);
   }

   @Generated
   public MovieQbittorrentController(final MovieQbittorrentService qbittorrentService) {
      this.qbittorrentService = qbittorrentService;
   }
}
