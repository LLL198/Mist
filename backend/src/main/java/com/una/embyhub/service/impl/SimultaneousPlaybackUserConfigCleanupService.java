package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.mapper.SystemConfigMapper;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.SystemConfig;
import java.util.List;
import java.util.function.Function;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

@Service
public class SimultaneousPlaybackUserConfigCleanupService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SimultaneousPlaybackUserConfigCleanupService.class);
   @Autowired
   private SystemConfigMapper systemConfigMapper;
   @Autowired
   private EmbyUserMapper embyUserMapper;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;

   @Transactional(
      propagation = Propagation.REQUIRES_NEW,
      rollbackFor = {Exception.class}
   )
   public int removeUserRule(Long embyInfoId, String embyUserId, String embyUserName) {
      SimultaneousPlaybackUserConfigRules.UserIdentity user = new SimultaneousPlaybackUserConfigRules.UserIdentity(embyInfoId, embyUserId, embyUserName);
      int removed = this.updateRules(configValue -> SimultaneousPlaybackUserConfigRules.removeUsers(configValue, List.of(user)));
      if (removed > 0) {
         log.info("已移除用户同播配置: serverId={}, embyUserId={}, userName={}", embyInfoId, embyUserId, embyUserName);
      }

      return removed;
   }

   @Transactional(
      propagation = Propagation.REQUIRES_NEW,
      rollbackFor = {Exception.class}
   )
   public int reconcileStaleRules() {
      int removed = this.updateRules(configValue -> SimultaneousPlaybackUserConfigRules.retainUsers(configValue, this::isEligibleUser));
      if (removed > 0) {
         log.info("已清理 {} 条失效或免限用户同播配置", removed);
      }

      return removed;
   }

   private int updateRules(Function<String, SimultaneousPlaybackUserConfigRules.CleanupResult> cleanup) {
      SystemConfig config = this.systemConfigMapper
         .selectOne(
            new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, "simultaneous_playback_disable_threshold")
               .last("LIMIT 1 FOR UPDATE")
         );
      if (config == null) {
         return 0;
      } else {
         SimultaneousPlaybackUserConfigRules.CleanupResult result = cleanup.apply(config.getConfigValue());
         if (result.removedCount() == 0) {
            return 0;
         } else {
            SystemConfig update = new SystemConfig();
            update.setId(config.getId());
            update.setConfigValue(result.configValue());
            if (this.systemConfigMapper.updateById(update) != 1) {
               throw new IllegalStateException("更新同时播放用户配置失败");
            } else {
               this.refreshConfigCacheAfterCommit();
               return result.removedCount();
            }
         }
      }
   }

   private boolean isEligibleUser(SimultaneousPlaybackUserConfigRules.UserRule rule) {
      EmbyUser user = this.findUser(rule);
      return user != null
         && !Integer.valueOf(1).equals(user.getIsAdmin())
         && !Integer.valueOf(1).equals(user.getIsPrimaryAdmin())
         && HostLineTypeEnum.normalize(user.getHostLineType()) != HostLineTypeEnum.WHITELIST.getCode();
   }

   private EmbyUser findUser(SimultaneousPlaybackUserConfigRules.UserRule rule) {
      LambdaQueryWrapper<EmbyUser> query = new LambdaQueryWrapper<EmbyUser>().eq(EmbyUser::getEmbyInfoId, rule.serverId());
      if (StringUtils.hasText(rule.userId())) {
         query.eq(EmbyUser::getEmbyUserId, rule.userId());
      } else {
         query.eq(EmbyUser::getEmbyUserName, rule.userName());
      }

      return this.embyUserMapper.selectOne(query.last("LIMIT 1"));
   }

   private void refreshConfigCacheAfterCommit() {
      if (!TransactionSynchronizationManager.isSynchronizationActive()) {
         this.refreshConfigCacheSafely();
      } else {
         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
               SimultaneousPlaybackUserConfigCleanupService.this.refreshConfigCacheSafely();
            }
         });
      }
   }

   private void refreshConfigCacheSafely() {
      try {
         this.configCacheLoaderUtils.loadConfigCache();
      } catch (Exception var2) {
         log.warn("同播用户配置已清理，但刷新配置缓存失败", (Throwable)var2);
      }
   }
}
