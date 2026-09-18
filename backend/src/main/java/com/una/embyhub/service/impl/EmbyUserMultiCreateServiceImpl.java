package com.una.embyhub.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserMultiCreateRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserMultiCreateResponse;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.EmbyUserRegisterRecord;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyLibraryAccessService;
import com.una.embyhub.service.EmbyUserCredentialPolicy;
import com.una.embyhub.service.EmbyUserIdentityUtils;
import com.una.embyhub.service.EmbyUserMultiCreateService;
import com.una.embyhub.service.EmbyUserRegisterRecordService;
import com.una.embyhub.service.EmbyUserService;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.UserServiceApi;
import embyclient.auth.ApiKeyAuth;
import embyclient.model.CreateUserByName;
import embyclient.model.LibraryUserCopyOptions;
import embyclient.model.QueryResultUserDto;
import embyclient.model.UpdateUserPassword;
import embyclient.model.UserDto;
import java.net.URL;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyUserMultiCreateServiceImpl implements EmbyUserMultiCreateService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyUserMultiCreateServiceImpl.class);
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private EmbyLibraryAccessService embyLibraryAccessService;
   @Autowired
   private EmbyUserRegisterRecordService embyUserRegisterRecordService;
   @Autowired
   private EmbyUserCredentialPolicy embyUserCredentialPolicy;

   @Override
   public EmbyUserMultiCreateResponse createMultiServerUser(EmbyUserMultiCreateRequest request) {
      if (Integer.valueOf(1).equals(request.getIsAdmin())) {
         this.embyUserService.assertCurrentUserCanManageAdministrators();
      }

      if (!this.isConfigEnabled("multi_server_user_creation")) {
         throw new BizException("多服务器同步创建用户功能未开启");
      } else {
         List<EmbyInfo> enabledServers = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
            .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
            .eq(EmbyInfo::getStatus, Integer.valueOf(0))
            .list();
         if (CollectionUtils.isEmpty(enabledServers)) {
            throw new BizException("当前没有启用的Emby服务器");
         } else {
            String userName = request.getEmbyUserName();
            if (!StringUtils.hasText(userName)) {
               throw new BizException("用户名不能为空");
            } else {
               String preparedPassword = StringUtils.hasText(request.getEmbyUserPassword()) ? request.getEmbyUserPassword() : RandomUtil.randomNumbers(6);
               request.setEmbyUserPassword(preparedPassword);

               EmbyUserMultiCreateResponse var7;
               try (EmbyUserCredentialPolicy.RegistrationGuard ignored = this.embyUserCredentialPolicy.guardIndependentRegistration(userName, preparedPassword)) {
                  Long identityGroupId = EmbyUserIdentityUtils.newIdentityGroupId();
                  var7 = this.createMultiServerUserLocked(request, enabledServers, userName, preparedPassword, identityGroupId);
               }

               return var7;
            }
         }
      }
   }

   private EmbyUserMultiCreateResponse createMultiServerUserLocked(
      EmbyUserMultiCreateRequest request, List<EmbyInfo> enabledServers, String userName, String preparedPassword, Long identityGroupId
   ) {
      for (EmbyInfo server : enabledServers) {
         Long count = new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper())
            .eq(EmbyUser::getEmbyUserName, userName)
            .eq(EmbyUser::getEmbyInfoId, server.getId())
            .count();
         if (count > 0L) {
            throw new BizException("用户 [" + userName + "] 在服务器 [" + server.getServerName() + "] 本地记录已存在");
         }

         try {
            EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(server);
            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);

            try {
               QueryResultUserDto queryResult = userServiceApi.getUsersQuery(null, null, null, null, null, null);
               boolean exists = Optional.ofNullable(queryResult.getItems())
                  .orElse(Collections.emptyList())
                  .stream()
                  .anyMatch(u -> u.getName().equalsIgnoreCase(userName));
               if (exists) {
                  throw new BizException("用户 [" + userName + "] 在 Emby 服务器 [" + server.getServerName() + "] 上已存在");
               }
            } catch (ApiException var14) {
               log.error("检查用户存在性失败: server={}", server.getServerName(), var14);
               throw new BizException("连接服务器 [" + server.getServerName() + "] 检测用户失败");
            }
         } catch (Exception var15) {
            if (var15 instanceof BizException) {
               throw (BizException)var15;
            }

            log.error("服务器异常", (Throwable)var15);
            throw new BizException("服务器 [" + server.getServerName() + "] 连接异常");
         }
      }

      List<EmbyUserMultiCreateResponse.ServerInfo> serverInfos = new ArrayList<>();

      for (EmbyInfo server : enabledServers) {
         try {
            this.createSingleServerUser(server, request, identityGroupId);
            EmbyUserMultiCreateResponse.ServerInfo serverInfo = new EmbyUserMultiCreateResponse.ServerInfo();
            serverInfo.setServerName(server.getServerName());

            try {
               URL serverUrl = new URL(this.buildServerBaseUrl(server));
               serverInfo.setProtocol(serverUrl.getProtocol());
               serverInfo.setHost(serverUrl.getHost());
               int port = serverUrl.getPort();
               if (port == -1) {
                  port = serverUrl.getDefaultPort();
               }

               if (port != -1) {
                  serverInfo.setPort(port);
               }
            } catch (Exception var16) {
               log.warn("URL解析失败: {}", server.getServerName());
               serverInfo.setProtocol(server.getEmbyAgreement());
               serverInfo.setHost(server.getEmbyUrl());
               if (StringUtils.hasText(server.getEmbyPort())) {
                  try {
                     serverInfo.setPort(Integer.valueOf(server.getEmbyPort()));
                  } catch (NumberFormatException var13) {
                  }
               }
            }

            serverInfos.add(serverInfo);
         } catch (Exception var17) {
            log.error("在服务器 {} 创建用户失败", server.getServerName(), var17);
            throw new BizException("在服务器 [" + server.getServerName() + "] 创建用户失败: " + var17.getMessage());
         }
      }

      EmbyUserMultiCreateResponse response = new EmbyUserMultiCreateResponse();
      response.setUsername(userName);
      response.setPassword(preparedPassword);
      response.setServers(serverInfos);
      return response;
   }

   private void createSingleServerUser(EmbyInfo server, EmbyUserMultiCreateRequest request, Long identityGroupId) throws ApiException {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(server);
      UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
      CreateUserByName createUserByName = new CreateUserByName();
      createUserByName.setName(request.getEmbyUserName());
      createUserByName.setCopyFromUserId(server.getCopyfromuserid());
      createUserByName.setUserCopyOptions(Arrays.asList(LibraryUserCopyOptions.USERPOLICY, LibraryUserCopyOptions.USERCONFIGURATION));
      UserDto userDto = userServiceApi.postUsersNew(createUserByName);

      try {
         this.embyLibraryAccessService.applyGlobalRuleToRemoteUser(server.getId(), userDto.getId());
      } catch (RuntimeException var15) {
         try {
            userServiceApi.deleteUsersById(userDto.getId());
         } catch (Exception var14) {
            log.error("多服创建媒体库分级失败，且远端补偿删除失败: serverId={}, embyUserId={}", server.getId(), userDto.getId(), var14);
         }

         throw var15;
      }

      String password = request.getEmbyUserPassword();
      if (!StringUtils.hasText(password)) {
         password = RandomUtil.randomNumbers(6);
      }

      UpdateUserPassword updateUserPassword = new UpdateUserPassword();
      updateUserPassword.setNewPw(password);
      userServiceApi.postUsersByIdPassword(updateUserPassword, userDto.getId());
      EmbyUser embyUser = new EmbyUser();
      embyUser.setEmbyInfoId(server.getId());
      Integer hostLineType = HostLineTypeEnum.normalize(request.getHostLineType());
      embyUser.setHostLineType(hostLineType);
      embyUser.setEmbyUserId(userDto.getId());
      embyUser.setEmbyUserName(userDto.getName());
      embyUser.setEmbyUserPassword(SaSecureUtil.md5(password));
      embyUser.setIdentityGroupId(identityGroupId);
      Integer days = request.getDay() != null ? request.getDay() : 30;
      if (hostLineType == HostLineTypeEnum.WHITELIST.getCode()) {
         embyUser.setExpirationDate(null);
      } else {
         embyUser.setExpirationDate(DateUtil.offsetDay(new Date(), days));
      }

      embyUser.setUserStatus(0);
      embyUser.setRemarks(request.getRemarks());
      embyUser.setRegisterChannel(RegisterChannelEnum.ADMIN_REGISTER.getCode());
      embyUser.setIsAdmin(request.getIsAdmin() != null ? request.getIsAdmin() : 0);
      String requestCount = this.configCacheLoaderUtils.getConfigValue("request_count");
      if (StringUtils.hasText(requestCount)) {
         embyUser.setRequestPackagesCount(Integer.valueOf(requestCount));
      }

      this.embyUserService.save(embyUser);
      this.saveRegisterRecord(embyUser, RegisterChannelEnum.ADMIN_REGISTER.getCode(), "后台多服务器批量创建");
   }

   private boolean isConfigEnabled(String configKey) {
      return this.configCacheLoaderUtils.getConfigValue(configKey) != null;
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveServerConfig(EmbyInfo embyInfo) {
      String url = this.buildServerBaseUrl(embyInfo);
      return new EmbyInfoCacheManagerUtils.EmbyServerConfig(
         embyInfo.getId(), url, embyInfo.getEmbyApikey(), embyInfo.getCopyfromuserid(), "multiserver", embyInfo.getAdminQueryUserid()
      );
   }

   private String buildServerBaseUrl(EmbyInfo embyInfo) {
      String embyUrl = embyInfo.getEmbyUrl();
      if (embyUrl == null || !embyUrl.startsWith("http://") && !embyUrl.startsWith("https://")) {
         StringBuilder baseUrl = new StringBuilder();
         if (StringUtils.hasText(embyInfo.getEmbyAgreement())) {
            baseUrl.append(embyInfo.getEmbyAgreement()).append("://");
         }

         if (StringUtils.hasText(embyInfo.getEmbyUrl())) {
            baseUrl.append(embyInfo.getEmbyUrl());
         }

         if (StringUtils.hasText(embyInfo.getEmbyPort())) {
            if (embyInfo.getEmbyUrl() != null && !embyInfo.getEmbyUrl().contains(":")) {
               baseUrl.append(":");
            }

            baseUrl.append(embyInfo.getEmbyPort());
         }

         return baseUrl.toString();
      } else {
         return embyUrl;
      }
   }

   private UserServiceApi buildUserServiceApi(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      ApiClient client = new ApiClient();
      client.setBasePath(config.url());
      ApiKeyAuth apiKeyAuth = (ApiKeyAuth)client.getAuthentication("apikeyauth");
      apiKeyAuth.setApiKey(config.apiKey());
      return new UserServiceApi(client);
   }

   private void saveRegisterRecord(EmbyUser embyUser, Integer channel, String channelDetail) {
      EmbyUserRegisterRecord record = new EmbyUserRegisterRecord();
      record.setUserId(embyUser.getId());
      record.setEmbyUserId(embyUser.getEmbyUserId());
      record.setEmbyUserName(embyUser.getEmbyUserName());
      record.setRegisterChannel(channel);
      record.setRegisterChannelDetail(channelDetail);
      record.setEmbyInfoId(embyUser.getEmbyInfoId());
      record.setRemarks(embyUser.getRemarks());
      record.setExpirationDate(embyUser.getExpirationDate());
      Date recordCreatedAt = new Date();
      record.setCreateDatetime(recordCreatedAt);
      record.setRegisterDays(this.resolveRegisterDays(recordCreatedAt, embyUser.getExpirationDate()));
      this.embyUserRegisterRecordService.save(record);
   }

   private Integer resolveRegisterDays(Date registrationDate, Date expirationDate) {
      if (registrationDate != null && expirationDate != null && expirationDate.after(registrationDate)) {
         long days = ChronoUnit.DAYS
            .between(
               registrationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
               expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );
         return days > 0L && days <= 2147483647L ? (int)days : null;
      } else {
         return null;
      }
   }
}
