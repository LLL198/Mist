package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.systemconfig.SystemConfigRequest;
import com.una.embyhub.model.dto.request.systemconfig.SystemConfigUpdate;
import com.una.embyhub.model.dto.response.systemconfig.SystemConfigResponse;
import com.una.embyhub.model.entity.SystemConfig;

public interface SystemConfigService extends IService<SystemConfig> {
   Page<SystemConfigResponse> select(MybatisPlusPage<SystemConfigRequest> page);

   void updateSystemConfig(SystemConfigUpdate systemConfigUpdate);

   boolean isPublicEnabled(String configKey);

   String getConfigValue(String configKey);
}
