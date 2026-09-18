package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.una.embyhub.model.dto.logintransition.LoginTransitionDtos;
import com.una.embyhub.service.LoginTransitionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"loginTransition"})
public class LoginTransitionController {
   private final LoginTransitionService loginTransitionService;

   public LoginTransitionController(LoginTransitionService loginTransitionService) {
      this.loginTransitionService = loginTransitionService;
   }

   @GetMapping({"settings"})
   @SaCheckPermission({"admin"})
   public LoginTransitionDtos.SettingsResponse settings() {
      return this.loginTransitionService.getSettings();
   }

   @PutMapping({"settings"})
   @SaCheckPermission({"admin"})
   public LoginTransitionDtos.SettingsResponse update(@RequestBody @Validated LoginTransitionDtos.UpdateRequest request) {
      return this.loginTransitionService.updateSettings(request);
   }

   @GetMapping({"current"})
   @SaCheckLogin
   public LoginTransitionDtos.CurrentResponse current() {
      return this.loginTransitionService.resolveForUser(StpUtil.getLoginIdAsLong());
   }
}
