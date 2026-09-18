package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.request.requestpackages.RequestPackagesSave;
import com.una.embyhub.model.dto.request.requestpackages.RequestPackagesUpdate;
import com.una.embyhub.model.dto.response.requestpackages.RequestPackagesResponse;
import com.una.embyhub.service.RequestPackagesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"requestPackages"})
public class RequestPackagesController {
   @Autowired
   private RequestPackagesService requestPackagesService;

   @PostMapping({"select"})
   public List<RequestPackagesResponse> list() {
      return this.requestPackagesService.select();
   }

   @PostMapping({"insertRequestPackages"})
   @SaCheckPermission({"admin"})
   public void insertRequestPackages(@RequestBody @Validated RequestPackagesSave requestPackagesSave) {
      this.requestPackagesService.insertRequestPackages(requestPackagesSave);
   }

   @PostMapping({"updateRequestPackages"})
   @SaCheckPermission({"admin"})
   public void updateRequestPackages(@RequestBody @Validated RequestPackagesUpdate requestPackagesUpdate) {
      this.requestPackagesService.updateRequestPackages(requestPackagesUpdate);
   }

   @PostMapping({"deleteByRequestPackagesId"})
   @SaCheckPermission({"admin"})
   public void deleteByRequestPackagesId(@RequestParam Long requestPackagesId) {
      this.requestPackagesService.deleteByRequestPackagesId(requestPackagesId);
   }
}
