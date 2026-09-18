package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.SystemConfigMapper;
import com.una.embyhub.model.dto.request.systemconfig.SystemConfigRequest;
import com.una.embyhub.model.dto.request.systemconfig.SystemConfigUpdate;
import com.una.embyhub.model.dto.response.systemconfig.SystemConfigResponse;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.service.SystemConfigService;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {
   private static final String SECRET_VALUE_MASK = "******";
   private static final String LEGACY_LICENSE_CONFIG_KEY = "license_admin_license_code";
   private static final Set<String> SENSITIVE_CONFIG_KEYS = Set.of("rose_admin_password", "movie_pilot_config", "rose_api_cookie");
   private static final Set<String> PUBLIC_ENABLED_CONFIG_KEYS = Set.of(
      "invitation_register_enabled", "card_register_enabled", "login_desktop_animation_enabled", "line_speed_test_enabled"
   );
   private static final Set<String> ALLOWED_ORDER_COLUMNS = Set.of("id", "config_key", "is_enabled", "is_update", "create_datetime", "update_datetime", "name");
   private static final long MAX_PAGE_SIZE = 200L;
   private static final String MOVIE_PILOT_URL_FIELD = "url";
   private static final String MOVIE_PILOT_USERNAME_FIELD = "username";
   private static final String MOVIE_PILOT_PASSWORD_FIELD = "password";
   private static final String MOVIE_PILOT_OTP_PASSWORD_FIELD = "otpPassword";
   private static final Set<String> MOVIE_PILOT_STANDARD_FIELDS = Set.of("url", "username", "password", "otpPassword");
   private static final Set<String> MOVIE_PILOT_SECRET_FIELDS = Set.of("password", "otpPassword");
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;

   @Override
   public Page<SystemConfigResponse> select(MybatisPlusPage<SystemConfigRequest> page) {
      if (page != null && page.getObject() != null) {
         SystemConfigRequest request = page.getObject();
         if (request.getDescription() != null && request.getDescription().length() > 128) {
            throw new BizException("配置说明筛选内容过长");
         } else if (request.getIsEnabled() != null && request.getIsEnabled() != 0 && request.getIsEnabled() != 1) {
            throw new BizException("配置启用状态无效");
         } else {
            page.setCurrent(Math.max(1L, page.getCurrent()));
            page.setSize(Math.max(1L, Math.min(200L, page.getSize())));
            page.setOrders(this.validateOrders(page.getOrders()));
            QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
            queryWrapper.ne("config_key", LEGACY_LICENSE_CONFIG_KEY);
            Page<SystemConfigResponse> systemConfigResponsePage = MpConvert.page(
               queryWrapper, this.getBaseMapper(), SystemConfigResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
            );
            systemConfigResponsePage.getRecords().forEach(this::maskSensitiveConfigValue);
            return systemConfigResponsePage;
         }
      } else {
         throw new BizException("分页请求格式有误");
      }
   }

   @Override
   public void updateSystemConfig(SystemConfigUpdate systemConfigUpdate) {
      SystemConfig systemConfigCustom = this.getById(systemConfigUpdate.getId());
      if (systemConfigCustom == null) {
         throw new BizException(ResponseStatusEnum.CONFIG_KEY_NOT_EXIST);
      } else if (this.isRemovedConfig(systemConfigCustom.getConfigKey())) {
         throw new BizException(ResponseStatusEnum.CONFIG_KEY_NOT_EXIST);
      } else {
         if (this.isMoviePilotConfig(systemConfigCustom.getConfigKey())) {
            systemConfigUpdate.setConfigValue(this.mergeMoviePilotMaskedConfigValue(systemConfigUpdate.getConfigValue(), systemConfigCustom.getConfigValue()));
         } else if (this.isSensitiveConfig(systemConfigCustom.getConfigKey())
            && this.shouldPreserveSensitiveValue(systemConfigUpdate.getConfigValue(), systemConfigCustom.getConfigValue())) {
            systemConfigUpdate.setConfigValue(systemConfigCustom.getConfigValue());
         }

         this.normalizeTelegramRegisterConfigValue(systemConfigCustom, systemConfigUpdate);
         SystemConfig systemConfig = BeanUtils.convert(systemConfigUpdate, SystemConfig.class);
         systemConfig.setIsUpdate(systemConfigCustom.getIsUpdate());
         this.updateById(systemConfig);
         this.configCacheLoaderUtils.loadConfigCache();
      }
   }

   @Override
   public boolean isPublicEnabled(String configKey) {
      String normalized = this.normalizedConfigKey(configKey);
      if (!PUBLIC_ENABLED_CONFIG_KEYS.contains(normalized)) {
         throw new BizException("该配置项不允许公开查询");
      } else {
         String configValue = this.configCacheLoaderUtils.getConfigValue(normalized);
         return configValue != null;
      }
   }

   private List<OrderItem> validateOrders(List<OrderItem> orders) {
      return orders != null && !orders.isEmpty() ? orders.stream().filter(Objects::nonNull).map(order -> {
         String column = this.normalizedConfigKey(order.getColumn());
         if (!ALLOWED_ORDER_COLUMNS.contains(column)) {
            throw new BizException("系统配置排序字段无效");
         } else {
            return order.isAsc() ? OrderItem.asc(column) : OrderItem.desc(column);
         }
      }).toList() : List.of();
   }

   @Override
   public String getConfigValue(String configKey) {
      return this.isRemovedConfig(configKey) || this.isSensitiveConfig(configKey) ? "" : this.configCacheLoaderUtils.getConfigValue(configKey);
   }

   private void maskSensitiveConfigValue(SystemConfigResponse response) {
      if (response != null && this.isSensitiveConfig(response.getConfigKey())) {
         if (this.isMoviePilotConfig(response.getConfigKey())) {
            response.setConfigValue(this.maskMoviePilotConfigValue(response.getConfigValue()));
         } else {
            response.setConfigValue(StringUtils.hasText(response.getConfigValue()) ? "******" : "");
         }
      }
   }

   private String maskMoviePilotConfigValue(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         try {
            JSONObject parsed = JSONObject.parseObject(value);
            if (parsed == null) {
               return "******";
            } else {
               JSONObject masked = new JSONObject();
               this.putMoviePilotVisibleField(masked, parsed, "url");
               this.putMoviePilotVisibleField(masked, parsed, "username");
               this.putMoviePilotSecretField(masked, parsed, "password");
               this.putMoviePilotSecretField(masked, parsed, "otpPassword");
               parsed.forEach((key, fieldValue) -> {
                  if (!MOVIE_PILOT_STANDARD_FIELDS.contains(key)) {
                     masked.put(key, this.maskMoviePilotExtraField(key, fieldValue));
                  }
               });
               return masked.toJSONString();
            }
         } catch (Exception var4) {
            return "******";
         }
      }
   }

   private void putMoviePilotVisibleField(JSONObject target, JSONObject source, String field) {
      target.put(field, this.defaultString(source.getString(field)));
   }

   private void putMoviePilotSecretField(JSONObject target, JSONObject source, String field) {
      target.put(field, StringUtils.hasText(source.getString(field)) ? "******" : "");
   }

   private Object maskMoviePilotExtraField(String key, Object value) {
      if (!this.isSensitiveConfig(key)) {
         return value;
      } else {
         return StringUtils.hasText(value == null ? "" : String.valueOf(value)) ? "******" : "";
      }
   }

   private String mergeMoviePilotMaskedConfigValue(String nextValue, String currentValue) {
      if (this.shouldPreserveSensitiveValue(nextValue, currentValue)) {
         return currentValue;
      } else if (!StringUtils.hasText(nextValue)) {
         return nextValue;
      } else {
         try {
            JSONObject nextConfig = JSONObject.parseObject(nextValue);
            if (nextConfig == null) {
               return nextValue;
            } else {
               JSONObject currentConfig = StringUtils.hasText(currentValue) ? JSONObject.parseObject(currentValue) : new JSONObject();
               if (currentConfig == null) {
                  currentConfig = new JSONObject();
               }

               for (String field : MOVIE_PILOT_SECRET_FIELDS) {
                  this.preserveMaskedMoviePilotSecretField(nextConfig, currentConfig, field);
               }

               for (String field : nextConfig.keySet()) {
                  if (!MOVIE_PILOT_SECRET_FIELDS.contains(field) && this.isSensitiveConfig(field)) {
                     this.preserveMaskedMoviePilotSecretField(nextConfig, currentConfig, field);
                  }
               }

               return nextConfig.toJSONString();
            }
         } catch (Exception var7) {
            return nextValue;
         }
      }
   }

   private void preserveMaskedMoviePilotSecretField(JSONObject nextConfig, JSONObject currentConfig, String field) {
      String nextFieldValue = nextConfig.getString(field);
      String currentFieldValue = currentConfig.getString(field);
      if (StringUtils.hasText(currentFieldValue) && (!StringUtils.hasText(nextFieldValue) || "******".equals(nextFieldValue))) {
         nextConfig.put(field, currentFieldValue);
      }
   }

   private String defaultString(String value) {
      return value == null ? "" : value;
   }

   private boolean isSensitiveConfig(String configKey) {
      if (!StringUtils.hasText(configKey)) {
         return false;
      } else {
         String normalized = configKey.trim().toLowerCase(Locale.ROOT);
         return SENSITIVE_CONFIG_KEYS.contains(normalized)
            || normalized.contains("password")
            || normalized.contains("secret")
            || normalized.contains("token")
            || normalized.contains("cookie")
            || normalized.contains("api_key")
            || normalized.contains("apikey");
      }
   }

   private boolean isRemovedConfig(String configKey) {
      return LEGACY_LICENSE_CONFIG_KEY.equals(this.normalizedConfigKey(configKey));
   }

   private boolean isMoviePilotConfig(String configKey) {
      return "movie_pilot_config".equals(this.normalizedConfigKey(configKey));
   }

   private String normalizedConfigKey(String configKey) {
      return StringUtils.hasText(configKey) ? configKey.trim().toLowerCase(Locale.ROOT) : "";
   }

   private boolean shouldPreserveSensitiveValue(String nextValue, String currentValue) {
      return StringUtils.hasText(currentValue) && (!StringUtils.hasText(nextValue) || "******".equals(nextValue));
   }

   private void normalizeTelegramRegisterConfigValue(SystemConfig current, SystemConfigUpdate update) {
      if (current != null && update != null && "telegram_bot_register_enabled".equals(current.getConfigKey())) {
         JSONObject currentConfig = this.parseTelegramRegisterConfig(current.getConfigValue());
         JSONObject nextConfig = this.parseTelegramRegisterConfig(update.getConfigValue());
         int currentUsedCount = currentConfig.getIntValue("usedCount");
         int nextUsedCount = nextConfig.getIntValue("usedCount");
         boolean resetUsedCount = !Objects.equals(current.getIsEnabled(), update.getIsEnabled())
            || Integer.valueOf(1).equals(current.getIsEnabled())
               && Integer.valueOf(1).equals(update.getIsEnabled())
               && currentUsedCount > 0
               && nextUsedCount == 0;
         nextConfig.put("usedCount", Integer.valueOf(resetUsedCount ? 0 : currentUsedCount));
         update.setConfigValue(nextConfig.toJSONString());
      }
   }

   private JSONObject parseTelegramRegisterConfig(String value) {
      JSONObject config = new JSONObject();
      config.put("defaultDays", Integer.valueOf(0));
      config.put("maxCount", Integer.valueOf(0));
      config.put("usedCount", Integer.valueOf(0));
      if (!StringUtils.hasText(value)) {
         return config;
      } else {
         String text = value.trim();
         if (text.startsWith("{")) {
            try {
               JSONObject parsed = JSONObject.parseObject(text);
               config.put("defaultDays", Integer.valueOf(this.parseNonNegativeInt(parsed.getString("defaultDays"))));
               config.put("maxCount", Integer.valueOf(this.parseNonNegativeInt(parsed.getString("maxCount"))));
               config.put("usedCount", Integer.valueOf(this.parseNonNegativeInt(parsed.getString("usedCount"))));
               return config;
            } catch (Exception var5) {
            }
         }

         config.put("defaultDays", Integer.valueOf(this.parseNonNegativeInt(text)));
         return config;
      }
   }

   private int parseNonNegativeInt(String value) {
      if (!StringUtils.hasText(value)) {
         return 0;
      } else {
         try {
            return Math.max(0, Integer.parseInt(value.trim()));
         } catch (NumberFormatException var3) {
            return 0;
         }
      }
   }
}
