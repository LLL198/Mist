package com.una.embyhub.foam.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.foam.client.EmbyClient;
import com.una.embyhub.model.dto.response.emby.EmbyUrlResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.EmbyUserService;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.SystemServiceApi;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/foam/emby"})
public class FoamEmbyController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(FoamEmbyController.class);
   private static final String SPEED_TEST_LIMIT_KEY_PREFIX = "foam:emby:speed-test-media:";
   private static final String SPEED_TEST_ADMIN_AUTH_LIMIT_KEY_PREFIX = "foam:emby:speed-test-admin-auth:";
   private static final long SPEED_TEST_LIMIT_WINDOW_SECONDS = 60L;
   private static final long SPEED_TEST_LIMIT_MAX_REQUESTS = 6L;
   private static final long SPEED_TEST_ADMIN_AUTH_LIMIT_MAX_REQUESTS = 5L;
   private final EmbyClient embyClient;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final EmbyUserService embyUserService;
   private final StringRedisTemplate stringRedisTemplate;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;

   @GetMapping({"/library/counts"})
   public JSONObject libraryCounts(
      @RequestParam(value = "embyInfoId",required = false) Long embyInfoId,
      @RequestParam(value = "includeToday",defaultValue = "false") boolean includeToday,
      @RequestParam(value = "timezone",required = false) String timezone
   ) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
      return includeToday ? this.embyClient.getLibraryCounts(serverConfig, embyUser, timezone) : this.embyClient.getLibraryCounts(serverConfig, embyUser);
   }

   @GetMapping({"/library/media-folders"})
   @SaCheckPermission({"admin"})
   public JSONObject mediaFolders(@RequestParam("embyInfoId") Long embyInfoId) {
      return this.embyClient.getMediaFolders(this.embyInfoCacheManager.getRequiredConfigById(embyInfoId));
   }

   @GetMapping({"/library/items"})
   @SaCheckPermission({"admin"})
   public JSONObject libraryItems(
      @RequestParam("embyInfoId") Long embyInfoId, @RequestParam("parentId") String parentId, @RequestParam(value = "limit",defaultValue = "20") Integer limit
   ) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      return this.embyClient.getLibraryItems(this.embyInfoCacheManager.getRequiredConfigById(embyInfoId), embyUser, parentId, limit == null ? 20 : limit);
   }

   @GetMapping({"/library/speed-test-media"})
   public JSONObject speedTestMedia(@RequestParam(value = "embyInfoId",required = false) Long embyInfoId, HttpServletResponse response) {
      this.ensureLineSpeedTestEnabled();
      response.setHeader("Cache-Control", "no-store, no-cache, max-age=0");
      response.setHeader("Pragma", "no-cache");
      response.setDateHeader("Expires", 0L);
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
      this.checkSpeedTestRateLimit(embyUser, serverConfig);
      return this.embyClient.getSpeedTestMedia(serverConfig, embyUser);
   }

   @PostMapping({"/library/speed-test-media/admin-auth"})
   @SaCheckPermission({"admin"})
   public JSONObject speedTestMediaWithAdminCredential(@RequestBody FoamEmbyController.AdminSpeedTestMediaRequest request, HttpServletResponse response) {
      this.ensureLineSpeedTestEnabled();
      response.setHeader("Cache-Control", "no-store, no-cache, max-age=0");
      response.setHeader("Pragma", "no-cache");
      response.setDateHeader("Expires", 0L);
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      Long embyInfoId = request == null ? null : request.embyInfoId();
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
      this.checkAdminSpeedTestAuthRateLimit(embyUser, serverConfig);
      String username = request != null && request.username() != null ? request.username().trim() : "";
      String password = request != null && request.password() != null ? request.password() : "";
      if (!username.isBlank() && !password.isBlank()) {
         return this.embyClient.getSpeedTestMediaWithCredential(serverConfig, embyUser, username, password);
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "请输入 Emby 账号和密码");
      }
   }

   @GetMapping({"/items/{id}/image/{imageType}"})
   public void itemImage(
      @PathVariable("id") String id,
      @PathVariable("imageType") String imageType,
      @RequestParam("embyInfoId") Long embyInfoId,
      @RequestParam(value = "imageIndex",required = false) Integer imageIndex,
      @RequestParam(value = "width",required = false) Integer width,
      @RequestParam(value = "height",required = false) Integer height,
      @RequestParam(value = "maxWidth",required = false) Integer maxWidth,
      @RequestParam(value = "maxHeight",required = false) Integer maxHeight,
      @RequestParam(value = "quality",defaultValue = "90") Integer quality,
      @RequestParam(value = "tag",required = false) String tag,
      HttpServletResponse response
   ) throws IOException {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");

      try {
         EmbyClient.EmbyImageBytes image = this.embyClient
            .getItemImage(this.resolveAccessibleServerConfig(embyInfoId, embyUser), id, imageType, imageIndex, width, height, maxWidth, maxHeight, quality, tag);
         this.writeImageResponse(response, image);
      } catch (EmbyClient.EmbyImageNotFoundException var14) {
         response.setStatus(404);
      }
   }

   @GetMapping({"/users/{id}/image/{imageType}"})
   public void userImage(
      @PathVariable("id") String id,
      @PathVariable("imageType") String imageType,
      @RequestParam("embyInfoId") Long embyInfoId,
      @RequestParam(value = "width",required = false) Integer width,
      @RequestParam(value = "height",required = false) Integer height,
      @RequestParam(value = "maxWidth",required = false) Integer maxWidth,
      @RequestParam(value = "maxHeight",required = false) Integer maxHeight,
      @RequestParam(value = "quality",defaultValue = "90") Integer quality,
      @RequestParam(value = "tag",required = false) String tag,
      HttpServletResponse response
   ) throws IOException {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");

      try {
         EmbyClient.EmbyImageBytes image = this.embyClient
            .getUserImage(this.resolveAccessibleServerConfig(embyInfoId, embyUser), id, imageType, width, height, maxWidth, maxHeight, quality, tag);
         this.writeImageResponse(response, image);
      } catch (EmbyClient.EmbyImageNotFoundException var13) {
         response.setStatus(404);
      }
   }

   private void writeImageResponse(HttpServletResponse response, EmbyClient.EmbyImageBytes image) throws IOException {
      response.setStatus(200);
      response.setContentType(image.contentType());
      response.setHeader("Cache-Control", CacheControl.maxAge(10L, TimeUnit.MINUTES).cachePublic().getHeaderValue());

      try {
         response.getOutputStream().write(image.bytes());
         response.flushBuffer();
      } catch (IOException var4) {
         if (this.isClientAbort(var4)) {
            log.debug("客户端已断开图片响应: {}", var4.getMessage());
         } else {
            throw var4;
         }
      }
   }

   private boolean isClientAbort(Throwable throwable) {
      for (Throwable current = throwable; current != null; current = current.getCause()) {
         String className = current.getClass().getName();
         String message = current.getMessage();
         String lowerMessage = message == null ? "" : message.toLowerCase(Locale.ROOT);
         if (className.contains("ClientAbortException") || lowerMessage.contains("broken pipe") || lowerMessage.contains("connection reset")) {
            return true;
         }
      }

      return false;
   }

   @GetMapping({"/library/latest"})
   public JSONObject latestLibrary(@RequestParam(defaultValue = "6") Integer limit, @RequestParam(value = "embyInfoId",required = false) Long embyInfoId) {
      if (limit == null || limit <= 0) {
         limit = 6;
      }

      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
      return this.embyClient.getLatestLibrary(serverConfig, embyUser, limit);
   }

   @GetMapping({"/library/page"})
   public JSONObject libraryPage(
      @RequestParam(defaultValue = "0") Integer startIndex,
      @RequestParam(defaultValue = "18") Integer limit,
      @RequestParam(value = "publisher",required = false) String publisher,
      @RequestParam(value = "includeItemTypes",defaultValue = "Movie,Series") String includeItemTypes,
      @RequestParam(value = "genres",required = false) String genres,
      @RequestParam(value = "embyInfoId",required = false) Long embyInfoId
   ) {
      if (startIndex == null || startIndex < 0) {
         startIndex = 0;
      }

      if (limit == null || limit <= 0) {
         limit = 18;
      }

      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
      return this.embyClient.getLibraryPage(startIndex, limit, publisher, includeItemTypes, genres, serverConfig, embyUser);
   }

   @GetMapping({"/library/search"})
   public JSONObject searchLibrary(
      @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "50") Integer limit,
      @RequestParam(value = "publisher",required = false) String publisher,
      @RequestParam(value = "includeItemTypes",defaultValue = "Movie,Series") String includeItemTypes,
      @RequestParam(value = "embyInfoId",required = false) Long embyInfoId
   ) {
      JSONObject result = new JSONObject();
      if (keyword != null && !keyword.isBlank()) {
         if (limit == null || limit <= 0) {
            limit = 50;
         }

         EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
         return this.embyClient.searchLibrary(serverConfig, embyUser, keyword, limit, publisher, includeItemTypes);
      } else {
         result.put("Items", new Object[0]);
         result.put("TotalRecordCount", Integer.valueOf(0));
         return result;
      }
   }

   @GetMapping({"/items/{id}"})
   public JSONObject itemDetail(@PathVariable("id") String id, @RequestParam(value = "embyInfoId",required = false) Long embyInfoId) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      return this.embyClient.getItemDetail(this.resolveAccessibleServerConfig(embyInfoId, embyUser), embyUser, id);
   }

   @PostMapping({"/items/{id}/cover"})
   @SaCheckPermission({"admin"})
   public JSONObject replaceItemCover(
      @PathVariable("id") String id,
      @RequestParam(value = "imageType",defaultValue = "Primary") String imageType,
      @RequestParam("file") MultipartFile file,
      @RequestParam("embyInfoId") Long embyInfoId
   ) {
      this.embyClient.replaceItemImage(id, imageType, file, this.embyInfoCacheManager.getRequiredConfigById(embyInfoId));
      JSONObject result = new JSONObject();
      result.put("message", "ok");
      return result;
   }

   @GetMapping({"/shows/{seriesId}/seasons"})
   public JSONObject seasonList(@PathVariable("seriesId") String seriesId, @RequestParam(value = "embyInfoId",required = false) Long embyInfoId) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
      return this.embyClient.getSeasonList(serverConfig, embyUser, seriesId);
   }

   @GetMapping({"/shows/{seriesId}/episodes"})
   public JSONObject episodeList(
      @PathVariable("seriesId") String seriesId,
      @RequestParam(value = "seasonId",required = false) String seasonId,
      @RequestParam(value = "seasonIndex",required = false) Integer seasonIndex,
      @RequestParam(value = "startIndex",defaultValue = "0") Integer startIndex,
      @RequestParam(value = "limit",defaultValue = "100") Integer limit,
      @RequestParam(value = "embyInfoId",required = false) Long embyInfoId
   ) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveAccessibleServerConfig(embyInfoId, embyUser);
      return this.embyClient.getEpisodeList(serverConfig, embyUser, seriesId, seasonId, seasonIndex, startIndex, limit);
   }

   @GetMapping({"getEmbyUrl"})
   public EmbyUrlResponse getEmbyUrl() throws ApiException {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfig(embyUser);
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, serverConfig);
      SystemServiceApi systemServiceApi = new SystemServiceApi(apiClient);
      String id = systemServiceApi.getSystemInfo().getId();
      EmbyUrlResponse embyUrlResponse = new EmbyUrlResponse();
      embyUrlResponse.setServerId(id);
      embyUrlResponse.setUrl(serverConfig.url());
      return embyUrlResponse;
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveAccessibleServerConfig(Long embyInfoId, EmbyUser user) {
      if (embyInfoId == null) {
         return this.embyInfoCacheManager.getRequiredConfig(user);
      } else if (this.canAccessServer(user, embyInfoId)) {
         return this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
      } else {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   private void ensureLineSpeedTestEnabled() {
      if (this.configCacheLoaderUtils.getConfigValue("line_speed_test_enabled") == null) {
         throw new BizException(ResponseStatusEnum.FORBIDDEN.getCode(), "系统未开启线路测速");
      }
   }

   private void checkSpeedTestRateLimit(EmbyUser user, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      this.checkRateLimit(user, serverConfig, "foam:emby:speed-test-media:", 6L, "测速请求过于频繁，请稍后再试");
   }

   private void checkAdminSpeedTestAuthRateLimit(EmbyUser user, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      this.checkRateLimit(user, serverConfig, "foam:emby:speed-test-admin-auth:", 5L, "Emby 授权测速请求过于频繁，请稍后再试");
   }

   private void checkRateLimit(EmbyUser user, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String keyPrefix, long maxRequests, String message) {
      if (user != null && user.getId() != null) {
         String serverId = serverConfig.id() == null ? "default" : String.valueOf(serverConfig.id());
         String key = keyPrefix + user.getId() + ":" + serverId;

         try {
            Long used = this.stringRedisTemplate.opsForValue().increment(key);
            if (used != null && used == 1L) {
               this.stringRedisTemplate.expire(key, Duration.ofSeconds(60L));
            }

            if (used != null && used > maxRequests) {
               throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
            }
         } catch (BizException var10) {
            throw var10;
         } catch (Exception var11) {
            log.warn("测速接口限流检查失败: userId={}, serverId={}, error={}", user.getId(), serverId, var11.getMessage());
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "测速限流检查失败，请稍后再试");
         }
      } else {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      }
   }

   private boolean canAccessServer(EmbyUser user, Long embyInfoId) {
      if (user == null || embyInfoId == null) {
         return false;
      } else {
         return StpUtil.hasPermission("admin") ? true : this.embyUserService.listAccessibleEmbyInfoIds(user).contains(embyInfoId);
      }
   }

   @Generated
   public FoamEmbyController(
      final EmbyClient embyClient,
      final EmbyInfoCacheManagerUtils embyInfoCacheManager,
      final EmbyUserService embyUserService,
      final StringRedisTemplate stringRedisTemplate,
      final ConfigCacheLoaderUtils configCacheLoaderUtils
   ) {
      this.embyClient = embyClient;
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.embyUserService = embyUserService;
      this.stringRedisTemplate = stringRedisTemplate;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
   }

   public static record AdminSpeedTestMediaRequest(Long embyInfoId, String username, String password) {
   }
}
