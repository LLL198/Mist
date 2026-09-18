package com.una.embyhub.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyDeviceMapper;
import com.una.embyhub.model.dto.request.embydevice.EmbyDeviceBlockRequest;
import com.una.embyhub.model.dto.request.embydevice.EmbyDeviceRequest;
import com.una.embyhub.model.dto.response.emby.EmbyDeviceInfoResponse;
import com.una.embyhub.model.dto.response.embydevice.EmbyDeviceResponse;
import com.una.embyhub.model.entity.EmbyDevice;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.service.EmbyBlockKeywordService;
import com.una.embyhub.service.EmbyDeviceService;
import com.una.embyhub.service.EmbyInfoService;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyDeviceServiceImpl extends ServiceImpl<EmbyDeviceMapper, EmbyDevice> implements EmbyDeviceService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyDeviceServiceImpl.class);
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final EmbyInfoService embyInfoService;
   private final EmbyBlockKeywordService embyBlockKeywordService;

   @Override
   public Page<EmbyDeviceResponse> select(MybatisPlusPage<EmbyDeviceRequest> page) {
      EmbyDeviceRequest request = page.getObject();
      LambdaQueryWrapper<EmbyDevice> queryWrapper = Wrappers.lambdaQuery(EmbyDevice.class)
         .like(
            request != null && StringUtils.hasText(request.getDeviceName()),
            EmbyDevice::getDeviceName,
            Optional.ofNullable(request).map(EmbyDeviceRequest::getDeviceName).orElse(null)
         )
         .like(
            request != null && StringUtils.hasText(request.getAppName()),
            EmbyDevice::getAppName,
            Optional.ofNullable(request).map(EmbyDeviceRequest::getAppName).orElse(null)
         )
         .eq(
            request != null && request.getBlocked() != null,
            EmbyDevice::getBlocked,
            Optional.ofNullable(request).map(EmbyDeviceRequest::getBlocked).orElse(null)
         )
         .eq(
            request != null && request.getEmbyInfoId() != null,
            EmbyDevice::getEmbyInfoId,
            Optional.ofNullable(request).map(EmbyDeviceRequest::getEmbyInfoId).orElse(null)
         )
         .orderByDesc(EmbyDevice::getId);
      return MpConvert.page(queryWrapper, this.getBaseMapper(), EmbyDeviceResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public void blockDevice(EmbyDeviceBlockRequest request) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.resolveConfig(request.getEmbyInfoId());
      String deviceId = request.getDeviceId();
      log.info("手动禁用设备，serverId={} deviceId={}", config.id(), deviceId);
      this.blockDeviceInServer(deviceId, config);
      LambdaQueryWrapper<EmbyDevice> wrapper = Wrappers.lambdaQuery(EmbyDevice.class)
         .eq(EmbyDevice::getDeviceId, deviceId)
         .eq(config.id() != null, EmbyDevice::getEmbyInfoId, config.id());
      EmbyDevice embyDevice = this.getOne(wrapper, false);
      if (embyDevice != null) {
         embyDevice.setBlocked(1);
         this.updateById(embyDevice);
      }
   }

   @Override
   public void syncDevices() {
      for (EmbyInfoCacheManagerUtils.EmbyServerConfig config : Optional.ofNullable(this.embyInfoCacheManager.getEnabledConfigs())
         .filter(configs -> !configs.isEmpty())
         .orElseGet(() -> List.of(this.embyInfoCacheManager.getRequiredConfig()))) {
         try {
            List<EmbyDeviceInfoResponse> devices = this.fetchDevices(config);
            if (!CollectionUtils.isEmpty(devices)) {
               Map<String, EmbyDevice> existingDevices = this.list(
                     Wrappers.lambdaQuery(EmbyDevice.class).eq(config.id() != null, EmbyDevice::getEmbyInfoId, config.id())
                  )
                  .stream()
                  .collect(Collectors.toMap(EmbyDevice::getDeviceId, it -> (EmbyDevice)it, (a, b) -> a));
               List<EmbyDevice> toInsert = new ArrayList<>();
               List<EmbyDevice> toUpdate = new ArrayList<>();

               for (EmbyDeviceInfoResponse deviceInfo : devices) {
                  EmbyDevice existed = existingDevices.get(deviceInfo.getId());
                  if (existed != null) {
                     EmbyDevice entity = this.buildEntity(deviceInfo, config);
                     if (this.isDeviceChanged(existed, entity)) {
                        entity.setId(existed.getId());
                        toUpdate.add(entity);
                     }
                  } else {
                     toInsert.add(this.buildEntity(deviceInfo, config));
                  }
               }

               if (!toInsert.isEmpty()) {
                  log.info("服务器 [{}] 新增 {} 个设备", config.url(), toInsert.size());
                  this.saveBatch(toInsert);
               }

               if (!toUpdate.isEmpty()) {
                  log.info("服务器 [{}] 更新 {} 个设备", config.url(), toUpdate.size());
                  this.updateBatchById(toUpdate);
               }
            }
         } catch (Exception var12) {
            log.error("同步服务器 [{}] 设备信息失败: {}", config.url(), var12.getMessage(), var12);
         }
      }
   }

   private boolean isDeviceChanged(EmbyDevice existing, EmbyDevice newData) {
      return !Objects.equals(existing.getDeviceName(), newData.getDeviceName())
         || !Objects.equals(existing.getAppName(), newData.getAppName())
         || !Objects.equals(existing.getAppVersion(), newData.getAppVersion())
         || !Objects.equals(existing.getLastUserId(), newData.getLastUserId())
         || !Objects.equals(existing.getLastUserName(), newData.getLastUserName())
         || !Objects.equals(existing.getBlocked(), newData.getBlocked())
         || !Objects.equals(existing.getLastActivityTime(), newData.getLastActivityTime());
   }

   @Override
   public List<String> getDefaultBlockKeywords() {
      return this.embyBlockKeywordService.getDefaultClientFilterPatterns();
   }

   private List<EmbyDeviceInfoResponse> fetchDevices(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      Map<String, Object> paramMap = Map.of("api_key", config.apiKey());
      String baseUrl = config.url().endsWith("/") ? config.url() : config.url() + "/";
      String body = HttpUtil.get(baseUrl + "Devices", paramMap);
      if (body != null && !body.isEmpty()) {
         JSONObject jsonObj = JSON.parseObject(body);
         if (jsonObj == null) {
            return List.of();
         } else {
            JSONArray items = jsonObj.getJSONArray("Items");
            return items != null && !items.isEmpty() ? items.toJavaList(EmbyDeviceInfoResponse.class) : List.of();
         }
      } else {
         return List.of();
      }
   }

   private void blockDeviceInServer(String deviceId, EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      String baseUrl = config.url().endsWith("/") ? config.url() : config.url() + "/";
      HttpResponse response = HttpRequest.delete(baseUrl + "Devices?Id=" + deviceId + "&api_key=" + config.apiKey()).timeout(8000).execute();
      if (response.getStatus() < 200 || response.getStatus() >= 300) {
         log.error("禁用设备失败 status={} body={}", response.getStatus(), response.body());
         throw new BizException(ResponseStatusEnum.EMBY_EXCEPTIION);
      }
   }

   private EmbyDevice buildEntity(EmbyDeviceInfoResponse deviceInfo, EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      EmbyDevice entity = new EmbyDevice();
      entity.setDeviceId(deviceInfo.getId());
      entity.setDeviceName(deviceInfo.getName());
      entity.setAppName(deviceInfo.getAppName());
      entity.setAppVersion(deviceInfo.getAppVersion());
      entity.setLastUserId(deviceInfo.getLastUserId());
      entity.setLastUserName(deviceInfo.getLastUserName());
      entity.setBlocked(Boolean.TRUE.equals(deviceInfo.getBlocked()) ? 1 : 0);
      entity.setEmbyInfoId(config.id());
      entity.setEmbyServerId(deviceInfo.getServerId());
      entity.setLastActivityTime(this.convertDate(deviceInfo.getDateLastActivity()));
      entity.setServerName(this.resolveServerName(config));
      return entity;
   }

   private Date convertDate(OffsetDateTime dateLastActivity) {
      return dateLastActivity == null ? null : Date.from(dateLastActivity.toInstant());
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveConfig(Long embyInfoId) {
      if (embyInfoId != null) {
         List<EmbyInfoCacheManagerUtils.EmbyServerConfig> enabledConfigs = this.embyInfoCacheManager.getEnabledConfigs();
         if (!CollectionUtils.isEmpty(enabledConfigs)) {
            Optional<EmbyInfoCacheManagerUtils.EmbyServerConfig> target = enabledConfigs.stream()
               .filter(config -> Objects.equals(config.id(), embyInfoId))
               .findFirst();
            if (target.isPresent()) {
               return target.get();
            }
         }
      }

      return this.embyInfoCacheManager.getRequiredConfig();
   }

   private String resolveServerName(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      if (config != null && config.id() != null) {
         EmbyInfo embyInfo = this.embyInfoService.getById(config.id());
         return embyInfo == null ? null : embyInfo.getServerName();
      } else {
         return null;
      }
   }

   @Generated
   public EmbyDeviceServiceImpl(
      final EmbyInfoCacheManagerUtils embyInfoCacheManager, final EmbyInfoService embyInfoService, final EmbyBlockKeywordService embyBlockKeywordService
   ) {
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.embyInfoService = embyInfoService;
      this.embyBlockKeywordService = embyBlockKeywordService;
   }
}
