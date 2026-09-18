package com.una.embyhub.controller;
import com.una.embyhub.model.dto.request.adminmenu.AdminMenuPermissionUpdateRequest;
import com.una.embyhub.model.dto.response.adminmenu.AdminMenuCatalogResponse;
import com.una.embyhub.model.dto.response.adminmenu.AdminMenuPermissionAssignmentResponse;
import com.una.embyhub.service.AdminMenuPermissionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"adminMenuPermission"})
public class AdminMenuPermissionController {
   private final AdminMenuPermissionService adminMenuPermissionService;

   public AdminMenuPermissionController(AdminMenuPermissionService adminMenuPermissionService) {
      this.adminMenuPermissionService = adminMenuPermissionService;
   }

   @GetMapping({"catalog"})
   public List<AdminMenuCatalogResponse> catalog() {
      this.adminMenuPermissionService.assertCurrentPrimaryAdmin();
      return this.adminMenuPermissionService.listCatalog();
   }

   @GetMapping({"{adminUserId}"})
   public AdminMenuPermissionAssignmentResponse assignment(@PathVariable Long adminUserId) {
      return this.adminMenuPermissionService.getAssignment(adminUserId);
   }

   @PutMapping({"{adminUserId}"})
   public AdminMenuPermissionAssignmentResponse update(@PathVariable Long adminUserId, @RequestBody @Valid AdminMenuPermissionUpdateRequest request) {
      return this.adminMenuPermissionService.replaceAssignment(adminUserId, request.getMenuKeys());
   }
}
