package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyblockkeyword.EmbyBlockKeywordRequest;
import com.una.embyhub.model.dto.request.embyblockkeyword.EmbyClientFilterExclusionUpdateRequest;
import com.una.embyhub.model.dto.request.embyblockkeyword.EmbyClientFilterSettingsRequest;
import com.una.embyhub.model.dto.request.embyclientfilter.EmbyClientFilterRecordRequest;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleCreateRequest;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleRequest;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleUpdateRequest;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyBlockKeywordResponse;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyClientFilterExclusionResponse;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyClientFilterExclusionSummaryResponse;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyClientFilterSettingsResponse;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyClientFilterStatsResponse;
import com.una.embyhub.model.dto.response.embyclientfilter.EmbyClientFilterRecordResponse;
import com.una.embyhub.model.dto.response.embyregionblock.EmbyRegionBlockRuleResponse;
import com.una.embyhub.model.entity.EmbyBlockKeyword;
import com.una.embyhub.model.entity.EmbyClientFilterRecord;
import com.una.embyhub.service.EmbyBlockKeywordService;
import com.una.embyhub.service.EmbyClientFilterExclusionService;
import com.una.embyhub.service.EmbyClientFilterRecordService;
import com.una.embyhub.service.EmbyRegionBlockRuleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"embyBlockKeyword"})
public class EmbyBlockKeywordController {
   @Autowired
   private EmbyBlockKeywordService embyBlockKeywordService;
   @Autowired
   private EmbyClientFilterRecordService embyClientFilterRecordService;
   @Autowired
   private EmbyClientFilterExclusionService embyClientFilterExclusionService;
   @Autowired
   private EmbyRegionBlockRuleService embyRegionBlockRuleService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<EmbyBlockKeywordResponse> select(@RequestBody MybatisPlusPage<EmbyBlockKeywordRequest> page) {
      return this.embyBlockKeywordService.select(page);
   }

   @PostMapping({"add"})
   @SaCheckPermission({"admin"})
   public void add(@RequestBody @Validated EmbyBlockKeywordRequest request) {
      this.embyBlockKeywordService.add(request);
   }

   @PostMapping({"update"})
   @SaCheckPermission({"admin"})
   public void update(@RequestBody @Validated EmbyBlockKeywordRequest request) {
      this.embyBlockKeywordService.update(request);
   }

   @PostMapping({"delete"})
   @SaCheckPermission({"admin"})
   public void delete(@RequestParam Long id) {
      this.embyBlockKeywordService.delete(id);
   }

   @GetMapping({"enabled"})
   public List<String> getEnabledKeywords() {
      return this.embyBlockKeywordService.getEnabledKeywords();
   }

   @GetMapping({"settings"})
   @SaCheckPermission({"admin"})
   public EmbyClientFilterSettingsResponse settings() {
      EmbyClientFilterSettingsResponse response = new EmbyClientFilterSettingsResponse();
      response.setEnabled(this.embyBlockKeywordService.isClientFilterEnabled());
      response.setBlockUser(this.embyBlockKeywordService.isClientFilterBlockUserEnabled());
      response.setRegionEnabled(this.embyRegionBlockRuleService.isEnabled());
      response.setUsingDefaultPatterns(this.embyBlockKeywordService.isUsingDefaultClientFilterPatterns());
      response.setDefaultPatterns(this.embyBlockKeywordService.getDefaultClientFilterPatterns());
      response.setEffectivePatterns(this.embyBlockKeywordService.getEffectiveClientFilterPatterns());
      return response;
   }

   @PostMapping({"settings"})
   @SaCheckPermission({"admin"})
   public EmbyClientFilterSettingsResponse updateSettings(@RequestBody @Validated EmbyClientFilterSettingsRequest request) {
      if (request.getEnabled() != null) {
         this.embyBlockKeywordService.updateClientFilterEnabled(Boolean.TRUE.equals(request.getEnabled()));
      }

      if (request.getBlockUser() != null) {
         this.embyBlockKeywordService.updateClientFilterBlockUserEnabled(Boolean.TRUE.equals(request.getBlockUser()));
      }

      if (request.getRegionEnabled() != null) {
         this.embyRegionBlockRuleService.updateEnabled(Boolean.TRUE.equals(request.getRegionEnabled()));
      }

      return this.settings();
   }

