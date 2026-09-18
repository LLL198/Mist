package com.una.embyhub.foam.client;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.model.entity.EmbyUser;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EmbyClient {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyClient.class);
   private static final String CACHE_PREFIX = "foam:emby:client:v1:";
   private static final String CACHE_STALE_SUFFIX = ":stale";
   private static final int JSON_REQUEST_TIMEOUT_MS = 30000;
   private static final int TODAY_COUNT_PAGE_SIZE = 200;
   private static final int TODAY_COUNT_MAX_PAGES = 100;
   private static final ZoneId DEFAULT_TODAY_COUNT_ZONE = ZoneId.of("Asia/Shanghai");
   private static final String ITEM_DETAIL_FIELDS = "Overview,Genres,Taglines,Studios,CommunityRating,ProductionYear,RunTimeTicks,PremiereDate,ParentId,Type,ImageTags,BackdropImageTags,People,RemoteTrailers,ProviderIds,MediaSources,MediaStreams";
   private static final String LIBRARY_ITEM_FIELDS = "PrimaryImageAspectRatio,Overview,Studios,Genres,CommunityRating,ProductionYear,ParentId,Type,ImageTags,PrimaryImageTag,BackdropImageTags,PremiereDate,DateCreated,SeriesName,SeriesId,SeriesPrimaryImageTag,Seasons,RunTimeTicks,People,MediaSources,MediaStreams";
   private static final String EPISODE_ITEM_FIELDS = "PrimaryImageAspectRatio,Overview,CommunityRating,ProductionYear,RunTimeTicks,ParentId,Type,ImageTags,PrimaryImageTag,BackdropImageTags,PremiereDate,DateCreated,SeriesName,SeriesId,SeriesPrimaryImageTag,SeasonId,SeasonName,IndexNumber,ParentIndexNumber,MediaSources,MediaStreams";
   private static final String SPEED_TEST_ITEM_FIELDS = "MediaSources,Path,Size,Type,Name";
   private static final long SPEED_TEST_SAMPLE_BYTES = 31457280L;
   private static final String SPEED_TEST_USER_TOKEN_KEY_PREFIX = "foam:emby:speed-test:user-token:";
   private static final String SPEED_TEST_USER_TOKEN_STATUS_KEY_PREFIX = "foam:emby:speed-test:user-token-status:";
   private static final long SPEED_TEST_USER_TOKEN_TTL_SECONDS = 604800L;
   private static final long SPEED_TEST_USER_TOKEN_STATUS_TTL_SECONDS = 600L;
   private static final String SPEED_TEST_USER_TOKEN_ENCRYPTED_PREFIX = "v1:";
   private static final int SPEED_TEST_USER_TOKEN_IV_BYTES = 12;
   private static final int SPEED_TEST_USER_TOKEN_GCM_TAG_BITS = 128;
   private static final SecureRandom SPEED_TEST_USER_TOKEN_RANDOM = new SecureRandom();
   private static final Set<String> ALLOWED_IMAGE_TYPES = Set.of(
      "Primary",
      "Art",
      "Backdrop",
      "Banner",
      "Logo",
      "Thumb",
      "Disc",
      "Box",
      "Screenshot",
      "Menu",
      "Chapter",
      "BoxRear",
      "Thumbnail",
      "LogoLight",
      "LogoLightColor"
   );
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final StringRedisTemplate stringRedisTemplate;

   private EmbyInfoCacheManagerUtils.EmbyServerConfig getCurrentServerConfig() {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      return this.embyInfoCacheManager.getRequiredConfig(embyUser);
   }

   private String getServerUrl() {
      return this.getCurrentServerConfig().url();
   }

   private String getApiKey() {
      return this.getCurrentServerConfig().apiKey();
   }

   private String getCopyfromuserid() {
      return this.getCurrentServerConfig().copyfromuserid();
   }

   private String buildBase(String path) {
      return this.buildBase(this.getServerUrl(), path);
   }

   private String buildBase(String baseUrl, String path) {
      String base = StrUtil.removeSuffix(baseUrl, "/");
      if (!path.startsWith("/")) {
         path = "/" + path;
      }

      if (base.toLowerCase().endsWith("/emby") && path.toLowerCase().startsWith("/emby/")) {
         path = path.substring("/emby".length());
      }

      return base + path;
   }

   private JSONObject getJson(String path, Map<String, Object> params) {
      String url = this.buildBase(path);
      HttpRequest request = HttpRequest.get(url).header("X-Emby-Token", this.getApiKey()).header("Accept", "application/json").timeout(30000);
      if (params != null && !params.isEmpty()) {
         request.form(params);
      }

      HttpResponse resp = request.execute();
      String body = resp.body();
      if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
         return JSON.parseObject(body);
      } else {
         throw new RuntimeException("Emby 请求失败: " + resp.getStatus() + " - " + body);
      }
   }

   private String resolveQueryUserId(EmbyInfoCacheManagerUtils.EmbyServerConfig config, EmbyUser user) {
      if (user != null && config.id().equals(user.getEmbyInfoId()) && StrUtil.isNotBlank(user.getEmbyUserId())) {
         return user.getEmbyUserId();
      } else {
         return user != null && Integer.valueOf(1).equals(user.getIsAdmin()) && StrUtil.isNotBlank(config.adminQueryUserid())
            ? config.adminQueryUserid()
            : config.copyfromuserid();
      }
   }

   public JSONObject getLibraryCounts(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      return this.getJsonWithConfigCached("/emby/Items/Counts", Map.of("userId", queryUserId), serverConfig, 120L, 1800L);
   }

   public JSONObject getLibraryCounts(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String timezone) {
      JSONObject result = new JSONObject(this.getLibraryCounts(serverConfig, user));
      result.putAll(this.getTodayLibraryCounts(serverConfig, user, timezone));
      return result;
   }

   private JSONObject getTodayLibraryCounts(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String timezone) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      ZoneId zone = this.resolveTodayCountZone(timezone);
      LocalDate today = LocalDate.now(zone);
      EmbyTodayCountAccumulator accumulator = new EmbyTodayCountAccumulator(today, zone);
      boolean completed = false;
      String minDateLastSaved = today.atStartOfDay(zone).toInstant().toString();

      for (int pageIndex = 0; pageIndex < 100; pageIndex++) {
         Map<String, Object> params = new HashMap<>();
         params.put("Recursive", true);
         params.put("IncludeItemTypes", "Movie,Episode");
         params.put("MediaTypes", "Video");
         params.put("MinDateLastSaved", minDateLastSaved);
         params.put("Fields", "Path,MediaSources,SeriesId");
         params.put("SortBy", "SortName");
         params.put("SortOrder", "Ascending");
         params.put("StartIndex", pageIndex * 200);
         params.put("Limit", 200);
         params.put("EnableUserData", false);
         JSONObject page = this.getJsonWithConfigCached("/emby/Users/" + queryUserId + "/Items", params, serverConfig, 5L, 120L);
         JSONArray items = page.getJSONArray("Items");
         accumulator.accept(items);
         if (items == null || items.size() < 200) {
            completed = true;
            break;
         }
      }

      return accumulator.toJson(!completed);
   }

   private ZoneId resolveTodayCountZone(String timezone) {
      if (!StrUtil.isBlank(timezone) && timezone.length() <= 64) {
         try {
            return ZoneId.of(timezone.trim());
         } catch (DateTimeException var3) {
            return DEFAULT_TODAY_COUNT_ZONE;
         }
      } else {
         return DEFAULT_TODAY_COUNT_ZONE;
      }
   }

   public JSONObject getMediaFolders(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      return this.getJsonWithConfig("/emby/Library/MediaFolders", null, serverConfig);
   }

   public JSONArray getSelectableMediaFolders(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      return this.getJsonArrayWithConfig("/emby/Library/SelectableMediaFolders", null, serverConfig);
   }

   public JSONObject getLibraryItems(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String parentId, int limit) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      Map<String, Object> params = new HashMap<>();
      params.put("ParentId", parentId);
      params.put("Limit", Math.max(1, limit));
      params.put("SortBy", "Random");
      params.put("Recursive", true);
      params.put("Fields", "PrimaryImageAspectRatio,ImageTags,BackdropImageTags,ProductionYear,Overview,Type");
      params.put("IncludeItemTypes", "Movie,Series");
      return this.getJsonWithConfig("/emby/Users/" + queryUserId + "/Items", params, serverConfig);
   }

   public JSONObject getSpeedTestMedia(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      JSONObject media = this.getSpeedTestMediaForUser(serverConfig, queryUserId);
      String userAccessToken = this.getCachedSpeedTestUserAccessToken(serverConfig, user);
      if (StrUtil.isNotBlank(userAccessToken)) {
         media.put("accessToken", userAccessToken);
      } else {
         media.put("downloadAuthMessage", this.getSpeedTestUserTokenStatus(serverConfig, user));
      }

      return media;
   }

   public JSONObject getSpeedTestMediaWithCredential(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser foamUser, String username, String rawPassword
   ) {
      if (serverConfig != null && foamUser != null && foamUser.getId() != null && !StrUtil.isBlank(username) && !StrUtil.isBlank(rawPassword)) {
         if (!Integer.valueOf(1).equals(foamUser.getIsAdmin())) {
            throw new BizException("只有管理员可以使用临时 Emby 授权测速");
         } else {
            try {
               JSONObject body = new JSONObject();
               body.put("Username", username.trim());
               body.put("Pw", rawPassword);
               HttpResponse response = HttpRequest.post(this.buildBase(serverConfig.url(), "/Users/AuthenticateByName"))
                  .header("X-Emby-Authorization", this.buildUserAuthorizationHeader(serverConfig, foamUser))
                  .header(Header.CONTENT_TYPE, "application/json")
                  .header("Accept", "application/json")
                  .body(body.toJSONString())
                  .timeout(30000)
                  .execute();
               if (response.getStatus() >= 200 && response.getStatus() < 300) {
                  JSONObject result = JSON.parseObject(response.body());
                  JSONObject tokenUser = result == null ? null : result.getJSONObject("User");
                  String tokenUserId = tokenUser == null ? "" : tokenUser.getString("Id");
                  String accessToken = result == null ? "" : result.getString("AccessToken");
                  if (!StrUtil.isBlank(tokenUserId) && !StrUtil.isBlank(accessToken)) {
                     JSONObject media = this.getSpeedTestMediaForUser(serverConfig, tokenUserId);
                     media.put("accessToken", accessToken);
                     return media;
                  } else {
                     log.warn("管理员临时 Emby 测速授权返回为空: userId={}, embyInfoId={}", foamUser.getId(), serverConfig.id());
                     throw new BizException("Emby 未返回可用的用户授权");
                  }
               } else {
                  log.warn("管理员临时 Emby 测速授权失败: userId={}, embyInfoId={}, status={}", foamUser.getId(), serverConfig.id(), response.getStatus());
                  throw new BizException(this.speedTestCredentialAuthFailureMessage(response.getStatus()));
               }
            } catch (BizException var12) {
               throw var12;
            } catch (Exception var13) {
               log.warn("管理员临时 Emby 测速授权异常: userId={}, embyInfoId={}, error={}", foamUser.getId(), serverConfig.id(), var13.getMessage());
               throw new BizException("Emby 用户下载测速授权获取失败");
            }
         }
      } else {
         throw new BizException("请输入 Emby 账号和密码");
      }
   }

   private JSONObject getSpeedTestMediaForUser(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String queryUserId) {
      if (StrUtil.isBlank(queryUserId)) {
         throw new BizException("未配置可用于测速的 Emby 用户");
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("Recursive", true);
         params.put("IncludeItemTypes", "Movie,Episode,Video");
         params.put("SortBy", "Random");
         params.put("Limit", 20);
         params.put("EnableUserData", false);
         params.put("Fields", "MediaSources,Path,Size,Type,Name");
         JSONObject result = this.getJsonWithConfig("/emby/Users/" + queryUserId + "/Items", params, serverConfig);
         JSONArray items = result == null ? null : result.getJSONArray("Items");
         if (items != null && !items.isEmpty()) {
            for (int index = 0; index < items.size(); index++) {
               JSONObject item = items.getJSONObject(index);
               JSONObject media = this.buildSpeedTestMedia(item, serverConfig);
               if (media != null) {
                  return media;
               }
            }

            throw new BizException("未找到可用于测速的 Emby 视频媒体");
         } else {
            throw new BizException("未找到可用于测速的 Emby 视频媒体");
         }
      }
   }

   public boolean tryCacheSpeedTestUserAccessToken(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String rawPassword) {
      if (serverConfig != null
         && user != null
         && !StrUtil.isBlank(rawPassword)
         && !StrUtil.isBlank(user.getEmbyUserName())
         && !StrUtil.isBlank(user.getEmbyUserId())) {
         try {
            JSONObject body = new JSONObject();
            body.put("Username", user.getEmbyUserName());
            body.put("Pw", rawPassword);
            HttpResponse response = HttpRequest.post(this.buildBase(serverConfig.url(), "/Users/AuthenticateByName"))
               .header("X-Emby-Authorization", this.buildUserAuthorizationHeader(serverConfig, user))
               .header(Header.CONTENT_TYPE, "application/json")
               .header("Accept", "application/json")
               .body(body.toJSONString())
               .timeout(30000)
               .execute();
            if (response.getStatus() >= 200 && response.getStatus() < 300) {
               JSONObject result = JSON.parseObject(response.body());
               JSONObject tokenUser = result == null ? null : result.getJSONObject("User");
               String tokenUserId = tokenUser == null ? "" : tokenUser.getString("Id");
               boolean administrator = tokenUser != null
                  && tokenUser.getJSONObject("Policy") != null
                  && Boolean.TRUE.equals(tokenUser.getJSONObject("Policy").getBoolean("IsAdministrator"));
               boolean foamAdministrator = Integer.valueOf(1).equals(user.getIsAdmin());
               String accessToken = result == null ? "" : result.getString("AccessToken");
               if (!user.getEmbyUserId().equals(tokenUserId)) {
                  log.warn("Emby 用户测速 Token 用户不匹配: userId={}, embyInfoId={}", user.getId(), serverConfig.id());
                  this.recordSpeedTestUserTokenStatus(serverConfig, user, "Emby 返回用户与当前账号不匹配，已拒绝下载测速授权");
                  return false;
               } else if (administrator && !foamAdministrator) {
                  log.warn("普通用户获取到 Emby 管理员测速 Token，已拒绝下发: userId={}, embyInfoId={}", user.getId(), serverConfig.id());
                  this.recordSpeedTestUserTokenStatus(serverConfig, user, "当前 Emby 用户是管理员，普通用户不能使用管理员授权测速");
                  return false;
               } else if (StrUtil.isBlank(accessToken)) {
                  log.warn("Emby 用户测速 Token 为空: userId={}, embyInfoId={}", user.getId(), serverConfig.id());
                  this.recordSpeedTestUserTokenStatus(serverConfig, user, "Emby 未返回用户下载测速授权");
                  return false;
               } else {
                  this.writeEncryptedSpeedTestUserAccessToken(serverConfig, user, accessToken);
                  this.clearSpeedTestUserTokenStatus(serverConfig, user);
                  return true;
               }
            } else {
               log.warn("Emby 用户测速 Token 获取失败: userId={}, embyInfoId={}, status={}", user.getId(), serverConfig.id(), response.getStatus());
               this.recordSpeedTestUserTokenStatus(serverConfig, user, this.speedTestAuthFailureMessage(response.getStatus()));
               return false;
            }
         } catch (Exception var12) {
            log.warn("Emby 用户测速 Token 缓存失败: userId={}, embyInfoId={}, error={}", user.getId(), serverConfig.id(), var12.getMessage());
            this.recordSpeedTestUserTokenStatus(serverConfig, user, "Emby 用户下载测速授权获取失败");
            return false;
         }
      } else {
         this.recordSpeedTestUserTokenStatus(serverConfig, user, "当前用户缺少 Emby 用户信息，无法获取下载测速授权");
         return false;
      }
   }

   private String getCachedSpeedTestUserAccessToken(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      if (serverConfig != null && user != null && user.getId() != null && serverConfig.id() != null) {
         try {
            String cached = this.stringRedisTemplate.opsForValue().get(this.speedTestUserTokenKey(serverConfig, user));
            if (StrUtil.isBlank(cached)) {
               return "";
            } else if (!cached.startsWith("v1:")) {
               this.writeEncryptedSpeedTestUserAccessToken(serverConfig, user, cached);
               return cached;
            } else {
               return this.decryptSpeedTestUserAccessToken(serverConfig, user, cached);
            }
         } catch (Exception var4) {
            log.warn("读取 Emby 用户测速 Token 失败: userId={}, embyInfoId={}, error={}", user.getId(), serverConfig.id(), var4.getMessage());
            return "";
         }
      } else {
         return "";
      }
   }

   private void writeEncryptedSpeedTestUserAccessToken(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String accessToken) {
      String encrypted = this.encryptSpeedTestUserAccessToken(serverConfig, user, accessToken);
      this.stringRedisTemplate.opsForValue().set(this.speedTestUserTokenKey(serverConfig, user), encrypted, 604800L, TimeUnit.SECONDS);
   }

   private String speedTestUserTokenKey(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      return "foam:emby:speed-test:user-token:" + user.getId() + ":" + serverConfig.id();
   }

   private String getSpeedTestUserTokenStatus(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      if (serverConfig != null && user != null && user.getId() != null && serverConfig.id() != null) {
         try {
            String status = this.stringRedisTemplate.opsForValue().get(this.speedTestUserTokenStatusKey(serverConfig, user));
            return StrUtil.blankToDefault(status, "下载测速缺少用户授权，请重新登录后再试");
         } catch (Exception var4) {
            log.warn("读取 Emby 用户测速 Token 状态失败: userId={}, embyInfoId={}, error={}", user.getId(), serverConfig.id(), var4.getMessage());
            return "下载测速缺少用户授权，请重新登录后再试";
         }
      } else {
         return "下载测速缺少用户授权，请重新登录后再试";
      }
   }

   private void recordSpeedTestUserTokenStatus(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String message) {
      if (serverConfig != null && user != null && user.getId() != null && serverConfig.id() != null) {
         try {
            this.stringRedisTemplate
               .opsForValue()
               .set(this.speedTestUserTokenStatusKey(serverConfig, user), StrUtil.blankToDefault(message, "下载测速缺少用户授权，请重新登录后再试"), 600L, TimeUnit.SECONDS);
         } catch (Exception var5) {
            log.warn("写入 Emby 用户测速 Token 状态失败: userId={}, embyInfoId={}, error={}", user.getId(), serverConfig.id(), var5.getMessage());
         }
      }
   }

   private void clearSpeedTestUserTokenStatus(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      if (serverConfig != null && user != null && user.getId() != null && serverConfig.id() != null) {
         try {
            this.stringRedisTemplate.delete(this.speedTestUserTokenStatusKey(serverConfig, user));
         } catch (Exception var4) {
            log.debug("清理 Emby 用户测速 Token 状态失败: userId={}, embyInfoId={}, error={}", user.getId(), serverConfig.id(), var4.getMessage());
         }
      }
   }

   private String speedTestUserTokenStatusKey(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      return "foam:emby:speed-test:user-token-status:" + user.getId() + ":" + serverConfig.id();
   }

   private String encryptSpeedTestUserAccessToken(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String accessToken) {
      if (StrUtil.isBlank(accessToken)) {
         throw new IllegalArgumentException("accessToken cannot be blank");
      } else {
         try {
            byte[] iv = new byte[12];
            SPEED_TEST_USER_TOKEN_RANDOM.nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, new SecretKeySpec(this.speedTestUserTokenCryptoKey(serverConfig, user), "AES"), new GCMParameterSpec(128, iv));
            cipher.updateAAD(this.speedTestUserTokenAad(serverConfig, user));
            byte[] cipherText = cipher.doFinal(accessToken.getBytes(StandardCharsets.UTF_8));
            byte[] payload = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, payload, 0, iv.length);
            System.arraycopy(cipherText, 0, payload, iv.length, cipherText.length);
            return "v1:" + Base64.getEncoder().encodeToString(payload);
         } catch (GeneralSecurityException var8) {
            throw new IllegalStateException("Emby 用户测速 Token 加密失败", var8);
         }
      }
   }

   private String decryptSpeedTestUserAccessToken(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String encrypted) {
      try {
         String encoded = encrypted.substring("v1:".length());
         byte[] payload = Base64.getDecoder().decode(encoded);
         if (payload.length <= 12) {
            throw new IllegalArgumentException("invalid encrypted token payload");
         } else {
            byte[] iv = new byte[12];
            byte[] cipherText = new byte[payload.length - 12];
            System.arraycopy(payload, 0, iv, 0, iv.length);
            System.arraycopy(payload, iv.length, cipherText, 0, cipherText.length);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, new SecretKeySpec(this.speedTestUserTokenCryptoKey(serverConfig, user), "AES"), new GCMParameterSpec(128, iv));
            cipher.updateAAD(this.speedTestUserTokenAad(serverConfig, user));
            return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
         }
      } catch (GeneralSecurityException | IllegalArgumentException var9) {
         throw new IllegalStateException("Emby 用户测速 Token 解密失败", var9);
      }
   }

   private byte[] speedTestUserTokenCryptoKey(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) throws GeneralSecurityException {
      if (serverConfig != null && user != null && !StrUtil.isBlank(serverConfig.apiKey()) && serverConfig.id() != null && user.getId() != null) {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         String keyMaterial = serverConfig.apiKey() + ":" + serverConfig.id() + ":" + user.getId() + ":foam-speed-test-token:v1";
         return digest.digest(keyMaterial.getBytes(StandardCharsets.UTF_8));
      } else {
         throw new IllegalArgumentException("Emby 用户测速 Token 加密上下文不完整");
      }
   }

   private byte[] speedTestUserTokenAad(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      String aad = "serverId=" + serverConfig.id() + ";userId=" + user.getId();
      return aad.getBytes(StandardCharsets.UTF_8);
   }

   private String speedTestAuthFailureMessage(int status) {
      return status != 401 && status != 403 ? "Emby 用户下载测速授权获取失败: HTTP " + status : "Emby 用户认证失败，请确认 Mist 登录密码和 Emby 密码一致";
   }

   private String speedTestCredentialAuthFailureMessage(int status) {
      return status != 401 && status != 403 ? "Emby 用户下载测速授权获取失败: HTTP " + status : "Emby 用户认证失败，请确认账号密码";
   }

   private String buildUserAuthorizationHeader(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user) {
      String deviceId = "mist-speed-test-" + serverConfig.id() + "-" + user.getId();
      return "MediaBrowser Client=\"Mist\", Device=\"Mist Web\", DeviceId=\"" + deviceId + "\", Version=\"2.1.8\"";
   }

   private JSONObject buildSpeedTestMedia(JSONObject item, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (item == null) {
         return null;
      } else {
         String itemId = item.getString("Id");
         if (StrUtil.isBlank(itemId)) {
            return null;
         } else {
            JSONArray mediaSources = item.getJSONArray("MediaSources");
            JSONObject mediaSource = this.firstMediaSource(mediaSources);
            String mediaSourceId = mediaSource == null ? null : mediaSource.getString("Id");
            Long size = mediaSource == null ? item.getLong("Size") : mediaSource.getLong("Size");
            JSONObject media = new JSONObject();
            media.put("embyInfoId", serverConfig.id());
            media.put("itemId", itemId);
            media.put("mediaSourceId", mediaSourceId);
            media.put("name", item.getString("Name"));
            media.put("type", item.getString("Type"));
            media.put("size", size);
            media.put("sampleBytes", Long.valueOf(31457280L));
            media.put("streamPath", this.buildSpeedTestStreamPath(itemId, mediaSourceId));
            return media;
         }
      }
   }

   private JSONObject firstMediaSource(JSONArray mediaSources) {
      if (mediaSources != null && !mediaSources.isEmpty()) {
         for (int index = 0; index < mediaSources.size(); index++) {
            JSONObject mediaSource = mediaSources.getJSONObject(index);
            if (mediaSource != null && StrUtil.isNotBlank(mediaSource.getString("Id"))) {
               return mediaSource;
            }
         }

         return mediaSources.getJSONObject(0);
      } else {
         return null;
      }
   }

   private String buildSpeedTestStreamPath(String itemId, String mediaSourceId) {
      StringBuilder path = new StringBuilder("/emby/Videos/").append(this.encodeQueryValue(itemId)).append("/stream?static=true");
      if (StrUtil.isNotBlank(mediaSourceId)) {
         path.append("&MediaSourceId=").append(this.encodeQueryValue(mediaSourceId));
      }

      return path.toString();
   }

   private String encodeQueryValue(String value) {
      return URLEncoder.encode(StrUtil.blankToDefault(value, ""), StandardCharsets.UTF_8);
   }

   public EmbyClient.EmbyImageBytes getItemImage(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      String itemId,
      String imageType,
      Integer imageIndex,
      Integer width,
      Integer height,
      Integer maxWidth,
      Integer maxHeight,
      Integer quality,
      String tag
   ) {
      String normalizedImageType = this.normalizeImageType(imageType);
      String path = "/emby/Items/" + itemId + "/Images/" + normalizedImageType;
      if ("Backdrop".equals(normalizedImageType) && imageIndex != null && imageIndex >= 0) {
         path = path + "/" + imageIndex;
      }

      return this.getImage(serverConfig, path, width, height, maxWidth, maxHeight, quality, tag);
   }

   public EmbyClient.EmbyImageBytes getUserImage(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      String userId,
      String imageType,
      Integer width,
      Integer height,
      Integer maxWidth,
      Integer maxHeight,
      Integer quality,
      String tag
   ) {
      String normalizedImageType = this.normalizeImageType(imageType);
      String path = "/emby/Users/" + userId + "/Images/" + normalizedImageType;
      return this.getImage(serverConfig, path, width, height, maxWidth, maxHeight, quality, tag);
   }

   private EmbyClient.EmbyImageBytes getImage(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      String path,
      Integer width,
      Integer height,
      Integer maxWidth,
      Integer maxHeight,
      Integer quality,
      String tag
   ) {
      String url = this.buildBase(serverConfig.url(), path);
      HttpRequest request = HttpRequest.get(url)
         .header("X-Emby-Token", serverConfig.apiKey())
         .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
         .timeout(10000);
      if (width != null && width > 0) {
         request.form("width", width);
      }

      if (height != null && height > 0) {
         request.form("height", height);
      }

      if (maxWidth != null && maxWidth > 0) {
         request.form("maxWidth", maxWidth);
      }

      if (maxHeight != null && maxHeight > 0) {
         request.form("maxHeight", maxHeight);
      }

      if (quality != null && quality > 0) {
         request.form("quality", quality);
      }

      if (StrUtil.isNotBlank(tag)) {
         request.form("tag", tag);
      }

      HttpResponse response = request.execute();
      if (response.getStatus() >= 200 && response.getStatus() < 300) {
         String contentType = response.header(Header.CONTENT_TYPE);
         return new EmbyClient.EmbyImageBytes(response.bodyBytes(), StrUtil.blankToDefault(contentType, "image/jpeg"));
      } else if (response.getStatus() == 404) {
         throw new EmbyClient.EmbyImageNotFoundException(response.body());
      } else {
         throw new RuntimeException("Emby 图片请求失败: " + response.getStatus() + " - " + response.body());
      }
   }

   public JSONObject getLatestLibrary(int limit) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      return this.getLatestLibrary(this.getCurrentServerConfig(), embyUser, limit);
   }

   public JSONObject getLatestLibrary(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, int limit) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      return this.getJsonWithConfigCached(
         "/emby/Users/" + queryUserId + "/Items",
         Map.of(
            "Recursive",
            true,
            "IncludeItemTypes",
            "Movie,Series",
            "SortBy",
            "DateCreated",
            "SortOrder",
            "Descending",
            "Limit",
            limit,
            "EnableUserData",
            false,
            "Fields",
            "PrimaryImageAspectRatio,Overview,Studios,Genres,CommunityRating,ProductionYear,ParentId,Type,ImageTags,BackdropImageTags,PremiereDate,DateCreated"
         ),
         serverConfig,
         60L,
         900L
      );
   }

   public JSONObject getLibraryPage(
      int startIndex,
      int limit,
      String publisher,
      String includeItemTypes,
      String genres,
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      EmbyUser user
   ) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      Map<String, Object> params = new HashMap<>();
      params.put("Recursive", true);
      params.put("IncludeItemTypes", this.normalizeIncludeItemTypes(includeItemTypes));
      params.put("SortBy", "DateCreated");
      params.put("SortOrder", "Descending");
      params.put("StartIndex", startIndex);
      params.put("Limit", limit);
      params.put("EnableUserData", false);
      params.put(
         "Fields",
         "PrimaryImageAspectRatio,Overview,Studios,Genres,CommunityRating,ProductionYear,ParentId,Type,ImageTags,PrimaryImageTag,BackdropImageTags,PremiereDate,DateCreated,SeriesName,SeriesId,SeriesPrimaryImageTag,Seasons,RunTimeTicks,People,MediaSources,MediaStreams"
      );
      if (StrUtil.isNotBlank(publisher)) {
         params.put("StudioIds", publisher);
      }

      if (StrUtil.isNotBlank(genres)) {
         params.put("Genres", genres);
      }

      return this.getJsonWithConfigCached("/emby/Users/" + queryUserId + "/Items", params, serverConfig, 45L, 600L);
   }

   private String normalizeIncludeItemTypes(String includeItemTypes) {
      return StrUtil.isNotBlank(includeItemTypes) ? includeItemTypes : "Movie,Series";
   }

   private JSONObject getJsonWithConfig(String path, Map<String, Object> params, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      String url = this.buildBase(serverConfig.url(), path);
      HttpRequest request = HttpRequest.get(url).header("X-Emby-Token", serverConfig.apiKey()).header("Accept", "application/json").timeout(30000);
      if (params != null && !params.isEmpty()) {
         request.form(params);
      }

      HttpResponse resp = request.execute();
      String body = resp.body();
      if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
         return JSON.parseObject(body);
      } else {
         throw new RuntimeException("Emby 请求失败: " + resp.getStatus() + " - " + body);
      }
   }

   private JSONArray getJsonArrayWithConfig(String path, Map<String, Object> params, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      String url = this.buildBase(serverConfig.url(), path);
      HttpRequest request = HttpRequest.get(url).header("X-Emby-Token", serverConfig.apiKey()).header("Accept", "application/json").timeout(30000);
      if (params != null && !params.isEmpty()) {
         request.form(params);
      }

      HttpResponse resp = request.execute();
      String body = resp.body();
      if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
         return JSON.parseArray(body);
      } else {
         throw new RuntimeException("Emby 请求失败: " + resp.getStatus() + " - " + body);
      }
   }

   private JSONObject getJsonWithConfigCached(
      String path, Map<String, Object> params, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, long freshTtlSeconds, long staleTtlSeconds
   ) {
      String cacheKey = this.buildJsonCacheKey(path, params, serverConfig);
      String cached = this.readCache(cacheKey);
      if (StrUtil.isNotBlank(cached)) {
         return JSON.parseObject(cached);
      } else {
         try {
            JSONObject result = this.getJsonWithConfig(path, params, serverConfig);
            this.writeCache(cacheKey, result, freshTtlSeconds, staleTtlSeconds);
            return result;
         } catch (Exception var12) {
            String stale = this.readCache(cacheKey + ":stale");
            if (StrUtil.isNotBlank(stale)) {
               log.warn("Emby 请求失败，返回旧缓存: serverId={}, path={}, error={}", serverConfig != null ? serverConfig.id() : null, path, var12.getMessage());
               return JSON.parseObject(stale);
            } else {
               throw var12;
            }
         }
      }
   }

   private String buildJsonCacheKey(String path, Map<String, Object> params, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      String raw = (serverConfig != null ? serverConfig.id() : "default") + ":" + path + ":" + JSON.toJSONString(params == null ? Map.of() : params);
      return "foam:emby:client:v1:" + DigestUtil.sha256Hex(raw);
   }

   private String readCache(String key) {
      try {
         return this.stringRedisTemplate.opsForValue().get(key);
      } catch (Exception var3) {
         log.debug("读取 Emby 页面缓存失败: key={}, error={}", key, var3.getMessage());
         return null;
      }
   }

   private void writeCache(String key, JSONObject value, long freshTtlSeconds, long staleTtlSeconds) {
      if (value != null) {
         try {
            String json = value.toJSONString();
            this.stringRedisTemplate.opsForValue().set(key, json, freshTtlSeconds, TimeUnit.SECONDS);
            this.stringRedisTemplate.opsForValue().set(key + ":stale", json, staleTtlSeconds, TimeUnit.SECONDS);
         } catch (Exception var8) {
            log.debug("写入 Emby 页面缓存失败: key={}, error={}", key, var8.getMessage());
         }
      }
   }

   private String getCopyfromuseridResult() {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      String copyfromuserid = "";
      if (embyUser.getIsAdmin() == 1) {
         copyfromuserid = this.getCopyfromuserid();
      } else {
         copyfromuserid = embyUser.getEmbyUserId();
      }

      return copyfromuserid;
   }

   public JSONObject searchLibrary(String keyword, int limit) {
      return this.searchLibrary(keyword, limit, null, null);
   }

   public JSONObject searchLibrary(String keyword, int limit, String publisher, String includeItemTypes) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfig(embyUser);
      return this.searchLibraryWithConfig(serverConfig, embyUser, keyword, limit, publisher, includeItemTypes);
   }

   public JSONObject searchLibrary(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String keyword, int limit) {
      return this.searchLibrary(serverConfig, user, keyword, limit, null, null);
   }

   public JSONObject searchLibraryForSystem(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String keyword, int limit, String includeItemTypes) {
      return this.searchLibraryWithConfig(serverConfig, null, keyword, limit, null, includeItemTypes);
   }

   public JSONObject searchLibrary(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String keyword, int limit, String publisher, String includeItemTypes
   ) {
      return this.searchLibraryWithConfig(serverConfig, user, keyword, limit, publisher, includeItemTypes);
   }

   private JSONObject searchLibraryWithConfig(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String keyword, int limit, String publisher, String includeItemTypes
   ) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      Map<String, Object> params = new HashMap<>();
      params.put("Recursive", true);
      params.put("IncludeItemTypes", this.normalizeIncludeItemTypes(includeItemTypes));
      params.put("SearchTerm", keyword);
      params.put("Limit", limit);
      params.put("EnableUserData", false);
      params.put(
         "Fields",
         "PrimaryImageAspectRatio,Overview,Studios,Genres,CommunityRating,ProductionYear,ParentId,Type,ImageTags,PrimaryImageTag,BackdropImageTags,PremiereDate,DateCreated,SeriesName,SeriesId,SeriesPrimaryImageTag,Seasons,RunTimeTicks,People,MediaSources,MediaStreams"
      );
      if (StrUtil.isNotBlank(publisher)) {
         params.put("StudioIds", publisher);
      }

      return this.getJsonWithConfigCached("/emby/Users/" + queryUserId + "/Items", params, serverConfig, 30L, 300L);
   }

   public JSONObject getItemDetail(String id) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfig(embyUser);
      return this.getItemDetail(serverConfig, embyUser, id);
   }

   public JSONObject getItemDetail(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String id) {
      return this.getItemDetail(serverConfig, null, id);
   }

   public JSONObject getItemDetail(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String id) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      return this.getJsonWithConfig(
         "/emby/Users/" + queryUserId + "/Items/" + id,
         Map.of(
            "Fields",
            "Overview,Genres,Taglines,Studios,CommunityRating,ProductionYear,RunTimeTicks,PremiereDate,ParentId,Type,ImageTags,BackdropImageTags,People,RemoteTrailers,ProviderIds,MediaSources,MediaStreams"
         ),
         serverConfig
      );
   }

   public JSONObject getSeasonList(String seriesId) {
      String url = this.buildBase("/emby/Shows/" + seriesId + "/Seasons");
      HttpRequest request = HttpRequest.get(url)
         .header("X-Emby-Token", this.getApiKey())
         .header("X-Emby-Client", "Mist Web")
         .header("X-Emby-Device-Name", "Mist App")
         .header("X-Emby-Device-Id", "mist-client")
         .header("X-Emby-Client-Version", "1.0.0")
         .header("X-Emby-Language", "zh-cn")
         .header("Accept", "application/json")
         .timeout(30000)
         .form("UserId", this.getCopyfromuseridResult())
         .form("Fields", "BasicSyncInfo,CanDelete,CanDownload,PrimaryImageAspectRatio,Overview,ProductionYear,CommunityRating,ImageTags,ParentIndexNumber")
         .form("IsSpecialSeason", false)
         .form("EnableUserData", false)
         .form("EnableTotalRecordCount", false)
         .form("EnableImages", true);
      HttpResponse resp = request.execute();
      String body = resp.body();
      log.info("Emby Seasons GET status={}, seriesId={}", resp.getStatus(), seriesId);
      if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
         return JSON.parseObject(body);
      } else {
         throw new RuntimeException("Emby getSeasonList 请求失败: " + resp.getStatus());
      }
   }

   public JSONObject getSeasonList(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, EmbyUser user, String seriesId) {
      String url = this.buildBase(serverConfig.url(), "/emby/Shows/" + seriesId + "/Seasons");
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      HttpRequest request = HttpRequest.get(url)
         .header("X-Emby-Token", serverConfig.apiKey())
         .header("X-Emby-Client", "Mist Web")
         .header("X-Emby-Device-Name", "Mist App")
         .header("X-Emby-Device-Id", "mist-client")
         .header("X-Emby-Client-Version", "1.0.0")
         .header("X-Emby-Language", "zh-cn")
         .header("Accept", "application/json")
         .timeout(30000)
         .form("UserId", queryUserId)
         .form("Fields", "BasicSyncInfo,CanDelete,CanDownload,PrimaryImageAspectRatio,Overview,ProductionYear,CommunityRating,ImageTags,ParentIndexNumber")
         .form("IsSpecialSeason", false)
         .form("EnableUserData", false)
         .form("EnableTotalRecordCount", false)
         .form("EnableImages", true);
      HttpResponse resp = request.execute();
      String body = resp.body();
      log.info("Emby Seasons GET status={}, serverId={}, seriesId={}", resp.getStatus(), serverConfig.id(), seriesId);
      if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
         return JSON.parseObject(body);
      } else {
         throw new RuntimeException("Emby getSeasonList 请求失败: " + resp.getStatus());
      }
   }

   public JSONObject getEpisodeList(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      EmbyUser user,
      String seriesId,
      String seasonId,
      Integer seasonIndex,
      Integer startIndex,
      Integer limit
   ) {
      String queryUserId = this.resolveQueryUserId(serverConfig, user);
      Map<String, Object> params = new HashMap<>();
      params.put("ParentId", StrUtil.isNotBlank(seasonId) ? seasonId : seriesId);
      params.put("Recursive", true);
      params.put("IncludeItemTypes", "Episode");
      params.put("SortBy", "ParentIndexNumber,IndexNumber");
      params.put("SortOrder", "Ascending");
      params.put(
         "Fields",
         "PrimaryImageAspectRatio,Overview,CommunityRating,ProductionYear,RunTimeTicks,ParentId,Type,ImageTags,PrimaryImageTag,BackdropImageTags,PremiereDate,DateCreated,SeriesName,SeriesId,SeriesPrimaryImageTag,SeasonId,SeasonName,IndexNumber,ParentIndexNumber,MediaSources,MediaStreams"
      );
      params.put("EnableUserData", false);
      params.put("EnableImages", true);
      params.put("EnableTotalRecordCount", true);
      params.put("StartIndex", Math.max(startIndex == null ? 0 : startIndex, 0));
      params.put("Limit", Math.min(Math.max(limit == null ? 100 : limit, 1), 500));
      if (StrUtil.isBlank(seasonId) && seasonIndex != null && seasonIndex > 0) {
         params.put("ParentIndexNumber", seasonIndex);
      }

      return this.getJsonWithConfigCached("/emby/Users/" + queryUserId + "/Items", params, serverConfig, 45L, 600L);
   }

   public void replaceItemImage(String itemId, String imageType, MultipartFile file, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (StrUtil.isBlank(itemId)) {
         throw new IllegalArgumentException("itemId cannot be blank");
      } else if (file != null && !file.isEmpty()) {
         try {
            String normalizedImageType = this.normalizeImageType(imageType);
            String url = this.buildBase(serverConfig.url(), StrUtil.format("/Items/{}/Images/{}", new Object[]{itemId, normalizedImageType}));
            byte[] bodyBytes = file.getBytes();
            String base64Str = cn.hutool.core.codec.Base64.encode(bodyBytes);
            HttpResponse response = HttpRequest.post(url)
               .header("X-Emby-Token", serverConfig.apiKey())
               .header(Header.CONTENT_TYPE, StrUtil.emptyToDefault(file.getContentType(), "image/jpeg"))
               .body(base64Str)
               .execute();
            int status = response.getStatus();
            log.info("Emby replace cover POST status={}, serverId={}, itemId={}, imageType={}", status, serverConfig.id(), itemId, normalizedImageType);
            if (status < 200 || status >= 300) {
               throw new RuntimeException("替换封面失败: " + status);
            }
         } catch (IOException var11) {
            throw new RuntimeException("读取上传文件失败", var11);
         }
      } else {
         throw new IllegalArgumentException("file cannot be empty");
      }
   }

   private String normalizeImageType(String imageType) {
      if (StrUtil.isBlank(imageType)) {
         return "Primary";
      } else {
         for (String allowed : ALLOWED_IMAGE_TYPES) {
            if (allowed.equalsIgnoreCase(imageType)) {
               return allowed;
            }
         }

         log.warn("Unknown imageType '{}', falling back to Primary", imageType);
         return "Primary";
      }
   }

   @Generated
   public EmbyClient(final EmbyInfoCacheManagerUtils embyInfoCacheManager, final StringRedisTemplate stringRedisTemplate) {
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.stringRedisTemplate = stringRedisTemplate;
   }

   public static record EmbyImageBytes(byte[] bytes, String contentType) {
   }

   public static class EmbyImageNotFoundException extends RuntimeException {
      public EmbyImageNotFoundException(String message) {
         super(message);
      }
   }
}
