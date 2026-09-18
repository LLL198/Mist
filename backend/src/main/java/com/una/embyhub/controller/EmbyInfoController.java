package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoRequest;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoSave;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoUpdate;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoUserOptionsRequest;
import com.una.embyhub.model.dto.response.embyinfo.EmbyInfoEnabledResponse;
import com.una.embyhub.model.dto.response.embyinfo.EmbyInfoResponse;
import com.una.embyhub.model.dto.response.embyinfo.EmbyInfoUserOptionResponse;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.EmbyInfoService;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"embyInfo"})
public class EmbyInfoController {
   @Autowired
   private EmbyInfoService embyInfoService;

   @PostMapping({"select"})
   public Page<EmbyInfoResponse> select(@RequestBody MybatisPlusPage<EmbyInfoRequest> page) {
      boolean isAdmin = StpUtil.hasPermission("admin");
      EmbyUser user = (EmbyUser)StpUtil.getSession().get("user");
      boolean isDistributor = user != null && user.getIsDistributor() != null && user.getIsDistributor() == 1;
      if (!isAdmin && !isDistributor) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      } else {
         return this.embyInfoService.select(page);
      }
   }

   @PostMapping({"deleteByEmbyInfoId"})
   @SaCheckPermission({"admin"})
   public void deleteByUserId(@RequestParam List<Long> embyInfoIds) {
      this.embyInfoService.deleteByUserId(embyInfoIds);
   }

   @PostMapping({"insertEmbyInfo"})
   @SaCheckPermission({"admin"})
   public void insertEmbyInfo(@RequestBody EmbyInfoSave embyInfoSave) {
      this.embyInfoService.insertEmbyInfo(embyInfoSave);
   }

   @PostMapping({"updateEmbyInfo"})
   @SaCheckPermission({"admin"})
   public void updateEmbyInfo(@RequestBody EmbyInfoUpdate embyUserUpdate) {
      this.embyInfoService.updateEmbyInfo(embyUserUpdate);
   }

   @PostMapping({"enable"})
   @SaCheckPermission({"admin"})
   public void enableEmbyServer(@RequestParam Long embyInfoId) {
      this.embyInfoService.enableEmbyServer(embyInfoId);
   }

   @PostMapping({"updateEnabled"})
   @SaCheckPermission({"admin"})
   public void updateEmbyServerEnabled(@RequestParam Long embyInfoId, @RequestParam Integer enabled) {
      this.embyInfoService.updateEmbyServerEnabled(embyInfoId, enabled);
   }

   @PostMapping({"selectableUsers"})
   @SaCheckPermission({"admin"})
   public List<EmbyInfoUserOptionResponse> selectableUsers(@RequestBody EmbyInfoUserOptionsRequest request) {
      return this.embyInfoService.listSelectableUsers(request);
   }

   @PostMapping({"getEmbyInfoEnabled"})
   public EmbyInfoEnabledResponse getEmbyInfoEnabled() {
      EmbyInfo embyInfo = this.embyInfoService.getEmbyInfoEnabled();
      if (embyInfo == null) {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_CONFIGURED);
      } else {
         EmbyInfoEnabledResponse embyInfoEnabledResponse = BeanUtils.convert(embyInfo, EmbyInfoEnabledResponse.class);
         embyInfoEnabledResponse.setEmbyInfoId(embyInfo.getId());
         String urlStr = embyInfoEnabledResponse.getEmbyUrl();
         embyInfoEnabledResponse.setEmbyUrl(embyInfo.getEmbyUrl().replaceFirst("(?i)/emby/?$", ""));
         if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://")) {
            urlStr = "http://" + urlStr;
         }

         URL url = URLUtil.url(urlStr);
         String protocol = url.getProtocol();
         String host = url.getHost();
         int port = url.getPort();
         if (port == -1) {
            if ("https".equalsIgnoreCase(protocol)) {
               port = 443;
            } else {
               port = 80;
            }
         }

         embyInfoEnabledResponse.setHost(host);
         embyInfoEnabledResponse.setPort(port);
         embyInfoEnabledResponse.setProtocol(protocol);
         return embyInfoEnabledResponse;
      }
   }
}
