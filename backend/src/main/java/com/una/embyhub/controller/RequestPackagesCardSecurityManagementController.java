package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementRequest;
import com.una.embyhub.model.dto.response.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementStatusResponse;
import com.una.embyhub.service.RequestPackagesCardSecurityManagementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"requestPackagesCardSecurityManagement"})
public class RequestPackagesCardSecurityManagementController {
   @Autowired
   private RequestPackagesCardSecurityManagementService requestPackagesCardSecurityManagementService;

   @PostMapping({"select"})
   public Page<RequestPackagesCardSecurityManagementResponse> select(@RequestBody MybatisPlusPage<RequestPackagesCardSecurityManagementRequest> page) {
      return this.requestPackagesCardSecurityManagementService.select(page);
   }

   @PostMapping({"verification"})
   public void verification(@RequestParam String cardPassword) {
      this.requestPackagesCardSecurityManagementService.verification(cardPassword);
   }

   @PostMapping({"add"})
   @SaCheckPermission({"admin"})
   public List<String> add(@RequestParam Integer count, @RequestParam Integer num) {
      return this.requestPackagesCardSecurityManagementService.add(count, num);
   }

   @PostMapping({"delete"})
   @SaCheckPermission({"admin"})
   public void delete(@RequestBody List<Long> idList) {
      this.requestPackagesCardSecurityManagementService.delete(idList);
   }

   @PostMapping({"status"})
   @SaCheckPermission({"admin"})
   public RequestPackagesCardSecurityManagementStatusResponse status() {
      return this.requestPackagesCardSecurityManagementService.status();
   }
}
