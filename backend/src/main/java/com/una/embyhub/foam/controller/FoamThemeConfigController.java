package com.una.embyhub.foam.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.fasterxml.jackson.databind.JsonNode;
import com.una.embyhub.foam.service.FoamThemeConfigService;
import jakarta.validation.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"foam/theme-config"})
@Validated
@SaCheckPermission({"admin"})
public class FoamThemeConfigController {
   private final FoamThemeConfigService foamThemeConfigService;

   @PostMapping({"{key}"})
   public JsonNode create(@PathVariable("key") @NotBlank String key, @RequestBody JsonNode data) {
      return this.foamThemeConfigService.create(key, data);
   }

   @GetMapping({"{key}"})
   public JsonNode get(@PathVariable("key") @NotBlank String key) {
      return this.foamThemeConfigService.get(key);
   }

   @PutMapping({"{key}"})
   public JsonNode update(@PathVariable("key") @NotBlank String key, @RequestBody JsonNode data) {
      return this.foamThemeConfigService.update(key, data);
   }

   @DeleteMapping({"{key}"})
   public void delete(@PathVariable("key") @NotBlank String key) {
      this.foamThemeConfigService.delete(key);
   }

   @GetMapping({"keys"})
   public List<String> keys() {
      return this.foamThemeConfigService.keys();
   }

   @Generated
   public FoamThemeConfigController(final FoamThemeConfigService foamThemeConfigService) {
      this.foamThemeConfigService = foamThemeConfigService;
   }
}
