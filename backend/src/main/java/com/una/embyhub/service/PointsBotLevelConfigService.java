package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigUpdate;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLevelConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLevelConfigStatsResponse;
import com.una.embyhub.model.entity.PointsBotLevelConfig;

public interface PointsBotLevelConfigService extends IService<PointsBotLevelConfig> {
   Page<PointsBotLevelConfigResponse> select(MybatisPlusPage<PointsBotLevelConfigRequest> page);

   void insertConfig(PointsBotLevelConfigSave save);

   void updateConfig(PointsBotLevelConfigUpdate update);

   void deleteByConfigId(Long configId);

   PointsBotLevelConfig findLevelForPoints(long points);

   PointsBotLevelConfigStatsResponse getStats();
}
