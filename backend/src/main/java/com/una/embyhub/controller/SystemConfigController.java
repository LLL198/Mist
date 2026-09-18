package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.systemconfig.SystemConfigEnabledRequest;
import com.una.embyhub.model.dto.request.systemconfig.SystemConfigRequest;
import com.una.embyhub.model.dto.request.systemconfig.SystemConfigUpdate;
import com.una.embyhub.model.dto.response.systemconfig.SystemConfigResponse;
import com.una.embyhub.service.SystemConfigService;
import com.una.embyhub.service.impl.SimultaneousPlaybackUserConfigCleanupService;
import java.io.IOException;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"systemConfig"})
public class SystemConfigController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SystemConfigController.class);
   @Autowired
   private SystemConfigService systemConfigService;
   @Autowired
   private SimultaneousPlaybackUserConfigCleanupService simultaneousPlaybackUserConfigCleanupService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<SystemConfigResponse> select(@RequestBody @Validated MybatisPlusPage<SystemConfigRequest> page) {
      try {
         this.simultaneousPlaybackUserConfigCleanupService.reconcileStaleRules();
      } catch (Exception var3) {
         log.warn("校正失效的用户同播配置失败，不影响系统配置查询", (Throwable)var3);
      }

      return this.systemConfigService.select(page);
   }

   @PostMapping({"updateSystemConfig"})
   @SaCheckPermission({"admin"})
   public void updateSystemConfig(@RequestBody @Validated SystemConfigUpdate systemConfigUpdate) throws IOException, ClassNotFoundException {
      this.systemConfigService.updateSystemConfig(systemConfigUpdate);
   }

   @PostMapping({"isEnabled"})
   public boolean isEnabled(@RequestBody @Validated SystemConfigEnabledRequest request) {
      return this.systemConfigService.isPublicEnabled(request.getConfigKey());
   }

   @PostMapping({"getConfigValue"})
   @SaCheckPermission({"admin"})
   public String getConfigValue(@RequestBody @Validated SystemConfigEnabledRequest request) {
      return this.systemConfigService.getConfigValue(request.getConfigKey());
   }
}
