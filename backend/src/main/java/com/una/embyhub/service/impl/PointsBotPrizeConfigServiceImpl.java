package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotPrizeConfigMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigUpdate;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPrizeConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPrizeConfigStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotLevelConfig;
import com.una.embyhub.model.entity.PointsBotPrizeConfig;
import com.una.embyhub.service.PointsBotLevelConfigService;
import com.una.embyhub.service.PointsBotPrizeConfigService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsBotPrizeConfigServiceImpl extends ServiceImpl<PointsBotPrizeConfigMapper, PointsBotPrizeConfig> implements PointsBotPrizeConfigService {
   private final PointsBotLevelConfigService levelConfigService;

   public PointsBotPrizeConfigServiceImpl(PointsBotLevelConfigService levelConfigService) {
      this.levelConfigService = levelConfigService;
   }

   @Override
   public Page<PointsBotPrizeConfigResponse> select(MybatisPlusPage<PointsBotPrizeConfigRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      return MpConvert.page(queryWrapper, this.getBaseMapper(), PointsBotPrizeConfigResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public void insertConfig(PointsBotPrizeConfigSave save) {
      PointsBotPrizeConfig config = BeanUtils.convert(save, PointsBotPrizeConfig.class);
      this.applyAutoFields(config, save.getLevelId(), save.getRequiredPoints(), save.getRemainingQuantity());
      this.save(config);
   }

   @Override
   public void updateConfig(PointsBotPrizeConfigUpdate update) {
      PointsBotPrizeConfig config = BeanUtils.convert(update, PointsBotPrizeConfig.class);
      this.applyAutoFields(config, update.getLevelId(), update.getRequiredPoints(), update.getRemainingQuantity());
      this.updateById(config);
   }

   @Override
   public void deleteByConfigId(Long configId) {
      this.removeById(configId);
   }

   @Override
   public List<PointsBotPrizeConfig> listAvailablePrizes() {
      return this.lambdaQuery()
         .eq(PointsBotPrizeConfig::getEnabled, Integer.valueOf(1))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .gt(PointsBotPrizeConfig::getRemainingQuantity, Integer.valueOf(0))
         .orderByDesc(PointsBotPrizeConfig::getSort)
         .orderByDesc(PointsBotPrizeConfig::getId)
         .list();
   }

   private void applyAutoFields(PointsBotPrizeConfig config, Long levelId, Integer requiredPoints, Integer remainingQuantity) {
      if (remainingQuantity == null && config.getTotalQuantity() != null && config.getId() == null) {
         config.setRemainingQuantity(config.getTotalQuantity());
      }

      if (requiredPoints == null && levelId != null) {
         PointsBotLevelConfig level = this.levelConfigService.getById(levelId);
         if (level != null && level.getMinPoints() != null) {
            config.setRequiredPoints(level.getMinPoints());
         }
      }
   }

   @Override
   public PointsBotPrizeConfigStatsResponse getStats() {
      PointsBotPrizeConfigStatsResponse stats = new PointsBotPrizeConfigStatsResponse();
      QueryWrapper<PointsBotPrizeConfig> summaryWrapper = new QueryWrapper<>();
      summaryWrapper.select(
         new String[]{
            "COUNT(*) AS totalPrizes",
            "COALESCE(SUM(CASE WHEN enabled = 1 THEN 1 ELSE 0 END), 0) AS enabledPrizes",
            "COALESCE(SUM(CASE WHEN remaining_quantity IS NOT NULL THEN remaining_quantity ELSE 0 END), 0) AS totalRemainingQuantity",
            "COALESCE(SUM(CASE WHEN remaining_quantity IS NOT NULL AND remaining_quantity <= 0 THEN 1 ELSE 0 END), 0) AS soldOutPrizes",
            "COALESCE(SUM(CASE WHEN required_points IS NOT NULL THEN required_points ELSE 0 END), 0) AS totalPrizeValue"
         }
      );
      summaryWrapper.eq("del_flag", Integer.valueOf(0));
      Map<String, Object> row = this.getBaseMapper().selectMaps(summaryWrapper).stream().findFirst().orElseGet(HashMap::new);
      stats.setTotalPrizes(this.toLong(row.get("totalPrizes")));
      stats.setEnabledPrizes(this.toLong(row.get("enabledPrizes")));
      stats.setTotalRemainingQuantity(this.toLong(row.get("totalRemainingQuantity")));
      stats.setSoldOutPrizes(this.toLong(row.get("soldOutPrizes")));
      stats.setTotalPrizeValue(this.toLong(row.get("totalPrizeValue")));
      return stats;
   }

   private long toLong(Object value) {
      if (value == null) {
         return 0L;
      } else if (value instanceof Number number) {
         return number.longValue();
      } else {
         try {
            return Long.parseLong(value.toString());
         } catch (Exception var3) {
            return 0L;
         }
      }
   }
}
