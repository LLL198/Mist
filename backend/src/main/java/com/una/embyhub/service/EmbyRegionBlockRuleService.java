package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleCreateRequest;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleRequest;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleUpdateRequest;
import com.una.embyhub.model.dto.response.embyregionblock.EmbyRegionBlockRuleResponse;
import java.util.Optional;

public interface EmbyRegionBlockRuleService {
   Page<EmbyRegionBlockRuleResponse> select(MybatisPlusPage<EmbyRegionBlockRuleRequest> page);

   void createCustom(EmbyRegionBlockRuleCreateRequest request);

   void update(EmbyRegionBlockRuleUpdateRequest request);

   void deleteCustom(Long id);

   boolean isEnabled();

   void updateEnabled(boolean enabled);

   long catalogRuleCount();

   long enabledRuleCount();

   Optional<EmbyRegionBlockRuleService.RegionMatch> match(String remoteEndPoint);

   public static record Location(String ip, String country, String province, String city) {
   }

   public static record RegionMatch(String ruleCode, String displayName, EmbyRegionBlockRuleService.Location location) {
   }
}
