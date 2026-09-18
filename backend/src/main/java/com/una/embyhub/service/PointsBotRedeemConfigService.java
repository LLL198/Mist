package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigUpdate;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedeemConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedeemConfigStatsResponse;
import com.una.embyhub.model.entity.PointsBotRedeemConfig;

public interface PointsBotRedeemConfigService extends IService<PointsBotRedeemConfig> {
   Page<PointsBotRedeemConfigResponse> select(MybatisPlusPage<PointsBotRedeemConfigRequest> page);

   void insertConfig(PointsBotRedeemConfigSave save);

   void updateConfig(PointsBotRedeemConfigUpdate update);

   void deleteByConfigId(Long configId);

   PointsBotRedeemConfig findEnabledConfig();

   PointsBotRedeemConfigStatsResponse getStats();
}
