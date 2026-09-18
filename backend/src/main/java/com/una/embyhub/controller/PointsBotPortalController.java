package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPortalLedgerRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPortalRedeemRecordRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPortalRedeemRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalLedgerPageResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemCatalogResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemRecordPageResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemResponse;
import com.una.embyhub.pointsbot.service.PointsBotPortalService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"pointsBot/portal"})
@Validated
@SaCheckLogin
public class PointsBotPortalController {
   private final PointsBotPortalService portalService;

   @PostMapping({"ledger/select"})
   public PointsBotPortalLedgerPageResponse selectMyLedger(@RequestBody @Valid MybatisPlusPage<PointsBotPortalLedgerRequest> request) {
      return this.portalService.selectMyLedger(StpUtil.getLoginIdAsLong(), request);
   }

   @GetMapping({"redeem/catalog"})
   public PointsBotPortalRedeemCatalogResponse getRedeemCatalog() {
      return this.portalService.getRedeemCatalog(StpUtil.getLoginIdAsLong());
   }

   @PostMapping({"redeem"})
   public PointsBotPortalRedeemResponse redeem(@RequestBody @Valid PointsBotPortalRedeemRequest request) {
      return this.portalService.redeem(StpUtil.getLoginIdAsLong(), request);
   }

   @PostMapping({"redeem/records/select"})
   public PointsBotPortalRedeemRecordPageResponse selectMyRedeemRecords(@RequestBody @Valid MybatisPlusPage<PointsBotPortalRedeemRecordRequest> request) {
      return this.portalService.selectMyRedeemRecords(StpUtil.getLoginIdAsLong(), request);
   }

   @Generated
   public PointsBotPortalController(final PointsBotPortalService portalService) {
      this.portalService = portalService;
   }
}
