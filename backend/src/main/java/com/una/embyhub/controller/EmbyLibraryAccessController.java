package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessGlobalUpdateRequest;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessUserUpdateRequest;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessUsersUpdateRequest;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessOverviewResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUpdateResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUserOptionResponse;
import com.una.embyhub.service.EmbyLibraryAccessService;
import com.una.embyhub.service.EmbyUserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Generated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/emby/library-access"})
@SaCheckPermission({"admin"})
public class EmbyLibraryAccessController {
   private final EmbyLibraryAccessService embyLibraryAccessService;
   private final EmbyUserService embyUserService;

   @GetMapping({"/overview"})
   public EmbyLibraryAccessOverviewResponse overview(@RequestParam Long embyInfoId, @RequestParam(required = false) Long userId) {
      if (userId != null) {
         this.embyUserService.assertUserCanBeViewed(userId);
      }

      return this.embyLibraryAccessService.overview(embyInfoId, userId);
   }

   @GetMapping({"/users"})
   public List<EmbyLibraryAccessUserOptionResponse> users(@RequestParam Long embyInfoId) {
      return this.embyLibraryAccessService.listUsers(embyInfoId);
   }

   @PostMapping({"/global"})
   public EmbyLibraryAccessUpdateResponse updateGlobal(@RequestBody @Valid EmbyLibraryAccessGlobalUpdateRequest request) {
      return this.embyLibraryAccessService.updateGlobal(request);
   }

   @PostMapping({"/user"})
   public EmbyLibraryAccessUpdateResponse updateUser(@RequestBody @Valid EmbyLibraryAccessUserUpdateRequest request) {
      this.embyUserService.assertUserCanBeManaged(request.getUserId());
      return this.embyLibraryAccessService.updateUser(request);
   }

   @PostMapping({"/users/batch"})
   public EmbyLibraryAccessUpdateResponse updateUsers(@RequestBody @Valid EmbyLibraryAccessUsersUpdateRequest request) {
      request.getUserIds().stream().distinct().forEach(this.embyUserService::assertUserCanBeManaged);
      return this.embyLibraryAccessService.updateUsers(request);
   }

   @Generated
   public EmbyLibraryAccessController(final EmbyLibraryAccessService embyLibraryAccessService, final EmbyUserService embyUserService) {
      this.embyLibraryAccessService = embyLibraryAccessService;
      this.embyUserService = embyUserService;
   }
}
