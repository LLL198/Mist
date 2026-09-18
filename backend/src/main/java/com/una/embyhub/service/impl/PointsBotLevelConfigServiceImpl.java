package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotLevelConfigMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigUpdate;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLevelConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLevelConfigStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotLevelConfig;
import com.una.embyhub.service.PointsBotLevelConfigService;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsBotLevelConfigServiceImpl extends ServiceImpl<PointsBotLevelConfigMapper, PointsBotLevelConfig> implements PointsBotLevelConfigService {
   @Override
   public Page<PointsBotLevelConfigResponse> select(MybatisPlusPage<PointsBotLevelConfigRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      return MpConvert.page(queryWrapper, this.getBaseMapper(), PointsBotLevelConfigResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public void insertConfig(PointsBotLevelConfigSave save) {
      PointsBotLevelConfig config = BeanUtils.convert(save, PointsBotLevelConfig.class);
      this.save(config);
   }

   @Override
   public void updateConfig(PointsBotLevelConfigUpdate update) {
      PointsBotLevelConfig config = BeanUtils.convert(update, PointsBotLevelConfig.class);
      this.updateById(config);
   }

   @Override
   public void deleteByConfigId(Long configId) {
      this.removeById(configId);
   }

   @Override
   public PointsBotLevelConfig findLevelForPoints(long points) {
      return this.lambdaQuery()
         .eq(PointsBotLevelConfig::getEnabled, Integer.valueOf(1))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .le(PointsBotLevelConfig::getMinPoints, Long.valueOf(points))
         .orderByDesc(PointsBotLevelConfig::getMinPoints)
         .orderByDesc(PointsBotLevelConfig::getSort)
         .orderByDesc(PointsBotLevelConfig::getId)
         .last("limit 1")
         .one();
   }

   @Override
   public PointsBotLevelConfigStatsResponse getStats() {
      PointsBotLevelConfigStatsResponse stats = new PointsBotLevelConfigStatsResponse();
      long totalLevels = this.lambdaQuery().eq(BaseEntity::getDelFlag, Integer.valueOf(0)).count();
      stats.setTotalLevels(totalLevels);
      long enabledLevels = this.lambdaQuery().eq(BaseEntity::getDelFlag, Integer.valueOf(0)).eq(PointsBotLevelConfig::getEnabled, Integer.valueOf(1)).count();
      stats.setEnabledLevels(enabledLevels);
      Map<Long, Long> userDistribution = new HashMap<>();

      for (Long levelId : this.lambdaQuery()
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .select(PointsBotLevelConfig::getId)
         .list()
         .stream()
         .map(PointsBotLevelConfig::getId)
         .collect(Collectors.toList())) {
         userDistribution.put(levelId, 0L);
      }

      stats.setUserDistribution(userDistribution);
      return stats;
   }
}
