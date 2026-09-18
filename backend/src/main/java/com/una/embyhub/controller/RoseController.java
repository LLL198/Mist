package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.model.dto.request.rose.RoseAdminUnbindRequest;
import com.una.embyhub.model.dto.request.rose.RoseBindRequest;
import com.una.embyhub.model.dto.request.rose.RoseLibraryBrowseRequest;
import com.una.embyhub.model.dto.request.rose.RoseQrStartRequest;
import com.una.embyhub.model.dto.response.rose.RoseBindingResponse;
import com.una.embyhub.model.dto.response.rose.RoseProfileResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.RoseUserBindingService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"rose"})
public class RoseController {
   @Autowired
   private RoseUserBindingService roseUserBindingService;
   @Autowired
   private EmbyUserService embyUserService;

   @GetMapping({"profile"})
   public RoseProfileResponse profile() {
      return this.roseUserBindingService.profile(this.currentUser(), true);
   }

   @PostMapping({"profile"})
   public RoseProfileResponse profilePost() {
      return this.profile();
   }

   @PostMapping({"qr/start"})
   public JSONObject startQr(@RequestBody RoseQrStartRequest request) {
      return this.roseUserBindingService.startQr(this.currentUser(), request);
   }

   @GetMapping({"qr/{sessionId}"})
   public JSONObject qrStatus(@PathVariable String sessionId) {
      return this.roseUserBindingService.qrStatus(this.currentUser(), sessionId);
   }

   @GetMapping({"qr/{sessionId}/image"})
   public ResponseEntity<byte[]> qrImage(@PathVariable String sessionId) {
      byte[] bytes = this.roseUserBindingService.qrImage(this.currentUser(), sessionId);
      return ResponseEntity.ok().cacheControl(CacheControl.maxAge(300L, TimeUnit.SECONDS).cachePrivate()).contentType(MediaType.IMAGE_PNG).body(bytes);
   }

   @PostMapping({"bind"})
   public RoseProfileResponse bind(@RequestBody RoseBindRequest request) {
      return this.roseUserBindingService.bind(this.currentUser(), request);
   }

   @PostMapping({"library-source-root/browse"})
   public JSONObject browseLibrarySourceRoot(@RequestBody RoseLibraryBrowseRequest request) {
      return this.roseUserBindingService.browseLibrarySourceRoot(this.currentUser(), request);
   }

   @PostMapping({"library-source-root/resolve"})
   public JSONObject resolveLibrarySourceRoot(@RequestBody RoseLibraryBrowseRequest request) {
      return this.roseUserBindingService.resolveLibrarySourceRoot(this.currentUser(), request);
   }

   @GetMapping({"admin/users/{userId}/binding"})
   @SaCheckPermission({"admin"})
   public RoseBindingResponse adminBinding(@PathVariable Long userId) {
      this.embyUserService.assertUserCanBeViewed(userId);
      return this.roseUserBindingService.adminBinding(userId);
   }

   @PostMapping({"admin/users/{userId}/unbind"})
   @SaCheckPermission({"admin"})
   public RoseBindingResponse adminUnbind(@PathVariable Long userId, @RequestBody(required = false) RoseAdminUnbindRequest request) {
      this.embyUserService.assertUserCanBeManaged(userId);
      EmbyUser user = this.embyUserService.getById(userId);
      if (user == null) {
         throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
      } else {
         return this.roseUserBindingService.adminUnbind(user, request == null ? "" : request.getAdminPassword());
      }
   }

   private EmbyUser currentUser() {
      Object userObj = StpUtil.getSession().get("user");
      if (userObj instanceof EmbyUser) {
         return (EmbyUser)userObj;
      } else {
         Long userId = StpUtil.getLoginIdAsLong();
         EmbyUser user = this.embyUserService.getById(userId);
         if (user == null) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else {
            StpUtil.getSession().set("user", user);
            return user;
         }
      }
   }
}
