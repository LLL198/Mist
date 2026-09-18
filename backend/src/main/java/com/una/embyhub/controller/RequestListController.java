package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.requestlist.RequestListAudit;
import com.una.embyhub.model.dto.request.requestlist.RequestListReject;
import com.una.embyhub.model.dto.request.requestlist.RequestListRequest;
import com.una.embyhub.model.dto.request.requestlist.RequestListSave;
import com.una.embyhub.model.dto.request.requestlist.RequestListUpdate;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.requestlist.RequestListResponse;
import com.una.embyhub.model.dto.response.requestlist.RequestListStatusResponse;
import com.una.embyhub.service.RequestListService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"requestList"})
public class RequestListController {
   @Autowired
   private RequestListService requestListService;

   @PostMapping({"select"})
   public Page<RequestListResponse> select(@RequestBody MybatisPlusPage<RequestListRequest> page) {
      return this.requestListService.select(page);
   }

   @GetMapping({"todayCount"})
   public long todayCount(@RequestParam(required = false) Long embyInfoId, @RequestParam(required = false) String timezone) {
      return this.requestListService.todayCount(embyInfoId, timezone);
   }

   @PostMapping({"insertRequestList"})
   public EmbyUserResponse insertRequestList(@RequestBody @Validated RequestListSave requestListSave) {
      return this.requestListService.insertRequestList(requestListSave);
   }

   @GetMapping({"status"})
   public RequestListStatusResponse status(@RequestParam String tmdbId, @RequestParam String type, @RequestParam(required = false) Integer season) {
      return this.requestListService.getRequestStatus(tmdbId, season, type);
   }

   @PostMapping({"updateRequestList"})
   public void updateRequestList(@RequestBody @Validated RequestListUpdate requestListUpdate) {
      this.requestListService.updateRequestList(requestListUpdate);
   }

   @PostMapping({"deleteByRequestListId"})
   public void deleteByRequestListId(@RequestParam Long requestListId) {
      this.requestListService.deleteByRequestListId(requestListId);
   }

   @PostMapping({"updateByRequestListId"})
   @SaCheckPermission({"admin"})
   public void updateByRequestListId(@RequestParam List<Long> requestListIdList, @RequestParam(required = false) Long embyInfoId) {
      this.requestListService.updateByRequestListId(requestListIdList, embyInfoId);
   }

   @PostMapping({"rejectRequestList"})
   @SaCheckPermission({"admin"})
   public void rejectRequestList(@RequestBody @Validated RequestListReject request) {
      this.requestListService.rejectRequestList(request);
   }

   @PostMapping({"auditRequestList"})
   @SaCheckPermission({"admin"})
   public void auditRequestList(@RequestBody @Validated RequestListAudit request) {
      this.requestListService.auditRequestList(request);
   }
}
