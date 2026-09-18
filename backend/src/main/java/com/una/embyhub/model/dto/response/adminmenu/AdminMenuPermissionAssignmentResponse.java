package com.una.embyhub.model.dto.response.adminmenu;

import java.util.List;

public record AdminMenuPermissionAssignmentResponse(Long adminUserId, String adminUserName, List<String> menuKeys) {
}
