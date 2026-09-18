package com.una.embyhub.payment.controller;
import com.una.embyhub.payment.model.PaymentAccountDtos;
import com.una.embyhub.payment.service.PaymentAccountOrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"paymentAccount"})
public class PaymentAccountController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PaymentAccountController.class);
   private final PaymentAccountOrderService orderService;

   @GetMapping({"config"})
   public PaymentAccountDtos.PublicConfig config() {
      return this.orderService.publicConfig();
   }

   @PostMapping({"orders"})
   public PaymentAccountDtos.CreateOrderResponse createOrder(
      @RequestBody @Valid PaymentAccountDtos.CreateOrderRequest request, HttpServletRequest servletRequest
   ) {
      return this.orderService.createOrder(request, servletRequest.getRemoteAddr());
   }

   @PostMapping({"orders/query"})
   public PaymentAccountDtos.LookupOrderResponse queryOrder(
      @RequestBody @Valid PaymentAccountDtos.LookupOrderRequest request, HttpServletRequest servletRequest
   ) {
      return this.orderService.lookupOrder(request, servletRequest.getRemoteAddr());
   }

   @RequestMapping(
      value = {"easypay/notify"},
      method = {RequestMethod.GET, RequestMethod.POST},
      produces = {"text/plain"}
   )
   public String easyPayNotify(@RequestParam Map<String, String> parameters) {
      try {
         this.orderService.handleNotification(parameters);
         return "success";
      } catch (RuntimeException var3) {
         log.warn("易支付回调处理失败: orderNo={}, error={}", parameters == null ? null : parameters.get("out_trade_no"), var3.getMessage());
         return "fail";
      }
   }

   @Generated
   public PaymentAccountController(final PaymentAccountOrderService orderService) {
      this.orderService = orderService;
   }
}
