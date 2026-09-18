package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyuser.DisableUserRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserAdminUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserBatchExpirationUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserProfileUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserSave;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserTelegramGroupCheckRequest;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdate;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdateData;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserUpdatePassword;
import com.una.embyhub.model.dto.request.embyuser.InsertUserCardRequest;
import com.una.embyhub.model.dto.request.embyuser.LoginRequest;
import com.una.embyhub.model.dto.request.embyuser.PublicUserRegistrationRequest;
import com.una.embyhub.model.dto.request.embyuser.RegisteredUserSave;
import com.una.embyhub.model.dto.request.embyuser.SyncEmbyUserRequest;
import com.una.embyhub.model.dto.request.invitation.InvitationRegisterRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyServerUserStatsResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserDiffResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserTelegramGroupCheckResponse;
import com.una.embyhub.model.dto.response.embyuser.InsertUserResponse;
import com.una.embyhub.model.dto.response.embyuser.RegisteredUserResponse;
import com.una.embyhub.model.dto.response.embyuser.UserStatsResponse;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.TelegramGroupMembershipService;
import embyclient.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"embyUser"})
public class EmbyUserController {
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private TelegramGroupMembershipService telegramGroupMembershipService;

   @PostMapping({"login"})
   public EmbyUserCustomResponse login(@RequestBody LoginRequest loginRequest) {
      return this.embyUserService.login(loginRequest);
   }

   @PostMapping({"insertUser"})
   @SaCheckPermission({"admin"})
   public InsertUserResponse insertUser(@RequestBody @Validated EmbyUserSave embyUserSave) throws ApiException {
      return this.embyUserService.insertUser(embyUserSave);
   }

   @PostMapping({"insertUserCard"})
   public InsertUserResponse insertUserCard(@RequestBody @Validated InsertUserCardRequest insertUserCardRequest) throws ApiException {
      return this.embyUserService.insertUserCard(insertUserCardRequest);
   }

   @PostMapping({"updateUser"})
   @SaCheckPermission({"admin"})
   public void updateUser(@RequestBody @Validated EmbyUserUpdate embyUserUpdate) {
      this.embyUserService.updateUser(embyUserUpdate);
   }

   @PostMapping({"updatePassword"})
   public void updatePassword(@RequestBody @Validated EmbyUserUpdatePassword embyUserUpdatePassword) {
      this.embyUserService.updatePassword(embyUserUpdatePassword);
   }

   @PostMapping({"updateProfile"})
   public EmbyUserCustomResponse updateProfile(@RequestBody EmbyUserProfileUpdate embyUserProfileUpdate) {
      return this.embyUserService.updateProfile(embyUserProfileUpdate);
   }

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<EmbyUserResponse> select(@RequestBody MybatisPlusPage<EmbyUserRequest> page) {
      return this.embyUserService.select(page);
   }

   @PostMapping({"checkTelegramGroupMembership"})
   @SaCheckPermission({"admin"})
   public EmbyUserTelegramGroupCheckResponse checkTelegramGroupMembership(@RequestBody @Validated EmbyUserTelegramGroupCheckRequest request) {
      return this.telegramGroupMembershipService.checkUsers(request.getUserIds());
   }

   @PostMapping({"selectDistributor"})
   public Page<EmbyUserResponse> selectDistributor(@RequestBody MybatisPlusPage<EmbyUserRequest> page) {
      return this.embyUserService.selectDistributor(page);
   }

   @PostMapping({"disableUser"})
   @SaCheckPermission({"admin"})
   public void disableUser(@RequestBody DisableUserRequest disableUserRequest) {
      this.embyUserService.disableUser(disableUserRequest);
   }

   @PostMapping({"renewAdmin"})
   @SaCheckPermission({"admin"})
   public EmbyUserCustomResponse renewAdmin(@RequestParam Long userId, @RequestParam Integer day) {
      return this.embyUserService.renewAdmin(userId, day);
   }

   @PostMapping({"renewUser"})
   public EmbyUserCustomResponse renewUser(@RequestParam String cardPassword) {
      return this.embyUserService.renewUser(cardPassword);
   }

   @PostMapping({"logout"})
   public void logout() {
      this.embyUserService.logout();
   }

   @PostMapping({"getEmbyUserById"})
   public EmbyUserCustomResponse getEmbyUserById() {
      return this.embyUserService.getEmbyUserById();
   }

   @PostMapping({"deleteByUserId"})
   @SaCheckPermission({"admin"})
   public void deleteByUserId(@RequestParam List<Long> userIdList) throws ApiException {
      this.embyUserService.deleteByUserId(userIdList);
   }

   @PostMapping({"userStats"})
   @SaCheckPermission({"admin"})
   public UserStatsResponse userStats() {
      return this.embyUserService.userStats();
   }

   @PostMapping({"serverUserStats"})
   @SaCheckPermission({"admin"})
   public List<EmbyServerUserStatsResponse> serverUserStats() {
      return this.embyUserService.serverUserStats();
   }

