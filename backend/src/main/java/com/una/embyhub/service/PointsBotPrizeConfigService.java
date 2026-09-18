package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigUpdate;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPrizeConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPrizeConfigStatsResponse;
import com.una.embyhub.model.entity.PointsBotPrizeConfig;
import java.util.List;

public interface PointsBotPrizeConfigService extends IService<PointsBotPrizeConfig> {
   Page<PointsBotPrizeConfigResponse> select(MybatisPlusPage<PointsBotPrizeConfigRequest> page);

   void insertConfig(PointsBotPrizeConfigSave save);

   void updateConfig(PointsBotPrizeConfigUpdate update);

   void deleteByConfigId(Long configId);

   List<PointsBotPrizeConfig> listAvailablePrizes();

   PointsBotPrizeConfigStatsResponse getStats();
}
