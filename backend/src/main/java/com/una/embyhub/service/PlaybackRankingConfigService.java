package com.una.embyhub.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.mapper.PlaybackRankingConfigMapper;
import com.una.embyhub.model.dto.request.scheduledtask.PlaybackRankingConfigUpdateRequest;
import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingConfigResponse;
import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingConfigSummaryResponse;
import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingUserOptionResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PlaybackRankingConfig;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PlaybackRankingConfigService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PlaybackRankingConfigService.class);
   private static final int MAX_USER_ID_LENGTH = 128;
   private static final int MAX_EXCLUDED_USER_COUNT = 500;
   private final PlaybackRankingConfigMapper configMapper;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final PlaybackRankingUserGateway userGateway;

   @Transactional(
      readOnly = true
   )
   public PlaybackRankingConfigSummaryResponse summary() {
      PlaybackRankingConfigSummaryResponse response = new PlaybackRankingConfigSummaryResponse();
      Map<String, List<String>> result = response.getExcludedUserIdsByServer();
      List<PlaybackRankingConfig> configs = this.configMapper
         .selectList(
            Wrappers.lambdaQuery(PlaybackRankingConfig.class).eq(BaseEntity::getDelFlag, Integer.valueOf(0)).orderByAsc(PlaybackRankingConfig::getEmbyInfoId)
         );
      if (configs != null) {
         for (PlaybackRankingConfig config : configs) {
            if (config.getEmbyInfoId() != null) {
               result.put(String.valueOf(config.getEmbyInfoId()), this.parseUserIds(config.getExcludedUserIds()));
            }
         }
      }

      return response;
   }

   @Transactional(
      readOnly = true
   )
   public PlaybackRankingConfigResponse details(Long embyInfoId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(embyInfoId);
      List<PlaybackRankingUserOptionResponse> users = this.userGateway.listUsers(serverConfig);
      Set<String> validUserIds = users.stream().map(PlaybackRankingUserOptionResponse::getId).collect(Collectors.toCollection(LinkedHashSet::new));
      List<String> selectedIds = new ArrayList<>(this.excludedUserIds(embyInfoId));
      selectedIds.removeIf(userId -> !validUserIds.contains(userId));
      return this.response(serverConfig, selectedIds, users);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PlaybackRankingConfigResponse update(PlaybackRankingConfigUpdateRequest request) {
      if (request == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "排行榜配置不能为空");
      } else {
         List<String> normalizedIds = this.normalizeUserIds(request.getExcludedUserIds());
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(request.getEmbyInfoId());
         List<PlaybackRankingUserOptionResponse> users = this.userGateway.listUsers(serverConfig);
         Set<String> validUserIds = users.stream().map(PlaybackRankingUserOptionResponse::getId).collect(Collectors.toCollection(LinkedHashSet::new));

         for (String userId : normalizedIds) {
            if (!validUserIds.contains(userId)) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "排除用户不属于当前服务器，请刷新用户列表后重试");
            }
         }

         PlaybackRankingConfig config = this.findConfig(serverConfig.id());
         if (config == null) {
            config = new PlaybackRankingConfig();
            config.setEmbyInfoId(serverConfig.id());
            config.setExcludedUserIds(JSON.toJSONString(normalizedIds));
            this.configMapper.insert(config);
         } else {
            config.setExcludedUserIds(JSON.toJSONString(normalizedIds));
            this.configMapper.updateById(config);
         }

         return this.response(serverConfig, normalizedIds, users);
      }
   }

   @Transactional(
      readOnly = true
   )
   public Set<String> excludedUserIds(Long embyInfoId) {
      if (embyInfoId == null) {
         return Set.of();
      } else {
         PlaybackRankingConfig config = this.findConfig(embyInfoId);
         return new LinkedHashSet<>(config == null ? List.of() : this.parseUserIds(config.getExcludedUserIds()));
      }
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig requireServer(Long embyInfoId) {
      if (embyInfoId == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "服务器不能为空");
      } else {
         return this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      }
   }

   private PlaybackRankingConfig findConfig(Long embyInfoId) {
      return this.configMapper
         .selectOne(
            Wrappers.lambdaQuery(PlaybackRankingConfig.class)
               .eq(PlaybackRankingConfig::getEmbyInfoId, embyInfoId)
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .last("LIMIT 1")
         );
   }

   private List<String> normalizeUserIds(List<String> values) {
      LinkedHashSet<String> result = new LinkedHashSet<>();
      if (values == null) {
         return List.of();
      } else if (values.size() > 500) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "单个服务器最多排除500个用户");
      } else {
         for (String value : values) {
            if (!StringUtils.hasText(value)) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "排除用户ID不能为空");
            }

            String normalized = value.trim();
            if (normalized.length() > 128) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "排除用户ID长度无效");
            }

            result.add(normalized);
         }

         return new ArrayList<>(result);
      }
   }

   private List<String> parseUserIds(String value) {
      if (!StringUtils.hasText(value)) {
         return List.of();
      } else {
         try {
            List<String> parsed = JSON.parseArray(value, String.class);
            return this.normalizeUserIds(parsed);
         } catch (Exception var3) {
            log.warn("排行榜排除用户配置格式无效，已按空配置处理");
            return List.of();
         }
      }
   }

   private PlaybackRankingConfigResponse response(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, List<String> selectedIds, List<PlaybackRankingUserOptionResponse> users
   ) {
      PlaybackRankingConfigResponse response = new PlaybackRankingConfigResponse();
      response.setEmbyInfoId(serverConfig.id());
      response.setServerName(serverConfig.serverName());
      response.setExcludedUserIds(new ArrayList<>(selectedIds));
      response.setUsers(new ArrayList<>(users));
      return response;
   }

   @Generated
   public PlaybackRankingConfigService(
      final PlaybackRankingConfigMapper configMapper, final EmbyInfoCacheManagerUtils embyInfoCacheManager, final PlaybackRankingUserGateway userGateway
   ) {
      this.configMapper = configMapper;
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.userGateway = userGateway;
   }
}
