package com.una.embyhub.payment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.payment.model.PaymentManagementDtos;
import com.una.embyhub.payment.service.PaymentAccountOrderService;
import com.una.embyhub.payment.service.PaymentPurchasePackageService;
import com.una.embyhub.payment.service.PaymentPurchaseSettingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Generated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"paymentManagement"})
@SaCheckPermission({"admin"})
public class PaymentManagementController {
   private final PaymentPurchaseSettingService settingService;
   private final PaymentPurchasePackageService packageService;
   private final PaymentAccountOrderService orderService;

   @GetMapping({"setting"})
   public PaymentManagementDtos.SettingResponse setting() {
      return this.settingService.getAdminSetting();
   }

   @PutMapping({"setting"})
   public PaymentManagementDtos.SettingResponse updateSetting(@RequestBody PaymentManagementDtos.UpdateSettingRequest request) {
      return this.settingService.updateAdminSetting(request);
   }

   @GetMapping({"packages"})
   public List<PaymentManagementDtos.PackageResponse> packages() {
      return this.packageService.listAdminPackages();
   }

   @PostMapping({"packages"})
   public PaymentManagementDtos.PackageResponse createPackage(@RequestBody @Valid PaymentManagementDtos.PackageRequest request) {
      return this.packageService.createPackage(request);
   }

   @PutMapping({"packages/{id}"})
   public PaymentManagementDtos.PackageResponse updatePackage(@PathVariable Long id, @RequestBody @Valid PaymentManagementDtos.PackageRequest request) {
      return this.packageService.updatePackage(id, request);
   }

   @DeleteMapping({"packages/{id}"})
   public void deletePackage(@PathVariable Long id) {
      this.packageService.deletePackage(id);
   }

   @GetMapping({"orders"})
   public Page<PaymentManagementDtos.OrderResponse> orders(
      @RequestParam(defaultValue = "1") long current,
      @RequestParam(defaultValue = "20") long size,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String orderNo,
      @RequestParam(required = false) String buyerName,
      @RequestParam(required = false) String providerTradeNo,
      @RequestParam(required = false) String packageName
   ) {
      return this.orderService.listAdminOrders(current, size, status, orderNo, buyerName, providerTradeNo, packageName);
   }

   @PostMapping({"orders/{orderNo}/reconcile"})
   public PaymentManagementDtos.ReconcileResponse reconcile(@PathVariable String orderNo) {
      return this.orderService.reconcileAdminOrder(orderNo);
   }

   @PostMapping({"orders/{orderNo}/confirm-paid"})
   public PaymentManagementDtos.OrderResponse confirmPaid(@PathVariable String orderNo, @RequestBody @Valid PaymentManagementDtos.ManualConfirmRequest request) {
      return this.orderService.manuallyConfirmPaid(orderNo, request);
   }

   @Generated
   public PaymentManagementController(
      final PaymentPurchaseSettingService settingService, final PaymentPurchasePackageService packageService, final PaymentAccountOrderService orderService
   ) {
      this.settingService = settingService;
      this.packageService = packageService;
      this.orderService = orderService;
   }
}
