package com.una.embyhub.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.diboot.core.binding.Binder;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.dto.request.telegram.TelegramLoginRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramRebindStartRequest;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindSessionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingActionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramLoginSessionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramRebindSessionResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.service.AdminMenuPermissionService;
import com.una.embyhub.service.EmbyUserIdentityUtils;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.TelegramAuthService;
import com.una.embyhub.service.TelegramBindingManager;
import com.una.embyhub.service.TelegramBindingReviewNotifier;
import com.una.embyhub.service.TelegramBindingReviewService;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TelegramAuthServiceImpl implements TelegramAuthService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramAuthServiceImpl.class);
   private final TelegramClientUtils telegramClientUtils;
   private final EmbyUserMapper embyUserMapper;
   private final UserOauthBindingMapper userOauthBindingMapper;
   private final RedisTemplate<String, Object> redisTemplate;
   private final EmbyUserService embyUserService;
   private final AdminMenuPermissionService adminMenuPermissionService;
   private final TelegramBindingManager telegramBindingManager;
   private final TelegramBindingReviewService telegramBindingReviewService;
   private final TelegramBindingReviewNotifier telegramBindingReviewNotifier;
   private static final String LOGIN_SESSION_PREFIX = "tg:login:session:";
   private static final String LOGIN_COMPLETION_PREFIX = "tg:login:completion:";
   private static final String BIND_SESSION_PREFIX = "tg:bind:session:";
   private static final String CREDENTIAL_BIND_SELECTION_PREFIX = "tg:bind:credential-selection:";
   private static final String REBIND_SESSION_PREFIX = "tg:rebind:session:";
   private static final String REBIND_RATE_USER_PREFIX = "tg:rebind:rate:user:";
   private static final String REBIND_RATE_TARGET_PREFIX = "tg:rebind:rate:target:";
   private static final long LOGIN_SESSION_TTL_MINUTES = 5L;
   private static final long BIND_SESSION_TTL_MINUTES = 10L;
   private static final long CREDENTIAL_BIND_SELECTION_TTL_MINUTES = 5L;
   private static final long REBIND_SESSION_TTL_MINUTES = 10L;
   private static final long REBIND_RATE_LIMIT_SECONDS = 60L;
   private static final int REBIND_MAX_VERIFY_ATTEMPTS = 5;
   private static final SecureRandom SECURE_RANDOM = new SecureRandom();
   private static final long AUTH_DATE_VALIDITY_SECONDS = 300L;
   private static final long AUTH_DATE_FUTURE_SKEW_SECONDS = 300L;
   private static final String OPAQUE_TOKEN_PATTERN = "^[0-9a-f]{32}$";

   @Override
   public boolean verifySignature(TelegramLoginRequest request) {
      if (request != null
         && request.getId() != null
         && request.getId() > 0L
         && StringUtils.hasText(request.getHash())
         && request.getHash().matches("^[0-9a-fA-F]{64}$")) {
         TelegramResponse telegramConfig = this.telegramClientUtils.getTelegramResponse();
         if (telegramConfig != null && StringUtils.hasText(telegramConfig.getBotToken())) {
            String botToken = telegramConfig.getBotToken();
            if (request.getAuth_date() == null) {
               return false;
            } else {
               long now = Instant.now().getEpochSecond();
               if (request.getAuth_date() <= now + 300L && now - request.getAuth_date() <= 300L) {
                  try {
                     Map<String, String> dataMap = new TreeMap<>();
                     dataMap.put("id", String.valueOf(request.getId()));
                     if (StringUtils.hasText(request.getFirst_name())) {
                        dataMap.put("first_name", request.getFirst_name());
                     }

                     if (StringUtils.hasText(request.getLast_name())) {
                        dataMap.put("last_name", request.getLast_name());
                     }

                     if (StringUtils.hasText(request.getUsername())) {
                        dataMap.put("username", request.getUsername());
                     }

                     if (StringUtils.hasText(request.getPhoto_url())) {
                        dataMap.put("photo_url", request.getPhoto_url());
                     }

                     dataMap.put("auth_date", String.valueOf(request.getAuth_date()));
                     StringBuilder dataCheckString = new StringBuilder();

                     for (Entry<String, String> entry : dataMap.entrySet()) {
                        if (dataCheckString.length() > 0) {
                           dataCheckString.append("\n");
                        }

                        dataCheckString.append(entry.getKey()).append("=").append(entry.getValue());
                     }

                     MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
                     byte[] secretKey = sha256.digest(botToken.getBytes(StandardCharsets.UTF_8));
                     Mac hmac = Mac.getInstance("HmacSHA256");
                     hmac.init(new SecretKeySpec(secretKey, "HmacSHA256"));
                     byte[] hashBytes = hmac.doFinal(dataCheckString.toString().getBytes(StandardCharsets.UTF_8));
                     byte[] suppliedHash = HexFormat.of().parseHex(request.getHash());
                     boolean valid = MessageDigest.isEqual(hashBytes, suppliedHash);
                     if (!valid) {
                        log.warn("Telegram 签名验证失败");
                     }

                     return valid;
                  } catch (Exception var14) {
                     log.error("Telegram 签名验证异常", (Throwable)var14);
                     return false;
                  }
               } else {
                  log.warn("Telegram 认证时间无效或已过期");
                  return false;
               }
            }
         } else {
            log.warn("Telegram Bot Token 未配置，无法验证签名");
            return false;
         }
      } else {
         return false;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse bind(TelegramLoginRequest request) {
      if (!this.verifySignature(request)) {
         throw new BizException("Telegram 签名验证失败");
      } else {
         EmbyUser sessionUser = (EmbyUser)StpUtil.getSession().get("user");
         if (sessionUser != null && sessionUser.getId() != null) {
            EmbyUser currentUser = this.telegramBindingManager.findUsableUser(sessionUser.getId());
            if (currentUser == null) {
               throw new BizException("当前账号不存在或已被禁用");
            } else {
               TelegramBindingActionResponse result = this.telegramBindingReviewService
                  .submitBind(currentUser, request.getId(), request.getUsername(), request.getPhoto_url(), "WEB", false);
               log.info("用户 {} 提交 Telegram 绑定: telegramId={}, status={}", currentUser.getEmbyUserName(), request.getId(), result.getStatus());
               return result;
            }
         } else {
            throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public EmbyUserCustomResponse login(TelegramLoginRequest request) {
      if (!this.verifySignature(request)) {
         throw new BizException("Telegram 签名验证失败");
      } else {
         EmbyUser user = this.telegramBindingManager.findBoundUser(request.getId(), true);
         if (user == null) {
            throw new BizException("未找到绑定的 Emby 账户，请先登录后绑定 Telegram");
         } else {
            StpUtil.login(user.getId());
            StpUtil.getSession().set("user", user);
            EmbyUserCustomResponse response = this.buildLoginResponse(user);
            log.info("用户 {} 通过 Telegram 登录成功", user.getEmbyUserName());
            return response;
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse unbind() {
      EmbyUser currentUser = (EmbyUser)StpUtil.getSession().get("user");
      if (currentUser == null) {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      } else {
         TelegramBindingActionResponse result = this.telegramBindingReviewService.submitUnbindByUserId(currentUser.getId(), "WEB");
         log.info("用户 {} 提交 Telegram 解绑: status={}", currentUser.getEmbyUserName(), result.getStatus());
         return result;
      }
   }

   @Override
   public TelegramRebindSessionResponse startRebind(TelegramRebindStartRequest request) {
      EmbyUser sessionUser = this.currentSessionUser();
      EmbyUser currentUser = this.telegramBindingManager.findUsableUser(sessionUser.getId());
      if (currentUser == null) {
         throw new BizException("当前账号不存在或已被禁用");
      } else if (StringUtils.hasText(request.getCurrentPassword())
         && MessageDigest.isEqual(this.safeBytes(currentUser.getEmbyUserPassword()), this.safeBytes(SaSecureUtil.md5(request.getCurrentPassword())))) {
         UserOauthBinding oldBinding = this.telegramBindingManager.findBindingByUserId(currentUser.getId());
         if (oldBinding != null && StringUtils.hasText(oldBinding.getProviderUserId())) {
            long oldTelegramUserId = this.parseTelegramUserId(oldBinding.getProviderUserId(), "当前 Telegram 绑定信息无效");
            long newTelegramUserId = request.getNewTelegramUserId();
            if (oldTelegramUserId == newTelegramUserId) {
               throw new BizException("新 Telegram 账号不能与当前绑定相同");
            } else if (this.telegramBindingReviewService.findPendingByUserId(currentUser.getId()) != null) {
               throw new BizException("当前已有 Telegram 申请等待审批，请先取消或等待处理");
            } else {
               this.telegramBindingManager.validateBind(currentUser, newTelegramUserId, true);
               String userRateKey = "tg:rebind:rate:user:" + currentUser.getId();
               String targetRateKey = "tg:rebind:rate:target:" + newTelegramUserId;
               if (!Boolean.TRUE.equals(this.redisTemplate.opsForValue().setIfAbsent(userRateKey, "1", 60L, TimeUnit.SECONDS))) {
                  throw new BizException("验证码发送过于频繁，请稍后再试");
               } else if (!Boolean.TRUE.equals(this.redisTemplate.opsForValue().setIfAbsent(targetRateKey, "1", 60L, TimeUnit.SECONDS))) {
                  this.redisTemplate.delete(userRateKey);
                  throw new BizException("该 Telegram 验证码发送过于频繁，请稍后再试");
               } else {
                  String verificationId = UUID.randomUUID().toString().replace("-", "");
                  String verificationCode = String.format("%06d", SECURE_RANDOM.nextInt(1000000));
                  String requestedTelegramUsername = this.normalizeTelegramUsername(request.getNewTelegramUsername());
                  TelegramBindingReviewNotifier.TelegramChatProfile telegramProfile = this.telegramBindingReviewNotifier
                     .resolveTelegramChatProfile(newTelegramUserId);
                  String newTelegramUsername = telegramProfile.resolved()
                     ? this.normalizeTelegramUsername(telegramProfile.username())
                     : requestedTelegramUsername;
                  TelegramAuthServiceImpl.RebindSession rebindSession = new TelegramAuthServiceImpl.RebindSession(
                     System.currentTimeMillis(),
                     currentUser.getId(),
                     oldTelegramUserId,
                     oldBinding.getProviderUsername(),
                     oldBinding.getProviderAvatar(),
                     newTelegramUserId,
                     newTelegramUsername,
                     this.hashRebindCode(verificationId, verificationCode),
                     0,
                     false
                  );
                  String sessionKey = "tg:rebind:session:" + verificationId;
                  this.redisTemplate.opsForValue().set(sessionKey, rebindSession, 10L, TimeUnit.MINUTES);

                  try {
                     this.telegramBindingReviewNotifier.sendRebindVerificationCode(newTelegramUserId, verificationCode, currentUser.getEmbyUserName(), 10L);
                  } catch (RuntimeException var19) {
                     this.redisTemplate.delete(List.of(sessionKey, userRateKey, targetRateKey));
                     throw var19;
                  }

                  log.info("用户 {} 发起 Telegram 换绑所有权验证: oldTelegramId={}, newTelegramId={}", currentUser.getEmbyUserName(), oldTelegramUserId, newTelegramUserId);
                  return this.rebindSessionResponse(verificationId, rebindSession);
               }
            }
         } else {
            throw new BizException("当前账号尚未绑定 Telegram，请使用绑定功能");
         }
      } else {
         throw new BizException(ResponseStatusEnum.PASSWORD_ERROR);
      }
   }

   @Override
   public TelegramRebindSessionResponse verifyRebind(String verificationId, String verificationCode) {
      EmbyUser currentUser = this.currentSessionUser();
      TelegramAuthServiceImpl.RebindSession session = this.loadRebindSession(verificationId, currentUser.getId());
      if (session.isVerified()) {
         return this.rebindSessionResponse(verificationId, session);
      } else if (session.getFailedAttempts() >= 5) {
         this.redisTemplate.delete("tg:rebind:session:" + verificationId);
         throw new BizException("验证码错误次数过多，请重新获取");
      } else {
         byte[] actualHash = this.hashRebindCode(verificationId, verificationCode);
         if (!MessageDigest.isEqual(session.getVerificationCodeHash(), actualHash)) {
            session.setFailedAttempts(session.getFailedAttempts() + 1);
            int remaining = 5 - session.getFailedAttempts();
            if (remaining <= 0) {
               this.redisTemplate.delete("tg:rebind:session:" + verificationId);
            } else {
               this.saveRebindSessionWithRemainingTtl(verificationId, session);
            }

            throw new BizException(remaining > 0 ? "验证码错误，还可尝试 " + remaining + " 次" : "验证码错误次数过多，请重新获取");
         } else {
            session.setVerified(true);
            this.saveRebindSessionWithRemainingTtl(verificationId, session);
            log.info("用户 {} 已完成新 Telegram 所有权验证: newTelegramId={}", currentUser.getEmbyUserName(), session.getNewTelegramUserId());
            return this.rebindSessionResponse(verificationId, session);
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse submitRebind(String verificationId) {
      EmbyUser sessionUser = this.currentSessionUser();
      TelegramAuthServiceImpl.RebindSession session = this.loadRebindSession(verificationId, sessionUser.getId());
      if (!session.isVerified()) {
         throw new BizException("请先完成新 Telegram 验证码校验");
      } else {
         EmbyUser currentUser = this.telegramBindingManager.findUsableUser(sessionUser.getId());
         if (currentUser == null) {
            throw new BizException("当前账号不存在或已被禁用");
         } else {
            TelegramBindingActionResponse result = this.telegramBindingReviewService
               .submitRebind(
                  currentUser,
                  session.getOldTelegramUserId(),
                  session.getOldTelegramUsername(),
                  session.getOldTelegramAvatar(),
                  session.getNewTelegramUserId(),
                  session.getNewTelegramUsername(),
                  null,
                  "WEB"
               );
            this.redisTemplate.delete("tg:rebind:session:" + verificationId);
            log.info(
               "用户 {} 提交 Telegram 换绑: oldTelegramId={}, newTelegramId={}, status={}",
               currentUser.getEmbyUserName(),
               session.getOldTelegramUserId(),
               session.getNewTelegramUserId(),
               result.getStatus()
            );
            return result;
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse refreshBindingProfile() {
      EmbyUser sessionUser = this.currentSessionUser();
      EmbyUser currentUser = this.telegramBindingManager.findUsableUser(sessionUser.getId());
      if (currentUser == null) {
         throw new BizException("当前账号不存在或已被禁用");
      } else {
         UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(currentUser.getId());
         if (binding != null && StringUtils.hasText(binding.getProviderUserId())) {
            long telegramUserId = this.parseTelegramUserId(binding.getProviderUserId(), "当前 Telegram 绑定信息无效");
            TelegramBindingReviewNotifier.TelegramChatProfile telegramProfile = this.telegramBindingReviewNotifier.resolveTelegramChatProfile(telegramUserId);
            if (!telegramProfile.resolved()) {
               throw new BizException("暂时无法读取 Telegram 资料，请确认已私聊并启动 Mist Bot");
            } else {
               this.telegramBindingManager.refreshBindingProfile(currentUser, telegramUserId, this.normalizeTelegramUsername(telegramProfile.username()), null);
               EmbyUserCustomResponse response = Binder.convertAndBindRelations(currentUser, EmbyUserCustomResponse.class);
               String message = StringUtils.hasText(telegramProfile.username()) ? "Telegram 用户名已同步" : "新 Telegram 没有公开用户名，已改为显示用户 ID";
               log.info("用户 {} 刷新 Telegram 绑定资料: telegramId={}, username={}", currentUser.getEmbyUserName(), telegramUserId, telegramProfile.username());
               return TelegramBindingActionResponse.completed(message, response);
            }
         } else {
            throw new BizException("当前账号尚未绑定 Telegram");
         }
      }
   }

   @Override
   public TelegramBindingReviewResponse pendingReview() {
      EmbyUser currentUser = (EmbyUser)StpUtil.getSession().get("user");
      if (currentUser != null && currentUser.getId() != null) {
         return this.telegramBindingReviewService.findPendingByUserId(currentUser.getId());
      } else {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingReviewResponse cancelReview(String reviewUuid) {
      EmbyUser currentUser = (EmbyUser)StpUtil.getSession().get("user");
      if (currentUser != null && currentUser.getId() != null) {
         TelegramBindingReviewResponse result = this.telegramBindingReviewService.cancelByUserId(currentUser.getId(), reviewUuid);
         log.info("用户 {} 自助取消 Telegram 审批申请: reviewUuid={}", currentUser.getEmbyUserName(), result.getReviewUuid());
         return result;
      } else {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      }
   }

   private EmbyUser currentSessionUser() {
      EmbyUser currentUser = (EmbyUser)StpUtil.getSession().get("user");
      if (currentUser != null && currentUser.getId() != null) {
         return currentUser;
      } else {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      }
   }

   private TelegramAuthServiceImpl.RebindSession loadRebindSession(String verificationId, Long expectedUserId) {
      if (!StringUtils.hasText(verificationId)) {
         throw new BizException("换绑会话不能为空");
      } else if (this.redisTemplate.opsForValue().get("tg:rebind:session:" + verificationId) instanceof TelegramAuthServiceImpl.RebindSession session) {
         if (expectedUserId != null && expectedUserId.equals(session.getUserId())) {
            return session;
         } else {
            throw new BizException("换绑会话不属于当前账号");
         }
      } else {
         throw new BizException("换绑验证已过期，请重新获取验证码");
      }
   }

   private void saveRebindSessionWithRemainingTtl(String verificationId, TelegramAuthServiceImpl.RebindSession session) {
      String key = "tg:rebind:session:" + verificationId;
      Long remainingSeconds = this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
      if (remainingSeconds != null && remainingSeconds > 0L) {
         this.redisTemplate.opsForValue().set(key, session, remainingSeconds, TimeUnit.SECONDS);
      } else {
         this.redisTemplate.delete(key);
         throw new BizException("换绑验证已过期，请重新获取验证码");
      }
   }

   private TelegramRebindSessionResponse rebindSessionResponse(String verificationId, TelegramAuthServiceImpl.RebindSession session) {
      String username = StringUtils.hasText(session.getNewTelegramUsername()) ? "@" + session.getNewTelegramUsername() : null;
      String display = username == null ? String.valueOf(session.getNewTelegramUserId()) : username + " / " + session.getNewTelegramUserId();
      return new TelegramRebindSessionResponse(verificationId, TimeUnit.MINUTES.toSeconds(10L), display, session.isVerified());
   }

   private byte[] hashRebindCode(String verificationId, String verificationCode) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         return digest.digest((verificationId + ":" + verificationCode).getBytes(StandardCharsets.UTF_8));
      } catch (Exception var4) {
         throw new BizException("验证码处理失败，请稍后重试");
      }
   }

   private byte[] safeBytes(String value) {
      return (value == null ? "" : value).getBytes(StandardCharsets.UTF_8);
   }

   private long parseTelegramUserId(String value, String errorMessage) {
      try {
         long telegramUserId = Long.parseLong(value);
         if (telegramUserId <= 0L) {
            throw new NumberFormatException();
         } else {
            return telegramUserId;
         }
      } catch (NumberFormatException var5) {
         throw new BizException(errorMessage);
      }
   }

   private String normalizeTelegramUsername(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         String normalized = value.trim().replaceFirst("^@", "");
         if (!normalized.matches("[A-Za-z0-9_]{5,32}")) {
            throw new BizException("Telegram 用户名格式无效");
         } else {
            return normalized;
         }
      }
   }

   @Override
   public TelegramLoginSessionResponse generateLoginSession() {
      String sessionId = UUID.randomUUID().toString().replace("-", "");
      String clientToken = UUID.randomUUID().toString().replace("-", "");
      String loginUrl = this.buildBotDeepLink("login", sessionId);
      TelegramAuthServiceImpl.LoginSession session = new TelegramAuthServiceImpl.LoginSession(true);
      session.setClientToken(clientToken);
      this.redisTemplate.opsForValue().set("tg:login:session:" + sessionId, session, 5L, TimeUnit.MINUTES);
      log.info("生成 Telegram 登录会话: {}", sessionId);
      return new TelegramLoginSessionResponse(sessionId, clientToken, loginUrl);
   }

   @Override
   public EmbyUserCustomResponse checkLoginStatus(String sessionId, String clientToken) {
      if (this.isOpaqueToken(sessionId) && this.isOpaqueToken(clientToken)) {
         String key = "tg:login:session:" + sessionId;
         TelegramAuthServiceImpl.LoginSession session = (TelegramAuthServiceImpl.LoginSession)this.redisTemplate.opsForValue().get(key);
         if (session == null) {
            return null;
         } else if (!this.matchesClientToken(session.getClientToken(), clientToken)) {
            log.warn("Telegram 登录会话轮询 token 不匹配: sessionId={}", sessionId);
            return null;
         } else if (!session.failed && session.pendingUserId == null) {
            EmbyUserCustomResponse pendingResponse = new EmbyUserCustomResponse();
            pendingResponse.setUserStatus(-2);
            return pendingResponse;
         } else {
            TelegramAuthServiceImpl.LoginSession terminalSession = (TelegramAuthServiceImpl.LoginSession)this.redisTemplate.opsForValue().getAndDelete(key);
            if (terminalSession == null || !this.matchesClientToken(terminalSession.getClientToken(), clientToken)) {
               return null;
            } else if (terminalSession.failed) {
               return this.failedLoginResponse(terminalSession.errorMessage);
            } else {
               EmbyUser pendingUser = this.telegramBindingManager.findUsableUser(terminalSession.pendingUserId);
               if (pendingUser == null) {
                  return this.failedLoginResponse("绑定的用户不存在或已被禁用");
               } else {
                  StpUtil.login(pendingUser.getId());
                  StpUtil.getSession().set("user", pendingUser);
                  EmbyUserCustomResponse response = this.buildLoginResponse(pendingUser);
                  log.info("用户 {} 通过 Telegram 深链接登录成功 (checkLoginStatus)", pendingUser.getEmbyUserName());
                  return response;
               }
            }
         }
      } else {
         return null;
      }
   }

   private boolean isOpaqueToken(String value) {
      return StringUtils.hasText(value) && value.matches("^[0-9a-f]{32}$");
   }

   private EmbyUserCustomResponse failedLoginResponse(String message) {
      EmbyUserCustomResponse response = new EmbyUserCustomResponse();
      response.setUserStatus(-1);
      response.setRemarks(message);
      return response;
   }

   private boolean matchesClientToken(String expected, String actual) {
      if (StringUtils.hasText(expected) && StringUtils.hasText(actual)) {
         byte[] expectedBytes = expected.getBytes(StandardCharsets.UTF_8);
         byte[] actualBytes = actual.getBytes(StandardCharsets.UTF_8);
         return MessageDigest.isEqual(expectedBytes, actualBytes);
      } else {
         return false;
      }
   }

   @Override
   public TelegramBindSessionResponse generateBindSession() {
      EmbyUser currentUser = (EmbyUser)StpUtil.getSession().get("user");
      if (currentUser != null && currentUser.getId() != null) {
         String sessionId = UUID.randomUUID().toString().replace("-", "");
         String bindUrl = this.buildBotDeepLink("bind", sessionId);
         TelegramAuthServiceImpl.BindSession session = new TelegramAuthServiceImpl.BindSession(System.currentTimeMillis(), currentUser.getId());
         this.redisTemplate.opsForValue().set("tg:bind:session:" + sessionId, session, 10L, TimeUnit.MINUTES);
         log.info("生成 Telegram 绑定会话: userId={}, sessionId={}", currentUser.getId(), sessionId);
         return new TelegramBindSessionResponse(bindUrl);
      } else {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public EmbyUserCustomResponse completeLogin(String sessionId, Long telegramUserId) {
      if (this.isOpaqueToken(sessionId) && telegramUserId != null && telegramUserId > 0L) {
         String key = "tg:login:session:" + sessionId;
         TelegramAuthServiceImpl.LoginSession session = (TelegramAuthServiceImpl.LoginSession)this.redisTemplate.opsForValue().get(key);
         if (session == null) {
            log.warn("Telegram 登录会话不存在或已过期");
            return null;
         } else if (!session.failed && session.pendingUserId == null) {
            Boolean claimed = this.redisTemplate
               .opsForValue()
               .setIfAbsent("tg:login:completion:" + sessionId, String.valueOf(telegramUserId), 5L, TimeUnit.MINUTES);
            if (!Boolean.TRUE.equals(claimed)) {
               log.warn("Telegram 登录会话已被消费");
               return null;
            } else {
               EmbyUser user = this.telegramBindingManager.findBoundUser(telegramUserId, true);
               if (user == null) {
                  log.warn("Telegram 用户未绑定可用的 Emby 账户");
                  session.failed = true;
                  session.errorMessage = "您的 Telegram 账户尚未绑定 Emby 账户，请先登录后绑定";
                  this.redisTemplate.opsForValue().set(key, session, 5L, TimeUnit.MINUTES);
                  return null;
               } else {
                  session.pendingUserId = user.getId();
                  this.redisTemplate.opsForValue().set(key, session, 5L, TimeUnit.MINUTES);
                  EmbyUserCustomResponse response = this.buildLoginResponse(user);
                  log.info("用户 {} 通过 Telegram 深链接验证成功, 等待前端完成登录, sessionId={}", user.getEmbyUserName(), sessionId);
                  return response;
               }
            }
         } else {
            return null;
         }
      } else {
         log.warn("completeLogin 参数无效");
         return null;
      }
   }

   private EmbyUserCustomResponse buildLoginResponse(EmbyUser user) {
      EmbyUserCustomResponse response = Binder.convertAndBindRelations(user, EmbyUserCustomResponse.class);
      response.setServers(this.embyUserService.listAccessibleServers(user));
      response.setMenuPermissions(this.adminMenuPermissionService.resolveMenuKeys(user));
      return response;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse completeBind(String sessionId, Long telegramUserId, String telegramUsername, String telegramAvatar) {
      if (this.isOpaqueToken(sessionId) && telegramUserId != null && telegramUserId > 0L) {
         String key = "tg:bind:session:" + sessionId;
         TelegramAuthServiceImpl.BindSession session = (TelegramAuthServiceImpl.BindSession)this.redisTemplate.opsForValue().getAndDelete(key);
         if (session != null && session.getUserId() != null) {
            EmbyUser embyUser = this.telegramBindingManager.findUsableUser(session.getUserId());
            if (embyUser == null) {
               throw new BizException("绑定失败：用户不存在或已被禁用");
            } else {
               TelegramBindingActionResponse result = this.telegramBindingReviewService
                  .submitBind(embyUser, telegramUserId, telegramUsername, telegramAvatar, "WEB", false);
               log.info("用户 {} 通过 Telegram 一次性会话提交绑定: telegramId={}, status={}", embyUser.getEmbyUserName(), telegramUserId, result.getStatus());
               return result;
            }
         } else {
            log.warn("Telegram 绑定会话不存在、已过期或已被消费");
            return null;
         }
      } else {
         log.warn("completeBind 参数无效");
         return null;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse bindByCredentials(
      Long telegramUserId, String telegramUsername, String telegramAvatar, String embyUserName, String password
   ) {
      if (telegramUserId != null && StringUtils.hasText(embyUserName) && StringUtils.hasText(password)) {
         String passwordHash = SaSecureUtil.md5(password);
         List<EmbyUser> users = new LambdaQueryChainWrapper<>(this.embyUserMapper)
            .eq(EmbyUser::getEmbyUserName, embyUserName.trim())
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .list();
         List<EmbyUser> matchedUsers = users.stream().filter(user -> passwordHash.equals(user.getEmbyUserPassword())).toList();
         if (matchedUsers.isEmpty()) {
            throw new BizException("用户名或密码错误");
         } else if (!EmbyUserIdentityUtils.belongToSameIdentity(matchedUsers)) {
            throw new BizException("当前账户暂时无法登录，请联系管理员处理");
         } else {
            Map<Long, EmbyUser> userByServerId = matchedUsers.stream()
               .filter(user -> user.getEmbyInfoId() != null)
               .sorted(Comparator.comparing(EmbyUser::getId))
               .collect(Collectors.toMap(EmbyUser::getEmbyInfoId, user -> (EmbyUser)user, (first, ignored) -> first, LinkedHashMap::new));
            List<EmbyUserCustomResponse.ServerOption> serverOptions = this.embyUserService
               .listAccessibleServers(matchedUsers.get(0))
               .stream()
               .filter(server -> server.getEmbyInfoId() != null && userByServerId.containsKey(server.getEmbyInfoId()))
               .toList();
            if (serverOptions.size() > 1) {
               Set<Long> selectableServerIds = serverOptions.stream()
                  .map(EmbyUserCustomResponse.ServerOption::getEmbyInfoId)
                  .collect(Collectors.toCollection(LinkedHashSet::new));
               List<Long> candidateUserIds = matchedUsers.stream()
                  .filter(user -> user.getId() != null && selectableServerIds.contains(user.getEmbyInfoId()))
                  .map(EmbyUser::getId)
                  .distinct()
                  .toList();
               String selectionToken = UUID.randomUUID().toString().replace("-", "");
               TelegramAuthServiceImpl.CredentialBindSelectionSession session = new TelegramAuthServiceImpl.CredentialBindSelectionSession(
                  System.currentTimeMillis(), telegramUserId, candidateUserIds
               );
               this.redisTemplate.opsForValue().set("tg:bind:credential-selection:" + selectionToken, session, 5L, TimeUnit.MINUTES);
               return TelegramBindingActionResponse.serverSelection(selectionToken, serverOptions, "同一账号属于多个服务器，请选择要绑定的服务器");
            } else {
               EmbyUser embyUser = serverOptions.size() == 1
                  ? userByServerId.get(serverOptions.get(0).getEmbyInfoId())
                  : matchedUsers.stream().min(Comparator.comparing(EmbyUser::getId)).orElseThrow(() -> new BizException("用户名或密码错误"));
               return this.submitCredentialBind(embyUser, telegramUserId, telegramUsername, telegramAvatar);
            }
         }
      } else {
         throw new BizException("用法：/bind 用户名 密码");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse completeCredentialBindSelection(
      String selectionToken, Long telegramUserId, String telegramUsername, String telegramAvatar, Long embyInfoId
   ) {
      if (this.isOpaqueToken(selectionToken) && telegramUserId != null && telegramUserId > 0L && embyInfoId != null) {
         String key = "tg:bind:credential-selection:" + selectionToken;
         if (this.redisTemplate.opsForValue().get(key) instanceof TelegramAuthServiceImpl.CredentialBindSelectionSession session) {
            if (!telegramUserId.equals(session.getTelegramUserId())) {
               throw new BizException("该服务器选择不属于当前 Telegram 账号");
            } else {
               if (this.redisTemplate.opsForValue().getAndDelete(key) instanceof TelegramAuthServiceImpl.CredentialBindSelectionSession consumedSession
                  && telegramUserId.equals(consumedSession.getTelegramUserId())) {
                  List<Long> candidateUserIds = consumedSession.getCandidateUserIds();
                  if (candidateUserIds != null && !candidateUserIds.isEmpty()) {
                     EmbyUser embyUser = new LambdaQueryChainWrapper<>(this.embyUserMapper)
                        .in(EmbyUser::getId, candidateUserIds)
                        .eq(EmbyUser::getEmbyInfoId, embyInfoId)
                        .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                        .one();
                     if (embyUser == null) {
                        throw new BizException("所选服务器不在本次绑定范围内，请重新发送 /bind 用户名 密码");
                     }

                     return this.submitCredentialBind(embyUser, telegramUserId, telegramUsername, telegramAvatar);
                  }

                  throw new BizException("服务器选择已失效，请重新发送 /bind 用户名 密码");
               }

               throw new BizException("服务器选择已失效，请重新发送 /bind 用户名 密码");
            }
         } else {
            throw new BizException("服务器选择已过期，请重新发送 /bind 用户名 密码");
         }
      } else {
         throw new BizException("服务器选择无效，请重新发送 /bind 用户名 密码");
      }
   }

   private TelegramBindingActionResponse submitCredentialBind(EmbyUser embyUser, Long telegramUserId, String telegramUsername, String telegramAvatar) {
      TelegramBindingActionResponse result = this.telegramBindingReviewService
         .submitBind(embyUser, telegramUserId, telegramUsername, telegramAvatar, "BOT_CREDENTIAL", false);
      log.info("用户 {} 通过 Telegram 私聊凭证提交绑定: telegramId={}, status={}", embyUser.getEmbyUserName(), telegramUserId, result.getStatus());
      return result;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse bindExistingUser(
      Long embyUserId, Long telegramUserId, String telegramUsername, String telegramAvatar, String requestSource
   ) {
      if (embyUserId != null && telegramUserId != null) {
         EmbyUser embyUser = this.telegramBindingManager.findUsableUser(embyUserId);
         if (embyUser == null) {
            throw new BizException("绑定失败：用户不存在或已被禁用");
         } else {
            TelegramBindingActionResponse result = this.telegramBindingReviewService
               .submitBind(embyUser, telegramUserId, telegramUsername, telegramAvatar, requestSource, false);
            log.info("Telegram 新用户 {} 提交绑定: telegramId={}, status={}", embyUser.getEmbyUserName(), telegramUserId, result.getStatus());
            return result;
         }
      } else {
         throw new BizException("绑定参数无效");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public TelegramBindingActionResponse unbindByTelegramId(Long telegramUserId) {
      TelegramBindingActionResponse result = this.telegramBindingReviewService.submitUnbindByTelegramId(telegramUserId, "BOT");
      log.info("Telegram 用户 {} 提交解绑: status={}", telegramUserId, result.getStatus());
      return result;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public EmbyUser forceUnbindByTelegramId(Long telegramUserId) {
      EmbyUser user = this.telegramBindingManager.unbindByTelegramId(telegramUserId);
      log.info("系统事件强制解除 Telegram 用户 {} 的绑定", telegramUserId);
      return user;
   }

   @Override
   public EmbyUser findBoundUser(Long telegramUserId) {
      return telegramUserId == null ? null : this.telegramBindingManager.findBoundUser(telegramUserId, true);
   }

   private String buildBotDeepLink(String action, String sessionId) {
      TelegramResponse telegramConfig = this.telegramClientUtils.getTelegramResponse();
      if (telegramConfig != null && StringUtils.hasText(telegramConfig.getBotName())) {
         String botName = telegramConfig.getBotName().trim().replaceFirst("^@", "");
         if (!botName.matches("[A-Za-z0-9_]{5,32}")) {
            throw new BizException("Telegram Bot 用户名配置无效");
         } else {
            return "https://t.me/" + botName + "?start=" + action + "_" + sessionId;
         }
      } else {
         throw new BizException("Telegram Bot 未配置");
      }
   }

   @Generated
   public TelegramAuthServiceImpl(
      final TelegramClientUtils telegramClientUtils,
      final EmbyUserMapper embyUserMapper,
      final UserOauthBindingMapper userOauthBindingMapper,
      final RedisTemplate<String, Object> redisTemplate,
      final EmbyUserService embyUserService,
      final AdminMenuPermissionService adminMenuPermissionService,
      final TelegramBindingManager telegramBindingManager,
      final TelegramBindingReviewService telegramBindingReviewService,
      final TelegramBindingReviewNotifier telegramBindingReviewNotifier
   ) {
      this.telegramClientUtils = telegramClientUtils;
      this.embyUserMapper = embyUserMapper;
      this.userOauthBindingMapper = userOauthBindingMapper;
      this.redisTemplate = redisTemplate;
      this.embyUserService = embyUserService;
      this.adminMenuPermissionService = adminMenuPermissionService;
      this.telegramBindingManager = telegramBindingManager;
      this.telegramBindingReviewService = telegramBindingReviewService;
      this.telegramBindingReviewNotifier = telegramBindingReviewNotifier;
   }

   private static class BindSession implements Serializable {
      private long createTime;
      private Long userId;

      @Generated
      public long getCreateTime() {
         return this.createTime;
      }

      @Generated
      public Long getUserId() {
         return this.userId;
      }

      @Generated
      public void setCreateTime(final long createTime) {
         this.createTime = createTime;
      }

      @Generated
      public void setUserId(final Long userId) {
         this.userId = userId;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof TelegramAuthServiceImpl.BindSession other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getCreateTime() != other.getCreateTime()) {
            return false;
         } else {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            return this$userId == null ? other$userId == null : this$userId.equals(other$userId);
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof TelegramAuthServiceImpl.BindSession;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         long $createTime = this.getCreateTime();
         result = result * 59 + (int)($createTime >>> 32 ^ $createTime);
         Object $userId = this.getUserId();
         return result * 59 + ($userId == null ? 43 : $userId.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "TelegramAuthServiceImpl.BindSession(createTime=" + this.getCreateTime() + ", userId=" + this.getUserId() + ")";
      }

      @Generated
      public BindSession() {
      }

      @Generated
      public BindSession(final long createTime, final Long userId) {
         this.createTime = createTime;
         this.userId = userId;
      }
   }

   private static class CredentialBindSelectionSession implements Serializable {
      private long createTime;
      private Long telegramUserId;
      private List<Long> candidateUserIds;

      @Generated
      public long getCreateTime() {
         return this.createTime;
      }

      @Generated
      public Long getTelegramUserId() {
         return this.telegramUserId;
      }

      @Generated
      public List<Long> getCandidateUserIds() {
         return this.candidateUserIds;
      }

      @Generated
      public void setCreateTime(final long createTime) {
         this.createTime = createTime;
      }

      @Generated
      public void setTelegramUserId(final Long telegramUserId) {
         this.telegramUserId = telegramUserId;
      }

      @Generated
      public void setCandidateUserIds(final List<Long> candidateUserIds) {
         this.candidateUserIds = candidateUserIds;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof TelegramAuthServiceImpl.CredentialBindSelectionSession other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getCreateTime() != other.getCreateTime()) {
            return false;
         } else {
            Object this$telegramUserId = this.getTelegramUserId();
            Object other$telegramUserId = other.getTelegramUserId();
            if (this$telegramUserId == null ? other$telegramUserId == null : this$telegramUserId.equals(other$telegramUserId)) {
               Object this$candidateUserIds = this.getCandidateUserIds();
               Object other$candidateUserIds = other.getCandidateUserIds();
               return this$candidateUserIds == null ? other$candidateUserIds == null : this$candidateUserIds.equals(other$candidateUserIds);
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof TelegramAuthServiceImpl.CredentialBindSelectionSession;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         long $createTime = this.getCreateTime();
         result = result * 59 + (int)($createTime >>> 32 ^ $createTime);
         Object $telegramUserId = this.getTelegramUserId();
         result = result * 59 + ($telegramUserId == null ? 43 : $telegramUserId.hashCode());
         Object $candidateUserIds = this.getCandidateUserIds();
         return result * 59 + ($candidateUserIds == null ? 43 : $candidateUserIds.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "TelegramAuthServiceImpl.CredentialBindSelectionSession(createTime="
            + this.getCreateTime()
            + ", telegramUserId="
            + this.getTelegramUserId()
            + ", candidateUserIds="
            + this.getCandidateUserIds()
            + ")";
      }

      @Generated
      public CredentialBindSelectionSession() {
      }

      @Generated
      public CredentialBindSelectionSession(final long createTime, final Long telegramUserId, final List<Long> candidateUserIds) {
         this.createTime = createTime;
         this.telegramUserId = telegramUserId;
         this.candidateUserIds = candidateUserIds;
      }
   }

   private static class LoginSession implements Serializable {
      private long createTime;
      private String clientToken;
      private Long pendingUserId;
      private boolean failed;
      private String errorMessage;

      public LoginSession(boolean init) {
         this.createTime = System.currentTimeMillis();
      }

      @Generated
      public long getCreateTime() {
         return this.createTime;
      }

      @Generated
      public String getClientToken() {
         return this.clientToken;
      }

      @Generated
      public Long getPendingUserId() {
         return this.pendingUserId;
      }

      @Generated
      public boolean isFailed() {
         return this.failed;
      }

      @Generated
      public String getErrorMessage() {
         return this.errorMessage;
      }

      @Generated
      public void setCreateTime(final long createTime) {
         this.createTime = createTime;
      }

      @Generated
      public void setClientToken(final String clientToken) {
         this.clientToken = clientToken;
      }

      @Generated
      public void setPendingUserId(final Long pendingUserId) {
         this.pendingUserId = pendingUserId;
      }

      @Generated
      public void setFailed(final boolean failed) {
         this.failed = failed;
      }

      @Generated
      public void setErrorMessage(final String errorMessage) {
         this.errorMessage = errorMessage;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof TelegramAuthServiceImpl.LoginSession other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getCreateTime() != other.getCreateTime()) {
            return false;
         } else if (this.isFailed() != other.isFailed()) {
            return false;
         } else {
            Object this$pendingUserId = this.getPendingUserId();
            Object other$pendingUserId = other.getPendingUserId();
            if (this$pendingUserId == null ? other$pendingUserId == null : this$pendingUserId.equals(other$pendingUserId)) {
               Object this$clientToken = this.getClientToken();
               Object other$clientToken = other.getClientToken();
               if (this$clientToken == null ? other$clientToken == null : this$clientToken.equals(other$clientToken)) {
                  Object this$errorMessage = this.getErrorMessage();
                  Object other$errorMessage = other.getErrorMessage();
                  return this$errorMessage == null ? other$errorMessage == null : this$errorMessage.equals(other$errorMessage);
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof TelegramAuthServiceImpl.LoginSession;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         long $createTime = this.getCreateTime();
         result = result * 59 + (int)($createTime >>> 32 ^ $createTime);
         result = result * 59 + (this.isFailed() ? 79 : 97);
         Object $pendingUserId = this.getPendingUserId();
         result = result * 59 + ($pendingUserId == null ? 43 : $pendingUserId.hashCode());
         Object $clientToken = this.getClientToken();
         result = result * 59 + ($clientToken == null ? 43 : $clientToken.hashCode());
         Object $errorMessage = this.getErrorMessage();
         return result * 59 + ($errorMessage == null ? 43 : $errorMessage.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "TelegramAuthServiceImpl.LoginSession(createTime="
            + this.getCreateTime()
            + ", clientToken="
            + this.getClientToken()
            + ", pendingUserId="
            + this.getPendingUserId()
            + ", failed="
            + this.isFailed()
            + ", errorMessage="
            + this.getErrorMessage()
            + ")";
      }

      @Generated
      public LoginSession() {
      }

      @Generated
      public LoginSession(final long createTime, final String clientToken, final Long pendingUserId, final boolean failed, final String errorMessage) {
         this.createTime = createTime;
         this.clientToken = clientToken;
         this.pendingUserId = pendingUserId;
         this.failed = failed;
         this.errorMessage = errorMessage;
      }
   }

   private static class RebindSession implements Serializable {
      private long createTime;
      private Long userId;
      private Long oldTelegramUserId;
      private String oldTelegramUsername;
      private String oldTelegramAvatar;
      private Long newTelegramUserId;
      private String newTelegramUsername;
      private byte[] verificationCodeHash;
      private int failedAttempts;
      private boolean verified;

      @Generated
      public long getCreateTime() {
         return this.createTime;
      }

      @Generated
      public Long getUserId() {
         return this.userId;
      }

      @Generated
      public Long getOldTelegramUserId() {
         return this.oldTelegramUserId;
      }

      @Generated
      public String getOldTelegramUsername() {
         return this.oldTelegramUsername;
      }

      @Generated
      public String getOldTelegramAvatar() {
         return this.oldTelegramAvatar;
      }

      @Generated
      public Long getNewTelegramUserId() {
         return this.newTelegramUserId;
      }

      @Generated
      public String getNewTelegramUsername() {
         return this.newTelegramUsername;
      }

      @Generated
      public byte[] getVerificationCodeHash() {
         return this.verificationCodeHash;
      }

      @Generated
      public int getFailedAttempts() {
         return this.failedAttempts;
      }

      @Generated
      public boolean isVerified() {
         return this.verified;
      }

      @Generated
      public void setCreateTime(final long createTime) {
         this.createTime = createTime;
      }

      @Generated
      public void setUserId(final Long userId) {
         this.userId = userId;
      }

      @Generated
      public void setOldTelegramUserId(final Long oldTelegramUserId) {
         this.oldTelegramUserId = oldTelegramUserId;
      }

      @Generated
      public void setOldTelegramUsername(final String oldTelegramUsername) {
         this.oldTelegramUsername = oldTelegramUsername;
      }

      @Generated
      public void setOldTelegramAvatar(final String oldTelegramAvatar) {
         this.oldTelegramAvatar = oldTelegramAvatar;
      }

      @Generated
      public void setNewTelegramUserId(final Long newTelegramUserId) {
         this.newTelegramUserId = newTelegramUserId;
      }

      @Generated
      public void setNewTelegramUsername(final String newTelegramUsername) {
         this.newTelegramUsername = newTelegramUsername;
      }

      @Generated
      public void setVerificationCodeHash(final byte[] verificationCodeHash) {
         this.verificationCodeHash = verificationCodeHash;
      }

      @Generated
      public void setFailedAttempts(final int failedAttempts) {
         this.failedAttempts = failedAttempts;
      }

      @Generated
      public void setVerified(final boolean verified) {
         this.verified = verified;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof TelegramAuthServiceImpl.RebindSession other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getCreateTime() != other.getCreateTime()) {
            return false;
         } else if (this.getFailedAttempts() != other.getFailedAttempts()) {
            return false;
         } else if (this.isVerified() != other.isVerified()) {
            return false;
         } else {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$oldTelegramUserId = this.getOldTelegramUserId();
               Object other$oldTelegramUserId = other.getOldTelegramUserId();
               if (this$oldTelegramUserId == null ? other$oldTelegramUserId == null : this$oldTelegramUserId.equals(other$oldTelegramUserId)) {
                  Object this$newTelegramUserId = this.getNewTelegramUserId();
                  Object other$newTelegramUserId = other.getNewTelegramUserId();
                  if (this$newTelegramUserId == null ? other$newTelegramUserId == null : this$newTelegramUserId.equals(other$newTelegramUserId)) {
                     Object this$oldTelegramUsername = this.getOldTelegramUsername();
                     Object other$oldTelegramUsername = other.getOldTelegramUsername();
                     if (this$oldTelegramUsername == null ? other$oldTelegramUsername == null : this$oldTelegramUsername.equals(other$oldTelegramUsername)) {
                        Object this$oldTelegramAvatar = this.getOldTelegramAvatar();
                        Object other$oldTelegramAvatar = other.getOldTelegramAvatar();
                        if (this$oldTelegramAvatar == null ? other$oldTelegramAvatar == null : this$oldTelegramAvatar.equals(other$oldTelegramAvatar)) {
                           Object this$newTelegramUsername = this.getNewTelegramUsername();
                           Object other$newTelegramUsername = other.getNewTelegramUsername();
                           return (
                                 this$newTelegramUsername == null
                                    ? other$newTelegramUsername == null
                                    : this$newTelegramUsername.equals(other$newTelegramUsername)
                              )
                              ? Arrays.equals(this.getVerificationCodeHash(), other.getVerificationCodeHash())
                              : false;
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof TelegramAuthServiceImpl.RebindSession;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         long $createTime = this.getCreateTime();
         result = result * 59 + (int)($createTime >>> 32 ^ $createTime);
         result = result * 59 + this.getFailedAttempts();
         result = result * 59 + (this.isVerified() ? 79 : 97);
         Object $userId = this.getUserId();
         result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
         Object $oldTelegramUserId = this.getOldTelegramUserId();
         result = result * 59 + ($oldTelegramUserId == null ? 43 : $oldTelegramUserId.hashCode());
         Object $newTelegramUserId = this.getNewTelegramUserId();
         result = result * 59 + ($newTelegramUserId == null ? 43 : $newTelegramUserId.hashCode());
         Object $oldTelegramUsername = this.getOldTelegramUsername();
         result = result * 59 + ($oldTelegramUsername == null ? 43 : $oldTelegramUsername.hashCode());
         Object $oldTelegramAvatar = this.getOldTelegramAvatar();
         result = result * 59 + ($oldTelegramAvatar == null ? 43 : $oldTelegramAvatar.hashCode());
         Object $newTelegramUsername = this.getNewTelegramUsername();
         result = result * 59 + ($newTelegramUsername == null ? 43 : $newTelegramUsername.hashCode());
         return result * 59 + Arrays.hashCode(this.getVerificationCodeHash());
      }

      @Generated
      @Override
      public String toString() {
         return "TelegramAuthServiceImpl.RebindSession(createTime="
            + this.getCreateTime()
            + ", userId="
            + this.getUserId()
            + ", oldTelegramUserId="
            + this.getOldTelegramUserId()
            + ", oldTelegramUsername="
            + this.getOldTelegramUsername()
            + ", oldTelegramAvatar="
            + this.getOldTelegramAvatar()
            + ", newTelegramUserId="
            + this.getNewTelegramUserId()
            + ", newTelegramUsername="
            + this.getNewTelegramUsername()
            + ", verificationCodeHash="
            + Arrays.toString(this.getVerificationCodeHash())
            + ", failedAttempts="
            + this.getFailedAttempts()
            + ", verified="
            + this.isVerified()
            + ")";
      }

      @Generated
      public RebindSession() {
      }

      @Generated
      public RebindSession(
         final long createTime,
         final Long userId,
         final Long oldTelegramUserId,
         final String oldTelegramUsername,
         final String oldTelegramAvatar,
         final Long newTelegramUserId,
         final String newTelegramUsername,
         final byte[] verificationCodeHash,
         final int failedAttempts,
         final boolean verified
      ) {
         this.createTime = createTime;
         this.userId = userId;
         this.oldTelegramUserId = oldTelegramUserId;
         this.oldTelegramUsername = oldTelegramUsername;
         this.oldTelegramAvatar = oldTelegramAvatar;
         this.newTelegramUserId = newTelegramUserId;
         this.newTelegramUsername = newTelegramUsername;
         this.verificationCodeHash = verificationCodeHash;
         this.failedAttempts = failedAttempts;
         this.verified = verified;
      }
   }
}
