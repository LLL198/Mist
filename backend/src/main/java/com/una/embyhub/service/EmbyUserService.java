package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyuser.DisableUserRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserBatchExpirationUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserProfileUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserSave;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdateData;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdatePassword;
import com.una.embyhub.model.dto.request.embyuser.InsertUserCardRequest;
import com.una.embyhub.model.dto.request.embyuser.LoginRequest;
import com.una.embyhub.model.dto.request.embyuser.RegisteredUserSave;
import com.una.embyhub.model.dto.request.embyuser.SyncEmbyUserRequest;
import com.una.embyhub.model.dto.request.invitation.InvitationRegisterRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyServerUserStatsResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserDiffResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.embyuser.InsertUserResponse;
import com.una.embyhub.model.dto.response.embyuser.RegisteredUserResponse;
import com.una.embyhub.model.dto.response.embyuser.UserStatsResponse;
import com.una.embyhub.model.entity.EmbyUser;
import embyclient.ApiException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface EmbyUserService extends IService<EmbyUser> {
   EmbyUserCustomResponse login(LoginRequest loginRequest);

   List<EmbyUserCustomResponse.ServerOption> listAccessibleServers(EmbyUser embyUser);

   List<Long> listAccessibleEmbyInfoIds(EmbyUser embyUser);

   InsertUserResponse insertUser(EmbyUserSave embyUserSave) throws ApiException;

   InsertUserResponse insertUserCard(InsertUserCardRequest insertUserCardRequest) throws ApiException;

   InsertUserResponse insertUserCardByTelegram(InsertUserCardRequest insertUserCardRequest, String telegramChannelDetail) throws ApiException;

   void updateUser(EmbyUserUpdate embyUserUpdate);

   void updatePassword(EmbyUserUpdatePassword embyUserUpdatePassword);

   EmbyUserCustomResponse updateProfile(EmbyUserProfileUpdate embyUserProfileUpdate);

   Page<EmbyUserResponse> select(MybatisPlusPage<EmbyUserRequest> page);

   Page<EmbyUserResponse> selectDistributor(MybatisPlusPage<EmbyUserRequest> page);

   void disableUser(DisableUserRequest disableUserRequest);

   EmbyUserCustomResponse renewAdmin(Long userId, Integer day);

   EmbyUserCustomResponse renewByPoints(Long userId, Integer day);

   void validatePointsRedeemCreate(String userName, String password, Long serverId);

   EmbyUserCustomResponse renewUser(String cardPassword);

   EmbyUserCustomResponse renewUserByTelegramCard(String cardPassword, Long userId, String telegramChannelDetail);

   void logout();

   EmbyUserCustomResponse getEmbyUserById();

   void deleteByUserId(List<Long> userIdList) throws ApiException;

   UserStatsResponse userStats();

   List<EmbyServerUserStatsResponse> serverUserStats();

   List<EmbyServerUserStatsResponse> serverUserStatsDistributor();

   void enableUser(Long userId);

   void updateUserData(EmbyUserUpdateData embyUserUpdateData);

   int batchUpdateExpirationDate(EmbyUserBatchExpirationUpdate request);

   void updateUserAdmin(Long userId, Integer isAdmin);

   void updateUserAdminByBot(Long userId, Integer isAdmin, boolean owner);

   void assertCurrentUserCanManageAdministrators();

   void assertUserCanBeManaged(Long userId);

   void assertUserCanBeViewed(Long userId);

   void assertUserCanBeEdited(Long userId);

   void assertEmbyUserCanBeManaged(String embyUserId);

   void updateUserDataByBot(EmbyUserUpdateData embyUserUpdateData, boolean owner);

   void enableUserByBot(Long userId, boolean owner);

   void disableUserByBot(Long userId, boolean owner);

   void deleteUserByBot(Long userId, boolean owner) throws ApiException;

   void updateUserWhitelistByBot(Long userId, boolean whitelist, Integer ordinaryDays, boolean owner);

   void resetPasswordByBot(Long userId, String newPassword, boolean owner);

   String syncUserData(String defaultPassword, Long embyInfoId);

   String syncUserBetweenServers(SyncEmbyUserRequest syncEmbyUserRequest);

   RegisteredUserResponse registeredUser(RegisteredUserSave registeredUserSave, String clientIp) throws ApiException;

   RegisteredUserResponse registeredUserByTelegram(RegisteredUserSave registeredUserSave, String registerChannelDetail) throws ApiException;

   RegisteredUserResponse registeredByInvitation(InvitationRegisterRequest invitationRegisterRequest, String clientIp) throws ApiException;

   boolean userExist();

   boolean enableRegistration();

   boolean embyUserNameExist(String embyUserName, Long embyInfoId);

   byte[] uploadAvatar(MultipartFile file);

   int extendExpiredUser(Long embyInfoId, Integer expiredDayRange, Integer extensionDay);

   int syncUserStatusConsistency();

   byte[] exportUserDiffExcel(Long embyInfoId);

   List<EmbyUserDiffResponse> userDiff(Long embyInfoId);

   void setDistributor(Long userId, Integer isDistributor);

   void updateTheme(String theme);
}
