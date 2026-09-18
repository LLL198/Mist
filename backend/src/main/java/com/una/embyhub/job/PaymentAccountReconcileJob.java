package com.una.embyhub.job;

import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.payment.service.PaymentAccountOrderService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PaymentAccountReconcileJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PaymentAccountReconcileJob.class);
   private final PaymentAccountOrderService orderService;

   @Scheduled(
      fixedDelay = 60000L,
      initialDelay = 30000L
   )
   @ScheduledTaskMeta(
      name = "易支付售卡对账",
      remark = "主动查询未完成支付订单并接续已付款卡密生成"
   )
   public void reconcile() {
      try {
         this.orderService.reconcileOpenOrders();
      } catch (RuntimeException var2) {
         log.warn("易支付售卡对账任务失败: error={}", var2.getMessage(), var2);
      }
   }

   @Generated
   public PaymentAccountReconcileJob(final PaymentAccountOrderService orderService) {
      this.orderService = orderService;
   }
}
