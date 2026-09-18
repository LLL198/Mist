package com.una.embyhub.service;

import com.una.embyhub.model.dto.response.adminmenu.AdminMenuCatalogResponse;
import com.una.embyhub.model.dto.response.adminmenu.AdminMenuPermissionAssignmentResponse;
import com.una.embyhub.model.entity.EmbyUser;
import java.util.Collection;
import java.util.List;

public interface AdminMenuPermissionService {
   List<AdminMenuCatalogResponse> listCatalog();

   AdminMenuPermissionAssignmentResponse getAssignment(Long adminUserId);

   AdminMenuPermissionAssignmentResponse replaceAssignment(Long adminUserId, Collection<String> menuKeys);

   List<String> resolveMenuKeys(EmbyUser user);

   boolean hasAnyMenuPermission(Long adminUserId, Collection<String> menuKeys);

   void removeAssignments(Long adminUserId);

   void assertCurrentPrimaryAdmin();
}
