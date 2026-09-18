package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyBlockKeywordMapper;
import com.una.embyhub.model.dto.request.embyblockkeyword.EmbyBlockKeywordRequest;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyBlockKeywordResponse;
import com.una.embyhub.model.entity.EmbyBlockKeyword;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.service.EmbyBlockKeywordService;
import com.una.embyhub.service.SystemConfigService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyBlockKeywordServiceImpl extends ServiceImpl<EmbyBlockKeywordMapper, EmbyBlockKeyword> implements EmbyBlockKeywordService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyBlockKeywordServiceImpl.class);
   private static final List<String> DEFAULT_CLIENT_FILTER_PATTERNS = List.of(
      ".*curl.*",
      ".*wget.*",
      ".*python.*",
      ".*spider.*",
      ".*crawler.*",
      ".*scraper.*",
      ".*downloader.*",
      ".*aria2.*",
      ".*youtube-dl.*",
      ".*yt-dlp.*",
      ".*ffmpeg.*",
      ".*vlc.*"
   );
   private final SystemConfigService systemConfigService;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;

   @Override
   public Page<EmbyBlockKeywordResponse> select(MybatisPlusPage<EmbyBlockKeywordRequest> page) {
      EmbyBlockKeywordRequest request = page.getObject();
      LambdaQueryWrapper<EmbyBlockKeyword> queryWrapper = Wrappers.lambdaQuery(EmbyBlockKeyword.class)
         .like(request != null && StringUtils.hasText(request.getKeyword()), EmbyBlockKeyword::getKeyword, request != null ? request.getKeyword() : null)
         .eq(request != null && request.getEnabled() != null, EmbyBlockKeyword::getEnabled, request != null ? request.getEnabled() : null)
         .orderByDesc(EmbyBlockKeyword::getId);
      return MpConvert.page(queryWrapper, this.getBaseMapper(), EmbyBlockKeywordResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public void add(EmbyBlockKeywordRequest request) {
      String keyword = request.getKeyword();
      if (!StringUtils.hasText(keyword)) {
         throw new BizException("关键字不能为空");
      } else {
         String lowerKeyword = keyword.toLowerCase();
         Long count = this.count(Wrappers.lambdaQuery(EmbyBlockKeyword.class).eq(EmbyBlockKeyword::getKeyword, lowerKeyword));
         if (count > 0L) {
            throw new BizException("关键字已存在");
         } else {
            EmbyBlockKeyword entity = new EmbyBlockKeyword();
            entity.setKeyword(lowerKeyword);
            entity.setDescription(request.getDescription());
            entity.setEnabled(request.getEnabled() != null ? request.getEnabled() : 1);
            this.save(entity);
            log.info("新增设备屏蔽关键字: {}", keyword);
         }
      }
   }

   @Override
   public void update(EmbyBlockKeywordRequest request) {
      EmbyBlockKeyword entity = this.getById(request.getId());
      if (entity == null) {
         throw new BizException("关键字不存在");
      } else {
         if (StringUtils.hasText(request.getKeyword())) {
            entity.setKeyword(request.getKeyword().toLowerCase());
         }

         if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
         }

         if (request.getEnabled() != null) {
            entity.setEnabled(request.getEnabled());
         }

         this.updateById(entity);
         log.info("更新设备屏蔽关键字: id={}, keyword={}", request.getId(), entity.getKeyword());
      }
   }

   @Override
   public void delete(Long id) {
      this.removeById(id);
      log.info("删除设备屏蔽关键字: id={}", id);
   }

   @Override
   public List<String> getEnabledKeywords() {
      return this.list(Wrappers.lambdaQuery(EmbyBlockKeyword.class).eq(EmbyBlockKeyword::getEnabled, Integer.valueOf(1)))
         .stream()
         .map(EmbyBlockKeyword::getKeyword)
         .filter(StringUtils::hasText)
         .collect(Collectors.toList());
   }

   @Override
   public List<String> getDefaultClientFilterPatterns() {
      return DEFAULT_CLIENT_FILTER_PATTERNS;
   }

   @Override
   public List<String> getEffectiveClientFilterPatterns() {
      List<String> customKeywords = this.getEnabledKeywords();
      return customKeywords.isEmpty() ? DEFAULT_CLIENT_FILTER_PATTERNS : customKeywords;
   }

   @Override
   public boolean isUsingDefaultClientFilterPatterns() {
      return this.getEnabledKeywords().isEmpty();
   }

   @Override
   public boolean isClientFilterEnabled() {
      return this.isBooleanConfigEnabled("emby_client_filter_enabled");
   }

   @Override
   public void updateClientFilterEnabled(boolean enabled) {
      this.updateBooleanConfig("emby_client_filter_enabled", "Emby 客户端过滤", "是否开启 Emby webhook 非法客户端拦截", enabled);
      log.info("更新 Emby 客户端过滤开关: {}", enabled);
   }

   @Override
   public boolean isClientFilterBlockUserEnabled() {
      return this.isBooleanConfigEnabled("emby_client_filter_block_user_enabled");
   }

   @Override
   public void updateClientFilterBlockUserEnabled(boolean enabled) {
      this.updateBooleanConfig("emby_client_filter_block_user_enabled", "Emby 客户端过滤命中后禁用用户", "是否在 Emby webhook 非法客户端命中后禁用 Emby 用户", enabled);
      log.info("更新 Emby 客户端过滤禁用用户开关: {}", enabled);
   }

   private boolean isBooleanConfigEnabled(String configKey) {
      String enabled = this.configCacheLoaderUtils.getConfigValue(configKey);
      return "true".equalsIgnoreCase(enabled) || "1".equals(enabled);
   }

   private void updateBooleanConfig(String configKey, String name, String description, boolean enabled) {
      SystemConfig config = this.systemConfigService.getOne(Wrappers.lambdaQuery(SystemConfig.class).eq(SystemConfig::getConfigKey, configKey), false);
      if (config == null) {
         config = new SystemConfig();
         config.setName(name);
         config.setConfigKey(configKey);
         config.setDescription(description);
         config.setIsUpdate(1);
      }

      config.setConfigValue(Boolean.toString(enabled));
      config.setIsEnabled(enabled ? 1 : 0);
      this.systemConfigService.saveOrUpdate(config);
      this.configCacheLoaderUtils.refreshCache();
   }

   @Override
   public List<String> getAllKeywords() {
      return this.list().stream().map(EmbyBlockKeyword::getKeyword).collect(Collectors.toList());
   }

   @Override
   public void addIfNotExists(String keyword, String description) {
      if (StringUtils.hasText(keyword)) {
         String lowerKeyword = keyword.toLowerCase();
         Long count = this.count(Wrappers.lambdaQuery(EmbyBlockKeyword.class).eq(EmbyBlockKeyword::getKeyword, lowerKeyword));
         if (count <= 0L) {
            EmbyBlockKeyword entity = new EmbyBlockKeyword();
            entity.setKeyword(lowerKeyword);
            entity.setDescription(description);
            entity.setEnabled(0);
            this.save(entity);
            log.info("自动新增设备屏蔽关键字: {} ({})", lowerKeyword, description);
         }
      }
   }

   @Generated
   public EmbyBlockKeywordServiceImpl(final SystemConfigService systemConfigService, final ConfigCacheLoaderUtils configCacheLoaderUtils) {
      this.systemConfigService = systemConfigService;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
   }
}
