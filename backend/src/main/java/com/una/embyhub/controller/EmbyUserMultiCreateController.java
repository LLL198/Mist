package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserMultiCreateRequest;
import com.una.embyhub.model.dto.response.ResponseData;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserMultiCreateResponse;
import com.una.embyhub.service.EmbyUserMultiCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/emby/user"})
public class EmbyUserMultiCreateController {
   @Autowired
   private EmbyUserMultiCreateService embyUserMultiCreateService;

   @PostMapping({"/multi_create"})
   @SaCheckPermission({"admin"})
   public ResponseData<EmbyUserMultiCreateResponse> multiCreate(@RequestBody @Validated EmbyUserMultiCreateRequest request) {
      EmbyUserMultiCreateResponse response = this.embyUserMultiCreateService.createMultiServerUser(request);
      return ResponseData.success(response);
   }
}
