package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyiplocations.EmbyIpLocationsRequest;
import com.una.embyhub.model.dto.request.embyiplocations.ThresholdUserRequest;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationMapResponse;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationsResponse;
import com.una.embyhub.model.dto.response.embyiplocations.ThresholdUserResponse;
import com.una.embyhub.service.EmbyIpLocationsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"embyIpLocations"})
public class EmbyIpLocationsController {
   @Autowired
   private EmbyIpLocationsService embyIpLocationsService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<EmbyIpLocationsResponse> select(@RequestBody MybatisPlusPage<EmbyIpLocationsRequest> page) {
      return this.embyIpLocationsService.select(page);
   }

   @PostMapping({"thresholdUser"})
   @SaCheckPermission({"admin"})
   public Page<ThresholdUserResponse> thresholdUser(@RequestBody MybatisPlusPage<ThresholdUserRequest> page) {
      return this.embyIpLocationsService.thresholdUser(page);
   }

   @GetMapping({"mapSummary"})
   @SaCheckPermission({"admin"})
   public List<EmbyIpLocationMapResponse> mapSummary(Long embyInfoId) {
      return this.embyIpLocationsService.mapSummary(embyInfoId);
   }
}
