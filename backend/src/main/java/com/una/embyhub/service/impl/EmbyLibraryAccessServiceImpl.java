package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.telegrambot.TelegramBotAuthorizationService;
import com.una.embyhub.config.common.telegrambot.TelegramBotPermission;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.mapper.EmbyInfoMapper;
import com.una.embyhub.mapper.EmbyLibraryAccessRuleMapper;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessGlobalUpdateRequest;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessUserUpdateRequest;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessUsersUpdateRequest;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessConfigResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessOverviewResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUpdateResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUserOptionResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryFolderResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyLibraryAccessRule;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.EmbyLibraryAccessGateway;
import com.una.embyhub.service.EmbyLibraryAccessMutationRateLimiter;
import com.una.embyhub.service.EmbyLibraryAccessService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
public class EmbyLibraryAccessServiceImpl extends ServiceImpl<EmbyLibraryAccessRuleMapper, EmbyLibraryAccessRule> implements EmbyLibraryAccessService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyLibraryAccessServiceImpl.class);
   static final String SCOPE_GLOBAL = "GLOBAL";
   static final String SCOPE_USER = "USER";
   private static final int MAX_FOLDER_COUNT = 500;
   private static final int MAX_FOLDER_ID_LENGTH = 128;
   private static final int MAX_TARGET_USER_COUNT = 500;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final EmbyLibraryAccessGateway gateway;
   private final EmbyInfoMapper embyInfoMapper;
   private final EmbyUserMapper embyUserMapper;
   private final TelegramBotAuthorizationService telegramBotAuthorizationService;
   private final EmbyLibraryAccessMutationRateLimiter mutationRateLimiter;

   @Transactional(
      readOnly = true
   )
   @Override
   public EmbyLibraryAccessOverviewResponse overview(Long embyInfoId, Long userId) {
      this.requireWebAccess();
      return this.overviewAuthorized(embyInfoId, userId);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public EmbyLibraryAccessOverviewResponse overviewFromTelegram(Long embyInfoId, Long userId, long telegramOperatorId) {
      this.requireTelegramAccess(telegramOperatorId);
      return this.overviewAuthorized(embyInfoId, userId);
   }

   private EmbyLibraryAccessOverviewResponse overviewAuthorized(Long embyInfoId, Long userId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(embyInfoId);
      List<EmbyLibraryFolderResponse> folders = this.requireFolders(serverConfig);
      List<String> allFolderIds = folders.stream().map(EmbyLibraryFolderResponse::getId).toList();
      Set<String> validFolderIds = new LinkedHashSet<>(allFolderIds);
      Map<String, String> folderIdAliases = this.folderIdAliases(folders);
      EmbyLibraryAccessRule globalRule = this.findGlobalRule(embyInfoId);
      EmbyLibraryAccessRule userRule = null;
      EmbyUser targetUser = null;
      if (userId != null) {
         targetUser = this.requireEligibleUser(embyInfoId, userId);
         userRule = this.findUserRule(embyInfoId, userId);
      }

      EmbyLibraryAccessOverviewResponse response = new EmbyLibraryAccessOverviewResponse();
      response.setEmbyInfoId(embyInfoId);
      response.setServerName(serverConfig.serverName());
      response.setFolders(folders);
      response.setGlobalConfig(this.toConfig(globalRule, allFolderIds, validFolderIds, folderIdAliases, "GLOBAL"));
      if (targetUser != null) {
         response.setUserId(targetUser.getId());
         response.setUserName(targetUser.getEmbyUserName());
         response.setUserConfig(this.toConfig(userRule, allFolderIds, validFolderIds, folderIdAliases, "USER"));
      }

      response.setEffectiveConfig(this.effectiveConfig(globalRule, userRule, allFolderIds, validFolderIds, folderIdAliases));
      return response;
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public List<EmbyLibraryAccessUserOptionResponse> listUsers(Long embyInfoId) {
      this.requireWebAccess();
      return this.listUsersAuthorized(embyInfoId);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public List<EmbyLibraryAccessUserOptionResponse> listUsersFromTelegram(Long embyInfoId, long telegramOperatorId) {
      this.requireTelegramAccess(telegramOperatorId);
      return this.listUsersAuthorized(embyInfoId);
   }

   private List<EmbyLibraryAccessUserOptionResponse> listUsersAuthorized(Long embyInfoId) {
      this.requireServer(embyInfoId);
      return new LambdaQueryChainWrapper<>(this.embyUserMapper)
         .eq(EmbyUser::getEmbyInfoId, embyInfoId)
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .and(wrapper -> wrapper.ne(EmbyUser::getIsAdmin, Integer.valueOf(1)).or().isNull(EmbyUser::getIsAdmin))
         .and(wrapper -> wrapper.ne(EmbyUser::getIsPrimaryAdmin, Integer.valueOf(1)).or().isNull(EmbyUser::getIsPrimaryAdmin))
         .isNotNull(EmbyUser::getEmbyUserId)
         .ne(EmbyUser::getEmbyUserId, "")
         .orderByAsc(EmbyUser::getEmbyUserName)
         .list()
         .stream()
         .map(user -> new EmbyLibraryAccessUserOptionResponse(user.getId(), user.getEmbyUserId(), user.getEmbyUserName()))
         .toList();
   }

   @Override
   public EmbyLibraryAccessUpdateResponse updateGlobal(EmbyLibraryAccessGlobalUpdateRequest request) {
      this.requireWebGlobalMutation();
      return this.updateGlobalAuthorized(request, null);
   }

   @Override
   public EmbyLibraryAccessUpdateResponse updateGlobalFromTelegram(EmbyLibraryAccessGlobalUpdateRequest request, long telegramOperatorId, String actorName) {
      this.requireTelegramGlobalMutation(telegramOperatorId);
      return this.updateGlobalAuthorized(request, actorName);
   }

   private EmbyLibraryAccessUpdateResponse updateGlobalAuthorized(EmbyLibraryAccessGlobalUpdateRequest request, String actorName) {
      if (request != null && request.getEmbyInfoId() != null && request.getEnabled() != null) {
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(request.getEmbyInfoId());
         List<EmbyLibraryFolderResponse> folders = this.requireFolders(serverConfig);
         Set<String> allFolderIds = this.folderIdSet(folders);
         Map<String, String> folderIdAliases = this.folderIdAliases(folders);
         List<String> visibleFolderIds = this.validateVisibleFolderIds(request.getVisibleFolderIds(), allFolderIds, folderIdAliases);
         EmbyLibraryAccessRule globalRule = this.upsertRule(
            request.getEmbyInfoId(), "GLOBAL", "GLOBAL", null, request.getEnabled(), visibleFolderIds, actorName
         );
         List<EmbyUser> users = this.eligibleUsers(request.getEmbyInfoId());
         EmbyLibraryAccessUpdateResponse response = new EmbyLibraryAccessUpdateResponse();
         response.setMatchedUserCount(users.size());

         for (EmbyUser user : users) {
            EmbyLibraryAccessRule userRule = this.findUserRule(request.getEmbyInfoId(), user.getId());
            this.applyEffectiveRule(serverConfig, user, globalRule, userRule, folders, allFolderIds, folderIdAliases, response);
         }

         return response;
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      }
   }

   @Override
   public EmbyLibraryAccessUpdateResponse updateUser(EmbyLibraryAccessUserUpdateRequest request) {
      this.requireWebUserMutation();
      return this.updateUsersAuthorized(
         request == null ? null : request.getEmbyInfoId(),
         request != null && request.getUserId() != null ? List.of(request.getUserId()) : null,
         request == null ? null : request.getOverrideEnabled(),
         request == null ? null : request.getVisibleFolderIds(),
         null
      );
   }

   @Override
   public EmbyLibraryAccessUpdateResponse updateUsers(EmbyLibraryAccessUsersUpdateRequest request) {
      this.requireWebUserMutation();
      return this.updateUsersAuthorized(
         request == null ? null : request.getEmbyInfoId(),
         request == null ? null : request.getUserIds(),
         request == null ? null : request.getOverrideEnabled(),
         request == null ? null : request.getVisibleFolderIds(),
         null
      );
   }

   @Override
   public EmbyLibraryAccessUpdateResponse updateUserFromTelegram(EmbyLibraryAccessUserUpdateRequest request, long telegramOperatorId, String actorName) {
      this.requireTelegramUserMutation(telegramOperatorId);
      return this.updateUsersAuthorized(
         request == null ? null : request.getEmbyInfoId(),
         request != null && request.getUserId() != null ? List.of(request.getUserId()) : null,
         request == null ? null : request.getOverrideEnabled(),
         request == null ? null : request.getVisibleFolderIds(),
         actorName
      );
   }

   private EmbyLibraryAccessUpdateResponse updateUsersAuthorized(
      Long embyInfoId, List<Long> requestedUserIds, Boolean overrideEnabled, List<String> requestedFolderIds, String actorName
   ) {
      if (embyInfoId != null && overrideEnabled != null) {
         List<Long> userIds = this.normalizeTargetUserIds(requestedUserIds);
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(embyInfoId);
         List<EmbyUser> targetUsers = userIds.stream().map(userId -> this.requireEligibleUser(embyInfoId, userId)).toList();
         List<EmbyLibraryFolderResponse> folders = this.requireFolders(serverConfig);
         Set<String> allFolderIds = this.folderIdSet(folders);
         Map<String, String> folderIdAliases = this.folderIdAliases(folders);
         List<String> visibleFolderIds = this.validateVisibleFolderIds(requestedFolderIds, allFolderIds, folderIdAliases);
         EmbyLibraryAccessRule globalRule = this.findGlobalRule(embyInfoId);
         EmbyLibraryAccessUpdateResponse response = new EmbyLibraryAccessUpdateResponse();
         response.setMatchedUserCount(targetUsers.size());

         for (EmbyUser targetUser : targetUsers) {
            EmbyLibraryAccessRule userRule = this.upsertRule(
               embyInfoId, "USER", this.userScopeKey(targetUser.getId()), targetUser.getId(), overrideEnabled, visibleFolderIds, actorName
            );
            this.applyEffectiveRule(serverConfig, targetUser, globalRule, userRule, folders, allFolderIds, folderIdAliases, response);
         }

         return response;
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      }
   }

   private List<Long> normalizeTargetUserIds(List<Long> requestedUserIds) {
      if (requestedUserIds != null && !requestedUserIds.isEmpty()) {
         if (requestedUserIds.size() > 500) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "单次最多设置500个用户");
         } else {
            LinkedHashSet<Long> normalized = new LinkedHashSet<>();

            for (Long userId : requestedUserIds) {
               if (userId == null || userId <= 0L) {
                  throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户参数无效");
               }

               normalized.add(userId);
            }

            return new ArrayList<>(normalized);
         }
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请至少选择一个用户");
      }
   }

   @Override
   public void applyGlobalRuleToRemoteUser(Long embyInfoId, String embyUserId) {
      if (embyInfoId != null && StringUtils.hasText(embyUserId)) {
         EmbyLibraryAccessRule globalRule = this.findGlobalRule(embyInfoId);
         if (globalRule != null) {
            EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.requireServer(embyInfoId);
            List<EmbyLibraryFolderResponse> folders = this.requireFolders(serverConfig);
            Set<String> allFolderIds = this.folderIdSet(folders);
            List<String> selectedIds = this.currentFolderIds(globalRule, allFolderIds, this.folderIdAliases(folders));
            this.gateway.applyFolderAccess(serverConfig, embyUserId, Integer.valueOf(1).equals(globalRule.getRuleEnabled()), selectedIds, folders);
         }
      }
   }

   private void requireWebAccess() {
      StpUtil.checkPermission("admin");
   }

   private void requireWebGlobalMutation() {
      this.requireWebAccess();
      this.mutationRateLimiter.checkWebGlobal(StpUtil.getLoginIdAsLong());
   }

   private void requireWebUserMutation() {
      this.requireWebAccess();
      this.mutationRateLimiter.checkWebUser(StpUtil.getLoginIdAsLong());
   }

   private void requireTelegramAccess(long telegramOperatorId) {
      if (telegramOperatorId <= 0L || !this.telegramBotAuthorizationService.hasPermission(telegramOperatorId, TelegramBotPermission.LIBRARY_ACCESS)) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   private void requireTelegramGlobalMutation(long telegramOperatorId) {
      this.requireTelegramAccess(telegramOperatorId);
      this.mutationRateLimiter.checkTelegramGlobal(telegramOperatorId);
   }

   private void requireTelegramUserMutation(long telegramOperatorId) {
      this.requireTelegramAccess(telegramOperatorId);
      this.mutationRateLimiter.checkTelegramUser(telegramOperatorId);
   }

   private void applyEffectiveRule(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      EmbyUser user,
      EmbyLibraryAccessRule globalRule,
      EmbyLibraryAccessRule userRule,
      List<EmbyLibraryFolderResponse> folders,
      Set<String> allFolderIds,
      Map<String, String> folderIdAliases,
      EmbyLibraryAccessUpdateResponse response
   ) {
      EmbyLibraryAccessRule effectiveRule = userRule != null && Integer.valueOf(1).equals(userRule.getRuleEnabled()) ? userRule : globalRule;
      boolean restrictionEnabled = effectiveRule != null && Integer.valueOf(1).equals(effectiveRule.getRuleEnabled());
      List<String> visibleIds = (List<String>)(effectiveRule == null
         ? new ArrayList<>(allFolderIds)
         : this.currentFolderIds(effectiveRule, allFolderIds, folderIdAliases));

      try {
         this.gateway.applyFolderAccess(serverConfig, user.getEmbyUserId(), restrictionEnabled, visibleIds, folders);
         response.setAppliedUserCount(response.getAppliedUserCount() + 1);
      } catch (Exception var13) {
         log.warn("同步 Emby 媒体库权限失败: serverId={}, userId={}, embyUserId={}, error={}", serverConfig.id(), user.getId(), user.getEmbyUserId(), var13.getMessage());
         response.setFailedUserCount(response.getFailedUserCount() + 1);
         response.getFailures().add(this.safeUserName(user) + "：同步失败");
      }
   }

   private EmbyLibraryAccessRule upsertRule(
      Long embyInfoId, String scopeType, String scopeKey, Long targetUserId, boolean enabled, List<String> visibleFolderIds, String actorName
   ) {
      EmbyLibraryAccessRule rule = this.lambdaQuery()
         .eq(EmbyLibraryAccessRule::getEmbyInfoId, embyInfoId)
         .eq(EmbyLibraryAccessRule::getScopeKey, scopeKey)
         .one();
      boolean creating = rule == null;
      if (creating) {
         rule = new EmbyLibraryAccessRule();
         rule.setEmbyInfoId(embyInfoId);
         rule.setScopeType(scopeType);
         rule.setScopeKey(scopeKey);
         rule.setTargetUserId(targetUserId);
         if (StringUtils.hasText(actorName)) {
            rule.setCreateUserName(actorName.trim());
         }
      }

      rule.setRuleEnabled(enabled ? 1 : 0);
      rule.setVisibleFolderIds(JSON.toJSONString(visibleFolderIds));
      if (StringUtils.hasText(actorName)) {
         rule.setUpdateUserName(actorName.trim());
      }

      if (creating) {
         this.save(rule);
      } else {
         this.updateById(rule);
      }

      return rule;
   }

   private EmbyLibraryAccessConfigResponse effectiveConfig(
      EmbyLibraryAccessRule globalRule,
      EmbyLibraryAccessRule userRule,
      List<String> allFolderIds,
      Set<String> validFolderIds,
      Map<String, String> folderIdAliases
   ) {
      if (userRule != null && Integer.valueOf(1).equals(userRule.getRuleEnabled())) {
         return new EmbyLibraryAccessConfigResponse(true, true, this.currentFolderIds(userRule, validFolderIds, folderIdAliases), "USER");
      } else {
         return globalRule != null && Integer.valueOf(1).equals(globalRule.getRuleEnabled())
            ? new EmbyLibraryAccessConfigResponse(true, true, this.currentFolderIds(globalRule, validFolderIds, folderIdAliases), "GLOBAL")
            : new EmbyLibraryAccessConfigResponse(globalRule != null, false, new ArrayList<>(allFolderIds), "ALL");
      }
   }

   private EmbyLibraryAccessConfigResponse toConfig(
      EmbyLibraryAccessRule rule, List<String> allFolderIds, Set<String> validFolderIds, Map<String, String> folderIdAliases, String source
   ) {
      return rule == null
         ? new EmbyLibraryAccessConfigResponse(false, false, new ArrayList<>(allFolderIds), source)
         : new EmbyLibraryAccessConfigResponse(
            true, Integer.valueOf(1).equals(rule.getRuleEnabled()), this.currentFolderIds(rule, validFolderIds, folderIdAliases), source
         );
   }

   private List<EmbyUser> eligibleUsers(Long embyInfoId) {
      return new LambdaQueryChainWrapper<>(this.embyUserMapper)
         .eq(EmbyUser::getEmbyInfoId, embyInfoId)
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .and(wrapper -> wrapper.ne(EmbyUser::getIsAdmin, Integer.valueOf(1)).or().isNull(EmbyUser::getIsAdmin))
         .and(wrapper -> wrapper.ne(EmbyUser::getIsPrimaryAdmin, Integer.valueOf(1)).or().isNull(EmbyUser::getIsPrimaryAdmin))
         .isNotNull(EmbyUser::getEmbyUserId)
         .ne(EmbyUser::getEmbyUserId, "")
         .list();
   }

   private EmbyUser requireEligibleUser(Long embyInfoId, Long userId) {
      EmbyUser user = this.embyUserMapper.selectById(userId);
      if (user == null || user.getDelFlag() != 0 || !Objects.equals(user.getEmbyInfoId(), embyInfoId) || !StringUtils.hasText(user.getEmbyUserId())) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else if (!Integer.valueOf(1).equals(user.getIsAdmin()) && !Integer.valueOf(1).equals(user.getIsPrimaryAdmin())) {
         return user;
      } else {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED.getCode(), "管理员账号不参与媒体库分级");
      }
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig requireServer(Long embyInfoId) {
      if (embyInfoId == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请选择服务器");
      } else {
         EmbyInfo server = this.embyInfoMapper.selectById(embyInfoId);
         if (server != null && server.getDelFlag() == 0 && Integer.valueOf(1).equals(server.getEnabled()) && Integer.valueOf(0).equals(server.getStatus())) {
            return this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
         } else {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "服务器不可用，请重新选择");
         }
      }
   }

   private List<EmbyLibraryFolderResponse> requireFolders(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      List<EmbyLibraryFolderResponse> folders = this.gateway.listFolders(serverConfig);
      if (folders.isEmpty()) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "当前服务器没有可配置的媒体库");
      } else if (folders.size() > 500) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "媒体库数量超过安全上限");
      } else {
         return folders;
      }
   }

   private List<String> validateVisibleFolderIds(List<String> requestedIds, Set<String> validFolderIds, Map<String, String> folderIdAliases) {
      if (requestedIds == null) {
         return new ArrayList<>(validFolderIds);
      } else if (requestedIds.size() > 500) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "媒体库选择数量超过安全上限");
      } else {
         LinkedHashSet<String> normalized = new LinkedHashSet<>();

         for (String value : requestedIds) {
            if (StringUtils.hasText(value)) {
               String folderId = value.trim();
               String normalizedFolderId = this.normalizeFolderId(folderId, validFolderIds, folderIdAliases);
               if (folderId.length() > 128 || normalizedFolderId == null) {
                  throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "媒体库选择已失效，请重新加载");
               }

               normalized.add(normalizedFolderId);
            }
         }

         return new ArrayList<>(normalized);
      }
   }

   List<String> currentFolderIds(EmbyLibraryAccessRule rule, Set<String> validFolderIds, Map<String, String> folderIdAliases) {
      if (rule != null && StringUtils.hasText(rule.getVisibleFolderIds())) {
         try {
            List<String> stored = JSON.parseArray(rule.getVisibleFolderIds(), String.class);
            return (List<String>)(stored == null
               ? new ArrayList<>()
               : stored.stream()
                  .filter(StringUtils::hasText)
                  .map(String::trim)
                  .map(folderId -> this.normalizeFolderId(folderId, validFolderIds, folderIdAliases))
                  .filter(Objects::nonNull)
                  .distinct()
                  .toList());
         } catch (Exception var5) {
            log.warn("解析媒体库分级规则失败: ruleId={}", rule.getId());
            return new ArrayList<>();
         }
      } else {
         return new ArrayList<>(validFolderIds);
      }
   }

   private EmbyLibraryAccessRule findGlobalRule(Long embyInfoId) {
      return this.lambdaQuery().eq(EmbyLibraryAccessRule::getEmbyInfoId, embyInfoId).eq(EmbyLibraryAccessRule::getScopeKey, "GLOBAL").one();
   }

   private EmbyLibraryAccessRule findUserRule(Long embyInfoId, Long userId) {
      return userId == null
         ? null
         : this.lambdaQuery().eq(EmbyLibraryAccessRule::getEmbyInfoId, embyInfoId).eq(EmbyLibraryAccessRule::getScopeKey, this.userScopeKey(userId)).one();
   }

   private String userScopeKey(Long userId) {
      return "USER:" + userId;
   }

   private Set<String> folderIdSet(List<EmbyLibraryFolderResponse> folders) {
      LinkedHashSet<String> result = new LinkedHashSet<>();
      folders.stream().map(EmbyLibraryFolderResponse::getId).forEach(result::add);
      return result;
   }

   Map<String, String> folderIdAliases(List<EmbyLibraryFolderResponse> folders) {
      Map<String, String> aliases = new LinkedHashMap<>();

      for (EmbyLibraryFolderResponse folder : folders) {
         if (folder != null && StringUtils.hasText(folder.getId())) {
            String folderId = folder.getId().trim();
            aliases.put(folderId.toLowerCase(Locale.ROOT), folderId);
            if (folder.getLegacyIds() != null) {
               for (String legacyId : folder.getLegacyIds()) {
                  if (StringUtils.hasText(legacyId)) {
                     aliases.put(legacyId.trim(), folderId);
                     aliases.put(legacyId.trim().toLowerCase(Locale.ROOT), folderId);
                  }
               }
            }
         }
      }

      return aliases;
   }

   String normalizeFolderId(String folderId, Set<String> validFolderIds, Map<String, String> folderIdAliases) {
      if (!StringUtils.hasText(folderId)) {
         return null;
      } else {
         String trimmed = folderId.trim();
         if (validFolderIds.contains(trimmed)) {
            return trimmed;
         } else if (folderIdAliases == null) {
            return null;
         } else {
            String resolved = folderIdAliases.get(trimmed);
            return resolved != null ? resolved : folderIdAliases.get(trimmed.toLowerCase(Locale.ROOT));
         }
      }
   }

   private String safeUserName(EmbyUser user) {
      return StringUtils.hasText(user.getEmbyUserName()) ? user.getEmbyUserName() : String.valueOf(user.getId());
   }

   @Generated
   public EmbyLibraryAccessServiceImpl(
      final EmbyInfoCacheManagerUtils embyInfoCacheManager,
      final EmbyLibraryAccessGateway gateway,
      final EmbyInfoMapper embyInfoMapper,
      final EmbyUserMapper embyUserMapper,
      final TelegramBotAuthorizationService telegramBotAuthorizationService,
      final EmbyLibraryAccessMutationRateLimiter mutationRateLimiter
   ) {
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.gateway = gateway;
      this.embyInfoMapper = embyInfoMapper;
      this.embyUserMapper = embyUserMapper;
      this.telegramBotAuthorizationService = telegramBotAuthorizationService;
      this.mutationRateLimiter = mutationRateLimiter;
   }
}
