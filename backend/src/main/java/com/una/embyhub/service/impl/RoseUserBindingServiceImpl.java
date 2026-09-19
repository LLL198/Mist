package com.una.embyhub.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.mapper.RoseUserBindingMapper;
import com.una.embyhub.model.dto.request.rose.RoseBindRequest;
import com.una.embyhub.model.dto.request.rose.RoseLibraryBindingRequest;
import com.una.embyhub.model.dto.request.rose.RoseLibraryBrowseRequest;
import com.una.embyhub.model.dto.request.rose.RoseQrStartRequest;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.rose.RoseBindingResponse;
import com.una.embyhub.model.dto.response.rose.RoseLibraryBindingResponse;
import com.una.embyhub.model.dto.response.rose.RoseProfileResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.RoseUserBinding;
import com.una.embyhub.service.RoseUserBindingService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class RoseUserBindingServiceImpl extends ServiceImpl<RoseUserBindingMapper, RoseUserBinding> implements RoseUserBindingService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(RoseUserBindingServiceImpl.class);
   private static final String STATUS_UNBOUND = "UNBOUND";
   private static final String STATUS_PENDING = "PENDING";
   private static final String STATUS_BOUND = "BOUND";
   private static final String STATUS_ERROR = "ERROR";
   private static final List<String> DEFAULT_APPS = List.of(
      "web",
      "desktop",
      "chrome",
      "ios",
      "115ios",
      "android",
      "115android",
      "ipad",
      "115ipad",
      "tv",
      "apple_tv",
      "qandroid",
      "qios",
      "qipad",
      "os_windows",
      "os_mac",
      "os_linux",
      "windows",
      "mac",
      "linux",
      "wechatmini",
      "alipaymini",
      "harmony"
   );
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private NotifyUtils notifyUtils;

   @Override
   public RoseProfileResponse profile(EmbyUser user, boolean syncRose) {
      RoseProfileResponse response = new RoseProfileResponse();
      response.setEnabled(this.isRoseEnabled());
      response.setBaseUrlConfigured(StringUtils.hasText(this.getRoseBaseUrl()));
      response.setSupportedApps(DEFAULT_APPS);
      RoseUserBinding binding = this.getByUserId(user.getId());
      response.setBinding(this.toResponse(binding));
      if (!Boolean.TRUE.equals(response.getEnabled()) || !Boolean.TRUE.equals(response.getBaseUrlConfigured())) {
         response.setMessage("Rose绑定未开启或未配置接口地址");
         return response;
      } else if (binding != null && StringUtils.hasText(binding.getRoseAuthCookieText())) {
         try {
            JSONObject roseResponse = this.getUserJson("/api/user/profile", binding.getRoseAuthCookieText());
            JSONObject sanitized = this.sanitizeJson(roseResponse);
            response.setRoseProfile(sanitized.get("profile"));
            response.setMeta(sanitized.get("meta"));
            List<String> apps = this.resolveSupportedApps(sanitized.getJSONObject("meta"));
            if (!apps.isEmpty()) {
               response.setSupportedApps(apps);
            }

            this.saveProfileSnapshot(user, roseResponse);
            response.setBinding(this.toResponse(this.getByUserId(user.getId())));
         } catch (Exception var8) {
            response.setMessage(var8.getMessage());
         }

         return response;
      } else {
         return response;
      }
   }

   @Override
   public JSONObject startQr(EmbyUser user, RoseQrStartRequest request) {
      this.assertRoseReady();
      if (request == null) {
         throw new BizException("生成二维码参数不能为空");
      } else {
         RoseUserBinding binding = this.getByUserId(user.getId());
         String roseAuthCookie = this.resolveRoseAuthCookie(user, request.getEmbyPassword());
         JSONObject payload = new JSONObject();
         payload.put("app", this.normalizeApp(request.getApp()));
         JSONObject response = this.postUserJson("/api/user/qr/start", payload, roseAuthCookie);
         JSONObject session = response.getJSONObject("session");
         if (binding == null) {
            binding = this.getOrCreateBinding(user);
         }

         binding.setDeviceApp(payload.getString("app"));
         binding.setRoseAuthCookieText(roseAuthCookie);
         binding.setBindingStatus("PENDING");
         binding.setLastError(null);
         if (session != null) {
            binding.setQrSessionId(session.getString("id"));
            binding.setQrStatus(session.getString("status"));
            binding.setQrScanUrl(session.getString("scan_url"));
         }

         this.saveBinding(binding);
         return this.sanitizeJson(response);
      }
   }

   @Override
   public JSONObject qrStatus(EmbyUser user, String sessionId) {
      this.assertRoseReady();
      String normalizedSessionId = this.requireText(sessionId, "二维码会话不能为空");
      RoseUserBinding binding = this.getByUserId(user.getId());
      JSONObject response = this.getUserJson("/api/user/qr/" + this.encodePath(normalizedSessionId), this.requireRoseAuthCookie(binding));
      JSONObject session = response.getJSONObject("session");
      if (session != null) {
         this.updateBindingFromQrSession(user, session);
      }

      return this.sanitizeJson(response);
   }

   @Override
   public byte[] qrImage(EmbyUser user, String sessionId) {
      this.assertRoseReady();
      this.requireText(sessionId, "二维码会话不能为空");
      RoseUserBinding binding = this.getByUserId(user.getId());
      return this.getUserBytes("/api/user/qr/" + this.encodePath(sessionId) + "/image", this.requireRoseAuthCookie(binding));
   }

   @Override
   public RoseProfileResponse bind(EmbyUser user, RoseBindRequest request) {
      this.assertRoseReady();
      if (request == null) {
         throw new BizException("绑定参数不能为空");
      } else {
         RoseUserBinding binding = this.getOrCreateBinding(user);
         String cookie = StringUtils.hasText(request.getCookie()) ? request.getCookie().trim() : binding.getCookieText();
         boolean hasCookie = StringUtils.hasText(cookie);
         if (!hasCookie && !StringUtils.hasText(binding.getRoseAccountId())) {
            throw new BizException("请先扫码或输入 Rose Cookie");
         } else {
            String roseAuthCookie = this.resolveRoseAuthCookie(user, request.getEmbyPassword());
            JSONObject payload = new JSONObject();
            payload.put("app", this.normalizeApp(request.getApp(), binding.getDeviceApp()));
            if (hasCookie) {
               payload.put("cookie", cookie);
            } else {
               payload.put("account_id", binding.getRoseAccountId());
            }

            if (StringUtils.hasText(request.getTargetRoot())) {
               payload.put("target_root", request.getTargetRoot().trim());
            }

            JSONArray roseLibrariesPayload = this.buildLibrariesPayload(request.getLibraries(), false);
            JSONArray localLibrariesPayload = this.buildLibrariesPayload(request.getLibraries(), true);
            if (!roseLibrariesPayload.isEmpty()) {
               payload.put("libraries", roseLibrariesPayload);
            }

            JSONObject response = this.postUserJson("/api/user/cookie", payload, roseAuthCookie);
            JSONObject accountSummary = response.getJSONObject("account_summary");
            JSONObject profile = response.getJSONObject("profile");
            if (hasCookie) {
               binding.setCookieText(cookie);
               binding.setCookieTextMasked(this.maskCookie(cookie));
               binding.setCookieUpdatedAt(new Date());
            }

            binding.setDeviceApp(payload.getString("app"));
            binding.setRoseAuthCookieText(roseAuthCookie);
            binding.setTargetRoot(request.getTargetRoot());
            binding.setLibrariesJson(localLibrariesPayload.isEmpty() ? null : localLibrariesPayload.toJSONString());
            binding.setBindingStatus("BOUND");
            binding.setLastError(null);
            binding.setBoundAt(new Date());
            binding.setLastSyncAt(new Date());
            binding.setRoseProfileJson(profile == null ? null : profile.toJSONString());
            binding.setAccountSummaryJson(accountSummary == null ? null : accountSummary.toJSONString());
            this.fillAccountSummary(binding, accountSummary);
            this.fillManagedAccount(binding, profile);
            this.saveBinding(binding);
            return this.profile(user, true);
         }
      }
   }

   @Override
   public RoseBindingResponse adminUnbind(EmbyUser user, String adminPassword) {
      this.assertRoseReady();
      if (user == null) {
         throw new BizException("用户不能为空");
      } else {
         RoseUserBinding binding = this.getByUserId(user.getId());
         boolean shouldCallRose = binding == null || "BOUND".equalsIgnoreCase(binding.getBindingStatus()) || StringUtils.hasText(binding.getRoseAccountId());
         if (shouldCallRose) {
            String adminCookie = this.loginRoseAdmin(adminPassword);

            try {
               this.postJson("/api/admin/emby-users/unbind", this.identityPayload(user), adminCookie);
            } catch (Exception var7) {
               if (!this.isRoseMissingBindingError(var7)) {
                  if (binding != null) {
                     binding.setLastError(var7.getMessage());
                     binding.setBindingStatus("ERROR");
                     this.saveBinding(binding);
                  }

                  throw var7;
               }
            }
         }

         if (binding == null) {
            binding = this.getOrCreateBinding(user);
         }

         this.clearLocalBinding(binding);
         return this.toResponse(binding);
      }
   }

   @Override
   public JSONObject browseLibrarySourceRoot(EmbyUser user, RoseLibraryBrowseRequest request) {
      this.assertRoseReady();
      JSONObject payload = this.buildLibraryBrowsePayload(user, request);
      RoseUserBinding binding = this.getByUserId(user.getId());
      JSONObject response = this.postUserJson("/api/user/library-source-root/browse", payload, this.resolveBrowseRoseAuthCookie(user, binding, request));
      return this.sanitizeJson(response);
   }

   @Override
   public JSONObject resolveLibrarySourceRoot(EmbyUser user, RoseLibraryBrowseRequest request) {
      this.assertRoseReady();
      JSONObject payload = this.buildLibraryBrowsePayload(user, request);
      if (request != null && StringUtils.hasText(request.getSourceRoot())) {
         payload.put("source_root", request.getSourceRoot().trim());
      }

      RoseUserBinding binding = this.getByUserId(user.getId());
      JSONObject response = this.postUserJson("/api/user/library-source-root/resolve", payload, this.resolveBrowseRoseAuthCookie(user, binding, request));
      return this.sanitizeJson(response);
   }

   @Override
   public RoseBindingResponse adminBinding(Long userId) {
      if (userId == null) {
         throw new BizException("用户id不能为空");
      } else {
         return this.toResponse(this.getByUserId(userId));
      }
   }

   @Async
   @Override
   public void unbindExpiredUserIfBoundAsync(EmbyUser user) {
      if (user != null && user.getId() != null) {
         if (this.isRoseEnabled()) {
            RoseUserBinding binding = this.getByUserId(user.getId());
            if (this.hasActiveRoseBinding(binding)) {
               if (!StringUtils.hasText(this.getRoseBaseUrl())) {
                  String message = "Rose接口地址未配置，跳过到期删除自动解绑";
                  this.markBindingUnbindError(binding, message);
                  this.notifyExpiredUnbindFailure(user, binding, message);
                  log.warn("Rose到期删除自动解绑跳过，接口地址未配置：userId={}, embyUserName={}", user.getId(), user.getEmbyUserName());
               } else {
                  String adminPassword = this.getConfiguredRoseAdminPassword();
                  if (!StringUtils.hasText(adminPassword)) {
                     String message = "Rose管理员密码未配置，跳过到期删除自动解绑";
                     this.markBindingUnbindError(binding, message);
                     this.notifyExpiredUnbindFailure(user, binding, message);
                     log.warn("Rose到期删除自动解绑跳过，管理员密码未配置：userId={}, embyUserName={}", user.getId(), user.getEmbyUserName());
                  } else {
                     try {
                        String adminCookie = this.loginRoseAdmin(adminPassword);
                        this.postJson("/api/admin/emby-users/unbind", this.identityPayload(user), adminCookie);
                        this.clearLocalBinding(binding);
                        log.info("Rose到期删除自动解绑成功：userId={}, embyUserName={}", user.getId(), user.getEmbyUserName());
                     } catch (Exception var5) {
                        if (this.isRoseMissingBindingError(var5)) {
                           this.clearLocalBinding(binding);
                           log.info("Rose到期删除自动解绑完成，Rose侧未找到绑定：userId={}, embyUserName={}", user.getId(), user.getEmbyUserName());
                           return;
                        }

                        this.markBindingUnbindError(binding, var5.getMessage());
                        this.notifyExpiredUnbindFailure(user, binding, var5.getMessage());
                        log.warn("Rose到期删除自动解绑失败，不影响用户删除主流程：userId={}, embyUserName={}, error={}", user.getId(), user.getEmbyUserName(), var5.getMessage());
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void attachBindings(List<EmbyUserResponse> users) {
      if (!CollectionUtils.isEmpty(users)) {
         List<Long> userIds = users.stream().map(EmbyUserResponse::getId).filter(Objects::nonNull).toList();
         if (!userIds.isEmpty()) {
            List<RoseUserBinding> bindings = this.lambdaQuery().in(RoseUserBinding::getUserId, userIds).list();
            Map<Long, RoseBindingResponse> map = new HashMap<>();

            for (RoseUserBinding binding : bindings) {
               map.put(binding.getUserId(), this.toResponse(binding));
            }

            users.forEach(user -> user.setRoseBinding(map.get(user.getId())));
         }
      }
   }

   private JSONObject buildLibraryBrowsePayload(EmbyUser user, RoseLibraryBrowseRequest request) {
      RoseUserBinding binding = this.getByUserId(user.getId());
      String requestCookie = this.roseBrowseRequestCookie(request);
      if (binding == null && !StringUtils.hasText(requestCookie)) {
         throw new BizException("请先完成 Rose 扫码绑定或输入 Cookie");
      } else if (StringUtils.hasText(requestCookie)
         || binding != null && (StringUtils.hasText(binding.getCookieText()) || StringUtils.hasText(binding.getRoseAccountId()))) {
         JSONObject payload = new JSONObject();
         if (StringUtils.hasText(requestCookie)) {
            payload.put("cookie", requestCookie);
         } else if (StringUtils.hasText(binding.getCookieText())) {
            payload.put("cookie", binding.getCookieText());
         } else {
            payload.put("account_id", binding.getRoseAccountId());
         }

         payload.put("app", this.normalizeApp(request == null ? "" : request.getApp(), binding == null ? "" : binding.getDeviceApp()));
         if (request != null && StringUtils.hasText(request.getCid())) {
            payload.put("cid", request.getCid().trim());
         } else {
            payload.put("cid", Integer.valueOf(0));
         }

         if (request != null && StringUtils.hasText(request.getAccountId())) {
            payload.put("account_id", request.getAccountId().trim());
         } else if (binding != null && StringUtils.hasText(binding.getRoseAccountId())) {
            payload.put("account_id", binding.getRoseAccountId());
         }

         if (request != null && StringUtils.hasText(request.getSourceAccount())) {
            payload.put("source_account", request.getSourceAccount().trim());
         }

         return payload;
      } else {
         throw new BizException("请先完成 Rose 扫码绑定");
      }
   }

   private String resolveBrowseRoseAuthCookie(EmbyUser user, RoseUserBinding binding, RoseLibraryBrowseRequest request) {
      String embyPassword = request == null ? "" : request.getEmbyPassword();
      return StringUtils.hasText(embyPassword) ? this.loginRoseUser(user, embyPassword) : this.requireRoseAuthCookie(binding);
   }

   private String roseBrowseRequestCookie(RoseLibraryBrowseRequest request) {
      return request == null ? "" : this.firstText(request.getCookie(), request.getCookieText(), request.getCookies());
   }

   private RoseUserBinding getByUserId(Long userId) {
      return userId == null ? null : this.lambdaQuery().eq(RoseUserBinding::getUserId, userId).one();
   }

   private RoseUserBinding getOrCreateBinding(EmbyUser user) {
      RoseUserBinding binding = this.getByUserId(user.getId());
      if (binding == null) {
         binding = new RoseUserBinding();
         binding.setUserId(user.getId());
         binding.setBindingStatus("UNBOUND");
      }

      binding.setEmbyUserId(user.getEmbyUserId());
      binding.setEmbyUserName(user.getEmbyUserName());
      binding.setEmbyInfoId(user.getEmbyInfoId());
      return binding;
   }

   private void saveBinding(RoseUserBinding binding) {
      if (binding.getId() == null) {
         this.save(binding);
      } else {
         this.updateById(binding);
      }
   }

   private void clearLocalBinding(RoseUserBinding binding) {
      Date now = new Date();
      binding.setRoseAccountId(null);
      binding.setRoseAccountName(null);
      binding.setRoseUserId(null);
      binding.setRoseUsername(null);
      binding.setRoseMobile(null);
      binding.setRoseAvatarUrl(null);
      binding.setBindingStatus("UNBOUND");
      binding.setCookieText(null);
      binding.setCookieTextMasked(null);
      binding.setRoseAuthCookieText(null);
      binding.setTargetRoot(null);
      binding.setLibrariesJson(null);
      binding.setQrSessionId(null);
      binding.setQrStatus(null);
      binding.setQrScanUrl(null);
      binding.setLastError(null);
      binding.setRoseProfileJson(null);
      binding.setAccountSummaryJson(null);
      binding.setCookieUpdatedAt(null);
      binding.setBoundAt(null);
      binding.setLastSyncAt(now);
      if (binding.getId() == null) {
         this.save(binding);
      } else {
         this.lambdaUpdate()
            .eq(RoseUserBinding::getId, binding.getId())
            .set(RoseUserBinding::getRoseAccountId, null)
            .set(RoseUserBinding::getRoseAccountName, null)
            .set(RoseUserBinding::getRoseUserId, null)
            .set(RoseUserBinding::getRoseUsername, null)
            .set(RoseUserBinding::getRoseMobile, null)
            .set(RoseUserBinding::getRoseAvatarUrl, null)
            .set(RoseUserBinding::getBindingStatus, "UNBOUND")
            .set(RoseUserBinding::getCookieText, null)
            .set(RoseUserBinding::getCookieTextMasked, null)
            .set(RoseUserBinding::getRoseAuthCookieText, null)
            .set(RoseUserBinding::getTargetRoot, null)
            .set(RoseUserBinding::getLibrariesJson, null)
            .set(RoseUserBinding::getQrSessionId, null)
            .set(RoseUserBinding::getQrStatus, null)
            .set(RoseUserBinding::getQrScanUrl, null)
            .set(RoseUserBinding::getLastError, null)
            .set(RoseUserBinding::getRoseProfileJson, null)
            .set(RoseUserBinding::getAccountSummaryJson, null)
            .set(RoseUserBinding::getCookieUpdatedAt, null)
            .set(RoseUserBinding::getBoundAt, null)
            .set(RoseUserBinding::getLastSyncAt, now)
            .update();
      }
   }

   private void saveProfileSnapshot(EmbyUser user, JSONObject roseResponse) {
      if (roseResponse != null) {
         RoseUserBinding binding = this.getOrCreateBinding(user);
         JSONObject profile = roseResponse.getJSONObject("profile");
         if (profile != null) {
            binding.setRoseProfileJson(profile.toJSONString());
            this.fillManagedAccount(binding, profile);
         }

         binding.setLastSyncAt(new Date());
         this.saveBinding(binding);
      }
   }

   private void updateBindingFromQrSession(EmbyUser user, JSONObject session) {
      RoseUserBinding binding = this.getOrCreateBinding(user);
      String status = session.getString("status");
      binding.setQrSessionId(session.getString("id"));
      binding.setQrStatus(status);
      binding.setQrScanUrl(session.getString("scan_url"));
      if (!StringUtils.hasText(binding.getDeviceApp()) && StringUtils.hasText(session.getString("app"))) {
         binding.setDeviceApp(session.getString("app"));
      }

      JSONObject accountSummary = session.getJSONObject("account_summary");
      if (accountSummary != null) {
         binding.setAccountSummaryJson(accountSummary.toJSONString());
         this.fillAccountSummary(binding, accountSummary);
      }

      String cookie = this.firstText(session.getString("cookie_text"), session.getString("cookie"), session.getString("cookies"));
      if (StringUtils.hasText(cookie)) {
         binding.setCookieText(cookie);
         binding.setCookieTextMasked(this.maskCookie(cookie));
         binding.setCookieUpdatedAt(new Date());
         binding.setBindingStatus("PENDING");
      }

      boolean completed = "completed".equalsIgnoreCase(status)
         || "success".equalsIgnoreCase(status)
         || "bound".equalsIgnoreCase(status)
         || Boolean.TRUE.equals(session.getBoolean("completed"))
         || session.getIntValue("status_code") == 2 && accountSummary != null;
      if (completed) {
         binding.setBindingStatus(StringUtils.hasText(binding.getCookieText()) ? "PENDING" : "BOUND");
      }

      binding.setLastSyncAt(new Date());
      this.saveBinding(binding);
   }

   private RoseBindingResponse toResponse(RoseUserBinding binding) {
      if (binding == null) {
         return null;
      } else {
         RoseBindingResponse response = new RoseBindingResponse();
         response.setId(binding.getId());
         response.setUserId(binding.getUserId());
         response.setEmbyUserId(binding.getEmbyUserId());
         response.setEmbyUserName(binding.getEmbyUserName());
         response.setRoseAccountId(binding.getRoseAccountId());
         response.setRoseAccountName(binding.getRoseAccountName());
         response.setRoseUserId(binding.getRoseUserId());
         response.setRoseUsername(binding.getRoseUsername());
         response.setRoseMobile(binding.getRoseMobile());
         response.setRoseAvatarUrl(binding.getRoseAvatarUrl());
         response.setDeviceApp(binding.getDeviceApp());
         response.setHasCookie(StringUtils.hasText(binding.getCookieText()));
         response.setCookieTextMasked(null);
         List<RoseLibraryBindingResponse> libraries = this.parseLibraries(binding.getLibrariesJson());
         response.setTargetRoot(binding.getTargetRoot());
         this.fillTargetRootDisplay(response, libraries);
         response.setLibraries(libraries);
         response.setBindingStatus(binding.getBindingStatus());
         response.setBound("BOUND".equalsIgnoreCase(binding.getBindingStatus()));
         response.setQrSessionId(binding.getQrSessionId());
         response.setQrStatus(binding.getQrStatus());
         response.setQrScanUrl(binding.getQrScanUrl());
         response.setLastError(binding.getLastError());
         response.setRoseProfile(this.parseJsonObject(binding.getRoseProfileJson()));
         response.setAccountSummary(this.parseJsonObject(binding.getAccountSummaryJson()));
         response.setCookieUpdatedAt(binding.getCookieUpdatedAt());
         response.setBoundAt(binding.getBoundAt());
         response.setLastSyncAt(binding.getLastSyncAt());
         return response;
      }
   }

   private boolean hasActiveRoseBinding(RoseUserBinding binding) {
      return binding != null && ("BOUND".equalsIgnoreCase(binding.getBindingStatus()) || StringUtils.hasText(binding.getRoseAccountId()));
   }

   private void markBindingUnbindError(RoseUserBinding binding, String message) {
      if (binding != null) {
         binding.setBindingStatus("ERROR");
         binding.setLastError(this.abbreviate(message));
         this.saveBinding(binding);
      }
   }

   private void notifyExpiredUnbindFailure(EmbyUser user, RoseUserBinding binding, String reason) {
      try {
         SendMessageRequest request = new SendMessageRequest();
         request.setName("\ud83c\udf39 Rose自动解绑失败");
         request.setOverview(this.buildExpiredUnbindFailureMessage(user, binding, reason));
         Map<String, String> extras = new HashMap<>();
         extras.put("userName", this.safeUserName(user));
         extras.put("userId", user != null && user.getId() != null ? String.valueOf(user.getId()) : "");
         extras.put("embyUserId", user == null ? "" : this.firstText(user.getEmbyUserId(), ""));
         extras.put("roseAccount", binding == null ? "" : this.firstText(binding.getRoseAccountName(), binding.getRoseUsername(), binding.getRoseUserId(), ""));
         extras.put("reason", this.abbreviate(reason));
         request.setExtraVariables(extras);
         this.notifyUtils.sendMultiChannel(request, "media_text_message", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
      } catch (Exception var6) {
         log.warn(
            "Rose到期删除自动解绑失败通知发送失败：userId={}, embyUserName={}, error={}",
            user == null ? null : user.getId(),
            user == null ? null : user.getEmbyUserName(),
            var6.getMessage()
         );
      }
   }

   private String buildExpiredUnbindFailureMessage(EmbyUser user, RoseUserBinding binding, String reason) {
      StringBuilder message = new StringBuilder();
      message.append("\ud83c\udf39 Rose到期删除自动解绑失败\n\n");
      message.append("\ud83d\udc64 用户：").append(this.safeUserName(user)).append("\n");
      if (user != null && user.getId() != null) {
         message.append("🌁 Mist 用户ID：").append(user.getId()).append("\n");
      }

      if (user != null && StringUtils.hasText(user.getEmbyUserId())) {
         message.append("\ud83c\udfac Emby用户ID：").append(user.getEmbyUserId()).append("\n");
      }

      String roseAccount = binding == null
         ? ""
         : this.firstText(binding.getRoseAccountName(), binding.getRoseUsername(), binding.getRoseUserId(), binding.getRoseAccountId());
      if (StringUtils.hasText(roseAccount)) {
         message.append("\ud83c\udf39 Rose账号：").append(roseAccount).append("\n");
      }

      message.append("⚠️ 原因：").append(this.abbreviate(reason)).append("\n\n");
      message.append("✅ 用户删除主流程已继续执行\n");
      message.append("\ud83d\udd0e 请手动检查 Rose 侧绑定。");
      return message.toString();
   }

   private String safeUserName(EmbyUser user) {
      return user == null ? "" : this.firstText(user.getEmbyUserName(), user.getEmail(), String.valueOf(user.getId()));
   }

   private List<RoseLibraryBindingResponse> parseLibraries(String librariesJson) {
      List<RoseLibraryBindingResponse> result = new ArrayList<>();
      if (!StringUtils.hasText(librariesJson)) {
         return result;
      } else {
         try {
            for (Object item : JSON.parseArray(librariesJson)) {
               if (item instanceof JSONObject json) {
                  RoseLibraryBindingResponse response = new RoseLibraryBindingResponse();
                  response.setLibraryKey(json.getString("library_key"));
                  response.setName(json.getString("name"));
                  response.setTargetRoot(json.getString("target_root"));
                  response.setTargetRootName(json.getString("target_root_name"));
                  response.setTargetRootPath(json.getString("target_root_path"));
                  response.setEnabled(json.getBoolean("enabled"));
                  result.add(response);
               }
            }

            return result;
         } catch (Exception var8) {
            return result;
         }
      }
   }

   private Object parseJsonObject(String json) {
      if (!StringUtils.hasText(json)) {
         return null;
      } else {
         try {
            return JSON.parseObject(json);
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private void fillTargetRootDisplay(RoseBindingResponse response, List<RoseLibraryBindingResponse> libraries) {
      if (response != null && libraries != null) {
         String targetRoot = response.getTargetRoot();

         for (RoseLibraryBindingResponse library : libraries) {
            if (library != null
               && !Boolean.FALSE.equals(library.getEnabled())
               && (!StringUtils.hasText(targetRoot) || targetRoot.equals(library.getTargetRoot()))) {
               if (!StringUtils.hasText(targetRoot)) {
                  response.setTargetRoot(library.getTargetRoot());
               }

               response.setTargetRootName(library.getTargetRootName());
               response.setTargetRootPath(library.getTargetRootPath());
               return;
            }
         }
      }
   }

   private JSONArray buildLibrariesPayload(List<RoseLibraryBindingRequest> libraries, boolean includeDisplayFields) {
      JSONArray array = new JSONArray();
      if (libraries == null) {
         return array;
      } else {
         for (RoseLibraryBindingRequest library : libraries) {
            if (library != null && StringUtils.hasText(library.getLibraryKey())) {
               JSONObject item = new JSONObject();
               item.put("library_key", library.getLibraryKey().trim());
               item.put("name", library.getName());
               item.put("target_root", StringUtils.hasText(library.getTargetRoot()) ? library.getTargetRoot().trim() : "0");
               item.put("enabled", Boolean.valueOf(Boolean.TRUE.equals(library.getEnabled())));
               if (includeDisplayFields) {
                  item.put("target_root_name", library.getTargetRootName());
                  item.put("target_root_path", library.getTargetRootPath());
               }

               array.add(item);
            }
         }

         return array;
      }
   }

   private JSONObject identityPayload(EmbyUser user) {
      JSONObject payload = new JSONObject();
      payload.put("emby_user_id", user.getEmbyUserId());
      payload.put("emby_username", user.getEmbyUserName());
      payload.put("display_name", user.getEmbyUserName());
      return payload;
   }

   private String loginRoseUser(EmbyUser user, String embyPassword) {
      String username = this.requireText(user == null ? "" : user.getEmbyUserName(), "当前 Mist 用户缺少 Emby 用户名");
      String password = this.requireText(embyPassword, "请输入 Emby 密码后再继续操作");
      JSONObject payload = new JSONObject();
      payload.put("username", username);
      payload.put("password", password);
      HttpRequest request = HttpRequest.post(this.getRoseBaseUrl() + "/api/auth/login/emby")
         .timeout(30000)
         .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
         .body(payload.toJSONString());

      String var9;
      try (HttpResponse response = request.execute()) {
         this.parseRoseResponse(response);
         String roseSessionCookie = this.extractRoseSessionCookie(response.header("Set-Cookie"));
         if (!StringUtils.hasText(roseSessionCookie)) {
            throw new BizException("Rose登录成功但未返回登录态");
         }

         var9 = roseSessionCookie;
      }

      return var9;
   }

   private String loginRoseAdmin(String adminPassword) {
      String password = this.requireText(adminPassword, "请输入 Rose 管理员密码后再解绑");
      JSONObject payload = new JSONObject();
      payload.put("password", password);
      HttpRequest request = HttpRequest.post(this.getRoseBaseUrl() + "/api/auth/login/admin")
         .timeout(30000)
         .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
         .body(payload.toJSONString());

      String var7;
      try (HttpResponse response = request.execute()) {
         this.parseRoseResponse(response);
         String roseSessionCookie = this.extractRoseSessionCookie(response.header("Set-Cookie"));
         if (!StringUtils.hasText(roseSessionCookie)) {
            throw new BizException("Rose管理员登录成功但未返回登录态");
         }

         var7 = roseSessionCookie;
      }

      return var7;
   }

   private String resolveRoseAuthCookie(EmbyUser user, String embyPassword) {
      if (StringUtils.hasText(embyPassword)) {
         return this.loginRoseUser(user, embyPassword);
      } else {
         throw new BizException("请输入 Emby 密码后再继续操作");
      }
   }

   private JSONObject postJson(String path, JSONObject payload) {
      return this.postJson(path, payload, this.getConfiguredRoseCookie());
   }

   private JSONObject postUserJson(String path, JSONObject payload, String roseAuthCookie) {
      return this.postJson(path, payload, this.requireRoseAuthCookie(roseAuthCookie));
   }

   private JSONObject postJson(String path, JSONObject payload, String cookieText) {
      HttpRequest request = HttpRequest.post(this.getRoseBaseUrl() + path)
         .timeout(30000)
         .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
         .body(payload == null ? "{}" : payload.toJSONString());
      this.applyRequestCookie(request, cookieText);

      JSONObject var6;
      try (HttpResponse response = request.execute()) {
         var6 = this.parseRoseResponse(response);
      }

      return var6;
   }

   private JSONObject getJson(String path) {
      return this.getJson(path, this.getConfiguredRoseCookie());
   }

   private JSONObject getUserJson(String path, String roseAuthCookie) {
      return this.getJson(path, this.requireRoseAuthCookie(roseAuthCookie));
   }

   private JSONObject getJson(String path, String cookieText) {
      HttpRequest request = HttpRequest.get(this.getRoseBaseUrl() + path).timeout(30000).header(Header.ACCEPT, "application/json");
      this.applyRequestCookie(request, cookieText);

      JSONObject var5;
      try (HttpResponse response = request.execute()) {
         var5 = this.parseRoseResponse(response);
      }

      return var5;
   }

   private byte[] getBytes(String path) {
      return this.getBytes(path, this.getConfiguredRoseCookie());
   }

   private byte[] getUserBytes(String path, String roseAuthCookie) {
      return this.getBytes(path, this.requireRoseAuthCookie(roseAuthCookie));
   }

   private byte[] getBytes(String path, String cookieText) {
      HttpRequest request = HttpRequest.get(this.getRoseBaseUrl() + path).timeout(30000);
      this.applyRequestCookie(request, cookieText);

      byte[] var6;
      try (HttpResponse response = request.execute()) {
         int status = response.getStatus();
         if (status < 200 || status >= 300) {
            throw new BizException("Rose二维码图片获取失败(" + status + ")");
         }

         var6 = response.bodyBytes();
      }

      return var6;
   }

   private JSONObject parseRoseResponse(HttpResponse response) {
      int status = response.getStatus();
      String body = response.body();
      if (status >= 200 && status < 300) {
         JSONObject json;
         try {
            json = JSON.parseObject(body);
         } catch (Exception var7) {
            throw new BizException("Rose接口返回非JSON数据");
         }

         Boolean ok = json.getBoolean("ok");
         if (Boolean.FALSE.equals(ok)) {
            throw new BizException(this.roseErrorMessage(json));
         } else {
            Integer code = json.getInteger("code");
            if (code != null && code != 0 && code != 200) {
               throw new BizException(this.roseErrorMessage(json));
            } else {
               return json;
            }
         }
      } else {
         throw new BizException(this.roseHttpErrorMessage(status, body));
      }
   }

   private String roseHttpErrorMessage(int status, String body) {
      if (status == 401 && StringUtils.hasText(body)) {
         if (body.contains("请先使用 Emby 账号登录")) {
            return "Rose登录态已失效，请重新输入 Emby 密码生成二维码";
         }

         if (body.contains("请先登录管理员账号")) {
            return "Rose接口需要管理员登录态：Foam后端请求不会携带浏览器里的Rose登录Cookie";
         }

         if (body.contains("AuthenticationError")) {
            return "Rose登录态已失效，请重新输入 Emby 密码生成二维码";
         }
      }

      if (StringUtils.hasText(body)) {
         try {
            JSONObject json = JSON.parseObject(body);
            String message = this.roseErrorMessage(json);
            if (StringUtils.hasText(message)) {
               return message;
            }
         } catch (Exception var5) {
         }
      }

      return "Rose接口异常(" + status + "): " + this.abbreviate(body);
   }

   private void applyRequestCookie(HttpRequest request, String cookieText) {
      if (StringUtils.hasText(cookieText)) {
         request.cookie(cookieText.trim());
      }
   }

   private String getConfiguredRoseCookie() {
      return this.configCacheLoaderUtils.getConfigValue("rose_api_cookie");
   }

   private String getConfiguredRoseAdminPassword() {
      return this.configCacheLoaderUtils.getConfigValue("rose_admin_password");
   }

   private String requireRoseAuthCookie(RoseUserBinding binding) {
      return this.requireRoseAuthCookie(binding == null ? "" : binding.getRoseAuthCookieText());
   }

   private String requireRoseAuthCookie(String cookieText) {
      if (!StringUtils.hasText(cookieText)) {
         throw new BizException("Rose登录态已失效，请重新输入 Emby 密码生成二维码");
      } else {
         return cookieText.trim();
      }
   }

   private String extractRoseSessionCookie(String setCookieHeader) {
      if (!StringUtils.hasText(setCookieHeader)) {
         return "";
      } else {
         String[] cookieBlocks = setCookieHeader.split("(?i),\\s*(?=[A-Za-z0-9_\\-]+=)");

         for (String block : cookieBlocks) {
            String firstToken = block.split(";", 2)[0].trim();
            if (firstToken.startsWith("rose_session=")) {
               return firstToken;
            }
         }

         return "";
      }
   }

   private JSONObject sanitizeJson(JSONObject source) {
      if (source == null) {
         return null;
      } else {
         JSONObject copy = JSON.parseObject(source.toJSONString());
         this.removeSecretFields(copy);
         return copy;
      }
   }

   private void removeSecretFields(Object value) {
      if (value instanceof JSONObject object) {
         object.remove("cookie");
         object.remove("cookies");
         object.remove("cookie_text");
         object.remove("cookieText");
         object.remove("rose_auth_cookie_text");
         object.remove("roseAuthCookieText");
         object.remove("rose_session");

         for (Object nested : object.values()) {
            this.removeSecretFields(nested);
         }
      } else if (value instanceof JSONArray) {
         for (Object nested : (JSONArray)value) {
            this.removeSecretFields(nested);
         }
      }
   }

   private void fillAccountSummary(RoseUserBinding binding, JSONObject summary) {
      if (summary != null) {
         binding.setRoseUserId(this.firstText(summary.getString("user_id"), summary.getString("uid")));
         binding.setRoseUsername(this.firstText(summary.getString("username"), summary.getString("user_name"), summary.getString("nickname")));
         binding.setRoseMobile(summary.getString("mobile"));
         binding.setRoseAvatarUrl(this.firstText(summary.getString("avatar_url"), summary.getString("avatar")));
      }
   }

   private void fillManagedAccount(RoseUserBinding binding, JSONObject profile) {
      if (profile != null) {
         JSONObject managedAccount = profile.getJSONObject("managed_account");
         boolean remoteCookieExists = false;
         if (managedAccount != null) {
            binding.setRoseAccountId(managedAccount.getString("account_id"));
            binding.setRoseAccountName(this.firstText(managedAccount.getString("account_name"), managedAccount.getString("self_service_account_name")));
            remoteCookieExists = Boolean.TRUE.equals(managedAccount.getBoolean("cookie_exists"))
               || StringUtils.hasText(managedAccount.getString("cookie_text_masked"));
            if (!StringUtils.hasText(binding.getCookieTextMasked())) {
               binding.setCookieTextMasked(managedAccount.getString("cookie_text_masked"));
            }

            if (!StringUtils.hasText(binding.getDeviceApp()) && StringUtils.hasText(managedAccount.getString("app"))) {
               binding.setDeviceApp(managedAccount.getString("app"));
            }
         }

         Object bindingObject = profile.get("binding");
         if (bindingObject instanceof JSONObject roseBinding) {
            binding.setTargetRoot(roseBinding.getString("target_root"));
         }

         if (remoteCookieExists || bindingObject instanceof JSONObject) {
            binding.setBindingStatus("BOUND");
            if (binding.getBoundAt() == null) {
               binding.setBoundAt(new Date());
            }
         }
      }
   }

   private List<String> resolveSupportedApps(JSONObject meta) {
      List<String> apps = new ArrayList<>();
      if (meta == null) {
         return apps;
      } else {
         JSONArray array = meta.getJSONArray("supported_account_apps");
         if (array == null) {
            return apps;
         } else {
            for (Object item : array) {
               if (item != null && StringUtils.hasText(String.valueOf(item))) {
                  apps.add(String.valueOf(item));
               }
            }

            return apps;
         }
      }
   }

   private String roseErrorMessage(JSONObject json) {
      return this.cleanRoseErrorMessage(
         this.firstText(json.getString("detail"), json.getString("error"), json.getString("msg"), json.getString("message"), "Rose接口调用失败")
      );
   }

   private String cleanRoseErrorMessage(String message) {
      return !StringUtils.hasText(message) ? "" : message.trim().replaceFirst("^[A-Za-z]+Error:\\s*", "");
   }

   private boolean isRoseEnabled() {
      return this.configCacheLoaderUtils.getConfigValue("rose_binding_enabled") != null;
   }

   private void assertRoseReady() {
      if (!this.isRoseEnabled()) {
         throw new BizException("Rose绑定入口未开启");
      } else if (!StringUtils.hasText(this.getRoseBaseUrl())) {
         throw new BizException("Rose接口地址未配置");
      }
   }

   private String getRoseBaseUrl() {
      String baseUrl = this.configCacheLoaderUtils.getConfigValue("rose_api_base_url");
      if (!StringUtils.hasText(baseUrl)) {
         return "";
      } else {
         String normalized = baseUrl.trim();

         while (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
         }

         return normalized;
      }
   }

   private String normalizeApp(String app) {
      return this.normalizeApp(app, null);
   }

   private String normalizeApp(String app, String fallback) {
      String value = StringUtils.hasText(app) ? app.trim() : fallback;
      return StringUtils.hasText(value) ? value.trim() : "web";
   }

   private String requireText(String value, String message) {
      if (!StringUtils.hasText(value)) {
         throw new BizException(message);
      } else {
         return value.trim();
      }
   }

   private String encodePath(String value) {
      return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
   }

   private String maskCookie(String cookie) {
      if (!StringUtils.hasText(cookie)) {
         return "";
      } else {
         String value = cookie.trim();
         return value.length() <= 12 ? "******" : value.substring(0, 6) + "..." + value.substring(value.length() - 6);
      }
   }

   private String firstText(String... values) {
      if (values == null) {
         return "";
      } else {
         for (String value : values) {
            if (StringUtils.hasText(value)) {
               return value.trim();
            }
         }

         return "";
      }
   }

   private String abbreviate(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String normalized = value.replace("\r", " ").replace("\n", " ").trim();
         return normalized.length() > 240 ? normalized.substring(0, 240) + "..." : normalized;
      }
   }

   private boolean isRoseMissingBindingError(Exception e) {
      String message = e == null ? "" : e.getMessage();
      return !StringUtils.hasText(message) ? false : message.contains("没有找到可解绑") || message.toLowerCase().contains("not found");
   }
}
