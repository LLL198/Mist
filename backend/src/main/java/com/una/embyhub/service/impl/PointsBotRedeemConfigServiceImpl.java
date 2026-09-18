package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.PointsBotRedeemTypeEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotRedeemConfigMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigUpdate;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedeemConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedeemConfigStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotRedeemConfig;
import com.una.embyhub.service.PointsBotRedeemConfigService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsBotRedeemConfigServiceImpl extends ServiceImpl<PointsBotRedeemConfigMapper, PointsBotRedeemConfig> implements PointsBotRedeemConfigService {
   @Override
   public Page<PointsBotRedeemConfigResponse> select(MybatisPlusPage<PointsBotRedeemConfigRequest> page) {
      String redeemType = page.getObject().getRedeemType();
      if (StringUtils.hasText(redeemType) && !PointsBotRedeemTypeEnum.isSupported(redeemType)) {
         throw this.badRequest("兑换类型不合法");
      } else {
         QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
         return MpConvert.page(
            queryWrapper, this.getBaseMapper(), PointsBotRedeemConfigResponse.class, page.getCurrent(), page.getSize(), resolvePageOrders(page.getOrders())
         );
      }
   }

   static List<OrderItem> resolvePageOrders(List<OrderItem> requestedOrders) {
      return requestedOrders != null && !requestedOrders.isEmpty() ? requestedOrders : List.of(OrderItem.desc("create_datetime"), OrderItem.desc("id"));
   }

   @Override
   public void insertConfig(PointsBotRedeemConfigSave save) {
      this.validateRedeemType(save.getRedeemType());
      save.setConfigName(save.getConfigName().trim());
      if (StringUtils.hasText(save.getRemark())) {
         save.setRemark(save.getRemark().trim());
      }

      PointsBotRedeemConfig config = BeanUtils.convert(save, PointsBotRedeemConfig.class);
      if (!this.save(config)) {
         throw this.badRequest("兑换配置保存失败");
      }
   }

   @Override
   public void updateConfig(PointsBotRedeemConfigUpdate update) {
      if (this.getById(update.getId()) == null) {
         throw this.badRequest("兑换配置不存在或已删除");
      } else {
         if (update.getRedeemType() != null) {
            this.validateRedeemType(update.getRedeemType());
         }

         if (update.getConfigName() != null) {
            update.setConfigName(update.getConfigName().trim());
            if (!StringUtils.hasText(update.getConfigName())) {
               throw this.badRequest("配置名称不能为空");
            }
         }

         if (StringUtils.hasText(update.getRemark())) {
            update.setRemark(update.getRemark().trim());
         }

         PointsBotRedeemConfig config = BeanUtils.convert(update, PointsBotRedeemConfig.class);
         if (!this.updateById(config)) {
            throw this.badRequest("兑换配置更新失败");
         }
      }
   }

   @Override
   public void deleteByConfigId(Long configId) {
      if (configId == null || configId <= 0L || this.getById(configId) == null) {
         throw this.badRequest("兑换配置不存在或已删除");
      } else if (!this.removeById(configId)) {
         throw this.badRequest("兑换配置删除失败");
      }
   }

   @Override
   public PointsBotRedeemConfig findEnabledConfig() {
      return this.lambdaQuery()
         .eq(PointsBotRedeemConfig::getEnabled, Integer.valueOf(1))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .orderByDesc(PointsBotRedeemConfig::getSort)
         .orderByDesc(PointsBotRedeemConfig::getId)
         .last("limit 1")
         .one();
   }

   @Override
   public PointsBotRedeemConfigStatsResponse getStats() {
      PointsBotRedeemConfigStatsResponse stats = new PointsBotRedeemConfigStatsResponse();
      List<PointsBotRedeemConfig> allConfigs = this.lambdaQuery().eq(BaseEntity::getDelFlag, Integer.valueOf(0)).list();
      stats.setTotalConfigs((long)allConfigs.size());
      long enabledConfigs = allConfigs.stream().filter(config -> config.getEnabled() != null && config.getEnabled() == 1).count();
      stats.setEnabledConfigs(enabledConfigs);
      stats.setDisabledConfigs(stats.getTotalConfigs() - enabledConfigs);
      Map<Long, Long> configsByServer = allConfigs.stream()
         .filter(config -> config.getEmbyInfoId() != null)
         .collect(Collectors.groupingBy(PointsBotRedeemConfig::getEmbyInfoId, Collectors.counting()));
      stats.setConfigsByServer(configsByServer);
      return stats;
   }

   private void validateRedeemType(String redeemType) {
      if (!PointsBotRedeemTypeEnum.isSupported(redeemType)) {
         throw this.badRequest("兑换类型不合法");
      }
   }

   private BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }
}
