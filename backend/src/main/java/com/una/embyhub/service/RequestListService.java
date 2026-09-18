package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.requestlist.RequestListAudit;
import com.una.embyhub.model.dto.request.requestlist.RequestListReject;
import com.una.embyhub.model.dto.request.requestlist.RequestListRequest;
import com.una.embyhub.model.dto.request.requestlist.RequestListSave;
import com.una.embyhub.model.dto.request.requestlist.RequestListUpdate;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.requestlist.RequestListResponse;
import com.una.embyhub.model.dto.response.requestlist.RequestListStatusResponse;
import com.una.embyhub.model.entity.RequestList;
import java.util.List;

public interface RequestListService extends IService<RequestList> {
   Page<RequestListResponse> select(MybatisPlusPage<RequestListRequest> page);

   long todayCount(Long embyInfoId, String timezone);

   EmbyUserResponse insertRequestList(RequestListSave requestListSave);

   EmbyUserResponse insertRequestListForUser(Long userId, RequestListSave requestListSave);

   RequestList insertTelegramRequestListForUser(Long userId, RequestListSave requestListSave, Long telegramUserId, Integer pointsCost, String pointsRefId);

   void updateRequestList(RequestListUpdate requestListUpdate);

   void deleteByRequestListId(Long requestListId);

   void updateByRequestListId(List<Long> requestListIdList, Long embyInfoId);

   void rejectRequestList(RequestListReject requestListReject);

   void auditRequestList(RequestListAudit requestListAudit);

   boolean getRequestListStatusByTmdbid(String tmdbid);

   boolean getRequestListStatusByTmdbidAndSeasons(String tmdbid, Integer seasons, String type);

   boolean isCurrentUserRequestSubmitted(String tmdbid, Integer seasons, String type);

   RequestListStatusResponse getRequestStatus(String tmdbid, Integer seasons, String type);
}
