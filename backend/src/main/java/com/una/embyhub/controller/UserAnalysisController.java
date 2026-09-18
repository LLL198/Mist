package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisOverviewResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisUserOptionResponse;
import com.una.embyhub.service.UserAnalysisService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"userAnalysis"})
public class UserAnalysisController {
   @Autowired
   private UserAnalysisService userAnalysisService;

   @GetMapping({"admin/overview"})
   @SaCheckPermission({"admin"})
   public UserAnalysisOverviewResponse adminOverview(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) Long embyInfoId,
      @RequestParam(required = false) String embyUserId,
      @RequestParam(required = false) String embyUserName,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate
   ) {
      return this.userAnalysisService.adminOverview(userId, embyInfoId, embyUserId, embyUserName, startDate, endDate);
   }

   @GetMapping({"admin/users"})
   @SaCheckPermission({"admin"})
   public List<UserAnalysisUserOptionResponse> activeUserOptions(@RequestParam(required = false) Long embyInfoId) {
      return this.userAnalysisService.activeUserOptions(embyInfoId);
   }

   @GetMapping({"me"})
   public UserAnalysisOverviewResponse myOverview(
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate
   ) {
      return this.userAnalysisService.myOverview(startDate, endDate);
   }
}
