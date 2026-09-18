package com.una.embyhub.config.common.utils;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.foam.properties.EmbyProperties;
import com.una.embyhub.mapper.EmbyInfoMapper;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import embyclient.ApiClient;
import embyclient.Configuration;
import embyclient.auth.ApiKeyAuth;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmbyInfoCacheManagerUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyInfoCacheManagerUtils.class);
   private final EmbyInfoMapper embyInfoMapper;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   @Autowired
   private EmbyProperties embyProperties;
   private static final String KEY_ACTIVE_CONFIG = "emby:config:active";
   private static final String KEY_ADMIN_CONFIGS = "emby:config:admin_list";
   private static final String KEY_ENABLED_CONFIGS = "emby:config:enabled_list";
   private static final String KEY_PREFIX_ID = "emby:config:id:";
   private static final String KEY_PREFIX_SERVER_ID = "emby:config:serverid:";

   public EmbyInfoCacheManagerUtils(EmbyInfoMapper embyInfoMapper) {
      this.embyInfoMapper = embyInfoMapper;
   }

   private Object safeGetFromRedis(String key) {
      try {
         return this.redisTemplate.opsForValue().get(key);
      } catch (Exception var3) {
         LoggerFactory.getLogger(EmbyInfoCacheManagerUtils.class)
            .warn("Redis read failed for key: " + key + ", treating as cache miss. Error: " + var3.getMessage());
         return null;
      }
   }

   public EmbyInfoCacheManagerUtils.EmbyServerConfig getConfig() {
      EmbyInfoCacheManagerUtils.EmbyServerConfig config = (EmbyInfoCacheManagerUtils.EmbyServerConfig)this.safeGetFromRedis("emby:config:active");
      if (config == null) {
         config = this.loadConfig();
         if (config != null) {
            this.redisTemplate.opsForValue().set("emby:config:active", config);
         }
      }

      return config;
   }

   public EmbyInfoCacheManagerUtils.EmbyServerConfig getConfig(EmbyUser embyUser) {
      if (embyUser == null) {
         return this.getConfig();
      } else if (embyUser.getIsAdmin() != null && embyUser.getIsAdmin() == 1) {
         List<EmbyInfoCacheManagerUtils.EmbyServerConfig> adminConfigs = this.getAdminConfigs();
         return !adminConfigs.isEmpty() ? adminConfigs.get(0) : this.getConfig();
      } else {
         Long embyInfoId = embyUser.getEmbyInfoId();
         if (embyInfoId == null) {
            return this.getConfig();
         } else {
            String key = "emby:config:id:" + embyInfoId;
            EmbyInfoCacheManagerUtils.EmbyServerConfig config = (EmbyInfoCacheManagerUtils.EmbyServerConfig)this.safeGetFromRedis(key);
            if (config == null) {
               config = this.loadConfigById(embyInfoId);
               if (config != null) {
                  this.redisTemplate.opsForValue().set(key, config);
               }
            }

            return config != null ? config : this.getConfig();
         }
      }
   }

   public EmbyInfoCacheManagerUtils.EmbyServerConfig getRequiredConfig() {
      EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.getConfig();
      if (config != null && StringUtils.hasText(config.url()) && StringUtils.hasText(config.apiKey())) {
         return config;
      } else {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_CONFIGURED);
      }
   }

   public EmbyInfoCacheManagerUtils.EmbyServerConfig getRequiredConfig(EmbyUser embyUser) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.getConfig(embyUser);
      if (config != null && StringUtils.hasText(config.url()) && StringUtils.hasText(config.apiKey())) {
         return config;
      } else {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_CONFIGURED);
      }
   }

   public EmbyInfoCacheManagerUtils.EmbyServerConfig getRequiredConfigByServerId(String serverId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.getConfigByServerId(serverId);
      if (config != null && StringUtils.hasText(config.url()) && StringUtils.hasText(config.apiKey())) {
         return config;
      } else {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_CONFIGURED);
      }
   }

   public EmbyInfoCacheManagerUtils.EmbyServerConfig getConfigByServerId(String serverId) {
      if (!StringUtils.hasText(serverId)) {
         return null;
      } else {
         String key = "emby:config:serverid:" + serverId;
         EmbyInfoCacheManagerUtils.EmbyServerConfig config = (EmbyInfoCacheManagerUtils.EmbyServerConfig)this.safeGetFromRedis(key);
         if (config == null) {
            config = this.loadConfigByServerId(serverId);
            if (config != null) {
               this.redisTemplate.opsForValue().set(key, config);
            }
         }

         return config;
      }
   }

   public List<EmbyInfoCacheManagerUtils.EmbyServerConfig> getAdminConfigs() {
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> configs = (List<EmbyInfoCacheManagerUtils.EmbyServerConfig>)this.safeGetFromRedis(
         "emby:config:admin_list"
      );
      if (configs == null) {
         configs = this.loadAdminConfigs();
         if (configs != null) {
            this.redisTemplate.opsForValue().set("emby:config:admin_list", configs);
         } else {
            configs = Collections.emptyList();
         }
      }

      return configs;
   }

   public List<EmbyInfoCacheManagerUtils.EmbyServerConfig> getEnabledConfigs() {
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> configs = (List<EmbyInfoCacheManagerUtils.EmbyServerConfig>)this.safeGetFromRedis(
         "emby:config:enabled_list"
      );
      if (configs == null) {
         configs = this.loadEnabledConfigs();
         if (configs != null) {
            this.redisTemplate.opsForValue().set("emby:config:enabled_list", configs);
         } else {
            configs = Collections.emptyList();
         }
      }

      return configs;
   }

   public EmbyInfoCacheManagerUtils.EmbyServerConfig getRequiredConfigById(Long embyInfoId) {
      if (embyInfoId == null) {
         return this.getRequiredConfig();
      } else {
         EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.loadConfigById(embyInfoId);
         if (config != null && StringUtils.hasText(config.url()) && StringUtils.hasText(config.apiKey())) {
            return config;
         } else {
            throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_CONFIGURED);
         }
      }
   }

   public void refresh() {
      Set<String> keys = this.redisTemplate.keys("emby:config:*");
      if (keys != null && !keys.isEmpty()) {
         this.redisTemplate.delete(keys);
      }
   }

   public void applyTo(ApiClient apiClient) {
      if (apiClient != null) {
         EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.getConfig();
         this.applyTo(apiClient, config);
      }
   }

   public void applyTo(ApiClient apiClient, EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      if (apiClient != null && config != null) {
         apiClient.setBasePath(config.url());
         ApiKeyAuth apiKeyAuth = (ApiKeyAuth)apiClient.getAuthentication("apikeyauth");
         if (apiKeyAuth != null) {
            apiKeyAuth.setApiKey(config.apiKey());
         }

         Configuration.setDefaultApiClient(apiClient);
      }
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig loadConfig() {
      EmbyInfo embyInfo = new LambdaQueryChainWrapper<>(this.embyInfoMapper)
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(EmbyInfo::getSpread, Integer.valueOf(1))
         .last("limit 1")
         .one();
      if (embyInfo == null) {
         embyInfo = new EmbyInfo();
      }

      return new EmbyInfoCacheManagerUtils.EmbyServerConfig(
         embyInfo.getId(),
         embyInfo.getEmbyUrl(),
         embyInfo.getEmbyApikey(),
         embyInfo.getCopyfromuserid(),
         embyInfo.getServerName(),
         embyInfo.getAdminQueryUserid()
      );
   }

   private List<EmbyInfoCacheManagerUtils.EmbyServerConfig> loadAdminConfigs() {
      List<EmbyInfo> embyInfos = new LambdaQueryChainWrapper<>(this.embyInfoMapper)
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(EmbyInfo::getSpread, Integer.valueOf(1))
         .list();
      return embyInfos != null && !embyInfos.isEmpty()
         ? embyInfos.stream()
            .map(
               info -> new EmbyInfoCacheManagerUtils.EmbyServerConfig(
                     info.getId(), info.getEmbyUrl(), info.getEmbyApikey(), info.getCopyfromuserid(), info.getServerName(), info.getAdminQueryUserid()
                  )
            )
            .collect(Collectors.toList())
         : Collections.emptyList();
   }

   private List<EmbyInfoCacheManagerUtils.EmbyServerConfig> loadEnabledConfigs() {
      List<EmbyInfo> embyInfos = new LambdaQueryChainWrapper<>(this.embyInfoMapper)
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .list();
      return embyInfos != null && !embyInfos.isEmpty()
         ? embyInfos.stream()
            .map(
               info -> new EmbyInfoCacheManagerUtils.EmbyServerConfig(
                     info.getId(), info.getEmbyUrl(), info.getEmbyApikey(), info.getCopyfromuserid(), info.getServerName(), info.getAdminQueryUserid()
                  )
            )
            .collect(Collectors.toList())
         : Collections.emptyList();
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig loadConfigById(Long embyInfoId) {
      EmbyInfo embyInfo = new LambdaQueryChainWrapper<>(this.embyInfoMapper)
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(EmbyInfo::getId, embyInfoId)
         .last("limit 1")
         .one();
      return embyInfo == null
         ? null
         : new EmbyInfoCacheManagerUtils.EmbyServerConfig(
            embyInfo.getId(),
            embyInfo.getEmbyUrl(),
            embyInfo.getEmbyApikey(),
            embyInfo.getCopyfromuserid(),
            embyInfo.getServerName(),
            embyInfo.getAdminQueryUserid()
         );
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig loadConfigByServerId(String serverId) {
      EmbyInfo embyInfo = new LambdaQueryChainWrapper<>(this.embyInfoMapper)
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(EmbyInfo::getEmbyServerId, serverId)
         .last("limit 1")
         .one();
      return embyInfo == null
         ? null
         : new EmbyInfoCacheManagerUtils.EmbyServerConfig(
            embyInfo.getId(),
            embyInfo.getEmbyUrl(),
            embyInfo.getEmbyApikey(),
            embyInfo.getCopyfromuserid(),
            embyInfo.getServerName(),
            embyInfo.getAdminQueryUserid()
         );
   }

   @JsonTypeInfo(
      use = Id.CLASS
   )
   public static record EmbyServerConfig(Long id, String url, String apiKey, String copyfromuserid, String serverName, String adminQueryUserid)
      implements Serializable {
   }
}
