package com.una.embyhub.payment.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.PaymentPurchasePackageMapper;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.payment.model.PaymentAccountDtos;
import com.una.embyhub.payment.model.PaymentManagementDtos;
import com.una.embyhub.payment.model.PaymentPurchasePackage;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PaymentPurchasePackageService extends ServiceImpl<PaymentPurchasePackageMapper, PaymentPurchasePackage> {
   private static final BigDecimal MAX_PRICE = new BigDecimal("99999999.99");
   private final EmbyInfoService embyInfoService;
   private final EmbyUserService embyUserService;

   public List<PaymentManagementDtos.PackageResponse> listAdminPackages() {
      this.assertPrimaryAdmin();
      return this.orderedPackages(false).stream().map(this::toAdminResponse).toList();
   }

   public List<PaymentAccountDtos.PublicPackage> listPublicPackages() {
      return this.orderedPackages(true)
         .stream()
         .filter(item -> this.serverAvailable(item.getEmbyInfoId()))
         .map(item -> new PaymentAccountDtos.PublicPackage(item.getId(), item.getName(), item.getValidityDays(), item.getPrice()))
         .toList();
   }

   public PaymentPurchasePackage requirePurchasable(Long packageId) {
      PaymentPurchasePackage item = packageId == null
         ? null
         : new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(PaymentPurchasePackage::getId, packageId)
            .eq(PaymentPurchasePackage::getEnabled, Integer.valueOf(1))
            .oneOpt()
            .orElse(null);
      if (item == null) {
         throw new BizException("所选套餐不存在或已下架，请刷新后重试");
      } else {
         this.validateServer(item.getEmbyInfoId());
         return item;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PaymentManagementDtos.PackageResponse createPackage(PaymentManagementDtos.PackageRequest request) {
      this.assertPrimaryAdmin();
      PaymentPurchasePackage item = new PaymentPurchasePackage();
      this.apply(item, request);
      this.save(item);
      return this.toAdminResponse(item);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PaymentManagementDtos.PackageResponse updatePackage(Long id, PaymentManagementDtos.PackageRequest request) {
      this.assertPrimaryAdmin();
      PaymentPurchasePackage item = id == null ? null : this.getById(id);
      if (item == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "套餐不存在");
      } else {
         this.apply(item, request);
         this.updateById(item);
         return this.toAdminResponse(this.getById(id));
      }
   }

   public void deletePackage(Long id) {
      this.assertPrimaryAdmin();
      if (id != null && this.getById(id) != null) {
         this.removeById(id);
      } else {
         throw new BizException("套餐不存在");
      }
   }

   private List<PaymentPurchasePackage> orderedPackages(boolean enabledOnly) {
      return new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(enabledOnly, PaymentPurchasePackage::getEnabled, Integer.valueOf(1))
         .orderByAsc(PaymentPurchasePackage::getSortOrder)
         .orderByAsc(PaymentPurchasePackage::getId)
         .list();
   }

   private void apply(PaymentPurchasePackage item, PaymentManagementDtos.PackageRequest request) {
      if (request == null) {
         throw new BizException("套餐数据不能为空");
      } else {
         String name = request.name() == null ? "" : request.name().trim();
         if (StringUtils.hasText(name) && name.length() <= 128) {
            int days = request.validityDays() == null ? 0 : request.validityDays();
            if (days >= 1 && days <= 3650) {
               BigDecimal price = request.price();
               if (price != null && price.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(MAX_PRICE) <= 0 && price.stripTrailingZeros().scale() <= 2) {
                  this.validateServer(request.embyInfoId());
                  item.setName(name);
                  item.setValidityDays(days);
                  item.setPrice(price.setScale(2, RoundingMode.UNNECESSARY));
                  item.setEmbyInfoId(request.embyInfoId());
                  item.setHostLineType(HostLineTypeEnum.normalize(request.hostLineType()));
                  item.setEnabled(request.enabled() ? 1 : 0);
                  item.setSortOrder(request.sortOrder() == null ? 0 : Math.max(-9999, Math.min(9999, request.sortOrder())));
                  item.setRemarks(StringUtils.hasText(request.remarks()) ? request.remarks().trim() : null);
               } else {
                  throw new BizException("套餐价格需大于 0，且最多保留两位小数");
               }
            } else {
               throw new BizException("有效天数需在 1 到 3650 天之间");
            }
         } else {
            throw new BizException("套餐名称需为 1 到 128 个字符");
         }
      }
   }

   private void validateServer(Long embyInfoId) {
      if (!this.serverAvailable(embyInfoId)) {
         throw new BizException(ResponseStatusEnum.EMBY_INFO_DISABLED);
      }
   }

   private boolean serverAvailable(Long embyInfoId) {
      EmbyInfo server = embyInfoId == null ? null : this.embyInfoService.getById(embyInfoId);
      return server != null && Integer.valueOf(1).equals(server.getEnabled()) && Integer.valueOf(0).equals(server.getStatus());
   }

   private PaymentManagementDtos.PackageResponse toAdminResponse(PaymentPurchasePackage item) {
      return new PaymentManagementDtos.PackageResponse(
         item.getId(),
         item.getName(),
         item.getValidityDays(),
         item.getPrice(),
         item.getEmbyInfoId(),
         item.getHostLineType(),
         Integer.valueOf(1).equals(item.getEnabled()),
         item.getSortOrder(),
         item.getRemarks(),
         item.getCreateDatetime(),
         item.getUpdateDatetime()
      );
   }

   private void assertPrimaryAdmin() {
      this.embyUserService.assertCurrentUserCanManageAdministrators();
   }

   @Generated
   public PaymentPurchasePackageService(final EmbyInfoService embyInfoService, final EmbyUserService embyUserService) {
      this.embyInfoService = embyInfoService;
      this.embyUserService = embyUserService;
   }
}
