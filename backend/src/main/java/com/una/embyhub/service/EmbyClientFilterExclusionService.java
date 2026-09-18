package com.una.embyhub.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.mapper.EmbyClientFilterExclusionConfigMapper;
import com.una.embyhub.model.dto.request.embyblockkeyword.EmbyClientFilterExclusionUpdateRequest;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyClientFilterExclusionResponse;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyClientFilterExclusionSummaryResponse;
import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingUserOptionResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyClientFilterExclusionConfig;
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
public class EmbyClientFilterExclusionService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyClientFilterExclusionService.class);
   private static final int MAX_USER_ID_LENGTH = 128;
   private final EmbyClientFilterExclusionConfigMapper configMapper;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final PlaybackRankingUserGateway userGateway;

   @Transactional(
      readOnly = true
   )
   public EmbyClientFilterExclusionSummaryResponse summary() {
      EmbyClientFilterExclusionSummaryResponse response = new EmbyClientFilterExclusionSummaryResponse();
      Map<String, List<String>> result = response.getExcludedUserIdsByServer();
      List<EmbyClientFilterExclusionConfig> configs = this.configMapper
         .selectList(
            Wrappers.lambdaQuery(EmbyClientFilterExclusionConfig.class)
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .orderByAsc(EmbyClientFilterExclusionConfig::getEmbyInfoId)
         );
      if (configs != null) {
         for (EmbyClientFilterExclusionConfig config : configs) {
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
   public EmbyClientFilterExclusionResponse details(Long embyInfoId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(embyInfoId);
      List<PlaybackRankingUserOptionResponse> users = this.userGateway.listUsers(serverConfig);
      Set<String> validUserIds = this.userIds(users);
      List<String> selectedIds = new ArrayList<>(this.excludedUserIds(embyInfoId));
      selectedIds.removeIf(userId -> !validUserIds.contains(userId));
      return this.response(serverConfig, selectedIds, users);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public EmbyClientFilterExclusionResponse update(EmbyClientFilterExclusionUpdateRequest request) {
      if (request == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "UA拦截排除配置不能为空");
      } else {
         List<String> normalizedIds = this.normalizeUserIds(request.getExcludedUserIds());
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(request.getEmbyInfoId());
         List<PlaybackRankingUserOptionResponse> users = this.userGateway.listUsers(serverConfig);
         Set<String> validUserIds = this.userIds(users);

         for (String userId : normalizedIds) {
            if (!validUserIds.contains(userId)) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "排除用户不属于当前服务器，请刷新用户列表后重试");
            }
         }

         EmbyClientFilterExclusionConfig config = this.findConfig(serverConfig.id());
         if (config == null) {
            config = new EmbyClientFilterExclusionConfig();
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

   public boolean isExcluded(Long embyInfoId, String embyUserId) {
      if (embyInfoId != null && StringUtils.hasText(embyUserId)) {
         try {
            return this.excludedUserIds(embyInfoId).contains(embyUserId.trim());
         } catch (Exception var4) {
            log.error("UA拦截排除配置读取失败，将继续执行拦截: embyInfoId={}, embyUserId={}", embyInfoId, embyUserId, var4);
            return false;
         }
      } else {
         return false;
      }
   }

   @Transactional(
      readOnly = true
   )
   public Set<String> excludedUserIds(Long embyInfoId) {
      if (embyInfoId == null) {
         return Set.of();
      } else {
         EmbyClientFilterExclusionConfig config = this.findConfig(embyInfoId);
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

   private EmbyClientFilterExclusionConfig findConfig(Long embyInfoId) {
      return this.configMapper
         .selectOne(
            Wrappers.lambdaQuery(EmbyClientFilterExclusionConfig.class)
               .eq(EmbyClientFilterExclusionConfig::getEmbyInfoId, embyInfoId)
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .last("LIMIT 1")
         );
   }

   private Set<String> userIds(List<PlaybackRankingUserOptionResponse> users) {
      return users.stream().map(PlaybackRankingUserOptionResponse::getId).filter(StringUtils::hasText).collect(Collectors.toCollection(LinkedHashSet::new));
   }

   private List<String> normalizeUserIds(List<String> values) {
      LinkedHashSet<String> result = new LinkedHashSet<>();
      if (values == null) {
         return List.of();
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
            log.warn("UA拦截排除用户配置格式无效，已按空配置处理");
            return List.of();
         }
      }
   }

   private EmbyClientFilterExclusionResponse response(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, List<String> selectedIds, List<PlaybackRankingUserOptionResponse> users
   ) {
      EmbyClientFilterExclusionResponse response = new EmbyClientFilterExclusionResponse();
      response.setEmbyInfoId(serverConfig.id());
      response.setServerName(serverConfig.serverName());
      response.setExcludedUserIds(new ArrayList<>(selectedIds));
      response.setUsers(new ArrayList<>(users));
      return response;
   }

   @Generated
   public EmbyClientFilterExclusionService(
      final EmbyClientFilterExclusionConfigMapper configMapper,
      final EmbyInfoCacheManagerUtils embyInfoCacheManager,
      final PlaybackRankingUserGateway userGateway
   ) {
      this.configMapper = configMapper;
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.userGateway = userGateway;
   }
}
