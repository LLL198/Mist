package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.cardsecuritymanagement.CardSecurityManagementRequest;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementStatusResponse;
import com.una.embyhub.service.CardSecurityManagementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"cardSecurityManagement"})
public class CardSecurityManagementController {
   @Autowired
   private CardSecurityManagementService cardSecurityManagementService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<CardSecurityManagementResponse> select(@RequestBody MybatisPlusPage<CardSecurityManagementRequest> page) {
      return this.cardSecurityManagementService.select(page);
   }

   @PostMapping({"selectDistributor"})
   public Page<CardSecurityManagementResponse> selectDistributor(@RequestBody MybatisPlusPage<CardSecurityManagementRequest> page) {
      return this.cardSecurityManagementService.selectDistributor(page);
   }

   @PostMapping({"cardPasswordVerification"})
   public CardSecurityManagementResponse cardPasswordVerification(@RequestParam String cardPassword) {
      return this.cardSecurityManagementService.cardPasswordVerification(cardPassword);
   }

   @PostMapping({"addCardSecurityManagementList"})
   @SaCheckPermission({"admin"})
   public List<String> addCardSecurityManagementList(
      @RequestParam Integer count,
      @RequestParam Integer day,
      @RequestParam Long embyInfoId,
      @RequestParam(required = false) Integer hostLineType,
      @RequestParam(required = false) String remarks,
      @RequestParam(required = false) String copyfromuserid
   ) {
      return this.cardSecurityManagementService.addCardSecurityManagementList(count, day, embyInfoId, hostLineType, remarks, copyfromuserid, null);
   }

   @PostMapping({"deleteCardSecurityManagementList"})
   @SaCheckPermission({"admin"})
   public void deleteCardSecurityManagementList(@RequestBody List<Long> idList) {
      this.cardSecurityManagementService.deleteCardSecurityManagementList(idList);
   }

   @PostMapping({"cardSecurityManagementListStatus"})
   @SaCheckPermission({"admin"})
   public CardSecurityManagementStatusResponse cardSecurityManagementListStatus() {
      return this.cardSecurityManagementService.cardSecurityManagementListStatus();
   }

   @PostMapping({"cardSecurityManagementListStatusDistributor"})
   public CardSecurityManagementStatusResponse cardSecurityManagementListStatusDistributor() {
      return this.cardSecurityManagementService.cardSecurityManagementListStatusDistributor();
   }
}
