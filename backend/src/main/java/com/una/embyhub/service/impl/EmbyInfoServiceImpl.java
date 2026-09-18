package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyClientAdminUserUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyInfoMapper;
import com.una.embyhub.mapper.HostLineMapper;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoRequest;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoSave;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoUpdate;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoUserOptionsRequest;
import com.una.embyhub.model.dto.response.embyinfo.EmbyInfoResponse;
import com.una.embyhub.model.dto.response.embyinfo.EmbyInfoUserOptionResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.HostLine;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import embyclient.ApiClient;
import embyclient.api.SystemServiceApi;
import embyclient.auth.ApiKeyAuth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyInfoServiceImpl extends ServiceImpl<EmbyInfoMapper, EmbyInfo> implements EmbyInfoService {
   private static final String EMBY_URL_REQUIRED_SUFFIX = "/emby/";
   private static final String EMBY_URL_HTTP_PROTOCOL = "http://";
   private static final String EMBY_URL_HTTPS_PROTOCOL = "https://";
   private static final String EMBY_URL_FORMAT_MESSAGE = "服务器地址必须以 http:// 或 https:// 开头，并以 /emby/ 结尾";
   @Autowired
   private HostLineMapper hostLineMapper;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private ApiClient apiClient;
   @Autowired
   private EmbyUserService embyUserService;

   private void refreshEmbyConfig() {
      this.embyInfoCacheManager.refresh();
      this.embyInfoCacheManager.applyTo(this.apiClient);
   }

   @Override
   public Page<EmbyInfoResponse> select(MybatisPlusPage<EmbyInfoRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("spread");
      queryWrapper.orderByDesc("id");
      Page<EmbyInfoResponse> embyInfoResponsePage = MpConvert.page(
         queryWrapper, this.getBaseMapper(), EmbyInfoResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      Map<Long, Long> lineCountByServer = Collections.emptyMap();
      List<Long> embyInfoIds = embyInfoResponsePage.getRecords().stream().map(EmbyInfoResponse::getId).filter(Objects::nonNull).toList();
      if (!embyInfoIds.isEmpty()) {
         QueryWrapper<HostLine> lineCountWrapper = new QueryWrapper<>();
         lineCountWrapper.select(new String[]{"emby_info_id AS embyInfoId", "COUNT(*) AS lineCount"});
         lineCountWrapper.eq("del_flag", Integer.valueOf(0));
         lineCountWrapper.in("emby_info_id", embyInfoIds);
         lineCountWrapper.groupBy("emby_info_id");
         List<Map<String, Object>> rows = this.hostLineMapper.selectMaps(lineCountWrapper);
         lineCountByServer = rows.stream()
            .collect(Collectors.toMap(row -> this.toLong(row.get("embyInfoId")), row -> this.toLong(row.get("lineCount")), (left, right) -> left, HashMap::new));
      }

      Map<Long, Long> finalLineCountByServer = lineCountByServer;
      embyInfoResponsePage.getRecords().forEach(item -> item.setLineCount(finalLineCountByServer.getOrDefault(item.getId(), 0L)));
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      if (embyUser.getIsAdmin() == 0) {
         embyInfoResponsePage.getRecords().forEach(item -> item.setCopyfromuserid(null));
      }

      embyInfoResponsePage.getRecords().forEach(item -> item.setEmbyApikey(null));
      return embyInfoResponsePage;
   }

   @Override
   public void deleteByUserId(List<Long> embyInfoIds) {
      StringBuilder errorMessage = new StringBuilder();

      for (Long embyInfoId : embyInfoIds) {
         long userCount = this.embyUserService.lambdaQuery().eq(EmbyUser::getEmbyInfoId, embyInfoId).count();
         if (userCount > 0L) {
            EmbyInfo embyInfo = this.getById(embyInfoId);
            String serverName = embyInfo != null ? embyInfo.getServerName() : String.valueOf(embyInfoId);
            errorMessage.append("无法删除服务器 [").append(serverName).append("]，存在 ").append(userCount).append(" 个关联的用户\n");
         }
      }

      if (errorMessage.length() > 0) {
         throw new BizException(errorMessage.toString().trim());
      } else {
         this.removeByIds(embyInfoIds);
         this.refreshEmbyConfig();
      }
   }

   @Override
   public void insertEmbyInfo(EmbyInfoSave embyInfoSave) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfo embyInfo = BeanUtils.convert(embyInfoSave, EmbyInfo.class);
      this.validateEmbyUrl(embyInfo);
      this.normalizeRequiredEmbyApiKey(embyInfo);
      this.normalizeEmbyOpenUrl(embyInfo);
      String embyServerId = this.fetchEmbyServerId(embyInfo);
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(EmbyInfo::getEmbyServerId, embyServerId).count();
      if (count > 0L) {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_EXISTS);
      } else {
         embyInfo.setEmbyServerId(embyServerId);
         embyInfo.setUserId(embyUser.getId());
         if (Objects.equals(embyInfo.getEnabled(), 1)) {
            this.lambdaUpdate().set(EmbyInfo::getEnabled, Integer.valueOf(0)).eq(BaseEntity::getDelFlag, Integer.valueOf(0)).update();
         }

         this.save(embyInfo);
         this.refreshEmbyConfig();
      }
   }

   @Override
   public void updateEmbyInfo(EmbyInfoUpdate embyUserUpdate) {
      EmbyInfo embyInfo = BeanUtils.convert(embyUserUpdate, EmbyInfo.class);
      this.validateEmbyUrl(embyInfo);
      this.preserveExistingEmbyApiKeyIfBlank(embyInfo);
      this.normalizeEmbyOpenUrl(embyInfo);
      if (embyUserUpdate.getSpread() == 1) {
         new LambdaUpdateChainWrapper<>(this.getBaseMapper()).set(EmbyInfo::getSpread, Integer.valueOf(0)).update();
      }

      String embyServerId = this.fetchEmbyServerId(embyInfo);
      embyInfo.setEmbyServerId(embyServerId);
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(EmbyInfo::getEmbyServerId, embyServerId)
         .ne(EmbyInfo::getId, embyInfo.getId())
         .count();
      if (count > 0L) {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_EXISTS);
      } else {
         this.lambdaUpdate()
            .set(EmbyInfo::getSpread, embyUserUpdate.getSpread())
            .set(EmbyInfo::getEnabled, embyUserUpdate.getEnabled())
            .eq(EmbyInfo::getId, embyInfo.getId())
            .update();
         this.updateById(embyInfo);
         this.refreshEmbyConfig();
      }
   }

   @Override
   public void enableEmbyServer(Long embyInfoId) {
      EmbyInfo embyInfo = this.getById(embyInfoId);
      if (embyInfo == null) {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_FOUND);
      } else {
         this.lambdaUpdate()
            .set(EmbyInfo::getEnabled, Integer.valueOf(1))
            .eq(EmbyInfo::getId, embyInfoId)
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .update();
         this.refreshEmbyConfig();
      }
   }

   @Override
   public void updateEmbyServerEnabled(Long embyInfoId, Integer enabled) {
      if (!Objects.equals(enabled, 0) && !Objects.equals(enabled, 1)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      } else {
         EmbyInfo embyInfo = this.getById(embyInfoId);
         if (embyInfo == null) {
            throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_FOUND);
         } else {
            this.lambdaUpdate().set(EmbyInfo::getEnabled, enabled).eq(EmbyInfo::getId, embyInfoId).eq(BaseEntity::getDelFlag, Integer.valueOf(0)).update();
            this.refreshEmbyConfig();
         }
      }
   }

   @Override
   public List<EmbyInfoUserOptionResponse> listSelectableUsers(EmbyInfoUserOptionsRequest request) {
      if (request == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请先正确填写服务器地址和 ApiKey，才能获取用户信息");
      } else {
         EmbyInfo embyInfo;
         if (request.getEmbyInfoId() != null) {
            embyInfo = this.getById(request.getEmbyInfoId());
            if (embyInfo == null || !StringUtils.hasText(embyInfo.getEmbyApikey())) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "所选服务器不存在或未配置 ApiKey");
            }
         } else {
            if (!StringUtils.hasText(request.getEmbyApikey())) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请先正确填写服务器地址和 ApiKey，才能获取用户信息");
            }

            embyInfo = new EmbyInfo();
            embyInfo.setEmbyUrl(request.getEmbyUrl());
            embyInfo.setEmbyApikey(request.getEmbyApikey().trim());
            this.validateEmbyUrl(embyInfo);
         }

         try {
            return EmbyClientAdminUserUtils.listEnabledNonAdministratorUsersNoPaging(embyInfo.getEmbyUrl(), embyInfo.getEmbyApikey())
               .stream()
               .map(user -> new EmbyInfoUserOptionResponse(user.id, user.name, user.avatarUrl))
               .toList();
         } catch (Exception var4) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "无法获取用户信息，请确认服务器地址和 ApiKey 是否正确");
         }
      }
   }

   @Override
   public EmbyInfo getEmbyInfoEnabled() {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyUser embyUserData = this.embyUserService.getById(embyUser.getId());
      return new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(embyUserData.getIsAdmin() == 0, EmbyInfo::getId, embyUserData.getEmbyInfoId())
         .eq(embyUserData.getIsAdmin() == 1, EmbyInfo::getSpread, Integer.valueOf(1))
         .one();
   }

   @Override
   public EmbyInfo getByApiKey(String apiKey) {
      return new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getEmbyApikey, apiKey)
         .one();
   }

   @Override
   public EmbyInfo getByServerId(String serverId) {
      return new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getEmbyServerId, serverId)
         .one();
   }

   private String fetchEmbyServerId(EmbyInfo embyInfo) {
      try {
         ApiClient client = new ApiClient();
         client.setBasePath(this.buildServerBaseUrl(embyInfo));
         ApiKeyAuth apiKeyAuth = (ApiKeyAuth)client.getAuthentication("apikeyauth");
         if (apiKeyAuth != null) {
            apiKeyAuth.setApiKey(embyInfo.getEmbyApikey());
         }

         SystemServiceApi systemServiceApi = new SystemServiceApi(client);
         return systemServiceApi.getSystemInfo().getId();
      } catch (Exception var5) {
         throw new BizException("获取Emby服务器ID失败:" + var5.getMessage());
      }
   }

   private void validateEmbyUrl(EmbyInfo embyInfo) {
      String embyUrl = embyInfo.getEmbyUrl();
      String trimmedEmbyUrl = StringUtils.hasText(embyUrl) ? embyUrl.trim() : "";
      boolean hasRequiredProtocol = trimmedEmbyUrl.startsWith("http://") || trimmedEmbyUrl.startsWith("https://");
      if (hasRequiredProtocol && trimmedEmbyUrl.endsWith("/emby/")) {
         embyInfo.setEmbyUrl(trimmedEmbyUrl);
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "服务器地址必须以 http:// 或 https:// 开头，并以 /emby/ 结尾");
      }
   }

   private void normalizeRequiredEmbyApiKey(EmbyInfo embyInfo) {
      String embyApikey = embyInfo.getEmbyApikey();
      if (!StringUtils.hasText(embyApikey)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请填写 Emby ApiKey");
      } else {
         embyInfo.setEmbyApikey(embyApikey.trim());
      }
   }

   private void preserveExistingEmbyApiKeyIfBlank(EmbyInfo embyInfo) {
      String embyApikey = embyInfo.getEmbyApikey();
      if (StringUtils.hasText(embyApikey)) {
         embyInfo.setEmbyApikey(embyApikey.trim());
      } else {
         EmbyInfo existing = this.getById(embyInfo.getId());
         if (existing != null && StringUtils.hasText(existing.getEmbyApikey())) {
            embyInfo.setEmbyApikey(existing.getEmbyApikey());
         } else {
            throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_FOUND);
         }
      }
   }

   private void normalizeEmbyOpenUrl(EmbyInfo embyInfo) {
      String embyOpenUrl = embyInfo.getEmbyOpenUrl();
      if (!StringUtils.hasText(embyOpenUrl)) {
         embyInfo.setEmbyOpenUrl(null);
      } else {
         embyInfo.setEmbyOpenUrl(embyOpenUrl.trim());
      }
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

   private long toLong(Object value) {
      if (value == null) {
         return 0L;
      } else if (value instanceof Number number) {
         return number.longValue();
      } else {
         try {
            return Long.parseLong(value.toString());
         } catch (Exception var3) {
            return 0L;
         }
      }
   }
}
