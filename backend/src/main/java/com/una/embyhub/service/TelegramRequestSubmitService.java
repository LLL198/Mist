package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.requestlist.RequestListSave;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.entity.RequestList;
import java.util.UUID;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class TelegramRequestSubmitService {
   @Autowired
   private TelegramRequestPointsService telegramRequestPointsService;
   @Autowired
   private RequestListService requestListService;

   public TelegramRequestSubmitService.TelegramRequestSubmitResult submit(Long embyUserId, Long telegramUserId, RequestListSave requestListSave) {
      String pointsRefId = this.buildPointsRefId(telegramUserId, requestListSave);
      TelegramRequestPointsService.ChargeResult chargeResult = this.telegramRequestPointsService.chargeForSubmit(embyUserId, telegramUserId, pointsRefId);
      Integer remainingRequestPackagesCount = null;
      RequestList requestList;
      if (chargeResult.isPointsEnabled()) {
         requestList = this.requestListService
            .insertTelegramRequestListForUser(embyUserId, requestListSave, telegramUserId, chargeResult.getPointsCost(), pointsRefId);
      } else {
         EmbyUserResponse embyUserResponse = this.requestListService.insertRequestListForUser(embyUserId, requestListSave);
         remainingRequestPackagesCount = embyUserResponse == null ? null : embyUserResponse.getRequestPackagesCount();
         requestList = null;
      }

      return new TelegramRequestSubmitService.TelegramRequestSubmitResult(requestList, chargeResult, remainingRequestPackagesCount);
   }

   private String buildPointsRefId(Long telegramUserId, RequestListSave requestListSave) {
      String mediaType = requestListSave != null && requestListSave.getType() != null ? requestListSave.getType() : "unknown";
      Integer tmdbId = requestListSave == null ? null : requestListSave.getTmdbId();
      Integer season = requestListSave == null ? null : requestListSave.getSeason();
      return "tg_req:" + telegramUserId + ":" + mediaType + ":" + tmdbId + ":" + (season == null ? 0 : season) + ":" + UUID.randomUUID();
   }

   public static class TelegramRequestSubmitResult {
      private final RequestList requestList;
      private final TelegramRequestPointsService.ChargeResult chargeResult;
      private final Integer remainingRequestPackagesCount;

      public TelegramRequestSubmitResult(RequestList requestList, TelegramRequestPointsService.ChargeResult chargeResult, Integer remainingRequestPackagesCount) {
         this.requestList = requestList;
         this.chargeResult = chargeResult;
         this.remainingRequestPackagesCount = remainingRequestPackagesCount;
      }

      @Generated
      public RequestList getRequestList() {
         return this.requestList;
      }

      @Generated
      public TelegramRequestPointsService.ChargeResult getChargeResult() {
         return this.chargeResult;
      }

      @Generated
      public Integer getRemainingRequestPackagesCount() {
         return this.remainingRequestPackagesCount;
      }
   }
}
