package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.telegram.TelegramLoginRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramRebindStartRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindSessionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingActionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramLoginSessionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramRebindSessionResponse;
import com.una.embyhub.model.entity.EmbyUser;

public interface TelegramAuthService {
   boolean verifySignature(TelegramLoginRequest request);

   TelegramBindingActionResponse bind(TelegramLoginRequest request);

   EmbyUserCustomResponse login(TelegramLoginRequest request);

   TelegramBindingActionResponse unbind();

   TelegramRebindSessionResponse startRebind(TelegramRebindStartRequest request);

   TelegramRebindSessionResponse verifyRebind(String verificationId, String verificationCode);

   TelegramBindingActionResponse submitRebind(String verificationId);

   TelegramBindingActionResponse refreshBindingProfile();

   TelegramBindingReviewResponse pendingReview();

   TelegramBindingReviewResponse cancelReview(String reviewUuid);

   TelegramLoginSessionResponse generateLoginSession();

   EmbyUserCustomResponse checkLoginStatus(String sessionId, String clientToken);

   TelegramBindSessionResponse generateBindSession();

   EmbyUserCustomResponse completeLogin(String sessionId, Long telegramUserId);

   TelegramBindingActionResponse completeBind(String sessionId, Long telegramUserId, String telegramUsername, String telegramAvatar);

   TelegramBindingActionResponse bindByCredentials(Long telegramUserId, String telegramUsername, String telegramAvatar, String embyUserName, String password);

   TelegramBindingActionResponse completeCredentialBindSelection(
      String selectionToken, Long telegramUserId, String telegramUsername, String telegramAvatar, Long embyInfoId
   );

   TelegramBindingActionResponse bindExistingUser(Long embyUserId, Long telegramUserId, String telegramUsername, String telegramAvatar, String requestSource);

   TelegramBindingActionResponse unbindByTelegramId(Long telegramUserId);

   EmbyUser forceUnbindByTelegramId(Long telegramUserId);

   EmbyUser findBoundUser(Long telegramUserId);
}
