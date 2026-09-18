package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.cardsecuritymanagement.CardSecurityManagementRequest;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementStatusResponse;
import com.una.embyhub.model.entity.CardSecurityManagement;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

public interface CardSecurityManagementService extends IService<CardSecurityManagement> {
   Page<CardSecurityManagementResponse> select(MybatisPlusPage<CardSecurityManagementRequest> page);

   Page<CardSecurityManagementResponse> selectDistributor(MybatisPlusPage<CardSecurityManagementRequest> page);

   CardSecurityManagementResponse cardPasswordVerification(String cardPassword);

   default List<String> addCardSecurityManagementList(Integer count, @RequestParam Integer day, @RequestParam Long embyInfoId, Integer hostLineType) {
      return this.addCardSecurityManagementList(count, day, embyInfoId, hostLineType, null);
   }

   List<String> addCardSecurityManagementList(Integer count, @RequestParam Integer day, @RequestParam Long embyInfoId, Integer hostLineType, String remarks);

   List<String> addCardSecurityManagementList(
      Integer count, @RequestParam Integer day, @RequestParam Long embyInfoId, Integer hostLineType, String remarks, String creatorName
   );

   List<String> addCardSecurityManagementList(
      Integer count, @RequestParam Integer day, @RequestParam Long embyInfoId, Integer hostLineType, String remarks, String copyfromuserid, String creatorName
   );

   CardSecurityManagement createPaymentCard(Long paymentOrderId, Integer day, Long embyInfoId, Integer hostLineType, String remarks);

   void deleteCardSecurityManagementList(List<Long> idList);

   CardSecurityManagementStatusResponse cardSecurityManagementListStatus();

   CardSecurityManagementStatusResponse cardSecurityManagementListStatusDistributor();
}
