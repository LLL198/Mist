package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.una.embyhub.model.dto.request.telegram.TelegramBindingReviewCancelRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramLoginRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramRebindStartRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramRebindSubmitRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramRebindVerifyRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindSessionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingActionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramLoginSessionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramRebindSessionResponse;
import com.una.embyhub.service.TelegramAuthRateLimiter;
import com.una.embyhub.service.TelegramAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"telegramAuth"})
public class TelegramAuthController {
   private final TelegramAuthService telegramAuthService;
   private final TelegramAuthRateLimiter telegramAuthRateLimiter;

   @PostMapping({"bind"})
   @SaCheckLogin
   public TelegramBindingActionResponse bind(@RequestBody @Valid TelegramLoginRequest request) {
      return this.telegramAuthService.bind(request);
   }

   @PostMapping({"login"})
   public EmbyUserCustomResponse login(@RequestBody @Valid TelegramLoginRequest request, HttpServletRequest httpRequest) {
      this.telegramAuthRateLimiter.checkLoginAttempt(httpRequest.getRemoteAddr());
      return this.telegramAuthService.login(request);
   }

   @PostMapping({"unbind"})
   @SaCheckLogin
   public TelegramBindingActionResponse unbind() {
      return this.telegramAuthService.unbind();
   }

   @PostMapping({"rebind/start"})
   @SaCheckLogin
   public TelegramRebindSessionResponse startRebind(@RequestBody @Valid TelegramRebindStartRequest request) {
      return this.telegramAuthService.startRebind(request);
   }

   @PostMapping({"rebind/verify"})
   @SaCheckLogin
   public TelegramRebindSessionResponse verifyRebind(@RequestBody @Valid TelegramRebindVerifyRequest request) {
      return this.telegramAuthService.verifyRebind(request.getVerificationId(), request.getVerificationCode());
   }

   @PostMapping({"rebind/submit"})
   @SaCheckLogin
   public TelegramBindingActionResponse submitRebind(@RequestBody @Valid TelegramRebindSubmitRequest request) {
      return this.telegramAuthService.submitRebind(request.getVerificationId());
   }

   @PostMapping({"refreshBindingProfile"})
   @SaCheckLogin
   public TelegramBindingActionResponse refreshBindingProfile() {
      return this.telegramAuthService.refreshBindingProfile();
   }

   @GetMapping({"pendingReview"})
   @SaCheckLogin
   public TelegramBindingReviewResponse pendingReview() {
      return this.telegramAuthService.pendingReview();
   }

   @PostMapping({"cancelReview"})
   @SaCheckLogin
   public TelegramBindingReviewResponse cancelReview(@RequestBody @Valid TelegramBindingReviewCancelRequest request) {
      return this.telegramAuthService.cancelReview(request.getReviewUuid());
   }

   @GetMapping({"loginSession"})
   public TelegramLoginSessionResponse generateLoginSession(HttpServletRequest request) {
      this.telegramAuthRateLimiter.checkLoginSessionCreation(request.getRemoteAddr());
      return this.telegramAuthService.generateLoginSession();
   }

   @GetMapping({"bindSession"})
   @SaCheckLogin
   public TelegramBindSessionResponse generateBindSession() {
      return this.telegramAuthService.generateBindSession();
   }

   @GetMapping({"checkLogin"})
   public EmbyUserCustomResponse checkLoginStatus(@RequestParam String sessionId, @RequestParam String clientToken, HttpServletRequest request) {
      this.telegramAuthRateLimiter.checkLoginPoll(request.getRemoteAddr());
      return this.telegramAuthService.checkLoginStatus(sessionId, clientToken);
   }

   @Generated
   public TelegramAuthController(final TelegramAuthService telegramAuthService, final TelegramAuthRateLimiter telegramAuthRateLimiter) {
      this.telegramAuthService = telegramAuthService;
      this.telegramAuthRateLimiter = telegramAuthRateLimiter;
   }
}
