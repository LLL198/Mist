package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embydevice.EmbyDeviceBlockRequest;
import com.una.embyhub.model.dto.request.embydevice.EmbyDeviceRequest;
import com.una.embyhub.model.dto.response.embydevice.EmbyDeviceResponse;
import com.una.embyhub.service.EmbyDeviceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"embyDevice"})
public class EmbyDeviceController {
   @Autowired
   private EmbyDeviceService embyDeviceService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<EmbyDeviceResponse> select(@RequestBody MybatisPlusPage<EmbyDeviceRequest> page) {
      return this.embyDeviceService.select(page);
   }

   @PostMapping({"blockDevice"})
   @SaCheckPermission({"admin"})
   public void blockDevice(@RequestBody @Validated EmbyDeviceBlockRequest request) {
      this.embyDeviceService.blockDevice(request);
   }

   @GetMapping({"blockKeywords"})
   public List<String> blockKeywords() {
      return this.embyDeviceService.getDefaultBlockKeywords();
   }
}
