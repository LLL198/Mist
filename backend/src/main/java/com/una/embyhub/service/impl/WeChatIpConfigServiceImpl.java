package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.mapper.WeChatIpConfigMapper;
import com.una.embyhub.model.dto.request.wechatipconfig.WeChatIpConfigSave;
import com.una.embyhub.model.dto.request.wechatipconfig.WeChatIpConfigUpdate;
import com.una.embyhub.model.dto.response.wechatipconfig.WeChatIpConfigResponse;
import com.una.embyhub.model.entity.WeChatIpConfig;
import com.una.embyhub.service.WeChatIpConfigService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class WeChatIpConfigServiceImpl extends ServiceImpl<WeChatIpConfigMapper, WeChatIpConfig> implements WeChatIpConfigService {
   @Override
   public List<WeChatIpConfigResponse> select() {
      return BeanUtils.convertList(this.list(), WeChatIpConfigResponse.class);
   }

   @Override
   public void add(WeChatIpConfigSave save) {
      WeChatIpConfig config = BeanUtils.convert(save, WeChatIpConfig.class);
      if (config.getEnabled() != null && config.getEnabled() == 1) {
         this.disableAllConfigs();
      }

      this.save(config);
   }

   @Override
   public void update(WeChatIpConfigUpdate update) {
      WeChatIpConfig config = BeanUtils.convert(update, WeChatIpConfig.class);
      if (config.getEnabled() != null && config.getEnabled() == 1) {
         this.disableAllConfigsExcept(config.getId());
      }

      this.updateById(config);
   }

   @Override
   public void delete(Long id) {
      this.removeById(id);
   }

   @Override
   public WeChatIpConfig getFirstEnabled() {
      LambdaQueryWrapper<WeChatIpConfig> wrapper = new LambdaQueryWrapper<>();
      wrapper.eq(WeChatIpConfig::getEnabled, Integer.valueOf(1)).orderByAsc(WeChatIpConfig::getId).last("LIMIT 1");
      return this.getOne(wrapper);
   }

   @Override
   public List<WeChatIpConfig> getAllEnabled() {
      LambdaQueryWrapper<WeChatIpConfig> wrapper = new LambdaQueryWrapper<>();
      wrapper.eq(WeChatIpConfig::getEnabled, Integer.valueOf(1)).orderByAsc(WeChatIpConfig::getId);
      return this.list(wrapper);
   }

   @Override
   public void updateLastIp(Long id, String ip) {
      LambdaUpdateWrapper<WeChatIpConfig> wrapper = new LambdaUpdateWrapper<>();
      wrapper.eq(WeChatIpConfig::getId, id).set(WeChatIpConfig::getLastIp, ip).set(WeChatIpConfig::getLastIpUpdateTime, new Date());
      this.update(wrapper);
   }

   private void disableAllConfigs() {
      LambdaUpdateWrapper<WeChatIpConfig> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(WeChatIpConfig::getEnabled, Integer.valueOf(0));
      this.update(wrapper);
   }

   private void disableAllConfigsExcept(Long excludeId) {
      if (excludeId == null) {
         this.disableAllConfigs();
      } else {
         LambdaUpdateWrapper<WeChatIpConfig> wrapper = new LambdaUpdateWrapper<>();
         wrapper.ne(WeChatIpConfig::getId, excludeId).set(WeChatIpConfig::getEnabled, Integer.valueOf(0));
         this.update(wrapper);
      }
   }
}
