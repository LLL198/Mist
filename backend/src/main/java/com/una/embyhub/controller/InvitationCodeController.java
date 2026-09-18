package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.model.dto.request.invitation.InvitationCodeGenerateRequest;
import com.una.embyhub.model.dto.request.invitation.InvitationCodeQueryRequest;
import com.una.embyhub.model.dto.response.invitation.InvitationCodeResponse;
import com.una.embyhub.model.dto.response.invitation.InvitationCodeStatusResponse;
import com.una.embyhub.service.InvitationCodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"invitationCode"})
public class InvitationCodeController {
   @Autowired
   private InvitationCodeService invitationCodeService;

   @PostMapping({"generate"})
   @SaCheckPermission({"admin"})
   public List<InvitationCodeResponse> generate(@RequestBody @Validated InvitationCodeGenerateRequest request) {
      return this.invitationCodeService.generate(request);
   }

   @PostMapping({"list"})
   @SaCheckPermission({"admin"})
   public Page<InvitationCodeResponse> list(@RequestBody(required = false) InvitationCodeQueryRequest request) {
      return this.invitationCodeService.query(request);
   }

   @PostMapping({"delete"})
   @SaCheckPermission({"admin"})
   public void delete(@RequestParam Long id) {
      this.invitationCodeService.deleteById(id);
   }

   @PostMapping({"status"})
   @SaCheckPermission({"admin"})
   public InvitationCodeStatusResponse status() {
      return this.invitationCodeService.status();
   }
}
