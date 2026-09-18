package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.IpAddressUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyRegionBlockRuleMapper;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleCreateRequest;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleRequest;
import com.una.embyhub.model.dto.request.embyregionblock.EmbyRegionBlockRuleUpdateRequest;
import com.una.embyhub.model.dto.response.embyregionblock.EmbyRegionBlockRuleResponse;
import com.una.embyhub.model.entity.EmbyRegionBlockRule;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.service.EmbyRegionBlockRuleService;
import com.una.embyhub.service.EmbyRegionCatalogLoader;
import com.una.embyhub.service.SystemConfigService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Generated;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.core.IpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyRegionBlockRuleServiceImpl implements EmbyRegionBlockRuleService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyRegionBlockRuleServiceImpl.class);
   private final EmbyRegionBlockRuleMapper ruleMapper;
   private final EmbyRegionCatalogLoader catalogLoader;
   private final Ip2regionSearcher ip2regionSearcher;
   private final SystemConfigService systemConfigService;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<EmbyRegionBlockRuleResponse> select(MybatisPlusPage<EmbyRegionBlockRuleRequest> page) {
      MybatisPlusPage<EmbyRegionBlockRuleRequest> safePage = page == null ? new MybatisPlusPage<>() : page;
      EmbyRegionBlockRuleRequest request = safePage.getObject();
      List<EmbyRegionBlockRule> persisted = this.ruleMapper.selectList(Wrappers.lambdaQuery(EmbyRegionBlockRule.class));
      Map<String, EmbyRegionBlockRule> persistedByCode = new LinkedHashMap<>();
      if (persisted != null) {
         for (EmbyRegionBlockRule rule : persisted) {
            if (StringUtils.hasText(rule.getRuleCode())) {
               persistedByCode.put(rule.getRuleCode(), rule);
            }
         }
      }

      List<EmbyRegionCatalogLoader.CatalogRule> catalogRules = this.catalogLoader.rules();
      Set<String> catalogCodes = catalogRules.stream().map(EmbyRegionCatalogLoader.CatalogRule::ruleCode).collect(Collectors.toSet());
      List<EmbyRegionBlockRuleResponse> combined = new ArrayList<>();
      if (persisted != null) {
         persisted.stream().filter(this::isCustomRule).map(this::toCustomResponse).forEach(combined::add);
      }

      catalogRules.stream().map(rulex -> this.toResponse(rulex, persistedByCode.get(rulex.ruleCode()))).forEach(combined::add);
      List<EmbyRegionBlockRuleResponse> filtered = combined.stream()
         .filter(response -> Boolean.TRUE.equals(response.getCustom()) || catalogCodes.contains(response.getRuleCode()))
         .filter(this.buildFilter(request))
         .toList();
      long current = Math.max(1L, safePage.getCurrent());
      long size = safePage.getSize() > 0L ? safePage.getSize() : 10L;
      long startLong = (current - 1L) * size;
      List<EmbyRegionBlockRuleResponse> records = List.of();
      if (startLong >= 0L && startLong < (long)filtered.size()) {
         int start = Math.toIntExact(startLong);
         int end = (int)Math.min((long)filtered.size(), startLong + size);
         records = new ArrayList<>(filtered.subList(start, end));
      }

      Page<EmbyRegionBlockRuleResponse> result = new Page<>(current, size, (long)filtered.size());
      result.setRecords(records);
      result.setOrders(safePage.getOrders());
      return result;
   }

   @Override
   public void createCustom(EmbyRegionBlockRuleCreateRequest request) {
      if (request == null) {
         throw this.badRequest("自定义地区规则不能为空");
      } else {
         String ruleType = this.upper(request.getRuleType());
         if (!"COUNTRY".equals(ruleType) && !"PROVINCE".equals(ruleType)) {
            throw this.badRequest("地区类型仅支持国家/地区或省/州");
         } else {
            String country = EmbyRegionCatalogLoader.normalize(request.getCountry());
            if (country == null) {
               throw this.badRequest("国家/地区不能为空");
            } else {
               String province = EmbyRegionCatalogLoader.normalize(request.getProvince());
               if ("PROVINCE".equals(ruleType) && province == null) {
                  throw this.badRequest("省/州规则必须填写省/州名称");
               } else {
                  if ("COUNTRY".equals(ruleType)) {
                     province = null;
                  }

                  String catalogCode = "PROVINCE".equals(ruleType)
                     ? EmbyRegionCatalogLoader.provinceRuleCode(country, province)
                     : EmbyRegionCatalogLoader.countryRuleCode(country);
                  if (this.catalogLoader.rules().stream().anyMatch(rule -> rule.ruleCode().equals(catalogCode))) {
                     throw this.badRequest("该地区已在内置规则中，可直接搜索并启用");
                  } else {
                     LambdaQueryWrapper<EmbyRegionBlockRule> duplicateQuery = Wrappers.lambdaQuery(EmbyRegionBlockRule.class)
                        .eq(EmbyRegionBlockRule::getRuleType, ruleType)
                        .eq(EmbyRegionBlockRule::getCountry, country);
                     if (province == null) {
                        duplicateQuery.isNull(EmbyRegionBlockRule::getProvince);
                     } else {
                        duplicateQuery.eq(EmbyRegionBlockRule::getProvince, province);
                     }

                     if (this.ruleMapper.selectCount(duplicateQuery) > 0L) {
                        throw this.badRequest("该自定义地区规则已存在");
                     } else {
                        EmbyRegionBlockRule entity = new EmbyRegionBlockRule();
                        entity.setRuleCode("CUSTOM:" + UUID.randomUUID());
                        entity.setRuleType(ruleType);
                        entity.setCountry(country);
                        entity.setProvince(province);
                        entity.setDisplayName(province == null ? country : country + " / " + province);
                        entity.setEnabled(Boolean.TRUE.equals(request.getEnabled()) ? 1 : 0);
                        this.ruleMapper.insert(entity);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void update(EmbyRegionBlockRuleUpdateRequest request) {
      if (request != null && StringUtils.hasText(request.getRuleCode()) && request.getEnabled() != null) {
         String ruleCode = request.getRuleCode().trim();
         EmbyRegionCatalogLoader.CatalogRule catalogRule = this.catalogLoader
            .rules()
            .stream()
            .filter(rule -> rule.ruleCode().equals(ruleCode))
            .findFirst()
            .orElse(null);
         EmbyRegionBlockRule entity = this.ruleMapper
            .selectOne(Wrappers.lambdaQuery(EmbyRegionBlockRule.class).eq(EmbyRegionBlockRule::getRuleCode, ruleCode).last("LIMIT 1"));
         if (entity == null && catalogRule == null) {
            throw this.badRequest("地区规则不存在");
         } else if (entity == null) {
            entity = new EmbyRegionBlockRule();
            this.applyCatalogRule(entity, catalogRule);
            entity.setEnabled(Boolean.TRUE.equals(request.getEnabled()) ? 1 : 0);
            this.ruleMapper.insert(entity);
         } else {
            if (catalogRule != null) {
               this.applyCatalogRule(entity, catalogRule);
            } else if (!this.isCustomRule(entity)) {
               throw this.badRequest("地区规则不存在");
            }

            entity.setEnabled(Boolean.TRUE.equals(request.getEnabled()) ? 1 : 0);
            this.ruleMapper.updateById(entity);
         }
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      }
   }

   @Override
   public void deleteCustom(Long id) {
      if (id == null) {
         throw this.badRequest("自定义地区规则ID不能为空");
      } else {
         EmbyRegionBlockRule entity = this.ruleMapper.selectById(id);
         if (!this.isCustomRule(entity)) {
            throw this.badRequest("只能删除自定义地区规则");
         } else {
            this.ruleMapper.deleteById(id);
         }
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public boolean isEnabled() {
      String value = this.configCacheLoaderUtils.getConfigValue("emby_region_block_enabled");
      return "true".equalsIgnoreCase(value) || "1".equals(value);
   }

   @Override
   public void updateEnabled(boolean enabled) {
      SystemConfig config = this.systemConfigService
         .getOne(Wrappers.lambdaQuery(SystemConfig.class).eq(SystemConfig::getConfigKey, "emby_region_block_enabled"), false);
      if (config == null) {
         config = new SystemConfig();
         config.setName("Emby 地区拦截");
         config.setConfigKey("emby_region_block_enabled");
         config.setDescription("是否根据 Emby webhook 会话 IP 归属地禁用用户");
         config.setIsUpdate(1);
      }

      config.setConfigValue(Boolean.toString(enabled));
      config.setIsEnabled(enabled ? 1 : 0);
      this.systemConfigService.saveOrUpdate(config);
      this.configCacheLoaderUtils.refreshCache();
   }

   @Override
   public long catalogRuleCount() {
      return (long)this.catalogLoader.rules().size();
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public long enabledRuleCount() {
      Long count = this.ruleMapper.selectCount(Wrappers.lambdaQuery(EmbyRegionBlockRule.class).eq(EmbyRegionBlockRule::getEnabled, Integer.valueOf(1)));
      return count == null ? 0L : count;
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Optional<EmbyRegionBlockRuleService.RegionMatch> match(String remoteEndPoint) {
      if (!this.isEnabled()) {
         return Optional.empty();
      } else {
         Optional<EmbyRegionBlockRuleService.Location> locationOptional = this.resolveLocation(remoteEndPoint);
         if (locationOptional.isEmpty()) {
            return Optional.empty();
         } else {
            EmbyRegionBlockRuleService.Location location = locationOptional.get();
            LambdaQueryWrapper<EmbyRegionBlockRule> ruleQuery = Wrappers.lambdaQuery(EmbyRegionBlockRule.class)
               .eq(EmbyRegionBlockRule::getEnabled, Integer.valueOf(1))
               .eq(EmbyRegionBlockRule::getCountry, location.country());
            if (StringUtils.hasText(location.province())) {
               ruleQuery.and(query -> query.isNull(EmbyRegionBlockRule::getProvince).or().eq(EmbyRegionBlockRule::getProvince, location.province()));
            } else {
               ruleQuery.isNull(EmbyRegionBlockRule::getProvince);
            }

            List<EmbyRegionBlockRule> rules = this.ruleMapper.selectList(ruleQuery);
            if (rules != null && !rules.isEmpty()) {
               if (StringUtils.hasText(location.province())) {
                  Optional<EmbyRegionBlockRule> provinceMatch = rules.stream()
                     .filter(rule -> "PROVINCE".equals(rule.getRuleType()))
                     .filter(rule -> location.province().equals(rule.getProvince()))
                     .findFirst();
                  if (provinceMatch.isPresent()) {
                     EmbyRegionBlockRule matched = provinceMatch.get();
                     return Optional.of(new EmbyRegionBlockRuleService.RegionMatch(matched.getRuleCode(), matched.getDisplayName(), location));
                  }
               }

               return rules.stream()
                  .filter(rule -> "COUNTRY".equals(rule.getRuleType()))
                  .filter(rule -> rule.getProvince() == null)
                  .findFirst()
                  .map(matchedx -> new EmbyRegionBlockRuleService.RegionMatch(matchedx.getRuleCode(), matchedx.getDisplayName(), location));
            } else {
               return Optional.empty();
            }
         }
      }
   }

   Optional<EmbyRegionBlockRuleService.Location> resolveLocation(String remoteEndPoint) {
      Optional<IpAddressUtils.ParsedIp> ip = IpAddressUtils.parseLiteral(remoteEndPoint);
      if (!ip.isEmpty() && !ip.get().privateOrLocal()) {
         try {
            IpInfo ipInfo = IpAddressUtils.safeLookup(this.ip2regionSearcher, ip.get().address()).orElse(null);
            String country = EmbyRegionCatalogLoader.normalize(ipInfo == null ? null : ipInfo.getCountry());
            if (country == null) {
               return Optional.empty();
            } else {
               String province = EmbyRegionCatalogLoader.normalize(ipInfo.getProvince());
               String city = EmbyRegionCatalogLoader.normalize(ipInfo.getCity());
               return Optional.of(new EmbyRegionBlockRuleService.Location(ip.get().address(), country, province, city));
            }
         } catch (Exception var7) {
            log.warn("地区拦截 IP 归属地解析失败，按未命中处理: endpoint={}", remoteEndPoint);
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   private Predicate<EmbyRegionBlockRuleResponse> buildFilter(EmbyRegionBlockRuleRequest request) {
      if (request == null) {
         return ignored -> true;
      } else {
         String keyword = this.lower(request.getKeyword());
         String ruleType = this.upper(request.getRuleType());
         String country = this.lower(request.getCountry());
         String province = this.lower(request.getProvince());
         return response -> (
                  keyword == null
                     || this.containsIgnoreCase(response.getRuleCode(), keyword)
                     || this.containsIgnoreCase(response.getDisplayName(), keyword)
                     || this.containsIgnoreCase(response.getCountry(), keyword)
                     || this.containsIgnoreCase(response.getProvince(), keyword)
               )
               && (ruleType == null || ruleType.equals(response.getRuleType()))
               && (country == null || this.containsIgnoreCase(response.getCountry(), country))
               && (province == null || this.containsIgnoreCase(response.getProvince(), province))
               && (request.getEnabled() == null || request.getEnabled().equals(response.getEnabled()));
      }
   }

   private boolean containsIgnoreCase(String value, String normalizedNeedle) {
      String normalizedValue = this.lower(value);
      return normalizedValue != null && normalizedValue.contains(normalizedNeedle);
   }

   private String lower(String value) {
      return StringUtils.hasText(value) ? value.trim().toLowerCase(Locale.ROOT) : null;
   }

   private String upper(String value) {
      return StringUtils.hasText(value) ? value.trim().toUpperCase(Locale.ROOT) : null;
   }

   private void applyCatalogRule(EmbyRegionBlockRule entity, EmbyRegionCatalogLoader.CatalogRule catalogRule) {
      entity.setRuleCode(catalogRule.ruleCode());
      entity.setRuleType(catalogRule.ruleType());
      entity.setCountry(catalogRule.country());
      entity.setProvince(catalogRule.province());
      entity.setDisplayName(catalogRule.displayName());
   }

   private EmbyRegionBlockRuleResponse toResponse(EmbyRegionCatalogLoader.CatalogRule catalogRule, EmbyRegionBlockRule persisted) {
      EmbyRegionBlockRuleResponse response = new EmbyRegionBlockRuleResponse();
      response.setRuleCode(catalogRule.ruleCode());
      response.setRuleType(catalogRule.ruleType());
      response.setCountry(catalogRule.country());
      response.setProvince(catalogRule.province());
      response.setDisplayName(catalogRule.displayName());
      response.setEnabled(persisted != null && Integer.valueOf(1).equals(persisted.getEnabled()));
      response.setPersisted(persisted != null);
      response.setCustom(false);
      if (persisted != null) {
         response.setId(persisted.getId());
         response.setCreateDatetime(persisted.getCreateDatetime());
         response.setUpdateDatetime(persisted.getUpdateDatetime());
      }

      return response;
   }

   private EmbyRegionBlockRuleResponse toCustomResponse(EmbyRegionBlockRule entity) {
      EmbyRegionBlockRuleResponse response = new EmbyRegionBlockRuleResponse();
      response.setId(entity.getId());
      response.setRuleCode(entity.getRuleCode());
      response.setRuleType(entity.getRuleType());
      response.setCountry(entity.getCountry());
      response.setProvince(entity.getProvince());
      response.setDisplayName(entity.getDisplayName());
      response.setEnabled(Integer.valueOf(1).equals(entity.getEnabled()));
      response.setPersisted(true);
      response.setCustom(true);
      response.setCreateDatetime(entity.getCreateDatetime());
      response.setUpdateDatetime(entity.getUpdateDatetime());
      return response;
   }

   private boolean isCustomRule(EmbyRegionBlockRule entity) {
      return entity != null && StringUtils.hasText(entity.getRuleCode()) && entity.getRuleCode().startsWith("CUSTOM:");
   }

   private BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }

   @Generated
   public EmbyRegionBlockRuleServiceImpl(
      final EmbyRegionBlockRuleMapper ruleMapper,
      final EmbyRegionCatalogLoader catalogLoader,
      final Ip2regionSearcher ip2regionSearcher,
      final SystemConfigService systemConfigService,
      final ConfigCacheLoaderUtils configCacheLoaderUtils
   ) {
      this.ruleMapper = ruleMapper;
      this.catalogLoader = catalogLoader;
      this.ip2regionSearcher = ip2regionSearcher;
      this.systemConfigService = systemConfigService;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
   }
}
