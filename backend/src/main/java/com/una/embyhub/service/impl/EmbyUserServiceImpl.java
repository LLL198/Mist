package com.una.embyhub.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.Binder;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.binding.RelationsBinder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.config.common.enums.RenewChannelEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.exception.MultipleServerMatchException;
import com.una.embyhub.config.common.utils.CheckPasswordUtils;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.RedisLockUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.event.CardRegisterSuccessNotifyEvent;
import com.una.embyhub.event.CardRenewSuccessNotifyEvent;
import com.una.embyhub.event.InvitationRegisterSuccessNotifyEvent;
import com.una.embyhub.event.SimultaneousPlaybackUserConfigCleanupEvent;
import com.una.embyhub.foam.client.EmbyClient;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.dto.request.embyuser.DisableUserRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserBatchExpirationUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserProfileUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserSave;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdateData;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdatePassword;
import com.una.embyhub.model.dto.request.embyuser.InsertUserCardRequest;
import com.una.embyhub.model.dto.request.embyuser.LoginRequest;
import com.una.embyhub.model.dto.request.embyuser.RegisteredUserSave;
import com.una.embyhub.model.dto.request.embyuser.SyncEmbyUserRequest;
import com.una.embyhub.model.dto.request.invitation.InvitationRegisterRequest;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyServerUserStatsResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserDiffResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.embyuser.InsertUserResponse;
import com.una.embyhub.model.dto.response.embyuser.LoginMultipleServerResponse;
import com.una.embyhub.model.dto.response.embyuser.RegisteredUserResponse;
import com.una.embyhub.model.dto.response.embyuser.UserOauthBindingResponse;
import com.una.embyhub.model.dto.response.embyuser.UserStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.CardSecurityManagement;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.EmbyUserRegisterRecord;
import com.una.embyhub.model.entity.EmbyUserRenewRecord;
import com.una.embyhub.model.entity.InvitationCode;
import com.una.embyhub.model.entity.UserInvitation;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.service.AdminMenuPermissionService;
import com.una.embyhub.service.CardSecurityManagementService;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyLibraryAccessService;
import com.una.embyhub.service.EmbyUserCredentialPolicy;
import com.una.embyhub.service.EmbyUserIdentityUtils;
import com.una.embyhub.service.EmbyUserRegisterRecordService;
import com.una.embyhub.service.EmbyUserRenewRecordService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.InvitationCodeService;
import com.una.embyhub.service.InvitationRegistrationIpRateLimiter;
import com.una.embyhub.service.RoseUserBindingService;
import com.una.embyhub.service.SystemConfigService;
import com.una.embyhub.service.UserInvitationService;
import com.una.embyhub.service.UserPointsService;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.Configuration;
import embyclient.api.UserServiceApi;
import embyclient.model.CreateUserByName;
import embyclient.model.LibraryUserCopyOptions;
import embyclient.model.QueryResultUserDto;
import embyclient.model.UpdateUserPassword;
import embyclient.model.UserDto;
import embyclient.model.UserPolicy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.Generated;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyUserServiceImpl extends ServiceImpl<EmbyUserMapper, EmbyUser> implements EmbyUserService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyUserServiceImpl.class);
   private static final int CARD_AND_INVITATION_REGISTER_USER_NAME_MIN_LENGTH = 6;
   private static final String DISTRIBUTOR_CARD_REGISTERED_USER_IDS_SQL = "SELECT DISTINCT user_id FROM card_security_management WHERE user_id IS NOT NULL AND card_status = 1 AND distributor_id IS NOT NULL AND distributor_id > 0";
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private EmbyClient embyClient;
   @Autowired
   private EmbyLibraryAccessService embyLibraryAccessService;
   @Autowired
   private RoseUserBindingService roseUserBindingService;
   @Autowired
   private AdminMenuPermissionService adminMenuPermissionService;
   @Autowired
   private CardSecurityManagementService cardSecurityManagementService;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private ApplicationEventPublisher applicationEventPublisher;
   @Autowired
   private InvitationCodeService invitationCodeService;
   @Autowired
   private Ip2regionSearcher searchSearcher;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private EmbyUserRegisterRecordService embyUserRegisterRecordService;
   @Autowired
   private EmbyUserRenewRecordService embyUserRenewRecordService;
   @Autowired
   private UserOauthBindingMapper userOauthBindingMapper;
   @Autowired
   private SystemConfigService systemConfigService;
   @Autowired
   private UserPointsService userPointsService;
   @Autowired
   private UserInvitationService userInvitationService;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   @Autowired
   private RedisTemplate<String, byte[]> binaryRedisTemplate;
   @Autowired
   private RedisLockUtils redisLockUtils;
   @Autowired
   private EmbyUserCredentialPolicy embyUserCredentialPolicy;
   @Autowired
   private InvitationRegistrationIpRateLimiter invitationRegistrationIpRateLimiter;
   private static final String REGISTER_NAME_LOCK_PREFIX = "foam:register:name:";
   private static final String REGISTER_CARD_LOCK_PREFIX = "foam:register:card:";
   private static final String REGISTER_INVITATION_LOCK_PREFIX = "foam:register:invitation:";
   private static final String RENEW_USER_LOCK_PREFIX = "foam:renew:user:";
   private static final String REGISTER_INIT_ADMIN_LOCK_KEY = "foam:register:init-admin";
   private static final long REGISTER_LOCK_TTL_SECONDS = 180L;

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveServerConfig(Long embyInfoId) {
      if (embyInfoId != null) {
         return this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      } else {
         EmbyUser embyUser = null;
         if (StpUtil.isLogin()) {
            embyUser = (EmbyUser)StpUtil.getSession().get("user");
         }

         return embyUser != null ? this.embyInfoCacheManager.getRequiredConfig(embyUser) : this.embyInfoCacheManager.getRequiredConfig();
      }
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveSelfServiceRegistrationServerConfig() {
      String configValue = this.configCacheLoaderUtils.getConfigValue("self_service_register_emby_info_id");
      if (!StringUtils.hasText(configValue)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请先选择自助注册服务器");
      } else {
         String serverIdValue = Arrays.stream(configValue.split("[,，\\s]+")).map(String::trim).filter(StringUtils::hasText).findFirst().orElse("");
         if (!StringUtils.hasText(serverIdValue)) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请先选择自助注册服务器");
         } else {
            try {
               return this.embyInfoCacheManager.getRequiredConfigById(Long.valueOf(serverIdValue));
            } catch (NumberFormatException var4) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请先选择自助注册服务器");
            }
         }
      }
   }

   private String getCopyfromuserid(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      return serverConfig.copyfromuserid();
   }

   private UserServiceApi buildUserServiceApi(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, serverConfig);
      Configuration.setDefaultApiClient(apiClient);
      return new UserServiceApi();
   }

   private String resolveDefaultPassword(String password) {
      return StringUtils.hasText(password) ? password : "123456";
   }

   private String buildUserKey(String userName, Long embyInfoId) {
      return userName + "@" + embyInfoId;
   }

   private void applyTelegramBoundFilter(QueryWrapper queryWrapper, EmbyUserRequest request) {
      if (request != null && request.getTelegramBound() != null) {
         String telegramBindingSql = "SELECT user_id FROM user_oauth_binding WHERE provider = 'telegram' AND del_flag = 0";
         if (request.getTelegramBound() == 1) {
            queryWrapper.inSql("id", telegramBindingSql);
         } else if (request.getTelegramBound() == 0) {
            queryWrapper.notInSql("id", telegramBindingSql);
         }
      }
   }

   private void applyHostLineAccessFilter(QueryWrapper queryWrapper, Integer hostLineType) {
      if (hostLineType != null) {
         if (HostLineTypeEnum.normalize(hostLineType) == HostLineTypeEnum.WHITELIST.getCode()) {
            queryWrapper.apply("(is_admin = 1 OR host_line_type = 1)", new Object[0]);
         } else {
            queryWrapper.apply("((is_admin <> 1 OR is_admin IS NULL) AND (host_line_type <> 1 OR host_line_type IS NULL))", new Object[0]);
         }
      }
   }

   private void applyWhitelistNeverExpires(List<EmbyUserResponse> records) {
      if (!CollectionUtils.isEmpty(records)) {
         records.forEach(record -> {
            if (record != null && HostLineTypeEnum.normalize(record.getHostLineType()) == HostLineTypeEnum.WHITELIST.getCode()) {
               record.setExpirationDate(null);
               record.setExpireDateCount(null);
               if (record.getUserStatus() != null) {
                  record.setUserStatus(record.getUserStatus());
               }
            }
         });
      }
   }

   private void attachUserListBindings(List<EmbyUserResponse> records) {
      if (!CollectionUtils.isEmpty(records)) {
         for (EmbyUserResponse response : records) {
            if (response.getCardSecurityManagementList() != null && !response.getCardSecurityManagementList().isEmpty()) {
               RelationsBinder.bind(response.getCardSecurityManagementList());
            }
         }

         this.roseUserBindingService.attachBindings(records);
         this.attachTelegramBindings(records);
      }
   }

   private void attachDistributorUserListBindings(List<EmbyUserResponse> records, Long distributorId) {
      if (!CollectionUtils.isEmpty(records)) {
         records.forEach(
            record -> {
               List<CardSecurityManagementResponse> cards = record.getCardSecurityManagementList();
               if (!CollectionUtils.isEmpty(cards)) {
                  List<CardSecurityManagementResponse> filteredCards = cards.stream()
                     .filter(Objects::nonNull)
                     .filter(card -> card.getDistributorId() != null && card.getDistributorId() > 0L)
                     .filter(card -> distributorId == null || Objects.equals(card.getDistributorId(), distributorId))
                     .collect(Collectors.toList());
                  record.setCardSecurityManagementList(filteredCards);
               }
            }
         );
         this.attachUserListBindings(records);
      }
   }

   private Long getCurrentDistributorIdOrThrow() {
      Long currentUserId = StpUtil.getLoginIdAsLong();
      EmbyUser embyUser = this.getById(currentUserId);
      if (embyUser != null && embyUser.getIsDistributor() != null && embyUser.getIsDistributor() != 0) {
         return embyUser.getId();
      } else {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   private void attachTelegramBindings(List<EmbyUserResponse> records) {
      List<Long> userIds = records.stream().map(EmbyUserResponse::getId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(userIds)) {
         List<UserOauthBinding> bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
            .in(UserOauthBinding::getUserId, userIds)
            .eq(UserOauthBinding::getProvider, "telegram")
            .list();
         if (!CollectionUtils.isEmpty(bindings)) {
            Map<Long, UserOauthBindingResponse> bindingMap = bindings.stream()
               .collect(Collectors.toMap(UserOauthBinding::getUserId, this::toUserOauthBindingResponse, (first, second) -> first));
            records.forEach(response -> response.setTelegramBinding(bindingMap.get(response.getId())));
         }
      }
   }

   private UserOauthBindingResponse toUserOauthBindingResponse(UserOauthBinding binding) {
      UserOauthBindingResponse response = new UserOauthBindingResponse();
      response.setProvider(binding.getProvider());
      response.setProviderUserId(binding.getProviderUserId());
      response.setProviderUsername(binding.getProviderUsername());
      response.setProviderAvatar(binding.getProviderAvatar());
      response.setCreateDatetime(binding.getCreateDatetime());
      return response;
   }

   @Override
   public EmbyUserCustomResponse login(LoginRequest loginRequest) {
      String passwordHash = SaSecureUtil.md5(loginRequest.getPassword());
      List<EmbyUser> users = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyUser::getEmbyUserName, loginRequest.getUserName()).list();
      if (users.isEmpty()) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         List<EmbyUser> matchedUsers = users.stream().filter(u -> u.getEmbyUserPassword().equals(passwordHash)).collect(Collectors.toList());
         if (matchedUsers.isEmpty()) {
            throw new BizException(ResponseStatusEnum.PASSWORD_ERROR);
         } else if (!EmbyUserIdentityUtils.belongToSameIdentity(matchedUsers)) {
            throw new BizException("当前账户暂时无法登录，请联系管理员处理");
         } else if (loginRequest.getEmbyInfoId() != null) {
            EmbyUser selectedUser = matchedUsers.stream()
               .filter(user -> Objects.equals(user.getEmbyInfoId(), loginRequest.getEmbyInfoId()))
               .findFirst()
               .orElse(null);
            if (selectedUser == null) {
               boolean userExistsOnSelectedServer = users.stream().anyMatch(user -> Objects.equals(user.getEmbyInfoId(), loginRequest.getEmbyInfoId()));
               throw new BizException(userExistsOnSelectedServer ? ResponseStatusEnum.PASSWORD_ERROR : ResponseStatusEnum.USER_NOT_EXIST);
            } else {
               return this.doLoginAndCacheSpeedTestToken(selectedUser, loginRequest.getPassword());
            }
         } else if (matchedUsers.size() == 1) {
            return this.doLoginAndCacheSpeedTestToken(matchedUsers.get(0), loginRequest.getPassword());
         } else {
            List<Long> embyInfoIds = matchedUsers.stream().map(EmbyUser::getEmbyInfoId).distinct().collect(Collectors.toList());
            List<EmbyInfo> serverInfos = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper()).in(EmbyInfo::getId, embyInfoIds).list();
            List<LoginMultipleServerResponse.ServerOption> serverOptions = serverInfos.stream()
               .map(info -> new LoginMultipleServerResponse.ServerOption(info.getId(), info.getServerName()))
               .collect(Collectors.toList());
            LoginMultipleServerResponse response = new LoginMultipleServerResponse(serverOptions);
            throw new MultipleServerMatchException(response);
         }
      }
   }

   private EmbyUserCustomResponse doLogin(EmbyUser embyUser) {
      this.assertLoginServerEnabled(embyUser);
      StpUtil.login(embyUser.getId());
      StpUtil.getSession().set("user", embyUser);
      EmbyUserCustomResponse embyUserCustomResponse = Binder.convertAndBindRelations(embyUser, EmbyUserCustomResponse.class);
      this.populateAvatar(embyUserCustomResponse, embyUser.getId());
      this.populateAccessibleServers(embyUserCustomResponse, embyUser);
      this.populateAdminMenuPermissions(embyUserCustomResponse, embyUser);
      Object themeObj = this.redisTemplate.opsForValue().get("user:theme:" + embyUser.getId());
      if (themeObj != null) {
         embyUserCustomResponse.setTheme(themeObj.toString());
      }

      return embyUserCustomResponse;
   }

   private EmbyUserCustomResponse doLoginAndCacheSpeedTestToken(EmbyUser embyUser, String rawPassword) {
      EmbyUserCustomResponse response = this.doLogin(embyUser);
      this.cacheSpeedTestTokensForLogin(embyUser, rawPassword);
      return response;
   }

   private void cacheSpeedTestTokensForLogin(EmbyUser embyUser, String rawPassword) {
      if (embyUser != null && StringUtils.hasText(rawPassword)) {
         this.cacheSpeedTestToken(embyUser, rawPassword);
      }
   }

   private void cacheSpeedTestToken(EmbyUser embyUser, String rawPassword) {
      try {
         this.embyClient.tryCacheSpeedTestUserAccessToken(this.resolveServerConfig(embyUser.getEmbyInfoId()), embyUser, rawPassword);
      } catch (Exception var4) {
         log.warn("缓存 Emby 用户测速 Token 失败: userId={}, embyInfoId={}, error={}", embyUser.getId(), embyUser.getEmbyInfoId(), var4.getMessage());
      }
   }

   private void assertLoginServerEnabled(EmbyUser embyUser) {
      if (!Integer.valueOf(1).equals(embyUser.getIsAdmin())) {
         Long count = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
            .eq(EmbyInfo::getId, embyUser.getEmbyInfoId())
            .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
            .eq(EmbyInfo::getStatus, Integer.valueOf(0))
            .count();
         if (count <= 0L) {
            throw new BizException(ResponseStatusEnum.EMBY_INFO_DISABLED);
         }
      }
   }

   @Override
   public List<EmbyUserCustomResponse.ServerOption> listAccessibleServers(EmbyUser embyUser) {
      if (embyUser == null) {
         return Collections.emptyList();
      } else {
         List<Long> embyInfoIds;
         if (this.isActiveAdmin(embyUser)) {
            embyInfoIds = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
               .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
               .eq(EmbyInfo::getStatus, Integer.valueOf(0))
               .list()
               .stream()
               .map(EmbyInfo::getId)
               .filter(Objects::nonNull)
               .distinct()
               .toList();
         } else if (embyUser.getIdentityGroupId() != null) {
            embyInfoIds = new LambdaQueryChainWrapper<>(this.getBaseMapper())
               .eq(EmbyUser::getIdentityGroupId, embyUser.getIdentityGroupId())
               .eq(EmbyUser::getUserStatus, Integer.valueOf(0))
               .isNotNull(EmbyUser::getEmbyInfoId)
               .list()
               .stream()
               .map(EmbyUser::getEmbyInfoId)
               .filter(Objects::nonNull)
               .distinct()
               .toList();
         } else {
            embyInfoIds = embyUser.getEmbyInfoId() == null ? Collections.emptyList() : List.of(embyUser.getEmbyInfoId());
         }

         if (embyInfoIds.isEmpty()) {
            return Collections.emptyList();
         } else {
            Map<Long, Integer> order = new HashMap<>();

            for (int i = 0; i < embyInfoIds.size(); i++) {
               order.putIfAbsent(embyInfoIds.get(i), i);
            }

            return new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
               .in(EmbyInfo::getId, embyInfoIds)
               .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
               .eq(EmbyInfo::getStatus, Integer.valueOf(0))
               .list()
               .stream()
               .sorted(Comparator.comparingInt(info -> order.getOrDefault(info.getId(), Integer.MAX_VALUE)))
               .map(this::toServerOption)
               .toList();
         }
      }
   }

   @Override
   public List<Long> listAccessibleEmbyInfoIds(EmbyUser embyUser) {
      return this.listAccessibleServers(embyUser).stream().map(EmbyUserCustomResponse.ServerOption::getEmbyInfoId).filter(Objects::nonNull).toList();
   }

   private boolean isActiveAdmin(EmbyUser embyUser) {
      return embyUser != null && embyUser.getId() != null
         ? new LambdaQueryChainWrapper<>(this.getBaseMapper())
               .eq(EmbyUser::getId, embyUser.getId())
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .eq(EmbyUser::getUserStatus, Integer.valueOf(0))
               .eq(EmbyUser::getIsAdmin, Integer.valueOf(1))
               .count()
            > 0L
         : false;
   }

   private void populateAccessibleServers(EmbyUserCustomResponse response, EmbyUser embyUser) {
      if (response != null) {
         response.setServers(this.listAccessibleServers(embyUser));
      }
   }

   private EmbyUserCustomResponse.ServerOption toServerOption(EmbyInfo embyInfo) {
      EmbyUserCustomResponse.ServerOption option = new EmbyUserCustomResponse.ServerOption();
      option.setId(embyInfo.getId());
      option.setEmbyInfoId(embyInfo.getId());
      option.setServerName(embyInfo.getServerName());
      option.setEmbyUrl(embyInfo.getEmbyUrl());
      option.setEmbyOpenUrl(embyInfo.getEmbyOpenUrl());
      option.setEmbyServerId(embyInfo.getEmbyServerId());
      return option;
   }

   @Override
   public InsertUserResponse insertUser(EmbyUserSave embyUserSave) throws ApiException {
      if (Integer.valueOf(1).equals(embyUserSave.getIsAdmin())) {
         this.assertCurrentUserCanManageAdministrators();
      }

      if (embyUserSave.getRegisterChannel() == null) {
         embyUserSave.setRegisterChannel(RegisterChannelEnum.ADMIN_REGISTER.getCode());
      }

      return this.addUser(embyUserSave);
   }

   @Override
   public InsertUserResponse insertUserCard(InsertUserCardRequest insertUserCardRequest) throws ApiException {
      return this.insertUserCardInternal(insertUserCardRequest, "前台卡密激活");
   }

   @Override
   public InsertUserResponse insertUserCardByTelegram(InsertUserCardRequest insertUserCardRequest, String telegramChannelDetail) throws ApiException {
      return this.insertUserCardInternal(insertUserCardRequest, StringUtils.hasText(telegramChannelDetail) ? telegramChannelDetail : "Telegram卡密开号");
   }

   private InsertUserResponse insertUserCardInternal(InsertUserCardRequest insertUserCardRequest, String registerChannelDetail) throws ApiException {
      if (!this.isConfigEnabled("card_register_enabled")) {
         throw new BizException(ResponseStatusEnum.CARD_REGISTER_DISABLED);
      } else if (insertUserCardRequest == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      } else {
         this.validateCardOrInvitationRegisterUserName(insertUserCardRequest.getEmbyUserName());
         this.validateOptionalRegisterPassword(insertUserCardRequest.getPassword());
         String cardLockKey = this.registerCardLockKey(insertUserCardRequest.getCardPassword());
         String cardLockToken = this.tryRegisterLock(cardLockKey);
         if (!StringUtils.hasText(cardLockToken)) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该卡密正在激活中，请稍后再试");
         } else {
            InsertUserResponse insertUserResponse;
            CardSecurityManagement cardSecurityManagement;
            try {
               CardSecurityManagement cardPreview = this.findAvailableCardForRegister(insertUserCardRequest.getCardPassword());
               String nameLockKey = this.registerNameLockKey(cardPreview.getEmbyInfoId(), insertUserCardRequest.getEmbyUserName());
               String nameLockToken = this.tryRegisterLock(nameLockKey);
               if (!StringUtils.hasText(nameLockToken)) {
                  throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该用户名正在注册中，请稍后再试");
               }

               try {
                  cardSecurityManagement = this.getCardSecurityManagement(insertUserCardRequest.getCardPassword());
                  EmbyUserSave embyUserSave = new EmbyUserSave();
                  embyUserSave.setDay(cardSecurityManagement.getCardValidity());
                  embyUserSave.setRemarks(insertUserCardRequest.getRemarks());
                  embyUserSave.setEmbyUserPassword(insertUserCardRequest.getPassword());
                  embyUserSave.setEmbyUserName(insertUserCardRequest.getEmbyUserName());
                  embyUserSave.setEmbyInfoId(cardSecurityManagement.getEmbyInfoId());
                  embyUserSave.setHostLineType(cardSecurityManagement.getHostLineType());
                  embyUserSave.setRegisterChannel(RegisterChannelEnum.CARD_REGISTER.getCode());
                  embyUserSave.setRegisterChannelDetail(registerChannelDetail);
                  insertUserResponse = this.addUser(embyUserSave, cardSecurityManagement.getCopyfromuserid());
                  cardSecurityManagement.setUserId(insertUserResponse.getId());
                  cardSecurityManagement.setEmbyUserName(insertUserResponse.getEmbyUserName());
                  cardSecurityManagement.setUpdateUserName(insertUserResponse.getEmbyUserName());
                  cardSecurityManagement.setUpdateUserId(insertUserResponse.getId());
                  this.cardSecurityManagementService.updateById(cardSecurityManagement);
               } finally {
                  this.redisLockUtils.unlock(nameLockKey, nameLockToken);
               }
            } finally {
               this.redisLockUtils.unlock(cardLockKey, cardLockToken);
            }

            if (cardSecurityManagement.getDistributorId() != null && cardSecurityManagement.getDistributorId() > 0L) {
               try {
                  Long inviterId = cardSecurityManagement.getDistributorId();
                  Long inviteeId = insertUserResponse.getId();
                  long count = new LambdaQueryChainWrapper<>(this.userInvitationService.getBaseMapper()).eq(UserInvitation::getInviteeId, inviteeId).count();
                  if (count == 0L) {
                     UserInvitation invitation = new UserInvitation();
                     invitation.setInviterId(inviterId);
                     invitation.setInviteeId(inviteeId);
                     invitation.setInvitationSource("CARD_KEY");
                     this.userInvitationService.save(invitation);
                     if (cardSecurityManagement.getDistributorId() != null) {
                        int rewardPoints = 10;
                        String configValue = this.systemConfigService.getConfigValue("distribution_invite_reward");
                        if (configValue != null) {
                           try {
                              rewardPoints = Integer.parseInt(configValue);
                           } catch (NumberFormatException var23) {
                              log.error("Failed to parse distribution_invite_reward config: {}", configValue);
                           }
                        }

                        this.userPointsService
                           .addPoints(cardSecurityManagement.getDistributorId(), rewardPoints, "INVITE_REWARD", "邀请用户: " + insertUserResponse.getEmbyUserName());
                     }
                  }
               } catch (Exception var24) {
                  log.error("处理分销邀请逻辑异常", (Throwable)var24);
               }
            }

            this.publishCardRegisterSuccessNotifyEvent(insertUserResponse, cardSecurityManagement);
            return insertUserResponse;
         }
      }
   }

   private CardSecurityManagement findAvailableCardForRegister(String cardPassword) {
      if (!StringUtils.hasText(cardPassword)) {
         throw new BizException(ResponseStatusEnum.CARD_NOT_EXIST);
      } else {
         CardSecurityManagement cardSecurityManagement = new LambdaQueryChainWrapper<>(this.cardSecurityManagementService.getBaseMapper())
            .eq(CardSecurityManagement::getCardPassword, cardPassword)
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
            .last("limit 1")
            .one();
         if (cardSecurityManagement == null) {
            throw new BizException(ResponseStatusEnum.CARD_USED_OR_NOT_EXIST);
         } else {
            return cardSecurityManagement;
         }
      }
   }

   private CardSecurityManagement getCardSecurityManagement(String cardPassword) {
      boolean update = new LambdaUpdateChainWrapper<>(this.cardSecurityManagementService.getBaseMapper())
         .set(CardSecurityManagement::getCardStatus, Integer.valueOf(1))
         .eq(CardSecurityManagement::getCardPassword, cardPassword)
         .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
         .update();
      if (!update) {
         throw new BizException(ResponseStatusEnum.CARD_USED);
      } else {
         CardSecurityManagement cardSecurityManagement = new LambdaQueryChainWrapper<>(this.cardSecurityManagementService.getBaseMapper())
            .eq(CardSecurityManagement::getCardPassword, cardPassword)
            .one();
         if (cardSecurityManagement == null) {
            throw new BizException(ResponseStatusEnum.CARD_NOT_EXIST);
         } else {
            cardSecurityManagement.setCardStatus(1);
            return cardSecurityManagement;
         }
      }
   }

   private InsertUserResponse addUser(EmbyUserSave embyUserSave) throws ApiException {
      return this.addUser(embyUserSave, null);
   }

   private InsertUserResponse addUser(EmbyUserSave embyUserSave, String copyfromuserid) throws ApiException {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUserSave.getEmbyInfoId());
      Long targetEmbyInfoId = embyUserSave.getEmbyInfoId() != null ? embyUserSave.getEmbyInfoId() : serverConfig.id();
      String name;
      if (StringUtils.hasText(embyUserSave.getEmbyUserName())) {
         name = embyUserSave.getEmbyUserName();
         this.validateUserNameFormat(name);
         this.validateReservedUserName(name);
         Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(EmbyUser::getEmbyUserName, embyUserSave.getEmbyUserName())
            .eq(EmbyUser::getEmbyInfoId, targetEmbyInfoId)
            .count();
         if (count > 0L) {
            throw new BizException(ResponseStatusEnum.USER_EXIST);
         }
      } else {
         name = DateUtil.format(new Date(), "yyyyMMdd") + RandomUtil.randomNumbers(3);
      }

      String rawPassword = StringUtils.hasText(embyUserSave.getEmbyUserPassword()) ? embyUserSave.getEmbyUserPassword() : RandomUtil.randomNumbers(6);

      InsertUserResponse var8;
      try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy.guardIndependentRegistration(name, rawPassword)) {
         var8 = this.createSingleIndependentUser(embyUserSave, serverConfig, targetEmbyInfoId, name, rawPassword, copyfromuserid);
      }

      return var8;
   }

   private InsertUserResponse createSingleIndependentUser(
      EmbyUserSave embyUserSave,
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      Long targetEmbyInfoId,
      String name,
      String rawPassword,
      String copyfromuserid
   ) throws ApiException {
      UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
      CreateUserByName createUserByName = new CreateUserByName();
      createUserByName.setName(name);
      createUserByName.setCopyFromUserId(this.resolveCopyfromuserid(serverConfig, copyfromuserid));
      createUserByName.setUserCopyOptions(Arrays.asList(LibraryUserCopyOptions.USERPOLICY, LibraryUserCopyOptions.USERCONFIGURATION));
      UserDto userDto = userServiceApi.postUsersNew(createUserByName);
      if (!Integer.valueOf(1).equals(embyUserSave.getIsAdmin())) {
         this.applyInitialLibraryAccessOrDelete(userServiceApi, targetEmbyInfoId, userDto.getId());
      }

      String embyUserPassword = SaSecureUtil.md5(rawPassword);
      UpdateUserPassword updateUserPassword = new UpdateUserPassword();
      updateUserPassword.setNewPw(rawPassword);
      userServiceApi.postUsersByIdPassword(updateUserPassword, userDto.getId());
      EmbyUser embyUserCustom = new EmbyUser();
      embyUserCustom.setEmbyUserId(userDto.getId());
      embyUserCustom.setEmbyUserName(userDto.getName());
      embyUserCustom.setEmbyUserPassword(embyUserPassword);
      embyUserCustom.setIdentityGroupId(EmbyUserIdentityUtils.newIdentityGroupId());
      Integer hostLineType = HostLineTypeEnum.normalize(embyUserSave.getHostLineType());
      embyUserCustom.setExpirationDate(this.resolveExpirationDate(hostLineType, embyUserSave.getDay()));
      embyUserCustom.setUserStatus(0);
      embyUserCustom.setRemarks(embyUserSave.getRemarks());
      embyUserCustom.setRegisterChannel(embyUserSave.getRegisterChannel());
      embyUserCustom.setIsAdmin(embyUserSave.getIsAdmin() != null ? embyUserSave.getIsAdmin() : 0);
      embyUserCustom.setHostLineType(hostLineType);
      String requestCount = this.configCacheLoaderUtils.getConfigValue("request_count");
      if (StringUtils.hasText(requestCount)) {
         embyUserCustom.setRequestPackagesCount(Integer.valueOf(requestCount));
      }

      embyUserCustom.setEmbyInfoId(targetEmbyInfoId);
      this.save(embyUserCustom);
      this.saveRegisterRecord(embyUserCustom, embyUserSave.getRegisterChannel(), this.resolveRegisterRecordDetailForAddUser(embyUserSave));
      InsertUserResponse insertUserResponse = BeanUtils.convert(embyUserCustom, InsertUserResponse.class);
      insertUserResponse.setEmbyUserPassword(rawPassword);
      String embyUrl = serverConfig.url();
      if (StringUtils.hasText(embyUrl)) {
         String urlStr = embyUrl;
         if (embyUrl.endsWith("/emby/")) {
            urlStr = embyUrl.substring(0, embyUrl.length() - 6);
         } else if (embyUrl.endsWith("/emby")) {
            urlStr = embyUrl.substring(0, embyUrl.length() - 5);
         }

         if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://")) {
            urlStr = "http://" + urlStr;
         }

         try {
            URL url = URLUtil.url(urlStr);
            insertUserResponse.setProtocol(url.getProtocol());
            insertUserResponse.setHost(url.getHost());
            int port = url.getPort();
            if (port == -1) {
               port = "https".equalsIgnoreCase(url.getProtocol()) ? 443 : 80;
            }

            insertUserResponse.setPort(port);
         } catch (Exception var20) {
            log.warn("解析 Emby URL 失败: {}", embyUrl, var20);
         }
      }

      return insertUserResponse;
   }

   String resolveCopyfromuserid(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String copyfromuserid) {
      return StringUtils.hasText(copyfromuserid) ? copyfromuserid.trim() : this.getCopyfromuserid(serverConfig);
   }

   @Override
   public void updatePassword(EmbyUserUpdatePassword embyUserUpdatePassword) {
      if (!StringUtils.hasText(embyUserUpdatePassword.getEmbyUserPassword())) {
         throw new BizException(ResponseStatusEnum.PASSWORD_LENGTH_ERROR);
      } else if (CheckPasswordUtils.isContinuousChar(embyUserUpdatePassword.getEmbyUserPassword())) {
         throw new BizException(ResponseStatusEnum.PASSWORD_CONTAIN_CONTINUOUS_CHAR);
      } else {
         Long currentUserId = StpUtil.getLoginIdAsLong();
         if (!StringUtils.hasText(embyUserUpdatePassword.getOldPassword())) {
            throw new BizException(ResponseStatusEnum.PASSWORD_ERROR);
         } else if (embyUserUpdatePassword.getEmbyUserPassword().length() >= 6 && embyUserUpdatePassword.getEmbyUserPassword().length() <= 30) {
            EmbyUser embyUserCustom = new LambdaQueryChainWrapper<>(this.getBaseMapper())
               .eq(EmbyUser::getId, currentUserId)
               .eq(EmbyUser::getUserStatus, Integer.valueOf(0))
               .one();
            if (embyUserCustom == null) {
               throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
            } else if (!embyUserCustom.getEmbyUserPassword().equals(SaSecureUtil.md5(embyUserUpdatePassword.getOldPassword()))) {
               throw new BizException(ResponseStatusEnum.PASSWORD_ERROR);
            } else {
               String rawNewPassword = embyUserUpdatePassword.getEmbyUserPassword();

               try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy
                     .guardCredentialChange(embyUserCustom.getId(), embyUserCustom.getIdentityGroupId(), embyUserCustom.getEmbyUserName(), rawNewPassword)) {
                  String embyUserPassword = SaSecureUtil.md5(rawNewPassword);
                  if (embyUserCustom.getIsAdmin() != 1) {
                     try {
                        EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUserCustom.getEmbyInfoId());
                        UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
                        UpdateUserPassword updateUserPassword = new UpdateUserPassword();
                        updateUserPassword.setNewPw(rawNewPassword);
                        userServiceApi.postUsersByIdPassword(updateUserPassword, embyUserCustom.getEmbyUserId());
                     } catch (ApiException var11) {
                        if (var11.getCode() == 404) {
                           log.error(var11.getResponseBody());
                           throw new BizException(ResponseStatusEnum.EMBY_USER_NOT_EXIST);
                        }

                        var11.printStackTrace();
                        throw new BizException(ResponseStatusEnum.EMBY_EXCEPTIION);
                     }
                  }

                  embyUserCustom.setEmbyUserPassword(embyUserPassword);
                  this.updateById(embyUserCustom);
               }
            }
         } else {
            throw new BizException(ResponseStatusEnum.PASSWORD_LENGTH_ERROR);
         }
      }
   }

   @Override
   public EmbyUserCustomResponse updateProfile(EmbyUserProfileUpdate embyUserProfileUpdate) {
      Long currentUserId = StpUtil.getLoginIdAsLong();
      EmbyUser currentUser = this.getById(currentUserId);
      if (currentUser == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         EmbyUserProfileUpdate profileUpdate = embyUserProfileUpdate == null ? new EmbyUserProfileUpdate() : embyUserProfileUpdate;
         this.lambdaUpdate()
            .eq(EmbyUser::getId, currentUserId)
            .set(EmbyUser::getEmail, this.normalizeProfileText(profileUpdate.getEmail()))
            .set(EmbyUser::getMobile, this.normalizeProfileText(profileUpdate.getMobile()))
            .set(EmbyUser::getGender, this.normalizeProfileText(profileUpdate.getGender()))
            .set(EmbyUser::getBirthday, profileUpdate.getBirthday())
            .set(EmbyUser::getInterests, this.normalizeProfileText(profileUpdate.getInterests()))
            .set(EmbyUser::getRemarks, this.normalizeProfileText(profileUpdate.getRemarks()))
            .update();
         EmbyUser latestUser = this.getById(currentUserId);
         StpUtil.getSession().set("user", latestUser);
         return this.getEmbyUserById();
      }
   }

   private String normalizeProfileText(String value) {
      return !StringUtils.hasText(value) ? null : value.trim();
   }

   @Override
   public void updateUser(EmbyUserUpdate embyUserUpdate) {
      if (!StringUtils.hasText(embyUserUpdate.getEmbyUserPassword())) {
         throw new BizException(ResponseStatusEnum.PASSWORD_LENGTH_ERROR);
      } else if (CheckPasswordUtils.isContinuousChar(embyUserUpdate.getEmbyUserPassword())) {
         throw new BizException(ResponseStatusEnum.PASSWORD_CONTAIN_CONTINUOUS_CHAR);
      } else if (embyUserUpdate.getId() == null) {
         throw new BizException(ResponseStatusEnum.USER_ID_NOT_NULl);
      } else if (embyUserUpdate.getEmbyUserPassword().length() >= 6 && embyUserUpdate.getEmbyUserPassword().length() <= 30) {
         EmbyUser embyUserCustom = this.getById(embyUserUpdate.getId());
         if (embyUserCustom != null && Integer.valueOf(0).equals(embyUserCustom.getUserStatus())) {
            this.assertUserCanBeEdited(embyUserCustom);
            String rawNewPassword = embyUserUpdate.getEmbyUserPassword();

            try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy
                  .guardCredentialChange(embyUserCustom.getId(), embyUserCustom.getIdentityGroupId(), embyUserCustom.getEmbyUserName(), rawNewPassword)) {
               String embyUserPassword = SaSecureUtil.md5(rawNewPassword);
               if (embyUserCustom.getIsAdmin() != 1) {
                  try {
                     EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUserCustom.getEmbyInfoId());
                     UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
                     UpdateUserPassword updateUserPassword = new UpdateUserPassword();
                     updateUserPassword.setNewPw(rawNewPassword);
                     userServiceApi.postUsersByIdPassword(updateUserPassword, embyUserCustom.getEmbyUserId());
                  } catch (ApiException var10) {
                     if (var10.getCode() == 404) {
                        log.error(var10.getResponseBody());
                        throw new BizException(ResponseStatusEnum.EMBY_USER_NOT_EXIST);
                     }

                     var10.printStackTrace();
                     throw new BizException(ResponseStatusEnum.EMBY_EXCEPTIION);
                  }
               }

               embyUserCustom.setEmbyUserPassword(embyUserPassword);
               this.updateById(embyUserCustom);
            }
         } else {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         }
      } else {
         throw new BizException(ResponseStatusEnum.PASSWORD_LENGTH_ERROR);
      }
   }

   @Override
   public Page<EmbyUserResponse> select(MybatisPlusPage<EmbyUserRequest> page) {
      Integer hostLineTypeFilter = page.getObject().getHostLineType();
      page.getObject().setHostLineType(null);
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      this.applyHostLineAccessFilter(queryWrapper, hostLineTypeFilter);
      this.applyTelegramBoundFilter(queryWrapper, page.getObject());
      applyAdministratorVisibility(queryWrapper, this.currentLoginIdOrNull(), this.isCurrentUserPrimaryAdmin());
      queryWrapper.select(
         new Object[]{
            "id",
            "emby_user_id",
            "emby_user_name",
            "emby_user_password",
            "is_admin",
            "is_primary_admin as isPrimaryAdmin",
            "user_status",
            "expiration_date",
            "create_datetime",
            "update_datetime",
            "create_user_name",
            "update_user_name",
            "update_user_id",
            "create_user_id",
            "del_flag",
            "DATEDIFF(expiration_date, CURRENT_TIMESTAMP()) as expireDateCount",
            "remarks",
            "request_packages_count as requestPackagesCount",
            "register_channel as registerChannel",
            "is_distributor as isDistributor",
            "host_line_type as hostLineType",
            "emby_info_id"
         }
      );
      if (page.getObject().getUserStatus() != null && page.getObject().getUserStatus() == 2) {
         queryWrapper.last(
            "and (host_line_type <> 1 or host_line_type is null) and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) <= 3 and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) >= 0 "
               + userListOrderBy()
         );
      } else {
         queryWrapper.eq(page.getObject().getUserStatus() != null, "user_status", page.getObject().getUserStatus());
         queryWrapper.last(userListOrderBy());
      }

      Page<EmbyUserResponse> embyUserResponsePage = MpConvert.page(
         queryWrapper, this.getBaseMapper(), EmbyUserResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      this.applyWhitelistNeverExpires(embyUserResponsePage.getRecords());
      this.attachUserListBindings(embyUserResponsePage.getRecords());
      return embyUserResponsePage;
   }

   @Override
   public Page<EmbyUserResponse> selectDistributor(MybatisPlusPage<EmbyUserRequest> page) {
      boolean isAdmin = StpUtil.hasPermission("admin");
      Long distributorId = null;
      Integer hostLineTypeFilter = page.getObject().getHostLineType();
      page.getObject().setHostLineType(null);
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      this.applyHostLineAccessFilter(queryWrapper, hostLineTypeFilter);
      this.applyTelegramBoundFilter(queryWrapper, page.getObject());
      if (isAdmin) {
         applyAdministratorVisibility(queryWrapper, this.currentLoginIdOrNull(), this.isCurrentUserPrimaryAdmin());
      } else {
         applyPrimaryAdminVisibility(queryWrapper, false);
      }

      if (isAdmin) {
         queryWrapper.inSql(
            "id",
            "SELECT DISTINCT user_id FROM card_security_management WHERE user_id IS NOT NULL AND card_status = 1 AND distributor_id IS NOT NULL AND distributor_id > 0"
         );
      } else {
         distributorId = this.getCurrentDistributorIdOrThrow();
         queryWrapper.inSql(
            "id",
            "SELECT DISTINCT user_id FROM card_security_management WHERE user_id IS NOT NULL AND card_status = 1 AND distributor_id IS NOT NULL AND distributor_id > 0 AND distributor_id = "
               + distributorId
         );
      }

      queryWrapper.eq("register_channel", Integer.valueOf(RegisterChannelEnum.CARD_REGISTER.getCode()));
      queryWrapper.select(
         new Object[]{
            "id",
            "emby_user_id",
            "emby_user_name",
            "emby_user_password",
            "is_admin",
            "is_primary_admin as isPrimaryAdmin",
            "user_status",
            "expiration_date",
            "create_datetime",
            "update_datetime",
            "create_user_name",
            "update_user_name",
            "update_user_id",
            "create_user_id",
            "del_flag",
            "DATEDIFF(expiration_date, CURRENT_TIMESTAMP()) as expireDateCount",
            "remarks",
            "request_packages_count as requestPackagesCount",
            "register_channel as registerChannel",
            "is_distributor as isDistributor",
            "host_line_type as hostLineType",
            "emby_info_id"
         }
      );
      if (page.getObject().getUserStatus() != null && page.getObject().getUserStatus() == 2) {
         queryWrapper.last(
            "and (host_line_type <> 1 or host_line_type is null) and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) <= 3 and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) >= 0 order by case when is_admin = 1 then 0 else 1 end, id desc"
         );
      } else {
         queryWrapper.eq(page.getObject().getUserStatus() != null, "user_status", page.getObject().getUserStatus());
         queryWrapper.last("order by case when is_admin = 1 then 0 else 1 end, id desc");
      }

      Page<EmbyUserResponse> embyUserResponsePage = MpConvert.page(
         queryWrapper, this.getBaseMapper(), EmbyUserResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      this.applyWhitelistNeverExpires(embyUserResponsePage.getRecords());
      this.attachDistributorUserListBindings(embyUserResponsePage.getRecords(), distributorId);
      return embyUserResponsePage;
   }

   @Override
   public void disableUser(DisableUserRequest disableUserRequest) {
      try {
         Long embyInfoId = null;
         EmbyUser user = this.lambdaQuery().eq(EmbyUser::getEmbyUserId, disableUserRequest.getEmbyUserId()).one();
         if (user != null) {
            this.assertUserCanBeManaged(user);
            embyInfoId = user.getEmbyInfoId();
         }

         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyInfoId);
         UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
         UserDto userDto = userServiceApi.getUsersById(disableUserRequest.getEmbyUserId());
         UserPolicy userPolicy = userDto.getPolicy();
         userPolicy.setIsDisabled(true);
         userServiceApi.postUsersByIdPolicy(userPolicy, disableUserRequest.getEmbyUserId());
      } catch (ApiException var8) {
         if (var8.getCode() != 404) {
            throw new BizException(ResponseStatusEnum.USER_DISABLE_FAILED);
         }

         log.warn("Emby用户不存在，跳过禁用，embyUserId={}", disableUserRequest.getEmbyUserId());
      }

      QueryWrapper<EmbyUser> embyUserQueryWrapper = new QueryWrapper<>();
      embyUserQueryWrapper.eq("emby_user_id", disableUserRequest.getEmbyUserId());
      EmbyUser embyUser = new EmbyUser();
      embyUser.setUserStatus(1);
      embyUser.setDisableReason(null);
      embyUser.setDisabledDatetime(null);
      this.update(embyUser, embyUserQueryWrapper);
   }

   @Override
   public EmbyUserCustomResponse renewAdmin(Long userId, Integer day) {
      return this.renew(userId, day, RenewChannelEnum.ADMIN_RENEW.getCode(), "后台手动续费");
   }

   @Override
   public EmbyUserCustomResponse renewByPoints(Long userId, Integer day) {
      return this.renew(userId, day, RenewChannelEnum.POINTS_REDEEM.getCode(), "Web积分兑换续期");
   }

   @Override
   public void validatePointsRedeemCreate(String userName, String password, Long serverId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(serverId);
      this.validateRegisterUserName(userName, serverConfig.id());
      this.validateOptionalRegisterPassword(password);
   }

   private EmbyUserCustomResponse renew(Long userId, Integer day, Integer channel, String channelDetail) {
      EmbyUser embyUser = this.getById(userId);
      if (embyUser != null && Integer.valueOf(0).equals(embyUser.getUserStatus())) {
         this.assertUserCanBeManaged(embyUser);
         if (embyUser.getUserStatus() == 1) {
            throw new BizException(ResponseStatusEnum.USER_DISABLED);
         } else {
            Date beforeExpiration = embyUser.getExpirationDate();
            embyUser.setExpirationDate(this.resolveRenewedExpirationDate(embyUser, day));
            embyUser.setUserStatus(0);
            this.updateById(embyUser);
            this.saveRenewRecord(embyUser, channel, channelDetail, day, beforeExpiration);
            return BeanUtils.convert(embyUser, EmbyUserCustomResponse.class);
         }
      } else {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      }
   }

   private void updateEmbyUserStatus(EmbyUser embyUser, boolean disabled, boolean ignoreNotFound) {
      this.updateEmbyUserStatus(embyUser, disabled, ignoreNotFound, false);
   }

   private void updateEmbyUserStatus(EmbyUser embyUser, boolean disabled, boolean ignoreNotFound, boolean updateSession) {
      try {
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUser.getEmbyInfoId());
         UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
         UserDto userDto = userServiceApi.getUsersById(embyUser.getEmbyUserId());
         UserPolicy userPolicy = userDto.getPolicy();
         userPolicy.setIsDisabled(disabled);
         userServiceApi.postUsersByIdPolicy(userPolicy, embyUser.getEmbyUserId());
         if (updateSession && StpUtil.isLogin()) {
            StpUtil.getSession().set("user", embyUser);
         }
      } catch (ApiException var9) {
         if (ignoreNotFound && var9.getCode() == 404) {
            log.warn("Emby用户不存在，跳过启用，embyUserId={}", embyUser.getEmbyUserId());
         } else {
            throw new BizException(ResponseStatusEnum.USER_ENABLE_ERROR);
         }
      } catch (BizException var10) {
         throw var10;
      } catch (Exception var11) {
         throw new BizException(ResponseStatusEnum.USER_ENABLE_ERROR);
      }
   }

   @Override
   public EmbyUserCustomResponse renewUser(String cardPassword) {
      long userId = StpUtil.getLoginIdAsLong();
      EmbyUser embyUser = this.getById(Long.valueOf(userId));
      return this.renewUserByCard(cardPassword, embyUser, "前台卡密续费", true);
   }

   @Override
   public EmbyUserCustomResponse renewUserByTelegramCard(String cardPassword, Long userId, String telegramChannelDetail) {
      if (userId == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         EmbyUser embyUser = this.getById(userId);
         return this.renewUserByCard(cardPassword, embyUser, StringUtils.hasText(telegramChannelDetail) ? telegramChannelDetail : "Telegram卡密续费", false);
      }
   }

   private EmbyUserCustomResponse renewUserByCard(String cardPassword, EmbyUser embyUser, String renewChannelDetail, boolean updateSession) {
      if (embyUser != null && embyUser.getDelFlag() != 1) {
         Integer userStatus = embyUser.getUserStatus();
         String cardLockKey = this.registerCardLockKey(cardPassword);
         String cardLockToken = this.tryRegisterLock(cardLockKey);
         if (!StringUtils.hasText(cardLockToken)) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该卡密正在使用中，请稍后再试");
         } else {
            String userLockKey = "foam:renew:user:" + embyUser.getId();
            String userLockToken = null;

            EmbyUserCustomResponse var14;
            try {
               userLockToken = this.tryRegisterLock(userLockKey);
               if (!StringUtils.hasText(userLockToken)) {
                  throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该用户正在续费中，请稍后再试");
               }

               CardSecurityManagement cardSecurityManagement = this.getCardSecurityManagement(cardPassword);
               if (!Objects.equals(embyUser.getEmbyInfoId(), cardSecurityManagement.getEmbyInfoId())) {
                  throw new BizException(ResponseStatusEnum.PLEASE_USE_CORRECT_CARD_KEY);
               }

               cardSecurityManagement.setUserId(embyUser.getId());
               cardSecurityManagement.setEmbyUserName(embyUser.getEmbyUserName());
               this.cardSecurityManagementService.updateById(cardSecurityManagement);
               Date beforeExpiration = embyUser.getExpirationDate();
               Integer beforeHostLineType = HostLineTypeEnum.normalize(embyUser.getHostLineType());
               Integer renewedHostLineType = HostLineTypeEnum.normalize(cardSecurityManagement.getHostLineType());
               embyUser.setExpirationDate(this.resolveCardRenewedExpirationDate(embyUser, cardSecurityManagement, beforeHostLineType, renewedHostLineType));
               embyUser.setHostLineType(renewedHostLineType);
               embyUser.setUserStatus(0);
               embyUser.setDisableReason(null);
               embyUser.setDisabledDatetime(null);
               if (this.isWhitelistHostLineType(renewedHostLineType)) {
                  embyUser.setExpireDateCount(null);
               }

               this.updateById(embyUser);
               if (this.isWhitelistHostLineType(renewedHostLineType)) {
                  new LambdaUpdateChainWrapper<>(this.getBaseMapper())
                     .eq(EmbyUser::getId, embyUser.getId())
                     .set(EmbyUser::getExpirationDate, null)
                     .set(EmbyUser::getExpireDateCount, null)
                     .update();
                  this.publishSimultaneousPlaybackUserConfigCleanup(embyUser);
               }

               this.saveRenewRecord(
                  embyUser, RenewChannelEnum.CARD_RENEW.getCode(), renewChannelDetail, cardSecurityManagement.getCardValidity(), beforeExpiration
               );
               this.publishCardRenewSuccessNotifyEvent(embyUser, cardSecurityManagement);
               if (userStatus == 1) {
                  this.updateEmbyUserStatus(embyUser, false, false, updateSession);
               } else if (updateSession && StpUtil.isLogin()) {
                  StpUtil.getSession().set("user", embyUser);
               }

               var14 = BeanUtils.convert(embyUser, EmbyUserCustomResponse.class);
            } finally {
               this.redisLockUtils.unlock(userLockKey, userLockToken);
               this.redisLockUtils.unlock(cardLockKey, cardLockToken);
            }

            return var14;
         }
      } else {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      }
   }

   @Override
   public int extendExpiredUser(Long embyInfoId, Integer expiredDayRange, Integer extensionDay) {
      if (extensionDay == null || extensionDay <= 0) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      } else if (expiredDayRange != null && expiredDayRange <= 0) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      } else {
         Date now = new Date();
         LambdaQueryChainWrapper<EmbyUser> query = this.lambdaQuery()
            .eq(EmbyUser::getIsAdmin, Integer.valueOf(0))
            .isNotNull(EmbyUser::getExpirationDate)
            .and(wrapper -> wrapper.ne(EmbyUser::getHostLineType, Integer.valueOf(HostLineTypeEnum.WHITELIST.getCode())).or().isNull(EmbyUser::getHostLineType));
         if (embyInfoId != null) {
            query = query.eq(EmbyUser::getEmbyInfoId, embyInfoId);
         }

         if (expiredDayRange != null) {
            Date thresholdDate = DateUtil.offsetDay(now, -expiredDayRange);
            query = query.ge(EmbyUser::getExpirationDate, thresholdDate);
         }

         List<EmbyUser> targetUsers = query.list();
         List<EmbyUser> disabledUsers = new ArrayList<>();
         targetUsers.forEach(user -> {
            if (DateUtil.compare(user.getExpirationDate(), now) < 0) {
               user.setExpirationDate(DateUtil.offsetDay(now, extensionDay));
            } else {
               user.setExpirationDate(DateUtil.offsetDay(user.getExpirationDate(), extensionDay));
            }

            boolean wasDisabled = Objects.equals(user.getUserStatus(), 1);
            user.setUserStatus(0);
            user.setDisableReason(null);
            user.setDisabledDatetime(null);
            if (wasDisabled) {
               disabledUsers.add(user);
            }
         });
         if (!targetUsers.isEmpty()) {
            this.updateBatchById(targetUsers);
            disabledUsers.forEach(user -> this.updateEmbyUserStatus(user, false, true));
         }

         return targetUsers.size();
      }
   }

   @Override
   public void logout() {
      StpUtil.logout();
   }

   @Override
   public EmbyUserCustomResponse getEmbyUserById() {
      EmbyUser embyUser = this.getById(((EmbyUser)StpUtil.getSession().get("user")).getId());
      if (embyUser == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         EmbyUserCustomResponse embyUserCustomResponse = Binder.convertAndBindRelations(embyUser, EmbyUserCustomResponse.class);
         List<UserOauthBinding> bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper).eq(UserOauthBinding::getUserId, embyUser.getId()).list();
         if (!CollectionUtils.isEmpty(bindings)) {
            List<UserOauthBindingResponse> bindingResponses = BeanUtils.convertList(bindings, UserOauthBindingResponse.class);
            embyUserCustomResponse.setOauthBindings(bindingResponses);
         } else {
            embyUserCustomResponse.setOauthBindings(new ArrayList<>());
         }

         this.populateAvatar(embyUserCustomResponse, embyUser.getId());
         this.populateAccessibleServers(embyUserCustomResponse, embyUser);
         this.populateAdminMenuPermissions(embyUserCustomResponse, embyUser);
         Object themeObj = this.redisTemplate.opsForValue().get("user:theme:" + embyUser.getId());
         if (themeObj != null) {
            embyUserCustomResponse.setTheme(themeObj.toString());
         }

         return embyUserCustomResponse;
      }
   }

   @Override
   public void deleteByUserId(List<Long> userIdList) throws ApiException {
      if (!CollectionUtils.isEmpty(userIdList)) {
         List<EmbyUser> targetUsers = this.listByIds(userIdList);
         targetUsers.forEach(this::assertUserCanBeManaged);
         Long currentLoginId = this.currentLoginIdOrNull();
         if (currentLoginId != null && userIdList.contains(currentLoginId)) {
            throw new BizException(ResponseStatusEnum.CURRENT_USER_CANNOT_DELETE);
         } else {
            for (Long userId : userIdList) {
               EmbyUser embyUser = this.getById(userId);
               if (embyUser != null) {
                  this.userOauthBindingMapper.delete(new LambdaQueryWrapper<UserOauthBinding>().eq(UserOauthBinding::getUserId, userId));
                  this.removeById(userId);

                  try {
                     EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUser.getEmbyInfoId());
                     UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
                     userServiceApi.deleteUsersById(embyUser.getEmbyUserId());
                  } catch (ApiException var9) {
                     if (var9.getCode() != 404) {
                        throw var9;
                     }

                     log.error(var9.getResponseBody());
                  } catch (BizException var10) {
                     log.warn("Emby服务器配置异常，跳过远程用户删除: userId={}, error={}", userId, var10.getMessage());
                  }

                  this.publishSimultaneousPlaybackUserConfigCleanup(embyUser);
               }
            }
         }
      }
   }

   @Override
   public UserStatsResponse userStats() {
      UserStatsResponse userStatsResponse = new UserStatsResponse();
      Long currentUserId = this.currentLoginIdOrNull();
      boolean currentUserPrimaryAdmin = this.isCurrentUserPrimaryAdmin();
      QueryWrapper<EmbyUser> activeQuery = new QueryWrapper<>();
      applyAdministratorVisibility(activeQuery, currentUserId, currentUserPrimaryAdmin);
      activeQuery.eq("user_status", Integer.valueOf(0));
      Long activeUserCount = this.count(activeQuery);
      QueryWrapper<EmbyUser> inactiveQuery = new QueryWrapper<>();
      applyAdministratorVisibility(inactiveQuery, currentUserId, currentUserPrimaryAdmin);
      inactiveQuery.eq("user_status", Integer.valueOf(1));
      Long inactiveUserCount = this.count(inactiveQuery);
      QueryWrapper<EmbyUser> expiringQuery = new QueryWrapper<>();
      applyAdministratorVisibility(expiringQuery, currentUserId, currentUserPrimaryAdmin);
      expiringQuery.eq("user_status", Integer.valueOf(0))
         .last("and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) <= 3 and DATEDIFF(expiration_date,CURRENT_TIMESTAMP()) >= 0");
      Long expiringSoonUserCount = this.count(expiringQuery);
      userStatsResponse.setActiveUserCount(activeUserCount);
      userStatsResponse.setInactiveUserCount(inactiveUserCount);
      userStatsResponse.setExpiringSoonUserCount(expiringSoonUserCount);
      userStatsResponse.setAllUserCount(activeUserCount + inactiveUserCount + expiringSoonUserCount);
      return userStatsResponse;
   }

   @Override
   public List<EmbyServerUserStatsResponse> serverUserStats() {
      return this.getBaseMapper().selectServerUserStats(this.currentLoginIdOrNull(), this.isCurrentUserPrimaryAdmin());
   }

   @Override
   public List<EmbyServerUserStatsResponse> serverUserStatsDistributor() {
      boolean isAdmin = StpUtil.hasPermission("admin");
      if (isAdmin) {
         return this.getBaseMapper().selectServerUserStatsDistributor(null);
      } else {
         Long distributorId = this.getCurrentDistributorIdOrThrow();
         return this.getBaseMapper().selectServerUserStatsDistributor(distributorId);
      }
   }

   @Override
   public void enableUser(Long userId) {
      try {
         EmbyUser embyUser = this.getById(userId);
         if (embyUser == null) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else {
            this.assertUserCanBeManaged(embyUser);
            embyUser.setUserStatus(0);
            embyUser.setDisableReason(null);
            embyUser.setDisabledDatetime(null);
            this.updateById(embyUser);
            EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUser.getEmbyInfoId());
            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
            UserDto userDto = userServiceApi.getUsersById(embyUser.getEmbyUserId());
            UserPolicy userPolicy = userDto.getPolicy();
            userPolicy.setIsDisabled(false);
            userServiceApi.postUsersByIdPolicy(userPolicy, embyUser.getEmbyUserId());
         }
      } catch (BizException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new BizException(ResponseStatusEnum.USER_ENABLE_ERROR);
      }
   }

   @Override
   public void updateUserData(EmbyUserUpdateData embyUserUpdateData) {
      EmbyUser existingUser = this.requireUser(embyUserUpdateData.getId());
      this.assertUserCanBeEdited(existingUser);
      Integer hostLineType = embyUserUpdateData.getHostLineType() == null ? null : HostLineTypeEnum.normalize(embyUserUpdateData.getHostLineType());
      boolean whitelistUser = this.isWhitelistHostLineType(hostLineType);
      boolean expirationDateProvided = embyUserUpdateData.isExpirationDateSet();
      boolean expirationDateChanged = expirationDateProvided && !Objects.equals(existingUser.getExpirationDate(), embyUserUpdateData.getExpirationDate());
      boolean expirationClearedByWhitelist = whitelistUser && existingUser.getExpirationDate() != null;
      if (expirationDateChanged || expirationClearedByWhitelist) {
         this.assertExpirationDateCanBeModified(existingUser);
      }

      if (whitelistUser) {
         embyUserUpdateData.setExpirationDate(null);
      }

      EmbyUser embyUser = BeanUtils.convert(embyUserUpdateData, EmbyUser.class);
      if (hostLineType != null) {
         embyUser.setHostLineType(hostLineType);
      }

      this.updateById(embyUser);
      if (expirationDateProvided || whitelistUser) {
         UpdateWrapper<EmbyUser> expirationUpdateWrapper = (UpdateWrapper<EmbyUser>)((UpdateWrapper)((UpdateWrapper)new UpdateWrapper()
                  .eq("id", embyUserUpdateData.getId()))
               .set("expiration_date", embyUserUpdateData.getExpirationDate()))
            .set("expire_date_count", null);
         this.getBaseMapper().update(null, expirationUpdateWrapper);
      }

      if (whitelistUser) {
         this.publishSimultaneousPlaybackUserConfigCleanup(existingUser);
      }

      EmbyUser sessionUser = (EmbyUser)StpUtil.getSession().get("user");
      if (sessionUser != null && embyUserUpdateData.getId() != null && Objects.equals(sessionUser.getId(), embyUserUpdateData.getId())) {
         StpUtil.getSession().set("user", this.getById(sessionUser.getId()));
      }
   }

   @Override
   public int batchUpdateExpirationDate(EmbyUserBatchExpirationUpdate request) {
      List<Long> userIds = request.getUserIds().stream().distinct().toList();
      List<EmbyUser> targetUsers = this.listByIds(userIds);
      if (targetUsers.size() != userIds.size()) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         for (EmbyUser targetUser : targetUsers) {
            this.assertExpirationDateCanBeModified(targetUser);
            if (this.isWhitelistHostLineType(targetUser.getHostLineType())) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "白名单用户为永不过期，不能修改到期时间");
            }
         }

         boolean currentUserPrimaryAdmin = this.isCurrentUserPrimaryAdmin();
         UpdateWrapper<EmbyUser> updateWrapper = new UpdateWrapper<EmbyUser>()
               .in("id", userIds)
               .and(wrapper -> wrapper.ne("is_primary_admin", Integer.valueOf(1)).or().isNull("is_primary_admin"))
               .and(wrapper -> wrapper.ne("host_line_type", Integer.valueOf(HostLineTypeEnum.WHITELIST.getCode())).or().isNull("host_line_type"))
               .set("expiration_date", request.getExpirationDate())
               .set("expire_date_count", null);
         if (!currentUserPrimaryAdmin) {
            updateWrapper.and(wrapper -> wrapper.ne("is_admin", Integer.valueOf(1)).or().isNull("is_admin"));
         }

         int affected = this.getBaseMapper().update(null, updateWrapper);
         if (affected != userIds.size()) {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED.getCode(), "用户角色或状态已变化，请刷新后重试");
         } else {
            return affected;
         }
      }
   }

   private boolean isWhitelistHostLineType(Integer hostLineType) {
      return hostLineType != null && HostLineTypeEnum.normalize(hostLineType) == HostLineTypeEnum.WHITELIST.getCode();
   }

   private Date resolveExpirationDate(Integer hostLineType, Integer days) {
      if (this.isWhitelistHostLineType(hostLineType)) {
         return null;
      } else {
         int validDays = days != null && days > 0 ? days : 30;
         return DateUtil.offsetDay(new Date(), validDays);
      }
   }

   private Date resolveRenewedExpirationDate(EmbyUser user, Integer days) {
      return this.resolveRenewedExpirationDate(user == null ? null : user.getHostLineType(), user == null ? null : user.getExpirationDate(), days);
   }

   private Date resolveCardRenewedExpirationDate(EmbyUser user, CardSecurityManagement card, Integer beforeHostLineType, Integer renewedHostLineType) {
      Date currentExpiration = user != null && !this.isWhitelistHostLineType(beforeHostLineType) ? user.getExpirationDate() : null;
      return this.resolveRenewedExpirationDate(renewedHostLineType, currentExpiration, card == null ? null : card.getCardValidity());
   }

   private Date resolveRenewedExpirationDate(Integer hostLineType, Date currentExpiration, Integer days) {
      if (this.isWhitelistHostLineType(hostLineType)) {
         return null;
      } else {
         int validDays = days != null && days > 0 ? days : 30;
         Date now = new Date();
         return currentExpiration != null && DateUtil.compare(currentExpiration, now) >= 0
            ? DateUtil.offsetDay(currentExpiration, validDays)
            : DateUtil.offsetDay(now, validDays);
      }
   }

   @Override
   public void updateUserAdmin(Long userId, Integer isAdmin) {
      if (userId == null) {
         throw new BizException(ResponseStatusEnum.USER_ID_NOT_NULl);
      } else {
         EmbyUser targetUser = this.getById(userId);
         if (targetUser == null) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else {
            this.assertUserCanBeManaged(targetUser);
            this.assertCurrentUserCanManageAdministrators();
            Integer nextIsAdmin = Integer.valueOf(1).equals(isAdmin) ? 1 : 0;
            Long currentUserId = StpUtil.getLoginIdAsLong();
            if (Objects.equals(currentUserId, userId) && Integer.valueOf(0).equals(nextIsAdmin)) {
               throw new BizException(ResponseStatusEnum.CURRENT_ADMIN_CANNOT_CANCEL);
            } else {
               EmbyUser embyUser = new EmbyUser();
               embyUser.setId(userId);
               embyUser.setIsAdmin(nextIsAdmin);
               this.updateById(embyUser);
               if (Integer.valueOf(0).equals(nextIsAdmin)) {
                  this.adminMenuPermissionService.removeAssignments(userId);
               }

               if (Integer.valueOf(1).equals(nextIsAdmin)) {
                  this.publishSimultaneousPlaybackUserConfigCleanup(targetUser);
               }

               EmbyUser sessionUser = (EmbyUser)StpUtil.getSession().get("user");
               if (sessionUser != null && Objects.equals(sessionUser.getId(), userId)) {
                  StpUtil.getSession().set("user", this.getById(userId));
               }
            }
         }
      }
   }

   @Override
   public void updateUserDataByBot(EmbyUserUpdateData embyUserUpdateData, boolean owner) {
      if (embyUserUpdateData.getId() == null) {
         throw new BizException(ResponseStatusEnum.USER_ID_NOT_NULl);
      } else {
         EmbyUser existingUser = this.getById(embyUserUpdateData.getId());
         if (existingUser == null) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else {
            this.assertBotTargetCanBeManaged(existingUser, owner);
            EmbyUser embyUser = BeanUtils.convert(embyUserUpdateData, EmbyUser.class);
            this.updateById(embyUser);
            if (this.isWhitelistHostLineType(embyUserUpdateData.getHostLineType())) {
               this.publishSimultaneousPlaybackUserConfigCleanup(existingUser);
            }
         }
      }
   }

   @Override
   public void enableUserByBot(Long userId, boolean owner) {
      EmbyUser embyUser = this.getById(userId);
      if (embyUser == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         this.assertBotTargetCanBeManaged(embyUser, owner);
         embyUser.setUserStatus(0);
         embyUser.setDisableReason(null);
         embyUser.setDisabledDatetime(null);
         this.updateById(embyUser);

         try {
            EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUser.getEmbyInfoId());
            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
            UserDto userDto = userServiceApi.getUsersById(embyUser.getEmbyUserId());
            UserPolicy userPolicy = userDto.getPolicy();
            userPolicy.setIsDisabled(false);
            userServiceApi.postUsersByIdPolicy(userPolicy, embyUser.getEmbyUserId());
         } catch (ApiException var8) {
            if (var8.getCode() != 404) {
               throw new BizException(ResponseStatusEnum.USER_ENABLE_ERROR);
            }

            log.warn("Emby用户不存在，跳过启用，embyUserId={}", embyUser.getEmbyUserId());
         }
      }
   }

   @Override
   public void disableUserByBot(Long userId, boolean owner) {
      EmbyUser embyUser = this.getById(userId);
      if (embyUser == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         this.assertBotTargetCanBeManaged(embyUser, owner);
         embyUser.setUserStatus(1);
         this.updateById(embyUser);

         try {
            EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyUser.getEmbyInfoId());
            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
            UserDto userDto = userServiceApi.getUsersById(embyUser.getEmbyUserId());
            UserPolicy userPolicy = userDto.getPolicy();
            userPolicy.setIsDisabled(true);
            userServiceApi.postUsersByIdPolicy(userPolicy, embyUser.getEmbyUserId());
         } catch (ApiException var8) {
            if (var8.getCode() != 404) {
               throw new BizException(ResponseStatusEnum.USER_DISABLE_FAILED);
            }

            log.warn("Emby用户不存在，跳过禁用，embyUserId={}", embyUser.getEmbyUserId());
         }
      }
   }

   @Override
   public void updateUserWhitelistByBot(Long userId, boolean whitelist, Integer ordinaryDays, boolean owner) {
      EmbyUser user = this.requireUser(userId);
      this.assertBotTargetCanBeManaged(user, owner);
      if (whitelist || ordinaryDays != null && ordinaryDays > 0 && ordinaryDays <= 3650) {
         this.lambdaUpdate()
            .eq(EmbyUser::getId, userId)
            .set(EmbyUser::getHostLineType, Integer.valueOf(whitelist ? HostLineTypeEnum.WHITELIST.getCode() : HostLineTypeEnum.COMMON.getCode()))
            .set(EmbyUser::getExpirationDate, whitelist ? null : DateUtil.offsetDay(new Date(), ordinaryDays))
            .set(EmbyUser::getExpireDateCount, null)
            .update();
         if (whitelist) {
            this.publishSimultaneousPlaybackUserConfigCleanup(user);
         }
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "移出白名单时有效天数必须在 1 到 3650 之间");
      }
   }

   @Override
   public void resetPasswordByBot(Long userId, String newPassword, boolean owner) {
      if (!StringUtils.hasText(newPassword) || newPassword.length() < 6 || newPassword.length() > 30) {
         throw new BizException(ResponseStatusEnum.PASSWORD_LENGTH_ERROR);
      } else if (CheckPasswordUtils.isContinuousChar(newPassword)) {
         throw new BizException(ResponseStatusEnum.PASSWORD_CONTAIN_CONTINUOUS_CHAR);
      } else {
         EmbyUser user = this.requireUser(userId);
         this.assertBotTargetCanBeManaged(user, owner);
         if (!Integer.valueOf(0).equals(user.getUserStatus())) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else {
            try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy
                  .guardCredentialChange(user.getId(), user.getIdentityGroupId(), user.getEmbyUserName(), newPassword)) {
               if (!Integer.valueOf(1).equals(user.getIsAdmin())) {
                  try {
                     EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(user.getEmbyInfoId());
                     UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
                     UpdateUserPassword request = new UpdateUserPassword();
                     request.setNewPw(newPassword);
                     userServiceApi.postUsersByIdPassword(request, user.getEmbyUserId());
                  } catch (ApiException var10) {
                     if (var10.getCode() == 404) {
                        throw new BizException(ResponseStatusEnum.EMBY_USER_NOT_EXIST);
                     }

                     throw new BizException(ResponseStatusEnum.EMBY_EXCEPTIION);
                  }
               }

               this.lambdaUpdate().eq(EmbyUser::getId, userId).set(EmbyUser::getEmbyUserPassword, SaSecureUtil.md5(newPassword)).update();
            }
         }
      }
   }

   @Override
   public byte[] uploadAvatar(MultipartFile file) {
      if (file != null && !file.isEmpty()) {
         try {
            byte[] bytes = file.getBytes();
            EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
            String redisKey = "user:avatar:v2:" + embyUser.getId();
            this.binaryRedisTemplate.opsForValue().set(redisKey, bytes);
            this.redisTemplate.delete("user:avatar:" + embyUser.getId());
            return bytes;
         } catch (IOException var5) {
            log.error("上传头像失败", (Throwable)var5);
            throw new BizException(ResponseStatusEnum.AVATAR_UPLOAD_FAILED);
         }
      } else {
         throw new BizException(ResponseStatusEnum.AVATAR_FILE_EMPTY);
      }
   }

   @Override
   public String syncUserData(String defaultPassword, Long embyInfoId) {
      StringBuilder stringBuilder = new StringBuilder();
      String resolvedPassword = this.resolveDefaultPassword(defaultPassword);
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = embyInfoId == null
         ? Optional.ofNullable(this.embyInfoCacheManager.getAdminConfigs())
            .filter(list -> !CollectionUtils.isEmpty(list))
            .orElseGet(() -> Collections.singletonList(this.embyInfoCacheManager.getRequiredConfig()))
         : Collections.singletonList(this.embyInfoCacheManager.getRequiredConfigById(embyInfoId));

      try {
         stringBuilder.append(getRandomEmoji() + "同步用户数据：\n");
         stringBuilder.append(getRandomEmoji() + "管理员不会同步\n");
         List<EmbyUser> embyUserList = this.list();
         Map<String, EmbyUser> embyUserMap = embyUserList.stream()
            .collect(Collectors.toMap(user -> this.buildUserKey(user.getEmbyUserName(), user.getEmbyInfoId()), user -> (EmbyUser)user, (a, b) -> a));
         boolean hasUsers = false;

         for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
            EmbyInfo embyInfo = this.embyInfoService.getById(serverConfig.id());
            if (embyInfo == null) {
               throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_FOUND);
            }

            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
            QueryResultUserDto queryResultUserDto = userServiceApi.getUsersQuery(null, null, null, null, null, null);
            if (!CollectionUtils.isEmpty(queryResultUserDto.getItems())) {
               hasUsers = true;
               Integer conutAll = queryResultUserDto.getItems().size();
               Integer conutExist = 0;
               Integer conutSync = 0;
               Integer conutAdmin = 0;

               for (UserDto item : queryResultUserDto.getItems()) {
                  if (item.getPolicy().isIsAdministrator()) {
                     conutAdmin = conutAdmin + 1;
                  } else {
                     String userKey = this.buildUserKey(item.getName(), embyInfo.getId());
                     EmbyUser existingUser = embyUserMap.get(userKey);
                     if (existingUser != null) {
                        if (this.isAdministrator(existingUser)) {
                           conutAdmin = conutAdmin + 1;
                        } else {
                           existingUser.setEmbyUserId(item.getId());
                           this.updateById(existingUser);
                           stringBuilder.append(getRandomEmoji() + "用户：" + item.getName()).append("\ud83d\udd04\n");
                           conutExist = conutExist + 1;
                        }
                     } else {
                        EmbyUser embyUser = new EmbyUser();
                        embyUser.setEmbyUserId(item.getId());
                        embyUser.setEmbyUserName(item.getName());
                        embyUser.setEmbyUserPassword(SaSecureUtil.md5(resolvedPassword));
                        embyUser.setIdentityGroupId(EmbyUserIdentityUtils.newIdentityGroupId());
                        embyUser.setUserStatus(item.getPolicy().isIsDisabled() ? 1 : 0);
                        embyUser.setEmbyInfoId(embyInfo.getId());

                        try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy
                              .guardIndependentRegistration(item.getName(), resolvedPassword)) {
                           this.save(embyUser);
                        }

                        embyUserList.add(embyUser);
                        embyUserMap.put(userKey, embyUser);
                        stringBuilder.append(getRandomEmoji() + "用户：" + item.getName()).append("✅\n");
                        conutSync = conutSync + 1;
                     }
                  }
               }

               stringBuilder.append(getRandomEmoji() + "同步完成，同步用户数量：" + conutAll + "\n");
               stringBuilder.append("✅用户数量：" + conutSync + "\n");
               stringBuilder.append("❌用户数量：" + conutExist + "\n");
               stringBuilder.append("\ud83d\udd30数量：" + conutAdmin + "\n");
            }
         }

         if (!hasUsers) {
            return "没有需要同步用户";
         }
      } catch (ApiException var28) {
         var28.printStackTrace();
         throw new BizException(ResponseStatusEnum.USER_SYNC_ERROR);
      }

      return stringBuilder.toString();
   }

   @Override
   public String syncUserBetweenServers(SyncEmbyUserRequest syncEmbyUserRequest) {
      if (Objects.equals(syncEmbyUserRequest.getSourceEmbyInfoId(), syncEmbyUserRequest.getTargetEmbyInfoId())) {
         throw new BizException(ResponseStatusEnum.SYNC_SERVER_ID_CONFLICT);
      } else {
         EmbyInfoCacheManagerUtils.EmbyServerConfig sourceConfig = this.embyInfoCacheManager.getRequiredConfigById(syncEmbyUserRequest.getSourceEmbyInfoId());
         EmbyInfoCacheManagerUtils.EmbyServerConfig targetConfig = this.embyInfoCacheManager.getRequiredConfigById(syncEmbyUserRequest.getTargetEmbyInfoId());
         EmbyInfo sourceEmbyInfo = this.embyInfoService.getById(sourceConfig.id());
         EmbyInfo targetEmbyInfo = this.embyInfoService.getById(targetConfig.id());
         String sourceServerName = sourceEmbyInfo != null && StringUtils.hasText(sourceEmbyInfo.getServerName())
            ? sourceEmbyInfo.getServerName()
            : String.valueOf(sourceConfig.id());
         String targetServerName = targetEmbyInfo != null && StringUtils.hasText(targetEmbyInfo.getServerName())
            ? targetEmbyInfo.getServerName()
            : String.valueOf(targetConfig.id());
         UserServiceApi sourceUserServiceApi = this.buildUserServiceApi(sourceConfig);
         UserServiceApi targetUserServiceApi = this.buildUserServiceApi(targetConfig);
         List<EmbyUser> embyUserList = this.list();
         Set<String> administratorUserKeys = embyUserList.stream()
            .filter(this::isAdministrator)
            .map(user -> this.buildUserKey(user.getEmbyUserName(), user.getEmbyInfoId()))
            .collect(Collectors.toSet());
         Map<String, EmbyUser> embyUserMap = embyUserList.stream()
            .collect(Collectors.toMap(user -> this.buildUserKey(user.getEmbyUserName(), user.getEmbyInfoId()), item -> (EmbyUser)item, (a, b) -> a));
         String resolvedPassword = this.resolveDefaultPassword(syncEmbyUserRequest.getDefaultPassword());

         try {
            QueryResultUserDto sourceUsers = sourceUserServiceApi.getUsersQuery(null, null, null, null, null, null);
            QueryResultUserDto targetUsers = targetUserServiceApi.getUsersQuery(null, null, null, null, null, null);
            Map<String, UserDto> targetUserMap = Optional.ofNullable(targetUsers.getItems())
               .orElse(Collections.emptyList())
               .stream()
               .filter(item -> item.getPolicy() == null || !item.getPolicy().isIsAdministrator())
               .collect(Collectors.toMap(UserDto::getName, item -> (UserDto)item, (a, b) -> a));
            if (CollectionUtils.isEmpty(sourceUsers.getItems())) {
               return "源服务器暂无可同步的用户";
            } else {
               int syncedCount = 0;
               int existedCount = 0;
               int adminCount = 0;

               for (UserDto sourceUser : sourceUsers.getItems()) {
                  String sourceUserKey = this.buildUserKey(sourceUser.getName(), sourceConfig.id());
                  String targetUserKey = this.buildUserKey(sourceUser.getName(), targetConfig.id());
                  if (sourceUser.getPolicy() != null && sourceUser.getPolicy().isIsAdministrator()) {
                     adminCount++;
                  } else if (!administratorUserKeys.contains(sourceUserKey) && !administratorUserKeys.contains(targetUserKey)) {
                     UserDto targetExisting = targetUserMap.get(sourceUser.getName());
                     EmbyUser targetLocalUser = embyUserMap.get(targetUserKey);
                     if (targetExisting == null && targetLocalUser == null) {
                        EmbyUser sourceLocalUser = embyUserMap.get(sourceUserKey);
                        Long identityGroupId = sourceLocalUser != null && sourceLocalUser.getIdentityGroupId() != null
                           ? sourceLocalUser.getIdentityGroupId()
                           : EmbyUserIdentityUtils.newIdentityGroupId();

                        try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy
                              .guardIdentityMemberCreation(identityGroupId, sourceUser.getName(), resolvedPassword)) {
                           CreateUserByName createUserByName = new CreateUserByName();
                           createUserByName.setName(sourceUser.getName());
                           createUserByName.setCopyFromUserId(this.getCopyfromuserid(targetConfig));
                           createUserByName.setUserCopyOptions(Arrays.asList(LibraryUserCopyOptions.USERPOLICY, LibraryUserCopyOptions.USERCONFIGURATION));

                           UserDto newUser;
                           try {
                              newUser = targetUserServiceApi.postUsersNew(createUserByName);
                              this.applyInitialLibraryAccessOrDelete(targetUserServiceApi, targetConfig.id(), newUser.getId());
                              targetUserMap.put(newUser.getName(), newUser);
                           } catch (ApiException var34) {
                              if (!this.isUserAlreadyExists(var34)) {
                                 throw var34;
                              }

                              log.info("目标服务器用户已存在，跳过创建：{}", sourceUser.getName());
                              existedCount++;
                              continue;
                           }

                           UpdateUserPassword updateUserPassword = new UpdateUserPassword();
                           updateUserPassword.setNewPw(resolvedPassword);
                           targetUserServiceApi.postUsersByIdPassword(updateUserPassword, newUser.getId());
                           if (sourceUser.getPolicy() != null && sourceUser.getPolicy().isIsDisabled()) {
                              UserPolicy userPolicy = newUser.getPolicy() == null ? new UserPolicy() : newUser.getPolicy();
                              userPolicy.setIsDisabled(true);
                              targetUserServiceApi.postUsersByIdPolicy(userPolicy, newUser.getId());
                           }

                           this.createSyncedLocalUser(
                              targetConfig, sourceUser, embyUserMap, newUser.getId(), resolvedPassword, sourceLocalUser, identityGroupId
                           );
                           syncedCount++;
                        }
                     } else {
                        existedCount++;
                     }
                  } else {
                     adminCount++;
                  }
               }

               return getRandomEmoji()
                  + "同步服务器：\n"
                  + sourceServerName
                  + " ➡️ "
                  + targetServerName
                  + "\n"
                  + "\ud83d\udcca 总计处理用户："
                  + sourceUsers.getItems().size()
                  + "\n"
                  + "\ud83d\udc64 新增用户："
                  + syncedCount
                  + "\n"
                  + "\ud83d\udcdd 已存在跳过："
                  + existedCount
                  + "\n"
                  + "\ud83d\udd30 管理员跳过："
                  + adminCount;
            }
         } catch (ApiException var36) {
            log.error("跨服务器同步用户失败", (Throwable)var36);
            throw new BizException(ResponseStatusEnum.USER_SYNC_ERROR);
         }
      }
   }

   private void createSyncedLocalUser(
      EmbyInfoCacheManagerUtils.EmbyServerConfig targetConfig,
      UserDto sourceUser,
      Map<String, EmbyUser> embyUserMap,
      String targetUserId,
      String defaultPassword,
      EmbyUser sourceLocalUser,
      Long identityGroupId
   ) {
      String userKey = this.buildUserKey(sourceUser.getName(), targetConfig.id());
      boolean disabled = sourceUser.getPolicy() != null && sourceUser.getPolicy().isIsDisabled();
      EmbyUser newUser = new EmbyUser();
      newUser.setEmbyUserName(sourceUser.getName());
      newUser.setEmbyUserPassword(SaSecureUtil.md5(defaultPassword));
      newUser.setIsAdmin(0);
      newUser.setUserStatus(disabled ? 1 : 0);
      newUser.setEmbyInfoId(targetConfig.id());
      newUser.setEmbyUserId(targetUserId);
      newUser.setIdentityGroupId(identityGroupId);
      if (sourceLocalUser != null) {
         newUser.setExpirationDate(sourceLocalUser.getExpirationDate());
         newUser.setRemarks(sourceLocalUser.getRemarks());
         newUser.setRequestPackagesCount(sourceLocalUser.getRequestPackagesCount());
         newUser.setRegisterChannel(sourceLocalUser.getRegisterChannel());
         newUser.setHostLineType(HostLineTypeEnum.normalize(sourceLocalUser.getHostLineType()));
      }

      this.save(newUser);
      embyUserMap.put(userKey, newUser);
   }

   private boolean isUserAlreadyExists(ApiException e) {
      if (e.getCode() == 409) {
         return true;
      } else {
         String responseBody = e.getResponseBody();
         if (!StringUtils.hasText(responseBody)) {
            return false;
         } else {
            String lowerResponse = responseBody.toLowerCase(Locale.ROOT);
            return lowerResponse.contains("exists") || lowerResponse.contains("already");
         }
      }
   }

   private void validateRegisterUserName(String userName, Long embyInfoId) {
      this.validateUserNameFormat(userName);
      this.validateReservedUserName(userName);
      if (this.embyUserNameExist(userName, embyInfoId)) {
         throw new BizException(ResponseStatusEnum.USER_NAME_EXIST);
      }
   }

   private void validateCardOrInvitationRegisterUserName(String userName) {
      this.validateUserNameFormat(userName);
      this.validateReservedUserName(userName);
      if (userName.length() < 6) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户名长度不能少于6个字符");
      }
   }

   private void validateInitAdminUserName(String userName) {
      this.validateUserNameFormat(userName);
      this.validateReservedUserName(userName);
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyUser::getEmbyUserName, userName).count();
      if (count > 0L) {
         throw new BizException(ResponseStatusEnum.USER_NAME_EXIST);
      }
   }

   private void validateUserNameFormat(String userName) {
      if (!StringUtils.hasText(userName)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户名不能为空");
      } else if (userName.length() > 64) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户名长度不能超过64个字符");
      } else if (userName.chars().anyMatch(Character::isWhitespace)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户名不能包含空格");
      }
   }

   private void validateReservedUserName(String userName) {
      if ("admin".equalsIgnoreCase(userName) || "root".equalsIgnoreCase(userName)) {
         throw new BizException(ResponseStatusEnum.USER_NAME_NOT_ALLOWED);
      }
   }

   private void validateRegisterPassword(String password) {
      if (!StringUtils.hasText(password) || password.length() < 6 || password.length() > 30) {
         throw new BizException(ResponseStatusEnum.PASSWORD_LENGTH_ERROR);
      } else if (CheckPasswordUtils.isContinuousChar(password)) {
         throw new BizException(ResponseStatusEnum.PASSWORD_CONTAIN_CONTINUOUS_CHAR);
      }
   }

   private void validateOptionalRegisterPassword(String password) {
      if (StringUtils.hasText(password)) {
         this.validateRegisterPassword(password);
      }
   }

   @Override
   public RegisteredUserResponse registeredUser(RegisteredUserSave registeredUserSave, String clientIp) throws ApiException {
      this.invitationRegistrationIpRateLimiter.checkAttempt(clientIp);
      if (registeredUserSave == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      } else {
         this.validateRegisterPassword(registeredUserSave.getEmbyUserPassword());
         sanitizePublicRegistrationRequest(registeredUserSave);
         EmbyUser embyUserData = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyUser::getIsAdmin, Integer.valueOf(1)).last("limit 1").one();
         boolean initializeAdmin = embyUserData == null;
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = null;
         if (!initializeAdmin) {
            if (!this.isConfigEnabled("registered_user")) {
               throw new BizException(ResponseStatusEnum.USER_REGISTER_FAILED);
            }

            serverConfig = this.resolveSelfServiceRegistrationServerConfig();
         }

         InvitationRegistrationIpRateLimiter.Reservation ipReservation = this.invitationRegistrationIpRateLimiter.reserveOpenRegistration(clientIp);
         boolean ipReservationCompletionRegistered = false;

         RegisteredUserResponse var9;
         try {
            RegisteredUserResponse response = initializeAdmin
               ? this.registeredInitAdminWithLock(registeredUserSave)
               : this.registerUserWithNameLock(registeredUserSave, serverConfig, null, null);
            this.invitationRegistrationIpRateLimiter.completeAfterCommit(ipReservation);
            ipReservationCompletionRegistered = true;
            var9 = response;
         } finally {
            if (!ipReservationCompletionRegistered) {
               this.invitationRegistrationIpRateLimiter.release(ipReservation);
            }
         }

         return var9;
      }
   }

   @Override
   public RegisteredUserResponse registeredUserByTelegram(RegisteredUserSave registeredUserSave, String registerChannelDetail) throws ApiException {
      this.validateRegisterPassword(registeredUserSave.getEmbyUserPassword());
      registeredUserSave.setEmbyUserId(null);
      registeredUserSave.setUserStatus(0);
      registeredUserSave.setRequestPackagesCount(null);
      registeredUserSave.setHostLineType(HostLineTypeEnum.COMMON.getCode());
      Long adminCount = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyUser::getIsAdmin, Integer.valueOf(1)).count();
      if (adminCount != null && adminCount != 0L) {
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveSelfServiceRegistrationServerConfig();
         Integer defaultDays = this.resolveTelegramRegisterDefaultDays();
         if (defaultDays != null && defaultDays > 0) {
            registeredUserSave.setExpirationDate(DateUtil.offsetDay(new Date(), defaultDays));
         }

         return this.registerUserWithNameLock(registeredUserSave, serverConfig, null, registerChannelDetail);
      } else {
         throw new BizException(ResponseStatusEnum.USER_REGISTER_FAILED);
      }
   }

   private Integer resolveTelegramRegisterDefaultDays() {
      String value = this.configCacheLoaderUtils.getConfigValue("telegram_bot_register_enabled");
      if (!StringUtils.hasText(value)) {
         return 0;
      } else {
         String text = value.trim();
         if (text.startsWith("{")) {
            try {
               JSONObject config = JSONObject.parseObject(text);
               return Math.max(0, config.getIntValue("defaultDays"));
            } catch (Exception var4) {
               log.warn("Telegram 机器人注册配置 JSON 无效: {}", value);
               return 0;
            }
         } else {
            try {
               return Math.max(0, Integer.parseInt(text));
            } catch (NumberFormatException var5) {
               log.warn("Telegram 机器人注册默认有效期天数配置无效: {}", value);
               return 0;
            }
         }
      }
   }

   @Override
   public RegisteredUserResponse registeredByInvitation(InvitationRegisterRequest invitationRegisterRequest, String clientIp) throws ApiException {
      this.invitationRegistrationIpRateLimiter.checkAttempt(clientIp);
      if (invitationRegisterRequest == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      } else {
         this.validateRegisterPassword(invitationRegisterRequest.getEmbyUserPassword());
         this.validateCardOrInvitationRegisterUserName(invitationRegisterRequest.getEmbyUserName());
         String invitationCodeText = invitationRegisterRequest.resolveInvitationCode();
         if (!StringUtils.hasText(invitationCodeText)) {
            throw new BizException(ResponseStatusEnum.INVITATION_CODE_NOT_FOUND);
         } else {
            String invitationLockKey = this.registerInvitationLockKey(invitationCodeText);
            String invitationLockToken = this.tryRegisterLock(invitationLockKey);
            if (!StringUtils.hasText(invitationLockToken)) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该邀请码正在使用中，请稍后再试");
            } else {
               String nameLockKey = null;
               String nameLockToken = null;
               InvitationRegistrationIpRateLimiter.Reservation ipReservation = null;
               boolean ipReservationCompletionRegistered = false;

               RegisteredUserResponse var15;
               try {
                  InvitationCode invitationCodePreview = this.invitationCodeService.lambdaQuery().eq(InvitationCode::getCode, invitationCodeText).one();
                  if (invitationCodePreview != null) {
                     nameLockKey = this.registerNameLockKey(invitationCodePreview.getEmbyInfoId(), invitationRegisterRequest.getEmbyUserName());
                     nameLockToken = this.tryRegisterLock(nameLockKey);
                     if (!StringUtils.hasText(nameLockToken)) {
                        throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该用户名正在注册中，请稍后再试");
                     }

                     this.validateRegisterUserName(invitationRegisterRequest.getEmbyUserName(), invitationCodePreview.getEmbyInfoId());
                  }

                  if (!this.isConfigEnabled("invitation_register_enabled")) {
                     throw new BizException(ResponseStatusEnum.INVITATION_REGISTER_DISABLED);
                  }

                  ipReservation = this.invitationRegistrationIpRateLimiter.reserve(invitationCodeText, clientIp);
                  InvitationCode invitationCode = this.invitationCodeService.useInvitationCode(invitationCodeText, invitationRegisterRequest.getEmbyUserName());
                  EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(invitationCode.getEmbyInfoId());
                  if (nameLockKey == null) {
                     nameLockKey = this.registerNameLockKey(serverConfig.id(), invitationRegisterRequest.getEmbyUserName());
                     nameLockToken = this.tryRegisterLock(nameLockKey);
                     if (!StringUtils.hasText(nameLockToken)) {
                        throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该用户名正在注册中，请稍后再试");
                     }

                     this.validateRegisterUserName(invitationRegisterRequest.getEmbyUserName(), serverConfig.id());
                  }

                  RegisteredUserSave registeredUserSave = BeanUtils.convert(invitationRegisterRequest, RegisteredUserSave.class);
                  RegisteredUserResponse response = this.registerUserOnServer(registeredUserSave, serverConfig, invitationCode);
                  this.publishInvitationRegisterSuccessNotifyEvent(response, invitationCode, serverConfig);
                  this.invitationRegistrationIpRateLimiter.completeAfterCommit(ipReservation);
                  ipReservationCompletionRegistered = true;
                  var15 = response;
               } finally {
                  if (!ipReservationCompletionRegistered) {
                     this.invitationRegistrationIpRateLimiter.release(ipReservation);
                  }

                  this.redisLockUtils.unlock(nameLockKey, nameLockToken);
                  this.redisLockUtils.unlock(invitationLockKey, invitationLockToken);
               }

               return var15;
            }
         }
      }
   }

   private RegisteredUserResponse registeredInitAdminWithLock(RegisteredUserSave registeredUserSave) throws ApiException {
      String lockToken = this.tryRegisterLock("foam:register:init-admin");
      if (!StringUtils.hasText(lockToken)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "系统初始化注册正在处理中，请稍后再试");
      } else {
         RegisteredUserResponse var17;
         try {
            EmbyUser existsAdmin = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyUser::getIsAdmin, Integer.valueOf(1)).last("limit 1").one();
            if (existsAdmin != null) {
               throw new BizException(ResponseStatusEnum.USER_REGISTER_FAILED);
            }

            this.validateInitAdminUserName(registeredUserSave.getEmbyUserName());
            String embyUserPassword = SaSecureUtil.md5(registeredUserSave.getEmbyUserPassword());
            EmbyUser embyUser = BeanUtils.convert(registeredUserSave, EmbyUser.class);
            embyUser.setIsAdmin(1);
            embyUser.setIsPrimaryAdmin(1);
            embyUser.setUserStatus(0);
            embyUser.setEmbyUserPassword(embyUserPassword);
            embyUser.setIdentityGroupId(EmbyUserIdentityUtils.newIdentityGroupId());
            embyUser.setRegisterChannel(RegisterChannelEnum.ADMIN_REGISTER.getCode());
            Integer hostLineType = HostLineTypeEnum.normalize(registeredUserSave.getHostLineType());
            embyUser.setHostLineType(hostLineType);
            if (this.isWhitelistHostLineType(hostLineType)) {
               embyUser.setExpirationDate(null);
            }

            try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy
                  .guardIndependentRegistration(registeredUserSave.getEmbyUserName(), registeredUserSave.getEmbyUserPassword())) {
               this.save(embyUser);
            }

            this.saveRegisterRecord(embyUser, embyUser.getRegisterChannel(), "系统初始化管理员注册");
            var17 = BeanUtils.convert(embyUser, RegisteredUserResponse.class);
         } finally {
            this.redisLockUtils.unlock("foam:register:init-admin", lockToken);
         }

         return var17;
      }
   }

   private RegisteredUserResponse registerUserWithNameLock(
      RegisteredUserSave registeredUserSave,
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      InvitationCode invitationCode,
      String registerChannelDetail
   ) throws ApiException {
      String lockKey = this.registerNameLockKey(serverConfig.id(), registeredUserSave.getEmbyUserName());
      String lockToken = this.tryRegisterLock(lockKey);
      if (!StringUtils.hasText(lockToken)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该用户名正在注册中，请稍后再试");
      } else {
         RegisteredUserResponse var7;
         try {
            this.validateRegisterUserName(registeredUserSave.getEmbyUserName(), serverConfig.id());
            var7 = this.registerUserOnServer(registeredUserSave, serverConfig, invitationCode, registerChannelDetail);
         } finally {
            this.redisLockUtils.unlock(lockKey, lockToken);
         }

         return var7;
      }
   }

   private RegisteredUserResponse registerUserOnServer(
      RegisteredUserSave registeredUserSave, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, InvitationCode invitationCode
   ) throws ApiException {
      return this.registerUserOnServer(registeredUserSave, serverConfig, invitationCode, null);
   }

   private RegisteredUserResponse registerUserOnServer(
      RegisteredUserSave registeredUserSave,
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      InvitationCode invitationCode,
      String registerChannelDetail
   ) throws ApiException {
      String rawPassword = this.resolveDefaultPassword(registeredUserSave.getEmbyUserPassword());

      RegisteredUserResponse var7;
      try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy
            .guardIndependentRegistration(registeredUserSave.getEmbyUserName(), rawPassword)) {
         var7 = this.createRegisteredUserOnServer(registeredUserSave, serverConfig, invitationCode, registerChannelDetail, rawPassword);
      }

      return var7;
   }

   private RegisteredUserResponse createRegisteredUserOnServer(
      RegisteredUserSave registeredUserSave,
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      InvitationCode invitationCode,
      String registerChannelDetail,
      String rawPassword
   ) throws ApiException {
      UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
      CreateUserByName createUserByName = new CreateUserByName();
      createUserByName.setName(registeredUserSave.getEmbyUserName());
      createUserByName.setCopyFromUserId(this.getCopyfromuserid(serverConfig));
      createUserByName.setUserCopyOptions(Arrays.asList(LibraryUserCopyOptions.USERPOLICY, LibraryUserCopyOptions.USERCONFIGURATION));
      UserDto userDto = userServiceApi.postUsersNew(createUserByName);
      this.applyInitialLibraryAccessOrDelete(userServiceApi, serverConfig.id(), userDto.getId());
      UpdateUserPassword updateUserPassword = new UpdateUserPassword();
      updateUserPassword.setNewPw(rawPassword);
      userServiceApi.postUsersByIdPassword(updateUserPassword, userDto.getId());
      registeredUserSave.setEmbyUserId(userDto.getId());
      registeredUserSave.setEmbyUserName(userDto.getName());
      EmbyUser embyUser = BeanUtils.convert(registeredUserSave, EmbyUser.class);
      embyUser.setIsAdmin(0);
      embyUser.setUserStatus(0);
      embyUser.setEmbyUserPassword(SaSecureUtil.md5(rawPassword));
      embyUser.setIdentityGroupId(EmbyUserIdentityUtils.newIdentityGroupId());
      embyUser.setEmbyInfoId(serverConfig.id());
      embyUser.setRegisterChannel(invitationCode != null ? RegisterChannelEnum.INVITATION.getCode() : RegisterChannelEnum.USER_REGISTER.getCode());
      Integer hostLineType = HostLineTypeEnum.normalize(invitationCode != null ? invitationCode.getHostLineType() : registeredUserSave.getHostLineType());
      embyUser.setHostLineType(hostLineType);
      EmbyUserServiceImpl.InvitationRewardResult invitationReward = EmbyUserServiceImpl.InvitationRewardResult.none();
      if (invitationCode != null) {
         embyUser.setInvitationCode(invitationCode.getCode());
         embyUser.setRemarks("通过邀请码注册");
         if (!this.isWhitelistHostLineType(hostLineType) && invitationCode.getValidityDays() != null && invitationCode.getValidityDays() > 0) {
            embyUser.setExpirationDate(DateUtil.offsetDay(new Date(), invitationCode.getValidityDays()));
         }

         invitationReward = this.applyInvitationReward(embyUser, hostLineType, invitationCode);
      } else if (this.isWhitelistHostLineType(hostLineType)) {
         embyUser.setExpirationDate(null);
      }

      String requestCount = this.configCacheLoaderUtils.getConfigValue("request_count");
      if (StringUtils.hasText(requestCount)) {
         embyUser.setRequestPackagesCount(Integer.valueOf(requestCount));
      }

      this.save(embyUser);
      String recordDetail = StringUtils.hasText(registerChannelDetail) ? registerChannelDetail : (invitationCode != null ? "前台邀请码注册" : "前台开放注册");
      this.saveRegisterRecord(embyUser, embyUser.getRegisterChannel(), recordDetail, invitationReward.duration(), invitationReward.unit());
      return BeanUtils.convert(embyUser, RegisteredUserResponse.class);
   }

   private void applyInitialLibraryAccessOrDelete(UserServiceApi userServiceApi, Long embyInfoId, String embyUserId) {
      try {
         this.embyLibraryAccessService.applyGlobalRuleToRemoteUser(embyInfoId, embyUserId);
      } catch (RuntimeException var7) {
         try {
            userServiceApi.deleteUsersById(embyUserId);
         } catch (Exception var6) {
            log.error("新用户媒体库分级失败，且远端补偿删除失败: serverId={}, embyUserId={}", embyInfoId, embyUserId, var6);
         }

         throw var7;
      }
   }

   static void sanitizePublicRegistrationRequest(RegisteredUserSave request) {
      request.setEmbyUserId(null);
      request.setUserStatus(0);
      request.setExpirationDate(null);
      request.setRequestPackagesCount(null);
      request.setHostLineType(HostLineTypeEnum.COMMON.getCode());
   }

   private EmbyUserServiceImpl.InvitationRewardResult applyInvitationReward(EmbyUser embyUser, Integer hostLineType, InvitationCode invitationCode) {
      if (embyUser != null && invitationCode != null && !this.isWhitelistHostLineType(hostLineType)) {
         int rewardDuration = invitationCode.getRewardDuration() == null ? 0 : invitationCode.getRewardDuration();
         if (rewardDuration <= 0) {
            return EmbyUserServiceImpl.InvitationRewardResult.none();
         } else {
            String rewardUnit = InvitationCodeServiceImpl.normalizeRewardDurationUnit(invitationCode.getRewardDurationUnit());
            Date now = new Date();
            Date baseExpiration = embyUser.getExpirationDate();
            if (baseExpiration == null || DateUtil.compare(baseExpiration, now) < 0) {
               baseExpiration = now;
            }

            Date rewardedExpiration = "HOUR".equals(rewardUnit)
               ? DateUtil.offsetHour(baseExpiration, rewardDuration)
               : DateUtil.offsetDay(baseExpiration, rewardDuration);
            embyUser.setExpirationDate(rewardedExpiration);
            return new EmbyUserServiceImpl.InvitationRewardResult(rewardDuration, rewardUnit);
         }
      } else {
         return EmbyUserServiceImpl.InvitationRewardResult.none();
      }
   }

   private String resolveRegisterRecordDetailForAddUser(EmbyUserSave embyUserSave) {
      if (StringUtils.hasText(embyUserSave.getRegisterChannelDetail())) {
         return embyUserSave.getRegisterChannelDetail();
      } else {
         Integer registerChannel = embyUserSave.getRegisterChannel();
         if (Objects.equals(registerChannel, RegisterChannelEnum.POINTS_REDEEM.getCode())) {
            return "Telegram积分兑换";
         } else if (Objects.equals(registerChannel, RegisterChannelEnum.CARD_REGISTER.getCode())) {
            return "前台卡密激活";
         } else if (Objects.equals(registerChannel, RegisterChannelEnum.ADMIN_REGISTER.getCode())) {
            return "后台手动新增";
         } else if (Objects.equals(registerChannel, RegisterChannelEnum.INVITATION.getCode())) {
            return "前台邀请码注册";
         } else {
            return Objects.equals(registerChannel, RegisterChannelEnum.USER_REGISTER.getCode()) ? "前台开放注册" : RegisterChannelEnum.resolveLabel(registerChannel);
         }
      }
   }

   private void saveRegisterRecord(EmbyUser embyUser, Integer registerChannel, String registerChannelDetail) {
      this.saveRegisterRecord(embyUser, registerChannel, registerChannelDetail, 0, null);
   }

   private void saveRegisterRecord(EmbyUser embyUser, Integer registerChannel, String registerChannelDetail, Integer rewardDuration, String rewardDurationUnit) {
      if (embyUser != null) {
         EmbyUserRegisterRecord record = new EmbyUserRegisterRecord();
         record.setUserId(embyUser.getId());
         record.setEmbyUserId(embyUser.getEmbyUserId());
         record.setEmbyUserName(embyUser.getEmbyUserName());
         record.setRegisterChannel(registerChannel);
         record.setRegisterChannelDetail(registerChannelDetail);
         record.setEmbyInfoId(embyUser.getEmbyInfoId());
         record.setRemarks(embyUser.getRemarks());
         record.setExpirationDate(embyUser.getExpirationDate());
         record.setRegisterDays(this.resolveRegisterDays(embyUser.getCreateDatetime(), embyUser.getExpirationDate()));
         int normalizedRewardDuration = rewardDuration != null && rewardDuration >= 0 ? rewardDuration : 0;
         record.setRewardDuration(normalizedRewardDuration);
         record.setRewardDurationUnit(normalizedRewardDuration > 0 ? InvitationCodeServiceImpl.normalizeRewardDurationUnit(rewardDurationUnit) : null);
         this.embyUserRegisterRecordService.save(record);
      }
   }

   private Integer resolveRegisterDays(Date registrationDate, Date expirationDate) {
      if (expirationDate == null) {
         return null;
      } else {
         Date effectiveRegistrationDate = registrationDate == null ? new Date() : registrationDate;
         if (!expirationDate.after(effectiveRegistrationDate)) {
            return null;
         } else {
            long days = ChronoUnit.DAYS
               .between(
                  effectiveRegistrationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                  expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
               );
            return days > 0L && days <= 2147483647L ? (int)days : null;
         }
      }
   }

   private void saveRenewRecord(EmbyUser embyUser, Integer renewChannel, String renewChannelDetail, Integer renewDays, Date beforeExpiration) {
      if (embyUser != null) {
         EmbyUserRenewRecord record = new EmbyUserRenewRecord();
         record.setUserId(embyUser.getId());
         record.setEmbyUserId(embyUser.getEmbyUserId());
         record.setEmbyUserName(embyUser.getEmbyUserName());
         record.setRenewChannel(renewChannel);
         record.setRenewChannelDetail(renewChannelDetail);
         record.setRenewDays(renewDays);
         record.setExpirationDateBefore(beforeExpiration);
         record.setExpirationDateAfter(embyUser.getExpirationDate());
         record.setEmbyInfoId(embyUser.getEmbyInfoId());
         this.embyUserRenewRecordService.save(record);
      }
   }

   private void publishCardRegisterSuccessNotifyEvent(InsertUserResponse response, CardSecurityManagement card) {
      if (response != null && card != null) {
         this.applicationEventPublisher
            .publishEvent(new CardRegisterSuccessNotifyEvent(response.getEmbyUserName(), card.getEmbyInfoId(), card.getCardValidity(), card.getCardPassword()));
      }
   }

   private void publishInvitationRegisterSuccessNotifyEvent(
      RegisteredUserResponse response, InvitationCode invitationCode, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig
   ) {
      if (response != null && invitationCode != null) {
         this.applicationEventPublisher
            .publishEvent(
               new InvitationRegisterSuccessNotifyEvent(
                  response.getEmbyUserName(),
                  serverConfig == null ? invitationCode.getEmbyInfoId() : serverConfig.id(),
                  invitationCode.getValidityDays(),
                  invitationCode.getCode()
               )
            );
      }
   }

   private void publishCardRenewSuccessNotifyEvent(EmbyUser embyUser, CardSecurityManagement card) {
      if (embyUser != null && card != null) {
         this.applicationEventPublisher
            .publishEvent(
               new CardRenewSuccessNotifyEvent(
                  embyUser.getEmbyUserName(), embyUser.getEmbyInfoId(), card.getCardValidity(), card.getCardPassword(), embyUser.getExpirationDate()
               )
            );
      }
   }

   private void publishSimultaneousPlaybackUserConfigCleanup(EmbyUser embyUser) {
      if (embyUser != null && this.applicationEventPublisher != null) {
         try {
            this.applicationEventPublisher
               .publishEvent(new SimultaneousPlaybackUserConfigCleanupEvent(embyUser.getEmbyInfoId(), embyUser.getEmbyUserId(), embyUser.getEmbyUserName()));
         } catch (Exception var3) {
            log.warn("提交用户同播配置清理任务失败，不影响用户主操作: userId={}", embyUser.getId(), var3);
         }
      }
   }

   @Override
   public boolean userExist() {
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyUser::getIsAdmin, Integer.valueOf(1)).count();
      return count > 0L;
   }

   @Override
   public boolean enableRegistration() {
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyUser::getIsAdmin, Integer.valueOf(1)).count();
      if (count == 0L) {
         return true;
      } else if (!this.isConfigEnabled("registered_user")) {
         return false;
      } else {
         try {
            this.resolveSelfServiceRegistrationServerConfig();
            return true;
         } catch (BizException var3) {
            return false;
         }
      }
   }

   private boolean isConfigEnabled(String configKey) {
      return this.configCacheLoaderUtils.getConfigValue(configKey) != null;
   }

   private String tryRegisterLock(String lockKey) {
      return this.redisLockUtils.tryLock(lockKey, 180L);
   }

   private String registerNameLockKey(Long embyInfoId, String userName) {
      return "foam:register:name:" + (embyInfoId == null ? "default" : embyInfoId) + ":" + this.normalizeRegisterLockPart(userName);
   }

   private String registerCardLockKey(String cardPassword) {
      return "foam:register:card:" + this.normalizeRegisterLockPart(cardPassword);
   }

   private String registerInvitationLockKey(String invitationCode) {
      return "foam:register:invitation:" + this.normalizeRegisterLockPart(invitationCode);
   }

   private String normalizeRegisterLockPart(String value) {
      return StringUtils.hasText(value) ? value.trim().toLowerCase(Locale.ROOT) : "blank";
   }

   @Override
   public boolean embyUserNameExist(String embyUserName, Long embyInfoId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyInfoId);
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(EmbyUser::getEmbyUserName, embyUserName)
         .eq(EmbyUser::getEmbyInfoId, serverConfig.id())
         .count();
      return count > 0L;
   }

   public static String getRandomEmoji() {
      List<String> emojis = new ArrayList<>(
         List.of(
            "\ud83d\ude00",
            "\ud83d\ude03",
            "\ud83d\ude04",
            "\ud83d\ude01",
            "\ud83d\ude06",
            "\ud83d\ude05",
            "\ud83d\ude02",
            "\ud83e\udd23",
            "\ud83e\udd72",
            "\ud83e\udd79",
            "☺️",
            "\ud83d\ude0a",
            "\ud83d\ude07",
            "\ud83d\ude42",
            "\ud83d\ude43",
            "\ud83d\ude09",
            "\ud83d\ude0c",
            "\ud83d\ude0d",
            "\ud83e\udd70",
            "\ud83d\ude18",
            "\ud83d\ude17",
            "\ud83d\ude19",
            "\ud83d\ude1a",
            "\ud83d\ude0b",
            "\ud83d\ude1b",
            "\ud83d\ude1d",
            "\ud83d\ude1c",
            "\ud83e\udd2a",
            "\ud83e\udd28",
            "\ud83e\uddd0",
            "\ud83e\udd13",
            "\ud83d\ude0e",
            "\ud83e\udd78",
            "\ud83e\udd29",
            "\ud83e\udd73",
            "\ud83d\ude0f",
            "\ud83d\ude12",
            "\ud83d\ude1e",
            "\ud83d\ude14",
            "\ud83d\ude1f",
            "\ud83d\ude15",
            "\ud83d\ude41",
            "\ud83d\ude23",
            "\ud83d\ude16",
            "\ud83d\ude2b",
            "\ud83d\ude29",
            "\ud83e\udd7a",
            "\ud83d\ude22",
            "\ud83d\ude2d",
            "\ud83d\ude2e\u200d\ud83d\udca8",
            "\ud83d\ude24",
            "\ud83d\ude20",
            "\ud83d\ude21",
            "\ud83e\udd2c",
            "\ud83e\udd2f",
            "\ud83d\ude33",
            "\ud83e\udd75",
            "\ud83e\udd76",
            "\ud83d\ude31",
            "\ud83d\ude28",
            "\ud83d\ude30",
            "\ud83d\ude25",
            "\ud83d\ude13",
            "\ud83e\udee3",
            "\ud83e\udd17",
            "\ud83e\udee1",
            "\ud83e\udd14",
            "\ud83e\udee2",
            "\ud83e\udd2d",
            "\ud83e\udd2b",
            "\ud83e\udd25",
            "\ud83d\ude36",
            "\ud83d\ude36\u200d\ud83c\udf2b",
            "\ud83d\ude10",
            "\ud83d\ude11",
            "\ud83d\ude2c",
            "\ud83e\udee8",
            "\ud83e\udee0",
            "\ud83d\ude44",
            "\ud83d\ude2f",
            "\ud83d\ude26",
            "\ud83d\ude27",
            "\ud83d\ude2e",
            "\ud83d\ude32",
            "\ud83e\udd71",
            "\ud83d\ude34",
            "\ud83e\udd24",
            "\ud83d\ude2a",
            "\ud83d\ude35",
            "\ud83d\ude35\u200d\ud83d\udcab",
            "\ud83e\udee5",
            "\ud83e\udd10",
            "\ud83e\udd74",
            "\ud83e\udd22",
            "\ud83e\udd2e",
            "\ud83e\udd27",
            "\ud83d\ude37",
            "\ud83e\udd12",
            "\ud83e\udd15",
            "\ud83e\udd11",
            "\ud83e\udd20",
            "\ud83d\ude08",
            "\ud83d\udc7f",
            "\ud83d\udc79",
            "\ud83d\udc7a",
            "\ud83e\udd21",
            "\ud83d\udca9",
            "\ud83d\udc7b",
            "\ud83d\udc80",
            "☠️",
            "\ud83d\udc7d",
            "\ud83d\udc7e",
            "\ud83e\udd16",
            "\ud83c\udf83",
            "\ud83d\ude3a",
            "\ud83d\ude38",
            "\ud83d\ude39",
            "\ud83d\ude3b",
            "\ud83d\ude3c",
            "\ud83d\ude3d",
            "\ud83d\ude40",
            "\ud83d\ude3f",
            "\ud83d\ude3e"
         )
      );
      Random random = new Random();
      return emojis.get(random.nextInt(emojis.size()));
   }

   @Override
   public int syncUserStatusConsistency() {
      int fixedCount = 0;
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
      if (serverConfigs.isEmpty()) {
         serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
      }

      for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
         try {
            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
            QueryResultUserDto remoteUsers = userServiceApi.getUsersQuery(null, null, null, null, null, null);
            if (remoteUsers != null && !CollectionUtils.isEmpty(remoteUsers.getItems())) {
               List<String> remoteEnabledUserIds = remoteUsers.getItems()
                  .stream()
                  .filter(u -> u.getPolicy() != null && !u.getPolicy().isIsDisabled())
                  .map(UserDto::getId)
                  .toList();
               if (!remoteEnabledUserIds.isEmpty()) {
                  for (EmbyUser user : new LambdaQueryChainWrapper<>(this.getBaseMapper())
                     .eq(serverConfig.id() != null, EmbyUser::getEmbyInfoId, serverConfig.id())
                     .eq(EmbyUser::getUserStatus, Integer.valueOf(1))
                     .eq(EmbyUser::getIsAdmin, Integer.valueOf(0))
                     .in(EmbyUser::getEmbyUserId, remoteEnabledUserIds)
                     .list()) {
                     try {
                        log.info("发现状态不一致用户，开始自动修复（禁用）：username={}, embyUserId={}", user.getEmbyUserName(), user.getEmbyUserId());
                        this.updateEmbyUserStatus(user, true, false);
                        fixedCount++;
                     } catch (Exception var12) {
                        log.error("修复用户状态失败：username={}", user.getEmbyUserName(), var12);
                     }
                  }
               }
            }
         } catch (Exception var13) {
            String serverName = serverConfig.serverName() != null ? serverConfig.serverName() : "Unknown";
            log.error("同步服务器用户状态失败：server={}", serverName, var13);
         }
      }

      return fixedCount;
   }

   @Override
   public byte[] exportUserDiffExcel(Long embyInfoId) {
      List<EmbyUserDiffResponse> diffRows = this.userDiff(embyInfoId);

      try {
         byte[] var20;
         try (
            XSSFWorkbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         ) {
            Sheet sheet = workbook.createSheet("用户差异");
            Row header = sheet.createRow(0);
            String[] headers = new String[]{"服务器ID", "服务器名称", "用户名", "系统状态", "Emby状态", "差异说明", "系统用户ID", "系统Emby用户ID", "Emby用户ID", "创建时间", "更新时间"};

            for (int i = 0; i < headers.length; i++) {
               Cell cell = header.createCell(i);
               cell.setCellValue(headers[i]);
            }

            int rowNum = 1;

            for (EmbyUserDiffResponse rowData : diffRows) {
               Row row = sheet.createRow(rowNum++);
               row.createCell(0).setCellValue(rowData.getEmbyInfoId() != null ? String.valueOf(rowData.getEmbyInfoId()) : "-");
               row.createCell(1).setCellValue(StringUtils.hasText(rowData.getServerName()) ? rowData.getServerName() : "-");
               row.createCell(2).setCellValue(rowData.getUserName());
               row.createCell(3).setCellValue(rowData.getSystemStatus());
               row.createCell(4).setCellValue(rowData.getEmbyStatus());
               row.createCell(5).setCellValue(rowData.getDiffType());
               row.createCell(6).setCellValue(rowData.getSystemUserId() != null ? String.valueOf(rowData.getSystemUserId()) : "-");
               row.createCell(7).setCellValue(StringUtils.hasText(rowData.getSystemEmbyUserId()) ? rowData.getSystemEmbyUserId() : "-");
               row.createCell(8).setCellValue(StringUtils.hasText(rowData.getEmbyUserId()) ? rowData.getEmbyUserId() : "-");
               row.createCell(9).setCellValue(rowData.getCreateDatetime() != null ? DateUtil.formatDateTime(rowData.getCreateDatetime()) : "-");
               row.createCell(10).setCellValue(rowData.getUpdateDatetime() != null ? DateUtil.formatDateTime(rowData.getUpdateDatetime()) : "-");
            }

            for (int i = 0; i < headers.length; i++) {
               sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            var20 = outputStream.toByteArray();
         }

         return var20;
      } catch (IOException var16) {
         throw new BizException("导出用户差异 Excel 失败");
      }
   }

   @Override
   public List<EmbyUserDiffResponse> userDiff(Long embyInfoId) {
      LambdaQueryChainWrapper<EmbyUser> localQuery = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(embyInfoId != null, EmbyUser::getEmbyInfoId, embyInfoId);
      Long currentUserId = this.currentLoginIdOrNull();
      boolean currentUserPrimaryAdmin = this.isCurrentUserPrimaryAdmin();
      List<EmbyUser> allLocalUsers = localQuery.list();
      Set<String> hiddenAdministratorNames = allLocalUsers.stream()
         .filter(user -> !this.isUserVisibleToAdministrator(user, currentUserId, currentUserPrimaryAdmin))
         .map(EmbyUser::getEmbyUserName)
         .filter(StringUtils::hasText)
         .collect(Collectors.toSet());
      List<EmbyUser> localUsers = allLocalUsers.stream()
         .filter(user -> this.isUserVisibleToAdministrator(user, currentUserId, currentUserPrimaryAdmin))
         .toList();
      Map<String, EmbyUser> localUserMap = localUsers.stream()
         .filter(u -> StringUtils.hasText(u.getEmbyUserName()))
         .collect(Collectors.toMap(EmbyUser::getEmbyUserName, u -> (EmbyUser)u, (a, b) -> a, TreeMap::new));
      Map<String, UserDto> remoteUserMap = new TreeMap<>();
      EmbyInfoCacheManagerUtils.EmbyServerConfig currentServerConfig = null;
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs;
      if (embyInfoId != null) {
         currentServerConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
         serverConfigs = List.of(currentServerConfig);
      } else {
         serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
         if (serverConfigs.isEmpty()) {
            serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
         }
      }

      for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
         if (currentServerConfig == null) {
            currentServerConfig = serverConfig;
         }

         try {
            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
            QueryResultUserDto remoteUsers = userServiceApi.getUsersQuery(null, null, null, null, null, null);
            if (remoteUsers != null && !CollectionUtils.isEmpty(remoteUsers.getItems())) {
               for (UserDto userDto : remoteUsers.getItems()) {
                  if (userDto != null && StringUtils.hasText(userDto.getName())) {
                     boolean remoteAdministrator = userDto.getPolicy() != null && userDto.getPolicy().isIsAdministrator();
                     boolean currentUserAccount = currentUserId != null
                        && localUsers.stream()
                           .anyMatch(localx -> Objects.equals(localx.getId(), currentUserId) && Objects.equals(localx.getEmbyUserName(), userDto.getName()));
                     if (!hiddenAdministratorNames.contains(userDto.getName()) && (currentUserPrimaryAdmin || !remoteAdministrator || currentUserAccount)) {
                        remoteUserMap.putIfAbsent(userDto.getName(), userDto);
                     }
                  }
               }
            }
         } catch (Exception var24) {
            log.warn("获取 Emby 用户失败，已跳过该服务器: server={}, error={}", serverConfig.serverName(), var24.getMessage());
         }
      }

      TreeSet<String> allUserNames = new TreeSet<>();
      allUserNames.addAll(localUserMap.keySet());
      allUserNames.addAll(remoteUserMap.keySet());
      Long resultServerId = embyInfoId;
      String resultServerName = currentServerConfig != null ? currentServerConfig.serverName() : null;
      List<EmbyUserDiffResponse> rows = new ArrayList<>();

      for (String userName : allUserNames) {
         EmbyUser local = localUserMap.get(userName);
         UserDto remote = remoteUserMap.get(userName);
         String localStatus = local == null ? "-" : (Objects.equals(local.getUserStatus(), 0) ? "启用" : "禁用");
         String remoteStatus = "-";
         if (remote != null) {
            boolean disabled = remote.getPolicy() != null && remote.getPolicy().isIsDisabled();
            remoteStatus = disabled ? "禁用" : "启用";
         }

         String diff;
         if (local != null && remote == null) {
            diff = "仅系统存在";
         } else if (local == null) {
            diff = "仅Emby存在";
         } else if (Objects.equals(localStatus, remoteStatus)) {
            diff = "状态一致";
         } else {
            diff = "状态不一致";
         }

         EmbyUserDiffResponse row = new EmbyUserDiffResponse();
         row.setEmbyInfoId(local != null ? local.getEmbyInfoId() : resultServerId);
         row.setServerName(resultServerName);
         row.setUserName(userName);
         row.setSystemStatus(localStatus);
         row.setEmbyStatus(remoteStatus);
         row.setDiffType(diff);
         row.setSystemUserId(local != null ? local.getId() : null);
         row.setSystemEmbyUserId(local != null ? local.getEmbyUserId() : null);
         row.setEmbyUserId(remote != null ? remote.getId() : null);
         row.setCreateDatetime(local != null ? local.getCreateDatetime() : null);
         row.setUpdateDatetime(local != null ? local.getUpdateDatetime() : null);
         rows.add(row);
      }

      return rows;
   }

   @Override
   public void setDistributor(Long userId, Integer isDistributor) {
      EmbyUser user = this.getById(userId);
      if (user == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         this.assertUserCanBeManaged(user);
         user.setIsDistributor(isDistributor);
         this.updateById(user);
      }
   }

   @Override
   public void assertUserCanBeManaged(Long userId) {
      this.assertUserCanBeManaged(this.requireUser(userId));
   }

   @Override
   public void assertUserCanBeViewed(Long userId) {
      this.assertAdministratorVisibleToCurrentUser(this.requireUser(userId));
   }

   @Override
   public void assertUserCanBeEdited(Long userId) {
      this.assertUserCanBeEdited(this.requireUser(userId));
   }

   private void assertUserCanBeEdited(EmbyUser user) {
      this.assertAdministratorVisibleToCurrentUser(user);
   }

   private void assertUserCanBeManaged(EmbyUser user) {
      this.assertAdministratorVisibleToCurrentUser(user);
      this.assertNotPrimaryAdmin(user);
   }

   private void assertExpirationDateCanBeModified(EmbyUser user) {
      this.assertAdministratorVisibleToCurrentUser(user);
      this.assertNotPrimaryAdmin(user);
      if (this.isAdministrator(user) && !this.isCurrentUserPrimaryAdmin()) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   @Override
   public void assertCurrentUserCanManageAdministrators() {
      if (!this.isCurrentUserPrimaryAdmin()) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   private EmbyUser requireUser(Long userId) {
      if (userId == null) {
         throw new BizException(ResponseStatusEnum.USER_ID_NOT_NULl);
      } else {
         EmbyUser user = this.getById(userId);
         if (user == null) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else {
            return user;
         }
      }
   }

   @Override
   public void assertEmbyUserCanBeManaged(String embyUserId) {
      if (StringUtils.hasText(embyUserId)) {
         List<EmbyUser> matchedUsers = this.lambdaQuery().eq(EmbyUser::getEmbyUserId, embyUserId).list();
         matchedUsers.forEach(this::assertUserCanBeManaged);
      }
   }

   private void assertAdministratorVisibleToCurrentUser(EmbyUser user) {
      if (this.isPrimaryAdmin(user)) {
         this.assertPrimaryAdminVisibleToCurrentUser(user);
      } else if (this.isAdministrator(user) && this.hasCurrentLogin() && !this.isCurrentUser(user) && !this.isCurrentUserPrimaryAdmin()) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      }
   }

   private void assertNotPrimaryAdmin(EmbyUser user) {
      if (this.isPrimaryAdmin(user)) {
         this.assertPrimaryAdminVisibleToCurrentUser(user);
         throw new BizException(ResponseStatusEnum.PRIMARY_ADMIN_PROTECTED);
      }
   }

   private void assertBotTargetCanBeManaged(EmbyUser user, boolean owner) {
      if (this.isPrimaryAdmin(user)) {
         throw new BizException(ResponseStatusEnum.PRIMARY_ADMIN_PROTECTED);
      } else if (this.isAdministrator(user) && !owner) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   private void assertPrimaryAdminVisibleToCurrentUser(EmbyUser user) {
      if (this.isPrimaryAdmin(user)) {
         if (!this.hasCurrentLogin()) {
            throw new BizException(ResponseStatusEnum.PRIMARY_ADMIN_PROTECTED);
         } else if (!this.isCurrentUser(user)) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         }
      }
   }

   private boolean isCurrentUser(EmbyUser user) {
      if (user != null && this.hasCurrentLogin()) {
         try {
            return Objects.equals(StpUtil.getLoginIdAsLong(), user.getId());
         } catch (Exception var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isCurrentUserPrimaryAdmin() {
      if (!this.hasCurrentLogin()) {
         return false;
      } else {
         try {
            return this.isPrimaryAdmin(this.getById(Long.valueOf(StpUtil.getLoginIdAsLong())));
         } catch (Exception var2) {
            return false;
         }
      }
   }

   private boolean hasCurrentLogin() {
      try {
         return StpUtil.isLogin();
      } catch (Exception var2) {
         return false;
      }
   }

   private Long currentLoginIdOrNull() {
      if (!this.hasCurrentLogin()) {
         return null;
      } else {
         try {
            return StpUtil.getLoginIdAsLong();
         } catch (Exception var2) {
            return null;
         }
      }
   }

   static void applyPrimaryAdminVisibility(QueryWrapper<?> queryWrapper, boolean includePrimaryAdmin) {
      queryWrapper.ne(!includePrimaryAdmin, "is_primary_admin", Integer.valueOf(1));
   }

   static void applyAdministratorVisibility(QueryWrapper<?> queryWrapper, Long currentUserId, boolean currentUserPrimaryAdmin) {
      if (!currentUserPrimaryAdmin) {
         if (currentUserId == null) {
            queryWrapper.and(wrapper -> wrapper.ne("is_admin", Integer.valueOf(1)).or().isNull("is_admin"));
         } else {
            queryWrapper.and(
               wrapper -> wrapper.and(nonAdministrator -> nonAdministrator.ne("is_admin", Integer.valueOf(1)).or().isNull("is_admin"))
                     .or()
                     .eq("id", currentUserId)
            );
         }
      }
   }

   static String userListOrderBy() {
      return "order by case when is_primary_admin = 1 then 0 when is_admin = 1 then 1 else 2 end, id desc";
   }

   private boolean isPrimaryAdmin(EmbyUser user) {
      return user != null && Integer.valueOf(1).equals(user.getIsPrimaryAdmin());
   }

   private boolean isAdministrator(EmbyUser user) {
      return user != null && Integer.valueOf(1).equals(user.getIsAdmin());
   }

   private boolean isUserVisibleToAdministrator(EmbyUser user, Long currentUserId, boolean currentUserPrimaryAdmin) {
      return currentUserPrimaryAdmin || !this.isAdministrator(user) || currentUserId != null && Objects.equals(currentUserId, user.getId());
   }

   private void populateAdminMenuPermissions(EmbyUserCustomResponse response, EmbyUser user) {
      response.setMenuPermissions(this.adminMenuPermissionService.resolveMenuKeys(user));
   }

   private void populateAvatar(EmbyUserCustomResponse embyUserCustomResponse, Long userId) {
      try {
         String redisKeyV2 = "user:avatar:v2:" + userId;
         String redisKeyLegacy = "user:avatar:" + userId;
         String base64Avatar = null;
         byte[] avatarBytes = this.binaryRedisTemplate.opsForValue().get(redisKeyV2);
         if (avatarBytes != null) {
            base64Avatar = Base64.getEncoder().encodeToString(avatarBytes);
         } else {
            Object avatarObj = this.redisTemplate.opsForValue().get(redisKeyLegacy);
            if (avatarObj instanceof String) {
               base64Avatar = (String)avatarObj;

               try {
                  byte[] legacyBytes = Base64.getDecoder().decode(base64Avatar);
                  this.binaryRedisTemplate.opsForValue().set(redisKeyV2, legacyBytes);
                  this.redisTemplate.delete(redisKeyLegacy);
               } catch (IllegalArgumentException var9) {
               }
            } else if (avatarObj instanceof byte[] legacyBytes) {
               base64Avatar = Base64.getEncoder().encodeToString(legacyBytes);
               this.binaryRedisTemplate.opsForValue().set(redisKeyV2, legacyBytes);
               this.redisTemplate.delete(redisKeyLegacy);
            } else if (avatarObj != null) {
               base64Avatar = avatarObj.toString();
            }
         }

         if (StringUtils.hasText(base64Avatar)) {
            String mimeType = "image/jpeg";
            if (base64Avatar.startsWith("iVBORw0KGgo")) {
               mimeType = "image/png";
            } else if (base64Avatar.startsWith("R0lGOD")) {
               mimeType = "image/gif";
            } else if (base64Avatar.startsWith("UklGR")) {
               mimeType = "image/webp";
            }

            embyUserCustomResponse.setAvatar("data:" + mimeType + ";base64," + base64Avatar);
         }
      } catch (Exception var10) {
         log.warn("Failed to retrieve avatar from Redis for user {}", userId, var10);
      }
   }

   @Override
   public void updateTheme(String theme) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      if (embyUser != null) {
         this.redisTemplate.opsForValue().set("user:theme:" + embyUser.getId(), theme);
      }
   }

   private static record InvitationRewardResult(Integer duration, String unit) {
      static EmbyUserServiceImpl.InvitationRewardResult none() {
         return new EmbyUserServiceImpl.InvitationRewardResult(0, null);
      }
   }
}
