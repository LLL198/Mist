package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessGlobalUpdateRequest;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessUserUpdateRequest;
import com.una.embyhub.model.dto.request.embylibraryaccess.EmbyLibraryAccessUsersUpdateRequest;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessOverviewResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUpdateResponse;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryAccessUserOptionResponse;
import com.una.embyhub.model.entity.EmbyLibraryAccessRule;
import java.util.List;

public interface EmbyLibraryAccessService extends IService<EmbyLibraryAccessRule> {
   EmbyLibraryAccessOverviewResponse overview(Long embyInfoId, Long userId);

   EmbyLibraryAccessOverviewResponse overviewFromTelegram(Long embyInfoId, Long userId, long telegramOperatorId);

   List<EmbyLibraryAccessUserOptionResponse> listUsers(Long embyInfoId);

   List<EmbyLibraryAccessUserOptionResponse> listUsersFromTelegram(Long embyInfoId, long telegramOperatorId);

   EmbyLibraryAccessUpdateResponse updateGlobal(EmbyLibraryAccessGlobalUpdateRequest request);

   EmbyLibraryAccessUpdateResponse updateGlobalFromTelegram(EmbyLibraryAccessGlobalUpdateRequest request, long telegramOperatorId, String actorName);

   EmbyLibraryAccessUpdateResponse updateUser(EmbyLibraryAccessUserUpdateRequest request);

   EmbyLibraryAccessUpdateResponse updateUsers(EmbyLibraryAccessUsersUpdateRequest request);

   EmbyLibraryAccessUpdateResponse updateUserFromTelegram(EmbyLibraryAccessUserUpdateRequest request, long telegramOperatorId, String actorName);

   void applyGlobalRuleToRemoteUser(Long embyInfoId, String embyUserId);
}