   @GetMapping({"stats"})
   @SaCheckPermission({"admin"})
   public EmbyClientFilterStatsResponse stats() {
      EmbyClientFilterStatsResponse response = new EmbyClientFilterStatsResponse();
      response.setEnabled(this.embyBlockKeywordService.isClientFilterEnabled());
      response.setBlockUser(this.embyBlockKeywordService.isClientFilterBlockUserEnabled());
      response.setRegionEnabled(this.embyRegionBlockRuleService.isEnabled());
      response.setCustomRuleCount(this.embyBlockKeywordService.count());
      response.setEnabledRuleCount(
         this.embyBlockKeywordService.count(Wrappers.lambdaQuery(EmbyBlockKeyword.class).eq(EmbyBlockKeyword::getEnabled, Integer.valueOf(1)))
      );
      response.setInterceptTotalCount(this.embyClientFilterRecordService.count());
      response.setRegionCatalogRuleCount(this.embyRegionBlockRuleService.catalogRuleCount());
      response.setRegionEnabledRuleCount(this.embyRegionBlockRuleService.enabledRuleCount());
      response.setRegionInterceptCount(
         this.embyClientFilterRecordService.count(Wrappers.lambdaQuery(EmbyClientFilterRecord.class).eq(EmbyClientFilterRecord::getFilterType, "REGION"))
      );
      return response;
   }

   @PostMapping({"records"})
   @SaCheckPermission({"admin"})
   public Page<EmbyClientFilterRecordResponse> records(@RequestBody @Validated MybatisPlusPage<EmbyClientFilterRecordRequest> page) {
      return this.embyClientFilterRecordService.select(page);
   }

   @PostMapping({"regionRules/select"})
   @SaCheckPermission({"admin"})
   public Page<EmbyRegionBlockRuleResponse> regionRules(@RequestBody MybatisPlusPage<EmbyRegionBlockRuleRequest> page) {
      return this.embyRegionBlockRuleService.select(page);
   }

   @PostMapping({"regionRules/custom/add"})
   @SaCheckPermission({"admin"})
   public void addCustomRegionRule(@RequestBody @Validated EmbyRegionBlockRuleCreateRequest request) {
      this.embyRegionBlockRuleService.createCustom(request);
   }

   @PostMapping({"regionRules/update"})
   @SaCheckPermission({"admin"})
   public void updateRegionRule(@RequestBody @Validated EmbyRegionBlockRuleUpdateRequest request) {
      this.embyRegionBlockRuleService.update(request);
   }

   @PostMapping({"regionRules/custom/delete"})
   @SaCheckPermission({"admin"})
   public void deleteCustomRegionRule(@RequestParam Long id) {
      this.embyRegionBlockRuleService.deleteCustom(id);
   }

   @GetMapping({"exclusions/summary"})
   @SaCheckPermission({"admin"})
   public EmbyClientFilterExclusionSummaryResponse exclusionSummary() {
      return this.embyClientFilterExclusionService.summary();
   }

   @GetMapping({"exclusions"})
   @SaCheckPermission({"admin"})
   public EmbyClientFilterExclusionResponse exclusions(@RequestParam Long embyInfoId) {
      return this.embyClientFilterExclusionService.details(embyInfoId);
   }

   @PostMapping({"exclusions"})
   @SaCheckPermission({"admin"})
   public EmbyClientFilterExclusionResponse updateExclusions(@RequestBody @Validated EmbyClientFilterExclusionUpdateRequest request) {
      return this.embyClientFilterExclusionService.update(request);
   }
}
