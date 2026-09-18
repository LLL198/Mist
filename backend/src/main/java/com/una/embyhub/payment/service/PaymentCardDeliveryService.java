package com.una.embyhub.payment.service;

import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.PaymentAccountOrderMapper;
import com.una.embyhub.model.entity.CardSecurityManagement;
import com.una.embyhub.payment.model.PaymentAccountOrder;
import com.una.embyhub.service.CardSecurityManagementService;
import java.util.Date;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentCardDeliveryService {
   private final PaymentAccountOrderMapper orderMapper;
   private final CardSecurityManagementService cardService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public CardSecurityManagement fulfill(String orderNo) {
      PaymentAccountOrder order = this.orderMapper.selectByOrderNoForUpdate(orderNo);
      if (order == null) {
         throw new BizException("支付订单不存在");
      } else {
         if (order.getCardSecurityId() != null) {
            CardSecurityManagement existing = this.cardService.getById(order.getCardSecurityId());
            if (existing != null) {
               return existing;
            }
         }

         if (!"PAID".equals(order.getStatus()) && !"PROVISIONING".equals(order.getStatus())) {
            throw new BizException("订单尚未确认支付，不能生成卡密");
         } else {
            Date now = new Date();
            order.setStatus("PROVISIONING");
            order.setProvisionStartedDatetime(now);
            order.setUpdateDatetime(now);
            this.orderMapper.updateById(order);
            CardSecurityManagement card = this.cardService
               .createPaymentCard(order.getId(), order.getValidityDays(), order.getEmbyInfoId(), order.getHostLineType(), "易支付订单 " + order.getOrderNo());
            order.setStatus("COMPLETED");
            order.setCardSecurityId(card.getId());
            order.setCompletedDatetime(new Date());
            order.setFailureReason(null);
            order.setUpdateDatetime(new Date());
            this.orderMapper.updateById(order);
            return card;
         }
      }
   }

   @Generated
   public PaymentCardDeliveryService(final PaymentAccountOrderMapper orderMapper, final CardSecurityManagementService cardService) {
      this.orderMapper = orderMapper;
      this.cardService = cardService;
   }
}