   @PostMapping({"serverUserStatsDistributor"})
   public List<EmbyServerUserStatsResponse> serverUserStatsDistributor() {
      return this.embyUserService.serverUserStatsDistributor();
   }

   @PostMapping({"enableUser"})
   @SaCheckPermission({"admin"})
   public void enableUser(@RequestParam Long userId) {
      this.embyUserService.enableUser(userId);
   }

   @PostMapping({"extendExpiredUser"})
   @SaCheckPermission({"admin"})
   public int extendExpiredUser(
      @RequestParam(required = false) Long embyInfoId, @RequestParam(required = false) Integer expiredDayRange, @RequestParam Integer extensionDay
   ) {
      return this.embyUserService.extendExpiredUser(embyInfoId, expiredDayRange, extensionDay);
   }

   @PostMapping({"syncUserStatusConsistency"})
   @SaCheckPermission({"admin"})
   public int syncUserStatusConsistency() {
      return this.embyUserService.syncUserStatusConsistency();
   }

   @PostMapping({"updateUserData"})
   @SaCheckPermission({"admin"})
   public void updateUserData(@RequestBody EmbyUserUpdateData embyUserUpdateData) {
      this.embyUserService.updateUserData(embyUserUpdateData);
   }

   @PostMapping({"batchUpdateExpirationDate"})
   @SaCheckPermission({"admin"})
   public int batchUpdateExpirationDate(@RequestBody @Validated EmbyUserBatchExpirationUpdate request) {
      return this.embyUserService.batchUpdateExpirationDate(request);
   }

   @PostMapping({"setAdmin"})
   @SaCheckPermission({"admin"})
   public void setAdmin(@RequestBody @Validated EmbyUserAdminUpdate request) {
      this.embyUserService.updateUserAdmin(request.getUserId(), request.getIsAdmin());
   }

   @PostMapping({"updateTheme"})
   public void updateTheme(@RequestParam String theme) {
      this.embyUserService.updateTheme(theme);
   }

   @PostMapping({"uploadAvatar"})
   public ResponseEntity<byte[]> uploadAvatar(@RequestParam("file") MultipartFile file) {
      byte[] bytes = this.embyUserService.uploadAvatar(file);
      return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType())).body(bytes);
   }

   @PostMapping({"syncUserData"})
   @SaCheckPermission({"admin"})
   public String syncUserData(@RequestParam(required = false) String defaultPassword, @RequestParam(required = false) Long embyInfoId) {
      return this.embyUserService.syncUserData(defaultPassword, embyInfoId);
   }

   @PostMapping({"syncUserBetweenServers"})
   @SaCheckPermission({"admin"})
   public String syncUserBetweenServers(@RequestBody @Validated SyncEmbyUserRequest syncEmbyUserRequest) {
      return this.embyUserService.syncUserBetweenServers(syncEmbyUserRequest);
   }

   @PostMapping({"registeredUser"})
   public RegisteredUserResponse registeredUser(@RequestBody PublicUserRegistrationRequest requestBody, HttpServletRequest request) throws ApiException {
      RegisteredUserSave registeredUserSave = BeanUtils.convert(requestBody, RegisteredUserSave.class);
      return this.embyUserService.registeredUser(registeredUserSave, request.getRemoteAddr());
   }

   @PostMapping({"registeredByInvitation"})
   public RegisteredUserResponse registeredByInvitation(@RequestBody InvitationRegisterRequest invitationRegisterRequest, HttpServletRequest request) throws ApiException {
      return this.embyUserService.registeredByInvitation(invitationRegisterRequest, request.getRemoteAddr());
   }

   @PostMapping({"userExist"})
   public boolean userExist() {
      return this.embyUserService.userExist();
   }

   @PostMapping({"enableRegistration"})
   public boolean enableRegistration() {
      return this.embyUserService.enableRegistration();
   }

   @PostMapping({"embyUserNameExist"})
   public boolean embyUserNameExist(@RequestParam String embyUserName, @RequestParam(required = false) Long embyInfoId) {
      return this.embyUserService.embyUserNameExist(embyUserName, embyInfoId);
   }

   @PostMapping({"userDiff"})
   @SaCheckPermission({"admin"})
   public List<EmbyUserDiffResponse> userDiff(@RequestParam(required = false) Long embyInfoId) {
      return this.embyUserService.userDiff(embyInfoId);
   }

   @PostMapping({"exportUserDiffExcel"})
   @SaCheckPermission({"admin"})
   public ResponseEntity<byte[]> exportUserDiffExcel(@RequestParam(required = false) Long embyInfoId) {
      byte[] bytes = this.embyUserService.exportUserDiffExcel(embyInfoId);
      return ResponseEntity.ok()
         .header("Content-Disposition", new String[]{"attachment; filename*=UTF-8''%E7%94%A8%E6%88%B7%E5%B7%AE%E5%BC%82%E5%AF%B9%E6%AF%94.xlsx"})
         .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
         .body(bytes);
   }
}
